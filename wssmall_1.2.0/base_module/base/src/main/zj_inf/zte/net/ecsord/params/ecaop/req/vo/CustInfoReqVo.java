/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 客户信息
 *
 */
public class CustInfoReqVo implements Serializable {
	
	private String authTag; // Y 鉴权标识 0：未鉴权 1：已鉴权
	private String realNameType; // Y 客户实名标识 0：实名 1：匿名
	private String custType; // Y 新老客户标识 0：新增客户 1：老客户
	private String custId; // N 老客户ID
	private List<NewCustInfoReqVo> newCustomerInfo; // 新客户信息
	
	public String getAuthTag() {
		if (StringUtils.isBlank(authTag)) return null;
		return authTag;
	}
	public void setAuthTag(String authTag) {
		this.authTag = authTag;
	}
	public String getRealNameType() {
		if (StringUtils.isBlank(realNameType)) return null;
		return realNameType;
	}
	public void setRealNameType(String realNameType) {
		this.realNameType = realNameType;
	}
	public String getCustType() {
		if (StringUtils.isBlank(custType)) return null;
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustId() {
		if (StringUtils.isBlank(custId)) return null;
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public List<NewCustInfoReqVo> getNewCustomerInfo() {
		if (newCustomerInfo==null || newCustomerInfo.size()<=0) return null;
		return newCustomerInfo;
	}
	public void setNewCustomerInfo(
			List<NewCustInfoReqVo> newCustomerInfo) {
		this.newCustomerInfo = newCustomerInfo;
	}
	
}
