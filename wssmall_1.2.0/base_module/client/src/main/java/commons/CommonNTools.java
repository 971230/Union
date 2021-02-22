package commons;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import params.ZteError;
import params.ZteResponse;
import utils.CoreThreadLocalHolder;
import utils.GlobalThreadLocalHolder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ApiRunTimeException;
import com.ztesoft.net.mall.core.consts.Consts;


public class CommonNTools {
	
	private static final ThreadLocal<String> sessionIdLocal = new ThreadLocal<String>();
	private static final ThreadLocal<ZteResponse> outEntryLocal = new ThreadLocal<ZteResponse>(); //外围参数
	
	public static String getUUID(){
		String session_id = GlobalThreadLocalHolder.getInstance().getUUID();
		return session_id;
	}
	
	
	/**
	 * 异常类
	 * @param errorEntity
	 */
	public static void addFailError(String message){
		if(CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError() ==null)
			CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(new ZteError(Consts.ERROR_FAIL,message));
		ZteError zteError = new ZteError(Consts.ERROR_FAIL,message);
		setZteRequestError(zteError);
		throw new ApiRunTimeException(Consts.ERROR_FAIL,message);
	}
	

	/**
	 * 业务异常类
	 * @param errorEntity
	 * @throws BusiException 
	 */
	public static void addBusiError(ZteError zteError) throws ApiBusiException{
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(zteError);
		setZteRequestError(zteError);
		throw new ApiBusiException(zteError.getError_msg());
	}

	 /**
     * 业务异常类
     * @param errorEntity
	 * @throws BusiException 
     */
    public static void addBusiError(String errCode,String msg) throws ApiBusiException{
        ZteError zteError=new ZteError(errCode,msg);
        zteError.setType_code(Consts.EXP_BUSS);
        ZteError zteErrorn= CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
        //add by wui追加处理
        if(zteErrorn !=null)
        	zteError.setError_msg(zteError.getError_msg()+","+zteErrorn.getError_msg());
        CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(zteError);
        setZteRequestError(zteError);
        throw new ApiBusiException(zteError.getError_msg());
    }
    
    
	
	
	/**
	 * 异常类
	 * @param errorEntity
	 */
	public static void addError(ZteError zteError){
		if(CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError() ==null)
			CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(zteError);
		//错误信息写到入参变量中，解决dubbo、rop调用问题
		setZteRequestError(zteError);
		throw new ApiRunTimeException(zteError.getError_code(),zteError.getError_msg());
	}

    /**
     * 异常类
     * @param errorEntity
     */
    public static void addError(String errCode,String msg){
        ZteError zteError=new ZteError(errCode,msg);
        if(CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError() ==null)
        	CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(zteError);
        setZteRequestError(zteError);
        throw new ApiRunTimeException(errCode,zteError.getError_msg());
    }
	/**
	 * 错误信息
	 * @param zteError
	 */
    public static void setZteRequestError(ZteError zteError){
    	//错误信息写到入参变量中，解决dubbo、rop调用问题
//  		if(CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest() !=null)
//  			CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest().setZteError(zteError);
    }
    
	
	public static String getErrorStr(){
		return CommonNTools.beanToJson(getZteError());
	}
	
	public static ZteError getZteError(){
		return CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
	}
	public static ZteResponse getZteResponse(){
		return CoreThreadLocalHolder.getInstance().getZteCommonData().getZteResponse();
	}
	
	
	/**
	 * bean转换为json
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJson(T src){
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteClassName,
		};
		
		return JSON.toJSONString(src,features);
	}
	
	/**
	 * bean转换为json
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJsonFilterNull(T src){
		SerializerFeature[] features = {
			SerializerFeature.WriteClassName,
		};
		return JSON.toJSONString(src,features);
	}
	
	
	
//	public static <T> String beanToXml(T src){
//		String xml = com.ztesoft.common.util.XmlUtils.zteRequestToXml(s);
//	}
//	
	/**
	 * bean转换为json
	 * @作者 wu.i 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJsonNCls(T src){
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
		};
		return JSON.toJSONString(src,features);
	}
	
	/**
	 * json转为Bean
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToBean(String json,Class clazz){
		return (T) JSON.parseObject(json, clazz);
	}
	
	
	
	/**
	 * json 转为list
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json,Class clazz){
		return JSON.parseArray(json, clazz);
	}
	
	/**
	 * 创建sessionId
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @return
	 */
	public static String createSessionId(){
		return GlobalThreadLocalHolder.getInstance().getUUID();
	}
	
	
	/**
	 * 字段UTF-8转码
	 * @作者 wu.i 
	 * @创建日期 2013-9-23 
	 * @param in_value
	 * @return
	 */
	public static String getUTF8Value(String in_value){
		String contextInfo = System.getProperty(Consts.SERVLET_CONTEXT_INFO);
		if(contextInfo!=null && contextInfo.indexOf("WebLogic")==-1){
			try { //add by wui 中文编码转换
				in_value= new String(in_value.getBytes("iso8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return in_value;
	}
	
	public static String getUserSessionId() {
		String session_id =getUUID();
		return session_id;
	}
	
	
	
	/**
	 * 设置外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param order
	 */
	public static void setOutEntryLocal(ZteResponse order){
		outEntryLocal.set(order);
	}
	
	
	public static<T> T genOutThreadVO(Class<T> cla){
		
		T t = null;
		try {
			t = cla.newInstance();
			setOutEntryLocal((ZteResponse) t);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	
		return t;

	}
	
	public static ZteResponse  getOutEntryLocal(){
		return outEntryLocal.get();
	}
	
	public static<T> T getOutThreadVO(Class<T> cla){
		T t = (T)getOutEntryLocal();
		return t;

	}
	
	public static void  setOutThreadVO(ZteResponse ZteResponse){
		setOutEntryLocal(ZteResponse);

	}

	//////////////////////////add by wui sdk http工具转换类
	public  static  void removeKeys(Map paramMap,String [] keys){
		for (int i = 0; i < keys.length; i++) {
			paramMap.remove(keys[i]);
		}
	}
//	
//	@SuppressWarnings("rawtypes")
//	public static void beanToMap(Map paramMap, Object obj) throws Exception {
//		BeanUtils.copyProperties(paramMap, obj);
//		removeKeys(paramMap,new String []{"version","sign","sourceFrom","ropRequestContext","locale","remote_type","format","apiMethodName","textParams","method","appSecret","appKey","class"});
//		Iterator it = paramMap.keySet().iterator();
//		while (it.hasNext()) {
//			String key = (String) it.next();
//			Object value = paramMap.get(key);
//			if (value != null) {
//				String clsName = value.getClass().getName();
//				if (value instanceof ZteRequest  || value instanceof ZteResponse || clsName.indexOf("com.") > -1) {
//					Map subParamMap = new HashMap();
//					beanToMap(subParamMap, value);
//					paramMap.put(key, subParamMap);
//				}else if(value instanceof List){
//					List listVal = (List)value;
//					List subArrList = new ArrayList();
//					for (int i = 0; i < listVal.size(); i++) {
//						Object pvalue =listVal.get(i);
//						clsName = pvalue.getClass().getName();
//						if (value instanceof ZteRequest ||  value instanceof ZteResponse || clsName.indexOf("com.") > -1) {
//							Map subParamMap = new HashMap();
//							beanToMap(subParamMap, pvalue);
//							subArrList.add(subParamMap);
//						}else{
//							subArrList.add(pvalue);
//						}
//					}
//					paramMap.put(key, subArrList);
//					
//				}
//			}
//		}
//	}

	public static String formatDate(String dateStr, String formatStr){
		String resultStr = dateStr;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date shit = dateFormat.parse(dateStr);
			
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			resultStr = dft.format(shit);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	
}