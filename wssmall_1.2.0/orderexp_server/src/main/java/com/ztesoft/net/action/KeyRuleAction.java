package com.ztesoft.net.action;

import java.util.List;

import model.EsearchSpec;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IExpConfigInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.ExpConfigInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.model.EsearchSpecvaluesRulesQuery;
import com.ztesoft.net.model.SpecvaluesCheckParams;
import com.ztesoft.net.param.inner.ChangeSpecvRuleUpdateExpInstInner;
import com.ztesoft.net.param.inner.ExpInstQueryInner;
import com.ztesoft.net.param.inner.KeyRuleInner;
import com.ztesoft.net.param.inner.SpecvaluesCheckProcessedInner;
import com.ztesoft.net.param.outer.KeyRuleOuter;
import com.ztesoft.net.service.IEsearchSpecDefineManager;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IExpConfigManager;
import com.ztesoft.net.service.IOrderExpManager;

public class KeyRuleAction extends WWAction{
	private IExpConfigManager expConfigManager;
	private IExpConfigInfoManager expConfigInfoManager;

	private IEsearchSpecDefineManager esearchSpecDefineManager;
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	private IOrderExpManager orderExpManager;
	
	private String search_name;//搜索规格名称
	private String search_id;//搜索id
	private String search_code;//搜索编码
	private String id;
	private KeyRuleInner inner;
	private List<EsearchSpec> specDefines;
	private EsearchSpecvaluesRulesQuery keyRule;
	private String flag;//全量抽取关键字按钮开关
	
	public String list(){
		//调用查找关键字规则列表接口
//		if(page == 0){
//			page = 1;
//		}
//		if(pageSize == 0){
//			pageSize = 10;
//		}
		flag = getConfigInfoValueByCfId("EXTRACT_KEYWORD_FLAG");
		KeyRuleInner keyRuleInner = new KeyRuleInner();
		keyRuleInner.setPageIndex(this.getPage());
		keyRuleInner.setPageSize(this.getPageSize());
		keyRuleInner.setSearch_name(search_name);
		keyRuleInner.setSearch_id(search_id);
		keyRuleInner.setSearch_code(search_code);
		KeyRuleOuter keyRuleOuter = expConfigManager.queryKeyRulePage(keyRuleInner);
		webpage = keyRuleOuter.getPage();
		
		return "index";
	}
	
	public String del(){
		try {
			KeyRuleInner inner = new KeyRuleInner();
			inner.setKey_id(id);
			this.expConfigManager.deleteKeyRuleByKeyId(inner);
			this.json = "{'result':0, 'message':'删除成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'删除失败！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String toAdd(){
		//查询规格数据
		try {
			/*SpecDefineQueryInner sdqi = new SpecDefineQueryInner();
			SpecDefineQueryOuter sdqo= this.esearchSpecDefineManager.querySpecDefineListNoCache(sdqi);
			specDefines = sdqo.getSpecDefineList();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "add";
	}
	
	public String add(){
		try {
			this.expConfigManager.addKeyRule(inner);
			//返回参数
			this.json = "{'result':0, 'message':'添加成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'添加失败！'}";
			e.printStackTrace();
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String toUpdate(){	
		KeyRuleInner kri = new KeyRuleInner();
		kri.setKey_id(id);
		KeyRuleOuter kro =  new KeyRuleOuter();
		try {
			kro = this.expConfigManager.findKeyRuleByKeyId(kri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		keyRule = kro.getEsrq();
		return "update";
	}
	
	public String update(){
		try {
			this.expConfigManager.updateKeyRuleByKeyId(inner);
			this.json = "{'result':0, 'message':'更新成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'更新失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	//异常实例列表
	public String orderexpList(){
		//调用异常实例查询接口
		ExpInstQueryInner eqi = new ExpInstQueryInner();
		eqi.setSearch_id(inner.getSearch_id());
		eqi.setRecord_status("0");
		eqi.setMatch_content(inner.getMatch_content());
		eqi.setOut_tid(inner.getOut_tid());//外部单号
		eqi.setExcp_inst_id(inner.getExcp_inst_id());
		eqi.setRel_obj_type("order");//订单类型
		eqi.setHas_match_content(inner.getHas_match_content());
		webpage = this.orderExpManager.queryExpInstPage(eqi, this.getPage(), this.getPageSize());
		
		return "orderexpList";
	}
	//抽取关键字异常更新
	public String expInstUpdate(){
		try {
			List<ExpInstQueryInner> expInsts = inner.getExpInstList();
			ChangeSpecvRuleUpdateExpInstInner csi = new ChangeSpecvRuleUpdateExpInstInner();
			csi.setList(expInsts);
			this.esearchSpecvaluesManager.changeSepcvRuleUpdateExpInst(csi);
			this.json = "{'result':0, 'message':'更新成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 抽取全部关键字
	 * @return
	 */
	public String extractAllKeyword(){
		try {
			expConfigManager.extractAllKeyword();
			this.json = "{'result':0, 'message':'抽取成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;	
	}
	
	//模拟抽取关键字
	public String imitateSpecvalues(){
		String logId = super.getRequest().getParameter("log_id");
		String searchId = super.getRequest().getParameter("search_id");
		String searchCode = super.getRequest().getParameter("search_code");
		try {
			//调用模拟抽取关键字API
			SpecvaluesCheckProcessedInner inner = new SpecvaluesCheckProcessedInner();
			inner.setLog_id(logId);
			inner.setSearch_id(searchId);
			inner.setSearch_code(searchCode);
			SpecvaluesCheckParams params = this.esearchSpecvaluesManager.imitateExtractSpecvalues(inner);
			showSuccessJson(params.getKey_word());
		} catch (Exception e) {
			showErrorJson("抽取关键字失败:"+e.getMessage());
			e.printStackTrace();
		}
		return super.JSON_MESSAGE;
	}

	/**
	 * 
	 * @Description: 
	 * @param cfId
	 * @return  全量抽取关键字按钮开关
	 */
	public String getConfigInfoValueByCfId(String cfId){
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		return cacheUtil.getConfigInfo(cfId);
	}
	
	public String refreshSpecvaluesRules(){
		ExpConfigInfoCacheProxy proxy = new ExpConfigInfoCacheProxy();
		try {
			proxy.refreshSpecvaluesRules();
			this.json = "{'result':0, 'message':'刷新成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;	
	}
	
	public IExpConfigManager getExpConfigManager() {
		return expConfigManager;
	}

	public void setExpConfigManager(IExpConfigManager expConfigManager) {
		this.expConfigManager = expConfigManager;
	}

	public IEsearchSpecDefineManager getEsearchSpecDefineManager() {
		return esearchSpecDefineManager;
	}

	public void setEsearchSpecDefineManager(
			IEsearchSpecDefineManager esearchSpecDefineManager) {
		this.esearchSpecDefineManager = esearchSpecDefineManager;
	}

	public IEsearchSpecvaluesManager getEsearchSpecvaluesManager() {
		return esearchSpecvaluesManager;
	}

	public void setEsearchSpecvaluesManager(
			IEsearchSpecvaluesManager esearchSpecvaluesManager) {
		this.esearchSpecvaluesManager = esearchSpecvaluesManager;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearch_name() {
		return search_name;
	}

	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}

	public String getSearch_id() {
		return search_id;
	}

	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public String getSearch_code() {
		return search_code;
	}

	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}

	public KeyRuleInner getInner() {
		return inner;
	}

	public void setInner(KeyRuleInner inner) {
		this.inner = inner;
	}

	public List<EsearchSpec> getSpecDefines() {
		return specDefines;
	}

	public void setSpecDefines(List<EsearchSpec> specDefines) {
		this.specDefines = specDefines;
	}

	public EsearchSpecvaluesRulesQuery getKeyRule() {
		return keyRule;
	}

	public void setKeyRule(EsearchSpecvaluesRulesQuery keyRule) {
		this.keyRule = keyRule;
	}

	public IOrderExpManager getOrderExpManager() {
		return orderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		this.orderExpManager = orderExpManager;
	}

	public IExpConfigInfoManager getExpConfigInfoManager() {
		return expConfigInfoManager;
	}

	public void setExpConfigInfoManager(IExpConfigInfoManager expConfigInfoManager) {
		this.expConfigInfoManager = expConfigInfoManager;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
