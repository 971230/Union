package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import params.req.EsearchGetReq;
import params.req.EsearchLogInfoDateReq;
import params.req.EsearchLogInfoIdsReq;
import params.resp.EsearchGetResp;
import params.resp.EsearchLogInfoDateResp;
import params.resp.EsearchLogInfoIdsResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.service.IInfLogsManager;
import commons.CommonTools;

/**
 * 接口日志action
 * @作者 shen.qiyu
 * @创建日期 2015-11-30 
 * @版本 V 1.0
 */
public class InfLogsAction  extends WWAction {
	@Resource
	private IInfLogsManager infLogsManager;
	private static final long serialVersionUID = 1L;
	protected Map<String,String> params = new HashMap<String,String>();
	protected String ids;
	protected String json;
	protected static final String JSON_MESSAGE = "json_message";
	protected String log_id;//日志id
	protected String out_param;//出参报文
	protected String in_param;//入参报文
	protected String begin_date;//写入开始时间段
	protected String finish_date;//写入结束时间段
	
	public String showInfLogsList(){
		//只查询距离当前日期 1天内的数据
	    /*DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = new GregorianCalendar();
    	date.add(Calendar.DAY_OF_MONTH, -1);
		String startTime = DF.format(date.getTime());
		String endTime = DF.format(new Date());
		if(StringUtil.isEmpty(params.get("start_date"))){
	    	params.put("start_date",startTime);
		}
		if(StringUtil.isEmpty(params.get("end_date"))){
	    	params.put("end_date",endTime);
		}*/
		if (null == params.get("rel_obj_id")) {//内部订单号
			params.put("rel_obj_id", "");
		}
		this.webpage = infLogsManager.queryInfLogsByDates(getPage(), getPageSize(),params);
		return "inf_logs_list";
	}
	
	public String writeEsearchByDays(){
		EsearchLogInfoDateReq req = new EsearchLogInfoDateReq();
		req.setFromTime(begin_date);
		req.setToTime(finish_date);
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		EsearchLogInfoDateResp resp = client.execute(req, EsearchLogInfoDateResp.class);
	    if(StringUtils.equals("0", resp.getError_code())){
			logger.info("begin_date~finish_date:"+begin_date+"~"+finish_date+",写入esearch成功");
			json = "{result:0,message:'写入成功'}";
		}else{
			logger.info("begin_date~finish_date:"+begin_date+"~"+finish_date+",写入esearch失败");
			json = "{result:1,message:'写入失败'}";
		}
		return JSON_MESSAGE;
	}
	
	public String writeEsearchByIds(){
		String[] idStr = ids.split(",");
		List list = new ArrayList();
		for(int i=0;i<idStr.length;i++){
			list.add(idStr[i]);
		}
		EsearchLogInfoIdsReq req = new EsearchLogInfoIdsReq();
		req.setIdList(list);
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		EsearchLogInfoIdsResp resp = client.execute(req, EsearchLogInfoIdsResp.class);
		if(StringUtils.equals("0", resp.getError_code())){
			logger.info("lodids:"+idStr+",写入esearch成功");
			json = "{result:0,message:'写入成功'}";
		}else{
			logger.info("lodids:"+idStr+",写入esearch失败");
			json = "{result:1,message:'写入失败'}";
		}
		return JSON_MESSAGE;	
	}
	
	public String showDays(){
		return "show_days";
	}
	
	public String showEsearch(){
		//调用[报文检索查询]API
		ESearchData esData = new ESearchData();
		esData.setLog_id(log_id);
		
		try {
			EsearchGetReq req = new EsearchGetReq();
			req.setEsData(esData);
			ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
			EsearchGetResp resp = client.execute(req, EsearchGetResp.class);
			if(resp.error_code.equals("0")){
				esData = resp.getEsData();
				if(esData == null){
					CommonTools.addError("-9999", "报文没有写入文件系统：报文实例id为"+log_id);
				}
			}else{
				CommonTools.addError("-1", "调用esearch查询接口报错"+log_id+"=="+resp.getError_msg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out_param = esData.getOut_param();
		in_param = esData.getIn_param();
		//如果为模拟抽取关键字操作
		String searchField = super.getRequest().getParameter("search_field");
		String searchId = super.getRequest().getParameter("search_id");
		String searchCode = super.getRequest().getParameter("search_code");
		if(!StringUtil.isEmpty(searchField)){
			super.getRequest().setAttribute("search_field", searchField);
			super.getRequest().setAttribute("search_id", searchId);
			super.getRequest().setAttribute("search_code", searchCode);
			return "extract_specvalues";
		}
		return "show_esearch";
	}
	
	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}

	public String getOut_param() {
		return out_param;
	}

	public void setOut_param(String out_param) {
		this.out_param = out_param;
	}

	public String getIn_param() {
		return in_param;
	}

	public void setIn_param(String in_param) {
		this.in_param = in_param;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}

	public static String getJsonMessage() {
		return JSON_MESSAGE;
	}

	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public IInfLogsManager getInfLogsManager() {
		return infLogsManager;
	}
	
	public void setInfLogsManager(IInfLogsManager infLogsManager) {
		this.infLogsManager = infLogsManager;
	}

}
