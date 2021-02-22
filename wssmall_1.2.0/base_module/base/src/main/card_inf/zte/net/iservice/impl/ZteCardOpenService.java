package zte.net.iservice.impl;

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
import zte.net.iservice.ICardService;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;


@ServiceMethodBean(version="1.0")
public class ZteCardOpenService implements ICardService{
	
	private ICardService cardService;
	private void init() {
		if (null == cardService) cardService = ApiContextHolder.getBean("cardServices");
	}
	@Override
	@ServiceMethod(method="zte.net.card.readIccid",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ReadIccidResp readIccid(ReadIccidReq  req) {
		init();
		return cardService.readIccid(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.card.outCard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse outCard(OutCardReq  req) {
		init();
		return cardService.outCard(req);
	}
	@Override
	@ServiceMethod(method="zte.net.card.writeCardStateQuery",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryWriteCardStateResp queryWriteCardState(QueryWriteCardStateReq  req) {
		init();
		return cardService.queryWriteCardState(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.card.writeCard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse writeCard(WriteCardReq  req){
		init();
		return cardService.writeCard(req);
	}
	
	
	@Override
	@ServiceMethod(method="zte.net.card.writeQueue",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse writeQueue(WriteQueueReq req) {
		init();
		return cardService.writeQueue(req);
	}
	@Override
	@ServiceMethod(method="zte.net.card.queryQueueAlowCount",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryQueueAlowCountResp queryQueueAlowCount(QueryQueueAlowCountReq req) {
		init();
		return cardService.queryQueueAlowCount(req);
	}
	
}
