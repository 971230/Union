package zte.net.ecsord.params.taobao.vo;

import java.io.Serializable;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单详情查询接口
 * 
 */
public class LogisticsTAOBAOVirtual implements Serializable {

	private static final long serialVersionUID = 6252569674462156409L;

	@ZteSoftCommentAnnotationParam(name="淘宝交易ID",type="String",isNecessary="Y",desc="tid：持最小值为：1000")
	private String tid;

	@ZteSoftCommentAnnotationParam(name="拆单子订单列表",type="String",isNecessary="N",desc="sub_tid：对应的数据是：子订单号的列表。可以不传，但是如果传了则必须符合传递的规则。子订单必须是操作的物流订单的子订单的真子集！")
	private String sub_tid;

	@ZteSoftCommentAnnotationParam(name="表明是否是拆单",type="String",isNecessary="N",desc="is_split：默认值0，1表示拆单")
	private String is_split;

	@ZteSoftCommentAnnotationParam(name="运单号",type="String",isNecessary="N",desc="out_sid：具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；")
	private String out_sid;

	@ZteSoftCommentAnnotationParam(name="物流公司代码",type="String",isNecessary="Y",desc="company_code：如“POST”就代表中国邮政,“ZJS”就代表宅急送.调用 taobao.logistics.companies.get 获取。 如果是货到付款订单，选择的物流公司必须支持货到付款发货方式")
	private String company_code;

	@ZteSoftCommentAnnotationParam(name="卖家联系人地址库ID",type="String",isNecessary="N",desc="sender_id：可以通过taobao.logistics.address.search接口查询到地址库ID。如果为空，取的卖家的默认取货地址")
	private String sender_id;

	@ZteSoftCommentAnnotationParam(name="卖家联系人地址库ID",type="String",isNecessary="N",desc="cancel_id：可以通过taobao.logistics.address.search接口查询到地址库ID。如果为空，取的卖家的默认退货地址")
	private String cancel_id;

	@ZteSoftCommentAnnotationParam(name="特征",type="String",isNecessary="N",desc="feature：特征")
	private String feature;

	@ZteSoftCommentAnnotationParam(name="商家的IP地址",type="String",isNecessary="N",desc="seller_ip：商家的IP地址")
	private String seller_ip;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSub_tid() {
		return sub_tid;
	}

	public void setSub_tid(String sub_tid) {
		this.sub_tid = sub_tid;
	}

	public String getIs_split() {
		return is_split;
	}

	public void setIs_split(String is_split) {
		this.is_split = is_split;
	}

	public String getOut_sid() {
		return out_sid;
	}

	public void setOut_sid(String out_sid) {
		this.out_sid = out_sid;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getCancel_id() {
		return cancel_id;
	}

	public void setCancel_id(String cancel_id) {
		this.cancel_id = cancel_id;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getSeller_ip() {
		return seller_ip;
	}

	public void setSeller_ip(String seller_ip) {
		this.seller_ip = seller_ip;
	}


}
