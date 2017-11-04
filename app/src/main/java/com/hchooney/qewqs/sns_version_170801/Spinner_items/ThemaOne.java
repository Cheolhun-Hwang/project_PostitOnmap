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

public class ThemaOne implements AdapterView.OnItemSelectedListener {
    public static String High_Category_meta;

    private Context mContext;
    private Spinner Filter_Category_ThemaTwo;

    public ThemaOne(Context context, Spinner spinner){
        this.mContext = context;
        this.Filter_Category_ThemaTwo = spinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                setSpinnerThemaTwo(R.array.Mid_array_1);
                High_Category_meta = "A01";
                break;
            case 1:
                setSpinnerThemaTwo(R.array.Mid_array_2);
                High_Category_meta = "A02";
                break;
            case 2:
                setSpinnerThemaTwo(R.array.Mid_array_3);
                High_Category_meta = "A03";
                break;
            case 3:
                setSpinnerThemaTwo(R.array.Mid_array_4);
                High_Category_meta = "A04";
                break;
            case 4:
                setSpinnerThemaTwo(R.array.Mid_array_5);
                High_Category_meta = "A05";
                break;
            case 5:
                setSpinnerThemaTwo(R.array.Mid_array_6);
                High_Category_meta = "B02";
                break;
            default:
                Log.d("Filter_Spinner", "Spinner Error Check!");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinnerThemaTwo(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_Category_ThemaTwo.setAdapter(adapter);
    }
}
