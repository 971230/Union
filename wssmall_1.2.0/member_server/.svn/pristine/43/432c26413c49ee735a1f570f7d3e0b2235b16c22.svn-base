package com.ztesoft.net.mall.core.service;


import java.util.List;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.database.Page;

/**
 * 会员管理接口
 * @author kingapex
 *2010-4-30上午10:07:35
 */
public interface IMemberManager {

	/**
	 * 添加会员
	 * 
	 * @param member
	 * @return 0：用户名已存在，1：添加成功
	 */
	public int add(Member member);
	
	/**
	 * 添加会员
	 * 连连科技
	 * @param member
	 * @return 0：用户名已存在，1：添加成功
	 */
	public int addMember(Member member);
	

	/**
	 * 检测手机号是否存在
	 * */
	public boolean checkmobile(String mobile);
	
	/**
	 * 验证某上用户
	 * @param checkStr
	 */
	public Member checkEmail(String checkStr);
	
	
	
	
	/**
	 * 检测用户名是否存在
	 * 
	 * @param name
	 * @return 存在返回1，否则返回0
	 */
	public int checkname(String name);

	/**
	 * 修改会员信息
	 * 
	 * @param member
	 * @return
	 */
	public Member edit(Member member);

	/**
	 * 根据会员id获取会员信息
	 * 
	 * @param member_id
	 * @return
	 */
	public Member get(String member_id);

	/**
	 * 分页读取会员
	 * 
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page list(String order, int page, int pageSize);
	
	public Page list(String order, String name, String uname, int page, int pageSize);

	/**
	 * 删除会员
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 根据用户名称取用户信息
	 * 
	 * @param uname
	 * @return 如果没有找到返回null
	 */
	public Member getMemberByUname(String uname);

	
	/**
	 * 根据手机号码取用户信息
	 * 
	 * @param mobile
	 * @return 如果没有找到返回null
	 */
	public Member getMemberByMobile(String mobile);
	
	
	/**
	 * 修改当前登录会员的密码
	 * 
	 * @param password
	 */
	public void updatePassword(String password);
	
	
	
	/**
	 * 更新某用户的密码
	 * @param memberid
	 * @param password
	 */
	public void updatePassword(String memberid,String password);
	
	
	/**
	 * 增加预存款
	 */
	public void addMoney(String memberid,Double num);
	
	
	
	/**
	 * 减少预存款
	 * @param memberid
	 * @param num
	 */
	public void cutMoney(String memberid,Double num);
	
	
	
	
	
	/**
	 * 会员登录 
	 * @param username 用户名
	 * @param password 密码
	 * @return 1:成功, 0：失败
	 */
	public int login(String session_id,String username,String password);
	
	
	/**
	 * 会员注销
	 */
	public void logout(  );
	
	
	
	/**
	 * 管理员以会员身份登录
	 * @param username 要登录的会员名称
	 * @return 0登录失败，无此会员
	 * @throws  RuntimeException 无权操作
	 */
	public int loginbysys(String username);
	
	/**
	 * 单点登录
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-13 
	 * @param username
	 * @return
	 */
	public int authLogin(String username);
	
	
	/**
	 * 更新某个会员的等级
	 * @param memberid
	 * @param lvid
	 */
	public void updateLv(String memberid,String lvid);
	
	/**
	 * 更新密码但不对密码进行加密
	 * @param memberid
	 * @param lvid
	 */
	public void updatePassword1(String memberid, String password);
	/**
	 * 更新会员信息
	 * @param member
	 * @return
	 */
	public Member editSaveMember(Member member);
	
	
	/**
	 * 查询会员信息（分页展示）
	 * 
	 * */
	public Page queryMemberPage(String uname, int page_index, int page_size);
	
	/****
	 * 查询订单日志
	 * @param order_id
	 * @return
	 */
	public List listOrderLog(String order_id) ;
	
	/****
	 * 查询订单赠品
	 * @param order_id
	 * @return
	 */
	public List listGiftByOrderId(String order_id);
	
	/****
	 * 会员日志
	 * @param uname
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	public Page queryPointHistoryPage(String pointType,String member_id, int page_index, int page_size);
	
	/*****
	 * 累计消费积分
	 * @param pointType
	 * @return
	 */
	public String queryConsumePoint(String pointType, String member_id);
	
	/*****
	 * 累计获得积分
	 * @param pointType
	 * @return
	 */
	public String queryGainPoint(String pointType, String member_id);
	
	/*****
	 * 当前会员是否购买过此商品
	 * @param goods_id
	 * @return
	 */
	public boolean isBuy(String goods_id);
	
	public int loginByMobile(String userSessionId,String mobile, String password);
}