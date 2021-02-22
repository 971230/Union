package utils;

import params.ZteCommonData;


/**
 * 线程变量接口
* @作者 MoChunrun 
* @创建日期 2013-10-12 
* @版本 V 1.0
* 
* 
* mod by wui 此对象只做dubbo、rop等方式调用的入参，出参对象信息
 */
public class CoreThreadLocalHolder {
	
	private static final CoreThreadLocalHolder local = new CoreThreadLocalHolder();
	//外系统订单参数
	
	private  ThreadLocal<ZteCommonData> zteCommonDataLocal = new ThreadLocal<ZteCommonData>(); //DefaultDubbo调用时的统一入口
	
	public static CoreThreadLocalHolder getInstance(){
		return local;
	}

	public  ZteCommonData  getZteCommonData() {
		if (zteCommonDataLocal.get() == null) {
			zteCommonDataLocal.set(new ZteCommonData());
		}
		return zteCommonDataLocal.get();
	}
	
	/*dubbo调用结束后销毁时调*/
	public void clear(){
		if(zteCommonDataLocal.get() !=null)
			zteCommonDataLocal.get().setZteRequest(null); //调用完成直接清空
	}
}
