/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.CreateRedPkgResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * CreateRedPkgReq.java
 * 2015-3-17
 * 生成红包
 */
public class CreateRedPkgReq extends ZteRequest<CreateRedPkgResp> {
	
	@ZteSoftCommentAnnotationParam(name="促销红包名称",type="String",isNecessary="Y",desc="促销红包名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="促销红包类型",type="String",isNecessary="Y",desc="001-普通红包，002-随机红包，003-话费红包")
	private String type;
	@ZteSoftCommentAnnotationParam(name="促销红包个数",type="long",isNecessary="Y",desc="促销红包类型")
	private String num;
	@ZteSoftCommentAnnotationParam(name="促销红包描述",type="String",isNecessary="Y",desc="促销红包描述")
	private String memo;
	@ZteSoftCommentAnnotationParam(name="促销红包总金额",type="long",isNecessary="Y",desc="促销红包总金额")
	private String amount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.promotionredpkg.create";
	}

}
