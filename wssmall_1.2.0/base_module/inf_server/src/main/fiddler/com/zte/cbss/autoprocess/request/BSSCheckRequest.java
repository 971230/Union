package com.zte.cbss.autoprocess.request;

import java.lang.reflect.Field;

import net.sf.json.JSONObject;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.data.BSSCheckData;

/**
 * 发送BSS检查请求
 * @author 张浩
 * @version 1.0.0
 */
public class BSSCheckRequest extends AbsHttpRequest<BSSCheckData,CustomInfo>{
	private static Logger logger = Logger.getLogger(BSSCheckRequest.class);
	public BSSCheckRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(BSSCheckData data) {
		try{
			String inparam = JSONObject.fromObject(data).toString();
			this.body.add(new BasicNameValuePair("inparam",inparam));
			this.body.add(new BasicNameValuePair("globalPageName","pub.chkcust.MainChkCust"));
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected CustomInfo unpack(PageHttpResponse response) {
		try{
			//解析XML
			String respXml = response.getResponse();
			logger.info(respXml);
			CustomInfo result = new CustomInfo();
			Document doc = DocumentHelper.parseText(respXml);
			Element root = doc.getRootElement();// 获得根节点
			Element data = root.element("pCustInfo").element("data");
			
			Field[] fields = CustomInfo.class.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				Attribute attribute = data.attribute(fields[i].getName());
				if(attribute != null){
					fields[i].set(result, attribute.getValue());
				}
			}
			data = root.element("AuthOk");
			result.setIsBlackCust(data.attributeValue("isBlackCust"));
			result.setTouchId(data.attributeValue("touchId"));
			logger.info("============ 系统校验成功  ============");
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=swallow/pub.chkcust.MainChkCust/authenticate/1";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/pub.chkcust.MainChkCust");
		return "https://gd.cbss.10010.com/custserv?service=page/pub.chkcust.MainChkCust"+param;
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
