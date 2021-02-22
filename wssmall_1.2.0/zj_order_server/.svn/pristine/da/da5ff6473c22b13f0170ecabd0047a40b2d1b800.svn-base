package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 2.3.5.	订单信息查询接口
 * 
 * @author 宋琪
 *
 * @date 2017年6月14日 
 */
public class OrderBaseInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="source_from",type="String",isNecessary="N",desc="source_from：订单来源")
	private String source_from;
	
	@ZteSoftCommentAnnotationParam(name="out_order_id",type="String",isNecessary="N",desc="out_order_id：外部订单号")
	private String out_order_id;
	
	@ZteSoftCommentAnnotationParam(name="order_status",type="String",isNecessary="N",desc="order_status：订单状态")
	private String order_status;
	
	@ZteSoftCommentAnnotationParam(name="order_city_code",type="String",isNecessary="N",desc="order_city_code：订单归属地市")
	private String order_city_code;
	
	@ZteSoftCommentAnnotationParam(name="county_id",type="String",isNecessary="N",desc="county_id：订单归属县分")
	private String county_id;
	
	@ZteSoftCommentAnnotationParam(name="create_time",type="String",isNecessary="N",desc="create_time：订单创建时间")
	private String create_time;
	
	@ZteSoftCommentAnnotationParam(name="bss_pre_order_id",type="String",isNecessary="N",desc="bss_pre_order_id：bssid")
	private String bss_pre_order_id;
	
	/**Add Wcl
	 * 返回订单异常信息
	 */
	@ZteSoftCommentAnnotationParam(name="exception_desc",type="String",isNecessary="N",desc="exception_desc:异常信息")
	private String exception_desc ;
	
	/**
	 * add sgf
	 */
	@ZteSoftCommentAnnotationParam(name="order_state",type="String",isNecessary="N",desc="order_state:订单处理状态")
    private String order_state ;
    
	@ZteSoftCommentAnnotationParam(name="lock_user_name",type="String",isNecessary="N",desc="order_state:处理人")
    private String lock_user_name ;
    

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getBss_pre_order_id() {
		return bss_pre_order_id;
	}

	public void setBss_pre_order_id(String bss_pre_order_id) {
		this.bss_pre_order_id = bss_pre_order_id;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getException_desc() {
		return exception_desc;
	}

	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getLock_user_name() {
        return lock_user_name;
    }

    public void setLock_user_name(String lock_user_name) {
        this.lock_user_name = lock_user_name;
    }
}
