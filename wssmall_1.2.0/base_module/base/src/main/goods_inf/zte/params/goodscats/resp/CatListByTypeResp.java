package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.mall.core.model.Cat;

import params.ZteResponse;

public class CatListByTypeResp extends ZteResponse {

	private List<Cat> catList;

	public List<Cat> getCatList() {
		return catList;
	}

	public void setCatList(List<Cat> catList) {
		this.catList = catList;
	}
}
