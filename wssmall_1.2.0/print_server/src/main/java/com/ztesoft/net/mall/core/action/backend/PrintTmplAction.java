package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import params.print.req.PrintReq;
import params.print.req.PrintTmplConfigListReq;
import service.PrintInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.PrintConfigName;
import com.ztesoft.net.mall.core.model.PrintTmpl;
import com.ztesoft.net.mall.core.model.Printtmplconfig;
import com.ztesoft.net.mall.core.service.IPrintTmplManager;

/**
 * 打印模板
 * 
 * @author lzf<br/>
 *         2010-5-4上午11:10:46<br/>
 *         version 1.0
 */
public class PrintTmplAction extends WWAction {
	
	private IPrintTmplManager printTmplManager;
	private List list;
	private List trash;
	private List listCanUse;
	private String[] id;
	private String prt_tmpl_id;
	private PrintTmpl printTmpl;
	private PrintInf printServ;
	private List<Printtmplconfig> configList;
	private List<PrintConfigName> nameList;
	private String config_id;
	
	public String listPrintItems(){
		PrintReq printReq = new PrintReq();
		printReq.setConfig_id(config_id);
		nameList = printServ.getConfigName(printReq).getConfigItemList();
		//nameList = printtmplconfigManager.getConfigName(config_id);
		return "name_items";
	}
	
	public String list(){
		list = printTmplManager.list();
		return "list";
	}
	
	public String trash(){
		trash = printTmplManager.trash();
		return "trash";
	}
	
	public String listCanUse(){
		listCanUse = printTmplManager.listCanUse();
		return "listCanUse";
	}
	
	public String add(){
		PrintTmplConfigListReq printTmplConfigListReq = new PrintTmplConfigListReq();
		configList = this.printServ.printTpmlConfigList(printTmplConfigListReq).getPrintTmplConfigList();
		
		PrintReq printReq = new PrintReq();
		if(configList!=null && configList.size()>0)
			printReq.setConfig_id(configList.get(0).getConfig_id());
		    nameList = this.printServ.getConfigName(printReq).getConfigItemList();
			//nameList = printtmplconfigManager.getConfigName(configList.get(0).getConfig_id());
		    return "add";
	}
	
	public String saveAdd(){
		try {
			printTmpl.setPrt_tmpl_id(System.currentTimeMillis()+"");
			printTmpl.setPrt_tmpl_data(printTmpl.getPrt_tmpl_data()==null?"":printTmpl.getPrt_tmpl_data().replace("&lt;", "<").replace("‘", "'").replace("＝", "="));
			
			printTmplManager.add(printTmpl);
			this.msgs.add("模板添加成功");
		} catch (Exception e) {
			this.msgs.add("模板添加失败");
			e.printStackTrace();
		}
		this.urls.put("打印模板列表", "printTmpl!list.do");
		return WWAction.MESSAGE;
	}
	
	public String edit(){
		printTmpl = printTmplManager.get(prt_tmpl_id);
		PrintReq printReq = new PrintReq();
		printReq.setConfig_id(printTmpl.getConfig_id());
	   
		PrintTmplConfigListReq printTmplConfigListReq = new PrintTmplConfigListReq();
		configList = this.printServ.printTpmlConfigList(printTmplConfigListReq).getPrintTmplConfigList();
		//configList = printtmplconfigManager.listPrinttmplconfig();
		
		nameList = this.printServ.getConfigName(printReq).getConfigItemList();
		
		return "edit";
	}
	
	public String saveEdit(){
		try {
			printTmpl.setPrt_tmpl_data(printTmpl.getPrt_tmpl_data()==null?"":printTmpl.getPrt_tmpl_data().replace("&lt;", "<").replace("‘", "'").replace("＝", "="));
			
			printTmplManager.edit(printTmpl);
			this.msgs.add("模板修改成功");
		} catch (Exception e) {
			this.msgs.add("模板修改失败");
			e.printStackTrace();
		}
		this.urls.put("打印模板列表", "printTmpl!list.do");
		return WWAction.MESSAGE;
	}
	
	public String delete(){
		try {
			this.printTmplManager.delete(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (Exception e) {
			this.json = "{'result':1;'message':'删除失败'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String revert(){
		try {
			this.printTmplManager.revert(id);
			this.json = "{'result':0,'message':'还原成功'}";
		} catch (Exception e) {
			this.json = "{'result':1;'message':'还原失败'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String clean(){
		try {
			this.printTmplManager.clean(id);
			this.json = "{'result':0,'message':'清除成功'}";
		} catch (Exception e) {
			this.json = "{'result':1;'message':'清除失败'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	public IPrintTmplManager getPrintTmplManager() {
		return printTmplManager;
	}

	public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
		this.printTmplManager = printTmplManager;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List getTrash() {
		return trash;
	}

	public void setTrash(List trash) {
		this.trash = trash;
	}

	public List getListCanUse() {
		return listCanUse;
	}

	public void setListCanUse(List listCanUse) {
		this.listCanUse = listCanUse;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String getPrt_tmpl_id() {
		return prt_tmpl_id;
	}

	public void setPrt_tmpl_id(String prt_tmpl_id) {
		this.prt_tmpl_id = prt_tmpl_id;
	}

	public PrintTmpl getPrintTmpl() {
		return printTmpl;
	}

	public void setPrintTmpl(PrintTmpl printTmpl) {
		this.printTmpl = printTmpl;
	}

	

	public PrintInf getPrintServ() {
		return printServ;
	}

	public void setPrintServ(PrintInf printServ) {
		this.printServ = printServ;
	}

	public List<Printtmplconfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<Printtmplconfig> configList) {
		this.configList = configList;
	}

	public List<PrintConfigName> getNameList() {
		return nameList;
	}

	public void setNameList(List<PrintConfigName> nameList) {
		this.nameList = nameList;
	}

	public String getConfig_id() {
		return config_id;
	}

	public void setConfig_id(String config_id) {
		this.config_id = config_id;
	}

}
