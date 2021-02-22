package zte.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.drools.core.util.StringUtils;

import params.ask.req.AskReq;
import params.ask.resp.AskResp;
import params.attention.req.AttentionReq;
import params.attention.resp.AttentionResp;
import params.guest.req.GuestBookReq;
import params.guest.resp.GuestBookResp;
import params.login.req.LoginReq;
import params.login.resp.LoginResp;
import params.member.req.AgentReq;
import params.member.req.CommentAddReq;
import params.member.req.CommentListReq;
import params.member.req.MemberAddReq;
import params.member.req.MemberCouponsReq;
import params.member.req.MemberEditReq;
import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLvByIdReq;
import params.member.req.MemberLvListReq;
import params.member.req.MemberLvPriceReq;
import params.member.req.MemberOrderIsBuyReq;
import params.member.req.MemberPriceByPIdReq;
import params.member.req.MemberPriceListByGoodsIdReq;
import params.member.req.MemberQryPageReq;
import params.member.req.MemberQryReq;
import params.member.req.PointConsumeReq;
import params.member.req.PointHistoryReq;
import params.member.req.PwdModifyReq;
import params.member.req.RegisterReq;
import params.member.resp.AgentResp;
import params.member.resp.CommentListResp;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberCouponsResp;
import params.member.resp.MemberEditResp;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLvByIdResp;
import params.member.resp.MemberLvListResp;
import params.member.resp.MemberLvPriceResp;
import params.member.resp.MemberOrderIsBuyResp;
import params.member.resp.MemberPriceByPIdResp;
import params.member.resp.MemberPriceListByGoodsIdResp;
import params.member.resp.MemberQryResp;
import params.member.resp.PointConsumeResp;
import params.member.resp.PointHistoryResp;
import params.member.resp.RegisterResp;
import params.order.req.MemberOrderGiftReq;
import params.order.req.MemberOrderLogReq;
import params.order.resp.MemberOrderGiftResp;
import params.order.resp.MemberOrderLogResp;
import services.AgentInf;
import services.AskInf;
import services.AttentionInf;
import services.GuestBookInf;
import services.LoginInf;
import services.MemberCenterInf;
import services.MemberInf;
import services.MemberLVSpecInf;
import services.MemberLvInf;
import services.MemberPriceInf;
import services.ServiceBase;
import zte.net.iservice.IMemberService;
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

import com.ztesoft.api.ApiRunTimeException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IMemberCouponsManager;
import com.ztesoft.net.mall.core.service.IMemberPriceManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class MemberService extends ServiceBase implements IMemberService {

	@Resource
	private AgentInf agentServ;
	@Resource
	private AskInf askServ;
	@Resource
	private AttentionInf attentionServ;
	@Resource
	private GuestBookInf guestBookServ;
	@Resource
	private MemberCenterInf memberCenterServ;
	@Resource
	private MemberLvInf memberLvServ;
	@Resource
	private LoginInf loginServ;
	@Resource
	private MemberInf memberServ;

	@Resource
	private MemberLVSpecInf memberLVSpecServ;
	@Resource
	private MemberPriceInf memberPriceServ;
	@Resource
	private IMemberPriceManager memberPriceManager;
	@Resource
	private IMemberCouponsManager memberCouponsManager;

	@Override
	public AgentPageListResp queryAgentForPage(AgentPageListReq req) {
		AgentReq areq = new AgentReq();
		AgentPageListResp resp = new AgentPageListResp();
		try {
			BeanUtils.copyProperties(areq, req);
			AgentResp aresp = agentServ.getStaffInfo(areq);
			BeanUtils.copyProperties(resp, aresp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberQryResp qryMemberList(MemberQryPageReq req) {

		return this.memberServ.qryMemberList(req);
	}

	@Override
	public AskAddResp addAsk(AskAddReq req) {
		AskReq areq = new AskReq();
		areq.setAsk(req.getAsk());
		askServ.add(areq);
		AskAddResp resp = new AskAddResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public AskPageListResp queryAskForPage(AskPageListReq req) {
		AskReq areq = new AskReq();
		AskPageListResp resp = new AskPageListResp();
		try {
			// BeanUtils.copyProperties(areq, req);
			areq.setKeyword(req.getKeyword());
			areq.setStartTime(req.getStartTime());
			areq.setEndTime(req.getEndTime());
			areq.setPageNo(req.getPageNo());
			areq.setPageSize(req.getPageSize());
			areq.setUserSessionId(req.getUserSessionId());
			AskResp aresp = askServ.listMyAsk(areq);
			resp.setAskPage(aresp.getAskPage());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public AskGetResp getAsk(AskGetReq req) {
		AskReq areq = new AskReq();
		areq.setAskid(req.getAsk_id());
		AskResp aresp = askServ.get(areq);
		AskGetResp resp = new AskGetResp();
		resp.setAsk(aresp.getAsk());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public FavAddResp addFav(FavAddReq req) {
		FavAddResp resp = new FavAddResp();
		try {
			AttentionReq areq = new AttentionReq();
			BeanUtils.copyProperties(areq, req);
			AttentionResp aresp = attentionServ.addFav(areq);
			resp.setError_code(aresp.getError_code());
			resp.setError_msg(aresp.getError_msg());
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			if(null == resp.getError_msg() || "".equals(resp.getError_msg())){
				resp.setError_msg("失败");
			}
			
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public FavPageListResp queryFavForPage(FavPageListReq req) {
		FavPageListResp resp = new FavPageListResp();
		try {
			AttentionReq areq = new AttentionReq();
			BeanUtils.copyProperties(areq, req);
			AttentionResp aresp = null;
			if (FavPageListReq.GOODS_TYPE.equals(req.getFav_type())) {
				aresp = attentionServ.queryGoodsFav(areq);
			} else if (FavPageListReq.GOODS_TYPE.equals(req.getFav_type())) {
				aresp = attentionServ.querySupplerFav(areq);
			} else if (FavPageListReq.PARTNER_TYPE.equals(req.getFav_type())) {
				aresp = attentionServ.queryPartnerFav(areq);
			}

			resp.setFavPage(aresp.getWebPage());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public FavCancelResp cancelFav(FavCancelReq req) {
		FavCancelResp resp = new FavCancelResp();
		AttentionReq areq = new AttentionReq();
		areq.setFavorite_id(req.getFavorite_id());
		attentionServ.delete(areq);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public GuestBookAddResp addGuestBook(GuestBookAddReq req) {
		GuestBookAddResp resp = new GuestBookAddResp();
		GuestBookReq areq = new GuestBookReq();
		areq.setGuestbook(req.getGuestbook());
		guestBookServ.add(areq);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public GuestBookPageListResp queryGuestBookForPage(GuestBookPageListReq req) {
		GuestBookReq areq = new GuestBookReq();
		GuestBookPageListResp resp = new GuestBookPageListResp();
		try {
			BeanUtils.copyProperties(areq, req);
			GuestBookResp aresp = guestBookServ.list(areq);
			resp.setPage(aresp.getPage());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPwdModifyResp modifyMemberPwd(MemberPwdModifyReq req) {
		MemberPwdModifyResp resp = new MemberPwdModifyResp();
		PwdModifyReq areq = new PwdModifyReq();
		try {
			BeanUtils.copyProperties(areq, req);
			memberCenterServ.modifyPwd(areq);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public CommentPageListResp queryCommoentForPage(CommentPageListReq req) {
		CommentListReq areq = new CommentListReq();
		CommentPageListResp resp = new CommentPageListResp();
		try {
			BeanUtils.copyProperties(areq, req);
			CommentListResp aresp = memberCenterServ.commentList(areq);
			resp.setPage(aresp.getPage());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public CommentAddResp addComment(CommentAddReq req) {
		memberCenterServ.commentAdd(req);
		CommentAddResp resp = new CommentAddResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberRegisterResp memberRegister(MemberRegisterReq req) {
		RegisterReq areq = new RegisterReq();
		areq.setMember(req.getMember());
		RegisterResp aresp = memberCenterServ.register(areq);
		MemberRegisterResp resp = new MemberRegisterResp();
		resp.setMember(aresp.getMember());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLvGetResp getMemberLv(MemberLvGetReq req) {
		MemberLvByIdReq areq = new MemberLvByIdReq();
		areq.setMember_lv_id(req.getMember_lv_id());
		MemberLvByIdResp aresp = memberLvServ.getMemberLvById(areq);
		MemberLvGetResp resp = new MemberLvGetResp();
		resp.setMemberLv(aresp.getMemberLv());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLvListResp listMemberLv(MemberLvListReq req) {
		return memberLvServ.getMemberLvList(req);
	}

	@Override
	public MemberLoginResp memberLogin(MemberLoginReq req) {
		LoginReq areq = new LoginReq();
		MemberLoginResp resp = new MemberLoginResp();
		try {
			BeanUtils.copyProperties(areq, req);
			areq.setUserSessionId(req.getUserSessionId());
			LoginResp aresp = loginServ.login(areq);
			BeanUtils.copyProperties(resp, aresp);
			resp.setUserSessionId(areq.getUserSessionId());
			resp.setMember(aresp.getMember());
		} catch (ApiRunTimeException ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg(ex.getErrMsg());
		} catch (Exception ef) {
			ef.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLvPriceResp getMemberLvPrice(MemberLvPriceReq req) {
		MemberLvPriceResp resp = new MemberLvPriceResp();
		try {
			MemberLvPriceResp aresp = memberLVSpecServ.getMemberLvPrice(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setMemberLVSpecMap(aresp.getMemberLVSpecMap());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberLVCanBuyResp isMemberLvCanBy(MemberLVCanBuyReq req) {
		MemberLVCanBuyResp resp = new MemberLVCanBuyResp();
		try {
			MemberLVCanBuyResp aresp = new MemberLVCanBuyResp();
			if (req.getBuy_type().equals(
					MemberLVCanBuyReq.BUY_TYPE.MEMBER_BUY_PRODUCT))
				aresp = memberLVSpecServ.isMemberLvCanByProduct(req);
			else
				aresp = memberLVSpecServ.isMemberLvCanByGoods(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setResult(aresp.isResult());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPriceByPIdResp qryPriceByPid(MemberPriceByPIdReq req) {
		MemberPriceByPIdResp resp = new MemberPriceByPIdResp();
		try {
			MemberPriceByPIdResp aresp = memberPriceServ.getPriceByPid(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setGoodsLvPrice(aresp.getGoodsLvPrice());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberPriceListByGoodsIdResp qryPriceList(
			MemberPriceListByGoodsIdReq req) {
		MemberPriceListByGoodsIdResp resp = new MemberPriceListByGoodsIdResp();
		try {
			MemberPriceListByGoodsIdResp aresp = memberPriceServ
					.getPriceListByGoodsId(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setGoodsLvPriceList(aresp.getGoodsLvPriceList());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberQryResp qryMemberPage(MemberQryReq req) {
		MemberQryResp resp = new MemberQryResp();
		try {
			MemberQryResp aresp = null;
			aresp = memberServ.qryMember(req);

			BeanUtils.copyProperties(resp, aresp);
			resp.setMember(aresp.getMember());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberAddResp addMember(MemberAddReq req) {
		MemberAddResp resp = new MemberAddResp();
		try {
			MemberAddResp aresp = memberServ.addNewMember(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setCount(aresp.getCount());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberEditResp editMember(MemberEditReq req) {
		MemberEditResp resp = new MemberEditResp();
		try {
			MemberEditResp aresp = memberServ.editMemberInfo(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberOrderLogResp qryOrderLog(MemberOrderLogReq req) {
		// TODO Auto-generated method stub
		MemberOrderLogResp resp = new MemberOrderLogResp();
		try {
			MemberOrderLogResp aresp = memberServ.qryOrderLog(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberOrderGiftResp qryOrderGiftByOrderId(MemberOrderGiftReq req) {
		// TODO Auto-generated method stub
		MemberOrderGiftResp resp = new MemberOrderGiftResp();
		try {
			MemberOrderGiftResp aresp = memberServ.qryGiftByOrderId(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询会员积分日志", summary = "查询会员积分日志")
	public PointHistoryResp pagePointHistory(PointHistoryReq req) {
		// TODO Auto-generated method stub
		PointHistoryResp resp = new PointHistoryResp();
		try {
			PointHistoryResp aresp = memberServ.qryPointHistoryPage(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "会员累计积分", summary = "会员累计积分")
	public PointConsumeResp qryConsumePoint(PointConsumeReq req) {
		// TODO Auto-generated method stub
		PointConsumeResp resp = new PointConsumeResp();
		try {
			PointConsumeResp aresp = memberServ.qryPointConsume(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "判断当前会员是否购买过此商品", summary = "判断当前会员是否购买过此商品")
	public MemberOrderIsBuyResp memberIsBuy(MemberOrderIsBuyReq req) {
		// TODO Auto-generated method stub
		MemberOrderIsBuyResp resp = new MemberOrderIsBuyResp();
		try {
			MemberOrderIsBuyResp aresp = memberServ.isBuy(req);
			BeanUtils.copyProperties(resp, aresp);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "判断用户是否存在", summary = "判断用户是否存在")
	public MemberIsExistsResp memberIsExists(MemberIsExistsReq req) {
		MemberIsExistsResp resp = new MemberIsExistsResp();
		String sql = SF.memberSql("MEMBER_IS_EXIST");
		List param = new ArrayList();
		if (!StringUtils.isEmpty(req.getUname())) {
			sql += " and m.uname=?";
			param.add(req.getUname());
		}
		if (!StringUtils.isEmpty(req.getPhone_no())) {
			sql += " and m.tel=?";
			param.add(req.getPhone_no());
		}
		int count = this.baseDaoSupport.queryForInt(sql, param.toArray());
		resp.setExists(count > 0);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据用户号码查会员", summary = "根据用户号码查会员")
	public MemberByMobileResp getMemberByMobile(MemberByMobileReq req) {
		MemberByMobileResp resp = new MemberByMobileResp();
		try {
			String sql = SF.memberSql("SERVICE_GET_MEMBER_BY_MOBILE");
			List param = new ArrayList();
			param.add(req.getPhone_no());

			Member member = (Member) this.baseDaoSupport.queryForObject(sql,
					Member.class, param.toArray());
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setMember(member);
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public GoodsMemberPriceListResp listGoodsMemberPrice(
			GoodsMemberPriceListReq req) {
		List priceList = null;
		String goods_id = req.getGoods_id();
		GoodsMemberPriceListResp resp = new GoodsMemberPriceListResp();
		try {
			if (!StringUtils.isEmpty(goods_id)) {
				priceList = this.memberPriceManager
						.loadMemberLvPriceList(goods_id);
				if (priceList == null || priceList.isEmpty())
					priceList = this.memberPriceManager.loadMemberLvList();
			} else {
				priceList = this.memberPriceManager.loadMemberLvList();
			}
			IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder
					.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
					dcPublicInfoManager);
			List discount = dcPublicCache.getList("1");
			resp.setPriceList(priceList);
			resp.setDiscountList(discount);
		} catch (RuntimeException e) {
			this.logger.info(e.getMessage(), e.fillInStackTrace());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取会员优惠券", summary = "获取会员优惠券")
	public MemberCouponsResp listMemberCoupons(MemberCouponsReq req) {
		MemberCouponsResp resp = new MemberCouponsResp();
		Page page = this.memberCouponsManager.pageMemberCoupons(req.getPageNo(), req.getPageSize());
		resp.setPage(page);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
}
