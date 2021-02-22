package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

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
 * @author Rapon
 * @version 2016-07-21
 * @see 华盛订单信息表
 * 
 */
@RequestBeanAnnontion(primary_keys = "order_id", depency_keys = "order_id", table_name = "es_huasheng_order", service_desc = "华盛订单信息表")
public class HuashengOrderBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单号&主键")
	private String order_id	;
	@RequestFieldAnnontion(dname = "vbeln", desc = "出库单号")
	private String vbeln;
	@RequestFieldAnnontion(dname = "mjahr", desc = "年度")
	private String mjahr;
	@RequestFieldAnnontion(dname = "werks", desc = "地点代码")
	private String werks;
	@RequestFieldAnnontion(dname = "company_id", desc = "客户编码")
	private String company_id;
	@RequestFieldAnnontion(dname = "company_name", desc = "客户名称")
	private String company_name;
	@RequestFieldAnnontion(dname = "disvendor_code", desc = "分销编码")
	private String disvendor_code;
	@RequestFieldAnnontion(dname = "sf_origincode", desc = "顺丰原寄地代码--暂时写死,待SAP通过备注字段提供")
	private String sf_origincode;
	@RequestFieldAnnontion(dname = "sf_destcode", desc = "顺丰目的地代码")
	private String sf_destcode;
	@RequestFieldAnnontion(dname = "bstkd", desc = "退货申请单号")
	private String bstkd;
	@RequestFieldAnnontion(dname = "lgort", desc = "库存地点")
	private String lgort;
	@RequestFieldAnnontion(dname = "outinflag", desc = "出入库标示")
	private String outinflag;
	@RequestFieldAnnontion(dname = "post_company", desc = "退货物流公司")
	private String post_company;
	@RequestFieldAnnontion(dname = "post_number", desc = "退货物流单号")
	private String post_number;
	@RequestFieldAnnontion(dname = "zrkpc", desc = "入库批次(多次入库批次号从0001顺序编码)(WMS提供)")
	private String zrkpc;
	@RequestFieldAnnontion(dname = "zjklb", desc = "仓储商接口类别")
	private String zjklb;
	@RequestFieldAnnontion(dname = "lifnr", desc = "供应商编码")
	private String lifnr;
	@RequestFieldAnnontion(dname = "ebeln", desc = "采购凭证号(WMS提供)")
	private String ebeln;
	@RequestFieldAnnontion(dname = "source_from", desc = "")
	private String source_from;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<HuashengOrderBusiRequest> updateRequest = new ZteInstUpdateRequest<HuashengOrderBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<HuashengOrderBusiRequest>> resp = new QueryResponse<List<HuashengOrderBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<HuashengOrderBusiRequest>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getMjahr() {
		return mjahr;
	}

	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDisvendor_code() {
		return disvendor_code;
	}

	public void setDisvendor_code(String disvendor_code) {
		this.disvendor_code = disvendor_code;
	}

	public String getSf_origincode() {
		return sf_origincode;
	}

	public void setSf_origincode(String sf_origincode) {
		this.sf_origincode = sf_origincode;
	}

	public String getSf_destcode() {
		return sf_destcode;
	}

	public void setSf_destcode(String sf_destcode) {
		this.sf_destcode = sf_destcode;
	}

	public String getBstkd() {
		return bstkd;
	}

	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getOutinflag() {
		return outinflag;
	}

	public void setOutinflag(String outinflag) {
		this.outinflag = outinflag;
	}

	public String getPost_company() {
		return post_company;
	}

	public void setPost_company(String post_company) {
		this.post_company = post_company;
	}

	public String getPost_number() {
		return post_number;
	}

	public void setPost_number(String post_number) {
		this.post_number = post_number;
	}

	public String getZrkpc() {
		return zrkpc;
	}

	public void setZrkpc(String zrkpc) {
		this.zrkpc = zrkpc;
	}

	public String getZjklb() {
		return zjklb;
	}

	public void setZjklb(String zjklb) {
		this.zjklb = zjklb;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getEbeln() {
		return ebeln;
	}

	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}
