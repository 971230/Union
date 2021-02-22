/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * @author songqi
 * @see 用户信息
 */
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "首月付费模式", type = "String", isNecessary = "Y", desc = "首月付费模式 01：标准资费（免首月月租） 02：全月套餐")
	private String firstMonBillMode;// 首月付费模式 01：标准资费（免首月月租） 02：全月套餐
	@ZteSoftCommentAnnotationParam(name = "产品信息", type = "String", isNecessary = "Y", desc = "开户时选择的产品信息")
	private List<ProductVO> product;// 开户时选择的产品信息
	@ZteSoftCommentAnnotationParam(name = "活动信息", type = "String", isNecessary = "Y", desc = "开户时活动信息")
	private List<ActivityInfo> activityInfo;// 开户时活动信息
	@ZteSoftCommentAnnotationParam(name = "担保方式 ", type = "String", isNecessary = "Y", desc = "担保方式 ")
	private String guarantorType;// 担保方式 参见附录
	@ZteSoftCommentAnnotationParam(name = "担保信息参数", type = "String", isNecessary = "Y", desc = "担保信息参数")
	private String guaratorID;// 担保信息参数
	/*
	 * 担保信息参数 根据担保类型确定 如担保类型为01 则填担保人客户ID 如担保类型为02 则填退款方式（11 按年，12 一次性） 如担保类型为03
	 * 则置空 如担保类型为04 则填无担保原因 如担保类型为05 则填社保卡卡号 如担保类型为06 则填“单位名称，单位编码，合同协议编码”
	 * 如担保类型为07 则填“银行名称，银行编码” 如担保类型为08 则填“银行或保险公司名称，银行或保险公司编码，合同协议编码” 如担保类型为09
	 * 则填“银行公司名称,银行公司编码,合同协议编码,冻结存款流水号,冻结金额(单位：元)” 如担保类型为10
	 * 则填“担保公司名称，担保公司编码，合同协议编码” 如担保类型为11
	 * 则填“单位名称，单位编码，银行名称，银行编码，合同协议编码，冻结金额（单位：元）”如担保类型为12
	 * 则填“担保公司名称，担保公司编码，合同协议编码”
	 */
	private String guCertType;// 被担保人证件类型
	private String guCertNum;// 被担保人证件号码
	private List<PayInfo> payInfo;// 支付信息

	public String getFirstMonBillMode() {
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.FIRST_PAYMENT);
		firstMonBillMode = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment);
		return firstMonBillMode;
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}

	public List<ProductVO> getProduct() {
		product = new ArrayList<ProductVO>();
		ProductVO productVO = new ProductVO();
		productVO.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		product.add(productVO);
		return product;
	}

	public void setProduct(List<ProductVO> product) {
		this.product = product;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ActivityInfo> getActivityInfo() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
		String sql = "select goods_id from es_pmt_goods where pmt_id in (select (case pmts_id when 'gift' then pmt_id else '' end) from es_promotion where pmta_id in (select pmta_id  from es_promotion where pmt_id in (select pmt_id from es_pmt_goods where goods_id = '"
				+ goods_id + "')))";
		List list = daoSupport.queryForListByMap(sql, null);
		String actPlanId = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			actPlanId = CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(),
					"cbss_product_code"); // Y 活动ID（合约编码）
		}
		if (StringUtil.isEmpty(actPlanId)) {// 活动信息可以为空的 现有业务都是没有该节点的
			return null;
		}
		activityInfo = new ArrayList<ActivityInfo>();
		ActivityInfo activity = new ActivityInfo();
		activity.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		activityInfo.add(activity);
		return activityInfo;
	}

	public void setActivityInfo(List<ActivityInfo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getGuarantorType() {
		String guarantor = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR);
		if (StringUtil.isEmpty(guarantor) || "无".equals(guarantor)) {
			return null;
		}
		guarantorType = "01";
		return guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	public String getGuaratorID() {
		String guarantor = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR);
		if (StringUtil.isEmpty(guarantor) || "无".equals(guarantor)) {
			return null;
		}
		guaratorID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GUARANTEE_INFO);
		return guaratorID;
	}

	public void setGuaratorID(String guaratorID) {
		this.guaratorID = guaratorID;
	}

	public String getGuCertType() {
		String guarantor = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR);
		if (StringUtil.isEmpty(guarantor) || "无".equals(guarantor)) {
			return null;
		}
		guCertType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR_CERTI_TYPE);
		return guCertType;
	}

	public void setGuCertType(String guCertType) {
		this.guCertType = guCertType;
	}

	public String getGuCertNum() {
		String guarantor = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR);
		if (StringUtil.isEmpty(guarantor) || "无".equals(guarantor)) {
			return null;
		}
		guCertNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GUARANTOR_CERTI_NO);
		return guCertNum;
	}

	public void setGuCertNum(String guCertNum) {
		this.guCertNum = guCertNum;
	}

	public List<PayInfo> getPayInfo() {
		payInfo = new ArrayList<PayInfo>();
		PayInfo p = new PayInfo();
		p.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		payInfo.add(p);
		return payInfo;
	}

	public void setPayInfo(List<PayInfo> payInfo) {
		this.payInfo = payInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
