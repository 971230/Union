package zte.net.service;

import params.req.ZbQueryOrderDetailReq;
import params.resp.ZbQueryOrderDetailResp;

public interface ICustomerOrderInquiryService {

	public ZbQueryOrderDetailResp queryOrderDetail(ZbQueryOrderDetailReq req);
	
}
