package com.hchooney.qewqs.sns_version_170801.Spinner_items;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hchooney.qewqs.sns_version_170801.R;

/**
 * Created by qewqs on 2017-08-07.
 */

public class Citys implements AdapterView.OnItemSelectedListener {
    public static String areaCode_meta;

    private Context mContext;
    private Spinner Filter_Localcity;

    public Citys(Context context, Spinner spinner){
        this.mContext = context;
        this.Filter_Localcity = spinner;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position){
            case 0:
                setLocalSpinner(R.array.seoul_array);
                areaCode_meta = "1";
                break;
            case 1:
                setLocalSpinner(R.array.incheon_array);
                areaCode_meta = "2";
                break;
            case 2:
                setLocalSpinner(R.array.daejeon_array);
                areaCode_meta = "3";
                break;
            case 3:
                setLocalSpinner(R.array.daegu_array);
                areaCode_meta = "4";
                break;
            case 4:
                setLocalSpinner(R.array.gwangju_array);
                areaCode_meta = "5";
                break;
            case 5:
                setLocalSpinner(R.array.busan_array);
                areaCode_meta = "6";
                break;
            case 6:
                setLocalSpinner(R.array.ulsan_array);
                areaCode_meta = "7";
                break;
            case 7:
                setLocalSpinner(R.array.sejong_array);
                areaCode_meta = "8";
                break;
            case 8:
                setLocalSpinner(R.array.gyunggi_array);
                areaCode_meta = "31";
                break;
            case 9:
                setLocalSpinner(R.array.gangwon_array);
                areaCode_meta = "32";
                break;
            case 10:
                setLocalSpinner(R.array.norchung_array);
                areaCode_meta = "33";
                break;
            case 11:
                setLocalSpinner(R.array.souchung_array);
                areaCode_meta = "34";
                break;
            case 12:
                setLocalSpinner(R.array.norgyung_array);
                areaCode_meta = "35";
                break;
            case 13:
                setLocalSpinner(R.array.sougyung_array);
                areaCode_meta = "36";
                break;
            case 14:
                setLocalSpinner(R.array.norjun_array);
                areaCode_meta = "37";
                break;
            case 15:
                setLocalSpinner(R.array.soujun_array);
                areaCode_meta = "38";
                break;
            case 16:
                setLocalSpinner(R.array.jeju_array);
                areaCode_meta = "39";
                break;
            default:
                Log.d("Filter_Spinner", "Spinner Error Check!");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setLocalSpinner(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(mContext, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_Localcity.setAdapter(fAdapter);
    }
}
