package com.integration.view.controller.dto;



import java.util.List;

public class KindListResponseDTO {

    private String totalNum;

    private List<InsElementDTO> insuranceDTOList;

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<InsElementDTO> getInsuranceDTOList() {
        return insuranceDTOList;
    }

    public void setInsuranceDTOList(List<InsElementDTO> insuranceDTOList) {
        this.insuranceDTOList = insuranceDTOList;
    }
}
