package services;

import java.util.List;

import params.friend.resp.FriendResp;

import com.ztesoft.net.app.base.core.model.FriendsLinkMapper;

public class FriendsLinkServ extends ServiceBase implements FriendsLinkInf{
	@Override
	public FriendResp listLink() {
		FriendResp friendResp = new FriendResp();
		List list = this.baseDaoSupport.queryForList("select * from friends_link order by sort",new FriendsLinkMapper());
		friendResp.setListLink(list);
		return friendResp;
	}
}
