package com.ztesoft.net.mall.plugin.standard.price;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;

import params.member.req.MemberPriceListAddReq;
import params.member.resp.MemberPriceListAddResp;
import services.MemberPriceInf;
import zte.params.member.req.GoodsMemberPriceListReq;
import zte.params.member.resp.GoodsMemberPriceListResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/***
 * 标准的商品价格插件
 * @author zou.qh
 *
 */
public class StandardGoodsPricePluginN extends AbstractGoodsPluginN {
	
	private MemberPriceInf memberPriceServ;
	private IDcPublicInfoManager dcPublicInfoManager;
	@Override
	public void addTabs() {
	}
	
	@Override
	public String onFillGoodsAddInput() {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		GoodsMemberPriceListReq req = new GoodsMemberPriceListReq();
		ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		GoodsMemberPriceListResp resp = client.execute(req, GoodsMemberPriceListResp.class);
		freeMarkerPaser.putData("priceList",resp.getPriceList());
		freeMarkerPaser.putData("discountList",resp.getDiscountList());
		freeMarkerPaser.setPageName("goods_price_inputn");
		return freeMarkerPaser.proessPageContent();
	 
	}

	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		String goods_id = Const.getStrValue(goods, "goods_id");
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		GoodsMemberPriceListReq req = new GoodsMemberPriceListReq();
		req.setGoods_id(goods_id);
		ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		GoodsMemberPriceListResp resp = client.execute(req, GoodsMemberPriceListResp.class);
		freeMarkerPaser.putData("priceList",resp.getPriceList());
		freeMarkerPaser.putData("discountList",resp.getDiscountList());
		freeMarkerPaser.setPageName("goods_price_inputn");
		return freeMarkerPaser.proessPageContent();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
		//页面js控制取值 mod by easonwu 2013-08-20
		/**
		HttpServletRequest  httpRequest = ThreadContextHolder.getHttpRequest();
		goods.put("cost", httpRequest.getParameter("cost") );
		goods.put("weight", httpRequest.getParameter("weight") );
		goods.put("store", httpRequest.getParameter("store"));
		goods.put("price", httpRequest.getParameter("price"));
		
		if(StringUtil.isEmpty((String)goods.get("cost")) ){ goods.put("cost", 0);}
		if(StringUtil.isEmpty((String)goods.get("price"))){ goods.put("price", 0);}
		if(StringUtil.isEmpty((String)goods.get("weight"))){ goods.put("weight", 0);}
		if(StringUtil.isEmpty((String)goods.get("store"))){ goods.put("store", 0);}		
		*/

	}
	
	private  void processprice(String goodsid,GoodsExtData goodsExtData){
		//生成会员价list
		List<GoodsLvPrice> priceList  =  createGoodsPrices(goodsid,goodsExtData);
		if(priceList != null && !priceList.isEmpty()){
			MemberPriceListAddResp resp = new MemberPriceListAddResp();
			MemberPriceListAddReq req = new MemberPriceListAddReq();
			req.setPriceList(priceList);
			resp = memberPriceServ.addPriceByList(req);
		}
		
	}
	
	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)
			throws RuntimeException {
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		//add by wui 屏蔽掉，GoodsSpecPlugin规则统一处理
		this.processprice(goods.get("goods_id").toString(),goodsExtData);
	}

	
	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		//add by wui 屏蔽掉，GoodsSpecPlugin规则统一处理
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		this.processprice(goods.get("goods_id").toString(),goodsExtData);	

	}
	
	 
	/**
	 * 根据会员级别id和会员价信息生成会员价list<br>
	 * 会员价格如果无空则不插入数据，即不生成会员价，而是按此会员级别的默认折扣计算会员价格,以减少冗余数据。
	 * 
	 * @param lvPriceStr 会员价数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param lvidStr 会员级别id数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param goodsid 当前商品id，没有填充productid,此插件只支持商品统一的会员价格
	 */
	/**
	 * 根据会员级别id和会员价信息生成会员价list<br>
	 * 会员价格如果无空则不插入数据，即不生成会员价，而是按此会员级别的默认折扣计算会员价格,以减少冗余数据。
	 * 
	 * @param lvPriceStr 会员价数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param lvidStr 会员级别id数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param goodsid 当前商品id，没有填充productid,此插件只支持商品的统一会员价格
	 * @return 生成的List<GoodsLvPrice>
	 */
	private List<GoodsLvPrice> createGoodsPrices(String goodsid, GoodsExtData goodsExtData){
		
		if(StringUtils.isEmpty(goodsid)) 
			throw new RuntimeException("商品id不能为空");
		String[] lvid = goodsExtData.getLvid();
		String[] lvPrice = goodsExtData.getLvPrice();
		String[] lvDiscount = goodsExtData.getLv_discount();		
		List<GoodsLvPrice> list = new ArrayList<GoodsLvPrice>();
		if(lvid!=null && lvid.length > 0 ){ 
			for(int i=0;i<lvid.length;i++){
				if(lvPrice != null){
					if(com.ztesoft.net.framework.util.StringUtil.isEmpty(lvPrice[i])){
						lvPrice[i] = "0";
					}
					if(-999 == Double.parseDouble(lvPrice[i]))
						continue ;
					GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
					goodsLvPrice.setGoodsid(goodsid);
					goodsLvPrice.setPrice(Double.parseDouble(lvPrice[i]));
					goodsLvPrice.setLvid(lvid[i]);
					goodsLvPrice.setLv_discount(Float.valueOf(lvDiscount[i]));
					list.add(goodsLvPrice);
				}
			}
		}
		return list;
	}
	
	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		/**
		HttpServletRequest  httpRequest = ThreadContextHolder.getHttpRequest();
		goods.put("cost", httpRequest.getParameter("cost") );
		//goods.put("price", httpRequest.getParameter("price") );
		goods.put("weight", httpRequest.getParameter("weight") );
		goods.put("store", httpRequest.getParameter("store") );
		
		if(  StringUtil.isEmpty((String)goods.get("cost")) ){ goods.put("cost", 0);}
		//if(StringUtil.isEmpty((String)goods.get("price"))){ goods.put("price", 0);}
		if(StringUtil.isEmpty((String)goods.get("weight"))){ goods.put("weight", 0);}
		if(StringUtil.isEmpty((String)goods.get("store"))){ goods.put("store", 0);}		
		*/
	}
	
	@Override
	public String getAuthor() {
		return "zou.qh";
	}
	
	@Override
	public String getId() {
		return "goods_priceN";
	}
	
	@Override
	public String getName() {
		
		return "标准商品价格插件";
	}
	
	@Override
	public String getType() {
		
		return "goods";
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {

	}

	public MemberPriceInf getMemberPriceServ() {
		return memberPriceServ;
	}

	public void setMemberPriceServ(MemberPriceInf memberPriceServ) {
		this.memberPriceServ = memberPriceServ;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

}
