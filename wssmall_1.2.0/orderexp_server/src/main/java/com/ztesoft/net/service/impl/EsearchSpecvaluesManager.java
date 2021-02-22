package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.EsearchSpecvalues;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.req.EsearchGetReq;
import params.req.EsearchUpdateClassReq;
import params.req.EsearchUpdateReq;
import params.resp.EsearchGetResp;
import params.resp.EsearchUpdateClassResp;
import params.resp.EsearchUpdateResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.model.EsearchCatalog;
import com.ztesoft.net.model.EsearchExpInstQuery;
import com.ztesoft.net.model.EsearchSpecvaluesRelc;
import com.ztesoft.net.model.SpecvaluesCheckParams;
import com.ztesoft.net.param.inner.ChangeSpecvRuleUpdateExpInstInner;
import com.ztesoft.net.param.inner.ChangeSpecvalueUpdateInner;
import com.ztesoft.net.param.inner.EsearchCatalogInner;
import com.ztesoft.net.param.inner.EsearchSpecvaluesInner;
import com.ztesoft.net.param.inner.ExpInstInner;
import com.ztesoft.net.param.inner.ExpInstQueryInner;
import com.ztesoft.net.param.inner.ExtractKeyInner;
import com.ztesoft.net.param.inner.SpecvalueInner;
import com.ztesoft.net.param.inner.SpecvaluesCheckProcessedInner;
import com.ztesoft.net.param.inner.SpecvaluesClassifyInner;
import com.ztesoft.net.param.inner.SpecvaluesQueryInner;
import com.ztesoft.net.param.inner.UnCheckSpecvalueInner;
import com.ztesoft.net.param.outer.ChangeSpecvRuleUpdateExpInstOuter;
import com.ztesoft.net.param.outer.ChangeSpecvalueUpdateOuter;
import com.ztesoft.net.param.outer.ExpInstOuter;
import com.ztesoft.net.param.outer.ExpInstQueryOuter;
import com.ztesoft.net.param.outer.ExtractKeyOuter;
import com.ztesoft.net.param.outer.SpecvaluesCheckProcessedOuter;
import com.ztesoft.net.param.outer.SpecvaluesClassifyOuter;
import com.ztesoft.net.param.outer.SpecvaluesQueryOuter;
import com.ztesoft.net.param.outer.UncheckSpecvalueOuter;
import com.ztesoft.net.search.conf.EsearchValues;
import com.ztesoft.net.service.IEsearchSpecDefineManager;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IExpConfigManager;
import com.ztesoft.net.service.IOrderExpManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.utils.OrderExpUtils;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 关键字管理
 * @author qin.yingxiong
 */
public class EsearchSpecvaluesManager extends BaseSupport<EsearchSpecvalues> implements IEsearchSpecvaluesManager {
	protected final Logger logger = Logger.getLogger(getClass());
	private IExpConfigManager expConfigManager;
	private IEsearchSpecDefineManager esearchSpecDefineManager;
	private IOrderExpManager orderExpManager;
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SpecvaluesClassifyOuter specvaluesClassify(
			SpecvaluesClassifyInner inner) {
		SpecvaluesClassifyOuter outer = new SpecvaluesClassifyOuter();
		EsearchSpecvaluesRelc esvr = new EsearchSpecvaluesRelc();
		esvr.setCatalog_id(inner.getCatalog_id());
		esvr.setKey_id(inner.getKey_id());
		esvr.setCreate_time("sysdate");
		esvr.setStaff_no(inner.getStaff_no());
		//1、[关键字归类表]插入或更新数据。
		String sql = SF.orderExpSql("QueryEsearchSpecvaluesRelc");
		Map esvcResult = new HashMap();
		//因为底层用了spring的JdbcTemplate的queryForMap方法，此方法如果没有找到结果集就会抛出EmptyResultDataAccessException异常
		try {
			List<Map> list = this.baseDaoSupport.queryForList(sql, inner.getKey_id());
			if(list != null && list.size() > 0) {
				esvcResult = list.get(0);
			}
		} catch (ObjectNotFoundException e1) {
			esvcResult = null;
		}
		if(esvcResult != null && esvcResult.size() > 0) {//如果该关键字归类关系已存在，则删除后再新增
			if(inner.getCatalog_id().equals(esvcResult.get("catalog_id").toString())) {
				outer.setOuter_status(ConstsCore.ERROR_FAIL);
				outer.setOuter_msg("关键字ID"+inner.getKey_id()+"与归类ID"+inner.getCatalog_id()+"关系已存在");
				CommonTools.addError(ConstsCore.ERROR_FAIL,"关键字ID"+inner.getKey_id()+"与归类ID"+inner.getCatalog_id()+"关系已存在");
				return outer;
			}
			this.baseDaoSupport.execute(SF.orderExpSql("DeleteEsearchSpecvaluesRelc"), inner.getKey_id());
		}
		this.baseDaoSupport.insert("es_esearch_specvalues_relc",esvr);
		//2、根据搜索id、关键字id、记录状态（未处理）调用【异常实例查询】API。  需要确认是否需要搜索ID
		EsearchCatalogInner eclInner = new EsearchCatalogInner();
		eclInner.setCatalog_id(inner.getCatalog_id());
		List<EsearchCatalog> ecLList = expConfigManager.queryEsearchCatalog(eclInner);
		if(ecLList == null || ecLList.size() < 1) {
			outer.setOuter_status(ConstsCore.ERROR_FAIL);
			outer.setOuter_msg("归类ID"+inner.getCatalog_id()+"不存在");
			CommonTools.addError(ConstsCore.ERROR_FAIL,"归类ID"+inner.getCatalog_id()+"不存在");
			return outer;
		}
		
		ExpInstQueryInner eiqInner = new ExpInstQueryInner();
		eiqInner.setKey_id(inner.getKey_id());
		EsearchCatalog ecl = ecLList.get(0);
		eiqInner.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_0);
		ExpInstQueryOuter eiqOuter = orderExpManager.queryExpInstList(eiqInner);
		//3、循环结果集。
		if(eiqOuter != null 
				&& eiqOuter.getEsearchExpInstList() != null 
				&& eiqOuter.getEsearchExpInstList().size() > 0) {
			for(EsearchExpInstQuery eei : eiqOuter.getEsearchExpInstList()) {
				//4、传入关键字id、归类id、归类名称调用【异常实例更新】API。
				ExpInstInner eiInner = new ExpInstInner();
				eiInner.setExcp_inst_id(eei.getExcp_inst_id());
				eiInner.setCatalog_id(inner.getCatalog_id());
				eiInner.setKey_id(inner.getKey_id());
				ExpInstOuter eiOuter = orderExpManager.updateExpInst(eiInner);
			}
		}
		
		//获取esearch开关
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		
		try {
			//5、传入关键字id、归类id、归类名称调用【关键字、归类更新API】。找启龙			
			if("0".equals(is_esearch_write)){
				logger.info("esearch写入开关为N，归类信息更新到文件系统失败!");
			}else{
				EsearchUpdateClassReq req = new EsearchUpdateClassReq();
				ESearchData esData = new ESearchData();
				esData.setKeyword_id(inner.getKey_id());//放入关键字id或名称esData.setKeyword_name(keyName);
				esData.setClass_id(inner.getCatalog_id());//需要更新的归类id
				//esData.setClass_name(ecl.getCatalog_name());//需要更新的归类名称
				esData.setClass_value(ecl.getCatalog_name());
				req.setEsData(esData);
				ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
				EsearchUpdateClassResp resp = client.execute(req, EsearchUpdateClassResp.class);
				if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
					logger.info("调用esearch关键字归类和更新API[EsearchUpdateClassReq]失败,key_id:" + inner.getKey_id() + " 错误信息：" + resp.getError_msg());
					throw new Exception(resp.getError_msg());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			outer.setOuter_status(ConstsCore.ERROR_FAIL);
			outer.setOuter_msg("调用关键字、归类更新API失败   "+e.getMessage());
			CommonTools.addError(ConstsCore.ERROR_FAIL,"调用关键字、归类更新API失败   "+e.getMessage());
		}
		return outer;
	}
	
	@Override
	public SpecvaluesQueryOuter querySpecvalues(SpecvaluesQueryInner inner){
		SpecvaluesQueryOuter outer = new SpecvaluesQueryOuter();
		IDcPublicInfoManager dcPublicInfoManager = (IDcPublicInfoManager)SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicInfoCacheProxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<EsearchSpecvalues> list = dcPublicInfoCacheProxy.getSpecvalues(inner.getSearch_code(), inner.getKey_id(), inner.getKey_word());
		if(list!=null && !list.isEmpty()){
			outer.setSpecvalues(list.get(0));
		}
		return outer;
	}
	
	@Override
	public SpecvaluesCheckProcessedOuter specvaluesCheckProcessed(SpecvaluesCheckProcessedInner inner){
		SpecvaluesCheckProcessedOuter outer = new SpecvaluesCheckProcessedOuter();
		//获取检验规格关键字相关参数
		SpecvaluesCheckParams params = getSpecvaluesCheckParams(inner);
		String key_word = params.getKey_word();
		String search_code = inner.getSearch_code();
		String search_id = inner.getSearch_id();
		String key_rule_id = params.getKey_rule_id();
		String key_word_type = params.getKey_word_type();
		//调用[规格关键字查询]API，判断记录是否存在
		SpecvaluesQueryInner specvaluesInner = new SpecvaluesQueryInner();
		specvaluesInner.setSearch_code(search_code);
		specvaluesInner.setKey_word(key_word);
	    SpecvaluesQueryOuter specvaluesOuter = querySpecvalues(specvaluesInner);
		EsearchSpecvalues specvalues = specvaluesOuter.getSpecvalues();
		String key_id ="";
		//不存在，则插入规格关键字定义表，同时返回结果对象
		if (specvalues == null){
			key_id = this.daoSupport.getSequences("ES_ESEARCH_SPECVALUES_SEQ", "1", 18);
			EsearchSpecvaluesInner insertvaluesInner = new EsearchSpecvaluesInner();
			//插入规格关键字
			insertvaluesInner.setSearch_code(search_code);
			insertvaluesInner.setSearch_id(search_id);
			insertvaluesInner.setKey_id(key_id);
			insertvaluesInner.setKey_word(key_word);
			insertvaluesInner.setKey_rule_id(key_rule_id);
			insertvaluesInner.setMatch_content_type(key_word_type);
			insertSpecvalues(insertvaluesInner);
			//插入规格关键字归类
			EsearchSpecvaluesRelc esearchSpecvaluesRelc = new EsearchSpecvaluesRelc();
			esearchSpecvaluesRelc.setCatalog_id(EcsOrderConsts.EXP_DEFAULT_CATALOG);
			esearchSpecvaluesRelc.setKey_id(key_id);
			esearchSpecvaluesRelc.setCreate_time("sysdate");
			String staff_no ="";
			try{
				staff_no = ManagerUtils.getUserId();
			}catch(Exception ex){
				logger.info("插入规格关键字归类时用户信息为空");
			}
			esearchSpecvaluesRelc.setStaff_no(staff_no);
			insertSpecvaluesRelc(esearchSpecvaluesRelc);
			outer.setKey_id(key_id);
			outer.setIs_wirte(EcsOrderConsts.IS_WRITE_EXCEPTION_1);
			if(StringUtils.equals(EcsOrderConsts.EXP_SUCC, key_word_type)||
					StringUtils.equals(EcsOrderConsts.EXP_FILTER, key_word_type)){
				outer.setIs_wirte(EcsOrderConsts.IS_WRITE_EXCEPTION_0);
			}
		}else {
			String match_content_type = specvalues.getMatch_content_type();
			//判断是否成功标识，是返回null，说明不是异常实例
			if (StringUtils.equals(EcsOrderConsts.EXP_SUCC, match_content_type)||
					StringUtils.equals(EcsOrderConsts.EXP_FILTER, match_content_type)) {
				outer.setIs_wirte(EcsOrderConsts.IS_WRITE_EXCEPTION_0);
			}else{
				key_id = specvalues.getKey_id();
				outer.setKey_id(specvalues.getKey_id());
				outer.setIs_wirte(EcsOrderConsts.IS_WRITE_EXCEPTION_1);
			}
		}
		//如果抽取出来的关键字和旧的一样，无需操作
		if(!StringUtils.equals(key_word, inner.getOld_key_word())){
			deleteSpecvalues(inner.getAction_type(),inner.getOld_key_word(),search_code);
		}
		outer.setKey_word(key_word);
		outer.setOld_key_word(inner.getOld_key_word());
		outer.setSearch_id(search_id);
		return outer;
	}
	
	/**
	 * 删除旧关键字
	 * @param action_type
	 * @param old_key_word
	 * @param search_code
	 */
	public void deleteSpecvalues(String action_type,String old_key_word,String search_code){
		//如果旧关键字不为空，并且操作为修改，删除原有的记录，并将原有的记录保存到历史表
		if(StringUtils.equals(EcsOrderConsts.EXP_M, action_type)&&!StringUtils.isEmpty(old_key_word)){
			SpecvaluesQueryInner specvaluesInner = new SpecvaluesQueryInner();
			specvaluesInner.setSearch_code(search_code);
			specvaluesInner.setKey_word(old_key_word);
		    SpecvaluesQueryOuter specvaluesOuter = querySpecvalues(specvaluesInner);
			EsearchSpecvalues specvalues = specvaluesOuter.getSpecvalues();
			if(specvalues!=null){
				if(isExistSpecvaluesHis(specvalues.getKey_id())){
					this.baseDaoSupport.insert("es_esearch_specvalues_his",specvalues);
				}
				String sql = SF.orderExpSql("DelelteSpecvaluesBySearchAndKey");
				this.baseDaoSupport.execute(sql, new String[]{search_code,old_key_word});
				//删除关键字归类关联数据
				String deleteSpecvaluesRelcSql = SF.orderExpSql("DeleteSpecvaluesRelcByKeyId");
				this.baseDaoSupport.execute(deleteSpecvaluesRelcSql, new String[]{specvalues.getKey_id()});
			}
		}
	}
	
	public boolean isExistSpecvaluesHis(String key_id){
		String sql = "select * from es_esearch_specvalues_his where key_id = ?";
		List list = this.baseDaoSupport.queryForList(sql, key_id);
		if(list==null||list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public SpecvaluesCheckParams getSpecvaluesCheckParams(SpecvaluesCheckProcessedInner inner){
		SpecvaluesCheckParams params = new SpecvaluesCheckParams();
		//调用[抽取关键字]API，获取关键值
		String search_id = inner.getSearch_id();
		String param = inner.getParam();
		ExtractKeyInner extractKeyinner = new ExtractKeyInner();
		extractKeyinner.setSearch_id(search_id);
		extractKeyinner.setParam(param);
		//判断接口异常或者业务异常
		if(StringUtils.equals(EcsOrderConsts.EXP_INF, inner.getExp_type())){
			ExtractKeyOuter extractKeyouter = expConfigManager.extractKeyword(extractKeyinner);
			params.setKey_word(extractKeyouter.getKeyword());
			params.setKey_word_type(extractKeyouter.getKey_word_type());
			params.setKey_rule_id(extractKeyouter.getKey_rule_id());
		}else{
			extractKeyinner.setError_stack_msg(inner.getError_stack_msg());
			ExtractKeyOuter extractKeyouter = expConfigManager.extractBusKeyword(extractKeyinner);
			if(extractKeyouter==null){
				params.setKey_word(inner.getParam());
				params.setKey_word_type("fail");
			}else{
				params.setKey_word(extractKeyouter.getKeyword());
				params.setKey_word_type(extractKeyouter.getKey_word_type());
				params.setKey_rule_id(extractKeyouter.getKey_rule_id());
			}
		}
		//关键字长度最长为500，超过就截取一部分
		String key_word = params.getKey_word();
		if(key_word.length()>500){
			key_word = key_word.substring(0,200);
			params.setKey_word(key_word);
		}
		logger.info("日志id:"+inner.getLog_id()+";抽取关键字："+key_word);
		return params;
	}
	
	/**
	 * 插入规格关键字
	 * @param search_code
	 * @param search_id
	 * @param key_id
	 * @param key_word
	 */
	@Override
	public void insertSpecvalues(EsearchSpecvaluesInner insertvaluesInner){
		EsearchSpecvalues specvalues = new EsearchSpecvalues();
	    specvalues.setSearch_code(insertvaluesInner.getSearch_code());
	    specvalues.setSearch_id(insertvaluesInner.getSearch_id());
	    specvalues.setKey_id(insertvaluesInner.getKey_id());
	    specvalues.setMatch_content(insertvaluesInner.getKey_word());
	    specvalues.setKey_rule_id(insertvaluesInner.getKey_rule_id());
	    try{
	    	specvalues.setCreate_time(DateUtil.getTime2());
	    	specvalues.setMatch_content_type(insertvaluesInner.getMatch_content_type());
	  	    specvalues.setRecord_status(EcsOrderConsts.EXP_00A);
	  	    this.baseDaoSupport.insert("es_esearch_specvalues", specvalues);
	    }catch (Exception e){
	    	logger.info(e.getMessage());
	    }
	}
	
	/**
	 * 插入规格关键字默认归类
	 * @param insertvaluesInner
	 */
	public void insertSpecvaluesRelc(EsearchSpecvaluesRelc inner){
		this.baseDaoSupport.insert("es_esearch_specvalues_relc",inner);
	}
	
	@Override
	public UncheckSpecvalueOuter unCheckSpecvalueUpdate(
			UnCheckSpecvalueInner inner) throws Exception {
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		String searchId = inner.getSearch_id();
		int percent = 0;
		//获取esearch开关
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		//调用【异常实例查询API】
		ExpInstQueryInner expInstQueryInner = new ExpInstQueryInner();
		expInstQueryInner.setSearch_id(searchId);
		expInstQueryInner.setRecord_status("0");
		expInstQueryInner.setMatch_content("未匹配到关键字(缺省)");
		//expInstQueryInner.setKey_id(inner.getKey_id()); //未匹配关键字id
		ExpInstQueryOuter expInstQueryOuter = this.orderExpManager.queryExpInstList(expInstQueryInner);
		if(null != expInstQueryOuter.getEsearchExpInstList() && expInstQueryOuter.getEsearchExpInstList().size()>0){
			List<EsearchExpInstQuery> expInsts = expInstQueryOuter.getEsearchExpInstList();
			SpecvaluesCheckProcessedInner scpi = new SpecvaluesCheckProcessedInner();
			for(int i=0,length=expInsts.size(); i<length; i++){
				if("0".equals(is_esearch_write)){
					logger.info("esearch写入开关为N，抽取关键字失败!");
				}else{
					EsearchExpInstQuery temExpInt = expInsts.get(i);	
					//调用esearch_server查询报文的能力
					ESearchData esData = new ESearchData();
					esData.setLog_id(temExpInt.getLog_id());
					EsearchGetReq esgReq = new EsearchGetReq();
					esgReq.setEsData(esData);
					EsearchGetResp esResp  = client.execute(esgReq, EsearchGetResp.class);	
					Map map = OrderExpUtils.getEsearchParam(inner.getSearch_code(),esResp.getEsData().getOut_param(),esResp.getEsData().getIn_param());
					//调用【规格关键字校验、写入、更新API】
					scpi.setLog_id(temExpInt.getLog_id());
					scpi.setOld_key_word(temExpInt.getMatch_content());
					scpi.setAction_type(EcsOrderConsts.EXP_M);
					scpi.setExp_type(EcsOrderConsts.EXP_INF);
					scpi.setParam((String)map.get("param"));
					scpi.setSearch_id((String)map.get("search_id"));
					SpecvaluesCheckProcessedOuter scpo = this.specvaluesCheckProcessed(scpi);
					
					
					if(!StringUtil.isEmpty(scpo.getKey_word())){
						if(!scpo.getKey_word().equals(scpo.getOld_key_word())){//新关键字、与旧关键字不等
							//调用关键字、归类更新API
							EsearchUpdateReq req = new EsearchUpdateReq();
							ESearchData esearch = new ESearchData();
							esearch.setKeyword_id(scpo.getKey_id());
							esearch.setLog_id(temExpInt.getLog_id());
							//esearch.setKeyword_value(scpo.getKey_word());
							esearch.setKeyword_name(scpo.getKey_word());
							req.setEsData(esearch);
							EsearchUpdateResp resp = client.execute(req, EsearchUpdateResp.class);
							if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
								logger.info("log_id="+temExpInt.getLog_id() + "更新失败！"+ "具体信息：" + resp.getError_msg());
							}else{
								logger.info("log_id="+temExpInt.getLog_id() + "更新成功！");
							}
							//调用异常实例更新API
							ExpInstInner eii = new ExpInstInner();
							eii.setKey_id(scpo.getKey_id());
							eii.setExcp_inst_id(temExpInt.getExcp_inst_id());
							this.orderExpManager.updateExpInst(eii);
						}
					}
					//计算处理进度
					percent = i/length * 100;
					ServletActionContext.getRequest().getSession().setAttribute("ORDEREXP_PROGRESS", new Integer(percent));
				}
			}
		}
		//返回参数
		UncheckSpecvalueOuter uso = new UncheckSpecvalueOuter();
		
		return uso;
	}
	
	@Override
	public ChangeSpecvalueUpdateOuter changeSpecvalueUpdateExpInst(
			ChangeSpecvalueUpdateInner inner) throws Exception {
		String keyId = inner.getKey_id();
		String searchId = inner.getSearch_id();
		//根据key_id 查询规格关键字,获取关键字值
		SpecvalueInner specvalueInner = new SpecvalueInner();
		specvalueInner.setKey_id(keyId);
		EsearchSpecvalues esearchSpecvalues = this.esearchSpecvaluesManager.findSpecvalues(specvalueInner);
		String keyValue = "";//关键字值
		if(null != esearchSpecvalues){
			keyValue = esearchSpecvalues.getMatch_content();
		}
		//获取esearch开关
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		//调用异常实例查询API
		ExpInstQueryInner eqi = new ExpInstQueryInner();
		eqi.setKey_id(keyId);
		eqi.setSearch_id(searchId);
		eqi.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_0);
		ExpInstQueryOuter eqo =  this.orderExpManager.queryExpInstList(eqi);
		//循环迭代调用【关键字、归类更新API】
		List<EsearchExpInstQuery> expInsts = eqo.getEsearchExpInstList();
		if(null != expInsts && expInsts.size()>0){
			ESearchData esearch = null;
			for(int i=0,length=expInsts.size(); i<length; i++){
				EsearchExpInstQuery eeq = expInsts.get(i);
				//调用【关键字、归类更新API】
				if("0".equals(is_esearch_write)){
					logger.info("esearch写入开关为N，关键字更新到文件系统失败!");
				}else{
					EsearchUpdateReq req = new EsearchUpdateReq();
					esearch = new ESearchData();
					esearch.setKeyword_id(eeq.getKey_id());
					esearch.setLog_id(eeq.getLog_id());
					//esearch.setKeyword_value(keyValue);
					esearch.setKeyword_name(keyValue);
					req.setEsData(esearch);
					ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
					EsearchUpdateResp resp = null;
						resp = client.execute(req, EsearchUpdateResp.class);
					if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
						logger.info("log_id="+eeq.getLog_id() + "更新失败！"+ "具体信息：" + resp.getError_msg());
					}else{
						logger.info("log_id="+eeq.getLog_id() + "更新成功！");
					}
				}
				
			}
		}
		//返回参数
		ChangeSpecvalueUpdateOuter csuo = new ChangeSpecvalueUpdateOuter();
		
		return csuo;
	}
	
	@Override
	public ChangeSpecvRuleUpdateExpInstOuter changeSepcvRuleUpdateExpInst(
			ChangeSpecvRuleUpdateExpInstInner inner) throws Exception {
		//获取esearch开关
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		List<ExpInstQueryInner> expInsts = inner.getList();
		if(null != expInsts && expInsts.size() > 0){
			SpecvaluesCheckProcessedInner scpi = null;
			for(int i=0,length=expInsts.size(); i<length; i++){
				if("0".equals(is_esearch_write)){
					logger.info("esearch写入开关为N，抽取关键字失败!");
				}else{
					ExpInstQueryInner expInst = expInsts.get(i);		
					try {
						this.extractSpecvalue(client, expInst);
						logger.info("--------------------异常单："+ expInst.getExcp_inst_id() +" 重新抽取关键字成功----------------------");
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("--------------------" + e.getMessage() + "--------------------------");
					}
				}
			}
		}
		ChangeSpecvRuleUpdateExpInstOuter csrueo = new ChangeSpecvRuleUpdateExpInstOuter();
		
		return csrueo;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void extractSpecvalue(ZteClient client, ExpInstQueryInner expInst) throws Exception{
		try {
			//调用esearch_server查询报文的能力
			logger.info("关键字抽取-----esearch_server查询报文API(异常实例id："+expInst.getExcp_inst_id()+") 开始执行....");
			ESearchData esData = new ESearchData();
			esData.setLog_id(expInst.getLog_id());
			EsearchGetReq esgReq = new EsearchGetReq();
			esgReq.setEsData(esData);
			EsearchGetResp esResp  = client.execute(esgReq, EsearchGetResp.class);	
			if(ConstsCore.ERROR_FAIL.equals(esResp.getError_code())){
				logger.info("esearch_server查询报文API(log_id："+expInst.getExcp_inst_id()+")"+"调用失败,错误信息："+esResp.getError_msg());
				CommonTools.addError(ConstsCore.ERROR_FAIL, "esearch_server查询报文API调用失败");
			}
			Map map = OrderExpUtils.getEsearchParam(expInst.getSearch_code(),esResp.getEsData().getOut_param(),esResp.getEsData().getIn_param());
			logger.info("关键字抽取-----esearch_server查询报文API(异常实例id："+expInst.getExcp_inst_id()+") 执行完成.");
			
			//调用【规格关键字检验、写入、更新API】
			logger.info("关键字抽取-----规格关键字检验、写入、更新API(异常实例id："+expInst.getExcp_inst_id()+") 开始执行....");
			SpecvaluesCheckProcessedInner scpi = new SpecvaluesCheckProcessedInner();
			scpi.setLog_id(expInst.getLog_id());
			scpi.setOld_key_word(expInst.getMatch_content());
			scpi.setAction_type(EcsOrderConsts.EXP_M);
			scpi.setExp_type(EcsOrderConsts.EXP_INF);
			scpi.setParam((String)map.get("param"));
			scpi.setSearch_id((String)map.get("search_id"));
			scpi.setSearch_code(expInst.getSearch_code());
			SpecvaluesCheckProcessedOuter scpo = this.specvaluesCheckProcessed(scpi);
			logger.info("关键字抽取-----规格关键字检验、写入、更新API(异常实例id："+expInst.getExcp_inst_id()+") 执行完成.");
			
			//如果是不需要处理的异常，标记为已处理，否则更新关键字值
			if(EcsOrderConsts.IS_WRITE_EXCEPTION_0.equals(scpo.getIs_wirte())){
				try {
					ExpInstInner eii = new ExpInstInner();
					eii.setExcp_inst_id(expInst.getExcp_inst_id());
					eii.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
					this.orderExpManager.updateExpInst(eii);
					logger.info("关键字抽取-----异常单："+expInst.getExcp_inst_id()+" 为需要过滤的异常，标记为已处理成功。");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("关键字抽取-----异常单："+expInst.getExcp_inst_id()+" 为需要过滤的异常，标记为已处理失败。");
				}
			}else{
				//调用【异常实例更新API】
				ExpInstInner eii = new ExpInstInner();
				eii.setExcp_inst_id(expInst.getExcp_inst_id());
				eii.setKey_id(scpo.getKey_id());
				eii.setExcp_update_time(DateUtil.getTime2());//只有关键字更新
				this.orderExpManager.updateExpInst(eii);
				//调用【关键字、归类更新API】
				logger.info("关键字抽取-----esearch关键字归类和更新API(异常实例id："+expInst.getExcp_inst_id()+") 开始执行....");
				EsearchUpdateReq req = new EsearchUpdateReq();		
				ESearchData esearch = new ESearchData();
				esearch.setLog_id(expInst.getLog_id());
				esearch.setKeyword_id(scpo.getKey_id());
				esearch.setKeyword_name(scpo.getKey_word());
				esearch.setClass_id((String)this.orderExpManager.getCataLogId(scpo.getKey_id()).get("catalog_id"));
				esearch.setClass_value((String)this.orderExpManager.getCataLogId(scpo.getKey_id()).get("catalog_name"));
				req.setEsData(esearch);
				EsearchUpdateResp resp = client.execute(req, EsearchUpdateResp.class);	
				if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
					logger.info("log_id="+expInst.getLog_id() + "更新失败！"+ "具体信息：" + resp.getError_msg());
					throw new Exception("调用esearch 关键字归类和更新(EsearchUpdateReq)API失败");
				}else{
					logger.info("log_id="+expInst.getLog_id() + "更新成功！");
				}
				logger.info("关键字抽取-----esearch关键字归类和更新API(异常实例id："+expInst.getExcp_inst_id()+") 执行完成.");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addError(ConstsCore.ERROR_FAIL,"异常单："+expInst.getExcp_inst_id()+" 重新抽取关键字失败"+e.getMessage());
		}
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
	
	public IOrderExpManager getOrderExpManager() {
		return orderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		this.orderExpManager = orderExpManager;
	}
	
	public IEsearchSpecvaluesManager getEsearchSpecvaluesManager() {
		return esearchSpecvaluesManager;
	}

	public void setEsearchSpecvaluesManager(
			IEsearchSpecvaluesManager esearchSpecvaluesManager) {
		this.esearchSpecvaluesManager = esearchSpecvaluesManager;
	}

	@Override
	public Page querySpecvaluesPage(SpecvalueInner inner) {
		StringBuffer sql = new StringBuffer();
		if("all".equals(inner.getCatalog())){//查询所有
			sql.append(SF.orderExpSql("QuerySpecvaluesPage"));
		}else if("yes".equals(inner.getCatalog())){//查询已归类
			sql.append(SF.orderExpSql("QuerySpecvaluesPageIsCatalog"));
		}else if("no".equals(inner.getCatalog())){//查询未归类
			sql.append(SF.orderExpSql("QuerySpecvaluesPageNotCatalog"));
		}else{//默认查所有
			sql.append(SF.orderExpSql("QuerySpecvaluesPage"));
		}
		
		List<String> where = new ArrayList<String>();
		if(!StringUtil.isEmpty(inner.getMatch_content())){
			sql.append(" AND ees.match_content like ?");
			where.add("%" + inner.getMatch_content() + "%");
		}
		if(!StringUtil.isEmpty(inner.getStart_date())){
			String datetime = inner.getStart_date() + " 00:00:00";
			sql.append(" AND ees.create_time >="+DBTUtil.to_sql_date("?", 2));
			where.add(datetime);
		}
		if(!StringUtil.isEmpty(inner.getEnd_date())){
			String datetime = inner.getEnd_date() + " 23:59:59";
			sql.append(" AND ees.create_time <="+DBTUtil.to_sql_date("?", 2));
			where.add(datetime);
		}
		
		if(!StringUtil.isEmpty(inner.getSearch_code())){
			sql.append(" AND ees.search_code = ?");
			where.add(inner.getSearch_code());
		}
		if(!StringUtil.isEmpty(inner.getSearch_id())){
			sql.append(" AND ees.search_id = ?");
			where.add(inner.getSearch_id());
		}
		String countSql = "select count(*) from ("+sql+") cord";
		Page page  = this.baseDaoSupport.queryForCPage(sql.toString(), inner.getPageIndex(), inner.getPageSize(), EsearchSpecvalues.class, countSql, where.toArray());
		return page;
	}

	@Override
	public void updateSpecvalues(EsearchSpecvalues inner) {
		//需要更新的字段
		Map<String,String> fields = new HashMap<String, String>();
		if(null != inner.getTimeout_flag()){
			fields.put("timeout_flag", inner.getTimeout_flag());
		}
		if(null != inner.getTimeout_limit()){
			fields.put("timeout_limit", inner.getTimeout_limit());
		}
		if(null != inner.getWarming_flag()){
			fields.put("warming_flag", inner.getWarming_flag());
		}
		if(null != inner.getWarming_limit()){
			fields.put("warming_limit", inner.getWarming_limit());
		}
		if(null != inner.getMatch_content_type()){
			fields.put("match_content_type", inner.getMatch_content_type());
		}
		//条件
		Map<String, String> where = new HashMap<String, String>();
		where.put("key_id", inner.getKey_id());
		
		this.baseDaoSupport.update("es_esearch_specvalues", fields, where);
		
	}

	@Override
	public EsearchSpecvalues findSpecvalues(SpecvalueInner inner) {
		String sql = SF.orderExpSql("FindSpecvalues");
		EsearchSpecvalues esearchSpecvalues = this.baseDaoSupport.queryForObject(sql, EsearchSpecvalues.class, inner.getKey_id());
		return esearchSpecvalues;
	}

	@Override
	public SpecvaluesCheckParams imitateExtractSpecvalues(
			SpecvaluesCheckProcessedInner inner) {
		String logId = inner.getLog_id();
		String searchId = inner.getSearch_id();
		String searchCode = inner.getSearch_code();
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
		
		//调用esearch_server查询报文的能力
		logger.info("关键字抽取-----esearch_server查询报文API(log_id："+logId+") 开始执行....");
		ESearchData esData = new ESearchData();
		esData.setLog_id(logId);
		EsearchGetReq esgReq = new EsearchGetReq();
		esgReq.setEsData(esData);
		EsearchGetResp esResp  = client.execute(esgReq, EsearchGetResp.class);	
		if(ConstsCore.ERROR_FAIL.equals(esResp.getError_code())){
			logger.info("esearch_server查询报文API(log_id："+logId+")"+"调用失败,错误信息："+esResp.getError_msg());
			CommonTools.addError(ConstsCore.ERROR_FAIL, "esearch_server查询报文API调用失败");
		}
		Map map = OrderExpUtils.getEsearchParam(searchCode,esResp.getEsData().getOut_param(),esResp.getEsData().getIn_param());
		logger.info("关键字抽取-----esearch_server查询报文API(log_id："+logId+") 执行完成.");
		
		//调用【规格关键字检验、写入、更新API】
		logger.info("关键字抽取-----关键字抽取API(log_id："+logId+") 开始执行....");
		SpecvaluesCheckProcessedInner scpi = new SpecvaluesCheckProcessedInner();
		scpi.setParam((String)map.get("param"));
		scpi.setSearch_id((String)map.get("search_id"));
		scpi.setExp_type(EcsOrderConsts.EXP_INF);
		SpecvaluesCheckParams result = this.getSpecvaluesCheckParams(scpi);
		logger.info("关键字抽取-----关键字抽取API(log_id："+logId+") 执行完成.");
		return result;
	}

	@Override
	public List<EsearchSpecvalues> querySpecvalues(SpecvalueInner inner) {
		StringBuffer sql = new StringBuffer("SELECT a.key_id,a.match_content FROM es_esearch_specvalues a WHERE 1=1");
		List<String> sqlParams = new ArrayList<String>();
		if(inner != null){
			if(!StringUtil.isEmpty(inner.getMatch_content())){
				sql.append(" AND a.match_content like ?");
				sqlParams.add(inner.getMatch_content() + "%");
			}
		}
		List<EsearchSpecvalues> result = this.baseDaoSupport.queryForList(sql.toString(), EsearchSpecvalues.class, sqlParams.toArray());
		return result;
	}

}