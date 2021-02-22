package com.ztesoft.net.cms.core.action;

import com.ztesoft.net.app.base.core.model.MultiSite;
import com.ztesoft.net.app.base.core.service.IMultiSiteManager;
import com.ztesoft.net.cms.core.model.DataCat;
import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.ArticlePluginBundle;
import com.ztesoft.net.cms.core.service.IDataCatManager;
import com.ztesoft.net.cms.core.service.IDataFieldManager;
import com.ztesoft.net.cms.core.service.IDataManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.List;
import java.util.Map;

/**
 * 文章管理action
 * 
 * @author kingapex 2010-7-5上午11:22:57
 */
public class DataAction extends WWAction {
	private IDcPublicInfoManager dcPublicInfoManager;
	private IDataFieldManager dataFieldManager;
	private IDataCatManager dataCatManager;
	private IDataManager dataManager;
	private ArticlePluginBundle articlePluginBundle;
	private IMultiSiteManager multiSiteManager;
	private String dataid;
	private String catid;
	private Integer modelid;
	private List<DataField> fieldList;

	private DataCat cat;
	private String searchField;	//	要搜索的字段
	private String searchText;	//	要搜索的标题
	private List<DataCat> catList;
	private Map article;
	private boolean isEdit;

	private Integer siteid;
	private String source_from;
	private String sourcefrom;
	private List sourceFromList;
	
	private String[] ids;
	private Integer[] sorts;

	public String updateSort() {
		try {
			this.dataManager.updateSort(ids, sorts, catid, source_from);
			this.json = "{result:1}";
		} catch (Exception e) {
			this.logger.info(e);
			this.json = "{result:0,message:'" + e.getMessage() + "'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String add() {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		this.sourceFromList = dcPublicCache.getList("10003");
		this.isEdit = false;
		this.cat = this.dataCatManager.get(catid,source_from);
		this.catList = this.dataCatManager.listAllChildren(catid);
		// this.catList.add(0,cat);
		this.modelid = cat.getModel_id();
		this.fieldList = dataFieldManager.listByCatId(catid,source_from);
		for (DataField field : fieldList) {
			field.setInputHtml(articlePluginBundle.onDisplay(field, null));
		}
		return "input";
	}

	/**
	 * 导入数据列表
	 * 
	 * @return
	 */
	public String implist() {
		Integer sitecode = 100000;
		if (siteid != null) {
			MultiSite site = this.multiSiteManager.get(siteid);
			sitecode = site.getCode();
		}
		this.webpage = this.dataManager
				.list(catid, this.getPage(), 5, sitecode);
		cat = this.dataCatManager.get(catid,source_from);
		fieldList = this.dataFieldManager.listIsShow(cat.getModel_id());
		return "implist";
	}

	public String importdata() {
		this.dataManager.importdata(catid, ids);
		this.json = "{result:0}";
		return WWAction.JSON_MESSAGE;
	}

	public String edit() {
		this.isEdit = true;
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		this.sourceFromList = dcPublicCache.getList("10003");
		this.article = this.dataManager.get(dataid,catid,source_from,false);
		if (owner(this.article.get("site_code"))) {
			this.cat = this.dataCatManager.get(catid,source_from);
			this.catList = this.dataCatManager.listAllChildren("0");
			// this.catList.add(0,cat);
			this.modelid = cat.getModel_id();
			this.fieldList = dataFieldManager.listByCatId(catid,source_from);
			for (DataField field : fieldList) {
				field.setInputHtml(articlePluginBundle.onDisplay(field, article
						.get(field.getEnglish_name())));
			}
			return "input";
		} else {
			this.msgs.add("非本站内容，不能编辑！");
			this.urls.put("文章列表", "data!list.do?catid=" + catid);
			return WWAction.MESSAGE;
		}
	}

	public String saveAdd() {
		
		this.dataManager.add(modelid, catid);
		this.msgs.add("文章添加成功");
		this.urls.put("文章列表", "data!list.do?catid=" + catid+"&source_from="+this.source_from);
		return WWAction.MESSAGE;
	}

	public String saveEdit() {
		this.dataManager.edit(modelid, catid, dataid,source_from);
		
		this.msgs.add("文章修改成功");
		this.urls.put("文章列表", "data!list.do?catid=" + catid+"&source_from="+this.source_from);
		return WWAction.MESSAGE;
	}
	
	private EopSite site;

	public EopSite getSite() {
		return site;
	}

	public void setSite(EopSite site) {
		this.site = site;
	}

	public String list() {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		this.sourceFromList = dcPublicCache.getList("10003");
		site = EopContext.getContext().getCurrentSite();
		String term = "";
		if(this.searchText!=null)
//			term = " and " + this.PAGE_KEYWORDS + " like '%" + this.searchText + "%' ";
			term = " and page_keywords like '%" + this.searchText + "%' "; 
		if(this.source_from!=null){
			term += " and source_from = '" + this.source_from + "' "; 
		}else{
			term += " and source_from = '" + ManagerUtils.getSourceFrom() + "' "; 
		}
		this.webpage = this.dataManager.listAll(catid,source_from, term, null, false, this
				.getPage(), this.getPageSize());
		cat = this.dataCatManager.get(catid,source_from);
		this.sourcefrom = source_from; 
		fieldList = this.dataFieldManager.listIsShow(cat.getModel_id(),source_from);
		return "list";
	}

	public String dlgList() {
		this.webpage = this.dataManager.listAll(catid,source_from, null, this.getPage(), 15);
		cat = this.dataCatManager.get(catid,source_from);
		fieldList = this.dataFieldManager.listIsShow(cat.getModel_id());
		return "dlglist";
	}

	private boolean owner(Object site_code) {
		if (EopContext.getContext().getCurrentSite().getMulti_site() == 0) {// 未开启多站点
			return true;
		} else {
			if (site_code == null) {
				return true;
			} else if (site_code.toString().equals(
					EopContext.getContext().getCurrentChildSite().getCode()
							.toString())) {
				return true; // 属于本站
			} else {
				return false; // 不属于本站
			}
		}
	}

	public String delete() {
		this.article = this.dataManager.get(dataid, catid,source_from, false);
		if (this.article.get("sys_lock").toString().equals("1")) {
			this.msgs.add("此文章为系统文章，不能删除！");
		} else {
			if (owner(this.article.get("site_code"))) {
				this.dataManager.delete(catid, dataid,source_from);
				this.msgs.add("文章删除成功");
			} else {
				this.msgs.add("非本站内容，不能删除！");
			}
		}
		this.urls.put("文章列表", "data!list.do?catid=" + catid+"&source_from="+this.source_from);
		return WWAction.MESSAGE;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public IDataFieldManager getDataFieldManager() {
		return dataFieldManager;
	}

	public void setDataFieldManager(IDataFieldManager dataFieldManager) {
		this.dataFieldManager = dataFieldManager;
	}

	public List getFieldList() {
		return fieldList;
	}

	public Integer getModelid() {
		return modelid;
	}

	public void setModelid(Integer modelid) {
		this.modelid = modelid;
	}

	public IDataCatManager getDataCatManager() {
		return dataCatManager;
	}

	public void setDataCatManager(IDataCatManager dataCatManager) {
		this.dataCatManager = dataCatManager;
	}

	public DataCat getCat() {
		return cat;
	}

	public void setCat(DataCat cat) {
		this.cat = cat;
	}

	public IDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	public ArticlePluginBundle getArticlePluginBundle() {
		return articlePluginBundle;
	}

	public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
		this.articlePluginBundle = articlePluginBundle;
	}

	public void setFieldList(List<DataField> fieldList) {
		this.fieldList = fieldList;
	}

	public Map getArticle() {
		return article;
	}

	public void setArticle(Map article) {
		this.article = article;
	}

	public boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Integer[] getSorts() {
		return sorts;
	}

	public void setSorts(Integer[] sorts) {
		this.sorts = sorts;
	}

	public List<DataCat> getCatList() {
		return catList;
	}

	public void setCatList(List<DataCat> catList) {
		this.catList = catList;
	}

	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	public IMultiSiteManager getMultiSiteManager() {
		return multiSiteManager;
	}

	public void setMultiSiteManager(IMultiSiteManager multiSiteManager) {
		this.multiSiteManager = multiSiteManager;
	}
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	public String getSourcefrom() {
		return sourcefrom;
	}

	public void setSourcefrom(String sourcefrom) {
		this.sourcefrom = sourcefrom;
	}
	
	public List getSourceFromList() {
		return sourceFromList;
	}

	public void setSourceFromList(List sourceFromList) {
		this.sourceFromList = sourceFromList;
	}	
	
}
