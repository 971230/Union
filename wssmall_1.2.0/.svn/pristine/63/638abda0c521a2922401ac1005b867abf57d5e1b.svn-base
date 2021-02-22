//package com.ztesoft.ibss.ct.vo;
//
//import com.powerise.ibss.framework.DynamicDict;
//import com.ztesoft.common.rule.protocol.BusiData;
//import com.ztesoft.ibss.common.util.SqlMapExe;
//import org.apache.commons.lang.StringUtils;
//
//import java.sql.Connection;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//
//public class TwbPreQuery {
//	BusiData data;
//	public TwbPreQuery(BusiData data){
//		this.data = data;
//	}
//	Connection conn = null;
//	private String pre_ord_no;
//	private String serv_no;
//	private String ask_num;
//	private String oper_time;
//	private String user_name;
//	private String area_code;
//	private String user_no;
//	private String product_id;
//	private String col1;
//	private String col2;
//	private String col3;
//	private String col4;
//	
//	public void setCol1(String col1) {
//		this.col1 = col1;
//	}
//
//	public void setCol2(String col2) {
//		this.col2 = col2;
//	}
//	public void setCol3(String col3) {
//		this.col3 = col3;
//	}
//	
//	public void setCol4(String col43) {
//		this.col4 = col4;
//	}
//	public void setArea_code(String area_code) {
//		this.area_code = area_code;
//	}
//
//	public void setAsk_num(String ask_num) {
//		
//		this.ask_num = ask_num;
//	}
//	public String getOper_time() {
//		return oper_time;
//	}
//	public void setOper_time(String oper_time) {
//		this.oper_time = oper_time;
//	}
//	
//	public void setPre_ord_no(String pre_ord_no) {
//		this.pre_ord_no = pre_ord_no;
//	}
//	public String getServ_no() {
//		return serv_no;
//	}
//	public void setServ_no(String serv_no) {
//		this.serv_no = serv_no;
//	}
//	
//	public void setUser_no(String user_no) {
//		this.user_no = user_no;
//	}
//
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
//	
//	
//
//	public void setProduct_id(String product_id) {
//		this.product_id = product_id;
//	}
//	//get方法
//	public String getUser_no() {
//		if(StringUtils.isEmpty(user_no))
//			user_no = User.getUser_no();
//		return user_no;
//	}
//	public String getUser_name() {
//		if(StringUtils.isEmpty(user_name))
//			user_name = User.getUser_name();
//		return user_name;
//	}
//	public String getPre_ord_no() {
//		if(StringUtils.isEmpty(pre_ord_no)){
//			String random =com.ztesoft.ibss.common.util.StringUtils.genRandom(9);
//			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHMMss");
//			pre_ord_no = formatter.format(new Date()) + random;
//		}
//		return pre_ord_no;
//	}
//	public String getAsk_num() {
//		if(StringUtils.isEmpty(ask_num))
//			ask_num = User.getUser_no();
//		return ask_num;
//	}
//	public String getArea_code() {
//		if(StringUtils.isEmpty(area_code))
//			area_code = User.getLogin_area_code();
//		return area_code;
//	}
//	
////	public String getProduct_id() {
////		if(StringUtils.isEmpty(area_code))
////			product_id = User.getProduct_id();
////		return product_id;
////	}
//	public void save(){
//		try{
//			DynamicDict dict = data.getRuleDictData();
//			String insertSql ="insert into TWB_PRE_QUERY a (a.pre_ord_no,a.serv_no,a.user_name,a.user_no,a.state,a.area_code,a.oper_time,a.col1,a.col2,a.col3,a.ask_num) values(?,?,?,?,'004',?,sysdate,?,?,?,?)";
//			ArrayList sqlParams = new ArrayList();
//			sqlParams.add(this.getPre_ord_no());
//			sqlParams.add(this.getServ_no());
//			sqlParams.add(this.getUser_name());
//			sqlParams.add(this.getUser_no());
//			sqlParams.add(this.getArea_code());
//			sqlParams.add(this.col1);
//			sqlParams.add(this.col2);
//			sqlParams.add(this.col3);
//			sqlParams.add(this.getAsk_num());
//			SqlMapExe sqlExe = new SqlMapExe(dict.GetConnection());
//			sqlExe.excuteUpdate(insertSql, sqlParams);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	
//}
