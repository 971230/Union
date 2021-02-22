package services;

import java.util.List;

import params.member.req.MemberLvByIdReq;
import params.member.req.MemberLvByLvIdReq;
import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvByIdResp;
import params.member.resp.MemberLvByLvIdResp;
import params.member.resp.MemberLvListResp;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.mall.core.service.IMemberLvManager;

public class MemberLvServ extends ServiceBase implements MemberLvInf{
	
	private IMemberLvManager memberLvManager;

	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}

	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

	@Override
	public MemberLvByIdResp getMemberLvById(MemberLvByIdReq mlReq) {
		MemberLvByIdResp memberLvResp = new MemberLvByIdResp();
		MemberLv lv = this.memberLvManager.get(mlReq.getMember_lv_id());
		memberLvResp.setMemberLv(lv);
		memberLvResp.setError_code("0");
		memberLvResp.setError_msg("成功");
		addReturn(mlReq, memberLvResp);
		return memberLvResp;
	}

	@Override
	public MemberLvByLvIdResp getMemberLvByLvId(MemberLvByLvIdReq req) {
		
		MemberLvByLvIdResp resp = new MemberLvByLvIdResp();
		MemberLv lv = memberLvManager.get(req.getLv_id());
		
		resp.setMemberLv(lv);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLvListResp getMemberLvList(MemberLvListReq req) {
		MemberLvListResp resp = new MemberLvListResp();
		 
		List<MemberLv> list = null;
		if (req.getMember_lv_ids() != null && req.getGoods_id()==null)
			list = this.memberLvManager.list(req.getMember_lv_ids());
		if(req.getMember_lv_ids() == null && req.getGoods_id()==null) 
			list = this.memberLvManager.list();
		if(req.getMember_lv_ids()!=null && req.getGoods_id() != null)
			list = this.memberLvManager.listByLv_idsAndGoods_id(req.getMember_lv_ids(), req.getGoods_id());
		resp.setMemberLvs(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	
}
