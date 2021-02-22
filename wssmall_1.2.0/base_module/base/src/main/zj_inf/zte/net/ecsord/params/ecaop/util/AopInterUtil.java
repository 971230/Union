package zte.net.ecsord.params.ecaop.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ActivityInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.AopBrdOpenAppReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.BindInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.BoradDiscntInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.InterTvDiscntInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.InterTvInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.InterTvServiceAttr;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.MachineInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.NewUserInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.PackageElement;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ProductInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.AopBrdOpenSubReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.FeeInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.Para;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.PayInfo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class AopInterUtil {
	
	/**
	 * 判断是否是总部的融合业务
	 * @param type_id
	 * @return
	 */
	public static boolean isAopBrdOpenApply(String type_id){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		//规则编号
		String cfg_type = cacheUtil.getConfigInfo("AOP_MIX_BORD_TYPE");
		
		if(StringUtils.isBlank(cfg_type))
			cfg_type = "180441456282001431";
		
		if(StringUtils.isNotBlank(type_id) && cfg_type.equals(type_id))
			return true;
		else
			return false;
	}
	
	/**
	 * 获取融合业务-总部的预提交规则编号
	 * @param order_id
	 * @return
	 */
	public static String getAopBrdPreSubRuleId(String order_id){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		//规则编号
		String rule_id = cacheUtil.getConfigInfo("AOP_MIX_BORD_PRE_SUB_RULE_ID");
		
		if(StringUtils.isBlank(rule_id))
			return "";
		else
			return rule_id;
	}

	/**
	 * 获取操作员信息
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	public static EmpOperInfoVo getEssInfo(String orderId) throws Exception {
		EmpOperInfoVo essOperInfo = new EmpOperInfoVo();
		
		String operatorId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "out_operator");
		String province = "36";
		
		List<OrderDeliveryBusiRequest> delivery = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderDeliveryBusiRequests();
		if(delivery==null || delivery.size()==0)
			throw new Exception("配送信息为空");
		
		String city = String.valueOf(delivery.get(0).getCity_id());
		
		String district = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "district_code");
		String channelId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "channelId");
		String channelType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "channel_type");
		if(!StringUtil.isEmpty(city)){
		    if(city.length()==6){
		        city=CommonDataFactory.getInstance().getOtherDictVodeValue("city", city);//AOP接口 六位转三位编码
		    }
		}
	   /* String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", city);//AOP接口 六位转三位编码*/
		essOperInfo.setEss_emp_id(operatorId);
		essOperInfo.setProvince(province);
		essOperInfo.setCity(city);
		essOperInfo.setDistrict(district);
		essOperInfo.setChannel_id(channelId);
		essOperInfo.setChannel_type(channelType);
		
		return essOperInfo;
	}
	
	public static Integer getOpeSysType(String orderId) {
		Integer opeSysType;
		
		// 暂无其他类型
		// opeSysType = 1;
		String type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.IS_AOP);
		
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(type)) {
			opeSysType = 1;
		} else {
			opeSysType = 2;
		}
		
		return opeSysType;
	}
	
	//获取证件过期时间
	public static String getCertExpireDate(String orderId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		
		Date date = null;
		
		String cert_failure_time = CommonDataFactory.getInstance()
				.getAttrFieldValue(orderId, AttrConsts.CERT_FAILURE_TIME).toString();
		
		if (StringUtil.isEmpty(cert_failure_time)) {
			return "20501231";
		}
		
		try {
			date = sdf.parse(cert_failure_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String certExpireDate = sd.format(date).replace(" ", "");
		
		return certExpireDate;
	}
	
	/**
	 * 获取宽带速率
	 * @param orderId
	 * @return
	 */
	public static String getSpeed_level(String orderId) {
		String goods_id= CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String bss_brand_speed = CommonDataFactory.getInstance().getGoodSpec(orderId, goods_id, "brand_speed");
		
		return bss_brand_speed;
	}
	
	public static List<PackageElement> getPackageElement(String order_id) {
		List<PackageElement> list = new ArrayList<PackageElement>();
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrPackageBusiRequest> p_list = orderTree.getPackageBusiRequests();
		
		if (p_list != null && p_list.size() > 0) {
			for (AttrPackageBusiRequest busi : p_list) {
				PackageElement vo = new PackageElement();
				vo.setPackageId(busi.getPackageCode());
				vo.setElementId(busi.getElementCode());
				vo.setElementType(busi.getElementType());
				list.add(vo);
			}
			return list;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取产品信息
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public static List<ProductInfo> getProduct(String order_id) throws Exception{
		List<ProductInfo> list = new ArrayList<ProductInfo>();
		
		String goods_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getGoods_id();
		
		//读取商品参数配置
		//是否下发云盘
		String sendCloud = CommonDataFactory.getInstance().
				getGoodSpec(order_id, goods_id, "send_cloud");
		//主产品ID
		String main_product_id = CommonDataFactory.getInstance().
				getGoodSpec(order_id, goods_id, "main_product_id");
		
		//不下发资费ID
		String abort_price_id = CommonDataFactory.getInstance().
				getGoodSpec(order_id, goods_id, "abort_price_id");
		
		//不下发资费包ID
		String abort_price_package_id = CommonDataFactory.getInstance().
				getGoodSpec(order_id, goods_id, "abort_price_package_id");
		
		//不下发资费类型
		String abort_price_type = CommonDataFactory.getInstance().
				getGoodSpec(order_id, goods_id, "abort_price_type");
		
		//主产品
		if(StringUtils.isBlank(main_product_id))
			throw new Exception(goods_id + "商品中未配置主产品编号");
		
		ProductInfo mainProduct = new ProductInfo();
		mainProduct.setProductId(main_product_id);
		mainProduct.setProductMode("1");
		
		List<PackageElement> pList = new ArrayList<PackageElement>();
		
		if(StringUtils.isNotBlank(abort_price_id)){
			
			PackageElement pack = new PackageElement();
			pack.setPackageId(abort_price_package_id);
			pack.setElementId(abort_price_id);
			pack.setElementType(abort_price_type);
			pack.setOptType("1");
			pList.add(pack);
		}
		
		if("0".equals(sendCloud)){
			//云盘默认下发，不下发时需要加入报文
			//查询货品中的云盘包编号和元素编号
			String packageId = "";
			String elementId = "";
			
			List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
			
			if(products!=null && products.size()>0){
				for (Goods p : products) {
					
					Map specs = SpecUtils.getProductSpecMap(p);
					
					if (specs != null && !specs.isEmpty()) {
						
						packageId = specs.get("wo_pan_package_id") == null ? 
								EcsOrderConsts.EMPTY_STR_VALUE : specs.get("wo_pan_package_id").toString();
						elementId = specs.get("wo_pan_element_id") == null ? 
								EcsOrderConsts.EMPTY_STR_VALUE : specs.get("wo_pan_element_id").toString();
					}
				}
				
				if(StringUtils.isNotBlank(packageId) && StringUtils.isNotBlank(elementId)){
					
					PackageElement pack = new PackageElement();
					pack.setPackageId(packageId);
					pack.setElementId(elementId);
					pack.setElementType("S");
					pack.setOptType("1");
					pList.add(pack);
				}
			}
		}
		
		if(pList!=null && pList.size()>0)
			mainProduct.setPackageElement(pList);
		
		list.add(mainProduct);

		return list;
	}
	
	/**
	 * 获取活动信息
	 * @param orderId
	 * @return
	 */
	public static List<ActivityInfo> getActivityInfo(String orderId) {
		List<ActivityInfo> activityInfo = new ArrayList<ActivityInfo>();
		
		String goods_id = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests().get(0).getGoods_id();
		
		//活动编号
		String actPlanId = CommonDataFactory.getInstance().getGoodSpec(orderId, goods_id, "active_id");
		
		if(!StringUtil.isEmpty(actPlanId)){
			ActivityInfo activity = new ActivityInfo();
			
			activity.setActPlanId(actPlanId);
			activityInfo.add(activity);
		}
		
		return activityInfo;
	}
	
	/**
	 * 获取资费信息
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public static List<BoradDiscntInfo> getBoradDiscntInfo(String orderId) throws Exception{
		List<BoradDiscntInfo> disInfoList = new ArrayList<BoradDiscntInfo>();
		String goods_id = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests().get(0).getGoods_id();
		
		//主产品资费
		String main_price_id = CommonDataFactory.getInstance().getGoodSpec(orderId, goods_id, "main_price_id");
		
		if(StringUtils.isNotBlank(main_price_id)){
			BoradDiscntInfo disInfo = new BoradDiscntInfo();
			disInfo.setBoradDiscntId(main_price_id);
			disInfoList.add(disInfo);
		}
		
		return disInfoList;
	}
	
	/**
	 * 获取互联网电视节点
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public static List<InterTvInfo> getInterTvInfo(String orderId) throws Exception{
		List<InterTvInfo> interTvInfo = new ArrayList<InterTvInfo>();
		
		String goods_id = CommonDataFactory.getInstance().getOrderTree(orderId).
				getOrderItemBusiRequests().get(0).getGoods_id();
		
		//读取商品参数配置
		//互联网电视产品ID
		String intertv_product_id = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "intertv_product_id");
		//互联网电视服务ID
		String intertv_service_id = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "intertv_service_id");
		//互联网电视资费ID
		String intertv_price_id = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "intertv_price_id");
		//互联网电视终端类型
		String terminalsrcmode = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "terminalsrcmode");
		//互联网电视终端提供方式
		String terminaltype1 = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "terminaltype1");
		//互联网电视平台类型
		String nettvflattype = CommonDataFactory.getInstance().
				getGoodSpec(orderId, goods_id, "nettvflattype");
		
		if(StringUtils.isNotBlank(intertv_product_id)){
			//如果商品参数中有互联网宽带信息，则拼装互联网宽带节点
			InterTvInfo info = new InterTvInfo();
			
			info.setInterTvInfoService(intertv_service_id);
			
			List<InterTvServiceAttr> interTvServiceAttr = new ArrayList<InterTvServiceAttr>();
			
			String account = "";
			String password = "";
			
			List<OrderAdslBusiRequest> adsls = CommonDataFactory.getInstance().
					getOrderTree(orderId).getOrderAdslBusiRequest();
			
			if(adsls==null || adsls.size()==0)
				throw new Exception(orderId + "订单中没有宽带信息");
			
			for(OrderAdslBusiRequest adsl : adsls){
				if("TV".equals(adsl.getProduct_type())){
					account = adsl.getAdsl_account();
					password = adsl.getAdsl_password();
					break;
				}
			}
			
			//互联网电视属性
			//账号
			InterTvServiceAttr nettvnbrAttr = new InterTvServiceAttr();
			nettvnbrAttr.setCode("nettvnbr");
			nettvnbrAttr.setValue(account);
			
			//密码
			InterTvServiceAttr nettvpasswordAttr = new InterTvServiceAttr();
			nettvpasswordAttr.setCode("nettvpassword");
			nettvpasswordAttr.setValue(password);
			
			//互联网电视终端类型
			InterTvServiceAttr terminalsrcmodeAttr = new InterTvServiceAttr();
			terminalsrcmodeAttr.setCode("terminalsrcmode");
			terminalsrcmodeAttr.setValue(terminalsrcmode);
			
			//互联网电视终端提供方式
			InterTvServiceAttr terminaltype1Attr = new InterTvServiceAttr();
			terminaltype1Attr.setCode("terminaltype1");
			terminaltype1Attr.setValue(terminaltype1);
			
			//互联网电视平台类型
			InterTvServiceAttr nettvflattypeAttr = new InterTvServiceAttr();
			nettvflattypeAttr.setCode("nettvflattype");
			nettvflattypeAttr.setValue(nettvflattype);
			
			//其它属性
			InterTvServiceAttr developdepartidAttr = new InterTvServiceAttr();
			developdepartidAttr.setCode("developdepartid");
			developdepartidAttr.setValue("");
			
			InterTvServiceAttr developdepartnameAttr = new InterTvServiceAttr();
			developdepartnameAttr.setCode("developdepartname");
			developdepartnameAttr.setValue("");
			
			InterTvServiceAttr developerstaffidAttr = new InterTvServiceAttr();
			developerstaffidAttr.setCode("developerstaffid");
			developerstaffidAttr.setValue("");
			
			InterTvServiceAttr developstaffnameAttr = new InterTvServiceAttr();
			developstaffnameAttr.setCode("developstaffname");
			developstaffnameAttr.setValue("");
			
			InterTvServiceAttr terminalbrandAttr = new InterTvServiceAttr();
			terminalbrandAttr.setCode("terminalbrand");
			terminalbrandAttr.setValue("");
			
			InterTvServiceAttr terminalmacAttr = new InterTvServiceAttr();
			terminalmacAttr.setCode("terminalmac");
			terminalmacAttr.setValue("");
			
			InterTvServiceAttr terminalmodelAttr = new InterTvServiceAttr();
			terminalmodelAttr.setCode("terminalmodel");
			terminalmodelAttr.setValue("");
			
			InterTvServiceAttr terminalsnAttr = new InterTvServiceAttr();
			terminalsnAttr.setCode("terminalsn");
			terminalsnAttr.setValue("");
			
			interTvServiceAttr.add(nettvnbrAttr);
			interTvServiceAttr.add(nettvpasswordAttr);
			interTvServiceAttr.add(terminalsrcmodeAttr);
			interTvServiceAttr.add(terminaltype1Attr);
			interTvServiceAttr.add(nettvflattypeAttr);
			
			interTvServiceAttr.add(developdepartidAttr);
			interTvServiceAttr.add(developdepartnameAttr);
			interTvServiceAttr.add(developerstaffidAttr);
			interTvServiceAttr.add(developstaffnameAttr);
			interTvServiceAttr.add(terminalbrandAttr);
			
			interTvServiceAttr.add(terminalmacAttr);
			interTvServiceAttr.add(terminalmodelAttr);
			interTvServiceAttr.add(terminalsnAttr);
			
			
			info.setInterTvServiceAttr(interTvServiceAttr);
			
			//宽带资费
			List<InterTvDiscntInfo> InterTvDiscntInfoList = new ArrayList<InterTvDiscntInfo>();
			
			if(StringUtils.isNotBlank(intertv_price_id)){
				InterTvDiscntInfo intertv_month_price = new InterTvDiscntInfo();
				intertv_month_price.setInterTvDiscntId(intertv_price_id);
				
				InterTvDiscntInfoList.add(intertv_month_price);
			}
			
			if(InterTvDiscntInfoList!=null && InterTvDiscntInfoList.size()>0)
				info.setInterTvDiscntInfo(InterTvDiscntInfoList);
			
			interTvInfo.add(info);
		}
		
		return interTvInfo;
	}
	
	//拼装用户信息
	public static NewUserInfo getUserInfo(String orderId,boolean isOldCust) throws Exception{
		NewUserInfo userInfo = new NewUserInfo();
		
		String certName = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
				AttrConsts.PHONE_OWNER_NAME);
		String type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERTI_TYPE);
		String certType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", type);
		String certNum = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NUM);
		String certAddress = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS);
		String certExpireDate = getCertExpireDate(orderId);
		String speed = getSpeed_level(orderId);
		
		userInfo.setCertType(certType);
		userInfo.setCertName(certName);
		userInfo.setCertNum(certNum);
		userInfo.setCertAddress(certAddress);
		userInfo.setCertExpireDate(certExpireDate);
		
		userInfo.setSpeedLevel(speed);
		
		//宽带信息
		OrderAdslBusiRequest adslInfo = null;
		
		List<OrderAdslBusiRequest> adsls = CommonDataFactory.getInstance().
				getOrderTree(orderId).getOrderAdslBusiRequest();
		
		if(adsls==null || adsls.size()==0)
			throw new Exception(orderId + "订单中没有宽带信息");
		
		for(int i=0;i<adsls.size();i++){
			if("KD".equals(adsls.get(i).getProduct_type())){
				adslInfo = adsls.get(i);
				break;
			}
		}
		
		if(adslInfo == null)
			throw new Exception(orderId + "订单中没有宽带为空");
		
		String addressCode = adslInfo.getAdsl_addr();
		String user_address = adslInfo.getUser_address();
		String cbssAccessMode = adslInfo.getAccess_type();
		String hopeDate = adslInfo.getAppt_date();
		
		if(StringUtils.isNotBlank(hopeDate)){
			hopeDate = hopeDate.replace("-", "");
			hopeDate = hopeDate.replace("/", "");
			hopeDate = hopeDate.replace(":", "");
			hopeDate = hopeDate.replace(" ", "");
		}else{
			Date now = new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			hopeDate = format.format(now);
		}
		
		String exchCode = adslInfo.getExch_code();
		String gridCode = adslInfo.getAdsl_grid();
		
		String areaExchId  = adslInfo.getArea_exch_id();
		String pointExchId = adslInfo.getPoint_exch_id();
	
		//宽带地址
		userInfo.setAddressCode(addressCode);
		userInfo.setAddressName(user_address);
		userInfo.setInstallAddress(user_address);
		
		userInfo.setAccessMode(cbssAccessMode);
//		userInfo.setCbssAccessMode(cbssAccessMode);
		userInfo.setHopeDate(hopeDate);
		userInfo.setExchCode(exchCode);
		
		userInfo.setAreaExchId(areaExchId);//区局
		userInfo.setPointExchId(pointExchId);//端局
		
		userInfo.setGridCode(gridCode);
		
		if(isOldCust)
			userInfo.setCreateOrExtendsAcct("1");
		else
			userInfo.setCreateOrExtendsAcct("0");

		userInfo.setConstructionTime("0");
		
		//获取产品信息
		List<ProductInfo> productList = getProduct(orderId);
		if(productList!=null && productList.size()>0)
			userInfo.setProductInfo(productList);
		
		//获取活动信息
		List<ActivityInfo> activeList = getActivityInfo(orderId);
		if(activeList!=null && activeList.size()>0)
			userInfo.setActivityInfo(activeList);
		
		//获取资费信息
		List<BoradDiscntInfo> disInfoList = getBoradDiscntInfo(orderId);
		if(disInfoList!=null && disInfoList.size()>0)
			userInfo.setBoradDiscntInfo(disInfoList);
		
		String goods_id = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests().get(0).getGoods_id();
		
		//读取商品参数配置
		String intertv_product_id = CommonDataFactory.getInstance().getGoodSpec(orderId, goods_id, "intertv_product_id");
		if(StringUtils.isNotBlank(intertv_product_id))
			userInfo.setInterTvProductId(intertv_product_id);
		
		//获取互联网电视节点
		List<InterTvInfo> InterTvInfo = getInterTvInfo(orderId);
		if(InterTvInfo!=null && InterTvInfo.size()>0)
			userInfo.setInterTvInfo(InterTvInfo);
		
		String bindSerialNumber = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "service_num");
		userInfo.setDebutySerialNumber(bindSerialNumber);
		
		return userInfo;
	}
	
	/**
	 * 获取终端信息
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public static List<MachineInfo> getMachineInfo(String order_id) throws Exception{
		String goods_id= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getGoods_id();
		String machine_provide = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "machine_provide");
		String machine_type = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "machine_type");
		
		List<MachineInfo> machineInfo = new ArrayList<MachineInfo>();
		
		MachineInfo info = new MachineInfo();
		
		info.setMachineType(machine_type);
		info.setMachineProvide(machine_provide);
		
		machineInfo.add(info);
		
		return machineInfo;
	}
	
	public static AopBrdOpenAppReq getAopBrdOpenAppReq(String orderId) throws Exception{
		AopBrdOpenAppReq req = new AopBrdOpenAppReq();
		
		req.setNotNeedReqStrOrderId(orderId);
		
		//获取操作员信息
		EmpOperInfoVo operInfo = getEssInfo(orderId);
		
		if(null == operInfo)
			throw new Exception("获取操作员信息失败！");
		
		//操作员，地市信息
		String operatorId = operInfo.getEss_emp_id();
		String province = operInfo.getProvince();
		String city = operInfo.getCity();
		String district = operInfo.getDistrict();
		String channelId = operInfo.getChannel_id();
		String channelType = operInfo.getChannel_type();
		
		req.setOperatorId(operatorId);
		req.setProvince(province);
		req.setCity(city);
		req.setDistrict(district);
		req.setChannelId(channelId);
		req.setChannelType(channelType);
		
		int opeSysType = getOpeSysType(orderId);
		req.setOpeSysType(String.valueOf(opeSysType));
		
		//TODO 扣款标示待确定
		req.setDeductionTag(EcsOrderConsts.AOP_IS_NO);
		
		String markingTag = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.MARKING_TAG);
		req.setMarkingTag(markingTag);
		
		String saleModType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SALE_MOD_TYPE);
		req.setSaleModType(saleModType);
		
		//宽带账号
		//宽带信息
		OrderAdslBusiRequest adslInfo = null;
		String AUTH_ACCT_ID = "";// 宽带认证账号
		String SERIAL_NUMBER = "";// 宽带统一编码
		String passowrd = "";//密码
		
		List<OrderAdslBusiRequest> adsls = CommonDataFactory.getInstance().
				getOrderTree(orderId).getOrderAdslBusiRequest();
		
		if(adsls==null || adsls.size()==0)
			throw new Exception(orderId + "订单中没有宽带信息");
		
		for(int i=0;i<adsls.size();i++){
			if("KD".equals(adsls.get(i).getProduct_type())){
				adslInfo = adsls.get(i);
				break;
			}
		}
		
		if(adslInfo == null)
			throw new Exception(orderId + "订单中没有宽带为空");
		
		AUTH_ACCT_ID = adslInfo.getAdsl_account();
		SERIAL_NUMBER = adslInfo.getAdsl_number();
		passowrd = adslInfo.getAdsl_password();
		
		req.setAuthAcctId(AUTH_ACCT_ID);
		req.setSerialNumber(SERIAL_NUMBER);
		req.setUserName(AUTH_ACCT_ID);
		req.setUserPasswd(passowrd);
		
		//总部订单编号
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ZB_INF_ID);
		req.setOrderNo(orderNo);
		
		//判断新老客户
		boolean isOldCust = false;
		
		if("1".equals(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.IS_OLD))){
			isOldCust = true;
		}
		
		if(isOldCust){
			//4G用户绑定宽带，设置绑定信息
			req.setCustType("1");
			
			String custId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CUST_ID);
			req.setCustId(custId);
			
			List<BindInfo> bindList = new ArrayList<BindInfo>();
			BindInfo bindInfo = new BindInfo();
			
			String bindSerialNumber = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "service_num");
			String bindType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "bind_type");
			String bindUserId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "subs_id");
			
			bindInfo.setBindSerialNumber(bindSerialNumber);
			bindInfo.setBindType(bindType);
			bindInfo.setBindSrc("0");
			bindInfo.setBindUserId(bindUserId);
			bindList.add(bindInfo);
			req.setBindInfo(bindList );		
		}else{
			req.setCustType("0");
		}
		
		//用户信息
		NewUserInfo userInfo = getUserInfo(orderId,isOldCust);
		
		req.setNewUserInfo(userInfo);
		
		//联系人
		String contactPerson = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_NAME);
		String contactPhone = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REVEIVER_MOBILE);
		String contactAddress = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ADDR);
		
		req.setContactPerson(contactPerson); 
		req.setContactPhone(contactPhone); // Y 联系人电话>6位
		req.setContactAddress(contactAddress); // N 通讯地址
		
		//发展人
		String recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(orderId,AttrConsts.DEVELOPMENT_CODE);
		String recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(orderId,AttrConsts.DEVELOPMENT_NAME);

		req.setRecomPersonId(recomPersonId);
		req.setRecomPersonName(recomPersonName);
		
		//终端信息
		List<MachineInfo> machineInfo = getMachineInfo(orderId);
		req.setMachineInfo(machineInfo);

		return req;
	}
	
	public static String getOrigTotalFee(String orderId) {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
		List<OrderItemBusiRequest> orderItemBusiRequests =orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		String OrigTotalFee = orderItemExtBusiRequest.getTotalFee();
		return OrigTotalFee;
	}
	
	public static List<FeeInfo> getFeeInfo(String orderId) {
		List<FeeInfo> feeInfo = new ArrayList<FeeInfo>();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		List<AttrFeeInfoBusiRequest> list = orderTree.getFeeInfoBusiRequests();
		
		for (AttrFeeInfoBusiRequest req : list) {
			if (StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())) {
				FeeInfo vo = new FeeInfo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());// 收费项科目
				vo.setFeeDes(req.getFee_item_name());

				String origFee = req.getO_fee_num() * 1000 + "";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));

				String reliefFee = req.getDiscount_fee() * 1000 + "";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));

				String realFee = req.getN_fee_num() * 1000 + "";
				vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));

				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason()) ? "无" : req.getDiscount_reason());
				vo.setCalculateTag("N");
				
				feeInfo.add(vo);
			}
		}
		
		if (feeInfo != null && feeInfo.isEmpty()) {
			feeInfo = null;
		}
		
		return feeInfo;
	}
	
	public static PayInfo getPayInfo(String orderId) throws Exception{
		PayInfo payInfo = new PayInfo();
		
		payInfo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.BILL_TYPE)); // 付费方式
		payInfo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
		payInfo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.BANK_ACCOUNT)); // 支付账号
		
		DecimalFormat df = new DecimalFormat("#");  
		Double payfee = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest().getOrder_amount();
		String origFee = df.format(payfee*1000);
		payInfo.setPayFee(origFee);
		
		//TODO 待确定
		payInfo.setPayMode("0");
		
		return payInfo;
	}
	
	public static List<Para> getPara(String orderId) {		
		//4G的加参数
		List<Para> para = new ArrayList<Para>();
		Para paraVo=new Para();
		
		String net_type = CommonDataFactory.getInstance().
				getProductSpec(orderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		
		if(StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)){
			paraVo.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
			paraVo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
			para.add(paraVo);
		}
		
		if(para!=null&&para.isEmpty()){
			para=null;
		}
		
		return para;
	}
	
	public static AopBrdOpenSubReq getAopBrdOpenSubReq(String orderId) throws Exception{
		AopBrdOpenSubReq req = new AopBrdOpenSubReq();
		
		req.setNotNeedReqStrOrderId(orderId);
		
		EmpOperInfoVo operInfo = getEssInfo(orderId);
		
		req.setOperatorId(operInfo.getEss_emp_id());
		req.setProvince(operInfo.getProvince());
		req.setCity(operInfo.getCity());
		req.setDistrict(operInfo.getDistrict());
		req.setChannelId(operInfo.getChannel_id());
		req.setChannelType(operInfo.getChannel_type());
		
		int opeSysType = getOpeSysType(orderId);
		
		req.setOpeSysType(String.valueOf(opeSysType));
		
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ZB_INF_ID));
		
		String provOrderId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ACTIVE_NO);
		
		req.setProvOrderId(provOrderId);
		req.setOrigTotalFee(getOrigTotalFee(orderId));
		
		//费用信息
		req.setFeeInfo(getFeeInfo(orderId));
		
		//支付信息
		req.setPayInfo(getPayInfo(orderId));
		
		req.setPara(getPara(orderId));
		
		return req;
	}
}
