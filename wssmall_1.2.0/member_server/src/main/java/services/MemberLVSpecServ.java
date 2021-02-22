package services;

import java.util.List;
import java.util.Map;

import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLVSpecReq;
import params.member.req.MemberLvPriceReq;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLVSpecResp;
import params.member.resp.MemberLvPriceResp;

import com.ztesoft.net.mall.widget.memberlv.IMemberLVSpec;

public class MemberLVSpecServ extends ServiceBase implements MemberLVSpecInf {

	private IMemberLVSpec memberLVSpecManager;

	@Override
	public MemberLVCanBuyResp memberLvCanBuyGoods(MemberLVCanBuyReq req) {
		MemberLVCanBuyResp resp = new MemberLVCanBuyResp();

		boolean flag = memberLVSpecManager.memberLvCanBy(req.getProduct_id(),
				req.getMember_lv_id());
		resp.setResult(flag);
		if (resp.isResult()) {
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
	public MemberLvPriceResp getMemberLvPrice(MemberLvPriceReq req) {
		MemberLvPriceResp resp = new MemberLvPriceResp();
		Map map = memberLVSpecManager.getMemerLvPrice(req.getProduct_id(),
				req.getGoods_id(), req.getLvid());
		resp.setMemberLVSpecMap(map);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;

	}

	@Override
	public MemberLVSpecResp getMemberLVByIdAndGoodsId(MemberLVSpecReq req) {

		MemberLVSpecResp resp = new MemberLVSpecResp();
		List list = memberLVSpecManager.qryMLVbyIdsAndGoodsID(req.getLv_ids(),
				req.getGoods_id());
		resp.setMemberLvList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLVCanBuyResp isMemberLvCanByProduct(MemberLVCanBuyReq req) {
		MemberLVCanBuyResp resp = new MemberLVCanBuyResp();
		boolean flag = memberLVSpecManager.memberLvCanBy(req.getProduct_id(),
				req.getMember_lv_id());
		resp.setResult(flag);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;

	}
	
	@Override
	public MemberLVCanBuyResp isMemberLvCanByGoods(MemberLVCanBuyReq req) {
		MemberLVCanBuyResp resp = new MemberLVCanBuyResp();
		boolean flag = memberLVSpecManager.memberLvCanByGoodsid(req.getMember_good_id(),
				req.getMember_lv_id());
		resp.setResult(flag);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;

	}

	public IMemberLVSpec getMemberLVSpecManager() {
		return memberLVSpecManager;
	}

	public void setMemberLVSpecManager(IMemberLVSpec memberLVSpecManager) {
		this.memberLVSpecManager = memberLVSpecManager;
	}

}
