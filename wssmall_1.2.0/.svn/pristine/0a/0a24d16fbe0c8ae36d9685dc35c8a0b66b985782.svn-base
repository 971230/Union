package com.ztesoft.remote.params.activity.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 *
 */
public class PromotionMapByIdResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="活动基本信息及规则信息", type="Map", isNecessary="N", 
			desc="活动基本信息及规则信息。其中，pmt_code：活动编码； pmt_name：活动名称；"
			+ " begin_time：活动开始时间； end_time：活动结束时间；region:活动地市；"
			+ " relief_no_class：靓号减免类型，多个用半角逗号隔开如:1,2,3；modify_eff_time：活动修改后的生效时间；"
			+ " act_condition：活动条件（如果是满赠、满减则存满多少，如果是团购、秒杀，则存团购人数下限或秒杀个数）；  "
			+ " pmt_type： 活动类型标识；pmt_type_name：活动类型名称；"
			+ " pmt_describe：活动描述； pmt_solution：活动方案[如果活动类型是打折，则为折扣值，如打8折就是0.8；"
			+ " 如果活动类型是满减，则为要减去的值，如满500减300就是300；如果活动类型是直降，则为直降的数值，如直降200就是200；"
			+ " 如果活动类型是预售，则这里存预售的价格；如果活动类型是团购或秒杀，则是团购或秒杀的价格]")
	private Map pmt_map;
	
	@ZteSoftCommentAnnotationParam(name="适用该活动的商品列表", type="List", isNecessary="N", 
			desc="适用该活动的商品列表，如果活动类型是满减则没有该商品列表，如果活动类型是满赠，则该商品列表是要赠送的。其中，"
			+ " goods_id：商品标识； sku：商品编码；goods_name：商品名称", hasChild=true)
	private List<Map> goodsLst;
	
	@ZteSoftCommentAnnotationParam(name="赠送的商品列表", type="List", isNecessary="N", 
			desc="赠送的商品列表。其中goods_id：商品标识； sku：商品编码；goods_name：商品名称；goods_type_id：商品类型标识；"
			+ " goods_type_name：商品类型名称", hasChild=true)
	private List<Map> giftsLst;
	
	public Map getPmt_map() {
		return pmt_map;
	}

	public void setPmt_map(Map pmt_map) {
		this.pmt_map = pmt_map;
	}

	public List<Map> getGoodsLst() {
		return goodsLst;
	}

	public void setGoodsLst(List<Map> goodsLst) {
		this.goodsLst = goodsLst;
	}

	public List<Map> getGiftsLst() {
		return giftsLst;
	}

	public void setGiftsLst(List<Map> giftsLst) {
		this.giftsLst = giftsLst;
	}
	
}
