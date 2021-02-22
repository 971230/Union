package com.ztesoft.mall.core.action.backend;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.model.support.TypeSaveState;
import com.ztesoft.net.mall.core.service.IBrandManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 类型action 负责商品类型的添加和修改 <br/>
 * 负责类型插件相关业务管理
 * 
 * @author apexking
 * 
 */
public class TypeAction extends WWAction {

	private IGoodsTypeManager goodsTypeManager;
	private IBrandManager brandManager;
	private List brandlist;
	private List brandModelList;
	private List machineTypeList;
	private GoodsTypeDTO goodsType;

	/** ****用于属性信息的读取***** */
	private String[] propnames; // 参数名数组

	private int[] proptypes; // 属性类型数组

	private String[] options; // 可选值列表
	
	/** ****用于型号信息的读取***** */
	private String [] modelnames;//型号名
	private String [] modelcodes;//型号值

	/** ****用于参数信息的读取***** */
	private String paramnum; // 参数组中参数个数信息
	
	private String[] groupnames; // 参数组名数组
	private String[] paramnums;
	private String[] paramnames; // 参数名数组
	private String[] required;//是否必填
	private String[] enames;//参数英文名
	private String[] attrvaltypes;//参数取值类型
	private String[] attrtypes;//参数类型(attrtype)[商品参数(goodsparam)、关系参数（relparam)]
	private String[] attrcodes;//参数选择取值(attrcode)（下拉框需要展示该字段）
	private String[] attrdefvalues;//参数缺省值(attrdefvalue)

	private String type_id;
	private String brand_id;

	private int is_edit;

	private String[] id;// 清空商品类型使用

	// 用户选择关联的品牌
	private String[] chhoose_brands;

	private static String GOODSTYPE_SESSION_KEY = "goods_type_in_session";

	private static String GOODSTYPE_STATE_SESSION_KEY = "goods_type_state_in_session";

	private String order;
	
	private String source_from;
	
	private String name;
	
	private String type;
	
	private String ctx;
	
	private String cat_id;
	
	private String goods_id;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String checkname(){
	
		 if(this.goodsTypeManager.checkname( this.goodsType.getName(),goodsType.getType_id() )){
			 this.json="{result:1}"; //存在返回1
		 }else{
			 this.json="{result:0}";
		 }
		 return WWAction.JSON_MESSAGE;
	}
	
	
	public String list() {
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			this.webpage = this.goodsTypeManager.pageTypeECS(this.getName(), this.getType(), this.getOrder(), 
					this.getPage(), this.getPageSize());
			return "list_ecs";
		}
		else{
			this.webpage = this.goodsTypeManager.pageType(order, this.getPage(),this.getPageSize());
			return "list";
		}
		
	}
	
	public String listECS() {
		
		this.webpage = this.goodsTypeManager.pageTypeECS(this.getName(), this.getType(), this.getOrder(), 
				this.getPage(), this.getPageSize());
		
		return "list_ecs";
	}

	//
	public String trash_list() {

		this.webpage = this.goodsTypeManager.pageTrashType(order, this
				.getPage(), this.getPageSize());
		return "trash_list";
	}

	//查询类型详情
	public String serachTypeInfo(){
		goodsType = this.goodsTypeManager.get(type_id);
		return "typeinfo";
	}
	
	public String paramsConvert(){
		try {
			goodsTypeManager.convertParams();
		} catch (Exception e) {
		}
		return JSON_MESSAGE;
	}
	
	//
	public String step1() {
		//广东联通ECS，类型管理隐藏“属性”
		source_from = ManagerUtils.getSourceFrom();
		return "step1";
	}

	//
	public String step2() {

		// *步骤的状态，存在session中，在每步进行更改这个状态*//
		TypeSaveState saveState = new TypeSaveState();
		this.session.put(GOODSTYPE_STATE_SESSION_KEY, saveState);

		GoodsType tempType = getTypeFromSession();
		if (tempType == null) {

			this.session.put(GOODSTYPE_SESSION_KEY, goodsType); // 用页面上收集的信息
		} else { // 用于编辑的时候，先从session取出从数据库里读取的类型信息，然后根据页面收集用户选择的情况改变session中的信息。

			if (is_edit == 1) {
				tempType.setHave_parm(goodsType.getHave_parm());
				tempType.setHave_prop(goodsType.getHave_prop());
				tempType.setHave_price(goodsType.getHave_price());
				tempType.setHave_stock(goodsType.getHave_stock());
				tempType.setJoin_brand(goodsType.getJoin_brand());
				tempType.setIs_physical(goodsType.getIs_physical());
				tempType.setHave_field(goodsType.getHave_field());
				tempType.setName(goodsType.getName());
				tempType.setType(goodsType.getType());
			} else {
				this.session.put(GOODSTYPE_SESSION_KEY, goodsType);
			}
		}

		String result = getResult();
		if (result == null) {
			this.renderText("参数不正确！");
		}
		return result;
	}

	/**
	 * 编辑类型
	 * 
	 * @return
	 */
	public String edit() {
		source_from = ManagerUtils.getSourceFrom();
		this.goodsType = this.goodsTypeManager.get(type_id);
		this.session.put(GOODSTYPE_SESSION_KEY, goodsType);
		this.is_edit = 1;
		return "edit";
	}
	
	public String editProps(){
		this.goodsType = this.goodsTypeManager.get(type_id);
		this.is_edit = 1;
		return "edit_props";
	}
	
	public String editParams(){
		this.goodsType = this.goodsTypeManager.get(type_id);
		this.is_edit = 1;
		return "edit_params";
	}
	
	/**
	 * 保存属性信息
	 * 
	 * @return
	 */
	public String savePropsEdit() {

		String props = this.goodsTypeManager.getPropsString(propnames,
				proptypes, options);
		GoodsType tempType = this.goodsTypeManager.getGoodsType(type_id, "goods");
		if(!StringUtils.isEmpty(props) && !"[]".equals(props)){
			tempType.setHave_prop(1);
			tempType.setProps(props);
		}
		this.goodsTypeManager.saveProps(tempType);
		this.json = "{'result':0,'message':'操作成功!','type_id':"+type_id+"}";
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveParamsEdit(){
//		if (paramnum != null) {
//			if (paramnum.indexOf(",-1") >= 0) {// 检查是否有删除的参数组
//				paramnum = paramnum.replaceAll(",-1", "");
//			}
//			paramnums = paramnum.split(",");
//		}
		String params = this.goodsTypeManager.getParamString(paramnums,groupnames, paramnames, null,enames,attrvaltypes,
								attrtypes,attrcodes,attrdefvalues,required);
		GoodsType tempType = this.goodsTypeManager.getGoodsType(type_id, "goods");
		if(!"[]".equals(params)){
			tempType.setHave_parm(1);
		}
		tempType.setParams(params);
		this.goodsTypeManager.saveProps(tempType);
		this.json = "{'result':0,'message':'操作成功!','type_id':"+type_id+"}";
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 保存参数信息
	 * 
	 * @return
	 */
	public String saveParams() {
		String[] paramnums = new String[] {};
		if (paramnum != null) {
			if (paramnum.indexOf(",-1") >= 0) {// 检查是否有删除的参数组
				paramnum = paramnum.replaceAll(",-1", "");
			}
			paramnums = paramnum.split(",");
		}
		String params = this.goodsTypeManager.getParamString(paramnums,groupnames, paramnames, null,enames,attrvaltypes,
								attrtypes,attrcodes,attrdefvalues,required);
		GoodsType tempType = getTypeFromSession();
		TypeSaveState saveState = getSaveStateFromSession();
		tempType.setParams(params);
		saveState.setDo_save_params(1);
		return getResult();
	}

	/**
	 * 保存属性信息
	 * 
	 * @return
	 */
	public String saveProps() {

		String props = this.goodsTypeManager.getPropsString(propnames,
				proptypes, options);
		GoodsType tempType = getTypeFromSession();
		tempType.setProps(props);

		// *标志流程属性保存状态为已保存*//
		TypeSaveState saveState = getSaveStateFromSession();
		saveState.setDo_save_props(1);
		return getResult();
	}
	

	/**.
	 * 保存品牌数据
	 * 
	 * @return
	 */
	public String saveBrand() {
		GoodsType tempType = getTypeFromSession();
		tempType.setBrand_ids(this.chhoose_brands);

		// *标志流程品牌保存状态为已保存*//
		TypeSaveState saveState = getSaveStateFromSession();
		saveState.setDo_save_brand(1);
		return getResult();
	}
	
	/**
	 * 发布类型-广东联通ECS
	 * @return
	 */
	public String doPublish(){
		try{
			Map params = new HashMap();
			this.goodsTypeManager.doPublish(params);
			this.json = "{'result':0,'message':'发布成功!'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':1,'message':'发布失败!'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	

	public String save() {
		String havePrice=goodsType.getHave_price();
		String haveStock=goodsType.getHave_stock();
		GoodsTypeDTO tempType = getTypeFromSession();
		tempType.setDisabled(0);
		tempType.setBrandList(null);
		tempType.setPropList(null);
		tempType.setParamGroups(null);
		
		this.type_id =this.goodsTypeManager.save(tempType);
		this.session.remove(GOODSTYPE_SESSION_KEY);
		
		//没有自定义商品字段直接保存,并提示
		if(tempType.getHave_field() ==0){
			this.msgs.add("商品类型保存成功");
			this.urls.put("商品类型列表", "type!list.do");	
			return WWAction.MESSAGE;
		}else{//定义了商品字段，到商品字段定义页面
			return "field";	
		}
		
	}

	
	
	//
	private GoodsTypeDTO getTypeFromSession() {
		Object obj = this.session.get(GOODSTYPE_SESSION_KEY);

		if (obj == null) {
			// this.renderText("参数不正确");
			return null;
		}

		GoodsTypeDTO tempType = (GoodsTypeDTO) obj;

		return tempType;
	}

	//
	/**
	 * 当前流程的保存状态
	 * 
	 * @return
	 */
	private TypeSaveState getSaveStateFromSession() {

		// *从session中取出收集的类型数据*//
		Object obj = this.session.get(GOODSTYPE_STATE_SESSION_KEY);
		if (obj == null) {
			this.renderText("参数不正确");
			return null;
		}
		TypeSaveState tempType = (TypeSaveState) obj;
		return tempType;
	}

	//
	/**
	 * 根据流程状态以及在第一步时定义的
	 * 
	 * @return
	 */
	private String getResult() {

		GoodsType tempType = getTypeFromSession();
		this.goodsType = getTypeFromSession();
		TypeSaveState saveState = getSaveStateFromSession();
		String result = null;

		if (tempType.getHave_prop() != 0 && saveState.getDo_save_props() == 0) { // 用户选择了此类型有属性，同时还没有保存过
			result = "add_props";
		} else if (tempType.getHave_parm() != 0
				&& saveState.getDo_save_params() == 0) { // 用户选择了此类型有参数，同时还没有保存过
			result = "add_parms";
		}
		else if (tempType.getJoin_brand() != 0
				&& saveState.getDo_save_brand() == 0) { // 用户选择了此类型有品牌，同时还没有保存过
			brandlist= this.brandManager.list();
			result = "join_brand";
		}
		else {

			result = save();
		}

		return result;
	}

	/**
	 * 将商品类型放入回收站
	 * 
	 * @return
	 */
	public String delete() {
		try {
			int result  = goodsTypeManager.delete(id);
			if(result ==1)
			this.json = "{'result':0,'message':'删除成功'}";
			else
			this.json ="{'result':1,'message':'此类型存在与之关联的商品或类别，不能删除。'}";
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 清空商品类型
	 * 
	 * @return
	 */
	public String clean() {
		try {
			goodsTypeManager.clean(id);
			this.json = "{'result':0,'message':'清除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'清除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 从回收站中还原商品类型
	 * 
	 * @return
	 */
	public String revert() {
		try {
			goodsTypeManager.revert(id);
			this.json = "{'result':0,'message':'还原成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'还原失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	private List attrList;// 某个类型的属性定义列表
	private ParamGroup[] paramAr; // 某个类型的参数定义列表
	private List paramList;
	//

	// 读取某个类型下的商品属性定义并形成输入html
	// 被ajax抓取用
	public String disPropsInput() {
		attrList = this.goodsTypeManager.getAttrListByTypeId(type_id);
		attrList =attrList==null || attrList.isEmpty() ?null:attrList;
		return "props_input"; 
	}
	
	public String disPropsInputN() {
		this.ctx = ThreadContextHolder.getHttpRequest().getContextPath();
		attrList = this.goodsTypeManager.getAttrListByTypeId(type_id);
		attrList =attrList==null || attrList.isEmpty() ?null:attrList;
		List tempList = null;
		List subAttrList = new ArrayList();
		tempList = attrList;
		attrList = new ArrayList();
		if(tempList != null){
			int j=1;
			for(int i=0;i<tempList.size();i++){
				Attribute attr = (Attribute) tempList.get(i);
				subAttrList.add(attr);
				if(j%3==0 || j==tempList.size()){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("subAttrList", subAttrList);
					attrList.add(map);
					subAttrList = new ArrayList();
				}
				j++;
			}
		}
		if(attrList.size()==0){
			attrList = null;
		}
		return "props_inputn"; 
	}

	//
	// 读取某个类型下的商品参数定义并形成输入html
	// 被ajax抓取用
	public String disParamsInput() {
		this.paramAr = this.goodsTypeManager.getParamArByTypeId(type_id);
		return "params_input";
	}
	
	public String disParamsInputN() {
		this.ctx = ThreadContextHolder.getHttpRequest().getContextPath();
		this.paramAr = this.goodsTypeManager.getParamArByTypeId(type_id);
		paramList = new ArrayList();
		List subParamList = new ArrayList();
		Map<String,Object> map = null;
		if(this.paramAr != null){
			for(ParamGroup group : paramAr){
				List<GoodsParam> list = group.getParamList();
				int j=1;
				int line = 1;
				int paramNum = list.size();
				for(GoodsParam param : list){
					subParamList.add(param);
					if(j%3==0 || j==paramNum){
						map = new HashMap<String,Object>();
						if(line==1){
							map.put("showGroupName", "1");
						}
						map.put("subParamList", subParamList);
						map.put("name", group.getName());
						map.put("paramNum", paramNum);
						paramList.add(map);
						subParamList = new ArrayList();
						line++;
					}
					j++;
				}
			}
		}
		if(paramList.size()==0){
			paramList = null;
		}
		return "params_inputn";
	}

	public String listBrandSelector(){
		this.webpage = this.goodsTypeManager.listAllBrand(this.getPage(),this.getPageSize());
		return "brand_selector";
	}

	//添加或修改商品时异步读取品牌列表
	public String listBrand(){
		this.brandlist = this.goodsTypeManager.listByTypeId(type_id);
		return "brand_list";
	}	
	
	//添加或修改货品一步读取货品型号
	public String listBrandModel(){
		this.brandModelList = this.goodsTypeManager.listByBrandId(brand_id);
		return "model_list";
	}
	
	//添加或修改货品异步读取机型
	public String listMachineType(){
		this.machineTypeList = this.goodsTypeManager.listByBrandId(brand_id);
		return "machine_list";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAllGoodsInfo() throws Exception{
		List types = this.goodsTypeManager.getAllGoodsTypes();
		List cats = this.goodsTypeManager.getAllGoodsCats();
		List goods = this.goodsTypeManager.getAllGoods();
		
		Map retMap = new HashMap();
		
		retMap.put("types", types);
		retMap.put("cats", cats);
		retMap.put("goods", goods);
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAllGoodsTypes() throws Exception{
		List types = this.goodsTypeManager.getAllGoodsTypes();
		
		Map retMap = new HashMap();
		
		retMap.put("types", types);

		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGoodsCatsByTypeid() throws Exception{
		List cats = this.goodsTypeManager.getGoodsCatsByTypeid(this.type_id);
		
		Map retMap = new HashMap();
		
		retMap.put("cats", cats);
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGoodsByCatid() throws Exception{
		List goods = this.goodsTypeManager.getGoodsByCatid(this.cat_id);
		
		Map retMap = new HashMap();
		
		retMap.put("goods", goods);
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGoodsByGoodsid() throws Exception{
		List goods = this.goodsTypeManager.getGoodsByGoodsid(this.goods_id);
		
		Map retMap = new HashMap();
		
		retMap.put("goods", goods);
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}

	public List getAttrList() {
		return attrList;
	}

	public void setAttrList(List attrList) {
		this.attrList = attrList;
	}

	public ParamGroup[] getParamAr() {
		return paramAr;
	}

	public void setParamAr(ParamGroup[] paramAr) {
		this.paramAr = paramAr;
	}

	public List getParamList() {
		return paramList;
	}

	public void setParamList(List paramList) {
		this.paramList = paramList;
	}

	public GoodsTypeDTO getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsTypeDTO goodsType) {
		this.goodsType = goodsType;
	}

	public String[] getPropnames() {
		return propnames;
	}

	public void setPropnames(String[] propnames) {
		this.propnames = propnames;
	}

	public int[] getProptypes() {
		return proptypes;
	}

	public void setProptypes(int[] proptypes) {
		this.proptypes = proptypes;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

	public String[] getGroupnames() {
		return groupnames;
	}

	public void setGroupnames(String[] groupnames) {
		this.groupnames = groupnames;
	}

	public String[] getParamnums() {
		return paramnums;
	}

	public void setParamnums(String[] paramnums) {
		this.paramnums = paramnums;
	}

	public String[] getParamnames() {
		return paramnames;
	}

	public void setParamnames(String[] paramnames) {
		this.paramnames = paramnames;
	}

	public String getParamnum() {
		return paramnum;
	}

	public void setParamnum(String paramnum) {
		this.paramnum = paramnum;
	}

	public String[] getRequired() {
		return required;
	}

	public void setRequired(String[] required) {
		this.required = required;
	}

	public String[] getChhoose_brands() {
		return chhoose_brands;
	}

	public void setChhoose_brands(String[] chhoose_brands) {
		this.chhoose_brands = chhoose_brands;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public int getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(int is_edit) {
		this.is_edit = is_edit;
	}


	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public void setBrandManager(IBrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public List getBrandlist() {
		return brandlist;
	}

	public void setBrandlist(List brandlist) {
		this.brandlist = brandlist;
	}

	public String[] getEnames() {
		return enames;
	}

	public void setEnames(String[] enames) {
		this.enames = enames;
	}

	public String[] getAttrvaltypes() {
		return attrvaltypes;
	}

	public void setAttrvaltypes(String[] attrvaltypes) {
		this.attrvaltypes = attrvaltypes;
	}

	public String[] getAttrtypes() {
		return attrtypes;
	}

	public void setAttrtypes(String[] attrtypes) {
		this.attrtypes = attrtypes;
	}

	public String[] getAttrcodes() {
		return attrcodes;
	}

	public void setAttrcodes(String[] attrcodes) {
		this.attrcodes = attrcodes;
	}

	public String[] getAttrdefvalues() {
		return attrdefvalues;
	}

	public void setAttrdefvalues(String[] attrdefvalues) {
		this.attrdefvalues = attrdefvalues;
	}

	public String[] getModelnames() {
		return modelnames;
	}

	public void setModelnames(String[] modelnames) {
		this.modelnames = modelnames;
	}

	public String[] getModelcodes() {
		return modelcodes;
	}

	public void setModelcodes(String[] modelcodes) {
		this.modelcodes = modelcodes;
	}

	public List getBrandModelList() {
		return brandModelList;
	}

	public void setBrandModelList(List brandModelList) {
		this.brandModelList = brandModelList;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public List getMachineTypeList() {
		return machineTypeList;
	}

	public void setMachineTypeList(List machineTypeList) {
		this.machineTypeList = machineTypeList;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	
}
