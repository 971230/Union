package context;

import params.ZteRequest;

/**
 * 
 * @author wui 通用上下文对象
 * 
 */
public class HttpWebContext extends WebContext {

	@Override
	public void initContext(ZteRequest request) {
		super.initContext(request);
	}

	@Override
	public void execContext(ZteRequest zteRequest) {
		super.execContext(zteRequest);
	}
	
	// 对象销毁
	@Override
	public void destoryContext(ZteRequest zteRequest) {
		super.destoryContext(zteRequest);
	}
}