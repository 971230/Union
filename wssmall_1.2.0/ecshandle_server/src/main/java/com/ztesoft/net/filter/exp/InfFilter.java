package com.ztesoft.net.filter.exp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import com.ztesoft.net.filter.IFilter;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.ExpFilterResponse;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;

/**
 * 统一过滤不用写入异常的接口
 * @author shen.qiyu
 */
public class InfFilter implements IFilter {
	private static Logger logger = Logger.getLogger(InfFilter.class);
	private final static String WRITE_FLAG = "1";//
	
	@Override
	public FilterResponse doFilter(FilterRequest request) {
		ExpFilterResponse response = new ExpFilterResponse();
		response.setStatus(ConstsCore.ERROR_SUCC);//默认不过滤
		
		ExpFilterRequest efReq = (ExpFilterRequest)request;
		String flag = efReq.getWrite_flag();
		if(StringUtils.equals(WRITE_FLAG,flag)) {
			response.setStatus(ConstsCore.ERROR_FAIL);
			response.setMsg("该接口不用生成异常实例 ");
			response.setWrite_flag(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE);
			logger.info("订单编码："+efReq.getRel_obj_id()+
					";接口编码（"+efReq.getSearch_code()+"）"+"：该接口不用生成异常实例；需要过滤");
			return response;
		}
		List dcList = CommonDataFactory.getInstance().listDcPublicData("JKZF_MARK");
		for(int i=0;i<dcList.size();i++){
			Map map = (Map) dcList.get(i);
			if(StringUtils.equals((String)map.get("pname"),efReq.getSearch_code())
					&&StringUtils.isEmpty(efReq.getRel_obj_id())){
				response.setStatus(ConstsCore.ERROR_FAIL);
				response.setMsg("该接口不用生成异常实例 ");
				response.setWrite_flag(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE);
				logger.info("订单编码："+efReq.getRel_obj_id()+
						";转发商城的接口-"+"接口编码（"+efReq.getSearch_code()+"）"+"：此时订单系统并没有订单生成；需要过滤");
				return response;
			}
		}
		return response;
	}

}
