package com.ztesoft.net.cms.plugin.params;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;

import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamsFieldPlugin extends AbstractFieldPlugin {

	@Override
	public int getHaveSelectValue() {
	 
		return 0;
	}
	@Override
	public String getDataType() {
		 
		return "text";
	}
	@Override
	public String onDisplay(DataField field, Object value ) {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		if(value!=null){
			List<Param> paramlist = (List)JSONArray.toCollection(JSONArray.fromObject(value), Param.class) ;
			freeMarkerPaser.putData("paramlist",paramlist);
		}
		return freeMarkerPaser.proessPageContent();
		 
	}
	
	@Override
	public void onSave(Map article, DataField field) {
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String[] names = request.getParameterValues("param_name");
		String[] values = request.getParameterValues("param_value");
		List<Param> paramList = new ArrayList<Param>();
		
		if(names!=null){
			for(int i=0;i<names.length;i++){
				if(i==0) continue;
				Param param = new Param();
				param.setName(names[i]);
				param.setValue(values[i]);
				paramList.add(param);
			}
		}
		
		article.put("params", JSONArray.fromObject(paramList));
	}

	
	@Override
	public Object onShow(DataField field, Object value) {
		if(value!=null){
			List<Param> paramlist = (List)JSONArray.toCollection(JSONArray.fromObject(value), Param.class) ;
			return paramlist;
		}
		return  new ArrayList();
	}
	
	@Override
	public String getAuthor() {
	 
		return "kingapex";
	}

	@Override
	public String getId() {
		return "paramsField";
	}

	@Override
	public String getName() {
		return "自定义参数";
	}

	@Override
	public String getType() {
		return "field";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
