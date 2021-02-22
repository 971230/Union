package esearch_server;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.req.EsearchAddReq;
import params.resp.EsearchAddResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.model.ESearchData;

public class EsearchTest {
	public static void main(String[] args) {
		ZteClient client=getRopZteClient();
		
		ESearchData esData = new ESearchData();
		esData.setLog_id("111");
		esData.setOperation_code("es_order_queue");
		esData.setIn_param("testtest2016年1月4号");// 写入报文
		
		EsearchAddReq req = new EsearchAddReq();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ds = sdf.format(new Date());
		esData.setIndex("es_order_queue_idx"+ds);
		esData.setType("es_order_queue");
		req.setEsData(esData);
		EsearchAddResp resp = client.execute(req, EsearchAddResp.class);
		
	}
	
	
	public static  ZteClient getDubboZteClient(){
		return ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
	}
	
	public static  ZteClient getRopZteClient(){
		return ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
	}
}