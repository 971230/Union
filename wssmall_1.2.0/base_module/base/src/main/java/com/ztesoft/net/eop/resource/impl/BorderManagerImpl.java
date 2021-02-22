package com.ztesoft.net.eop.resource.impl;

import java.util.List;

import com.ztesoft.net.eop.resource.IBorderManager;
import com.ztesoft.net.eop.resource.model.Border;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

/**
 * saas式的边框管理 
 * @author kingapex
 * 2010-1-28下午05:41:09
 */
public class BorderManagerImpl extends BaseSupport<Border>  implements IBorderManager {
 
	@Override
	public void clean() {
		this.baseDaoSupport.execute("truncate table border");
	}
	
	
	@Override
	public void add(Border border) {
		this.baseDaoSupport.insert("border", border);
	}

	
	@Override
	public void delete(Integer id) {
		this.baseDaoSupport.execute("delete from border where id=?", id);
	}

	
	@Override
	public List<Border> list() {
		String sql  ="select * from  border";
		List<Border> list = this.baseDaoSupport.queryForList(sql, Border.class);
		return list;
	}

	
	@Override
	public void update(Border border) {
		this.baseDaoSupport.update("border", border, "id="+border.getId());
	}

 
	
}
