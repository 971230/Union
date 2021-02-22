package zte.net.ecsord.params.hs.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.HuashengOrderBusiRequest;
import zte.net.ecsord.params.busi.req.HuashengOrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.hs.resp.OrderCheckOutResp;
import zte.net.ecsord.params.hs.vo.OrderCheckOutHeader;
import zte.net.ecsord.params.hs.vo.OrderCheckOutHeaderItems;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * B2B订单出入库信息回传SAP
 * @author duan.shaochu
 *
 */
public class OrderCheckOutReq extends ZteRequest<OrderCheckOutResp> {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="采购凭证号",type="String",isNecessary="Y",desc="采购凭证号")
	private String EBELN;
	@ZteSoftCommentAnnotationParam(name="地点",type="String",isNecessary="Y",desc="地点")
	private String WERKS;
	@ZteSoftCommentAnnotationParam(name="供应商编码",type="String",isNecessary="Y",desc="供应商编码")
	private String LIFNR;
	@ZteSoftCommentAnnotationParam(name="仓储商接口类别",type="String",isNecessary="Y",desc="仓储商接口类别")
	private String ZJKLB;
	@ZteSoftCommentAnnotationParam(name="仓储商编码",type="String",isNecessary="Y",desc="仓储商编码")
	private String ZCCSBM;
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="Y",desc="预留字段1")
	private String RESERVE1;
	@ZteSoftCommentAnnotationParam(name="出库指令传输请求抬头",type="List",isNecessary="Y",desc="出库指令传输请求抬头")
	private List<OrderCheckOutHeader> HEADER = new ArrayList<OrderCheckOutHeader>();

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.hs.orderCheckOutB2B";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getEBELN() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null != tree && null != tree.getHuashengOrderBusiRequest() 
				&& tree.getHuashengOrderBusiRequest().size() > 0){
			HuashengOrderBusiRequest hsOrder = tree.getHuashengOrderBusiRequest().get(0);
			EBELN = hsOrder.getVbeln();
		}
		return EBELN;
	}

	public void setEBELN(String eBELN) {
		EBELN = eBELN;
	}

	public String getWERKS() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null != tree && null != tree.getHuashengOrderBusiRequest() 
				&& tree.getHuashengOrderBusiRequest().size() > 0){
			HuashengOrderBusiRequest hsOrder = tree.getHuashengOrderBusiRequest().get(0);
			WERKS = hsOrder.getWerks();
		}
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getLIFNR() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null != tree && null != tree.getHuashengOrderBusiRequest() 
				&& tree.getHuashengOrderBusiRequest().size() > 0){
			HuashengOrderBusiRequest hsOrder = tree.getHuashengOrderBusiRequest().get(0);
			LIFNR = hsOrder.getLifnr();
		}
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getZJKLB() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null != tree && null != tree.getHuashengOrderBusiRequest() 
				&& tree.getHuashengOrderBusiRequest().size() > 0){
			HuashengOrderBusiRequest hsOrder = tree.getHuashengOrderBusiRequest().get(0);
			//SAP传03下来,回传04
			//SAP传21下来,回传22
			ZJKLB = hsOrder.getZjklb();
			if(StringUtils.equals(ZJKLB, "03")){
				return "04";
			}else if(StringUtils.equals(ZJKLB, "21")){
				return "22";
			}
		}
		return ZJKLB;
	}

	public void setZJKLB(String zJKLB) {
		ZJKLB = zJKLB;
	}

	public String getRESERVE1() {
		return RESERVE1;
	}

	public void setRESERVE1(String rESERVE1) {
		RESERVE1 = rESERVE1;
	}

	public List<OrderCheckOutHeader> getHEADER() {
		//屏蔽无用代码 xiao.ruidan 20180518
		/*OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null != tree && null != tree.getHuashengOrderItemBusiRequest() 
				&& tree.getHuashengOrderItemBusiRequest().size() > 0){
			List<OrderCheckOutHeader> orderHeader = new ArrayList<OrderCheckOutHeader>();
			List<OrderItemExtvtlBusiRequest> orderItemExtvtls = tree.getOrderItemExtvtlBusiRequests();
			//串码管理
			String mchk = "";
			for(HuashengOrderItemBusiRequest hsOrderItem : tree.getHuashengOrderItemBusiRequest()){
				OrderCheckOutHeader oh = new OrderCheckOutHeader();
				oh.setEBELP(hsOrderItem.getEbelp());
				oh.setLGORT(hsOrderItem.getLgort());
				oh.setMATNR(hsOrderItem.getMatnr());
				oh.setMENGE(hsOrderItem.getMenge());
				oh.setXBLNR(hsOrderItem.getXblnr());
				oh.setSOBKZ(hsOrderItem.getSobkz() == null ? "" : hsOrderItem.getSobkz());
				//oh.setBKTXT("");
				//oh.setRESERVE2("");
				//oh.setRESERVE3("");
				mchk = CommonDataFactory.getInstance().getHSMCHK(hsOrderItem.getMatnr());
				if(null != orderItemExtvtls && orderItemExtvtls.size() > 0){
					List<OrderCheckOutHeaderItems> headItems = new ArrayList<OrderCheckOutHeaderItems>();
					for(OrderItemExtvtlBusiRequest extVtl : orderItemExtvtls){
						if(StringUtils.equals(extVtl.getSku(), hsOrderItem.getSku())){
							//串码管理[X]的商品才传串号
							if(StringUtils.equals(mchk, "X")){
								OrderCheckOutHeaderItems item = new OrderCheckOutHeaderItems();
								item.setCUANM(extVtl.getResources_code());
								item.setZLINE(extVtl.getZline());
								item.setEBELP(hsOrderItem.getEbelp());
								//item.setRESERVE4("");
								//item.setRESERVE5("");
								headItems.add(item);
							}
						}
					}
					oh.setITEMS(headItems);
				}
				orderHeader.add(oh);
			}
			return orderHeader;
		}*/
		return HEADER;
	}

	public void setHEADER(List<OrderCheckOutHeader> hEADER) {
		HEADER = hEADER;
	}

	public String getZCCSBM() {
		return "02";
	}

	public void setZCCSBM(String zCCSBM) {
		ZCCSBM = zCCSBM;
	}

	
}
