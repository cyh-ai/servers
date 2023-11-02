package com.integration.server.dto.insuredForceCszRule;

import com.integration.core.anno.ExcelExport;

import java.util.Date;

/**
 * @author cyh
 * 指定配置数据模板
 */
public class InsuredForceCszRuleExportTemplate {

    private int rowNum;

    @ExcelExport(value = "分公司", required = true)
    private String fgsName;


    @ExcelExport(value = "中支公司", required = true)
    private String zzgsName;
    @ExcelExport(value = "年龄", required = true)
    private String maxAge;
    @ExcelExport(value = "校验反洗钱", required = true)
    private String isCheckAnti;
    @ExcelExport(value = "生效时间", required = true)
    private Date startTime;
    @ExcelExport(value = "截止时间", required = true)
    private Date endTime;
    @ExcelExport(value = "配置时间", required = true)
    private Date modifyTime;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getFgsName() {
        return fgsName;
    }

    public void setFgsName(String fgsName) {
        this.fgsName = fgsName;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
