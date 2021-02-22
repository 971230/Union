package com.ztesoft.net.eop.resource.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-04 10:24
 * To change this template use File | Settings | File Templates.
 *
 *
 */
public class AdminUserEnterpriseRel implements Serializable {
    private String user_id;//

    private String enterprise_id;//企业编码

    private String type;//个人F 企业T

    private String source_from;//来源

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource_from() {
        return source_from;
    }

    public void setSource_from(String source_from) {
        this.source_from = source_from;
    }
}
