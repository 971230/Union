package com.ztesoft.net.mall.core.action.backend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberPriceManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

public class MemberPriceAction extends WWAction {
	
	private Double price;
	private String goodsid;
	private String productid;
	private IMemberLvManager memberLvManager;
	private IMemberPriceManager memberPriceManager;
	private List<MemberLv > lvList;
	private IDcPublicInfoManager dcPublicInfoManager;
	
//	private IGoodsPriceManager goodsPriceManager ;
	

	/**
	 * 显示会员价格输入对话框
	 */
	@Override
	public String execute(){
		this.processLv();
		return "list";
	}
	
	public String listMemberPriceN(){
		this.processLv();
		return "listn";
	}

	/**
	 * 显示会员价格input列表，在生成规格时填充所用
	 * @return
	 */
	public String disLvInput(){
		this.processLv();
		return "dis_lv_input";
	}
	
	/**
	 * 显示会员价格input列表，在生成规格时填充所用
	 * @return
	 */
	public String disLvInputN(){
		this.processLv();
		return "dis_lv_inputn";
	}
	
	public String getLvPriceJson(){
		List priceList  = null ;
		try{
			if(StringUtils.isNotEmpty(goodsid)){
				priceList  = this.memberPriceManager.loadMemberLvPriceList(goodsid);
				if(priceList == null || priceList.isEmpty())
					priceList  = this.memberPriceManager.loadMemberLvList();
			}else{
				priceList  = this.memberPriceManager.loadMemberLvList();
			}
			
			DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
			List discount = dcPublicCache.getList("1");
			
			JSONArray jsonArray = JSONArray.fromObject( priceList );  
			JSONArray discountArray = JSONArray.fromObject( discount );  
			this.json = "{result:1,priceData:"+jsonArray.toString()+",discountData:"+discountArray.toString()+"}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e.fillInStackTrace());
			this.json = "{result:0,message:"+e.getMessage()+"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	private void processLv(){
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
		String[] lvidStr = httpRequest.getParameterValues("lvid");
		
		Map<String,Double> lvMap = new HashMap<String,Double>() ;
		if(lvidStr != null && lvidStr.length > 0){
			for(int i = 0 ; i <lvidStr.length ; i++ ){
				lvMap.put(lvidStr[i], Double.valueOf(lvPriceStr[i])) ;
			}
		}
		
		if(StringUtils.isNotEmpty(this.goodsid) && StringUtils.isNotEmpty(this.productid)){
			List<Map> datas = memberPriceManager.getSpecPrices(this.goodsid, this.productid) ;
			if(datas != null && !datas.isEmpty()){
				lvList = new ArrayList<MemberLv>() ;
				for(Map map :datas ){
					MemberLv lv = new MemberLv() ;
					lv.setLv_id((String)map.get("lvid")) ;
					lv.setLvPrice( ((BigDecimal)map.get("price") ).doubleValue() ) ;
					lv.setName((String)map.get("name")) ;
					lvList.add(lv) ;
				}
			}else{
				lvList = memberLvManager.list();
				price= price==null?0:price;
				for(MemberLv lv:lvList){
					int discount = lv.getDiscount();
					lv.setLvPrice(price*discount/100);
				}
			}
		}else{ 
			lvList = memberLvManager.list();
			price= price==null?0:price;
			for(MemberLv lv:lvList){
				int discount = lv.getDiscount();
				lv.setLvPrice(price*discount/100);
			}
		}
		
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}

	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

	public List<MemberLv> getLvList() {
		return lvList;
	}

	public void setLvList(List<MemberLv> lvList) {
		this.lvList = lvList;
	}

	public IMemberPriceManager getMemberPriceManager() {
		return memberPriceManager;
	}

	public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
		this.memberPriceManager = memberPriceManager;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}
	
}
