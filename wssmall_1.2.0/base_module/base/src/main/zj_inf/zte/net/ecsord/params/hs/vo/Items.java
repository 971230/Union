package zte.net.ecsord.params.hs.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Items implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232039351530990864L;
	@ZteSoftCommentAnnotationParam(name="明细表",type="String",isNecessary="N",desc="明细表")
	private List<Item> Item;

	public List<Item> getItem() {
		return Item;
	}

	public void setItem(List<Item> item) {
		Item = item;
	}
	
}
