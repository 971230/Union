package com.ztesoft.net.mall.core.service;


import java.util.List;

import com.ztesoft.net.app.base.core.model.Lan;

public interface ILanManager {
	

	/**
	 * 读取某个引用的区域id集合
	 * 
	 * @param relid
	 * @return
	 */
	public List<String> list(String lanids);

	/**
	 * 某个引用设置区域
	 * 
	 * @param relid
	 * @param lanids
	 */
	public void saveRels(String goodsid, Integer[] lanids);
	public List<Lan> listEdit();
	
	/**
	 * 读取所有本地网信息
	 * @return  
	 */
     public List<Lan> listLan();
     /**
 	 * 添加一个本地网信息
 	 * @param lan 角色实体
 	 */
     public String  add(Lan lan);
     /**
      * 删除一个本地网信息
      */
     public void delete(String lanid);
     /**
      * 修改一个本地网信息
      */
     public void update(Lan lan);
    /**
     *根据lan_id 得到 lan_name
     */
     public String getLanNameByID(String lan_id);
     /**
      *根据lan_id 得到 lan
      */
     public Lan getLanByID(String lan_id);

    //获取本地网树形结构
     public List queryTreeList(String arg);

     public List queryFirstList();
}
