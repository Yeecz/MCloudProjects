package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.DynamicListNineAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CacheActivity;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.entity.Mylinked;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaeger.ninegridimageview.NineGridImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class User_data_Activity extends AppCompatActivity {

    TextView hename, text_ip, sex, dongtai, dizhi, word;
    ImageView touxian1, sexi;
    FrameLayout frameLayout;
    NineGridImageView nine;
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.sendbutton)
    Button sendbutton;
    @Bind(R.id.linearlayout3)
    LinearLayout linearlayout3;
    String name;
    MUserData  mUserData;
    String v1;
    String beizhu;
    Mylinked mu;
    String muri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_);
        ButterKnife.bind(this);
        mu = (Mylinked)getIntent().getSerializableExtra("Mylink");
        MyApplication myApplication = (MyApplication)getApplication();
        muri = myApplication.getUrl();
        initda();

        if (!CacheActivity.activityList.contains(User_data_Activity.this)) {
            CacheActivity.addActivity(User_data_Activity.this);
        }

    }

    private void initdata() {

        beizhu= getIntent().getStringExtra("Mylinked1");
        //----------------------------------------------------------------------
        name = mUserData.getName();       //姓名
        String userwords =mUserData.getSign();//签名
        String touxian = mUserData.getHeadportrait();    //头像
        touxian1= (ImageView) findViewById(R.id.touxian);
       // String background1 = my.getBackground();//背景图片
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.img_loading_bg)
                .setCircular(true)
                .build();
        //viewHolder.imageView.setImageResource(this.list.get(i).getUser_image());
        x.image().bind(touxian1,"http://oc1souo4f.bkt.clouddn.com/"+touxian,imageOptions);

        Integer sex = mUserData.getSex();             //性别

        //头像是个网络地址，通过xuntils从网络获得地址；


        hename = (TextView) findViewById(R.id.hename);//设置姓名
        hename.setText(name);
        word = (TextView) findViewById(R.id.words);//个性签名
        word.setText(userwords);
        dongtai = (TextView) findViewById(R.id.dongtai);//动态-语句
        dongtai.setText("你好啊！");
      sexi = (ImageView) findViewById(R.id.seximageView);//性别
        //判断性别
       if(sex==1){
           sexi.setImageResource(R.drawable.woman);
       }else if(sex==0){
            sexi.setImageResource(R.drawable.man);
       }
        frameLayout = (FrameLayout) findViewById(R.id.frame);//设置背景
        frameLayout.setBackgroundResource(R.drawable.aaa);
        //地址
       // text_ip = (TextView) findViewById(R.id.text_ip);//地址
        //text_ip.setText("苏州");
        nine=(NineGridImageView) findViewById(R.id.dongtainine);
        String[] pics ={"http://f.hiphotos.baidu.com/news/pic/item/9358d109b3de9c82954897de6481800a19d843b5.jpg",
                "http://f.hiphotos.baidu.com/news/pic/item/9358d109b3de9c82954897de6481800a19d843b5.jpg",
                "http://f.hiphotos.baidu.com/news/pic/item/9358d109b3de9c82954897de6481800a19d843b5.jpg",
                "http://f.hiphotos.baidu.com/news/pic/item/9358d109b3de9c82954897de6481800a19d843b5.jpg"};
        if (pics!=null){
            List<String> picslist = Arrays.asList(pics);
            DynamicListNineAdapter dynamicListNineAdapter = new DynamicListNineAdapter();
         //   nine.setAdapter(dynamicListNineAdapter);
        //    nine.setImagesData(picslist);
        }else {
            nine.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.backbutton, R.id.more, R.id.sendbutton,R.id.linearlayout3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
               // Toast.makeText(this, "zxcxz", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.more:
              //  Toast.makeText(this, "gengduo", Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(this,Mycloud_moresActivity.class);
                 Bundle bundle=new Bundle();
                 bundle.putSerializable("Mylink", mu);
                 bundle.putSerializable("Mylinked2", mUserData);
                 bundle.putString("Mylinked1", beizhu);
                 intent1.putExtras(bundle);
                  startActivity(intent1);
                break;
            case R.id.sendbutton:
                Log.i("User_data_Activity", "发起聊天：" +mUserData.getId() + "," + mUserData.getName() );
                if (RongIM.getInstance()!=null){
                    UserInfo userInfo = new UserInfo(String.valueOf(mUserData.getId()),mUserData.getName(), Uri.parse("http://oc1souo4f.bkt.clouddn.com/" + mUserData.getHeadportrait()));
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    RongIM.getInstance().startPrivateChat(this,String.valueOf(mUserData.getId()),mUserData.getName());
                }

               // Toast.makeText(this, "fanhui", Toast.LENGTH_LONG).show();
                break;
            case R.id.linearlayout3:
                Toast.makeText(this, "空间跳转", Toast.LENGTH_LONG).show();
                break;
        }
    }

public void initda(){
    RequestParams params = new RequestParams(muri);
    params.addQueryStringParameter("flag", "65");
    params.addQueryStringParameter("id", "" +  mu.getFrident_id());
    x.http().get(params, new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
             mUserData = gson.fromJson(result, MUserData.class);
             initdata();
            // Toast.makeText(User_data_Activity.this,mUserData.toString()+"",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            ex.printStackTrace();

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    });
}
}
