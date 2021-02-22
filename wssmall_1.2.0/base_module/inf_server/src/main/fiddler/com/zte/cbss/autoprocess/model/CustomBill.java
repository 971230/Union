package com.zte.cbss.autoprocess.model;

import java.util.List;

/**
 * 客户账单
 * @author 张浩
 * @version 1.0.0(2014-5-20)
 */
public class CustomBill {

    private String psptId; 					//身份证ID

    private String customName; 				//客户姓名
   
    private String serialNumber;			//待开户号码
    
    private String eparchyCode;				//归属城市
    
    private String idTypeCode = "1";		//证件类型，默认：身份证
    
    private String psptEndDate;				//证件有效期
	
	private String phone;					//客户联系电话
	
	private String postAddress;				//客户通讯地址
	
	private String psptAddress;				//证件地址
	
	private String contact;					//联系人姓名
	
	private String contactPhone;			//联系人电话
    
    private String productName;				//产品名称(查询关键字)
    // 移动业务选号，电信类型
    private String netTypeCode;
    // 客户归属业务区
    private String userCityCode;
    // 产品编码
    private String selectedProductId;
    // 活动类型
    private String activityType;
    
    private String feeItemName;//费用名称
    
	private String liangNumFee;//靓号金额
	
	private String fee;//订单应缴金额
	
	private String feeWaivers;//订单减免金额
    
    /**SIM卡ID，即ICCID*/
    private String iccid;
    
    public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	// 活动类型编码
    private String activityCode;
    //增值业务需要的参数	
    List<String> addServiceCode;
    //基本套餐 判断参数
    private String firstFeeType;
    //短信资费包
    private String messageCode;
    //自由组合流量包
    private String flowPackage;
    private String flowFirstFeeType;
    public String getFlowFirstFeeType() {
		return flowFirstFeeType;
	}

	public void setFlowFirstFeeType(String flowFirstFeeType) {
		this.flowFirstFeeType = flowFirstFeeType;
	}

	public String getCallFirstFeeType() {
		return callFirstFeeType;
	}

	public void setCallFirstFeeType(String callFirstFeeType) {
		this.callFirstFeeType = callFirstFeeType;
	}

	public String getMessageFirstFeeType() {
		return messageFirstFeeType;
	}

	public void setMessageFirstFeeType(String messageFirstFeeType) {
		this.messageFirstFeeType = messageFirstFeeType;
	}

	//自由组合语音包
    private String callPackage;
    private String callFirstFeeType;
     //自由组合来电显示包
    private String callDisplay;
    private String messageFirstFeeType;
  
   

	/**
     * 取得 psptId
     * @return psptId String
     */
    public String getPsptId() {
        return psptId;
    }

    /**
     * 设定 psptId
     * @param psptId String
     */
    public void setPsptId(String psptId) {
        this.psptId = psptId;
    }

    /**
     * 取得 customName
     * @return customName String
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设定 customName
     * @param customName String
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 取得 eparchyCode
     * @return eparchyCode String
     */
    public String getEparchyCode() {
        return eparchyCode;
    }

    /**
     * 设定 eparchyCode
     * @param eparchyCode String
     */
    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    /**
     * 取得 idTypeCode
     * @return idTypeCode String
     */
    public String getIdTypeCode() {
        return idTypeCode;
    }

    /**
     * 设定 idTypeCode
     * @param idTypeCode String
     */
    public void setIdTypeCode(String idTypeCode) {
        this.idTypeCode = idTypeCode;
    }

    /**
     * 取得 psptEndDate
     * @return psptEndDate String
     */
    public String getPsptEndDate() {
        return psptEndDate;
    }

    /**
     * 设定 psptEndDate
     * @param psptEndDate String
     */
    public void setPsptEndDate(String psptEndDate) {
        this.psptEndDate = psptEndDate;
    }

    /**
     * 取得 phone
     * @return phone String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设定 phone
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 取得 postAddress
     * @return postAddress String
     */
    public String getPostAddress() {
        return postAddress;
    }

    /**
     * 设定 postAddress
     * @param postAddress String
     */
    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    /**
     * 取得 psptAddress
     * @return psptAddress String
     */
    public String getPsptAddress() {
        return psptAddress;
    }

    /**
     * 设定 psptAddress
     * @param psptAddress String
     */
    public void setPsptAddress(String psptAddress) {
        this.psptAddress = psptAddress;
    }

    /**
     * 取得 contact
     * @return contact String
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设定 contact
     * @param contact String
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 取得 contactPhone
     * @return contactPhone String
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设定 contactPhone
     * @param contactPhone String
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 取得 serialNumber
     * @return serialNumber String
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设定 serialNumber
     * @param serialNumber String
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 取得 productName
     * @return productName String
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设定 productName
     * @param productName String
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 取得 netTypeCode
     * @return netTypeCode String
     */
    public String getNetTypeCode() {
        return netTypeCode;
    }

    /**
     * 设定 netTypeCode
     * @param netTypeCode String
     */
    public void setNetTypeCode(String netTypeCode) {
        this.netTypeCode = netTypeCode;
    }

    /**
     * 取得 userCityCode
     * @return userCityCode String
     */
    public String getUserCityCode() {
        return userCityCode;
    }

    /**
     * 设定 userCityCode
     * @param userCityCode String
     */
    public void setUserCityCode(String userCityCode) {
        this.userCityCode = userCityCode;
    }

    /**
     * 取得 selectedProductId
     * @return selectedProductId String
     */
    public String getSelectedProductId() {
        return selectedProductId;
    }

    /**
     * 设定 selectedProductId
     * @param selectedProductId String
     */
    public void setSelectedProductId(String selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    /**
     * 取得 activityType
     * @return activityType String
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * 设定 activityType
     * @param activityType String
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    /**
     * 获取 addServiceCode
     * @param addServiceCode
     */
	public List<String> getAddServiceCode() {
		return addServiceCode;
	}
    /**
     * 设定 addServiceCode
     * @param addServiceCode
     */
	public void setAddServiceCode(List<String> addServiceCode) {
		this.addServiceCode = addServiceCode;
	}
	/**
     * 获取 firstFeeType
     * @param firstFeeType
     */
	public String getFirstFeeType() {
		return firstFeeType;
	}
	/**
     * 设定 firstFeeType
     * @param firstFeeType
     */
	public void setFirstFeeType(String firstFeeType) {
		this.firstFeeType = firstFeeType;
	}
	/**
     * 获取 messageCode
     * @param messageCode
     */
	public String getMessageCode() {
		return messageCode;
	}
	/**
     * 设定 messageCode
     * @param messageCode
     */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getFeeItemName() {
		return feeItemName;
	}

	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}

	public String getLiangNumFee() {
		return liangNumFee;
	}

	public void setLiangNumFee(String liangNumFee) {
		this.liangNumFee = liangNumFee;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFeeWaivers() {
		return feeWaivers;
	}

	public void setFeeWaivers(String feeWaivers) {
		this.feeWaivers = feeWaivers;
	}

	public String getFlowPackage() {
		return flowPackage;
	}

	public void setFlowPackage(String flowPackage) {
		this.flowPackage = flowPackage;
	}

	public String getCallPackage() {
		return callPackage;
	}

	public void setCallPackage(String callPackage) {
		this.callPackage = callPackage;
	}

	public String getCallDisplay() {
		return callDisplay;
	}

	public void setCallDisplay(String callDisplay) {
		this.callDisplay = callDisplay;
	}


}