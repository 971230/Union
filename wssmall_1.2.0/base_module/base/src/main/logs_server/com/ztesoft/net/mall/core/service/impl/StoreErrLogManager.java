package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.StoreErrLog;
import com.ztesoft.net.mall.core.service.IStoreErrLogManager;

public class StoreErrLogManager extends BaseSupport  implements IStoreErrLogManager{
	
	@Override
	public void log(StoreErrLog log) {
		this.baseDaoSupport.insert("ES_STORE_ERR_LOG", log);
	}
}
