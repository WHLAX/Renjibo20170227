package com.renjibo.renjibo20170227;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renjibo.renjibo20170227.model.PersonBean;

import org.xutils.x;

import java.util.List;

/**
 * 适配器
 * 任继波
 * Created by Administrator on 2017/2/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<PersonBean.DataBean> dataArray;

    public MyAdapter(List<PersonBean.DataBean> dataArray) {
        this.dataArray = dataArray;
        Log.d("zzz","da:"+dataArray.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonBean.DataBean dataBean = dataArray.get(position);
        Log.d("zzz","da"+dataBean.toString());
        holder.textView_age.setText(dataBean.getUserAge()+"");
        holder.textView_introduction.setText(dataBean.getIntroduction()+"");
        holder.textView_occupation.setText(dataBean.getOccupation());
        holder.textView_title.setText(dataBean.getTitle());
        x.image().bind(holder.imageView,dataBean.getUserImg());
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_age;
        private TextView textView_introduction;
        private TextView textView_occupation;
        private TextView textView_title;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_age = (TextView) itemView.findViewById(R.id.textView_age);
            textView_introduction = (TextView) itemView.findViewById(R.id.textView_introduction);
            textView_occupation = (TextView) itemView.findViewById(R.id.textView_occupation);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            imageView = (ImageView) itemView.findViewById(R.id.recy_imageView);
        }
    }
}
