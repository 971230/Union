package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * 广告接口
 * 
 * @author 李志富 lzf<br/>
 *         2010-2-4 下午03:25:36<br/>
 *         version 1.0<br/>
 * <br/>
 */
public interface IAdvManager {

	/**
	 * 广告信息修改
	 * 
	 * @param adv
	 */
	public void updateAdv(Adv adv);

	/**
	 * 获取广告详细
	 * 
	 * @param advid
	 * @return
	 */
	public Adv getAdvDetail(String advid);

    public Adv getAdvDetail(String advid,String source_from);

	/**
	 * 广告新增
	 * 
	 * @param adv
	 */
	public void addAdv(Adv adv);

	/**
	 * 广告删除
	 * 
	 * @param advid
	 */
	public void delAdvs(String ids);
	/**
	 * 根据工号删除广告信息并归档到历史表
	 * 
	 * @param staff_no
	 */
	public void delAdvByStaffNo(String staff_no);
	/**
	 * 分页读取广告
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page pageAdv(String order, int page, int pageSize);
	
	/**
	 * 获取对应acid的所有广告列表
	 * @param acid
	 * @return
	 */
	public List listAdv(String acid);
	
	/**
	 * 获取对应广告位标识和用户（商家）标识获取广告列表
	 * @param acid
	 * @param user_id
	 * @return
	 */
	public List listAdv(String acid, String user_id);
	
	/**
	 * 搜索关键字
	 * @param acid
	 * @param cname
	 * @return
	 */
	public Page search(String acid,String advname,int state,int pageNo,int pageSize,String order);
	
	public Page search(String acid,String source_from,String advname,int state,int pageNo,int pageSize,String order);
	
	public int getNoAuditAdvNum();
	
	public int getAllAdvNum();
	
	public void updateAdvIsClose(String aid,int isClose);
	
	/**
	 * @param adv
	 * @param pageNo
	 * @param pageSize
	 * @param isPublic 1：公有，0私有
	 * @return
	 */
	public Page listAdvPage(Adv adv,int pageNo,int pageSize,String isPublic);
	
	
	public List<Adv> listAdv(Map<String, String> params);
}
