package com.ztesoft.net.app.base.core.service.auth.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.adminuser.req.AdminUserListReq;
import params.adminuser.req.SmsValidCodeGetReq;
import params.adminuser.req.UserPasswordUpdateReq;
import params.adminuser.resp.SmsValidCodeGetResp;
import params.adminuser.resp.UserPasswordUpdateResp;
import params.req.PartnerAddReq;
import params.req.PartnerLogOffReq;
import params.req.PartnerUserEditReq;
import params.resp.PartnerLogOffResp;
import services.PartnerInf;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.nd.vo.User;
import zte.params.order.req.SmsSendReq;
import zte.params.order.resp.SmsResp;
import zte.params.order.resp.SmsSendResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.MultiSite;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.SupplierStaff;
import com.ztesoft.net.app.base.core.service.auth.IAdminUserManager;
import com.ztesoft.net.app.base.core.service.auth.IPermissionManager;
import com.ztesoft.net.consts.MblConsts;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.Staff;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserContext;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.Operations;
import com.ztesoft.net.mall.core.service.ISmsCode;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.service.impl.LanManager;
import com.ztesoft.net.mall.core.util.SysUtils;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.sqls.SqlLikeBuilder;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.remote.pojo.AppInfo;

import commons.CommonTools;
import consts.ConstsCore;


/**
 * 管理员管理实现
 * 
 * @author kingapex 2010-11-5下午06:49:02
 */
public class AdminUserManagerImpl extends BaseSupport<AdminUser> implements
		IAdminUserManager {

	private IPermissionManager permissionManager;
	private PartnerInf partnerServ;
	private LanManager lanManager;
	private ICacheUtil cacheUtil;
	@Resource
	private ISmsCode smsCode;

	@Override
	public void clean() {
		this.baseDaoSupport.execute("truncate table adminuser");
	}

	@Override
	public String getAdminUserSequences() {
		String adminUserId = this.baseDaoSupport.getSequences("S_ES_ADMINUSER");
		return adminUserId;
	}

	
	//获取登录用户信息
	@Override
	@SuppressWarnings("unchecked")
	public Map getAgentSystemUser(String staff_code){
		Map result = null;
		if(SysUtils.isSecondPartner())
		{
			String parent_userid =SysUtils.getAdminUser().getParuserid();
			AdminUser pAdminUser = this.getAdminUserById(parent_userid);
			staff_code =pAdminUser.getRelcode();// 获取父级工号
		}
		
		String querySql ="select distinct sys.*,  org.union_org_code, org.org_code from es_system_user sys, es_staff s, es_organization org "
				+" where sys.staff_code = s.staff_code  and s.org_id = org.party_id and org.org_type_id = 8 "
				+" and sys.party_id = s.party_id  and s.status_cd = '00A' and org.lan_id = '731' and org.status_cd = '00A'"
				+" and sys.STAFF_CODE = ? "+ManagerUtils.apSFromCond("org")+ManagerUtils.apSFromCond("sys");
		
		List<Map> list = this.baseDaoSupport.queryForList(querySql,staff_code);
		if(null != list && !list.isEmpty()){
			if(list.size() == 1)
				result = list.get(0);
			else 
				throw new RuntimeException("工号信息配置错误");
		}
		return result;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String addSupplier(String adminUserId, String supplier_id,
			String account_number, String password, String phone,
			String company_name, String supplier_type) {

		AdminUser adminUser = new AdminUser();

		adminUser.setUserid(adminUserId);
		adminUser.setUsername(account_number);
		adminUser.setPhone_num(phone);
		adminUser.setRealname(company_name);
		adminUser.setPassword(StringUtil.md5(password));
		adminUser.setCreate_time(DBTUtil.current());
		adminUser.setCur_logintime(DBTUtil.current());
		adminUser.setLast_logintime(DBTUtil.current());
		adminUser.setFail_logincount(0);
		adminUser.setFail_logincount(0);
		
		//add by wui
		adminUser.setParuserid(adminUserId);
		adminUser.setRelno(supplier_id);
		
		
		
		
		adminUser.setFounder(Consts.CURR_FOUNDER4);
		adminUser.setState(Consts.user_state1);

		String roles = "";
		AdminUser user=this.getCurrentUser();
		
		boolean isSupplier = (user==null?true:((Consts.SUPPLIER_FOUNDER==user.getFounder())||(5==user.getFounder())));
		if(isSupplier){
			if (StringUtils.isNotEmpty(supplier_type)
					&& SupplierStatus.TYPE_1.equals(supplier_type)) {
				roles = getRolesToUser(Consts.DC_PUBLIC_STYPE_98763,
						Consts.DC_PUBLIC_98763_PKEY);
				adminUser.setReltype(Consts.REL_TYEP_SUPPLIER);
			} else if (StringUtils.isNotEmpty(supplier_type)
					&& SupplierStatus.TYPE_2.equals(supplier_type)) {
				roles = getRolesToUser(Consts.DC_PUBLIC_STYPE_98764,
						Consts.DC_PUBLIC_98764_PKEY);
				adminUser.setReltype(Consts.SUPPLIER_RELTYPE_DEALER);
			}
		}else {
			if (StringUtils.isNotEmpty(supplier_type)
					&& SupplierStatus.TYPE_1.equals(supplier_type)) {
				roles = getRolesToUser(Consts.DC_PUBLIC_STYPE_98762,
						Consts.DC_PUBLIC_98762_PKEY);
				adminUser.setReltype(Consts.REL_TYEP_SUPPLIER);
			} else if (StringUtils.isNotEmpty(supplier_type)
					&& SupplierStatus.TYPE_2.equals(supplier_type)) {
				roles = getRolesToUser(Consts.DC_PUBLIC_STYPE_98764,
						Consts.DC_PUBLIC_98764_PKEY);
				adminUser.setReltype(Consts.SUPPLIER_RELTYPE_DEALER);
			}
		}
		

		adminUser.setUserid(adminUserId);
		this.baseDaoSupport.insert("es_adminuser", adminUser);

		
		// 给用户赋予默认角色
		permissionManager.giveRolesToUser(adminUserId, new String[] { roles });
		return adminUserId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String bindingstaff(SupplierStaff supplierStaff) {

		AdminUser adminUser = new AdminUser();

		adminUser.setUsername(supplierStaff.getAccount_number());
		adminUser.setPhone_num(supplierStaff.getPhone());
		adminUser.setRealname(supplierStaff.getUser_name());
		adminUser.setPassword(StringUtil.md5(supplierStaff.getPassword()));
		adminUser.setCreate_time(DBTUtil.current());
		adminUser.setCur_logintime(DBTUtil.current());
		adminUser.setLast_logintime(DBTUtil.current());
		adminUser.setFail_logincount(0);
		//adminUser.setParuserid(supplierStaff.getSupplier_id());
		adminUser.setFail_logincount(0);
		adminUser.setFounder(Consts.CURR_FOUNDER5);
		adminUser.setState(Consts.user_state1);
		// 添加管理员
		// ManagerUtils mu = new ManagerUtils();
		// adminUser.setParuserid(mu.getUserId());
		
		
		//add by wui 关联字段设置
		String par_supper_id = this.baseDaoSupport.queryForString("select b.userid from es_supplier_staff a,es_supplier b where a.supplier_id = b.supplier_id and a.staff_id = '"+supplierStaff.getStaff_id()+"'");
		adminUser.setParuserid(par_supper_id);
		adminUser.setRelno(supplierStaff.getStaff_id());
		adminUser.setReltype(Consts.REL_TYEP_SUPPELIER_STAFF);
		
		
		String adminUserId = this.baseDaoSupport.getSequences("S_ES_ADMINUSER");

		adminUser.setUserid(adminUserId);
		this.baseDaoSupport.insert("es_adminuser", adminUser);

		String roles = getRolesToUser(Consts.DC_PUBLIC_STYPE_98761,
				Consts.DC_PUBLIC_98761_PKEY);
		// 给员工赋予默认角色
		permissionManager.giveRolesToUser(adminUserId, new String[] { roles });
		return adminUserId;
	}

	@Override
	public String getRolesToUser(Integer stype, String pkey) {
		String sql = "select p.pname value_desc from es_dc_public p where p.stype= ? and p.pkey = ?";
		Map<String, String> map = baseDaoSupport.queryForMap(sql, stype, pkey);
		if (!map.isEmpty())
			return map.get("value_desc");
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String add(AdminUser adminUser) {
		if(StringUtil.isEmpty(adminUser.getParuserid())){
			  adminUser.setParuserid(CommonTools.getUserId());
		}
		String checkResult =passwdCheck(adminUser.getPassword());
		if(!StringUtil.isEmpty(checkResult)){
			throw new RuntimeException(checkResult);
		}
		adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));
		adminUser.setCreate_time(DBTUtil.current());
		adminUser.setCur_logintime(DBTUtil.current());
		adminUser.setLast_logintime(DBTUtil.current());
		adminUser.setFail_logincount(0);
		adminUser.setSuccess_logincount(0);
		// 添加管理员
		if(StringUtils.isEmpty(adminUser.getParuserid())){
			ManagerUtils mu = new ManagerUtils();
			adminUser.setParuserid(ManagerUtils.getUserId());
		}
		//创建工号，标记为未登录过
        adminUser.setIs_login_first(EcsOrderConsts.IS_LOGIN_FIRST_0);
		this.baseDaoSupport.insert("es_adminuser", adminUser);
		String userid = adminUser.getUserid();
		int founder = adminUser.getFounder();
		String relcode  = adminUser.getRelcode();

		if (Consts.CURR_FOUNDER_3.equals(founder + "")) {// 为一级分销商
			// 新增一级分销商
			// /baocun
			String partner_id  = adminUser.getRelcode();
			
			if(partner_id==null||"".equals(partner_id)){
				Partner partner = new Partner();
				partner.setPartner_name(adminUser.getRealname());
				partner.setPartner_code(adminUser.getUsername());
				partner.setLegal_name(adminUser.getRealname());
				partner.setLinkman(adminUser.getRealname());
				partner.setPhone_no(adminUser.getPhone_num());
				partner.setLegal_phone_no(adminUser.getPhone_num());
				partner.setUserid(userid);
				
				PartnerAddReq partnerAddRep = new PartnerAddReq();
				partnerAddRep.setPartner(partner);
				this.partnerServ.addPartner(partnerAddRep);
				// --欢迎您成为中国电信互联网分销商用户，您的登录工号为{userno}，登录初始密码为{password}，轻松享受互联网分销服务！详咨10000.
			}else{
				
				String partnerID = adminUser.getRelcode();
				PartnerUserEditReq partnerUserEditRep = new PartnerUserEditReq();
				partnerUserEditRep.setPartner_id(partnerID);
				partnerUserEditRep.setUser_id(userid);
				
				this.partnerServ.updateUserid(partnerUserEditRep);

			}
		}
		if (Consts.CURR_FOUNDER_2.equals(founder + "")) {
			// 根据parentID对应分销商表ID更改userID更新二级分销商
			String partnerID = adminUser.getRelcode();

			PartnerUserEditReq partnerUserEditRep = new PartnerUserEditReq();
			partnerUserEditRep.setPartner_id(partnerID);
			partnerUserEditRep.setUser_id(userid);
			
			this.partnerServ.updateUserid(partnerUserEditRep);

		}

		// 给用户赋予角色
		permissionManager.giveRolesToUser(userid, adminUser.getRoleids());
		
		if(Consts.WSSMALL.equals(ManagerUtils.getSourceFrom())){//连连科技设置,统一平台在添加员工的时候,判断是是否单点登录,yes表示可以登录代理商 no 不可以
			if(Consts.LLKJ_WSSMALL_IS_LOGIN_AGENT_YES.equals(adminUser.getCol10())){
				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String roleidStr = cacheUtil.getConfigInfo(Consts.LLKJ_AGENT_ROLEID);
				String[] roleids=roleidStr.split(",");
				//roleids[0]="201403108626000291";
				
				this.baseDaoSupport.execute("delete from user_role where userid=? and source_from='LLKJ_AGENT'", userid);
				for(int i=0;i<roleids.length;i++){
					String roleid = roleids[i];
					this.baseDaoSupport.execute("insert into user_role(id,userid,roleid,source_from)values(?,?,?,?)", this.baseDaoSupport.getSeqByTableName("ES_USER_ROLE"),userid,roleid,"LLKJ_AGENT");
				}
			}
		}
		return userid;
	}

	/**
	 * 为某个站点添加管理员
	 * 
	 * @param userid
	 * @param siteid
	 * @param adminUser
	 * @return 添加的管理员id
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String add(String userid, String siteid, AdminUser adminUser) {
		adminUser.setState(1);
		this.baseDaoSupport.insert("adminuser", adminUser);
		return adminUser.getUserid();
	}

	@Override
	public int checkLast() {
		int count = this.baseDaoSupport
				.queryForInt("select count(0) from adminuser");
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		// 如果只有一个管理员，则抛出异常
		if (this.checkLast() == 1) {
			throw new RuntimeException("必须最少保留一个管理员");
		}

		// 清除用户角色
		permissionManager.cleanUserRoles(id);

		// 删除用户基本信息
		this.baseDaoSupport.execute("update es_adminuser  set state='0'  where userid=?",id); //delete from es_adminuser删除处理,逻辑删除
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAdminUsesr(String id) throws Exception {
		// 如果只有一个管理员，则抛出异常
		if (this.checkLast() == 1) {
			throw new RuntimeException("必须最少保留一个管理员");
		}
		
		AdminUser pojo = new AdminUser();
		pojo.setUserid(id);
		
		List<AdminUser> ret = this.baseDaoSupport.queryPojoList("es_adminuser", pojo , null);
		if(ret!=null && ret.size()==0)
			throw new Exception("该用户已经删除,您不能再执行此操作!");
		
		AdminUser user = ret.get(0);
		if("1".equals(user.getIs_syn()))
			throw new Exception("该工号为同步工号，不能删除！");

		// 清除用户角色
		permissionManager.cleanUserRoles(id);

		// 删除用户基本信息
		this.baseDaoSupport.execute("delete from es_adminuser  where userid=?",id); //delete from es_adminuser删除处理,逻辑删除
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(AdminUser adminUser) {

		// 给用户赋予角色
		permissionManager.giveRolesToUser(adminUser.getUserid(), adminUser
				.getRoleids());

		// 修改用户基本信息
		if (!StringUtil.isEmpty(adminUser.getPassword()))
			adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));
		String userId = adminUser.getUserid();

		adminUser.setUserid(null);
		this.baseDaoSupport.update("adminuser", adminUser, "userid='" + userId+"'");
	}

	@Override
	public AdminUser getAdminUserById(String userId) {
		String sql = "select a.* from es_adminuser a where a.userid = ?";
		return this.baseDaoSupport.queryForObject(sql, AdminUser.class, userId);
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newpassword
	 * @param userid
	 * @return 1-修改成功 0-旧密码错误
	 */
	@Override
	public int updatePassword(String oldPassword, String newpassword,
			String userid) {
		String sql = "select * from adminuser where userid=?";
		List<AdminUser> userList = this.baseDaoSupport.queryForList(sql,
				AdminUser.class, userid);
		AdminUser user = userList.get(0);
		if (!StringUtil.md5(oldPassword).equalsIgnoreCase(user.getPassword())) {
			return 0;
		}
		user.setPassword(StringUtil.md5(newpassword));
		String update_sql = "update es_adminuser set password=? where userid=? ";
		this.baseDaoSupport.execute(update_sql, user.getPassword(),userid);
		if(EcsOrderConsts.IS_LOGIN_FIRST_0.equals(user.getIs_login_first())){
			//用户标记为已登陆
			String update_is_first_sql = "update es_adminuser set is_login_first=? where userid=? ";
			this.baseDaoSupport.execute(update_is_first_sql, EcsOrderConsts.IS_LOGIN_FIRST_1,userid);
			return 2;
		}
		//this.baseDaoSupport.update("adminuser", user, "userid=" + userid);
		return 1;

	}
	
	
	/**
	 * 重置密码
	 * 
	 * @param phone
	 * @param newpassword
	 * @param username
	 * @return 1-修改成功 0-与工号绑定的手机号不符
	 */
	public int resetPassword(String phone, String newpassword,
			String username) {
		String sql = "select * from adminuser where username=?";
		List<AdminUser> userList = this.baseDaoSupport.queryForList(sql,
				AdminUser.class, username);
		AdminUser user = userList.get(0);
		if (!StringUtils.equals(phone, user.getPhone_num())) {
			return 0;
		}
		user.setPassword(StringUtil.md5(newpassword));
		String update_sql = "update es_adminuser set password=? where username=? ";
		this.baseDaoSupport.execute(update_sql, user.getPassword(),username);
		//用户标记为未登陆
		String update_is_first_sql = "update es_adminuser set is_login_first=? where username=? ";
		this.baseDaoSupport.execute(update_is_first_sql, EcsOrderConsts.IS_LOGIN_FIRST_0,username);
		return 1;

	}
	
	/**
	 * 禁用用户
	 * 
	 * @param user_id
	 */
	@Override
	public void disableAdminUser(String user_id) {
		if (user_id == null || "".equals(user_id)) {
			return;
		}
		String sql = "update adminuser set state=0 where userid=?";
		this.baseDaoSupport.execute(sql, user_id);
	}

	@Override
	public AdminUser get(String id) {
        List<AdminUser> adminUsers=this.daoSupport.queryForList( "select * from es_adminuser where userid=? and source_from is not null", AdminUser.class, id);
		if(!ListUtil.isEmpty(adminUsers))
			return adminUsers.get(0);
        return null;
	}

	@Override
	public List list() {
		ManagerUtils mu = new ManagerUtils();
		String userid = ManagerUtils.getUserId();
		if ("1".equals(userid)) {
			return this.baseDaoSupport.queryForList(
					"select * from adminuser where  state=0 order by dateline",
					AdminUser.class);
		} else if ("0".equals(userid))
			return this.baseDaoSupport
					.queryForList(
							"select * from adminuser where founder=3 and state=0 order by dateline ",
							AdminUser.class);
		else {
			return this.baseDaoSupport
					.queryForList(
							"select * from adminuser where founder=2 and state=0 order by dateline ",
							AdminUser.class);
		}
	}

	@Override
	public List<Map> list(Integer userid, Integer siteid) {
		String sql = "select * from es_adminuser_" + userid + "_" + siteid;
		return this.daoSupport.queryForList(sql);
	}

	/**
	 * 管理员登录
	 * 
	 * @param username
	 * @param password
	 *            未经过md5加密的密码
	 * @return 登录成功返回管理员
	 * @throws RuntimeException
	 *             当登录失败时抛出此异常，登录失败的原因可通过getMessage()方法获取
	 */
	@Override
	public String login(String username, String password) {
		return this.loginBySys(username, StringUtil.md5(password));
	}

	/**
	 * 系统登录
	 * 
	 * @param username
	 * @param password
	 *            此处为经过md5加密的密码
	 * @return 返回登录成功的用户id
	 * @throws RuntimeException
	 *             登录失败抛出此异常，登录失败原因可通过getMessage()方法获取
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String loginBySys(String username, String password) {
		boolean result =false;
		AdminUser user =null;
		String sql = "select * from adminuser where username=? and state='1' and source_from is not null"; 					//查询有效的
		List<AdminUser> userList = this.baseDaoSupport.queryForList(sql, AdminUser.class, username);
		if (userList == null || userList.size() == 0||StringUtil.isEmpty(username)||StringUtil.isEmpty(password)) {
			result=true;
		}else{
			user = userList.get(0);
		}
		// 判断登录失败的次数是否》=5 》=5将此用户冻结 不能登录系统
        String limit_cou=this.cacheUtil.getConfigInfo("LIMIT_COUNT");
		int limit_count =StringUtils.isNotBlank(limit_cou)?Integer.parseInt(limit_cou):5 ;
		if(user!=null){
			if (user.getFail_logincount() >= limit_count) {
				throw new RuntimeException("您已经连续" + limit_count
						+ "次密码错误，不可再登录此系统，请联系管理员解冻");
			}
			if (!password.equalsIgnoreCase(user.getPassword())) {
				// ++ 密码错误 登录失败次数 failCount +1
				this.updateCountAndLoginTime(Consts.loginFail_type, user);
				result=true;
			}
			if(result){
				throw new RuntimeException("用户名或密码错误！");
			}

			if (user.getState() == 0) {
				throw new RuntimeException("此用户已经被禁用");
			}
			
			 //操作成功后的逻辑，修改当前登录时间，和上次登录时间 ，登录次数1+ failCount少于5次 将 failCount 清零

			this.updateCountAndLoginTime(Consts.loginSuccess_type, user);

			EopSite site = EopContext.getContext().getCurrentSite();
			
			logger.info("AdminUserManagerImpl.loginBySys.site===>" + site);
			if (null == site) {
				logger.info("AdminUserManagerImpl.loginBySys.site is null .... ");
			}

			// 开启多站点功能
			if (site.getMulti_site() == 1) {
				if (user.getFounder() != 1) { // 非超级管理员检测是否是站点管理员
					MultiSite childSite = EopContext.getContext()
							.getCurrentChildSite();
					if (user.getSiteid() == null
							|| childSite.getSiteid().equals(user.getSiteid())) {
						throw new RuntimeException("非此站点管理员");
					}
				}
			}

			// 更新月登录次数和此站点的最后登录时间
			int logincount = site.getLogincount();
			long lastlogin = (site.getLastlogin()) * 1000;
			Date today = new Date();
			if (DateUtil.toString(new Date(lastlogin), "yyyy-MM").equals(
					DateUtil.toString(today, "yyyy-MM"))) {// 与上次登录在同一月内
				logincount++;
			} else {
				logincount = 1;
			}

			//
			site.setLogincount(logincount);
			site.setLastlogin(DateUtil.getDatelineLong());
			this.daoSupport.execute(
					"update eop_site set logincount=?, lastlogin=? where id=?",
					logincount, site.getLastlogin(), site.getId());

			// 查询导出excel的条数常量
			StringBuffer sql_excel = new StringBuffer();
			sql_excel.append("select codea from es_dc_public where stype = '8787'");
			String item = this.baseDaoSupport.queryForString(sql_excel.toString());

			// 记录session信息
			
			//换成真session方式的
			WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();

			UserContext userContext = new UserContext(site.getUserid(), site.getId(), user.getUserid());
			sessonContext.setAttribute(UserContext.CONTEXT_KEY, userContext);
			
			
			AppInfo appInfo = (AppInfo) sessonContext.getAttribute(MblConsts.CURRENT_APP_INFO);
			
			logger.info("AdminUserManagerImpl.appInfo===>" + appInfo);
			
			if(appInfo == null) {//设置登录平台主题风格
				user.setTheme_source_from("WSSMALL");
			}
			
			if (StringUtils.isEmpty(user.getTheme_source_from()) || "null".equals(user.getTheme_source_from())) {
				user.setTheme_source_from(appInfo.getSourceFrom());
			}
			
			this.getWorkCustomPermissions(user);
			
			//设置数据权限
			this.setUserDataAuth(user);
			sessonContext.setAttribute("admin_user_key", user);
			sessonContext.setAttribute("excel_items", item);
			
			if (user.getLan_id() != null && !"".equals(user.getLan_id())
					&& !Consts.LAN_PROV.equals(user.getLan_id())) {
				String lan_name = lanManager.getLanNameByID(user.getLan_id());
				sessonContext.setAttribute("lan_name", lan_name);
			}
			return user.getUserid();
		}else{
			throw new RuntimeException("用户名不存在");
		}
		
	}
	
	/**
	 * 营业县分转换为行政县分
	 * @param countySet
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Set<String> transBusiCounty2County(Set<String> countySet){
		Set<String> result = new HashSet<String>();
		
		List<String> list = new ArrayList<String>(countySet);
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT a.field_value FROM es_dc_public_dict_relation a WHERE a.stype='county' ");
		sqlBuilder.append(SqlUtil.getSqlInStr("a.other_field_value", list, false, true));
		
		List<Map> ret = this.baseDaoSupport.queryForList(sqlBuilder.toString());
		
		if(ret!=null && ret.size()>0){
			for(Iterator<Map> it = ret.iterator();it.hasNext();){
				Map item = it.next();
				
				String value = String.valueOf(item.get("field_value"));
				
				result.add(value);
			}
		}
		
		return result;
	}
	
	/**
	 * 根据营业县分获取地市
	 * @param countySet
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Set<String> getRegionByCounty(Set<String> countySet){
		Set<String> result = new HashSet<String>();
		
		List<String> list = new ArrayList<String>(countySet);
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT b.field_value FROM es_dc_public_dict_relation b ");
		sqlBuilder.append(" WHERE b.stype='region'  ");
		sqlBuilder.append(" and exists (  ");
		sqlBuilder.append(" SELECT a.countyid FROM es_busicty a WHERE a.countyid <> 'Z00' ");
		sqlBuilder.append(" and a.areaid=b.other_field_value ");
		sqlBuilder.append(SqlUtil.getSqlInStr("a.countyid", list, false, true));
		sqlBuilder.append(" ) ");
		
		List<Map> ret = this.baseDaoSupport.queryForList(sqlBuilder.toString());
		
		if(ret!=null && ret.size()>0){
			for(Iterator<Map> it = ret.iterator();it.hasNext();){
				Map item = it.next();
				
				String value = String.valueOf(item.get("field_value"));
				
				result.add(value);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取地市、县分权限
	 * @param user
	 */
	@SuppressWarnings("rawtypes")
	private void getWorkCustomPermissions(AdminUser user){
		//管理员供货或者权限控制开关关闭时直接赋予省级权限
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String flag = cacheUtil.getConfigInfo("PERMISSION_CONTROL_FLAG");
		
		if("1".equals(user.getUserid()) || 
				(!"1".equals(flag))){
			user.setPermission_level("1");
			return;
		}

		//设置自定义权限层级和权限地市、县分		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT c.order_region, c.busicty_id ");
		sqlBuilder.append("   FROM es_user_role a, es_role_auth b, es_role_data c ");
		sqlBuilder.append("    WHERE a.userid = '"+ user.getUserid() +"' ");
		sqlBuilder.append("     and a.roleid = b.roleid ");
		sqlBuilder.append("    and b.authid = c.id ");
		sqlBuilder.append("  and  c.source_from='ECS' ");
		
		String cfgPermissions = this.cacheUtil.getConfigInfo("WORK_CUSTOM_PERMISSIONS");
		
		if(StringUtils.isNotBlank(cfgPermissions)){
			sqlBuilder.append("    and c.id in ( ").append(cfgPermissions).append(") ");
		}
		
		List<Map> ret = this.baseDaoSupport.queryForList(sqlBuilder.toString());
		
		if(ret!=null && ret.size()>0){
			Set<String> regionSet = new HashSet<String>();
			Set<String> countySet = new HashSet<String>();
			
			for(int i=0;i<ret.size();i++){
				Map m = ret.get(i);
				
				String strRegion = String.valueOf(m.get("order_region"));
				String strCounty = String.valueOf(m.get("busicty_id"));
				
				if(StringUtils.isNotBlank(strRegion)){
					String[] regionArr = strRegion.split(",");
					
					if(regionArr!=null && regionArr.length>0){
						List<String> tempList = Arrays.asList(regionArr);
						
						regionSet.addAll(tempList);
					}
				}
				
				if(StringUtils.isNotBlank(strCounty)){
					String[] countyArr = strCounty.split(",");
					
					if(countyArr!=null && countyArr.length>0){
						List<String> tempList = Arrays.asList(countyArr);
						
						countySet.addAll(tempList);
					}
				}
			}
			
			if(regionSet.contains("330000")){
				//省级权限
				user.setPermission_level("1");
				return ;
			}else if(countySet.size() > 0){
				//县分权限
				user.setPermission_level("3");
				
				//权限营业县分
				List<String> perm_busi_county = new ArrayList<String>(countySet);
				user.setPerm_busi_county(perm_busi_county);
				
				//根据县分查询地市
				regionSet = this.getRegionByCounty(countySet);
				
				user.setPermission_region(new ArrayList<String>(regionSet));
				
				//权限行政县分
				countySet = this.transBusiCounty2County(countySet);
				
				user.setPermission_county(new ArrayList<String>(countySet));
				return;
			}else if(regionSet.size() > 0){
				//地市权限
				user.setPermission_level("2");
				
				user.setPermission_region(new ArrayList<String>(regionSet));
				return ;
			}
		}
	}
	
	/**
	 * 获取用户数据权限
	 * @param userid
	 */
	private void setUserDataAuth(AdminUser user){
		String sql = "select distinct a.flow_node,a.order_region,a.order_origin,a.sp_prod_type,a.order_model,a.ord_receive_user,a.ord_lock_user "+
				" from es_role_data a,es_role_auth b,es_role c,es_user_role d,es_adminuser e  "+
				"where a.id = b.authid and a.source_from=b.source_from "+
				"  and b.roleid=c.roleid and b.source_from=c.source_from "+
				"  and c.roleid=d.roleid and c.source_from=d.source_from "+
				"  and d.userid=e.userid and d.source_from=e.source_from and e.userid=? "
				+ "and e.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List<Map> authList = this.baseDaoSupport.queryForList(sql, user.getUserid());
		List tacheList = new ArrayList();
		List receiveUserList = new ArrayList();
		List lockUserList = new ArrayList();
		if(authList!=null && authList.size()>0){
			for(int i=0;i<authList.size();i++){
				Map authMap = authList.get(i);
				String tacheAuth = (String) authMap.get("flow_node");
				if(!StringUtils.isEmpty(tacheAuth)){
					String[] taches = tacheAuth.split(",");
					for(int j=0;j<taches.length;j++){
						String tache = taches[j];
						if(!StringUtils.isEmpty(tache) && !tacheList.contains(tache)){
							tacheList.add(tache);
						}
					}
				}
				String receiveUserAuth = (String) authMap.get("ord_receive_user");
				if(!StringUtils.isEmpty(receiveUserAuth)){
					String[] receiveUsers = receiveUserAuth.split(",");
					for(int j=0;j<receiveUsers.length;j++){
						String receiveUser = receiveUsers[j];
						if(!StringUtils.isEmpty(receiveUser) && !receiveUserList.contains(receiveUser)){
							receiveUserList.add(receiveUser);
						}
					}
				}
				String lockUserAuth = (String) authMap.get("ord_lock_user");
				if(!StringUtils.isEmpty(lockUserAuth)){
					String[] lockUsers = lockUserAuth.split(",");
					for(int j=0;j<lockUsers.length;j++){
						String lockUser = lockUsers[j];
						if(!StringUtils.isEmpty(lockUser) && !lockUserList.contains(lockUser)){
							lockUserList.add(lockUser);
						}
					}
				}
			}
			String tacheAuthStr = Const.getInWhereCond(tacheList);
			String receiveUserStr = Const.getInWhereCond(receiveUserList);
			String lockUserStr = Const.getInWhereCond(lockUserList);
			user.setTacheAuth(tacheAuthStr);
			user.setReceiveUserAuth(receiveUserStr);
			user.setLockUserAuth(lockUserStr);
		}
	}

	/*
	 * 按条件查询 员工分页
	 */

	@Override
	public Page list(AdminUserListReq adminUserListReq) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(" select * from es_adminuser a where a.source_from='ECS' ");
		
		if(adminUserListReq.getUsertype() != -1){
			builder.append(" and a.founder=");
			builder.append(adminUserListReq.getUsertype());
		}
		
		if(StringUtils.isNotBlank(adminUserListReq.getRealname())){
			builder.append(" and a.realname like '%");
			builder.append(adminUserListReq.getRealname());
			builder.append("%'");
		}
		
		if(StringUtils.isNotBlank(adminUserListReq.getName())){
			builder.append(" and a.username like '%");
			builder.append(adminUserListReq.getName());
			builder.append("%'");
		}
		
		if(StringUtils.isNotBlank(adminUserListReq.getUserid())){
			builder.append(" and a.userid ='");
			builder.append(adminUserListReq.getUserid());
			builder.append("'");
		}
		
		if(StringUtils.isNotBlank(adminUserListReq.getState()) && 
				!"2".equals(adminUserListReq.getState())){
			builder.append(" and a.state=");
			builder.append(adminUserListReq.getState());
		}
		
		if(StringUtils.isNotBlank(adminUserListReq.getLan_id())){
			builder.append(" and a.lan_id ='");
			builder.append(adminUserListReq.getLan_id());
			builder.append("'");
		}else{
			//没有传入地市，加上操作员的地市限制
			AdminUser user = ManagerUtils.getAdminUser();
			
			if(!"1".equals(user.getPermission_level())){
				builder.append(SqlUtil.getSqlInStr("a.lan_id", user.getPermission_region(), false, true));
			}
		}

		builder.append(" order by a.create_time desc");
		
		return this.daoSupport.queryForPage(builder.toString(), 
				Integer.valueOf(adminUserListReq.getPageNo()), Integer.valueOf(adminUserListReq.getPageSize()));
	}

	/**
	 * 查询分销商。电信查询一级分销商，一级查询自己和他所属二级分销商，二级分销商查询自己
	 */
	@Override
	public Page queryUser(AdminUser queryUser, AdminUser loginUser,
			Operations logic, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(
				"select * from es_adminuser t where 1=1 ");
		Map<String, Object> map = new HashMap<String, Object>();
		if (queryUser != null) {

			String username = queryUser.getUsername();
			String realname = queryUser.getRealname();
			boolean emptyUserName = StringUtil.isEmpty(username);
			boolean emptyRealName = StringUtil.isEmpty(realname);
			int countCondition = 0;
			if (!emptyUserName || !emptyRealName) {
				if (logic == null)
					logic = Operations.EQUALS;
				String combineOper = "and";
				if (Operations.isLike(logic)) {
					combineOper = "or";
					username = emptyUserName ? username : username + "%";
					realname = emptyRealName ? realname : realname + "%";
				}
				sql.append(" and (");

				if (!emptyUserName) { // 用户名称
					sql.append("t.username " + logic + " :username");
					map.put("username", username);
					countCondition++;
				}
				if (!emptyRealName) { // 用户名称
					if (countCondition == 0) // username为空. and (and/or c1
												// =// XXX) ---> 去掉前面的连接符 and/or
												// --- // > and (c1 = XXX)
						combineOper = "";
					sql.append(" " + combineOper + " t.realname " + logic
							+ " :realname");
					map.put("realname", realname);
				}
				sql.append(")");
			}
		}

		if (loginUser != null) {
			int founder = loginUser.getFounder();
			switch (founder) {
			case 0:
			case 1:
				sql.append(" and t.founder = " + Consts.CURR_FOUNDER3);
				break;
			case 2:
				sql.append(" and t.founder = " + Consts.CURR_FOUNDER2);
				sql.append(" and t.userid = '" + loginUser.getUserid() + "'");
				break;
			case 3:
				sql
						.append(" and (t.userid = '" + loginUser.getUserid()
								+ "' or t.paruserid = '"
								+ loginUser.getUserid() + "')");
				break;
			default:
				throw new RuntimeException("当前工号异常：" + loginUser.getUserid());
			}
		}
		// sql.append(" order by t.realname");
		logger.debug("===查询用户===: " + sql.toString());
		return baseDaoSupport.queryForPageByMap(sql.toString(), pageNo,
				pageSize, AdminUser.class, map);
	}

	@Override
	public Page list(String username, String realname, int founder, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from es_adminuser where 1=1");
		if (!StringUtil.isEmpty(username)) {
			sql.append(" and username like '%");
			sql.append(username);
			sql.append("%'");
		}
		if (!StringUtil.isEmpty(realname)) {
			sql.append(" and realname like '%");
			sql.append(realname);
			sql.append("%'");
		}
		if (founder == Consts.CURR_FOUNDER0 || founder == Consts.CURR_FOUNDER1
				|| founder == Consts.CURR_FOUNDER4
				|| founder == Consts.CURR_FOUNDER5
				|| founder == Consts.CURR_FOUNDER2
				|| founder == Consts.CURR_FOUNDER3) {
			sql.append(" and founder=");
			sql.append(founder);
		}

		return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize);
	}

	@Override
	public AdminUser getCurrentUser() {
		WebSessionContext<AdminUser> sessonContext = ThreadContextHolder.getSessionContext();
		AdminUser adminUser= sessonContext.getAttribute("admin_user_key");
		if(adminUser ==null)
			adminUser=(AdminUser) ThreadContextHolder.getHttpSessionContext().getAttribute("admin_user_key");
		return adminUser;
	}

	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	@Override
	public void cleanFailCount(String userId) {

		this.baseDaoSupport.execute(
				"update es_adminUser set fail_loginCount=0 where userid =?",
				userId);

	}

	@Override
	@SuppressWarnings("unused")
	public void updateCountAndLoginTime(String type, AdminUser adminUser) {
		ManagerUtils mu = new ManagerUtils();
		AdminUser admin = new AdminUser();
		if (Consts.loginFail_type.equals(type)) {

			this.baseDaoSupport
					.execute(
							"update es_adminUser set fail_loginCount=fail_loginCount+1,last_loginfail_time="+DBTUtil.current()+" where userid =?",
							adminUser.getUserid());
		}

		// add by wui
		if (Consts.loginSuccess_type.equals(type)) {
			String cur_logintime = adminUser.getCur_logintime();
			if (StringUtil.isEmpty(cur_logintime)) {// cur_logintime,
				this.baseDaoSupport
						.execute(
								"UPDATE es_adminuser SET success_logincount=?, cur_logintime="+DBTUtil.current()+",  fail_logincount=?, last_logintime="+DBTUtil.current()+" WHERE userid=?",
								adminUser.getSuccess_logincount() + 1, 0,
								adminUser.getUserid());
			} else {
				if (cur_logintime.lastIndexOf(".0") > -1)
					cur_logintime = cur_logintime.substring(0, cur_logintime
							.length() - 2);
				this.baseDaoSupport
						.execute(
								"UPDATE es_adminuser SET success_logincount=?, cur_logintime="+DBTUtil.current()+",  fail_logincount=?, last_logintime="+DBTUtil.to_date(2)+" WHERE userid=?",
								adminUser.getSuccess_logincount() + 1, 0,
								cur_logintime, adminUser.getUserid());
			}
		}

	}



	@Override
	public int getUserCountByUserName(String username) {
		int count = 0;
		count = this.baseDaoSupport.queryForInt(
				"select count(*) from es_adminuser where username=?", username);
		return count;
	}

	@Override
	public List getUseridsByFounder(int founder) {

		return this.baseDaoSupport.queryForList(
				"select userid from es_adminuser where founder =?", founder);

	}

	@Override
	public int getCountByUsername(String userid) {
		int count = this.baseDaoSupport.queryForInt(
				"select count(*) from es_adminuser where userid=?", userid);
		return count;
	}

	@Override
	public AdminUser getAdminUserByRefCode(String refCode) {
		return daoSupport.queryForObject(
				"select * from es_adminuser u where u.relCode = ?",
				AdminUser.class, refCode);
	}

	@Override
	public AdminUser getAdminUserByUserName(String username) {
		AdminUser adminUser = null;
		String sql = "select * from es_adminuser u where u.username=?";
		List<AdminUser> userList = this.baseDaoSupport.queryForList(sql,
				AdminUser.class, username);
		if (userList != null && userList.size() > 0) {
			adminUser = userList.get(0);
		}
		return adminUser;
	}

	@Override
	public Page orgUserList(String org_id, String state, String realname,
			String username,int pageNo,int pageSize) {
	    List list = new ArrayList();
		String sql = "select a.*,b.type_name from es_adminuser a inner join es_user_type b on a.founder=b.founder where  a.source_from =? and b.source_from=? and b.create_founder=? ";
		list.add(CommonTools.getSourceForm());
		list.add(CommonTools.getSourceForm());
		list.add(ManagerUtils.getFounder());
		String countSql = "select count(*) from es_adminuser a inner join es_user_type b on a.founder=b.founder where  a.source_from =? and b.source_from=? and b.create_founder=? ";
		
		//org_id =?
		String whereSql ="";
		if(org_id!=null&&!"".equals(org_id)){
			whereSql +=" and a.org_id =?";
			
			list.add(org_id);
		}		
		if(state!=null&&!"".equals(state)&&!"2".equals(state)){
			whereSql += " and a.state ="+state;
		}
		if(realname!=null&&!"".equals(realname)){
			whereSql+=" and a.realname like '%"+realname+"%'";
		}
		if(username!=null&&!"".equals(username)){
			whereSql+=" and a.username like '%"+username+"%'";
		}
		if(!"1".equals(CommonTools.getUserId())){
			whereSql +=" and (a.paruserid = ? or a.userid = ? ) ";
			list.add(CommonTools.getUserId());
			list.add(CommonTools.getUserId());
		}
		
		whereSql += " order by a.create_time desc";
		sql = sql +whereSql;
		countSql = countSql+whereSql;
	    Page page = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, AdminUser.class, countSql, list.toArray());//this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, list.toArray());
		return page;
	}
	public LanManager getLanManager() {
		return lanManager;
	}

	public void setLanManager(LanManager lanManager) {
		this.lanManager = lanManager;
	}

	@Override
	public void partnerLogin(AdminUser user) {
		// 判断登陆失败的次数是否》=5 》=5将此用户冻结 不能登陆系统
		

		if (user.getState() == 0) {
			throw new RuntimeException("此用户已经被禁用");
		}

		// //操作成功后的逻辑，修改当前登陆时间，和上次登陆时间 ，登陆次数1+ failCount少于5次 将 failCount 清零

		this.updateCountAndLoginTime(Consts.loginSuccess_type, user);

		EopSite site = EopContext.getContext().getCurrentSite();

		// 开启多站点功能
		if (site.getMulti_site() == 1) {
			if (user.getFounder() != 1) { // 非超级管理员检测是否是站点管理员
				MultiSite childSite = EopContext.getContext()
						.getCurrentChildSite();
				if (user.getSiteid() == null
						|| childSite.getSiteid().equals(user.getSiteid())) {
					throw new RuntimeException("非此站点管理员");
				}
			}
		}

		// 更新月登录次数和此站点的最后登录时间
		int logincount = site.getLogincount();
		long lastlogin = (site.getLastlogin()) * 1000;
		Date today = new Date();
		if (DateUtil.toString(new Date(lastlogin), "yyyy-MM").equals(
				DateUtil.toString(today, "yyyy-MM"))) {// 与上次登录在同一月内
			logincount++;
		} else {
			logincount = 1;
		}

		//
		site.setLogincount(logincount);
		site.setLastlogin(DateUtil.getDatelineLong());
		this.daoSupport.execute(
				"update eop_site set logincount=?, lastlogin=? where id=?",
				logincount, site.getLastlogin(), site.getId());

		// 查询导出excel的条数常量
		StringBuffer sql_excel = new StringBuffer();
		sql_excel.append("select codea from es_dc_public where stype = '8787'");
		String item = this.baseDaoSupport.queryForString(sql_excel.toString());

		// 记录session信息
		WebSessionContext sessonContext = ThreadContextHolder
				.getHttpSessionContext();

		UserContext userContext = new UserContext(site.getUserid(), site
				.getId(), user.getUserid());
		sessonContext.setAttribute(UserContext.CONTEXT_KEY, userContext);
		sessonContext.setAttribute("admin_user_key", user);
		sessonContext.setAttribute("excel_items", item);
		
		if (user.getLan_id() != null && !"".equals(user.getLan_id())
				&& !Consts.LAN_PROV.equals(user.getLan_id())) {
			String lan_name = lanManager.getLanNameByID(user.getLan_id());
			sessonContext.setAttribute("lan_name", lan_name);
		}
	}

	@Override
	public void insertLoginFail(String userid) {
		this.baseDaoSupport.execute("insert into ES_FAIL_LOGIN_LOG(userid,create_time) values(?,"+DBTUtil.current()+")", userid);
	}

	@Override
	public int countDayLoginFail(String userid) {
		String sql = "select count(*) from ES_FAIL_LOGIN_LOG where userid=? and "+DBTUtil.to_date("create_time", 1)+" = "+DBTUtil.to_date(DBTUtil.current(), 1)+"";
		return this.baseDaoSupport.queryForInt(sql,userid);
	}

	@Override
	public void clearLoginFailTimes(String userid) {
		this.baseDaoSupport.execute("update es_adminUser set fail_loginCount=0 where userid =?",userid);
	}

	@Override
	public void updateLoginStatus(String userid,String sessionid,int status) {
		if(sessionid==null)sessionid="";
		String sql = "update es_adminuser set current_sessionid=? ,login_status=? where userid=?";
		this.baseDaoSupport.execute(sql, sessionid,status,userid);
	}

	@Override
	public void getAdminUserFromStaff(AdminUser adminUser, Staff staff) {
         adminUser.setUserid(staff.getStaff_id());
         adminUser.setRelcode(staff.getStaff_id());
         adminUser.setPassword("123");
         adminUser.setSex(staff.getSex());
         adminUser.setUser_pid(staff.getUser_pid());
         adminUser.setBirthday(staff.getBirthday());
         adminUser.setRealname(staff.getStaff_name());
         adminUser.setOrg_id(staff.getDepart_id());
         adminUser.setFounder(Consts.CURR_FOUNDER3);
         adminUser.setState(1);
         adminUser = this.fillAdminUser(adminUser);
         int count = this.getUserCount(adminUser.getRelcode());
         if(count>0){
        	 this.edit(adminUser);
         }else{
          this.add(adminUser);
         }      
	}

	@Override
	public AdminUser fillAdminUser(AdminUser adminUser) {
		  
		     String sql ="select * from es_organization where party_id =?";
	         Map<String,String> orgMap  = this.baseDaoSupport.queryForMap(sql,adminUser.getOrg_id());
	         

	         if(StringUtils.isNotEmpty(Const.getStrValue(orgMap,"org_name")))
	         adminUser.setDep_name(orgMap.get("org_name"));
	         if(StringUtils.isNotEmpty(Const.getStrValue(orgMap,"region_id")))
	         {  
	        	 String city_id =Const.getStrValue(orgMap,"region_id");
	        	 adminUser.setCity_id(city_id);
	        	 String city_name = this.getOrgName(city_id);
	        	 if(city_name!=null&&!"".equals(city_name)){
	        		 adminUser.setCity(city_name);
	        	 }
	         }
	         if(StringUtils.isNotEmpty(Const.getStrValue(orgMap,"lan_id")))
	         {  
	        	 String lan_id =Const.getStrValue(orgMap,"lan_id");
	        	 adminUser.setLan_id(lan_id);
	        	 String lan_name = this.getOrgName(lan_id);
	        	 if(lan_name!=null&&!"".equals(lan_name)){
	        		 adminUser.setLan_name(lan_name);
	        	 }
	         }
		     return adminUser;
	}
    public String getOrgName(String party_id){
    	String str ="";
    	String sql = "select org_name from es_organization where party_id ="+party_id;
    	str = this.baseDaoSupport.queryForString(sql);
    	return str;
    }
	@Override
	public Page getStaffList(String party_id, String staff_id,
			String staff_name, int pageNo, int pageSize) {
		String sql ="select * from es_mbl_staff where depart_id=?";
		if(!"".equals(staff_id)&&staff_id!=null){
			sql+=" and staff_id like '%"+staff_id+"%'";
		}
		if(!"".equals(staff_name)&&staff_name!=null){
			sql+= " and staff_name like '%"+staff_name+"%'";
		}
	    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, party_id);
			
		return page;
	}

	@Override
	public int getUserCount(String relcode) {
		int count = 0 ;
		String sql ="select count(*) from es_adminuser where relcode=?";
		count =  this.baseDaoSupport.queryForInt(sql, relcode);
		return count;
	}

	@Override
	public int getUserCountByRelcode(String relcode, String username) {
        int count = 0 ;
		String sql ="select count(*) from es_adminuser where  username=?";
		count = this.baseDaoSupport.queryForInt(sql,username);
		return count;
	}
    /***
     * 输入
     * inputMap{serial_number 分销商的号码,passwd原密码,staff_passwd新密码}
     * 修改成功返回outMap{result:1,message:""}
     * 失败返回返回outMap{result:0,message:"修改失败的原因"}
     */
	@Override
	public Map updateBossPwd(Map<String, String> inputMap) {
		Map resultMap=new HashMap();
		try {
//			IAdminUserManager adminuserManager = PlatService.getPlatServInstance(AdminUserManagerImpl.class);
//			return adminuserManager.updateBossPwd(inputMap);
			int code = -1;
			String resetPwd = null;
			if(StringUtils.equals(Const.getStrValue(inputMap, "flag"),"reset")){
				resetPwd = ManagerUtils.genRandowNum(6);
				code=this.resetPassword(Const.getStrValue(inputMap, "phone"),resetPwd, Const.getStrValue(inputMap, "username"));
				if(code==1){
					resultMap.put("result", "1");
					resultMap.put("message", "密码重置成功，已发新密码到工号绑定手机！");
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
							.getSourceFrom());
					SmsSendReq req = new SmsSendReq();
					req.setAcc_nbr(Const.getStrValue(inputMap, "phone"));
					String json ="您的工号："+Const.getStrValue(inputMap, "username")+
							"重置密码成功，新密码为："+resetPwd+"；首次登录请修改密码！";
					req.setSmsContent(json);
					SmsSendResp smsSendResp = client.execute(req, SmsSendResp.class);
					String body = smsSendResp.getBody();
					SmsResp resp = JsonUtil.fromJson(body, SmsResp.class,null);
					if("1".equals(resp.getResp_code())){
						resultMap.put("result", "0");
						resultMap.put("message", "短信下发失败，请重新重置密码！");
					}
				}else{
					resultMap.put("result", "0");
					resultMap.put("message", "手机号码与绑定的工号不符");
				}
			}else{
				String checkResult =passwdCheck(Const.getStrValue(inputMap, "staff_passwd"));
				if(StringUtils.isNotEmpty(checkResult)){
					resultMap.put("result", "0");
					resultMap.put("message", "密码修改失败:"+checkResult);
				}else{
					code=this.updatePassword(Const.getStrValue(inputMap, "passwd"), Const.getStrValue(inputMap, "staff_passwd"), ManagerUtils.getUserId());
					if(code==1){
						resultMap.put("result", "1");
						resultMap.put("message", "密码修改成功");
					}else if(code==2){
						resultMap.put("result", "2");
						resultMap.put("message", "密码修改成功,请重新登陆");
					}else{
						resultMap.put("result", "0");
						resultMap.put("message", "旧密码错误");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", "修改密码出错,"+e.getMessage());
		}
		return resultMap;
	}
	/***
     * 输入
     * inputMap{serial_number 分销商的号码,passwd原密码,staff_passwd新密码}
     * 修改成功返回outMap{result:1,message:""}
     * 失败返回返回outMap{result:0,message:"修改失败的原因"}
     */
	@Override
	public Map updateBossPwdZj(Map<String, String> inputMap) {
		Map resultMap=new HashMap();
		try {
//			IAdminUserManager adminuserManager = PlatService.getPlatServInstance(AdminUserManagerImpl.class);
//			return adminuserManager.updateBossPwd(inputMap);
			int code = -1;
			String resetPwd = null;
			if(StringUtils.equals(Const.getStrValue(inputMap, "flag"),"reset")){
				resetPwd = ManagerUtils.genRandowNum(6);
				code=this.resetPassword(Const.getStrValue(inputMap, "phone"),resetPwd, Const.getStrValue(inputMap, "username"));
				if(code==1){
					resultMap.put("result", "1");
					resultMap.put("message", "密码重置成功，已发新密码到工号绑定手机！");
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
							.getSourceFrom());
//					SmsSendReq req = new SmsSendReq();
//					req.setAcc_nbr(Const.getStrValue(inputMap, "phone"));
					String json ="您的工号："+Const.getStrValue(inputMap, "username")+
							"重置密码成功，新密码为："+resetPwd+"；首次登录请修改密码！";
//					req.setSmsContent(json);
//					SmsSendResp smsSendResp = client.execute(req, SmsSendResp.class);
//					String body = smsSendResp.getBody();
//					SmsResp resp = JsonUtil.fromJson(body, SmsResp.class,null);
					
					AopSmsSendReq smsSendReq = new AopSmsSendReq();
					smsSendReq.setService_num(Const.getStrValue(inputMap, "phone"));
					smsSendReq.setSms_data(json);
					
					AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
					
					if("1".equals(smsSendResp.getError_code())){
						resultMap.put("result", "0");
						resultMap.put("message", "短信下发失败，请重新重置密码！");
					}
				}else{
					resultMap.put("result", "0");
					resultMap.put("message", "手机号码与绑定的工号不符");
				}
			}else{
				String checkResult =passwdCheck(Const.getStrValue(inputMap, "staff_passwd"));
				if(StringUtils.isNotEmpty(checkResult)){
					resultMap.put("result", "0");
					resultMap.put("message", "密码修改失败:"+checkResult);
				}else{
					code=this.updatePassword(Const.getStrValue(inputMap, "passwd"), Const.getStrValue(inputMap, "staff_passwd"), ManagerUtils.getUserId());
					if(code==1){
						resultMap.put("result", "1");
						resultMap.put("message", "密码修改成功");
					}else if(code==2){
						resultMap.put("result", "2");
						resultMap.put("message", "密码修改成功,请重新登陆");
					}else{
						resultMap.put("result", "0");
						resultMap.put("message", "旧密码错误");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", "修改密码出错,"+e.getMessage());
		}
		return resultMap;
	}
	public String passwdCheck(String password){
		Pattern safePattern =Pattern.compile("^(?=.{8,}).*$");//字符长度
		String result ="";
			if(!safePattern.matcher(password).matches()){
				result ="密码必须大于8个字符";
				return result;
			}	
			int  count=3;
			safePattern =Pattern.compile("^(?=.*[a-z]).*$");//
			if(safePattern.matcher(password).matches()){
				count--;
			}
			safePattern =Pattern.compile("^(?=.*[A-Z]).*$");//
			if(safePattern.matcher(password).matches()){
				count--;
			}
			safePattern =Pattern.compile("^(?=.*[0-9]).*$");//
			if(safePattern.matcher(password).matches()){
				count--;
			}
			safePattern =Pattern.compile("^(?=.*\\W).*$");//特殊符号校验
			if(safePattern.matcher(password).matches()){
				count--;
			}
			if(count>0){
				result ="密码需要由6位以上的大写字母.小写字母.数字.特殊符号中的三项组成";
				return result;
			}
			safePattern =Pattern.compile("^.*(.)\\1{3}.*$");//4个及以上相同字符校验
			if(safePattern.matcher(password).matches()){
				result ="密码不能包含4个及以上相同字符";
				return result;
			}
			if(hasLH(password,4)){
				result ="密码不能包含4个及以上连续字符";
				return result;
			}
		return result;
		
	}
	private static boolean hasLH(String str,int count) {
        int pre = 0;
        int len = 1;
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            char c = s.charAt(0);
            if (i == 0) {
                pre = c;
            }
            if (c - 1 == pre) {
                len++;
                if(len>=count){
                    return true;
                }
            }else {
                len = 1;
            }
            pre = c;
        }
        return false;
    }
	@Override
	public Map founderMap(){
		Map<String,String> map = new HashMap();
		String sql = "select pkey,pname from es_dc_public where stype='111101'";
		List<Map> list = this.baseDaoSupport.queryForList(sql);
		for(int i=0;i<list.size();i++){
			Map<String,String> map1 = new HashMap();
			map1 = list.get(i);
			map.put(map1.get("pkey"),map1.get("pname"));
		}
		return map;
	}
	

	@Override
	public AdminUser getUser(String id) {
		String sql = " select a.userid, a.USERNAME, a.PASSWORD, a.STATE, a.REALNAME, a.USERNO, a.USERDEPT, a.REMARK, a.DATELINE, a.FOUNDER, a.SITEID, a.RELTYPE, " +
				     " a.RELNO, a.PARUSERID, a.USERLAN, a.RELCODE, a.CREATE_TIME, a.LAN_ID, a.CUR_LOGINTIME, a.LAST_LOGINTIME, a.FAIL_LOGINCOUNT, a.SUCCESS_LOGINCOUNT, " +
				     " a.PHONE_NUM, a.ORG_ID, a.DEP_NAME, a.SOURCE_FROM, a.USER_LEVEL, a.APPLY_TIME, a.OPEN_TIME, a.BOSS_USER_TYPE, a.EMAIL, a.DESKS, " +
				     " (select c.region_name from es_common_region c where a.city_id = c.region_id ) CITY, " +
				     " (select b.region_name from es_common_region b where a.lan_id=b.region_id ) LAN_NAME, " +
				     " a.LAST_LOGINFAIL_TIME, a.CURRENT_SESSIONID, a.LOGIN_STATUS, a.CITY_ID, a.SEX, a.BIRTHDAY, a.USER_PID  from es_adminuser a  where userid=? ";
		AdminUser user = this.daoSupport.queryForObject(sql, AdminUser.class, id);
		
		return user;
	}
	
	@Override
	public List findMemberAuditNoteByUserId(String userName){
    	//这代码有问题，需要修改2013-11-29
    	AdminUser user = this.getAdminUserByUserName(userName);
    	String userid ="";//this.getAdminUserByUserName(userName).getUserid();// 当前登陆的ID
    	if(user!=null)
    		userid = user.getUserid();
    	//Member member  = UserServiceFactory.getUserService().getCurrentMember();
    	//String userid = "";
    	//if(member!=null)
    	//	userid = member.getMember_id();
		String sql="SELECT audit_note FROM es_flow_item_inst a,es_flow_inst b WHERE a.flow_inst_id=b.flow_inst_id AND a.source_from=? AND b.apply_user=? AND a.flow_state='00F' ORDER BY a.audit_date DESC";
		return this.baseDaoSupport.queryForList(sql, ManagerUtils.getSourceFrom(),userid);
    }

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	@Override
	public Page queryAdminUser(String userName,int pageNo,int pageSize) {
		String sql = "select * from es_adminuser t where t.state=1 and t.source_from is not null ";
		if(!StringUtils.isEmpty(userName))
			sql += " and (upper(t.username) like '%"+userName.trim().toUpperCase()+"%' or upper(t.realname) like '%"+userName.trim().toUpperCase()+"%')";
		String countSql = sql.replace("*", "count(*)");
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, AdminUser.class, countSql);
	}
	@Override
	public String loginBySysOrRandCode(String sms_valid_code, String username, String password) {
		String sql = "select * from adminuser where username=? and state='1' and source_from is not null"; 					//查询有效的
		List<AdminUser> userList = this.baseDaoSupport.queryForList(sql, AdminUser.class, username);
		if (userList == null || userList.size() == 0) {
			//throw new RuntimeException("此用户不存在");这个提示过于友好,给人可乘之机
			throw new RuntimeException("用户名或者密码错误,请核查.");
		}
		
		AdminUser user = userList.get(0);
		// 判断登陆失败的次数是否》=5 》=5将此用户冻结 不能登陆系统
        String limit_cou=this.cacheUtil.getConfigInfo("LIMIT_COUNT");
		int limit_count =StringUtils.isNotBlank(limit_cou)?Integer.parseInt(limit_cou):5 ;
		if (user.getFail_logincount() >= limit_count) {
			throw new RuntimeException("您已经连续" + limit_count
					+ "次密码错误，不可再登陆此系统，请联系管理员解冻");
		}

		if (StringUtil.isEmpty(username)) {
			throw new RuntimeException("用户名不能为空！");
		}

		if (StringUtil.isEmpty(password)) {
			throw new RuntimeException("密码不能为空！");
		}
		
		if (StringUtil.md5(password).equals(user.getPassword()) || password.equals(sms_valid_code)) {
		
		}else{
			// ++ 密码错误 登陆失败次数 failCount +1
			this.updateCountAndLoginTime(Consts.loginFail_type, user);
			throw new RuntimeException("密码错误," + "您还有"
					+ (limit_count - (user.getFail_logincount() + 1)) + "次机会");
		}
		
		if (user.getState() == 0) {
			throw new RuntimeException("此用户已经被禁用");
		}

		// //操作成功后的逻辑，修改当前登陆时间，和上次登陆时间 ，登陆次数1+ failCount少于5次 将 failCount 清零

		this.updateCountAndLoginTime(Consts.loginSuccess_type, user);

		EopSite site = EopContext.getContext().getCurrentSite();

		// 开启多站点功能
		if (site.getMulti_site() == 1) {
			if (user.getFounder() != 1) { // 非超级管理员检测是否是站点管理员
				MultiSite childSite = EopContext.getContext()
						.getCurrentChildSite();
				if (user.getSiteid() == null
						|| childSite.getSiteid().equals(user.getSiteid())) {
					throw new RuntimeException("非此站点管理员");
				}
			}
		}

		// 更新月登录次数和此站点的最后登录时间
		int logincount = site.getLogincount();
		long lastlogin = (site.getLastlogin()) * 1000;
		Date today = new Date();
		if (DateUtil.toString(new Date(lastlogin), "yyyy-MM").equals(
				DateUtil.toString(today, "yyyy-MM"))) {// 与上次登录在同一月内
			logincount++;
		} else {
			logincount = 1;
		}

		//
		site.setLogincount(logincount);
		site.setLastlogin(DateUtil.getDatelineLong());
		this.daoSupport.execute(
				"update eop_site set logincount=?, lastlogin=? where id=?",
				logincount, site.getLastlogin(), site.getId());

		// 查询导出excel的条数常量
		StringBuffer sql_excel = new StringBuffer();
		sql_excel.append("select codea from es_dc_public where stype = '8787'");
		String item = this.baseDaoSupport.queryForString(sql_excel.toString());

		// 记录session信息
		WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();

		UserContext userContext = new UserContext(site.getUserid(), site
				.getId(), user.getUserid());
		sessonContext.setAttribute(UserContext.CONTEXT_KEY, userContext);
		sessonContext.setAttribute("admin_user_key", user);
		sessonContext.setAttribute("excel_items", item);
		
		if (user.getLan_id() != null && !"".equals(user.getLan_id())
				&& !Consts.LAN_PROV.equals(user.getLan_id())) {
			String lan_name = lanManager.getLanNameByID(user.getLan_id());
			sessonContext.setAttribute("lan_name", lan_name);
		}
		return user.getUserid();
	}

	@Override
	public List getUserTypeListByFounder() {
		// TODO Auto-generated method stub
		String sql ="select * from es_user_type where create_founder=? and source_from =?";
		return this.baseDaoSupport.queryForList(sql,ManagerUtils.getFounder(),CommonTools.getSourceForm());
	}

	@Override
	public AdminUser getAdminUser(String userid) {
		String sql = SF.sysSql("ADMIN_USER_GET");
		return this.baseDaoSupport.queryForObject(sql, AdminUser.class, userid);
	}

	@Override
	public UserPasswordUpdateResp updateUserPassword(UserPasswordUpdateReq req) {
		UserPasswordUpdateResp resp = new UserPasswordUpdateResp();
		try {
			String sql = "select * from es_adminuser  where username = ?";
			AdminUser adminUser = this.baseDaoSupport.queryForObject(sql, AdminUser.class,req.getUsername());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			if(adminUser==null){
				resp.setError_msg("系统不存在该用户");
			}else if(adminUser.getState()==Consts.user_state0){
				resp.setError_msg("该用户已被禁用");
			}else if(!req.getIs_loser()&&(!StringUtil.md5(req.getOldPassword()).equals(adminUser.getPassword()))){
				resp.setError_msg("原密码错误");
			}else{
				adminUser.setPassword(StringUtil.md5(req.getNewPassword()));
				String update_sql = "update es_adminuser set password=? where username=? ";
			    this.baseDaoSupport.execute(update_sql, StringUtil.md5(req.getNewPassword()),req.getUsername());
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("操作成功");
				resp.setAdminUsers(adminUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_msg("密码修改出错："+e.getMessage());
			
		}
		   return resp;
	}

	@Override
	public PartnerLogOffResp partnerLogoff(PartnerLogOffReq req) {
		PartnerLogOffResp resp = new PartnerLogOffResp();
		try {
			String sql = "select e.* from es_adminuser e  where e.userid = ? and source_from =?";
			AdminUser adminUser = this.baseDaoSupport.queryForObject(sql, AdminUser.class,req.getUser_id(),CommonTools.getSourceForm());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			if(adminUser==null){
				resp.setError_msg("系统不存在该用户");
			}else{
				String update_sql = "update es_adminuser set state=? where userid=? ";
			    this.baseDaoSupport.execute(update_sql, req.getState(),req.getUser_id());
				resp.setError_code(ConstsCore.ERROR_SUCC);
				if(req.getState().equals("1")){
					resp.setError_msg("用户启用成功");
				}else if(req.getState().equals("0")){
					resp.setError_msg("销户成功");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_msg("注销出错："+e.getMessage());
			
		}
		   return resp;
	}
	
	@Override
	public SmsValidCodeGetResp getSmsValidCode(SmsValidCodeGetReq req){
		SmsValidCodeGetResp resp = new SmsValidCodeGetResp();
		String user_name = req.getUser_name();
		if(StringUtils.isEmpty(user_name)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("请输入用户名!");
		}else{
			try{
				 AdminUser user = getAdminUserByUserName(user_name);
				 if(user == null){
					 resp.setError_code(ConstsCore.ERROR_FAIL);
					 resp.setError_msg("请输入有效的用户名!");
				 }else{
					 if(StringUtils.isEmpty(user.getPhone_num())){
						 resp.setError_code(ConstsCore.ERROR_FAIL);
						 resp.setError_msg("此工号未配置短信电话!");
					 }else{
						 HttpSession session = ServletActionContext.getRequest().getSession();
						 Object obj = session.getAttribute(ISmsCode.SESSION_SMS_CODE_APPLY_TIME);
						 String timeInterval = cacheUtil.getConfigInfo("VALID_CODE_TIME_INTERVAL");
						 if (obj != null) {
							 int timeInt = 0;
							 if(!StringUtils.isEmpty(timeInterval)){
								 timeInt = Integer.valueOf(timeInterval);
								 long lastApplyCode = Long.parseLong(obj.toString());
								 if(System.currentTimeMillis() - lastApplyCode < 1000*timeInt){
									 resp.setError_code(ConstsCore.ERROR_FAIL);
									 resp.setError_msg(timeInterval+"秒内只能申请一次验证码!");
								 }
								 else{
									 resp = sendSms(user);
								 }
							 }
							 else{
								 resp = sendSms(user);
							 }
						 } else {
							 resp = sendSms(user);
						 }
						 resp.setTime_interval(timeInterval);
					 }
				 }
			}catch(Exception e){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("发送短信验证码异常!");
				e.printStackTrace();
			}
		}
		return resp;
		/*HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(JSONObject.toJSONString(resp));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 发送短信验证码给用户
	 * @作者 zengxianlian
	 * @创建日期 2015-06-29
	 */
	private SmsValidCodeGetResp sendSms(AdminUser user){
		SmsValidCodeGetResp resp = new SmsValidCodeGetResp();
		HttpSession session = ServletActionContext.getRequest().getSession();
		Object obj = session.getAttribute(ISmsCode.SMS_SEND_NO);
		WebSessionContext sessionContext = ThreadContextHolder.getHttpSessionContext();
		
		try {
			
			if(null != obj){//把原有短信置为无效
				String sendNos = obj.toString();
				String sendNo = sendNos;
				if(sendNos.contains(",")){
					sendNo = sendNos.substring(sendNos.lastIndexOf(",")+1, sendNos.length());
				}
				smsCode.updateState(sendNo);
			}
			
			String randcode = smsCode.createRandCode();
			String recPhoneNum = user.getPhone_num();
			String smsContent = smsCode.getSmsContent(randcode);
			String sendNo = smsCode.sendSms(recPhoneNum, randcode);
			
			session.setAttribute(ISmsCode.SESSION_SMS_CODE_APPLY_TIME, System.currentTimeMillis());
			session.setAttribute(ISmsCode.SESSION_SMS_CODE, randcode);
			sessionContext.setAttribute(ISmsCode.SESSION_SMS_CODE, randcode);
			
			if(null != obj){
				String sendNos = obj.toString();
				sendNos = sendNos+","+sendNo;
				session.setAttribute(ISmsCode.SMS_SEND_NO, sendNos);
				sessionContext.setAttribute(ISmsCode.SMS_SEND_NO, sendNos);
			}else{
				session.setAttribute(ISmsCode.SMS_SEND_NO, sendNo);
				sessionContext.setAttribute(ISmsCode.SMS_SEND_NO, sendNo);
			}
			resp.setReceive_phone(recPhoneNum);
			resp.setValid_code(smsContent);
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("短信发送成功,请查收!");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("短信发送失败!"+e.getMessage());
		}
		
		return resp;
	}

	@Override
	public Page qryUserPageByPojo(int pageNo, int pageSize, User pojo)
			throws Exception {
		List<SqlBuilderInterface> sqlBuildList = new ArrayList<SqlBuilderInterface>();
		SqlLikeBuilder userNameBuilder = new SqlLikeBuilder("realname", pojo.getRealname());
		sqlBuildList.add(userNameBuilder);
		
		return this.baseDaoSupport.queryPageByPojo("es_adminuser", pojo, sqlBuildList, pageNo, pageSize);
	}

	@Override
	public Page qryUserPageByPojo(int pageNo, int pageSize, User pojo,
			List<SqlBuilderInterface> sqlBuild) throws Exception {
		return this.baseDaoSupport.queryPageByPojo("es_adminuser", pojo, sqlBuild, pageNo, pageSize);
	}
}