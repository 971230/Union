package com.ztesoft.net.mall.core.model;
public class BusinessCatId implements java.io.Serializable{

    
    private static final long serialVersionUID = 1L;
    private String busi_cat_id;// 业务类型ID
    private String busi_cat_name;//业务类型名称
    private String goods_cat_id;//商品类型
    private String order_from; //订单来源
    private String source_from;//数据来源
    private String oper_mode;//操作模式
    private String is_send_wms;//是否WMS发送
    private String send_zb;//是否发送总部
    private String flow_id;//流程ID
    private String is_aop;//是否aop
    //若不上线代码回退
    private String match_type;
    public String getBusi_cat_id() {
        return busi_cat_id;
    }
    public void setBusi_cat_id(String busi_cat_id) {
        this.busi_cat_id = busi_cat_id;
    }
    public String getBusi_cat_name() {
        return busi_cat_name;
    }
    public void setBusi_cat_name(String busi_cat_name) {
        this.busi_cat_name = busi_cat_name;
    }
    public String getGoods_cat_id() {
        return goods_cat_id;
    }
    public void setGoods_cat_id(String goods_cat_id) {
        this.goods_cat_id = goods_cat_id;
    }
    public String getOrder_from() {
        return order_from;
    }
    public void setOrder_from(String order_from) {
        this.order_from = order_from;
    }
    public String getSource_from() {
        return source_from;
    }
    public void setSource_from(String source_from) {
        this.source_from = source_from;
    }
    public String getOper_mode() {
        return oper_mode;
    }
    public void setOper_mode(String oper_mode) {
        this.oper_mode = oper_mode;
    }
    public String getIs_send_wms() {
        return is_send_wms;
    }
    public void setIs_send_wms(String is_send_wms) {
        this.is_send_wms = is_send_wms;
    }
    public String getFlow_id() {
        return flow_id;
    }
    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id;
    }
    public String getIs_aop() {
        return is_aop;
    }
    public void setIs_aop(String is_aop) {
        this.is_aop = is_aop;
    }
    public String getSend_zb() {
        return send_zb;
    }
    public void setSend_zb(String send_zb) {
        this.send_zb = send_zb;
    }
    public String getMatch_type() {
        return match_type;
    }
    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }
    
}
