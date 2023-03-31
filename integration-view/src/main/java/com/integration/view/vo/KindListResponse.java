package com.integration.view.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "险种列表响应体")
public class KindListResponse {

    @ApiModelProperty(value = "页数")
    private String totalNum;

    /**
     * 险种列表
     */
    @ApiModelProperty(value = "险种集合")
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
