package com.ztesoft.net.filter.exp;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.filter.IFilter;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.ExpFilterResponse;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;

/**
 * periphery.exceptionnotify，该接口，如果订单是非（新用户、白卡），则不要生成异常实例
 * @author qin.yingxiong
 */
public class ExceptionNotifyInfFilter implements IFilter {

	private final static String PERIPHERY_EXCEPTIONNOTIFY = "J111";//periphery.exceptionnotify  订单异常通知
	
	@Override
	public FilterResponse doFilter(FilterRequest request) {
		ExpFilterResponse response = new ExpFilterResponse();
		response.setStatus(ConstsCore.ERROR_SUCC);//默认不过滤
		
		ExpFilterRequest efReq = (ExpFilterRequest)request;
		if(StringUtils.isNotEmpty(efReq.getSearch_id())
				&& efReq.getSearch_id().equals(PERIPHERY_EXCEPTIONNOTIFY)) {
			OrderTreeBusiRequest orderTreeBusiRequest = 
					CommonDataFactory.getInstance().getOrderTree(efReq.getRel_obj_id());
			String product_type = CommonDataFactory.getInstance()
					.getAttrFieldValue(orderTreeBusiRequest,efReq.getRel_obj_id(), AttrConsts.PRODUCT_TYPE);
			String is_old = CommonDataFactory.getInstance()
					.getAttrFieldValue(orderTreeBusiRequest,efReq.getRel_obj_id(), AttrConsts.IS_OLD);
			if(!EcsOrderConsts.PRODUCT_TYPE_BAI_CARD.equals(product_type)
					&& EcsOrderConsts.IS_OLD_1.equals(is_old)) {//如果不是白卡和新用户，则过滤掉
				response.setWrite_flag(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_INVISIBLE);
				response.setStatus(ConstsCore.ERROR_FAIL);
				response.setMsg("periphery.exceptionnotify，该接口，如果订单是非（新用户、白卡），则不要生成异常实例 ");
				return response;
			}
		}
		return response;
	}

}
