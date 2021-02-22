package services;

import params.member.req.MemberAddressReq;
import params.member.resp.MemberAddressQryResp;
import params.order.req.ShowOrderReq;

import com.ztesoft.net.mall.core.service.IMemberAddressManager;

public class MemberAddressServ extends ServiceBase implements MemberAddressInf{
	
	private IMemberAddressManager memberAddressManager;
	
	@Override
	public MemberAddressQryResp getMemberAddressList(ShowOrderReq cp) {
		MemberAddressQryResp resp = new MemberAddressQryResp();
		resp.setMemberAddressList(memberAddressManager.listAddress());
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(cp, resp);
		return resp;
	}

	

	@Override
	public MemberAddressQryResp getAddressById(MemberAddressReq req) {
		
		MemberAddressQryResp resp = new MemberAddressQryResp();
		resp.setMemberAddress( memberAddressManager.getAddress(req.getAddress_id()));
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	
	public IMemberAddressManager getMemberAddressManager() {
		return memberAddressManager;
	}

	public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
		this.memberAddressManager = memberAddressManager;
	}
}
