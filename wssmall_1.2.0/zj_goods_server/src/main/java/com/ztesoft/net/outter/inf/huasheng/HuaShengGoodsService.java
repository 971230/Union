package com.ztesoft.net.outter.inf.huasheng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.Xml2JsonUtil;
import com.ztesoft.net.outter.inf.model.OutterTmpl;
import com.ztesoft.net.outter.inf.service.DBUtils;
import commons.BeanUtils;

public class HuaShengGoodsService {
	
	@Resource
	DBUtils dbUtils;
	@Resource
	HuaShengGoodsManager hsGoodsManager;

	public boolean executeInfService(OutterTmpl tmpl) throws Exception {//获取、处理数据失败则抛出异常，防止漏数据
		boolean flag = false;
		//调用华盛商品主数据接口并保存数据
		String url = dbUtils.queryServiceURL("HS_GOODS_SYNC");
		String tableName = "ES_GOODS_HUASHENG";
		StringBuffer reqXmlBuff = new StringBuffer();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String data_end_time = tmpl.getData_end_time();
		Date d = dateFormat.parse(data_end_time);
		String start_time = df.format(d);//往前推3分钟，降低漏数据概率
		String end_time = dateFormat.format(new Date(System.currentTimeMillis()));
		reqXmlBuff.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		reqXmlBuff.append(" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">");
		reqXmlBuff.append(" <soapenv:Header/><soapenv:Body><urn:ZB2CDWL_001><INPUT><![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		reqXmlBuff.append(" <INPUT><ERSDS>").append(start_time).append("</ERSDS>");
		reqXmlBuff.append("<ERSDE>").append(df.format(new Date(System.currentTimeMillis()))).append("</ERSDE>");
		reqXmlBuff.append("<MATNR></MATNR>");
		reqXmlBuff.append("<RESERVE1></RESERVE1><RESERVE2></RESERVE2><RESERVE3></RESERVE3></INPUT>]]></INPUT>");
		reqXmlBuff.append("</urn:ZB2CDWL_001></soapenv:Body></soapenv:Envelope>");
		
		//1、调接口
		String respXml = HuaShengServiceClient.callServers(url, reqXmlBuff.toString());
		if(StringUtils.isNotEmpty(respXml)){
			try{
				String respJson = Xml2JsonUtil.xml2JSON(respXml).replaceAll(">", "");
				HuaShengGoodsDto goodsDto = JsonUtil.fromJson(respJson, HuaShengGoodsDto.class);
				//成功
				if(StringUtils.equals("S", goodsDto.getTYPE())){
					List<HuaShengGoodsItems> goodsItems = goodsDto.getITMES();
					for (HuaShengGoodsItems item : goodsItems) {
						for(int i = 0 ; i < item.getITEM().size(); i++){
							HuaShengGoodsItemsDetail itemDetail = item.getITEM().get(i);
							Map paramMap = new HashMap();
							Map whereMap = new HashMap();
							BeanUtils.bean2Map(paramMap, itemDetail);
							paramMap.remove("RESERVE2");
							paramMap.remove("RESERVE3");
							//判断是否已存在
							if(goodsIsExists(itemDetail.getMATNR())){
								paramMap.put("UPDATE_TIME", dateFormat.format(new Date()));
								whereMap.put("MATNR", itemDetail.getMATNR());
								//存在 更新
								dbUtils.update(tableName, paramMap, whereMap);
							}else{
								//获取序列
								String id = dbUtils.querySqlResult("select S_ES_GOODS_HUASHENG.Nextval from dual");
								paramMap.put("ID", id);
								paramMap.put("CREATE_TIME", dateFormat.format(new Date()));
								//不存在 添加
								dbUtils.insertOrderData(tableName, paramMap);
							}
							//添加映射数据
							String matnr = itemDetail.getMATNR();
							String mchk = itemDetail.getMCHK();
							String mtart = itemDetail.getMTART();
							Map<String,String> params = new HashMap<String, String>();
							params.put("matnr", matnr);
							//params.put("mchk", mchk);
							//params.put("mtart", mtart);
							hsGoodsManager.hsGoodsMapping(params);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		//2、保存数据
		flag = true;
		if(flag){
			tmpl.setData_end_time(end_time);
		}
		
		return true;
	}
	
	/**
	 * 判断华盛商品是否已存在
	 * @param sn
	 * @return
	 */
	private boolean goodsIsExists(String sn){
		String sql = "select count(1) from es_goods_huasheng c where c.MATNR = '"+sn+"'";
		String count = dbUtils.querySqlResult(sql);
		if("0".equals(count)){
			return false;
		}else{
			return true;
		}
	}
}
