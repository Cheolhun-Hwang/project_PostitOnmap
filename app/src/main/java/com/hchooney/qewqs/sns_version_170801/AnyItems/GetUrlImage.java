package com.hchooney.qewqs.sns_version_170801.AnyItems;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by qewqs on 2017-08-07.
 */

public class GetUrlImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageview;

    public GetUrlImage(ImageView imageview){
        this.imageview = imageview;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap map = null;
        for (String url : urls) {
            map = downloadImage(url);
        }
        return map;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(Bitmap result) {


        imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            /*MATRIX 원본 크기 그대로 보여줌 (왼쪽상단 정렬)
            CENTER 원본 크기 그대로 보여줌 (가운데 정렬)
            CENTER_CROP View 영역에 공백이 있으면 채워서 보여줌(비율유지)
            CENTER_INSIDE View 영역을 벗어나면 맞춰서 보여줌(비율유지)
            FIT_START View 영역에 맞게 보여줌 (왼쪽상단 정렬, 비율유지)
            FIT_CENTER View 영역에 맞게 보여줌 (가운데 정렬, 비율유지)
            FIT_END View 영역에 맞게 보여줌 (왼쪽하단 정렬, 비율유지)
            FIT_XY View 영역을 가득 채워서 보여줌(비율유지 안함)*/
        imageview.setImageBitmap(result);

    }

    // Creates Bitmap from InputStream and returns it
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }


}