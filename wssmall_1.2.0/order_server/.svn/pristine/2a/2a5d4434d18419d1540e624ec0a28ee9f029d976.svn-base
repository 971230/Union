package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import services.LanInf;

import com.ztesoft.net.app.base.core.model.Lan;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IReportManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 云卡激活率
 * @author chen.zewen
 *
 */
public class ActiveCloudReportAction extends WWAction {

    @Resource
    private LanInf lanServ;

	private IReportManager reportManager;

	private String partnerName;
	private String keyWord;
	private String realname;
	private String username ;
	private String userid;
	private String startTime;
	private String endTime;
	private String lan_id;
	private List<Lan> lanList;
	private AdminUser adminUser;
	private Boolean isExport = false;
	private String state;
	
	public String reportList(){
    	this.adminUser = ManagerUtils.getAdminUser();
		//电信员工
    	if (ManagerUtils.isNetStaff()) {
    		lanList = new ArrayList<Lan>();
    		if (ManagerUtils.isProvStaff()) {  // 省员工 
    			lanList.add(new Lan(Consts.LAN_PROV, "全省"));
    			lanList.addAll(this.lanServ.listLan());
    		} else if (ManagerUtils.isLanStaff()){ //本地员工
    			lanList.add(this.lanServ.getLanByID(ManagerUtils.getLanId()));
    		} else {
    			throw new RuntimeException("非法数据，电信员工userid为：" + ManagerUtils.getUserId() + "lan_id可能为空");
    		}
    	} else {
    	//分销商
    		if (StringUtil.isEmpty(realname)) {
    			userid = ManagerUtils.getUserId();
    			username = ManagerUtils.getAdminUser().getUsername();
    			realname = ManagerUtils.getAdminUser().getRealname();
    		}
    	}
    	
		if (StringUtil.isEmpty(startTime)) {
			startTime = DateUtil.toString(new Date(), "yyyy-MM-dd");
		}
		if (StringUtil.isEmpty(endTime)) {
			endTime = DateUtil.toString(new Date(), "yyyy-MM-dd");
		}
    	
		String returnResult = isExport ? "export_report_list" : "report_list";
		this.webpage = reportManager.getCloudActivePage(userid, lan_id == null ? null :new String[]{lan_id}, startTime, endTime, getPage(), getPageSize(), isExport);
		
		if(excel != null && !"".equals(excel)){
			HttpServletRequest request = ServletActionContext.getRequest();
			String[] title = {"本地网","用户工号","用户名称","调拨数量", "激活数量", "激活率"};
			String[] content = {"lan_name","username","realname","all_count", "active_count", "active_rate"};
			String fileTitle = "云卡激活率" + DateUtil.toString(new Date(), "yyyyMMdd");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("fileTitle", fileTitle);
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return returnResult;
		}
		
    }
	
	public String reportDetail() {
		String returnResult = isExport ? "export_report_detail" : "report_detail";
		this.webpage = reportManager.getCloudActiveDetail(userid, new String[]{lan_id}, state, startTime, endTime, getPage(), getPageSize(), isExport);
		if(excel != null && !"".equals(excel)){
			HttpServletRequest request = ServletActionContext.getRequest();
			String[] title = {"销售品名称","本地网","业务号码","状态", "UIM卡号", "调拨时间", "批开金额"}; 	
			String[] content = {"offer_name","lan_name","phone_num","state_name", "uim", "deal_time", "pay_money"};
			String fileTitle = "云卡激活率详情" + DateUtil.toString(new Date(), "yyyyMMdd");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("fileTitle", fileTitle);
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return returnResult;
		}
	}
	

	public IReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(IReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public List<Lan> getLanList() {
		return lanList;
	}

	public void setLanList(List<Lan> lanList) {
		this.lanList = lanList;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Boolean getIsExport() {
		return isExport;
	}

	public void setIsExport(Boolean isExport) {
		this.isExport = isExport;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

	
	
	
}
