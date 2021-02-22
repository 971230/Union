package zte.net.ecsord.params.ecaop.resp.vo;
import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.Para;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RespInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "code")
    private String custId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
