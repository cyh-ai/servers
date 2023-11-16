package com.integration.core.util;

import java.util.Date;

/**
 * @author cyh
 * 待补充
 */
public class ExternalRequestInfoPrint {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 事件名称
     */
    private String eventName;
    /**
     * 日志ID
     */
    private String eventId;
    /**
     * 发生时间
     */
    private Date occurTime;
    /**
     * 源地址（请求方IP）
     */
    private String srcAddress;
    /**
     * 目标地址（被请求方IP）
     */
    private String dstAddress;
    /**
     * 当前URL路径
     */
    private String cQueryUrl;
    /**
     * 结果 200-成功；403-失败
     */
    private String result;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    public String getcQueryUrl() {
        return cQueryUrl;
    }

    public void setcQueryUrl(String cQueryUrl) {
        this.cQueryUrl = cQueryUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
