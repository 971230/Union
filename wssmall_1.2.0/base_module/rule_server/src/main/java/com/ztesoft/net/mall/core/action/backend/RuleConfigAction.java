package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.RuleConst;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.RuleActionAudit;
import com.ztesoft.net.mall.core.model.RuleCondAudit;
import com.ztesoft.net.mall.core.model.RuleConfigAudit;
import com.ztesoft.net.mall.core.model.RuleConfigConstAudit;
import com.ztesoft.net.mall.core.model.RuleConfigListAudit;
import com.ztesoft.net.mall.core.model.RuleObj;
import com.ztesoft.net.mall.core.model.RuleObjAttr;
import com.ztesoft.net.mall.core.model.RuleObjAttrRel;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IRuleConfigManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rule.core.util.DroolsFactScanUtil;
import com.ztesoft.rule.core.util.RuleScriptUtil;

/**
 * 规则管理action
 * @作者 MoChunrun
 * @创建日期 2013-12-11 
 * @版本 V 1.0
 */
public class RuleConfigAction extends WWAction{
	
	private IRuleConfigManager ruleConfigManager;
	private Page ruleConfigList;
	private String rule_name;
	private String rule_code;
	private RuleConfigAudit ruleConfig;
	private String action = "0";
	private String rule_id;
	private String stype_code;
	private String ruleObjId;
	private String never_run_flag;
	
	private IDcPublicInfoManager dcPublicInfoManager;
	
	private List<RuleObj> ruleObjList;
	private List<RuleActionAudit> ruleActionList;
	private List<RuleCondAudit> ruleCondList;
	private RuleActionAudit ruleAction;
	private List<Map<String, String>> ruleOperList;
	private RuleObjAttrRel ruleObjAttr;
	
	private String [] ruleObjIdArray;
	private String [] ruleObjAttrArray;
	private String [] z_ruleObjAttrArray;
	private String [] z_ruleObjIdArray;
	private String [] ruleOptTypeArray;
	private String [] ruleCondValueArray;//英文
	private String [] ruleCondCValueArray;//中文
	private String [] connect_codeArray;//比上面的要少一个
	private String [] left_bracketsArray;
	private String [] right_bracketsArray;
	//private String action_script;
	private String [] action_script;//20140326修改为数组
	
	private String obj_name;
	private String obj_id;
	
	private static Map<String,String> optTypeMap;
	
	private List<RuleConfigListAudit> configListAuditList;
	private String isaudit;
	
	private int condSize = 0;
	
	private int constSize = 0;
	
	private int ruleCondSize = 0;
		
	private String rule_sys_flag; 			//广东联通，订单系统标识
	
	//常量
	private String [] ruleConstValueArray;
	private String [] ruleConstCValueArray;
	private String [] ruleConstObjIdArray;
	private String [] ruleConstAttrIdArray;
	private String [] ruleConstObjCodeArray;
	private String [] ruleConstAttrCodeArray;
	
	//private static Map<String,String> opTypeMapC;
	
	static{
		/*>、>=、<、<=、==、!=、contains、not contains、memberof、not memberof、matches、not matches
				十二种类型的比较操作符当中*/
		optTypeMap = new HashMap<String,String>();
		optTypeMap.put(">", "大于");
		optTypeMap.put(">=", "大于等于");
		optTypeMap.put("<", "小于");
		optTypeMap.put("<=", "小于等于");
		optTypeMap.put("==", "等于");
		optTypeMap.put("!=", "不等于");
		optTypeMap.put("contains", "contains");//比较操作符contains 是用来检查一个Fact 对象的某个字段（该字段要是一个Collection 或是一个Array 类型的对象）是否包含一个指定的对象。
		optTypeMap.put("not contains", "not contains");//not contains 作用与contains 作用相反
		optTypeMap.put("memberof", "memberof");//memberOf 是用来判断某个Fact 对象的某个字段是否在一个集合（Collection/Array）当中
		optTypeMap.put("not memberof", "not memberof");//"not memberof"
		optTypeMap.put("matches", "matches");//matches 是用来对某个Fact 的字段与标准的Java 正则表达式进行相似匹配，被比较的字符串可以是一个标准的Java 正则表达式
		optTypeMap.put("not matches", "not matches");//"not matches"
	}
	
	private RuleConfigAudit ruleConfigAudit;
	
	/**
	 * 规则审核列表
	 * @return
	 */
	public String ruleAuditList(){
		
		//DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	   // this.statusList = dcPublicCache.getList("1195");
	    
	    if(this.ruleConfigAudit == null){
	    	this.ruleConfigAudit = new RuleConfigAudit();
	    }
	    
	    this.webpage = this.ruleConfigManager.qryRuleAuditList(this.ruleConfigAudit,this.getPage(),this.getPageSize());
		
		return "rule_audit_list";
	}
	
	/**
	 * 规则审核
	 * @return
	 */
	public String ruleAudit(){
		
		
		try {
			this.ruleConfigManager.ruleAudit(rule_id);
			this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 提交审核
	 * @作者 MoChunrun
	 * @创建日期 2014-3-27 
	 * @return
	 */
	public String sAudit(){
		try{
			ruleConfigManager.updateRuleConfigAuditStatus(rule_id, "00S");
			json = "{status:0,msg:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 刷新对象属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @return
	 */
	public String reScanObjInfo(){
		try{
			int count = DroolsFactScanUtil.scanDroolsFact("com.ztesoft.net.rule");
			int count2 =  DroolsFactScanUtil.scanDroolsFact("zte.net.ecsord.rule");
			int count3 =  DroolsFactScanUtil.scanDroolsFact("zte.net.ord.rule.inf");
			json = "{status:0,msg:'刷新成功，总共刷对象数量["+ (count + count2 + count3) +"]个'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 选中某个对象
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @return
	 */
	public String objSelect(){
		RuleObj obj = ruleConfigManager.getRuleObjById(obj_id);
		List<RuleObjAttr> attrs = ruleConfigManager.qryRuleObjAttrByObjId(obj_id);
		json = "{obj:"+JSON.toJSONString(obj)+",attrs:"+JSON.toJSONString(attrs)+"}";
		/*HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return JSON_MESSAGE;
	}
	
	/**
	 * 选择对象
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @return
	 */
	public String qryRuleObj(){
		webpage = ruleConfigManager.qryRuleObjPage(obj_name, getPage(), getPageSize());
		return "rule_obj_list";
	}

	/**
	 * 规则列表
	 * @作者 MoChunrun
	 * @创建日期 2013-12-11 
	 * @return
	 */
	public String ruleConfigList(){
		ruleConfigList = ruleConfigManager.qryRuleConfigAuditPage(rule_name, rule_code, getPage(), getPageSize());
		return "rule_config_list";
	}
	
	/**
	 * 显示配置窗口
	 * @作者 MoChunrun
	 * @创建日期 2013-12-11 
	 * @return
	 */
	public String showConfigAdd(){
		try{
			if(!"0".equals(action)){
				ruleConfig = ruleConfigManager.getRuleConfigAuditById(rule_id);
				if(ruleConfig!=null){
					ruleConfig.setEff_date(ruleConfig.getEff_date().substring(0,10));
					ruleConfig.setExp_date(ruleConfig.getExp_date().substring(0,10));
				}
				ruleObjList = ruleConfigManager.qryRuleObjByAuditRuleId(rule_id);
				/*for(RuleObj obj:ruleObjList){
					obj.setObjAttrList(ruleConfigManager.qryRuleObjAttr(obj.getObj_id(), rule_id));
				}*/
				
				if(null != ruleObjList && !ruleObjList.isEmpty()){
					ruleCondSize = ruleObjList.size();
				}
				ruleActionList = ruleConfigManager.qryRuleActionAuditByRuleId(rule_id);
				ruleCondList = ruleConfigManager.qryRuleCondAuditByRuleId(rule_id);
				configListAuditList = ruleConfigManager.listRuleConfigListAudit(rule_id);
				if(configListAuditList!=null && configListAuditList.size()>0){
					condSize = configListAuditList.size();
					for(RuleConfigListAudit ca:configListAuditList){
						List<RuleCondAudit> rcalist = new ArrayList<RuleCondAudit>();
						for(RuleCondAudit rca:ruleCondList){
							if(rca.getList_id().equals(ca.getList_id())){
								rcalist.add(rca);
							}
						}
						ca.setRuleCondAuditList(rcalist);
						for(RuleActionAudit raa:ruleActionList){
							if(raa.getList_id().equals(ca.getList_id())){
								ca.setRuleActionAudit(raa);
								String actionscript = raa.getAction_script();
								for(RuleObj ro:ruleObjList){
									List<RuleObjAttr> list = ruleConfigManager.qryRuleObjAttrByObjId(ro.getObj_id());
									for(RuleObjAttr ra:list){
										String ckey = ro.getObj_name()+"."+ra.getAttr_name();
										String ekey = ro.getObj_code()+"."+ra.getAttr_code();
										if(null != actionscript && !"".equals(actionscript)){
											actionscript = actionscript.replace(ekey, ckey);
										}
									}
								}
								raa.setAction_script(actionscript);
								break ;
							}
						}
						ca.setRuleObjList(ruleConfigManager.qryRuleObjByAuditRuleId(rule_id,ca.getList_id()));
						for(RuleObj obj:ca.getRuleObjList()){
							obj.setObjAttrList(ruleConfigManager.qryRuleObjAttr(obj.getObj_id(), rule_id));
						}
						ca.setConstAuditList(ruleConfigManager.queryRuleConfigConstAuditByListId(ca.getList_id()));
						if(rule_sys_flag != null && 
								RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag)){
							if(ca.getConstAuditList() != null && !ca.getConstAuditList().isEmpty()){
								this.constSize = ca.getConstAuditList().size();
							}
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(rule_sys_flag != null && 
				RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag)){		//联通订单系统，新规则配置界面
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		    this.ruleOperList = dcPublicCache.getList(RuleConst.DROOLS_KEY);
		    if(ruleCondSize == 0 && condSize > 0){
		    	ruleObjAttr = ruleConfigManager.getRuleObjAttr(rule_id, 
		    			RuleConst.RULE_CAL_COND, new String[]{RuleConst.DROOLS_WITHOUT_COND, RuleConst.DROOLS_NEVER_RUN});
		    }
			return "rule_cond_page";
		}
		return "rule_config_add";
	}
	
	/**
	 * 保存或修改规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-11 
	 * @return
	 */
	public String saveConfigRule(){
		try{
			if(StringUtil.isEmpty(ruleConfig.getRule_code())){
				json = "{status:0,msg:'规则编码已经存在，请重新输入。'}";
				return JSON_MESSAGE;
			}
			ruleConfig.setStatus_cd(Const.RULE_STATUS_00A);
			ruleConfig.setCreate_date("sysdate");
			AdminUser user = ManagerUtils.getAdminUser();
			if(user!=null){
				ruleConfig.setCreate_user(user.getUsername());
			}
			//封装常量
			List<List<String[]>> constList = new ArrayList<List<String[]>>();
			List<String[]> cs = new ArrayList<String[]>();
			for(int i=0;i<ruleConstValueArray.length;i++){
				if(!"group".equals(ruleConstValueArray[i])){
					if(rule_sys_flag != null && 
							RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag)){
						String [] svs = {ruleConstValueArray[i], ruleConstObjIdArray[i],ruleConstAttrIdArray[i],ruleConstObjCodeArray[i],ruleConstAttrCodeArray[i], ruleConstCValueArray[i]};
						cs.add(svs);
					}else {
						String [] svs = {ruleConstValueArray[i], ruleConstObjIdArray[i],ruleConstAttrIdArray[i],ruleConstObjCodeArray[i],ruleConstAttrCodeArray[i]};
						cs.add(svs);
					}
				}
				if("group".equals(ruleConstValueArray[i])){
					constList.add(cs);
					cs = new ArrayList<String[]>();
				}
			}
			
			if(!"0".equals(action)){
				ruleConfigManager.delRuleCondAuditByRuleId(ruleConfig.getRule_id());
				ruleConfigManager.delRuleActionAuditByRuleId(ruleConfig.getRule_id());
				ruleConfigManager.deleteRuleConfigListByRuleAuditId(ruleConfig.getRule_id());
				ruleConfigManager.delRuleConfigConstAuditByRuleId(ruleConfig.getRule_id());
				ruleConfig.setAudit_status("00E");
			}else{
				if(ruleConfigManager.ruleColeIsExists(ruleConfig.getRule_code())){
					json = "{status:0,msg:'规则编码已经存在，请重新输入。'}";
					return JSON_MESSAGE;
				}
				String ruleConfigId = ruleConfigManager.getRuleConfigId();
				ruleConfig.setRule_id(ruleConfigId);
				ruleConfig.setAudit_status("00N");
			}
			
			
			if("CF".equals(ruleConfig.getImpl_type())){
				List<RuleCondAudit> ruleCondList = null;
				List<RuleConfigListAudit> configListAuditList = new ArrayList<RuleConfigListAudit>();
				String ids = "";
				RuleConfigListAudit configListAudit = null;
				int connect = 0;//连接符下标减数
				boolean connectGroup = false;
				for(int i=0;i<ruleObjIdArray.length;i++){
					if("group".equals(ruleObjIdArray[i])){
						//ruleObjIdArray的第一个肯定是 group 
						configListAudit = new RuleConfigListAudit();
						configListAudit.setList_name(ruleConfig.getRule_code()+i);
						configListAudit.setSort(i);
						configListAudit.setStatus("00A");
						configListAudit.setRule_id(ruleConfig.getRule_id());
						ruleConfigManager.insertRuleConfigListAudit(configListAudit);
						configListAuditList.add(configListAudit);
						connect ++;
						connectGroup = true;
						ruleCondList = new ArrayList<RuleCondAudit>();
						configListAudit.setRuleCondAuditList(ruleCondList);
					}else{
						RuleCondAudit rc = new RuleCondAudit();
						rc.setList_id(configListAudit.getList_id());
						rc.setAttr_id(ruleObjAttrArray[i-connect]);
						rc.setAttr_index(i);
						rc.setLeft_brackets(left_bracketsArray[i-connect]);
						rc.setRight_brackets(right_bracketsArray[i-connect]);
						rc.setObj_id(ruleObjIdArray[i]);
						ids += ruleObjIdArray[i]+",";
						rc.setOpt_type(ruleOptTypeArray[i-connect]);
						if(!connectGroup){
							rc.setPre_log(connect_codeArray[i-connect*2]);
						}
						rc.setRule_id(ruleConfig.getRule_id());
						rc.setZ_attr_id(z_ruleObjAttrArray[i-connect]);
						rc.setZ_obj_id(z_ruleObjIdArray[i-connect]);
						rc.setZ_value(ruleCondValueArray[i-connect]);
						rc.setZ_cvalue(ruleCondCValueArray[i-connect]);
						rc.setStatus("00A");
						ruleConfigManager.insertRuleCondAudit(rc);
						ruleCondList.add(rc);
						connectGroup = false;
					}
				}
				if(ids.length()>1){
					ids = ids.substring(0,ids.length()-1);
				}
				if(rule_sys_flag == null  ||
						!RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag))				//直接审核通过
				ruleObjList = ruleConfigManager.qryRuleObjByIds(ids);
				//List<RuleActionAudit> ruleActionList = new ArrayList<RuleActionAudit>();
				for(int i=0;i<action_script.length;i++){
					if(null != ruleObjList && ruleObjList.size() > 0){
						for(RuleObj ro:ruleObjList){
							List<RuleObjAttr> list = ruleConfigManager.qryRuleObjAttrByObjId(ro.getObj_id());
							for(RuleObjAttr ra:list){
								String ckey = ro.getObj_name()+"."+ra.getAttr_name();
								String ekey = ro.getObj_code()+"."+ra.getAttr_code();
								action_script[i] = action_script[i].replace(ckey, ekey);
							}
						}
					}
					RuleActionAudit ra = new RuleActionAudit();
					ra.setAction_index(0);
					ra.setAction_script(action_script[i]);
					ra.setRule_id(ruleConfig.getRule_id());
					ra.setStatus("00A");
					ra.setList_id(configListAuditList.get(i).getList_id());
					ruleConfigManager.insertRuleActionAudit(ra);
					configListAuditList.get(i).setRuleActionAudit(ra);
					
					//常量
					List<String[]> consts = constList.get(i);
					if(consts!=null && consts.size()>0){
						List<RuleConfigConstAudit> rcvList = new ArrayList<RuleConfigConstAudit>();
						for(String[] cvs:consts){
							RuleConfigConstAudit rcv = new RuleConfigConstAudit();
							rcv.setList_id(configListAuditList.get(i).getList_id());
							rcv.setAction_id(ra.getAction_id());
							rcv.setAttr_id(cvs[2]);
							rcv.setObj_id(cvs[1]);
							rcv.setConst_value(cvs[0]);
							rcv.setObj_code(cvs[3]);
							rcv.setAttr_code(cvs[4]);
							if(rule_sys_flag != null && 
									RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag))
								rcv.setConst_name(cvs[5]);
							rcv.setRule_id(ruleConfig.getRule_id());
							rcv.setStatus("00A");
							rcv.setBatch_time("sysdate");
							ruleConfigManager.addRuleConfigConstAudit(rcv);
							rcvList.add(rcv);
						}
						configListAuditList.get(i).setConstAuditList(rcvList);
					}
				}
				ruleConfig.setRule_sys_flag(rule_sys_flag);
				ruleConfig.setObj_id(ruleObjId);
				ruleConfig.setNever_run_flag(never_run_flag);
				String script = RuleScriptUtil.parseRuleScript(ruleConfig,configListAuditList);
				ruleConfig.setRule_script(script);
			}
			if("yes".equals(isaudit)){
				ruleConfig.setAudit_status("00S");
			}
			if(!"0".equals(action)){
				ruleConfigManager.editRuleConfigAudit(ruleConfig);
			}else{
				ruleConfigManager.insertRuleConfigAudit(ruleConfig);
			}
			if(rule_sys_flag != null && 
					RuleConst.RULE_SYS_FLAG.equals(rule_sys_flag))				//直接审核通过
				ruleConfigManager.ruleAudit(rule_id);
			json = "{status:1,msg:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:0,msg:'失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 保存或修改规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-11 
	 * @return
	 */
	/*@Deprecated
	public String saveConfigRule_back20140326(){
		try{
			ruleConfig.setStatus_cd(Const.RULE_STATUS_00A);
			ruleConfig.setCreate_date("sysdate");
			AdminUser user = ManagerUtils.getAdminUser();
			if(user!=null){
				ruleConfig.setCreate_user(user.getUsername());
			}
			
			if(!"0".equals(action)){
				//ruleConfigManager.editRuleConfig(ruleConfig);
				ruleConfigManager.delRuleCondByRuleId(ruleConfig.getRule_id());
				ruleConfigManager.delRuleActionByRuleId(ruleConfig.getRule_id());
			}else{
				String ruleConfigId = ruleConfigManager.getRuleConfigId();
				ruleConfig.setRule_id(ruleConfigId);
				//ruleConfigManager.insertRuleConfig(ruleConfig);
			}
			
			List<RuleCond> ruleCondList = new ArrayList<RuleCond>();
			
			String ids = "";
			if("CF".equals(ruleConfig.getImpl_type())){
				for(int i=0;i<ruleObjIdArray.length;i++){
					RuleCond rc = new RuleCond();
					rc.setAttr_id(ruleObjAttrArray[i]);
					rc.setAttr_index(i);
					//rc.setAttr_script(attr_script)
					rc.setLeft_brackets(left_bracketsArray[i]);
					rc.setRight_brackets(right_bracketsArray[i]);
					rc.setObj_id(ruleObjIdArray[i]);
					ids += ruleObjIdArray[i]+",";
					rc.setOpt_type(ruleOptTypeArray[i]);
					//rc.setOpt_type(opTypeMapC.get(ruleOptTypeArray[i]));
					if(i!=0){
						rc.setPre_log(connect_codeArray[i-1]);
					}
					rc.setRule_id(ruleConfig.getRule_id());
					rc.setZ_attr_id(z_ruleObjAttrArray[i]);
					rc.setZ_obj_id(z_ruleObjIdArray[i]);
					rc.setZ_value(ruleCondValueArray[i]);
					rc.setZ_cvalue(ruleCondCValueArray[i]);
					ruleConfigManager.insertRuleCond(rc);
					ruleCondList.add(rc);
				}
				if(ids.length()>1){
					ids = ids.substring(0,ids.length()-1);
				}
				ruleObjList = ruleConfigManager.qryRuleObjByIds(ids);
				for(RuleObj ro:ruleObjList){
					List<RuleObjAttr> list = ruleConfigManager.qryRuleObjAttrByObjId(ro.getObj_id());
					for(RuleObjAttr ra:list){
						String ckey = ro.getObj_name()+"."+ra.getAttr_name();
						String ekey = ro.getObj_code()+"."+ra.getAttr_code();
						action_script = action_script.replace(ckey, ekey);
					}
				}
				RuleAction ra = new RuleAction();
				ra.setAction_index(0);
				ra.setAction_script(action_script);
				ra.setRule_id(ruleConfig.getRule_id());
				ruleConfigManager.insertRuleAction(ra);
				String script = RuleScriptUtil.parseRuleScript(ruleConfig, ruleCondList, ra);
				ruleConfig.setRule_script(script);
				if(!"0".equals(action)){
					ruleConfigManager.editRuleConfig(ruleConfig);
				}else{
					ruleConfigManager.insertRuleConfig(ruleConfig);
				}
			}
			json = "{status:1,msg:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:0,msg:'失败'}";
		}
		return this.JSON_MESSAGE;
	}*/
	
	
	public String listPublics(){
		
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    List list = dcPublicCache.getList(stype_code);
		
	    List<JSONObject> jsList = new ArrayList<JSONObject>();
	    
	    if(!ListUtil.isEmpty(list)){
	    	for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				JSONObject json = JSONObject.fromObject(map);
				jsList.add(json);
			}
	    }
	    
	    JSONArray js = JSONArray.fromObject(list);
	    
	    this.json = "{list:'"+js+"'}";
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	

	public Page getRuleConfigList() {
		return ruleConfigList;
	}

	public void setRuleConfigList(Page ruleConfigList) {
		this.ruleConfigList = ruleConfigList;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_code() {
		return rule_code;
	}

	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}

	public IRuleConfigManager getRuleConfigManager() {
		return ruleConfigManager;
	}

	public void setRuleConfigManager(IRuleConfigManager ruleConfigManager) {
		this.ruleConfigManager = ruleConfigManager;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public List<RuleObj> getRuleObjList() {
		return ruleObjList;
	}

	public void setRuleObjList(List<RuleObj> ruleObjList) {
		this.ruleObjList = ruleObjList;
	}

	public String[] getRuleObjIdArray() {
		return ruleObjIdArray;
	}

	public void setRuleObjIdArray(String[] ruleObjIdArray) {
		this.ruleObjIdArray = ruleObjIdArray;
	}

	public String[] getRuleObjAttrArray() {
		return ruleObjAttrArray;
	}

	public void setRuleObjAttrArray(String[] ruleObjAttrArray) {
		this.ruleObjAttrArray = ruleObjAttrArray;
	}

	public String[] getRuleCondValueArray() {
		return ruleCondValueArray;
	}

	public void setRuleCondValueArray(String[] ruleCondValueArray) {
		this.ruleCondValueArray = ruleCondValueArray;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String[] getZ_ruleObjAttrArray() {
		return z_ruleObjAttrArray;
	}

	public void setZ_ruleObjAttrArray(String[] z_ruleObjAttrArray) {
		this.z_ruleObjAttrArray = z_ruleObjAttrArray;
	}

	public String[] getZ_ruleObjIdArray() {
		return z_ruleObjIdArray;
	}

	public void setZ_ruleObjIdArray(String[] z_ruleObjIdArray) {
		this.z_ruleObjIdArray = z_ruleObjIdArray;
	}

	public String[] getConnect_codeArray() {
		return connect_codeArray;
	}

	public void setConnect_codeArray(String[] connect_codeArray) {
		this.connect_codeArray = connect_codeArray;
	}

	public String[] getLeft_bracketsArray() {
		return left_bracketsArray;
	}

	public void setLeft_bracketsArray(String[] left_bracketsArray) {
		this.left_bracketsArray = left_bracketsArray;
	}

	public String[] getRight_bracketsArray() {
		return right_bracketsArray;
	}

	public void setRight_bracketsArray(String[] right_bracketsArray) {
		this.right_bracketsArray = right_bracketsArray;
	}

	public String[] getRuleOptTypeArray() {
		return ruleOptTypeArray;
	}

	public void setRuleOptTypeArray(String[] ruleOptTypeArray) {
		this.ruleOptTypeArray = ruleOptTypeArray;
	}

	public Map<String, String> getOptTypeMap() {
		return optTypeMap;
	}

	public String[] getRuleCondCValueArray() {
		return ruleCondCValueArray;
	}

	public void setRuleCondCValueArray(String[] ruleCondCValueArray) {
		this.ruleCondCValueArray = ruleCondCValueArray;
	}

	public String[] getAction_script() {
		return action_script;
	}

	public void setAction_script(String[] action_script) {
		this.action_script = action_script;
	}

	public RuleConfigAudit getRuleConfig() {
		return ruleConfig;
	}

	public void setRuleConfig(RuleConfigAudit ruleConfig) {
		this.ruleConfig = ruleConfig;
	}

	public List<RuleActionAudit> getRuleActionList() {
		return ruleActionList;
	}

	public void setRuleActionList(List<RuleActionAudit> ruleActionList) {
		this.ruleActionList = ruleActionList;
	}

	public List<RuleCondAudit> getRuleCondList() {
		return ruleCondList;
	}

	public void setRuleCondList(List<RuleCondAudit> ruleCondList) {
		this.ruleCondList = ruleCondList;
	}

	public RuleActionAudit getRuleAction() {
		return ruleAction;
	}

	public void setRuleAction(RuleActionAudit ruleAction) {
		this.ruleAction = ruleAction;
	}

	public List<RuleConfigListAudit> getConfigListAuditList() {
		return configListAuditList;
	}

	public void setConfigListAuditList(List<RuleConfigListAudit> configListAuditList) {
		this.configListAuditList = configListAuditList;
	}

	public String getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(String isaudit) {
		this.isaudit = isaudit;
	}

	public RuleConfigAudit getRuleConfigAudit() {
		return ruleConfigAudit;
	}

	public void setRuleConfigAudit(RuleConfigAudit ruleConfigAudit) {
		this.ruleConfigAudit = ruleConfigAudit;
	}

	public int getCondSize() {
		return condSize;
	}

	public void setCondSize(int condSize) {
		this.condSize = condSize;
	}

	public String getStype_code() {
		return stype_code;
	}

	public void setStype_code(String stype_code) {
		this.stype_code = stype_code;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String[] getRuleConstValueArray() {
		return ruleConstValueArray;
	}

	public void setRuleConstValueArray(String[] ruleConstValueArray) {
		this.ruleConstValueArray = ruleConstValueArray;
	}

	public String[] getRuleConstObjIdArray() {
		return ruleConstObjIdArray;
	}

	public void setRuleConstObjIdArray(String[] ruleConstObjIdArray) {
		this.ruleConstObjIdArray = ruleConstObjIdArray;
	}

	public String[] getRuleConstAttrIdArray() {
		return ruleConstAttrIdArray;
	}

	public void setRuleConstAttrIdArray(String[] ruleConstAttrIdArray) {
		this.ruleConstAttrIdArray = ruleConstAttrIdArray;
	}

	public String[] getRuleConstObjCodeArray() {
		return ruleConstObjCodeArray;
	}

	public void setRuleConstObjCodeArray(String[] ruleConstObjCodeArray) {
		this.ruleConstObjCodeArray = ruleConstObjCodeArray;
	}

	public String[] getRuleConstAttrCodeArray() {
		return ruleConstAttrCodeArray;
	}

	public void setRuleConstAttrCodeArray(String[] ruleConstAttrCodeArray) {
		this.ruleConstAttrCodeArray = ruleConstAttrCodeArray;
	}

	public String getRule_sys_flag() {
		return rule_sys_flag;
	}

	public void setRule_sys_flag(String rule_sys_flag) {
		this.rule_sys_flag = rule_sys_flag;
	}

	public int getConstSize() {
		return constSize;
	}

	public void setConstSize(int constSize) {
		this.constSize = constSize;
	}

	public String[] getRuleConstCValueArray() {
		return ruleConstCValueArray;
	}

	public void setRuleConstCValueArray(String[] ruleConstCValueArray) {
		this.ruleConstCValueArray = ruleConstCValueArray;
	}

	public List<Map<String, String>> getRuleOperList() {
		return ruleOperList;
	}

	public void setRuleOperList(List<Map<String, String>> ruleOperList) {
		this.ruleOperList = ruleOperList;
	}

	public String getRuleObjId() {
		return ruleObjId;
	}

	public void setRuleObjId(String ruleObjId) {
		this.ruleObjId = ruleObjId;
	}

	public int getRuleCondSize() {
		return ruleCondSize;
	}

	public void setRuleCondSize(int ruleCondSize) {
		this.ruleCondSize = ruleCondSize;
	}

	public RuleObjAttrRel getRuleObjAttr() {
		return ruleObjAttr;
	}

	public void setRuleObjAttr(RuleObjAttrRel ruleObjAttr) {
		this.ruleObjAttr = ruleObjAttr;
	}

	public String getNever_run_flag() {
		return never_run_flag;
	}

	public void setNever_run_flag(String never_run_flag) {
		this.never_run_flag = never_run_flag;
	}
}
