package com.renjibo.renjibo20170227;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.renjibo.renjibo20170227.model.PersonBean;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主activitry
 * 任继波
 * 2017 02 27
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Gson gson;
    private Button buttonLocation;
    private Button buttonRemove;
    private RecyclerView recyclerView;
    private List<PersonBean.DataBean> dataArray=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        initView();
        getJson("http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=10&gender=2&ts=1871746850&page=1");
    }
    public void initView(){
        buttonLocation = (Button) findViewById(R.id.buttonLocation);
        buttonRemove = (Button) findViewById(R.id.buttonRemove);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linea=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linea);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));

        buttonLocation.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);
    }
    //请求数据
    public void getJson(String sql){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url(sql)
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);
//请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Log.d("zzz","json:"+string);
                PersonBean personBean = gson.fromJson(string, PersonBean.class);
                Log.d("zzz","bean:"+personBean.toString());
                List<PersonBean.DataBean> data = personBean.getData();
                dataArray.addAll(data);


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //定位
            case R.id.buttonLocation:
                startActivity(new Intent(this,Main2Activity.class));
                break;
            //清除缓存
            case R.id.buttonRemove:
                Toast.makeText(this,"清除成功",Toast.LENGTH_SHORT).show();
                buttonRemove.setText("缓存为"+(0));
                MyAdapter myAdapte=new MyAdapter(dataArray);
                recyclerView.setAdapter(myAdapte);
                buttonRemove.setText("缓存为"+(1024*13));
                break;
        }
    }
}
