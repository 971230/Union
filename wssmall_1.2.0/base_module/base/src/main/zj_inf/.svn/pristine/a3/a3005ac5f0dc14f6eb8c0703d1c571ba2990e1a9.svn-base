package zte.net.ecsord.params.zb.vo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.wms.vo.GiftInfoVo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GoodInfo implements Serializable{
 

@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
 private String OrderId;
 @ZteSoftCommentAnnotationParam(name="商品编码",type="String",isNecessary="Y",desc="GoodsCode：商品编码")
 private String GoodsCode;
 
 @ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="GoodsName：商品名称")
 private String GoodsName;
 
 @ZteSoftCommentAnnotationParam(name="赠品信息",type="String",isNecessary="N",desc="GiftInfo：赠品信息")
 private String GiftInfo;
 
 @ZteSoftCommentAnnotationParam(name="商品应收",type="String",isNecessary="Y",desc="GoodsOrigFee:商品应收")
 private String GoodsOrigFee;
 
 @ZteSoftCommentAnnotationParam(name="商品实收",type="String",isNecessary="Y",desc="GoodsRealFee:商品实收")
 private String GoodsRealFee;
 
 @ZteSoftCommentAnnotationParam(name="商品减免原因",type="String",isNecessary="N",desc="GoodsReliefRes:商品减免原因")
 private String GoodsReliefRes;
 
 @ZteSoftCommentAnnotationParam(name="商品附属信息",type="String",isNecessary="N",desc="GoodsAttInfo:商品附属信息")
 private String GoodsAttInfo;
 
 @ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="Y",desc="GoodsType:商品类型")
 private String GoodsType;
 
 @ZteSoftCommentAnnotationParam(name="费用明细",type="String",isNecessary="N",desc="FeeInfo:费用明细")
 private String FeeInfo;
 
 @ZteSoftCommentAnnotationParam(name="流程信息",type="String",isNecessary="N",desc="FlowInfo:流程信息")
 private String FlowInfo;
 
 @ZteSoftCommentAnnotationParam(name="操作人信息",type="String",isNecessary="N",desc="OperatorInfo:操作人信息")
 private String OperatorInfo;
 public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
 public String getGoodsCode() {
  return GoodsCode;
 }
 public void setGoodsCode(String goodsCode) {
  GoodsCode = goodsCode;
 }
 public String getGoodsName() {
  return CommonDataFactory.getInstance().getGoodSpec(OrderId, null, SpecConsts.GOODS_NAME);
 }
 public void setGoodsName(String goodsName) {
  GoodsName = goodsName;
 }
 public String getGiftInfo() {
  return GiftInfo;
 }
 public void setGiftInfo(String giftInfo) {
  GiftInfo = giftInfo;
 }
 public String getGoodsOrigFee() {
	 return CommonDataFactory.getInstance().getGoodSpec(OrderId, null, AttrConsts.PRO_ORIG_FEE);
 }
 public void setGoodsOrigFee(String goodsOrigFee) {
  GoodsOrigFee = goodsOrigFee;
 }
 public String getGoodsRealFee() {
  return GoodsRealFee;
 }
 public void setGoodsRealFee(String goodsRealFee) {
  GoodsRealFee = goodsRealFee;
 }
 public String getGoodsReliefRes() {
  return GoodsReliefRes;
 }
 public void setGoodsReliefRes(String goodsReliefRes) {
  GoodsReliefRes = goodsReliefRes;
 }
 public String getGoodsAttInfo() {
  return GoodsAttInfo;
 }
 public void setGoodsAttInfo(String goodsAttInfo) {
  GoodsAttInfo = goodsAttInfo;
 }
 public String getGoodsType() {
  return GoodsType;
 }
 public void setGoodsType(String goodsType) {
  GoodsType = goodsType;
 }
 public String getFeeInfo() {
  return FeeInfo;
 }
 public void setFeeInfo(String feeInfo) {
  FeeInfo = feeInfo;
 }
 public String getFlowInfo() {
  return FlowInfo;
 }
 public void setFlowInfo(String flowInfo) {
  FlowInfo = flowInfo;
 }
 public String getOperatorInfo() {
  return OperatorInfo;
 }
 public void setOperatorInfo(String operatorInfo) {
  OperatorInfo = operatorInfo;
 }
 
 
 
 
}
