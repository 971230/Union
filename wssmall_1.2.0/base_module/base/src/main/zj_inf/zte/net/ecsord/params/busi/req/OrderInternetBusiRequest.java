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

@RequestBeanAnnontion(primary_keys = "internet_phone_info_id", table_name = "es_order_internet_info", depency_keys="order_id",service_desc = "")
public class OrderInternetBusiRequest extends ZteBusiRequest implements
		IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847544360443417683L;
	
	
	@RequestFieldAnnontion()
	private String internet_phone_info_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String order_from;
	@RequestFieldAnnontion
	private String lhscheme_id;
	@RequestFieldAnnontion
	private String pre_fee;
	@RequestFieldAnnontion
	private String advancePay;
	@RequestFieldAnnontion
	private String classId;
	@RequestFieldAnnontion
	private String lowCostPro;
	@RequestFieldAnnontion
	private String timeDurPro;
	@RequestFieldAnnontion
	private String is_new ;
	@RequestFieldAnnontion
	private String subs_id ;
	@RequestFieldAnnontion
	private String service_num ;
	@RequestFieldAnnontion
	private String evdo_num ;
	@RequestFieldAnnontion
	private String is_blankcard; 
	@RequestFieldAnnontion
	private String is_main_number ;
	@RequestFieldAnnontion
	private String number_level;
	@RequestFieldAnnontion
	private String scheme_id_l ;
	@RequestFieldAnnontion
	private String product_id ;
	@RequestFieldAnnontion
	private String scheme_id ;
	@RequestFieldAnnontion
	private String source_flag ;
	@RequestFieldAnnontion
	private String url_key ;
	@RequestFieldAnnontion
	private String http_request ;
	@RequestFieldAnnontion
	private String res_number ;
	@RequestFieldAnnontion
	private String http_request1 ;
	@RequestFieldAnnontion
	private String source_from ;
	@RequestFieldAnnontion
	private String user_kind ;
	@RequestFieldAnnontion
    private String sale_mode ;
	@RequestFieldAnnontion
    private String object_esn ;
	@RequestFieldAnnontion
    private String object_id ;
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderInternetBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderInternetBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderInternetBusiRequest>> resp = new QueryResponse<List<OrderInternetBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderFileBusiRequest>());
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

	public String getInternet_phone_info_id() {
		return internet_phone_info_id;
	}

	public void setInternet_phone_info_id(String internet_phone_info_id) {
		this.internet_phone_info_id = internet_phone_info_id;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getLhscheme_id() {
		return lhscheme_id;
	}

	public void setLhscheme_id(String lhscheme_id) {
		this.lhscheme_id = lhscheme_id;
	}

	public String getPre_fee() {
		return pre_fee;
	}

	public void setPre_fee(String pre_fee) {
		this.pre_fee = pre_fee;
	}

	public String getAdvancePay() {
		return advancePay;
	}

	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLowCostPro() {
		return lowCostPro;
	}

	public void setLowCostPro(String lowCostPro) {
		this.lowCostPro = lowCostPro;
	}

	public String getTimeDurPro() {
		return timeDurPro;
	}

	public void setTimeDurPro(String timeDurPro) {
		this.timeDurPro = timeDurPro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIs_new() {
		return is_new;
	}

	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}

	public String getSubs_id() {
		return subs_id;
	}

	public void setSubs_id(String subs_id) {
		this.subs_id = subs_id;
	}

	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getEvdo_num() {
		return evdo_num;
	}

	public void setEvdo_num(String evdo_num) {
		this.evdo_num = evdo_num;
	}

	public String getIs_blankcard() {
		return is_blankcard;
	}

	public void setIs_blankcard(String is_blankcard) {
		this.is_blankcard = is_blankcard;
	}

	public String getIs_main_number() {
		return is_main_number;
	}

	public void setIs_main_number(String is_main_number) {
		this.is_main_number = is_main_number;
	}

	public String getNumber_level() {
		return number_level;
	}

	public void setNumber_level(String number_level) {
		this.number_level = number_level;
	}

	public String getScheme_id_l() {
		return scheme_id_l;
	}

	public void setScheme_id_l(String scheme_id_l) {
		this.scheme_id_l = scheme_id_l;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getSource_flag() {
		return source_flag;
	}

	public void setSource_flag(String source_flag) {
		this.source_flag = source_flag;
	}

	public String getUrl_key() {
		return url_key;
	}

	public void setUrl_key(String url_key) {
		this.url_key = url_key;
	}

	public String getHttp_request() {
		return http_request;
	}

	public void setHttp_request(String http_request) {
		this.http_request = http_request;
	}

	public String getRes_number() {
		return res_number;
	}

	public void setRes_number(String res_number) {
		this.res_number = res_number;
	}

	public String getHttp_request1() {
		return http_request1;
	}

	public void setHttp_request1(String http_request1) {
		this.http_request1 = http_request1;
	}

    public String getSale_mode() {
        return sale_mode;
    }

    public void setSale_mode(String sale_mode) {
        this.sale_mode = sale_mode;
    }

    public String getObject_esn() {
        return object_esn;
    }

    public void setObject_esn(String object_esn) {
        this.object_esn = object_esn;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

}
