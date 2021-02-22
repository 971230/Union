package com.ztesoft.net.mall.plugin.service;


import java.util.List;

import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.plugin.model.GoodsAttrDef;
import com.ztesoft.net.mall.plugin.model.GoodsTempInst;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 11:39
 * To change this template use File | Settings | File Templates.
 */
public interface IGoodsAttrDefManager {
	public void modifyProperty(GoodsAttrDef goods);
    public String save(GoodsAttrDef arg);
	public String  add(GoodsAttrDef god);
	public void  addTemp(GoodsTempInst temp);
	public void delte (String attr_spec_id);
    public List findByAccpt(String field_attr_id);
	public List findByRel(String field_attr_id);
	public String addTemps(GoodsTempInst temps);
	public void addTempProperty(GoodsAttrDef def);
	public Goods goodsname(String name);
	public GoodsTempInst tempId(String temp_inst_id);
	
    public String save1(String attr_code,String field_attr_id,String attr_spec_type,String sub_attr_spec_type,String field_name,String field_desc,String default_value,String default_value_desc,String rel_table_name,String o_field_name,String field_type,String owner_table_fields,String source_from,String attr_spec_id);
    public List goodList(String goods_id);
    //public List query(String[] ids,GoodsServiceType arg);
    public List querys(String[] ids,GoodsServiceType arg);
    public List selectList(String field_attr_id);
    public GoodsAttrDef goodEdit(String field_attr_id,String attr_spec_id);
    public List query(String id,GoodsServiceType type);
    public List querys(String id,GoodsServiceType type);
    public GoodsTempInst tempList(String goods_id);  
    public void delRel(String field_attr_id,GoodsServiceType arg);
    public void del(String field_attr_id);
    public void updateModule(GoodsTempInst temps);
    public void delModule(String temp_inst_id);
	//public int getCountFieldName(String field_attr_id,String field_name);
	public int getCountAttrId(String attr_spec_id,String field_name);
	public int getCountGoodsId(String goods_id);
	
	
}
