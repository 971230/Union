package com.ztesoft.parameters.accept.bean;

import com.ztesoft.parameters.ListType;

import javax.xml.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ordItems")
public class OrdItems {
	
	@ListType(name="com.ztesoft.parameters.accept.bean.OrdItemBean")
	@XmlElementWrapper(name = "itemLists")
    @XmlElement(name = "itemList")
	private ArrayList items;

	public ArrayList getItems() {
		return items;
	}

	public void setItems(ArrayList items) {
		this.items = items;
	}
	
	public static void main(String[] args) {
		try {
			Field fields = OrdItems.class.getDeclaredField("items");
			ListType type = fields.getAnnotation(ListType.class);
			//logger.info(type.name());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
