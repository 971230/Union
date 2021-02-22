package com.ztesoft.net.app.base.core.service.auth;

import java.util.List;
import java.util.Map;

import params.adminuser.req.AdminUserListReq;
import params.adminuser.req.SmsValidCodeGetReq;
import params.adminuser.req.UserPasswordUpdateReq;
import params.adminuser.resp.SmsValidCodeGetResp;
import params.adminuser.resp.UserPasswordUpdateResp;
import params.req.PartnerLogOffReq;
import params.resp.PartnerLogOffResp;
import zte.net.ecsord.params.nd.vo.User;

import com.ztesoft.net.app.base.core.model.SupplierStaff;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.Staff;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.Operations;

/**
 * 管理员管理接口
 * @author kingapex
 * 2010-11-7下午05:49:12
 */
public interface IAdminUserManager {
	
	/*
	 * 得到角色类型
	 */
	public String getRolesToUser(Integer stype, String pkey);
	
	
	/*
	 * 删除一个管理员
	 */
	public void deleteAdminUsesr(String id) throws Exception;
	
	
	public int updatePassword(String oldPassword,String newpassword,String userid);
	/**
	 * 为当前站点添加一个管理员
	 * @param adminUser
	 * @return 添加的管理员id
	 */
	public String add(AdminUser adminUser);
	
	public Map getAgentSystemUser(String staff_code);
	/**
	 * 为供货商添加一个管理员
	 * @param adminUser
	 * @return 添加的管理员id
	 */
	public String addSupplier(String adminUserId,String supplier_id,String account_number,String password,String phone,String company_name,String supplier_type);
	
	/**
	 * 为供货商绑定一个员工
	 * @param adminUser
	 * @return 添加的员工id
	 */
	public String bindingstaff(SupplierStaff supplierStaff);
	/**
	 * 禁用用户
	 * @param user_id
	 */
	public void disableAdminUser(String user_id);
	
	/**
	 * 为某个站点添加管理员
	 * @param userid
	 * @param siteid
	 * @param adminUser
	 * @return 添加的管理员id
	 */
	public String add(String userid,String siteid,AdminUser adminUser);
	
	public AdminUser getAdminUserById(String userId);
	
	/***
	 * 
	 */
	public Page list(AdminUserListReq adminUserListReq);
	
	/**
	 * 查询分销商。电信查询一级分销商，一级查询自己和他所属二级分销商，二级分销商查询自己
	 * @param user
	 * @param logic 查询逻辑。equals = select * from es_adminuser t where 1=1 and (t.username = 'wuhuif' and t.realname = 'wuhuif')<br/> 
	 * like = select * from es_adminuser t where 1=1 and (t.username like 'wuhuif%' or t.realname like 'wuhuif%')<br/>
	 * not_like = select * from es_adminuser t where 1=1 and (t.username not like 'wuhuif%' and t.realname not like 'wuhuif%')<br/>
	 * 传入null等于equals
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryUser(AdminUser queryUser, AdminUser loginUser, Operations logic, int pageNo, int pageSize);
	
	/**
	 * 用于消息管理收件人查询
	 * @param realname用户姓名
	 * @param founder用户类型
	 * @param userid 用户id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(String username,String realname,int founder,int pageNo, int pageSize);
	/**
	 * 管理员
	 * @param username
	 * @param password 此处为未经过md5加密的密码
	 * @return 返回登录成功的用户id
	 * @throws RuntimeException 登录失败抛出此异常，登录失败原因可通过getMessage()方法获取
	 */
	public String  login(String username,String password);
	
	
	/**
	 * 获取当前登录管理员
	 * @return
	 */
	public AdminUser getCurrentUser();
	
	/**
	 * 通过随机数对密码进行MD5加密
	 * @param username
	 * @param password
	 * @param md5Rand
	 * @return
	 */
	public String loginBySys(String username, String password) ;
	
	/**
	 * 系统登录
	 * @param username
	 * @param password 此处为未经过md5加密的密码
	 * @return 返回登录成功的用户id
	 * @throws RuntimeException 登录失败抛出此异常，登录失败原因可通过getMessage()方法获取
	 */
	public String loginBySysOrRandCode(String sms_valid_code, String username, String password);
	
	/**
	 * 读取管理员信息
	 * @param id
	 * @return
	 */
	public AdminUser get(String id);
	
	/**
	 * 读取用户信息
	 * @param id
	 * @return
	 */
	public AdminUser getUser(String id);
	
	
	/**
	 * 修改管理员信息 
	 * @param eopUserAdmin
	 */
	public void edit(AdminUser eopUserAdmin);
	
	
	/**
	 * 删除管理员
	 * @param id
	 * @throws RuntimeException  当删除最后一个管理员时
	 */
	public void delete(String id);
	
	
	/**
	 * 检查是否为最后一个管理员
	 * @return 
	 */
	public int checkLast();
 
  
	/**
	 * 读取此站点所有管理员
	 * @return
	 */
	public List list( ) ;
	
	
	/**
	 * 读取某站点的所有的管理员
	 * @param userid
	 * @param siteid
	 * @return
	 */
	public List<Map> list(Integer userid,Integer siteid);
	
	/**
	 * 清除本站点的所有管理员
	 * 一般安装所用
	 */
	public void clean();
	/**
	 * 
	 * @param type 根据登录失败与否进行次数修改
	 * @return
	 */
	public void  updateCountAndLoginTime(String type,AdminUser adminUser);
	/*
	 * 解冻时或者登陆成功之前登陆失败次数不超过5次 将failCount清零
	 */
	public void cleanFailCount(String userId);
	
   /**
    * 避免插入重复的用户
    * @return 0 为无此用户 可增加
    * 1为有此用户 不可再增加
    */
	public int getUserCountByUserName(String username);
	
	public List getUseridsByFounder(int founder);
	public int getCountByUsername(String userid);
	
	/**
	 * 通过CRM关联工号获取adminUser
	 * @param refCode
	 * @return
	 */
	public AdminUser getAdminUserByRefCode(String refCode);
	
	public AdminUser getAdminUserByUserName(String username);
	
	//得到序列
	public String getAdminUserSequences();
	
	public void partnerLogin(AdminUser user);
	
	public void insertLoginFail(String userid);
	
	public int countDayLoginFail(String userid);
	
	public void clearLoginFailTimes(String userid);
	
	public void updateLoginStatus(String userid,String sessionid,int status);
	
	public Page orgUserList(String org_id,String state,String real_name,String username,int pageNo,int pageSize);

	public void getAdminUserFromStaff(AdminUser adminUser,Staff staff);
	
	public AdminUser fillAdminUser(AdminUser adminUser);
	
	public Page getStaffList(String party_id,String staff_id,String staff_name,int page,int pageSize);
    
	public int getUserCount(String relcode);
	
	public int getUserCountByRelcode(String code,String username);
	
	public Map updateBossPwd(Map<String,String> map);//修改Boss密码接口
	
	public Map updateBossPwdZj(Map<String,String> map);//修改Boss密码接口
    
	public Map founderMap();
	
	public List findMemberAuditNoteByUserId(String userName);
	
	/**
	 * 查询用户
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param userName
	 * @return
	 */
	public Page queryAdminUser(String userName,int pageNo,int pageSize);
	
	public List getUserTypeListByFounder();
	
	public AdminUser getAdminUser(String userid);
	/**
	 * 修改密码或者找回密码  已req里面的is_loser标识 如果是true 就是找回密码 false就是修改密码
	 * @param req
	 * @return
	 */
	public UserPasswordUpdateResp updateUserPassword(UserPasswordUpdateReq req);
	
	/**
	 * 注销用户，设置state为0（为1则启用）
	 * @param userid
	 * @return
	 */
	public PartnerLogOffResp partnerLogoff(PartnerLogOffReq req);
	
	/**
	 * 获取验证码
	 * @param req
	 * @return
	 */
	public SmsValidCodeGetResp getSmsValidCode(SmsValidCodeGetReq req);
	
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
