package com.ztesoft.net.mall.core.timer;

import params.ZteResponse;
import zte.params.goods.req.GoodsImportReq;
import zte.params.goods.resp.GoodsImportResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;

public class GoodsImportTimer {

	public void run() {
		try{
			//ip验证处理 add by wui所有定时任务都需要加上限制条件
	  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
	  			return ;
	  		
			GoodsImportReq req = new GoodsImportReq();
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			ZteResponse response = client.execute(req, GoodsImportResp.class);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
}
