package services;

import params.ask.req.AskReq;
import params.ask.resp.AskResp;

public interface AskInf {

	/**
	 * 提问<br>
	 * 实现者要记录提问时间、提问人、相应站点名称及站点域名
	 * @param ask 实体中不包含上述信息， 只包含问题本身信息
	 */
	public void add(AskReq askReq);
	
	/**
	 * 读取一个问题的详细，包括其回复信息
	 * @param askid
	 * @return 回复列表数据存储在replyList属性中
	 */
	public AskResp get(AskReq askReq);
	
	
	
	/**
	 * 读取我的问题列表，此查询在作用域为当前用户的问题
	 * @param keyword 关键字，可搜索title和content，为空则不以此条件筛选
	 * @param startTime 开始时间条件,为空则不以此条件筛选
	 * @param endTime 结束时间,为空则不以此条件筛选
	 * @param pageNo  页码
	 * @param pageSize 页大小
	 * @return 
	 */
	public AskResp listMyAsk(AskReq askReq);

}
