package com.integration.server.BaseEntitys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8548784565879765191L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间：yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "insert_time", nullable = false)
    private Date insertTime;

    /**
     * 创建人名称
     */
    @Column(name = "insert_man", length = 100, nullable = false)
    private String insertMan;

    /**
     * 修改时间：yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改人名称
     */
    @Column(name = "update_man", length = 100)
    private String updateMan;

    /**
     * 状态：1开启，0禁用
     */
    @Column(nullable = false)
    private Boolean state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getInsertMan() {
        return insertMan;
    }

    public void setInsertMan(String insertMan) {
        this.insertMan = insertMan;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}