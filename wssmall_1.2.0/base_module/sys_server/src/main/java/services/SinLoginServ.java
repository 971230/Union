package services;

import java.util.List;
import java.util.Map;

import params.adminuser.req.SinLoginReq;
import params.adminuser.resp.SinLoginResp;

import com.ztesoft.net.mall.core.service.ISinLoginInfoManager;


public class SinLoginServ extends ServiceBase implements SinLoginInf{
	
	private ISinLoginInfoManager sinLoginInfoManager;
	
	@Override
	public SinLoginResp list(SinLoginReq sinLoginReq){
		List<Map> list = sinLoginInfoManager.list();
		
		SinLoginResp sinLoginResp = new SinLoginResp();
		sinLoginResp.setList(list);
		addReturn(sinLoginReq, sinLoginResp);
		return sinLoginResp;
	}
	
	
	public ISinLoginInfoManager getSinLoginInfoManager() {
		return sinLoginInfoManager;
	}

	public void setSinLoginInfoManager(ISinLoginInfoManager sinLoginInfoManager) {
		this.sinLoginInfoManager = sinLoginInfoManager;
	}
	
}