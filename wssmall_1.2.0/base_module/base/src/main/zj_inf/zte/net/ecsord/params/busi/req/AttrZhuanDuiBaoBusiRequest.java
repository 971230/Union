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

@RequestBeanAnnontion(primary_keys = "out_tid", depency_keys = "order_id", table_name = "es_gd_zhuanduibao", service_desc = "转兑包")
public class AttrZhuanDuiBaoBusiRequest  extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {

	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String out_tid;
	@RequestFieldAnnontion()
	private String pmt_code;
	@RequestFieldAnnontion()
	private String goods_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String source_from;
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrZhuanDuiBaoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrZhuanDuiBaoBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrZhuanDuiBaoBusiRequest>> resp = new QueryResponse<List<AttrZhuanDuiBaoBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<AttrPackageActivityBusiRequest>());
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

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getPmt_code() {
		return pmt_code;
	}

	public void setPmt_code(String pmt_code) {
		this.pmt_code = pmt_code;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	
}
