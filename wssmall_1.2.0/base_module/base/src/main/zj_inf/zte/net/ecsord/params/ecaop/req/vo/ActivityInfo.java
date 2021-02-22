/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * @author songqi
 * @see 开户时活动信息
 */
public class ActivityInfo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "活动ID", type = "String", isNecessary = "N", desc = "活动ID")
	private String actPlanId;// 活动ID
	@ZteSoftCommentAnnotationParam(name = "活动名称", type = "String", isNecessary = "N", desc = "活动名称")
	private String actPlanName;// 活动名称
	@ZteSoftCommentAnnotationParam(name = "资源类型 ", type = "String", isNecessary = "N", desc = "资源类型 03：移动电话 04：上网卡 05：上网本")
	private String resourcesType;// 资源类型 03：移动电话 04：上网卡 05：上网本
	@ZteSoftCommentAnnotationParam(name = "资源费用", type = "String", isNecessary = "N", desc = "资源费用")
	private String resourcesFee;// 资源费用
	@ZteSoftCommentAnnotationParam(name = "活动预存费用", type = "String", isNecessary = "N", desc = "活动预存费用")
	private String activityFee;// 活动预存费用

	@SuppressWarnings("rawtypes")
	public String getActPlanId() {
		// actPlanId =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.OUT_PACKAGE_ID);
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
		String sql = "select goods_id from es_pmt_goods where pmt_id in (select (case pmts_id when 'gift' then pmt_id else '' end) from es_promotion where pmta_id in (select pmta_id  from es_promotion where pmt_id in (select pmt_id from es_pmt_goods where goods_id = '"
				+ goods_id + "')))";
		List list = daoSupport.queryForListByMap(sql, null);
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			actPlanId = CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(),
					"cbss_product_code"); // Y 活动ID（合约编码）
		}
		if (StringUtil.isEmpty(actPlanId)) {// 测试用
			return "123456";
		}
		return actPlanId;
	}

	public void setActPlanId(String actPlanId) {
		this.actPlanId = actPlanId;
	}

	@SuppressWarnings("rawtypes")
	public String getActPlanName() {// package_name
		// actPlanName =
		// CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "",
		// "name");
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
		String sql = "select goods_id from es_pmt_goods where pmt_id in (select (case pmts_id when 'gift' then pmt_id else '' end) from es_promotion where pmta_id in (select pmta_id  from es_promotion where pmt_id in (select pmt_id from es_pmt_goods where goods_id = '"
				+ goods_id + "')))";
		List list = daoSupport.queryForListByMap(sql, null);
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			actPlanName = CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(),
					"package_name"); //
		}
		if (StringUtil.isEmpty(actPlanName)) {// 测试用
			return "actPlanName";
		}
		return actPlanName;
	}

	public void setActPlanName(String actPlanName) {
		this.actPlanName = actPlanName;
	}

	public String getResourcesType() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.TYPE_ID);//商品大类
		resourcesType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type", type_id);
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	public String getResourcesFee() {
		return resourcesFee;
	}

	public void setResourcesFee(String resourcesFee) {
		this.resourcesFee = resourcesFee;
	}

	public String getActivityFee() {
		return activityFee;
	}

	public void setActivityFee(String activityFee) {
		this.activityFee = activityFee;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
