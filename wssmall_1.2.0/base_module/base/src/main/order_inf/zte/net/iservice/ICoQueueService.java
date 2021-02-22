package zte.net.iservice;

import java.util.List;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.req.CoQueueModifyReq;
import params.coqueue.resp.CoQueueAddResp;
import params.coqueue.resp.CoQueueModifyResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.mall.core.model.CoQueue;


@ZteSoftCommentAnnotation(type="class",desc="消息队列API",summary="消息队列API")
public interface ICoQueueService {

	
	@ZteSoftCommentAnnotation(type="method", desc="写入一条消息", summary="写入一条消息")
    public CoQueueAddResp add(CoQueueAddReq coQueueAddReq);
	
	/**
	 * 更新状态
	 * @param co_id
	 * @param resp_code
	 * @param resp_msg
	 */
	public void modifyStatus(String co_id, String resp_code, String resp_msg);
	
	/**
	 * 修改状态
	 * @param co_id  消息队列标识
	 * @param status_new  新状态
	 * @param status_old  旧状态
	 * @param desc  描述信息
	 */
	public void modifyStatus(String co_id, String status_new, String status_old, String desc);
	
	/**
	 * 删除记录
	 * @param co_id
	 */
	public void delete(String co_id);
	
	/**
	 * 定时任务扫描
	 * 只取状态为未发送【WFS】和响应失败【XYSB】（失败次数小于4）
	 * @param service_code
	 * @param max_count
	 * @return
	 */
	public List<CoQueue> getForJob(String service_code, int max_count);
	
	/**
	 * 上锁（扫描后需要更新状态为发送中【FSZ】）
	 * @param co_id
	 */
	public void lock(String co_id);
	
	
	public CoQueueModifyResp modifyStatusToWFS(CoQueueModifyReq req);
	
	public CoQueue get(String co_id);
	
}
