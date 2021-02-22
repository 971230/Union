package com.ztesoft.net.server;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderExtInfoGetReq;
import zte.params.order.req.TaobaoOrderSyncReq;
import zte.params.order.resp.OrderExtInfoGetResp;
import zte.params.order.resp.OrderTaobaoSyncResp;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.OrderItemAddAccept;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.EcsXmlUtil;

/**pay_mothed
 * 订单获取接口
 * @author rock
 *
 */
public class OrderGetServlet extends HttpServlet{
	
	private static final Logger logger = Logger.getLogger(OrderGetServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String date_type = request.getParameter("date_type");
		String begin_time = StringUtil.toUTF8(request.getParameter("begin_time"));
		String end_time = StringUtil.toUTF8(request.getParameter("end_time"));
		String page_no = request.getParameter("page_no");
		String page_size = request.getParameter("page_size");

		
		if(StringUtils.isEmpty(page_no)){
			page_no = "1";
		}
		if(StringUtils.isEmpty(page_size)){
			page_size = "200";
		}
		try {
			begin_time = URLDecoder.decode(begin_time, "UTF-8");
			end_time = URLDecoder.decode(end_time, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		TaobaoOrderSyncReq orderReq = new TaobaoOrderSyncReq();
		orderReq.setBegin_time(begin_time);
		orderReq.setEnd_time(end_time);
		orderReq.setPage_no(Integer.parseInt(page_no));
		orderReq.setPage_size(Integer.parseInt(page_size));
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
		IOrderServices mananger = (IOrderServices) context.getBean("orderServices"); 
		//获取订单数据，返回OrderTaobaoSyncResp类型的对象，该对象保存了订单信息
		OrderTaobaoSyncResp orderResp =  mananger.taobaoOrderSync(orderReq);
		
		//将订单信息转化成xml格式的字符串
		String xmlStr = this.getXmlStr(orderResp);
		xmlStr = xmlStr.replaceAll("&", "");
		logger.info(xmlStr);
		//发送报文
		this.responseDocument(response, request, xmlStr);

	}
	
	protected static IOrderServiceLocal orderServiceLocal;
	protected static IOrderServices orderServices;

	public String getXmlStr(OrderTaobaoSyncResp orderResp){
		List<Order> list = orderResp.getOrderSyncList();
		Integer totalresults = list.size();
		Long totalresultsall = orderResp.getTotalCount();
		orderServiceLocal = SpringContextHolder.getBean("orderServiceLocal");
		orderServices = SpringContextHolder.getBean("orderServices");

		StringBuffer sb = new StringBuffer("<items>");
		sb.append("<totalresults>"+totalresults+"</totalresults>");
		sb.append("<totalResultsAll>"+totalresultsall+"</totalResultsAll>");
		for(int i=0;i<totalresults;i++){
			Order order = list.get(i);
			List<OrderItemAddAccept> orderItemAcceptList = order.getOrderItemAcceptList();
			OrderItemAddAccept accept = new OrderItemAddAccept();
			if(orderItemAcceptList != null && orderItemAcceptList.size()>0){
				accept = orderItemAcceptList.get(0);
			}
			List<OrderItem> orderItemList = order.getOrderItemList();
			OrderItem item = new OrderItem();
			if(orderItemList!=null && orderItemList.size()>0){
				item = orderItemList.get(0);
			}
			
			String baidu_begin_time = "";
			String baidu_end_time = "";
			String invoice_title = "";
			try {
				Map m = orderServiceLocal.queryMap(" select a.reserve0,a.reserve5,a.reserve9 from es_outer_accept a WHERE a.order_id = ? AND ROWNUM < 2 ", order.getOrder_id());
				if(m.get("reserve5") != null)
				baidu_begin_time = m.get("reserve5").toString();
				if(m.get("reserve9") != null)
				baidu_end_time = m.get("reserve9").toString();
				if(m.get("reserve0") != null)
				invoice_title = m.get("reserve0").toString();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			                                              
			sb.append("<Rows>");
				sb.append("<tid>"+getValue(order.getOrder_id())+"</tid>");                              
				sb.append("<out_tid>"+getValue(accept.getOut_tid())+"</out_tid>");
				sb.append("<order_channel>"+getValue(accept.getOrder_channel())+"</order_channel>");
				sb.append("<order_from>"+getValue(accept.getOrder_from())+"</order_from>");
				sb.append("<type>"+getValue(order.getOrderType())+"</type>");
				sb.append("<status>"+getValue(order.getStatus())+"</status>");
				sb.append("<abnormal_status>"+getValue(accept.getAbnormal_status())+"</abnormal_status>");
				sb.append("<merge_status>"+getValue(accept.getMerge_status())+"</merge_status>");
				sb.append("<delivery_status>"+getValue(accept.getDelivery_status())+"</delivery_status>");
				sb.append("<platform_status>"+getValue(accept.getPlatform_status())+"</platform_status>");
				sb.append("<plat_type>"+getValue(accept.getPlat_type())+"</plat_type>");
				sb.append("<pro_totalfee>"+getValue(order.getGoods_amount())+"</pro_totalfee>");
				sb.append("<order_totalfee>"+getValue(accept.getOrder_realfee())+"</order_totalfee>");
				sb.append("<tid_time>"+getValue(accept.getTid_time())+"</tid_time>");
				
//				sb.append("<pay_time>"+getValue(accept.getPay_time())+"</pay_time>");
				//付款时间格式调整
				if(accept.getPay_time() != null && !"".equalsIgnoreCase(accept.getPay_time()) 
						&& accept.getPay_time().indexOf("-") < 0){
				    	String timea = getValue(accept.getPay_time());
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
				        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				        String timesb = null;  
				        try {
							timesb = sdf2.format(sdf.parse(timea));
							 sb.append("<pay_time>"+timesb+"</pay_time>"); 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				       
			    }
			    else{
			    	sb.append("<pay_time>"+getValue(accept.getPay_time())+"</pay_time>"); 
			    }
				
				sb.append("<get_time>"+getValue(order.getCreate_time())+"</get_time>");
				sb.append("<buyer_message>"+getValue(order.getRemark())+"</buyer_message>");
				sb.append("<service_remarks>"+getValue(accept.getService_remarks())+"</service_remarks>");
				if(accept.getOrder_provinc_code() == null || "".equalsIgnoreCase(accept.getOrder_provinc_code())){
					sb.append("<order_provinc_code>"+getValue(accept.getProvince())+"</order_provinc_code>");
				}else{
					sb.append("<order_provinc_code>"+getValue(accept.getOrder_provinc_code())+"</order_provinc_code>");
				}
				
				if(accept.getOrder_city_code() == null || "".equalsIgnoreCase(accept.getOrder_city_code())){
					sb.append("<order_city_code>"+getValue(accept.getCity())+"</order_city_code>");
				}else{
					sb.append("<order_city_code>"+getValue(accept.getOrder_city_code())+"</order_city_code>");
				}
				
				
				sb.append("<order_disfee>"+getValue(accept.getOrder_disfee())+"</order_disfee>");
				sb.append("<is_adv_sale>"+getValue(accept.getIs_adv_sale())+"</is_adv_sale>");
				sb.append("<plat_distributor_code>"+getValue(accept.getPlat_distributor_code())+"</plat_distributor_code>");
				sb.append("<alipay_id>"+getValue(accept.getAlipay_id())+"</alipay_id>");
				
				//付款方式  大写转小写
				if(accept.getPay_mothed().equals("YJHK") || accept.getPay_mothed()=="YJHK"){
					sb.append("<pay_mothed>"+"yj"+"</pay_mothed>");
				}
				else if(accept.getPay_mothed().equals("XJZF") || accept.getPay_mothed()=="XJZF"){
					sb.append("<pay_mothed>"+"xianjin"+"</pay_mothed>");
				}
				else if(accept.getPay_mothed().equals("HDFK") || accept.getPay_mothed()=="HDFK"){
					sb.append("<pay_mothed>"+"hdfk"+"</pay_mothed>");
				}
				else if(accept.getPay_mothed().equals("YLYF") || accept.getPay_mothed()=="YLYF" ||accept.getPay_mothed().equals("ZHXC")){
					sb.append("<pay_mothed>"+"cash"+"</pay_mothed>");
				}
				else if(accept.getPay_mothed().equals("ZFB") || accept.getPay_mothed()=="ZFB"){
					sb.append("<pay_mothed>"+"alipay"+"</pay_mothed>");
				}
				else if(accept.getPay_mothed().equals("YB") || accept.getPay_mothed()=="YB"){
					sb.append("<pay_mothed>"+"yibao"+"</pay_mothed>");
				}
				else{
				sb.append("<pay_mothed>"+getValue(accept.getPay_mothed().toLowerCase())+"</pay_mothed>");
				}
				
				sb.append("<pay_status>"+getPayStatusName(accept.getPlatform_status())+"</pay_status>");
				sb.append("<order_origfee>"+getValue(accept.getOrder_realfee())+"</order_origfee>");
				sb.append("<order_realfee>"+getValue(accept.getOrder_realfee())+"</order_realfee>");
				sb.append("<buyer_id>"+getValue(accept.getBuyer_id())+"</buyer_id>");
				sb.append("<buyer_name>"+getValue(accept.getBuyer_name())+"</buyer_name>");
				sb.append("<receiver_name>"+getValue(order.getShip_name())+"</receiver_name>");
				sb.append("<receiver_mobile>"+getValue(order.getShip_mobile())+"</receiver_mobile>");
				sb.append("<phone>"+getValue(order.getShip_tel())+"</phone>");
				sb.append("<province>"+getValue(accept.getProvince())+"</province>");
				sb.append("<city>"+getValue(accept.getCity())+"</city>");
				sb.append("<district>"+getValue(accept.getDistrict())+"</district>");
				if("淘宝".equalsIgnoreCase(getValue(accept.getOrder_from()))){
					sb.append("<address>"+getValue(accept.getProvince())+" "+getValue(accept.getCity())+" "+getValue(accept.getDistrict())+" "+getValue(order.getShip_addr())+"</address>");
					
				}else{
					sb.append("<address>"+getValue(order.getShip_addr())+"</address>");
					
				}
				String order_from = MallUtils.getOrderFromId(accept.getOrder_from());
//		    	判断发展人编码是否为空，为空则根据订单来源和订单归属地址从es_development_code(来自现有商城发展人信息表.xlsx)表中关联
		    	String development_code = accept.getDevelopment_code();
		    	if(null == development_code || "".equals(development_code)){
		    		development_code = orderServiceLocal.queryString("select development_code from es_development_code where area_code = '"+accept.getCity_code()+"' and source_from_Id = '"+order_from+"' and rownum < 2");
		    	}
		    	String development_name = accept.getDevelopment_name();
		    	if(null == development_name ||"".equals(development_name)){
		    		development_name = development_code;
		    	}
				
				sb.append("<post>"+getValue(order.getShip_zip())+"</post>");
				sb.append("<provinc_code>"+getValue(accept.getProvinc_code())+"</provinc_code>");
				sb.append("<city_code>"+getValue(accept.getCity_code())+"</city_code>");
				sb.append("<area_code>"+getValue(accept.getArea_code())+"</area_code>");
				sb.append("<sending_type>"+getValue(accept.getSending_type())+"</sending_type>");
				sb.append("<is_bill>"+getValue(accept.getIs_bill())+"</is_bill>");
				sb.append("<invoice_title>"+getValue(invoice_title)+"</invoice_title>");
				sb.append("<invoice_print_type>"+getValue(accept.getInvoice_print_type())+"</invoice_print_type>");
				sb.append("<recommended_name>"+getValue(accept.getRecommended_name())+"</recommended_name>");
				sb.append("<recommended_code>"+getValue(accept.getRecommended_code())+"</recommended_code>");
				sb.append("<recommended_phone>"+getValue(accept.getRecommended_phone())+"</recommended_phone>");
				sb.append("<development_code>"+development_code+"</development_code>");
				sb.append("<development_name>"+development_name+"</development_name>");
				sb.append("<file_return_type>"+getValue(accept.getFile_return_type())+"</file_return_type>");
				
				String tid_category = "";
				if(accept.getTid_category() == null || "".equalsIgnoreCase(accept.getTid_category())){
					try {
						tid_category = orderServiceLocal.queryString(" select a.tid_category from inf_tid_category a WHERE a.cat_id = '"+accept.getPro_type()+"' ");

					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}else{
					tid_category = accept.getTid_category();
				}
				
				
				sb.append("<tid_category>"+getValue(tid_category)+"</tid_category>");
				sb.append("<collection_free>"+getValue(accept.getCollection_free())+"</collection_free>");
				sb.append("<vouchers_money>"+getValue(accept.getVouchers_money())+"</vouchers_money>");
				sb.append("<vouchers_code>"+getValue(accept.getVouchers_money())+"</vouchers_code>");
				sb.append("<disfee_type>"+getValue(accept.getDisfee_type())+"</disfee_type>");
				sb.append("<promotion_area>"+getValue(accept.getPromotion_area())+"</promotion_area>");
				sb.append("<one_agents_id>"+getValue(accept.getOne_agents_id())+"</one_agents_id>");
				sb.append("<two_agents_id>"+getValue(accept.getTwo_agents_id())+"</two_agents_id>");
				sb.append("<wm_order_from>"+getValue(accept.getWm_order_from())+"</wm_order_from>");
				sb.append("<wt_paipai_order_id>"+getValue(accept.getWt_paipai_order_id())+"</wt_paipai_order_id>");
				sb.append("<CouponName>"+getValue(accept.getCouponname())+"</CouponName>");
				sb.append("<CouponBatchId>"+getValue(accept.getCouponbatchid())+"</CouponBatchId>");
				sb.append("<OrderAccType>"+getValue(accept.getOrderacctype())+"</OrderAccType>");
				sb.append("<OrderAccCode>"+getValue(accept.getOrderacccode())+"</OrderAccCode>");
				sb.append("<PayType>"+getValue(accept.getPaytype())+"</PayType>");
				sb.append("<PayFinTime>"+getValue(accept.getPayfintime())+"</PayFinTime>");
				sb.append("<PayPlatFormOrderId>"+getValue(accept.getPayplatformorderid())+"</PayPlatFormOrderId>");
				sb.append("<PayProviderId>"+getValue(accept.getPayproviderid())+"</PayProviderId>");
				sb.append("<PayProviderName>"+getValue(accept.getPayprovidername())+"</PayProviderName>");
				sb.append("<PayChannelId>"+getValue(accept.getPaychannelid())+"</PayChannelId>");
				sb.append("<PayChannelName>"+getValue(accept.getPaychannelname())+"</PayChannelName>");
				sb.append("<DiscountName>"+getValue(accept.getDiscountname())+"</DiscountName>");
				sb.append("<DiscountID>"+getValue(accept.getDiscountid())+"</DiscountID>");
				sb.append("<DiscountRange>"+getValue(accept.getDiscountrange())+"</DiscountRange>");
				sb.append("<DiscountValue>"+getValue(accept.getDiscountvalue())+"</DiscountValue>");
				sb.append("<bss_order_type>"+getValue(accept.getBss_order_type())+"</bss_order_type>");
				sb.append("<wm_isreservation_from>"+getValue(accept.getWm_isreservation_from())+"</wm_isreservation_from>");
				sb.append("<chanel_id>"+getValue(accept.getPaychannelid())+"</chanel_id>");
				sb.append("<baidu_account>"+getValue(accept.getBaidu_id())+"</baidu_account>");
				sb.append("<baidu_no>"+getValue(accept.getFreeze_tran_no())+"</baidu_no>");
				sb.append("<baidu_money>"+getValue(accept.getFreeze_free())+"</baidu_money>");

				sb.append("<baidu_begin_time>"+getValue(baidu_begin_time)+"</baidu_begin_time>");
				sb.append("<baidu_end_time>"+getValue(baidu_end_time)+"</baidu_end_time>");
//				sb.append("<order_type>"+getValue(accept.getPro_type())+"</order_type>");
				sb.append("<tid_item>");
					sb.append("<Item>");
					sb.append("<pro_detail_code>"+getValue(accept.getPro_detail_code())+"</pro_detail_code>");
					sb.append("<pro_name>"+getValue(accept.getPro_name())+"</pro_name>");
					sb.append("<pro_type>"+getValue(accept.getPro_type())+"</pro_type>");
					sb.append("<isgifts>"+getValue(accept.getIsgifts())+"</isgifts>");
					sb.append("<pro_num>"+getValue(item.getNum())+"</pro_num>");
					sb.append("<sell_price>"+getValue(accept.getSell_price())+"</sell_price>");
					sb.append("<pro_origfee>"+getValue(accept.getPro_origfee())+"</pro_origfee>");
					sb.append("<brand_number>"+getValue(accept.getBrand_number())+"</brand_number>");
					sb.append("<brand_name>"+getValue(accept.getBrand_name())+"</brand_name>");
					sb.append("<color_code>"+getValue(accept.getColor_code())+"</color_code>");
					sb.append("<color_name>"+getValue(accept.getColor_name())+"</color_name>");
					sb.append("<specification_code>"+getValue(accept.getSpecification_code())+"</specification_code>");
					sb.append("<specificatio_nname>"+getValue(accept.getSpecificatio_nname())+"</specificatio_nname>");
					sb.append("<model_code>"+getValue(accept.getModel_code())+"</model_code>");
					sb.append("<model_name>"+getValue(accept.getModel_name())+"</model_name>");
					sb.append("<terminal_num>"+getValue(accept.getTerminal_num())+"</terminal_num>");
					sb.append("<phone_num>"+getValue(accept.getPhone_num())+"</phone_num>");
					sb.append("<phone_owner_name>"+getValue(accept.getPhone_owner_name())+"</phone_owner_name>");
					sb.append("<cert_type>"+getValue(accept.getCerti_type())+"</cert_type>");
					sb.append("<cert_card_num>"+getValue(accept.getCerti_number())+"</cert_card_num>");
					sb.append("<cert_failure_time>"+getValue(accept.getCert_failure_time())+"</cert_failure_time>");
					sb.append("<cert_address>"+getValue(accept.getCert_address())+"</cert_address>");
					String white_cart_type = "";
					try{
						Map map =orderServiceLocal.queryMap(" select a.params from v_product_phone_params a where a.a_goods_id = ? and rownum < 2", order.getGoods_id());
						if(map != null){
						String strpar = map.get("params").toString();
						strpar = strpar.substring(1, strpar.lastIndexOf("]"));
						ParamList pl = JsonUtil.fromJson(strpar, ParamList.class);
						
						if (pl.getParamList().size() > 0) {
							for (int pi = 0; pi < pl.getParamList().size(); pi++) {
								GoodsVo tmp = pl.getParamList().get(pi);
								if("white_cart_type".equalsIgnoreCase(tmp.getEname())){
									if("0".equalsIgnoreCase(tmp.getValue())){
										white_cart_type = "NANO卡";
									}else if("1".equalsIgnoreCase(tmp.getValue())){
										white_cart_type = "大卡";
									}else if("2".equalsIgnoreCase(tmp.getValue())){
										white_cart_type = "小卡";
									}else{
										white_cart_type = tmp.getValue();
									}
									
								}
							}
						}
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
					sb.append("<white_cart_type>"+getValue(white_cart_type)+"</white_cart_type>");
					sb.append("<first_payment>"+getValue(accept.getFirst_payment())+"</first_payment>");
					sb.append("<collection>"+getValue(accept.getCollection())+"</collection>");
					sb.append("<out_package_id>"+getValue(accept.getOut_package_id())+"</out_package_id>");
					sb.append("<plan_title>"+getValue(accept.getPlan_title())+"</plan_title>");
					ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
//					IOrderServiceLocal osl = (IOrderServiceLocal) context.getBean("orderServiceLocal"); 
					
					String out_plan_id_ess = null;
					String out_plan_id_bss = null;
					
						try{
							Map map =orderServiceLocal.queryMap(" select a.params from v_product_params a where a.a_goods_id = ? and rownum < 2", order.getGoods_id());
							if(map != null){
							String strpar = map.get("params").toString();
							strpar = strpar.substring(1, strpar.lastIndexOf("]"));
							ParamList pl = JsonUtil.fromJson(strpar, ParamList.class);
							
							if (pl.getParamList().size() > 0) {
								for (int pi = 0; pi < pl.getParamList().size(); pi++) {
									GoodsVo tmp = pl.getParamList().get(pi);
									if("bss_code".equalsIgnoreCase(tmp.getEname())){
										out_plan_id_bss = tmp.getValue();
									}else if("ess_code".equalsIgnoreCase(tmp.getEname())){
										out_plan_id_ess = tmp.getValue();
									}
								}
							}
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					
					sb.append("<out_plan_id_ess>"+out_plan_id_bss+"</out_plan_id_ess>");
					sb.append("<out_plan_id_bss>"+out_plan_id_ess+"</out_plan_id_bss>");
					String bizpackage = "";
					try {
						OrderExtInfoGetReq orderExtReq = new OrderExtInfoGetReq();
						orderExtReq.setOrder_id(order.getOrder_id());
						OrderExtInfoGetResp orderExtResp = orderServices.getOrderExtInfo(orderExtReq);
						if(orderExtResp != null && "0".equalsIgnoreCase(orderExtResp.getError_code()) ){
							Map orderExtMap = orderExtResp.getExtMap();
							if(orderExtMap != null){
								bizpackage = orderExtMap.get("customerization_offer_str").toString();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					
					
					sb.append("<bizpackage>"+getValue(bizpackage)+"</bizpackage>");
					
					sb.append("<reliefpres_flag>"+getValue(accept.getReliefpres_flag())+"</reliefpres_flag>");
					sb.append("<simid>"+getValue(accept.getSimid())+"</simid>");
					sb.append("<product_net>"+getValue(accept.getProduct_net())+"</product_net>");
					String act_protper = "";
					try{
						Map map =orderServiceLocal.queryMap(" select a.params from v_product_plan_params a where a.a_goods_id = ? and rownum < 2", order.getGoods_id());
						if(map != null){
						String strpar = map.get("params").toString();
						strpar = strpar.substring(1, strpar.lastIndexOf("]"));
						ParamList pl = JsonUtil.fromJson(strpar, ParamList.class);
						
						if (pl.getParamList().size() > 0) {
							for (int pi = 0; pi < pl.getParamList().size(); pi++) {
								GoodsVo tmp = pl.getParamList().get(pi);
								if("package_limit".equalsIgnoreCase(tmp.getEname())){
									act_protper = tmp.getValue();
								}
							}
						}
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					sb.append("<act_protper>"+getValue(act_protper)+"</act_protper>");
					sb.append("<phone_deposit>"+getValue(accept.getPhone_deposit())+"</phone_deposit>");
					sb.append("<famliy_num>"+getValue(accept.getFamliy_num())+"</famliy_num>");
					sb.append("<adjustment_credit>"+getValue(accept.getAdjustment_credit())+"</adjustment_credit>");
					sb.append("<superiors_bankcode>"+getValue(accept.getSuperiors_bankcode())+"</superiors_bankcode>");
					sb.append("<bank_code>"+getValue(accept.getBank_code())+"</bank_code>");
					sb.append("<bank_account>"+getValue(accept.getBank_account())+"</bank_account>");
					sb.append("<bank_user>"+getValue(accept.getBank_user())+"</bank_user>");
					if("".equalsIgnoreCase(getValue(accept.getIs_iphone_plan()))){
						sb.append("<is_iphone_plan>0</is_iphone_plan>");
					}else{
						sb.append("<is_iphone_plan>"+getValue(accept.getIs_iphone_plan())+"</is_iphone_plan>");
					}
					sb.append("<is_loves_phone>"+getValue(accept.getIs_loves_phone())+"</is_loves_phone>");
					sb.append("<loves_phone_num>"+getValue(accept.getLoves_phone_num())+"</loves_phone_num>");
					sb.append("<is_liang>"+getValue(accept.getIs_liang())+"</is_liang>");
					sb.append("<liang_price>"+getValue(accept.getLiang_price())+"</liang_price>");
					sb.append("<liang_code>"+getValue(accept.getLiang_code())+"</liang_code>");
					sb.append("<is_stock_phone>"+getValue(accept.getIs_stock_phone())+"</is_stock_phone>");
					sb.append("<ative_type>"+getValue(accept.getAtive_type())+"</ative_type>");
					sb.append("<disfee_select>"+getValue(accept.getDisfee_select())+"</disfee_select>");
					sb.append("<society_name>"+getValue(accept.getSociety_name())+"</society_name>");
					sb.append("<society_price>"+getValue(accept.getSociety_price())+"</society_price>");
					
					
					//受理单JSON格式
					StringBuffer jsonStr=new StringBuffer();
					String mainContentOne_1 = "";
					String mainContentOne_2 = "";
					String taocan_name = "";
					String deposit_fee = "";
					String order_return ="";
					String mon_return = "";
					String all_give = "";
	    	    	String sms_send_num = "";
					String answering_free = "";
					String call_times = "";
					String flow = "";
					String visual_phone = "";
					String other_business = "";
					String out_flow = "";
	    	    	String pay_type = "";
	    	    	
	    	    	taocan_name = orderServiceLocal.queryString("select c.name from (select a.source_from,b.name,a.a_goods_id from es_goods_rel a join es_goods b on b.goods_id = a.z_goods_id and b.type_id = 10002) c where c.a_goods_id = '"+order.getGoods_id()+"'");
	    	    	if(taocan_name !=null&&!"".equalsIgnoreCase(taocan_name)){
	    	    		mainContentOne_1 ="1、  套餐名称："+ taocan_name+"，详见业务协议；";
	    	    	}else {
	    	    		mainContentOne_1 = "无套餐信息";
	    	    	}
	    	    	
	    	    	try{
	    				Map map =orderServiceLocal.queryMap(" select a.params from v_product_params a where a.a_goods_id = ? and rownum < 2", order.getGoods_id());
	    				if(map != null){
	    				String strpar = map.get("params").toString();
	    				strpar = strpar.substring(1, strpar.lastIndexOf("]"));
	    				ParamList pl = JsonUtil.fromJson(strpar, ParamList.class);
	    				if (pl.getParamList().size() > 0) {
	    					for (int pi = 0; pi < pl.getParamList().size(); pi++) {
	    						GoodsVo tmp = pl.getParamList().get(pi);
	    						if("deposit_fee".equalsIgnoreCase(getValue(tmp.getEname()))){//预存金额
	    							deposit_fee = "预存金额"+tmp.getValue()+",";
	    						}else if("order_return".equalsIgnoreCase(getValue(tmp.getEname()))){//首月返还
	    							order_return = "首月返还" + tmp.getValue()+",";
	    						}else if("mon_return".equalsIgnoreCase(getValue(tmp.getEname()))){//分月返还
	    							mon_return = "分月返还" + tmp.getValue()+",";
	    						}else if("all_give".equalsIgnoreCase(getValue(tmp.getEname()))){//分月返还
	    							all_give = "总送费金额" + tmp.getValue()+",";
	    						}else if("sms_send_num".equalsIgnoreCase(getValue(tmp.getEname()))){//国内短信发送条数
	    							 sms_send_num = "国内短信发送条数" + tmp.getValue()+",";
	    						}else if("answering_free".equalsIgnoreCase(getValue(tmp.getEname()))){//接听免费
	    							 answering_free = tmp.getValue()+"接听免费,";
	    						}else if("call_times".equalsIgnoreCase(getValue(tmp.getEname()))){//国内语音拨打分钟数
	    							 call_times = "国内语音拨打"+tmp.getValue()+"分钟,";
	    						}else if("flow".equalsIgnoreCase(getValue(tmp.getEname()))){//国内流量
	    							 flow = "国内流量"+tmp.getValue()+",";
	    						}else if("visual_phone".equalsIgnoreCase(getValue(tmp.getEname()))){//可视电话
	    							 visual_phone = "可视电话"+tmp.getValue()+"分钟,";
	    						}else if("other_business".equalsIgnoreCase(getValue(tmp.getEname()))){//国内流量
	    							 other_business = "赠送增值业务"+tmp.getValue()+",";
	    						}else if("out_flow".equalsIgnoreCase(getValue(tmp.getEname()))){//国内流量
	    							 out_flow = "套餐外流量费用"+tmp.getValue()+"元/分钟";
	       						}else if("pay_type".equalsIgnoreCase(getValue(tmp.getEname()))){//国内流量
	       							if( "BAK".equalsIgnoreCase(getValue(tmp.getEname()))){
	       								pay_type = "后付费";
	       							}else{
	       								pay_type = "预付费";
	       							}
	       							
	       						}
	    					}
	    				}
	    				}
	    			}catch(Exception ex){
	    				ex.printStackTrace();
	    			}
	    	    	
	    	    	if("".equalsIgnoreCase(deposit_fee)&&"".equalsIgnoreCase(order_return)
	    	    			&&"".equalsIgnoreCase(mon_return)&&"".equalsIgnoreCase(all_give)&&"".equalsIgnoreCase(sms_send_num)
	    	    			&&"".equalsIgnoreCase(answering_free)&&"".equalsIgnoreCase(call_times)
	    	    			&&"".equalsIgnoreCase(flow)&&"".equalsIgnoreCase(visual_phone)
	    	    			&&"".equalsIgnoreCase(other_business)&&"".equalsIgnoreCase(out_flow)){
	    	    		mainContentOne_2= deposit_fee+order_return+mon_return+all_give+sms_send_num
	    	    				+answering_free+call_times+flow+visual_phone+other_business+out_flow;}
	    	    	else{
	    	    		mainContentOne_2 = "2、"+deposit_fee+order_return+mon_return+all_give+sms_send_num
	    	    				+answering_free+call_times+flow+visual_phone+other_business+out_flow;
	    	    		}
	    	    	
	              	jsonStr.append("{\"mainContentOne\":").append("\"").append(mainContentOne_1).append(mainContentOne_2).append("\","); 
	    	    	jsonStr.append("\"AcceptanceMode\":").append("\"").append("0").append("\",");
	    	    	jsonStr.append("\"contactAddr\":").append("\"").append(getValue(order.getShip_addr())).append("\",");
	    	    	
	    	    	String ywmcms = "";
	    	    	String str_goods_type = orderServiceLocal.queryString("select t.type_id from es_goods t where t.goods_id = '"+order.getGoods_id()+"'");
	    	    	if("20000".equalsIgnoreCase(str_goods_type)){  //号卡
	    	    		ywmcms = "国内通话";
	    	    	}else if("20001".equalsIgnoreCase(str_goods_type)){   //上网卡
	    	    		try {
	    					Map online_time_m = orderServiceLocal.queryMap("select a.params from es_gdlt_v_goods_swk a where a.order_id = '"+order.getOrder_id()+"'");
	    					if(online_time_m != null){
	    						String online_time_s = online_time_m.get("params").toString();
	    						online_time_s = online_time_s.substring(1,online_time_s.lastIndexOf("]"));
	    						JSONObject a1=JSONObject.fromObject(online_time_s);
	    						JSONArray aa1= a1.getJSONArray("paramList");
	    						
	    						for(int s=0;s<aa1.size();s++){
	    							JSONObject jsonobj = (JSONObject) aa1.get(s);
	    							String attrcode = jsonobj.getString("attrcode");
	    							String value = jsonobj.getString("value");
	    							if(attrcode != null && !"".equalsIgnoreCase(attrcode)){
	    								String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = '"+attrcode +"' and rownum < 2 ";
	    								   try {
	    								    Map tmpMapdc=  orderServiceLocal.queryMap(strdc);
	    									String dc_sql =  tmpMapdc.get("dc_sql").toString();
	    									String dca = dc_sql.replaceAll("'", "");
//	    									String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ='"+value+"' and rownum < 2 ";
	    									String str_yanshe="select value_desc from (select 'ECS' source_from, e.* from ("+dca+") e)T where T.VALUE ='"+value+"' and rownum < 2 ";
	    									String tmpMap_value =  orderServiceLocal.queryString(str_yanshe);
	    									value = tmpMap_value;
	    								} catch (Exception e) {
	    									e.printStackTrace();
	    								} 
	    							}
	    							if("IS_GROUP".equalsIgnoreCase(attrcode)){
	    								if("1".equalsIgnoreCase(value)){
	    									ywmcms = "国内上网卡";
	    								}else if("0".equalsIgnoreCase(value)){
	    									ywmcms = "本地上网卡";
	    								}
	    							}
	    						}
	    					}
	    					
	    				} catch (Exception e) {
	    					// TODO: handle exception
	    				}
	    	    	}else if("20002".equalsIgnoreCase(str_goods_type)){   //合约机
	    	    		ywmcms = "合约机";
	    	    	}else if("20003".equalsIgnoreCase(str_goods_type)){   //裸机
	    	    		ywmcms = "裸终端";
	    	    	}
	    	    	String mainContentTwo_1 = "";
	    	    	if(mainContentTwo_1 !=null&&!"".equalsIgnoreCase(mainContentTwo_1)){
	    	    		mainContentTwo_1 ="基本通信服务及附加业务名称及描述："+ ywmcms;
	    	    	}else {
	    	    		mainContentTwo_1 ="基本通信服务及附加业务名称及描述：无";
	    	    	}
		    		jsonStr.append("\"mainContentTwo\":").append("\"").append(mainContentTwo_1).append("\\r").
		    		append("可选业务包名称及描述：").append("无").append("\\r").append("号码信息：您选择的号码").append(getValue(accept.getPhone_num())).
		    		append("，具体规则详见业务协议").append("\",");
//		    		append("，具体规则详见业务协议").append("<br/>").append("活动信息：").append("").append("\",");
	    	    	
	    	    	
	    	    	jsonStr.append("\"agentPaperType\":").append("\"").append("").append("\",");//代理人证件类型，来源不清楚  空
	    	    	jsonStr.append("\"acctAddr\":").append("\"").append(getValue(order.getShip_addr())).append("\",");//cust_addr
	    	    	jsonStr.append("\"userType\":").append("\"").append("").append("\",");//prod_offer_type  跟字典值做对应关系，字典值不清楚        空
	    	    	jsonStr.append("\"paperExpr\":").append("\"").append("").append("\",");//证件有效期   传空值
	    	    	jsonStr.append("\"acctName\":").append("\"").append(getValue(accept.getBuyer_name())).append("\",");//用户姓名   cust_name
	    	    	jsonStr.append("\"agentPhone\":").append("\"").append("").append("\",");//代理人电话号码，来源不清楚  空
	    	    	jsonStr.append("\"custType\":").append("\"").append("").append("\",");//客户类型  字典值有（个人或集团）  要商城传，接口要改
	    	    	
	    	    	//付费方式  payment_type  跟字典值做对应关系，字典值不清楚
					if(accept.getPay_mothed().equals("yj") || accept.getPay_mothed()=="yj"){
//						jsonStr.append("\"payMethod\":").append("\"").append("yj").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("邮局汇款").append("\",");
					}
					else if(accept.getPay_mothed().equals("xianjin") || accept.getPay_mothed()=="xianjin"){
//						jsonStr.append("\"payMethod\":").append("\"").append("xianjin").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("现金支付").append("\",");
					}
					else if(accept.getPay_mothed().equals("hdfk") || accept.getPay_mothed()=="hdfk"){
//						jsonStr.append("\"payMethod\":").append("\"").append("hdfk").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("货到付款").append("\",");
					}
					else if(accept.getPay_mothed().equals("cash") || accept.getPay_mothed()=="cash" ||accept.getPay_mothed().equals("ZHXC")){
//						jsonStr.append("\"payMethod\":").append("\"").append("cash").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("银联预付卡").append("\",");
					}
					else if(accept.getPay_mothed().equals("alipay") || accept.getPay_mothed()=="alipay"){
//						jsonStr.append("\"payMethod\":").append("\"").append("alipay").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("支付宝").append("\",");
					}
					else if(accept.getPay_mothed().equals("yibkao") || accept.getPay_mothed()=="yibkao"){
//						jsonStr.append("\"payMethod\":").append("\"").append("yibkao").append("\",");
						jsonStr.append("\"payMethod\":").append("\"").append("易宝").append("\",");
					}
					else{
					    jsonStr.append("\"payMethod\":").append("\"").append(accept.getPay_mothed().toLowerCase()).append("\",");
					}
					
	    	    	jsonStr.append("\"paperAddr\":").append("\"").append(getValue(accept.getCert_address())).append("\",");    //证件地址  certi_addr	    	
	    	    	jsonStr.append("\"paperType\":").append("\"").append(getValue(accept.getCerti_type())).append("\",");  //证件类型  certi_type
	    	    	jsonStr.append("\"staffInfo\":").append("\"").append("").append("\","); //受理人及工号  传空值                                
	    	    	jsonStr.append("\"contactPhone\":").append("\"").append(getValue(order.getShip_mobile())).append("\",");//客户联系电话  ship_phone
	    	    	jsonStr.append("\"bankAcctName\":").append("\"").append(getValue(accept.getBank_account())).append("\",");//银行帐号      bank_cust_code
	    	    	jsonStr.append("\"bankName\":").append("\"").append("").append("\",");//银行名称      bank_name
	    	    	jsonStr.append("\"bankAcct\":").append("\"").append("").append("\",");//该字段没有用到，来源不清楚   空
	    	    	jsonStr.append("\"paperNo\":").append("\"").append(getValue(accept.getCerti_number())).append("\",");//证件号码  certi_num
	    	    	jsonStr.append("\"bankCode\":").append("\"").append(getValue(accept.getBank_code())).append("\",");//银行编码  bank_code
	    	    	jsonStr.append("\"agentPaperNo\":").append("\"").append("").append("\",");//代理人证件号，来源不清楚  空
	    	    	jsonStr.append("\"agentName\":").append("\"").append("").append("\",");//代理人姓名，来源不清楚  空	
	    	    	jsonStr.append("\"userNo\":").append("\"").append(getValue(accept.getPhone_num())).append("\"");
	    	    	jsonStr.append("}");
	    	    	
					sb.append("<AccountInfo>");
					sb.append("<AcceptanceForm>"+jsonStr.toString()+"</AcceptanceForm>");
	    	    	sb.append("<AcceptanceMode>"+"0"+"</AcceptanceMode>");
//	    	    	/*"RECEIPT_CODE  "	打印规则 	
//	    	    	presub_t	号码是预付费的合约机和号卡
//	    	    	postsub_t	号码是后付费的合约机和号卡
//	    	    	wlpost_p_t	非集团用户的后付费无线上网卡
//	    	    	wlpost_g_t	集团用户的后付费无线上网卡
//	    	    	precard_t	预付费无线上网卡
//	    	    	
//	    	    	合约机：号码+合约计划+手机+套餐
//	    	    	号卡：号码+合约计划（可选）+套餐
//	    	    	上网卡：套餐+上网卡硬件（可选）
//	    	    	裸机：手机*/

	    	    	if("20000".equalsIgnoreCase(str_goods_type)){//号卡
	    	    		if("预付费".equalsIgnoreCase(pay_type)){
	    	    			sb.append("<AcceptanceTp>"+"presub_t"+"</AcceptanceTp>");
	    	    		}else if("后付费".equalsIgnoreCase(pay_type)){
	    	    			sb.append("<AcceptanceTp>"+"postsub_t"+"</AcceptanceTp>");
	    	    		}
	    	    	}else if("20002".equalsIgnoreCase(str_goods_type)){//合约机
	    	    		if("预付费".equalsIgnoreCase(pay_type)){
	    	    			sb.append("<AcceptanceTp>"+"presub_t"+"</AcceptanceTp>");
	    	    		}else if ("后付费".equalsIgnoreCase(pay_type)){
	    	    			sb.append("<AcceptanceTp>"+"postsub_t"+"</AcceptanceTp>");
	    	    		}
	    	    	}else if ("20001".equalsIgnoreCase(str_goods_type)){//上网卡
	    	    		if("后付费".equalsIgnoreCase(pay_type)){
	    	    			if("农行商城".equalsIgnoreCase(order.getSource_from())){ //是否集团用户  农行商城过来的单子为集团用户，其他为非集团客户
								sb.append("<AcceptanceTp>"+"wlpost_g_t"+"</AcceptanceTp>");
							}else{
								sb.append("<AcceptanceTp>"+"wlpost_p_t"+"</AcceptanceTp>");
							}
	    	    		}else {
	    	    			sb.append("<AcceptanceTp>"+"precard_t"+"</AcceptanceTp>");
						}
	    	    		
	    	    	}else{
	    	    		sb.append("<AcceptanceTp>"+""+"</AcceptanceTp>");
	    	    	}
	    	    	
	    	    	
	    	    	//保留字段
                  sb.append("<Para>");
                  sb.append("<ParaID>"+""+"</ParaID>");//保留字段   送空
                  sb.append("<ParaValue>"+""+"</ParaValue>");//保留字段   送空
				  sb.append("</Para>");
				  sb.append("</AccountInfo>");
					
					
					
					sb.append("</Item>");
				sb.append("</tid_item>");
			sb.append("</Rows>");

		}
		sb.append("</items>");
		return sb.toString();
	}
	
	// CLON转换String
	public static String ClobToString(Clob cb) {
		try {
			// 以 java.io.Reader 对象形式（或字符流形式）
			// 检索此 Cb 对象指定的 CLOB 值 --Clob的转换
			Reader inStreamDoc = cb.getCharacterStream();
			// 取得cb的长度
			char[] tempDoc = new char[(int) cb.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}
	
	private String getValue(Object value){
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
	
	private String getPayStatusName(Integer pay_status){
		String pay_status_name = "";
		if(pay_status == 0){
			pay_status_name =  "未付款";
		}else if(pay_status == 1){
			pay_status_name =  "已付款";
		}else if(pay_status == 2){
			pay_status_name =  "已退款全部退款";
		}else if(pay_status == 3){
			pay_status_name =  "已退款部分退款";
		}else if(pay_status == 4){
			pay_status_name =  "";
		}else if(pay_status == 5){
			pay_status_name =  "";
		}
		
		return pay_status_name;
	}
	
	private String getPayStatusName(String  platform_status){
		String pay_status_name = "";
		if("SELLER_CONSIGNED_PART".equalsIgnoreCase(platform_status) 
				|| "WAIT_SELLER_SEND_GOODS".equalsIgnoreCase(platform_status)
				|| "WAIT_BUYER_CONFIRM_GOODS".equalsIgnoreCase(platform_status)
				|| "TRADE_BUYER_SIGNED".equalsIgnoreCase(platform_status)
				|| "TRADE_FINISHED".equalsIgnoreCase(platform_status)){
			pay_status_name =  "已付款";
			
		}else{
			pay_status_name =  "未付款";
		}
		
		return pay_status_name;
	}
	

	
	public void responseDocument(HttpServletResponse response,HttpServletRequest request,String xmlStr) {
		logger.info("订单获取接口回复报文："+xmlStr);

		try {
			
			XMLOutputter out;
			Format format = Format.getCompactFormat();
			format.setEncoding("UTF-8");
			format.setIndent("     ");
			out = new XMLOutputter(format);
			
			response.setCharacterEncoding("UTF-8");
			Document document = EcsXmlUtil.string2Doc(xmlStr);
			out.output(document,response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ParamList {
		private String name;
		private List<GoodsVo> paramList;
		private int paramNum;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public List<GoodsVo> getParamList() {
			return paramList;
		}
		public void setParamList(List<GoodsVo> paramList) {
			this.paramList = paramList;
		}
		public int getParamNum() {
			return paramNum;
		}
		public void setParamNum(int paramNum) {
			this.paramNum = paramNum;
		}
		
		

	}
	

public class GoodsVo {
	private String attrcode;
	private String attrtype;
	private String attrvaltype;
	private String ename;
	private String name;
	private String value;
	public String getAttrcode() {
		return attrcode;
	}
	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}
	public String getAttrtype() {
		return attrtype;
	}
	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}
	public String getAttrvaltype() {
		return attrvaltype;
	}
	public void setAttrvaltype(String attrvaltype) {
		this.attrvaltype = attrvaltype;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "GoodsVo [attrcode=" + attrcode + ", attrtype=" + attrtype
				+ ", attrvaltype=" + attrvaltype + ", ename=" + ename
				+ ", name=" + name + ", value=" + value + "]";
	}
	
	

}

	
}

