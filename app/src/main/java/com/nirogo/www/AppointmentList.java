package com.nirogo.www;

import org.w3c.dom.CDATASection;

public class AppointmentList {



    private int id;
    private String ord_id;
    private  String start_time;
    private String total_price;
    private  String end_time;
    private  String shop_area;
    private  String shop_name;
    private String date;
    private String lat;
    private String lon;
    private String whatsapp;
    private String call_number;
    private String img;



    AppointmentList(int id, String ord_id, String start_time, String total_price, String end_time,
                    String shop_area, String shop_name, String date,
                    String lat, String lon, String whatsapp, String call_number, String img) {

        this.id = id;
        this.ord_id = ord_id;
        this.total_price = total_price;
        this.shop_area = shop_area;
        this.shop_name = shop_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
        this.lat= lat;
        this.lon= lon;
        this.whatsapp = whatsapp;
        this.call_number = call_number;
        this.img = img;

    }



    public int getId() {
        return id;
    }



    public String getOrd_id() {
        return ord_id;
    }



    public String getTotal_price() {
        return total_price;
    }


    public String getShop_area() {
        return shop_area;
    }

    public String getShop_name() {
        return shop_name;
    }





    public String getStart_time() {
        return start_time;
    }
    public String getEnd_time() {
        return end_time;
    }

    public String getDate() {
        return date;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public String getCall_number() {
        return call_number;
    }
    public String getImg() {
        return img;
    }



}

