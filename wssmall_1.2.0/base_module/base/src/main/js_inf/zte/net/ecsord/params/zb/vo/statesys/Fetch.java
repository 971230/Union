package zte.net.ecsord.params.zb.vo.statesys;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Fetch implements Serializable {
	
	private static final long serialVersionUID = 7035019928170021677L;

	@ZteSoftCommentAnnotationParam(name = "归档单据数量", type = "String", isNecessary = "Y", desc = "FetchNum：归档单据数量")
	private String FetchNum;	
	
	@ZteSoftCommentAnnotationParam(name = "归档单据类型", type = "String", isNecessary = "Y", desc = "FetchFile：归档单据类型")
	private String FetchFile;

	public String getFetchNum() {
		return FetchNum;
	}

	public void setFetchNum(String fetchNum) {
		FetchNum = fetchNum;
	}

	public String getFetchFile() {
		return FetchFile;
	}

	public void setFetchFile(String fetchFile) {
		FetchFile = fetchFile;
	}	
}
