package com.ztesoft.remote.basic.params.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.ChargeFeeResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 11:08
 * To change this template use File | Settings | File Templates.
 */
public class ChargeFeeRequest extends ZteRequest<ChargeFeeResponse> {

    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
    private String accNbr;

    @ZteSoftCommentAnnotationParam(name="金额",type="Double",isNecessary="Y",desc="用户充值金额")
    private Double chargeMoney;
    
    @ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(充话费)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
    @ZteSoftCommentAnnotationParam(name="银通扣款额",type="String",isNecessary="Y",desc="银通扣款额")
    private Double yt_pay_money;
    
    @ZteSoftCommentAnnotationParam(name="操作工号的手机号码",type="String",isNecessary="N",desc="操作工号的手机号码")
    private String phone_no;
    
    @ZteSoftCommentAnnotationParam(name="支付类型",type="String",isNecessary="N",desc="支付类型：掌厅团购支付传:12，话费充值传：4，其他类型后续扩展，与pay_type对应")
    private String payType;    
    
	private Map other;
    
	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	
	public Map getOther() {
		return other;
	}

	public void setOther(Map other) {
		this.other = other;
	}
	
	public Double getYt_pay_money() {
		return yt_pay_money;
	}

	public void setYt_pay_money(Double yt_pay_money) {
		this.yt_pay_money = yt_pay_money;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

    @Override
    public void check() throws ApiRuleException {
        if(ApiUtils.isBlank(accNbr)){
            throw new ApiRuleException("-1","充值号码不能为空!");
        }

        if(chargeMoney==0){
            throw new ApiRuleException("-1","充值金额不合法!");
        }
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public Double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(Double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"chargeFee";
    }

	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
}
