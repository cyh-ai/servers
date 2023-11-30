package com.integration.server.dto.redisCache;

import java.util.Date;

/**
 * @author cyh
 * 待补充
 */
public class RedisRegisterDTO {

    private Long id;

    private String business;

    private String subBusiness;

    private String businessName;

    private String subBusinessName;

    private Date updateDate;

    private String updateMan;

    private String delMatch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSubBusiness() {
        return subBusiness;
    }

    public void setSubBusiness(String subBusiness) {
        this.subBusiness = subBusiness;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSubBusinessName() {
        return subBusinessName;
    }

    public void setSubBusinessName(String subBusinessName) {
        this.subBusinessName = subBusinessName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public String getDelMatch() {
        return delMatch;
    }

    public void setDelMatch(String delMatch) {
        this.delMatch = delMatch;
    }
}
