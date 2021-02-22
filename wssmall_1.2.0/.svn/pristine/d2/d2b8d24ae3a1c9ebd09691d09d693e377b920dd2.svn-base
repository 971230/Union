package zte.net.iservice;

import params.ZteResponse;
import zte.net.card.params.req.OutCardReq;
import zte.net.card.params.req.QueryQueueAlowCountReq;
import zte.net.card.params.req.QueryWriteCardStateReq;
import zte.net.card.params.req.ReadIccidReq;
import zte.net.card.params.req.WriteCardReq;
import zte.net.card.params.req.WriteQueueReq;
import zte.net.card.params.resp.QueryQueueAlowCountResp;
import zte.net.card.params.resp.QueryWriteCardStateResp;
import zte.net.card.params.resp.ReadIccidResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="自动写卡API",summary="自动写卡API")
public interface ICardService {
	//方案一接口
	@ZteSoftCommentAnnotation(type="method",desc="查询iccid",summary="查询iccid")
	public ReadIccidResp readIccid(ReadIccidReq  req);
	
	@ZteSoftCommentAnnotation(type="method",desc="吐卡、回收卡",summary="吐卡、回收卡")
	public ZteResponse outCard(OutCardReq  req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询iccid",summary="查询iccid")
	public QueryWriteCardStateResp queryWriteCardState(QueryWriteCardStateReq  req);
	
	//方案1/2共用
	@ZteSoftCommentAnnotation(type="method",desc="写卡",summary="写卡")
	public ZteResponse writeCard(WriteCardReq  req);
	

	
	
	//方案二接口
	@ZteSoftCommentAnnotation(type="method",desc="同步队列数据到写卡调度中心",summary="同步队列数据到写卡调度中心")
	public ZteResponse writeQueue(WriteQueueReq  req);
	
	
	@ZteSoftCommentAnnotation(type="method",desc="查询写卡队列允许插入数量",summary="查询写卡队列允许插入数量")
	public QueryQueueAlowCountResp queryQueueAlowCount(QueryQueueAlowCountReq  req);
	
}
