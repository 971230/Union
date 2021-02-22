
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
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyActivityInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyPackageElementVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyProductVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * 4G老用户存费送费
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class CunFeeSongFee4GReq extends ZteRequest {

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

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
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
		vo.setProductId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PLAN_ID_ESS)); // Y 产品标识
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
		String limit = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
		if (StringUtils.isNotBlank(limit)) {
			vo.setActPlanId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PACKAGE_ID));
		} else {
			vo.setActPlanId(null);
		}
		
		//合约业务包取值
		List<AttrPackageActivityBusiRequest> attrPackageActivityBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getAttrPackageActivityBusiRequest();
		List<OpenDealApplyPackageElementVo> activityEles = new ArrayList<OpenDealApplyPackageElementVo>();
		for(AttrPackageActivityBusiRequest attrPackageActivity : attrPackageActivityBusiRequest){
			OpenDealApplyPackageElementVo activityElement = new OpenDealApplyPackageElementVo();
			activityElement.setPackageId(attrPackageActivity.getPackage_code());
			activityElement.setElementId(attrPackageActivity.getElement_code());
			activityElement.setElementType(attrPackageActivity.getElement_type());
			activityEles.add(activityElement);
		}
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
		return "ecaop.trades.sell.mob.oldu.fee.chg";
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
