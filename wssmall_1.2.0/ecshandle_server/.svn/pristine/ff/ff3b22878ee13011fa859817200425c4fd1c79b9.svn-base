package com.ztesoft.net.filter.exp;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;

import com.ztesoft.net.filter.IFilter;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.ExpFilterResponse;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;

/**
 * 对配送方式为现场交付的类型进行过滤
 * @author qin.yingxiong
 */
public class SendingTypeFilter implements IFilter {

	@Override
	public FilterResponse doFilter(FilterRequest request) {
		ExpFilterResponse response = new ExpFilterResponse();
		response.setStatus(ConstsCore.ERROR_SUCC);//默认不过滤
		
		ExpFilterRequest efReq = (ExpFilterRequest)request;
		
		String sending_type = null;
		if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER.equals(efReq.getRel_obj_type())) {
			OrderTreeBusiRequest orderTreeBusiRequest = 
					CommonDataFactory.getInstance().getOrderTree(efReq.getRel_obj_id());
			sending_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTreeBusiRequest,efReq.getRel_obj_id(), AttrConsts.SENDING_TYPE);
		}else if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER_QUEUE.equals(efReq.getRel_obj_type())){
			OrderQueueBusiRequest queue = CommonDataFactory.getInstance().getOrderQueueFor(null, efReq.getRel_obj_id());
			if(queue != null && StringUtils.isNotEmpty(queue.getContents())) {
				int idx = queue.getContents().indexOf("shipping_type");
				if(idx != -1) {
					String start = queue.getContents().substring(idx).replaceAll(" ", "");
					String temp = start.substring(start.indexOf("\"")+3);
					sending_type = temp.substring(0, temp.indexOf("\""));
				}
			}
		}
		
		if(StringUtils.isNotEmpty(sending_type)
				&& EcsOrderConsts.SENDING_TYPE_XJ.equals(sending_type)) {
			response.setStatus(ConstsCore.ERROR_FAIL);
			response.setWrite_flag(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_INVISIBLE);//写入异常但不展示
			response.setMsg("sending_type为XJ  写入异常但不展示");
		}
		return response;
	}

}
