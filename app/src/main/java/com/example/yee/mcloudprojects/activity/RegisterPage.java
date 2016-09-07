package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterPage extends AppCompatActivity {
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.ET1)
    EditText ET1;      //昵称
    @Bind(R.id.ET2)
    EditText ET2;
    @Bind(R.id.ET3)
    EditText ET3;     //密码
         //确认密码
    @Bind(R.id.button)
    Button button;   //注册

    MyApplication myApplication;

    String url;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        ButterKnife.bind(this);
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();
        Intent intent = getIntent();
        phone=intent.getStringExtra("phone");
        button= (Button) findViewById(R.id.button);
        backbutton= (ImageButton) findViewById(R.id.backbutton);

        //返回事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //注册单击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yanzheng();
            }
        });
//        Toast.makeText(RegisterPage.this, "获得手机号 ： "+phone, Toast.LENGTH_SHORT).show();
    }

//判断密码方法
    public void yanzheng(){
        EditText name = (EditText) findViewById(R.id.ET1);
        EditText password1= (EditText) findViewById(R.id.ET2);
        EditText password2= (EditText) findViewById( R.id.ET3);
        if("".equals(name.getText().toString())){
            Toast.makeText(RegisterPage.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password1.getText().toString().length()<5||password1.getText().toString().length()>16){
            Toast.makeText(RegisterPage.this, "密码长度不能小于6位或大于16位", Toast.LENGTH_SHORT).show();
                    return;}
        if(password1.getText().toString()!=password2.getText().toString()){
            Toast.makeText(RegisterPage.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;

        }if(password1.getText().toString().length()!=password2.getText().toString().length()){
            Toast.makeText(RegisterPage.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(RegisterPage.this,"注册成功",Toast.LENGTH_SHORT).show();
        tijiao(name.getText().toString(),password2.getText().toString());
        Intent intent=new Intent(RegisterPage.this,Login.class);
        startActivity(intent);
    }

//提交的方法
public void tijiao(String name,String password){
    RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
    params.addBodyParameter("name",name);
    params.addBodyParameter("phone",phone);
    params.addBodyParameter("flag", "2");
    params.addBodyParameter("password",password);




    //设置请求类型，并开始访问服务器，获取服务器返回结果
    x.http().post(params, new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {

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


}


