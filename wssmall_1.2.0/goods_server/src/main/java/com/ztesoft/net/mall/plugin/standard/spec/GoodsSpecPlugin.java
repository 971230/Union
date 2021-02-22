package com.ztesoft.net.mall.plugin.standard.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvListResp;
import services.MemberLvInf;
import services.SupplierInf;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsDeleteEvent;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/**
 * 商品规格插件
 * @author enation
 *
 */
public class GoodsSpecPlugin extends AbstractGoodsPlugin implements IGoodsDeleteEvent{
	
 
	private IProductManager productManager;
	private IGoodsManager goodsManager;
	private SupplierInf supplierServ;
	
	private MemberLvInf memberLvServ;
	@Override
	public void addTabs(){
		this.addTags(6, "规格");
	}
	
	/**
	 * 在添加商品之前处理商品要添加的数据
	 * @param goods
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void processGoods(Map goods, HttpServletRequest request){
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		String haveSpec = httpRequest.getParameter("haveSpec");
		goods.put("have_spec", haveSpec);
		//未开启规格
		if(goods.get("have_spec")== null || goods.get("have_spec").equals("0")){
			
//			goods.put("cost", httpRequest.getParameter("cost") );
//			goods.put("price", httpRequest.getParameter("price") );
//			goods.put("weight", httpRequest.getParameter("weight") );
//			goods.put("store", httpRequest.getParameter("store") );

			
		}else if("1".equals(haveSpec)){
			
			//记录规格相册关系
			String specs_img = httpRequest.getParameter("spec_imgs");
			specs_img = specs_img==null?"{}":specs_img;
			goods.put("specs", specs_img);
			
			
//			String[] prices = httpRequest.getParameterValues("prices");
//			String[] costs = httpRequest.getParameterValues("costs");
//			String[] stores = httpRequest.getParameterValues("stores");
//			String[] weights = httpRequest.getParameterValues("weights");
			
//			if(prices!=null && prices.length>0){ goods.put("price", prices[0] ); }
//			if(costs!=null && costs.length>0){ goods.put("cost", costs[0] ); }
//			if(stores!=null && stores.length>0){ goods.put("store", stores[0] ); }
//			if(weights!=null && weights.length>0){ goods.put("weight", weights[0] ); }
 
		}
//		if(StringUtil.isEmpty((String)goods.get("cost")) ){ goods.put("cost", 0);}
//		if(StringUtil.isEmpty((String)goods.get("price"))){ goods.put("price", 0);}
//		if(StringUtil.isEmpty((String)goods.get("weight"))){ goods.put("weight", 0);}
//		if(StringUtil.isEmpty((String)goods.get("store"))){ goods.put("store", 0);}		
		
		
		
		
		
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
//		this.processGoods(goods, request);
	}
	

	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request)  {
		this.processSpec(goods, request);
	}
	

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)  {
		this.processGoods(goods, request);
	}
	
	/**
	 * 2011-01-12新增：修复保存商品时，其货品的id会全部重新生成的问题<br>
	 * 处理有规格商品的逻辑
	 * 每一行（即每一个货品product）,有如下hidden：
	 * 		specids(规格id数组，以,号隔开)如：1,2
	 *      specvids(规格值数组，以,号隔开)如21,20
	 *      productids货品id数组，如果为新增，则为空
	 * @param goods
	 * @param request
	 */
	private  void processSpec(Map goods, HttpServletRequest request){
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		String goodsId = goods.get("goods_id").toString();
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		
		String haveSpec = httpRequest.getParameter("haveSpec") == null ? (String)httpRequest.getAttribute("haveSpec"):(String)httpRequest.getParameter("haveSpec"); 
		
		if("1".equals(haveSpec)){
			/**
			 * =======================
			 * 带有规格的商品的逻辑
			 * =======================
			 */
			
			String[] skus = httpRequest.getParameterValues("skus");
			String[] sns = httpRequest.getParameterValues("sns");
			if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
				//sns = new String[]{goods.get("sn").toString()};
				if(skus == null || skus.length==0)
					return ;
			}
			else{
				sns = httpRequest.getParameterValues("sns");
				if(sns == null || sns.length == 0)
					return ;
			}
			
			String[] specidsAr = httpRequest.getParameterValues("specids"); //规格id数组
			String[] specvidsAr = httpRequest.getParameterValues("specvids");//规格值id数组
			
			String[] productids = httpRequest.getParameterValues("productids"); //货品id数组
			String[] prices = httpRequest.getParameterValues("prices");
//			String[] costs = httpRequest.getParameterValues("costs");
			String[] stores = httpRequest.getParameterValues("stores");
			String[] weights = httpRequest.getParameterValues("weights");
			
			
			List<Product> productList = new ArrayList<Product>();
			int i=0;
			if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
				for(int s=0;s<skus.length;s++){
					
					String sku = skus[s];
					String productId = StringUtil.isEmpty(productids[i])?"":productids[i];
					if(sku==null || sku.equals(""))
						sku=goods.get("sku")+"-"+(i+1);
				
					/*
					 * 组合商品、货品、规格值、规格对应关系
					 */
					List<SpecValue> valueList = new ArrayList<SpecValue>();
					int j =0;
					String[] specids =specidsAr[i].split(","); //此货品的规格
					String[] specvids= specvidsAr[i].split(","); //此货品的规格值
					
					//此货品的规格值list
					if(specids.length>0){
						for(String specid:specids){
							SpecValue specvalue = new SpecValue();
							if(specvids[j].trim().equals(""))continue;
							specvalue.setSpec_value_id(specvids[j].trim());
							specvalue.setSpec_id(specid.trim());
							valueList.add(specvalue);
							j++;
						}
					}
					
					//生成货品对象
					Product product = new Product();
					product.setSku(sku);
					product.setType(goods.get("type").toString());
					product.setGoods_id(goodsId);
					product.setSpecList(valueList);//设置此货品的规格list
					product.setName((String)goods.get("name"));
					product.setSn( sns[s] );
					product.setProduct_id(productId); //2010-1-12新增写入货品id，如果是新货品，则会是null
					product.setHaveSpec(haveSpec) ;//2013-08-22 规则开启 mod by easonwu 
					String[] specvalues =  httpRequest.getParameterValues("specvalue_"+i);
					product.setSpecs(StringUtil.arrayToString(specvalues, "、"));
					//价格
					if( null==prices || null==prices[i] || "".equals(prices[i]))
						product.setPrice( 0D );
					else
						product.setPrice(  Double.valueOf(   prices[i]) );
					
					
					
					if(null==stores || null==stores[i] || "".equals(stores[i]))
						product.setStore(0);
					else
						product.setStore(Integer.valueOf(stores[i]));
					
					//成本价
//					if(null==costs[i] || "".equals(costs[i]))
//						product.setCost( 0D);
//					else	
//						product.setCost( Double.valueOf( costs[i]));
					
					//重量
					if(null==weights || null==weights[i] || "".equals(weights[i]))
						product.setWeight(0D);
					else									
						product.setWeight(Double.valueOf(weights[i]));
					
					
					
					//20101123新增会员价格相应逻辑
					//规格为：name为加_index
					String[] lvPriceStr = httpRequest.getParameterValues("lvPrice_"+i);
					String[] lvidStr = httpRequest.getParameterValues("lvid_"+i);
					
					
					//生成会员价list
					if(lvidStr!=null && lvidStr.length>0){  //lzf add line 20110114
						List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPriceStr, lvidStr, goodsId, product.getProduct_id());
						product.setGoodsLvPrices(goodsLvPrices);
					}   //lzf add line 20110114
					
					productList.add(product);
					i++;
				}
			}else{
				for(String sn  :sns){
					
					
					String productId = StringUtil.isEmpty(productids[i])?"":productids[i];
					if(sn==null || sn.equals(""))
						sn=goods.get("sn")+"-"+(i+1);
				
					/*
					 * 组合商品、货品、规格值、规格对应关系
					 */
					List<SpecValue> valueList = new ArrayList<SpecValue>();
					int j =0;
					String[] specids =specidsAr[i].split(","); //此货品的规格
					String[] specvids= specvidsAr[i].split(","); //此货品的规格值
					
					//此货品的规格值list
					if(specids.length>0){
						for(String specid:specids){
							SpecValue specvalue = new SpecValue();
							if(specvids[j].trim().equals(""))continue;
							specvalue.setSpec_value_id(specvids[j].trim());
							specvalue.setSpec_id(specid.trim());
							valueList.add(specvalue);
							j++;
						}
					}
					
					//生成货品对象
					Product product = new Product();
					product.setGoods_id(goodsId);
					product.setSpecList(valueList);//设置此货品的规格list
					product.setName((String)goods.get("name"));
					product.setSn( sn );
					product.setProduct_id(productId); //2010-1-12新增写入货品id，如果是新货品，则会是null
					product.setHaveSpec(haveSpec) ;//2013-08-22 规则开启 mod by easonwu 
					String[] specvalues =  httpRequest.getParameterValues("specvalue_"+i);
					product.setSpecs(StringUtil.arrayToString(specvalues, "、"));
					//价格
					if(null==prices[i] || "".equals(prices[i]))
						product.setPrice( 0D );
					else
						product.setPrice(  Double.valueOf(   prices[i]) );
					
					
					
					if(null==stores[i] || "".equals(stores[i]))
						product.setStore(0);
					else
						product.setStore(Integer.valueOf(stores[i]));
					
					//成本价
//					if(null==costs[i] || "".equals(costs[i]))
//						product.setCost( 0D);
//					else	
//						product.setCost( Double.valueOf( costs[i]));
					
					//重量
					if(null==weights[i] || "".equals(weights[i]))
						product.setWeight(0D);
					else									
						product.setWeight(Double.valueOf(weights[i]));
					
					
					
					//20101123新增会员价格相应逻辑
					//规格为：name为加_index
					String[] lvPriceStr = httpRequest.getParameterValues("lvPrice_"+i);
					String[] lvidStr = httpRequest.getParameterValues("lvid_"+i);
					
					
					//生成会员价list
					if(lvidStr!=null && lvidStr.length>0){  //lzf add line 20110114
						List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPriceStr, lvidStr, goodsId, product.getProduct_id());
						product.setGoodsLvPrices(goodsLvPrices);
					}   //lzf add line 20110114
					
					productList.add(product);
					i++;
				} 
			}
			
			this.productManager.add(productList , goodsId);
		}else{
			 
			Product product = this.productManager.getByGoodsId(goodsId);
			if(product== null ) {
				product =  new Product();
			}
			String cost =goods.get("cost")+"";
			if(StringUtil.isEmpty(cost) || "null".equals(cost))
				cost ="0";
			
			String store =goods.get("store")+"";
			if(StringUtil.isEmpty(store) || "null".equals(store))
				store ="0";
			
			
			String weight =goods.get("weight")+"";
			if(StringUtil.isEmpty(weight) || "null".equals(weight))
				weight ="0";
			
			
			String price =goods.get("price")+"";
			if(StringUtil.isEmpty(price) || "null".equals(price))
				price ="0";
			
			product.setHaveSpec(haveSpec) ;//2013-08-22 规则开启 mod by easonwu 
			product.setGoods_id(goodsId);
			product.setCost(  Double.valueOf(cost) );
			product.setPrice(   Double.valueOf( price)  );
			product.setSn((String)goods.get("sn"));
			product.setStore(Integer.valueOf(store));
			product.setWeight(Double.valueOf(weight));
			product.setName((String)goods.get("name"));
			product.setSku((goods.get("sku")==null || "".equals(goods.get("sku").toString()))?null:goods.get("sku").toString());
			product.setType((goods.get("type")==null || "".equals(goods.get("type").toString()))?null:goods.get("type").toString());
			//add by wui
			//product.setSpecs(goods.get("intro").toString());//配件导入描述信息
			
			//20101123新增会员价格相应逻辑
			String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
			String[] lvidStr = httpRequest.getParameterValues("lvid");
			
			//String color = httpRequest.getParameter("color");
			//product.setColor(color);
			//颜色去手机参数中的数值...去掉重复选项---zengxianlian
			if(null != goods.get("params") && !"[]".equals(goods.get("params"))){
				net.sf.json.JSONArray params = net.sf.json.JSONArray.fromObject( goods.get("params"));
				JSONObject json = params.getJSONObject(0);
				net.sf.json.JSONArray paramList =json.getJSONArray("paramList");
				if(paramList.size()>0){
					for (int j = 0; j < paramList.size(); j++) {
						JSONObject jo = (JSONObject) paramList.get(j);
						if("color".equals(jo.get("ename"))){
							//logger.info(jo.get("value"));
							product.setColor(jo.get("value").toString());
							break;
						}
					}
				}
			}
			//生成会员价list
			if(lvidStr!=null && lvidStr.length>0){  //lzf add line 20110114
				List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPriceStr, lvidStr, goodsId, product.getProduct_id());
				product.setGoodsLvPrices(goodsLvPrices);
			} //lzf add line 20110114
			
			
			
			
			List<Product> productList = new ArrayList<Product>();
			productList.add(product);
			this.productManager.add(productList,goodsId);
			
			httpRequest.setAttribute("imp_product_id",product.getProduct_id());
			
			
		}
	}
 
	/**
	 * 根据会员级别id和会员价信息生成会员价list<br>
	 * 会员价格如果无空则不插入数据，即不生成会员价，而是按此会员级别的默认折扣计算会员价格,以减少冗余数据。
	 * 
	 * @param lvPriceStr 会员价数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param lvidStr 会员级别id数组，数组的值类型为字串，考虑到一般由request中获取。
	 * @param goodsid 当前商品id，没有填充productid,此id在productmanager中添加数据时动态获取到并填充
	 * @return 生成的List<GoodsLvPrice>
	 */
	private List<GoodsLvPrice> createGoodsLvPrices(String[] lvPriceStr,String[] lvidStr,String goodsid, String product_id){
		List<GoodsLvPrice> goodsLvPrices = new ArrayList<GoodsLvPrice>();
		for(int i=0;i<lvidStr.length;i++){
			String lvid = lvidStr[i];
			String price = null;
			if(!lvPriceStr[i].equals("") && null != lvPriceStr[i]){//如果不为空：界面上各会员价格有初始化
				price = lvPriceStr[i];
			}else{
				if(!product_id.equals("") && null != product_id){//可能为界面没有初始化，尝试取数据库数据
//					String sql = SF.goodsSql("ACCEPT_PRICE_GET");
//					price = this.baseDaoSupport.queryForString(sql, goodsid, product_id, lvid);
					price = this.goodsManager.getAcceptPrice(goodsid, product_id, lvid);
				}else{
					price = null;
				}
			}
			if( null != price && !price.equals("")){
				price = price.replaceAll(",", "");
				Double lvPrice = new Double(price);
				GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
				goodsLvPrice.setGoodsid(goodsid);
				goodsLvPrice.setPrice(lvPrice);
				goodsLvPrice.setLvid(lvid);
				goodsLvPrices.add(goodsLvPrice);
			}
		}
		return goodsLvPrices;
	}
	
	
	
	
	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)  {
		this.processSpec(goods, request);
		
	}
	
	
	
	/**
	 * 响应修改时填充数据事件
	 * <br/>形成规格list
	 */
	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		String goods_id = goods.get("goods_id").toString();
		List<Specification> specList  =productManager.listSpecs(goods_id);
		List<Product> productList = productManager.list(goods_id);
		
		List lvList = new ArrayList();
		MemberLvListReq req = new MemberLvListReq();
		MemberLvListResp resp = new MemberLvListResp();
		resp = memberLvServ.getMemberLvList(req);
		if(resp != null){
			lvList = resp.getMemberLvs();
		}
		freeMarkerPaser.putData("lvList", lvList);
		
		freeMarkerPaser.putData("productList", productList);
		freeMarkerPaser.putData("specList", specList);
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("spec_"+ManagerUtils.getSourceFrom().toLowerCase());
		}
		else{
			freeMarkerPaser.setPageName("spec");
		}
//		freeMarkerPaser.setPageFolder("/");
		return freeMarkerPaser.proessPageContent();
	}
	
	
	
	/**
	 * 响应商品添加页填充数据事件
	 * <br/>
	 */
	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		
		//freeMarkerPaser.setPageFolder("/plugin/spec");
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("spec_"+ManagerUtils.getSourceFrom().toLowerCase());
		}
		else{
			freeMarkerPaser.setPageName("spec");
		}
		return freeMarkerPaser.proessPageContent();
	}



	
	@Override
	public void onGoodsDelete(String[] goodsid) {
		 this.productManager.delete(goodsid);
	}

	@Override
	public String getAuthor() {
		 
		return "kingapex";
	}

	@Override
	public String getId() {
		 
		return "goodsspec";
	}

	@Override
	public String getName() {
		 
		return "通用商品规格插件";
	}

	@Override
	public String getType() {
		return "";
	}

	@Override
	public String getVersion() {
		 
		return "1.0";
	}

	@Override
	public void perform(Object... params) {
		
	}
 



	public IProductManager getProductManager() {
		return productManager;
	}



	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}


	public SupplierInf getSupplierServ() {
		return supplierServ;
	}

	public void setSupplierServ(SupplierInf supplierServ) {
		this.supplierServ = supplierServ;
	}

	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}

	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}	
	
}
