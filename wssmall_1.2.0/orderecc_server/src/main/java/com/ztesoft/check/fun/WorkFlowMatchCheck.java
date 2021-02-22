package com.ztesoft.check.fun;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.common.factory.BizDataFatory;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.inf.Check;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFator;
import com.ztesoft.net.consts.EccConsts;

/**
 * 
 * 工作流匹配校验
 *
 */
public class WorkFlowMatchCheck extends AbstractCheckHander implements Check{

	@Override
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException {
		IdentifyResp resp = new IdentifyResp();
		String order_id = identification.getOrder_id();
		String need_open_act = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_ACCOUNT);
		String user_flag = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10002, "", SpecConsts.NET_TYPE);
		String wm_isreservation_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WM_ISRESERVATION_FROM);
		BizFator bizFator = new BizFator();
		bizFator.setOrder_id(order_id);
		bizFator.setGoods_type(goods_type);
		bizFator.setNeed_open_act(need_open_act);
		bizFator.setNet_type(net_type);
		bizFator.setUser_flag(user_flag);
		bizFator.setWm_isreservation_from(wm_isreservation_from);
		
		Biz biz = BizDataFatory.getInstance().getflowBizByFator(bizFator);
		if(biz!=null){
			resp.setCode(EccConsts.IDENTI_SUCCESS);
			resp.setMsg("工作流匹配校验成功.");
		}else{
			throw new ApiBusiException("order_id["+order_id+"]工作流匹配不成功成功.");
/*			resp.setCode(EccConsts.IDENTI_0006);
			resp.setMsg("工作流匹配不成功成功.");*/
		}		
		return resp;
	}
	
	private void flowMatch(){

	}
	


}