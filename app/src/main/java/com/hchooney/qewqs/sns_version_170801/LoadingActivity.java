package com.hchooney.qewqs.sns_version_170801;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {
    private final static String TAG = "LoadingActivity";
    private ImageView BackgroundImg;
    private Thread ActivityChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        /*
        * 현재 액티비티는 로딩 시 필요한 액티비티입니다.
        * 앱의 특징 및 자사의 특징이 노출된 이미지가 표시되어야 합니다.
        * 이미지는 PNG로 사용하며 2자리 kb 이상을 넘어서면 안되는 점을 인지하여야 합니다.
        *
        * 차후 수정 명시하여야 합니다.
        * 처음 2~3초로 수동으로 지정합니다. 서버에서 초반 데이터를 받거나 로그인 과정이 필요시
        * 이를 이용합니다.
        *
         */

        init();

        ActivityChanger = new Thread(new Runnable() {
            @Override
            public void run() {
                Loading_Holder(3);
            }
        });

        //관리자 권한설정 확인
        checkDangerousPermissions();


    }

    private void init(){

        /*
        * 수정시 명시 바람
        * 이미지 : jeju2 현재 임시로 사용
         */
        BackgroundImg = (ImageView) findViewById(R.id.Background_ImageView);
        BackgroundImg.setImageResource(R.drawable.jeju2);

    }

    private void Loading_Holder(int sec){
        /*
        * 로딩 시 초간 홀드하는 함수 입니다.
        * 매개변수 : sec 초간 받습니다. sec * 1000
        *
        * 추후 수정시 명시바람
        *
        * 이후 로그인 관련 사항 시
        *
        * 첫 로그인 시 : 로그인 파트로 이동!! -> 로그인
        * 자동 로그인 설정 시 : 메인액티비티로 이동한다.
         */

        try{
            Thread.sleep( sec*1000 );
        }catch (Exception e){
            Log.e(TAG, "Loading Error : Sleep Part !!");
            e.printStackTrace();
        }finally {
            Activity_Intent();
        }

    }

    private void Activity_Intent(){
        /*
        * 액티비티를 넘기기 위한 함수 입니다.
        * 다시 돌아올 필요가 없기 때문에 페이지 이동 시 반드시 액티비티 종료가 필요.
        *
        * 이후 필요시 수정 필요
        * Bundle로 데이터 이동 시 명시할 것.
         */
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra()
        startActivity(intent);
        this.finish();
    }

    /* 사용자 권한 확인 메서드
       - import android.Manifest; 를 시킬 것
     */
    private void checkDangerousPermissions() {
        String[] permissions = {//import android.Manifest;
                android.Manifest.permission.ACCESS_FINE_LOCATION,   //GPS 이용권한
                android.Manifest.permission.ACCESS_COARSE_LOCATION, //네트워크/Wifi 이용 권한
                android.Manifest.permission.READ_EXTERNAL_STORAGE,  //읽기 권한
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,  //쓰기 권한
                android.Manifest.permission.INTERNET                 //인터넷 사용 권한
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "권한있음");
            ActivityChanger.start();
        } else {
            Log.d(TAG, "권한없음");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Log.d(TAG, "권한설명란");
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }//end of checkDangerousPermissions

    // 사용자의 권한 확인 후 사용자의 권한에 대한 응답 결과를 확인하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
            ActivityChanger.start();
        }
    }//end of onRequestPermissionsResult
}
