package com.ztesoft.net.server.jsonserver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderStandardPushResp;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.DBUtils;
import com.ztesoft.net.service.IOrderServiceLocal;

/**
 * @author lin.jun  订单信息同步接口
 * @version 
 */
public class ExecutionFactory {	
	private static DBUtils dbUtils = null;
	private static Map orderMap = new HashMap();
	private static ICommClientBO commClientBO;
	private static ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO = SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	public String getCityname(String cityId) throws FrameException, SQLException{
		String m = getICommClientBO().queryForString("select a.region_name from es_common_region a where a.region_code = '"+cityId+"'");

		return m;
	}
	
	public String getProviceId(String CountryId) throws FrameException, SQLException{
		String m = getICommClientBO().queryForString("select a.parent_region_id from es_common_region a where a.region_code = '"+CountryId+"'");
		return m;
	}
	
	public String getProvicename(String CountryId) throws FrameException, SQLException{
		String m = getICommClientBO().queryForString("select ss.region_name from es_common_region ss where ss.region_id = (select a.parent_region_id from es_common_region a where a.region_code = '"+CountryId+"')");
		return m;
	}
	
	private static Logger logger = Logger.getLogger(ExecutionFactory.class);
	
	// @Resource
//	protected static IGoodsService goodServices;
	protected static IOrderServiceLocal goodServices;
	protected static IOutterECSTmplManager outterECSTmplManager;
	
	public static void packageGoodsParam(OuterItem o, Map<String, String> param) {

		o.setPhone_deposit(StringUtil.isEmpty(param.get("deposit_fee")) ? "0"
				: param.get("deposit_fee"));// 活动预存款 单位元

		String product_id = param.get("product_id");// i.getOuterIid();//String.valueOf(i.getNumIid());
		// product_id = "1";
		logger.info("product_id:=" + product_id);
		o.setProduct_id(product_id);// 产品ID
		o.setGoods_id(param.get("goods_id"));
		/**
		 * 规格数所
		 */
		o.setAct_protper(StringUtil.isEmpty(param.get("package_limit")) ? "0"
				: param.get("package_limit")); // 合约期限 12月、18月、24月、36月、48月
		/**
		 * 规格数所
		 */
		o.setAtive_type(StringUtil.isEmpty(param.get("ative_type")) ? ""
				: param.get("ative_type")); // 字典：5购机送费、4存费送机、3存费送费 --合约类型
		/**
		 * 规格数所
		 */
		o.setBrand_name(param.get("brand_name")); // 品牌
		/**
		 * 规格数所
		 */
		o.setBrand_number(param.get("brand_number")); // 品牌编码
		/**
		 * 规格数所
		 */
		o.setColor_code(StringUtil.isEmpty(param.get("color_code")) ? ""
				: param.get("color_code"));// 颜色编码
		o.setColor_name(StringUtil.isEmpty(param.get("color_name")) ? ""
				: param.get("color_name"));
		/**
		 * 规格数所
		 */
		o.setIs_iphone_plan(param.get("is_iphone_plan"));// 是否iphone套餐 字典：0否，1是
															// 没有
		/**
		 * 规格数所
		 */
		o.setIs_liang(param.get("is_liang"));// 是否靓号 字典：0否，1是 --待确认
		/**
		 * 规格数所
		 */
		o.setIs_loves_phone(StringUtil.isEmpty(param.get("is_loves_phone")) ? "0"
				: param.get("is_loves_phone")); // 是否情侣号码 字典：0否，1是 --默认0
		/**
		 * 规格数所
		 */
		o.setIs_stock_phone(StringUtil.isEmpty(param.get("is_stock_phone")) ? "0"
				: param.get("is_stock_phone"));// 是否库存机 字典：0否，1是 --终端属性
		/**
		 * 规格数所
		 */
		o.setIsgifts(String.valueOf(param.get("isgifts")));// 是否赠品 字典：0否，1是
		/**
		 * 规格数所
		 */
		o.setModel_code(param.get("model_code"));// 机型编码 　---如果要送总部开户，就要对应总部的编码
													// 如果不送总部，就为空。待局方确认
		/**
		 * 规格数所
		 */
		o.setModel_name(param.get("model_name"));// 机型名称

		o.setOut_plan_id_bss(param.get("out_plan_id_bss"));// BSS套餐编码 BSS套餐编码
															// --待局方确认
		o.setOut_plan_id_ess(param.get("out_plan_id_ess"));// 总部套餐编码 总部套餐编码
															// ---如果要送总部开户，就要对应总部的编码
															// 如果不送总部，就为空。待局方确认

		/**
		 * 规格数所
		 */
		o.setPlan_title(param.get("goods_name"));// 套餐名称wt.getPlan_title();
													// 　商品名称
													// param.get("goods_name")

		o.setPro_name(param.get("goods_name"));// 产品名称 　商品名称

		/**
		 * 规格数所
		 */
		o.setPro_type(param.get("product_type"));// 产品类型
		o.setProduct_net(param.get("product_net"));// 产品网别 　2G、3G --填空
		/**
		 * 规格数所
		 */
		o.setSpecificatio_nname(StringUtil.isEmpty(param
				.get("specification_name")) ? "" : param
				.get("specification_name"));// 型号名称
		/**
		 * 规格数所
		 */
		o.setSpecification_code(StringUtil.isEmpty(param
				.get("specification_code")) ? "" : param
				.get("specification_code"));// 型号编码 　---如果要送总部开户，就要对应总部的编码
											// 如果不送总部，就为空。待局方确认
		o.setType_id(param.get("type_id"));
	}
	
	
	public static Map<String,String> getGoodsInfo(String sku){
//		List pList = new ArrayList();
		String sql = "select g.goods_id, g.name goods_name,  g.type_id, g.cat_id product_type, g.params, p.price goods_price, m.model_code specification_code, m.model_name specification_name, b.name brand_name, b.brand_code brand_number, m.machine_code model_code, m.machine_name model_name, s.spec_value_id color_code, v.spec_value color_name, (case when (select count(*) from es_pmt_goods pmt where pmt.goods_id = g.goods_id) > 0 then 1 else 0 end) isgifts, p.product_id from es_goods g left join es_brand b on g.brand_id = b.brand_id left join es_brand_model m on b.brand_code = m.brand_code left join es_product p on g.goods_id = p.goods_id left join es_goods_spec s on s.product_id = p.product_id and s.spec_id = 1 left join es_spec_values v on s.spec_value_id = v.spec_value_id where p.type = 'goods' and g.source_from = '"+ManagerUtils.getSourceFrom()+"' and g.sku = '"+sku+"'";
		
		Map<String,String> goods = new HashMap<String,String>();
		try{
			List<Map> datalist = getICommClientBO().queryForList(sql);
			Map data = null;
			if(datalist!=null && datalist.size()>0)
				data = datalist.get(0);
			
			
			if(data==null)
				return goods;
			
			//获取合约类型
			String active_type = getICommClientBO().queryForString("select (case when b.cat_id = '690301000' then 5 when b.cat_id = '690302000' then 4 when b.cat_id = '690303000' then 3 end) ative_type from es_goods_rel a, es_goods b where a.a_goods_id = '"+data.get("goods_id").toString()+"' and b.type_id = 10001 and b.type = 'product'  and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.z_goods_id = b.goods_id" );
			data.put("ative_type", active_type);
			
			//获取型号信息
			Map<String,String> model = getICommClientBO().queryForMap("  select b.model_code as specification_code, (select c.model_name from es_brand_model c where c.model_code = b.model_code) as specification_name from es_goods_rel a, es_goods b where a.a_goods_id = '"+data.get("goods_id").toString()+"' and b.type_id = 10000 and b.type = 'product' and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.z_goods_id = b.goods_id ");
			if(!model.isEmpty()){
				data.put("specification_code", model.get("specification_code"));
				data.put("specification_name", model.get("specification_name"));
			}
			//是否iphone套餐
			String is_iphone_plan = getICommClientBO().queryForString("  select (case    when b.cat_id = '690102000' then  '1'   else '0'  end) is_iphone_plan  from es_goods_rel a, es_goods b    where a.a_goods_id = '"+data.get("goods_id").toString()+"'   and b.type_id = 10002   and b.type = 'product' and b.source_from='"+ManagerUtils.getSourceFrom()+"'   and a.z_goods_id = b.goods_id  ");
			data.put("is_iphone_plan", is_iphone_plan);
			
			//获取颜色
			Map<String,String> color = getICommClientBO().queryForMap(" select color color_code, (select pname from es_dc_public t where p.color = t.pkey and stype = 10002) color_name from es_goods g, es_product p, es_goods_rel r where g.goods_id = p.goods_id and p.product_id = r.product_id and g.source_from = '"+ManagerUtils.getSourceFrom()+"' and p.color is not null and a_goods_id = '"+data.get("goods_id").toString()+"'");
			if(!color.isEmpty()){
				data.put("color_code", color.get("color_code"));
				data.put("color_name", color.get("color_name"));
			}
	 		String params = (String) data.get("params");
			ParamGroup[] paramArs = converFormString( params);// 处理参数
			if(paramArs!=null){
				for(ParamGroup group : paramArs){
					List list = group.getParamList();
					for(int i=0;i<list.size();i++){
						GoodsParam param = (GoodsParam) list.get(i);
						data.put(param.getEname(), param.getValue());
					}
				}
			}
			
			if(data!=null && !data.isEmpty()){
				Iterator it = data.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					goods.put(key, String.valueOf(data.get(key)));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return goods;
	}
	
	public static ParamGroup[] converFormString(String params) {
		if (params == null || "".equals(params) || "[]".equals(params)||"null".equals(params))
			return null;
		Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("paramList", GoodsParam.class);
		JSONArray jsonArray = JSONArray.fromObject(params);

		Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

		if (obj == null)
			return null;

		return (ParamGroup[]) obj;
		
	}
	
	
	public static String execute(HttpServletRequest request,
            HttpServletResponse respose) throws Exception {
		ExecutionFactory factor = new ExecutionFactory();
		goodServices = SpringContextHolder.getBean("orderServiceLocal");
		outterECSTmplManager = SpringContextHolder.getBean("outterECSTmplManager");
		dbUtils = SpringContextHolder.getBean("dbUtils");
		
	    String inJson = null;
	    String outJson = null;
	    logger.info("=========获取请求json============");
//	    inJson = getInJson(request);
	    inJson = IOUtils.toString(request.getInputStream(),"utf-8"); 
	    logger.info("请求json="+inJson);
	    String LackOfField  = "";      
	    
	    List<Outer> out_order_List = new ArrayList<Outer>();
	    Outer out_order =  new Outer();
	    out_order_List.add(out_order);
	    //设置扩展参数
	    Map<String,String> extMap = new HashMap<String, String>();
	    out_order.setExtMap(extMap);
	    try{
	    	logger.info("============请求json串转jsonobj==============");
	    JSONObject jsonObject =  JSONObject.fromObject(inJson.toString());
	    logger.info("============获取order_req==============");
	    String order_req = jsonObject.get("order_req").toString();
	    JSONObject jsonObject_order_req = JSONObject.fromObject(order_req);
	    logger.info("jsonObject_order_req=============报文order_req=："+jsonObject_order_req);
	    if(jsonObject_order_req.get("pay_time")!= null && jsonObject_order_req.get("pay_time") !=""){
		       out_order.setPay_time(jsonObject_order_req.get("pay_time").toString().replace("-", "").replace(":", "").replace(" ", "").trim());//(API)支付时间pay_time-支付时间pay_time  
	    }else{
		    	  logger.info("订单信息同步接口pay_time不能正常setDTO异常=================================================================");
		      }
		    if(jsonObject_order_req.get("invoice_title") != null && jsonObject_order_req.get("invoice_title") !=""){
//		    out_order.setInvoice_title(jsonObject_order_req.get("invoice_title").toString());//(API)发票抬头invoice_title-发票抬头Reserve0
		    out_order.setReserve0(jsonObject_order_req.get("invoice_title").toString());
		    }else{
		    	logger.info("订单信息同步接口invoice_title不能正常setDTO异常=================================================================");
	        }
		    if(jsonObject_order_req.get("invoice_type") != null && jsonObject_order_req.get("invoice_type") !=""){
			    out_order.setInvoice_title(jsonObject_order_req.get("invoice_type").toString());//(API)发票类型invoice_title-发票类型invoice_type
//			    out_order.setReserve0(jsonObject_order_req.get("invoice_type").toString());
			    }else{
			    	logger.info("订单信息同步接口invoice_type不能正常setDTO异常=================================================================");
		        }
		    if(jsonObject_order_req.get("invoice_content") != null && jsonObject_order_req.get("invoice_content") !=""){
			    out_order.setReserve1(jsonObject_order_req.get("invoice_content").toString());//(API)发票内容invoice_content-发票类型Reserve1
			    }else{
			    	logger.info("订单信息同步接口invoice_type不能正常setDTO异常=================================================================");
		        }
		    if(jsonObject_order_req.get("shipping_type") != null && jsonObject_order_req.get("shipping_type") !=""){
				//out_order.setSending_type(jsonObject_order_req.get("shipping_type").toString());//(API)配送方式sending_type-shipping_type
		    	if(jsonObject_order_req.get("shipping_type").toString().equals("KD")){
					out_order.setSending_type("快递");
		    	}
		    	else if(jsonObject_order_req.get("shipping_type").toString().equals("SH")){
					out_order.setSending_type("送货");
		    	}
		    	else if(jsonObject_order_req.get("shipping_type").toString().equals("ZT")){
					out_order.setSending_type("自提");
		    	}
		    	else if(jsonObject_order_req.get("shipping_type").toString().equals("SDS")){
					out_order.setSending_type("闪电送");
		    	}
		    	else if(jsonObject_order_req.get("shipping_type").toString().equals("XCTH")){
					out_order.setSending_type("现场提货");
		    	}
		    	else{
		    		out_order.setSending_type("其他");
		    	}
		    
		    }else{
		    	logger.info("订单信息同步接口shipping_type不能正常setDTO异常=================================================================");	
	        }
		    if(jsonObject_order_req.get("order_type")!= null && jsonObject_order_req.get("order_type") !=""){
		    	out_order.setOrderacctype(jsonObject_order_req.get("order_type").toString());//订单接入类型orderacctype-订单类型order_type
		    	//out_order.setBss_order_type(jsonObject_order_req.get("order_type").toString());
		    }else{
		    	 LackOfField +="缺少必填字段为：order_type.";
		    	logger.info("订单信息同步接口order_type不能正常setDTO异常=====必填项============================================================");
	        }
		    //=====================================百度新增三字段
		    if(jsonObject_order_req.get("baidu_account")!= null && jsonObject_order_req.get("baidu_account") !=""){
		    	out_order.setBaidu_id(jsonObject_order_req.get("baidu_account").toString());//百度账号 -----API(入参DTO为 ：baidu_id)
		    }else{
		    	// LackOfField +="缺少必填字段为：baidu_account.";
		    	logger.info("订单信息同步接口baidu_account不能正常setDTO异常=====必填项============================================================");
	        }
		    if(jsonObject_order_req.get("baidu_no")!= null && jsonObject_order_req.get("baidu_no") !=""){
		    	out_order.setFreeze_tran_no(jsonObject_order_req.get("baidu_no").toString());//冻结流水号 -----API(入参DTO为 ：freeze_tran_no)
		    }else{
		    	// LackOfField +="缺少必填字段为：baidu_no.";
		    	logger.info("订单信息同步接口baidu_no不能正常setDTO异常=====必填项============================================================");
	        }
		    if(jsonObject_order_req.get("baidu_money")!= null && jsonObject_order_req.get("baidu_money") !=""){
		    	out_order.setFreeze_free(jsonObject_order_req.get("baidu_money").toString());//冻结款 -----API(入参DTO为 ：freeze_free)
		    }else{
		    	// LackOfField +="缺少必填字段为：baidu_money.";
		    	logger.info("订单信息同步接口baidu_money不能正常setDTO异常=====必填项============================================================");
	        }
		    if(jsonObject_order_req.get("baidu_begin_time")!= null && jsonObject_order_req.get("baidu_begin_time") !=""){
		    	String timea = jsonObject_order_req.get("baidu_begin_time").toString();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        String timesb = null;  
		        timesb = sdf2.format(sdf.parse(timea));  
		    	out_order.setReserve5(timesb.toString());//百度冻结开始时间-----API(入参DTO为 ：Reserve5)
		    }else{
		    	// LackOfField +="缺少必填字段为：baidu_begin_time.";
		    	logger.info("订单信息同步接口baidu_begin_time不能正常setDTO异常=====必填项============================================================");
	        }
		    if(jsonObject_order_req.get("baidu_end_time")!= null && jsonObject_order_req.get("baidu_end_time") !=""){
		    	String timea = jsonObject_order_req.get("baidu_end_time").toString();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        String timesb = null;  
		        timesb = sdf2.format(sdf.parse(timea));
		    	out_order.setReserve9(jsonObject_order_req.get("baidu_end_time").toString());//百度冻结结束时间-----API(入参DTO为 ：Reserve9)
		    }else{
		    	// LackOfField +="缺少必填字段为：baidu_end_time.";
		    	logger.info("订单信息同步接口baidu_end_time不能正常setDTO异常=====必填项============================================================");
	        }
		    //=======================================================
		    if(jsonObject_order_req.get("order_id") != null && jsonObject_order_req.get("order_id") !=""){
		    	out_order.setOrderacccode(jsonObject_order_req.get("order_id").toString());//订单接入编码orderacccode-订单编码order_id
		    }else{
		    	LackOfField +="缺少必填字段为：order_id.";
		    	  logger.info("订单信息同步接口order_id不能正常setDTO异常======必填项===========================================================");
	        }
		    if(jsonObject_order_req.get("status") != null && jsonObject_order_req.get("status") !=""){
		    	out_order.setStatus(jsonObject_order_req.get("status").toString());//处理状态status-订单状态status
		    	//out_order.setDelivery_status(jsonObject_order_req.get("status").toString());
		    	//out_order.setPlatform_status(jsonObject_order_req.get("status").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：status.";
		    	  logger.info("订单信息同步接口status不能正常setDTO异常====必填项=============================================================");
	      }
		    if(jsonObject_order_req.get("order_disacount") != null && jsonObject_order_req.get("order_disacount") !=""){
		    	out_order.setDiscountValue(jsonObject_order_req.get("order_disacount").toString());//优惠金额discountValue-优惠金额order_disacount
		    }else{
		    	LackOfField +="缺少必填字段为：order_disacount.";
		    	  logger.info("订单信息同步接口order_disacount不能正常setDTO异常===必填项==============================================================");
	      }
		    if(jsonObject_order_req.get("ship_name") != null && jsonObject_order_req.get("ship_name") !=""){
		    	out_order.setReceiver_name(jsonObject_order_req.get("ship_name").toString());//收货人姓名 ship_name-收货人姓名ship_name
		    }else{
		    	  logger.info("订单信息同步接口ship_name不能正常setDTO异常=================================================================");
	      }
		    
		    if(jsonObject_order_req.get("source_from_system") != null && jsonObject_order_req.get("source_from_system") !=""){
		    	//out_order.setPlat_type(jsonObject_order_req.get("source_from_system").toString());//订单来源系统
		    	if(jsonObject_order_req.get("source_from_system").toString().equals("10001")){
					out_order.setOrder_from("淘宝");
		    	out_order.setOrder_channel("淘宝");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10002")){
									out_order.setPlat_type("联通商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10003")){
									out_order.setPlat_type("总部商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10004")){
									out_order.setPlat_type("网盟店铺");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10005")){
									out_order.setPlat_type("拍拍");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10006")){
									out_order.setPlat_type("农行商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10007")){
									out_order.setPlat_type("360商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10008")){
									out_order.setPlat_type("沃云购");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10009")){
									out_order.setPlat_type("订单系统");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10010")){
									out_order.setPlat_type("WMS");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10011")){
									out_order.setPlat_type("商品管理系统");
				}
				
				
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10012")){
									out_order.setPlat_type("淘宝分销");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10036")){
									out_order.setPlat_type("沃商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10037")){
									out_order.setPlat_type("CPS");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10038")){
									out_order.setPlat_type("异业联盟");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10015")){
									out_order.setPlat_type("电话商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10030")){
									out_order.setPlat_type("微商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10031")){
									out_order.setPlat_type("沃货架");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10032")){
									out_order.setPlat_type("营业厅U惠站");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10033")){
									out_order.setPlat_type("销售联盟");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10034")){
									out_order.setPlat_type("vip商城");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10035")){
									out_order.setPlat_type("电子沃店");
				}
		    	else if(jsonObject_order_req.get("source_from_system").toString().equals("10039")){
					out_order.setPlat_type("百度担保");
		    	}
				else{
					out_order.setPlat_type("其他");
				}
		    	
		    	
		    }else{
		    	LackOfField +="缺少必填字段为：source_from_system.";
		    	  logger.info("订单信息同步接口source_from_system不能正常setDTO异常=================================================================");
	      }
		    
		    if(jsonObject_order_req.get("ship_phone") != null && jsonObject_order_req.get("ship_phone") !=""){
		    	out_order.setReceiver_mobile(jsonObject_order_req.get("ship_phone").toString());//收货手机 ship_mobile-手机号码ship_phone
		    }else{
		    	  logger.info("订单信息同步接口ship_phone不能正常setDTO异常=================================================================");
	      }
		    if(jsonObject_order_req.get("ship_addr")!= null && jsonObject_order_req.get("ship_addr") !=""){
		    	out_order.setAddress(jsonObject_order_req.get("ship_addr").toString());//收货人详细地址 SHIP_ADDR-地址ship_addr
		    }else{
		    	  logger.info("订单信息同步接口ship_addr不能正常setDTO异常=================================================================");
	      }
		    if(jsonObject_order_req.get("ship_city") != null && jsonObject_order_req.get("ship_city") !=""){
		    	out_order.setCity_code(jsonObject_order_req.get("ship_city").toString());
		    	String cityname = goodServices.getCityname(jsonObject_order_req.get("ship_city").toString());
		    	out_order.setCity(cityname);//收货人所在市city-地址地市ship_city
		    	 logger.info("*******************************"+cityname);
		    	String proId = goodServices.getProviceId(jsonObject_order_req.get("ship_city").toString());//根据地市编码查询省份编码
		    	out_order.setProvinc_code(proId);
		    	String proname = goodServices.getProvicename(jsonObject_order_req.get("ship_city").toString());//根据地市编码查询省份名称
		    	 logger.info("*******************************"+cityname+">>>>>>>"+proId+">>>>>>>>>>>>>>>"+proname);
		    	out_order.setProvince(proname);
		    	
		    }else{
		    	  logger.info("订单信息同步接口ship_city不能正常setDTO异常=================================================================");
	      }
		    if(jsonObject_order_req.get("ship_country")!= null && jsonObject_order_req.get("ship_country") !=""){
		    	out_order.setArea_code(jsonObject_order_req.get("ship_country").toString());
		    	String counrtyname = goodServices.getCityname(jsonObject_order_req.get("ship_country").toString());//地市编码转名称
		    	out_order.setDistrict(counrtyname);//收货人所在区 region-地址区县ship_country
		    	
		    }else{
		    	  logger.info("订单信息同步接口ship_country不能正常setDTO异常=================================================================");
	      }
		    if(jsonObject_order_req.get("ship_tel") != null && jsonObject_order_req.get("ship_tel") !=""){
		    	out_order.setPhone(jsonObject_order_req.get("ship_tel").toString());//收货phone电话 tel-固定电话ship_tel
		    }else{
		    	  logger.info("订单信息同步接口ship_tel不能正常setDTO异常=================================================================");
	      }
		    if(jsonObject_order_req.get("create_time") != null && jsonObject_order_req.get("create_time")!=""){
		    	String time = jsonObject_order_req.get("create_time").toString();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        String times = null;  
		        times = sdf2.format(sdf.parse(time));  
                logger.info("create_time::::::::::::::::::::::::::::::::::::="+times);  
		    	out_order.setTid_time(times.toString());//订货时间tid_time-下单时间create_time
		    }else{
		    	LackOfField +="缺少必填字段为：create_time.";
		    	  logger.info("订单信息同步接口create_time不能正常setDTO异常====必填项=============================================================");
	    }
		    if(jsonObject_order_req.get("channel_id") != null && jsonObject_order_req.get("channel_id") !=""){
		    	out_order.setPaychannelid(jsonObject_order_req.get("channel_id").toString());//支付渠道编码paychannelid-渠道标识channel_id
		    }else{
		    	  logger.info("订单信息同步接口channel_id不能正常setDTO异常=================================================================");
	    }
		    if(jsonObject_order_req.get("shipping_time") != null && jsonObject_order_req.get("shipping_time") !=""){
		    	out_order.setCert_failure_time(jsonObject_order_req.get("shipping_time").toString());//(API)XXX-配送时间shipping_time
		    }else{
		    	  logger.info("订单信息同步接口shipping_time不能正常setDTO异常=================================================================");
	    } 
		    if(jsonObject_order_req.get("invoice_type") != null && jsonObject_order_req.get("invoice_type") !=""){
		    	out_order.setIs_bill(jsonObject_order_req.get("invoice_type").toString());//是否开发票is_bill-发票类型invoice_type
		    }else{
		    	  logger.info("订单信息同步接口invoice_type不能正常setDTO异常=================================================================");
	  }
		    if(jsonObject_order_req.get("invoice_print_type")!= null && jsonObject_order_req.get("invoice_print_type")!=""){
		    	out_order.setInvoice_print_type(jsonObject_order_req.get("invoice_print_type").toString());//发票打印方式invoice_print_type-发票明细invoice_content
		    }else{
		    	  logger.info("订单信息同步接口invoice_content不能正常setDTO异常=================================================================");
	  } 
		    if(jsonObject_order_req.get("order_comment") != null && jsonObject_order_req.get("order_comment") !=""){
		    	out_order.setBuyer_message(jsonObject_order_req.get("order_comment").toString());//买家留言buyer_message-订单备注order_comment
		    	out_order.setService_remarks(jsonObject_order_req.get("order_comment").toString());
		    }else{
		    	  logger.info("订单信息同步接口buyer_message不能正常setDTO异常=================================================================");
	  }
		    
//		    添加买家备注 20140723
		    if (jsonObject_order_req.get("buyer_message") != null && !"".equals(jsonObject_order_req.get("buyer_message").toString())) {
				out_order.setBuyer_message(jsonObject_order_req.get("buyer_message").toString());
			}
		    
		    if(jsonObject_order_req.get("serial_no")!= null && jsonObject_order_req.get("serial_no")!=""){
		    	out_order.setPayplatformorderid(jsonObject_order_req.get("serial_no").toString());//支付订单号payplatformorderid-序列号serial_no
		    }else{
		    	LackOfField +="缺少必填字段为：serial_no.";
		    	  logger.info("订单信息同步接口serial_no不能正常setDTO异常=====必填项============================================================");
	  } 
		    if(jsonObject_order_req.get("time")!= null && jsonObject_order_req.get("time")!=""){
		    	String timea = jsonObject_order_req.get("create_time").toString();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        String timesb = null;  
		        timesb = sdf2.format(sdf.parse(timea));  
                logger.info("time:::::::AAAAA:::::::::::::::::::::::::::::="+timesb); 
		    	out_order.setGet_time(timesb.toString());//获取时间get_time-时间time
		    }else{
		    	  logger.info("订单信息同步接口time不能正常setDTO异常===必填项==============================================================");
	  }
		    if(jsonObject_order_req.get("source_system")!= null && jsonObject_order_req.get("source_system")!=""){
		    	out_order.setOut_tid(jsonObject_order_req.get("order_id").toString()); //取外部订单id
		    }else{
		    	LackOfField +="缺少必填字段为：source_system.";
		    	  logger.info("订单信息同步接口source_system不能正常setDTO异常=====必填项============================================================");
	  } 
		    if(jsonObject_order_req.get("receive_system")!= null && jsonObject_order_req.get("receive_system")!=""){
		    	out_order.setPayproviderid(jsonObject_order_req.get("receive_system").toString());//支付机构编码payproviderid-接收方系统标识receive_system
		    }else{
		    	LackOfField +="缺少必填字段为：receive_system.";
		    	  logger.info("订单信息同步接口receive_system不能正常setDTO异常=====必填项============================================================");
	}
		    if(jsonObject_order_req.get("channel_type")!= null && jsonObject_order_req.get("channel_type")!=""){
		    	 logger.info("source================================================================="+jsonObject_order_req.get("channel_type"));
		    	 if (jsonObject_order_req.get("channel_type").toString().equals("77")) {
		             out_order.setPaychannelname("自有渠道");
		           }
		           else if (jsonObject_order_req.get("channel_type").toString().equals("97")) {
		             out_order.setPaychannelname("社会渠道");
		           }
		           else if (jsonObject_order_req.get("channel_type").toString().equals("137")) {
		             out_order.setPaychannelname("其它渠道");
		           }
		           else if(jsonObject_order_req.get("channel_type").toString().equals("0")){
		    		out_order.setPaychannelname("自营");//支付渠道名称paychannelname-渠道类型channel_type
		    	}
		    	else if(jsonObject_order_req.get("channel_type").toString().equals("1")){
		    		out_order.setPaychannelname("代理商");//支付渠道名称paychannelname-渠道类型channel_type
		    	}
		    	else if(jsonObject_order_req.get("channel_type").toString().equals("2")){
		    		out_order.setPaychannelname("店铺");//支付渠道名称paychannelname-渠道类型channel_type
		    	}
		    	else {
		    		out_order.setPaychannelname("其他渠道");//支付渠道名称paychannelname-渠道类型channel_type
		    	}
		    	
		    
		    }else{
		    	LackOfField +="缺少必填字段为：channel_type.";
		    	  logger.info("订单信息同步接口channel_type不能正常setDTO异常====必填项=============================================================");
	} 
		    if(jsonObject_order_req.get("order_heavy")!= null && jsonObject_order_req.get("order_heavy")!=""){
		    	out_order.setOrder_totalfee(jsonObject_order_req.get("order_heavy").toString());//订单总金额order_totalfee-订单重量order_heavy
		    }else{
		    	LackOfField +="缺少必填字段为：order_heavy.";
		    	  logger.info("订单信息同步接口order_heavy不能正常setDTO异常====必填项=============================================================");
	}
		    if(jsonObject_order_req.get("shipping_amount")!= null && jsonObject_order_req.get("shipping_amount")!=""){
		    	out_order.setPost_fee(jsonObject_order_req.get("shipping_amount").toString());//邮费post_fee-运费shipping_amount
		    }else{
		    	  logger.info("订单信息同步接口shipping_amount不能正常setDTO异常=================================================================");
	} 
		    if(jsonObject_order_req.get("shipping_company")!= null && jsonObject_order_req.get("shipping_company")!=""){
		    	out_order.setRecommended_code(jsonObject_order_req.get("shipping_company").toString());//推荐人编码 Spread_code-物流公司编码shipping_company
		    }else{
		    	  logger.info("订单信息同步接口shipping_company不能正常setDTO异常=================================================================");
	}
//		    if(jsonObject_order_req.get("ship_area")!= null && jsonObject_order_req.get("ship_area")!=""){
//		    	out_order.setWm_order_from(jsonObject_order_req.get("ship_area").toString());//网盟订单来源wm_order_from-地址商圈ship_area
//		    }else{
//		    	  logger.info("订单信息同步接口ship_area不能正常setDTO异常=================================================================");
//	} 
//		    if(jsonObject_order_req.get("ship_email")!= null && jsonObject_order_req.get("ship_email")!=""){
//		    	out_order.setService_remarks(jsonObject_order_req.get("ship_email").toString());//客服备注service_remarks-电子邮件ship_email
//		    }else{
//		    	  logger.info("订单信息同步接口ship_email不能正常setDTO异常=================================================================");
//	}

		    if(jsonObject_order_req.get("source_from")!= null && jsonObject_order_req.get("source_from")!=""){
//		    	out_order.setOrder_from(jsonObject_order_req.get("source_from").toString());//订单来源order_from-订单来源source_from
//		    	out_order.setOrder_channel(jsonObject_order_req.get("source_from").toString());
		    	 logger.info("source================================================================="+jsonObject_order_req.get("source_from"));
		    	if(jsonObject_order_req.get("source_from").toString().equals("10001")){
					out_order.setOrder_from("淘宝");
		    	out_order.setOrder_channel("淘宝");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10002")){
									out_order.setOrder_from("联通商城");
						    	out_order.setOrder_channel("联通商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10003")){
									out_order.setOrder_from("总部商城");
						    	out_order.setOrder_channel("总部商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10004")){
									out_order.setOrder_from("网盟店铺");
						    	out_order.setOrder_channel("网盟店铺");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10005")){
									out_order.setOrder_from("拍拍");
						    	out_order.setOrder_channel("拍拍");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10006")){
									out_order.setOrder_from("农行商城");
						    	out_order.setOrder_channel("农行商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10007")){
									out_order.setOrder_from("360商城");
						    	out_order.setOrder_channel("360商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10008")){
									out_order.setOrder_from("沃云购");
						    	out_order.setOrder_channel("沃云购");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10009")){
									out_order.setOrder_from("订单系统");
						    	out_order.setOrder_channel("订单系统");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10010")){
									out_order.setOrder_from("WMS");
						    	out_order.setOrder_channel("WMS");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10011")){
									out_order.setOrder_from("商品管理系统");
						    	out_order.setOrder_channel("商品管理系统");
				}
				
				
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10012")){
									out_order.setOrder_from("淘宝分销");
						    	out_order.setOrder_channel("淘宝分销");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10036")){
									out_order.setOrder_from("沃商城");
						    	out_order.setOrder_channel("沃商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10037")){
									out_order.setOrder_from("CPS");
						    	out_order.setOrder_channel("CPS");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10038")){
									out_order.setOrder_from("异业联盟");
						    	out_order.setOrder_channel("异业联盟");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10039")){
					out_order.setOrder_from("百度担保");
					out_order.setOrder_channel("百度担保");
		    	}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10015")){
									out_order.setOrder_from("电话商城");
						    	out_order.setOrder_channel("电话商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10030")){
									out_order.setOrder_from("微商城");
						    	out_order.setOrder_channel("微商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10031")){
									out_order.setOrder_from("沃货架");
						    	out_order.setOrder_channel("沃货架");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10032")){
									out_order.setOrder_from("营业厅U惠站");
						    	out_order.setOrder_channel("营业厅U惠站");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10033")){
									out_order.setOrder_from("销售联盟");
						    	out_order.setOrder_channel("销售联盟");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10034")){
									out_order.setOrder_from("vip商城");
						    	out_order.setOrder_channel("vip商城");
				}
		    	else if(jsonObject_order_req.get("source_from").toString().equals("10035")){
									out_order.setOrder_from("电子沃店");
						    	out_order.setOrder_channel("电子沃店");
				}
				else{
					out_order.setOrder_from("其他");
			    	out_order.setOrder_channel("其他");
				}
		    	
		    }else{
		    	LackOfField +="缺少必填字段为：source_from.";
		    	  logger.info("订单信息同步接口source_from不能正常setDTO异常=====必填项============================================================");
	} 
		    if(jsonObject_order_req.get("payment_type")!= null && jsonObject_order_req.get("payment_type")!=""){
				out_order.setPay_method(jsonObject_order_req.get("payment_type").toString());//支付方式pay_method-支付方式payment_type
		    }else{
		    	LackOfField +="缺少必填字段为：payment_type.";
		    	  logger.info("订单信息同步接口payment_type不能正常setDTO异常=====必填项============================================================");
	}
		    if(jsonObject_order_req.get("order_amount")!= null && jsonObject_order_req.get("order_amount")!=""){
		    	out_order.setOrder_origfee(jsonObject_order_req.get("order_amount").toString());//订单应收总价order_origfee-订单总价order_amount
		    }else{
		    	LackOfField +="缺少必填字段为：order_amount.";
		    	  logger.info("订单信息同步接口order_amount不能正常setDTO异常===必填项==============================================================");
	} 
			
		    if(jsonObject_order_req.get("pay_money")!= null && jsonObject_order_req.get("pay_money")!=""){
				out_order.setOrder_realfee(jsonObject_order_req.get("pay_money").toString());//订单实收总价order_realfee-实收金额pay_money
		    }else{
		    	LackOfField +="缺少必填字段为：pay_money.";
		    	  logger.info("订单信息同步接口pay_money不能正常setDTO异常===必填项==============================================================");
	} 
		  
		    if(jsonObject_order_req.get("abnormal_status")!= null && jsonObject_order_req.get("abnormal_status")!=""){
				out_order.setAbnormal_status(jsonObject_order_req.get("abnormal_status").toString());//订单实收总价order_realfee-实收金额pay_money
		    }else{
		    	LackOfField +="缺少必填字段为：abnormal_status.";
		    	  logger.info("订单信息同步接口abnormal_status不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("delivery_status")!= null && jsonObject_order_req.get("delivery_status")!=""){
				out_order.setDelivery_status(jsonObject_order_req.get("delivery_status").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：delivery_status.";
		    	  logger.info("订单信息同步接口delivery_status不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("platform_status")!= null && jsonObject_order_req.get("platform_status")!=""){
				out_order.setPlatform_status(jsonObject_order_req.get("platform_status").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：platform_status.";
		    	  logger.info("订单信息同步接口platform_status不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("order_provinc_code")!= null && jsonObject_order_req.get("order_provinc_code")!=""){
						out_order.setOrder_provinc_code(jsonObject_order_req.get("order_provinc_code").toString());
				    }else{
				    	LackOfField +="缺少必填字段为：order_provinc_code.";
				    	  logger.info("订单信息同步接口order_provinc_code不能正常setDTO异常===必填项==============================================================");
			} 
		    if(jsonObject_order_req.get("order_city_code")!= null && jsonObject_order_req.get("order_city_code")!=""){
				out_order.setOrder_city_code(jsonObject_order_req.get("order_city_code").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：order_city_code.";
		    	  logger.info("订单信息同步接口order_city_code不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("alipay_id")!= null && jsonObject_order_req.get("alipay_id")!=""){
				out_order.setAlipay_id(jsonObject_order_req.get("alipay_id").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：alipay_id.";
		    	  logger.info("订单信息同步接口alipay_id不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("uname")!= null && jsonObject_order_req.get("uname")!=""){
				out_order.setBuyer_id(jsonObject_order_req.get("uname").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：uname.";
		    	  logger.info("订单信息同步接口uname不能正常setDTO异常===必填项==============================================================");
	} 
		    if(jsonObject_order_req.get("name")!= null && jsonObject_order_req.get("name")!=""){
				out_order.setBuyer_name(jsonObject_order_req.get("name").toString());
		    }else{
		    	LackOfField +="缺少必填字段为：name.";
		    	  logger.info("订单信息同步接口name不能正常setDTO异常===必填项==============================================================");
	} 
		    
			
		   
		//out_order.setCollection_free(jsonObject_order_req.get("collection_free").toString());//代收邮费collection_free
		//out_order.setPromotion_area(jsonObject_order_req.get("promotion_area").toString());//促销政策地
	
	    // if(jsonObject_order_req.get("pay_time").toString() != null&&jsonObject_order_req.get("pay_time").toString() != ""){
	    // }
		
	
		//out_order.setOrder_disfee(jsonObject_order_req.get("order_disfee").toString());//整单优惠
		//out_order.setPlat_distributor_code(jsonObject_order_req.get("plat_distributor_code").toString());//平台分销商编号
		
		
		//out_order.setProvince(jsonObject_order_req.get("province").toString());//收货人所在省
		
		//out_order.setPost(jsonObject_order_req.get("post").toString());//收货人邮编 ship_zip
		//out_order.setProvinc_code(jsonObject_order_req.get("provinc_code").toString());//收货省编码
		//out_order.setCity_code(jsonObject_order_req.get("city_code").toString());//收货市编码
		//out_order.setArea_code(jsonObject_order_req.get("area_code").toString());//收货区编码
		
		//out_order.setFile_return_type(jsonObject_order_req.get("file_return_type").toString());//资料回收方式
		
		//out_order.setVouchers_money(jsonObject_order_req.get("vouchers_money").toString());//代金券面值
		//out_order.setVouchers_code(jsonObject_order_req.get("vouchers_code").toString());//代金券编号
		//out_order.setDisfee_type(jsonObject_order_req.get("disfee_type").toString());//优惠类型
		//out_order.setOne_agents_id(jsonObject_order_req.get("one_agents_id").toString());//一级代理商id
		//out_order.setTwo_agents_id(jsonObject_order_req.get("two_agents_id").toString());//二级代理商id
		
		//out_order.setWt_paipai_order_id(jsonObject_order_req.get("wt_paipai_order_id").toString());//拍拍网厅的订单号*接口文档中有,样例JSON中无
		//out_order.setCouponname(jsonObject_order_req.get("couponname").toString());//代金券名称
		//out_order.setCouponbatchid(jsonObject_order_req.get("couponbatchid").toString());//代金券批次号
		
		//out_order.setPaytype(jsonObject_order_req.get("paytype").toString());//支付类型
		//out_order.setPayfintime(jsonObject_order_req.get("payfintime").toString());//支付时间
		
		
		//out_order.setPayprovidername(jsonObject_order_req.get("payprovidername").toString());//支付机构名称
		//out_order.setDiscountname(jsonObject_order_req.get("discountname").toString());//优惠活动名称
		//out_order.setDiscountid(jsonObject_order_req.get("discountid").toString());//优惠活动编号
		//out_order.setDiscountrange(jsonObject_order_req.get("discountrange").toString());//优惠幅度	
		//out_order.setBss_order_type(jsonObject_order_req.get("bss_order_type").toString());//BSS产品类型*接口文档中有,样例JSON中无
		//out_order.setCert_address(jsonObject_order_req.get("cert_address").toString());
		//out_order.setOrder_channel(jsonObject_order_req.get("order_channel").toString());//订单渠道
		//out_order.setType(jsonObject_order_req.get("type").toString());//订单类型
		//out_order.setMerge_status(jsonObject_order_req.get("merge_status").toString());//合并状态
		//out_order.setPlat_type(jsonObject_order_req.get("plat_type").toString());//平台类型
		//out_order.setPro_totalfee(jsonObject_order_req.get("pro_totalfee").toString());//产品总金额
		//out_order.setIs_adv_sale(jsonObject_order_req.get("is_adv_sale").toString());//是否预售
		//out_order.setPay_status(jsonObject_order_req.get("pay_status").toString());//付款状态
		
		//out_order.setRecommended_name(jsonObject_order_req.get("recommended_name").toString());//推荐人名称 spread_name
		//out_order.setRecommended_phone(jsonObject_order_req.get("recommended_phone").toString());//推荐人手机号码 spread_phone
		//out_order.setDevelopment_code(jsonObject_order_req.get("development_code").toString());//发展人编码  *接口文档此字段应该List里面
		//out_order.setDevelopment_name(jsonObject_order_req.get("development_name").toString());//发展人名称  *接口文档此字段应该List里面
		//out_order.setWm_isreservation_from(jsonObject_order_req.get("wm_isreservation_from").toString());//是否预约单
		//out_order.setGoods_num(jsonObject.get("").toString());
		//out_order.setTid_category(jsonObject_order_req.get("tid_category").toString());////订单类别
	   ////out_order.setItemList(jsonObject.get("temList").toString());
            logger.info("----------------进入List for取值---开始");
   		 List<OuterItem> itemList  = new ArrayList<OuterItem>();
   		String outer_sku_id = "";
	     out_order.setItemList(itemList);
		 JSONArray jsonArray = jsonObject_order_req.getJSONArray("order_list");
		  for (int i = 0; i < jsonArray.size(); i++) {
			  OuterItem OuterItemgz = new OuterItem();
			                JSONObject jsonStr= JSONObject.fromObject(jsonArray.getString(i));  
			                
			        	    if(jsonStr.get("prod_offer_code")!= null &&jsonStr.get("prod_offer_code") !=""){
			        	    	outer_sku_id = getValue(jsonStr.get("prod_offer_code"));
			        	    	Map<String, String> param = new HashMap<String, String>();
			        	    	if (!StringUtils.isEmpty(outer_sku_id)) {
			        	    		GoodsGetReq req = new GoodsGetReq();
//									req.setPackage_id(pagkage_id);
									req.setPackage_id(outer_sku_id);
									GoodsGetResp resp = null;
									
									try{
										resp = goodServices.getGoods(req);// client.execute(req,
//										resp = goodServices.getGoods(req);
									}catch(Exception ex){
										ex.printStackTrace();
										OuterError ng = new OuterError(null, outer_sku_id, outer_sku_id, null, "wsc", jsonObject_order_req.get("order_id").toString(), "sysdate","goodserror");//noparam  nogoods
										ng.setDeal_flag("-1");
										outterECSTmplManager.insertOuterError(ng);
										logger.info("获取商品相关参数失败======================");
										continue;
									}
									
//									if(!data.isEmpty()){
//										resp.setResult(true);
//										resp.setError_code("0");
//										resp.setError_msg("成功");
//										resp.setData(data);
//									}else{
//										resp.setResult(false);
//										resp.setError_code("-1");
//										resp.setError_msg("失败：返回为空");
//									}
									
									if ("0".equals(resp.getError_code())) {
										param = resp.getData();
										if(jsonStr.get("offer_price")!= null && jsonStr.get("offer_price")!=""){
							      	    	OuterItemgz.setPro_origfee(Double.valueOf(getValue(jsonStr.get("offer_price"))).doubleValue());//商品应收金额pro_origfee-商品价格offer_price
							      	    }else{
							      	    	LackOfField +="缺少必填字段为：offer_price.";
							      	    	  logger.info("订单信息同步接口offer_price不能正常setDTO异常=========必填项========================================================");
							      }
							      	    if(jsonStr.get("offer_coupon_price")!= null && jsonStr.get("offer_coupon_price")!=""){
							      	    	OuterItemgz.setSell_price(Double.valueOf(getValue(jsonStr.get("offer_coupon_price"))).doubleValue());//商品单价sell_price-实收价格offer_coupon_price 
							      	    }else{
							      	    	LackOfField +="缺少必填字段为：offer_coupon_price.";
							      	    	  logger.info("订单信息同步接口offer_coupon_price不能正常setDTO异常==必填项===============================================================");
							      } 
							      	  if(jsonStr.get("prod_offer_num")!= null && jsonStr.get("prod_offer_num")!=""){
							      		  OuterItemgz.setPro_num(new Integer(getValue(jsonStr.get("prod_offer_num"))));//订货数量pro_num-商品数量prod_offer_num
							      	  }else{
							      		LackOfField +="缺少必填字段为：prod_offer_num.";
						      	    	  logger.info("订单信息同步接口prod_offer_num不能正常setDTO异常====必填项=============================================================");
						      }
						      	    if(jsonStr.get("activity_list")!= null && jsonStr.get("activity_list")!=""){
						      	    	OuterItemgz.setAtive_type(getValue(jsonStr.get("activity_list")));//活动类型 ative_type-活动信息activity_list
						      	    }else{
						      	  	LackOfField +="缺少必填字段为：activity_list.";
						      	    	  logger.info("订单信息同步接口ative_type不能正常setDTO异常====必填项=============================================================");
						      } 
						      	  if(jsonStr.get("new_acc_nbr")!= null && jsonStr.get("new_acc_nbr")!=""){
						      		OuterItemgz.setPhone_num(getValue(jsonStr.get("new_acc_nbr")));//手机号码phone_num-新手机号码new_acc_nbr
						      	  }else{
					      	    	  logger.info("订单信息同步接口new_acc_nbr不能正常setDTO异常=================================================================");
					      }
					      	    if(jsonStr.get("card_type")!= null && jsonStr.get("card_type")!=""){
					      	    	String white_cart_type = "";
					      	    	if("NM".equalsIgnoreCase(jsonStr.get("card_type").toString())){
					      	    		white_cart_type = "1";
					      	    	}else if("MC".equalsIgnoreCase(jsonStr.get("card_type").toString())){
					      	    		white_cart_type = "2";
					      	    	}else if("NN".equalsIgnoreCase(jsonStr.get("card_type").toString())){
					      	    		white_cart_type = "3";
					      	    	}else{
					      	    		white_cart_type = jsonStr.get("card_type").toString();
					      	    	}
					      	    	OuterItemgz.setWhite_cart_type(getValue(white_cart_type));//卡类型 white_cart_type-卡类型card_type
					      	    }else{
					      	    	  logger.info("订单信息同步接口card_type不能正常setDTO异常=================================================================");
					      } 
							                
					      	  if(jsonStr.get("old_acc_nbr")!= null && jsonStr.get("old_acc_nbr")!=""){
					      		OuterItemgz.setLoves_phone_num(getValue(jsonStr.get("old_acc_nbr")));//情侣号码loves_phone_num-老用户号码old_acc_nbr
					      	  }else{
				      	    	  logger.info("订单信息同步接口old_acc_nbr不能正常setDTO异常=================================================================");
				      } 
					      	 if(jsonStr.get("inventory_code")!= null && jsonStr.get("inventory_code")!=""){
					      		  OuterItemgz.setOuter_sku_id(getValue(jsonStr.get("inventory_code")));//条型码outer_sku_id-仓库编码inventory_code
					      	 }else{
				     	    	  logger.info("订单信息同步接口inventory_code不能正常setDTO异常=================================================================");
				     } 
					      	 if(jsonStr.get("certi_type")!= null && jsonStr.get("certi_type") !=""){
					      		 String certi_type = "";
					      		 if("SFZ15".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
					      			certi_type = "2";
					      		 }else if("SFZ18".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "1";
						      	 }else if("HZB".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "3";
						      	 }else if("HKB".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "6";
						      	 }else if("JUZ".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "4";
						      	 }else if("JGZ".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "7";
						      	 }else if("GOT".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "10";
						      	 }else if("TWT".equalsIgnoreCase(jsonStr.get("certi_type").toString())){
						      			certi_type = "11";
						      	 }else{
						      		certi_type = jsonStr.get("certi_type").toString();
						      	 }
					      		OuterItemgz.setCert_type(getValue(certi_type));//证件类型cert_type-证件类型certi_type
					      	 }else{
				     	    	  logger.info("订单信息同步接口certi_type不能正常setDTO异常=================================================================");
				     } 
					      	 if(jsonStr.get("certi_num")!= null && jsonStr.get("certi_num")!=""){
					      		OuterItemgz.setCert_card_num(getValue(jsonStr.get("certi_num")));//证件号码cert_card_num-证件号码certi_num
					      	 }else{
				     	    	  logger.info("订单信息同步接口certi_num不能正常setDTO异常=================================================================");
				     } 
					    	 if(jsonStr.get("certi_address")!= null && jsonStr.get("certi_address") !=""){
					    		 out_order.setCert_address(getValue(jsonStr.get("certi_address")));//证件地址 cert_address-证件地址certi_address
					    		 OuterItemgz.setCert_address(getValue(jsonStr.get("certi_address")));//证件地址 cert_address-证件地址certi_address
					    	 }else{
				    	    	  logger.info("订单信息同步接口certi_address不能正常setDTO异常=================================================================");
				    } 
					    	 if(jsonStr.get("cust_name")!= null && jsonStr.get("cust_name")!=""){
					    		  OuterItemgz.setPhone_owner_name(getValue(jsonStr.get("cust_name")));//机主姓名phone_owner_name-客户名称cust_name
					    	 }else{
				    	    	  logger.info("订单信息同步接口cust_name不能正常setDTO异常=================================================================");
				    } 
					    	 if(jsonStr.get("certi_valid_date") != null && jsonStr.get("certi_valid_date")!=""){
					    		 OuterItemgz.setCert_failure_time(getValue(jsonStr.get("certi_valid_date")));//证件失效时间cert_failure_time-证件失效期certi_valid_date
					    	 }else{
				    	    	  logger.info("订单信息同步接口certi_valid_date不能正常setDTO异常=================================================================");
				    } 
					    	 if(jsonStr.get("development_code")!= null && jsonStr.get("development_code")!=""){
					    		 out_order.setDevelopment_code(getValue(jsonStr.get("development_code")));//发展人编码development_code
					    	 }else{
				    	    	  logger.info("订单信息同步接口development_code不能正常setDTO异常=================================================================");
				    } 
					    	 if(jsonStr.get("development_name")!= null && jsonStr.get("development_name") !=""){
					    		 out_order.setDevelopment_name(getValue(jsonStr.get("development_name")));//发展人名称development_name
					    	 }else{
				   	    	  logger.info("订单信息同步接口development_name不能正常setDTO异常=================================================================");
				   } 
					    	 if(jsonStr.get("reference_name")!= null && jsonStr.get("reference_name")!=""){
					    		 OuterItemgz.setPro_name(getValue(jsonStr.get("reference_name")));//产品名称pro_name-推荐人名称reference_name
					    	 }else{
				   	    	  logger.info("订单信息同步接口reference_name不能正常setDTO异常=================================================================");
				   } 
					    	 if(jsonStr.get("reference_phone")!= null && jsonStr.get("reference_phone")!=""){
					    		 OuterItemgz.setFamliy_num(getValue(jsonStr.get("reference_phone")));//亲情号码famliy_num-推荐人手机reference_phone
					    	 }else{
				   	    	  logger.info("订单信息同步接口reference_phone不能正常setDTO异常=================================================================");
				   } 
					    	 if(jsonStr.get("plan_title")!= null && jsonStr.get("plan_title")!=""){
					    		 OuterItemgz.setPlan_title(getValue(jsonStr.get("plan_title")));//套餐名称plan_title-商品备注offer_comment
					    	 }else{
					   	    	  logger.info("订单信息同步接口plan_title不能正常setDTO异常=================================================================");
					   } 
						    	 if(jsonStr.get("offer_eff_type")!= null && jsonStr.get("offer_eff_type")!=""){
						    			OuterItemgz.setFirst_payment(getValue(jsonStr.get("offer_eff_type")));//First_payment首月资费-offer_eff_type
						    	 }else{
					   	    	  logger.info("订单信息同步接口offer_eff_type不能正常setDTO异常=================================================================");
					   } 
						    	 if(jsonStr.get("offer_disacount_price")!= null && jsonStr.get("offer_disacount_price") !=""){
						    			OuterItemgz.setLiang_price(Double.valueOf(getValue(jsonStr.get("offer_disacount_price"))).doubleValue());//靓号金额liang_price-优惠价格offer_disacount_price
						    	 }else{
						    		 LackOfField +="缺少必填字段为：offer_disacount_price.";
						   	    	  logger.info("订单信息同步接口offer_disacount_price不能正常setDTO异常==必填项===============================================================");
						   } 
							    	 if(jsonStr.get("prod_offer_heavy")!= null && jsonStr.get("prod_offer_heavy")!=""){
							    		 OuterItemgz.setSociety_price(Double.valueOf(getValue(jsonStr.get("prod_offer_heavy"))).doubleValue() );//代理商终端结算价格society_price-商品重量prod_offer_heavy
							    	 }else{
							    		 LackOfField +="缺少必填字段为：prod_offer_heavy.";
						   	    	  logger.info("订单信息同步接口prod_offer_heavy不能正常setDTO异常====必填项=============================================================");
						   } 
						                   /* 
											
											OuterItemgz.setPro_type(getValue(jsonStr.get("pro_type")));//产品类型
											OuterItemgz.setIsgifts(getValue(jsonStr.get("isgifts")));//是否赠品	字典：0否，1是
											OuterItemgz.setBrand_number(getValue(jsonStr.get("brand_number")));//品牌编码
											OuterItemgz.setBrand_name(getValue(jsonStr.get("brand_name")));//品牌名称
											
											OuterItemgz.setColor_code(getValue(jsonStr.get("color_code")));//颜色编码
											OuterItemgz.setColor_name(getValue(jsonStr.get("color_name")));//颜色名称
											OuterItemgz.setSpecification_code(getValue(jsonStr.get("specification_code")));//型号编码
											OuterItemgz.setSpecificatio_nname(getValue(jsonStr.get("specificatio_nname")));//型号名称
										
											OuterItemgz.setTerminal_num(getValue(jsonStr.get("terminal_num")));//终端串号
											
											
											
											OuterItemgz.setFirst_payment(getValue(jsonStr.get("first_payment")));//首月资费方式
											OuterItemgz.setCollection(getValue(jsonStr.get("collection")));//是否托收	字典：0否，1是
											OuterItemgz.setOut_package_id(getValue(jsonStr.get("out_package_id")));//合约编码
											
											OuterItemgz.setOut_plan_id_ess(getValue(jsonStr.get("out_plan_id_ess")));//总部套餐编码
											OuterItemgz.setOut_plan_id_bss(getValue(jsonStr.get("out_plan_id_bss")));//BSS套餐编码
											OuterItemgz.setReliefpres_flag(getValue(jsonStr.get("reliefpres_flag")));//减免预存标记
											OuterItemgz.setSimid(getValue(jsonStr.get("simid")));//SIM卡号
											OuterItemgz.setProduct_net(getValue(jsonStr.get("product_net")));//产品网别
											
											OuterItemgz.setPhone_deposit(getValue(jsonStr.get("phone_deposit")));//活动预存款
											
											OuterItemgz.setAdjustment_credit(getValue(jsonStr.get("adjustment_credit")));//调整信用度
											OuterItemgz.setSuperiors_bankcode(getValue(jsonStr.get("superiors_bankcode")));//上级银行编码
											OuterItemgz.setBank_code(getValue(jsonStr.get("bank_code")));//银行编码
											OuterItemgz.setBank_account(getValue(jsonStr.get("bank_account")));//银行账号
											OuterItemgz.setBank_user(getValue(jsonStr.get("bank_user")));//银行开户名
											OuterItemgz.setIs_iphone_plan(getValue(jsonStr.get("is_iphone_plan")));//是否iphone套餐  字典：0否，1是
											OuterItemgz.setIs_loves_phone(getValue(jsonStr.get("is_loves_phone")));//是否情侣号码 字典：0否，1是 --默认0
											OuterItemgz.setIs_liang(getValue(jsonStr.get("is_liang")));//是否靓号
											
											OuterItemgz.setLiang_code(getValue(jsonStr.get("liang_code")));//靓号单编号
											OuterItemgz.setIs_stock_phone(getValue(jsonStr.get("is_stock_phone")));//是否库存机
											
											OuterItemgz.setDisfee_select(getValue(jsonStr.get("disfee_select")));//优惠选项
											OuterItemgz.setSociety_name(getValue(jsonStr.get("society_name")));//社会代理商名称
																
											//OuterItemgz.setPro_detail_code(getValue(jsonStr);
											OuterItemgz.setGoods_id(getValue(jsonStr.get("goods_id")));//订单ID
											 */
							    	 packageGoodsParam(OuterItemgz, param);
							    	 logger.info("OuterItemgz.getProduct_id() == "+OuterItemgz.getProduct_id());
							    	 itemList.add(OuterItemgz); 
										
										
									} else {
										OuterError ng = new OuterError(null, outer_sku_id, outer_sku_id, null, "wsc", jsonObject_order_req.get("order_id").toString(), "sysdate","goodserror");//noparam  nogoods
										ng.setDeal_flag("-1");
										outterECSTmplManager.insertOuterError(ng);
										logger.info("电商没有配置商品=====");
										outJson = "{\"order_resp\": {\"resp_code\": \"1\",\"resp_msg\": \"同步失败，原因为：电商没有配置商品\"}}"; 
										return outJson;
									}
			        	    	}
			        	    }else{
			        	    	LackOfField +="缺少必填字段为：prod_offer_code.";
			      	    	  logger.info("订单信息同步接口prod_offer_code不能正常setDTO异常====必填项=============================================================");
			      } 
			      	      
		  }
		 
		  logger.info("----------------进入List for取值---AAAAA结束");
		  if(LackOfField == "" ){
			  outJson = "{\"order_resp\": {\"resp_code\": \"0\",\"resp_msg\": \"同步成功\"}}"; 
		  }else{
			  outJson = "{\"order_resp\": {\"resp_code\": \"1\",\"resp_msg\": \"同步失败，原因为："+LackOfField+" \"}}"; 
		  }
//		  logger.info("1.9订单同步信息===BBBBBBBBBBBBBBB==Invoice_title="+((Outer) out_order).getInvoice_title()) ;
//		  logger.info("1.9订单同步信息===List裡面字段測試==Pay_time="+OuterItemgz.getProduct_id().toString()); 
		 // 下面部分是输出部分的处理  
	      

		    OrderStandardPushReq req = new OrderStandardPushReq();
			req.setOuterList(out_order_List);
			logger.info("************************out_order_List**********************************Product_id"+out_order_List.get(0).getItemList().get(0).getProduct_id());
			logger.info("************************out_order_List**********************************out_order_List"+out_order_List.size());
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			OrderStandardPushResp resp = client.execute(req, OrderStandardPushResp.class);
			String out_tid = out_order_List.get(0).getOut_tid();
			if ("0".equals(resp.getError_code())) {
				 outJson = "{\"order_resp\": {\"resp_code\": \"0\",\"resp_msg\": \""+jsonObject_order_req.get("order_id").toString()+"同步成功\"}}";				
			 }else {
				 OuterError ng = new OuterError(null, outer_sku_id, outer_sku_id, null, "wsc", out_tid, "sysdate",resp.getError_msg());//noparam  nogoods
				 ng.setDeal_flag("-1");
				 outterECSTmplManager.insertOuterError(ng);
				 outJson = "{\"order_resp\": {\"resp_code\": \"1\",\"resp_msg\": \""+jsonObject_order_req.get("order_id").toString()+"同步失败."+resp.getError_msg()+"\"}}";
			} 
			logger.info("订单["+out_order_List.get(0).getOut_tid()+"]-----------调用API返回-------------------------------------------------API返回resp="+resp.getError_msg());
		 logger.info("=============结束");
	      logger.info("1.9订单同步信息===CCCCCCCCCCCCCCCCCCCC==outJson="+outJson);
	      
	     // respose.getWriter().print(outJson);
		  return outJson;
	    } catch(Exception e){	
	        outJson = "{\"order_resp\": {\"resp_code\": \"1\",\"resp_msg\": \"同步失败:"+e.getMessage()+"\"}}";
	    	logger.info("1.9订单同步信息,订单["+out_order_List.get(0).getOut_tid()+"]=======操作接口失败=========================================");
	        return outJson;
	    } 
	}
	//保存接口交互报文
	private static void saveXml(Map inputMap,String xmlRequest,Map resultMap,long id,long startTime,long serviceTime,String errorMsg) throws Exception{

	}
	// 获得请求的报文,并作简单的校验  
	public static String getInJson(HttpServletRequest request) throws IOException {  

	    byte buffer[] = new byte[10 * 1024 * 1024];  
	    InputStream in = request.getInputStream();// 获取输入流对象  

	    int len = in.read(buffer);  
	    // 必须对数组长度进行判断，否则在new byte[len]会报NegativeArraySizeException异常  
	    if (len < 0) {  
	        throw new IOException("请求报文为空");  
	    }  

	    String encode = request.getCharacterEncoding();// 获取请求头编码  
	    // 必须对编码进行校验,否则在new String(data, encode);会报空指针异常  
	    if (null == encode || encode.trim().length() < 0) {  
	        throw new IOException("请求报文未指明请求编码");  
	    }  

	    byte data[] = new byte[len];  

	    // 把buffer数组的值复制到data数组  
	    System.arraycopy(buffer, 0, data, 0, len);  

	    // 通过使用指定的 charset 解码指定的 byte 数组，构造一个新的 String  
	    String inJson = new String(data, encode);  

	    return inJson;  
	}  

	// 不提供get的处理方式  
	protected void doGet(HttpServletRequest request,  
	        HttpServletResponse response) throws ServletException, IOException {  
	}  
	private static String getValue(Object value){
		String val = "";
		if(value!=null){
			val = value.toString();
		}
        return val;
	}
	
	private String getValue(Map order,String key){
	    
		String value = "";
		Object obj = order.get(key);
		if(obj != null){
			value = obj.toString();
		}
		return value;
		
	}
	
}
