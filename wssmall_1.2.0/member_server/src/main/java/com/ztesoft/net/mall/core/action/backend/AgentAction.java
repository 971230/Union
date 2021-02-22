package com.ztesoft.net.mall.core.action.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.remote.inf.IAdvService;
import com.ztesoft.remote.inf.IArticleService;
import com.ztesoft.remote.params.adv.req.DelAdvReq;
import com.ztesoft.remote.params.article.req.DelArticleReq;
import params.adminuser.req.AdminUserDisableReq;
import params.req.PartnerAgentListReq;
import params.req.PartnerExistsReq;
import params.req.PartnerShopStarListReq;
import params.req.PartnerShopTypeListReq;
import params.resp.PartnerAgentListResp;
import params.resp.PartnerExistsResp;
import params.resp.PartnerShopStarListResp;
import params.resp.PartnerShopTypeListResp;
import services.AdminUserInf;
import services.GoodsInf;
import services.PartnerInf;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.app.base.core.model.Agent;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IAgentManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

/**
 * 分销商户申请
 *
 * @author
 */
public class AgentAction extends WWAction {
    protected String username;
    protected String state;
    protected String order;
    protected String ic_card;
    protected String agent_id;
    protected String actionName;
    protected Map agentView;
    protected Agent agent;
    protected Agent agentSearch;//搜索
    protected String staff_no;
    protected String staff_name;
    private String part_partner_code, part_partner_name;
    private List agentList;//上级分销商
    private List partnerTypeList;//分销商类型
    private List partnerLeveList;//分销商级别
    private List partnerCatList;//分销商类别
    private List partnerShopTypeList;//店铺类型
    private List partnerShopStarList;//网店量级

    private IAgentManager agentManager;
    private PartnerInf partnerServ;
    private AdminUserInf adminUserServ;
    private IAdvService advService;
    private IArticleService articleService;
    private ICacheUtil cacheUtil;

    @Resource
    private GoodsInf goodsServ;

    //加载分销商下拉框
    public String showAgentSelect() {

        PartnerAgentListReq partnerAgentListReq = new PartnerAgentListReq();

        PartnerAgentListResp partnerAgentListResp = partnerServ.searchPartnerAdUserList(partnerAgentListReq);

        agentList = new ArrayList();
        if (partnerAgentListResp != null) {
            agentList = partnerAgentListResp.getAgentList();
        }

        return "show_agent_select";
    }

    /*//分销商类型
    public String showpartnerTypeSelect(){

        partnerTypeList=partnerManager.searchPartnerTypeList();

         return "show_partnerType_select";
    }*/
    //分销商级别
    /*public String showpartnerLevelSelect(){

		partnerLeveList=partnerManager.searchPartnerLevelList();

		 return "show_partnerLevel_select";
	}*/
    //分销商类别
	/*public String showpartnerCatSelect(){
		
		partnerCatList=partnerManager.searchPartneCatList();

		 return "show_partnerCat_select";
	}*/
    //分销商店铺类型
    public String showpartnerShopTypeSelect() {
        PartnerShopTypeListReq partnerShopTypeListReq = new PartnerShopTypeListReq();
        PartnerShopTypeListResp partnerShopTypeListResp = partnerServ.searchPartneShopTypeList(partnerShopTypeListReq);

        partnerShopTypeList = new ArrayList();
        if (partnerShopTypeListResp != null) {
            partnerShopTypeList = partnerShopTypeListResp.getShopTypeList();
        }

        return "show_partnerShopType_select";
    }

    //分销商网店量级
    public String showpartnerShopStarSelect() {
        PartnerShopStarListReq partnerShopStarListReq = new PartnerShopStarListReq();
        PartnerShopStarListResp partnerShopStarListResp = partnerServ.searchPartneShopStarList(partnerShopStarListReq);

        partnerShopStarList = new ArrayList();
        if (partnerShopStarListResp != null) {
            partnerShopStarList = partnerShopStarListResp.getShopStarList();
        }

        return "show_partnerShopStar_select";
    }

    public String isCodeAndNameExits() {

        try {
            PartnerExistsReq partnerExistsReq = new PartnerExistsReq();
            if (!StringUtil.isEmpty(part_partner_code)) {
                try {
                    part_partner_code = URLDecoder.decode(part_partner_code, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                partnerExistsReq.setPartner_code(part_partner_code);
                boolean flag = false;
                PartnerExistsResp partnerExistsResp = partnerServ.isPartnerExits(partnerExistsReq);
                if (partnerExistsResp != null) {
                    flag = partnerExistsResp.getFlag();
                }
                if (flag) {
                    json = "{'result':2,'message':'编号已存在！'}";
                } else {
                    json = "{'result':0,'message':'可以添加！'}";
                }
            } else if (!StringUtil.isEmpty(part_partner_name)) {
                try {
                    part_partner_name = URLDecoder.decode(part_partner_name, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                partnerExistsReq.setPartner_name(part_partner_name);
                boolean flag = false;
                PartnerExistsResp partnerExistsResp = partnerServ.isPartnerExits(partnerExistsReq);
                if (partnerExistsResp != null) {
                    flag = partnerExistsResp.getFlag();
                }
                if (flag) {
                    json = "{'result':2,'message':'分销商名称已存在！'}";
                } else {
                    json = "{'result':0,'message':'可以添加！'}";
                }
            }
        } catch (RuntimeException e) {
            json = "{'result':1,'message':'操作失败！'}";
            e.printStackTrace();
        }
        return WWAction.JSON_MESSAGE;
    }


    public String getPart_partner_code() {
        return part_partner_code;
    }

    public void setPart_partner_code(String part_partner_code) {
        this.part_partner_code = part_partner_code;
    }

    public String getPart_partner_name() {
        return part_partner_name;
    }

    public void setPart_partner_name(String part_partner_name) {
        this.part_partner_name = part_partner_name;
    }

    public GoodsInf getGoodsServ() {
        return goodsServ;
    }

    public void setGoodsServ(GoodsInf goodsServ) {
        this.goodsServ = goodsServ;
    }

    public List getPartnerTypeList() {
        return partnerTypeList;
    }

    public void setPartnerTypeList(List partnerTypeList) {
        this.partnerTypeList = partnerTypeList;
    }

    public List getPartnerLeveList() {
        return partnerLeveList;
    }

    public void setPartnerLeveList(List partnerLeveList) {
        this.partnerLeveList = partnerLeveList;
    }

    public List getPartnerCatList() {
        return partnerCatList;
    }

    public void setPartnerCatList(List partnerCatList) {
        this.partnerCatList = partnerCatList;
    }

    public List getPartnerShopTypeList() {
        return partnerShopTypeList;
    }

    public void setPartnerShopTypeList(List partnerShopTypeList) {
        this.partnerShopTypeList = partnerShopTypeList;
    }

    public List getPartnerShopStarList() {
        return partnerShopStarList;
    }

    public void setPartnerShopStarList(List partnerShopStarList) {
        this.partnerShopStarList = partnerShopStarList;
    }

    // 商品列表
    public String list() {
        this.webpage = agentManager.searchAgents(username, state, order, this
                .getPage(), this.getPageSize());
        return "list";
    }

    // 删除商户
    public String del() {
        agentManager.delAgentById(agent_id);
        staff_no = this.getRequest().getParameter("staff_no");
        AdminUserDisableReq adminUserDisableReq = new AdminUserDisableReq();
        adminUserDisableReq.setUser_id(staff_no);

        adminUserServ.disableAdminUser(adminUserDisableReq);
        goodsServ.delGoodsByStaffNo(staff_no);
        DelAdvReq delAdvReq = new DelAdvReq();
        delAdvReq.setStaff_no(staff_no);
        this.advService.delAdvbyStaffNo(delAdvReq);
        DelArticleReq articleReq = new DelArticleReq();
        articleReq.setStaff_no(staff_no);
        this.articleService.delArticleByStaffNo(articleReq);
        return list();
    }

    // 关联工号
    public String getstaff() {
        this.webpage = agentManager.searchStaff(staff_no, staff_name, this
                .getPage(), this.getPageSize());
        return "show_staff";
    }


    public static final String GOODS_ADD_PAGE_ID = "goods_add";

    /**
     * 到商户申请页面
     */
    public String edit() {
        // 先将商品更新为下架
        actionName = "agent!saveEdit.do";
        agentView = this.agentManager.getAgentById(agent_id);
        return Action.INPUT;
    }

    public String edit_staff() { // 商户关联工号
        // 先将商品更新为下架
        actionName = "agent!saveEdit.do";
        agentView = this.agentManager.getAgentById(agent_id);
        return "input_staff";
    }

    public ICacheUtil getCacheUtil() {
        return cacheUtil;
    }

    public void setCacheUtil(ICacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    public String saveEdit() {
        try {
            agentManager.edit(agent);
            this.msgs.add("处理成功");
            this.urls.put("商户列表", "agent!list.do");

        } catch (RuntimeException e) {
            e.printStackTrace();
            this.msgs.add(e.getMessage());
        }
        return WWAction.MESSAGE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getIc_card() {
        return ic_card;
    }

    public Agent getAgentSearch() {
        return agentSearch;
    }

    public void setAgentSearch(Agent agentSearch) {
        this.agentSearch = agentSearch;
    }

    public String getStaff_no() {
        return staff_no;
    }

    public void setStaff_no(String staff_no) {
        this.staff_no = staff_no;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public void setIc_card(String ic_card) {
        this.ic_card = ic_card;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Map getAgentView() {
        return agentView;
    }

    public void setAgentView(Map agentView) {
        this.agentView = agentView;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public IAgentManager getAgentManager() {
        return agentManager;
    }

    public void setAgentManager(IAgentManager agentManager) {
        this.agentManager = agentManager;
    }

    public static String getGOODS_ADD_PAGE_ID() {
        return GOODS_ADD_PAGE_ID;
    }

    public List getAgentList() {
        return agentList;
    }

    public void setAgentList(List agentList) {
        this.agentList = agentList;
    }


    public AdminUserInf getAdminUserServ() {
        return adminUserServ;
    }

    public void setAdminUserServ(AdminUserInf adminUserServ) {
        this.adminUserServ = adminUserServ;
    }

    public PartnerInf getPartnerServ() {
        return partnerServ;
    }

    public void setPartnerServ(PartnerInf partnerServ) {
        this.partnerServ = partnerServ;
    }


}
