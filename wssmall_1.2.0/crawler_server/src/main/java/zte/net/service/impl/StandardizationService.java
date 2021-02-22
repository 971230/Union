package zte.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.CrawlerUpdateHandleNumReq;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.autoprocess.base.exception.BusinessException;
import com.ztesoft.autoprocess.base.exception.SystemException;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.consts.CrawlerConsts;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;

import consts.ConstsCore;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.IStandardizationService;
import zte.params.goods.req.StdGoodsGetReq;
import zte.params.goods.resp.StdGoodsGetResp;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

public class StandardizationService implements IStandardizationService {

	/** 日志记录器 */
	private static final Log log = LogFactory.getLog(StandardizationService.class);
	
	/**缓存信息**/
	static INetCache cache;
	static {
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	private ZBSystemClient init(){
		ZBSystemClient client=null;
		try {
			String username = "";
			if(ZBSystemClient.clientLogin!=null){
				if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getUsername())){
					username = ZBSystemClient.clientLogin.getLoginParam().getUsername();
					//log.info("=================分配获取登录的用户名："+username);
				}else{
					username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
				}
			}else{
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
			}
			 client = ClientPool.get(username);//获取总商登录客户端对象
		} catch (Exception e) {
			e.printStackTrace();
			log.info("【总部系统-登陆系统异常】");
		}
		return client;
	}
	
	@Override
	public boolean orderStandardization(String threadName, String inJson) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String parseDetailFileOrderCtn(String threadName,
			Map<String, Object> orderMap, Map<String, Object> commonMap) {

		ZBSystemClient client =init();
		
		String stdResult = "";
		String orderId = orderMap.get("OrderNum").toString();
		String orderNo = orderMap.get("OrderId").toString();
		ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		//客服订单查询页面详情按钮链接
    	//String detailUrl = ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL;
		String detailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL);
    	
    	// 创建参数队列
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", orderId));//订单ID
		detailparams.add(new BasicNameValuePair("orderType", "1"));
		
		String detailOrder = null;
		try {
			detailOrder = client.post(detailparams, detailUrl);
		} catch (Exception e) {
			stdResult = "进入客服订单详情异常："+e.getMessage();
			e.printStackTrace();
		}
		//爬虫客服订单查询页面详情数据
		if(StringUtils.isNotBlank(detailOrder)){//爬虫获取的订单详情数据不为空
		    //getFile("allocation",oldFileName,orderId);
			Document detaildoc = Jsoup.parse(detailOrder);//解析详情数据
			//&nbsp;空格特殊处理
			String spaceStr = Jsoup.parse("&nbsp;").text();
			if(detaildoc!=null){
				Map<String, Object> goodsInfoMap = new HashMap<String, Object>(); //商品信息节点
			 	Map<String, Object> goodsAttInfoMap = new HashMap<String, Object>();  //商品附属信息节点
			 	Map<String, Object> activityInfoMap = new HashMap<String, Object>(); //活动信息节点
			 	Map<String, Object> resourcesInfoMap = new HashMap<String, Object>(); //终端信息节点
			 	//==================身份证信息====================
			 	getCertInfo(detaildoc,orderMap,goodsAttInfoMap);
			 	//=============订单收货配送信息===============
				getDeliveryInfo(detaildoc,orderMap,zteclient,spaceStr);
				//=================发票信息=================
				getInvoiceInfo(detaildoc,orderMap);
				//===============优惠信息====================
				//getDiscountInfo(detaildoc,orderMap);
				//================赠品信息==================
				//getGiftInfo(detaildoc, orderMap);
				//================加盟商盟信息============
				//getLeagueInfo(detaildoc, orderMap);
				//==========下单时间商品数量信息===============
				String ifSendPhotos = "0";//照片同步
				String goodsNum = "1";//订单中商品的总数量(需计算用户购买商品的数量)
				String orderTime = "";//下单时间
				Elements orderTimeElements = detaildoc.select("li[class=operationTime]");
				if(orderTimeElements!=null && orderTimeElements.size()>0){
					Element orderTimeElement = orderTimeElements.get(1);
					if(orderTimeElement!=null){	
						orderTime = orderTimeElement.text().trim();//下单时间，YYYY-MM-DD HH24:MI:SS
					}
				}
				//==============订单费用信息=====================
				getOrderFeeInfo(detaildoc, orderMap);
				//================支付信息===================
				getPayInfo(detaildoc,orderMap,orderTime,spaceStr);
				//===========useCustInfo(责任人使用人信息 爬虫不到相关信息)=============
				//getUseCustInfo(detaildoc, orderMap);
				//============订单数据===========================
				orderMap.put("GoodsNum", goodsNum);
				orderMap.put("IfSendPhotos",ifSendPhotos);
				orderMap.put("OrderTime", orderTime);
				//================获取es_std_goods_config商品配置信息===============
				getGoodsInfo(detaildoc, goodsInfoMap,goodsAttInfoMap,activityInfoMap,resourcesInfoMap, zteclient);
				//===================商品费用明细(爬虫取不到)==================
				//getGoodsFeeInfo(detaildoc, goodsInfoMap);
				//===================商品属性信息=========
				getGoodsAttInfo(detaildoc, goodsAttInfoMap, orderMap, commonMap);
				
				//ZHKL:号卡 ZHYL:号卡合约 ZZDL:终端合约 ZSWK:上网卡 ZLZD:裸终端ZPJL:配件 ZYWL:业务变更
				String goodsType = (String)goodsAttInfoMap.get("GoodsType");
				 
				//上网卡特殊处理
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equals(goodsType)){
					Elements packageNameElements = detaildoc.getElementsContainingOwnText("包名称：").parents().select("dd");
					if(packageNameElements!=null && packageNameElements.size()>0){
						String packageName = packageNameElements.get(0).text().trim();
						goodsAttInfoMap.put("ProPacCode", "");
						goodsAttInfoMap.put("ProPacDesc", packageName);
					}
				}
				Elements orderCityElements = detaildoc.getElementsContainingOwnText("归属地：").parents().select("dd");
				if(orderCityElements!=null && orderCityElements.size()>0){
					String orderCityStr = orderCityElements.get(0).text().trim();
					String orderCity = "";
					if(StringUtils.isNotBlank(orderCityStr)){
						orderCity = LocalCrawlerUtil.AREAIDBACK.get(orderCityStr);//订单地市
					}
					if(StringUtils.isBlank((String)orderMap.get("OrderCity"))){//地市为空
						orderMap.remove("OrderCity");
						orderMap.put("OrderCity", orderCity);
					}
				}
				//===============合约计划信息================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //商品类型为：号卡合约和终端合约的增加合约计划信息
					getActivityInfo(detaildoc, goodsInfoMap, goodsAttInfoMap,activityInfoMap);
				}
				//================终端信息====================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZLZD.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //商品类型为：终端合约和裸终端的增加终端信息
					String isCustomized = "TRUE";//是否为定制机(爬虫取不到数据)
					goodsAttInfoMap.put("IsCustomized", isCustomized);//(必填)
					getResourcesInfo(detaildoc, goodsInfoMap, goodsAttInfoMap, resourcesInfoMap);
				}
				//===========号码信息===========================
				if(StringUtils.isNotBlank(goodsType) && !EcsOrderConsts.ZB_GOODS_TYPE_ZLZD.equals(goodsType)){
					//不是裸机的都增加号码信息
					getPhoneInfo(detaildoc, goodsAttInfoMap, orderMap, commonMap);
				}
				//===========可选包信息============================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equals(goodsType) 
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //总部号卡、号卡合约、终端合约增加可选包信息（目前暂不添加）
					 //getMallPackageInfo(detaildoc, goodsAttInfoMap);
				}
				
				List<Map<String, Object>> goodsAttInfosMap = new ArrayList<Map<String, Object>>();
				goodsAttInfosMap.add(goodsAttInfoMap);
	
				goodsInfoMap.put("GoodsAttInfo", goodsAttInfosMap);//商品添加商品属性
				
				List<Map<String, Object>> goodsInfosMap = new ArrayList<Map<String, Object>>();
				goodsInfosMap.add(goodsInfoMap);
				 
				orderMap.put("GoodsInfo", goodsInfosMap);//订单添加商品信息
				 
				if(orderMap!=null && orderMap.size()>0){//买家昵称为空则取证件名称
					if(StringUtils.isBlank((String)orderMap.get("RegisterName"))){
						orderMap.remove("RegisterName");
						orderMap.put("RegisterName",orderMap.get("ConsiName"));
					}
				}
	
				String reqJosn = "";
				if(orderMap!=null && orderMap.size()>0){
					JSONObject json = JSONObject.fromObject(orderMap);//处理报文
					reqJosn = json.toString();
				}
				 
				log.info("=====封装报文所需的json字符串："+reqJosn);
				if(StringUtils.isNotBlank(reqJosn)){//组装报文不为空则调用.总商标准化收单接口
					boolean success = getOrderCtn(reqJosn,threadName);
					if(!success){
						stdResult = "调用收单接口失败";
					}else{//锁单
						//log.info("=====["+orderId+"]审核锁定成功");
						String reviewBtnUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL)+orderId+"?orderId="+orderId;
						
						String reviewBtnResult;
						try {
							reviewBtnResult = client.post(new ArrayList<NameValuePair>(),reviewBtnUrl);
					
							Map<String,String> paramMap = new HashMap<String, String>();
							paramMap.put("deliverTypeCode", "");
							paramMap.put("templateId", "");
							paramMap.put("isGroupCust", "");
							if(StringUtil.isEmpty(reviewBtnResult) || reviewBtnResult.indexOf("value=\"审核通过\"") == -1){
								log.info("=====["+orderId+"]审核锁定失败");
							}else{
								log.info("=====["+orderId+"]审核锁定成功");
							}
						} catch (SystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else{
					stdResult = "组装报文为空";
				}
			}
			else{
				stdResult = "使用Jsoup转换客服订单详情页失败";
			}
		}else{
			stdResult = "爬虫获取的获取订单详情数据为空！";
			log.info("======爬虫客服订单页面获取的获取订单详情数据为空！");
		}
		return stdResult;
	}

	/**
	 * 调用总商标准化接口地址
	 * @param inJson
	 */
	private synchronized static boolean getOrderCtn(String inJson,String threadName){
		boolean success = false;
		try {
			String outJson = "";//接口返回报文
			String orderId;//外部订单号
			String activeNo = "";// 序列号
			activeNo = CrawlerUtils.searchValue(inJson, "ActiveNo");
			orderId = CrawlerUtils.searchValue(inJson, "OrderId");
			
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

			Map<String, String> params = CrawlerSetting.HANDLE_NUM_MAP.get(threadName);
			String str = params.get("HANDLE_NUM");
			String status = params.get("UPDATE_STATUS");
			int handleNum = StringUtil.isEmpty(str)?-1:Integer.valueOf(str);
			if(handleNum == 0){//设置了处理条数，如果处理条数等于0则不做收单
				if("0".equals(status)){
	        		String server_ip = StringUtil.getLocalIpAddress();
	        		String server_port = StringUtil.getContextPort();
	        		CrawlerUpdateHandleNumReq req = new CrawlerUpdateHandleNumReq();
	        		req.setIp(server_ip);
	        		req.setPort(server_port);
	        		req.setHandleNum(0);
	        		ZteResponse resp = client.execute(req, ZteResponse.class);
	        		if("0".equals(resp.getError_code())){
	        			params.put("UPDATE_STATUS", "1");
	        		}
				}
				return success;
			}
			OrderCtnReq req = new OrderCtnReq();
			req.setOutServiceCode(CrawlerConsts.OUT_SERVICE_CODE_CENTERMALLORDERSTANDARD);
			req.setVersion(CrawlerConsts.VERSION);
			req.setReqMsgStr(inJson);
			req.setFormat(CrawlerConsts.ORDER_QUEUE_MSG_TYPE_JSON);
			req.setOutOrderId(orderId);
			long begin = System.currentTimeMillis();
			
			OrderCtnResp resp = client.execute(req, OrderCtnResp.class);
			long end = System.currentTimeMillis();
			log.info("=====[OrderCrawlerTimer] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
			if (null != resp) {
				String rpc_type = resp.getRpc_type();
				if (CrawlerConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
					String error_code = resp.getError_code();
					if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
						if(handleNum > 0){
							handleNum--;
							params.put("HANDLE_NUM", String.valueOf(handleNum));
							CrawlerSetting.HANDLE_NUM_MAP.put(threadName, params);
							
						}
						success = true;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
					} else {
						success = false;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
					}
				} else if (CrawlerConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
					String error_code = resp.getError_code();
					if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
						if(handleNum > 0){
							handleNum--;
							params.put("HANDLE_NUM", String.valueOf(handleNum));
							CrawlerSetting.HANDLE_NUM_MAP.put(threadName, params);
						}
						success = true;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
					} else {
						success = false;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
					}
				} else {
					success = false;
					outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
				}
			} else {
				success = false;
				outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败\"}}";
			}
			
			if(success)
				cache.set(OrderToExamineService.NAMESPACE, OrderToExamineService.HB_ZB_ORDER_ID+orderId,orderId);//把标准化成功的订单号设置到缓存系统中
			
			log.info("======[线程："+threadName+"]调用总商标准化订单接口返回结果：============"+outJson);
			log.info("======[线程："+threadName+"]调用总商标准化订单接口返回结果：============"+outJson);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return success;
	}
	
	/**
	 * 爬虫获取商品属性信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param goodsInfoMap
	 * @param orderMap
	 */
	private static void getGoodsAttInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap,Map<String, Object> orderMap,Map<String, Object> commonMap){
		 String saleMode = "";//销售方式
		 String appType = "";//应用类别，当加入具体集团时为必填 0：行业应用 	1：非行业应用
		 String checkType = "01";//认证类型 cbss系统订单必传 01：本地认证	02：公安认证	03：二代证读卡器
		 String customerType = EcsOrderConsts.CUSTOMER_CUST_TYPE_GRKH;//客户类型  个人客户 集团 客户
		 String groupId = "";//集团ID
		 String certLoseTime = "2050-01-01 00:00:00";//证件失效时间
		 String cardType = "NM";//卡类型  默认为普卡
		 Elements cardTypeElements = detaildoc.getElementsContainingOwnText("卡类型：").parents().select("dd");
		 if(cardTypeElements!=null && cardTypeElements.size()>0){
			 Element cardTypeElement = cardTypeElements.get(0);
			 if(cardTypeElement!=null){
				 cardType = LocalCrawlerUtil.CARDTYPEBACK.get(cardTypeElement.text().trim());//卡类型
				 if(StringUtils.isBlank(cardType) || cardType == null || "null".equals(cardType)){
					 cardType = "NM";
				 }
			 }
		 }
		 String productName = "";
		 //Elements productNameElements = detaildoc.select("li[class=commodityComposition]").select("dd");
		 Elements productNameElements = detaildoc.getElementsContainingOwnText("套餐：").parents().select("dd");
		 if(productNameElements!=null && productNameElements.size()>0){
			 productName = productNameElements.get(0).text().trim();//产品名称
		 }
		 String productType = "MAIN";//产品类型 MAIN 4G基本套餐 DIY DIY套餐
		 String sex = "";//性别，固定长度1位， M：男， F：女
		 String simId = ""; //SIM卡号
		 String subAppType = "";//应用子类型
		 
		 goodsAttInfoMap.put("AppType", appType);
		 goodsAttInfoMap.put("CardType", cardType);
		 goodsAttInfoMap.put("CertLoseTime", certLoseTime);
		 goodsAttInfoMap.put("CertNum", orderMap.get("ConsiCertNum"));
		 goodsAttInfoMap.put("CertType", orderMap.get("ConsiCertType"));
		 goodsAttInfoMap.put("CheckType", checkType);
		 goodsAttInfoMap.put("CustomerName", orderMap.get("ConsiName"));
		 goodsAttInfoMap.put("CustomerType", customerType);
		 goodsAttInfoMap.put("GroupId", groupId);
		 goodsAttInfoMap.put("ProductName", productName);
		 goodsAttInfoMap.put("ProductNet", commonMap.get("numNet"));
		 goodsAttInfoMap.put("ProductType", productType);
		 goodsAttInfoMap.put("SaleMode", saleMode);
		 goodsAttInfoMap.put("Sex", sex);
		 goodsAttInfoMap.put("SimId", simId);
		 goodsAttInfoMap.put("SubAppType", subAppType);
		 goodsAttInfoMap.put("UserType", commonMap.get("userType"));
		 //=============获取发展人和推荐人信息
		 getDevelopRefereeInfo(detaildoc, goodsAttInfoMap);
	}
	
	/**
	 * 爬虫获取证件信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getCertInfo(Document detaildoc,Map<String, Object> orderMap,Map<String, Object> goodsAttInfoMap){
		String consiCertNum = "";//证件号码
		 Elements consiCertNumElements = detaildoc.select("span[class=idCardShow]");
		 if(consiCertNumElements!=null && consiCertNumElements.size()>0){
			 consiCertNum = consiCertNumElements.text().trim();
		 }
		 String consiCertType = "";//证件类型
		 Elements consiCertElements = detaildoc.select("em[class=c_999]");
		 if(consiCertElements!=null){
			 String consiCertStr = consiCertElements.text().trim();
			 if(StringUtils.isNotBlank(consiCertStr)){
				 consiCertType = LocalCrawlerUtil.CUSTCARDTYPEBACK.get(consiCertStr.replace("(", "").replace(")", ""));
			 }
		 }
		 String consiName = "";//证件名称
		 Elements consiNameElements = detaildoc.select("label[class=receiverNameShow]");
		 if(consiNameElements!=null && consiNameElements.size()>0){
			 consiName = consiNameElements.text().trim();
		 }
		 String consiPhone = "";//联系电话
		 Elements consiPhoneElements = detaildoc.select("p:contains(电话：)");
		 if(consiPhoneElements!=null && consiPhoneElements.size()>0){
			 String phoneStr = consiPhoneElements.text().trim();
			 if(StringUtils.isNotBlank(phoneStr)){
				 int len = phoneStr.indexOf("：");
				 consiPhone = phoneStr.substring(len+1, len+12);//11位手机号
			 }
		 }
		 String consiEmail = "";//联系邮箱
		 Elements consiEmailElements = detaildoc.select(":containsOwn(联系邮箱：)");
		 if(consiEmailElements!=null && consiEmailElements.size()>0){
			 String consiEmailStr = consiEmailElements.text().trim();
			 if(StringUtils.isNotBlank(consiEmailStr)){
				 consiEmail = consiEmailStr.substring(consiEmailStr.indexOf("：")+1, consiEmailStr.length());
			 }
		 }
		 String consiCertAddress = "";//证件地址
		 Elements certAddressElements = detaildoc.select("span[class=addressName]");
		 if(certAddressElements!=null && certAddressElements.size()>0){
			 consiCertAddress = certAddressElements.text().trim();
		 }
		 orderMap.put("ConsiCertNum", consiCertNum);
		 orderMap.put("ConsiCertType", consiCertType);
		 orderMap.put("ConsiName", consiName);
		 orderMap.put("ConsiPhone", consiPhone);
		 //==========传递参数用=========
		 goodsAttInfoMap.put("CertAddr", consiCertAddress);
	}
	/**
	 * 爬虫获取配送信息
	 * @param detaildoc
	 * @param orderMap
	 * @param zteclient
	 * @param spaceStr
	 */
	private static void getDeliveryInfo(Document detaildoc,Map<String, Object> orderMap,ZteClient zteclient,String spaceStr){
		 String consiGoodsProv = "";//收货省
		 String consiGoodsCity = "";//收货地市
		 String consiGoodsDist = "";//收货地区
		 String consiPostAddress = "";//收货地址
		 Elements postAddressElements = detaildoc.select(":containsOwn(收货地址：)");//div[class=inforPosition]:containsOwn(收货地址：)
		 if(postAddressElements!=null && postAddressElements.size()>0){
			 String consiGoodsStr = postAddressElements.select("label").text().trim();
			 if(StringUtils.isNotBlank(consiGoodsStr)){
				 if(StringUtils.isNotBlank(consiGoodsStr)){
					 String[] consiGoodsArray = consiGoodsStr.split("，");
					 consiGoodsProv = LocalCrawlerUtil.AREAIDBACK.get(consiGoodsArray[0].trim());
					 consiGoodsCity = LocalCrawlerUtil.AREAIDBACK.get(consiGoodsArray[1].trim());
					 consiGoodsDist = consiGoodsArray[2].trim();
					 if(StringUtils.isNotBlank(consiGoodsDist)){
						 RegionsGetReq req = new RegionsGetReq();
						 req.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
						 req.setLocal_name(consiGoodsDist);
						 RegionsGetResp resp = zteclient.execute(req, RegionsGetResp.class);
						 if(resp!=null && resp.getRegions()!=null){
							 consiGoodsDist = resp.getRegions().getRegion_id();
						 }
					 }
					 //收货地址获取整个地址不做截取（跟老板确认的）
					 consiPostAddress = consiGoodsArray[3];
				 }
			 }
		 }

		 String deliMode = "KD";//配送方式 默认快递
		 String deliTimeMode = "NOLIMIT";//配送时间类型  默认不限时间送货
		 Elements deliModeElements = detaildoc.select(":containsOwn(配送方式：)").select("label");
		 if(deliModeElements!=null && deliModeElements.size()>0){
			 Element deliModeElement = deliModeElements.get(0);
			 if(deliModeElement!=null){
				 String deliModeStr = deliModeElement.text().trim();
				 if(StringUtils.isNotBlank(deliModeStr)){
					 String deliModeStr1 = deliModeStr.split(" ")[0].trim();
					 if(StringUtils.isNotBlank(deliModeStr1) && deliModeStr1.indexOf("自提")!=-1){
						 deliMode = "ZT";
					 }else{
						 deliMode = LocalCrawlerUtil.DELIVERYTYPE.get(deliModeStr1);//配送方式
					 }
				 }
			 }
			 if(deliModeElements.size()>1){//存在第二个lable标签
				 Element deliTimeModeElement = deliModeElements.get(1);
				 if(deliTimeModeElement!=null){
					 String deliTimeModeStr = deliTimeModeElement.text();
					 if(StringUtils.isNotBlank(deliTimeModeStr)){
						 deliTimeMode = LocalCrawlerUtil.DELIVERYTIMETYPE.get(deliTimeModeStr.trim());//配送时间类型
					 }
				 }
			 }
		 }
		 if(deliMode==null || "null".equals(deliMode) || StringUtils.isBlank(deliMode)){
			 deliMode = "KD";
		 }
		 if(deliTimeMode==null || "null".equals(deliTimeMode) || StringUtils.isBlank(deliTimeMode)){
			 deliTimeMode = "NOLIMIT";
		 }
		 orderMap.put("ConsiGoodsCity", consiGoodsCity);
		 orderMap.put("ConsiGoodsDist", consiGoodsDist);
		 orderMap.put("ConsiGoodsProv", consiGoodsProv);
		 orderMap.put("ConsiPostAddress", consiPostAddress);
		 orderMap.put("deliMode", deliMode);
		 orderMap.put("deliTimeMode", deliTimeMode);
	}
	/**
	 * 爬虫获取发票信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getInvoiceInfo(Document detaildoc,Map<String, Object> orderMap){
		String invoiceDesc = "";//发票内容
		 String invoiceTitle = "";//发票抬头
		 String invoiceType = ""; //发票类型 NOW 即时发票  MONTH 月结发票
		 Elements invoiceDescElements = detaildoc.select(":containsOwn(发票内容：)");
		 if(invoiceDescElements!=null && invoiceDescElements.size()>0){
			 String invoiceDescStr = invoiceDescElements.text();
			 if(StringUtils.isNotBlank(invoiceDescStr) && invoiceDescStr.indexOf("：")!=-1){
				 invoiceDesc = invoiceDescStr.substring(invoiceDescStr.indexOf("：")+1, invoiceDescStr.length()).trim();
				 if(StringUtils.isNotBlank(invoiceDesc) && "明细".equals(invoiceDesc)){
					 invoiceDesc = "MX";
					 invoiceType = "NOW";//默认为即时发票
					 
				 }
			 }//没有发票内容就不设置发票抬头
			 Elements invoiceTitleElements = detaildoc.select(":containsOwn(发票抬头：)");
			 if(invoiceTitleElements!=null && invoiceTitleElements.size()>0){
				 String invoiceTitleStr = invoiceTitleElements.text().trim();
				 if(StringUtils.isNotBlank(invoiceTitleStr)){
					 invoiceTitle = invoiceTitleStr.substring(invoiceTitleStr.indexOf("：")+1, invoiceTitleStr.length()).trim();
				 }else{
					 invoiceTitle = orderMap.get("ConsiName").toString();//发票抬头默认为收货人
				 }
			 }else{
				 invoiceTitle = orderMap.get("ConsiName").toString();//发票抬头默认为收货人
			 }
		 }
		 orderMap.put("InvoiceDesc", invoiceDesc);
		 orderMap.put("InvoiceTitle", invoiceTitle);
		 orderMap.put("InvoiceType", invoiceType);
	}
	/**
	 * 爬虫获取优惠信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getDiscountInfo(Document detaildoc,Map<String, Object> orderMap){
		 String discountName = "";
		 Elements discountNameElements = detaildoc.select("li[class=commodityName]").select(":containsOwn(活动：)").parents().select("dd");
		 if(discountNameElements!=null && discountNameElements.size()>0){
			 discountName = discountNameElements.get(0).text().trim();
			 List<Map<String,String>> discountInfos = new ArrayList<Map<String,String>>();
			 Map<String,String> discountInfoMap = new HashMap<String, String>();
			 discountInfoMap.put("DiscountID", "");
			 discountInfoMap.put("DiscountName", discountName);
			 discountInfoMap.put("DiscountType", "");
			 discountInfoMap.put("DiscountValue", "");
			 discountInfos.add(discountInfoMap);
			 orderMap.put("DiscountInfo", discountInfos);
		 }
	}
	/**
	 * 爬虫获取赠品信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getGiftInfo(Document detaildoc,Map<String, Object> orderMap){
		 String giftName = "";
		 Elements giftNameElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(赠品：)").parents().select("dd");
		 if(giftNameElements!=null && giftNameElements.size()>0){
			 giftName = giftNameElements.get(0).text().trim();
			 List<Map<String,String>> giftInfos = new ArrayList<Map<String,String>>();
			 Map<String,String> giftInfoMap = new HashMap<String, String>();
			 giftInfoMap.put("GiftDesc", "");
			 giftInfoMap.put("GiftID", "");
			 giftInfoMap.put("GiftName", giftName);
			 giftInfoMap.put("GiftNum", "1");
			 giftInfoMap.put("GiftValue", "0");
			 giftInfos.add(giftInfoMap);
			 orderMap.put("GiftInfo", giftInfos);
		 }
	}
	/**
	 * 爬虫获取联盟信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getLeagueInfo(Document detaildoc,Map<String, Object> orderMap){
		 Map<String,String> leagueInfoMap = new HashMap<String, String>();
		 leagueInfoMap.put("HigherLeagueId", "");
		 leagueInfoMap.put("HigherLeagueName", "");
		 leagueInfoMap.put("LeagueId", "");
		 leagueInfoMap.put("LeagueName", "");
		 orderMap.put("LeagueInfo", leagueInfoMap);
	}
	/**
	 * 爬虫获取订单费用信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getOrderFeeInfo(Document detaildoc,Map<String, Object> orderMap){
		 String orderOrigFee = "0";//订单应收总价，单位为厘
		 double moneyOrigFee = 0.0;
		 String orderReliefFee = "0";//订单减免金额，单位为厘
		 double moneyReliefFee = 0.0;
		 String origPostFee = "0";//应收邮寄费用，单位为厘
		 double moneyPostFee = 0.0;
		 Elements orderReliefFeeElements1 = detaildoc.select("li[class=totalAmount]").select("span");
		 Elements orderOrigFeeElements = detaildoc.select(":containsOwn(商品金额总计：)").select("span");
		 if(orderOrigFeeElements!=null && orderOrigFeeElements.size()>0){
			 String orderOrigFeeStr = orderOrigFeeElements.text().trim();
			 if(StringUtils.isNotBlank(orderOrigFeeStr)){
				 moneyOrigFee = Double.valueOf(orderOrigFeeStr.replace("￥", "").trim());
				 orderOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrigFee);//元转厘 订单应收总价，单位为厘
			 }
		 }else{
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String orderOrigFeeStr = orderReliefFeeElements1.get(0).text().trim();
				 if(StringUtils.isNotBlank(orderOrigFeeStr)){
					 moneyOrigFee = Double.valueOf(orderOrigFeeStr.replace("￥", "").trim());
					 orderOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrigFee);//元转厘 订单应收总价，单位为厘
				 }
			 }
		 }
		 Elements orderReliefFeeElements = detaildoc.select(":containsOwn(减免费用：)").select("span");
		 if(orderReliefFeeElements!=null && orderReliefFeeElements.size()>0){
			 String reliefFeeStr = orderReliefFeeElements.text().trim();
			 if(StringUtils.isNotBlank(reliefFeeStr)){
				 moneyReliefFee = Double.valueOf(reliefFeeStr.replace("￥", "").trim());
				 orderReliefFee = CrawlerUtils.parseMoneyToLi(moneyReliefFee);//订单减免金额，单位为厘 
			 }
		 }else{//搜索不到减免费用文字则通过另一种做匹配
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String reliefFeeStr = orderReliefFeeElements1.get(1).text().trim();
				 if(StringUtils.isNotBlank(reliefFeeStr)){
					 moneyReliefFee = Double.valueOf(reliefFeeStr.replace("￥", "").trim());
					 orderReliefFee = CrawlerUtils.parseMoneyToLi(moneyReliefFee);//订单减免金额，单位为厘 
				 }
			 }
		 }
		 double moneyRealFee = moneyOrigFee - moneyReliefFee;//商品金额总计 - 减免费用 = 订单实收总价（不包括运费）
		 String orderRealFee = CrawlerUtils.parseMoneyToLi(moneyRealFee);//订单实收总价，单位为厘
		 Elements origPostFeeElements = detaildoc.select(":containsOwn(运费：)").select("span");
		 if(origPostFeeElements!=null && origPostFeeElements.size()>0){
			 String origPostFeeStr = origPostFeeElements.text().trim();
			 moneyPostFee = Double.valueOf(origPostFeeStr.replace("￥", "").trim());
			 origPostFee = CrawlerUtils.parseMoneyToLi(moneyPostFee);//应收邮寄费用，单位为厘
		 }else{
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String origPostFeeStr = orderReliefFeeElements1.get(2).text().trim();
				 moneyPostFee = Double.valueOf(origPostFeeStr.replace("￥", "").trim());
				 origPostFee = CrawlerUtils.parseMoneyToLi(moneyPostFee);//应收邮寄费用，单位为厘
			 }
		 }
		 String realPostFee = origPostFee;//实收邮寄费用，单位为厘
		 orderMap.put("OrderOrigFee", Double.valueOf(orderOrigFee));
		 orderMap.put("OrderRealFee", Double.valueOf(orderRealFee));
		 orderMap.put("OrderReliefFee", Double.valueOf(orderReliefFee));
		 orderMap.put("OrigPostFee", Double.valueOf(origPostFee));
		 orderMap.put("RealPostFee", Double.valueOf(realPostFee));
	}
	/**
	 * 爬虫获取支付信息
	 * @param detaildoc
	 * @param orderMap
	 * @param orderTime
	 * @param spaceStr
	 */
	private static void getPayInfo(Document detaildoc,Map<String, Object> orderMap,String orderTime,String spaceStr){
		 String payFinTime = orderTime;//支付完成时间，YYYY-MM-DD HH24:MI:SS 货到付款的没有支付完成时间
		 String payTypeStr = "";
		 String payType = "HDFK";//默认货到付款
		 String payWay = "XJZF";//默认现金支付
		 String payChannelId = "";//支付渠道编码
		 String payChannelName = "";//支付渠道名称
		 String payProviderId = "";//支付机构编码
		 String payProviderName = "";//支付机构名称
		 Elements payTypeElements = detaildoc.select(":containsOwn(支付方式：)").select("label");
		 if(payTypeElements!=null && payTypeElements.size()>0){
			 Element payTypeElement = payTypeElements.get(0);
			 if(payTypeElement!=null){
				 payTypeStr = payTypeElement.text().trim();
				 if(StringUtils.isNotBlank(payTypeStr)){
					 String[] payTypeArray = payTypeStr.split("\\(");
					 payType = LocalCrawlerUtil.PAYTYPEBACK.get(payTypeArray[0].trim());//支付类型
					 payWay = LocalCrawlerUtil.PAYWAY.get(payTypeArray[1].replace(")", "").trim());//支付方式
				 }
			 }
			 if(payTypeElements.size()>1){//存在支付信息标签
				 Element payProviderElement = payTypeElements.get(1);
				 if(payProviderElement!=null){
					 String payStr = payProviderElement.text();
					 if(StringUtils.isNotBlank(payStr)){
						 String payProviderStr = payStr.substring(payStr.indexOf("：")+1, payStr.length()).replace(spaceStr, "").trim();
						 if(StringUtils.isNotBlank(payProviderStr) && payProviderStr.indexOf(" ")!=-1){
							 payChannelName = payProviderStr.substring(0, payProviderStr.indexOf(" ")).replace(spaceStr, "").trim();
							 if(StringUtils.isNotBlank(payChannelName)){
								 payChannelId = LocalCrawlerUtil.PAY_CHANNEL.get(payChannelName);//通过支付渠道名称获取支付渠道编码
								 if(payChannelId==null || "null".equals(payChannelId) || StringUtils.isBlank(payChannelId)){
									 payChannelId = "";
									 payChannelName = "";
								 }
							 }
							 //取最后空格之间的内容
							 payProviderName = payProviderStr.substring(payProviderStr.lastIndexOf(" "),payProviderStr.length()).replace(spaceStr, "").trim();
							 if(StringUtils.isNotBlank(payProviderName)){
								 payProviderId = LocalCrawlerUtil.PAY_PROVIDER.get(payProviderName.trim());//通过支付机构名称获取支付机构编码
								 if(payProviderId==null || "null".equals(payProviderId) || StringUtils.isBlank(payProviderId)){
									 payProviderId = "";
									 payProviderName = "";
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 if(payType==null || "null".equals(payType) || StringUtils.isBlank(payType)){
			 payType = "HDFK";
		 }
		 if(payWay==null || "null".equals(payWay ) || StringUtils.isBlank(payWay)){
			 payWay = "XJZF";
		 }
		 Map<String, String> payInfoMap = new HashMap<String, String>();
		 payInfoMap.put("PayFinTime", payFinTime);
		 payInfoMap.put("PayPlatFormOrderId", "");
		 payInfoMap.put("PayChannelId", payChannelId);
		 payInfoMap.put("PayChannelName", payChannelName);
		 payInfoMap.put("PayProviderId", payProviderId);
		 payInfoMap.put("PayProviderName", payProviderName);
		 payInfoMap.put("PayType", payType);
		 payInfoMap.put("PayWay", payWay);
		 orderMap.put("PayInfo", payInfoMap);
	}
	/**
	 * 爬虫获取责任人信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getUseCustInfo(Document detaildoc,Map<String, Object> orderMap){
		Map<String, String> useCustInfoMap = new HashMap<String, String>();
		orderMap.put("useCustInfo", useCustInfoMap); 
	}
	/**
	 * 爬虫获取商品费用明细
	 * @param detaildoc
	 * @param goodsInfoMap
	 */
	private static void getGoodsFeeInfo(Document detaildoc,Map<String, Object> goodsInfoMap){
		 String feeDes = "";//收费项描述
		 String feeID = "";//收费项编码
		 String origFee = "";//应收金额，单位为厘
		 String realFee = "";//实收金额，单位为厘
		 String reliefFee = "";//减免金额，单位为厘
		 List<Map<String, String>> feeInfo = new ArrayList<Map<String, String>>();
		 Map<String, String> feeMap = new HashMap<String, String>();
		 feeMap.put("FeeDes", feeDes);
		 feeMap.put("FeeID", feeID);
		 feeMap.put("OrigFee", origFee);
		 feeMap.put("RealFee", realFee);
		 feeMap.put("ReliefFee", reliefFee);
		 feeInfo.add(feeMap);
		 goodsInfoMap.put("FeeInfo", feeInfo);
	}
	/**
	 * 爬虫获取商品信息
	 * @param detaildoc
	 * @param goodsInfoMap
	 * @param zteclient
	 */
	private static void getGoodsInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap,Map<String, Object> activityInfoMap,Map<String, Object> resourcesInfoMap, ZteClient zteclient){
		 String goodsCode = "";//页面没有该参数值，只能通过商品名称去获取商品编码
		 String goodsName = "";//商品名称
		 String productName = "";//产品名称
		 String productCode = "";//产品编码
		 //ZHKL:号卡 ZHYL:号卡合约 ZZDL:终端合约 ZSWK:上网卡 ZLZD:裸终端ZPJL:配件 ZYWL:业务变更
		 String goodsType = EcsOrderConsts.ZB_GOODS_TYPE_ZHKL;//商品类型 (根据什么条件来判断商品类型)
		 String laterActFlag = "0";//是否后激活订单（0：普通订单 1：后激活订单）
		 String numType = "1";//主副标识（1：主号；2：副号）
		 String productBrand = "4GPH";//产品品牌 3GPH 3G手机卡 3GWK 3G上网卡 3GWB 3G上网本 2GPH 2G手机卡 4GPH 4G手机卡
		 String reliefPresFlag = EcsOrderConsts.RELIEFPRES_FLAG_NO;//减免预存标记
		 String serType = "BAK";//付费类型 后付费：BAK，预付费：PRE
		 String activityCode = "";//合约编码
		 String actProtPer = "";//合约期
		 String activityType = "";//合约类型
		 String resourcesTypeId = "";//终端机型编码
		 String resourcesBrand = "";//终端品牌编码
		 String resourcesModel = "";//终端型号编码
		 String resourcesColor = "";//终端颜色编码
		 
		 Elements goodsNameElements = detaildoc.select("ul[class=listInfor]").select("li[class=commodityName]").select("dd");
		 Elements productNameElements = detaildoc.getElementsContainingOwnText("套餐：").parents().select("dd");
		 if(productNameElements!=null && productNameElements.size()>0){
			 productName = productNameElements.get(0).text().trim();//产品名称
		 }
		 if(goodsNameElements!=null && goodsNameElements.size()>0){
			 goodsName = goodsNameElements.get(0).text().trim();
			 log.info("=========================爬虫获取的商品名称为："+goodsName);
			 log.info("=========================爬虫获取的产品名称为："+productName);
			 if(StringUtils.isNotBlank(goodsName)){
				 StdGoodsGetReq req = new StdGoodsGetReq();
				 //【滴滴王卡】滴滴小王卡-58元套餐
				 req.setGoodsName(goodsName);
				 req.setTitleName(productName);
				 //req.setGoodsName("三星G3502粉24月购机送费4G全国套餐76元");
				 StdGoodsGetResp resp = zteclient.execute(req, StdGoodsGetResp.class);
				 if(resp!=null){
					 goodsCode = resp.getpCode();
					 productCode = resp.getSn();
					 goodsType = resp.getGoodsType();
					 laterActFlag = resp.getLaterActFlag();
					 numType = resp.getNumType();
					 productBrand = resp.getProductBrand();
					 reliefPresFlag = resp.getReliefPresFlag();
					 serType = resp.getSerType();
					 actProtPer = resp.getContractMonth();
					 activityType = resp.getActivityType();
					 resourcesBrand = resp.getBrandCode();
					 resourcesModel = resp.getModelCode();
					 resourcesColor = resp.getColorCode();
					 
					 if("ZHKL".equalsIgnoreCase(goodsType)){
						 //号卡
						 activityCode = resp.getpCode(); 
					 }else if ( "ZHYL".equalsIgnoreCase(goodsType)) {
						 //号卡合约
						 activityCode =  resp.getpCode(); 
						 //actProtPer = resp.getSn();
					 }else if ("ZZDL".equalsIgnoreCase(goodsType)) {
						 //总部终端合约类
						 activityCode =  resp.getpCode(); 
						 //actProtPer = resp.getSn();
						 resourcesTypeId = resp.getSn();
					 }else if ("ZSWK".equalsIgnoreCase(goodsType)) {
						 //总部上网卡商品
					 }else if ("ZPJL".equalsIgnoreCase(goodsType)) {
						//总部配件商品
					 }else if ("ZLZD".equalsIgnoreCase(goodsType)) {
						//总部裸终端商品
						 resourcesTypeId = resp.getSn();
					 }else {
						 activityCode =  resp.getpCode(); 
						 resourcesTypeId = resp.getSn();
					 }
				 }
			 }else{
				 log.info("=====爬虫抓取订单详情解析商品名称为空，解析报文："+detaildoc.toString());
			 }
		 }
		 String goodsOrigFee = "";//商品应收，单位为厘
		 String goodsRealFee = "";//商品实收，单位为厘
		 Elements goodsOrigElements = detaildoc.select("ul[class=listInfor]").select("li[class=settlement]");
		 if(goodsOrigElements!=null && goodsOrigElements.size()>0){
			 String goodsOrigStr = goodsOrigElements.text().trim();
			 if(StringUtils.isNotBlank(goodsOrigStr)){
				 double moneyOrig = Double.valueOf(goodsOrigStr.replace("￥", "").trim());
				 goodsOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrig);//元转厘
				 goodsRealFee = goodsOrigFee;//*1000 单位为厘
			 }
		 }
		 goodsInfoMap.put("GoodsCode", goodsCode);
		 goodsInfoMap.put("GoodsName", goodsName);
		 goodsInfoMap.put("GoodsOrigFee", goodsOrigFee);
		 goodsInfoMap.put("GoodsRealFee", goodsRealFee);
		 goodsInfoMap.put("GoodsReliefRes", "");//商品减免原因
		 //商品附属信息节点
		 goodsAttInfoMap.put("ProductCode", productCode);
		 goodsAttInfoMap.put("GoodsType", goodsType);
		 goodsAttInfoMap.put("LaterActFlag", laterActFlag);
		 goodsAttInfoMap.put("NumType", numType);
		 goodsAttInfoMap.put("ProductBrand", productBrand);
		 goodsAttInfoMap.put("ReliefPresFlag", reliefPresFlag);
		 goodsAttInfoMap.put("SerType", serType);
		 //合约计划配置信息
		 activityInfoMap.put("ActivityCode", activityCode);
		 activityInfoMap.put("ActProtPer", actProtPer);
		 activityInfoMap.put("ActivityType", activityType);
		 //终端配置信息
		 resourcesInfoMap.put("ResourcesTypeId", resourcesTypeId);
		 resourcesInfoMap.put("ResourcesBrand", resourcesBrand);
		 resourcesInfoMap.put("ResourcesModel", resourcesModel);
		 resourcesInfoMap.put("ResourcesColor", resourcesColor);
	}
	/**
	 * 爬虫获取发展人推荐人信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getDevelopRefereeInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap){
		 String developCode = "";
		 String developDepId = "";//发展人ID
		 String developName = "";
		 String realnameType = "1";//实名认证类型：1：未实名认证 2：已实名认证
		 String refereeName = "";//推荐人名称
		 String refereeNum = "";//推荐人号码（联系方式）
		 //&nbsp;空格特殊处理
		 String spaceStr = Jsoup.parse("&nbsp;").text();
		 Elements developElements = detaildoc.getElementsContainingOwnText("发展人：").parents().select("dd");
		 if(developElements!=null && developElements.size()>0){
			 String developCodeStr = developElements.get(0).text().replace(spaceStr, "").trim();
			 if(StringUtils.isNotBlank(developCodeStr)){
				 developCode = developCodeStr.substring(developCodeStr.indexOf("（")+1, developCodeStr.length()-1).replace(spaceStr, "").trim();
				 developName = developCodeStr.substring(0, developCodeStr.indexOf("（")).replace(spaceStr, "").trim();//发展人名称
			 }
		 }
		 String firstMonBillMode = "";
		 Elements firstMonElements = detaildoc.getElementsContainingOwnText("首月：").parents().select("dd");
		 if(firstMonElements!=null && firstMonElements.size()>0){
			 Element firstMonElement = firstMonElements.get(0);
			 if(firstMonElement!=null){
				 firstMonBillMode = LocalCrawlerUtil.FIRST_FEE_TYPE.get(firstMonElement.text().replace(spaceStr, "").trim());//首月资费方式 FIRST_FEE_TYPE_ALLM
				 if(firstMonBillMode==null || "null".equals(firstMonBillMode) || StringUtils.isBlank(firstMonBillMode)){
					 firstMonBillMode = "";
				 }
			 }
		 }
		 Elements refereeElements = detaildoc.getElementsContainingOwnText("推荐人：").parents().select("dd");
		 if(refereeElements!=null && refereeElements.size()>0){
			 String refereeStr = refereeElements.get(0).text().replace(spaceStr, "").trim();
			 if(StringUtils.isNotBlank(refereeStr)){
				 refereeName = refereeStr.substring(0, refereeStr.indexOf("(")).trim();//发展人名称
				 refereeNum = refereeStr.substring(refereeStr.indexOf("(")+1, refereeStr.length()-1).trim();
			 }
		 }
		 goodsAttInfoMap.put("DevelopCode", developCode);
		 goodsAttInfoMap.put("DevelopDepId", developDepId);
		 goodsAttInfoMap.put("DevelopName", developName);
		 goodsAttInfoMap.put("FirstMonBillMode", firstMonBillMode);
		 goodsAttInfoMap.put("RealnameType", realnameType);
		 goodsAttInfoMap.put("RefereeName", refereeName);
		 goodsAttInfoMap.put("RefereeNum", refereeNum);
	}
	/**
	 * 爬虫获取合约计划信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param goodsName
	 */
	private static void getActivityInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap, Map<String, Object> activityInfoMap){
		 List<Map<String, Object>> activityInfoMapList = new ArrayList<Map<String, Object>>();
		 //Map<String, Object> activityMap = new HashMap<String, Object>();
		 String goodsName = (String)goodsInfoMap.get("GoodsName");
		 String activityName = "";//合约名称(必填)
		 String activityType = "";//合约类型(必填)
		 String actProtPer = "";//合约期限(必填)
		 
		 Elements activityTypeElements = detaildoc.getElementsContainingOwnText("合约：").parents().select("dd");
		 if(activityTypeElements!=null && activityTypeElements.size()>0){
			 String activityTypeStr = activityTypeElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(activityTypeStr) && activityTypeStr.indexOf("/")!=-1){
				 activityType = LocalCrawlerUtil.ACTIVITY_TYPE.get(activityTypeStr.substring(0, activityTypeStr.indexOf("/")));
			 }else{
				 activityType = LocalCrawlerUtil.ACTIVITY_TYPE.get(activityTypeStr);
			 }
		 }
		 Elements activityElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(活动：)").parents().select("dd");
		 if(activityElements!=null && activityElements.size()>0){
		     activityName = activityElements.get(0).text().trim();
		     if(StringUtils.isNotBlank(activityName) && "无".equals(activityName)){
		    	 activityName = "";
		     }
		 }
		 Elements actProtPerElements = detaildoc.getElementsContainingOwnText("期限：").parents().select("dd");
		 if(actProtPerElements!=null && actProtPerElements.size()>0){
			 String actProtPerStr = actProtPerElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(actProtPerStr)){
				 actProtPer = actProtPerStr.substring(0, 2);
			 }else{
				 if(StringUtils.isNotBlank(goodsName) && goodsName.indexOf("【")!=-1 && goodsName.indexOf("】")!=-1){
					 String gname = goodsName.substring(goodsName.indexOf("【")+1, goodsName.indexOf("】"));
					 //actProtPer = CrawlerUtils.getNumbers(gname);
					 if(StringUtils.isNotBlank(gname)){
						 actProtPer = gname.substring(0, 2);
						 if(!CrawlerUtils.isDigit(actProtPer)){//获取的不是数字则转换为数字
							 if("两年".equals(actProtPer)){
								 actProtPer = "24";
							 }else if("三年".equals(actProtPer)){
								 actProtPer = "36";
							 }else{
								 actProtPer = "";
							 }
						 }
					 }
				 }
			 }
		 }else{
			 if(StringUtils.isNotBlank(goodsName) && goodsName.indexOf("【")!=-1 && goodsName.indexOf("】")!=-1){
				 String gname = goodsName.substring(goodsName.indexOf("【")+1, goodsName.indexOf("】"));
				 //actProtPer = CrawlerUtils.getNumbers(gname);
				 if(StringUtils.isNotBlank(gname)){
					 actProtPer = gname.substring(0, 2);
					 if(!CrawlerUtils.isDigit(actProtPer)){//获取的不是数字则转换为数字
						 if("两年".equals(actProtPer)){
							 actProtPer = "24";
						 }else if("三年".equals(actProtPer)){
							 actProtPer = "36";
						 }else{
							 actProtPer = "";
						 }
					 }
				 }
			 }
		 }
		 //如果爬虫抓取到的合约期不为空，则使用爬虫抓取到的
		 if(!StringUtils.isEmpty(actProtPer)){
			 activityInfoMap.put("ActProtPer", actProtPer);
		 }
		//如果爬虫抓取到的活动类型不为空，则使用爬虫抓取到的
		 if(!StringUtils.isEmpty(activityType)){
			 activityInfoMap.put("ActivityType", activityType);
		 }
		 activityInfoMap.put("ActivityName", activityName);
		 
		 //合约下自选包
		 String elementName = "";//元素名称 
		 String elementType = "";//元素类型
		 String elementCode = "";//元素编码
		 String packageCode = "";//包编码
		 String packageName = "";//包名称
		 Elements elementElements = detaildoc.getElementsContainingOwnText("业务：").parents().select("dd");
		 if(elementElements!=null && elementElements.size()>0){
			 elementName = elementElements.get(0).text().trim();
			 elementType = "D";//S 服务 D 资费  A 礼品  K 礼品包 X 合作伙伴产品 
			 if(CrawlerUtils.isDigit(elementName)){//如果获取的业务是数字则为元素编码反之为元素名称
				 elementCode = elementName;
				 elementName = "";
			 }
		 }
		 List<Map<String, String>> activityPackageInfoMap = new ArrayList<Map<String, String>>();
		 Map<String, String> activityPackageMap = new HashMap<String, String>();
		 activityPackageMap.put("ElementCode", elementCode);
		 activityPackageMap.put("ElementName", elementName);
		 activityPackageMap.put("ElementType", elementType);
		 activityPackageMap.put("PackageCode", packageCode);
		 activityPackageMap.put("PackageName", packageName);
		 activityPackageInfoMap.add(activityPackageMap);
		 //activityMap.put("Package", activityPackageInfoMap);目前暂不添加可选包
		 
		 activityInfoMapList.add(activityInfoMap);
		 goodsAttInfoMap.put("ActivityInfo",activityInfoMapList);
	}
	/**
	 * 爬虫获取终端信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getResourcesInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap, Map<String, Object> resourcesInfoMap){
		 String resources = "";
		 String resourcesBrand = "";//终端品牌编码(必填)
		 String resourcesModel = "";//终端型号编码(必填)
		 String resourcesColor = "";//终端颜色编码(必填)
		 String resourcesTypeId = "";//终端机型编码(必填)
		 String resourcesCode = "";//终端串号
		 String resourcesFittings = "";//终端随机配件
		 Elements resourcesElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(手机：)").parents().select("dd");
		 if(resourcesElements!=null && resourcesElements.size()>0){
			 resources = resourcesElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(resources) && resources.indexOf(" ")!=-1){
				 String[] resourcesStr = resources.split(" ");
				 if(resourcesStr!=null && resourcesStr.length==2){//一个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1];
				 }else if (resourcesStr!=null && resourcesStr.length==3){//两个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1];
					 resourcesColor = resourcesStr[2];
				 }else if (resourcesStr!=null && resourcesStr.length==4){//三个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1]+" "+resourcesStr[2];
					 resourcesColor = resourcesStr[3];
				 }else if (resourcesStr!=null && resourcesStr.length==5){//四个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1]+" "+resourcesStr[2]+" "+resourcesStr[3];
					 resourcesColor = resourcesStr[4];
				 }else{
					 
				 }
			 }
		 }
		 if(!StringUtils.isEmpty(resourcesBrand)){
			 resourcesInfoMap.put("ResourcesBrand", resourcesBrand);
		 }
		 if(!StringUtils.isEmpty(resourcesModel)){
			 resourcesInfoMap.put("ResourcesModel", resourcesModel);
		 }
		 if(!StringUtils.isEmpty(resourcesColor)){
			 resourcesInfoMap.put("ResourcesColor", resourcesColor);
		 }
		 resourcesInfoMap.put("ResourcesCode", resourcesCode);
		 resourcesInfoMap.put("ResourcesFittings", resourcesFittings);

		 goodsAttInfoMap.put("ResourcesInfo", resourcesInfoMap);
	}
	/**
	 * 爬虫获取手机号码信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param orderMap
	 */
	private static void getPhoneInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap,Map<String, Object> orderMap,Map<String, Object> commonMap){
		 String numNet = commonMap.get("numNet").toString();//号码网别
		 String occupiedTime = "";//号码状态标识时间
		 String operatorState = "2";//操作状态
		 String proKey = orderMap.get("CustomerId").toString();//资源预占关键字(发现报文里面很多都是用了客户ID)
		 String proKeyMode = "1";//号码资源预占关键字类型 0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号

		 String phoneNum = "";//号码
		 Elements phoneNumElements = detaildoc.getElementsContainingOwnText("号码：").parents().select("dd");
		 if(phoneNumElements!=null && phoneNumElements.size()>0){
			 phoneNum = phoneNumElements.get(0).text().trim();//产品名称
			 phoneNum = phoneNum.substring(0,11);
		 }
		 
		 //NiceInfo(靓号信息)
		 String advancePay = "0";//预存话费金额
		 String cancelTagChe = "0";//考核期是否销户 0:允许 1：不允许
		 String cancelTagPro = "0";//协议期是否销户  0:允许 1：不允许
		 String changeTagChe = "0";//考核期是否过户  0:允许 1：不允许
		 String changeTagPro = "0";//协议期是否过户  0:允许 1：不允许
		 String classId = "9";//号码等级 1：一级靓号 2：二级靓号 3：三级靓号 4：四级靓号 5：五级靓号 6：六级靓号 9：普通号码
		 Map<String, Object> niceInfoMap = new HashMap<String, Object>();
		 niceInfoMap.put("AdvancePay", advancePay);
		 niceInfoMap.put("CancelTagChe", Integer.valueOf(cancelTagChe));
		 niceInfoMap.put("CancelTagPro", Integer.valueOf(cancelTagPro));
		 niceInfoMap.put("ChangeTagChe", Integer.valueOf(changeTagChe));
		 niceInfoMap.put("ChangeTagPro", Integer.valueOf(changeTagPro));
		 niceInfoMap.put("ClassId", Integer.valueOf(classId));
		 
		 Map<String, Object> phoneInfoMap = new HashMap<String, Object>();
		 phoneInfoMap.put("NumNet", "未知");//生产报文是“未知”，默认“未知”
		 phoneInfoMap.put("OccupiedTime", occupiedTime);
		 phoneInfoMap.put("OperatorState", operatorState);
		 phoneInfoMap.put("PhoneNum", phoneNum);
		 phoneInfoMap.put("ProKey", proKey);
		 phoneInfoMap.put("ProKeyMode", proKeyMode);
		 phoneInfoMap.put("NiceInfo", niceInfoMap);//爬虫抓取不到靓号信息，暂时取默认
		 
		 goodsAttInfoMap.put("PhoneInfo", phoneInfoMap);
	}
	/**
	 * 爬虫获取可选包信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getMallPackageInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap){
		 //Package 可选包信息
		 String melementCode = "";//元素编码
		 String melementName = "";//元素名称 
		 String melementType = "";//元素类型
		 String packageCode = "";//可选包编码
		 String packageName = "";//可选包名称
		 
		 List<Map<String, String>> mallPackageListMap = new ArrayList<Map<String, String>>();
		
		 Elements packageNameElements = detaildoc.getElementsContainingOwnText("包名称：").parents().select("dd");
		 if(packageNameElements!=null && packageNameElements.size()>0){
			 Map<String, String> mallPackagesMap = new HashMap<String, String>();
			 packageName = packageNameElements.get(0).text().trim();
			 mallPackagesMap.put("ElementCode", melementCode);
			 mallPackagesMap.put("ElementName", melementName);
			 mallPackagesMap.put("ElementType", melementType);
			 mallPackagesMap.put("PackageCode", packageCode);
			 mallPackagesMap.put("PackageName", packageName);
			 
			 mallPackageListMap.add(mallPackagesMap);
		 }else{
			 Elements mallPackageElements = detaildoc.select("li[class=commodityComposition]").select("dl");
			 //Elements mallPackageElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(流量包)");
			 String packageNameAll = "流量包,语音包,短/彩信,增值包,来显包,可选包";//4G组合套餐
			 if(mallPackageElements!=null && mallPackageElements.size()>0){
				 for(Element pelement:mallPackageElements){
					 Elements pelements = pelement.select("dt");
					 if(pelements!=null && pelements.size()>0){
						 packageName = pelements.text().trim();
						 packageName = packageName.replace("：", "");
						 if(StringUtils.isNotBlank(packageName) && packageNameAll.indexOf(packageName)!=-1){
							 Elements eelements = pelement.select("dd");
							 if(eelements!=null && eelements.size()>0){
								 melementName = eelements.text().trim();
								 if(StringUtils.isNotBlank(melementName) && !"无".equals(melementName)){
									 Map<String, String> mallPackagesMap = new HashMap<String, String>();
									 mallPackagesMap.put("ElementCode", melementCode);
									 mallPackagesMap.put("ElementName", melementName);
									 mallPackagesMap.put("ElementType", melementType);
									 mallPackagesMap.put("PackageCode", packageCode);
									 mallPackagesMap.put("PackageName", packageName);
									 
									 mallPackageListMap.add(mallPackagesMap);
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 goodsAttInfoMap.put("Package", mallPackageListMap);
	}
	
}