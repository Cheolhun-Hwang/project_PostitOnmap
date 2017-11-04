package com.hchooney.qewqs.sns_version_170801.Post_Items;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qewqs on 2017-07-13.
 */

public class ListInfomationItem implements Serializable {
    //ItemNumber
    private String ItemNumber;     //Who + __ + 경도 + __ + 위도  <- 해당 포스트는 지역에 하나만 쓸수 있음, 나머지는 수정.
    //위치정보
    private Double lat;            //위도
    private Double lon;            //경도
    //주소정보
    private String City;           //카테고리 검색용
    private String LocalCity;      //카테고리 검색용
    private String DetailCity;     //주소 표시용
    private String Where;          //장소 이름 - Title이 된다.
    //리스트
    private String PostItDesign;      //포스트잇배경번호
    private String Context;        //내용
    private String Who;            //작성자
    private String When;           //날짜
    //해시태그
    private ArrayList<String> HashTag;        //해시태그;  #정보, #정보#, ..
    //평점
    private double LikeRate;       //평점
    //분야
    private String GenreOne;
    private String GenreTwo;
    private String GenreThree;
    //사진
    private String photoURL;

    public ListInfomationItem( ) {
        ItemNumber = "";
        this.lat = 0.0;
        this.lon = 0.0;
        City = "";
        LocalCity = "";
        DetailCity = "";
        Where = "";
        PostItDesign = "";
        Context = "";
        Who = "";
        When = "";
        HashTag = null;
        LikeRate = 0.0;
        this.GenreOne = "";
        this.GenreTwo = "";
        this.GenreThree = "";
        this.photoURL = "NONE";
    }
    public ListInfomationItem(String itemNumber, Double lat, Double lon, String city, String localCity,
                              String detailCity, String where, String postItDesign, String context, String who,
                              String when, ArrayList<String> hashTag, double likeRate,
                              String genreOne, String genreTwo, String genreThree, String pho) {
        ItemNumber = itemNumber;
        this.lat = lat;
        this.lon = lon;
        City = city;
        LocalCity = localCity;
        DetailCity = detailCity;
        Where = where;
        PostItDesign = postItDesign;
        Context = context;
        Who = who;
        When = when;
        HashTag = hashTag;
        LikeRate = likeRate;
        this.GenreOne = genreOne;
        this.GenreTwo = genreTwo;
        this.GenreThree = genreThree;
        this.photoURL = pho;
    }

    public String getGenreOne() {
        return GenreOne;
    }

    public void setGenreOne(String genreOne) {
        GenreOne = genreOne;
    }

    public String getGenreTwo() {
        return GenreTwo;
    }

    public void setGenreTwo(String genreTwo) {
        GenreTwo = genreTwo;
    }

    public String getGenreThree() {
        return GenreThree;
    }

    public void setGenreThree(String genreThree) {
        GenreThree = genreThree;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        ItemNumber = itemNumber;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLocalCity() {
        return LocalCity;
    }

    public void setLocalCity(String localCity) {
        LocalCity = localCity;
    }

    public String getDetailCity() {
        return DetailCity;
    }

    public void setDetailCity(String detailCity) {
        DetailCity = detailCity;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    public String getPostItDesign() {
        return PostItDesign;
    }

    public void setPostItDesign(String postItDesign) {
        PostItDesign = postItDesign;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getWho() {
        return Who;
    }

    public void setWho(String who) {
        Who = who;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public ArrayList<String> getHashTag() {
        return HashTag;
    }

    public void setHashTag(ArrayList<String> hashTag) {
        HashTag = hashTag;
    }

    public double getLikeRate() {
        return LikeRate;
    }

    public void setLikeRate(double likeRate) {
        LikeRate = likeRate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
