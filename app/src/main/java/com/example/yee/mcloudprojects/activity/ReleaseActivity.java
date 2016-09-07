package com.example.yee.mcloudprojects.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.PhotoAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Dynamic;
import com.example.yee.mcloudprojects.listener.LocRecyclerItemClickListener;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.example.yee.mcloudprojects.utils.ProgressDialogUtil;
import com.example.yee.mcloudprojects.utils.UpImage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class ReleaseActivity extends AppCompatActivity {

    enum RequestCode{
        ImageButton(R.id.release_footer_addpic);

        final int mViewId;

        RequestCode(int viewId){
            mViewId = viewId;
        }
    }


    CircleImageView loginuser_tx;
    RelativeLayout locll;
    RecyclerView mRecyclerView;
    ImageButton mImageButton;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    List<String> imagesname = new ArrayList<>();
    PhotoAdapter photoAdapter;
    TextView loctext;
    ImageView backV;
    EditText dynamicContent;
    FloatingActionButton submit;
    public static final int REQUESTADDRCODE = 1;//获取地址返回码
    UpImage upImage;
    GetLoginUser getLoginUser = new GetLoginUser(this);
    ProgressDialog mProgressDialog;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==200){
                upUserDynamic();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        initView();
        initData();
        initAdapters();
        initListeners();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)){
            List<String> photos = null;
            if (data != null){
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }

            selectedPhotos.clear();

            if (photos != null){
                selectedPhotos.addAll(photos);

            }
            Log.i("Photo", "ReleaseActivity->onActivityResult" + photos.toString());
            photoAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUESTADDRCODE && resultCode == RESULT_OK){
            String address = data.getStringExtra("RequestAddress");
            loctext.setText(address);
            Toast.makeText(ReleaseActivity.this, address, Toast.LENGTH_SHORT).show();
            Log.i("Activity", "MainActivity->onActivityResult:" + address);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            onClick(RequestCode.values()[requestCode].mViewId);
        }else {
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        switch (permission){
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return false;

            case Manifest.permission.CAMERA:
                return false;

            default:
                return true;
        }
    }

    private void initListeners() {
        backV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dynamicContent.getWindowToken(), 0);
                finish();
            }
        });
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(RequestCode.ImageButton);
            }
        });

        mRecyclerView.addOnItemTouchListener(new LocRecyclerItemClickListener(this, new LocRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PhotoPreview.builder().setPhotos(selectedPhotos).setCurrentItem(position).start(ReleaseActivity.this);
            }
        }));

        locll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseActivity.this,ChooseLocActivity.class);
                startActivityForResult(intent,REQUESTADDRCODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dynamicContent.getText().length()<1){
                    Toast.makeText(ReleaseActivity.this, "说点什么吧~", Toast.LENGTH_SHORT).show();
                }else {
                    mProgressDialog.show();
                    if (!selectedPhotos.isEmpty()){
                        Log.i("ReleaseActivity", "ReleaseActivity->onClick");

                        upDynamicImages(selectedPhotos);
                    }else {
                        upUserDynamic();
                    }
                }



            }
        });

    }

    private void initData() {
        photoAdapter = new PhotoAdapter(selectedPhotos,this);


        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/"+getLoginUser.getDataHeadPortrait())
                .placeholder(R.drawable.avatar_default)
                .into(loginuser_tx);
    }

    private void initAdapters() {
        mRecyclerView.setAdapter(photoAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
    }

    private void initView() {
        backV = (ImageView) findViewById(R.id.release_close);
        loctext = (TextView) findViewById(R.id.release_loc_text);
        loginuser_tx = (CircleImageView) findViewById(R.id.release_user_pic);
        locll = (RelativeLayout) findViewById(R.id.release_loaction);
        mImageButton = (ImageButton) findViewById(R.id.release_footer_addpic);
        mRecyclerView = (RecyclerView) findViewById(R.id.release_pics);
        dynamicContent = (EditText) findViewById(R.id.release_content);
        submit = (FloatingActionButton) findViewById(R.id.release_footer_submit);
        mProgressDialog = ProgressDialogUtil.getProgressDialog(this,"发布中...");
    }

    private void checkPermission(RequestCode requestcode) {
        int readStoragePermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);

        boolean readStoragePermissionGranted = readStoragePermissionState != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermissionGranted = cameraPermissionState != PackageManager.PERMISSION_GRANTED;

        if (readStoragePermissionGranted || cameraPermissionGranted){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

            }else {
                String[] permissions;
                if (readStoragePermissionGranted && cameraPermissionGranted){
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

                }else {
                    permissions = new String[]{
                            readStoragePermissionGranted?Manifest.permission.READ_EXTERNAL_STORAGE:Manifest.permission.CAMERA
                    };
                }
                ActivityCompat.requestPermissions(this,permissions,requestcode.ordinal());
            }
        }else {
            onClick(requestcode.mViewId);
        }
    }

    private void onClick(int viewId) {
        switch (viewId){
            case R.id.release_footer_addpic:
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(3)
                        .start(this);
                break;
        }
    }

    public void upUserDynamic(){
        MyApplication myApplication = (MyApplication) getApplication();
        String url = myApplication.getUrl();

        int id = getLoginUser.getAccountId();
        String content = dynamicContent.getText().toString();
        String gps = loctext.getText().toString();
        Dynamic dynamic = new Dynamic();
        if (!gps.equals("显示位置")){
            dynamic.setGps(gps);
        }
        if (content!=null){
            dynamic.setText(content);
        }
        dynamic.setUser_id(id);
        dynamic.setImage_c(selectedPhotos.size());
        Gson gson = new Gson();
        String userdynamic = gson.toJson(dynamic);
        String userimages = null;
        if (!selectedPhotos.isEmpty()){
            imagesname.addAll(upImage.getImagenames());
            userimages = gson.toJson(imagesname);
        }
        Log.i("ReleaseActivity", "imagesname:" + imagesname);
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(12000);
        params.addBodyParameter("flag","64");
        params.addBodyParameter("dynamic",userdynamic);
        params.addBodyParameter("images",userimages);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")){
                    //完成提示框
                    Toast.makeText(ReleaseActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    setResult(9,intent);
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(dynamicContent.getWindowToken(), 0);
                    finish();
                }else {
                    Toast.makeText(ReleaseActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ReleaseActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mProgressDialog.cancel();
            }
        });
    }

    public void upDynamicImages(final ArrayList<String> selectedimages){
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (!selectedimages.isEmpty()){
                    upImage = new UpImage(handler,selectedimages,getLoginUser.getAccountNumber());
                    upImage.startUpLoadImages();
                }

            }
        }.start();

//        upImage.upImage(selectedimages);

        Log.i("ReleaseActivity", "ReleaseActivity->upDynamicImages");
    }


}
