package net.buffalo.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;


public class RoleService  {

	public boolean insertRole(HashMap Role ) throws Exception {
		Map param = new HashMap() ;
		param.put("Role", Role) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"insertRole" ,param)) ;
		return result ;
	}

	
	public boolean updateRole(HashMap Role ) throws Exception {
		Map param = new HashMap() ;
		param.put("Role", Role) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"updateRole" ,param)) ;
		
		return result ;
	}
	
	
	public List searchRoleData(String araId ) throws Exception {
		
		Map param = new HashMap() ;
		param.put("area_id", araId) ;
		
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("RoleBO",
						"searchRoleData" ,param)) ;
		return result ;
	}
	
	
	public Map getRoleById(String role_id) throws Exception {
		Map param = getRoleKeyMap(role_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("RoleBO",
						"getRoleById" ,param)) ;
		
		return result ;
	}
	

	public List findRoleByCond(String role_id) throws Exception {
		Map param = getRoleKeyMap(role_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("RoleBO",
						"findRoleByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteRoleById(String role_id) throws Exception {
		Map param = getRoleKeyMap(role_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"deleteRoleById" ,param)) ;
		
		return result ;
	}
	
	private Map getRoleKeyMap(String role_id){
		Map param = new HashMap() ;
				
		param.put("role_id", role_id) ;
				
		return param ;
	}
	
	public String loadPrivTree(String role_id) throws Exception{
		Map param = getRoleKeyMap(role_id) ;
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService("RoleBO",
						"loadPrivTree" ,param)) ;
		
		return result ;
	}
	
	public boolean deleteRolePriv(String role_priv_id)throws Exception{
		Map param = new HashMap() ;
    	param.put("role_priv_id", role_priv_id) ;
    	boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"deleteRolePriv" ,param)) ;
		return result ;
	}
	
	public String getOperJobId() throws Exception{
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService("RoleBO","getOperJobId" ,null)) ;
		
		return result ;
	}
	
	public String initRolePrivDialog(String operJobId) throws Exception{
		Map param = new HashMap() ;
    	param.put("oper_job_id", operJobId) ;
		
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService("RoleBO",
						"initRolePrivDialog" ,param)) ;
		
		return result ;
	}
	
	public List getPrivCodesByRoleId(String roleId) throws Exception{
		Map param = new HashMap() ;
    	param.put("role_id", roleId) ;
    	List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("RoleBO",
						"getPrivCodesByRoleId" ,param)) ;
		
		return result ;
	}
	
	public boolean insertRolePriv(HashMap RolePriv ) throws Exception {
		Map param = new HashMap() ;
		param.put("RolePriv", RolePriv) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"insertRolePriv" ,param)) ;
		return result ;
	}

	
	public boolean updateRolePriv(HashMap RolePriv ) throws Exception {
		Map param = new HashMap() ;
		param.put("RolePriv", RolePriv) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"updateRolePriv" ,param)) ;
		
		return result ;
	}
	
	
	
	
	public Map getRolePrivById(String role_priv_id) throws Exception {
		Map param = getRolePrivKeyMap(role_priv_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("RoleBO",
						"getRolePrivById" ,param)) ;
		
		return result ;
	}
	

	public List findRolePrivByCond(String role_priv_id) throws Exception {
		Map param = getRolePrivKeyMap(role_priv_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("RoleBO",
						"findRolePrivByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteRolePrivById(String role_priv_id) throws Exception {
		Map param = getRolePrivKeyMap(role_priv_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"deleteRolePrivById" ,param)) ;
		
		return result ;
	}
	
	private Map getRolePrivKeyMap(String role_priv_id){
		Map param = new HashMap() ;
				
		param.put("role_priv_id", role_priv_id) ;
				
		return param ;
	}
	
	public boolean batchInsertRolePriv(List rolePrivList) throws Exception {
		
		Map param = new HashMap() ;
		
		param.put("rolePrivList", rolePrivList) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("RoleBO",
						"batchInsertRolePriv" ,param)) ;
		
		return result;	
		
		
	}
	
}
