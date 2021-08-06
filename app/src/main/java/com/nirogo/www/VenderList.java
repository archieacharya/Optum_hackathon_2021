package com.nirogo.www;

public class VenderList {



    private String merchant_users_id;
    private String shop_name;
    private String shop_area;
    private String distance;
    private String mer_lat;
    private String mer_lon;
    private String img;



    VenderList(String merchant_users_id, String shop_name, String shop_area, String distance, String mer_lat, String mer_lon, String img) {

        this.merchant_users_id = merchant_users_id;
        this.shop_name = shop_name;
        this.shop_area = shop_area;
        this.distance = distance;
        this.mer_lat = mer_lat;
        this.mer_lon = mer_lon;
        this.img = img;
    }


    public String getMerchant_users_id() {
        return merchant_users_id;
    }

    public String getShop_name() {
        return shop_name;
    }


    public String getShop_area() {
        return shop_area;
    }

    public String getDistance() {
        return distance;
    }

    public String getMer_lat() {
        return mer_lat;
    }

    public String getMer_lon() {
        return mer_lon;
    }


    public String getImg() {
        return img;
    }

}

