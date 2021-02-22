package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 消息同步请求
 * @author 王昌磊
 */
public class QueryStdAddressReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	 
	@ZteSoftCommentAnnotationParam(name = "地址关键字", type = "String", isNecessary = "Y", desc = "")
	private String key_word;
	
	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "")
	private String region_id;
	
	@ZteSoftCommentAnnotationParam(name = "返回数据条数", type = "String", isNecessary = "Y", desc = "")
	private String row_num;
	
	@ZteSoftCommentAnnotationParam(name = "标准地址ID", type = "String", isNecessary = "Y", desc = "")
	private String std_addr_id;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
 
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getKey_word() {
		return key_word;
	}

	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getRow_num() {
		return row_num;
	}

	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}

	public String getStd_addr_id() {
		return std_addr_id;
	}

	public void setStd_addr_id(String std_addr_id) {
		this.std_addr_id = std_addr_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.qrystdaddr";
	}

	
}
