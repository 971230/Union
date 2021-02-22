package com.ztesoft.net.mall.core.action.backend;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.ImportTemplate;
import com.ztesoft.net.mall.core.model.TemplateColumn;
import com.ztesoft.net.mall.core.service.IImportManager;

@SuppressWarnings("all")
public class ImportAction extends WWAction {

	private String template_id;
	private String template_type;
	private String type_id;//上传商品大类
	private String fileName;
	private int max_thread_num;
	private File file;
	private List subTypes;
	private List<ImportTemplate> templateList;
	private ImportTemplate template;
	private TemplateColumn column;
	private Map<String,String> params = new HashMap<String,String>(0);
	private IImportManager importManager;
	
	private String actionName;
	private Boolean is_view;

	public String templateConfig(){
		actionName = "import!saveAdd.do";
		int cpuCoreNumber = Runtime.getRuntime().availableProcessors();
		this.max_thread_num = cpuCoreNumber * 50;
		return "import_template_config";
	}
	
	public String loadSubType(){
		subTypes = this.importManager.listSubTypes(type_id);
		return "sub_type_id";
	}
	//批量导入日志
	public String importLogList(){
		this.actionName = "import!importLogList.do";
		this.webpage = this.importManager.importLogList(params, this.getPage(),this.getPageSize());
		return "import_Log_List";
	}
	//已批量导入的数据
	public String importAlreadyUploadData(){
		this.actionName = "import!importAlreadyUploadData.do";
		this.webpage = this.importManager.importAlreadyUploadData(params, this.getPage(),this.getPageSize());
		return "import_already_upload_data";
	}
	
	public String searchImportTemplates(){
		this.actionName = "import!searchImportTemplates.do";
		this.webpage = this.importManager.searchImportTemplates(params, this.getPage(), this.getPageSize());
		return "import_template_list";
	}
	
	public String editTemplate(){
		this.is_view = false;
		actionName = "import!saveEdit.do";
		int cpuCoreNumber = Runtime.getRuntime().availableProcessors();
		this.max_thread_num = cpuCoreNumber * 50;
		this.template = this.importManager.getTemplate(template_id);
		this.column = this.template.getColumn();
		return "import_template_config";
	}
	
	public String viewTemplate(){
		this.is_view = true;
		this.template = this.importManager.getTemplate(template_id);
		this.column = this.template.getColumn();
		return "import_template_config";
	}
	
	public String deleteTemplate(){
		try{
			this.importManager.deleteTemplate(template_id);
			this.json = "{'result':'1','message':'操作成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':'0','message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveAdd(){
		try{
			this.importManager.saveAdd(template);
			this.json = "{'result':'1','message':'操作成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':'0','message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveEdit(){
		try{
			this.importManager.saveEdit(template);
			this.json = "{'result':'1','message':'操作成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':'0','message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String importDownloadFile(){
		this.templateList = this.importManager.listImportTemplates();
		return "import_download_file";
	}
	
	public String importFile(){
		try{
			fileName = URLDecoder.decode(fileName,"UTF-8");
			params.put("fileName", fileName);
			params.put("template_id", template_id);
			importManager.importFile(file, params);
			this.json = "{'result':'1','message':'操作成功'}";
		}catch(Exception e){
			this.json = "{'result':'0','message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public IImportManager getImportManager() {
		return importManager;
	}

	public void setImportManager(IImportManager importManager) {
		this.importManager = importManager;
	}

	public List getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List subTypes) {
		this.subTypes = subTypes;
	}

	public ImportTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ImportTemplate template) {
		this.template = template;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public TemplateColumn getColumn() {
		return column;
	}

	public void setColumn(TemplateColumn column) {
		this.column = column;
	}

	public List<ImportTemplate> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<ImportTemplate> templateList) {
		this.templateList = templateList;
	}

	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getIs_view() {
		return is_view;
	}

	public void setIs_view(Boolean is_view) {
		this.is_view = is_view;
	}

	public int getMax_thread_num() {
		return max_thread_num;
	}

	public void setMax_thread_num(int max_thread_num) {
		this.max_thread_num = max_thread_num;
	}
	
}
