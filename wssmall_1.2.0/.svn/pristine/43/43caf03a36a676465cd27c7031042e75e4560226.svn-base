package params.order.req;

import java.util.Map;

import params.ZteRequest;
import params.coqueue.resp.CoQueueAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.TempInst;

public class OrderOuterSyAttrReq extends ZteRequest {
	private Map map;
	private OrderOuter orderOuter;
	private TempInst temp;
	private Outer oo;
	private CoQueueAddResp coresp;
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public OrderOuter getOrderOuter() {
		return orderOuter;
	}
	public void setOrderOuter(OrderOuter orderOuter) {
		this.orderOuter = orderOuter;
	}
	public TempInst getTemp() {
		return temp;
	}
	public void setTemp(TempInst temp) {
		this.temp = temp;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.outer.orderoutersyattrreq";
	}
	public Outer getOo() {
		return oo;
	}
	public void setOo(Outer oo) {
		this.oo = oo;
	}
	public CoQueueAddResp getCoresp() {
		return coresp;
	}
	public void setCoresp(CoQueueAddResp coresp) {
		this.coresp = coresp;
	}
	
}
