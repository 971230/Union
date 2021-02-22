package com.ztesoft.net.mall.plugin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.plugin.model.GoodsAttrDef;
import com.ztesoft.net.mall.plugin.model.GoodsTempInst;
import com.ztesoft.net.mall.plugin.property.GoodsPropertyPlugin;
import com.ztesoft.net.mall.plugin.service.IGoodsAttrDefManager;
import com.ztesoft.net.mall.plugin.service.IGoodsTempInstManager;
import commons.CommonTools;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 15:53
 * To change this template use File | Settings | File Templates.
 *商品模板属性
 */
public class GoodsPropertyAction extends WWAction {
    private final String EDIT="edit";
    private final String ADD="add";
    private final String UPDATE="update";
    private int isEdit=-1;
    private GoodsAttrDef goodsAttrDef;
    private String attr_spec_type;
    private String sub_attr_spec_type;
    private String field_name;
    private String field_desc;
    private String default_value;
    private String default_value_desc;
    private String rel_table_name;
    private String owner_table_fields;
    private String o_field_name;
    private String field_type;
    private String source_from;
    private String attr_spec_id;
    private String field_attr_id;
    private List property;
    private List selectList;
    private String goods_id;
    private String editGoodsId;
    private String attr_code;
    private String source;
    private GoodsTempInst temp;
    private Goods goodsName;
    private List accpt;
    private List rel;
    private List typeList;//商品类型
    private List stypeList;//商品服务类型
    private List selGoodsList;
    private List temps;
    private String names;
    private Goods goodname;
    private String[] attr_spec_types;
    private String type_id;
    private String stype_id;
    private String[] types_id;
    private String[] stypes_id;
    private String[] goodsId;
    private String[] spec_type;
    private String[] name;
	private String[] desc;
	private String[] type;
	private String[] code;
	private String[] value;
	private String[] value_desc;
	private String[] table_fields;
	private String[] table_name;
	private String[] field_names;
	private String[] attrId;
	private GoodsPropertyPlugin goodsPropertyPlugin;
    @Resource
    private IGoodsAttrDefManager goodsAttrDefManager;
    @Resource
    private IGoodsTempInstManager goodsTempInstManager;
    private IGoodsTypeManager goodsTypeManager;
    private IGoodsManager goodsManager;
    private Map map;
    HttpServletRequest request;
    private String [] e_names;
    private String [] attr_ids;

    private String goodId;
    private String temp_inst_id;
    private String goodsname; 
    private String temp_name;
    private String temp_cols;
    private String [] hasselect;
   private String[] goods_name;
	public String saveProperty(){
		
    	goodsAttrDefManager.save(goodsAttrDef);
        isEdit=0;
        return ADD;
    }
	public String savePropertyModule(){
    	//goodsAttrDefManager.save(goodsAttrDef);
        isEdit=0;
        return "savePropertyModule";
    }
    public Goods getGoodname() {
		return goodname;
	}
	public void setGoodname(Goods goodname) {
		this.goodname = goodname;
	}
	public String delete(){
		try{
			
			this.goodsAttrDefManager.del(field_attr_id);
		    this.json="{result:1}";
			
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e);
			this.json="{result:0,message:'"+e.getMessage()+"'}";
		}
		     return WWAction.JSON_MESSAGE;
	   }
   public String delModule(){
	   try{
		   this.goodsAttrDefManager.delModule(temp_inst_id);
		   this.json="{result:1}";
	   }catch(RuntimeException e){
		   this.logger.info(e.getMessage(),e);
		   this.json="{result:0,message:'"+e.getMessage()+"'}";
	   }
	   return WWAction.JSON_MESSAGE;
   }
    public String List (){
    	property=this.goodsAttrDefManager.goodList(goods_id);
    	return "list";
    }
    public String selectList (){
    	selectList=this.goodsAttrDefManager.selectList(field_attr_id);
    	return "selectList";
    }
    public String modList (){
    	source=CommonTools.getSourceForm();
    	goodname=this.goodsAttrDefManager.goodsname(goodsname);
    	property=this.goodsAttrDefManager.goodList(goods_id);
    	return "modList";
    }
    public String editModList (){
    	source=CommonTools.getSourceForm();
    	temp=this.goodsAttrDefManager.tempId(temp_inst_id);
    	property=this.goodsAttrDefManager.goodList(goods_id);
    	return "editModList";
    }
    public String tempList(){
    	
    	this.goodsAttrDefManager.tempList(goods_id);
    	return "addModule";
    }
    public String editTempList(){
    	
    	this.goodsAttrDefManager.tempList(goods_id);
    	return "editTempList";
    }
public String addTempModule(){
	try{
    	for(int i=0;i<goodsId.length;i++){
    		this.goodsAttrDefManager.delte(goodsId[i]);
    		List<GoodsAttrDef> list=new ArrayList<GoodsAttrDef>();
    		for(int j=0;j<name.length;j++){
    			GoodsAttrDef def=new GoodsAttrDef();
    			def.setAttr_spec_id(goodsId[i]);
    			def.setField_name(name[j]);
    			def.setField_desc(desc[j]);
    			def.setAttr_code(code[j]);
    			def.setDefault_value(value[j]);
    			def.setDefault_value_desc(value_desc[j]);
    			def.setOwner_table_fields(table_fields[j]);
    			def.setRel_table_name(table_name[j]);
    			def.setO_field_name(field_names[j]);
    			def.setSub_attr_spec_type(spec_type[j]);
    			def.setAttr_spec_type("goods");
    		
    			this.goodsAttrDefManager.addTempProperty(def);
    			logger.info(hasselect[j]);
    			if("on".equalsIgnoreCase(hasselect[j])){
    				list.add(def);
    			}
    		}
    		GoodsTempInst temps=new GoodsTempInst();
    		
    		for(int a=0;a<goods_name.length;a++){
    			temps.setGoods_id(goodsId[i]);
    			temps.setTemp_name(goods_name[a]);
    			source=CommonTools.getSourceForm();
        		temps.setSource_from(source);
        		
        		String json = "[";
        		for(GoodsAttrDef d:list){
        			json += "{e_name:\""+d.getField_name()+"\",field_attr_id:\""+d.getField_attr_id()+"\"},";
            	}
        		json = json.substring(0, json.length()-1);
            	json += "]";
            	temps.setTemp_cols(json);
    		}
    		
        	this.goodsAttrDefManager.addTemps(temps);
        	this.json = "{'result':1,'message':'保存成功！'}";
    	}
	 }catch(RuntimeException e){
 		this.logger.info(e.getMessage(), e);
 		this.json = "{result:0,message:'" + e.getMessage() + "'}";
 	}	
    	return WWAction.JSON_MESSAGE;
   
}
    public String[] getGoods_name() {
	return goods_name;
}
public void setGoods_name(String[] goods_name) {
	this.goods_name = goods_name;
}
	public String addModule(){
    	
    	this.goods_id=this.goodId;
    	this.temp_name=this.names;
    	
    	String json = "[";
    	for(int i=0;i<e_names.length;i++){
    		json += "{e_name:\""+e_names[i]+"\",field_attr_id:\""+attr_ids[i]+"\"},";
    	}
    	json = json.substring(0, json.length()-1);
    	json += "]";
    	temp.setTemp_cols(json);
    	int goodsId=this.goodsAttrDefManager.getCountGoodsId(temp.getGoods_id());
    	if(goodsId>0){
    		String message="商品不能重复添加模板！";
    		this.json="hastmpl|"+message+"";
    		return WWAction.JSON_MESSAGE;
    	}
    	else{
    		this.goodsAttrDefManager.addTemp(temp);
    		//json = "{'result':1,'message':'新增成功！',goods_id:'"+attr_spec_id+"'}";
    	}
    	return "addModule";
    }
    public String addProperty(){
    	this.attr_spec_id = this.editGoodsId;
    	try {
    		int serCodeCount=this.goodsAttrDefManager.getCountAttrId(attr_spec_id, field_name);
    		if(serCodeCount>0){
    			String message="该属性存在!不能重复新增";
				this.json="{result:0,message:'"+message+"'}";
    		}
    		else{
    			
        		goodsAttrDefManager.save1(attr_code,field_attr_id,attr_spec_type, sub_attr_spec_type, field_name, field_desc, default_value, default_value_desc, rel_table_name, o_field_name, field_type, owner_table_fields, source_from,attr_spec_id);
        		//json = "{'result':1,'message':'新增成功！'}";
        		json = "{'result':1,'message':'新增成功！',goods_id:'"+attr_spec_id+"'}";
    		}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':0,'message':'新增失败！'}";
			return WWAction.JSON_MESSAGE;
		}
		return WWAction.JSON_MESSAGE;
   }
    public String updateProperty(){
        isEdit=1;
        return ADD;
    }
    public String edit(){
    	accpt=this.goodsAttrDefManager.findByAccpt(field_attr_id);
    	rel=this.goodsAttrDefManager.findByRel(field_attr_id);
		this.goodsAttrDef = this.goodsAttrDefManager.goodEdit(field_attr_id,attr_spec_id);
		return  "edit";		
	  }
    public String editProperty(){
    	accpt=this.goodsAttrDefManager.findByAccpt(field_attr_id);
    	rel=this.goodsAttrDefManager.findByRel(field_attr_id);
		this.goodsAttrDef = this.goodsAttrDefManager.goodEdit(field_attr_id,attr_spec_id);
		return  "editProperty";		
	  }
    //修改保存
	   public String editSave(){
		   try{ 
			   this.goodsAttrDefManager.modifyProperty(goodsAttrDef);
				json = "{'result':1,'message':'修改成功！'}";
			
			}catch(RuntimeException e){
				this.logger.info(e.getMessage(), e);
				this.json="{result:0,message:'"+e.getMessage()+"'}";
			}
		   return WWAction.JSON_MESSAGE;
	   }
    public String forwardIndexJsp(){
    	return "index";
    }
    public String ListAllType(){
    	this.typeList = this.goodsTypeManager.listAll();
    	return "typeList";
    }
    public String ListAllStype(){
    	this.stypeList = this.goodsTypeManager.listStype();
    	return "stypeList";
    }	
    public String listTypeGoods(){//根据商品类型查找商品
        this.selGoodsList = this.goodsManager.ListGoodsByTypesId(types_id);
        return "listSelGoods";
     }
    public String listStypeGoods(){//根据服务类型查找商品
    	this.stypeList = this.goodsManager.ListGoodsByStypesId(stypes_id);
    	return "listSelGoods";
    }
    

    public String updateModuleSave(){
    	String json = "[";
    	for(int i=0;i<e_names.length;i++){
    		json += "{e_name:\""+e_names[i]+"\",field_attr_id:\""+attr_ids[i]+"\"},";
    	}
    	json = json.substring(0, json.length()-1);
    	json += "]";
    	temp.setTemp_cols(json);
    	this.goodsAttrDefManager.updateModule(temp);
    	return "addModule";
    }

    public String listSelGoods(){
    	this.selGoodsList = this.goodsManager.listSelGoods(goodsId);
    	return  "listSelGoods";
    }
    //查询所有的商品
    public String listAllGoods(){
    	this.webpage = this.goodsManager.listAllGoods(this.getPage(), this.getPageSize(), names);
        return "listAllGoods";
    }
   
    public List getAccpt() {
		return accpt;
	}
	public void setAccpt(List accpt) {
		this.accpt = accpt;
	}
	public List getRel() {
		return rel;
	}
	public void setRel(List rel) {
		this.rel = rel;
	}
	public Goods getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(Goods goodsName) {
		this.goodsName = goodsName;
	}
	
	public String[] getAttr_spec_types() {
		return attr_spec_types;
	}
	public void setAttr_spec_types(String[] attr_spec_types) {
		this.attr_spec_types = attr_spec_types;
	}
	public String[] getName() {
		return name;
	}
	public void setName(String[] name) {
		this.name = name;
	}
	public String[] getDesc() {
		return desc;
	}
	public void setDesc(String[] desc) {
		this.desc = desc;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public String[] getCode() {
		return code;
	}
	public void setCode(String[] code) {
		this.code = code;
	}
	public String[] getValue() {
		return value;
	}
	public void setValue(String[] value) {
		this.value = value;
	}
	public String[] getValue_desc() {
		return value_desc;
	}
	public void setValue_desc(String[] value_desc) {
		this.value_desc = value_desc;
	}
	public String[] getTable_fields() {
		return table_fields;
	}
	public void setTable_fields(String[] table_fields) {
		this.table_fields = table_fields;
	}
	public String[] getTable_name() {
		return table_name;
	}
	public void setTable_name(String[] table_name) {
		this.table_name = table_name;
	}
	public String[] getField_names() {
		return field_names;
	}
	public void setField_names(String[] field_names) {
		this.field_names = field_names;
	}
	
	public String[] getAttrId() {
		return attrId;
	}
	public void setAttrId(String[] attrId) {
		this.attrId = attrId;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	
    public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
    public String getTemp_cols() {
		return temp_cols;
	}
	public void setTemp_cols(String temp_cols) {
		this.temp_cols = temp_cols;
	}
	public String getTemp_name() {
		return temp_name;
	}
	
	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}
	public String getTemp_inst_id() {
		return temp_inst_id;
	}
	public void setTemp_inst_id(String temp_inst_id) {
		this.temp_inst_id = temp_inst_id;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

 
    public String[] getSpec_type() {
		return spec_type;
	}
	public void setSpec_type(String[] spec_type) {
		this.spec_type = spec_type;
	}
    public int getEdit() {
        return isEdit;
    }
    public void setEdit(int edit) {
        isEdit = edit;
    }
    public GoodsAttrDef getGoodsAttrDef() {
        return goodsAttrDef;
    }
    public void setGoodsAttrDef(GoodsAttrDef goodsAttrDef) {
        this.goodsAttrDef = goodsAttrDef;
    }
	public int getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	public String getAttr_spec_type() {
		return attr_spec_type;
	}
	public void setAttr_spec_type(String attr_spec_type) {
		this.attr_spec_type = attr_spec_type;
	}
	public String getSub_attr_spec_type() {
		return sub_attr_spec_type;
	}
	public void setSub_attr_spec_type(String sub_attr_spec_type) {
		this.sub_attr_spec_type = sub_attr_spec_type;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_desc() {
		return field_desc;
	}

	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getDefault_value_desc() {
		return default_value_desc;
	}

	public void setDefault_value_desc(String default_value_desc) {
		this.default_value_desc = default_value_desc;
	}

	public String getRel_table_name() {
		return rel_table_name;
	}

	public void setRel_table_name(String rel_table_name) {
		this.rel_table_name = rel_table_name;
	}

	public String getOwner_table_fields() {
		return owner_table_fields;
	}

	public void setOwner_table_fields(String owner_table_fields) {
		this.owner_table_fields = owner_table_fields;
	}

	public IGoodsAttrDefManager getGoodsAttrDefManager() {
		return goodsAttrDefManager;
	}

	public void setGoodsAttrDefManager(IGoodsAttrDefManager goodsAttrDefManager) {
		this.goodsAttrDefManager = goodsAttrDefManager;
	}

	public IGoodsTempInstManager getGoodsTempInstManager() {
		return goodsTempInstManager;
	}

	public void setGoodsTempInstManager(IGoodsTempInstManager goodsTempInstManager) {
		this.goodsTempInstManager = goodsTempInstManager;
	}

	public String getEDIT() {
		return EDIT;
	}

	public String getADD() {
		return ADD;
	}

	public String getUPDATE() {
		return UPDATE;
	}

	public String getO_field_name() {
		return o_field_name;
	}

	public void setO_field_name(String o_field_name) {
		this.o_field_name = o_field_name;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getAttr_spec_id() {
		return attr_spec_id;
	}

	public void setAttr_spec_id(String attr_spec_id) {
		this.attr_spec_id = attr_spec_id;
	}

	public GoodsPropertyPlugin getGoodsPropertyPlugin() {
		return goodsPropertyPlugin;
	}

	public void setGoodsPropertyPlugin(GoodsPropertyPlugin goodsPropertyPlugin) {
		this.goodsPropertyPlugin = goodsPropertyPlugin;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public List getProperty() {
		return property;
	}
	public void setProperty(List property) {
		this.property = property;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getEditGoodsId() {
		return editGoodsId;
	}

	public void setEditGoodsId(String editGoodsId) {
		this.editGoodsId = editGoodsId;
	}
	public String getField_attr_id() {
		return field_attr_id;
	}
	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	
	public List getSelectList() {
		return selectList;
	}
	public void setSelectList(List selectList) {
		this.selectList = selectList;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public GoodsTempInst getTemp() {
		return temp;
	}
	public void setTemp(GoodsTempInst temp) {
		this.temp = temp;
	}
	public List getTemps() {
		return temps;
	}
	public void setTemps(List temps) {
		this.temps = temps;
	}
	public String[] getE_names() {
		return e_names;
	}
	public void setE_names(String[] e_names) {
		this.e_names = e_names;
	}
	public String[] getAttr_ids() {
		return attr_ids;
	}
	public void setAttr_ids(String[] attr_ids) {
		this.attr_ids = attr_ids;
	}
	public List getTypeList() {
		return typeList;
	}
	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}
	public List getStypeList() {
		return stypeList;
	}
	public void setStypeList(List stypeList) {
		this.stypeList = stypeList;
	}
	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}
	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}
	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}
	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getStype_id() {
		return stype_id;
	}
	public void setStype_id(String stype_id) {
		this.stype_id = stype_id;
	}
	
	public List getSelGoodsList() {
		return selGoodsList;
	}
	public void setSelGoodsList(List selGoodsList) {
		this.selGoodsList = selGoodsList;
	}
	public String[] getTypes_id() {
		return types_id;
	}
	public void setTypes_id(String[] types_id) {
		this.types_id = types_id;
	}
	public String[] getStypes_id() {
		return stypes_id;
	}
	public void setStypes_id(String[] stypes_id) {
		this.stypes_id = stypes_id;
	}
	public String[] getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String[] goodsId) {
		this.goodsId = goodsId;
	}
	public String[] getHasselect() {
		return hasselect;
	}
	public void setHasselect(String[] hasselect) {
		this.hasselect = hasselect;
	}
	
	
	
}
