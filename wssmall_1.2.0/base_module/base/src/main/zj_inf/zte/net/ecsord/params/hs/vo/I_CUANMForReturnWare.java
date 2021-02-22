package zte.net.ecsord.params.hs.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class I_CUANMForReturnWare implements Serializable {
	/**
	 * 三级节点(串号)
	 */
	private static final long serialVersionUID = 4671541973609779984L;

	@ZteSoftCommentAnnotationParam(name="串码",type="String",isNecessary="N",desc="串码")
	private String CUANM;

	public String getCUANM() {
		return CUANM;
	}

	public void setCUANM(String cUANM) {
		CUANM = cUANM;
	}
}
