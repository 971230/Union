package util;

import java.util.HashMap;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.iservice.IOrderServices;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
@SuppressWarnings("all")
public class EssInfoUtil {
	
	public static final String key = "OPERATOR_REL_TB";// ESS工号绑定关系
	public static final String CORE_CACHE_TYPE = "";
	// 100以下是保留，最大值不能超过10000
	public final static int NAMESPACE_OPERATOR_REL_TB = 1989;

	/**
	 * 根据cb/ess工号获取详情
	 * 如果缓存和数据库中查询不到，或者数据不完整,调接口更新
	 * @param essId
	 * @return
	 * @throws ApiBusiException
	 */
	public static EmpOperInfoVo getEssInfoByEssId(String operId,String orderId,String operIdType) throws ApiBusiException {
		
		if (StringUtils.isEmpty(operId)) {
			throw new ApiBusiException("工号参数为空，无法操作，请检查！");
		}
		if (StringUtils.isEmpty(orderId)) {
			throw new ApiBusiException("订单编号参数为空，无法操作，请检查！");
		}
		if (StringUtils.isEmpty(operIdType)) {
			throw new ApiBusiException("工号类型参数为空，无法操作，请检查！");
		}
				
		String operTypeName = StringUtils.equals(operIdType, EcsOrderConsts.OPER_TYPE_ORDER)?"订单":"CB/ESS";
		
		EmpOperInfoVo empinfo = null;
		
		// 获取ess工号数据
		if(StringUtils.equals(operIdType, EcsOrderConsts.OPER_TYPE_ORDER)){
			// 将订单归属6位地市转换为3位外部编码
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			if (StringUtils.isEmpty(otherCityCode)) {
				throw new ApiBusiException("地市映射失败or非法地市，请检查！订单号："+orderId+",地市：" + otherCityCode);
			}
			
			empinfo = EssOperatorInfoUtil.getECOperatorRelInfo(orderId,operId,otherCityCode);
		}else{
			empinfo = getEssInfo(orderId,operId,operTypeName);
		}
		
		if (empinfo == null) {
			throw new ApiBusiException("ESS员工信息获取失败，请检查"+operTypeName+"工号:"+operId);
		}
		
		return empinfo;
	}
	
	
	public static EmpOperInfoVo getEssInfo(String orderId,String operId,String operTypeName) throws ApiBusiException {
		EmpOperInfoVo empInfo = null;
		//去缓存拿数据
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		empInfo = (EmpOperInfoVo) cache.get(NAMESPACE_OPERATOR_REL_TB, key + operId);
		// 判断是否有缓存
		if (empInfo == null) {
			// 如果为空去查数据库
			empInfo = getEmpInfoFromDataBase(operId);
			
			if (empInfo == null) {//数据库都查不出来，说明初始绑定关系没有配，抛异常
				throw new ApiBusiException("ESS员工信息在数据库不存在，请检查！"+operTypeName+"工号:" + operId);
			} else {
				//数据库里有，则检查数据是否完整，如果完整则返回对象，不完整调接口查询
				if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){
					//数据库如果完整，则把数据加到缓存中
					cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, key + operId, empInfo);		
					return empInfo;
				}else{
					return getEssInfoByInterFace(orderId,operId,operTypeName);
				}
			}
		} else {	
			//有缓存，则检查缓存的数据是否完整，如果完整则返回对象，不完整调接口查询
			if(EssOperatorInfoUtil.checkEmpOperInfoData(empInfo)){		
				return empInfo;
			}else{
				return getEssInfoByInterFace(orderId,operId,operTypeName);
			}
		}

	}
	
	/**
	 * 从数据库中查询工号信息，只取第一条
	 * @param essId
	 * @return
	 */
	public static EmpOperInfoVo getEmpInfoFromDataBase(String essId) {
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select * from Es_OPERATOR_REL_EXT a where a.ESS_EMP_ID ='"+ essId+ "'"+"and a.source_from ='"+ManagerUtils.getSourceFrom()+"' and rownum=1";
		EmpOperInfoVo empIdEssIDRequest = support.queryForObject(sql,EmpOperInfoVo.class);
		return empIdEssIDRequest;
	}
	
	
	/**
	 * 从接口获取工号详情
	 * @return
	 * @throws ApiBusiException
	 */
	public static  EmpOperInfoVo getEssInfoByInterFace(String orderId,String operId,String operTypeName) throws ApiBusiException {
		BusiCompRequest busiComReq = new BusiCompRequest();
		Map<String, String> params = new HashMap<String, String>();
		params.put("essID", operId);
		params.put("orderGonghao", operId);
		busiComReq.setEnginePath("zteCommonTraceRule.essInfoQuery");
		busiComReq.setQueryParams(params);
		busiComReq.setOrder_id(orderId);
		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
		BusiCompResponse busiCompResponse;
		try {
			busiCompResponse = orderServices.execBusiComp(busiComReq);
			//取出最新缓存数据
			INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
			return (EmpOperInfoVo) cache.get(NAMESPACE_OPERATOR_REL_TB, key + operId);
		} catch (Exception e) {
			throw new ApiBusiException("CB(ESS)工号查询接口调用出错，请检查！"+operTypeName+"工工号:" + operId);
		}	
	}
}
