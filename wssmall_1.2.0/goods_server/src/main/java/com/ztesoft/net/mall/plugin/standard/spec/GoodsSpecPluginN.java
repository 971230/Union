package com.ztesoft.net.mall.plugin.standard.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import services.MemberLvInf;
import services.SupplierInf;
import zte.params.member.req.GoodsMemberPriceListReq;
import zte.params.member.resp.GoodsMemberPriceListResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsDeleteEventN;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/**
 * 商品规格插件
 * @author zou.qh
 *
 */
public class GoodsSpecPluginN extends AbstractGoodsPluginN implements IGoodsDeleteEventN{
 
	private IProductManager productManager;
	private IGoodsManagerN goodsManagerN;
	private SupplierInf supplierServ;
	
	private MemberLvInf memberLvServ;
	@Override
	public void addTabs(){
		//this.addTags(6, "规格");
	}
	
	/**
	 * 响应修改时填充数据事件
	 * <br/>形成规格list
	 */
	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		String goods_id = goods.get("goods_id").toString();
		String have_spec = Const.getStrValue(goods, "have_spec");
		List<Specification> specList  =productManager.listSpecs(goods_id);
		List<Product> productList = productManager.list(goods_id);
		GoodsMemberPriceListReq req = new GoodsMemberPriceListReq();
		req.setGoods_id(goods_id);
		GoodsMemberPriceListResp resp = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom()).execute(req, GoodsMemberPriceListResp.class);
		List<Map> priceList = resp.getPriceList();
		freeMarkerPaser.putData("have_spec", "1");
		freeMarkerPaser.putData("priceList", priceList);
		freeMarkerPaser.putData("productList", productList);
		//freeMarkerPaser.putData("specList", specList);
		freeMarkerPaser.setPageName("specn");
		return freeMarkerPaser.proessPageContent();
	}
	
	/**
	 * 响应商品添加页填充数据事件
	 * <br/>
	 */
	@Override
	public String onFillGoodsAddInput() {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.putData("have_spec", "0");
		freeMarkerPaser.setPageName("specn");
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
//		this.processGoods(goods, request);
	}
	
	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)  {
		this.processSpec(goods, goodsExtData);
	}
	
	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
		this.processSpec(goods, goodsExtData);
	}
	

	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
		this.processGoods(goods, goodsExtData);
	}
	
	/**
	 * 在添加商品之前处理商品要添加的数据
	 * @param goods
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void processGoods(Map goods, GoodsExtData goodsExtData){
		String haveSpec = goodsExtData.getHaveSpec();
		goods.put("have_spec", haveSpec);
		//开启规格
		if("1".equals(haveSpec)){
			//记录规格相册关系
			String specs_img = goodsExtData.getSpec_imgs();
			specs_img = specs_img==null?"{}":specs_img;
			goods.put("specs", specs_img);
		}

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
	private  void processSpec(Map goods, GoodsExtData goodsExtData){
		if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
		String goodsId = goods.get("goods_id").toString();
		String haveSpec = goodsExtData.getHaveSpec(); 
		boolean is_copy = goodsExtData.getIs_copy();
		if("1".equals(haveSpec)){
			/**
			 * =======================
			 * 带有规格的商品的逻辑
			 * =======================
			 */
			String[] specidsAr = goodsExtData.getSpecids(); //规格id数组
			String[] specvidsAr = goodsExtData.getSpecvids();//规格值id数组
			String[] productids = goodsExtData.getSpec_productids(); //货品id数组
			String[] prices = goodsExtData.getSpec_prices();
			String[] stores = goodsExtData.getStores();
			String[] weights = goodsExtData.getWeights();
			String[] sns = goodsExtData.getSns();
			
			String[] specvalues = goodsExtData.getSpecValues();
			String[] lvPriceValues = goodsExtData.getLvPriceValues();
			String[] lvIdValues = goodsExtData.getLvIdValues();
			
			sns = goodsExtData.getSns();
			if(sns == null || sns.length == 0)
				return ;
			
			List<Product> productList = new ArrayList<Product>();
			int i=0;
			for(String sn  :sns){
				String productId = is_copy?"":StringUtil.isEmpty(productids[i])?"":productids[i];
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
				if(!StringUtils.isEmpty(productId)){
					product.setProduct_id(productId); //2010-1-12新增写入货品id，如果是新货品，则会是null
				}
				product.setHaveSpec(haveSpec) ;//2013-08-22 规则开启 mod by easonwu 
				
				//价格
				if(null==prices[i] || "".equals(prices[i]))
					product.setPrice( 0D );
				else
					product.setPrice(  Double.valueOf(   prices[i]) );
				//库存
				if(null==stores[i] || "".equals(stores[i]))
					product.setStore(0);
				else
					product.setStore(Integer.valueOf(stores[i]));
				//重量
				if(null==weights[i] || "".equals(weights[i]))
					product.setWeight(0D);
				else									
					product.setWeight(Double.valueOf(weights[i]));
				
				product.setSpecs(specvalues[i]);
				//20101123新增会员价格相应逻辑
				//规格为：name为加_index
				if(lvIdValues!=null && lvIdValues.length!=0){
					String[] lvPriceStr = lvPriceValues[i]!=null?lvPriceValues[i].split(","):new String[0];
					String[] lvidStr = lvIdValues[i]!=null?lvIdValues[i].split(","):new String[0];
					if(lvPriceStr == null || lvPriceStr.length==0){
						lvPriceStr = new String[lvidStr.length];
					}
					//生成会员价list
					if(lvidStr!=null && lvidStr.length>0){  //lzf add line 20110114
						List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPriceStr, lvidStr, goodsId, product.getProduct_id());
						product.setGoodsLvPrices(goodsLvPrices);
					}
				}
				productList.add(product);
				i++;
			} 
			
			this.productManager.add(productList, goodsId, haveSpec);
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
			String[] lvPriceStr = goodsExtData.getSpec_lvPrice();
			String[] lvidStr = goodsExtData.getSpec_lvid();
			
			String color = goodsExtData.getColor();
			product.setColor(color);
			//生成会员价list
			if(lvidStr!=null && lvidStr.length>0){  //lzf add line 20110114
				List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPriceStr, lvidStr, goodsId, product.getProduct_id());
				product.setGoodsLvPrices(goodsLvPrices);
			} //lzf add line 20110114
			
			List<Product> productList = new ArrayList<Product>();
			productList.add(product);
			this.productManager.add(productList,goodsId,haveSpec);
//			//商品导入时使用-屏蔽
//			HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
//			httpRequest.setAttribute("imp_product_id",product.getProduct_id());
			
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
			if( null != lvPriceStr[i] && !lvPriceStr[i].equals("")){//如果不为空：界面上各会员价格有初始化
				price = lvPriceStr[i];
			}else{
				if( null != product_id && !product_id.equals("")){//可能为界面没有初始化，尝试取数据库数据
					price = this.goodsManagerN.getAcceptPrice(goodsid, product_id, lvid);
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
	public void onGoodsDelete(String[] goodsid) {
		 this.productManager.delete(goodsid);
	}

	@Override
	public String getAuthor() {
		return "zou.qh";
	}

	@Override
	public String getId() {
		return "goodsspecN";
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

	public IGoodsManagerN getGoodsManagerN() {
		return goodsManagerN;
	}

	public void setGoodsManagerN(IGoodsManagerN goodsManagerN) {
		this.goodsManagerN = goodsManagerN;
	}
	
}
