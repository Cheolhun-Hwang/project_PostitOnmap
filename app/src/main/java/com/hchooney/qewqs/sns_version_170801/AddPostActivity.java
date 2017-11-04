package com.hchooney.qewqs.sns_version_170801;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hchooney.qewqs.sns_version_170801.Post_Items.ListInfomationItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {
    private final static String TAG = "AddPostActivity";
    final static int REQ_CODE_SELECT_IMAGE = 8001;

    //상단바
    private ImageButton BackBTN;
    private Button CompleteBTN;

    //위치확인 레이아웃
    private TextView CurTextview;
    private ImageButton CurImageBTN;

    //카테고리 레이아웃
    private Spinner Category_One;
    private Spinner Category_Two;
    private Spinner Category_Three;

    //평점 레이아웃
    private RatingBar MyRating;

    //작성란
    private TextView CharCount;
    private EditText Title_EditText;
    private EditText Context_EditText;
    private EditText Hash_EditText;

    //갤러리
    private ImageButton Photo;
    private TextView DescriptPhotoTextview;

    //플래그
    private boolean isContextCharCount;

    //메타데이터
    private Location Save_location;
    private GPSListener gpsListener;
    private Geocoder GC;
    private Bitmap image_bitmap;
    private String who;
    private Uri PhotoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        if(getIntent() ==null){
            Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            this.who = getIntent().getStringExtra("Who");
        }

        init();

        Event();
    }

    private void init(){
        //상단바
        BackBTN = (ImageButton) findViewById(R.id.AddPost_Back_ImageButton);
        CompleteBTN = (Button) findViewById(R.id.AddPost_Complete_ImageButton);
        //위치 레이아웃
        CurTextview = (TextView) findViewById(R.id.AddPost_CurLocation_TextView);
        CurImageBTN = (ImageButton) findViewById(R.id.AddPost_CurLocation_ImageView);
        //카테고리
        Category_One = (Spinner) findViewById(R.id.AddPost_Spinner_One);
        setSpinnerThemaOne(R.array.High_array);
        Category_Two = (Spinner) findViewById(R.id.AddPost_Spinner_Two);
        Category_Three = (Spinner) findViewById(R.id.AddPost_Spinner_Three);
        //평점
        MyRating = (RatingBar) findViewById(R.id.AddPost_RatingBar);
        //작성
        CharCount = (TextView) findViewById(R.id.AddPost_CharCount);
        Title_EditText = (EditText) findViewById(R.id.AddPost_Title_Edittext);
        Context_EditText = (EditText) findViewById(R.id.AddPost_Context_EditText);
        Hash_EditText = (EditText) findViewById(R.id.AddPost_HashTag_Edittext);
        //갤러리
        Photo = (ImageButton)findViewById(R.id.AddPost_Galley_ImageButton);
        DescriptPhotoTextview = (TextView) findViewById(R.id.AddPost_Galley_Textview);
        //플래그
        isContextCharCount = false;
        //메타데이터
        GC = new Geocoder(this, Locale.KOREAN);
    }

    private void Event(){
        //상단바
        BackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CompleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                PostConfigFragment addMarkerFragment = new PostConfigFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Preview", getTempListInfo());
                addMarkerFragment.setArguments(bundle);
                addMarkerFragment.show(manager, "AddMarkerFragments");
            }
        });

        //위치 레이아웃
        CurImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
                CurTextview.setText("30초 이상 경과시 다시 눌러주세요.");
            }
        });
        //카테고리
        Category_One.setOnItemSelectedListener(spinSelectedListner1);
        Category_Two.setOnItemSelectedListener(spinSelectedListner2);
        //텍스트카운트
        Context_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 1000){
                    CharCount.setText(charSequence.length() + " / 1000");
                    CharCount.setTextColor(Color.RED);
                    isContextCharCount = false;
                }else{
                    CharCount.setText(charSequence.length() + " / 1000");
                    CharCount.setTextColor(Color.BLACK);
                    isContextCharCount = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //갤러리
        Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
    }

    //스피터 정의 : 상위 카테고리에서 하위 카테고리 변경
    public AdapterView.OnItemSelectedListener spinSelectedListner1 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    setSpinnerThemaTwo(R.array.Mid_array_1);
                    break;
                case 1:
                    setSpinnerThemaTwo(R.array.Mid_array_2);
                    break;
                case 2:
                    setSpinnerThemaTwo(R.array.Mid_array_3);
                    break;
                case 3:
                    setSpinnerThemaTwo(R.array.Mid_array_4);
                    break;
                case 4:
                    setSpinnerThemaTwo(R.array.Mid_array_5);
                    break;
                case 5:
                    setSpinnerThemaTwo(R.array.Mid_array_6);
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    public AdapterView.OnItemSelectedListener spinSelectedListner2 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int Tag_num = Category_One.getSelectedItemPosition();
            switch (Tag_num) {
                case (0):
                    switch (position){
                        case 0:
                            setSpinnerThemaThree(R.array.Low_array_1_1);
                            break;
                        case 1:
                            setSpinnerThemaThree(R.array.Low_array_1_2);
                            break;
                    }
                    break;
                case (1):
                    switch (position) {
                        case (0):
                            setSpinnerThemaThree(R.array.Low_array_2_1);
                            break;
                        case (1):
                            setSpinnerThemaThree(R.array.Low_array_2_2);
                            break;
                        case (2):
                            setSpinnerThemaThree(R.array.Low_array_2_3);
                            break;
                        case (4):
                            setSpinnerThemaThree(R.array.Low_array_2_4);
                            break;
                        case (5):
                            setSpinnerThemaThree(R.array.Low_array_2_5);
                            break;
                        case (6):
                            setSpinnerThemaThree(R.array.Low_array_2_6);
                            break;
                        case (7):
                            setSpinnerThemaThree(R.array.Low_array_2_7);
                            break;
                        case (8):
                            setSpinnerThemaThree(R.array.Low_array_2_8);
                            break;
                    }
                    break;
                case (2):
                    switch (position) {
                        case (0):
                            setSpinnerThemaThree(R.array.Low_array_3_1);
                            break;
                        case (1):
                            setSpinnerThemaThree(R.array.Low_array_3_2);
                            break;
                        case (2):
                            setSpinnerThemaThree(R.array.Low_array_3_3);
                            break;
                        case (3):
                            setSpinnerThemaThree(R.array.Low_array_3_4);
                            break;
                    }
                    break;
                case (3):
                    switch (position) {
                        case (0):
                            setSpinnerThemaThree(R.array.Low_array_4_1);
                            break;
                    }
                    break;
                case (4):
                    switch (position) {
                        case (0):
                            setSpinnerThemaThree(R.array.Low_array_5_1);
                            break;
                    }
                    break;
                case (5):
                    switch (position) {
                        case (0):
                            setSpinnerThemaThree(R.array.Low_array_6_1);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    private void setSpinnerThemaOne(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category_One.setAdapter(adapter);
    }

    private void setSpinnerThemaTwo(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category_Two.setAdapter(adapter);
    }

    private void setSpinnerThemaThree(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category_Three.setAdapter(adapter);
    }

    // 위치 정보 확인을 위해 정의한 메소드
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);

        // 위치 정보를 받을 위치 리스너 객체 생성
        gpsListener = new GPSListener();

        long minTime = 1000;//GPS정보 전달 시간 지정 - 1초마다 위치정보 전달
        float minDistance = 0;//이동거리 지정 - 이동하면 무조건 갱신

        //위치정보는 위치 프로바이더(Location Provider)를 통해 얻는다
        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, //위치 정보 확인 방법 설정
                    minTime, // 위치 정보 갱신 시간 설정
                    minDistance, //위치 정보 갱신을 위한 최소 이동거리 설정
                    gpsListener);//위치가 변동될 때마다 위치 정보 갱신을 위한 리스너 설정

            // 네트워크(기지국)를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();
                Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : "
                        + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "위치 확인 시작함.", Toast.LENGTH_SHORT).show();
    }//end of startLocationService()

    /**
     * 현재 위치의 지도를 보여주기 위해 정의한 메소드
     */
    private void SearchCurrentLocation(Location location) {
        /* 현재 위치를 이용해 주소 정보 확인하기
         */
        this.Save_location = location;
        List<Address> addresses = null;

        try{
            addresses = GC.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.d("GPS", "GPS addreses : " + GC.toString());
            if (addresses != null){
                Log.d("GPS", "GPS addreses Size : " + addresses.size());

                Address outAddr = addresses.get(0);
                StringBuffer outAddrStr = new StringBuffer();
                int addrCount = outAddr.getMaxAddressLineIndex() + 1;
                Log.d("GPS", "GPS addrCount Size : " + addrCount);
                for(int k=0;k<addrCount;k++){
                    outAddrStr.append(outAddr.getAddressLine(k));
                    Log.d("GPS", "GPS outAddrStr : " + outAddrStr.toString());
                }
                Log.d("GPS", "GPS outAddrStr final : " + outAddrStr.toString());
                CurTextview.setText(outAddrStr.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            LocationManager manager = (LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);
            manager.removeUpdates(gpsListener);
        }

    }// end of showCurrentLocation

    //GPS 인식
    private class GPSListener implements LocationListener {

        //위치 정보가 확인(수신)될 때마다 자동 호출되는 메소드
        public void onLocationChanged(Location location) {
            Log.i("GPSLocationService", location.toString());
            SearchCurrentLocation(location);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }// end of GPSListener


    //현재 시각을 얻기 위한 메소드 반환값 : String
    public String getCurrent_Date_time(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfnow = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        String stNow = sdfnow.format(date);
        return  stNow;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Log.d(TAG, "BitMap URL : " + data.getData());
                    PhotoURL = data.getData();
                    Log.d(TAG, "BitMap URL2 : " + this.PhotoURL);

                    //리사이즈
                    int height = image_bitmap.getHeight();
                    int width = image_bitmap.getWidth();

                    Bitmap resized = null;

                    //높이가 800이상 일때
                    while (height > 800) {
                        resized = Bitmap.createScaledBitmap(image_bitmap, (width * 800) / height, 800, true);
                        height = resized.getHeight();
                        width = resized.getWidth();
                    }

                    //배치해놓은 ImageView에 set
                    Photo.setImageBitmap(resized);
                    image_bitmap = resized;

                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private ListInfomationItem getTempListInfo(){
        ListInfomationItem listInfomationItem = new ListInfomationItem();

        //Item
        listInfomationItem.setItemNumber(this.who + "__" + Save_location.getLatitude() + "__" + Save_location.getLongitude());
        //위도 경도
        listInfomationItem.setLat(Save_location.getLatitude());
        listInfomationItem.setLon(Save_location.getLongitude());
        //주소
        String TempAddr = CurTextview.getText().toString();
        String[] SplitAddr = TempAddr.split(" ");
        listInfomationItem.setCity(SplitAddr[1]);
        listInfomationItem.setLocalCity(SplitAddr[2]);
        listInfomationItem.setDetailCity(TempAddr);

        //리스트
        listInfomationItem.setPostItDesign("postit_grade_one_img01");   //포스트잇은 각 클라이언트에 저장하게 한다. 이후에 각 사진이름을 태그 또는 이름을 받게한다.

        listInfomationItem.setContext(Context_EditText.getText().toString());
        listInfomationItem.setWho(this.who);
        listInfomationItem.setWhen(getCurrent_Date_time());
        listInfomationItem.setWhere(Title_EditText.getText().toString());
        //해시태그
        String tempString = Hash_EditText.getText().toString();
        String[] SplitString = tempString.split(",");
        ArrayList<String> temphashtag = new ArrayList<String>();
        for(int i = 0 ; i<SplitString.length ; i++){
            temphashtag.add(SplitString[i].replaceAll(" ", ""));
        }
        listInfomationItem.setHashTag(temphashtag);
        //평점
        listInfomationItem.setLikeRate(MyRating.getRating());
        Log.d(TAG, "Rating : " + MyRating.getRating());
        //카테고리
        listInfomationItem.setGenreOne(Category_One.getSelectedItem().toString());
        listInfomationItem.setGenreTwo(Category_Two.getSelectedItem().toString());
        listInfomationItem.setGenreThree(Category_Three.getSelectedItem().toString());

        return listInfomationItem;
    }

    public void NotificationAdator(){
        finish();
    }



    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

    public Uri getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(Uri photoURL) {
        PhotoURL = photoURL;
    }
}
