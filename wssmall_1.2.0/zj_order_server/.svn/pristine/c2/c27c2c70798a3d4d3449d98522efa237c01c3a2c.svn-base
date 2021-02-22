package com.ztesoft.net.action;

import java.io.File;
import java.util.Map;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AuditZBParams;
import com.ztesoft.net.model.AuditZBVO;
import com.ztesoft.net.service.IAuditZBManager;


public class AuditZBAction extends WWAction{
	private IAuditZBManager auditZBManager ;
	
	private File file;
	private String file_name;
	
	private String out_tids;
	private AuditZBParams auditZBParams;
	public String showList(){
		this.webpage = auditZBManager.showList(auditZBParams,this.getPage(), this.getPageSize());
		return "out_tid_list";
		
	}
	public String toImport(){
		return "out_tid_import";
		
	}
	/**
	 * 导入数据
	 * @return
	 */
	public String importacion(){
		try {
			long start=System.currentTimeMillis();
			String rtnStr = auditZBManager.importacion(file, file_name);	
			long end=System.currentTimeMillis();
			logger.info("总商单号数据入库花费时间："+(end-start));
			if ("0".equals(rtnStr)) {
				this.json="{result:1,message:'没有数据导入，请检查导入文件内容！'}";
				return this.JSON_MESSAGE;
			}
			String[] rtnArr = rtnStr.split("#");
			this.json="{result:0,message:'此次导入的总数是："+rtnArr[0]+"条'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json="{result:1,message:'导入失败!"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String auditByHand(){
		try {
			String flag = auditZBManager.checkStatus(out_tids);
			if(StringUtil.equals("0", flag)){
				auditZBManager.auditByHand(out_tids);
				this.json="{result:0,message:'审核成功！'}";
			}else{
				this.json="{result:1,message:'请不要选择同步次数小于3次或审核成功的订单！'}";
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			this.json="{result:1,message:'审核失败!"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String qryBSSOrderStatus(){
		this.webpage = auditZBManager.qryBSSOrderStatus(auditZBParams,this.getPage(), this.getPageSize());
		return "qry_bss_order_status";
	}
	
	public IAuditZBManager getAuditZBManager() {
		return auditZBManager;
	}
	public void setAuditZBManager(IAuditZBManager auditZBManager) {
		this.auditZBManager = auditZBManager;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getOut_tids() {
		return out_tids;
	}
	public void setOut_tids(String out_tids) {
		this.out_tids = out_tids;
	}
	public AuditZBParams getAuditZBParams() {
		return auditZBParams;
	}
	public void setAuditZBParams(AuditZBParams auditZBParams) {
		this.auditZBParams = auditZBParams;
	}
}
