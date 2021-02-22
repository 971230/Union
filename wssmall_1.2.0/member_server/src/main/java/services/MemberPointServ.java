package services;

import params.member.req.MemberPointAddReq;
import params.member.req.MemberPointCheckReq;
import params.member.req.MemberPointReq;
import params.member.resp.MemberPointAddResp;
import params.member.resp.MemberPointCheckResp;
import params.member.resp.MemberPointResp;

import com.ztesoft.net.mall.core.service.IMemberPointManger;

public class MemberPointServ extends ServiceBase implements MemberPointInf{
	
	private IMemberPointManger memberPointManger;

	public IMemberPointManger getMemberPointManger() {
		return memberPointManger;
	}

	public void setMemberPointManger(IMemberPointManger memberPointManger) {
		this.memberPointManger = memberPointManger;
	}

	@Override
	public MemberPointCheckResp checkIsOpen(MemberPointCheckReq mpReq) {
		
		MemberPointCheckResp resp = new MemberPointCheckResp();
		
		boolean flag = memberPointManger.checkIsOpen(mpReq.getType());
		resp.setResult(flag);
		if(flag){
			resp.setError_code("0");
			resp.setError_msg("成功");
		}else{
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		addReturn(mpReq, resp);
		return resp;
	}

	@Override
	public MemberPointResp getItemPointByName(MemberPointReq req) {

		MemberPointResp resp = new MemberPointResp();
		
		int pt = memberPointManger.getItemPoint(req.getType()+"_num");
		resp.setPoint(pt);
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPointAddResp addNewPointHistory(MemberPointAddReq req) {
		
		MemberPointAddResp resp = new MemberPointAddResp();
		
		memberPointManger.add(req.getMember_id(), req.getPoint(), req.getType(), req.getRelated_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		addReturn(req, resp);
		return resp;
	}
	
	
}
