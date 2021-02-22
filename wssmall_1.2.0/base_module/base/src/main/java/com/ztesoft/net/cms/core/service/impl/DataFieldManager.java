package com.ztesoft.net.cms.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.model.DataModel;
import com.ztesoft.net.cms.core.plugin.ArticlePluginBundle;
import com.ztesoft.net.cms.core.service.IDataFieldManager;
import com.ztesoft.net.cms.core.service.IDataModelManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 模型数据字段管理
 * @author kingapex
 * 2010-7-2下午03:41:44
 */
public class DataFieldManager extends  BaseSupport<DataField>   implements IDataFieldManager {
	
	private  IDataModelManager dataModelManager;
	private ArticlePluginBundle articlePluginBundle;
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public String add(DataField dataField) {
		dataField.setAdd_time(DBTUtil.current());
		this.baseDaoSupport.insert("data_field",dataField);
		String fieldid = dataField.getField_id();
		DataModel datamodel = this.dataModelManager.get(dataField.getModel_id(),"");
		StringBuffer sql  = new StringBuffer();
		sql.append("alter table ");
		sql.append( this.getModelTableName(datamodel.getEnglish_name())  );
		sql.append(" add ");
		sql.append( this.getFieldTypeSql( dataField  ) );
		
		this.daoSupport.execute(sql.toString());
		return fieldid;
		
	}

	private String getModelTableName(String tbname){
		tbname = this.getTableName(tbname);
		return tbname;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void delete(String fieldid) {
		
		
		DataField dataField  = this.get(fieldid);
		DataModel dataModel  = this.dataModelManager.get(dataField.getModel_id(),"");
		
		
		//删除模型中的相应字段
		String sql ="alter table " + this.getModelTableName( dataModel.getEnglish_name() ) +" drop "+ dataField.getEnglish_name();
		this.daoSupport.execute(sql);
		
		//删除字段表里的数据
		sql ="delete from  data_field where field_id=?";
		this.baseDaoSupport.execute(sql, fieldid);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void edit(DataField dataField) {
		DataField oldDataField  = this.get(dataField.getField_id());
		this.baseDaoSupport.update("data_field", dataField, "field_id="+dataField.getField_id());
		if(!oldDataField.getEnglish_name().equals(dataField.getEnglish_name())){//变更了字段名
			DataModel dataModel  = this.dataModelManager.get(dataField.getModel_id(),"");
			StringBuffer sql  = new StringBuffer();
			sql.append("alter table ");
			sql.append( this.getModelTableName(dataModel.getEnglish_name()));
			sql.append( " change column ");
			sql.append(oldDataField.getEnglish_name());
			sql.append(" ");
			sql.append(this.getFieldTypeSql( dataField ));
			this.daoSupport.execute(sql.toString());
		}
		
	}

	
	@Override
	public List<DataField> list(Integer modelid) {
		return this.baseDaoSupport.queryForList("select * from data_field where model_id=? order by taxis", DataField.class, modelid);
	}

	
	@Override
	public List<DataField> list(Integer modelid,String source_from) {
		return this.baseDaoSupport.queryForList("select * from data_field where model_id=? and source_from=? order by taxis", DataField.class, modelid, source_from);
	}
	
	@Override
	public DataField get(String fieldid){
		String sql  ="select * from data_field where field_id=?";
		return this.baseDaoSupport.queryForObject(sql, DataField.class, fieldid);
	}
	
	/**
	 * 拼装增加字段数据类型及大小sql
	 * @param field_name
	 * @param data_type 1:字符串 2:文本
	 * @param data_size
	 * @return
	 */
	private   String getFieldTypeSql(DataField field){
	 
  
	 
		return  field.getEnglish_name()+" "+this.articlePluginBundle.getFieldPlugin(field).getDataType();
	}

	public IDataModelManager getDataModelManager() {
		return dataModelManager;
	}

	public void setDataModelManager(IDataModelManager dataModelManager) {
		this.dataModelManager = dataModelManager;
	}

	
	@Override
	public List<DataField> listByCatId(String catid) {
		String sql  ="select df.* from "+ this.getTableName("data_field") +" df ," 
		+ this.getTableName("data_model") +" dm,"
		+ this.getTableName("data_cat")+" c where df.model_id=dm.model_id and dm.model_id= c.model_id and c.cat_id=?" +
				" and df.source_from = dm.source_from and dm.source_from = c.source_from and c.source_from = '" + ManagerUtils.getSourceFrom() + "'";
		
		return this.daoSupport.queryForList(sql, DataField.class, catid);
	}

	
	@Override
	public List<DataField> listByCatId(String catid,String source_from) {
		String sql  ="select df.* from "+ this.getTableName("data_field") +" df ," 
		+ this.getTableName("data_model") +" dm,"
		+ this.getTableName("data_cat")+" c where df.model_id=dm.model_id and dm.model_id= c.model_id and c.cat_id=?" +
				" and df.source_from = dm.source_from and dm.source_from = c.source_from and c.source_from=? ";
		
		return this.daoSupport.queryForList(sql, DataField.class, catid, source_from);
	}
	
	@Override
	public List<DataField> listIsShow(Integer modelid) {
		
		return this.baseDaoSupport.queryForList("select * from data_field  where model_id=? and is_show=1 order by taxis", DataField.class, modelid);
	}

	@Override
	public List<DataField> listIsShow(Integer modelid,String source_from) {
		
		return this.baseDaoSupport.queryForList("select * from data_field  where model_id=? and is_show=1 and source_from=? order by taxis", DataField.class, modelid,source_from);
	}
	
	@Override
	public void saveSort(String[] ids, Integer[] sorts) {
		String sql ;
		for(int i=0;i<ids.length;i++){
			sql = "update data_field set taxis=? where field_id=?";
			this.baseDaoSupport.execute(sql, sorts[i],ids[i]);
		}
	}

	public ArticlePluginBundle getArticlePluginBundle() {
		return articlePluginBundle;
	}

	public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
		this.articlePluginBundle = articlePluginBundle;
	}
	
	
}
