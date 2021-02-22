package com.ztesoft.net.sqls;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Member extends Sqls {

	
	public Sqls_Member(){
	}
	
  //查询会员等级
	public String getSERVICE_MEMBER_LV_LIST(){return "SELECT lv_id,NAME,discount,POINT,default_lv" + 
						" FROM es_member_lv where 1=1 ";}
	//查询会员列表
	public String getSERVICE_GET_MEMBER_LIST(){return "SELECT m.* FROM es_member m where 1=1";}// and m.source_from is not null 
	//會員計數
	public String getSERVICE_GET_MEMBER_COUNT(){return "select count(*) from es_member m where 1=1  ";}//and m.source_from is not null
	//查询会员基本信息
	public String getSERVICE_GET_MEMBER_LIST_BY_MEMBER_ID(){return "select * from member where member_id= ? ";}//and source_from is not null
	//查询会员等级信息
	public String getSERVICE_MEMBER_LV_ORDER_BY_LV_ID(){return "select * from member_lv  where 1=1 order by lv_id";}
	//刪除會員地址
	public String getSERVICE_DEL_MEMBER_ADDR_BY_ID(){return "delete from member_address where addr_id= ?";}
	//查詢會員地址
	public String getSERVICE_GET_MEMBER_ADDR_BY_ID(){return "select * from member_address where addr_id= ?";}
	//查詢會員地址
	public String getSERVICE_GET_MEMBER_ADDR_BY_MEM_ID(){return "select * from member_address where member_id= ? order by last_update desc";}
	
	public String getSERVICE_GET_MEMBER_RULE(){return "select r.*,ml.name as memberName from es_member_buy_rule r,es_member_lv ml where r.lv_id=ml.lv_id and r.goods_id=? and r.lv_id=?";}
	
	public String getSERVICE_GET_MEMBER_ORDER_SUM(){return "select sum(i.num) from es_order o,es_order_items i where o.order_id=i.order_id and o.status!=-10 and i.goods_id=? and i.lv_id=? and o.member_id=?";}
	
	public String getSERVICE_GET_MEMBER_LV_1(){return "select * from member_lv where default_lv=1";}
	
	public String getSERVICE_GET_MEMBER_LV_BY_ID(){return "select * from member_lv where lv_id=? and source_from ='"+ManagerUtils.getSourceFrom()+"'";} //共享
	
	public String getSERVICE_DEL_MEMBER_LV_BY_ID(){return "delete from member_lv where 1=1";}
	
	public String getSERVICE_GET_MEMBER_LV_BY_IDS(){return "select * from member_lv where  lv_id in(replaceSql) order by lv_id";}
	//个人客户才允许更新等级， 普通客户、银牌客户、金牌客户
	public String getSERVICE_GET_MEMBER_LV_BY_GRADE(){return  "select * from member_lv where  point<=? and lv_id not in ("+Const.MEMBER_LV_CHINA_TELECOM_DEP+","+Const.MEMBER_LV_CHINA_TELECOM_STAFF+","+Const.MEMBER_LV_SUPPLIER+") order by point desc";}
	
	public String getSERVICE_GET_MEMBER_LIST_ALL(){return  "select * from member_lv where 1=1";}
	
	public String getSERVICE_UPDATE_MEMBER_BY_ID(){return  "update member set is_cheked= 1 where member_id= ?";}
	
	public String getSERVICE_GET_MEMBER_BY_NAME(){return "select * from member where uname=?";}
	
	public String getSERVICE_GET_MEMBER_BY_EMAIL(){return "select * from member where email=?";}
	
	public String getSERVICE_GET_MEMBER_COUNT_BY_NAME(){return "select count(0) from member where uname=?";}
	
	public String getSERVICE_GET_MEMBER_COUNT_BY_EMAIL(){return "select count(0) from member where email=?";}
	
	public String getSERVICE_GET_MEMBER_LIST_01(){
		return "select m.*,lv.name as lv_name from es_member m left join es_member_lv lv on m.lv_id= lv.lv_id ";
	}
	
	public String getSERVICE_DEL_MEMBER_BY_IDS(){return "delete from member where 1=1";}
	
	public String getSERVICE_UPDATE_MEMBER_PASSWORD_BY_ID(){return "update member set password= ? where member_id =?";}
	
	public String getSERVICE_UPDATE_MEMBER_FIND_CODE_BY_ID(){return "update member set find_code= ? where member_id =? ";}
	
	public String getSERVICE_LOGIN_IN_BY_NAME(){return  "select m.*,l.name as lvname from es_member m left join es_member_lv l on m.lv_id= l.lv_id where m.uname=? and password=?";}
	
	public String getSERVICE_LOGIN_IN_BY_EMAIL(){return "select m.*,l.name as lvname from es_member m left join es_member_lv l on m.lv_id= l.lv_id where m.email=? and password=?";}
	
	public String getMEMBER_LOGIN_BY_SYS(){return "select m.*,l.name as lvname from es_member m left join es_member_lv l on m.lv_id= l.lv_id where m.uname=?";}
	
	public String getMEMBER_ADD_MONEY(){return "update member set advance= advance+? where member_id=?";}
	
	public String getMEMBER_CUT_MONEY(){return "update member set advance=advance-? where member_id=?";}
	
	public String getMEMBER_UPDATE_LV(){return "update member set lv_id=? where member_id=? and lv_id not in ("+Const.MEMBER_LV_CHINA_TELECOM_DEP+","+Const.MEMBER_LV_CHINA_TELECOM_STAFF+","+Const.MEMBER_LV_SUPPLIER+")";}
	
	public String getMEMBER_DC_PUBLIC(){return "select * from es_dc_public where stype=?";}
	
	public String getMEMBER_CHECK_TARGET_USER(){return "select count(*) unpay_count from es_order o where member_id=? and payment_type<>'offline' and pay_status=0 and create_time<? ";}
	
	public String getMEMBER_PAGE_ORDERS(){return "select * from order where member_id= ? and disabled=0 order by create_time desc";}
	
	public String getMEMBER_PAGE_ORDERS_STATUS(){return  "select * from order where member_id= ? and status=? and disabled=0 order by create_time desc";}
	
	public String getMEMBER_PAGEORDERS_ONGONING(){return "select * from order where member_id= ? and status<>? and disabled=0 order by create_time desc";}
	
	public String getMEMBER_ORDER_TOTALCOUNT(){return "select count(*) cnt from order where member_id= ? and disabled=0 order by create_time desc";}
	
	public String getMEMBER_ORDER_COUNT(){return "select count(*) cnt from order where member_id= ? and status=? and disabled=0 order by create_time desc";}
	
	public String getMEMBER_GET_ORDER(){return "select * from order where order_id= ?";}
	
	public String getMEMBER_GET_ORDER_DELIVERY(){return  "select * from delivery where order_id= ?";}
	
	public String getMEMBER_LIST_ORDERLOG(){return "select * from order_log where order_id= ?  order by op_time asc";}
	
	public String getMEMBER_LIST_GOODSITEMS(){return "select * from order_items where order_id= ?";}
	
	public String getMEMBER_LIST_GIFTITEMS(){return "select * from order_gift where order_id=?";}
	
	public String getMEMBER_IS_BUY(){return "select count(0) from es_order_items where  order_id in(select order_id from es_order where member_id=?) and goods_id =? ";}
	
	public String getMEMBER_USE_MARKET_POINT(){return "update member set mp=mp-? where member_id=?";}
	
	public String getMEMBER_ADD_POINT_GRADE(){return "update member set point=point+? where member_id=?";}
	
	public String getMEMBER_LIST_PRICE_BY_GID(){return "select * from goods_lv_price where goodsid =? and status='00A'";}
	
	public String getMEMBER_LOAD_MEMBER_LV_LIST(){return "select l.lv_id,l.name, 0 lv_price from es_member_lv l where l.source_from= ?";}
	
	public String getMEMBER_LOAD_PRICEPRIV_LIST_BY_GOODS_ID(){return " select  p.role_type lv_id ,state selected, (select name from es_member_lv l where l.lv_id=p.role_type and l.source_from= ?) name  "+
												" from es_price_priv p where p.goods_id=? order by role_type ";}
	
	public String getMEMBER_LOAD_PRICEPRIV_LIST(){return "select l.lv_id , l.name ,'00X' selected from es_member_lv l where l.source_from= ? order by lv_id " ;}
	
	public String getMEMBER_LOAD_MEMBERLV_PRICE_LIST(){return "select lvid lv_id,(select l.name from es_member_lv l where l.lv_id=g.lvid) name, price lv_price,lv_discount,productid from es_goods_lv_price g where g.goodsid=?  order by g.lvid asc";}
	
	public String getMEMBER_LIST_PRICE_BY_LVID(){return "select * from goods_lv_price where lvid =?";}
	
	public String getMEMBER_GET_SPECPRICES(){return "select p.lvid ,(select name from es_member_lv l where l.lv_id=p.lvid and l.source_from= ? ) name , p.price from es_goods_lv_price p where p.goodsid=? and p.productid=?" ;}
	
	public String getMEMBER_DEL_GOODS_BY_ID(){return "delete from  es_goods_lv_price  where goodsid=?";}
	
	public String getMEMBER_QRY_PRICE_BY_PID(){return "select * from goods_lv_price where productid =? and lvid=?";}
	
	public String getMEMBER_GOODSLV_LIST (){return "select a.id, "
			+ "       a.productid, "
//				+ "       (select d.name from es_product d where d.product_id= a.productid) prod_name, "
			+ "       a.goodsid, "
			+ "       a.lvid, "
//				+ "       (select l.name from es_member_lv l where l.lv_id= a.lvid) member_name, "
			+ "       a.price " 
			+ "  from es_goods_lv_price a "
			+ " where a.goodsid= ? and a.lvid='3'";} //只有 a.lvid='3' “电信集采”才允许修改价格
	
	public String getMEMBER_PAGE_POINT_HISTORY(){return "select * from point_history where member_id= ? and point_type=? order by time desc";}
	
	public String getMEMBER_GET_CONSUME_POINT(){return "select SUM(point) from point_history where  member_id= ?  and  point<0  and point_type=?";}
	
	public String getMEMBER_GET_GAINED_POINT(){return "select SUM(point) from point_history where    member_id= ? and  point>0  and point_type=?";}
	
	public String getMEMBER_LIST_POINT_HISTORY(){return "select * from point_history where member_id= ? order by time desc";}
	
	public String getMEMBER_LIST_CITY(){return "select * from regions where region_grade= 2 and p_region_id= ?";}
	
	public String getMEMBER_LIST_PROVINCE(){return "select * from regions where region_grade= 1";}
	
	public String getMEMBER_LIST_REGION(){return "select * from regions where region_grade= 3 and p_region_id= ?";}
	
	public String getMEMBER_LIST_CHILDREN(){return "select c.region_id,c.local_name,c.region_grade,c.p_region_id,count(s.region_id) as childnum from es_regions c" +
					" left join es_regions s on s.p_region_id= c.region_id and c.source_from= s.source_from where c.source_from= '" + ManagerUtils.getSourceFrom() + 
					"' and c.p_region_id in (replaceSql) group by c.region_id,c.local_name,c.region_grade,c.p_region_id order by region_id";}
	
	public String getMEMBER_LIST_CHILDRENP(){return "select c.region_id  from  es_regions  as c  where c. p_region_id in(replaceSql) order by region_id";}
	
	public String getMEMBER_DEL_REGIONS_BY_PATH(){return "delete from regions where region_path like ?";}
	
	public String getMEMBER_GET_REGIONS_BY_ID(){return "select * from regions where region_id= ? ";}
	
	public String getMEMBER_GET_REGIONS_BY_NAME(){return  "select * from regions where local_name= ?";}
	
	public String getMEMBER_QRY_MLV_BY_IDS(){return "select * from es_member_lv l where l.lv_id in(:instr)";}
	
	public String getMEMBER_QRYMLV_BY_IDS_AND_GOODSID(){return "select l.* from es_member_lv l,es_price_priv pp where l.lv_id in(:instr) and " +
			"l.lv_id=pp.role_type and pp.state= ? and pp.goods_id=? " +
			"and l.source_from= pp.source_from and pp.source_from= '" + ManagerUtils.getSourceFrom() + "' order by l.lv_id";}
	
	public String getMEMBER_LV_CANBY(){return "select pp.* from es_price_priv pp,es_product p where pp.goods_id=p.goods_id " +
			"and p.product_id=? and pp.role_type=? and pp.state= ? and pp.source_from= p.source_from and p.source_from= '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getMEMBER_GET_MEMER_LV_PRICE(){return "select p.price,g.image_default from es_goods g inner join es_goods_lv_price p on(g.goods_id=p.goodsid and p.productid=? and p.lvid=? and p.status='00A') where g.goods_id=? ";}
	
	public String getMEMBER_MODIFY_PWD(){return "update member set password= ? where member_id =? ";}
	
	public String getMEMBER_REGIONS_LIST(){return "select * from regions  where 1=1  order by region_id";}
	
	public String getMEMBER_IS_EXIST(){return "select count(*) from es_member m where 1=1 ";}
		
}
