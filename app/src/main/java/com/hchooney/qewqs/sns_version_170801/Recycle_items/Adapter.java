package com.hchooney.qewqs.sns_version_170801.Recycle_items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.hchooney.qewqs.sns_version_170801.AnyItems.GetUrlImage;
import com.hchooney.qewqs.sns_version_170801.DetailInfoActivity;
import com.hchooney.qewqs.sns_version_170801.Geo_Items.GeoMarkItem;
import com.hchooney.qewqs.sns_version_170801.MainActivity;
import com.hchooney.qewqs.sns_version_170801.MapFragment;
import com.hchooney.qewqs.sns_version_170801.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by qewqs on 2017-08-07.
 */

public class Adapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener, View.OnScrollChangeListener{
    private ArrayList<GeoMarkItem> items;
    private Context mContext;
    private RecyclerView recycleView;

    public Adapter(ArrayList<GeoMarkItem> it, RecyclerView recyclerView){
        this.items = it;
        this.recycleView = recyclerView;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.info_item, parent, false);
        view.setOnClickListener(this);

        mContext = view.getContext();
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        GeoMarkItem item = items.get(position);

        holder.Title.setText((position+1) +". " + item.getTitle());

        if(item.getAddr2().equals("None")){
            holder.Addr.setText(item.getAddr1());
        }else{
            holder.Addr.setText(item.getAddr1()+ " " + item.getAddr2());
        }

        holder.ModifyDay.setText(item.getModifiedtime());

        //GetUrlImage task = new GetUrlImage(holder.PresentationofImageview);
        if(!(item.getFirstImage().equals("None"))){
            //task.execute(new String[] { item.getFirstImage()});
            Log.d("Adapter", "Item URL : " + item.getFirstImage());
            Picasso.with(mContext).load(item.getFirstImage()).into(holder.PresentationofImageview);
            //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View view) {
        int itemId = this.recycleView.getChildAdapterPosition(view);
        Log.d("Recycle Item position", "position : " + itemId);

        Log.d("Recycle Item title", "position : " + items.get(itemId).getTitle());

        LatLng location = new LatLng(items.get(itemId).getMapY(), items.get(itemId).getMapX());
        Intent intent = new Intent(mContext, DetailInfoActivity.class);
        intent.putExtra("information", items.get(itemId));
        mContext.startActivity(intent);

    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        Log.d("Recycle Item position", "position : " + i);
        Log.d("Recycle Item title", "position : " + items.get(i).getTitle());

        Log.d("Recycle Item position", "position : " + i1);
        Log.d("Recycle Item title", "position : " + items.get(i1).getTitle());

        Log.d("Recycle Item position", "position : " + i2);
        Log.d("Recycle Item title", "position : " + items.get(i2).getTitle());

        Log.d("Recycle Item position", "position : " + i3);
        Log.d("Recycle Item title", "position : " + items.get(i3).getTitle());
    }
}
