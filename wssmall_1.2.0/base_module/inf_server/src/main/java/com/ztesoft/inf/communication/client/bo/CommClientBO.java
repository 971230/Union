package com.ztesoft.inf.communication.client.bo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.EsearchAddReq;
import params.req.EsearchUpdateReq;
import params.req.OrderExpWriteForInfReq;
import params.resp.EsearchAddResp;
import params.resp.EsearchUpdateResp;
import params.resp.OrderExpWriteResp;
import zte.net.iservice.IOrderQueueBaseManager;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.framework.sqls.SF;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.inf.communication.client.vo.ClientRequest;
import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
import com.ztesoft.inf.communication.client.vo.ClientResponse;
import com.ztesoft.inf.framework.dao.SeqUtil;
import com.ztesoft.inf.framework.dao.SqlExeNew;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.model.ESearchData;
import commons.CommonTools;

import consts.ConstsCore;

public class CommClientBO extends BaseSupport implements ICommClientBO{

	private static final String SELECT_OPERATION_BY_CODE = SF
			.frameworkSql("SELECT_OPERATION_BY_CODE");
	private static final String SELECT_ENDPOINT = SF
			.frameworkSql("SELECT_ENDPOINT");
	private static final String SELECT_REQUEST = SF
			.frameworkSql("SELECT_REQUEST");
	private static final String SELECT_RESPONSE = SF
			.frameworkSql("SELECT_RESPONSE");
	private static final String SELECT_GLOBAL_VARS = SF
			.frameworkSql("SELECT_GLOBAL_VARS");
	private static final String SELECT_LOG_COLS = SF
			.frameworkSql("SELECT_LOG_COLS");
	private static final String SELECT_REQUEST_USER = SF
			.frameworkSql("SELECT_REQUEST_USER");

	//
	private static final String INSERT_CLIENT_CALLLOG = SF
			.frameworkSql("INSERT_CLIENT_CALLLOG");
	private static final String INSERT_CLIENT_CALLLOG_NO_CLOB = SF
			.frameworkSql("INSERT_CLIENT_CALLLOG_NO_CLOB");

	private static final String INSERT_INF_CRM_SPS_LOG = SF
			.frameworkSql("INSERT_INF_CRM_SPS_LOG");
	
	private static final String SELECT_REMOTE_SERVICE = SF
			.frameworkSql("SELECT_REMOTE_SERVICE");
	
	private static final String UPDATE_INF_CALL_LOG = SF.frameworkSql("UPDATE_INF_CALL_LOG");
	private static final String UPDATE_INF_CALL_LOG_NO_CLOB = SF.frameworkSql("UPDATE_INF_CALL_LOG_NO_CLOB");

	 private SqlExeNew sqlExecNew = new SqlExeNew();

	 int time =60*24*60*20;//缺省缓存20天,memcache最大有效期是30天
	 private static INetCache cache = null;
	 
	 private IOrderQueueBaseManager orderQueueBaseManager;
	 static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType(""); //add by wui订单调用单独的订单缓存机器，通过业务分开，和业务静态数据分开
	 }
	 public static int space = 7798;
	@Override
	public Map getLogColsByOpId(String opId) throws FrameException {
		Map logCols = new HashMap();
		List list = null;
//		try {
//			list = this.baseDaoSupport.queryForList(SELECT_LOG_COLS, opId);
//		} catch (Exception e) {
//
//		}
		if (list != null) {
			for (Object obj : list) {
				Map item = (Map) obj;
				logCols.put(item.get("col_name"), item.get("param_key"));
			}
		}
		return logCols;
	}

	@Override
	public ClientOperation getOperationByCode(String operationCode,String ep_mall)
			throws FrameException {
		
		ClientOperation operation = (ClientOperation)cache.get(space, "INF_OPERATION"+operationCode+ep_mall);
		if(operation !=null)
			return operation;
		return this.getOperationByCodeFromDB(operationCode, ep_mall);
	}	
	@Override
	public ClientOperation getOperationByCodeFromDB(String operationCode,String ep_mall)throws FrameException{	
		Map map = null;
		try {
			List list = this.baseDaoSupport.queryForList(SELECT_OPERATION_BY_CODE, operationCode);
			if(!StringUtils.isEmpty(ep_mall)){
				for(int i=0;i<list.size();i++){
					Map operMap = (Map) list.get(i);
					String v_ep_mall = Const.getStrValue(operMap, "ep_mall");
					if(ep_mall.equals(v_ep_mall)){
						map = operMap;
						break ;
					}
				}
			}
			else{
				map = (Map) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map == null)
			return null;
		ClientOperation operation2 =  new ClientOperation(map);
		
		cache.set(space, "INF_OPERATION"+operationCode+ep_mall, operation2,time);
		return operation2;
	}

	@Override
	public ClientEndPoint getEndPoint(String endpointId,String ep_mall) throws FrameException {
		
		ClientEndPoint endPoint = (ClientEndPoint)cache.get(space, "INF_ENDPOINT"+endpointId+ep_mall);
		if(endPoint !=null)
			return endPoint;
		return this.getEndPointFromDB(endpointId, ep_mall);
	}
	@Override
	public ClientEndPoint getEndPointFromDB(String endpointId,String ep_mall) throws FrameException {
		Map map = null;
		try {
			List list = this.baseDaoSupport.queryForList(SELECT_ENDPOINT, endpointId);
			if(!StringUtils.isEmpty(ep_mall)){
				for(int i=0;i<list.size();i++){
					Map end = (Map) list.get(i);
					String v_ep_mall = Const.getStrValue(end, "ep_mall");
					if(ep_mall.equals(v_ep_mall)){
						map = end;
						break ;
					}
				}
			}
			else{
				map = (Map) list.get(0);
			}
		} catch (Exception e) {

		}
		if (map == null)
			return null;
		ClientEndPoint endPoint2 =  new ClientEndPoint(map);
		cache.set(space, "INF_ENDPOINT"+endpointId+ep_mall, endPoint2,time);
		return endPoint2;
	}
	@Override
	public ClientRequest getRequest(String requestId) throws FrameException {
		
		ClientRequest request = (ClientRequest)cache.get(space, "INF_REQUEST"+requestId);
		if(request !=null)
			return request;
//		
		return this.getRequestFromDB(requestId);
	}
	@Override
	public ClientRequest getRequestFromDB(String requestId) throws FrameException {
		
		List maps = null;
		try {
			maps  = this.baseDaoSupport.queryForList(SELECT_REQUEST,new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					String req_id = rs.getString("req_id");
//					BLOB clob  = (BLOB) rs.getObject("req_tpl");
					String oper_method = rs.getString("oper_method");
					String qname = rs.getString("qname");
					String class_path = rs.getString("class_path");
					String qname_encode = rs.getString("qname_encode");
					String gvar_id = rs.getString("gvar_id");
					String oper_classname = rs.getString("oper_classname");
					String clobValue = "";
//					if(clob != null){
//						InputStream is = clob.getBinaryStream();// 得到流
//						InputStreamReader reader = new InputStreamReader(is);
//						BufferedReader br = new BufferedReader(reader);
//						String s;
//						try {
//							s = br.readLine();
//							StringBuffer sb = new StringBuffer();
//							while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
//							sb.append(s);
//							s = br.readLine();
//							}
//							clobValue = sb.toString();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					String clobValue = "";
//					if(clob != null){
//						InputStream is = clob.getBinaryStream();// 得到流
						Object obj = rs.getBlob("req_tpl");
						if(obj != null){
						    Class clazz = obj.getClass();
						    Method method;
							try {
								  method = clazz.getMethod("getBinaryStream", new Class[]{});
								  InputStream is = (InputStream)method.invoke(obj, new Object[]{});
								  InputStreamReader reader = new InputStreamReader(is,"GBK");
								  BufferedReader br = new BufferedReader(reader);
								  String s = br.readLine();
								  StringBuffer sb = new StringBuffer();
								  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
									sb.append(s);
								    s = br.readLine();
								   }
								  clobValue = sb.toString();
									
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
//					}
					Map map = new HashMap();
					map.put("req_id", req_id);
					map.put("req_tpl", clobValue.getBytes());
					map.put("oper_method", oper_method);
					map.put("qname", qname);
					map.put("class_path", class_path);
					map.put("qname_encode", qname_encode);
					map.put("gvar_id", gvar_id);
					map.put("oper_classname", oper_classname);
					return map;
				}
			}, requestId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ListUtil.isEmpty(maps))
			return null;
		Map map = (Map) maps.get(0);
		
		
		ClientRequest cl = new ClientRequest(map);
		cache.set(space, "INF_REQUEST"+requestId, cl,time);
		return cl;
	}

	@Override
	public ClientResponse getResponse(String responseId) throws FrameException {
		
		ClientResponse response = (ClientResponse)cache.get(space, "INF_RESPONSE"+responseId);
		if(response !=null)
			return response;
		return this.getResponseFromDB(responseId);
	}
	
	@Override
	public ClientResponse getResponseFromDB(String responseId) throws FrameException {	
		List maps = null;
		try {
//			map = this.baseDaoSupport.queryForMap(SELECT_RESPONSE, responseId);
			maps  = this.baseDaoSupport.queryForList(SELECT_RESPONSE,new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					String rsp_id = rs.getString("rsp_id");
//					BLOB clob  = (BLOB) rs.getObject("trans_tpl");
					String cdata_path = rs.getString("cdata_path");
					String xml_mapper = rs.getString("xml_mapper");
					String trans_fault = rs.getString("trans_fault");
					String rsp_classpath = rs.getString("rsp_classpath");
					String rsp_type = rs.getString("rsp_type");
					
					 
					String clobValue = "";
//					if(clob != null){
//						InputStream is = clob.getBinaryStream();// 得到流
						Object obj = rs.getBlob("trans_tpl");
						if(obj !=null){
						    Class clazz = obj.getClass();
						    Method method;
							try {
								  method = clazz.getMethod("getBinaryStream", new Class[]{});
								  InputStream is = (InputStream)method.invoke(obj, new Object[]{});
								  InputStreamReader reader = new InputStreamReader(is,"GBK");
								  BufferedReader br = new BufferedReader(reader);
								  String s = br.readLine();
								  StringBuffer sb = new StringBuffer();
								  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
									sb.append(s);
								    s = br.readLine();
								   }
								  clobValue = sb.toString();
									
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
//					}
					
					   
					      
					
					Map map = new HashMap();
					map.put("rsp_id", rsp_id);
					map.put("trans_tpl", clobValue.getBytes());
					map.put("cdata_path", cdata_path);
					map.put("xml_mapper", xml_mapper);
					map.put("trans_fault", trans_fault);
					map.put("rsp_classpath", rsp_classpath);
					map.put("rsp_type", rsp_type);
					return map;
				}
			}, responseId);

		} catch (Exception e) {

		}
		if(ListUtil.isEmpty(maps))
			return null;
		Map map = (Map) maps.get(0);
		ClientResponse cl = new ClientResponse(map);
		
		cache.set(space, "INF_RESPONSE"+responseId, cl,time);
		
		
		return cl;
	}

	@Override
	public ClientRequestUser getRequestUser(String userId)
			throws FrameException {
		Map map = null;
		try {
			map = this.baseDaoSupport.queryForMap(SELECT_REQUEST_USER+" and ( source_from is null or source_from is not null) ", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (map == null)
			return new ClientRequestUser();
		return new ClientRequestUser(map);
	}

	@Override
	public void logCall(final List args) throws Exception {
		if (args == null || args.size() != 15) {
			// throw new IllegalArgumentException("参数为空，或参数的个数不符合要求");
		}
		String reqXml = (String) args.get(4);
		if (args.get(4) != null) {
			reqXml = args.get(4).toString();
		} else {
			reqXml = "";
		}
		final ByteArrayInputStream reqStream = new ByteArrayInputStream(
				reqXml.getBytes("GBK"));
		args.set(4, reqStream);

		String rsqXml = (String) args.get(5);
		if (args.get(5) != null) {
			rsqXml = args.get(5).toString();
		} else {
			rsqXml = "";
		}
		final ByteArrayInputStream rspStream = new ByteArrayInputStream(
				rsqXml.getBytes("GBK"));
		args.set(5, rspStream);

		if(!(args.size() == 16 && null != args.get(15))){
			String seq = new SeqUtil().getNextSequence("INF_COMM_CLIENT_CALLLOG", "LOG_ID");
			args.add(seq);
		}
		final int reqLength = reqXml.getBytes("GBK").length;
		final int rsqLength = rsqXml.getBytes("GBK").length;
		try {
			String writeClob = getConfigInfoValueByCfId("IS_WRITE_INF_CLOB");
			if("1".equals(writeClob)){//写接口日志大字段
				sqlExecNew.update(INSERT_CLIENT_CALLLOG,
						new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								int i = 0;
								ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
								ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
								ps.setString(++i, (String) args.get(i - 1));
								ps.setString(++i, (String) args.get(i - 1));
								ps.setBinaryStream(++i, reqStream, reqLength);
								ps.setBinaryStream(++i, rspStream, rsqLength);
								ps.setString(++i, (String) args.get(i - 1));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, StringUtils.substr(
										(String) args.get(i - 1), 2000));
								ps.setString(++i, (String) args.get(i - 1));
							}
						});
			}else{
//				List param = new ArrayList();
//				for(int i=0;i<args.size();i++){
//					if(i!=4&&i!=5){
//						param.add(args.get(i));
//					}
//				}
				//this.baseDaoSupport.execute(INSERT_CLIENT_CALLLOG_NO_CLOB, param);
					sqlExecNew.update(INSERT_CLIENT_CALLLOG_NO_CLOB,
							new PreparedStatementSetter() {
								@Override
								public void setValues(PreparedStatement ps)
										throws SQLException {
									int i = 0;
									ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
									ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
									ps.setString(++i, (String) args.get(i - 1));
									ps.setString(++i, (String) args.get(i - 1));
									ps.setString(++i, (String) args.get(i + 1));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, StringUtils.substr(
											(String) args.get(i + 1), 2000));
									ps.setString(++i, (String) args.get(i + 1));
								}
							});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//写入文件系统
		try{
			Timestamp t = (Timestamp) args.get(0); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
			
			final String busiTime = sdf.format(new Date(t.getTime()));
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
			final String index = "inf_comm_client_calllog_idx"+sdf1.format(new Date(t.getTime()));
			
			final String op_code = (String) args.get(2);
			final String in_param =reqXml;
			final String out_param = rsqXml;
			final String log_id = (String) args.get(15);
			final String orderId = (String) args.get(12);
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					//文件写入
					EsearchAddReq req = new EsearchAddReq();
					ESearchData es = new ESearchData();
					es.setIndex(index);
					es.setType("inf_comm_client_calllog");
					es.setLog_id(log_id);
					es.setObj_id(orderId);
					es.setIn_param(in_param);
					es.setOut_param(out_param);
					es.setOperation_code(op_code);
					es.setBusi_time(busiTime);
					es.setCreate_time(busiTime);
					req.setEsData(es);
					
					ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
					EsearchAddResp resp = client.execute(req, EsearchAddResp.class);
					if(ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
						logger.info("logid:"+log_id+",插入文件系统成功!");
					}else{
						logger.info("logid:"+log_id+",插入文件系统失败!");
					}
					
					
				}
			});
			thread.setName("LOGID"+log_id+",文件系统线程写入!");
			thread.start();
			
			//异常写入
			if(StringUtils.isEmpty(out_param))
				return;
			String flag = getConfigInfoValueByCfId("IS_EXCEPTION_RUN");
			if(StringUtils.equals(flag, "1")){
				
				//异常能力写入 ：接口异常api
			    OrderExpWriteForInfReq writeForInfReq = new OrderExpWriteForInfReq();
			    writeForInfReq.setLog_id(log_id); //报文日志id
			    writeForInfReq.setObj_id(orderId); //对象id
			    writeForInfReq.setObj_type("order"); //对象类型（order、order_queue）
			    writeForInfReq.setSearch_code(op_code);
			    writeForInfReq.setOut_param(out_param);
			    writeForInfReq.setIn_param(in_param);
			    
			    if(ConstsCore.DECOUPLING_EXEC_D.equals(MqEnvGroupConfigSetting.ORD_EXP_EXEC)){
			    	logCallWriteExpByDubbo(writeForInfReq);
			    }else{
			    	logCallWriteExpByMq(writeForInfReq);
			    }
			    
//			    OrderExpWriteResp writeForInfResp = client.execute(writeForInfReq, OrderExpWriteResp.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private ZteResponse logCallWriteExpByMq(OrderExpWriteForInfReq writeForInfReq){
		if(null == orderQueueBaseManager){
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
		req.setService_code(writeForInfReq.getApiMethodName());
		req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		req.setZteRequest((ZteRequest)writeForInfReq);
		req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		return orderQueueBaseManager.asynExecuteMsgWriteMq(req);
	}
	
	private void logCallWriteExpByDubbo(OrderExpWriteForInfReq writeForInfReq){
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		 OrderExpWriteResp writeForInfResp = client.execute(writeForInfReq, OrderExpWriteResp.class);
	}
	
	/**
	 * 
	 * @Description: 异常系统开关控制
	 * @param cfId
	 * @return   
	 */
	public String getConfigInfoValueByCfId(String cfId){
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		return cacheUtil.getConfigInfo(cfId);
	}
	
	@Override
	public void updateCallLog(final Map<String,String> orderMap,final Object o,final String log_id, final String rsp_xml){
		try{
			final ByteArrayInputStream rspStream = new ByteArrayInputStream(
					rsp_xml.getBytes("GBK"));
			int rsqLength = rsp_xml.getBytes("GBK").length;
			if(StringUtils.isEmpty(rsp_xml))
				rsqLength =0;
			final int rsqLengthp =rsqLength;
			String writeClob = getConfigInfoValueByCfId("IS_WRITE_INF_CLOB");
			if("1".equals(writeClob)){
				sqlExecNew.update(UPDATE_INF_CALL_LOG,
						new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								int i = 0;
								ps.setString(++i, StringUtils.substr(rsp_xml, 2000));
								ps.setBinaryStream(++i, rspStream, rsqLengthp);
								ps.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
								ps.setString(++i, log_id);
							}
						});
			}else{
				sqlExecNew.update(UPDATE_INF_CALL_LOG_NO_CLOB,
						new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								int i = 0;
								ps.setString(++i, StringUtils.substr(rsp_xml, 2000));
								ps.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
								ps.setString(++i, log_id);
							}
						});
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//写入文件系统
				try{
					
					if(StringUtils.isEmpty(rsp_xml))
						return;
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							//文件写入
							EsearchUpdateReq req = new EsearchUpdateReq();
							ESearchData esData = new ESearchData();
							esData.setLog_id(log_id);
							esData.setOut_param(rsp_xml);
							
							req.setEsData(esData);
							req.setUpdateDelay(true);
							req.setUpdateNull(false);
							ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
							EsearchUpdateResp resp = client.execute(req, EsearchUpdateResp.class);
							if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
								logger.info("logid:" + log_id + ",更新出参成功!");
							} else {
								logger.info("logid:" + log_id + ",更新出参失败!");
							}
							
						}
					});
					thread.setName("LOGID" + log_id + ",文件系统线程更新出参!");
					thread.start();
					
					//异常写入
					String flag = getConfigInfoValueByCfId("IS_EXCEPTION_RUN");
					if(StringUtils.equals(flag, "1")){
//						ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
						//异常能力写入 ：接口异常api
					    OrderExpWriteForInfReq writeForInfReq = new OrderExpWriteForInfReq();
					    writeForInfReq.setLog_id(log_id); //报文日志id
					    String orderId = orderMap.get("orderId"); 
					    String opCode = orderMap.get("opCode");
					    writeForInfReq.setObj_id(orderId); //对象id
					    writeForInfReq.setObj_type("order"); //对象类型（order、order_queue）
					    writeForInfReq.setSearch_code(opCode);
					    writeForInfReq.setOut_param(rsp_xml);
					    writeForInfReq.setIn_param(CommonTools.beanToJsonNCls(o));
					    //执行mq异步调用. 写mq失败则转为dubbo调用
					    if(ConstsCore.DECOUPLING_EXEC_D.equals(MqEnvGroupConfigSetting.ORD_EXP_EXEC)){
					    	updateCallLogWriteExpByDubbo(writeForInfReq);
					    }else{
					    	updateCallLogWriteExpByMq(writeForInfReq);
					    }
					    
//					    OrderExpWriteResp writeForInfResp = client.execute(writeForInfReq, OrderExpWriteResp.class);
					}
				}catch(Exception e){
					e.printStackTrace();
				}


	}

	private void updateCallLogWriteExpByDubbo(OrderExpWriteForInfReq writeForInfReq) {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		OrderExpWriteResp writeForInfResp = client.execute(writeForInfReq, OrderExpWriteResp.class);
	}

	private ZteResponse updateCallLogWriteExpByMq(OrderExpWriteForInfReq writeForInfReq) {
		if(null == orderQueueBaseManager){
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
		req.setService_code(writeForInfReq.getApiMethodName());
		req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		req.setZteRequest((ZteRequest)writeForInfReq);
		req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		return orderQueueBaseManager.asynExecuteMsgWriteMq(req);
	}
	
	@Override
	public ClientGlobalVar getGlobalVars(String globalVarsId) {

		if (StringUtils.isEmpty(globalVarsId)
				|| StringUtils.isEmpty(globalVarsId.trim()))
			return null;
		Map map = null;
		try {
			map = this.baseDaoSupport.queryForMap(SELECT_GLOBAL_VARS, globalVarsId);
		} catch (Exception e) {

		}
		if (map == null)
			return null;
		return new ClientGlobalVar(map);
	}

	@Override
	public void logINF_CRM_SPS_LOG(final List args) throws Exception {

		String content = (String) args.get(4);
		if (content != null && !"".endsWith(content)) {
			InputStream xml = new ByteArrayInputStream(content.getBytes("GBK"));
			args.add(4, xml);
		}
		try {
			sqlExecNew.update(INSERT_INF_CRM_SPS_LOG,
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							int i = 0;
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							InputStream content = (InputStream) args.get(i);
							int islen;
							try {
								if (content != null) {
									islen = content.available();
								} else {
									islen = 0;
								}

							} catch (IOException e) {
								throw new RuntimeException(e.getMessage());
							}
							ps.setBinaryStream(++i, content, islen);
						}
					});
		} catch (Exception e) {

		}
	}

	/**
	 * 取得大字段信息
	 * 
	 * @param queryCode
	 * @return
	 */
	/*private String getBlob(String strSql, String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.INF_DATASOURCE);

			stmt = conn.prepareStatement(strSql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				java.sql.Blob blob = rs.getBlob(1);
				if (blob != null) {
					byte[] bdata = blob.getBytes(1, (int) blob.length());
					String queryContent = new String(bdata);
					return queryContent;
				}
			}

			return "";
		} catch (Exception se) {

			throw new DAOSystemException("SQLException while getting "
					+ "blob:\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}*/
	
	@Override
	public List getRemoteServiceList(){
		List list = null;
		try {
			list = this.baseDaoSupport.queryForList(SELECT_REMOTE_SERVICE, new String[]{});
		} catch (Exception e) {

		}
		return list;
	}
	@Override
	public List queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public Map queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.baseDaoSupport.queryForMap(sql);
	}
	@Override
	public String queryForString(String sql){
		return this.baseDaoSupport.queryForString(sql);
	}
	@Override
	public String queryForString(String sql,String[] strs){
		return this.baseDaoSupport.queryForString(sql, strs);
	}
}
