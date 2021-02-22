package com.ztesoft.mall.core.action.backend;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.model.Numero;
import com.ztesoft.net.mall.core.service.INumeroGrupoManager;
import com.ztesoft.net.mall.core.service.INumeroManager;
import com.ztesoft.net.mall.core.service.impl.NumeroManager;


@SuppressWarnings("all")
public class NumeroAction extends BaseAction {
	private String numbers;
	private INumeroManager numeroManager;
	private INumeroGrupoManager ngManager;
	private Numero numero;
	private List estados;
	private List ciudads;
	private List grupos;
	private List liberacions;
	private List internets;
	
	private File file;
	private String fileFileName;
	private String listFormActionVal;
	private String busqueda;
	private String batchPublishWay;//号码批量发布方式，amount-按查询结果数量，import-按导入文件
	
	//发布号码弹出框
	public String treeDialog(){
		return "treeDialog";
	}
	
	public String selectShop(){
		return "selectShop";
	}
	
	public String numeroPubtree(){
		this.listFormActionVal = "numero!confirmPublish.do?ajax=yes";
		return "numeroPubtree";
	}
	
	public String numeroBatchPubTree(){
		if("amount".equals(batchPublishWay)){
			//按查询结果数量批量发布
			this.listFormActionVal = "numero!listBatchPublish.do";
		}
		if("import".equals(batchPublishWay)){
			//按文件导入批量发布
			this.listFormActionVal = "numero!importacion.do";
		}
		return "numeroBatchPubTree";
	}

	public String findFirstList(){
		List list = numeroManager.getFirstList(id);
		json = JSON.toJSONString(list).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	//确定发布
	public String confirmPublish(){
		try {
			this.json=numeroManager.publish(ids,numbers,id);
		} catch (Exception e) {
			logger.info(e);
		}
		return JSON_MESSAGE;
	}
	public String list(){
		
		if(StringUtils.isNotBlank(params.get("list"))){
			return importacion();
		}
		ciudads = numeroManager.getCiudads();
		estados =numeroManager.getEstatos();
		internets = numeroManager.getInternet();
		liberacions = numeroManager.getLiberacions();
		grupos = ngManager.getFormList(params);
		
		String flag = params.get("flag");
		webpage = numeroManager.getFormList(getPage(), getPageSize(),params);
		if("choose".equals(flag)){
			return "list";
		}
		return INDEX;
	}
	
	//批量发布号码
	public String listBatch(){
		ciudads = numeroManager.getCiudads();
		estados =numeroManager.getEstatos();
		internets = numeroManager.getInternet();
		liberacions = numeroManager.getLiberacions();
		grupos = ngManager.getFormList(params);
		webpage = numeroManager.getBatchList(getPage(), getPageSize(),params);
		return "listBatch";
	}
	
	//批量发布号码
	public String listBatchPublish(){
		String msg = numeroManager.listBatchPublish(params);
		this.msgs.add(msg);
		this.urls.put("号码列表", "numero!listBatch.do");
		return WWAction.MESSAGE;
	}
	
	public String numeroImportLogsECS(){
		this.webpage = numeroManager.searchNumeroImportLogsECS(params, this.getPage(), this.getPageSize());
		return "numero_import_logs";
	}
	
	public String importacion(){
		
		try {
			params.put("file_name", fileFileName);
			String rtnStr = numeroManager.importacion(file,params);	
			if ("0".equals(rtnStr)) {
				this.msgs.add("没有号码导入，请检查导入文件内容！");
				this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			
			String[] rtnArr = rtnStr.split("#");
			this.msgs.add("此次需导入的号码总数是:" + rtnArr[0] + "个。 批次号为:" + rtnArr[3]);
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("号码导入失败!" + e.getMessage());
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
		}
//		this.numeroManager.importNumero();
		return WWAction.MESSAGE;
		
	}
	
	/**
	 * 号码批量发布-根据文件导入批量发布
	 * @return
	 */
	public String batchPublish(){
		try {
			params.put("file_name", fileFileName);
			String rtnStr = numeroManager.importacion(file,params);	
			if ("0".equals(rtnStr)) {
				this.msgs.add("没有号码导入，请检查导入文件内容！");
				this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			
			String[] rtnArr = rtnStr.split("#");
			this.msgs.add("此次需导入的号码总数是:" + rtnArr[0] + "个。 批次号为:" + rtnArr[3]);
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("号码导入失败");
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
		}
		return WWAction.MESSAGE;
	}
	
	/**
	 * 号码批量修改
	 * @return
	 */
	public String batchModify(){
		try {
			params.put("file_name", fileFileName);
			String rtnStr = numeroManager.importacion(file,params);	
			if ("0".equals(rtnStr)) {
				this.msgs.add("没有号码导入，请检查导入文件内容！");
				this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			String[] rtnArr = rtnStr.split("#");
			this.msgs.add("此次需导入的号码总数是:" + rtnArr[0] + "个。 批次号为:" + rtnArr[3]);
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("号码导入失败");
			this.urls.put("号码导入日志列表", "numero!numeroImportLogsECS.do");
		}
		return WWAction.MESSAGE;
	}
	
	public String dowloadBatchImportTemplate(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/").replaceAll("\\\\", "/");
		if(path.endsWith("\\") || path.endsWith("/")){
			path += "shop/admin/numero/";
		}
		else{
			path += "/shop/admin/numero/";
		}
		String fileName = "号码批量导入模板.xls";
		String fileType = "xls";
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		try {
			DownLoadUtil.downLoadFile(path+fileName,response,fileName,fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String modificar(){
		estados =numeroManager.getEstatos();
		String flag = params.get("flag");
		
		if(StringUtils.isBlank(flag)){
			flag =numeroManager.queryForEstado(ids);;
		}
		if(flag.equals("000")){ 
			estados.clear();
			Map map = new HashMap();
			map.put("key", "111");
			map.put("value", "已使用");
			Map map2 = new HashMap();
			map2.put("key", "00X");
			map2.put("value", "已作废");
			Map map3 = new HashMap();
			map3.put("key", "000");
			map3.put("value", "未使用");
			estados.add(map);
			estados.add(map2);
			estados.add(map3);
		}else if(flag.equals("001")){ 
			estados.clear();
			Map map = new HashMap();
			map.put("key", "111");
			map.put("value", "已使用");
			Map map2 = new HashMap();
			map2.put("key", "00X");
			map2.put("value", "已作废");
			Map map3 = new HashMap();
			map3.put("key", "001");
			map3.put("value", "已预占");
			estados.add(map);
			estados.add(map2);
			estados.add(map3);
		}else if(flag.equals("111")){
			estados.clear();
			Map map = new HashMap();
			map.put("key", "000");
			map.put("value", "未使用");
			Map map2 = new HashMap();
			map2.put("key", "111");
			map2.put("value", "已使用");
			estados.add(map);
			estados.add(map2);
		}else if(flag.equals("00X")){
			estados.clear();
			Map map = new HashMap();
			map.put("key", "000");
			map.put("value", "未使用");
			Map map2 = new HashMap();
			map2.put("key", "00X");
			map2.put("value", "已作废");
			estados.add(map);
			estados.add(map2);
		}
//		000:未使用, 
//		111:已使用, 
//		001:已预占,  
//		00X:已作废
		grupos = ngManager.getFormList(params);
		return "modificar";
	}
	
	public String doModificar(){
		try {
			numeroManager.modificar(ids,params);
			msg.addResult(MESSAGE,"修改成功");
			msg.addResult("url", "numero!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE,"修改失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	/**
	 * 号码同步日志
	 * @return
	 */
	public String numeroSynchLogs(){
		listFormActionVal = "numero!numeroSynchLogs.do";
		this.webpage = this.numeroManager.searchNumeroSynchLogs(params, this.getPage(), this.getPageSize());
		return "numero_synch_logs";
	}
	
	public String publishAgain(){
		listFormActionVal = "numero!numeroSynchLogs.do";
		int count = numeroManager.publishAgain(params);
		if(count>0){
			msg.addResult(MESSAGE,"发布失败号码已成功重发");
			msg.setSuccess(true);
			msg.setResult(0);
		}
		else{
			msg.addResult(MESSAGE,"未找到可以重发的号码批次");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	public void setNumeroManager(NumeroManager numeroManager) {
		this.numeroManager = numeroManager;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public INumeroManager getNumeroManager() {
		return numeroManager;
	}

	public void setNumeroManager(INumeroManager numeroManager) {
		this.numeroManager = numeroManager;
	}

	public Numero getNumero() {
		return numero;
	}

	public void setNumero(Numero numero) {
		this.numero = numero;
	}

	public List getEstados() {
		return estados;
	}

	public void setEstados(List estados) {
		this.estados = estados;
	}

	public List getCiudads() {
		return ciudads;
	}

	public void setCiudads(List ciudads) {
		this.ciudads = ciudads;
	}

	public List getGrupos() {
		return grupos;
	}

	public void setGrupos(List grupos) {
		this.grupos = grupos;
	}

	public List getLiberacions() {
		return liberacions;
	}

	public void setLiberacions(List liberacions) {
		this.liberacions = liberacions;
	}

	public List getInternets() {
		return internets;
	}

	public void setInternets(List internets) {
		this.internets = internets;
	}

	public INumeroGrupoManager getNgManager() {
		return ngManager;
	}

	public void setNgManager(INumeroGrupoManager ngManager) {
		this.ngManager = ngManager;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getBatchPublishWay() {
		return batchPublishWay;
	}

	public void setBatchPublishWay(String batchPublishWay) {
		this.batchPublishWay = batchPublishWay;
	}
	
}
