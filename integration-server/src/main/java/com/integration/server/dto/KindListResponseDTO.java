package com.integration.server.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author cyh
 * 待补充类说明
 */
@ApiModel(description = "险种响应DTO对象")
public class KindListResponseDTO {

    @ApiModelProperty(value = "页数")
    private String totalNum;

    @ApiModelProperty(value = "险种集合DTO")
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
