package com.example.yee.mcloudprojects.activity;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/11.
 */
public class NetUtil {


    //使用get方式访问服务器

    public static void getDataByGet(final  String urlStr, final Handler handler){

        //创建子线程：new Thread().start(); Runnable, callback

        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpURLConnection httpURLConnection=null;
                InputStream is=null;

                try {
                    URL url=new URL(urlStr);
                    //建立和服务端连接
                    httpURLConnection=(HttpURLConnection) url.openConnection();
                    //设置请求方式：get
                    httpURLConnection.setRequestMethod("GET");

                    //设置连接超时时间
                    //Log.i("NetUtil","NetUtil:run:结果码"+httpURLConnection.getResponseCode());

                    //服务端返回成功：200
                    if(httpURLConnection.getResponseCode()==200){
                        //获取服务端返回数据
                        is=httpURLConnection.getInputStream();

                        //is转换成字符串 BufferedReader
                        String result= convertIsToString(is);
                        //通过handler,发送消息；将数据传给主线程
                        //创建消息对象
                        Message message=Message.obtain();//从消息吃中找到一个消息对象

                        //设置消息的标识
                        message.what=1;
                        //设置消息携带的参数
                        message.obj=result;

                        //通过handler，发送消息
                        handler.sendMessage(message);

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    httpURLConnection.disconnect();
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();




    }



    //url,参数的map，handler

    public static void getDataByPost(final String urlStr, final Map<String,Object> map, final Handler handler){

        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpURLConnection urlConnection = null;
                OutputStream os = null;
                InputStream is = null;
                //post方式访问网络
                try {
                    URL url=new URL(urlStr);
                    urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");//设置请求方式：post


                    //取出map中数据，放在string: key1=value1&key2=value2&
                    Set<Map.Entry<String,Object>> set=map.entrySet();

                    StringBuilder stringBuilder=new StringBuilder();

                    //遍历set集合
                    for(Map.Entry<String,Object> entry:set){
                        String key=  entry.getKey();
                        Object value=entry.getValue();
                        stringBuilder.append(key+"="+value+"&");

                    }

                    //转换成字符串
                    String s= stringBuilder.toString();

                    //开始位置，结束位置（不包含）：去掉最后一个”&“
                    String params=s.substring(0,s.length()-1);



                    //设置post请求参数
                    os= urlConnection.getOutputStream();
                    //参数格式： key1=value1&key2=value2
                    os.write( params.getBytes());


                    if(urlConnection.getResponseCode()==200){
                        is=  urlConnection.getInputStream();
                        String result= convertIsToString(is);

                        //创建消息
                        Message msg= Message.obtain();
                        msg.what=1;
                        msg.obj=result;


                        //handler发送消息
                        handler.sendMessage(msg);

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    urlConnection.disconnect();
                    try {

                        os.close();
                        is.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }


    //将Inputstream转换成字符串
    public static String convertIsToString(InputStream is){
        //创建字节数组，接收内存中的数据
        ByteArrayOutputStream baos=new ByteArrayOutputStream();

        int b;
        try {
            while((b=is.read())!=-1){

                //接收字节
                baos.write(b);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();

    }


}



