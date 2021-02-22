package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import params.ZteRequest;
import params.ZteResponse;
import rule.RuleInvoker;
import zte.params.req.MidDataProcessReq;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.EPlatform;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.ImportLog;
import com.ztesoft.net.mall.core.model.ImportMidData;
import com.ztesoft.net.mall.core.model.ImportTemplate;
import com.ztesoft.net.mall.core.model.TemplateCell;
import com.ztesoft.net.mall.core.model.TemplateColumn;
import com.ztesoft.net.mall.core.service.IImportManager;
import com.ztesoft.net.mall.core.utils.ExcelUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.OSUtils;
import com.ztesoft.net.sqls.SF;

public class ImportManager extends BaseSupport implements IImportManager {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List listSubTypes(String type_id) {
		String sql = SF.taskSql("SUB_GOODS_TYPE");
		List subTypes = this.baseDaoSupport.queryForList(sql, type_id);
		return subTypes;
	}
	
	@Override
	public Page importAlreadyUploadData(Map<String, String> params, int pageNum,int pageSize){
		StringBuilder sql = new StringBuilder(SF.taskSql("IMPORT_ALREADY_UPLOAD_DATA"));
		String batch_id = Const.getStrValue(params, "batch_id");
		String status = Const.getStrValue(params, "status");
		if(!StringUtils.isEmpty(batch_id)){
			sql.append(" and m.batch_id='"+batch_id+"' ");
		}
		if(!StringUtils.isEmpty(status)){
			sql.append(" and m.status='"+status+"' ");
		}
		sql.append(" order by m.created_time desc ");
		Page page = this.baseDaoSupport.qryForPage(sql.toString(), pageNum, pageSize, ImportMidData.class);
		return page;
	}
	
	@Override
	public Page importLogList(Map<String, String> params, int pageNum,int pageSize){
		StringBuilder sql = new StringBuilder(SF.taskSql("IMPORT_LOG_LIST"));
		String batch_id = Const.getStrValue(params, "batch_id");
		if(!StringUtils.isEmpty(batch_id)){
			sql.append(" and t.batch_id='"+batch_id+"' ");
		}
		sql.append(" order by t.created_time desc ");
		Page page = this.baseDaoSupport.qryForPage(sql.toString(), pageNum, pageSize, ImportLog.class);
		return page;
	}

	@Override
	public Page searchImportTemplates(Map<String, String> params, int pageNum,
			int pageSize) {
		
		String type_id = Const.getStrValue(params, "type_id");
		String sub_type_id = Const.getStrValue(params, "sub_type_id");
		String template_name = Const.getStrValue(params, "template_name");
		String status = Const.getStrValue(params, "status");
		
		StringBuilder sql = new StringBuilder(SF.taskSql("IMPORT_TEMPLATE_LIST"));
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(type_id)){
			sql.append(" and t.type_id=? ");
			pList.add(type_id);
		}
		if(!StringUtils.isEmpty(sub_type_id)){
			sql.append(" and t.sub_type_id=? ");
			pList.add(sub_type_id);
		}
		if(!StringUtils.isEmpty(template_name)){
			sql.append(" and t.template_name like ? ");
			pList.add("%"+template_name+"%");
		}
		if(!StringUtils.isEmpty(status)){
			sql.append(" and t.status=? ");
			pList.add(status);
		}
		String countSql = "select count(*) from("+sql.toString()+")";
		sql.append(" order by t.created_time desc ");
		Page page = this.baseDaoSupport.queryForCPage(sql.toString(), pageNum, pageSize, ImportTemplate.class, countSql, pList.toArray());
		return page;
	}

	@Override
	public ImportTemplate getTemplate(String template_id) {
		String sql = SF.taskSql("IMPORT_TEMPLATE")+" and template_id=? ";
		ImportTemplate template = (ImportTemplate) this.baseDaoSupport.queryForObject(sql, ImportTemplate.class, template_id);
		String columnJsonStr = template.getTemplate_columns();
		TemplateColumn column = converFormString(columnJsonStr);
		template.setColumn(column);
		return template;
	}
	
	public TemplateColumn converFormString(String columns){
		if (columns == null || "".equals(columns) || "[]".equals(columns)||"null".equals(columns))
			return null;
		Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("cells", TemplateCell.class); 
		Object obj = null;
		try{
			JSONArray jsonArray = JSONArray.fromObject(columns);

			obj = JSONArray.toArray(jsonArray, TemplateColumn.class, classMap);

			if (obj == null)
				return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		TemplateColumn[] tfs = (TemplateColumn[])obj;
		return tfs[0];
	}

	@Override
	public void saveAdd(ImportTemplate template) {
		try{
			String columns = processColumnsJson();
			template.setTemplate_columns(columns);
			String template_id = this.baseDaoSupport.getSequences("S_ES_IMPORT_TEMPLATE");
			template.setTemplate_id(template_id);
			template.setStatus("00A");
			this.baseDaoSupport.insert("es_import_template", template);
			
			//写规则表
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("模板保存失败："+e.getMessage());
		}
	}
	
	@Override
	public void saveEdit(ImportTemplate template){
		try{
			String columns = processColumnsJson();
			template.setTemplate_columns(columns);
			
			Map where = new HashMap();
			where.put("template_id", template.getTemplate_id());
			this.baseDaoSupport.update("es_import_template", template, where);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("模板修改失败："+e.getMessage());
		}
	}
	
	public String processColumnsJson(){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String name = request.getParameter("name");
		String[] cnames = request.getParameterValues("cname");
		String[] enames = request.getParameterValues("ename");
		TemplateColumn column = new TemplateColumn();
		column.setName(name);
		
		List<TemplateCell> cells = new ArrayList<TemplateCell>();
		int size = cnames.length;
		for(int i=0;i<size;i++){
			String cname = cnames[i];
			String ename = enames[i];
			
			TemplateCell cell = new TemplateCell();
			cell.setCname(cname);
			cell.setEname(ename);
			cells.add(cell);
		}
		column.setCells(cells);
		return "["+JSON.toJSONString(column)+"]";
	}

	@Override
	public void deleteTemplate(String template_id) {
		try{
			template_id = Const.getInWhereCond(template_id);
			StringBuilder sql = new StringBuilder(SF.taskSql("IMPORT_TEMPLATE_DELETE"));
			if(!StringUtils.isEmpty(template_id)){
				sql.append(" and template_id in("+template_id+")");
			}
			this.baseDaoSupport.execute(sql.toString(), null);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("删除失败："+e.getMessage());
		}
	}
	
	@Override
	public List<ImportTemplate> listImportTemplates(){
		String sql = SF.taskSql("IMPORT_TEMPLATE_LIST")+" and status='00A' order by created_time desc";
		List<ImportTemplate> templates = this.baseDaoSupport.queryForList(sql, ImportTemplate.class, null);
		return templates;
	}

	@Override
	public TemplateColumn getTemplateColumns(String template_id) {
		String sql = SF.taskSql("IMPORT_TEMPLATE_COLUMN");
		String columns = this.baseDaoSupport.queryForString(sql, template_id);
		TemplateColumn column = converFormString(columns);
		return column;
	}

	@Override
	public boolean importFile(File file, Map<String,String> params) {
		boolean isSuccess = false;
		List<ImportMidData> datas = null;
		try{
			long startTime=System.currentTimeMillis();   //获取开始时间
			String template_id = Const.getStrValue(params, "template_id");
			String fileName = Const.getStrValue(params, "fileName");
			String batch_id = this.baseDaoSupport.getSequences("S_ES_IMPORT_BATCH");
			
			//读取上传文件类容
			if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
				datas = ExcelUtils.readExcel(file, fileName);
			}
			else if(fileName.endsWith(".csv")){
				datas = ExcelUtils.readCsv(file, fileName);
			}
			
			String log_id = this.baseDaoSupport.getSequences("S_ES_IMPORT_LOG");
			//写中间数据表
			//int count = addImportMidData(datas,fileName,batch_id,log_id,template_id);
			batchInsert(datas,fileName,batch_id,log_id,template_id);
			//写日志表
			addImportLog(fileName,batch_id,log_id,datas.size());
			isSuccess = true;
			long endTime=System.currentTimeMillis(); //获取结束时间
			logger.info("导入数据使用时间： "+(endTime-startTime)+"ms");
		}catch(Exception e){
			e.printStackTrace();
			isSuccess = false;
			throw new RuntimeException("导入失败："+e.getMessage());
		}
		return isSuccess;
	}
	
	public int addImportMidData(List<ImportMidData> datas,String fileName,String batch_id,String log_id,String template_id){
		int count = 0;
		try{
			if(datas==null || datas.size()==0)
				return count;
			for(ImportMidData data: datas){
				data.setBatch_id(batch_id);
				data.setCreated_time(DBTUtil.current());
				data.setDeal_time(DBTUtil.current());
				data.setFile_name(fileName);
				data.setTemplate_id(template_id);
				data.setLog_id(log_id);
				data.setOper_id(ManagerUtils.getUserId());
				data.setDeal_time(DBTUtil.current());
				data.setStatus("0");
				this.baseDaoSupport.insert("es_import_mid_data", data);
				
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
	public void batchInsert(List<ImportMidData> datas,String file_name,String batch_id,String log_id,String template_id){
		final List<ImportMidData> batchDatas = datas;
		final String batch_file_name = file_name;
		final String batch_batch_id = batch_id;
		final String batch_log_id = log_id;
		final String batch_template_id = template_id;
		final String oper_id = ManagerUtils.getUserId();
		final String source_from = ManagerUtils.getSourceFrom();
		final String sql = SF.taskSql("IMPORT_MID_DATA_INSERT");
		int[] result = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ImportMidData data = batchDatas.get(i);
				ps.setString(1, batch_log_id);
				ps.setString(2, batch_batch_id);
				ps.setString(3, batch_file_name);
				ps.setString(4, batch_template_id);
				ps.setString(5, data.getData_json());
				ps.setString(6, oper_id);
				ps.setString(7, Consts.MID_DATA_STATUS_0);
				ps.setString(8, "导入");
				ps.setString(9, source_from);
				ps.setString(10, data.getId());
			}

			@Override
			public int getBatchSize() {
				return batchDatas.size();
			}
		});
	}
	
	public String addImportLog(String fileName,String batch_id,String log_id,Integer total_num){
		ImportLog log = new ImportLog();
		log.setLog_id(log_id);
		log.setBatch_id(batch_id);
		log.setFile_name(fileName);
		log.setCreated_time(DBTUtil.current());
		log.setTotal_num(total_num);
		log.setFail_num(0);
		log.setSucc_num(0);
		log.setWait_num(total_num);
		this.baseDaoSupport.insert("es_import_logs", log);
		return log_id;
	}

	@Override
	public List<ImportMidData> listMidDatas(String template_id, int max_num) {
		String sql = SF.taskSql("MID_DATA_LIST")+" and status='"+Consts.MID_DATA_STATUS_0+"' and template_id=?";
		String countSql = "select count(*) from("+sql+")";
		Page page = this.baseDaoSupport.queryForCPage(sql, 1, max_num, ImportMidData.class, countSql,template_id);
		return page.getResult();
	}

	@Override
	public boolean haveLocked(String id) {
		String sql = SF.taskSql("MID_DATA_LIST")+" and status='"+Consts.MID_DATA_STATUS_3+"' and id=?";
		List<ImportMidData> datas = this.baseDaoSupport.queryForList(sql, ImportMidData.class, id);
		if(datas!=null && datas.size()>0)
			return true;
		return false;
	}

	@Override
	public void lock(String id) {
		List pList = new ArrayList();
		pList.add(Consts.MID_DATA_STATUS_3);
		pList.add("处理中");
		pList.add(id);
		this.baseDaoSupport.execute(SF.taskSql("MID_DATA_STATUS_UPDATE"), pList.toArray());
	}

	@Override
	public void unLock(String id) {
		List pList = new ArrayList();
		pList.add(Consts.MID_DATA_STATUS_0);
		pList.add("");
		pList.add(id);
		this.baseDaoSupport.execute(SF.taskSql("MID_DATA_STATUS_UPDATE"), pList.toArray());
	}

	@Override
	public void modifyMidDataStatus(String log_id,String id, String status,String desc) {
		List pList = new ArrayList();
		pList.add(status);
		pList.add(desc);
		pList.add(id);
		this.baseDaoSupport.execute(SF.taskSql("MID_DATA_STATUS_UPDATE"), pList.toArray());
		if(Consts.MID_DATA_STATUS_1.equals(status)){
			this.baseDaoSupport.execute(SF.taskSql("LOG_SUCC_NUM_UPDATE"), log_id);
		}
		else{
			this.baseDaoSupport.execute(SF.taskSql("LOG_FAIL_NUM_UPDATE"), log_id);
		}
	}
	
	@Override
	public String getSequence(String sequence){
		return this.baseDaoSupport.getSequences(sequence);
	}
	
	@Override
	public int getSysMaxThreadNum(){
		EPlatform platform = OSUtils.getOSname();
		String memory = platform.getMaxProcessMemory();
		
		return 0;
	}

	@Override
	public void test(){
		String source_from = ManagerUtils.getSourceFrom();
		List<ImportTemplate> templates = this.listImportTemplates();
		for(int i=0;i<templates.size();i++){
			ImportTemplate template = templates.get(i);
			//多线程开启
			int thread_num = (template.getThread_num()==null || template.getThread_num()==0) ? 500 : template.getThread_num();
			ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, thread_num);
			
			try {
				//获取消息队列集
				MidDataProcessReq req = null;
				String template_id = template.getTemplate_id();
				String rule_java = template.getRule_java();
				int max_num = (template.getMax_num()==null || template.getMax_num()==0) ? 500 : template.getMax_num();
				List<ImportMidData> datas = this.listMidDatas(template_id, max_num);
				for (ImportMidData data : datas) {
					
					final String id = data.getId();
					final String json = data.getData_json();
					
					req = new MidDataProcessReq();
					req.setId(id);
					req.setLog_id(data.getLog_id());
					req.setJson(json);
					req.setSource_from(source_from);
					req.setExec_rule(rule_java);
					//并发控制
					if(!this.haveLocked(id)){
						this.lock(id);
						TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
							@Override
							public ZteResponse execute(ZteRequest zteRequest) {
								try {
									//调用规则
									RuleInvoker.importProcess((MidDataProcessReq)zteRequest);
								} catch (Exception e) {
									System.out.print("Exception  id = " + id);
									//this.unLock(id);  //出异常解锁
									e.printStackTrace();
								}
								return new ZteResponse();
							}
						});
						//放入任务池
						ThreadPoolFactory.submit(taskThreadPool, executor);  
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//关闭
				ThreadPoolFactory.closeThreadPool(executor);
			}
		}
	}
}
