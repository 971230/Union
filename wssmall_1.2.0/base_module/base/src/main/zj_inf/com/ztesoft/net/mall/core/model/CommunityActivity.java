package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 小区实体类
 * @author luwei
 *2017-2-22上午11:57:56
 */
@RequestBeanAnnontion(primary_keys="community_code",table_name="es_community",table_archive="yes",cache_store="yes",service_desc="小区管理映射类")
public class CommunityActivity implements Serializable {
	
	 /**
	  * 
	  */
	 private static final long serialVersionUID = 1L;
	
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="小区编码",type="String",isNecessary="Y",desc="小区编码")
	 private String community_code; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="小区名称",type="String",isNecessary="N",desc="小区名称")
	 private String community_name;   
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="地市编码 ",type="String",isNecessary="N",desc="地市编码 ")
	 private String city_code; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="地市 ",type="String",isNecessary="N",desc="地市 ")
	 private String city_name;   
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="行政区域",type="String",isNecessary="N",desc="行政区域")
	 private String area_code; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="行政区域名称 ",type="String",isNecessary="N",desc="行政区域名称 ")
	 private String area_name; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="县分公司ID",type="String",isNecessary="N",desc="县分公司ID")
	 private String county_comp_id; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="县分公司名称",type="String",isNecessary="N",desc="县分公司名称")
	 private String county_comp_name; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="标准地址 ",type="String",isNecessary="N",desc="标准地址 ")
	 private String std_addr; 
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="网格 ",type="String",isNecessary="N",desc="网格 ")
	 private String net_grid;  
	 @RequestFieldAnnontion()
	 @ZteSoftCommentAnnotationParam(name="平台来源",type="String",isNecessary="Y",desc="平台来源")
	 private String source_from;
	 
	 
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getCommunity_code() {
		return community_code;
	}
	public void setCommunity_code(String community_code) {
		this.community_code = community_code;
	}
	public String getCommunity_name() {
		return community_name;
	}
	public void setCommunity_name(String community_name) {
		this.community_name = community_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getCounty_comp_id() {
		return county_comp_id;
	}
	public void setCounty_comp_id(String county_comp_id) {
		this.county_comp_id = county_comp_id;
	}
	public String getCounty_comp_name() {
		return county_comp_name;
	}
	public void setCounty_comp_name(String county_comp_name) {
		this.county_comp_name = county_comp_name;
	}
	public String getStd_addr() {
		return std_addr;
	}
	public void setStd_addr(String std_addr) {
		this.std_addr = std_addr;
	}
	public String getNet_grid() {
		return net_grid;
	}
	public void setNet_grid(String net_grid) {
		this.net_grid = net_grid;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	 
	 
	 
}
