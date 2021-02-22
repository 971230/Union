/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

/**
 * @author ZX
 * @version 2015-06-23
 * @see 3G老用户业务校验（AOP二期）
 * @since ecaop.trades.check.oldu.check
 *
 */
public class OldUserCheck3GResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	private String checkCode;//校验编码：	0000：成功 
							 //			0006：产品信息校验失败，次月生效产品必须大于等于当前生效产品等级
							 // 		0007：当前合约结束时间大约六个月 	0008：当前用户不允许降档续约
							 //			1000：用户不是沃享
							 //			1001：该客户下用户已超过限制个数
							 //			9999：其它错误
	private String certType; // 证件类型，参考附录证件类型
	private String certNum; // 证件号码
	private String customerName; // 客户名称
	private String certAdress; // 证件地址
	private String changeType; // 套餐变更BIPCODE：
								// 0：BIP2F008
								// 1：BIP2F005
								// 2：BIP2F039
								// 3：BIP2F022
								// 4：BIP2F024
	private String sysType; // 数据来源系统：
							// 1：ESS
							// 2：CBSS
	private String subscrbType; // 付费标识：
								// 0：后付费 
								// 1：准预付费 
								// 2：OCS 
								// 3：PPS
	private String oldActivityId; // 用户当前3G活动ID
	private String activityType; // 用户当前合约类型：
								 //	0：iPhone合约
								 //	1：非iPhone合约
	private String activityEndTime; // 活动失效时间YYYYMMDDHHMMSS
	private String oldProductMoney; // 用户当前套餐金额，单位：元
	private String woNum; // 沃享账户
	private String woProductId; // 沃享产品ID
	private String woCountNum; // 沃享已有成员个数
	private List<ParaVo> paraVo; // 保留字段
	
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertAdress() {
		return certAdress;
	}
	public void setCertAdress(String certAdress) {
		this.certAdress = certAdress;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getSysType() {
		return sysType;
	}
	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	public String getSubscrbType() {
		return subscrbType;
	}
	public void setSubscrbType(String subscrbType) {
		this.subscrbType = subscrbType;
	}
	public String getOldActivityId() {
		return oldActivityId;
	}
	public void setOldActivityId(String oldActivityId) {
		this.oldActivityId = oldActivityId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	public String getOldProductMoney() {
		return oldProductMoney;
	}
	public void setOldProductMoney(String oldProductMoney) {
		this.oldProductMoney = oldProductMoney;
	}
	public String getWoNum() {
		return woNum;
	}
	public void setWoNum(String woNum) {
		this.woNum = woNum;
	}
	public String getWoProductId() {
		return woProductId;
	}
	public void setWoProductId(String woProductId) {
		this.woProductId = woProductId;
	}
	public String getWoCountNum() {
		return woCountNum;
	}
	public void setWoCountNum(String woCountNum) {
		this.woCountNum = woCountNum;
	}
	public List<ParaVo> getParaVo() {
		return paraVo;
	}
	public void setParaVo(List<ParaVo> paraVo) {
		this.paraVo = paraVo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
