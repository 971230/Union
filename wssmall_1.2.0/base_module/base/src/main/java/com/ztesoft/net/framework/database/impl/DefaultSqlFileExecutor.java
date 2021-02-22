package com.ztesoft.net.framework.database.impl;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.database.ISqlFileExecutor;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.StringUtil;
/**
 * 默认sql文件执行器
 * @author kingapex
 * 2010-1-25上午11:50:19
 */
final public class DefaultSqlFileExecutor implements ISqlFileExecutor {
	
	private JdbcTemplate jdbcTemplate;
	
	protected final static Logger logger = Logger.getLogger(DefaultSqlFileExecutor.class);
	
	
	@Override
	public void execute(String sqlPath) { 
		
		execute(sqlPath,false);

	}
	
	public void execute(String sqlPath,boolean exampleData) { 
		String content ;
		if(sqlPath.startsWith("file:")){
			content = FileBaseUtil.readFile(sqlPath.replaceAll("file:", ""));
		 
		}else{
			content = sqlPath;
		}

		batchExecute(content,exampleData);

	}
	 
	
	private void batchExecute(String content){
		batchExecute(content,false);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void batchExecute(String content, boolean exampleData){
//		if(content!=null && "1".equals(EopSetting.DBTYPE)){
//			content = content.replace('[', '`');
//			content = content.replace(']', '`');
//		}
		content = StringUtil.delSqlComment(content);
		content = content.replaceAll("\r", "");
		String spliter = ";\n";
		if(EopSetting.DBTYPE.equals("2") || EopSetting.DBTYPE.equals("3")){
			if(EopSetting.DBTYPE.equals("2") && exampleData)
				spliter = ";\n";
			else
				spliter="\ngo\n";
		}
		
		String[] sql_ar = content.split(spliter);

/*
		if(EopSetting.DBTYPE.equals("2")){
			for(int i=0;i<sql_ar.length;i++){
				String prefix = sql_ar[i].toLowerCase();
				prefix = prefix.substring(0,7).trim();
				if(!"create".equals(prefix))
					sql_ar[i] = sql_ar[i] + ";";
			}
		}
*/
		if(EopSetting.DBTYPE.equals("3")){
			if(sql_ar.length==1){ //sqlserver执行示例数据时，没有go语句，不能用go分隔
				sql_ar = content.split(";\n");
			}
		}
		
		if(StringUtil.isEmpty(content) || sql_ar== null || sql_ar.length==0) return ;
		
		if(logger.isDebugEnabled()){
			logger.debug("开始执行sql...." );
		}
		
		try{
		 	//this.jdbcTemplate.batchUpdate(sql_ar);
			for(int i=0;i<sql_ar.length;i++){
			String s = sql_ar[i];
			if(!StringUtil.isEmpty(s)){
				s = s.trim();
//				if(logger.isDebugEnabled()){
//					logger.debug("execute->"+s );
//				}
// 			logger.info("execute->"+s);
				if(!s.startsWith("declare")  )
				this.jdbcTemplate.execute(s); 
			} 
			else{ 
				logger.info("is empty");
			}
		  }				
		}catch(RuntimeException e){
			DefaultSqlFileExecutor.logger.info("执行sql出错",e.fillInStackTrace());
			throw  e;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("执行完成");
		}
		
	}


	private String mysql_escape_string(String str){
	       if( str == null || str.length() == 0 )
	           return str;
	       str = str.replaceAll("'", "\\'");
	       str = str.replaceAll("\"", "\\\"");        
	       return str;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public static void main(String[] args){
		String str= "abcajfjf[user]fj;ksafj;sajfoiju[rule]rrifj[delete]sdjfdf";
		logger.info(str.replaceAll("([)(.*)(])", "$1"));
	}
	
}
