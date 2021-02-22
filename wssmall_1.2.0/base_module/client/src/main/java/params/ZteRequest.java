package params;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ZteHashMap;
import com.ztesoft.net.framework.database.NotDbField;

public abstract class ZteRequest<T extends ZteResponse> extends BaseNParam {

	String remote_type ="REMOTE";//LOCAL,REMOTE 缺省为远程调用
	
	private String inf_log_id;		//接口日志id
	
	/**
	 * 客户端参数检查，减少服务端无效调用
	 */
	@Override
	@NotDbField
	public abstract void check() throws ApiRuleException ;
	

	/**
	 * 获取TOP的API名称。
	 * 
	 * @return API名称
	 */
	@Override
	@NotDbField
	public abstract String getApiMethodName();
	
	
	@NotDbField
	public Map<String, String> getTextParams() {
		ZteHashMap txtParams = new ZteHashMap();
		return txtParams;

	}
	@NotDbField
	public String getRemote_type() {
		return remote_type;
	}
	@NotDbField
	public void setRemote_type(String remote_type) {
		this.remote_type = remote_type;
	}
	public  String setApiMethodName(){
		return getApiMethodName();
	}
	@NotDbField
	public String getInf_log_id() {
		return inf_log_id;
	}
	@NotDbField
	public void setInf_log_id(String inf_log_id) {
		this.inf_log_id = inf_log_id;
	}
}
