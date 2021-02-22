package services;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.CouponsSearch;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 15:14
 * 优惠券
 */
public interface CouponInf {
    /**
     * 修改使用次数
     * @作者 MoChunrun
     * @创建日期 2013-9-3
     * @param member_id
     * @param cpns_id
     * @param addUseTimes
     */
    public void updateUseTimes(String member_id,String cpns_code,int addUseTimes);
    /**
     * 是否还有使用次数
     * @作者 MoChunrun
     * @创建日期 2013-9-3
     * @param coupon_id
     * @param memberr_id
     * @return
     */
    public boolean hascouponUseTimes(String coupon_id,String memberr_id);
    
    public void add(Coupons coupons);
    
    public Coupons getCoupon(String couponid);
    
    public void updateCouponName(Coupons coupons);
    
    public void edit(Coupons coupons);
    
    public void delete(String [] id,String[] pmtIds);
    
    public Page list(CouponsSearch couponsSearch, int page,int pageSize,String order);
    
    public List listCanExchange();
    
    public void saveExchange(String cpnsid,Integer point);
    
    public void deleteExchange(String [] id);
    
    public Page listExchange(int page,int pageSize);
}
