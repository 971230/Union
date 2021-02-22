package zte.net.ecsord.params.ecaop.req.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenAcctInfo {

	@ZteSoftCommentAnnotationParam(name = "是否继承账户标示 0：新建账户 1：继承老账户", type = "String", isNecessary = "Y", desc = "")
	private String createOrExtendsAcct;
	@ZteSoftCommentAnnotationParam(name = "账户付费方式，默认：10参考附录证件类型说明", type = "String", isNecessary = "Y", desc = "")
	private String accountPayType;
	@ZteSoftCommentAnnotationParam(name = "合帐帐户ID（副卡需要）", type = "String", isNecessary = "Y", desc = "")
	private String debutySn;

	public String getCreateOrExtendsAcct() {
		return createOrExtendsAcct;
	}

	public void setCreateOrExtendsAcct(String createOrExtendsAcct) {
		this.createOrExtendsAcct = createOrExtendsAcct;
	}

	public String getAccountPayType() {
		return accountPayType;
	}

	public void setAccountPayType(String accountPayType) {
		this.accountPayType = accountPayType;
	}

	public String getDebutySn() {
		return debutySn;
	}

	public void setDebutySn(String debutySn) {
		this.debutySn = debutySn;
	}

}
