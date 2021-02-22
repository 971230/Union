/**
 * 
 */
package com.ztesoft.net.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.InfCommClientCalllog;
import com.ztesoft.net.service.IInfLogMonitManager;

/**
 * @author ZX
 * @version 2015-10-26
 * @see 接口日志监控管理
 * 
 */
public class InfLogMonitManagerImpl extends BaseSupport implements
		IInfLogMonitManager {

	@SuppressWarnings("unchecked")
	public Page toInfLogPage(String startTime, String endTime, int pageNo,
			int pageSize) throws FrameException {
		
		String sql = "SELECT T.COL3, T.OP_CODE, T.REQ_TIME, T.RSP_TIME, T.STATE_MSG FROM INF_COMM_CLIENT_CALLLOG T WHERE 1=1";

		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			// 如果始结时间空，则查询当前时间前一个月记录
			String curDate = DateUtil.getTime2();
			String beforeOneMonthDate = com.zte.cbss.util.DateUtil
					.getFormatString(
							com.zte.cbss.util.DateUtil.addDateByDays(-1),
							com.zte.cbss.util.DateUtil.FULL_PATTERN);
			sql += " AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') >= '"
					+ beforeOneMonthDate
					+ "' AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') <= '"
					+ curDate + "'";
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql += " AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') >= '"
					+ startTime + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql += " AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') <= '"
					+ endTime + "'";
		}
		sql += " AND (T.SOURCE_FROM IS NOT NULL OR T.SOURCE_FROM IS NULL)";
		sql += " ORDER BY T.REQ_TIME DESC";


		String countSql = "select count(*) from ("+sql+") cord";
		
		return baseDaoSupport.queryForCPage(sql, pageNo, pageSize, InfCommClientCalllog.class, countSql);
	}

	public String toInfLogAnalyze(String startTime, String endTime) {

		String sql = "SELECT T.* FROM INF_COMM_CLIENT_CALLLOG T WHERE 1=1";
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			return "1";
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql += " AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') >= '"
					+ startTime + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql += " AND TO_CHAR(T.REQ_TIME, 'yyyy-MM-dd hh:mm:ss') <= '"
					+ endTime + "'";
		}
		sql += " AND (T.SOURCE_FROM IS NOT NULL OR T.SOURCE_FROM IS NULL)";
		sql += " ORDER BY T.REQ_TIME DESC";

		@SuppressWarnings("unchecked")
		List<InfCommClientCalllog> list = baseDaoSupport.queryForList(sql, new RowMapper(){
					public Object mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						InfCommClientCalllog calllog = new InfCommClientCalllog();
						String log_id = arg0.getString("log_id");
						String op_code = arg0.getString("op_code");
						String req_time = arg0.getString("req_time");
						String rsp_time = arg0.getString("rsp_time");
						String ep_address = arg0.getString("ep_address");
						String state = arg0.getString("state");
						String state_msg = arg0.getString("state_msg");
						String param_desc = arg0.getString("param_desc");
						String result_desc = arg0.getString("result_desc");
						String col3 = arg0.getString("col3");
						String req_xml = "";
						String rsp_xml = "";
						
						Object req_obj = arg0.getBlob("req_xml");
						Object rsp_obj = arg0.getBlob("rsp_xml");
						
						if (req_obj != null) {
							 Class clazz = req_obj.getClass();
							    Method method;
								try {
									  method = clazz.getMethod("getBinaryStream", new Class[]{});
									  InputStream is = (InputStream)method.invoke(req_obj, new Object[]{});
									  InputStreamReader reader = new InputStreamReader(is,"GBK");
									  BufferedReader br = new BufferedReader(reader);
									  String s = br.readLine();
									  StringBuffer sb = new StringBuffer();
									  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
										sb.append(s);
									    s = br.readLine();
									   }
									  req_xml = sb.toString();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
						if (rsp_obj != null) {
							 Class clazz = rsp_obj.getClass();
							    Method method;
								try {
									  method = clazz.getMethod("getBinaryStream", new Class[]{});
									  InputStream is = (InputStream)method.invoke(rsp_obj, new Object[]{});
									  InputStreamReader reader = new InputStreamReader(is,"GBK");
									  BufferedReader br = new BufferedReader(reader);
									  String s = br.readLine();
									  StringBuffer sb = new StringBuffer();
									  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
										sb.append(s);
									    s = br.readLine();
									   }
									  rsp_xml = sb.toString();
										
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
						
						calllog.setLog_id(log_id);
						calllog.setOp_code(op_code);
						calllog.setReq_time(req_time);
						calllog.setRsp_time(rsp_time);
						calllog.setEp_address(ep_address);
						calllog.setState(state);
						calllog.setState_msg(state_msg);
						calllog.setParam_desc(param_desc);
						calllog.setResult_desc(result_desc);
						calllog.setCol3(col3);
						calllog.setReq_xml(req_xml);
						calllog.setRsp_xml(rsp_xml);
						return calllog;
					}
			
		});

		matchDict(list); // 多线程处理字典匹配

		return "0";
	}

	private void matchDict(List<InfCommClientCalllog> list) {
		if (list != null && list.size() > 0) {
			for (InfCommClientCalllog calllog : list) {
				MatchDictLogsReq req = new MatchDictLogsReq();
				req.setObj_id(calllog.getCol3());
				req.setOp_code(calllog.getOp_code());
				req.setLocal_log_id(calllog.getLog_id());
				req.setRsp_xml(calllog.getRsp_xml());
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
						.getSourceFrom());
				MatchDictLogsResp resp = client.execute(req,
						MatchDictLogsResp.class);
			}
		}
	}

}
