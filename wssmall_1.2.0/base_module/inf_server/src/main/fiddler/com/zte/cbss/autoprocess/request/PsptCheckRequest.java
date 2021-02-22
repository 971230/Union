package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.PsptInfo;
import com.zte.cbss.autoprocess.model.data.PsptCheckData;

/**
 * 身份证检查请求
 * @author 张浩
 * @version 1.0.0
 */
public class PsptCheckRequest extends AbsHttpRequest<PsptCheckData,PsptInfo>{

	PsptCheckData data;
	
	public PsptCheckRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PsptCheckData data) {
		try{
			this.data = data;
			this.body.add(new BasicNameValuePair("PSPT_ID",data.getPsptId()));
			this.body.add(new BasicNameValuePair("CUST_NAME",data.getCustomName()));
			this.body.add(new BasicNameValuePair("globalPageName",data.getGlobalPageName()));
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected PsptInfo unpack(PageHttpResponse response) {
		try{
			//解析XML
			String respXml = response.getResponse();
//			logger.info(respXml);
			PsptInfo result = new PsptInfo();
			Document doc = DocumentHelper.parseText(respXml);
			Element root = doc.getRootElement();// 获得根节点
			Element data = root.element("personCardInfo").element("data");
			result.setAddressInfo(data.attributeValue("addressInfo"));
			result.setBirthday(data.attributeValue("birthday"));
			result.setIdentityName(data.attributeValue("identityName"));
			result.setIdentityNo(data.attributeValue("identityNo"));
			result.setLicencelssAuth(data.attributeValue("licencelssAuth"));
			result.setNationality(data.attributeValue("nationality"));
			result.setSex(data.attributeValue("sex"));
			result.setValidityStart(data.attributeValue("validityStart"));
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=swallow/popupdialog.PersonCardReaderSX/checkPsptInfo/1";
	}

	@Override
	public String getReferer() {
		return this.data.getReferer();
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
