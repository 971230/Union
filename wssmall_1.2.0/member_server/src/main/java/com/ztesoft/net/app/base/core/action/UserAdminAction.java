package com.ztesoft.net.app.base.core.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import params.adminuser.req.AdminAddReq;
import params.adminuser.req.AdminBossUpdateReq;
import params.adminuser.req.AdminUserCleanReq;
import params.adminuser.req.AdminUserCountByIdReq;
import params.adminuser.req.AdminUserCountReq;
import params.adminuser.req.AdminUserDelReq;
import params.adminuser.req.AdminUserGetReq;
import params.adminuser.req.AdminUserListReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.req.OrgInfoReq;
import params.adminuser.req.RoleListReq;
import params.adminuser.req.RolePageReq;
import params.adminuser.req.RoleReq;
import params.adminuser.req.StaffInfoReq;
import params.adminuser.req.UserFounderReq;
import params.adminuser.req.UserTypeListReq;
import params.adminuser.resp.AdminUserCountResp;
import params.adminuser.resp.AdminUserEditResp;
import params.adminuser.resp.AdminUserPageResp;
import params.adminuser.resp.AdminUserResp;
import params.adminuser.resp.OrgInfoResp;
import params.adminuser.resp.RoleListResp;
import params.adminuser.resp.RolePageResp;
import params.adminuser.resp.StaffInfoResp;
import params.adminuser.resp.UserFounderResp;
import params.adminuser.resp.UserTypeListResp;
import params.member.req.AgentReq;
import params.member.resp.AgentResp;
import params.orgAdmin.req.OrgAdminListReq;
import params.orgAdmin.req.OrgAdminReq;
import params.req.PartnerPageReq;
import params.resp.PartnerPageResp;
import params.suppler.req.SuppliersListReq;
import params.suppler.resp.SuppliersListResp;
import services.AdminUserInf;
import services.AgentInf;
import services.OrgAdminInf;
import services.PartnerInf;
import services.PermissionInf;
import services.RoleInf;
import services.SupplierInf;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.User;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.resource.IOrgManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Lan;
import com.ztesoft.net.mall.core.service.ILanManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SqlBuilder;
import com.ztesoft.net.sqls.SqlLikeBuilder;

import commons.CommonTools;
import edu.emory.mathcs.backport.java.util.Arrays;

import com.ztesoft.net.app.base.core.model.Role;

/**
 * 站点管理
 *
 * @author kingapex 2010-11-5下午04:28:22新增角色管理
 */
public class UserAdminAction extends WWAction {

    private AdminUserInf adminUserServ;

    private RoleInf roleServ;

    private PermissionInf permissionServ;

    private ILanManager lanManager;

    private IOrgManager orgManager;

    private AdminUser adminUser;

    private String id;

    private List roleList;
    
    private List<Map> listMapRoleGroup;

    private List lanList;

    private List orgList;

    private List userRoles;

    private String[] roleids;
   
    private String[] selroleids;
    private List userList;

    private String newPassword; // 新密码

    private String updatePwd;// 是否修改密码

    private int multiSite;

    private String currUserId;// 当前用户id

    private Integer currFounder;// 当前用户类型

    private String userNameKey;
    private String realNameKey;
    private String partner_code;
    private int limitFailCount;

    private String username;// 获取查询的用户名
    private String userid;// 查询的Id
    private String strstate;// 查询的启用状态
    private String realname;
    private PartnerInf partnerServ;
    private OrgAdminInf orgAdminInf;
	private int usertype;// 区分的类型
    private String state;
    private String provice_lanId;
    private String cur_lanName;
    private String relcode;
    private String partner_phoneNo;
    private String usercode_staff;
    private String username_staff;
    private String user_no;// 修改前的工号

    //add by hu.yi at 2013.5.19
    private String lan_id;    //本地网id
    private String lan_name;
    private String org_id;
    private String parent_id;
    private String level;
    private List<Lan> staffLanList;    //查询时用的本地网
    private String userType;    //页面上带过来的员工类型--电信员工、一级二级分销商等
    private String dept_name;
    private String party_id;//Staff部门Id
    private String staff_id;
    private String staff_name;
    private String org_name;
    private String org_type_id;
    private String org_content;
    private String union_org_code;

    private String userState;
    private Map<String,String>  bossPwdMap;
    private Map typeMap;
    private ICacheUtil cacheUtil;
    
    private AgentInf agentServ;
    private SupplierInf supplierServ;
    private OrgAdminInf orgAdminServ;
    private Page rolePage;
    private String role_name;
    private String role_code;
    private String auth_type;
    private List typeList;
    
    private String supplier_name;
    
    private String source_from;
    private String prov;
    
    private String city_id;
    private String city;
    
    private String sms_receive;
    
	public String orgUserSave() throws Exception {
      /*  String relCode = this.adminUser.getRelcode();

        AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
        adminUserCountReq.setRelCode(relCode);
        
        AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
        adminUserCountResp = this.adminUserServ.getUserCount(adminUserCountReq);
       
        int count = 0;
        if(adminUserCountResp != null){
        	count = adminUserCountResp.getCount();
        }
        if (count == 0 && StringUtils.isEmpty(adminUser.getUserid())) {
        	AdminUserFillReq adminUserFillReq = new AdminUserFillReq();
        	adminUserFillReq.setAdminUser(this.adminUser);
            AdminUserResp adminUserResp = adminUserServ.fillAdminUser(adminUserFillReq);
            this.adminUser = new AdminUser();
			if(adminUserResp != null){
				this.adminUser = adminUserResp.getAdminUser();
			}
            return this.addSave();
        } else {*/
		 AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
         adminUserCountReq.setUsername(this.adminUser.getUsername());
         
         AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
         adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
         
         int count = 0;
         if(adminUserCountResp != null){
         	count = adminUserCountResp.getCount();
         }
         if (StringUtils.isNotEmpty(adminUser.getUserid())) {
                	if (!this.user_no.equals(this.adminUser.getUsername())
                              && count > 0) {
                          this.json = "{result:1,message:'该工号已被占用，请换一个工号'}";
                          return WWAction.JSON_MESSAGE;
                    }
                    if (updatePwd != null&&!"".equals(updatePwd)) {
                        adminUser.setPassword(updatePwd);
                    }
                    adminUser.setRoleids(roleids);
                    String userId = adminUser.getUserid();
                    AdminUserReq adminUserReq = new AdminUserReq();
                	adminUserReq.setAdminUser(adminUser);
                    this.adminUserServ.edit(adminUserReq);
                    this.json = "{result:0,message:'操作成功',org_id:'" + org_id + "',dept_name:'" + dept_name + "',id:'" + userId + "'}";

                } else {
                	adminUser.setUserid(adminUser.getUsername());
                	return this.addSave();
                }

            return WWAction.JSON_MESSAGE;

    }

    public String getStaffList() {//boss的分销商列表
    	StaffInfoReq staffInfoReq = new StaffInfoReq();
    	staffInfoReq.setParty_id(party_id);
    	staffInfoReq.setStaff_id(staff_id);
    	staffInfoReq.setStaff_name(staff_name);
    	staffInfoReq.setPage(String.valueOf(this.getPage()));
    	staffInfoReq.setPageSize(String.valueOf(this.getPageSize()));
    	
    	StaffInfoResp staffInfoResp = new StaffInfoResp();
    	staffInfoResp = this.adminUserServ.getStaffList(staffInfoReq);
    	
        this.webpage = new Page();
        if(staffInfoResp != null){
        	this.webpage = staffInfoResp.getWebPage();
        }
        return "showStaffList";
    }

    public String getOrg() {
        return "show_user_org";
    }

    public String getCurrOrg() throws Exception {

        Map map = new HashMap();
        OrgAdminReq orgAdminReq = new OrgAdminReq();
        String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
        List org_list = new ArrayList();
        if (StringUtils.isEmpty(login_org_id)) return "";

        if ("1".equals(CommonTools.getUserId())) {
            org_list = orgAdminServ.getRootOrg().getRootOrgList();
        } else {
        	orgAdminReq.setLogin_org_id(login_org_id);
            org_list = orgAdminServ.getCurrOrg(orgAdminReq).getCurrOrgList();
        }
        if (!ListUtil.isEmpty(org_list)) {
            for (int i = 0; i < org_list.size(); i++) {
                Map o_map = (Map) org_list.get(i);
                String code = Const.getStrValue(o_map, "party_id");
                
                orgAdminReq.setOrg_id(code);
                int cnt = orgAdminServ.qryChildrenOrgCount(orgAdminReq).getChildrenOrgCount();
                if (cnt > 0) {
                    o_map.put("state", "closed");
                } else {
                    o_map.put("state", "open");
                }
            }
        }
        map.put("total", org_list.size());
        map.put("rows", org_list);
        this.json = JSON.toJSONString(map);
        return JSON_MESSAGE;
    }

    public String getChildrenOrg() throws Exception {
    	OrgAdminReq orgAdminReq = new OrgAdminReq();
        if (StringUtils.isEmpty(org_id)) return "error";
        orgAdminReq.setOrg_id(org_id);
        List children_list = orgAdminServ.getChildrenOrg(orgAdminReq).getChildrenOrgList();

        if (!ListUtil.isEmpty(children_list)) {
            for (int i = 0; i < children_list.size(); i++) {
                Map o_map = (Map) children_list.get(i);
                String code = Const.getStrValue(o_map, "party_id");
                orgAdminReq.setOrg_id(code);
                int cnt = orgAdminServ.qryChildrenOrgCount(orgAdminReq).getChildrenOrgCount();
                if (cnt > 0) {
                    o_map.put("state", "closed");
                } else {
                    o_map.put("state", "open");
                }
            }
        }
        this.json= JSON.toJSONString(children_list);
        return WWAction.JSON_MESSAGE;
    }

    public String orgUserList() throws UnsupportedEncodingException {
        //this.org_id ="1000000020";
    	UserFounderResp userFounderResp = new UserFounderResp();
    	UserFounderReq userFounderReq = new UserFounderReq();
   	 	userFounderResp = this.adminUserServ.founderMap(userFounderReq);
   	 
   	 	if(this.realname!=null&&!"".equals(realname)){
   	 	  this.realname =  java.net.URLDecoder.decode(realname,"utf-8"); 
   	 	}
   	 	if(this.username!=null&&!"".equals(this.username)){
   	 	   this.username =  java.net.URLDecoder.decode(username,"utf-8"); 
   	 	}
   	 	
	   	this.currFounder = ManagerUtils.getFounder();
	    this.currUserId = ManagerUtils.getUserId();
	      //this.cacheUtil.getConfigInfo("LIMIT_COUNT")获取不到会异常
	    Integer limitCount = Integer.parseInt("".equals(this.cacheUtil.getConfigInfo("LIMIT_COUNT"))?"0":this.cacheUtil.getConfigInfo("LIMIT_COUNT"));
	    this.limitFailCount = new Integer(limitCount).intValue();
	      
    	OrgInfoReq orgInfoReq = new OrgInfoReq();
    	orgInfoReq.setOrg_id(org_id);
    	orgInfoReq.setState(state);
    	orgInfoReq.setRealname(realname);
    	orgInfoReq.setUsername(username);
    	orgInfoReq.setPageNo(String.valueOf(this.getPage()));
    	orgInfoReq.setPageSize("4");
    	
    	OrgInfoResp orgInfoResp = new OrgInfoResp();
    	orgInfoResp = this.adminUserServ.orgUserList(orgInfoReq);
    	
        this.webpage = new Page();
        if(orgInfoResp != null){
        	this.webpage = orgInfoResp.getWebPage();
        }

        return "orgUserList";
    }
   
    public String showOrgList() {
        this.state = "2";
        lanList = lanManager.listLan();
        source_from=ManagerUtils.getSourceFrom();
        return "showOrgList";
    }
    public String  roleList(){
    	RolePageReq rolePageReq = new RolePageReq();
    	rolePageReq.setPageNo(this.getPage());
    	rolePageReq.setPageSize(this.getPageSize());
    	rolePageReq.setRole_code(this.role_code);
    	rolePageReq.setRole_name(role_name);
    	rolePageReq.setAuth_type(this.auth_type);
    	RolePageResp rolePageResp = new RolePageResp();
    	rolePageResp = this.roleServ.rolePage(rolePageReq);
    	this.rolePage = rolePageResp.getWebPage();
    	return "role_list";
    }
    public String initAdd(){
    	UserTypeListReq  userTypeListReq = new UserTypeListReq();
    	UserTypeListResp userTypeListResp = new UserTypeListResp();
    	userTypeListResp = this.adminUserServ.qryUserTypeList(userTypeListReq);
    	this.typeList = userTypeListResp.getList();
    	this.json =JSON.toJSONString(typeList);
    	return WWAction.JSON_MESSAGE;
    }
    public String editInit(){
    	 RoleReq roleReq = new RoleReq();
         roleReq.setRoleid(id);
         
         RoleListResp roleResp = new RoleListResp();
         roleResp = permissionServ.getUserRoles(roleReq);
         this.userRoles = new ArrayList();
         if(roleResp != null){
         	this.userRoles = roleResp.getList();
         }
    	this.json = JSON.toJSONString(userRoles);
    	return WWAction.JSON_MESSAGE;
    }
    public String userDetail() {
        this.provice_lanId = Consts.LAN_PROV;
        lanList = lanManager.listLan();
        this.currFounder = ManagerUtils.getFounder();
        if (id.equals(ManagerUtils.getUserId()) && currFounder.intValue() == Consts.CURR_FOUNDER3) {
            roleList = null;  //一级代理商查看自己详情时候。 不显示角色
        } else {
        	RoleListResp roleResp = new RoleListResp();
        	RoleListReq roleListReq = new RoleListReq();
        	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
        	roleList = new ArrayList();
        	if(roleResp != null){
        		roleList = roleResp.getRoleList();
        	}
        }

        RoleReq roleReq = new RoleReq();
        roleReq.setRoleid(id);
        
        RoleListResp roleResp = new RoleListResp();
        roleResp = permissionServ.getUserRoles(roleReq);
        this.userRoles = new ArrayList();
        if(roleResp != null){
        	this.userRoles = roleResp.getList();
        }
        
        AdminUserGetReq adminUserGetReq = new AdminUserGetReq();
        adminUserGetReq.setUser_id(id);
        
        AdminUserResp adminUserResp = adminUserServ.getUser(adminUserGetReq);
        this.adminUser = new AdminUser();
		if(adminUserResp != null){
			this.adminUser = adminUserResp.getAdminUser();
		}
        if(adminUser == null) adminUser = new AdminUser();
        this.cur_lanName = this.lanManager
                .getLanNameByID(adminUser.getLan_id());
        if (Consts.LAN_PROV.equals(adminUser.getLan_id())) {
            this.cur_lanName = "全省";
        }

        return "userDetail";
    }

    public String addOrgUser() {

        //this.provice_lanId = Consts.LAN_PROV;
    	RoleListResp roleResp = new RoleListResp();
    	RoleListReq roleListReq = new RoleListReq();
    	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
    	roleList = new ArrayList();
    	if(roleResp != null){
    		roleList = roleResp.getRoleList();
    	}
        lanList = lanManager.listLan();
        AdminUser adminUser = ManagerUtils.getAdminUser();
        currUserId = adminUser.getUserid();
        currFounder = adminUser.getFounder();
        return "addOrgUser";

    }

    @Override
	public String execute() {
    	UserFounderResp userFounderResp = new UserFounderResp();
    	UserFounderReq userFounderReq = new UserFounderReq();
   	 	userFounderResp = this.adminUserServ.founderMap(userFounderReq);
   	 
   	 	typeMap = new HashMap<String, String>();
   	 	if(userFounderResp != null){
   	 		typeMap = userFounderResp.getMap();
   	 	}
   	 	
        this.currFounder = ManagerUtils.getFounder();
        this.currUserId = ManagerUtils.getUserId();
        
        //this.cacheUtil.getConfigInfo("LIMIT_COUNT")获取不到会异常
        Integer limitCount = Integer.parseInt("".equals(this.cacheUtil.getConfigInfo("LIMIT_COUNT"))?"0":this.cacheUtil.getConfigInfo("LIMIT_COUNT"));
        this.limitFailCount = new Integer(limitCount).intValue();
        
        AdminUserListReq adminUserListReq = new AdminUserListReq();
        adminUserListReq.setUsertype(usertype);
        adminUserListReq.setRealname(realname);
        adminUserListReq.setName(username);
        adminUserListReq.setUserid(userid);
        adminUserListReq.setState(state);
        adminUserListReq.setPageNo(String.valueOf(this.getPage()));
        adminUserListReq.setPageSize(String.valueOf(this.getPageSize()));
        
        //传入地市条件
        adminUserListReq.setLan_id(this.lan_id);
        
        AdminUserPageResp adminUserPageResp = new AdminUserPageResp();
        adminUserPageResp = this.adminUserServ.list(adminUserListReq); 
        
        this.webpage = new Page();
        if(adminUserPageResp != null){
        	this.webpage = adminUserPageResp.getWebPage();
        }
        return SUCCESS;
    }
    
    public String llkjUserList(){
    	this.execute();
    	return "llkjUserList";
    }
    //管理供应商
    public String getSupplierFind(){
    	SuppliersListReq suppliersListReq = new SuppliersListReq();
    	suppliersListReq.setPage_index(this.getPage()+"");
    	suppliersListReq.setPage_size(this.getPageSize()+"");
    	if(supplier_name==null){
    		supplier_name="";
    	}
    	suppliersListReq.setSuppler_name(supplier_name);
    	
    	suppliersListReq.setPhone_num("");
    	SuppliersListResp suppliersListResp = new SuppliersListResp();
    	suppliersListResp = this.supplierServ.supplierList(suppliersListReq);  
    	this.webpage = suppliersListResp.getWebPage();
    	return "supplierList";
    }
    // 关联分销商 有查找功能
    public String getAgentFind() {

        Map map = new HashMap();
        map.put("userNameKey", userNameKey);
        map.put("realNameKey", realNameKey);
        map.put("partner_code", partner_code);

        PartnerPageReq partnerPageReq = new PartnerPageReq();
        partnerPageReq.setMap(map);
        partnerPageReq.setPage(this.getPage());
        partnerPageReq.setPageSize(this.getPageSize());
        
        PartnerPageResp partnerPageResp = partnerServ.searchPartner(partnerPageReq);
        
        this.webpage = new Page();
        if(partnerPageResp != null){
        	this.webpage = partnerPageResp.getWebPage();
        }
        // this.webpage=partnerManager.list(obj,"", page, pageSize)

        return "show_agent_find";
    }

    // 关联CRM 有查找功能
    public String getStaffFind() {
//		staffLanList = new ArrayList<Lan>();
//		if (ManagerUtils.isProvStaff()) {  // 省员工 
//			staffLanList.add(new Lan(Consts.LAN_PROV, "全省"));
//			staffLanList.addAll(lanManager.listLan());
//		} else if (ManagerUtils.isLanStaff()){ //本地员工
//			staffLanList.add(lanManager.getLanByID(ManagerUtils.getLanId()));
//		} 
        this.provice_lanId = Consts.LAN_PROV;
        lanList = lanManager.listLan();
        Map map = new HashMap();
        map.put("userCode", usercode_staff);
        map.put("userName", username_staff);
        map.put("lanId", lan_id);
        map.put("userType", userType);

        AgentReq req = new AgentReq();
        req.setInfoMap(map);
        req.setPage_index(this.getPage());
        req.setPage_size(this.getPageSize());
        
        AgentResp resp = agentServ.getStaffInfo(req);
        if(resp != null){
        	this.webpage = resp.getStaffInfoPage();
        }
        return "show_staff_find";
    }

    // 部门 有查找功能
    public String getOrgFind() {
        orgList = orgManager.getOrgList();
        return "show_org";
    }

    // 部门 有查找功能
    public String getOrgListByParentId() {
        orgList = orgManager.getOrgListByParentId(parent_id, level);
        return "show_org_unit";
    }

    public String cleanFailCount() {
        try{
    	AdminUserCleanReq adminUserCleanReq = new AdminUserCleanReq();
    	adminUserCleanReq.setUser_id(this.id);
    	
        adminUserServ.cleanFailCount(adminUserCleanReq);
        this.json = "{result:0,message:'操作成功'}";
        }catch(Exception e){
        	e.printStackTrace();
        	this.json = "{result:1,message:'操作失败："+e.getMessage()+"'}";
        }
        return WWAction.JSON_MESSAGE;
       // return "clear_FailCount";
    }

    /**
     * 添加员工
     * @return
     * @throws Exception
     */
    public String add() throws Exception {
    	UserFounderResp userFounderResp = new UserFounderResp();
    	UserFounderReq userFounderReq = new UserFounderReq();
   	 	userFounderResp = this.adminUserServ.founderMap(userFounderReq);
   	 
   	 	typeMap = new HashMap<String, String>();
   	 	if(userFounderResp != null){
   	 		typeMap = userFounderResp.getMap();
   	 	}
        multiSite = EopContext.getContext().getCurrentSite().getMulti_site();
        
        this.provice_lanId = Consts.LAN_PROV;
        
        RoleListResp roleResp = new RoleListResp();
        RoleListReq roleListReq = new RoleListReq();
    	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
    	
    	roleList = new ArrayList();
    	if(roleResp != null){
    		listMapRoleGroup = roleServ.getListRoleGroup(); //获取所有角色分组
    		roleList = roleResp.getRoleList();
    	}
    	
        lanList = lanManager.listLan();
        this.filterLan();
        
        AdminUser adminUser = ManagerUtils.getAdminUser();
        currUserId = adminUser.getUserid();
        currFounder = adminUser.getFounder();
        lan_name=adminUser.getLan_name();
        lan_id=adminUser.getLan_id();

        return "add";
    }
    public String addLLkjAgent() throws Exception{
    	this.add();
    	return "addLLkjPartner";
    }
    public String addSave() throws Exception {
        try {
            String password = this.adminUser.getPassword();
            
            if(StringUtils.isEmpty(newPassword)){
            	newPassword= this.adminUser.getPassword();
            }

            AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
            adminUserCountReq.setUsername(this.adminUser.getUsername());
            
            AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
            adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
            
            int count = 0;
            if(adminUserCountResp != null){
            	count = adminUserCountResp.getCount();
            }
            
            if (count > 0) {
                this.json = "{result:1,message:'该工号已被占用，请换一个工号'}";
                return WWAction.JSON_MESSAGE;
            } else {
                adminUser.setRoleids(roleids);
              //  this.adminUser.setRelcode(relcode);
                adminUser.setPassword(newPassword);
                AdminAddReq adminAddReq = new AdminAddReq();
                adminAddReq.setAdminUser(adminUser);
                adminUserServ.add(adminAddReq);
                int founder = this.adminUser.getFounder();
                if (this.partner_phoneNo != null
                        && !"".equals(this.partner_phoneNo)
                        && Consts.CURR_FOUNDER2 == this.adminUser.getFounder()) {
                    /*this.smsManager.addPartnerMsg(this.adminUser.getUsername(),
							password, this.partner_phoneNo);*/
                }

                this.json = "{result:0,message:'添加成功',org_id:'" + adminUser.getOrg_id()+ "',dept_name:'" + adminUser.getDep_name() + "'}";
            }
        } catch (RuntimeException e) {
            this.json = "{result:1,message:'添加失败："+e.getMessage()+"'}";
        }
        return WWAction.JSON_MESSAGE;

    }
    
    public String editLLkjAgent() throws Exception{
    	this.edit();
    	return "editLLkjPartner";
    }
    
    
    /**
     * 过滤管理员角色
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void filterManagerRoles() throws Exception{
    	AdminUser user = ManagerUtils.getAdminUser();
    	
    	if(!"1".equals(user.getUserid()) && this.roleList!=null && this.roleList.size()>0){
    		//不是超级管理员，过滤管理员角色
    		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    		String MANAGER_ROLES = cacheUtil.getConfigInfo("MANAGER_ROLES");
    		
    		if(StringUtils.isNotBlank(MANAGER_ROLES)){
    			String[] arr = MANAGER_ROLES.split(",");
    			
    			if(arr!=null && arr.length>0){
    				List<String> list = Arrays.asList(arr);
    				
    				Set<String> manageRoleSet = new HashSet<String>(list);
    				
    				Iterator it = this.roleList.iterator();
    				for(;it.hasNext();){
    					Role role = (Role)it.next();
    					
    					if(manageRoleSet.contains(role.getRoleid())){
    						it.remove();
    					}
    				}
    			}
    		}
    	}
    }
    
    
    /**
     * 根据权限过滤地市
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	private void filterLan() throws Exception{
    	AdminUser user = ManagerUtils.getAdminUser();
    	
    	if(!"1".equals(user.getUserid()) && this.lanList!=null && this.lanList.size()>0){
    		//不是超级管理员，根据权限过滤地市
			Set<String> pRegionsSet = new HashSet<String>(user.getPermission_region());
			
			Iterator it = this.lanList.iterator();
			for(;it.hasNext();){
				com.ztesoft.net.app.base.core.model.Lan lan = (com.ztesoft.net.app.base.core.model.Lan)it.next();
				
				if(!pRegionsSet.contains(lan.getLan_id())){
					it.remove();
				}
			}
    	}
    }
    @SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	public String edit() throws Exception {
		UserFounderResp userFounderResp = new UserFounderResp();
		UserFounderReq userFounderReq = new UserFounderReq();
		userFounderResp = this.adminUserServ.founderMap(userFounderReq);

		typeMap = new HashMap<String, String>();
		if (userFounderResp != null) {
			typeMap = userFounderResp.getMap();
		}

		AdminUserCountByIdReq adminUserCountByIdReq = new AdminUserCountByIdReq();
		adminUserCountByIdReq.setUser_id(id);

			
   	 	//查询人员是否存在
    	AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
    	adminUserCountResp = this.adminUserServ.getCountById(adminUserCountByIdReq);
    	
        int count = 0;
        if(adminUserCountResp != null){
        	count = adminUserCountResp.getCount();
        }
        
        if (count == 0) {
            this.msgs.add("该用户已经删除,您不能再执行操作!");
            this.urls.put("用户列表", "userAdmin.do?state=2&usertype=1");
            
            return WWAction.MESSAGE;
        } else {
            multiSite = EopContext.getContext().getCurrentSite().getMulti_site();
            this.provice_lanId = Consts.LAN_PROV;
            
            //地市选项枚举值
            lanList = lanManager.listLan();
            this.filterLan();
            
            //查询可选角色
        	RoleListResp roleResp = new RoleListResp();
        	RoleListReq roleListReq = new RoleListReq();
        	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
        	roleList = new ArrayList();
        	if(roleResp != null){
        		listMapRoleGroup = roleServ.getListRoleGroup(); //获取所有角色分组
        		roleList = roleResp.getRoleList();
        	}
        	
        	//过滤管理员角色
        	this.filterManagerRoles();
        	
        	//查询用户已选角色
        	RoleReq roleReq = new RoleReq();
            roleReq.setRoleid(id);
        	RoleListResp userRoleRsp = new RoleListResp();
        	userRoleRsp = permissionServ.getUserRoles(roleReq);
            this.userRoles = new ArrayList();
            if(userRoleRsp != null){
            	this.userRoles = userRoleRsp.getList();
            }
            
            AdminUserReq adminUserReq = new AdminUserReq();
            adminUserReq.setUser_id(id);
            

            //查询用户地市信息
			AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
			this.adminUser = new AdminUser();
			if (adminUserResp != null) {
				this.adminUser = adminUserResp.getAdminUser();
			}

			this.cur_lanName = this.lanManager.getLanNameByID(adminUser.getLan_id());
			if (Consts.LAN_PROV.equals(adminUser.getLan_id())) {
				this.cur_lanName = "全省";
			}
			return "edit";
		}
	}


    public String editSave() throws Exception {
        try {

        	AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
        	adminUserCountReq.setUsername(this.adminUser.getUsername());
        	 AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
             adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
             
             int count = 0;
             if(adminUserCountResp != null){
             	count = adminUserCountResp.getCount();
             }
             
            if (!this.user_no.equals(this.adminUser.getUsername())
                    && count > 0) {

                this.json = "{result:1,message:'该工号已被占用，请换一个工号'}";

            } else {
                if (updatePwd != null) {
                    adminUser.setPassword(newPassword);
                }
                adminUser.setRoleids(roleids);
                String userId = adminUser.getUserid();
                AdminUserReq adminUserReq2 = new AdminUserReq();
                adminUserReq2.setAdminUser(adminUser);
                this.adminUserServ.edit(adminUserReq2);

                this.json = "{result:0,message:'修改成功',org_id:'" + org_id + "',dept_name:'" + dept_name + "',id:'" + userId + "'}";
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            this.logger.info(e, e.fillInStackTrace());
            this.json = "{result:1,message:'修改失败'}";

        }
        return WWAction.JSON_MESSAGE;

    }

    public String delete() throws Exception {
        try {
        	
        	AdminUserCountByIdReq adminUserCountByIdReq = new AdminUserCountByIdReq();
        	adminUserCountByIdReq.setUser_id(id);
        	
        	AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
        	adminUserCountResp = this.adminUserServ.getCountById(adminUserCountByIdReq);
        	
            int count = 0;
            if(adminUserCountResp != null){
            	count = adminUserCountResp.getCount();
            	if (count == 0) {
                    this.json = "{result:1,message:'该用户已经删除,您不能再执行此操作!'}";
                    return WWAction.JSON_MESSAGE;
                } 
            }
            	AdminUserDelReq adminUserDelReq = new AdminUserDelReq();
            	adminUserDelReq.setUser_id(id);
                this.adminUserServ.deleteAdminUsesr(adminUserDelReq);
                this.json = "{result:0,message:'删除成功'}";
            
        } catch (Throwable e) {
            this.json = "{result:1,message:'删除失败，"+e.getMessage()+"'}";
        }

        return WWAction.JSON_MESSAGE;
    }

	public String forwardBossPwdJsp(){
	  return "updateBossPwd";
	}
	/**
	 * 更改短信接口开关页面
	 * @return
	 */
	public String updateSmsReceiveForm(){
		adminUser = ManagerUtils.getAdminUser();
		return "updateSmsReceive";
	}
	
	public String updateSmsReceive() {
		try {
			if (!StringUtils.isEmpty(sms_receive)) {
				Map<String, Object> fieldsMap = new HashMap<String, Object>();
				Map<String, Object> whereMap = new HashMap<String, Object>();
				fieldsMap.put("sms_receive", sms_receive);
				adminUser = ManagerUtils.getAdminUser();
				whereMap.put("userid", adminUser.getUserid());
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.update("es_adminuser", fieldsMap, whereMap);
				adminUser.setSms_receive(sms_receive);
				WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
				sessonContext.setAttribute("admin_user_key", adminUser);
				this.json = "{result:0,message:'修改成功'}";
			}else{
				this.json = "{result:1,message:'修改失败，参数为空'}";
			}
		} catch (Exception e) {
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	public String forwardBossPwdFirstJsp(){
		  return "updateBossPwdFirst";
	}
	public String forwardResetBossPwdJsp(){
		if("zj".equals(this.prov)){
			return "resetBossPwdZj";
		}
		else{
			return "resetBossPwd";
		}
	}

	public String resetBossPwd(){
		if("zj".equals(this.prov)){
			return updateBossPwdZj();
		}
		else{
			return updateBossPwd();
		}
	}
	
	/*
	 * 修改boss工号的密码
	 */
	public String updateBossPwdZj(){
		try{
			
			AdminBossUpdateReq adminBossUpdateReq = new AdminBossUpdateReq();
			adminBossUpdateReq.setBossPwdMap(bossPwdMap);
			AdminUserEditResp adminUserEditResp = new AdminUserEditResp();
			adminUserEditResp = this.adminUserServ.updateBossPwdZj(adminBossUpdateReq);
			Map<String,Object> outMap = new HashMap<String, Object>();
			if(adminUserEditResp != null){
				outMap = adminUserEditResp.getOutMap();
			}
			String result =(String) outMap.get("result");
			if("1".equals(result)){
				this.json = "{result:0,message:'"+outMap.get("message")+"'}";
			}else if("2".equals(result)){
				this.json = "{result:2,message:'"+outMap.get("message")+"'}";
			}else{
				this.json = "{result:1,message:'"+outMap.get("message")+"'}";
			}
		}catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		  return WWAction.JSON_MESSAGE;
	}
	
	/*
	 * 修改boss工号的密码
	 */
	public String updateBossPwd(){
		try{
			
			AdminBossUpdateReq adminBossUpdateReq = new AdminBossUpdateReq();
			adminBossUpdateReq.setBossPwdMap(bossPwdMap);
			AdminUserEditResp adminUserEditResp = new AdminUserEditResp();
			adminUserEditResp = this.adminUserServ.updateBossPwd(adminBossUpdateReq);
			Map<String,Object> outMap = new HashMap<String, Object>();
			if(adminUserEditResp != null){
				outMap = adminUserEditResp.getOutMap();
			}
			String result =(String) outMap.get("result");
			if("1".equals(result)){
				this.json = "{result:0,message:'"+outMap.get("message")+"'}";
			}else if("2".equals(result)){
				this.json = "{result:2,message:'"+outMap.get("message")+"'}";
			}else{
				this.json = "{result:1,message:'"+outMap.get("message")+"'}";
			}
		}catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		  return WWAction.JSON_MESSAGE;
	}
	
	
    public String list() throws Exception {
    	OrgAdminListReq orgAdminListReq = new OrgAdminListReq();
        String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
        orgAdminListReq.setLogin_org_id(login_org_id);
        orgAdminListReq.setParty_id(party_id);
        orgAdminListReq.setOrg_name(org_name);
        orgAdminListReq.setOrg_type_id(org_type_id);
        orgAdminListReq.setOrg_content(org_content);
        orgAdminListReq.setUnion_org_code(union_org_code);
        orgAdminListReq.setLan_id(lan_id);
        orgAdminListReq.setPageIndex(this.getPage());
        orgAdminListReq.setPageSize(this.getPageSize());
        
        //地市选项枚举值
        this.lanList = lanManager.listLan();
        this.filterLan();
        
        webpage = orgAdminServ.list(orgAdminListReq).getPage();

        return "list";
    }

    public String showRole(){
    	 int founder = ManagerUtils.getFounder();
    	 if (id.equals(ManagerUtils.getUserId()) &&  Consts.CURR_FOUNDER3  ==founder) {
             roleList = null;  //一级代理商查看自己详情时候。 不显示角色
         }else {
        	RoleListResp roleResp = new RoleListResp();
        	RoleListReq roleListReq = new RoleListReq();
         	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
         	roleList = new ArrayList();
         	if(roleResp != null){
         		roleList = roleResp.getRoleList();
         	}
         }
    	 RoleReq roleReq = new RoleReq();
    	 roleReq.setRoleid(id);
    	 
    	 RoleListResp roleResp = new RoleListResp();
         roleResp = permissionServ.getUserRoles(roleReq);
         this.userRoles = new ArrayList();
         if(roleResp != null){
         	this.userRoles = roleResp.getList();
         }
    	 return "selRole";
    }
    public String saveRoles() {
    	try{
    		
    	 
    	 adminUser.setRoleids(selroleids);
         String userId = adminUser.getUserid();
         AdminUserReq adminUserReq = new AdminUserReq();
         adminUserReq.setAdminUser(adminUser);
         
         this.adminUserServ.edit(adminUserReq);
        
         this.json = "{result:0,message:'操作成功',userRoles:'"+userRoles+"'}";
       
    	}catch (RuntimeException e) {
            e.printStackTrace();
            this.logger.info(e, e.fillInStackTrace());
            this.json = "{result:1,message:'"+e.getMessage()+"'}";

        }
    	return WWAction.JSON_MESSAGE;
    }
    public String editPassword() throws Exception {
        return "editPassword";
    }

    public String getCustomDealerUserTable() throws Exception {
        User pojo = new User();
        
        if(StringUtils.isNotBlank(this.realname))
			this.realname = URLDecoder.decode(this.realname, "UTF-8");
        
        if(StringUtils.isNotBlank(this.username))
			this.username = URLDecoder.decode(this.username, "UTF-8");
        
        pojo.setLan_id(this.lan_id);
        pojo.setUserid(this.userid);
        pojo.setUsername(this.username);
        pojo.setRealname(this.realname);
        pojo.setState("1");
        
        List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
        
        //名称模糊查询
		SqlLikeBuilder userNameBuilder = new SqlLikeBuilder("realname", pojo.getRealname());
		sqlBuilds.add(userNameBuilder);
		
		//县分查询
		if(StringUtils.isNotBlank(this.city_id)){
			//获取营业县分
			ES_DC_PUBLIC_DICT_RELATION countyMap = this.orgAdminServ.getCountyMap(this.city_id);
			
			StringBuilder builder = new StringBuilder();
			builder.append(" ( ");
			
			builder.append(" org_id = '").append(this.city_id).append("' ");
			
			if(countyMap != null){
				builder.append(" or ");
				
				builder.append(" syn_county_id = '").append(countyMap.getOther_field_value()).append("' ");
			}
			
			builder.append(" ) ");
			
			SqlBuilder countyBuilder = new SqlBuilder(builder.toString(), "");
			
			sqlBuilds.add(countyBuilder);
		}
        
		this.webpage = this.adminUserServ.qryUserPageByPojo(this.getPage(), this.getPageSize(), pojo,sqlBuilds );
        
        return "custom_dealer_user_table";
    }

    public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public RoleInf getRoleServ() {
		return roleServ;
	}

	public void setRoleServ(RoleInf roleServ) {
		this.roleServ = roleServ;
	}

	public PermissionInf getPermissionServ() {
		return permissionServ;
	}

	public void setPermissionServ(PermissionInf permissionServ) {
		this.permissionServ = permissionServ;
	}

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public List getUserRoles() {
        return userRoles;
    }

    public String getUserNameKey() {
        return userNameKey;
    }

    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }

    public String getRealNameKey() {
        return realNameKey;
    }

    public void setRealNameKey(String realNameKey) {
        this.realNameKey = realNameKey;
    }


    public void setUserRoles(List userRoles) {
        this.userRoles = userRoles;
    }

    public String[] getRoleids() {
        return roleids;
    }

    public void setRoleids(String[] roleids) {
        this.roleids = roleids;
    }

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    public Integer getCurrFounder() {
        return currFounder;
    }

    public void setCurrFounder(Integer currFounder) {
        this.currFounder = currFounder;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUpdatePwd() {
        return updatePwd;
    }

    public void setUpdatePwd(String updatePwd) {
        this.updatePwd = updatePwd;
    }

    public int getMultiSite() {
        return multiSite;
    }

    public void setMultiSite(int multiSite) {
        this.multiSite = multiSite;
    }

    public ILanManager getLanManager() {
        return lanManager;
    }

    public void setLanManager(ILanManager lanManager) {
        this.lanManager = lanManager;
    }

    public List getLanList() {
        return lanList;
    }

    public void setLanList(List lanList) {
        this.lanList = lanList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStrstate() {
        return strstate;
    }

    public void setStrstate(String strstate) {
        this.strstate = strstate;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	public int getLimitFailCount() {
        return limitFailCount;
    }

    public void setLimitFailCount(int limitFailCount) {
        this.limitFailCount = limitFailCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getProvice_lanId() {
        return provice_lanId;
    }

    public void setProvice_lanId(String provice_lanId) {
        this.provice_lanId = provice_lanId;
    }

    public String getCur_lanName() {
        return cur_lanName;
    }

    public void setCur_lanName(String cur_lanName) {
        this.cur_lanName = cur_lanName;
    }

    public String getRelcode() {
        return relcode;
    }

    public void setRelcode(String relcode) {
        this.relcode = relcode;
    }

    public String getPartner_phoneNo() {
        return partner_phoneNo;
    }

    public void setPartner_phoneNo(String partner_phoneNo) {
        this.partner_phoneNo = partner_phoneNo;
    }

    public String getUsercode_staff() {
        return usercode_staff;
    }

    public void setUsercode_staff(String usercode_staff) {
        this.usercode_staff = usercode_staff;
    }

    public String getUsername_staff() {
        return username_staff;
    }

    public void setUsername_staff(String username_staff) {
        this.username_staff = username_staff;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public List<Lan> getStaffLanList() {
        return staffLanList;
    }

    public void setStaffLanList(List<Lan> staffLanList) {
        this.staffLanList = staffLanList;
    }

    public String getLan_id() {
        return lan_id;
    }

    public void setLan_id(String lan_id) {
        this.lan_id = lan_id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public IOrgManager getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(IOrgManager orgManager) {
        this.orgManager = orgManager;
    }

    public List getOrgList() {
        return orgList;
    }

    public void setOrgList(List orgList) {
        this.orgList = orgList;
    }
    
    public List<Map> getListMapRoleGroup() {
		return listMapRoleGroup;
	}

	public void setListMapRoleGroup(List<Map> listMapRoleGroup) {
		this.listMapRoleGroup = listMapRoleGroup;
	}

	public String getOrg_id() {
        return org_id;
    }
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_type_id() {
		return org_type_id;
	}
	public void setOrg_type_id(String org_type_id) {
		this.org_type_id = org_type_id;
	}
	public String getOrg_content() {
		return org_content;
	}
	public void setOrg_content(String org_content) {
		this.org_content = org_content;
	}
	public String getUnion_org_code() {
		return union_org_code;
	}
	public void setUnion_org_code(String union_org_code) {
		this.union_org_code = union_org_code;
	}

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

	public Map<String, String> getBossPwdMap() {
		return bossPwdMap;
	}

	public void setBossPwdMap(Map<String, String> bossPwdMap) {
		this.bossPwdMap = bossPwdMap;
	}

	public Map getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(Map typeMap) {
		this.typeMap = typeMap;
	}

	public String[] getSelroleids() {
		return selroleids;
	}

	public void setSelroleids(String[] selroleids) {
		this.selroleids = selroleids;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public AgentInf getAgentServ() {
		return agentServ;
	}

	public void setAgentServ(AgentInf agentServ) {
		this.agentServ = agentServ;
	}
	
	public OrgAdminInf getOrgAdminInf() {
		return orgAdminInf;
	}

	public void setOrgAdminInf(OrgAdminInf orgAdminInf) {
		this.orgAdminInf = orgAdminInf;
	}
	
	public OrgAdminInf getOrgAdminServ() {
		return orgAdminServ;
	}

	public void setOrgAdminServ(OrgAdminInf orgAdminServ) {
		this.orgAdminServ = orgAdminServ;
	}

	public Page getRolePage() {
		return rolePage;
	}

	public void setRolePage(Page rolePage) {
		this.rolePage = rolePage;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public SupplierInf getSupplierServ() {
		return supplierServ;
	}

	public void setSupplierServ(SupplierInf supplierServ) {
		this.supplierServ = supplierServ;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getLan_name() {
		return lan_name;
	}

	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public String getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	

	public String getSms_receive() {
		return sms_receive;
	}

	public void setSms_receive(String sms_receive) {
		this.sms_receive = sms_receive;
	}
	
}
