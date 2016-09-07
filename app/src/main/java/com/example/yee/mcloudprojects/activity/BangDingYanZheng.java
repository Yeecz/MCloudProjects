package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class BangDingYanZheng extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.ib)
ImageButton ib;
    @Bind(R.id.L1)
    LinearLayout L1;
    @Bind(R.id.TV1)
    TextView TV1;
    @Bind(R.id.ET1)
    EditText ET1;
    @Bind(R.id.TV2)
    TextView TV2;
    @Bind(R.id.ET2)
    EditText ET2;
    @Bind(R.id.bt1)
    Button bt1;
    @Bind(R.id.bt2)
    Button bt2;
    int i = 60;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -9) {
                bt1.setText("重发（" + i + "）");
            } else if (msg.what == -8) {
                bt1.setText("获取验证码");
                bt1.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BangDingYanZheng.this, SingleCenter_more3.class);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_ding_yan_zheng);
        ButterKnife.bind(this);
        init();
        ib= (ImageButton) findViewById(R.id.ib);

        //绑定账号返回单击事件
        ib.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }
    private void init() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        // 启动短信验证sdk
        SMSSDK.initSDK(this,"1600a8e96c95a","88bbf6b5749512afa8481fef5279c5e0");
        EventHandler eventHandler=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onClick(View view) {
        String phoneNums = ET1.getText().toString();
        switch (view.getId()) {
            case R.id.bt1:
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                bt1.setEnabled(false);
                bt1.setText("重发(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.bt2:
                SMSSDK.submitVerificationCode("86", phoneNums, ET2.getText().toString());
                createProgressBar();
                break;
        }
    }
    //判断手机号码是否合理
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }
    //判断一个字符串的位数
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }
    //验证手机格式
    public static boolean isMobileNO(String mobileNums) {
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }
    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}