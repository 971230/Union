package com.ztesoft.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.drools.core.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.common.util.ListUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.CommunityActivity;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.ICommunityManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.util.MoneyAuditExcelUtil;

import commons.CommonTools;


/**
 * 小区管理接口实现类
 * 
 * @author luwei<br/>
 *         2017-2-22下午9:41:07
 *         version 1.0
 */
public class CommunityManager extends BaseSupport
		implements ICommunityManager {
	
    @Resource
	private JdbcTemplate jdbcTemplate;

    
    /**
	 * 分页读取小区信息列表
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return  小区列表
	 */
    @Override
	public Page list(CommunityActivity activity,int pageNo, int pageSize) {
		AdminUser au = ManagerUtils.getAdminUser();
		String sql = SF.goodsSql("ES_COMMUNITY_GET");
		if(!StringUtil.isEmpty(activity.getCity_name())){
			//6位转AB
			String city_code = activity.getCity_name().trim();
			String sqlstr = "select t.field_value,t.other_field_value from es_dc_public_dict_relation t where t.stype = 'bss_area_code'";
			List list = this.baseDaoSupport.queryForList(sqlstr);
			if(!ListUtil.isEmpty(list)){
				for(Object obj:list){
					Map map = (Map)obj;
					if(city_code.equals(map.get("field_value"))){
						city_code = (String)map.get("other_field_value");
						break;
					}
				}
			}
			sql += " AND a.city_code = '"+city_code+"'";
		}
		if(!StringUtil.isEmpty(activity.getArea_name())){
			sql += " AND a.area_name like '%"+activity.getArea_name().trim()+"%'";
		}
		if(!StringUtil.isEmpty(activity.getCounty_comp_name())){
			sql += " AND a.county_comp_name like '%"+activity.getCounty_comp_name().trim()+"%'";
		}
		if(!StringUtil.isEmpty(activity.getCommunity_name())){
			sql += " AND a.community_name like '%"+activity.getCommunity_name().trim()+"%'";
		}
		sql += " order by a.community_code desc";
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	
    /**
	 * 小区数据批量导入
	 * @param file 需要的文件（excel）
	 * @param fileName 文件名称
	 * @return
	 */
    @Override
    @Transactional
	public Map<String, String> saveBatchImportCommunity(File file, String fileName) {
		//返回数据 默认处理成功
		Map<String, String> result = new HashMap<String, String>();
		result.put("result_code", "1");
		result.put("result_message", "导入成功！");
		//获取excel表中的数据
		List<Map<String, String>> communityDatas = this.getImportExcelOrderData(file, fileName);
		//excel表中的数据入库
		try {
			this.saveBatchImportOrder(communityDatas);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("excel表数据批量入库失败==》"+communityDatas);
			result.put("result_code", "-1");
			result.put("result_message", e.getMessage());
			return result;
		}
		return result;
	}
	/**
	 * 对excel表中的数据入库处理
	 * @param orderDatas
	 */
	public void saveBatchImportOrder(List<Map<String, String>> communityDatas){
		List<Map<String, String>> communityParams = new ArrayList<Map<String,String>>();
		String sql = "INSERT INTO es_community(community_code,community_name,city_code,city_name,area_code,"
				+ "area_name,county_comp_id,county_comp_name,std_addr,net_grid,source_from)"
				+ "VALUES (:community_code,:community_name,:city_code,:city_name,:area_code,"
				+ ":area_name,:county_comp_id,:county_comp_name,:std_addr,:net_grid,:source_from)";
		String sql_community = SF.goodsSql("ES_COMMUNITY_DEL");
		sql_community += " AND a.community_code =:community_code";
		for(Map<String, String> communityData : communityDatas){
			if(!StringUtils.isEmpty(communityData.get("community_code"))){
				String community_code = communityData.get("community_code");
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("community_code", community_code);
				communityParams.add(paramMap);
			}else{
				CommonTools.addFailError("EXCEL表格中【小区编码】不能为空！");
			}
			communityData.put("source_from", ManagerUtils.getSourceFrom());
		}
		//批量删除重复的小区数据
		this.baseDaoSupport.batchExecuteByListMap(sql_community, communityParams);
		//批量新增小区数据
		this.baseDaoSupport.batchExecuteByListMap(sql, communityDatas);
	}


	/**
	 * 获取excel表中的数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	private List<Map<String, String>> getImportExcelOrderData(File file, String fileName){
		//excel 表中的字段名称对应接口的字段名称  key 为接口字段名 value 为excel表对应的字段名
		Map<String,String> fieldMap=new HashMap<String, String>();
		fieldMap.put("city_code","地市ID" );
		fieldMap.put("city_name","地市" );
		fieldMap.put("area_code","行政区域ID" );
 		fieldMap.put("area_name","行政区域" );
 		fieldMap.put("county_comp_id","县分公司ID" );
 		fieldMap.put("county_comp_name","县分公司" );
		fieldMap.put("community_code","小区编号");
 		fieldMap.put("community_name","小区名称");
 		fieldMap.put("std_addr","标准地址" );
 		fieldMap.put("net_grid","网格" );
 		//从excel表中获取数据并转换为需要入库的对象
 		List<Map<String,String>> list= MoneyAuditExcelUtil.getBatchMap(file, fieldMap, null,fileName,1);
		return list;
	}

	/**
	 * 跳转到商品关联小区页面,加载商品与小区关系
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return 
	 */
	@Override
	public Page GoodsCommunityList(CommunityActivity activity,int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		String sql = SF.goodsSql("ES_COMMUNITY_GOODS_GET");
		if(!StringUtils.isEmpty(activity.getCommunity_code())){
			sql +=" AND a.community_code='"+activity.getCommunity_code()+"'";
		}
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}

	/**
	 * 跳转到商品选择页面
	 * @param activity 
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return 
	 */
	@Override
	public Page getGoodsList(String goods_name, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		String sql = SF.goodsSql("ES_GOODS_GET");
		if(!StringUtils.isEmpty(goods_name)){
			sql +=" AND a.name LIKE'%"+goods_name.trim()+"%'";
		}
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}

	/**
	 * 添加商品与小区关系
	 *@param listofjson 
	 * @return
	 */
	@Override
	@Transactional
	public String add_commounity_goods_real(List<Map<String, String>> listofjson) {
		// TODO Auto-generated method stub
		for (int i = 0; listofjson != null && i< listofjson.size(); i++) {
			Map<String, String> poMap = listofjson.get(i);
			poMap.put("source_from", ManagerUtils.getSourceFrom());
			poMap.put("status", "1");
			this.baseDaoSupport.insert("es_goods_rel_community", poMap, poMap);
		}
		return "添加成功！";
	}

	/**
	 * 删除商品与小区关系
	 *@param listofjson 
	 * @return
	 */
	@Override
	public String del_commounity_goods_real(Map<String,Object> mapofjson) {
		// TODO Auto-generated method stub
		String community_code = mapofjson.get("community_code").toString();
		String goods_id = mapofjson.get("goods_id").toString();
		String sql = SF.goodsSql("ES_COMMUNITY_GOODS_DEL");
		sql += " AND a.goods_id='"+goods_id+"'AND a.community_code='"+community_code+"'";
		this.baseDaoSupport.execute(sql.toString(), null);
		return "删除成功！";
	}
	
}
