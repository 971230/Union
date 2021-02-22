package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.AttrTypeRela;

/**
 * 业务类型关联属性处理器接口
 * @author dingxiaotao
 *
 */
public interface IAttrTypeRelaManager {
	public List<AttrTypeRela> getAttrTypeRela(String busiType);
	
	public void refreshCache();
}
