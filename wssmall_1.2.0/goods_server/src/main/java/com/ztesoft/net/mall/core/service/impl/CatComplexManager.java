package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.CatComplex;
import com.ztesoft.net.mall.core.service.ICatComplexManager;
import com.ztesoft.net.sqls.SF;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.List;

public class CatComplexManager extends BaseSupport<CatComplex> implements ICatComplexManager {

	
	private SimpleJdbcTemplate simpleJdbcTemplate;
	@Override
	public void save(CatComplex catcom) {
		String tableName ="es_cat_complex";
		String [] goodsId = catcom.getGoods_id().split(",");
		String [] manaul = catcom.getManual().split(",");
		String [] goodsName = catcom.getGoods_name().split(",");
		for(int i =0 ;i<goodsId.length;i++){
			catcom.setGoods_id(goodsId[i].trim());
			catcom.setManual(manaul[i].trim());
			catcom.setGoods_name(goodsName[i]);
			int j = findCatComp(goodsId[i].trim(), catcom.getCat_id());
			if(j<1){
				this.baseDaoSupport.insert(tableName, catcom);
			}
		}
	}
	
	@Override
	public int findCatComp(String goods_id, String cat_id) {
		
		String sql = SF.goodsSql("FIND_CATCOMP");
		return this.baseDaoSupport.queryForInt(sql, goods_id,cat_id);
	}
	
	@Override
	public List<CatComplex> findListByCatId(String cat_id) {
		String sql = SF.goodsSql("FIND_LIST_BY_CATID");
		return this.baseDaoSupport.queryForList(sql, CatComplex.class, cat_id);
	}
	
	@Override
	public void delByCatId(String cat_id, String goods_id) {
		String sql=SF.goodsSql("DEL_BY_CATID");
		this.baseDaoSupport.execute(sql, cat_id, goods_id);
	}	
	
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	@Override
	public List<CatComplex> listCatComplex(){
		String sql = SF.goodsSql("LIST_CAT_COMPLEX");
		return this.baseDaoSupport.queryForList(sql, CatComplex.class);
	}

	
}
