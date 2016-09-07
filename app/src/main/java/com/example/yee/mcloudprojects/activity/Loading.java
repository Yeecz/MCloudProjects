package com.example.yee.mcloudprojects.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Friend;
import com.example.yee.mcloudprojects.entity.MCloudAccount;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.entity.RongToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class Loading extends AppCompatActivity {

    String mUrl;
    MyApplication myAppliacation;
    Intent intent;
    SQLiteDatabase mSQLiteDatabase;
    Login login = new Login();



    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_loading);

        long time=System.currentTimeMillis();
        Log.i("aaaa","Loading onCreate time="+time);


        initData();
        isOneLogin();
        Log.i("aaaa","Loading onCreateOVER");
    }

    private void initData() {
        myAppliacation = (MyApplication) getApplication();
        mUrl = myAppliacation.getUrl();
    }

    public void isOneLogin(){

        SharedPreferences qu = getSharedPreferences("init", 0);
        boolean one = qu.getBoolean("isone",false);

        if(one){
            int phonekey = qu.getInt("phonekey",0);
            Log.i("aaaa",phonekey+"取出");
            islogin(phonekey);
            linkRong();


        }else {
            Log.i("aaaa","首次开启应用");
            initrequest();
            initDataBase();

            handler.sendEmptyMessageDelayed(1,2000);
        }

    }

    private void linkRong() {


        linkRongFromDisk();


    }

    private void linkRongFromDisk() {
        String QueryRongToken = "select * from rongtoken";
        Cursor cursor3 = mSQLiteDatabase.rawQuery(QueryRongToken,null);
        //查询rongtoken获取token，code，userid
        int code = 0;
        String userId = null;
        String token = null;
        while(cursor3.moveToNext()){
            code = cursor3.getInt(1);
            userId = cursor3.getString(2);
            token = cursor3.getString(3);
        }
        RongToken rongToken = new RongToken();
        rongToken.setCode(code);
        rongToken.setUserId(userId);
        rongToken.setMtoken(token);
        initRong(rongToken);
    }

    private MUserData reGetToken() {
        MUserData getRongToken = null;
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE2 = "select * from data ";
        Cursor cursor1 =  mSQLiteDatabase.rawQuery(CREATE_TABLE2,null);
        int id = 0;
        String name = null;
        String tx = null;
        int sex = 0;
        String d = null;
        int photo = 0;
        int ver = 0;
        String sign = null;
        while (cursor1.moveToNext()){
            id = cursor1.getInt(1);
            name = cursor1.getString(2);
            tx = cursor1.getString(3);
            sex = cursor1.getInt(4);
            d = cursor1.getString(5);
            photo = cursor1.getInt(6);
            ver = cursor1.getInt(7);
            sign = cursor1.getString(8);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = (Date) sdf.parse(d);
            getRongToken.setBirthday(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getRongToken = new MUserData();
        getRongToken.setId(id);
        getRongToken.setName(name);
        getRongToken.setHeadportrait(tx);
        getRongToken.setSex(sex);
        getRongToken.setPhone(photo);
        getRongToken.setVer(ver);
        getRongToken.setSign(sign);

        return getRongToken;
    }


    public void islogin(int phonekey){



        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from account ";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        String CREATE_TABLE2 = "select * from data ";
        Cursor cursor1 =  mSQLiteDatabase.rawQuery(CREATE_TABLE2,null);
        String CREATE_TABLE3 = "select * from friend";
        Cursor cursor2 =  mSQLiteDatabase.rawQuery(CREATE_TABLE3,null);
        String QueryRongToken = "select * from rongtoken";
        Cursor cursor3 = mSQLiteDatabase.rawQuery(QueryRongToken,null);
        //获取sqlite表中的 RongToken对象

        String snumber = null;
        String spassword = null;
        String accountver= null;
        String dataver= null;


        //查询rongtoken获取token，code，userid
        int code = 0;
        String userId = null;
        String token = null;
        while(cursor3.moveToNext()){
            code = cursor3.getInt(1);
            userId = cursor3.getString(2);
            token = cursor3.getString(3);
        }
        RongToken rongToken = new RongToken();
        rongToken.setCode(code);
        rongToken.setUserId(userId);
        rongToken.setMtoken(token);

        //将RongToken对象 传入，连接融云服务器
        login.initRong(rongToken);

        while (cursor.moveToNext()){
            snumber =cursor.getString(2);
            spassword =cursor.getString(4);
            accountver =cursor.getString(8);
            Log.i("aaaa","snumber="+snumber+", spassword="+spassword+" ,dataver = "+accountver);
        }
        while (cursor1.moveToNext()){
            dataver =cursor1.getString(6);
            Log.i("aaaa","dataVer = "+dataver);
        }
        List<String[]> lver = new ArrayList();
        while (cursor2.moveToNext()){
            lver.add(new String[]{cursor2.getString(6),cursor2.getString(2)});
            Log.i("aaaa","好友签名 "+cursor2.getString(7));
        }
        Log.i("aaaa","好友版本："+lver.toString());

        Log.i("aaaa","snumber="+snumber+",spassword="+spassword);
        if(snumber!=null&&spassword!=null){
            login(snumber,spassword,accountver,phonekey,dataver,lver);
        }else{
            Log.i("aaaa","未获取到本地数据");
            handler.sendEmptyMessageDelayed(1,2000);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Log.i("aaaa","what=1");

                Intent intent = new Intent(Loading.this,Login.class);
                startActivity(intent);
                finish();
            }
            if (msg.what==2){
                Log.i("aaaa","what=2");
                Intent intent = new Intent(Loading.this,CloudSeaModuleActivity.class);
                startActivity(intent);
                finish();

            }
            if(msg.what==3){
                startActivity(intent);
                finish();
            }

        }
    };


    public void initrequest(){
        Log.i("aaaa","initrequest");
        RequestParams params = new RequestParams(mUrl);
        params.addBodyParameter("flag", "99");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("aaaa","onSuccess");
                SharedPreferences sharedPreferences = getSharedPreferences("init", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isone",true);
                int phonekey = Integer.parseInt(result);
                editor.putInt("phonekey",phonekey);
                editor.commit();
                Log.i("aaaa",""+result+"接收");
                if("-1".equals(result)){
                    initrequest();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.i("aaaa","onError"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("aaaa","onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("aaaa","onFinished");
            }
        });

    }



    private void login(String snumber,String spassword,String accountver,int phonekey,String dataver,List<String[]> lver) {
        //初始化请求参数
//        Type type =new TypeToken<List<String[]>>(){}.getType();
        Gson gson = new Gson();
        String slver = gson.toJson(lver);

        Log.i("aaaa", "login()");
        RequestParams params = new RequestParams(mUrl);
        params.addBodyParameter("flag", "53");//-------------------------------c
        params.addBodyParameter("number",snumber);
        params.addBodyParameter("password", spassword);
        params.addBodyParameter("phonekey", String.valueOf(phonekey));
        params.addBodyParameter("state", String.valueOf(24));
        params.addBodyParameter("accountver", accountver);
        params.addBodyParameter("dataver", dataver);
        params.addBodyParameter("slver", slver);


        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                gohome(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e("aaaa", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("aaaa", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("aaaa", "onFinished");
            }
        });
    }

    //连接融云服务器
    public void initRong(RongToken rongToken) {
        RongIM.connect(rongToken.getMtoken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("GetTokenActivity", "GetTokenActivity->onTokenIncorrect：融云连接失败");
                client2Server(Loading.this,reGetToken());

            }

            @Override
            public void onSuccess(String s) {
                Log.i("GetTokenActivity", "GetTokenActivity->onSuccess：融云连接成功");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                client2Server(Loading.this,reGetToken());
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
                mSQLiteDatabase = Loading.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
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

    public void gohome(String result){
        Type type = new TypeToken<ArrayList>() {}.getType();
        Type type1 = new TypeToken<List<Friend>>(){}.getType();
        List loginbag;
        Gson gson = new Gson();
        loginbag = gson.fromJson(result, type);
        STATE state = STATE.valueOf(loginbag.get(0).toString());

//        Toast.makeText(Loading.this, state.getName() , Toast.LENGTH_LONG).show();
        switch (state){
            case MATCHING:
                intent = new Intent(Loading.this,CloudSeaModuleActivity.class);
                handler.sendEmptyMessageDelayed(3,2000);
                break;
            case DATAUPDAE:
                MCloudAccount account = gson.fromJson(loginbag.get(1).toString(),MCloudAccount.class) ;
                MUserData data = gson.fromJson(loginbag.get(2).toString(),MUserData.class);
                List<Friend> lf = gson.fromJson(loginbag.get(3).toString(),type1);

                break;
            case FREEZE:
                break;
            case MISMATCHING:
                Toast.makeText(Loading.this, "帐号异常", Toast.LENGTH_LONG).show();
                SharedPreferences acc = getSharedPreferences("MCloudAccount", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = acc.edit();
                editor.clear();
                editor.commit();

                handler.sendEmptyMessageDelayed(1,1000);
                break;
            default:

                break;
        }

    }
    //--------------------------------------------------------------------------------------------

    public void dataUpdate(MCloudAccount account,MUserData data,List<Friend> lf){
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        Log.i("aaaa", "initDataBase");

        String CREATE_TABLE = "update account set";
        mSQLiteDatabase.execSQL(CREATE_TABLE);
        String CREATE_TABLE2 = "create table data(_id INTEGER PRIMARY KEY autoincrement," +
                "id INTEGER,name VARCHAR,headportrait VARCHAR,sex INTEGER,birthday DATA," +
                "phone VARCHAR,ver INTEGER)";
        mSQLiteDatabase.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "create table friend(_id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER,friend_id INTEGER,name VARCHAR,headportrait VARCHAR," +
                "remarks INTEGER,ver INTEGER)";
        mSQLiteDatabase.execSQL(CREATE_TABLE3);

    }




//-----------------------------------------------------------------------------------------------
    public void initDataBase() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        Log.i("aaaa", "initDataBase");

        String CREATE_TABLE = "create table account(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user_id INTEGER,number INTEGER,phone VARCHAR,password VARCHAR," +
                "mail VARCHAR,state INTEGER,skey INTEGER,ver INTEGER)";
        mSQLiteDatabase.execSQL(CREATE_TABLE);
        String CREATE_TABLE2 = "create table data(_id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER,name VARCHAR,headportrait VARCHAR,sex INTEGER,birthday DATA," +
                "phone VARCHAR,ver INTEGER,sign VARCHAR)";
        mSQLiteDatabase.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "create table friend(_id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER,friend_id INTEGER,name VARCHAR,headportrait VARCHAR," +
                "remarks INTEGER,ver INTEGER,sign VARCHAR)";
        mSQLiteDatabase.execSQL(CREATE_TABLE3);

        String CREATE_TABLE4 = "create table users(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "number INTEGER,ltime INTEGER)";
        mSQLiteDatabase.execSQL(CREATE_TABLE4);

        //创建融云获取的token用户表
        String CREATE_TABLE5 = "create table rongtoken(_id INTEGER PRIMARY KEY,code,userId,token)";
        mSQLiteDatabase.execSQL(CREATE_TABLE5);

        mSQLiteDatabase.close();
        Log.i("aaaa","表已創建");

    }





    //----------------------------------------------------------------------------------------------enum
    public enum STATE {
        NONE("账号不存在的",-1),
        ONELOGIN("第一次登陆",1),
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

