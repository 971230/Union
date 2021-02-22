package services;

import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLVSpecReq;
import params.member.req.MemberLvPriceReq;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLVSpecResp;
import params.member.resp.MemberLvPriceResp;

public interface MemberLVSpecInf {
	
	
	public MemberLVCanBuyResp memberLvCanBuyGoods(MemberLVCanBuyReq req);
	
	public MemberLvPriceResp getMemberLvPrice(MemberLvPriceReq req);
	
	public MemberLVSpecResp getMemberLVByIdAndGoodsId(MemberLVSpecReq req);
	
	
	public MemberLVCanBuyResp isMemberLvCanByProduct(MemberLVCanBuyReq req);
	
	public MemberLVCanBuyResp isMemberLvCanByGoods(MemberLVCanBuyReq req);
}
