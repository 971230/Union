package com.zte.cbss.autoprocess.request;

import net.sf.json.JSONObject;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.model.data.CheckProductData;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_DISCNT;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_PRODUCT;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_PRODUCT_TYPE;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_SUB_ITEM;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_SVC;

public class CheckProductRequest extends AbsHttpRequest<ParameterData,Boolean>{

	public CheckProductRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(ParameterData data) {
		try{
			CheckProductData entity = new CheckProductData();
			TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM item1 = new TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM();
			item1.setPRODUCT_ID("99999830");
			item1.setPRODUCT_MODE("00");
			item1.setSTART_DATE("2014-05-26 21:22:18");
			item1.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_PRODUCT().getITEM().add(item1);
			item1 = new TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM();
			item1.setPRODUCT_ID("89000100");
			item1.setPRODUCT_MODE("50");
			item1.setSTART_DATE("2014-06-01 00:00:00");
			item1.setEND_DATE("2015-05-31 23:59:59");
			entity.getTF_B_TRADE_PRODUCT().getITEM().add(item1);
			
			TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999792");
			item2.setDISCNT_CODE("5601000");
			item2.setSTART_DATE("2014-05-26 21:22:18");
			item2.setEND_DATE("2050-01-01 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999792");
			item2.setDISCNT_CODE("5631000");
			item2.setSTART_DATE("2014-05-26 21:22:18");
			item2.setEND_DATE("2050-01-01 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999792");
			item2.setDISCNT_CODE("5661000");
			item2.setSTART_DATE("2014-05-26 21:22:18");
			item2.setEND_DATE("2050-01-01 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999768");
			item2.setDISCNT_CODE("5990170");
			item2.setSTART_DATE("2014-05-26 21:22:18");
			item2.setEND_DATE("2050-12-30 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999767");
			item2.setDISCNT_CODE("5690000");
			item2.setSTART_DATE("2014-05-26 21:22:18");
			item2.setEND_DATE("2050-12-30 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("99999830");
			item2.setPACKAGE_ID("59999764");
			item2.setDISCNT_CODE("5702000");
			item2.setSTART_DATE("2014-06-01 00:00:00");
			item2.setEND_DATE("2050-12-30 23:59:59");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			item2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
			item2.setPRODUCT_ID("89000100");
			item2.setPACKAGE_ID("51000106");
			item2.setDISCNT_CODE("20000348");
			item2.setSTART_DATE("2014-06-01 00:00:00");
			item2.setEND_DATE("2015-05-31 23:59:59");
			item2.setITEM_ID("5114052601186276");
			entity.getTF_B_TRADE_DISCNT().getITEM().add(item2);
			
			TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50004");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999768");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50000");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50003");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50001");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50010");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50014");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50100");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50103");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50300");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50006");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50007");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50019");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50020");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50022");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			item3 = new TF_B_TRADE_SVC.TF_B_TRADE_SVC_ITEM();
			item3.setSERVICE_ID("50021");
			item3.setPRODUCT_ID("99999830");
			item3.setPACKAGE_ID("59999793");
			item3.setSTART_DATE("2014-05-26 21:22:18");
			item3.setEND_DATE("2050-12-30 00:00:00");
			entity.getTF_B_TRADE_SVC().getITEM().add(item3);
			
			TF_B_TRADE_SUB_ITEM.TF_B_TRADE_SUB_ITEM_ITEM item4 = new TF_B_TRADE_SUB_ITEM.TF_B_TRADE_SUB_ITEM_ITEM();
			item4.setATTR_TYPE_CODE("3");
			item4.setITEM_ID("5114052601186276");
			item4.setATTR_CODE("tradeId");
			item4.setATTR_VALUE("5114052634493087");
			item4.setSTART_DATE("2014-05-26 21:22:18");
			item4.setEND_DATE("2015-05-31 23:59:59");
			entity.getTF_B_TRADE_SUB_ITEM().getITEM().add(item4);
			
			TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM item5 = new TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM();
			item5.setPRODUCT_ID("99999830");
			item5.setPRODUCT_MODE("00");
			item5.setSTART_DATE("2014-05-26 21:22:18");
			item5.setEND_DATE("2050-12-30 00:00:00");
			item5.setPRODUCT_TYPE_CODE("4G000001");
			entity.getTF_B_TRADE_PRODUCT_TYPE().getITEM().add(item5);
			item5 = new TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM();
			item5.setPRODUCT_ID("89000100");
			item5.setPRODUCT_MODE("50");
			item5.setSTART_DATE("2014-06-01 00:00:00");
			item5.setEND_DATE("2015-05-31 23:59:59");
			item5.setPRODUCT_TYPE_CODE("CFSF001");
			entity.getTF_B_TRADE_PRODUCT_TYPE().getITEM().add(item5);
			
			String temp = JSONObject.fromObject(entity).toString();
//			logger.info("JSON："+temp);
			
			this.body.add(new BasicNameValuePair("Base","H4sIAAAAAAAAAFvzloG1/AbDtWqlkCBHF9d4H+fQeD9HX1clK6UQ5+B456LUxJLU0OLUoqDUdCUdqKKQyADXeGd/F6AqQwMdpcw83/yUVGcgBuoyAKoK8g8NcY13DXAMcvaIBIo92Tv76caNIAlPd48QqE6l5GKE6SFFiUDdyOYDFTybtuHF4tanexqedWyHy3m6AGVMDQ1NDEyNTI1NjA0sLc0s4LLBIY4hocFAFZl5mSVKtQBPGMa73gAAAA=="));
			this.body.add(new BasicNameValuePair("Ext",temp));
			this.body.add(new BasicNameValuePair("globalPageName","personalserv.createuser.CreateUser"));
			data.setProductData(entity); //赋值
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
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
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/checkProduct/1";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/personalserv.createuser.CreateUser");
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.createuser.CreateUser"+param;
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