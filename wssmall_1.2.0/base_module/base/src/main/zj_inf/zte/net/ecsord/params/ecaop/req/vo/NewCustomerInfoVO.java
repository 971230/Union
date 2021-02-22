/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;

/**
 * @author songqi
 * @see 新客户信息
 */
public class NewCustomerInfoVO implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "N", desc = "内部订单号 ")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "证件类型")
	private String certType;// 证件类型
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String certNum;// 证件号码
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "证件地址")
	private String certAdress;// 证件地址
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "Y", desc = "客户名称（不能全数字）")
	private String customerName;// 客户名称（不能全数字）
	@ZteSoftCommentAnnotationParam(name = "证件失效日期", type = "String", isNecessary = "Y", desc = "格式：yyyymmdd 证件失效日期(考虑默认)")
	private String certExpireDate;// 格式：yyyymmdd 证件失效日期(考虑默认)
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "Y", desc = "性别")
	private String sex;// 性别
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "联系人（不能全数字）")
	private String contactPerson;// 联系人（不能全数字）
	@ZteSoftCommentAnnotationParam(name = "联系人电话", type = "String", isNecessary = "Y", desc = "联系人电话>6位")
	private String contactPhone;// 联系人电话>6位
	@ZteSoftCommentAnnotationParam(name = "联系人通讯地址", type = "String", isNecessary = "Y", desc = "联系人通讯地址")
	private String contactAddress;// 联系人通讯地址
	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "Y", desc = "客户类型： 01：个人客户 02：集团客户")
	private String custType;// 客户类型： 01：个人客户 02：集团客户
	@ZteSoftCommentAnnotationParam(name = "客户备注", type = "String", isNecessary = "Y", desc = "客户备注")
	private List<CustomerRemarkVO> customerRemark;// 客户备注

	public String getCertType() {
		String type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		certType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", type);
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		certNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCertAdress() {
		certAdress = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ADDRESS);
		return certAdress;
	}

	public void setCertAdress(String certAdress) {
		this.certAdress = certAdress;
	}

	public String getCustomerName() {
		customerName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_OWNER_NAME);
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCertExpireDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		String cert_failure_time = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_FAILURE_TIME).toString();
		if (StringUtil.isEmpty(cert_failure_time)) {
			return "20501231";
		}
		try {
			date = sdf.parse(cert_failure_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		certExpireDate = sd.format(date).replace(" ", "");
		return certExpireDate;
	}

	public void setCertExpireDate(String certExpireDate) {
		this.certExpireDate = certExpireDate;
	}

	public String getSex() {
		// sex =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.CERTI_SEX);
		if (StringUtils.isBlank(sex)) {
			return null;
		}
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getContactPerson() {
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getOrderDeliveryBusiRequests().get(0);
		contactPerson = delivery.getShip_name();
		if (StringUtils.isBlank(contactPerson))
			return null;
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getOrderDeliveryBusiRequests().get(0);
		contactPhone = delivery.getLogi_receiver_phone();
		if (StringUtils.isBlank(contactPhone))
			return null;
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAddress() {
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getOrderDeliveryBusiRequests().get(0);
		contactAddress = delivery.getShip_addr();
		if (StringUtils.isBlank(contactAddress))
			return null;
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCustType() {
		String type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUSTOMER_TYPE);
		custType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_user_type", type);
		if (StringUtils.isBlank(custType)) {
			return null;
		}
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public List<CustomerRemarkVO> getCustomerRemark() {
		customerRemark = new ArrayList<CustomerRemarkVO>();
		CustomerRemarkVO customerRemarkVO = new CustomerRemarkVO();
		customerRemarkVO.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		customerRemark.add(customerRemarkVO);
		return customerRemark;
	}

	public void setCustomerRemark(List<CustomerRemarkVO> customerRemark) {
		this.customerRemark = customerRemark;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
