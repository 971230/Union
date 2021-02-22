package zte.params.template.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.template.model.NodeModel;
import com.ztesoft.net.template.model.OrderTemplate;

/**
 * 模板查询出参
 * @author xuefeng
 */
public class TemplateQryResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="模板结构",type="Map",isNecessary="Y",desc="模板结构")
	private Map<String, NodeModel> tplMap;
	
	@ZteSoftCommentAnnotationParam(name="模板编码",type="String",isNecessary="Y",desc="模板编码")
	private String template_code;
	
	@ZteSoftCommentAnnotationParam(name="模板基本信息",type="OrderTemplate",isNecessary="Y",desc="模板基本信息")
	private OrderTemplate tplBase;
	
	/**
	 * Map结构:<类名称<字段名称,字段值>>
	 */
	@ZteSoftCommentAnnotationParam(name="参数map",type="Map<String, String>",isNecessary="Y",desc="内部常量map")
	private Map<String,Map<String,Object>> paramMap;
	
	public Map<String, NodeModel> getTplMap() {
		return tplMap;
	}

	public void setTplMap(Map<String, NodeModel> tplMap) {
		this.tplMap = tplMap;
	}

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public OrderTemplate getTplBase() {
		return tplBase;
	}

	public void setTplBase(OrderTemplate tplBase) {
		this.tplBase = tplBase;
	}

	public Map<String, Map<String, Object>> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Map<String, Object>> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	
}
