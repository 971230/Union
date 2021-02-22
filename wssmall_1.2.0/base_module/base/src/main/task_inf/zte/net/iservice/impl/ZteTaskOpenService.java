package zte.net.iservice.impl;

import java.util.Map;

import zte.net.iservice.IJobTaskService;
import zte.params.req.JobTaskCheckedReq;
import zte.params.req.RefreshJobTaskCacheReq;
import zte.params.resp.JobTaskCheckedResp;
import zte.params.resp.RefreshJobTaskCacheResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteTaskOpenService implements IJobTaskService {

	private IJobTaskService jobTaskService;
	
    private void init() {
    	if (null == jobTaskService) jobTaskService = ApiContextHolder.getBean("jobTaskService");
    }
	
	/*@Override
	@ServiceMethod(method="zte.jobTaskService.task.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public JobTaskQueryResp queryJobTask(JobTaskQueryReq req) {
		this.init();
		req.setRopRequestContext(null);
		return jobTaskService.queryJobTask(req);
	}*/

	@Override
	@ServiceMethod(method="zte.jobTaskService.task.checked",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public JobTaskCheckedResp checkedJobTask(JobTaskCheckedReq req) {
		this.init();
		req.setRopRequestContext(null);
		return jobTaskService.checkedJobTask(req);
	}
	
	@Override
	@ServiceMethod(method="zte.taskService.task.cache.refresh",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RefreshJobTaskCacheResp refreshJobTaskCache(RefreshJobTaskCacheReq req) {
		this.init();
		return jobTaskService.refreshJobTaskCache(req);
	}
	
	@Override
	public Map checkedTaobaoJob(String server_ip,String server_port){
		this.init();
		return jobTaskService.checkedTaobaoJob(server_ip,server_port);
	}

	public IJobTaskService getJobTaskService() {
		this.init();
		return jobTaskService;
	}

	public void setJobTaskService(IJobTaskService jobTaskService) {
		this.init();
		this.jobTaskService = jobTaskService;
	}


}
