package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

public class ChannelConvertVO implements Serializable{
    private String zb_channel_id;
    private String zb_developer;
    private String sf_channel_id;
    private String sf_channel_name;//发展点名称
    private String sf_developer;
    private String sf_developer_name;//发展人名称
    public String getZb_channel_id() {
        return zb_channel_id;
    }
    public void setZb_channel_id(String zb_channel_id) {
        this.zb_channel_id = zb_channel_id;
    }
    public String getZb_developer() {
        return zb_developer;
    }
    public void setZb_developer(String zb_developer) {
        this.zb_developer = zb_developer;
    }
    public String getSf_channel_id() {
        return sf_channel_id;
    }
    public void setSf_channel_id(String sf_channel_id) {
        this.sf_channel_id = sf_channel_id;
    }
    public String getSf_developer() {
        return sf_developer;
    }
    public void setSf_developer(String sf_developer) {
        this.sf_developer = sf_developer;
    }
    public String getSf_developer_name() {
        return sf_developer_name;
    }
    public void setSf_developer_name(String sf_developer_name) {
        this.sf_developer_name = sf_developer_name;
    }
    public String getSf_channel_name() {
        return sf_channel_name;
    }
    public void setSf_channel_name(String sf_channel_name) {
        this.sf_channel_name = sf_channel_name;
    }
    
}

