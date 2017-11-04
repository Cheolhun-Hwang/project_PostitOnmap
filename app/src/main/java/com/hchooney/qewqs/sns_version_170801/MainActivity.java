package com.hchooney.qewqs.sns_version_170801;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.hchooney.qewqs.sns_version_170801.Post_Items.ListInfomationItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private FragmentManager fragmentManager;
    private PostFragment postFragment;
    private MapFragment mapFragment;

    /*
    * 메타데이터
    * 추후 반드시 명시할것
    *
    * 이름, 나이, 성별 데이터를 가지고 있어야한다.
     */
    private String Who = "Master";
    private String Age;
    private String Sex;

    //StorDate
    private ArrayList<ListInfomationItem> listInfomationItems;

    public ArrayList<ListInfomationItem> getListInfomationItems() {
        return listInfomationItems;
    }

    public void setListInfomationItems(ArrayList<ListInfomationItem> listInfomationItems) {
        this.listInfomationItems = listInfomationItems;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_postit:
                    Change_Fragment(mapFragment);
                    return true;
                case R.id.navigation_api:
                    Change_Fragment(postFragment);
                    return true;
                case R.id.navigation_myinfo:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        *   현재 액티비티는 메인을 담당하는 액티비티입니다.
        *   기본 네비게이터로 각 인포메이션을 이동합니다.
        *   메인 액티비티는 어떠한 일이 있어도 종료하면 안됩니다. 이동 간에 주의하시기 바랍니다.
        *
        *   이하설명 :
        *
        *
         */

        //액티비티 초기화
        init();

        //네미게이션의 경우 초기화 함수로 가지 않고 onCreate에서 처리한다.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //네비게이션 hold : 포스트잇 프레그먼트로 한다.
        navigation.setSelectedItemId(R.id.navigation_postit);
    }

    private void init(){
        //프레그먼트 메니저 초기화
        fragmentManager = getSupportFragmentManager();
        //각 탭의 프래그먼트 초기화
        postFragment = new PostFragment();
        mapFragment = new MapFragment();
        //포스트잇 데이터를 저장하는 동적리스트 초기화
        listInfomationItems = new ArrayList<ListInfomationItem>();
    }

    private void Change_Fragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
    }

    public String getWho() {
        return Who;
    }
}
