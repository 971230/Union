package com.ztesoft.component.common.staticdata.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DataUtil;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.dao.DcSqlDAO;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAO;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAOFactory;
//import com.ztesoft.grp.IFieldHandler;
//import com.ztesoft.grp.factory.FieldJudgeFactory;

public class StaticAttrBean extends DictAction{
	
	private static StaticAttrBean bean = new StaticAttrBean();

	public static StaticAttrBean getInstance(){
		return bean;
	}
	public ArrayList getStaticAttr(DynamicDict dto ) throws Exception {

		String attrCode = (String)dto.getValueByName("parameter") ;
		List attrList = new ArrayList();
		
		//@author add by qiu.huayang
		//@date 20111117
//		IFieldHandler h = FieldJudgeFactory.getComplexFieldContentHandler();
//		List _attrList = h.getStaticAttrList(attrCode);
//		if(_attrList.size()>0){//如果有值，则直接返回不往下处理，和明朝讨论的结果
//			attrList.addAll(_attrList);
//			return (ArrayList)attrList;
//		}
		//@Desc end
		
		StaticAttrDAO sd = StaticAttrDAOFactory.getInstance().getStaticAttrDAO();
		attrList = sd.findByCode(attrCode);

		return (ArrayList) attrList;
	}

	public ArrayList getAllStaticAttr(DynamicDict dto) throws Exception {

		StaticAttrDAO staticAttrDAO = StaticAttrDAOFactory.getInstance()
				.getStaticAttrDAO();
		ArrayList attrList = staticAttrDAO.findAllStaticAttr();
		dto.setValueByName("result", attrList) ;
		return attrList;
	}
	
	/**
	 * 加载静态数据配置信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel listDataOfDcSql(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto) ;
		String dcName = Const.getStrValue(m, "dcName");
		int pi = Const.getPageIndex(m) ;
		int ps = Const.getPageSize(m) ;
		StringBuffer whereCond = new StringBuffer() ;
		List param = new ArrayList() ;
		if(!DataUtil.ifEmpty(dcName)){
			whereCond.append(" and lower(dc_name) like ?") ;
			param.add("%" + dcName.toLowerCase().trim() + "%") ;
		}
		
		return new DcSqlDAO().searchByCond(whereCond.toString(), param, pi, ps) ;
	}

}

