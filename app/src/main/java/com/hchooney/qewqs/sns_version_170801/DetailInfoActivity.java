package com.hchooney.qewqs.sns_version_170801;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hchooney.qewqs.sns_version_170801.AnyItems.MyDialogFragment;
import com.hchooney.qewqs.sns_version_170801.Geo_Items.GeoMarkItem;
import com.hchooney.qewqs.sns_version_170801.Recycle_items.DetailImageAdater;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DetailInfoActivity extends AppCompatActivity {
    private static final String TAG = "DetailInfoActivity";

    private GeoMarkItem geoMarkItem;

    //상단 레잉아웃
    private ImageButton BackBTN;
    private TextView Detail_Title;

    //이미지 레이아웃
    private RecyclerView recyclerView;
    private DetailImageAdater adapter;


    private ArrayList<String> ImageURLList;

    //임시

    private TextView URL_ONE;
    private TextView URL_TWO;
    private TextView URL_THREE;

    String result = "";

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1001){
                Log.d(TAG, "Complete");
            }else if(msg.what == 1002){
                Toast.makeText(getApplicationContext(), "죄송합니다. 다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1003){
                Toast.makeText(getApplicationContext(), "총 0건이 검색되었습니다.", Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1004){
                recyclerView.setAdapter(adapter);
            }else if(msg.what == 2001){

                String addrs = "";
                if(geoMarkItem.getAddr2().equals("None")){
                    addrs = geoMarkItem.getAddr1();
                }else{
                    addrs = geoMarkItem.getAddr1() + " " + geoMarkItem.getAddr2();
                }
                URL_ONE.setText(
                        "공통소개" +
                                "\n주소 : " + addrs +
                                "\n우편주소 : " + geoMarkItem.getZipCode() +
                                "\n최근수정날짜 : " + geoMarkItem.getModifiedtime() +
                                "\n전화번호 : " + geoMarkItem.getTel() +
                                result
                );
            }else if(msg.what== 2002){
                URL_TWO.setText(result);
            }else{
                Log.d("Handler", "Handler msg.what check!!");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        if(getIntent() != null){
            geoMarkItem = (GeoMarkItem) getIntent().getSerializableExtra("information");
        }else{
            Log.e(TAG, "Intent Information error");
            Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }

        init();

        set();

        event();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getXmlData(geoMarkItem.getDetailPostURLPublic(), geoMarkItem.getDetailPostURLInfo()
                        , geoMarkItem.getDetailPostURLImages());
            }
        }).start();


    }

    private void init(){
        ImageURLList = new ArrayList<String>();

        BackBTN = (ImageButton) findViewById(R.id.Detail_Back_ImageButton);
        Detail_Title = (TextView) findViewById(R.id.Detail_Title);

        recyclerView= (RecyclerView) findViewById(R.id.DetailRecycle);
        adapter = new DetailImageAdater(ImageURLList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        URL_ONE = (TextView) findViewById(R.id.Detail_URL_Public);
        URL_TWO = (TextView) findViewById(R.id.Detail_URL_Info);
        URL_THREE = (TextView) findViewById(R.id.Detail_URL_Repeat);
     }

    private void set(){
        Detail_Title.setText(geoMarkItem.getTitle());
    }

    private void event(){
        BackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getXmlData(String url_one, String url_two, String url_four) {
        parseUrlOne(url_one);
        parseUrlTwo(url_two);
        parseUrlFour(url_four);
    }

    private void parseUrlOne(String url_base){
        Log.d(TAG, "Parser URL One");
        try {
            URL url = new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();  //태그값 초기화
            int eventType = xpp.getEventType();     //태그를통한 이벤트 확인

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:       //시작 태그확인 ex) <Head>, <Body>, <div>
                        tag = xpp.getName();
                        switch (tag){
                            case "body":
                                result = "";
                                break;
                            case "homepage":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result += ("\n홈페이지" + xpp.getText().toString());
                                break;
                            case "overview":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result += ("\n개요" + xpp.getText().toString());
                                break;
                            case "tel":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result += ("\n추가전화" + xpp.getText().toString());
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
                                break;

                            case "body" :
                                //시그널만 보넴
                                handler.sendEmptyMessage(2001);
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("XML Parser", "Parser Error Check! : getXML Method Error!");
            e.printStackTrace();
            handler.sendEmptyMessage(1002);
        }
    }

    private void parseUrlTwo(String url_base){
        Log.d(TAG, "Parser URL Two");
        try {
            URL url = new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();  //태그값 초기화
            int eventType = xpp.getEventType();     //태그를통한 이벤트 확인

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:       //시작 태그확인 ex) <Head>, <Body>, <div>
                        tag = xpp.getName();
                        switch (tag){
                            case "body":
                                result = "";
                                break;
                            case "chkcreditcardfood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n신용카드 정보 : " + xpp.getText().toString());
                                break;
                            case "firstmenu":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n대표메뉴 : " + xpp.getText().toString());
                                break;
                            case "infocenterfood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n전화 : " + xpp.getText().toString());
                                break;
                            case "opentimefood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n영업시간 : " + xpp.getText().toString());
                                break;
                            case "packing":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n포장 : " + xpp.getText().toString());
                                break;
                            case "parkingfood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차 시설 : " + xpp.getText().toString());
                                break;
                            case "reservationfood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n예약 안내 : " + xpp.getText().toString());
                                break;
                            case "restdatefood":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n쉬는 날 : " + xpp.getText().toString());
                                break;
                            case "smoking":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n금연/흡연 : " + xpp.getText().toString());
                                break;
                            case "treatmenu":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n취급 메뉴 : " + xpp.getText().toString());
                                break;
                            case "chkbabycarriageshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n유모차대여유무 : " + xpp.getText().toString());
                                break;
                            case "chkcreditcardshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n신용카드 가능 여부 : " + xpp.getText().toString());
                                break;
                            case "chkpetshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n애완동물 동반가능여부 : " + xpp.getText().toString());
                                break;
                            case "fairday":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n장 열리는 날 : " + xpp.getText().toString());
                                break;
                            case "infocentershopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n문의 및 안내 : " + xpp.getText().toString());
                                break;
                            case "saleitem":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n판매품목 : " + xpp.getText().toString());
                                break;
                            case "opentime":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n영업시간 : " + xpp.getText().toString());
                                break;
                            case "parkingshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차시설 : " + xpp.getText().toString());
                                break;
                            case "restdateshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n쉬는날 : " + xpp.getText().toString());
                                break;
                            case "restroom":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n화장실 : " + xpp.getText().toString());
                                break;
                            case "scaleshopping":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n규모 : " + xpp.getText().toString());
                                break;
                            case "shopguide":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n매장안내 : " + xpp.getText().toString());
                                break;
                            case "infocenterleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n문의 및 안내 : " + xpp.getText().toString());
                                break;
                            case "parkingleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차 시설 : " + xpp.getText().toString());
                                break;
                            case "restdateleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n이용시간 : " + xpp.getText().toString());
                                break;
                            case "usetimeleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n쉬는날 : " + xpp.getText().toString());
                                break;
                            case "reservation":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n예약안내 : " + xpp.getText().toString());
                                break;
                            case "accomcountleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n수용인원  : " + xpp.getText().toString());
                                break;
                            case "expagerangeleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n체험가능 연령  : " + xpp.getText().toString());
                                break;
                            case "openperiod":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n체험가능 연령  : " + xpp.getText().toString());
                                break;
                            case "parkingfeeleports":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차요금  : " + xpp.getText().toString());
                                break;
                            case "expguide":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n체험안내  : " + xpp.getText().toString());
                                break;
                            case "parking":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차시설  : " + xpp.getText().toString());
                                break;
                            case "restdate":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n쉬는날  : " + xpp.getText().toString());
                                break;
                            case "usetime":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n이용시간  : " + xpp.getText().toString());
                                break;
                            case "accomcountculture":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n규모   : " + xpp.getText().toString());
                                break;
                            case "discountinfo":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n할인정보   : " + xpp.getText().toString());
                                break;
                            case "infocenterculture":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n문의 및 안내   : " + xpp.getText().toString());
                                break;
                            case "parkingculture":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차시설   : " + xpp.getText().toString());
                                break;
                            case "restdateculture":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n쉬는날   : " + xpp.getText().toString());
                                break;
                            case "usetimeculture":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n이용시간   : " + xpp.getText().toString());
                                break;
                            case "usefee":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n이용요금   : " + xpp.getText().toString());
                                break;
                            case "parkingfee":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주차요금   : " + xpp.getText().toString());
                                break;
                            case "agelimit":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n관람가능연령  : " + xpp.getText().toString());
                                break;
                            case "eventenddate":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n행사종료일   : " + xpp.getText().toString());
                                break;
                            case "eventstartdate":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n행사시작일   : " + xpp.getText().toString());
                                break;
                            case "eventplace":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n행사장소   : " + xpp.getText().toString());
                                break;
                            case "playtime":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n공연시간    : " + xpp.getText().toString());
                                break;
                            case "program":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n프로그램     : " + xpp.getText().toString());
                                break;
                            case "spendtimefestival":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n관람소요시간      : " + xpp.getText().toString());
                                break;
                            case "sponsor1":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주최자       : " + xpp.getText().toString());
                                break;
                            case "sponsor1tel":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주최자 연락처 : " + xpp.getText().toString());
                                break;
                            case "sponsor2":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주관사 : " + xpp.getText().toString());
                                break;
                            case "sponsor2tel":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n주관사 연락처 : " + xpp.getText().toString());
                                break;
                            case "usetimefestival":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n이용요금  : " + xpp.getText().toString());
                                break;
                            case "subevent":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n부대행사  : " + xpp.getText().toString());
                                break;
                            case "bookingplace":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                result+= ("\n예매처  : " + xpp.getText().toString());
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
                                break;

                            case "body" :

                                //시그널만 보넴
                                handler.sendEmptyMessage(2002);
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("XML Parser", "Parser Error Check! : getXML Method Error!");
            e.printStackTrace();
            handler.sendEmptyMessage(1002);
        }
    }

    private void parseUrlFour(String url_base){
        Log.d(TAG, "Parser URL FOUR");
        try {
            URL url = new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();  //태그값 초기화
            int eventType = xpp.getEventType();     //태그를통한 이벤트 확인

            String image = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:       //시작 태그확인 ex) <Head>, <Body>, <div>
                        tag = xpp.getName();
                        switch (tag){
                            case "item":
                                image = "";
                                break;
                            case "originimgurl":
                                xpp.next();
                                Log.d("XML Tag", xpp.getText().toString());
                                image= xpp.getText().toString();
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
                                ImageURLList.add(image);
                                break;

                            case "body" :
                                if (ImageURLList.size() < 1){
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
            handler.sendEmptyMessage(1004);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("XML Parser", "Parser Error Check! : getXML Method Error!");
            e.printStackTrace();
            handler.sendEmptyMessage(1002);
        }
    }
}
