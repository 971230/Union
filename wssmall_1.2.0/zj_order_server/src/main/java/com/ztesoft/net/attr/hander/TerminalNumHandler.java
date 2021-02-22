package com.ztesoft.net.attr.hander;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class TerminalNumHandler implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String value = params.getValue();
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		String has = CommonDataFactory.getInstance().hasTerminal(req.getOrder_id());
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		AttrInstLoadResp resp = null;
		
		String terminal_num =  req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.TERMINAL_NUM);
		if(StringUtil.isEmpty(terminal_num)){
			List<OrderItemBusiRequest> itemBusiList = orderTree.getOrderItemBusiRequests();
			OrderItemBusiRequest itemBusi = itemBusiList.get(0);
			List<OrderItemProdBusiRequest> itemProdBusiList = itemBusi.getOrderItemProdBusiRequests();
			for(int i=0;i<itemProdBusiList.size();i++){
				OrderItemProdBusiRequest itemProdBusi = itemProdBusiList.get(i);
				if(EcsOrderConsts.PRODUCT_TYPE_CODE_TERMINAL.equals(itemProdBusi.getProd_spec_type_code())){
					terminal_num = itemProdBusi.getOrderItemProdExtBusiRequest().getTerminal_num();
					resp = new AttrInstLoadResp();
					resp.setField_value(terminal_num);
				}
			}
		}
		String order_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_TYPE);
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		if("1".equals(has)&&!EcsOrderConsts.ORDER_TYPE_09.equals(order_type)&&!EcsOrderConsts.ORDER_FROM_10061.equals(order_from)&&!EcsOrderConsts.ORDER_FROM_10062.equals(order_from)&&StringUtils.isEmpty(terminal_num)&&(EcsOrderConsts.DIC_ORDER_NODE_C.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id))){
			resp = new AttrInstLoadResp();
			resp.setField_desc("串号");
			resp.setField_value(terminal_num);
			resp.setCheck_message("串号不能为空。");
		}
		//开户、业务办理环节，裸机、老用户合约机，串号（可修改、并必填）;质检环节，裸机，串号（可修改、并必填）
		String order_id = req.getOrder_id();
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		if((StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_D)||StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_Y))&&//开户、业务办理环节
				(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_PHONE, goods_type)||//裸机
						(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE, goods_type)&&StringUtils.equals(EcsOrderConsts.IS_OLD_1, is_old)))||//老用户合约机
						(StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_F))&&(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_PHONE, goods_type))){//质检环节 裸机
			if(!EcsOrderConsts.ORDER_TYPE_09.equals(order_type)&&!EcsOrderConsts.ORDER_FROM_10061.equals(order_from)&&!EcsOrderConsts.ORDER_FROM_10062.equals(order_from)){
				resp = new AttrInstLoadResp();
				resp.setDisabled("enabled");//可修改
				if(StringUtils.isEmpty(terminal_num)){//必填
					resp.setField_desc("串号");
					resp.setField_value(terminal_num);
					resp.setCheck_message(resp.getField_desc()+"不能为空。");
				}
			}
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		String has = CommonDataFactory.getInstance().hasTerminal(req.getOrder_id());
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String terminal_num = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.TERMINAL_NUM);
		AttrInstLoadResp resp = null;
		String order_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_TYPE);
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		if("1".equals(has)&&!EcsOrderConsts.ORDER_TYPE_09.equals(order_type)&&!EcsOrderConsts.ORDER_FROM_10061.equals(order_from)&&!EcsOrderConsts.ORDER_FROM_10062.equals(order_from)&&StringUtils.isEmpty(terminal_num)&&(EcsOrderConsts.DIC_ORDER_NODE_C.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id))){
			resp = new AttrInstLoadResp();
			resp.setField_desc("串号");
			resp.setField_value(terminal_num);
			resp.setCheck_message("串号不能为空。");
		}
		//开户、业务办理环节，裸机、老用户合约机，串号（可修改、并必填）;质检环节，裸机，串号（可修改、并必填）
		String order_id = req.getOrder_id();
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		if((StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_D)||StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_Y))&&//开户、业务办理环节
				(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_PHONE, goods_type)||//裸机
						(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE, goods_type)&&StringUtils.equals(EcsOrderConsts.IS_OLD_1, is_old)))||//老用户合约机
						(StringUtils.equals(trace_id, EcsOrderConsts.DIC_ORDER_NODE_F))&&(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_PHONE, goods_type))){//质检环节 裸机
			if(!EcsOrderConsts.ORDER_TYPE_09.equals(order_type)&&!EcsOrderConsts.ORDER_FROM_10061.equals(order_from)&&!EcsOrderConsts.ORDER_FROM_10062.equals(order_from)){
				resp = new AttrInstLoadResp();
				resp.setDisabled("enabled");//可修改
				if(StringUtils.isEmpty(terminal_num)){//必填
					resp.setField_desc("串号");
					resp.setField_value(terminal_num);
					resp.setCheck_message(resp.getField_desc()+"不能为空。");
				}
			}
		}
		return resp;
	}

}