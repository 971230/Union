package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.List;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.PhotoInfoVO;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderPhotoInfoReq  extends ZteRequest{
	private static final long serialVersionUID = 6329382637012231542L;
	
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no：序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="busi_type：01 宽带开通类 02 号卡类业务")
	private String busi_type;
	@ZteSoftCommentAnnotationParam(name="订单中心订单号",type="String",isNecessary="Y",desc="order_id：订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="图片信息循环节点",type="List",isNecessary="Y",desc="photo_list：图片信息循环节点")
	private List<PhotoInfoVO> photo_list;
	
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
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public List<PhotoInfoVO> getPhoto_list() {
		return photo_list;
	}
	public void setPhoto_list(List<PhotoInfoVO> photo_list) {
		this.photo_list = photo_list;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.dealOrderPhotoInfoByRequ";
	}

}
