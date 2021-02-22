package zte.net.ecsord.params.hs.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.hs.resp.OrderCancelReceiveResp;
import zte.net.ecsord.params.hs.vo.Items;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 *  SAPB2C省分退货、拦截指令传输仓储商系统接口
 * @作者 Rapon
 * @创建日期 2016-07-22
 * @版本 V 1.0
 */
public class OrderCancelReceiveReq extends ZteRequest<OrderCancelReceiveResp> {
	@ZteSoftCommentAnnotationParam(name="退货申请单号",type="String",isNecessary="Y",desc="退货申请单号")
	private String 	BSTKD;
	@ZteSoftCommentAnnotationParam(name="原出库单号",type="String",isNecessary="Y",desc="原出库单号")
	private String ORI_VBELN;
	@ZteSoftCommentAnnotationParam(name="WERKS",type="String",isNecessary="Y",desc="WERKS")
	private String WERKS;
	@ZteSoftCommentAnnotationParam(name="库存地点",type="String",isNecessary="Y",desc="库存地点")
	private String LGORT;
	@ZteSoftCommentAnnotationParam(name="出入库标示传空",type="String",isNecessary="N",desc="出入库标示传空")
	private String OUTINFLAG;
	@ZteSoftCommentAnnotationParam(name="收货人地址",type="String",isNecessary="N",desc="收货人地址")
	private String STREET;
	@ZteSoftCommentAnnotationParam(name="收货人",type="String",isNecessary="N",desc="收货人")
	private String Buyer_Name;
	@ZteSoftCommentAnnotationParam(name="收货人电话号码",type="String",isNecessary="N",desc="收货人电话号码")
	private String TEL_NUMBER;
	@ZteSoftCommentAnnotationParam(name="配送类型A:上门提货 B:客户邮寄 C:客户拒收",type="String",isNecessary="Y",desc="配送类型A:上门提货 B:客户邮寄 C:客户拒收")
	private String POST_CATEGORY;
	@ZteSoftCommentAnnotationParam(name="物流公司",type="String",isNecessary="N",desc="物流公司")
	private String POST_COMPANY;
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="N",desc="物流单号")
	private String POST_NUMBER;
	@ZteSoftCommentAnnotationParam(name="明细表",type="String",isNecessary="N",desc="明细表")
	private List<Items> Items;
	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.hs.orderCancel";
	}
	public String getBSTKD() {
		return BSTKD;
	}
	public void setBSTKD(String bSTKD) {
		BSTKD = bSTKD;
	}
	public String getORI_VBELN() {
		return ORI_VBELN;
	}
	public void setORI_VBELN(String oRI_VBELN) {
		ORI_VBELN = oRI_VBELN;
	}
	public String getWERKS() {
		return WERKS;
	}
	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}
	public String getLGORT() {
		return LGORT;
	}
	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}
	public String getOUTINFLAG() {
		return OUTINFLAG;
	}
	public void setOUTINFLAG(String oUTINFLAG) {
		OUTINFLAG = oUTINFLAG;
	}
	public String getSTREET() {
		return STREET;
	}
	public void setSTREET(String sTREET) {
		STREET = sTREET;
	}
	public String getBuyer_Name() {
		return Buyer_Name;
	}
	public void setBuyer_Name(String buyer_Name) {
		Buyer_Name = buyer_Name;
	}
	public String getTEL_NUMBER() {
		return TEL_NUMBER;
	}
	public void setTEL_NUMBER(String tEL_NUMBER) {
		TEL_NUMBER = tEL_NUMBER;
	}
	public String getPOST_CATEGORY() {
		return POST_CATEGORY;
	}
	public void setPOST_CATEGORY(String pOST_CATEGORY) {
		POST_CATEGORY = pOST_CATEGORY;
	}
	public String getPOST_COMPANY() {
		return POST_COMPANY;
	}
	public void setPOST_COMPANY(String pOST_COMPANY) {
		POST_COMPANY = pOST_COMPANY;
	}
	public String getPOST_NUMBER() {
		return POST_NUMBER;
	}
	public void setPOST_NUMBER(String pOST_NUMBER) {
		POST_NUMBER = pOST_NUMBER;
	}
	public List<Items> getItems() {
		return Items;
	}
	public void setItems(List<Items> items) {
		Items = items;
	}
}
