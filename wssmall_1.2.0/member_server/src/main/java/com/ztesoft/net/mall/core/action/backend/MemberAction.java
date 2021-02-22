package com.ztesoft.net.mall.core.action.backend;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.eop.resource.model.EopUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.mall.core.model.AdvanceLogs;
import com.ztesoft.net.mall.core.model.PointHistory;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.mall.core.service.IPointHistoryManager;
import com.ztesoft.net.mall.core.service.IRegionsManager;

import params.order.req.MemberOrdReq;
import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionListByCityReq;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionListByCityResp;
import params.req.PartnerAdvLogsAddReq;
import params.req.PartnerAdvLogsReq;
import params.resp.PartnerAdvLogsResp;
import services.CommentsInf;
import services.OrderInf;
import services.PartnerInf;
import services.RegionsInf;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class MemberAction extends WWAction {

    private IMemberManager memberManager;
    private IMemberLvManager memberLvManager;
    private IRegionsManager regionsManager;
    private IPointHistoryManager pointHistoryManager;
    private PartnerInf partnerServ;
    private IUserManager userManager;
    private Member member;
    private MemberLv lv;
    private String birthday;
    private String order;
    private String lv_id;
    private String member_id;
    private String id;
    private List lvlist;
    private List provinceList;
    private List cityList;
    private List regionList;
    private List listOrder;
    private List listPointHistory;
    private List listAdvanceLogs;
    private List listComments;
    private List listLv_id;
    private int point;
    private Double modify_advance;
    private String modify_memo;
    private String object_type;
    private String name;
    private String uname;
    private String lv_idstr;
    private String lv_nameStr;
    private OrderInf orderServ;
    
    private String mobile;
    private int province_id;
    private int city_id;
    private RegionsInf regionsServ;

    @Resource
    private CommentsInf commentsServ;

    public String checkmobile(){
    	 try {
             boolean b=this.memberManager.checkmobile(mobile);
             if(b){
            	 this.json = "{'result':1,'message':'此手机号已被注册'}";
             }else{
            	 this.json = "{'result':0,'message':'此手机号可以注册'}";
             }
         } catch (RuntimeException e) {
        	 this.json = "{'result':1,'message':'此手机号已被注册'}";
         }
         return WWAction.JSON_MESSAGE;
    }
    
    public String add_lv() {
        return "add_lv";
    }

    public String edit_lv() {
        lv = this.memberLvManager.get(lv_id);
        return "edit_lv";
    }

    public String list_lv() {
        this.webpage = memberLvManager.list(order, this.getPage(), this
                .getPageSize());
        return "list_lv";
    }

    public String saveAddLv() {
        memberLvManager.add(lv);
        this.msgs.add("会员等级添加成功");
        this.urls.put("会员等级列表", "member!list_lv.do");
        return WWAction.MESSAGE;
    }

    public String saveEditLv() {
        memberLvManager.edit(lv);
        this.msgs.add("会员等级修改成功");
        this.urls.put("会员等级列表", "member!list_lv.do");
        return WWAction.MESSAGE;
    }

    public String deletelv() {
        try {
            this.memberLvManager.delete(id);
            this.json = "{'result':0,'message':'删除成功'}";
        } catch (RuntimeException e) {
            this.json = "{'result':1,'message':'删除失败'}";
        }
        return WWAction.JSON_MESSAGE;
    }
    
    public String list_region(){
		
		RegionListByCityReq req = new RegionListByCityReq();
		req.setCity_id(city_id);
		RegionListByCityResp resp = regionsServ.getRegionListByCityId(req);
		if(resp != null){
			regionList = resp.getChild_list();
		}
		
		return "list_region";
	}
    
    public String list_city(){
		
		RegionCityListByProvinceReq req = new RegionCityListByProvinceReq();
		req.setProvince_id(province_id);
		RegionCityListByProvinceResp resp = regionsServ.getCityListByProvinceId(req);
		if(resp != null){
			cityList = resp.getChild_list();
		}
		return "list_city";
	}

    public String add_member() {
        lvlist = this.memberLvManager.list();
        provinceList = this.regionsManager.listProvince();
        return "add_member";
    }

    public String edit_member() {

        member = this.memberManager.get(member_id);

        lvlist = this.memberLvManager.list();
        return "edit_member";
    }

    public String memberlist() {
        this.webpage = this.memberManager.list(order, name, uname, this.getPage(),
                this.getPageSize());
        return "list_member";
    }

    public String saveEditMember() {

        //Date br = DateUtil.toDate(birthday, "yyyy-MM-dd");

        try {
            member.setBirthday(birthday);
            member.setPassword(member.getPassword());
            member.setRegtime(DBTUtil.current());// lzf add
            member.setLv_id(this.lv_idstr);
            member.setLv_name(this.lv_nameStr);
            member.setSave(true);
            //this.memberManager.edit(member);
            this.memberManager.editSaveMember(member);

            this.json = "{'result':0,'message':'修改成功'}";
        } catch (Exception e) {
            this.json = "{'result':1,'message':'修改失败'}";
            e.printStackTrace();
        }
        return WWAction.JSON_MESSAGE;


    }

    public String delete() {
        try {
            this.memberManager.delete(id);
            this.json = "{'result':0,'message':'删除成功'}";
        } catch (RuntimeException e) {
            this.json = "{'result':1,'message':'删除失败'}";
        }
        return WWAction.JSON_MESSAGE;
    }

    public String detail() {
        logger.info(member_id);
        return "detail";
    }

    public String base() {
        this.member = this.memberManager.get(member_id);
        return "base";
    }

    public String edit() {
        member = this.memberManager.get(member_id);
        if (!"".equals(this.member.getBirthday()) && this.member.getBirthday() != null) {
            this.member.setBirthday(this.member.getBirthday().substring(0, 10));
        }
        lvlist = this.memberLvManager.list();
        provinceList = this.regionsManager.listProvince();
        if (member.getProvince_id() != null) {
            cityList = this.regionsManager.listCity(member.getProvince_id());
        }
        if (member.getCity_id() != null) {
            regionList = this.regionsManager.listRegion(member.getCity_id());
        }
        String[] idstr = this.member.getLv_ids().split(",");

        this.listLv_id = new ArrayList();
        for (int i = 0; i < idstr.length; i++) {
            this.listLv_id.add(idstr[i]);

        }
        return "edit";
    }

    public String orderLog() {
        MemberOrdReq memberOrdReq = new MemberOrdReq();
        memberOrdReq.setMember_id(member_id);
        listOrder = orderServ.listOrderByMemberId(memberOrdReq).getMemOrders();
        return "orderLog";
    }

    public String editPoint() {
        member = this.memberManager.get(member_id);
        return "editPoint";
    }

    public String editSavePoint() {
        member = this.memberManager.get(member_id);
        member.setPoint(member.getPoint() + point);
        PointHistory pointHistory = new PointHistory();
        pointHistory.setMember_id(member_id);
        pointHistory.setOperator("管理员");
        pointHistory.setPoint(point);
        pointHistory.setReason("operator_adjust");
        pointHistory.setTime(DBTUtil.current());
        try {
            memberManager.edit(member);
            pointHistoryManager.addPointHistory(pointHistory);
            this.json = "{'result':0,'message':'会员积分修改成功'}";
        } catch (Exception e) {
            this.json = "{'result':1,'message':'修改失败'}";
            e.printStackTrace();
        }
        return WWAction.JSON_MESSAGE;
    }

    public String pointLog() {
        listPointHistory = pointHistoryManager.listPointHistory(member_id);
        member = this.memberManager.get(member_id);
        return "pointLog";
    }

    public String advance() {
        member = this.memberManager.get(member_id);
        
        PartnerAdvLogsReq partnerAdvLogsReq = new PartnerAdvLogsReq();
        partnerAdvLogsReq.setMember_id(member_id);
        
        PartnerAdvLogsResp partnerAdvLogsResp = this.partnerServ.listAdvanceLogsByMemberId(partnerAdvLogsReq);
        
        listAdvanceLogs = new ArrayList();
        if(partnerAdvLogsResp != null){
        	listAdvanceLogs = partnerAdvLogsResp.getAdvLogsList();
        }
        return "advance";
    }

    public String comments() {
        listComments = commentsServ.listComments(member_id, object_type);
        return "comments";
    }

    public String remark() {
        member = this.memberManager.get(member_id);

        return "remark";
    }

    public String remarkSave() {
        member = this.memberManager.get(member_id);
        member.setRemark(modify_memo);
        try {
            memberManager.edit(member);
            this.json = "{'result':0,'message':'会员备注修改成功'}";
        } catch (Exception e) {
            this.json = "{'result':1,'message':'修改失败'}";
            e.printStackTrace();
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 预存款充值
     *
     * @return
     */
    public String editSaveAdvance() {
        member = this.memberManager.get(member_id);
        member.setAdvance(member.getAdvance() + modify_advance);
        try {
            memberManager.edit(member);
        } catch (Exception e) {
            this.json = "{'result':1, 'message':'操作失败'}";
            e.printStackTrace();
        }

        AdvanceLogs advanceLogs = new AdvanceLogs();
        advanceLogs.setMember_id(member_id);
        advanceLogs.setDisabled("false");
        advanceLogs.setMtime(DBTUtil.current());
        advanceLogs.setImport_money(modify_advance);
        advanceLogs.setMember_advance(member.getAdvance());
        advanceLogs.setShop_advance(member.getAdvance());// 此字段很难理解
        advanceLogs.setMoney(modify_advance);
        advanceLogs.setMessage(modify_memo);
        advanceLogs.setMemo("admin代充值");
        try {
        	PartnerAdvLogsAddReq partnerAdvLogsAddReq = new PartnerAdvLogsAddReq();
        	partnerAdvLogsAddReq.setAdvanceLogs(advanceLogs);
        	
            partnerServ.addAdvLogs(partnerAdvLogsAddReq);
        } catch (Exception e) {
            this.json = "{'result':1, 'message':'操作失败'}";
            e.printStackTrace();
        }
        this.json = "{'result':0,'message':'操作成功'}";

        return WWAction.JSON_MESSAGE;
    }

    private String message;

    /**
     * 预存款充值
     *
     * @return
     */
    // public String addMoney(){
    // //this.memberManager.addMoney(member.getBiz_money(),
    // member.getMember_id(),message);
    // this.msgs.add("充值成功");
    // this.urls.put("返回此会员预存款信息", "member/member_money.jsp?member_id="+
    // member.getMember_id());
    // return this.MESSAGE;
    // }

    /**
     * 保存添加会员
     * 连连科技
     * @return
     */
    public String saveMemberLlkj() {

        try {
            int result = memberManager.checkname(member.getUname());
            if (result == 1) {
                this.json = "{'result':1,'message':'用户名已经存在'}";
                //this.msgs.add("用户名已经存在");
                //this.urls.put("查看会员列表", "member!memberlist.do");

            } else {
                if (member != null) {
                    //Date br = DateUtil.toDate(birthday, "yyyy-MM-dd");
                    member.setBirthday(birthday);
                    member.setPassword(member.getPassword());
                    member.setRegtime(DBTUtil.current());// lzf add
                    member.setLv_id(this.lv_idstr);
                    member.setLv_name(this.lv_nameStr);
                    this.member.setSave(true);

                    memberManager.addMember(member);
                    this.json = "{'result':0,'message':'保存添加会员成功'}";
                    //this.msgs.add("保存添加会员成功");
                    //this.urls.put("查看会员列表", "member!memberlist.do");
                } //else
            }
        } catch (RuntimeException e) {

            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:\"保存添加会员失败：" + e.getMessage() + "\"}";
        }

        return WWAction.JSON_MESSAGE;
    }
    
    /**
     * 保存添加会员
     *
     * @return
     */
    public String saveMember() {

        try {
            int result = memberManager.checkname(member.getUname());
            if (result == 1) {
                this.json = "{'result':1,'message':'用户名已经存在'}";
                //this.msgs.add("用户名已经存在");
                //this.urls.put("查看会员列表", "member!memberlist.do");

            } else {
                if (member != null) {
                    //Date br = DateUtil.toDate(birthday, "yyyy-MM-dd");
                    member.setBirthday(birthday);
                    member.setPassword(member.getPassword());
                    member.setRegtime(DBTUtil.current());// lzf add
                    member.setLv_id(this.lv_idstr);
                    member.setLv_name(this.lv_nameStr);
                    this.member.setSave(true);

                    memberManager.add(member);
                    this.json = "{'result':0,'message':'保存添加会员成功'}";
                    //this.msgs.add("保存添加会员成功");
                    //this.urls.put("查看会员列表", "member!memberlist.do");
                } //else
            }
        } catch (RuntimeException e) {

            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:\"保存添加会员失败：" + e.getMessage() + "\"}";
        }

        return WWAction.JSON_MESSAGE;
    }

    public String sysLogin() {
        try {
            int r = this.memberManager.loginbysys(name);
            if (r == 1) {
                EopUser user = this.userManager.get(name);
                if (user != null) {
                    WebSessionContext<EopUser> sessonContext = ThreadContextHolder
                            .getSessionContext();
                    sessonContext.setAttribute(IUserManager.USER_SESSION_KEY, user);
                }
                return "syslogin";
            }
            this.msgs.add("登录失败");
            return WWAction.MESSAGE;
        } catch (RuntimeException e) {
            this.msgs.add(e.getMessage());
            return WWAction.MESSAGE;
        }
    }


    public MemberLv getLv() {
        return lv;
    }

    public void setLv(MemberLv lv) {
        this.lv = lv;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public IMemberManager getMemberManager() {
        return memberManager;
    }

    public void setMemberManager(IMemberManager memberManager) {
        this.memberManager = memberManager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public IMemberLvManager getMemberLvManager() {
        return memberLvManager;
    }

    public void setMemberLvManager(IMemberLvManager memberLvManager) {
        this.memberLvManager = memberLvManager;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLv_id() {
        return lv_id;
    }

    public void setLv_id(String lvId) {
        lv_id = lvId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String memberId) {
        member_id = memberId;
    }

    public List getLvlist() {
        return lvlist;
    }

    public void setLvlist(List lvlist) {
        this.lvlist = lvlist;
    }

    public IRegionsManager getRegionsManager() {
        return regionsManager;
    }

    public void setRegionsManager(IRegionsManager regionsManager) {
        this.regionsManager = regionsManager;
    }

    public List getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List provinceList) {
        this.provinceList = provinceList;
    }

    public List getCityList() {
        return cityList;
    }

    public void setCityList(List cityList) {
        this.cityList = cityList;
    }

    public List getRegionList() {
        return regionList;
    }

    public void setRegionList(List regionList) {
        this.regionList = regionList;
    }


    public List getListOrder() {
        return listOrder;
    }

    public void setListOrder(List listOrder) {
        this.listOrder = listOrder;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public IPointHistoryManager getPointHistoryManager() {
        return pointHistoryManager;
    }

    public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
        this.pointHistoryManager = pointHistoryManager;
    }

    public List getListPointHistory() {
        return listPointHistory;
    }

    public void setListPointHistory(List listPointHistory) {
        this.listPointHistory = listPointHistory;
    }

    public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	public List getListAdvanceLogs() {
        return listAdvanceLogs;
    }

    public void setListAdvanceLogs(List listAdvanceLogs) {
        this.listAdvanceLogs = listAdvanceLogs;
    }

    public Double getModify_advance() {
        return modify_advance;
    }

    public void setModify_advance(Double modifyAdvance) {
        modify_advance = modifyAdvance;
    }

    public String getModify_memo() {
        return modify_memo;
    }

    public void setModify_memo(String modifyMemo) {
        modify_memo = modifyMemo;
    }

    public List getListComments() {
        return listComments;
    }

    public void setListComments(List listComments) {
        this.listComments = listComments;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String objectType) {
        object_type = objectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public IUserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(IUserManager userManager) {
        this.userManager = userManager;
    }

    public String getLv_idstr() {
        return lv_idstr;
    }

    public void setLv_idstr(String lv_idstr) {
        this.lv_idstr = lv_idstr;
    }

    public String getLv_nameStr() {
        return lv_nameStr;
    }

    public void setLv_nameStr(String lv_nameStr) {
        this.lv_nameStr = lv_nameStr;
    }

    public List getListLv_id() {
        return listLv_id;
    }

    public void setListLv_id(List listLv_id) {
        this.listLv_id = listLv_id;
    }

    public OrderInf getOrderServ() {
        return orderServ;
    }

    public void setOrderServ(OrderInf orderServ) {
        this.orderServ = orderServ;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

}
