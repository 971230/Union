package com.ztesoft.rule.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.bo.RuleDBAccess;
import com.ztesoft.rule.core.module.fact.FactAttrTag;
import com.ztesoft.rule.core.module.fact.FactTag;
import com.ztesoft.rule.core.module.fact.IFact;

/**
 * RuleObj初始化
 * 1.凡是实现了IFact接口的类均可通过本实现，将数据持久化到es_rule_obj&es_rule_obj_attr
 * 2.时机：在系统启动时处理或者通过界面触发
 * 3.原理：获取到IFact所有实现类，与上两表的数据做比对，无则insert，有则update
 * 4.最最大前提：Fact的实现会变得复杂，需引入注解(类层面：obj_name、ext_pack ; 属性层面：attr_name，其它的可以通过反射获取)
 * @author easonwu 
 * @creation Dec 14, 2013
 * 
 */
public class FactAnnInit {
	private IRuleDBAccess ruleDBAccess;

	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

	//转Map
	private Map<String, Map> toMap(List<Map> datas, String key) {
		Map<String, Map> result = new HashMap<String, Map>();
		for (Map data : datas) {
			result.put((String) data.get(key), data);
		}
		return result;
	}

	//转Map
	private Map<String, Map<String, Map>> toAttrMap(List<Map> datas) {
		Map<String, Map<String, Map>> result = new HashMap<String, Map<String, Map>>();
		for (Map data : datas) {
			Map<String, Map> objMap = result.get("clazz");
			if (objMap == null)
				objMap = new HashMap<String, Map>();

			objMap.put((String) data.get("attr_code"), data);
		}
		return result;
	}

	//初始化操作
	public boolean init() {
		Class factClass = IFact.class;
		List<Class> classes = ClazzUtil.getAllClassByInterface(factClass);

		List<Map> ruleObjList = ruleDBAccess.loadRuleObjConfigs();
		Map<String, Map> ruleObjMap = toMap(ruleObjList, "clazz");

		List<Map> ruleObjAttrList = ruleDBAccess.loadRuleObjAttrConfigs();
		Map<String, Map<String, Map>> ruleObjAttrMap = toAttrMap(ruleObjAttrList);

		List<Map> addRuleObjList = new ArrayList<Map>();
		List<Map> modRuleObjList = new ArrayList<Map>();
		List<Map> delRuleObjList = new ArrayList<Map>();

		List<Map> addRuleObjAttrList = new ArrayList<Map>();
		List<Map> modRuleObjAttrList = new ArrayList<Map>();
		List<Map> delRuleObjAttrList = new ArrayList<Map>();

		String currentDate = RuleDBAccess.currentDate();

		for (Class clazz : classes) {
			//没有，则insert
			//有则update
			if (clazz.isAnnotationPresent(FactTag.class)) {
				Map factObj = new HashMap();
				FactTag factTag = (FactTag) clazz.getAnnotation(FactTag.class);
				factObj.put("obj_code", factTag.obj_code());
				factObj.put("class_name", factTag.class_name());
				factObj.put("ext_pack", factTag.ext_pack());
				factObj.put("remark", factTag.remark());

				//类级别处理
				if (ruleObjMap.get(clazz.getName()) != null) {//存在,则update
					factObj.put("action", "M");
					factObj.put("obj_id", ruleObjMap.get(clazz.getName()).get(
							"obj_id"));
					factObj.put("status_date", currentDate);

					modRuleObjList.add(factObj);
					//					 getSequences
				} else {//不存在,则insert
					factObj.put("action", "A");
					factObj.put("status_cd", "00A");
					factObj.put("obj_type", "fact");
					factObj.put("clazz", clazz.getName());
					factObj.put("create_date", currentDate);
					factObj.put("obj_id", ruleDBAccess.nextVal("rule_obj"));

					addRuleObjList.add(factObj);
				}

				Map<String, Map> attrMap = ruleObjAttrMap.get(clazz.getName());
				//属性级别处理
				Field[] fs = clazz.getDeclaredFields();
				for (Field f : fs) {
					if (f.isAnnotationPresent(FactAttrTag.class)) {
						FactAttrTag attrTag = f
								.getAnnotation(FactAttrTag.class);

						Map attrData = new HashMap();
						attrData.put("attr_name", attrTag.attr_name());
						attrData.put("remark", attrTag.remark());

						if (attrMap.get(f.getName()) != null) {//存在,则update
							attrData.put("action", "M");
							attrData.put("attr_id", attrMap.get(f.getName())
									.get("attr_id"));
							attrData.put("status_date", currentDate);

							modRuleObjAttrList.add(attrData);
							//							 getSequences
						} else {//不存在,则insert
							attrData.put("action", "A");
							attrData.put("status_cd", "00A");
							attrData.put("attr_type", f.getType().getName());
							attrData.put("obj_id", factObj.get("obj_id"));
							attrData.put("attr_id", ruleDBAccess
									.nextVal("rule_obj_attr"));
							attrData.put("create_date", currentDate);

							addRuleObjAttrList.add(attrData);
						}
					}
				}

			}

			//结果处理 
			ruleDBAccess.insertRuleObj(addRuleObjList);
			ruleDBAccess.insertRuleObjAttr(addRuleObjAttrList);
			ruleDBAccess.updateRuleObj(modRuleObjList);
			ruleDBAccess.updateRuleObjAttr(modRuleObjAttrList);

			//注解+反射做文章！
		}

		return true;
	}
}
