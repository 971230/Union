package com.ztesoft.net.util;

import zte.net.iservice.IJobTaskService;
import zte.params.req.JobTaskCheckedReq;
import zte.params.resp.JobTaskCheckedResp;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class TaskUtils {

	public static boolean checkJobTask(String className,String method){
		IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		JobTaskCheckedReq req = new JobTaskCheckedReq();
		req.setClassName(className);
		req.setMethod(method);
		JobTaskCheckedResp resp = jobTaskService.checkedJobTask(req);
		return resp.isCanRun();
	}
	
}
