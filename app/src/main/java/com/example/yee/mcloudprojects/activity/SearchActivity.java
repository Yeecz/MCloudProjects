package com.example.yee.mcloudprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MyListView;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.entity.Mylinked;
import com.example.yee.mcloudprojects.sqllite.RecordSQLiteOpenHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {

    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private ImageView iv;
    String muri;
    List<Mylinked> mylinkeds;
    Integer x1;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);
        getData();
        MyApplication myApplication = (MyApplication) getApplication();
        muri = myApplication.getUrl();
        // 初始化控件
      // check();
       // x1 = mylinkeds.get(0).getUser_id();
        iv = (ImageView) findViewById(R.id.backbutton);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();

        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    String s = et_search.getText().toString();
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                    if (a.equals(s) || a.equals("")) {
                        Toast.makeText(SearchActivity.this, "输不不能为自己或为空", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SearchActivity.this, "正在为您查找用户信息", Toast.LENGTH_LONG).show();
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    RequestParams params = new RequestParams(muri);
                    params.addQueryStringParameter("flag", "4");
                    params.addQueryStringParameter("res", "5");
                    params.addQueryStringParameter("text1", "" + et_search.getText().toString().trim());
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                            MUserData mUserData = gson.fromJson(result, MUserData.class);

                            final Intent intent = new Intent(SearchActivity.this, AddfridentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("UserData", mUserData);
                            intent.putExtras(bundle);
                            Timer time = new Timer();
                            TimerTask tk = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(intent);

                                }
                            };
                            time.schedule(tk, 500);
                            // Toast.makeText(SearchActivity.this,""+mUserData.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(SearchActivity.this, "找不到所在人的信息", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
                    // Intent intent=new Intent(SearchActivity.this,AddfridentActivity.class);
                    //startActivity(intent);
                }
                return false;
            }
        });


        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                //Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                if (a.equals(name)) {
                    Toast.makeText(SearchActivity.this, "输不不能为自己", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SearchActivity.this, "正在为您查找用户信息", Toast.LENGTH_SHORT).show();
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    RequestParams params = new RequestParams(muri);
                    params.addQueryStringParameter("flag", "4");
                    params.addQueryStringParameter("res", "5");
                    params.addQueryStringParameter("text1", "" + et_search.getText().toString().trim());
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                            MUserData mUserData = gson.fromJson(result, MUserData.class);

                            final Intent intent = new Intent(SearchActivity.this, AddfridentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("UserData", mUserData);
                            intent.putExtras(bundle);
                            Timer time = new Timer();
                            TimerTask tk = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(intent);

                                }
                            };
                            time.schedule(tk, 500);
                            // Toast.makeText(SearchActivity.this,""+mUserData.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(SearchActivity.this, "找不到所在人的信息", Toast.LENGTH_LONG).show();
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
        });

        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
//        Date date = new Date();
//        long time = date.getTime();
//        insertData("Leo" + time);
//
//        // 第一次进入查询所有的历史记录
        queryData("");
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);

        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
    }

    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE2 = "select * from account ";
        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE2, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(2);//用户id
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase.close();
    }
}