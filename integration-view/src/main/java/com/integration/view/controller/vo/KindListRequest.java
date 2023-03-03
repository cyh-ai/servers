package com.integration.view.controller.vo;

public class KindListRequest {

    private String cyh;

    private String pageIndex;
    private String pageSize;

    public String getCyh() {
        return cyh;
    }

    public void setCyh(String cyh) {
        this.cyh = cyh;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "";
    }
}
