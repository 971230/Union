package zte.net.ecsord.params.ecaop.resp.vo;
import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.Para;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CustInfoCreateRspVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "code")
    private String code;
    @ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail")
    private String detail;
	@ZteSoftCommentAnnotationParam(name = "客户标识", type = "String", isNecessary = "Y", desc = "custId：客户标识")
	private RespInfoVo respInfo;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="List",isNecessary="N",desc="para：保留字段")
	private List<Para> para;

	public RespInfoVo getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(RespInfoVo respInfo) {
        this.respInfo = respInfo;
    }

    public List<Para> getPara() {
		return para;
	}

	public void setPara(List<Para> para) {
		this.para = para;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
	

}
