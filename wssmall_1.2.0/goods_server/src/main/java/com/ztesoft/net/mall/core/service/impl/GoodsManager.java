package com.ztesoft.net.mall.core.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.req.CoQueueModifyReq;
import params.coqueue.resp.CoQueueModifyResp;
import params.goods.sales.req.GoodsSalesReq;
import params.member.req.MemberLvByLvIdReq;
import params.member.req.MemberPriceListByGoodsIdReq;
import params.member.resp.MemberLvByLvIdResp;
import params.member.resp.MemberPriceListByGoodsIdResp;
import params.suppler.req.SupplierObjReq;
import params.suppler.resp.SupplierObjResp;
import services.MemberLvInf;
import services.MemberPriceInf;
import services.ModHandlerInf;
import services.SupplierInf;
import vo.GoodsPlugin;
import zte.net.ecsord.params.ecaop.req.MessageSyncReq;
import zte.net.ecsord.params.ecaop.resp.MessageSyncResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.common.SerializeList;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.eop.sdk.utils.UploadUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.ActiveElementInfo;
import com.ztesoft.net.mall.core.model.AgentInfo;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Cloud;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.CommunityActivity;
import com.ztesoft.net.mall.core.model.Country;
import com.ztesoft.net.mall.core.model.EsTerminal;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.GoodsApplyJoin;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsControlStore;
import com.ztesoft.net.mall.core.model.GoodsImportLog;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.GoodsParamValue;
import com.ztesoft.net.mall.core.model.GoodsRel;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsSpecificationModel;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Paramsenum;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.ProductPramasList;
import com.ztesoft.net.mall.core.model.Relations;
import com.ztesoft.net.mall.core.model.SaleGoods;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.TerminalImportLog;
import com.ztesoft.net.mall.core.model.mapper.GoodsListMapper;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.plugin.goods.GoodsPluginBundle;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.IEsGoodsCoManager;
import com.ztesoft.net.mall.core.service.IFlowManager;
import com.ztesoft.net.mall.core.service.IGoodsAgreementManager;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsCommonManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.IPackageProductManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.service.IProductoManager;
import com.ztesoft.net.mall.core.service.impl.cache.GoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.GoodsUtils;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.SqlUtils;
import com.ztesoft.net.mall.core.workflow.core.FlowType;
import com.ztesoft.net.mall.core.workflow.core.InitFlowParam;
import com.ztesoft.net.mall.core.workflow.util.ModParams;
import com.ztesoft.net.mall.core.workflow.util.ModVO;
import com.ztesoft.net.mall.plugin.standard.type.GoodsTypeUtil;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;

/**
 * Saas式Goods业务管理
 * 
 * @author kingapex 2010-1-13下午12:07:07
 */
@SuppressWarnings("all")
public class GoodsManager extends BaseSupport implements IGoodsManager {

	private GoodsPluginBundle goodsPluginBundle;
	private IPackageProductManager packageProductManager;
	private IGoodsCatManager goodsCatManager;
	private IEsGoodsCoManager esGoodsM;
	private ICoQueueManager coQueueManager;
	private IProductoManager productoM;
	private CacheUtil cacheUtil;
	private GoodsCacheUtil goodsCacheUtil;
	private SqlUtils sqlUtils;
	private SupplierInf supplierServ;
	private IGoodsAgreementManager goodsAgreementManager;
	private IGoodsTypeManager goodsTypeManager;
	private IFlowManager flowManager ;
	
	private ModHandlerInf modInfoHandler ;
	private MemberPriceInf memberPriceServ;
	private MemberLvInf memberLvServ;
	private GoodsNetCacheRead read = new GoodsNetCacheRead();
	private IGoodsCommonManager goodsCommonManager;
	
	public static final String GOODS_TYPE_NUM_CARD = "20000";//号卡
	public static final String GOODS_TYPE_WIFI_CARD = "20001";//上网卡
	public static final String GOODS_TYPE_CONTRACT_MACHINE = "20002";//合约机
	public static final String GOODS_TYPE_PHONE = "20003";//裸机
	public static final String GOODS_TYPE_GIFT = "20008";//成品卡
	public static final String GOODS_TYPE_ADJUNCT = "69070000";//配件	
	
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	public static int NAMESPACE = 300;
	static int time =60*24*60*5;//缺省缓存20天,memcache最大有效期是30天
	
	/**
	 * 默认生成商品（货品）关系编码
	 * @return
	 */
	@Override
	public String createGoodsRelCode() {
		
		return this.baseDaoSupport.getSequences("S_ES_GOODS_REL_CODE");
	}
	
	/**
	 * 默认生成商品货品SKU
	 * @param type 
	 * @return
	 */
	@Override
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
		//this.baseDaoSupport.getSequences("S_ES_GOODS_REL_CODE");
		return sku;
	}
	
	/**
	 * 生成货品SN
	 * @param type 
	 */
	@Override
	public String createSN(String type,String prefix,String postfix){
		
		return this.baseDaoSupport.getSequences("S_ES_PRODUCT_SN");
	}
	
    /**
     * 添加商品控制信息
     */
	@Override
	public void insertGoodsControl(GoodsControl goodsControl){
		goodsControl.setCreate_date(DBTUtil.current());
		goodsControl.setControl_name(this.getControlName(goodsControl.getControl_type()));
		this.baseDaoSupport.insert("es_goods_control", goodsControl);
	}
	
	
	/**
	 * 得到商品控制名称
	 */
	
	public String getControlName(String controlType){
		String control_name = this.baseDaoSupport.queryForString(SF.goodsSql("GET_CONTROL_NAME"), controlType);
	    return control_name;
	}
	/**
	 * 根据ID获得商品控制信息
	 */
	@Override
	public List<GoodsControl> getControlById(String goods_id){
		
		String sql = SF.goodsSql("GET_CONTROL_BY_ID_0");
    	List<GoodsControl> goodsControlList = this.baseDaoSupport.queryForList(sql, goods_id);
    	
    	return goodsControlList;
		
	}
	
	/**
	 *  /**
     *  控制商品控制金额和数量
     * @param agreement_id 协议ID
     * @param lan_code     本地网编码
     * @param curr_control_value 当前输入控制值
     * @return  flag true  是该值小于统计的控制值 false  为大于 则不能输入此值
     */
	
	@Override
	public boolean isOverQtyOrMoney(String agreement_id,String lan_code,int curr_control_value){
		boolean flag = false;
		StringBuffer goodsControlsql = new StringBuffer();
		
		String  agreementControlsql = SF.goodsSql("IS_OVERQTY_OR_MONEY_SELECT");
		String countSql = SF.goodsSql("IS_OVERQTY_OR_MONEY_COUNT");
	    if(curr_control_value==0){
	    	return true;
	    }
		int agreeCount  = this.baseDaoSupport.queryForInt(countSql, agreement_id, lan_code);
		if(agreeCount==0){
	    	flag= false;
	    	return flag;
	    }
		Map map = this.baseDaoSupport.queryForMap(agreementControlsql, agreement_id, lan_code);
		String control_type = map.get("control_type").toString();
		String sub_control_type = map.get("sub_control_type").toString();
		goodsControlsql.append(SF.goodsSql("IS_OVERQTY_OR_MONEY_COUNT_VALUE"));
		if("CO".equals(control_type)){
			goodsControlsql.append(" and  sub_control_type='"+sub_control_type+"'");
		}
		int agreementControlVal = Integer.parseInt(map.get("control_value").toString());
		
		int goodsControlVal = this.baseDaoSupport.queryForInt(goodsControlsql.toString(), lan_code, control_type);
		
		int restVal = agreementControlVal - goodsControlVal;
		if(restVal>=curr_control_value){
			flag = true;
		}
		
		return flag;
	}
	
	//售后产品
	@Override
	public List<Goods> getAfterService(){
		//售后类型
		//商品分类
		String cat_id = "201412176703000540";
		//商品类型
		String type_id = "201412178804000355";
		String sql="select a.image_file,a.goods_id,a.name,a.sn,a.cat_id,a.type_id,a.goods_type,a.price,a.mktprice,a.create_time,a.store,a.params from es_goods a where 1=1 and a.cat_id=? and a.type_id=? and a.market_enable=1 and a.disabled=0 and a.source_from='FJ' ";
		return this.baseDaoSupport.queryForList(sql, Goods.class, cat_id,type_id);
	}
	
	/*
	 * 编辑页根据ID展示名称
	 */
	
	@Override
	public  Map getNameListByID(String prod,String agreement_id){
	     Map map = new HashMap();
      
		//String sql ="select user_name from   es_supplier where supplier_id in('"+prod+"','"+prch+"','"+prov+"','"+logst+"')";
	    
	     SupplierObjReq supplierObjReq = new SupplierObjReq();
	     supplierObjReq.setSupplier_id(prod);
	     
	     SupplierObjResp supplierObjResp = new SupplierObjResp();
	     supplierObjResp = this.supplierServ.findSupplierById(supplierObjReq);
	     String	prod_name = "";
	     if(supplierObjResp != null){
	    	 prod_name = supplierObjResp.getSupplier_name();
	     }
	   // String	prch_name = this.supplierManager.getUserNameById(prch);
	  //  String  prov_name = this.supplierManager.getUserNameById(prov);
	  //  String  logst_name = this.supplierManager.getUserNameById(logst);
	
		
		
		String sqlStr = SF.goodsSql("GET_NAMELIST_BY_ID");
    	String name = this.baseDaoSupport.queryForString(sqlStr, agreement_id);
		//String agreement_name = this.goodsAgreementManager.getNameById(agreement_id);
		map.put("agreement_name", name);
		
		return map;
	}
	/**
	 * 根据ID删除所有的控制信息
	 * @param goods_id
	 */
	@Override
	public void delAllControlByAgtId(String goods_id){
		String sql = SF.goodsSql("DEL_ALL_CONTROL_BY_AGTID");
		this.baseDaoSupport.execute(sql, goods_id);
	}
	/**
	 * 添加商品，同时激发各种事件
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Goods goods,GoodsRules goodsRules,GoodsPackage goodsPackage,String communityCodes,String countyIds,String custIds,String staffIds,String developIds,String officeIds, String s_scheme_id, String s_element_type) throws Exception{
			checkSave(goods);
			Map goodsMap = ReflectionUtil.po2Map(goods);
			
			//保存供货商id
			setSupperId(goodsMap);
			
			// 触发商品	添加前事件
			goodsPluginBundle.onBeforeAdd(goodsMap);
			goodsMap.put("disabled", 0);
			goodsMap.put("create_time", DBTUtil.current());
			goodsMap.put("view_count", 0);
			goodsMap.put("buy_count", 0);
			goodsMap.put("cost", 0);
			if(StringUtils.isEmpty((String)goodsMap.get("fail_date")))
				goodsMap.remove("fail_date");
			if(StringUtils.isEmpty((String)goodsMap.get("effect_date")))
				goodsMap.remove("effect_date");
			
			String goods_id = null;
			if(StringUtils.isEmpty(goods.getGoods_id()) || StringUtils.isEmpty(Const.getStrValue(goodsMap, "goods_id"))){
				goods_id = this.baseDaoSupport.getSequences("S_ES_GOODS");
				goods.setGoods_id(goods_id);
				goodsMap.put("goods_id", goods_id);
			}
			else{
				goods_id = goods.getGoods_id();
			}
			
			
			if(StringUtils.isEmpty((String)goodsMap.get("sku"))){
				
				//后台自动生成SKU
				String sku = this.createSKU(goods.getType(), goods.getCat_id());
				goodsMap.put("sku", sku);
				goods.setSku(sku);
			}
			
			this.baseDaoSupport.insert("goods", goodsMap);
			
			savePricePriv( goods_id);

			if("goods".equals(goods.getType())){
				saveGoodsRules(goods_id,"ORDERSTANDARDIZE_AUTO");
			}
			
			// addGoodsCount(goods.getCat_id());
			// 触发商品添加后事件
			goodsPluginBundle.onAfterAdd(goodsMap);
			//添加商品，保存es_goods_package
			//商品批量导入不在这里处理商品包
			if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) && 
					Consts.ECS_QUERY_TYPE_GOOD.equals(goods.getType())) {

				if(null == goodsPackage){
					goodsPackage = new GoodsPackage();
				}
				//页面手工填写了P_CODE或SN
				if (StringUtils.isNotEmpty(goods.getAct_code()) 
						|| StringUtils.isNotEmpty(goods.getProd_code())) {
					//裸机不校验es_goods_package数据(sn对应一个商品),允许多个
					//屏蔽页面配置时不填sn
					//if(!StringUtils.equals("20003", goodsMap.get("type_id").toString())){
						if (this.goodsPackageExists(goods_id, goods.getAct_code(), goods.getProd_code())) {
							CommonTools.addFailError("商品映射关系已经存在【es_goods_package】!");
						}
					//}
					goodsPackage.setGoods_id(goods_id);
					goodsPackage.setP_code(goods.getAct_code());
					goodsPackage.setSn(goods.getProd_code());
					//华盛物料号
					goodsPackage.setHs_matnr(goods.getMatnr());
					goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
					this.savaGoodsPackage(goodsPackage);
				}else if(StringUtils.isEmpty(goods.getProd_code()) 
						&& StringUtils.equals("20003", goodsMap.get("type_id").toString())){
					//裸机不校验es_goods_package数据(sn对应一个商品),允许多个
					//裸机页面不录入sn时直接保存空，避免走到else里去插入es_goods_package时报错
					goodsPackage.setGoods_id(goods_id);
					goodsPackage.setP_code("");
					goodsPackage.setSn("");
					//华盛物料号
					goodsPackage.setHs_matnr(goods.getMatnr());
					goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
					this.savaGoodsPackage(goodsPackage);
				} else {
					//华盛物料号
					goodsPackage.setHs_matnr(goods.getMatnr());
					processGoodsPackageData(goods_id,goodsPackage);
				}
			}
			
			//单宽带类商品关联小区
			if(StringUtils.isNotBlank(communityCodes)){
				saveGoodsRelCommunity(goods_id,communityCodes);
			}
			if(StringUtils.isNotBlank(countyIds)){
				saveGoodsRelCounty(goods_id,countyIds);
			}
				
			if(StringUtils.isNotBlank(custIds)){
				saveGoodsRelCust(goods_id,custIds);
			}
			if(StringUtils.isNotBlank(staffIds)){
				saveGoodsRelStaff(goods_id,staffIds);
			}
			if(StringUtils.isNotBlank(developIds)){
				saveGoodsRelDevelop(goods_id,developIds);
			}
			if(StringUtils.isNotBlank(officeIds)){
				saveGoodsRelOffice(goods_id,officeIds);
			}
			if(StringUtils.isNotBlank(s_scheme_id)&&StringUtils.isNotBlank(s_element_type)){
				saveGoodsElement(goods_id,s_scheme_id,s_element_type);
			}
			//缓存商品货品
			cacheGoods(goods);

			doAfterService(goodsMap,goods_id);

	}
	
	//是否为售后商品类型
	private void doAfterService(Map goodsMap,String goods_id){
		if("FJ".equals(ManagerUtils.getSourceFrom())&&"201412178804000355".equals(goodsMap.get("type_id").toString())){
			editGoods(goodsMap.get("params").toString(),goods_id);
		}
	}
	
	/**
	 * 添加新的商品
	 * @param goods
	 * @throws Exception
	 */
	@Override
	public void importAdd(Goods goods){
		//checkSave(goods);
		Map goodsMap = ReflectionUtil.po2Map(goods);
		
		//保存供货商id
		//setSupperId(goodsMap);
		
		goodsMap.put("disabled", 0);
		goodsMap.put("create_time", DBTUtil.current());
		goodsMap.put("view_count", 0);
		goodsMap.put("buy_count", 0);
		goodsMap.put("cost", 0);
		if(StringUtils.isEmpty((String)goodsMap.get("fail_date")))
			goodsMap.remove("fail_date");
		if(StringUtils.isEmpty((String)goodsMap.get("effect_date")))
			goodsMap.remove("effect_date");
		
		String goods_id = null;
		if(StringUtils.isEmpty(goods.getGoods_id()) || StringUtils.isEmpty(Const.getStrValue(goodsMap, "goods_id"))){
			goods_id = this.baseDaoSupport.getSequences("S_ES_GOODS");
			goods.setGoods_id(goods_id);
			goodsMap.put("goods_id", goods_id);
		}
		else{
			goods_id = goods.getGoods_id();
		}
		
		if(StringUtils.isEmpty((String)goodsMap.get("sku"))){
			//后台自动生成SKU
			String sku = this.createSKU(goods.getType(), goods.getCat_id());
			goodsMap.put("sku", sku);
			goods.setSku(sku);
		}
		
		this.baseDaoSupport.insert("goods", goodsMap);
		
		//缓存商品货品
		this.cacheGoods(goods);
		
		doAfterService(goodsMap,goods_id);
	}
	
	/**
	 * 插入es_product数据
	 * @param goods
	 */
	@Override
	public void insertProduct(Goods goods){
		String goodsId = goods.getGoods_id();
		Product product =  new Product();
		String cost =goods.getCost()+"";
		if(StringUtil.isEmpty(cost) || "null".equals(cost))
			cost ="0";
		
		String store =goods.getStore()+"";
		if(StringUtil.isEmpty(store) || "null".equals(store))
			store ="0";
		
		String weight =goods.getWeight()+"";
		if(StringUtil.isEmpty(weight) || "null".equals(weight))
			weight ="0";
		
		String price =goods.getPrice()+"";
		if(StringUtil.isEmpty(price) || "null".equals(price))
			price ="0";
		
		product.setHaveSpec(null) ;//2013-08-22 规则开启 mod by easonwu 
		product.setGoods_id(goodsId);
		product.setCost(  Double.valueOf(cost) );
		product.setPrice(   Double.valueOf( price)  );
		product.setSn(goods.getSn());
		product.setStore(Integer.valueOf(store));
		product.setWeight(Double.valueOf(weight));
		product.setName(goods.getName());
		product.setColor(goods.getColor());
		product.setSku((goods.getSku()==null || "".equals(goods.getSku()))?null:goods.getSku());
		product.setType((goods.getType()==null || "".equals(goods.getType()))?null:goods.getType());
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		
		IProductManager productManager = SpringContextHolder.getBean("productManager");
		productManager.addProductEcs(productList,goodsId);
		
	}
	
	public void processGoodsPackageData(String goods_id,GoodsPackage goodsPackage){
		if(goodsPackage==null)
			goodsPackage = new GoodsPackage();
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String[] sns = request.getParameterValues("sns")==null?(request.getAttribute("sns")==null?null:(String[])request.getAttribute("sns")):request.getParameterValues("sns");
		String[] typeids = request.getParameterValues("typeids")==null?(request.getAttribute("typeids")==null?null:(String[])request.getAttribute("typeids")):request.getParameterValues("typeids");
		String[] z_goods_ids = request.getParameterValues("goods_ids")==null?(request.getAttribute("goods_ids")==null?null:(String[])request.getAttribute("goods_ids")):request.getParameterValues("goods_ids");
		String contract_goods_id = null;//合约计划
		String offer_goods_id = null;
		String sn = null;
		if(typeids!=null && typeids.length>0){
			for(int i=0;i<typeids.length;i++){
				String typeid = typeids[i];
				if((Consts.GOODS_TYPE_TERMINAL.equals(typeid) 
						|| Consts.PRODUCT_TYPE_FIN_CARD.equals(typeid)
						|| Consts.PRODUCT_TYPE_ADJUNCT.equals(typeid)
						|| Consts.PRODUCT_TYPE_INTERNET.equals(typeid))
						&& sns[i]!=null && !"".equals(sns[i])){
					sn = sns[i];
					goodsPackage.setSn(sn);
				}
				if(Consts.GOODS_TYPE_CONTRACT.equals(typeid))
					contract_goods_id = z_goods_ids[i];
				if(Consts.GOODS_TYPE_OFFER.equals(typeid))
					offer_goods_id = z_goods_ids[i];
			}
		}
		
		String p_code = "";
		//合约、套餐都不为空时，取p_code
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
		
		goodsPackage.setGoods_id(goods_id);
		goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
		savaGoodsPackage(goodsPackage);
	}
	
	@Override
	public void cacheGoods(Goods goods){
		GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
		goodsRefreshDTO.setGoods_ids("'"+goods.getGoods_id()+"'");
		this.initGoodsCacheByCondition(goodsRefreshDTO);
	}
	
	private void setSupperId(Map goodsMap){
		//add by wui设置供货商信息,防止工号被删除，商品归属供货商信息丢失
		String userId ="";
		if(Consts.CURR_FOUNDER4==ManagerUtils.getAdminUser().getFounder().intValue()) //供货商
			userId =ManagerUtils.getAdminUser().getUserid();
		else if(Consts.CURR_FOUNDER5 == ManagerUtils.getAdminUser().getFounder().intValue()) //供货商员工
			userId = ManagerUtils.getAdminUser().getParuserid();
		if(!StringUtil.isEmpty(userId)){
			SupplierObjReq supplierObjReq = new SupplierObjReq();
			supplierObjReq.setSupplier_id(userId);
			
			SupplierObjResp supplierObjResp = new SupplierObjResp();
			supplierObjResp = supplierServ.findSupplierById(supplierObjReq);
			
			Supplier supper = new Supplier();
			if(supplierObjResp != null){
				supper = supplierObjResp.getSupplier();
			}
			if(supper !=null)
				goodsMap.put("supper_id", supper.getSupplier_id());
		}
	}
	/**
	 * 保存价格公开权限数据
	 * @param goods_id
	 * @return
	 */
	private boolean savePricePriv(String goods_id){
		//先删后插
		this.baseDaoSupport.execute(SF.goodsSql("PRICE_PRIV_DEL_BY_GOODS_ID") ,goods_id );
		
		
		String[] lvs = ThreadContextHolder.getHttpRequest().getParameterValues("lvidArray");
		if(lvs==null){
			Object [] object = (Object []) ThreadContextHolder.getHttpRequest().getAttribute("lvidArray");
			if(object!=null){
				lvs = new String[object.length];
				for(int i=0;i<object.length;i++){
					lvs[i] = object[i].toString();
				}
			}
			
		}
		
		String source_from = this.cacheUtil.getConfigInfo(Consts.KEY_SOURCE_FROM);
		
		List<Map> membersLvs = this.baseDaoSupport.queryForList(SF.goodsSql("MEMBER_LV_SELECT"), source_from) ;
		Map<String,String> membersLvMap = new HashMap<String,String>() ;
		if(membersLvs != null && !membersLvs.isEmpty()){
			for(Map lv : membersLvs ){
//				if(StringUtils.isNotEmpty(lv))
					membersLvMap.put((String)lv.get("lv_id"), "00X") ;
			}	
		}
		if(lvs != null && lvs.length > 0){
			for(String lv : lvs){
				if(membersLvMap.containsKey(lv))
					membersLvMap.put(lv, "00A") ;
			}
		}
		
//		List<Map<String,String>> datas = new ArrayList<Map<String,String>>() ;
		
		Map<String,String> data = null ;
		for(Iterator<String> it = membersLvMap.keySet().iterator() ; it.hasNext() ;){
			String role_type = it.next() ;
			String state = membersLvMap.get(role_type) ;
			data = new HashMap<String,String>() ;
			data.put("role_type", role_type) ;
			data.put("state", state) ;
			data.put("goods_id", goods_id) ;
			
//			datas.add(data) ;
			
			this.baseDaoSupport.insert("es_price_priv", data);
		}
		
		return true ;
		
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
	
	//保存产品包表信息
	@Override
	public void savaGoodsPackage(GoodsPackage goodsPackage){
		
		baseDaoSupport.insert("goods_package", goodsPackage);
	}
	
	@Override
	public List<Goods> checkExists(String cardCode,Integer mktprice,String offer_id,String charge_type){
		String sql = SF.goodsSql("GOODS_GET_BY_TYPE_CODE");
		if("".equals(offer_id)||null!=offer_id){
			sql+=" and crm_offer_id='"+offer_id+"' ";
		}
		if("".equals(mktprice)||null!=mktprice){
			sql+=" and mktprice='"+mktprice+"' ";
		}
		if(charge_type != null && !"".equals(charge_type)){
			sql+=" and effective_area_flag='"+charge_type+"'";
		}
		return this.baseDaoSupport.queryForList(sql, Goods.class, cardCode);
	}

	private void addGoodsCount(String catid) {

		// 更新商品分类的商品数+1
		Cat cat = this.goodsCatManager.getById(catid);
		String catpath = cat.getCat_path();
		catpath = catpath.replace('|', ',');
		this.baseDaoSupport.execute(SF.goodsSql("ADD_GOODS_COUNT") + " and cat_id in (" + catpath + ")");

	}

	/**
	 * 修改商品同时激发各种事件
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(Goods goods,GoodsRules goodsRules,String communityCodes,String countyIds,String custIds,String staffIds,String developIds,String officeIds, String s_scheme_id, String s_element_type) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始保存商品数据...");
		}
		goods.setLast_modify(DBTUtil.current());
		Map goodsMap = ReflectionUtil.po2Map(goods);
		if(StringUtils.isEmpty((String)goodsMap.get("fail_date")))
				goodsMap.remove("fail_date");
		if(StringUtils.isEmpty((String)goodsMap.get("effect_date")))
			goodsMap.remove("effect_date");
		this.goodsPluginBundle.onBeforeEdit(goodsMap);
		this.baseDaoSupport.update("goods", goodsMap, "goods_id='"+ goods.getGoods_id()+"'");

		//商品修改活动编码与产品编码---zengxianlian
		GoodsPackage goodsPackage = new GoodsPackage();
		goodsPackage.setGoods_id(goods.getGoods_id());
		goodsPackage.setP_code(goods.getAct_code());
		goodsPackage.setSn(goods.getProd_code());
		Map goodsPackageMap = ReflectionUtil.po2Map(goodsPackage);
		this.baseDaoSupport.update("goods_package", goodsPackageMap, "goods_id='"+ goods.getGoods_id()+"'");
		
		savePricePriv( goods.getGoods_id() );
		
		//editGoodsRules(goodsRules,goods.getGoods_id());
		
		//单宽带类商品关联小区
		
		saveGoodsRelCommunity(goods.getGoods_id(),communityCodes);
		
		saveGoodsRelCounty(goods.getGoods_id(),countyIds);
			
		saveGoodsRelCust(goods.getGoods_id(), custIds);
			
		saveGoodsRelStaff(goods.getGoods_id(),staffIds);
		
		saveGoodsRelDevelop(goods.getGoods_id(), developIds);
			
		saveGoodsRelOffice(goods.getGoods_id(), officeIds);
		this.saveGoodsElement(goods.getGoods_id(), s_scheme_id, s_element_type);
		
		this.goodsPluginBundle.onAfterEdit(goodsMap);
		if (logger.isDebugEnabled()) {
			logger.debug("保存商品数据完成.");
		}
		
		cacheGoods(goods);
		
		doAfterService(goodsMap,goods.getGoods_id());
	}

	
	/**
	 * 得到修改商品时的数据
	 * 
	 * @param goods_id
	 * @return
	 */
	@Override
	public GoodsEditDTO getGoodsEditData(String goods_id) {
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
		goods.put("matnr", goodsPkgMap.get("hs_matnr"));
		
		List<String> htmlList = goodsPluginBundle.onFillEditInputData(goods);
		editDTO.setGoods(goods);
		editDTO.setHtmlList(htmlList);

		return editDTO;
	}

	/**
	 * 读取一个商品的详细<br/> 处理由库中读取的默认图片和所有图片路径:<br>
	 * 如果是以本地文件形式存储，则将前缀替换为静态资源服务器地址。
	 */

	@Override
	public Map get(String goods_id) {
		//String sql = SF.goodsSql("GOODS_SELECT_BY_ID");
        String sql="select g.*,b.name as brand_name from es_goods g left join es_brand b on g.brand_id=b.brand_id where g.source_from =? and goods_id=?";
		Map goods = this.daoSupport.queryForMap(sql, ManagerUtils.getSourceFrom(),goods_id);

		String agentname ="";//
		goods.put("agentname", agentname);

		/**
		 * ====================== 对商品图片的处理 ======================
		 */
		String image_file = (String) goods.get("image_file");
		if (image_file != null) {
			image_file = UploadUtil.replacePath(image_file);
			goods.put("image_file", image_file);
		}

		String image_default = (String) goods.get("image_default");
		if (image_default != null) {
			image_default = UploadUtil.replacePath(image_default);
			goods.put("image_default", image_default);
		}

		/**
		 * ====================== 对会员价格的处理 ======================
		 */
		if (goods.get("have_spec") == null
				|| ((Integer) goods.get("have_spec")).intValue() == 0) {
			/** **************计算会员价格***************** */
			IUserService userService = UserServiceFactory.getUserService();
			Member member = userService.getCurrentMember();
			List<GoodsLvPrice> memPriceList = new ArrayList<GoodsLvPrice>();
			double discount = 1; // 默认是原价,防止无会员级别时出错
			if (member != null) {
				MemberPriceListByGoodsIdResp mbpResp = new MemberPriceListByGoodsIdResp();
				MemberPriceListByGoodsIdReq req = new MemberPriceListByGoodsIdReq();
				req.setGoods_id(goods.get("goods_id").toString());
				
				mbpResp = memberPriceServ.getPriceListByGoodsId(req);
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

				if (lv != null && lv.getDiscount() != null)
					discount = lv.getDiscount() / 100.00;
				Object obj = goods.get("price");
				Double price = (obj==null||"".equals(obj))?0d:(Double.parseDouble(obj.toString()) * discount);
				for (GoodsLvPrice lvPrice : memPriceList) {
					if (lvPrice.getGoodsid().equals(goods.get("goods_id"))
							&& lvPrice.getLvid().equals(member.getLv_id())) {
						price = lvPrice.getPrice();
					}
				}

				goods.put("price", price);
			}
		}

		return goods;
	}

	@Override
	public void getNavdata(Map goods) {
		// lzf 2011-08-29 add,lzy modified 2011-10-04
		String catid = goods.get("cat_id").toString();
		List list = goodsCatManager.getNavpath(catid);
		goods.put("navdata", list);
		// lzf add end
	}

	private String getListSql(int disabled) {
		String sql = SF.goodsSql("GOODS_CAT_SELECT") + " where g.source_from = '" + ManagerUtils.getSourceFrom() + "' and g.disabled=" + disabled;
		return sql;
	}

	/**
	 * 取得捆绑商品列表
	 * 
	 * @param disabled
	 * @return
	 */
	private String getBindListSql(int disabled) {
		String sql = SF.goodsSql("GOODS_CAT_SELECT_0")
				+ " where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.goods_type = 'bind' and g.disabled=" + disabled;
		return sql;
	}
	/**
	 * 后台搜索商品
	 * 
	 * @param params
	 *            通过map的方式传递搜索参数
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchGoods(String catid,String auditState, String name, String supplier_id, String sn,String brandId,String startTime,String endTime,
			Integer market_enable, String[] tagid, Integer[] staff_nos,
			String order,String selfProdType ,String type,String sku,Integer publish_state, int page, int pageSize) {
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
		if (order == null) {
			order = "create_time desc";
		}

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
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
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and g.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " tg where tag_id in("
					+ tagidstr + ") and g.source_from = tg.source_from)";
		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and g.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && market_enable!=2) {
			sql += "  and g.market_enable = " + market_enable;
		}
		if(publish_state !=null){
			sql += " and exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+publish_state+")";
		}
		
		if (startTime != null && !startTime.equals("")) {
			sql += " and g.create_time>= to_date('"+ startTime + " 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}

		if (endTime != null && !endTime.equals("")) {
			sql += " and g.create_time<= to_date('"+ endTime+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
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
		sql += " order by g." + order;
		//String countSql =countSql;
		
		
		Page webpage = this.baseDaoSupport.queryForCPage(sql, page, pageSize, Goods.class, countSql+statsWhereCond.toString());
		
		//xinz goods staff_no //

		return webpage;
	}

	/**
	 * 后台搜索待审核的商品
	 * 
	 * @param params
	 *            通过map的方式传递搜索参数
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchAuditGoods(Goods goods, int page, int pageSize) {
		String sql="";
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		int founder = adminUser.getFounder();
		// 管理员或省级或分销商不能看待审核的商品
			
		sql = SF.goodsSql("SEARCH_AUDIT_GOODS");
		if(goods != null){
			if (!StringUtil.isEmpty(goods.getName())) {
				sql += "  and a.name like '%" + goods.getName() + "%'";
			}
			if (!StringUtil.isEmpty(goods.getSn())) {
				sql += "   and a.sn like '%" + goods.getSn() + "%'";
			}
			if (!StringUtil.isEmpty(goods.getStart_date())) {
				sql += " and a.create_time>= to_date('"+ goods.getStart_date() + " 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if (!StringUtil.isEmpty(goods.getEnd_date())) {
				sql += " and a.create_time<= to_date('"+ goods.getEnd_date()+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			}
            if(StringUtils.isNotBlank(goods.getStype_id())){
                sql+=" and a.stype_id='"+goods.getStype_id()+"'";
            }
		}
		
		if(founder==Consts.CURR_FOUNDER4){
			sql += " and exists (select 1 from es_adminuser u where  u.userid=a.staff_no and u.userid='"+adminUser.getUserid()+"')";
		}
		
		String countSql = "select count(1)  " +sql.substring(sql.indexOf("from es_goods "));
		sql += " order by a.create_time desc" ;
		Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize, Goods.class, countSql);
		return webpage;
	}

	@Override
	public Page searchGoods(String catid, String name, String sn,
			Integer market_enable, String[] tagid, String order, int page,
			int pageSize) {

		String sql = SF.goodsSql("GOODS_SELECT_DIALOG");
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户

		if (order == null) {
			order = " create_time desc";
		}

		if (name != null && !name.equals("")) {
			sql += "  and t.name like '%" + name.trim() + "%'";
		}

		if (sn != null && !sn.equals("")) {
			sql += "   and t.sn = '" + sn.trim() + "'";
		}

		if (market_enable != null) {
			sql += " and t.market_enable=" + market_enable;
		}

		if (catid != null) {
			Cat cat = this.goodsCatManager.getById(catid);
			sql += " and  t.cat_id in(";
			sql += "select c.cat_id from " + this.getTableName("goods_cat")
					+ " c where c.cat_path like '" + cat.getCat_path()
					+ "%' and c.source_from = g.source_from)  ";
		}

		if (tagid != null && tagid.length > 0) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and t.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " where source_from = '" + ManagerUtils.getSourceFrom() + "' tag_id in("
					+ tagidstr + "))";
		}

		// 商品查询验证控制
		// if (!ManagerUtils.isAdminUser())
		// sql += " and g.staff_no =" + adminUser.getUserid();
		
		String countSql = "select count(*) from ("+sql+")";
		sql += " order by t." + order;
		
		Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize, Goods.class, countSql);

		return webpage;
	}

	/**
	 * 后台搜索商品
	 * 
	 * @param params
	 *            通过map的方式传递搜索参数
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchBindGoods(String name, String sn, String order, int page,
			int pageSize) {

		String sql = getBindListSql(0);

		if (order == null) {
			order = " create_time desc";
		}

		if (name != null && !name.equals("")) {
			sql += "  and g.name like '%" + name.trim() + "%'";
		}

		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}

		sql += " order by g." + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);

		List<Map> list = (webpage.getResult());

		for (Map map : list) {
			List productList = packageProductManager.list(map.get("goods_id")
					.toString());
			productList = productList == null ? new ArrayList() : productList;
			map.put("productList", productList);
		}

		return webpage;
	}

	/**
	 * 读取商品列表，待申请
	 * 
	 * @param params
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchApplyGoods(String catid, String name, String sn,String startTime,String endTime,String typeCode,
			Integer market_enable, String[] tagid, Integer[] staff_nos,
			String order, int page, int pageSize) {
		String sql = "";
		AdminUser adminUser=ManagerUtils.getAdminUser();
		String founder = Integer.toString(adminUser
				.getFounder());// 获取用户founder
		String paruserid = adminUser.getParuserid();// 获取归属上级工号
		String userid = adminUser.getUserid();// 获得分销商id
		String lan_id=adminUser.getLan_id();
		if (founder.equals(Consts.CURR_FOUNDER_1)||founder.equals(Consts.CURR_FOUNDER_0)
				|| Consts.LAN_PROV.equals(lan_id)) {// 电信管理员//超级管理员
			sql= SF.goodsSql("SEARCH_APPLY_GOODS_0_1");
		}

		if (Consts.CURR_FOUNDER_2.equals(founder)) {// 二级分销商
			sql = SF.goodsSql("SEARCH_APPLY_GOODS_2")
					+ " and  b.userid ='"
					+ paruserid
					+ "' and a.disabled='"+Consts.GOODS_DISABLED_0+"' and a.market_enable=1 and a.goods_id in (select area.goods_id from es_goods_area area where area.goods_id=a.goods_id and area.state='"+Consts.AREA_STATE_1+"' group by area.goods_id ) and b.state='"
					+ Consts.GOODS_USAGE_STATE_0
					+ "' "
					//+" and area.state='"+Consts.AREA_STATE_1+"'"
					+ " and not exists(select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.userid = '"
					+ userid
					+ "' and c.state='"
					+ Consts.GOODS_USAGE_STATE_1
					+ "') "
					+ " and not exists(select 1 from es_partner d where d.userid='"
					+ userid
					+ "' and d.state='"
					+ Consts.PARTNER_STATE_CONGELATION + " and d.sequ = "+ Consts.PARTNER_SEQ_0 +"') ";
		}
		if (Consts.CURR_FOUNDER_3.equals(founder)) {// 一级分销商
			sql = SF.goodsSql("SEARCH_APPLY_GOODS_3")
					//+ " and a.goods_id=area.goods_id"
					//+ " and area.state="+Consts.AREA_STATE_1
					+ " and not exists(select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.userid ='"
					+ userid
					+ "'"
					+ " and c.state='"
					+ Consts.GOODS_USAGE_STATE_1 + "') ";
		}
		
		if (order == null) {
			order = " create_time desc";
		}
		if (name != null && !name.equals("")) {
			sql += "  and a.name like '%" + name.trim() + "%'";
		}
		if (sn != null && !sn.equals("")) {
			sql += "   and a.sn = '" + sn.trim() + "'";
		}
		if (typeCode != null && !typeCode.equals("")) {
			sql += "   and c.type_code = '" + typeCode.trim() + "'";
		}
		if (startTime != null && !startTime.equals("")) {
			sql += " and a.create_time>= to_date('"+ startTime + " 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}

		if (endTime != null && !endTime.equals("")) {
			sql += " and a.create_time<= to_date('"+ endTime+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		sql += " order by a." + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		
		return webpage;
	}
	
	
	/**
	 * 待申请商品信息---主页面
	 */
	@Override
	public List<Goods> listGoodsApply() {
//		String sql = "select a.goods_id,a.name from es_goods a,es_goods_area b,es_goods_type c where a.goods_id = b.goods_id and b.state='0' and a.disabled=0 and a.market_enable=1 and a.type_id=c.type_id ";
		String sql="";
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String founder = Integer.toString(adminUser.getFounder());// 获取用户founder
		String paruserid = adminUser.getParuserid();// 获取归属上级工号
		String userid = ManagerUtils.getUserId();// 获得分销商id
		if (founder.equals(Consts.CURR_FOUNDER_1)|| Consts.LAN_PROV.equals(adminUser.getLan_id())||Consts.CURR_FOUNDER_0.equals(founder)) {// 电信管理员//超级管理员
			sql = SF.goodsSql("LIST_GOODS_APPLY_0_1");
		}
		if (Consts.CURR_FOUNDER_2.equals(founder)) {// 二级分销商
			sql = SF.goodsSql("LIST_GOODS_APPLY_2") + 
					" and u.userid='"+paruserid+"' and u.state='"+Consts.GOODS_USAGE_STATE_0+"' and t.type_id = a.type_id and a.disabled = '"+Consts.GOODS_DISABLED_0+"' and a.market_enable=1 and a.goods_id in (select area.goods_id from es_goods_area area where area.goods_id=a.goods_id and area.state='"+Consts.AREA_STATE_1+"' group by area.goods_id ) and not exists (select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.state = '"+Consts.GOODS_USAGE_STATE_1+"' and c.userid = '"+userid+"') and not exists(select 1 from es_partner d where d.userid='"+userid+"' and d.state='"+Consts.PARTNER_STATE_CONGELATION+"')  order by a.create_time desc";
		}
		if (Consts.CURR_FOUNDER_3.equals(founder)) {// 一级分销商
			sql = SF.goodsSql("LIST_GOODS_APPLY_3") + "  and not exists  (select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.userid ='" + 
						userid + "' and c.state = '"+ Consts.GOODS_USAGE_STATE_1 +"') order by a.create_time desc";
		}
		return this.daoSupport.queryForList(sql, Goods.class);
	}

	/**
	 * 成功订购的商品列表
	 * 
	 * @param params
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchApplyYesGoods(String catid, String name, String sn,String startTime,String endTime,String typeCode,
			Integer market_enable, String[] tagid, Integer[] staff_nos,
			String order, int page, int pageSize) {
		String userid = ManagerUtils.getUserId();// 获得分销商id
		String sql = SF.goodsSql("SEARCH_APPLYYES_GOODS");

		if (order == null) {
			order = " create_date desc";
		}
		if (name != null && !name.equals("")) {
			sql += "  and a.name like '%" + name.trim() + "%'";
		}
		if (sn != null && !sn.equals("")) {
			sql += "   and a.sn = '" + sn.trim() + "'";
		}
		if (typeCode != null && !typeCode.equals("")) {
			sql += "   and c.type_code = '" + typeCode.trim() + "'";
		}
		if (startTime != null && !startTime.equals("")) {
			sql += " and b.create_date>= to_date('"+ startTime + " 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}

		if (endTime != null && !endTime.equals("")) {
			sql += " and b.create_date<= to_date('"+ endTime+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		sql += " order by b." + order;
		Page webpage = this.daoSupport
				.queryForPage(sql, page, pageSize, userid);
		return webpage;
	}
	/**
	 * 一、二级分销商品已申请的商品数
	 * 
	 * */
	@Override
	public int applySuccNum(){
		String sql = SF.goodsSql("APPLY_SUCC_NUM");
		if(ManagerUtils.isProvStaff()){
			return 0;
		}else{
			return this.daoSupport.queryForList(sql, ManagerUtils.getUserId()).size();
		}
		
	}
	/**
	 * 电信员工看回收站数量
	 * */
	@Override
	public int getTrashNum(){
		if(!ManagerUtils.isNetStaff()){
			return 0;
		}else{
			String sql = SF.goodsSql("GET_TRASH_NUM");
			return this.baseDaoSupport.queryForList(sql,ManagerUtils.getUserId()).size();
		}
	}
	
	/**
	 * 校验商品编码是否已经存在
	 * */
	@Override
	public boolean checkGoodsSN(String sn,String goods_id){
		String num = "";
		boolean result = false;
		String sql = SF.goodsSql("CHECK_GOODS_SN");
		sql = sql+ " and g.type = '" + Consts.ECS_QUERY_TYPE_PRODUCT + "' ";
		if(goods_id !=null && !"".equals(goods_id)){
			sql = sql+ " and g.goods_id <> '"+goods_id+"'";
		}
		num = this.baseDaoSupport.queryForString(sql, sn); 
		
		if("0".equals(num)){
			result = false;
		}else{
			result = true;;
		}
		 
		return result;
	}
	

	/**
	 * 读取商品回收站列表
	 * 
	 * @param name
	 * @param sn
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page pageTrash(String name, String sn, String order, int page,
			int pageSize) {
		String sql = SF.goodsSql("PAGE_TRASH");
//		String sql = getListSql(1);
		String userId = null ;
		
		if (!ManagerUtils.isProvStaff()) {// 非管理员或电信员工
			sql += " and g.staff_no=? " ;
			userId = ManagerUtils.getAdminUser().getUserid();
		}
		
		if (order == null) {
			order = "goods_id desc";
		}

		if (name != null && !name.equals("")) {
			sql += "  and g.name like '%" + name.trim() + "%'";
		}

		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}

		if(order.contains("type_name")){
			sql += " order by t.name "+	order.substring(order.indexOf("type_name")+9,order.length());
		}else{
			sql += " order by g." + order;
		}
	
		
		Page webpage = null ;
		if (!ManagerUtils.isProvStaff())
			webpage = this.daoSupport.queryForPage(sql, page, pageSize,userId);
		else
			webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	/**
	 * 批量将商品放入回收站
	 * 
	 * @param ids
	 */
	@Override
	public void delete(String[] ids) {
		if (ids == null)
			return;

		String id_str = StringUtil.arrayToString(ids, ",");
		String sql = SF.goodsSql("GOODS_UPDATE_DISABLED_1"); 
		sql += ",STAFF_NO = '"+ManagerUtils.getAdminUser().getUserid()+"'";
		sql += " where 1 = 1 ";
		sql += " and goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql);
		
		String sql2 = "update es_goods_cat set goods_count=goods_count-1 " +
					 "where goods_count>0 and source_from='"+ManagerUtils.getSourceFrom()+"' " +
					 "and cat_id in(select distinct cat_id from es_goods where goods_id in("+id_str+"))";
		
		this.baseDaoSupport.execute(sql2);
	}

	/**
	 * 删除商户时下架该商户对应工号下的商品
	 * 
	 * @param staffno
	 */
	@Override
	public void delGoodsByStaffNo(String staffno) {
		if (staffno == null || "".equals(staffno))
			return;
		String sql = SF.goodsSql("DEL_GOODS_BY_STAFFNO");
		this.baseDaoSupport.execute(sql, staffno);
	}

	/**
	 * 还原
	 * 
	 * @param ids
	 */
	@Override
	public void revert(String[] ids) {
		if (ids == null)
			return;
		String id_str = StringUtil.arrayToString(ids, ",");
		String sql = SF.goodsSql("GOODS_REVERT") + " and goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql);
	}

	/**
	 * 清除
	 * 
	 * @param ids
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void clean(String[] ids) {
		if (ids == null)
			return;
		this.goodsPluginBundle.onGoodsDelete(ids);

		String id_str = StringUtil.arrayToString(ids, ",");
		String sql = SF.goodsSql("DEL_GOODS_BY_ID") + " and goods_id in (" + id_str + ")";
		String sql_delArea = SF.goodsSql("DEL_GOODS_AREA_BY_ID") + " and goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql_delArea);//删除区域表相关信息
		this.baseDaoSupport.execute(sql);

	}

	@Override
	public List list(String[] ids) {
		if (ids == null || ids.length == 0)
			return new ArrayList();
		StringBuffer sb = new StringBuffer("(");
		for(int i=0; i< ids.length; i++){
			if(i == ids.length -1){
				sb.append("'" + ids[i]+ "'");
			}else{
				sb.append("'" + ids[i]+ "',");
			}
		}
		sb.append(")");
		
		
		String sql = SF.goodsSql("GOODS_SELECT_BY_IDS") + sb.toString();
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List<String> fillAddInputData() {
		return goodsPluginBundle.onFillAddInputData();
	}

	/**
	 * 将po对象中有属性和值转换成map
	 * 
	 * @param po
	 * @return
	 */
	protected Map po2Map(Object po) {
		Map poMap = new HashMap();
		Map map = new HashMap();
		try {
			map = BeanUtils.describe(po);
		} catch (Exception ex) {
		}
		Object[] keyArray = map.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}
		return poMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void batchEdit() {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String[] ids = request.getParameterValues("goodsidArray");
		String[] names = request.getParameterValues("name");
		String[] prices = request.getParameterValues("price");
		String[] cats = request.getParameterValues("catidArray");
		String[] market_enable = request.getParameterValues("market_enables");
		String[] store = request.getParameterValues("store");
		String[] sord = request.getParameterValues("sord");

		String sql = "";

		for (int i = 0; i < ids.length; i++) {
			sql = "";
			if (names != null && names.length > 0) {
				if (!StringUtil.isEmpty(names[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " name='" + names[i] + "'";
				}
			}

			if (prices != null && prices.length > 0) {
				if (!StringUtil.isEmpty(prices[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " price=" + prices[i];
				}
			}
			if (cats != null && cats.length > 0) {
				if (!StringUtil.isEmpty(cats[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " cat_id=" + cats[i];
				}
			}
			if (store != null && store.length > 0) {
				if (!StringUtil.isEmpty(store[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " store=" + store[i];
				}
			}
			if (market_enable != null && market_enable.length > 0) {
				if (!StringUtil.isEmpty(market_enable[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " market_enable=" + market_enable[i];
				}
			}
			if (sord != null && sord.length > 0) {
				if (!StringUtil.isEmpty(sord[i])) {
					if (!sql.equals(""))
						sql += ",";
					sql += " sord=" + sord[i];
				}
			}
			sql = "update  goods set " + sql + " where goods_id=?";
			this.baseDaoSupport.execute(sql, ids[i]);

		}
	}

	@Override
	public Map census() {
		// 计算上架商品总数

		String founderSql1 = "";
		String founderSql2 = "";
		if (!ManagerUtils.isAdminUser()) {
			String staffNo = ManagerUtils.getAdminUser().getUserid();
			founderSql1 = " and staff_no=" + staffNo + " ";
			founderSql2 = " and exists(select 1 from "
					+ this.getTableName("goods")
					+ " where goods_id=t.object_id and staff_no=" + staffNo
					+ ")";
		}

		String sql = SF.goodsSql("GOODS_SELECT_1_0") + founderSql1;
		int salecount = this.baseDaoSupport.queryForInt(sql);

		// 计算下架商品总数
		sql = SF.goodsSql("GOODS_SELECT_0_0") + founderSql1;
		int unsalecount = this.baseDaoSupport.queryForInt(sql);

		// 计算回收站总数
		sql = SF.goodsSql("GOODS_SELECT_0_1") + founderSql1;
		int disabledcount = this.baseDaoSupport.queryForInt(sql);

		// 读取商品评论数
		sql = SF.goodsSql("GOODS_COMMENTS_COUNT") + founderSql2 + " and object_type='discuss'";
		int discusscount = this.baseDaoSupport.queryForInt(sql);

		// 读取商品评论数
		sql = SF.goodsSql("GOODS_COMMENTS_COUNT") + founderSql2 + " and object_type='ask'";
		int askcount = this.baseDaoSupport.queryForInt(sql);

		Map<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("salecount", salecount);
		map.put("unsalecount", unsalecount);
		map.put("disabledcount", disabledcount);
		map.put("allcount", unsalecount + salecount);
		map.put("discuss", discusscount);
		map.put("ask", askcount);
		return map;
	}

	@Override
	public List<Map> list() {
		String sql = SF.goodsSql("GOODS_SELECT_DISABLE_0");
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List<Goods> listGoods() {
		String sql = SF.goodsSql("LIST_GOODS");
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List<Goods> listGoodsNew() {
		String sql = SF.goodsSql("LIST_GOODS")+" and g.last_modify>sysdate-5";
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List<Goods> listProducts() {
		String sql = SF.goodsSql("LIST_PRODUCTS");
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0);
	}
	@Override
	public List<Map> listVproducts() {
		String sql = SF.goodsSql("LIST_V_PRODUCTS");
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List listGoodsAdjunctNum() {
		String sql = SF.goodsSql("LIST_GOODS_JOIN_ADJUNCT");
		return this.baseDaoSupport.queryForList(sql, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List listGoodsComplexNum(){
		String sql = SF.goodsSql("LIST_GOODS_COMPLEX_COUNT");
		return this.baseDaoSupport.queryForList(sql, Consts.GOODS_DISABLED_0);
	}
	
	
	@Override
	public List<Goods> listGoodsByBrandId(String brand_id){
		String sql = SF.goodsSql("GOODS_SELECT_DISABLE_0");
		String cond = " and brand_id=? ";
		return this.baseDaoSupport.queryForList(sql+cond, Goods.class,brand_id);
	}

	@Override
	public void updateField(String filedname, Object value, String goodsid) {
		
		ManagerUtils.resetWidgetCache();
		
		this.baseDaoSupport.execute("update goods set " + filedname
				+ "=? where goods_id=?", value, goodsid);
	}

	@Override
	public List listByCat(String catid) {
		String sql = getListSql(0);
		if (!catid.equals("0")) {
			Cat cat = this.goodsCatManager.getById(catid);
			sql += " and  g.cat_id in(";
			sql += "select c.cat_id from " + this.getTableName("goods_cat")
					+ " c where c.cat_path like '" + cat.getCat_path()
					+ "%')  ";
		}
		return this.daoSupport.queryForList(sql);
	}

	@Override
	public List listByTag(String[] tagid) {

		String sql = getListSql(0);
		if (tagid != null && tagid.length > 0) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and g.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " where tag_id in("
					+ tagidstr + "))";
		}
		return this.daoSupport.queryForList(sql);
	}

	/**
	 * 查询热卖商品
	 */
	@Override
	public Page searchHotGoods(int page, int pageSize) {

		
		@SuppressWarnings("unused")
		HttpServletRequest request  =ThreadContextHolder.getHttpRequest(); 
		String sql = SF.goodsSql("SEARCH_HOT_GOODS") + sqlUtils.getGoodsSqlByCatPath("") + "  order by buy_count desc";

		RowMapper rowMapper = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Goods goods = new Goods();
				goods.setName(rs.getString("name"));
				goods.setGoods_id(rs.getString("goods_id"));

				String image_default = rs.getString("image_default");
				if (image_default != null) {
					image_default = UploadUtil.replacePath(image_default);
					goods.setImage_default(image_default);
				}
				goods.setMktprice(rs.getDouble("mktprice"));
				goods.setPrice(rs.getDouble("price"));

				goods.setCreate_time(rs.getString("create_time"));
				goods.setLast_modify(rs.getString("last_modify"));
				goods.setType_id(rs.getString("type_id"));
				goods.setBuy_count(rs.getInt("buy_count"));
				goods.setStore(rs.getInt("store"));

				// lzy add
				goods.setSn(rs.getString("sn"));
				goods.setIntro(rs.getString("intro"));
				String image_file = rs.getString("image_file");
				if (image_file != null) {
					image_file = UploadUtil.replacePath(image_file);
					goods.setImage_file(image_file);
				}
				goods.setCat_id(rs.getString("cat_id"));
				return goods;
			}
		};
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,
				rowMapper);
		return webpage;
	}

	/**
	 * 商品总数
	 */
	@Override
	public int allGoodsNum() {
		String sql="";
		AdminUser user=ManagerUtils.getAdminUser();
		if (ManagerUtils.isProvStaff()) {// 电信管理员//超级管理员
			sql = SF.goodsSql("ALL_GOODS_NUM");
			}else{
			sql = SF.goodsSql("ALL_GOODS_NUM_0_1") + "'  and b.lan_id='"+ user.getLan_id() + "') ";
		}
		return this.daoSupport.queryForList(sql, Goods.class).size();
	}

	/**
	 * 待审核商品数
	 */
	@Override
	public int allGoodsAuditNum() {
		String sql = SF.goodsSql("ALL_GOODS_AUDITNUM");
		
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String founder =Integer.toString(adminUser.getFounder());// 获取用户founder
		if (founder.equals(Consts.CURR_FOUNDER_1)|| Consts.LAN_PROV.equals(adminUser.getLan_id())) {// 电信管理员//超级管理员
			return 0;
			//sql="select a.goods_id,a.name from es_goods a,es_goods_area b, es_goods_type t where a.goods_id =b.goods_id and b.state=1 and t.type_id = a.type_id and a.disabled = 0 and a.market_enable=1 and not exists (select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.state = '"+ Consts.GOODS_USAGE_STATE_1 +"') order by a.create_time desc";
		}else{
			sql+=" and b.lan_id='" + adminUser.getLan_id() + "'";
		}

//		if (Consts.CURR_FOUNDER_2.equals(founder)) {// 二级分销商
//			sql="select a.goods_id,a.name from es_goods a,es_goods_area b,es_goods_usage u, es_goods_type t where a.goods_id=u.goods_id and a.goods_id =b.goods_id and b.state=1 and u.userid='"+paruserid+"' and u.state='"+Consts.GOODS_USAGE_STATE_0+"' and t.type_id = a.type_id and a.disabled = 0 and a.market_enable=1 and not exists (select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.state = '"+Consts.GOODS_USAGE_STATE_1+"' and c.userid = '"+userid+"') and not exists(select 1 from es_partner d where d.userid='"+userid+"' and d.state='"+Consts.PARTNER_STATE_CONGELATION+"')  order by a.create_time desc";
//		}
//		if (Consts.CURR_FOUNDER_3.equals(founder)) {// 一级分销商
//			sql="select a.goods_id,a.name from es_goods a,es_goods_area b, es_goods_type t where a.goods_id =b.goods_id and b.state=1 and t.type_id = a.type_id and a.disabled = 0 and a.market_enable=1 and not exists (select 1 from es_goods_usage c where c.goods_id = a.goods_id and c.userid ='"+userid+"' and c.state = '"+ Consts.GOODS_USAGE_STATE_1 +"') order by a.create_time desc";
//		}
		
		return this.daoSupport.queryForList(sql, Goods.class).size();

	}
	//取CRM销售品ID_cloud
	@Override
	public Page findCrmOfferId(int pageNo,int pageSize,String offer_id,String offer_name){
		String sql = SF.goodsSql("FIND_CRM_OFFERID");
		String countSql = SF.goodsSql("FIND_CRM_OFFERID_COUNT");
		if(!"".equals(offer_id)&&offer_id!=null){
			sql+=" and a.offer_id="+offer_id;
			countSql+=" and a.offer_id="+offer_id;
		}
		if(offer_name != null && !"".equals(offer_name)){
			//sql += "  and a.name like '%" + name.trim() + "%'";
			sql+=" and a.offer_name like '%"+offer_name+"%'";
			countSql+=" and a.offer_name like '%"+offer_name+"%'";
			
		}
		return this.daoSupport.queryForCPage(sql, pageNo, pageSize, Cloud.class, countSql,Consts.ACC_NBR_STATE_0);
	}
	//取CRM销售品ID_contract
	@Override
	public Page findCrmOfferId2(int pageNo,int pageSize,String offer_id,String offer_name){
		String sql="";
		if(Consts.LAN_PROV.equals(ManagerUtils.getLanId())){
			sql = SF.goodsSql("FIND_CRM_OFFERID_2");
		}else{
			sql=SF.goodsSql("FIND_CRM_OFFERID_2_COUNT");
			
		}
		if(!"".equals(offer_id)&&offer_id!=null){
			sql+=" and a.prod_offer_id="+offer_id;
		}
		if(offer_name != null && !"".equals(offer_name)){
			sql+=" and a.prod_offer_name like '%"+offer_name+"%'";
		}
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize);
	}

	/**
	 * 根据商品类型和商品价格获取商品
	 * 
	 * @param price
	 *            商品价格
	 * @param goodsType商品类型
	 */
	@Override
	public Goods getGoodsByType(String typeCode, Integer price) {
		String sql = SF.goodsSql("GET_GOODS_BY_TYPE");
		return (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class, typeCode,price);
	}

	
	/*
	 * 
	 * 上架商品
	 */
	@Override
	public int groundingGoods() {
		AdminUser au = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getParuserid();
		boolean isSupplier = ((Consts.SUPPLIER_FOUNDER==au.getFounder())||(5==au.getFounder()));
		String sql = SF.goodsSql("GROUNDING_GOODS");
		if(isSupplier){
			sql += " and a.staff_no='"+userid+"'";
		}
		int totalAmount = this.baseDaoSupport.queryForInt(sql);
		return totalAmount;
	}
	
	/*
	 * 
	 * 下架商品
	 */
	@Override
	public int undercarriageGoods() {
		AdminUser au = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getParuserid();
		boolean isSupplier = ((Consts.SUPPLIER_FOUNDER==au.getFounder())||(5==au.getFounder()));
		String sql = SF.goodsSql("UNDER_CARRIAGE_GOODS");
		if(isSupplier){
			sql += " and a.staff_no='"+userid+"'";
		}
		int totalAmount = this.baseDaoSupport.queryForInt(sql);
		return totalAmount;
	}
	
	@Override
	public Goods getCatByGoodsId(String goodsId) {
		String sql = SF.goodsSql("GET_CATBY_GOODSID");
		return (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class, goodsId);
	}
	
	@Override
	public String getPaySequ(String seq) {
		return this.baseDaoSupport.getSequences(seq);
	}

	public GoodsPluginBundle getGoodsPluginBundle() {
		return goodsPluginBundle;
	}

	public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
		this.goodsPluginBundle = goodsPluginBundle;
	}

	public IPackageProductManager getPackageProductManager() {
		return packageProductManager;
	}

	public void setPackageProductManager(
			IPackageProductManager packageProductManager) {
		this.packageProductManager = packageProductManager;
	}
	
	public ICoQueueManager getCoQueueManager() {
		return coQueueManager;
	}

	public void setCoQueueManager(ICoQueueManager coQueueManager) {
		this.coQueueManager = coQueueManager;
	}

	@Override
	public Goods getGoods(String goods_id) {
		/*先刷一下缓存，判断商品是否被删除（商品申请用）*/
		//cacheUtil.refreshGoodsCache();
		return goodsCacheUtil.getGoodsById(goods_id);
	}
	
	@Override
	public String getLvPrice(String goods_id) {
		String lvPrice = "";
		String sql = SF.goodsSql("GET_LV_PRICE");
		lvPrice = this.baseDaoSupport.queryForString(sql, goods_id);
		return lvPrice;
	}

	public Goods getGoodsBaseInfo(String goods_id) {
		
		return (Goods) this.baseDaoSupport.queryForObject(SF.goodsSql("GET_GOODS_BY_ID"), Goods.class,goods_id);
	}
	
	@Override
	public Goods getGoodsById(String goods_id) {
		Goods goods = (Goods) this.baseDaoSupport.queryForObject(SF.goodsSql("GET_GOODS_BY_ID"), Goods.class,goods_id,0);
		if(goods != null){
			if(StringUtils.isNotBlank(goods.getImage_default())){
	            goods.setImage_default(UploadUtils.replacePath(goods.getImage_default()));
	        }
	        if(StringUtils.isNotBlank(goods.getImage_file())){
	            goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
	        }
		}
		return goods;
	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public int allGoods() {
		return this.baseDaoSupport.queryForInt(SF.goodsSql("ALL_GOODS"));
	}

	public CacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public SqlUtils getSqlUtils() {
		return sqlUtils;
	}

	public void setSqlUtils(SqlUtils sqlUtils) {
		this.sqlUtils = sqlUtils;
	}

	@Override
	public String getSupplierAdminUserId(String supplierId) {
		return this.baseDaoSupport.queryForString(SF.goodsSql("GET_SUPPLIER_ADMINUSERID"),supplierId);
	}

	
	@Override
	public boolean marketEnable(String goodsId, String state) {
		
		this.baseDaoSupport.execute(SF.goodsSql("MARKET_ENABLE") , new String[]{state ,goodsId });
		
		return true ;
	}



	public SupplierInf getSupplierServ() {
		return supplierServ;
	}


	public void setSupplierServ(SupplierInf supplierServ) {
		this.supplierServ = supplierServ;
	}


	@Override
	public Goods getGoodsByProductID(String productID) {
        String sql=SF.goodsSql("GET_GOODS_BY_PRODUCTID");
		return (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class,productID);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Goods> getInterestGoodByGoodIds(String goodIds){
		List<Goods> result=null;
		String sql1 = SF.goodsSql("GET_INTEREST_GOOD_BY_GOODIDS").replace(":replaceSql", goodIds);
		List<Cat> catList=this.baseDaoSupport.queryForList(sql1,Cat.class);
		String catIds="";
		if (catList!=null&&catList.size()>0) {
			for (Cat item:catList) {
				String[] catPathArray=item.getCat_path().split("\\|");
				if(catPathArray.length>0){
					for(String catId:catPathArray){
						catIds+=catId+",";
					}
				}
			}
		}
		if(!StringUtil.isEmpty(catIds)){
			catIds=catIds.substring(0, catIds.length()-1);
			String sql = SF.goodsSql("GET_INTEREST_GOODBYGOODIDS").replace(":instr", catIds);
			result=this.baseDaoSupport.queryForList(sql.toString(), 1, 50,new GoodsListMapper());
		}
		return result;
	}

	
	/**
	 * 商品入围申请
	 */
	@Override
	public void joinApply(GoodsApplyJoin goodsApplyJoin) {
		
		Map goodsApplyMap = null;
		
		goodsApplyMap = ReflectionUtil.po2Map(goodsApplyJoin);
		
		List<ModVO> modInfos = new ArrayList<ModVO>() ;
		ModVO modVO=new ModVO();
		
		modVO.setTableName("ES_GOODS");
		modVO.setObj(goodsApplyMap);
		
		modInfos.add(modVO);
		
		ModParams param = new ModParams(FlowType.GOODS_JOIN_APPLY,
				ManagerUtils.getAdminUser().getUserid(),goodsApplyJoin.getGoods_id() ,modInfos) ;
		
		this.modInfoHandler.checkAndStartFlow(param) ;

	}
	
	/**
	 * 商品入围申请
	 */
	@Override
	public void joinApply(String goods_id) {
		//变更状态信息
		String sql = SF.goodsSql("JOIN_APPLY");
		this.baseDaoSupport.execute(sql, goods_id) ;
		
		//起流程
		flowManager.startFlow(new InitFlowParam(FlowType.GOODS_JOIN_APPLY  , ManagerUtils.getAdminUser().getUserid() ,goods_id)) ;
	}
	
	/**
	 * 商品提价申请
	 */
	
	@Override
	public void appreciateApply(String goodsId, List<GoodsLvPrice> goodsLvPriceList){
		List<ModVO> modInfos = new ArrayList<ModVO>() ;
		for(GoodsLvPrice gp : goodsLvPriceList){
			ModVO vo = new ModVO();
			vo.setAction("M");//修改
			vo.setObj(gp);
			vo.setTableName("ES_GOODS_LV_PRICE");
			
			modInfos.add(vo);
		}
		ModParams param = new ModParams(FlowType.GOODS_MOD_PRICE  ,
				ManagerUtils.getAdminUser().getUserid(),goodsId ,modInfos ) ;		
		this.modInfoHandler.checkAndStartFlow(param) ;
		
		//提价申请中
		String sql = SF.goodsSql("APPRECIATE_APPLY");
		this.baseDaoSupport.execute(sql, goodsId) ;
//		
//		for(ModVO vo : modInfos){
//			if( !( vo.getChangedFields() != null && !vo.getChangedFields().isEmpty() ) ){
//				GoodsLvPrice price = (GoodsLvPrice)vo.getObj() ;
//				String sql = "update es_goods_lv_price set price=? where id=?";
//			}
//			//update 
//		}
	}
	
	/***
	 * 缺货商品总数
	 */
	@Override
	public int outofRegister(){
		int count =  0;
		String sql = SF.goodsSql("OUT_OFRE_GISTER");

		count = this.baseDaoSupport.queryForInt(sql);
		
		return count;
	}

	@Override
	public void modPrice(List<Map<String, String>> prices) {
		String goodsId = prices.get(0).get("goods_id") ;
		
		//goods_id/product_id/lvid/price >>不存在product_id则为新增,写表
		//商品层面的售价修改
		List<ModVO> modInfos = new ArrayList<ModVO>() ;
		ModParams param = new ModParams(FlowType.GOODS_MOD_PRICE  ,
				ManagerUtils.getAdminUser().getUserid(),goodsId ,modInfos ) ;
		this.modInfoHandler.checkAndStartFlow(param) ;
		
		
	}
	
	
	

	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}

	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}

	public IFlowManager getFlowManager() {
		return flowManager;
	}

	public void setFlowManager(IFlowManager flowManager) {
		this.flowManager = flowManager;
	}


	public IGoodsAgreementManager getGoodsAgreementManager() {
		return goodsAgreementManager;
	}


	public void setGoodsAgreementManager(
			IGoodsAgreementManager goodsAgreementManager) {
		this.goodsAgreementManager = goodsAgreementManager;
	}
	
	@Override
	public void addTemp(Goods goods) {
		Map goodsMap = po2Map(goods);
		String sql = SF.goodsSql("GOODS_TEMP_DEL");
        this.baseDaoSupport.execute(sql, goods.getGoods_id());
		this.baseDaoSupport.insert("goods_temp", goodsMap);
	}
	
	@Override
	public void updateTemp(Goods goods) {
		Map goodsMap = po2Map(goods);
            
		this.baseDaoSupport.update("GOODS_TEMP", goodsMap, "goods_id='"+ goods.getGoods_id()+"'");
	}
	
	@Override
	public Map qryGoodsTemp(String goods_id){
		
		String sql = SF.goodsSql("QRY_GOODS_TEMP");
		Map goodTmep = this.baseDaoSupport.queryForMap(sql, goods_id);
		return goodTmep;
	}
	@Override
	public List getPriceList(String goods_id){
		 String sql = SF.goodsSql("GET_PRICE_LIST");
	    
		 List list = this.baseDaoSupport.queryForList(sql, goods_id,goods_id);
		 
		 return list;
	}
	
	@Override
	public List getOrgByGoodsId(String type,String goods_id){
		 String sql = null;
		 if("goods".equalsIgnoreCase(type)){
			 sql = SF.goodsSql("GOODS_ORG_LIST");
		 }
		 else{
			 sql = SF.goodsSql("PRODUCT_ORG_LIST");
		 }
	    
		 List list = this.baseDaoSupport.queryForList(sql, goods_id);
		 
		 return list;
	}
	
	//批量
	@Override
	public List getOrgByGoodsIds(String type,String[] goods_ids){
		 String sql = null;
		 if("goods".equalsIgnoreCase(type)){
			 sql = SF.goodsSql("BATCH_GOODS_ORG_LIST");
		 }
		 else{
			 sql = SF.goodsSql("BATCH_PRODUCT_ORG_LIST");
		 }	    
		 List param = new ArrayList();
	     StringBuilder builder = new StringBuilder(1000);
	     builder.append(sql);
	     builder.append(" and g.goods_id in(");
	     for (String str : goods_ids) {
	         builder.append(str);
	         builder.append(",");
	     }
	     builder.deleteCharAt(builder.length() - 1);
	     builder.append(")");
	     List<Map> list = this.daoSupport.queryForList(builder.toString(),null);
	     List<Goods> listGoods=new ArrayList();
	     for (int i = 0; i < goods_ids.length; i++) {
	    	Goods goods=new Goods();
			String goods_id=goods_ids[i];
			String sale_store="";
			String name="";
			int index=0;
			for (int j = 0; j < list.size(); j++) {
		    	 Map g=list.get(j);
		    	 String id=g.get("goods_id")==null?"":g.get("goods_id").toString();
		    	 String org_name=g.get("org_name")==null?"":g.get("org_name").toString();		    	 
		    	 if(goods_id.equals(id)){
		    		if(index==0){
		    			sale_store="";
		    			sale_store+=org_name;
		    			name=g.get("name")==null?"":g.get("name").toString();
		    		}else{
		    			sale_store+="|";
		    			sale_store+=org_name;
		    		}
		    		index++;		    				    		
				 } 
			 }
			goods.setGoods_id(goods_id);
			goods.setSale_store(sale_store);	
			goods.setName(name);
			listGoods.add(goods);
			
		 }	     
		 return listGoods;
	}
	
	@Override
	public List<GoodsActionRule> queryGoodsRules(String goods_id,String service_code){
		
		String sql = SF.goodsSql("QUERY_GOODS_RULES");
		
		return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class, goods_id, service_code);
	}
	
	public void saveGoodsRules(String goods_id,String service_code){
		List<GoodsActionRule> list = getRuleList(goods_id,service_code);
		if(list != null && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				GoodsActionRule goodsActionRule = list.get(i);
				insertOrUpdateGoodsRules(goodsActionRule,goods_id);
				//this.baseDaoSupport.insert("es_goods_action_rule", goodsActionRule);
			}
		}
		
	}
	
	@Override
	public void saveGoodsRules(GoodsRules goodsRules,String goods_id,String service_code){
		List<GoodsActionRule> list = getRuleList(goodsRules,goods_id,service_code);
		if(list != null && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				GoodsActionRule goodsActionRule = list.get(i);
				insertOrUpdateGoodsRules(goodsActionRule,goods_id);
				//this.baseDaoSupport.insert("es_goods_action_rule", goodsActionRule);
			}
		}
		
	}
	
	@Override
	public void editGoodsRules(GoodsRules goodsRules,String goods_id,String service_code){
		List<GoodsActionRule> list = getRuleList(goodsRules,goods_id,service_code);
		if(list != null && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				GoodsActionRule goodsActionRule = list.get(i);
				insertOrUpdateGoodsRules(goodsActionRule,goods_id);
			}
		}
		
	}
	public void insertOrUpdateGoodsRules(GoodsActionRule goodsActionRule,String goods_id){
		String sql = SF.goodsSql("GOODS_ACTION_RULE_COUNT");
		int count = this.baseDaoSupport.queryForInt(sql, goods_id,goodsActionRule.getAction_code(),goodsActionRule.getService_code());
		if(count==0){
			this.baseDaoSupport.insert("es_goods_action_rule", goodsActionRule);
		}
		if(count>0){
			this.baseDaoSupport.update("es_goods_action_rule", goodsActionRule, "goods_id='"+goods_id+"' and action_code = '"+goodsActionRule.getAction_code()+"'"+" and service_code = '"+goodsActionRule.getService_code()+"'");
		}
	}
	private List<GoodsActionRule> getRuleList(GoodsRules goodsRules,String goods_id,String service_code){
		List<GoodsActionRule> list = new ArrayList<GoodsActionRule>();
		
		if(goodsRules != null){
			if(!StringUtils.isEmpty(goodsRules.getAccept())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_ACCEPT_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_ACCEPT);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getAccept());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
			if(!StringUtils.isEmpty(goodsRules.getInsure())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_CONFIRM_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_CONFIRM);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getInsure());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
			if(!StringUtils.isEmpty(goodsRules.getDelivery())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_DELIVERY_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_DELIVERY);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getDelivery());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
			if(!StringUtils.isEmpty(goodsRules.getPay())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_PAY_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_PAY);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getPay());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
			if(!StringUtils.isEmpty(goodsRules.getQuery())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_QUERY_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_QUERY);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getQuery());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
			if(!StringUtils.isEmpty(goodsRules.getCreate_order())){
				GoodsActionRule goodsActionRule = new GoodsActionRule();
				goodsActionRule.setAction_name(Consts.GOODS_RULES_CREATE_ORDER_NAME);
				goodsActionRule.setAction_code(Consts.GOODS_RULES_CREATE_ORDER);
				goodsActionRule.setGoods_id(goods_id);
				goodsActionRule.setStatus(goodsRules.getCreate_order());
				goodsActionRule.setDisabled(Consts.GOODS_RULES_ENABLE);
				goodsActionRule.setService_code(service_code);
				list.add(goodsActionRule);
			}
		}
		
		return list;
	}

	/**
	 * 新增订单时，增加商品动作规则
	 * @param goods_id
	 * @param service_code
	 * @return
	 */
	private List<GoodsActionRule> getRuleList(String goods_id,String service_code){
		List<GoodsActionRule> list = new ArrayList<GoodsActionRule>();
		
		GoodsActionRule goodsActionRule1 = new GoodsActionRule();
		goodsActionRule1.setAction_name(Consts.GOODS_RULES_ACCEPT_NAME);
		goodsActionRule1.setAction_code(Consts.GOODS_RULES_ACCEPT);
		goodsActionRule1.setGoods_id(goods_id);
		goodsActionRule1.setStatus("yes");
		goodsActionRule1.setDisabled(Consts.GOODS_RULES_ENABLE);
		goodsActionRule1.setService_code(service_code);
		list.add(goodsActionRule1);
		
		GoodsActionRule goodsActionRule2 = new GoodsActionRule();
		goodsActionRule2.setAction_name(Consts.GOODS_RULES_CONFIRM_NAME);
		goodsActionRule2.setAction_code(Consts.GOODS_RULES_CONFIRM);
		goodsActionRule2.setGoods_id(goods_id);
		goodsActionRule2.setStatus("no");
		goodsActionRule2.setDisabled(Consts.GOODS_RULES_ENABLE);
		goodsActionRule2.setService_code(service_code);
		list.add(goodsActionRule2);
		
		GoodsActionRule goodsActionRule3 = new GoodsActionRule();
		goodsActionRule3.setAction_name(Consts.GOODS_RULES_DELIVERY_NAME);
		goodsActionRule3.setAction_code(Consts.GOODS_RULES_DELIVERY);
		goodsActionRule3.setGoods_id(goods_id);
		goodsActionRule3.setStatus("yes");
		goodsActionRule3.setDisabled(Consts.GOODS_RULES_ENABLE);
		goodsActionRule3.setService_code(service_code);
		list.add(goodsActionRule3);
		
		GoodsActionRule goodsActionRule4 = new GoodsActionRule();
		goodsActionRule4.setAction_name(Consts.GOODS_RULES_PAY_NAME);
		goodsActionRule4.setAction_code(Consts.GOODS_RULES_PAY);
		goodsActionRule4.setGoods_id(goods_id);
		goodsActionRule4.setStatus("yes");
		goodsActionRule4.setDisabled(Consts.GOODS_RULES_ENABLE);
		goodsActionRule4.setService_code(service_code);
		list.add(goodsActionRule4);
		
		GoodsActionRule goodsActionRule5 = new GoodsActionRule();
		goodsActionRule5.setAction_name(Consts.GOODS_RULES_CREATE_ORDER_NAME);
		goodsActionRule5.setAction_code(Consts.GOODS_RULES_CREATE_ORDER);
		goodsActionRule5.setGoods_id(goods_id);
		goodsActionRule5.setStatus("yes");
		goodsActionRule5.setDisabled(Consts.GOODS_RULES_ENABLE);
		goodsActionRule5.setService_code(service_code);
		list.add(goodsActionRule5);
		
		return list;
	}

	@Override
	public List<GoodsControlStore> getGoodsStoreList(String goods_id) {
		List<GoodsControl> list = this.qryGoodsControlByGoodsId(goods_id);
		GoodsLvPrice lvPrice = this.getGoodsLvPrice(goods_id, Const.MEMBER_LV_CHINA_TELECOM_DEP);
		List<GoodsControlStore> gslist = new ArrayList<GoodsControlStore>();
		if(lvPrice==null)
			return gslist;
		for(GoodsControl gc:list){
			GoodsControlStore gs = new GoodsControlStore();
			gs.setLand_id(gc.getControl_lan_code());
			if(Const.CONTROL_CO.equals(gc.getControl_type())){
				int store = this.getGoodsStore(goods_id,gc.getControl_lan_code(), list, lvPrice);
				gs.setStore(store);
				gs.setLand_name(gc.getLocal_name());
				gs.setType(0);
			}else if(Const.CONTROL_NO.equals(gc.getControl_type()) || Const.CONTROL_PN.equals(gc.getControl_type()) || Const.CONTROL_MO.equals(gc.getControl_type())){
				int store = this.getGoodsStore(goods_id, null, list, lvPrice);
				gs.setStore(store);
				gs.setLand_name("全省");
				gs.setType(1);
			}
			gslist.add(gs);
		}
		return gslist;
	}
	
	

	@Override
	public GoodsLvPrice getGoodsLvPrice(String goods_id, String lv_id) {
		String sql = SF.goodsSql("GET_GOODS_LV_PRICE");
		List<GoodsLvPrice> list = this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, goods_id,lv_id);
		return (list==null || list.size()==0)?null:list.get(0);
	}
	
	@Override
	public List<GoodsControl> qryGoodsControlByGoodsId(String goods_id){
		String sql = SF.goodsSql("QRY_GOODS_CONTROL_BY_GOODSID");
		return this.baseDaoSupport.queryForList(sql, GoodsControl.class, goods_id);
	}
	
	private int goodsSalseCount(String goods_id, String land_code){
		String sql = SF.goodsSql("SERVICE_GOODS_SALES_COUNT");
		if(land_code!=null && !"".equals(land_code)){
			sql += " and t.lan_id=?";
			return this.baseDaoSupport.queryForInt(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP,land_code);
		}else{
			return this.baseDaoSupport.queryForInt(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP);
		}
	}
	
	private double goodsSalseMoney(String goods_id,String land_code){
		String sql = SF.orderSql("SERVICE_SALES_MONEY_SUM");
		List<Map> list = null;
		if(land_code!=null && !"".equals(land_code)){
			sql += " and t.lan_id=?";
			list = this.baseDaoSupport.queryForList(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP,land_code);
		}else{
			list = this.baseDaoSupport.queryForList(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP);
		}
		if(list!=null&&list.size()>0){
			Map map = list.get(0);
			Object p = map.get("price");
			if(p!=null)
				return Double.parseDouble(p.toString());
		}
		return 0;
	}
	
	@Override
	public int getGoodsStore(String goods_id,String land_code,List<GoodsControl> list,GoodsLvPrice lvPrice){
		if(list==null)
			list = this.qryGoodsControlByGoodsId(goods_id);
		GoodsSalesReq goodsSalesReq = new GoodsSalesReq();
		goodsSalesReq.setGoods_id(goods_id);
		
		if(list!=null && list.size()>0){
			for(GoodsControl gc:list){
				if(Const.CONTROL_NO.equals(gc.getControl_type())){
					//无控制
					return -1;
				}else if(Const.CONTROL_MO.equals(gc.getControl_type())){
					//按金额控制 全省控制需要省ID（暂时没有分省，只有江西省所以没有传省ID,如果要传需要改goodsSalseMoney方法）
					if(lvPrice==null)
						lvPrice = this.getGoodsLvPrice(goods_id, Const.MEMBER_LV_CHINA_TELECOM_DEP);
					if(lvPrice==null){
						return -1;
					}
					double d = goodsSalseMoney(goods_id,land_code);
					double price = lvPrice.getPrice();
					int store = (int)((gc.getControl_value()-d)/price);
					return store>0?store:0;
				}else if(Const.CONTROL_PN.equals(gc.getControl_type())){
					//按份额控制
					int bi = goodsSalseCount(goods_id,land_code);
					int store = (int)(gc.getControl_value()-bi);
					return store>0?store:0;
				}else if(Const.CONTROL_CO.equals(gc.getControl_type()) && Const.CONTROL_MO.equals(gc.getSub_control_type()) && land_code!=null && land_code.equals(gc.getControl_lan_code())){
					//按地市金额控制
					if(lvPrice==null)
						lvPrice = this.getGoodsLvPrice(goods_id, Const.MEMBER_LV_CHINA_TELECOM_DEP);
					if(lvPrice==null){
						return -1;
					}
					goodsSalesReq.setLan_code(land_code);
					double d = goodsSalseMoney(goods_id,land_code);
					double price = lvPrice.getPrice();
					int store = (int)((gc.getControl_value()-d)/price);
					return store>0?store:0;
				}else if(Const.CONTROL_CO.equals(gc.getControl_type()) && Const.CONTROL_PN.equals(gc.getSub_control_type()) && land_code!=null && land_code.equals(gc.getControl_lan_code())){
					//按地市分额控制
					int bi = goodsSalseCount(goods_id,land_code);
					int store = (int)(gc.getControl_value()-bi);
					return store>0?store:0;
				}
			}
		}else{
			return -1;
		}
		return -1;
	}
	
	// 根据订单展示类型
	@Override
	public GoodsTypeDTO getGoodsTypeByGoodsId(String goods_id) {
		GoodsTypeDTO goodsType=(GoodsTypeDTO)goodsCacheUtil.getGoodsTypeById(this.getGoods(goods_id).getType_id());
		return goodsType;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取插件
	 */
	public List<GoodsPlugin> getGoodsPlugin() {
		return this.baseDaoSupport.queryForList(SF.goodsSql("GET_GOODS_PLUGIN"), GoodsPlugin.class);
	}

	@Override
	public Page listAllGoods(int pageNo,int pageSize,String names){
		
		String sql = SF.goodsSql("LIST_ALL_GOODS");
		if(names!=null && !"".equals(names)){
	    	sql+=" and name like'%"+names+"%'";
	    }
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return page;
	}
	@Override
	public List listSelGoods(String[] goodsId){
		StringBuffer bufferSql = new StringBuffer();
		String sql = SF.goodsSql("LIST_SEL_GOODS");
		for (String goods_id : goodsId) {
			  bufferSql.append(goods_id);
			  bufferSql.append(",");
	        }
		  bufferSql.deleteCharAt(bufferSql.length() - 1);
		  bufferSql.append(")");
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

    //查询服务类型下的商品
	@Override
	public List ListGoodsByStypesId(String[] stypes_id) {
		// TODO Auto-generated method stub
		StringBuffer bufferSql = new StringBuffer();
		String sql = SF.goodsSql("LIST_GOODS_BY_STYPESID");
		  for (String stype_id : stypes_id) {
			  bufferSql.append(stype_id);
			  bufferSql.append(",");
	        }
		  bufferSql.deleteCharAt(bufferSql.length() - 1);
		  bufferSql.append(")");
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}
	

    //查询类别下的商品
	@Override
	public List ListGoodsByTypesId(String[] types_id) {
		StringBuffer bufferSql = new StringBuffer();
		String sql = SF.goodsSql("LIST_GOODS_BY_TYPESID");
		  for (String type_id : types_id) {
			  bufferSql.append("'"+type_id+"'");
			  bufferSql.append(",");
	          }
		  bufferSql.deleteCharAt(bufferSql.length() - 1);
		  bufferSql.append(")");
		List list = this.baseDaoSupport.queryForList(sql+bufferSql);
		return list;
	}
	
	
	@Override
	public List qryStypeList(){
		String source_from = CommonTools.getSourceForm();
		
		String sql = SF.goodsSql("QRY_STYPE_LIST");
		
		return this.baseDaoSupport.queryForList(sql, source_from);
	}
	
	
	
	@Override
	public List<Goods> qrySysServs(String serv_type,String serv_name){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(SF.goodsSql("QRY_SYS_SERVS"));
		
		if(!StringUtil.isEmpty(serv_name)){
			sql.append(" AND name like '%"+serv_name+"%'");
		}
		
		return this.baseDaoSupport.queryForList(sql.toString(), Goods.class, serv_type);
	}
	
	
	@Override
	public Goods findGoodsByGoodsSn(String sn){
		String sql = SF.goodsSql("FIND_GOODS_BY_GOODSSN");
		return (Goods)this.baseDaoSupport.queryForObject(sql, Goods.class, sn);
	}


	@Override
	public Page listGoodsByUserId(String name,int pageNo,int pageSize) {
		String sql = SF.goodsSql("LIST_GOODS_BY_USERID");
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		@SuppressWarnings("unused")
		int founder = adminUser.getFounder();
		
		if( Consts.CURR_FOUNDER_1.equals(founder+"")){// 管理员
			sql += " "; 
		}else if(Consts.CURR_FOUNDER_0.equals(founder+"")){//电信员工
			sql += " and a.staff_no='-1'"; 
		}else if(Consts.CURR_FOUNDER6==founder){
            sql = " and a.staff_no='"+adminUser.getUserid()+"' " ;
        }
		
		if (name != null && !name.equals("")) {
			sql += "  and a.name like '%" + name.trim() + "%'";
		}
		sql += " and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
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


	public GoodsCacheUtil getGoodsCacheUtil() {
		return goodsCacheUtil;
	}


	public void setGoodsCacheUtil(GoodsCacheUtil goodsCacheUtil) {
		this.goodsCacheUtil = goodsCacheUtil;
	}


	@Override
	public List<Goods> listComplexGoodsByCatId(String catId) {
		String sql = SF.goodsSql("LIST_GOODS");
		String cond = " and g.goods_id in(select goods_id from es_cat_complex where cat_id=? )";
		return this.baseDaoSupport.queryForList(sql+cond, Goods.class,Consts.GOODS_DISABLED_0,catId);
	}
	
	@Override
	public List<Goods> listGoodsByRelTag(String tag_id) {
		String sql = SF.goodsSql("GOODS_REL_BY_TAG");
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, tag_id); 
	}


	@Override
	public List<Goods> GoodsRelGoods(String a_goods_id, String rel_type) {
		String sql = SF.goodsSql("GOODS_REL_GOODS");
		return this.baseDaoSupport.queryForList(sql, Goods.class, a_goods_id, rel_type); 
	}
	
	@Override
	public Page GoodsRelGoodsPage(int pageNo,int pageSize,String a_goods_id, String rel_type, String source_from) {
		String sql = SF.goodsSql("GOODS_REL_GOODS_PAGE");
		StringBuilder whereCond = new StringBuilder();
		String[] rel_types = StringUtils.isEmpty(rel_type) ? new String[0] : rel_type.split(",");
		String relTypeStr = "";
		for(int i=0;i<rel_types.length;i++){
			relTypeStr += "'" + rel_types[i] +"',";
		}
		if(!StringUtils.isEmpty(relTypeStr)){
			whereCond.append(" and rel_type in("+relTypeStr.substring(0, relTypeStr.length()-1)+")");
		}
		if(StringUtils.isNotEmpty(a_goods_id)){
			whereCond.append(" and a_goods_id in("+a_goods_id+") and source_from='"+source_from+"' ");
		}
		sql = sql.replaceAll("#cond", whereCond.toString());
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Goods.class, source_from); 
	}

    @Override
    public Page GoodsRelGoodsPage(int pageNo, int pageSize, Object... args) {
        String sql="select * from es_goods t where goods_id in(select z_goods_id from es_goods_rel where rel_type=?  and a_goods_id in(?) and source_from=? ) and t.Sub_Stype_Id=? and t.Source_From=?";
        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Goods.class,args);
    }

    @Override
	public List<Goods> listGoodsByTerminalRelTerminal(String goods_id) {
		String sql = SF.goodsSql("TERMINAL_REL_TERMINAL");
        List<Goods> list= this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, goods_id);
		return converPath(list);
	}
	
	@Override
	public List listGoodsAttrDef(String goods_id) {
		String sql = SF.goodsSql("LIST_GOODS_ATTR_DEF");
		return this.baseDaoSupport.queryForList(sql, goods_id);
	}
	
	@Override
	public String getAcceptPrice(String goods_id,String product_id,String lv_id){
		String price = null;
		String sql = SF.goodsSql("ACCEPT_PRICE_GET");
		price = this.baseDaoSupport.queryForString(sql, goods_id,product_id,lv_id);
		return price;
	}
	
	/**
	 * 查询商品仓库库存
	 * @param goods_id
	 * @return
	 */
	@Override
	public int getGoodsStore(String goods_id){
		String sql = SF.goodsSql("GOODS_STORE_GET");
		String store = this.baseDaoSupport.queryForString(sql, goods_id);
		return store==null ? 0 : Integer.valueOf(store);
	}
	
	@Override
	public Map getGoodsMap(String goods_id) {
		return this.baseDaoSupport.queryForMap(SF.goodsSql("GET_GOODS_BY_ID"), Consts.GOODS_DISABLED_0, goods_id);
	}

	@Override
	public Map getGoodsBusinessMap(String goods_id) {
		return this.baseDaoSupport.queryForMap(SF.goodsSql("GET_GOODS_BUSINESS"), goods_id);
	}


	//1 同价位 2 同品牌 3 同类别
	@Override
	public List<Goods> qryGoodsRanking(String type, String val, String count) {
        if(StringUtils.isNotBlank(val) && val.equals("null")){
            return null;
        }
		String sql = "";
		if(type.equals("1")){
			sql = "select * from es_goods where price=? and disabled=0 and source_from = '" + ManagerUtils.getSourceFrom() + "' and rownum<1+"+count+" order by view_count";
		}else if(type.equals("2")){
			sql = "select * from es_goods where brand_id=? and disabled=0 and source_from = '" + ManagerUtils.getSourceFrom() + "' and rownum<1+"+count+" order by view_count";
		}else if(type.equals("3")){
			sql = "select * from es_goods where type_id=? and disabled=0 and source_from = '" + ManagerUtils.getSourceFrom() + "' and rownum<1+"+count+" order by view_count";
		}
        List list=this.baseDaoSupport.queryForList(sql, Goods.class, val);
		return converPath(list);
	}


	@Override
	public List listGoodsSpecs(String goods_id) {
		List<Specification> specs = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_SPEC_LIST"),Specification.class, goods_id);
		for(Specification spec : specs){
			String spec_id = spec.getSpec_id();
			List specValues = this.baseDaoSupport.queryForList(SF.goodsSql("SPEC_VALUES_LIST"), goods_id,spec_id);
			for(int i=0;i<specValues.size();i++){
				HashMap sv = (HashMap) specValues.get(i);
				if(i==0){
					sv.put("is_selected", true);//默认选中第一个规格
				}
				String spec_value_id = (String) sv.get("spec_value_id");
				List pros = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_ID_LIST"), goods_id,spec_value_id);
				processProductId(sv, pros);
			}
			spec.setValueList(specValues);
		}
		return specs;
	}
	
	public void processProductId(HashMap sv,List pros){
		StringBuilder ids = new StringBuilder();
		if(pros!=null && pros.size()>0){
			for(int i=0;i<pros.size();i++){
				HashMap pro = (HashMap) pros.get(i);
				ids.append(pro.get("product_id"));
				if(i<pros.size()-1){
					ids.append(",");
				}
			}
			sv.put("product_id", ids);
		}
	}

	@Override
	public List<HashMap> getPromotionByGoodsId(String goods_id) {
		String sql = SF.goodsSql("GOODS_COUPONS_GET_GOODS_ID");
		List<HashMap> pmts = this.baseDaoSupport.queryForList(sql, goods_id);
		return pmts;
	}


	@Override
	public HashMap getGoodsScore(String goods_id) {
		String sql = SF.goodsSql("GOODS_SCORE_GET");
		HashMap map = (HashMap) this.baseDaoSupport.queryForMap(sql, goods_id);
		return map;
	}


	@Override
	public HashMap getGoodsDetail(String goods_id) {
		String sql = SF.goodsSql("GOODS_DETAIL_GET");
		HashMap goods = (HashMap) this.baseDaoSupport.queryForMap(sql, goods_id);
		return goods;
	}


	@Override
	public boolean checkGoodsHasSpec(String goods_id) {
		String hasSpec = this.baseDaoSupport.queryForString(SF.goodsSql("GOODS_SPEC_CHECK"), goods_id);
		if("1".equals(hasSpec)){
			return true;
		}
		return false;
	}

    @Override
    public List<Product> getProductByGoodsId(String goods_id) {
        String sql="SELECT T.* FROM es_product T WHERE t.goods_id=? AND t.source_from=?";
        return this.daoSupport.queryForList(sql,Product.class,goods_id,ManagerUtils.getSourceFrom());
    }

    public List<Goods> converPath(List<Goods> list){
        List<Goods> result=new ArrayList<Goods>();
        for(Goods goods: list){
            if(org.apache.commons.lang3.StringUtils.isNotBlank(goods.getImage_default())){
                goods.setImage_default(UploadUtils.replacePath(goods.getImage_default()));
            }
            if(org.apache.commons.lang3.StringUtils.isNotBlank(goods.getImage_file())){
                goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
            }
            result.add(goods);
        }
        return result;
    }


	@Override
	public Page searchGoodsByType(String type_id, String price,String sub_stype_id,
			String source_from,int pageNo,int pageSize) {
		String sql = SF.goodsSql("GOODS_PAGE_LIST_BY_TYPE");
		if(!StringUtils.isEmpty(price)){
			sql = sql + " and price>="+Float.valueOf(price);
		}
		
		if(StringUtils.isNotEmpty(sub_stype_id)){
			sql = sql + " and sub_stype_id="+sub_stype_id;
		}
		
		sql=sql+" order by price ";
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Goods.class, type_id,source_from);
		return page;
	}
	
	
	@Override
	public Page queryGoodsByIdsAndName(String ids,String name,int page,int pageSize){
		
		StringBuffer sql = new StringBuffer(SF.goodsSql("GOODS_PAGE_LIST_BY_IDS"));
		StringBuffer cSql = new StringBuffer(SF.goodsSql("GOODS_PAGE_LIST_BY_IDS_COUNT"));
		StringBuffer where = new StringBuffer();
		
		if(StringUtils.isNotEmpty(ids)){
			where.append(" AND goods_id in ("+ids+")");
		}
		if(StringUtils.isNotEmpty(name)){
			where.append(" AND search_key like '%"+name+"%'");
		}
		
		where.append("ORDER BY create_time DESC");
		
		return this.baseDaoSupport.queryForCPage(sql.append(where).toString(), page, pageSize, 
				Goods.class, cSql.append(where).toString());
	}
	
	
	@Override
	public Page listProRelGoods(String product_id,int page,int pageSize){
		String sql = SF.goodsSql("PRODUCT_REL_GOODS_LIST");
		Page goods = this.baseDaoSupport.queryForPage(sql, page, pageSize, product_id);
		List list = goods.getResult();
		for(int i=0;i<list.size();i++){
			HashMap good = (HashMap) list.get(i);
			String goods_id = (String) good.get("goods_id");
			List<Map> orgs = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_GET"), goods_id);
			good.put("org_name", getOrgName(orgs));
		}
		return goods;
	}
	
	private String getOrgName(List<Map> orgs){
		StringBuilder sb = new StringBuilder();
		for(Map org : orgs){
			sb.append(org.get("org_name")).append(",");
		}
		return sb.length()>0?sb.substring(0, sb.length()-1):"";
	}


	@Override
	public List listProducts(String service_id) {
		return this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_LIST"), service_id);
	}

	@Override
	public Page listOfferChange(String goodsType, int pageNo, int pageSize, String sub_stype_id) {
		//String sql = SF.goodsSql("OFFER_CHANGE_LIST");
		String lan_id = ManagerUtils.getLanId();
		String sql ="select goods.goods_id goods_id,goods.name name,goods.crm_offer_id crm_offer_id,goods.p1 p1,goods.p2 p2,goods.p3 p3,goods.p4 p4,goods.p5 p5,goods.p6 p6,goods.p7 p7,goods.price price,goods.create_time create_time,goods.last_modify end_time,goods.intro specs,p.product_id product_id,p.name product_name from es_goods goods  join es_product p on goods.goods_id = p.goods_id  where goods.type_id=? and goods.source_from='"+ManagerUtils.getSourceFrom()+"' and p.source_from='"+ManagerUtils.getSourceFrom()+"'";

		if (!StringUtils.isEmpty(sub_stype_id)) {
			sql += " and goods.sub_stype_id=" + sub_stype_id;
		}
		
//		if(!StringUtils.isEmpty(lan_id)){
//			sql += " and area.lan_id="+lan_id ;
//		}
		Page datas = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,String> map = new HashMap<String, String>();   //for 掌厅
				try{
					map.put("goods_id", rs.getObject("goods_id").toString());
					map.put("name", rs.getObject("name").toString());
					map.put("crm_offer_id", rs.getObject("crm_offer_id").toString());
					map.put("p1", rs.getObject("p1").toString());
					map.put("p2", rs.getObject("p2").toString());
					map.put("p3", rs.getObject("p3").toString());
					map.put("p4", rs.getObject("p4").toString());
					map.put("p5", rs.getObject("p5").toString());
					map.put("p6", rs.getObject("p6").toString());
					map.put("p7", rs.getObject("p7").toString());
					map.put("price", rs.getObject("price").toString());
					map.put("create_time", rs.getObject("create_time").toString());
					map.put("end_time", rs.getObject("end_time").toString());
					map.put("specs", rs.getObject("specs").toString());
					map.put("product_id", rs.getObject("product_id").toString());
					map.put("product_name", rs.getObject("product_name").toString());
				//	map.put("lan_id", rs.getObject("lan_id").toString());
				}catch(Exception e){
					
				}
				return map;
			}
		}, goodsType);

		return datas;
	}
	
	@Override
	public int modifyStatus(String id, String service_code, String status_new) {
		
		String sql = "";
		List paramLst = new ArrayList();
		
		if (Consts.SERVICE_CODE_CO_SHANGPIN.equals(service_code)) {
			//获取旧状态---曾憲廉
			List<Map>  list = baseDaoSupport.queryForList("select * from ES_GOODS_CO where batch_id = ?", id);
			for(Map co : list){
				if((co.get("status").equals(Consts.PUBLISH_2)) || (co.get("status").equals(Consts.PUBLISH_3))&&status_new.equals(Consts.PUBLISH_1)){
					paramLst = new ArrayList();
					paramLst.add(status_new);
					paramLst.add(id);
					paramLst.add(co.get("org_id"));
					sql = SF.goodsSql("GOODS_CO_MODIFY_STATUS_BY_ID");  //商品
					this.baseDaoSupport.execute(sql, paramLst.toArray());
				}
			}
		} else if (Consts.SERVICE_CODE_CO_HUOPIN.equals(service_code)) {
			//获取旧状态---曾憲廉
			List<Map>  list = baseDaoSupport.queryForList("select * from ES_PRODUCT_CO where batch_id = ?", id);
			for(Map co : list){
				if((co.get("status").equals(Consts.PUBLISH_2)) || (co.get("status").equals(Consts.PUBLISH_3))&&status_new.equals(Consts.PUBLISH_1)){
					paramLst = new ArrayList();
					paramLst.add(status_new);
					paramLst.add(id);
					paramLst.add(co.get("org_id"));
					sql = SF.goodsSql("PRODUCT_CO_MODIFY_STATUS_BY_ID");  //货品
					this.baseDaoSupport.execute(sql, paramLst.toArray());
				}
			}
		} else if (Consts.SERVICE_CODE_CO_HAOMA.equals(service_code)) {
			paramLst.add(status_new);
			paramLst.add(id);
			sql = SF.goodsSql("NO_CO_MODIFY_STATUS_BY_ID");  //号码
			this.baseDaoSupport.execute(sql, paramLst.toArray());
		} else if (Consts.SERVICE_CODE_CO_HUODONG.equals(service_code)) {
			//只有在旧状态是发布中或者失败且新状态是成功才修改---zengixnalian
			List<Map>  list = baseDaoSupport.queryForList("select * from es_activity_co where batch_id = ?", id);
			for(Map co : list){
				if((co.get("status").equals(Consts.PUBLISH_STATE_2)) || (co.get("status").equals(Consts.PUBLISH_STATE_3))&&status_new.equals(Consts.PUBLISH_STATE_1)){
					paramLst = new ArrayList();
					paramLst.add(status_new.contains("-")?status_new.split("-")[1]:status_new);
					paramLst.add(id);
					sql = SF.goodsSql("ACTIVITY_CO_MODIFY_STATUS_BY_ID");  //活动
					this.baseDaoSupport.execute(sql, paramLst.toArray());
				}
			}
//			if(status_new.contains("-")){//失败次数为3次就把记录删除---zengxianlian
//				//暂时不能删除,还是改状态---zengxianlian
//				sql = SF.goodsSql("ACTIVITY_CO_MODIFY_STATUS_BY_ID");  //活动
//				paramLst.add(status_new.split("-")[1]);
//				paramLst.add(id);
//			}else{
//				sql = SF.goodsSql("ACTIVITY_CO_MODIFY_STATUS_BY_ID");  //活动
//				paramLst.add(status_new);
//				paramLst.add(id);
//			}
			//this.baseDaoSupport.execute(sql, paramLst.toArray());
//			String[] arr = status_new.split("-"); 
//			if(arr.length<3){
//				sql = SF.goodsSql("ACTIVITY_CO_MODIFY_STATUS_BY_ID");  //活动
//				paramLst.add(arr[0]);
//				paramLst.add(id);
//				paramLst.add(arr[1]);
//			}else{
//				sql = "delete es_activity_co   where 1=1 and batch_id = ? and org_id=?";
//				paramLst.add(id);
//				paramLst.add(arr[1]);
//			}
		}
		
		return 1;
	}
	
	
	@Override
	public Map<String,String> getGoodsInfo(String goods_id,String sn,String package_id){
		List pList = new ArrayList();
		String sql = sql = SF.goodsSql("GOODS_INFO_GET_0");
		if(StringUtils.isNotEmpty(goods_id)){
			sql += " and g.goods_id=? ";
			pList.add(goods_id);
		} else {
			if (StringUtils.isNotEmpty(sn)) {
				sql += " and pkg.sn=? ";
				pList.add(sn);
			} else {
				sql += " and pkg.sn is null ";
			}
			
			if (StringUtils.isNotEmpty(package_id)) {
				sql += " and pkg.p_code=? ";
				pList.add(package_id);
			} else {
				sql += " and pkg.p_code is null ";
			}
		} 
		
		List<Map> datalist = this.baseDaoSupport.queryForList(sql, pList.toArray());
		Map data = null;
		if(datalist!=null && datalist.size()>0)
			data = datalist.get(0);
		
		Map<String,String> goods = new HashMap<String,String>();
		if(data==null)
			return goods;
		
		//判断是否有赠品
		data.put("isgifts", "0");
		String isGifts = this.baseDaoSupport.queryForString(SF.goodsSql("HAVE_GIFTS"), 
				ManagerUtils.getSourceFrom(), "10001", data.get("goods_id").toString());
		if ("1".equals(isGifts)) {
			data.put("isgifts", isGifts);
		}
		
		//获取合约类型
		String active_type = getActiveType(data.get("goods_id").toString());
		data.put("ative_type", active_type);
		
		//获取型号信息
		Map<String,String> model = getProductModel(data.get("goods_id").toString());
		if(!model.isEmpty()){
			data.put("specification_code", model.get("specification_code"));
			data.put("specification_name", model.get("specification_name"));
		}
		//是否iphone套餐
		String is_iphone_plan = getIsIphonePlan(data.get("goods_id").toString());
		data.put("is_iphone_plan", is_iphone_plan);
		
		//获取颜色
		Map<String,String> color = getColor(data.get("goods_id").toString());
		if(!color.isEmpty()){
			data.put("color_code", color.get("color_code"));
			data.put("color_name", color.get("color_name"));
		}
		
		//4G自由组合套餐
		String customerization_offer_str = "";
		if (Consts.GOODS_CAT_ID_4G_ZYZH.equalsIgnoreCase((String)data.get("product_type"))) {
			customerization_offer_str = this.getCustomeriztnOff(goods_id);
		}
		data.put("customerization_offer_str", customerization_offer_str);
		
		
 		String params = (String) data.get("params");
		ParamGroup[] paramArs = GoodsTypeUtil.converFormString( params);// 处理参数
		if(paramArs!=null){
			for(ParamGroup group : paramArs){
				List list = group.getParamList();
				for(int i=0;i<list.size();i++){
					GoodsParam param = (GoodsParam) list.get(i);
					data.put(param.getEname(), param.getValue());
				}
			}
		}
		
		if(data!=null && !data.isEmpty()){
			Iterator it = data.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				goods.put(key, String.valueOf(data.get(key)));
			}
		}
		return goods;
	}

	@Override
	public List listAllComplex(String goods_id) {
		// TODO Auto-generated method stub
		String sql = SF.goodsSql("LIST_ALL_COMPLEX");
		
		List<Map> list = this.daoSupport.queryForList(sql, goods_id, goods_id);
		for(Map map :list){
			String image  =(String) map.get("image");
			image = UploadUtil.replacePath(image);
			map.put("image", image);
		}
		return list;
	}
	
	@Override
	public Page searchProductsECS(Map params,int page,int pageSize) {
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String typeId = ManagerUtils.getStrValue(params, "type_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String bssCode = ManagerUtils.getStrValue(params, "bssCode");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		
		String sql = SF.goodsSql("SEARCH_PRODUCTS_6_1");
		sql += " and g.disabled = '" + Consts.GOODS_DISABLED_0 + "'";  //有效记录
		sql += " and p.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}
		
		if (order == null | "".equals(order)) {
			order = "create_time desc";
		}
		
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
//			sql += " and m.model_code='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		if(StringUtils.isNotEmpty(typeId)){
			sql += "  and g.type_id = '" + typeId+"'";
		}
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
//		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
//			String tagidstr = StringUtil.arrayToString(tagid, ",");
//			sql += " and g.goods_id in(select rel_id from "
//					+ this.getTableName("tag_rel") + " tg where tag_id in("
//					+ tagidstr + ") and g.source_from = tg.source_from)";
//		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and p.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and p.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + market_enable;
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_product_co co where p.product_id=co.product_id))";
			}else{
				sql += " and exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+publish_state+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct epc.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}else{
			 //countSql = "select count(distinct p.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}
		
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
		}
		if(order.contains("sku")){
			sql += " order by p." + order;
		}else{
			sql += " order by g." + order;
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,Goods.class,true,new String[]{});
		List list = webpage.getResult();
		for(int i=0;i<list.size();i++){
			Goods good = (Goods) list.get(i);
			String goods_id = good.getGoods_id();
			String product_id = good.getProduct_id();
			//List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET"), product_id);
			//by zengxianlian
			List<Map> coList = null;
			if(StringUtils.isNotEmpty(publish_state)){
				coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET_STATUS"), product_id,publish_state);
			}else{
				coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET_STATUS"), product_id, Consts.PUBLISH_1);
				if(coList.size()<1){
					coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET_STATUS"), product_id,Consts.PUBLISH_3);
					if(coList.size()<1){
						coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET_STATUS"), product_id,Consts.PUBLISH_2);
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
					
				}
			}
			good.setAgent_name(org_names);
			good.setPublish_status(publish_status);
		}
		return webpage;
	}

	@Override
	public Page searchTags(int pageNo,int pageSize,String tag_name){
		String sql = SF.goodsSql("GOODS_SELECT_TAGS");
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotBlank(tag_name)){
			sql += " and a.tag_name like ?";
			params.add("%"+tag_name+"%");
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,true,params.toArray(new String[]{}));
		return webpage;
	}
	
	@Override
	public String productsBatchPublish(Map params,int page,int pageSize){
		String sql = SF.goodsSql("SEARCH_PRODUCT_BATCH_PUBLISH");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		String org_ids = ManagerUtils.getStrValue(params, "org_ids");
		Integer batchNum = Integer.valueOf(ManagerUtils.getStrValue(params, "batchNum"));
		
		if (StringUtils.isNotEmpty(sku)) {
			sql += "  and p.sku ='"+sku+"' ";
		}
		if(brandId!=null && !"".equals(brandId)){
			sql += "  and b.brand_id ='"+brandId+"' ";
		}
		if(type!=null && !"".equals(type)){
			sql += "  and g.type ='"+type+"' ";
		}
		if (name != null && !"".equals(name)) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		if(catid!=null && !"".equals(catid)){
			sql += "  and c.cat_path like '%|" + catid.trim()+ "%' ";
		}
		if(model_code!=null && !"".equals(model_code)){
//			sql += "  and m.model_code ='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !"".equals(start_date)){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !"".equals(end_date)){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		sql += " and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		sql +=" order by g.create_time desc ";
		String goods_id_strs = "";
		batchNum = batchNum>5000?5000:batchNum;
		Page p = baseDaoSupport.qryForPage(sql, 1, batchNum, Goods.class);
		List<Goods> list = p.getResult();
		int size = list.size();
		if(size<1){
			return "未查询到要发布的货品！";
		}
		for(int i=0;i<size;i++){
			goods_id_strs +=list.get(i).getGoods_id().toString()+",";
		}
		HashMap map = new HashMap();
		if(!"".equals(goods_id_strs)){
			goods_id_strs=goods_id_strs.substring(0, goods_id_strs.length()-1);
		}
		map.put("productos", goods_id_strs);
		productoM.liberacion(org_ids, map);
		String msg = "成功发布"+size+"个货品！";
		return msg;
	}
	
	@Override
	public String goodsBatchPublish(Map params,int page,int pageSize){
		String sql = SF.goodsSql("SEARCH_GOODS_BATCH_PUBLISH");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		String org_ids = ManagerUtils.getStrValue(params, "org_ids");
		Integer batchNum = Integer.valueOf(ManagerUtils.getStrValue(params, "batchNum"));
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		if (StringUtils.isNotEmpty(sku)) {
			sql += "  and g.sku ='"+sku+"' ";
		}
		if(brandId!=null && !"".equals(brandId)){
			sql += "  and b.brand_id ='"+brandId+"' ";
		}
		if(type!=null && !"".equals(type)){
			sql += "  and g.type ='"+type+"' ";
		}
		if (name != null && !"".equals(name)) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		if(catid!=null && !"".equals(catid)){
			sql += "  and c.cat_path like '%|" + catid.trim()+ "%' ";
		}
		if(model_code!=null && !"".equals(model_code)){
			//sql += "  and m.model_code ='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !"".equals(start_date)){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !"".equals(end_date)){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		//未发布
		if (Consts.PUBLISH_0.toString().equals(publish_state)) {
			sql += " and not exists(select 1 from es_goods_co co where co.goods_id = g.goods_id)";
		}
		
		//已发布
		if (Consts.PUBLISH_1.toString().equals(publish_state)) {
			sql += " and exists(select 1 from es_goods_co co where co.goods_id = g.goods_id)";
		}
		
		sql += " and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		String goods_id_strs = "";
		batchNum = batchNum>5000?5000:batchNum;
		Page p = baseDaoSupport.qryForPage(sql, 1, batchNum, Goods.class);
		List<Goods> list = p.getResult();
		int size = list.size();
		if(size<1){
			return "未查询到要发布的商品！";
		}
		for(int i=0;i<size;i++){
			goods_id_strs +=list.get(i).getGoods_id().toString()+",";
		}
		HashMap map = new HashMap();
		if(!"".equals(goods_id_strs)){
			goods_id_strs=goods_id_strs.substring(0, goods_id_strs.length()-1);
		}
		map.put("esgoodscos", goods_id_strs);
		esGoodsM.liberacion(org_ids,map);
		String msg = "成功发布"+size+"个商品！";
		return msg;
	}
	
	@Override
	public Page searchGoodsECS(Map params, int page,int pageSize) {
		
		String sql = "";
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		
		//add by zhengchuiliu
		String import_username = ManagerUtils.getStrValue(params, "import_username"); //导入人
		String previous_date = ManagerUtils.getStrValue(params, "previous_date");
		String last_update_date = ManagerUtils.getStrValue(params, "last_update_date");
		
		//zengxianlian
		String actCode =  ManagerUtils.getStrValue(params, "actCode");
		
		String[] tagid = (String[]) params.get("tagids");
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		@SuppressWarnings("unused")
		int founder = adminUser.getFounder();
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}

		//if (ManagerUtils.isProvStaff()) {// 管理员或电信员工
		if(Consts.CURR_FOUNDER_0.equals(founder+"")|| Consts.CURR_FOUNDER_1.equals(founder+"")){
			sql = SF.goodsSql("SEARCH_GOODS_0_2");
			if(null != actCode && !"".equals(actCode)){
				sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
			}
			sql+=" where 1=1  and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";
			
		}else if(Consts.CURR_FOUNDER6==founder){
			
            sql = SF.goodsSql("SEARCH_GOODS_6_1") ;
            if(null != actCode && !"".equals(actCode)){
				sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
			}
            sql+=" where 1=1 and g.staff_no='"+adminUser.getUserid()+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "' " ;

        }else { //供货商等
			sql = SF.goodsSql("SEARCH_GOODS_OTHER_1");
			 if(null != actCode && !"".equals(actCode)){
					sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
				}	
			sql+="where  g.disabled = '" + Consts.GOODS_DISABLED_0 + "'  and (g.staff_no='"+adminUser.getParuserid()+"' or g.staff_no='"+adminUser.getUserid()+"') and g.source_from='"+ManagerUtils.getSourceFrom()+"'" ;// add by wui 父级管理员
		}
		if (order == null | "".equals(order)) {
			order = "create_time desc";
		}
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
			//sql += "  and m.model_code ='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		//add by ty
		if(previous_date!=null && !previous_date.equals("")){
			sql += " and g.last_modify >= to_date('"+previous_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(last_update_date!=null && !last_update_date.equals("")){
			sql += " and g.last_modify <= to_date('"+last_update_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and g.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " tg where tag_id in("
					+ tagidstr + ") and g.source_from = tg.source_from)";
		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and g.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + Integer.valueOf(market_enable);
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_goods_co co where g.goods_id=co.goods_id))";
			}else{
				sql += " and exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct egc.goods_id) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}else{
			 //countSql = "select count(*) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}
		
		
		//zhengchuiliu 添加导入人查询
		if(!StringUtils.isEmpty(import_username)){
			String goods_log_sql = SF.goodsSql("GOODS_IMPORT_LOGS_BY_OPERNAME");
			int first_row = (page-1)*pageSize +1;
			int last_row = page * pageSize;
			List<Map> logs_list = this.baseDaoSupport.queryForList(goods_log_sql, new String[]{"%"+import_username+"%", last_row+"", first_row+""});
			String goodids = "'";
			for(int i = 0; i < logs_list.size(); i++){
				Map map = logs_list.get(i);
				String productCode = (String) map.get("product_code");
				String atvCode = (String) map.get("atv_code");
				String atvMonths = (String) map.get("atv_months");
				String productName = (String) map.get("product_name");
				String modelCode = (String) map.get("model_code");
				String colorCode = (String) map.get("color_code");
				
				String contractNet = "3G";
				String isIphone = "0";
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("4g")>=0){
					contractNet = "4G";
				}
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("iphone")>=0){
					isIphone = "1";
				}
				//查询套餐
				String offers_sql = SF.goodsSql("OFFER_GET_BY_ESSCODE");
				offers_sql += " and a.params like '%"+productCode+"%'";
				List<Map> offers = this.baseDaoSupport.queryForList(offers_sql);
				String offers_product_id = "";
				if(offers.size()>0){
					Map m = offers.get(0);
					offers_product_id = (String) m.get("product_id");
				}
				
				//查询合约计划
				String contracts_sql = SF.goodsSql("CONTRACT_GET_BY_SPEC");
				List<Map> contracts = this.baseDaoSupport.queryForList(contracts_sql, new String[]{atvCode, atvMonths, contractNet, isIphone});
				String contracts_product_id = "";
				if(contracts.size()>0){
					Map m = contracts.get(0);
					contracts_product_id = (String) m.get("product_id");
				}
				
				//查询终端
				String terminals_sql = SF.goodsSql("TERMINAL_BY_MODELCODE_COLOR");
				List<Map> terminals = this.baseDaoSupport.queryForList(terminals_sql, modelCode, colorCode);
				String terminals_product_id = "";
				if(terminals.size()>0){
					Map m = terminals.get(0);
					terminals_product_id = (String) m.get("product_id");
				}
				
				//查询商品
				String goods_sql = SF.goodsSql("CONTRACT_MACHINE");
				List<Map> goodsList = this.baseDaoSupport.queryForList(goods_sql, new String[]{terminals_product_id, contracts_product_id, offers_product_id});
				for(int j=0; j<goodsList.size(); j++){
					Map m = goodsList.get(j);
					String goodsId = (String) m.get("goods_id");
					goodids = goodids + goodsId + "','";
				}
			}
			goodids += "'";
			sql+=" and g.goods_id in ("+ goodids +")";
		}
		
		sql +=statsWhereCond.toString();
		sql += " order by g." + order;
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, Goods.class, true,new String[]{});
		List list = webpage.getResult();
		for(int i=0;i<list.size();i++){
			Goods good = (Goods) list.get(i);
			String goods_id = good.getGoods_id();
			//List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_GET"), goods_id);
			//by zengxianlian
			List<Map> coList = null;
			if(StringUtils.isNotEmpty(publish_state)){
				coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_ZJ_GET_STATUS"), goods_id,publish_state);
			}else{
				coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_ZJ_GET_STATUS"), goods_id, Consts.PUBLISH_1);
				if(coList.size()<1){
					coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_ZJ_GET_STATUS"), goods_id, Consts.PUBLISH_3);
					if(coList.size()<1){
						coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_ZJ_GET_STATUS"), goods_id, Consts.PUBLISH_2);
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
				}
			}
			good.setAgent_name(org_names);
			good.setPublish_status(publish_status);
		}
		return webpage;
	}
	
	@Override
	public Page searchGoodsTaobaoECS(String goods_name, String goods_cat, Integer market_enable, String order, 
			int page, int pageSize) {
		
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		
		String sql = SF.goodsSql("SEARCH_GOODS_TAOBAO_ECS");
		List paramLst = new ArrayList();
			
		if (order == null) {
			order = "create_time desc";
		}
		
		if (goods_name != null && !goods_name.equals("")) {
			sql += "  and upper(g.name) like '%" + goods_name.trim().toUpperCase() + "%'";
		}
		
//		if (p_code != null && !p_code.equals("")) {
//			sql += "   and p.p_code = ?";
//			paramLst.add(p_code);
//		}
		if(null!=goods_cat && !"".equals(goods_cat))
		{
			sql+=" and g.cat_id=?";
			paramLst.add(goods_cat);
		}
		
		if (market_enable != null && market_enable!=2) {
			sql += "  and g.market_enable = ?";
			paramLst.add(market_enable);
		}

		
		String countSql = "select count(*) from " +sql.substring(sql.lastIndexOf("from es_goods g") + 4);
		
		sql += " order by g." + order;
		
		Page webpage = this.baseDaoSupport.queryForCPage(sql, page, pageSize, Goods.class, countSql, paramLst.toArray());

		return webpage;
	}
	
	/**
	 * 查询商品操作日志
	 */
	@Override
	public Page searchGoodsOperLog(Map params, int page,int pageSize) {
		
		String sql = "";
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		
		//add by zhengchuiliu
		String import_username = ManagerUtils.getStrValue(params, "import_username"); //导入人
		String previous_date = ManagerUtils.getStrValue(params, "previous_date");
		String last_update_date = ManagerUtils.getStrValue(params, "last_update_date");
		
		String oper_name = ManagerUtils.getStrValue(params, "oper_name");
		
		String[] tagid = (String[]) params.get("tagids");
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		@SuppressWarnings("unused")
		int founder = adminUser.getFounder();
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}
		
		//if (ManagerUtils.isProvStaff()) {// 管理员或电信员工
		if(Consts.CURR_FOUNDER_0.equals(founder+"")|| Consts.CURR_FOUNDER_1.equals(founder+"")){
			sql = SF.goodsSql("SEARCH_GOODS_0_2_OPER_LOG");
			
		}else if(Consts.CURR_FOUNDER6==founder){
			
            sql = SF.goodsSql("SEARCH_GOODS_6_1") + " where 1=1 and g.staff_no='"+adminUser.getUserid()+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "' " ;

        }else { //供货商等
			sql = SF.goodsSql("SEARCH_GOODS_OTHER_1") +
				   "and (g.staff_no='"+adminUser.getParuserid()+"' or g.staff_no='"+adminUser.getUserid()+"') and g.source_from='"+ManagerUtils.getSourceFrom()+"'" ;// add by wui 父级管理员
		}
		if (order == null | "".equals(order)) {
			//使用create_time排序不正确....改成用oper_date排序---zengxianlian
			//order = "create_time desc";
			order = "oper_date desc";
		}
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
			//sql += "  and m.model_code ='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		//add by ty
		if(previous_date!=null && !previous_date.equals("")){
			sql += " and g.last_modify >= to_date('"+previous_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(last_update_date!=null && !last_update_date.equals("")){
			sql += " and g.last_modify <= to_date('"+last_update_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(oper_name!=null && !oper_name.equals("")){
			sql += " and g.oper_name like '%"+oper_name+"%' ";
		}
		

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and g.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " tg where tag_id in("
					+ tagidstr + ") and g.source_from = tg.source_from)";
		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and g.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + Integer.valueOf(market_enable);
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_goods_co co where g.goods_id=co.goods_id))";
			}else{
				sql += " and exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct egc.goods_id) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}else{
			 //countSql = "select count(*) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}
		
		
		//zhengchuiliu 添加导入人查询
		if(!StringUtils.isEmpty(import_username)){
			String goods_log_sql = SF.goodsSql("GOODS_IMPORT_LOGS_BY_OPERNAME");
			int first_row = (page-1)*pageSize +1;
			int last_row = page * pageSize;
			List<Map> logs_list = this.baseDaoSupport.queryForList(goods_log_sql, new String[]{"%"+import_username+"%", last_row+"", first_row+""});
			String goodids = "'";
			for(int i = 0; i < logs_list.size(); i++){
				Map map = logs_list.get(i);
				String productCode = (String) map.get("product_code");
				String atvCode = (String) map.get("atv_code");
				String atvMonths = (String) map.get("atv_months");
				String productName = (String) map.get("product_name");
				String modelCode = (String) map.get("model_code");
				String colorCode = (String) map.get("color_code");
				
				String contractNet = "3G";
				String isIphone = "0";
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("4g")>=0){
					contractNet = "4G";
				}
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("iphone")>=0){
					isIphone = "1";
				}
				//查询套餐
				String offers_sql = SF.goodsSql("OFFER_GET_BY_ESSCODE");
				offers_sql += " and a.params like '%"+productCode+"%'";
				List<Map> offers = this.baseDaoSupport.queryForList(offers_sql);
				String offers_product_id = "";
				if(offers.size()>0){
					Map m = offers.get(0);
					offers_product_id = (String) m.get("product_id");
				}
				
				//查询合约计划
				String contracts_sql = SF.goodsSql("CONTRACT_GET_BY_SPEC");
				List<Map> contracts = this.baseDaoSupport.queryForList(contracts_sql, new String[]{atvCode, atvMonths, contractNet, isIphone});
				String contracts_product_id = "";
				if(contracts.size()>0){
					Map m = contracts.get(0);
					contracts_product_id = (String) m.get("product_id");
				}
				
				//查询终端
				String terminals_sql = SF.goodsSql("TERMINAL_BY_MODELCODE_COLOR");
				List<Map> terminals = this.baseDaoSupport.queryForList(terminals_sql, modelCode, colorCode);
				String terminals_product_id = "";
				if(terminals.size()>0){
					Map m = terminals.get(0);
					terminals_product_id = (String) m.get("product_id");
				}
				
				//查询商品
				String goods_sql = SF.goodsSql("CONTRACT_MACHINE");
				List<Map> goodsList = this.baseDaoSupport.queryForList(goods_sql, new String[]{terminals_product_id, contracts_product_id, offers_product_id});
				for(int j=0; j<goodsList.size(); j++){
					Map m = goodsList.get(j);
					String goodsId = (String) m.get("goods_id");
					goodids = goodids + goodsId + "','";
				}
			}
			goodids += "'";
			sql+=" and g.goods_id in ("+ goodids +")";
		}
		
		sql +=statsWhereCond.toString();
		sql += " order by g." + order;
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, Goods.class, true,new String[]{});
		List list = webpage.getResult();
		for(int i=0;i<list.size();i++){
			Goods good = (Goods) list.get(i);
			String goods_id = good.getGoods_id();
			List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_GET"), goods_id);
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
					
				}
			}
			good.setAgent_name(org_names);
			good.setPublish_status(publish_status);
		}
		return webpage;
	}
	 /*
     * 通过name得到商品分类id
     */
    @Override
	public String getCatID(String catName){//
    	if (catName == null || "".equals(catName)){
    		return "";
    	}
    	String sql="select t.cat_id from es_goods_cat t where t.name = ?";
    	return this.daoSupport.queryForString(sql, new String[]{catName});
    	
    }
    /*
     * 通过name得到商品类型id
     */
    @Override
	public String getTypeID(String typeName){
    	if (typeName == null || "".equals(typeName)){
    		return "";
    	}
    	String sql="select t.type_id  from es_goods_type t where t.name = ?";
    	return this.daoSupport.queryForString(sql, new String[]{typeName});
    	
    }
    /*
     * 通过name得到品牌id
     */
    @Override
	public String getBrandID(String brandName){
    	if (brandName == null || "".equals(brandName)){
    		return "";
    	}
    	String sql="select t.brand_id from es_brand t where t.name = ? and rownum=1";
    	return this.daoSupport.queryForString(sql, new String[]{brandName});   	
    }
    /*
     * 通过name得到品牌id
     */
    @Override
	public String getStypeID(String stypeName){
    	if (stypeName == null || "".equals(stypeName)){
    		return "";
    	}
    	String sql="select t.stype_id from es_goods_stype t where t.name = ?";
    	return this.daoSupport.queryForString(sql, new String[]{stypeName});   	
    }
    /*
     * 通过name得到品牌id
     */
    @Override
	public String getSubStypeID(String subStypeName,String parentID){
    	if (subStypeName == null || "".equals(subStypeName)|| parentID == null || "".equals(parentID)){
    		return "";
    	}
    	String sql="select t.stype_id from es_goods_stype t where t.name = ? and parent_id=?";
    	return this.daoSupport.queryForString(sql, new String[]{subStypeName,parentID});   	
    }
    /*
     * 添加商品的会员价格等信息入表
     */
    @Override
	public void addGoodsPrice(Goods goods,String selledObject,String normal_price,String silver_price,String gold_price){
    	String[] roleType=null;
    	String goodsId=getGoodsId(goods.getSn());
    	String price=goods.getPrice().toString();
    	List<GoodsLvPrice> goodsPriceList = new ArrayList<GoodsLvPrice>();
		if (selledObject!=null && !"".equals(selledObject)){
			roleType=selledObject.split(",");
			for(int i=0;i<roleType.length;i++){
				GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
				goodsLvPrice.setGoodsid(goodsId);
				if ("0".equals(roleType[i])){
					goodsLvPrice.setPrice(Double.parseDouble(normal_price)*Double.parseDouble(price));
					goodsLvPrice.setLvid("0");
					goodsLvPrice.setLv_discount(Float.valueOf(normal_price));
				}else if ("1".equals(roleType[i])){
					goodsLvPrice.setPrice(Double.parseDouble(silver_price)*Double.parseDouble(price));
					goodsLvPrice.setLvid("1");
					goodsLvPrice.setLv_discount(Float.valueOf(silver_price));
				}else if ("2".equals(roleType[i])){
					goodsLvPrice.setPrice(Double.parseDouble(gold_price)*Double.parseDouble(price));
					goodsLvPrice.setLvid("2");
					goodsLvPrice.setLv_discount(Float.valueOf(gold_price));
				}
				goodsPriceList.add(goodsLvPrice);
			}
			save(goodsPriceList);
			addpriv(goodsPriceList);
		}
    	
    }
    /*
     * 更新goods表的params参数
     */
    @Override
	public void editGoods(String detail,String goodsId){
    	String sql="update es_goods set params=? where goods_id=? ";
    	if (detail != null){
//    		Map map=new HashMap();
//    		String param="["+JSONObject.fromObject(detail).toString()+"]";
//    		map.put("params", param);
//    		map.put("goods_id", goodsId);
    		this.daoSupport.execute(sql, new String[]{detail,goodsId});
//    		this.daoSupport.update(sql, map);
    	}
    	
    }
    /*
     * 通过商品编码查找商品id
     * 
     */
    @Override
	public String getGoodsId(String sn){
    	if (sn == null || "".equals(sn)){
    		return "";
    	}
    	String sql="select t.goods_id  from es_goods t where t.sn = ?";
    	return this.daoSupport.queryForString(sql, new String[]{sn});
    	
    }
    /**
	 * 添加会价格
	 */
	@Override
	public void save(List<GoodsLvPrice> goodsPriceList) {
		
		if(goodsPriceList!=null && goodsPriceList.size()>0){
			String sql = SF.memberSql("MEMBER_DEL_GOODS_BY_ID");
			this.baseDaoSupport.execute(sql, goodsPriceList.get(0).getGoodsid());
		 
		 for(GoodsLvPrice goodsPrice:goodsPriceList){
			 this.baseDaoSupport.insert("es_goods_lv_price", goodsPrice);
		 } 
		}
		
	}
	/*
	 * 添加商品权限
	 */
	@Override
	public void addpriv(List<GoodsLvPrice> list){
		String updateSql="update es_price_priv set state='00A'  where goods_id = ? ";
//		if(list!=null && list.size()>0){
//			 String sql="delete from  es_price_priv  where goods_id=?";
//			 this.baseDaoSupport.execute(sql, list.get(0).getGoodsid());
//			for (GoodsLvPrice goodslvpri:list) {
//				PricePriv pricePriv=new PricePriv();
//				pricePriv.setGoods_id(goodslvpri.getGoodsid());
//				pricePriv.setRole_type(goodslvpri.getLvid());
//				pricePriv.setState("00A");
//				this.baseDaoSupport.insert("es_price_priv", pricePriv);
//			}
//					
//		} 
		this.baseDaoSupport.execute(updateSql, new String[]{list.get(0).getGoodsid()});
	}
	@Override
	public HashMap getGoodsSpecInfo(String goods_id) {
		String sql = "select params from es_goods t where t.goods_id= ?";
		HashMap goods = (HashMap) this.baseDaoSupport.queryForMap(sql, goods_id);
		return goods;
	}
	@Override
	public HashMap getGoodsCatPath(String goods_id) {
		String sql = "select cat_path from es_goods_cat t where t.cat_id = (select cat_id from es_goods a where a.goods_id = ? )";
		HashMap goods = (HashMap) this.baseDaoSupport.queryForMap(sql, goods_id);
		return goods;
	}
	@Override
	public List<GoodsSpecificationModel> getcolConfig(String cat_id) {
		String sql = "select t.goods_cat,t.column_name,t.column_value,t.column_desc,t.rank,t.type from es_tab_col_config t where t.goods_cat = ? order by rank";
		List<GoodsSpecificationModel> goods =  this.baseDaoSupport.queryForList(sql,GoodsSpecificationModel.class,new String[]{cat_id});
		return goods;
	}


	@Override
	public List<Map> listGoodRelProducts(String goods_id) {
		String sql = SF.goodsSql("GOODS_REL_PRODUCT_LIST");
		List<Map> products = this.baseDaoSupport.queryForList(sql, goods_id);
		return products;
	}


	@Override
	public List listColor() {
		String sql = SF.goodsSql("PRODUCT_COLOR_LIST");
		List datas = this.baseDaoSupport.queryForList(sql, null);
		return datas;
	}


	@Override
	public Map getProductColor(String goods_id) {
		
		return this.baseDaoSupport.queryForMap(SF.goodsSql("PRODUCT_COLOR_GET"), goods_id);
	}
	
	
	@Override
	public List getProductIDByGoodsId(String goodsId){
		String sql="select * from es_product t where t.goods_id = ? ";
		List listProduct=this.baseDaoSupport.queryForList(sql, goodsId);
		return listProduct;
	}


	@Override
	public void addGoodsRelProduct(Map data) {
		this.baseDaoSupport.insert("es_goods_rel", data);
	}


	@Override
	public void eidtGoodsRelProduct(Map data) {
		this.baseDaoSupport.update("es_goods_rel", data," a_goods_id = '"+data.get("a_goods_id")+"'");
		
	}


	@Override
	public Page getOfferList(String name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("OFFER_LIST");
		if(StringUtils.isNotEmpty(name)){
			sql += " and p.name like '%"+name+"%'";
		}
		sql += " order by g.create_time desc";
		
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
		//获取商品参数，套餐对应的档次
		List list = page.getResult();
		for(int i=0;i<list.size();i++){
			Map offer = (Map)list.get(i);
			String params = (String)offer.get("params");
			if(StringUtils.isNotEmpty(params)){
				try{
					List<ParamGroup> paramsArray = GoodsUtils.getParamList(params);
					if(paramsArray.size()>0){
						ParamGroup paramGroup = paramsArray.get(0);
						List<GoodsParam> paramList = paramGroup.getParamList();
						for(GoodsParam goodsParam:paramList){
							if("month_fee".equals(goodsParam.getEname())){
								offer.put("month_fee", goodsParam.getValue());
								break;
							}
						}
					}
				}catch(Exception e){}
			}
			else{
				offer.put("month_fee", "");
			}
		}
		return page;
	}


	/**
	 * 查询合约关联的套餐
	 */
	@Override
	public List<Goods> listContractOffers(String goods_id) {
		String sql = SF.goodsSql("CONTRACT_OFFER_LIST");
		List<Goods> products = this.baseDaoSupport.queryForList(sql, Goods.class,goods_id);
		return products;
	}

	@Override
	public void deleteGoodsRelProduct(String a_goods_id) {
		this.baseDaoSupport.execute(SF.goodsSql("GOODS_REL_PRODUCT_DELETE"), a_goods_id);
	}

	private void checkSave(Goods goods) throws Exception {
		String sql = SF.goodsSql("GOODS_CHECK_SAVE");
		if("goods".equals(goods.getType())){
			checkCount(sql,goods.getSku(), "商品编码重复,请重新输入");
		}else{
			checkCount(sql,goods.getSku(), "SKU重复,请重新输入");
		}
	}
	
	private void checkCount(String sql, String value, String notar)
			throws Exception {
		int count = 0;
		if(StringUtils.isNotEmpty(value))
			count = baseDaoSupport.queryForInt(sql, value);
		if (count > 0)
			throw new Exception(notar);
	}

	@Override
	public Map getGoodsTagEcs(String goods_id) {
		List<Map> datas = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_TAG_GET"), goods_id);
		if(datas!=null && datas.size()>0){
			return datas.get(0);
		}
		return new HashMap();
	}

	@Override
	public String getSku(String goods_id) {
		String source = ManagerUtils.getSourceFrom();
		StringBuilder sql = new StringBuilder(SF.goodsSql("GET_SKU"));
		sql.append(" and  g.goods_id = '" + goods_id + "'");
		sql.append(" and  g.source_from = '" + source + "'");
		return baseDaoSupport.queryForString(sql.toString());

	}


	@Override
	public void reduccion(String ids) {
		StringBuilder sql = new StringBuilder(SF.goodsSql("GOODS_UPDATE_DISABLED_0"));
		sql.append( " where 1 = 1 ");
		sql.append(" and goods_id in (" + ids + ")");
		baseDaoSupport.execute(sql.toString());
	}


	@Override
	public void claro(String ids) {
		StringBuilder sql = new StringBuilder(SF.goodsSql("GOODS_CLARO"));
		sql.append( " and source_from ='"+ManagerUtils.getSourceFrom()+"' ");
		sql.append(" and goods_id in (" + ids + ")");
		baseDaoSupport.execute(sql.toString());
		
		//增加删除其他关联表数据----zengxianlian
		sql = new StringBuilder(SF.goodsSql("GOODS_CLARO1"));
		sql.append( " and source_from ='"+ManagerUtils.getSourceFrom()+"' ");
		sql.append(" and goods_id in (" + ids + ")");
		baseDaoSupport.execute(sql.toString());
		
		sql = new StringBuilder(SF.goodsSql("GOODS_CLARO2"));
		sql.append( " and source_from ='"+ManagerUtils.getSourceFrom()+"' ");
		sql.append(" and a_goods_id in (" + ids + ")");
		baseDaoSupport.execute(sql.toString());
		
		sql = new StringBuilder(SF.goodsSql("GOODS_CLARO3"));
		sql.append( " and source_from ='"+ManagerUtils.getSourceFrom()+"' ");
		sql.append(" and goods_id in (" + ids + ")");
		baseDaoSupport.execute(sql.toString());
	}


	@Override
	public Goods getGoodsInfo(String goods_id) {
		String sql = SF.goodsSql("GOODS_GET");
		List goodsList = this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, goods_id);
		if(goodsList==null && goodsList.size()==0)
			return null;
		Goods goods = (Goods) goodsList.get(0);
		return goods;
	}


	@Override
	public Goods getProductInfo(String product_id) {
		String sql = SF.goodsSql("PRODUCT_GET");
		List goodsList = this.baseDaoSupport.queryForList(sql, Goods.class, product_id);
		if(goodsList==null && goodsList.size()==0)
			return null;
		Goods goods = (Goods) goodsList.get(0);
		return goods;
	}

	@Override
	public Map<String, String> getProductInfoByName(String name){
		if(StringUtils.isEmpty(name)) return null;
		String sql = SF.goodsSql("LIST_V_PRODUCTS")+" and name=? ";
		List<Map<String, String>> products = this.baseDaoSupport.queryForList(sql, name);
		Map<String, String> rtnMap = (products==null||products.size()==0)?null:products.get(0);
		return rtnMap;
	}

	@Override
	public List<Goods> listProductsByGoodsId(String goods_id) {
		String sql = SF.goodsSql("LIST_PRODUCTS");
		sql += " and exists(select 1 from es_goods_rel r where r.rel_type='PRO_REL_GOODS' and g.goods_id=r.a_goods_id and a_goods_id=?)";
		List<Goods> products = this.baseDaoSupport.queryForList(sql, Goods.class, goods_id);
		return products;
	}


	@Override
	public List<Goods> listGoodsRelProducts() {
		String sql = SF.goodsSql("LIST_GOODS_REL_PRODUCTS");
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List<Goods> listGoodsRelProducts(String goods_id) {
		String preKey = goods_id + "LIST_GOODS_REL_PRODUCTS_GOODSID";
		String sql = SF.goodsSql("LIST_GOODS_REL_PRODUCTS_GOODSID");
		//SerializeList serializeList = (SerializeList)this.cache.get(NAMESPACE,preKey);
		//if(null != serializeList) {
			//return serializeList.getObj();
		//} else {
		    SerializeList serializeList = new SerializeList();
			List<Goods> list = baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, goods_id);
			serializeList.setObj(list);
			//cache.set(NAMESPACE, preKey, serializeList, time);
		//}
		return serializeList.getObj();
	}

	@Override
	public String getActiveType(String goods_id) {
		/*String sql = SF.goodsSql("ACTIVE_TYPE_GET");
		String active_type = this.baseDaoSupport.queryForString(sql, goods_id);
		return active_type;*/
		
		String active_type = "";
		String sql = SF.goodsSql("ACTIVE_TYPE_GET");
//		String active_type = this.baseDaoSupport.queryForString(sql, goods_id);
		List list = this.baseDaoSupport.queryForList(sql,goods_id);
		if(!list.isEmpty()){
			Map map = (Map)list.get(0);
			Object o = map.get("ative_type");
			active_type = o+"";
//			active_type = (String)((Map)list.get(0)).get("ative_type");
		}	
		return active_type;

	}
	
	public Map<String,String> getProductModel(String goods_id){
		String sql = SF.goodsSql("PRODUCT_MODEL");
		List<Map<String,String>> data = this.baseDaoSupport.queryForList(sql, goods_id);
		if(data!=null && data.size()>0)
			return data.get(0);
		else
			return new HashMap<String,String>();
	}
	
	public String getIsIphonePlan(String goods_id){
		/*String sql = SF.goodsSql("IS_IPHONE_PLAN");
		String is_iphone_plan = this.baseDaoSupport.queryForString(sql,goods_id);
		return is_iphone_plan;*/
		String is_iphone_plan = "0";  //默认否
		String sql = SF.goodsSql("IS_IPHONE_PLAN");
		List list = this.baseDaoSupport.queryForList(sql,goods_id);
		if (list != null && list.size() > 0) {
			
			is_iphone_plan = (String)((Map)list.get(0)).get("is_iphone_plan");
		}
		
		return is_iphone_plan;
	}
	
	
	public Map<String,String> getColor(String goods_id){
		String sql = SF.goodsSql("COLOR_GET");
		List<Map<String,String>> colors = this.baseDaoSupport.queryForList(sql, goods_id);
		if(colors!=null && colors.size()>0)
			return colors.get(0);
		else
			return new HashMap<String,String>();
	}
	
	/**
	 * 拼装商品下可选包货品参数中可选包可选元素编码字符串
	 * 格式为：可选元素编码值1|可选包货品名称1||可选元素编码值2|可选包货品名称2
	 * @param goods_id
	 * @return
	 */
	private String getCustomeriztnOff(String goods_id) {
		
		String sql = SF.goodsSql("CONTRACT_OFFER_LIST");
		sql += " and r.rel_type = '" + Consts.PRO_REL_GOODS + "'"
			 + " and g.type = '" + Consts.ECS_QUERY_TYPE_PRODUCT + "'"
			 + " and g.type_id = '" + Consts.PRODUCT_TYPE_KEXUANBAO + "'";
		
		String cOffStr = "";
		List<Map<String,String>> cOffLst = this.baseDaoSupport.queryForList(sql, goods_id);
		for (int i = 0; cOffLst != null && i < cOffLst.size(); i++) {
			Map cOff = cOffLst.get(i);
			String cOffCode = "";
			String name = (String)cOff.get("name");  //货品名称
			
			//从可选包货品参数中获取可选包可选元素编码
			String params = (String)cOff.get("params");  //货品参数
			ParamGroup[] paramArs = GoodsTypeUtil.converFormString(params);// 处理参数
			if (paramArs != null) {
				
				for (ParamGroup group : paramArs) {
					List list = group.getParamList();
					for (int k = 0; list != null && k < list.size(); k++) {
						GoodsParam param = (GoodsParam) list.get(k);
						
						//可选包可选元素编码
						if (Consts.PACKAGE_ELEMENT_CODE.equalsIgnoreCase(param.getEname())) {
							cOffCode = param.getValue();
							break;
						}
					}
				}
			}
			
			if (StringUtils.isNotEmpty(cOffCode)) {
				if (i == 0) {
					cOffStr = cOffCode + "|" + name;  //包名1|货品名1
				} else {
					cOffStr += "||" + cOffCode + "|" + name;  //包名1|货品名1||包名2|货品名2
				}
			}
		}
		
		return cOffStr;
	}
	
	
	@Override
	public Goods qryGoodsByIds(String goods_id,String product_id,String source_from){
		
		StringBuffer sql = new StringBuffer(SF.goodsSql("GOODS_BY_IDS"));
		
		sql.append(" AND a.source_from = '"+source_from+"'");
		
		if(StringUtils.isNotEmpty(goods_id)){
			sql.append(" AND a.goods_id = '"+goods_id+"'");
		}else{
			sql.append(" AND EXISTS (SELECT 1 FROM es_product b WHERE a.goods_id = b.goods_id AND b.source_from='"+source_from+"' AND b.product_id = '"+product_id+"')");
		}
		
		return (Goods) this.baseDaoSupport.queryForObject(sql.toString(), Goods.class);
	}
	
	@Override
	public void convertParams(String type) {
		this.baseDaoSupport.execute("delete from es_goods_param ");
		String sql = SF.goodsSql("PARAMS_LIST");
		List<Map> params = this.baseDaoSupport.queryForList(sql);
		if(params!=null && params.size()>0){
			for(Map param:params){
				String goods_id = (String) param.get("goods_id");
				String paramsJson = (String) param.get("params");
				if(StringUtils.isEmpty(paramsJson))
					continue;
				ParamGroup[] paramAr = GoodsTypeUtil.converFormString( paramsJson);// 处理参数
				if(paramAr==null || paramAr.length==0)
					continue;
				for(ParamGroup group:paramAr){
					List<GoodsParam> list = group.getParamList();
					for(GoodsParam goodParam:list){
						String ename = goodParam.getEname();
						String name = goodParam.getName();
						String value = goodParam.getValue();
						String attr_code = goodParam.getAttrcode();
						String value_desc = null;
						if(StringUtils.isNotEmpty(attr_code)){
							List datas = goodsTypeManager.getDropdownData(attr_code);
							if(datas!=null && datas.size()>0){
								for(int i=0;i<datas.size();i++){
									Map data = (Map) datas.get(i);
									Object[] objs = data.keySet().toArray();
									String key = (String) objs[0];
									String desc = (String) objs[1];
									if(value.equals(data.get(key))){
										value_desc = (String) data.get(desc);
									}
								}
							}
						}
						else{
							value_desc = value;
						}
						
						GoodsParamValue paramValue = new GoodsParamValue();
						paramValue.setGoods_id(goods_id);
						paramValue.setParam_code(ename);
						paramValue.setParam_name(name);
						paramValue.setParam_value_code(value);
						paramValue.setParam_value_desc(value_desc);
						this.baseDaoSupport.insert("es_goods_param", paramValue);
					}
				}
			}
		}
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}
	
	/**
	 * 导入合约机
	 */
	@Override
	public String importExcel(File file, Map<String, String> params){
		
		String fileName = Const.getStrValue(params, "fileName");
		if (!StringUtils.isEmpty(fileName)) {
			String sql = SF.goodsSql("JUDGE_GOODS_IMPORT_LOGS");
			sql += " and file_name = '" + fileName+ "'";
			String exists_flag = this.baseDaoSupport.queryForString(sql);
			if (!"0".equals(exists_flag)) {
				return "EXISTS_FILE";
			}
		}
		
		
		List<GoodsImportLog> logs = GoodsImportExcelUtil.readExcel(file,fileName);
		if(logs==null || logs.size()==0)
			return "0";
		String batch_id = this.baseDaoSupport.getSequences("S_ES_GOODS_IMPORT_BATCH","0",6);
		int total = logs.size();
		int success = 0;
		for(int i=0;i<logs.size();i++){
			GoodsImportLog log = logs.get(i);
			log.setBatch_id(batch_id);
			log.setFile_name(fileName);
			log.setBatch_amount(logs.size());
			log.setCreated_date(DBTUtil.current());
			log.setStatus_date(DBTUtil.current());
			log.setDeal_desc("文件批量导入");
			String log_id = this.baseDaoSupport.getSequences("S_ES_GOODS_IMPORT_LOG", "3", 18);
			log.setLog_id(log_id);
			this.baseDaoSupport.insert("es_goods_import_logs", log);
			success++;
		}
//		importGoods();
		return total+"#"+success+"#"+(total-success)+"#"+batch_id;
	}
	
	/**
	 * @author zengxianlian
	 * 导入虚拟串号
	
	@Override
	public String importEsTerminal(File file, Map<String, String> params){
		
		String fileName = Const.getStrValue(params, "fileName");
		
		List<EsTerminal> logs = GoodsImportExcelUtil.readEsTerminalExcel(file,fileName);
		if(logs==null || logs.size()==0)
			return "0";
		int total = logs.size();
		int success = 0;
		for(int i=0;i<logs.size();i++){
			EsTerminal log = logs.get(i);
			log.setSource_form(ManagerUtils.getSourceFrom());
			this.baseDaoSupport.insert("es_gd_es_terminal", log);
			success++;
		}
		return total+"#"+success+"#"+(total-success);
	} */
	
	//保存产品包表信息
	public void saveGoodsPackage(GoodsPackage goodsPackage){
		
		baseDaoSupport.insert("goods_package", goodsPackage);
	}
	
	@Override
	public void insertCoQueue(String type,String oper_id,String goods_id,Integer market_enable){
		List datas = this.getOrgResultByGoodsId(goods_id);
		//查询每个lv2的org_id_belong的最新更新记录再次进行修改同步
		for(int i=0;i<datas.size();i++){
		Map date =(Map) datas.get(i);
		CoQueue addReq = new CoQueue();
		String batch_id = null;
		String sql = null;
		boolean isPublished = false;
		if("goods".equalsIgnoreCase(type)){
			sql = SF.goodsSql("GOODS_PUBLISH_CHECK");
		}
		else{
			sql = SF.goodsSql("PRODUCT_PUBLISH_CHECK");
		}
		String count = this.baseDaoSupport.queryForString(sql, goods_id);
		if(!"0".equals(count)){
			isPublished = true;
		}
		if(isPublished){
			if("goods".equalsIgnoreCase(type)){
				sql = SF.goodsSql("GOODS_BATCH_ID");
				List<Map<String,String>> batches = this.baseDaoSupport.queryForList(sql, goods_id);
				batch_id = (batches!=null && batches.size()>0) ? batches.get(0).get("batch_id") : "-1";
				addReq.setObject_type("SHANGPIN");
				addReq.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
			}
			else{
				sql = SF.goodsSql("PRODUCT_BATCH_ID");
				List<Map<String,String>> batches = this.baseDaoSupport.queryForList(sql, goods_id);
				batch_id = (batches!=null && batches.size()>0) ? batches.get(0).get("batch_id") : "-1";
				addReq.setObject_type("HUOPIN");
				addReq.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
			}
			//batch_id = getSequence("S_ES_CO_BATCH_ID");
			addReq.setBatch_id(batch_id);
			addReq.setObject_id(goods_id);
//			List datas = getOrgByGoodsId(type,goods_id);
//			String org_id_str = "";
//			if(datas!=null && datas.size()>0){
//				for(int i=0;i<datas.size();i++){
//					Map data = (Map) datas.get(i);
//					String org_id = (String) data.get("party_id");
//					org_id_str = org_id_str + org_id.trim();
//					if(i!=datas.size()-1){
//						org_id_str += ",";
//					}
//				}
//			}
//			addReq.setOrg_id_str(org_id_str);
//			addReq.setOrg_id_belong("10008");
			if(date.get("org_id_str")!=null){
				String org_id_str=date.get("org_id_str").toString();
				addReq.setOrg_id_str(org_id_str);
			}
			if(date.get("org_id_belong")!=null){
				String org_id_belong=date.get("org_id_belong").toString();
				addReq.setOrg_id_belong(org_id_belong);
			}
			if(market_enable==0){
				addReq.setAction_code(Consts.ACTION_CODE_D);
				addReq.setCo_name("商品停用");
			}
			else if(market_enable==1){
				addReq.setAction_code(Consts.ACTION_CODE_M);
				addReq.setCo_name("商品启用");
			}
			else{//商品修改
				addReq.setAction_code(Consts.ACTION_CODE_M);
				addReq.setCo_name("商品货品修改");
			}
			addReq.setOper_id(oper_id);
			
			coQueueManager.add(addReq);
			}
		}
		
	}

	@Override
	public String getSequence(String seq) {
		return this.daoSupport.getSequences(seq);
	}

	public String getSequenceLen(String seq){
		return this.baseDaoSupport.getSequences(seq, "4", 18);
	}

	@Override
	public String getParentOrgId(String party_id) {
		String sql = SF.goodsSql("PARENT_PARTY_ID");
		return this.baseDaoSupport.queryForString(sql, party_id);
	}



	@Override
	public List<Goods> getContractmachine(String type_id,String terminal_pid,
			String contract_pid, String offerPids) {
		String sql = "	select a.goods_id," +
			    "   (select s.stype_id from es_goods_rel r,es_goods s where r.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			    "       and  r.z_goods_id=s.goods_id and r.a_goods_id = a.goods_id and r.rel_type='PRO_REL_GOODS' and s.type_id=10002 ) stype_id"+
				"   from es_goods a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.type_id='"+type_id+"'";
					    if(Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
				 sql += "	and exists (select 1 from es_goods_rel b where b.rel_type='PRO_REL_GOODS' and b.a_goods_id=a.goods_id and product_id = '"+terminal_pid+"')";
					    }
		sql +=  "	and exists (select 1 from es_goods_rel b where b.rel_type='PRO_REL_GOODS' and b.a_goods_id=a.goods_id and product_id = '"+contract_pid+"')"+
				"	and exists (select 1 from es_goods_rel b where b.rel_type='PRO_REL_GOODS' and b.a_goods_id=a.goods_id and product_id in ("+offerPids+"))";
		
		return this.baseDaoSupport.queryForList(sql, Goods.class);
	}


	@Override
	public List<GoodsRel> getGoodsRelByGoodsId(String goods_id) {
		String sql = SF.goodsSql("GOODS_REF_LIST");
		return this.baseDaoSupport.queryForList(sql, GoodsRel.class,goods_id);
	}


	public IEsGoodsCoManager getEsGoodsM() {
		return esGoodsM;
	}


	public void setEsGoodsM(IEsGoodsCoManager esGoodsM) {
		this.esGoodsM = esGoodsM;
	}


	public IProductoManager getProductoM() {
		return productoM;
	}


	public void setProductoM(IProductoManager productoM) {
		this.productoM = productoM;
	}

	@Override
	public Page searchGoodsImportLogsECS(Map params,int pageNum, int pageSize) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String deal_flag = Const.getStrValue(params, "deal_flag");
		String sql = SF.goodsSql("GOODS_IMPORT_LOGS");
		List pList = new ArrayList();
		if(StringUtils.isNotEmpty(batch_id)){
			sql += " and a.batch_id=? ";
			pList.add(batch_id);
		}
		if(StringUtils.isNotEmpty(start_date)){
			start_date += " 00:00:00";
			sql += " and a.created_date>=to_date(?,'yyyy/mm/dd hh24:mi:ss')";
			pList.add(start_date);
		}
		if(StringUtils.isNotEmpty(end_date)){
			end_date += " 23:59:59";
			sql += " and a.created_date<=to_date(?,'yyyy/mm/dd hh24:mi:ss')";
			pList.add(end_date);
		}
		if(StringUtils.isNotEmpty(deal_flag)){
			sql += " and a.deal_flag=? ";
			pList.add(deal_flag);
		}
		if(ManagerUtils.getAdminUser()!=null){
			sql += " and a.oper_id=? ";
			pList.add(ManagerUtils.getAdminUser().getUserid());
		}
		String countSql = "select count(*) from("+sql+")";
		sql += " order by a.created_date desc ";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, GoodsImportLog.class, countSql, pList.toArray());
		return page;
	}
	
	@Override
	public Page searchSynchLogs(Map params, int pageNum, int pageSize) {
//		try {
//			String startTime = DateUtil.getTime(DateUtil.DATE_FORMAT_6);
//			logger.info("开始时间："+startTime);
//		} catch (FrameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String batch_id = Const.getStrValue(params, "batch_id");
		String name = Const.getStrValue(params, "name");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String sku = Const.getStrValue(params, "sku");
		String status = Const.getStrValue(params, "status");
		String object_type = Const.getStrValue(params, "object_type");
		String sql1 = "";
		String sql2 = "";
		String sql = "";
		List pList = new ArrayList();
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(object_type) || StringUtils.isEmpty(object_type)){//商品同步日志
			sql1 = SF.goodsSql("GOODS_SYNCH_SUCCESS_LOG");  //同步成功
			sql2 = SF.goodsSql("GOODS_SYNCH_FAIL_LOG");  //同步失败
		}
		else if(Consts.ECS_QUERY_TYPE_PRODUCT.equals(object_type)){//货品同步日志
			sql1 = SF.goodsSql("PRODUCT_SYNCH_SUCCESS_LOG");  //同步成功
			sql2 = SF.goodsSql("PRODUCT_SYNCH_FAIL_LOG");  //同步失败
		}
		else if(Consts.ECS_QUERY_TYPE_MODEL.equals(object_type)){//型号同步日志
			sql1 = SF.goodsSql("MODEL_SYNCH_SUCCESS_LOG");  //同步成功
			sql2 = SF.goodsSql("MODEL_SYNCH_FAIL_LOG");  //同步失败
		}
		if(Consts.CO_QUEUE_STATUS_WFS.equals(status)){//未发送
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_WFS+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_FSZ.equals(status)){//发送中
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_FSZ+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_XYSB.equals(status)){//失败
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_XYSB+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_XYCG.equals(status)){//成功
			sql = sql1;
		}
		else{//全部
			sql = sql1+" union all "+sql2 + " and q.status in('"+Consts.CO_QUEUE_STATUS_WFS+"','"+Consts.CO_QUEUE_STATUS_FSZ+"','"+Consts.CO_QUEUE_STATUS_XYSB+"')";
		}
		sql = "select * from("+sql+") t where 1=1 ";
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and t.batch_id=?";
			pList.add(batch_id);
		}
//		if(!StringUtils.isEmpty(name)){
//			sql += " and t.name like '%" + name.trim() + "%'";
//		}
		//zengxianlian
		if(!StringUtils.isEmpty(name)){
			if(!Consts.ECS_QUERY_TYPE_MODEL.equals(object_type)){
				sql += " and t.name like '%" + name.trim() + "%'";
			}else{
				sql += " and t.Model_Name like '%" + name.trim() + "%'";
			}
	}
		
		if(!StringUtils.isEmpty(start_date)){
			sql += " and t.created_date >=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(start_date+" 00:00:00");
		}
		if(!StringUtils.isEmpty(end_date)){
			sql += " and t.created_date <=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(end_date+" 23:59:59");
		}
		if(!StringUtils.isEmpty(sku)&&!Consts.ECS_QUERY_TYPE_MODEL.equals(object_type)){
			sql += " and t.sku=? ";
			pList.add(sku);
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageNum, pageSize, pList.toArray());
		List<Map> pageList = page.getResult();
		if(pageList!=null && pageList.size()>0){
			for(Map data : pageList){
				if(Consts.ECS_QUERY_TYPE_MODEL.equals(object_type)){
					data.put("sku", data.get("model_code"));
					data.put("name", data.get("model_name"));
				}
				batch_id = Const.getStrValue(data, "batch_id");
				String countSql = SF.goodsSql("CYNCH_NUM_COUNT");
				List<Map> counts = this.baseDaoSupport.queryForList(countSql, new String[]{batch_id,batch_id,batch_id,batch_id});
				Map countMap = new HashMap();
				int batch_amount = 0;
				for(int i=0;i<counts.size();i++){
					Map result = counts.get(i);
					String amount = result.get("amount").toString();
					data.put(Const.getStrValue(result, "status"), amount);
					batch_amount += Integer.valueOf(amount);
				}
				data.put("batch_amount", batch_amount);
				String org_id_str = Const.getStrValue(data, "org_id_str");
				if(!StringUtils.isEmpty(org_id_str)){
					String org_name_str = this.baseDaoSupport.queryForString(SF.goodsSql("SYNCH_ORG_GET")+" and PARTY_ID IN("+org_id_str+")", null);
					data.put("org_name_str", org_name_str);
				}
			}
		}
		CountGoodsSynAmount(pageList);
//		try {
//			String endTime = DateUtil.getTime(DateUtil.DATE_FORMAT_6);
//			logger.info("结束时间："+endTime);
//		} catch (FrameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return page;
	}
	
	@Override
	public String importTerminalExcel(File file,Map<String, String> params){
		String fileName = Const.getStrValue(params, "fileName");
		if (!StringUtils.isEmpty(fileName)) {
			String sql = SF.goodsSql("JUDGE_TERMINAL_IMPORT_LOGS");
			sql += " and file_name = '" + fileName+ "'";
			String exists_flag = this.baseDaoSupport.queryForString(sql);
			if (!"0".equals(exists_flag)) {
				return "EXISTS_FILE";
			}
		}
		
		String cat_id = Const.getStrValue(params, "cat_id");
		List<TerminalImportLog> logs = GoodsImportExcelUtil.readPhoneExcel(file,fileName);
		String batch_id = this.baseDaoSupport.getSequences("S_ES_TERMINAL_IMPORT_BATCH","0",6);
		if(logs==null || logs.size()==0)
			return "0";
		int total = logs.size();
		int success = 0;
		for(int i=0;i<total;i++){
			TerminalImportLog log = logs.get(i);
			log.setCat_id(cat_id);
			log.setBatch_id(batch_id);
			log.setFile_name(fileName);
			log.setBatch_amount(total);
			log.setDeal_desc("文件批量导入");
			String log_id = this.baseDaoSupport.getSequences("S_ES_TERMINAL_IMPORT_LOG", "3", 18);
			log.setLog_id(log_id);
			this.baseDaoSupport.insert("es_terminal_import_logs", log);
			success++;
		}
		//importGoods();
		return total+"#"+success+"#"+(total-success)+"#"+batch_id;
	}

	@Override
	public Page searchPhoneImportLogsECS(Map params, int pageNum, int pageSize) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String deal_flag = Const.getStrValue(params, "deal_flag");
		
		String sql = SF.goodsSql("PHONE_IMPORT_LOGS");
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and batch_id=? ";
			pList.add(batch_id);
		}
		if(!StringUtils.isEmpty(start_date)){
			sql += " and created_date >=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(start_date+" 00:00:00");
		}
		if(!StringUtils.isEmpty(end_date)){
			sql += " and created_date <=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(end_date+" 23:59:59");
		}
		if(!StringUtils.isEmpty(deal_flag)){
			sql += " and deal_flag = ? ";
			pList.add(deal_flag);
		}
		if(ManagerUtils.getAdminUser()!=null){
			sql += " and oper_id=? ";
			pList.add(ManagerUtils.getUserId());
		}
		String countSql = "select count(*) from("+sql+")";
		sql += " order by created_date desc ";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, TerminalImportLog.class, countSql, pList.toArray());
		return page;
	}

	@Override
	public Page searchProductPkgECS(Map params, int pageNum, int pageSize) {
		String type_id = Const.getStrValue(params, "type_id");
		String brand_id = Const.getStrValue(params, "brand_id");
		String model_code = Const.getStrValue(params, "model_code");
		String name = Const.getStrValue(params, "name");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		
		List pList = new ArrayList();
		StringBuilder whereCond = new StringBuilder();
		if(!StringUtils.isEmpty(type_id)){
			whereCond.append(" and relation_type=? ");
			pList.add(type_id);
		}
		if(!StringUtils.isEmpty(brand_id)){
			whereCond.append(" and model_code in(select b.model_code from es_brand a,es_brand_model b where a.brand_code=b.brand_code and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.brand_id=? )");
			pList.add(brand_id);
		}
		if(!StringUtils.isEmpty(model_code)){
			whereCond.append(" and upper(model_code) like ?");
			pList.add("%" + model_code.toUpperCase() + "%");
		}
		if(!StringUtils.isEmpty(name)){
			whereCond.append(" and upper(relation_name) like ? ");
			pList.add("%"+name.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(start_date)){
			start_date = start_date +" 00:00:00";
			whereCond.append(" and created_date >= to_date(?,'yyyy/mm/dd hh24:mi:ss') ");
			pList.add(start_date);
		}
		if(!StringUtils.isEmpty(end_date)){
			end_date = end_date +" 23:59:59";
			whereCond.append(" and created_date <= to_date(?,'yyyy/mm/dd hh24:mi:ss') ");
			pList.add(end_date);
		}
		String sql = SF.goodsSql("PRODUCT_PKG_LIST")+whereCond;
		String countSql = "select count(*) from("+sql+")";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, Relations.class, countSql, pList.toArray());
		return page;
	}

	@Override
	public List listColorByModelCode(Map params) {
		String model_code = Const.getStrValue(params, "model_code");
		String sql = SF.goodsSql("COLOR_LIST_BY_MODEL_CODE");
		List colors = this.baseDaoSupport.queryForList(sql, model_code);
		return colors;
	}

	@Override
	public Page searchPkgGoodsECS(Map params, int pageNum, int pageSize) {
		String relation_id = Const.getStrValue(params, "relation_id");
		String colors = Const.getStrValue(params, "colors");
		String lvls_3g = Const.getStrValue(params, "lvls_3g");
		String lvls_4g = Const.getStrValue(params, "lvls_4g");
		//如果查询条件都为空，直接返回
		if(StringUtils.isEmpty(relation_id)){
			return new Page();
		}
				
		colors = Const.getInWhereCond(colors);
		lvls_3g = Const.getInWhereCond(lvls_3g);
		lvls_4g = Const.getInWhereCond(lvls_4g);
		
		StringBuilder whereCond = new StringBuilder();
		if(!StringUtils.isEmpty(relation_id)){
			whereCond.append(" and rd.relation_id='"+relation_id+"' ");
		}
		if(!StringUtils.isEmpty(colors)){
			whereCond.append(" and exists(select 1 from es_goods_rel a,es_product c where a.a_goods_id=rd.object_id and a.product_id=c.product_id and a.source_from='"+ManagerUtils.getSourceFrom()+"' and color in("+colors+")) ");
		}
		if(!StringUtils.isEmpty(lvls_3g)){
			whereCond.append(" and exists(select 1 from es_goods_rel a,es_goods b where a.a_goods_id=rd.object_id and a.z_goods_id=b.goods_id and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.stype_id in("+lvls_3g+")" +
					"	and (b.model_code = 'iphonetc' or upper(b.model_code) like '%3G%'))");
		}
		if(!StringUtils.isEmpty(lvls_4g)){
			whereCond.append(" and exists(select 1 from es_goods_rel a,es_goods b where a.a_goods_id=rd.object_id and a.z_goods_id=b.goods_id and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.stype_id in("+lvls_4g+")" +
					"	and (b.model_code = '4gtc' or upper(b.model_code) like '%4G%'))");
		}
		String sql = SF.goodsSql("PACKAGE_GOODS_LIST")+whereCond+ " order by g.name desc";
		String countSql = "select count(*) from("+sql+")";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, Goods.class, countSql, null);
		
		return page;
	}

	@Override
	public List listRelationDetails(String relation_id) {
		String sql = SF.goodsSql("RELATION_DETAIL_LIST");
		List details = this.baseDaoSupport.queryForList(sql, relation_id);
		return details;
	}

	@Override
	public void updatePkgGoodsStatusECS(String relation_id) {
		List details = listRelationDetails(relation_id);
		for(int i=0;i<details.size();i++){
			Map detail = (Map) details.get(i);
			String goods_id = Const.getStrValue(detail, "object_id");
			updateField("market_enable", Consts.MARKET_ENABLE_0, goods_id);
			insertCoQueue("goods", ManagerUtils.getUserId(), goods_id, 0);
		}
	}

	@Override
	public Map<String,Map<String,String>> getGoodsTypeByGoodsId(String[] goods_id) {
		Map<String,Map<String,String>> typeMap = new HashMap<String,Map<String,String>>();
		if(goods_id==null || goods_id.length==0)
			return new HashMap();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<goods_id.length;i++){
			sb.append("'"+goods_id[i]+"'");
			if(i<goods_id.length-1)
				sb.append(",");
		}
		String sql = SF.goodsSql("GOODS_TYPE_BY_GOODS_ID");
		if(sb.length()>0){
			sql += " and goods_id in("+sb.toString()+")";
		}
		List types = this.baseDaoSupport.queryForList(sql, null);
		for(int i=0;i<types.size();i++){
			Map map = (Map) types.get(i);
			typeMap.put(Const.getStrValue(map, "type_id"), map);
		}
		return typeMap;
	}

	@Override
	public Relations getPackage(Map params) {
		String sql = SF.goodsSql("PRODUCT_PKG_LIST");
		String type_id = Const.getStrValue(params, "type_id");
		String contract_goods_id = Const.getStrValue(params, "contract_goods_id");
		String model_code = Const.getStrValue(params, "model_code");
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(type_id)){
			sql += " and relation_type=? ";
			pList.add(type_id);
		}
		if(!StringUtils.isEmpty(contract_goods_id)){
			sql += " and contract_goods_id= ? ";
			pList.add(contract_goods_id);
		}
		else{
			sql += " and contract_goods_id is null ";
		}
		if(!StringUtils.isEmpty(model_code)){
			sql += " and model_code=? ";
			pList.add(model_code);
		}
		else{
			sql += " and model_code is null ";
		}
		sql +=" and status='00A'";
		List<Relations> relations = this.baseDaoSupport.queryForList(sql, Relations.class, pList.toArray());
		return relations.size()==0 ? null : relations.get(0);
	}

	@Override
	public void addPackageMember(String relation_id, String goods_id) {
		Map member = new HashMap();
		String detail_id = this.baseDaoSupport.getSequences("S_ES_RELATION_DETAIL");
		member.put("detail_id", detail_id);
		member.put("relation_id", relation_id);
		member.put("object_id", goods_id);
		this.baseDaoSupport.insert("es_relation_detail", member);
	}

	@Override
	public boolean checkPackageMember(String relation_id, String goods_id) {
		String sql = SF.goodsSql("PACKAGE_GOODS_CHECK");
		String count = this.baseDaoSupport.queryForSingleResult(sql, relation_id, goods_id);
		if(!"0".equals(count))
			return true;
		return false;
	}

	@Override
	public void deletePackageMember(String goods_id) {
		String sql = SF.goodsSql("PACKAGE_GOODS_DELETE");
		this.baseDaoSupport.execute(sql, goods_id);
	}
	
	@Override
	public Goods qryGoodsByCrmOfferId(String crm_offer_id,String source_from){
		
		StringBuffer sql = new StringBuffer(SF.goodsSql("GOODS_BY_IDS"));
		
		sql.append(" AND a.source_from = '"+source_from+"'");
		
		if(StringUtils.isNotEmpty(crm_offer_id)){
			sql.append(" AND a.crm_offer_id = '"+crm_offer_id+"'");
		}
		
		return (Goods) this.baseDaoSupport.queryForObject(sql.toString(), Goods.class);
	}

	@Override
	public int publishAgain(Map params) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String sku = Const.getStrValue(params, "sku");
		String status = Consts.CO_QUEUE_STATUS_XYSB;
		String object_type = Const.getStrValue(params, "object_type");
		String sql2 = "";
		String sql = "";
		List pList = new ArrayList();
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(object_type) || StringUtils.isEmpty(object_type)){//商品同步日志
			sql2 = SF.goodsSql("GOODS_SYNCH_FAIL_LOG");  //同步失败
		}
		else{//货品同步日志
			sql2 = SF.goodsSql("PRODUCT_SYNCH_FAIL_LOG");  //同步失败
		}
		sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_XYSB+"'";
		
		sql = "select * from("+sql+") t where 1=1 ";
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and t.batch_id=?";
			pList.add(batch_id);
		}
		if(!StringUtils.isEmpty(start_date)){
			sql += " and t.created_date >=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(start_date+" 00:00:00");
		}
		if(!StringUtils.isEmpty(end_date)){
			sql += " and t.created_date <=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(end_date+" 23:59:59");
		}
		if(!StringUtils.isEmpty(sku)){
			sql += " and t.sku=? ";
			pList.add(sku);
		}
		//如果是超级管理员则查全部工号的
		if(!Consts.CURR_FOUNDER_1.equals(Integer.toString(ManagerUtils.getAdminUser().getFounder()))){
			String userid = ManagerUtils.getAdminUser().getUserid();
			sql += " and t.oper_id=? ";
			pList.add(userid);
		}
		List<Map> queues = this.baseDaoSupport.queryForList(sql, pList.toArray());
		int count = 0;
		if(queues!=null && queues.size()>0){
			List ids = new ArrayList();
			int size = queues.size();
			for(int i=0;i<size;i++){
				count++;
				Map queue = queues.get(i);
				ids.add(Const.getStrValue(queue, "co_id"));
			}
			CoQueueModifyReq req = new CoQueueModifyReq();
			req.setCo_ids(ids);
			
			String source_from=ManagerUtils.getSourceFrom();
			ZteClient client = ClientFactory.getZteDubboClient(source_from);
			client.execute(req, CoQueueModifyResp.class);
		}
		return count;
	}

	@Override
	public String getTerminalModelCode(String[] z_goods_ids,String type) {
		String where = Const.getInWhereCond(z_goods_ids);
		String sql = SF.goodsSql("TERMINAL_MODEL_CODE")+" and goods_id in("+where+")";
		String model_code = this.baseDaoSupport.queryForString(sql, type);
		return model_code;
	}
	
	

	@Override
	public Map getGoodSpec(String goods_id) {
		Map<String,String> specMap = new HashMap<String,String>();
		Goods goods = read.getCachesGoods(goods_id);
		if(StringUtils.isEmpty(goods.getGoods_id())){
			String sql = SF.goodsSql("LIST_GOODS")+" and g.goods_id=? ";
			List<Goods> specs = this.baseDaoSupport.queryForList(sql, Goods.class,Consts.GOODS_DISABLED_0,goods_id);
			goods = (specs==null || specs.size()==0) ? new Goods():specs.get(0);
		}
		try {
			specMap = com.ztesoft.common.util.BeanUtils.bean2Map(specMap, goods);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String params = specMap.get("params");
		Map paramsMap = GoodsUtils.params2Map(params);
		specMap.remove("params");
		specMap.putAll(paramsMap);
		return specMap;
	}
	
	@Override
	public Map getProductSpec(Goods product) {
		Map productSpec = new HashMap();
		try {
			productSpec = com.ztesoft.common.util.BeanUtils.bean2Map(productSpec, product);
			productSpec.remove("params");//移除参数json信息
			String params = product.getParams();
			Map paramsMap = GoodsUtils.params2Map(params);
			productSpec.putAll(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productSpec;
	}

	@Override
	public List<Map> listGoodsSpec() {
		String sql = SF.goodsSql("GOODS_SPEC");
		List<Map> specs = null;
		try{
			specs = this.baseDaoSupport.queryForList(sql, Consts.GOODS_DISABLED_0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return specs;
	}

	@Override
	public String getGoodsTypeId(String goods_id) {
		String type_id = null;
		List<Goods> goodsList = null;
		String sql = SF.goodsSql("GET_GOODS_BY_ID")+" and type='product' ";
		try{
			goodsList = this.baseDaoSupport.queryForList(sql, Goods.class,goods_id,Consts.GOODS_DISABLED_0);
			if(goodsList!=null && goodsList.size()>0){
				Goods goods = goodsList.get(0);
				type_id = goods.getType_id();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return type_id;
	}

	@Override
	public String getPCode(String goods_id) {
		String sql = SF.goodsSql("P_CODE_1");
		List<Map> list = null;
		String result = "";
		try{
			list = this.baseDaoSupport.queryForList(sql,goods_id);						
			if (list==null || list.size()==0) {
				sql = SF.goodsSql("P_CODE_2");
				list = this.baseDaoSupport.queryForList(sql,goods_id);		
			}
			if(list!=null && list.size()>0){
				Map map = list.get(0);
				result = Const.getStrValue(map, "p_code");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Goods> listGoodsParams() {
		List<Goods> paramList = null;
		try{
			paramList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PARAMS_LIST"), Goods.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return paramList;
	}

	@Override
	public Goods getGoodsForCache(String goods_id) {
		String sql = SF.goodsSql("LIST_GOODS")+" and g.goods_id=? ";
		Goods goods = (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class, Consts.GOODS_DISABLED_0,goods_id);
		if(goods==null){
			goods = (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class, Consts.GOODS_DISABLED_1,goods_id);
		}
		return goods;
	}

	@Override
	//根据商品货品ID查es_goods_co的记录，仅单条记录
	public String getResultStrByGoodsId(String goods_id,String resultStr) {
		// TODO Auto-generated method stub
		String org_id_str="";
		String sql="select "+resultStr+" from(select a.*,rownum from es_co_queue_bak a where a.object_id=? order by a.status_date desc) where rownum <=1";
		try{
			org_id_str=this.baseDaoSupport.queryForString(sql,goods_id);
		}catch(Exception e){
			
		}
		return org_id_str;
	}

	@Override
	//根据商品货品ID查es_goods_co的记录，多条记录
	public List getOrgResultByGoodsId(String goods_id) {
		// TODO Auto-generated method stub
		List list=null;
		String sql="select * from es_co_queue_bak a where status_date in(select max(status_date) from es_co_queue_bak a " +
				"where object_id=? group by a.org_id_belong ) and object_id=?";
		try{
			list =this.baseDaoSupport.queryForList(sql, goods_id,goods_id);
		}catch(Exception e){
			
		}
		return list;
	}

	@Override
	public List<Goods> listGoodsIds() {
		String sql = SF.goodsSql("GOODS_IDS_LIST");
		return this.baseDaoSupport.queryForList(sql,Goods.class);
	}
	
	@Override
	public List<HashMap> listTerminalNum(){
		String sql = SF.goodsSql("TERMINAL_NUM_LIST");
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public String getTerminalNumBySN(String sn){
		String sql = SF.goodsSql("TERMINAL_NUM_LIST");
		String terminal_num = "";
		List<HashMap> terminalList = GoodsNetCacheRead.getInstance().getTerminalNumList();
		if(null != terminalList && !terminalList.isEmpty()){
			for(int i = 0; i < terminalList.size(); i++){
				Map terminal = terminalList.get(i);
				if(sn.equals(Const.getStrValue(terminal, "sn"))){
					terminal_num = Const.getStrValue(terminal, "terminal_no");
					break;
				}
			}
		}
		return terminal_num;
	}
	
	@Override
	public void initGoodsCache(){
//		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
//		//配置的不匹配直接选用文件配置的,add by wui
//		if(!(planDBSourceFroms.indexOf(EopSetting.SOURCE_FROM)>-1))
//			planDBSourceFroms = EopSetting.SOURCE_FROM;
//		if(StringUtil.isEmpty(planDBSourceFroms))
//			planDBSourceFroms = ManagerUtils.getSourceFrom();
//		if("ECSORD".equals(planDBSourceFroms))
//			planDBSourceFroms ="ECS";
//		GoodsNetCacheWrite write = new GoodsNetCacheWrite();
//		Class clazz = write.getClass();
//		Method[] methods = clazz.getMethods();
//		try {
//			for (Method method : methods) {
//				if ("loadAllGoods".equals(method.getName())
//						|| "loadAllProducts".equals(method.getName())
//						|| "loadAllGoodsTerminal".equals(method.getName())
//						|| "loadAllGoodsTC".equals(method.getName())
//						|| "loadAllGoodsContract".equals(method.getName())
//						|| "loadAllGoodsPromotion".equals(method.getName())
//						|| "loadAllGoodsTags".equals(method.getName())
//						|| "loadAllGoodsRelTags".equals(method.getName())
//						|| "loadGoodsComplex".equals(method.getName())
//						|| "loadGoodsAdjunct".equals(method.getName())
//						|| "loadCatComplex".equals(method.getName())
//						|| "loadGoodsType".equals(method.getName())
//						|| "loadBrand".equals(method.getName())
//						|| "loadGoodsCatByLvId".equals(method.getName())
//						|| "loadGoodsByCatLvI".equals(method.getName())
//						|| "loadAllGoodsByServ".equals(method.getName())
//						|| "loadAllTypes".equals(method.getName())
//						|| "loadBrandByTypeId".equals(method.getName())
//						|| "loadBrandModelByBrandId".equals(method.getName())
//						|| "loadGoodsCatByTypeId".equals(method.getName())
//						|| "loadAllGoodsBySku".equals(method.getName())
//						|| "loadAllVproductsByName".equals(method.getName())) {
//					
//					for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
//						ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
//						Method m = clazz.getDeclaredMethod(method.getName());
//						m.invoke(write);
//					}
//				}
//
//			}
//			
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}finally{
//			ManagerUtils.CACHE_REFRESH_SOURCE_FROM ="";
//		}
	}

//	ThreadPoolExecutor executor_t = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_NEW, 90);
//	public void initGoodsCache(){
//		int w = 0; //61-70 71-80 81-90
//		for(int i = 61 ; i <= 80; i ++){
//			BusiCompRequest request = new BusiCompRequest();
//			Map param = new HashMap();
//			param.put("i", i+"");
//			request.setQueryParams(param);
//			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(request) {
//				public ZteResponse execute(ZteRequest requestN) {
//					BusiCompRequest request = (BusiCompRequest)requestN;
//					Map param = request.getQueryParams();
//					String sql = "select a.goods_id from es_goods a where a.source_from = 'ECS'";
//					String i = (String)param.get("i");
//					IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
//					List<Map> list = (List<Map>) support.queryForPage(sql, new Integer(i).intValue(), 500).getData();
//					GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
//					StringBuffer s=new StringBuffer();
//					for(int j = 0; j < list.size(); j++){
//						Map temp = list.get(j);
//						String goods_id = Const.getStrValue(temp, "goods_id");
//						s.append(goods_id).append(",");
//					}
//					if(s.toString().length()>0){
//						logger.info("====================>>>>开始" + i);
//						goodsRefreshDTO.setGoods_ids(s.substring(0,s.length()-1));
//						initGoodsCacheByCondition(goodsRefreshDTO);
//						logger.info("====================>>>>结束" + i);
//					}
//				
//					return new ZteResponse();
//				}
//			});
//			ThreadPoolFactory.submit(taskThreadPool,executor_t);
//		}
//	}
	@Override
	public Map importZdbs(File file, String fileNameFileName,String act) throws Exception {
		// TODO 转兑包导入模版处理
		Integer successCount = 0;
		Integer failureCount = 0;
		Integer batch_amount = 0;
		String batch_id = "";
		Map result = new HashMap();
		List<Map> zdbsList = new ArrayList<Map>();
			if (!StringUtils.isEmpty(fileNameFileName)) {
				String sql = SF.goodsSql("JUDGE_ZDB_IMPORT_LOGS");
				sql += " and file_name = '" + fileNameFileName+ "'";
				String exists_flag = this.baseDaoSupport.queryForString(sql);
				if (!"0".equals(exists_flag)) {
					result.put("EXISTS_FILE", "yes");
					return result;
				}
			}
			
			zdbsList = readExcelInfo(file,fileNameFileName,act);
			if(zdbsList==null || zdbsList.size()==0){
				result.put("NO_DATA", "yes");
				return result;
			}
			batch_id = this.baseDaoSupport.getSequences("S_ES_ZDB_IMPORT_BATCH","0",6);
			batch_amount = zdbsList.size();
			try{
			for(Map zdb:zdbsList){
				zdb.put("oper_id", ManagerUtils.getAdminUser().getUserid());
				zdb.put("batch_id", batch_id);
				zdb.put("file_name", fileNameFileName);
				zdb.put("batch_count", batch_amount);
				zdb.put("deal_flag", 0);
				zdb.put("deal_num", 0);
//				zdb.put("deal_type", "PLDR");
				zdb.put("deal_desc", "文件批量导入");
				String log_id = this.baseDaoSupport.getSequences("S_ES_ZDB_IMPORT_LOG", "3", 18);
				zdb.put("log_id", log_id);
				this.baseDaoSupport.insert("es_zdb_import_logs", zdb);
				successCount++;
			}}catch(Exception e){
				e.printStackTrace();
			}
			failureCount = batch_amount - successCount;	
		result.put("successCount", successCount);
		result.put("failureCount", failureCount);
		result.put("totalCount", batch_amount);
		result.put("batch_id", batch_id);
		return result;
	}

	private List<Map> readExcelInfo(File file, String fileNameFileName,String act) throws Exception {
		// TODO 转兑包模版表格信息读取
		List<Map> zdbsList = new ArrayList<Map>();
		FileInputStream is = new FileInputStream(file);
		
		boolean flag;//表示是否有空行
		Workbook wb = null;
        if (!fileNameFileName.endsWith(".xlsx")){  
            wb = new HSSFWorkbook(is);  
        }else{  
            wb = new XSSFWorkbook(is);  
        }
        //遍历sheet
		for(int k=0;k<1;k++){//活动表因为附加了一张规则说明所以写定遍历的sheet数量
			
			Sheet sheet = wb.getSheetAt(k);
			
			//遍历行
			for (int i=1;i<=sheet.getLastRowNum();i++) {
				Map zdb = new HashMap();
				Row row = sheet.getRow(i);
				
				if(row==null){
					//跳过空行
					continue;
				}
				
				flag=true;
				for (int j=0;j<row.getLastCellNum();j++ ) {
					Cell cell = row.getCell(j);
					if(cell!=null){
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(!(StringUtils.equals(cell.getStringCellValue().trim(),""))){
							flag=false;
							break;
						}
					}
				}
				if(flag){
					//跳过空行
					continue;
				}
				
				//遍历列
				for (int j=0;j<row.getLastCellNum();j++ ) {
					
					Cell cell = row.getCell(j);
					// 读取当前单元格的值
					String title = "";
					if(sheet.getRow(0) != null && sheet.getRow(0).getCell(j) != null){
						 title = sheet.getRow(0).getCell(j).getStringCellValue();
					}
					if(StringUtils.isNotBlank(title)){
						title = title.trim();
					}

					if(title.trim().equals("名称")){
						if(cell==null) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validateZdbNameExist(cellValue)&&(StringUtils.equals(act, "delete")||StringUtils.equals(act, "edit"))){
							CommonTools.addFailError("不存在第" + (i+1) + "行的" + title.trim() + "转兑包，不能删除或修改！");
						}
						if(validateZdbNameExist(cellValue)&&StringUtils.equals(act, "import")){
							CommonTools.addFailError("已经存在第"+ (i+1) + "行的" + title.trim() + "转兑包，不能导入！");
						}
						zdb.put("zdb_name", cellValue);
					}else if(title.trim().equals("转兑包类型")){
						if(cell==null) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!(cellValue.equals("直降转兑包")||cellValue.equals("流量转兑包"))){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误");
						}
						zdb.put("zdb_type", cellValue);
					}else if(title.trim().equals("BSS编码")){
						if(cell==null) {
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!cellValue.matches("([0-9]{1,10})")){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						zdb.put("bss_code", cellValue);
					}else if(title.trim().equals("转兑包网别")){
						if(cell==null) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!(cellValue.equals("4G")||cellValue.equals("3G")||cellValue.equals("2G"))){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						zdb.put("zdb_gen", cellValue);
					}else if(title.trim().equals("调价额度（元）")){
						if(cell==null) {
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!StringUtils.isEmpty(cellValue)&&!cellValue.equals("0")&&!cellValue.matches("^([1-9][0-9]*)+(.[0-9]{1,2})?$")||cellValue.length()>10){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						zdb.put("zdb_price", cellValue);
					}
				}
				if(zdb.get("zdb_gen")!=null&&zdb.get("zdb_gen").toString().equals("3G")&&
						(zdb.get("bss_code")==null||zdb.get("bss_code").toString().equals(""))){
					CommonTools.addFailError("第" + (i+1) + "行的BSS_CODE在网别3G时不能为空！");
				}
				if(zdb.get("zdb_type")!=null&&zdb.get("zdb_type").toString().equals("直降转兑包")&&
						(zdb.get("zdb_price")==null||zdb.get("zdb_price").toString().equals(""))){
					CommonTools.addFailError("第" + (i+1) + "行直降转兑包的直降金额不能为空！");
				}
				if(zdb.get("zdb_name")==null||zdb.get("zdb_type")==null||zdb.get("zdb_gen")==null){
					CommonTools.addFailError("转兑包模板有错误，请检查！");
				}
				if(StringUtils.equals(act, "import")){
					zdb.put("deal_type", "PLDR");
				}else if(StringUtils.equals(act, "edit")){
					zdb.put("deal_type", "PLXG");
				}else if(StringUtils.equals(act, "delete")){
					zdb.put("deal_type", "PLHS");
				}
				zdbsList.add(zdb);						
			}
		}
		return zdbsList;
	}


	@Override
	public void importZdbToProduct(Goods goods,String act) {
		// TODO 把转兑包中间表的数据归到商品货品
		
		String zdb_name="";
		String log_id="";
		String sqlUpdateZdbLog="";
		String sqlUpdaetZdbGoods="";
		String deal_num="";
		
		if(StringUtils.equals(act, "import")){
			//批量导入
			String sqlZdbToPro=SF.goodsSql("ZDB_TO_PRO");
			List zdbList=this.baseDaoSupport.queryForList(sqlZdbToPro);
			for(int i=0;i<zdbList.size();i++){
				Map m=(Map)zdbList.get(i);
				log_id=m.get("log_id").toString();
				deal_num=m.get("deal_num").toString();
				int dn=Integer.parseInt(deal_num);
				dn++;
				deal_num=Integer.toString(dn);
				//设置商品信息
				zdb_name=m.get("zdb_name").toString();
				if(validateZdbNameExist(zdb_name)){
					//对于存在的转兑包记录不再导入
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 2 , deal_desc='已经存在转兑包，不能重复导入！' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, log_id);
					continue;
				}
				goods.setName(zdb_name);
				goods.setSn(this.createSN("", "", ""));//设置货品的SN
				goods.setSku(this.createSKU(goods.getGoods_type(), goods.getCat_id()));
				goods.setGoods_id(this.baseDaoSupport.getSequences("S_ES_GOODS"));//设置货品的商品ID
				goods.setDisabled(0);
				goods.setView_count(0);
				goods.setBuy_count(0);
				goods.setCost(0d);
				goods.setSearch_key("转兑包_中国联通_"+goods.getName());
				String params=this.changeZdbParams(m);
				goods.setParams(params);
				//设置货品信息
				Product product = new Product();
				product.setProduct_id(this.baseDaoSupport.getSequences("SEQ_PRODUCT_PRODUCT_ID"));
				product.setGoods_id(goods.getGoods_id());
				product.setSn(goods.getSn());
				product.setSku(goods.getSku());
				product.setName(goods.getName());
				product.setType("product");
				product.setStore(0);
				product.setCost(0d);
				product.setPrice(0d);
				product.setWeight(0d);
				try{
					this.baseDaoSupport.insert("es_product", product);
					this.baseDaoSupport.insert("es_goods", goods);
					cacheGoods(goods);//缓存货品的信息
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 1 , deal_desc='导入成功' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, log_id);
				}catch(Exception e){
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 2 , deal_desc=? , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, e.getMessage(),log_id);
				}
			}
			
		}
		else if(StringUtils.equals(act, "edit")){
			//批量修改
			String sqlZdbEditPro=SF.goodsSql("ZDB_EDIT_PRO");
			List zdbList=this.baseDaoSupport.queryForList(sqlZdbEditPro);
			for(int i=0;i<zdbList.size();i++){
				Map m=(Map)zdbList.get(i);
				log_id=m.get("log_id").toString();
				deal_num=m.get("deal_num").toString();
				int dn=Integer.parseInt(deal_num);
				dn++;
				deal_num=Integer.toString(dn);
				zdb_name=m.get("zdb_name").toString();
				String params=this.changeZdbParams(m);
				try{
					String sqlQueryExistZdb="select * from es_goods where name=?";
					goods=(Goods) this.baseDaoSupport.queryForObject(sqlQueryExistZdb, Goods.class, zdb_name);
					goods.setParams(params);
					sqlUpdaetZdbGoods="update es_goods set params = ? where name=?";
					this.baseDaoSupport.execute(sqlUpdaetZdbGoods, goods.getParams(), zdb_name);
					cacheGoods(goods);//缓存货品的信息	
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 1 , deal_desc='修改成功' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, log_id);
				}catch(Exception e){
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 2 , deal_desc='修改失败' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, e.getMessage(),log_id);
				}
			}
		}
		else if(StringUtils.equals(act, "delete")){
			//批量回收
			String sqlZdbDelPro=SF.goodsSql("ZDB_DEL_PRO");
			List zdbList=this.baseDaoSupport.queryForList(sqlZdbDelPro);
			for(int i=0;i<zdbList.size();i++){
				Map m=(Map)zdbList.get(i);
				log_id=m.get("log_id").toString();
				deal_num=m.get("deal_num").toString();
				int dn=Integer.parseInt(deal_num);
				dn++;
				deal_num=Integer.toString(dn);
				zdb_name=m.get("zdb_name").toString();
				String params=this.changeZdbParams(m);
				try{
					String sqlQueryExistZdb="select * from es_goods where name=?";
					goods=(Goods) this.baseDaoSupport.queryForObject(sqlQueryExistZdb, Goods.class, zdb_name);
					sqlUpdaetZdbGoods="update es_goods set market_enable = 0 where name=?";
					this.baseDaoSupport.execute(sqlUpdaetZdbGoods, zdb_name);
					cacheGoods(goods);//缓存货品的信息	
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 1 , deal_desc='回收成功' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, log_id);
				}catch(Exception e){
					sqlUpdateZdbLog="update es_zdb_import_logs set deal_flag = 2 , deal_desc='回收失败' , deal_num='"+deal_num+"' where log_id=?";
					this.baseDaoSupport.execute(sqlUpdateZdbLog, e.getMessage(),log_id);
				}
			}	
		}
	}

	private String changeZdbParams(Map m) {
		// TODO 转换MAP为转兑包的参数
		String zdb_type="";
		String zdb_type_code="";
		String bss_code="";
		String zdb_gen="";
		String zdb_price="";
		String params="";

		zdb_type=m.get("zdb_type").toString();
		if(StringUtils.equals(zdb_type, "直降转兑包")){
			zdb_type_code="10002";
		}
		if(StringUtils.equals(zdb_type, "流量转兑包")){
			zdb_type_code="10001";
		}
		if(m.get("bss_code")!=null){
			bss_code=m.get("bss_code").toString();
		}
		zdb_gen=m.get("zdb_gen").toString();
		zdb_price=m.get("zdb_price").toString();
		
		String[] paramnums = {"4"};
		String[] groupnames = {"分类属性"}; 
		String[] paramnames = {"转兑包类型","BSS编码","转兑包网别","调价额度"};
		String[] paramvalues = {zdb_type_code,bss_code,zdb_gen,zdb_price};
		String[] enames = {"package_type","bss_code","PACKAGE_NET","ADJUST_NUM"};
		String[] attrCodes = {"DC_EX_PACKAGE_TYPE","","DC_PACKAGE_NET",""};
		String[] attrvaltypes = {"1","0","1","0"};
		String[] attrtypes = {"goodsparam","goodsparam","goodsparam","goodsparam"};
		String[] required = {"yes","yes","yes",""};
		
		params = goodsTypeManager.getParamString(paramnums, groupnames,
				paramnames, paramvalues, enames, attrvaltypes, attrtypes,attrCodes,required);
		return params;
	}

	@Override
	public Page queryZdbLogsEcs(Map<String, String> params, int page,
			int pageSize) {
		// TODO 查询转兑包的导入日志
		List queryParam=new ArrayList<String>();
		String sqlQueryZdbLogs="select * from es_zdb_import_logs where 1=1";
		if(params.get("zdb_name")!=null){
			String zdb_name=params.get("zdb_name").toString();
			sqlQueryZdbLogs+=" and zdb_name like '%"+zdb_name+"%'";
		}
		if(params.get("batch_id")!=null){
			String batch_id=params.get("batch_id").toString();
			sqlQueryZdbLogs+=" and batch_id =?";
			queryParam.add(batch_id);
		}
		if(params.get("start_date")!=null){
			String start_date=params.get("start_date").toString();
			start_date+=" 00:00:00";
			sqlQueryZdbLogs+=" and status_date >=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			queryParam.add(start_date);
		}
		if(params.get("end_date")!=null){
			String end_date=params.get("end_date").toString();
			end_date+=" 23:59:59";
			sqlQueryZdbLogs+=" and status_date <=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			queryParam.add(end_date);
		}
		if(params.get("deal_flag")!=null){
			String deal_flag=params.get("deal_flag").toString();
			sqlQueryZdbLogs+=" and deal_flag =?";
			queryParam.add(deal_flag);
		}
		if(params.get("deal_type")!=null){
			String deal_type=params.get("deal_type").toString();
			sqlQueryZdbLogs+=" and deal_type=?";
			queryParam.add(deal_type);
		}
		sqlQueryZdbLogs+=" order by status_date desc";
		return this.baseDaoSupport.queryForPage(sqlQueryZdbLogs, page, pageSize,queryParam.toArray());
	}

	private boolean validateZdbNameExist(String zdb_name) {
		// TODO 校验转兑包是否已经存在
		String sql="select count(*) from es_goods where name=? and type='product'";
		int count = this.baseDaoSupport.queryForInt(sql,zdb_name);
		if(count>0)
		return true;
		else
		return false;
	}

	@Override
	public List queryZdbsExport(Map<String, String> params) {
		// TODO 导出转兑包的数据
		List list;
		List data=new ArrayList<Map>();
		String sqlZdb="select * from es_goods where cat_id='691000000' ";
		if(params.get("start_date")!=null){
			String start_date=params.get("start_date").toString();
			start_date+=" 00:00:00";
			sqlZdb+=" and create_time >=to_date('"+start_date+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(params.get("end_date")!=null){
			String end_date=params.get("end_date").toString();
			end_date+=" 23:59:59";
			sqlZdb+=" and create_time <=to_date('"+end_date+"','yyyy-mm-dd hh24:mi:ss')"; 
		}
		if(params.get("zdb_name")!=null){
			String zdb_name=params.get("zdb_name").toString();
			sqlZdb+=" and name like '%"+zdb_name+"%'";
		}
		list=this.baseDaoSupport.queryForList(sqlZdb);
		for(int i=0;i<list.size();i++){
			Map m=(Map)list.get(i);
			String params_list = m.get("params").toString();
			ProductPramasList pl=null;
			try {
				params_list = params_list.substring(1,params_list.lastIndexOf("]"));
				pl = JsonUtil.fromJson(params_list, ProductPramasList.class);
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			if (null != pl) {
				for (int j = 0; j < pl.getParamList().size(); j++) {
					Paramsenum ps = pl.getParamList().get(j);
					String ename = ps.getEname();
					String param_value = ps.getValue();
					if(StringUtils.equals(ename,"package_type")){
						if(StringUtils.equals(param_value, "10001"))
						m.put("zdb_type", "流量转兑包");
						else if(StringUtils.equals(param_value, "10002"))
						m.put("zdb_type", "直降转兑包");
						else
						m.put("zdb_type", param_value);
					}else if(StringUtils.equals(ename,"bss_code")){
						m.put("bss_code", param_value);
					}else if(StringUtils.equals(ename,"PACKAGE_NET")){
						m.put("zdb_gen", param_value);
					}else if(StringUtils.equals(ename,"ADJUST_NUM")){
						m.put("zdb_price", param_value);
					}
				}
			}
			m.put("brand_name", "中国联通");
			if(m.get("create_time")!=null){
				String create_time=m.get("create_time").toString().substring(0, 10);
				m.remove("create_time");
				m.put("create_time", create_time);
			}
			
			data.add(m);
		}
		return data;
	}
	
	
	@Override
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
	
	
	
	@Override
	public List<Goods> listGoodsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_GOODS");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.baseDaoSupport.queryForList(sql+newCondition.toString(), Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List<Goods> qrySysServsByCondition(String serv_type, String serv_name, GoodsRefreshDTO goodsRefreshDTO){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(SF.goodsSql("QRY_SYS_SERVS"));
		
		if(!StringUtil.isEmpty(serv_name)){
			sql.append(" AND name like '%"+serv_name+"%'");
		}
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append("and create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		sql.append(newCondition.toString());
		return this.baseDaoSupport.queryForList(sql.toString(), Goods.class, serv_type);
	}
	
	@Override
	public List<Goods> listProductsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_PRODUCTS");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getProduct_id())){
			newCondition.append(" and p.product_id = '").append(goodsRefreshDTO.getProduct_id()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		
		return this.baseDaoSupport.queryForList(sql+newCondition.toString(), Goods.class, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List listGoodsComplexNumByCondition(GoodsRefreshDTO goodsRefreshDTO){
		String sql = SF.goodsSql("LIST_GOODS_COMPLEX_COUNT");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and  g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");

		}
		sql = sql.replace("group",  newCondition.toString()+ " group ");
		return this.baseDaoSupport.queryForList(sql, Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List listGoodsAdjunctNumByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_GOODS_JOIN_ADJUNCT");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and  g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.baseDaoSupport.queryForList(sql+newCondition.toString(), Consts.GOODS_DISABLED_0);
	}
	
	@Override
	public List<Goods> listGoodsParamsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		List<Goods> paramList = null;
		try{
			String sql = SF.goodsSql("GOODS_PARAMS_LIST");
			// 组装新增条件...
			StringBuffer newCondition = new StringBuffer();
			if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
				newCondition.append(" and c.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
			}
			if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
				newCondition.append(" and c.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
			}
			if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
				newCondition.append(" and c.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
			}
			if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
				newCondition.append(" and c.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
			}
			if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
				newCondition.append(" and c.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
			}
			paramList = this.baseDaoSupport.queryForList(sql+newCondition.toString(), Goods.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return paramList;
	}
	
	@Override
	public List<Map> listVproductsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_V_PRODUCTS");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and c.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and c.skua = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and c.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and c.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and c.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getName())){
			newCondition.append(" and c.name= '"+goodsRefreshDTO.getName()+"' ");
		}
		return this.baseDaoSupport.queryForList(sql+newCondition.toString());
	}
	
	@Override
	public List<Goods> listGoodsByRelTagByCondition(String tagId,GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("GOODS_REL_BY_TAG");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.baseDaoSupport.queryForList(sql, Goods.class, Consts.GOODS_DISABLED_0, tagId); 
	}
	
	@Override
	public List<Goods> listGoodsByCatIdByCondition(String catId,GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("LIST_GOODS");
		String cond = " and g.goods_id in(select goods_id from es_cat_complex where cat_id=? )";
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and g.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and g.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and g.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and g.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and g.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.baseDaoSupport.queryForList(sql+cond+newCondition.toString(), Goods.class,Consts.GOODS_DISABLED_0,catId);
	}
	
	@Override
	public List<Goods> listGoodsIdsByCondition(GoodsRefreshDTO goodsRefreshDTO) {
		String sql = SF.goodsSql("GOODS_IDS_LIST");
		// 组装新增条件...
		StringBuffer newCondition = new StringBuffer();
		if (StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())) {
			newCondition.append(" and t.goods_id in (").append(goodsRefreshDTO.getGoods_ids()).append(") ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getSku())) {
			newCondition.append(" and t.sku = '").append(goodsRefreshDTO.getSku()).append("' ");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getRecently_time())){
			newCondition.append(" and t.create_time >= sysdate-1/").append(goodsRefreshDTO.getRecently_time()).append(" ");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())) {
			newCondition.append(" and t.create_time >= ").append("to_date('").append(goodsRefreshDTO.getCreate_start()).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())) {
			newCondition.append(" and t.create_time <= ").append("to_date('").append(goodsRefreshDTO.getCreate_end()).append("','yyyy-mm-dd hh24:mi:ss')");
		}		
		return this.baseDaoSupport.queryForList(sql+newCondition.toString(),Goods.class);
	}
	@Override
	public List<Map<String,String>> listSkuByActivityCode(String activityCode){
		String sql = "select g.sku from es_pmt_goods t, es_goods g  where g.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.goods_id = g.goods_id and t.pmt_id in (select b.pmt_id from es_promotion_activity a, es_promotion b where a.id = b.pmta_id and b.pmts_id = 'gift' and a.pmt_code = '"+activityCode+"')";
		
		List<Map<String,String>> goodsList = baseDaoSupport.queryForLists(sql);
		return goodsList;
	}
	@Override
	public Page getProductPageQuery(String productId, String goodsId,
			String productName,int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		String sql = SF.goodsSql("PRODUCT_INFO_LIST");
		StringBuffer buff = new StringBuffer(3);
		List param = new ArrayList(3);
		if(StringUtils.isNotEmpty(productId)){
			buff.append(" and product_id = ? ");
			param.add(productId);
		}
		if(StringUtils.isNotEmpty(goodsId)){
			buff.append(" and goods_id = ? ");
			param.add(goodsId);
		}
		if(StringUtils.isNotEmpty(productName)){
			buff.append(" and name like ? ");
			param.add("%"+productName+"%");
		}
		sql+=buff.toString();
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, param.toArray());
		return page;
	}
	
	@Override
	public Goods getGoodsBySku(String sku) {
		String sql = "select * from es_goods where  sku=? and disabled=? and type='goods' and source_from='"+ManagerUtils.getSourceFrom()+"'";
		Goods goods = (Goods) this.baseDaoSupport.queryForObject(sql, Goods.class,sku,0);
		if(goods != null){
			if(StringUtils.isNotBlank(goods.getImage_default())){
	            goods.setImage_default(UploadUtils.replacePath(goods.getImage_default()));
	        }
	        if(StringUtils.isNotBlank(goods.getImage_file())){
	            goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
	        }
		}
		return goods;
	}
	
	@Override
	public List listSn() {
		String sql = SF.goodsSql("ES_TERMINAL_LIST");
		List datas = this.baseDaoSupport.queryForList(sql, null);
		return datas;
	}
	
	/**
	 * @author zhengchuiliu
	 */
	@Override
	public Page getEsTerminalList(int page,int pageSize,String sn,String terminal_no){
		String sql = SF.goodsSql("ES_TERMINAL_LIST");
		if(sn!=null){
			sql+=" and t.sn like '%"+sn.trim()+"%' ";
		}
		if(terminal_no!=null){
			sql+=" and t.terminal_no like '%"+terminal_no.trim()+"%' ";
		}
		
		return this.baseDaoSupport.queryForPage(sql, page, pageSize);
	}
	/**
	 * @author zhengchuiliu
	 */
	@Override
	public boolean saveOrUpdateEsTerminal(EsTerminal esTerminal, String action) {
		String sn = esTerminal.getSn();
		if(checkSnIsExist(sn)>0){
			action = "edit";
		}
		
		if("edit".equals(action)){
			//更新
			Map where=new HashMap();
			where.put("sn", sn);
			this.baseDaoSupport.update("es_gd_es_terminal", esTerminal, where);
		}else if("add".equals(action)){
			//新增
			this.baseDaoSupport.insert("es_gd_es_terminal", esTerminal);
		}
		return true;
	}

	/**
	 * @author zhengchuiliu
	 */
	@Override
	public EsTerminal getEsTerminalDetail(String sn) {
		String sql = SF.goodsSql("ES_TERMINAL_DETAIL");
		EsTerminal esTerminal =  (EsTerminal) this.baseDaoSupport.queryForObject(sql, EsTerminal.class, sn);
		return esTerminal;
	}

	/**
	 * @author zhengchuiliu
	 */
	@Override
	public void deleteEsTerminal(String sn) {
		String[] ids = sn.split(",");
		String attr = "";
		for(int i=0;i<ids.length;i++){
			attr += "'"+ids[i]+"',";
		}
		attr = attr.substring(0, attr.length()-1);
		if (StringUtils.isEmpty(sn)) {
			return;
		}
		
		String sql = SF.goodsSql("ES_TERMINAL_DELETE") + " and sn in ("+attr+")";
		
		this.baseDaoSupport.execute(sql);
	}

	/**
	 * @author zhengchuiliu
	 */
	@Override
	public Page snSelectList(int pageNo, int pageSize, String sn) {
		String sql = SF.goodsSql("PRODUCTS_SELECT_TAGS");
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotBlank(sn)){
			sql += " and t.sn like ?";
			params.add("%"+sn+"%");
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,true,params.toArray(new String[]{}));
		return page;
	}
	
	/**
	 * @author zhengchuiliu
	 * 导入虚拟串号
	 */
	@Override
	public String importEsTerminal(File file, Map<String, String> params) {
		String fileName = Const.getStrValue(params, "fileName");
		
		List<EsTerminal> esTerminals = EsTerminalImportExcelUtil.readExcel(file,fileName);
		if(esTerminals==null || esTerminals.size()==0)
			return "0";
		int total = esTerminals.size();
		int success = 0;
		for(int i=0;i<esTerminals.size();i++){
			EsTerminal esTerminal = esTerminals.get(i);
			if(checkSnIsExist(esTerminal.getSn())>0){
				Map where=new HashMap();
				where.put("sn", esTerminal.getSn());
				this.baseDaoSupport.update("es_gd_es_terminal", esTerminal, where);
			}else{
				this.baseDaoSupport.insert("es_gd_es_terminal", esTerminal);
			}
			success++;
		}
		return total+"#"+success+"#"+(total-success);
	}
	
	/**
	 * @author zhengchuiliu
	 */
	@Override
	public int checkSnIsExist(String sn) {
		String sql = SF.goodsSql("IS_EXISTS_SN");
		int count = baseDaoSupport.queryForInt(sql, sn);
		return count;
	}
	
	/**
	 * @author zengxianlian
	 * 选择商品批量导入
	 */
	@Override
	public String goodsBatchPublishByChoice(String goodsIds,String orgIds){
		HashMap map = new HashMap();
		map.put("esgoodscos", goodsIds);
		esGoodsM.liberacion(orgIds,map);
		int size = goodsIds.split(",").length;
		String msg = "成功发布"+size+"个商品！";
		//String msg = "成功发布货品！";
		return msg;
	}
	
	/**
	 * @author zengxianlian
	 * 选择货品批量导入
	 */
	@Override
	public String productsBatchPublishByChoice(String goodsIds,String orgIds){
		HashMap map = new HashMap();
		map.put("productos", goodsIds);
		productoM.liberacion(orgIds, map);
		int size = goodsIds.split(",").length;
		String msg = "成功发布"+size+"个货品！";
		//String msg = "成功发布货品！";
		return msg;
	}

	/**
	 * 同步日志导出
	 */
	@Override
	public List querySynchLogsExport(Map<String, String> params) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String name = Const.getStrValue(params, "name");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String sku = Const.getStrValue(params, "sku");
		String status = Const.getStrValue(params, "status");
		String object_type = Const.getStrValue(params, "object_type");
		String sql1 = "";
		String sql2 = "";
		String sql = "";
		List pList = new ArrayList();
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(object_type) || StringUtils.isEmpty(object_type)){//商品同步日志
			sql1 = SF.goodsSql("GOODS_SYNCH_SUCCESS_LOG");  //同步成功
			sql2 = SF.goodsSql("GOODS_SYNCH_FAIL_LOG");  //同步失败
		}
		else if(Consts.ECS_QUERY_TYPE_PRODUCT.equals(object_type)){//货品同步日志
			sql1 = SF.goodsSql("PRODUCT_SYNCH_SUCCESS_LOG");  //同步成功
			sql2 = SF.goodsSql("PRODUCT_SYNCH_FAIL_LOG");  //同步失败
		}
		if(Consts.CO_QUEUE_STATUS_WFS.equals(status)){//未发送
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_WFS+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_FSZ.equals(status)){//发送中
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_FSZ+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_XYSB.equals(status)){//失败
			sql = sql2 +" and q.status='"+Consts.CO_QUEUE_STATUS_XYSB+"'";
		}
		else if(Consts.CO_QUEUE_STATUS_XYCG.equals(status)){//成功
			sql = sql1;
		}
		else{//全部
			sql = sql1+" union all "+sql2 + " and q.status in('"+Consts.CO_QUEUE_STATUS_WFS+"','"+Consts.CO_QUEUE_STATUS_FSZ+"','"+Consts.CO_QUEUE_STATUS_XYSB+"')";
		}
		sql = "select * from("+sql+") t where 1=1 ";
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and t.batch_id=?";
			pList.add(batch_id);
		}
		if(!StringUtils.isEmpty(name)){
			sql += " and t.name like '%" + name.trim() + "%'";
		}
		if(!StringUtils.isEmpty(start_date)){
			sql += " and t.created_date >=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(start_date+" 00:00:00");
		}
		if(!StringUtils.isEmpty(end_date)){
			sql += " and t.created_date <=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(end_date+" 23:59:59");
		}
		if(!StringUtils.isEmpty(sku)){
			sql += " and t.sku=? ";
			pList.add(sku);
		}
		List export_list=new ArrayList<Map>();
		List<Map> list=this.baseDaoSupport.queryForList(sql,pList.toArray());
		CountGoodsSynAmount(list);
//		if(list!=null && list.size()>0){
//			for(Map data : list){
//				batch_id = Const.getStrValue(data, "batch_id");
//				String countSql = SF.goodsSql("CYNCH_NUM_COUNT");
//				List<Map> counts = this.baseDaoSupport.queryForList(countSql, new String[]{batch_id,batch_id,batch_id,batch_id});
//				Map countMap = new HashMap();
//				int batch_amount = 0;
//				for(int i=0;i<counts.size();i++){
//					Map result = counts.get(i);
//					String amount = result.get("amount").toString();
//					data.put(Const.getStrValue(result, "status"), amount);
//					batch_amount += Integer.valueOf(amount);
//				}
//				data.put("batch_amount", batch_amount);
//				String org_id_str = Const.getStrValue(data, "org_id_str");
//				if(!StringUtils.isEmpty(org_id_str)){
//					String org_name_str = this.baseDaoSupport.queryForString(SF.goodsSql("SYNCH_ORG_GET")+" and PARTY_ID IN("+org_id_str+")", null);
//					data.put("org_name_str", org_name_str);
//				}
//			}
//		}
		
		for(int i=0;i<list.size();i++){
			Map m=list.get(i);
			
			String act = "";
			String sta = "";
			String action_code = (String) m.get("action_code");
			String log_status = (String) m.get("status");
			
			if("A".equals(action_code)){
				act = "新增";
			}else if("M".equals(action_code)){
				act = "修改";
			}else if("D".equals(action_code)){
				act = "删除";
			}
			
			if("FSZ".equals(log_status)){
				sta = "发送中";
			}else if("WFS".equals(log_status)){
				sta = "未发送";
			}else if("XYSB".equals(log_status)){
				sta = "失败";
			}else if("XYCG".equals(log_status)){
				sta = "成功";
			}
			
			m.put("action_code", act);
			m.put("status", sta);
			
			export_list.add(m);
		}
		return export_list;
	}

	@Override
	public Map<String, String> checkPublishGoods(Map<String, String> params) {
		List<Product> productList = new ArrayList<Product>(0);
		List<String> rmKeys = new ArrayList<String>(0);
		for(String key : params.keySet()){
			StringBuffer noSynProduct = new StringBuffer();
			StringBuffer isSynGoods = new StringBuffer();
			String org = params.get(key);
			String[] orgData = org.split("-");
			String[] orgIds = orgData[0].split(",");
			String[] orgNames = orgData[1].split(",");
			productList = getProductRelByGoodsId(key);
			boolean flag = true;
			for(int i=0;i<orgIds.length;i++){
				if(!"10001".equals(orgIds[i])&&!"10003".equals(orgIds[i])){
					for(Product p : productList){
						int countAct = this.baseDaoSupport.queryForInt(SF.goodsSql("Products_Is_Sync"), p.getProduct_id(),"%"+orgIds[i]+"%");
						if(countAct<1){
							flag = false;
							noSynProduct.append("【"+p.getName()+"】货品没用同步到"+orgNames[i]+"商城,\n");
						}
					}
				}
			}
			if(flag){
				rmKeys.add(key);
				//params.remove(key);
			}else{
				params.put(key, noSynProduct.toString());
			}
		}
		if(rmKeys.size()>0){
			for(String keys : rmKeys){
				params.remove(keys);
			}
		}
		return params;
	}
	
	/**
	 * 商品导出
	 */
	@Override
	public Map queryGoodsExport(Map params) {
		String sql = "";
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		
		//add by zhengchuiliu
		String import_username = ManagerUtils.getStrValue(params, "import_username"); //导入人
		String previous_date = ManagerUtils.getStrValue(params, "previous_date");
		String last_update_date = ManagerUtils.getStrValue(params, "last_update_date");
		
		//zengxianlian
		String actCode =  ManagerUtils.getStrValue(params, "actCode");
		
		String log = ManagerUtils.getStrValue(params, "log");
		String oper_name = ManagerUtils.getStrValue(params, "oper_name");
		
		String[] tagid = (String[]) params.get("tagids");
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		@SuppressWarnings("unused")
		int founder = adminUser.getFounder();
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}

		//if (ManagerUtils.isProvStaff()) {// 管理员或电信员工
		if(Consts.CURR_FOUNDER_0.equals(founder+"")|| Consts.CURR_FOUNDER_1.equals(founder+"")){
			sql = SF.goodsSql("SEARCH_GOODS_0_2");
			if(null != actCode && !"".equals(actCode)){
				sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
			}
			sql+=" where 1=1  and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";
			
		}else if(Consts.CURR_FOUNDER6==founder){
			
            sql = SF.goodsSql("SEARCH_GOODS_6_1") ;
            if(null != actCode && !"".equals(actCode)){
				sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
			}
            sql+=" where 1=1 and g.staff_no='"+adminUser.getUserid()+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "' " ;

        }else { //供货商等
			sql = SF.goodsSql("SEARCH_GOODS_OTHER_1");
			 if(null != actCode && !"".equals(actCode)){
					sql += "  join es_goods_package egp on g.goods_id=egp.goods_id and egp.p_code='"+actCode+"' ";
				}	
			sql+="where  g.disabled = '" + Consts.GOODS_DISABLED_0 + "'  and (g.staff_no='"+adminUser.getParuserid()+"' or g.staff_no='"+adminUser.getUserid()+"') and g.source_from='"+ManagerUtils.getSourceFrom()+"'" ;// add by wui 父级管理员
		}
		if (order == null | "".equals(order)) {
			order = "create_time desc";
		}
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
			//sql += "  and m.model_code ='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		//add by ty
		if(previous_date!=null && !previous_date.equals("")){
			sql += " and g.last_modify >= to_date('"+previous_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(last_update_date!=null && !last_update_date.equals("")){
			sql += " and g.last_modify <= to_date('"+last_update_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
			String tagidstr = StringUtil.arrayToString(tagid, ",");
			sql += " and g.goods_id in(select rel_id from "
					+ this.getTableName("tag_rel") + " tg where tag_id in("
					+ tagidstr + ") and g.source_from = tg.source_from)";
		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and g.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + Integer.valueOf(market_enable);
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_goods_co co where g.goods_id=co.goods_id))";
			}else{
				sql += " and exists (select 1 from es_goods_co co where g.goods_id=co.goods_id and status="+Integer.valueOf(publish_state)+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct egc.goods_id) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}else{
			 //countSql = "select count(*) from " +sql.substring(sql.lastIndexOf("from es_goods g")+4);
		}
		
		//zhengchuiliu 添加导入人查询
		if(!StringUtils.isEmpty(import_username)){
			String goods_log_sql = SF.goodsSql("GOODS_IMPORT_LOGS_BY_OPERNAME");
			List<Map> logs_list = this.baseDaoSupport.queryForList(goods_log_sql, new String[]{"%"+import_username+"%"});
			String goodids = "'";
			for(int i = 0; i < logs_list.size(); i++){
				Map map = logs_list.get(i);
				String productCode = (String) map.get("product_code");
				String atvCode = (String) map.get("atv_code");
				String atvMonths = (String) map.get("atv_months");
				String productName = (String) map.get("product_name");
				String modelCode = (String) map.get("model_code");
				String colorCode = (String) map.get("color_code");
				
				String contractNet = "3G";
				String isIphone = "0";
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("4g")>=0){
					contractNet = "4G";
				}
				if(!StringUtils.isEmpty(productName) && productName.toLowerCase().indexOf("iphone")>=0){
					isIphone = "1";
				}
				//查询套餐
				String offers_sql = SF.goodsSql("OFFER_GET_BY_ESSCODE");
				offers_sql += " and a.params like '%"+productCode+"%'";
				List<Map> offers = this.baseDaoSupport.queryForList(offers_sql);
				String offers_product_id = "";
				if(offers.size()>0){
					Map m = offers.get(0);
					offers_product_id = (String) m.get("product_id");
				}
				
				//查询合约计划
				String contracts_sql = SF.goodsSql("CONTRACT_GET_BY_SPEC");
				List<Map> contracts = this.baseDaoSupport.queryForList(contracts_sql, new String[]{atvCode, atvMonths, contractNet, isIphone});
				String contracts_product_id = "";
				if(contracts.size()>0){
					Map m = contracts.get(0);
					contracts_product_id = (String) m.get("product_id");
				}
				
				//查询终端
				String terminals_sql = SF.goodsSql("TERMINAL_BY_MODELCODE_COLOR");
				List<Map> terminals = this.baseDaoSupport.queryForList(terminals_sql, modelCode, colorCode);
				String terminals_product_id = "";
				if(terminals.size()>0){
					Map m = terminals.get(0);
					terminals_product_id = (String) m.get("product_id");
				}
				
				//查询商品
				String goods_sql = SF.goodsSql("CONTRACT_MACHINE");
				List<Map> goodsList = this.baseDaoSupport.queryForList(goods_sql, new String[]{terminals_product_id, contracts_product_id, offers_product_id});
				for(int j=0; j<goodsList.size(); j++){
					Map m = goodsList.get(j);
					String goodsId = (String) m.get("goods_id");
					goodids = goodids + goodsId + "','";
				}
			}
			goodids += "'";
			sql+=" and g.goods_id in ("+ goodids +")";
		}
		
		sql +=statsWhereCond.toString();
		sql += " order by g." + order;
		
		if(log.equals("yes")){
			sql=sql.replaceFirst("from es_goods", ",g.oper_name,g.oper_date from es_goods_l");
			
			if(oper_name!=null && !"".equals(oper_name)){
				sql=sql.replaceFirst("where 1=1", "where 1=1 and g.oper_name like '%"+oper_name+"%' ");
			}
		}
		List list = this.baseDaoSupport.queryForList(sql,new String[]{});
		List exportList1=new ArrayList();
		List exportList2=new ArrayList();
		List exportList3=new ArrayList();
		List exportList4=new ArrayList();
		List exportList5=new ArrayList();
		List exportList6=new ArrayList();
		Map expMap=new HashMap();
		for(int i=0;i<list.size();i++){			
			Map good = (Map) list.get(i);
			String goods_id = (String) good.get("goods_id");
			List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PUBLISH_ORG_GET"), goods_id);
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
					
				}
			}
			good.put("agent_name", org_names);
			
			if(good.get("market_enable").toString().equals("1")){
				good.put("market_enable","启用");
			}else{
				good.put("market_enable","停用");
			}

			if(publish_status==0){
				good.put("publish_status","未发布");
			}else if(publish_status==1){
				good.put("publish_status","已发布");
			}else if(publish_status==2){
				good.put("publish_status","发布中");
			}else if(publish_status==3){
				good.put("publish_status","发布失败");
			}else{
				good.put("publish_status","");
			}	
			
			//商品下的货品
			Map mapProduct=returnProducts(goods_id);
			if(mapProduct!=null){
				good.put("products", Const.getStrValue(mapProduct, "products"));
				good.put("productSku", Const.getStrValue(mapProduct, "productSku"));
				good.put("terminalNo", Const.getStrValue(mapProduct, "terminalNo"));
			}
			
			//商品包
			Map goodsTagMap =returnTag(goods_id);
			good.put("tag_name", goodsTagMap.get("tag_name"));
			
			//总部活动、商品编码、总部商品编码
			Map goodsPkgMap=returnPkgMap(goods_id);
			good.put("act_code", goodsPkgMap.get("p_code"));
			good.put("prod_code", goodsPkgMap.get("sn"));

			//当前生效活动			
			returnActivity(log,good,goods_id);
			
			//商品参数
			returnGoodsParams(log,good,goods_id);
			
			//发布商城			
			good.put("sale_store", returnSaleStore(type,goods_id));				
			
			//卡类型
			returnCardType(log,good,goods_id);			
			
			
			String type_id = (String) good.get("type_id");
			if(type_id.equals(GOODS_TYPE_CONTRACT_MACHINE)){
				exportList1.add(good);
			}else if(type_id.equals(GOODS_TYPE_NUM_CARD)){
				exportList2.add(good);
			}else if(type_id.equals(GOODS_TYPE_WIFI_CARD)){
				exportList3.add(good);
			}else if(type_id.equals(GOODS_TYPE_PHONE)){
				exportList4.add(good);
			}else if(type_id.equals(GOODS_TYPE_GIFT)){
				exportList5.add(good);
			}else if(type_id.equals(GOODS_TYPE_ADJUNCT)){
				exportList6.add(good);
			}
		}
		expMap.put(GOODS_TYPE_CONTRACT_MACHINE, exportList1);
		expMap.put(GOODS_TYPE_NUM_CARD, exportList2);
		expMap.put(GOODS_TYPE_WIFI_CARD, exportList3);
		expMap.put(GOODS_TYPE_PHONE, exportList4);
		expMap.put(GOODS_TYPE_GIFT, exportList5);
		expMap.put(GOODS_TYPE_ADJUNCT, exportList6);
		return expMap;
	}
	
	/**
	 * 商品下的货品
	 */
	public Map returnProducts(String goods_id){
		String sqlProduct = "select p.*,c.terminal_no from es_product p left join es_goods_rel r on p.product_id=r.product_id left join es_gd_es_terminal c on p.sn=c.sn where p.source_from=r.source_from and p.source_from='"+ManagerUtils.getSourceFrom()+"' and r.a_goods_id=?";
		List<Map> productList = this.baseDaoSupport.queryForList(sqlProduct, goods_id);
		String products="";
		String productSku="";
		String terminalNo="";
		for (int k = 0; k < productList.size(); k++) {
			Map productMap=productList.get(k);
			String product_name=productMap.get("name")==null?"":productMap.get("name").toString();
			String product_sku=productMap.get("sku")==null?"":productMap.get("sku").toString();
			String terminal_no=productMap.get("terminal_no")==null?"":productMap.get("terminal_no").toString();
			if(k>0){
				products += "|"+product_name;		
				productSku+= "|"+product_sku;	
				terminalNo+= "|"+terminal_no;	
			}
			if(k==0){
				products =product_name;		
				productSku =product_sku;
				terminalNo = terminal_no;	
			}
		}
		Map map=new HashMap();
		map.put("products", products);
		map.put("productSku", productSku);
		map.put("terminalNo", terminalNo);
		return map;
	}
	/**
	 * 商品包
	 */
	public Map returnTag(String goods_id){
		List<Map> datas = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_TAG_GET"), goods_id);
		Map goodsTagMap = datas.size()>0?datas.get(0):new HashMap();
		return goodsTagMap;
	}
	
	/**
	 * 总部活动、商品编码、总部商品编码
	 * @return
	 */
	@Override
	public Map returnPkgMap(String goods_id){
		List<Map> goodsPkgLst = this.baseDaoSupport.queryForList(SF.goodsSql("GOODS_PACKAGE_QUERY")+" and goods_id = ?", goods_id);
		Map goodsPkgMap = goodsPkgLst.size()>0?goodsPkgLst.get(0):new HashMap();
		return goodsPkgMap;
	}
	/**
	 * 当前生效活动
	 * @param goods_id
	 * @return
	 */
	public void returnActivity(String log,Map good,String goods_id){
		String sqlActivity="select distinct d.name, b.pmt_id,d.brief,c.pmt_type,TRUNC(d.end_time-d.begin_time) active_time,(select s.pname from es_dc_public s where s.stype = 1443 and s.pkey = c.pmt_type and s.source_from = '"+ManagerUtils.getSourceFrom()+"') pmt_type_name "
				+ "from es_goods a, es_pmt_goods b,  es_promotion c,  es_promotion_activity d where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=b.goods_id and b.pmt_id=c.pmt_id and c.pmta_id=d.id and d.enable = 1 and to_char(sysdate, 'yyyy-mm-dd HH24:MI:SS') between to_char(d.begin_time, 'yyyy-mm-dd HH24:MI:SS') and to_char(d.end_time, 'yyyy-mm-dd HH24:MI:SS') and a.goods_id = ?";
		if(log.equals("yes")){
			sqlActivity=sqlActivity.replaceFirst("es_goods", "es_goods_l");
			
		}
		List<Map> resultList=this.baseDaoSupport.queryForList(sqlActivity, goods_id);
		List<String> result=new ArrayList<String>();
		String 	activitynames="";
		String 	pmt_id="";
		String pmt_type_name="";
		String pmt_type="";
		String brief="";
		String active_time="";
		if(resultList!=null||resultList.size()>0){
			for (int j = 0; j < resultList.size(); j++){
				Map map=resultList.get(j);
				if(j==0){
					activitynames=Const.getStrValue(map, "name");
					pmt_id=Const.getStrValue(map, "pmt_id");
					pmt_type_name=Const.getStrValue(map, "pmt_type_name");
					pmt_type=Const.getStrValue(map, "pmt_type");
					brief=Const.getStrValue(map, "brief");
					active_time=Const.getStrValue(map, "active_time");
				}else{
					activitynames+="|"+Const.getStrValue(map, "name");
					pmt_id+="|"+Const.getStrValue(map, "pmt_id");
					pmt_type_name+="|"+Const.getStrValue(map, "pmt_type_name");
					pmt_type+="|"+Const.getStrValue(map, "pmt_type");
					brief+="|"+Const.getStrValue(map, "brief");
					active_time+="|"+Const.getStrValue(map, "active_time");
				}
			}
		}
		good.put("activity_names", activitynames);	
		good.put("pmt_id", pmt_id);	
		good.put("pmt_type_name", pmt_type_name);
		good.put("pmt_type", pmt_type);
		good.put("brief", brief);
		good.put("active_time", active_time);
	}
	
	/**
	 * 销售品参数
	 * @param good
	 * @param goods_id
	 */
	public Map returnGoodsParams(String log,Map good,String goods_id){
		String sqlParams = "select params from es_goods t where t.goods_id= ?";
		if(log.equals("yes")){
			sqlParams="select params from es_goods t where t.serial_no= ?";
			goods_id=Const.getStrValue(good, "serial_no");
		}
		Map goodsMap =new HashMap();
		Map paramsGoods=new HashMap();
		try{
			goodsMap= this.baseDaoSupport.queryForMap(sqlParams, goods_id);
			if(goodsMap!=null){
				String params_list = (String) goodsMap.get("params");			
				ProductPramasList pl=null;
				try {
					params_list = params_list.substring(1,params_list.lastIndexOf("]"));
					pl = JsonUtil.fromJson(params_list, ProductPramasList.class);
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
				}
				if (null != pl) {
					for (int j = 0; j < pl.getParamList().size(); j++) {
						Paramsenum ps = pl.getParamList().get(j);
						String ename = ps.getEname();
						String attr_code=ps.getAttrcode();
						String param_value = ps.getValue();
						String name = ps.getName();
						if(StringUtils.isNotEmpty(attr_code)){
							IDictManager dictManager = SpringContextHolder.getBean("dictManager");
							Map mapValue= dictManager.loadData(attr_code);
							List valueMap=(List)mapValue.get(attr_code); //CommonDataFactory.getInstance().getDcPublicDataByPkey( attr_code,param_value);
							for (int k = 0; k < valueMap.size(); k++) {
								Map map1=(Map) valueMap.get(k);
								String valueText=(String)map1 .get("value");
								if(valueText.equals(param_value)){
									String valueDesc=(String)map1 .get("value_desc");
									good.put(ename, valueDesc);
									paramsGoods.put(name, valueDesc);
								}
							}
						}else{
							good.put(ename, param_value);
							paramsGoods.put(name, param_value);
						}
					}
				}	
			}
		}catch(Exception e){
		}
		return paramsGoods;
	}
	
	
	/**
	 * 销售品参数
	 * @param good
	 * @param goods_id
	 */
	public Map returnLogsParams(Map good,String serial_no){
		String sqlParams = "select params from es_goods_l t where t.serial_no= ?";
		
		HashMap goodsMap = (HashMap) this.baseDaoSupport.queryForMap(sqlParams, serial_no);
		String params_list = (String) goodsMap.get("params");			
		ProductPramasList pl=null;
		Map paramsGoods=new HashMap();
		try {
			params_list = params_list.substring(1,params_list.lastIndexOf("]"));
			pl = JsonUtil.fromJson(params_list, ProductPramasList.class);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if (null != pl) {
			for (int j = 0; j < pl.getParamList().size(); j++) {
				Paramsenum ps = pl.getParamList().get(j);
				String ename = ps.getEname();
				String attr_code=ps.getAttrcode();
				String param_value = ps.getValue();
				String name = ps.getName();
				if(StringUtils.isNotEmpty(attr_code)){
					IDictManager dictManager = SpringContextHolder.getBean("dictManager");
					Map mapValue= dictManager.loadData(attr_code);
					List valueMap=(List)mapValue.get(attr_code); //CommonDataFactory.getInstance().getDcPublicDataByPkey( attr_code,param_value);
					for (int k = 0; k < valueMap.size(); k++) {
						Map map1=(Map) valueMap.get(k);
						String valueText=(String)map1 .get("value");
						if(valueText.equals(param_value)){
							String valueDesc=(String)map1 .get("value_desc");
							good.put(ename, valueDesc);
							paramsGoods.put(name, valueDesc);
						}
					}
				}else{
					good.put(ename, param_value);
					paramsGoods.put(name, param_value);
				}
			}
		}	
		return paramsGoods;
	}
	/**
	 * 销售品参数
	 * @param good
	 * @param goods_id
	 */
	public Map returnGoodsParamsExcel(Map good,String goods_id){
		String sqlParams = "select params from es_goods t where t.goods_id= ?";
		HashMap goodsMap = (HashMap) this.baseDaoSupport.queryForMap(sqlParams, goods_id);
		String params_list = (String) goodsMap.get("params");			
		ProductPramasList pl=null;
		Map paramsGoods=new HashMap();
		try {
			params_list = params_list.substring(1,params_list.lastIndexOf("]"));
			pl = JsonUtil.fromJson(params_list, ProductPramasList.class);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if (null != pl) {
			for (int j = 0; j < pl.getParamList().size(); j++) {
				Paramsenum ps = pl.getParamList().get(j);
				String ename = ps.getEname();
				String attr_code=ps.getAttrcode();
				String param_value = ps.getValue();
				String name = ps.getName();
				paramsGoods.put(name, ename);
			}
		}	
		return paramsGoods;
	}
	/**
	 * 发布商城
	 */
	public String returnSaleStore(String type,String goods_id){
		//发布商城
		String sqlSaleStore = null;
		 if("goods".equalsIgnoreCase(type)){
			 sqlSaleStore = SF.goodsSql("BATCH_GOODS_ORG_LIST");
		 }
		 else{
			 sqlSaleStore = SF.goodsSql("BATCH_PRODUCT_ORG_LIST");
		 }	    
	     List<Map> listSaleStore = this.daoSupport.queryForList(sqlSaleStore+" and g.goods_id="+goods_id,null);
		 String sale_store="";
		 for (int j = 0; j < listSaleStore.size(); j++) {
		    Map g=listSaleStore.get(j);
		    String org_name=g.get("org_name")==null?"":g.get("org_name").toString();		    	 
		    if(j>0){
		    	sale_store+="|"+org_name;
		    }else{
		    	sale_store=org_name;
			 }
		 }
		 return sale_store;
	}
	
	
	/**
	 * 商品卡类型
	 * @param goods_id
	 * @return
	 */
	public void returnCardType(String log,Map good,String goods_id){
		String machineSql="select b.machine_code from es_goods t,es_brand_model b where t.model_code=b.model_code and t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.goods_id = ?";
		if(log.equals("yes")){
			machineSql=machineSql.replaceFirst("es_goods", "es_goods_l");
		}
		List<Map> resultList=this.baseDaoSupport.queryForList(machineSql, goods_id);
		List<String> result=new ArrayList<String>();
		String 	machine_type="";
		if(resultList!=null||resultList.size()>0){
			for (int j = 0; j < resultList.size(); j++){
				Map map=resultList.get(j);
				String machine_code=Const.getStrValue(map, "machine_code");
				if(machine_code==null||machine_code.equals("")||machine_code.isEmpty()){
					continue;
				}
				String str="";
				if(machine_code.trim().equals("5869080000020140918001398")){
					str="nano卡 ";
				}else if(machine_code.trim().equals("5869080000020140918001435")){
					str="小卡";
				}else if(machine_code.trim().equals("5869080000020140918001397")){
					str="大卡";
				}
				if(j==0){
					machine_type=Const.getStrValue(map, "machine_code");
				}else{
					machine_type+="|"+Const.getStrValue(map, "machine_code");
				}
			}
		}
		good.put("machine_type", machine_type);	
	}
	
	/**
	 * 导入日志
	 */
	public String returnImportLog(Map good,String goods_id){
		 return "";
	}
	/**
	 * 货品导出
	 */
	@Override
	public List queryProductsExport(Map params) {
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		String log = ManagerUtils.getStrValue(params, "log");
		String oper_name = ManagerUtils.getStrValue(params, "oper_name");
		
		String sql = SF.goodsSql("SEARCH_PRODUCTS_6_1_LIST");
		sql += " and g.disabled = '" + Consts.GOODS_DISABLED_0 + "'";  //有效记录
		sql += " and p.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}
		
		if (order == null | "".equals(order)) {
			order = "create_time desc";
		}
		
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
//			sql += " and m.model_code='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
//		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
//			String tagidstr = StringUtil.arrayToString(tagid, ",");
//			sql += " and g.goods_id in(select rel_id from "
//					+ this.getTableName("tag_rel") + " tg where tag_id in("
//					+ tagidstr + ") and g.source_from = tg.source_from)";
//		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and p.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + market_enable;
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_product_co co where p.product_id=co.product_id))";
			}else{
				sql += " and exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+publish_state+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct epc.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}else{
			 //countSql = "select count(distinct p.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}
		
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
		}
		if(log.equals("yes")){
			sql=sql.replaceFirst("g.last_modify", "g.last_modify,g.oper_name,g.oper_date ");
			sql=sql.replaceFirst("es_goods g", "es_goods_l g");
			
			if(oper_name!=null && !"".equals(oper_name)){
				sql+=" and g.oper_name like '%"+oper_name+"%'";
			}
		}
		if(order.contains("sku")){
			sql += " order by p." + order;
		}else{
			sql += " order by g." + order;
		}
		List list = this.baseDaoSupport.queryForList(sql,new String[]{});
		for(int i=0;i<list.size();i++){
			Map good = (Map) list.get(i);
			String goods_id = (String) good.get("goods_id");
			String product_id =(String) good.get("product_id");
			List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET"), product_id);
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
				}
			}
			good.put("agent_name", org_names);
			
			if(good.get("market_enable").toString().equals("1")){
				good.put("market_enable","启用");
			}else{
				good.put("market_enable","停用");
			}
			
			if(publish_status==0){
				good.put("publish_status","未发布");
			}else if(publish_status==1){
				good.put("publish_status","已发布");
			}else if(publish_status==2){
				good.put("publish_status","发布中");
			}else if(publish_status==3){
				good.put("publish_status","发布失败");
			}else{
				good.put("publish_status","");
			}	
			
			
			//货品参数
			Map map=returnGoodsParamsExcel(good,goods_id);
			good.put("mapParams", map);
		}
		return list;
	}
	
	/**
	 * 根据批次标识批量查询商品/货品的同步信息
	 * 通过组装查询参数的方式去批量查询同步的统计信息，替换原来通过每个批次循环去查库的操作
	 * 【es_co_queue_bak 这个归档的日志表的数据比较大4000W，原来的调用方式会参数很大的延时】。
	 * 由于in（？，？，？...）方式组装参数有数量的限制（最多只能拼装1000个,方法里面按999为一批），所以超出1000的参数通过拆分SQL再UNION的方式解决这个问题。
	 * 如当前有2800个批次参数，需要拆分为(2800/999+1[如果有余数才+1])个SQL块，查询SQL为：
	 *     select * from (
	 *                (查询SQL:BATCH_CYNCH_NUM_COUNT) union  (查询SQL:SQL:BATCH_CYNCH_NUM_COUNT) union (查询SQL:SQL:BATCH_CYNCH_NUM_COUNT)
	 *             ) t order by t.batch_id
	 * ***/
	private void CountGoodsSynAmount(List<Map> pageList){
		if(pageList!=null && pageList.size()>0){
			int count = 1;
			String countSql = SF.goodsSql("BATCH_CYNCH_NUM_COUNT");
			if(pageList.size() > 999){
				count = pageList.size() / 999;
				if((pageList.size() % 999) > 0){
					count ++;
				}
			}
			
			String[] sqlList = new String[count]; //SQL块数据
			StringBuffer params = new StringBuffer();//保存参数占位符“?”
			List[] paramsArray = new ArrayList[count];//对应每个SQL块数据，保存对应的查询参数
			List paramList = new ArrayList();//保存每个SQL块的参数
		    int batchNum = 0;//参数个数
		    int index = 0;//数据位置
		    for(int j = 0 ; j < pageList.size(); j++){
		    	Map data =  pageList.get(j);
		    	String batch_id = Const.getStrValue(data, "batch_id");
		    	if(0 == batchNum){
		    		params.append("?");
		    	}else{
		    		params.append(",?");
		    	}
		    	paramList.add(batch_id);
		    	batchNum++;
	    		
		    	
		    	if(999 == batchNum){//参数已经达到上限，拆分SQL；组装参数
		    		sqlList[index] = countSql.replace("$PARAMS$", params.toString());
		    		combineParam(paramList);//组装参数
		    		paramsArray[index] = paramList;
		    		batchNum = 0;
		    		params = new StringBuffer();
		    		paramList = new ArrayList();
		    		index++;
		    		continue;
		    	}
		    	
		    	if(j == (pageList.size()-1)){
		    		sqlList[index] = countSql.replace("$PARAMS$", params.toString());
		    		combineParam(paramList);//组装参数
		    		paramsArray[index] = paramList;
		    	}
		    	
		    }
		    
		    
		    List synParams = new ArrayList();
		    for(int i = 0 ; i < paramsArray.length; i++){
		    	List paramsList = paramsArray[i];
		    	for(int j = 0 ; j < paramsList.size(); j++){
		    		synParams.add(paramsList.get(j));
		    	}
		    }
		    
		    //查询同步信息
		    String countSqlSum = "select * from (";
		    if(999 == pageList.size()){
		    	countSqlSum += sqlList[0];
		    }else if(1 >= count ){
		    	countSqlSum += countSql.replace("$PARAMS$", params.toString());
		    }else{
		    	
		    	for(int num = 0 ; num < sqlList.length; num++){
		    		if(0 == num){
		    			countSqlSum += "("+sqlList[num]+")";
		    		}else{
		    			countSqlSum += "union all ("+sqlList[num]+")";
		    		}
		    	}
		    }
		    countSqlSum += ") t ";
		    List<Map> counts = this.baseDaoSupport.queryForList(countSqlSum, synParams.toArray());
		    String countBatch = "";
			for(Map data : pageList){
				String batch_id = Const.getStrValue(data, "batch_id");
				int batch_amount = 0;
				for(int i = 0 ; i < counts.size(); i++){
					Map result = counts.get(i);
					String batch_id_temp = (String)result.get("batch_id");
					if(batch_id.equals(batch_id_temp)){
						String amount = ""+result.get("amount");
						data.put(Const.getStrValue(result, "status"), amount);
						batch_amount += Integer.valueOf(amount);
					}
				}
				
				if(StringUtils.isEmpty(Const.getStrValue(data, "WFS"))){
					data.put("WFS", "0");
				}
				
				if(StringUtils.isEmpty(Const.getStrValue(data, "FSZ"))){
					data.put("FSZ", "0");
				}
				
				if(StringUtils.isEmpty(Const.getStrValue(data, "XYSB"))){
					data.put("XYSB", "0");
				}
				
				if(StringUtils.isEmpty(Const.getStrValue(data, "XYCG"))){
					data.put("XYCG", "0");
				}
				
				data.put("batch_amount", batch_amount);
				String org_id_str = Const.getStrValue(data, "org_id_str");
				if(!StringUtils.isEmpty(org_id_str)){
					String org_name_str = this.baseDaoSupport.queryForString(SF.goodsSql("SYNCH_ORG_GET")+" and PARTY_ID IN("+org_id_str+")", null);
					data.put("org_name_str", org_name_str);
				}
			}
		    
		}
	}
	
	/**
	 * 组装参数对象，查询的SQL由四组SQL union all 组合，需要按照顺序组装查询参数
	 * **/
	private void combineParam(List paramList){
		String[] paramTemp1 = new String[paramList.size()];
		String[] paramTemp2 = new String[paramList.size()];
		String[] paramTemp3 = new String[paramList.size()];
		for(int i = 0 ; i < paramList.size();i++){
			paramTemp1[i] = (String)paramList.get(i);
			paramTemp2[i] = (String)paramList.get(i);
			paramTemp3[i] = (String)paramList.get(i);
		}
		
		int count = paramList.size();
		
		for(int i = 0 ; i < count; i++){
			paramList.add(paramTemp1[i]);
		}
		
		for(int i = 0 ; i < count; i++){
			paramList.add(paramTemp2[i]);
		}
		
		for(int i = 0 ; i < count; i++){
			paramList.add(paramTemp3[i]);
		}
	}

	@Override
	public List<Product> getProductRelByGoodsId(String goods_id) {
		String sql="SELECT T.* FROM es_product T left join es_goods_rel a on a.product_id=t.product_id  WHERE a.a_goods_id=? AND t.source_from=?";
        return this.daoSupport.queryForList(sql,Product.class,goods_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<Goods> checkSaveAdd(String typeCode, String checkData) {
		String sql = "";
		if(Consts.GOODS_TYPE_TERMINAL.equals(typeCode)){
			sql = "select t.* from es_goods t where t.type='product' and t.type_id = '10000' and t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.sn=?";
		}else if(Consts.GOODS_TYPE_OFFER.equals(typeCode)){
			sql = "select a.* from es_goods a where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.type_id = '10002' and a.type='product'  and a.params like ?";
			checkData = "%"+checkData+"%";
		}
		return this.baseDaoSupport.queryForList(sql, Goods.class, checkData);
	}
	
	@Override
	public List<Goods> checkGoodsSaveAdd(Map params) {
		String typeCode = (String)params.get("typeCode");
		String checkData = (String)params.get("sn");
		String cat_id = (String)params.get("cat_id");
//		if(Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(typeCode)){
			String[] ids = checkData.split(",");
			Goods goods = null;
			List proIds = new ArrayList();
			List<Product> products = null;
			for(String id : ids){
				goods = getGoods(id);
				if(null != goods){
					products = this.baseDaoSupport.queryForList(SF.goodsSql("GET_BY_GOODSID"), Product.class, goods.getGoods_id());
					String proId = products.get(0).getProduct_id();
					proIds.add(proId);
				}
			}
			
			List<Goods> returnList = new ArrayList();
			if(proIds.size()>0){
				StringBuffer sql = new StringBuffer();
				sql.append("select a.* from es_goods a where a.source_from = '"+ManagerUtils.getSourceFrom()+"' ");
				for(int i=0;i<proIds.size();i++){
					sql.append(" and exists (select 1 from es_goods_rel b where b.a_goods_id = a.goods_id and product_id =?  and b.source_from = '"+ManagerUtils.getSourceFrom()+"') ");
				}
				List<Goods> goodsList = this.baseDaoSupport.queryForList(sql.toString(), Goods.class, proIds.toArray());
				if(null!=goodsList&&goodsList.size()>0){//这里说明已有商品包含新商品所有的货品
					for(Goods good:goodsList){
						String sqlCount = "select count(1) from es_goods_rel b where b.a_goods_id = ? ";
						int count = this.baseDaoSupport.queryForInt(sqlCount, good.getGoods_id());
						if(count==proIds.size()){//如果既有商品货品数和新商品货品数相等,说明二者一致(这里默认同一商品不会存在相同id的货品)
							if(!Consts.GOODS_TYPE_PHONE.equals(typeCode)||//非裸机认为重复
									StringUtils.equals(good.getCat_id(), cat_id)){//商品小类相同认为重复(这里默认商品小类相同则商品大类必相同，符合系统现状)
								returnList.add(good);
								break;
							}
						}
					}
				}
			}
			return returnList;
//		}
//		return null;
	}


	/**
	 * 添加商品修改的操作日志
	 * 修改原有sql,p1保存goods_package中的p_code,p2保存goods_package中的sn---zengxianlian
	 */
	@Override
	public void insertOperLog(Map param) {
		//伤心日志
		String sql="insert into es_goods_l (goods_id,name,brand_id,cat_id,type_id,goods_type,weight,market_enable,"
				+ "brief,price,cost,mktprice,have_spec,create_time,last_modify,view_count,buy_count,"
				+ "disabled,store,point,intro,page_title,meta_keywords,meta_description,p20,p19,p18,p17,"
				+ "p16,p15,p14,p13,p12,p11,p10,p9,p8,p7,p6,p5,p4,p3,p2,p1,sord,have_field,isgroup,islimit,"
				+ "staff_no,grade,service_type,image_default,image_file,specs,adjuncts,crm_offer_id,type_code,"
				+ "state,creator_user,supper_id,audit_state,simple_name,specifications,min_nim,"
				+ "unit,crmid,stype_id,ctn,effect_date,fail_date,source_from,pay_price,sub_stype_id,search_key,"
				+ "sku,model_code,type,sn,params,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,"
				+ "serial_no,oper_no,oper_date,oper_name) "
				+ "select goods_id,name,brand_id,cat_id,type_id,goods_type,weight,market_enable,"
				+ "brief,price,cost,mktprice,have_spec,create_time,last_modify,view_count,buy_count,"
				+ "disabled,store,point,intro,page_title,meta_keywords,meta_description,p20,p19,p18,p17,"
				+ "p16,p15,p14,p13,p12,p11,p10,p9,p8,p7,p6,p5,p4,p3,?,?,sord,have_field,isgroup,islimit,"
				+ "staff_no,grade,service_type,image_default,image_file,specs,adjuncts,crm_offer_id,type_code,"
				+ "state,creator_user,supper_id,audit_state,simple_name,specifications,min_nim,"
				+ "unit,crmid,stype_id,ctn,effect_date,fail_date,source_from,pay_price,sub_stype_id,search_key,"
				+ "sku,model_code,type,sn,params,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,"
				+ " ?,?,sysdate,? from es_goods where goods_id=?";
		String serial_no=//DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14) 
				this.baseDaoSupport.getSequences("S_ES_GOODS_L_SERIAL_NO");	
		//不删除修改日志
//		String sqlOperLog="delete from es_goods_l where serial_no=(select serial_no from (select * from es_goods_l where goods_id=? order by serial_no desc) where rownum=1)";
//		if(!Const.getStrValue(param, "flag").equals("no")){
//			try {
//				this.baseDaoSupport.execute(sqlOperLog,Const.getStrValue(param, "goods_id"));
//			} catch (Exception e) {
//			}			
//		}
		this.baseDaoSupport.execute(sql,Const.getStrValue(param, "p2"),Const.getStrValue(param, "p1"),serial_no,Const.getStrValue(param, "oper_no"),Const.getStrValue(param, "oper_name"),Const.getStrValue(param, "goods_id"));
	}
	
	/**
	 * 添加货品关联商品表
	 */
	@Override
	public void goodsRelLog(boolean flag,Map param){
		//商品关联商品的日志
		String sql_goods_rel="insert into ES_GOODS_REL_L(a_goods_id,z_goods_id,rel_type,rel_attr_inst,rel_contract_inst,source_from,product_id,rel_code,serial_no,oper_no,oper_date,oper_name) "
				+ "select a_goods_id,z_goods_id,rel_type,rel_attr_inst,rel_contract_inst,source_from,product_id,rel_code,?,?,sysdate,? from ES_GOODS_REL where a_goods_id=?";
		String rel_serial_no=this.baseDaoSupport.getSequences("S_ES_GOODS_REL_L_SERIAL_NO");		
		String sqlGoodsRelLog="delete from ES_GOODS_REL_L where serial_no=(select serial_no from (select * from ES_GOODS_REL_L where a_goods_id=? order by serial_no desc) where rownum=1)";
		if(flag){
			try {
				this.baseDaoSupport.execute(sqlGoodsRelLog,param.get("goods_id").toString());
			} catch (Exception e) {
			}		
		}
		this.baseDaoSupport.execute(sql_goods_rel,rel_serial_no,Const.getStrValue(param, "oper_no"),Const.getStrValue(param, "oper_name"),Const.getStrValue(param, "goods_id"));
	}

	/**
	 * 比较商品
	 */
	@Override
	public List compareGoods(String[] serial_nos) {
		 String sql = SF.goodsSql("GET_GOODS_EDITDATA_LOG");
		 List param = new ArrayList();
	     StringBuilder builder = new StringBuilder(1000);
	     builder.append(sql);
	     //builder.append(" and a.goods_id in(");
	     builder.append(" and a.serial_no in(");
	     for (String str : serial_nos) {
	         builder.append(str);
	         builder.append(",");
	     }
	     builder.deleteCharAt(builder.length() - 1);
	     builder.append(")");
	     List<Map> list = this.daoSupport.queryForList(builder.toString(),null);
	     Map map1=list.get(0);
	     Map map2=list.get(1);
	     List<Map> listGoods=new ArrayList();
	     if(!Const.getStrValue(map1, "sku").equals(Const.getStrValue(map2, "sku"))){
	    	 map1.put("sku_l", 1);
	     }
	     if(!Const.getStrValue(map1, "name").equals(Const.getStrValue(map2, "name"))){
	    	 map1.put("name_l", 1);
	     }
	     if(!Const.getStrValue(map1, "simple_name").equals(Const.getStrValue(map2, "simple_name"))){
	    	 map1.put("simple_name_l", 1);
	     }
	     if(!Const.getStrValue(map1, "mktprice").equals(Const.getStrValue(map2, "mktprice"))){
	    	 map1.put("mktprice_l", 1);
	     }
	     if(!Const.getStrValue(map1, "price").equals(Const.getStrValue(map2, "price"))){
	    	 map1.put("price_l", 1);
	     }
	     if(!Const.getStrValue(map1, "weight").equals(Const.getStrValue(map2, "weight"))){
	    	 map1.put("weight_l", 1);
	     }
	     if(!Const.getStrValue(map1, "effect_date").equals(Const.getStrValue(map2, "effect_date"))){
	    	 map1.put("effect_date_l", 1);
	     }
	     if(!Const.getStrValue(map1, "fail_date").equals(Const.getStrValue(map2, "fail_date"))){
	    	 map1.put("fail_date_l", 1);
	     }
	     if(!Const.getStrValue(map1, "market_enable").equals(Const.getStrValue(map2, "market_enable"))){
	    	 map1.put("market_enable_l", 1);
	     }
	     if(!Const.getStrValue(map1, "store").equals(Const.getStrValue(map2, "store"))){
	    	 map1.put("store_l", 1);
	     }
	     if(!Const.getStrValue(map1, "point").equals(Const.getStrValue(map2, "point"))){
	    	 map1.put("point_l", 1);
	     }
	     if(!Const.getStrValue(map1, "unit").equals(Const.getStrValue(map2, "unit"))){
	    	 map1.put("unit_l", 1);
	     }
	     if(!Const.getStrValue(map1, "min_num").equals(Const.getStrValue(map2, "min_num"))){
	    	 map1.put("min_num_l", 1);
	     }
	     
	     //商品包
		 String sqlTag="select t.*,serial_no from es_tag_rel r,es_tags t,es_goods_l l where r.rel_id=l.goods_id and r.tag_id=t.tag_id and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		 StringBuilder builderTag = new StringBuilder(1000);
		 builderTag.append(sqlTag);
		 builderTag.append("  and l.serial_no in(");
		 builderTag.append(Const.getStrValue(map1, "serial_no"));
		 builderTag.append(",");
		 builderTag.append(Const.getStrValue(map2, "serial_no"));
		 builderTag.append(")");
		 List<Map> listTag = this.daoSupport.queryForList(builderTag.toString(),null);
		if (listTag != null && listTag.size() > 0) {
			Map map1Tag = listTag.get(0);
			Map map2Tag = listTag.get(1);
			if (Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map1Tag, "serial_no"))) {
				map1.put("tag_name", Const.getStrValue(map1Tag, "tag_name"));
				map2.put("tag_name", Const.getStrValue(map2Tag, "tag_name"));
				if (!Const.getStrValue(map1, "tag_name").equals(Const.getStrValue(map2, "tag_name"))) {
					map1.put("tag_name_l", 1);
				}
			} else if (Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map2Tag, "serial_no"))) {
				map1.put("tag_name", Const.getStrValue(map2Tag, "tag_name"));
				map2.put("tag_name", Const.getStrValue(map1Tag, "tag_name"));
				if (!Const.getStrValue(map1, "tag_name").equals(Const.getStrValue(map2, "tag_name"))) {
					map1.put("tag_name_l", 1);
				}
			}
		}
		 
		 //总部活动、商品编码、总部商品编码
		//修改原有逻辑,直接取p1,跟p2的值---zengxianlian
		if(!Const.getStrValue(map1, "p1").equals(Const.getStrValue(map2, "p1"))){
	    	 map1.put("act_code_l", 1);
	     }
	     if(!Const.getStrValue(map1, "p2").equals(Const.getStrValue(map2, "p2"))){
	    	 map1.put("prod_code_l", 1);
	     }
//	     if(Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map1Pakge, "serial_no"))){
//   	 map1.put("act_code", Const.getStrValue(map1Pakge, "act_code"));
//   	 map1.put("prod_code", Const.getStrValue(map1Pakge, "sn"));
//   	 map2.put("act_code", Const.getStrValue(map2Pakge, "act_code"));
//   	 map2.put("prod_code", Const.getStrValue(map2Pakge, "sn"));
//   	 if(!Const.getStrValue(map1, "act_code").equals(Const.getStrValue(map2, "act_code"))){
//   		 map1.put("act_code_l", 1);
//   	 }
//   	 if(!Const.getStrValue(map1, "prod_code").equals(Const.getStrValue(map2, "prod_code"))){
//   		 map1.put("prod_code_l", 1);
//   	 }
//    }else if(Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map2Pakge, "serial_no"))){
//   	 map1.put("act_code", Const.getStrValue(map2Pakge, "act_code"));
//   	 map1.put("prod_code", Const.getStrValue(map2Pakge, "sn"));
//   	 map2.put("act_code", Const.getStrValue(map1Pakge, "act_code"));
//   	 map2.put("prod_code", Const.getStrValue(map1Pakge, "sn"));
//   	 if(!Const.getStrValue(map1, "act_code").equals(Const.getStrValue(map2, "act_code"))){
//   		 map1.put("act_code_l", 1);
//   	 }
//   	 if(!Const.getStrValue(map1, "prod_code").equals(Const.getStrValue(map2, "prod_code"))){
//   		 map1.put("prod_code_l", 1);
//   	 }
//    }
//		 String sqlPakge="select a.*,l.serial_no from es_goods_package a ,es_goods_l l where l.goods_id=a.goods_id and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";
//		 StringBuilder builderPakge = new StringBuilder(1000);
//		 builderPakge.append(sqlPakge);
//		 builderPakge.append("  and l.serial_no in(");
//		 builderPakge.append(Const.getStrValue(map1, "serial_no"));
//		 builderPakge.append(",");
//		 builderPakge.append(Const.getStrValue(map2, "serial_no"));
//		 builderPakge.append(")");
//		 List<Map> listPakge = this.daoSupport.queryForList(builderPakge.toString(),null);
//		 if(listPakge!=null&&listPakge.size()>0){
//			 Map map1Pakge=listPakge.get(0);
//		     Map map2Pakge=listPakge.get(1);
//		     if(Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map1Pakge, "serial_no"))){
//		    	 map1.put("act_code", Const.getStrValue(map1Pakge, "act_code"));
//		    	 map1.put("prod_code", Const.getStrValue(map1Pakge, "sn"));
//		    	 map2.put("act_code", Const.getStrValue(map2Pakge, "act_code"));
//		    	 map2.put("prod_code", Const.getStrValue(map2Pakge, "sn"));
//		    	 if(!Const.getStrValue(map1, "act_code").equals(Const.getStrValue(map2, "act_code"))){
//		    		 map1.put("act_code_l", 1);
//		    	 }
//		    	 if(!Const.getStrValue(map1, "prod_code").equals(Const.getStrValue(map2, "prod_code"))){
//		    		 map1.put("prod_code_l", 1);
//		    	 }
//		     }else if(Const.getStrValue(map1, "serial_no").equals(Const.getStrValue(map2Pakge, "serial_no"))){
//		    	 map1.put("act_code", Const.getStrValue(map2Pakge, "act_code"));
//		    	 map1.put("prod_code", Const.getStrValue(map2Pakge, "sn"));
//		    	 map2.put("act_code", Const.getStrValue(map1Pakge, "act_code"));
//		    	 map2.put("prod_code", Const.getStrValue(map1Pakge, "sn"));
//		    	 if(!Const.getStrValue(map1, "act_code").equals(Const.getStrValue(map2, "act_code"))){
//		    		 map1.put("act_code_l", 1);
//		    	 }
//		    	 if(!Const.getStrValue(map1, "prod_code").equals(Const.getStrValue(map2, "prod_code"))){
//		    		 map1.put("prod_code_l", 1);
//		    	 }
//		     }
//		 }
		 
	     //当前生效活动
		 String sqlActivity="select a.goods_id,d.name from es_goods_l a, es_pmt_goods b,  es_promotion c,  es_promotion_activity d where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=b.goods_id and b.pmt_id=c.pmt_id and c.pmta_id=d.id and d.enable = 1 and to_char(sysdate, 'yyyy-mm-dd HH24:MI:SS') between to_char(d.begin_time, 'yyyy-mm-dd HH24:MI:SS') and to_char(d.end_time, 'yyyy-mm-dd HH24:MI:SS') and a.serial_no=?";
		 List<Map<String, String>> resultList=this.baseDaoSupport.queryForList(sqlActivity, Const.getStrValue(map1, "serial_no"));
		 List<String> result=new ArrayList<String>();
		 if(resultList!=null&&resultList.size()>0){
			 for (int j = 0; j < resultList.size(); j++)
			 result.add(resultList.get(j).values().iterator().next().trim());
			 String activitynames=StringUtils.join(result, ",  ");
			 if(null==activitynames)
				activitynames="";
			 map1.put("activity_names", activitynames);
		 }
			 
		 List<Map<String, String>> resultList2=this.baseDaoSupport.queryForList(sqlActivity, Const.getStrValue(map2, "serial_no"));
		 List<String> result2=new ArrayList<String>();
		 if(resultList2!=null&&resultList2.size()>0){
			 for (int j = 0; j < resultList2.size(); j++)
				 result2.add(resultList2.get(j).values().iterator().next().trim());
				 String activitynames2=StringUtils.join(result2, ",  ");
				 if(null==activitynames2)
					 activitynames2="";
				 map2.put("activity_names", activitynames2);
		 }
		 if(!Const.getStrValue(map1, "activity_names").equals(Const.getStrValue(map2, "activity_names"))){
	    		map1.put("activity_names_l", 1);
	     }
	   
	  // 将本地存储的图片替换为静态资源服务器地址
		String image_file1 =Const.getStrValue(map1, "image_file");
		if (!StringUtil.isEmpty(image_file1)) {
			image_file1 = UploadUtil.replacePath(image_file1);
			map1.put("image_file", image_file1);
		}
		String image_file2 =Const.getStrValue(map2, "image_file");
		if (!StringUtil.isEmpty(image_file2)) {
			image_file2 = UploadUtil.replacePath(image_file2);
			map2.put("image_file", image_file2);
		}
		String intro1 = Const.getStrValue(map1, "intro");
		if (intro1 != null) {
			intro1 = UploadUtil.replacePath(intro1);
			map1.put("intro", intro1);
		}
		String intro2 = Const.getStrValue(map2, "intro");
		if (intro2 != null) {
			intro2 = UploadUtil.replacePath(intro2);
			map2.put("intro", intro2);
		}
		if(!intro1.equals(intro2)){
			map1.put("intro_l", 1);
		}
		
		
		//变更前商品下的货品
		String sqlProduct = SF.goodsSql("GOODS_REL_PRODUCT_LOG");
		List productList1 = this.baseDaoSupport.queryForList(sqlProduct, Const.getStrValue(map1, "goods_id"));
		map1.put("list", productList1);
		
		//变更后商品下的货品
		List productList2 = this.baseDaoSupport.queryForList(sqlProduct, Const.getStrValue(map2, "goods_id"));
		map2.put("list", productList2);
		
		//商品参数
		Map valuesMap1=returnLogsParams(map1, Const.getStrValue(map1, "serial_no"));
		Map valuesMap2=returnLogsParams(map2, Const.getStrValue(map2, "serial_no"));
		
	    listGoods.add(map1);
	    listGoods.add(map2);
	    listGoods.add(valuesMap1);
	    listGoods.add(valuesMap2);
//			List<String> htmlList = goodsPluginBundle.onFillEditInputData(goods);
//			editDTO.setGoods(goods);
//			editDTO.setHtmlList(htmlList);
	     return listGoods;
	}

	
	/**
	 * 比较商品
	 */
	@Override
	public List compareProducts(String[] goods_ids) {
		 String sql = SF.goodsSql("GET_GOODS_EDITDATA_LOG");
		 List param = new ArrayList();
	     StringBuilder builder = new StringBuilder(1000);
	     builder.append(sql);
	     builder.append(" and a.goods_id in(");
	     for (String str : goods_ids) {
	         builder.append(str);
	         builder.append(",");
	     }
	     builder.deleteCharAt(builder.length() - 1);
	     builder.append(")");
	     List<Map> list = this.daoSupport.queryForList(builder.toString(),null);
	     Map map1=list.get(0);
	     Map map2=list.get(1);
	     List<Map> listGoods=new ArrayList();
	     if(!Const.getStrValue(map1, "sku").equals(Const.getStrValue(map2, "sku"))){
	    	 map1.put("sku_l", 1);
	     }
	     if(!Const.getStrValue(map1, "name").equals(Const.getStrValue(map2, "name"))){
	    	 map1.put("name_l", 1);
	     }
	     if(!Const.getStrValue(map1, "simple_name").equals(Const.getStrValue(map2, "simple_name"))){
	    	 map1.put("simple_name_l", 1);
	     }
	     if(!Const.getStrValue(map1, "mktprice").equals(Const.getStrValue(map2, "mktprice"))){
	    	 map1.put("mktprice_l", 1);
	     }
	     if(!Const.getStrValue(map1, "price").equals(Const.getStrValue(map2, "price"))){
	    	 map1.put("price_l", 1);
	     }
	     if(!Const.getStrValue(map1, "weight").equals(Const.getStrValue(map2, "weight"))){
	    	 map1.put("weight_l", 1);
	     }
	     if(!Const.getStrValue(map1, "effect_date").equals(Const.getStrValue(map2, "effect_date"))){
	    	 map1.put("effect_date_l", 1);
	     }
	     if(!Const.getStrValue(map1, "fail_date").equals(Const.getStrValue(map2, "fail_date"))){
	    	 map1.put("fail_date_l", 1);
	     }
	     if(!Const.getStrValue(map1, "market_enable").equals(Const.getStrValue(map2, "market_enable"))){
	    	 map1.put("market_enable_l", 1);
	     }
	     if(!Const.getStrValue(map1, "store").equals(Const.getStrValue(map2, "store"))){
	    	 map1.put("store_l", 1);
	     }
	     if(!Const.getStrValue(map1, "point").equals(Const.getStrValue(map2, "point"))){
	    	 map1.put("point_l", 1);
	     }
	     if(!Const.getStrValue(map1, "unit").equals(Const.getStrValue(map2, "unit"))){
	    	 map1.put("unit_l", 1);
	     }
	     if(!Const.getStrValue(map1, "min_num").equals(Const.getStrValue(map2, "min_num"))){
	    	 map1.put("min_num_l", 1);
	     }
	     if(!Const.getStrValue(map1, "tag_name").equals(Const.getStrValue(map2, "tag_name"))){
	    	 map1.put("tag_name_l", 1);
	     }
	     if(!Const.getStrValue(map1, "act_code").equals(Const.getStrValue(map2, "act_code"))){
	    	 map1.put("act_code_l", 1);
	     }
	     if(!Const.getStrValue(map1, "prod_code").equals(Const.getStrValue(map2, "prod_code"))){
	    	 map1.put("prod_code_l", 1);
	     }
	     if(!Const.getStrValue(map1, "activity_names").equals(Const.getStrValue(map2, "activity_names"))){
	    	 map1.put("activity_names_l", 1);
	     }
	   
	  // 将本地存储的图片替换为静态资源服务器地址
		String image_file1 =Const.getStrValue(map1, "image_file");
		if (!StringUtil.isEmpty(image_file1)) {
			image_file1 = UploadUtil.replacePath(image_file1);
			map1.put("image_file", image_file1);
		}
		String image_file2 =Const.getStrValue(map2, "image_file");
		if (!StringUtil.isEmpty(image_file2)) {
			image_file2 = UploadUtil.replacePath(image_file2);
			map2.put("image_file", image_file2);
		}
		String intro1 = Const.getStrValue(map1, "intro");
		if (intro1 != null) {
			intro1 = UploadUtil.replacePath(intro1);
			map1.put("intro", intro1);
		}
		String intro2 = Const.getStrValue(map2, "intro");
		if (intro2 != null) {
			intro2 = UploadUtil.replacePath(intro2);
			map2.put("intro", intro2);
		}
		if(!intro1.equals(intro2)){
			map1.put("intro_l", 1);
		}
		
		Map valuesMap1=returnLogsParams(map1, Const.getStrValue(map1, "serial_no"));
		Map valuesMap2=returnLogsParams(map2, Const.getStrValue(map2, "serial_no"));
	    listGoods.add(map1);
	    listGoods.add(map2);
	    listGoods.add(valuesMap1);
	    listGoods.add(valuesMap2);
	    return listGoods;
	}
	@Override
	public Page searchProductOperLog(Map params, int page, int pageSize) {
		String order = ManagerUtils.getStrValue(params, "order");
		String type = ManagerUtils.getStrValue(params, "type");
		String name = ManagerUtils.getStrValue(params, "name");
		String supplier_id = ManagerUtils.getStrValue(params, "supplier_id");
		String catid = ManagerUtils.getStrValue(params, "cat_id");
		String brandId = ManagerUtils.getStrValue(params, "brand_id");
		String sn = ManagerUtils.getStrValue(params, "sn");
		String sku = ManagerUtils.getStrValue(params, "sku");
		String market_enable = ManagerUtils.getStrValue(params, "market_enable");
		String publish_state = ManagerUtils.getStrValue(params, "publish_state");
		String auditState = ManagerUtils.getStrValue(params, "auditState");
		//add by liqingyi
		String model_code = ManagerUtils.getStrValue(params, "model_code");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		String oper_name = ManagerUtils.getStrValue(params, "oper_name");
		
		String sql = SF.goodsSql("SEARCH_PRODUCTS_6_1");
		sql=sql.replaceFirst("es_goods", "es_goods_l");
		sql=sql.replaceFirst("from es_product p", ",g.oper_no,g.oper_name,g.oper_date,g.serial_no from es_product p");
		
		sql += " and g.disabled = '" + Consts.GOODS_DISABLED_0 + "'";  //有效记录
		sql += " and p.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		String oids = ManagerUtils.getStrValue(params, "org_ids");
		if(StringUtils.isNotBlank(oids)){
			if(oids.endsWith(",")){
				oids = oids.substring(0,oids.length()-1);
			}
		}
		
		if (order == null | "".equals(order)) {
			order = "create_time desc";
		}
		
		
		//add by liqingyi
		if(model_code!=null && !"".equals(model_code)){
//			sql += " and m.model_code='"+model_code+"' ";
			sql += "  and g.model_code = '"+model_code+"' ";
		}
		if(start_date!=null && !start_date.equals("")){
			sql += " and g.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date!=null && !end_date.equals("")){
			sql += " and g.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		if(oper_name!=null && !oper_name.equals("")){
			sql += " and g.oper_name like '%"+oper_name+"%'";
		}

		if(type!=null && !type.equals("")){
			sql += " and g.type='"+type+"'";
		}
		
		if (name != null && !name.equals("")) {
			String[] nameStrs = name.trim().split(" ");
			for(int i=0;i<nameStrs.length;i++){
				String str = nameStrs[i].trim();
				if(!StringUtils.isEmpty(str)){
					sql += "  and upper(g.name) like '%" + str.toUpperCase() + "%'";
				}
			}
		}
		
		if (supplier_id != null && !supplier_id.equals("")) {
			sql += "  and g.supper_id = '" + supplier_id.trim() + "'";
		}	
		
		if (catid != null && !catid.equals("")) {
			sql += "  and g.cat_id in(select cat_id from es_goods_cat where cat_path like '%|" + catid.trim()+ "%')";
		}
		
		if(StringUtils.isNotEmpty(brandId)){
			sql += "  and g.brand_id = '" + brandId+"'";
		}
		
//		if (!ArrayUtils.isEmpty(tagid)&&StringUtils.isNotEmpty(tagid[0])) {
//			String tagidstr = StringUtil.arrayToString(tagid, ",");
//			sql += " and g.goods_id in(select rel_id from "
//					+ this.getTableName("tag_rel") + " tg where tag_id in("
//					+ tagidstr + ") and g.source_from = tg.source_from)";
//		}
		
		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn = '" + sn.trim() + "'";
		}
		if(sku !=null && !sku.equals("")){
			sql += "   and p.sku = '" + sku.trim() + "'";
		}
		if (market_enable != null && !"".equals(market_enable)) {
			sql += "  and g.market_enable = " + market_enable;
		}
		if(publish_state !=null && !"".equals(publish_state)){
			if(Consts.PUBLISH_0.toString().equals(publish_state)){
				sql += " and (exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+Integer.valueOf(publish_state)+")"+
			           "    or not exists (select 1 from es_product_co co where p.product_id=co.product_id))";
			}else{
				sql += " and exists (select 1 from es_product_co co where p.product_id=co.product_id and status="+publish_state+")";
			}
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
		
		//String countSql = "";
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
			 //countSql = "select count(distinct epc.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}else{
			 //countSql = "select count(distinct p.product_id) from " +sql.substring(sql.lastIndexOf("from es_product p")+4);
		}
		
		if(StringUtils.isNotBlank(oids)){
			sql+=" and ego.party_id  in ("+oids+")";
		}
		if(order.contains("sku")){
			sql += " order by p." + order;
		}else{
			sql += " order by g." + order;
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,Goods.class,true,new String[]{});
		List list = webpage.getResult();
		for(int i=0;i<list.size();i++){
			Goods good = (Goods) list.get(i);
			String goods_id = good.getGoods_id();
			String product_id = good.getProduct_id();
			List<Map> coList = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_PUBLISH_ORG_GET"), product_id);
			StringBuilder sb = new StringBuilder();
			String org_names = "";
			Integer publish_status = Consts.PUBLISH_0;
			
			for(int j=0;j<coList.size();j++){
				Map co = coList.get(j);
				if(j>0){
					org_names += ",";
				}
				String org_name = (String)co.get("org_name");
				org_names += org_name;
				if(j==0){
					publish_status = (Integer)co.get("status");
					
				}
			}
			good.setAgent_name(org_names);
			good.setPublish_status(publish_status);
		}
		return webpage;
	}

	@Override
	public void deleteGoodsByID(String[] ids) {
		if (ids == null)
			return;
		String id_str = StringUtil.arrayToString(ids, ",");
		String sql="delete from es_goods a where a.goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql);
		sql="delete from es_product a where a.goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql);
		sql="delete from es_goods_rel  a where a.a_goods_id in (" + id_str + ")";
		this.baseDaoSupport.execute(sql);
	}

	/**
	 * @author zengxianlian
	 * 获取华盛商品数据
	 */
	@Override
	public Page getHSGoodsList(int page,int pageSize,String name,String sku,String isMatched){
		String sql = SF.goodsSql("HS_GOODS_LIST");
		if(StringUtils.isNotEmpty(name)){
			sql+=" and t.matnr = '"+name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(sku)){
			sql+=" and t.mtart = '"+sku.trim() + "'";
		}
		if(StringUtils.isNotEmpty(isMatched) && "1".equals(isMatched)){
			//匹配
			sql += " AND exists (select 1 from es_goods_package egp where egp.hs_matnr = t.matnr)";
		}else if(StringUtils.isNotEmpty(isMatched) && "0".equals(isMatched)){
			//未匹配
			sql += " AND not exists (select 1 from es_goods_package egp where egp.hs_matnr = t.matnr)";
		}
		return this.baseDaoSupport.queryForPage(sql, page, pageSize);
	}


	@Override
	public Map getPCodeAndSnByGoodsId(String goodsId) {
        String sql="select t.p_code,t.sn from es_goods_package t where t.source_from=? and t.goods_id=?";
        try{
    		Map goods = this.daoSupport.queryForMap(sql, ManagerUtils.getSourceFrom(),goodsId);
    		return goods;
        }catch(Exception e){
        	return new HashMap();
        }
	}
	
	@Override
	public boolean checkMatnrExists(String matnr){
		boolean flag = false;
		//判断物料号是否已关联商品
		String sql = "select count(1) from es_goods_package egp where egp.hs_matnr = ? and egp.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		int counts = this.baseDaoSupport.queryForInt(sql, matnr);
		if(counts > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<Map> getGoodsJoinActivities(String goodsId, String orderCity,
			String orderTime, int userType) {
		String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, " +
				" e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d," +
				" es_promotion_activity e, es_pmt_goods c, es_activity_co f where " +
				" c.goods_id = ? and c.pmt_id = d.pmt_id" +
				" and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10003' and f.status='1' " +
				" and (e.region = '"+cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"' or instr(e.region,?)>0) and " +
				" to_date(?,'yyyy-mm-dd hh24:mi:ss') between " +
				" e.begin_time and e.end_time and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = d.source_from " +
				" and f.source_from = e.source_from and f.source_from = c.source_from " +
				" and d.pmt_type in  ('006','011') and e.enable=1 and e.user_type in (1,?) order by e.status_date";
		logger.info(sql);
		List<Map> activityList = this.baseDaoSupport.queryForList(sql,goodsId,orderCity,orderTime,userType);
		return activityList;
	}
	
	@Override
	public Map getStdGoodsByName(String goodsName,String titleName){
		String sql = "select a.* from es_std_goods_config a where a.goods_name=?";
		List pList = new ArrayList();
		pList.add(goodsName);
		if(!StringUtils.isEmpty(titleName)){
			sql += " and a.title_name=? ";
			pList.add(titleName);
		}
		List goodsList = this.baseDaoSupport.queryForList(sql, pList.toArray());
		Map goodsMap = new HashMap();
		if(goodsList!=null && goodsList.size()>0){
			goodsMap = (Map) goodsList.get(0);
		}
		return goodsMap;
	}

	/**
	 * 保存商品关联小区的相关信息(单宽带类商品)
	 * @param goods_id
	 * @param communityCodes
	 * @return
	 */
	public void saveGoodsRelCommunity(String goods_id,String communityCodes) {
		this.baseDaoSupport.execute("delete from es_goods_rel_community where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(communityCodes)){
			String[] arr_communityCodes = communityCodes.split(",");
			for (String communityCode : arr_communityCodes) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("community_code", communityCode);
				map.put("status", "1");
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_community", map);
			}
		}
		
	}
	
	/**
	 * 保存商品关联区县的相关信息(单宽带类商品)
	 * @param goods_id
	 * @param communityCodes
	 * @return
	 */
	public void saveGoodsRelCounty(String goods_id,String countyIds) {
		this.baseDaoSupport.execute("delete from es_goods_rel_county where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(countyIds)){
			String[] arr_countyIds = countyIds.split(",");
			for (String countyid : arr_countyIds) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("countyid", countyid);
				map.put("status", "1");
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_county", map);
			}
		}
		
	}

	/**
	 * 保存商品关联工号的相关信息
	 * @param goods_id
	 * @param staffIds
	 * @return
	 */
	public void saveGoodsRelStaff(String goods_id,String staffIds) {
		this.baseDaoSupport.execute("delete from es_goods_rel_staff where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(staffIds)){
			String[] arr_staffIds = staffIds.split(",");
			for (String staffid : arr_staffIds) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("staff_id", staffid);
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_staff", map);
			}
		}
		
	}
	
	/**
	 * 保存商品关联客户的相关信息
	 * @param goods_id
	 * @param communityCodes
	 * @return
	 */
	public void saveGoodsRelCust(String goods_id,String custIds) {
		this.baseDaoSupport.execute("delete from es_goods_rel_cust where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(custIds)){
			String[] arr_custIds = custIds.split(",");
			for (String custid : arr_custIds) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("cust_id", custid);
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_cust", map);
			}
		}
		
	}
	
	/**
	 * 保存商品发展渠道的相关信息
	 * @param goods_id
	 * @param developIds
	 * @return
	 */
	public void saveGoodsRelDevelop(String goods_id,String developIds) {
		this.baseDaoSupport.execute("delete from es_goods_rel_develop where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(developIds)){
			String[] arr_developIds = developIds.split(",");
			for (String developid : arr_developIds) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("develop_rela_id", developid);
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_develop", map);
			}
		}
		
	}
	
	/**
	 * 保存商品受理渠道的相关信息
	 * @param goods_id
	 * @param officeIds
	 * @return
	 */
	public void saveGoodsRelOffice(String goods_id,String officeIds) {
		this.baseDaoSupport.execute("delete from es_goods_rel_office where goods_id=?", goods_id);
		if(!StringUtil.isEmpty(officeIds)){
			String[] arr_officeIds = officeIds.split(",");
			for (String officeid : arr_officeIds) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("goods_id", goods_id);
				map.put("office_rela_id", officeid);
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel_office", map);
			}
		}
		
	}
	
	/**
	 * 保存泛智能终端活动元素相关信息
	 * @param goods_id
	 * @param s_scheme_id
	 * @param s_element_type
	 * @return
	 */
	public void saveGoodsElement(String goods_id,String s_scheme_id,String s_element_type) {
		if(!StringUtil.isEmpty(s_scheme_id)){
			this.baseDaoSupport.execute("delete from es_goods_action_element where goods_id=?", goods_id);
			if(!StringUtil.isEmpty(s_scheme_id)&&!StringUtil.isEmpty(s_element_type)){
				String sql = " select scheme_id,scheme_name,element_id,element_name,element_type,must_type, "
						   + " mobile_type,terminal_name,(select p.pname from es_dc_public_ext p where p.pkey=element_type and p.stype='element_type') as element_type_n "
						   + " from es_goods_action_element where element_type=? and scheme_id=? and goods_id is null";
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				sql = cacheUtil.getConfigInfo("qry_active_element_sql")==null?sql:cacheUtil.getConfigInfo("qry_active_element_sql");
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				List<ActiveElementInfo> list = baseDaoSupport.queryForList(sql, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int c) throws SQLException {
						Map data = new HashMap();
						    data.put("scheme_id", rs.getString("scheme_id"));
						    data.put("scheme_name", rs.getString("scheme_name"));
						    data.put("element_id", rs.getString("element_id"));
						    data.put("element_name", rs.getString("element_name"));
						    data.put("element_type", rs.getString("element_type"));
						    data.put("must_type", rs.getString("must_type"));
						    data.put("mobile_type", rs.getString("mobile_type"));
						    data.put("element_type_n", rs.getString("element_type_n"));
						    data.put("terminal_name", rs.getString("terminal_name"));
						return data;
					}
				}, new String[]{s_element_type,s_scheme_id});
				for (int i = 0; i < list.size(); i++) {
					Map map =  (Map) list.get(i);
					map.remove("element_type_n");
					map.put("goods_id", goods_id);
					this.baseDaoSupport.insert("es_goods_action_element", map);
				}
			}
		}
		
		
	}
	
	@Override
	public void saveGoodsElements(String goods_ids,String s_element_type){
		goods_ids = "'"+goods_ids.replace(",", "','")+"'";
		String sql = " select distinct t.scheme_id,t.goods_id from es_goods_action_element t where t.goods_id in ("+goods_ids+") ";
		List<Map> scheme_list = this.baseDaoSupport.queryForList(sql, null);
		for (int i = 0; i < scheme_list.size(); i++) {
			Map scheme_map = scheme_list.get(i);
			String scheme_id = Const.getStrValue(scheme_map, "scheme_id");
			String goods_id = Const.getStrValue(scheme_map, "goods_id");
			saveGoodsElement(goods_id,scheme_id,s_element_type);
		}
	}
	/*
	 * 添加商品中，获取小区相关信息(单宽带类商品)
	 */
	@Override
	public Page searchCommunityInfo(String communityName, int page, int pageSize) {
		String sql = "select t.* from es_community t";
		if(StringUtils.isNotBlank(communityName)){
			sql += " where t.community_name like '%"+communityName+"%'";
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,CommunityActivity.class,true,new String[]{});
		return webpage;
	}
	
	/*
	 * 添加商品中，获取县区相关信息
	 */
	@Override
	public Page searchCountryInfo(String areadef, String countyname, String region_type, int page, int pageSize) {
		String sql = "select t.areadef,t.areaid,t.countyname,t.countyid,t.hq_countyid from es_county t where t.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		if(StringUtils.isNotBlank(areadef)){
			sql += " and t.areadef like '%"+areadef+"%'";
		}
		if(StringUtils.isNotBlank(countyname)){
			sql += " and t.countyname like '%"+countyname+"%'";
		}
		if(StringUtils.isNotBlank(region_type)){
			sql += " and t.region_type = '"+region_type+"'";
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,Country.class,true,new String[]{});
		return webpage;
	}
	
	@Override
	public Page searchGoodsInfo(String sku, String name, String goods_type, int page, int pageSize){
		String sql = "select t.sku,t.name,a.name cat_name from es_goods t,es_goods_cat a where t.cat_id = a.cat_id "
				+ " and t.type = 'goods' and t.source_from = '"+ManagerUtils.getSourceFrom()+"'" + " and t.disabled = '0' ";
		if(StringUtils.isNotBlank(sku)){
			sql += " and t.sku like '%"+sku+"%'";
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and t.name like '%"+name+"%'";
		}
		if(StringUtils.isNotBlank(goods_type)){
			sql += " and t.type_id = '"+goods_type+"'";
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,Goods.class,true,new String[]{});
		return webpage;
	}
	
	@Override
	public Page searchTagInfo(String tag_group_type, String name, String tag_code, int page, int pageSize){
		String table = "es_sale_tag";
		if(StringUtils.equals("goods", tag_group_type)){
			table = "es_goods_tag";
		}
		String sql = "select t.tag_code,t.tag_name from "+table+" t "
				+ " where t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.tag_type = 'G'";
		
		if(StringUtils.isNotBlank(name)){
			sql += " and t.tag_name like '%"+name+"%'";
		}
		if(StringUtils.isNotBlank(tag_code)){
			sql += " and t.tag_code = '"+tag_code+"'";
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,Tag.class,true,new String[]{});
		return webpage;
	}
	
	@Override
	public List<Map> qryAdmissibleBroadbandGoods(String communityCode,String type){
		String sql = "select distinct a.name prod_offer_name,a.goods_id prod_offer_code from es_goods a,es_goods_rel_community c,es_community d "+
					 "where a.goods_id=c.goods_id and a.source_from=c.source_from and c.community_code=d.community_code and d.community_code = ? and a.type_id =? and d.source_from = ?";
		sql += "union select distinct a.name prod_offer_name, a.goods_id prod_offer_code from es_goods a, es_goods_rel_county c, es_county d "
				+ " where a.goods_id = c.goods_id and a.source_from = c.source_from and c.countyid = d.countyid and d.areaid in (select dc.other_field_value from es_dc_public_dict_relation dc, es_community co  where dc.stype = 'bss_area_code' and co.city_code = dc.other_field_value and co.community_code = ?) and a.type_id = ? and d.source_from = ? and d.region_type='city'";
		List<Map> goodsList = this.baseDaoSupport.queryForList(sql, communityCode,type,ManagerUtils.getSourceFrom(), communityCode,type,ManagerUtils.getSourceFrom());
		return goodsList;
	}
	
	@Override
	/**
	 * 通过地市县分查询宽带套餐信息
	 */
	public List<Map> qryAdmissibleBroadbandGoodsByCityOrCounty(String countyId,String type){
		String sql = "select distinct a.name prod_offer_name,a.goods_id prod_offer_code from es_goods a , es_goods_rel_county b where a.goods_id = b.goods_id and a.type_id =? and b.countyid = ? and b.status = \'1\' and b.source_from = ?";
		List<Map> goodsList = this.baseDaoSupport.queryForList(sql, type,countyId,ManagerUtils.getSourceFrom());
		return goodsList;
	}
	
	@Override
	public List<CommunityActivity> getGoodsRelCommunityList(String goodsid){
		String sql = "select b.community_code,b.community_name from es_goods_rel_community a,es_community b where a.community_code=b.community_code and a.source_from=b.source_from and a.goods_id=? and a.source_from=?";
		return this.baseDaoSupport.queryForList(sql, CommunityActivity.class, goodsid,ManagerUtils.getSourceFrom());
	}

	
	@Override
	public void liberacion(String[]ids,String enable) throws Exception  {
		for (String id : ids) {
			Map salegoodsMap = this.baseDaoSupport.queryForMap("select * from es_sale_goods where 1=1 and sale_gid = ?",id);
////		      
		      String serial_no = this.baseDaoSupport.getSequences("serial_no");
		      Map<String,Object> sale_goods = new LinkedHashMap<String,Object>();
		      sale_goods.put("serial_no", serial_no);
		         
		      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		      Date date = new Date();
			  date = sdf.parse(MapUtils.getString(salegoodsMap, "create_time"));
	   
		      sale_goods.put("time", sdf.format(date));
		      sale_goods.put("source_system", "10009");
		      sale_goods.put("receive_system", MapUtils.getString(salegoodsMap, "publish_shop"));
		      
		      if(MapUtils.getString(salegoodsMap, "modifier_id") == null || MapUtils.getString(salegoodsMap, "modifier_id").equals("")) {
		      	sale_goods.put("action", "A");
		      }else {
		    	sale_goods.put("action", "M");
		      }
		      
		      if(enable.equals("2")) {
		    	  sale_goods.put("action", "D");
		      }
		      
		      sale_goods.put("goods_offer_code", MapUtils.getString(salegoodsMap, "sale_gid"));
		      sale_goods.put("goods_offer_name", MapUtils.getString(salegoodsMap, "sale_gname"));
		      sale_goods.put("channel", MapUtils.getString(salegoodsMap, "publish_shop"));
		      sale_goods.put("goods_offer_type", MapUtils.getString(salegoodsMap, "package_type"));
		      if(enable.equals("1")) {
		    	  sale_goods.put("goods_offer_state", "0");
		      } else if(enable.equals("2")) {
		    	  sale_goods.put("goods_offer_state", "1");
		      }
		      
		      List<Map> tagcodes = this.baseDaoSupport.queryForList("select * from es_goods_tag_rel a where a.sale_goods_id = ?", id);
		      String codes = new String();
		      int i = 0;
		      for (Map map : tagcodes) {
				String tag_group_id = MapUtils.getString(map, "tag_group_id");
				if(i == 0) {
					codes += tag_group_id;
				}else {
					codes += ","+tag_group_id;
				}
				i++;
			}
		      sale_goods.put("goods_tag_code", codes);

		      //销售商品中存在的多个商品
		      List<Map<String, Object>> goodsIdList= this.baseDaoSupport.queryForList("select z_goods_id from es_goods_rel where a_goods_id = ?", id);
		      
		      List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
		      for (Map map : goodsIdList) {
					String goodsid = MapUtils.getString(map, "z_goods_id");
					Map<String,Object> resultGood = this.baseDaoSupport.queryForMap("select * from es_goods where goods_id = ?", goodsid);
					
					Map<String,Object> goodsMap = new LinkedHashMap<String,Object>();
					
					goodsMap.put("prod_offer_code",MapUtils.getString(resultGood, "goods_id"));
					goodsMap.put("prod_offer_name", MapUtils.getString(resultGood, "name"));
					goodsMap.put("channel", "中国联通");
					goodsMap.put("prod_offer_type", MapUtils.getString(salegoodsMap, "package_type"));
					goodsMap.put("prod_offer_state", MapUtils.getString(resultGood, "market_enable"));
					goodsMap.put("prod_offer_price", MapUtils.getString(resultGood, "price"));
					
	 				String goodsSql = SF.goodsSql(  "GOODS_PACKAGE_QUERY");
					goodsSql += " and goods_id = ?";
					List<Map> goodsPkgLst = this.baseDaoSupport.queryForList(goodsSql, goodsid);
					
					if(goodsPkgLst != null && goodsPkgLst.size() == 1) {
						goodsMap.put("prod_hq_act_code", MapUtils.getString(goodsPkgLst.get(0), "p_code"));
						goodsMap.put("prod_hq_code", MapUtils.getString(goodsPkgLst.get(0), "sn"));
					}

					List<Map<String, Object>> countyList= this.baseDaoSupport.queryForList("select * from es_goods_rel_county where goods_id = ?",goodsid);
					if(countyList != null && countyList.size() >=1) {
						String countyids = "";
						int m = 0;
						for (Map<String, Object> map2 : countyList) {
							String countys = MapUtils.getString(map2, "countyid");
							if(m == 0) {
								countyids += countys;
							}else {
								countyids += ","+countys;
							}
							m++;
						}
						String countyid = MapUtils.getString(countyList.get(0), "countyid");
						Map<String,Object>countyMap = this.baseDaoSupport.queryForMap("select * from es_county  where countyid = ?", countyid);
						goodsMap.put("county", countyids);
						goodsMap.put("region_type", MapUtils.getString(countyMap, "region_type"));
					} else {
						goodsMap.put("county", "");
						goodsMap.put("region_type", "");
					}

					Map<String,Object>prod = new LinkedHashMap<String,Object>();
					
					//根据商品id找到关联的多个货品
					List<Map<String, Object>> prodsIdList= this.baseDaoSupport.queryForList("select z_goods_id from es_goods_rel where a_goods_id = ?", goodsid);
					
					List<Map<String, Object>> prodsList = new ArrayList<Map<String, Object>>();
					
					for (Map prodMap : prodsIdList) {
						String prodid = MapUtils.getString(prodMap, "z_goods_id");
						
						List<Map<String, Object>>resultProdList = this.baseDaoSupport.queryForList("select * from es_goods where goods_id = ?", prodid);
						if(resultProdList != null && resultProdList.size() == 1) {
							Map<String,Object> resultProd = resultProdList.get(0);
							
							Map<String,Object> prodsMap = new LinkedHashMap<String,Object>();
							prodsMap.put("goods_name", MapUtils.getString(resultProd, "name"));
							
							prodsMap.put("goods_brand", MapUtils.getString(this.baseDaoSupport.queryForMap("select name from es_brand where brand_id = ?", MapUtils.getString(resultProd, "brand_id")), "name"));
							prodsMap.put("goods_spec", MapUtils.getString(resultProd,"goods_id"));
							prodsMap.put("goods_state", MapUtils.getString(resultProd,"market_enable"));
							
							prodsList.add(prodsMap);
							
							
							//String转json
							String prodsParams = MapUtils.getString(resultProd, "params");
							if(prodsParams != null && !prodsParams.equals("") && !prodsParams.equals("[]")) {
								JSONObject prodsParamsJson = JSONObject.fromObject(prodsParams.substring(1, prodsParams.length()-1));
								//json转jsonArray
								net.sf.json.JSONArray jsonArr = prodsParamsJson.getJSONArray("paramList");
								//jsonArray转List
								List<Map> prods_attr_params_list = (List<Map>)JSONArray.toCollection(jsonArr, Map.class);
								List<Map> result_prods_attr = new ArrayList<Map>();
								
								for (Map prods_attr_map : prods_attr_params_list) {
									Map<String,Object> new_prods_attr = new LinkedHashMap<String,Object>();
									
									if(prods_attr_map != null) {
										new_prods_attr.put("param_code", MapUtils.getString(prods_attr_map, "ename"));
										new_prods_attr.put("param_name", MapUtils.getString(prods_attr_map, "name"));
										new_prods_attr.put("sku_attr", MapUtils.getString(prods_attr_map, "attrvaltype"));
										List param_value = getDcSqlByDcName(MapUtils.getString(prods_attr_map, "attrcode"));
										
										if(param_value == null || param_value.size() == 0 ) {
											new_prods_attr.put("param_value", MapUtils.getString(prods_attr_map, "value"));
											new_prods_attr.put("param_value_id", "");
										}else {
											for(int j=0;j<param_value.size();j++){
												String value = Const.getStrValue((Map)param_value.get(j),"value");
												String value_desc = Const.getStrValue((Map)param_value.get(j),"value_desc");
												if(StringUtil.equals(value, MapUtils.getString(prods_attr_map, "value"))){
													new_prods_attr.put("param_value", value_desc);
													break;
												}
											}
											new_prods_attr.put("param_value_id", MapUtils.getString(prods_attr_map, "value"));
										}
										
										
										
									}
									
									result_prods_attr.add(new_prods_attr);
								}
								
								prodsMap.put("goods_attr", result_prods_attr);
							} else {
								prodsMap.put("goods_attr", new ArrayList());
							}
						}
						
					}
					//货品信息
					goodsMap.put("goods", prodsList);
					
					String goodsParams = MapUtils.getString(resultGood, "params");
					if(goodsParams != null && !goodsParams.equals("") && !goodsParams.equals("[]")) {
						JSONObject json = JSONObject.fromObject(goodsParams.substring(1, goodsParams.length()-1));
						//json转jsonArray
						net.sf.json.JSONArray jsonArr = json.getJSONArray("paramList");
						//jsonArray转List
						List<Map> goods_attr_params_list = (List<Map>)JSONArray.toCollection(jsonArr, Map.class);
						List<Map> result_goods_attr = new ArrayList<Map>();
						
						for (Map map2 : goods_attr_params_list) {
							Map<String,Object> new_goods_attr = new LinkedHashMap<String, Object>();
							if(map2 != null) {
								if(MapUtils.getString(map2, "value") != null && !MapUtils.getString(map2, "value").equals("")) {
									new_goods_attr.put("param_code", MapUtils.getString(map2, "ename"));
									new_goods_attr.put("param_name", MapUtils.getString(map2, "name"));
									new_goods_attr.put("param_value_code", MapUtils.getString(map2, "attrcode"));
									List goods_param_value = getDcSqlByDcName(MapUtils.getString(map2, "attrcode"));
									
									if(goods_param_value == null || goods_param_value.size() == 0) {
										new_goods_attr.put("param_value", MapUtils.getString(map2, "value"));
										new_goods_attr.put("param_value_id", "");
									}else {
										for(int j=0;j<goods_param_value.size();j++){
											String value = Const.getStrValue((Map)goods_param_value.get(j),"value");
											String value_desc = Const.getStrValue((Map)goods_param_value.get(j),"value_desc");
											if(StringUtil.equals(value, MapUtils.getString(map2, "value"))){
												new_goods_attr.put("param_value", value_desc);
												break;
											}
										}
										new_goods_attr.put("param_value_id", MapUtils.getString(map2, "value"));
									}
									
									
									result_goods_attr.add(new_goods_attr);
								}
							}
						}	
						goodsMap.put("prod_offer_param", result_goods_attr);
					}else {
						goodsMap.put("prod_offer_param", new ArrayList());
					}
					goodsList.add(goodsMap);
					
				}
		      //商品信息
		      sale_goods.put("prod_offer",goodsList);
		      
		      Map finishMap = new HashMap();
		      finishMap.put("goods_offer_req", sale_goods);
		      JSONObject lastResult = JSONObject.fromObject(finishMap);
		      String seq_no = this.baseDaoSupport.getSequences("req_serial_no");
		      Map<String,Object> fields = new HashMap<String,Object>();
		      
		      fields.put("serial_no", seq_no); 
		      fields.put("source_from", ManagerUtils.getSourceFrom());
		      fields.put("request", lastResult.toString()); 
		      fields.put("sku", MapUtils.getString(salegoodsMap, "sku"));
		      fields.put("create_date", new Date());
		      
		      this.baseDaoSupport.insert("es_goods_req", fields, null);
			
		      //消息同步
		      MessageSyncReq req = new MessageSyncReq();
		      
		      req.setSerial_no(this.baseDaoSupport.getSequences("serial_no"));
		      req.setAction(MapUtils.getString(sale_goods, "action"));
		      req.setSeq_no(seq_no);
		      req.setSource_system("10009");
		      req.setReceive_system(MapUtils.getString(salegoodsMap, "publish_shop"));
		      req.setType("goods_offer");
		      req.setTime(sdf.format(new Date()));
		     		      
		      String source_from=ManagerUtils.getSourceFrom();
			  ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			  MessageSyncResp resp = client.execute(req, MessageSyncResp.class);
				
		}
	}
	
	@Override
	public List<Map> getDcSqlByDcName(String dcName) {
		List<Map> list = new ArrayList<Map>();
		StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='"+dcName+"'");
		List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
		if (dc_sql_list != null && dc_sql_list.size() > 0) {
			String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
			list = baseDaoSupport.queryForList(dc_sql);
		}
		return list;
	}
	
	@Override
	public List<Country> getGoodsRelCountryList(String goodsid){
		String sql = " select b.areadef,b.areaid,b.countyname,b.countyid,b.hq_countyid,b.region_type from es_goods_rel_county a,es_county b "
				+ " where b.countyid = a.countyid and a.source_from=b.source_from and a.goods_id=? and a.source_from=? ";
		return this.baseDaoSupport.queryForList(sql, Country.class, goodsid,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<String> getGoodsRelCustList(String goodsid) {
		String sql = "select cust_id from es_goods_rel_cust where goods_id = ?";
		return this.baseDaoSupport.queryForList(sql, goodsid);
	}
	
	@Override
	public List<String> getGoodsRelStaffList(String goodsid) {
		String sql = "select staff_id from es_goods_rel_staff where goods_id = ?";
		return this.baseDaoSupport.queryForList(sql, goodsid);
	}
	
	@Override
	public List<String> getGoodsRelDevelopRelaList(String goodsid) {
		String sql = "select develop_rela_id from es_goods_rel_develop where goods_id = ?";
		return this.baseDaoSupport.queryForList(sql, goodsid);
	}
	
	@Override
	public List<String> getGoodsRelOfficeRelaList(String goodsid) {
		String sql = "select office_rela_id from es_goods_rel_office where goods_id = ?";
		return this.baseDaoSupport.queryForList(sql, goodsid);
		
	}
	
	@Override
	public List<Goods> getGoodsTypeList(){
		String sql = " select t.type_id,t.name type_name from es_goods_type t where t.type = 'goods' and t.disabled = '0' and t.source_from=? ";
		return this.baseDaoSupport.queryForList(sql, Goods.class,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<Tag> getGoodsTagList(String goods_id){
		String sql = " select t.tag_code,t.tag_name,a.sort from es_goods_tag t,es_goods_tag_rel a "
				+ " where t.tag_code = a.tag_group_id and a.tag_group_type = 'goods' and a.sale_goods_id = ? and a.source_from = ? "
				+ " order by a.sort ";
		return this.baseDaoSupport.queryForList(sql, Tag.class, goods_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<Tag> getSaleTagList(String goods_id){
		String sql = " select t.tag_code,t.tag_name from es_sale_tag t,es_goods_tag_rel a "
				+ " where t.tag_code = a.tag_group_id and a.tag_group_type = 'sale' and a.sale_goods_id = ? and a.source_from = ? ";
		return this.baseDaoSupport.queryForList(sql, Tag.class, goods_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<Goods> getGoodsListRelSale(String sale_gid){
		String sql = " select b.sku,b.name,c.name cat_name from es_goods_rel t,es_sale_goods a,es_goods b,es_goods_cat c "
				+ " where t.a_goods_id = a.sale_gid and t.z_goods_id = b.goods_id and c.cat_id = b.cat_id "
				+ " and t.rel_type = 'SALE_GOODS_REL' and t.source_from = ? and a.sale_gid = ? ";
		List<Goods> a = this.baseDaoSupport.queryForList(sql, Goods.class, ManagerUtils.getSourceFrom(), sale_gid);
		return this.baseDaoSupport.queryForList(sql, Goods.class, ManagerUtils.getSourceFrom(), sale_gid);
	}
	
	@Override
	public List<String> getAgentNames(Map params) {
		String sale_gid = ManagerUtils.getStrValue(params, "sale_gid");
		String agentsSql = "";
		if(sale_gid == null || sale_gid.equals("")) {
			agentsSql = "select org_name from es_goods_zj_org a where a.org_code in (select publish_shop from es_sale_goods)";
		} else {
			agentsSql = "select org_name from es_goods_zj_org a where a.org_code in (select publish_shop from es_sale_goods where sale_gid ="+sale_gid+")";
		}
		
		
		
		
		return this.baseDaoSupport.queryForList(agentsSql, null);
	}
	
	@Override
	public Page searchSaleGoodsECS(Map params,int page,int pageSize){
		String sale_gid = ManagerUtils.getStrValue(params, "sale_gid");
		String sale_gname = ManagerUtils.getStrValue(params, "sale_gname");
		String package_type = ManagerUtils.getStrValue(params, "package_type");
		String market_enable = ManagerUtils.getStrValue(params, "publish_state");
		String start_date = ManagerUtils.getStrValue(params, "start_date");
		String end_date = ManagerUtils.getStrValue(params, "end_date");
		
		String sql = " select t.sale_gid,t.sku,t.sale_gname,a.name package_type,t.market_enable,t.create_time,t.publish_shop "
				+ " from es_sale_goods t,es_goods_type a where t.package_type = a.type_id and t.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		
		if(sale_gid !=null && !sale_gid.equals("")){//销售商品sku
			sql += " and t.sku = '" + sale_gid.trim() + "'";
		}
		if(sale_gname !=null && !sale_gname.equals("")){//销售商品名称
			sql += " and t.sale_gname like '%" + sale_gname.trim() + "%'";
		}
		if(package_type !=null && !package_type.equals("")){//商品分类
			sql += " and t.package_type = '" + package_type.trim() + "'";
		}
		if(market_enable !=null && !market_enable.equals("")){//发布状态
			sql += " and t.market_enable = '" + market_enable.trim() + "'";
		}
		if(start_date !=null && !start_date.equals("")){//开始时间
			sql += " and t.create_time >= to_date('"+start_date+" 00:00:01','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(end_date !=null && !end_date.equals("")){//结束时间
			sql += " and t.create_time <= to_date('"+end_date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		/**
		 * add Wcl
		 * 按时间排序商品
		 * End
		 */
		sql += " order by t.create_time desc";
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize,SaleGoods.class,true,new String[]{});
		
		List<SaleGoods> saleGoods = webpage.getResult();
		
		
		for (SaleGoods SaleGood : saleGoods) {
			//先查询所有外围系统
			String sysSql = "select org_name,org_code from es_goods_zj_org where org_level = '2'";
			List<Map> sysMap =  this.baseDaoSupport.queryForList(sysSql);
			List<AgentInfo> agentsName = new ArrayList();
			for (Map map : sysMap) {
				String org_name = MapUtils.getString(map, "org_name");
				String org_code = MapUtils.getString(map, "org_code");
								
				String infoSql = "select to_char(req_time,'yyyy-mm-dd hh24:mi:ss') as req_time,to_char(rsp_time,'yyyy-mm-dd hh24:mi:ss') as rsp_time,status from es_goods_req where receive_system ="+org_code+" and sku ="+SaleGood.getSku()+" order by req_time desc";
				List<Map<String,String>> agentInfoList = this.baseDaoSupport.queryForList(infoSql);
				if(agentInfoList != null && agentInfoList.size() != 0) {
					AgentInfo info = new AgentInfo();
					info.setAgent_name(org_name);
					info.setReq_time(agentInfoList.get(0).get("req_time"));
					info.setStatus(agentInfoList.get(0).get("status").equals("1")?"已发布":"已下架");
					info.setRsp_time(agentInfoList.get(0).get("rsp_time"));
					
					agentsName.add(info);
				}
				
			}
			SaleGood.setAgent_names(agentsName);			
		}
		
		return webpage;
		
	}
	
	@Override
	public void updateSaleMarketEnable(String sale_gid,int market_enable){
		Map map = new HashMap();
		String sql = " update es_sale_goods t set t.market_enable = "+market_enable+" where t.sale_gid in ("+sale_gid+")";
		this.baseDaoSupport.execute(sql);
	}
	
	@Override
	public void addSaleGoods(SaleGoods saleGoods, String skus,
			String tag_codes, String sale_tag_codes, String sort){
		
		Map saleGoodsMap = ReflectionUtil.po2Map(saleGoods);
		
		String sale_gid = this.baseDaoSupport.getSequences("S_ES_GOODS");
		saleGoods.setSale_gid(sale_gid);
		saleGoodsMap.put("sale_gid", sale_gid);
		
		//后台自动生成SKU
		String sku = sku = "59" + DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14) 
				+ this.baseDaoSupport.getSequences("S_ES_GOODS_SKU", "0", 6);
		saleGoodsMap.put("sku", sku);
		saleGoods.setSku(sku);
		
		this.baseDaoSupport.insert("es_sale_goods", saleGoodsMap);
		
		//保存销售商品和商品的关联信息
		if(StringUtils.isNotBlank(skus)){
			saveGoodsRelSale(sale_gid,skus);
		}
		//保存销售商品和商品标签组的关联信息
		if(StringUtils.isNotBlank(tag_codes)){
			saveGoodsTagRel(sale_gid,tag_codes,sort);
		}
		//保存销售商品和营销标签组的关联信息
		if(StringUtils.isNotBlank(tag_codes)){
			saveSaleTagRel(sale_gid,sale_tag_codes);
		}
	}
	
	@Override
	public void editSaleGoods(SaleGoods saleGoods, String skus,
			String tag_codes, String sale_tag_codes, String sort){
		
		Map saleGoodsMap = ReflectionUtil.po2Map(saleGoods);
		String sale_gid = saleGoods.getSale_gid();
		
		this.baseDaoSupport.update("es_sale_goods", saleGoodsMap, "sale_gid = '"+sale_gid+"'");
		
		//保存销售商品和商品的关联信息
		saveGoodsRelSale(sale_gid,skus);
		
		//保存销售商品和商品标签组的关联信息
		saveGoodsTagRel(sale_gid,tag_codes,sort);

		//保存销售商品和营销标签组的关联信息
		saveSaleTagRel(sale_gid,sale_tag_codes);
		
	}
	
	public void saveGoodsRelSale(String sale_gid,String skus) {
		this.baseDaoSupport.execute("delete from es_goods_rel where a_goods_id=?", sale_gid);
		if(!StringUtil.isEmpty(skus)){
			String[] arr_skus = skus.split(",");
			for (String sku : arr_skus) {
				String goods_id_sql = "select t.goods_id from es_goods t where t.sku = ? and t.type = 'goods' and t.source_from = '"+ManagerUtils.getSourceFrom()+"'";				
				String goods_id = this.baseDaoSupport.queryForString(goods_id_sql, sku);
				//判断商品是否已关联，防止重复
				String rel_sql = "select 1 from es_goods_rel t where t.z_goods_id = ? and t.a_goods_id = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"'";
				List relList = this.baseDaoSupport.queryForList(rel_sql, goods_id, sale_gid);
				if(!ListUtil.isEmpty(relList)){
					continue;
				}
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("a_goods_id", sale_gid);
				map.put("z_goods_id", goods_id);
				map.put("rel_type", "SALE_GOODS_REL");
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_rel", map);
			}
		}
		
	}
	
	public void saveGoodsTagRel(String sale_gid,String tag_codes,String sort) {
		this.baseDaoSupport.execute("delete from es_goods_tag_rel where sale_goods_id=? and tag_group_type = 'goods'", sale_gid);
		if(!StringUtil.isEmpty(tag_codes)){
			String[] arr_tag_codes = tag_codes.split(",");
			for (int i = 0; i < arr_tag_codes.length; i++) {
				String tag_code = arr_tag_codes[i];
				if(!StringUtil.isEmpty(sort)){
					String[] arr_sort = sort.split(",");
					for (int j = 0; j < arr_sort.length; j++) {
						String sortNumber = arr_sort[j];
						if(i==j){
							Map map = new HashMap();
							map.put("sale_goods_id", sale_gid);
							map.put("tag_group_id", tag_code);
							map.put("tag_group_type", "goods");
							map.put("sort", Integer.parseInt(sortNumber.trim()));
							map.put("source_from", "ECS");
							this.baseDaoSupport.insert("es_goods_tag_rel", map);
						}
					}
				}
			}
		}
		
	}
	
	public void saveSaleTagRel(String sale_gid,String sale_tag_codes) {
		this.baseDaoSupport.execute("delete from es_goods_tag_rel where sale_goods_id=? and tag_group_type = 'sale'", sale_gid);
		if(!StringUtil.isEmpty(sale_tag_codes)){
			String[] arr_sale_tag_codes = sale_tag_codes.split(",");
			for (String sale_tag_code : arr_sale_tag_codes) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("sale_goods_id", sale_gid);
				map.put("tag_group_id", sale_tag_code);
				map.put("tag_group_type", "sale");
				map.put("source_from", "ECS");
				this.baseDaoSupport.insert("es_goods_tag_rel", map);
			}
		}
		
	}
	
	@Override
	public Map querySaleGoods(String sale_gid){
		String sql = " select t.sale_gid,t.sku,t.sale_gname,t.public_title,t.selling_point1,t.selling_point2,t.selling_point3, "
				+ " t.package_type,t.publish_city,t.publish_shop,t.channel_type,t.intro,t.market_enable,t.mall_staff_id, "
				+ " t.creator_id,t.create_time,t.modifier_id,t.modify_time "
				+ " from es_sale_goods t where t.sale_gid = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		List<Map> saleGoodsViewList = this.baseDaoSupport.queryForList(sql, sale_gid);
		Map saleGoodsViewMap = saleGoodsViewList.get(0);
		String publish_city_id = ManagerUtils.getStrValue(saleGoodsViewMap, "publish_city");
		String publish_shop_id = ManagerUtils.getStrValue(saleGoodsViewMap, "publish_shop");
		
		String publish_city_sql = " select t.region_name from es_common_region t "
				+ " where t.parent_region_id = '330000' and t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.region_id = ? ";
		String publish_shop_sql = " select t.org_name from es_goods_org t where t.org_code = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		
		//获取活动地市名称
		String regionName = "";
		if(StringUtils.isNotBlank(publish_city_id)){
			String[] publish_city_ids = publish_city_id.split(",");
			for (int i = 0; i < publish_city_ids.length; i++) {
				String publish_city = this.baseDaoSupport.queryForString(publish_city_sql, publish_city_ids[i]);
				if(i == 0){
					regionName += publish_city;
				}else{
					regionName += "," + publish_city;
				}
			}
		}
		
		//获取活动商城名称
		String act_org_names = "";
		if(StringUtils.isNotBlank(publish_shop_id)){
			String[] publish_shop_ids = publish_shop_id.split(",");
			for (int i = 0; i < publish_shop_ids.length; i++) {
				String publish_shop = this.baseDaoSupport.queryForString(publish_shop_sql, publish_shop_ids[i]);
				if(i == 0){
					act_org_names += publish_shop;
				}else{
					act_org_names += "," + publish_shop;
				}
			}
		}
				
		saleGoodsViewMap.put("regionName", regionName);
		saleGoodsViewMap.put("act_org_names", act_org_names);
		
		return saleGoodsViewMap;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean saveBraoadband(Goods goods, String[] propvalues, List<Map<String,Object>> products,String relType){//生产宽带商品
		
		this.setGoodsDefaultValue(goods);
		try {
			checkSave(goods);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		Map goodsMap = ReflectionUtil.po2Map(goods);
		goodsCommonManager.removeEmptyFields(goodsMap);
		
		String goods_id = this.baseDaoSupport.getSequences("S_ES_GOODS");
		goods.setGoods_id(goods_id);
		goodsMap.put("goods_id", goods_id);
		
		if(StringUtils.isEmpty((String)goodsMap.get("sku"))){
			//后台自动生成SKU
			String sku = createSKU(goods.getType(), goods.getCat_id());
			goodsMap.put("sku", sku);
			goods.setSku(sku);
		}
		goodsCommonManager.setGoodsModelCode(goodsMap, goods.getType(), goods.getType_id(), products);
		goodsCommonManager.setGoodsProps(goodsMap, null, propvalues);
		
		this.baseDaoSupport.insert("goods", goodsMap);
		
		goodsCommonManager.savePricePriv( goods_id);
		
		//商品与货品关联表
		goodsCommonManager.saveGoodsRelations(goods_id, products,relType);
		//商品表
		goodsCommonManager.saveProductInfo(goodsMap, null, null);
		
		//添加商品，保存es_goods_package
		GoodsPackage goodsPackage = new GoodsPackage();
		String snString = this.baseDaoSupport.getSequences("S_ES_PRODUCT_SN", "0", 8);
		goodsPackage.setSn(snString);
		goodsPackage.setGoods_id(goods_id);
		goodsPackage.setSource_from(ManagerUtils.getSourceFrom());
		savaGoodsPackage(goodsPackage);
		
		//缓存商品货品
		//goodsManager.cacheGoods(goods);ricky test modify
		return true;
	}
	/**
	 * 设置商品货品默认值
	 * @param goods
	 */
	private void setGoodsDefaultValue(Goods goods){
		goods.setMarket_enable(1);
		goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
		goods.setCreate_time(DBTUtil.getDBCurrentTime());
		goods.setStaff_no("-1");
		goods.setCreator_user("1");
		goods.setGoods_type("normal");
		goods.setLast_modify(DBTUtil.current());
		goods.setSearch_key("_admin__"+goods.getName());
		
		goods.setDisabled(0);
		goods.setView_count(0);
		goods.setBuy_count(0);
		goods.setCost(0D);
		goods.setPrice(goods.getPrice()==null?0:goods.getPrice());
		goods.setMktprice(goods.getMktprice()==null?0:goods.getMktprice());
		if(goods.getType_id()!=null){
			IGoodsTypeManager goodsTypeManager = SpringContextHolder.getBean("goodsTypeManager");
			GoodsType goodsType=goodsTypeManager.get(goods.getType_id());
			goods.setType_code(goodsType.getType_code());
		}
	}

	@Override
	public Page searchElementInfo(String scheme_id,String element_type){
		/*String sql = " select a.scheme_id,a.scheme_name,b.element_id,c.object_name as element_name, "
				   + " b.element_type,b.sele_type as must_type,c.object_sub_type as mobile_type, "
				   + " d.model_name as terminal_name,(select p.pname from es_dc_public_ext p where p.pkey=b.element_type and p.stype='element_type') as mobile_type_n"
				   + " from zjucrm1o.pm_scheme@to_crmdb1                a, "
				   + " zjucrm1o.pm_scheme_element@to_crmdb1         b, "
				   + " zjucrm1o.pm_object@to_crmdb1                 c, "
				   + " zjucrm1o.mobile_param_mobile_type@to_crmdb1  d "
				   + " where a.scheme_id = b.scheme_id "
				   + " and b.element_id = c.object_id "
				   + " and c.object_sub_type = d.mobile_model "
				   + " and b.element_type=? "
				   + " and sysdate between a.effect_time and a.expire_time "
				   + " and sysdate between b.effect_time and b.expire_time "
				   + " and a.scheme_id=? ";*/
		String sql = " select scheme_id,scheme_name,element_id,element_name,element_type,must_type, "
				   + " mobile_type,terminal_name,(select p.pname from es_dc_public_ext p where p.pkey=element_type and p.stype='element_type') as element_type_n "
				   + " from es_goods_action_element where element_type=? and scheme_id=? and goods_id is null";
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		sql = cacheUtil.getConfigInfo("qry_active_element_sql")==null?sql:cacheUtil.getConfigInfo("qry_active_element_sql");
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<ActiveElementInfo> list = baseDaoSupport.queryForList(sql, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data = new HashMap();
				    data.put("scheme_id", rs.getString("scheme_id"));
				    data.put("scheme_name", rs.getString("scheme_name"));
				    data.put("element_id", rs.getString("element_id"));
				    data.put("element_name", rs.getString("element_name"));
				    data.put("element_type", rs.getString("element_type"));
				    data.put("must_type", rs.getString("must_type"));
				    data.put("mobile_type", rs.getString("mobile_type"));
				    data.put("element_type_n", rs.getString("element_type_n"));
				    data.put("terminal_name", rs.getString("terminal_name"));
				return data;
			}
		}, new String[]{element_type,scheme_id});
		Page page = new Page();
		if(list.size()>0){
			page.setParam(1, list.size(), list.size(), list);
		}
		return page;
	}
	
	@Override
	public List<ActiveElementInfo> getElementInfo(String goods_id){
		String sql = " select scheme_id,scheme_name,element_id,element_name,element_type,must_type, "
				   + " mobile_type,terminal_name,(select p.pname from es_dc_public_ext p where p.pkey=element_type and p.stype='element_type') as element_type_n "
				   + " from es_goods_action_element where goods_id=? ";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<ActiveElementInfo> list = baseDaoSupport.queryForList(sql, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data = new HashMap();
				    data.put("scheme_id", rs.getString("scheme_id"));
				    data.put("scheme_name", rs.getString("scheme_name"));
				    data.put("element_id", rs.getString("element_id"));
				    data.put("element_name", rs.getString("element_name"));
				    data.put("element_type", rs.getString("element_type"));
				    data.put("must_type", rs.getString("must_type"));
				    data.put("mobile_type", rs.getString("mobile_type"));
				    data.put("element_type_n", rs.getString("element_type_n"));
				    data.put("terminal_name", rs.getString("terminal_name"));
				return data;
			}
		}, new String[]{goods_id});
		return list;
	}
	
	public IGoodsCommonManager getGoodsCommonManager() {
		return goodsCommonManager;
	}

	public void setGoodsCommonManager(IGoodsCommonManager goodsCommonManager) {
		this.goodsCommonManager = goodsCommonManager;
	}
	/**
	 * 蜂行动商品映射
	 */
	@Override
	public Page fxdMapping(Map params, int page, int pageSize) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		StringBuffer sql = new StringBuffer();
		if (params.get("stype") != null && !params.get("stype").equals("")) {
			sql.append("select  t.stype,t.pkey,t.pname,t.codea,t.codeb,t.comments  from es_dc_public_ext t where ");
			sql.append(" t.stype = '" + params.get("stype") + "'");
		}else {
			sql.append("select  t.stype,t.pkey,t.pname,t.codea,t.codeb,t.comments  from es_dc_public_ext t where ( t.stype='DIC_BLD_GOODS' or t.stype='DIC_FXD_GOODS' )");
		}
		if (params.get("pkey") != null && !params.get("pkey").equals("")) {
			sql.append(" and t.pkey like '%" + params.get("pkey") + "%'");
		}
		if (params.get("pname") != null && !params.get("pname").equals("")) {
			sql.append(" and t.pname like '%" + params.get("pname") + "%'");
		}
		if (params.get("codea") != null && !params.get("codea").equals("")) {
			sql.append(" and t.codea like '%" + params.get("codea") + "%'");
		}
		if (params.get("codeb") != null && !params.get("codeb").equals("")) {
			sql.append(" and t.codeb like '%" + params.get("codeb") + "%'");
		}
		if (params.get("comments") != null && !params.get("comments").equals("")) {
			sql.append(" and t.comments like '%" + params.get("comments") + "%'");
		}
		sql.append(" and t.source_from = 'ECS'");
		Page webPage = null;
		webPage = this.baseDaoSupport.queryForPage(new String(sql), page, pageSize, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data = new HashMap();
				data.put("stype", rs.getString("stype")); 
				data.put("pkey", rs.getString("pkey")); 
				data.put("pname", rs.getString("pname")); 
				data.put("codea", rs.getString("codea")); 
				data.put("codeb", rs.getString("codeb")); 
				data.put("comments", rs.getString("comments")); 
				return data;
			}
		});
		return webPage;
	}

	@Override
	public void fxdMappingAdd(Map params) {
//		String sql =" insert into es_dc_public_ext (STYPE, PKEY, PNAME, CODEA, CODEB, COMMENTS, SOURCE_FROM)"+
//				"values ('{stype}', '{pkey}', '{pname}', '{codea}', '{codeb}', '{comments}', 'ECS')";
//		sql = sql.replace("{stype}", params.get("stype") == null ? "" : params.get("stype").toString());
//		sql = sql.replace("{pkey}",  params.get("pkey") == null ? "" : params.get("pkey").toString());
//		sql = sql.replace("{pname}", params.get("pname") == null ? "" : params.get("pname").toString());
//		sql = sql.replace("{codea}", params.get("codea") == null ? "" : params.get("codea").toString());
//		sql = sql.replace("{codeb}", params.get("codeb") == null ? "" : params.get("codeb").toString());
//		sql = sql.replace("{comments}", params.get("comments") == null ? "" : params.get("comments").toString());
//		
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.insert("es_dc_public_ext", params);
		//baseDaoSupport.execute(sql);
	}

	@Override
	public void deleteFxdMapping(Map params) {
		String sql = "delete from  es_dc_public_ext t where t.pkey = '"+params.get("pkey")+"' and t.STYPE = '"+params.get("stype")+"' and t.PNAME = '"+params.get("pname")+"'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(sql);
	}

	@Override
	public int editFxdMapping(Map params) {
		String sql = "select count(es.STYPE) as num from es_dc_public_ext es where"
				+ " es.PKEY = '" + params.get("pkey") + "' and es.STYPE = '" + params.get("stype") +"'  and es.PNAME = '"+  params.get("pname") +"'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		int num = baseDaoSupport.queryForInt(sql);
		return num;
	}

	@Override
	public void updateFxdMapping(Map params) {
		String sql = "update es_dc_public_ext set STYPE = '" + params.get("stype") + "',CODEA = '" + params.get("codea") + "',CODEB = '" + params.get("codeb") +"',COMMENTS = '"
				+ params.get("comments") +"' where PKEY = '"+  params.get("pkey") +"' and PNAME = '" + params.get("pname")+"' and STYPE = '"+  params.get("stype") + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(sql);
	}
}