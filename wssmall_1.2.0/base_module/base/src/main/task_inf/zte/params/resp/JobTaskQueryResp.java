package zte.params.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.JobTask;

public class JobTaskQueryResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="定时任务执行配置信息",type="Array",isNecessary="Y",desc="定时任务执行配置信息",hasChild=true)
	private JobTask jobTask;

	public JobTask getJobTask() {
		return jobTask;
	}

	public void setJobTask(JobTask jobTask) {
		this.jobTask = jobTask;
	}
	
}
