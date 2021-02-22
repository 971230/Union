package com.ztesoft.net.server.servlet.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.wyg.req.RefreshGoodsCacheReq;
import zte.net.ecsord.params.wyg.resp.RefreshGoodsCacheResp;

import com.google.gson.Gson;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.servlet.ICommonServlet;

/**
 * 商品系统导入/更新商品后刷新订单这边的商品缓存数据
 * 
 * @author Dylan.zeng
 */
public class RefreshGoodsCacheServlet implements ICommonServlet {
	private final static Log log = LogFactory.getLog(RefreshGoodsCacheServlet.class);
	private  static String interfaceName="【供商品系统使用,商品系统导入/更新商品后刷新订单这边的商品缓存数据】";


	private String targetReqType;
	public RefreshGoodsCacheServlet(String targetReqType) {
		this.targetReqType = targetReqType;
	}

	/**
	 * 顺丰路由查询接口
	 */
	@Override
	public String service(String reqString) throws Exception {
		log.info(interfaceName+"请求的字符串："+reqString);
		log.info(interfaceName+"请求的字符串："+reqString);
		String returnString = null;
		RefreshGoodsCacheResp zteResponse = new RefreshGoodsCacheResp();
		zteResponse.setResp_code("1");//默认失败
		RefreshGoodsCacheReq req = null;
		if(StringUtils.isNotBlank(reqString)) {
			Gson gson = new Gson();
			try {
				req = gson.fromJson(reqString, RefreshGoodsCacheReq.class);
				if(!targetReqType.equals(req.getReqType())){
					zteResponse.setResp_msg("请求类型[reqType]非法!");
				}else if(!CommonDataFactory.getInstance().checkCommonServletReq(req.getReqId(),req.getReqType())){
					zteResponse.setResp_msg("您无权访问此接口!");
				}else{
					zteResponse = refresh(req);//业务处理
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info(interfaceName+"解析JSON字符串出错");
				log.info(interfaceName+"解析JSON字符串出错");
				zteResponse.setResp_msg("解析JSON字符串出错");
			}
		} else {
			zteResponse.setResp_msg("请求参数为空");
		}

		String resp_msg = zteResponse.getResp_msg();
		if(null!=resp_msg&&resp_msg.length()>200){//返回信息限制在200字内
			resp_msg = resp_msg.substring(0,200);
		}
		String serial_no = "";
		String time = "";
		try{
			time = DateUtil.getTime5();
			serial_no = req.getSerial_no();
		}catch(Exception e){
			//什么都没做
		}
		returnString = "{\"serial_no\":\""+serial_no+"\",\"time\":\""+time+"\",\"resp_code\":\""+zteResponse.getResp_code()+"\",\"resp_msg\":\""+resp_msg+"\"}";
		log.info(interfaceName+"返回的字符串："+returnString);
		log.info(interfaceName+"返回的字符串："+returnString);
		return returnString;
		
		
		
		
		/*log.info(interfaceName + "请求报文内容：" + reqString);
		JSONObject json = null;

		try {
			json = JSONObject.fromObject(reqString);
		} catch (Exception e) {
			log.info(interfaceName + "请求报文不满足JSON格式");
			throw new Exception(interfaceName + "请求报文不满足JSON格式");
		}

		String serialNo = null;
		String reqType = null;
		String reqId = null;
		String goodsIds = null;
		try {
			reqType = json.getString("reqType");
			reqId = json.getString("reqId");// 请求ID
			serialNo = json.getString("serial_no");
			goodsIds = json.getString("goodsIds");
			RefreshGoodsCacheReq req = new RefreshGoodsCacheReq();
			req.setGoodsIds(goodsIds);
			RefreshGoodsCacheResp zteResponse = new RefreshGoodsCacheResp();
			zteResponse.setResp_code("1");//默认失败
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			zteResponse = client.execute(req, RefreshGoodsCacheResp.class);//业务处理
		
			return "{\"serial_no\":\"11111\",\"time\":\""+new Date()+"\",\"resp_code\":\""+zteResponse.getResp_code()+"\",\"resp_msg\":\""+zteResponse.getError_msg()+"\"}";
		} catch (Exception e) {
			log.info(interfaceName + e);
			//return getResponseInfo(serialNo, EcsOrderConsts.JKZF_VALID_FAIL_CODE, "处理请求异常：" + e.getMessage(), "");
			return "{\"serial_no\":\"11111\",\"time\":\""+new Date()+"\",\"resp_code\":\"1\",\"resp_msg\":\""+e.getMessage()+"\"}";
		}*/
	}
	
	/**
	 * 具体业务处理
	 * @param opidSynProtocol
	 * @return
	 */
	public RefreshGoodsCacheResp refresh(RefreshGoodsCacheReq req)throws Exception  {
		RefreshGoodsCacheResp zteResponse = new RefreshGoodsCacheResp();
		zteResponse.setResp_code("1");//默认失败
		try {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			zteResponse = client.execute(req, RefreshGoodsCacheResp.class);//业务处理
		} catch (Exception e) {
			log.info(interfaceName+"接口处理错误描述:"+e.getMessage());
			zteResponse.setResp_msg("接口处理错误");
		}
		return zteResponse;
	}
}
