package zte.net.iservice;

import java.util.Map;

import zte.params.req.JobTaskCheckedReq;
import zte.params.req.RefreshJobTaskCacheReq;
import zte.params.resp.JobTaskCheckedResp;
import zte.params.resp.RefreshJobTaskCacheResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="定时任务管理API",summary="定时任务管理API[查询可执行定时任务、检定时任务是否可执行]")
public interface IJobTaskService {

	/**
	 * 查询可执行的定时任务配置信息
	 * @作者 MoChunrun
	 * @创建日期 2014-4-21 
	 * @param req
	 * @return
	 */
	//@ZteSoftCommentAnnotation(type="method",desc="查询可执行的定时任务配置信息",summary="查询可执行的定时任务配置信息")
	//public JobTaskQueryResp queryJobTask(JobTaskQueryReq req);
	
	/**
	 * 检查定时任务是否可执行，并记录日志
	 * @作者 MoChunrun
	 * @创建日期 2014-4-21 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="检查定时任务是否可执行",summary="检查定时任务是否可执行，并记录日志")
	public JobTaskCheckedResp checkedJobTask(JobTaskCheckedReq req);
	
	/**
	 * 刷定时任务缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-5-29 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="刷定时任务缓存",summary="刷定时任务缓存")
	public RefreshJobTaskCacheResp refreshJobTaskCache(RefreshJobTaskCacheReq req);
	
	public Map checkedTaobaoJob(String server_ip,String server_port);
}
