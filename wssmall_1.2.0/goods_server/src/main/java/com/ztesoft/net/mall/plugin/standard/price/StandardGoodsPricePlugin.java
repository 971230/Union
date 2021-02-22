package com.ztesoft.net.mall.plugin.standard.price;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import params.member.req.MemberPriceListAddReq;
import params.member.resp.MemberPriceListAddResp;
import services.MemberPriceInf;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;

/***
 * 标准的商品价格插件
 * @author kingapex
 *
 */
public class StandardGoodsPricePlugin extends AbstractGoodsPlugin {

//	private IMemberLvManager memberLvManager;
//	private IGoodsPriceManager goodsPriceManager;
	
	private MemberPriceInf memberPriceServ;
	@Override
	public void addTabs() {
		this.addTags(1, "价格");
	}

	
	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
 
		freeMarkerPaser.setPageName("goods_price_input");
		return freeMarkerPaser.proessPageContent();
	 
	}

	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
	 
//		List lvList  = this.memberLvManager.list();
//		freeMarkerPaser.putData("lvList", lvList);
 
		freeMarkerPaser.setPageName("goods_price_input");
		return freeMarkerPaser.proessPageContent();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
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
	
	private  void processprice(String goodsid){
//	
//		HttpServletRequest  httpRequest = ThreadContextHolder.getHttpRequest();
//		String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
//		String[] lvidStr = httpRequest.getParameterValues("lvid");
//		
//		//生成会员价list
//		if(lvidStr!=null && lvidStr.length>0){   
			List<GoodsLvPrice> priceList  =  createGoodsPrices(goodsid);
			if(priceList != null && !priceList.isEmpty()){
				MemberPriceListAddResp resp = new MemberPriceListAddResp();
				MemberPriceListAddReq req = new MemberPriceListAddReq();
				req.setPriceList(priceList);
				resp = memberPriceServ.addPriceByList(req);
			}
//		}  
		
	}
	
	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		//add by wui 屏蔽掉，GoodsSpecPlugin规则统一处理
		this.processprice(goods.get("goods_id").toString());
	}

	
	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
		//add by wui 屏蔽掉，GoodsSpecPlugin规则统一处理
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		this.processprice(goods.get("goods_id").toString());	

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
	private List<GoodsLvPrice> createGoodsPrices(String goodsid ){
//		List<GoodsPrice> goodsLvPrices = new ArrayList<GoodsPrice>();
//		for(int i=0;i<lvidStr.length;i++){
//			String lvid =  lvidStr[i];
//			Double  lvPrice = StringUtil.toDouble( lvPriceStr[i] );
//			
//			if( lvPrice.doubleValue()!=0 ){//输入了会员价格
//				GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
//				goodsLvPrice.setGoodsid(goodsid);
//				goodsLvPrice.setPrice(lvPrice);
//				goodsLvPrice.setLvid(lvid);
//				goodsLvPrices.add(goodsLvPrice);
//			}
//			
//		}
		
		
		if(StringUtils.isEmpty(goodsid)) 
			throw new RuntimeException("商品id不能为空");
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		String[] lvid = httpRequest.getParameterValues("price.lvid");
		String[] lvPrice = httpRequest.getParameterValues("price.lvPrice");
		String[] lvDiscount = httpRequest.getParameterValues("price.lv_discount");		
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
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
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
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "goods_price";
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

//
//	public IMemberLvManager getMemberLvManager() {
//		return memberLvManager;
//	}
//
//
//	public void setMemberLvManager(IMemberLvManager memberLvManager) {
//		this.memberLvManager = memberLvManager;
//	}
//
//

//
//	public IGoodsPriceManager getGoodsPriceManager() {
//		return goodsPriceManager;
//	}
//
//
//	public void setGoodsPriceManager(IGoodsPriceManager goodsPriceManager) {
//		this.goodsPriceManager = goodsPriceManager;
//	}

}
