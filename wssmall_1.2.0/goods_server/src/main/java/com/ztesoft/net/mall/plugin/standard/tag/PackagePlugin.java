package com.ztesoft.net.mall.plugin.standard.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Relations;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.IGoodsManager;

/**
 * 货品包插件
 * @author Administrator
 * @date 2014-08-08
 */
public class PackagePlugin extends AbstractGoodsPlugin {

	private IGoodsManager goodsManager;
	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
		
	}

	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
//		logger.info("***********货品包***********");
//		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) 
//				&& Consts.ECS_QUERY_TYPE_GOOD.equals(Const.getStrValue(goods, "type"))){
//			String goods_type_id = Const.getStrValue(goods, "type_id");
//			String a_goods_id = (String) goods.get("goods_id");
//			String[] z_goods_ids = request.getParameterValues("goods_ids");
//			Relations relation = getRelation(goods_type_id,z_goods_ids);
//			if(relation!=null){
//				String relation_id = relation.getRelation_id();
//				goodsManager.addPackageMember(relation_id, a_goods_id);
//			}
//		}
	}

	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
//		logger.info("***********货品包***********");
//		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) 
//				&& Consts.ECS_QUERY_TYPE_GOOD.equals(Const.getStrValue(goods, "type"))){
//			String goods_type_id = Const.getStrValue(goods, "type_id");
//			String a_goods_id = (String) goods.get("goods_id");
//			String[] z_goods_ids = request.getParameterValues("goods_ids");
//			
//			goodsManager.deletePackageMember(a_goods_id);
//			
//			Relations relation = getRelation(goods_type_id,z_goods_ids);
//			if(relation!=null){
//				String relation_id = relation.getRelation_id();
//				goodsManager.addPackageMember(relation_id, a_goods_id);
//			}
//		}
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {

	}
	
	public Relations getRelation(String goods_type_id,String[] z_goods_ids){
		
		Map<String,Map<String,String>> typeMap = goodsManager.getGoodsTypeByGoodsId(z_goods_ids);
		//params获取货品包的参数
		Map params = new HashMap();
		params.put("type_id", goods_type_id);
		if(Consts.GOODS_TYPE_NUM_CARD.equals(goods_type_id)){//号卡
			//号卡分为合约计划+套餐号卡，单套餐号卡
			//如果是合约计划+套餐号卡，根据商品类型和合约计划获取货品包
			//如果是单套餐号卡，根据商品类型获取contract_goods_id,model_code为空的货品包
			if(z_goods_ids.length>1){//合约计划+套餐号卡
				Map contract = typeMap.get(Consts.GOODS_TYPE_CONTRACT);
				params.put("contract_goods_id", Const.getStrValue(contract, "goods_id"));
			}
		}
		else if(Consts.GOODS_TYPE_WIFI_CARD.equals(goods_type_id)){//上网卡
			//如果商品是上网卡，根据商品类型获取货品包
		}
		else if(Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type_id)){//合约机
			Map contract = typeMap.get(Consts.GOODS_TYPE_CONTRACT);
			Map terminal = typeMap.get(Consts.GOODS_TYPE_TERMINAL);
			params.put("contract_goods_id", Const.getStrValue(contract, "goods_id"));
			params.put("model_code", Const.getStrValue(terminal, "model_code"));
		}
		else if(Consts.GOODS_TYPE_PHONE.equals(goods_type_id)){//裸机
			Map terminal = typeMap.get(Consts.GOODS_TYPE_TERMINAL);
			params.put("model_code", Const.getStrValue(terminal, "model_code"));
		}
		Relations relation = goodsManager.getPackage(params);
		return relation;
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "package";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "货品包";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTabs() {
		// TODO Auto-generated method stub

	}

}
