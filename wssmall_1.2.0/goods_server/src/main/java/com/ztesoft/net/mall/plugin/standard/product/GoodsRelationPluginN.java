package com.ztesoft.net.mall.plugin.standard.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterAddEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterEditEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsBeforeAddEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsBeforeEditEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsDeleteEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsFillAddInputDataEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsFillEditInputDataEventN;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class GoodsRelationPluginN extends AutoRegisterPlugin implements IGoodsFillAddInputDataEventN, IGoodsFillEditInputDataEventN, IGoodsBeforeAddEventN, IGoodsAfterAddEventN,IGoodsBeforeEditEventN,IGoodsAfterEditEventN,IGoodsDeleteEventN {

	private IGoodsManagerN goodsManagerN;
	
	public IGoodsManagerN getGoodsManagerN() {
		return goodsManagerN;
	}

	public void setGoodsManagerN(IGoodsManagerN goodsManagerN) {
		this.goodsManagerN = goodsManagerN;
	}

	@Override
	public void onGoodsDelete(String[] goodsid) {
		
	}

	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		String goods_id = Const.getStrValue(goods, "goods_id");
		if(StringUtils.isEmpty(goods_id)) throw new RuntimeException("商品id不能为空");
		this.baseDaoSupport.execute(SF.goodsSql("GOODS_REL_PRODUCT_DELETE"), goods_id);
		
		String[] rel_codes = goodsExtData.getRel_codes();
		String[] rel_types = goodsExtData.getRel_types();
		String[] rel_goods_ids = goodsExtData.getRel_goods_ids();
		String[] rel_product_ids = goodsExtData.getRel_product_ids();
		Map<String, String> map = null;
		if(rel_goods_ids!=null && rel_goods_ids.length>0){
			for(int i=0;i<rel_goods_ids.length;i++){
				String rel_goods_id = rel_goods_ids[i];
				String rel_code = rel_codes[i];
				String rel_type = rel_types[i];
				String rel_prod_id = rel_product_ids[i];
				
				map = new HashMap<String, String>();
				map.put("a_goods_id", goods_id);
				map.put("z_goods_id", rel_goods_id);
				map.put("product_id", rel_prod_id);
				map.put("rel_type", rel_type);
				map.put("rel_code", rel_code);
				map.put("source_from", ManagerUtils.getSourceFrom());
				this.baseDaoSupport.insert("es_goods_rel", map);
			}
		}
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		
	}

	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)
			throws RuntimeException {
		String goods_id = Const.getStrValue(goods, "goods_id");
		String[] rel_codes = goodsExtData.getRel_codes();
		String[] rel_types = goodsExtData.getRel_types();
		String[] rel_goods_ids = goodsExtData.getRel_goods_ids();
		String[] rel_product_ids = goodsExtData.getRel_product_ids();
		Map<String, String> map = null;
		if(rel_goods_ids!=null && rel_goods_ids.length>0){
			for(int i=0;i<rel_goods_ids.length;i++){
				String rel_goods_id = rel_goods_ids[i];
				String rel_code = rel_codes[i];
				String rel_type = rel_types[i];
				String rel_prod_id = rel_product_ids[i];
				
				map = new HashMap<String, String>();
				map.put("a_goods_id", goods_id);
				map.put("z_goods_id", rel_goods_id);
				map.put("product_id", rel_prod_id);
				map.put("rel_type", rel_type);
				map.put("rel_code", rel_code);
				map.put("source_from", ManagerUtils.getSourceFrom());
				this.baseDaoSupport.insert("es_goods_rel", map);
			}
		}
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
		
	}
	
	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		String goods_id = Const.getStrValue(goods, "goods_id");
		if(StringUtils.isEmpty(goods_id)) throw new RuntimeException("商品id不能为空");
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setPageName("goods_relation");
		List<Goods> goodsList = this.goodsManagerN.listRelGoods(goods_id);
		freeMarkerPaser.putData("goodsList", goodsList);
		freeMarkerPaser.putData("haveRelGoods", goodsList.size());
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public String onFillGoodsAddInput() {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.setPageName("goods_relation");
		freeMarkerPaser.putData("haveRelGoods", 0);
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public void register() {
		
	}
	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getId() {
		return "goodsRelN";
	}

	@Override
	public String getName() {
		return "商品关联关系";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "zou.qh";
	}

	@Override
	public void perform(Object... params) {
		
	}

}
