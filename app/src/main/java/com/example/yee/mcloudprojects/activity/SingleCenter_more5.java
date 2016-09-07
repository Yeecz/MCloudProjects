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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleCenter_more5 extends AppCompatActivity implements View.OnClickListener {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;

    MyApplication myApplication;
    String url;
    private static final int REQUEST_CODE = 732;
    private String capturePath = null;
    private ArrayList<String> mResults = new ArrayList<>();
    // 图片名
    public String name;

    PopupWindow popupWindow;
    //（4）关于上传头像
    List<String> files = new ArrayList<>();
    String username;
    UploadManager uploadManager;
    int progress = 1;
    List<String> imagenames = new ArrayList<>();

    public static final int UPCOMPLEWHAT = 200;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==UPCOMPLEWHAT){
                Toast.makeText(SingleCenter_more5.this, "上传成功！", Toast.LENGTH_SHORT).show();
                headportrait();
                finish();
            }
        }
    };


    // 存储路径
    private static final String PATH = Environment.getExternalStorageDirectory() + "/DCIM";

    Button bt2;
    Button bt3;
    RelativeLayout R1;
    ImageButton backbutton;
    CircleImageView mcloud_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_center_more5);
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();
        mcloud_tx= (CircleImageView) findViewById(R.id.mcloud_tx);
        R1= (RelativeLayout) findViewById(R.id.R1);
        backbutton = (ImageButton) findViewById(R.id.backbutton);


        String touxiang = getData();
        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/" + touxiang)
                .placeholder(R.drawable.cloud_user_sliding)
                .into(mcloud_tx);

        //返回单击点击事件点击跳转回SingleCenter
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //
        mcloud_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popView = LayoutInflater.from(SingleCenter_more5.this).inflate(R.layout.take_photo_pop, null);
                //当前页面的跟布局
                View rootView = findViewById(R.id.L1);
                popupWindow = new PopupWindow(popView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setAnimationStyle(R.style.popupAnimation);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失

                //显示在根布局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                        0);
                //点击空白处时，隐藏掉pop窗口

                bt2 = (Button) popView.findViewById(R.id.bt2);
                bt3 = (Button) popView.findViewById(R.id.bt3);




                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //从相册中选
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");//相片类型
                        startActivityForResult(intent, 11);
                        popupWindow.dismiss();
                    }

                });
                bt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();


                    }
                });
            }
        });

        //提交成功点击事件
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToQiniu();
                Toast.makeText(SingleCenter_more5.this,"设置成功",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void uploadImageToQiniu() {
        initQiniu();
        getToken();

    }


    /**
     * (1)上传图片到七牛
     * @param filePath 要上传的图片路径
     * @param token 在七牛官网上注册的token
     */
    private void uploadImageToQiniu(String filePath, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
            }
        }, null);
    }
    //（2）访问自己的服务器获取token;
    public void getToken() {
        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("flag", "5");
        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    String token = result;
                    Log.i("UpImage", "onSuccess:" + token);
                    upImage(token);

                } else {
                    getToken();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                getToken();
                Log.e("erorr", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("onCancelled", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("onFinished", "onFinished");
            }
        });
    }
    //(7)initQiniu()
    public void initQiniu() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(null)  // recorder分片上传时，已上传片记录器。默认null
//              .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);

        Log.i("UpImage", "UpImage->initQiniu");
    }
    //（5）创建照片姓名格式
    public String createImageName(String imgPreFix,int p,String username) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String imgp = String.valueOf(p);
        String key = df.format(new Date());
        String keyheader = "dymaic";;
        key = key.trim();
        if (key != null && !"".equals(key)) {
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) >= 48 && key.charAt(i) <= 57) {
                    keyheader += key.charAt(i);
                }
            }
        }
        return keyheader + username + imgp + "." +  imgPreFix;
    }

    //(3)使用获取的token把头像上传到七牛云
    String k;
    public void upImage(String token) {
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        Log.i("UpImage", "UpImage->upImage");

//        File data = url;
//        File data = new File("/mnt/sdcard/login_bg2.jpg");//<File对象、或 文件路径、或 字节数组>
//        String key = filename; //<指定七牛服务上的文件名，或 null>;
        if (token != null) {
            Log.i("UpImage", "upImage:开始");
            Log.i("UpImage", "file.size:" + files.size());
            for (int i = 0; i < files.size(); i++) {
                File data = new File(files.get(i));
                Log.i("UpImage", "data:" + files.get(i));
                String key = createImageName(getPreFix(data),i,username);
                Log.i("UpImage", "key:" + key);
                k = key;
                uploadManager.put(data, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                upComplete(info);
                                progress++;
                            }
                        }, null);

                imagenames.add(key);
            }

        } else {
            Log.i("UpImage", "error");
        }


    }
    //（6）get网络
    public String getPreFix(File file) {
//        File f =new File("TileTest.doc");
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Log.i("UpImage", "UpImage->get");
        return prefix;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
            //判断一下是否符合要求
            case R.id.mcloud_tx:

                break;

        }

    }
    //（7）上传结束
    public void upComplete(ResponseInfo info){
        if (info.isOK()&&progress == files.size()){
            handler.sendEmptyMessage(UPCOMPLEWHAT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println(resultCode);
        Log.i("SingleCenter_more5", "SingleCenter_more5:" + resultCode);
        //  Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
        if (requestCode == 11 && resultCode == RESULT_OK && data!=null) {
            files.clear();
            Uri uri = data.getData();
            Log.i("txuri", "onActivityResult: " + uri);
            Log.i("txuri", "onActivityResult: " + uri.getPath());
            Log.i("txuri", "onActivityResult: " + uri.toString());
            files.add(uri.getPath());

            Picasso.with(this).load(uri).placeholder(R.drawable.avatar_default).into(mcloud_tx);

        }
        if(requestCode == 10 && resultCode == RESULT_OK && data!=null){
            files.clear();
            if(data.getData() != null){
                Uri uri = data.getData();
                files.add(uri.getPath());
                Picasso.with(this).load(uri).placeholder(R.drawable.avatar_default).into(mcloud_tx);
            }else{
                Bundle bundle = data.getExtras();
                Bitmap   bitmap = (Bitmap) bundle.get("data");
                Uri  uriImageData  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                Picasso.with(this).load(uriImageData).placeholder(R.drawable.avatar_default).into(mcloud_tx);
            }
        }

    }
    public void gaiTX(){
        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/" + k)
                .placeholder(R.drawable.cloud_user_sliding)
                .into(mcloud_tx);
    }
    //从本能地数据库中获取
    @Override
    protected void onStart() {
        super.onStart();

    }
    public void headportrait(){
        tijiao(k);
        getData();
    }

    //传到本地服务端
    public void tijiao(String headportrait){
        String user_id="0";
        SQLiteDatabase mSQLiteDatabase = SingleCenter_more5.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from data";
        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        while(cur.moveToNext()){
            user_id = cur.getString(1);
        }

        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("headportrait",headportrait);
        params.addBodyParameter("flag", "12");
        params.addBodyParameter("user_id",user_id);
        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if("ok".equals(result)){
                    SQLiteDatabase mSQLiteDatabase = SingleCenter_more5.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                    String CREATE_TABLE="update data set headportrait=?";
                    mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                            k
                    });
                    mSQLiteDatabase.close();
                }

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e("zzzzzzz", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("zzzzzz", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("zzzzzz", "onFinished");
            }
        });
    }
    //查找获取头像
    SQLiteDatabase mSQLiteDatabase;

    public String getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from data ";

        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            return cursor1.getString(3);//头像
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase.close();
        return null;
    }


}