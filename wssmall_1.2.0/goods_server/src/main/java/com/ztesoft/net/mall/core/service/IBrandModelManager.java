package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
public interface IBrandModelManager {
	
	public Page getBrandModelList(int page,int pageSize,String brand_name,String machine_name,String model_name);
	
	/**
	 * 根据型号编码查询，如果为-1则是所有
	 * @param model_code
	 * @return
	 */
	public List<BrandModel> getBrandModelListAll(String model_code);
	
	/**
	 * 品牌终端列表ECS
	 * @param page
	 * @param pageSize
	 * @param brand_name
	 * @param model_name
	 * @return
	 */
	public Page getBrandModelListECS(int pageNo,int pageSize,BrandModel brandModel);
	
	
	/**
	 * 品牌列表
	 * @param brand
	 * @return 
	 */
	public Page getBrandList(Brand brand,int pageNo, int pageSize);
	
	
	/**
	 * 终端品牌详情
	 * @param brand_model_id
	 * @return
	 */
	public BrandModel getbrandModelDetail(String brand_model_id);
	
	/**
	 * 验证是否存在型号
	 * @param model_name
	 * @return true 已经存在
	 */
	public boolean checkModelName(String model_name);
	
	/**
	 * 保存品牌终端ECS
	 * @param brandModel
	 * @return
	 */
	public boolean saveOrUpdateBrandMode(BrandModel brandModel);
	
	/**
	 * 判断是否已经在使用
	 * @param brand_model_id
	 */
	boolean checkUsed(String brand_model_id);
	
	/**
	 * 删除
	 * @param brand_model_id
	 */
	void doDelete(String brand_model_id);
	
	/**
	 * 品牌型号发布
	 * @param params
	 */
	void doPublish(Map params);
	
	/**
	 * 品牌型号发布
	 * @param params
	 */
	void doBatchPublish(Map params);
	
	public List<BrandModel> listModelByBrandId(String brand_id);
	
	public List<BrandModel> listCacheModelByBrandId(String brand_id);
	
	public List<BrandModel> listCacheModelByModel(String model_code);
	
	public boolean checkIsExistsModelCode(String model_codel);
	
}
