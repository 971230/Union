package com.ztesoft.api.mq;


import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteRequest;

import com.ztesoft.mq.client.EventMessageExt;
import com.ztesoft.mq.client.EventSingleListenHandler;
import com.ztesoft.mq.client.exception.EventHandleException;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import consts.ConstsCore;

/**
 * @Description 通用消费者-消费主题：electron_topic
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */

public class MqReceiveElectron implements EventSingleListenHandler {
	
	private static Logger logger = Logger.getLogger(MqReceiveElectron.class);
	
	@Resource
	private ExcuteReceiveMsg excuteReceiveMsg;
	private ICacheUtil cacheUtil;
	private void init(){
		if(cacheUtil ==null)
			cacheUtil = SpringContextHolder.getBean("cacheUtil");
	}
	
	
	@Override
	public void executeEvent(EventMessageExt e) throws EventHandleException {
		//logger.info("消费的消息:" + (ZteRequest)(e.getDeSerializeData())+e.toString());
		//logger.info("electron_topic消费者接收一条消息" );
		//接收对象，请求相应的api
		ExcuteReceiveMsg erm=new ExcuteReceiveMsg();
//		this.reqApiByDubbo(e);
	} 
	
	private void reqApiByDubbo(EventMessageExt msg){
		if(msg!=null&&msg.getDeSerializeData()!=null){ //add by wui消息消费失败，则插入队列，插入成功则历史归档
			try{
				this.init();
				String pressure_test_flag = cacheUtil.getConfigInfo("PRESSURE_TEST_FLAG");
				if(!ConstsCore.CONSTS_YES.equals(pressure_test_flag)){//压力测试开关，开：就不做标准化处理
					if(msg.getDeSerializeData()!=null){
						ZteRequest request=(ZteRequest) msg.getDeSerializeData();
						logger.info("[MQ消费者] 订单: ["+request.getBase_order_id()+"]  [MqReceiveElectron] 开始消费!!");
						long end2 = System.currentTimeMillis();
						if(excuteReceiveMsg ==null)
							excuteReceiveMsg = SpringContextHolder.getBean("excuteReceiveMsg");
						if(request!=null){excuteReceiveMsg.executeDubbo(request);}
						long end3 = System.currentTimeMillis();
						logger.info("[MQ消费者] 订单: ["+request.getBase_order_id()+"] [MqReceiveElectron] 消息队列消费消息耗时 :"+(end3-end2));
					}
				}
			}catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

}
