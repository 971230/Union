package com.ztesoft.net.app.base.core.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.Organization;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.service.IOrgAdminManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.model.OrgPart;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SqlBuilder;
import com.ztesoft.net.sqls.SqlLikeBuilder;

import commons.CommonTools;

public class OrgAdminAction extends WWAction{
	

	private IOrgAdminManager orgAdminManager;
	
	private String org_id;
	private String party_id;
	private String org_code;
	private String parent_party_id;
	private String org_name;
	private String org_type_id;
	private String union_org_code;
	private String org_content;
	private String org_level;//组织等级
	private String parent_org_name;
	private List   lanList;//本地网列表
	private List   regionList;//辖区列表
	private List   areaList;//区域列表
	private String lan_id;
	private String region_id;
	private String channel_type;//渠道
	
	private String hq_dept_id;
	private String dept_id;
	
	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getParent_party_id() {
		return parent_party_id;
	}

	public void setParent_party_id(String parent_party_id) {
		this.parent_party_id = parent_party_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_type_id() {
		return org_type_id;
	}

	public void setOrg_type_id(String org_type_id) {
		this.org_type_id = org_type_id;
	}

	public String getUnion_org_code() {
		return union_org_code;
	}

	public void setUnion_org_code(String union_org_code) {
		this.union_org_code = union_org_code;
	}

	public String getOrg_content() {
		return org_content;
	}

	public void setOrg_content(String org_content) {
		this.org_content = org_content;
	}

	public IOrgAdminManager getOrgAdminManager() {
		return orgAdminManager;
	}

	public void setOrgAdminManager(IOrgAdminManager orgAdminManager) {
		this.orgAdminManager = orgAdminManager;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	
	
	public String getCurrOrg() throws Exception{
		
		Map map = new HashMap();
		String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
		List org_list = new ArrayList();
		if(StringUtils.isEmpty(login_org_id)) return "";
		
		if("1".equals(CommonTools.getUserId())){
			org_list = orgAdminManager.getRootOrg();
		}else{
			org_list = orgAdminManager.getCurrOrg(login_org_id);
		}
		
		if(!ListUtil.isEmpty(org_list)){
			
			List<String> idList = new ArrayList<String>();
			
			for(int i=0; i< org_list.size(); i++){
				Map o_map = (Map)org_list.get(i);
				String id = Const.getStrValue(o_map, "party_id");
				
				idList.add(id);
			}
			
			Set<String> pSet = orgAdminManager.getHasChildOrg(idList);
			
			for(int i=0; i< org_list.size(); i++){
				Map o_map = (Map)org_list.get(i);
				String id = Const.getStrValue(o_map, "party_id");

				if(pSet.contains(id)){
					o_map.put("state", "closed");
				}else{
					o_map.put("state", "open");
				}
			}
		}
		
		map.put("total", org_list.size());
		map.put("rows", org_list);
		this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
	}
	
	
	public String getChildrenOrg() throws Exception{
			
			if(StringUtils.isEmpty(org_id)) return "error";
			
			List children_list = orgAdminManager.getChildrenOrg(org_id);
			
			if(!ListUtil.isEmpty(children_list)){
				
				List<String> idList = new ArrayList<String>();
				
				for(int i=0; i< children_list.size(); i++){
					Map o_map = (Map)children_list.get(i);
					String id = Const.getStrValue(o_map, "party_id");
					
					idList.add(id);
				}
				
				Set<String> pSet = orgAdminManager.getHasChildOrg(idList);
				
				for(int i=0; i< children_list.size(); i++){
					Map o_map = (Map)children_list.get(i);
					String id = Const.getStrValue(o_map, "party_id");

					if(pSet.contains(id)){
						o_map.put("state", "closed");
					}else{
						o_map.put("state", "open");
					}
				}
			}
			
			this.json = JSON.toJSONString(children_list);
			return WWAction.JSON_MESSAGE;
	}
    /*部门列表 为第五级   根据父级ID和level=5来过来查询*/
	public String partList() throws Exception{
		this.webpage = this.orgAdminManager.listPart(this.getPage(), this.getPageSize(), this.parent_party_id, org_name);
		return "part_list";
	}
	public String orgChannelList(){
		List list = new ArrayList();
		list = this.orgAdminManager.listOrgChannelByType(channel_type);
		this.json = JSON.toJSONString(list);
		return WWAction.JSON_MESSAGE;
	}
	public String getOrg(){
		return "org_list";
	}
	
	//添加修改的时候初始化 本地网 ,辖区, 区域
	public String initAddOrModOrg(){
		String orgTypeName = this.orgAdminManager.getOrgTypeName(org_level);
		
		if("3".equals(org_level)){
			//查询本地网
		    this.lanList = this.orgAdminManager.listLanByProvId(party_id);
		    this.lanList.add(orgTypeName);
		    this.lanList.add(org_level);
		
		    this.json = JSON.toJSONString(lanList);
		    //this.json = "{orgTypeName:"+orgTypeName+",lanList:"+lanListJson+"}";
		    return WWAction.JSON_MESSAGE;
		}
		
		if("4".equals(org_level)){
			//查询辖区
			this.regionList = this.orgAdminManager.listRegionByLanId(lan_id);
			this.regionList.add(orgTypeName);
			this.regionList.add(org_level);
			this.json = JSON.toJSONString(regionList);
		    return WWAction.JSON_MESSAGE;
			
		}
		
		if("5".equals(org_level)){
			//查询区域
			
		}
		
		List list = new ArrayList();
		list.add(orgTypeName);
		list.add(org_level);
		this.json = JSON.toJSONString(list);
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String addOrg() throws UnsupportedEncodingException{
		this.org_name = java.net.URLDecoder.decode(this.org_name, "UTF-8");
		this.org_content = java.net.URLDecoder.decode(this.org_content, "UTF-8");
		
		Map<String,String> retMap = new HashMap<String, String>();
		
		try{
			String newId = orgAdminManager.getNewId();
			
			if(!orgAdminManager.ifExistsOrg(newId)){
				String name = java.net.URLDecoder.decode(org_name, "utf-8");
				
				String content = "";
				if(StringUtils.isNotEmpty(org_content)){
					content = java.net.URLDecoder.decode(org_content, "utf-8");
				}
				
				Organization org = new Organization();
				org.setParty_id(newId);
				org.setParent_party_id(parent_party_id);
				org.setOrg_code(newId);
				org.setOrg_name(name);
				org.setOrg_type_id(org_type_id);
				org.setUnion_org_code(union_org_code);
				org.setOrg_content(content);
				org.setOrg_level(org_level);
				org.setLan_id(lan_id);
				org.setRegion_id(region_id);
				org.setChannel_type(channel_type);
				org.setStatus_cd("00A");
				
				orgAdminManager.addOrg(org);
				
				retMap.put("code", "0");
				retMap.put("msg", "新增成功");
				retMap.put("newId", newId);
			}else{
				retMap.put("code", "1");
				retMap.put("msg", "存在相同的组织编码");
			}
		}catch(Exception e){
			e.printStackTrace();
			
			retMap.put("code", "1");
			retMap.put("msg", "新增失败:" + e.getMessage());
		}
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String modOrg(){
		Map<String,String> retMap = new HashMap<String, String>();
		
		try{
			String name = java.net.URLDecoder.decode(org_name, "utf-8");
			String content = "";
			if(StringUtils.isNotEmpty(org_content)){
				content = java.net.URLDecoder.decode(org_content, "utf-8");
			}
			
			Organization org = new Organization();
			org.setParty_id(party_id);
			org.setOrg_name(name);
			org.setOrg_content(content);
			org.setChannel_type(channel_type);
			org.setUnion_org_code(union_org_code);
			
			org.setOrg_type_id(org_type_id);
			org.setLan_id(lan_id);
			org.setRegion_id(region_id);
			
			orgAdminManager.modOrg(org);
			
			retMap.put("code", "0");
			retMap.put("msg", "修改成功");
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "修改失败:" + e.getMessage());
		}
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings("static-access")
	public String delOrg(){
		Map<String,String> retMap = new HashMap<String, String>();
		
		try{
			int cnt = orgAdminManager.qryChildrenOrgCount(party_id);
			
			if (cnt > 0) {
				retMap.put("code", "1");
				retMap.put("msg", "存在下级组织");
			} else {
				//不做物理删除，只是修改组织状态
				Organization org = new Organization();
				org.setParty_id(party_id);
				org.setStatus_cd("00X");
				
				orgAdminManager.modOrg(org);
				
				retMap.put("code", "0");
				retMap.put("msg", "删除成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			retMap.put("code", "1");
			retMap.put("msg", "删除失败");
		}
		
		this.json = JSON.toJSONString(retMap);
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 组织查询
	 * @return
	 */
	public String searchOrg() throws Exception{
		OrgPart pojo = new OrgPart();
		
		pojo.setParty_id(this.party_id);
		pojo.setOrg_name(this.org_name);
		pojo.setHq_dept_id(this.hq_dept_id);
		pojo.setDept_id(this.dept_id);
		
		this.webpage = this.orgAdminManager.searchOrgPage(this.getPage(), this.getPageSize(), pojo);
		
		return "searchOrg";
	}
	
	public String searchOrgTable() throws Exception{
		OrgPart pojo = new OrgPart();
		
		pojo.setParty_id(this.party_id);
		
		if(StringUtils.isNotBlank(this.org_name))
			this.org_name = URLDecoder.decode(this.org_name, "UTF-8");
		
		pojo.setOrg_name(this.org_name);
		pojo.setHq_dept_id(this.hq_dept_id);
		pojo.setDept_id(this.dept_id);
		pojo.setLan_id(this.lan_id);
		
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		
		SqlLikeBuilder orgNameBuilder = new SqlLikeBuilder("org_name", pojo.getOrg_name());
		sqlBuilds.add(orgNameBuilder);
		
		if(StringUtils.isNotBlank(this.region_id)){
			//获取营业县分
			ES_DC_PUBLIC_DICT_RELATION countyMap = this.orgAdminManager.getCountyMap(this.region_id);
			
			StringBuilder builder = new StringBuilder();
			builder.append(" ( ");
			
			builder.append(" party_id = '").append(this.region_id).append("' ");
			
			if(countyMap != null){
				builder.append(" or ");
				
				builder.append(" countyid = '").append(countyMap.getOther_field_value()).append("' ");
			}
			
			builder.append(" ) ");
			
			SqlBuilder countyBuilder = new SqlBuilder(builder.toString(), "");
			
			sqlBuilds.add(countyBuilder);
		}
		
		this.webpage = this.orgAdminManager.searchOrgPage(this.getPage(), 
				this.getPageSize(), pojo,sqlBuilds);
		
		return "searchOrgTable";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getLanRegionInfo() throws Exception{
		
		List lList = this.orgAdminManager.getAllLan();
		List rList = this.orgAdminManager.getAllRegioin();
		
		Map retMap = new HashMap();
		
		retMap.put("lan", lList);
		retMap.put("region", rList);
		
		this.json = JSON.toJSONString(retMap);
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getOrgChannel() throws Exception{
		
		List list = this.orgAdminManager.getAllOrgChannel();
		
		Map retMap = new HashMap();
		
		retMap.put("orgChannel", list);
		
		this.json = JSON.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getOrgType() throws Exception{
		
		List list = this.orgAdminManager.getAllOrgType();
		
		Map retMap = new HashMap();
		
		retMap.put("orgType", list);
		
		this.json = JSON.toJSONString(retMap);
		return JSON_MESSAGE;
	}
	
	/**
	 * 获取地市和行政县分信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getLanAndShowRegion() throws Exception{
		
		List lList = this.orgAdminManager.getAllLan();
		List rList = this.orgAdminManager.getShowRegioin();
		
		Map retMap = new HashMap();
		
		retMap.put("lan", lList);
		retMap.put("region", rList);
		
		this.json = JSON.toJSONString(retMap);
		return JSON_MESSAGE;
	}
	
	/**
	 * 获取地市和营业县分信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getLanAndBusiCounty() throws Exception{
		
		List lList = this.orgAdminManager.getAllLan();
		List rList = this.orgAdminManager.getBusiCounty();
		
		Map retMap = new HashMap();
		
		retMap.put("lan", lList);
		retMap.put("region", rList);
		
		this.json = JSON.toJSONString(retMap);
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPermissionRegionCounty() throws Exception{
		List lList = this.orgAdminManager.getAllLan();
		List rList = null;
		
		AdminUser user = ManagerUtils.getAdminUser();
		
		if(!"1".equals(user.getPermission_level())){
			//非省级权限
			
			//过滤地市权限
			List<String> pRegionList = user.getPermission_region();
			if(pRegionList==null)
				pRegionList = new ArrayList<String>();
			
			Set<String> regionSet = new HashSet<String>(pRegionList);
			
			if(lList!=null && lList.size()>0){
				Iterator it = lList.iterator();
				
				for(;it.hasNext();){
					Map region = (Map)it.next();
					
					if(!regionSet.contains(region.get("lan_id"))){
						it.remove();
					}
				}
			}
			
			//县分权限
			if("2".equals(user.getPermission_level())){
				//地市层级权限
				//根据地市查询县分
				rList = this.orgAdminManager.getCountyByIds(pRegionList,null);
			}else if("3".equals(user.getPermission_level())){
				//县分层级权限
				//根据县分编码查询营业县分
				rList = this.orgAdminManager.getCountyByIds(null,user.getPermission_county());
			}
		}else{
			//获取所有县分
			rList = this.orgAdminManager.getShowRegioin();
		}
		
		Map retMap = new HashMap();
		
		retMap.put("lan", lList);
		retMap.put("region", rList);
		retMap.put("level", user.getPermission_level());
		
		this.json = JSON.toJSONString(retMap);
		return JSON_MESSAGE;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getOrg_level() {
		return org_level;
	}

	public void setOrg_level(String org_level) {
		this.org_level = org_level;
	}

	public String getParent_org_name() {
		return parent_org_name;
	}

	public void setParent_org_name(String parent_org_name) {
		this.parent_org_name = parent_org_name;
	}

	public List getLanList() {
		return lanList;
	}

	public void setLanList(List lanList) {
		this.lanList = lanList;
	}

	public List getRegionList() {
		return regionList;
	}

	public void setRegionList(List regionList) {
		this.regionList = regionList;
	}

	public List getAreaList() {
		return areaList;
	}

	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getHq_dept_id() {
		return hq_dept_id;
	}

	public void setHq_dept_id(String hq_dept_id) {
		this.hq_dept_id = hq_dept_id;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	
}
