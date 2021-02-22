package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.iservice.IOrderCtnService;
import zte.params.orderctn.req.ChannelConvertQryCtnReq;
import zte.params.orderctn.req.CtnStartWorkflowReq;
import zte.params.orderctn.req.CtnWorkflowMatchReq;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderNiceInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrder;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderContactInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderCustInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderPhoneInfo;
import com.ztesoft.net.server.jsonserver.intentpojo.IntentOrderResp;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.sqls.SqlUtil;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;

/**
 * wssmall.intent.order.create
 * 
 * @author 宋琪
 *
 * @date 2018年4月10日
 */
public class IntentOrderCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(IntentOrderCreateServlet.class);
	
	private IOrderCtnService orderCtnService;
	
	private IOrderCtnService getOrderCtnService() throws Exception{
		if(null == this.orderCtnService) {
			this.orderCtnService = SpringContextHolder.getBean("orderCtnService");
		}
		
		return this.orderCtnService;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		IntentOrderResp resp = null;
		IntentOrder requ = null;
		try {
			// 获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[IntentOrderCreateServlet] 请求报文-request:" + requJsonStr);
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			// 请求入参
			JSONObject orderResultJson = requJson.getJSONObject("intent_order_req");
			// 解析请求参数
			requ = jsonToIntentOrder(orderResultJson.toString());
			// ZteClient client =
			// ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			// resp = client.execute(requ, IntentOrderResp.class);
			resp = intentOrderCreate(requ);
		} catch (Exception e) {
			resp = new IntentOrderResp();
			resp.setResp_code("1");
			resp.setResp_msg(e.getMessage());
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("intent_order_resp", resp);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(map).toString();
		// 返回接口调用结果
		logger.info("[IntentOrderCreateServlet] 响应报文-response:" + respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IntentOrderResp intentOrderCreate(IntentOrder req) {
		IntentOrderResp rsp = new IntentOrderResp();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql1 = "select field_value,other_field_value from es_dc_public_dict_relation where stype ='county'";
		final List<Map<String, String>> list1 = baseDaoSupport.queryForList(sql1);
		try {
			String msg = intentOrderCheck(req);
			CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String intent_kg = cacheUtil.getConfigInfo("channel_convert_kg_intent");
			if("1".equals(intent_kg) && StringUtil.isEmpty(msg)){
			    String sql2 ="select distinct t.cat_id from es_goods t where t.goods_id='"+req.getGoods_info().get(0).getProd_code()+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
			    String cat_id= baseDaoSupport.queryForString(sql2);
			    String cbssGoodsCat = cacheUtil.getConfigInfo("CBSS_GOODS_CAT_"+req.getSource_system_type());
			    if(!StringUtil.isEmpty(cat_id)&&!StringUtil.isEmpty(cbssGoodsCat) && cbssGoodsCat.contains(cat_id)){
			        msg = intentConvertCbss(req);
			    }
			    String bssGoodsCat = cacheUtil.getConfigInfo("BSS_GOODS_CAT_"+req.getSource_system_type());
			    if(!StringUtil.isEmpty(cat_id)&&!StringUtil.isEmpty(bssGoodsCat) && bssGoodsCat.contains(cat_id)){
			        msg= intentConverBss(req);
			    }
			}
			if (StringUtils.isEmpty(msg)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("source_from", ManagerUtils.getSourceFrom());
				map.put("intent_order_id", req.getIntent_order_id());
				map.put("source_system", req.getSource_system());
				map.put("source_system_type", req.getSource_system_type());
				map.put("order_province_code", req.getOrder_province_code());
				map.put("order_city_code", req.getOrder_city_code());
				map.put("order_county_code", req.getOrder_county_code());
				map.put("order_type", req.getOrder_type());
				if(!StringUtils.isEmpty(req.getOrder_county_code())){
					//行政县分转营业县分-如果developer_info下传county_id就覆盖
					for(Map<String, String> county:list1){
						if(county.get("field_value").equals(req.getOrder_county_code())){
							map.put("county_id", county.get("other_field_value"));
							break;
						}
					}
				}
				map.put("remark", req.getRemark());
				String prex = cacheUtil.getConfigInfo("ORDER_PREX"); // 订单Id前缀
				String order_id = prex + baseDaoSupport.getSequences("s_es_order");
				map.put("order_id", order_id);
				List<IntentOrderGoodsInfo> goods_info = req.getGoods_info();
				for (IntentOrderGoodsInfo goods : goods_info) {
					map.put("goods_id", goods.getProd_code());
					map.put("share_goods_id", goods.getProd_code());
	                map.put("goods_name", goods.getProd_name());
					map.put("prod_offer_code", goods.getProd_offer_code());
					map.put("prod_offer_name", goods.getProd_offer_name());
					map.put("goods_num", goods.getGoods_num());
					map.put("offer_price", goods.getOffer_price());
					map.put("real_offer_price", goods.getReal_offer_price());
				}
				IntentOrderDeveloperInfo developer_info = req.getDeveloper_info();
				if (null != developer_info) {
					map.put("develop_point_code", developer_info.getDevelop_point_code());
					map.put("develop_point_name", developer_info.getDevelop_point_name());
					map.put("develop_code", developer_info.getDevelop_code());
					map.put("develop_name", developer_info.getDevelop_name());
					map.put("referee_num", developer_info.getReferee_num());
					map.put("referee_name", developer_info.getReferee_name());
					if(!StringUtils.isEmpty(developer_info.getCounty_id())){
						map.put("county_id", developer_info.getCounty_id());
					}
					map.put("deal_office_type", developer_info.getDeal_office_type());
					map.put("channelType", developer_info.getChannelType());
					map.put("deal_office_id", developer_info.getDeal_office_id());
					map.put("deal_operator", developer_info.getDeal_operator());
					map.put("deal_operator_name", developer_info.getDeal_operator_name());
					map.put("deal_operator_num", developer_info.getDeal_operator_num());
					map.put("develop_phone", developer_info.getDevelop_phone());
				}
				// IntentOrderCustInfo cust_info = req.getCust_info();
				// if (null != cust_info) {
				// map.put("customer_phone", cust_info.getCust_tel());
				// map.put("customer_name", cust_info.getCustomer_name());
				// }
				IntentOrderContactInfo contact_info = req.getContact_info();
				if (null != contact_info) {
					map.put("ship_tel", contact_info.getShip_tel());
					map.put("ship_name", contact_info.getShip_name());
					map.put("ship_addr", contact_info.getShip_addr());
					map.put("ship_tel2", contact_info.getShip_tel2());
				}
				IntentOrderCustInfo cust_info = req.getCust_info();
				if(null!=cust_info){
					map.put("is_real_name", cust_info.getIs_real_name());
					map.put("customer_name", cust_info.getCustomer_name());
					map.put("cert_type", cust_info.getCert_type());
					map.put("cert_num", cust_info.getCert_num());
					map.put("cert_addr", cust_info.getCert_addr());
					map.put("cert_nation", cust_info.getCert_nation());
					map.put("cert_sex", cust_info.getCert_sex());
					map.put("cust_birthday", cust_info.getCust_birthday());
					map.put("cert_issuedat", cust_info.getCert_issuedat());
					map.put("cert_expire_date", cust_info.getCert_expire_date());
					map.put("cert_effected_date", cust_info.getCert_effected_date());
					map.put("cust_tel", cust_info.getCust_tel());
					map.put("customer_type", cust_info.getCustomer_type());
					map.put("cust_id", cust_info.getCust_id());
					map.put("group_code", cust_info.getGroup_code());
					map.put("req_swift_num", cust_info.getReq_swift_num());
					map.put("check_type", cust_info.getCheck_type());
					map.put("cert_num2", cust_info.getCert_num2());
					if (cust_info.getChip_illumination() != null) {
						if (cust_info.getChip_illumination().length() >20000) {
							rsp.setResp_code("1");
							rsp.setResp_msg("芯片照太大");
							return rsp;
						} else {
							map.put("chip_illumination", cust_info.getChip_illumination());
						}
					}
					
				}
				IntentOrderPhoneInfo phone_info = req.getPhone_info();
				if(null!=phone_info){
					map.put("acc_nbr", phone_info.getAcc_nbr());
					map.put("contract_month", phone_info.getContract_month());
					map.put("mainNumber", phone_info.getMainNumber());//添加主卡号码  副卡开户必传
					InterGroupBusiOrderNiceInfo nice_info = phone_info.getNice_info();
					if(null!=nice_info){
						map.put("lhscheme_id", nice_info.getLhscheme_id());
						map.put("pre_fee", nice_info.getPre_fee());
						map.put("advancePay", nice_info.getAdvancePay());
						map.put("classId", nice_info.getClassId());
						map.put("lowCostPro", nice_info.getLowCostPro());
						map.put("timeDurPro", nice_info.getTimeDurPro());
					}
				}
				map.put("market_user_id", req.getMarket_user_id());
				map.put("Seed_user_id", req.getSeed_user_id());
				map.put("share_svc_num", req.getShare_svc_num());
				map.put("is_no_modify", req.getIs_no_modify());
				map.put("create_time", new Date());
				map.put("status", "01");
				map.put("req", JSONObject.fromObject(req).toString());
				map.put("activity_name", req.getActivity_name());
				map.put("top_share_userid", req.getTop_share_userid());
				map.put("top_share_num", req.getTop_share_num());
				map.put("offer_eff_type", req.getOffer_eff_type());
				map.put("top_seed_professional_line", req.getTop_seed_professional_line());
				map.put("top_seed_type", req.getTop_seed_type());
				map.put("top_seed_group_id", req.getTop_seed_group_id());
				
				
				//自定义流程匹配
				ES_WORK_CUSTOM_CFG cfg = this.doWorkflowMatch(map);
				
				if(cfg == null){
					//未匹配到自定义流程，按原来逻辑发送短信，并沉淀处理工号
					this.sendSms(req, map, order_id);
				}
				
				baseDaoSupport.insert("es_order_intent", map);
				
				if(cfg != null){
					//启动工作流
					this.startWorkflow(map, cfg);
				}
				
				rsp.setResp_code("0");
				rsp.setResp_msg("成功");
				rsp.setOrder_id(order_id);
			} else {
				rsp.setResp_code("1");
				rsp.setResp_msg(msg);
				rsp.setOrder_id("");
			}
		} catch (Exception e) {
			rsp.setResp_code("1");
			rsp.setResp_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}
	/**
	 * bss转换
	 */
	private String intentConverBss(IntentOrder req) {
	   // TODO Auto-generated method stub
        StringBuffer stringBuffer = new StringBuffer();
        String develop_code=req.getDeveloper_info().getDevelop_code();//发展人
        String develop_point_code=req.getDeveloper_info().getDevelop_point_code();//发展点
        if(MallUtils.isEmpty(develop_code) && MallUtils.isEmpty(develop_point_code)){ //第四种  不进行处理
            return null;
        }
        if(!MallUtils.isEmpty(develop_point_code)){//b 发展点  第一种可能
            if(MallUtils.isEmpty(develop_code)){//b  只有发展点 没有发展人
                ChannelConvertQrySubResp resp =  channelConvert(develop_point_code);//传发展点
                if("00000".equals(resp.getCode())){
                	if (!MallUtils.isEmpty(resp.getRespJson().getSf_channel_id())) {
                		 req.getDeveloper_info().setDevelop_point_code(resp.getRespJson().getSf_channel_id());
                         req.getDeveloper_info().setDevelop_point_name(resp.getRespJson().getSf_channel_name());
                        return null;
					} else {
						stringBuffer.append("根据收单发展点查不到省分发展点");
	                    return stringBuffer.toString();
					}
                	
                }else if("00502".equals(resp.getCode())){
                	stringBuffer.append("无发展点编码信息");
                    return stringBuffer.toString();
                }else{
                	 stringBuffer.append(resp.getMsg());
                     return stringBuffer.toString();
                }
            }else{//转换值  有发展点 有发展人 规则2
                ChannelConvertQrySubResp resp =  channelConvert(develop_code);//若有则进行传发展人
                if("00000".equals(resp.getCode())){
                    if(MallUtils.isEmpty(resp.getRespJson().getSf_developer())){//如果发展人为空拒绝收单
                        stringBuffer.append("bss发展点传值错误");
                        return stringBuffer.toString();
                    }else{
                        if(!develop_point_code.equals(resp.getRespJson().getSf_channel_id()) && !develop_point_code.equals(resp.getRespJson().getZb_channel_id())){
                            stringBuffer.append("bss发展点和bss发展人关系不一致");
                            return stringBuffer.toString();
                        }else{
                            req.getDeveloper_info().setDevelop_point_code(resp.getRespJson().getSf_channel_id());
                            req.getDeveloper_info().setDevelop_point_name(resp.getRespJson().getSf_channel_name());
                            req.getDeveloper_info().setDevelop_code(resp.getRespJson().getSf_developer());
                            req.getDeveloper_info().setDevelop_name(resp.getRespJson().getSf_developer_name());
                            return null;
                        }
                    }
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
            }
        }else{
            if(!MallUtils.isEmpty(develop_code)){
                ChannelConvertQrySubResp resp =  channelConvert(develop_code);//若有则进行传发展人
                if("00000".equals(resp.getCode())){
                    if(!MallUtils.isEmpty(resp.getRespJson().getSf_developer())){
                        req.getDeveloper_info().setDevelop_code(resp.getRespJson().getSf_developer());
                        req.getDeveloper_info().setDevelop_name(resp.getRespJson().getSf_developer_name());
                        req.getDeveloper_info().setDevelop_point_code(resp.getRespJson().getSf_channel_id());
                        req.getDeveloper_info().setDevelop_point_name(resp.getRespJson().getSf_channel_name());
                        return null;
                    }else{
                        stringBuffer.append("bss发展人编码传值错误");
                        return stringBuffer.toString();
                    }
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
            }
        }
        return null;
    }
	/**
	 * cbss 转换@return
	 */
    private String intentConvertCbss(IntentOrder req) {
      //针对于CB业务需求  是否可以按照字段进行判断
        StringBuffer stringBuffer = new StringBuffer();
        String develop_code=req.getDeveloper_info().getDevelop_code();//发展人
        String develop_point_code=req.getDeveloper_info().getDevelop_point_code();//发展点
        if(MallUtils.isEmpty(develop_point_code) && MallUtils.isEmpty(develop_code)){ //第四种  不进行处理
            return null;
        }
        if(!MallUtils.isEmpty(develop_point_code)){//cb 发展点  第一种可能
            if(MallUtils.isEmpty(develop_code)){//cb发展人  有发展点无发展人拒绝收单
                stringBuffer.append("CBSS发展点和发展人传值不符合业务要求");
                return stringBuffer.toString();
            }else{//转换值  有发展点 有发展人
                ChannelConvertQrySubResp resp =  channelConvert(develop_code);//若有则进行传发展人
                Boolean flag = false;
                if("00000".equals(resp.getCode())){
                    flag = true;
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
               if(flag && MallUtils.isEmpty(resp.getRespJson().getZb_developer())){
                    stringBuffer.append("CBSS发展人传值错误");
                    return stringBuffer.toString();
                }else if(flag && !MallUtils.isEmpty(resp.getRespJson().getZb_developer())){//如果接口返回发展人编码和收单不一致  
                    if(!develop_point_code.equals(resp.getRespJson().getSf_channel_id()) && !develop_point_code.equals(resp.getRespJson().getZb_channel_id())){
                        stringBuffer.append("CBSS发展人和CBSS发展点关系不一致");
                        return stringBuffer.toString();
                    }else {
                        req.getDeveloper_info().setDevelop_point_code(resp.getRespJson().getZb_channel_id());
                        req.getDeveloper_info().setDevelop_point_name(resp.getRespJson().getSf_channel_name());
                        req.getDeveloper_info().setDevelop_code(resp.getRespJson().getZb_developer());
                        req.getDeveloper_info().setDevelop_name(resp.getRespJson().getSf_developer_name());
                        return null;
                    }
                } 
            }
        }else{
            if(!MallUtils.isEmpty(develop_code)){//转换按照规则3 无发展点  有发展人信息
                //DOTO 请求转换进行判断
                ChannelConvertQrySubResp resp =  channelConvert(develop_code);//若有则进行传发展人
                Boolean flag = false;
                if("00000".equals(resp.getCode())){
                    flag = true;
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
                if(flag){
                    if(MallUtils.isEmpty(resp.getRespJson().getZb_developer())){
                        stringBuffer.append("CBSS发展人传值错误");
                        return stringBuffer.toString();
                    }else{
                        req.getDeveloper_info().setDevelop_point_code(resp.getRespJson().getZb_channel_id());
                        req.getDeveloper_info().setDevelop_point_name(resp.getRespJson().getSf_channel_name());
                        req.getDeveloper_info().setDevelop_code(resp.getRespJson().getZb_developer());
                        req.getDeveloper_info().setDevelop_name(resp.getRespJson().getSf_developer_name());
                        return null;
                     }
                }
            }
        }
        return null;
    }
    private ChannelConvertQrySubResp channelConvert(String code){
        ChannelConvertQrySubResp resp = new ChannelConvertQrySubResp();
        ChannelConvertQryCtnReq req = new ChannelConvertQryCtnReq();
        try{
            req.setOp_type("21");
            req.setOp_value(code);
            ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
            resp = client.execute(req, ChannelConvertQrySubResp.class);
            return resp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @SuppressWarnings({ "rawtypes" })
	private String intentOrderCheck(IntentOrder req) {
		if (StringUtils.isEmpty(req.getSource_system())) {
			return "缺少source_system";
		}
		if (StringUtils.isEmpty(req.getSource_system_type())) {
			return "缺少source_system_type";
		}
		if (StringUtils.isEmpty(req.getIntent_order_id())) {
			return "缺少intent_order_id";
		}
		List<IntentOrderGoodsInfo> goods_info = req.getGoods_info();
		if (null == goods_info || goods_info.size() < 1) {
			return "缺少goods_info";
		}
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		for (IntentOrderGoodsInfo goods : goods_info) {
			if (StringUtils.isEmpty(goods.getProd_code())) {
				return "缺少Prod_code";
			}
			String sql_goods = "select name from es_goods where goods_id='"+goods.getProd_code()+"'";//根据传入的编号查询出商品名称
            String goods_name= baseDaoSupport.queryForString(sql_goods);
            if(StringUtil.isEmpty(goods_name)){
                return "商品编码不存在";
            }else{
                goods.setProd_name(goods_name);
            }
			if (StringUtils.isEmpty(goods.getProd_name())) {
				return "缺少Prod_name";
			}
			if (StringUtils.isEmpty(goods.getProd_offer_code())) {
				return "缺少Prod_offer_code";
			}
			if (StringUtils.isEmpty(goods.getProd_offer_name())) {
				return "缺少Prod_offer_name";
			}
			//sgf
	        String goodsIdAll = cacheUtil.getConfigInfo("goodIdInfo");//配置商品小类  b的副卡
	        String sql = "select cat_id from es_goods t where t.goods_id='" + goods.getProd_code() + "' and source_from='"+ManagerUtils.getSourceFrom()+"'";
	        String cat_id= baseDaoSupport.queryForString(sql);
	        if(!StringUtils.isEmpty(cat_id)&&goodsIdAll.contains(cat_id) ){//得到的是商品  根据商品查询出商品小类  再根据商品小类判断
	            IntentOrderPhoneInfo intentOrderPhoneInfo = req.getPhone_info();
	            if(null!=intentOrderPhoneInfo){
	                String mainNumber = req.getPhone_info().getMainNumber()+"";
	                if(StringUtils.isEmpty(mainNumber)){
	                    return "缺少mainNumber";
	                }else{
	                    if(mainNumber.length()>11){
	                        return "mainNumber不符合11位长度";
	                    }
	                }
	            }else{
	                return "缺少phone_info节点";
	            }
	        }
		}
	    IntentOrderCustInfo cust_info = req.getCust_info();
	    if(cust_info != null){
	        if("GAT".equals(cust_info.getCert_type())){
	            if(StringUtil.isEmpty(cust_info.getCert_num2())){
	                return "缺少cert_num2";
	            }
	        }
	    }
		String sql = "select * from es_order_intent t where intent_order_id='" + req.getIntent_order_id() + "' and source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list = baseDaoSupport.queryForList(sql);
		if (list.size() > 0) {
			return "intent_order_id重复"+req.getIntent_order_id();
		}
		// IntentOrderDeveloperInfo developer_info= req.getDeveloper_info();
		// IntentOrderCustInfo cust_info= req.getCust_info();
		// IntentOrderContactInfo contact_info= req.getContact_info();
		return "";
	}

	@SuppressWarnings("rawtypes")
	public IntentOrder jsonToIntentOrder(String json) throws Exception {
		// 替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
		// json中[]表示一个List集合，需要定义集合对象来接收
		// 因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", IntentOrderGoodsInfo.class);
		classMap.put("developer_info", IntentOrderDeveloperInfo.class);
		classMap.put("cust_info", IntentOrderCustInfo.class);
		classMap.put("contact_info", IntentOrderContactInfo.class);
		classMap.put("phone_info", IntentOrderPhoneInfo.class);

		JsonConfig config = new JsonConfig();
		// 忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(IntentOrder.class);
		// 生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		IntentOrder intentOrder = (IntentOrder) JSONObject.toBean(jsonObject, config);
		return intentOrder;
	}

	/**
	 * 替换空格、回车、换行、制表位为空字符
	 * 
	 * @param str
	 * @return
	 */
	public String strReplaceBlank(String str) {
		Pattern pattern = Pattern.compile("\r|\t|\n");
		Matcher m = pattern.matcher(str);
		return m.replaceAll("");
	}

	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
		String requString = "";
		try {
			// 获取流
			in = request.getInputStream();
			// 转为string
			requString = IOUtils.toString(in, "utf-8");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {

			}
		}
		return requString;
	}

	@SuppressWarnings("rawtypes")
	private ES_WORK_CUSTOM_CFG doWorkflowMatch(Map map) throws Exception{
		String order_id = SqlUtil.getStrValue(map.get("order_id"));
		String order_city_code = SqlUtil.getStrValue(map.get("order_city_code"));
		String order_county_code = SqlUtil.getStrValue(map.get("order_county_code"));
		String source_system_type = SqlUtil.getStrValue(map.get("source_system_type"));
		String goods_id = SqlUtil.getStrValue(map.get("goods_id"));
		
		CtnWorkflowMatchReq matchReq = new CtnWorkflowMatchReq();
		ES_ORDER_INTENT orderIntent = new ES_ORDER_INTENT();
		orderIntent.setOrder_id(order_id);
		orderIntent.setOrder_city_code(order_city_code);
		orderIntent.setOrder_county_code(order_county_code);
		orderIntent.setSource_system_type(source_system_type);
		orderIntent.setGoods_id(goods_id);
		
		matchReq.setOrderIntent(orderIntent);
		matchReq.setCfg_type("intent");
		
		WorkflowMatchRsp matchRsp = this.getOrderCtnService().doWorkflowMatch(matchReq);
		
		String error_code = matchRsp.getError_code();
		if("-1".equals(error_code)){
			//调用DUBBO异常
			throw new Exception(matchRsp.getError_msg());
		}else if("0".equals(error_code)){
		 	return null;
		}else{
			return matchRsp.getCfg();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void startWorkflow(Map map,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		CtnStartWorkflowReq flowReq = new CtnStartWorkflowReq();
		
		String order_id = SqlUtil.getStrValue(map.get("order_id"));
		String order_city_code = SqlUtil.getStrValue(map.get("order_city_code"));
		String order_county_code = SqlUtil.getStrValue(map.get("order_county_code"));
		String source_system_type = SqlUtil.getStrValue(map.get("source_system_type"));
		String goods_id = SqlUtil.getStrValue(map.get("goods_id"));
		
		ES_ORDER_INTENT orderIntent = new ES_ORDER_INTENT();
		orderIntent.setOrder_id(order_id);
		orderIntent.setOrder_city_code(order_city_code);
		orderIntent.setOrder_county_code(order_county_code);
		orderIntent.setSource_system_type(source_system_type);
		orderIntent.setGoods_id(goods_id);
		
		flowReq.setOrderIntent(orderIntent);
		flowReq.setCfg(cfg);
        
        StartWorkflowRsp flowRsp = this.getOrderCtnService().startWorkflow(flowReq);
        
        String workflow_id = flowRsp.getWorkflow_id();
        
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        if("-1".equals(flowRsp.getError_code())){
        	//调用DUBBO异常
        	//删除意向单
        	baseDaoSupport.save("es_order_intent", "delete", orderIntent, new String[]{"order_id"});
        	throw new Exception(flowRsp.getError_msg());
        }else if("-1".equals(workflow_id)){
        	//启动自定义流程异常
        	//删除意向单
        	baseDaoSupport.save("es_order_intent", "delete", orderIntent, new String[]{"order_id"});
        	throw new Exception(flowRsp.getError_msg());
        }
	}
	
	/**
	 * 标记处理人并发送短信
	 * @param req
	 * @param map
	 * @param order_id
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void sendSms(IntentOrder req,Map map,String order_id) throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		String allot_status = "0";
		String district_code = req.getOrder_county_code();
		List<Map> list = new ArrayList<Map>();
		
		StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='DC_COUNTY_USERS_"+req.getSource_system_type()+"' ");
		List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
		if (dc_sql_list != null && dc_sql_list.size() > 0) {
			String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
			list = baseDaoSupport.queryForList(dc_sql);
		}
		
		String gonghao  = "";
		//匹配县分工号
		for (int i = 0; i < list.size(); i++) {
			String value = Const.getStrValue(list.get(i), "value");
			String value_desc = Const.getStrValue(list.get(i), "value_desc");
			if (StringUtil.equals(value, district_code)) {
				gonghao = value_desc;
				break;
			}
		}
		//匹配地市工号
		if (StringUtils.isEmpty(gonghao) && !StringUtils.isEmpty(req.getOrder_city_code())) {
			sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='DC_CITY_USERS_"
					+ req.getSource_system_type() + "' ");
			dc_sql_list = baseDaoSupport.queryForList(sql.toString());
			if (dc_sql_list != null && dc_sql_list.size() > 0) {
				String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
				list = baseDaoSupport.queryForList(dc_sql);
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						String value = Const.getStrValue(list.get(i), "value");
						String value_desc = Const.getStrValue(list.get(i), "value_desc");
						if (StringUtil.equals(value, req.getOrder_city_code())) {
							gonghao = value_desc;
							break;
						}
					}
				}
			}
		}
		//匹配省工号
		if(StringUtils.isEmpty(gonghao)){
			gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_"+req.getSource_system_type());
			allot_status = "1";
		}
		
		// 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
		//工号分配成功
		List<Map> gonghaoInfos = java.util.Collections.emptyList();
		if(StringUtils.equals(allot_status, "0")) {
	        String yxdswitch = cacheUtil.getConfigInfo("yxdSemss");//配置商品小类  b的副卡
	        if("0".equals(yxdswitch)){
	            StringBuilder sbBuilder = new StringBuilder();
	            sbBuilder.append(" select ea.userid, ea.username, ea.realname, ea.phone_num, ea.dep_name as org_name ");
	            sbBuilder.append("  from es_adminuser ea  ");
	            sbBuilder.append(" where ea.userid in  ");
	            sbBuilder.append("      (select eur.userid from es_user_role eur where eur.roleid in ");
	            sbBuilder.append("            (select era.roleid from es_role_auth era ");
	            sbBuilder.append("              where era.authid in (select erd.id  from es_role_data erd where erd.ord_receive_user like '").append(gonghao).append(",%') ");
	            sbBuilder.append("                       ) ");
	            sbBuilder.append("      ) ");
	            sbBuilder.append(" and ea.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
	            sbBuilder.append(" and ea.state = '1' ");
	            gonghaoInfos = baseDaoSupport.queryForList(sbBuilder.toString());
	        }
		}else { //分配失败，对省中台发短信
			String qrySztSql = "select ea.userid, ea.username,ea.realname,ea.phone_num,ea.dep_name as org_name " +
							   " from es_adminuser ea "+
							   " where ea.userid in "+
							   " (select eur.userid "+
							   " from es_user_role eur "+
							   " where eur.roleid in "+
							   " (select era.roleid "+
							   " from es_role_auth era "+
							   " where era.authid = "+
							   " (select erd.id "+
							   " from es_role_data erd "+
							   " where erd.ord_receive_user like '"+gonghao+",%'))) and ea.source_from = '"+ManagerUtils.getSourceFrom()+"' and ea.state='1' and ea.sms_receive='1' ";
			gonghaoInfos = baseDaoSupport.queryForList(qrySztSql);
		}
		
		if(gonghaoInfos != null && gonghaoInfos.size() > 0) {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			List<ES_WORK_SMS_SEND> listPojo = new ArrayList<ES_WORK_SMS_SEND>();
			for (Map gonghaoMap : gonghaoInfos) {
					String org_name = MapUtils.getString(gonghaoMap, "org_name");
					String userId = MapUtils.getString(gonghaoMap, "userid");
					//您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
					String smsContent = "您的【"+org_name+"订单池】收到待处理订单"+order_id+"（订单编号），可用工号【"+userId+"】登录订单中心领取处理。";
					ES_WORK_SMS_SEND smsSendPojo = new ES_WORK_SMS_SEND();
					smsSendPojo.setBill_num("10010");
					smsSendPojo.setService_num(MapUtils.getString(gonghaoMap, "phone_num"));// 接受号码--省内联通号码
					smsSendPojo.setSms_data(smsContent);
					smsSendPojo.setOrder_id(order_id);
					listPojo.add(smsSendPojo);
			}
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			smsSendReq.setListpojo(listPojo);
			client.execute(smsSendReq, AopSmsSendResp.class);
		}
		
		String sql_user="select userid,realname from es_adminuser where userid='"+gonghao+"'";
		List<Map<String,String>> user_list = baseDaoSupport.queryForList(sql_user);
		if(user_list.size()>0){
			map.put("lock_name", user_list.get(0).get("realname"));
		}
		map.put("lock_id", gonghao);
		map.put("allot_status", allot_status);
	}
}
