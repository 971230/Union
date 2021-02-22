package params.orgAdmin.resp;

import java.util.List;

import params.ZteResponse;

public class OrgAdminResp extends ZteResponse {
	private List RootOrgList;
	private List CurrOrgList;
	private List ChildrenOrgList;
	private int ChildrenOrgCount;
	private int delOrgNum;
	private boolean ifExistsOrg;
	
	public List getRootOrgList() {
		return RootOrgList;
	}
	public void setRootOrgList(List rootOrgList) {
		RootOrgList = rootOrgList;
	}
	public List getCurrOrgList() {
		return CurrOrgList;
	}
	public void setCurrOrgList(List currOrgList) {
		CurrOrgList = currOrgList;
	}
	public List getChildrenOrgList() {
		return ChildrenOrgList;
	}
	public void setChildrenOrgList(List childrenOrgList) {
		ChildrenOrgList = childrenOrgList;
	}
	public int getChildrenOrgCount() {
		return ChildrenOrgCount;
	}
	public void setChildrenOrgCount(int childrenOrgCount) {
		ChildrenOrgCount = childrenOrgCount;
	}
	public int getDelOrgNum() {
		return delOrgNum;
	}
	public void setDelOrgNum(int delOrgNum) {
		this.delOrgNum = delOrgNum;
	}
	public boolean isIfExistsOrg() {
		return ifExistsOrg;
	}
	public void setIfExistsOrg(boolean ifExistsOrg) {
		this.ifExistsOrg = ifExistsOrg;
	}
}
