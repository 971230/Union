package com.ztesoft.check.fun;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.inf.Check;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class ProcCheck extends AbstractCheckHander implements Check {

	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

	@Override
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException {
		// TODO Auto-generated method stub
		IdentifyResp resp = new IdentifyResp();
		resp.setCode(EccConsts.IDENTI_SUCCESS);
		resp.setMsg("校验成功.");
		
		String proc = identification.getClazz();
		final String storedProc = "{call "+proc+"(?,?,?,?)}";
		final String orderId = identification.getOrder_id();
		final String paramStr = identification.getStrParam();
		try{
			//调用存储过程		
			String returnStr =this.baseDaoSupport.executeProc(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con)
						throws SQLException {
					// TODO Auto-generated method stub
					// 调用的sql   
					CallableStatement cs = con.prepareCall(storedProc);
					cs.setString(1, orderId);// 设置输入参数的值  
					cs.setString(2, paramStr);// 设置输入参数的值  
					cs.registerOutParameter(3, OracleTypes.VARCHAR);// 注册输出参数的类型
					cs.registerOutParameter(4, OracleTypes.VARCHAR);// 注册输出参数的类型   
					return cs;
	
				}
			},new CallableStatementCallback<String>() {
				@Override
				public String doInCallableStatement(CallableStatement cs)
						throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					cs.execute();   				
					return cs.getString(3)+","+cs.getString(4);// 获取输出参数的值   
				}
			});
			if(StringUtils.isNotEmpty(returnStr)){
				String[] str = returnStr.split(",");
				resp.setCode(str[0]);
				resp.setMsg(str[1]);
			}
			if(EccConsts.IDENTI_SUCCESS.equals(resp.getCode())){
				throw new ApiBusiException(resp.getMsg());
			}
		}catch(Exception e){
			throw new ApiBusiException("error");
/*			resp.setCode(EccConsts.IDENTI_9999);
			resp.setMsg(e.getMessage());
			return resp;*/
		}
		return resp;
	}
}
