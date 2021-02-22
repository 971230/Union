package zte.net.ecsord.params.busi.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

/**
 * 
 * @author wu.i 订单数对象
 * 
 */
//@RequestBeanAnnontion(primary_keys = "id", table_name = "es_order_phone_info",depency_keys="order_id", service_desc = "订单号码表")
@RequestBeanAnnontion(primary_keys = "order_id", table_name = "es_order_phone_info", service_desc = "订单号码表")
public class OrderPhoneInfoBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//号码基本信息
	@RequestFieldAnnontion()
	private String id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String phone_num;
	@RequestFieldAnnontion()
    private String operatorState;
	@RequestFieldAnnontion()
    private String proKey;
	@RequestFieldAnnontion()
	private String occupiedFlag;
	@RequestFieldAnnontion()
	private String occupiedTime;
	@RequestFieldAnnontion()
    private String proKeyMode;
	//靓号信息 NiceInfo 
	@RequestFieldAnnontion()
	private String lhscheme_id;
	@RequestFieldAnnontion()
	private Integer classId;
	@RequestFieldAnnontion()
	private String advancePay;
	@RequestFieldAnnontion()
	private String advanceCom;
	@RequestFieldAnnontion()
	private String advanceSpe;
	@RequestFieldAnnontion()
	private String numThawTim;
	@RequestFieldAnnontion()
	private String numThawPro;
	@RequestFieldAnnontion()
	private String lowCostChe;
	@RequestFieldAnnontion()
	private String timeDurChe;
	@RequestFieldAnnontion()
	private int    changeTagChe;
	@RequestFieldAnnontion()
	private int    cancelTagChe;
	@RequestFieldAnnontion()
	private String bremonChe;
	@RequestFieldAnnontion()
	private String lowCostPro;
	@RequestFieldAnnontion()
	private String timeDurPro;
	@RequestFieldAnnontion()
	private int    changeTagPro;
	@RequestFieldAnnontion()
	private int    cancelTagPro;
	@RequestFieldAnnontion()
	private String broMonPro;
	@RequestFieldAnnontion()
	private String order_from;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String occupied_msg;//预占预定的操作信息(用来保存失败的信息 或者提示信息)
	@RequestFieldAnnontion()
	private String bssOccupiedFlag;//bss号码操作状态
	
	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderPhoneInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderPhoneInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);

	}
	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<OrderPhoneInfoBusiRequest> resp = new QueryResponse<OrderPhoneInfoBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this);
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
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

	public String getLhscheme_id() {
		return lhscheme_id;
	}
	public void setLhscheme_id(String lhscheme_id) {
		this.lhscheme_id = lhscheme_id;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
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
	public int getChangeTagChe() {
		return changeTagChe;
	}
	public void setChangeTagChe(int changeTagChe) {
		this.changeTagChe = changeTagChe;
	}
	public int getCancelTagChe() {
		return cancelTagChe;
	}
	public void setCancelTagChe(int cancelTagChe) {
		this.cancelTagChe = cancelTagChe;
	}
	public String getBremonChe() {
		return bremonChe;
	}
	public void setBremonChe(String bremonChe) {
		this.bremonChe = bremonChe;
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
	public int getChangeTagPro() {
		return changeTagPro;
	}
	public void setChangeTagPro(int changeTagPro) {
		this.changeTagPro = changeTagPro;
	}
	public int getCancelTagPro() {
		return cancelTagPro;
	}
	public void setCancelTagPro(int cancelTagPro) {
		this.cancelTagPro = cancelTagPro;
	}
	public String getBroMonPro() {
		return broMonPro;
	}
	public void setBroMonPro(String broMonPro) {
		this.broMonPro = broMonPro;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getOccupied_msg() {
		return occupied_msg;
	}
	public void setOccupied_msg(String occupied_msg) {
		this.occupied_msg = occupied_msg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getBssOccupiedFlag() {
		return bssOccupiedFlag;
	}
	public void setBssOccupiedFlag(String bssOccupiedFlag) {
		this.bssOccupiedFlag = bssOccupiedFlag;
	}
	
	
}
