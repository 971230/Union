package params.adminuser.resp;

import params.ZteResponse;


/**
 * 供货商添加返回实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class SupplierAddResp extends ZteResponse{

	private String adminUserId;

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
}
