package com.hchooney.qewqs.sns_version_170801.Geo_Items;

import java.io.Serializable;

/**
 * Created by qewqs on 2017-08-07.
 */

public class GeoMarkItem implements Serializable {
    /*
    *http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=인증키
    * &contentTypeId=12&contentId=129165&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&
    * firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y
    *
    * http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=인증키
    * &contentTypeId=28&contentId=557269&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&
    * firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y
    * 하위는 사진 없는거
    * http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=인증키
    * &contentTypeId=28&contentId=488663&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&
    * firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y
    *
    * 세부검색에 필요한 것은
    * 콘텐츠 타입 아이디, 콘텐츠 아이디 2가지이다.
    *
    * 이하 기본 마커에 필요한
    * 제목, 수정날짜, 사진, 주소, 우편주소, 좌표 경도위도, 읽은 수, 전화번호, 홈페이지
    * 단, 날짜는 수정날짜를 기본으로 한다. 또한 mLevel은 제외한다.
    * 이하
    * 1. 맨처음 글자는 대문자로 한다.
    * 2. 이름은 서버 제공 이름과 동일하게 한다.
    * 3. 없을 수도 있음을 한줄 주석으로 명시한다.
    *
    * 이를 이용한 다음 코드까지 초기화 되어야한다. 이 post URL은 자동으로 한다.
     */

    private String Title;   //위치 이름
    private String Addr1;   //기본 주소
    private String Addr2;   //세부 주소 - 없을 수 있다.
    private String Cat1;    //대분류
    private String Cat2;    //중분류
    private String Cat3;    //소분류
    private String SigunguCode; //시군구
    private String AreaCode;    //광역, 시
    private String ContentID;   //콘텐츠 아이디
    private String ContentTypeID;   //콘텐츠 타입 아이디
    private String Modifiedtime;    //최근 수정날짜
    private String FirstImage;  //첫 사진 URL - 없을 수 있다.
    private String FirstImage2;  //첫 사진 URL - 없을 수 있다.
    private double MapX;    //경도
    private double MapY;    //위도
    private String ReadCount;   //조회수
    private String ZipCode; //우편주소
    private String Tel; //전화번호

    private String DetailPostURLPublic;   //이 변수는 이후에 URL이 바로 전송되도록 POST URL을 저장하는 변수이다.
    private String DetailPostURLInfo;
    private String DetailPostURLRepeat;
    private String DetailPostURLImages;
    //이 변수는 자동으로 할당되게 한다. 이에 해당하는 메소드를 만든다.
    private String KeyCode;         //keyCode의 경우 유일하기에 바로 할당한다.

    public GeoMarkItem( ) {
        /*
        * 없을 수도 있는 것들은 기본 NONE으로 하여 차후 수정하기 쉽도록 한다.
        * 되도록이면 해당 생성자로 초기화하고 Set 메소드를 이용하여 데이터를 입력하는 방식을
        * 이용하도록 한다.
         */
        Title = "";
        Addr1 = "";
        Addr2 = "None";
        Cat1 = "";
        Cat2 = "";
        Cat3 = "";
        SigunguCode = "";
        AreaCode = "";
        ContentID = "";
        ContentTypeID = "";
        Modifiedtime = "";
        FirstImage = "None";
        FirstImage2 = "None";
        MapX = 0;
        MapY = 0;
        ReadCount = "None";
        ZipCode = "";
        Tel = "";
        DetailPostURLPublic = "";
        DetailPostURLInfo = "";
        DetailPostURLRepeat = "";
        DetailPostURLImages = "";
        KeyCode = "6tLZdNCgFtUUkC1aMEPPSDH5EqZB09HbJ9vEwO1DeRGItkpZQzyAxdTw2npenOfhIQdsklstTNt9qrj2RODhkQ%3D%3D";
    }

    public GeoMarkItem(String title, String addr1, String addr2, String cat1, String cat2, String cat3,
                                String sigunguCode, String areaCode, String contentID, String contentTypeID,
                                String modifiedtime, String firstImage, String firstImage2,
                                double mapX, double mapY, String readCount, String zipCode, String tel) {
        Title = title;
        Addr1 = addr1;
        Addr2 = addr2;
        Cat1 = cat1;
        Cat2 = cat2;
        Cat3 = cat3;
        SigunguCode = sigunguCode;
        AreaCode = areaCode;
        ContentID = contentID;
        ContentTypeID = contentTypeID;
        Modifiedtime = modifiedtime;
        FirstImage = firstImage;
        FirstImage2 = firstImage2;
        MapX = mapX;
        MapY = mapY;
        ReadCount = readCount;
        ZipCode = zipCode;
        Tel = tel;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddr1() {
        return Addr1;
    }

    public void setAddr1(String addr1) {
        Addr1 = addr1;
    }

    public String getAddr2() {
        return Addr2;
    }

    public void setAddr2(String addr2) {
        Addr2 = addr2;
    }

    public String getCat1() {
        return Cat1;
    }

    public void setCat1(String cat1) {
        Cat1 = cat1;
    }

    public String getCat2() {
        return Cat2;
    }

    public void setCat2(String cat2) {
        Cat2 = cat2;
    }

    public String getCat3() {
        return Cat3;
    }

    public void setCat3(String cat3) {
        Cat3 = cat3;
    }

    public String getSigunguCode() {
        return SigunguCode;
    }

    public void setSigunguCode(String sigunguCode) {
        SigunguCode = sigunguCode;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
    }

    public String getContentTypeID() {
        return ContentTypeID;
    }

    public void setContentTypeID(String contentTypeID) {
        ContentTypeID = contentTypeID;
    }

    public String getModifiedtime() {
        return Modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        Modifiedtime = modifiedtime;
    }

    public String getFirstImage() {
        return FirstImage;
    }

    public void setFirstImage(String firstImage) {
        FirstImage = firstImage;
    }

    public String getFirstImage2() {
        return FirstImage2;
    }

    public void setFirstImage2(String firstImage2) {
        FirstImage2 = firstImage2;
    }

    public double getMapX() {
        return MapX;
    }

    public void setMapX(double mapX) {
        MapX = mapX;
    }

    public double getMapY() {
        return MapY;
    }

    public void setMapY(double mapY) {
        MapY = mapY;
    }

    public String getReadCount() {
        return ReadCount;
    }

    public void setReadCount(String readCount) {
        ReadCount = readCount;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getDetailPostURLPublic() {
        return DetailPostURLPublic;
    }

    public void setDetailPostURLPublic(String detailPostURLPublic) {
        DetailPostURLPublic = detailPostURLPublic;
    }

    public String getDetailPostURLInfo() {
        return DetailPostURLInfo;
    }

    public void setDetailPostURLInfo(String detailPostURLInfo) {
        DetailPostURLInfo = detailPostURLInfo;
    }

    public String getDetailPostURLRepeat() {
        return DetailPostURLRepeat;
    }

    public void setDetailPostURLRepeat(String detailPostURLRepeat) {
        DetailPostURLRepeat = detailPostURLRepeat;
    }

    public String getDetailPostURLImages() {
        return DetailPostURLImages;
    }

    public void setDetailPostURLImages(String detailPostURLImages) {
        DetailPostURLImages = detailPostURLImages;
    }

    public void createDetailPostURL(){
        this.DetailPostURLPublic = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=" + this.KeyCode
                +"&contentTypeId=" + this.ContentTypeID + "&contentId=" + this.ContentID + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&"
                + "firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";

        this.DetailPostURLInfo = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey="
                + this.KeyCode + "&contentTypeId=" + this.ContentTypeID + "&contentId="+ this.ContentID + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";

        this.DetailPostURLRepeat = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey="
                +this.KeyCode + "&contentTypeId=" + this.ContentTypeID + "&contentId=" + this.ContentID + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";

        this.DetailPostURLImages = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey="
                + this.KeyCode + "&contentTypeId=" + this.getContentTypeID() + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId=" + this.ContentID + "&imageYN=Y";
    }

    public void clear(){
        Title = "";
        Addr1 = "";
        Addr2 = "None";
        Cat1 = "";
        Cat2 = "";
        Cat3 = "";
        SigunguCode = "";
        AreaCode = "";
        ContentID = "";
        ContentTypeID = "";
        Modifiedtime = "";
        FirstImage = "None";
        FirstImage2 = "None";
        MapX = 0;
        MapY = 0;
        ReadCount = "None";
        ZipCode = "";
        Tel = "";
    }
}
