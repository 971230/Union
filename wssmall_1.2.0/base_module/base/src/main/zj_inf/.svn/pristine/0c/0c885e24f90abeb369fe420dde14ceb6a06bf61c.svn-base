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
 * @see 华盛订单商品信息表
 * 
 */
@RequestBeanAnnontion(primary_keys = "items_id", depency_keys = "order_id", table_name = "es_huasheng_order_item", service_desc = "华盛订单商品信息表")
public class HuashengOrderItemBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "items_id", desc = "主键")
	private String items_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "内部订单编号")
	private String order_id;
	@RequestFieldAnnontion(dname = "item", desc = "出库单行项目")
	private String item;
	@RequestFieldAnnontion(dname = "lgort", desc = "库位")
	private String lgort;
	@RequestFieldAnnontion(dname = "matnr", desc = "商品编码")
	private String matnr;
	@RequestFieldAnnontion(dname = "sobkz", desc = "经销/代销（K为代销、空为经销）")
	private String sobkz;
	@RequestFieldAnnontion(dname = "menge", desc = "数量")
	private String menge;
	@RequestFieldAnnontion(dname = "meins", desc = "单位，固定值：PCS")
	private String meins;
	@RequestFieldAnnontion(dname = "posnr", desc = "项次号")
	private String posnr;
	@RequestFieldAnnontion(dname = "sn", desc = "机型编码")
	private String sn;
	@RequestFieldAnnontion(dname = "ebelp", desc = "采购凭证的项目编号")
	private String ebelp;
	@RequestFieldAnnontion(dname = "xblnr", desc = "物流公司入库单号(WMS提供)")
	private String xblnr;
	@RequestFieldAnnontion(dname = "source_from")
	private String source_from;
	@RequestFieldAnnontion(dname = "sku")
	private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<HuashengOrderItemBusiRequest> updateRequest = new ZteInstUpdateRequest<HuashengOrderItemBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<HuashengOrderItemBusiRequest>> resp = new QueryResponse<List<HuashengOrderItemBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<HuashengOrderItemBusiRequest>());
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

	public String getItems_id() {
		return items_id;
	}

	public void setItems_id(String items_id) {
		this.items_id = items_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getSobkz() {
		return sobkz;
	}

	public void setSobkz(String sobkz) {
		this.sobkz = sobkz;
	}

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	public String getPosnr() {
		return posnr;
	}

	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getEbelp() {
		return ebelp;
	}

	public void setEbelp(String ebelp) {
		this.ebelp = ebelp;
	}

	public String getXblnr() {
		return xblnr;
	}

	public void setXblnr(String xblnr) {
		this.xblnr = xblnr;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}
