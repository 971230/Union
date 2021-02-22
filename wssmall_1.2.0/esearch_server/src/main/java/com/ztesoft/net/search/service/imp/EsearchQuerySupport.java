package com.ztesoft.net.search.service.imp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.ESearchData;

@SuppressWarnings("rawtypes")
public class EsearchQuerySupport extends BaseSupport {
	
	private static EsearchQuerySupport inst = null;
	
	public static EsearchQuerySupport getInstance(){
		inst = SpringContextHolder.getBean("esearchQuerySupport");
		return inst;
	}
	
	public List qryForLogInfoPage(Map<String,Object> values){
		int pageNum = (Integer) values.get("pageNum");
		int pageSize = (Integer) values.get("pageSize");
		String sql = (String) values.get("sql");
		
		Page page = this.baseDaoSupport.queryForPage(sql, pageNum, pageSize,new RowMapper(){

			@SuppressWarnings("unchecked")
			@Override
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ESearchData esData = new ESearchData();
				
				Date reqTime =  rs.getTimestamp("REQ_TIME");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String ds = sdf.format(reqTime);//根据日期作为索引
				String index = "inf_comm_client_calllog_idx"+ds+"";
				esData.setIndex(index);
				esData.setType("inf_comm_client_calllog");
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSZ");
				String busiTime = sdf1.format(reqTime);
				esData.setBusi_time(busiTime);
				
				String creatTime = sdf1.format(new Date());
				esData.setCreate_time(creatTime);
				
				esData.setLog_id((String) rs.getString("LOG_ID"));

				esData.setOperation_code(rs.getString("OP_CODE"));
				
				String inBlobStr ="";
				Object inBlob = rs.getBlob("REQ_XML");
				if(inBlob !=null){
				    Class clazz = inBlob.getClass();
				    Method method;
					try {
						  method = clazz.getMethod("getBinaryStream", new Class[]{});
						  InputStream is = (InputStream)method.invoke(inBlob, new Object[]{});
						  InputStreamReader reader = new InputStreamReader(is,"GBK");
						  BufferedReader br = new BufferedReader(reader);
						  String s = br.readLine();
						  StringBuffer sb = new StringBuffer();
						  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
							sb.append(s);
						    s = br.readLine();
						   }
						  inBlobStr = sb.toString();
							
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				
				String outBlobStr ="";
				Object outBlob = rs.getBlob("RSP_XML");
				if(outBlob !=null){
				    Class clazz = outBlob.getClass();
				    Method method;
					try {
						  method = clazz.getMethod("getBinaryStream", new Class[]{});
						  InputStream is = (InputStream)method.invoke(outBlob, new Object[]{});
						  InputStreamReader reader = new InputStreamReader(is,"GBK");
						  BufferedReader br = new BufferedReader(reader);
						  String s = br.readLine();
						  StringBuffer sb = new StringBuffer();
						  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
							sb.append(s);
						    s = br.readLine();
						   }
						  outBlobStr = sb.toString();
							
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				esData.setIn_param(inBlobStr);
				esData.setOut_param(outBlobStr);
				
				return esData;
			}
			
		});
		
		return page.getResult();
	}
	
	public List qryForLogInfoList(Map<String,Object> values){
		String sql = (String) values.get("sql");
		
		return null;
	}
}


