package com.ztesoft.net.mall.widget.goods.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.member.req.MemberLvByLvIdReq;
import params.member.req.MemberPriceListByLvIdReq;
import params.member.resp.MemberLvByLvIdResp;
import params.member.resp.MemberPriceListByLvIdResp;
import services.MemberLvInf;
import services.MemberPriceInf;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.StringMapper;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.mapper.GoodsListMapper;
import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
 

public class GoodsDataProvider extends BaseSupport  {


	private IGoodsCatManager goodsCatManager;
	
	private MemberPriceInf memberPriceServ;
	private MemberLvInf memberLvServ;
	
	private static Map<Integer,String> orderMap;
	static{
		orderMap = new HashMap<Integer, String>();
		orderMap.put(0, " goods_id desc");
		orderMap.put(1, " last_modify desc");
		orderMap.put(2, " last_modify asc");
		orderMap.put(3, " price desc");
		orderMap.put(4, " view_count desc");
		orderMap.put(5, " buy_count desc");
	}
	

	
	@SuppressWarnings("unchecked")
	public List<GoodsView> list(Term term ,int num){
		StringBuffer sql = new StringBuffer();
		sql.append(SF.goodsSql("GOODS_SELECT_BY_TYPE"));
		String tagids  = term.getTagid();
		String brandids = term.getBrandid();
		String catids = term.getCatid();
		String type_code = term.getType_code();
		Integer typeid = term.getTypeid();
		String  keyword = term.getKeyword();
		
		if(!StringUtil.isEmpty(keyword)){
			sql.append(" and (name like '%"+keyword+"%' or brief like '%"+keyword+"%' or intro like '%"+keyword+"%' or search_key like '%"+keyword+"%')");
		}
		
		if(ThreadContextHolder.isMemberLvCache()){
			String lv_id = ManagerUtils.getCurrentLvId();
			sql.append(" and exists (select 1 from es_goods_lv_cat gl where gl.cat_id=g.cat_id and gl.lv_id='"+lv_id+"') ");
		}
		
		if(typeid!=null ){
			sql.append(" and type_id ="+typeid);		
		}
	
		
		if(!StringUtil.isEmpty(type_code) ){
			sql.append(" and b.type_code ='"+type_code+"'");		
		}
		
		
		
		if(!StringUtil.isEmpty(brandids)){
			sql.append(" and brand_id in("+brandids+")");		
		}
		
		
		 
		if(!StringUtil.isEmpty(catids)){//lzf modified 20101210
			String str_catids = "0";
			String[] ids = catids.split(",");
			for(String cat_id:ids){
				Cat cat = this.goodsCatManager.getById(cat_id);
				List<String> catIdList = this.baseDaoSupport.queryForList(SF.goodsSql("CAT_ID_SELECT")+"and cat_path like '" + cat.getCat_path() + "%'", new StringMapper());
				str_catids += "," + StringUtil.listToString(catIdList,
						",");
			}
			if(!StringUtil.isEmpty(str_catids) && str_catids.length()-1 == str_catids.lastIndexOf(",")){
				str_catids = str_catids.substring(0,str_catids.lastIndexOf(","));
			}
			sql.append(" and cat_id in("+str_catids+")");		
		}		
		
 
		if(!StringUtil.isEmpty(tagids)){
			String filter= this.goodsIdInTags(tagids);
			filter =filter.equals("")?"-1":filter;
			sql.append( " and goods_id in(" +filter+")" );
		}
		
		if( term.getMaxprice() !=null){
			sql.append(" and price<="+ term.getMaxprice());
		}
		
		if( term.getMinprice() !=null){
			sql.append(" and price>="+ term.getMinprice() );
		}
		
		Integer order = term.getOrder();
		if(order==null){
			order = 1;
		}
		
//		if(!StringUtil.isEmpty(StringUtil.getAgnId()))
//		{
//			sql.append(" and g.staff_no = ").append(StringUtil.getAgnId());
//		}
//		
		sql.append( " order by "+ orderMap.get(order));
		
		if(num ==0)
			num = 50; // 缺省
		List<GoodsView> list = null;
		try{
			 list = this.baseDaoSupport.queryForList(sql.toString(), 1, num,new GoodsListMapper());
		}catch (Exception e) {
			
		}
//		String countSql ="select count(*) from " +sql.toString().substring(sql.toString().indexOf("from ")+4);
//		Page webpage = this.daoSupport.queryForPage(sql.toString(),countSql, 1, 50,
//				new GoodsListMapper());
//		List list = (List) webpage.getResult();
		if(list!=null  && !list.isEmpty()){
		  list.get(list.size()-1).setIsLast(true) ;
		  list.get(0).setIsFirst(true) ;
		}
		list = list == null ? new ArrayList() : list;
		
		/****************计算会员价格******************/
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		List<GoodsLvPrice> memPriceList = new ArrayList<GoodsLvPrice>();
		double discount =1; //默认是原价,防止无会员级别时出错
		if(member!=null && member.getLv_id()!=null){
			
			MemberPriceListByLvIdResp mbpResp = new MemberPriceListByLvIdResp();
			MemberPriceListByLvIdReq req = new MemberPriceListByLvIdReq();
			req.setLv_id(member.getLv_id());
			
			mbpResp = memberPriceServ.getPriceListByLvId(req);
			if(mbpResp != null){
				memPriceList = mbpResp.getGoodsLvPriceList();
			}
			
			MemberLv lv = null;
			
			MemberLvByLvIdReq req1 = new MemberLvByLvIdReq();
			MemberLvByLvIdResp mlResp = new MemberLvByLvIdResp();
			req1.setLv_id(member.getLv_id());
			
			mlResp = memberLvServ.getMemberLvByLvId(req1);
			if(mlResp != null){
				lv = mlResp.getMemberLv();
			}
			discount = lv.getDiscount()/100.00;
			this.applyMemPrice(list, memPriceList, discount);
		}
		
		return list; 
	}
	
	
	/**
	 * 应用会员价
	 * @param itemList
	 * @param memPriceList
	 * @param discount
	 */
	private void applyMemPrice(List<GoodsView>  itemList,List<GoodsLvPrice> memPriceList,double discount ){
		for(GoodsView item : itemList ){
			double price  = item.getPrice() *  discount;
			for(GoodsLvPrice lvPrice:memPriceList){
				if( item.getHasSpec()==0 && item.getGoods_id().equals(lvPrice.getGoodsid())){
					price = lvPrice.getPrice();
				} 
			}
			
			item.setPrice(price);
		}
	}
	
//	private void applyMemPromotionPrice(double price, Integer goodsid){
//		IUserService userService = UserServiceFactory.getUserService();
//		Member member = userService.getCurrentMember();
//		Integer memberLvid = member.getLv_id();
//		List<Promotion> list = promotionServ.list(goodsid, memberLvid);
//		if((list!=null)&&list.size()>0){
//			Promotion pmn = list.get(0);
//			pmn.get
//		}
//		
//	}
	
	
	/**
	 * 读取在tags范围的goodsid字串，以,号分隔
	 * @param tags
	 * @return 如果没有找到返回 ""
	 */
	private String goodsIdInTags(String tags){
		String sql =SF.goodsSql("REL_ID_SELECT")+" and tag_id in (" +tags+")";
		List<String> goodsIdList = this.baseDaoSupport.queryForList(sql, new StringMapper());
		return StringUtil.listToString(goodsIdList, ",");
	}
 

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}


	public MemberPriceInf getMemberPriceServ() {
		return memberPriceServ;
	}


	public void setMemberPriceServ(MemberPriceInf memberPriceServ) {
		this.memberPriceServ = memberPriceServ;
	}


	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}


	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}


	public static Map<Integer, String> getOrderMap() {
		return orderMap;
	}


	public static void setOrderMap(Map<Integer, String> orderMap) {
		GoodsDataProvider.orderMap = orderMap;
	}
 
}
