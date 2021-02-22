package zte.params.template.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
/**
 * 模板转出 出参
 * @author xuefeng
 */
public class TemplateOuterConvertResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="报文结构",type="Map",isNecessary="Y",desc="模板结构")
	private Map<String, Object> infMap;

	public Map<String, Object> getInfMap() {
		return infMap;
	}

	public void setInfMap(Map<String, Object> infMap) {
		this.infMap = infMap;
	}
}
