package zte.service;

import java.util.List;

import javax.annotation.Resource;

import services.MemberAddressInf;
import services.ServiceBase;
import zte.net.iservice.IMemberAddressService;
import zte.params.addr.req.MemberAddressAddReq;
import zte.params.addr.req.MemberAddressDeleteReq;
import zte.params.addr.req.MemberAddressEditReq;
import zte.params.addr.req.MemberAddressGetReq;
import zte.params.addr.req.MemberAddressListReq;
import zte.params.addr.resp.MemberAddressAddResp;
import zte.params.addr.resp.MemberAddressDeleteResp;
import zte.params.addr.resp.MemberAddressEditResp;
import zte.params.addr.resp.MemberAddressGetResp;
import zte.params.addr.resp.MemberAddressListResp;

import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.mall.core.service.IMemberAddressManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class MemberAddressService extends ServiceBase implements
		IMemberAddressService {

	@Resource
	private IMemberAddressManager memberAddressManager;
	@Resource
	private MemberAddressInf memberAddressServ;

	@Override
	public MemberAddressListResp listMemberAddress(MemberAddressListReq req) {
		List<MemberAddress> list = memberAddressManager.listMemberAddress(req
				.getMember_id());
		MemberAddressListResp resp = new MemberAddressListResp();
		resp.setAddressList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddressGetResp getMemberAddress(MemberAddressGetReq req) {
		MemberAddress address = null;
		if (req.isGetLastAddress())
			address = memberAddressManager.getLastAddress();
		else
			address = memberAddressManager.getAddress(req.getAddress_id());
		MemberAddressGetResp resp = new MemberAddressGetResp();
		resp.setMemberAddress(address);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddressAddResp addMemeberAddress(MemberAddressAddReq req) {
		MemberAddressAddResp resp = new MemberAddressAddResp();
		memberAddressManager.addAddress(req.getMemberAddress());
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddressEditResp editMemberAddress(MemberAddressEditReq req) {
		memberAddressManager.updateAddress(req.getAddress());
		MemberAddressEditResp resp = new MemberAddressEditResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddressDeleteResp deleteMemberAddress(
			MemberAddressDeleteReq req) {
		memberAddressManager.deleteAddress(req.getAddress_id());
		MemberAddressDeleteResp resp = new MemberAddressDeleteResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	/*
	 * @Override public MemberAddressListResp listAddress(MemberAddressListReq
	 * req) {
	 * 
	 * @SuppressWarnings("unchecked") List<MemberAddress> list =
	 * memberAddressManager.listAddress(); MemberAddressListResp resp = new
	 * MemberAddressListResp(); resp.setAddressList(list);
	 * resp.setError_code("0"); resp.setError_msg("成功"); addReturn(req, resp);
	 * return resp; }
	 */

}
