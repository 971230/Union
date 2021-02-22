package com.ztesoft.net.mall.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.adminuser.req.ZbAdminUserGetReq;
import params.adminuser.resp.ZbAdminUserGetResp;
import params.req.PartnerByIdReq;
import params.resp.PartnerByIdResp;
import params.suppler.req.SupplierObjReq;
import params.suppler.resp.SupplierObjResp;
import services.SupplierInf;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.params.GoodsExtDataECS;
import com.ztesoft.net.mall.core.params.GoodsPlatformInfo;
import com.ztesoft.net.mall.core.plugin.goods.GoodsPluginBundleN;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.service.ISpecManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;

public class GoodsManagerN extends BaseSupport implements IGoodsManagerN {
	@Resource
	private GoodsPluginBundleN goodsPluginBundleN;
	private SupplierInf supplierServ;
	@Resource
	private ICacheUtil cacheUtil;
	@Resource
	private ISpecManager specManager;
	
	private void init(){
		if(null == supplierServ) supplierServ = ApiContextHolder.getBean("supplierServ");
	}
	
	@Override
	public void add(Goods goods,GoodsRules goodsRules,GoodsExtData goodsExtData) throws Exception {
		//商品编码SKU校验
		checkSave(goods);
		//设置商品默认值等信息
		String goods_id = setGoodsData(goods);
		String cat_id = goods.getCat_id();
		goods.setHave_spec(goodsExtData.getHaveSpec());
		goodsExtData.setGoods_id(goods_id);
		goodsExtData.setType(goods.getType());
		//Bean对象转换成HashMap
		Map goodsMap = ReflectionUtil.po2Map(goods);
		goodsMap.put("create_time", DBTUtil.current());
		//商品生效日期和实效日期处理
		if(StringUtils.isEmpty((String)goodsMap.get("fail_date")))
			goodsMap.remove("fail_date");
		if(StringUtils.isEmpty((String)goodsMap.get("effect_date")))
			goodsMap.remove("effect_date");
		//触发商品添加前事件：遍历插件装，在插件装中处理各自数据
		goodsPluginBundleN.onBeforeAdd(goodsMap,goodsExtData);
		//保存商品
		addGoods(goodsMap);
		addCatGoodsNum(cat_id);
		//保存商品价格公开权限
		savePricePriv(goods_id, goodsExtData.getLvidArray());
		//保存商品规则信息，定时任务处理，如需添加商品时处理，在此处添加处理逻辑
		//触发商品添加后事件
		goodsPluginBundleN.onAfterAdd(goodsMap,goodsExtData);
		//缓存商品信息
		asyCacheGoods(goods);
		//福建售后商品处理
		doAfterService(goodsMap,goods_id);
	}
	
	public void addCatGoodsNum(String cat_id){
		if(!StringUtils.isEmpty(cat_id)){
			this.baseDaoSupport.execute(SF.goodsSql("CAT_GOODS_NUM_UPDATE"), cat_id);
		}
		
	}
	
	public void updateCatGoodsNum(String n_cat_id, String o_cat_id){
		if(!StringUtils.isEmpty(n_cat_id)){
			this.baseDaoSupport.execute(SF.goodsSql("CAT_GOODS_NUM_UPDATE"), n_cat_id);
		}
		if(!StringUtils.isEmpty(o_cat_id)){
			this.baseDaoSupport.execute(SF.goodsSql("CAT_GOODS_NUM_UPDATE_MINUS"), o_cat_id);
		}
	}
	
	private void addGoods(Map goodsMap){
		this.baseDaoSupport.insert("goods", goodsMap);
	}
	
	@Override
	public void addGoods(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception{
		String have_spec = goodsExtData.getHaveSpec();
		String[] specValues = goodsExtData.getSpecValues();
		String[] sns = goodsExtData.getSns();//没有传货品SN，先创建一个数组
		String[] spec_productids = goodsExtData.getSpec_productids();
		String[] weights = goodsExtData.getWeights();
		String[] specids = goodsExtData.getSpecids();
		String[] specvids = goodsExtData.getSpecvids();
		String[] stores = goodsExtData.getStores();
		caculateStore(goods, stores); //开启规格，每个规格商品的库存相加得出商品库存
		//如果有商品规格值，但是商品规格ID【specids】和规格值ID【specvids】都为空，则根据规格值查询，把规格ID和规格值ID放入数组
		//如果根据传入的规格值没有查询到数据，则新增规格，并把新增的规格ID和规格值ID放入数组
		if(!"1".equals(have_spec) && (specValues!=null && specValues.length>0)
				&& (specids==null || specids.length==0)
				&& (specvids==null || specvids.length==0)){
			if(sns == null || sns.length==0){
				sns = new String[specValues.length];
			}
			if(spec_productids == null || spec_productids.length == 0){
				spec_productids = new String[specValues.length];
			}
			if(weights == null || weights.length ==0){
				weights = new String[specValues.length];
			}
			specids = new String[specValues.length];
			specvids = new String[specValues.length];
			goods.setHave_spec("1");//有规格值，设置是否开启规格为开启
			goodsExtData.setHaveSpec("1");
			for(int i=0;i<specValues.length;i++){
				String value = specValues[i];
				List<SpecValue> valueList = this.baseDaoSupport.queryForList(SF.goodsSql("SPEC_VALUE_GET"), SpecValue.class,value);
				if(valueList !=null && valueList.size()>0){
					SpecValue sv = valueList.get(0);
					specids[i] = sv.getSpec_id();
					specvids[i] = sv.getSpec_value_id();
				}
				else{
					String image = null;//规格关联图片设置为null
					Map<String, String> rtnMap = addSpec(value, image);
					specids[i] = rtnMap.get("spec_id");
					specvids[i] = rtnMap.get("spec_value_id");
				}
				if(StringUtils.isEmpty(sns[i])){
					sns[i] = "";//赋空值，用于处理规格GoodsSpecPluginN
				}
				if(StringUtils.isEmpty(spec_productids[i])){
					spec_productids[i] = "";//赋一个空值，用于处理规格GoodsSpecPluginN
				}
				if(StringUtils.isEmpty(weights[i])){
					weights[i] = "0";
				}
			}
			goodsExtData.setSpecids(specids);
			goodsExtData.setSpecvids(specvids);
			goodsExtData.setSns(sns);
			goodsExtData.setSpec_productids(spec_productids);
			goodsExtData.setWeights(weights);
			goods.setMktprice(goods.getPrice());
			goods.setBrand_id("20001");
			goods.setMarket_enable(1);//默认上架
		}
		else if(StringUtils.isEmpty(have_spec)){
			goods.setHave_spec("0");//设置是否开启规格为关闭
			goodsExtData.setHaveSpec("0");
			goods.setMarket_enable(1);//默认上架
		}
		if(StringUtils.isEmpty(goods.getSimple_name())){
			goods.setSimple_name(goods.getName());
		}
		if(StringUtils.isEmpty(goods.getType_id())){
			goods.setType_id("30001");//商品类型为空，设置一个默认商品类型
		}
		this.add(goods, goodsRules, goodsExtData);
	}
	
	@Override
	public void editGoods(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception{
		String goods_id = goods.getGoods_id();
		String have_spec = goodsExtData.getHaveSpec();
		String[] specValues = goodsExtData.getSpecValues();
		String[] sns = goodsExtData.getSns();//没有传货品SN，先创建一个数组
		String[] spec_productids = goodsExtData.getSpec_productids();
		String[] weights = goodsExtData.getWeights();
		String[] specids = goodsExtData.getSpecids();
		String[] specvids = goodsExtData.getSpecvids();
		String[] stores = goodsExtData.getStores();
		if(StringUtils.isEmpty(goods.getSn())){//针对商品SN为空的处理：如果商品SN为空，根据goods_id从数据库查询SN
			Goods editGoods = (Goods) this.baseDaoSupport.queryForObject(SF.goodsSql("GOODS_SELECT_BY_ID"), Goods.class, goods_id);
			goods.setSn(editGoods.getSn());
			if(StringUtils.isEmpty(editGoods.getCat_id()) && !StringUtils.isEmpty(goods.getCat_id())){
				addCatGoodsNum(goods.getCat_id());
			}
			else if(!StringUtils.isEmpty(editGoods.getCat_id()) && !StringUtils.isEmpty(goods.getCat_id()) && !editGoods.getCat_id().equals(goods.getCat_id())){
				updateCatGoodsNum(goods.getCat_id(),editGoods.getCat_id());
			}
		}
		caculateStore(goods, stores); //开启规格，每个规格商品的库存相加得出商品库存
		//如果有商品规格值，但是传入的商品规格ID【specids】和规格值ID【specvids】都为空，则根据规格值查询，把规格ID和规格值ID放入数组
		//如果根据传入的规格值没有查询到数据，则新增规格，并把新增的规格ID和规格值ID放入数组
		//河北O2O，对其他无影响
		if(StringUtils.isEmpty(have_spec) && (specValues!=null && specValues.length>0)
				&& (specids==null || specids.length==0)
				&& (specvids==null || specvids.length==0)){
			if(sns == null || sns.length==0){
				sns = new String[specValues.length];
			}
			//新增商品【开启规格】，spec_productids数组不能为null或者数组长度为0，必须一个货品传一个空串
			//这里创建一个长度与货品数量相等的货品ID数组，并在后面赋一个空值
			if(spec_productids == null || spec_productids.length == 0){
				spec_productids = new String[specValues.length];
			}
			if(weights == null || weights.length ==0){
				weights = new String[specValues.length];
			}
			specids = new String[specValues.length];
			specvids = new String[specValues.length];
			goods.setHave_spec("1");//设置是否开启规格为开启
			goodsExtData.setHaveSpec("1");
			for(int i=0;i<specValues.length;i++){
				String value = specValues[i];
				List<SpecValue> valueList = this.baseDaoSupport.queryForList(SF.goodsSql("SPEC_VALUE_GET"), SpecValue.class,value);
				if(valueList !=null && valueList.size()>0){
					SpecValue sv = valueList.get(0);
					specids[i] = sv.getSpec_id();
					specvids[i] = sv.getSpec_value_id();
				}
				else{
					String image = null;
					Map<String, String> rtnMap = addSpec(value, image);
					specids[i] = rtnMap.get("spec_id");
					specvids[i] = rtnMap.get("spec_value_id");
				}
				sns[i] = "";//赋空值，用于处理规格GoodsSpecPluginN
				spec_productids[i] = "";//赋一个空值，用于处理规格GoodsSpecPluginN
				weights[i] = "0";
			}
			goodsExtData.setSpecids(specids);
			goodsExtData.setSpecvids(specvids);
			goodsExtData.setSns(sns);
			goodsExtData.setSpec_productids(spec_productids);
			goodsExtData.setWeights(weights);
			goods.setMktprice(goods.getPrice());
			goods.setBrand_id("20001");
			goods.setMarket_enable(1);//默认上架
		}
		else{
			goods.setHave_spec("0");//设置是否开启规格为关闭
			goodsExtData.setHaveSpec("0");
			goods.setMarket_enable(1);//默认上架
		}
		if(StringUtils.isEmpty(goods.getSimple_name())){
			goods.setSimple_name(goods.getName());
		}
		if(StringUtils.isEmpty(goods.getType_id())){
			goods.setType_id("30001");//商品类型为空，设置一个默认商品类型
		}
		this.edit(goods, goodsRules, goodsExtData);
	}
	
	public void caculateStore(Goods goods, String[] stores){
		int total = 0;
		if(stores!=null && stores.length>0){
			for(int i=0;i<stores.length;i++){
				int store = 0;
				try{
					store = Integer.valueOf(stores[i]);
				}catch(Exception e){
					e.printStackTrace();
					store = 0;
				}
				total += store;
			}
			goods.setStore(total);
		}
	}
	
	/**
	 * 添加规格
	 * @param value 规格值
	 * @param imageArray 规格图片
	 */
	public Map<String, String> addSpec(String value, String image){
		Specification spec = new Specification();
		String spec_id = this.baseDaoSupport.getSequences("s_es_specification");
		spec.setSpec_id(spec_id);
		spec.setSpec_name(value);
		spec.setSpec_memo(value);
		spec.setSpec_type(0);//规格类型默认为文字
		
		List<SpecValue> valueList = new ArrayList<SpecValue>();
		SpecValue specValue = new SpecValue();
		String spec_value_id = this.baseDaoSupport.getSequences("s_es_spec_values");
		specValue.setSpec_value_id(spec_value_id);
		specValue.setSpec_value(value);
		if(image == null || image.equals("")) 
			image = "../shop/admin/spec/image/spec_def.gif";
		else
			image = UploadUtil.replacePath(image);
		specValue.setSpec_image(image);
		valueList.add(specValue);
		this.specManager.add(spec, valueList);
		
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("spec_id", spec_id);
		rtnMap.put("spec_value_id", spec_value_id);
		return rtnMap;
	}
	
	/**
	 * 添加产品包信息-联通ECS
	 * @param goodsPackage
	 * @param goods_id 商品ID
	 * @param type 类型：goods-商品，product-货品
	 * @param act_code  界面输入合约编码p_code
	 * @param prod_code  界面输入SN
	 * @param sns 货品SN数组
	 * @param typeids 货品类型ID数组
	 * @param z_goods_ids 货品goods_id数组
	 */
	@Override
	public void addGoodsPackage(GoodsExtDataECS goodsExtData){
		String contract_goods_id = null;//合约计划
		String offer_goods_id = null;
		String sn = null;
		GoodsPackage goodsPackage = new GoodsPackage();
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) && 
				Consts.ECS_QUERY_TYPE_GOOD.equals(goodsExtData.getType())) {
			//页面手工填写了P_CODE或SN
			if (StringUtils.isNotEmpty(goodsExtData.getAct_code()) || StringUtils.isNotEmpty(goodsExtData.getProd_code())) {
				if (this.goodsPackageExists(goodsExtData.getGoods_id(), goodsExtData.getAct_code(), goodsExtData.getProd_code())) {
					CommonTools.addFailError("商品映射关系已经存在【es_goods_package】!");
				}
				goodsPackage = new GoodsPackage();
				goodsPackage.setGoods_id(goodsExtData.getGoods_id());
				goodsPackage.setP_code(goodsExtData.getAct_code());
				goodsPackage.setSn(goodsExtData.getProd_code());
				goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
			} else {
				String[] typeids = goodsExtData.getTypeids();
				String[] sns = goodsExtData.getEcs_sns();
				String[] z_goods_ids = goodsExtData.getZ_goods_ids();
				if(typeids!=null && typeids.length>0){
					for(int i=0;i<typeids.length;i++){
						String typeid = typeids[i];
						//如果货品类型是终端、成品卡、配件或上网卡，取该货品的SN作为es_goods_package的SN字段的取值
						if((Consts.GOODS_TYPE_TERMINAL.equals(typeid) 
								|| Consts.PRODUCT_TYPE_FIN_CARD.equals(typeid)
								|| Consts.PRODUCT_TYPE_ADJUNCT.equals(typeid)
								|| Consts.PRODUCT_TYPE_INTERNET.equals(typeid))
								&& sns[i]!=null && !"".equals(sns[i])){
							sn = sns[i];
							goodsPackage.setSn(sn);
						}
						//取合约计划goods_id
						if(Consts.GOODS_TYPE_CONTRACT.equals(typeid))
							contract_goods_id = z_goods_ids[i];
						//取套餐goods_id
						if(Consts.GOODS_TYPE_OFFER.equals(typeid))
							offer_goods_id = z_goods_ids[i];
					}
				}
				//合约、套餐都不为空时，取合约计划和套餐的关系编码作为p_code的取值
				if(StringUtils.isEmpty(goodsPackage.getP_code()) && StringUtils.isNotEmpty(contract_goods_id) && StringUtils.isNotEmpty(offer_goods_id)){
					String sql = SF.goodsSql("P_CODE");
					List<Map> list = this.baseDaoSupport.queryForList(sql, contract_goods_id,offer_goods_id);
					if(list!=null && list.size()>0){
						Map codeMap = list.get(0);
						goodsPackage.setP_code(codeMap.get("rel_code").toString());
					}
				}
				
				if(StringUtils.isEmpty(goodsPackage.getSn())){
					String snString = this.baseDaoSupport.getSequences("S_ES_PRODUCT_SN", "0", 8);
					goodsPackage.setSn(snString);
				}
				goodsPackage.setGoods_id(goodsExtData.getGoods_id());
				goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
			}
			this.baseDaoSupport.insert("es_goods_package", goodsPackage);
		}
	}
	
	private String setGoodsData(Goods goods){
		//初始化beans
		init();
		
		GoodsNetCacheRead read = new GoodsNetCacheRead();
		String staff_no = goods.getStaff_no();
		//设置供货商信息,防止工号被删除，商品归属供货商信息丢失
		if(!StringUtil.isEmpty(staff_no)){
			SupplierObjReq supplierObjReq = new SupplierObjReq();
			supplierObjReq.setSupplier_id(staff_no);
			
			SupplierObjResp supplierObjResp = new SupplierObjResp();
			supplierObjResp = supplierServ.findSupplierById(supplierObjReq);
			
			Supplier supper = new Supplier();
			if(supplierObjResp != null){
				supper = supplierObjResp.getSupplier();
			}
			if(supper !=null)
				goods.setSupper_id(supper.getSupplier_id());
		}
		if(goods.getType_id()!=null){
			GoodsType goodsType = read.getCachesGoodsType(goods.getType_id());
			if(goodsType == null || goodsType.getType_id() == null){
				goodsType = read.getCachesProductType(goods.getType_id());
			}
			goods.setType_code(goodsType.getType_code());
		}
		if(goods.getCat_id()!=null){
			Cat cat = read.getCacheCat(goods.getCat_id());
			String cat_name = cat==null?"":cat.getName();
			if(goods.getSearch_key()!=null){
				goods.setSearch_key(goods.getSearch_key()+cat_name+"_");
			}else{
				goods.setSearch_key("_"+cat_name+"_");
			}
		}
		String goods_id = this.baseDaoSupport.getSequences("S_ES_GOODS");
		goods.setGoods_id(goods_id);
		goods.setType("goods");
		String sku = this.createSKU(goods.getType(), goods.getCat_id());
		goods.setSku(sku);
		goods.setCat_id(goods.getCat_id()!=null?goods.getCat_id().split(",")[0]:null);
		goods.setCreate_time(DBTUtil.getDBCurrentTime());
		goods.setGoods_type("normal");
		goods.setLast_modify(DBTUtil.current());
		goods.setDisabled(0);
		goods.setBuy_count(0);
		goods.setView_count(0);
		goods.setCost(0D);
		
		return goods_id;
	}
	
	private void checkCount(String sql, String value, String notar)
			throws Exception {
		int count = 0;
		if(StringUtils.isNotEmpty(value))
			count = baseDaoSupport.queryForInt(sql, value);
		if (count > 0)
			throw new Exception(notar);
	}
	
	/**
	 * SKU校验
	 * @param goods
	 * @throws Exception
	 */
	private void checkSave(Goods goods) throws Exception {
		String sql = SF.goodsSql("GOODS_CHECK_SAVE");
		if("goods".equals(goods.getType())){
			checkCount(sql,goods.getSku(), "商品编码重复,请重新输入");
		}else{
			checkCount(sql,goods.getSku(), "SKU重复,请重新输入");
		}
	}
	
	/**
	 * 默认生成商品货品SKU
	 * @param type 
	 * @return
	 */
	public String createSKU(String type, String cat_id) {
		String sku = "";
		if (Consts.ECS_QUERY_TYPE_GOOD.equals(type)) {
			//商品sku：59开头+商品细类编码+日期（8位）+（6位序列）
			sku = "59"+ cat_id 
					+ DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT_8) 
					+ this.baseDaoSupport.getSequences("S_ES_GOODS_SKU", "0", 6);
		} else {
			//货品sku：58开头+货品细类编码+日期（8位）+（6位序列）
			sku = "58"+ cat_id 
					+ DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT_8) 
					+ this.baseDaoSupport.getSequences("S_ES_PRODUCT_SKU", "0", 6);
		}
		return sku;
	}
	
	/**
	 * 保存价格公开权限数据
	 * @param goods_id 商品ID
	 * @param lvidArray 会员等级ID
	 * @return
	 */
	private boolean savePricePriv(String goods_id,String[] lvidArray){
		//删除商品原有价格公开权限
		deletePricePriv(goods_id);
		
		String source_from = ManagerUtils.getSourceFrom();
		List<Map> membersLvs = this.baseDaoSupport.queryForList(SF.goodsSql("MEMBER_LV_SELECT"), source_from) ;
		Map<String,String> membersLvMap = new HashMap<String,String>() ;
		if(membersLvs != null && !membersLvs.isEmpty()){
			for(Map lv : membersLvs ){
				membersLvMap.put((String)lv.get("lv_id"), "00X") ;
			}	
		}
		if(lvidArray != null && lvidArray.length > 0){
			for(String lv : lvidArray){
				if(membersLvMap.containsKey(lv))
					membersLvMap.put(lv, "00A") ;
			}
		}
		Map<String,String> data = null ;
		for(Iterator<String> it = membersLvMap.keySet().iterator() ; it.hasNext() ;){
			String role_type = it.next() ;
			String state = membersLvMap.get(role_type) ;
			data = new HashMap<String,String>() ;
			data.put("role_type", role_type) ;
			data.put("state", state) ;
			data.put("goods_id", goods_id) ;
			this.baseDaoSupport.insert("es_price_priv", data);
		}
		return true ;
		
	}
	
	/**
	 * 删除商品价格公开权限
	 * @param goods_id
	 */
	private void deletePricePriv(String goods_id){
		this.baseDaoSupport.execute(SF.goodsSql("PRICE_PRIV_DEL_BY_GOODS_ID") ,goods_id );
	}
	
	/**
	 * 判断映射关系是否已经存在
	 * @param goods_id
	 * @param p_code
	 * @param sn
	 * @return
	 */
	private boolean goodsPackageExists(String goods_id, String p_code, String sn) {
		String sql = SF.goodsSql("COUNT_GOODS_PACKAGE");
		
		List params = new ArrayList();
		params.add(goods_id);
		if (StringUtils.isNotEmpty(p_code)) {
			sql += " and p_code = ?";
			params.add(p_code);	
		}
		
		if (StringUtils.isNotEmpty(sn)) {
			sql += " and sn = ?";
			params.add(sn);	
		}
		
		int cnt = this.baseDaoSupport.queryForInt(sql, params.toArray());
		if (cnt > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 缓存商品货品信息
	 * @param goods
	 */
	private void asyCacheGoods(final Goods goods){
		new Thread() {                                         
            @Override
			public void run() { 
            	GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
        		goodsRefreshDTO.setGoods_ids("'"+goods.getGoods_id()+"'");
        		initGoodsCacheByCondition(goodsRefreshDTO);
            }                                                  
        }.start();
		
	}
	
	public List<Goods> listGoodsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_GOODS");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotEmpty(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotEmpty(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotEmpty(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotEmpty(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotEmpty(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.baseDaoSupport.queryForList(sql+newCondition.toString(), Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	public List<Goods> initGoodsCacheByCondition(GoodsRefreshDTO goodsRefreshDTO){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
		//配置的不匹配直接选用文件配置的,add by wui
		if(!(planDBSourceFroms.indexOf(EopSetting.SOURCE_FROM)>-1))
			planDBSourceFroms = EopSetting.SOURCE_FROM;
		if(StringUtil.isEmpty(planDBSourceFroms))
			planDBSourceFroms = ManagerUtils.getSourceFrom();
		if("ECSORD".equals(planDBSourceFroms))
			planDBSourceFroms ="ECS";
		GoodsNetCacheWrite write = new GoodsNetCacheWrite();
		Class clazz = write.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		try {
			//先 执行loadAllGoodsByCondition方法获取增量goods
			List<Goods> goodsList = null; 
//			for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
//				ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
//				goodsList = (List<Goods>) clazz.getDeclaredMethod("loadAllGoodsByCondition",GoodsRefreshDTO.class).invoke(write, goodsRefreshDTO);
				goodsList = this.listGoodsByCondition(goodsRefreshDTO); //add by wui
//			}
			if(null == goodsList || goodsList.isEmpty()) return null;
			for (Method method : methods) {
				if (	"loadAllGoodsByCondition".equals(method.getName()) //主表 es_goods 已新增条件        可刷
						||"loadGoodsRelProductsByCondition".equals(method.getName()) //主表 es_goods 已经新增条件  可刷
						|| "loadAllProductsByCondition".equals(method.getName()) // 主表: es_product p 已经新增条件 是否新增条件:product_id?   可刷
						|| "loadAllGoodsTerminalByCondition".equals(method.getName()) //主表:es_goods  已经新增条件  可刷
						|| "loadAllGoodsTCByCondition".equals(method.getName()) // 主表:es_goods 已经新增条件 可刷
						|| "loadAllGoodsContractByCondition".equals(method.getName())// 主表:es_goods  已经新增条件 可刷
						|| "loadAllGoodsPromotion".equals(method.getName())// 主表:es_coupons 主表数据量较小,暂没加条件(不包含goods)
						|| "loadAllGoodsTags".equals(method.getName())//主表:tags 300多条数据,暂没加条件 不包含goods)
						|| "loadAllGoodsRelTagsByConditionAndGoodsInfo".equals(method.getName()) // 主表:tags  300多条数据标签数据 缓存标签关联商品信息(根据标签查询商品)  是否添加条件 tagsID? 包含goods 处理完成 可刷
						//|| "loadGoodsComplexByCondition".equals(method.getName()) //主表:es_goods g  已经新增条件 缓存商品绑定关联商品 完成 , 统计数量,不可刷
						|| "loadGoodsAdjunctByCondition".equals(method.getName())//主表 es_goods g 已经新增条件 ?
						|| "loadCatComplexByConditionAndGoodsInfo".equals(method.getName())//主表:es_cat_complex 10多条数据 分类推荐商品缓存  是否新增条件:cat_id  包含goods 处理完成 可刷
						|| "loadGoodsType".equals(method.getName())//主表: es_goods_type 30多条数据  商品类型 是否新增条件:type_id   不包含goods
						|| "loadBrand".equals(method.getName())// 主表:es_brand  80多条数据 是否新增条件:brand_id		不包含goods
						|| "loadGoodsCatByLvId".equals(method.getName())//主表:es_member_lv 十条数据内(电信采集,经销商) 是否新增条件lv_id  商品分类缓存   不包含goods
						|| "loadGoodsByCatLvIByConditionAndGoodsInfo".equals(method.getName())//主表:es_goods_cat  100多条数据     缓存分类商品  是否新增条件:cat_id   包含goods  处理完成 可刷
						|| "loadAllGoodsByServByConditionAndGoodsInfo".equals(method.getName()) // 主表:es_goods_stype 缓存所有服务对应的商品  是否新增:STYPE_ID 疑问 包含goods .在最后sql上 处理  可刷
						|| "loadAllTypes".equals(method.getName()) //主表:es_goods_type  30多条数据  缓存所有服务对应的商品 是否新增条件 :type_id  不包含goods
						|| "loadBrandByTypeId".equals(method.getName())//主表:es_goods_type 300多条数据  缓存类型的品牌信息 是否新增条件:type_id  不包含goods
						|| "loadBrandModelByBrandId".equals(method.getName())//主表:es_brand  180多条数据 是否新增条件brand_id 不包含goods
						|| "loadGoodsCatByTypeId".equals(method.getName())//主表:es_goods_type 100多条数据  不包含goods
						|| "loadAllGoodsBySkuByCondition".equals(method.getName())//主表 es_goods c  已添加条件 可刷
						|| "loadAllVproductsByNameByCondition".equals(method.getName())) {  //主表(视图):v_product 视图新增字段:create_time
					
					for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
						ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
						if(method.getName().endsWith("ByCondition")){
							Method m = clazz.getDeclaredMethod(method.getName(),GoodsRefreshDTO.class);
							m.invoke(write, goodsRefreshDTO);
						}else if (method.getName().endsWith("ByConditionAndGoodsInfo")){
							Method m = clazz.getDeclaredMethod(method.getName(),List.class,GoodsRefreshDTO.class);
							m.invoke(write,goodsList,goodsRefreshDTO);
						}else{
							Method m = clazz.getDeclaredMethod(method.getName());
							m.invoke(write);
						}
					}
				}
			}
			return goodsList;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally{
			ManagerUtils.CACHE_REFRESH_SOURCE_FROM ="";
		}
		return null;
	}
	
	/**
	 * 是否为售后商品类型-福建
	 * @param goodsMap
	 * @param goods_id
	 */
	private void doAfterService(Map goodsMap,String goods_id){
		if("FJ".equals(ManagerUtils.getSourceFrom())&&"201412178804000355".equals(goodsMap.get("type_id").toString())){
			editGoods(goodsMap.get("params").toString(),goods_id);
		}
	}
	
	/**
	 * 更新goods表的params参数
	 * @param detail
	 * @param goodsId
	 */
    public void editGoods(String detail,String goodsId){
    	String sql="update es_goods set params=? where goods_id=? ";
    	if (detail != null){
    		this.daoSupport.execute(sql, new String[]{detail,goodsId});
    	}
    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public void edit(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception {
		goods.setLast_modify(DBTUtil.current());
		goods.setHave_spec(goodsExtData.getHaveSpec());
		Map goodsMap = ReflectionUtil.po2Map(goods);
		if(StringUtils.isEmpty((String)goodsMap.get("fail_date")))
			goodsMap.remove("fail_date");
		if(StringUtils.isEmpty((String)goodsMap.get("effect_date")))
			goodsMap.remove("effect_date");
		this.goodsPluginBundleN.onBeforeEdit(goodsMap, goodsExtData);
		this.baseDaoSupport.update("goods", goodsMap, "goods_id='"+ goods.getGoods_id()+"'");
		this.savePricePriv(goods.getGoods_id(), goodsExtData.getLvidArray());
		this.goodsPluginBundleN.onAfterEdit(goodsMap, goodsExtData);
		
		asyCacheGoods(goods);
		doAfterService(goodsMap,goods.getGoods_id());
	}
	
	/**
	 * 修改时获取商品数据
	 * 
	 * @param goods_id
	 * @return
	 */
	@Override
	public GoodsEditDTO getGoodsEditData(String goods_id){
		GoodsEditDTO editDTO = new GoodsEditDTO();
		String sql = SF.goodsSql("GET_GOODS_EDITDATA");
		List<Map> goodsList = this.baseDaoSupport.queryForList(sql, goods_id);
		Map goods = goodsList.size()>0?goodsList.get(0):new HashMap();
		// 将本地存储的图片替换为静态资源服务器地址
		String image_file = (String) goods.get("image_file");
		if (!StringUtil.isEmpty(image_file)) {
			image_file = UploadUtil.replacePath(image_file);
			goods.put("image_file", image_file);
		}
		String intro = (String) goods.get("intro");
		if (intro != null) {
			intro = UploadUtil.replacePath(intro);
			goods.put("intro", intro);
		}

		sql = SF.goodsSql("GOODS_PACKAGE_QUERY");
		sql += " and goods_id = ?";
		List<Map> goodsPkgLst = this.baseDaoSupport.queryForList(sql, goods_id);
		Map goodsPkgMap = goodsPkgLst.size()>0?goodsPkgLst.get(0):new HashMap();
		goods.put("act_code", goodsPkgMap.get("p_code"));
		goods.put("prod_code", goodsPkgMap.get("sn"));
		GoodsExtData extData = new GoodsExtData();
		List<String> htmlList = goodsPluginBundleN.onFillEditInputData(goods,extData);
		editDTO.setGoods(goods);
		editDTO.setHtmlList(htmlList);

		return editDTO;
	}
	
	/**
     * 根据ID得到控制信息
     * @param goods_id
     * @return
     */
	@Override
	public List<GoodsControl> getControlById(String goods_id){
		String sql = SF.goodsSql("GET_CONTROL_BY_ID_0");
    	List<GoodsControl> goodsControlList = this.baseDaoSupport.queryForList(sql, goods_id);
    	return goodsControlList;
	}
	
	/**
	 * 填充添加前的数据
	 * 
	 */
	@Override
	public  List<String> fillAddInputData(){
		return goodsPluginBundleN.onFillAddInputData();
	}

	@Override
	public void updateField(String filedname, Object value, String goodsid) {
		ManagerUtils.resetWidgetCache();
		this.baseDaoSupport.execute("update goods set " + filedname
				+ "=? where goods_id=?", value, goodsid);
	}

	@Override
	public List getOrgResultByGoodsId(String goods_id) {
		List list=null;
		String sql = SF.goodsSql("QUEUE_SYN_STATUS_COUNT");
		try{
			list =this.baseDaoSupport.queryForList(sql, goods_id,goods_id);
		}catch(Exception e){
			
		}
		return list;
	}

	@Override
	public String getSequence(String seq) {
		return this.daoSupport.getSequences(seq);
	}

	@Override
	public void delete(String goods_id) {
		this.baseDaoSupport.execute("update goods set disabled=1 where goods_id=?", goods_id);
	}
	
	@Override
	public Page searchGoods(Map<String, String> params, int pageNo, int pageSize){
		String name = Const.getStrValue(params, "name");
		String sn = Const.getStrValue(params, "sn");
		String market_enable = Const.getStrValue(params, "market_enable");
		String catid = Const.getStrValue(params, "catid");
		String supplier_id = Const.getStrValue(params, "supplier_id");
		String auditState = Const.getStrValue(params, "audit_state");
		String sql = "";
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		@SuppressWarnings("unused")
		int founder = adminUser.getFounder();
		
		//if (ManagerUtils.isProvStaff()) {// 管理员或电信员工
		if(Consts.CURR_FOUNDER_0.equals(founder+"")|| Consts.CURR_FOUNDER_1.equals(founder+"")){
			sql = SF.goodsSql("SEARCH_GOODS_0_1");
			
		}else if(Consts.CURR_FOUNDER6==founder){
            sql = SF.goodsSql("SEARCH_GOODS_6") + " where 1=1 and g.staff_no='"+adminUser.getUserid()+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "' " ;

        }else { //供货商等
			sql = SF.goodsSql("SEARCH_GOODS_OTHER") +
				   "and (g.staff_no='"+adminUser.getParuserid()+"' or g.staff_no='"+adminUser.getUserid()+"') and g.source_from='"+ManagerUtils.getSourceFrom()+"'" ;// add by wui 父级管理员
		}
		if (name != null && !name.equals("")) {
			sql += "  and upper(g.name) like '%" + name.trim().toUpperCase() + "%'";
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id = '" + catid.trim() + "'";
		}
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if (!StringUtils.isEmpty(market_enable) && !"2".equals(market_enable)) {
			sql += "  and g.market_enable = " + market_enable;
		}
		StringBuffer statsWhereCond =  new StringBuffer();
		//以下0代表待审核，1审核通过，2审核不通过,3下架
		if(auditState!=null&&!auditState.equals("")&&!auditState.isEmpty()){
			if("wait_audit".equals(auditState))
			{
				statsWhereCond.append(" and not exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1,2,3))");
			}else if("audit_y".equals(auditState))
			{
				statsWhereCond.append(" and not exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (0,2,3))");
			}else if("audit_n".equals(auditState))
			{
				statsWhereCond.append(" and not exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (0,1,3))");
			}else if("audit_some".equals(auditState))
			{
				statsWhereCond.append(" and (exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1))) " +
						"and (exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (0)) and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1))) " +
						"or (exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1)) and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (2))) " +
						"or (exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (0)) and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1)) and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (2)))" );
			}else if("some_fail".equals(auditState)){
				statsWhereCond.append(" and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (2))  and exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (0))  and not exists (select 1 from es_goods_area ea where g.source_from = ea.source_from and ea.goods_id = g.goods_id and state in (1)) ");
			}else{
				
			}
		}
		
		String countSql = "select count(*) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		sql +=statsWhereCond.toString();
		sql += " order by g.create_time desc ";
		Page webpage = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, Goods.class, countSql+statsWhereCond.toString());
		return webpage;
	}

	@Override
	public void addGoodsPlatFormInfo(GoodsPlatformInfo goodsPlatformInfo) {
		// TODO Auto-generated method stub
		goodsPlatformInfo.setCreate_time(Consts.SYSDATE);
		this.baseDaoSupport.insert("es_goods_plantform_info", goodsPlatformInfo);
	}

	@Override
	public GoodsPlatformInfo getGoodsPlatFormInfo(String goods_id) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String sql ="select * from es_goods_plantform_info where goods_id =?";
		GoodsPlatformInfo goodsPlatformInfo = new GoodsPlatformInfo();
		list =  this.baseDaoSupport.queryForList(sql,GoodsPlatformInfo.class,goods_id);
		if(list!=null&&list.size()>0){
			goodsPlatformInfo = (GoodsPlatformInfo)list.get(0);
            String  obj = goodsPlatformInfo.getOther_intors();
            IStoreProcesser  netBlog  = null;
            if(null != obj && !"".equals(obj)){
            	netBlog = StoreProcesser.getProcesser("es_goods_plantform_info","other_intors",goodsPlatformInfo.getSource_from(),obj);
            	obj = netBlog.getFileUrl(obj);
            }
			goodsPlatformInfo.setOther_intors(obj);
		}else{
			goodsPlatformInfo = null;
		}
		return goodsPlatformInfo;
	}

	@Override
	public void delGoodsPlatformInfo(String goods_id) {
		// TODO Auto-generated method stub
		String sql = "delete from es_goods_plantform_info where goods_id =?";
		this.baseDaoSupport.execute(sql, goods_id);
	}

	@Override
	public String getAcceptPrice(String goods_id, String product_id,
			String lv_id) {
		String price = null;
		String sql = SF.goodsSql("ACCEPT_PRICE_GET");
		price = this.baseDaoSupport.queryForString(sql, goods_id,product_id,lv_id);
		return price;
	}

	@Override
	public void addProxyGoods(Map<String, String> proxy) {
		this.baseDaoSupport.insert("es_goods_proxy", proxy);
	}

	@Override
	public void deleteProxyGoods(Map<String, String> proxy) {
		String userid = proxy.get("userid");
		String p_goods_id = proxy.get("p_goods_id");
		this.baseDaoSupport.execute(SF.goodsSql("PROXY_GOODS_DELETE"), userid, p_goods_id);
	}

	@Override
	public void copyGoods(String goods_id, String user_id, String founder, String parent_user_id) {
		String userId = null;
		Goods goods = (Goods) this.baseDaoSupport.queryForObject(SF.goodsSql("GOODS_SELECT_BY_ID"), Goods.class, goods_id);
		String old_goods_id = goods.getGoods_id();//原goods_id
		String old_staff_no = goods.getStaff_no();//原商品创建人
		String n_goods_id = this.baseDaoSupport.getSequences("S_ES_GOODS");
		AdminUser adminUser = this.getUserInfo(user_id);
		if(goods != null){
			goods.setGoods_id(n_goods_id);
			if (Consts.CURR_FOUNDER0 == Integer.valueOf(founder) || Consts.CURR_FOUNDER1 == Integer.valueOf(founder)) {
				userId = "-1" ;
				goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
			}else if(Consts.CURR_FOUNDER3==Integer.valueOf(founder)){
				userId = adminUser.getUserid();
				goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
			}else{
				int value = Integer.valueOf(founder);
				if(Consts.CURR_FOUNDER4==value) //供货商
					userId = user_id;
				else if(Consts.CURR_FOUNDER5 == value) //供货商员工
					userId = parent_user_id;
				goods.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
			}
			goods.setStaff_no(userId);
			goods.setCreator_user(user_id);
			goods.setDisabled(Consts.GOODS_DISABLED_0);//正常列表中

			String goods_spec = goods.getSpecs();
			
			List<Map> products = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_SELECT")+" and g.goods_id=? ", goods_id);
			for(Map product : products){
				String n_product_id =  this.baseDaoSupport.getSequences("s_es_product");
				product.put("goods_id", n_goods_id);
				product.put("product_id", n_product_id);
				JSONArray jsonArray = JSONArray.fromObject(goods_spec);
				String old_product_id = "";
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					old_product_id = jsonObject.get("product_id").toString();
				}
				goods_spec = goods_spec.replace(old_product_id, n_product_id);
				goods_spec = goods_spec.replace(old_goods_id, n_goods_id);
				this.baseDaoSupport.insert("es_product", product);
			}
			
			List<Map> specs = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_SPEC_COPY"), goods_id);
			for(Map spec : specs){
				spec.put("goods_id", n_goods_id);
				this.baseDaoSupport.insert("es_goods_spec", spec);
			}
			
			List<Map> tagRels = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_TAG_REL_COPY"), goods_id);
			for(Map rel : tagRels){
				rel.put("rel_id", n_goods_id);
				this.baseDaoSupport.insert("es_tag_rel", rel);
			}
			
			List<Map> platforms = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PLATFORM_INFO_COPY"), goods_id);
			for(Map platform : platforms){
				platform.put("goods_id", n_goods_id);
				this.baseDaoSupport.insert("es_goods_plantform_info", platform);
			}
			
			List<Map> privs = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PRICE_PRIV_COPY"), goods_id);
			for(Map priv : privs){
				priv.put("goods_id", n_goods_id);
				this.baseDaoSupport.insert("es_price_priv", priv);
			}
			
			goods.setSpecs(goods_spec);
			Map goodsMap = ReflectionUtil.po2Map(goods);
			goodsMap.put("col1", old_goods_id);
			goodsMap.put("col2", old_staff_no);
			this.baseDaoSupport.insert("es_goods", goodsMap);
		}
		
	}

	@Override
	public AdminUser getUserInfo(String userid) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		PartnerByIdReq partnerReq = new PartnerByIdReq();
		partnerReq.setPartnerId(userid);
		PartnerByIdResp partnerResp = client.execute(partnerReq, PartnerByIdResp.class);
		Partner partner = partnerResp.getPartner();
		ZbAdminUserGetResp resp = null;
		if(partner!=null){
			ZbAdminUserGetReq req = new ZbAdminUserGetReq();
			req.setUserid(partner.getUserid());
			resp = client.execute(req, ZbAdminUserGetResp.class);
		}
		else{
			ZbAdminUserGetReq req = new ZbAdminUserGetReq();
			req.setUserid(userid);
			resp = client.execute(req, ZbAdminUserGetResp.class);
		}
		return (resp == null || resp.getAdminUser()==null)?null:resp.getAdminUser();
	}

	@Override
	public List<Goods> listRelGoods(String a_goods_id) {
		String sql = SF.goodsSql("GOODS_REL_GOODS_PAGE").replaceAll("#cond", " and rel_type in('CONTRACT_OFFER','TERMINAL_PLAN') and a_goods_id=? ");
		String source_from = ManagerUtils.getSourceFrom();
		return this.baseDaoSupport.queryForList(sql, Goods.class, a_goods_id, source_from); 
	}
	
	@Override
	public List<Goods> listGoodsByCatId(String cat_id){
		String sql = "";
		if("-1".equals(cat_id)){//cat_id=-1展示未分类
			sql = SF.goodsSql("LIST_GOODS_FOR_CORE")+" and g.cat_id is null ";
			return this.baseDaoSupport.queryForList(sql, Goods.class,Consts.GOODS_DISABLED_0);
		}else{
			sql = SF.goodsSql("LIST_GOODS_FOR_CORE")+" and g.cat_id= ? ";
			return this.baseDaoSupport.queryForList(sql, Goods.class,Consts.GOODS_DISABLED_0,cat_id);
		}
	}

	@Override
	public Goods getGoodsIntros(String goods_id) {
		String sql = SF.goodsSql("GOODS_INTRO_GET");
		List<Goods> goodList = this.baseDaoSupport.queryForList(sql, Goods.class, goods_id, Consts.GOODS_DISABLED_0);
		Goods goods = (goodList!=null && goodList.size()>0) ? goodList.get(0) : null;
		return goods;
	}

	@Override
	public Goods getGoodsBaseInfo(String goods_id) {
		String sql = SF.goodsSql("LIST_GOODS_FOR_CORE")+" and g.goods_id=? ";
		List<Goods> goodsList = this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, goods_id);
		Goods goods = (goodsList!=null || goodsList.size()>0) ? goodsList.get(0) : null;
		return goods;
	}
}
