package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.AccBalanceResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-11 17:09
 * To change this template use File | Settings | File Templates.
 */
public class AccBalanceRequest extends ZteRequest<AccBalanceResponse> {
    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
    private String acc_nbr;
    
    @ZteSoftCommentAnnotationParam(name="本地网区号",type="String",isNecessary="Y",desc="本地网区号，带前导0")
    private String area_code;
    
    @ZteSoftCommentAnnotationParam(name="查询业务类型",type="String",isNecessary="Y",desc="查询业务类型：0 按账户查询")
    private String query_flag;
    
    @ZteSoftCommentAnnotationParam(name="来源",type="String",isNecessary="Y",desc="LLKJ_WT:网厅，LLKJ_AGENT:代理商")
    private String source_from;

	@ZteSoftCommentAnnotationParam(name="查询余额类型",type="String",isNecessary="Y",desc="0 表示查询对象拥有的余额账本,1 表示查询对象可以使用的余额账本")
    private String query_item_type;
    
    @ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(查询)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
    public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getQuery_flag() {
		return query_flag;
	}

	public void setQuery_flag(String query_flag) {
		this.query_flag = query_flag;
	}

	public String getQuery_item_type() {
		return query_item_type;
	}

	public void setQuery_item_type(String query_item_type) {
		this.query_item_type = query_item_type;
	}
    
    public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@Override
    public void check() throws ApiRuleException {
    	
    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"accBalance";
    }
}
