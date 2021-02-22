package com.zte.cbss.autoprocess.request;

import java.util.LinkedHashMap;
import net.sf.json.JSONObject;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.model.data.CreateCustomData;
import com.zte.cbss.autoprocess.model.data.CreateCustomPageData;

public class CreateCustomRequest extends AbsHttpRequest<CreateCustomPageData,Boolean>{

	public CreateCustomRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(CreateCustomPageData data) {
		try{
			ParameterData param = data.getParam();
			CreateCustomData formData = new CreateCustomData();
			formData.setCommon(new CreateCustomData.Common());
			CreateCustomData.CustInfo info = new CreateCustomData.CustInfo();
			info.setBIRTHDAY(param.getPsptInfo().getBirthday());
			info.setCUST_NAME(param.getBill().getCustomName());
			info.setPSPT_TYPE_CODE(param.getBill().getIdTypeCode());
			info.setPSPT_ADDR(param.getPsptInfo().getAddressInfo());
			info.setPSPT_ID(param.getBill().getPsptId());
			info.setPSPT_END_DATE(param.getBill().getPsptEndDate());
			info.setCONTACT(param.getBill().getContact());
			info.setCONTACT_PHONE(param.getBill().getContactPhone());
			info.setPHONE(param.getBill().getPhone());
			info.setPOST_ADDRESS(param.getBill().getPostAddress());
			info.setCUST_ID(data.getCUST_ID());
			formData.setCustInfo(info);
			formData.getTF_B_TRADE_CUST_PERSON().setITEM(info);
			formData.getTF_B_TRADE_CUSTOMER().getITEM().setCUST_NAME(param.getBill().getCustomName());
			formData.getTF_B_TRADE_CUSTOMER().getITEM().setPSPT_ID(param.getBill().getPsptId());
			formData.getTF_B_TRADE_CUSTOMER().getITEM().setPSPT_TYPE_CODE(param.getBill().getIdTypeCode());
			
			LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("Common", formData.getCommon());
			map.put("CustInfo", formData.getCustInfo());
			map.put("TF_B_TRADE_CUSTOMER", formData.getTF_B_TRADE_CUSTOMER());
			map.put("TF_B_TRADE_CUST_PERSON", formData.getTF_B_TRADE_CUST_PERSON());
			
			String temp = JSONObject.fromObject(map).toString();
//			logger.info("JSON："+temp);
			this.body.add(new BasicNameValuePair("Base",data.getBaseStr()));
			this.body.add(new BasicNameValuePair("Ext",temp));
			this.body.add(new BasicNameValuePair("globalPageName","personalserv.createcusttrade.CreateCust"));
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		//<?xml version="1.0" encoding="UTF-8"?><root><message></message><TradeSubmitOk tradeId='5114052534295139' subscribeId='5114052534295139' proviceOrderId='5114052534295139'><Fee feenum='0'></Fee><TradeData prepayTag="" tradeTypeCode="0020" strisneedprint="0" serialNumber="" tradeReceiptInfo="[{&quot;RECEIPT_INFO5&quot;:&quot;&quot;,&quot;RECEIPT_INFO2&quot;:&quot;&quot;,&quot;RECEIPT_INFO1&quot;:&quot;&quot;,&quot;RECEIPT_INFO4&quot;:&quot;&quot;,&quot;RECEIPT_INFO3&quot;:&quot;&quot;}]" netTypeCode="0010"/></TradeSubmitOk></root>
		try{
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
//			logger.info(respXml);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createcusttrade.CreateCust/submitMobTrade/1";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/personalserv.createcusttrade.CreateCust");
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.createcusttrade.CreateCust"+param;
	}

	@Override
	public boolean isXMLHttpRequest() {
		return true;
	}

	@Override
	public boolean isPost() {
		return true;
	}
	
}
