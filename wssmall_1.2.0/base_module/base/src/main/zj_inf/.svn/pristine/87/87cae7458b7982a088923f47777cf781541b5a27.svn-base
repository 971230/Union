package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.utils.GetAreaInfoUtils;
import zte.net.ecsord.utils.ZjCommonUtils;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


import net.sf.json.JSONObject;

/**
 * 资源预判请求
 * @author 王昌磊
 */
public class ResourcePreCheckReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "serial_num", type = "String", isNecessary = "Y", desc = "serial_num:流水号")
	private String serial_num ;//流水号
	
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "operator_id:操作员")
	private String operator_id ;//操作员
	
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "office_id:操作点")
	private String office_id ; //操作点
	
	@ZteSoftCommentAnnotationParam(name = "是否省份调用 ", type = "String", isNecessary = "Y", desc = "is_province:1:是 0:否")
	private String is_province ;//是否省份调用 1:是 0:否
	
	@ZteSoftCommentAnnotationParam(name = "JSON参数格式串 ", type = "String", isNecessary = "Y", desc = "msg:JSON参数格式串")
	private Object msg;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_num() {
		try {
			serial_num = DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOperator_id() {
		operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		
		if(StringUtils.isEmpty(operator_id)) {
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		
		if(StringUtils.isEmpty(office_id)) {
			office_id=ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getIs_province() {
		return is_province;
	}

	public void setIs_province(String is_province) {
		this.is_province = is_province;
	}
	
	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.ecaop.resourceprecheck";
	}
}
