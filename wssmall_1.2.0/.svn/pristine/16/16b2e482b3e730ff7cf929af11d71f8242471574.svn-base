package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminUserIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 供货商添加实体
 * @author hu.yi
 * @date 2013.12.23 
 */
public class SupplierAddReq extends ZteRequest<AdminUserIdResp>{

	private String adminUserId;
	private String supplier_id;
	private String account_number;
	private String password;
	private String phone;
	private String company_name;
	private String supplier_type;
	
	
	
	public String getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getSupplier_type() {
		return supplier_type;
	}
	public void setSupplier_type(String supplier_type) {
		this.supplier_type = supplier_type;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(supplier_id) || StringUtil.isEmpty(adminUserId) || StringUtil.isEmpty(password)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.AdminUserIdResp";
	}
}
