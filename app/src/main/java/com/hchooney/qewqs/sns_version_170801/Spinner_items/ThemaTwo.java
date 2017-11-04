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

public class ThemaTwo implements AdapterView.OnItemSelectedListener {
    public static String Middle_Category_meta;

    private Context mContext;
    private Spinner Filter_Category_ThemaOne;
    private Spinner Filter_Category_ThemaThree;

    public ThemaTwo(Context context, Spinner spinner, Spinner spinner2){
        this.mContext = context;
        this.Filter_Category_ThemaOne = spinner;
        this.Filter_Category_ThemaThree = spinner2;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        int Tag_num = Filter_Category_ThemaOne.getSelectedItemPosition();
        switch (Tag_num) {
            case (0):
                switch (position){
                    case 0:
                        setSpinnerThemaThree(R.array.Low_array_1_1);
                        Middle_Category_meta = "A0101";
                        break;
                    case 1:
                        setSpinnerThemaThree(R.array.Low_array_1_2);
                        Middle_Category_meta = "A0102";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            case (1):
                switch (position) {
                    case (0):
                        setSpinnerThemaThree(R.array.Low_array_2_1);
                        Middle_Category_meta = "A0201";
                        break;
                    case (1):
                        setSpinnerThemaThree(R.array.Low_array_2_2);
                        Middle_Category_meta = "A0202";
                        break;
                    case (2):
                        setSpinnerThemaThree(R.array.Low_array_2_3);
                        Middle_Category_meta = "A0203";
                        break;
                    case (3):
                        setSpinnerThemaThree(R.array.Low_array_2_4);
                        Middle_Category_meta = "A0204";
                        break;
                    case (4):
                        setSpinnerThemaThree(R.array.Low_array_2_5);
                        Middle_Category_meta = "A0205";
                        break;
                    case (5):
                        setSpinnerThemaThree(R.array.Low_array_2_6);
                        Middle_Category_meta = "A0206";
                        break;
                    case (6):
                        setSpinnerThemaThree(R.array.Low_array_2_7);
                        Middle_Category_meta = "A0207";
                        break;
                    case (7):
                        setSpinnerThemaThree(R.array.Low_array_2_8);
                        Middle_Category_meta = "A0208";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            case (2):
                switch (position) {
                    case (0):
                        setSpinnerThemaThree(R.array.Low_array_3_1);
                        Middle_Category_meta = "A0302";
                        break;
                    case (1):
                        setSpinnerThemaThree(R.array.Low_array_3_2);
                        Middle_Category_meta = "A0303";
                        break;
                    case (2):
                        setSpinnerThemaThree(R.array.Low_array_3_3);
                        Middle_Category_meta = "A0304";
                        break;
                    case (3):
                        setSpinnerThemaThree(R.array.Low_array_3_4);
                        Middle_Category_meta = "A0305";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            case (3):
                switch (position) {
                    case (0):
                        setSpinnerThemaThree(R.array.Low_array_4_1);
                        Middle_Category_meta = "A0401";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            case (4):
                switch (position) {
                    case (0):
                        setSpinnerThemaThree(R.array.Low_array_5_1);
                        Middle_Category_meta = "A0502";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            case (5):
                switch (position) {
                    case (0):
                        setSpinnerThemaThree(R.array.Low_array_6_1);
                        Middle_Category_meta = "B0201";
                        break;
                    default:
                        Log.d("Filter_Spinner", "Spinner Error Check!");
                        break;
                }
                break;
            default:
                Log.d("Filter_Spinner", "Spinner Error Check!");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinnerThemaThree(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_Category_ThemaThree.setAdapter(adapter);
    }
}