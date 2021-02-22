package com.ztesoft.net.mall.core.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.Organization;
import zte.net.ecsord.params.nd.vo.SrcUser;
import zte.net.ecsord.params.nd.vo.SynOrgMapping;
import zte.net.ecsord.params.nd.vo.TabOffice;
import zte.net.ecsord.params.nd.vo.User;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.sqls.SqlInBuilder;
import com.ztesoft.net.sqls.SqlUtil;

/**
 * 组织工号信息同步TASK
 * @author zhaoc
 *
 */
public class OrgUserSynTimer{

	private Logger log = Logger.getLogger(this.getClass());
	
	private Map<String,ES_DC_PUBLIC_DICT_RELATION> countyInfoMap = 
			new HashMap<String, ES_DC_PUBLIC_DICT_RELATION>();
	@SuppressWarnings("rawtypes")
	private IDaoSupport dao = null;

	public void doTask() throws Exception{
		
		try{
			if("N".equals(EopSetting.DB_CONNECT)){
				return;
			}
	    	
			if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "doTask")) {
	  			return;
			}
			
			Date start = new Date();
			
			log.error("组织工号信息同步TASK开始同步："+start.toString());
			
			if(dao==null)
				dao = SpringContextHolder.getBean("baseDaoSupport");
			
			if(countyInfoMap==null || countyInfoMap.size()==0)
				this.countyInfoMap = this.getCountyInfoMap();
			
			//组织同步
			this.doOrgSyn();
			
			//清除源组织不存在或者状态异常的已同步组织信息
			this.clearNotExistSynOrg();
			
			//人员同步
			this.doUserSyn();
			
			//清除源人员信息不存在或者状态异常的已同步人员信息
			this.clearNotExistSynUser();
			
			Date end = new Date();
			
			log.error("组织工号信息同步TASK同步结束："+end.toString());
			
			log.error("组织工号信息同步TASK同步耗时："+(end.getTime()-start.getTime())+"ms");
			
		}catch(Exception e){
			log.error("组织工号信息同步TASK同步失败：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 组织同步
	 * @throws Exception
	 */
	private void doOrgSyn() throws Exception{
		//获取基础组织映射信息
		List<SynOrgMapping> orgMappingList = this.getSynOrgMapping();
		
		//基础组织映射MAP
		Map<String,String> baseOrgMapping = new HashMap<String, String>();
		List<String> partyIdList = new ArrayList<String>();
		List<String> hqDeptIdList = new ArrayList<String>();
		
		for(SynOrgMapping m : orgMappingList){
			baseOrgMapping.put(m.getHq_dept_id(), m.getParty_id());
			
			partyIdList.add(m.getParty_id());
			hqDeptIdList.add(m.getHq_dept_id());
		}
		
		//查询基础组织已同步的组织信息
		Map<String,Organization> partyExistMap = this.getPartyIdOrgObjMap(partyIdList);
		Map<String,Organization> deptExistMap = this.gethqDeptIddOrgObjMap(hqDeptIdList);
		
		if(baseOrgMapping==null || baseOrgMapping.size()==0)
			throw new Exception("省、地市、县分组织映射为空");
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		//省公司查询SQL
		String queryOuterProvinceSql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_PROVNICE");
		
		if(StringUtils.isBlank(queryOuterProvinceSql))
			throw new Exception("外系统省公司组织的查询SQL配置为空");
		
		//外系统省公司信息
		List<TabOffice> rootOrg = this.getTableOffice(queryOuterProvinceSql);
		
		if(rootOrg==null || rootOrg.size()==0)
			throw new Exception("未查到外部系统省公司信息");
		
		String roothqDeptId = rootOrg.get(0).getHq_dept_id();
		
		if(!baseOrgMapping.containsKey(roothqDeptId)){
			throw new Exception("在组织映射表中未找到省公司组织hqDeptId="+ roothqDeptId + "的组织映射信息");
		}
		
		String rootPartyId = baseOrgMapping.get(roothqDeptId);
		
		if(!partyExistMap.containsKey(rootPartyId)){
			throw new Exception("根组织partyId="+ rootPartyId + "不存在");
		}
		
		//同步的最大组织深度
		int maxLevel = 20;
		//批次处理的上级组织
		int batchSize = 100;
		//上级组织map
		Map<String,Organization> parentOrgMap = new HashMap<String, Organization>();
		
		for(int count=0;count<maxLevel;count++){
			
			Map<String,Organization> tempParentMap = new HashMap<String, Organization>();
			Map<String,Organization> nextLevelParent = new HashMap<String, Organization>();
			
			if(count == 0){
				//初始同步省公司
				nextLevelParent.putAll(this.doSynChildOrg(roothqDeptId, parentOrgMap, 
						baseOrgMapping, partyExistMap, deptExistMap));
			}else{
				int c = 0;
				//根据传入的上级组织MAP查询下级组织并同步
				//传入的上级组织可能很多，批量处理同步
				for(Iterator<Entry<String, Organization>> it = parentOrgMap.entrySet().iterator();it.hasNext();){
					
					Entry<String, Organization> entry = it.next();
					tempParentMap.put(entry.getKey(), entry.getValue());
					
					if(++c % batchSize == 0){
						nextLevelParent.putAll(this.doSynChildOrg(null, tempParentMap, 
								baseOrgMapping, partyExistMap, deptExistMap));
						
						tempParentMap.clear();
					}
				}
				
				if(tempParentMap!=null && tempParentMap.size()>0){
					nextLevelParent.putAll(this.doSynChildOrg(null, tempParentMap, 
							baseOrgMapping, partyExistMap, deptExistMap));
					
					tempParentMap.clear();
				}
			}
			
			if(nextLevelParent==null || nextLevelParent.size()==0)
				//没有下级组织，跳出，组织同步结束
				break;
			else{
				//本次处理的组织成为下次处理的上级组织
				parentOrgMap = nextLevelParent;
			}
		}
	}
	
	/**
	 * 下级组织同步
	 * @param rootDeptId	根组织信息，这里为浙江省公司，初始同步时传入
	 * @param parentOrgMap	上级组织MAP
	 * @param baseOrgMapping	省公司，地市分公司，县区分公司基础组织映射关系
	 * @param partyExistMap		基础组织已同步的已partyId为主键的组织MAP
	 * @param deptExistMap		基础组织已同步的已hqDeptId为主键的组织MAP
	 * @return	本次同步的组织信息
	 * @throws Exception
	 */
	private Map<String,Organization> doSynChildOrg(String rootDeptId,Map<String,Organization> parentOrgMap,
			Map<String,String> baseOrgMapping,Map<String,Organization> partyExistMap,
			Map<String,Organization> deptExistMap) throws Exception{
		//已同步组织MAP
		Map<String,Organization> doneOrgMap = new HashMap<String, Organization>();
		
		//下级组织查询SQL
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String childOrgSql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_CHILD_ORG");
		
		if(StringUtils.isBlank(childOrgSql))
			throw new Exception("下级组织的查询SQL配置为空");
		
		String deptInSql = "";
		String channelInSql = "";
		
		if(StringUtils.isNotBlank(rootDeptId)){
			List<String> idList = new ArrayList<String>();
			
			idList.add(rootDeptId);
			
			deptInSql = SqlUtil.getSqlInStr("T.HQ_DEPT_ID", idList, false,true);
			channelInSql = SqlUtil.getSqlInStr("T1.CHANNEL_HQ_CODE", idList, false,true);
		}else if(parentOrgMap!=null && parentOrgMap.size()>0){
			List<String> idList = new ArrayList<String>(parentOrgMap.keySet());
			
			deptInSql = SqlUtil.getSqlInStr("T.ZB_ADMIN_DEPART_CODE", idList, false,true);
			channelInSql = SqlUtil.getSqlInStr("T2.MANAGER_DEPT_CODE", idList, false,true);
		}else{
			return doneOrgMap;
		}
		
		String querySql = childOrgSql.replace("{DEPT_CONDITION}", deptInSql);
		querySql = querySql.replace("{CHANNEL_CONDITION}", channelInSql);
		
		//查询待同步组织
		List<TabOffice> srcOrgs = this.getTableOffice(querySql);
		
		if(srcOrgs!=null && srcOrgs.size()>0){
			
			List<Organization> addOrgList = new ArrayList<Organization>();
			List<Organization> updateOrgList = new ArrayList<Organization>();
			
			//非基础组织HqDeptId列表
			List<String> otherHqDeptIdList = new ArrayList<String>();
			
			for(int i=0;i<srcOrgs.size();i++){
				if(!baseOrgMapping.containsKey(srcOrgs.get(i).getHq_dept_id())){
					otherHqDeptIdList.add(srcOrgs.get(i).getHq_dept_id());
				}
			}
			
			//根据HqDeptId查询已同步的非基础组织
			Map<String,Organization> otherOrgMap = this.gethqDeptIddOrgObjMap(otherHqDeptIdList);
			
			for(int i=0;i<srcOrgs.size();i++){
				TabOffice srcOrg = srcOrgs.get(i);
				
				//获取上级组织，省公司的上级组织为空
				Organization parentOrg = parentOrgMap.get(srcOrg.getZb_admin_depart_code());
				Organization doneOrg = null;
				
				String hqDeptId = srcOrg.getHq_dept_id();
				
				if(baseOrgMapping.containsKey(hqDeptId)){
					//基础组织
					String partyId = baseOrgMapping.get(hqDeptId);
					
					boolean isNew = false;//新增组织标识
					boolean isUpdate = false;//更新组织标识
					boolean isChangeMap = false;//修改组织映射标识
					
					Organization targetOrg = null;
					Organization nowOrg = null;
					
					if(partyExistMap.containsKey(partyId) &&
							deptExistMap.containsKey(hqDeptId)){
						targetOrg = partyExistMap.get(partyId);
						nowOrg = deptExistMap.get(hqDeptId);
						
						//根据partyId和hqDeptId都找到组织，需要判断是否同一组织
						if(targetOrg.getParty_id().equals(nowOrg.getParty_id())){
							//是同一组织的，更新
							isUpdate = true;
						}else{
							//不是同一组织，修改组织映射
							isChangeMap = true;
						}
					}else if(partyExistMap.containsKey(partyId) &&
							!deptExistMap.containsKey(hqDeptId)){
						//根据partyId能查到组织，但是hqDeptId都找不到组织，说明还未同步，合并更新组织信息
						targetOrg = partyExistMap.get(partyId);
						isUpdate = true;
					}else if(!partyExistMap.containsKey(partyId) &&
							deptExistMap.containsKey(hqDeptId)){
						//根据partyId查不到组织，但是hqDeptId查到组织，说明组织映射修改了
						nowOrg = deptExistMap.get(hqDeptId);
						isChangeMap = true;
					}else{
						//根据partyId和hqDeptId都找不到组织，需要新增
						isNew = true;
					}
					
					if(isNew){
						doneOrg = this.addOrg(partyId, srcOrg, parentOrg, addOrgList);
					}else if(isUpdate){
						doneOrg = this.updateOrg(targetOrg, srcOrg, parentOrg, updateOrgList);
					}else if(isChangeMap){
						//清除当前映射组织中的同步信息
						this.clearOrg(nowOrg, updateOrgList);
						
						//新增或更新映射组织
						if(targetOrg==null){
							doneOrg = this.addOrg(partyId, srcOrg, parentOrg, addOrgList);
						}else{
							doneOrg = this.updateOrg(targetOrg, srcOrg, parentOrg, updateOrgList);
						}
					}
				}else{
					//非基础组织
					if(otherOrgMap.containsKey(hqDeptId)){
						Organization targetOrg = otherOrgMap.get(hqDeptId);
						
						doneOrg = this.updateOrg(targetOrg, srcOrg,parentOrg, updateOrgList);
					}else{
						doneOrg = this.addOrg(srcOrg.getHq_dept_id(), srcOrg, parentOrg, addOrgList);
					}
				}
				
				if(doneOrg!=null)
					doneOrgMap.put(hqDeptId, doneOrg);
			}
			
			Map<String,Organization> addOrgMap = new HashMap<String, Organization>();
			for(int i=0;i<addOrgList.size();i++){
				addOrgMap.put(addOrgList.get(i).getParty_id(), addOrgList.get(i));
			}
			
			addOrgList = new ArrayList<Organization>(addOrgMap.values());
			
			Map<String,Organization> updateOrgMap = new HashMap<String, Organization>();
			for(int i=0;i<updateOrgList.size();i++){
				updateOrgMap.put(updateOrgList.get(i).getParty_id(), updateOrgList.get(i));
			}
			
			updateOrgList = new ArrayList<Organization>(updateOrgMap.values());
			
			dao.saveBatch("es_organization", "insert", addOrgList, new String[]{"party_id"});
			dao.saveBatch("es_organization", "update", updateOrgList, new String[]{"party_id"});
		}
		
		//本次处理的组织成为下次处理的上级组织	
		return doneOrgMap;
	}
	
	/**
	 * 获取partyId为KEY值的组织MAP
	 * @param partyIdList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Organization> getPartyIdOrgObjMap(List<String> partyIdList) throws Exception{
		Map<String,Organization> ret = new HashMap<String, Organization>();
		
		Organization popjo = new Organization();
		
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		SqlInBuilder partyIdBuilder = new SqlInBuilder("party_id", partyIdList);
		sqlBuilds.add(partyIdBuilder);
		
		List<Organization> paryOrgList = this.dao.queryPojoList("es_organization", popjo , sqlBuilds);
		
		if(paryOrgList!=null && paryOrgList.size()>0){
			for(int i=0;i<paryOrgList.size();i++){
				ret.put(paryOrgList.get(i).getParty_id(), paryOrgList.get(i));
			}
		}
		
		return ret;
	}
	
	/**
	 * 获取hqDeptId为KEY值的组织MAP
	 * @param hqDeptIdList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Organization> gethqDeptIddOrgObjMap(List<String> hqDeptIdList) throws Exception{
		Map<String,Organization> ret = new HashMap<String, Organization>();
		
		Organization popjo  = new Organization();
		
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		SqlInBuilder hqDeptIdBuilder = new SqlInBuilder("hq_dept_id", hqDeptIdList);
		sqlBuilds.add(hqDeptIdBuilder);
		
		List<Organization> hqDeptOrgList = this.dao.queryPojoList("es_organization", popjo, sqlBuilds);
		
		if(hqDeptOrgList!=null && hqDeptOrgList.size()>0){
			for(int i=0;i<hqDeptOrgList.size();i++){
				ret.put(hqDeptOrgList.get(i).getHq_dept_id(), hqDeptOrgList.get(i));
			}
		}
		
		return ret;
	}
	
	/**
	 * 待同步信息是否有变化
	 * @param target
	 * @param parent
	 * @param src
	 * @return
	 * @throws Exception
	 */
	private boolean isOrgChanged(Organization target,Organization parent,TabOffice src) throws Exception{
		if(!StringUtils.equals(target.getDept_id(), src.getDept_id()))
			return true;
		
		if(!StringUtils.equals(target.getHq_dept_id(), src.getHq_dept_id()))
			return true;
		
		if(!StringUtils.equals(target.getDept_name(), src.getDept_name()))
			return true;
		
		if(!StringUtils.equals(target.getDept_lvl(), src.getDept_lvl()))
			return true;
		
		if(!StringUtils.equals(target.getAreaid(), src.getAreaid()))
			return true;
		
		if(!StringUtils.equals(target.getCountyid(), src.getCountyid()))
			return true;
		
		if(!StringUtils.equals(target.getZb_admin_depart_code(), src.getZb_admin_depart_code()))
			return true;
		
		if(!StringUtils.equals(target.getZb_parent_depart_code(), src.getZb_parent_depart_code()))
			return true;
		
		if(!StringUtils.equals(target.getSyn_channel_type(), src.getSyn_channel_type()))
			return true;
		
		if(!StringUtils.equals(target.getSyn_sub_type(), src.getSyn_sub_type()))
			return true;
		
		if(!StringUtils.equals(target.getType_id(), src.getType_id()))
			return true;
		
		if(!StringUtils.equals(target.getChnl_kind_id(), src.getChnl_kind_id()))
			return true;
		
		if(!StringUtils.equals(target.getDept_status(), src.getDept_status()))
			return true;
		
		if(parent!=null && !StringUtils.equals(target.getParent_party_id(), parent.getParty_id()))
			return true;
		
		return false;
	}
	
	/**
	 * 获取组织类型
	 * @param srcOrg	BSS的源组织信息
	 * @return
	 * @throws Exception
	 */
	private String getOrgType(TabOffice srcOrg) throws Exception{
		String orgType = "";
		
		//组织类型
		if(StringUtils.isNotBlank(srcOrg.getChnl_kind_id())){
			if(srcOrg.getChnl_kind_id().startsWith("1")){
				orgType = "14";
			}else if(srcOrg.getChnl_kind_id().startsWith("2")){
				orgType = "15";
			}else{
				orgType = "16";
			}
		}else if("1001".equals(srcOrg.getDept_lvl())){
			orgType = "6";
		}else if("1002".equals(srcOrg.getDept_lvl())){
			orgType = "7";
		}else if("2000".equals(srcOrg.getDept_lvl())){
			orgType = "8";
		}else if("2001".equals(srcOrg.getDept_lvl())){
			orgType = "9";
		}else if("2002".equals(srcOrg.getDept_lvl())){
			orgType = "10";
		}else if("3000".equals(srcOrg.getDept_lvl())){
			orgType = "11";
		}else if("3001".equals(srcOrg.getDept_lvl())){
			orgType = "12";
		}else if("3002".equals(srcOrg.getDept_lvl())){
			orgType = "13";
		}else{
			orgType = "17";
		}
		
		return orgType;
	}
	
	/**
	 * 组织状态转换
	 * @param srcOrg
	 * @return
	 * @throws Exception
	 */
	private String transferStatus(TabOffice srcOrg) throws Exception{
		String status = "00X";
		
		if(StringUtils.isBlank(srcOrg.getChnl_kind_id())){
			//部门
			if("0".equals(srcOrg.getDept_status()))
				status = "00A";
		}else{
			//渠道
			if("30".equals(srcOrg.getDept_status()))
				status = "00A";
		}
		
		return status;
	}
	
	/**
	 * 新增组织处理
	 * @param partyId	组织编号
	 * @param srcOrg	BSS的源组织信息
	 * @param parent	云订单系统上级组织信息
	 * @param addOrgList	新增的组织列表
	 * @return	新增的组织信息
	 * @throws Exception
	 */
	private Organization addOrg(String partyId,TabOffice srcOrg,Organization parent,List<Organization> addOrgList) throws Exception{
		if(parent==null){
			throw new Exception("没有找到外系统"+srcOrg.getHq_dept_id()+"组织的上级同步组织");
		}
		
		if(!srcOrg.getZb_admin_depart_code().equals(parent.getHq_dept_id())){
			throw new Exception("传入上级组织错误！hq_dept_id="+srcOrg.getHq_dept_id()+
					"的上级组织为"+srcOrg.getZb_admin_depart_code()+"，传入的为"+parent.getHq_dept_id());
		}
		
		//新增时组织状态不正常的组织不需要同步
		String status = this.transferStatus(srcOrg);
		
		if("00X".equals(status))
			return null;
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		String orgLevel = String.valueOf(Integer.valueOf(parent.getOrg_level())+1);
		String orgType = this.getOrgType(srcOrg);
		
		Organization addOrg = new Organization();
		addOrg.setParty_id(partyId);
		
		//上级组织
		addOrg.setParent_party_id(parent.getParty_id());
		
		//设置组织信息
		addOrg.setOrg_code(partyId);
		addOrg.setOrg_name(srcOrg.getDept_name());
		addOrg.setOrg_level(orgLevel);
		addOrg.setStatus_cd(status);
		addOrg.setStatus_date(date);
		addOrg.setOrg_type_id(orgType);
		
		if(this.countyInfoMap.containsKey(srcOrg.getCountyid())){
			//源组织信息中县分为营业县分，根据营业县分取行政县分的地市，县区信息
			ES_DC_PUBLIC_DICT_RELATION info = this.countyInfoMap.get(srcOrg.getCountyid());
			String countyId = info.getField_value();
			String regionId = info.getCol1();
			countyId = countyId.replace("ZJ", "");
			
			addOrg.setRegion_id(countyId);
			addOrg.setLan_id(regionId);
		}else{
			//上级组织的地市，县分信息
			addOrg.setRegion_id(parent.getRegion_id());
			addOrg.setLan_id(parent.getLan_id());
		}
		
		addOrg.setCreate_date(date);
		addOrg.setCol1("001");
		addOrg.setCol4(partyId);
		addOrg.setSource_from("ECS");
		addOrg.setCreate_user_id("1");
		addOrg.setUpdate_date(date);
		addOrg.setUpdate_user_id("1");
		
		//更新同步信息
		addOrg.setDept_id(srcOrg.getDept_id());
		addOrg.setHq_dept_id(srcOrg.getHq_dept_id());
		addOrg.setDept_name(srcOrg.getDept_name());
		addOrg.setDept_lvl(srcOrg.getDept_lvl());
		addOrg.setAreaid(srcOrg.getAreaid());
		addOrg.setCountyid(srcOrg.getCountyid());
		addOrg.setZb_admin_depart_code(srcOrg.getZb_admin_depart_code());
		addOrg.setZb_parent_depart_code(srcOrg.getZb_parent_depart_code());
		
		addOrg.setSyn_channel_type(srcOrg.getSyn_channel_type());
		addOrg.setSyn_sub_type(srcOrg.getSyn_sub_type());
		addOrg.setType_id(srcOrg.getType_id());
		addOrg.setChnl_kind_id(srcOrg.getChnl_kind_id());
		addOrg.setDept_status(srcOrg.getDept_status());
		
		addOrg.setIs_syn("1");
		addOrg.setSyn_date(date);
		
		//插入新增列表中
		addOrgList.add(addOrg);
		
		return addOrg;
	}
	
	/**
	 * 更新组织处理
	 * @param targetOrg		待更新的组织信息
	 * @param srcOrg	BSS的源组织信息
	 * @param parent	云订单系统上级组织信息
	 * @param updateOrgList	更新的组织列表
	 * @return	更新的组织信息
	 * @throws Exception
	 */
	private Organization updateOrg(Organization targetOrg,TabOffice srcOrg,
			Organization parent,List<Organization> updateOrgList) throws Exception{
		if(!"1000".equals(srcOrg.getDept_lvl()) && parent==null){
			throw new Exception("更新hq_dept_id="+srcOrg.getHq_dept_id()+
					"组织出错！上级组织传入为空");
		}
		
		if(parent!=null && !srcOrg.getZb_admin_depart_code().equals(parent.getHq_dept_id())){
			throw new Exception("传入上级组织错误！hq_dept_id="+srcOrg.getHq_dept_id()+
					"的上级组织为"+srcOrg.getZb_admin_depart_code()+"，传入的为"+parent.getHq_dept_id());
		}
		
		if(this.isOrgChanged(targetOrg, parent, srcOrg)){
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
			String date = format.format(now);
			
			String targetSyn = "";
			
			String orgType = this.getOrgType(srcOrg);
			
			//更新时，不是新增同步的组织，都将同步标识改为2--合并同步
			if("1".equals(targetOrg.getIs_syn()))
				targetSyn = "1";
			else
				targetSyn = "2";
			
			//组织状态
			String status = this.transferStatus(srcOrg);
			
			targetOrg.setStatus_cd(status);
			
			//修改上级组织
			if(this.countyInfoMap.containsKey(srcOrg.getCountyid())){
				//源组织信息中县分为营业县分，根据营业县分取行政县分的地市，县区信息
				ES_DC_PUBLIC_DICT_RELATION info = this.countyInfoMap.get(srcOrg.getCountyid());
				String countyId = info.getField_value();
				String regionId = info.getCol1();
				countyId = countyId.replace("ZJ", "");
				
				targetOrg.setRegion_id(countyId);
				targetOrg.setLan_id(regionId);
			}else if(parent != null){
				//上级组织的地市，县分信息
				targetOrg.setRegion_id(parent.getRegion_id());
				targetOrg.setLan_id(parent.getLan_id());
			}else{
				targetOrg.setRegion_id("");
				targetOrg.setLan_id("");
			}
			
			//新增同步的组织可以修改组织类型
			if("1".equals(targetOrg.getIs_syn()))
				targetOrg.setOrg_type_id(orgType);
			
			//更新同步信息
			targetOrg.setDept_id(srcOrg.getDept_id());
			targetOrg.setHq_dept_id(srcOrg.getHq_dept_id());
			targetOrg.setDept_name(srcOrg.getDept_name());
			targetOrg.setDept_lvl(srcOrg.getDept_lvl());
			targetOrg.setAreaid(srcOrg.getAreaid());
			targetOrg.setCountyid(srcOrg.getCountyid());
			targetOrg.setZb_admin_depart_code(srcOrg.getZb_admin_depart_code());
			targetOrg.setZb_parent_depart_code(srcOrg.getZb_parent_depart_code());
			
			targetOrg.setSyn_channel_type(srcOrg.getSyn_channel_type());
			targetOrg.setSyn_sub_type(srcOrg.getSyn_sub_type());
			targetOrg.setType_id(srcOrg.getType_id());
			targetOrg.setChnl_kind_id(srcOrg.getChnl_kind_id());
			targetOrg.setDept_status(srcOrg.getDept_status());
			
			targetOrg.setIs_syn(targetSyn);
			targetOrg.setSyn_date(date);
			
			if("00X".equals(status)){
				//如果源组织状态为异常的，清除组织
				this.clearOrg(targetOrg, updateOrgList);
				
				return targetOrg;
			}else{
				//只是更新信息，插入更新列表中
				updateOrgList.add(targetOrg);
			}
		}
		
		return targetOrg;
	}
	
	/**
	 * 清除同步组织处理
	 * @param nowOrg
	 * @param updateOrgList
	 * @throws Exception
	 */
	private void clearOrg(Organization nowOrg,List<Organization> updateOrgList) throws Exception{
		if(nowOrg==null)
			return;
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		if("1".equals(nowOrg.getIs_syn())){
			//如果是同步新增的，修改组织状态为删除状态
			nowOrg.setStatus_cd("00X");
			nowOrg.setSyn_date(date);
		}else{
			//合并同步的，清空同步信息
			nowOrg.setDept_id(null);
			nowOrg.setHq_dept_id(null);
			nowOrg.setDept_name(null);
			nowOrg.setDept_lvl(null);
			nowOrg.setAreaid(null);
			nowOrg.setCountyid(null);
			nowOrg.setZb_admin_depart_code(null);
			nowOrg.setZb_parent_depart_code(null);
			
			nowOrg.setSyn_channel_type(null);
			nowOrg.setSyn_sub_type(null);
			nowOrg.setType_id(null);
			nowOrg.setChnl_kind_id(null);
			nowOrg.setDept_status(null);
			
			nowOrg.setIs_syn("0");
			nowOrg.setSyn_date(date);
		}
		
		updateOrgList.add(nowOrg);
	}
	
	/**
	 * 清除源组织不存在或者状态异常的已同步组织信息
	 * @throws Exception
	 */
	private void clearNotExistSynOrg() throws Exception{
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String notExistsql_1 = cacheUtil.getConfigInfo("SYN_ORG_USER_CLEAR_NOTEXIST_SYN_ORG_1");
		
		String notExistsql_2 = cacheUtil.getConfigInfo("SYN_ORG_USER_CLEAR_NOTEXIST_SYN_ORG_2");

		
		//清除源组织不存了的新增同步组织is_syn=1
		if(StringUtils.isNotBlank(notExistsql_1))
			this.dao.execute(notExistsql_1);
		
		//清除源组织不存了的合并同步组织is_syn=2
		if(StringUtils.isNotBlank(notExistsql_2))
			this.dao.execute(notExistsql_2);
	}
	
	/**
	 * 获取省公司、地市、县分组织映射
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private List<SynOrgMapping> getSynOrgMapping() throws Exception{
		
		String sql = "SELECT A.* FROM ES_SYN_ORG_MAP A";
		
		List<SynOrgMapping> orgMap = this.dao.queryForList(sql,SynOrgMapping.class);
		
		return orgMap;
	}
	
	/**
	 * 查询外系统组织信息
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private List<TabOffice> getTableOffice(String sql) throws Exception{
		
		List<TabOffice> ret = this.dao.queryForList(sql,TabOffice.class);
		
		return ret;
	}
	
	/**
	 * 同步用户
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private void doUserSyn() throws Exception{
		int pageNo = 1;
		int pageSize = 100;
		
		while(true){
			Organization popjo = new Organization();
			popjo.setStatus_cd("00A");
			
			//每次查询100条组织
			List<Organization> orgList = this.dao.queryPojoListWithPageNo("es_organization", popjo, 
					null, pageNo, pageSize);
			
			//如果没有组织了，跳出循环
			if(orgList==null || orgList.size()==0)
				break;
			
			Map<String,Organization> orgMap = new HashMap<String, Organization>();
			
			//以同步的省内编号为键值，创建orgMap
			for(int i=0;i<orgList.size();i++){
				String deptId = orgList.get(i).getDept_id();
				
				if(StringUtils.isNotBlank(deptId)){
					orgMap.put(deptId, orgList.get(i));
				}
			}
			
			if(orgMap==null || orgMap.size()==0)
				continue;
			
			List<String> deptIdList = new ArrayList<String>(orgMap.keySet());
			
			//根据归属组织查询用户
			Map<String,SrcUser> srcUserMap = this.getSrcUserMapByDeptIds(deptIdList);
			
			List<String> userIdList = new ArrayList<String>(srcUserMap.keySet());
			
			//根据用户编号查询已同步的用户信息
			Map<String,User> existUserMap = this.getUserMapByUserId(userIdList);
			
			List<User> addList = new ArrayList<User>();
			List<User> updateList = new ArrayList<User>();
			
			//同步数据
			for(Iterator<Entry<String, SrcUser>> it = srcUserMap.entrySet().iterator();it.hasNext();){
				SrcUser srcuser = it.next().getValue();
				String userId = srcuser.getUser_id();
				String deptId = srcuser.getSyn_dept_id();
				Organization org = orgMap.get(deptId);
				
				if(!existUserMap.containsKey(userId)){
					//用户不存在，新增
					User user = this.getNewUser(srcuser,org);
					
					addList.add(user);
				}else{
					//用户已存在，更新
					User user = existUserMap.get(userId);
					
					user = this.getUpdateUser(srcuser, user,org);
					
					if(user!=null)
						updateList.add(user);
				}
			}
			
			Map<String,User> addUserMap = new HashMap<String, User>();
			for(int i=0;i<addList.size();i++){
				addUserMap.put(addList.get(i).getUserid(), addList.get(i));
			}
			
			addList = new ArrayList<User>(addUserMap.values());
			
			Map<String,User> updateUserMap = new HashMap<String, User>();
			for(int i=0;i<updateList.size();i++){
				updateUserMap.put(updateList.get(i).getUserid(), updateList.get(i));
			}
			
			updateList = new ArrayList<User>(updateUserMap.values());
			
			this.dao.saveBatch("es_adminuser", "insert", addList, new String[]{"userid"});
			this.dao.saveBatch("es_adminuser", "update", updateList, new String[]{"userid"});
			
			//页数自加
			pageNo++;
		}
	}
	
	/**
	 * 新增用户
	 * @param srcUser	源用户
	 * @param org	归属组织
	 * @return
	 * @throws Exception
	 */
	private User getNewUser(SrcUser srcUser,Organization org) throws Exception{
		User user = new User();
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		String msg = "新增用户" + date;
		
		String password = StringUtil.md5("Password2018");
		
		String countyId = srcUser.getSyn_county_id();
		ES_DC_PUBLIC_DICT_RELATION countyInfo = null;
		if(this.countyInfoMap.containsKey(countyId))
			countyInfo = this.countyInfoMap.get(countyId);
		
		//设置用户级别信息
		user.setUserid(srcUser.getUser_id());
		user.setUsername(srcUser.getUser_id());
		user.setSex(srcUser.getSex());
		user.setPassword(password);
		user.setState("1");
		user.setRealname(srcUser.getUsername());
		
		user.setFounder("0");
		user.setParuserid("1");
		user.setCreate_time(date);
		
		//根据源数据的行政县分做转换
		if(countyInfo!=null){
			user.setLan_id(countyInfo.getCol1());
			user.setLan_name(countyInfo.getCol2());
			
			user.setCity_id(countyInfo.getField_value().replace("ZJ", ""));
			user.setCity(countyInfo.getField_value_desc());
		}else if(org != null){
			user.setLan_id(org.getLan_id());
			user.setLan_name("");
			user.setCity_id(org.getRegion_id());
			user.setCity("");
			
			msg = msg + "，用户所属县区未在营业县分与行政县分关系映射中找到";
		}else{
			user.setLan_id("");
			user.setLan_name("");
			user.setCity_id("");
			user.setCity("");
			
			msg = msg + "，用户所属县区未在营业县分与行政县分关系映射中找到";
		}
		
		user.setCur_logintime(date);
		user.setLast_logintime(date);
		user.setFail_logincount("0");
		user.setSuccess_logincount("0");
		user.setPhone_num(srcUser.getPhone_num());
		user.setOrg_id(org.getParty_id());
		user.setDep_name(org.getOrg_name());
		
		user.setSource_from("ECS");
		
		user.setLast_loginfail_time(date);
		user.setLogin_status("0");
		user.setCol6("0");
		user.setCol7("0");
		user.setIs_login_first("0");
		user.setUsertype("1");
		
		//设置同步信息
		user.setSyn_dept_id(srcUser.getSyn_dept_id());
		user.setSyn_hq_dept_id(org.getHq_dept_id());
		user.setSyn_dept_type(srcUser.getSyn_dept_type());
		user.setSyn_county_id(srcUser.getSyn_county_id());
		user.setSyn_user_id(srcUser.getSyn_user_id());
		user.setSyn_employee_id(srcUser.getSyn_employee_id());
		user.setSyn_use_domain(srcUser.getSyn_use_domain());
		user.setIs_syn("1");
		user.setSyn_date(date);
		user.setSyn_remark(msg);
		
		//TODO 设置用户角色
		
		return user;
	}
	
	/**
	 * 更新用户
	 * @param srcUser	源用户
	 * @param user	同步用户
	 * @param org	归属组织
	 * @return	更新后的用户，没有更新信息时返回null
	 * @throws Exception
	 */
	private User getUpdateUser(SrcUser srcUser,User user,Organization org) throws Exception{
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		String msg = "更新用户" + date;
		
		if(!"1".equals(user.getIs_syn())){
			//人员编号一样，但不是同步生成的人员，不能修改人员信息
			msg = msg + "，此人员的编号与待同步的人员编号一致但不是同步生成的人员，不能修改人员信息";
			user.setSyn_remark(msg);
			user.setSyn_date(date);
			
			return user;
		}
		
		if(!this.isUserChanged(srcUser, user, org)){
			return null;
		}
		
		String countyId = srcUser.getSyn_county_id();
		ES_DC_PUBLIC_DICT_RELATION countyInfo = null;
		if(this.countyInfoMap.containsKey(countyId))
			countyInfo = this.countyInfoMap.get(countyId);
		
		//设置用户级别信息
		user.setRealname(srcUser.getUsername());
		user.setSex(srcUser.getSex());
		
		user.setState("1");
		
		//根据源数据的行政县分做转换
		if(countyInfo!=null){
			user.setLan_id(countyInfo.getCol1());
			user.setLan_name(countyInfo.getCol2());
		}else{
			msg = msg + "，用户所属县区未在营业县分与行政县分关系映射中找到";
		}
		
		user.setPhone_num(srcUser.getPhone_num());
		user.setOrg_id(org.getParty_id());
		user.setDep_name(org.getOrg_name());
		
		//设置同步信息
		user.setSyn_dept_id(srcUser.getSyn_dept_id());
		user.setSyn_hq_dept_id(org.getHq_dept_id());
		user.setSyn_dept_type(srcUser.getSyn_dept_type());
		user.setSyn_county_id(srcUser.getSyn_county_id());
		user.setSyn_user_id(srcUser.getSyn_user_id());
		user.setSyn_employee_id(srcUser.getSyn_employee_id());
		user.setSyn_use_domain(srcUser.getSyn_use_domain());
		user.setSyn_date(date);
		user.setSyn_remark(msg);
		
		return user;
	}
	
	/**
	 * 判断待同步的用户信息是否改变
	 * @param srcUser	源用户
	 * @param user	同步用户
	 * @param org	归属组织信息
	 * @return	有信息改变返回true，否则返回false
	 * @throws Exception
	 */
	private boolean isUserChanged(SrcUser srcUser,User user,Organization org) throws Exception{
		if(!StringUtils.equals(srcUser.getUsername(), user.getRealname()))
			return true;
		
		if(!StringUtils.equals(srcUser.getPhone_num(), user.getPhone_num()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSex(), user.getSex()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_dept_id(), org.getDept_id()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_dept_type(), user.getSyn_dept_type()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_county_id(), user.getSyn_county_id()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_user_id(), user.getSyn_user_id()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_employee_id(), user.getSyn_employee_id()))
			return true;
		
		if(!StringUtils.equals(srcUser.getSyn_use_domain(), user.getSyn_use_domain()))
			return true;
		
		return false;
	}
	
	/**
	 * 获取以userid为键值的待同步用户MAP
	 * @param deptIdList	用户所属组织的省内编号
	 * @return	以user_id为KEY，用户信息为value的MAP
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private Map<String,SrcUser> getSrcUserMapByDeptIds(List<String> deptIdList) throws Exception{
		Map<String,SrcUser> userMap = new HashMap<String, SrcUser>();
		
		if(deptIdList==null || deptIdList.size()==0)
			return userMap;
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_SRC_USER");
		
		if(StringUtils.isBlank(sql))
			throw new Exception("查询同步源用户SQL配置为空");
		
		SrcUser popjo = new SrcUser();
		
		List<SqlBuilderInterface> sqlBuildList = new ArrayList<SqlBuilderInterface>();
		SqlInBuilder inBuilder = new SqlInBuilder("syn_dept_id", deptIdList);
		sqlBuildList.add(inBuilder);
		
		List<SrcUser> srcUserList = this.dao.queryPojoList(sql, popjo, sqlBuildList);
		
		if(srcUserList==null || srcUserList.size()==0)
			return userMap;
		
		for(int i=0;i<srcUserList.size();i++){
			String userId = srcUserList.get(i).getUser_id();
			
			userMap.put(userId, srcUserList.get(i));
		}
		
		return userMap;
	}
	
	/**
	 * 获取以userid为键值的已同步用户MAP
	 * @param userIdList	用户编号
	 * @return	userid为键值的已同步用户MAP
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private Map<String,User> getUserMapByUserId(List<String> userIdList) throws Exception{
		Map<String,User> userMap = new HashMap<String, User>();
		
		if(userIdList==null || userIdList.size()==0)
			return userMap;
		
		User pojo = new User();
		
		List<SqlBuilderInterface> sqlBuildList = new ArrayList<SqlBuilderInterface>();
		SqlInBuilder inBuilder = new SqlInBuilder("userid", userIdList);
		sqlBuildList.add(inBuilder);
		
		List<User> existUserList = this.dao.queryPojoList("es_adminuser", 
				pojo,sqlBuildList);
		
		if(existUserList==null || existUserList.size()==0)
			return userMap;
		
		for(int i=0;i<existUserList.size();i++){
			String userId = existUserList.get(i).getUserid();
			userMap.put(userId, existUserList.get(i));
		}
		
		return userMap;
	}
	
	/**
	 * 获取营业县分，行政县分信息
	 * @return	县分编号为KEY值的县分信息MAP
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	private Map<String,ES_DC_PUBLIC_DICT_RELATION> getCountyInfoMap() throws Exception{
		Map<String,ES_DC_PUBLIC_DICT_RELATION> map = new HashMap<String, ES_DC_PUBLIC_DICT_RELATION>();
		
		ES_DC_PUBLIC_DICT_RELATION param = new ES_DC_PUBLIC_DICT_RELATION();
		param.setStype("county");
		param.setSource_from("ECS");
		
		List<ES_DC_PUBLIC_DICT_RELATION> retList = this.dao.
				queryPojoList("es_dc_public_dict_relation", param, null);
		
		if(retList!=null && retList.size()>0){
			for(int i=0;i<retList.size();i++){
				String countyId = retList.get(i).getOther_field_value();
				
				map.put(countyId, retList.get(i));
			}
		}
		
		return map;
	}
	
	private void clearNotExistSynUser() throws Exception{
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String notExistsql = cacheUtil.getConfigInfo("SYN_ORG_USER_CLEAR_NOTEXIST_SYN_USER");
		
		//清除源用户不存了的同步用户
		if(StringUtils.isNotBlank(notExistsql))
			this.dao.execute(notExistsql);
	}
}
