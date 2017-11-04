package com.hchooney.qewqs.sns_version_170801.Spinner_items;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by qewqs on 2017-08-07.
 */

public class ThemaThree  implements AdapterView.OnItemSelectedListener {
    public static String Low_Category_meta;

    private Context mContext;
    private Spinner Filter_Category_ThemaOne;
    private Spinner Filter_Category_ThemaTwo;
    private int Tag_num;

    public ThemaThree(Context context, Spinner spinner, Spinner spinner2){
        this.mContext = context;
        this.Filter_Category_ThemaOne = spinner;
        this.Filter_Category_ThemaTwo = spinner2;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        int TagNumOne = Filter_Category_ThemaOne.getSelectedItemPosition();
        int TagNumTwo = Filter_Category_ThemaTwo.getSelectedItemPosition();
        switch (TagNumOne){
            case 0:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A01010100";
                                break;
                            case 1:
                                Low_Category_meta = "A01010200";
                                break;
                            case 2:
                                Low_Category_meta = "A01010300";
                                break;
                            case 3:
                                Low_Category_meta = "A01010400";
                                break;
                            case 4:
                                Low_Category_meta = "A01010500";
                                break;
                            case 5:
                                Low_Category_meta = "A01010600";
                                break;
                            case 6:
                                Low_Category_meta = "A01010700";
                                break;
                            case 7:
                                Low_Category_meta = "A01010800";
                                break;
                            case 8:
                                Low_Category_meta = "A01010900";
                                break;
                            case 9:
                                Low_Category_meta = "A01011000";
                                break;
                            case 10 :
                                Low_Category_meta = "A01011100";
                                break;
                            case 11 :
                                Low_Category_meta = "A01011200";
                                break;
                            case 12 :
                                Low_Category_meta = "A01011300";
                                break;
                            case 13 :
                                Low_Category_meta = "A01011400";
                                break;
                            case 14 :
                                Low_Category_meta = "A01011500";
                                break;
                            case 15 :
                                Low_Category_meta = "A01011600";
                                break;
                            case 16:
                                Low_Category_meta = "A01011700";
                                break;
                            case 17:
                                Low_Category_meta = "A01011800";
                                break;
                            case 18 :
                                Low_Category_meta = "A01011900";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 1:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A01020100";
                                break;
                            case 1:
                                Low_Category_meta = "A01020200";
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
                break;
            case 1:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02010100";
                                break;
                            case 1:
                                Low_Category_meta = "A02010200";
                                break;
                            case 2:
                                Low_Category_meta = "A02010300";
                                break;
                            case 3:
                                Low_Category_meta = "A02010400";
                                break;
                            case 4:
                                Low_Category_meta = "A02010500";
                                break;
                            case 5:
                                Low_Category_meta = "A02010600";
                                break;
                            case 6:
                                Low_Category_meta = "A02010700";
                                break;
                            case 7:
                                Low_Category_meta = "A02010800";
                                break;
                            case 8:
                                Low_Category_meta = "A02010900";
                                break;
                            case 9:
                                Low_Category_meta = "A02011000";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 1:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02020100";
                                break;
                            case 1:
                                Low_Category_meta = "A02020200";
                                break;
                            case 2:
                                Low_Category_meta = "A02020300";
                                break;
                            case 3:
                                Low_Category_meta = "A02020400";
                                break;
                            case 4:
                                Low_Category_meta = "A02020500";
                                break;
                            case 5:
                                Low_Category_meta = "A02020600";
                                break;
                            case 6 :
                                Low_Category_meta = "A02020700";
                                break;
                            case 7 :
                                Low_Category_meta = "A02020800";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 2:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02030100";
                                break;
                            case 1:
                                Low_Category_meta = "A02030200";
                                break;
                            case 2:
                                Low_Category_meta = "A02030300";
                                break;
                            case 3:
                                Low_Category_meta = "A02030400";
                                break;
                            case 4:
                                Low_Category_meta = "A02030500";
                                break;
                            case 5:
                                Low_Category_meta = "A02030600";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 3:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02040100";
                                break;
                            case 1:
                                Low_Category_meta = "A02040200";
                                break;
                            case 2:
                                Low_Category_meta = "A02040300";
                                break;
                            case 3:
                                Low_Category_meta = "A02040400";
                                break;
                            case 4:
                                Low_Category_meta = "A02040500";
                                break;
                            case 5:
                                Low_Category_meta = "A02040600";
                                break;
                            case 6:
                                Low_Category_meta = "A02040700";
                                break;
                            case 7:
                                Low_Category_meta = "A02040800";
                                break;
                            case 8:
                                Low_Category_meta = "A02040900";
                                break;
                            case 9:
                                Low_Category_meta = "A02041000";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 4:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02050100";
                                break;
                            case 1:
                                Low_Category_meta = "A02050200";
                                break;
                            case 2:
                                Low_Category_meta = "A02050300";
                                break;
                            case 3:
                                Low_Category_meta = "A02050400";
                                break;
                            case 4:
                                Low_Category_meta = "A02050500";
                                break;
                            case 5:
                                Low_Category_meta = "A02050600";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 5:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02060100";
                                break;
                            case 1:
                                Low_Category_meta = "A02060200";
                                break;
                            case 2:
                                Low_Category_meta = "A02060300";
                                break;
                            case 3:
                                Low_Category_meta = "A02060400";
                                break;
                            case 4:
                                Low_Category_meta = "A02060500";
                                break;
                            case 5:
                                Low_Category_meta = "A02060600";
                                break;
                            case 6:
                                Low_Category_meta = "A02060700";
                                break;
                            case 7:
                                Low_Category_meta = "A02060800";
                                break;
                            case 8:
                                Low_Category_meta = "A02060900";
                                break;
                            case 9:
                                Low_Category_meta = "A02061000";
                                break;
                            case 10:
                                Low_Category_meta = "A02061100";
                                break;
                            case 11:
                                Low_Category_meta = "A02061200";
                                break;
                            case 12:
                                Low_Category_meta = "A02061300";
                                break;
                            case 13:
                                Low_Category_meta = "A02061400";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 6:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02070100";
                                break;
                            case 1:
                                Low_Category_meta = "A02070200";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 7:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A02080100";
                                break;
                            case 1:
                                Low_Category_meta = "A02080200";
                                break;
                            case 2:
                                Low_Category_meta = "A02080300";
                                break;
                            case 3:
                                Low_Category_meta = "A02080400";
                                break;
                            case 4:
                                Low_Category_meta = "A02080500";
                                break;
                            case 5:
                                Low_Category_meta = "A02080600";
                                break;
                            case 6:
                                Low_Category_meta = "A02080700";
                                break;
                            case 7:
                                Low_Category_meta = "A02080800";
                                break;
                            case 8:
                                Low_Category_meta = "A02080900";
                                break;
                            case 9:
                                Low_Category_meta = "A02081000";
                                break;
                            case 10:
                                Low_Category_meta = "A02081100";
                                break;
                            case 11:
                                Low_Category_meta = "A02081200";
                                break;
                            case 12:
                                Low_Category_meta = "A02081300";
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
                break;
            case 2:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A03020100";
                                break;
                            case 1:
                                Low_Category_meta = "A03020200";
                                break;
                            case 2:
                                Low_Category_meta = "A03020300";
                                break;
                            case 3:
                                Low_Category_meta = "A03020400";
                                break;
                            case 4:
                                Low_Category_meta = "A03020500";
                                break;
                            case 5:
                                Low_Category_meta = "A03020600";
                                break;
                            case 6:
                                Low_Category_meta = "A03020700";
                                break;
                            case 7:
                                Low_Category_meta = "A03020800";
                                break;
                            case 8:
                                Low_Category_meta = "A03020900";
                                break;
                            case 9:
                                Low_Category_meta = "A03021000";
                                break;
                            case 10:
                                Low_Category_meta = "A03021100";
                                break;
                            case 11:
                                Low_Category_meta = "A03021200";
                                break;
                            case 12:
                                Low_Category_meta = "A03021300";
                                break;
                            case 13:
                                Low_Category_meta = "A03021400";
                                break;
                            case 14:
                                Low_Category_meta = "A03021500";
                                break;
                            case 15:
                                Low_Category_meta = "A03021600";
                                break;
                            case 16:
                                Low_Category_meta = "A03021700";
                                break;
                            case 17:
                                Low_Category_meta = "A03021800";
                                break;
                            case 18:
                                Low_Category_meta = "A03021900";
                                break;
                            case 19:
                                Low_Category_meta = "A03022000";
                                break;
                            case 20:
                                Low_Category_meta = "A03022100";
                                break;
                            case 21:
                                Low_Category_meta = "A03022200";
                                break;
                            case 22:
                                Low_Category_meta = "A03022300";
                                break;
                            case 23:
                                Low_Category_meta = "A03022400";
                                break;
                            case 24:
                                Low_Category_meta = "A03022500";
                                break;
                            case 25:
                                Low_Category_meta = "A03022600";
                                break;
                            case 26:
                                Low_Category_meta = "A03022700";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 1:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A03030100";
                                break;
                            case 1:
                                Low_Category_meta = "A03030200";
                                break;
                            case 2:
                                Low_Category_meta = "A03030300";
                                break;
                            case 3:
                                Low_Category_meta = "A03030400";
                                break;
                            case 4:
                                Low_Category_meta = "A03030500";
                                break;
                            case 5:
                                Low_Category_meta = "A03030600";
                                break;
                            case 6:
                                Low_Category_meta = "A03030700";
                                break;
                            case 7:
                                Low_Category_meta = "A03030800";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 2:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A03040100";
                                break;
                            case 1:
                                Low_Category_meta = "A03040200";
                                break;
                            case 2:
                                Low_Category_meta = "A03040300";
                                break;
                            case 3:
                                Low_Category_meta = "A03040400";
                                break;
                            default:
                                Log.d("Filter_Spinner", "Spinner Error Check!");
                                break;
                        }
                        break;
                    case 3:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A03050100";
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
                break;
            case 3:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A04010100";
                                break;
                            case 1:
                                Low_Category_meta = "A04010200";
                                break;
                            case 2:
                                Low_Category_meta = "A04010300";
                                break;
                            case 3:
                                Low_Category_meta = "A04010400";
                                break;
                            case 4:
                                Low_Category_meta = "A04010500";
                                break;
                            case 5:
                                Low_Category_meta = "A04010600";
                                break;
                            case 6:
                                Low_Category_meta = "A04010700";
                                break;
                            case 7:
                                Low_Category_meta = "A04010800";
                                break;
                            case 8:
                                Low_Category_meta = "A04010900";
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
                break;
            case 4:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "A05020100";
                                break;
                            case 1:
                                Low_Category_meta = "A05020200";
                                break;
                            case 2:
                                Low_Category_meta = "A05020300";
                                break;
                            case 3:
                                Low_Category_meta = "A05020400";
                                break;
                            case 4:
                                Low_Category_meta = "A05020500";
                                break;
                            case 5:
                                Low_Category_meta = "A05020600";
                                break;
                            case 6:
                                Low_Category_meta = "A05020700";
                                break;
                            case 7:
                                Low_Category_meta = "A05020800";
                                break;
                            case 8:
                                Low_Category_meta = "A05020900";
                                break;
                            case 9:
                                Low_Category_meta = "A05021000";
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
                break;
            case 5:
                switch (TagNumTwo){
                    case 0:
                        switch (position){
                            case 0:
                                Low_Category_meta = "B02010100";
                                break;
                            case 1:
                                Low_Category_meta = "B02010200";
                                break;
                            case 2:
                                Low_Category_meta = "B02010300";
                                break;
                            case 3:
                                Low_Category_meta = "B02010400";
                                break;
                            case 4:
                                Low_Category_meta = "B02010500";
                                break;
                            case 5:
                                Low_Category_meta = "B02010600";
                                break;
                            case 6:
                                Low_Category_meta = "B02010700";
                                break;
                            case 7:
                                Low_Category_meta = "B02010800";
                                break;
                            case 8:
                                Low_Category_meta = "B02010900";
                                break;
                            case 9:
                                Low_Category_meta = "B02011000";
                                break;
                            case 10:
                                Low_Category_meta = "B02011100";
                                break;
                            case 11:
                                Low_Category_meta = "B02011200";
                                break;
                            case 12:
                                Low_Category_meta = "B02011300";
                                break;
                            case 13:
                                Low_Category_meta = "B02011400";
                                break;
                            case 14:
                                Low_Category_meta = "B02011500";
                                break;
                            case 15:
                                Low_Category_meta = "B02011600";
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
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}