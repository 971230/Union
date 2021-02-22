package zte.net.ecsord.params.hs.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.HuashengOrderBusiRequest;
import zte.net.ecsord.params.busi.req.HuashengOrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.hs.resp.DeliveNotifyResp;
import zte.net.ecsord.params.hs.vo.ItemForDelNotify;
import zte.net.ecsord.params.hs.vo.ZSCHUANMForDelNotify;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 *  出库信息回传SAP
 * @作者 Rapon
 * @创建日期 2016-07-23
 * @版本 V 1.0
 */
public class DeliveNotifyReq extends ZteRequest<DeliveNotifyResp> {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="仓储商订单号",type="String",isNecessary="Y",desc="仓储商订单号")
	private String EBELN;
	@ZteSoftCommentAnnotationParam(name="出库单号",type="String",isNecessary="Y",desc="出库单号")
	private String MBLNR;
	@ZteSoftCommentAnnotationParam(name="年度",type="String",isNecessary="Y",desc="年度")
	private String MJAHR;
	@ZteSoftCommentAnnotationParam(name="地点代码",type="String",isNecessary="Y",desc="地点代码")
	private String WERKS;
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="Y",desc="预留字段1")
	private String RESERVE1;
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="N",desc="预留字段2")
	private String RESERVE2;
	@ZteSoftCommentAnnotationParam(name="预留字段3",type="String",isNecessary="N",desc="预留字段3")
	private String RESERVE3;
	@ZteSoftCommentAnnotationParam(name="明细表",type="String",isNecessary="N",desc="明细表")
	private List<ItemForDelNotify> Items;
	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.hs.deliveNotify";
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getEBELN() {
		EBELN = notNeedReqStrOrderId;
		return EBELN;
	}
	public void setEBELN(String eBELN) {
		EBELN = eBELN;
	}
	public String getMBLNR() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				MBLNR = order.getVbeln();
			}
		}
		return MBLNR;
	}
	public void setMBLNR(String mBLNR) {
		MBLNR = mBLNR;
	}
	public String getMJAHR() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				MJAHR = order.getMjahr();
			}
		}
		return MJAHR;
	}
	public void setMJAHR(String mJAHR) {
		MJAHR = mJAHR;
	}
	public String getWERKS() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				WERKS = order.getWerks();
			}
		}
		return WERKS;
	}
	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}
	public String getRESERVE1() {
		return RESERVE1;
	}
	public void setRESERVE1(String rESERVE1) {
		RESERVE1 = rESERVE1;
	}
	public String getRESERVE2() {
		return RESERVE2;
	}
	public void setRESERVE2(String rESERVE2) {
		RESERVE2 = rESERVE2;
	}
	public String getRESERVE3() {
		return RESERVE3;
	}
	public void setRESERVE3(String rESERVE3) {
		RESERVE3 = rESERVE3;
	}
	public List<ItemForDelNotify> getItems() {
		//屏蔽无用代码 xiao.ruidan 20180518
		/*OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderItemBusiRequest()&&tree.getHuashengOrderItemBusiRequest().size()>0){
			List<OrderItemExtvtlBusiRequest> orderItemExtvtls = tree.getOrderItemExtvtlBusiRequests();
			
			List<ItemForDelNotify> items = new ArrayList<ItemForDelNotify>();
			ItemForDelNotify item;
			List<ZSCHUANMForDelNotify> ZSCHUANMList;
			ZSCHUANMForDelNotify ZSCHUANM;
			//串码管理
			String mchk = "";
			for(HuashengOrderItemBusiRequest orderItem:tree.getHuashengOrderItemBusiRequest()){
				item = new ItemForDelNotify();
				item.setITEM(orderItem.getItem());
				item.setLGORT(orderItem.getLgort());
				item.setMATNR(orderItem.getMatnr());
				item.setEBELP(orderItem.getEbelp());
				item.setSOBKZ(orderItem.getSobkz());
				item.setMENGE(orderItem.getMenge());
				item.setMEINS(orderItem.getMeins());
				mchk = CommonDataFactory.getInstance().getHSMCHK(orderItem.getMatnr());
//				item.setRESERVE4("");
//				item.setRESERVE5("");
				if(null!=orderItemExtvtls&&orderItemExtvtls.size()>0){
					ZSCHUANMList = new ArrayList<ZSCHUANMForDelNotify>();
					for(OrderItemExtvtlBusiRequest orderItemExtvtl:orderItemExtvtls){
						if(StringUtils.equals(orderItem.getSku(), orderItemExtvtl.getSku())){
							//串码管理[X]的商品才传串号
							if(StringUtils.equals(mchk, "X")){
								ZSCHUANM = new ZSCHUANMForDelNotify();
								ZSCHUANM.setITEM(orderItem.getItem());
								ZSCHUANM.setCUANM(orderItemExtvtl.getResources_code());
								ZSCHUANM.setZLINE(orderItemExtvtl.getZline());
//								ZSCHUANM.setRESERVE6("");
//								ZSCHUANM.setRESERVE7("");
								ZSCHUANMList.add(ZSCHUANM);
							}
						}
					}
					item.setZSCHUANM(ZSCHUANMList);
				}
				items.add(item);
			}
			Items = items;
		}*/
		return Items;
	}
	public void setItems(List<ItemForDelNotify> items) {
		Items = items;
	}
}
