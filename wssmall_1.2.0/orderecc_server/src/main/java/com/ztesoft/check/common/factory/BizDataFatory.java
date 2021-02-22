package com.ztesoft.check.common.factory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.wltea.expression.ExpressionEvaluator;

import com.ztesoft.check.common.Consts;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFator;
import com.ztesoft.check.model.BizRequirements;
import com.ztesoft.net.eop.sdk.utils.ReflectionUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class BizDataFatory{
	//自定义业务
	private final ConcurrentMap<Biz, List<BizRequirements>> bizRequirementsMap = new ConcurrentHashMap<Biz, List<BizRequirements>>();
	//要过滤的业务
	private final ConcurrentMap<Biz, List<BizRequirements>> antiBizRequirementsMap = new ConcurrentHashMap<Biz, List<BizRequirements>>();
	//工作流
	private final ConcurrentMap<Biz, List<BizRequirements>> flowBizRequirementsMap = new ConcurrentHashMap<Biz, List<BizRequirements>>();
	private final ConcurrentMap<String, Biz> bizList = new ConcurrentHashMap<String, Biz>();
	
	public static BizDataFatory getInstance(){
		return BizDataFatoryLoader.instance;
	}
	
	private static class BizDataFatoryLoader {
		public static BizDataFatory instance = new BizDataFatory();
	}
	
	private void init(){
		bizRequirementsMap.clear();
		antiBizRequirementsMap.clear();
		flowBizRequirementsMap.clear();
		bizList.clear();
		String sql = "select * from es_ecc_biz i where i.status='00A'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<Biz> bizList = baseDaoSupport.queryForList(sql, Biz.class);
		for(Biz biz : bizList){//遍历es_ecc_biz匹配biz
			this.bizList.put(biz.getBiz_id(), biz);
			sql = "select * from es_ecc_biz_requirements i where i.biz_id=? and i.status='00A'";
			List<BizRequirements> bizRequirementsList = baseDaoSupport.queryForList(sql, BizRequirements.class, biz.getBiz_id());
			if(biz.getBiz_type().equals(Consts.BIZ_TYPE_001)){
				antiBizRequirementsMap.put(biz, bizRequirementsList);
			}else if(biz.getBiz_type().equals(Consts.BIZ_TYPE_002)){
				flowBizRequirementsMap.put(biz, bizRequirementsList);
			}else if(biz.getBiz_type().equals(Consts.BIZ_TYPE_003)){
				bizRequirementsMap.put(biz, bizRequirementsList);
			}
		}	
	}
	
	public BizDataFatory(){
		init();
	}

	private Biz matchBiz(BizFator bizFator, final ConcurrentMap<Biz, List<BizRequirements>> bizRequirementsMap){
		if(null == bizRequirementsMap || bizRequirementsMap.isEmpty()){
			this.init();
		}
		for(Map.Entry<Biz, List<BizRequirements>> entry : bizRequirementsMap.entrySet()){
			Biz biz = entry.getKey();
			List<BizRequirements> list = entry.getValue();
			if(list.isEmpty()) continue;//为空跳过
			StringBuffer express = new StringBuffer("true");
			for(BizRequirements bizRequ : list){
				String attr_value = (String) ReflectionUtils.getFieldValue(bizFator, bizRequ.getAttr_code());
				express.append(bizRequ.getPre_log());
				if(CONTAINS.equals(bizRequ.getOpt_type())){
					express.append(IK_CONTAINS+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(NOT_CONTAINS.equals(bizRequ.getOpt_type())){
					express.append("!"+IK_CONTAINS+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(MATCHES.equals(bizRequ.getOpt_type())){
					express.append(IK_MATCHES+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(NOT_MATCHES.equals(bizRequ.getOpt_type())){
					express.append("!"+IK_MATCHES+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(IN.equals(bizRequ.getOpt_type())){
					express.append(IK_IN+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(NOT_IN.equals(bizRequ.getOpt_type())){
					express.append("!"+IK_IN+"(\""+attr_value+"\",\""+bizRequ.getZ_value()+"\")");
				}else if(EQ.equals(bizRequ.getOpt_type())){
					express.append(attr_value + EQ + bizRequ.getZ_value());
				}else if(NE.equals(bizRequ.getOpt_type())){
					express.append(attr_value + NE + bizRequ.getZ_value());
				}else if(GE.equals(bizRequ.getOpt_type())){
					express.append(attr_value + GE + bizRequ.getZ_value());
				}else if(GT.equals(bizRequ.getOpt_type())){
					express.append(attr_value + GT + bizRequ.getZ_value());
				}else if(LT.equals(bizRequ.getOpt_type())){
					express.append(attr_value + LT + bizRequ.getZ_value());
				}else if(LE.equals(bizRequ.getOpt_type())){
					express.append(attr_value + LE + bizRequ.getZ_value());
				}
				
			}
//			logger.info("ExpressionEvaluator.evaluate:"+express.toString());
//			Object result = ExpressionEvaluator.evaluate("true&&$MATCHES(\"4G\",\"4G\")&&$MATCHES(\"0\",\"0\")&&$MATCHES(\"1\",\"1\")&&$IN(\"20000\",\"20000,20002\")");
			Object result = ExpressionEvaluator.evaluate(express.toString());
			if((Boolean)result) return biz;
		}
		
		return null;
	}
	
	/**
	 * 根据业务因子匹配工作流
	 * @param bizFator
	 * @return
	 */
	public Biz getBizByFator(BizFator bizFator){
		return this.matchBiz(bizFator, bizRequirementsMap);
	}
	
	/**
	 * 根据业务因子匹配工作流
	 * @param bizFator
	 * @return
	 */
	public Biz getAntiBizByFator(BizFator bizFator){
		return this.matchBiz(bizFator, antiBizRequirementsMap);
	}	
	
	/**
	 * 根据业务因子匹配工作流
	 * @param bizFator
	 * @return
	 */
	public Biz getflowBizByFator(BizFator bizFator){
		return this.matchBiz(bizFator, flowBizRequirementsMap);
	}
	/**
	 * 
	 * @param key = biz.getBiz_id()
	 * @return
	 */
	public Biz getBizByKey(String key){
		if(null == bizList || bizList.isEmpty()){
			this.init();
		}
		if(this.bizList.containsKey(key)){
			return this.bizList.get(key);
		}
		return null;
	}
	
	public void refreshData(){
		this.init();
	}
	
	
	public static final String EQ = "==";
	public static final String NE = "!=";
	public static final String GE = ">=";
	public static final String GT = ">";
	public static final String LT = "<";
	public static final String LE = "<=";
	public static final String CONTAINS = "contains";
	public static final String NOT_CONTAINS = "not contains";
	public static final String MEMBEROF = "memberof";
	public static final String NOT_MEMBEROF = "not memberof";
	public static final String MATCHES = "matches";
	public static final String NOT_MATCHES = "not matches";
	public static final String IN = "in";
	public static final String NOT_IN = "not in";
	
	public static final String IK_CONTAINS = "$CONTAINS";
	public static final String IK_STARTSWITH = "$STARTSWITH";
	public static final String IK_ENDSWITH = "$ENDSWITH";
	public static final String IK_CALCDATE = "$CALCDATE";
	public static final String IK_SYSDATE = "$SYSDATE";
	public static final String IK_DAYEQUALS = "$DAYEQUALS";
	public static final String IK_IN = "$IN";
	public static final String IK_MATCHES = "$MATCHES";

}
