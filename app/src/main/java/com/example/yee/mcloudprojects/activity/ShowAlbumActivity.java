package com.example.yee.mcloudprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;

import com.example.yee.mcloudprojects.adapter.MyRecyclerPhotoAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Album;
import com.example.yee.mcloudprojects.entity.OnRecyclerViewItemClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowAlbumActivity extends AppCompatActivity {
    // private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";
    public static final String TAG = "ShowAlbumActivity";
    @Bind(R.id.back2)
    Button back2;
    @Bind(R.id.album_name2)
    TextView albumName2;
    @Bind(R.id.album_function2)
    ImageView albumFunction2;
    //    @Bind(R.id.album_upload_photo2)
//    Button albumUploadPhoto2;
    List<Album> lass = new ArrayList();
    private Context mContext;
    private Button editButton, manageButton, bigButton, smallButton, uploadButton;
    private boolean isBigShow = false;
    RecyclerView  recyclerView;
     MyRecyclerPhotoAdapter adapter;

    SQLiteDatabase mSQLiteDatabase;
    int albumId;//相册I

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_album);
        //获取intent
        Intent intent = getIntent();
        //方式二：
        Bundle bundle = intent.getExtras();
        albumId = bundle.getInt("albumId");
 //       Toast.makeText(ShowAlbumActivity.this, "ShowAlbumActivity接收Id" + albumId, Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this);
        getData();
//            mContext = this;
//        recyclerView = (RecyclerView) findViewById(R.id.photo_list);
//        recyclerView.setHasFixedSize(true);
//        final MyRecyclerPhotoAdapter adapter;
//        recyclerView.setAdapter(adapter = new MyRecyclerPhotoAdapter(createMockList(), R.layout.showphoto));
//        showPhoto();
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Photo>() {
//            @Override
//            public void onItemClick(View view, Photo mPhotos) {
//                // adapter.remove(mPhotos);
//                jumpP(view);
//            }
//        });
            setAlbumUI();

    }

    public void setAlbumUI() {

        recyclerView = (RecyclerView) findViewById(R.id.photo_list);
        recyclerView.setHasFixedSize(true);

        adapter = new MyRecyclerPhotoAdapter(lass, R.layout.showphoto);
        recyclerView.setAdapter(adapter);
        showPhoto();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //获取intent
        Intent intent = getIntent();

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Album>() {
            @Override
            public void onItemClick(View view, Album mAlbum) {
                // adapter.remove(mAlbums);
                jumpP(view);
            }
        });
    }


    public void showPhoto() {
        if (isBigShow == true) {
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(linearLayoutManager);

        } else {
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(linearLayoutManager);

        }

    }

//    private List<Photo> createMockList() {
//        List<Photo> mPhotos = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            mPhotos.add(new Photo(i, MOCK_URL + (i % 10 + 1)));
//        }
//        return mPhotos;
//    }


    @OnClick({R.id.back2, R.id.album_function2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back2:
                jumpT(view);
                break;
            case R.id.album_function2:
       //         Toast.makeText(ShowAlbumActivity.this, "输出点击showPopupWindow：", Toast.LENGTH_SHORT).show();
                showPopupWindow(view);
                break;
        }
    }

    private void jumpT(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(ShowAlbumActivity.this, TextActivity.class);
        startActivity(intent);
    }

    private void jumpU(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(ShowAlbumActivity.this, UploadPhotoActivity.class);
        int upAlbumId = albumId;

        //方式二：
        Bundle bundle = new Bundle();
        bundle.putInt("albumId", upAlbumId);
        intent.putExtras(bundle);
    //    Toast.makeText(ShowAlbumActivity.this, "ShowAlbumActivity传递Id:" + upAlbumId, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

//    private void jumpM() {
//        //显式跳转：跳转到jumpb
//        Intent intent = new Intent(ShowAlbumActivity.this, ManagePhotoActivity.class);
//        int upAlbumId = albumId;
//
//        //方式二：
//        Bundle bundle = new Bundle();
//        bundle.putInt("albumId", upAlbumId);
//        intent.putExtras(bundle);
//    //    Toast.makeText(ShowAlbumActivity.this, "ShowAlbumActivity传递Id:" + upAlbumId, Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//    }

    private void jumpP(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(ShowAlbumActivity.this, LeafletPhotoShowActivity.class);
        startActivity(intent);
    }

    private void jumpN(View contentView) {
        //显式跳转：跳转到jumpb


        Intent intent = new Intent(ShowAlbumActivity.this, NewAlbumActivity.class);
        startActivity(intent);
    }

    private void showPopupWindow(View view) {
        //自定义布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(R.layout.album_popupwindow, null);
        //设置按钮的点击事件
        editButton = (Button) contentView.findViewById(R.id.edit_album);
        manageButton = (Button) contentView.findViewById(R.id.edit_manage_photo);
        bigButton = (Button) contentView.findViewById(R.id.edit_big_photo);
        smallButton = (Button) contentView.findViewById(R.id.edit__small_photo);
        uploadButton = (Button) contentView.findViewById(R.id.edit__upload_photos);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contentView) {
                Log.e(TAG, "你点击的是：编辑相册信息");
                     jumpN(contentView);


            }
        });
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contentView) {
                Log.e(TAG, "你点击的是：管理照片");
//                 jumpM();

            }
        });
        bigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contentView) {
                Log.e(TAG, "你点击的是：大图浏览");
                isBigShow = true;
                showPhoto();


            }
        });

        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contentView) {
                Log.e(TAG, "你点击的是：小图浏览");
                isBigShow = false;
                showPhoto();


            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contentView) {
                Log.e(TAG, "你点击的是：上传图片");
                jumpU(contentView);

            }
        });
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e(TAG, "-->onTouch");
                return false;
                //这里如果返回true的话，touch事件被拦截
                //拦截后popupWindow的OnTouchEvent不被调用，这样点击外部区域无法dismiss；
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.album_background));
        //设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }


    public void getData() {
//        lass.clear();
//        String photo_id = "0";
//        SQLiteDatabase mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
//        String CREATE_TABLE = "select * from data";
//        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
//        while (cur.moveToNext()) {
//            photo_id = cur.getString(1);
//        }
       int  albumI= albumId;
//        Toast.makeText(ShowAlbumActivity.this, "输出结果：" +albumI, Toast.LENGTH_SHORT).show();

        MyApplication myApplication = new MyApplication();
        String url = myApplication.getUrl();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag", "18");
//        Toast.makeText(ShowAlbumActivity.this, "输出"+albumId, Toast.LENGTH_SHORT).show();
        params.addBodyParameter("photo_id", String.valueOf(albumId));
     //  params.addBodyParameter("photo_id", String.valueOf(24));


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //         Toast.makeText(TextActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Type type = new TypeToken<ArrayList<Album>>() {
                }.getType();
                List<Album> lassss = gson.fromJson(result, type);
                for(Album lass:lassss){

                        String a=lass.getUrl();


                    Log.e("ShowAlbumActivity","输出:"+a);

                }
                lass.addAll(lassss);
                adapter = new MyRecyclerPhotoAdapter(lass, R.layout.showphoto);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                Toast.makeText(ShowAlbumActivity.this, "ShowAlbumActivity输出结果" + lassss, Toast.LENGTH_SHORT).show();

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

}
