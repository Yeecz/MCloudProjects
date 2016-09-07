package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
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

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogOff extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.R1)
    RelativeLayout R1;
    @Bind(R.id.R2)
    RelativeLayout R2;
    @Bind(R.id.R3)
    RelativeLayout R3;
    private static final int REQUEST_CODE = 732;
    @Bind(R.id.touxian)
    CircleImageView touxian;
    @Bind(R.id.TV1)
    TextView TV1;
    @Bind(R.id.L1)
    LinearLayout L1;
    @Bind(R.id.TV2)
    TextView TV2;
//    private String capturePath = null;
//    private ArrayList<String> mResults = new ArrayList<>();
//    String a;
//    String b;
//    String c;
//    String d;
//    String e;
//    String f;
//    String g;
//    String url="http://10.50.7.49:8080/moyun/dispose";
//
//    Button bt2;
//    Button bt3;
//    // 图片名
//    public String name;
//
//    // 存储路径
//    private static final String PATH = Environment.getExternalStorageDirectory() + "/DCIM";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_off);
//        ButterKnife.bind(this);
//        getData();
//        backbutton = (ImageButton) findViewById(R.id.backbutton);
//        R2 = (RelativeLayout) findViewById(R.id.R2);
//        R3 = (RelativeLayout) findViewById(R.id.R3);
//        TV1= (TextView) findViewById(R.id.TV1);
//
//
//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        //添加账号返回登入界面
//        R2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LogOff.this, Login.class);
//                startActivity(intent);
//            }
//        });
//        R3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View popView = LayoutInflater.from(LogOff.this).inflate(R.layout.log_off_pop, null);
//                //当前页面的跟布局
//                View rootView = findViewById(R.id.L1);
//                final PopupWindow popupWindow = new PopupWindow(popView,
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow.setAnimationStyle(R.style.popupAnimation);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                //显示在根布局的底部
//                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
//                        0);
//
//                //点击空白处时，隐藏掉pop窗口
//                bt2 = (Button) popView.findViewById(R.id.bt2);
//                bt3 = (Button) popView.findViewById(R.id.bt3);
//
//                //退出点击事件
//                bt2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(LogOff.this, Login.class);
//                        startActivity(intent);
//                    }
//                });
//                bt3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        popupWindow.dismiss();
//                    }
//                });
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        TV1.setText(b);
//        super.onStart();
//    }
//
//    SQLiteDatabase mSQLiteDatabase;
//
//    public void getData() {
//        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
//        String CREATE_TABLE="select * from data ";
//
//        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
//        while (cursor1.moveToNext()) {
//            a = cursor1.getString(1);//用户id
//            b = cursor1.getString(2);//昵称
//            c = cursor1.getString(3);//头像
//            d = cursor1.getString(4);//性别
//            e = cursor1.getString(5);//生日
//            f = cursor1.getString(6);//手机
//            g = cursor1.getString(8);//签名
//            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
//        }
//        cursor1.close();
//        mSQLiteDatabase.close();
//    }


}

