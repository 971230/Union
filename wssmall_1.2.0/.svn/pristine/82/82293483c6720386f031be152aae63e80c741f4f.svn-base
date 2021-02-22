package params;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.parameters.AbstractRopRequest;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2013-11-01 08:55 To change
 * this template use File | Settings | File Templates.
 */
public class BaseNParam extends AbstractRopRequest implements Serializable {

	
	private String format = "json";

	private String locale = "zh_CN";

	private String method;

	private String version = "1.0";

	private String appKey;

	private String appSecret;

	private String sign;

	private String serverUrl;

    private String sourceFrom;//来源
    
    private String mqTopic;//mq主题
    
    private String base_co_id;//队列id
    
    private String base_order_id;//订单id
    
    @NotDbField
    public String getSourceFrom() {
        return sourceFrom;
    }
    @NotDbField
    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    @NotDbField
	public String getFormat() {
		return format;
	}
	@NotDbField
	public void setFormat(String format) {
		this.format = format;
	}
	@NotDbField
	public String getLocale() {
		return locale;
	}
	@NotDbField
	public void setLocale(String locale) {
		this.locale = locale;
	}
	@NotDbField
	public String getMethod() {
		return method;
	}
	@NotDbField
	public void setMethod(String method) {
		this.method = method;
	}
	@NotDbField
	public String getVersion() {
		return version;
	}
	@NotDbField
	public void setVersion(String version) {
		this.version = version;
	}
	@NotDbField
	public String getAppKey() {
		return appKey;
	}
	@NotDbField
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	@NotDbField
	public String getAppSecret() {
		return appSecret;
	}
	@NotDbField
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	@NotDbField
	public String getSign() {
		return sign;
	}
	@NotDbField
	public void setSign(String sign) {
		this.sign = sign;
	}
	@NotDbField
	public String getServerUrl() {
		return serverUrl;
	}
	@NotDbField
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@NotDbField
	public String getMqTopic() {
		return mqTopic;
	}
	@NotDbField
	public void setMqTopic(String mqTopic) {
		this.mqTopic = mqTopic;
	}
	
	@NotDbField
	public String getBase_co_id() {
		return base_co_id;
	}
	@NotDbField
	public void setBase_co_id(String base_co_id) {
		this.base_co_id = base_co_id;
	}
	@NotDbField
	public String getBase_order_id() {
		return base_order_id;
	}
	@NotDbField
	public void setBase_order_id(String base_order_id) {
		this.base_order_id = base_order_id;
	}
	
	

}
