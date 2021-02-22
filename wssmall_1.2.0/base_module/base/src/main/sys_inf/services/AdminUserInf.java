package services;

import java.util.List;

import params.adminuser.req.AdminAddReq;
import params.adminuser.req.AdminBossUpdateReq;
import params.adminuser.req.AdminCurrUserReq;
import params.adminuser.req.AdminStatusUpdateReq;
import params.adminuser.req.AdminUserAddReq;
import params.adminuser.req.AdminUserByObjPageReq;
import params.adminuser.req.AdminUserCleanReq;
import params.adminuser.req.AdminUserCountByIdReq;
import params.adminuser.req.AdminUserCountReq;
import params.adminuser.req.AdminUserDelReq;
import params.adminuser.req.AdminUserDisableReq;
import params.adminuser.req.AdminUserFillReq;
import params.adminuser.req.AdminUserGetReq;
import params.adminuser.req.AdminUserIdReq;
import params.adminuser.req.AdminUserListReq;
import params.adminuser.req.AdminUserLoginReq;
import params.adminuser.req.AdminUserPageReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.req.AdminUserUpdateReq;
import params.adminuser.req.OrgInfoReq;
import params.adminuser.req.StaffInfoReq;
import params.adminuser.req.SupplierAddReq;
import params.adminuser.req.UserFounderReq;
import params.adminuser.req.UserPasswordUpdateReq;
import params.adminuser.req.UserRoleReq;
import params.adminuser.req.UserTypeListReq;
import params.adminuser.req.UserWdLoginReq;
import params.adminuser.resp.AdminAddResp;
import params.adminuser.resp.AdminEditUserResp;
import params.adminuser.resp.AdminStatusUpdateResp;
import params.adminuser.resp.AdminUserCleanResp;
import params.adminuser.resp.AdminUserCountResp;
import params.adminuser.resp.AdminUserDelResp;
import params.adminuser.resp.AdminUserDisableResp;
import params.adminuser.resp.AdminUserEditResp;
import params.adminuser.resp.AdminUserIdResp;
import params.adminuser.resp.AdminUserLoginResp;
import params.adminuser.resp.AdminUserPageResp;
import params.adminuser.resp.AdminUserResp;
import params.adminuser.resp.OrgInfoResp;
import params.adminuser.resp.StaffInfoResp;
import params.adminuser.resp.UserFounderResp;
import params.adminuser.resp.UserPasswordUpdateResp;
import params.adminuser.resp.UserRoleResp;
import params.adminuser.resp.UserTypeListResp;
import params.adminuser.resp.UserWdLoginResp;
import params.org.req.OrgReq;
import params.org.resp.OrgResp;
import params.req.PartnerLogOffReq;
import params.req.PartnerSSOReq;
import params.resp.PartnerLogOffResp;
import zte.net.ecsord.params.nd.vo.User;

import com.ztesoft.net.app.base.core.model.SupplierStaff;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

/**
 * 系统
 * 
 * @作者 wui
 * @创建日期 2013-9-27
 * @版本 V 1.0
 */
public interface AdminUserInf {

	/**
	 * 新增系统工号用户信息
	 * 
	 * @作者 wui
	 * @创建日期 2013-9-25
	 * @param bp
	 * @return
	 */
	public AdminUserResp createAdminUser(AdminUserReq bp);

	// 获取用户组织信息
	@SuppressWarnings("unchecked")
	public OrgResp getAgentOrgInfo(OrgReq orgReq);

	public AdminUserResp getAdminUserById(AdminUserReq adminUserReq);

	public AdminUserResp getAdminuserByRole(AdminUserReq adminUserReq);

	public AdminUserLoginResp loginBySys(AdminUserLoginReq adminUserLoginReq);

	public AdminUserLoginResp loginBySysOrRandCode(String sms_valid_code,
			AdminUserLoginReq adminUserLoginReq);

	// 用户登录
	public AdminUserLoginResp login(AdminUserLoginReq adminUserLoginReq);

	public AdminUserResp getAdminUserByUserName(AdminUserReq adminUserReq);

	public AdminUserPageResp queryUser(
			AdminUserByObjPageReq adminUserByObjPageReq);

	public AdminUserEditResp updatePassword(
			AdminUserUpdateReq adminUserUpdateReq);

	public UserRoleResp getRolesToUser(UserRoleReq userRoleReq);

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public AdminUserIdResp getAdminUserSequences(AdminUserIdReq adminUserIdReq);

	/**
	 * 添加供货商
	 * 
	 * @param supplierAddReq
	 * @return
	 */
	public AdminUserIdResp addSupplier(SupplierAddReq supplierAddReq);

	/**
	 * 查找用户记录
	 * 
	 * @return
	 */
	public AdminUserCountResp getUserCountByUserName(
			AdminUserCountReq adminUserCountReq);

	/**
	 * 绑定用户
	 * 
	 * @param supplierStaff
	 * @return
	 */
	public AdminUserIdResp bindingstaff(SupplierStaff supplierStaff);

	/**
	 * 删除用户
	 * @throws Exception 
	 */
	public AdminUserDelResp deleteAdminUsesr(AdminUserDelReq adminUserDelReq) throws Exception;

	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	public UserFounderResp founderMap(UserFounderReq userFounderReq);

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	public AdminUserResp getCurrentUser(AdminCurrUserReq adminCurrUserReq);

	/**
	 * 获取用户数量
	 * 
	 * @param adminUserCountReq
	 * @return
	 */
	public AdminUserCountResp getUserCount(AdminUserCountReq adminUserCountReq);

	/**
	 * 补充用户信息
	 * 
	 * @param adminUser
	 * @return
	 */
	public AdminUserResp fillAdminUser(AdminUserFillReq adminUserFillReq);

	/**
	 * 获取用户数量
	 * 
	 * @param adminUserCountReq
	 * @return
	 */
	public AdminUserCountResp getUserCountByRelcode(
			AdminUserCountReq adminUserCountReq);

	/**
	 * 编辑用户信息
	 */
	public AdminEditUserResp edit(AdminUserReq adminUserReq);

	/**
	 * 获取分销商信息
	 * 
	 * @param staffInfoReq
	 * @return
	 */
	public StaffInfoResp getStaffList(StaffInfoReq staffInfoReq);

	/**
	 * 获取组织信息
	 * 
	 * @param orgInfoReq
	 * @return
	 */
	public OrgInfoResp orgUserList(OrgInfoReq orgInfoReq);

	/**
	 * 获取用户信息
	 * 
	 * @param adminUserReq
	 * @return
	 */
	public AdminUserResp getUser(AdminUserGetReq adminUserGetReq);

	/**
	 * 获取用户列表
	 * 
	 * @param adminUserPageReq
	 * @return
	 */
	public AdminUserPageResp list(AdminUserListReq adminUserListReq);

	/**
	 * 更新失败次数
	 * 
	 * @param adminUserReq
	 */
	public AdminUserCleanResp cleanFailCount(AdminUserCleanReq adminUserCleanReq);

	/**
	 * 新增用户
	 * 
	 * @param adminUser
	 */
	public AdminAddResp add(AdminAddReq adminAddReq);

	/**
	 * 获取用户数量
	 * 
	 * @param adminUserReq
	 * @return
	 */
	public AdminUserCountResp getCountById(
			AdminUserCountByIdReq adminUserCountByIdReq);

	/**
	 * 修改boss系统密码
	 * 
	 * @param adminUserEditReq
	 * @return
	 */
	public AdminUserEditResp updateBossPwd(AdminBossUpdateReq adminBossUpdateReq);
	
	/**
	 * 修改boss系统密码
	 * 
	 * @param adminUserEditReq
	 * @return
	 */
	public AdminUserEditResp updateBossPwdZj(AdminBossUpdateReq adminBossUpdateReq);

	/**
	 * 禁用用户
	 * 
	 * @param adminUserReq
	 */
	public AdminUserDisableResp disableAdminUser(
			AdminUserDisableReq adminUserDisableReq);

	/**
	 * 添加管理员
	 * 
	 * @param adminUserAddReq
	 * @return
	 */
	public AdminUserIdResp addUser(AdminUserAddReq adminUserAddReq);

	/**
	 * 更新用户登录状态
	 * 
	 * @param adminUserEditReq
	 */
	public AdminStatusUpdateResp updateLoginStatus(
			AdminStatusUpdateReq adminStatusUpdateReq);

	public Page queryAdminUser(String userName, int pageNo, int pageSize);

	public AdminUserPageResp qryAdminUser(AdminUserPageReq adminUserPageReq);

	public UserTypeListResp qryUserTypeList(UserTypeListReq userTypeListReq);

	public UserWdLoginResp userWdLogin(UserWdLoginReq userWdLoginReq);

	public UserWdLoginResp userWdLoginSSO(PartnerSSOReq req);
	/**
	 * 修改密码或者找回密码 已req里面的is_loser标识 如果是true 就是找回密码 false就是修改密码
	 * 
	 * @param req
	 * @return
	 */
	public UserPasswordUpdateResp updateUserPassword(UserPasswordUpdateReq req);
	
	public PartnerLogOffResp partnerLogoff(PartnerLogOffReq req);
	
	/**
	 * 通过pojo查询用户PAGE
	 * @param pageNo
	 * @param pageSize
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	public Page qryUserPageByPojo(int pageNo,int pageSize,User pojo) throws Exception;
	
	/**
	 * 通过pojo查询用户PAGE
	 * @param pageNo
	 * @param pageSize
	 * @param pojo
	 * @param sqlBuild
	 * @return
	 * @throws Exception
	 */
	public Page qryUserPageByPojo(int pageNo,int pageSize,
			User pojo,List<SqlBuilderInterface> sqlBuild) throws Exception;
}
