package zte.net.ecsord.params.zb.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PhoneInfo {
   @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="phoneNum：号码")
   private String phoneNum;
   @ZteSoftCommentAnnotationParam(name="靓号信息",type="String",isNecessary="N",desc="niceInfo：靓号信息")
   private NiceInfo niceInfo;
   @ZteSoftCommentAnnotationParam(name="操作状态",type="String",isNecessary="N",desc="operatorState：0：预占成功 1：预占失败 2：预订成功 3：预订失败 4：非付费预订预订成功 5：非付费预订预订失败")
   private String operatorState;//操作状态 0：预占成功 1：预占失败 2：预订成功 3：预订失败 4：非付费预订预订成功 5：非付费预订预订失败
   @ZteSoftCommentAnnotationParam(name="资源预占关键字",type="String",isNecessary="N",desc="proKey：资源预占关键字")
   private String proKey;//资源预占关键字
   @ZteSoftCommentAnnotationParam(name="号码状态标识",type="String",isNecessary="Y",desc="occupiedFlag：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源")
   private String occupiedFlag;//号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
   @ZteSoftCommentAnnotationParam(name="预占预定时间",type="String",isNecessary="N",desc="occupiedTime：yyyymmddhh24miss。OccupiedFlag:1，2，3的时候必填")
   private String occupiedTime;//yyyymmddhh24miss。OccupiedFlag:1，2，3的时候必填
   @ZteSoftCommentAnnotationParam(name="号码资源预占关键字类型",type="String",isNecessary="N",desc="proKeyMode：0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号'")
   private String proKeyMode;//号码资源预占关键字类型 0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号';
public String getPhoneNum() {
	return phoneNum;
}
public void setPhoneNum(String phoneNum) {
	this.phoneNum = phoneNum;
}
public NiceInfo getNiceInfo() {
	return niceInfo;
}
public void setNiceInfo(NiceInfo niceInfo) {
	this.niceInfo = niceInfo;
}
public String getOperatorState() {
	return operatorState;
}
public void setOperatorState(String operatorState) {
	this.operatorState = operatorState;
}
public String getProKey() {
	return proKey;
}
public void setProKey(String proKey) {
	this.proKey = proKey;
}
public String getOccupiedFlag() {
	return occupiedFlag;
}
public void setOccupiedFlag(String occupiedFlag) {
	this.occupiedFlag = occupiedFlag;
}
public String getOccupiedTime() {
	return occupiedTime;
}
public void setOccupiedTime(String occupiedTime) {
	this.occupiedTime = occupiedTime;
}
public String getProKeyMode() {
	return proKeyMode;
}
public void setProKeyMode(String proKeyMode) {
	this.proKeyMode = proKeyMode;
}
   
   
}
