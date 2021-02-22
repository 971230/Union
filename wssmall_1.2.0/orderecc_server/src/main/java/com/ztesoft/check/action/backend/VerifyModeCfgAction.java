package com.ztesoft.check.action.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;

import com.alibaba.fastjson.JSON;
import com.ztesoft.check.model.Fun;
import com.ztesoft.check.service.IVerifyModeCfgManager;
import com.ztesoft.net.framework.action.WWAction;


/**
 * 校验方式配置action
 * @author Administrator
 *
 */
public class VerifyModeCfgAction extends WWAction{
	private Map<String,String> params = new HashMap<String,String>();
	private IVerifyModeCfgManager verifyModeCfgManager;	
	private String id ;
	private MsgBox msg = new MsgBox();
	private Fun fun;
	private List estatos;
	private List esimpls;
	
	public List getEstatos() {
		return estatos;
	}

	public void setEstatos(List estatos) {
		this.estatos = estatos;
	}

	public List getEsimpls() {
		return esimpls;
	}

	public void setEsimpls(List esimpls) {
		this.esimpls = esimpls;
	}

	public MsgBox getMsg() {
		return msg;
	}

	public void setMsg(MsgBox msg) {
		this.msg = msg;
	}

	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	public Map<String, String> getParams() {
		return params;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public IVerifyModeCfgManager getVerifyModeCfgManager() {
		return verifyModeCfgManager;
	}

	public void setVerifyModeCfgManager(IVerifyModeCfgManager verifyModeCfgManager) {
		this.verifyModeCfgManager = verifyModeCfgManager;
	}

    public void init(){
		this.estatos = CommonDataFactory.getInstance().listDropDownData("DC_VERIFY_STATUS");
		this.esimpls = CommonDataFactory.getInstance().listDropDownData("DC_VERIFY_MODE");
    }

	public String list(){
		webpage = verifyModeCfgManager.getVerifyModeList(getPage(), getPageSize(),params);
		return "list";
	}
	
	public String edit() {
		init();
		if (id != null) {
			fun = verifyModeCfgManager.get(id);
		}
		return "input";
	}

	public String save() {
		String flag = "新增";
		try {
			if (StringUtils.isNotBlank(fun.getFun_id())){
				flag = "编辑";
			}
			verifyModeCfgManager.saveOrUpdate(fun);
			msg.addResult(MESSAGE, flag + "成功");
			msg.addResult("url", "verifyModeCfg!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		}  catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE, flag + "失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
}
