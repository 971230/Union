package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 消息同步请求
 */
public class MessageSyncReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "唯一接口流水号", type = "String", isNecessary = "Y", desc = "serial_no")
	private String serial_no;
	
	@ZteSoftCommentAnnotationParam(name = "发起系统标识", type = "String", isNecessary = "Y", desc = "")
	private String source_system;
	
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "")
	private String receive_system;
	
	@ZteSoftCommentAnnotationParam(name = "记录接口动作", type = "String", isNecessary = "Y", desc = "A 新增"+
																								"M 修改"+
																								"D 停用")
	private String action;

	@ZteSoftCommentAnnotationParam(name = "商品同步类型", type = "String", isNecessary = "Y", desc = "goods 货品"+
																								 "product 商品"+
																								 "activity 活动"+
																								 "type 分类"+
																								 "brand 品牌"+
																								 "class 型号"+
																								 "tag 标签")
	private String type;
	
	@ZteSoftCommentAnnotationParam(name = "商品标识", type = "String", isNecessary = "Y", desc = "")
	private String seq_no;
	
	@ZteSoftCommentAnnotationParam(name = "接口调用时间", type = "String", isNecessary = "Y", desc = "")
	private String time;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}


	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getReceive_system() {
		return receive_system;
	}

	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}



	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.messageSync";
	}

	
}
