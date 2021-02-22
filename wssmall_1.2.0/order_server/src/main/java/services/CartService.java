package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import params.cart.req.CartAddReq;
import params.cart.req.CartAddReq.Adjunct;
import params.cart.req.CartCleanReq;
import params.cart.req.CartDelReq;
import params.cart.req.CartListReq;
import params.cart.req.CartTotalReq;
import params.cart.req.CartUpdateReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartCleanResp;
import params.cart.resp.CartListResp;
import params.cart.resp.CartTotalResp;
import params.member.req.MemberByIdReq;
import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLvByIdReq;
import params.member.req.MemberLvPriceReq;
import params.member.req.MemberPriceByPIdReq;
import params.member.resp.MemberByIdResp;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLvPriceResp;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.AdjunctItem;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 购物车service
 *
 * @作者 MoChunrun
 * @创建日期 2013-9-23
 * @版本 V 1.0
 */
public class CartService extends ServiceBase implements ICartService {
    @Resource
    public ProductInf proudctServ;

    private ICartManager cartManager;

    private MemberLvInf memberLvServ;
    private MemberPriceInf memberPriceServ;
    private MemberInf memberServ;
    private MemberLVSpecInf memberLVSpecServ;

    @Resource
    private GoodsAdjunctInf goodsAdjunctServ;

    @Resource
    private PromotionInf promotionServ;
    @Resource
    private GoodsInf goodsServ;
    
    private void init(){
    	if(null == memberLvServ) memberLvServ = ApiContextHolder.getBean("memberLvServ");
    	if(null == memberPriceServ) memberPriceServ = ApiContextHolder.getBean("memberPriceServ");
    	if(null == memberServ) memberServ = ApiContextHolder.getBean("memberServ");
    	if(null == memberLVSpecServ) memberLVSpecServ = ApiContextHolder.getBean("memberLVSpecServ");
    }

    /**
     * 增加购物车商品
     * {"productId":"111111","num":"3","memberLvId":"1","adjuncts":[{"productId":"1","num":"2"},{"productId":"3","num":"2"}],"logined_id":"123123","session_id":"222222"}
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public CartAddResp add(CartAddReq cp) {
        try {
        	//初始化bean
        	init();
        	
            String result_code = "0";
            String result_msg = "成功";
            CartAddResp op = new CartAddResp();
            //CartAddReq cp = CommonTools.jsonToBean(json, CartAddReq.class);
            //检查数据====
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                cp.setUserSessionId(CommonTools.createSessionId());
            if (cp.getProduct_id() == null || "".equals(cp.getProduct_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "product_id不能为空"));
            if (cp.getMember_lv_id() == null || "".equals(cp.getMember_lv_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "member_lv_id不能为空"));
            if (cp.getNum() == null || "".equals(cp.getNum()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "num不能为空"));
            Member member = CommonTools.getLoginMember();
            if (member == null && cp.getMember_id() != null && !"".equals(cp.getMember_id())){
            	MemberByIdReq req = new MemberByIdReq();
            	req.setMember_id(cp.getMember_id());
            	
            	MemberByIdResp re = memberServ.getMemberById(req);
            	if(re != null){	
            		member = re.getMember();
            	}	
            	// member = memberManager.get(cp.getMember_id());
            }
            if (member != null && !CommonTools.checkMemberLvId(cp.getMember_lv_id(), member))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户等级ID[" + cp.getMember_lv_id() + "]有误"));
            if (member != null)
                ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            /*if (!memberLVSpecServ.memberLvCanBy(cp.getProduct_id(), cp.getMember_lv_id()))
                CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL, "会员ID[" + cp.getMember_lv_id() + "]不能购买商品[" + cp.getProduct_id() + "]"));*/
            //检查商品
            MemberLVCanBuyResp resp = new MemberLVCanBuyResp();
            MemberLVCanBuyReq mlvsReq = new MemberLVCanBuyReq();
            mlvsReq.setProduct_id(cp.getProduct_id());
            mlvsReq.setMember_lv_id(cp.getMember_lv_id());
			 resp = memberLVSpecServ.memberLvCanBuyGoods(mlvsReq);
			 if(!"0".equals(resp.getError_code())){
				 CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"会员ID["+cp.getMember_lv_id()+"]不能购买商品["+cp.getProduct_id()+"]"));
			 }
            
            Goods goods = goodsServ.getGoodsByProductID(cp.getProduct_id());
            if (goods == null)
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "productid[" + cp.getProduct_id() + "]不存在"));
            String num = cp.getNum();
            int ino = Integer.parseInt(num);
//			if(goods.getCtn()!=0 && ino%goods.getCtn()!=0)CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"购买数量["+num+"]不符合装箱量["+goods.getCtn()+"]"));
            Product product = proudctServ.get(cp.getProduct_id());
            if (product == null)
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "productid[" + cp.getProduct_id() + "]不存在"));
            if (!StringUtil.isEmpty(cp.getPrice())) {
                product.setPrice(Double.valueOf(cp.getPrice()));
            }
            //封装购物车数据
            Cart cart = new Cart();
            cart.setProduct_id(product.getProduct_id());
            cart.setSession_id(cp.getUserSessionId());
            cart.setNum(ino);
            cart.setItemtype(0);
            cart.setWeight(product.getWeight());
            if (member != null) {
            	MemberPriceByPIdReq preq = new MemberPriceByPIdReq();
            	preq.setProduct_id(product.getProduct_id());
            	preq.setMember_lv_id(cp.getMember_lv_id());
            	GoodsLvPrice gp = memberPriceServ.getPriceByPid(preq).getGoodsLvPrice();
                //GoodsLvPrice gp = memberPriceManager.qryPriceByPid(product.getProduct_id(), cp.getMember_lv_id());
            	MemberLvByIdReq req = new MemberLvByIdReq();
            	req.setMember_id(cp.getMember_id());
            	MemberLv lv = memberLvServ.getMemberLvById(req).getMemberLv();
               // MemberLv lv = this.memberLvManager.get(cp.getMember_lv_id());
                if (lv != null) {
                    double discount = lv.getDiscount() / 100.00;
                    Double price = product.getPrice() * discount;
                    if (gp != null) {
                        price = gp.getPrice();
                    }
                    cart.setPrice(price);
                } else {
                    cart.setPrice(product.getPrice());
                }
                cart.setMember_lv_id(cp.getMember_lv_id());
            } else {
                cart.setPrice(product.getPrice());
                cart.setMember_lv_id(Const.MEMBER_LV_COMMON);
            }
            cart.setName(product.getName());

            //添加配件=====
            if (cp.getAdjuncts() != null && cp.getAdjuncts().size() > 0) {
                String name = "";
                Double price = 0D;
                List<Map> adjGroupList = goodsAdjunctServ.list(goods.getGoods_id());
                if (adjGroupList == null || adjGroupList.size() == 0)
                    CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品[" + cp.getProduct_id() + "]没有配件"));
                List<AdjunctItem> adjList = new ArrayList<AdjunctItem>();
                List<Adjunct> list = cp.getAdjuncts();
                for (Map adjGroup : adjGroupList) {

                    String jsonItems = (String) adjGroup.get("items");
                    List<Map> items = JSON.parseArray(jsonItems, Map.class);
                    for (Map m : items) {
                        for (Adjunct a : list) {
                            if (a.getProduct_id().equals(m.get("productid"))) {
                                Product pa = proudctServ.get(a.getProduct_id());
                                if (pa == null)
                                    CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "配件[" + pa.getProduct_id() + "]不存在"));
                                String adjname = pa.getName();
                                Double adjprice = pa.getPrice();
                                String lvid = cp.getMember_lv_id();
                                if (lvid == null || "".equals(lvid)) {
                                    if (member != null) {
                                        lvid = cp.getMember_lv_id();
                                    } else {
                                        lvid = Const.MEMBER_LV_COMMON;
                                    }
                                }
                                Map gmap = null;
                                MemberLvPriceReq mlsreq = new MemberLvPriceReq();
								mlsreq.setGoods_id(pa.getGoods_id());
								mlsreq.setProduct_id(pa.getProduct_id());
								mlsreq.setLvid(lvid);
								MemberLvPriceResp mlvs = new MemberLvPriceResp();
								mlvs = memberLVSpecServ.getMemberLvPrice(mlsreq);
								if(mlvs != null){
									gmap = mlvs.getMemberLVSpecMap();
								}
                                Double memberPrice = null;
                                if (gmap != null) {
                                    memberPrice = Double.valueOf(gmap.get("price").toString());
                                }
                                if (memberPrice != null)
                                    adjprice = memberPrice;

                                String type = (String) adjGroup.get("set_price");  //折扣方式
                                Double discount = ((BigDecimal) adjGroup.get("price")).doubleValue();      //折扣价格
                                if ("discount".equals(type)) { //打折方式
                                    adjprice = CurrencyUtil.mul(adjprice, discount);
                                }
                                if ("minus".equals(type)) { //直接减价
                                    adjprice = CurrencyUtil.sub(adjprice, discount);
                                }
                                adjprice = CurrencyUtil.mul(Double.valueOf(a.getNum()), adjprice);
                                price = CurrencyUtil.add(price, adjprice);
                                name += "+" + adjname + "(" + a.getNum() + ")";
                                AdjunctItem adjunctItem = new AdjunctItem();
                                adjunctItem.setName(name);
                                adjunctItem.setNum(Integer.valueOf(a.getNum()));
                                adjunctItem.setGoodsid(pa.getGoods_id());
                                adjunctItem.setProductid(pa.getProduct_id());
                                adjunctItem.setPrice(pa.getPrice());
                                adjunctItem.setCoupPrice(adjprice);
                                adjList.add(adjunctItem);
                            }
                        }
                    }
                }
                if (adjList.size() < list.size()) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "配件不存在"));
                cart.setName(cart.getName() + name);
                cart.setPrice(CurrencyUtil.add(cart.getPrice(), price));
                cart.setAddon(JSONArray.fromObject(adjList).toString());
            }
            cartManager.add(cart);
            op.setUserSessionId(cp.getUserSessionId());
            op.setError_code(result_code);
            op.setError_msg(result_msg);
            op.setCart(cart);
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 删除购物车商品
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public ZteResponse delete(CartDelReq cp) {
        try {
            String result_code = "0";
            String result_msg = "成功";
            ZteResponse op = new ZteResponse();
            //CartDelReq cp = CommonTools.jsonToBean(json, CartDelReq.class);
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            if (cp.getCart_ids() == null || cp.getCart_ids().length == 0)
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "cart_ids不能为空"));
            String cartids = "";
            for (String s : cp.getCart_ids()) {
                cartids += s + ",";
            }
            if (cartids.length() > 1)
                cartids = cartids.substring(0, cartids.length() - 1);
            cartManager.deleteSelect(cp.getUserSessionId(), cartids);
            op.setUserSessionId(cp.getUserSessionId());
            op.setError_code(result_code);
            op.setError_msg(result_msg);
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 清空购物车
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public ZteResponse clear(ZteRequest cp) {
        try {
            ZteResponse op = new ZteResponse();
            //CartDelReq cp = CommonTools.jsonToBean(json, CartDelReq.class);
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            cartManager.clean(cp.getUserSessionId(), false);
            op.setError_code("0");
            op.setError_msg("成功");
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 修改购物车商品
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public ZteResponse update(CartUpdateReq cp) {
        try {
            String result_code = "0";
            String result_msg = "成功";
            ZteResponse op = new ZteResponse();
            //CartUpdateReq cp = CommonTools.jsonToBean(json, CartUpdateReq.class);
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            if (cp.getAction() == null || "".equals(cp.getAction()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "action不能为空"));
            if (cp.getCartList() == null || cp.getCartList().size() == 0)
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "CartList不能为空"));
            if ("num".equals(cp.getAction())) {
                for (params.cart.req.CartUpdateReq.Cart c : cp.getCartList()) {
                    cartManager.updateNum(cp.getUserSessionId(), Long.valueOf(c.getCart_id()), Integer.valueOf(c.getNum()));
                }
            } else if ("checked".equals(cp.getAction())) {
                for (params.cart.req.CartUpdateReq.Cart c : cp.getCartList()) {
                    String checkedFlag = "1";
                    if (!c.isChecked())
                        checkedFlag = "0";
                    String sql = SF.orderSql("SERVICE_CART_UPDATE");
                    this.baseDaoSupport.execute(sql, checkedFlag, cp.getUserSessionId(), c.getCart_id());
                }
            } else {
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "[" + cp.getAction() + "]有误"));
            }
            op.setUserSessionId(cp.getUserSessionId());
            op.setError_code(result_code);
            op.setError_msg(result_msg);
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 查看购物车总价
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public CartTotalResp getTotal(CartTotalReq cp) {
        try {
        	//初始化bean
        	init();
        	
            String result_code = "0";
            String result_msg = "成功";
            CartTotalResp op = new CartTotalResp();
            //CartTotalReq cp = CommonTools.jsonToBean(json, CartTotalReq.class);
            Member member = CommonTools.getLoginMember();
            if (member == null && cp.getMember_id() != null && !"".equals(cp.getMember_id())){
            	MemberByIdReq req = new MemberByIdReq();
            	req.setMember_id(cp.getMember_id());
            	
            	MemberByIdResp re = memberServ.getMemberById(req);
            	if(re != null){	
            		member = re.getMember();
            	}	
            }
            
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            String sessionid = cp.getUserSessionId();
            Coupons coupons = null;
            if(!StringUtils.isEmpty(cp.getCoupon_code()) && member!=null)
            	coupons = promotionServ.useCoupon(cp.getCoupon_code(), member.getMember_id());
            op.setCoupon(coupons);

            List<CartItem> goodsItemList = cartManager.listGoods(member,sessionid,coupons);
            List<CartItem> checkedGoodsItemList = new ArrayList<CartItem>();
            for (int i = 0; goodsItemList != null && i < goodsItemList.size(); i++) {
                CartItem cartItem = goodsItemList.get(i);
                if ("1".equals(cartItem.getIs_checked())) {
                    checkedGoodsItemList.add(cartItem);
                }
            }
            OrderPrice orderprice = null;//this.cartManager.countPrice(sessionid, null, null, null, cp.isCheckedFlag());
           
           
            if(!StringUtils.isEmpty(cp.getStaff_no())){
            	OrderOuter ot = new OrderOuter();
            	ot.setCoupon(coupons);
            	orderprice = cartManager.countPrice(ot,member,sessionid, null, null, null, cp.isCheckedFlag(),cp.getStaff_no());
            }else{
            	orderprice = this.cartManager.countPrice(member,sessionid, null, null, null, cp.isCheckedFlag(),coupons);
            }
            if (member != null && !CommonTools.checkMemberLvId(cp.getMember_lv_id(), member))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户等级ID[" + cp.getMember_lv_id() + "]有误"));
            if (member != null)
                ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            if (member != null) {
                Set<String> set = new HashSet<String>();
                for (CartItem c : checkedGoodsItemList) {
                    set.add(c.getStaff_no());
                }
                String[] stafs = set.toArray(new String[0]);
                List<Promotion> pmtList = promotionServ.list(orderprice.getGoodsPrice(), cp.getMember_lv_id(),coupons, stafs);
                if (pmtList != null && pmtList.size() > 0)
                    op.setOrdePmtList(pmtList);
            }
            op.setUserSessionId(cp.getUserSessionId());
            op.setError_code(result_code);
            op.setError_msg(result_msg);
            op.setCartPrice(orderprice);
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 购物车列表
     *
     * @param json
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-23
     */
    @Override
	public CartListResp list(CartListReq cp) {
        try {
        	//初始化bean
        	init();
        	
            String result_code = "0";
            String result_msg = "成功";
            CartListResp op = new CartListResp();
            //CartListReq cp = CommonTools.jsonToBean(json, CartListReq.class);
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            Member member = CommonTools.getLoginMember();
            if (member == null && cp.getMember_id() != null && !"".equals(cp.getMember_id())){
            	MemberByIdReq req = new MemberByIdReq();
            	req.setMember_id(cp.getMember_id());
            	
            	MemberByIdResp re = memberServ.getMemberById(req);
            	if(re != null){	
            		member = re.getMember();
            	}		
            	//member = memberManager.get(cp.getMember_id());
            }
            if (member != null)
                ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            String sessionid = cp.getUserSessionId();
            List<CartItem> goodsList = null; //商品列表
            List<CartItem> giftItemList = null; //兑换赠品列表
            List<CartItem> pgkItemList = null; //捆绑商品列表
            List<CartItem> groupBuyList = null; //读取团购列表
            if(!StringUtils.isEmpty(cp.getStaff_no())){
            	goodsList = cartManager.listGoods(member,sessionid,cp.getStaff_no(),null); //商品列表
            	giftItemList = cartManager.listGift(member,sessionid,cp.getStaff_no()); //兑换赠品列表
            	pgkItemList = cartManager.listPgk(member,sessionid,cp.getStaff_no()); //捆绑商品列表
            	groupBuyList = cartManager.listGroupBuy(member,sessionid,cp.getStaff_no()); //读取团购列表
            }else{
            	goodsList = cartManager.listGoods(member,sessionid,null); //商品列表
            	giftItemList =cartManager.listGift(member,sessionid); //兑换赠品列表
            	pgkItemList = cartManager.listPgk(member,sessionid); //捆绑商品列表
            	groupBuyList = cartManager.listGroupBuy(sessionid); //读取团购列表
            }
            List<CartItem> checkedGoodsItemList = new ArrayList<CartItem>();
			for(int i=0; goodsList!=null && i<goodsList.size(); i++){
				CartItem cartItem = goodsList.get(i);
				if("1".equals(cartItem.getIs_checked())){
					checkedGoodsItemList.add(cartItem);
				}
			}
			goodsList = checkedGoodsItemList;
            op.setGoodsItemList(goodsList);
            op.setGiftItemList(giftItemList);
            op.setPgkItemList(pgkItemList);
            op.setGroupBuyList(groupBuyList);
            //分供货商
            //Map<String,Map<String,List<CartItem>>> map = this.parseCartItem(goodsList, giftItemList, pgkItemList, groupBuyList);
            //op.setCartMap(map);
            Coupons coupons = null;
            op.setCoupon(coupons);

            op.setUserSessionId(cp.getUserSessionId());
            op.setError_code(result_code);
            op.setError_msg(result_msg);
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    public Map parseCartItem(List<CartItem> goodsList, List<CartItem> giftItemList, List<CartItem> pgkItemList, List<CartItem> groupBuyList) {
        Map<String, Map> map = new HashMap<String, Map>();
        for (CartItem c : goodsList) {
            String staff_no = c.getStaff_no();
            if (map.containsKey(staff_no)) {
                if (map.get(staff_no).containsKey("goodsItemList")) {
                    ((List) (map.get(staff_no).get("goodsItemList"))).add(c);
                } else {
                    List<CartItem> cls = new ArrayList<CartItem>();
                    cls.add(c);
                    map.get(staff_no).put("goodsItemList", cls);
                }
            } else if (staff_no != null) {
                Map<String, List<CartItem>> goods = new HashMap<String, List<CartItem>>();
                List<CartItem> cls = new ArrayList<CartItem>();
                cls.add(c);
                goods.put("goodsItemList", cls);
                map.put(staff_no, goods);
                putShopName(goods, staff_no);
            }
        }
        for (CartItem c : giftItemList) {
            String staff_no = c.getStaff_no();
            if (map.containsKey(staff_no)) {
                if (map.get(staff_no).containsKey("giftItemList")) {
                    ((List) (map.get(staff_no).get("giftItemList"))).add(c);
                } else {
                    List<CartItem> cls = new ArrayList<CartItem>();
                    cls.add(c);
                    map.get(staff_no).put("giftItemList", cls);
                }
            } else if (staff_no != null) {
                Map<String, List<CartItem>> goods = new HashMap<String, List<CartItem>>();
                List<CartItem> cls = new ArrayList<CartItem>();
                cls.add(c);
                goods.put("giftItemList", cls);
                map.put(staff_no, goods);
                putShopName(goods, staff_no);
            }
        }
        for (CartItem c : pgkItemList) {
            String staff_no = c.getStaff_no();
            if (map.containsKey(staff_no)) {
                if (map.get(staff_no).containsKey("pgkItemList")) {
                    ((List) (map.get(staff_no).get("pgkItemList"))).add(c);
                } else {
                    List<CartItem> cls = new ArrayList<CartItem>();
                    cls.add(c);
                    map.get(staff_no).put("pgkItemList", cls);
                }
            } else if (staff_no != null) {
                Map<String, List<CartItem>> goods = new HashMap<String, List<CartItem>>();
                List<CartItem> cls = new ArrayList<CartItem>();
                cls.add(c);
                goods.put("pgkItemList", cls);
                map.put(staff_no, goods);
                putShopName(goods, staff_no);
            }
        }
        for (CartItem c : groupBuyList) {
            String staff_no = c.getStaff_no();
            if (map.containsKey(staff_no)) {
                if (map.get(staff_no).containsKey("groupBuyList")) {
                    ((List) (map.get(staff_no).get("groupBuyList"))).add(c);
                } else {
                    List<CartItem> cls = new ArrayList<CartItem>();
                    cls.add(c);
                    map.get(staff_no).put("groupBuyList", cls);
                }
            } else if (staff_no != null) {
                Map<String, List<CartItem>> goods = new HashMap<String, List<CartItem>>();
                List<CartItem> cls = new ArrayList<CartItem>();
                cls.add(c);
                goods.put("groupBuyList", cls);
                map.put(staff_no, goods);
                putShopName(goods, staff_no);
            }
        }
        return map;
    }

    /**
     * @param map
     * @param userid 供货商管理员ID
     * @作者 MoChunrun
     * @创建日期 2013-8-23
     */
    public void putShopName(Map map, String userid) {
        if (Const.ADMIN_CHINA_TELECOM_SUPPLIER_ID.equals(userid)) {
            map.put("shopName", "电信部门");
            return;
        }
        map.put("shopName", getShopName(userid));
    }

    public String getShopName(String userid) {
        String sql = SF.orderSql("SERVICE_SUPPLIER_SELECT");
        List<Map> list = this.baseDaoSupport.queryForList(sql, userid);
        if (list != null && list.size() > 0) {
            return String.valueOf(list.get(0).get("company_name"));
        } else {
            return "电信部门";
        }
    }

    @Override
	public CartCleanResp clean(CartCleanReq cp) {
        cartManager.clean(cp.getSessionid(), cp.getUserid(), cp.getId());
        return null;
    }

    public ICartManager getCartManager() {
        return cartManager;
    }

    public void setCartManager(ICartManager cartManager) {
        this.cartManager = cartManager;
    }

	public ProductInf getProudctServ() {
		return proudctServ;
	}

	public void setProudctServ(ProductInf proudctServ) {
		this.proudctServ = proudctServ;
	}

	public GoodsAdjunctInf getGoodsAdjunctServ() {
		return goodsAdjunctServ;
	}

	public void setGoodsAdjunctServ(GoodsAdjunctInf goodsAdjunctServ) {
		this.goodsAdjunctServ = goodsAdjunctServ;
	}

	public PromotionInf getPromotionServ() {
		return promotionServ;
	}

	public void setPromotionServ(PromotionInf promotionServ) {
		this.promotionServ = promotionServ;
	}

	public GoodsInf getGoodsServ() {
		return goodsServ;
	}

	public void setGoodsServ(GoodsInf goodsServ) {
		this.goodsServ = goodsServ;
	}

	@Override
	public void insertCart(Cart cart) {
		this.cartManager.add(cart);
	}

    /*public IMemberLvManager getMemberLvManager() {
        return memberLvManager;
    }

    public void setMemberLvManager(IMemberLvManager memberLvManager) {
        this.memberLvManager = memberLvManager;
    }

    public IMemberPriceManager getMemberPriceManager() {
        return memberPriceManager;
    }

    public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
        this.memberPriceManager = memberPriceManager;
    }

    public IMemberManager getMemberManager() {
        return memberManager;
    }

    public void setMemberManager(IMemberManager memberManager) {
        this.memberManager = memberManager;
    }*/

}
