package com.example.lausecdan.huongdandulich.ViewHolder;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Interface.ItemClickListener;
import com.example.lausecdan.huongdandulich.R;

import org.w3c.dom.Text;

public class DiadiemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtTenDiaDiem;
    public ImageView imageView;

    private ItemClickListener itemClickListener;
    public DiadiemViewHolder(View itemView) {
        super(itemView);
        txtTenDiaDiem=(TextView)itemView.findViewById(R.id.diadiem_name);
        imageView=(ImageView)itemView.findViewById(R.id.diadiem_image);
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
