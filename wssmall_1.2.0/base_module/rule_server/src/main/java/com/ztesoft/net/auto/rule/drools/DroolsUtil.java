package com.ztesoft.net.auto.rule.drools;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import params.ZteResponse;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.rule.fact.OrderPageFact;

/**
 * 规则工具类
 * 使用共用的一个session session改为无状态的，有状态的session会保存实体数据。
 * StatefulKnowledgeSession是有状态session 
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public class DroolsUtil {
	
	public static final String RES_TYPE_DRL = "DRL";
	
	public static final Lock lock = new ReentrantLock();
	
	public static Map<String,StatefulKnowledgeSession> ruleSessionMap = new HashMap<String,StatefulKnowledgeSession>();
	//静态
	public static Map<String,StatelessKnowledgeSession> lessSessionMap = new HashMap<String,StatelessKnowledgeSession>();
	
	/**
	 * 静态执行
	 * @作者 MoChunrun
	 * @创建日期 2014-11-22 
	 * @param rule_id
	 * @param script
	 * @param resourceType
	 * @param fact
	 * @throws ApiBusiException
	 */
	public static void callRuleByLess(String rule_id,String script,AutoFact fact) throws ApiBusiException{
		callRuleByLess(rule_id, script, RES_TYPE_DRL, fact);
	}
	public static void callRuleByLess(String rule_id,String script,String resourceType,AutoFact fact) throws ApiBusiException{
		if("201410254964000164".equals(rule_id))
			logger.info("111111111111111111111");
		StatelessKnowledgeSession ksession = lessSessionMap.get(rule_id);
//		if(ksession==null){
			if(StringUtil.isEmpty(script))throw new ApiBusiException("[script]不能为空");
			if(StringUtil.isEmpty(resourceType))throw new ApiBusiException("[resourceType]不能为空");
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newByteArrayResource(script.getBytes()),ResourceType.getResourceType(resourceType));
			if (kbuilder.hasErrors()) {
				logger.info("规则文件错误"+kbuilder.getErrors());
				throw new ApiBusiException("Unable to compile ["+resourceType+"]");
			}
			Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
			KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(pkgs);
			ksession = kbase.newStatelessKnowledgeSession();
			lessSessionMap.put(rule_id, ksession);
//		}
		
		ksession.execute(fact);
	}
	
	/**
	 * 执行规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param script
	 * @param resourceType
	 * @param fact
	 * @throws Exception
	 */
	public static void callRule(String rule_id,String script,String resourceType,AutoFact fact) throws ApiBusiException{
		StatefulKnowledgeSession session = createSession(rule_id,script, resourceType);
		synchronized (session) {
			insertFact(session, fact);
			fireAllRulesAndDispose(session);
		}
		/*try{
			lock.lock();
			insertFact(session, fact);
			fireAllRulesAndDispose(session);
		}finally{
			lock.unlock();
		}*/
	}
	
	/**
	 * 执行规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param script
	 * @param resourceType
	 * @param fact
	 * @throws Exception
	 */
	public static void callRule(String script,String resourceType,List<AutoFact> facts) throws ApiBusiException{
		StatefulKnowledgeSession session = createSession(script, resourceType);
		insertFact(session, facts);
		fireAllRulesAndDispose(session);
	}
	
	/**
	 * 执行规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param script
	 * @param resourceType
	 * @param fact
	 * @throws Exception
	 */
	public static void callRule(String rule_id,String script,AutoFact fact) throws ApiBusiException{
		callRule(rule_id,script, RES_TYPE_DRL, fact);
	}
	
	/**
	 * 执行规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param script
	 * @param resourceType
	 * @param fact
	 * @throws Exception
	 */
	public static void callRule(String script,List<AutoFact> facts) throws ApiBusiException{
		callRule(script, RES_TYPE_DRL, facts);
	}

	/**
	 * 启动规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param session
	 */
	public static void fireAllRulesAndDispose(StatefulKnowledgeSession session){
		// 启动所有规则
		session.fireAllRules();
		// 释放资源
		session.dispose();
	}
	
	public static StatefulKnowledgeSession createSession(String script) throws ApiBusiException{
		return createSession(script, RES_TYPE_DRL);
	}
	
	/**
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param script
	 * @param resourceType  DRL
	 * @return
	 */
	public static StatefulKnowledgeSession createSession(String script,String resourceType) throws ApiBusiException{
		if(StringUtil.isEmpty(script))throw new ApiBusiException("[script]不能为空");
		if(StringUtil.isEmpty(resourceType))throw new ApiBusiException("[resourceType]不能为空");
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newByteArrayResource(script.getBytes()),ResourceType.getResourceType(resourceType));
		if (kbuilder.hasErrors()) {
			logger.info("规则文件错误"+kbuilder.getErrors());
			throw new ApiBusiException("Unable to compile ["+resourceType+"]");
		}
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		return ksession;
	}
	
	/**
	 * 按规则缓存规则session 并返回
	 * @作者 MoChunrun
	 * @创建日期 2014-11-20 
	 * @param rule_id
	 * @param script
	 * @param resourceType
	 * @return
	 * @throws ApiBusiException
	 */
	public static StatefulKnowledgeSession createSession(String rule_id,String script,String resourceType) throws ApiBusiException{
		StatefulKnowledgeSession ksession = ruleSessionMap.get(rule_id);
		if(ksession==null){
			ksession = createSession(script, resourceType);
			ruleSessionMap.put(rule_id, ksession);
		}
		return ksession;
	}
	
	/**
	 * 清空session
	 * @作者 MoChunrun
	 * @创建日期 2014-11-20
	 */
	public static void clearRuleSession(){
		try{
			ruleSessionMap.clear();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			lessSessionMap.clear();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertFact(StatefulKnowledgeSession session,List<AutoFact> facts){
		for(AutoFact fact:facts){
			insertFact(session, fact);
		}
	}
	
	public static void insertFact(StatefulKnowledgeSession session,AutoFact fact){
//		logger.info("订单fact对象插入insertFact："+":"+fact.getObj_id());
		session.insert(fact);
	}
	
	private static Logger logger = Logger.getLogger(DroolsUtil.class);
	
	public static void main(String[] args) throws ApiBusiException {
		
		for(int i=0;i<40;i++){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
						long start = System.currentTimeMillis();
						String str = "package com.ztesoft.rule                            "+
								"import com.ztesoft.net.auto.rule.drools.OrderPageFact       "+
								"rule \"2014101065310000870\"                          "+
								"when                                                "+
								"$fact:OrderPageFact();                           "+
								"                                                    "+
								"then                                                "+
								"   $fact.setActionUrl(\"ooooooooooooooooooooo\");      "+
								"end";

						OrderPageFact fact = new OrderPageFact();
						ZteResponse resp = new ZteResponse();
						try {
							//DroolsUtil.callRule("111",str, fact);
							DroolsUtil.callRuleByLess("111",str, fact);
						} catch (ApiBusiException e1) {
							e1.printStackTrace();
						}
						long end = System.currentTimeMillis();
				    	logger.info(end-start);
				}
			}).start();
	    	
	    	
//	    	logger.debug("loggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerloggerlogger测试测试测试测试测试");
//			logger.info("infoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfo测试测试测试测试测试");
		
			
			
		}
	}
}
