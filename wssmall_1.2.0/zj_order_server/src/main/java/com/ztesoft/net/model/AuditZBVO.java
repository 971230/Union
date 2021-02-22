package com.ztesoft.net.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
/**
 * @Description es_order_audit_zb
 * @author YanPJ
 * @Date 2016年12月29日
 */
public class AuditZBVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String out_tid;//外部订单号
	private String zb_id;//总部订单ID
	private String audit_status;//审核状态：0审核失败，1审核成功
	private String audit_status_n;
	private Date audit_time;//审核时间
	private Integer audit_num;//审核次数
	private String audit_type;//审核类型：auto自动，hand手动
	private String audit_type_n;
	private String oper_id;//手动审核工号
	private String crawler_status;//审核状态：0审核失败，1审核成功
	private String crawler_status_n;
	private Date crawler_time;//审核时间
	private String crawler_result;
	private String source_from;
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getZb_id() {
		return zb_id;
	}
	public void setZb_id(String zb_id) {
		this.zb_id = zb_id;
	}
	public String getAudit_status() {
		return audit_status;
	}
	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	public Date getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}
	public Integer getAudit_num() {
		return audit_num;
	}
	public void setAudit_num(Integer audit_num) {
		this.audit_num = audit_num;
	}
	public String getAudit_type() {
		return audit_type;
	}
	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAudit_status_n() {
		if(!StringUtils.isEmpty(audit_status)){
			audit_status_n = getCacheName("ZB_AUDIT_STATUS",audit_status);
		}else{
			audit_status_n = "未审核";
		}
		return audit_status_n;
	}
	public void setAudit_status_n(String audit_status_n) {
		this.audit_status_n = audit_status_n;
	}
	public String getAudit_type_n() {
			audit_type_n = getCacheName("ZB_AUDIT_TYPE",audit_type);
		return audit_type_n;
	}
	public void setAudit_type_n(String audit_type_n) {
		this.audit_type_n = audit_type_n;
	}
	public String getCrawler_status() {
		return crawler_status;
	}
	public void setCrawler_status(String crawler_status) {
		this.crawler_status = crawler_status;
	}
	public String getCrawler_status_n() {
		if(!StringUtils.isEmpty(crawler_status)){
			crawler_status_n = getCacheName("ZB_CRAWLER_STATUS",crawler_status);
		}else{
			crawler_status_n = "未抓取";
		}
		return crawler_status_n;
	}
	public void setCrawler_status_n(String crawler_status_n) {
		this.crawler_status_n = crawler_status_n;
	}
	public Date getCrawler_time() {
		return crawler_time;
	}
	public void setCrawler_time(Date crawler_time) {
		this.crawler_time = crawler_time;
	}
	
	public String getCrawler_result() {
		return crawler_result;
	}
	public void setCrawler_result(String crawler_result) {
		this.crawler_result = crawler_result;
	}
	public String getCacheName(String code,String key){
		String  cacheName = "";
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List list = dc.loadData(code);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if(value.equals(key)){
					cacheName = (String) map.get("value_desc");
				}
				
			}
		}
		
		return cacheName;
	}
	
}