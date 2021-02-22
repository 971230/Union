package params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


public abstract class ZteBusiRequest<T extends ZteResponse> implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String is_dirty ="no";//缺省为脏数据
	private String db_action ="";//数据库操作
	private String asy ="no";//异步操作
	private String need_query ="yes";//默认子节点不需要查询
	private List<String> changeFiels =new ArrayList<String>(); //需要更新的字段
	
	private Map<String, Object> dyn_field;					   //动态扩充字段
	
	@NotDbField
	public String getDb_action() {
		return db_action;
	}
	public void setDb_action(String db_action) {
		this.db_action = db_action;
	}
	@NotDbField
	public String getAsy() {
		return asy;
	}
	public void setAsy(String asy) {
		this.asy = asy;
	}
	@NotDbField
	public String getNeed_query() {
		return need_query;
	}
	public void setNeed_query(String need_query) {
		this.need_query = need_query;
	}
	@NotDbField
	public String getIs_dirty() {
		return is_dirty;
	}
	public void setIs_dirty(String is_dirty) {
		this.is_dirty = is_dirty;
	}
	@NotDbField
	public abstract void check() throws ApiRuleException ;
	/**
	 * 获取TOP的API名称。
	 * 
	 * @return API名称
	 */
	@NotDbField
	public abstract String getApiMethodName();
	@NotDbField
	public List<String> getChangeFiels() {
		return changeFiels;
	}
	@NotDbField
	public void setChangeFiels(List<String> changeFiels) {
		this.changeFiels = changeFiels;
	}
	@NotDbField
	public Map<String, Object> getDyn_field() {
		return dyn_field;
	}
	public void setDyn_field(Map<String, Object> dyn_field) {
		this.dyn_field = dyn_field;
	}
	
	
}
