package com.ztesoft.test.dubbo.base;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;

public class DubboClientTest {

    public ApplicationContext context = null;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @BeforeClass
    public void setUp() throws Exception {
    	System.setProperty("CONFIG", "F:\\workspace5\\conf\\fj\\"); //此处替换成为本地配置 单元测试 ，此处替换本地配置add by wui
        String configs[]=new String[]{"classpath*:spring/*.xml","classpath*:dubbo/spring/*.xml","classpath*:dubbo/reference/dubbo-client-reference.xml","classpath*:dubbo/reference/remote-reference.xml"}; //,"classpath*:dubbo/service/*.xml","classpath*:dubbo/reference/dubbo-client-reference.xml","classpath*:dubbo/reference/remote-reference.xml"
        context = new ClassPathXmlApplicationContext(configs);
       /* //GoodsGetReq req = new GoodsGetReq();
		//req.setPackage_id("20130918130000016556");
		//req.setSn("16163013");
        //IGoodsService goodServices = SpringContextHolder.getBean("goodServices");
        //GoodsGetResp resp = goodServices.getGoods(req);
       *//* CoQueueAddReq no_req = new CoQueueAddReq();
		no_req.setCo_name("号码同步");
		no_req.setBatch_id("2222222222");
		no_req.setService_code("CO_HAOMA");//CO_HAOMA
		no_req.setAction_code("M");
		no_req.setObject_type("HAOMA");
		no_req.setObject_id("22222222233");
		no_req.setOper_id("-1");
		ICoQueueService coService = SpringContextHolder.getBean("coQueueService");
		coService.add(no_req);
        logger.info("11111");*//*
        
        InventoryReduceReq iReq = new InventoryReduceReq();
		//iReq.setOrder_id("10001");
		iReq.setGoods_id("6901020100007");
		iReq.setNum(1);
		iReq.setHouse_id("1");
		//iReq.setOrg_id("10001");
		IWarehouseService warehouseService = SpringContextHolder.getBean("warehouseService");
		InventoryReduceResp resp = warehouseService.inventoryReduce(iReq);*/
		
    }

    
    public  ZteClient getDubboZteClient(){
    	return ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
    }

}
