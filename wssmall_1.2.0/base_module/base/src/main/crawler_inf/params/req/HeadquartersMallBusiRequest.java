package params.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 总商登录对象表
 * 
 */
@RequestBeanAnnontion(primary_keys = "staff_code", table_name = "ES_HEADQUARTERS_MALL_STAFF", service_desc = "总商登录对象表")
public class HeadquartersMallBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
//depency_keys="order_id"
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String staff_id;
	@RequestFieldAnnontion()
	private String staff_code;
	@RequestFieldAnnontion()
	private String staff_name;
	@RequestFieldAnnontion()
	private String remark;
	@RequestFieldAnnontion()
	private String message_code;
	@RequestFieldAnnontion()
	private String cookie;
	@RequestFieldAnnontion()
	private String status;
	@RequestFieldAnnontion()
	private String password;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<HeadquartersMallBusiRequest> updateRequest = new ZteInstUpdateRequest<HeadquartersMallBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<List<HeadquartersMallBusiRequest>> resp = new QueryResponse<List<HeadquartersMallBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, new ArrayList<HeadquartersMallBusiRequest>());
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage_code() {
		return message_code;
	}

	public void setMessage_code(String message_code) {
		this.message_code = message_code;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
