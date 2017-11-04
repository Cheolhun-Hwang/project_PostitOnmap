package com.hchooney.qewqs.sns_version_170801.AnyItems;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.Log;

/**
 * Created by qewqs on 2017-08-03.
 */

public class MyDialogFragment {
    private final static String TAG = "MyDialogFragment";
    private ProgressDialog progressDialog;

    public MyDialogFragment(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("해당 지역을 찾고 있습니다.");
        progressDialog.setMessage("빠르게 확인중...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Log.d(TAG, "My Dialog init Complete");
    }

    public void setTitleToMyDialog(String title){
        progressDialog.setTitle(title);
        Log.d(TAG, "My Dialog set title Complete");
    }

    public void setMessageToMyDialog(String mes){
        progressDialog.setMessage(mes);
        Log.d(TAG, "My Dialog set Mes Complete");
    }

    public void StartDialog(){
        progressDialog.show();
        Log.d(TAG, "My Dialog Start Complete");
    }

    public void EndDialog(){
        progressDialog.dismiss();
        Log.d(TAG, "My Dialog End Complete");
    }
}
