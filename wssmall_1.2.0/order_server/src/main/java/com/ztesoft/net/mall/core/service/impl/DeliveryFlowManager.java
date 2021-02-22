package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.sqls.SF;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DeliveryFlowManager extends BaseSupport implements IDeliveryFlow {

	@Override
	public void addDeliveryFlow(DeliveryFlow flow) {
		this.baseDaoSupport.insert("delivery_flow", flow);
	}

	@Override
	public List<DeliveryFlow> qryDeliveryFlowByDeliveryID(String deliveryID) {
		String sql = SF.orderSql("SERVICE_DELIVERY_FLOW_SELECT");
		List<DeliveryFlow> list = this.baseDaoSupport.queryForList(sql, DeliveryFlow.class, deliveryID);
		return list;
	}

	@Override
	public List<DeliveryFlow> qryFlowInfo(String logi_no, String logi_id) {
			List<DeliveryFlow> list = new ArrayList<DeliveryFlow>();
			
			try {
				String sql = SF.orderSql("SERVICE_LOGI_COMPANY_SELECT");
				List logi_list = this.baseDaoSupport.queryForList(sql, Logi.class, logi_id);
				Logi logi = null;
				if(!ListUtil.isEmpty(logi_list)){
					logi = (Logi) logi_list.get(0);
				}
				
				if(logi != null){
					String company_code = logi.getCompany_code();
					String company_name = logi.getName();
					String key_str = logi.getKey_str();
					
					URL url = new URL("http://api.kuaidi100.com/api?id="+key_str+"&com="+ company_code + "&nu=" + logi_no+ "&valicode=&show=0&muti=1&order=desc");
					URLConnection con = url.openConnection();
					con.setAllowUserInteraction(false);
					InputStream urlStream = url.openStream();
					String type = URLConnection.guessContentTypeFromStream(urlStream);
					String charSet = null;
					if (type == null)
						type = con.getContentType();
	
					if (type == null || type.trim().length() == 0
							|| type.trim().indexOf("text/html") < 0)
						return list;
	
					if (type.indexOf("charset=") > 0)
						charSet = type.substring(type.indexOf("charset=") + 8);
																			

					byte b[] = new byte[10000];
					int numRead = urlStream.read(b);
					String content = new String(b, 0, numRead, charSet);
					while (numRead != -1) {
						numRead = urlStream.read(b);
						if (numRead != -1) {
							String newContent = new String(b, 0, numRead, charSet);
							content += newContent;
						}
					}
					logger.info(content);
					String contentString = java.net.URLDecoder.decode(content, "utf-8");
					JSONObject o = JSONObject.fromObject(contentString);
					Object status = o.get("status");
					if (status.toString().equals("1")) {
						Object data = o.get("data");
						String data0 = java.net.URLDecoder.decode(data.toString(), "utf-8");
						String data1 = data0.substring(1, data0.length() - 1);
						String[] dataArraryString = data1.split("},");
						for (int i = 0; i < dataArraryString.length - 1; i++) {
							dataArraryString[i] = dataArraryString[i] + "}";
						}
						for (int i = 0; i < dataArraryString.length; i++) {
							DeliveryFlow dFlow = new DeliveryFlow();
							String datan = java.net.URLDecoder.decode(
									dataArraryString[i], "utf-8");
							logger.info("dataArraryString[" + i + "]=" + datan);
							JSONObject o1 = JSONObject.fromObject(datan);
							String time = o1.get("time").toString();
							String context = o1.get("context").toString();
							
							dFlow.setCreate_time(time);
							dFlow.setLogi_name(company_name);
							dFlow.setDescription(context);
	
							list.add(dFlow);
						}
					}
					urlStream.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return list;
		}

}
