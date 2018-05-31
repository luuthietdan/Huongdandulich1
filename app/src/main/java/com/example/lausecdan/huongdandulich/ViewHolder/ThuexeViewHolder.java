package com.example.lausecdan.huongdandulich.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Interface.ItemClickListener;
import com.example.lausecdan.huongdandulich.R;

public class ThuexeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtthuexe;
    public ImageView imageView;

    private ItemClickListener itemClickListener;
    public ThuexeViewHolder(View itemView) {
        super(itemView);
        txtthuexe=(TextView)itemView.findViewById(R.id.thuexe_name);
        imageView=(ImageView)itemView.findViewById(R.id.thuexe_image);
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
