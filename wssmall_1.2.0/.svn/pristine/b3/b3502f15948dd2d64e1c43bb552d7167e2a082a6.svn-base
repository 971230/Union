package com.ztesoft.net.app.base.core.service.impl;

import com.ztesoft.net.app.base.core.service.IDStoreManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.DStoreInst;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class DStoreManager extends BaseSupport implements IDStoreManager{

	@Override
	public String add(DStoreInst inst) {
		inst.setSeq(this.daoSupport.getSequences("ES_DSTORE_INST_SEQ"));
//		inst.setTable_name(table_name.toUpperCase());
//		inst.setField_name(field_name.toUpperCase());
//		inst.setKey_col_name(key_col_name);
//		inst.setPrimy_key_value(primy_key_value);
//		inst.setStore_type(store_type);
//		inst.setFile_path(file_path);
		inst.setSource_from(ManagerUtils.getSourceFrom());
		inst.setDisbaled(String.valueOf(Consts.DSTORE_DISABLED_0));
		inst.setState(Consts.DSTORE_STATE_00A);
		this.baseDaoSupport.insert("es_dstore_inst", inst);
		return inst.getSeq();
	}

}
