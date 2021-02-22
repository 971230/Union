package zte.params.goods.resp;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.GroupBuy;

public class GroupBuyResp extends ZteResponse {
	private GroupBuy groupBuy;

	public GroupBuy getGroupBuy() {
		return groupBuy;
	}

	public void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}
	
}
