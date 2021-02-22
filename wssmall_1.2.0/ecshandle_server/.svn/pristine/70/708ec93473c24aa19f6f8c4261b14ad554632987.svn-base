package com.ztesoft.net.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.filter.IFilter;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.ExpFilterResponse;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class CommonFilterFactory {
	private final static String EXP_FILTER_KEY = "exp";
	private final ConcurrentMap<String, ArrayList<String>> filterMap = new ConcurrentHashMap<String, ArrayList<String>>();
	
	public static CommonFilterFactory getInstance(){
		return FilterFactoryLoader.instance;
	}
	
	private static class FilterFactoryLoader {
		public static CommonFilterFactory instance = new CommonFilterFactory();
	}
	
	public CommonFilterFactory(){
		init();
	}
	
	private void init() {
		ArrayList<String> expfilter = new ArrayList<String>();
		List dcList = CommonDataFactory.getInstance().listDcPublicData("expfilter");
		for(int i=0;i<dcList.size();i++){
			Map map = (Map)dcList.get(i);
			expfilter.add(map.get("pkey").toString());
		}
		filterMap.put(EXP_FILTER_KEY, expfilter);
	}
	
	private ArrayList<FilterResponse> doFilter(ArrayList<String> filters, FilterRequest request) {
		ArrayList<FilterResponse> responses = new ArrayList<FilterResponse>();
		if(filters != null && filters.size() > 0) {
			for(String beanName:filters) {
				try{
					IFilter filter = SpringContextHolder.getBean(beanName);
					FilterResponse response = filter.doFilter(request);
					responses.add(response);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return responses;
	}
	
	public ArrayList<FilterResponse> doExpFilter(ExpFilterRequest request) {
		ArrayList<String> filters = filterMap.get(EXP_FILTER_KEY);
		return doFilter(filters, request);
	}
	
	public String readExpFilterResonse(ArrayList<FilterResponse> resps) {
		if(resps == null || resps.size() < 1) {
			return EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_VISIBLE;
		}
		
		Map<String,String> write_flags = new HashMap<String,String>(); 
		for(FilterResponse resp:resps) {
			ExpFilterResponse expResp= (ExpFilterResponse)resp;
			if(StringUtils.isNotEmpty(expResp.getWrite_flag())) {
				write_flags.put(expResp.getWrite_flag(), expResp.getWrite_flag());
			}
		}
		
		if(write_flags.get(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE) != null) {//不写入，优先级最高
			return EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE;
		}
		
		if(write_flags.get(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_INVISIBLE) != null) {//写入不展示
			return EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_INVISIBLE;
		}
		
		if(write_flags.get(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_VISIBLE) != null) {//写入并展示
			return EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_VISIBLE;
		}
		return EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_VISIBLE;
	}
}
