/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.jce.Random;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import utils.UUIDUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

/**
 * @author songqi
 * @see 新客户信息
 */
public class CustomerRemarkVO implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "N", desc = "内部订单号 ")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "客户备注ID ", type = "String", isNecessary = "Y", desc = "客户备注ID ")
	private String customerRemarkId;// 客户备注ID
	@ZteSoftCommentAnnotationParam(name = "客户备注信息 ", type = "String", isNecessary = "Y", desc = "客户备注信息 ")
	private String customerRemarkValue;// 客户备注信息 //买家留言

	public String getCustomerRemarkId() {
		// if (StringUtils.isBlank(customerRemarkId)) return "1";
		customerRemarkId = "1";
		return customerRemarkId;
	}

	public void setCustomerRemarkId(String customerRemarkId) {
		this.customerRemarkId = customerRemarkId;
	}

	public String getCustomerRemarkValue() {
		// if (StringUtils.isBlank(customerRemarkValue)) return "备注为空"; //买家留言
//		customerRemarkValue = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
//				AttrConsts.BUYER_MESSAGE);
//		if (StringUtils.isBlank(customerRemarkValue))
//			return "备注为空";2017年11月15日 22:26:50鲍小亮确认
		customerRemarkValue = "备注为空";
		return customerRemarkValue;
	}

	public void setCustomerRemarkValue(String customerRemarkValue) {
		this.customerRemarkValue = customerRemarkValue;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
