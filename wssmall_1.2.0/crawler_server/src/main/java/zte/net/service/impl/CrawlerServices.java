package zte.net.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.BackOrderLayerReq;
import params.req.BindCustInfo2OrderReq;
import params.req.CallOutOperationReq;
import params.req.CallRefundReq;
import params.req.CardSubmitInfoReq;
import params.req.CrawlerAccountInfoReq;
import params.req.CrawlerCodeReq;
import params.req.CrawlerDeliveryInfoReq;
import params.req.CrawlerMiFiNumberReq;
import params.req.CrawlerProcCondReq;
import params.req.CrawlerReq;
import params.req.CrawlerTerminalInfoReq;
import params.req.CrawlerUpdateGoodsInfoReq;
import params.req.CrawlerUpdatePostInfoReq;
import params.req.CrawlerWriteCardSucReq;
import params.req.GetCardDatasReq;
import params.req.ManualModifyBuyerInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.OpenAccountDetailReq;
import params.req.OpenAccountSubmitOrderReq;
import params.req.OperationRecordReq;
import params.req.OrderSubmitReq;
import params.req.QueryOrderAllocationStatusReq;
import params.req.QueryOrderProcessReq;
import params.req.QueryRunThreadStatusReq;
import params.req.ReAllotOrderReq;
import params.req.RejectLayerReq;
import params.req.SingleApplicationReq;
import params.req.SubmitOrderReq;
import params.req.UpdateCrawlerSettingReq;
import params.req.ZbBackfillLogisticsReq;
import params.req.ZbOrderAuditStatusReq;
import params.req.ZbOrderDeliveryCodeQueryReq;
import params.req.ZbOrderDeliveryReq;
import params.req.ZbOrderDistributeReq;
import params.req.ZbOrderStateQueryReq;
import params.req.ZbOrderVerifyReq;
import params.req.ZbQueryOrderDetailReq;
import params.resp.BindCustInfo2OrderResp;
import params.resp.CrawlerFeeInfo;
import params.resp.CrawlerProcCondResp;
import params.resp.CrawlerResp;
import params.resp.GetCardDatasResp;
import params.resp.ManualModifyBuyerInfoResp;
import params.resp.OpenAccountDetailResp;
import params.resp.OpenAccountSubmitOrderResp;
import params.resp.OperationRecordResp;
import params.resp.OrderSubmitResp;
import params.resp.QueryOrderProcessResp;
import params.resp.QueryRunThreadStatusrResp;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;
import params.resp.ZbQueryOrderDetailResp;
import services.ServiceBase;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.iservice.ICrawlerService;
import zte.net.service.ICustomerOrderInquiryService;
import zte.net.service.IOrderArtificialSectionService;
import zte.net.service.IOrderDeliverySectionService;
import zte.net.service.IOrderOpenAccountSectionService;
import zte.net.service.IOrderVerifySectionService;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.autoprocess.base.exception.BusinessException;
import com.ztesoft.autoprocess.base.exception.SystemException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;

import consts.ConstsCore;


@ZteSoftCommentAnnotation(type="class", desc="爬虫登录服务", summary="爬虫登录服务")
public class CrawlerServices extends ServiceBase implements ICrawlerService{
	@Resource
	private IOrderOpenAccountSectionService orderOpenAccountSectionService;
	@Resource
	private IOrderDeliverySectionService orderDeliverySectionService;
	@Resource
	private IOrderVerifySectionService orderVerifySectionService;
	@Resource
	private IOrderArtificialSectionService orderArtificialSectionService;
	@Resource
	private ICustomerOrderInquiryService customerOrderInquiryService;
	
	/**
	 * 登录总商API接口
	 */
	@Override
	public CrawlerResp zbClientLogin(CrawlerReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			if(!StringUtils.isEmpty(req.getCookie())){
				String username = req.getUserName();
				String password = req.getPassWord();
				String cookieValue = req.getCookie();
				if(StringUtils.isNotBlank(cookieValue)){
					//如果直接通过获取验证码登录，这里一定需要复制，不然自动登录定时器获取不到验证码无法定时自动登录更新cookie
					ZBSystemClient.cookieValue1 = cookieValue;
					ZBSystemClient client = ZBSystemClient.getInstanceByCookie(username,password,"",cookieValue);
			        if (client != null) {
			            ClientPool.add(username, client);
			            logger.info("用户["+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"]用cookie登陆成功!");
			            resp.setResp_code("0");//成功
						resp.setResp_msg("登录成功");
			        } else {
			        	//关闭连接
			        	if(ClientPool.activeClientMap.size()>0){
			        		Iterator iterator=ClientPool.activeClientMap.entrySet().iterator();
				        	while(iterator.hasNext()){
				        		Entry<String, ZBSystemClient> entry = (Entry<String, ZBSystemClient>) iterator.next();
				        		ClientPool.close(entry);
				        	}
			        	}
			            logger.info("用户["+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"]用cookie登陆失败,cookie已经失效!");
			            ZBSystemClient.cookieValue1 = ZBSystemClient.cookieValue;
			            resp.setResp_code("1");//失败
						resp.setResp_msg("登录失败");
			        }
				}
			}else{
				String loginCookie = ZBSystemClient.zbClientLogin(req.getUserName(),req.getPassWord(),req.getValidateCode());
				if(!StringUtil.isEmpty(loginCookie)){
					resp.setCookie(loginCookie);
					resp.setResp_code("0");//成功
					resp.setResp_msg("登录成功");
				}else{
					resp.setResp_code("1");//失败
					resp.setResp_msg("登录失败");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");//失败
			resp.setResp_msg("登录失败");
		}
		return resp;
	}
	/**
	 * 获取总商验证码API接口
	 */
	@Override
	public CrawlerResp doSendValidate(CrawlerCodeReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			boolean isSend = ZBSystemClient.doSendValidate(req.getUserName(),req.getPassWord(),req.getSendType());
			if(isSend){
				resp.setResp_code("0");//成功
				resp.setResp_msg("发送验证码成功");
			}else{
				resp.setResp_code("1");//失败
				resp.setResp_msg("发送验证码失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");//失败
			resp.setResp_msg("发送验证码失败");
		}
		return resp;
	}
	
	@Override
	public CrawlerResp reAllotOrder(ReAllotOrderReq req) {
		// TODO Auto-generated method stub
		return orderOpenAccountSectionService.reAllotOrder(req);
	}

	@Override
	public OpenAccountDetailResp openAccountDetail(OpenAccountDetailReq req) {
		// TODO Auto-generated method stub
		return orderOpenAccountSectionService.getOpenAccountDetail(req);
	}

	@Override
	public ZteResponse modifyOpenAccountGoods(ModifyOpenAccountGoodsReq req) {
		// TODO Auto-generated method stub
		return orderOpenAccountSectionService.modifyOpenAccountGoods(req);
	}


	@Override
	public CrawlerResp callOutOperation(CallOutOperationReq req) {
		CrawlerResp resp = new CrawlerResp();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				resp = ZBSystemClient.callOutOperation(client, req);
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);//成功
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);//成功
			resp.setError_msg("外呼确认异常"+e.getMessage());
		}
		return resp;
	}
	

	/**
	 * 修改总商配送信息API接口
	 */
	@Override
	public CrawlerResp updateZbDeliveryInfo(CrawlerDeliveryInfoReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				//通过爬虫开户详情页获取固定参数
	    		List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
	    		formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//总商订单ID长订单号
	    		
	    		//获取地址自动开户和手动开户的接口地址不一样  add by mo.chencheng 2017-04-06
				String url = "";
				if("1".equals(req.getIsManual())){//手动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL);
				}else{//自动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);
				}
	    		
				String openDetail = client.post(formparams, url);
				if(StringUtils.isBlank(openDetail) || openDetail.indexOf("当前订单["+req.getOrderNo()+"]已经进入开户处理") == -1 ){
					resp.setResp_code("1");//0为成功非0失败
					resp.setResp_msg("爬虫开户详情页未获取到订单信息");
					return resp;
				}
				CrawlerAccountInfoReq creq = null;
				if(StringUtils.isNotBlank(openDetail)){
					creq = getZbOpenDetail(openDetail);
				}
				/*JSONObject formparams.add(new BasicNameValuePair = new JSONObject();
				updateParams.put("orderId", req.getOrderId());//订单ID
				updateParams.put("tmplId", creq.getTmplId());
				updateParams.put("merchantId", creq.getMerchantId());
				updateParams.put("oldProvinceId", req.getOldProvinceId());
				updateParams.put("oldCityId", req.getOldCityId());
				updateParams.put("provinceId", req.getProvinceId());
				updateParams.put("cityId", req.getCityId());
				updateParams.put("districtId", req.getDistrictId());
				updateParams.put("postAddr", req.getPostAddr());
				updateParams.put("dispachCode", req.getDispachCode());
				updateParams.put("dlvTypeCode", req.getDlvTypeCode());
				updateParams.put("payWayCode", req.getPayWayCode());
				updateParams.put("logisticCode", req.getLogisticCode());
				updateParams.put("delayTime", req.getDelayTime());
				updateParams.put("deliverySelfName", req.getDeliverySelfName());
				updateParams.put("deliverySelfPhone", req.getDeliverySelfPhone());
				//配送方式为自提，则传入自提点信息
				if(StringUtils.isNotBlank(req.getDispachCode()) && req.getDispachCode().equals("02")){
					updateParams.put("selfFetchAddrId", req.getSelfFetchAddrId());
					updateParams.put("selfType", req.getSelfType()!=null?req.getSelfType():"1");
					updateParams.put("selfAddrName", req.getSelfAddrName());
				}
				String json = updateParams.toString();
				String respStr = client.jsonPost(json, ZBOrderUrlConsts.UPDATE_ZB_DELIVERY_INFO_URL);*/
				
				// 创建参数队列
	    		List<NameValuePair> updateformparams = new ArrayList<NameValuePair>();
	    		updateformparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//订单ID
	    		updateformparams.add(new BasicNameValuePair("tmplId", creq.getTmplId()));
	    		updateformparams.add(new BasicNameValuePair("merchantId", creq.getMerchantId()));
	    		updateformparams.add(new BasicNameValuePair("oldProvinceId", req.getOldProvinceId()));
	    		updateformparams.add(new BasicNameValuePair("oldCityId", req.getOldCityId()));
	    		updateformparams.add(new BasicNameValuePair("provinceId", req.getProvinceId()));
	    		updateformparams.add(new BasicNameValuePair("cityId", req.getCityId()));
	    		updateformparams.add(new BasicNameValuePair("districtId", req.getDistrictId()));
	    		updateformparams.add(new BasicNameValuePair("postAddr", req.getPostAddr()));
	    		updateformparams.add(new BasicNameValuePair("dispachCode", req.getDispachCode()));
	    		updateformparams.add(new BasicNameValuePair("dlvTypeCode", req.getDlvTypeCode()));
	    		updateformparams.add(new BasicNameValuePair("payWayCode", req.getPayWayCode()));
	    		updateformparams.add(new BasicNameValuePair("logisticCode", req.getLogisticCode()));
	    		updateformparams.add(new BasicNameValuePair("delayTime", req.getDelayTime()));
	    		updateformparams.add(new BasicNameValuePair("deliverySelfName", req.getDeliverySelfName()));
	    		updateformparams.add(new BasicNameValuePair("deliverySelfPhone", req.getDeliverySelfPhone()));
	    		//配送方式为自提，则传入自提点信息
				if(StringUtils.isNotBlank(req.getDispachCode()) && req.getDispachCode().equals("02")){
					updateformparams.add(new BasicNameValuePair("selfFetchAddrId", req.getSelfFetchAddrId()));
					updateformparams.add(new BasicNameValuePair("selfType", req.getSelfType()!=null?req.getSelfType():"1"));
					updateformparams.add(new BasicNameValuePair("selfAddrName", req.getSelfAddrName()));
				}
				
				//获取地址自动开户和手动开户的接口地址不一样  add by mo.chencheng 2017-04-06
				if("1".equals(req.getIsManual())){//手动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_UPDATE_ZB_DELIVERY_INFO_URL);
				}else{//自动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.UPDATE_ZB_DELIVERY_INFO_URL);
				}
				
				String respStr = client.post(updateformparams, url);
				//返回内容不为空并且是json格式
				if(StringUtils.isNotBlank(respStr) && CrawlerUtils.isJson(respStr)){
					JSONObject respJSON = JSONObject.fromObject(respStr);
					if(respJSON!=null && respJSON.containsKey("respInfo")){
						String respCode = respJSON.getJSONObject("respInfo").getString("RespCode");
						String respDesc = respJSON.getJSONObject("respInfo").getString("RespDesc");
						resp.setResp_code(respCode);//0为成功非0失败
						resp.setResp_msg(respDesc);
					}
				}else{
					if(respStr != null && respStr.indexOf("<title>订单处理详情</title>") != -1) {
						resp.setResp_code("0");//0为成功非0失败
						resp.setResp_msg("修改总商配送信息成功");
			        }else{
			        	resp.setResp_code("1");//0为成功非0失败
						resp.setResp_msg("修改总商配送信息失败");
			        }
				}
			}else{
				resp.setResp_code("1");//0为成功非0失败
				resp.setResp_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
		} catch (SystemException e) {
			e.printStackTrace();
			resp.setResp_code("1");//0为成功非0失败
			resp.setResp_msg("修改总商配送信息失败");
		} catch (BusinessException e) {
			e.printStackTrace();
			resp.setResp_code("1");//0为成功非0失败
			resp.setResp_msg("修改总商配送信息失败");
		}
		return resp;
	}
	
	/**
	 * 爬虫校验开户信息
	 */
	@Override
	public CrawlerResp checkZbAccountInfo(CrawlerAccountInfoReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				//校验入网信息
				if(StringUtils.isBlank(req.getCustomerName()) || StringUtils.isBlank(req.getCertNum()) 
				|| StringUtils.isBlank(req.getCertType()) || StringUtils.isBlank(req.getCertAddress())){
					resp.setResp_code("1");//0为成功非0失败
					resp.setResp_msg("入网信息存在错误！证件名称、证件号码、证件类型、证件地址不能为空！");
					return resp;
				}
				//如果是老用户便捷换卡处理流程，则调用方直接调用写卡处理流程
				if(StringUtils.isNotBlank(req.getIsCardChange()) && "1".equals(req.getIsCardChange())){
					resp.setResp_code("2");//0为成功非0失败
					resp.setResp_msg("请直接进入写卡处理流程！");
					return resp;
				}
				//判断发展人编码是否为空并且是需要校验发展人编码则提示发展人不能为空
				if(StringUtils.isBlank(req.getDevelopCode()) && req.isNeedCheckRecommend()){
					resp.setResp_code("1");//0为成功非0失败
					resp.setResp_msg("请在商品清单中录入发展人编码！");
					return resp;
				}
				//通过爬虫开户详情页获取固定参数
	    		List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
	    		formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//总商订单ID长订单号
				String openDetail = client.post(formparams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL));
				if(StringUtils.isBlank(openDetail) || openDetail.indexOf("当前订单["+req.getOrderNo()+"]已经进入开户处理") == -1 ){
					resp.setResp_code("1");//0为成功非0失败
					resp.setResp_msg("爬虫开户详情页未获取到订单信息");
					return resp;
				}
				CrawlerAccountInfoReq creq = null;
				if(StringUtils.isNotBlank(openDetail)){
					creq = getZbOpenDetail(openDetail);
				}
				//资源校验 
				// 非含终端上网卡商品   终端串号脱离焦点调用资源变更接口
				// 含终端上网卡商品  必须同时输入终端串号和上网卡号码才触发资源变更接口调用
				//1、终端校验(调用资源变更接口)
				if(StringUtils.isNotBlank(req.getResourcesCode())){
					CrawlerTerminalInfoReq treq = new CrawlerTerminalInfoReq();
					treq.setOrderId(req.getOrderId());//订单ID
					treq.setTmplId(creq.getTmplId());//商品类型ID
					treq.setImeiCode(req.getResourcesCode());//终端串号
					treq.setAllocationFlag(req.getAllocationFlag());//调拨标识 1标识调拨否则不调拨
					resp = checkTerminalInfo(treq);//调用资源变更接口
					if(StringUtils.isNotBlank(resp.getResp_code()) && !"0".equals(resp.getResp_code())){
						logger.info("=====CrawlerServices checkZbAccountInfo 上网卡号码预占接口调用出错【errorMsg:"+resp.getError_msg()+"】");
						return resp;
					}
				}
				//2、上网卡号码不为空并且是白卡则需调用号码预占接口
				if(StringUtils.isNotBlank(req.getNetCardNum())){
					if(!CrawlerUtils.isDigit(req.getNetCardNum())){
						resp.setResp_code("1");//0为成功非0失败
						resp.setResp_msg("上网卡号码必须为数字");
						return resp;
					}
					if(req.getNetCardNum().length()!=11){
						resp.setResp_code("1");//0为成功非0失败
						resp.setResp_msg("上网卡号码长度必须是11位");
						return resp;
					}
					if(StringUtils.isNotBlank(req.getIsOldCard()) && "0".equals(req.getIsOldCard())){
						//封装参数 通过爬虫调用号码预占接口
						CrawlerMiFiNumberReq mreq = new CrawlerMiFiNumberReq();
						mreq.setOrderId(req.getOrderId());
						mreq.setNetCardNum(req.getNetCardNum());
						resp = checkMiFiNumber(mreq);
						if(StringUtils.isNotBlank(resp.getResp_code()) && !"0".equals(resp.getResp_code())){
							logger.info("=====CrawlerServices checkZbAccountInfo 终端资源变更接口调用出错【errorMsg:"+resp.getError_msg()+"】");
							return resp;
						}
					}
				}
				
				List<NameValuePair> accountformparams = new ArrayList<NameValuePair>();
				accountformparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//订单ID
				accountformparams.add(new BasicNameValuePair("tmplId", creq.getTmplId()));
				if(StringUtils.isNotBlank(req.getNetCardNum())){
					accountformparams.add(new BasicNameValuePair("netCardNum", req.getNetCardNum()));
				}
				accountformparams.add(new BasicNameValuePair("userTag", creq.getUserTag()));
				accountformparams.add(new BasicNameValuePair("accountFlag", creq.getAccountFlag()));
				accountformparams.add(new BasicNameValuePair("resourcesCode", req.getResourcesCode()));
				accountformparams.add(new BasicNameValuePair("certType", req.getCertType()));
				accountformparams.add(new BasicNameValuePair("certNum", req.getCertNum()));
				accountformparams.add(new BasicNameValuePair("customerName", req.getCustomerName()));
				accountformparams.add(new BasicNameValuePair("contactPerson", req.getContactPerson()));
				accountformparams.add(new BasicNameValuePair("contactPhone", req.getContactPhone()));
				accountformparams.add(new BasicNameValuePair("iccId", req.getIccId()));
				accountformparams.add(new BasicNameValuePair("isOldCard", req.getIsOldCard()));
				accountformparams.add(new BasicNameValuePair("postAddr", req.getPostAddr()));
				accountformparams.add(new BasicNameValuePair("goodInstId", creq.getGoodInstId()));//需通过爬虫获取
				accountformparams.add(new BasicNameValuePair("incomeMoney", creq.getIncomeMoney()));
				accountformparams.add(new BasicNameValuePair("preNum", req.getPreNum()));
				accountformparams.add(new BasicNameValuePair("confirmCheckCert", req.getConfirmCheckCert()));
				accountformparams.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));
				
				String respStr = client.post(accountformparams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_ZB_ACCOUNT_INFO_URL));

				//返回内容不为空并且是json格式
				if(StringUtils.isNotBlank(respStr) && CrawlerUtils.isJson(respStr)){
					JSONObject respJSON = JSONObject.fromObject(respStr);
					if(respJSON!=null && respJSON.containsKey("respInfo")){
						JSONObject respInfo = respJSON.getJSONObject("respInfo");
						String respDesc = "";
						String respCode = "";
						if(respInfo.containsKey("ErrDesc")){
							respDesc = respInfo.getString("ErrDesc");
						}
						if(respInfo.containsKey("ErrCode")){
							respCode = respInfo.getString("ErrCode");
						}
						if(respInfo.containsKey("ErrorMsg") && respInfo.getBoolean("ErrorMsg")){
							resp.setResp_code("1");//0为成功非0失败
							resp.setResp_msg(respDesc);
						}
						if(respInfo.containsKey("ErrCode") && StringUtils.isNotBlank(respCode) && "CONFIRM".equals(respCode)){
							resp.setResp_code("1");//0为成功非0失败
							resp.setResp_msg(respDesc+"身份证校验失败是否继续开户！");
						}
						if(respInfo.containsKey("ErrCode") && StringUtils.isNotBlank(respCode) && "0000".equals(respCode)){
							resp.setResp_code("0");//0为成功非0失败
							resp.setResp_msg(respDesc);
							if(respInfo.containsKey("OtherFee")){//多缴预存
								String otherFee = respInfo.getString("OtherFee");
								resp.setOtherFee(otherFee);
							}
							if(respInfo.containsKey("CardFeeDisable")){//应收卡费是否可编辑 1:不可编辑，其他值则可以编辑应收卡费
								String cardFeeDisable = respInfo.getString("CardFeeDisable");
								resp.setCardFeeDisable(cardFeeDisable);
							}
							if(respInfo.containsKey("UsimFee")){//应收卡费
								String usimFee = respInfo.getString("UsimFee");
								resp.setUsimFee(usimFee);
							}
							if(respInfo.containsKey("TotleFee")){//费用实收合计
								String totleFee = respInfo.getString("TotleFee");
								resp.setTotleFee(totleFee);
							}
							if(respInfo.containsKey("FeeInfo")){//费用明细
								JSONArray feeArray = respInfo.getJSONArray("FeeInfo");
								List<CrawlerFeeInfo> crawlerFeeInfo = new ArrayList<CrawlerFeeInfo>();
								if(feeArray!=null && feeArray.size()>0){
									for(int i=0;i<feeArray.size();i++){
										CrawlerFeeInfo feeInfo = new CrawlerFeeInfo();
										JSONObject feeJson = feeArray.getJSONObject(i);
										feeInfo.setFeeCategory(feeJson.getString("feeCategory"));
										feeInfo.setFeeID(feeJson.getString("feeId"));
										feeInfo.setMaxRelief(feeJson.getString("maxRelief"));
										feeInfo.setFeeDes(feeJson.getString("feeDes"));
										feeInfo.setOrigFee(feeJson.getString("origFee"));
										feeInfo.setReliefFee(feeJson.getString("reliefFee"));
										feeInfo.setRealFee(feeJson.getString("realFee"));
										feeInfo.setReliefResult(feeJson.getString("reliefResult"));
										crawlerFeeInfo.add(feeInfo);
									}
								}
								resp.setCrawlerFeeInfo(crawlerFeeInfo);
							}
						}else{
							resp.setResp_code("1");//0为成功非0失败
							resp.setResp_msg(respDesc);
						}
					}
				}else{
					if(respStr != null && respStr.indexOf("<title>订单处理详情</title>") != -1) {
						resp.setResp_code("0");//0为成功非0失败
						resp.setResp_msg("开户校验成功");
			        }else{
			        	resp.setResp_code("1");//0为成功非0失败
						resp.setResp_msg("开户校验失败");
			        }
				}
			}else{
				resp.setResp_code("1");//0为成功非0失败
				resp.setResp_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");//0为成功非0失败
			resp.setResp_msg("开户校验失败"+e);
		}
		return resp;
	}
	/**
	 * 终端资源变更接口
	 */
	@Override
	public CrawlerResp checkTerminalInfo(CrawlerTerminalInfoReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				//封装参数
				/*JSONObject terminalParams = new JSONObject();
				terminalParams.put("imeiCode",req.getImeiCode());//终端串号
				terminalParams.put("orderId", req.getOrderId());//订单ID
				terminalParams.put("tmplId", req.getTmplId());//商品类型ID
				terminalParams.put("allocationFlag", req.getAllocationFlag());//调拨标识 1标识调拨否则不调拨
				String json = terminalParams.toString();
				String respStr = client.jsonPost(json, ZBOrderUrlConsts.CALL_ZB_CHECK_RES_URL);*/
				
				List<NameValuePair> terminalformparams = new ArrayList<NameValuePair>();
				terminalformparams.add(new BasicNameValuePair("imeiCode",req.getImeiCode()));//终端串号
				terminalformparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//订单ID
				terminalformparams.add(new BasicNameValuePair("tmplId", req.getTmplId()));//商品类型ID
				terminalformparams.add(new BasicNameValuePair("allocationFlag", req.getAllocationFlag()));//调拨标识 1标识调拨否则不调拨
				
				String respStr = client.post(terminalformparams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_ZB_CHECK_RES_URL));
				
				//返回内容不为空并且是json格式
				if(StringUtils.isNotBlank(respStr) && CrawlerUtils.isJson(respStr)){
					JSONObject respJSON = JSONObject.fromObject(respStr);
					if(respJSON!=null && respJSON.containsKey("respInfo")){
						JSONObject respInfo = respJSON.getJSONObject("respInfo");
						if(respInfo.containsKey("resSuccess")){
							String resSuccess = respInfo.getString("resSuccess");
							if(StringUtils.isNotBlank(resSuccess) && "CONFIRM".equals(resSuccess)){
								String channelId = "";
								String channelName = "";
								String myChannelId = "";
								if(respInfo.containsKey("channelId")){
									channelId = respInfo.getString("channelId");
								}
								if(respInfo.containsKey("channelName")){
									channelName = respInfo.getString("channelName");
								}
								if(respInfo.containsKey("myChannelId")){
									myChannelId = respInfo.getString("myChannelId");
								}
								resp.setResp_code("3");//0为成功非0失败
								resp.setResp_msg("终端归属“"+channelId+"("+channelName+")”，需调拨至您所在的渠道“"+myChannelId+"”才能继续，确定进行调拨操作？");
							}
							if(respInfo.containsKey("ErrCode") && StringUtils.isNotBlank(respInfo.getString("ErrCode"))){
								String errDesc = "";
								if(respInfo.containsKey("ErrDesc")){
									errDesc = respInfo.getString("ErrDesc");
								}
								resp.setResp_code("1");//0为成功非0失败
								resp.setResp_msg("终端资源变更失败："+errDesc);
							}else{
								resp.setResp_code("0");//0为成功非0失败
								resp.setResp_msg("终端资源变更成功");
							}
						}
					}
				}
			}else{
				resp.setResp_code("1");//0为成功非0失败
				resp.setResp_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");//0为成功非0失败
			resp.setResp_msg("上网卡号码预占失败"+e);
		}
		return resp;
	}
	
	/**
	 * 上网卡号码预占接口
	 */
	@Override
	public CrawlerResp checkMiFiNumber(CrawlerMiFiNumberReq req) {
		CrawlerResp resp = new CrawlerResp();
		try {
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				//封装参数 通过爬虫调用号码预占接口
				/*JSONObject miFiNumberParams = new JSONObject();
				miFiNumberParams.put("orderId", req.getOrderId());//订单ID
				miFiNumberParams.put("resourcesCode",req.getNetCardNum());//上网卡号码
				String json = miFiNumberParams.toString();
				String respStr = client.jsonPost(json, ZBOrderUrlConsts.CHECK_ZB_MIFI_NUMBER_URL);*/
				
				List<NameValuePair> miFiNumberformparams = new ArrayList<NameValuePair>();
				miFiNumberformparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//订单ID
				miFiNumberformparams.add(new BasicNameValuePair("resourcesCode",req.getNetCardNum()));//上网卡号码
				
				String respStr = client.post(miFiNumberformparams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CHECK_ZB_MIFI_NUMBER_URL));
				
				//返回内容不为空并且是json格式
				if(StringUtils.isNotBlank(respStr) && CrawlerUtils.isJson(respStr)){
					JSONObject respJSON = JSONObject.fromObject(respStr);
					if(respJSON!=null && respJSON.containsKey("RespCode")){
						String respCode = respJSON.getString("RespCode");
						String respDesc = "";
						if(respJSON.containsKey("RespDesc")){
							respDesc = respJSON.getString("RespDesc");
						}
						if(StringUtils.isNotBlank(respCode) && !"0000".equals(respCode)){
							resp.setResp_code("1");//0为成功非0失败
							resp.setResp_msg("上网卡号码预占失败："+respDesc);
						}else{
							resp.setResp_code("0");//0为成功非0失败
							resp.setResp_msg("上网卡号码预占成功");
						}
					}
				}
			}else{
				resp.setResp_code("1");//0为成功非0失败
				resp.setResp_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");//0为成功非0失败
			resp.setResp_msg("上网卡号码预占失败"+e);
		}
		return resp;
	}
	
	/**
	 * 爬虫获取开户详情页面信息
	 * @param openDetail
	 * @return
	 */
	private static CrawlerAccountInfoReq getZbOpenDetail(String openDetail){
		CrawlerAccountInfoReq req = new CrawlerAccountInfoReq();
		if(StringUtils.isNotBlank(openDetail)){
			Document doc = Jsoup.parse(openDetail);
			String tmplId = "";
			String merchantId = "";
			String accountFlag = "";
			String goodInstId = "";
			String userTag = "";
			String incomeMoney = "";
			String isZFKNewOrder = "";
			if(doc!=null){
				Element tmplIdElement = doc.getElementById("goodsTMPLId");
				if(tmplIdElement!=null){//防止标签不存在
					tmplId = tmplIdElement.val();
					req.setTmplId(tmplId);
				}
				Element merchantIdElement = doc.getElementById("merchantId");
				if(merchantIdElement!=null){
					merchantId = merchantIdElement.val();
					req.setMerchantId(merchantId);
				}
				Element accountFlagElement = doc.getElementById("accountFlag");
				if(accountFlagElement!=null){
					accountFlag = accountFlagElement.val();
					req.setAccountFlag(accountFlag);
				}else{
					req.setAccountFlag("0");//默认为0
				}
				Element goodInstIdElement = doc.getElementById("goodInstId");
				if(goodInstIdElement!=null){
					goodInstId = goodInstIdElement.val();
					req.setGoodInstId(goodInstId);
				}
				Element userTagElement = doc.getElementById("userTag");
				if(userTagElement!=null){
					userTag = userTagElement.val();
					req.setUserTag(userTag);
				}else{
					req.setUserTag("1");//默认为1
				}
				Elements incomeMoneyElement = doc.getElementsByAttributeValue("name", "incomeMoney");
				if(incomeMoneyElement!=null && incomeMoneyElement.size()>0){
					incomeMoney = incomeMoneyElement.val();
					req.setIncomeMoney(incomeMoney);
				}
				Elements isZFKNewOrderElement = doc.getElementsByAttributeValue("name", "isZFKNewOrder");
				if(isZFKNewOrderElement!=null && isZFKNewOrderElement.size()>0){
					isZFKNewOrder = isZFKNewOrderElement.val();
					req.setIsZFKNewOrder(isZFKNewOrder);
				}else{
					req.setIsZFKNewOrder("0");//默认为0
				}
				
			}
		}
		return req;
	}
	
	@Override
	public ZteResponse zbBackfillLogistics(ZbBackfillLogisticsReq req) {
		// TODO Auto-generated method stub
		 return orderDeliverySectionService.zbBackfillLogistics(req);
	}
	@Override
	public ZteResponse zbOrderDelivery(ZbOrderDeliveryReq req) {
		// TODO Auto-generated method stub
		return orderDeliverySectionService.zbOrderDelivery(req);
	}
	@Override
	public ZteResponse submitOrder(SubmitOrderReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.submitOrder(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("确认提交订单失败");
		}
		return resp;
		
	}
	@Override
	public OrderSubmitResp callSubmit(OrderSubmitReq req) {
		// TODO Auto-generated method stub
		OrderSubmitResp resp = new OrderSubmitResp();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.callSubmit(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("订单提交按钮失败");
		}
		return resp;
	}
	@Override
	public ZteResponse callCardSubmit(CardSubmitInfoReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.callCardSubmit(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("提交写卡失败");
		}
		return resp;
	}
	@Override
	public GetCardDatasResp getCardDatas(GetCardDatasReq req) {
		// TODO Auto-generated method stub
		GetCardDatasResp resp = new GetCardDatasResp();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.getCardDatas(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("获取写卡数据失败");
		}
		return resp;
	}
	@Override
	public ZteResponse writeCardSuc(CrawlerWriteCardSucReq req){
		ZteResponse resp ;
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.writeCardSuc(client, req);
			}else{
				resp = new ZteResponse();
				resp.setError_code(ConstsCore.ERROR_FAIL);//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp = new ZteResponse();
			resp.setError_code(ConstsCore.ERROR_FAIL);//失败
			resp.setError_msg("执行写卡成功失败");
		}
		return resp;
	}
	@Override
	public ZteResponse singleApplication(SingleApplicationReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.singleApplication(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("申请退单失败");
		}
		return resp;
	}

	@Override
	public ZteResponse orderAuditStatusModify(ZbOrderAuditStatusReq req) {
		// TODO Auto-generated method stub
		return orderArtificialSectionService.orderAuditStatusModify(req);
	}
	@Override
	public ZteResponse orderStateQuery(ZbOrderStateQueryReq req) {
		// TODO Auto-generated method stub
		return orderArtificialSectionService.orderStateQuery(req);
	}
	@Override
	public ZteResponse zbOrderQueryDeliveryNum(ZbOrderDeliveryCodeQueryReq req) {
		// TODO Auto-generated method stub
		return orderDeliverySectionService.zbOrderQueryDeliveryNum(req);
	}
	
	@Override
	public ZteResponse backOrderLayer(BackOrderLayerReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.backOrderLayer(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("退单失败");
		}
		return resp;
	}
	@Override
	public ZteResponse rejectLayer(RejectLayerReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return client.rejectLayer(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("驳回失败");
		}
		return resp;
	}
	@Override
	public ZteResponse callRefund(CallRefundReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return ZBSystemClient.callRefund(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("退款失败");
		}
		return resp;
	}
	
	@Override
	public QueryOrderProcessResp queryOrderProcess(QueryOrderProcessReq req) {
		// TODO Auto-generated method stub
		QueryOrderProcessResp resp = new QueryOrderProcessResp();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				List<String> orderList = ZBSystemClient.queryOrderProcess(client,req.getOrderNo());
				if(orderList.size()>0){
					resp.setOrderIdList(orderList);
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("爬虫获取自动开户列表成功");
				}else{
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单在总商开户列表中未查到");
				}
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);//0为成功非0失败
				resp.setError_msg("爬虫获取自动开户列表失败");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);//失败
			resp.setError_msg("爬虫获取自动开户列表失败");
		}
		return resp;
	}
	
	@Override
	public ZteResponse updateGoodsInfo(CrawlerUpdateGoodsInfoReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return ZBSystemClient.updateGoodsInfo(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("修改商品清单失败");
		}
		return resp;
	}
	
	@Override
	public ZteResponse updatePostInfo(CrawlerUpdatePostInfoReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
				return ZBSystemClient.updatePostInfo(client, req);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("修改配送信息失败");
		}
		return resp;
	}
	
	@Override
	public ZteResponse queryOrderAllocationStatusByOrderNo(QueryOrderAllocationStatusReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(client!=null){
		     	String initStatus = "0";
	        	if(CrawlerSetting.OPERATION_URL_MAP == null || CrawlerSetting.OPERATION_URL_MAP.size() <= 0){//执行job之前先判断是不是初始化了参数，先用操作链接作为判断标准，如果没有操作链接后面的所有操作都无法执行
	        		String server_ip = StringUtil.getLocalIpAddress();
	        		String server_port = StringUtil.getContextPort();
	        		
	        		logger.info(server_ip+":"+server_port);
	        		CrawlerProcCondReq initReq = new CrawlerProcCondReq();
	        		initReq.setIp(server_ip);
	        		initReq.setPort(server_port);
	        		ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	        		CrawlerProcCondResp initResp = zteClient.execute(req, CrawlerProcCondResp.class);
	        		if("0".equals(initResp.getError_code())){
	        			CrawlerSetting.init(initResp);	
	        		}else{
	        			initStatus = "1";
	        			logger.info("==========爬虫查询初始化配置出错："+resp.getError_msg());
	        		}
	        	}
				if("0".equals(initStatus)){
					int status = ZBSystemClient.queryOrderAllocationStatusByOrderNo(client, req.getOrderNo());
					resp.setBody(String.valueOf(status));
					resp.setError_code("0");
				}else{
					
				}
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("查询分配状态失败");
		}
		return resp;
	}

	@Override
	public ZbOrderVerifyResp orderVerify(ZbOrderVerifyReq verifyReq) {
		// TODO Auto-generated method stub
		return orderVerifySectionService.orderVerify(verifyReq);
	}
	@Override
	public ZbOrderDistributeResp orderDistribute(ZbOrderDistributeReq req) {
		// TODO Auto-generated method stub
		return orderVerifySectionService.orderDistribute(req);
	}
	
	
	@Override
	public ZteResponse updateCrawlerSetting(UpdateCrawlerSettingReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try{
			String result = CrawlerSetting.updateCrawlerSetting(req);
			if(!StringUtils.isEmpty(result) && !"0".equals(result)){
				resp.setError_code("1");
				resp.setError_msg(result);
			}else{
				resp.setError_code("0");//成功
			}
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("更新爬虫查询条件失败");
		}
		return resp;
	}

	
	@Override
	public OperationRecordResp queryZBLogisticsInformation(OperationRecordReq req){
		// TODO Auto-generated method stub
		OperationRecordResp resp = new OperationRecordResp();
		try{
			String orderId = req.getOrderId();
			if(StringUtils.isEmpty(orderId)){
				resp.setError_code("1");
				resp.setError_msg("查询单号为空");
			}
			
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if(true){
				return ZBSystemClient.queryZBLogisticsInformation(client, orderId);
			}else{
				resp.setError_code("1");//0为成功非0失败
				resp.setError_msg("获取不到爬虫客户端对象，请先通过爬虫登录总商");
			}
			
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("查询总商物流轨迹失败");
		}
		return resp;
	}
	
	@Override
	public QueryRunThreadStatusrResp queryRunThreadStatus(QueryRunThreadStatusReq req){
		// TODO Auto-generated method stub
		QueryRunThreadStatusrResp resp = new QueryRunThreadStatusrResp();
		try{
			Map<String,String> runThreadNames = CrawlerSetting.queryRunThreadStatus();
			resp.setRunThreadName(runThreadNames);
			resp.setError_code("0");//0为成功非0失败
		}catch (Exception e){
			e.printStackTrace();
			resp.setError_code("1");//失败
			resp.setError_msg("查询运行线程状态失败");
		}
		return resp;
	}
	
	@Override
	public BindCustInfo2OrderResp bindCustInfo2Order(BindCustInfo2OrderReq req) {
		
		return orderOpenAccountSectionService.bindCustInfo2Order(req);
	}
	
	@Override
	public OpenAccountSubmitOrderResp openAccountSubmitOrder(
			OpenAccountSubmitOrderReq req) {
		
		return orderOpenAccountSectionService.openAccountSubmitOrder(req);
	}
	
	@Override
	public ManualModifyBuyerInfoResp manualModifyBuyerInfo(
			ManualModifyBuyerInfoReq req) {
		
		return orderOpenAccountSectionService.manualModifyBuyerInfo(req);
	}
	@Override
	public ZbQueryOrderDetailResp queryOrderDetail(ZbQueryOrderDetailReq req) {
		
		return customerOrderInquiryService.queryOrderDetail(req);
	}
}
