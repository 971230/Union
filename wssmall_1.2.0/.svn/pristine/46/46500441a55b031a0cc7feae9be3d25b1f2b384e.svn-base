package com.ztesoft.net.mall.core.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;

import com.ztesoft.cache.CacheList;
import com.ztesoft.cache.CacheMap;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.HostEnvVo;
import com.ztesoft.net.mall.core.service.IGrayDataSyncManager;
import com.ztesoft.util.CacheUtils;

public class GrayDataSyncManagerImpl extends BaseSupport  implements IGrayDataSyncManager{
	private INetCache cache = CacheFactory.getCacheByType("");
	private static final String dataSourceTableCache = "dataSourceTable";
	@SuppressWarnings("unchecked")
	@Override
	public String doGrayDataSync(final String data_type,final String from_db_db,final String to_db_db) {
		// TODO Auto-generated method stub
		 String result =this.baseDaoSupport.executeProc(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con)
					throws SQLException {
				// TODO Auto-generated method stub
				String storedProc = "{call P_MOVE_CONFIG_DATA(?,?,?,?)}";// 调用的sql   
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setString(1, data_type);// 设置输入参数的值  
				cs.setString(2, from_db_db);// 设置输入参数的值  
				cs.setString(3, to_db_db);// 设置输入参数的值  
				cs.registerOutParameter(4, OracleTypes.VARCHAR);// 注册输出参数的类型   
				return cs;

			}
		},new CallableStatementCallback<String>() {

			@Override
			public String doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				cs.execute();   
				return cs.getString(4);// 获取输出参数的值   

			}
		});
		 return result;
	}

	@Override
	public Page getHostEnvList(HostEnvVo hostEnvVo, int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		String sql ="select * from es_abgray_hostenv where 1=1 ";
		List param = new ArrayList();
		if(hostEnvVo!=null){
			if(!StringUtils.isEmpty(hostEnvVo.getEnv_name())){
				sql += " and env_name like ? ";
				param.add("%"+hostEnvVo.getEnv_name()+"%");
			}
			if(!StringUtils.isEmpty(hostEnvVo.getEnv_type())){
				sql += " and env_type like ? ";
				param.add("%"+hostEnvVo.getEnv_type()+"%");
			}
			if(!StringUtils.isEmpty(hostEnvVo.getEnv_code())){
				sql += " and env_code like ? ";
				param.add("%"+hostEnvVo.getEnv_code()+"%");
			}
			if(!StringUtils.isEmpty(hostEnvVo.getEnv_status())){
				sql += " and env_status = ? ";
				param.add(hostEnvVo.getEnv_status());
			}
		}
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, param.toArray());
	}

	@Override
	public List getHostEnvAllList() {
		// TODO Auto-generated method stub
		List list =( List)cache.get(Const.CACHE_SPACE,"abgray_hostenv");
		if(list==null||list.size()<=0){
			list = refreshHostEnv();
		}
		return list;
	}

	@Override
	public List refreshHostEnv() {
		// TODO Auto-generated method stub
		String sql ="select * from es_abgray_hostenv";
		List list = this.baseDaoSupport.queryForList(sql);
		CacheList cacheList = new CacheList<HostEnvVo>();
		cacheList.addAll(list);
		cache.set(Const.CACHE_SPACE,"abgray_hostenv", cacheList);
		return list;
	}

	@Override
	public void refrenshDataSourceConfig() {
		// TODO Auto-generated method stub
		String sql = "select table_name,data_source_name from es_data_source_config";
    	List<Map<String,Object>> list = baseDaoSupport.queryForList(sql);
		if(list!=null){
			CacheMap<String,String> dataTables = new CacheMap<String,String>();
			for(Map m:list){
				String tableName = (String) m.get("TABLE_NAME");
				String dataSourceName = (String) m.get("DATA_SOURCE_NAME");
				dataTables.put(tableName.toUpperCase(), dataSourceName);
			}
			CacheUtils.addCache(dataSourceTableCache, dataTables);
		}
	}

	@Override
	public List getDataSyncHostEnv() {
		// TODO Auto-generated method stub
		String sql ="SELECT env_type,env_status,LISTAGG(substr(host_info,instr(host_info,'.',1,2)+1), ',') WITHIN GROUP(ORDER BY rownum) AS host_info from es_abgray_hostenv where env_type in('ecsord_server','ecsord_server2.0','ecsord_ceshi','ecsord2.0_ceshi') group by  env_type,env_status ";
		return this.baseDaoSupport.queryForList(sql);
	}
	
}
