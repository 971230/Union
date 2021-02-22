package com.ztesoft.net.filter.exp;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.net.filter.IFilter;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.ExpFilterResponse;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;

/**
 * 报文为空：过滤
 * @author shen.qiyu
 */
public class ParamFilter implements IFilter {
	private static Logger logger = Logger.getLogger(ParamFilter.class);
	private final static String WRITE_FLAG = "1";//
	
	@Override
	public FilterResponse doFilter(FilterRequest request) {
		ExpFilterResponse response = new ExpFilterResponse();
		response.setStatus(ConstsCore.ERROR_SUCC);//默认不过滤
		ExpFilterRequest efReq = (ExpFilterRequest)request;
		String param = efReq.getParam();
		//报文为空：过滤
		if(StringUtils.isEmpty(param)){
			response.setStatus(ConstsCore.ERROR_FAIL);
			response.setMsg("报文为空：过滤");
			response.setWrite_flag(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE);
			logger.info("订单编码："+efReq.getRel_obj_id()+";报文为空:需要过滤;");
			return response;
		}
		return response;
	}

}
