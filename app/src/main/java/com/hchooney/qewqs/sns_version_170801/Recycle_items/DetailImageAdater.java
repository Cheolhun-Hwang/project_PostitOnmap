package com.hchooney.qewqs.sns_version_170801.Recycle_items;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hchooney.qewqs.sns_version_170801.DetailInfoActivity;
import com.hchooney.qewqs.sns_version_170801.Geo_Items.GeoMarkItem;
import com.hchooney.qewqs.sns_version_170801.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by qewqs on 2017-08-08.
 */

public class DetailImageAdater extends RecyclerView.Adapter<DetailImageHolder> {
    private ArrayList<String> items;
    private Context mContext;

    public DetailImageAdater(ArrayList<String> it){
        this.items = it;
    }

    @Override
    public DetailImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.detail_recycle_item, parent, false);

        mContext = view.getContext();
        return new DetailImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailImageHolder holder, int position) {
        String item = items.get(position);

        if(!(item.equals("None"))){
            //task.execute(new String[] { item.getFirstImage()});
            Log.d("Adapter", "Item URL : " + item);
            Picasso.with(mContext).load(item).into(holder.PresentationofImageview);
            //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}