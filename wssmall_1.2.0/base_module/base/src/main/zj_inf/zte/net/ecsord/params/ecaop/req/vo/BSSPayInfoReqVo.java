/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 *
 */
public class BSSPayInfoReqVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683630065224166537L;
	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="PayType：支付方式  参考附录支付方式说明 现金，不填写支付机构和支付账号")
	private String PayType;
	@ZteSoftCommentAnnotationParam(name="支付机构名称",type="String",isNecessary="N",desc="PayOrg：支付机构名称")
	private String PayOrg;		
	@ZteSoftCommentAnnotationParam(name="支付账号",type="String",isNecessary="N",desc="PayNum：支付账号")
	private String PayNum;
	@ZteSoftCommentAnnotationParam(name="信用卡分期期数",type="String",isNecessary="N",desc="PayPeriods：信用卡分期期数号")
	private String PayPeriods;
	public String getPayType() {
		return PayType;
	}
	public void setPayType(String payType) {
		PayType = payType;
	}
	public String getPayOrg() {
		return PayOrg;
	}
	public void setPayOrg(String payOrg) {
		PayOrg = payOrg;
	}
	public String getPayNum() {
		return PayNum;
	}
	public void setPayNum(String payNum) {
		PayNum = payNum;
	}
	public String getPayPeriods() {
		return PayPeriods;
	}
	public void setPayPeriods(String payPeriods) {
		PayPeriods = payPeriods;
	}
	
	
	
}
