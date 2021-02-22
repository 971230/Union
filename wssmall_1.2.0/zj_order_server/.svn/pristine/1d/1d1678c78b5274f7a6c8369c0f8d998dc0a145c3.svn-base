package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
/**
 * 商品实收价格
 * @author Administrator
 *
 */
public class ProRealFeeHander implements IAttrHandler{

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
        //实收价格如果为空  默认用应收价格减掉优惠价格
		if(StringUtils.isEmpty(req.getNew_value())){
			String discountrange = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.DISCOUNTRANGE); //优惠价格
			String pro_origfee = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.PRO_ORIG_FEE);//商品应收价格
		    if(StringUtils.isEmpty(discountrange)){
		    	discountrange="0";
		    }
			if(!StringUtils.isEmpty(discountrange)&&!StringUtils.isEmpty(pro_origfee)){
				double pro_realFee = Double.parseDouble(pro_origfee) -  Double.parseDouble(discountrange);
				if(pro_realFee>=0){
					AttrInstLoadResp resp = new AttrInstLoadResp();
					resp.setField_value(pro_realFee+"");
					return resp;
				}
			}
		}
		
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		//实收价格如果为空  默认用应收价格减掉优惠价格
				if(StringUtils.isEmpty(req.getNew_value())){
					String discountrange = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.DISCOUNTRANGE); //优惠价格
					String pro_origfee = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.PRO_ORIG_FEE);//商品应收价格
				    if(StringUtils.isEmpty(discountrange)){
				    	discountrange="0";
				    }
					if(!StringUtils.isEmpty(discountrange)&&!StringUtils.isEmpty(pro_origfee)){
						double pro_realFee = Double.parseDouble(pro_origfee) -  Double.parseDouble(discountrange);
						if(pro_realFee>=0){
							AttrInstLoadResp resp = new AttrInstLoadResp();
							resp.setField_value(pro_realFee+"");
							return resp;
						}
					}
				}
				
				return null;
	}

}
