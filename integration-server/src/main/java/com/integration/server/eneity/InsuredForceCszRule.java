package com.integration.server.eneity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author cyh
 * 年龄配置表 参考数据库字段说明
 */
@Entity
@Table(name = "t_insured_force_csz_rule")
public class InsuredForceCszRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fgs_code")
    private String fgsCode;
    @Column(name = "fgs_name")
    private String fgsName;
    @Column(name = "zzgs_code")
    private String zzgsCode;
    @Column(name = "zzgs_name")
    private String zzgsName;
    @Column(name = "max_age")
    private String maxAge;
    @Column(name = "is_check_anti")
    private String isCheckAnti;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "modify_time")
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
}
