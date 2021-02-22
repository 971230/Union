package com.ztesoft.rule.core.test;

import org.apache.log4j.Logger;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	public static void main(String[] args) {
		int i = 1;
		logger.info(i);
		logger.info(i<<1);
		logger.info(i<<2);
		logger.info(i<<3);
		double a = 0.5;
		logger.info(Math.pow(a,0));
		logger.info(Math.pow(a,1));
		logger.info(Math.pow(a,2));
		logger.info(Math.pow(a,3));
		
		double b = 0.5;
		int c = 8;
		logger.info(Math.pow(b,0));
		logger.info(c*Math.pow(b,1));
		logger.info(c*Math.pow(b,2));
		logger.info(c*Math.pow(b,3));
		logger.info(c*Math.pow(b,4));
	}
	
}
