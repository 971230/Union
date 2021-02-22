package com.ztesoft.net.mall.core.timer;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.jcraft.jsch.ChannelSftp;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.impl.MakeExcel;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.util.SFTPChannel;

public class OrderDataBySearchTimer {

	private ChannelSftp sftpchannel;
	private SFTPChannel channel;
	
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		String sql_total = SF.ecsordSql("ORDER_DATA_SEARCH_TOTAL_LIST_NEW");
		String sql = SF.ecsordSql("ORDER_DATA_LIST_NEW");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int int2 = Integer.parseInt(sdf.format(date));
        sdf = new SimpleDateFormat("MM");
		int int1 = Integer.parseInt(sdf.format(date));
		String begin_time = "";
		if(int1==1){
			int2 = int2-1;
			begin_time = int2+"-12-01 00:00:00";
		}else{
			String a = "";
			int1 = int1-1;
			if(int1<10){
				a = "0"+int1;
			}else{
				a = int1+"";
			}
			begin_time = int2+"-"+a+"-01 00:00:00";
		}
		sql += " and eoe.tid_time >= to_date('"+begin_time+"','yyyy-mm-dd hh24:mi:ss') ";
		sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
		String new_sql = sql_total + " from( " + sql + ") ord ";
		new_sql += " order by ord.tid_time desc";
		System.out.println(new_sql);
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List list = baseDaoSupport.queryForList(new_sql, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int c) throws SQLException { 
				Map data = new HashMap();

				data.put("订单号", rs.getString("out_tid")); // 订单号
				data.put("订单生成日期", rs.getString("tid_time")); // 订单生成日期
				data.put("订单领取时间", rs.getString("order_collect_time")); // 订单领取时间
				data.put("时间差(用户签收时间-订单领取时间)", rs.getString("time_difference")); // 时间差(用户签收时间-订单领取时间)
				data.put("收货地址", rs.getString("ship_addr")); // 收货地址
				data.put("主号码", rs.getString("mainnumber")); // 主号码 新增
				data.put("发货时间", rs.getString("ship_end_time")); // 发货时间
				data.put("激活状态", rs.getString("active_flag")); // 激活状态 新增
				data.put("激活时间", rs.getString("active_time")); // 激活时间 新增
				data.put("签收状态", rs.getString("sign_status")); // 签收状态 新增
				data.put("签收时间", rs.getString("route_acceptime")); // 签收时间
				data.put("数据来源", rs.getString("order_from")); // 数据来源
				data.put("商城来源", rs.getString("order_origin")); // 商城来源
				data.put("推广渠道", rs.getString("spread_channel")); // 推广渠道
				data.put("发展点名称", rs.getString("development_name")); // 发展点名称
				data.put("发展点编码", rs.getString("development_code")); // 发展点编码
				data.put("订单处理人", rs.getString("handle_name")); // 订单处理人
				data.put("订单处理时间", rs.getString("handle_time")); // 订单处理时间
				data.put("订单稽核人", rs.getString("f_op_id")); // 订单稽核人
				data.put("订单稽核时间", rs.getString("f_end_time")); // 订单稽核时间
				data.put("物流编号", rs.getString("logi_no")); // 物流编号
				data.put("终端串号", rs.getString("terminal_num")); // 终端串号
				data.put("退单原因", rs.getString("refund_desc")); // 退单原因
				data.put("地市", rs.getString("lan_code")); // 地市
				data.put("商品类型", rs.getString("goods_type")); // 商品类型
				data.put("商品名称", rs.getString("goodsName")); // 商品名称
				data.put("发票串号", rs.getString("invoice_no")); // 发票串号
				data.put("套餐名称", rs.getString("plan_title")); // 套餐名称
				data.put("特权包", rs.getString("privilege_combo")); // 特权包
				data.put("用户号码", rs.getString("phone_num")); // 用户号码
				data.put("副卡", rs.getString("zb_fuka_info")); // 副卡
				data.put("终端", rs.getString("terminal")); // 终端
				data.put("合约类型", rs.getString("prod_cat_id")); // 合约类型
				data.put("合约期限", rs.getString("contract_month")); // 合约期限
				data.put("实收(元)", rs.getString("pro_realfee")); // 实收(元)
				data.put("支付状态", rs.getString("pay_status")); // 支付状态
				data.put("支付类型", rs.getString("paytype")); // 支付类型
				data.put("订单状态", rs.getString("status")); // 订单状态
				data.put("是否开户（是/否）", rs.getString("account_status")); // 是否开户（是/否）
				data.put("用户类型", rs.getString("is_old")); // 用户类型
				data.put("联系人", rs.getString("ship_name")); // 联系人
				data.put("联系人电话", rs.getString("reference_phone")); // 联系人电话
				data.put("其他联系电话", rs.getString("carry_person_mobile")); // 其他联系电话
				data.put("操作备注", rs.getString("audit_remark")); // 操作备注
				data.put("订单备注", rs.getString("service_remarks")); // 订单备注
				data.put("发展人", rs.getString("devlopname")); // 发展人
				data.put("配送方式", rs.getString("sending_type")); // 配送方式
				data.put("奖品", rs.getString("prize")); // 奖品
				data.put("特色包", rs.getString("special_combo")); // 特色包
				data.put("入网人姓名", rs.getString("phone_owner_name")); // 入网人姓名

				return data;
			}
		}, null);
		
		List<String> ll=new ArrayList();
		String[] title = new String[] { "订单号", "订单生成日期", "订单领取时间", "签收状态", "用户签收时间", "时间差(用户签收时间-订单领取时间)", "数据来源",
				"商城来源", "推广渠道", "发展点名称", "发展点编码", "订单处理人", "订单处理时间", "订单稽核人", "订单稽核时间", "发货时间", "物流编号", "终端串号", "退单原因",
				"地市", "商品类型", "商品名称", "发票串号", "套餐名称", "特权包", "用户号码", "副卡", "终端", "合约类型", "合约期限", "实收(元)", "支付状态",
				"支付类型", "订单状态", "是否开户（是/否）", "用户类型", "联系人", "联系人电话", "收货地址", "其他联系电话", "操作备注", "订单备注", "发展人", "配送方式",
				"奖品", "特色包", "入网人姓名", "主号码", "激活状态", "激活时间" };
		/*String[] content = { "out_tid", "tid_time", "order_collect_time", "sign_status", "route_acceptime",
				"time_difference", "order_from", "order_origin", "spread_channel", "development_name",
				"development_code", "handle_name", "handle_time", "f_op_id", "f_end_time", "ship_end_time", "logi_no",
				"terminal_num", "refund_desc", "lan_code", "goods_type", "goodsName", "invoice_no", "plan_title",
				"privilege_combo", "phone_num", "zb_fuka_info", "terminal", "prod_cat_id", "contract_month",
				"pro_realfee", "pay_status", "paytype", "status", "account_status", "is_old", "ship_name",
				"reference_phone", "ship_addr", "carry_person_mobile", "audit_remark", "service_remarks", "devlopname",
				"sending_type", "prize", "special_combo", "phone_owner_name", "mainnumber", "active_flag",
				"active_time" };*/

		//String fileTitle = "订单数据查询";
		for (int i = 0; i < title.length; i++) {
			ll.add(title[i]);
		}
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String url = cacheUtil.getConfigInfo("OrderDataBySearchURL");
			MakeExcel.CreateExcelFile(list, new File(url),ll,"");
			String params = cacheUtil.getConfigInfo("VOX2WAV_PARAM");
			net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(params);
			Map<String,String> login_map = new HashMap<String,String>();
			login_map.put("ip", jsonobj.getString("ip"));
			login_map.put("port", jsonobj.getString("port"));
			login_map.put("userName", jsonobj.getString("userName"));
			login_map.put("passWord", jsonobj.getString("passWord"));
			channel = new SFTPChannel();
			sftpchannel = channel.getChannel(login_map, 200000);
			String uplod_url = jsonobj.getString("uplod_url");
			File file = new File(url);
			FileInputStream in = new FileInputStream(file);
			sftpchannel.cd(uplod_url);
			sftpchannel.put(in,url.substring(url.lastIndexOf("/")+1));
			in.close();
			channel.deleteFile(url);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}
}
