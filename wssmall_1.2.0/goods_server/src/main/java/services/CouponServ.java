package services;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.CouponsSearch;
import com.ztesoft.net.mall.core.service.ICouponManager;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 15:15
 * To change this template use File | Settings | File Templates.
 */
public class CouponServ implements CouponInf {
    @Resource
    private ICouponManager couponManager;
    @Override
    public void updateUseTimes(String member_id, String cpns_code, int addUseTimes) {
      this.couponManager.updateUseTimes(member_id,cpns_code,addUseTimes);
    }

    @Override
    public boolean hascouponUseTimes(String coupon_id, String memberr_id) {
        return this.couponManager.hascouponUseTimes(coupon_id,memberr_id);
    }
    
    @Override
	public void add(Coupons coupons){
    	couponManager.add(coupons);
    }
    
    @Override
	public Coupons getCoupon(String couponid){
    	return couponManager.get(couponid);
    }
    
    @Override
	public void updateCouponName(Coupons coupons){
    	couponManager.updateCouponName(coupons);
    }
    
    @Override
	public void delete(String [] id,String[] pmtIds){
    	couponManager.delete(id, pmtIds);
    }
    
    @Override
	public Page list(CouponsSearch couponsSearch,int page,int pageSize,String order){
    	return couponManager.list(couponsSearch,page, pageSize, order);
    }
    @Override
	public List listCanExchange(){
    	return couponManager.listCanExchange();
    }
    
    @Override
	public void saveExchange(String cpnsid,Integer point){
    	couponManager.saveExchange(cpnsid, point);
    }
    
    @Override
	public void deleteExchange(String [] id){
    	couponManager.deleteExchange(id);
    }
    @Override
	public Page listExchange(int page,int pageSize){
    	return couponManager.listExchange(page, pageSize);
    }
    
    @Override
	public void edit(Coupons coupons){
    	couponManager.edit(coupons);
    }
}
