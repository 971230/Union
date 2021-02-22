package com.zte.cbss.autoprocess.sometestcode;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MakeXml {
	private static Logger logger = Logger.getLogger(MakeXml.class);
	public static void main(String[] args) throws DocumentException {
		String respXml=
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><message></message><TradeSubmitOk tradeId='5114060735995682' subscribeId='5114060735995682' proviceOrderId='5114060735995682'><Fee><data operateType=\"1\" feeTypeCode=\"1010\" payTag=\"0\" tradeId=\"5114060735995682\" maxDerateFee=\"2000\" feeitemCode=\"1010\" feeMode=\"0\" oldfee=\"2000\" fee=\"2000\"/><data operateType=\"1\" feeTypeCode=\"100005\" payTag=\"0\" tradeId=\"5114060735995682\" maxDerateFee=\"0\" feeitemCode=\"100005\" feeMode=\"2\" oldfee=\"12000\" fee=\"12000\"/></Fee><TradeData prepayTag=\"\" tradeTypeCode=\"0116\" strisneedprint=\"0\" serialNumber=\"18575895164\" tradeReceiptInfo=\"[{&quot;RECEIPT_INFO5&quot;:&quot;&quot;,&quot;RECEIPT_INFO2&quot;:&quot;&quot;,&quot;RECEIPT_INFO1&quot;:&quot;&quot;,&quot;RECEIPT_INFO4&quot;:&quot;&quot;,&quot;RECEIPT_INFO3&quot;:&quot;&quot;}]\" netTypeCode=\"0050\"/></TradeSubmitOk></root>";

		/**解析返回值中新的tradeId,这个是用于缴费步骤的*/
		try{
			Document doc = DocumentHelper.parseText(respXml);
			Element root = doc.getRootElement();// 获得根节点
			Element eleTradeSubmitOk = root.element("TradeSubmitOk");
			String tradeId=eleTradeSubmitOk.attributeValue("tradeId");
		//	data.setTradeId(tradeId);
			logger.info(tradeId);
		}catch(Exception e){
		//	log.info("解析xml出错",e);
			e.printStackTrace();
		}
	}
}
