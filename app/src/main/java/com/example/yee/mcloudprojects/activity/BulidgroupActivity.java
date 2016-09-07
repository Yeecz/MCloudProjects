package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.MChatRoom;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.example.yee.mcloudprojects.utils.UpImage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class BulidgroupActivity extends AppCompatActivity {

    @Bind(R.id.backbutton)
ImageButton backbutton;
    @Bind(R.id.editText2)
    EditText editText2;
    @Bind(R.id.tijiao)
    Button tijiao;
    @Bind(R.id.chat_search)
    Button chatsearch;
    @Bind(R.id.chatroom_search_content)
            EditText csc;

    SimpleDraweeView draweeView;
    String text;
    Button b1;
    Button b2;
    Button b3;
    String a;
//-------------------------------------

    MyApplication myApplication;

    private List<MChatRoom> mChatRoomList = new ArrayList<>();
    List<String> photos;
    int chatRoomId;
    String chatRoomName;
    private String capturePath = null;
    private ArrayList<String> mResults = new ArrayList<>();
    // 图片名
    public String name;
    Gson gson = new Gson();
    SQLiteDatabase mSQLiteDatabase;
    // 存储路径
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           if(msg.what==200){
               Log.i("cxz","xcz");
           }
        }
    };
    private static final String PATH = Environment.getExternalStorageDirectory() + "/DCIM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_bulidgroup);
        ButterKnife.bind(this);
        text = editText2.getText().toString();//建立的群名称
        draweeView = (SimpleDraweeView) findViewById(R.id.imageView);
        getSupportActionBar().hide();
        getData();
    }

    @OnClick({R.id.backbutton, R.id.tijiao, R.id.imageView,R.id.chat_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
            case R.id.tijiao:
//                submit();
                //判断一下是否符合要求
                if (editText2.length()<1){
                    Toast.makeText(BulidgroupActivity.this, "取个名字吧", Toast.LENGTH_SHORT).show();
                }else {
                    chatRoom2Server();
                }
                

                break;
            case R.id.imageView:
                View popView = LayoutInflater.from(this).inflate(
                        R.layout.alert_dialog, null);
                //当前页面的跟布局
                View rootView = findViewById(R.id.main);
                final PopupWindow popupWindow = new PopupWindow(popView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setAnimationStyle(R.style.popupAnimation);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                // 顯示在根佈局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                        0);
                // 点击空白处时，隐藏掉pop窗口
                b1 = (Button) popView.findViewById(R.id.t1);
                b2 = (Button) popView.findViewById(R.id.t2);
                b3 = (Button) popView.findViewById(R.id.t3);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //拍照
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, 10);

                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        //从相册中选
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");//相片类型
                        startActivityForResult(intent,11);
                    }

                });
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.chat_search:
                Intent intent = new Intent(this,SearchChatRoomActivity.class);
                intent.putExtra("chatroomsearch",csc.getText().toString());
                startActivity(intent);

                break;
        }

    }

    private void chatRoom2Server() {
        chatRoomId = createChatRoomId();
        chatRoomName = editText2.getText().toString().trim();
        MChatRoom mChatRoom = new MChatRoom();
        mChatRoom.setId(String.valueOf(chatRoomId));
        mChatRoom.setName(chatRoomName);
        mChatRoomList.add(mChatRoom);

        GetLoginUser getLoginUser = new GetLoginUser(this);
        int userid = getLoginUser.getAccountId();

        String request = gson.toJson(mChatRoomList);

        String url = "http://10.50.7.49:8080/moyun/MChatRooms";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("MCloudCode","20");
        params.addBodyParameter("MGroups",request);
        params.addBodyParameter("user_id",String.valueOf(userid));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                createChatRoomState(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createChatRoomState(String result) {
        Log.i("BulidgroupActivity", "result: " +  result);
        if (result.equals("200")){
            if (RongIM.getInstance()!=null){
                RongIM.getInstance().startConversation(this, Conversation.ConversationType.CHATROOM, String.valueOf(chatRoomId), chatRoomName);
            }

            Toast.makeText(BulidgroupActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(BulidgroupActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(resultCode);
        //  Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
       photos = new ArrayList<>();

        String image=null;
        if(requestCode == 11){


            Uri uri = data.getData();
            image=uri.getPath();
            draweeView.setImageURI(uri);
        }
        if(requestCode == 10){
            if(data.getData() != null){
                Uri uri = data.getData();
                 image=uri.getPath();
                draweeView.setImageURI(uri);
            }else{
                Bundle bundle = data.getExtras();
                Bitmap   bitmap = (Bitmap) bundle.get("data");
                Uri  uriImageData  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                image=uriImageData.getPath();
                draweeView.setImageURI(uriImageData);
            }
        }
        photos.add(image);

    }
//}
//   }
//  }
//
//   }
    public void submit(){
        Toast.makeText(BulidgroupActivity.this,""+photos+a,Toast.LENGTH_LONG).show();
        new Thread(){
            @Override
            public void run() {
                super.run();

                 UpImage upImage = new UpImage(handler,photos,a);
                    upImage.startUpLoadImages();

            }
        }.start();
    }


    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from account";

        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(2);//用户id

        }
        cursor1.close();
        mSQLiteDatabase.close();
    }

    //生成一个云社id随机数
    public int createChatRoomId() {
        int number = 0;

        while(true){
            number = (int)(Math.random()*7);
            if(number>=4)
                break;
        }
        switch (number) {
            case 4:	number = (int)(Math.random()*9000+1000);
                break;
            case 5:	number = (int)(Math.random()*90000+10000);
                break;
            case 6:	number = (int)(Math.random()*900000+100000);
                break;
            default:break;
        }
        return number;
    }

}

