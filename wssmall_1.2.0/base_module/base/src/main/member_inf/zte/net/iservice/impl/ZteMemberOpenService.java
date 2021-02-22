package zte.net.iservice.impl;

import params.member.req.CommentAddReq;
import params.member.req.MemberAddReq;
import params.member.req.MemberCouponsReq;
import params.member.req.MemberEditReq;
import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLvListReq;
import params.member.req.MemberLvPriceReq;
import params.member.req.MemberOrderIsBuyReq;
import params.member.req.MemberPriceByPIdReq;
import params.member.req.MemberPriceListByGoodsIdReq;
import params.member.req.MemberQryPageReq;
import params.member.req.MemberQryReq;
import params.member.req.PointConsumeReq;
import params.member.req.PointHistoryReq;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberCouponsResp;
import params.member.resp.MemberEditResp;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLvListResp;
import params.member.resp.MemberLvPriceResp;
import params.member.resp.MemberOrderIsBuyResp;
import params.member.resp.MemberPriceByPIdResp;
import params.member.resp.MemberPriceListByGoodsIdResp;
import params.member.resp.MemberQryResp;
import params.member.resp.PointConsumeResp;
import params.member.resp.PointHistoryResp;
import params.order.req.MemberOrderGiftReq;
import params.order.req.MemberOrderLogReq;
import params.order.resp.MemberOrderGiftResp;
import params.order.resp.MemberOrderLogResp;
import zte.net.iservice.IMemberAddressService;
import zte.net.iservice.IMemberService;
import zte.net.iservice.IRegionService;
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
import zte.params.member.req.AgentPageListReq;
import zte.params.member.req.AskAddReq;
import zte.params.member.req.AskGetReq;
import zte.params.member.req.AskPageListReq;
import zte.params.member.req.CommentPageListReq;
import zte.params.member.req.FavAddReq;
import zte.params.member.req.FavCancelReq;
import zte.params.member.req.FavPageListReq;
import zte.params.member.req.GoodsMemberPriceListReq;
import zte.params.member.req.GuestBookAddReq;
import zte.params.member.req.GuestBookPageListReq;
import zte.params.member.req.MemberByMobileReq;
import zte.params.member.req.MemberIsExistsReq;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.req.MemberLvGetReq;
import zte.params.member.req.MemberPwdModifyReq;
import zte.params.member.req.MemberRegisterReq;
import zte.params.member.resp.AgentPageListResp;
import zte.params.member.resp.AskAddResp;
import zte.params.member.resp.AskGetResp;
import zte.params.member.resp.AskPageListResp;
import zte.params.member.resp.CommentAddResp;
import zte.params.member.resp.CommentPageListResp;
import zte.params.member.resp.FavAddResp;
import zte.params.member.resp.FavCancelResp;
import zte.params.member.resp.FavPageListResp;
import zte.params.member.resp.GoodsMemberPriceListResp;
import zte.params.member.resp.GuestBookAddResp;
import zte.params.member.resp.GuestBookPageListResp;
import zte.params.member.resp.MemberByMobileResp;
import zte.params.member.resp.MemberIsExistsResp;
import zte.params.member.resp.MemberLoginResp;
import zte.params.member.resp.MemberLvGetResp;
import zte.params.member.resp.MemberPwdModifyResp;
import zte.params.member.resp.MemberRegisterResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsGetResp;
import zte.params.region.resp.RegionsListResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteMemberOpenService implements IMemberAddressService,IRegionService,IMemberService {
	
	private IMemberAddressService memberAddressService;
	private IRegionService regionService;
	private IMemberService memberService;	

	private void init(){
		memberAddressService= ApiContextHolder.getBean("memberAddressService");
		regionService = ApiContextHolder.getBean("regionService");
		memberService = ApiContextHolder.getBean("memberService");
	}

	@Override
	@ServiceMethod(method="zte.memberService.address.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddressListResp listMemberAddress(MemberAddressListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberAddressService.listMemberAddress(req);
	}
	
	
	@Override
	@ServiceMethod(method="zte.memberService.member.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberQryResp qryMemberList(MemberQryPageReq req){
		this.init();
		
		return memberService.qryMemberList(req);
	}

	@Override
	@ServiceMethod(method="zte.memberService.address.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddressGetResp getMemberAddress(MemberAddressGetReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberAddressService.getMemberAddress(req);
	}

	@Override
	@ServiceMethod(method="zte.memberService.address.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddressAddResp addMemeberAddress(MemberAddressAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberAddressService.addMemeberAddress(req);
	}

	@Override
	@ServiceMethod(method="zte.memberService.address.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddressEditResp editMemberAddress(MemberAddressEditReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberAddressService.editMemberAddress(req);
	}

	@Override
	@ServiceMethod(method="zte.memberService.address.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddressDeleteResp deleteMemberAddress(
			MemberAddressDeleteReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberAddressService.deleteMemberAddress(req);
	}

	@Override
	@ServiceMethod(method="zte.regionService.region.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RegionsListResp listRegions(RegionsListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return regionService.listRegions(req);
	}
	@Override
	@ServiceMethod(method = "zte.memberService.agent.page", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AgentPageListResp queryAgentForPage(AgentPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.queryAgentForPage(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.ask.add", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AskAddResp addAsk(AskAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.addAsk(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.ask.page", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AskPageListResp queryAskForPage(AskPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.queryAskForPage(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.ask.get", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AskGetResp getAsk(AskGetReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.getAsk(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.fav.add", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FavAddResp addFav(FavAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.addFav(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.fav.page", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FavPageListResp queryFavForPage(FavPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.queryFavForPage(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.fav.cancel", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FavCancelResp cancelFav(FavCancelReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.cancelFav(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.guestbook.add", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GuestBookAddResp addGuestBook(GuestBookAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.addGuestBook(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.guestbook.page", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GuestBookPageListResp queryGuestBookForPage(GuestBookPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.queryGuestBookForPage(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.password.modify", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MemberPwdModifyResp modifyMemberPwd(MemberPwdModifyReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.modifyMemberPwd(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.comment.page", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CommentPageListResp queryCommoentForPage(CommentPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.queryCommoentForPage(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.comment.add", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CommentAddResp addComment(CommentAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.addComment(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.member.register", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MemberRegisterResp memberRegister(MemberRegisterReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.memberRegister(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.memberlv.get", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MemberLvGetResp getMemberLv(MemberLvGetReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.getMemberLv(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.memberlv.list", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MemberLvListResp listMemberLv(MemberLvListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.listMemberLv(req);
	}

	@Override
	@ServiceMethod(method = "zte.memberService.member.login", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MemberLoginResp memberLogin(MemberLoginReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.memberLogin(req);
	}
	
	@Override
	@ServiceMethod(method="zte.regionService.memberLvPrice.getMemerLvPrice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberLvPriceResp getMemberLvPrice(MemberLvPriceReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.getMemberLvPrice(req);
	}

	@Override	
	@ServiceMethod(method="zte.regionService.region.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RegionsGetResp getRegion(RegionsGetReq req) {
		this.init();
		req.setRopRequestContext(null);
		return regionService.getRegion(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.memberLV.isCanBuy",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberLVCanBuyResp isMemberLvCanBy(MemberLVCanBuyReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.isMemberLvCanBy(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberServer.memberPrice.qryPriceByPid",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberPriceByPIdResp qryPriceByPid(MemberPriceByPIdReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryPriceByPid(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberServer.memberPrice.qryPriceList",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberPriceListByGoodsIdResp qryPriceList(MemberPriceListByGoodsIdReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryPriceList(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.qryMemberPage",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberQryResp qryMemberPage(MemberQryReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryMemberPage(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberServer.member.addMember",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberAddResp addMember(MemberAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.addMember(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberServer.member.editMember",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberEditResp editMember(MemberEditReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.editMember(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberServer.member.queryOrderLog",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberOrderLogResp qryOrderLog(MemberOrderLogReq req) {
		// TODO Auto-generated method stub
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryOrderLog(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.queryGiftByOrderId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberOrderGiftResp qryOrderGiftByOrderId(MemberOrderGiftReq req) {
		// TODO Auto-generated method stub
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryOrderGiftByOrderId(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.point.history",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PointHistoryResp pagePointHistory(PointHistoryReq req) {
		// TODO Auto-generated method stub
		this.init();
		req.setRopRequestContext(null);
		return memberService.pagePointHistory(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.point.consume",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PointConsumeResp qryConsumePoint(PointConsumeReq req) {
		// TODO Auto-generated method stub
		this.init();
		req.setRopRequestContext(null);
		return memberService.qryConsumePoint(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.current.isBuy",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberOrderIsBuyResp memberIsBuy(MemberOrderIsBuyReq req) {
		// TODO Auto-generated method stub
		this.init();
		req.setRopRequestContext(null);
		return memberService.memberIsBuy(req);
	}

	@Override
	@ServiceMethod(method="zte.memberService.member.isexists",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberIsExistsResp memberIsExists(MemberIsExistsReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.memberIsExists(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberService.member.getMemberByMobile",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberByMobileResp getMemberByMobile(MemberByMobileReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.getMemberByMobile(req);
	}
	
	@Override
	@ServiceMethod(method="zte.memberService.goods.price.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsMemberPriceListResp listGoodsMemberPrice(GoodsMemberPriceListReq req){
		this.init();
		req.setRopRequestContext(null);
		return memberService.listGoodsMemberPrice(req);
	}

	@Override
	@ServiceMethod(method="zte.memberServer.member.qryMemberCouponsPage",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberCouponsResp listMemberCoupons(MemberCouponsReq req) {
		this.init();
		req.setRopRequestContext(null);
		return memberService.listMemberCoupons(req);
	}
}
