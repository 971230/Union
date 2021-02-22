package zte.net.ecsord.params.sr.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.sr.vo.ActivityInfo;
import zte.net.ecsord.params.sr.vo.AttachedInfo;
import zte.net.ecsord.params.sr.vo.OrderInfo;
import zte.net.ecsord.params.sr.vo.PackageInfo;
import zte.net.ecsord.params.sr.vo.ProductInfo;
import zte.net.ecsord.params.sr.vo.SpInfo;
import zte.net.ecsord.params.sr.vo.UserInfo;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * 订单系统访问森锐仿真系统
 */
public class SimulationRequset extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "activeNo", type = "String", isNecessary = "Y", desc = "activeNo：流水号")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name = "userInfo", type = "String", isNecessary = "Y", desc = "userInfo：用户信息")
	private UserInfo userInfo;
	
	@ZteSoftCommentAnnotationParam(name = "orderInfo", type = "String", isNecessary = "Y", desc = "orderInfo：订单信息")
	private OrderInfo orderInfo;

	private EmpOperInfoVo essOperInfo;
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getActiveNo() {
		if(DataUtil.checkFieldValueNull(activeNo)){
			activeNo = CommonDataFactory.getInstance().getActiveNo(false);
		}
		return activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public UserInfo getUserInfo() {
		if(DataUtil.checkFieldValueNull(userInfo)){
			userInfo = new UserInfo();
			if(null!=essOperInfo){//没绑定工号如何处理?派单的话不符合协议要求。参照InfServices写法,essOperInfo不会为空
				userInfo.setProvince(essOperInfo.getProvince());
				userInfo.setUserName(essOperInfo.getEss_emp_id());
			}
		}
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public OrderInfo getOrderInfo() {//还没写完
		if(DataUtil.checkFieldValueNull(orderInfo)){
			String serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);//开户号码
			String psPtId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);//开户人证件号码,理论上可能不是身份证号码,非必填
			//				String productName = "test";//开户套餐别名，必须是名称全称	可以不传
			String productId = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE);//开户套餐的ID
			//确认是否有这东东，否则传以下cat_id；(货品合约计划配置时编码也是cat_id)/*select * from es_goods_cat t where t.parent_id='10001';*/
			String productType = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);//活动类型，编码参考活动类型编码表 （编码）
			String productCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.OUT_PACKAGE_ID);//活动id，如：存120元送240元的id
			String flowId = "16164281";//附加产品ID,如:4G省内闲时流量包附加产品
//			String flowPackageId = "16164281";//具体附加产品的ID,如:10元3G闲时省内流量包的ID.	字段删除
			String packageId_attachedInfo = "16220948";//包编号	资费包
			String elementId_attachedInfo = "16000948";//元素编号	资费id
			String spId = "91932";//SP产品名称的ID,需要支持多个，如:8000623900, 8000623901, 8000623902
			String spName = "WO+新视界包月";//SP产品别名,如:名称1,名称2,名称3
			String deviceImei = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);//终端串号
			String deviceType = SpecConsts.TYPE_ID_10000;//终端类型编码
			String deviceAttach = "";//终端归属编码//确认是否有，有则传内部编码；否则写死或不传
			String payType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_METHOD);//默认订单系统编码:10 现金支付
			String payPrice = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_REAL_FEE);;//支付金额（实收）
			
			//首月资费方式
			String inner_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT);//内部编码
			List<Map> modes = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.SIMULATION_FIRST_MON_BILL_MODE);
			String outer_code = "";//对外编码
			if(modes!=null && modes.size()>0){
				for(Map mode : modes){
					if(inner_code.equals(CommonDataFactory.getInstance().getStrValue(mode, "field_value"))){//匹配内部编码
						outer_code = CommonDataFactory.getInstance().getStrValue(mode, "other_field_value");//赋值外部编码
						break ;
					}
				}
			}
			String firstMonBillMode = outer_code;
			
			List<PackageInfo> packageInfos = null;
			PackageInfo packageInfo = null;
		
			
			orderInfo = new OrderInfo();
			orderInfo.setSequence(notNeedReqStrOrderId);//内部订单号
			orderInfo.setAccessPwd(EcsOrderConsts.ACCESS_PWD);//目前不同模版采用相同key值
			
			String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);

			if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)){//号卡
				orderInfo.setSystemCode(EcsOrderConsts.SYSTEM_CODE_CBSS);
				orderInfo.setBusinessCode(EcsOrderConsts.BUSINESS_CODE_000);

				orderInfo.setSerialNumber(serialNumber);
				orderInfo.setPsPtId(psPtId);

				List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
				ProductInfo productInfo = new ProductInfo();
				//				productInfo.setProductName(productName);
				productInfo.setProductId(productId);
				productInfos.add(productInfo);
				orderInfo.setProductInfo(productInfos);

				List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
				ActivityInfo activityInfo = new ActivityInfo();
				activityInfo.setActivityId(productCode);
				activityInfo.setProductType(productType);
				//				activityInfo.setDeviceImei(deviceImei);//终端串号，号卡不用传
				//				orderInfo.setProductCode(productCode);
				activityInfos.add(activityInfo);
				orderInfo.setActivityInfo(activityInfos);

				List<AttachedInfo> attachedInfos = new ArrayList<AttachedInfo>();
				AttachedInfo attachedInfo = new AttachedInfo();
				packageInfos = new ArrayList<PackageInfo>();
				packageInfo = new PackageInfo();
				packageInfo.setPackageId(packageId_attachedInfo);
				packageInfo.setElementId(elementId_attachedInfo);
				packageInfos.add(packageInfo);
				attachedInfo.setPackageInfo(packageInfos);
				attachedInfo.setFlowId(flowId);
//				attachedInfo.setFlowPackageId(flowPackageId);//删除字段
				attachedInfos.add(attachedInfo);
				orderInfo.setAttachedInfo(attachedInfos);

				List<SpInfo> spInfos = new ArrayList<SpInfo>();
				SpInfo spInfo = new SpInfo();
				spInfo.setSpId(spId);
				spInfo.setSpName(spName);
				spInfos.add(spInfo);
				orderInfo.setSpInfo(spInfos);
				
				orderInfo.setFirstMonBillMode(firstMonBillMode);
			}else if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)){//合约机
				orderInfo.setSystemCode(EcsOrderConsts.SYSTEM_CODE_CBSS);
				orderInfo.setBusinessCode(EcsOrderConsts.BUSINESS_CODE_001);

				orderInfo.setPsPtId(psPtId);
				orderInfo.setSerialNumber(serialNumber);

				List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
				ProductInfo productInfo = new ProductInfo();
				productInfo.setProductId(productId);
				//				productInfo.setProductName(productName);
				productInfos.add(productInfo);
				orderInfo.setProductInfo(productInfos);

				List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
				ActivityInfo activityInfo = new ActivityInfo();
				activityInfo.setActivityId(productCode);
				activityInfo.setDeviceImei(deviceImei);
				activityInfo.setProductType(productType);
				activityInfos.add(activityInfo);
				orderInfo.setActivityInfo(activityInfos);

				List<AttachedInfo> attachedInfos = new ArrayList<AttachedInfo>();
				AttachedInfo attachedInfo = new AttachedInfo();
				packageInfos = new ArrayList<PackageInfo>();
				packageInfo = new PackageInfo();
				packageInfo.setPackageId(packageId_attachedInfo);
				packageInfo.setElementId(elementId_attachedInfo);
				packageInfos.add(packageInfo);
				attachedInfo.setPackageInfo(packageInfos);
				attachedInfo.setFlowId(flowId);
//				attachedInfo.setFlowPackageId(flowPackageId);//删除字段
				attachedInfos.add(attachedInfo);
				orderInfo.setAttachedInfo(attachedInfos);

				List<SpInfo> spInfos = new ArrayList<SpInfo>();
				SpInfo spInfo = new SpInfo();
				spInfo.setSpId(spId);
				spInfo.setSpName(spName);
				spInfos.add(spInfo);
				orderInfo.setSpInfo(spInfos);

				orderInfo.setFirstMonBillMode(firstMonBillMode);
			}else if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)){//裸机
				orderInfo.setSystemCode(EcsOrderConsts.SYSTEM_CODE_ESS);
				orderInfo.setBusinessCode(EcsOrderConsts.BUSINESS_CODE_002);

				orderInfo.setDeviceType(deviceType);
				orderInfo.setDeviceAttach(deviceAttach);
				orderInfo.setDeviceImei(deviceImei);
				orderInfo.setPayType(payType);
				orderInfo.setPayPrice(payPrice);

			}//目前没有其他模版
		}
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.infservices.simulation";
	}

	public EmpOperInfoVo getEssOperInfo() {
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}
