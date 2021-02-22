package com.zte.cbss.autoprocess.model;

import java.io.Serializable;

import com.zte.cbss.autoprocess.model.data.CheckProductData;

/**
 * 业务参数数据
 * @author Administrator
 *
 */
public class ParameterData implements Serializable{

	private CustomBill bill;							//客户订单
	private CustomInfo customInfo;   					//客户信息
	private PsptInfo psptInfo; 							//证件信息
	private WriteCardResponse writeCardResponse;		//写卡响应信息
	private SimcardInfo simcardInfo;		//写卡信息
	
	
	//平台管理员信息及安全信息
    private String staffId; 							//用户ID
    private String provinceId; 							//省ID
    private String departId; 							//部门ID
    private String eparchyCode; 						//地市ID
    private String LOGIN_RANDOM_CODE;					//登录随机编码
    private String LOGIN_CHECK_CODE;					//检查编码
    private String random = "11452112453140";			//随机数，目前写死
    private String otree_order_id; //订单tree订单编号
    private String order_create_time; // 下单时间
    // 产品类型编码
    private String productTypeCode;
    //
    private String brandCode;

    private String tradeId;
    
    private String userId;
    
    private String acctId;
    
    private String itemId;
    
    private String tradeBase;
    
    private String iccid;
    
    
    private CheckProductData productData;				//开户选择的产品信息
    
    private String developStaffId; //发展人编码
    private String developDepartId; //渠道编码
    private String developEparchyCode; //发展人区号
    private String developCityCode; //发展人地市
    private String userCallingArea; //用户呼叫区域
            
    private String tradeFee;
    
    public String getOrder_create_time() {
		return order_create_time;
	}

	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}

	public String getTradeBase() {
		return tradeBase;
	}

	public void setTradeBase(String tradeBase) {
		this.tradeBase = tradeBase;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	/**
     * 取得 staffId
     * @return staffId String
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * 设定 staffId
     * @param staffId String
     */
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    /**
     * 取得 provinceId
     * @return provinceId String
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * 设定 provinceId
     * @param provinceId String
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 取得 departId
     * @return departId String
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设定 departId
     * @param departId String
     */
    public void setDepartId(String departId) {
        this.departId = departId;
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
     * 取得 lOGIN_RANDOM_CODE
     * @return lOGIN_RANDOM_CODE String
     */
    public String getLOGIN_RANDOM_CODE() {
        return LOGIN_RANDOM_CODE;
    }

    /**
     * 设定 lOGIN_RANDOM_CODE
     * @param lOGIN_RANDOM_CODE String
     */
    public void setLOGIN_RANDOM_CODE(String lOGIN_RANDOM_CODE) {
        LOGIN_RANDOM_CODE = lOGIN_RANDOM_CODE;
    }

    /**
     * 取得 lOGIN_CHECK_CODE
     * @return lOGIN_CHECK_CODE String
     */
    public String getLOGIN_CHECK_CODE() {
        return LOGIN_CHECK_CODE;
    }

    /**
     * 设定 lOGIN_CHECK_CODE
     * @param lOGIN_CHECK_CODE String
     */
    public void setLOGIN_CHECK_CODE(String lOGIN_CHECK_CODE) {
        LOGIN_CHECK_CODE = lOGIN_CHECK_CODE;
    }

    public CustomInfo getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(CustomInfo customInfo) {
        this.customInfo = customInfo;
    }

    public CustomBill getBill() {
        return bill;
    }

    public void setBill(CustomBill bill) {
        this.bill = bill;
    }

    /**
     * 取得 productTypeCode
     * @return productTypeCode String
     */
    public String getProductTypeCode() {
        return productTypeCode;
    }

    /**
     * 设定 productTypeCode
     * @param productTypeCode String
     */
    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    /**
     * 取得 brandCode
     * @return brandCode String
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * 设定 brandCode
     * @param brandCode String
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

	public PsptInfo getPsptInfo() {
		return psptInfo;
	}

	public void setPsptInfo(PsptInfo psptInfo) {
		this.psptInfo = psptInfo;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
	
	/**
	 * 中文证件名称
	 * @return
	 */
	public String getPsptType() {
		return this.customInfo.getPsptType();
	}

	public String getPsptId(){
		return this.bill.getPsptId();
	}

	public CheckProductData getProductData() {
		return productData;
	}

	public void setProductData(CheckProductData productData) {
		this.productData = productData;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getDevelopStaffId() {
		return developStaffId;
	}

	public void setDevelopStaffId(String developStaffId) {
		this.developStaffId = developStaffId;
	}

	public String getDevelopDepartId() {
		return developDepartId;
	}

	public void setDevelopDepartId(String developDepartId) {
		this.developDepartId = developDepartId;
	}

	public String getDevelopEparchyCode() {
		return developEparchyCode;
	}

	public void setDevelopEparchyCode(String developEparchyCode) {
		this.developEparchyCode = developEparchyCode;
	}

	public String getDevelopCityCode() {
		return developCityCode;
	}

	public void setDevelopCityCode(String developCityCode) {
		this.developCityCode = developCityCode;
	}

	public String getUserCallingArea() {
		return userCallingArea;
	}

	public void setUserCallingArea(String userCallingArea) {
		this.userCallingArea = userCallingArea;
	}

	public WriteCardResponse getWriteCardResponse() {
		return writeCardResponse;
	}

	public void setWriteCardResponse(WriteCardResponse writeCardResponse) {
		this.writeCardResponse = writeCardResponse;
	}

	public SimcardInfo getSimcardInfo() {
		return simcardInfo;
	}

	public void setSimcardInfo(SimcardInfo simcardInfo) {
		this.simcardInfo = simcardInfo;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOtree_order_id() {
		return otree_order_id;
	}

	public void setOtree_order_id(String otree_order_id) {
		this.otree_order_id = otree_order_id;
	}
	
}