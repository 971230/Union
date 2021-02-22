package zte.params.number.req;


import params.ZteError;
import params.ZteRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;
import consts.ConstsCore;
/**
 * 
 * @author deng.yx
 * 号码状态修改消息返回
 *
 */
public class NumberChangeStatusReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="电话号码", type="String", isNecessary="Y", desc="电话号码,如：18600008888")
	private String no;

	@ZteSoftCommentAnnotationParam(name="当前状态", type="String", isNecessary="Y", desc="修改前的状态000:未使用,"
			+ "111:已使用;001:已预占;00X:已作废")
	private String status_old;
	
	@ZteSoftCommentAnnotationParam(name="新状态", type="String", isNecessary="Y", desc="修改后的新状态,000:未使用,"
			+ "111:已使用;001:已预占;00X:已作废")
	private String status_new;
	
	@ZteSoftCommentAnnotationParam(name="订单编号", type="String", isNecessary="N", desc="订单编号，预占或释放的订单")
	private String order_id;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStatus_old() {
		return status_old;
	}

	public void setStatus_old(String status_old) {
		this.status_old = status_old;
	}

	public String getStatus_new() {
		return status_new;
	}

	public void setStatus_new(String status_new) {
		this.status_new = status_new;
	}

	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty((this.getNo()))) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "号码不能为空"));
        }
		
		if (StringUtils.isEmpty((this.getStatus_old()))) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "旧状态不能为空"));
        }
		
		if (StringUtils.isEmpty((this.getStatus_new()))) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "新状态不能为空"));
        }
		
	}

	@Override
	public String getApiMethodName() {
        return "com.ztesoft.remote.number.changeNumStatus";
        
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}
