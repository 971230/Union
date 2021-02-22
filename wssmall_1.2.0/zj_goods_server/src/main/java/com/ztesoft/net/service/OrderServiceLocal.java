package com.ztesoft.net.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.ServiceBase;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "订单服务", summary = "订单服务")
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceLocal extends ServiceBase implements IOrderServiceLocal {
	private IOrderMangerLocal oml;
	
	@Override
	public GoodsGetResp getGoods(GoodsGetReq req) {
		String package_id = req.getPackage_id();
		oml = SpringContextHolder.getBean("orderMangerLocal");
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
		oml = SpringContextHolder.getBean("orderMangerLocal");
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
	public String getProviceId(String CountryId){
		String result = null;
		oml = SpringContextHolder.getBean("orderMangerLocal");
		try {
			result = oml.getProviceId(CountryId);
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
	public String getProvicename(String CountryId){
		String result = null;
		oml = SpringContextHolder.getBean("orderMangerLocal");
		try {
			result = oml.getProvicename(CountryId);
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
		oml = SpringContextHolder.getBean("orderMangerLocal");
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
		oml = SpringContextHolder.getBean("orderMangerLocal");
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
}
