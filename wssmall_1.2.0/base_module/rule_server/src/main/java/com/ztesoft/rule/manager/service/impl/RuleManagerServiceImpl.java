/**
 * 
 */
package com.ztesoft.rule.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.common.annontion.context.action.DefaultActionBeanDefine;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.Assembly;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleRel;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.RuleCondCache;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.model.RuleObj;
import com.ztesoft.net.mall.core.model.RuleObjAttr;
import com.ztesoft.net.mall.core.model.RuleObjAttrRel;
import com.ztesoft.net.mall.core.model.RuleObjPlan;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rop.common.ServiceMethodDefinition;
import com.ztesoft.rop.common.ServiceMethodHandler;
import com.ztesoft.rule.manager.action.DirectoryVo;
import com.ztesoft.rule.manager.action.EditChildrenRuleVo;
import com.ztesoft.rule.manager.action.QueryObjAttrVo;
import com.ztesoft.rule.manager.action.RuleNodeVo;
import com.ztesoft.rule.manager.action.SchemeNodeVo;
import com.ztesoft.rule.manager.action.SearchObjAttrVo;
import com.ztesoft.rule.manager.action.SearchRuleByDirVo;
import com.ztesoft.rule.manager.action.SearchRuleByPlanVo;
import com.ztesoft.rule.manager.service.IRuleManagerService;
import commons.CommonTools;

/**
 * @author ZX RuleManagerServiceImpl.java 2014-9-16
 */
public class RuleManagerServiceImpl extends BaseSupport implements
		IRuleManagerService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ztesoft.rule.manager.service.IRuleManagerService#saveDirectory(com
	 * .ztesoft.rule.manager.action.DirectoryVo)
	 */
	@Override
	public void saveDirectory(DirectoryVo directoryVo) {
		// TODO Auto-generated method stub
		this.baseDaoSupport.insert("es_catalogue", directoryVo);
	}

	@Override
	public void editDirectory(DirectoryVo directoryVo) {
		// TODO Auto-generated method stub
		this.baseDaoSupport.update("es_catalogue", directoryVo, "id='" + directoryVo.getId() + "'");
	}

	@Override
	public DirectoryVo qryDirectory(String id) {
		// TODO Auto-generated method stub
		DirectoryVo directoryVo = null;
		String sql = "select t.* from es_catalogue t where t.id = ? order by t.id";
		List<Map> list = this.baseDaoSupport.queryForList(sql, id);
		if (list != null && list.size()>0) {
			Map map = list.get(0);
			if (map.size() > 0 && map != null) {
				directoryVo =	new DirectoryVo();
				directoryVo.setId(String.valueOf(map.get("ID")));
				directoryVo.setPid(String.valueOf(map.get("PID")));
				directoryVo.setName(String.valueOf(map.get("NAME")));
				directoryVo.setType(Integer.valueOf(String.valueOf(map.get("TYPE"))));
				directoryVo.setHint(String.valueOf(map.get("HINT")));
				directoryVo.setCreate_time(String.valueOf(map.get("CREATE_TIME")));
				directoryVo.setSource_from(String.valueOf(map.get("SOURCE_FROM")));
			}
		}
		return directoryVo;
	}

	@Override
	public void deleteDirectory(String id) {
		String sql = "delete from es_catalogue t where t.id=?";
		this.baseDaoSupport.execute(sql, id);		
	}

	@Override
	public List<DirectoryVo> qryDirectory(String id, String pid) {
		List<DirectoryVo> directoryVoList = new ArrayList<DirectoryVo>();
		DirectoryVo directoryVo = null;
		StringBuilder sql = new StringBuilder("select t.* from es_catalogue t where 1=1");
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and t.id = ?");
		}
		if (StringUtils.isNotBlank(pid)) {
			sql.append(" and t.pid = ?");
		}
		sql.append(" order by t.id ");
		List<Map> list = null;
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(pid))
			list = this.baseDaoSupport.queryForList(sql.toString(), id, pid);
		if (StringUtils.isBlank(id) && StringUtils.isNotBlank(pid))
			list = this.baseDaoSupport.queryForList(sql.toString(), pid);
		if (list != null && list.size()>0) {
			for (Map mp : list) {			
				if (mp.size() > 0 && mp != null) {
					directoryVo =	new DirectoryVo();
					directoryVo.setId(mp.get("ID")!=null ? String.valueOf(mp.get("ID")) : "");
					directoryVo.setPid(mp.get("PID")!=null ? String.valueOf(mp.get("PID")) : "");
					directoryVo.setName(mp.get("NAME")!=null ? String.valueOf(mp.get("NAME")) : "");
					directoryVo.setType(mp.get("TYPE")!=null&&StringUtils.isNotBlank(mp.get("TYPE").toString())
							? Integer.valueOf(String.valueOf(mp.get("TYPE"))) : 0);
					directoryVo.setHint(mp.get("HINT")!=null ? String.valueOf(mp.get("HINT")) : "");
					directoryVo.setCreate_time(mp.get("CREATE_TIME")!=null ? String.valueOf(mp.get("CREATE_TIME")) : "");
					directoryVo.setSource_from(mp.get("SOURCE_FROM")!=null ? String.valueOf(mp.get("SOURCE_FROM")) : "");
					directoryVoList.add(directoryVo);
				}
			}
		}
		return directoryVoList;
	}

	@Override
	public List<SchemeNodeVo> qrySchemeNode(String catalogueId) {
		// TODO Auto-generated method stub
		SchemeNodeVo schemeNodeVo = null;
		List<SchemeNodeVo> schemeNodeVoList = new ArrayList<SchemeNodeVo>();
		StringBuilder sql = new StringBuilder("SELECT T.PLAN_ID, T.PLAN_CODE, T.PLAN_NAME, " +
				"T.PLAN_TYPE, T.CATALOGUE_ID, T.COL1 FROM ES_RULE_BIZ_PLAN T WHERE T.CATALOGUE_ID=?");
		sql.append(" ORDER BY T.COL1 ASC, T.PLAN_ID ASC");
		List<Map> list = this.baseDaoSupport.queryForList(sql.toString(), catalogueId);
		if (list!=null && list.size()>0) {
			for (Map mp : list) {
				schemeNodeVo = new SchemeNodeVo();
				schemeNodeVo.setPlan_id(mp.get("PLAN_ID")!=null ? mp.get("PLAN_ID").toString() : "");
				schemeNodeVo.setPlan_code(mp.get("PLAN_CODE")!=null ? mp.get("PLAN_CODE").toString() : "");
				schemeNodeVo.setPlan_name(mp.get("PLAN_NAME")!=null ? mp.get("PLAN_NAME").toString() : "");
				schemeNodeVo.setPlan_type(mp.get("PLAN_TYPE")!=null ? mp.get("PLAN_TYPE").toString() : "");
				schemeNodeVo.setCatalogue_id(mp.get("CATALOGUE_ID")!=null ? mp.get("CATALOGUE_ID").toString() : "");
				schemeNodeVo.setCol1(mp.get("COL1")!=null ? mp.get("COL1").toString() : "");
				schemeNodeVoList.add(schemeNodeVo);
			}
		}
		return schemeNodeVoList;
	}

	@Override
	public List<RuleNodeVo> qryRuleNode(String plan_id, String pid) {
		// TODO Auto-generated method stub
		RuleNodeVo ruleNodeVo = null;
		List<RuleNodeVo> ruleNodeVoList = new ArrayList<RuleNodeVo>();
		StringBuilder sql = new StringBuilder("SELECT ERC.RULE_ID, ERC.RULE_CODE, ERC.RULE_NAME, " +
				"ERC.PID, ERE.PLAN_ID, ERC.ASS_ID, ERE.PRIORITY, ERE.AUTO_EXE, ERE.REL_TYPE FROM " +
				"ES_RULE_CONFIG_AUDIT ERC, ES_RULE_REL_AUDIT ERE " +
				"WHERE 1=1 AND ERC.STATUS_CD<>'00H' AND ERC.SOURCE_FROM=ERE.SOURCE_FROM " +
				"AND ERC.RULE_ID=ERE.RULE_ID AND ERE.PLAN_ID=? AND ERC.PID=? ORDER BY ERE.PRIORITY ASC");
		List<Map> list = this.daoSupport.queryForList(sql.toString(), plan_id, pid);
		
		if (list != null && list.size()>0) {
			for (Map mp : list) {
				ruleNodeVo = new RuleNodeVo();
				ruleNodeVo.setRule_id(mp.get("RULE_ID")!=null ? mp.get("RULE_ID").toString() : "");
				ruleNodeVo.setRule_code(mp.get("RULE_CODE")!=null ? mp.get("RULE_CODE").toString() : "");
				ruleNodeVo.setRule_name(mp.get("RULE_NAME")!=null ? mp.get("RULE_NAME").toString() : "");
				ruleNodeVo.setPid(mp.get("PID")!=null ? mp.get("PID").toString() : "");
				ruleNodeVo.setPlan_id(mp.get("PLAN_ID")!=null ? mp.get("PLAN_ID").toString() : "");
				ruleNodeVo.setAss_id(mp.get("ASS_ID")!=null ? mp.get("ASS_ID").toString() : "");
				ruleNodeVo.setPriority(mp.get("PRIORITY")!=null ? mp.get("PRIORITY").toString() : "");
				ruleNodeVo.setAuto_exe(mp.get("AUTO_EXE")!=null ? mp.get("AUTO_EXE").toString() : "");
				ruleNodeVo.setRel_type(mp.get("REL_TYPE")!=null ? mp.get("REL_TYPE").toString() : "");
				ruleNodeVoList.add(ruleNodeVo);
			}
		}
		
		return ruleNodeVoList;
	}

	@Override
	public RuleObjPlan qrySchemeObj(String plan_id) {
		// TODO Auto-generated method stub
		RuleObjPlan ruleObjPlan = null;
		StringBuilder sql = new StringBuilder("SELECT T.* FROM ES_RULE_BIZ_PLAN T WHERE T.PLAN_ID=?");
		ruleObjPlan = (RuleObjPlan) this.baseDaoSupport.queryForObject(sql.toString(), RuleObjPlan.class, plan_id);		
		return ruleObjPlan;
	}

	@Override
	public void editSchemeObj(RuleObjPlan schemeObj) {
		// TODO Auto-generated method stub		
		this.baseDaoSupport.update("es_rule_biz_plan", schemeObj, "plan_id='"+schemeObj.getPlan_id()+"'");
		
	}

	@Override
	public void saveSchemeObj(RuleObjPlan schemeObj) {
		// TODO Auto-generated method stub
		if (schemeObj != null) {
			schemeObj.setPlan_code(this.daoSupport.getSequences("SEQ_PLAN_CODE", "1", 18));
			try {
				schemeObj.setExec_time(DateUtil.getTime1());
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 执行时间
			schemeObj.setExec_cycle("week"); // 执行周期
			schemeObj.setStatus_cd("00A"); // 审批状态
		}
		this.baseDaoSupport.insert("ES_RULE_BIZ_PLAN", schemeObj);
	}

	@Override
	public List<Map> qryPidList(String id) {
		// TODO Auto-generated method stub
		
		StringBuilder sql = new StringBuilder("SELECT T.ID, T.NAME FROM ES_CATALOGUE T WHERE 1=1");
		if (StringUtils.isNotBlank(id))
			sql.append(" AND T.ID = ? ORDER BY T.ID");
		else
			sql.append(" AND T.ID <> ? ORDER BY T.ID");
		return this.baseDaoSupport.queryForList(sql.toString(), "0");
	}

	@Override
	public List<RuleConfig> qryRuleObj(String rule_id, String plan_id, String pid) {
		// TODO Auto-generated method stub
		List<RuleConfig> ruleConfigLs = null;
		RuleConfig ruleConfig = null;
		StringBuilder sql = new StringBuilder("SELECT ERC.RULE_ID, ERC.RULE_CODE, " +
				"ERC.RULE_NAME, ERC.PID, ERC.ASS_ID, ERC.RULE_DESC, ERC.EXP_DATE, " +
				"ERC.EFF_DATE, ERC.CREATE_DATE, ERC.CREATE_USER, ERC.STATUS_CD, " +
				"ERR.PLAN_ID FROM ES_RULE_CONFIG_AUDIT ERC, ES_RULE_REL_AUDIT ERR " +
				"WHERE ERC.SOURCE_FROM=ERR.SOURCE_FROM AND ERC.RULE_ID=ERR.RULE_ID AND ERC.STATUS_CD<>'00H'");
		if (StringUtils.isNotBlank(plan_id))
			sql.append(" AND ERR.PLAN_ID='"+plan_id+"'");
		if (StringUtils.isNotBlank(rule_id))
			sql.append(" AND ERC.RULE_ID='"+rule_id+"'");
		if (StringUtils.isNotBlank(pid))
			sql.append(" AND ERC.PID='"+pid+"'");
		sql.append(" ORDER BY ERR.PRIORITY ASC");
		List<Map> lsMp = this.baseDaoSupport.queryForList(sql.toString());
		if (lsMp != null && lsMp.size()>0) {
			ruleConfigLs = new ArrayList<RuleConfig>();
			for (Map mp : lsMp) {
				ruleConfig = new RuleConfig();
				RuleRel ruleRel = null;
				ruleConfig.setRule_id(mp.get("RULE_ID")!=null?mp.get("RULE_ID").toString():"");
				ruleConfig.setRule_code(mp.get("RULE_CODE")!=null?mp.get("RULE_CODE").toString():"");
				ruleConfig.setRule_name(mp.get("RULE_NAME")!=null?mp.get("RULE_NAME").toString():"");
				ruleConfig.setExp_date(mp.get("EXP_DATE")!=null?mp.get("EXP_DATE").toString():"");
				ruleConfig.setEff_date(mp.get("EFF_DATE")!=null?mp.get("EFF_DATE").toString():"");				
				ruleConfig.setCreate_date(mp.get("CREATE_DATE")!=null?mp.get("CREATE_DATE").toString():"");
				ruleConfig.setCreate_user(mp.get("CREATE_USER")!=null?mp.get("CREATE_USER").toString():"");
				ruleConfig.setStatus_cd(mp.get("STATUS_CD")!=null?mp.get("STATUS_CD").toString():"");				
				ruleConfig.setPid(mp.get("PID")!=null?mp.get("PID").toString():"");
				ruleConfig.setAss_id(mp.get("ASS_ID")!=null?mp.get("ASS_ID").toString():"");
				ruleConfig.setRule_desc(mp.get("RULE_DESC")!=null?mp.get("RULE_DESC").toString():"");
				ruleConfig.setPlan_id(mp.get("PLAN_ID")!=null?mp.get("PLAN_ID").toString():"");
				if (StringUtils.isNotBlank(ruleConfig.getAss_id())) {
					Assembly assembly = qryAssembly(ruleConfig.getAss_id());
					ruleConfig.setAss_code(assembly!=null?assembly.getAss_code():"");
				}
				if (mp.get("RULE_ID") != null && mp.get("PLAN_ID") != null) {
					StringBuilder rule_rel_sql = new StringBuilder("SELECT T.* FROM ES_RULE_REL_AUDIT T WHERE 1=1");
					if (StringUtils.isNotBlank(mp.get("RULE_ID").toString()))
						rule_rel_sql.append(" AND T.RULE_ID='"+mp.get("RULE_ID").toString()+"'");
					if (StringUtils.isNotBlank(mp.get("PLAN_ID").toString()))
						rule_rel_sql.append(" AND T.PLAN_ID='"+mp.get("PLAN_ID").toString()+"'");
					ruleRel = (RuleRel) this.baseDaoSupport.queryForObject(rule_rel_sql.toString(), RuleRel.class);
					if (ruleRel != null)
						ruleConfig.setRuleRel(ruleRel);
				}
				ruleConfigLs.add(ruleConfig);
			}
		}
		return ruleConfigLs;
	}
	
	@Override
	public Page qryAssemblyLs(int pageNO, int pageSize, Assembly assembly, AdminUser adminUser) {
		
		List<Assembly> assemblyList = null;
		StringBuilder statusCountSql = new StringBuilder("SELECT T.ASS_ID, T.ASS_NAME, T.ASS_CODE, " +
				"T.EXE_PATH, T.STATUS, T.CREATE_TIME, T.HINT FROM ES_ASSEMBLY T WHERE 1=1");
		StringBuilder cSql = new StringBuilder("SELECT COUNT(EA.ASS_ID) FROM ES_ASSEMBLY EA WHERE 1=1 AND EA.SOURCE_FROM IS NOT NULL");
		if (assembly != null) {
			if (StringUtils.isNotBlank(assembly.getAss_name()))
				statusCountSql.append(" AND T.ASS_NAME like '%"+assembly.getAss_name()+"%'");
			if (StringUtils.isNotBlank(assembly.getAss_id()))
				statusCountSql.append(" AND T.ASS_ID='"+assembly.getAss_id()+"'");
		}
//		statusCountSql.append(" AND T.SOURCE_FROM='ECSORD'");
		statusCountSql.append(" ORDER BY T.ASS_ID");
		return this.baseDaoSupport.queryForCPage(statusCountSql.toString(), pageNO, pageSize, 
				Assembly.class, cSql.toString());
//		if (CollectionUtils.isNotEmpty(lsMp)) {
//			assemblyList = new ArrayList<Assembly>();
//			for (Map mp : lsMp) {
//				assembly = new Assembly();
//				assembly.setAss_id(mp.get("ASS_ID")!=null?mp.get("ASS_ID").toString():"");
//				assembly.setAss_name(mp.get("ASS_NAME")!=null?mp.get("ASS_NAME").toString():"");
//				assembly.setAss_code(mp.get("ASS_CODE")!=null?mp.get("ASS_CODE").toString():"");
//				assembly.setExe_path(mp.get("EXE_PATH")!=null?mp.get("EXE_PATH").toString():"");
//				assembly.setStatus(mp.get("STATUS")!=null?Integer.valueOf(mp.get("STATUS").toString()):0);
//				assembly.setCreate_time(mp.get("CREATE_TIME")!=null?mp.get("CREATE_TIME").toString():"");
//				assembly.setHint(mp.get("HINT")!=null?mp.get("HINT").toString():"");
//				assemblyList.add(assembly);
//			}
//		}
//		return assemblyList;
	}
	@Override
	public Assembly qryAssembly(String ass_id) {
		Assembly assembly = new Assembly();		
		StringBuilder sql = new StringBuilder("SELECT T.* FROM ES_ASSEMBLY T WHERE 1=1");
		if (StringUtils.isNotBlank(ass_id)) {
			sql.append(" AND T.ASS_ID='"+ass_id+"'");
			List<Map> assemblyList = this.baseDaoSupport.queryForList(sql.toString());
			if (assemblyList!=null && assemblyList.size()>0) {
				Map mp = assemblyList.get(0);
				assembly.setAss_id(mp.get("ASS_ID")!=null?mp.get("ASS_ID").toString():"");
				assembly.setAss_code(mp.get("ASS_CODE")!=null?mp.get("ASS_CODE").toString():"");
				assembly.setAss_name(mp.get("ASS_NAME")!=null?mp.get("ASS_NAME").toString():"");
				assembly.setExe_path(mp.get("EXE_PATH")!=null?mp.get("EXE_PATH").toString():"");
				assembly.setStatus(mp.get("STATUS")!=null?Integer.valueOf(mp.get("STATUS").toString()):0);
				return assembly;
			}
		} else {
			return assembly;
		}
		return assembly;
	}
	
	@Override
	public void editRuleConfig(RuleConfig ruleConfig) {
		// TODO Auto-generated method stub
		if (ruleConfig != null) {
			this.baseDaoSupport.update("ES_RULE_CONFIG_AUDIT", ruleConfig, "RULE_ID='"+ruleConfig.getRule_id()+"' AND STATUS_CD<>'00H'");
			this.baseDaoSupport.update("ES_RULE_CONFIG", ruleConfig, "RULE_ID='"+ruleConfig.getRule_id()+"' AND STATUS_CD<>'00H'");
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveRuleConfig(RuleConfig ruleConfig) {
		// TODO Auto-generated method stub
		if (ruleConfig != null) {
			ruleConfig.setRule_code(this.daoSupport.getSequences("SEQ_RULE_CODE", "1", 18));
			ruleConfig.setStatus_cd("00A");
			this.baseDaoSupport.insert("ES_RULE_CONFIG_AUDIT", ruleConfig);
			this.baseDaoSupport.insert("ES_RULE_CONFIG", ruleConfig);
			RuleRel ruleRel = new RuleRel();
			ruleRel.setRule_id(ruleConfig.getRule_id());
			ruleRel.setPlan_id(ruleConfig.getPlan_id());
			this.baseDaoSupport.insert("ES_RULE_REL_AUDIT", ruleRel);
			this.baseDaoSupport.insert("ES_RULE_REL", ruleRel);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RuleObj> getRuleObjList() {
		List<RuleObj> ruleObjectList = null;
		String objSql = SF.ruleSql("RULE_OBJ_LIST");
		String objAttrSql = SF.ruleSql("RULE_OBJ_ATTR_LIST");
		try{
			ruleObjectList = this.daoSupport.queryForList(objSql, RuleObj.class);
			if(null != ruleObjectList && !ruleObjectList.isEmpty()){
				for(int i = 0; i < ruleObjectList.size(); i++){
					RuleObj ruleObj = ruleObjectList.get(i);
					List<RuleObjAttr> ruleObjAttrList = this.daoSupport.queryForList(objAttrSql, RuleObjAttr.class, ruleObj.getObj_id());
					ruleObj.setObjAttrList(ruleObjAttrList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ruleObjectList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void saveRuleAttr(String ruleAttrStr, String condType,String rule_id) {
		String sql = SF.ruleSql("RULE_OBJ_ATTR_REL_LIST");
		JSONArray jsonArray = JSONArray.fromObject(ruleAttrStr);
		//先删除属性
		String delSql = SF.ruleSql("DEL_OBJ_ATTR_REL_LIST");
		this.baseDaoSupport.execute(delSql, rule_id, condType);
		
		if(jsonArray.size() == 0)
			return ;
		//保存属性
		List<String[]> listParam = new ArrayList<String[]>();
		String source_from = ManagerUtils.getSourceFrom();
		for(int i = 0; i < jsonArray.size(); i++){
			Map<String, Object> map = (Map<String, Object>)jsonArray.get(i);
			String attr_id = map.get("attr_id") == null ? "" : map.get("attr_id").toString();
			String obj_id = map.get("obj_id") == null ? "" : map.get("obj_id").toString();
			String obj_name = map.get("obj_name") == null ? "" : map.get("obj_name").toString();
			String attr_code = map.get("attr_code") == null ? "" : map.get("attr_code").toString();
			String attr_name = map.get("attr_name") == null ? "" : map.get("attr_name").toString();
			String ele_type = map.get("ele_type") == null ? "" : map.get("ele_type").toString();
			String ele_value = map.get("ele_value") == null ? "" : map.get("ele_value").toString();
			String cond_type = map.get("cond_type") == null ? "" : map.get("cond_type").toString();
			String obj_code = map.get("obj_code") == null ? "" : map.get("obj_code").toString();
			String[] params = new String[]{rule_id, attr_id, obj_id, attr_code, 
					attr_name, ele_type, ele_value, source_from, obj_name, cond_type, obj_code};
			listParam.add(params);
		}
		this.daoSupport.batchExecute(sql, listParam);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RuleObjAttrRel> getSelectedAttr(String rule_id, String cond_type) {
		String sql = SF.ruleSql("RULE_SELECT_ATTR");
		List<RuleObjAttrRel> result = null;;
		try{
			result = this.baseDaoSupport.queryForList(sql, RuleObjAttrRel.class, rule_id, cond_type);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	protected IAutoRule autoRuleImpl;
	
	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}

	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}
	
	@Override
	public void editChildrenRule(EditChildrenRuleVo editChildrenRuleVo) {
		// TODO Auto-generated method stub
		if (editChildrenRuleVo != null) {
			
			String attr_children_id = (editChildrenRuleVo.getAttr_children_id()!=null?editChildrenRuleVo.getAttr_children_id():",,");
			String attr_priority = (editChildrenRuleVo.getAttr_priority()!=null?editChildrenRuleVo.getAttr_priority():",,");
			String attr_rel = (editChildrenRuleVo.getAttr_rel()!=null?editChildrenRuleVo.getAttr_rel():",,");
			String attr_relyon = (editChildrenRuleVo.getAttr_relyon()!=null?editChildrenRuleVo.getAttr_relyon():",,");
			String attr_status = (editChildrenRuleVo.getAttr_status()!=null?editChildrenRuleVo.getAttr_status():",,");
			String attr_autoexe = (editChildrenRuleVo.getAttr_autoexe()!=null?editChildrenRuleVo.getAttr_autoexe():",,"); 
			
			String[] array_children_id = attr_children_id.split(",");
			int length = array_children_id.length;
			String[] array_priority = getStringArray(attr_priority, length);
			String[] array_rel = getStringArray(attr_rel, length);
			String[] array_relyon = getStringArray(attr_relyon, length);
			String[] array_status = getStringArray(attr_status, length);
			String[] array_autoexe = getStringArray(attr_autoexe, length);
			
			for (int i = 0; i < array_children_id.length; i ++) {
				Map<String, String> rel_map = new HashMap<String, String>();
				Map<String, String> rule_map = new HashMap<String, String>();
				rel_map.put("PRIORITY", StringUtils.isNotBlank(array_priority[i])?array_priority[i]:"");
				rel_map.put("REL_TYPE", StringUtils.isNotBlank(array_rel[i])?array_rel[i]:"");
				rel_map.put("RELYON_RULE_ID", StringUtils.isNotBlank(array_relyon[i])?array_relyon[i]:"");
				rel_map.put("AUTO_EXE", StringUtils.isNotBlank(array_autoexe[i])?array_autoexe[i]:"");
				rule_map.put("STATUS_CD", StringUtils.isNotBlank(array_status[i])?array_status[i]:"");
				this.baseDaoSupport.update("ES_RULE_REL_AUDIT", rel_map, "RULE_ID='"+array_children_id[i]+"'");
				this.baseDaoSupport.update("ES_RULE_REL", rel_map, "RULE_ID='"+array_children_id[i]+"'");
				this.baseDaoSupport.update("ES_RULE_CONFIG_AUDIT", rule_map, "RULE_ID='"+array_children_id[i]+"' AND STATUS_CD<>'00H'");
				this.baseDaoSupport.update("ES_RULE_CONFIG", rule_map, "RULE_ID='"+array_children_id[i]+"' AND STATUS_CD<>'00H'");
			}
		}
	}
	
	@Override
	public Integer refreshAssembly() {
		// TODO Auto-generated method stub

		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
		Map<String, ServiceMethodHandler> map = context.getAllServiceMethodHandlers();
		ServiceMethodHandler serviceMethodHandler = null;
		if (map != null && map.size() > 0) {
			for(String key : map.keySet()) {
				StringBuilder qry_sql = new StringBuilder("SELECT * FROM ES_ASSEMBLY WHERE 1=1 ");
				serviceMethodHandler = map.get(key);
				ServiceMethodDefinition serviceMethodDefinition = serviceMethodHandler.getServiceMethodDefinition();
				String title = serviceMethodDefinition.getMethodTitle(); // 组件名称
				String path = serviceMethodDefinition.getMethod(); // 组件路径
				qry_sql.append(" AND EXE_PATH = '"+path+"'");
				List<Map> ass_list = this.baseDaoSupport.queryForList(qry_sql.toString());
				if (ass_list!=null && ass_list.size() > 0) {
					Map mp = new HashMap();
					mp.put("ASS_NAME", title);
					mp.put("EXE_PATH", path);
					try {
						mp.put("CREATE_TIME", DateUtil.getTime1());
					} catch (FrameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.baseDaoSupport.update("ES_ASSEMBLY", mp, " EXE_PATH='"+ass_list.get(0).get("EXE_PATH")+"'");
				} else {
					Assembly assembly = new Assembly();
					assembly.setAss_name(title);
					assembly.setExe_path(path);
					assembly.setAss_code(path);
					try {
						assembly.setCreate_time(DateUtil.getTime1());
					} catch (FrameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					assembly.setStatus(0);
					this.baseDaoSupport.insert("ES_ASSEMBLY", assembly);
				}
			}
		}
		return map.size();
	}

	@Override
	public List<RuleNodeVo> qryRuleNodeByNames(String ruleName) {
		// TODO Auto-generated method stub
		
		RuleNodeVo ruleNodeVo = null;
		List<RuleNodeVo> ruleNodeVoList = new ArrayList<RuleNodeVo>();
		StringBuilder sql = new StringBuilder("SELECT ERC.RULE_ID, ERC.RULE_CODE, ERC.RULE_NAME, " +
				"ERC.PID, ERE.PLAN_ID, ERC.ASS_ID FROM ES_RULE_CONFIG_AUDIT ERC, ES_RULE_REL_AUDIT ERE " +
				"WHERE 1=1 AND ERC.SOURCE_FROM=ERE.SOURCE_FROM " +
				"AND ERC.RULE_ID=ERE.RULE_ID AND ERC.STATUS_CD<>'00H' AND ERC.RULE_NAME like '%"+ruleName+"%'");
		List<Map> list = this.daoSupport.queryForList(sql.toString());
		
		if (list != null && list.size()>0) {
			for (Map mp : list) {
				ruleNodeVo = new RuleNodeVo();
				ruleNodeVo.setRule_id(mp.get("RULE_ID")!=null ? mp.get("RULE_ID").toString() : "");
				ruleNodeVo.setRule_code(mp.get("RULE_CODE")!=null ? mp.get("RULE_CODE").toString() : "");
				ruleNodeVo.setRule_name(mp.get("RULE_NAME")!=null ? mp.get("RULE_NAME").toString() : "");
				ruleNodeVo.setPid(mp.get("PID")!=null ? mp.get("PID").toString() : "");
				ruleNodeVo.setPlan_id(mp.get("PLAN_ID")!=null ? mp.get("PLAN_ID").toString() : "");
				ruleNodeVo.setAss_id(mp.get("ASS_ID")!=null ? mp.get("ASS_ID").toString() : "");
				ruleNodeVoList.add(ruleNodeVo);
			}
		}
		
		return ruleNodeVoList;
	}

	@Override
	public void delRule(String rule_id, String plan_id) {
		// TODO Auto-generated method stub
		StringBuilder rule_sql = new StringBuilder("SELECT T.RULE_ID FROM ES_RULE_CONFIG_AUDIT T WHERE T.STATUS_CD<>'00H' AND (T.RULE_ID='"+rule_id+"' OR T.PID='"+rule_id+"')");
		List<Map> rule_list = this.baseDaoSupport.queryForList(rule_sql.toString());
		List<String> ruleid_list = new ArrayList<String>();
		if (rule_list!=null && rule_list.size()>0) {
			for (Map map : rule_list) {
				ruleid_list.add(map.get("RULE_ID")!=null?map.get("RULE_ID").toString():"");
			}
		}
		for (String ruleid : ruleid_list) {
			StringBuilder del_rule_audit_sql = new StringBuilder("DELETE FROM ES_RULE_CONFIG_AUDIT T WHERE T.RULE_ID='"+ruleid+"' AND T.STATUS_CD<>'00H'");
			StringBuilder del_rule_sql = new StringBuilder("DELETE FROM ES_RULE_CONFIG T WHERE T.RULE_ID='"+ruleid+"'");
			this.baseDaoSupport.execute(del_rule_sql.toString());
			this.baseDaoSupport.execute(del_rule_audit_sql.toString());
			StringBuilder del_rule_rel_audit_sql = new StringBuilder("DELETE FROM ES_RULE_REL_AUDIT T WHERE T.RULE_ID='"+ruleid+"'");
			StringBuilder del_rule_rel_sql = new StringBuilder("DELETE FROM ES_RULE_REL T WHERE T.RULE_ID='"+ruleid+"'");
			if (StringUtils.isNotBlank(plan_id)) {
				del_rule_rel_audit_sql.append(" AND T.PLAN_ID='"+plan_id+"'");
				del_rule_rel_sql.append(" AND T.PLAN_ID='"+plan_id+"'");
			}
			this.baseDaoSupport.execute(del_rule_rel_audit_sql.toString());
			this.baseDaoSupport.execute(del_rule_rel_sql.toString());
		}
	}

	private String[] getStringArray(String str , int length) {
		String[] array = new String[length];
		if (StringUtils.isNotBlank(str)) {
			String[] ss = str.split(",");
			int i = 0;
			for (String s : ss) {
				array[i] = s;
				i ++;
			}
		}
		return array;
	}

	@Override
	public void delScheme(RuleObjPlan ruleObjPlan) {
		// TODO Auto-generated method stub
		
		StringBuilder rule_sql = new StringBuilder("SELECT RR.RULE_ID FROM ES_RULE_CONFIG RC, ES_RULE_REL RR");
		rule_sql.append(" WHERE RC.RULE_ID=RR.RULE_ID AND RC.SOURCE_FROM=RR.SOURCE_FROM AND RR.PLAN_ID='"+ruleObjPlan.getPlan_id()+"'");
		List<Map> rule_ls = this.baseDaoSupport.queryForList(rule_sql.toString());
		
		if (rule_ls!=null&&rule_ls.size()>0) {
			StringBuilder sb = new StringBuilder("");
			for(int i=0; i<rule_ls.size(); i ++) {
				Map mp = rule_ls.get(i);
				if (i != 0)
					sb.append(",'"+mp.get("RULE_ID")+"'");
				else
					sb.append("'"+mp.get("RULE_ID")+"'");
			}
			StringBuilder del_ruleCofing_sql = new StringBuilder("DELETE FROM ES_RULE_CONFIG T");
			del_ruleCofing_sql.append(" WHERE T.RULE_ID IN ("+sb.toString()+")");
			StringBuilder del_ruleCofing_sql_audit = new StringBuilder("DELETE FROM ES_RULE_CONFIG_AUDIT T");
			del_ruleCofing_sql_audit.append(" WHERE T.RULE_ID IN ("+sb.toString()+")");
			StringBuilder del_ruleRel_sql = new StringBuilder("DELETE FROM ES_RULE_REL T");
			del_ruleRel_sql.append(" WHERE T.RULE_ID IN ("+sb.toString()+")");
			StringBuilder del_ruleRes_sql_audit = new StringBuilder("DELETE FROM ES_RULE_REL_AUDIT T");
			del_ruleRes_sql_audit.append(" WHERE T.RULE_ID IN ("+sb.toString()+")");
			this.baseDaoSupport.execute(del_ruleCofing_sql.toString());
			this.baseDaoSupport.execute(del_ruleCofing_sql_audit.toString());
			this.baseDaoSupport.execute(del_ruleRel_sql.toString());
			this.baseDaoSupport.execute(del_ruleRes_sql_audit.toString());
		}

		StringBuilder scheme_sql = new StringBuilder("DELETE FROM ES_RULE_BIZ_PLAN T");
		scheme_sql.append(" WHERE T.PLAN_ID='"+ruleObjPlan.getPlan_id()+"'");			
		this.baseDaoSupport.execute(rule_sql.toString());
		this.baseDaoSupport.execute(scheme_sql.toString());
		
	}

	@Override
	public List<RuleObjPlan> getRuleObjPlanList() {
		// TODO Auto-generated method stub
		List<RuleObjPlan> ruleObjPlanList = null;
		StringBuilder sql = new StringBuilder("select t.* from es_rule_biz_plan t where 1=1");
		ruleObjPlanList = baseDaoSupport.queryForList(sql.toString(), RuleObjPlan.class);
		return ruleObjPlanList;
	}
	
	@Override
	public List<DirectoryVo> getRuleObjDirList() {
		// TODO Auto-generated method stub
		List<DirectoryVo> list = new ArrayList<DirectoryVo>();
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" T.*");
		sql.append(" FROM");
		sql.append(" ES_CATALOGUE T");
		sql.append(" WHERE");
		sql.append(" T.SOURCE_FROM=?");
		list = baseDaoSupport.queryForList(sql.toString(), DirectoryVo.class, ManagerUtils.getSourceFrom());
		return list;
	}
	
	@Override
	public List<RuleObj> getRuleObjListByPlanId(String planId, String dirId) {
		String plan_id = "";
		if (StringUtils.isNotBlank(planId)&&!planId.equals("-1")) {
			plan_id = planId;
		} else if (StringUtils.isNotBlank(dirId)&&!dirId.equals("-1")) {
			plan_id = getPlanListByDirId(dirId).get(0).getPlan_id();
		}
		
		String obj_id = "";
		StringBuilder sql = new StringBuilder("");
		sql.append("select tt.obj_id from es_rule_rel t, es_rule_obj_attr_rel tt");
		sql.append(" where 1=1");
		sql.append(" and t.source_from=tt.source_from");
		sql.append(" and t.rule_id=tt.rule_id");
		sql.append(" and t.plan_id='"+plan_id+"'");
		List<Map> forList = baseDaoSupport.queryForList(sql.toString());
		if (forList != null && forList.size() > 0) {
			obj_id = (forList.get(0).get("obj_id")).toString(); // 根据方案ID找到Fact对象ID即obj_id
		}
		List<RuleObj> ruleObjectList = null;
		String objSql = SF.ruleSql("RULE_OBJ_LIST");
		String objAttrSql = SF.ruleSql("RULE_OBJ_ATTR_LIST");
		objSql += (" and obj_id='"+obj_id+"'");
		try{
			ruleObjectList = this.daoSupport.queryForList(objSql, RuleObj.class);
			if(null != ruleObjectList && !ruleObjectList.isEmpty()){
				for(int i = 0; i < ruleObjectList.size(); i++){
					RuleObj ruleObj = ruleObjectList.get(i);
					List<RuleObjAttr> ruleObjAttrList = this.daoSupport.queryForList(objAttrSql, RuleObjAttr.class, ruleObj.getObj_id());
					ruleObj.setObjAttrList(ruleObjAttrList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ruleObjectList;
	}

	@Override
	public Page searchRuleConfig(String searchCond, String is_include, String planId, String dirId, int pageNo, int pageSize, String queryRuleName) {
		Page page = new Page();
//		if (searchCond.equals("without_cond") 
//				|| searchCond.equals("never_run_cond")) {
//			page = queryRuleByCond(searchCond, pageNo, pageSize, queryRuleName);
//		} else {
//			page = queryRuleByRel(searchCond, is_include, pageNo, pageSize, queryRuleName);
//		}
		String plan_id = "";
		String dir_id = "";
		List list = new ArrayList();
		if (StringUtils.isNotBlank(planId)&&!planId.equals("-1")) {
			plan_id = planId;
			list = searchPlanRuleByPlan(toSearchRuleVo(searchCond, plan_id));
		} else if (StringUtils.isNotBlank(dirId)&&!dirId.equals("-1")) {
			List<RuleObjPlan> ruleObjPlanList = getPlanListByDirId(dirId);
			for (RuleObjPlan plan : ruleObjPlanList) {
				List listResult = searchPlanRuleByPlan(toSearchRuleVo(searchCond, plan.getPlan_id()));
				list.addAll(listResult);
			}
		}
		if (list != null && list.size() > 10) {
			int total = list.size();
			List pageList = new ArrayList();
			int p_size = 0;
			if (pageNo*pageSize % total >= 10) {
				p_size = pageNo*pageSize;
			} else {
				p_size = total;
			}
			for (int i = (pageNo-1)*pageSize; i < p_size; i ++ ) {
				pageList.add(list.get(i));
			}
			page.setData(pageList);
		} else if (list != null && list.size() <= 10) {
			page.setData(list);
		}
		page.setPageSize(10);
		page.setTotalCount(list.size());
		return page;
	}

	@Override
	public boolean savePlanCond(String plan_id, String plan_cond) {
		boolean is_success = false;
		RuleObjPlan ruleObjPlan = getRuleObjPlan(plan_id);
		ruleObjPlan.setCol2(plan_cond);
		StringBuilder where = new StringBuilder("");
		where.append("PLAN_ID='"+plan_id+"'");
		where.append("AND SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'");
		try {
			baseDaoSupport.update("ES_RULE_BIZ_PLAN", ruleObjPlan, where.toString());
			is_success = true;
		} catch (Exception e) {
			// TODO: handle exception
			is_success = false;
			e.printStackTrace();
		}
		
		return is_success;
	}
	
	/**
	 * 根据目录ID获取改目录下的所有方案
	 * @param dir_id
	 * @return
	 */
	private List<RuleObjPlan> getPlanListByDirId(String dir_id) {
		List<RuleObjPlan> ruleObjPlanList = new ArrayList<RuleObjPlan>();
		String sql = "SELECT T.* FROM ES_RULE_BIZ_PLAN T WHERE T.CATALOGUE_ID=? AND T.SOURCE_FROM=?";
		ruleObjPlanList = baseDaoSupport.queryForList(sql, RuleObjPlan.class, dir_id, ManagerUtils.getSourceFrom());
		return ruleObjPlanList;
	}
	
	private SearchRuleByPlanVo toSearchRuleVo(String searchCond, String plan_id) {
		SearchRuleByPlanVo searchRuleVo = new SearchRuleByPlanVo();
		searchRuleVo.setPlan_id(plan_id);
		String[] filters = searchCond.split(",");
		if (filters != null && filters.length > 0) {
			List<SearchObjAttrVo> objAttrList = new ArrayList<SearchObjAttrVo>();
			for (String searchVal : filters) {
				SearchObjAttrVo vo = new SearchObjAttrVo();
				String[] strs = searchVal.split(":");
				String[] attrs = strs[0].split("#");
				String[] attr_val = strs[1].split("#");
				vo.setObj_code(attrs[0]);
				vo.setAttr_code(attrs[1]);
				vo.setIs_include(attrs[3]);
				List<String> attr_value = new ArrayList<String>();
				for (String val : attr_val) {
					attr_value.add(val);
				}
				vo.setAttr_value(attr_value);
				objAttrList.add(vo);
			}
			searchRuleVo.setObjAttrList(objAttrList);
		}
		return searchRuleVo;
	}
	
	private Page queryRuleByCond(String searchCond, int pageNo, int pageSize, String qRuleName) {
		StringBuilder sql = new StringBuilder("SELECT T.*, TT.RULE_NAME");
		sql.append(" FROM ES_RULE_OBJ_ATTR_REL T, ES_RULE_CONFIG TT");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.RULE_ID=TT.RULE_ID");
		sql.append(" AND T.SOURCE_FROM=TT.SOURCE_FROM");
		sql.append(" AND T.ATTR_CODE='"+searchCond+"'");
		if (StringUtils.isNotBlank(qRuleName)) {
			sql.append(" AND TT.RULE_NAME LIKE '%"+qRuleName+"%'");
		}
		String cSql = "SELECT COUNT(C.RULE_ID) FROM ("+sql.toString()+") C";
		Page page = baseDaoSupport.queryForCPage(sql.toString(), pageNo, pageSize, RuleObjAttrRel.class, cSql);
		return page;
	}
	
	private Page queryRuleByRel0(String searchCond, 
			String is_include, String qRuleName) {
		// TODO Auto-generated method stub
		Page page = new Page();
		if (StringUtils.isNotBlank(searchCond)) {
			StringBuilder sql = new StringBuilder("SELECT T.*, RC.RULE_NAME");
			sql.append(" FROM ES_RULE_OBJ_ATTR_REL T, ES_RULE_CONFIG RC");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.SOURCE_FROM=RC.SOURCE_FROM");
			sql.append(" AND T.RULE_ID=RC.RULE_ID");
			sql.append(" AND T.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'");
			if (StringUtils.isNotBlank(qRuleName)) {
				sql.append(" AND RC.RULE_NAME LIKE '%"+qRuleName+"%'");
			}
			StringBuilder sbSql = new StringBuilder("");
			String[] searchConds = searchCond.split(",");
			for (String s : searchConds) {
				sql.append(" AND T.OBJ_ID='"+s.split("_")[0]+"'");
				sql.append(" AND T.ATTR_ID='"+s.split("_")[1]+"'");
			}
			String cSql = ("SELECT COUNT(C.RULE_ID) FROM ("+sql.toString()+") C");
			List<RuleObjAttrRel> relList = baseDaoSupport.queryForList(sql.toString(), RuleObjAttrRel.class);
			for (RuleObjAttrRel rel : relList) {
				
			}
			
			return page;
		}
		return page;
	}
	
	/**
	 * 分页查询规则
	 * @param searchCond
	 * @param is_include
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private Page queryRuleByRel(String searchCond, 
			String is_include, int pageNo, int pageSize, String qRuleName) {
		// TODO Auto-generated method stub
		Page page = new Page();
		if (StringUtils.isNotBlank(searchCond)) {
			StringBuilder sql = new StringBuilder("SELECT T.*, RC.RULE_NAME");
			sql.append(" FROM ES_RULE_OBJ_ATTR_REL T, ES_RULE_CONFIG RC");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.SOURCE_FROM=RC.SOURCE_FROM");
			sql.append(" AND T.RULE_ID=RC.RULE_ID");
			sql.append(" AND T.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'");
			if (StringUtils.isNotBlank(qRuleName)) {
				sql.append(" AND RC.RULE_NAME LIKE '%"+qRuleName+"%'");
			}
			String sSql = splitSToSql(searchCond, is_include);
			sql.append(sSql);
			String cSql = ("SELECT COUNT(C.RULE_ID) FROM ("+sql.toString()+") C");
			page = baseDaoSupport.queryForCPage(sql.toString(), pageNo, pageSize, RuleObjAttrRel.class, cSql);
			return page;
		}
		return page;
	}
	
	/**
	 * 将查询条件转化成SQL
	 * @param searchCond
	 * @param is_include 是否包含（0-包含，1-包含）
	 * @return
	 */
	private String splitSToSql(String searchCond, String is_include) {
		// 页面瓶装的查询条件（格式："objId_attrid_eleType:attrValue0_attrValue1,objId_attrid_eleType:attrValue0_attrValue1"）
		// StringBuilder sbSql = new StringBuilder("(");
		StringBuilder sbSql = new StringBuilder("");
		String[] searchConds = searchCond.split(",");
		int i = 0;
		for(String strs : searchConds) {
			String[] ss = strs.split(":"); // 格式 'objId_attrid_eleType:attrValue0_attrValue1'
			String[] attr_obj_ids = ss[0].split("_"); // objId,attrId,eleType,is_include集合
			String[] attr_obj_values = ss[1].split("_"); // attrId集合（属性值（页面如果没有勾选传到后台是"_"））
			String sql = splitSsToSql(attr_obj_ids, attr_obj_values, attr_obj_ids[3]);
			if (i == 0) { // 以第一条查询条件为主，后面全部用"AND"匹配第一条查询出来的结果，取交集
				sbSql.append(" AND "+sql);
			} else {
				sbSql.append(" AND T.RULE_ID IN ("+getHeadSql());
				sbSql.append(" AND " + sql);
				sbSql.append(")");
			}
			i ++;
		}
		return sbSql.toString();
	}
	/**
	 * 统一拼查询条件（界面右侧属性条件）
	 * @param attr_obj_ids
	 * @param attr_obj_values
	 * @return
	 */
	private String splitSsToSql(String[] attr_obj_ids, String[] attr_obj_values, String is_include) {
		String obj_id = attr_obj_ids[0]; // objId 查询界面左边Fact对象ID
		String attr_id = attr_obj_ids[1]; // attrId 查询界面左边属性的ID
		String ele_type = attr_obj_ids[2]; // eleType 查询界面左边属性类型
		StringBuilder cSql = new StringBuilder(""); // 拼es_rule_cond表查询条件
		StringBuilder sql = new StringBuilder(""); // 拼es_rule_obj_attr_rel表查询条件
		sql.append("(T.OBJ_ID='"+obj_id+"'");
		sql.append(" AND T.ATTR_ID='"+attr_id+"'");
		for (String val : attr_obj_values) {
			if (ele_type.equals(EcsOrderConsts.ELE_TYPE_RADIO) 
					|| ele_type.equals(EcsOrderConsts.ELE_TYPE_CHECKBOX)
					|| ele_type.equals(EcsOrderConsts.ELE_TYPE_LIST)) { // RADIO,LIST,CHECKBOX类型判断时前面加'attr_value'
				sql.append(" AND INSTR(T.ELE_VALUE, '\"attr_value\":\""+val+"\"')>0");
			} else if (ele_type.equals(EcsOrderConsts.ELE_TYPE_INPUT)) { // INPUT类型判断时，只需要判断是否包含
				sql.append(" AND INSTR(T.ELE_VALUE, '"+val+"')>0");
			}
			cSql.append(" AND INSTR(TT.Z_VALUE, '"+val+"')>0");
		}
		StringBuilder rSql = new StringBuilder("SELECT TT.RULE_ID FROM ES_RULE_COND TT WHERE 1=1");
		rSql.append(" AND TT.OBJ_ID='"+obj_id+"'");
		rSql.append(" AND TT.ATTR_ID='"+attr_id+"'");
		rSql.append(" AND TT.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'");
		rSql.append(cSql.toString());
		rSql.append(" AND (TT.OPT_TYPE='not in' OR TT.OPT_TYPE='not matches')");
		if (is_include.equals("1")) { // 包含，剔除es_rule_cond表中(opt_type='not in' or opt='not matches')数据
			sql.append(" AND T.RULE_ID NOT IN ("+rSql.toString()+")"); // es_rule_obj_attr_rel表根据rule_id过滤
		} else { // 包含，剔除es_rule_cond表中(opt_type<>'not in' or opt_type<>'not matches')数据
			sql.append(" AND T.RULE_ID IN ("+rSql.toString()+")");
		}
		sql.append(")");
		return sql.toString();
	}
	
	/**
	 * 公用的查询头部	
	 * @return
	 */
	private String getHeadSql() {
		StringBuilder sql = new StringBuilder("SELECT T.RULE_ID");
		sql.append(" FROM ES_RULE_OBJ_ATTR_REL T, ES_RULE_CONFIG RC");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.SOURCE_FROM=RC.SOURCE_FROM");
		sql.append(" AND T.RULE_ID=RC.RULE_ID");
		sql.append(" AND T.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'");
		return sql.toString();
	}
	
	/**
	 * 通过方案ID查询规则
	 * @param planid
	 * @param orderid
	 * @return
	 */
	private List<RuleConfig> getRuleByPlanid(String planid, String orderid) {
		List<RuleConfig> ruleConfigList = new ArrayList<RuleConfig>();
		RuleObjPlan ruleObjPlan = getRuleObjPlan(planid);
		String col2 = ruleObjPlan.getCol2(); // 定义为ObjId;
		String col3 = ruleObjPlan.getCol3(); // 定义为AttrId;
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" ROAR.*");
		sql.append(" FROM");
		sql.append(" ES_RULE_CONFIG RC,");
		sql.append(" ES_RULE_OBJ_ATTR_REL ROAR");
		sql.append(" WHERE");
		sql.append(" AND RC.RULE_ID=ROAR.RULE_ID");
		sql.append(" AND ROAR.OBJ_ID='"+col2+"'");
		sql.append(" AND ROAR.ATTR_ID='"+col3+"'");
		List<RuleObjAttrRel> ruleObjAttrRelList = baseDaoSupport.queryForList(sql.toString(), RuleObjAttrRel.class);
		for (RuleObjAttrRel rel : ruleObjAttrRelList) {
			String value = rel.getEle_value();
			if (value.contains(col2)) {
				ruleConfigList.add(getRuleConfigByRuleid(rel.getRule_id()));
			}
		}
		return ruleConfigList;
	}
	
	/**
	 * 根据方案ID获取方案对象
	 * @param planid
	 * @return
	 */
	private RuleObjPlan getRuleObjPlan(String planid) {
		RuleObjPlan ruleObjPlan = new RuleObjPlan();
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" T.*");
		sql.append(" FROM");
		sql.append(" ES_RULE_BIZ_PLAN T");
		sql.append(" WHERE");
		sql.append(" T.PLAN_ID='"+planid+"'");
		ruleObjPlan = (RuleObjPlan) baseDaoSupport.queryForObject(sql.toString(), RuleObjPlan.class);
		return ruleObjPlan;
	}
	
	/**
	 * 获取规则对象
	 * @param ruleid
	 * @return
	 */
	private RuleConfig getRuleConfigByRuleid(String ruleid) {
		RuleConfig ruleConfig = new RuleConfig();
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" T.*");
		sql.append(" FROM");
		sql.append(" ES_RULE_CONFIG T");
		sql.append(" WHERE");
		sql.append(" T.RULE_ID='"+ruleid+"'");
		ruleConfig = (RuleConfig) baseDaoSupport.queryForObject(sql.toString(), RuleConfig.class);
		return ruleConfig;
	}
	
	private INetCache cache = CacheFactory.getCacheByType("");
	public static int space = Const.CACHE_SPACE;
	private String IN = "in"; // 包含
	private String NOT_IN = "not in"; // 不包含
	private String MATCHES = "matches"; // 匹配
	private String NOT_MATCHES = "not matches"; // 不匹配
	private String DIR_KEY = "DIR_KEY"; // 目录KEY
	private String PLAN_KEY = "PLAN_KEY"; // 方案KEY
	private String COND_KEY = "COND_KEY"; // 规则条件KEY
	

	private List<PlanRule> searchPlanRuleByDir(SearchRuleByDirVo dirVo) {
		List<PlanRule> planRuleList = new ArrayList<PlanRule>();
		String dir_id = dirVo.getDir_id();
		List<SearchRuleByPlanVo> searchRuleByPlanVoList = dirVo.getSearchRuleByPlanList(); 
		List<Plan> planList = (List<Plan>) cache.get(space, DIR_KEY + "_" + dir_id);
		for (SearchRuleByPlanVo searchRuleVo : searchRuleByPlanVoList) {
			List<PlanRule> planRules = searchPlanRuleByPlan(searchRuleVo);
			planRuleList.addAll(planRules);
		}
		return planRuleList;
	}
	@SuppressWarnings("unchecked")
	private List<PlanRule> searchPlanRuleByPlan(SearchRuleByPlanVo searchRuleVo) {
		List<PlanRule> planRuleList = new ArrayList<PlanRule>();
		String plan_id = searchRuleVo.getPlan_id(); // 方案ID
		List<SearchObjAttrVo> objattrlist = searchRuleVo.getObjAttrList(); // 过滤条件
		List<PlanRule> one_level_List = (List<PlanRule>) cache.get(space,
				PLAN_KEY + "_" + plan_id); // 取一级规则
		if(one_level_List==null){
			String sql = SF.ruleSql("QUERY_PLAN_RULE");
			one_level_List = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id);
		}
		planRuleList = filterRuleByAttrCode(one_level_List, objattrlist);
		if (one_level_List != null && one_level_List.size() > 0) {
			List<PlanRule> planRuleChildrenList = filterChildrenRule(planRuleList, objattrlist);
			planRuleList.addAll(planRuleChildrenList);
		}
		return planRuleList;
	}
	
	/**
	 * 根据条件集合objAttrList，过滤规则集合planRulelist
	 * @param planRulelist
	 * @param objattrlist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<PlanRule> filterRuleByAttrCode(List<PlanRule> planRuleList,
			List<SearchObjAttrVo> objattrlist) {
		List<PlanRule> list = new ArrayList<PlanRule>();
		if(planRuleList!=null){
			for (PlanRule planRule : planRuleList) {
				List<RuleCondCache> cond_list = (List<RuleCondCache>) cache.get(
						space, COND_KEY + "_" + planRule.getRule_id()); // 条件集合
				if (cond_list == null || cond_list.size() == 0) { // 判断规则是否有条件，如果没有条件，则适用任何条件
					if (isAllCond(planRule.getRule_id())) {
						list.add(planRule);
					}
					continue;
				}
				if (filter_1(objattrlist, cond_list) 
						|| filter_3(objattrlist, cond_list)) {
					list.add(planRule);
					continue;
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 循环过滤子规则
	 * @param planRuleList
	 * @param objattrlist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<PlanRule> filterChildrenRule(List<PlanRule> planRuleList, 
			List<SearchObjAttrVo> objattrlist) {
		List<PlanRule> list_all = new ArrayList<PlanRule>();
		for (PlanRule planRule : planRuleList) {
			List<PlanRule> list = new ArrayList<PlanRule>();
			List<PlanRule> planRuleChildren = (List<PlanRule>) cache.get(
					space, PLAN_KEY + "_" + planRule.getPlan_id() + "_"
							+ planRule.getRule_id()); // 取下级规则
			if (planRuleChildren != null && planRuleChildren.size() > 0) {
				list = filterRuleByAttrCode(planRuleChildren, objattrlist);
				list_all.addAll(list);
				filterChildrenRule(planRuleChildren, objattrlist);
			}
		}
		return list_all;
	}
	
	/**
	 * 
	 * @param objattrlist - 搜索条件集（多条属性）
	 * @param cond_list - 缓存中的规则条件
	 * @return
	 */
	private boolean filter_3(List<SearchObjAttrVo> objattrlist, 
			List<RuleCondCache> cond_list) {
		boolean isclude = false;
		for (SearchObjAttrVo objattr : objattrlist) {
			isclude = filter_2(objattr, cond_list);
			if (!isclude) {break;}
		}
		return isclude;
	}
	/**
	 * 判断缓存规则条件是否满足过滤属性
	 * @param objattr - 过滤属性
	 * @param cond_list - 缓存中的规则条件
	 * @return
	 */
	private boolean filter_2(SearchObjAttrVo objattr , 
			List<RuleCondCache> cond_list) {
		boolean isclude = false;
		for (RuleCondCache cond : cond_list) {
			if (cond.getObj_code().equals(objattr.getObj_code())
					||cond.getAttr_code().equals(objattr.getAttr_code())) {
				List<String> attr_value = objattr.getAttr_value(); // 过滤属性值
				if (objattr.getIs_include().equals("1")) { // 包含
					if ((cond.getOpt_type().equals(IN)
							||cond.getOpt_type().equals(MATCHES))) { // 缓存中规则条件（opt_type）字段值
						isclude = matches(attr_value, cond.getZ_value());
					}
				} else {
					if ((cond.getOpt_type().equals(NOT_IN)
							||cond.getOpt_type().equals(NOT_MATCHES))) {
						isclude = matches(attr_value, cond.getZ_value());
					}
				}
				if (isclude) {break;}
			}
		}
		return isclude;
	}
	/**
	 * 缓存条件属性值是否包含过滤属性值
	 * @param attr_value - 过滤的属性值
	 * @param z_value - 缓存属性值
	 * @return
	 * @author ZX（缓存条件属性值中存在过滤属性值，则返回true否则返回false）
	 */
	private boolean matches(List<String> attr_value,
			String z_value) {
		boolean isclude = false;
		List<String> z_value_list = toListByString(z_value);
		if (z_value_list.containsAll(attr_value)) {
			isclude = true;
		} else {
			isclude = false;
		}
		return isclude;
	}
	
	/**
	 * 过滤属性是否包含在规则条件里面
	 * @param objattrlist
	 * @param cond_list
	 * @return false-包含，true-不包含
	 * @author ZX(不包含则不参与计算，然规则不被过滤；包含则参与计算，然再匹配规则的条件值是否和搜索条件中的值一致)
	 */
	private boolean filter_1(List<SearchObjAttrVo> objattrlist,
			List<RuleCondCache> cond_list) {
		boolean isclude = false;
		for (SearchObjAttrVo objattr : objattrlist) {
			if (filter_0(objattr, cond_list)) {
				isclude = true;
			} else {
				isclude = false;
				break;
			}
		}
		return isclude;
	}
	/**
	 * 单个属性过滤
	 * @param objattr
	 * @param cond_list
	 * @return
	 * @author ZX（循环缓存中的规则条件，如果过滤的属性在缓存的规则条件中不存在（不参与计算），则返回true否则返回false）
	 */
	private boolean filter_0(SearchObjAttrVo objattr , 
			List<RuleCondCache> cond_list) {
		boolean isclude = false;
		for (RuleCondCache cond : cond_list) {
			if (objattr.getObj_code().equals(cond.getObj_code())
					&&objattr.getAttr_code().equals(cond.getAttr_code())) {
				if (filter_2(objattr, cond_list))
					isclude = true;
				else
					isclude = false;
				break;
			} else {
				isclude = true;
				continue;
			}
		}
		return isclude;
	}
	/**
	 * 规则条件值，以","分割，转换成List集合
	 * @param z_value
	 * @return
	 */
	private List<String> toListByString(String z_value) {
		List<String> list = new ArrayList<String>();
		String[] strs = z_value.split(",");
		for (String s : strs) {
			list.add(s);
		}
		return list;
	}
	private boolean isAllCond(String rule_id) {
		boolean isAllCond = false;
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" T.*");
		sql.append(" FROM");
		sql.append(" ES_RULE_OBJ_ATTR_REL T,");
		sql.append(" ES_RULE_CONFIG TT");
		sql.append(" WHERE");
		sql.append(" T.SOURCE_FROM=TT.SOURCE_FROM");
		sql.append(" AND T.RULE_ID=TT.RULE_ID");
		sql.append(" AND TT.STATUS_CD='00A'");
		sql.append(" AND T.RULE_ID='"+rule_id+"'");
		sql.append(" AND T.ATTR_CODE='without_cond'");
		List<RuleObjAttrRel> list = baseDaoSupport.queryForList(sql.toString(), RuleObjAttrRel.class);
		if (list != null && list.size() > 0) {
			isAllCond = true;
		}
		return isAllCond;
	}

	/**
	 * @wuhui
	 * @2015-06-14
	 * 规则过滤总体入口
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PlanRule> filterRuleByAttrCode(List<PlanRule> planRuleList,String plan_id,String order_id) {
		if(StringUtils.isEmpty(order_id))
			return planRuleList;
		List<SearchObjAttrVo>  objattrlist = getSearchObjAttrList(plan_id, order_id);
		if(ListUtil.isEmpty(objattrlist))
			return planRuleList;
		List<PlanRule> list = new ArrayList<PlanRule>();
		if(planRuleList!=null){
			for (PlanRule planRule : planRuleList) {
				
				//add by wui缓存处理
//				BusiCompRequest busiCompRequest =  (BusiCompRequest) cache.get(space, COND_KEY + "_" + planRule.getRule_id()); // 条件集合
//				List<RuleCondCache> cond_list = new ArrayList<RuleCondCache>();
//				Map conMap = new HashMap();
//				if(busiCompRequest !=null){
//					conMap = busiCompRequest.getQueryParams();
//					if(conMap !=null)
//						cond_list = (List<RuleCondCache>) conMap.get("cond_list");
//				}
//				if(ListUtil.isEmpty(cond_list)){
//					busiCompRequest = new BusiCompRequest();
//					cond_list = getRuleCondListByData(planRule.getRule_id());
//					conMap.put("cond_list", cond_list);
//					busiCompRequest.setQueryParams(conMap);
//					cache.set(space, COND_KEY + "_" + planRule.getRule_id(), busiCompRequest);
//				}
				IAutoRule autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
				List<RuleCondCache> cond_list = autoRuleImpl.getRuleCond(planRule.getRule_id());
				
				if (cond_list == null || cond_list.size() == 0) { // 判断规则是否有条件，如果没有条件，则适用任何条件
					if (isAllCond(planRule.getRule_id())) {
						list.add(planRule);
					}
					continue;
				}
				if (filter_1(objattrlist, cond_list) 
						|| filter_3(objattrlist, cond_list)) {
					list.add(planRule);
					continue;
				}
			}
		}
		if(ListUtil.isEmpty(list))
			return null;
		if(!ListUtil.isEmpty(list) && list.size() ==1) //add by wui当只有一个结果,则代表不需要做IK计算,节省性能0617
			list.get(0).setRule_script("notneedexec");
		return list;
	}
	
	/**
	 * 规则数据过滤
	 */
	@Override
	public List<SearchObjAttrVo> getSearchObjAttrList(String plan_id, String order_id) {
		List<SearchObjAttrVo> searchObjAttrVoList = new ArrayList<SearchObjAttrVo>();
		try{
			List<QueryObjAttrVo> queryObjAttrList = new ArrayList<QueryObjAttrVo>();
			Plan ruleObjPlan = autoRuleImpl.getPlan(plan_id);
			if(ruleObjPlan ==null)
				return searchObjAttrVoList;
			String json_cond = ruleObjPlan.getCol2();
			/**
			 * net_type = CommonDataFactory.getInstance().getProductSpec(order_id, 
				SpecConsts.TYPE_ID_10002, "", SpecConsts.NET_TYPE);
			 */
//			OrderTreeBusiRequest treeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<AttrInstBusiRequest> attrInstBusiRequests = null;//treeBusiRequest.getAttrInstBusiRequests();
			if (!StringUtils.isEmpty(json_cond) && !"[]".equals(json_cond)) {
				queryObjAttrList = CommonTools.jsonToList(json_cond, QueryObjAttrVo.class);
				if (queryObjAttrList != null && queryObjAttrList.size() > 0) {
					for (QueryObjAttrVo vo : queryObjAttrList) {
						SearchObjAttrVo objAttrVo = new SearchObjAttrVo();
						String fact_value = vo.getAttr_value();
						//add by wui
						if("undefined".equals(vo.getObj_code()) || StringUtils.isEmpty(vo.getObj_code()))
							continue;
						String attr_value ="";
						if("workFlowFact".equals(vo.getObj_code())){
							WorkFlowFact workFlowFact = new WorkFlowFact();
							workFlowFact.setOrder_id(order_id);
							attr_value = (String) MethodUtils.invokeMethod(workFlowFact, "get"+fact_value.substring(0,1).toUpperCase()+fact_value.substring(1), null);
						}else if("modeFact".equals(vo.getObj_code())){
							ModeFact fact = new ModeFact();
							fact.setOrder_id(order_id);
							attr_value = (String) MethodUtils.invokeMethod(fact, "get"+fact_value.substring(0,1).toUpperCase()+fact_value.substring(1), null);
						}else{
							attr_value = CommonDataFactory.getInstance().getAttrFieldValue(order_id, fact_value); 
						}
						List<String> ls = new ArrayList<String>();
						ls.add(attr_value);
						objAttrVo.setAttr_code(vo.getAttr_code());
						objAttrVo.setAttr_id(vo.getAttr_id());
						objAttrVo.setAttr_name(vo.getAttr_name());
						objAttrVo.setAttr_value(ls);
						objAttrVo.setEle_type(vo.getEle_type());
						objAttrVo.setIs_include(vo.getIs_include());
						objAttrVo.setObj_code(vo.getObj_code());
						objAttrVo.setObj_id(vo.getObj_id());
						objAttrVo.setObj_name(vo.getObj_name());
						searchObjAttrVoList.add(objAttrVo);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchObjAttrVoList;
	}
	
}
