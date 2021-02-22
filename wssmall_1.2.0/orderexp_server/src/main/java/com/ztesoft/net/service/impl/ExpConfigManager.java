package com.ztesoft.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.EsearchSpecvalues;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.ZteError;
import params.req.EsearchSearchReq;
import params.req.QueryExpCatalogReq;
import params.resp.EsearchSearchResp;
import params.resp.QueryExpCatalogResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.cms.page.file.SftpUpLoadImpl;
import com.ztesoft.cms.page.file.vo.FileBean;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.context.SftpConfigSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.keyword.handler.IKeywordHandler;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.cache.ExpConfigInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.model.EsearchCatalog;
import com.ztesoft.net.model.EsearchExpInstSolution;
import com.ztesoft.net.model.EsearchSpecvaluesRules;
import com.ztesoft.net.model.EsearchSpecvaluesRulesQuery;
import com.ztesoft.net.param.inner.EsearchCatalogInner;
import com.ztesoft.net.param.inner.EsearchCatalogSolutionInner;
import com.ztesoft.net.param.inner.EsearchExpInstSolutionInner;
import com.ztesoft.net.param.inner.EsearchSpecvaluesInner;
import com.ztesoft.net.param.inner.ExtractKeyInner;
import com.ztesoft.net.param.inner.KeyRuleInner;
import com.ztesoft.net.param.inner.KeyWordInner;
import com.ztesoft.net.param.inner.SpecvalueInner;
import com.ztesoft.net.param.inner.SpecvaluesQueryInner;
import com.ztesoft.net.param.outer.EsearchCatalogOuter;
import com.ztesoft.net.param.outer.EsearchExpInstSolutionOuter;
import com.ztesoft.net.param.outer.ExtractKeyOuter;
import com.ztesoft.net.param.outer.KeyRuleOuter;
import com.ztesoft.net.param.outer.KeyWordOuter;
import com.ztesoft.net.param.outer.SpecvaluesQueryOuter;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IExpConfigManager;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
/**
 * 异常配置管理类
 * @作者 shenqiyu
 * @创建日期 2015-11-23 
 */

public class ExpConfigManager extends BaseSupport implements IExpConfigManager{
	
	protected final Logger logger = Logger.getLogger(getClass());
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	private ExpConfigInfoCacheProxy proxy = new ExpConfigInfoCacheProxy() ;
	
	@Override
	public EsearchCatalogOuter addEsearchCatalog(EsearchCatalogInner eci) {
		//入参转化为实体
		EsearchCatalog esearchCatalog = new EsearchCatalog();
		esearchCatalog.setCatalog_code(eci.getCatalog_code());
		esearchCatalog.setCatalog_name(eci.getCatalog_name());
		esearchCatalog.setCatalog_desc(eci.getCatalog_desc());
		esearchCatalog.setSub_catalog_id(eci.getSub_catalog_id());
		esearchCatalog.setSource_from(eci.getSource_from()); 
		esearchCatalog.setSolution_id(eci.getSolution_id());
		esearchCatalog.setStaff_no(eci.getStaff_no());
		esearchCatalog.setSolution_id(EcsOrderConsts.EXP_DEFAULT_SOLUTION);//设置默认解决方案
		
		String catalogId = SequenceTools.getdefualt22PrimaryKey();
		esearchCatalog.setCatalog_id(catalogId);
		this.baseDaoSupport.insert("es_esearch_catalog", esearchCatalog);
		
		//出参返回
		EsearchCatalogOuter eco = new EsearchCatalogOuter();
		eco.setCatalog_id(catalogId);
		return eco;
		
	}

	@Override
	public void updateEsearchCatalog(EsearchCatalogInner eci) {
		//需要更新的字段
		Map<String,String> fields = new HashMap<String, String>();
		if(null != eci.getCatalog_desc()){
			fields.put("catalog_name", eci.getCatalog_name());
		}
		if(null != eci.getCatalog_desc()){
			fields.put("catalog_desc", eci.getCatalog_desc());
		}
		if(null != eci.getSub_catalog_id()){
			fields.put("sub_catalog_id", eci.getSub_catalog_id());
		}
		if(null != eci.getSolution_id()){
			fields.put("solution_id", eci.getSolution_id());
		}
		if(null != eci.getSolution_id()){
			fields.put("solution_id", eci.getSolution_id());
		}
		if(null != eci.getDisabled()){
			fields.put("disabled", eci.getDisabled());
		}
		//条件
		Map<String, String> where = new HashMap<String, String>();
		where.put("catalog_id", eci.getCatalog_id());
		
		this.baseDaoSupport.update("es_esearch_catalog", fields, where);
		
	}

	@Override
	public void deleteEsearchCatalog(EsearchCatalogInner eci) {
		String sql = SF.orderExpSql("DeleteEsearchCatalog");
		this.baseDaoSupport.execute(sql, eci.getCatalog_id());
	}

	@Override
	public List<EsearchCatalog> queryEsearchCatalog(EsearchCatalogInner eci) {
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalog"));
		Map where = new HashMap();		
		if(null != eci.getCatalog_id()){
			sql.append(" AND catalog_id=:catalog_id");
			where.put("catalog_id", eci.getCatalog_id());
		}
		if(null != eci.getCatalog_name()){
			sql.append(" AND catalog_name=:catalog_name");
			where.put("catalog_name", eci.getCatalog_name());
		}
		if(null != eci.getSub_catalog_id()){
			sql.append(" AND sub_catalog_id=:sub_catalog_id");
			where.put("sub_catalog_id", eci.getSub_catalog_id());
		}
		
		List<EsearchCatalog> list = this.baseDaoSupport.queryForListByMap(sql.toString(), EsearchCatalog.class, where);
		return list;
	}
	
	@Override
	public List<Map<String, String>> queryEsearchCatalogTree(String parent_catalog_id){
		if(StringUtils.isEmpty(parent_catalog_id)) throw new IllegalArgumentException("parent_catalog_id argument is null");
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalog"));
		sql.append(" AND sub_catalog_id= ? ");
		
		List catalogList = this.baseDaoSupport.queryForList(sql.toString(), parent_catalog_id);
		return catalogList;
	}
	
	@Override
	public EsearchCatalog getEsearchCatalog(String catalog_id){
		if(StringUtils.isEmpty(catalog_id)) throw new IllegalArgumentException("catalog_id argument is null");
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalogById"));
		sql.append(" AND eec.catalog_id= ? ");
		List<EsearchCatalog> catalogList = this.baseDaoSupport.queryForList(sql.toString(), EsearchCatalog.class, catalog_id);
		EsearchCatalog esearchCatalog = null;
		if(catalogList!=null && catalogList.size()>0)
			esearchCatalog = catalogList.get(0);
		return esearchCatalog;
	}
	
	@Override
	public Page queryEsearchCatalogPage(EsearchCatalogInner eci, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalog"));
		List<Object> params = new ArrayList<Object>();
		if(eci != null) {
			if(null != eci.getCatalog_id()){
				sql.append(" AND catalog_id=?");
				params.add(eci.getCatalog_id());
			}
			if(null != eci.getCatalog_name()){
				sql.append(" AND catalog_name like '%"+eci.getCatalog_name()+"%'");
			}
			if(null != eci.getSub_catalog_id()){
				sql.append(" AND sub_catalog_id=?");
				params.add(eci.getSub_catalog_id());
			}
		}
		return this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, EsearchCatalog.class, params.toArray());
	}
	
	@Override
	public List<EsearchCatalog> getCatalogSelectOption(){
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalog"));
		List<EsearchCatalog> catalogList = this.baseDaoSupport.queryForList(sql.toString(), EsearchCatalog.class);
		List<EsearchCatalog> topCatalogList  = new ArrayList<EsearchCatalog>();
		for(EsearchCatalog catalog :catalogList){
			if("0".equals(catalog.getSub_catalog_id())){
				List<EsearchCatalog> children = this.getChildren(catalogList, catalog.getCatalog_id());
				catalog.setChildrenCatalog(children);
				topCatalogList.add(catalog);
			}
		}
		return topCatalogList;
	}
	
	/**
	 * 在一个集合中查找子
	 * @param menuList 所有菜单集合
	 * @param parentid 父id
	 * @return 找到的子集合
	 */
	private List<EsearchCatalog> getChildren(List<EsearchCatalog> catalogList ,String parentid){
		List<EsearchCatalog> children =new ArrayList<EsearchCatalog>();
		for(EsearchCatalog catalog :catalogList){
			if(catalog.getSub_catalog_id()!=null && catalog.getSub_catalog_id().equals(parentid)){
				catalog.setChildrenCatalog(this.getChildren(catalogList, catalog.getCatalog_id()));
				children.add(catalog);
			}
		}
		return children;
	}

	@Override
	public EsearchExpInstSolutionOuter queryEsearchExpInstSolution(EsearchExpInstSolutionInner inner) {
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchExpInstSolution"));
		int pageIndex = inner.getPageIndex();
		int pageSize = inner.getPageSize();
		
		Map<String,String> where = new HashMap<String, String>();
		if(!StringUtil.isEmpty(inner.getSolution_id())){
			sql.append(" AND solution_id=:solution_id");
			where.put("solution_id", inner.getSolution_id());
		}
		if(!StringUtil.isEmpty(inner.getSolution_desc())){
			sql.append(" AND solution_desc like :solution_desc");
			where.put("solution_desc", "%" + inner.getSolution_desc() +"%");
		}
		if(!StringUtil.isEmpty(inner.getSolution_name())){
			sql.append(" AND solution_name like :solution_name");
			where.put("solution_name", "%" + inner.getSolution_name() +"%");
		}
		if(!StringUtil.isEmpty(inner.getSolution_type())){
			sql.append(" AND solution_type =:solution_type");
			where.put("solution_type", inner.getSolution_type());
		}
		Page page = this.baseDaoSupport.queryForPageByMap(sql.toString(), pageIndex, pageSize, where);
		EsearchExpInstSolutionOuter result = new EsearchExpInstSolutionOuter();
		result.setPage(page);
		return result;
		
//		, 
	}
	
	@Override
	public EsearchExpInstSolutionOuter queryEsearchExpInstSolutionById(
			EsearchExpInstSolutionInner inner) {
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchExpInstSolution"));
		
		Map<String,String> where = new HashMap<String, String>();
		if(!StringUtil.isEmpty(inner.getSolution_id())){
			sql.append(" AND solution_id=:solution_id");
			where.put("solution_id", inner.getSolution_id());
		}
		List resultList = this.baseDaoSupport.queryForListByMap(sql.toString(),EsearchExpInstSolution.class , where);
		EsearchExpInstSolutionOuter result = new EsearchExpInstSolutionOuter();
		result.setList(resultList);
		return result;
	}
	
	@Override
	public Page queryEsearchExpInstSolutionPage(EsearchExpInstSolutionInner inner, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchExpInstSolution"));
		Map<String,String> where = new HashMap<String, String>();
		if(!StringUtil.isEmpty(inner.getSolution_type())){
			sql.append(" AND solution_type=:solution_type");
			where.put("solution_type", inner.getSolution_type());
		}
		if(!StringUtil.isEmpty(inner.getSolution_value())){
			sql.append(" AND solution_value like :solution_value ");
			where.put("solution_value", "%" + inner.getSolution_value() + "%");
		}
		if(!StringUtil.isEmpty(inner.getSolution_name())){
			sql.append(" AND solution_name like :solution_name ");
			where.put("solution_name", "%" + inner.getSolution_name() + "%");
		}
		Page page = this.baseDaoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, EsearchExpInstSolution.class, where);
		return page;
	}
	
	@Override
	public ExtractKeyOuter extractKeyword(ExtractKeyInner inner){
		ExtractKeyOuter outer = extractKeyword_a(inner);
		String key_word = outer.getKeyword();
		//统一截取字符：数据库关键字的长度为varchar(500)
		if(key_word.length()>500){
			key_word = key_word.substring(0, 300);
			outer.setKeyword(key_word);
		}
		return outer;
	}
	public ExtractKeyOuter extractKeyword_a(ExtractKeyInner inner){
		ExtractKeyOuter outer = null;
		String param = inner.getParam();
		//根据搜索id查询抽取规则，按顺序号排列
		List<EsearchSpecvaluesRules> rulesList = proxy.getSpecvaluesRules(inner.getSearch_id());
		//循环抽取规则，若匹配到关键字，则立即返回关键字
		for (int i = 0; i < rulesList.size(); i++){
			EsearchSpecvaluesRules rule = rulesList.get(i);
			try{
				//若匹配字段不为空：按照匹配字段抽取
				String match_word = rule.getMatch_word();
				if(!StringUtils.isBlank(match_word)){
					outer = getKeyWordByMatch(rule,match_word,param);
					if(outer != null){
						return outer;
					}
				}
				//若起始字符不为空：按照起始字符抽取关键字
				String begin_word = rule.getBegin_word();
				if(begin_word!=null && !begin_word.isEmpty()){
					outer = getFailWordByBeginWord(rule,begin_word,param);
					if(outer != null){
						return outer;
					}
				}
				//属性处理器:
				if(rule.getHander()!=null){
					outer = getFailWordByHandler(rule,param);
					if(outer != null){
						return outer;
					}
				}
				//若起始下标不为空：按照起始下标抽取
				if(rule.getBegin_index()!=null){
					outer = getFailWordByBeginIndex(rule,param);
					if(outer != null){
						return outer;
					}
				}
			}catch(Exception e){
				logger.info("抽取规则id为："+rule.getKey_rule_id()+"对象id为："+inner.getObj_id()+"--抽取关键字出错");
				logger.info(e.getMessage());
			}
		}
		List<EsearchSpecvaluesRules> pubRulesList = proxy.getSpecvaluesRules(EcsOrderConsts.PUBLIC_SEARCH_ID);
		for(int i=0;i<pubRulesList.size();i++){
			EsearchSpecvaluesRules rule = pubRulesList.get(i);
			outer = getKeyWordByMatch(rule,param);
			if(outer != null){
				return outer;
			}
		}

		//若上述操作无法抽取关键字，默认返回"未匹配到关键字(缺省)"
		outer = new ExtractKeyOuter();
		outer.setKeyword("未匹配到关键字(缺省)");
		outer.setKey_word_type("fail");
		outer.setCond_filter("未匹配到关键字(缺省)");
		return outer;
	}
	
	/**
	 * 按照匹配字符抽取关键字
	 * @param rule
	 * @param regex
	 * @param param
	 * @return
	 */
	public ExtractKeyOuter getKeyWordByMatch(EsearchSpecvaluesRules rule,String regex,String param){
		ExtractKeyOuter outer = new ExtractKeyOuter();
		Matcher m = Pattern.compile(regex).matcher(param);
		if(m.find()){
			String keyword = m.group(0);
			outer.setKeyword(keyword);
			outer.setKey_word_type(rule.getKey_word_type());
			outer.setKey_rule_id(rule.getKey_rule_id());
			outer.setCond_filter(keyword);
			return outer;
		}
		return null;
	}
	
	/**
	 * 通过属性处理器抽取关键字
	 * @param rule
	 * @return
	 */
	public ExtractKeyOuter getFailWordByHandler(EsearchSpecvaluesRules rule,String param){
		ExtractKeyOuter outer = new ExtractKeyOuter();
		IKeywordHandler handler = SpringContextHolder.getBean(rule.getHander());
		KeyWordInner inner = new KeyWordInner();
		inner.setParam(param);
		KeyWordOuter keyWordOuter = handler.getKeyWord(inner);
		if(keyWordOuter!=null){
			String keyword = keyWordOuter.getKey_word();
			outer.setKeyword(keyword);
			outer.setKey_word_type(rule.getKey_word_type());
			outer.setKey_rule_id(rule.getKey_rule_id());
			return outer;
		}
		return null;
	}
	
	/**
	 * 
	 * 按照起始字符抽取关键字（失败关键字）
	 * @param rule
	 * @param begin_word
	 * @param param
	 * @return
	 */
	public ExtractKeyOuter getFailWordByBeginWord(EsearchSpecvaluesRules rule,String begin_word,String param){
		ExtractKeyOuter outer = new ExtractKeyOuter();
		String keyword = "";
		Matcher m = Pattern.compile(begin_word).matcher(param);
		//判断是否有 起始字符：有起始字符的按照起始字符抽取
		if(m.find()){
			param = param.substring(m.start(0));
			String end_word = rule.getEnd_word();
			String[] endWords = new String[]{};
			if(!StringUtils.isEmpty(end_word)){
				endWords = end_word.split("#");
			}
			//判断是否有多个截止字符
			for(int j=0;j<endWords.length;j++){
				//截取从起始位置开始的报文
				Matcher m1 = Pattern.compile(endWords[j]).matcher(param);
				if(m1.find()){
					begin_word = begin_word.replace("\\\\", "\\");
					keyword = param.substring(begin_word.length(),m1.start(0));
					outer.setCond_filter(keyword);
					//判断是否有正则表达式需要特殊
					if(!StringUtils.isEmpty(rule.getExpress())){
						Map<String,String> map = doRegexKeyWord(rule.getExpress(),rule.getCut_word(),keyword);
						if(map.get("cond_filter")!=null){
							outer.setCond_filter(map.get("cond_filter"));
							outer.setSum(map.get("sum"));
						}
						keyword = map.get("key_word");
					}
					outer.setKeyword(keyword);
					outer.setKey_word_type(rule.getKey_word_type());
					outer.setKey_rule_id(rule.getKey_rule_id());
					return outer;
				}
			}
		}
		return null;
	}
	
	/**
	 * 对抽取的关键字做正则处理
	 * @param regex
	 * @param key_word
	 * @return
	 */
	public Map<String,String> doRegexKeyWord(String regex,String cut_word,String key_word){
		Map<String,String> map = new HashMap<String,String>();
		Matcher m = Pattern.compile(regex).matcher(key_word);
		String cond_filter = ""+key_word;
		int k=0;
		while (m.find()){
			k++;
			String matchStr = m.group(0);
			cond_filter = cond_filter.replace(matchStr, " ");//esearch支持模糊过滤
			key_word = key_word.replace(matchStr, "");
		}
		map.put("sum", k+"");
		map.put("cond_filter",cond_filter);
		map.put("key_word",key_word);
		return map;
	}
	
	/**
	 * 按照起始下标抽取关键字（失败关键字）
	 * @param begin_index
	 * @param keyword
	 * @param key_rule_id
	 * @param param
	 * @return
	 */
	public ExtractKeyOuter getFailWordByBeginIndex(EsearchSpecvaluesRules rule,String param){
		ExtractKeyOuter outer = new ExtractKeyOuter();
		String keyword = "";
		Integer begin_index = rule.getBegin_index();
		Integer end_index = rule.getEnd_index();
		String key_rule_id = rule.getKey_rule_id();
		String cut_word = rule.getCut_word();
		//先判断是否含有该字典
		if(!StringUtils.isBlank(cut_word)){
			ExtractKeyOuter outer1 = getKeyWordByMatch(rule,cut_word,param);
			if(outer1 == null){
				return null;
			}
		}
		
		//如果报文长度小于起始下标，则截取起始下标起的字符串
		if(begin_index<param.length()){
			if(null != end_index && end_index < param.length()){//结束位置		
				keyword = param.substring(begin_index,end_index);
			}else{
				keyword = param.substring(begin_index,param.length());
			}
			outer.setKeyword(keyword);
			outer.setCond_filter(keyword);
			outer.setKey_word_type("fail");
			outer.setKey_rule_id(key_rule_id);
			return outer;
		}
		return null;
	}
	
	/**
	 * 抽取超时类关键字
	 * @param rule
	 * @param param
	 * @return
	 */
	public ExtractKeyOuter getKeyWordByMatch(EsearchSpecvaluesRules rule,String param){
		ExtractKeyOuter outer = new ExtractKeyOuter();
		String regex = rule.getMatch_word();
		Integer length = rule.getLength();
		//超过定义的长度，无需抽取
		if(param.length()>length){
			return null;
		}
		if(StringUtils.isBlank(regex)){
			return null;
		}
		String[] words = regex.split("#");
		Matcher m = null;
		for(int i=0;i<words.length;i++){
			if(StringUtils.isEmpty(words[i])){
				continue ;
			}
			m = Pattern.compile(words[i]).matcher(param);
			if(m.find()){
				String keyword = m.group(0);
				outer.setKeyword(keyword);
				outer.setKey_word_type(rule.getKey_word_type());
				outer.setKey_rule_id(rule.getKey_rule_id());
				outer.setCond_filter(keyword);
				return outer;
			}
		}
		return null;
	}
	
	@Override
	public ExtractKeyOuter extractBusKeyword(ExtractKeyInner inner){
		ExtractKeyOuter outer = null;
		String param = inner.getParam();
		//根据搜索id查询抽取规则，按顺序号排列
		String sql = SF.orderExpSql("SpecvaluesRulesBySearchId");
		List<EsearchSpecvaluesRules> rulesList = this.baseDaoSupport.queryForList(sql, EsearchSpecvaluesRules.class, inner.getSearch_id());
		//循环抽取规则，若匹配到关键字，则立即返回关键字
		for (int i = 0; i < rulesList.size(); i++){
			EsearchSpecvaluesRules rule = rulesList.get(i);
			String error_stack_msg = inner.getError_stack_msg();
			//先判断堆栈信息需要抽取的关键字
			if(!StringUtils.isEmpty(error_stack_msg)){
				if(!StringUtils.isBlank(rule.getMatch_word())){
					outer = getKeyWordByMatch(rule,rule.getMatch_word(),error_stack_msg);
					if(outer != null){
						return outer;
					}
				}	
			}
		}
		//循环抽取规则，若匹配到关键字，则立即返回关键字
		for (int i = 0; i < rulesList.size(); i++){
			EsearchSpecvaluesRules rule = rulesList.get(i);
			//属性处理器:
			if(rule.getHander()!=null){
				outer = getFailWordByHandler(rule,param);
				if(outer != null){
					return outer;
				}
			}
			outer = new ExtractKeyOuter();
			//正则过滤
			if(!StringUtils.isEmpty(rule.getExpress())){
				Map<String,String> map = doRegexKeyWord(rule.getExpress(),rule.getCut_word(),param);
				outer.setKey_rule_id(rule.getKey_rule_id());
				outer.setKey_word_type(rule.getKey_word_type());
				outer.setKeyword(map.get("key_word"));
				return outer;
			}else{
				outer.setKey_rule_id(rule.getKey_rule_id());
				outer.setKey_word_type(rule.getKey_word_type());
				outer.setKeyword(param);
				return outer;
			}
		}
		//若上述操作无法抽取关键字，默认返回"未匹配到关键字(缺省)"
		outer.setKeyword("未匹配到关键字(缺省)");
		outer.setKey_word_type("fail");
		outer.setCond_filter("未匹配到关键字(缺省)");
		return outer;
	}
	
	/**
	 * 中文字正则匹配检索
	 * @param param
	 * @return
	 */
	@Override
	public String getWordByChineserRegular(String param){
		String keyword = "";
		String regex = "[\u4e00-\u9fa5]+";
		Matcher m = Pattern.compile(regex).matcher(param);
		while (m.find()){
			String r = m.group(0);
			keyword += r + " ";
		}
		return keyword;
	}

	@Override
	public EsearchCatalogOuter queryEsearchCatalogBySpecv(
			EsearchCatalogInner inner) {
		String sql = SF.orderExpSql("QueryEsearchCatalogBySpecv");
		String keyId = inner.getKey_id();
		List<EsearchCatalog> list = this.daoSupport.queryForList(sql, EsearchCatalog.class, keyId);
		
		//返回参数
		EsearchCatalogOuter result = new EsearchCatalogOuter();
		result.setList(list);
		return result;
	}

	@Override
	public EsearchExpInstSolutionOuter queryEesByCatalog(
			EsearchExpInstSolutionInner inner) {
		String sql = SF.orderExpSql("QueryEesByCatalog");
		List list = this.daoSupport.queryForList(sql, EsearchExpInstSolution.class, inner.getCatalog_id());
		
		//返回参数
		EsearchExpInstSolutionOuter result = new EsearchExpInstSolutionOuter();
		result.setList(list);
		return result;
	}

	@Override
	public void addEsearchCatalogSolution(EsearchCatalogSolutionInner inner) {
		
		String solution_id = SequenceTools.getdefualt22PrimaryKey();
		//入参转化为实体
		EsearchExpInstSolution esearchExpInstSolution = new EsearchExpInstSolution();
		esearchExpInstSolution.setSolution_desc(inner.getSolution_desc());
		esearchExpInstSolution.setSolution_type(inner.getSolution_type());
		esearchExpInstSolution.setSolution_value(inner.getSolution_value());
		esearchExpInstSolution.setIs_delete_rule_log(inner.getIs_delete_rule_log());
		esearchExpInstSolution.setSolution_id(solution_id);
		esearchExpInstSolution.setHander_fact(inner.getHander_fact());
		esearchExpInstSolution.setSolution_name(inner.getSolution_name());
		esearchExpInstSolution.setIs_batch_process(inner.getIs_batch_process());
		this.baseDaoSupport.insert("es_esearch_expinst_solution", esearchExpInstSolution);
//		String solutionId = this.baseDaoSupport.getLastId("es_esearch_expinst_solution");
		//出参返回
//		EsearchCatalogSolutionOuter outer = new EsearchCatalogSolutionOuter();
//		outer.setSolution_id(solutionId);
		
//		return outer;
	}

	@Override
	public void updateEsearchCatalogSolution(
			EsearchCatalogSolutionInner inner) {
		//需要更新的字段
		Map<String,String> fields = new HashMap<String, String>();
		if(null != inner.getSolution_desc()){
			fields.put("solution_desc", inner.getSolution_desc());
		}
		if(null != inner.getSolution_type()){
			fields.put("solution_type", inner.getSolution_type());
		}
		if(null != inner.getSolution_value()){
			fields.put("solution_value", inner.getSolution_value());
		}
		if(null != inner.getIs_delete_rule_log()){
			fields.put("is_delete_rule_log", inner.getIs_delete_rule_log());
		}
		if(null != inner.getHander_fact()){
			fields.put("hander_fact", inner.getHander_fact());
		}
		if(null != inner.getSolution_name()){
			fields.put("solution_name", inner.getSolution_name());
		}
		if(null != inner.getIs_batch_process()){
			fields.put("is_batch_process", inner.getIs_batch_process());
		}
		//条件
		Map<String, String> where = new HashMap<String, String>();
		where.put("solution_id", inner.getSolution_id());
		
		this.baseDaoSupport.update("es_esearch_expinst_solution", fields, where);
	}

	@Override
	public void deleteEsearchCatalogSolution(
			EsearchCatalogSolutionInner inner) {
		String sql = SF.orderExpSql("DeleteEsearchCatalogSolution");
		this.baseDaoSupport.execute(sql, inner.getSolution_id());
	}

	@Override
	public KeyRuleOuter queryKeyRulePage(KeyRuleInner inner) {
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryKeyRulePage"));
		int pageIndex = inner.getPageIndex();
		int pageSize = inner.getPageSize();
		
		Map<String, String> where = new HashMap<String, String>();
		
		if(!StringUtil.isEmpty(inner.getSearch_name())){
			sql.append(" AND ees.search_name like :search_name");
			where.put("search_name", "%" + inner.getSearch_name() + "%");
		}
		if(!StringUtil.isEmpty(inner.getSearch_id())){
			sql.append(" AND ees.search_id = :search_id");
			where.put("search_id", inner.getSearch_id());
		}
		if(!StringUtil.isEmpty(inner.getSearch_code())){
			sql.append(" AND ees.search_code = :search_code");
			where.put("search_code", inner.getSearch_code());
		}
		//分组、排序
		sql.append(" ORDER BY eesr.seq");
		
		Page page = this.baseDaoSupport.queryForPageByMap(sql.toString(), pageIndex, pageSize, where);
		//返回参数
		KeyRuleOuter result = new KeyRuleOuter();
		result.setPage(page);
	
		return result;
	}

	@Override
	public KeyRuleOuter deleteKeyRuleByKeyId(KeyRuleInner inner) {
		String sql = SF.orderExpSql("DeleteKeyRuleById");
		this.baseDaoSupport.execute(sql, inner.getKey_id());
		//返回参数
		KeyRuleOuter result = new KeyRuleOuter();
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public KeyRuleOuter addKeyRule(KeyRuleInner inner) {
		//返回参数
		KeyRuleOuter result = null;
		try {
			//入参转为实体
			Map<String,String> esr = new HashMap<String, String>();
			//esr.put("key_rule_id", inner.getKey_id());
			esr.put("search_id", inner.getSearch_id());
			esr.put("express", inner.getExpress());
			esr.put("hander", inner.getHander());
			esr.put("seq", inner.getSeq());
			esr.put("begin_index", inner.getBegin_index());
			esr.put("end_index", inner.getEnd_index());
			esr.put("begin_word", inner.getBegin_word());
			esr.put("end_word", inner.getEnd_word());
			esr.put("key_word_type", inner.getKey_word_type());
			esr.put("match_word", inner.getMatch_word());
			esr.put("cut_word", inner.getCut_word());
			esr.put("source_from", ManagerUtils.getSourceFrom());
			esr.put("staff_no", ManagerUtils.getUserId());
			//关键字规则id
			String keyRuleId = SequenceTools.getdefualt22PrimaryKey();
			esr.put("key_rule_id", keyRuleId);
			
			this.daoSupport.insert("ES_ESEARCH_SPECVALUES_RULES", esr);
			/*//调用未匹配关键字异常更新API
			UnCheckSpecvalueInner ucsi = new UnCheckSpecvalueInner();
			ucsi.setSearch_id(inner.getSearch_id());
			this.esearchSpecvaluesManager.unCheckSpecvalueUpdate(ucsi);*/
			result = new KeyRuleOuter();
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addError("-1","添加关键字抽取规则失败！ "+e.getMessage());
		}
		return result;
	}

	@Override
	public KeyRuleOuter findKeyRuleByKeyId(KeyRuleInner inner) {
		String sql = SF.orderExpSql("FindKeyRuleByKeyId");
		EsearchSpecvaluesRulesQuery esrq = (EsearchSpecvaluesRulesQuery) this.baseDaoSupport.queryForObject(sql, EsearchSpecvaluesRulesQuery.class, inner.getKey_id());
		
		//返回参数
		KeyRuleOuter result = new KeyRuleOuter();
		result.setEsrq(esrq);
		return result;
	}

	@Override
	public KeyRuleOuter updateKeyRuleByKeyId(KeyRuleInner inner) {
		Map<String, String> where = new HashMap<String, String>();
		where.put("key_rule_id", inner.getKey_id());
		Map<String, String> fields = new HashMap<String, String>();
		if(!StringUtil.isEmpty(inner.getSearch_id())){	
			fields.put("search_id", inner.getSearch_id());
		}
		if(null != inner.getExpress()){
			fields.put("express", inner.getExpress());
		}
		if(null != inner.getHander()){			
			fields.put("hander", inner.getHander());
		}
		if(null != inner.getSeq()){		
			fields.put("seq", inner.getSeq());
		}
		if(null != inner.getBegin_index()){		
			fields.put("begin_index", inner.getBegin_index());
		}
		if(null != inner.getBegin_index()){		
			fields.put("end_index", inner.getEnd_index());
		}
		if(null != inner.getBegin_index()){		
			fields.put("begin_word", inner.getBegin_word());
		}
		if(null != inner.getBegin_index()){		
			fields.put("end_word", inner.getEnd_word());
		}
		if(null != inner.getBegin_index()){		
			fields.put("key_word_type", inner.getKey_word_type());
		}
		if(null != inner.getBegin_index()){		
			fields.put("match_word", inner.getMatch_word());
		}
		if(null != inner.getBegin_index()){		
			fields.put("cut_word", inner.getCut_word());
		}
		fields.put("upd_staff_no", ManagerUtils.getUserId());
		
		this.baseDaoSupport.update("ES_ESEARCH_SPECVALUES_RULES", fields, where);
		//返回参数
		KeyRuleOuter result = new KeyRuleOuter();
		return result;
	}

	@Override
	public void addEsearchCatalogRelc(SpecvalueInner inner) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("key_id", inner.getKey_id());
		fields.put("catalog_id", inner.getCatalog_id());
		this.baseDaoSupport.insert("ES_ESEARCH_SPECVALUES_RELC", fields, null);
	}

	@Override
	public void updateEsearchCatalogRelc(SpecvalueInner inner) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("catalog_id", inner.getCatalog_id());
		Map<String, String> where = new HashMap<String, String>();
		where.put("catalog_id", inner.getOld_catalog_id());
		where.put("key_id", inner.getKey_id());
		this.baseDaoSupport.update("ES_ESEARCH_SPECVALUES_RELC", fields, where);	
	}
	
	public IEsearchSpecvaluesManager getEsearchSpecvaluesManager() {
		return esearchSpecvaluesManager;
	}

	public void setEsearchSpecvaluesManager(
			IEsearchSpecvaluesManager esearchSpecvaluesManager) {
		this.esearchSpecvaluesManager = esearchSpecvaluesManager;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String[] uploadFile(File file, Map<String, String> params)throws Exception {
		
		String fileName = Const.getStrValue(params, "fileName");
//		//上传文件加时间戳
		StringBuffer fileNames = new StringBuffer(fileName);
		//fileName = fileNames.insert(fileName.lastIndexOf("."), String.valueOf(System.currentTimeMillis())).toString();
		
		String[] returnArray =new String[2];
		//上传
		String loaclpath  = ServletActionContext.getServletContext().getRealPath("/fakepath/");
		String path  = SftpConfigSetting.SFTP_PATH;
		if(file != null){
			File savefile = new File(new File(loaclpath),fileName);
		    if(!savefile.getParentFile().exists()){
		    	savefile.getParentFile().mkdirs(); 
		    } 
		    FileUtils.copyFile(file, savefile);
			//修改为上传到公共应用服务器的目录  by heqilong
			SftpUpLoadImpl sftp = new SftpUpLoadImpl();
			HashMap config = new HashMap();
			config.put("server", SftpConfigSetting.SFTP_SERVER);
			config.put("username", SftpConfigSetting.SFTP_USERNAME);
			config.put("password", SftpConfigSetting.SFTP_PASSWORD);
			Map fileMap = new HashMap();
			fileMap.put("FILE_ITEM", savefile);
			FileBean fileBean =  new FileBean();
			fileBean.setFolder("");
			fileMap.put("FILE_BEAN", fileBean);
			
			try{
				sftp.uploadNew(fileMap, config, path);
				returnArray[0] = "success";
			    returnArray[1] = path+fileName;
			}catch(Exception e){
				returnArray[0] = "flase";
				returnArray[1] = "上传文件失败！！";
			}
			savefile.delete();
		}else{
			returnArray[0] = "flase";
			returnArray[1] = "上传文件失败！！";
		}
		
		return returnArray;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void extractAllKeyword() throws Exception {
		String sql = SF.orderExpSql("QuerySpec");
		List<Map> sepcList = this.baseDaoSupport.queryForList(sql);
		if(sepcList==null||sepcList.size()==0)CommonTools.addError(new ZteError("-1", "规格定义表数据为空!"));
		for(Map map : sepcList){
			String searchCode = (String) map.get("SEARCH_CODE");
			String searchId = (String) map.get("SEARCH_ID");
			String searchFiled = (String) map.get("SEARCH_FIELD");
			int k = 0;
			extract(k,searchId,searchCode,searchFiled,new HashMap<String,String>(),new ArrayList<String>());
		}
		logger.info("抽取完成！！！！");
	}
	
	private void extract(int k,String searchId,String searchCode,String searchField,Map<String,String> qsParams,List<String> keywordList) throws Exception{
		//拼接关键字条件，查询报文,如果查询为空则返回
		Map<String,String> boolParams = new HashMap<String,String>();
		//boolParams.put("operation_code",searchCode);
		qsParams.put(searchCode, "+operation_code:");
		
		logger.info("过滤条件："+qsParams+" 接口编码:"+searchCode);
		
		EsearchSearchReq req = new EsearchSearchReq();
		req.setQsParams(qsParams);
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		EsearchSearchResp resp = client.execute(req, EsearchSearchResp.class);
		if(StringUtils.equals("-1", resp.getError_code())){
			logger.info("esearch查询出错：接口编码："+searchCode);
		}
		
		List<ESearchData> esList = resp.getEsList();
		if(esList==null||esList.size()==0)return;
		ESearchData es = esList.get(0);
		
		String param = "";
		if("0".equals(searchField)){
			param = es.getOut_param();
		}else{
			param = es.getIn_param();
		}
		
		//抽取关键字
		ExtractKeyInner extractKeyinner = new ExtractKeyInner();
		extractKeyinner.setSearch_id(searchId);
		extractKeyinner.setParam(param);
		extractKeyinner.setObj_id(es.getLog_id());
		ExtractKeyOuter extractKeyouter = extractKeyword(extractKeyinner);
		String key_word = extractKeyouter.getKeyword();
		String key_rule_id = extractKeyouter.getKey_rule_id();
		String key_word_type = extractKeyouter.getKey_word_type();
		String qsword = extractKeyouter.getCond_filter();
		qsword = qsword.replace("\\","\\\\");
		qsword = qsword.replace("\"", "\\\"");
		qsword = "\""+qsword+"\""+"~"+extractKeyouter.getSum();
		
		IEsearchSpecvaluesManager specvaluesManager = SpringContextHolder.getBean("esearchSpecvaluesManager");
		if(!keywordList.contains(key_word)||"未匹配到关键字(缺省)".equals(key_word)){
			//调用[规格关键字查询]API，判断记录是否存在
			SpecvaluesQueryInner specvaluesInner = new SpecvaluesQueryInner();
			specvaluesInner.setSearch_code(searchCode);
			specvaluesInner.setKey_word(key_word);
		    SpecvaluesQueryOuter specvaluesOuter = specvaluesManager.querySpecvalues(specvaluesInner);
			EsearchSpecvalues specvalues = specvaluesOuter.getSpecvalues();
			if(specvalues==null){
				//查询关键字id
				String key_id = this.daoSupport.getSequences("ES_ESEARCH_SPECVALUES_SEQ", "1", 18);
				//插入规格关键字
				EsearchSpecvaluesInner insertvaluesInner = new EsearchSpecvaluesInner();
				insertvaluesInner.setSearch_code(searchCode);
				insertvaluesInner.setSearch_id(searchId);
				insertvaluesInner.setKey_id(key_id);
				insertvaluesInner.setKey_word(key_word);
				insertvaluesInner.setKey_rule_id(key_rule_id);
				insertvaluesInner.setMatch_content_type(key_word_type);
				esearchSpecvaluesManager.insertSpecvalues(insertvaluesInner);
				String sql = "update es_esearch_specvalues set col3=? where match_content=? and search_id=?";
				this.baseDaoSupport.execute(sql, es.getLog_id(),key_word,searchId);
			}
			keywordList.add(key_word);
			if("未匹配到关键字(缺省)".equals(key_word)){
				//未匹配到关键字：说明接口没配好规则，直接退出
				return ;
			}
		}else{
			//防止死循环：esearch过滤不了数据
			if(k++>10){
				String sql = "update es_esearch_specvalues set col1='Y' where match_content=? ";
				this.baseDaoSupport.execute(sql, key_word);
				return ;
			}
		}
		if("0".equals(searchField)){
			qsParams.put(qsword, "-out_param:");
		}else{
			qsParams.put(qsword, "-in_param:");
		}
		extract(k,searchId,searchCode,searchField,qsParams,keywordList);
	}

	@Override
	public QueryExpCatalogResp queryEsearchCatalog(QueryExpCatalogReq req) {
		QueryExpCatalogResp resp = new QueryExpCatalogResp();
		try {
			EsearchCatalogInner inner = new EsearchCatalogInner();
			List<EsearchCatalog> list = this.queryEsearchCatalog(inner);
			resp.setError_code("0");
			resp.setError_msg("查询成功！");
			resp.setList(list);
		} catch (Exception e) {
			resp.setError_code("1");
			resp.setError_msg("查询失败！");
			e.printStackTrace();
		}
		
		return resp;
	}

	public ExpConfigInfoCacheProxy getProxy() {
		return proxy;
	}

	public void setProxy(ExpConfigInfoCacheProxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public List<Map<String, String>> queryEsearchCatalogTree(Map<String,String> params) {
		List<String> sqlParams = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer(SF.orderExpSql("QueryEsearchCatalog"));
		if(!StringUtils.isEmpty(params.get("parent_catalog_id"))){
			sql.append(" AND sub_catalog_id= ? ");
			sqlParams.add(params.get("parent_catalog_id"));
		}
		
		if(!StringUtils.isEmpty(params.get("catalog_code"))){
			sql.append(" AND catalog_code = ?");
			sqlParams.add(params.get("catalog_code"));
		}
		if(!StringUtils.isEmpty(params.get("catalog_name"))){
			sql.append(" AND catalog_name like ?");
			sqlParams.add("%" + params.get("catalog_name") + "%");
		}
		
		List catalogList = this.baseDaoSupport.queryForList(sql.toString(), sqlParams.toArray());
		return catalogList;
	}
	
}
