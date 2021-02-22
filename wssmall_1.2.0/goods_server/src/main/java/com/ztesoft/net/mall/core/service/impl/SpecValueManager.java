package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.mapper.SpecValueMapper;
import com.ztesoft.net.mall.core.service.ISpecValueManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;
import java.util.Map;

/**
 * 规格值管理
 * @author kingapex
 *2010-3-7下午06:33:06
 */
public class SpecValueManager extends BaseSupport<SpecValue> implements ISpecValueManager {

	
	@Override
	public void add(SpecValue value) {
	   this.baseDaoSupport.insert("es_spec_values",value);

	}



	
	@Override
	public List<SpecValue> list(String specId) {
		String sql = SF.goodsSql("SPEC_VALUES_GET_BY_ID");
		List valueList = this.baseDaoSupport.queryForList(sql, new SpecValueMapper() ,specId);
		return valueList;
	}
	

	
	@Override
	public Map get(String valueId) {
		String sql = SF.goodsSql("SPEC_VALUES_GET_BY_SPECIFICATION");
		Map temp = this.daoSupport.queryForMap(sql, valueId);
		String spec_image = (String)temp.get("spec_image");
		if(spec_image!=null){
			spec_image  =UploadUtil.replacePath(spec_image); 
		}
		temp.put("spec_image", spec_image);
		return temp;
	}

}
