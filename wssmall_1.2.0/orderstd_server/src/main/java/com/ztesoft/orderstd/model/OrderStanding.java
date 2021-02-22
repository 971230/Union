package com.ztesoft.orderstd.model;

import org.apache.log4j.Logger;

import params.req.CenterMallOrderStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.TaoBaoMallOrderStandardReq;
import params.req.TemplatesOrderStandardReq;
import params.resp.CenterMallOrderStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.TaoBaoMallOrderStandardResp;
import params.resp.TemplatesOrderStandardResp;
import com.ztesoft.api.DefaultZteRopClient;
import com.ztesoft.api.ZteClient;


public class OrderStanding {
	private static Logger logger = Logger.getLogger(OrderStanding.class);
	private static ZteClient getClient() {
	      //return new DefaultZteRopClient("http://10.45.47.90:7019/router","wssmall_mm","123456","1.0");
	   return new DefaultZteRopClient("http://172.0.1.54:8080/router","wssmall_ecs","123456","1.0");
	}
    public static void  main(String[] args){
    	newMallStandard();
//    	centerMallStandard();
//    	NewMallOrderUtil.test();
//    	templatesOrderStandard();
//    	taoBaoMallStandard();
    }
    public static void newMallStandard(){
		NewMallOrderStandardReq  req = new NewMallOrderStandardReq();
		req.setBase_co_id("1500121611240686300004");
		NewMallOrderStandardResp resp = getClient().execute(req,NewMallOrderStandardResp.class);
    }
    
    public static void centerMallStandard(){
		CenterMallOrderStandardReq  req = new CenterMallOrderStandardReq();
		req.setBase_order_id("123111");
		req.setBase_co_id("151081706300000013");
		CenterMallOrderStandardResp resp = getClient().execute(req,CenterMallOrderStandardResp.class);
    }
    public static void taoBaoMallStandard(){
		TaoBaoMallOrderStandardReq  req = new TaoBaoMallOrderStandardReq();
		req.setParams("{appkey:'12469285',secret:'d7f3540761ae620397baaa27afc1c035',sessionKey:'6101a16779afeb26a73b1fa51ed9e05faa5c9a4b4bd0aa6747143122',url:'http://gw.api.taobao.com/router/rest'}");
		req.setBase_co_id("151081104200000029");
		TaoBaoMallOrderStandardResp resp = getClient().execute(req,TaoBaoMallOrderStandardResp.class);
    }
    
    public static void templatesOrderStandard(){
    	TemplatesOrderStandardReq req = new TemplatesOrderStandardReq();
    	req.setBase_co_id("151081037120000003");
    	req.setBase_order_id("1500120910371210900003");
    	req.setTemplate_code("TMPL_ORDER_CREATE_ECS_EXAMPLE");
    	req.setTemplate_version("0.1");
    	TemplatesOrderStandardResp resp = getClient().execute(req,TemplatesOrderStandardResp.class);
    	logger.info(resp);
    }
}
