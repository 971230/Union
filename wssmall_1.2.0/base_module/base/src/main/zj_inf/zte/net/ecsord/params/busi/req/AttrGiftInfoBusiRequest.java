package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import zte.net.ecsord.common.AttrConsts;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="gift_inst_id",table_name="es_attr_gift_info",depency_keys="order_id",service_desc="赠品信息")
public class AttrGiftInfoBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String gift_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String inst_id; 
	@RequestFieldAnnontion()
	private String sku_id; //sku,giftid
	@RequestFieldAnnontion()
	private String sku_fee;
	@RequestFieldAnnontion()
	private String goods_name;
	@RequestFieldAnnontion()
	private String goods_type;
	@RequestFieldAnnontion()
	private String goods_category;
	@RequestFieldAnnontion()
	private String goods_desc;
	@RequestFieldAnnontion()
	private String is_gift;
	@RequestFieldAnnontion()
	private int sku_num;
	@RequestFieldAnnontion()
	private String gift_id;
	@RequestFieldAnnontion()
	private String gift_value;
	@RequestFieldAnnontion()
	private String gift_unit;
	@RequestFieldAnnontion()
	private String gift_desc;
	@RequestFieldAnnontion()
	private String gift_brand;
	@RequestFieldAnnontion()
	private String gift_model;
	@RequestFieldAnnontion()
	private String gift_color;
	@RequestFieldAnnontion()
	private String gift_sku;
	@RequestFieldAnnontion()
	private String is_process;
	@RequestFieldAnnontion()
	private String process_type;
	@RequestFieldAnnontion()
	private String process_desc;
	@RequestFieldAnnontion()
	private String gift_type;
	@RequestFieldAnnontion()
	private String is_virtual;
	@RequestFieldAnnontion()
	private String series_num;
	@RequestFieldAnnontion()
	private String iccid;
	@RequestFieldAnnontion()
	private String readid;
	@RequestFieldAnnontion()
	private String bss_status;
	@RequestFieldAnnontion()
	private String bss_desc;
	@RequestFieldAnnontion()
	private String bss_time;
	@RequestFieldAnnontion()
	private String col1;
	@RequestFieldAnnontion()
	private String col2;
	@RequestFieldAnnontion()
	private String col3;
	
	private Map<String, String> params;
	
	@RequestFieldAnnontion(dname="es_attr_gift_param",need_insert="no",desc="赠品参数列表信息",service_name="AttrGiftParamBusiRequest")
	List<AttrGiftParamBusiRequest>  giftParamBusiRequests = new ArrayList<AttrGiftParamBusiRequest>();
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrGiftInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrGiftInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrGiftInfoBusiRequest>> resp = new QueryResponse<List<AttrGiftInfoBusiRequest>>();
		T t = RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrGiftInfoBusiRequest>());
		loadGiftParamValue();
		return t;
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

	public String getGift_inst_id() {
		return gift_inst_id;
	}

	public void setGift_inst_id(String gift_inst_id) {
		this.gift_inst_id = gift_inst_id;
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

	public String getSku_id() {
		return sku_id;
	}

	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}

	public String getSku_fee() {
		return sku_fee;
	}

	public void setSku_fee(String sku_fee) {
		this.sku_fee = sku_fee;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getGoods_category() {
		return goods_category;
	}

	public void setGoods_category(String goods_category) {
		this.goods_category = goods_category;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getIs_gift() {
		return is_gift;
	}

	public void setIs_gift(String is_gift) {
		this.is_gift = is_gift;
	}

	public int getSku_num() {
		return sku_num;
	}

	public void setSku_num(int sku_num) {
		this.sku_num = sku_num;
	}
	public String getGift_id() {
		return gift_id;
	}

	public void setGift_id(String gift_id) {
		this.gift_id = gift_id;
	}

	public String getGift_value() {
		return gift_value;
	}

	public void setGift_value(String gift_value) {
		this.gift_value = gift_value;
	}

	public String getGift_unit() {
		return gift_unit;
	}

	public void setGift_unit(String gift_unit) {
		this.gift_unit = gift_unit;
	}

	public String getGift_desc() {
		return gift_desc;
	}

	public void setGift_desc(String gift_desc) {
		this.gift_desc = gift_desc;
	}

	public String getGift_brand() {
		return gift_brand;
	}

	public void setGift_brand(String gift_brand) {
		this.gift_brand = gift_brand;
	}

	public String getGift_model() {
		return gift_model;
	}

	public void setGift_model(String gift_model) {
		this.gift_model = gift_model;
	}

	public String getGift_color() {
		return gift_color;
	}

	public void setGift_color(String gift_color) {
		this.gift_color = gift_color;
	}

	public String getGift_sku() {
		return gift_sku;
	}

	public void setGift_sku(String gift_sku) {
		this.gift_sku = gift_sku;
	}

	public String getIs_process() {
		return is_process;
	}

	public void setIs_process(String is_process) {
		this.is_process = is_process;
	}

	public String getProcess_type() {
		return process_type;
	}

	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}

	public String getProcess_desc() {
		return process_desc;
	}

	public void setProcess_desc(String process_desc) {
		this.process_desc = process_desc;
	}

	public String getGift_type() {
		return gift_type;
	}

	public void setGift_type(String gift_type) {
		this.gift_type = gift_type;
	}

	public String getIs_virtual() {
		return is_virtual;
	}

	public void setIs_virtual(String is_virtual) {
		this.is_virtual = is_virtual;
	}

	@NotDbField
	public List<AttrGiftParamBusiRequest> getGiftParamBusiRequests() {
		return giftParamBusiRequests;
	}
	@NotDbField
	public void setGiftParamBusiRequests(
			List<AttrGiftParamBusiRequest> giftParamBusiRequests) {
		this.giftParamBusiRequests = giftParamBusiRequests;
		this.loadGiftParamValue();
	}
	
	@NotDbField
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getSeries_num() {
		return series_num;
	}

	public void setSeries_num(String series_num) {
		this.series_num = series_num;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getReadid() {
		return readid;
	}

	public void setReadid(String readid) {
		this.readid = readid;
	}
	public String getBss_status() {
		return bss_status;
	}

	public void setBss_status(String bss_status) {
		this.bss_status = bss_status;
	}
	public String getBss_desc() {
		return bss_desc;
	}

	public void setBss_desc(String bss_desc) {
		this.bss_desc = bss_desc;
	}
	public String getBss_time() {
		return bss_time;
	}

	public void setBss_time(String bss_time) {
		this.bss_time = bss_time;
	}
	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public void loadGiftParamValue()
	{
		String value="";
		if(this!=null)
		{
			this.params = new HashMap();
			for(AttrGiftParamBusiRequest attrGiftParamBusiRequest:giftParamBusiRequests)
			{
				
				if (attrGiftParamBusiRequest.getHas_value_code().equals("0")) {
					value = attrGiftParamBusiRequest.getParam_value();
				} else {
					value = attrGiftParamBusiRequest.getParam_value_code();
				}
			
				this.params.put(attrGiftParamBusiRequest.getParam_code(), value);
			}
			
		}
		
		
	}

}
