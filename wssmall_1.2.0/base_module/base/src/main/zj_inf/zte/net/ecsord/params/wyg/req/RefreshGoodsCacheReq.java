package zte.net.ecsord.params.wyg.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.wyg.vo.*;

public class RefreshGoodsCacheReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="请求id",type="String",isNecessary="Y",desc="reqId:请求id")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType:请求类型")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="商品id",type="String",isNecessary="Y",desc="goodsIds：商品id")
	private String goodsIds;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.refreshCache";
	}
	public String getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

}