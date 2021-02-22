package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.ProdPackElementReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfoReqVo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * @author ZX
 * @version 2015-06-26
 * @see 套餐变更-支持3G
 *
 */
public class ProdChangeApplyReq extends ZteRequest<ZteResponse> {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "订单ID")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分（客户归属）", type = "String", isNecessary = "Y", desc = "省分（客户归属）")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市（客户归属）", type = "String", isNecessary = "Y", desc = "地市（客户归属）")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县（客户归属）", type = "String", isNecessary = "Y", desc = "区县（客户归属）")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码（联通分配）", type = "String", isNecessary = "Y", desc = "渠道编码（联通分配）")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型（联通分配）", type = "String", isNecessary = "Y", desc = "渠道类型（联通分配）")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "N", desc = "渠道标示")
	private String eModeCode;
	@ZteSoftCommentAnnotationParam(name = "电子商城订单ID", type = "String", isNecessary = "Y", desc = "电子商城订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：ESS 2：CBSS", type = "String", isNecessary = "N", desc = "opeSysType")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "业务标示 001：退出原群组", type = "String", isNecessary = "N", desc = "")
	private String serType;
	@ZteSoftCommentAnnotationParam(name = "变更类型：0：套餐间变更，如A主套餐变更为B主套餐 1：套餐内变更，如取消流量封顶  不传默认套餐间变更", type = "String", isNecessary = "N", desc = "changeType")
	private String changeType;
	@ZteSoftCommentAnnotationParam(name = "扣款标示0：不扣款1：扣款--该字段没传的时候默认扣款", type = "String", isNecessary = "N", desc = "")
	private String deductionTag;
	@ZteSoftCommentAnnotationParam(name = "产品", type = "ProductInfoReqVo", isNecessary = "Y", desc = "productInfo")
	private List<ProductInfoReqVo> productInfo;
	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "Y", desc = "recomPersonId")
	private String recomPersonId;
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "Y", desc = "recomPersonName")
	private String recomPersonName;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "PackageChangeParaVo", isNecessary = "Y", desc = "para")
	private List<ParaVo> para;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	private EmpOperInfoVo essOperInfo;

	public EmpOperInfoVo getEssOperInfo() {
		if (essOperInfo == null) {
			// essOperInfo =
			// CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();//会给默认值
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.order_gonghao ='" + opercode + "' and a.city='"
					+ otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
			if (null == essOperInfo) {// 未配置opercode
				essOperInfo = new EmpOperInfoVo();
			}
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

	public String getOperatorId() {
		this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.OUT_OPERATOR);
		if (StringUtils.isEmpty(this.operatorId)) {
			this.operatorId = getEssOperInfo().getEss_emp_id();
		}
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		// return getEssOperInfo().getProvince();
		return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (!StringUtils.isEmpty(this.city) && this.city.length() == 6) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		} else {
			if (!StringUtils.isEmpty(getEssOperInfo().getCity())) {
				this.city = getEssOperInfo().getCity();
			}
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DISTRICT_CODE);
		if (StringUtils.isEmpty(this.district)) {
			this.district = getEssOperInfo().getDistrict();
		}
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_CHA_CODE);
		if (StringUtils.isEmpty(this.channelId)) {
			this.channelId = getEssOperInfo().getChannel_id();
		}
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CHANNEL_TYPE);
		if (StringUtil.isEmpty(this.channelType)) {
			this.channelType = getEssOperInfo().getChannel_type();
		}
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String geteModeCode() {
		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
	}

	public String getOrdersId() {
		this.ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		if (StringUtils.isEmpty(this.ordersId)) {
			this.ordersId = notNeedReqStrOrderId.replaceAll("[a-zA-Z]", "");
		}
		return this.ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getSerialNumber() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOpeSysType() {
		return EcsOrderConsts.AOP_OPE_SYS_TYPE_2; // 业务类型1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public String getSerType() {
		if (StringUtils.isEmpty(serType)) {
			return null;
		}
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getDeductionTag() {
		return deductionTag;
	}

	public void setDeductionTag(String deductionTag) {
		this.deductionTag = deductionTag;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getChangeType() {
		// 变更类型：
		// 0：套餐间变更，如A主套餐变更为B主套餐
		// 1：套餐内变更，如取消流量封顶
		// 不传默认套餐间变更
		// 可以根据3户接口获取原产品id，跟订单订购的产品id比较，来判断是套餐间变更还是套餐内变更
		// String oldProdCode =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.OLD_PRODUCT_ID);
		// String newProdCode =
		// CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		// SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE);
		//
		// changeType = StringUtils.equals(oldProdCode, newProdCode)?"1":"0";
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<ProductInfoReqVo> getProductInfo() {
	    String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
        String opt_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"opttype");
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String cat_id = cacheUtil.getConfigInfo("prodChangeCatId");
	    if((StringUtil.equals("170151439562000274", catId) || cat_id.contains(catId+",")) && StringUtil.equals("02", opt_type)){
	        List<ProductInfoReqVo> list = new ArrayList<ProductInfoReqVo>();
	        ProductInfoReqVo productInfo_old = new ProductInfoReqVo();
	        /*productInfo1.setProductId("90063345");*/
	        String old_product_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.OLD_PRODUCT_ID);
	        productInfo_old.setProductId(old_product_id);
	        productInfo_old.setOptType("01");
	        productInfo_old.setProductMode("1");
	        productInfo_old.setProductNameX(null);
	        productInfo_old.setPackageElement(null);
	        list.add(productInfo_old);
	        ProductInfoReqVo productInfo = new ProductInfoReqVo();
	        String procuct_idsString =  CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", "bss_code");
	        productInfo.setProductId(procuct_idsString); // Y 产品标识
	        List<ProdPackElementReqVo> lists = getPackageElement(CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", "bss_code"));
	        if(lists==null || lists.size()==0){
	            lists =  new ArrayList<ProdPackElementReqVo>();
	        }
	        String productMode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "productMode");
	        if (!StringUtil.isEmpty(productMode)) {
	            productInfo.setProductMode(productMode); // 0：可选产品；1：主产品
	        } else {
	            productInfo.setProductMode("1"); // 0：可选产品；1：主产品
	        }
	        String optType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "optType");
	        if (StringUtil.isEmpty(optType)) {
	            optType = "00";
	        }
	        productInfo.setOptType("00"); // 待定， 00：订购；01：退订；02：变更
	        productInfo.setCompanyId(null);
	        productInfo.setProductNameX(null);
	        //list.add(productInfo);
	        String modType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "modType");//0 订购 1 退订
	        if(StringUtil.isEmpty(modType)){
	            modType="0";
	        }
	        IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
	        //所有货品
	        List<Goods> products = goodsManager.listGoodsRelProducts(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
	        if (products.size() > 0) {
	            Set<String> productIdStrs = new HashSet<String>();// 多个附加产品的编码
	            for (Goods g : products) {
	                Map specs = SpecUtils.getProductSpecMap(g);
	                if (specs != null && !specs.isEmpty()) {
	                    String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
	                    if (!StringUtil.isEmpty(productId) && productId.equals(procuct_idsString)) {
	                        String packageId = specs.get("package_code") == null ? null : specs.get("package_code") + "";
                            String elementId = specs.get("element_code") == null ? null : specs.get("element_code") + "";
                            String elementType = specs.get("elementType") == null ? "D" : specs.get("elementType") + "";
                            if (!StringUtil.isEmpty(elementId) || !StringUtil.isEmpty(elementId)) {
                                ProdPackElementReqVo element = new ProdPackElementReqVo();
                                element.setElementId(elementId);
                                element.setPackageId(packageId);
                                element.setElementType(elementType);
                                element.setModType(modType);
                                element.setServiceAttr(null);
                                lists.add(element);
                            }
	                    }else{
	                        productIdStrs.add(productId);
	                    }
	                }
	            }
	            if (!productIdStrs.isEmpty()) {
	                for (String pid : productIdStrs) {// 不同的产品编码可能会有多个包元素编码
	                    if (!StringUtil.isEmpty(pid)) {
	                        ProductInfoReqVo subVo = new ProductInfoReqVo();
	                        subVo.setProductId(pid);
	                        subVo.setProductMode("2");
	                        subVo.setProductCode(null);
	                        
	                        List<ProdPackElementReqVo> packageElement = new ArrayList<ProdPackElementReqVo>();
	                        for (Goods g : products) {
	                            Map specs = SpecUtils.getProductSpecMap(g);
	                            if (specs != null && !specs.isEmpty()) {
	                                String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
	                                if (!StringUtil.isEmpty(productId) && pid.equals(productId)) {
	                                    String packageId = specs.get("package_code") == null ? null : specs.get("package_code") + "";
	                                    String elementId = specs.get("element_code") == null ? null : specs.get("element_code") + "";
	                                    String elementType = specs.get("elementType") == null ? "D" : specs.get("elementType") + "";
	                                    if (!StringUtil.isEmpty(elementId) || !StringUtil.isEmpty(elementId)) {
	                                        ProdPackElementReqVo element = new ProdPackElementReqVo();
	                                        element.setElementId(elementId);
	                                        element.setPackageId(packageId);
	                                        element.setElementType(elementType);
	                                        element.setModType(modType);
	                                        element.setServiceAttr(null);
	                                        packageElement.add(element);
	                                    }
	                                }
	                            }
	                        }
	                        if(packageElement.size()>0){
	                            subVo.setPackageElement(packageElement);
	                        }else{
	                            subVo.setPackageElement(null);
	                        }
	                        list.add(subVo);
	                    }
	                }
	            }
	        }
	        productInfo.setPackageElement(lists);
	        list.add(productInfo);
	        return list;
	    }else{
	        return productInfo;
	    }
	}

	private List<ProdPackElementReqVo> getPackageElement(String goodSpec) {
	    String modType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "modType");//0 订购 1 退订
        if(StringUtil.isEmpty(modType)){
            modType="0";
        }
	    List<ProdPackElementReqVo> ls = new ArrayList<ProdPackElementReqVo>();
        List<AttrPackageBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
                .getPackageBusiRequests();
        // 没可选包就不用处理了
        if (list == null || list.size() == 0){
           /* ProdPackElementReqVo vo1 = new ProdPackElementReqVo();
            ProdPackElementReqVo vo2 = new ProdPackElementReqVo();
            vo1.setPackageId("51984128");
            vo1.setElementId("8322490");
            vo1.setElementType("D");
            vo1.setModType("0");
            vo1.setServiceAttr(null);
            ls.add(vo1);*/
            return null;
        }
        // 获取可选包的依赖包等信息
        List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, goodSpec,
                CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
        if (map != null) {
            for (Map m : map) {
                ProdPackElementReqVo vo = new ProdPackElementReqVo();
                if (m.size() > 0) {
                    vo.setPackageId(m.get("package_id").toString());
                    vo.setElementId(m.get("element_id").toString());
                    vo.setElementType(m.get("element_type_code").toString());
                    vo.setModType(modType);
                    vo.setServiceAttr(null);
                    ls.add(vo);
                }
            }
            return ls;
        } else {
           return null;
        }
    }

    public void setProductInfo(List<ProductInfoReqVo> productInfo) {
		this.productInfo = productInfo;
	}

	public String getRecomPersonId() {
		recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				"develop_code_new");
		if (StringUtils.isBlank(recomPersonId)) {
			return null;
		}
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}

	public String getRecomPersonName() {
		recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				"develop_name_new");
		if (StringUtils.isBlank(recomPersonName)) {
			return null;
		}
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}

	public List<ParaVo> getPara() {
		if (null == para || para.size() < 1) {
			return null;
		}
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.oldu.product.chg";
	}
}
