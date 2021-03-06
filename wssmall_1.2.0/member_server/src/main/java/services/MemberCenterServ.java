package services;

import java.util.List;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import params.comments.req.AddCommentsReq;
import params.member.req.CommentAddReq;
import params.member.req.CommentListReq;
import params.member.req.OrderInfoReq;
import params.member.req.OrderListReq;
import params.member.req.PwdModifyReq;
import params.member.req.RegisterReq;
import params.member.resp.CommentListResp;
import params.member.resp.OrderInfoResp;
import params.member.resp.OrderListResp;
import params.member.resp.RegisterResp;
import params.order.req.OrderReq;
import utils.CoreThreadLocalHolder;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.resource.IDataLogManager;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Comments;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderMeta;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.mall.core.service.IMemberOrderManager;
import com.ztesoft.net.sqls.SF;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 个人中心
 *
 * @作者 MoChunrun
 * @创建日期 2013-9-25
 * @版本 V 1.0
 */
public class MemberCenterServ extends ServiceBase implements MemberCenterInf {

    private IMemberOrderManager memberOrderManager;
    private IMemberManager memberManager;
    private IDataLogManager dataLogManager;
    private OrderInf orderServ;

    private CommentsInf commentsServ;

    /**
     * 修改密码
     *
     * @param req
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-27
     */
    @Override
	public ZteResponse modifyPwd(PwdModifyReq req) {
        try {
            ZteResponse resp = new ZteResponse();
            if (StringUtil.isEmpty(req.getOldPwd()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "OldPwd不能为空"));
            if (StringUtil.isEmpty(req.getNewPwd()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "NewPwd不能为空"));
            Member member = null;//CommonTools.getLoginMember();
            if (member == null && req.getMember_id() != null && !"".equals(req.getMember_id()))
                member = memberManager.get(req.getMember_id());
            if (member == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员ID有误"));
            String oldPwd = StringUtil.md5(req.getOldPwd());
            if (oldPwd.equals(member.getPassword())) {
                String newPwd = StringUtil.md5(req.getNewPwd());
                String sql = SF.memberSql("MEMBER_MODIFY_PWD");
                this.baseDaoSupport.execute(sql, newPwd, member.getMember_id());
            } else {
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "OldPwd不正确"));
            }
            resp.setUserSessionId(req.getUserSessionId());
            resp.setError_code("0");
            resp.setError_msg("成功");
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 查询用户订单列表
     *
     * @param req
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-25
     */
    @Override
	public OrderListResp qryOrderList(OrderListReq req) {
        try {
            OrderListResp resp = new OrderListResp();
            Member member = CommonTools.getLoginMember();
            if (member == null && req.getMember_id() != null && !"".equals(req.getMember_id()))
                member = memberManager.get(req.getMember_id());
            if (member == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员ID有误"));
            ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            if (req.getOrderStatus() == null || "".equals(req.getOrderStatus()) || "0".equals(req.getOrderStatus())) {
                Page page = memberOrderManager.pageOrders(req.getPageNo(), req.getPageSize());
                resp.setPage(page);
            } else {
                Page page = memberOrderManager.pageOrders(req.getPageNo(), req.getPageSize(), req.getOrderStatus());
                resp.setPage(page);
            }
            resp.setUserSessionId(req.getUserSessionId());
            resp.setError_code("0");
            resp.setError_msg("成功");
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 查询订单信息
     *
     * @param req
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-25
     */
    @Override
	public OrderInfoResp qryOrderInfo(OrderInfoReq req) {
        try {

            if (req.getOrder_id() == null || "".equals(req.getOrder_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
            OrderInfoResp resp = new OrderInfoResp();
            OrderReq orderReq = new OrderReq();
            orderReq.setOrder_id(req.getOrder_id());
            Order order = orderServ.get(orderReq).getOrder();

            if (order == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id有误"));
            if (order != null && order.getPay_time() != null && order.getPay_time().endsWith(".0")) {
                order.setPay_time(order.getPay_time().substring(0, order.getPay_time().length() - 2));
            }

            orderReq = new OrderReq();
            orderReq.setOrder_id(order.getOrder_id());

            List orderLogList = memberOrderManager.listOrderLog(order.getOrder_id());
            List orderItemsList = orderServ.listGoodsItems(orderReq).getOrderItems();
            List giftList = memberOrderManager.listGiftItems(order.getOrder_id());
            resp.setOrder(order);
            resp.setOrderLogList(orderLogList);
            resp.setOrderItemsList(orderItemsList);
            resp.setGiftList(giftList);
            List<OrderMeta> metaList = orderServ.listOrderMetas(orderReq).getMetaList();
            resp.setMetaList(metaList);
            resp.setError_code("0");
            resp.setError_msg("成功");
            resp.setUserSessionId(req.getUserSessionId());
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 查询用户评论与咨询
     *
     * @param req
     * @作者 MoChunrun
     * @创建日期 2013-9-25
     */
    @Override
	public CommentListResp commentList(CommentListReq req) {
        try {
            Member member = CommonTools.getLoginMember();
            if (member == null && req.getMember_id() != null && !"".equals(req.getMember_id()))
                member = memberManager.get(req.getMember_id());
            if (member == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员ID有误"));
            ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            CommentListResp resp = new CommentListResp();
            Page page = commentsServ.pageComments_Display(req.getPageNo(), req.getPageSize());
            resp.setPage(page);
            resp.setError_code("0");
            resp.setError_msg("成功");
            resp.setUserSessionId(req.getUserSessionId());
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 发表评论
     *
     * @param req
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-25
     */
    @Override
	public ZteResponse commentAdd(CommentAddReq req) {
        try {
            if (req.getAction() == null)
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "action有误，请填写1(咨询)或2(评论)"));
            if (req.getMember_id() == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "Member_id有误"));
            if (req.getComment() == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "Comment不能为空"));
            Member member = CommonTools.getLoginMember();
            if (member == null && req.getMember_id() != null && !"".equals(req.getMember_id()))
                member = memberManager.get(req.getMember_id());
            //if(member==null)CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"会员ID有误"));
            //ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY,member);
            if (member == null) {
                member = new Member();
                member.setMember_id("0");
                member.setName("匿名");
            }
            ZteResponse resp = new ZteResponse();

            Comments comment = req.getComment();
            if (req.getAction().equals("1")) {
                comment.setCommenttype("goods");
                comment.setObject_type("ask");
            } else {
                comment.setCommenttype("wssdetails");
                comment.setObject_type("discuss");
            }
            comment.setDisabled("false");
            comment.setDisplay("false");
            comment.setAuthor(member.getName());
            comment.setAuthor_id(member.getMember_id());
            comment.setTime(DBTUtil.current());
            
            AddCommentsReq addCommentsReq = new AddCommentsReq();
            addCommentsReq.setComments(comment);
            commentsServ.addComments(addCommentsReq);
            /*DataLog datalog  = new DataLog();
			datalog.setContent("标题:"+req.getComment().getTitle()+"<br>内容"+ req.getComment().getAcomment());
			datalog.setLogtype("评论");
			datalog.setOptype("添加");
			dataLogManager.add(datalog);*/

            resp.setError_code("0");
            resp.setError_msg("添加成功,待管理员审核后显示");
            resp.setUserSessionId(req.getUserSessionId());
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 用户注册
     *
     * @param req
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-27
     */
    @Override
	public RegisterResp register(RegisterReq req) {
        try {
            RegisterResp resp = new RegisterResp();
            Member member = req.getMember();
            if (req.getMember() == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "Member有误"));
            if (StringUtil.isEmpty(req.getMember().getMember_id()) && StringUtil.isEmpty(req.getMember().getUname()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "Uname不能为空"));
//			if(!OrderStatus.SOURCE_FROM_TAOBAO.equals(member.getSource_from()) && !OrderStatus.SOURCE_FROM_FJ.equals(member.getSource_from()) && !OrderStatus.SOURCE_FROM_WT.equals(member.getSource_from())){
//				if(member.getPassword()==null || "".equals(member.getPassword()))
//					member.setPassword(member.getUname());
//				if(StringUtil.isEmpty(req.getMember().getPassword()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Password不能为空"));
//				//if(StringUtil.isEmpty(req.getMember().getName()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Name不能为空"));
//				//if(req.getMember().getSex()==null)CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Sex不能为空"));
//				//if(StringUtil.isEmpty(req.getMember().getBirthday()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Birthday不能为空"));
//				//if(StringUtil.isEmpty(req.getMember().getEmail()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Email不能为空"));
//			}else{
            if (member.getPassword() == null || "".equals(member.getPassword()))
                member.setPassword(member.getUname());
            //}

            //logger.info(member.getPassword());
            //member.setPassword(com.ztesoft.net.framework.util.StringUtil.md5(member.getPassword()));
            //logger.info(member.getPassword());
            member.setRegtime(DBTUtil.current());
            if (StringUtil.isEmpty(member.getParentid())) member.setParentid("0");
            member.setLv_id(Const.MEMBER_LV_COMMON);
            member.setLv_name("普通会员");
            Member m = null;
            if (!StringUtil.isEmpty(member.getMember_id()))
                m = memberManager.get(member.getMember_id());
            //去掉用户名判断，同名也新增会员   2014/9/24 mochunrun
//            if (m == null)
//                m = memberManager.getMemberByUname(member.getUname());
            if (m == null) {
                int result = memberManager.add(member);

                if (result == 1) {
                    resp.setError_code("0");
                    resp.setError_msg("成功");
                } else if (result == 0) {
                    CommonTools.addError(new ZteError("3", "会员已存在"));
                } else {
                    resp.setError_code("2");
                    resp.setError_msg("失败");
                }
            } else {
                member = m;
            }
            //登录成功设置session_id
            resp.setUserSessionId(req.getUserSessionId());
            resp.setMember(member);
            ZteRequest zteRequest = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest();
            if(zteRequest !=null && StringUtil.isEmpty(zteRequest.getUserSessionId()))
            	zteRequest.setUserSessionId(req.getUserSessionId());
            
            addReturn(req, resp);
            return resp;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    public IMemberOrderManager getMemberOrderManager() {
        return memberOrderManager;
    }

    public void setMemberOrderManager(IMemberOrderManager memberOrderManager) {
        this.memberOrderManager = memberOrderManager;
    }


    public IMemberManager getMemberManager() {
        return memberManager;
    }

    public void setMemberManager(IMemberManager memberManager) {
        this.memberManager = memberManager;
    }

    public IDataLogManager getDataLogManager() {
        return dataLogManager;
    }

    public void setDataLogManager(IDataLogManager dataLogManager) {
        this.dataLogManager = dataLogManager;
    }

    public OrderInf getOrderServ() {
        return orderServ;
    }

    public void setOrderServ(OrderInf orderServ) {
        this.orderServ = orderServ;
    }

	public CommentsInf getCommentsServ() {
		return commentsServ;
	}

	public void setCommentsServ(CommentsInf commentsServ) {
		this.commentsServ = commentsServ;
	}

}
