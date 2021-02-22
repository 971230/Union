package com.zte.cbss.autoprocess.request;

import java.util.LinkedHashMap;

import net.sf.json.JSONObject;

import org.apache.http.message.BasicNameValuePair;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.model.data.CheckProductData;
import com.zte.cbss.autoprocess.model.data.SubmitMobTradeData;

public class SubmitMobTradeRequest extends AbsHttpRequest<ParameterData,Boolean>{

	public SubmitMobTradeRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(ParameterData data) {
		try{
			SubmitMobTradeData entity = new SubmitMobTradeData();
			
			LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("Common", entity.getCommon());
			map.put("TF_B_TRADE_ITEM", entity.getTF_B_TRADE_ITEM());
			map.put("TF_B_TRADE_USER", entity.getTF_B_TRADE_USER());
			map.put("TF_B_TRADE_RES", entity.getTF_B_TRADE_RES());
			map.put("TF_B_TRADE_ACCT", entity.getTF_B_TRADE_ACCT());
			
			CheckProductData formData = data.getProductData();
			map.put("TF_B_TRADE_PRODUCT", formData.getTF_B_TRADE_PRODUCT());
			map.put("TF_B_TRADE_DISCNT", formData.getTF_B_TRADE_DISCNT());
			map.put("TF_B_TRADE_SVC", formData.getTF_B_TRADE_SVC());
			map.put("TF_B_TRADE_SUB_ITEM", formData.getTF_B_TRADE_SUB_ITEM());
			map.put("TF_B_TRADE_PRODUCT_TYPE", formData.getTF_B_TRADE_PRODUCT_TYPE());
			
			String temp = JSONObject.fromObject(map).toString();
//			logger.info("JSON："+temp);
			this.body.add(new BasicNameValuePair("Base","H4sIAAAAAAAAAFvzloG1/AbDtWqlkCBHF9d4H+fQeD9HX1clK6UQ5+B456LUxJLU0OLUoqDUdCUdqKKQyADXeGd/F6AqQwMdpcw83/yUVGcgBuoyAKoK8g8NcY13DXAMcvaIBIo92Tv76caNIAlPd48QqE6l5GKE6SFFiUDdyOYDFTybtuHF4tanexqedWyHy3m6AGVMDQ1NDEyNTI1NjA0sLc0s4LLBIY4hocFAFZl5mSVKtQBPGMa73gAAAA=="));
			this.body.add(new BasicNameValuePair("Ext",temp));
			this.body.add(new BasicNameValuePair("globalPageName","personalserv.createuser.CreateUser"));
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/submitMobTrade/1";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/personalserv.createuser.CreateUser");
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.createuser.CreateUser" + param;
	}

	@Override
	public boolean isXMLHttpRequest() {
		return false;
	}

	@Override
	public boolean isPost() {
		return false;
	}

}
