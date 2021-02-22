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
import zte.net.ecsord.utils.GetAreaInfoUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.DesUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

/**
 * 消息同步请求
 * @author 王昌磊
 */ 
public class AreaQueryReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	  
	@ZteSoftCommentAnnotationParam(name = "查询类型", type = "String", isNecessary = "Y", desc = "1为根据地址名称查询，2为根据百度坐标查询")
	private String type;
	
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String content;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
 
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getType() {
		return "1";//暂时定为根据地址查询
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		String address = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_address();
		String eparchyCode = GetAreaInfoUtils.getEparchyCode(notNeedReqStrOrderId);
		ICacheUtil util = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		String DES_KEY = util.getConfigInfo("DES_KEY");// unic0Mnetunic0m6
		try {
			content = DesUtil.encode("address="+address+"&eparchyCode="+eparchyCode, DES_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.yth.areaquery";
	}

	
}
