package com.ztesoft.mall.core.action.backend;


import java.util.List;

import com.ztesoft.net.mall.core.service.IProductoManager;

public class ProductoAction extends BaseAction {
	
	private IProductoManager productoM;
	List productos ;

	public String list(){
		webpage = productoM.getFormList(getPage(),getPageSize(),params);
		if(webpage.getResult().size()<1){
			return "null";
		}
		return "organizacion";
	}
	
	public String liberacion(){
		try {
			productoM.liberacion(ids, params);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{'result':1,'message':'操作失败'}";
		}
		return JSON_MESSAGE;
	
	}
	


	public IProductoManager getProductoM() {
		return productoM;
	}

	public void setProductoM(IProductoManager productoM) {
		this.productoM = productoM;
	}

	public List getProductos() {
		return productos;
	}

	public void setProductos(List productos) {
		this.productos = productos;
	}
	
	
}