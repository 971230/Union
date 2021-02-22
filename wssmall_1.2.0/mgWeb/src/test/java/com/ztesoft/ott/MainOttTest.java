//package com.ztesoft.ott;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ztesoft.api.DefaultZteRopClient;
//import org.testng.annotations.Test;
//import zte.params.ott.req.OttCommunityIndexReq;
//import zte.params.ott.resp.OttCommunityIndexResp;
//
///**
// * Created with IntelliJ IDEA.
// * User: guangping
// * Date: 2014-06-09 14:41
// * To change this template use File | Settings | File Templates.
// */
//public class MainOttTest {
//    //10.45.54.105:8080  localhost:8010   10.45.47.170:9002
//    private String url = "http://localhost:8010/router";
//    private String appKey = "wssmall_fj";
//    private String secret = "123456";
//    private String userId = "201406046741000759";
//    private String appId = "1398755715028";
//
//    @Test
//    public void run() {
//      for(int i=0;i<5;i++){
//          DefaultZteRopClient client = new DefaultZteRopClient(url, appKey, secret);
//          OttCommunityIndexReq req = new OttCommunityIndexReq();
//          req.setUser_id(userId);
//          req.setReqCode("getPage");
//          req.setApp_id(appId);
//          OttCommunityIndexResp resp = client.execute(req, OttCommunityIndexResp.class);
//
//          logger.info("响应:" + JSONObject.toJSONString(resp));
//      }
//    }
//
//
//  /*  @Test
//    public void generateUrl() {
//        String appSecret = "123456";
//        OTTWeb ottWeb = new OTTWeb();
//        ottWeb.setAppKey("0000");
//        ottWeb.setService_id("20140315");
//
//        Map paramas = JSONObject.parseObject(JSONObject.toJSONString(ottWeb), Map.class);
//        String sign = RopUtils.sign(paramas, appSecret);
//
//        logger.info("加密串:" + sign);
//    }
//
//
//    @Test
//    public void generatUrl2(){
//        String url="http://baidu.com";
//        String appSecret = "123456";
//        OTTWeb ottWeb = new OTTWeb();
//        ottWeb.setAppKey("0000");
//        ottWeb.setService_id("20140315");
//
//        logger.info("生成链接:"+ GenerateUrl.GenerateUrl(url,appSecret,ottWeb));
//    }
//
//    @Test
//    public void getFields() {
//        Class clazz=null;
//
//        OTTWeb web = new OTTWeb();
//        Field[] fields = web.getClass().getDeclaredFields();
//        for(Field field : fields){
//            logger.info("字段:"+field.getName());
//
//        }
//    }*/
//
//}
