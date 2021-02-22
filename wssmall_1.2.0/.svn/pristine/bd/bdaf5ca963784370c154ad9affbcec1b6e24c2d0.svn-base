package com.ztesoft.net.sqls;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.ibss.common.util.CrmConstants;

/**
 * 日期SQL构造器
 * 传入的参数格式为“开始时间|结束时间"
 * 日期的格式为yyyy-MM-dd HH:mm:ss
 * 
 * 返回的SQL为“ [and 列名 to_date(开始时间)] [and 列名 to_date(结束时间)]”
 * 
 * 可以只传开始时间--“开始时间|"
 * 
 * 可以只传结束时间--“|结束时间"
 * 
 * @author zhaoc
 *
 */
public class SqlDateBuilder extends SqlBuilder {
	public SqlDateBuilder(){
		super();
	}
	
	public SqlDateBuilder(String col_name, Object parm) {
		super(col_name, parm);
	}
	
	public SqlDateBuilder(String col_name, Object parm,boolean isAnd) {
		super(col_name, parm,isAnd);
	}

	@Override
	public String getSql() throws Exception {
		if(StringUtils.isBlank(this.col_name) || this.parm==null)
			return "";
		
		String condition = " and ";
		if(!isAnd)
			condition = " or ";
		
		String str = String.valueOf(parm);
		StringBuilder builder = new StringBuilder();
		
		if(StringUtils.isBlank(str))
			return "";
		
		String[] dateArr = String.valueOf(str).split("\\|");
		
		if(dateArr!=null && dateArr.length>1){
			SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
			
			String start = dateArr[0];
			String end = dateArr[1];
			
			try{
				format.parse(start);
				
				builder.append(condition).append(this.col_name).append(">=to_date('").append(start).append("','yyyy-MM-dd hh24:mi:ss') ");
			}catch(Exception e){
				
			}
			
			try{
				format.parse(end);
				builder.append(condition).append(this.col_name).append("<=to_date('").append(end).append("','yyyy-MM-dd hh24:mi:ss') ");
			}catch(Exception e){
				
			}
		}
		
		return builder.toString();
	}
}
