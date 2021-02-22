/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.AcctInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.CustInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.UserInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-06-25
 * @see 用户资料校验三户返回
 *
 */
public class UserInfoCheck3BackResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "code")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail")
	private String detail;	
	@ZteSoftCommentAnnotationParam(name = "数据来源系统：1：ESS 2：CBSS", type = "String", isNecessary = "N", desc = "sysType")
	private String sysType;
	@ZteSoftCommentAnnotationParam(name = "USIM卡号", type = "String", isNecessary = "N", desc = "simCard")
	private String simCard;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "CustInfoRspVo", isNecessary = "N", desc = "custInfo")
	private CustInfoRspVo custInfo;
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "UserInfoRspVo", isNecessary = "N", desc = "userInfo")
	private UserInfoRspVo userInfo;
	@ZteSoftCommentAnnotationParam(name = "失效时间", type = "String", isNecessary = "Y", desc = "endDate")
	private String endDate;
	@ZteSoftCommentAnnotationParam(name = "账户信息", type = "AcctInfoRspVo", isNecessary = "Y", desc = "acctInfo")
	private AcctInfoRspVo acctInfo;
	@ZteSoftCommentAnnotationParam(name = "保留信息", type = "ParaVo", isNecessary = "Y", desc = "para")
	private List<ParaVo> para;
	
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
	public String getSysType() {
		return sysType;
	}
	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	public String getSimCard() {
		return simCard;
	}
	public void setSimCard(String simCard) {
		this.simCard = simCard;
	}
	public CustInfoRspVo getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(CustInfoRspVo custInfo) {
		this.custInfo = custInfo;
	}
	
	public UserInfoRspVo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfoRspVo userInfo) {
		this.userInfo = userInfo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public AcctInfoRspVo getAcctInfo() {
		return acctInfo;
	}
	public void setAcctInfo(AcctInfoRspVo acctInfo) {
		this.acctInfo = acctInfo;
	}
	public List<ParaVo> getPara() {
		return para;
	}
	public void setPara(List<ParaVo> para) {
		this.para = para;
	}
}
