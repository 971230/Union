package com.ztesoft.inf.communication.client;

import org.apache.log4j.Logger;
import org.phw.eop.api.EopClient;
import org.phw.eop.api.EopReq;
import org.phw.eop.api.EopRsp;

import params.ZteResponse;
import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.framework.dao.SeqUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * @author sguo
 * <p>
 * 		联通总商http接口，对端提供sdk包 提供对端的一些参数配置在inf_comm_client_request_user
 * </p>
 * 
 */
public class EOPSDKJsonInvoker extends Invoker {
	private static Logger logger = Logger.getLogger(EOPSDKJsonInvoker.class);
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		String serverUrl = endpoint;
		context.setEndpoint(endpoint);
		String appCode = reqUser.getUser_code();
		String signKey = reqUser.getUser_pwd();
		String action = context.getOperationCode();
		String param_name = reqUser.getUser_param();
		
		EopReq eopReq = new EopReq(action);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
//		param_value = "{\"activeNo\":\"EM20141026334293418\",\"orderId\":\"6532163018\",\"GoodsType\":\"ZHKL\",\"UpdateScope\":\"000000111000\",\"PostInfo\":null,\"DistributionInfo\":null,\"InvoiceInfo\":null,\"PayInfo\":null,\"DiscountInfo\":null,\"GiftInfo\":null,\"GoodsAttInfo\":[{\"CustomerName\":\"ZTE测试\",\"CertType\":\"HZB\",\"CertNum\":\"E834180245\",\"CustomerType\":\"GRKH\",\"CertLoseTime\":\"20501231235959\",\"CertAddr\":\"测试测试测试测试测试测试\",\"RefereeName\":\"\",\"RefereeNum\":\"\",\"DevelopCode\":\"5101158748\",\"DevelopName\":\"孙文娟\",\"PhoneNum\":\"18502086042\",\"ReliefPresFlag\":\"NO\",\"SaleMode\":\"FCPB\",\"SimId\":\"\",\"CardType\":\"NM\",\"ProductCode\":\"89002148\",\"ProductName\":\"4G全国组合套餐\",\"ProductNet\":\"4G\",\"ProductBrand\":\"4GPH\",\"FirstMonBillMode\":\"ALLM\",\"SerType\":\"BAK\",\"SubProdInfo\":[]}],\"FeeInfo\":[{\"FeeID\":\"1001\",\"FeeDes\":\"USIM卡费用\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"2001\",\"FeeDes\":\"号码预存费用\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"4001\",\"FeeDes\":\"多交预存款费用\",\"OrigFee\":50000,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":50000},{\"FeeID\":\"EMAL1002\",\"FeeDes\":\"商城侧卡费\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"99\",\"FeeDes\":\"多缴预存\",\"OrigFee\":50000,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":50000}],\"UserInfo\":{\"CustomerName\":\"ZTE测试\",\"CertType\":\"HZB\",\"CertNum\":\"E834180245\",\"CustomerType\":\"GRKH\",\"CertLoseTime\":\"20501231235959\",\"CertAddr\":\"测试测试测试测试测试测试\",\"RefereeName\":\"\",\"RefereeNum\":null,\"UserType\":\"NEW\"},\"DevelopInfo\":null,\"OperatorInfo\":null,\"FlowInfo\":null}";
		param_value = StringUtils.capitalized(param_value);
		eopReq.put(param_name, param_value);
		logger.info(param_value);
		context.setRequestTime(DateUtil.currentTime());
		context.setRequestString(param_value);
		EopRsp rsp = null;
		try{
			EopClient client = new EopClient(serverUrl, appCode, signKey);
			rsp = client.execute(eopReq); 
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("超时异常");
		}
		logger.info("EOPSDKJsonInvoker-----"+rsp);
		context.setResultString(rsp.toString());
		context.setResponeString(rsp.toString());
		context.setResponseTime(DateUtil.currentTime());
		Class<?> clazz = Class.forName(rspPath);
		String rspStr = rsp.getResult().toString();
		if(rspStr.contains("\"RespCode\":\"0000\"")){
			logColMap.put("col2", "success");
		}else{
//			recordDictLog((String)logColMap.get("order_id"),action,rspStr); // ZX 屏蔽 2015-11-13 17:39:21
			logColMap.put("col2", "error");
		}
		ZteResponse resp = (ZteResponse) JsonUtil.fromJson(rsp.getResult().toString(), clazz);
		return resp;

	}
	
	//记录异常日志
	public void recordDictLog(String order_id, String op_code, String rsp_xml){
		String seq = new SeqUtil().getNextSequence("INF_COMM_CLIENT_CALLLOG", "LOG_ID");
		this.logColMap.put("log_id", seq);
		MatchDictLogsReq req = new MatchDictLogsReq();
		req.setObj_id(order_id);
		req.setOp_code(op_code);
		req.setLocal_log_id(seq);
		req.setRsp_xml(rsp_xml);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		MatchDictLogsResp response=client.execute(req, MatchDictLogsResp.class);
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		String serverUrl = endpoint;
		context.setEndpoint(endpoint);
		String appCode = reqUser.getUser_code();
		String signKey = reqUser.getUser_pwd();
		String action = context.getOperationCode();
		String param_name = reqUser.getUser_param();
		EopClient client = new EopClient(serverUrl, appCode, signKey);
		EopReq eopReq = new EopReq(action);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		param_value = StringUtils.capitalized(param_value);
		eopReq.put(param_name, param_value);
		context.setRequestTime(DateUtil.currentTime());
		context.setRequestString(param_value);
		ZteResponse resp = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(rspPath);
			resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		context.setResultString(transform);
		context.setResponeString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}	

}
