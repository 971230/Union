package com.zte.cbss.autoprocess.request;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.Common;
import com.zte.cbss.autoprocess.model.data.MobileProductPage1_1Date;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_DISCNT;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_PRODUCT;
import com.zte.cbss.autoprocess.model.data.TF_B_TRADE_PRODUCT_TYPE;
import com.zte.cbss.autoprocess.model.data.TRADE_OTHER_INFO;
import com.zte.cbss.autoprocess.model.data.TRADE_SUB_ITEM;

/**
 * 第三步:提交移网产品服务办理-->模拟[移网产品/服务变更 ]页面中确定按钮.
 *
 */
public class MobileProductPage1_1Request extends AbsHttpRequest<MobileNetworkServiceHandleReq,String>{
	
	private final static Logger logger = Logger.getLogger(MobileProductPage1_1Request.class);

	private final String globalPageName = "personalserv.changeelement.ChangeElement";

	public MobileProductPage1_1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		try {
			String inparam = String.valueOf(JSONObject.fromObject(getRequestObject(data)));
			inparam = inparam.replaceAll("common", "Common").replaceAll("x_DATATYPE", "X_DATATYPE");
			logger.info("提交移网产品服务办理请求字符串:"+inparam);
			this.body.add(new BasicNameValuePair("Ext",inparam));
			this.body.add(new BasicNameValuePair("globalPageName",globalPageName));
			this.body.add(new BasicNameValuePair("Base",client.getParam().getTradeBase()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取请求对象
	 * @param req
	 * @return
	 */
	private MobileProductPage1_1Date getRequestObject(MobileNetworkServiceHandleReq req){
		MobileProductPage1_1Date mobileProductPage1_1Date = new MobileProductPage1_1Date();
		String start_date = "";
		start_date = com.ztesoft.ibss.common.util.DateUtil.nextMonthFirstDate(
				client.getParam().getOrder_create_time(),
				com.ztesoft.ibss.common.util.DateUtil.DATE_FORMAT_2);
		Common Common = new Common();
		mobileProductPage1_1Date.setCommon(Common);

		//基本产品信息
		TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM tf_b_trade_product_item = new TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM();
//		tf_b_trade_product_item.setPRODUCT_ID("89056167");
		tf_b_trade_product_item.setPRODUCT_ID("89096193");//20150226修改
		tf_b_trade_product_item.setPRODUCT_MODE("01");
		tf_b_trade_product_item.setSTART_DATE(start_date);
		tf_b_trade_product_item.setEND_DATE("2050-12-31 00:00:00");
		tf_b_trade_product_item.setMODIFY_TAG("0");
		tf_b_trade_product_item.setUSER_ID_A("-1");
		tf_b_trade_product_item.setITEM_ID("");
		tf_b_trade_product_item.setBRAND_CODE("4G00");
		tf_b_trade_product_item.setX_DATATYPE("NULL");
		List<TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM> tf_b_trade_product_item_list = new ArrayList<TF_B_TRADE_PRODUCT.TF_B_TRADE_PRODUCT_ITEM>();
		tf_b_trade_product_item_list.add(tf_b_trade_product_item);
		TF_B_TRADE_PRODUCT tf_b_trade_product = new TF_B_TRADE_PRODUCT();
		tf_b_trade_product.setITEM(tf_b_trade_product_item_list);
		mobileProductPage1_1Date.setTF_B_TRADE_PRODUCT(tf_b_trade_product);

		//5元的
		TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM tf_b_trade_discnt_item_1 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
//		tf_b_trade_discnt_item_1.setID("5115011368520341");
//		tf_b_trade_discnt_item_1.setID("5115011368519463");
		tf_b_trade_discnt_item_1.setID(super.client.getParam().getUserId());
		tf_b_trade_discnt_item_1.setID_TYPE("1");
//		tf_b_trade_discnt_item_1.setPRODUCT_ID("89056167");
		tf_b_trade_discnt_item_1.setPRODUCT_ID("89096193");
//		tf_b_trade_discnt_item_1.setPACKAGE_ID("51058884");
		tf_b_trade_discnt_item_1.setPACKAGE_ID("51093077");
//		tf_b_trade_discnt_item_1.setDISCNT_CODE(req.getPackFlag()!=null?req.getPackFlag().equals("a")?"5997400":"5997501":""); // --(b 10 替换 5997501,a 5 替换 5997401)
		tf_b_trade_discnt_item_1.setDISCNT_CODE(req.getPackFlag()!=null?req.getPackFlag().equals("a")?"5997401":"5997501":""); // --(b 10 替换 5997501,a 5 替换 5997401)
		tf_b_trade_discnt_item_1.setSPEC_TAG("0");
		tf_b_trade_discnt_item_1.setMODIFY_TAG("0");
		tf_b_trade_discnt_item_1.setSTART_DATE(start_date);
		tf_b_trade_discnt_item_1.setEND_DATE("2050-12-31 23:59:59");
		tf_b_trade_discnt_item_1.setRELATION_TYPE_CODE("");
		tf_b_trade_discnt_item_1.setUSER_ID_A("-1");
		tf_b_trade_discnt_item_1.setITEM_ID("");
		tf_b_trade_discnt_item_1.setX_DATATYPE("NULL");

		//0折优惠资费
		TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM tf_b_trade_discnt_item_2 = new TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM();
//		tf_b_trade_discnt_item_2.setID("5115011368520341");
//		tf_b_trade_discnt_item_2.setID("5115011368519463");
		tf_b_trade_discnt_item_2.setID(super.client.getParam().getUserId());
		tf_b_trade_discnt_item_2.setID_TYPE("1");
		tf_b_trade_discnt_item_2.setPRODUCT_ID("89096193");
		tf_b_trade_discnt_item_2.setPACKAGE_ID("51058886");
		tf_b_trade_discnt_item_2.setDISCNT_CODE("5997600");
		tf_b_trade_discnt_item_2.setSPEC_TAG("0");
		tf_b_trade_discnt_item_2.setMODIFY_TAG("0");
		tf_b_trade_discnt_item_2.setSTART_DATE(start_date);
		tf_b_trade_discnt_item_2.setEND_DATE("2050-12-31 23:59:59");
		tf_b_trade_discnt_item_2.setRELATION_TYPE_CODE("");
		tf_b_trade_discnt_item_2.setUSER_ID_A("-1");
		tf_b_trade_discnt_item_2.setITEM_ID("");
		tf_b_trade_discnt_item_2.setX_DATATYPE("NULL");
		
		
		
		List<TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM> tf_b_trade_discnt_item_list = new ArrayList<TF_B_TRADE_DISCNT.TF_B_TRADE_DISCNT_ITEM>();
		tf_b_trade_discnt_item_list.add(tf_b_trade_discnt_item_1);
		tf_b_trade_discnt_item_list.add(tf_b_trade_discnt_item_2);
		TF_B_TRADE_DISCNT tf_b_trade_discnt = new TF_B_TRADE_DISCNT();
		tf_b_trade_discnt.setITEM(tf_b_trade_discnt_item_list);
		mobileProductPage1_1Date.setTF_B_TRADE_DISCNT(tf_b_trade_discnt);
		
		
		

		TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM tf_b_trade_product_type_item = new TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM();
//		tf_b_trade_product_type_item.setPRODUCT_ID("89056167");
		tf_b_trade_product_type_item.setPRODUCT_ID("89096193");//20150226修改
		tf_b_trade_product_type_item.setPRODUCT_MODE("01");
		tf_b_trade_product_type_item.setSTART_DATE(start_date);
		tf_b_trade_product_type_item.setEND_DATE("2050-12-31 00:00:00");
		tf_b_trade_product_type_item.setMODIFY_TAG("0");
		tf_b_trade_product_type_item.setX_DATATYPE("NULL");
		tf_b_trade_product_type_item.setUSER_ID(super.client.getParam().getUserId());
		tf_b_trade_product_type_item.setPRODUCT_TYPE_CODE("4G000001");
		List<TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM> tf_b_trade_product_type_item_list = new ArrayList<TF_B_TRADE_PRODUCT_TYPE.TF_B_TRADE_PRODUCT_TYPE_ITEM>();
		tf_b_trade_product_type_item_list.add(tf_b_trade_product_type_item);
		TF_B_TRADE_PRODUCT_TYPE tf_b_trade_product_type = new TF_B_TRADE_PRODUCT_TYPE();
		tf_b_trade_product_type.setITEM(tf_b_trade_product_type_item_list);
		mobileProductPage1_1Date.setTF_B_TRADE_PRODUCT_TYPE(tf_b_trade_product_type);

		TRADE_SUB_ITEM trade_sub_item = new TRADE_SUB_ITEM();
		trade_sub_item.setLINK_NAME(super.client.getParam().getCustomInfo().getCustName());
		trade_sub_item.setLINK_PHONE(super.client.getParam().getBill().getSerialNumber());
		mobileProductPage1_1Date.setTRADE_SUB_ITEM(trade_sub_item);

		TRADE_OTHER_INFO.TRADE_OTHER_INFO_ITEM trade_other_info_item = new TRADE_OTHER_INFO.TRADE_OTHER_INFO_ITEM();
		trade_other_info_item.setBLACK_CUST("8");
		trade_other_info_item.setCHECK_TYPE("0");
		TRADE_OTHER_INFO trade_other_info = new TRADE_OTHER_INFO();
		trade_other_info.setITEM(trade_other_info_item);
		mobileProductPage1_1Date.setTRADE_OTHER_INFO(trade_other_info);
		return mobileProductPage1_1Date;
		
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
			logger.info("提交移网产品服务办理返回字符串:"+respXml);
			return respXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.changeelement.ChangeElement/submitMobTrade/1";
	}

	@Override
	public String getReferer() {
		return "https://gd.cbss.10010.com/custserv";
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
