package com.ztesoft.net.mall.core.service;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vo.GoodsPlugin;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.ActiveElementInfo;
import com.ztesoft.net.mall.core.model.CommunityActivity;
import com.ztesoft.net.mall.core.model.Country;
import com.ztesoft.net.mall.core.model.EsTerminal;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.GoodsApplyJoin;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsControlStore;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.GoodsRel;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsSpecificationModel;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Relations;
import com.ztesoft.net.mall.core.model.SaleGoods;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;


/**
 * 商品管理接口
 * 
 * @author kingapex
 * 
 */
public interface IGoodsManager {
	
	
	public static final String plugin_type_berforeadd= "goods_add_berforeadd" ;
	public static final String plugin_type_afteradd= "goods_add_afteradd" ;
	
	public void joinApply(String goodsId) ;
	
	public void appreciateApply(String goodsId, List<GoodsLvPrice> goodsLvPriceList);
	
	public void modPrice(List<Map<String,String>> prices) ;
	
	public List<String> getAgentNames(Map params);
	/**
	 * 获取售后服务产品
	 * */
	public List<Goods> getAfterService();
	
	// 根据订单展示类型
	public GoodsTypeDTO getGoodsTypeByGoodsId(String goods_id);
	/**
	 * 插入商品控制数据
	 * @param goodsControl
	 */
	public void insertGoodsControl(GoodsControl goodsControl);
	
	public GoodsLvPrice getGoodsLvPrice(String goods_id,String lv_id);
	public List<GoodsControl> qryGoodsControlByGoodsId(String goods_id);
	public int getGoodsStore(String goods_id,String land_code,List<GoodsControl> list,GoodsLvPrice lvPrice);
	public List<GoodsControlStore> getGoodsStoreList(String goods_id);
	
    /**
     * 根据ID得到控制信息
     * @param goods_id
     * @return
     */
	public List<GoodsControl> getControlById(String goods_id);
	
	/**
	 * 根据ID删除所有的控制信息
	 */
	public void delAllControlByAgtId(String goods_id);
	/**
	 * 
	 * @param prod  产品供销商ID
	 * @param prch  采购代理供应商
	 * @param prov  省内物流供应商
	 * @param logst 省际物流供应商
	 * @param agreement_id
	 * @return
	 */
	public  Map getNameListByID(String prod,String agreement_id);
    /**
     * 
     * @param agreement_id 协议ID
     * @param lan_code     本地网编码
     * @param curr_control_value 当前输入控制值
     * @return  flag true  是该值小于统计的控制值 false  为大于 则不能输入此值
     */
	public boolean isOverQtyOrMoney(String agreement_id,String lan_code,int curr_control_value);
	/**
	 * 填充添加前的数据
	 * 
	 */
	public  List<String> fillAddInputData();
	
	
	public Goods getCatByGoodsId(String goodsId);
	
	
	/**
	 * 商品总数
	 * */
	public int allGoodsNum();
	/**
	 * 待审核商品数
	 * */
	public int allGoodsAuditNum();
	/**
	 * 检查商品是否已存在*/
	public List<Goods> checkExists(String cardCode,Integer mktprice,String offer_id,String charge_type);
	/**
	 * 读取一个商品的详细
	 * 
	 * @param Goods_id
	 * @return
	 */
	public Map get(String goods_id);
	public Goods getGoodsById(String goodsid);
	public Goods getGoods(String goods_id);
	public Map getGoodsMap(String goods_id);
	public Map getGoodsBusinessMap(String goods_id);
	/**
	 * 修改时获取数据
	 * 
	 * @param goods_id
	 * @return
	 */
	public GoodsEditDTO getGoodsEditData(String goods_id);
	
	
	
	/**
	 * 添加商品
	 * 
	 * @param goods
	 * @param s_element_type 
	 * @param s_scheme_id 
	 */

	public void add(Goods goods,GoodsRules goodsRules,GoodsPackage goodsPackage,String communityCodes,String countyIds,String custIds,String staffIds,String developIds,String officeIds, String s_scheme_id, String s_element_type) throws Exception;
	
	/**
	 * 修改商品
	 * 
	 * @param goods
	 * @param s_element_type 
	 * @param s_scheme_id 
	 */
	public void edit(Goods goods,GoodsRules goodsRules,String communityCodes,String countyIds,String custIds,String staffIds,String developIds,String officeIds, String s_scheme_id, String s_element_type);
	
	/**
	 * 添加修改商品操作日志
	 * 
	 * @param goods
	 */
	public void insertOperLog(Map map);
	/**
	 * 待申请商品信息
	 * @param goods
	 */
	public List<Goods> listGoodsApply();

	public Page searchGoods(String catid,String auditState,String name,String supplier_id,String sn,String brandId,String startTime,String endTime,Integer market_enable,String[] tagid,Integer [] staff_nos,String order,String selfProdType ,String type ,String sku,Integer publish_state,int page,int pageSize);
	public Page searchGoods(String catid,String name,String sn,Integer market_enable,String[] tagid,String order,int page,int pageSize);
	public Page searchApplyGoods(String catid,String name,String sn,String startTime,String endTime,String typeCode,Integer market_enable,String[] tagid,Integer [] staff_nos,String order,int page,int pageSize);
	
	/**
	 * 已订购的商 品
	 * @param 
	 * @return    
	 * 
	 */
	public Page searchApplyYesGoods(String catid,String name,String sn,String startTime,String endTime,String typeCode,Integer market_enable,String[] tagid,Integer [] staff_nos,String order,int page,int pageSize);
	/**
	 * 一、二级分销商品
	 * 已申请的商品数
	 * **/
	public int applySuccNum();
	/**
	 * 电信员工看回收站商品数*/
	public int getTrashNum();
	 
	public Page searchAuditGoods(Goods goods,int page,int pageSize);
	
	public Page searchBindGoods(String name,String sn,String order,int page,int pageSize);
	public Page pageTrash(String name,String sn,String order,int page,int pageSize);
	public void delete(String[] ids);
	public void  revert(String[] ids);
	public void clean(String[] ids);
	
	/**
	 * 根据商品id数组读取商品列表
	 * 
	 * @param ids
	 * @return
	 */
	public List list(String[] ids);
	
	/**
	 * 按分类id列表商品
	 * 
	 * @param catid
	 * @return
	 */
	public List listByCat(String catid);
	
	/**
	 * 按标签id列表商品 如果tagid为空则列表全部
	 * 
	 * @param tagid
	 * @return
	 */
	public List listByTag(String[] tagid);
	
	/**
	 * 不分页、不分类别读取所有有效商品，包含捆绑商品
	 * 
	 * @return
	 */
	public List<Map> list();
	public List<Goods> listGoods();
	public List<Goods> listGoodsNew();
	public List<Goods> listProducts();
	public List<Map> listVproducts();
	/**
	 * 批量编辑商品
	 */
	public void batchEdit();
	
	
	/**
	 * 商品信息统计
	 * 
	 * @return
	 */
	public Map census();
	
	
	/**
	 * 更新某个商品的字段值
	 * 
	 * @param filedname
	 *            字段名称
	 * @param value
	 *            字段值
	 * @param goodsid
	 *            商品id
	 */
	public void updateField(String filedname,Object value,String goodsid);
	
	/**
	 * 获取某个商品的位置信息
	 */
	public void getNavdata(Map goods);
	
	public Page searchHotGoods(int page,int pageSize);
	/**
	 * 删除商户时下架该商户对应工号下的商品
	 * 
	 * @param staffno
	 */
	public void delGoodsByStaffNo(String staffno);
	
	public Goods getGoodsByType(String typeCode, Integer price);
	
	public String getPaySequ(String seq);
	public Page findCrmOfferId(int pageNo,int pageSize,String offer_id,String offer_name);
	public Page findCrmOfferId2(int pageNo,int pageSize,String offer_id,String offer_name);
	
	/**
	 * 根据供应商标识，获取供应商管理员用户标识
	 * @param supplierId
	 * @return
	 */
	public String getSupplierAdminUserId(String supplierId) ;
	
	/**
	 * 上下架处理
	 * @param goodsId
	 * @param state
	 * @return
	 */
	public boolean marketEnable(String goodsId ,String state ) ;
	
	/**
	 * 按productid查询商品
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-26 
	 * @param productID
	 * @return
	 */
	public Goods getGoodsByProductID(String productID);
	
	/**
	 * 获取敢兴趣的商品
	 * @param goodIds
	 * @return
	 */
	public List<Goods> getInterestGoodByGoodIds(String goodIds);
	/**
	 * 
	 * @return  缺货登记商品总数
	 */
	public int outofRegister();
	
	/**
	 * 校验商品编码是否已经存在
	 * */
	public boolean checkGoodsSN(String sn,String goods_id);
	
	/*
	 * 上架商品
	 * 
	 */
	public int groundingGoods();
	
	/*
	 * 下架商品
	 * 
	 */
	public int undercarriageGoods();
	
	/**
	 * 查询商品电信采集价格
	 * */
	public String getLvPrice(String goods_id);
	
	public void joinApply(GoodsApplyJoin goodsApplyJoin); 
	
	public void addTemp(Goods goods); 
	
	public void updateTemp(Goods goods); 
	
	public Map qryGoodsTemp(String goods_id);
	/**
	 * 
	 * @param goods_id 商品ID
	 * @return  返回价格列表
	 */
	public List getPriceList(String goods_id);
	
	public List getOrgByGoodsId(String type,String goods_id);
	
	/**
	 * 
	 * @param type
	 * @param goods_id
	 * @return
	 */
	public List getOrgByGoodsIds(String type,String[] goods_ids);
	
	/**
	 * 
	 * @param goods_id
	 * @return
	 */
	public List compareGoods(String[] goods_ids);
	/**
	 * 
	 * @param products_id
	 * @return
	 */
	public List compareProducts(String[] goods_ids);
	
	
	public List<GoodsActionRule> queryGoodsRules(String goods_id,String service_code);
	
	public List<GoodsPlugin> getGoodsPlugin();
	
	public List ListGoodsByTypesId(String[] types_id);
	
	public List ListGoodsByStypesId(String[] stypes_id);
	
	public Page listAllGoods(int pageNo,int pageSize,String names);
	
	public List listSelGoods(String[] goodsId);
	
	/**
	 * 查询商品类型
	 * @return
	 */
	public List qryStypeList();
	
	
	/**
	 * 查询本系统同步到的业务
	 * @param serv_type 类型
	 * @param serv_name 名称
	 * @return
	 */
	public List<Goods> qrySysServs(String serv_type,String serv_name);
	
	
	public Goods findGoodsByGoodsSn(String sn);
	
	/**
	 * 根据登录用户ID查询商品
	 * @return
	 */
	public Page listGoodsByUserId(String name,int pageNo,int pageSize);
	
	public void editGoodsRules(GoodsRules goodsRules,String goods_id,String service_code);
	
	public void saveGoodsRules(GoodsRules goodsRules,String goods_id,String service_code);
	
	public List<Goods> listComplexGoodsByCatId(String cat_id);
	
	public List<Goods> listGoodsByBrandId(String brand_id);
	
	public List listGoodsAdjunctNum();
	
	public List listGoodsComplexNum();
	
	/**
	 * 缓存标签关联商品信息
	 * @param tag_id
	 * @return
	 */
	public List<Goods> listGoodsByRelTag(String tag_id);
	
	public List listGoodsAttrDef(String goods_id);
	
	List<Goods> GoodsRelGoods(String a_goods_id, String rel_type);
	
	public Page GoodsRelGoodsPage(int pageNo,int pageSize,String a_goods_id, String rel_type, String source_from);

    public Page GoodsRelGoodsPage(int pageNo,int pageSize,Object ...args);

	List<Goods> listGoodsByTerminalRelTerminal(String goods_id);
	
	public String getAcceptPrice(String goods_id,String product_id,String lv_id);
	
	public int getGoodsStore(String goods_id);

	public List<Goods> qryGoodsRanking(String type, String val, String count);
	
	public List listGoodsSpecs(String goods_id);
	
	public List<HashMap> getPromotionByGoodsId(String goods_id);
	
	public HashMap getGoodsScore(String goods_id);
	
	
	public HashMap getGoodsDetail(String goods_id);
	
	public boolean checkGoodsHasSpec(String goods_id);

    //获取产品
    public List<Product> getProductByGoodsId(String goods_id);
    
    public Page searchGoodsByType(String type_id,String price,String sub_stype_id,String source_from,int pageNo,int pageSize);
    
    
    public Page queryGoodsByIdsAndName(String ids,String name,int page,int pageSize);
    
    public Page listProRelGoods(String product_id,int page,int pageSize);
    
    public List listProducts(String service_id);
    
    public List<Goods> listProductsByGoodsId(String goods_id);
    
    public List<Goods> listGoodsRelProducts();
    
    public List<Goods> listGoodsRelProducts(String goods_id);
    
    public Page listOfferChange(String goodsType,int pageNo,int pageSize, String sub_stype_id);
    
    /**
     * 发布状态更新,包括：商品、货品、号码
     * @param req
     * @return
     */
    public int modifyStatus(String id, String service_code, String status_new);
    
    
    public Map getGoodsInfo(String goods_id,String sn,String package_id);
    
    //根据商品id查询关联商品
    public List listAllComplex(String goods_id);

    public Page searchGoodsECS(Map params,int page,int pageSize);
    
    public Page searchSaleGoodsECS(Map params,int page,int pageSize);
    
    public Page searchGoodsOperLog(Map params,int page,int pageSize);
    
    public Page searchProductOperLog(Map params,int page,int pageSize);
    
    /**
     * 商品批量发布
     * @param params
     * @param page
     * @param pageSize
     * @return
     */
    public String goodsBatchPublish(Map params,int page,int pageSize);
    
    /**
     * 货品批量发布
     * @param params
     * @param page
     * @param pageSize
     * @return
     */
    String productsBatchPublish(Map params,int page,int pageSize);
    
    public Page searchGoodsTaobaoECS(String goods_name, String p_code, Integer market_enable, String order, int page, int pageSize);
    
    public Page searchProductsECS(Map params,int page,int pageSize);

    public Page searchTags(int pageNo,int pageSize,String tag_name);
    /*
     * 通过name得到商品分类id
     */
    public String getCatID(String catName);
    /*
     * 通过name得到商品类型id
     */
    public String getTypeID(String typeName);
    /*
     * 通过name得到品牌id
     */
    public String getBrandID(String brandName);
    /*
     * 通过name得到品牌id
     */
    public String getStypeID(String stypeName);
    /*
     * 通过name得到品牌id
     */
    public String getSubStypeID(String subStypeName,String parentID);
    /*
     * 添加商品的会员价格等信息入表
     */
    public void addGoodsPrice(Goods goods,String selledObject,String normal_price,String silver_price,String gold_price);
    /*
	 * 添加商品权限
	 */
	public void addpriv(List<GoodsLvPrice> list);
	/**
	 * 添加会价格
	 */
	public void save(List<GoodsLvPrice> goodsPriceList);
	 /*
     * 通过商品编码查找商品id
     * 
     */
    public String getGoodsId(String sn);
    /*
     * 更新goods表的params参数
     */
    public void editGoods(String detail,String goodsId);
    public HashMap getGoodsSpecInfo(String goods_id);
    /*
     * 获取商品catid
     */
    public HashMap getGoodsCatPath(String goods_id);
    /*
     * 获得规格配置信息
     */
    public List<GoodsSpecificationModel> getcolConfig(String cat_id); 
    

    public List<Map> listGoodRelProducts(String goods_id);
    
    public List<Goods> listContractOffers(String goods_id);
    
    public List listColor();
    
    public Map getProductColor(String goods_id);

    public List getProductIDByGoodsId(String goodsId);
    
    public void addGoodsRelProduct(Map data);
    
    public void eidtGoodsRelProduct(Map data);
    
    public void deleteGoodsRelProduct(String a_goods_id);
    
    public Page getOfferList(String name,int pageNo,int pageSize);
    
    public Map getGoodsTagEcs(String goods_id);

	public String getSku(String goods_id);

	public void reduccion(String ids);

	public void claro(String ids);
	
	public Goods getGoodsInfo(String goods_id);
	
	public Goods getProductInfo(String product_id);
	
	public Map<String, String> getProductInfoByName(String name);
	
	public Goods qryGoodsByIds(String goods_id,String product_id,String source_from);
	
	/**
	 * 解析商品货品params，写入es_goods_params表，每个参数一条记录
	 * @param type
	 */
	public void convertParams(String type);
	
	public String importExcel(File file,Map<String,String> params) throws Exception;
	
	public String getSequence(String seq);
	
	//获取销售渠道上级组织
	public String getParentOrgId(String party_id);
	
	public List<Goods> getContractmachine(String type_id,String terminal_pid,String contract_pid,String offerPids);
	
	//通过商品id查询关联信息
	public List<GoodsRel> getGoodsRelByGoodsId(String goods_id);
	
	public void insertCoQueue(String type,String oper_id,String goods_id,Integer market_enable);
	
	public Page searchGoodsImportLogsECS(Map params,int pageNum,int pageSize);
	
	public String createSKU(String type, String cat_id);
	
	public String createSN(String type,String prefix,String postfix);

	public void cacheGoods(Goods goods);
	
	public void savaGoodsPackage(GoodsPackage goodsPackage);
	
	/**
	 * 定时任务导入商品货品调用方法--去掉从request对象中获取数据的逻辑
	 * @param goods
	 */
	public void importAdd(Goods goods);
	
	/**
	 * 定时任务导入商品货品插入es_product数据
	 * @param goods
	 */
	public void insertProduct(Goods goods);
	
	/**
	 * 查询商品货品同步日志
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchSynchLogs(Map params,int pageNum,int pageSize);
	
	/**
	 * 默认生成商品（货品）关系编码
	 * @return
	 */
	public String createGoodsRelCode();
	
	/**
	 * 导入手机货品
	 * @param file
	 * @param params
	 * @return
	 */
	public String importTerminalExcel(File file,Map<String, String> params);
	
	/**
	 * 查询手机货品导入日志
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchPhoneImportLogsECS(Map params,int pageNum,int pageSize);
	
	/**
	 * 查询货品包
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchProductPkgECS(Map params,int pageNum,int pageSize);
	
	/**
	 * 根据机型编码获取终端颜色
	 * @param params
	 * @return
	 */
	public List listColorByModelCode(Map params);
	
	
	public Page searchPkgGoodsECS(Map params,int pageNum,int pageSize);
	
	
	public List listRelationDetails(String relation_id);
	
	public void updatePkgGoodsStatusECS(String relation_id);
	
	public Map<String,Map<String,String>> getGoodsTypeByGoodsId(String[] goods_id);
	
	public Relations getPackage(Map params);
	
	public void addPackageMember(String relation_id,String goods_id);
	
	public boolean checkPackageMember(String relation_id,String goods_id);
	
	public void deletePackageMember(String goods_id);	
	
	/**
	 * 根据关联CRM id查询商品
	 * @param crm_offer_id
	 * @param source_from
	 * @return
	 */
	public Goods qryGoodsByCrmOfferId(String crm_offer_id,String source_from);
	
	public int publishAgain(Map params);
	
	//兼容套餐商品获得型号编码---zengxianlian
	public String getTerminalModelCode(String[] z_goods_ids,String type);
	
	//订单自动化
	/**
	 * 获取商品规格参数信息
	 * @param goods_id
	 * @return
	 */
	public Map getGoodSpec(String goods_id);
	
	/**
	 * 获取货品规格参数信息
	 * @param goods_id
	 * @return
	 */
	public Map getProductSpec(Goods product);
	
	/**
	 * 获取商品货品名称，型号，机型，品牌，颜色等信息，用于缓存
	 * @return
	 */
	public List<Map> listGoodsSpec();
	
	/**
	 * 判断是否赠品
	 * @param goods_id
	 * @return
	 */
	public String getGoodsTypeId(String goods_id);
	
	/**
	 * 获取合约编码
	 * @param goods_id
	 * @return
	 */
	public String getPCode(String goods_id);
	
	public String getActiveType(String goods_id) ;
	
	public List<Goods> listGoodsParams();
	
	public Goods getGoodsForCache(String goods_id);

	public String getResultStrByGoodsId(String goods_id,String resultStr);

	public List getOrgResultByGoodsId(String goods_id);
	
	public List<Goods> listGoodsIds();
	
	public List<HashMap> listTerminalNum();
	
	public String getTerminalNumBySN(String sn);
	
	/**
	 * 刷新缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-12-19
	 */
	public void initGoodsCache();


	//转兑包增删改
	public Map importZdbs(File file, String fileNameFileName,String actType) throws Exception;

	public void importZdbToProduct(Goods goods,String actType);

	public Page queryZdbLogsEcs(Map<String, String> params, int page,
			int pageSize);

	public List queryZdbsExport(Map<String, String> params);

	
	/**
	 * 根据条件增量刷新商品/货品缓存
	 * @param goods_id
	 * @return
	 */
	public List<Goods> initGoodsCacheByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取商品基本信息、商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、商品规格产品列表，不包括详情页属性信息，详情信息
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> listGoodsByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取终端缓存 STYPE_CODE= TERMINAL
	 * @param serv_type
	 * @param serv_name
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> qrySysServsByCondition(String serv_type, String serv_name, GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取货品缓存
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> listProductsByCondition(GoodsRefreshDTO goodsRefreshDTO);
		
	/**
	 * 增量获取商品绑定关联商品
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List listGoodsComplexNumByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取商品的配件信息
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List listGoodsAdjunctNumByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取 商品基本信息、商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、商品规格产品列表，不包括详情页属性信息，详情信息 : sku维度
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> listGoodsParamsByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取货品 -对应视图v_product
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Map> listVproductsByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取标签关联商品信息
	 * @param tagId
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> listGoodsByRelTagByCondition(String tagId,GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 增量获取分类推荐商品
	 * @param catId
	 * @param goodsRefreshDTO
	 * @return
	 */
	public List<Goods> listGoodsByCatIdByCondition(String catId,GoodsRefreshDTO goodsRefreshDTO);
	
	/**
	 * 获取商品ID
	 */
	public List<Goods> listGoodsIdsByCondition(GoodsRefreshDTO goodsRefreshDTO);
	
	public List<Map<String,String>> listSkuByActivityCode(String activityCode);
	public Page getProductPageQuery(String productId, String goodsId,
			String productName,int pageNo,int pageSize);
	
	public Goods getGoodsBySku(String sku);
	public List listSn();
	
	
	/**
	 * @author zhengchuiliu
	 * 获取虚拟串号列表
	 */
	public Page getEsTerminalList(int page,int pageSize,String sn,String terminal_no);
	
	/**
	 * 保存或更新虚拟串号记录
	 */
	public boolean saveOrUpdateEsTerminal(EsTerminal esTerminal, String action);
	
	/**
	 * 获取虚拟串号详情
	 */
	public EsTerminal getEsTerminalDetail(String sn);
	/**
	 * 删除串号
	 */
	public void deleteEsTerminal(String sn);
	/**
	 * 货品管理添加选择条形码
	 */
	public Page snSelectList(int pageNo, int pageSize, String sn);
	/**
	 * 虚拟串号导入
	 */
	public String importEsTerminal(File file, Map<String, String> params);
	/**
	 * 判断sn是否已存在
	 */
	public int checkSnIsExist(String sn);
	/**
	 * 同步日志导出
	 */
	public List querySynchLogsExport(Map<String, String> params);
	/**
	 * @author zengxianlian
	 * 批量发布商品(可选择)
	 * @return
	 */
	public String goodsBatchPublishByChoice(String goodsIds,String orgIds);
	/**
	 * @author zengxianlian
	 * 批量发布货品(可选择)
	 * @return
	 */
	public String productsBatchPublishByChoice(String goodsIds,String orgIds);
	
	/**
	 * @author zengxianlian
	 * 发布商品前验证货品是否成功同步
	 * @return
	 */
	public Map<String,String> checkPublishGoods(Map<String,String> params);
	
	/**
	 * @author zengxianlian
	 * 获取商品下的货品信息
	 * @return
	 */
	public List<Product> getProductRelByGoodsId(String goods_id);
	/**
	 * @author zengxianlian
	 * 新增货品校验唯一性
	 * @return
	 */
	public List<Goods> checkSaveAdd(String typeCode,String checkData);
	/**
	 * @author zengxianlian
	 * 新增商品校验唯一性
	 * @return
	 */
	public List<Goods> checkGoodsSaveAdd(Map params);
	
	
	
	/**
	 * 商品导出
	 */
	public Map queryGoodsExport(Map<String, String> params);
	/**
	 * 货品导出
	 */
	public List queryProductsExport(Map<String, String> params);
	
	/**
	 * 添加货品和商品关联
	 */
	public void goodsRelLog(boolean tag,Map param);

	public void deleteGoodsByID(String[] ids);
	
	/**
	 * @author zengxianlian
	 * 获取华盛数据列表
	 */
	public Page getHSGoodsList(int page,int pageSize,String name,String sku, String isMatched);
	
	/**
	 * @author zengxianlian
	 * 根据商品id获取活动编码与产品编码
	 */
	public Map getPCodeAndSnByGoodsId(String goodsId);
	
	/**
	 * 检查物料号是否已关联商品
	 * @param matnr
	 * @return
	 */
	public boolean checkMatnrExists(String matnr);
	
	/**
	 * 获取商品参与的活动
	 * @param goodsId
	 * @param orderCity
	 * @param orderTime
	 * @param userType
	 * @return
	 */
	public List<Map> getGoodsJoinActivities(String goodsId, String orderCity, String orderTime,int userType);
	
	/**
	 * 根据商城商品名称和套餐名称获取标准化商品配置
	 * @param goodsName
	 * @param titleName
	 * @return
	 */
	public Map getStdGoodsByName(String goodsName,String titleName);
	
	/*
	 * 商品添加中，查询小区信息
	 */
	public Page searchCommunityInfo(String communityName,int page,int pageSize);
	
	public List<Map> qryAdmissibleBroadbandGoods(String communityCode, String string);
	
	//通过地市县分查询宽带套餐信息
	public List<Map> qryAdmissibleBroadbandGoodsByCityOrCounty(String countyId, String string);
	
	public List<CommunityActivity> getGoodsRelCommunityList(String goodsid);

	public void liberacion(String[]ids,String enable) throws Exception;

	
	public void updateSaleMarketEnable(String sale_gid,int market_enable);

	public List<Country> getGoodsRelCountryList(String goodsid);
	
	public List<String> getGoodsRelCustList(String goodsid);
	
	public List<String> getGoodsRelStaffList(String goodsid);
	
	public List<String> getGoodsRelDevelopRelaList(String goodsid);
	
	public List<String> getGoodsRelOfficeRelaList(String goodsid);

	public Page searchCountryInfo(String areadef, String countyname, String region_type, int page,
			int pageSize);

	public Page searchGoodsInfo(String sku, String name, String goods_type, int page, int pageSize);

	public List<Goods> getGoodsTypeList();

	public List<Tag> getGoodsTagList(String goods_id);

	public List<Tag> getSaleTagList(String goods_id);

	public Page searchTagInfo(String tag_group_type, String name, String tag_code, int page, int pageSize);

	public void addSaleGoods(SaleGoods saleGoods, String skus,
			String tag_codes, String sale_tag_codes, String sort);

	public void editSaleGoods(SaleGoods saleGoods, String skus,
			String tag_codes, String sale_tag_codes, String sort);

	public Map querySaleGoods(String sale_gid);

	public List<Goods> getGoodsListRelSale(String sale_gid);
	public boolean saveBraoadband(Goods goods, String[] propvalues, List<Map<String,Object>> products,String relType);

	public Page searchElementInfo(String scheme_id,String element_type);
	public List<ActiveElementInfo> getElementInfo(String goods_id);
	/**
	 * 总部活动、商品编码、总部商品编码
	 * @return
	 */
	public Map returnPkgMap(String goods_id);

	public List<Map> getDcSqlByDcName(String string);

	public void saveGoodsElements(String goods_ids, String s_element_type);
	/**
	 * 蜂行动商品映射
	 * @param params
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page fxdMapping(Map params,int page,int pageSize);
	/**
	 * 
	 */
	public void fxdMappingAdd(Map params);
	/**
	 * 
	 */
	public void deleteFxdMapping(Map params);
	
	public int editFxdMapping(Map params);
	
	public void updateFxdMapping(Map params);
	
}
