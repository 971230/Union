package com.ztesoft.mall.core.action.backend;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import params.member.req.PricePrivListReq;
import params.member.resp.PricePrivListResp;
import params.req.PartnerAddrListReq;
import params.req.PartnerAddrReq;
import params.req.PartnerInfoOneReq;
import params.resp.PartnerAddrListResp;
import params.resp.PartnerInfoResp;
import services.MemberPriceInf;
import services.PartnerInf;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.utils.SpecUtils;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.resp.ProductsListResp;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAddress;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseJdbcDaoSupport;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.exception.NameRepetirException;
import com.ztesoft.net.mall.core.model.ActiveElementInfo;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.CommunityActivity;
import com.ztesoft.net.mall.core.model.Country;
import com.ztesoft.net.mall.core.model.EsTerminal;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.GoodsApplyJoin;
import com.ztesoft.net.mall.core.model.GoodsArea;
import com.ztesoft.net.mall.core.model.GoodsBusiness;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.GoodsRel;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsStype;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.model.HSGoods;
import com.ztesoft.net.mall.core.model.Lan;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.SaleGoods;
import com.ztesoft.net.mall.core.model.Tab;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.plugin.field.GoodsField;
import com.ztesoft.net.mall.core.plugin.field.GoodsFieldPluginBundle;
import com.ztesoft.net.mall.core.plugin.goods.GoodsPluginBundle;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IGoodsAgreementManager;
import com.ztesoft.net.mall.core.service.IGoodsAreaManager;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsFieldManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.IGoodsUsageManager;
import com.ztesoft.net.mall.core.service.IGoodsUserManager;
import com.ztesoft.net.mall.core.service.ILanManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.service.IProductoManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

@SuppressWarnings("all")
public class GoodsAction extends WWAction {
	public static final String GOODS_TYPE_NUM_CARD = "20000";//号卡
	public static final String GOODS_TYPE_WIFI_CARD = "20001";//上网卡
	public static final String GOODS_TYPE_CONTRACT_MACHINE = "20002";//合约机
	public static final String GOODS_TYPE_PHONE = "20003";//裸机
	public static final String GOODS_TYPE_GIFT = "20008";//成品卡
	public static final String GOODS_TYPE_ADJUNCT = "69070000";//配件	
	
	protected String areadef;
	protected String countyname;
	
	private String cat_name;
	protected String name;
	protected String supplier_id;
	protected String supplier;
	protected String sn;
	protected String sku;
	protected String auditState;
	protected String startTime;
	protected String endTime;
	protected String typeCode;
	protected String order;
	protected String lan_ids;//区域ID
	private String catid;
	protected String[] id;
	private String usageid;
	private String founder;
//	private String intro;
	protected Goods goods;
	protected GoodsArea goodsArea;
	protected GoodsUsage goodsUsage;
	protected GoodsRules goodsRules;
	private GoodsApply goodsApply;
	private List<GoodsLvPrice> goodsLvPriceList = new ArrayList<GoodsLvPrice>();
	
	private Lan lan;
	protected Map goodsView;
	protected String goods_id;
	protected String goods_ids;
	protected String lan_id;
	protected List catList; // 所有商品分类
	private ILanManager lanManager;
	protected IGoodsCatManager goodsCatManager;
	protected IGoodsAreaManager goodsAreaManager;
	protected IGoodsUsageManager goodsUsageManager;
	
	
	protected IGoodsManager goodsManager;
	private IProductManager productManager; 
	private PartnerInf partnerServ;
	private IGoodsTypeManager goodsTypeManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	private ICoQueueManager coQueueManager;
	private List<PartnerAddress> addressList;
	private GoodsTypeDTO goodsType;
	private Partner partner;
	protected Boolean is_edit;
	protected Boolean is_copy;
	protected String actionName;
	protected Integer market_enable;
	protected Integer publish_state;
	private String[] tagids;
	private Integer[] staff_nos;
	private ICacheUtil cacheUtil;
	private IGoodsCacheUtil goodsCacheUtil;
	private String goodsAreaParam;	//商品地域信息
	private GoodsBusiness goodsBusiness;	//商品外系统扩展属性
	private List tagList;
	private List agentList;
	private List<GoodsField> fieldList;
	private GoodsPluginBundle goodsPluginBundle;
	private IGoodsFieldManager goodsFieldManager;
	private GoodsFieldPluginBundle goodsFieldPluginBundle;
	private ITagManager tagManager;
	protected List<Tab> tabs;
	private String agent_login ="yes";
	private AdminUser adminUser;
	
	private IGoodsAgreementManager agreeManager;
	private String agreement_name;
	private String store;	//库存量，用于跳转的值带入
	private Integer have_stock;	//是否有库存，一并带入
	
	private String cardType="";//添加时根据商品类型展示CRM_ID
	private String offer_name;//添加商品时模糊
	private String offer_id;//
	private String isApplyParStock;	//是否申请查看父级库存
	
	//配置新地址将填写好的数据传回去
	private String begin_nbr;
	private String end_nbr;
	private Integer goods_num;
	private String apply_desc;
	private List lanList;
	
	private String cardCode;
	
	private String page_type;
	
	private Integer mktprice;
	private String charge_type;
	private String goods_type;//商品类型
	private String brand_id;//品牌ID
	private String brand_name;
	private String org_ids;
	private String org_names;

	private String zdb_name;
	
	private EsTerminal esTerminal;
	private String terminal_no;//虚拟串号
	private String action;

	private String productList;
	private String goodsList;
	
	/**===华盛属性开始====*/
	private HSGoods hsGoods;
	private String matnr;
	private String mtart;
	private String isMatched;
	/**===华盛属性结束====*/
	
	private String serial_nos;
	private String batch_id;
	private String start_date;
	private String end_date;
	private String deal_flag;
	private String deal_type;
	
	//是否为自主产品类型：T是,F否(入围产品)
	private String selfProdType  = "T"; 
	
	private String listFormActionVal = "goods!list.do" ;

	private String ref_obj_id ;
	
	private List codeList;//本地网
	
	private String money_control_value;//金额控制
	private String qty_control_value;//数量控制
	private String controlValStr;
	private String codeStr;
	private GoodsControl goodsControl;
    private List  controlTypeList;
    private List  subControlTypeList;
    private List  companyTypeList;
    private List  goodsControlList;
    private String control_type;
    private String sub_control_type;
    private String control_value;
	private Map    nameMap ;//根据ID显示款家名称和供应商名称
	private String agreement_id;
	private String lan_code;
	private int curr_control_value;
	
	private String type;//广东联通ECS：goods,product
	private String product_id;//广东联通ECS：查询关联的商品
	private List colorList;//广东联通ECS
	private List snList;//广东联通ECS
	
	private List  priceList;
	private int   priceListSize;
	
	private List  orgList;  //发布的组织
	private int   orgListSize;//发布的组织数量
	
    private List stypeList;//服务类型

    private GoodsPackage goodsPackage; //产品包
    private GoodsRel goods_rel;
    private String search_tag_name;
    private String ids;
	protected MsgBox msg = new MsgBox();
	private Map<String, String> params = new HashMap<String, String>(0);
	private String terminal_pid;
	private String contract_pid;
	private String offerPids;
	private GoodsParam goodsParam;
	private Map param;
	private String type_id;
	private String cat_id;
	private Double price;
	private String busqueda;
	private String log;
	protected Map goodsOld;
	protected Map goodsNew;
	protected Map productsOld;
	protected Map productsNew;
	protected List goodsOldList;
	protected List goodsNewList;
	protected Map goodsParamOldMap;
	protected Map goodsParamNewMap;
	protected String communityCodes;
	protected String countyIds;
	protected List<CommunityActivity> cmutList;
	protected List<Country> countryList;
	protected List<String> staffIdList;
	protected List<String> custIdList;
	protected List<String> developIdList;
	protected List<String> officeIdList;
	protected List<String> agentNamesList;
	protected List<Goods> goodsTypeList;
	protected List<Tag> goodsTagList;
	protected List<Tag> saleTagList;
	protected List<Goods> goodsListRelSale;
	
	
	
	private String sale_gid;//销售商品ID
	private String sale_gname;//销售商品名称
	private String package_type;//商品类型
	private String[] sale_gids;
	private String tag_group_type;//标签组类型
	private String tag_code;//标签组编码
	private ActivityAction activityAction;
	private SaleGoods saleGoods;
	private String skus;
	private String tag_codes;
	private String sale_tag_codes;
	private String sort;
	private Map saleGoodsView;
	private List<Map> regionList;
	private String region_type;
	private String inputname ;
	private String body ;
	private String node ;
	private String custIds ;
	private String staffIds ;
	private String developIds ;
	private String officeIds ;
	private String q_scheme_id;
	private String q_element_type;
	private List<ActiveElementInfo> elementList;
	private List<Map> element_type_list;
	private String s_scheme_id;
	private String s_element_type;
	
	//蜂行动商品映射 
	private String stype;
	private String pkey;
	private String pname;
	private String codea;
	private String codeb;
	private String comments;
	private String operType;
	
	
	public String getCustIds() {
		
		return custIds;
	}

	public void setCustIds(String custIds) {
		try {
			custIds = java.net.URLDecoder.decode(custIds,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.custIds = custIds;
		
	}

	public String getStaffIds() {
		
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		try {
			staffIds = java.net.URLDecoder.decode(staffIds,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.staffIds = staffIds;
	}

	public String getDevelopIds() {
		
		return developIds;
	}

	public void setDevelopIds(String developIds) {
		try {
			developIds = java.net.URLDecoder.decode(developIds,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.developIds = developIds;
	}

	public String getOfficeIds() {
		
		return officeIds;
	}

	public void setOfficeIds(String officeIds) {
		try {
			officeIds = java.net.URLDecoder.decode(officeIds,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.officeIds = officeIds;
	}

	
	public List<String> getAgentNamesList() {
		return agentNamesList;
	}

	public void setAgentNamesList(List<String> agentNamesList) {
		this.agentNamesList = agentNamesList;
	}

	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
	}

	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public List<Goods> getGoodsListRelSale() {
		return goodsListRelSale;
	}

	public void setGoodsListRelSale(List<Goods> goodsListRelSale) {
		this.goodsListRelSale = goodsListRelSale;
	}

	public List<Map> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Map> regionList) {
		this.regionList = regionList;
	}

	public Map getSaleGoodsView() {
		return saleGoodsView;
	}

	public void setSaleGoodsView(Map saleGoodsView) {
		this.saleGoodsView = saleGoodsView;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public ActivityAction getActivityAction() {
		return activityAction;
	}

	public void setActivityAction(ActivityAction activityAction) {
		this.activityAction = activityAction;
	}

	public SaleGoods getSaleGoods() {
		return saleGoods;
	}

	public void setSaleGoods(SaleGoods saleGoods) {
		this.saleGoods = saleGoods;
	}

	public String getSkus() {
		return skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
	}

	public String getTag_codes() {
		return tag_codes;
	}

	public void setTag_codes(String tag_codes) {
		this.tag_codes = tag_codes;
	}

	public String getSale_tag_codes() {
		return sale_tag_codes;
	}

	public void setSale_tag_codes(String sale_tag_codes) {
		this.sale_tag_codes = sale_tag_codes;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	private List estados;

	public List getEstados() {
		return estados;
	}

	public void setEstados(List estados) {
		this.estados = estados;
	}
    

    private String p_code;  //产品包编码

	
    public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}


	private File fileName;//上传文件名
	private String fileNameFileName;//
	
	private IProductoManager productoM;
    
    
	
	public String getFileNameFileName() {
		return fileNameFileName;
	}

	public void setFileNameFileName(String fileNameFileName) {
		this.fileNameFileName = fileNameFileName;
	}
	private  GoodsApplyJoin goodsApplyJoin;

	private boolean join_suced =false ;
	
	public String getRef_obj_id() {
		return ref_obj_id;
	}

	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}

	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}

	/**
	 * 停用货品时，展示关联商品列表
	 * @return
	 */
	public String listProRelGoods(){
		this.webpage = this.goodsManager.listProRelGoods(product_id,this.getPage(),this.getPageSize());
		
		return "pro_rel_goods";
	}
	
	public String offerList(){
		try {
			if(offer_name!=null){
				offer_name = URLDecoder.decode(offer_name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage = this.goodsManager.getOfferList(offer_name,this.getPage(), this.getPageSize());
		return  "offerList";
    }
	
	public String  getPriceListById(){
	    this.priceList  = this.goodsManager.getPriceList(goods_id);
	    this.priceListSize = this.priceList.size();
		return "priceList";
	}
	
	public String getOrgByGoodsId(){
	    this.orgList  = this.goodsManager.getOrgByGoodsId(type,goods_id);
	    this.orgListSize = this.orgList.size();
		return "orgList";
	}
	
	//批量
	public String getOrgByGoodsIds(){
		String[] ids=goods_ids.split(",");
		this.orgList  = this.goodsManager.getOrgByGoodsIds(type,ids);
		this.orgListSize = this.orgList.size();
		return "batchOrgList";
	}
	
	//控制商品输入的数量
	public String controlValue(){
		try{
			boolean flag = false;
			flag = this.goodsManager.isOverQtyOrMoney(this.agreement_id, this.lan_code, this.curr_control_value);
			if(flag==true){
			this.json = "{result:0,message:'输入的值小于框架协议'}";
			}else{
				this.json = "{result:1,message:'输入的值大于框架协议控制值,请重新输入'}";
			}
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
			
		
	}
	//刷新缓存
	public String refreshCache(){
		//cacheUtil.refreshGoodsCache();
		try{
			GoodsNetCacheWrite goodsNetCacheWrite = SpringContextHolder.getBean("goodsNetCacheWrite");
			if(Consts.GOODS_CACHE_REFRESH_ALL.equals(type)){//全量刷新商品
				goodsNetCacheWrite.loadAllGoods();
				goodsNetCacheWrite.loadAllProducts();
				goodsNetCacheWrite.loadAllGoodsBySku();
				goodsNetCacheWrite.loadAllVproductsByName();
				goodsNetCacheWrite.loadAllActivites();
			}
			else if(Consts.GOODS_CACHE_REFRESH_NEW.equals(type)){//刷新最近5天修改的商品
				goodsNetCacheWrite.refreshNewGoods();
			}
			else if(Consts.GOODS_CACHE_REFRESH_CFG.equals(type)){//刷新商品配置数据
				goodsNetCacheWrite.loadAllTypes();
				goodsNetCacheWrite.loadAllCats();
				goodsNetCacheWrite.loadGoodsType();
				goodsNetCacheWrite.loadProductType();
				goodsNetCacheWrite.loadBrand();
				goodsNetCacheWrite.loadBrandByTypeId();
				goodsNetCacheWrite.loadBrandModel();
				goodsNetCacheWrite.loadBrandModelByBrandId();
				goodsNetCacheWrite.loadGoodsCatByTypeId();
				goodsNetCacheWrite.loadTerminalNumList();
			}
			json = "{result:0,msg:'执行成功'}";
		}catch(Exception e){
			e.printStackTrace();
			json = "{result:1,msg:'执行失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	//发布商品时检查商品的唯一
	public String checkExists(){
		List<Goods> listGoods=goodsManager.checkExists(cardCode,mktprice,offer_id,charge_type);
		if(listGoods.size()>0){
			this.json = "{result:1}";
		}else{
			this.json = "{result:0}";
		}
		
		return WWAction.JSON_MESSAGE;
		
	}
	
	//商品列表
	public String list(){
		
		this.adminUser=ManagerUtils.getAdminUser();
		this.webpage = goodsManager.searchGoods(catid,auditState,name,supplier_id,sn,brand_id,startTime,endTime,
				market_enable,tagids,staff_nos,order , selfProdType,type,sku,publish_state, this.getPage(),this.getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
		if(!is_edit)
			return "list";
		else
			return "edit_list";
	}
	
	//导入配件
	public String importAdjunct(){
		return "importAdjunct";
	}
	
	//入围商品列表
	public String listInbox(){
		this.selfProdType = "F" ;
		this.listFormActionVal = "goods!listInbox.do" ;
		return list();
	}
	
	//入围商品列表
	public String productListInbox(){
		this.selfProdType = "F" ;
		this.listFormActionVal = "goods!productListInbox.do" ;
		return productList();
	}
	
	//待审核的商品列表
	public String list_audit(){
        stypeList = this.goodsTypeManager.childListStype(String.valueOf(-1));
		this.webpage = goodsManager.searchAuditGoods(goods, this.getPage(),this.getPageSize());
		return "audit_list";
	}

	//审核的商品
	public String showGoodsAudit(){
		goods = goodsManager.getGoods(goods_id);
		goodsArea=goodsAreaManager.getGoodsAreaById(goods_id,lan_id);
		return "goods_audit";
	}
	
	//商品列表--》待申请
	public String list_apply(){
		
		this.adminUser= ManagerUtils.getAdminUser();
		
		if(ManagerUtils.isAdminUser())
			agent_login ="no";
		this.webpage = goodsManager.searchApplyGoods(catid,name,sn,startTime,endTime,typeCode,market_enable,tagids,staff_nos,order, this.getPage(),this.getPageSize());
		
		if(isApplyParStock !=null && !"".equals(isApplyParStock)){
			List<Map> listArea =this.webpage.getResult();
			
			for (int i = 0; i < listArea.size(); i++) {
				Map map = listArea.get(i);
				String type_id = (String) map.get("type_id");
				String goodsId = (String) map.get("goods_id");
				String crm_offer_id = (String) map.get("crm_offer_id");
				
				GoodsType goodsType = goodsCacheUtil.getGoodsTypeById(type_id);
//				if(OrderBuilder.CARD_KEY.equals(goodsType.getType_code()) || OrderBuilder.RECHARGE_CARD_KEY.equals(goodsType.getType_code()) || OrderBuilder.TIME_CARD_KEY.equals(goodsType.getType_code()))
//				{
//					Card card = new Card();
//					card.setGoods_id(goodsId);
//					card.setType_code(goodsType.getType_code());
//					card.setState(Consts.CLOUD_INFO_STATE_0);
//					card.setP_apply_show_parent_stock(isApplyParStock);
//					Map  countMap = cardManager.listc(card);
//					String count = (String) countMap.get("ableCar_count");
//					map.put("goods_stock", count);
//					
//				}else if(OrderBuilder.CLOUD_KEY.equals(goodsType.getType_code()))
//				{
//					Cloud cloud = new Cloud();
//					cloud.setGoods_id(goodsId);
//					cloud.setState(Consts.CLOUD_INFO_STATE_0);
//					cloud.setP_apply_show_parent_stock(isApplyParStock);
//					Map  countMap = cloudManager.listc(cloud);
//					String count = (String) countMap.get("ableCloud_count");
//					
//					//如果是一级分销商看电信，统一变成有货，存放一个标志来判断是否需要超链接
//					if(adminUser.getFounder() == Consts.CURR_FOUNDER3){
//						map.put("goods_isHref", 0);
//						map.put("goods_stock", "有货");
//					}else{
//						map.put("goods_stock", count);
//					}
//					
//				}else if(OrderBuilder.CONTRACT_KEY.equals(goodsType.getType_code()))
//				{
//					
//					AccNbr accNbr = new AccNbr();
//					accNbr.setP_apply_show_parent_num(isApplyParStock);
//					accNbr.setQuery_type(Consts.ACCNBR_QUERY_TYPE_00A);
//					Map countMap = accNbrManager.listc(accNbr);
//					String count = (String) countMap.get("ableCloud_count");
//					
//					map.put("goods_stock", count);
//				}
				
				map.put("goodsType", goodsType.getType_code());
			}
		}
		
		return "list_apply";
	}
	//成功订购的商品
	public String list_apply_yes(){
		
		this.adminUser= ManagerUtils.getAdminUser();
		
		if(ManagerUtils.isAdminUser())
			agent_login ="no";
		this.webpage = goodsManager.searchApplyYesGoods(catid,name,sn,startTime,endTime,typeCode,market_enable,tagids,staff_nos,order, this.getPage(),this.getPageSize());
		
		List<Map> listArea =this.webpage.getResult();
		
		for (int i = 0; i < listArea.size(); i++) {
			Map map = listArea.get(i);
			String type_id = (String) map.get("type_id");
			String goodsId = (String) map.get("goods_id");
			String crm_offer_id = (String) map.get("crm_offer_id");
			
			GoodsType goodsType = goodsCacheUtil.getGoodsTypeById(type_id);
//			if(OrderBuilder.CARD_KEY.equals(goodsType.getType_code()) || OrderBuilder.RECHARGE_CARD_KEY.equals(goodsType.getType_code()) || OrderBuilder.TIME_CARD_KEY.equals(goodsType.getType_code()))
//			{
//				Card card = new Card();
//				card.setGoods_id(goodsId);
//				card.setType_code(goodsType.getType_code());
//				Map  countMap = cardManager.listc(card);
//				String count = (String) countMap.get("ableCar_count");
//				map.put("goods_stock", count);
//				
//			}else if(OrderBuilder.CLOUD_KEY.equals(goodsType.getType_code()))
//			{
//				Cloud cloud = new Cloud();
//				cloud.setGoods_id(goodsId);
//				Map  countMap = cloudManager.listc(cloud);
//				String count = (String) countMap.get("ableCloud_count");
//				
//				//如果是一级分销商看电信，统一变成有货，存放一个标志来判断是否需要超链接
//				if(adminUser.getFounder() == Consts.CURR_FOUNDER3){
//					map.put("goods_isHref", 0);
//					map.put("goods_stock", "有货");
//				}else{
//					map.put("goods_stock", count);
//				}
//			}else if(OrderBuilder.CONTRACT_KEY.equals(goodsType.getType_code()))
//			{
//				AccNbr accNbr = new AccNbr();
//				Map countMap = accNbrManager.listc(accNbr);
//				String count = (String) countMap.get("ableCloud_count");
//				
//				map.put("goods_stock", count);
//			}
			
			map.put("goodsType", goodsType.getType_code());
		}
		
		return "list_apply_yes";
	}
	//一‘二级分销商品展示商品申请成功数量
	public String applySuccNum(){
		return "applySuccNum";
	}
	//转入申请商品页面
	public String showGoodsApply(){
		try {
			if(apply_desc!=null){
				apply_desc=URLDecoder.decode(apply_desc,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		lanList = goodsUsageManager.getLanByGoodsId(goods_id);
		
		goods = goodsManager.getGoods(goods_id);
		
		goodsType = goodsManager.getGoodsTypeByGoodsId(goods_id);
		String user_id = ManagerUtils.getUserId();
		//在es_partner表中用user_id找partner_id
		
		PartnerInfoOneReq partnerInfoOneReq = new PartnerInfoOneReq();
		partnerInfoOneReq.setUserid(user_id);
		PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneReq);
		
		partner = new Partner();
		if(partnerInfoResp != null){
			partner = partnerInfoResp.getPartner();
		}
		String partner_id=partner.getPartner_id();
		PartnerAddrReq partnerAddrReq = new PartnerAddrReq();
		partnerAddrReq.setAddressNum(partner_id);
		
		PartnerAddrListReq partnerAddrListReq = new PartnerAddrListReq();
		partnerAddrListReq.setPartner_id(partner_id);
		
		PartnerAddrListResp partnerAddrListResp = partnerServ.getPartnerAddress(partnerAddrListReq);
		
		addressList = new ArrayList<PartnerAddress>();
		if(partnerAddrListResp != null){
			addressList = partnerAddrListResp.getList();
		}
		
		
//		//检测商品是否被删除
//		if(goods.getName()==null||"".equals(goods.getName())){
//			this.json = "{result:0}";
//			return this.JSON_MESSAGE;
//		}
		//获取被冻结的分销商列表,分销商检测自己是否被冻结
//		List<Partner> list_partnerList=partnerManager.searchPartneridList();
//		String userid=ManagerUtils.getUserId();
//		for(int i=0;i<list_partnerList.size();i++){
//			if(list_partnerList.get(i).getUserid().equals(userid)){
//				this.json = "{result:2}";
//				return this.JSON_MESSAGE;
//			}
//		}
//		//分销商检测商品是否被冻结
//		List<GoodsUsage> list_usage=goodsUsageManager.findGoodsUsageList(ManagerUtils.getAdminUser().getParuserid(), goods_id);
//		if(list_usage.size()>0){
//			this.json = "{result:1}";
//			return this.JSON_MESSAGE;
//		}
//		//二级检查上级分销商是否被冻结
//		if(ManagerUtils.isSecondPartner()){
//			String paruserid=ManagerUtils.getAdminUser().getParuserid();
//			for(int i=0;i<list_partnerList.size();i++){
//				if(list_partnerList.get(i).getUserid().equals(paruserid)){
//					this.json = "{result:3}";
//					return this.JSON_MESSAGE;
//				}
//			}
//		}
		
//		if(partner!=null){
//			partner_id=partner.getPartner_id();
//		}else{
//			this.json = "{result:1}";
//			return this.JSON_MESSAGE;
//		}
		
		
		return "goods_apply";
	}
	
	public String searchDialog(){
		//tagHtmlList = goodsManager.fillAddInputData();
		return "search_dialog";
	}
	public String searchProductDialog(){
		return "search_product_dialog";
	}
	//跳转到商品阀值修改页面
	public String showGoodsAuditYes(){
		goods = goodsManager.getGoods(goods_id);
		goodsUsage=goodsUsageManager.getGoodsUsageByGoodsid(goods_id,ManagerUtils.getUserId(),usageid);
		return "showGoodsAuditYes";
	}
	
	public String batchEdit(){
		try{
			this.goodsManager.batchEdit();
			this.json ="{result:1}";
		}catch(RuntimeException e ){
			e.printStackTrace();
			this.json = "{result:0}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String getCatTree(){
		this.catList = this.goodsCatManager.listAllChildren(0 + "");
		return "cat_tree";
	}
	
	
	public String getStaffCatTree(){
		this.catList = this.goodsCatManager.listAllChildrenByAgentNo(0 + "");
		return "cat_tree";
	}
	
	//商品回收站列表
	public String trash_list(){
		this.webpage = this.goodsManager.pageTrash(name, sn, order, this.getPage(), this.getPageSize());
		return "trash_list";
	}
	
	//还原
	public String reduccion(){
		try {
			goodsManager.reduccion(ids);
			msg.addResult(MESSAGE,"还原成功");
			msg.addResult("url", "goods!trash_list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			msg.addResult(MESSAGE,"还原失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	public String claro(){
		try {
			goodsManager.claro(ids);
			msg.addResult(MESSAGE,"清除成功");
			msg.addResult("url", "goods!trash_list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			msg.addResult(MESSAGE,"清除失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	//删除
	public String delete(){
		try {
			goodsManager.delete(id);
			msg.addResult(MESSAGE,"删除成功");
			msg.addResult("url", "goods!productList.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			msg.addResult(MESSAGE,"删除失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	//还原 
	public String revert(){
		try{
			this.goodsManager.revert(id);
			this.json = "{'result':0,'message':'清除成功'}";
		 }catch(RuntimeException e){
			 this.json="{'result':1,'message':'清除失败'}";
		 }
		return WWAction.JSON_MESSAGE;
	}
	
	//清除
	public String clean(){
		try{
			this.goodsManager.clean(id);
			this.json = "{'result':0,'message':'清除成功'}";
		 }catch(RuntimeException e){
			 e.printStackTrace();
			 this.json="{'result':1,'message':'清除失败'}";
		 }
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	public String selector_list_ajax(){
		
		return "selector";
	}
	

	private List<String> tagHtmlList ;
	
	private List lvList ;
	private List<GoodsStype> childStypeList;
	private String parent_id;
	
	private MemberPriceInf memberPriceServ;
	
	public String listTypeChildrens(){
		childStypeList=this.goodsTypeManager.childListStype(parent_id);
		return "type_childrens";
	}

	/**
	 * 到商品添加页
	 */
	public String add() {
		
		founder =ManagerUtils.getAdminUser().getFounder()+"";
		goodsRules = new GoodsRules();
		actionName = "goods!saveAdd.do";
		this.adminUser = ManagerUtils.getAdminUser();
		is_edit =false;
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.codeList = dcPublicCache.getList("6666");
//		if(!"T".equals(this.selfProdType)){
		PricePrivListReq req = new PricePrivListReq();
		
		PricePrivListResp resp = new PricePrivListResp();
		resp = memberPriceServ.loadPricePrivList(req);
		if(resp != null){
			this.lvList = resp.getPricePrivList();
		}
//		}else{
//			List tmp = this.memberPriceManager.loadPricePrivList(null ) ;
//			lvList = new ArrayList() ;
//			lvList.add(tmp.get(0)) ;
//			lvList.add(tmp.get(1)) ;
//			lvList.add(tmp.get(2)) ;
//		}
		tagHtmlList = goodsManager.fillAddInputData();

		return Action.INPUT;
	}
	
	/**
	 * 到商品添加页
	 */
	public String addGoodsEcs() {
		HttpServletRequest request =ThreadContextHolder.getHttpRequest();
		request.setAttribute("type", "goods");
		founder =ManagerUtils.getAdminUser().getFounder()+"";
		goodsRules = new GoodsRules();
		actionName = "goods!saveAdd.do";
		this.adminUser = ManagerUtils.getAdminUser();
		is_edit =false;
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.codeList = dcPublicCache.getList("6666");
//		if(!"T".equals(this.selfProdType)){
		PricePrivListReq req = new PricePrivListReq();
		
		PricePrivListResp resp = new PricePrivListResp();
		resp = memberPriceServ.loadPricePrivList(req);
		if(resp != null){
			this.lvList = resp.getPricePrivList();
		}
		tagHtmlList = goodsManager.fillAddInputData();
		
		return "goodsInputEcs";
	}
	
	public String selectShopDialog(){
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(goods_type)){
			this.listFormActionVal = "goods!goodsBatchPublish.do";
		}
		else{
			this.listFormActionVal = "goods!productsBatchPublish.do";
		}
		
		return "selectShopDialog";
	}
	
	//商品批量发布
	public String goodsBatchPublish(){
		params.put("cat_id", catid);//
		params.put("name", name);//
		params.put("brand_id", brand_id);//
		params.put("market_enable", "1");
		params.put("type", "goods");
		params.put("sku", sku);
		params.put("publish_state", publish_state==null?"":String.valueOf(publish_state));
		String message = goodsManager.goodsBatchPublish(params, this.getPage(),this.getPageSize());
		this.msgs.add(message);
		this.urls.put("商品列表", "goods!goodsList.do?type=goods");
		return WWAction.MESSAGE;
	}
	//货品批量发布
	public String productsBatchPublish(){
		params.put("cat_id", catid);
		params.put("name", name);
		params.put("brand_id", brand_id);
		params.put("market_enable", "1");
		params.put("type", "product");
		params.put("sku", sku);
		String message = goodsManager.productsBatchPublish(params, this.getPage(),this.getPageSize());
		this.msgs.add(message);
		this.urls.put("货品列表", "goods!productList.do?type=product");
		return WWAction.MESSAGE;
	}
	
	public String goodsList(){
		this.adminUser=ManagerUtils.getAdminUser();
		
		if (StringUtil.isEmpty(this.getOrg_names())) {
			this.setOrg_ids("");
		}
		
		setQueryParams();
		params.put("type", "goods");
		this.webpage = goodsManager.searchGoodsECS(params, this.getPage(),this.getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
    	
		this.setListFormActionVal("goods!goodsList.do");
		
		return "goods_list";
		
		
	}
	
	public String saleGoodsList(){
		this.adminUser=ManagerUtils.getAdminUser();
		this.goodsTypeList = this.goodsManager.getGoodsTypeList();
		this.webpage = goodsManager.searchSaleGoodsECS(params, this.getPage(),this.getPageSize());
				
		return "sale_goods_list";
		
	}
	
	/**
	 * 添加销售商品
	 */
	public String addSaleGoodsEcs() {
		try {
			actionName = "goods!saveAddSale.do";
			this.adminUser = ManagerUtils.getAdminUser();
			is_edit =false;
			
			this.regionList = activityAction.getConsts("DC_COMMON_REGION_GUANGDONG");
			
			this.goodsTypeList = this.goodsManager.getGoodsTypeList();
//			tagHtmlList = goodsManager.fillAddInputData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sale_goods_input_ecs";
	}
	
	/**
	 * 修改销售商品
	 */
	public String editSaleGoodsEcs() {
		try {
			actionName = "goods!saveEditSale.do";
			this.adminUser = ManagerUtils.getAdminUser();
			is_edit =true;
			
			saleGoodsView = this.goodsManager.querySaleGoods(sale_gid);
			this.regionList = activityAction.getConsts("DC_COMMON_REGION_GUANGDONG");
			this.goodsListRelSale = this.goodsManager.getGoodsListRelSale(sale_gid);
			this.goodsTypeList = this.goodsManager.getGoodsTypeList();
			this.goodsTagList = this.goodsManager.getGoodsTagList(sale_gid);
			this.saleTagList = this.goodsManager.getSaleTagList(sale_gid);
//			tagHtmlList = goodsManager.fillAddInputData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sale_goods_input_ecs";
	}
	
	/**
	 * 修改销售商品
	 */
	public String copySaleGoodsEcs() {
		try {
			actionName = "goods!saveAddSale.do";
			this.adminUser = ManagerUtils.getAdminUser();
			is_edit =true;
			
			saleGoodsView = this.goodsManager.querySaleGoods(sale_gid);
			this.regionList = activityAction.getConsts("DC_COMMON_REGION_GUANGDONG");
			this.goodsListRelSale = this.goodsManager.getGoodsListRelSale(sale_gid);
			this.goodsTypeList = this.goodsManager.getGoodsTypeList();
			this.goodsTagList = this.goodsManager.getGoodsTagList(sale_gid);
			this.saleTagList = this.goodsManager.getSaleTagList(sale_gid);
//			tagHtmlList = goodsManager.fillAddInputData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sale_goods_input_ecs";
	}
	
	//保存新增销售商品
	public String saveAddSale() {
		try {
			AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
			String userid = adminUser.getUserid();
			saleGoods.setCreator_id(userid);
			saleGoods.setCreate_time(DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT));
			saleGoods.setMarket_enable("0");
			saleGoods.setPackage_type(saleGoods.getGoods_type());
//			saleGoods.setIntro(intro);
			
			if((tag_codes == null || tag_codes.equals("")) && (sale_tag_codes == null || sale_tag_codes.equals(""))) {
				this.json = "{result:0,message:'标签信息不能为空！'}";
				
				return WWAction.JSON_MESSAGE;
			}
			
			this.goodsManager.addSaleGoods(saleGoods,skus,tag_codes,sale_tag_codes,sort);
			this.json="{result:1,message:'操作成功！'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json="{result:0,message:'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	//保存修改销售商品
	public String saveEditSale(){
		try{
			AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
			String userid = adminUser.getUserid();
			saleGoods.setModifier_id(userid);
			saleGoods.setModify_time(DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT));
			saleGoods.setPackage_type(saleGoods.getGoods_type());
//			saleGoods.setIntro(intro);
			
			logger.info("goodsType = = = = "+saleGoods.getPackage_type());
			logger.info("channelType = = = "+saleGoods.getChannel_type());
			if((tag_codes == null || tag_codes.equals("")) && (sale_tag_codes == null || sale_tag_codes.equals(""))) {
				this.json = "{result:0,message:'标签信息不能为空！'}";
				
				return WWAction.JSON_MESSAGE;
			}
			
			this.goodsManager.editSaleGoods(saleGoods,skus,tag_codes,sale_tag_codes,sort);
			
			this.json = "{result:1,message:'操作成功'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			this.json="{result:0,message:'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	public String updateSaleMarketEnable(){
		try {
			String gids = "";
			for (int i = 0; i < sale_gids.length; i++) {
				if(i == 0){
					gids += "'"+sale_gids[i]+"'";
				}else{
					gids += ",'"+sale_gids[i]+"'";
				}
			}
			this.goodsManager.updateSaleMarketEnable(gids,this.market_enable);
			this.json="{result:1}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json="{result:0}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	//商品操作日志列表
	public String showGoodsOperLog(){
		setQueryParams();
		params.put("type", "goods");
		this.webpage = goodsManager.searchGoodsOperLog(params, this.getPage(),this.getPageSize());
		List<Goods> list = this.webpage.getResult();
		this.setListFormActionVal("goods!showGoodsOperLog.do");
		return "goods_log";
	}
	
	//货品操作日志列表
	public String showProductOperLog(){
		setQueryParams();
		params.put("type", "product");
		this.webpage = goodsManager.searchProductOperLog(params, this.getPage(),this.getPageSize());
		List<Goods> list = this.webpage.getResult();
		this.setListFormActionVal("goods!showProductOperLog.do");
		return "products_log";
	}
	
	public void setQueryParams(){
		if (StringUtil.isEmpty(this.cat_name)) {
			this.setCatid("");
		}
		if (StringUtil.isEmpty(this.brand_name)) {
			this.setBrand_id("");
		}
		if (StringUtil.isEmpty(this.params.get("model_name"))) {
			this.params.put("model_code", "");
		}
		this.params.put("cat_id", catid);
		this.params.put("auditState", auditState);
		this.params.put("name", name);
		this.params.put("supplier_id", supplier_id);
		this.params.put("sn", sn);
		this.params.put("brand_id", brand_id);
		this.params.put("startTime", startTime);
		this.params.put("endTime", endTime);
		this.params.put("market_enable", market_enable==null?"":String.valueOf(market_enable));
		this.params.put("order", order);
		this.params.put("selfProdType", selfProdType);
		this.params.put("sku", sku);
		this.params.put("org_ids", org_ids);
		this.params.put("publish_state", publish_state==null?"":String.valueOf(publish_state));
	}

	// taobao search page
	// used as condition to search
	private String taobao_search_goods_cat;

	public String getTaobao_search_goods_cat()
	{
		return null == this.taobao_search_goods_cat ? "69030101"
				: this.taobao_search_goods_cat;
	}

	public void setTaobao_search_goods_cat(String taobao_search_goods_cat)
	{
		this.taobao_search_goods_cat = taobao_search_goods_cat;
	}

	public boolean isTaobao_search_contract_type()
	{
		boolean result = false;
		if (null == this.getTaobao_search_goods_cat())
		{
			result = false;
		}
		if ("69030101".equals(this.getTaobao_search_goods_cat())
				|| "69030102".equals(this.getTaobao_search_goods_cat())
				|| "69030103".equals(this.getTaobao_search_goods_cat())
				|| "69030104".equals(this.getTaobao_search_goods_cat())
				|| "690301041".equals(this.getTaobao_search_goods_cat())
				|| "690301042".equals(this.getTaobao_search_goods_cat()))
		{
			result = true;
		}

		return result;

	}

	/**
	 * 展示商品-淘宝-ECS
	 * 
	 * @return
	 */
	public String goodsTaobaoECS()
	{
		this.adminUser = ManagerUtils.getAdminUser();

		List<Goods> list = new ArrayList<Goods>();
		if (null != this.getTaobao_search_goods_cat())
		{
			this.webpage = goodsManager.searchGoodsTaobaoECS(this.getName(),
					this.getTaobao_search_goods_cat(), this.getMarket_enable(),
					this.getOrder(), this.getPage(), this.getPageSize());

			list = this.webpage.getResult();
		}

		is_edit = is_edit == null ? Boolean.FALSE : Boolean.TRUE;

		this.setListFormActionVal("goods!goodsTaobaoECS.do");

		return "goods_taobao_ecs";

	}
	
	public String productList(){
		estados  = productoM.getEstados();
		
		this.adminUser=ManagerUtils.getAdminUser();
		
		if (StringUtil.isEmpty(this.params.get("org"))) {
			this.setOrg_ids("");
		}
		
		setQueryParams();
		params.put("type", "product");
		this.webpage = goodsManager.searchProductsECS(params,getPage(),getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
		
		this.setListFormActionVal("goods!productList.do");
		
		return "products_list";
		
	}
	
	public String goodsImportLogsECS(){
		this.setListFormActionVal("goods!goodsImportLogsECS.do");
		this.webpage = goodsManager.searchGoodsImportLogsECS(params, this.getPage(), this.getPageSize());
		return "goods_import_logs";
	}
	
	public String terminalImportLogsECS(){
		this.setListFormActionVal("goods!terminalImportLogsECS.do");
		this.webpage = goodsManager.searchPhoneImportLogsECS(params, this.getPage(), this.getPageSize());
		return "terminal_import_logs";
	}
	
	public String importTerminals(){
		try {
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			String rtnmsg = goodsManager.importTerminalExcel(file, params);
			if ("EXISTS_FILE".equalsIgnoreCase(rtnmsg)) {
				this.msgs.add("该文件【"+ fileNameFileName +"】已经导入，不能重复导入！");
				this.urls.put("手机导入日志列表", "goods!terminalImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			if ("0".equals(rtnmsg)) {
				this.msgs.add("没有手机货品数据，请检查导入文件内容！");
				this.urls.put("手机导入日志列表", "goods!terminalImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			String[] rtnArr = rtnmsg.split("#");
			this.msgs.add("此次导入的手机货品总数是" + rtnArr[0] + "个。"
					+ " 其中，成功导入数为" + rtnArr[1] + "个，失败数为" + rtnArr[2] + "个 ，批次号为"+rtnArr[3]);
			this.urls.put("手机导入日志列表", "goods!terminalImportLogsECS.do");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			this.msgs.add("手机导入失败");
			this.urls.put("手机导入日志列表", "goods!terminalImportLogsECS.do");
		}
		
		return WWAction.MESSAGE;
	}
	
	
	/**
	 * 添加商品的商品包选择
	 * @return
	 */
	public String tagsSelectList(){
		
		try{
			if(StringUtils.isNotEmpty(search_tag_name)){
				search_tag_name = URLDecoder.decode(search_tag_name,"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.webpage = goodsManager.searchTags( this.getPage(),this.getPageSize(),search_tag_name);
		
		return "tags_select_list";
	}
	/**
	 * 添加商品的货品选择
	 * @return
	 */
	public String productSelectList(){
		try {
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.params.put("name", name);
		this.params.put("type", "product");
		this.params.put("market_enable", "1");
		this.adminUser=ManagerUtils.getAdminUser();
		this.webpage = goodsManager.searchProductsECS(params,getPage(),getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
		
		return "products_select_list";
	}
	
	/**
	 * 套餐选择弹出框
	 * @return
	 */
	public String planTitleSelectList(){
		try {
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.params.put("name", name);
		this.params.put("type", "product");
		this.params.put("type_id", this.type_id);
		this.params.put("market_enable", "1");
		this.adminUser=ManagerUtils.getAdminUser();
		this.webpage = goodsManager.searchProductsECS(params,getPage(),getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
		
		return "plantitle_select_list";
	}
	
	/**
	 * 商品选择弹出框
	 * @return
	 */
	public String goodsSelectList(){
		try {
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.params.put("name", name);
		this.params.put("type", "goods");
		this.params.put("market_enable", "1");
		this.params.put("cat_id", this.cat_id);
		this.adminUser=ManagerUtils.getAdminUser();
		this.webpage = goodsManager.searchProductsECS(params,getPage(),getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		is_edit =is_edit == null?Boolean.FALSE:Boolean.TRUE;
		
		return "goods_select_list";
	}
	
	/**
	 * 到货品添加页-广东联通ECS
	 */
	public String productAdd() {
		HttpServletRequest request =ThreadContextHolder.getHttpRequest();
		request.setAttribute("type", "product");
		founder =ManagerUtils.getAdminUser().getFounder()+"";
		goodsRules = new GoodsRules();
		actionName = "goods!saveAdd.do";
		this.adminUser = ManagerUtils.getAdminUser();
		is_edit =false;
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.codeList = dcPublicCache.getList("6666");
//		if(!"T".equals(this.selfProdType)){
		PricePrivListReq req = new PricePrivListReq();
		
		PricePrivListResp resp = new PricePrivListResp();
		resp = memberPriceServ.loadPricePrivList(req);
		if(resp != null){
			this.lvList = resp.getPricePrivList();
		}
		tagHtmlList = goodsManager.fillAddInputData();
		colorList = goodsManager.listColor();
		this.setGoodsView(new HashMap());
		
		//生成默认的SN，页面可以修改
		this.goodsView.put("sn", goodsManager.createSN("", "", ""));
		
		return "product_input";
	}
	
	/**
	 * 到商品添加页
	 */
	public String addBind() {
		
		actionName = "goods!saveBindAdd.do";
		return "bind_goods_input";
	}
	
	/**
	 * 校验商品编码的唯一性
	 */
	public String checkGoodsSN() {
		
       boolean result = this.goodsManager.checkGoodsSN(sn,goods_id);
	   if(!result){
		   this.json = "{result:0, 'message':'OK'}";
	   }else{
		   this.json = "{'result':1, 'message':'条形码已经存在！'}";
	   }

		return WWAction.JSON_MESSAGE;
	}
	
	public String copy(){
		//先将商品更新为下架
		try{
		is_copy = true;
		founder =ManagerUtils.getAdminUser().getFounder()+"";
		//this.goodsManager.updateField("market_enable", 0, goods_id);
		actionName = "goods!saveAdd.do";
		this.adminUser = ManagerUtils.getAdminUser();
		if(StringUtils.isNotEmpty(ref_obj_id)){
			goods_id = ref_obj_id ;
		}
		is_edit =false;
		
		
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		catList = goodsCatManager.listAllChildren(0 + "");
		GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(goods_id);
		goodsView = editDTO.getGoods();
		if(StringUtil.isEmpty((String)goodsView.get("brand_id")))
			goodsView.put("brand_id", "0");
		String type_id=goodsView.get("type_id").toString();
		goodsType=goodsTypeManager.get(type_id);
		this.tagHtmlList = editDTO.getHtmlList();
		goodsView.put("store", store);//放入传入的库存
		
//				if(!"T".equals(this.selfProdType)){
		PricePrivListReq req = new PricePrivListReq();
		req.setGoods_id(goods_id);
		PricePrivListResp resp = new PricePrivListResp();
		resp = memberPriceServ.loadPricePrivList(req);
		if(resp != null){
			this.lvList = resp.getPricePrivList();
		}
//				}else{
//					List tmp = this.memberPriceManager.loadPricePrivList(goods_id ) ;
//					if(tmp == null || tmp.isEmpty())
//						tmp = this.memberPriceManager.loadPricePrivList(null ) ;
//					lvList = new ArrayList() ;
//					lvList.add(tmp.get(0)) ;
//					lvList.add(tmp.get(1)) ;
//					lvList.add(tmp.get(2)) ;
//				}
		
		
		goods = new Goods();
		//时间格式处理
		String arrival = null ;
		if(goodsView.get("arrival") != null && 
				goodsView.get("arrival") instanceof java.sql.Timestamp)
			arrival = ((java.sql.Timestamp)goodsView.get("arrival")).toString() ;
		else
			arrival = (String)goodsView.get("arrival") ;
		
		if(StringUtils.isNotEmpty(arrival)){
			if(arrival.length()<10){
				goodsView.put("arrival", arrival) ; 
			}else{
			goodsView.put("arrival", arrival.substring( 0, 10)) ; 
			}
		}
		
		String exp_date = null ;
		if(goodsView.get("exp_date") != null && 
				goodsView.get("exp_date") instanceof java.sql.Timestamp)
			exp_date = ((java.sql.Timestamp)goodsView.get("exp_date")).toString() ;
		else
			exp_date = (String)goodsView.get("exp_date") ;
		
		if(StringUtils.isNotEmpty(exp_date) ){
			goodsView.put("exp_date", exp_date.substring( 0, 10)) ; 
		}
		
		
	
		BeanUtils.copyProperties(goods, goodsView);
		fieldList= this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
		for(GoodsField field:fieldList){
			String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
			field.setInputHtml(html);
		}
		
		//商品控制信息APPLY_JOIN
		//goods.audit_state == '00M' && goods.apply_join == 'T' 
        String audit_state = goodsView.get("audit_state").toString();
        int userFounder  =  adminUser.getFounder();
        if(userFounder==4)
        {
        	this.join_suced = true;
        }
	    if("F".equals(this.selfProdType)){
		DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.codeList = dcPublicCache.getList("6666");
		this.controlTypeList = dcPublicCache.getList("6661");
		this.subControlTypeList = dcPublicCache.getList("6662");
		this.companyTypeList = dcPublicCache.getList("6663");
		this.goodsControlList = this.goodsManager.getControlById(this.goods_id);
		Map map = (Map) this.goodsControlList.get(0);
		this.control_type =map.get("control_type").toString() ;
		this.sub_control_type = map.get("sub_control_type").toString();
		this.control_value = map.get("control_value").toString();
	    }
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		//goodsView.put("goods_id", "");
		//goods.setGoods_id("");

		return Action.INPUT;
	}
	
	/**
	 * 到商品修改页面
	 */
	@SuppressWarnings("unchecked")
	public String  edit(){
		//先将商品更新为下架
		try{
		
			founder =ManagerUtils.getAdminUser().getFounder()+"";
			actionName = "goods!saveEdit.do";
			this.adminUser = ManagerUtils.getAdminUser();
			if(StringUtils.isNotEmpty(ref_obj_id)){
				goods_id = ref_obj_id ;
			}
			is_edit =true;
			
			
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.tabs = JspPageTabs.getTabs("goods");
			catList = goodsCatManager.listAllChildren(0 + "");
			GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(goods_id);
			goodsView = editDTO.getGoods();
			if(StringUtil.isEmpty((String)goodsView.get("brand_id")))
				goodsView.put("brand_id", "0");
			String type_id=goodsView.get("type_id").toString();
			goodsType=goodsTypeManager.get(type_id);
			this.tagHtmlList = editDTO.getHtmlList();
			goodsView.put("store", store);//放入传入的库存
			
			PricePrivListReq req = new PricePrivListReq();
			req.setGoods_id(goods_id);
			PricePrivListResp resp = new PricePrivListResp();
			resp = memberPriceServ.loadPricePrivList(req);
			if(resp != null){
				this.lvList = resp.getPricePrivList();
			}
			
			goods = new Goods();
			//时间格式处理
			String arrival = null ;
			if(goodsView.get("arrival") != null && 
					goodsView.get("arrival") instanceof java.sql.Timestamp)
				arrival = ((java.sql.Timestamp)goodsView.get("arrival")).toString() ;
			else
				arrival = (String)goodsView.get("arrival") ;
			
			if(StringUtils.isNotEmpty(arrival)){
				if(arrival.length()<10){
					goodsView.put("arrival", arrival) ; 
				}else{
				goodsView.put("arrival", arrival.substring( 0, 10)) ; 
				}
			}
			
			String exp_date = null ;
			if(goodsView.get("exp_date") != null && 
					goodsView.get("exp_date") instanceof java.sql.Timestamp)
				exp_date = ((java.sql.Timestamp)goodsView.get("exp_date")).toString() ;
			else
				exp_date = (String)goodsView.get("exp_date") ;
			
			if(StringUtils.isNotEmpty(exp_date) ){
				goodsView.put("exp_date", exp_date.substring( 0, 10)) ; 
			}
		
			BeanUtils.copyProperties(goods, goodsView);
			fieldList= this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
			for(GoodsField field:fieldList){
				String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
				field.setInputHtml(html);
			}
			
			//商品控制信息APPLY_JOIN
			//goods.audit_state == '00M' && goods.apply_join == 'T' 
	        String audit_state = goodsView.get("audit_state").toString();
	        int userFounder  =  adminUser.getFounder();
	        if(userFounder==4)
	        {
	        	this.join_suced = true;
	        }
		    if("F".equals(this.selfProdType)){
				DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
				this.codeList = dcPublicCache.getList("6666");
				this.controlTypeList = dcPublicCache.getList("6661");
				this.subControlTypeList = dcPublicCache.getList("6662");
				this.companyTypeList = dcPublicCache.getList("6663");
				this.goodsControlList = this.goodsManager.getControlById(this.goods_id);
				Map map = (Map) this.goodsControlList.get(0);
				this.control_type =map.get("control_type").toString() ;
				this.sub_control_type = map.get("sub_control_type").toString();
				this.control_value = map.get("control_value").toString();
		    }
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
		/*
		List<GoodsActionRule> ruleList = new ArrayList<GoodsActionRule>();
		ruleList = this.goodsManager.queryGoodsRules(goods_id);
		goodsRules = new GoodsRules();
		
		if(ruleList != null && !ruleList.isEmpty()){
			for (int i = 0; i < ruleList.size(); i++) {
				GoodsActionRule goodsActionRule = ruleList.get(i);
				String action_code = goodsActionRule.getAction_code();
				if(Consts.GOODS_RULES_CONFIRM.equals(action_code)){
					goodsRules.setInsure(goodsActionRule.getStatus());
				}else if(Consts.GOODS_RULES_PAY.equals(action_code)){
					goodsRules.setPay(goodsActionRule.getStatus());
				}else if(Consts.GOODS_RULES_ACCEPT.equals(action_code)){
					goodsRules.setAccept(goodsActionRule.getStatus());
				}else if(Consts.GOODS_RULES_DELIVERY.equals(action_code)){
					goodsRules.setDelivery(goodsActionRule.getStatus());
				}
			}
		}
		*/
		
		return Action.INPUT;
	}
	
	/**
	 * 到货品修改页面
	 */
	@SuppressWarnings("unchecked")
	public String productEdit(){
		
		//先将商品更新为下架
		try{
			HttpServletRequest request =ThreadContextHolder.getHttpRequest();
			request.setAttribute("type", "product");
			founder =ManagerUtils.getAdminUser().getFounder()+"";
			//this.goodsManager.updateField("market_enable", 0, goods_id);
			actionName = "goods!saveEdit.do";
			this.adminUser = ManagerUtils.getAdminUser();
			if(StringUtils.isNotEmpty(ref_obj_id)){
				goods_id = ref_obj_id ;
			}
			is_edit =true;
			
			
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.tabs = JspPageTabs.getTabs("goods");
			catList = goodsCatManager.listAllChildren(0 + "");
			GoodsEditDTO editDTO = goodsManager.getGoodsEditData(goods_id);
			goodsView = editDTO.getGoods();
			Integer have_spec = (Integer) goodsView.get("have_spec");
			if(have_spec==0){
				Map color = this.goodsManager.getProductColor(goods_id);
				goodsView.put("color", color.get("color"));
			}
			if(goodsView.get("sku")==null | "".equals(goodsView.get("sku"))){
				String psku = goodsManager.getSku(goods_id);
				goodsView.put("sku", psku);
			}
			if(StringUtil.isEmpty((String)goodsView.get("brand_id")))
				goodsView.put("brand_id", "0");
			String type_id=goodsView.get("type_id").toString();
			goodsType=goodsTypeManager.get(type_id);
			this.tagHtmlList = editDTO.getHtmlList();
			goodsView.put("store", store);//放入传入的库存
			
	//		if(!"T".equals(this.selfProdType)){
			PricePrivListReq req = new PricePrivListReq();
			req.setGoods_id(goods_id);
			PricePrivListResp resp = new PricePrivListResp();
			resp = memberPriceServ.loadPricePrivList(req);
			if(resp != null){
				this.lvList = resp.getPricePrivList();
			}
			goods = new Goods();
			//时间格式处理
			String arrival = null ;
			if(goodsView.get("arrival") != null && 
					goodsView.get("arrival") instanceof java.sql.Timestamp)
				arrival = ((java.sql.Timestamp)goodsView.get("arrival")).toString() ;
			else
				arrival = (String)goodsView.get("arrival") ;
			
			if(StringUtils.isNotEmpty(arrival)){
				if(arrival.length()<10){
					goodsView.put("arrival", arrival) ; 
				}else{
				goodsView.put("arrival", arrival.substring( 0, 10)) ; 
				}
			}
			
			String exp_date = null ;
			if(goodsView.get("exp_date") != null && 
					goodsView.get("exp_date") instanceof java.sql.Timestamp)
				exp_date = ((java.sql.Timestamp)goodsView.get("exp_date")).toString() ;
			else
				exp_date = (String)goodsView.get("exp_date") ;
			
			if(StringUtils.isNotEmpty(exp_date) ){
				goodsView.put("exp_date", exp_date.substring( 0, 10)) ; 
			}
			
			colorList = goodsManager.listColor();
			BeanUtils.copyProperties(goods, goodsView);
			fieldList= this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
			for(GoodsField field:fieldList){
				String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
				field.setInputHtml(html);
			}
			
			//商品控制信息APPLY_JOIN
			//goods.audit_state == '00M' && goods.apply_join == 'T' 
	        String audit_state = goodsView.get("audit_state").toString();
	        int userFounder  =  adminUser.getFounder();
	        if(userFounder==4)
	        {
	        	this.join_suced = true;
	        }
		    if("F".equals(this.selfProdType)){
				DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
				this.codeList = dcPublicCache.getList("6666");
				this.controlTypeList = dcPublicCache.getList("6661");
				this.subControlTypeList = dcPublicCache.getList("6662");
				this.companyTypeList = dcPublicCache.getList("6663");
				this.goodsControlList = this.goodsManager.getControlById(this.goods_id);
				Map map = (Map) this.goodsControlList.get(0);
				this.control_type =map.get("control_type").toString() ;
				this.sub_control_type = map.get("sub_control_type").toString();
				this.control_value = map.get("control_value").toString();
		    }
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return "product_input";
	}
	
	/**
	 * 到上品修改页面
	 */
	@SuppressWarnings("unchecked")
	public String goodsEditEcs(){
		
		//先将商品更新为下架
		try{
			HttpServletRequest request =ThreadContextHolder.getHttpRequest();
			request.setAttribute("type", "goods");
			founder =ManagerUtils.getAdminUser().getFounder()+"";
			//this.goodsManager.updateField("market_enable", 0, goods_id);
			actionName = "goods!saveEdit.do";
			this.adminUser = ManagerUtils.getAdminUser();
			if(StringUtils.isNotEmpty(ref_obj_id)){
				goods_id = ref_obj_id ;
			}
			is_edit =true;
			
			
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.tabs = JspPageTabs.getTabs("goods");
			catList = goodsCatManager.listAllChildren(0 + "");
			GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(goods_id);
			goodsView = editDTO.getGoods();
			if(StringUtil.isEmpty((String)goodsView.get("brand_id")))
				goodsView.put("brand_id", "0");
			String type_id=goodsView.get("type_id").toString();
			goodsType=goodsTypeManager.get(type_id);
			this.tagHtmlList = editDTO.getHtmlList();
			goodsView.put("store", store);//放入传入的库存
			
	//		if(!"T".equals(this.selfProdType)){
			PricePrivListReq req = new PricePrivListReq();
			req.setGoods_id(goods_id);
			PricePrivListResp resp = new PricePrivListResp();
			resp = memberPriceServ.loadPricePrivList(req);
			if(resp != null){
				this.lvList = resp.getPricePrivList();
			}
			goods = new Goods();
			//时间格式处理
			String arrival = null ;
			if(goodsView.get("arrival") != null && 
					goodsView.get("arrival") instanceof java.sql.Timestamp)
				arrival = ((java.sql.Timestamp)goodsView.get("arrival")).toString() ;
			else
				arrival = (String)goodsView.get("arrival") ;
			
			if(StringUtils.isNotEmpty(arrival)){
				if(arrival.length()<10){
					goodsView.put("arrival", arrival) ; 
				}else{
				goodsView.put("arrival", arrival.substring( 0, 10)) ; 
				}
			}
			
			String exp_date = null ;
			if(goodsView.get("exp_date") != null && 
					goodsView.get("exp_date") instanceof java.sql.Timestamp)
				exp_date = ((java.sql.Timestamp)goodsView.get("exp_date")).toString() ;
			else
				exp_date = (String)goodsView.get("exp_date") ;
			
			if(StringUtils.isNotEmpty(exp_date) ){
				goodsView.put("exp_date", exp_date.substring( 0, 10)) ; 
			}
//			act_code=511209065429, prod_code=99104646
			Map tag = goodsManager.getGoodsTagEcs(goodsView.get("goods_id").toString());
			goodsView.put("tag_id", tag.get("tag_id"));
			goodsView.put("tag_name", tag.get("tag_name"));
			colorList = goodsManager.listColor();
			BeanUtils.copyProperties(goods, goodsView);
			fieldList= this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
			for(GoodsField field:fieldList){
				String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
				field.setInputHtml(html);
			}
			
			//商品控制信息APPLY_JOIN
			//goods.audit_state == '00M' && goods.apply_join == 'T' 
	        String audit_state = goodsView.get("audit_state").toString();
	        int userFounder  =  adminUser.getFounder();
	        if(userFounder==4)
	        {
	        	this.join_suced = true;
	        }
		    if("F".equals(this.selfProdType)){
				DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
				this.codeList = dcPublicCache.getList("6666");
				this.controlTypeList = dcPublicCache.getList("6661");
				this.subControlTypeList = dcPublicCache.getList("6662");
				this.companyTypeList = dcPublicCache.getList("6663");
				this.goodsControlList = this.goodsManager.getControlById(this.goods_id);
				Map map = (Map) this.goodsControlList.get(0);
				this.control_type =map.get("control_type").toString() ;
				this.sub_control_type = map.get("sub_control_type").toString();
				this.control_value = map.get("control_value").toString();
		    }
		    this.cmutList = this.goodsManager.getGoodsRelCommunityList(this.goods_id);
		    this.countryList = this.goodsManager.getGoodsRelCountryList(this.goods_id);
		    this.staffIdList = this.goodsManager.getGoodsRelStaffList(this.goods_id);
		    this.custIdList = this.goodsManager.getGoodsRelCustList(this.goods_id);
		    this.developIdList = this.goodsManager.getGoodsRelDevelopRelaList(this.goods_id);
		    this.officeIdList = this.goodsManager.getGoodsRelOfficeRelaList(this.goods_id);
		    
			this.elementList = goodsManager.getElementInfo(this.goods_id);
		    if(!ListUtil.isEmpty(countryList)){
		    	Country country = countryList.get(0);
		    	String region_type = country.getRegion_type();
		    	goodsView.put("region_type", region_type);
		    }
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
		//lookups the activities of this goods
		if("view".equalsIgnoreCase(page_type))
				try
				{
					BaseJdbcDaoSupport dao=SpringContextHolder.getBean("baseDaoSupport");
					String sql="select d.name from es_goods a, es_pmt_goods b,  es_promotion c,  es_promotion_activity d where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=b.goods_id and b.pmt_id=c.pmt_id and c.pmta_id=d.id and d.enable = 1 and to_char(sysdate, 'yyyy-mm-dd HH24:MI:SS') between to_char(d.begin_time, 'yyyy-mm-dd HH24:MI:SS') and to_char(d.end_time, 'yyyy-mm-dd HH24:MI:SS') and a.goods_id = ?";
					List<Map<String, String>> resultList=dao.queryForList(sql, this.goods_id);
					List<String> result=new ArrayList<String>();
					for (int i = 0; i < resultList.size(); i++)
						result.add(resultList.get(i).values().iterator().next().trim());
					String 	activitynames=org.apache.commons.lang.StringUtils.join(result, ",  ");
					if(null==activitynames)
						activitynames="";
					this.goodsView.put("activity_names", activitynames.trim());
				}
				catch (Exception e)
				{
					//logger.info(e.getMessage());
					this.goodsView.put("activity_names", "");
				}
		
		return "goodsInputEcs";
	}
	
	/**
	 * 到货品查看页面
	 */
	@SuppressWarnings("unchecked")
	public String productView(){
		
		page_type="view";
		
		productEdit();

		return "product_input";
	}
	
	/**
	 * 到货品查看页面
	 */
	@SuppressWarnings("unchecked")
	public String goodView(){
		
		page_type="view";				
		
		goodsEditEcs();

		return "goodsInputEcs";
	}
	
	/**
	 * 商品比较
	 */
	@SuppressWarnings("unchecked")
	public String compareGoods(){
		String[] ids=serial_nos.split(",");
		this.orgList  = this.goodsManager.compareGoods(ids);
		goodsOld=(Map) orgList.get(1);
		goodsNew=(Map) orgList.get(0);
		goodsNewList=(List) goodsNew.get("list");
		goodsOldList=(List) goodsOld.get("list");
		goodsParamOldMap=(Map) orgList.get(3);
		goodsParamNewMap=(Map) orgList.get(2);
		return "goodsCompare";
	}
	
	/**
	 * 货品比较
	 */
	@SuppressWarnings("unchecked")
	public String compareProduct(){
		String[] ids=goods_ids.split(",");
		this.orgList  = this.goodsManager.compareProducts(ids);
		productsOld=(Map) orgList.get(1);
		productsNew=(Map) orgList.get(0);
		goodsParamOldMap=(Map) orgList.get(3);
		goodsParamNewMap=(Map) orgList.get(2);
		return "productsCompare";
	}
	
	/**
	 * 到商品查看页面
	 */
	@SuppressWarnings("unchecked")
	public String  view(){
		
		page_type="view";
		
		edit();
		
		return Action.INPUT;
	}
	
	/**
	 * 到商品查看页面
	 */
	public String  showGoodsMsg(){
		actionName = "";
		this.adminUser = ManagerUtils.getAdminUser();
		is_edit =true;
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		catList = goodsCatManager.listAllChildren(0 + "");
		GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(goods_id);
		goodsView = editDTO.getGoods();
		String type_id=goodsView.get("type_id").toString();
		goodsType=goodsTypeManager.get(type_id);
		this.tagHtmlList = editDTO.getHtmlList();
		goodsView.put("store", store);//放入传入的库存
		fieldList= this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
		for(GoodsField field:fieldList){
			String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
			field.setInputHtml(html);
		}
		return "showGoodsMsg";
	}
	
	/**
	 * 上下架处理
	 * @return
	 */
	public String marketEnable(){
		
		try{
			this.goodsManager.updateField("market_enable", this.market_enable, goods_id);
			this.insertCoQueue(goods_id, market_enable);
			this.showSuccessJson("更新上架状态成功");
		}catch(RuntimeException e){
			this.showErrorJson("更新上架状态失败");
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	/**
	 * 批量上下架处理
	 * @return
	 */
	public String batchMarketEnable(){
		try{
			String[] ids=goods_ids.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id=ids[i];
				this.goodsManager.updateField("market_enable", this.market_enable, id);
				this.insertCoQueue(goods_id, market_enable);
			}
			this.showSuccessJson("更新上架状态成功");
		}catch(RuntimeException e){
			this.showErrorJson("更新上架状态失败");
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	public void insertCoQueue(String goods_id,Integer market_enable){
		
//		List datas = goodsManager.getOrgByGoodsId("goods",goods_id);
//		String org_id_str = "";
//		if(datas!=null && datas.size()>0){
//			for(int i=0;i<datas.size();i++){
//				Map data = (Map) datas.get(i);
//				String org_id = (String) data.get("party_id");
//				org_id_str = org_id_str + org_id.trim();
//				if(i!=datas.size()-1){
//					org_id_str += ",";
//				}
//			}
//		}
		List datas = goodsManager.getOrgResultByGoodsId(goods_id);
		//查询每个lv2的org_id_belong的最新更新记录再次进行修改同步
		for(int i=0;i<datas.size();i++){
			Map date =(Map) datas.get(i);
			CoQueue addReq = new CoQueue();
			String batch_id = goodsManager.getSequence("S_ES_CO_BATCH_ID");
			addReq.setBatch_id(batch_id);
			addReq.setObject_id(goods_id);
			if(date.get("org_id_str")!=null){
				String org_id_str=date.get("org_id_str").toString();
				addReq.setOrg_id_str(org_id_str);
			}
			if(date.get("org_id_belong")!=null){
				String org_id_belong=date.get("org_id_belong").toString();
				addReq.setOrg_id_belong(org_id_belong);
			}
			addReq.setObject_type("SHANGPIN");
			addReq.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
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
			addReq.setOper_id(ManagerUtils.getUserId());
			
			coQueueManager.add(addReq);

		}
//		org_id_str=goodsManager.getResultStrByGoodsId(goods_id,"org_id_str");
//		org_id_belong=goodsManager.getResultStrByGoodsId(goods_id, "org_id_belong");
		}

	
	
	/**
	 * 商品入围申请,发起流程
	 * @return
	 */
	public String joinApply() {

		//this.goodsManager.joinApply(goodsApplyJoin);
		this.goodsManager.addTemp(goods);
		//商口控制保存
		String controlType = this.goodsControl.getControl_type();
	    this.goodsControl.setGoods_id(goods.getGoods_id());
		if("CO".equals(controlType) && !"".equals(this.codeStr)){
			  String[] codeList = this.codeStr.split(",");
			  String[] controlValList = this.controlValStr.split(",");
			  for(int i=0;i<codeList.length;i++){
				   this.goodsControl.setControl_lan_code(codeList[i]);
				   this.goodsControl.setControl_value(Double.parseDouble(controlValList[i]));
				   this.goodsManager.insertGoodsControl(goodsControl);
			  }
			  
		}else{
				 if("MO".equals(controlType))
					 this.goodsControl.setControl_value(Double.parseDouble(this.money_control_value));

			     if("PN".equals(controlType))
			    	 this.goodsControl.setControl_value(Double.parseDouble(this.qty_control_value));
			
				 this.goodsControl.setControl_lan_code(Consts.LAN_PROV);
				 this.goodsManager.insertGoodsControl(goodsControl);
			}
		this.goodsManager.joinApply(goods.getGoods_id());
		
		this.json = "{result:0}";
		 
		return WWAction.JSON_MESSAGE;
	}
	
	
	public String applyAuditInfo(){
		this.selfProdType = "F";
		this.is_edit = true;
		if(StringUtils.isNotEmpty(ref_obj_id)){
			this.goods_id = ref_obj_id;
			goodsView = this.goodsManager.qryGoodsTemp(ref_obj_id);
	    	DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    	this.codeList = dcPublicCache.getList("6666");
	    	this.controlTypeList = dcPublicCache.getList("6661");
	    	this.subControlTypeList = dcPublicCache.getList("6662");
	    	this.companyTypeList = dcPublicCache.getList("6663");
	    	this.goodsControlList = this.goodsManager.getControlById(this.goods_id);
	    	Map map = (Map) this.goodsControlList.get(0);
	    	this.control_type =map.get("control_type").toString() ;
	    	this.sub_control_type = map.get("sub_control_type").toString();
	    	this.control_value = map.get("control_value").toString();
			
		}else{//第一次从goods表当中取值
			edit();
		}

		return "apply_Audit_info";
	}
	
	
	public String saveGoodsTemp(){
		
		this.goodsManager.updateTemp(goods);
		
		if("F".equals(this.selfProdType)&&this.join_suced==false){
			this.goodsManager.delAllControlByAgtId(goods.getGoods_id());
			String controlType = this.goodsControl.getControl_type();
		    this.goodsControl.setGoods_id(goods.getGoods_id());
			if("CO".equals(controlType)){
				  String[] codeList = this.codeStr.split(",");
				  String[] controlValList = this.controlValStr.split(",");
				  for(int i=0;i<codeList.length;i++){
					   this.goodsControl.setControl_lan_code(codeList[i]);
					   this.goodsControl.setControl_value(Double.parseDouble(controlValList[i]));
					   this.goodsManager.insertGoodsControl(goodsControl);
				  }
				  
				}else{
					 if("MO".equals(controlType))
						 this.goodsControl.setControl_value(Double.parseDouble(this.money_control_value));

				     if("PN".equals(controlType))
				    	 this.goodsControl.setControl_value(Double.parseDouble(this.qty_control_value));
				
					 this.goodsControl.setControl_lan_code(Consts.LAN_PROV);
					 this.goodsManager.insertGoodsControl(goodsControl);
				}
			  }
		
		this.json = "{result:0}";
		 
		return WWAction.JSON_MESSAGE;
	}
	/**
	 * 商品提价申请
	 * @return
	 */
	public String appreciateApply(){
		try {
			this.goodsManager.appreciateApply(this.goods_id, goodsLvPriceList);
			json = "{'result':1,'message':'修改成功！'}";
		} catch (Exception e) {
			json = "{'result':0,'message':'修改失败！'}";
			e.printStackTrace();
		}			
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 保存商品添加
	 * @return
	 */
	public String saveAdd() {
		try {
			if(!StringUtil.isEmpty(goods.getProd_code())){
			goods.setProd_code(goods.getProd_code().trim());}
			if(!StringUtil.isEmpty(goods.getAct_code())){
			goods.setAct_code(goods.getAct_code().trim());}
			
		
			
			saveGoodsBus();
			setGoodsValue(goods);
			
			goodsManager.add(goods,goodsRules,goodsPackage,communityCodes,countyIds,custIds,staffIds,developIds,officeIds,s_scheme_id,s_element_type);
			msg.setUrl("goods!productList.do");
			msg.addResult("url", "goods!productList.do");
			msg.setMessage("操作成功");
			msg.addResult(MESSAGE,"操作成功");
			if(null == goods.getType()){
				msg.setUrl("goods!list.do");
				msg.addResult(MESSAGE,"操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
				msg.setMessage("操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
			}
			else if(goods.getType().equalsIgnoreCase("goods")){
				msg.setUrl("goods!goodsList.do");
				msg.addResult(MESSAGE,"操作成功");
				msg.setMessage("操作成功");
			}			
			msg.setSuccess(true);
			msg.setResult(1);
		} catch (NameRepetirException e) {
			e.printStackTrace();
			msg.addResult(MESSAGE, e.getMessage());
			msg.setMessage(e.getMessage());
			msg.setSuccess(false);
			msg.setResult(0);
		}catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE,"操作失败");
			msg.setMessage("操作失败");
			msg.setSuccess(false);
			msg.setResult(0);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	private void setGoodsValue(Goods goods2) {
		adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		String user_no =adminUser.getUserid();
		String user_name = adminUser.getUsername();
		String userId =adminUser.getParuserid();
		
		if (Consts.CURR_FOUNDER0 == adminUser.getFounder().intValue() || ManagerUtils.isAdminUser() ) {
			userId = "-1" ;
			goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
		}else{
			int value = adminUser.getFounder().intValue();
			if(Consts.CURR_FOUNDER4==value) //供货商
				userId =adminUser.getUserid();
			else if(Consts.CURR_FOUNDER5 == value) //供货商员工
				userId = adminUser.getParuserid();
			
			goods.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
		}
		
		goods.setCreate_time(DBTUtil.getDBCurrentTime());
		goods.setStaff_no(userId);
		goods.setCreator_user(user_no);
		goods.setGoods_type("normal");
		goods.setLast_modify(DBTUtil.current());
		//goods.setApply_username(user_name);
		if(goods.getSearch_key()!=null){
			goods.setSearch_key("_"+user_name+"_"+goods.getSearch_key());
		}else{
			goods.setSearch_key("_"+user_name+"_");
		}
		
		if(goods.getType_id()!=null){
			goodsType=goodsTypeManager.get(goods.getType_id());
			goods.setType_code(goodsType.getType_code());
		}
		
		goods.setCat_id(goods.getCat_id().split(",")[0]);
	}
	
	private void setGoodsValue2(Goods goods2) {
		adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		String user_no =adminUser.getUserid();
		String user_name = adminUser.getUsername();
		String userId =adminUser.getParuserid();
		
		if (Consts.CURR_FOUNDER0 == adminUser.getFounder().intValue() || ManagerUtils.isAdminUser() ) {
			userId = "-1" ;
			goods2.setAudit_state(Consts.GOODS_AUDIT_SUC);
		}else{
			int value = adminUser.getFounder().intValue();
			if(Consts.CURR_FOUNDER4==value) //供货商
				userId =adminUser.getUserid();
			else if(Consts.CURR_FOUNDER5 == value) //供货商员工
				userId = adminUser.getParuserid();
			
			goods2.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
		}
		
		goods2.setCreate_time(DBTUtil.getDBCurrentTime());
		goods2.setStaff_no(userId);
		goods2.setCreator_user(user_no);
		goods2.setGoods_type("normal");
		goods2.setLast_modify(DBTUtil.current());
		if(goods2.getSearch_key()!=null){
			goods2.setSearch_key("_"+user_name+"_"+goods2.getSearch_key());
		}else{
			goods2.setSearch_key("_"+user_name+"_");
		}
		
		if(goods2.getType_id()!=null){
			goodsType=goodsTypeManager.get(goods2.getType_id());
			goods2.setType_code(goodsType.getType_code());
		}
		
		goods2.setCat_id(goods2.getCat_id().split(",")[0]);
	}
	

	public void saveGoodsArea(){
		//设置地域参数
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		request.getSession().setAttribute("goodsAreaParam", goodsAreaParam);
	}
	
	
	public void saveGoodsBus(){
		//设置外系统扩展参数
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		request.getSession().setAttribute("goodsBusiness", goodsBusiness);
	}
	
	
	/**
	 * 商品总数--首页使用
	 * */
	public String allGoodsNum(){
		try{	
			int allGoodsNum = this.goodsManager.allGoodsNum();
			int allGoodsAuditNum = this.goodsManager.allGoodsAuditNum();
			int allApplyNum=this.goodsManager.listGoodsApply().size();
			int applySuccNum=this.goodsManager.applySuccNum();
			int trashNum=this.goodsManager.getTrashNum();
			Map countMap = new HashMap();
		    countMap.put("allGoodsNum",allGoodsNum+"");
		    countMap.put("allGoodsAuditNum", allGoodsAuditNum+"");
		    countMap.put("allApplyNum", allApplyNum+"");
		    countMap.put("applySuccNum", applySuccNum+"");
		    countMap.put("trashNum", trashNum+"");
		    String jsonStr = ManagerUtils.toJsonString(countMap);
			
		    this.json = "{result:1,"+jsonStr+"}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";

		}
		          return WWAction.JSON_MESSAGE;
		
	}
	
	public String saveEdit(){
		try{
			saveGoodsBus();
			
			//修改商品，检查发布区域变化？新的区域待审核：旧的只改变商品参数
			AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
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
			
			//修改前添加操作日志
			Map logParam=new HashMap();
			logParam.put("oper_name", adminUser.getRealname());
			logParam.put("goods_id", goods.getGoods_id());
			logParam.put("oper_no", adminUser.getUserid());
			//修改原有sql,把原来goods_package的p_code跟sn保存---zengxianlian
			Map goodsPackageMap = goodsManager.getPCodeAndSnByGoodsId(goods.getGoods_id());
			logParam.put("p1", goodsPackageMap.get("p_code"));
			logParam.put("p2", goodsPackageMap.get("sn"));
			goodsManager.insertOperLog(logParam);
			
//			goods.setCust_id(custIds == null ?"":custIds);
//			goods.setStaff_id(staffIds == null ? "":staffIds);
//			goods.setDevelop_rela_id(developIds == null ? "":developIds);
//			goods.setOffice_rela_id(officeIds == null ? "":officeIds); 
//			if(!StringUtil.isEmpty(custIds)){
//				goods.setCust_id(custIds);}
//			if(!StringUtil.isEmpty(staffIds)){
//				goods.setStaff_id(staffIds);}
//			if(!StringUtil.isEmpty(developIds)){
//				goods.setDevelop_rela_id(developIds);}
//			if(!StringUtil.isEmpty(officeIds)){
//				goods.setOffice_rela_id(officeIds);}
			
			
			goodsManager.edit(goods,goodsRules,this.communityCodes,this.countyIds,this.custIds,this.staffIds,this.developIds,this.officeIds,this.s_scheme_id,this.s_element_type);
			
			//修改后添加操作日志
			logParam.put("flag", "no");
			//修改原有sql,保存页面所传的p_code跟sn---zengxianlian
			logParam.put("p1", goods.getAct_code());
			logParam.put("p2", goods.getProd_code());
			goodsManager.insertOperLog(logParam);
			
			
			if("F".equals(this.selfProdType)&&this.join_suced==false){
			this.goodsManager.delAllControlByAgtId(goods.getGoods_id());
			String controlType = this.goodsControl.getControl_type();
		    this.goodsControl.setGoods_id(goods.getGoods_id());
			if("CO".equals(controlType)){
				  String[] codeList = this.codeStr.split(",");
				  String[] controlValList = this.controlValStr.split(",");
				  for(int i=0;i<codeList.length;i++){
					   this.goodsControl.setControl_lan_code(codeList[i]);
					   this.goodsControl.setControl_value(Double.parseDouble(controlValList[i]));
					   this.goodsManager.insertGoodsControl(goodsControl);
				  }
				  
				}else{
					 if("MO".equals(controlType))
						 this.goodsControl.setControl_value(Double.parseDouble(this.money_control_value));

				     if("PN".equals(controlType))
				    	 this.goodsControl.setControl_value(Double.parseDouble(this.qty_control_value));
				
					 this.goodsControl.setControl_lan_code(Consts.LAN_PROV);
					 this.goodsManager.insertGoodsControl(goodsControl);
				}
			  }
			
			
			msg.setUrl("goods!productList.do");
			msg.addResult("url", "goods!productList.do");
			if(null == goods.getType()){
				msg.setUrl("goods!list.do");
			}
			else if(goods.getType().equalsIgnoreCase("goods")){
				msg.setUrl("goods!goodsList.do");
			}
			String goods_id = "goods".equalsIgnoreCase(goods.getType())?goods.getGoods_id():goods.getProduct_id();
			String oper_id = ManagerUtils.getUserId();
			this.goodsManager.insertCoQueue(goods.getType(),oper_id,goods_id,2);
			this.json = "{result:1,message:'操作成功'}";
			//this.msgs.add("商品修改成功");
			//this.urls.put("商品列表", "goods!list.do");
			//cacheUtil.refreshGoodsCache();
			msg.addResult(MESSAGE,"操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
			msg.setMessage("操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
			msg.setSuccess(true);
			msg.setResult(1);
			
			//修改成功后,同时刷新订单商品的缓存---zengxianlian
			updateGoodsCache(goods_id);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE,"操作失败");
			msg.setMessage("操作失败");
			msg.setSuccess(false);
			msg.setResult(0);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	public String getPlanTitleByGoodsId(){
		String planTitle = "";
		String packageLimit = "";//合约期
		ProductsListReq req = new ProductsListReq();
		req.setGoods_id(this.goods_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ProductsListResp resp = client.execute(req, ProductsListResp.class);
		if(ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
			List<Goods> productList = resp.getProducts();
			if(productList !=null){
				for(Goods product:productList){
					if(SpecConsts.TYPE_ID_10002.equals(product.getType_id())){
						planTitle = product.getName();
					}
					if(SpecConsts.TYPE_ID_10001.equals(product.getType_id())){
						Map specs = SpecUtils.getProductSpecMap(product);
						packageLimit = EcsOrderConsts.EMPTY_STR_VALUE;
						if(specs!=null && !specs.isEmpty())
							packageLimit = specs.get(SpecConsts.PACKAGE_LIMIT)==null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get(SpecConsts.PACKAGE_LIMIT).toString();
					}
				}
			}
		}
		String isAttached = CommonDataFactory.getInstance().getGoodSpec(null, this.goods_id, SpecConsts.IS_ATTACHED);
		this.json="{result:1,planTitle:'"+planTitle+"',packageLimit:'"+packageLimit+"',isAttached:'"+isAttached+"'}";
		return WWAction.JSON_MESSAGE;
	}
	
	
	//关联CRM 销售品ID 
	public String findCrmOfferId(){
		logger.info(cardType);
		if("CLOUD".equals(cardType)){//云卡
			this.webpage=goodsManager.findCrmOfferId(this.getPage(), this.getPageSize(),offer_id,offer_name);
			return "findCrmOfferId_cloud";
		}else{//合约卡CONTRACT
			this.webpage=goodsManager.findCrmOfferId2(this.getPage(), this.getPageSize(),offer_id,offer_name);
			return "findCrmOfferId_contract";
		}
		
	}
	
	public String updateMarketEnable(){
		try{
			this.goodsManager.updateField("market_enable", 1, goods_id);
			this.showSuccessJson("更新上架状态成功");
		}catch(RuntimeException e){
			this.showErrorJson("更新上架状态失败");
		}
		return WWAction.JSON_MESSAGE;
	}
	//商品申请页面配送地址添加
	public String parAddrAdd(){
		try {
			if(apply_desc!=null){
				apply_desc=URLDecoder.decode(apply_desc,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "parAddrAdd";
	}
	//获取框架列表
	
	/**
	 * 点击打开调整价格页面
	 * @return
	 */
	public String modPrice(){
		
		
		return "modPricePage" ;
	}
	
	public String goodsAgreementList() {
		 this.webpage = this.agreeManager.listAgreement(this.agreement_name, this.getPage(),this.getPageSize());
		 return "goodsAgreementList";
	}
	/**
	 * 
	 * 保存调价信息
	 * @return
	 */
	public String saveModPrice(){
		
		this.showSuccessJson("调价申请中");
		return WWAction.JSON_MESSAGE;
	}
	
	public String batchUpload(){
		return "batchUpload";
	}
	/*
	 * 批量添加商品信息
	 */
	public String batchAddDetails(){
		if (fileName != null) {
			if (FileBaseUtil.isAllowUpXls(fileNameFileName)) {
		
			} else {
				this.json="不允许上传的文件格式，请上传xlsx,xls格式文件。";
				return WWAction.RESULT_MESSAGE;
			}
			
//			brand.setFile(this.logo);
//			brand.setFileFileName(this.logoFileName);
		}else {
			this.json="请上传商品";
			return WWAction.RESULT_MESSAGE;
		}
		
		String path=fileName.getPath();
		XlsPaser paser=XlsPaser.getInstance();
		GoodsUsers goodsUser= new GoodsUsers();
		GoodsRules goodsRules= new GoodsRules();
		try{
			List<Goods> list=paser.paseXls(path);
			String role_type= null;
			String normal_price=null ;
			String silver_price =null;
			String gold_price =null;
//			GoodsUsers goodsUser=new GoodsUsers();
			String detail=null;
			
			for (Goods gs:list) {
				detail=gs.getJson();
				gs.setJson(null);
				role_type=gs.getRole_type();
				normal_price=gs.getNormal_price();
				silver_price =gs.getSilver_price();
				gold_price = gs.getGold_price();
				gs.setRole_type(null);
				gs.setNormal_price(null);
				gs.setSilver_price(null);
				gs.setGold_price(null);
				goods=gs;
				saveAdd();
				this.goodsManager.addGoodsPrice(gs,role_type,normal_price,silver_price,gold_price);
				this.goodsManager.editGoods(detail,gs.getGoods_id());
				
				//设置商品工号   默认选择订购服务类型，受理和确认选择否
				
//				assembleGoodsUser(goodsUser);
				addGoodsUser(goodsUser,gs,goodsRules);
			}
			this.json = "商品添加成功";
//			this.msgs.add("商品添加成功");
		}catch(Exception e ){
			e.printStackTrace();
			this.json = "商品添加失败";
//			this.msgs.add("商品添加失败");
			
		}
//		return this.MESSAGE;
		return WWAction.RESULT_MESSAGE;
		
	}
	public void addGoodsUser(GoodsUsers goodsUser,Goods gs,GoodsRules goodsRules) {
        try {
        	IGoodsUserManager goodsUserManager=SpringContextHolder.getBean("goodsUserManager");
        	//默认设置GoodsUsers
        	goodsUser.setAccept_group_id("");
        	goodsUser.setAccept_user_id("1");
        	goodsUser.setQuery_group_id("");
        	goodsUser.setQuery_user_id("1");
        	goodsUser.setPay_group_id("");
        	goodsUser.setPay_user_id("1");
        	goodsUser.setShip_group_id("");
        	goodsUser.setShip_user_id("1");
        	goodsUser.setQr_group_id("");
        	goodsUser.setQr_user_id("1");
        	goodsUser.setService_code("3");
        	goodsUser.setGoodsIds(new String[]{gs.getGoods_id()});
            goodsUserManager.saveGoodsServiceRule(goodsUser,null);
            //默认设置goodsRules
            
            goodsRules.setInsure("no");
            goodsRules.setAccept("no");
            goodsRules.setCreate_order("yes");
            goodsRules.setDelivery("yes");
            goodsRules.setPay("yes");
            goodsRules.setRule("yes");
            goodsUserManager.add(goodsUser, goodsRules);
            
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
        }
    }
	
	public void assembleGoodsUser(GoodsUsers goodsUser){
		IGoodsUserManager goodsUserManager=SpringContextHolder.getBean("goodsUserManager");
		
		
	}
	
	public String convertParams(){
		try {
			goodsManager.convertParams(type);
			
		} catch (Exception e) {

		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 去商品导入页面
	 * @return
	 */
	public String importGoodsPage(){
		return "goods_import";
	}
	
	private File file;
	public String importGoods(){
		try{
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			String rtnmsg = goodsManager.importExcel(file, params);
			if ("EXISTS_FILE".equalsIgnoreCase(rtnmsg)) {
				this.msgs.add("该文件【"+ fileNameFileName +"】已经导入，不能重复导入！");
				this.urls.put("商品导入日志列表", "goods!goodsImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			if ("0".equals(rtnmsg)) {
				this.msgs.add("没有商品数据，请检查导入文件内容！");
				this.urls.put("商品导入日志列表", "goods!goodsImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			String[] rtnArr = rtnmsg.split("#");
			this.msgs.add("此次导入的商品总数是" + rtnArr[0] + "个。"
					+ " 其中，成功导入数为" + rtnArr[1] + "个，失败数为" + rtnArr[2] + "个 ，批次号为"+rtnArr[3]);
			this.urls.put("商品导入日志列表", "goods!goodsImportLogsECS.do");
			
		}catch(Exception e){
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.msgs.add("商品导入失败");
			this.urls.put("商品导入日志列表", "goods!goodsImportLogsECS.do");
		}
		return WWAction.MESSAGE;
	}
	
	
	
	public String dowloadBatchImportTemplate(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/").replaceAll("\\\\", "/");
		if(path.endsWith("\\") || path.endsWith("/")){
			path+="shop/admin/goods/";
		}else{
			path+="/shop/admin/goods/";
		}
		logger.info("模板存放路径***********************"+path);
		String fileName = null;
		if("goods".equals(type)){
			fileName = "商品批量导入模板.xls";
		}
		else{
			fileName = "手机货品导入模板.xls";
		}
		String fileType = "xls";
		
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			DownLoadUtil.downLoadFile(path+fileName,response,fileName,fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getContractmachine(){
		List<Goods> list = goodsManager.getContractmachine(type_id,terminal_pid, contract_pid, offerPids);
		
    	this.json = "{'goodsList':[";
    	for(int i=0;i<list.size();i++){
    		Goods goods = list.get(i);
    		String goods_id = goods.getGoods_id();
    		String stype_id = goods.getStype_id();
    		List<GoodsRel> relList = goodsManager.getGoodsRelByGoodsId(goods_id);
    		if(i>0) this.json += ",";
    		this.json += "{'goods_id':'"+goods_id+"','stype_id':'"+stype_id+"'";
    		this.json += ",'goods_rels':[";
    		for(int j=0;j<relList.size();j++){
    			GoodsRel rel =  relList.get(j);
    			String z_goods_id = rel.getZ_goods_id();
    			String product_id = rel.getProduct_id();
    		    String  rel_type =  rel.getRel_type();
    		    String  rel_code = rel.getRel_code(); 
    		    
    		    product_id = product_id==null?"":product_id;
    		    rel_type = rel_type==null?"":rel_type;
    		    rel_code = rel_code==null?"":rel_code;
    		    
    		    if(j>0) this.json += ",";		
    			this.json += "{'z_goods_id':'"+z_goods_id+"','product_id':'"+product_id+"','rel_type':'"+rel_type+"'," +
    					"'rel_code':'"+rel_code+"'}";
    		}
    		this.json += "]}";
    	}
    	this.json += "]}";
     	return  WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 批量修改合约机
	 * @return
	 */
	public String editContract(){

		try{
			Goods goods = goodsManager.getGoodsById(goods_id);
			if(goods != null){
				if(price != null){
					goods.setPrice(price);
				}
				if(StringUtils.isNotBlank(cat_id)){
					goods.setCat_id(cat_id);
				}
				
				goodsManager.edit(goods,new GoodsRules(),this.communityCodes,this.countyIds,this.custIds,this.staffIds,this.developIds,this.officeIds,this.s_scheme_id,this.s_element_type);
				insertCoQueue(goods.getGoods_id(),2);
			}

			this.json = "{result:1,message:'修改成功'}";

		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:0,message:'修改失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 商品货品同步日志
	 * @return
	 */
	public String synchLogs(){
		listFormActionVal = "goods!synchLogs.do?searchType=clickBtn";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		HttpServletRequest request =ThreadContextHolder.getHttpRequest();
		String searchType = request.getParameter("searchType");
		if(StringUtils.isEmpty(searchType)){//页面初始化加载，默认查询当天同步失败日志
			params.put("start_date", df.format(new Date()));
			params.put("end_date", df.format(new Date()));
			params.put("status", "XYSB");
		}
		
		this.webpage = this.goodsManager.searchSynchLogs(params, this.getPage(), this.getPageSize());
		return "synch_logs";
	}
	
	public String publishAgain(){
		listFormActionVal = "goods!synchLogs.do";
		String object_type = Const.getStrValue(params, "object_type");
		int count = goodsManager.publishAgain(params);
		if(count>0){
			msg.addResult("url", "goods!synchLogs.do");
			if("1".equals(object_type)){
				msg.addResult(MESSAGE,"发布失败的商品已成功重发");
			}
			else{
				msg.addResult(MESSAGE,"发布失败的货品已成功重发");
			}
			msg.setSuccess(true);
			msg.setResult(0);
		}
		else{
			if("1".equals(object_type)){
				msg.addResult(MESSAGE,"未找到可以重发的商品");
			}
			else{
				msg.addResult(MESSAGE,"未找到可以重发的货品");
			}
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	
	private List typeList;
	public List getTypeList() {
		return typeList;
	}
	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public String goProductPkgPage(){
		return "product_package";
	}
	
	public String searchProductPkgECS(){
		try{
			List<GoodsType> types = goodsTypeManager.getGoodsTypeList();
			this.typeList = new ArrayList();
			for(GoodsType type : types){
				if(Consts.ECS_QUERY_TYPE_GOOD.equals(type.getType())){
					this.typeList.add(type);
				}
			}
			String model_code = URLDecoder.decode(Const.getStrValue(params, "model_code"),"UTF-8");
			String name = URLDecoder.decode(Const.getStrValue(params, "name"),"UTF-8");
			params.put("model_code", model_code);
			params.put("name", name);
			String type_id = Const.getStrValue(params, "type_id");
			this.lanList = goodsTypeManager.listByTypeId(type_id);//获取品牌
			this.webpage = goodsManager.searchProductPkgECS(params, this.getPage(), this.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "product_package_select";
	}
	
	public String queryProductPkgECS(){
		try{
			List<GoodsType> types = goodsTypeManager.getGoodsTypeList();
			this.typeList = new ArrayList();
			for(GoodsType type : types){
				if(Consts.ECS_QUERY_TYPE_GOOD.equals(type.getType())){
					this.typeList.add(type);
				}
			}
			String model_code = URLDecoder.decode(Const.getStrValue(params, "model_code"),"UTF-8");
			String name = URLDecoder.decode(Const.getStrValue(params, "name"),"UTF-8");
			params.put("model_code", model_code);
			params.put("name", name);
			String type_id = Const.getStrValue(params, "type_id");
			this.lanList = goodsTypeManager.listByTypeId(type_id);//获取品牌
			this.webpage = goodsManager.searchProductPkgECS(params, this.getPage(), this.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "product_package_show";
	}
	
	public String listColorByModelCode(){
		this.colorList = goodsManager.listColorByModelCode(params);
		return "color_list";
	}

	public String searchPkgGoodsECS(){
		this.webpage = goodsManager.searchPkgGoodsECS(params, this.getPage(), this.getPageSize());
		return "product_package";
	}
	
	/**
	 * 停用货品包下的商品
	 * @return
	 */
	public String updatePkgGoodsStatusECS(){
		try{
			String relation_id = Const.getStrValue(params, "relation_id");
			goodsManager.updatePkgGoodsStatusECS(relation_id);
			this.json = "{result:1,message:'操作成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:0,message:'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 规则列表测试方法，(拷贝货品列表)
	 * @return
	 */
	public String listAttr(){
		estados  = productoM.getEstados();
		
		this.adminUser=ManagerUtils.getAdminUser();
		
		if (StringUtil.isEmpty(this.params.get("org"))) {
			this.setOrg_ids("");
		}
		
		setQueryParams();
		params.put("type", "product");
		this.webpage = goodsManager.searchProductsECS(params,getPage(),getPageSize());

		List<Goods> list = this.webpage.getResult();
		
		return "test_list";
		
	}
	
	//转兑包导入页面
	public String zdbImportLogsEcs(){
		Map params= new HashMap();
		if(!StringUtils.isEmpty(this.batch_id)){
			params.put("batch_id", this.batch_id);
		}
		if(!StringUtils.isEmpty(this.zdb_name)){
			params.put("zdb_name", this.zdb_name);
		}
		if(!StringUtils.isEmpty(this.start_date)){
			params.put("start_date", this.start_date);
		}
		if(!StringUtils.isEmpty(this.end_date)){
			params.put("end_date", this.end_date);
		}
		if(!StringUtils.isEmpty(this.deal_flag)){
			params.put("deal_flag", this.deal_flag);
		}
		if(!StringUtils.isEmpty(this.deal_type)){
			params.put("deal_type", this.deal_type);
		}
		this.webpage=this.goodsManager.queryZdbLogsEcs(params,this.getPage(),this.getPageSize());
		return "zdb_import_logs";
	}
	
	public void exportZdbs(){
		Map params= new HashMap();
		if(!StringUtils.isEmpty(this.zdb_name)){
			params.put("zdb_name", this.zdb_name);
		}
		if(!StringUtils.isEmpty(this.start_date)){
			params.put("start_date", this.start_date);
		}
		if(!StringUtils.isEmpty(this.end_date)){
			params.put("end_date", this.end_date);
		}
		List list;
		list=goodsManager.queryZdbsExport(params);
		String[] title=new String[]{"转兑包名称","SKU","品牌","创建时间","转兑包类型","转兑包网别","bss_code","调价额度"};
		
		String[] content = { "name","sku","brand_name","create_time", "zdb_type", "zdb_gen","bss_code", "zdb_price"};
		
		
		String fileTitle = "转兑包导出";
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		}
	}
	
	//转兑包批量导入
	public String importzdbs(){
		try {
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			Map result = this.goodsManager.importZdbs(file,fileNameFileName,"import");
			if(!StringUtils.isEmpty(Const.getStrValue(result, "EXISTS_FILE"))){
				this.msgs.add("该文件【"+ fileNameFileName +"】已经存在，不能重复导入！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			if(!StringUtils.isEmpty(Const.getStrValue(result, "NO_DATA"))){
				this.msgs.add("没有转兑包数据，请检查导入文件内容！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			Integer successCount = (Integer) result.get("successCount");
			Integer failureCount = (Integer) result.get("failureCount");
			Integer totalCount = (Integer) result.get("totalCount");
			String batch_id = (String) result.get("batch_id");
			this.msgs.add("此次导入的转兑包总数是" + totalCount + "个。"
					+ " 其中，成功导入数为" + successCount + "个，失败数为" + failureCount + "个 ，批次号为"+batch_id);
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("转兑包导入失败"+e.getMessage());
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		}
		this.importZdbToProduct("import");
		return WWAction.MESSAGE;
	}
	
	//转兑包批量修改
	public String editzdbs(){
		try {
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			Map result = this.goodsManager.importZdbs(file,fileNameFileName,"edit");
			if(!StringUtils.isEmpty(Const.getStrValue(result, "EXISTS_FILE"))){
				this.msgs.add("该文件【"+ fileNameFileName +"】已经存在，不能重复修改！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			if(!StringUtils.isEmpty(Const.getStrValue(result, "NO_DATA"))){
				this.msgs.add("没有转兑包数据，请检查文件内容！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			Integer successCount = (Integer) result.get("successCount");
			Integer failureCount = (Integer) result.get("failureCount");
			Integer totalCount = (Integer) result.get("totalCount");
			String batch_id = (String) result.get("batch_id");
			this.msgs.add("此次的转兑包总数是" + totalCount + "个。"
					+ " 其中，成功修改数为" + successCount + "个，失败数为" + failureCount + "个 ，批次号为"+batch_id);
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("转兑包修改失败"+e.getMessage());
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		}
		this.importZdbToProduct("edit");
		return WWAction.MESSAGE;
	}
	
	public String deletezdbs(){
		try {
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			Map result = this.goodsManager.importZdbs(file,fileNameFileName,"delete");
			if(!StringUtils.isEmpty(Const.getStrValue(result, "EXISTS_FILE"))){
				this.msgs.add("该文件【"+ fileNameFileName +"】已经存在，不能重复删除！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			if(!StringUtils.isEmpty(Const.getStrValue(result, "NO_DATA"))){
				this.msgs.add("没有转兑包数据，请检查文件内容！");
				this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
				return WWAction.MESSAGE;
			}
			Integer successCount = (Integer) result.get("successCount");
			Integer failureCount = (Integer) result.get("failureCount");
			Integer totalCount = (Integer) result.get("totalCount");
			String batch_id = (String) result.get("batch_id");
			this.msgs.add("此次的转兑包总数是" + totalCount + "个。"
					+ " 其中，成功回收数为" + successCount + "个，失败数为" + failureCount + "个 ，批次号为"+batch_id);
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("转兑包删除失败"+e.getMessage());
			this.urls.put("转兑包导入日志列表", "goods!zdbImportLogsEcs.do");
		}
		this.importZdbToProduct("delete");
		return WWAction.MESSAGE;
	}
	
	private void importZdbToProduct(String act) {
		// TODO 移动转兑包入商品货品表
		Goods goods = new Goods();
		goods.setType_id("10010");
		goods.setCat_id("691000000");
		goods.setBrand_id("2014032710061");
		goods.setModel_code("WXSWKT");
		goods.setType("product");
		setGoodsValue2(goods);
		this.goodsManager.importZdbToProduct(goods,act);
	}
	
	/**
	 * @author zengxianlian
	 * 商品批量发布
	 */
	public void goodsBatchPublishByChoice(){
        String gids = this.params.get("goodsIds");
        String oids = this.params.get("orgIds");
        Map<String,String> params = new HashMap<String,String>(0);
        String[] goodsIds = gids.split(",");
        for(String id : goodsIds){
        	params.put(id, oids);
        }
        params = goodsManager.checkPublishGoods(params);
        if(params.size()<1){
        	String message = goodsManager.goodsBatchPublishByChoice(gids, oids.split("-")[0]);
			//this.json = "{\"result\":\"0\",\"message\":\""+message+"\"}";
			//render(json,"text/x-json;charset=UTF-8");
			render(message,"text/html;charset=UTF-8");
		}else{
			StringBuffer sb = new StringBuffer();
			for(String key : params.keySet()){
				sb.append(params.get(key));
			}
			//this.json = "{\"result\":\"0\",\"message\":\"1111\"}";
			//render(json,"text/x-json;charset=UTF-8");
			render(sb.toString(),"text/html;charset=UTF-8");
		}
	}
	
	/**
	 * @author zengxianlian
	 * 货品批量发布
	 */
	public void productsBatchPublishByChoice(){
        String goodsIds = this.params.get("goodsIds");
        String orgIds = this.params.get("orgIds");
		String message = goodsManager.productsBatchPublishByChoice(goodsIds, orgIds.split("-")[0]);
		render("发布成功!","text/html;charset=UTF-8");
	}
	
	/**
	 * @author zengxianlian
	 * 新增货品前检查唯一性
	 */
	public void checkSaveAdd(){
		//使用sn字段作为唯一性接收参数
		List<Goods> goodsList = goodsManager.checkSaveAdd(typeCode, sn);
		if(goodsList.size()>0){
			String msg = "";
			if(Consts.GOODS_TYPE_TERMINAL.equals(typeCode)){
				msg="{\"result\":\"0\",\"message\":\"已存在相同条形码的货品!sku为"+goodsList.get(0).getSku()+"\"}";
			}else if(Consts.GOODS_TYPE_OFFER.equals(typeCode)){
				msg="{\"result\":\"0\",\"message\":\"已存在相同bss编码的货品!sku为"+goodsList.get(0).getSku()+"\"}";
			}
			render(msg,"text/html;charset=UTF-8");
		}else{
			render("{\"result\":\"1\"}","text/html;charset=UTF-8");
		}
	}
	
	/**
	 * @author zengxianlian
	 * 新增货品前检查唯一性
	 */
	public void checkGoodsSaveAdd(){
		//使用sn字段作为唯一性接收参数	typeCode作废，所有商品校验
		Map params = new HashMap();
		params.put("typeCode", typeCode);
		params.put("sn", sn);
		params.put("cat_id", cat_id);
		boolean flag = true;
		//检查物料号是否重复
		if(StringUtils.isNotEmpty(matnr)){
			if(goodsManager.checkMatnrExists(matnr)){
				//提示返回物料号已存在
				String msg = "";
				msg="{\"result\":\"0\",\"message\":\"物料号["+matnr+"]已成功关联了商品，不允许重复关联，请检查！\"}";
				render(msg,"text/html;charset=UTF-8");
				flag = false;
			}
		}
		
		if(flag){
			List<Goods> goodsList = goodsManager.checkGoodsSaveAdd(params);
			if(goodsList.size()>0){
				String msg = "";
				msg="{\"result\":\"0\",\"message\":\"已存在相同的商品!sku为"+goodsList.get(0).getSku()+"\"}";
//				if(Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(typeCode)){
//					msg="{\"result\":\"0\",\"message\":\"已存在相同的合约机商品!sku为"+goodsList.get(0).getSku()+"\"}";
//				}else if(Consts.GOODS_TYPE_OFFER.equals(typeCode)){
//					msg="{\"result\":\"0\",\"message\":\"已存在相同bss编码的货品!sku为"+goodsList.get(0).getSku()+"\"}";
//				}
				render(msg,"text/html;charset=UTF-8");
			}else{
				render("{\"result\":\"1\"}","text/html;charset=UTF-8");
			}
		}
	}
	
	/**
	 * @author zengxianlian
	 * 商品修改/导入需要更新订单中商品缓存
	 */
	private void updateGoodsCache(String goodsIds){
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
			msg = postHttpReq(jsonObject.toString(), url);
			logger.info("返回信息 : "+msg+"===============================");
		}
		if(!StringUtil.isEmpty(url2)){
			url = url2+"?reqId=sp-valid&reqType=sp_refresh_goods_cache";
			JSONObject jsonObject = JSONObject.fromObject(json);
			logger.info(jsonObject.toString());
			msg = postHttpReq(jsonObject.toString(), url);
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
	
	/**============zhengchuiliu=============**/
	//虚拟串号
	public String esTerminalList(){
		try {
			if(esTerminal == null){
				esTerminal = new EsTerminal();
			}
			if(sn!=null){
				sn = URLDecoder.decode(sn,"utf-8");
			}
			if(terminal_no!=null){
				terminal_no = URLDecoder.decode(terminal_no,"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.webpage=this.goodsManager.getEsTerminalList(this.getPage(), this.getPageSize(),sn,terminal_no);
		return "es_terminal_list";
	}
	
	//保存或更新记录
	public String saveOrUpdateEsTerminal(){
		try {
			this.goodsManager.saveOrUpdateEsTerminal(esTerminal, action);
			this.json = "{result:true}";
		} catch (Exception e) {
			this.json = "{result:false}";
			e.printStackTrace();
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	//获取虚拟串号详情
	public String esTerminalDetail(){
		String sn = "";
		if(esTerminal != null){
			sn = esTerminal.getSn();
			esTerminal = this.goodsManager.getEsTerminalDetail(sn);
		}
		
		return "es_terminal_detail";
	}
	
	//删除串号
	public String deleteEsTerminal() {
		try {
			sn = sn.substring(0, sn.length()-3);
			
			this.goodsManager.deleteEsTerminal(sn);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	//虚拟串号导入
	public String importEsTerminal(){
		try{
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			params.put("fileName", fileNameFileName);
			String rtnmsg = goodsManager.importEsTerminal(file, params);
			
			String[] rtnArr = rtnmsg.split("#");
			this.msgs.add("此次导入的虚拟串号总数是" + rtnArr[0] + "个。"
					+ " 其中，成功导入数为" + rtnArr[1] + "个，失败数为" + rtnArr[2] + "个");
			this.urls.put("", "goods!esTerminalList.do");
			this.params.put("inputMsg", "此次导入的虚拟串号总数是" + rtnArr[0] + "个。"
					+ " 其中，成功导入数为" + rtnArr[1] + "个，失败数为" + rtnArr[2] + "个");
		}catch(Exception e){
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
		}
		this.webpage=this.goodsManager.getEsTerminalList(this.getPage(), this.getPageSize(),sn,terminal_no);
		return "es_terminal_list";
	}
	
	/**
	 * @author zhengchuiliu
	 * 添加货品的条形码选择
	 * @return
	 */
	public String snSelectList(){
		try{
			this.webpage = goodsManager.snSelectList( this.getPage(),this.getPageSize(),sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sn_select_list";
	}
	
	/**
	 * @author zhengchuiliu
	 * 同步日志导出
	 * @return
	 */
	public void exportSynchLogs(){
		
 		List list=goodsManager.querySynchLogsExport(params);
		String[] title=new String[]{"SKU","商品/货品名称","批次号","同步总数","成功数","失败数","动作","创建时间","同步时间","状态","同步结果"};
		
		String[] content = { "sku","name","batch_id","batch_amount", "XYCG", "XYSB","action_code", "created_date","status_date","status","failure_desc"};
		
		String fileTitle = "同步日志导出";
//		HttpServletRequest request = ServletActionContext.getRequest();
//		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		}
	}
	
	/**
	 * @author tanyan
	 * 商品导出
	 * @return
	 */
	public void exportGoods(){	
      this.adminUser=ManagerUtils.getAdminUser();
		if (StringUtil.isEmpty(this.getOrg_names())) {
			this.setOrg_ids("");
		}
		setQueryParams();
		params.put("type", "goods");
		params.put("log", log);
 		Map map=goodsManager.queryGoodsExport(params);
 		
 		List list1=(List) map.get(GOODS_TYPE_CONTRACT_MACHINE);
 		List list2=(List) map.get(GOODS_TYPE_NUM_CARD);
 		List list3=(List) map.get(GOODS_TYPE_WIFI_CARD);
 		List list4=(List) map.get(GOODS_TYPE_PHONE);
 		List list5=(List) map.get(GOODS_TYPE_GIFT);
 		List list6=(List) map.get(GOODS_TYPE_ADJUNCT);
 		
 		//合约机
		String[] title1=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","当前生效活动","购机金额","总送费金额","BSS编码","月送费金额","虚拟串号","卡类型","活动编码","活动名称","活动描述","活动期限(天)","活动类型编码值","预存金额","首月返还","按月返还金额"};
		//号卡
		String[] title2=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","预存金额","首月返还","分月返还","BSS编码","是否成品卡","是否副卡","协议期总送费金额","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城"};
		//上网卡
		String[] title3=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","上网卡时间","是否全国卡","是否成品卡","上网卡类型","新商品大类","新商品小类","新产品品牌","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城"};
		//裸机
		String[] title4=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城"};
		//礼品
		String[] title5=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城"};
		//配件
		String[] title6=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城"};
												
		String[] content1 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","activity_names","mobile_price","all_give","bss_code","mon_give","terminalNo","machine_type","pmt_id","activity_names","brief","active_time","pmt_type","deposit_fee","order_return","mon_return"};
		String[] content2 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name","mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","deposit_fee","order_return","mon_return","bss_code","IS_SET","is_attached","all_give","apply_username", "create_time","publish_status","sale_store"};
		String[] content3 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","CARD_TIME","IS_GROUP","IS_SET","NETCARD_TYPE","new_type_id","new_cat_id","new_prod_brand","apply_username", "create_time","publish_status","sale_store"};
		String[] content4 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store"};
		String[] content5 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store"};
		String[] content6 = {"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store"};
		if(log!=null&&log.equals("yes")){
			//合约机
			 title1=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","当前生效活动","购机金额","总送费金额","BSS编码","月送费金额","虚拟串号","卡类型","活动编码","活动名称","活动描述","活动期限(天)","活动类型编码值","预存金额","首月返还","按月返还金额","操作人","操作时间"};
			//号卡
			 title2=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","预存金额","首月返还","分月返还","BSS编码","是否成品卡","是否副卡","协议期总送费金额","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城","操作人","操作时间"};
			//上网卡
			 title3=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","上网卡时间","是否全国卡","是否成品卡","上网卡类型","新商品大类","新商品小类","新产品品牌","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城","操作人","操作时间"};
			//裸机
			 title4=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城","操作人","操作时间"};
			//礼品
			 title5=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城","操作人","操作时间"};
			//配件
			 title6=new String[]{"SKU","GOODS_ID","商品分类","商品类型","品牌","商品编码","商品名称","商品简称","市场价(元)","销售价(元)","重量(克)","商品包","总部活动、商品编码","总部产品编码","当前生效活动","货品SKU","货品名称","商品数据录入人","商品数据录入时间","商品发布状态","商品发布商城","操作人","操作时间"};
			
			 content1 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","activity_names","mobile_price","all_give","bss_code","mon_give","terminalNo","machine_type","pmt_id","activity_names","brief","active_time","pmt_type","deposit_fee","order_return","mon_return","oper_name","oper_date"};
			 content2 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name","mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","deposit_fee","order_return","mon_return","bss_code","IS_SET","is_attached","all_give","apply_username", "create_time","publish_status","sale_store","oper_name","oper_date"};
			 content3 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","CARD_TIME","IS_GROUP","IS_SET","NETCARD_TYPE","new_type_id","new_cat_id","new_prod_brand","apply_username", "create_time","publish_status","sale_store","oper_name","oper_date"};
			 content4 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store","oper_name","oper_date"};
			 content5 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store","oper_name","oper_date"};
			 content6 = new String[]{"sku","goods_id","cat_name","type_name","brand_name","sn","name","simple_name", "mktprice", "price","weight","tag_name","act_code","prod_code","activity_names","productSku","products","apply_username","create_time","publish_status","sale_store","oper_name","oper_date"};
		}		
		String fileTitle = "商品导出";
		if (excel != null && !"".equals(excel)) {
			List<Map> listMap=new ArrayList<Map>();
			Map map1=new HashMap();
			map1.put("sheetTitile", "合约机");
			map1.put("list", list1);
			map1.put("title", title1);
			map1.put("content", content1);
			listMap.add(map1);
			
			Map map2=new HashMap();
			map2.put("sheetTitile", "号卡");
			map2.put("list", list2);
			map2.put("title", title2);
			map2.put("content", content2);
			listMap.add(map2);
			
			Map map3=new HashMap();
			map3.put("sheetTitile", "上网卡");
			map3.put("list", list3);
			map3.put("title", title3);
			map3.put("content", content3);
			listMap.add(map3);
			
			Map map4=new HashMap();
			map4.put("sheetTitile", "裸机");
			map4.put("list", list4);
			map4.put("title", title4);
			map4.put("content", content4);
			listMap.add(map4);
			
			Map map5=new HashMap();
			map5.put("sheetTitile", "礼品");
			map5.put("list", list5);
			map5.put("title", title5);
			map5.put("content", content5);
			listMap.add(map5);
			
			Map map6=new HashMap();
			map6.put("sheetTitile", "配件");
			map6.put("list", list6);
			map6.put("title", title6);
			map6.put("content", content6);
			listMap.add(map6);
			DownLoadUtil.exportForMoreSheet(listMap, fileTitle, ServletActionContext.getResponse());
		}
	}
	
	/**
	 * @author tanyan
	 * 货品导出
	 * @return
	 */
	public void exportProducts(){		
		estados  = productoM.getEstados();		
		this.adminUser=ManagerUtils.getAdminUser();		
		if (StringUtil.isEmpty(this.params.get("org"))) {
			this.setOrg_ids("");
		}		
		setQueryParams();
		params.put("type", "product");
		params.put("log", log);
		List list=goodsManager.queryProductsExport(params);
		String[] title=new String[]{"SKU","货品ID","货品名称","品牌","类型","商品分类","发布状态","状态","货品创建人","创建时间","更新时间"};
		String[] content = {"sku","goods_id","name","brand_name","type_name","cat_name","publish_status","market_enable","apply_username", "create_time","last_modify"};
		if(log.equals("yes")){
			title=new String[]{"SKU","货品ID","货品名称","品牌","类型","商品分类","发布状态","状态","货品创建人","创建时间","更新时间","操作人","操作时间"};
			content =new String[]{"sku","goods_id","name","brand_name","type_name","cat_name","publish_status","market_enable","apply_username", "create_time","last_modify","oper_name","oper_date"};
		}
		String fileTitle = "货品导出";
		if (excel != null && !"".equals(excel)) {
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		}
	}
	/**
	 * @author weiyanhong
	 * 根据货品批量生成商品
	 * @return
	 */
	public String batchGoodsConfig(){
		return "goods_per_config";
	}
	
	public String addProdPerConfig(){
		this.goodsParam=new GoodsParam();
		List datas = goodsTypeManager.getDropdownData("DC_GOODS_COLOR");
		this.goodsParam.setDropdownValues(datas);
		if(!"updatePer".equals(is_edit)){
			this.setGoodsView(new HashMap());
			//生成默认的SN，页面可以修改
			if("690001000".equals(this.cat_id)){
				this.goodsView.put("sn", goodsManager.createSN("", "", ""));
			}
		}
		
		return "prodPerConfig";
	}
	public String showProdConfigList(){
		return "showProdConfigList";
	}
	public String saveBatchAdd(){
		List  productList = JsonUtil.fromJson(this.productList, List.class);
		List  goodsList = JsonUtil.fromJson(this.goodsList, List.class);
		if(productList!=null&&productList.size()>0){
			Map<String,String> productIdMap=new HashMap();
			List<String> goodsIdList=new ArrayList();
			try {
				if(!StringUtil.isEmpty(goods.getProd_code())){
					goods.setProd_code(goods.getProd_code().trim());}
				if(!StringUtil.isEmpty(goods.getAct_code())){
					goods.setAct_code(goods.getAct_code().trim());}
				saveGoodsBus();
				setGoodsValue(goods);
				for (int i = 0; i < productList.size(); i++) {
					Map map = (Map) productList.get(i);
					String product_name =URLDecoder.decode((String) map.get("product_name"),"UTF-8");
					String goods_sn = (String) map.get("goods_sn");
					String dc_goods_color = (String) map.get("dc_goods_color");
					String input_prod_id = (String) map.get("input_prod_id");
					goods.setName(product_name);
					goods.setSn(goods_sn);
					goods.setSku(null);
					goods.setGoods_id(null);
					HttpServletRequest request = ThreadContextHolder.getHttpRequest();
					request.setAttribute("type", "product");
					String[] paramvalues = request.getParameterValues("paramvalues");
					if(paramvalues==null){
						if(request.getAttribute("paramvalues")!=null){
							paramvalues=(String[]) request.getAttribute("paramvalues");
							paramvalues[0]=dc_goods_color;
							request.setAttribute("paramvalues", paramvalues);
						}
					}else{
						paramvalues[0]=dc_goods_color;
						request.getParameterMap().put("paramvalues",paramvalues);
					}
					goodsManager.add(goods,goodsRules,goodsPackage,communityCodes,countyIds,custIds,staffIds,developIds,officeIds,s_scheme_id,s_element_type);
					//productIdMap.put(input_prod_id, new String[]{goods.getGoods_id(),goods.getProduct_id()});
					productIdMap.put(input_prod_id, goods.getGoods_id());
				}
				for (int i = 0; i < goodsList.size(); i++) {
					Map goodsMap=(Map) goodsList.get(i);
					String goods_name=URLDecoder.decode((String) goodsMap.get("goods_name"),"UTF-8");
					String input_prod_id=(String) goodsMap.get("input_prod_id");
					String prices=(String) goodsMap.get("price");
					Double price=Double.valueOf(prices);
					Goods goodsN=new Goods();
					goodsN.setType_id("20003");
					goodsN.setName(goods_name);
					goodsN.setPrice(price);
					goodsN.setMktprice(price);
					goodsN.setSku(null);
					goodsN.setGoods_id(null);
					if("690001000".equals(goods.getCat_id())){
						goodsN.setCat_id("69050200");
					}else if("690002000".equals(goods.getCat_id())){
						goodsN.setCat_id("69050100");
					}
					goodsN.setBrand_id(goods.getBrand_id());
					goodsN.setType("goods");
					HttpServletRequest request = ThreadContextHolder.getHttpRequest();
					request.setAttribute("type", "goods");
					request.getParameterMap().put("groupnames",null);
					request.setAttribute("groupnames", null);
					//String[] str = (String[]) productIdMap.get(input_prod_id);
					String rel_goods_id=productIdMap.get(input_prod_id);
					request.setAttribute("goods_ids", new String[]{rel_goods_id});
					request.setAttribute("rel_types", new String[]{"PRO_REL_GOODS"});
					List<Product> productIdList = goodsManager.getProductByGoodsId(rel_goods_id);
					if(!ListUtil.isEmpty(productIdList)){
						Product product = productIdList.get(0);
						request.setAttribute("product_ids", new String[]{product.getProduct_id()});
						goodsManager.add(goodsN,goodsRules,goodsPackage,communityCodes,countyIds,custIds,staffIds,developIds,officeIds,s_scheme_id,s_element_type);
					}
				}
				msg.setUrl("goods!productList.do");
				msg.addResult("url", "goods!productList.do");
				msg.setMessage("操作成功");
				msg.addResult(MESSAGE,"操作成功");
				if(null == goods.getType()){
					msg.setUrl("goods!list.do");
					msg.addResult(MESSAGE,"操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
					msg.setMessage("操作成功,新增货品请手动同步到WMS(通过同步给新商城1.0即可)");
				}		
				msg.setSuccess(true);
				msg.setResult(1);
			}catch (Exception e) {
				if(productIdMap.size()>0){
					Set<String> idsKeySet = productIdMap.keySet();
					int size = goodsIdList.size();
					String[] prod_ids = new String[size];
					int j=0;
					for (String prod_id : idsKeySet) {
						prod_ids[j++]= productIdMap.get(prod_id);
					}
					if(goodsIdList.size()>0){
						int length = goodsIdList.size();
						String[] ids = new String[length];
						for (int i = 0; i < goodsIdList.size(); i++) {
							ids[i]=goodsIdList.get(i);
						}
						try {
							goodsManager.deleteGoodsByID(prod_ids);
							goodsManager.deleteGoodsByID(ids);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
				}
				e.printStackTrace();
				msg.addResult(MESSAGE,"操作失败");
				msg.setMessage("操作失败");
				msg.setSuccess(false);
				msg.setResult(0);
			}
		}else{
			msg.addResult(MESSAGE,"货品列表信息为空，请填写至少一个货品个性信息");
			msg.setMessage("货品列表信息为空，请填写至少一个货品个性信息");
			msg.setSuccess(false);
			msg.setResult(0);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	public String batchGoodsCofig(){
		List  productList = JsonUtil.fromJson(this.productList, List.class);
		List  goodsList = JsonUtil.fromJson(this.goodsList, List.class);
		return "showProdConfigList";
	}
	
	/**============华盛开始=============**/
	
	/**
	 * zengxianlian
	 * 查询华盛数据
	 * @return
	 */
	public String getHSGoodsList(){
		try {
			if(hsGoods == null){
				hsGoods = new HSGoods();
			}
			if(StringUtils.isNotEmpty(matnr)){
				matnr = URLDecoder.decode(matnr,"utf-8");
				hsGoods.setMatnr(matnr);
			}
			if(StringUtils.isNotEmpty(mtart)){
				mtart = URLDecoder.decode(mtart,"utf-8");
				hsGoods.setMtart(mtart);
			}
			if(StringUtils.isNotEmpty(isMatched)){
				hsGoods.setIsMatched(isMatched);
			}else{
				hsGoods.setIsMatched("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.webpage=this.goodsManager.getHSGoodsList(this.getPage(), this.getPageSize(),matnr,mtart,isMatched);
		return "goods_of_hs_list";
	}
	
	/**============华盛结束=============**/
	//zengxianlian
	private void showSku(){
		String skus[] = {};
		for(int i = 0;i < skus.length; i++){
			Goods goods = goodsManager.getGoodsBySku(skus[i].trim());
			if(null != goods){
				String sku = goodsManager.createSKU(goods.getType(), goods.getCat_id());
				logger.info("update es_goods t set t.sku='"+goods.getSku()+"' where t.sku='"+sku+"';");
			}
		}
	}
	
	
	//添加商品中，查询小区相关信息
	public String communitySelectList(){
		try {
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage = goodsManager.searchCommunityInfo(name,getPage(),getPageSize());

		List<CommunityActivity> list = this.webpage.getResult();
		
		return "communitys_select_list";
	}
	
	public String elementSelectList(){
		element_type_list = goodsManager.getDcSqlByDcName("element_type");
			if (element_type_list == null) {
				element_type_list = new ArrayList<Map>();
			}
			/*Map m0 = new HashMap();
			m0.put("value", "-1");
			m0.put("value_desc", "--请选择--");
			element_id_list.add(0, m0);*/

		this.webpage = goodsManager.searchElementInfo(q_scheme_id,q_element_type);
		return "elements_select_list";
	}
	
	public String toElementSyn(){
		element_type_list = goodsManager.getDcSqlByDcName("element_type");
		if (element_type_list == null) {
			element_type_list = new ArrayList<Map>();
		}
		/*Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		element_id_list.add(0, m0);*/
		return "toElementSyn";
	}
	
	public String synElement(){
		try{
			goodsManager.saveGoodsElements(goods_ids,s_element_type);
			this.json = "{'result':'0','message':'更新成功'}";
		}catch(Exception e){
			this.json = "{'result':'1','message':'更新失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	public String inputRelation() {
		logger.info(inputname);
		if(null != inputname && inputname.equals("")) {
			inputname = "name";
			return "";
		}else if(inputname.equals("officereal")){ 
			inputname = "受理渠道名称";
			return "office_select";
		}else if(inputname.equals("develop")){ 
			inputname = "发展渠道名称";
			
			return "develop_select";
		}else if(inputname.equals("staffid")){ 
			inputname = "关联工号";
			
			return "staffid_select";
		}else if(inputname.equals("custid")){ 
			inputname = "关联客户";
			
			return "custid_select";
		}
		return "";
	}
	
	//批量发布
	
	public String liberacionForSaleGoods() {
		try {
			this.goodsManager.liberacion(sale_gids, market_enable+"");
			this.json = "{'result':'0','message':'发布成功'}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.json = "{'result':'1'，'message':'发布失败'}";
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	
	//添加商品中，查询县分相关信息
	public String countrySelectList(){
		try {
			if(areadef!=null){
				areadef = URLDecoder.decode(areadef,"utf-8");
			}
			if(countyname!=null){
				countyname = URLDecoder.decode(countyname,"utf-8");
			}
			if(region_type!=null){
				region_type = URLDecoder.decode(region_type,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage = goodsManager.searchCountryInfo(areadef,countyname,region_type,getPage(),getPageSize());

		List<Country> list = this.webpage.getResult();
		
		return "countrys_select_list";
	}
	
	//销售商品中，查询商品相关信息
	public String  addGoodsFromSale(){
		try {
			if(sku!=null){
				sku = URLDecoder.decode(sku,"utf-8");
			}
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
			if(goods_type!=null){
				goods_type = URLDecoder.decode(goods_type,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage = goodsManager.searchGoodsInfo(sku,name,goods_type,getPage(),getPageSize());

		List<Country> list = this.webpage.getResult();
		
		return "add_goods_list";
	}
	
	//添加销售商品中，查询标签相关信息
	public String tagSelectList(){
		try {
			if(tag_group_type!=null){
				tag_group_type = URLDecoder.decode(tag_group_type,"utf-8");
			}
			if(name!=null){
				name = URLDecoder.decode(name,"utf-8");
			}
			if(tag_code!=null){
				tag_code = URLDecoder.decode(tag_code,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage = goodsManager.searchTagInfo(tag_group_type,name,tag_code,getPage(),getPageSize());

		List<Country> list = this.webpage.getResult();
		
		return "tag_select_list";
	}
	
	private List<String>stringToList(String attrString) {
		List<String>stringList = new ArrayList<String>();
		if(attrString != null && !attrString.equals("")) {
			String[]attrStrings = attrString.split(",");
			
			for (String string : attrStrings) {
				stringList.add(string);
			}
		}
		
		return stringList;
	}
	
	public String getTag_group_type() {
		return tag_group_type;
	}

	public void setTag_group_type(String tag_group_type) {
		this.tag_group_type = tag_group_type;
	}

	public String getTag_code() {
		return tag_code;
	}

	public void setTag_code(String tag_code) {
		this.tag_code = tag_code;
	}

	public String getAreadef() {
		return areadef;
	}

	public void setAreadef(String areadef) {
		this.areadef = areadef;
	}

	public String getCountyname() {
		return countyname;
	}

	public void setCountyname(String countyname) {
		this.countyname = countyname;
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public  String selector(){
		
		return "selector";
	}

	public List getCatList() {
		return catList;
	}

	public void setCatList(List catList) {
		this.catList = catList;
	}

 

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}


	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}


	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}



	public Goods getGoods() {
		return goods;
	}


	public String getGoods_id() {
		return goods_id;
	}


	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}


	public Map getGoodsView() {
		return goodsView;
	}


	public void setGoodsView(Map goodsView) {
		this.goodsView = goodsView;
	}


	public Boolean getIs_edit() {
		return is_edit;
	}


	public void setIs_edit(Boolean is_edit) {
		this.is_edit = is_edit;
	}


	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String[] getId() {
		return id;
	}


	public void setId(String[] id) {
		this.id = id;
	}

	public GoodsPluginBundle getGoodsPluginBundle() {
		return goodsPluginBundle;
	}

	public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
		this.goodsPluginBundle = goodsPluginBundle;
	}

	public List<String> getTagHtmlList() {
		return tagHtmlList;
	}

	public void setTagHtmlList(List<String> tagHtmlList) {
		this.tagHtmlList = tagHtmlList;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public Integer getMarket_enable() {
		return market_enable;
	}

	public void setMarket_enable(Integer marketEnable) {
		market_enable = marketEnable;
	}

	public Integer getPublish_state() {
		return publish_state;
	}

	public void setPublish_state(Integer publish_state) {
		this.publish_state = publish_state;
	}

	public IGoodsFieldManager getGoodsFieldManager() {
		return goodsFieldManager;
	}

	public void setGoodsFieldManager(IGoodsFieldManager goodsFieldManager) {
		this.goodsFieldManager = goodsFieldManager;
	}

	public GoodsFieldPluginBundle getGoodsFieldPluginBundle() {
		return goodsFieldPluginBundle;
	}

	public void setGoodsFieldPluginBundle(
			GoodsFieldPluginBundle goodsFieldPluginBundle) {
		this.goodsFieldPluginBundle = goodsFieldPluginBundle;
	}

	public List<GoodsField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<GoodsField> fieldList) {
		this.fieldList = fieldList;
	}

	public String[] getTagids() {
		return tagids;
	}

	public void setTagids(String[] tagids) {
		this.tagids = tagids;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

	public List getTagList() {
		return tagList;
	}

	public void setTagList(List tagList) {
		this.tagList = tagList;
	}

	public String getAgent_login() {
		return agent_login;
	}

	public void setAgent_login(String agent_login) {
		this.agent_login = agent_login;
	}

	public List getAgentList() {
		return agentList;
	}

	public void setAgentList(List agentList) {
		this.agentList = agentList;
	}

	public Integer[] getStaff_nos() {
		return staff_nos;
	}

	public void setStaff_nos(Integer[] staff_nos) {
		this.staff_nos = staff_nos;
	}
	public String getLan_ids() {
		return lan_ids;
	}
	public void setLan_ids(String lan_ids) {
		this.lan_ids = lan_ids;
	}
	public IGoodsAreaManager getGoodsAreaManager() {
		return goodsAreaManager;
	}
	public void setGoodsAreaManager(IGoodsAreaManager goodsAreaManager) {
		this.goodsAreaManager = goodsAreaManager;
	}
	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}
	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}
	public ICoQueueManager getCoQueueManager() {
		return coQueueManager;
	}

	public void setCoQueueManager(ICoQueueManager coQueueManager) {
		this.coQueueManager = coQueueManager;
	}

	public List<PartnerAddress> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<PartnerAddress> addressList) {
		this.addressList = addressList;
	}
	public GoodsTypeDTO getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsTypeDTO goodsType) {
		this.goodsType = goodsType;
	}
	public IProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}
	
	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}
	public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public GoodsArea getGoodsArea() {
		return goodsArea;
	}
	public void setGoodsArea(GoodsArea goodsArea) {
		this.goodsArea = goodsArea;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public GoodsUsage getGoodsUsage() {
		return goodsUsage;
	}
	public void setGoodsUsage(GoodsUsage goodsUsage) {
		this.goodsUsage = goodsUsage;
	}
	public IGoodsUsageManager getGoodsUsageManager() {
		return goodsUsageManager;
	}
	public void setGoodsUsageManager(IGoodsUsageManager goodsUsageManager) {
		this.goodsUsageManager = goodsUsageManager;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public AdminUser getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsApplyParStock() {
		return isApplyParStock;
	}
	public void setIsApplyParStock(String isApplyParStock) {
		this.isApplyParStock = isApplyParStock;
	}



	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}



	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}




	public String getUsageid() {
		return usageid;
	}



	public void setUsageid(String usageid) {
		this.usageid = usageid;
	}



	public String getStore() {
		return store;
	}



	public void setStore(String store) {
		this.store = store;
	}



	public Integer getHave_stock() {
		return have_stock;
	}



	public void setHave_stock(Integer have_stock) {
		this.have_stock = have_stock;
	}



	public String getTypeCode() {
		return typeCode;
	}



	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}



	public Lan getLan() {
		return lan;
	}



	public void setLan(Lan lan) {
		this.lan = lan;
	}



	public ILanManager getLanManager() {
		return lanManager;
	}



	public void setLanManager(ILanManager lanManager) {
		this.lanManager = lanManager;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public List getLanList() {
		return lanList;
	}
	public String getOffer_name() {
		return offer_name;
	}

	public void setLanList(List lanList) {
		this.lanList = lanList;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public String getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}

	public GoodsApply getGoodsApply() {
		return goodsApply;
	}

	public void setGoodsApply(GoodsApply goodsApply) {
		this.goodsApply = goodsApply;
	}

	public String getBegin_nbr() {
		return begin_nbr;
	}

	public void setBegin_nbr(String begin_nbr) {
		this.begin_nbr = begin_nbr;
	}

	public String getEnd_nbr() {
		return end_nbr;
	}

	public void setEnd_nbr(String end_nbr) {
		this.end_nbr = end_nbr;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}

	public String getApply_desc() {
		return apply_desc;
	}

	public void setApply_desc(String apply_desc) {
		this.apply_desc = apply_desc;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Integer getMktprice() {
		return mktprice;
	}

	public void setMktprice(Integer mktprice) {
		this.mktprice = mktprice;
	}


	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public List getLvList() {
		return lvList;
	}

	public void setLvList(List lvList) {
		this.lvList = lvList;
	}
	

	public MemberPriceInf getMemberPriceServ() {
		return memberPriceServ;
	}

	public void setMemberPriceServ(MemberPriceInf memberPriceServ) {
		this.memberPriceServ = memberPriceServ;
	}

	public String getSelfProdType() {
		return selfProdType;
	}

	public void setSelfProdType(String selfProdType) {
		this.selfProdType = selfProdType;
	}

	public IGoodsAgreementManager getAgreeManager() {
		return agreeManager;
	}

	public void setAgreeManager(IGoodsAgreementManager agreeManager) {
		this.agreeManager = agreeManager;
	}

	public String getAgreement_name() {
		return agreement_name;
	}

	public void setAgreement_name(String agreement_name) {
		this.agreement_name = agreement_name;
	}

	public String getPage_type() {
		return page_type;
	}

	public void setPage_type(String page_type) {
		this.page_type = page_type;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public List getCodeList() {
		return codeList;
	}

	public void setCodeList(List codeList) {
		this.codeList = codeList;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String getMoney_control_value() {
		return money_control_value;
	}

	public void setMoney_control_value(String money_control_value) {
		this.money_control_value = money_control_value;
	}

	public String getQty_control_value() {
		return qty_control_value;
	}

	public void setQty_control_value(String qty_control_value) {
		this.qty_control_value = qty_control_value;
	}

	public String getControlValStr() {
		return controlValStr;
	}

	public void setControlValStr(String controlValStr) {
		this.controlValStr = controlValStr;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	public GoodsControl getGoodsControl() {
		return goodsControl;
	}

	public void setGoodsControl(GoodsControl goodsControl) {
		this.goodsControl = goodsControl;
	}

	public List getControlTypeList() {
		return controlTypeList;
	}

	public void setControlTypeList(List controlTypeList) {
		this.controlTypeList = controlTypeList;
	}

	public List getSubControlTypeList() {
		return subControlTypeList;
	}

	public void setSubControlTypeList(List subControlTypeList) {
		this.subControlTypeList = subControlTypeList;
	}

	public List getCompanyTypeList() {
		return companyTypeList;
	}

	public void setCompanyTypeList(List companyTypeList) {
		this.companyTypeList = companyTypeList;
	}

	public List getGoodsControlList() {
		return goodsControlList;
	}

	public void setGoodsControlList(List goodsControlList) {
		this.goodsControlList = goodsControlList;
	}

	public String getControl_type() {
		return control_type;
	}

	public void setControl_type(String control_type) {
		this.control_type = control_type;
	}

	public String getSub_control_type() {
		return sub_control_type;
	}

	public void setSub_control_type(String sub_control_type) {
		this.sub_control_type = sub_control_type;
	}

	public String getControl_value() {
		return control_value;
	}

	public void setControl_value(String control_value) {
		this.control_value = control_value;
	}
	
	
	public List<GoodsLvPrice> getGoodsLvPriceList() {
		return goodsLvPriceList;
	}

	public void setGoodsLvPriceList(List<GoodsLvPrice> goodsLvPriceList) {
		this.goodsLvPriceList = goodsLvPriceList;
	}

	public boolean isJoin_suced() {
		return join_suced;
	}

	public void setJoin_suced(boolean join_suced) {
		this.join_suced = join_suced;
	}

	public Map getNameMap() {
		return nameMap;
	}

	public void setNameMap(Map nameMap) {
		this.nameMap = nameMap;
	}

	public String getAgreement_id() {
		return agreement_id;
	}

	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
	}

	public String getLan_code() {
		return lan_code;
	}

	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}

	public int getCurr_control_value() {
		return curr_control_value;
	}

	public void setCurr_control_value(int curr_control_value) {
		this.curr_control_value = curr_control_value;
	}
	
	public GoodsApplyJoin getGoodsApplyJoin() {
		return goodsApplyJoin;
	}

	public void setGoodsApplyJoin(GoodsApplyJoin goodsApplyJoin) {
		this.goodsApplyJoin = goodsApplyJoin;
	}

	public List getPriceList() {
		return priceList;
	}

	public void setPriceList(List priceList) {
		this.priceList = priceList;
	}

	public int getPriceListSize() {
		return priceListSize;
	}

	public void setPriceListSize(int priceListSize) {
		this.priceListSize = priceListSize;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	
	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public GoodsRules getGoodsRules() {
		return goodsRules;
	}

	public void setGoodsRules(GoodsRules goodsRules) {
		this.goodsRules = goodsRules;
	}

	public List<GoodsStype> getChildStypeList() {
		return childStypeList;
	}

	public void setChildStypeList(List<GoodsStype> childStypeList) {
		this.childStypeList = childStypeList;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

    public List getStypeList() {
        return stypeList;
    }

    public void setStypeList(List stypeList) {
        this.stypeList = stypeList;
    }

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getGoodsAreaParam() {
		return goodsAreaParam;
	}

	public void setGoodsAreaParam(String goodsAreaParam) {
		this.goodsAreaParam = goodsAreaParam;
	}

	public GoodsBusiness getGoodsBusiness() {
		return goodsBusiness;
	}

	public void setGoodsBusiness(GoodsBusiness goodsBusiness) {
		this.goodsBusiness = goodsBusiness;
	}
	
	public String getSupplier_id() {
		if(supplier==null || "".equals(supplier)){
			this.supplier_id="";
		}
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		if(supplier==null || "".equals(supplier)){
			this.supplier_id="";
		}		
		this.supplier_id = supplier_id;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {		
		this.supplier = supplier;
	}

	public IGoodsCacheUtil getGoodsUtil() {
		return goodsCacheUtil;
	}

	public void setGoodsUtil(IGoodsCacheUtil goodsCacheUtil) {
		this.goodsCacheUtil = goodsCacheUtil;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIs_copy() {
		return is_copy;
	}

	public void setIs_copy(Boolean is_copy) {
		this.is_copy = is_copy;
	}

	public GoodsPackage getGoodsPackage() {
		return goodsPackage;
	}
	public String getProduct_id() {
		return product_id;
	}

	public void setGoodsPackage(GoodsPackage goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public GoodsRel getGoods_rel() {
		return goods_rel;
	}

	public void setGoods_rel(GoodsRel goods_rel) {
		this.goods_rel = goods_rel;
	}


	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public List getColorList() {
		return colorList;
	}

	public void setColorList(List colorList) {
		this.colorList = colorList;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getSearch_tag_name() {
		return search_tag_name;
	}

	public void setSearch_tag_name(String search_tag_name) {
		this.search_tag_name = search_tag_name;
	}

	
	public String organizacion(){
		estados  = productoM.getEstados();
		return "organizacion";
	}

	public IProductoManager getProductoM() {
		return productoM;
	}

	public void setProductoM(IProductoManager productoM) {
		this.productoM = productoM;
	}

	public List getOrgList() {
		return orgList;
	}

	public void setOrgList(List orgList) {
		this.orgList = orgList;
	}

	public int getOrgListSize() {
		return orgListSize;
	}

	public void setOrgListSize(int orgListSize) {
		this.orgListSize = orgListSize;
	}

	public String getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(String org_ids) {
		this.org_ids = org_ids;
	}

	public String getOrg_names() {
		return org_names;
	}

	public void setOrg_names(String org_names) {
		this.org_names = org_names;
	}

	public String getTerminal_pid() {
		return terminal_pid;
	}

	public void setTerminal_pid(String terminal_pid) {
		this.terminal_pid = terminal_pid;
	}

	public String getContract_pid() {
		return contract_pid;
	}

	public void setContract_pid(String contract_pid) {
		this.contract_pid = contract_pid;
	}

	public String getOfferPids() {
		return offerPids;
	}

	public void setOfferPids(String offerPids) {
		this.offerPids = offerPids;
	}

	public GoodsParam getGoodsParam() {
		return goodsParam;
	}

	public void setGoodsParam(GoodsParam goodsParam) {
		this.goodsParam = goodsParam;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}


	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getGoods_ids() {
		return goods_ids;
	}

	public void setGoods_ids(String goods_ids) {
		this.goods_ids = goods_ids;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Map getGoodsOld() {
		return goodsOld;
	}

	public void setGoodsOld(Map goodsOld) {
		this.goodsOld = goodsOld;
	}

	public Map getGoodsNew() {
		return goodsNew;
	}

	public void setGoodsNew(Map goodsNew) {
		this.goodsNew = goodsNew;
	}

	public Map getProductsOld() {
		return productsOld;
	}

	public void setProductsOld(Map productsOld) {
		this.productsOld = productsOld;
	}

	public Map getProductsNew() {
		return productsNew;
	}

	public void setProductsNew(Map productsNew) {
		this.productsNew = productsNew;
	}

	public List getGoodsOldList() {
		return goodsOldList;
	}

	public void setGoodsOldList(List goodsOldList) {
		this.goodsOldList = goodsOldList;
	}

	public List getGoodsNewList() {
		return goodsNewList;
	}

	public void setGoodsNewList(List goodsNewList) {
		this.goodsNewList = goodsNewList;
	}

	public Map getGoodsParamOldMap() {
		return goodsParamOldMap;
	}

	public void setGoodsParamOldMap(Map goodsParamOldMap) {
		this.goodsParamOldMap = goodsParamOldMap;
	}

	public Map getGoodsParamNewMap() {
		return goodsParamNewMap;
	}

	public void setGoodsParamNewMap(Map goodsParamNewMap) {
		this.goodsParamNewMap = goodsParamNewMap;
	}

	public HSGoods getHsGoods() {
		return hsGoods;
	}

	public void setHsGoods(HSGoods hsGoods) {
		this.hsGoods = hsGoods;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMtart() {
		return mtart;
	}

	public void setMtart(String mtart) {
		this.mtart = mtart;
	}

	public String getSerial_nos() {
		return serial_nos;
	}

	public void setSerial_nos(String serial_nos) {
		this.serial_nos = serial_nos;
	}

	public String getIsMatched() {
		return isMatched;
	}

	public void setIsMatched(String isMatched) {
		this.isMatched = isMatched;
	}

	public String getCommunityCodes() {
		return communityCodes;
	}

	public void setCommunityCodes(String communityCodes) {
		this.communityCodes = communityCodes;
	}
	
	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public String getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public EsTerminal getEsTerminal() {
		return esTerminal;
	}

	public void setEsTerminal(EsTerminal esTerminal) {
		this.esTerminal = esTerminal;
	}

	public String getTerminal_no() {
		return terminal_no;
	}

	public void setTerminal_no(String terminal_no) {
		this.terminal_no = terminal_no;
	}

	public String getZdb_name() {
		return zdb_name;
	}

	public void setZdb_name(String zdb_name) {
		this.zdb_name = zdb_name;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getDeal_flag() {
		return deal_flag;
	}

	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}

	public String getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}

	public List<CommunityActivity> getCmutList() {
		return cmutList;
	}

	public void setCmutList(List<CommunityActivity> cmutList) {
		this.cmutList = cmutList;
	}

	public String getCountyIds() {
		return countyIds;
	}

	public void setCountyIds(String countyIds) {
		this.countyIds = countyIds;
	}
	
	

	public List<String> getStaffIdList() {
		return staffIdList;
	}

	public void setStaffIdList(List<String> staffIdList) {
		this.staffIdList = staffIdList;
	}

	public List<String> getCustIdList() {
		return custIdList;
	}

	public void setCustIdList(List<String> custIdList) {
		this.custIdList = custIdList;
	}

	public List<String> getDevelopIdList() {
		return developIdList;
	}

	public void setDevelopIdList(List<String> developIdList) {
		this.developIdList = developIdList;
	}

	public List<String> getOfficeIdList() {
		return officeIdList;
	}

	public void setOfficeIdList(List<String> officeIdList) {
		this.officeIdList = officeIdList;
	}

	public List<Goods> getGoodsTypeList() {
		return goodsTypeList;
	}

	public void setGoodsTypeList(List<Goods> goodsTypeList) {
		this.goodsTypeList = goodsTypeList;
	}

	public List<Tag> getGoodsTagList() {
		return goodsTagList;
	}

	public void setGoodsTagList(List<Tag> goodsTagList) {
		this.goodsTagList = goodsTagList;
	}

	public List<Tag> getSaleTagList() {
		return saleTagList;
	}

	public void setSaleTagList(List<Tag> saleTagList) {
		this.saleTagList = saleTagList;
	}

	public String getSale_gid() {
		return sale_gid;
	}

	public void setSale_gid(String sale_gid) {
		this.sale_gid = sale_gid;
	}

	public String getSale_gname() {
		return sale_gname;
	}

	public void setSale_gname(String sale_gname) {
		this.sale_gname = sale_gname;
	}

	public String getPackage_type() {
		return package_type;
	}

	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}

	public String[] getSale_gids() {
		return sale_gids;
	}

	public void setSale_gids(String[] sale_gids) {
		this.sale_gids = sale_gids;
	}


	public List<ActiveElementInfo> getElementList() {
		return elementList;
	}

	public void setElementList(List<ActiveElementInfo> elementList) {
		this.elementList = elementList;
	}

	public List<Map> getElement_type_list() {
		return element_type_list;
	}

	public void setElement_type_list(List<Map> element_type_list) {
		this.element_type_list = element_type_list;
	}

	public String getQ_scheme_id() {
		return q_scheme_id;
	}

	public String getS_scheme_id() {
		return s_scheme_id;
	}

	public void setS_scheme_id(String s_scheme_id) {
		this.s_scheme_id = s_scheme_id;
	}

	public String getS_element_type() {
		return s_element_type;
	}

	public void setS_element_type(String s_element_type) {
		this.s_element_type = s_element_type;
	}

	public void setQ_scheme_id(String q_scheme_id) {
		this.q_scheme_id = q_scheme_id;
	}

	public String getQ_element_type() {
		return q_element_type;
	}

	public void setQ_element_type(String q_element_type) {
		this.q_element_type = q_element_type;
	}


//	public String getIntro() {
//		return intro;
//	}
//
//	public void setIntro(String intro) {
//		this.intro = intro;
//	}
	
	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCodea() {
		return codea;
	}

	public void setCodea(String codea) {
		this.codea = codea;
	}

	public String getCodeb() {
		return codeb;
	}

	public void setCodeb(String codeb) {
		this.codeb = codeb;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	/**
	 * 商品映射页面
	 * @return 
	 * @throws IOException 
	 */
	public String fengXingDongMapping() {
		params.put("stype", stype);
		params.put("pkey", pkey);
		params.put("pname", pname);
		params.put("codea", codea);
		params.put("codeb", codeb);
		params.put("comments", comments);
		this.webpage = goodsManager.fxdMapping(params, this.getPage(),this.getPageSize());
		return "fengXingDongMapping";
	}
	
	/**
	 * 删除
	 */
	public String deletefengXingDongMapping() {
		params.put("stype", stype);
		params.put("pkey", pkey);
		params.put("pname", pname);
		if((null != stype&&!stype.equals(""))&&(null!=pkey&&!pkey.equals(""))&&(null!=pname&&!pname.equals(""))) {
			goodsManager.deleteFxdMapping(params);
		}
		params.clear();//避免回显
		this.webpage = goodsManager.fxdMapping(params, this.getPage(),this.getPageSize());
		return "fengXingDongMapping";
	}
	
	
	/**
	 * 增加
	 */
	public String addfengXingDongMapping() {
		params.put("stype", stype);
		params.put("pkey", pkey);
		params.put("pname", pname);
		params.put("codea", codea);
		params.put("codeb", codeb);
		params.put("comments", comments);
		int num = 0;
		if((null != stype&&!stype.equals(""))&&(null!=pkey&&!pkey.equals(""))&&(null!=pname&&!pname.equals(""))) {
			num = goodsManager.editFxdMapping(params);
		}
		if(num != 0) {//执行更新
			goodsManager.updateFxdMapping(params);
		}else {
			if((null != stype&&!stype.equals(""))&&(null!=pkey&&!pkey.equals(""))&&(null!=pname&&!pname.equals(""))) {
				goodsManager.fxdMappingAdd(params);
			}
		}
		params.clear();//避免回显
		this.webpage = goodsManager.fxdMapping(params, this.getPage(),this.getPageSize());
		return "fengXingDongMapping";
	}
	
	/**
	 * 添加页面
	 */
	
	public String showAddInput() {
		return "fxd_add_input";
	}
}



	/**
	 * 校验配置类型和总部商品编码是唯一
	 * @throws IOException 
	
	public boolean member_Check() throws IOException {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//response.setCharacterEncoding("UTF-8");
		params.put("pkey", pkey);
		params.put("stype", stype);
		params.put("pname",pname);
		Map<String,String> map = null;
		map =  goodsManager.editFxdMapping(params);
		if(null != map) {
			params.put("flag", "0");
			//response.getWriter().write("0");
		}
	}
	 */
