package com.ztesoft.net.service;

import java.util.List;

import model.EsearchSpecvalues;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.SpecvaluesCheckParams;
import com.ztesoft.net.param.inner.ChangeSpecvRuleUpdateExpInstInner;
import com.ztesoft.net.param.inner.ChangeSpecvalueUpdateInner;
import com.ztesoft.net.param.inner.EsearchSpecvaluesInner;
import com.ztesoft.net.param.inner.SpecvalueInner;
import com.ztesoft.net.param.inner.SpecvaluesCheckProcessedInner;
import com.ztesoft.net.param.inner.SpecvaluesClassifyInner;
import com.ztesoft.net.param.inner.SpecvaluesQueryInner;
import com.ztesoft.net.param.inner.UnCheckSpecvalueInner;
import com.ztesoft.net.param.outer.ChangeSpecvRuleUpdateExpInstOuter;
import com.ztesoft.net.param.outer.ChangeSpecvalueUpdateOuter;
import com.ztesoft.net.param.outer.SpecvaluesCheckProcessedOuter;
import com.ztesoft.net.param.outer.SpecvaluesClassifyOuter;
import com.ztesoft.net.param.outer.SpecvaluesQueryOuter;
import com.ztesoft.net.param.outer.UncheckSpecvalueOuter;

public interface IEsearchSpecvaluesManager {
	
	/**
	 * 关键字归类
	 */
	public SpecvaluesClassifyOuter specvaluesClassify(SpecvaluesClassifyInner inner);

	/**
	 * 规格关键字查询(支持接口编码和关键字id或者接口编码和关键字值查询)
	 * @param inner
	 * @return
	 */
	public  SpecvaluesQueryOuter querySpecvalues(SpecvaluesQueryInner inner);
	
	/**
	 * 规格关键字校验、写入、更新
	 * @param inner
	 * @return
	 */
	public  SpecvaluesCheckProcessedOuter specvaluesCheckProcessed(SpecvaluesCheckProcessedInner inner);

	/**
	 * 未匹配关键字异常更新
	 * @param inner
	 * @return
	 */
	public UncheckSpecvalueOuter unCheckSpecvalueUpdate(UnCheckSpecvalueInner inner) throws Exception;
	
	/**
	 * 关键字修改更新
	 * @param inner
	 * @return
	 */
	public ChangeSpecvalueUpdateOuter changeSpecvalueUpdateExpInst(ChangeSpecvalueUpdateInner inner) throws Exception;
	
	/**
	 * 抽取关键字异常更新
	 * @param inner
	 * @return
	 */
	public ChangeSpecvRuleUpdateExpInstOuter changeSepcvRuleUpdateExpInst(ChangeSpecvRuleUpdateExpInstInner inner) throws Exception;

	/**
	 * 查询关键字分页列表（支持关键字模糊查询、关键字创建时间、是否归类）
	 * @param inner
	 * @return
	 */
	public Page querySpecvaluesPage(SpecvalueInner inner);
	/**
	 * 查询关键字列表（仅查关键字表）查询人工标记异常用
	 * @param inner
	 * @return
	 */
	public List<EsearchSpecvalues> querySpecvalues(SpecvalueInner inner);
	
	public void insertSpecvalues(EsearchSpecvaluesInner insertvaluesInner);
	
	/**
	 * 更新关键字信息
	 * @param inner
	 */
	public void updateSpecvalues(EsearchSpecvalues inner);
	
	/**
	 * 查找关键字信息
	 * @param inner
	 * @return
	 */
	public EsearchSpecvalues findSpecvalues(SpecvalueInner inner);
	
	/**
	 * 模拟抽取关键字
	 * @param inner
	 * @return
	 */
	public SpecvaluesCheckParams imitateExtractSpecvalues(SpecvaluesCheckProcessedInner inner);

}
