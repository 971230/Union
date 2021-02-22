package com.ztesoft.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;


public class InfCommonUtils {
	
	public static final String WMS_SYS_FLAG = "WMS";
	
	/**
	 * 将实体类拼装为xml报文，并支持数据库新增字段
	 * @param param
	 * @param operCode
	 * @return
	 */
	public static String infXMLConvert(Object param, String operCode, Object... args){
		String result = "";
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			BeanUtils.bean2Map(paramMap, param);
			//动态节点插入、后续搞
			
			result = XmlUtils.mapToXml(paramMap);
			result = result.replaceAll("<root>", "");
			result = result.replaceAll("</root>", "");
			if(args.length > 0 && WMS_SYS_FLAG.equals(args[0])){
				result = result.replaceAll("<", "<tem:");
				result = result.replaceAll("<tem:/", "</tem:");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, String>> getInfNewFields(String operCode){
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "";
		List<Map<String, String>> result = baseDaoSupport.queryForList(sql, operCode);
		
		return result ;
	}
}