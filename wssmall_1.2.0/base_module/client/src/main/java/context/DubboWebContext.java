package context;

import params.ZteRequest;

/**
 * @author wui 通用上下文对象
 */
public class DubboWebContext extends WebContext {

	//add by wui说明：dubbo入口声明的线程变成，在真正的dubbo实现类处理器中是获取不到的，因为此时是调用了另外一台机器的应用，线程变量不支持跨应用处理
    @Override
	public void initContext(ZteRequest request) {
       
    }

    @Override
	public void destoryContext(ZteRequest zteRequest) {
    	super.destoryContext(zteRequest);
    }

    @Override
	public void execContext(ZteRequest zteRequest) {
        super.execContext(zteRequest);
    }

}