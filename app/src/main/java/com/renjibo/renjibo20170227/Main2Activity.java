package com.renjibo.renjibo20170227;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 地图显示
 * 任继波
 * 2017 02 27
 */
public class Main2Activity extends AppCompatActivity {


    //经纬度
    private double longitude;
    private double latitude;
    private MyLocationListener myLitenner = new MyLocationListener();
    //地图控件
    //百度地图对象
    private BaiduMap baiduMap=null;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker marker;
    //是否首次定位
    boolean isFirstLoc=true;
    private LocationClient locationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
        mMapView = (MapView) findViewById(R.id.mapView);

        baiduMap=mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //实例化 LocationClient类
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(myLitenner);//注册监听函数
        this.setLocationOption();
        initData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    public void initData(){
        locationClient.start();//开始定位
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为一般地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
//        baiduMap.setTrafficEnabled(true);//开启交通图
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd0911");//返回的定位结果是百度经纬度，默认值是gcj02
        option.setScanSpan(5000);//设置发起定位请求的时间间隔为5000ms
        option.setIsNeedAddress(true);//返回的定位结果饱饭地址信息
        option.setNeedDeviceDirect(true);// 返回的定位信息包含手机的机头方向
        locationClient.setLocOption(option);
    }
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            longitude=bdLocation.getLongitude();
            latitude=bdLocation.getLatitude();
            boolean isLocateFailed=false;//定位是否成功
            //Map View 销毁后不再处理新接受的位置
            if(bdLocation==null||mMapView==null){
                return ;
            }
            MyLocationData locationData=new MyLocationData.Builder()
                    //此处设置开发者获得到的方向信息   顺时针 度数
                    .accuracy(bdLocation.getRadius())
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locationData);
            //设置定位数据
            if(isFirstLoc){
                isFirstLoc=false;
                LatLng ll=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLngZoom(ll,16);
                //设置地图中心点 以及缩放级别
                baiduMap.animateMapStatus(mapStatusUpdate);
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}

