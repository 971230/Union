package com.zte.cbss.dto;

/**
 * 封装返回账号查询列表的数据
 * @author Jim_Zim
 *
 */
public class AccountMappingListDataDTO {

	private Integer id;//主键，自增
	
	private String userId;//订单系统工号
	
	private String eparchyCode;//订单所属地区编码
	
	private Integer deliveryType;//配送方式
	
	private String cbssAccount;//CBSS登录账号
	
	private String cbssPassword;//CBSS账号登录密码

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEparchyCode() {
		return eparchyCode;
	}

	public void setEparchyCode(String eparchyCode) {
		this.eparchyCode = eparchyCode;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getCbssAccount() {
		return cbssAccount;
	}

	public void setCbssAccount(String cbssAccount) {
		this.cbssAccount = cbssAccount;
	}

	public String getCbssPassword() {
		return cbssPassword;
	}

	public void setCbssPassword(String cbssPassword) {
		this.cbssPassword = cbssPassword;
	}
	
}
