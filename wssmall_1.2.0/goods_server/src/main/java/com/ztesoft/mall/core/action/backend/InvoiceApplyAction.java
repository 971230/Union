package com.ztesoft.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IInvoiceApplyManager;

public class InvoiceApplyAction extends WWAction {
	private IInvoiceApplyManager invoiceApplyManager;
	private Integer id;
	private String reson;
	public String list(){
		this.webpage =invoiceApplyManager.list(this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String agree(){
		try{
			this.invoiceApplyManager.updateState(id, 1);
			this.json ="{result:1}";
		}catch(RuntimeException e){
			e.printStackTrace();
			this.logger.info(e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	} 
 
	public String refuse(){
		try{
			this.invoiceApplyManager.refuse(id, reson);
			this.json ="{result:1}";
		}catch(RuntimeException e){
			e.printStackTrace();
			this.logger.info(e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	} 
 
	
	public String delete(){
		try{
			this.invoiceApplyManager.delete(id);
			this.msgs.add("删除 成功");
		}catch(RuntimeException e){
			this.logger.info(e);
			this.msgs.add("删除失败");
			
		}
		this.urls.put("发票列表", "invocie!list.do");
		return WWAction.MESSAGE;
	}

	public IInvoiceApplyManager getInvoiceApplyManager() {
		return invoiceApplyManager;
	}

	public void setInvoiceApplyManager(IInvoiceApplyManager invoiceApplyManager) {
		this.invoiceApplyManager = invoiceApplyManager;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}
	
}