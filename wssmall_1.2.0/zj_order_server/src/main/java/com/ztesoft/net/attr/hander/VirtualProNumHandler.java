package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Utils;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;
/**
 * 保存多个商品信息
 * @author Rapon
 *
 */
public class VirtualProNumHandler extends BaseSupport implements IAttrHandler {
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

		String order_type = (String) orderAttrValues.get("type");//

		if(EcsOrderConsts.ORDER_FROM_10053.equals(order_from)&&"7".equals(order_type)){//order_from属性处理器在此之后，此时以order_from作为判断条件风险点为:总部、淘宝订单来源未转为内部编码

			JSONArray array = null;
			try{
				array = JSONArray.fromObject(value);
			}catch(Exception e){
				e.printStackTrace();
				logger.info("virtual_pro_num属性解析失败");
			}
			if(null!=array){
				//设置同一sku入库次数,生成序列用
				Map<String,String> mapSku = new HashMap<String,String>();
				for(int i=0;i<array.size();i++){
					try{
						JSONObject object = (JSONObject) array.get(i);
						int num = Integer.parseInt(object.getString("virtualGoodsNum"));//商品数量
						String goodsId = object.getString("virtualGoodsId");//商品id
						Double goodsPrice = Double.parseDouble(object.getString("virtualGoodsPrice"));//商品价格

						Map mobileTerminalSpec = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goodsId, SpecConsts.TYPE_ID_10000);//货品手机信息

						String goods_name = CommonDataFactory.getInstance().getGoodSpec(null, goodsId, SpecConsts.GOODS_NAME);
						String product_id = (String) mobileTerminalSpec.get(SpecConsts.PROD_GOODS_ID);//取es_goods表的goods_id
						String goods_type = SpecConsts.TYPE_ID_10000;
						String machine_type_code = (String) mobileTerminalSpec.get(SpecConsts.GOODS_SN);
						String machine_type_name = (String) mobileTerminalSpec.get(SpecConsts.MODEL_NAME);
						String resources_brand_code = (String) mobileTerminalSpec.get(SpecConsts.BRAND_CODE);
						String resources_brand_name = (String) mobileTerminalSpec.get(SpecConsts.BRAND_NAME);
						String resources_model_code = (String) mobileTerminalSpec.get(SpecConsts.MODEL_CODE);
						String resources_model_name = (String) mobileTerminalSpec.get(SpecConsts.MODEL_NAME);
						String resources_color = (String) mobileTerminalSpec.get(SpecConsts.COLOR_NAME);
						String sku = (String) mobileTerminalSpec.get(SpecConsts.SKU);

						Map mapValue = new HashMap();
						mapValue.put("order_id", order_id);//内部订单号
						mapValue.put("goods_id", goodsId);//商品ID
						mapValue.put("goods_name", goods_name);//商品名称
						mapValue.put("product_id", product_id);//货品ID
						mapValue.put("goods_type", goods_type);//货品类型（例如10000：手机;10006：配件）
						mapValue.put("machine_type_code", machine_type_code);//机型编码
						mapValue.put("machine_type_name", machine_type_name);//机型名称
						mapValue.put("resources_brand_code", resources_brand_code);//品牌编码
						mapValue.put("resources_brand_name", resources_brand_name);//品牌名称
						mapValue.put("resources_model_code", resources_model_code);//型号编码
						mapValue.put("resources_model_name", resources_model_name);//型号名称
						mapValue.put("resources_color", resources_color);//颜色
						//					mapValue.put("resources_code", "");//终端串号
						//					mapValue.put("operation_status", "");//操作状态
						//					mapValue.put("operation_desc", "");//操作结果描述
						mapValue.put("sku", sku);//货品sku
						mapValue.put("source_from", ManagerUtils.getSourceFrom());
						mapValue.put("goods_price", goodsPrice);//商城推送订单时，NewMallServlet已将厘转为元
						
						if(!mapSku.containsKey(sku)){
							mapSku.put(sku, "1");
						}
						for(int j=0;j<num;j++){//保存商品(实际上是货品)信息
							//生成6位序列
							mapValue.put("zline", Utils.lpad(mapSku.get(sku), 6, "0"));
							OrderItemExtvtlBusiRequest request = new OrderItemExtvtlBusiRequest();

							mapValue.put("items_id",this.baseDaoSupport.getSequences("s_es_order_items_extvtl"));//序列号

							BeanUtils.copyProperties(request, mapValue);

							request.setDb_action(ConstsCore.DB_ACTION_INSERT);
							request.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							request.store();
							//序列加1
							int nums = Integer.parseInt(mapSku.get(sku)) + 1;
							mapSku.put(sku, nums + "");
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;//商品数量或商品价格不是数字,无法入库
					}
				}
			}
		}


		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value("1");//存个1表示进过处理器
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}
}
