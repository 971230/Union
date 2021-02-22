package com.ztesoft.net.mall.core.timer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import params.ZteRequest;
import params.ZteResponse;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IOrderCtnService;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdnumberQryResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.BSSGonghaoInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.DevelopDeployExt;
import com.ztesoft.net.ecsord.params.ecaop.vo.KdyydInfoVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.BeanUtils;
import consts.ConstsCore;

public class KdyydSaveTimer {

	private static Logger logger = Logger.getLogger(KdyydSaveTimer.class);
	private String qry_sql = " select * from es_kdyyd_order where del_status='1' and deal_status not in ('CLZ','CLCG') and to_number(deal_num)<4 ";
	
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//System.out.println("KdyydSaveTimer------begin---------------------");
		Long sta = System.currentTimeMillis();
		String update_sql = " update es_kdyyd_order set deal_status = ?,deal_num = ? where id = ? ";
		String sql = qry_sql+" and source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		/*Page page =  baseDaoSupport.queryForPage(sql, 1, 20, new RowMapper(){
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("id",rs.getString("id"));
				data.put("bespeakid",rs.getString("bespeakid"));
				data.put("time",rs.getString("time"));
				data.put("comefrom",rs.getString("comefrom"));
				data.put("thirdno",rs.getString("thirdno"));
				data.put("thirdtime",rs.getString("thirdtime"));
				data.put("provincecode",rs.getString("provincecode"));
				data.put("provincename",rs.getString("provincename"));
				data.put("citycode",rs.getString("citycode"));
				data.put("cityname",rs.getString("cityname"));
				data.put("districtcode",rs.getString("districtcode"));
				data.put("districtname",rs.getString("districtname"));
				data.put("username",rs.getString("username"));
				data.put("userphone",rs.getString("userphone"));
				data.put("certno",rs.getString("certno"));
				data.put("useraddress",rs.getString("useraddress"));
				data.put("productname",rs.getString("productname"));
				data.put("broadbandid",rs.getString("broadbandid"));
				data.put("price",rs.getString("price"));
				data.put("speed",rs.getString("speed"));
				data.put("tarifftype",rs.getString("tarifftype"));
				data.put("appointment",rs.getString("appointment"));
				data.put("appoactivity",rs.getString("appoactivity"));
				data.put("actphone",rs.getString("actphone"));
				data.put("devprovincecode",rs.getString("devprovincecode"));
				data.put("devcitycode",rs.getString("devcitycode"));
				data.put("devid",rs.getString("devid"));
				data.put("devname",rs.getString("devname"));
				data.put("channelid",rs.getString("channelid"));
				data.put("devdepartcode",rs.getString("devdepartcode"));
				data.put("devphoneno",rs.getString("devphoneno"));
				data.put("relatedcardnum",rs.getString("relatedcardnum"));
				data.put("prenum",rs.getString("prenum"));
				data.put("hotspotid",rs.getString("hotspotid"));
				data.put("deal_num",rs.getString("deal_num"));
				data.put("source_from",rs.getString("source_from"));
			return data;
			}
		}, null);*/
		Page page = baseDaoSupport.queryForPage(sql, 1, 20, KdyydInfoVO.class, null);
		List list = page.getResult();
		for (int i = 0; i < list.size(); i++) {
			KdyydInfoVO yydVo = (KdyydInfoVO)list.get(i);
			Map map = new HashMap();
					try {
						BeanUtils.bean2Map(map, yydVo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			int deal_num = Integer.parseInt(Const.getStrValue(map, "deal_num"));
			String id = Const.getStrValue(map, "id");
			baseDaoSupport.execute(update_sql, new String[]{"CLZ",deal_num+"",id});
			sava(map);
		}
		Long end = System.currentTimeMillis();
		//System.out.println("-----------------------END--------------------------------------------------->"+(end-sta));
	}
	
	private static void sava(Map map){
		CoQueueRuleReq coQueueRuleReq = new CoQueueRuleReq();
		coQueueRuleReq.setParams(map);
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
				@Override
				public ZteResponse execute(ZteRequest zteRequest) {
				CoQueueRuleReq coQueueRuleReq =(CoQueueRuleReq)(zteRequest);
				Map map  = coQueueRuleReq.getParams();
            	Map mall_req = new HashMap();
            	//System.out.println("mmp异步进程");
            	String update_sql = " update es_kdyyd_order set deal_status = ?,deal_num = ? where id = ? ";
            	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
            	String id = (String)(map.get("id")==null?"":map.get("id"));
    			String bespeakid = (String)(map.get("bespeakid")==null?"":map.get("bespeakid"));//订单ID
    			String time = (String)(map.get("time")==null?"":map.get("time"));//订单创建时间，yyyy-mm-dd HH24:mi:ss
    			String comefrom = (String)(map.get("comefrom")==null?"":map.get("comefrom"));//web-网厅；mobile-手厅；weixin-联能微厅；jd-京东；tmall-天猫；alipay-支付宝；wkzs-王卡助手；msg-码上购；wkkd-商城统一王卡宽带预约服务
    			String thirdno = (String)(map.get("thirdno")==null?"":map.get("thirdno"));//第三方预约ID
    			String thirdtime = (String)(map.get("thirdtime")==null?"":map.get("thirdtime"));//第三方下单时间，格式：YYYY-MM-DD HH24:mi:ss
    			String provincecode = (String)(map.get("provincecode")==null?"":map.get("provincecode"));//省份编码
    			String provincename = (String)(map.get("provincename")==null?"":map.get("provincename"));//省份名称
    			String citycode = (String)(map.get("citycode")==null?"":map.get("citycode"));//地市编码
    			String cityname = (String)(map.get("cityname")==null?"":map.get("cityname"));//地市名称
    			String districtcode = (String)(map.get("districtcode")==null?"":map.get("districtcode"));//区县编码
    			String districtname = (String)(map.get("districtname")==null?"":map.get("districtname"));//区县名称
    			String username = (String)(map.get("username")==null?"":map.get("username"));//用户名
    			String userphone = (String)(map.get("userphone")==null?"":map.get("userphone"));//手机号码
    			String certno = (String)(map.get("certno")==null?"":map.get("certno"));//身份证号
    			String useraddress = (String)(map.get("useraddress")==null?"":map.get("useraddress"));//装机地址
    			String productname = (String)(map.get("productname")==null?"":map.get("productname"));//产品名称
    			String broadbandid = (String)(map.get("broadbandid")==null?"":map.get("broadbandid"));//产品编码
    			String price = (String)(map.get("price")==null?"":map.get("price"));//价格 单位：离
    			String speed = (String)(map.get("speed")==null?"":map.get("speed"));//速率 eg. 6M
    			String tarifftype = (String)(map.get("tarifftype")==null?"":map.get("tarifftype"));//资费方式 eg.包一年
    			String appointment = (String)(map.get("appointment")==null?"":map.get("appointments"));//预约安装时间 格式：yyyy-mm-dd HH24:mi:ss
    			String appoactivity = (String)(map.get("appoactivity")==null?"":map.get("appoactivity"));//预约活动ID
    			String actphone = (String)(map.get("actphone")==null?"":map.get("actphone"));//优惠活动手机号
    			String devprovincecode = (String)(map.get("devprovincecode")==null?"":map.get("devprovincecode"));//发展人省份编码
    			String devcitycode = (String)(map.get("devcitycode")==null?"":map.get("devcitycode"));//发展人地市编码
    			String devid = (String)(map.get("devid")==null?"":map.get("devid"));//发展人编码
    			String devname = (String)(map.get("devname")==null?"":map.get("devname"));//发展人名称
    			String channelid = (String)(map.get("channelid")==null?"":map.get("channelid"));//渠道ID
    			String devdepartcode = (String)(map.get("devdepartcode")==null?"":map.get("devdepartcode"));//发展人部门编码
    			String devphoneno = (String)(map.get("devphoneno")==null?"":map.get("devphoneno"));//发展人电话
    			String relatedcardnum = (String)(map.get("relatedcardnum")==null?"":map.get("relatedcardnum"));//下单时关联的王卡号码 (目前只针对于 wkkd 渠道)
    			String prenum = (String)(map.get("prenum")==null?"":map.get("prenum"));//宽带融合业务下单时关联的号码
    			String hotspotid = (String)(map.get("hotspotid")==null?"":map.get("hotspotid"));//热点ID
    			
    			String package_state = (String)(map.get("package_state")==null?"":map.get("package_state"));//是否为融合产品00 单宽带01 融合02 王卡宽带
    			String post_add = (String)(map.get("post_add")==null?"":map.get("post_add"));//配送地址
    			String pre_num = (String)(map.get("pre_num")==null?"":map.get("pre_num"));//业务号码
    			String num_order_id = (String)(map.get("num_order_id")==null?"":map.get("num_order_id"));//商城订单编号（号卡）
    			String user_tag = (String)(map.get("user_tag")==null?"":map.get("user_tag"));//用户类型 1:新用户,2:老用户（指号卡的用户类型）
    			String ope_sys = (String)(map.get("ope_sys")==null?"":map.get("ope_sys"));//业务系统：1、cbss2、bss
    			String appointment_date_segment = (String)(map.get("appointment_date_segment")==null?"":map.get("appointment_date_segment"));//预约办理时间段：0-全天；1-上午；2-下午
    			String pay_type = (String)(map.get("pay_type")==null?"":map.get("pay_type"));//支付方式01在线支付03线下现场付款
    			String pay_state = (String)(map.get("pay_state")==null?"":map.get("pay_state"));//支付状态 00 未支付 01 已支付
    			String develelper_channel_code = (String)(map.get("develelper_channel_code")==null?"":map.get("develelper_channel_code"));//发展人渠道编码
    			String syn_mode = (String)(map.get("syn_mode")==null?"":map.get("syn_mode"));
    			
    			int deal_num = Integer.parseInt((String)(map.get("deal_num")==null?"":map.get("deal_num")));
    			
    			try{
    				String goods_sql = " select g.goods_id,g.name,g.type_id,g.cat_id,g.price from es_goods_package p,es_goods g "
    				         + " where p.goods_id=g.goods_id and g.source_from='"+ManagerUtils.getSourceFrom()+"' and p.hs_matnr like '%"+broadbandid+",%' ";
    				Page goods_page = baseDaoSupport.queryForPage(goods_sql, 1, 20, new RowMapper(){
    					@Override
						public Object mapRow(ResultSet rs, int c) throws SQLException {
    						Map data  = new HashMap();
    						data.put("goods_id",rs.getString("goods_id"));
    						data.put("name",rs.getString("name"));
    						data.put("type_id",rs.getString("type_id"));
    						data.put("price",rs.getString("price"));
    						data.put("cat_id",rs.getString("cat_id"));
    						return data;
    					}
    				}, null);
    				List goods_list = goods_page.getResult();
    				if(goods_list.size()<1){
    					baseDaoSupport.execute(update_sql, new String[]{"CLSB",(deal_num+1)+"",id});
    					throw new Exception("消息ID：'"+id+"'未配置对应商品");
    				}
    				Map goods_map = (Map) goods_list.get(0);
    				String type_id = Const.getStrValue(goods_map, "type_id");
                    String cat_id = Const.getStrValue(goods_map, "cat_id");//商品小类 为了取发展人和发展点
    				String out_service_code = "";
    				
    				Map cust_info = new HashMap();
    				mall_req.put("source_system", "AO");
    				mall_req.put("serial_no", id);
    				mall_req.put("create_time", DateUtil.getTime5());
    				String order_from_sql = " select t.pname from es_dc_public_ext t where t.stype='zb_come_from' and t.pkey like '%"+comefrom+"%' ";
    				String source_system_type = baseDaoSupport.queryForString(order_from_sql);
    				mall_req.put("source_system_type", source_system_type);
    				mall_req.put("mall_order_id", bespeakid);

    				List city_list = baseDaoSupport.queryForList("select a.* from es_dc_public_dict_relation a where a.stype=? and a.other_field_value=?","city",citycode);
    				String order_city_code = Const.getStrValue((Map)city_list.get(0),"field_value");
    				mall_req.put("order_city_code",  order_city_code);
    				/*String county_sql = " select distinct substr(a.field_value,0,8) field_value from es_dc_public_dict_relation a where a.stype='county' and a.field_value_desc like '%"+districtname+"%' ";
    			    String order_county_code = baseDaoSupport.queryForString(county_sql);
    				mall_req.put("order_county_code", order_county_code);*/
    				mall_req.put("order_county_code", "ZJ"+districtcode);
    				String goods_price = Const.getStrValue(goods_map, "price");
    				mall_req.put("order_amount", price);
    				mall_req.put("pay_amount", price);
    				mall_req.put("discount_amount", "0");
    				mall_req.put("num_order_id", num_order_id);
    				if(StringUtils.isEmpty(package_state)||!"02".equals(package_state)){
    					Map contact_info = new HashMap();
        				contact_info.put("ship_name", username);
        				contact_info.put("ship_tel", userphone);
        				contact_info.put("ship_addr", useraddress);
        				mall_req.put("contact_info", net.sf.json.JSONObject.fromObject(contact_info));
    				}else{
    					mall_req.put("create_time", DateUtil.getTime2());
    					mall_req.put("receive_system", "10011");
    					mall_req.put("deli_mode", "WX");
    					mall_req.put("deli_time_mode", "NOLIMIT");
    					mall_req.put("user_to_account", "1");
    					mall_req.put("busi_type", "04");
    					mall_req.put("realname_type", "2");	
    					mall_req.put("channel_mark", "13");
    					mall_req.put("is_pay", "0");
    					mall_req.put("service_type", "0");
    					mall_req.put("orig_post_fee", "0");
    					mall_req.put("real_post_fee", "0");
    					mall_req.put("source_system", source_system_type);
    					mall_req.put("ship_name", username);
    					mall_req.put("ship_addr", useraddress);
    					mall_req.put("ship_tel", userphone);
    				}
    				
    				mall_req.put("syn_mode", syn_mode);
    				
    				Map goods_info = new HashMap();
    				List goods_info_list = new ArrayList();
    				if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    					goods_info.put("prod_offer_code", Const.getStrValue(goods_map, "goods_id"));
        				goods_info.put("prod_offer_name", Const.getStrValue(goods_map, "name"));
        				goods_info.put("real_offer_price", goods_price);
        				goods_info.put("goods_num", "1");
        				goods_info.put("offer_price", goods_price);
    				}else{
    					goods_info.put("prod_code", Const.getStrValue(goods_map, "goods_id"));
        				goods_info.put("prod_name", Const.getStrValue(goods_map, "name"));
        				goods_info.put("prod_offer_code", Const.getStrValue(goods_map, "goods_id"));
        				goods_info.put("prod_offer_name", Const.getStrValue(goods_map, "name"));
    				}
    				
    				goods_info_list.add(goods_info);
    				mall_req.put("goods_info", net.sf.json.JSONArray.fromObject(goods_info_list));
    				/*Map developer_info = new HashMap();*/
    				BSSGonghaoInfoVO gonghaoInfo = new BSSGonghaoInfoVO();
    				gonghaoInfo.setCity_id(order_city_code);
    				gonghaoInfo.setOrder_from("10083");
    				if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    					cust_info.put("user_type", "NEW");
    					cust_info.put("customer_type", "GRKH");
    					String kingcard_state_sql = " select t.order_state,t.pre_num from es_kdyyd_kingcard_status t where t.order_id='"+bespeakid+"' order by t.state_change desc ";
    					Page kingcard_page = baseDaoSupport.queryForPage(kingcard_state_sql, 1, 20, new RowMapper(){
        					@Override
							public Object mapRow(ResultSet rs, int c) throws SQLException {
        						Map data  = new HashMap();
        						data.put("order_state",rs.getString("order_state"));
        						data.put("service_num",rs.getString("pre_num"));
        						return data;
        					}
        				}, null);
    					List kingcard_list = kingcard_page.getResult();
    					if(kingcard_list.size()>0){
    						Map kingcard_map = (Map) kingcard_list.get(0);
    						mall_req.put("kingcard_status", Const.getStrValue(kingcard_map, "order_state"));
    						cust_info.put("service_num", Const.getStrValue(kingcard_map, "pre_num"));
    					}else{
    						mall_req.put("kingcard_status", "00");
    					}
    				}
    				/*developer_info.put("deal_operator", gonghaoInfo.getUser_code());
    				developer_info.put("deal_office_id", gonghaoInfo.getDept_id());*/
    				/*mall_req.put("developer_info", net.sf.json.JSONObject.fromObject(developer_info));*/
    				
    				cust_info.put("is_real_name", "0");
    				cust_info.put("customer_name", username);
    				cust_info.put("cert_type", "SFZ18");
    				cust_info.put("cert_num", certno);
    				mall_req.put("cust_info", net.sf.json.JSONObject.fromObject(cust_info));
    				Map pay_info = new HashMap();
    				if(!StringUtil.isEmpty(syn_mode)){
    					if(!StringUtil.isEmpty(pay_state)&&"01".equals(pay_state)){
        					pay_info.put("pay_status", "1");
        				}else{
        					pay_info.put("pay_status", "0");
        				}
    					if(!StringUtil.isEmpty(pay_type)&&"01".equals(pay_type)){
    						pay_info.put("pay_type", "ZXSF");
        				}else{
        					pay_info.put("pay_type", "SMSF");
        				}
    				}else{
    					pay_info.put("pay_status", "0");
    					pay_info.put("pay_type", "SMSF");
    				}
    				if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    					pay_info.put("pay_method", "WZF");
    				}
    				mall_req.put("pay_info", net.sf.json.JSONObject.fromObject(pay_info));
    				Map<String, String> develop_find = new HashMap<String, String>();
    				develop_find.put("type_id",type_id);//大类
                    develop_find.put("cat_id",cat_id);//小类
                    develop_find.put("goods_id",Const.getStrValue(goods_map, "goods_id"));//商品
                    develop_find.put("order_city_code", order_city_code);//地市
                    develop_find.put("source_system_type",source_system_type );//来源
                    develop_find.put("source_system","AO");//来源大类
                    develop_find.put("order_county_code", "ZJ"+districtcode);//县分
                    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
                    String kg = cacheUtil.getConfigInfo("kdyyd_save_kg");
                    List<DevelopDeployExt> tList = new ArrayList<DevelopDeployExt>();
                    if(!StringUtil.isEmpty(kg) && "1".equals(kg)){
                        tList = KdyydSaveTimer.developFindExt(develop_find);
                    }
    				if("20021".equals(type_id)){
    					mall_req.put("order_prov_code", "36");
    					Map broad_map = new HashMap();
    					broad_map.put("product_type","KD");
    					broad_map.put("user_address",useraddress);
    					broad_map.put("std_address","-1");
    					broad_map.put("is_judge_address","0");
                        Map developer_info = new HashMap();
                        if(tList!=null&&!tList.isEmpty() && tList.size()>0){
                            DevelopDeployExt deployExt = tList.get(0);
                            if(!StringUtil.isEmpty(deployExt.getDeveop_code())){
                                developer_info.put("develop_code", deployExt.getDeveop_code());
                            }
                            if(!StringUtil.isEmpty(deployExt.getDevelop_point_code())){
                                developer_info.put("develop_point_code", deployExt.getDevelop_point_code());
                            }
                        }
    					mall_req.put("developer_info", net.sf.json.JSONObject.fromObject(developer_info));
    					String kd_num = KdyydSaveTimer.getKDNumber(gonghaoInfo);
    					if(!StringUtil.isEmpty(kd_num)){
    						broad_map.put("bb_account", kd_num);
    						broad_map.put("bb_num", kd_num);
    					}else{
    						throw new Exception("消息ID：'"+id+"'获取宽带号码异常");
    					}
    					mall_req.put("broad_info", net.sf.json.JSONObject.fromObject(broad_map));
    					out_service_code = "create_order_broadBandCtnStandard";//develop_info 节点
    				}else if ("170502112412000711".equals(type_id)){
    					mall_req.put("order_province_code", "36");
				       Map developer_info = new HashMap();
                       if(tList!=null&&!tList.isEmpty() && tList.size()>0){
                           DevelopDeployExt deployExt = tList.get(0);
                           if(!StringUtil.isEmpty(deployExt.getDeveop_code())){
                               mall_req.put("develop_code", deployExt.getDeveop_code());
                           }
                           if(!StringUtil.isEmpty(deployExt.getDevelop_point_code())){
                               developer_info.put("develop_point_code", deployExt.getDevelop_point_code());
                           }
                        }
                        mall_req.put("developer_info", net.sf.json.JSONObject.fromObject(developer_info));
    					Map broad_map = new HashMap();
    					Map broad_map2 = new HashMap();
    					if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    					    if(tList!=null&&!tList.isEmpty() && tList.size()>0){
    	                           DevelopDeployExt deployExt = tList.get(0);
    	                           if(!StringUtil.isEmpty(deployExt.getDeveop_code())){
    	                               mall_req.put("develop_code", deployExt.getDeveop_code());
    	                           }
    	                           if(!StringUtil.isEmpty(deployExt.getDevelop_point_code())){
    	                               mall_req.put("develop_point_code_new",deployExt.getDevelop_point_code());
    	                               broad_map.put("develop_point_code",deployExt.getDevelop_point_code());
    	                           }
    	                     }
    					}
    					broad_map.put("product_type","KD");
    					broad_map.put("user_address",useraddress);
    					broad_map.put("std_address","-1");
    					broad_map.put("is_judge_address","0");
    					String kd_num = KdyydSaveTimer.getKDNumber(gonghaoInfo);
    					if(!StringUtil.isEmpty(kd_num)){
    						broad_map.put("bb_account", kd_num);
    						broad_map.put("bb_num", kd_num);
    					}else{
    						throw new Exception("消息ID：'"+id+"'获取宽带号码异常");
    					}
    					broad_map2.put("product_type","TV");
    					broad_map2.put("user_address",useraddress);
    					broad_map2.put("std_address","-1");
    					broad_map2.put("is_judge_address","0");
    					kd_num = KdyydSaveTimer.getKDNumber(gonghaoInfo);
    					if(!StringUtil.isEmpty(kd_num)){
    						broad_map2.put("bb_account", kd_num);
    						broad_map2.put("bb_num", kd_num);
    						if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    							broad_map2.put("bb_password", kd_num.substring(kd_num.length()-6, kd_num.length()));
    							String user_sql = " select t.ess_emp_id,t.city,t.district,t.dep_id,t.channel_id,t.channel_type from es_operator_rel_ext t where t.order_gonghao='zjplxk' and t.city='"+citycode+"' ";
    							Page user_page = baseDaoSupport.queryForPage(user_sql, 1, 20, new RowMapper(){
    	        					@Override
									public Object mapRow(ResultSet rs, int c) throws SQLException {
    	        						Map data  = new HashMap();
    	        						data.put("ess_emp_id",rs.getString("ess_emp_id"));
    	        						data.put("city",rs.getString("city"));
    	        						data.put("district",rs.getString("district"));
    	        						data.put("dep_id",rs.getString("dep_id"));
    	        						data.put("channel_id",rs.getString("channel_id"));
    	        						data.put("channel_type",rs.getString("channel_type"));
    	        						return data;
    	        					}
    	        				}, null);
    							List user_list = user_page.getResult();
    							if(user_list.size()>0){
    								Map user_map = (Map) user_list.get(0);
    								broad_map2.put("deal_operator", Const.getStrValue(user_map, "ess_emp_id"));
    								mall_req.put("ship_city", Const.getStrValue(user_map, "city"));
    								mall_req.put("ship_province", "330000");
    								mall_req.put("channel_id", Const.getStrValue(user_map, "channel_id"));
    							}
    							
    						}
    					}else{
    						throw new Exception("消息ID：'"+id+"'获取宽带号码异常");
    					}
    					List broad_info = new ArrayList();
    					broad_info.add(broad_map);
    					broad_info.add(broad_map2);
    					mall_req.put("broad_info", net.sf.json.JSONArray.fromObject(broad_info));
    					out_service_code = "create_order_internetGroupStandard";//develop_info 节点
    					if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    						out_service_code = "create_order_groupOrderStandard";//存在主节点下的
						}
    				}else{
    					mall_req.put("order_province_code", "36");
    					if(StringUtils.isEmpty(package_state)||!"02".equals(package_state)){
    						Map developer_info = new HashMap();
                            if(tList!=null&&!tList.isEmpty() && tList.size()>0){
                                DevelopDeployExt deployExt = tList.get(0);
                                if(!StringUtil.isEmpty(deployExt.getDeveop_code())){
                                    developer_info.put("develop_code", deployExt.getDeveop_code());
                                }
                                if(!StringUtil.isEmpty(deployExt.getDevelop_point_code())){
                                    developer_info.put("develop_point_code", deployExt.getDevelop_point_code());
                                }
                            }
        					mall_req.put("developer_info", net.sf.json.JSONObject.fromObject(developer_info));
    					}
    					Map broad_map = new HashMap();
    					Map broad_map2 = new HashMap();
    					broad_map.put("product_type","KD");
    					broad_map.put("user_address",useraddress);
    					broad_map.put("std_address","-1");
    					broad_map.put("is_judge_address","0");
    					String bind_rela_type = CommonDataFactory.getInstance().getGoodSpec(null, Const.getStrValue(goods_map, "goods_id"), "bind_rela_type");
    					broad_map.put("bind_rela_type",bind_rela_type);
    					String kd_num = KdyydSaveTimer.getKDNumber(gonghaoInfo);
    					if(!StringUtil.isEmpty(kd_num)){
    						broad_map.put("bb_account", kd_num);
    						broad_map.put("bb_num", kd_num);
    					}else{
    						throw new Exception("消息ID：'"+id+"'获取宽带号码异常");
    					}
    					broad_map2.put("product_type","TV");
    					broad_map2.put("user_address",useraddress);
    					broad_map2.put("std_address","-1");
    					broad_map2.put("is_judge_address","0");
    					broad_map2.put("bind_rela_type",bind_rela_type);
    					if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    				           if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    	                            if(tList!=null&&!tList.isEmpty() && tList.size()>0){
	                                   DevelopDeployExt deployExt = tList.get(0);
	                                   if(!StringUtil.isEmpty(deployExt.getDeveop_code())){
	                                       mall_req.put("develop_code", deployExt.getDeveop_code());
	                                   }
	                                   if(!StringUtil.isEmpty(deployExt.getDevelop_point_code())){
	                                       mall_req.put("develop_point_code_new",deployExt.getDevelop_point_code());
	                                       broad_map2.put("develop_point_code",deployExt.getDevelop_point_code());
	                                   }
    	                            }
    	                        }
							broad_map2.put("bb_password", kd_num.substring(kd_num.length()-6, kd_num.length()));
							broad_map.put("county_id", gonghaoInfo.getCounty_id());
							broad_map2.put("county_id", gonghaoInfo.getCounty_id());
							String user_sql = " select t.ess_emp_id,t.city,t.district,t.dep_id,t.channel_id,t.channel_type from es_operator_rel_ext t where t.order_gonghao='zjplxk' and t.city='"+citycode+"' ";
							Page user_page = baseDaoSupport.queryForPage(user_sql, 1, 20, new RowMapper(){
	        					@Override
								public Object mapRow(ResultSet rs, int c) throws SQLException {
	        						Map data  = new HashMap();
	        						data.put("ess_emp_id",rs.getString("ess_emp_id"));
	        						data.put("city",rs.getString("city"));
	        						data.put("district",rs.getString("district"));
	        						data.put("dep_id",rs.getString("dep_id"));
	        						data.put("channel_id",rs.getString("channel_id"));
	        						data.put("channel_type",rs.getString("channel_type"));
	        						return data;
	        					}
	        				}, null);
							List user_list = user_page.getResult();
							if(user_list.size()>0){
								Map user_map = (Map) user_list.get(0);
								broad_map2.put("deal_operator", Const.getStrValue(user_map, "ess_emp_id"));
								mall_req.put("ship_city", Const.getStrValue(user_map, "city"));
								mall_req.put("ship_province", "330000");
								mall_req.put("channelId", Const.getStrValue(user_map, "channel_id"));
								mall_req.put("channelType", Const.getStrValue(user_map, "channel_type"));
								mall_req.put("district", Const.getStrValue(user_map, "district"));
							}
							
						}
    					kd_num = KdyydSaveTimer.getKDNumber(gonghaoInfo);
    					if(!StringUtil.isEmpty(kd_num)){
    						broad_map2.put("bb_account", kd_num);
    						broad_map2.put("bb_num", kd_num);
    						
    					}else{
    						throw new Exception("消息ID：'"+id+"'获取宽带号码异常");
    					}
    					List broad_info = new ArrayList();
    					broad_info.add(broad_map);
    					broad_info.add(broad_map2);
    					mall_req.put("broad_info", net.sf.json.JSONArray.fromObject(broad_info));
    					out_service_code = "create_order_internetGroupStandard";
    					if(!StringUtils.isEmpty(package_state)&&"02".equals(package_state)){
    						out_service_code = "create_order_groupOrderStandard";
						}
    				}
    				String jsonStr = JsonUtil.toJson(mall_req);
    				OrderCtnReq orderCtnReq = new OrderCtnReq();
    				Map<String, Object> reqParamsMap = new HashMap<String, Object>();
                    reqParamsMap.put("orderSource", source_system_type);
                    orderCtnReq.setOutServiceCode(out_service_code);
                    orderCtnReq.setVersion("1.0");
    				orderCtnReq.setReqMsgStr(jsonStr);
    				orderCtnReq.setFormat("json");
    				orderCtnReq.setOutOrderId(bespeakid);
    				orderCtnReq.setReqParamsMap(reqParamsMap);
    				IOrderCtnService orderCtnService = SpringContextHolder.getBean("orderCtnService");
    				OrderCtnResp resp = orderCtnService.orderCtn(orderCtnReq);
    				if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
    					String insert_sql = " insert into es_kdyyd_order_his "
    							+ " select id,bespeakid,time,comefrom,thirdno,thirdtime,provincecode,provincename,"
    							+ "citycode,cityname,districtcode,districtname,username,userphone,certno,useraddress,"
    							+ "productname,broadbandid,price,speed,tarifftype,appointment,appoactivity,actphone,"
    							+ "devprovincecode,devcitycode,devid,devname,channelid,devdepartcode,devphoneno,"
    							+ "relatedcardnum,prenum,hotspotid,del_status,'CLCG',deal_num,source_from,"
    							+ "address_code,cell_name,iom_cell_name,cell_code,cell_type,package_state,"
    							+ "post_add,pre_num,num_order_id,user_tag,ope_sys,product_id,broadband_price,"
    							+ "tariff_mode,appointment_date_segment,activename,optproduct_id,"
    							+ "optproduct_price,optpackage_id,optpackage_price,pay_type,"
    							+ "pay_state,pay_amout,serviceplatid,functioncode,supplierid,syn_mode "
    							+ " from es_kdyyd_order where id=? ";
    					baseDaoSupport.execute(insert_sql, new String[]{id});
    					String del_sql = " delete from es_kdyyd_order where id =? " ;
    					baseDaoSupport.execute(del_sql, new String[]{id});
    				}else{
    					baseDaoSupport.execute(update_sql, new String[]{"CLSB",(deal_num+1)+"",id});
    				}
    			}catch(Exception e){
    				baseDaoSupport.execute(update_sql, new String[]{"CLSB",(deal_num+1)+"",id});
    				logger.info(e.getMessage());
    				e.printStackTrace();
    			}
				return null;
            }
        }); 
		ThreadPoolFactory.orderExecute(taskThreadPool); //异步单线程执行
	}
	/**
	 * 三个维度要同时满足需求 以订单来源（订单来源大类、订单来源小类）,商品（商品小类，商品大类，商品编码）,地市（地市、县分）纬度匹配
	 * 先不进行做县分的校验  后续若加  逻辑需要更改
	 */
	protected static List<DevelopDeployExt> developFindExt(Map<String, String> develop_find) {
	    if(StringUtil.isEmpty(develop_find.get("type_id"))  && StringUtil.isEmpty(develop_find.get("cat_id")) && StringUtil.isEmpty(develop_find.get("goods_id"))){
	        return null;
	    }
	    if(StringUtil.isEmpty(develop_find.get("order_city_code"))){
            return null;
        }
        if(StringUtil.isEmpty(develop_find.get("source_system"))  && StringUtil.isEmpty(develop_find.get("source_system_type"))){//三个维度任意一个为空  不进行匹配
             return null;
        }
        String sqlSelect = "select t.mainkey,t.source_system, t.source_system_type, t.type_mis, t.goods_type, t.goods_cat, t.goods_id, t.order_city_code, t.order_county_code, t.deveop_code, t.develop_point_code, t.source_from  from es_develop_deploy_ext t where t.source_from = '"+ManagerUtils.getSourceFrom()+"'";
        StringBuilder order_city_code = new StringBuilder();
        if(!StringUtil.isEmpty(develop_find.get("order_city_code"))){
            order_city_code.append("and t.order_city_code='").append(develop_find.get("order_city_code")).append("' ");
        }
        StringBuilder goods_ids = new StringBuilder();
        goods_ids.append(" and ( ");
        if(!StringUtil.isEmpty(develop_find.get("goods_id"))){
            goods_ids.append(" t.goods_id='").append(develop_find.get("goods_id")).append("' ");
        }
        
        if(!StringUtil.isEmpty(develop_find.get("cat_id"))){
            goods_ids.append(" or t.goods_cat='").append(develop_find.get("cat_id")).append("' ");
        }

        if(!StringUtil.isEmpty(develop_find.get("type_id"))){
            goods_ids.append(" or t.goods_type='").append(develop_find.get("type_id")).append("' ");
        }
        goods_ids.append(" )");
        
        StringBuilder source_systems = new StringBuilder();
        source_systems.append(" and (");
        if(!StringUtil.isEmpty(develop_find.get("source_system"))){
            source_systems.append(" t.source_system='").append(develop_find.get("source_system")).append("' ");
        }
        if(!StringUtil.isEmpty(develop_find.get("source_system_type"))){
            source_systems.append(" or t.source_system_type='").append(develop_find.get("source_system_type")).append("' ");
        }
        source_systems.append(" )");
        StringBuilder abBuilder = new StringBuilder();
        abBuilder.append(sqlSelect).append(order_city_code.toString()).append(source_systems.toString()).append(goods_ids.toString());
        String sqlString = "select count(1) from es_develop_deploy_ext t where t.source_from = '"+ManagerUtils.getSourceFrom()+"' "+order_city_code.toString()+source_systems.toString()+goods_ids.toString();
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        List<DevelopDeployExt> pList = new ArrayList<DevelopDeployExt>();
        Page page = baseDaoSupport.queryForPage(abBuilder.toString(),sqlString,1, 1000, new RowMapper(){
            @Override
			public Object mapRow(ResultSet rs, int c) throws SQLException {
                DevelopDeployExt develop = new DevelopDeployExt();
                develop.setDevelop_point_code(rs.getString("develop_point_code"));
                develop.setDeveop_code(rs.getString("deveop_code"));
                develop.setGoods_cat(rs.getString("goods_cat"));
                develop.setGoods_id(rs.getString("goods_id"));
                develop.setGoods_type(rs.getString("goods_type"));
                develop.setMainkey(rs.getString("mainkey"));
                develop.setSource_from(rs.getString("source_from"));
                develop.setType_mis(rs.getString("type_mis"));
                develop.setSource_system_type(rs.getString("source_system_type"));
                develop.setSource_system(rs.getString("source_system"));
                develop.setOrder_county_code(rs.getString("order_county_code"));
                develop.setOrder_city_code(rs.getString("order_city_code"));
                return develop;
            }
        }, pList.toArray());
        List list = page.getResult();
        List<DevelopDeployExt> maps = KdyydSaveTimer.select_info_develop(list,develop_find);
        if(maps!=null && !maps.isEmpty() && maps.size()>0){//商品维度筛选符合的
            return maps;
        }
        /**
         * 若商品维度不符合要求需要进行来源维度匹配
         */
        List<DevelopDeployExt> sysDevelopDeploy = KdyydSaveTimer.select_info_system(list,develop_find);
        if(sysDevelopDeploy!=null && !sysDevelopDeploy.isEmpty() && sysDevelopDeploy.size()>0){
            return sysDevelopDeploy;
        }
        return null;
    }
	/**
	 * 第一维根据商品依次筛选初步符合的信息
	 */
    @SuppressWarnings("rawtypes")
    private static List<DevelopDeployExt> select_info_develop(List list, Map<String, String> develop_finds) {
        List<DevelopDeployExt> listMap = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listinfo = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listgoodscat = new ArrayList<DevelopDeployExt>();
        Map<String, String> map = new HashMap<String, String>();
        /**
         * 若是100901 和100902 商品配置为null
         * 分 若商品为空 或者 不为空
         */
        if(develop_finds.containsKey("goods_id") && !StringUtil.isEmpty(develop_finds.get("goods_id"))){//结果集中找出是该商品小类的结果集
            for (int i = 0; i < list.size(); i++) {
                DevelopDeployExt developDeploy = (DevelopDeployExt)list.get(i);
                if(!StringUtil.isEmpty(developDeploy.getGoods_id()) && develop_finds.get("goods_id").equals(developDeploy.getGoods_id())){//商品编码不为空 且和传值相同
                    listinfo.add(developDeploy);
                }else{
                    listgoodscat.add(developDeploy);
                }
            }
            if(listinfo != null && !listinfo.isEmpty() && listinfo.size()>0){
                listMap = KdyydSaveTimer.select_info_system(listinfo,develop_finds);
            }
            if(listMap!=null && !listMap.isEmpty() && listMap.size()>0){
                return listMap;
            }
        }
        
        List<DevelopDeployExt>  listgoodstype = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listcatinfo = new ArrayList<DevelopDeployExt>();
        if( listgoodscat != null &&develop_finds.containsKey("cat_id") && !StringUtil.isEmpty(develop_finds.get("cat_id"))){
            for (int i = 0; i < listgoodscat.size(); i++) {
                DevelopDeployExt developDeploy = listgoodscat.get(i);
                if(!StringUtil.isEmpty(developDeploy.getGoods_cat()) && develop_finds.get("cat_id").equals(developDeploy.getGoods_cat())){
                    listcatinfo.add(developDeploy);
                }else{
                    listgoodstype.add(developDeploy);
                }
            }
            if(listcatinfo != null && !listcatinfo.isEmpty() && listcatinfo.size()>0){
                listMap = KdyydSaveTimer.select_info_system(listcatinfo,develop_finds);
            }
            if(listMap!=null && !listMap.isEmpty() && listMap.size()>0){
                return listMap;
            }
        }
        List<DevelopDeployExt>  listtypeinfo = new ArrayList<DevelopDeployExt>();

        if(listgoodstype != null && develop_finds.containsKey("type_id") && !StringUtil.isEmpty(develop_finds.get("type_id"))){
            for (int i = 0; i < listgoodstype.size(); i++) {
                DevelopDeployExt developDeploy = listgoodstype.get(i);
                if(!StringUtil.isEmpty(developDeploy.getGoods_type()) && develop_finds.get("type_id").equals(developDeploy.getGoods_type())){
                    listtypeinfo.add(developDeploy);
                }
            }
            if(listtypeinfo != null && !listtypeinfo.isEmpty() && listtypeinfo.size()>0){
                listMap = KdyydSaveTimer.select_info_system(listtypeinfo,develop_finds);
            }
            if(listMap!= null && !listMap.isEmpty() && listMap.size()>0){
                return listMap;
            }
        }
        return null;
    }
    /**
     * 内部第二维度判断  来源
     */
    private static List<DevelopDeployExt> select_info_system( List<DevelopDeployExt> list, Map<String, String> develop_finds) {
        List<DevelopDeployExt> listMap = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listinfo = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listsystemtype = new ArrayList<DevelopDeployExt>();
        Map<String, String> map = new HashMap<String, String>();
        if(develop_finds.containsKey("source_system_type") && !StringUtil.isEmpty(develop_finds.get("source_system_type"))){//根据小类筛选
            for (int i = 0; i < list.size(); i++) {
                DevelopDeployExt developDeploy = list.get(i);
                if(!StringUtil.isEmpty(developDeploy.getSource_system_type()) && develop_finds.get("source_system_type").equals(developDeploy.getSource_system_type()) && develop_finds.get("source_system").equals(developDeploy.getSource_from())){
                    listinfo.add(developDeploy);
                }else if(StringUtil.isEmpty(developDeploy.getSource_system_type()) && develop_finds.get("source_system").equals(developDeploy.getSource_system())){
                    listsystemtype.add(developDeploy);
                }
            }
        }
        if(listinfo != null && !listinfo.isEmpty()&&listinfo.size()>0){
            listMap = KdyydSaveTimer.select_info_county(listinfo,develop_finds);
        }
        if(listMap!=null && !listMap.isEmpty() && listMap.size()>0){
            return listMap;
        }else{
           return listsystemtype;
        }
    }
    /**
     * 第三维度 来匹配地市信息  此处应该就需要是返回唯一值信息了
     */
    private static List<DevelopDeployExt> select_info_county(List<DevelopDeployExt> list, Map<String, String> develop_finds) {
        List<DevelopDeployExt> listMap = new ArrayList<DevelopDeployExt>();
        List<DevelopDeployExt>  listinfo = new ArrayList<DevelopDeployExt>();
        Map<String, String> map = new HashMap<String, String>(); 
        if(develop_finds.containsKey("order_city_code") && !StringUtil.isEmpty(develop_finds.get("order_city_code"))){
            for (int i = 0; i < list.size(); i++) {
                DevelopDeployExt developDeploy = list.get(i);
                if(!StringUtil.isEmpty(developDeploy.getOrder_city_code()) && develop_finds.get("order_city_code").equals(developDeploy.getOrder_city_code())){
                    listMap.add(developDeploy);
                }
            }
            return listMap;
        }
       return null;
     }

    private static String getKDNumber(BSSGonghaoInfoVO gonghaoInfo){
		try{
			KdnumberQryResp resp = new KdnumberQryResp();
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			Map map = new HashMap();
			map.put("QRY_TYPE", "2");
			map.put("SERVICE_CLASS_CODE", "0040");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(map);
			resp = new KdnumberQryResp();
			param.put("msg", jsonObject);
			param.put("operator_id", gonghaoInfo.getUser_code());
			param.put("office_id", gonghaoInfo.getDept_id());
			param.put("serial_num", DateUtil.getTime7());
			resp = (KdnumberQryResp) caller.invoke("ecaop.trades.query.resource.kdnumber.qry", param);
			if (EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getRespJson().getRESP_CODE())) {
				return resp.getRespJson().getACCT_INFO().getSERIAL_NUMBER();
			}else{
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
}