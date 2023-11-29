package com.integration.server.eneity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "t_redis_register")
public class RedisRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "business",length = 100,nullable = false)
    private String business;
    @Column(name = "sub_business",length = 100,nullable = false)
    private String subBusiness;
    @Column(name = "business_name",length = 100,nullable = false)
    private String businessName;
    @Column(name = "sub_business_name",length = 100,nullable = false)
    private String subBusinessName;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "update_man")
    private String updateMan;
    @Column(name = "del_match")
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
