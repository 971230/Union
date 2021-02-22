package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import params.ZteRequest;
import params.ZteResponse;
import rule.RuleInvoker;
import zte.params.req.MidDataProcessReq;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.mall.core.model.ImportMidData;
import com.ztesoft.net.mall.core.model.ImportTemplate;
import com.ztesoft.net.mall.core.service.IImportManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class ImportMidDataProcessTimer {
	private static int maxPoolSize = 500;
	private static int maxNumSize = 500;
	@Resource
	private IImportManager importManager;

	public void run() {
		
		//定时任务IP地址限制
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
		    return ;
		}
		
		String source_from = ManagerUtils.getSourceFrom();
		List<ImportTemplate> templates = this.importManager.listImportTemplates();
		for(int i=0;i<templates.size();i++){
			ImportTemplate template = templates.get(i);
			//多线程开启
			int thread_num = (template.getThread_num()==null || template.getThread_num()==0) ? maxPoolSize : template.getThread_num();
			int max_num = (template.getMax_num()==null || template.getMax_num()==0) ? maxNumSize : template.getMax_num();
			ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, maxPoolSize);
			
			try {
				//获取消息队列集
				MidDataProcessReq req = null;
				String template_id = template.getTemplate_id();
				String rule_java = template.getRule_java();
				List<ImportMidData> datas = this.importManager.listMidDatas(template_id, max_num);
				for (ImportMidData data : datas) {
					final String id = data.getId();
					final String json = data.getData_json();
					
					req = new MidDataProcessReq();
					req.setId(id);
					req.setLog_id(data.getLog_id());
					req.setJson(json);
					req.setSource_from(source_from);
					req.setExec_rule(rule_java);
					//并发控制
					if(!importManager.haveLocked(id)){
						importManager.lock(id);
						TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
							@Override
							public ZteResponse execute(ZteRequest zteRequest) {
								try {
									//调用规则
									RuleInvoker.importProcess((MidDataProcessReq)zteRequest);
								} catch (Exception e) {
									System.out.print("Exception  id = " + id);
									importManager.unLock(id);  //出异常解锁
									e.printStackTrace();
								}
								return new ZteResponse();
							}
						});
						//放入任务池
						ThreadPoolFactory.submit(taskThreadPool, executor);  
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//关闭
				ThreadPoolFactory.closeThreadPool(executor);
			}
		}
	}
	
}
