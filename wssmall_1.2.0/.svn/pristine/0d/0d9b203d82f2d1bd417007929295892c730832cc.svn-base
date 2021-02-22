//package com.ztesoft.ibss.ct.vo;
//
//import com.ztesoft.accept.attr.BusiObject;
//
//import java.util.List;
//import java.util.Map;
//
//public class ServCommentSpec extends BusiObject {
//
//	private static final long serialVersionUID = 1L;
//
//	public void loadFromMap(Map map) {
//		super.loadFromMap(map);
//	}
//	
//	/*查询规格数据*/
//	public List querySpecByItemCode(String modual_code, String item_code) { // and instr(b.rule_kind,?,1,1)>0
//		String querySql = "select a.* from twb_serv_comment_spec a where a.modual_code = ? and ( instr(?,a.item_code,1,1)>0  or a.item_code ='-1') and a.state ='1' and sysdate between a.eff_date and a.exp_date";
//		return getSqlExeInst().execForMapList(querySql,new String[] { modual_code, item_code });
//	}
//
//	public String querySpecCountByItemCode(String modual_code, String item_code) {
//		String querySql = "select count(*) from twb_serv_comment_spec a where a.modual_code = ? and (a.item_code = ? or a.item_code ='-1') and a.state ='1' and sysdate between a.eff_date and a.exp_date";
//		return getSqlExeInst().querySingleValue(querySql,new String[] { modual_code, item_code });
//	}
//	
//}
