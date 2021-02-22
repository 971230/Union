package params.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class ZbOrderAuditDetailResp extends ZteResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8637793135310247422L;
	@ZteSoftCommentAnnotationParam(name="审核图片和视频地址",type="String",isNecessary="Y",desc="审核图片和视频地址")
	private List filePath;
	public List getFilePath() {
		return filePath;
	}
	public void setFilePath(List filePath) {
		this.filePath = filePath;
	}
	
}
