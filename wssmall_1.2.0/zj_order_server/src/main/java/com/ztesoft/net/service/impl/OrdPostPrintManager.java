package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.DeliveryItemsQueryByDeIdReq;
import zte.params.order.resp.DeliveryItemsQueryByDeIdResp;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.DeliveryPrints;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IOrdPostPrintManager;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.sqls.SF;


/**
 * 物流打印处理类
 */
public class OrdPostPrintManager extends BaseSupport implements IOrdPostPrintManager {
	@Resource
	private IOrderServices orderServices;
	@Resource
	private IOrderSupplyManager iOrderSupplyManager;
	/**
	 * 查询物流单数据
	 * @param order_id 订单id
	 * @param order_ids 多个订单的id  目前设计为一个订单
	 * @param logi_no 物流单ID
	 * @param sending_type 配送方式
	 * @param n_shipping_amount 运费
	 * @param post_type 物流类型 0正常发货、1补寄、2重发
	 * @param delivery_id  物流单id
	 *  @param order_is_his  1-是历史单据
	 * @param logi_company 物流公司id
	 * retrun  DeliveryPrints 
	 */
	public DeliveryPrints queryPostDefaultInfo(String order_id,String order_ids,String sending_type,String n_shipping_amount,String post_type,String delivery_id,String order_is_his,String logi_company) {
		String orderIdsel=order_id;
		DeliveryPrints orderPostPrintvo=new DeliveryPrints();
		orderPostPrintvo.setIs_insur(EcsOrderConsts.IS_INSUR_NO);
		//处理勾选订单的信息累加
		Double feeds = 0.00; //原价和
		String isCod = EcsOrderConsts.IS_COD_NO; //非代收
		String dsPayment = "";//没用上
		String postItems = "";
		String postItemtTmp = "";
		List<String> postItemList = new ArrayList<String>();
		String isInsur = EcsOrderConsts.IS_INSUR_NO; //非保价,实物需要保价

		
		
			String giftstr ="";
			String    shipType=null;//配送方式
			OrderTreeBusiRequest orderTreeSel=null;
			if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//是历史单（重发、补寄情况才有）
				orderTreeSel= CommonDataFactory.getInstance().getOrderTreeHis(orderIdsel);
				shipType=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.SENDING_TYPE);
			}else{
				//if(post_type.equals(EcsOrderConsts.LOGIS_NORMAL)){//正常
				//	CommonDataFactory.getInstance().updateOrderTree(order_id);
				//}
				orderTreeSel= CommonDataFactory.getInstance().getOrderTree(orderIdsel);
				shipType=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
			}

			if(post_type.equals(EcsOrderConsts.LOGIS_SUPPLY)){//补寄
				orderPostPrintvo.setDelivery_id(delivery_id);
				List<DeliveryItem> itemsList=new ArrayList<DeliveryItem>();
				if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//是历史单
					List<OrderDeliveryItemBusiRequest>	list=iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
					for (OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest : list) {
						DeliveryItem item=new DeliveryItem();
						item.setName(orderDeliveryItemBusiRequest.getName());
						item.setNum(orderDeliveryItemBusiRequest.getNum());
						itemsList.add(item);
					}
				}else{
					
					DeliveryItemsQueryByDeIdReq req=new DeliveryItemsQueryByDeIdReq();
					req.setDelivery_id(delivery_id);
					DeliveryItemsQueryByDeIdResp resp=orderServices.queryDeliveryItemsByDeId(req);
					itemsList=resp.getDeliveryItems();
				}
				
				if(itemsList!=null&&itemsList.size()>0){
					for (int i = 0; i < itemsList.size(); i++) {
						DeliveryItem items=itemsList.get(i);
						String cardCnt=items.getName()+"×"+items.getNum()+"  ";
						if(i==0){
							giftstr ="、补寄品（ ";
						}
						giftstr =giftstr+cardCnt;
						if(i==(itemsList.size()-1)){
							giftstr =giftstr+"）";
						}
					}
				}
				postItemList.add(giftstr);
			}else{//正常、重发
				OrderBusiRequest  orderBusi=orderTreeSel.getOrderBusiRequest();
				if(post_type.equals(EcsOrderConsts.LOGIS_NORMAL)){//正常
					List<OrderDeliveryBusiRequest>  deliveryList=orderTreeSel.getOrderDeliveryBusiRequests();
						for (OrderDeliveryBusiRequest delivery : deliveryList) {
							if(post_type.equals(delivery.getDelivery_type())){
								orderPostPrintvo.setDelivery_id(delivery.getDelivery_id());//正常物流只有一个
							}
				    }
						
				}else{//重发
					orderPostPrintvo.setDelivery_id(delivery_id);
				}
				if(EcsOrderConsts.POST_COMPANY_ID_ZJS0001.equals(logi_company)||EcsOrderConsts.POST_COMPANY_ID_ZJS_JZ.equals(logi_company)||
						EcsOrderConsts.POST_COMPANY_ID_ZJS_JZ_DS.equals(logi_company)){//宅急送设置为保价,并打印保价金额
					isInsur = EcsOrderConsts.IS_INSUR_YES;
				}
				//如果订单货到付款，就选中代收款
				if(EcsOrderConsts.PAY_TYPE_HDFK.equals(orderBusi.getPay_type()) || !EcsOrderConsts.PAY_STATUS_PAYED.equals(StringUtil.toString(orderBusi.getPay_status()))){ //货到付款，未支付
					isCod = EcsOrderConsts.IS_COD_YES; //是代收
					//dsPayment = "02"; //已支付
				}
				Double payMoney=0.0;
				if(orderBusi.getPaymoney()!=null){
					payMoney=orderBusi.getPaymoney();
				}
				if(EcsOrderConsts.PAY_TYPE_HDFK.equals(orderBusi.getPay_type()))
				{
					payMoney = 0.0;
				}
	        	//Double feetmp = orderBusi.getOrder_amount()-payMoney; //原价（和代收费用一致）代收金额=订单价格-已收金额
				//feeds += feetmp;
//				feeds=orderBusi.getOrder_amount();//确认用订单金额就可以
				feeds=orderBusi.getPaymoney();//20150619确认用实收金额
				//是否有赠品
				List<AttrGiftInfoBusiRequest> gifList=orderTreeSel.getGiftInfoBusiRequests();
				if(gifList!=null&&gifList.size()>0){
					giftstr ="、赠品";
					/*for (int i = 0; i < gifList.size(); i++) {
						AttrGiftInfoBusiRequest gif=gifList.get(i);
						String cardCnt=gif.getGoods_name()+"×"+gif.getSku_num()+"  ";
						if(i==0){
							giftstr ="、赠品（ ";
						}
							giftstr =giftstr+cardCnt;
						if(i==(gifList.size()-1)){
							giftstr =giftstr+"）";
						}
					}*/
				}

				String goodType =null;	
				List<OrderItemBusiRequest>  OrderItemlist=orderTreeSel.getOrderItemBusiRequests();
				List<Map> goodsTypeList=CommonDataFactory.getInstance().listDcPublicData(AttrConsts.GOODS_TYPE);
				  for (int i = 0; i < OrderItemlist.size(); i++) { //订单商品单信息, 一个订单一个商品
					String goodsId = OrderItemlist.get(i).getGoods_id();
					/*if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
						//goodType = CommonDataFactory.getInstance().getGoodSpec(orderIdsel,goodsId,SpecConsts.TYPE_ID);
					}else{*/
					if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//是历史单
						goodType = CommonDataFactory.getInstance().getGoodSpecHis(orderIdsel,goodsId,SpecConsts.TYPE_ID);
					}else{
						goodType = CommonDataFactory.getInstance().getGoodSpec(orderIdsel,goodsId,SpecConsts.TYPE_ID);
					}
						
						
					/*}*/
					
					if(goodType==null){
						goodType = "";	
					}
					for (Map map : goodsTypeList) {
						String pkey= Const.getStrValue(map, "pkey");
						String codec= Const.getStrValue(map, "codec");
							if(pkey.equals(goodType)){
								postItemtTmp=";"+codec+giftstr;
							}
							if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goodType)||EcsOrderConsts.GOODS_TYPE_PHONE.equals(goodType)
									||EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(goodType)){
									isInsur = EcsOrderConsts.IS_INSUR_YES; // 有实物则保价：合约机、裸机、配件
							}
					}
					String phoneNum=null;
					if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
						 phoneNum=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,AttrConsts.PHONE_NUM);
					}else{
						 phoneNum=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.PHONE_NUM);
					}
					if(phoneNum!=null){
		            	 postItemtTmp += "("+phoneNum+")";
		             }
					if(!postItemList.contains(postItemtTmp)){
						postItemList.add(postItemtTmp);
					}
					
				}
			}

		//orderPostPrintvo.setIs_insur(isInsur);  所有订单都不保价 奥叔与生产口确认 2016-01-13
		orderPostPrintvo.setIs_insur(EcsOrderConsts.IS_INSUR_NO);
		orderPostPrintvo.setIs_cod(isCod);
		orderPostPrintvo.setDs_payment(dsPayment);
		orderPostPrintvo.setInsur_value(feeds.toString()); //保价费,所有订单保价费
		orderPostPrintvo.setCod_price(feeds.toString());	 //代收货款，与保价费用一致
		for(String tmp : postItemList){ //构造明细
			postItems += tmp;
		}				
		if(postItems.length()>0){
			orderPostPrintvo.setPost_items(postItems.substring(1));		
		}else{
			orderPostPrintvo.setPost_items(postItems);		
		}
		orderPostPrintvo.setCarry_fee(n_shipping_amount);
		if((shipType!=null&&shipType.equals(EcsOrderConsts.SHIP_TYPE_MJBY))||EcsOrderConsts.SHIP_TYPE_MJBY.equals(sending_type)){
        	orderPostPrintvo.setPostage_mode(EcsOrderConsts.POSTAGE_MODE_JFDSF);
        }else{
        	orderPostPrintvo.setPostage_mode(EcsOrderConsts.POSTAGE_MODE_JF);
        }
		return orderPostPrintvo;
	}
	
	/**
	 * 获取物流公司详细信息
	 * @param logi_company 物流公司ID
	 * @return  Map  （key是表es_logi_company_person的字段名）
	 */
	@Override
	public Map<String,String> queryPostModel(String logi_company, String order_id) {
		Map<String,String> rMap=new HashMap<String, String>();
		if(!StringUtils.isEmpty(logi_company) ){
			//1、根据物流公司id 查询物流公司编码 company_code
			IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			List<Map> logiCompanies = proxy.getLogiCompanyList();
			String company_code = logi_company;
			for(Map company : logiCompanies){
				if(logi_company.equals(Const.getStrValue(company, "id"))){
					company_code = Const.getStrValue(company, "company_code");
				}
			}
			
			//2、取物流公司联系人
			if(!StringUtils.isEmpty(company_code)){
				try {
					String org_code="";
					List<Logi> logi_company_list = new ArrayList<Logi>();
					String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "order_city_code");
					 org_code = CommonDataFactory.getInstance().getLanCode(order_city_code);
					 if(org_code==null){org_code="";}
//					String org_id=ManagerUtils.getAdminUser().getOrg_id();
					/*String sql="select t.col4 from Es_Organization t "
	                        +" where  t.party_id = '"+org_id+"'";
					Map col4Map=this.baseDaoSupport.queryForMap(sql);
					if(col4Map!=null&&!col4Map.isEmpty()){
						org_code=Const.getStrValue(col4Map, "col4");
					}
					if(org_code==null){
						org_code="";
					}*/
					List<Map> LogiCompanyPersonList = proxy.getLogiCompanyPersonList();
					for(Map Person : LogiCompanyPersonList){
						if(org_code.equals(Const.getStrValue(Person, "org_code"))&&company_code.equals(Const.getStrValue(Person, "post_id"))){
							rMap=Person;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
		return rMap;
	}
	
	/**
	 * 保存物流打印补充信息
	 */
	public String doAddOne(DeliveryPrints prints){
		String delvery_print_id="";
			if(prints!=null){
				prints.setCmp_remarks(prints.getDeliver_info());
				prints.setPost_num(EcsOrderConsts.POST_NUM);//一个数量
				delvery_print_id=this.baseDaoSupport.getSequences("S_ES_DELIVERY_PRINTS");
				prints.setDelvery_print_id(delvery_print_id);
				this.baseDaoSupport.insert("es_delivery_prints", prints);
			}
	return delvery_print_id;

	}
	
	/**
	 * 接收自定义标签的参数 
	 * @param name属性名称
	 * @param order_id 
	 * retrun 参数的值
	 */
	public String getParamByLikeName(String order_id,String name){
		String val="";
		Map attrInstMap = new HashMap();
    	HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    	if(request ==null)
    		return val;
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(key.indexOf(AttrBusiInstTools.getAttrInstElementPrefix(order_id))>-1&&key.indexOf(name)>-1){
            	String values []= request.getParameterValues(key);
            	String prefix =",";
            	
            	for (int i = 0; i < values.length; i++) {
        			if(i==values.length-1)
        				prefix="";
        			val += values[i]+prefix;
        			break;
        		}
            	
            }
        }
        return val;
	}
	
	
	public OrderDeliveryItemBusiRequest addDeliveryItem(OrderDeliveryItemBusiRequest item){
		String itemId=this.baseDaoSupport.getSequences("S_ES_DELIVERY_ITEM");
		item.setItem_id(itemId);
		
		this.baseDaoSupport.insert("es_delivery_item", item);
		return item;
		
	}
	@Override
	public String queryEntityProdInfos(String order_id){
		String  cacheName = "";
		String key = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List list = dc.loadData("DC_MODE_GOODS_TYPE");
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if(value.equals(key)){
					cacheName = (String) map.get("value_desc");
				}
				
			}
		}
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String groupFlowKg = cacheUtil.getConfigInfo("kg_plan_title");
	    String kg_plans = cacheUtil.getConfigInfo("kg_plans");
	    String plan_title = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PLAN_TITLE);
	    String goods_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODSNAME);
	    
		if("1".equals(kg_plans)){
		      String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
             if("1".equals(groupFlowKg)&&!StringUtil.isEmpty(goods_name) && !goods_name.equals(plan_title)){
                 plan_title  = " 商品： "+ goods_name;
             }else{
                 plan_title  = " 商品： "+ plan_title;
             }
             
             String object_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_name");
             if("170502112412000711".equals(key)&&"10093".equals(order_from)){
                 plan_title = "终端型号:" + object_name;
             }
             
             String terminal = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);      //货品-手机
             if(SpecConsts.TYPE_ID_20002.equals(key)||SpecConsts.TYPE_ID_20003.equals(key)){  //合约机  裸机
                 plan_title = "终端型号:"+terminal;
             }
             
             String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
             String cacheName1="";
             if(!StringUtils.isEmpty(phone_num)){
                 cacheName1 ="开户号码：" + phone_num;
             }
             cacheName = cacheName1+" 商品类型:" + cacheName+" "+plan_title;
		}else{
    		    if("1".equals(groupFlowKg)&&!StringUtil.isEmpty(goods_name) && !goods_name.equals(plan_title)){
    	            plan_title  =  goods_name;
    	        }
    	        String object_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_name");
    	        if("170502112412000711".equals(key)){
    	            plan_title =object_name;
    	        }
    	        String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
    	        String cacheName1="";
    	        if(!StringUtils.isEmpty(phone_num)){
    	            cacheName1 ="开户号码：" + phone_num;
    	        }
    	        cacheName = cacheName1+" 商品类型：" + cacheName+" 商品："+plan_title;
		}
		//终端型号
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id,SpecConsts.TYPE_ID_10000))){
			cacheName = cacheName + "  " + CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10000,SpecConsts.MODEL_NAME) + "	发票";
		}
		//实物赠品名称
		String phyGiftNames = "";
		List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
		for(AttrGiftInfoBusiRequest gift:gifts){
			if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())){//实物(非虚拟)赠品
				phyGiftNames += gift.getGoods_name() + " ";
			}
		}
		if(!StringUtils.isEmpty(phyGiftNames)){
			cacheName = cacheName + "  " + phyGiftNames;
		}
		return cacheName;
	}
	/**
	 * 根据物流单号找到物流打印单中的保价金额
	 * */
	@Override
	public Map queryInsureValue(String order_id){
		String insureValue = "";
		String sql = SF.ecsordSql("INSURE_VALUE_BY_ORDERID");
		ArrayList para = new ArrayList();
		para.add(order_id);
		List insureValues = this.baseDaoSupport.queryForList(sql, order_id);
		if(null!=insureValues&&insureValues.size()>0){
			Map<String,String> map = (Map<String,String>)insureValues.get(0);
			return map;
		}
		return null;
	}
}
