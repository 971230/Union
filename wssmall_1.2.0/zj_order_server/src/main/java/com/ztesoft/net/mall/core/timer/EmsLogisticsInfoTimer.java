package com.ztesoft.net.mall.core.timer;
    
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.iservice.impl.ZteInfOpenService;
import zte.params.ecsord.req.EMSLogisticsInfoReq;
import zte.params.ecsord.resp.EMSLogisticsInfoResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
@SuppressWarnings("all")
public class EmsLogisticsInfoTimer {
	private static Logger logger = Logger.getLogger(EmsLogisticsInfoTimer.class);
	private IEcsOrdManager ecsOrdManager;
	private ZteInfOpenService zteInfOpenService;
	ZteClient  client =null;
	EMSLogisticsInfoReq req=null;
	private static final int maxNum = 500;  //每次扫描500条
	int pageNo=1;
	int pageSize=1000;//每小时查询的条数
	public void run() {
		//定时任务IP地址限制
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
	
		//多线程开启
		ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, 500);
				
		try{
			client=ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
			Page orderPage=ecsOrdManager.emsOrderInfoQuery(pageNo, pageSize);
			List orderList=(List) orderPage.getData();
			if(orderList.size()==0){
				pageNo=1;
				return;
			}
			Map map =new HashMap();
			String order_id=null;
			List list=new ArrayList();
			for(int i=0;i<orderList.size();i++){
				 map=(Map) orderList.get(i);
				 order_id=map.get("order_id").toString();
				 logger.info(order_id);
				 req=new EMSLogisticsInfoReq();
				 req.setNotNeedReqStrOrderId(order_id);
				 list.add(req);
			}
			req.setList(list);
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
				@Override
				public ZteResponse execute(ZteRequest zteRequest) {
					try {
						logger.info("进来了");
						String asad=req.getNotNeedReqStrOrderId();
						EMSLogisticsInfoResp resp = client.execute(req, EMSLogisticsInfoResp.class);
					} catch (Exception e) {
						e.printStackTrace();
					}	
					return new ZteResponse();
				}
			});
			
			//放入任务池
			ThreadPoolFactory.submit(taskThreadPool, executor);  
			pageNo++;
			logger.info(pageNo);
	}catch(Exception e){
		e.printStackTrace();
		}
	}
}