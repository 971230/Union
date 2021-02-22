package zte.net.ecsord.params.busi.req;

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
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="account_id",table_name="es_order_account")
public class OrderActBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {

	@RequestFieldAnnontion(dname="order_id")
	private String order_id;
	
	@RequestFieldAnnontion(dname="account_id")
	private String account_id;
	
	@RequestFieldAnnontion(dname="acc_nbr")
	private String acc_nbr;
	
	private static final long serialVersionUID = 1L;

	

	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderActBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderActBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<OrderActBusiRequest> resp = new QueryResponse<OrderActBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this);
		
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
}
