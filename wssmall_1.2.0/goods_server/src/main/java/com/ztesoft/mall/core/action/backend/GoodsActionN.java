package com.ztesoft.mall.core.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import params.member.req.PricePrivListReq;
import params.member.resp.PricePrivListResp;
import services.MemberPriceInf;
import services.SupplierInf;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Tab;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.params.GoodsPlatformInfo;
import com.ztesoft.net.mall.core.plugin.field.GoodsField;
import com.ztesoft.net.mall.core.plugin.field.GoodsFieldPluginBundle;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsFieldManager;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.ISpecManager;
import com.ztesoft.net.mall.core.service.ISpecValueManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 商品管理配置
 * @author zou.qh
 *
 */
public class GoodsActionN extends WWAction {
	
	private IGoodsManagerN goodsManagerN;
	private IGoodsCatManager goodsCatManager;
	private IGoodsTypeManager goodsTypeManager;
	private IGoodsFieldManager goodsFieldManager;
	private MemberPriceInf memberPriceServ;
	private GoodsFieldPluginBundle goodsFieldPluginBundle;
	private IDcPublicInfoManager dcPublicInfoManager;
	private ICoQueueManager coQueueManager;
	private ISpecManager specManager;
	private ISpecValueManager specValueManager;
	private SupplierInf supplierServ;

	private Goods goods;
	private GoodsRules goodsRules; //商品动作规则
	private GoodsExtData goodsExtData;
	private AdminUser adminUser;
	private Map goodsView;
	private GoodsType goodsType;
	private GoodsPlatformInfo goodsPlatformInfo; 
	
	private String goods_id;
	private boolean join_suced =false ;
	private boolean is_edit;
	private boolean is_copy;
	private String actionName;
	private String founder;
	private String ref_obj_id;
	private String store;	//库存量，用于跳转的值带入
	private String selfProdType  = "T"; 
	private String control_type;
    private String sub_control_type;
    private String control_value;
    private String page_type;
    private Integer market_enable;
	
	private List<Tab> tabs;  //标签页
	private List catList; // 所有商品分类
	private List<String> tagHtmlList ;
	private List lvList ; //会员等级
	private List<GoodsField> fieldList;
	private List codeList;//本地网
	private List controlTypeList;
	private List subControlTypeList;
    private List companyTypeList;
    private List goodsControlList;
    private List specList;
    
    private boolean IsEditGoodsPlatInfo = false;
    private Map<String, String> params = new HashMap<String, String>();
    //商品图片信息
    private String[]  pc_image_file_list;
    private String[]  mbl_image_file_list;
    private String[]  wx_image_file_list;
    
    public String list(){
		this.adminUser=ManagerUtils.getAdminUser();
    	this.webpage = this.goodsManagerN.searchGoods(params, this.getPage(), this.getPageSize());
    	return "goods_listn";
    }
    
    public String searchDialog(){
		return "search_dialog";
	}
    
  //入围商品列表
  	public String listInbox(){
  		this.selfProdType = "F" ;
  		this.actionName = "goodsN!listInbox.do" ;
  		return list();
  	}
  	
	
	public String saveAdd(){
		try {
			this.getUserInfo(goods);
			this.goodsManagerN.addGoods(goods, goodsRules, goodsExtData);
			this.json = "{result:1,message:'商品添加成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:0,message:'商品添加失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public void getUserInfo(Goods goods){
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		String user_no = adminUser.getUserid();
		String user_name = adminUser.getUsername();
		String userId = adminUser.getParuserid();
		if (Consts.CURR_FOUNDER0 == adminUser.getFounder().intValue() || ManagerUtils.isAdminUser() ) {
			userId = "-1" ;
			goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
		}else{
			int value = adminUser.getFounder().intValue();
			if(Consts.CURR_FOUNDER4==value) //供货商
				userId = adminUser.getUserid();
			else if(Consts.CURR_FOUNDER5 == value) //供货商员工
				userId = adminUser.getParuserid();
			goods.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
		}
		if(goods.getSearch_key()!=null){
			goods.setSearch_key("_"+user_name+"_"+goods.getSearch_key());
		}else{
			goods.setSearch_key("_"+user_name+"_");
		}
		goods.setStaff_no(userId);
		goods.setCreator_user(user_no);
	}
	
	public String add(){
		is_edit = true;
		founder =ManagerUtils.getAdminUser().getFounder()+"";
		actionName = "goodsN!saveAdd.do";
		this.adminUser = ManagerUtils.getAdminUser();
		this.pageId = Consts.GOODS_ADD_PAGE_ID;
		this.tabs = JspPageTabs.getTabs("goods");
		DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.codeList = dcPublicCache.getList("6666");
		PricePrivListReq req = new PricePrivListReq();
		PricePrivListResp resp = new PricePrivListResp();
		resp = memberPriceServ.loadPricePrivList(req);
		if(resp != null){
			this.lvList = resp.getPricePrivList();
		}
		specList = specManager.list();
		if(specList == null) specList = new ArrayList();
		for(int i=0;i<specList.size();i++){
			Map spec = (Map)specList.get(i);
			String spec_id = Const.getStrValue(spec, "spec_id");
			List valueList = this.specValueManager.list(spec_id);
			spec.put("valueList", valueList);
		}
		tagHtmlList = goodsManagerN.fillAddInputData();
		return "goods_inputn";
	}
	
	public String saveEdit(){
		try{
			this.getUserInfo(goods);
			this.goodsManagerN.edit(goods, goodsRules, goodsExtData);
			this.json = "{result:1,message:'商品编辑成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:0,message:'商品编辑失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String edit(){
		try{
			is_edit = true;
			founder = ManagerUtils.getAdminUser().getFounder()+"";
			actionName = "goodsN!saveEdit.do";
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.adminUser = ManagerUtils.getAdminUser();
			if(StringUtils.isNotEmpty(ref_obj_id)){
				goods_id = ref_obj_id ;
			}
			
			specList = specManager.list();
			if(specList == null) specList = new ArrayList();
			for(int i=0;i<specList.size();i++){
				Map spec = (Map)specList.get(i);
				String spec_id = Const.getStrValue(spec, "spec_id");
				List valueList = this.specValueManager.list(spec_id);
				spec.put("valueList", valueList);
			}
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.tabs = JspPageTabs.getTabs("goods");
			catList = goodsCatManager.listAllChildren(0 + "");
			GoodsEditDTO editDTO = this.goodsManagerN.getGoodsEditData(goods_id);
			goodsView = editDTO.getGoods();
			if(StringUtil.isEmpty((String)goodsView.get("brand_id")))
				goodsView.put("brand_id", "0");
			String type_id=goodsView.get("type_id").toString();
			goodsType = goodsTypeManager.get(type_id);
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
			if(goodsView.get("arrival") != null && goodsView.get("arrival") instanceof java.sql.Timestamp)
				arrival = ((java.sql.Timestamp)goodsView.get("arrival")).toString() ;
			else
				arrival = (String)goodsView.get("arrival") ;
		
			if(StringUtils.isNotEmpty(arrival)){
				if(arrival.length()<10){
					goodsView.put("arrival", arrival) ; 
				}
				else{
					goodsView.put("arrival", arrival.substring( 0, 10)) ; 
				}
			}
		
			String exp_date = null ;
			if(goodsView.get("exp_date") != null && goodsView.get("exp_date") instanceof java.sql.Timestamp)
				exp_date = ((java.sql.Timestamp)goodsView.get("exp_date")).toString() ;
			else
				exp_date = (String)goodsView.get("exp_date") ;
		
			if(StringUtils.isNotEmpty(exp_date) ){
				goodsView.put("exp_date", exp_date.substring( 0, 10)) ; 
			}
			BeanUtils.copyProperties(goods, goodsView);
			fieldList = this.goodsFieldManager.list(""+ editDTO.getGoods().get("type_id"));
			for(GoodsField field:fieldList){
				String html = this.goodsFieldPluginBundle.onDisplay(field, editDTO.getGoods());
				field.setInputHtml(html);
			}
			//商品控制信息APPLY_JOIN
			//goods.audit_state == '00M' && goods.apply_join == 'T' 
			String audit_state = goodsView.get("audit_state").toString();
			int userFounder  =  adminUser.getFounder();
			if(userFounder==4){
				this.join_suced = true;
			}
			if("F".equals(this.selfProdType)){
				DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
				this.codeList = dcPublicCache.getList("6666");
				this.controlTypeList = dcPublicCache.getList("6661");
				this.subControlTypeList = dcPublicCache.getList("6662");
				this.companyTypeList = dcPublicCache.getList("6663");
				this.goodsControlList = this.goodsManagerN.getControlById(this.goods_id);
				Map map = (Map) this.goodsControlList.get(0);
				this.control_type =map.get("control_type").toString() ;
				this.sub_control_type = map.get("sub_control_type").toString();
				this.control_value = map.get("control_value").toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return "goods_inputn";
	}
	
	/**
	 * 到商品查看页面
	 */
	public String  view(){
		page_type="view";
		edit();
		return "goods_inputn";
	}
	
	public String copy(){
		//先将商品更新为下架
		try{
			is_edit = false;
			is_copy = true;
			founder = ManagerUtils.getAdminUser().getFounder()+"";
			actionName = "goodsN!saveAdd.do";
			this.adminUser = ManagerUtils.getAdminUser();
			if(StringUtils.isNotEmpty(ref_obj_id)){
				goods_id = ref_obj_id ;
			}
			
			this.pageId = Consts.GOODS_ADD_PAGE_ID;
			this.tabs = JspPageTabs.getTabs("goods");
			catList = goodsCatManager.listAllChildren(0 + "");
			GoodsEditDTO editDTO = this.goodsManagerN.getGoodsEditData(goods_id);
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
				this.goodsControlList = this.goodsManagerN.getControlById(this.goods_id);
				Map map = (Map) this.goodsControlList.get(0);
				this.control_type =map.get("control_type").toString() ;
				this.sub_control_type = map.get("sub_control_type").toString();
				this.control_value = map.get("control_value").toString();
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "goods_inputn";
	}
	
	/**
	 * 上下架处理
	 * @return
	 */
	public String marketEnable(){
		
		try{
			this.goodsManagerN.updateField("market_enable", this.market_enable, goods_id);
			this.insertCoQueue(goods_id, market_enable);
			this.showSuccessJson("更新上架状态成功");
		}catch(RuntimeException e){
			this.showErrorJson("更新上架状态失败");
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	public void insertCoQueue(String goods_id,Integer market_enable){
		List datas = goodsManagerN.getOrgResultByGoodsId(goods_id);
		//查询每个lv2的org_id_belong的最新更新记录再次进行修改同步
		for(int i=0;i<datas.size();i++){
			Map date =(Map) datas.get(i);
			CoQueue addReq = new CoQueue();
			String batch_id = goodsManagerN.getSequence("S_ES_CO_BATCH_ID");
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
	}
	
	public String specList(){
		specList = specManager.list();
		return "spec_list";
	}
	
	public String delete(){
		this.goodsManagerN.delete(goods_id);
		this.json = "{result:0,message:'操作成功'}";
		return WWAction.JSON_MESSAGE;
	}
 
	//初始化商品图片
	public void initGoodsPlatFormInfo(String goods_id){
		
	    this.goodsPlatformInfo = this.goodsManagerN.getGoodsPlatFormInfo(goods_id);
	    if(goodsPlatformInfo!=null){
		    if(!StringUtil.isEmpty(goodsPlatformInfo.getPc_image_file())){
		    	this.pc_image_file_list = goodsPlatformInfo.getPc_image_file().split(",");
		    }
		    if(!StringUtil.isEmpty(goodsPlatformInfo.getMbl_image_file())){
		    	this.mbl_image_file_list = goodsPlatformInfo.getMbl_image_file().split(",");
		    }
		    if(!StringUtil.isEmpty(goodsPlatformInfo.getWx_image_file())){
		    	this.wx_image_file_list = goodsPlatformInfo.getWx_image_file().split(",");
		    }
	    }
	}

	// 保存商品图片信息
	public String saveGoodsPaltFormInfo() {
		try {
			
			if(StringUtil.isEmpty(goods_id)){
				this.json = "{result:1,message:'操作失败：'goods_id不能为空'}";
				return WWAction.JSON_MESSAGE;
			}
            if(this.IsEditGoodsPlatInfo){
				this.goodsManagerN.delGoodsPlatformInfo(this.goods_id);
			}
            this.goodsPlatformInfo.setSource_from(ManagerUtils.getSourceFrom());
			this.goodsPlatformInfo.setGoods_id(this.goods_id);
			this.goodsPlatformInfo.setPc_image_default(this.goodsPlatformInfo.getPc_image_file().split(",")[0]);
			this.goodsPlatformInfo.setMbl_image_default(this.goodsPlatformInfo.getMbl_image_file().split(",")[0]);
			this.goodsPlatformInfo.setWx_image_default(this.goodsPlatformInfo.getWx_image_file().split(",")[0]);
			this.goodsManagerN.addGoodsPlatFormInfo(goodsPlatformInfo);
			this.json = "{result:0,message:'操作成功'}";

		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'操作失败：'" + e.getMessage() + "}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public GoodsPlatformInfo getGoodsPlatformInfo() {
		return goodsPlatformInfo;
	}

	public void setGoodsPlatformInfo(GoodsPlatformInfo goodsPlatformInfo) {
		this.goodsPlatformInfo = goodsPlatformInfo;
	}

	public IGoodsManagerN getGoodsManagerN() {
		return goodsManagerN;
	}

	public void setGoodsManagerN(IGoodsManagerN goodsManagerN) {
		this.goodsManagerN = goodsManagerN;
	}
	public String[] getPc_image_file_list() {
		return pc_image_file_list;
	}

	public void setPc_image_file_list(String[] pc_image_file_list) {
		this.pc_image_file_list = pc_image_file_list;
	}

	public String[] getMbl_image_file_list() {
		return mbl_image_file_list;
	}

	public void setMbl_image_file_list(String[] mbl_image_file_list) {
		this.mbl_image_file_list = mbl_image_file_list;
	}

	public String[] getWx_image_file_list() {
		return wx_image_file_list;
	}

	public void setWx_image_file_list(String[] wx_image_file_list) {
		this.wx_image_file_list = wx_image_file_list;
	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

	public IGoodsFieldManager getGoodsFieldManager() {
		return goodsFieldManager;
	}

	public void setGoodsFieldManager(IGoodsFieldManager goodsFieldManager) {
		this.goodsFieldManager = goodsFieldManager;
	}

	public MemberPriceInf getMemberPriceServ() {
		return memberPriceServ;
	}

	public void setMemberPriceServ(MemberPriceInf memberPriceServ) {
		this.memberPriceServ = memberPriceServ;
	}

	public GoodsFieldPluginBundle getGoodsFieldPluginBundle() {
		return goodsFieldPluginBundle;
	}

	public void setGoodsFieldPluginBundle(
			GoodsFieldPluginBundle goodsFieldPluginBundle) {
		this.goodsFieldPluginBundle = goodsFieldPluginBundle;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public ICoQueueManager getCoQueueManager() {
		return coQueueManager;
	}

	public void setCoQueueManager(ICoQueueManager coQueueManager) {
		this.coQueueManager = coQueueManager;
	}

	public ISpecManager getSpecManager() {
		return specManager;
	}

	public void setSpecManager(ISpecManager specManager) {
		this.specManager = specManager;
	}

	public ISpecValueManager getSpecValueManager() {
		return specValueManager;
	}

	public void setSpecValueManager(ISpecValueManager specValueManager) {
		this.specValueManager = specValueManager;
	}

	public SupplierInf getSupplierServ() {
		return supplierServ;
	}

	public void setSupplierServ(SupplierInf supplierServ) {
		this.supplierServ = supplierServ;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsRules getGoodsRules() {
		return goodsRules;
	}

	public void setGoodsRules(GoodsRules goodsRules) {
		this.goodsRules = goodsRules;
	}

	public GoodsExtData getGoodsExtData() {
		return goodsExtData;
	}

	public void setGoodsExtData(GoodsExtData goodsExtData) {
		this.goodsExtData = goodsExtData;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public Map getGoodsView() {
		return goodsView;
	}

	public void setGoodsView(Map goodsView) {
		this.goodsView = goodsView;
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public boolean isJoin_suced() {
		return join_suced;
	}

	public void setJoin_suced(boolean join_suced) {
		this.join_suced = join_suced;
	}

	public boolean isIs_edit() {
		return is_edit;
	}

	public void setIs_edit(boolean is_edit) {
		this.is_edit = is_edit;
	}

	public boolean getIs_copy() {
		return is_copy;
	}

	public void setIs_copy(boolean is_copy) {
		this.is_copy = is_copy;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getRef_obj_id() {
		return ref_obj_id;
	}

	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getSelfProdType() {
		return selfProdType;
	}

	public void setSelfProdType(String selfProdType) {
		this.selfProdType = selfProdType;
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

	public String getPage_type() {
		return page_type;
	}

	public void setPage_type(String page_type) {
		this.page_type = page_type;
	}

	public Integer getMarket_enable() {
		return market_enable;
	}

	public void setMarket_enable(Integer market_enable) {
		this.market_enable = market_enable;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public List getCatList() {
		return catList;
	}

	public void setCatList(List catList) {
		this.catList = catList;
	}

	public List<String> getTagHtmlList() {
		return tagHtmlList;
	}

	public void setTagHtmlList(List<String> tagHtmlList) {
		this.tagHtmlList = tagHtmlList;
	}

	public List getLvList() {
		return lvList;
	}

	public void setLvList(List lvList) {
		this.lvList = lvList;
	}

	public List<GoodsField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<GoodsField> fieldList) {
		this.fieldList = fieldList;
	}

	public List getCodeList() {
		return codeList;
	}

	public void setCodeList(List codeList) {
		this.codeList = codeList;
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

	public List getSpecList() {
		return specList;
	}

	public void setSpecList(List specList) {
		this.specList = specList;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}
