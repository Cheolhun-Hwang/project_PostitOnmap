package com.hchooney.qewqs.sns_version_170801;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hchooney.qewqs.sns_version_170801.AnyItems.MyDialogFragment;
import com.hchooney.qewqs.sns_version_170801.Geo_Items.GeoMarkItem;
import com.hchooney.qewqs.sns_version_170801.Recycle_items.Adapter;
import com.hchooney.qewqs.sns_version_170801.Spinner_items.Citys;
import com.hchooney.qewqs.sns_version_170801.Spinner_items.LocalCitys;
import com.hchooney.qewqs.sns_version_170801.Spinner_items.ThemaOne;
import com.hchooney.qewqs.sns_version_170801.Spinner_items.ThemaThree;
import com.hchooney.qewqs.sns_version_170801.Spinner_items.ThemaTwo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment{
    private View view;
    public GoogleMap mMap;
    private SupportMapFragment supportMapFragment;

    //상단바 버튼
    private ImageButton Map_Search_Btn;

    //Filter
    private LinearLayout Filter_Layout;
    private ImageButton Filter_BackBTN;
    private Button Filter_Complete;

    //Spinner
    private Spinner Filter_Category_city;
    private Spinner Filter_Category_Local;

    private Spinner Filter_Category_ThemaOne;
    private Spinner Filter_Category_ThemaTwo;
    private Spinner Filter_Category_themaThree;

    private ThemaOne themaOne;
    private ThemaTwo themaTwo;
    private ThemaThree themaThree;
    private Citys citys;
    private LocalCitys localCitys;


    private Spinner Filter_HowToShow;

    //프로그래스바
    private MyDialogFragment myDialogFragment;

    //Geo 마커 정보를 가질 동적 리스트
    private ArrayList<GeoMarkItem> Geo_Markers;

    //Info Recycle;
    private LinearLayout Info_Layout;
    private RecyclerView Info_Recycle;
    private Adapter adapter;


    //URL Post 할때 쓰는거
    private String contentTypeId_meta = "";
    private String areaCode_meta = "";
    private String sigunguCode_meta = "";
    private String High_Category_meta = "";
    private String Middle_Category_meta = "";
    private String Low_Category_meta = "";
    private String Arrang_meta = "";
    private String PageNumRows_meta = "";
    private String pageNo_meta = "";

    //플래그
    private boolean isSearch;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1001){
                setMarkersOnMap();
                Info_Layout.setVisibility(View.VISIBLE);
                Info_Recycle.setAdapter(adapter);
                myDialogFragment.EndDialog();
                isSearch = true;
            }else if(msg.what == 1002){
                Toast.makeText(getContext(), "죄송합니다. 다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1003){
                Toast.makeText(getContext(), "총 0건이 검색되었습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Handler", "Handler msg.what check!!");
            }
        }
    };

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_map, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        Map_Search_Btn = (ImageButton) view.findViewById(R.id.Map_Search_ImageButton);
        Filter_Layout = (LinearLayout) view.findViewById(R.id.Filter_Layout);
        Filter_BackBTN = (ImageButton) view.findViewById(R.id.Filter_Back_ImageButton);
        Filter_Complete = (Button) view.findViewById(R.id.Filter_Complete_ImageButton);
        Filter_Category_city = (Spinner) view.findViewById(R.id.Filter_Category_City);
        Filter_Category_Local = (Spinner) view.findViewById(R.id.Filter_Category_LocalCity);
        Filter_Category_ThemaOne = (Spinner) view.findViewById(R.id.Filter_Spinner_One);
        Filter_Category_ThemaTwo = (Spinner) view.findViewById(R.id.Filter_Spinner_Two);
        Filter_Category_themaThree = (Spinner) view.findViewById(R.id.Filter_Spinner_Three);
        Filter_HowToShow = (Spinner) view.findViewById(R.id.Filter_HowToBest

        );
        //스피너 동적할당을 위한 부분
        setSpinnerThemaOne(R.array.High_array);
        setCitySpinner(R.array.city_array);
        setHowToShowSpinner(R.array.HowToShowTheList);

        citys = new Citys(getContext(), Filter_Category_Local);
        localCitys = new LocalCitys(getContext(), Filter_Category_city);
        themaOne = new ThemaOne(getContext(), Filter_Category_ThemaTwo);
        themaTwo = new ThemaTwo(getContext(), Filter_Category_ThemaOne, Filter_Category_themaThree);
        themaThree = new ThemaThree(getContext(),Filter_Category_ThemaOne, Filter_Category_themaThree);

        Filter_Category_city.setOnItemSelectedListener(citys);
        Filter_Category_Local.setOnItemSelectedListener(localCitys);
        Filter_Category_ThemaOne.setOnItemSelectedListener(themaOne);
        Filter_Category_ThemaTwo.setOnItemSelectedListener(themaTwo);
        Filter_Category_themaThree.setOnItemSelectedListener(themaThree);
        Filter_HowToShow.setOnItemSelectedListener(HowToShowSpinSelectListener);

        //프로그레스바
        myDialogFragment = new MyDialogFragment(getContext());
        myDialogFragment.setTitleToMyDialog("해당 지역을 찾고 있습니다.");
        myDialogFragment.setMessageToMyDialog("빠르게 확인 중....");

        //Geo 마커 동적리스트 초기화
        Geo_Markers = new ArrayList<GeoMarkItem>();

        //info
        Info_Layout = (LinearLayout) view.findViewById(R.id.info_Layout);
        Info_Recycle = (RecyclerView) view.findViewById(R.id.info_Recycleview);
        adapter = new Adapter(Geo_Markers, Info_Recycle);

        Info_Recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //플래그
        isSearch = false;


        //이벤트 함수 호출
        Event();
    }

    private void Event(){
        Map_Search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filter_Layout.setVisibility(View.VISIBLE);
                if(Info_Layout.getVisibility() == View.VISIBLE){
                    Info_Layout.setVisibility(View.GONE);
                }
            }
        });

        Filter_BackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filter_Layout.setVisibility(View.GONE);
                if (isSearch == true){
                    Info_Layout.setVisibility(View.VISIBLE);
                }
            }
        });

        Filter_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geo_Markers.clear();

                Filter_Layout.setVisibility(view.GONE);

                myDialogFragment.StartDialog();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetDateBasetoServer();
                    }
                }).start();
            }
        });

        Info_Recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("OnScrollLister", "postion : " + dx);
            }
        });

    }

    //스피터 정의 : 상위 카테고리에서 하위 카테고리 변경
    private AdapterView.OnItemSelectedListener HowToShowSpinSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
            switch (postion){
                case 0:
                    Arrang_meta = "R";
                    break;
                case 1:
                    Arrang_meta = "P";
                    break;
                case 2:
                    Arrang_meta = "O";
                    break;
                default:
                    Log.d("Filter_Spinner", "Spinner Error Check!");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void setSpinnerThemaOne(int itemNum) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), itemNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_Category_ThemaOne.setAdapter(adapter);
    }

    private void setCitySpinner(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(getContext(), itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_Category_city.setAdapter(fAdapter);
    }

    private void setHowToShowSpinner(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(getContext(), itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Filter_HowToShow.setAdapter(fAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.Map_Mapview);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.Map_Mapview, supportMapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap == null && !isSearch) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    // Add a marker in Sydney, Australia, and move the camera.
                    LatLng location =  new LatLng(37.553186, 126.972013);
                    mMap.addMarker(new MarkerOptions().position(location).title("서울역"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));


                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(getContext(), "GPS 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(getContext(), "GPS 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    if(isSearch){
                        setMarkersOnMap();
                    }else{
                        LatLng location =  new LatLng(37.553186, 126.972013);
                        mMap.addMarker(new MarkerOptions().position(location).title("서울역"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
                    }

                }
            });
        }
    }

    private void GetDateBasetoServer(){
        /*
        * 서버에서 해당 정보를 가져오기 위한 메소드이다.
        *
        * 해당 메소드는 당연히 서브쓰레드에서 실행한다.
        *
        * 한번 받아올때 PageRaows 는 400개로 고정한다. 페이지는 첫번째장만 확인한다.
        * 즉, 최종 맵에 표시되는 GPS의 최대 수는 400개이며,
        * 전체를 한번에 찍을  수 없이 세부 내용까지 확인하여야만 해당 마커가 표시되도록한다.
        *
        * 이는 차후에 바꿀 수 있으며, 이는 위와 같이 반드시 명시한다.
         */

        this.PageNumRows_meta = "400";
        this.pageNo_meta = "1";

        this.areaCode_meta = citys.areaCode_meta;
        this.sigunguCode_meta = localCitys.sigunguCode_meta;
        this.High_Category_meta = themaOne.High_Category_meta;
        this.Middle_Category_meta = themaTwo.Middle_Category_meta;
        this.Low_Category_meta = themaThree.Low_Category_meta;

        String base_URL = "http://api.visitkorea.or.kr/openapi/service/rest/";
        String Service_kor = "KorService/areaBasedList?";
        String Service_key = "6tLZdNCgFtUUkC1aMEPPSDH5EqZB09HbJ9vEwO1DeRGItkpZQzyAxdTw2npenOfhIQdsklstTNt9qrj2RODhkQ%3D%3D";
        String contentTypeId = "&contentTypeId=";
        String areaCode = "&areaCode=";
        String sigunguCode = "&sigunguCode=";
        String High_Category = "&cat1=";
        String Middle_Category = "&cat2=";
        String Row_Category = "&cat3=";
        String base_service = "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&";
        String Arrage = "arrange=";
        String PageNumRows = "&numOfRows=";
        String pageNo = "&pageNo=";

        String Post_URL = base_URL + Service_kor + "ServiceKey=" + Service_key + contentTypeId + contentTypeId_meta +
                areaCode + areaCode_meta + sigunguCode + sigunguCode_meta + High_Category + High_Category_meta + Middle_Category +
                Middle_Category_meta + Row_Category + Low_Category_meta + base_service + Arrage + Arrang_meta + PageNumRows + PageNumRows_meta+
                pageNo + pageNo_meta;

        Log.d("Post URL", Post_URL);

        getXmlData(Post_URL);

    }

    void getXmlData(String url_base) {
        try {
            URL url = new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();  //태그값 초기화
            int eventType = xpp.getEventType();     //태그를통한 이벤트 확인

            String addr1 = "";
            String addr2 = "";
            String areacode = "";
            String cat1= "";
            String cat2= "";
            String cat3= "";
            String contentid= "";
            String contenttypeid= "";
            String firstimage= "";
            String firstimage2= "";
            double mapx=0;
            double mapy=0;
            String modified= "";
            String readcount= "";
            String sigungucode= "";
            String tel= "";
            String title= "";
            String zipcode= "";


            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:       //시작 태그확인 ex) <Head>, <Body>, <div>
                        tag = xpp.getName();
                        switch (tag){
                            case "item":
                                 addr1 = "";
                                 addr2 = "None";
                                 areacode = "";
                                 cat1 = "";
                                 cat2 = "";
                                 cat3 = "";
                                 contentid = "";
                                 contenttypeid = "";
                                 firstimage = "None";
                                 firstimage2 = "None";
                                 mapx = 0;
                                 mapy = 0;
                                 modified = "";
                                 readcount = "None";
                                 sigungucode = "";
                                 tel = "";
                                 title = "";
                                 zipcode = "";
                                break;
                            case "addr1":
                                xpp.next();
                                addr1 = xpp.getText().toString();
                                break;
                            case "addr2":
                                xpp.next();
                                addr2 = xpp.getText().toString();
                                break;
                            case "areacode":
                                xpp.next();
                                areacode = xpp.getText().toString();
                                break;
                            case "cat1":
                                xpp.next();
                                cat1 = xpp.getText().toString();
                                break;
                            case "cat2":
                                xpp.next();
                                cat3 = xpp.getText().toString();
                                break;
                            case "cat3":
                                xpp.next();
                                cat3 = xpp.getText().toString();
                                break;
                            case "contentid":
                                xpp.next();
                                contentid = xpp.getText().toString();
                                break;
                            case "contenttypeid":
                                xpp.next();
                                contenttypeid = xpp.getText().toString();
                                break;
                            case "firstimage":
                                xpp.next();
                                firstimage = xpp.getText().toString();
                                break;
                            case "firstimage2":
                                xpp.next();
                                firstimage2 = xpp.getText().toString();
                                break;
                            case "mapx":
                                xpp.next();
                                mapx = Double.parseDouble(xpp.getText().toString());
                                break;
                            case "mapy":
                                xpp.next();
                                mapy = Double.parseDouble(xpp.getText().toString());
                                break;
                            case "modifiedtime":
                                xpp.next();
                                modified = xpp.getText().toString();
                                break;
                            case "readcount":
                                xpp.next();
                                readcount = xpp.getText().toString();
                                break;
                            case "sigungucode":
                                xpp.next();
                                sigungucode = xpp.getText().toString();
                                break;
                            case "tel":
                                xpp.next();
                                tel = xpp.getText().toString();
                                break;
                            case "title":
                                xpp.next();
                                title = xpp.getText().toString();
                                break;
                            case "zipcode":
                                xpp.next();
                                zipcode = xpp.getText().toString();
                                break;
                            default:
                                Log.d("XML Tag", xpp.toString());
                                break;
                        }
                        break;
                    case XmlPullParser.TEXT:            //태그 사이에 들어있는 텍스트
                        break;
                    case XmlPullParser.END_TAG:       //종료 태그확인 ex) </Head>, </Body>, </div>
                        tag = xpp.getName();    //테그 이름 얻어오기
                        switch (tag){
                            case "item":
                                Log.d("XML Tag", "-----------------------------------------------------");
                                Log.d("XML Tag", "Complete insert Array");
                                GeoMarkItem item = new GeoMarkItem();
                                item.setAddr1(addr1);
                                item.setAddr2(addr2);
                                item.setAreaCode(areacode);
                                item.setCat1(cat1);
                                item.setCat2(cat2);
                                item.setCat3(cat3);
                                item.setContentID(contentid);
                                item.setContentTypeID(contenttypeid);
                                item.setFirstImage(firstimage);
                                item.setFirstImage2(firstimage2);
                                item.setMapX(mapx);
                                item.setMapY(mapy);
                                item.setModifiedtime(modified);
                                item.setReadCount(readcount);
                                item.setSigunguCode(sigungucode);
                                item.setTel(tel);
                                item.setTitle(title);
                                item.setZipCode(zipcode);
                                item.createDetailPostURL();
                                Geo_Markers.add(item);
                                break;

                            case "body" :
                                if (Geo_Markers.size() < 1){
                                    handler.sendEmptyMessage(1003);
                                }
                                break;
                            default:
                                Log.e("XML Parser", "Parser Error Check! : End Tag");
                                break;
                        }
                        break;
                    default:
                        Log.e("XML Parser", "Parser Error Check! : Whole Tag");
                        break;
                }
                eventType = xpp.next();
            }

            //시그널만 보넴
            handler.sendEmptyMessage(1001);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("XML Parser", "Parser Error Check! : getXML Method Error!");
            e.printStackTrace();
            handler.sendEmptyMessage(1002);
        }
    }

    private void setMarkersOnMap(){

        mMap.clear();

        for(int marker_count = 0 ; marker_count < Geo_Markers.size() ; marker_count ++){
            GeoMarkItem item = Geo_Markers.get(marker_count);

            //첫번째 마커 리스트가 먼저 맵의 줌 기준이 된다.
            if (marker_count == 0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(item.getMapY(), item.getMapX()), 14));
            }

            //카테고리마다 마터 색상 변경
            float bitmapDescriptorFactory = 0;
            switch (item.getContentTypeID()){
                case "12":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_AZURE;
                    break;
                case "14":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_BLUE;
                    break;
                case "15":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_CYAN;
                    break;
                case "25":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_GREEN;
                    break;
                case "28":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ORANGE;
                    break;
                case "32":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                    break;
                case "38":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_VIOLET;
                    break;
                case "39":
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_YELLOW;
                    break;
                default:
                    Log.e("SetMarkersOnMap", "Markers ContentType Error");
                    break;
            }

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(item.getMapY(), item.getMapX()))
                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                    .title((marker_count+1) +". " +item.getTitle())
                    .zIndex((float)marker_count)
            );

            //정보창 클릭 리스너
            //mMap.setOnInfoWindowClickListener(infoWindowClickListener);

            //마커 클릭 리스너
            this.mMap.setOnMarkerClickListener(markerClickListener);

        }

    }

    //정보창 클릭 리스너
    /*GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(getContext(), "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
        }
    };*/

    //마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            float markerId = marker.getZIndex();

            //선택한 타겟위치
            LatLng location = marker.getPosition();
            //oast.makeText(getContext(), "마커 클릭 Marker ID : "+markerId+"("+location.latitude+" "+location.longitude+")", Toast.LENGTH_SHORT).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

            Info_Recycle.scrollToPosition((int)markerId); //use to focus the item with index
            adapter.notifyDataSetChanged();

            return false;
        }
    };

}
