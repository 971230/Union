/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 *
 */
public class OpenDealApplyPayInfoVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="payType：支付方式  参考附录支付方式说明 现金，不填写支付机构和支付账号")
	private String payType;
	@ZteSoftCommentAnnotationParam(name="支付机构名称",type="String",isNecessary="N",desc="payOrg：支付机构名称")
	private String payOrg;		
	@ZteSoftCommentAnnotationParam(name="支付账号",type="String",isNecessary="N",desc="payNum：支付账号")
	private String payNum;
	
	public String getPayType() {
		if (StringUtils.isBlank(payType)) return null;
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayOrg() {
		if (StringUtils.isBlank(payOrg)) return null;
		return payOrg;
	}
	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}
	public String getPayNum() {
		if (StringUtils.isBlank(payNum)) return null;
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	
}
