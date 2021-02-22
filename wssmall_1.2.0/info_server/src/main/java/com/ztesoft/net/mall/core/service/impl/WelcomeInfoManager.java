package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.DoubleMapper;
import com.ztesoft.net.mall.core.service.IWelcomeInfoManager;

public class WelcomeInfoManager extends BaseSupport implements
        IWelcomeInfoManager {
   
    @Override
	@SuppressWarnings("unchecked")
    public Map mapWelcomInfo() {
        IUserService userService = UserServiceFactory.getUserService();
        Member member = userService.getCurrentMember();
        Map map = new HashMap();
        int oNum = this.baseDaoSupport.queryForInt("SELECT count(*) as oNum  FROM order where  member_id=? and status != 7", member.getMember_id());
        map.put("oNum", oNum);
        int oeNum = this.baseDaoSupport.queryForInt("SELECT count(*) as oNum  FROM order where pay_status=0 and member_id=? and status=2", member.getMember_id());
        map.put("oeNum", oeNum);
        int totalOrder = this.baseDaoSupport.queryForInt("SELECT count(*) as totalOrder  FROM order where member_id=? and disabled=0 ", member.getMember_id());
        map.put("totalOrder", totalOrder);
        //int mNum = this.baseDaoSupport.queryForInt( "SELECT count(*) as mNum FROM message where to_id=? and unread='0' and to_type=0 and disabled='false' and del_status=1", member.getMember_id() );
        int mNum = this.baseDaoSupport.queryForInt("select count(*) as mNum  from es_usermsg a, es_usermsg_rel b where a.m_recivertype=10 and  b.state!=2 and a.m_id in(select b.m_id from es_usermsg_rel where b.userid=? and source_from=?)  order by a.m_sendtime desc", member.getMember_id(), ManagerUtils.getSourceFrom());
        map.put("mNum", mNum);
        int pNum = this.baseDaoSupport.queryForInt("SELECT sum(point) as pNum FROM member where member_id=?", member.getMember_id());
        map.put("pNum", pNum);
        Object oaNum = this.baseDaoSupport.queryForObject("SELECT advance FROM member where member_id=?", new DoubleMapper(), member.getMember_id());
        Double aNum = oaNum == null ? 0L : (Double) oaNum;
        map.put("aNum", aNum);
        int couponNum = this.baseDaoSupport.queryForInt("SELECT count(*) as couponNum FROM member_coupon where member_id=?", member.getMember_id());
        map.put("couponNum", couponNum);
        //String curTime = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss").format((new Date()).getTime());
        String sql = "SELECT count(*) as cNum FROM " + this.getTableName("member_coupon") + " mc left join " + this.getTableName("coupons") + " c on c.cpns_id=mc.cpns_id left join " + this.getTableName("promotion") + " p on c.pmt_id=p.pmt_id where member_id=?";
        sql += " and p.pmt_time_end - "+DBTUtil.current()+"< 6 and mc.source_from=?";
        int cNum = this.daoSupport.queryForInt(sql, member.getMember_id(),ManagerUtils.getSourceFrom());
        map.put("cNum", cNum);
        int commentRNum = this.baseDaoSupport.queryForInt("SELECT count(*) as commentRNum FROM comments where author_id=? and display='true'", member.getMember_id());// and lastreply>0
        map.put("commentRNum", commentRNum);
        List pa = this.baseDaoSupport.queryForList("SELECT name FROM promotion_activity where enable='1' and end_time>="+DBTUtil.current()+" and begin_time<="+DBTUtil.current());
        pa = pa == null ? new ArrayList() : pa;
        map.put("pa", pa);
        //相关推荐
        String gc_sql="select goods_id, sn, name, image_default image, price, mktprice from es_goods t where source_from =?  and exists(select 1 from es_order a, es_order_items b,es_goods_complex c where c.source_from =? and a.order_id=b.order_id and b.goods_id=c.goods_1 and a.member_id=? and goods_2=t.goods_id) or exists(select 1 from es_order a, es_order_items b,es_goods_complex c where c.source_from =? and a.order_id=b.order_id and b.goods_id=c.goods_1 and manual='both' and a.member_id=? and goods_1=t.goods_id)";
        /*List gc = this.baseDaoSupport.queryForList("select goods_id, sn, name, image_default image, price, mktprice from "
                + this.getTableName("goods") + " t where " +
                "exists(select 1 from " + this.getTableName("order") + " a, " + this.getTableName("order_items") + " b,"
                + this.getTableName("goods_complex") + " c " +
                "where a.order_id=b.order_id and b.goods_id=c.goods_1 and a.member_id=? and goods_2=t.goods_id) " +
                "or exists(select 1 from " + this.getTableName("order") + " a, " + this.getTableName("order_items")
                + " b," + this.getTableName("goods_complex") + " c " +
                "where a.order_id=b.order_id and b.goods_id=c.goods_1 and manual='both' and a.member_id=? " +
                "and goods_1=t.goods_id)", member.getMember_id(), member.getMember_id());*/
        List params=new ArrayList();
        params.add(ManagerUtils.getSourceFrom());
        params.add(ManagerUtils.getSourceFrom());
        params.add(member.getMember_id());
        params.add(ManagerUtils.getSourceFrom());
        params.add(member.getMember_id());
        List gc=this.daoSupport.queryForList(gc_sql,params.toArray());
        gc = gc == null ? new ArrayList() : gc;
        map.put("gc", gc);

        //	gc = new ArrayList();

//        if (ListUtil.isEmpty(gc)) {
//            Tag tag = new Tag();
//            // 获取二级节点
//            Term term = new Term();
//            term.setTagid(Consts.TAG_REMD);
//            List goodsList = goodsDataProviderServ.list(term, new Integer(9).intValue());// 设置显示的数量,标签处理
//            map.put("gc", goodsList);
//        }
        return map;
    }


}