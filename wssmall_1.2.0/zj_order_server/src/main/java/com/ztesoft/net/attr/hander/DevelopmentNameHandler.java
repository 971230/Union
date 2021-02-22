package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.model.AttrDef;


/**
 * 发展人编码名称
 * @author wu.i
 * 
 *
 */
public class DevelopmentNameHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		if(StringUtils.isEmpty(value)){
			String cityId = (String) orderAttrValues.get(AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.city=? and a.order_gonghao in (select pname from es_dc_public_ext  where stype='operator_code' and pkey=?)";
			List<Map<String, String>> list = support.queryForList(sql,new String[]{otherCityCode,order_from});
			if(list.size()>0){
				value = list.get(0).get("dep_name");
			}
		}
		/*
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		String good_id = (String) orderAttrValues.get(AttrConsts.GOODS_ID);
		//商品分类是省内号卡
		String type_id = CommonDataFactory.getInstance().getGoodSpec(null, good_id, "type_id");
		
		if(!EcsOrderConsts.GOODS_TYPE_ID_HKSN.equals(type_id) && !EcsOrderConsts.GOODS_TYPE_ID_JKHK.equals(type_id) && !EcsOrderConsts.GOODS_TYPE_FEI_HK.equals(type_id)) {

			if(CommonDataFactory.getInstance().isZbOrder(plat_type))
				order_from = EcsOrderConsts.ORDER_FROM_10003;
			String development_code = (String) orderAttrValues.get(AttrConsts.DEVELOPMENT_CODE);
			if((StringUtils.isEmpty(value) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(value)) && StringUtils.isNotEmpty(development_code)){
				IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
				DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
				value=dcPublicCache.getDevelopmentName(development_code, null);
				if (StringUtils.isEmpty(value)) {
					value = development_code;
	    		}
			}
		
		}	
		*/			
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
//		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GOODS_TYPE);
		//号卡、号卡合约、合约机、上网卡必填
		AttrInstLoadResp resp = null;
		/*String development_name  = req.getNew_value();
		if(StringUtils.isEmpty(development_name)&&(SpecConsts.TYPE_ID_20000.equals(goods_type)||SpecConsts.TYPE_ID_20002.equals(goods_type)||SpecConsts.TYPE_ID_20001.equals(goods_type)))
		{
			resp = new AttrInstLoadResp();
			resp.setField_desc("发展人名称");
			resp.setField_value(development_name);
			resp.setCheck_message("发展人名称不能为空。");
		}*/
			return resp;
	}
	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
//		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GOODS_TYPE);
		//号卡、号卡合约、合约机、上网卡必填
		AttrInstLoadResp resp = null;
		/*String development_name  = req.getNew_value();
		if(StringUtils.isEmpty(AttrConsts.DEVELOPMENT_NAME)&&(SpecConsts.TYPE_ID_20000.equals(goods_type)||SpecConsts.TYPE_ID_20002.equals(goods_type)||SpecConsts.TYPE_ID_20001.equals(goods_type)))
		{
			resp = new AttrInstLoadResp();
			resp.setField_desc("发展人名称");
			resp.setField_value(development_name);
			resp.setCheck_message("发展人名称不能为空。");
		}*/
			return resp;
	}

}
