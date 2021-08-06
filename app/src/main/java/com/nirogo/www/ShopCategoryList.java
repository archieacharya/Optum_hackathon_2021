package com.nirogo.www;

public class ShopCategoryList {
    private int SHP_ID;
    private String SHP_CAT;
    private String SHP_IMG;
    private String SHP_DESC;



    ShopCategoryList(int SHP_ID, String SHP_CAT, String SHP_IMG, String SHP_DESC) {
        this.SHP_ID = SHP_ID;
        this.SHP_CAT = SHP_CAT;
        this.SHP_IMG = SHP_IMG;
        this.SHP_DESC = SHP_DESC;
    }

    public int getSHP_ID() {
        return SHP_ID;
    }

    public String getSHP_CAT() {
        return SHP_CAT;
    }

    public String getSHP_IMG() {
        return SHP_IMG;
    }

    public String getSHP_DESC() {
        return SHP_DESC;
    }

}


