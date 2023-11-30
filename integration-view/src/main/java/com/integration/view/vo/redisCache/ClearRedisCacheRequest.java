package com.integration.view.vo.redisCache;

/**
 * @author cyh
 * 清空/刷新缓存请求类
 */
public class ClearRedisCacheRequest {

    /**
     * 业务大类
     */
    private String categoryKey;

    /**
     * 业务小类
     */
    private String nameKey;

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }
}
