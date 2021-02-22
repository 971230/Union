
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrPackageActivityBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyActivityInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyPackageElementVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyProductVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 4G老用户存费送费
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class CunFeeSongFee4GReqZJ extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "区县", type = "district", isNecessary = "Y", desc = "district：区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;

	@ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "Y", desc = "eModeCode：渠道标示")
	private String eModeCode;
	
	@ZteSoftCommentAnnotationParam(name = "客户ID", type = "String", isNecessary = "N", desc = "custId：客户ID")
	private String custId;
	
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统：1：ESS,2：CBSS,不传默认ESS")
	private String opeSysType;

	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "N", desc = "serialNumber:手机号码")
	private String serialNumber;
	
	@ZteSoftCommentAnnotationParam(name = "产品", type = "List", isNecessary = "N", desc = "productInfo：产品")
	private List<OpenDealApplyProductVo> productInfo;

	@ZteSoftCommentAnnotationParam(name = "参加的存费送费活动信息", type = "List", isNecessary = "N", desc = "activityInfo：参加的存费送费活动信息")
	private List<OpenDealApplyActivityInfoVo> activityInfo;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "List", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para = new ArrayList<ParamsVo>();
	@ZteSoftCommentAnnotationParam(name = "销售模式类型", type = "String", isNecessary = "N", desc = "saleModType：0：自营厅行销模式 1：行销渠道直销模式")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "是否行销装备", type = "String", isNecessary = "N", desc = "chkBlcTag：0：否，1：是 该字段没传的时候默认 0：否")
	private String markingTag;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getSaleModType() {
		saleModType=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SALE_MOD_TYPE);
		return saleModType;
	}

	public void setSaleModType(String saleModType) {
		this.saleModType = saleModType;
	}

	public String getMarkingTag() {
		markingTag=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MARKING_TAG);
		return markingTag;
	}

	public void setMarkingTag(String markingTag) {
		this.markingTag = markingTag;
	}
	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
			this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
			this.province = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROVINCE);
			return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (this.city.trim().length()!=3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT_CODE);
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_CODE);
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE);
		return this.channelType.trim().length()==0?"1030100":this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOpeSysType() {
//		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
//		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return EcsOrderConsts.AOP_OPE_SYS_TYPE_2; //
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}
	
	
	
	public String getSerialNumber() {
		serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String geteModeCode() {
		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
	}

	public String getCustId() {
		custId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID);
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public List<OpenDealApplyProductVo> getProductInfo() {
		List<OpenDealApplyProductVo> ls = new ArrayList<OpenDealApplyProductVo>();
		OpenDealApplyProductVo vo = new OpenDealApplyProductVo();
		OrderTreeBusiRequest tree = new OrderTreeBusiRequest();
		tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String goods_id=tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport)SpringContextHolder.getBean("daoSupport");
		String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='"+goods_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list = daoSupport.queryForListByMap(sql, null);
		Map map = (Map) list.get(0);
		vo.setProductId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_PRODUCT_ID)); // Y 产品标识
//		vo.setProductId(com.ztesoft.ibss.common.util.Const.getStrValue(map, "sn")+""); // Y 产品标识
		vo.setProductMode("1"); // Y 产品模式 1：主产品 0：附加产品 （）
		vo.setPackageElement(getPackageElement(vo.getProductId())); // * 产品下附加包及包元素（资费，服务） OpenDealApplyPackageElementVo
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		
		//附加产品处理
		List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderSubProductBusiRequest();
		//获取附加产品数据
		for(OrderSubProductBusiRequest subProduct : subProductLists){
			//只处理主卡的可选包 2016-05-06
			if(null != subProduct.getProd_inst_id() && "0".equals(subProduct.getProd_inst_id())){
				OpenDealApplyProductVo subVo = new OpenDealApplyProductVo();
				subVo.setProductId(subProduct.getSub_pro_code());
				subVo.setPackageElement(getSubPackageElement(subProduct.getSub_pro_code(),subProduct.getSub_pro_inst_id()));
				subVo.setProductMode("0");
				subVo.setProductCode(null);
				ls.add(subVo);
			}
		}
		
		return ls;
	}

	public void setProductInfo(List<OpenDealApplyProductVo> productInfo) {
		this.productInfo = productInfo;
	}
	
	/**
	 * 获取可选包
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyPackageElementVo> getPackageElement(String product_id) {
		List<OpenDealApplyPackageElementVo> ls = new ArrayList<OpenDealApplyPackageElementVo>();		
		List<AttrPackageBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPackageBusiRequests();
		//没可选包就不用处理了
		if(list==null ||list.size()==0)return null;
		
		//获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
																		CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				OpenDealApplyPackageElementVo vo = new OpenDealApplyPackageElementVo();
				if(m.size()>0){
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}

	public List<OpenDealApplyActivityInfoVo> getActivityInfo() {
		List<OpenDealApplyActivityInfoVo> ls = new ArrayList<OpenDealApplyActivityInfoVo>();
		OpenDealApplyActivityInfoVo vo = new OpenDealApplyActivityInfoVo();
		OrderTreeBusiRequest tree = new OrderTreeBusiRequest();
		tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		String goods_id=tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport)SpringContextHolder.getBean("daoSupport");
		String sql = "select z_goods_id from es_goods_rel where a_goods_id='"+goods_id+"'";
		List list = daoSupport.queryForListByMap(sql, null);
		Map map = (Map) list.get(0);
		vo.setActPlanId(CommonDataFactory.getInstance().getGoodSpec( null,map.get("z_goods_id").toString(), "cbss_product_code"));
//		String limit = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
//		if (StringUtils.isNotBlank(limit)) {
//			vo.setActPlanId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PACKAGE_ID));
//		} else {
//			vo.setActPlanId(null);
//		}
		
		//合约业务包取值
		List<AttrPackageActivityBusiRequest> attrPackageActivityBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getAttrPackageActivityBusiRequest();
		List<OpenDealApplyPackageElementVo> activityEles = new ArrayList<OpenDealApplyPackageElementVo>();
		OpenDealApplyPackageElementVo activityElement = new OpenDealApplyPackageElementVo();
		activityElement.setPackageId(CommonDataFactory.getInstance().getGoodSpec( null,map.get("z_goods_id").toString(), "package_code"));
		activityElement.setElementId(CommonDataFactory.getInstance().getGoodSpec( null,map.get("z_goods_id").toString(), "element_code"));
		activityElement.setElementType("D");
		activityEles.add(activityElement);
//		for(AttrPackageActivityBusiRequest attrPackageActivity : attrPackageActivityBusiRequest){
//			OpenDealApplyPackageElementVo activityElement = new OpenDealApplyPackageElementVo();
//			activityElement.setPackageId(attrPackageActivity.getPackage_code());
//			activityElement.setElementId(attrPackageActivity.getElement_code());
//			activityElement.setElementType(attrPackageActivity.getElement_type());
//			activityEles.add(activityElement);
//		}
		vo.setPackageElement(activityEles);
		ls.add(vo);
		return ls;
	}

	public void setActivityInfo(List<OpenDealApplyActivityInfoVo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public List<ParamsVo> getPara() {
		ParamsVo pa = new ParamsVo();
		pa.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
		pa.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
		para.add(pa);
		return para;
	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.oldu.fee.chgzj";
	}


	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}
	
	/**
	 * 获取附加产品可选包
	 * @param product_id
	 * @param sub_inst_id
	 * @return
	 */
	private List<OpenDealApplyPackageElementVo> getSubPackageElement(String product_id , String sub_inst_id) {
		List<OpenDealApplyPackageElementVo> ls = new ArrayList<OpenDealApplyPackageElementVo>();		
		List<AttrPackageBusiRequest> list = new ArrayList<AttrPackageBusiRequest>();
		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getAttrPackageSubProdBusiRequest();
		
		//没可选包就不用处理了
		if(attrPackageSubProdList==null ||attrPackageSubProdList.size()==0)return null;
		
		//附加产品可选包转换为AttrPackageBusiRequest类对象
		for(AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList){
			if(sub_inst_id.equals(subPackage.getSubProd_inst_id())){
				AttrPackageBusiRequest attrPackage = new AttrPackageBusiRequest();
				attrPackage.setElementCode(subPackage.getElement_code());
				list.add(attrPackage);
			}
		}
		
		//获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
																		CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				OpenDealApplyPackageElementVo vo = new OpenDealApplyPackageElementVo();
				if(m.size()>0){
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}
}
