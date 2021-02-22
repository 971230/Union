package zte.net.ecsord.params.ecaop.req;

import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

@SuppressWarnings("rawtypes")
public class OrderQueryReq extends ZteRequest {
	
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;
	
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;
	
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "Y", desc = "opeSysType:办理业务系统")
	private String opeSysType;
	
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "ordersId:订单编号，业务受理系统订单ID")
	private String ordersId;
	
	@ZteSoftCommentAnnotationParam(name = "身份证号码", type = "String", isNecessary = "N", desc = "certNum:身份证号码，订单编码和身份证号码必须有一项为必填")
	private String certNum;
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParaVo> para;
	
	
	private EmpOperInfoVo essOperInfo;	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperatorId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		//固移融合商品
		String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String gyrh_cat_id = cacheUtil.getConfigInfo("gyrh_cat_id");
		if(gyrh_cat_id.contains(goods_cat_id)){
			opeSysType = "2";
		}
		return opeSysType;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("DIC_CB_BUSI_TYPE");
        String pname = "";
        for(int i=0;i<list.size();i++){
        	if(StringUtil.equals(goods_type, (String)list.get(i).get("pkey"))){
        		pname = (String)list.get(i).get("pname");
        		break;
        	}
        }
        if (pname.equals("1")||pname.equals("2")) {
        	ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BIP_CODE);
		}
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public List<ParaVo> getPara() {
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	@NotDbField
	public void check() throws ApiRuleException {}

	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.query.ordi.ordilist.qry";
	}
	
	public EmpOperInfoVo getEssOperInfo() {
//		if(essOperInfo==null){
//			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
//		}
		if (essOperInfo == null) {
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			if (StringUtil.isEmpty(opercode)) {
				opercode = "zjplxk";
			}
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='" + opercode + "' and a.CITY='"
					+ otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}


}
