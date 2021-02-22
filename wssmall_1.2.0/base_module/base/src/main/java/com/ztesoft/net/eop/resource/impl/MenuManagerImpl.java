package com.ztesoft.net.eop.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.eop.resource.IMenuManager;
import com.ztesoft.net.eop.resource.model.Menu;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.sqls.SF;

/**
 * 菜单管理
 * @author kingapex
 *2010-5-10下午02:00:10
 */
public class MenuManagerImpl extends BaseSupport<Menu> implements IMenuManager {

	@Override
	public void clean() {
		this.baseDaoSupport.execute("truncate table menu");
	}
	@Override
	public List<Menu> getMenuList() {
		
		String sql = SF.baseSql("GET_MENU_LIST_0");
		return this.baseDaoSupport.queryForList(sql, Menu.class);
	}

	
	@Override
	public String add(Menu menu) {
		if(menu.getTitle()==null) throw new IllegalArgumentException("title argument is null");
		if(menu.getPid()==null) throw new IllegalArgumentException("pid argument is null");
		if(menu.getUrl() ==null) throw new IllegalArgumentException("url argument is null");
		if(menu.getSorder() ==null) throw new IllegalArgumentException("sorder argument is null");
		menu.setDeleteflag(0);
		menu.setId(this.baseDaoSupport.getSequences("s_es_menu", "0", 7));
		this.baseDaoSupport.insert("menu", menu);
		return menu.getId();
	}


	@Override
	public List<Menu> getMenuTree(Integer menuid) {
		if(menuid==null)throw new IllegalArgumentException("menuid argument is null");
		List<Menu> menuList  = this.getMenuList();
		List<Menu> topMenuList  = new ArrayList<Menu>();
		for(Menu menu :menuList){
			if(new Integer(menu.getPid()).intValue() ==menuid.intValue()){
				List<Menu> children = this.getChildren(menuList, menu.getId());
				menu.setChildren(children);
				topMenuList.add(menu);
			}
		}
		return topMenuList;
	}

	/**
	 * 在一个集合中查找子
	 * @param menuList 所有菜单集合
	 * @param parentid 父id
	 * @return 找到的子集合
	 */
	private List<Menu> getChildren(List<Menu> menuList ,String parentid){
		List<Menu> children =new ArrayList<Menu>();
		for(Menu menu :menuList){
			if(menu.getPid().equals(parentid)){
			 	menu.setChildren(this.getChildren(menuList, menu.getId()));
				children.add(menu);
			}
		}
		return children;
	}


	@Override
	public Menu get(Integer id) {
		if(id==null)throw new IllegalArgumentException("ids argument is null");
		String sql ="select * from menu where id=?";
		return this.baseDaoSupport.queryForObject(sql, Menu.class, id);
	}


	@Override
	public void edit(Menu menu) {
		if(menu.getId()==null) throw new IllegalArgumentException("id argument is null");
		if(menu.getTitle()==null) throw new IllegalArgumentException("title argument is null");
		if(menu.getPid()==null) throw new IllegalArgumentException("pid argument is null");
		if(menu.getUrl() ==null) throw new IllegalArgumentException("url argument is null");
		if(menu.getSorder() ==null) throw new IllegalArgumentException("sorder argument is null");
		menu.setDeleteflag(0);
		this.baseDaoSupport.update("menu", menu, "id="+ menu.getId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSort(Integer[] ids, Integer[] sorts) {
		if(ids==null)throw new IllegalArgumentException("ids argument is null");
		if(sorts==null)throw new IllegalArgumentException("sorts argument is null");
		if(sorts.length !=ids.length) throw new IllegalArgumentException("ids's length and sorts's length not same");
		for(int i=0;i<ids.length;i++){
			String sql ="update menu set sorder=? where id=?";
			this.baseDaoSupport.execute(sql, sorts[i],ids[i]);
		}
	}


	@Override
	public void delete(Integer id) throws RuntimeException {
		if(id==null)throw new IllegalArgumentException("ids argument is null");
		String sql  ="select count(0) from menu where pid=?";
		int count  = this.baseDaoSupport.queryForInt(sql, id);
		if(count>0) throw new RuntimeException("菜单"+ id +"存在子类别,不能直接删除，请先删除其子类别。");
		sql ="delete from menu where id=?";
		this.baseDaoSupport.execute(sql, id);
	}
	
	
	@Override
	public List<Map> listShotCutMenu(String user_id){
		
		String sql = SF.baseSql("QRY_SHORT_CUT_MENU");
		
		return this.baseDaoSupport.queryForList(sql, user_id);
	}
}
