package com.ztesoft.net.mall.plugin.standard.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.util.StringUtils;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GoodsProductPlugin extends AbstractGoodsPlugin {

	private IGoodsManager goodsManager;

	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
			freeMarkerPaser.setPageName("products_ecs");
			return freeMarkerPaser.proessPageContent();
		}
		else{
			return null;
		}
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
		logger.info("货品插件桩。。。。。。。");
//		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())
//				&& Consts.ECS_QUERY_TYPE_GOOD.equals(goods.get("type"))
//				&& (Consts.GOODS_TYPE_PHONE.equals(goods.get("type_id")) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods.get("type_id")))){
//			String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
//			String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids);
//			goods.put("model_code", model_code);
//		}
		//兼容套餐类型的商品----zengxianlian
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())
				&& Consts.ECS_QUERY_TYPE_GOOD.equals(goods.get("type"))){
			String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
			if(Consts.GOODS_TYPE_PHONE.equals(goods.get("type_id")) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods.get("type_id"))){
				String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids,Consts.GOODS_TYPE_TERMINAL);
				goods.put("model_code", model_code);
			}else if(Consts.GOODS_TYPE_NUM_CARD.equals(goods.get("type_id"))){
				String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids,Consts.GOODS_TYPE_OFFER);
				goods.put("model_code", model_code);
			}
			
		}
	}

	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
			String goods_id = (String) goods.get("goods_id");
			
			List<Map> products = goodsManager.listGoodRelProducts(goods_id);
			freeMarkerPaser.putData("productList", products);
			freeMarkerPaser.setPageName("products_ecs");
			return freeMarkerPaser.proessPageContent();
		}
		else{
			return null;
		}
	}

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			String a_goods_id = (String) goods.get("goods_id");
			String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
			String[] productids = request.getParameterValues("product_ids")==null?(request.getAttribute("product_ids")==null?null:(String[])request.getAttribute("product_ids")):request.getParameterValues("product_ids");
			String[] rel_codes = request.getParameterValues("rel_codes")==null?(request.getAttribute("rel_codes")==null?null:(String[])request.getAttribute("rel_codes")):request.getParameterValues("rel_codes");
			String[] rel_types = request.getParameterValues("rel_types")==null?(request.getAttribute("rel_types")==null?null:(String[])request.getAttribute("rel_types")):request.getParameterValues("rel_types");
			if(productids==null || productids.length==0)
				return ;
			
			String rel_code = "-1";
			for(int i=0;i<productids.length;i++){
				String product_id = productids[i];
				String z_goods_id = z_goods_ids[i];
				rel_code = goodsManager.createGoodsRelCode();
				String rel_type = rel_types[i];
				
				Map data = new HashMap();
				data.put("a_goods_id", a_goods_id);
				data.put("z_goods_id", z_goods_id);
				data.put("rel_type", rel_type);
				data.put("rel_code", rel_code);
				data.put("product_id", product_id);
				data.put("source_from", ManagerUtils.getSourceFrom());
				this.goodsManager.addGoodsRelProduct(data);
			}
		}
	}

	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			String a_goods_id = (String) goods.get("goods_id");
			String[] z_goods_ids = request.getParameterValues("goods_ids");
			String[] productids = request.getParameterValues("product_ids");
			String[] rel_codes = request.getParameterValues("rel_codes");
			String[] rel_types = request.getParameterValues("rel_types");
			
			Map param=new HashMap();
			param.put("goods_id", a_goods_id);
			//删除日志
			this.goodsManager.goodsRelLog(true,param);
			
			this.goodsManager.deleteGoodsRelProduct(a_goods_id);
			
			
			if(productids==null || productids.length==0)
				return ;
			
			String rel_code = "";
			for(int i=0;i<productids.length;i++){
				String product_id = productids[i];
				String z_goods_id = z_goods_ids[i];
				rel_code = rel_codes[i];
				if (StringUtils.isEmpty(rel_code)) {
					rel_code = goodsManager.createGoodsRelCode();
				}						
				String rel_type = rel_types[i];
				
				Map data = new HashMap();
				data.put("a_goods_id", a_goods_id);
				data.put("z_goods_id", z_goods_id);
				data.put("rel_type", rel_type);
				data.put("rel_code", rel_code);
				data.put("product_id", product_id);
				data.put("source_from", ManagerUtils.getSourceFrom());
				this.goodsManager.addGoodsRelProduct(data);
				
				//添加日志
				Map logParam=new HashMap();
				logParam.put("oper_name", ManagerUtils.getAdminUser().getRealname());
				logParam.put("oper_no", ManagerUtils.getAdminUser().getUserid());
				logParam.put("goods_id", a_goods_id);
				this.goodsManager.goodsRelLog(false,logParam);
			}
		}
		
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
		// TODO Auto-generated method stub
//		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())
//				&& Consts.ECS_QUERY_TYPE_GOOD.equals(goods.get("type"))
//				&& (Consts.GOODS_TYPE_PHONE.equals(goods.get("type_id")) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods.get("type_id")))){
//			String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
//			String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids);
//			goods.put("model_code", model_code);
//		}
		//兼容套餐类型的商品----zengxianlian
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())
				&& Consts.ECS_QUERY_TYPE_GOOD.equals(goods.get("type"))){
			String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
			if(Consts.GOODS_TYPE_PHONE.equals(goods.get("type_id")) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods.get("type_id"))){
				String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids,Consts.GOODS_TYPE_TERMINAL);
				goods.put("model_code", model_code);
			}else if(Consts.GOODS_TYPE_NUM_CARD.equals(goods.get("type_id"))){
				String model_code = this.goodsManager.getTerminalModelCode(z_goods_ids,Consts.GOODS_TYPE_OFFER);
				goods.put("model_code", model_code);
			}
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return "goodsProduct";
	}

	@Override
	public String getName() {
		return "货品插件";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "zqh";
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTabs() {
		// TODO Auto-generated method stub
		
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}
	

}