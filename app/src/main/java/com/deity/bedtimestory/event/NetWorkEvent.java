package com.deity.bedtimestory.event;

/**
 * 网络请求事件
 * Created by Deity on 2016/10/24.
 */

public enum  NetWorkEvent {
    REQUEST_NETWORK_DATA;


    private String destUrl;//目标地址
    private int currentPage;//附加信息

    public void setData(String destUrl,int currentPage){
        this.destUrl = destUrl;
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public String getDestUrl() {
        return destUrl;
    }
}
