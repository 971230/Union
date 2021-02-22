package com.ztesoft.net.mall.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import services.AdminUserInf;
import services.GoodsInf;
import services.GoodsTypeInf;
import services.ProductInf;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 订单工具
 *
 * @author wui
 */
public class OrderUtils extends BaseSupport implements IOrderUtils {
    private IOrderManager orderManager;

    @Resource
    private GoodsTypeInf goodsTypeServ;

    @Resource
    private GoodsInf goodsServ;

    @Resource
    public ProductInf proudctServ;

    @Resource
    private AdminUserInf adminUserServ;
    private CacheUtil cacheUtil;

    private IGoodsCacheUtil goodsCacheUtil;

    private void init(){
    	if(null == goodsCacheUtil) goodsCacheUtil = ApiContextHolder.getBean("goodsCacheUtil");
    }
    
    // 根据订单展示类型
    @Override
	@SuppressWarnings("unchecked")
    public String getTypeCodeByOrderId(String orderId) {
        List itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
        String goods_id = (String) ((Map) itemList.get(0)).get("goods_id");
        GoodsTypeDTO goodsType = goodsTypeServ.get(goodsServ.getGoods(goods_id).getType_id());
        String type_code = goodsType.getType_code();
        return type_code;
    }

    @Override
	public Goods getGoodsByOrderId(String orderId) {
        List itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
        String goods_id = (String) ((Map) itemList.get(0)).get("goods_id");
        Goods goods = goodsServ.getGoods(goods_id);
        return goods;
    }


    //获取市场价格
    @Override
	public Double getMarketPrice(Order order) {
        //根据金额获取市场价格
        Goods goods = this.getGoodsId(order.getGoods_id());
        Double marketPrice = goods.getMktprice();
        return marketPrice;
    }

    @Override
	public String getGoodsUsageState(String goodsId, String userid) {
        GoodsUsage goodsUsage = (GoodsUsage) this.baseDaoSupport.queryForObject("select * from es_goods_usage a where a.goods_id = ? and a.userid = ? ", GoodsUsage.class, goodsId, userid);
        if (goodsUsage == null)
            return "";
        return goodsUsage.getState();
    }

    // 根据订单展示类型
    @Override
	public GoodsType qryGoodsTypeByGoodsId(String goods_id) {
    	//初始化beans
    	init();
    	
        GoodsType goodsType = goodsCacheUtil.getGoodsTypeById(goodsServ.getGoods(goods_id).getType_id());
        return goodsType;
    }


    @Override
	public Goods getGoodsId(String goods_id) {
        Goods goods = goodsServ.getGoods(goods_id);
        return goods;
    }

    @Override
	public Goods getGoodsBySqlId(String goods_id) {
        return (Goods) this.baseDaoSupport.queryForObject("select * from es_goods  a where a.goods_id = ? ", Goods.class, goods_id);
    }


    @Override
	public Product getProductByGoodsId(String goods_id) {
        Product product = this.proudctServ.getByGoodsId(goods_id);
        return product;
    }

    public IOrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
	public void insertGoodsUsage(GoodsUsage goodsUsage) {
        goodsUsage.setCreate_date(DBTUtil.current());
        goodsUsage.setUsagetype(ManagerUtils.getAdminUser().getFounder() + "");
        goodsUsage.setUsageid(DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14));

        // 插入前验证
        List usages = searchGoodsUsageByUserGoodsId(goodsUsage.getUserid(), goodsUsage.getGoods_id());
        if (ListUtil.isEmpty(usages))
            this.baseDaoSupport.insert("GOODS_USAGE", goodsUsage);

    }


    @Override
	public List searchGoodsUsageByUserGoodsId(String user_id, String goods_id) {
        return this.baseDaoSupport
                .queryForList(
                        "select * from ES_GOODS_USAGE where goods_id = ? and userid = ? ",
                        goods_id, user_id);
    }

    /**
     * 判断充值订单计费是否充值成功
     */
    @Override
	public List queryRechargeSuccForBill(String orderseq) {
        return this.baseDaoSupport.queryForList("select * from es_order a,es_order_rel b  where a.order_id = b.a_order_id and b.z_order_id = ? and a.accept_status = ? ", orderseq, OrderStatus.ACCEPT_STATUS_3);
    }


    /**
     * 判断是否生成订单
     */
    @Override
	public List queryOrderByTransaction_id(String orderseq) {
        return this.baseDaoSupport.queryForList("select * from es_order a where a.transaction_id  =?  ", orderseq);
    }


//	/**
//	 * 
//	 */
//	public List queryRechargeSuccForBill(String orderseq) {
//		return this.baseDaoSupport.queryForList("select * from es_order a,es_order_rel b  where a.order_id = b.a_order_id and b.z_order_id = ? and a.accept_status = ? ",orderseq,OrderStatus.ACCEPT_STATUS_3);
//	}


    @Override
	public Order getOrderByTrnsId(String transaction_id) {
        String querySql = "select * from es_order where transaction_id = ? ";
        return (Order) this.baseDaoSupport.queryForObject(querySql, Order.class, transaction_id);

    }


    //根据本地网，工号获取staff_id
    @Override
	public Map getStaffByStaffCodeAndLanId(String staff_code, String lan_id) {
        Map map = new HashMap();
        if (ManagerUtils.isFirstPartner()) {
            staff_code = ManagerUtils.getAdminUser().getRelcode();
        } else if (ManagerUtils.isSecondPartner()) {
            String parent_userid = ManagerUtils.getAdminUser().getParuserid();
            AdminUser pAdminUser = this.getAdminUserById(parent_userid);
            staff_code = pAdminUser.getRelcode();// 获取父级工号
        } else {
            staff_code = ManagerUtils.getAdminUser().getUsername();
        }
        String sql = "select a.staff_id,a.org_id from es_staff a,es_organization b  where a.STAFF_CODE  = ? " +
                " and a.ORG_ID = b.PARTY_ID and b.LAN_ID = ?  "+DBTUtil.andRownum("2");
        List<Map> list = this.baseDaoSupport.queryForList(sql, staff_code, lan_id);
        if (list != null && !list.isEmpty()) {
            map = list.get(0);
        }
        return map;
    }


    @Override
	public OrderItem getOrderItemByOrderId(String orderId) {
        return (OrderItem) this.baseDaoSupport.queryForObject("select * from es_order_items where order_id = ? "+DBTUtil.andRownum("2"), OrderItem.class, orderId);
    }

    //更新号码区间状态
    @Override
	public void updateAccIntervalState(String order_id, String col1_state) {
        this.baseDaoSupport.execute("update ES_CARD set col1 = ?  where ORDER_ID = ? and FIELD_ENAME in('begin_nbr','end_nbr') and col1 is null ", col1_state, order_id);
    }


    @Override
	public String getAdminUserByUserId(String userid) {
        String state = this.baseDaoSupport.queryForString("select state from es_adminuser where userid = '" + userid + "'");
        if (StringUtil.isEmpty(state))
            return "";
        else {
            if ("0".equals(state))
                return "invalid";
        }
        return "1";
    }


    @Override
	public Map getOrgByStaffIdAndLanid(String staff_code, String lan_id) {
        String querySql = "select org.* from es_staff s, es_organization org  where s.staff_code = ? and org.lan_id = ?  and s.org_id = org.party_id";
        return this.baseDaoSupport.queryForMap(querySql, staff_code, lan_id);
    }


    @Override
	public AdminUser getAdminUserById(String userid) {
        AdminUserReq adminUserReq = new AdminUserReq();
        adminUserReq.setUser_id(userid);
        AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    AdminUser adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		return adminUser;
    }


    public CacheUtil getCacheUtil() {
        return cacheUtil;
    }

    public void setCacheUtil(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    public static List<GoodsRelAttConvert> converSetContractFormString(String props) {
        if (StringUtils.isBlank(props)){
            return new ArrayList();
        }
        List<GoodsRelAttConvert> lists = JSON.parseArray(props,GoodsRelAttConvert.class) ;
        return lists;
    }


}
