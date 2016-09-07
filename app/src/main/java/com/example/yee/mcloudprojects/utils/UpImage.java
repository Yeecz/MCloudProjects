package com.example.yee.mcloudprojects.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caixuecheng on 2016/8/18.
 */
public class UpImage {

    public static final int UPCOMPLEWHAT = 200;
    UploadManager uploadManager;
    List<String> imagenames = new ArrayList<>();
    List<String> files = new ArrayList<>();
    String username;
    Handler handler;
    int progress = 1;


    public UpImage(){}


    //主线程hanlder，图片的地址，上传用户的id
    public UpImage(Handler handler,List<String> files,String username){
        this.handler = handler;
        this.files = files;
        this.username = username;
    }

    public List<String> getImagenames() {
        return imagenames;
    }

    public void upImage(String token) {
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        Log.i("UpImage", "UpImage->upImage");

//        File data = url;
//        File data = new File("/mnt/sdcard/login_bg2.jpg");//<File对象、或 文件路径、或 字节数组>
//        String key = filename; //<指定七牛服务上的文件名，或 null>;
        if (token != null) {
            Log.i("UpImage", "upImage:开始");
            Log.i("UpImage", "file.size:" + files.size());
            for (int i = 0; i < files.size(); i++) {
                File data = new File(files.get(i));
                Log.i("UpImage", "data:" + files.get(i));
                String key = createImageName(getPreFix(data),i,username);
                Log.i("UpImage", "key:" + key);
                uploadManager.put(data, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                //res包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                upComplete(info);
                                progress++;
                            }
                        }, null);

                imagenames.add(key);
            }

        } else {
            Log.i("UpImage", "error");
        }


    }

    public String getPreFix(File file) {
//        File f =new File("TileTest.doc");
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Log.i("UpImage", "UpImage->get");
        return prefix;
    }

    public String createImageName(String imgPreFix,int p,String username) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String imgp = String.valueOf(p);
        String key = df.format(new Date());
        String keyheader = "dymaic";;
        key = key.trim();
        if (key != null && !"".equals(key)) {
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) >= 48 && key.charAt(i) <= 57) {
                    keyheader += key.charAt(i);
                }
            }
        }
        return keyheader + username + imgp + "." +  imgPreFix;
    }

    public void initQiniu() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(null)  // recorder分片上传时，已上传片记录器。默认null
//              .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);

        Log.i("UpImage", "UpImage->initQiniu");
    }

    public void startUpLoadImages(){
        initQiniu();
        getToken();//<从服务端SDK获取>;
    }

    public void upComplete(ResponseInfo info){
        if (info.isOK()&&progress == files.size()){
            handler.sendEmptyMessage(UPCOMPLEWHAT);
        }
    }

    public void getToken() {
        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("flag", "5");
        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    String token = result;
                    Log.i("UpImage", "onSuccess:" + token);
                    upImage(token);

                } else {
                    getToken();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                getToken();
                Log.e("erorr", ex.getMessage());
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                Log.i("onCancelled", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("onFinished", "onFinished");
            }
        });
    }

}
