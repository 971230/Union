package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.iservice.IOrderServices;
@SuppressWarnings("all")
public class EssOperatorInfoUtil {
	private static Logger logger = Logger.getLogger(EssOperatorInfoUtil.class);
	public static final String key = "OPERATOR_REL_TB";// CB(ESS)工号绑定关系
	public static final String CORE_CACHE_TYPE = "";
	// 100以下是保留，最大值不能超过10000
	public final static int NAMESPACE_OPERATOR_REL_TB = 1989;
	
	/**
	 * 根据订单Id，获取订单锁定工号，再根据工号获取绑定的ess工号信息
	 * 先查缓存，缓存没有则调用工号查询接口
	 * @param orderIdParam
	 * @return
	 * @throws ApiBusiException
	 */
	public static EmpOperInfoVo getEssInfoByOrderId(String orderId) throws ApiBusiException {
		if (StringUtils.isEmpty(orderId)) {
			throw new ApiBusiException("订单号为空，请检查！");
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		//获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		EmpOperInfoVo empinfo = new EmpOperInfoVo();
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		//获取订单工号和地市
		Map mapParm = getLookUserIdAndCityCodeByOrder(orderId);
		String orderOperId= mapParm.get("lockUserId").toString();
		String cityCode= mapParm.get("otherCityCode").toString();
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			empinfo = getECOperatorRelInfos(orderId,orderOperId,cityCode);
			
			if(null == empinfo){
				empinfo = new EmpOperInfoVo();
			}
		}else{
			// 获取CB(ESS)工号数据
			empinfo = getECOperatorRelInfo(orderId,orderOperId,cityCode);
				
			if (empinfo == null) {
				throw new ApiBusiException("CB(ESS)工号信息获取失败!订单系统工号:"+orderOperId+",地市"+cityCode);
			}
		}
		
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String flow_code = getFlowCode(orderId);
			// 自定义流程，检查自定义流程的操作员、操作点是否配置
			String order_from_operator = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_ID_FLAG_"+orderExt.getOrder_from());
			String order_from_channelId = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_ID_FLAG_"+orderExt.getOrder_from());
			String order_from_channelType = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_TYPE_FLAG_"+orderExt.getOrder_from());
			
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			empinfo.setCity(otherCityCode);
			
			if(StringUtil.isEmpty(order_from_operator)){
				// 根据来源未取到配置，根据flow_code取人员配置
				
				if(!StringUtil.isEmpty(flow_code)){
					order_from_operator = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_ID_FLAG_"+flow_code);
					order_from_channelId = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_ID_FLAG_"+flow_code);
					order_from_channelType = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_TYPE_FLAG_"+flow_code);
				}
			}
			
			if(!StringUtil.isEmpty(order_from_operator)){
				empinfo.setEss_emp_id(order_from_operator);
				empinfo.setChannel_id(order_from_channelId);
				empinfo.setChannel_type(order_from_channelType);
			}
		} 
		
		return empinfo;
	}
	
	/**
	 * 根据订单编号查询流程编码
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	private static String getFlowCode(String order_id) throws ApiBusiException{
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "SELECT a.flow_code FROM es_work_custom_workflow_ins a WHERE a.order_id='"+order_id+"'";
		
		return support.queryForString(sql);
	}

	/**
	 * 根据订单id取操作工号和地市，地市转码3位
	 * @throws ApiBusiException
	 */
	public static Map getLookUserIdAndCityCodeByOrder(String orderId) throws ApiBusiException {
		Map mapParam = new HashMap();
		//获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest=orderTree.getOrderLockBusiRequests();
		OrderLockBusiRequest orderLockBusiRequest=null;
		if(null != orderLockRequest && orderLockRequest.size() > 0){
			for(int i=0;i<orderLockRequest.size();i++){
				if(orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())){
					orderLockBusiRequest=orderLockRequest.get(i);
				}
			}
		}
		
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			
		// 获取环节
		String lockUserId = "";
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		// 检查订单锁定状态
//		String lock_status = orderTree.getOrderExtBusiRequest().getLock_status();
		String lock_status=EcsOrderConsts.UNLOCK_STATUS;
		if(null != orderLockBusiRequest){
			lock_status = orderLockBusiRequest.getLock_status();
		}		
		// 根据环节判断工号取值
		if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
			if (EcsOrderConsts.UNLOCK_STATUS.equals(lock_status)) {// 未锁定
				//取不到锁定工号,就取默认工号
				lockUserId = CommonDataFactory.getInstance().getOperatorCode(orderId);
			}else {//锁定
//				lockUserId = orderTree.getOrderExtBusiRequest().getLock_user_id();
				lockUserId=orderLockBusiRequest.getLock_user_id();
			}
		} else {
			if (EcsOrderConsts.UNLOCK_STATUS.equals(lock_status)) {// 未锁定
				// 预处理处理的时候锁定的user_id
				lockUserId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PRE_LOCK_USER_ID);
			} else {//锁定
//				lockUserId = orderTree.getOrderExtBusiRequest().getLock_user_id();
				lockUserId=orderLockBusiRequest.getLock_user_id();
			}
		}
		//集中写卡模式订单货品默认锁定工号
		if(EcsOrderConsts.OPER_MODE_XK.equals(orderExtBusiRequest.getOrder_model())
				|| EcsOrderConsts.OPER_MODE_XXAPP.equals(orderExtBusiRequest.getOrder_model())){
			List lockList = dcPublicCache.getdcPublicExtList(AttrConsts.PRE_LOCK_USER_ID);
			if(lockList!=null){
				Map map = (Map)lockList.get(0);
				lockUserId = (String)map.get("pname");
			}else{
				lockUserId = EcsOrderConsts.LOCK_USER_ID_JZ_XK;
			}
		}
		
		//还取不到，抛异常
		if (StringUtils.isEmpty(lockUserId)) {
			throw new ApiBusiException("未取到订单的锁定工号，请检查！内部订单号：" + orderId);
		}
		// 将订单归属6位地市转换为3位外部编码
		String cityId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE);
		String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
		if (StringUtils.isEmpty(otherCityCode)) {
			throw new ApiBusiException("地市映射失败or非法地市，请检查！地市：" + otherCityCode);
		}
		mapParam.put("otherCityCode", otherCityCode);
		mapParam.put("lockUserId", lockUserId);
		return mapParam;
	}

	/**
	 * 去缓存中cb工号数据，取不到则查数据库，并检查数据完整性，不完整则调接口
	 * @return
	 * @throws ApiBusiException
	 */
	public static EmpOperInfoVo getECOperatorRelInfo(String orderId,String orderOperId,String cityCode) throws ApiBusiException {
		EmpOperInfoVo empInfo = null;
		//去缓存拿数据
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		empInfo = (EmpOperInfoVo) cache.get(NAMESPACE_OPERATOR_REL_TB, key + orderOperId + cityCode);
		// 判断是否有缓存
		if (empInfo == null) {
			// 如果为空去查数据库
			empInfo = getEmpInfoFromDataBase(orderId,orderOperId,cityCode);
			
			if (empInfo == null) {//数据库都查不出来，说明初始绑定关系没有配，抛异常
				throw new ApiBusiException("CB(ESS)工号→订单工号绑定关系未获取到，请检查！订单系统工号:" + orderOperId+",地市"+cityCode);
			} else {
				//数据库里有，则检查数据是否完整，如果完整则返回对象，不完整调接口查询
				if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){
					//数据库如果完整，则把数据加到缓存中
					cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, key + orderOperId + cityCode, empInfo);		
					return empInfo;
				}else{
					return getEssInfoByInterFace(empInfo,orderId,orderOperId,cityCode);
				}
			}
		} else {	
			//有缓存，则检查缓存的数据是否完整，如果完整则返回对象，不完整调接口查询
			if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){		
				return empInfo;
			}else{
				return getEssInfoByInterFace(empInfo,orderId,orderOperId,cityCode);
			}
		}

	}
	
	
	/**
	 * 去缓存中cb工号数据，取不到则查数据库，并检查数据完整性，不完整则调接口
	 * @return
	 * @throws ApiBusiException
	 */
	public static EmpOperInfoVo getECOperatorRelInfos(String orderId,String orderOperId,String cityCode) throws ApiBusiException {
		EmpOperInfoVo empInfo = null;
		//去缓存拿数据
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		empInfo = (EmpOperInfoVo) cache.get(NAMESPACE_OPERATOR_REL_TB, key + orderOperId + cityCode);
		// 判断是否有缓存
		if (empInfo == null) {
			// 如果为空去查数据库
			empInfo = getEmpInfoFromDataBase(orderId,orderOperId,cityCode);
			
			if (empInfo != null) {//数据库都查不出来，说明初始绑定关系没有配，抛异常
				//数据库里有，则检查数据是否完整，如果完整则返回对象，不完整调接口查询
				if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){
					//数据库如果完整，则把数据加到缓存中
					cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, key + orderOperId + cityCode, empInfo);		
					return empInfo;
				}else{
					return getEssInfoByInterFace(empInfo,orderId,orderOperId,cityCode);
				}
			} else {
				return empInfo;
			}
		} else {	
			//有缓存，则检查缓存的数据是否完整，如果完整则返回对象，不完整调接口查询
			if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){		
				return empInfo;
			}else{
				return getEssInfoByInterFace(empInfo,orderId,orderOperId,cityCode);
			}
		}

	}
	
	/**
	 * 根据订单工号+地市获取数据库中cb工号信息
	 * @return
	 */
	public static EmpOperInfoVo getEmpInfoFromDataBase(String orderId,String orderOperId,String cityCode) {
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='"+ orderOperId+ "' and a.CITY='"+ cityCode+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		EmpOperInfoVo empIdEssIDRequest = support.queryForObject(sql,EmpOperInfoVo.class);
		return empIdEssIDRequest;
	}
	
	/**
	 * 接口获取CB工号信息
	 * @return
	 * @throws ApiBusiException
	 */
	public static  EmpOperInfoVo getEssInfoByInterFace (EmpOperInfoVo empinfo,String orderId,String orderOperId,String cityCode) throws ApiBusiException {
		//CB(ESS)工号信息不完整则调接口
		BusiCompRequest busiComReq = new BusiCompRequest();
		Map<String, String> params = new HashMap<String, String>();
		params.put("essID", empinfo.getEss_emp_id());
		params.put("orderGonghao", orderOperId);
		busiComReq.setEnginePath("zteCommonTraceRule.essInfoQuery");
		busiComReq.setQueryParams(params);
		busiComReq.setOrder_id(orderId);
		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
		BusiCompResponse busiCompResponse;
		try {
			busiCompResponse = orderServices.execBusiComp(busiComReq);
		} catch (Exception e) {
			throw new ApiBusiException("CB(ESS)工号查询接口调用出错，请检查！订单系统工号:" + orderOperId+",地市"+cityCode);
		}	
		if(StringUtils.equals(busiCompResponse.getError_code(),EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)){	
			throw new ApiBusiException("CB(ESS)工号查询接口调用出错，请检查！订单系统工号:" + orderOperId+",地市"+cityCode+"！接口错误描述:"+busiCompResponse.getError_msg());
		}else{
			//取出最新缓存数据
			INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
			return (EmpOperInfoVo) cache.get(NAMESPACE_OPERATOR_REL_TB, key + orderOperId + cityCode);	
		}
	}
	
	/**
	 * 根据订单工号刷新工号-地市缓存对象
	 * @param orderOperId
	 */
	public static void refreshorderOperIdInfoCache(String orderOperId){
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='"+ orderOperId+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		List<EmpOperInfoVo> empInfo = support.queryForList(sql,EmpOperInfoVo.class);
		INetCache cache = CacheFactory.getCacheByType(EssOperatorInfoUtil.CORE_CACHE_TYPE);
		EmpOperInfoVo essInfo  = null;
		if(empInfo.size()>0){
			for(int i = 0 ;i<empInfo.size();i++){
				essInfo = empInfo.get(i);
				//cache.delete(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, EssOperatorInfoUtil.key + essInfo .getOrder_gonghao() + "510");
				cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, EssOperatorInfoUtil.key + essInfo .getOrder_gonghao() + essInfo .getCity(),essInfo);
			}
		}
	}
	
	
	/**
	 * 根据订单工号获取绑定信息列表
	 * @param orderOperId
	 */
	public static List<EmpOperInfoVo> getBindRelByOrderGonghao(String orderOperId){
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='"+ orderOperId+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		List<EmpOperInfoVo> empInfo = support.queryForList(sql,EmpOperInfoVo.class);
		return empInfo;
	}
	
	/**
	 * 检查工号信息是否完整
	 * @param empinfo
	 * @return
	 */
	public static boolean checkEmpOperInfoData (EmpOperInfoVo empinfo){
		if (StringUtils.isEmpty(empinfo.getChannel_id()) 
				|| StringUtils.isEmpty(empinfo.getChannel_type())
				|| StringUtils.isEmpty(empinfo.getStaffName())) {
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据订单工号删除绑定关系(先删缓存，再删数据库)
	 * @param orderOperId
	 */
	public static void deleteBindRelByOrderGonghao(String orderOperId){
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		//查出工号绑定的所有地市
		String sql = "select city from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='"+ orderOperId+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		List cities = support.queryForList(sql);
		INetCache cache = CacheFactory.getCacheByType(EssOperatorInfoUtil.CORE_CACHE_TYPE);
		
		//删缓存
		try{
			if(null!=cities&&cities.size()>0){
				for(Object city:cities){
					Map s = (Map)city;
					cache.delete(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, EssOperatorInfoUtil.key + orderOperId + s.get("city"));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.info("工号"+orderOperId+"绑定关系缓存删除失败");
			//删缓存失败，旧的绑定关系可以继续使用
		}
		String sql_delbd = "delete from es_operator_rel_ext a where a.order_gonghao ='"+ orderOperId+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		support.executeProceduce(sql_delbd);//删除数据库记录
	}
	
	/**
	 * 3G存费送费接口，根据订单来源取工号
	 * 先查缓存，缓存没有则调用工号查询接口
	 * @param orderId
	 * @return
	 * @throws ApiBusiException
	 */
	public static EmpOperInfoVo getEssInfoByOrderFrom(String orderId) throws ApiBusiException {
		if (StringUtils.isEmpty(orderId)) {
			throw new ApiBusiException("订单号为空，请检查！");
		}
		//获取订单工号和地市
		Map mapParm = getLookUserIdAndCityCodeByOrderFrom(orderId);
		String orderOperId= mapParm.get("lockUserId").toString();
		String cityCode= mapParm.get("otherCityCode").toString();
		EmpOperInfoVo empinfo = null;
		
		// 获取CB(ESS)工号数据
		empinfo = EssOperatorInfoUtil.getECOperatorRelInfo(orderId,orderOperId,cityCode);
		if (empinfo == null) {
			throw new ApiBusiException("CB(ESS)工号信息获取失败!订单系统工号:"+orderOperId+",地市"+cityCode);
		}
		
		return empinfo;
	}
	
	/**
	 * 根据订单获取订单来源、根据来源获取操作工号、再根据操作工号取绑定的CB工号
	 * @throws ApiBusiException
	 */
	public static Map getLookUserIdAndCityCodeByOrderFrom(String orderId) throws ApiBusiException {
		Map mapParam = new HashMap();
		//根据订单获取来源，根据来源获取绑定的工号
		//select a.pkey value, a.pname value_desc from es_dc_public_ext a where a.stype='operator_code';
		String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(orderId);
		if(StringUtils.isEmpty(opercode)){
			throw new ApiBusiException("该订单的来源未配置工号关联，请检查！orderId：" + orderId);
		}
		// 将订单归属6位地市转换为3位外部编码
		String cityId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE);
		String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
		if (StringUtils.isEmpty(otherCityCode)) {
			throw new ApiBusiException("地市映射失败or非法地市，请检查！地市：" + otherCityCode);
		}
		mapParam.put("otherCityCode", otherCityCode);
		mapParam.put("lockUserId", opercode);
		return mapParam;
	}
	
}