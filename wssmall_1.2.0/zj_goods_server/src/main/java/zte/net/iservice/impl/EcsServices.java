package zte.net.iservice.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import params.RuleParams;
import params.ZteError;
import params.ZteResponse;
import rule.AbstractRuleHander;
import rule.params.coqueue.req.CoQueueRuleReq;
import services.OrderRuleInf;
import services.ServiceBase;
import utils.CoreThreadLocalHolder;
import vo.Rule;
import zte.net.iservice.IEcsServices;
import zte.params.order.req.EcsTestReq;
import zte.params.order.req.SmsSendReq;
import zte.params.order.resp.SmsSendResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.config.ParamsConfig;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.taobao.TaobaoOrdStdService;
import com.ztesoft.net.rule.order.OrderStandardizing;
import com.ztesoft.net.server.sendsms.SmsSend;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import commons.CommonTools;

@ServiceMethodBean(version="1.0")
public class EcsServices extends ServiceBase implements IEcsServices{

	/**
	 * 通用反射工具类
	 */
	@Override
    public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException {
    	OrderRuleInf orderRuleServ = SpringContextHolder.getBean("orderRuleServ");
    	List<Rule> rules  = orderRuleServ.getRulesByCond(ruleParams);
        ZteResponse obj = null;
        String s = "";
        logger.info("EcsServices.processrule.rules.size() == " + rules.size());
//        logger.info(ruleParams.getUserSessionId()+"===================================wui");
        for (int i = 0; i < rules.size(); i++) {
            s = "";
            Rule rule = rules.get(i);
            AbstractRuleHander acceptBaseRule = null;
            try {
                try {
                	String ruleBean = rule.getRule_java() + CommonTools.getSourceForm();
                    acceptBaseRule = SpringContextHolder.getBean(ruleBean);
                } catch (NoSuchBeanDefinitionException e) {
                    acceptBaseRule = SpringContextHolder.getBean(rule.getRule_java());
                }
                s = ruleParams.getMethod_name() + "Perform"; //执行抽象类规则
                logger.debug("反射规则类:" + rule.toString() + ",方法:" + s);
                obj = (ZteResponse) MethodUtils.invokeMethod(acceptBaseRule, s, new Object[]{ruleParams.getZteRequest()});

                if ((i + 1) == rules.size()) {
                    return (null == obj) ? null : (ZteResponse) obj;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ZteError entity = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
                if (entity != null && Consts.EXP_BUSS.equals(entity.getType_code()))
                    throw new ApiBusiException(e.getMessage());
                else
                    throw new RuntimeException(e);
            }
        }
        return null;
    }
	
	@Override
	@ServiceMethod(method="zte.service.wssmall.ecs.ecstest",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse ecsTest(EcsTestReq test) throws ApiBusiException{
		OrderStandardizing orderStandardizing = SpringContextHolder.getBean("orderStandardizing");
		orderStandardizing.coQueue((CoQueueRuleReq)test.getCreq());
		return null;
		
	}

	@Override
	public SmsSendResp smsSend(SmsSendReq req) throws ApiBusiException {
		SmsSendResp resp = new SmsSendResp();
		String content = req.getSmsContent();
		if (!ParamsConfig.isDebug()){
			if(null == content || "".equals(content)){
				SmsSend.sendMonitorSms(req.getAcc_nbr());
			}else{
				resp.setBody(SmsSend.sendMonitorSms(req.getAcc_nbr(), req.getSmsContent()));
			}
		}
		return resp;
	}

	private TaobaoOrdStdService taobaoOrdStdService;
	public TaobaoOrdStdService getTaobaoOrdStdService(){
		return taobaoOrdStdService;
	}
	public void setTaobaoOrdStdService(TaobaoOrdStdService taobaoOrdStdService){
		this.taobaoOrdStdService = taobaoOrdStdService;
	}
	@Override
	public List<Outer> executeInfService(String req_content,Map param,String order_from)throws Exception{
		return taobaoOrdStdService.executeInfService(req_content, param, order_from);
	}
}
