package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftParamBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 同步订单信息到WMS系统
 * 订单信息对象
 */
public class NotifyOrderInfoVo implements Serializable{
	
	private static final long serialVersionUID = -567176338763017571L;
	
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="订单来源",type="String",isNecessary="Y",desc="TBFX:淘宝分销、 ZBWO:总部商城、 WO:沃商城、" +
			" PP:拍拍商城、 WBDR:外部导入、 JK:网盟集团客户、 TB:淘宝商城、 WAP:沃商城WAP、" +
			" VIP:VIP商城、 UHZ:U惠站、 WMTY:网盟统一接口、 WMQT:网盟其他来源")
	private String orderOrigin;
	@ZteSoftCommentAnnotationParam(name="归属地市",type="String",isNecessary="Y",desc="归属地市")
	private String orderCity;
	@ZteSoftCommentAnnotationParam(name="用户下单时间",type="String",isNecessary="Y",desc="用户下单时间(格式YYYY-MM-DD HH:mm:ss)")
	private String orderCreateTime;
	@ZteSoftCommentAnnotationParam(name="订单生成时间",type="String",isNecessary="Y",desc="订单生成时间(格式YYYY-MM-DD HH:mm:ss)")
	private String createTime;
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="Y",desc="订单状态[1：正常订单、2：换货单]")
	private String ordertype;
	@ZteSoftCommentAnnotationParam(name="订单生产模式",type="String",isNecessary="Y",desc="订单生产模式[01: 自动化生产模式、02：人工集中生产模式、03：现场生产模式、04：物流生产模式、05：独立模式]")
	private String orderModel;
	@ZteSoftCommentAnnotationParam(name="华盛订单类型",type="String",isNecessary="Y",desc="03：退货采购单 21：调拨出库单 ")
	private String hsOrderType;
	@ZteSoftCommentAnnotationParam(name="收货人信息",type="List<CarryInfoVo>",isNecessary="Y",desc="收货人信息")
	private List<CarryInfoVo> carryInfo;
	@ZteSoftCommentAnnotationParam(name="商品信息",type="List<NotifyGoodInfoVo>",isNecessary="Y",desc="商品信息")
	private List<NotifyGoodInfoVo> goodInfo;
	@ZteSoftCommentAnnotationParam(name="礼品信息",type="List<GiftInfoVo>",isNecessary="Y",desc="礼品信息")
	private List<GiftInfoVo> giftInfo;
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderOrigin() {
		return orderOrigin;
	}

	public void setOrderOrigin(String orderOrigin) {
		this.orderOrigin = orderOrigin;
	}

	public String getOrderCity() {
		return orderCity;
	}

	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}

	public String getOrderCreateTime() {
		
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getCreateTime() {
		
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrderModel() {
		return orderModel;
	}

	public void setOrderModel(String orderModel) {
		this.orderModel = orderModel;
	}

	public List<CarryInfoVo> getCarryInfo() {
		return carryInfo;
	}

	public void setCarryInfo(List<CarryInfoVo> carryInfo) {
		this.carryInfo = carryInfo;
	}

	public List<NotifyGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<NotifyGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}

	public List<GiftInfoVo> getGiftInfo() {
		
		return giftInfo;
	}

	public void setGiftInfo(List<GiftInfoVo> giftInfo) {
		this.giftInfo = giftInfo;
	}

	public String getHsOrderType() {
		return hsOrderType;
	}

	public void setHsOrderType(String hsOrderType) {
		this.hsOrderType = hsOrderType;
	}
	
}
