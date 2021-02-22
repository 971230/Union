package context;


/**
 * 
 * @author wui 通用上下文工厂
 * 
 */
public class WebContextFactory {
	
	public final static String DUBBO_WEB ="dubbo";
	public final static String HTTP_WEB ="http";
	
	public static WebContext getInstance(String type)
	{
		if(DUBBO_WEB.equals(type))
			return  new DubboWebContext();
		if(HTTP_WEB.equals(type))
			return  new HttpWebContext();
		return null;
	}
	
}