package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;

/**
 * 商品关联县区实体类
 */
@RequestBeanAnnontion(table_name="es_country",table_archive="yes",cache_store="yes",service_desc="县区管理映射类")
public class Country implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="地市名称",type="String",isNecessary="Y",desc="地市名称")
	private String areadef; 
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="县区编码",type="String",isNecessary="Y",desc="县区编码")
	private String countyid; 
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="地市编码",type="String",isNecessary="Y",desc="地市编码")
	private String areaid; 
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="县区名称",type="String",isNecessary="Y",desc="县区名称")
	private String countyname; 
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="总部县区编码",type="String",isNecessary="Y",desc="总部县区编码")
	private String hq_countyid;
	
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="县分类型",type="String",isNecessary="Y",desc="县分类型")
	private String region_type;

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public String getAreadef() {
		return areadef;
	}

	public void setAreadef(String areadef) {
		this.areadef = areadef;
	}

	public String getCountyid() {
		return countyid;
	}

	public void setCountyid(String countyid) {
		this.countyid = countyid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCountyname() {
		return countyname;
	}

	public void setCountyname(String countyname) {
		this.countyname = countyname;
	}

	public String getHq_countyid() {
		return hq_countyid;
	}

	public void setHq_countyid(String hq_countyid) {
		this.hq_countyid = hq_countyid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	
}
