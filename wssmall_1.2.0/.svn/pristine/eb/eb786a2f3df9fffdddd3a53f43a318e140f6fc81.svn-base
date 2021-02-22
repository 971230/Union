package params;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.rop.common.RopResp;

public class ZteResponse extends RopResp {
	
    String userSessionId;
    String error_cause;
    
    private String body;
    private OrderResp resp;
    private String exec_path;
    private String rule_id;
    private String rule_name;
    
    private List<OrderItemResp> item=new ArrayList<OrderItemResp>();

    @NotDbField
    public List<OrderItemResp> getItem() {
        return item;
    }
    @NotDbField
    public OrderResp getResp() {
        return resp;
    }
    public void setResp(OrderResp resp) {
        this.resp = resp;
    }
    
    boolean result = false;
    @NotDbField
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    
    @NotDbField
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setItem(List<OrderItemResp> item) {
		this.item = item;
	}
	
	public String getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}
	public String getError_cause() {
		return error_cause;
	}
	public void setError_cause(String error_cause) {
		this.error_cause = error_cause;
	}
	public String getExec_path() {
		return exec_path;
	}
	public void setExec_path(String exec_path) {
		this.exec_path = exec_path;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

}
