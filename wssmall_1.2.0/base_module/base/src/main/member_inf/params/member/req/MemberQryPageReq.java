package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * 用户查询请求参数
 * @author hu.yi
 * @date 2014.03.17
 */
public class MemberQryPageReq extends ZteRequest {
	
	public enum QRY_CON_TYPE {ID, UNAME, EMAIL};
	@ZteSoftCommentAnnotationParam(name="购买类型",type="String",isNecessary="Y",desc="购买类型:MEMBER_BUY_PRODUCT产品，MEMBER_BUY_GOODS商品")
	private QRY_CON_TYPE qry_con_type = QRY_CON_TYPE.UNAME;
	public QRY_CON_TYPE getQry_con_type() {
		return qry_con_type;
	}

	public void setQry_con_type(QRY_CON_TYPE qry_con_type) {
		this.qry_con_type = qry_con_type;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	@ZteSoftCommentAnnotationParam(name="用户账号",type="String",isNecessary="N",desc="用户账号")
	private String uname;// 用户账号	
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="N",desc="会员ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="会员Email",type="String",isNecessary="N",desc="会员Email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="pageNo：第几页")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="Y",desc="pageSize：每页记录数")
	private int pageSize=10;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "zte.memberService.member.list";
	}
}