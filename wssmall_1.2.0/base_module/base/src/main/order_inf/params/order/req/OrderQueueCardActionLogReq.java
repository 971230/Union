package params.order.req;

import java.io.Serializable;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

/**
 * PC批量写卡日志表实体
 * @author duan.shaochu
 *
 */
public class OrderQueueCardActionLogReq extends ZteRequest implements Serializable {
	
	private String action_code;	//动作编码
	private String action_desc;	//动作描述
	private String action_time;	//动作时间
	private String order_id;	//内部订单号
	private String queue_code;	//队列编码
	private String iccid;	//ICCID
	private String machine_no;	//写卡机编码
	private String batch_id;	//批次号
	
	private String source_from;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getAction_code() {
		return action_code;
	}
	
	public void setAction_code(String action_code) {
		this.action_code = action_code;
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
	
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getQueue_code() {
		return queue_code;
	}
	
	public void setQueue_code(String queue_code) {
		this.queue_code = queue_code;
	}
	
	public String getIccid() {
		return iccid;
	}
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	public String getMachine_no() {
		return machine_no;
	}
	
	public void setMachine_no(String machine_no) {
		this.machine_no = machine_no;
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
	
}
