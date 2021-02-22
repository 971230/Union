package com.ztesoft.check.fun;

import java.util.Iterator;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.inf.Check;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class SqlCheck  extends AbstractCheckHander implements Check {
	
	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

	@Override
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException {
		// TODO Auto-generated method stub
		IdentifyResp resp = new IdentifyResp();
		resp.setCode(EccConsts.IDENTI_SUCCESS);
		resp.setMsg("校验成功.");
		Map paramMap = identification.getFullMapParam();
		String sql = identification.getClazz();
		sql = convertSql(paramMap,sql);
		//sql  模式为 "select '0001' as hint_level,'test' as hint_message from es_ecc_fun where fun_id={$fun_id}";
		try{
			Map returnMap = this.baseDaoSupport.queryForMap(sql);
			if(null != returnMap && !returnMap.isEmpty()){
				resp.setCode(returnMap.get("hint_level").toString());
				resp.setMsg(returnMap.get("hint_message").toString());
			}
		}catch(Exception e){
			throw new ApiBusiException(e.getMessage());
/*			resp.setCode(EccConsts.IDENTI_9999);
			resp.setMsg(e.getMessage());
			return resp;*/
		}
		return resp;
	}
	
	//sql参数转换
    public String convertSql(Map paramMap, String strSql)
    {
        if (strSql.indexOf("{") < 0)
            return strSql;
        if (paramMap != null)
            for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();)
            {
                Map.Entry map = (Map.Entry) it.next();
                String key = (String) map.getKey();
                Object val = map.getValue();
                if (val instanceof String)
                {
                    String _val = (String) val;
                    String newFieldName = "\\{\\$" + key + "\\}";
                    strSql = strSql.replaceAll(newFieldName, _val);
                }
            }
        strSql = strSql.replaceAll("\\{\\$[\\w]+\\}", "").replaceAll("''", "'");
        return strSql;
    }
	
}
