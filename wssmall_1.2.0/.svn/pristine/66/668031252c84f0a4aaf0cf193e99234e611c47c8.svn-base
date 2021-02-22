package com.ztesoft.rule.core.test;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.ztesoft.net.rule.order.OrderFact;
import com.ztesoft.rule.core.module.fact.RuleResult;
import com.ztesoft.rule.core.module.fact.RuleResults;

public class TestOrder {
	private static Logger logger = Logger.getLogger(TestOrder.class);
	public static void main(String[] args) {
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		String str = "package test                                            \n"
				+"import com.ztesoft.net.rule.order.OrderFact         \n"
				+"rule \"testorder\"                                        \n"
				+"when                                                    \n"
				+"  $order:OrderFact();                           \n"
				+"then                                                    \n"
				+"  double tmp = $order.getGoods_money()*0.20;            \n"
				+"  logger.info(tmp);          \n"
				+"  $order.addResult(\"888888\",tmp) ;                      \n"
				+"end                                                     \n"
				+"rule \"testorder2\"                                        \n"
				+"when                                                    \n"
				+"  $order:OrderFact();                           \n"
				+"then                                                    \n"
				+"  double tmp = $order.getGoods_money()*0.50;            \n"
				+"  logger.info(\"0.5:=\"+tmp);          \n"
				+"  $order.addResult(\"888888\",tmp) ;                      \n"
				+"end                                                     \n";
		
		
//		String str = "package com.ztesoft.rule                                                              "+
//				"import com.ztesoft.net.rule.order.OrderFact                                           "+
//				"rule \"test1110\"                                                                     "+
//				"when                                                                                  "+
//				"$orderFact:OrderFact(goods_money != 100 || grade <= 9 || staff_no == 1);              "+
//				"                                                                                      "+
//				"then                                                                                  "+
//				"$orderFact.setAmount($orderFact.getGoods_money()*$orderFact.getGoods_num()*0.1);      "+
//				"$orderFact.addResult(\"test111\",\"201403263488000063\",$orderFact.getAmount());      " +
//				"logger.info($orderFact.getGoods_money()+\":88888888:\"+$orderFact.getAmount());"+
//				"end                                                                                   "+
//				"                                                                                      "+
//				"rule \"test1114\"                                                                     "+
//				"when                                                                                  "+
//				"$orderFact:OrderFact(goods_money > 99 && goods_num > 7 && lan_code == 90);            "+
//				"                                                                                      "+
//				"then                                                                                  "+
//				"$orderFact.setAmount(200.0);                                                          "+
//				"$orderFact.addResult(\"test111\",\"201403263488000063\",$orderFact.getAmount());      "+
//				"logger.info($orderFact.getGoods_money()+\":77777:\"+$orderFact.getAmount());"+
//				"end                                                                                   "+
//				"                                                                                      "+
//				"rule \"test1118\"                                                                     "+
//				"when                                                                                  "+
//				"$orderFact:OrderFact(goods_money > 999);                                              "+
//				"                                                                                      "+
//				"then                                                                                  "+
//				"$orderFact.setAmount(200.0);                                                     "+
//				"$orderFact.addResult(\"test111\",\"201403263488000063\",$orderFact.getAmount()); "+
//				"logger.info($orderFact.getGoods_money()+\":999999999999:\"+$orderFact.getAmount());"+
//				"end                                                                                   ";
		
		kbuilder.add(ResourceFactory.newByteArrayResource(str.getBytes()),ResourceType.getResourceType("DRL") );
		//kbuilder.add(ResourceFactory.newClassPathResource("order.drl",TestOrder.class), ResourceType.DRL);
		
		if (kbuilder.hasErrors()) {
			logger.info(kbuilder.getErrors().toString());
			throw new RuntimeException("Unable to compile \"order.drl\".");
		}
		
		final Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);
		final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		OrderFact of = new OrderFact();
		of.setGoods_id("1");
		of.setGoods_money(20000d);
		of.setGoods_num(1);
		of.setGoods_type("no");
		of.setLan_code("1");
		of.setOrder_id("123");
		ksession.insert(of);
		ksession.fireAllRules();
		ksession.dispose();
		RuleResults rs = of.getRuleResults();
		for(RuleResult r:rs.getResults()){
			logger.info(r.getResult()+"\t"+r.getRule_id());
		}
		logger.info(of.getAmount());
	}
	
}
