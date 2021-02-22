package com.ztesoft.net.mall.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsRel;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.core.service.IGoodsContractManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-05 14:27
 * To change this template use File | Settings | File Templates.
 */
public class GoodsContractManager extends BaseSupport implements IGoodsContractManager {


	@Override
	public List<GoodsRelAttConvert> getSetContract(String a_goods_id, String goods_id){
		String sql ="select rel_contract_inst from es_goods_rel where  rel_type='TERMINAL_PLAN' and z_goods_id ="+goods_id + " and a_goods_id="+a_goods_id ;
		String rel_contract_inst = this.baseDaoSupport.queryForString(sql);
		
		List<GoodsRelAttConvert> listSetContract =  JSON.parseArray(rel_contract_inst,GoodsRelAttConvert.class);
		return listSetContract;
	}
    @Override
    public Page query(String name, int pageIndex, int pageSize, GoodsServiceType arg) {
        String sql = "select t.goods_id,t.name,t.stype_id,d.name service_name,d.stype_code from es_goods t,es_goods_stype d where t.stype_id=d.stype_id and d.stype_code=?";
        StringBuilder builder = new StringBuilder(1000);
        builder.append(sql);
        List list = new ArrayList();
        list.add(String.valueOf(arg));
        if (StringUtils.isNotBlank(name)) {
            builder.append(" and t.name like ?");
            name = "'%" + name + "%'";
            list.add(name);
        }
        Page page = null;
        try {
            page = this.daoSupport.queryForPage(builder.toString(), pageIndex, pageSize, list.toArray());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return page;
    }


    @Override
	public List query(String[] ids, GoodsServiceType arg) {
        if (null == ids) return new ArrayList();

        String sql = "select t.goods_id,t.name,t.stype_id,d.name service_name,d.stype_code from es_goods t,es_goods_stype d where t.stype_id=d.stype_id and d.stype_code=?";
        List param = new ArrayList();
        param.add(String.valueOf(arg));
        StringBuilder builder = new StringBuilder(1000);
        builder.append(sql);
        builder.append(" and t.goods_id in(");
        for (String str : ids) {
            builder.append("'" + str + "'");
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
    public List query(String id, GoodsServiceType type) {
        if (StringUtils.isEmpty(id)) return null;

     //   String sql = "select t.goods_id,t.name,t.stype_id,d.name service_name,rel.rel_contract_inst rel_contract_inst,d.stype_code" +
      //  		    " from es_goods t  join es_goods_stype d on t.stype_id =d.stype_id  join es_goods_rel rel on rel.z_goods_id = t.goods_id"+
       //             " where t.goods_id in(select z_goods_id from es_goods_rel where  a_goods_id=? and rel_type=?)";
        String sql = "select t.goods_id, t.name, t.stype_id,d.name service_name, rel.rel_contract_inst rel_contract_inst, d.stype_code "+
        			 " from es_goods t, es_goods_stype d,es_goods_rel rel where t.source_from=d.source_from and t.source_from=rel.source_from and t.source_from '"+ManagerUtils.getSourceFrom()+"' and t.stype_id = d.stype_id and  rel.z_goods_id = t.goods_id "+
        			 " and rel.a_goods_id = ? and rel_type = ? ";
        List list = daoSupport.queryForList(sql, id, String.valueOf(type));
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String save(GoodsRel arg) {
        if (null == arg) return null;
        if (StringUtils.isEmpty(arg.getA_goods_id())) {
            throw new RuntimeException("添加商品ID为空!");
        }
        if (StringUtils.isEmpty(arg.getZ_goods_id())) {
            throw new RuntimeException("添加关联商品ID为空!");
        }

       // String seq = daoSupport.getSequences("ES_GOODS_REL_SERVICE_seq");
       // arg.setId(seq);
        daoSupport.insert("ES_GOODS_REL", arg);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void delRel(String goods_id, GoodsServiceType arg) {
        String sql = "delete from ES_GOODS_REL where a_goods_id=? and rel_type=?";
        daoSupport.execute(sql, goods_id, String.valueOf(arg));
    }
}