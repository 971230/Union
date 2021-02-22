package com.ztesoft.remote.params.notice.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
/**
 * 
 * @author wui
 * 公告、特白公告请求实体
 * 
 * 查询语句
 * select *
  from es_en_article
 where cat_id in
       (select cat_id from es_data_cat where cat_path like '0|2|9|%')
 order by sort, add_time desc

 *
 */
public class NoticeReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="公告分类标识",type="String",isNecessary="Y",desc="公告分类标识")
	private String catid; //分类

	@ZteSoftCommentAnnotationParam(name="需要展示的页数",type="int",isNecessary="N",desc="需要展示的页数")
	private String indexof; //展示

	@ZteSoftCommentAnnotationParam(name="需要展示的条数",type="int",isNecessary="N",desc="需要展示的条数")
	private String count; //展示数量
	
	@ZteSoftCommentAnnotationParam(name="扩展条件",type="String",isNecessary="N",desc="扩展条件:SQL条件【and...】")
	private String term;  //扩展条件
	
	@ZteSoftCommentAnnotationParam(name="排序字段",type="String",isNecessary="N",desc="排序字段:order by 【orders】")
	private String orders; //排序字段
	
	public String getCatid() {
		return catid;
	}
	public void setCatid(String catid) {
		this.catid = catid;
	}
	public String getIndexof() {
		return indexof;
	}
	public void setIndexof(String indexof) {
		this.indexof = indexof;
	}	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(catid)) {
            throw new ApiRuleException("-1", "分类【catid】不能为空!");
        }
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.notice.queryNotice";
	}
	
}
