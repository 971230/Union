package services;

import java.util.List;

import params.member.req.MemberPriceByPIdReq;
import params.member.req.MemberPriceListAddReq;
import params.member.req.MemberPriceListByGoodsIdReq;
import params.member.req.MemberPriceListByLvIdReq;
import params.member.req.MemberPriceQryReq;
import params.member.req.PricePrivListReq;
import params.member.resp.MemberPriceByPIdResp;
import params.member.resp.MemberPriceListAddResp;
import params.member.resp.MemberPriceListByGoodsIdResp;
import params.member.resp.MemberPriceListByLvIdResp;
import params.member.resp.MemberPriceResp;
import params.member.resp.PricePrivListResp;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.service.IMemberPriceManager;

public class MemberPriceServ extends ServiceBase implements MemberPriceInf{
	
	private IMemberPriceManager memberPriceManager;
	
	public IMemberPriceManager getMemberPriceManager() {
		return memberPriceManager;
	}

	public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
		this.memberPriceManager = memberPriceManager;
	}

	@Override
	public MemberPriceByPIdResp getPriceByPid(MemberPriceByPIdReq req) {
		
		MemberPriceByPIdResp resp = new MemberPriceByPIdResp();
		
		GoodsLvPrice goodsLvPrice = memberPriceManager.qryPriceByPid(req.getProduct_id(), req.getMember_lv_id());
		
		resp.setGoodsLvPrice(goodsLvPrice);
		resp.setError_code("0");
		resp.setError_msg("成功");
		//addReturn(req, resp);
		return resp;
	}
	
	@Override
	public MemberPriceListByGoodsIdResp getPriceListByGoodsId(MemberPriceListByGoodsIdReq req){
		
		MemberPriceListByGoodsIdResp resp = new MemberPriceListByGoodsIdResp();
		List<GoodsLvPrice> memPriceList = this.memberPriceManager.listPriceByGid(req.getGoods_id());
		resp.setGoodsLvPriceList(memPriceList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPriceListByLvIdResp getPriceListByLvId(MemberPriceListByLvIdReq req) {

		MemberPriceListByLvIdResp resp = new MemberPriceListByLvIdResp();
		List<GoodsLvPrice> memPriceList = memberPriceManager.listPriceByLvid(req.getLv_id());
		resp.setGoodsLvPriceList(memPriceList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPriceListAddResp addPriceByList(MemberPriceListAddReq req) {
		
		MemberPriceListAddResp resp = new MemberPriceListAddResp();
		try{
			memberPriceManager.save(req.getPriceList());
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("异常");
			
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPriceResp getGoodsLvPage(MemberPriceQryReq req) {
		
		MemberPriceResp resp = new MemberPriceResp();
		
		Page page = this.memberPriceManager.goodsLvList(req.getPageNo(), req.getPageSize(), req.getSn());
		
		resp.setGoodsLvPage(page);
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		return resp;
	}
	
	@Override
	public PricePrivListResp loadPricePrivList(PricePrivListReq req){

		PricePrivListResp resp = new PricePrivListResp();
		
		List list =  memberPriceManager.loadPricePrivList(req.getGoods_id()) ;
		
		resp.setPricePrivList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		return resp;
	}
}
