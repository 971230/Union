package com.ztesoft.check.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFunRel;
import com.ztesoft.check.model.BizRequirements;
import com.ztesoft.check.model.Fun;
import com.ztesoft.check.service.IBizCheckCfgManager;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.sqls.SF;

public class BizCheckCfgManager extends BaseSupport implements IBizCheckCfgManager {

	@Override
	public Page qryCfgPage(Map param, int page, int pageSize) {
		String sql = SF.eccSql("BIZ_PAGE");
		String countSql = "select count(1) " + sql.substring(sql.indexOf("FROM ES_ECC_BIZ "));
		sql = sql + " order by create_time desc ";
		Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize, Biz.class, countSql);
		return webpage;
	}

	@Override
	public List bizFactorCfgList() {
		String sql = SF.eccSql("BIZREQUIREMENTS_LIST");
		List list = this.daoSupport.queryForLists(sql);
		return list;
	}

	@Override
	public void saveBizCheckCfg(Biz biz, ArrayList<BizRequirements> list, ArrayList<BizFunRel> bizFunList, ArrayList<BizFunRel> beforeBizFunList) {
		String biz_id = this.baseDaoSupport.getSequences("S_ES_BIZ");
		biz.setBiz_id(biz_id);
		this.baseDaoSupport.insert("ecc_biz", biz);
		int i = 0;
		for(BizRequirements bizRequirements : list){
			if(!StringUtil.isEmpty(bizRequirements.getCheck()) && "1".equals(bizRequirements.getCheck())&& !StringUtil.isEmpty(bizRequirements.getZ_value())){
				bizRequirements.setBiz_id(biz_id);
				Map br = ReflectionUtil.po2Map(bizRequirements);
				String required_id = this.baseDaoSupport.getSequences("S_ES_BIZ_REQUIREMENTS");
				br.put("required_id", required_id);
				br.put("opt_index", String.valueOf(++i));
				br.remove("check");
				this.baseDaoSupport.insert("ecc_biz_requirements", br);
			}
		}
		i = 0;
		for(BizFunRel bizFunRel : bizFunList){
			if(!StringUtil.isEmpty(bizFunRel.getCheck()) && "1".equals(bizFunRel.getCheck()) && !StringUtil.isEmpty(bizFunRel.getFun_id())){
				if(!StringUtil.isEmpty(bizFunRel.getTache_code())){
					bizFunRel.setBiz_id(biz_id);
					String [] fun_array = bizFunRel.getFun_id().split(",");
					for(String fun : fun_array){
						Map br = ReflectionUtil.po2Map(bizFunRel);
						br.put("order_id", String.valueOf(++i));
						br.put("fun_id", fun.trim());
						br.remove("check");
						this.baseDaoSupport.insert("ecc_biz_fun_rel", br);
					}
				}
			}
		}
		i = 0;
		for(BizFunRel bizFunRel : beforeBizFunList){
			if(!StringUtil.isEmpty(bizFunRel.getCheck()) && "1".equals(bizFunRel.getCheck()) && !StringUtil.isEmpty(bizFunRel.getFun_id())){
				if(!StringUtil.isEmpty(bizFunRel.getTache_code())){
					bizFunRel.setBiz_id(biz_id);
					String [] fun_array = bizFunRel.getFun_id().split(",");
					for(String fun : fun_array){
						Map br = ReflectionUtil.po2Map(bizFunRel);
						br.put("order_id", String.valueOf(++i));
						br.put("fun_id", fun.trim());
						br.remove("check");
						this.baseDaoSupport.insert("ecc_biz_fun_rel", br);
					}
				}
			}
		}
	}

	@Override
	public List funList() {
		String sql = SF.eccSql("FUN_LIST");
		List<Fun> list = this.daoSupport.queryForList(sql, Fun.class);
		return list;
	}

	@Override
	public Biz qryEditBiz(String id) {
		String sql = "SELECT * FROM es_ecc_biz WHERE biz_id =? ";
		Biz biz=(Biz) this.daoSupport.queryForObject(sql, Biz.class, id);
		return biz;
	}
	
	@Override
	public Map qryBizExeMethod(String id, String exe_time) {
		Map retMap = new HashMap();
		String sFunExeMethodSql = "SELECT Distinct(a.tache_code) tache_code,a.exe_method FROM es_ecc_biz_fun_rel a WHERE exe_time = '" + exe_time + "' AND a.biz_id='" + id + "'";
		List sFunExeMethodList = this.daoSupport.queryForLists(sFunExeMethodSql);
		for (int i = 0; i < sFunExeMethodList.size(); i++) {
			Map em = (Map) sFunExeMethodList.get(i);
			retMap.put(em.get("tache_code"), em.get("exe_method"));
		}
		return retMap;
	}
	
	@Override
	public Map qryBizFunList(List funList, String id, String exe_time) {
		String sFunSql = "SELECT * FROM es_ecc_biz_fun_rel a WHERE exe_time='" + exe_time + "' AND biz_id='" + id + "'";
		List<Map> sFunList = this.daoSupport.queryForLists(sFunSql);
		
		Map retMap = new HashMap();
		String traceStr = "B,C,D,F,H,J,L,V,X,Y,R";
		for(String t : traceStr.split(",")){
			List retList = new ArrayList();
			retMap.put(t, retList);
			
			for (int i = 0; i < funList.size(); i++) {
				Fun fun = (Fun) funList.get(i);
				Map funMap = new HashMap();
				funMap.put("fun_id", fun.getFun_id());
				funMap.put("fun_name", fun.getFun_name());
				funMap.put("checked", "unchecked");
				
				for (int j = 0; j < sFunList.size(); j++) {
					Map sFunMap = sFunList.get(j);
					String s_tache_code = (String) sFunMap.get("tache_code");
					String s_fun_id = (String) sFunMap.get("fun_id");
					if (StringUtil.equals(s_tache_code, t) && StringUtil.equals(s_fun_id, fun.getFun_id())) {
						funMap.put("checked", "checked");
						break;
					}
				}
				
				retList.add(funMap);
			}
		}
		
		return retMap;
	}
	@Override
	public List qryBizRequirementsList(String id) {
		String sql = "SELECT * FROM es_ecc_biz_requirements WHERE biz_id ='"+id+"'";
		List list = this.daoSupport.queryForLists(sql);
		return list;
	}
	
	@Override
	public void saveUpdateBizCheckCfg(Biz biz, ArrayList<BizRequirements> list, ArrayList<BizFunRel> bizFunList, ArrayList<BizFunRel> beforeBizFunList) {
		String biz_id = biz.getBiz_id();
		//String biz_id = this.baseDaoSupport.getSequences("S_ES_BIZ");
		//biz.setBiz_id(biz_id);
		this.baseDaoSupport.execute("DELETE FROM es_ecc_biz WHERE biz_id =? ", biz_id);
		this.baseDaoSupport.execute("DELETE FROM es_ecc_biz_requirements WHERE biz_id =? ", biz_id);
		this.baseDaoSupport.execute("DELETE FROM es_ecc_biz_fun_rel WHERE biz_id =? ", biz_id);
		this.baseDaoSupport.insert("ecc_biz", biz);
		int i = 0;
		for(BizRequirements bizRequirements : list){
			if(!StringUtil.isEmpty(bizRequirements.getCheck()) && "1".equals(bizRequirements.getCheck())&& !StringUtil.isEmpty(bizRequirements.getZ_value())){
				bizRequirements.setBiz_id(biz_id);
				Map br = ReflectionUtil.po2Map(bizRequirements);
				String required_id = this.baseDaoSupport.getSequences("S_ES_BIZ_REQUIREMENTS");
				br.put("required_id", required_id);
				br.put("opt_index", String.valueOf(++i));
				br.remove("check");
				this.baseDaoSupport.insert("ecc_biz_requirements", br);
			}
		}
		i = 0;
		for(BizFunRel bizFunRel : bizFunList){
			if(!StringUtil.isEmpty(bizFunRel.getCheck()) && "1".equals(bizFunRel.getCheck())){
				if(!StringUtil.isEmpty(bizFunRel.getTache_code())&&!StringUtil.isEmpty(bizFunRel.getFun_id())){
					bizFunRel.setBiz_id(biz_id);
					String [] fun_array = bizFunRel.getFun_id().split(",");
					for(String fun : fun_array){
						Map br = ReflectionUtil.po2Map(bizFunRel);
						br.put("order_id", String.valueOf(++i));
						br.put("fun_id", fun.trim());
						br.remove("check");
						this.baseDaoSupport.insert("ecc_biz_fun_rel", br);
					}
				}
			}
		}
		i = 0;
		for(BizFunRel bizFunRel : beforeBizFunList){
			if(!StringUtil.isEmpty(bizFunRel.getCheck()) && "1".equals(bizFunRel.getCheck())){
				if(!StringUtil.isEmpty(bizFunRel.getTache_code())&&!StringUtil.isEmpty(bizFunRel.getFun_id())){
					bizFunRel.setBiz_id(biz_id);
					String [] fun_array = bizFunRel.getFun_id().split(",");
					for(String fun : fun_array){
						Map br = ReflectionUtil.po2Map(bizFunRel);
						br.put("order_id", String.valueOf(++i));
						br.put("fun_id", fun.trim());
						br.remove("check");
						this.baseDaoSupport.insert("ecc_biz_fun_rel", br);
					}
				}
			}
		}
	}
	
	/**
	 * 处理是否预约单和是否开户数据
	 * @param editBizRequirementsList
	 * @param DC_DATA
	 * @return
	 */
	@Override
	public List deal_DC_DATA(List editBizRequirementsList ,List DC_DATA, String string){
		List data = new ArrayList();
		for(int i=0;i<editBizRequirementsList.size();i++){
			Map m = (Map)editBizRequirementsList.get(i);
			String attr_code=Const.getStrValue(m, "attr_code");
			String z_value = Const.getStrValue(m, "z_value");
			String[] values=z_value.split(",");
			
			if(string.equals(attr_code)){
				for(int j=0;j<DC_DATA.size();j++){
					Map dc = (Map)DC_DATA.get(j);
					Map obj = new HashMap();
					obj.putAll(dc);
					String key=Const.getStrValue(obj, "value");
					obj.put("check_type", "");
					for(int k=0;k<values.length;k++){
						String ke=values[k].trim();
						if(key.equals(ke)){
							obj.put("check_type", ke);
						}
					}
					data.add(obj);
					
				}
			}
		}
		
		return data;
	}
	
	/**
	 * 处理是否预约单等所有的数据
	 */
	@Override
	public List deal_newFactorCfgList(List editBizRequirementsList, List factorCfgList){
		List newFactorCfgList= new ArrayList();
		Map cfg_key=new HashMap();
		Set set =new HashSet();
		for(int i=0;i<editBizRequirementsList.size();i++){
			Map obj = (Map)editBizRequirementsList.get(i);
			Map m= new HashMap();
			m.putAll(obj);
			String attr_code=Const.getStrValue(m, "attr_code");
			String z_value = Const.getStrValue(m, "z_value");
			String[] values=z_value.split(",");
			String opt_type=Const.getStrValue(m, "opt_type");
			String pre_log=Const.getStrValue(m, "pre_log");
			String opt_index=Const.getStrValue(m, "opt_index");
			
			for(int n=0;n<factorCfgList.size();n++){
				Map obj1=(Map)factorCfgList.get(n);
				Map cfg=new HashMap();
				cfg.putAll(obj1);
				String a_code=Const.getStrValue(cfg, "attr_code");
				if(a_code.equals(attr_code)){
					cfg.put("pre_log",pre_log);
					cfg.put("opt_type",opt_type);
					cfg.put("opt_index",opt_index);
					cfg.put("code_check", "1");
					newFactorCfgList.add(cfg);
					cfg_key.put(attr_code, attr_code);
				}
				set.add(a_code);
			}
			
			
		}
		
		//循环没有选中的数据并加入集合中
		Iterator<String> it = set.iterator();  
		while (it.hasNext()) {  
		  String str = it.next();
		  if(cfg_key.containsKey(str)==false){
			  for(int t=0;t<factorCfgList.size();t++){
				  Map m1=(Map)factorCfgList.get(t);
				  String str1=Const.getStrValue(m1, "attr_code");
				  if(str.equals(str1)){
					  m1.put("pre_log","");
					  m1.put("opt_type","");
					  newFactorCfgList.add(m1);
				  }
			  }
		  }
		}
		for(int i=0;i<newFactorCfgList.size();i++){
			for(int j=1;j<newFactorCfgList.size()-i;j++){
				Map m = new HashMap();
				Map p = (Map) newFactorCfgList.get(j-1);
				Map q = (Map) newFactorCfgList.get(j);
				if ((Const.getStrValue(p, "opt_index")).compareTo(Const.getStrValue(q, "opt_index")) > 0) {
					  
                    m = (Map) newFactorCfgList.get(j - 1);  
                    newFactorCfgList.set((j - 1), newFactorCfgList.get(j));  
                    newFactorCfgList.set(j, m);  
                } 
			}
		}
		return newFactorCfgList;
	}

}
