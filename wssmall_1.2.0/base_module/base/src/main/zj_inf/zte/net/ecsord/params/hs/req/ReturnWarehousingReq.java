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
import zte.net.ecsord.params.hs.resp.ReturnWarehousingResp;
import zte.net.ecsord.params.hs.vo.I_CUANMForReturnWare;
import zte.net.ecsord.params.hs.vo.ItemForReturnWare;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 *  退货入库传输SAP
 *  一级节点(订单)
 * @作者 Rapon
 * @创建日期 2016-07-23
 * @版本 V 1.0
 */
public class ReturnWarehousingReq extends ZteRequest<ReturnWarehousingResp> {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="退货申请单号",type="String",isNecessary="Y",desc="退货申请单号")
	private String ZTHNUM;
	@ZteSoftCommentAnnotationParam(name="原出库单号",type="String",isNecessary="Y",desc="原出库单号")
	private String ORI_VBELN;
	@ZteSoftCommentAnnotationParam(name="地点",type="String",isNecessary="Y",desc="地点")
	private String WERKS;
	@ZteSoftCommentAnnotationParam(name="库存地点",type="String",isNecessary="Y",desc="库存地点")
	private String LGORT;
	@ZteSoftCommentAnnotationParam(name="明细表",type="String",isNecessary="N",desc="明细表")
	private List<ItemForReturnWare> ITEMS;
	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.hs.returnWarehousing";
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getZTHNUM() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				ZTHNUM = order.getBstkd();
			}
		}
		return ZTHNUM;
	}
	public void setZTHNUM(String zTHNUM) {
		ZTHNUM = zTHNUM;
	}
	public String getORI_VBELN() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				ORI_VBELN = order.getVbeln();
			}
		}
		return ORI_VBELN;
	}
	public void setORI_VBELN(String oRI_VBELN) {
		ORI_VBELN = oRI_VBELN;
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
	public String getLGORT() {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderBusiRequest()&&tree.getHuashengOrderBusiRequest().size()>0){
			HuashengOrderBusiRequest order = tree.getHuashengOrderBusiRequest().get(0);
			if(null!=order){
				LGORT = order.getLgort();
			}
		}
		return LGORT;
	}
	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}
	public List<ItemForReturnWare> getITEMS() {
		//屏蔽无用代码 xiao.ruidan 20180518
		/*OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		if(null!=tree&&null!=tree.getHuashengOrderItemBusiRequest()&&tree.getHuashengOrderItemBusiRequest().size()>0){
			List<OrderItemExtvtlBusiRequest> orderItemExtvtls = tree.getOrderItemExtvtlBusiRequests();
			
			List<ItemForReturnWare> items = new ArrayList<ItemForReturnWare>();
			ItemForReturnWare item;
			List<I_CUANMForReturnWare> I_CUANMList;
			I_CUANMForReturnWare I_CUANM;
			//串码管理
			String mchk = "";
			for(HuashengOrderItemBusiRequest orderItem:tree.getHuashengOrderItemBusiRequest()){
				item = new ItemForReturnWare();
				item.setPOSNR(orderItem.getPosnr());
				item.setMENGE(orderItem.getMenge());
				item.setMEINS(orderItem.getMeins());
				item.setMATNR(orderItem.getMatnr());
				mchk = CommonDataFactory.getInstance().getHSMCHK(orderItem.getMatnr());
				if(null!=orderItemExtvtls&&orderItemExtvtls.size()>0){
					I_CUANMList = new ArrayList<I_CUANMForReturnWare>();
					for(OrderItemExtvtlBusiRequest orderItemExtvtl:orderItemExtvtls){
						if(StringUtils.equals(orderItem.getSku(), orderItemExtvtl.getSku())){
							//串码管理[X]的商品才传串号
							if(StringUtils.equals(mchk, "X")){
								I_CUANM = new I_CUANMForReturnWare();
								I_CUANM.setCUANM(orderItemExtvtl.getResources_code());
								I_CUANMList.add(I_CUANM);
							}
						}
					}
					item.setI_CUANM(I_CUANMList);
				}
				items.add(item);
			}
			ITEMS = items;
		}*/
		return ITEMS;
	}
	public void setITEMS(List<ItemForReturnWare> iTEMS) {
		ITEMS = iTEMS;
	}
}
