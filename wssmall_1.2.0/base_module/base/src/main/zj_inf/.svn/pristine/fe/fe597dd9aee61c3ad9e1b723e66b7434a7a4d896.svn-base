/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

/**
 * @author songqi
 * @see 开户时选择的产品信息
 */
public class ProductVO implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "N", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "产品标识", type = "String", isNecessary = "Y", desc = "产品标识")
	private String productId;// 产品标识
	@ZteSoftCommentAnnotationParam(name = "产品名称", type = "String", isNecessary = "Y", desc = "产品名称")
	private String productName;// 产品名称
	@ZteSoftCommentAnnotationParam(name = "产品模式", type = "String", isNecessary = "Y", desc = "产品模式 1：主产品 2：附加产品")
	private String productMode;// 产品模式 1：主产品 2：附加产品
	@ZteSoftCommentAnnotationParam(name = "套包编码", type = "String", isNecessary = "Y", desc = "套包编码，北六无线上网卡必传")
	private String productCode;// 套包编码，北六无线上网卡必传

	public String getProductId() {
		//productId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PLAN_ID_ESS);
		String goods_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='" + goods_id + "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
		IDaoSupport daoSupport = SpringContextHolder.getBean("daoSupport");
		List<Map<String,String>> list = daoSupport.queryForList(sql);
		if(list.size()>0){
			productId = list.get(0).get("sn");
		}
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		productName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODSNAME);
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductMode() {
		productMode = "1";
		return productMode;
	}

	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
