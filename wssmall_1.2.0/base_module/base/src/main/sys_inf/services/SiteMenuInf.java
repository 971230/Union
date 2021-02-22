package services;

import params.site.req.SiteMenuReq;
import params.site.resp.SiteMenuResp;

/**
 * 站点菜单管理接口
 * 
 */
public interface SiteMenuInf {

	/**
	 * 添加
	 * 
	 * @param siteMenu
	 */
	public void add(SiteMenuReq siteMenuReq);

	
	/**
	 * 读取菜单详细
	 * @param menuid
	 * @return
	 */
	public SiteMenuResp get(SiteMenuReq siteMenuReq);
	
	
	
	/**
	 * 修改
	 * 
	 * @param siteMenu
	 */
	public void edit(SiteMenuReq siteMenuReq);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(SiteMenuReq siteMenuReq);

	
	
	/**
	 * 读取子菜单列表，包括其所有子的
	 * 
	 * @param parentid
	 * @return
	 */
	public SiteMenuResp list(SiteMenuReq siteMenuReq);
	
	
	
	
	/**
	 * 更新排序
	 * @param menuid 菜单id数组
	 * @param sort	排序值数组
	 * menuid 和sort要一一对应
	 */
	public void updateSort(SiteMenuReq siteMenuReq);
}
