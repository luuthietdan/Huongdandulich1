package com.example.lausecdan.huongdandulich.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Interface.ItemClickListener;
import com.example.lausecdan.huongdandulich.R;

public class KhachsanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtkhachsan;
    public ImageView imageView;

    private ItemClickListener itemClickListener;
    public KhachsanViewHolder(View itemView) {
        super(itemView);
        txtkhachsan=(TextView)itemView.findViewById(R.id.khachsan_name);
        imageView=(ImageView)itemView.findViewById(R.id.khachsan_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
