package com.ztesoft.net.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;



import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CommunityActivity;


/**
 * 小区管理接口类
 * @author luwei
 *2017-2-22下午9:41:07
 */
public interface ICommunityManager {
	
	
	/**
	 * 分页读取小区信息列表
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return  小区列表
	 */
	public Page list(CommunityActivity activity,int pageNo,int pageSize);
	
	/**
	 * 小区数据批量导入
	 * @param file 需要的文件（excel）
	 * @param fileName 文件名称
	 * @return
	 */
	@Transactional
	public Map<String, String> saveBatchImportCommunity(File file, String fileName);
	/**
	 * 跳转到商品关联小区页面,加载商品与小区关系
	 * @param activity 
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return 
	 */
	public Page GoodsCommunityList(CommunityActivity activity, int page, int pageSize);
	/**
	 * 跳转到商品选择页面
	 * @param activity 
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return 
	 */
	public Page getGoodsList(String goods_name, int page, int pageSize);

	/**
	 * 添加商品与小区关系
	 *@param listofjson 
	 * @return
	 */
	@Transactional
	public String add_commounity_goods_real(List<Map<String, String>> listofjson);

	/**
	 * 删除商品与小区关系
	 *@param listofjson 
	 * @return
	 */
	public String del_commounity_goods_real(Map<String, Object> mapofjson);

	

}
