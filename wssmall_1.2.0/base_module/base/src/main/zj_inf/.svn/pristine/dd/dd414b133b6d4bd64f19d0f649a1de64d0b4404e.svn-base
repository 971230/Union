package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * @author songqi
 * @see 套包对应产品活动信息
 */
public class ProductActivityInfo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "资源可选产品ID", type = "String", isNecessary = "N", desc = "资源可选产品ID")
	private String productId;// 资源可选产品ID
	@ZteSoftCommentAnnotationParam(name = "已打包终端套餐对应活动编码", type = "String", isNecessary = "N", desc = "已打包终端套餐对应活动编码")
	private String resourcesActivityCode;// 已打包终端套餐对应活动编码。
	@ZteSoftCommentAnnotationParam(name = "活动协议期限", type = "String", isNecessary = "N", desc = " 活动协议期限，单位：月，正整数")
	private String resourcesActivityPer;// 活动协议期限，单位：月，正整数

	@SuppressWarnings("rawtypes")
	public String getProductId() {
		// productId =
		// CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		// SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE);
		// productId =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.OLD_PRODUCT_ID);
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String cat_Id = tree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_cat_id();
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String FUKA_CAT_ID = cacheUtil.getConfigInfo("FUKA_CAT_ID");
		if (FUKA_CAT_ID.contains(cat_Id)) {
			productId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "viceProductId");
		} else {
			String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
			String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='" + goods_id
					+ "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
			IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
			List list = daoSupport.queryForListByMap(sql, null);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				if (!StringUtils.isEmpty(map.get("sn") + "")) {
					productId = map.get("sn") + "";
				}
			}
		}
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getResourcesActivityCode() {
		return resourcesActivityCode;
	}

	public void setResourcesActivityCode(String resourcesActivityCode) {
		this.resourcesActivityCode = resourcesActivityCode;
	}

	public String getResourcesActivityPer() {
		return resourcesActivityPer;
	}

	public void setResourcesActivityPer(String resourcesActivityPer) {
		this.resourcesActivityPer = resourcesActivityPer;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
