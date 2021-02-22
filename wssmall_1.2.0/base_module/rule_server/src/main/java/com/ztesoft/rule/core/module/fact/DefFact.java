package com.ztesoft.rule.core.module.fact;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.comm.Error;
import com.ztesoft.rule.core.module.comm.Errors;
import com.ztesoft.rule.core.util.ServiceException;

/**
 * 
 * 默认Fact对象,其他Fact对象均需继承改对象
 * 
 * 一.完成功能：
 * 1.提供添加错误处理函数
 * 2.提供添加规则执行结果处理函数
 * 3.提供filter处理判断
 * 4.有error时,退出规则运行
 * 5.提供持久化散列实现对象 factFinalResult/factEveryRuleResults
 * 
 * 二.继承类要求说明：
 * 1.继承DefFact的类需要用@FactTag(xx...xxx...)做声明
 * 2.继承DefFact的类,其属性需要声明为@FactAttrTag(xx...xxx...)做声明,
 * - 前面1/2两点目的是为前台配置,做FactAnnInit初始化时使用
 * 3.之类可选择性覆盖实现：factFinalResult/factEveryRuleResults
 * 
 * @author easonwu 2013-12-03
 *
 */
public abstract class DefFact implements IFact{
	
	//结果值对象key
	public static final String RESULT_VALUE_KEY = "RESULT" ;
	
	//验证通过filter标识，默认可通过验证，进入计算WorkingMemory当中
	private boolean validFlag = true ;
	
	//错误信息列表记录器对象：记录过滤器信息、执行过程错误信息等;与它相关Func可作为RHS部分
	private Errors errors = new Errors() ;
	
	//判断errors是否有对象,如有,则规则退出执行(可选,由业务程序决定);可作为LHS部分
	private boolean hasError = false ;
	
	//规则执行结果列表:针对当前Fact没在一个rule中正常执行完毕之后的结果;与它相关Func可作为RHS部分
	private RuleResults ruleResults = new RuleResults() ; 
	
	
	//Fact类型标识，用于区隔不同实现类
	private String mid_data_code ;
	private String plan_id ;
	
	//【重要】最终幻想汇总计算结果,以Map形式传递
	private Map factFinalResultMap = new HashMap() ;
	
	//【重要】最终没规则处理结果,每个阶段值以Map方式传递
	private List<Map> factEveryRuleResultsList = new ArrayList<Map>() ;
	
	
	/**
	 * 过滤属性,下属属性无需放到处理对象中
	 * @param name
	 * @return
	 */
	private boolean canFiltFiled(String name ){
		return name.equals("factFinalResultMap")
		|| name.equals("ruleResults")
		|| name.equals("factEveryRuleResultsList")
		|| name.equals("validFlag")
		|| name.equals("errors") ;
	}
	
	/**
	 * 规则最终结果集
	 * 1.提供给Fact正常运行完毕后,【汇总结果】持久化使用
	 * 2.子类需把保存到数据库的对象散列到Map中
	 * @return
	 */
	public Map factFinalResult(){
		//提取field值
		processFields(factFinalResultMap) ;
		//默认处理====================mochunrun 2014-3-5
		double amount = 0d;
		for(RuleResult rs:ruleResults.getResults()){
			amount += Double.parseDouble(String.valueOf(rs.getResult()));
		}
		//默认处理====================mochunrun 2014-3-5
		factFinalResultMap.put("amount", amount);//方案结算结果，所有规则的汇总金额
		
		//处理结果集
		processFinalResult( factFinalResultMap ,ruleResults ) ;
		
		return factFinalResultMap ;
	}
	
	/**
	 * 专供子类实现
	 */
	public abstract void processFinalResult(Map data , RuleResults ruleResults ) ;
	
	public abstract void handleOneRuleResule(Map data , RuleResult ruleResult) ;
	
	
	
	/**
	 * 每个规则结果集
	 * 1.提供给Fact正常运行完毕后,【清单结果】持久化使用
	 * 2.子类需把保存到数据库的对象(ruleResults)散列到Map中
	 * 3.handleOneRuleResule(Map data , RuleResult ruleResult)  由子类实现
	 * @return
	 */
	public List<Map> factEveryRuleResults() {
		
		//提取field值
		final Map commData = new HashMap() ;
		processFields(commData) ;
		
		//遍历处理每个RuleResult
		List<Map> commDatas = new ArrayList<Map>(this.ruleResults.getResults().size()) ;
		for(RuleResult ruleResult : ruleResults.getResults()){
			Map data = new HashMap() ;
			commDatas.add(data) ;
			
			data.putAll(commData) ;
			//默认处理====================mochunrun 2014-3-5
			data.put("amount", ruleResult.getResult()) ;//规则结算结果
			data.put("rule_id", ruleResult.getRule_id());
			data.put("rule_code", ruleResult.getRuleCode());
			//默认处理====================mochunrun 2014-3-5
			handleOneRuleResule(data , ruleResult) ;
		}
		
		return commDatas ;
	}

	/**
	 * 添加结果
	 * @param aResult
	 */
	public void addResult(RuleResult aResult){
		ruleResults.addResult(aResult) ;
	}
	
	/**
	 * 添加结果
	 * @param ruleCode 规则编码
	 * @param aRuleResult 规则结果
	 */
	public void addResult(String ruleCode , Object aRuleResult){
		ruleResults.addResult( ruleCode ,  aRuleResult);
	}
	
	/**
	 * 添加结果
	 * @param ruleCode 规则编码
	 * @param aRuleResult 规则结果
	 */
	public void addResult(String ruleCode ,String rule_id, Object aRuleResult){
		ruleResults.addResult( ruleCode ,rule_id,  aRuleResult);
	}

	
	/**
	 * 添加错误
	 * @param error 错误对象
	 */
	public void addError(Error error){
		errors.addError(error) ;
		setHasError(true) ;
	}
	
	/**
	 * 添加错误
	 * @param ruleCode 规则编码
	 * @param errorCode 错误码
	 * @param errorMsg 错误提示信息
	 * @param stackTrace 错误堆栈
	 */
	public void addError(String ruleCode , String errorCode , String errorMsg , String stackTrace){
		errors.addError( ruleCode ,  errorCode ,  errorMsg ,  stackTrace) ;
		setHasError(true) ;
	}
	
	/**
	 * 添加错误
	 * @param ruleCode 规则编码
	 * @param errorCode 错误码
	 * @param errorMsg 错误提示信息
	 */
	public void addError(String ruleCode , String errorCode , String errorMsg){
		errors.addError( ruleCode ,  errorCode ,  errorMsg ) ;
		setHasError(true) ;
	}
	
	
	/**
	 * Filter处理判断,遇到为false时,则停止运行
	 * 改fact将不会进入规则中运行
	 * @return
	 */
	public boolean isValidFlag() {
		return validFlag;
	}

	/**
	 * 在Filter中设置为false,当Filter验证当前Fact不符合运行要求时
	 * @param validFlag
	 */
	public void setValidFlag(boolean validFlag) {
		this.validFlag = validFlag;
	}

	/**
	 * 作为LHS条件使用,由rule决定是否继续或者退出运行
	 * @return
	 */
	public boolean isHasError() {
		return hasError;
	}

	/**
	 * 由addError调用,设置为true
	 * @param hasError
	 */
	private void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	


	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getMid_data_code() {
		return mid_data_code;
	}


	public void setMid_data_code(String mid_data_code) {
		this.mid_data_code = mid_data_code;
	}

	
	public Errors getErrors() {
		return errors;
	}


	private void setErrors(Errors errors) {
		this.errors = errors;
	}


	public RuleResults getRuleResults() {
		return ruleResults;
	}


	private  void setRuleResults(RuleResults ruleResults) {
		this.ruleResults = ruleResults;
	}

	
	//pojo > map处理
	private void processFields(Map  data){
		aClassHandle( getClass() ,  data ) ;//子类
		aClassHandle( getClass().getSuperclass() ,  data ) ;//当前类
	}
	
	/**
	 * 处理具体class
	 * @param clazz
	 * @param data
	 */
	private void aClassHandle(Class clazz ,Map  data ){
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				
				f.setAccessible(true) ;
				
				//判断是否为static 声明,凡是static声明变量,不做传递拷贝处理
				boolean isStatic = Modifier.isStatic(f.getModifiers());
				
				//过滤不需要处理的属性
				if(isStatic || canFiltFiled(f.getName()))
					continue ;

				data.put(f.getName(),  f.get(this));
			}
			
		} catch (IllegalArgumentException e) {
			throw new ServiceException(e) ;
		} catch (IllegalAccessException e) {
			throw new ServiceException(e) ;
		}
	}
}
