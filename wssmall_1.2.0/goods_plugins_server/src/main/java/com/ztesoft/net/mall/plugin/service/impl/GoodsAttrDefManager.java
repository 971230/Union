package com.ztesoft.net.mall.plugin.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.plugin.model.GoodsAttrDef;
import com.ztesoft.net.mall.plugin.model.GoodsTempInst;
import com.ztesoft.net.mall.plugin.service.IGoodsAttrDefManager;
import com.ztesoft.net.sqls.SF;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 12:12
 * To change this template use File | Settings | File Templates.
 */
public class GoodsAttrDefManager extends BaseSupport implements IGoodsAttrDefManager {

	
    @Override
    public String save(GoodsAttrDef arg) {
    	  if (null == arg) return null;
          if (StringUtils.isEmpty(arg.getGoods_id())) {
              throw new RuntimeException("添加商品ID为空!");
          }
          if (StringUtils.isEmpty(arg.getField_attr_id())) {
              throw new RuntimeException("添加关联商品ID为空!");
          }

          String seq = daoSupport.getSequences("ES_ATTR_DEF_SEQ");
          arg.setField_attr_id(seq);
          daoSupport.insert("ES_ATTR_DEF", arg);
          return seq;  
    	
    }

//    public List query(String[] ids, GoodsServiceType arg) {
//        if (null == ids) return new ArrayList();
//
//        String sql = "select g.goods_id,d.attr_code,d.field_attr_id,d.attr_spec_id,d.attr_spec_type,d.field_name,d.field_desc,d.o_field_name,d.rel_table_name from es_goods g,es_attr_def d where g.goods_id=d.attr_spec_id";
//        List param = new ArrayList();
//        param.add(String.valueOf(arg));
//        StringBuilder builder = new StringBuilder(1000);
//        builder.append(sql);
//        builder.append(" and g.goods_id in(");
//        for (String str : ids) {
//            builder.append(str);
//            builder.append(",");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        builder.append(")");
//        List list = null;
//        try {
//            list = this.daoSupport.queryForList(builder.toString(), param.toArray());
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        return list;  //To change body of implemented methods use File | Settings | File Templates.
//    }
   

    @Override
    public List query(String id, GoodsServiceType type) {
        if (StringUtils.isEmpty(id)) return null;

        String sql = SF.goodsSql("LIST_GOODS_ATTR_DEF2");
        List list = daoSupport.queryForList(sql, id);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    
	@Override
	public List goodList(String goods_id ) {
		String sql="select * from es_attr_def where attr_spec_id=? ";
		return this.daoSupport.queryForList(sql,goods_id);
	}
	@Override
	public GoodsAttrDef goodEdit(String field_attr_id,String attr_spec_id) {
		GoodsAttrDef appService = null;
		String sql = "select g.goods_id,d.field_attr_id,d.attr_spec_id,d.field_type,d.attr_spec_type,d.default_value,d.default_value_desc,d.field_name,d.field_desc,d.o_field_name,d.rel_table_name from es_goods g,es_attr_def d where g.goods_id=d.attr_spec_id and g.source_from='"+ManagerUtils.getSourceFrom()+"' and d.source_from='"+ManagerUtils.getSourceFrom()+"' and field_attr_id=? and  attr_spec_id=?";
		appService = (GoodsAttrDef) this.baseDaoSupport.queryForObject(sql,GoodsAttrDef.class, field_attr_id,attr_spec_id);
		return appService;

	}
	//修改模板列表的数据
	@Override
	public void updateModule(GoodsTempInst temps){
		this.baseDaoSupport.update("es_temp_inst", temps, "temp_inst_id="+temps.getTemp_inst_id());
	}
	
	@Override
	public String save1(String attr_code,String field_attr_id,String attr_spec_type, String sub_attr_spec_type,
			String field_name, String field_desc, String default_value,
			String default_value_desc, String rel_table_name,
			String o_field_name, String field_type, String owner_table_fields,
			String source_from,String attr_spec_id) {
		GoodsAttrDef good=new GoodsAttrDef();
		good.setField_attr_id(field_attr_id);
		good.setAttr_spec_type(attr_spec_type);
		good.setSub_attr_spec_type(sub_attr_spec_type);
		good.setField_name(field_name);
		good.setField_desc(field_desc);
		good.setDefault_value(default_value);
		good.setDefault_value_desc(default_value_desc);
		good.setRel_table_name(rel_table_name);
		good.setO_field_name(o_field_name);
		good.setField_type(field_type);
		good.setOwner_table_fields(owner_table_fields);
		good.setSource_from(source_from);
		good.setAttr_spec_id(attr_spec_id);
		return add(good);
	}
	@Override
	public String add(GoodsAttrDef god) {
		god.setField_attr_id(this.baseDaoSupport.getSequences("ES_ATTR_DEF_SEQ"));
		this.daoSupport.insert("ES_ATTR_DEF", god);
		return god.getField_attr_id();

	}
	@Override
	public void delRel(String field_attr_id, GoodsServiceType arg) {
		 String sql = "delete from es_attr_def where field_attr_id=?";
	     daoSupport.execute(sql, field_attr_id, String.valueOf(arg));
	}
	@Override
	public void delte(String attr_spec_id) {
		 String sql = "delete from es_attr_def where attr_spec_id=?";
		 String sql_1 = "delete from es_temp_inst where goods_id = ?";
	     daoSupport.execute(sql, attr_spec_id);
	     daoSupport.execute(sql_1, attr_spec_id);
	}
	@Override
	public void del(String field_attr_id) {
		 String sql = "delete from es_attr_def where field_attr_id=?";
	     daoSupport.execute(sql, field_attr_id);
	}
	//删除模板列表的数据
	@Override
	public void delModule(String temp_inst_id){
		String sql="delete from es_temp_inst where temp_inst_id=?";
		daoSupport.execute(sql,temp_inst_id);
	}
	@Override
	public void modifyProperty(GoodsAttrDef goods) {
		this.baseDaoSupport.update("es_attr_def", goods, "field_attr_id="+goods.getField_attr_id());

	}
	//查出已选模板的数据
	@Override
	public List selectList(String field_attr_id) {
		String sql="select * from es_attr_def where field_attr_id=?";
		return baseDaoSupport.queryForList(sql, field_attr_id);
	}

	@Override
	public int getCountAttrId(String attr_spec_id, String field_name) {
		int  count=0;
		String sql="select count(*) from es_attr_def where attr_spec_id=? and field_name=?";
		count=this.baseDaoSupport.queryForInt(sql,attr_spec_id,field_name );
		return count;
	}
	@Override
	public int getCountGoodsId(String goods_id){
		int count=0;
		String sql="select count(*) from es_temp_inst where goods_id=?";
		count=this.baseDaoSupport.queryForInt(sql, goods_id);
		return count;
	}
	@Override
	public void addTemp(GoodsTempInst temp) {
		//temp.setTemp_inst_id(this.baseDaoSupport.getSequences("S_TEMP_INST_SEQ"));
		this.baseDaoSupport.insert("es_temp_inst", temp);
	}
	@Override
	public GoodsTempInst tempList(String goods_id) {
		GoodsTempInst temp=null;
		String sql="select * from es_temp_inst where goods_id=?";
		temp=(GoodsTempInst)this.baseDaoSupport.queryForObject(sql,GoodsTempInst.class , goods_id);
		return temp;
	}

	@Override
	public GoodsTempInst tempId(String temp_inst_id) {
		String sql="select * from es_temp_inst where temp_inst_id=?";
		return (GoodsTempInst) this.baseDaoSupport.queryForObject(sql, GoodsTempInst.class, temp_inst_id);
	}

	@Override
	public List querys(String[] ids, GoodsServiceType arg) {
		        if (null == ids) return new ArrayList();

		        String sql = "select  g.goods_id,t.temp_inst_id,t.temp_name,t.temp_cols,t.source_from,t.goods_id from es_goods g,es_temp_inst t where g.goods_id=t.goods_id";
		        List param = new ArrayList();
		        param.add(String.valueOf(arg));
		        StringBuilder builder = new StringBuilder(1000);
		        builder.append(sql);
		        builder.append(" and g.goods_id in(");
		        for (String str : ids) {
		            builder.append(str);
		            builder.append(",");
		        }
		        builder.deleteCharAt(builder.length() - 1);
		        builder.append(")");
		        List list = null;
		        try {
		            list = this.daoSupport.queryForList(builder.toString(), param.toArray());
		        } catch (Exception e) {
		            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		        }
		        return list;  //To change body of implemented methods use File | Settings | File Templates.
	}
	@Override
    public List querys(String id, GoodsServiceType type) {
        if (StringUtils.isEmpty(id)) return null;

        String sql = SF.goodsSql("LIST_GOODS_TEMP_INST");       
        List list = daoSupport.queryForList(sql, id);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public void addTempProperty(GoodsAttrDef def) {
		def.setField_attr_id(this.baseDaoSupport.getSequences("ES_ATTR_DEF_SEQ"));
		this.baseDaoSupport.insert("es_attr_def", def);
	}

	@Override
	public String addTemps(GoodsTempInst temps) {
		temps.setTemp_inst_id(System.currentTimeMillis()+"");
//		temps.setTemp_inst_id(this.baseDaoSupport.getSequences("S_ES_TEMP_INST"));
		this.baseDaoSupport.insert("es_temp_inst", temps);
		return temps.getTemp_inst_id();
	}

	@Override
	public Goods goodsname(String name) {
		String sql="select name from es_goods where goods_id=?";
		return (Goods)this.baseDaoSupport.queryForObject(sql, Goods.class, name);
	}
//	@Override
//	public List findById(int app_id) {
//		String sql="select a.fun_name from PM_APP_AUDIT_SERVICE b,pm_app_service a  where a.fun_id=b.fun_id and b.status_cd='00A' and b.app_id =? ";
//		return this.daoSupport.queryForList(sql, app_id);
//	}
	@Override
	public List findByAccpt(String field_attr_id) {
		String sql="select sub_attr_spec_type from es_attr_def where field_attr_id=?";
		return this.baseDaoSupport.queryForList(sql, field_attr_id);
	}

	@Override
	public List findByRel(String field_attr_id) {
		String sql="select owner_table_fields from es_attr_def where field_attr_id=?";
		return this.baseDaoSupport.queryForList(sql, field_attr_id);
	}
}