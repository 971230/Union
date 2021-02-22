package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import params.adminuser.resp.AdminAddResp;

/**
 * 新增管理员实体
 * 
 * @author hu.yi
 * @date 2013.12.23
 */
public class ZteNumRequest extends ZteRequest<AdminAddResp> {
	private String str;
	private int num;

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

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
