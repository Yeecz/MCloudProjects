package com.example.yee.mcloudprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Friend;
import com.example.yee.mcloudprojects.entity.MCloudAccount;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.entity.RongToken;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "FAIL";
    String mUrl;
    MyApplication mycation;
    SQLiteDatabase mSQLiteDatabase;
    EditText number,password;
    Button login,forgetpwd,register;
    MUserData getRongToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        initData();
    }

    private void initData() {
        mycation = (MyApplication) getApplication();
        mUrl = mycation.getUrl();
        number = (EditText) findViewById(R.id.etnumber);
        password = (EditText) findViewById(R.id.etpassword);

        login = (Button) findViewById(R.id.but_login);
        forgetpwd= (Button) findViewById(R.id.but_forgetpwd);
        register = (Button) findViewById(R.id.but_register);

        login.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void login() {
        //初始化请求参数
        Log.i("aaaa", "login");
        String snumber = number.getText().toString();
        String spassword = password.getText().toString();
//        Toast.makeText(this,"长度："+snumber.length(),Toast.LENGTH_SHORT).show();
        if("".equals(snumber)||"".equals(spassword)){
            Toast.makeText(this,"帐号密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String flag ="";
        if(snumber.length()==11){
            flag = "54";
        }else{
            flag = "53";
        }
        SharedPreferences qu = getSharedPreferences("init", 0);
        int phonekey = qu.getInt("phonekey",0);

        RequestParams params = new RequestParams(mUrl);
        params.addBodyParameter("phonekey",String.valueOf(phonekey));
        params.addBodyParameter("state","24");
        params.addBodyParameter("flag", flag);
        params.addBodyParameter("number", snumber);
        params.addBodyParameter("password", spassword);

        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                gohome(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e(TAG, ex.getMessage());
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

    public void gohome(String result){

        Type type = new TypeToken<List<Object>>() {}.getType();
        Type type1 = new TypeToken<List<Friend>>(){}.getType();
        List<Object> loginbag;
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        loginbag = gson.fromJson(result, type);
        STATE state = STATE.valueOf(loginbag.get(0).toString());
//        Log.i("Login", "Login->gohome:" + loginbag.size());
//        Log.i("Login", "Login->gohome:" + loginbag.get(2));
//        Log.i("Login", "Login->gohome:" + loginbag.get(2).toString());
//        getRongToken = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
//        Log.i("Login", "Login->gohome:" + getRongToken.toString());
        Log.i("aaaa", "gohome  state="+state.getName());



        switch (state){
            case ONELOGIN:
                //


                Log.i("aaaa", "ONELOGIN----------");
                MCloudAccount account = gson.fromJson(loginbag.get(1).toString(),MCloudAccount.class);
                Log.i("aaaa",""+loginbag.get(2));
                MUserData data = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
//                MUserData data = (MUserData) loginbag.get(2);
                getRongToken = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
                Log.i("Login", "Login->data:" + data.toString());
                List<Friend> lf = gson.fromJson(loginbag.get(3).toString(),type1);
//                List<Friend> lf = (List<Friend>) loginbag.get(3);
                client2Server(this,data);
                addData(account,data,lf);

                break;
            case ONELOGIN_PHONE:

                Log.i("aaaa", "ONELOGIN---PHONE");
                MCloudAccount account1 = gson.fromJson(loginbag.get(1).toString(),MCloudAccount.class);
                Log.i("aaaa",""+loginbag.get(2));
                MUserData data1 = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
                getRongToken = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
//                MUserData data1 = (MUserData) loginbag.get(2);
                List<Friend> lf1 = gson.fromJson(loginbag.get(3).toString(),type1);
//                List<Friend> lf1 = (List<Friend>) loginbag.get(3);
                truncateTable();
                Log.i("Login", "Login->data1:" + data1.toString());
                client2Server(this,data1);
                addData(account1,data1,lf1);
                break;
            case MATCHING:
                Log.i("aaaa", "MATCHING");
                Toast.makeText(Login.this, state.getName() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this,CloudSeaModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            case FREEZE:
                Toast.makeText(Login.this, "此帐号被冻结", Toast.LENGTH_LONG).show();
                break;
            case NOACTIVATED:
                Toast.makeText(Login.this, "此帐号未激活", Toast.LENGTH_LONG).show();
            default:
                Toast.makeText(Login.this, "帐号或密码有误，请重新输入。", Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void truncateTable(){//清空表
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "delete from account";
        mSQLiteDatabase.execSQL(CREATE_TABLE);
        String CREATE_TABLE2 = "delete from data";
        mSQLiteDatabase.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "delete from friend";
        mSQLiteDatabase.execSQL(CREATE_TABLE3);
        String CREATE_TABLE4 = "delete from rongtoken";
        mSQLiteDatabase.execSQL(CREATE_TABLE4);
        Log.i("aaaa","本地表已清空");
    }
    //插入资料
    public void addData(MCloudAccount account,MUserData data,List<Friend> lf){
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        Log.i("aaaa", "addData");

        String CREATE_TABLE = "insert into account(user_id ,number ,phone ,password ," +
                "mail ,state ,skey ,ver ) values(?,?,?,?,?,?,?,?)";
        mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                account.getId(),account.getNumber(),
                account.getPhone(),account.getPassword(),
                account.getMail(),account.getState(),
                account.getKey(),account.getVer()
        });
        String CREATE_TABLE2 = "insert into data(user_id,name,headportrait,sex ," +
                "birthday,phone,ver) values(?,?,?,?,?,?,?)";
        mSQLiteDatabase.execSQL(CREATE_TABLE2,new Object[]{
                data.getId(),data.getName(),data.getHeadportrait(),
                data.getSex(),data.getBirthday(),data.getPhone(),data.getVer()
        });

        String CREATE_TABLE3 = "insert into friend(user_id ,friend_id ,name ,headportrait ," +
                "remarks ,ver,sign )values(?,?,?,?,?,?,?)";
        int i=0;
        for(Friend f:lf){
            mSQLiteDatabase.execSQL(CREATE_TABLE3,new Object[]{
                    f.getUser_id(),f.getFriend_id(),f.getName(),
                    f.getHeadportrait(),f.getRemarks(),f.getVer(),f.getSign()
            });
            i++;
        }

        Log.i("aaaa", "插入完成  ,朋友 "+i+" 条");

        Intent intent = new Intent(Login.this,CloudSeaModuleActivity.class);
        startActivity(intent);
        finish();

    }


    //连接融云服务器
    public void initRong(RongToken rongToken) {
        RongIM.connect(rongToken.getMtoken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("GetTokenActivity", "GetTokenActivity->onTokenIncorrect：融云连接失败");

                client2Server(Login.this,getRongToken);
            }

            @Override
            public void onSuccess(String s) {
                Log.i("GetTokenActivity", "GetTokenActivity->onSuccess：融云连接成功");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
//                client2Server(Login.this,getRongToken);

                Log.i("GetTokenActivity", "GetTokenActivity->onError：融云连接错误:" + errorCode.toString());
            }
        });
    }


    //从服务器获取rongtoken对象
    public void client2Server(Context context,MUserData mUserData){
        String url = "http://10.50.7.49:8080/moyun/GetTokenTest";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String mUser = gson.toJson(mUserData);
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("MCloudCode","10");
        params.addBodyParameter("MUser",mUser);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("GetTokenActivity", "GetTokenActivity->onSuccess");
                Gson gson = new Gson();
                RongToken rongToken = gson.fromJson(result,RongToken.class);
                mSQLiteDatabase = Login.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                String CREATE_TABLE = "insert into rongtoken(code ,userId ,token) values(?,?,?)";
                mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                        rongToken.getCode(),rongToken.getUserId(),rongToken.getMtoken()
                });
                Log.i("Login", "onSuccess:RongToken 插入完成" );
                initRong(rongToken);
                Log.i("GetRongToken", "获取token -- onSuccess:" + rongToken.getMtoken());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("GetTokenActivity", "GetTokenActivity->onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("GetTokenActivity", "GetTokenActivity->onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("GetTokenActivity", "GetTokenActivity->onFinished");
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.but_login:
                Log.i("aaaa","but_login");
                login();
                break;
            case R.id.but_forgetpwd:
                Log.i("aaaa","but_forgetpwd");
                Intent intent1=new Intent(Login.this,ForgetPassword.class);
                startActivity(intent1);
                break;
            case R.id.but_register:
                Log.i("aaaa","but_register");
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
                break;
        }
    }

//------------------------------------------------------------------------------------------------enum
    static enum STATE {
        NONE("账号不存在的",-1),
        ONELOGIN("第一次登陆",1),
        ONELOGIN_PHONE("第一次在本手机登陆",2),
        FREEZE ("冻结",20),
        NOACTIVATED ("未激活",0),
        NORMAL("正常",1),
        G2_ONLINE ("2G在线",21),
        G3_ONLINE ("3G在线",22),
        G4_ONLINE ("4G在线",23),
        WIFI_ONLINE ("wifi在线",24),
        OFFLINE ("离线",25),
        MATCHING("匹配可以登陆",29),
        MISMATCHING("账号或密码不匹配",30),
        DATAUPDAE("需要更新资料",31),
        NONE_OPTION("不存在的STATE选项",100);





        private String name;
        private int state;

        STATE(String name,int state){
            this.name=name;
            this.state=state;
        }

        public static STATE queryState(int state){

            for(STATE s:STATE.values()){
                if(s.state==state){
                    return s;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

    }


}

