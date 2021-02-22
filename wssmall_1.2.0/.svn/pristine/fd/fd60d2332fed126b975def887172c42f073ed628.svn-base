package com.ztesoft.net.mall.core.service.impl;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.DStoreConfig;
import com.ztesoft.net.mall.core.model.DStoreInst;
import com.ztesoft.net.mall.core.service.IBlogManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class BlogManagerImpl extends BaseSupport<DStoreConfig> implements IBlogManager {

	@Override
	public List<DStoreConfig> getDStoreConfigList() {
		String sql = "select a.* from es_blob_config a where a.source_from = '" + ManagerUtils.getSourceFrom() + "'" ;
		return this.baseDaoSupport.queryForList(sql, DStoreConfig.class);
	}
	
	@Override
	public Map getDstoreInst(String file_path) {
		String sql = "select * from es_dstore_inst where file_path=?";
		List<Map> ret = this.baseDaoSupport.queryForList(sql, file_path);
		return (ret==null ||ret.size()==0)?null:ret.get(0);
	}

	
	@Override
	public void insertDstoreInst(DStoreInst inst) {
		this.baseDaoSupport.insert("es_dstore_inst", inst);
	}


	
}
