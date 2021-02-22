package zte.net.ecsord.params.ecaop.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SubProOrderReq extends ZteRequest {
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="accessID",type="String",isNecessary="Y",desc="accessID")
	private String accessID;
	@ZteSoftCommentAnnotationParam(name="accessSEQ",type="String",isNecessary="Y",desc="accessSEQ")
	private String accessSEQ;
	@ZteSoftCommentAnnotationParam(name="accessPwd",type="String",isNecessary="Y",desc="accessPwd")
	private String accessPwd;
	@ZteSoftCommentAnnotationParam(name="businessCode",type="String",isNecessary="Y",desc="businessCode")
	private String businessCode;
	@ZteSoftCommentAnnotationParam(name="订购号码",type="String",isNecessary="Y",desc="订购号码")
	private String serial_number;
	@ZteSoftCommentAnnotationParam(name="业务受理地点",type="String",isNecessary="Y",desc="业务受理地点")
	private String depart_id;
	@ZteSoftCommentAnnotationParam(name="业务受理人",type="String",isNecessary="Y",desc="业务受理人")
	private String staff_id;
	@ZteSoftCommentAnnotationParam(name="产品编码",type="String",isNecessary="N",desc="产品编码")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="包编码",type="String",isNecessary="N",desc="包编码")
	private String package_id;
	@ZteSoftCommentAnnotationParam(name="元素编码",type="String",isNecessary="N",desc="元素编码")
	private String element_id;
	@ZteSoftCommentAnnotationParam(name="开关标志",type="String",isNecessary="Y",desc="开关标志:O-增加;C-删除;M-修改")
	private String oper_tag;
	@ZteSoftCommentAnnotationParam(name="生效模式",type="String",isNecessary="Y",desc="生效模式:NOW-立即生效;NEXTMONTH-下月生效;")
	private String enable_tag;
	@ZteSoftCommentAnnotationParam(name="主附产品标识",type="String",isNecessary="N",desc="主产品填 E，附加产品填 F")
	private String oper_type ;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="保留字段")
	private String reserver1 ;
	
	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getAccessID() {
		return accessID;
	}

	public void setAccessID(String accessID) {
		this.accessID = accessID;
	}

	public String getAccessSEQ() {
		return CommonDataFactory.getInstance().getAccessSEQ();
	}

	public void setAccessSEQ(String accessSEQ) {
		this.accessSEQ = accessSEQ;
	}

	public String getAccessPwd() {
		return accessPwd;
	}

	public void setAccessPwd(String accessPwd) {
		this.accessPwd = accessPwd;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getSerial_number() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(String depart_id) {
		this.depart_id = depart_id;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getElement_id() {
		return element_id;
	}

	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}

	public String getOper_tag() {
		return oper_tag;
	}

	public void setOper_tag(String oper_tag) {
		this.oper_tag = oper_tag;
	}

	public String getEnable_tag() {
		return enable_tag;
	}

	public void setEnable_tag(String enable_tag) {
		this.enable_tag = enable_tag;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getReserver1() {
		return reserver1;
	}

	public void setReserver1(String reserver1) {
		this.reserver1 = reserver1;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "soa.zte.net.iservice.impl.subProOrder";
	}

	public EmpOperInfoVo getEssOperInfo() {//此接口工号固定传SUPERUSR，essOperInfo仅作预留属性
//		if(essOperInfo==null){
//			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
//		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}
