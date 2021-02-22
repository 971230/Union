package services;

import java.util.List;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import rule.impl.AcceptBaseRule;
import rule.params.accept.req.AcceptRuleReq;
import rule.params.accept.resp.AcceptRuleResp;
import zte.net.iservice.ICoQueueService;
import zte.net.iservice.INumberService;
import zte.params.number.req.NumberChangeStatusReq;
import zte.params.number.resp.NumberChangeStatusResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ApiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.outter.inf.service.DBUtils;
import com.ztesoft.net.server.sendsms.SmsSend;

public class TaobaoAcceptRule extends AcceptBaseRule {

	@Override
	public AcceptRuleResp validateAcceptInst(AcceptRuleReq acceptRuleReq) {
		AcceptRuleResp resp = new AcceptRuleResp();
		resp.setFlag("yes");
		return resp;
	}

	@Override
	public AcceptRuleResp afSaveAcceptInst(AcceptRuleReq acceptRuleReq)
			throws ApiBusiException {
		AcceptRuleResp resp = new AcceptRuleResp();
		if(acceptRuleReq==null || acceptRuleReq.getOattrInsts()==null) return resp;
		List<AttrInst> oattrInsts = acceptRuleReq.getOattrInsts();
		String order_from = acceptRuleReq.getOrder().getApp_key();
		try{
			for(AttrInst ai:oattrInsts){
				if(ai!=null && "phone_num".equals(ai.getField_name()) && !StringUtil.isEmpty(ai.getField_value())){
					String phone_no = ai.getField_value();

					if ( null != order_from && !JumpNoCheck(order_from)) {
						logger.info("############################order_from:" + order_from);
						//查询号码是否存在
						DBUtils dbUtils = SpringContextHolder.getBean("dbUtils");
						boolean flag = dbUtils.queryMsisdnExists(phone_no);
						if (!flag) {
							//发送告警短信
							SmsSend.sendMonitorSms(phone_no);
							logger.info("号码[" + phone_no +"]在商品系统不存在2,时间："+DateUtil.getTime2());
							throw new ApiException("号码[" + phone_no +"]在商品系统不存在");
						}
					}
					
					NumberChangeStatusReq req = new NumberChangeStatusReq();
					req.setNo(phone_no);
					req.setStatus_old("-999");
					req.setStatus_new("001");
					req.setOrder_id(acceptRuleReq.getOrder().getOrder_id());
					INumberService service = SpringContextHolder.getBean("numberService");
					NumberChangeStatusResp sresp = service.changeNumStatus(req);
					//ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					//client.execute(req, NumberChangeStatusResp.class);
					//写消息队列
					//server_code=CO_HAOMA   action_code = M
					CoQueueAddReq no_req = new CoQueueAddReq();
					no_req.setCo_name("号码同步");
					no_req.setBatch_id(phone_no);
					no_req.setService_code("CO_HAOMA");//CO_HAOMA
					no_req.setAction_code("M");
					no_req.setObject_type("HAOMA");
					no_req.setObject_id(phone_no);
					no_req.setOper_id("-1");
					ICoQueueService coService = SpringContextHolder.getBean("coQueueService");
					CoQueueAddResp no_resp = coService.add(no_req);
					break ;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resp;
	}

	private boolean JumpNoCheck(String order_from) {
		// 查询商城是否进行号码校验
		String sql="select count(*) from es_mall_config  where field_name='jumpnocheck' and field_value=?";
		int count=this.baseDaoSupport.queryForInt(sql, order_from);
		if(count>0){
			return true;	
		}else{
			return false;
		}
	}

}
