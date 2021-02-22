package services;

import java.util.List;

import params.login.req.LoginReq;
import params.login.resp.LoginResp;
import params.member.req.MemberAddReq;
import params.member.req.MemberByIdReq;
import params.member.req.MemberEditReq;
import params.member.req.MemberLevelListReq;
import params.member.req.MemberOrderIsBuyReq;
import params.member.req.MemberPasswordReq;
import params.member.req.MemberQryPageReq;
import params.member.req.MemberQryReq;
import params.member.req.PointConsumeReq;
import params.member.req.PointHistoryReq;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberByIdResp;
import params.member.resp.MemberEditResp;
import params.member.resp.MemberLevelListResp;
import params.member.resp.MemberOrderIsBuyResp;
import params.member.resp.MemberPasswordResp;
import params.member.resp.MemberQryResp;
import params.member.resp.PointConsumeResp;
import params.member.resp.PointHistoryResp;
import params.order.req.MemberOrderGiftReq;
import params.order.req.MemberOrderLogReq;
import params.order.resp.MemberOrderGiftResp;
import params.order.resp.MemberOrderLogResp;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberManager;

public class MemberServ extends ServiceBase implements MemberInf {

	private IMemberManager memberManager;
	private IMemberLvManager memberLvManager;

	public IMemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}

	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

	@Override
	public MemberQryResp qryMember(MemberQryReq req) {
		MemberQryResp resp = new MemberQryResp();

		Member member = null;
		if (req.getQry_con_type().equals(MemberQryReq.QRY_CON_TYPE.UNAME))
			member = memberManager.getMemberByUname(req.getUname());
		else if (req.getQry_con_type().equals(MemberQryReq.QRY_CON_TYPE.ID))
			member = memberManager.get(req.getMember_id());
		else 
			member = memberManager.checkEmail(req.getEmail());
		resp.setMember(member);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberQryResp qryMemberList(MemberQryPageReq req) {
		MemberQryResp resp = new MemberQryResp();

		Page memberPage = memberManager.queryMemberPage(req.getUname(),
				req.getPageNo(), req.getPageSize());
		resp.setMemberPage(memberPage);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLevelListResp qryMemberLvList(MemberLevelListReq req) {

		MemberLevelListResp resp = new MemberLevelListResp();

		List<MemberLv> list = memberLvManager.qryMemberLvList(req
				.getMember_id());
		resp.setMemberLvList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public LoginResp loginValid(LoginReq req) {
		LoginResp resp = new LoginResp();
		int ret = 0;
		if(!StringUtil.isEmpty(req.getUserName())){
			ret = memberManager.login(req.getUserSessionId(),req.getUserName(), req.getPwd());
		}else{
			ret = memberManager.loginByMobile(req.getUserSessionId(),req.getMobile(), req.getPwd());
		}
		if (ret == 1) {
			resp.setError_code("0");
			resp.setError_msg("成功");
		} else {
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberByIdResp getMemberById(MemberByIdReq req) {

		MemberByIdResp resp = new MemberByIdResp();

		Member memb = memberManager.get(req.getMember_id());

		resp.setMember(memb);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddResp addNewMember(MemberAddReq req) {

		MemberAddResp resp = new MemberAddResp();
		int ret = memberManager.add(req.getMember());
		if (ret > 0) {
			resp.setError_code("0");
			resp.setError_msg("新增成功");
			resp.setResult(true);
		} else {
			resp.setError_code("-1");
			resp.setError_msg("新增失败");
		}
		resp.setCount(ret);
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPasswordResp updateMemberPassword(MemberPasswordReq req) {

		MemberPasswordResp resp = new MemberPasswordResp();
		try {
			memberManager
					.updatePassword1(req.getMember_id(), req.getPassword());
			resp.setError_code("0");
			resp.setError_msg("修改密码成功");
			resp.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("修改密码失败");
			resp.setResult(false);
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberEditResp editMemberInfo(MemberEditReq req) {
		MemberEditResp resp = new MemberEditResp();
		try {
			memberManager.edit(req.getMember());

			resp.setError_code("0");
			resp.setError_msg("修改信息成功");
			resp.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("修改信息失败");
			resp.setResult(false);
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberOrderLogResp qryOrderLog(MemberOrderLogReq req) {
		// TODO Auto-generated method stub
		
		MemberOrderLogResp resp = new MemberOrderLogResp();
		List list = memberManager.listOrderLog(req.getOrder_id());
		resp.setLogList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberOrderGiftResp qryGiftByOrderId(MemberOrderGiftReq req) {
		// TODO Auto-generated method stub
		MemberOrderGiftResp resp = new MemberOrderGiftResp();
		List list = memberManager.listGiftByOrderId(req.getOrder_id());
		resp.setOrderGift(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	@Override
	public PointHistoryResp qryPointHistoryPage(PointHistoryReq req) {
		// TODO Auto-generated method stub
		PointHistoryResp resp = new PointHistoryResp();
		Page list = memberManager.queryPointHistoryPage(String.valueOf(req.getPointHistory().getPoint_type()),req.getPointHistory().getMember_id(),Integer.parseInt(req.getPageIndex()),Integer.parseInt(req.getPageSize()));
		resp.setPointHistoryPage(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public PointConsumeResp qryPointConsume(PointConsumeReq req) {
		// TODO Auto-generated method stub
		PointConsumeResp resp = new PointConsumeResp();
		String consumePoint = memberManager.queryConsumePoint(req.getPointType(),null);
		String gainedPoint = memberManager.queryGainPoint(req.getPointType(),null);
		resp.setConsumePoint(consumePoint);
		resp.setGainedPoint(gainedPoint);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberOrderIsBuyResp isBuy(MemberOrderIsBuyReq req) {
		// TODO Auto-generated method stub
		MemberOrderIsBuyResp resp = new MemberOrderIsBuyResp();
		boolean result = memberManager.isBuy(req.getGoods_id());
		resp.setResult(result);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
}
