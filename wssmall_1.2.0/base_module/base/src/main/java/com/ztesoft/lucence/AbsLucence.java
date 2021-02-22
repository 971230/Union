package com.ztesoft.lucence;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

/**
 * lucence全文搜索
 * @作者 MoChunrun
 * @创建日期 2014-7-31 
 * @版本 V 1.0
 */
public abstract class AbsLucence extends BaseSupport{

	public static final int pageSize = 100;
	
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String LINK_URL = "link_url";
	public static final String DATA_TYPE = "data_type";
	public static final String DATA_TYPE_CODE = "data_type_code";
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	
	public String getFilePath()throws Exception{
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> lucencePath = dcPublicCache.getList("201408041003");
        if(lucencePath!=null && lucencePath.size()>0){
        	Object obj = lucencePath.get(0).get("pkey");
        	if(obj==null){
        		throw new Exception("没有配置lucence路径，请在es_dc_public表配置:stype为201408041003");
        	}else{
        		return String.valueOf(obj);
        	}
        }else{
        	throw new Exception("没有配置lucence路径，请在es_dc_public表配置:stype为201408041003");
        }
	}
	
	public abstract List<Map> execute(LucenceConfig config,String startTime,String endTime);
	
	public String perform(LucenceConfig config) throws Exception{
		boolean isCreate = (config.getIs_new()==null || config.getIs_new()==0)?true:false;
		String endTime = DBTUtil.getDBCurrentTime();
		String startTime = "2000-01-01 00:00:00";
		if(!StringUtil.isEmpty(config.getLast_update_time())){
			startTime = config.getLast_update_time().substring(0,config.getLast_update_time().length()-2);
		}
		List<Map> list = this.execute(config, startTime, endTime);
		List<Document> docs = LucenceUtils.createDocumentList(list,config);
		LucenceUtils.create(docs, isCreate,getFilePath());
		return endTime;
	}
	
	public Page search(Map<String,String> params) throws Exception{
		return LucenceUtils.search(params, getFilePath());
	}
	
}
