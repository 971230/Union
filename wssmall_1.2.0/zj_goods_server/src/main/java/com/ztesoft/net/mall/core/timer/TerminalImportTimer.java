package com.ztesoft.net.mall.core.timer;

import params.ZteResponse;
import zte.params.goods.req.TerminalImportReq;
import zte.params.goods.resp.TerminalImportResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;

public class TerminalImportTimer {

	public void run(){
		try{
			//ip验证处理 add by wui所有定时任务都需要加上限制条件
	  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
	  			return ;
	  		
			TerminalImportReq req = new TerminalImportReq();
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			ZteResponse response = client.execute(req, TerminalImportResp.class);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
}
