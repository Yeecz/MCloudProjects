package com.example.yee.mcloudprojects.BaiduLocation;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yee on 2016/8/15.
 */
public class GetLocData {
    private static GetLocData instance = null;
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private PoiSearch mPoiSearch = PoiSearch.newInstance();
    private OnGetPoiSearchResultListener poiListener;
    private boolean isFirstLoc = true; // 是否首次定位
    Handler handler;
    private int radius = 1000;
    private int loadIndex = 0;
    private String city;
    private LatLng center;
    private List poilist = new ArrayList();
    private List poiinfolist = new ArrayList();
    LatLng southwest;
    LatLng northeast;
    LatLngBounds searchbound;

    public static GetLocData getInstance(){
        if (instance==null){
            instance = new GetLocData();
        }
        return instance;
    }

    public void startLoc(Context context, Handler handler) {
        // 定位初始化
        this.handler = handler;
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(0);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    //设置定位监听器
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            poilist.clear();
            List list = location.getPoiList();
            Log.i("MyLocationListenner", "定位测试onReceiveLocation" +center +  "," + city + "," + list.size());
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            if (isFirstLoc) {
                isFirstLoc = false;
                center = new LatLng(location.getLatitude(),location.getLongitude());
                southwest = new LatLng(location.getLatitude(),location.getLongitude());
                northeast = new LatLng(location.getLatitude()+0.05,location.getLongitude()+0.05);
                city = location.getCity();
                Log.i("MyLocationListenner", "onReceiveLocation:" + center + "," + city);
            }
            if (city!=null&&center!=null&&list!=null){

                poilist.addAll(list);
                handler.sendEmptyMessage(1);
            }
        }

    }


    //区域检索
    public void searchBoundByProcess(String loc){
        poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                List<PoiInfo> poiInfoList = poiResult.getAllPoi();
                poiinfolist.clear();
                if (poiInfoList!=null){
                    poiinfolist.addAll(poiInfoList);
                    handler.sendEmptyMessage(2);
                }

                //Log.i("Main2Activity", "Main2Activity->onGetPoiResult" + poiInfoList.get(0).name);

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获取Place详情页检索结果
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };

        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        PoiBoundSearchOption searchOption = new PoiBoundSearchOption();
        searchbound = new LatLngBounds.Builder().include(southwest).include(northeast).build();
        Log.i("GetLocData", "searchBoundByProcess:" + searchbound.toString());
        searchOption.bound(searchbound).keyword(loc).pageNum(loadIndex).pageCapacity(15);
        Log.i("GetLocData", "searchBoundByProcess:" + loc);
        mPoiSearch.searchInBound(searchOption);
    }

    //城市中搜索，关键字检索
    /*public void searchInCityByProcess(String loc){
        poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                List<PoiInfo> poiInfoList = poiResult.getAllPoi();
                if (poiInfoList!=null){
                    poiinfolist.addAll(poiInfoList);
                    handler.sendEmptyMessage(2);
                }

                //Log.i("Main2Activity", "Main2Activity->onGetPoiResult" + poiInfoList.get(0).name);

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获取Place详情页检索结果
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        PoiCitySearchOption searchOption = new PoiCitySearchOption().city(city).keyword(loc).pageNum(loadIndex);
        mPoiSearch.searchInCity(searchOption);
    }*/

    /*public void goSearchNearBy(String loc){
        Log.i("GetLocData", "goSearchNearBy" + " 点击获取");
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword(loc)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius)
                .pageNum(loadIndex);
        mPoiSearch.searchNearby(nearbySearchOption);

        poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                        //获取POI检索结果
                List<PoiInfo> poiInfoList = poiResult.getAllPoi();
                if (poiInfoList!=null){
                    poiinfolist.addAll(poiInfoList);
                    handler.sendEmptyMessage(2);
                }

                //Log.i("Main2Activity", "Main2Activity->onGetPoiResult" + poiInfoList.get(0).name);

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获取Place详情页检索结果
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

    }*/

    /*public void searchButtonProcess(String lockey) {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city).keyword(lockey).pageNum(loadIndex));
    }*/

    //翻页
    public void goToNextPage(String loc) {
        loadIndex++;
        searchBoundByProcess(loc);
    }



    public void stopLoc(){
        if (mLocClient.isStarted()&&mLocClient!=null){
            mLocClient.stop();
        }
        if (mPoiSearch!=null){
            mPoiSearch.destroy();
        }
    }

    public String getCity() {
        return city;
    }

    public LatLng getCenter() {
        return center;
    }

    public List getPoilist() {
        return poilist;
    }

    public List getPoiinfolist() {
        return poiinfolist;
    }
}
