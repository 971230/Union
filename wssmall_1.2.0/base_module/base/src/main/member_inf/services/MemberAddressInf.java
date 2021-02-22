package services;

import params.member.req.MemberAddressReq;
import params.member.resp.MemberAddressQryResp;
import params.order.req.ShowOrderReq;


public interface MemberAddressInf {
	
	
	
	/**
	 * 得到当前登录用户对应memberaddress
	 * 
	 * */
	public MemberAddressQryResp getMemberAddressList(ShowOrderReq cp);
	
	/**
	 * 根据address_id得到MemberAddress
	 * 
	 * */
	public MemberAddressQryResp getAddressById(MemberAddressReq req);

}
