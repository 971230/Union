/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

/**
 * @author songqi
 * @see 支付信息
 */
public class PayInfo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "N", desc = "支付方式")
	private String payType;// 支付方式 参考附录支付方式说明
	@ZteSoftCommentAnnotationParam(name = "支付机构名称", type = "String", isNecessary = "N", desc = "支付机构名称")
	private String payOrg;// 支付机构名称
	@ZteSoftCommentAnnotationParam(name = "支付账号", type = "String", isNecessary = "N", desc = "支付账号")
	private String payNum;// 支付账号

	public String getPayType() {
		this.payType = "10";
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayOrg() {
		payOrg = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_NAME);
		if (StringUtil.isEmpty(payOrg)) {
			return null;
		}
		return payOrg;
	}

	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
