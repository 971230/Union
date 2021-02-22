/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author songqi
 * @see 客户信息
 */
public class CustomerInfoVo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "N", desc = "内部订单号 ")
	private String notNeedReqStrOrderId;
	private List<NewCustomerInfoVO> newCustomerInfo;// 新客户信息

	public List<NewCustomerInfoVO> getNewCustomerInfo() {
		newCustomerInfo = new ArrayList<NewCustomerInfoVO>();
		NewCustomerInfoVO newCustomerInfoVO = new NewCustomerInfoVO();
		newCustomerInfoVO.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		newCustomerInfo.add(newCustomerInfoVO);
		return newCustomerInfo;
	}

	public void setNewCustomerInfo(List<NewCustomerInfoVO> newCustomerInfo) {
		this.newCustomerInfo = newCustomerInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
