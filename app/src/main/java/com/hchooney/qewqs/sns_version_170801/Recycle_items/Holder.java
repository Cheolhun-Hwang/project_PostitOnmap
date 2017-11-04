package com.hchooney.qewqs.sns_version_170801.Recycle_items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hchooney.qewqs.sns_version_170801.R;

/**
 * Created by qewqs on 2017-08-07.
 */

public class Holder extends RecyclerView.ViewHolder{
    public ImageView PresentationofImageview;
    public TextView Title;
    public TextView Addr;
    public TextView ModifyDay;

    public Holder(View view){
        super(view);
        init(view);
    }

    private void init(View view){
        PresentationofImageview = (ImageView) view.findViewById(R.id.info_recycleview_image);
        Title = (TextView) view.findViewById(R.id.info_recycleview_title);
        Addr = (TextView) view.findViewById(R.id.info_recycleview_addr);
        ModifyDay = (TextView) view.findViewById(R.id.info_recycleview_modify);
    }
}