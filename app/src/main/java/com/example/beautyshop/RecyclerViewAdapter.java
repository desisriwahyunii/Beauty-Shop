package com.example.beautyshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Produk> mData;

    public RecyclerViewAdapter(Context mContext, List<Produk> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInfater = LayoutInflater.from(mContext);
        view = mInfater.inflate(R.layout.item_main,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_produk_title.setText(mData.get(position).getTitle());
        holder.img_produk_thumbnail.setImageResource(mData.get(position).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_produk_title;
        ImageView img_produk_thumbnail;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_produk_title = (TextView) itemView.findViewById(R.id._produk_title_id);
            img_produk_thumbnail = (ImageView) itemView.findViewById(R.id.produk_img_id);
        }
    }
}
