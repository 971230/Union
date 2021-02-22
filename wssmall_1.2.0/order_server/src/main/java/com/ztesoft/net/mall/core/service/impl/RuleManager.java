package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import params.RuleParams;
import params.ZteError;
import params.ZteResponse;
import rule.AbstractRuleHander;
import services.OrderRuleInf;
import utils.CoreThreadLocalHolder;
import vo.Rule;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IRuleManager;
import commons.CommonTools;

/**
 * 订单管理规则处理器
 * @author wui
 */

@SuppressWarnings("unchecked")
public class RuleManager implements IRuleManager {
    private Logger logger = Logger.getLogger(RuleManager.class);

    /**
	 * 通用反射工具类
	 */
	@Override
    public ZteResponse process(RuleParams ruleParams) throws ApiBusiException {
    	OrderRuleInf orderRuleServ = SpringContextHolder.getBean("orderRuleServ");
    	List<Rule> rules  = orderRuleServ.getRulesByCond(ruleParams);
        ZteResponse obj = null;
        String s = "";
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
}
