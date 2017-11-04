package com.hchooney.qewqs.sns_version_170801.Post_Items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hchooney.qewqs.sns_version_170801.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by qewqs on 2017-07-14.
 */

public class PostItAdapter extends BaseAdapter implements View.OnClickListener{
    private Context myContext;
    private int Layout;
    private ArrayList<ListInfomationItem> listInfomationItems;
    private LayoutInflater inflater;
    private ListInfomationItem item;

    private ImageView BestPhoto;

    public PostItAdapter( ) {
        this.myContext = null;
        this.Layout = 0;
        this.listInfomationItems = null;
        this.inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public PostItAdapter(Context myContext, int layout, ArrayList<ListInfomationItem> listInfomationItems) {
        this.myContext = myContext;
        this.Layout = layout;
        this.listInfomationItems = listInfomationItems;
        this.inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listInfomationItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listInfomationItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        this.item = listInfomationItems.get(i);

        if(convertView == null){
            convertView = inflater.inflate(this.Layout, viewGroup, false);
        }

        TextView UserID = (TextView) convertView.findViewById(R.id.PostItem_UserName);
        TextView WriteDate = (TextView) convertView.findViewById(R.id.PostItem_WriteDate);
        TextView LikeRate = (TextView) convertView.findViewById(R.id.PostItem_LikeRate);
        TextView HashTag =(TextView) convertView.findViewById(R.id.PostItem_HashTag);
        TextView Context = (TextView) convertView.findViewById(R.id.PostItem_Context);
        TextView Title = (TextView) convertView.findViewById(R.id.PostItem_Title);
        LinearLayout Background = (LinearLayout)convertView.findViewById(R.id.PostItem_Background);
        Button Location = (Button) convertView.findViewById(R.id.PostITem_LocationBTN);
        BestPhoto = (ImageView) convertView.findViewById(R.id.PostItem_ImageView);

        UserID.setText("ID : " + item.getWho());
        WriteDate.setText("등록일 : " + item.getWhen());

        double temp_Rate = item.getLikeRate();

        if(temp_Rate > 4.5 && temp_Rate <= 5.0){
            LikeRate.setText("Perfect");
            LikeRate.setTextColor(Color.parseColor("#D500F9")); //밝은 보라색
        }else if(temp_Rate > 4.0 && temp_Rate <= 4.5){
            LikeRate.setText("Excellent");
            LikeRate.setTextColor(Color.parseColor("#00C853")); //짙은 녹색
        }else if(temp_Rate > 3.5 && temp_Rate <= 4.0){
            LikeRate.setText("Good");
            LikeRate.setTextColor(Color.parseColor("#0D47A1")); //짙은 파란색
        }else if(temp_Rate > 3.0 && temp_Rate <= 3.5){
            LikeRate.setText("Nice");
            LikeRate.setTextColor(Color.parseColor("#F57F17")); //주황색
        }else if(temp_Rate > 2.5 && temp_Rate <= 3.0){
            LikeRate.setText("Not Bad");
            LikeRate.setTextColor(Color.parseColor("#EC407A")); //핑크색
        }else if(temp_Rate > 1.5 && temp_Rate <= 2.5){
            LikeRate.setText("Bad");
            LikeRate.setTextColor(Color.parseColor("#ff0000")); //빨강색
        }else{
            LikeRate.setText("Oh..no..");
            LikeRate.setTextColor(Color.parseColor("#000000")); //검은색
        }

        Title.setText(item.getWhere());

        String temp = "";
        int count = item.getHashTag().size();
        for(int num = 0 ; num < count ; num ++){
            temp = temp + item.getHashTag().get(num);
            if(num < count - 1 ){
                temp += ", ";
            }
        }
        HashTag.setText(temp);

        Context.setText(item.getContext());
        String tempPostitName =  item.getPostItDesign();
        Log.d("PostAdater", "getPostDesign : " + tempPostitName);
        if(tempPostitName.equals("postit_grade_one_img01")) {
            //backgroundIMG.setBackgroundResource(R.drawable.postit_grade_one_img01);
            Background.setBackgroundResource(R.drawable.postit_grade_one_img01);
        }else if(tempPostitName.equals("postit_grade_one_img02")){
            //backgroundIMG.setBackgroundResource(R.drawable.postit_grade_one_img02);
            Background.setBackgroundResource(R.drawable.postit_grade_one_img02   );
        }
        //background_postit.setBackground(item.getPostItDesign());

        Log.d("PostItAdator ImageURl", "imageUrl : " + item.getPhotoURL());

        if(item.getPhotoURL() == null || item.getPhotoURL().equals("NONE")){
            BestPhoto.setVisibility(View.GONE);
        }else{

            Log.d("PostItAdator ImageURl", "Action Doing~~~~");
            /*FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://startupproject-5b5ca.appspot.com");
            StorageReference imagesRef = storageRef.child(item.getWho() + "__" + item.getLat() + "__" + item.getLon());
            final long ONE_MEGABYTE = 800 * 800;
            imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Data for "images/island.jpg" is returns, use this as needed

                    Log.d("Test Photo", "onSuccess");
                    if(BestPhoto.getDrawable() == null){
                        Log.d("Test Photo", "getDrawable : " + null);
                        Bitmap bitmap = BitmapFactory.decodeByteArray( bytes, 0, bytes.length ) ;
                        BestPhoto.setImageBitmap(bitmap);
                    }else{
                        Log.d("Test Photo", "getDrawable : " + true);
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray( bytes, 0, bytes.length ) ;
                        BestPhoto.setImageBitmap(bitmap2);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });*/

            new Thread(new Runnable() {
                public void run() {
                    Bitmap tempBitmap = null;
                    try {
                       tempBitmap = getBitmap(item.getPhotoURL());
                    }catch(Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(tempBitmap!=null) {
                            final Bitmap finalTempBitmap = tempBitmap;
                            runOnUiThread(new Runnable() {
                                @SuppressLint("NewApi")
                                public void run() {
                                    BestPhoto.setImageBitmap(finalTempBitmap);
                                }
                            });
                        }
                    }
                }
            }).start();

            //Picasso.with(myContext).load(item.getPhotoURL()).into(BestPhoto);



        }

        Location.setText(item.getDetailCity());
        Location.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View view) {
        double lon = item.getLon();
        double lat = item.getLat();
        Toast.makeText(myContext, "위도 / 경도 : " + lon + " / " + lat, Toast.LENGTH_SHORT).show();
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

    public class ViewHolder{
        public TextView UserID;
        public TextView WriteDate;
        public TextView LikeRate;
        public TextView HashTag;
        public TextView Context;
        public TextView Title;
        public LinearLayout Background;
        public Button Location;
        public ImageView BestPhoto;
    }


}
