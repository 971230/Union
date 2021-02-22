package com.ztesoft.net.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.EsearchSpec;

import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.service.IOrderExpManager;
import commons.CommonTools;

public class OrderExpUtils {
	
	public static String getCacheName(String code,String key){
		String  cacheName = "";
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List list = dc.loadData(code);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if(value.equals(key)){
					cacheName = (String) map.get("value_desc");
					return cacheName;
				}
				
			}
		}
		
		return cacheName;
	}
	
	/**
	 * 批量执行解决方案SQL
	 * @param sqls
	 * @param order_id
	 * @return
	 */
	public static int excuteSolutionSql(String sqls,String order_id){
		IOrderExpManager orderExpManager = SpringContextHolder.getBean("orderExpManager");
		int result = 0;
		try {
			result = orderExpManager.excuteSolutionSql(sqls, order_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取需要解析的报文
	 * @return
	 */
	public static Map getEsearchParam(String search_code,String out_param,String in_param){
		Map<String,String> map = new HashMap<String,String>();
		IDcPublicInfoManager dcPublicInfoManager = (IDcPublicInfoManager)SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicInfoCacheProxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<EsearchSpec> specList = dcPublicInfoManager.getSpecDefine(search_code,"");
		EsearchSpec esearchSpec = new EsearchSpec();
		if(specList!=null && !specList.isEmpty()){
			esearchSpec = specList.get(0);
			map.put("search_id", esearchSpec.getSearch_id());
			map.put("flag", esearchSpec.getFlag());
		}else{
			CommonTools.addError("-9999", "请先配置规格定义表：搜索编码为"+search_code);
		}
		if(StringUtils.equals(EcsOrderConsts.EXP_OUT_PARAM,esearchSpec.getSearch_field())){
			map.put("param",out_param);
		}else{
			map.put("param",in_param);
		}
		return map;
	}
	
}
