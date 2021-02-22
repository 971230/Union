package params.suppler.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.ZteResponse;


/**
 * 供货商分页查询对象
 * @author hu.yi
 * @date 2013.12.24
 */
public class SupplierPageReq extends  ZteRequest<ZteResponse> {

	
	private String user_name;
	private String phone;
	private int page;
	private int pageSize;
	
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
