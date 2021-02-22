package zte.net.ecsord.params.zb.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.zb.vo.Goods;
import zte.net.ecsord.params.zb.vo.orderattr.Operator;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class NotifyStringZBRequest extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;

	@ZteSoftCommentAnnotationParam(name = "操作人信息", type = "String", isNecessary = "N", desc = "OperatorInfo：操作人信息")
	private Operator OperatorInfo;

	@ZteSoftCommentAnnotationParam(name = "顺序号", type = "String", isNecessary = "N", desc = "ACCOUNT_INDEX：顺序号")
	private String ACCOUNT_INDEX;

	@ZteSoftCommentAnnotationParam(name = "节点", type = "List", isNecessary = "N", desc = "GoodsInfo：节点")
	private List<Goods> GoodsInfo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public Operator getOperatorInfo() {
		OperatorInfo = new Operator();
//		String lock_status = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_status();
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest=orderTree.getOrderLockBusiRequests();
		OrderLockBusiRequest orderLockBusiRequest=null;
		for(int i=0;i<orderLockRequest.size();i++){
			if(orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())){
				orderLockBusiRequest=orderLockRequest.get(i);
			}
		}
		String lock_status=orderLockBusiRequest.getLock_status();
		
		
		if(EcsOrderConsts.UNLOCK_STATUS.equals(lock_status)){
			String oper_code =  CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PRE_LOCK_USER_ID);
			if(StringUtils.isEmpty(oper_code)){
				oper_code = CommonDataFactory.getInstance().getOperatorCode(notNeedReqStrOrderId);
			}
			AdminUser adminUser = CommonDataFactory.getInstance().getOperatorInfo(oper_code);
			//zengxianlian
			OperatorInfo.setOperatorCode(adminUser.getUsername());
			OperatorInfo.setOperatorName(adminUser==null?"":adminUser.getRealname());
		}else {
//			String operatorID = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_id();
			String operatorID=orderLockBusiRequest.getLock_user_id();
			OperatorInfo.setOperatorCode(operatorID);
//			String operatorName = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_name();
			String operatorName = orderLockBusiRequest.getLock_user_name();
			OperatorInfo.setOperatorName(operatorName == null ? "" : operatorName);
		}
		return OperatorInfo;
	}
    
	public void setOperatorInfo(Operator operatorInfo) {
		OperatorInfo = operatorInfo;
	}

	public String getACCOUNT_INDEX() {
		return "1";
	}

	public void setACCOUNT_INDEX(String account_index) {
		ACCOUNT_INDEX = account_index;
	}

	public List<Goods> getGoodsInfo() {
		List<Goods> GoodsInformation = new ArrayList<Goods>();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.TYPE_ID);
		List<OrderItemProdBusiRequest> products = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
		for(OrderItemProdBusiRequest product : products){
			String type_code = product.getProd_spec_type_code();
			String prod_type_id = CommonDataFactory.getInstance().getTypeIdByTypeCode(type_code);
			String is_physical_prod = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_PHYSICAL, prod_type_id);
			
			//如果是号卡，则只有一个货品信息【套餐】，串号为空；如果是其他商品，遍历实物货品，串号为实物货品串号，如终端串号
			if((EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id) && EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(product.getProd_spec_type_code())) 
					|| ((!EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)) && EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical_prod))){
				Goods good = new Goods();
				
				good.setGoodsType(CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ZB_PROD_TYPE, type_id));

				OrderItemProdExtBusiRequest prodExt = product.getOrderItemProdExtBusiRequest();
				if ( good.getGoodsType()!=null
						&&(good.getGoodsType().equals(EcsOrderConsts.ZB_PRODUCT_TYPE_SJL)
						|| good.getGoodsType().equals(EcsOrderConsts.ZB_PRODUCT_TYPE_SWK))) {
					String terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
					if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id)){
						good.setGoodsKeyCode("");
					}else{
						good.setGoodsKeyCode(terminal_num);
					}					
					good.setGoodsTypeId(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, prod_type_id, null,SpecConsts.GOODS_SN));
				} else {
					good.setGoodsKeyCode("");
					good.setGoodsTypeId("");
				}
				good.setGoodsKeyName("终端串号");
				good.setSuppInfo(new ArrayList());			//暂无供货商信息
				String orig_fee = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PRO_ORIG_FEE);
				if(null == orig_fee || "".equals(orig_fee)){
					orig_fee = "0";
				}
				good.setGoodsFee(Double.valueOf(orig_fee).intValue() * 1000);
				good.setGoodsNum(1);
				GoodsInformation.add(good);
			}
		}
		
		return GoodsInformation;
	}

	public void setGoodsInfo(List<Goods> goodsInfo) {
		GoodsInfo = goodsInfo;
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
		return "com.zte.unicomService.zb.NotifyStringZB";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
}
