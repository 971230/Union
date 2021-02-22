package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.app.base.core.plugin.PromotionPluginBundle;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IntegerMapper;
import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.plugin.IPlugin;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.model.PromotionVO;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.DiscountPrice;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.IPromotionManager;
import com.ztesoft.net.mall.core.service.promotion.IDiscountBehavior;
import com.ztesoft.net.mall.core.service.promotion.IGiveGiftBehavior;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.mall.core.service.promotion.IReduceFreightBehavior;
import com.ztesoft.net.mall.core.service.promotion.IReducePriceBehavior;
import com.ztesoft.net.mall.core.service.promotion.ITimesPointBehavior;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderPromotion;
import com.ztesoft.net.sqls.SF;


/**
 * 优惠规则管理
 *
 * @author kingapex
 *         2010-4-15下午05:14:57
 */

public class PromotionManager extends BaseSupport implements IPromotionManager {
    private PromotionPluginBundle promotionPluginBundle;
    private ISettingService settingService;

    public ISettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public String add(Promotion promotion, Integer[] memberLvIdArray,
                      String[] goodsIdArray) {

        if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
        if (promotion.getPmt_describe() == null)
            throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
        if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
        if (promotion.getPmt_time_begin() == null)
            throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
        if (promotion.getPmt_time_end() == null)
            throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");
        //	if(promotion.getPmt_basic_type()== null ) throw new  IllegalArgumentException("param promotion 's Pmt_basic_type is NULL");


        promotion.setPmt_update_time(new java.util.Date().getTime());
        promotion.setDisabled("false");
        IPlugin plugin = this.getPlugin(promotion.getPmts_id());
        promotion.setPmt_basic_type(plugin.getType());

        this.baseDaoSupport.insert("promotion", promotion);
        String pmtid = promotion.getPmt_id();
        promotion.setPmt_id(pmtid);


        //保存会员级别关联
        if (memberLvIdArray != null) {
            this.saveMemberLv(pmtid, memberLvIdArray);
        }

        if (goodsIdArray != null) {
            this.saveGoods(pmtid, goodsIdArray);
        }

        this.pluginSave(promotion);
        return pmtid;
    }
    /**
     * 团购秒杀插入promotion_activity&promotion数据
     * @param promotion
     * @param memberLvIdArray
     * @param goodsIdArray
     * @return
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void addT(Map map ) {

        if (map == null) throw new IllegalArgumentException("param map is NULL");
		PromotionActivity activity = new PromotionActivity();
		String name = (String) map.get("name");
		int enable =Integer.parseInt((String)map.get("enable"));
		String begin_time = (String)map.get("begin_time");
		String end_time =(String) map.get("end_time");
		String pmt_code = (String) map.get("pmt_code");
		String brief = (String) map.get("brief");
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getUserid();
		if(ManagerUtils.isAdminUser())
			userid="-1";
		activity.setUserid(userid);
		activity.setName(name);
		activity.setEnable(enable);
		activity.setBegin_time(begin_time);
		activity.setEnd_time(end_time);
		activity.setBrief(brief);
		activity.setPmt_code(pmt_code);
		this.baseDaoSupport.insert("promotion_activity", activity);
		String id = activity.getId();
        String pmt_time_begin = (String) map.get("pmt_time_begin");
        String pmt_time_end = (String) map.get("pmt_time_end");
        String pmt_solution = (String) map.get("pmt_solution");
        String pmt_describe = (String) map.get("pmt_describe");
        String a = (String) map.get("a_flag");
      
    	Promotion promotion = new Promotion();
	    if("1".equals(a)){
    	    promotion.setPromotion_type("008");    
          }else{
        	  promotion.setPromotion_type("009");  
          }
    	promotion.setPmta_id(id);
    	promotion.setPmt_time_begin(pmt_time_begin);
    	promotion.setPmt_time_end(pmt_time_end);
    	promotion.setPmt_solution(pmt_solution);
    	promotion.setPmt_describe(pmt_describe);
    	promotion.setPmt_type("0");
        promotion.setPmt_update_time(new java.util.Date().getTime());
        promotion.setDisabled("false");
       /* IPlugin plugin = this.getPlugin(promotion.getPmts_id());
        promotion.setPmt_basic_type(plugin.getType());*/
        promotion.setPmts_id(" ");
        this.baseDaoSupport.insert("promotion", promotion);


    }
    @Override
	public String add(Promotion promotion, Integer[] memberLvIdArray, int type, String[] goodsIdArray, Integer[] goodsCatIdArray, Integer[] tagIdArray) {
        String result = "";
        if (type == 1) {
            result = this.add(promotion, memberLvIdArray, goodsIdArray);
        }
        if (type == 2) {
        }
        return result;
    }


    @Override
	public void edit(Promotion promotion, Integer[] memberLvIdArray, String[] goodsIdArray) {

        if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
        if (promotion.getPmt_describe() == null)
            throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
        if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
        if (promotion.getPmt_time_begin() == null)
            throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
        if (promotion.getPmt_time_end() == null)
            throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");

        promotion.setPmt_update_time(new java.util.Date().getTime());
        promotion.setDisabled("false");
        IPlugin plugin = this.getPlugin(promotion.getPmts_id());
        promotion.setPmt_basic_type(plugin.getType());
        String pmtid = promotion.getPmt_id();
        this.baseDaoSupport.update("promotion", promotion, "pmt_id=" + pmtid);
        promotion.setPmt_id(pmtid);


        //保存会员级别关联
        if (memberLvIdArray != null) {
            this.baseDaoSupport.execute(SF.goodsSql("PMT_MEMBER_LV_DEL"), pmtid);
            this.saveMemberLv(pmtid, memberLvIdArray);
        }

        if (goodsIdArray != null) {
            this.baseDaoSupport.execute(SF.goodsSql("PMT_GOODS_DEL"), pmtid);
            this.saveGoods(pmtid, goodsIdArray);
        }

        this.pluginSave(promotion);

    }

	@Override
	public Page queryPromotionAct(PromotionActivity activity,Promotion promotion,String goodsid,int pageNo, int pageSize) {
		String sql = SF.goodsSql("PROMOTION_LIST");
	
		if(activity!=null){
			if(!StringUtil.isEmpty(activity.getUserid())){
				sql += " AND t.userid = '"+activity.getUserid()+"'";
			}
			
			if(!StringUtil.isEmpty(activity.getPartner_id())){
				sql += " AND t.userid in (select adminuser.userid from es_adminuser adminuser where adminuser.source_from=t.source_from and adminuser.relcode ='"+activity.getPartner_id()+"')";
			}
			
			if(!StringUtil.isEmpty(activity.getName())){
				sql += " AND t.name like '%"+activity.getName()+"%'";
			}
			
			if(activity.getEnable()>-1){
				sql += " AND t.enable = "+activity.getEnable();
			}
			if(!StringUtil.isEmpty(activity.getBegin_time())){
				sql += " AND t.begin_time >= "+ DBTUtil.to_date(activity.getBegin_time(), 1)+"";
			}
			if(!StringUtil.isEmpty(activity.getEnd_time())){
				sql += " AND t.end_time <= "+DBTUtil.to_date(activity.getEnd_time(), 1)+"";
			}
			
		}
		
		if(promotion!=null){
			if(!StringUtil.isEmpty(promotion.getPromotion_type())){
				sql += " AND a.promotion_type = "+promotion.getPromotion_type();
			}
		}
		if(!StringUtil.isEmpty(goodsid)){
//		  sql+=	" and exists(select 1 from es_pmt_goods WHERE pmt_id=a.pmt_id and goods_id ="+goodsid+")";
		  sql+=	" and a.pmt_id in (select pmt_goods.pmt_id from es_pmt_goods pmt_goods WHERE pmt_goods.source_from = a.source_from and pmt_goods.pmt_id=a.pmt_id and pmt_goods.goods_id ="+goodsid+")";
		}
				
		sql += " order by a.pmt_id desc";
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,PromotionVO.class, null);
		return page; 	
	}
	
    @Override
	public List<Promotion> list(String goodsid, String memberLvId,Coupons coupon, String... others) {
        if (goodsid == null) throw new IllegalArgumentException("goodsid is NULL");
        if (memberLvId == null) throw new IllegalArgumentException("memberLvId is NULL");
        //long now  = System.currentTimeMillis();
        String AUserId = "";
        if (others != null && others.length > 0) {
            String stfids = "";
            for (String s : others) {
                stfids += s + ",";
            }
            stfids = stfids.substring(0, stfids.length() - 1);
            stfids = stfids.replace(",", "','");
            if (!StringUtil.isEmpty(stfids))
                AUserId += " and a.userid in ('" + stfids + "')";
        }
        String pSql ="select * from es_promotion where pmt_basic_type='goods'" +
		    " and  pmt_time_begin<"+DBTUtil.current()+" and  pmt_time_end>"+DBTUtil.current()+
		    " and (if_limit='0' or (if_limit='1' and limit_num>buy_num))"+
		    " and ((pmt_id in  (select pmt_id from es_pmt_goods where goods_id='"+goodsid+"')" +
		    " and pmt_id in (select pmt_id from es_pmt_member_lv where lv_id ='"+memberLvId+"' or lv_id ='-1' )" + 
		    " and pmt_type='0' "; //SF.goodsSql("PROMOTION_SELECT")
        String sql =  pSql+ " and exists (select 1 from es_promotion_activity a where a.id=pmta_id " + AUserId + " and a.enable=1 and pmta_id is not null and "+DBTUtil.current()+" between a.begin_time and a.end_time))";
       
        //如果会员使作了优惠卷，将优惠卷对应的促销规则加入
        if(coupon==null)
        	coupon = (Coupons) ThreadContextHolder.getSessionContext().getAttribute("coupon");
        if (coupon != null) {
            String coup_pmtid = coupon.getPmt_id();
            String CUserId = "";
            if (others != null && others.length > 0) {
                String stfids = "";
                for (String s : others) {
                    stfids += s + ",";
                }
                stfids = stfids.substring(0, stfids.length() - 1);
                stfids = stfids.replace(",", "','");
                if (!StringUtil.isEmpty(stfids))
                    AUserId += " and a.userid in ('" + stfids + "')";
            }
            sql += " or (pmt_id=" + coup_pmtid + " and pmt_basic_type='goods' and exists (select 1 from es_coupons t where t.cpns_status=1 and t.pmt_id is not null and t.pmt_id='" + coup_pmtid + "' " + CUserId + "))";
        }
        sql += ")";
       // params[2] = goodsid;
        //params[3] =memberLvId;
        sql+= "and source_from ='"+ManagerUtils.getSourceFrom()+"'";
        
//        sql ="select * from es_promotion where pmt_basic_type = 'goods'   and pmt_time_begin < sysdate and pmt_time_end > sysdate" 
//		  +" and ((pmt_id in (select pmt_id from es_pmt_goods where goods_id = '201402248439001606') and"
//		   +"    pmt_id in (select pmt_id from es_pmt_member_lv where lv_id = '1') and"
//		     +"     pmt_type = '0' and exists"
//		     +"      (select 1"
//		      +"         from es_promotion_activity a "
//		       +"       where a.id = pmta_id"
//		       +"         and a.enable = 1 "
//		        +"        and pmta_id is not null "
//		         +"       and sysdate between a.begin_time and a.end_time))) "
//		    +"  and source_from = 'FJ'";
        
        return this.daoSupport.queryForList(sql, Promotion.class,null);
    }

    /**
     * others 是供货商ID
     */
    @Override
	public List<Promotion> list(Double orderPrice, String memberLvId,Coupons coupon, String... others) {
        long now = System.currentTimeMillis();

        String AUserId = "";
        if (others != null && others.length > 0) {
            String stfids = "";
            for (String s : others) {
                stfids += s + ",";
            }
            stfids = stfids.substring(0, stfids.length() - 1);
            AUserId += " and a.userid in ('" + stfids + "')";
        }

        String sql = SF.goodsSql("PROMOTION_SELECT_0") + " and exists (select 1 from es_promotion_activity a where a.id=pmta_id and a.enable=1 " + AUserId + " and pmta_id is not null and "+DBTUtil.current()+" between a.begin_time and a.end_time))";

        List param = new ArrayList();
       // param.add(DBTUtil.current());
       // param.add(DBTUtil.current());
        //如果会员使作了优惠卷，将优惠卷对应的促销规则加入
        //Coupons coupon = (Coupons) ThreadContextHolder.getSessionContext().getAttribute("coupon");
        if (coupon != null) {
            String coup_pmtid = coupon.getPmt_id();
            String CUserId = "";
            if (others != null && others.length > 0) {
                String stfids = "";
                for (String s : others) {
                    stfids += s + ",";
                }
                stfids = stfids.substring(0, stfids.length() - 1);
                CUserId += " and t.userid in ('" + stfids + "') ";
            }
            sql += " or ( pmt_id=" + coup_pmtid + " and pmt_basic_type='order' and exists (select 1 from es_coupons t where t.cpns_status=1 and t.pmt_id is not null and t.pmt_id='" + coup_pmtid + "' " + CUserId + "))";
        }
        sql += ")";
        param.add(orderPrice);
        param.add(orderPrice);
        param.add(memberLvId);
        //param.add(DBTUtil.current());
        return this.daoSupport.queryForList(sql, Promotion.class, param.toArray());
    }
    @Override
	public void editTM(String pmt_solution,String enable){
    	String sql = SF.goodsSql("PMTA_ID");
    	String sql2 = SF.goodsSql("PROMOTION_ACTIVITY_ENABLE");
    	String pmta_id = this.daoSupport.queryForString(sql, pmt_solution);
    	this.daoSupport.execute(sql2,enable,pmta_id);
    }
    private Integer getCoupPmtId(String coupcode) {
        String sql = SF.goodsSql("GET_COUP_PMTID");
        return this.daoSupport.queryForInt(sql, coupcode);
    }


    @Override
	public void applyGoodsPmt(List<CartItem> list, String memberLvId,Coupons coupon, String... others) {

        if (list == null || memberLvId == null) return;

        for (CartItem item : list) {

            String goodsid = item.getGoods_id();
            memberLvId = item.getMember_lv_id();
            //查询此商品所享有的优惠规则
            List<Promotion> pmtList = this.list(goodsid, memberLvId,coupon, item.getStaff_no());


            ///////////////////add by wui促销信息处理
            List<Promotion> copyPmtList = new ArrayList<Promotion>();
            //add by wu i
            for (Promotion pmt : pmtList) {
                if (pmt.getPmt_basic_type().equals("goods")) {
                    int count = this.baseDaoSupport.queryForInt(SF.goodsSql("PMT_GOODS_SELECT"), pmt.getPmt_id(), goodsid);
                    if (count == 0) //积分，打折不是增对该商品的，不给于处理
                        continue;
                }
                copyPmtList.add(pmt);
            }
            item.setPmtList(copyPmtList);
            ///////////////////add by wui  end


            for (Promotion pmt : pmtList) {


                //查找相应插件
                String pluginBeanId = pmt.getPmts_id();
                IPromotionPlugin plugin = this.getPlugin(pluginBeanId);


                if (plugin == null) {
                    logger.info("plugin[" + pluginBeanId + "] not found ");
                    throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
                }

                //查找相应优惠方式
                String methodBeanName = plugin.getMethods();
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("find promotion method[" + methodBeanName + "]");
                }
                IPromotionMethod promotionMethod = SpringContextHolder.getBean(methodBeanName);
                if (promotionMethod == null) {
                    logger.info("plugin[" + methodBeanName + "] not found ");
                    throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
                }


                if (pmt.getPmt_basic_type().equals("goods")) {
                    int count = this.baseDaoSupport.queryForInt(SF.goodsSql("PMT_GOODS_COUNT"), pmt.getPmt_id(), goodsid);

                    if (count == 0) //积分，打折不是增对该商品的，不给于处理
                        continue;
                }
                //打折方式
                if (promotionMethod instanceof IDiscountBehavior) {
                    IDiscountBehavior discountBehavior = (IDiscountBehavior) promotionMethod;
                    Double newPrice =0.0;
                    if(EcsOrderConsts.PMT_TYPE_ZHIJIANG.equals(pmt.getPromotion_type())) //直降
                    {
                    	String solution = pmt.getPmt_solution();
                		Double  discount =  Double.valueOf(solution);
                		newPrice =CurrencyUtil.sub(item.getCoupPrice(), discount);
                    }else if(EcsOrderConsts.PMT_TYPE_PRE_SALE.equals(pmt.getPromotion_type()))//预售
                    {
                    	String solution = pmt.getPmt_solution();
                		Double  discount =  Double.valueOf(solution);
                    	newPrice =discount;
                    } else{ //打折
                    	newPrice = discountBehavior.discount(pmt, item.getCoupPrice());
                    }
                    item.setCoupPrice(newPrice);
                }


                //翻倍积分方式
                if (promotionMethod instanceof ITimesPointBehavior) {
                    Integer point = item.getPoint();
                    ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior) promotionMethod;
                    point = timesPointBehavior.countPoint(pmt, point);
                    item.setPoint(point);
                }

            }


        }
    }


    @Override
	public DiscountPrice applyOrderPmt(Member member,Double orderPrice, Double shipFee, Integer point, String memberLvId,Coupons coupon, String... others) {
        List<Promotion> pmtList = this.list(orderPrice, memberLvId,coupon, others);
        for (Promotion pmt : pmtList) {

            //查找相应插件
            String pluginBeanId = pmt.getPmts_id();
            IPromotionPlugin plugin = this.getPlugin(pluginBeanId);
            if (plugin == null) {
                logger.info("plugin[" + pluginBeanId + "] not found ");
                throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
            }

            //查找相应优惠方式
            String methodBeanName = plugin.getMethods();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("find promotion method[" + methodBeanName + "]");
            }
            IPromotionMethod promotionMethod = SpringContextHolder.getBean(methodBeanName);
            if (promotionMethod == null) {
                logger.info("plugin[" + methodBeanName + "] not found ");
                throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
            }

            //减价方式
            if (promotionMethod instanceof IReducePriceBehavior) {
                IReducePriceBehavior reducePriceBehavior = (IReducePriceBehavior) promotionMethod;
                orderPrice = reducePriceBehavior.reducedPrice(pmt, orderPrice);
            }

            //打折方式
            if (promotionMethod instanceof IDiscountBehavior) {
                IDiscountBehavior discountBehavior = (IDiscountBehavior) promotionMethod;
                orderPrice = discountBehavior.discount(pmt, orderPrice);
            }

            //免运费方式
            if (promotionMethod instanceof IReduceFreightBehavior) {
                IReduceFreightBehavior reduceFreightBehavior = (IReduceFreightBehavior) promotionMethod;
                shipFee = reduceFreightBehavior.reducedPrice(shipFee);
            }

            //翻倍积分方式
            if (promotionMethod instanceof ITimesPointBehavior) {
                ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior) promotionMethod;
                point = timesPointBehavior.countPoint(pmt, point);
            }


        }
        DiscountPrice discountPrice = new DiscountPrice();
        discountPrice.setOrderPrice(orderPrice);
        discountPrice.setShipFee(shipFee);
        discountPrice.setPoint(point);
        return discountPrice;
    }


    /**
     * others是供货商id
     */
    @Override
	public void applyOrderPmt(String orderId, Double orderPrice, String memberLvId,Coupons coupon, String... others) {
        List<Promotion> pmtList = this.list(orderPrice, memberLvId,coupon,others);
        for (Promotion pmt : pmtList) {

            //查找相应插件
            String pluginBeanId = pmt.getPmts_id();
            IPromotionPlugin plugin = this.getPlugin(pluginBeanId);
            if (plugin == null) {
                logger.info("plugin[" + pluginBeanId + "] not found ");
                throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
            }

            //查找相应优惠方式
            String methodBeanName = plugin.getMethods();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("find promotion method[" + methodBeanName + "]");
            }
            IPromotionMethod promotionMethod = SpringContextHolder.getBean(methodBeanName);
            if (promotionMethod == null) {
                logger.info("plugin[" + methodBeanName + "] not found ");
                throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
            }

            //送赠品
            if (promotionMethod instanceof IGiveGiftBehavior) {
                IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior) promotionMethod;
                giveGiftBehavior.giveGift(pmt, orderId,memberLvId);
            }

        }
    }


    @Override
	public List listGift(List<Promotion> pmtList) {
        List giftList = new ArrayList();
        for (Promotion pmt : pmtList) {

            //查找相应插件
            String pluginBeanId = pmt.getPmts_id();
            IPromotionPlugin plugin = this.getPlugin(pluginBeanId);
            if (plugin == null) {
                logger.info("plugin[" + pluginBeanId + "] not found ");
                throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
            }

            //查找相应优惠方式
            String methodBeanName = plugin.getMethods();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("find promotion method[" + methodBeanName + "]");
            }
            IPromotionMethod promotionMethod = SpringContextHolder.getBean(methodBeanName);
            if (promotionMethod == null) {
                logger.info("plugin[" + methodBeanName + "] not found ");
                throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
            }

            if (promotionMethod instanceof IGiveGiftBehavior) {
                IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior) promotionMethod;
                List list = giveGiftBehavior.getGiftList(pmt);
                giftList.addAll(list);

            }
        }

        return giftList;
    }


    /**
     * 读取某活动的规则列表
     */

    @Override
	public List listByActivityId(String activityid) {
        String sql = SF.goodsSql("LIST_BY_ACTIVITYID");
        return this.baseDaoSupport.queryForList(sql, Promotion.class, activityid);
    }


    /**
     * 调用相应插件的保存（包括添加和修改）
     *
     * @param promotion
     */
    private void pluginSave(Promotion promotion) {
        //寻找优惠规则插件，并做相应处理
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("find promotion plugin[" + promotion.getPmts_id() + "]");
        }
        IPromotionPlugin plugin = this.getPlugin(promotion.getPmts_id());

        if (plugin == null) {
            logger.info("plugin[" + promotion.getPmts_id() + "] not found ");
            throw new ObjectNotFoundException("plugin[" + promotion.getPmts_id() + "] not found ");
        }

        //寻找优惠方法，并执行其保存操作
        String methodBeanName = plugin.getMethods();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("find promotion method[" + methodBeanName + "]");
        }
        IPromotionMethod promotionMethod = SpringContextHolder.getBean(methodBeanName);
        if (promotionMethod == null) {
            logger.info("plugin[" + methodBeanName + "] not found ");
            throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
        }

        String solution = promotionMethod.onPromotionSave(promotion.getPmt_id());
        if(promotion.getPmt_solution()!=null && !"".equals(promotion.getPmt_solution()) && "".equals(solution)){
        	solution = promotion.getPmt_solution();
        }
        this.baseDaoSupport.execute(SF.goodsSql("PLUGIN_SAVE"), solution, promotion.getPmt_id());

    }


    /**
     * 保存会员级别关联
     *
     * @param pmtid
     * @param memberLvIdArray
     */
    private void saveMemberLv(String pmtid, Integer[] memberLvIdArray) {


        for (Integer memberLvId : memberLvIdArray) {

            this.baseDaoSupport.execute(SF.goodsSql("SAVE_MEMBER_LV"), pmtid, memberLvId);
        }
    }


    /**
     * 保存商品关联
     *
     * @param pmtid
     * @param goodsIdArray
     */
    public void saveGoods(String pmtid, String[] goodsIdArray) {

        for (String goodsid : goodsIdArray) {
            this.baseDaoSupport.execute(SF.goodsSql("PMT_GOODS_INSERT"), pmtid, goodsid);
        }
    }


    @Override
	public List listPmtPlugins() {

        return promotionPluginBundle.getPluginList();
    }


    /**
     * 在桩中查找某个优惠规则插件，如果没找到则返回 null
     *
     * @param pluginid
     * @return
     */

    @Override
	public IPromotionPlugin getPlugin(String pluginid) {
        List<IPlugin> pluginList = promotionPluginBundle.getPluginList();

        for (IPlugin plugin : pluginList) {
            if (plugin.getId().equals(pluginid)) {
                if (plugin instanceof IPromotionPlugin) {
                    return (IPromotionPlugin) plugin;
                }
            }
        }
        return null;
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED)

    public void delete(String idStr) {
//        if (pmtidArray == null || pmtidArray.length == 0) return;
//        String idStr = StringUtil.arrayToString(pmtidArray, ",");
        String sql = SF.goodsSql("PMT_GOODS_DEL_BY_IDS") + " and pmt_id in(" + idStr + ")";
        this.baseDaoSupport.execute(sql);

        sql = SF.goodsSql("PMT_MEMBER_LV_BY_IDS") + " and pmt_id in(" + idStr + ")";
        this.baseDaoSupport.execute(sql);

        sql = SF.goodsSql("PROMOTION_DEL_BY_IDS") + " and pmt_id in(" + idStr + ")";
        this.baseDaoSupport.execute(sql);

    }


    @Override
	public Promotion get(String pmtid) {
        return (Promotion) this.baseDaoSupport.queryForObject(SF.goodsSql("PROMOTION_SELECT_BY_ID"), Promotion.class, pmtid);
    }


    @Override
	public List listGoodsId(String pmtid) {
        String sql = SF.goodsSql("GOODS_SELECT_BY_PMT_ID");
        return this.daoSupport.queryForList(sql, pmtid);
    }


    @Override
	public List listMemberLvId(String pmtid) {
        String sql = SF.goodsSql("LIST_MEMBER_LVID");
        return this.baseDaoSupport.queryForList(sql, new IntegerMapper(), pmtid);
    }


    @Override
	public Coupons useCoupon(String code, String memberId) {
        String str_mc_use_times = settingService.getSetting("coupons", "coupon.mc.use_times");
        int mc_use_times = str_mc_use_times == null ? 1 : Integer.valueOf(str_mc_use_times);
        String sql = SF.goodsSql("USE_COUPON");
        List param=new ArrayList();
        param.add(code);
        param.add(memberId);
        param.add(mc_use_times);
        //param.add(DBTUtil.current());
        Coupons coupons = (Coupons) this.baseDaoSupport.queryForObject(sql, Coupons.class, param.toArray());
        if (coupons != null) {
            coupons.setMemc_code(code);
            ThreadContextHolder.getSessionContext().setAttribute("coupon", coupons);
        }
        return coupons;
    }

    public PromotionPluginBundle getPromotionPluginBundle() {
        return promotionPluginBundle;
    }

    public void setPromotionPluginBundle(PromotionPluginBundle promotionPluginBundle) {
        this.promotionPluginBundle = promotionPluginBundle;
    }


    @Override
	public List<Map> listOrderPmt(String orderid) {
        String sql = SF.goodsSql("LIST_ORDER_PMT");
        return this.baseDaoSupport.queryForList(sql, orderid);
    }

    @Override
    public boolean hasOrderPmt() {
        String sql = SF.goodsSql("HAS_ORDER_PMT");
        List param = new ArrayList();
        param.add(DBTUtil.current());
        param.add(DBTUtil.current());

        //如果会员使作了优惠卷，将优惠卷对应的促销规则加入
        Coupons coupon = (Coupons) ThreadContextHolder.getSessionContext().getAttribute("coupon");
        Member member = UserServiceFactory.getUserService().getCurrentMember();
        String lv = Const.MEMBER_LV_COMMON;
        if (member != null)
            lv = member.getLv_id();
        if (coupon != null) {
            String coup_pmtid = coupon.getPmt_id();
            sql += " or ( pmt_id=" + coup_pmtid + " and pmt_basic_type='order')";
        }
        param.add(lv);
        int count = this.baseDaoSupport.queryForInt(sql, param.toArray());
        return count > 0;
    }

	@Override
	public List<OrderPromotion> listOrderPromotion(String order_id) {
		String sql = SF.orderSql("ListOrderPromotion");
		return this.baseDaoSupport.queryForList(sql, OrderPromotion.class, order_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<Goods> listPromotionGoods(String pmt_id) {
		String sql = SF.orderSql("ListPromotionGoods");
		return this.baseDaoSupport.queryForList(sql, Goods.class, pmt_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<MemberLv> listPromotionMenberLv(String pmt_id){
		String sql = SF.orderSql("PromotionMenberLv");
		return this.baseDaoSupport.queryForList(sql, MemberLv.class, pmt_id,ManagerUtils.getSourceFrom());
	}


}
