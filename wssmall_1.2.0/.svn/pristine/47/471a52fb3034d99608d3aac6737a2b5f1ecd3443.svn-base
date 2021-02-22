package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.LbsRechargeResp;
import com.ztesoft.remote.basic.utils.BasicConst;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 11:08
 * To change this template use File | Settings | File Templates.
 */
public class LbsRechargeReq extends ZteRequest<LbsRechargeResp> {

    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
    private String acc_nbr;

    @ZteSoftCommentAnnotationParam(name="商户系统唯一订单号",type="String",isNecessary="Y",desc="商户系统唯一订单号")
    private String ord_no;
    
    @ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间")
    private String ord_time;
    
    @ZteSoftCommentAnnotationParam(name="商户备注信息",type="String",isNecessary="Y",desc="商户备注信息")
    private String ord_att;
    
    @ZteSoftCommentAnnotationParam(name="商户充值金额，单位：分",type="String",isNecessary="Y",desc="商户充值金额，单位：分")
    private String ord_amount;
    
    @ZteSoftCommentAnnotationParam(name="充值类型",type="String",isNecessary="Y",desc="1：充话费；2：充钱包")
    private String charge_type;
    
    @ZteSoftCommentAnnotationParam(name="商户充值结果异步通知URL",type="String",isNecessary="Y",desc="商户充值结果异步通知URL")
    private String notify_url;
    
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(充话费)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
    public String getOrd_no() {
		return ord_no;
	}

	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}

	public String getOrd_time() {
		return ord_time;
	}

	public void setOrd_time(String ord_time) {
		this.ord_time = ord_time;
	}

	public String getOrd_att() {
		return ord_att;
	}

	public void setOrd_att(String ord_att) {
		this.ord_att = ord_att;
	}

	public String getOrd_amount() {
		return ord_amount;
	}

	public void setOrd_amount(String ord_amount) {
		this.ord_amount = ord_amount;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

    @Override
    public void check() throws ApiRuleException {
        
    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"lbsRecharge";
    }
}
