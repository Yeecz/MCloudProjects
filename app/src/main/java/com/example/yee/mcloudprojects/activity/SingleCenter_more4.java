package com.example.yee.mcloudprojects.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SingleCenter_more4 extends AppCompatActivity {
    String j;

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.user_text)
    EditText userText;
    @Bind(R.id.sex_text)
   TextView sexText;
    @Bind(R.id.brithday_text)
    TextView brithdayText;
    @Bind(R.id.phone_text)
    EditText phonetext;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.text_i2)
    TextView textI2;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.personality)
    EditText personality;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String items[]={"女","男"};
    String select_item = null;

    MyApplication myApplication;
    String url;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_center_more4);
        ButterKnife.bind(this);
        MyApplication myApplication= (MyApplication) getApplication();
        url = myApplication.getUrl();
       init();
       addlistener();
   }

    private void addlistener() {
        final Calendar i = Calendar.getInstance();
        sexText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1();
            }
        });
        brithdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("DatePickerDialog", "onClick: ");
                DatePickerDialog dialog = new DatePickerDialog(SingleCenter_more4.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                i.set(year, monthOfYear, dayOfMonth);
                                brithdayText.setText(DateFormat.format("yyy-MM-dd", i));
                            }
                        }, i.get(Calendar.YEAR), i.get(Calendar.MONTH),i.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        textI2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tijiao();
                // finish();
            }
        });
        //返回点击事件跳转至SingleCenter

        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        userText= (EditText) findViewById(R.id.user_text);
        sexText= (TextView) findViewById(R.id.sex_text);
        phonetext= (EditText) findViewById(R.id.phone_text);
        personality= (EditText) findViewById(R.id.personality);
        brithdayText= (TextView) findViewById(R.id.brithday_text);
        myApplication = (MyApplication) getApplication();
        textI2 = (TextView) findViewById(R.id.text_i2);
    }

    public void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                SingleCenter_more4.this);
        builder.setTitle("请选择性别");
        builder.setSingleChoiceItems(items, Integer.parseInt(j), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                changeSex(which);
              //  Toast.makeText(SingleCenter_more4.this,""+j,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();


    }

    private void changeSex(int which) {
        Log.i("SingleCenter_more4", "changeSex:which:" + which);
        select_item = items[which].toString();
        Log.i("SingleCenter_more4", "changeSex:" + select_item);
        sexText.setText(select_item);

        if("男".equals(select_item)){
            j="1";
        }else{
            j="0";
        }
    }

    //传给数据库
    public void tijiao (){

        if("".equals(userText.getText().toString())){
            Toast.makeText(SingleCenter_more4.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String user_id="0";
        SQLiteDatabase mSQLiteDatabase = SingleCenter_more4.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from data";
        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        while(cur.moveToNext()){
            user_id = cur.getString(1);
        }
        String birth = brithdayText.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long bri=0;
        try {
            bri = sdf.parse(birth).getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
//        String i;

//        if(sexText.equals("男")){
//            i="1";
//        }else{
//            i="0";
//        }
        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("user_id",user_id);
        params.addBodyParameter("name",userText.getText().toString());
        params.addBodyParameter("sex",j);
        params.addBodyParameter("birthday",String.valueOf(bri));
        params.addBodyParameter("phone",phonetext.getText().toString());
        params.addBodyParameter("sign",personality.getText().toString());
        params.addBodyParameter("flag","81");

        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if("ok".equals(result)){
                    SQLiteDatabase mSQLiteDatabase = SingleCenter_more4.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                    String CREATE_TABLE="update data set name=?,sex=?,birthday=?,phone=?,ver=ver+1,sign=?";
                    mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                            userText.getText().toString(),Integer.parseInt(j),
                            brithdayText.getText().toString(),phonetext.getText().toString(),personality.getText().toString()
                    });
                    mSQLiteDatabase.close();
                }
                Toast.makeText(SingleCenter_more4.this,"修改成功",Toast.LENGTH_SHORT).show();
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


    //从数据库中获取


    @Override
    protected void onStart() {
        getData();
        userText.setText(b);
        if("1".equals(d)){
            sexText.setText("男");
            select_item = "男";
            j="1";

        }else{
            sexText.setText("女");
            select_item = "女";
            j="0";

        }
        brithdayText.setText(e);
        phonetext.setText(f);
        personality.setText(g);
    super.onStart();
    }

    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from data ";

        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(1);//用户id
            b = cursor1.getString(2);//昵称
            c = cursor1.getString(3);//头像
            d = cursor1.getString(4);//性别
            e = cursor1.getString(5);//生日
            f = cursor1.getString(6);//手机
            g = cursor1.getString(8);//签名
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase.close();
    }


}



