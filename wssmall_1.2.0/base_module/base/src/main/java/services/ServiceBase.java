package services;

import iservice.IService;
import params.ZteRequest;
import params.ZteResponse;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

/**
 * 通用服务类
 * @author wui
 *
 */
@SuppressWarnings({ "unused", "unchecked" })
public  class  ServiceBase extends BaseSupport implements IService {
	
	//会员登录成功后需要单独设置sessionid，而不是统一设置 add by wui
	public  <T> void addReturn(ZteRequest zteRequest,ZteResponse op){
		
	}
	
}