package rule.params.accept.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import rule.params.accept.resp.AcceptRuleResp;
import vo.AcceptVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.AttrInst;

/**
 * 受理规则入参
 * 
 * @author wu.i
 * 
 */
public class AcceptRuleReq extends ZteRequest<AcceptRuleResp> {

	List<AcceptVo> acceptVos = new ArrayList<AcceptVo>();
	List<AttrInst> oattrInsts =new ArrayList<AttrInst>();//外系统属性
	
	private ZteRequest zteRequest;
	OrderItem orderItem;
	Order order;
	
	private Map params; //传入的map参数
	
	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public List<AcceptVo> getAcceptVos() {
		if (acceptVos == null)
			return new ArrayList<AcceptVo>();
		return acceptVos;
	}

	public void setAcceptVos(List<AcceptVo> acceptVos) {
		this.acceptVos = acceptVos;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderservice.tplinst.save";
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public List<AttrInst> getOattrInsts() {
		return oattrInsts;
	}

	public void setOattrInsts(List<AttrInst> oattrInsts) {
		this.oattrInsts = oattrInsts;
	}

	public ZteRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest zteRequest) {
		this.zteRequest = zteRequest;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	

}
