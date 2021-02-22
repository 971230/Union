package params.suppler.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.suppler.resp.SuppliersListResp;


/**
 * 查询供货商列表传参
 * @author hu.yi
 * @date 2013.12.31
 */
public class SuppliersListReq extends ZteRequest<SuppliersListResp>{

	private String suppler_id;//供货商id
	private String remd ="";//yes为精品商家
	private String suppler_name;//供货商名称
	private String company_name;//公司名称
	private String phone_num;//联系电话
	private String page_index ="1";//页数 默认为1
	private String page_size ="10";//每页10条 
	
	public String getSuppler_id() {
		return suppler_id;
	}

	public void setSuppler_id(String suppler_id) {
		this.suppler_id = suppler_id;
	}

	public String getRemd() {
		return remd;
	}

	public void setRemd(String remd) {
		this.remd = remd;
	}

	public String getSuppler_name() {
		return suppler_name;
	}

	public void setSuppler_name(String suppler_name) {
		this.suppler_name = suppler_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}
	
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "supplierServ.supplierList";
	}

}
