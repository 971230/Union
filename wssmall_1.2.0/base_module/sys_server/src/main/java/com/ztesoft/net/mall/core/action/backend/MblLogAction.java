package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.model.OfferVo;
import com.ztesoft.net.mall.core.service.IMblLogManager;

public class MblLogAction extends WWAction{
	private IMblLogManager mblLogManager;
	protected Page webpage;
	private String lan_id;
	private String parnter_phone;
	private String op_name;
	private String phoneno;
	private String staff_name;
	private String start_time;
	private String end_time;
	private String mobile;
	private String staff_code;
	private String seq;
	private String username ;
	private String userlan;
	private String cur_logintime;
	private String dep_name;
	private String email;
	private String desks;
	private String business_name;
	  private List typeList;
	private String business_status;
	private String service_offer_name;
	private OfferVo offer;
	private String city;
	public OfferVo getOffer() {
		return offer;
	}
	public void setOffer(OfferVo offer) {
		this.offer = offer;
	}
	public String getService_offer_name() {
		return service_offer_name;
	}
	public void setService_offer_name(String service_offer_name) {
		this.service_offer_name = service_offer_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	@Override
	public Page getWebpage() {
		return webpage;
	}
	@Override
	public void setWebpage(Page webpage) {
		this.webpage = webpage;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getParnter_phone() {
		return parnter_phone;
	}
	public void setParnter_phone(String parnter_phone) {
		this.parnter_phone = parnter_phone;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public String loginLog(){
		
		
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "序号", "分销商名称", "工号", "工位", "手机号码", "城市",
				"IP地址", "登录时间"};
		String[] content = { "seq", "staff_name", "staff_code", "station", "mobile",
				"city", "ip","create_time"};
		String fileTitle = "工作日志数据导出";

		
		if (excel != null && !"".equals(excel)) {
			if("yes".equals(currentPage)){
				this.webpage=mblLogManager.loginLogList( staff_name,mobile, staff_code, start_time, end_time, this.getPage(), this.getPageSize());
			}else{
				this.webpage=mblLogManager.loginLogList( staff_name,mobile, staff_code, start_time, end_time, 1, 500);
			}
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			this.webpage=mblLogManager.loginLogList( staff_name,mobile, staff_code, start_time, end_time, this.getPage(), 10);
			return "loginLog";
		}
		
	}
	public String staffList(){
		this.webpage=mblLogManager.staffList(username, this.getPage(), 8);
		return "staffList";
	}
	public String workList(){
		this.webpage=mblLogManager.workList(username, this.getPage(),8);
		
		return "workList";
	}
	public String workLog(){
		typeList=mblLogManager.listType();
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "序号", "办理业务名称", "办理业务类型", "办理业务状态", "业务金额", "客户姓名",
				"客户手机号码", "手机品牌", "城市" ,"分销商工号","操作时间"};
		String[] content = { "seq", "business_name", "business_type", "business_status", "price",
				"username", "phoneno", "brand", "city","op_name","accept_time" };
		String fileTitle = "工作日志数据导出";
		if (excel != null && !"".equals(excel)) {
			if("yes".equals(currentPage)){
				this.webpage=mblLogManager.workLogList(business_name, service_offer_name, business_status, phoneno, username,city, start_time, end_time, this.getPage(),this.getPageSize());
			}
			else {
			   this.webpage=mblLogManager.workLogList(business_name, service_offer_name, business_status, phoneno, username,city, start_time, end_time, 1,500);
			}
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			this.webpage=mblLogManager.workLogList(business_name, service_offer_name, business_status, phoneno, username,city, start_time, end_time, this.getPage(),this.getPageSize());
			return "workLog";
		}
		
	}
	
	public String callerLog(){
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "序号", "op_code", "请求时间", "返回时间", "请求渠道"};
		String[] content = { "log_id", "op_code", "req_time", "rsp_time", "source_from"};
		String fileTitle = "接口日志数据导出";
		if (excel != null && !"".equals(excel)) {
			if("yes".equals(currentPage)){
				this.webpage=mblLogManager.callerLog(start_time, end_time, this.getPage(), this.getPageSize());
			}else{
				this.webpage=mblLogManager.callerLog(start_time, end_time, 1, 500);
			}
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
		} else {
			this.webpage = mblLogManager.callerLog(start_time, end_time, this.getPage(),this.getPageSize());
			return "callerLog";
		}
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStaff_code() {
		return staff_code;
	}
	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public IMblLogManager getMblLogManager() {
		return mblLogManager;
	}
	public void setMblLogManager(IMblLogManager mblLogManager) {
		this.mblLogManager = mblLogManager;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getUserlan() {
		return userlan;
	}
	public void setUserlan(String userlan) {
		this.userlan = userlan;
	}
	public String getCur_logintime() {
		return cur_logintime;
	}
	public void setCur_logintime(String cur_logintime) {
		this.cur_logintime = cur_logintime;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesks() {
		return desks;
	}
	public void setDesks(String desks) {
		this.desks = desks;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBusiness_status() {
		return business_status;
	}
	public void setBusiness_status(String business_status) {
		this.business_status = business_status;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public List getTypeList() {
		return typeList;
	}
	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}