package com.integration.server.dto.insuredForceCszRule;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.integration.core.util.JsonUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cyh
 * 配置数据DTO 字段参考InsuredForceCszRuleExportTemplate
 */
public class InsuredForceCszRuleDTO implements Serializable {

    private Long id;

    private String fgsCode;

    private String fgsName;

    private String zzgsCode;

    private String zzgsName;

    private String maxAge;

    private String isCheckAnti;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date insertTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFgsCode() {
        return fgsCode;
    }

    public void setFgsCode(String fgsCode) {
        this.fgsCode = fgsCode;
    }

    public String getFgsName() {
        return fgsName;
    }

    public void setFgsName(String fgsName) {
        this.fgsName = fgsName;
    }

    public String getZzgsCode() {
        return zzgsCode;
    }

    public void setZzgsCode(String zzgsCode) {
        this.zzgsCode = zzgsCode;
    }

    public String getZzgsName() {
        return zzgsName;
    }

    public void setZzgsName(String zzgsName) {
        this.zzgsName = zzgsName;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getIsCheckAnti() {
        return isCheckAnti;
    }

    public void setIsCheckAnti(String isCheckAnti) {
        this.isCheckAnti = isCheckAnti;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
