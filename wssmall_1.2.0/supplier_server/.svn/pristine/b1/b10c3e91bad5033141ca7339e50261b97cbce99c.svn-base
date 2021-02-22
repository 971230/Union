package com.ztesoft.net.service;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.mall.core.service.ISupplierManager;

import services.ServiceBase;
import zte.net.iservice.ISupplierService;
import zte.params.supplier.req.SupplierGetReq;
import zte.params.supplier.req.SupplierListReq;
import zte.params.supplier.resp.SupplierGetResp;
import zte.params.supplier.resp.SupplierListResp;

public class SupplierService extends ServiceBase implements ISupplierService {

	private ISupplierManager supplierManager;
	
	public ISupplierManager getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(ISupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商家销售排行榜", summary = "获取商家商品销售量的排行")
	public SupplierListResp listSupplier(SupplierListReq req) {
		List list = supplierManager.listSupplierSalesRank();
		SupplierListResp resp = new SupplierListResp();
		resp.setSupplierList(list);
		if(list!=null && list.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商家信息", summary = "获取商家信息")
	public SupplierGetResp getSupplier(SupplierGetReq req) {
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("company_name", req.getCompany_name());
		List suppliers = this.supplierManager.getSupplierByCond(params);
		Supplier supplier = null;
		if(suppliers!=null && suppliers.size()>0){
			supplier = (Supplier) suppliers.get(0);
		}
		SupplierGetResp resp = new SupplierGetResp();
		if(supplier!=null){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		resp.setSupplier(supplier);
		addReturn(req,resp);
		return resp;
	}

}
