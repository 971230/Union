package com.ztesoft.net.mall.core.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.ImportMidData;
import com.ztesoft.net.mall.core.model.ImportTemplate;
import com.ztesoft.net.mall.core.model.TemplateColumn;

public interface IImportManager {

	public List listSubTypes(String type_id);
	
	/**
	 * 导入日志列表
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page importLogList(Map<String,String> params,int pageNum,int pageSize);
	/**
	 * 已经批量导入的数据
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page importAlreadyUploadData(Map<String,String> params,int pageNum,int pageSize);
	
	public Page searchImportTemplates(Map<String,String> params,int pageNum,int pageSize);
	
	public ImportTemplate getTemplate(String template_id);
	
	public void saveAdd(ImportTemplate template);
	
	public void saveEdit(ImportTemplate template);
	
	public void deleteTemplate(String template_id);
	
	public List<ImportTemplate> listImportTemplates();
	
	public TemplateColumn getTemplateColumns(String template_id);
	
	public boolean importFile(File file,Map<String,String> params);
	
	public List<ImportMidData> listMidDatas(String template_id,int max_num);
	
	public boolean haveLocked(String id);
	
	public void lock(String id);
	
	public void unLock(String id);
	
	public void modifyMidDataStatus(String log_id,String id,String status,String desc);
	
	public String getSequence(String sequence);
	
	public int getSysMaxThreadNum();
	
	public void test();
	
}
