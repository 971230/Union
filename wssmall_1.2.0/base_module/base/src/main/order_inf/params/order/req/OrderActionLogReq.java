package params.order.req;

import java.io.Serializable;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

/**
 * @author Rapon
 * OrderActionLogReq.java
 * 2016-10-21
 */
public class OrderActionLogReq extends ZteRequest implements Serializable {
	
	private String action; //动作
	private String action_desc; //描述
	private String action_time; //时间
	private String write_station_code; //写卡位编码
	private String order_id; //上传写卡组编码时间
	private String write_machine_code; //分配写卡机时间
	private String batch_id; //批次号
	private String source_from; // 来源
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAction_desc() {
		return action_desc;
	}
	public void setAction_desc(String action_desc) {
		this.action_desc = action_desc;
	}
	public String getAction_time() {
		return action_time;
	}
	public void setAction_time(String action_time) {
		this.action_time = action_time;
	}
	public String getWrite_station_code() {
		return write_station_code;
	}
	public void setWrite_station_code(String write_station_code) {
		this.write_station_code = write_station_code;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getWrite_machine_code() {
		return write_machine_code;
	}
	public void setWrite_machine_code(String write_machine_code) {
		this.write_machine_code = write_machine_code;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
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
}
