package com.ztesoft.net.mall.plugin.standard.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.processor.core.freemarker.RepeatDirective;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/**
 * 类型管理插件
 * @author kingapex
 *
 */
public class GoodsTypeAdminPlugin extends AbstractGoodsPlugin{

	private IGoodsCatManager goodsCatManager;
	private String parent_id;
	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


	@Override
	public void addTabs() {
		this.addTags(2, "属性");
		this.addTags(3, "参数");
	}


	private IGoodsTypeManager goodsTypeManager;

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)  {
		// 处理参数信息
		String[] paramnums = request.getParameterValues("paramnums");
		String[] groupnames = request.getParameterValues("groupnames");
		String[] paramnames = request.getParameterValues("paramnames");
		String[] paramvalues = request.getParameterValues("paramvalues");
		String[] enames = request.getParameterValues("ename");
		String[] attrCodes = request.getParameterValues("attrcode");
		String[] attrvaltypes = request.getParameterValues("attrvaltype");
		String[] attrtypes = request.getParameterValues("attrtype");
		String[] required = request.getParameterValues("required");
		
		String params = goodsTypeManager.getParamString(paramnums, groupnames,
				paramnames, paramvalues, enames, attrvaltypes, attrtypes, attrCodes,required);
		goods.put("params", params);
		
		//广东联通ECS，当货品类型是套餐时，stype_id字段保存套餐档次
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			if(enames!=null && enames.length>0 && Consts.OFFER_ID.equals(goods.get("type_id"))){
				for(int i=0;i<enames.length;i++){
					String ename = enames[i];
					if(Consts.MONTH_FEE.equals(ename)){
						goods.put("stype_id", paramvalues[i]);
					}
				}
			}
		}
		
		String[] propvalues= request.getParameterValues("propvalues");  // 值数组

		try{
			String goods_id  =(String) goods.get("goods_id");
			 saveProps(goods_id,propvalues);	
		}
		catch(NumberFormatException e){
			throw new  RuntimeException("商品id格式错误");
		}			
	}
	
	
	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
		
		// 处理参数信息
		//增加request.getAttribute获取参数，广东联通ECS商品导入
		String[] paramnums = request.getParameterValues("paramnums")==null?(request.getAttribute("paramnums")==null?null:(String[])request.getAttribute("paramnums")):request.getParameterValues("paramnums");
		String[] groupnames = request.getParameterValues("groupnames")==null?(request.getAttribute("groupnames")==null?null:(String[])request.getAttribute("groupnames")):request.getParameterValues("groupnames");;
		String[] paramnames = request.getParameterValues("paramnames")==null?(request.getAttribute("paramnames")==null?null:(String[])request.getAttribute("paramnames")):request.getParameterValues("paramnames");;
		String[] paramvalues = request.getParameterValues("paramvalues")==null?(request.getAttribute("paramvalues")==null?null:(String[])request.getAttribute("paramvalues")):request.getParameterValues("paramvalues");;
		String[] enames = request.getParameterValues("ename")==null?(request.getAttribute("ename")==null?null:(String[])request.getAttribute("ename")):request.getParameterValues("ename");;
		String[] attrCodes = request.getParameterValues("attrcode")==null?(request.getAttribute("attrcode")==null?null:(String[])request.getAttribute("attrcode")):request.getParameterValues("attrcode");;
		String[] attrvaltypes = request.getParameterValues("attrvaltype")==null?(request.getAttribute("attrvaltype")==null?null:(String[])request.getAttribute("attrvaltype")):request.getParameterValues("attrvaltype");;
		String[] attrtypes = request.getParameterValues("attrtype")==null?(request.getAttribute("attrtype")==null?null:(String[])request.getAttribute("attrtype")):request.getParameterValues("attrtype");
		String[] required = request.getParameterValues("required")==null?(request.getAttribute("required")==null?null:(String[])request.getAttribute("required")):request.getParameterValues("required");;
		
		String params = goodsTypeManager.getParamString(paramnums, groupnames,
				paramnames, paramvalues, enames, attrvaltypes, attrtypes,attrCodes,required);
		
		//添加params参数为空判断，防止定时任务导入商品货品时已经组装的参数被覆盖--add by zou.qinghua
		if(StringUtils.isEmpty(Const.getStrValue(goods,"params"))){
			goods.put("params", params);
		}
		
		//广东联通ECS，当货品类型是套餐时，stype_id字段保存套餐档次
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			if(enames!=null && enames.length>0 && Consts.OFFER_ID.equals(goods.get("type_id"))){
				for(int i=0;i<enames.length;i++){
					String ename = enames[i];
					if(Consts.MONTH_FEE.equals(ename)){
						goods.put("stype_id", paramvalues[i]);
					}
				}
			}
		}
		
		List typeList = goodsTypeManager.listAll();
		
		request.setAttribute("typeList", typeList);
		 
	}

	
	@Override
	public String onFillGoodsAddInput(HttpServletRequest req) {
		String type = (String) req.getAttribute("type");//广东联通ECS添加，区分商品货品
		List typeList = this.goodsTypeManager.listAll();
		List catList = goodsCatManager.listAllChildrenByAgentNo("0"); //add by wui根据工号过滤
		List stypeList = this.goodsTypeManager.childListStype(String.valueOf(-1));
		List childStypeList=this.goodsTypeManager.childListStype(parent_id);
		List newCatList = new ArrayList();//货品或商品分类
		HttpServletRequest request   = ThreadContextHolder.getHttpRequest();

		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();// new FreeMarkerPaser(getClass(),"/plugin/type");
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("type_input_ecs");
			if(!StringUtils.isEmpty(type)){
				for(int i=0;i<catList.size();i++){//根据type过滤货品或商品分类
					Cat cat = (Cat) catList.get(i);
					if(type.equals(cat.getType())){
						newCatList.add(cat);
					}
				}
			}
			freeMarkerPaser.putData("catList", newCatList);
		}
		else{
			freeMarkerPaser.setPageName("type_input");
			freeMarkerPaser.putData("catList", catList);
		}
		freeMarkerPaser.putData("typeList", typeList);
		freeMarkerPaser.putData("stypeList", stypeList);
		freeMarkerPaser.putData("childListStype", childStypeList);
		
		freeMarkerPaser.putData("is_edit", false);
		freeMarkerPaser.putData("repeat", new RepeatDirective());
		freeMarkerPaser.putData("type", type);
		freeMarkerPaser.putData("founder", ManagerUtils.getAdminUser().getFounder()+"");
		String catid = request.getParameter("catid");
		if(!StringUtil.isEmpty(catid)){
			freeMarkerPaser.putData("catid",Integer.valueOf(catid));
		}
		return freeMarkerPaser.proessPageContent();
	}
	
 
	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request)  {
		 
		
	}
	

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)  {
		
		 String[] propvalues= request.getParameterValues("propvalues");  // 值数组

			try{
				String goods_id  =  (String)goods.get("goods_id");
				saveProps(goods_id,propvalues);	
			}
			catch(NumberFormatException e){
				/*Integer 最大值为 2147483647
				 * 商品ID为：201304090000000233
				 * 一定会触发异常
				 * */
				throw new  RuntimeException("商品id格式错误");
			}
	}
		

	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		String type = (String) request.getAttribute("type");//广东联通ECS添加，区分商品货品
		List typeList = this.goodsTypeManager.listAll();
		List stypeList = this.goodsTypeManager.listStype();
        String stype_id=(goods.get("stype_id")==null?"":String.valueOf(goods.get("stype_id")));
        List childListStype=this.goodsTypeManager.childListStype(stype_id);
		//读取参数信息
        ParamGroup[] paramAr1 = null;//类型规格参数
        ParamGroup[] paramAr2 = null;//商品货品参数
        ParamGroup[] paramAr = null;//商品货品参数
		String params  = goods.get("params")==null ? "[]" : goods.get("params").toString();
		//如果商品货品参数未填，取类型的参数，避免修改商品货品出现“该类型未定义参数”导致不能修改
		
		String type_id = (String) goods.get("type_id");
		GoodsTypeDTO dto = goodsTypeManager.get(type_id);
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			paramAr1 = dto.getParamGroups();
			paramAr2 = GoodsTypeUtil.converFormString( params);// 处理参数
			paramAr = processParamData(paramAr1,paramAr2);//把商品参数值复制到商品类型规格参数中
		}
		else{
			paramAr = GoodsTypeUtil.converFormString( params);
		}
		getDropdownData(paramAr);
		//读取属性信息
		Map propMap = new HashMap();
		for(int i=0;i<20;i++){
			String value = goods.get("p" + (i+1))==null ? "" : goods.get("p" + (i+1)).toString();
			propMap.put("p"+i,value);
		}
		goods.put("propMap",propMap);
		
		List propList  = proessProps(goods);
		List catList = goodsCatManager.listAllChildren("0");
		List newCatList = new ArrayList();//货品或商品分类
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("type_input_ecs");
			if(!StringUtils.isEmpty(type)){
				for(int i=0;i<catList.size();i++){//根据type过滤货品或商品分类
					Cat cat = (Cat) catList.get(i);
					if(type.equals(cat.getType())){
						newCatList.add(cat);
					}
				}
			}
			
			freeMarkerPaser.putData("catList", newCatList);
		}
		else{
			freeMarkerPaser.setPageName("type_input");
			freeMarkerPaser.putData("catList", catList);
		}
		freeMarkerPaser.putData("attrList", propList);
		freeMarkerPaser.putData("typeList", typeList);
		freeMarkerPaser.putData("stypeList", stypeList);
        freeMarkerPaser.putData("childListStype",childListStype);//子服务类型
		freeMarkerPaser.putData("paramAr", paramAr);
		freeMarkerPaser.putData("is_edit", true);
		freeMarkerPaser.putData("founder", ManagerUtils.getAdminUser().getFounder()+"");
		freeMarkerPaser.putData("repeat", new RepeatDirective());
		freeMarkerPaser.putData("type", type);
		return freeMarkerPaser.proessPageContent();
	}
	
	public ParamGroup[] processParamData(ParamGroup[] paramAr1, ParamGroup[] paramAr2){
		if(paramAr2!=null && paramAr2.length>0){
			for(ParamGroup typeParamGrp:paramAr1){
				String groupName1 = typeParamGrp.getName();
				for(ParamGroup goodParamGrp:paramAr2){
					String groupName2 = goodParamGrp.getName();
					if(groupName1.equals(groupName2)){
						List<GoodsParam> typeparams = typeParamGrp.getParamList();
						List<GoodsParam> goodparams = goodParamGrp.getParamList();
						for(GoodsParam typeparam : typeparams){
							for(GoodsParam goodparam : goodparams){
								if(goodparam.getEname().equals(typeparam.getEname())){
									typeparam.setValue(goodparam.getValue());
								}
							}
						}
					}
				}
			}
		}
		return paramAr1;
	}
	
	public void getDropdownData(ParamGroup[] paramAr){
		if(paramAr==null)
			return ;
		for(int i=0;i<paramAr.length;i++){
			ParamGroup group = paramAr[i];
			List<GoodsParam> params = group.getParamList();
			for(int j=0;j<params.size();j++){
				GoodsParam param = params.get(j);
				String attrCode = param.getAttrcode();
				String attrvaltype = param.getAttrvaltype();
				//zengxianlian
				//if("1".equals(attrvaltype) && !StringUtils.isEmpty(attrCode)){
				if(!StringUtils.isEmpty(attrCode)){
					List datas = this.goodsTypeManager.getDropdownData(attrCode);
					param.setDropdownValues(datas);
				}
			}
		}
	}

	
	// 处理属性信息
	private List proessProps(Map goodsView) {
		
		if( goodsView.get("type_id")==null ) {
			throw new  RuntimeException("类型id为空");
		}
		String goods_id ="";
		try{
			 goods_id  = goodsView.get("type_id").toString();
		}catch(NumberFormatException e){
			throw new  RuntimeException("类型不为数字");
		}
		List  propList = this.goodsTypeManager.getAttrListByTypeId( goods_id );
		if(propList==null) return propList;
		Map<String, String> propMap = (Map)goodsView.get("propMap");
		for (int i = 0; i < propList.size(); i++) {
			Attribute attribute = (Attribute) propList.get(i);
			String value = propMap.get("p" + i);
			attribute.setValue(value);
		}
		return propList;
	}
	
	
	/**
	 * 商品添加时处理相关的属性信息 是添加完后更新, 因为可能存在 有p1 到p20 个字段,所以更新要比较添加方便
	 * 
	 * @param goodsid
	 * @param propvalues
	 */
	private void saveProps(String goodsid, String[] propvalues) {
		if(propvalues!=null && propvalues.length>0){
			HashMap fields = new HashMap();
			int length = propvalues.length;
			length = length > 20 ? 20 : length; // 只支持20个自定义属性

			// 循环所有属性,按p_个数 为字段名存在 goods表中
			// 字段中存的是 值,当是下拉框时也存的是值,并不是属性的id
			for (int i = 0; i < length; i++) {
				String value = propvalues[i];
				fields.put("p" + (i + 1), value);
			}

			// 更新这个商品的属性信息
			this.daoSupport.update("es_goods", fields, "goods_id=" + goodsid);
			
		}

	}
	


	@Override
	public void perform(Object... params) {

	}
	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {
		return "goodstype";
	}

	@Override
	public String getName() {
		return "商品类型插件";
	}

	@Override
	public String getType() {
		
		return null;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

 

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}


	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}


	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}



	
}
