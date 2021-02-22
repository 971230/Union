/**
 * 
 */
package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * @version 2015-12-23
 * @see 主副卡表
 * 
 */
@RequestBeanAnnontion(primary_keys = "inst_id", depency_keys = "order_id", table_name = "ES_ORDER_PHONE_INFO_FUKA", service_desc = "副卡信息表")
public class OrderPhoneInfoFukaBusiRequest extends ZteBusiRequest<ZteResponse>
		implements IZteBusiRequestHander {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6390261151775565252L;

	@RequestFieldAnnontion(dname = "inst_id", desc = "副卡实例ID", need_query = "yes")
	private String inst_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单ID", need_query = "no")
	private String order_id;
	@RequestFieldAnnontion(dname = "order_from", desc = "订单来源", need_query = "no")
	private String order_from;
	@RequestFieldAnnontion(dname = "phoneNum", desc = "副卡手机号码", need_query = "no")
	private String phoneNum;
	@RequestFieldAnnontion(dname = "is_old", desc = "新老用户标记", need_query = "no")
	private String is_old;
	@RequestFieldAnnontion(dname = "cardType", desc = "卡类型", need_query = "no")
	private String cardType;
	@RequestFieldAnnontion(dname = "userType", desc = "入网类型", need_query = "no")
	private String userType;
	@RequestFieldAnnontion(dname = "numType", desc = "主副卡标记", need_query = "no")
	private String numType;
	@RequestFieldAnnontion(dname = "productCode", desc = "产品编码", need_query = "no")
	private String productCode;
	@RequestFieldAnnontion(dname = "productName", desc = "产品名称", need_query = "no")
	private String productName;
	@RequestFieldAnnontion(dname = "productType", desc = "产品类型", need_query = "no")
	private String productType;
	@RequestFieldAnnontion(dname = "productNet", desc = "产品网别", need_query = "no")
	private String productNet;
	@RequestFieldAnnontion(dname = "productBrand", desc = "产品品牌编码", need_query = "no")
	private String productBrand;
	@RequestFieldAnnontion(dname = "firstMonbillMode", desc = "首月资费类型", need_query = "no")
	private String firstMonbillMode;
	@RequestFieldAnnontion(dname = "activityType", desc = "合约类型", need_query = "no")
	private String activityType;
	@RequestFieldAnnontion(dname = "activityCode", desc = "合约编码", need_query = "no")
	private String activityCode;
	@RequestFieldAnnontion(dname = "activityName", desc = "合约名称", need_query = "no")
	private String activityName;
	@RequestFieldAnnontion(dname = "actProtPer", desc = "合约期限", need_query = "no")
	private String actProtPer;
	@RequestFieldAnnontion(dname = "prokey", desc = "预占关键字", need_query = "no")
	private String prokey;
	@RequestFieldAnnontion(dname = "occupiedflag", desc = "操作类型", need_query = "no")
	private String occupiedflag;
	@RequestFieldAnnontion(dname = "operatorstate", desc = "操作结果状态", need_query = "no")
	private String operatorstate;
	@RequestFieldAnnontion(dname = "result_msg", desc = "操作结果描述", need_query = "no")
	private String result_msg;
	@RequestFieldAnnontion(dname = "source_from", desc = "订单来源", need_query = "no")
	private String source_from;
	@RequestFieldAnnontion(dname = "update_time", desc = "最后更新时间", need_query = "no")
	private String update_time;
	@RequestFieldAnnontion(dname = "bss_occupied_flag", desc = "BSS操作类型", need_query = "no")
	private String bss_occupied_flag;
	@RequestFieldAnnontion(dname = "oper_id", desc = "预占工号", need_query = "no")
	private String oper_id;
	@RequestFieldAnnontion(dname = "certType", desc = "副卡证件类型", need_query = "no")
	private String certType;
	@RequestFieldAnnontion(dname = "customerName", desc = "副卡证件名称", need_query = "no")
	private String customerName;
	@RequestFieldAnnontion(dname = "certNum", desc = "副卡证件号码", need_query = "no")
	private String certNum;
	@RequestFieldAnnontion(dname = "advancePay", desc = "预存话费金额", need_query = "no")
	private String advancePay;
	@RequestFieldAnnontion(dname = "advanceCom", desc = "普通预存", need_query = "no")
	private String advanceCom;
	@RequestFieldAnnontion(dname = "advanceSpe", desc = "专项预存", need_query = "no")
	private String advanceSpe;
	@RequestFieldAnnontion(dname = "numThawTim", desc = "返还时长", need_query = "no")
	private String numThawTim;
	@RequestFieldAnnontion(dname = "numThawPro", desc = "分月返还金额", need_query = "no")
	private String numThawPro;
	@RequestFieldAnnontion(dname = "classId", desc = "号码等级", need_query = "no")
	private String classId;
	@RequestFieldAnnontion(dname = "lowCostChe", desc = "考核期最低消费", need_query = "no")
	private String lowCostChe;
	@RequestFieldAnnontion(dname = "timeDurChe", desc = "考核时长", need_query = "no")
	private String timeDurChe;
	@RequestFieldAnnontion(dname = "changeTagChe", desc = "考核期是否过户", need_query = "no")
	private String changeTagChe;
	@RequestFieldAnnontion(dname = "cancelTagChe", desc = "考核期是否销户", need_query = "no")
	private String cancelTagChe;
	@RequestFieldAnnontion(dname = "breMonChe", desc = "考核期违约金月份", need_query = "no")
	private String breMonChe;
	@RequestFieldAnnontion(dname = "lowCostPro", desc = "协议期最低消费", need_query = "no")
	private String lowCostPro;
	@RequestFieldAnnontion(dname = "timeDurPro", desc = "协议时长", need_query = "no")
	private String timeDurPro;
	@RequestFieldAnnontion(dname = "changeTagPro", desc = "协议期是否过户", need_query = "no")
	private String changeTagPro;
	@RequestFieldAnnontion(dname = "cancelTagPro", desc = "协议期是否销户", need_query = "no")
	private String cancelTagPro;
	@RequestFieldAnnontion(dname = "broMonPro", desc = "协议期违约金月份", need_query = "no")
	private String broMonPro;
	@RequestFieldAnnontion(dname = "proKeyMode", desc = "号码资源预占关键字类型", need_query = "no")
	private String proKeyMode;
	@RequestFieldAnnontion(dname = "occupiedTime", desc = "号码变更时间", need_query = "no")
	private String occupiedTime;
	@RequestFieldAnnontion(dname = "CertLoseTime", desc = "证件失效时间", need_query = "no")
	private String CertLoseTime;
	@RequestFieldAnnontion(dname = "certAddr", desc = "证件地址", need_query = "no")
	private String certAddr;
	@RequestFieldAnnontion(dname = "sex", desc = "性别", need_query = "no")
	private String sex;
	@RequestFieldAnnontion(dname = "reliefPresFlag", desc = "减免预存标记", need_query = "no")
	private String reliefPresFlag;
	@RequestFieldAnnontion(dname = "saleMode", desc = "销售方式", need_query = "no")
	private String saleMode;
	@RequestFieldAnnontion(dname = "simId", desc = "SIM卡号", need_query = "no")
	private String simId;
	@RequestFieldAnnontion(dname = "goodsType", desc = "商品类型", need_query = "no")
	private String goodsType;
	@RequestFieldAnnontion(dname = "serType", desc = "付费类型", need_query = "no")
	private String serType;
	@RequestFieldAnnontion(dname = "checkType", desc = "认证类型", need_query = "no")
	private String checkType;
	@RequestFieldAnnontion(dname = "realNameType", desc = "实名认证类型", need_query = "no")
	private String realNameType;
	@RequestFieldAnnontion(dname = "proPacCode", desc = "产品包编码", need_query = "no")
	private String proPacCode;
	@RequestFieldAnnontion(dname = "proPacDesc", desc = "产品包说明", need_query = "no")
	private String proPacDesc;
	@RequestFieldAnnontion(dname = "resourcesBrand", desc = "终端品牌编码", need_query = "no")
	private String resourcesBrand;
	@RequestFieldAnnontion(dname = "resourcesModel", desc = "终端型号编码", need_query = "no")
	private String resourcesModel;
	@RequestFieldAnnontion(dname = "resourcesTypeId", desc = "终端机型编码", need_query = "no")
	private String resourcesTypeId;
	@RequestFieldAnnontion(dname = "resourcesColor", desc = "终端颜色编码", need_query = "no")
	private String resourcesColor;
	@RequestFieldAnnontion(dname = "resourcesCode", desc = "终端串号", need_query = "no")
	private String resourcesCode;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderPhoneInfoFukaBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderPhoneInfoFukaBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderPhoneInfoFukaBusiRequest>> resp = new QueryResponse<List<OrderPhoneInfoFukaBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<OrderPhoneInfoFukaBusiRequest>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIs_old() {
		return is_old;
	}

	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductNet() {
		return productNet;
	}

	public void setProductNet(String productNet) {
		this.productNet = productNet;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getFirstMonbillMode() {
		return firstMonbillMode;
	}

	public void setFirstMonbillMode(String firstMonbillMode) {
		this.firstMonbillMode = firstMonbillMode;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActProtPer() {
		return actProtPer;
	}

	public void setActProtPer(String actProtPer) {
		this.actProtPer = actProtPer;
	}

	public String getProkey() {
		return prokey;
	}

	public void setProkey(String prokey) {
		this.prokey = prokey;
	}

	public String getOccupiedflag() {
		return occupiedflag;
	}

	public void setOccupiedflag(String occupiedflag) {
		this.occupiedflag = occupiedflag;
	}

	public String getOperatorstate() {
		return operatorstate;
	}

	public void setOperatorstate(String operatorstate) {
		this.operatorstate = operatorstate;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getBss_occupied_flag() {
		return bss_occupied_flag;
	}

	public void setBss_occupied_flag(String bss_occupied_flag) {
		this.bss_occupied_flag = bss_occupied_flag;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getBreMonChe() {
		return breMonChe;
	}

	public void setBreMonChe(String breMonChe) {
		this.breMonChe = breMonChe;
	}

	public String getAdvancePay() {
		return advancePay;
	}

	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}

	public String getAdvanceCom() {
		return advanceCom;
	}

	public void setAdvanceCom(String advanceCom) {
		this.advanceCom = advanceCom;
	}

	public String getAdvanceSpe() {
		return advanceSpe;
	}

	public void setAdvanceSpe(String advanceSpe) {
		this.advanceSpe = advanceSpe;
	}

	public String getNumThawTim() {
		return numThawTim;
	}

	public void setNumThawTim(String numThawTim) {
		this.numThawTim = numThawTim;
	}

	public String getNumThawPro() {
		return numThawPro;
	}

	public void setNumThawPro(String numThawPro) {
		this.numThawPro = numThawPro;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLowCostChe() {
		return lowCostChe;
	}

	public void setLowCostChe(String lowCostChe) {
		this.lowCostChe = lowCostChe;
	}

	public String getTimeDurChe() {
		return timeDurChe;
	}

	public void setTimeDurChe(String timeDurChe) {
		this.timeDurChe = timeDurChe;
	}

	public String getChangeTagChe() {
		return changeTagChe;
	}

	public void setChangeTagChe(String changeTagChe) {
		this.changeTagChe = changeTagChe;
	}

	public String getCancelTagChe() {
		return cancelTagChe;
	}

	public void setCancelTagChe(String cancelTagChe) {
		this.cancelTagChe = cancelTagChe;
	}

	public String getLowCostPro() {
		return lowCostPro;
	}

	public void setLowCostPro(String lowCostPro) {
		this.lowCostPro = lowCostPro;
	}

	public String getTimeDurPro() {
		return timeDurPro;
	}

	public void setTimeDurPro(String timeDurPro) {
		this.timeDurPro = timeDurPro;
	}

	public String getChangeTagPro() {
		return changeTagPro;
	}

	public void setChangeTagPro(String changeTagPro) {
		this.changeTagPro = changeTagPro;
	}

	public String getCancelTagPro() {
		return cancelTagPro;
	}

	public void setCancelTagPro(String cancelTagPro) {
		this.cancelTagPro = cancelTagPro;
	}

	public String getBroMonPro() {
		return broMonPro;
	}

	public void setBroMonPro(String broMonPro) {
		this.broMonPro = broMonPro;
	}

	public String getProKeyMode() {
		return proKeyMode;
	}

	public void setProKeyMode(String proKeyMode) {
		this.proKeyMode = proKeyMode;
	}

	public String getOccupiedTime() {
		return occupiedTime;
	}

	public void setOccupiedTime(String occupiedTime) {
		this.occupiedTime = occupiedTime;
	}

	public String getCertLoseTime() {
		return CertLoseTime;
	}

	public void setCertLoseTime(String certLoseTime) {
		CertLoseTime = certLoseTime;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getReliefPresFlag() {
		return reliefPresFlag;
	}

	public void setReliefPresFlag(String reliefPresFlag) {
		this.reliefPresFlag = reliefPresFlag;
	}

	public String getSaleMode() {
		return saleMode;
	}

	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getRealNameType() {
		return realNameType;
	}

	public void setRealNameType(String realNameType) {
		this.realNameType = realNameType;
	}

	public String getProPacCode() {
		return proPacCode;
	}

	public void setProPacCode(String proPacCode) {
		this.proPacCode = proPacCode;
	}

	public String getProPacDesc() {
		return proPacDesc;
	}

	public void setProPacDesc(String proPacDesc) {
		this.proPacDesc = proPacDesc;
	}

	public String getResourcesBrand() {
		return resourcesBrand;
	}

	public void setResourcesBrand(String resourcesBrand) {
		this.resourcesBrand = resourcesBrand;
	}

	public String getResourcesModel() {
		return resourcesModel;
	}

	public void setResourcesModel(String resourcesModel) {
		this.resourcesModel = resourcesModel;
	}

	public String getResourcesTypeId() {
		return resourcesTypeId;
	}

	public void setResourcesTypeId(String resourcesTypeId) {
		this.resourcesTypeId = resourcesTypeId;
	}

	public String getResourcesColor() {
		return resourcesColor;
	}

	public void setResourcesColor(String resourcesColor) {
		this.resourcesColor = resourcesColor;
	}

	public String getResourcesCode() {
		return resourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}

}
