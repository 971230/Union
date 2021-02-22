package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;

import consts.ConstsCore;
/**
 * 联通订单归集页面处理
 * @wuhui
 */
public class EcsOrderAction extends WWAction {
  
  OrderTreeBusiRequest orderTree;
  private String order_id;
  
  /*
	 * 
	 * 
	 * 自定义属性标签举例说明
	 <html:orderattr  order_id ="201409305211134430" field_name="development_code"  field_desc ="发展人编码" ></html:orderattr> 
	 <html:orderattr  order_id ="201409305211134430" field_name="service_remarks" field_type='textarea'  style='width:600px;' field_desc ="客服备注" ></html:orderattr> 
	 <html:orderattr  order_id ="201409305211134430" field_name="order_from" field_value='126' field_desc ="订单来源" ></html:orderattr>
	 <html:orderattr  order_id ="201409305211134430" field_name="order_from" field_value='126' field_type="checkbox" field_desc ="订单来源" ></html:orderattr>
	 <html:orderattr  order_id ="201409305211134430" field_name="order_from" field_value='126' field_type="radio" field_desc ="订单来源" ></html:orderattr>
	 * 
	 */
	public String edit(){
		return "";
	}
	
	/**
	 * 页面保存
	 * @return
	 */
	public String editSave(){
		
		//校验前，先将校验值清空处理
		
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		ThreadContextHolder.setHttpRequest(getRequest());
//		String value =ThreadContextHolder.getHttpRequest().getParameter("oattrinstspec_201409305211134430_201409309204195946_1000071_service_remarks");
		String msg ="";
		//pageLoad属性验证方法校验
		List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		if(attrInstLoadResps.size()>0)
			msg= "调用load方法，返回错误校验信息";
//				
		attrInstLoadResps =AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_UPDATE);
		if(attrInstLoadResps.size()>0)
			msg= "调用update方法，返回错误校验信息";
		
		
		
//				
//				//待所有验证通过，处理逻辑获取界面修改脏数据,保存入库,增量数据入库处理
		List<AttrInstBusiRequest>  pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id,orderTree);
		for ( AttrInstBusiRequest pageAttrInstBusiRequest :pageAttrInstBusiRequests) {
			pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			pageAttrInstBusiRequest.store(); //属性数据入库
		}
		
		//
		
		
//		BusiCompRequest busi = new BusiCompRequest();
//		Map queryParams = new HashMap();
//		busi.setEnginePath("ZteActTraceRule.engineDo");
//		busi.setQueryParams(queryParams);
//		BusiCompResponse response = BusiCompPraser.getInstance().performBusiComp(busi);
//		
		
		return "";
	}
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
}
