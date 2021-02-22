/**
 * 
 */
package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Aries
 * 
 */
public class PageTagUtils {

	private static final String VISIBLE = "0";

	private static final String READONLY = "1";

	private static final String SUBLIST = "2";

	private static final String CHINA_DIS = "3";//中文显示的问题

	private static final String REQUIRED = "4";

	private static final String POPUP_AND_READONLY = "5";
	
	private static final String CHINA_DIS_AND_DATE="6";//中文显示
	
	
	private static Map intercs=new HashMap();

	private static final PageTagUtils instance = new PageTagUtils();

	protected PageTagUtils() {
		super();
	}

	
	public static PageTagUtils getInstance() {
		return instance;
	}

	//初始化查询
	private static List datas=new ArrayList();
	
//	private static String configsql="select * from do_page_element";
	
	
	public void initlize(){
		
		
		
		
//		datas=new CommonDAO().executeQueryForMapList(configsql);
		
		
	}
	
	public Map lookUpConfigMap(String dynaControlId,String type,String parameter){
		if(datas==null||datas.isEmpty()||dynaControlId.equalsIgnoreCase("reload")){
			
//			datas=new CommonDAO().executeQueryForMapList(configsql);
			
			if(datas==null||datas.isEmpty())
				
				return null;
		}
		for(int i=0;i<datas.size();i++){	
			Map map=(Map)datas.get(i);
			String elementId=(String) map.get("element_id");
			String elementType=(String) map.get("element_type");
			if(dynaControlId.equals(elementId)&&type.equals(elementType)){
				String ctrlParam=(String) map.get("ctrl_param");
				if(ctrlParam==null||"".equals(ctrlParam))	
					return map;	
				if(parameter!=null&&!"".equals(parameter)){
					if(ctrlParam.indexOf(parameter)!=-1){
						return map;
					}else{
						return null;
					}
				}else{
					return null;
				}
			}
		}
		return  null;
		
	}
	
	public void setFieldTagDynaElement(FieldTag tag) throws JspException {

		if (tag.getDynaControlId() == null)

			return;

		/*String sql = "select * from do_page_element where element_id='"
				+ tag.getDynaControlId() + "' and element_type='FIELD'";

		if (tag.getParameter() != null && !"".equals(tag.getParameter())) {
			sql += "  and ctrl_param like'%" + tag.getParameter() + "%'";
		}

		List list = new CommonDAO().executeQueryForMapList(sql);

		if (list.isEmpty()) {
			return;
		} else if (list.size() > 1) {
			throw new JspException("页面动态元素标识" + tag.getDynaControlId()
					+ "在数据库配置中重复,请重新定义!");
		}*/
		Map map = lookUpConfigMap(tag.getDynaControlId(),"FIELD",tag.getParameter());

		if(map==null)return ;
		
		String actionType = (String) map.get("action_type");

		String htmlContent = (String) map.get("html_content");

		if (VISIBLE.equals(actionType)) {
			tag.setVisible(htmlContent);
		} else if (READONLY.equals(actionType)) {
			tag.setReadOnly(htmlContent);
		} else if (SUBLIST.equals(actionType)) {
			if (!"".equals(tag.getAttrCode())) {
				tag.setSubList(htmlContent);
			}
		} else if (CHINA_DIS.equals(actionType)) {
			tag.setLabel(htmlContent);
		} else if (REQUIRED.equals(actionType)) {
			tag.setRequired(htmlContent);
		} else if (POPUP_AND_READONLY.equals(actionType)) {
			tag.setPopup(htmlContent);
			tag.setReadOnly(htmlContent);
		}
		else if (CHINA_DIS_AND_DATE.equals(actionType)) {
			tag.setType("datetime");
			
			tag.setLabel(htmlContent);
		}
	}

	public void setButtonTagDynaElement(ButtonTag tag) throws JspException {

		if (tag.getDynaControlId() == null)

			return;
		/*String sql = "select * from do_page_element where element_id='"
				+ tag.getDynaControlId() + "' and element_type='BUTTON'";

		List list = new CommonDAO().executeQueryForMapList(sql);

		if (list.isEmpty()) {
			return;
		} else if (list.size() > 1) {
			throw new JspException("页面动态元素标识" + tag.getDynaControlId()
					+ "在数据库配置中重复,请重新定义!");
		}
		Map map = (Map) list.get(0);
*/
		Map map = lookUpConfigMap(tag.getDynaControlId(),"BUTTON",null);

		if(map==null)return ;
		String actionType = (String) map.get("action_type");
		String htmlContent = (String) map.get("html_content");
		if (VISIBLE.equals(actionType)) {
			tag.setDisplay(" display:none");
		} else if (READONLY.equals(actionType)) {
			tag.setDisabled("true");
		}else if (CHINA_DIS.equals(actionType)) {
			
			tag.setContent(htmlContent);
		}
	}

	public void setTabpageTagDynaElement(TabpageTag tag) throws JspException {
		if (tag.getDynaControlId() == null)

			return;
		/*String sql = "select * from do_page_element where element_id='"
				+ tag.getDynaControlId() + "' and element_type='TABPAGE'";

		List list = new CommonDAO().executeQueryForMapList(sql);

		if (list.isEmpty()) {
			return;
		} else if (list.size() > 1) {
			throw new JspException("页面动态元素标识" + tag.getDynaControlId()
					+ "在数据库配置中重复,请重新定义!");
		}
		Map map = (Map) list.get(0);*/
		Map map = lookUpConfigMap(tag.getDynaControlId(),"TABPAGE",null);

		if(map==null)return ;
		String htmlContent = (String) map.get("html_content");
		String actionType = (String) map.get("action_type");
		if (VISIBLE.equals(actionType)) {
			tag.setVisible(htmlContent);
		}
	}
	
	public void setDatasetTagDynaElement(DatasetTag tag) throws JspException {
		if (tag.getDynaControlId() == null)

			return;

		Map map = lookUpConfigMap(tag.getDynaControlId(),"DATASET",null);

		if(map==null)return ;
		String htmlContent = (String) map.get("html_content");
		String actionType = (String) map.get("action_type");
		DatasetVo datasetVo=tag.getDataset();
		List fields=datasetVo.getFields();
		if (VISIBLE.equals(actionType)) {		
			
			for(int i=0;i<fields.size();i++){
				FieldVo fieldVo=(FieldVo)fields.get(i);
				if(htmlContent!=null&&!"".equals(htmlContent)){
					if(htmlContent.indexOf(fieldVo.getField())!=-1){		
						fieldVo.setVisible("false");						
					}
				}
			}
			
		}else if(READONLY.equals(actionType)){
			for(int i=0;i<fields.size();i++){
				FieldVo fieldVo=(FieldVo)fields.get(i);
				if(htmlContent!=null&&!"".equals(htmlContent)){
					if(htmlContent.indexOf(fieldVo.getField())!=-1){		
						fieldVo.setReadOnly("true");						
					}
				}
			}
		}
	}

	public void setTabPanelTagDynaElement(PanelTag tag) throws JspException {

		if (tag.getDynaControlId() == null)

			return;
		/*String sql = "select * from do_page_element where element_id='"
				+ tag.getDynaControlId() + "' and element_type='PANEL'";

		List list = new CommonDAO().executeQueryForMapList(sql);

		if (list.isEmpty()) {
			return;
		} else if (list.size() > 1) {
			throw new JspException("页面动态元素标识" + tag.getDynaControlId()
					+ "在数据库配置中重复,请重新定义!");
		}
		Map map = (Map) list.get(0);
*/
		Map map = lookUpConfigMap(tag.getDynaControlId(),"PANEL",null);
		if(map==null)return ;
		String actionType = (String) map.get("action_type");
		String htmlContent = (String) map.get("html_content");
		if (CHINA_DIS.equals(actionType)) {
			tag.setDesc(htmlContent);
		}
	}
	
//	public Class getInterceptClass(String intercId,ArrayList parameters){
//		
//		String intercKey=intercId;
//		//通过参数去获取拦截器类
//		if(parameters==null||parameters.isEmpty()){
//			return null;
//		}
//		Iterator it=parameters.iterator();
//		Map paramsMap=new HashMap();
//		while(it.hasNext()){
//			ParamVo paramVo=(ParamVo)it.next();
//			paramsMap.put(paramVo.getName(),paramVo.getValue());
//			intercKey+=paramVo.getName()+paramVo.getValue();
//		}
//		
//		String className=(String) intercs.get(intercKey);
//		if(className==null){//如果为空则到数据库中获取
//			
//			String serviceId=(String) paramsMap.get("serviceId");
//			if(serviceId==null)
//				serviceId="-1";
//			String offerId=(String) paramsMap.get("offerId");
//			if(offerId==null)
//				offerId="-1";
//			String productId=(String) paramsMap.get("productId");
//			if(productId==null)
//				productId="-1";
//			String actionId=getActionId(serviceId);
//			if("".equals(actionId))
//				actionId="-1";
//			
//			List list = new CommonDAO().query(sqlA(serviceId,offerId,productId,"AftDataSetParse",actionId,"A","0"));
//			
//			if(list==null||list.isEmpty())
//				return null;
//			
//			 className=list.get(0).toString();
//			 
//			 intercs.put(intercKey,className);
//		}
//		
//		try {
//			return Clazz.forName(className);
//		} catch (ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		}
//		
//		return null;
//		
//	}
//	private String getActionId(String serviceId) {
//		
//		return new CommonDAO().querySingleValue("select action_id from service_offer where service_offer_id=" + serviceId);
//
//	}

	private String sqlA(String serviceId, String offerId, String productId, String intercTime, String actionId,String offerKind,String billingModeId) {
		StringBuffer buffer = new StringBuffer(" select interc_class from service_offer_interc s,interc i where i.interc_id=s.interc_id and (service_id=" + serviceId + " or service_id=-1)");
		buffer.append(" and (offer_id=" + offerId + " or offer_id=-1)");
		buffer.append(" and (product_id=" + productId + " or product_id=-1)");
		buffer.append(" and (interc_time='" + intercTime + "' or interc_time='-1')");
		buffer.append(" and (action_id=" + actionId + " or action_id=-1)");
		buffer.append(" and (offer_kind='" + offerKind + "' or offer_kind='A')");
		buffer.append(" and (billing_mode_id='" + billingModeId + "' or billing_mode_id = '0')");
		buffer.append(" and state_flag='00A'");
		buffer.append(" order by priority desc");
		return buffer.toString();
	}

}
