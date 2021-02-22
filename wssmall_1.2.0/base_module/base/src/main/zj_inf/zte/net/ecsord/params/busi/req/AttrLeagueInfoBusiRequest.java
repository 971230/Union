package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="league_inst_id",table_name="es_attr_league_info",depency_keys="order_id",service_desc="联盟信息")
public class AttrLeagueInfoBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1279009283160700836L;
	
	@RequestFieldAnnontion()
	private String league_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion()
	private String league_id;
	@RequestFieldAnnontion()
	private String league_name;
	@RequestFieldAnnontion()
	private String higher_league_id;
	@RequestFieldAnnontion()
	private String higher_league_name;

	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrLeagueInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrLeagueInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrLeagueInfoBusiRequest>> resp = new QueryResponse<List<AttrLeagueInfoBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrLeagueInfoBusiRequest>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLeague_inst_id() {
		return league_inst_id;
	}

	public void setLeague_inst_id(String league_inst_id) {
		this.league_inst_id = league_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getLeague_id() {
		return league_id;
	}

	public void setLeague_id(String league_id) {
		this.league_id = league_id;
	}

	public String getLeague_name() {
		return league_name;
	}

	public void setLeague_name(String league_name) {
		this.league_name = league_name;
	}

	public String getHigher_league_id() {
		return higher_league_id;
	}

	public void setHigher_league_id(String higher_league_id) {
		this.higher_league_id = higher_league_id;
	}

	public String getHigher_league_name() {
		return higher_league_name;
	}

	public void setHigher_league_name(String higher_league_name) {
		this.higher_league_name = higher_league_name;
	}

}
