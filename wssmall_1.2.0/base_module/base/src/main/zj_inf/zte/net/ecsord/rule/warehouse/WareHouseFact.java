package zte.net.ecsord.rule.warehouse;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

@DroolsFact(name="仓库匹配",code="wareHouseFact")
public class WareHouseFact extends AutoFact implements Serializable {
	
	private String order_id;
	private List<Object>   list;
	@DroolsFactField(name="仓库匹配方法", ele_type="radio", stype_code="DC_WAREHOUSE_API_LIST")
	private String api_path;
	
	@DroolsFactField(name="是否匹配仓库", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_match_warehouse;
	
	@DroolsFactField(name="是否已匹配仓库", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_matched;
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	
	public OrderTreeBusiRequest getOrderTree(){
		return CommonDataFactory.getInstance().getOrderTree(order_id);
	}
	
	public void toStringN(){
		
	}

	public String getApi_path() {
		return api_path;
	}

	public void setApi_path(String api_path) {
		this.api_path = api_path;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public String getIs_match_warehouse(){
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_MATCH_WAREHOUSE);
	}
	public void setIs_match_warehouse(String is_match_warehouse){
		this.is_match_warehouse = is_match_warehouse;
	}
	public String getIs_matched(){
		if(null!=list&&list.size()==1){
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		}else{
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		}
	}
	public void setIs_matched(String is_matched){
		this.is_matched = is_matched;
	}
}
