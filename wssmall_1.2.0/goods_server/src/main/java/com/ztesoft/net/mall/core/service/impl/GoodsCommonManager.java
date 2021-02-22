package com.ztesoft.net.mall.core.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.springframework.util.StringUtils;

import params.adminuser.req.ZbAdminUserGetReq;
import params.adminuser.resp.ZbAdminUserGetResp;
import params.member.req.MemberPriceListAddReq;
import params.member.resp.MemberPriceListAddResp;
import services.MemberPriceInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsBusiness;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IGoodsCommonManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;


public class GoodsCommonManager extends BaseSupport implements IGoodsCommonManager {

	@Resource
	private IGoodsManager goodsManager;
	@Resource
	private IGoodsTypeManager goodsTypeManager;
	@Resource
	private ITagManager tagManager;
	@Resource
	private MemberPriceInf memberPriceServ;
	@Resource
	private IProductManager productManager;
	
	@Override
	public void saveGoodsBus(String goods_id, GoodsBusiness goodsBusiness){
		if(goodsBusiness != null){
			//更新es_goods_business表数据
			goodsBusiness.setGoods_id(goods_id);
			this.baseDaoSupport.update("es_goods_business", goodsBusiness, "goods_id='"+goodsBusiness.getGoods_id()+"'");
		}
	}

	@Override
	public void setGoodsUserInfo(Goods goods, String userId) {
		
		ZbAdminUserGetReq req = new ZbAdminUserGetReq();
		req.setUserid(StringUtils.isEmpty(userId)?"1":userId);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbAdminUserGetResp resp = client.execute(req, ZbAdminUserGetResp.class);
		AdminUser adminUser = resp.getAdminUser();
		
		String user_name = adminUser.getUsername();
		if(goods.getSearch_key()!=null){
			goods.setSearch_key("_"+user_name+"_"+goods.getSearch_key());
		}else{
			goods.setSearch_key("_"+user_name+"_");
		}
		if(Consts.GOODS_AUDIT_FAIL.equals(goods.getAudit_state())){
			if (Consts.CURR_FOUNDER0 == adminUser.getFounder().intValue() || ManagerUtils.isAdminUser() ) {
				goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
			}else{
				goods.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
			}
		}
		
	}

	@Override
	public void addOperLog(String userId,String realName,String goodsId,String... args) {
		//修改前添加操作日志
		Map logParam=new HashMap();
		logParam.put("oper_name", realName);
		logParam.put("goods_id", goodsId);
		logParam.put("oper_no", userId);
		//修改原有sql,把原来goods_package的p_code跟sn保存---zengxianlian
		Map goodsPackageMap = goodsManager.getPCodeAndSnByGoodsId(goodsId);
		logParam.put("p1", goodsPackageMap.get("p_code"));
		logParam.put("p2", goodsPackageMap.get("sn"));
		if(args != null && args.length==2){
			//修改后添加操作日志
			logParam.put("flag", "no");
			//修改原有sql,保存页面所传的p_code跟sn---zengxianlian
			logParam.put("p1", args[0]);
			logParam.put("p2", args[1]);
		}
		goodsManager.insertOperLog(logParam);
	}

	@Override
	public void saveGoodsPackageEdit(String goodsId,String actCode,String prodCode) {
		//商品修改活动编码与产品编码
		GoodsPackage goodsPackage = new GoodsPackage();
		goodsPackage.setGoods_id(goodsId);
		goodsPackage.setP_code(actCode);
		goodsPackage.setSn(prodCode);
		Map goodsPackageMap = ReflectionUtil.po2Map(goodsPackage);
		this.baseDaoSupport.update("goods_package", goodsPackageMap, "goods_id='"+ goodsId+"'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveGoodsEdit(String goodsId, Map goodsMap) {
		this.baseDaoSupport.update("goods", goodsMap, "goods_id='"+ goodsId+"'");		
	}

	@Override
	public void setGoodsModelCode(Map goods, String type, String typeId, List<Map<String, Object>> products){
		if(products!=null && products.size()>0 && 
				Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) && Consts.ECS_QUERY_TYPE_GOOD.equals(type)){
			String[] aGoodsIds = new String[products.size()];
			for(int i=0;i<products.size();i++){
				Map product = products.get(i);
				aGoodsIds[i] = Const.getStrValue(product, "goods_id");
			}
			if(Consts.GOODS_TYPE_PHONE.equals(typeId) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(typeId)){
				String model_code = this.goodsManager.getTerminalModelCode(aGoodsIds,Consts.GOODS_TYPE_TERMINAL);
				goods.put("model_code", model_code);
			}else if(Consts.GOODS_TYPE_NUM_CARD.equals(typeId)){
				String model_code = this.goodsManager.getTerminalModelCode(aGoodsIds,Consts.GOODS_TYPE_OFFER);
				goods.put("model_code", model_code);
			}
		}
	}

	@Override
	public void setGoodsSpecs(Map goods, String specs) {
		specs = specs==null?"{}":specs;
		goods.put("specs", specs);
	}
	
	@Override
	public void removeEmptyFields(Map goodsMap){
		if(StringUtils.isEmpty(goodsMap.get("fail_date")))
			goodsMap.remove("fail_date");
		if(StringUtils.isEmpty(goodsMap.get("effect_date")))
			goodsMap.remove("effect_date");
		goodsMap.put("create_time", DBTUtil.current());
	}

	@Override
	public void setGoodsProps(Map goods, String taocanLevel, String[] propvalues) {
		//广东联通ECS，当货品类型是套餐时，stype_id字段保存套餐档次
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) && Consts.OFFER_ID.equals(goods.get("type_id"))){
			goods.put("stype_id", taocanLevel);
		}

		if(propvalues!=null && propvalues.length>0){
			Map fields = new HashMap();
			int length = propvalues.length;
			length = length > 20 ? 20 : length; // 只支持20个自定义属性
			// 循环所有属性,按p_个数 为字段名存在 goods表中
			// 字段中存的是 值,当是下拉框时也存的是值,并不是属性的id
			for (int i = 0; i < length; i++) {
				String value = propvalues[i];
				fields.put("p" + (i + 1), value);
			}
			goods.putAll(fields);
		}
	}

	@Override
	public void saveGoodsTags(String goodsId, String[] tagsArr) {
		this.tagManager.saveRels(goodsId, tagsArr);
	}
	
	@Override
	public void savePricePriv(String goodsId){
		//先删后插
		this.baseDaoSupport.execute(SF.goodsSql("PRICE_PRIV_DEL_BY_GOODS_ID") ,goodsId );
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String source_from = cacheUtil.getConfigInfo(Consts.KEY_SOURCE_FROM);
		
		List<Map> membersLvs = this.baseDaoSupport.queryForList(SF.goodsSql("MEMBER_LV_SELECT"), source_from) ;
		Map<String,String> membersLvMap = new HashMap<String,String>() ;
		if(membersLvs != null && !membersLvs.isEmpty()){
			for(Map lv : membersLvs ){
				membersLvMap.put((String)lv.get("lv_id"), "00X") ;
			}	
		}
		
		Map<String,String> data = null ;
		for(Iterator<String> it = membersLvMap.keySet().iterator() ; it.hasNext() ;){
			String role_type = it.next() ;
			String state = membersLvMap.get(role_type) ;
			data = new HashMap<String,String>() ;
			data.put("role_type", role_type) ;
			data.put("state", state) ;
			data.put("goods_id", goodsId) ;
			this.baseDaoSupport.insert("es_price_priv", data);
		}
	}

	@Override
	public void saveGoodsLvPrices(String goodsId, String[] lvid,
			String[] lvPrice, String[] lvDiscount) {
		List<GoodsLvPrice> priceList = new ArrayList<GoodsLvPrice>();
		if(lvid!=null && lvid.length > 0 ){ 
			for(int i=0;i<lvid.length;i++){
				if(lvPrice != null){
					if(com.ztesoft.net.framework.util.StringUtil.isEmpty(lvPrice[i])){
						lvPrice[i] = "0";
					}
					if(-999 == Double.parseDouble(lvPrice[i]))
						continue ;
					GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
					goodsLvPrice.setGoodsid(goodsId);
					goodsLvPrice.setPrice(Double.parseDouble(lvPrice[i]));
					goodsLvPrice.setLvid(lvid[i]);
					goodsLvPrice.setLv_discount(Float.valueOf(lvDiscount[i]));
					priceList.add(goodsLvPrice);
				}
			}
			
			MemberPriceListAddResp resp = new MemberPriceListAddResp();
			MemberPriceListAddReq req = new MemberPriceListAddReq();
			req.setPriceList(priceList);
			memberPriceServ.addPriceByList(req);
		}
	}

	@Override
	public void saveGoodsRelations(String aGoodsId, List<Map<String, Object>> products,String relType) {
		Map param=new HashMap();
		param.put("goods_id", aGoodsId);
		//删除日志
		this.goodsManager.goodsRelLog(true,param);
		this.goodsManager.deleteGoodsRelProduct(aGoodsId);
		
		if(products==null || products.size()==0)
			return ;
		
		for(int i=0;i<products.size();i++){
			Map product = products.get(i);
			String product_id = Const.getStrValue(product, "product_id");
			String z_goods_id = Const.getStrValue(product, "goods_id");
			String rel_code = Const.getStrValue(product, "rel_code");
			if (StringUtils.isEmpty(rel_code)) {
				rel_code = goodsManager.createGoodsRelCode();
			}						
			
			Map data = new HashMap();
			data.put("a_goods_id", aGoodsId);
			data.put("z_goods_id", z_goods_id);
			data.put("rel_type", relType);
			data.put("rel_code", rel_code);
			data.put("product_id", product_id);
			data.put("source_from", "ECS");
			this.goodsManager.addGoodsRelProduct(data);
			
			//添加日志
			Map logParam=new HashMap();
			logParam.put("oper_name", ManagerUtils.getAdminUser()==null?"超级管理员":ManagerUtils.getAdminUser().getRealname());
			logParam.put("oper_no", ManagerUtils.getAdminUser()==null?"1":ManagerUtils.getAdminUser().getUserid());
			logParam.put("goods_id", aGoodsId);
			this.goodsManager.goodsRelLog(false,logParam);
		}
	}

	@Override
	public void saveProductInfo(Map goods, String[] lvIds, String[] lvPrices) {
		String goodsId = Const.getStrValue(goods, "goods_id");
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
		
		product.setHaveSpec("0") ;//2013-08-22 规则开启 mod by easonwu 
		product.setGoods_id(goodsId);
		product.setCost(  Double.valueOf(cost) );
		product.setPrice(   Double.valueOf( price)  );
		product.setSn((String)goods.get("sn"));
		product.setStore(Integer.valueOf(store));
		product.setWeight(Double.valueOf(weight));
		product.setName((String)goods.get("name"));
		product.setSku((goods.get("sku")==null || "".equals(goods.get("sku").toString()))?null:goods.get("sku").toString());
		product.setType((goods.get("type")==null || "".equals(goods.get("type").toString()))?null:goods.get("type").toString());
		
		//颜色去手机参数中的数值...去掉重复选项
		if(null != goods.get("params") && !"[]".equals(goods.get("params"))){
			net.sf.json.JSONArray params = net.sf.json.JSONArray.fromObject( goods.get("params"));
			JSONObject json = params.getJSONObject(0);
			net.sf.json.JSONArray paramList =json.getJSONArray("paramList");
			if(paramList.size()>0){
				for (int j = 0; j < paramList.size(); j++) {
					JSONObject jo = (JSONObject) paramList.get(j);
					if("color".equals(jo.get("ename"))){
						product.setColor(jo.get("value").toString());
						break;
					}
				}
			}
		}
		//生成会员价list
		if(lvIds!=null && lvIds.length>0){  //lzf add line 20110114
			List<GoodsLvPrice> goodsLvPrices = this.createGoodsLvPrices(lvPrices, lvIds, goodsId, product.getProduct_id());
			product.setGoodsLvPrices(goodsLvPrices);
		}
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		this.productManager.add(productList,goodsId);
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
	public void updateGoodsCache(String goodsIds){
		ICacheUtil util = (ICacheUtil)SpringContextHolder.getBean("cacheUtil");
		String url1 = util.getConfigInfo("REFRESH_GOODS_CACHE_1.0");
		String url2 = util.getConfigInfo("REFRESH_GOODS_CACHE_2.0");
		String json = "{\"reqId\": \"sp-valid\"," +
				"\"reqType\": \"sp_refresh_goods_cache\"," +
				"\"serial_no\": \"999999999\"," +
				"\"time\": \""+new Date()+"\"," +
				"\"source_system\": \"10011\"," +
				"\"receive_system\": \"10008\"," +
				"\"goodsIds\": \""+goodsIds+"\"}";
		
		String url = null;
		String msg = null;
		if(!StringUtil.isEmpty(url1)){
			url = url1+"?reqId=sp-valid&reqType=sp_refresh_goods_cache";
			JSONObject jsonObject = JSONObject.fromObject(json);
			logger.info(jsonObject.toString());
			msg = this.postHttpReq(jsonObject.toString(), url);
			logger.info("返回信息 : "+msg+"===============================");
		}
		if(!StringUtil.isEmpty(url2)){
			url = url2+"?reqId=sp-valid&reqType=sp_refresh_goods_cache";
			JSONObject jsonObject = JSONObject.fromObject(json);
			logger.info(jsonObject.toString());
			msg = this.postHttpReq(jsonObject.toString(), url);
			logger.info("返回信息 : "+msg+"===============================");
		}
	}
	
	private String postHttpReq(String json, String url) {
		HttpClient httpClient = new HttpClient();

		EntityEnclosingMethod postMethod = new PostMethod();
		try {
			byte b[] = json.getBytes("utf-8");
			RequestEntity requestEntity = new ByteArrayRequestEntity(b);
			postMethod.setRequestEntity(requestEntity);// 设置数据
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 把字符串转换为二进制数据

		postMethod.setPath(url);// 设置服务的url
		postMethod.setRequestHeader("Content-Type", "text/html;charset=utf-8");// 设置请求头编码

		// 设置连接超时
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5 * 1000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(200 * 1000);

		String responseMsg = "";
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);// 发送请求
			responseMsg = postMethod.getResponseBodyAsString();// 获取返回值
			logger.info("--------------------------------");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();// 释放连接
		}

		if (statusCode != HttpStatus.SC_OK) {
			logger.info("CCCCCCCCCCCCCCCCCCCC================================HTTP服务异常"+ statusCode);
		}
		return responseMsg;
	}
}
