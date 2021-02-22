package com.ztesoft.net.mall.core.timer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.nd.req.OrderResultNotifyReq;
import zte.net.ecsord.params.nd.resp.OrderResultNotifyRsp;
import zte.net.ecsord.params.nd.resp.WechatRetBean;
import zte.net.ecsord.params.nd.vo.OrderResultNotifyVo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 异步通知接口调用失败处理定时器
 * @author zhaoc
 *
 */
public class OrderResultNotifyTimer {

	private Logger log = Logger.getLogger(this.getClass());

	public void doTask() throws Exception{
		try {
        	if("N".equals(EopSetting.DB_CONNECT)){
    			return;
    		}
        	
    		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "doTask")) {
      			return;
    		}
    		
    		//查询天数
    		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    		String days = cacheUtil.getConfigInfo("ORDER_RESULT_NOTIFY_DAYS");
    		if(StringUtils.isBlank(days))
    			days = "1";
    		
    		String notify_count = cacheUtil.getConfigInfo("ORDER_RESULT_NOTIFY_COUNT");
    		
    		if(StringUtils.isBlank(notify_count))
    			notify_count = "3";
            
    		//查询在支付环节的订单
    		String sql = "SELECT * FROM es_order_result_notify a " + 
    				"WHERE a.create_date>(sysdate-1) and a.notify_status=0 " + 
    				"and a.notify_count<" + notify_count;
    		
    		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
    		log.info("OrderResultNotifyTimer query sql :"+sql);
    		List<OrderResultNotifyVo> notifyFailList = support.queryForList(sql,OrderResultNotifyVo.class);
    		
    		if(null != notifyFailList && notifyFailList.size() > 0){
    			
    			for (OrderResultNotifyVo vo : notifyFailList) {
    				String errorMsg = "";
    				
    				try{
    					OrderResultNotifyReq req = new OrderResultNotifyReq();
        				
        				//设置参数
        				req.setOrder_id(vo.getOrder_id());
        				req.setOut_order_id(vo.getOut_id());
        				req.setResult_code(vo.getResult_code());
        				req.setResult_msg(vo.getResult_msg());
        				//取异步通知接口的服务名
        				req.setNotify_method(vo.getNotify_method());
        				
        				//访问的方法ZteInfOpenService--doOrderResultNotify（wssmall.order.result.notify）
        				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        				OrderResultNotifyRsp rsp = client.execute(req, OrderResultNotifyRsp.class);
        				
        				//返回信息处理
        				if(rsp == null){
        					errorMsg = "调用接口异常:能力开放平台返回的信息为空";
        				}else if(!"200".equals(rsp.getError_code())){
        					//访问能力开发平台失败
        					errorMsg = "访问能力开发平台异常:" + rsp.getError_msg();
        				}else if(!"00000".equals(rsp.getRes_code())){
        					//能力开发平台返回失败
        					errorMsg = "能力开发平台异常:" + rsp.getRes_message();
        				}else{
        					WechatRetBean webchatBean = rsp.getResultMsg();
        					
        					if(null == webchatBean){
        						//微信公众号平台返回信息为空
        						errorMsg = "调用接口异常:微信公众号平台返回的信息为空";
        					}else if(!"0".equals(webchatBean.getResp_code())){
        						//微信公众号平台返回失败
        						errorMsg = "外围平台返回：" + webchatBean.getResp_msg();	
        					}
        				}
    				}catch(Exception e){
    					errorMsg = "OrderResultNotifyTimer定时器，调用接口失败:"+e.getMessage();
    				}
    				
    				if("0".equals(vo.getResult_code()) && StringUtils.isBlank(errorMsg)){
    					//原先业务办理成功但是通知失败的订单，会卡在订单归档环节的通知微信公众号规则
    					//业务办理成功且接口调用成功的，执行规则，使订单继续流转
    					
    					//规则编号--业务不同，规则不同
    					//根据商品类型判断
    					String type_id = CommonDataFactory.getInstance().
    							getGoodSpec(vo.getOrder_id(), "", SpecConsts.TYPE_ID);
    					
    					String rules = cacheUtil.getConfigInfo("ORDER_RESULT_NOTIFY_RULES");
    					
    					if(StringUtils.isNotBlank(rules)){
    						String[] ruleArr = rules.split(",");
    						
    						for(String cfg : ruleArr){
    							
    							String[] cfgArr = cfg.split("\\|");
    							
    							if(cfgArr!=null && cfgArr.length>1){
		    						String cfgType = cfgArr[0];
		    						String cfgRule = cfgArr[1];
		    						
		    						try{
	    								RuleTreeExeReq requ = new RuleTreeExeReq();
	    		    					TacheFact fact = new TacheFact();
	    		    						
    		    						if(cfgType.equals(type_id)){
    		    							fact.setOrder_id(vo.getOrder_id());
    	    		    					
    	    		    					requ.setRule_id(cfgRule);
    	    		    					requ.setFact(fact);
    	    		    					requ.setCheckAllRelyOnRule(true);
    	    		    					requ.setCheckCurrRelyOnRule(true);
    	    		    					
    	    		    					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
    	    		    					
    	    		    					//执行规则
    	    		    					client.execute(requ, RuleTreeExeResp.class);
    	    		    					
    	    		    					break;
    		    						}
	    							}catch(Exception error){
	    								log.info("OrderResultNotifyTimer定时器处理" + vo.getOrder_id() +
	    										"订单执行" + cfgRule + "规则失败：", error);
	    							}
    							}
    						}
    					}
    				}
    			}
    		}
        } catch (Exception e) {
            log.info("OrderResultNotifyTimer执行出错", e);
            e.printStackTrace();
        }
	}
}
