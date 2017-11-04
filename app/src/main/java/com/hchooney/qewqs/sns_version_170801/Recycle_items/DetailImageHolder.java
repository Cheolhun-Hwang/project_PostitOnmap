package com.hchooney.qewqs.sns_version_170801.Recycle_items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hchooney.qewqs.sns_version_170801.R;

/**
 * Created by qewqs on 2017-08-08.
 */

public class DetailImageHolder extends RecyclerView.ViewHolder{
    public ImageView PresentationofImageview;

    public DetailImageHolder(View view){
        super(view);
        init(view);
    }

    private void init(View view){
        PresentationofImageview = (ImageView) view.findViewById(R.id.Detail_recycle_imageview);
    }
}
