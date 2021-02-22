package zte.net.ecsord.params.base.req;

import java.util.Map;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.model.Outer;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 属性同步
 * @作者 wui
 * @创建日期 2014-9-29 
 * @版本 V 1.0
 */
public class AttrSyLoadListReq extends ZteRequest{
    
	private Outer outer;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		if(outer==null){CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "outer对象不能为空！"));}
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.attr.req.attrsyloadListReq";
	}

	public Outer getOuter() {
		return outer;
	}

	public void setOuter(Outer outer) {
		this.outer = outer;
	}

}
