package com.nirogo.www;

public class TimeTableList {


    private String TM_ID;
    private String TM_MER_ID;
    private String TM_DATE;
    private String TM_START_TM;
    private String TM_END_TM;
    private String TM_AMT;
    private String TM_STS;



    TimeTableList(String TM_ID, String TM_MER_ID, String TM_DATE, String TM_START_TM,
                 String TM_END_TM, String  TM_AMT,String  TM_STS) {
        this.TM_ID = TM_ID;
        this.TM_MER_ID = TM_MER_ID;
        this.TM_DATE = TM_DATE;
        this.TM_START_TM = TM_START_TM;
        this.TM_END_TM = TM_END_TM;
        this.TM_AMT = TM_AMT;
        this.TM_STS = TM_STS;
    }


    public String  getTM_ID() {
        return TM_ID;
    }

    public String getTM_MER_ID() {
        return TM_MER_ID;
    }

    public String getTM_DATE() {
        return TM_DATE;
    }

    public String getTM_START_TM() {
        return TM_START_TM;
    }


    public String getTM_END_TM() {
        return TM_END_TM;
    }

    public String getTM_AMT() {
        return TM_AMT;
    }

    public String getTM_STS() {
        return TM_STS;
    }
}