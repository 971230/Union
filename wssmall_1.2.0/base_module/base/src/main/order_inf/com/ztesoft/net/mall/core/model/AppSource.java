package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-23 16:10
 * To change this template use File | Settings | File Templates.
 */
public class AppSource implements Serializable {
    private String appKey;//app编码

    private String source;//FJ  OTT  WT 来源

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
