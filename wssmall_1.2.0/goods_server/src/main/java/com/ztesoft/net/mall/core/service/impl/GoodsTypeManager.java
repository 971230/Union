package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.GoodsStype;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.TypeParams;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.service.IGoodsFieldManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.plugin.standard.type.GoodsTypeUtil;
import com.ztesoft.net.sqls.SF;

/**
 * 商品类型管理
 * @author kingapex
 * 2010-1-10下午05:53:40
 */
public class GoodsTypeManager extends BaseSupport<GoodsType> implements IGoodsTypeManager {
	private static final Log loger = LogFactory.getLog(GoodsTypeManager.class  );
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private IGoodsFieldManager goodsFieldManager;
	private GoodsNetCacheRead read = new GoodsNetCacheRead();
	
	@Override
	public List listAll() {
		String sql = SF.goodsSql("GOODS_TYPE_BY_0");
		List typeList = this.baseDaoSupport.queryForList(sql,GoodsType.class);
		
		return typeList;
	}
	
	@Override
	public List ListAllGoodTypes(){
		String sql = SF.goodsSql("GOODS_TYPE_BY_0_0");
		List typeList = this.baseDaoSupport.queryForList(sql,GoodsType.class);
		
		return typeList;
	}
	
	@Override
	public List listAllProduct() {
		String sql = SF.goodsSql("GOODS_TYPE_BY_0_1");
		List typeList = this.baseDaoSupport.queryForList(sql,GoodsType.class);
		return typeList;
	}
	
	@Override
	public List listStype(){
		String sql = SF.goodsSql("LIST_STYPE");
		
		List stypeList = this.baseDaoSupport.queryForList(sql,GoodsStype.class);
		
		return stypeList;
	}
	@Override
	public List childListStype(String parent_id) {
		
		String sql = SF.goodsSql("CHILD_LIST_STYPE");
		List childStypeList=this.baseDaoSupport.queryForList(sql, GoodsStype.class,parent_id);
		
		return childStypeList;
	}
	/**
	 * 商品类型列表
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	
	@Override
	public Page pageType(String order, int page, int pageSize) {
		order  = order==null?" type_id desc":order;
		
		String sql  = SF.goodsSql("PAGE_TYPE_0")  + "order by " + order;
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	@Override
	public Page pageTypeECS(String name, String type, String order, int page, int pageSize) {
		
		order  = order==null?" type_id desc":order;
		
		String sql  = SF.goodsSql("PAGE_TYPE_0");
		if (!"".equals(name) && null != name) {
			sql += "  and name like '%" + name + "%'";
		}
		if (!"".equals(type) && null != type) {
			sql += "  and type = '" + type + "'";
		}
		
		sql += "order by " + order;
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	/**
	 * 商品类型回收站列表
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page pageTrashType(String order,int page,int pageSize){
		order  = order==null?" type_id desc":order;
		
		String sql  = SF.goodsSql("PAGE_TYPE_1") + " order by " + order;
		
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	
	
	
	/**
	 * 读取一个类型的信息
	 * @param type_id
	 * @return
	 */	
	
	@Override
	public GoodsTypeDTO get(String type_id) {
		if(StringUtils.isEmpty(type_id)) return null;
		String sql = SF.goodsSql("GOODS_TYPE_BY_ID");
		GoodsTypeDTO type=(GoodsTypeDTO) baseDaoSupport.queryForObject(sql, GoodsTypeDTO.class, type_id);//(sql, new GoodsTypeMapper(), type_id);该DTO继承商品类型对象，因为要转换类型的属性、参数、品牌值并返回
		if(type==null){
			return null;
		}
		
		IStoreProcesser netBlog1 = StoreProcesser.getProcesser("ES_GOODS_TYPE","PROPS",ManagerUtils.getSourceFrom(),type.getProps());
		Object obj1 = netBlog1.getFileUrl(type.getProps());
		List<Attribute> propList = GoodsTypeUtil.converAttrFormString((String)obj1);
		
		IStoreProcesser netBlog2 = StoreProcesser.getProcesser("ES_GOODS_TYPE","PARAMS",ManagerUtils.getSourceFrom(),type.getParams());
		Object obj2 = netBlog2.getFileUrl(type.getParams());			
		ParamGroup[] paramGroups  = GoodsTypeUtil.converFormString((String)obj2);
		
		List brandList  = this.getBrandListByTypeId(type_id);
		type.setPropList(propList);
		type.setParamGroups(paramGroups);
		type.setBrandList(brandList);
		return type;
	}
	@Override
	public List<GoodsType> getGoodsTypeList() {
		String sql = SF.goodsSql("GET_GOODS_TYPE_LIST");
		List<GoodsType> listGoodsType=baseDaoSupport.queryForList(sql, GoodsTypeDTO.class,0);
		return listGoodsType;
	}
	
	@Override
	public String getTagName(Integer tag_id) {
		String sql = SF.goodsSql("GET_TAG_NAME");
		if (EopSetting.DBTYPE.equals("1")) {
			sql += " limit 1";
		} else if (EopSetting.DBTYPE.equals("2")) {
			sql += " and rownum=1";
		}
		return baseDaoSupport.queryForString(sql, tag_id);
	}
	
	
	
	/**
	 * 读取某个类型关联的品牌
	 * 
	 * @param type_id
	 * @return
	 */
	@Override
	public List getBrandListByTypeId(String type_id) {
		String sql = SF.goodsSql("GET_BRAND_LIST_BY_TYPE_ID");

		List list = this.daoSupport.queryForList(sql,type_id);
		return list;
	}



	@Override
	public List listByTypeId(String typeid) {
		//String sql = SF.goodsSql("LIST_BY_TYPE_ID");
		String sql="select b.* from es_type_brand tb inner join es_brand b  on b.brand_id = tb.brand_id and tb.source_from=b.source_from where tb.type_id=? and b.disabled=0 AND b.source_from=?";
		//如果货品类型是附件产品和SP产品时，品牌类型默认只加载“中国联通” add by mo.chencheng 2016-04-20
		if("10050".equals(typeid) || "10051".equals(typeid)){
			sql = sql + " AND b.name = '中国联通'";
		}
		
		List list =	this.daoSupport.queryForList(sql,  typeid, ManagerUtils.getSourceFrom());
	 
		return list;
	}
	
	
	@Override
	public Page listAllBrand(int pageNo,int pageSize){
		String sql = SF.goodsSql("BRAND_LIST");
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
	}
 

	/**
	 * 将一个json字串转为list
	 * @param props
	 * @return
	 */
	public static List<Attribute> converAttrFormString(String props){
		if (StringUtils.isBlank(props)){
            return new ArrayList();
        }
        List<Attribute> list = JSON.parseArray(props,Attribute.class);
        return list;
	}
	
	
	
	/**
	 * 读取某个类型的属性信息定义
	 * 
	 * @param type_id
	 * @return
	 */
	@Override
	public List<Attribute> getAttrListByTypeId(String type_id) {
		GoodsTypeDTO type = this.get(type_id);
		if(type ==null || type.getHave_prop()==0) return new ArrayList<Attribute>();
		
		return type.getPropList();
	 
	}
	
	

	/**
	 * 读取某个类型的参数信息定义
	 * 
	 * @param type_id
	 * @return
	 */
	@Override
	public ParamGroup[] getParamArByTypeId(String type_id) {
		String params = getParamsByTypeId(type_id);
		ParamGroup[] groups = GoodsTypeUtil.converFormString(params);
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom()) && groups!=null){
			for(ParamGroup group : groups){
				List<GoodsParam> list = group.getParamList();
				for(GoodsParam param : list){
					//改用是否填写字典编码判断下拉框---zengxianlian
					//if("1".equals(param.getAttrvaltype())){
					if(!"".equals(param.getAttrcode())){
						String attr_code = param.getAttrcode();
						List datas = getDropdownData(attr_code);
						param.setDropdownValues(datas);
					}
				}
			}
		}
		return groups;

	}
	
 

	/**
	 * 读取某个类型的参数定义
	 * 
	 * @param type_id
	 * @return
	 */
	private String getParamsByTypeId(String type_id) {
		
		String sql = SF.goodsSql("GET_PARAMS_BY_TYPEID");
		IDaoSupport<String> strDaoSuport=(IDaoSupport)this.baseDaoSupport;
	 
		String props = 	strDaoSuport.queryForString(sql, type_id);
		
		IStoreProcesser netBlog1 = StoreProcesser.getProcesser("ES_GOODS_TYPE","PARAMS",ManagerUtils.getSourceFrom(),props);
		Object obj1 = netBlog1.getFileUrl(props);
		
		return (String)obj1;
	}

	/**
	 * 
	 * 将一个 Attribute 对象的List 生成Json字串,<br/>此字串将会保存到数据库goods_type表的props字段 List
	 * 是根据客户端页面用户输入的属性信息生成的
	 * 
	 * @param propnames
	 * @param proptypes
	 * @param options
	 * @return
	 */
	@Override
	public String getPropsString(String[] propnames, int[] proptypes,
			String[] options) {
		List list = toAttrList(propnames, proptypes, options);
		return JSON.toJSONString(list);
	}
	
	
	/**
	 * 将一个ParamGroup 对像的List 生成json字串 <br/> 此字串将会保存在数据库goods_type的params字段 或
	 * js_goods中的params字段
	 * 不同的是：goods_type字段中的参数信息无参数值信息，而js_goods表中的params字段有参数值信息。 <br/> List
	 * 是根据是根据客户端页面用户输入的参数信息生成的
	 * 
	 * @param paramnum
	 * @param groupnames
	 * @param paramnames
	 * @return
	 */
	@Override
	public String getParamString(String[] paramnums, String[] groupnames,
			String[] paramnames, String[] paramvalues,String[] enames,String[] attrvaltypes,String[] attrtypes,String[] attrCodes,String[] required) {
		List list = toParamList(paramnums, groupnames, paramnames, paramvalues, enames, attrvaltypes, attrtypes, attrCodes,required);
		return JSON.toJSONString(list);
	}

	/**
	 * 将一个ParamGroup 对像的List 生成json字串 <br/> 此字串将会保存在数据库goods_type的params字段 或
	 * js_goods中的params字段
	 * 不同的是：goods_type字段中的参数信息无参数值信息，而js_goods表中的params字段有参数值信息。 <br/> List
	 * 是根据是根据客户端页面用户输入的参数信息生成的
	 * 
	 * @param paramnum
	 * @param groupnames
	 * @param paramnames
	 * @return
	 */
	@Override
	public String getParamString(String[] paramnums, String[] groupnames,
			String[] paramnames, String[] paramvalues,String[] enames, String[] attrvaltypes,String[] attrtypes, String[] attrcodes,String[] attrdefvalues, String[] required) {
		List list = toParamList2(paramnums, groupnames, paramnames, paramvalues,enames,attrvaltypes,attrtypes,attrcodes,attrdefvalues,required);
		return JSON.toJSONString(list);
	}

	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public String save(GoodsType type) {
		String typeTableName ="es_goods_type";
		String tbTableName = "es_type_brand";
		String[] brand_id = type.getBrand_ids();

		type.setBrand_ids(null);
		if(type.getParams()!=null && type.getParams().equals("[]")){
			type.setParams(null);
		}
		String type_id=null;
		if(type.getType_id()!=null){
			type_id = type.getType_id();
			if(type.getHave_prop()==0){
				type.setProps(null);
			}
			if(type.getHave_parm()==0){
				type.setParams(null);
			}
			this.baseDaoSupport.update(typeTableName, type, "type_id="+ type_id);
			String sql = SF.goodsSql("TYPE_BRAND_DEL");
			this.simpleJdbcTemplate.update(sql,type_id);
			
		}else{ //新增
			baseDaoSupport.insert(typeTableName, type);
			type_id  = type.getType_id();
			if(loger.isDebugEnabled()){
				loger.debug("增加商品类型成功 , id is " + type_id);
			}
			
		}

		if (brand_id != null) {
			for (int i = 0; i < brand_id.length; i++) {
				 
				String sql = SF.goodsSql("TYPE_BRAND_INSERT");
				simpleJdbcTemplate.update(sql, type_id, brand_id[i],ManagerUtils.getSourceFrom());
			}
		}	
		
		return type_id;
	}

	@Override
	public void saveProps(GoodsType type){
		this.baseDaoSupport.update("es_goods_type", type, "type_id="+ type.getType_id());
	}

	/**
	 * 根据页面用户输入的信息形成 Attribute 对象的List
	 * 
	 * @param propnames
	 * @param proptypes
	 * @param options
	 * @return
	 */
	private List<Attribute> toAttrList(String[] propnames, int[] proptypes,
			String[] options) {
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(propnames!=null && proptypes!= null && options!= null){
			for (int i = 0; i < propnames.length; i++) {
	
				Attribute attribute = new Attribute();
				String name = propnames[i];
				String option = options[i];
				int type = proptypes[i];
	
				attribute.setName(name);
				attribute.setOptions(option);
				attribute.setType(type);
				attrList.add(attribute);
			}
		}
		return attrList;
	}
	

	/**
	 * 根据页面用户的参数信息 生成 ParamGroup 实体的List
	 * 
	 * @param paramnum
	 * @param groupnames
	 * @param paramnames
	 * @return
	 */
	private List<ParamGroup> toParamList(String[] ar_paramnum,
			String[] groupnames, String[] paramnames, String[] paramvalues,
			String[] enames,String[] attrvaltypes,String[] attrtypes,String[] attrCodes,String[] required) {

		List<ParamGroup> list = new ArrayList<ParamGroup>();
		if (groupnames != null) {
			for (int i = 0; i < groupnames.length; i++) {
				if(!StringUtils.isEmpty(groupnames[i])){
					ParamGroup paramGroup = new ParamGroup();
					paramGroup.setName(groupnames[i]);
					List<GoodsParam> paramList = getParamList(ar_paramnum,
							paramnames, paramvalues,enames,attrvaltypes,attrtypes,attrCodes,required, i);
					paramGroup.setParamList(paramList);
					list.add(paramGroup);
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据页面用户输入信息生成GoodsParam 对象的List,此list将被保存在相应的ParamGroup对象中 <br/>
	 * GoodsParam对象只有name 属性会被赋值,value属性不会被处理.
	 * 
	 * @param ar_paramnum
	 * @param paramnames
	 * @param index
	 * @return
	 */
	private List<GoodsParam> getParamList(String[] ar_paramnum,
			String[] paramnames, String[] paramvalues,String[] enames,String[] attrvaltypes,String[] attrtypes,String[] attrCodes,String[] required, int groupindex) {
		int[] pos = this.countPos(groupindex, ar_paramnum);
		List<GoodsParam> list = new ArrayList<GoodsParam>();
		for (int i = pos[0]; i < pos[1]; i++) {
			GoodsParam goodsParam = new GoodsParam();

			goodsParam.setName(paramnames[i]);

			if (paramvalues != null) {
				String value = paramvalues[i];
				goodsParam.setValue(value);
			}
			if(enames != null){
				String ename = enames[i];
				goodsParam.setEname(ename);
			}
			if(attrvaltypes !=null){
				String attrvaltype = attrvaltypes[i];
				goodsParam.setAttrvaltype(attrvaltype);
			}
			if(attrtypes != null){
				String attrtype = attrtypes[i];
				goodsParam.setAttrtype(attrtype);
			}
			if(attrCodes != null){
				String attrCode = attrCodes[i];
				goodsParam.setAttrcode(attrCode);
			}
			if(required != null){
				String req = required[i];
				goodsParam.setRequired(req);
			}

			list.add(goodsParam);
		}
		return list;
	}
	
	/**
	 * 根据页面用户的参数信息 生成 ParamGroup 实体的List
	 * 
	 * @param paramnum
	 * @param groupnames
	 * @param paramnames
	 * @return
	 */
	private List<ParamGroup> toParamList2(String[] ar_paramnum,
			String[] groupnames, String[] paramnames, String[] paramvalues,String[] enames, 
			String[] attrvaltypes,String[] attrtypes, String[] attrcodes,String[] attrdefvalues,String[] required) {

		List<ParamGroup> list = new ArrayList<ParamGroup>();
		if (groupnames != null) {
			for (int i = 0; i < groupnames.length; i++) {
				ParamGroup paramGroup = new ParamGroup();
				paramGroup.setName(groupnames[i]);
				List<GoodsParam> paramList = getParamList2(ar_paramnum,
						paramnames, paramvalues, i,enames,attrvaltypes,attrtypes,attrcodes,attrdefvalues,required);
				paramGroup.setParamList(paramList);
				list.add(paramGroup);
			}
		}
		return list;
	}

	/**
	 * 根据页面用户输入信息生成GoodsParam 对象的List,此list将被保存在相应的ParamGroup对象中 <br/>
	 * GoodsParam对象只有name 属性会被赋值,value属性不会被处理.
	 * 
	 * @param ar_paramnum
	 * @param paramnames
	 * @param index
	 * @return
	 */
	private List<GoodsParam> getParamList2(String[] ar_paramnum, String[] paramnames, String[] paramvalues, int groupindex,
			String[] enames, String[] attrvaltypes,String[] attrtypes, String[] attrcodes,String[] attrdefvalues,String[] required) {
		int[] pos = this.countPos(groupindex, ar_paramnum);
		List<GoodsParam> list = new ArrayList<GoodsParam>();
		for (int i = pos[0]; i < pos[1]; i++) {
			GoodsParam goodsParam = new GoodsParam();
			
			goodsParam.setName(paramnames[i]);
			goodsParam.setEname(enames[i]);
			goodsParam.setAttrvaltype(attrvaltypes[i]);
			goodsParam.setAttrtype(attrtypes[i]);
			if("1".equals(attrvaltypes[i])){//下拉框
				goodsParam.setAttrcode(attrcodes[i]);
			}else{//文本
				goodsParam.setAttrcode("");
			}
			goodsParam.setAttrdefvalue(attrdefvalues[i]);
			goodsParam.setRequired(required[i]);
			if (paramvalues != null) {
				String value = paramvalues[i];
				goodsParam.setValue(value);
			}

			list.add(goodsParam);
		}
		return list;
	}

	/**
	 * 计算 某个参数组 的参数 在 从页面传递过来的paramnames 数组的位置
	 * 
	 * @param groupindex
	 * @param ar_paramnum
	 * @return
	 */
	private int[] countPos(int groupindex, String[] ar_paramnum) {

		int index = 0;
		int start = 0;
		int end = 0;
		int[] r = new int[2];
		for (int i = 0; i <= groupindex; i++) {
			if(!StringUtils.isEmpty(ar_paramnum[i])){
				int p_num = Integer.valueOf(ar_paramnum[i]);

				index = index + p_num;
				if (i == groupindex - 1) { // 上一个参数组的参数 结束
					start = index;
				}

				if (i == groupindex) { // 当前的 本参数组的参数开始
					end = index;
				}
			}

		}

		r[0] = start;
		r[1] = end;

		return r;
	}
	
	
	
	/**
	 * 查询类型是否已经被类别关联
	 * @param type_ids
	 * @return
	 */
	private boolean checkUsed(Integer[] type_ids){
//		String sql="select count(0) from es_goods_cat where type_id in";
		return false;
	}
	
	/**
	 * 如果已经被使用返回0
	 * 删除成功返回1
	 */
	@Override
	public int delete(String[] type_ids) {
		
		if(type_ids==null) return 1;
		
		String ids = "";
		for (int i = 0; i < type_ids.length; i++) {
			if(i!=0)
				ids+=",";
			ids+=type_ids[i];
		}
//		String sql  = SF.goodsSql("GOODS_GET_BY_TYPE_ID") + " and type_id in ('" + ids.replaceAll(",", "','") + "')";
//		int count = this.daoSupport.queryForInt(sql);
//		
//		sql = SF.goodsSql("GOODS_CAT_COUNT") + " and type_id in ('" + ids.replaceAll(",", "','") + "')";
//		int catcout=this.baseDaoSupport.queryForInt(sql);
//		if(catcout>0){
//			return 0;
//		} 
		
//		if(count==0){
			String sql  = SF.goodsSql("GOODS_TYPE_UPDATE_BY_TYPE_ID") + " and type_id in ('" + ids.replaceAll(",", "','") + "')";
			this.baseDaoSupport.execute(sql) ;
			return 1;
//		}
		
	}

	
	
	
	/**
	 * 清空商品类型及其关联的品牌
	 * @param type_id
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void clean(String[] type_ids){
		if(type_ids==null) return ;
		String ids = "";
		for (int i = 0; i < type_ids.length; i++) {
			if(i!=0)
				ids+=",";
			ids+=type_ids[i];
		}
		String sql  = SF.goodsSql("GOODS_TYPE_DEL_BY_TYPE_ID") + " and type_id in(" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
		//sql="delete from "+this.getTableName("type_brand")+" where type_id in("+ids+")";
		//this.simpleJdbcTemplate.update(sql);
		
//		this.goodsFieldManager.delete(ids);
		
	}
		
	
	/**
	 * 将商品类型从回收站中还原
	 * @param type_ids
	 */
	@Override
	public void revert(String[] type_ids){
		
		if(type_ids==null) return ;
		String ids = "";
		for (int i = 0; i < type_ids.length; i++) {
			if(i!=0)
				ids+=",";
			ids+=type_ids[i];
		}
		String sql  = SF.goodsSql("GOODS_TYPE_UPDATE_0") + " and type_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
	}


	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
	@Override
	public boolean checkname(String name, String typeid) {
		if(name!=null)name=name.trim();
		String sql  = SF.goodsSql("CHECK_NAME");
		if(typeid==null) typeid= "0";
		int count = this.baseDaoSupport.queryForInt(sql, name,typeid);
		if(count>0)
			return true;
		else
			return false;
	}
	public IGoodsFieldManager getGoodsFieldManager() {
		return goodsFieldManager;
	}
	public void setGoodsFieldManager(IGoodsFieldManager goodsFieldManager) {
		this.goodsFieldManager = goodsFieldManager;
	}


	@Override
	public List listByBrandId(String brand_id) {
		String sql = SF.goodsSql("BRAND_MODEL_LIST") + " order by m.model_name desc";
		List datas = this.baseDaoSupport.queryForList(sql, brand_id);
		return datas;
	}
	
	@Override
	public GoodsType getGoodsType(String type_id, String type) {
		GoodsType goodsType = null;
		if(type.equals(Consts.ECS_QUERY_TYPE_GOOD)){
			goodsType = read.getCachesGoodsType(type_id);
		}
		else{
			goodsType = read.getCachesProductType(type_id);
		}
		if(goodsType==null || StringUtils.isEmpty(goodsType.getType_id())){
			String sql = SF.goodsSql("GOODS_TYPE_BY_0")+" and type_id=? ";
			goodsType = this.baseDaoSupport.queryForObject(sql, GoodsType.class, type_id);
		}
		return goodsType;
	}


	@Override
	public List getDropdownData(String attrCode) {
		List datas = null;
		List<Map> sqls = this.baseDaoSupport.queryForList(SF.goodsSql("ATTR_CODE_GET"), attrCode);
		if(sqls!=null && sqls.size()>0){
			Map sql = sqls.get(0);
			datas = this.baseDaoSupport.queryForList(sql.get("dc_sql").toString());
		}
		return datas;
	}
	
	@Override
	public void convertParams() {
		this.baseDaoSupport.execute("delete from es_type_param ");
		String sql = SF.goodsSql("TYPE_PARAMS_LIST");
		List<Map> params = this.baseDaoSupport.queryForList(sql);
		if(params!=null && params.size()>0){
			for(Map param:params){
				String type_id = (String) param.get("type_id");
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
						String value_desc = null;
						
						TypeParams typeParam = new TypeParams();
						typeParam.setType_id(type_id);
						typeParam.setParam_code(ename);
						typeParam.setParam_name(name);
						typeParam.setParam_value_code(value);
						typeParam.setParam_value_desc(value_desc);
						this.baseDaoSupport.insert("es_type_param", typeParam);
					}
				}
			}
		}
	}

	@Override
	public void doPublish(Map params) {
		String org_id_str = ManagerUtils.getStrValue(params, "orgIds");
		this.doCoQueue(org_id_str);  //全量发布
	}
	
	/**
	 * 写同步消息队列
	 * @param org_id_str  销售组织标识串
	 */
	private void doCoQueue(String org_id_str) {
		
		//写消息队列
		CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
		coQueueAddReq.setCo_name("类型同步");
		coQueueAddReq.setBatch_id("-1");
		coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_LEIXING);
		coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
		coQueueAddReq.setObject_id("-1");
		coQueueAddReq.setObject_type("LEIXING");
		coQueueAddReq.setContents("-1");
		coQueueAddReq.setOrg_id_str("10008");
		coQueueAddReq.setOrg_id_belong("10008");  //新商城
		coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
		coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(coQueueAddReq, CoQueueAddResp.class);
	}

	@Override
	public List<GoodsType> listAllCacheType(String type) {
		List<GoodsType> types = read.getTypes();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if(!StringUtils.isEmpty(type)){
			for(GoodsType goodstype : types){
				if(type.equals(goodstype.getType())){
					typeList.add(goodstype);
				}
			}
		}
		else{
			typeList = types;
		}
		return typeList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllGoodsTypes() throws Exception {
		String sql = "SELECT a.type_id,a.name FROM es_goods_type a WHERE a.type='goods' and a.disabled=0";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllGoodsCats() throws Exception {
		String sql = "SELECT a.cat_id,a.name,a.type_id FROM es_goods_cat a WHERE a.type='goods'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllGoods() throws Exception {
		String sql = "SELECT a.goods_id,a.name,a.type_id,a.cat_id FROM es_goods a  WHERE a.type='goods' and a.disabled=0 and a.market_enable=1";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getGoodsCatsByTypeid(String type_id) throws Exception {
		String sql = "SELECT a.cat_id,a.name,a.type_id FROM es_goods_cat a WHERE a.type='goods' and a.type_id='" + type_id + "'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getGoodsByCatid(String cat_id) throws Exception {
		String sql = "SELECT a.goods_id,a.name,a.type_id,a.cat_id FROM es_goods a  WHERE a.type='goods' and a.disabled=0 and a.market_enable=1 and a.cat_id='"+cat_id+"'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List getGoodsByGoodsid(String goods_id) throws Exception{
		String sql = "SELECT a.goods_id,a.name,a.type_id,a.cat_id FROM es_goods a  WHERE a.type='goods' and a.disabled=0 and a.market_enable=1 and a.goods_id='"+goods_id+"'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}
}
