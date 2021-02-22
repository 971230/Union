package com.ztesoft.net.app.base.core.service;

import com.ztesoft.net.app.base.core.model.FriendsLink;

import java.util.List;

/**
 * 友情链接
 * 
 * @author lzf<br/>
 *         2010-4-8 下午06:17:50<br/>
 *         version 1.0<br/>
 */
public interface IFriendsLinkManager {
	
	public FriendsLink get(int link_id);
	
	public List listLink();
	
	public void add(FriendsLink friendsLink);
	
	public void delete(String id);
	
	public void update(FriendsLink friendsLink);

}
