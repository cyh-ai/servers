package com.integration.view.vo;

import java.util.List;

public class KindListResponse {

    private String totalNum;

    /**
     * 险种列表
     */
    private List<InsElement> insuranceList;

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<InsElement> getInsuranceList() {
        return insuranceList;
    }

    public void setInsuranceList(List<InsElement> insuranceList) {
        this.insuranceList = insuranceList;
    }
}
