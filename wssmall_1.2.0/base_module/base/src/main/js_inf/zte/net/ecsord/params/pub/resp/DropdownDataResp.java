package zte.net.ecsord.params.pub.resp;

import java.util.List;

import params.ZteResponse;

public class DropdownDataResp extends ZteResponse {

	private List dropDownList;

	public List getDropDownList() {
		return dropDownList;
	}

	public void setDropDownList(List dropDownList) {
		this.dropDownList = dropDownList;
	}

}
