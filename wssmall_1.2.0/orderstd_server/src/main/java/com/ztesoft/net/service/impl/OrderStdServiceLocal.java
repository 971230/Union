package com.ztesoft.net.service.impl;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.service.IOrderMangerLocal;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import services.ServiceBase;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

@ServiceMethodBean(version="1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "订单服务", summary = "订单服务")
@Transactional(propagation = Propagation.REQUIRED)
public class OrderStdServiceLocal extends ServiceBase implements IOrderServiceLocal {
	private IOrderMangerLocal oml;
	
	@Override
	public GoodsGetResp getGoods(GoodsGetReq req) {
		String package_id = req.getPackage_id();
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		Map<String,String> data = this.oml.getGoodsInfo(package_id);
		
		GoodsGetResp resp = new GoodsGetResp();
		if(!data.isEmpty()){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setData(data);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public String getCityname(String cityId){
		String result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			result = oml.getCityname(cityId);
			
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	@Override
	public String getProviceId(String cityId) {
		String result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			// 支持全国 省份编码
			result = oml.getProviceId(cityId);

		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String getProvicename(String city){
		String result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			// 支持全国省份编码
			result = oml.getProvicename(city);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	@Override
	public String queryString(String sql){
		String result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			result = oml.queryString(sql);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	@Override
	public Map queryMap(String sql,Object... args){
		Map result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			result = oml.queryForMap(sql, args);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}

	@Override
	public boolean updateHSMatnrByGoodsId(String goods_id, String matnr) {
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		return oml.updateHSMatnrByGoodsId(goods_id, matnr);
	}
	
	/**
	 * 根据物料号获取商品
	 */
	@Override
	public GoodsGetResp getHSGoodsByMatnr(GoodsGetReq req) {
		String matnr = req.getMatnr();
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		Map<String,String> data = this.oml.getHSGoodsByMatnr(matnr);
		
		GoodsGetResp resp = new GoodsGetResp();
		if(!data.isEmpty()){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setData(data);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public String getCountryName(String ship_country) {
		String result = null;
		oml = SpringContextHolder.getBean("orderStdMangerLocal");
		try {
			// 支持全国县分编码
			result = oml.getCountyName(ship_country);
		} catch (FrameException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result ;
	}
}
