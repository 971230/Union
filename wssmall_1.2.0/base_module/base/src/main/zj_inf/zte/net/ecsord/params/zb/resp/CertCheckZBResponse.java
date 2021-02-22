package zte.net.ecsord.params.zb.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.zb.vo.orderattr.PhotoInfoVO;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CertCheckZBResponse extends ZteBusiResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6391633591755566268L;
	@ZteSoftCommentAnnotationParam(name = "应答编码", type = "String", isNecessary = "Y", desc = "RespCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "证件照片信息", type = "String", isNecessary = "Y", desc = "PhotoInfo：证件照片信息")
	private PhotoInfoVO PhotoInfo;
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public PhotoInfoVO getPhotoInfo() {
		return PhotoInfo;
	}
	public void setPhotoInfo(PhotoInfoVO photoInfo) {
		PhotoInfo = photoInfo;
	}
	
}
