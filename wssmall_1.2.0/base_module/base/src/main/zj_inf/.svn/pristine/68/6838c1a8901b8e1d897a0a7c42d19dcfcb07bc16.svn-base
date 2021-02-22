/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderSubOtherInfoBusiRequest;

/**
 * @author songqi
 * @see 责任人使用人信息(集客开户传)
 */
public class UseCustInfo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "使用人责任人姓名", type = "String", isNecessary = "N", desc = "使用人责任人姓名")
	private String useCustName;// 使用人责任人姓名
	@ZteSoftCommentAnnotationParam(name = "使用人或责任人客户证件类型", type = "String", isNecessary = "N", desc = "使用人或责任人客户证件类型")
	private String useCustPsptType;// 使用人或责任人客户证件类型
	@ZteSoftCommentAnnotationParam(name = "使用人或责任人客户证件号码", type = "String", isNecessary = "N", desc = "使用人或责任人客户证件号码")
	private String useCustPsptCode;// 使用人或责任人客户证件号码
	@ZteSoftCommentAnnotationParam(name = "使用人或责任人证件地址", type = "String", isNecessary = "N", desc = "使用人或责任人证件地址")
	private String useCustPsptAddress;// 使用人或责任人证件地址
	@ZteSoftCommentAnnotationParam(name = "B2B、B2C类集客产品标识", type = "String", isNecessary = "N", desc = "B2B、B2C类集客产品标识")
	private String itmPrdGroupType;// B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
	@ZteSoftCommentAnnotationParam(name = "责任人标识", type = "String", isNecessary = "N", desc = "责任人标识(责任人时传1)")
	private String itmPrdRespobsible;// 责任人标识(责任人时传1)
	@ZteSoftCommentAnnotationParam(name = "使用人打标", type = "String", isNecessary = "N", desc = "使用人打标")
	private String useCustMark;// 使用人打标（2、系统3、公安4、二代证）

	public String getUseCustName() {
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubOtherInfoBusiRequest();
		useCustName = orderSubOtherInfoBusiRequest.getCertify_cust_name();
		return useCustName;
	}

	public void setUseCustName(String useCustName) {
		this.useCustName = useCustName;
	}

	public String getUseCustPsptType() {
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubOtherInfoBusiRequest();
		useCustPsptType = orderSubOtherInfoBusiRequest.getCertify_cert_type();
		return useCustPsptType;
	}

	public void setUseCustPsptType(String useCustPsptType) {
		this.useCustPsptType = useCustPsptType;
	}

	public String getUseCustPsptCode() {
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubOtherInfoBusiRequest();
		useCustPsptCode = orderSubOtherInfoBusiRequest.getCertify_cert_num();
		return useCustPsptCode;
	}

	public void setUseCustPsptCode(String useCustPsptCode) {
		this.useCustPsptCode = useCustPsptCode;
	}

	public String getUseCustPsptAddress() {
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubOtherInfoBusiRequest();
		useCustPsptAddress = orderSubOtherInfoBusiRequest.getCertify_cert_addr();
		return useCustPsptAddress;
	}

	public void setUseCustPsptAddress(String useCustPsptAddress) {
		this.useCustPsptAddress = useCustPsptAddress;
	}

	public String getItmPrdGroupType() {
		return itmPrdGroupType;
	}

	public void setItmPrdGroupType(String itmPrdGroupType) {
		this.itmPrdGroupType = itmPrdGroupType;
	}

	public String getItmPrdRespobsible() {// 责任人标识(责任人时传1)
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubOtherInfoBusiRequest();
		itmPrdRespobsible = orderSubOtherInfoBusiRequest.getCertify_flag();
		if (StringUtil.isEmpty(itmPrdRespobsible)) {
			return null;
		} else {// 数据库存入的是 责任人/使用人标识【1】 0责任人，1使用人
			if ("0".equals(itmPrdRespobsible)) {
				itmPrdRespobsible = "1";
			} else {
				return null;
			}
		}
		return itmPrdRespobsible;
	}

	public void setItmPrdRespobsible(String itmPrdRespobsible) {
		this.itmPrdRespobsible = itmPrdRespobsible;
	}

	public String getUseCustMark() {
		// 暂无
		return useCustMark;
	}

	public void setUseCustMark(String useCustMark) {
		this.useCustMark = useCustMark;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
