package com.example.beautyshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_menu,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.tv_produk_title.setText(mData.get(position).getTitle());
        holder.tv_harga_id.setText(mData.get(position).getHarga());
        holder.img_produk_thumbnail.setImageResource(mData.get(position).getThumbnail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProdukActivity.class);
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Harga",mData.get(position).getHarga());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                mContext.startActivity(intent);
    }

        });
    }

    @Override
    public int getItemCount() { return mData.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_produk_title;
        TextView tv_harga_id;
        TextView tv_deskripsi;
        ImageView img_produk_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_produk_title = (TextView) itemView.findViewById(R.id.produk_title_id);
            tv_harga_id = (TextView) itemView.findViewById(R.id.produk_harga_id);
            tv_deskripsi = (TextView) itemView.findViewById(R.id.deskripsi);
            img_produk_thumbnail = (ImageView) itemView.findViewById(R.id.produk_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
