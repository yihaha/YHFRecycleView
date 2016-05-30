package com.yibh.yhfrecycleview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yibh.yhfrecycleview.R;
import com.yibh.yhfrecycleview.model.AllData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y on 2016/5/30.
 */
public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ReHolder> {

    private List<AllData.GankData> gankDatas=new ArrayList<>();

    public ReAdapter(List<AllData.GankData> gankDatas) {
        this.gankDatas.addAll(gankDatas);
    }

    public void setRefre(List<AllData.GankData> list){
        this.gankDatas.clear();
        this.gankDatas.addAll(list);
//        notifyDataSetChanged();
    }

    @Override
    public ReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizi_item, parent, false);
        return new ReHolder(view);
    }

    @Override
    public void onBindViewHolder(ReHolder holder, int position) {
        AllData.GankData gankData = gankDatas.get(position);
        Glide.with(holder.imageView.getContext())
                .load(gankData.url)
                .dontAnimate()
                .into(holder.imageView);

        holder.title.setText(gankData.desc);
    }

    @Override
    public int getItemCount() {
        return gankDatas.size();
    }

    public class ReHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;

        public ReHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }
}
