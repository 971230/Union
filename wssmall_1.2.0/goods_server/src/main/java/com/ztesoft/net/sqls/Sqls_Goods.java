package com.ztesoft.net.sqls;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Goods extends Sqls {

	public Sqls_Goods(){
		//SqlUtil.initSqls(Sqls_Goods.class, this , sqls) ;}
	}
	
	public String getGOODS_PACKAGE_QUERY() {
		return "select * from es_goods_package "
				+ " where 1=1 and source_from = '" + ManagerUtils.getSourceFrom() + "'";
	}
	
	public String getCOUNT_GOODS_PACKAGE() {
		return "select count(*) from es_goods_package "
				+ " where 1=1 and goods_id <> ?";
	}
	
	public String getSEARCH_GOODS_BATCH_PUBLISH(){
		return "select distinct g.goods_id,g.name "
				+ " from es_goods g "
				+ " left join es_goods_cat c on c.cat_id = g.cat_id and c.source_from = g.source_from "
				+ " left join es_brand b on b.brand_id = g.brand_id and b.source_from = g.source_from"
			//	+ " left join es_brand_model m on m.brand_code = b.brand_code and m.source_from = g.source_from"
				+ " where 1 = 1 "
				+ " and g.market_enable='1' and g.disabled='0' ";
				//+ " and not exists(select 1 from es_goods_co co where co.goods_id = g.goods_id) ";
		}
	
	public String getSEARCH_PRODUCT_BATCH_PUBLISH(){
		return "select distinct p.product_id as goods_id, g.name, g.create_time "
				+ " from es_product p  "
				+ " inner join es_goods g on p.goods_id = g.goods_id and g.source_from = p.source_from"
				+ " left join es_goods_type t on g.type_id = t.type_id and t.source_from = g.source_from "
				+ " left join es_goods_cat c on c.cat_id = g.cat_id and c.source_from = g.source_from "
				+ " left join es_brand b on b.brand_id = g.brand_id  and b.source_from = g.source_from"
			//	+ " left join es_brand_model m on m.brand_code = b.brand_code and m.source_from = g.source_from"
				+ " where 1 = 1 "
				+ " and g.disabled = '0'   "
				+ " and g.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				+ " and g.type = 'product'";
//				+ " and not exists(select 1 from es_product_co co where co.product_id = p.product_id) ";
	}
	
	public String getQRY_BATCH_ID_BY_ID(){return "select a.batch_id from es_activity_co a where a.activity_id =? and rownum<2";}
	//zengxianlian
	public String getQRY_BATCH_ID_BY_ID_ORG(){return "select a.batch_id from es_activity_co a where a.activity_id =? and a.org_id =?  and rownum<2";}
	public String getQRY_ACTIVITY_CO_BY_ID(){return "select a.id from es_activity_co a where a.org_id=? and a.activity_id=? and rownum<2";}
	public String getFIND_VHOUSE_BY_NAME(){return "select * from es_virtual_warehouse w where w.house_name=? and w.source_from=? ";}
	public String getFIND_VHOUSE_BY_CODE(){return "select * from es_virtual_warehouse w where w.house_code=? and w.source_from=? ";}
	public String getFIND_HOUSE_BY_NAME(){return "select * from es_warehouse w where w.house_name=? and w.source_from=? ";}
	public String getFIND_HOUSE_BY_CODE(){return "select * from es_warehouse w where w.house_code=? and w.source_from=? and w.comp_code=? ";}
	public String getFIND_COMPANY_BY_NAME(){return "select * from es_product_comp w where w.comp_name=? and w.source_from=? ";}
	public String getFIND_COMPANY_BY_CODE(){return "select * from es_product_comp w where w.comp_code=? and w.source_from=? ";}
	public String getACTIVITY_CO_CHECK(){return "select a.activity_id,a.org_id,a.status,a.oper_id from es_activity_co a where a.activity_id=? and a.source_from=?";}
	// 配置的SQL...
	public String getGOODS_CO_CHECK (){return "select goods_id,org_id from es_goods_co where 1=1 and source_from=? and goods_id=?";}
	public String getGOODS_ALREADY_PUBLISH (){return "select c.org_id,decode(c.status,0,'未发布',1,'已发布',2,'发布中',3,'发布失败') status from ES_GOODS_CO c where 1=1 and c.goods_id = ? and c.source_from=? ";} 
	public String getGOODS_ORG_TREE (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = '-1' and g.source_from = ? ";}
	public String getGOODS_ORG_TREE_1 (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = ? and g.org_level=2 and g.source_from = ? ";}
	public String getGOODS_ORG_TREE_2 (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = ? and  g.org_level <> 4  and g.source_from = ? ";}
	
	
	public String getGOODS_ORG_TREE_ZJ (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_zj_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = '-1' and g.source_from = ? ";}
	public String getGOODS_ORG_TREE_ZJ1 (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_zj_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = ? and g.org_level=2 and g.source_from = ? ";}
	public String getGOODS_ORG_TREE_ZJ2 (){return "select g.party_id ,g.parent_party_id ,g.org_code,g.org_name name,g.org_level,g.status_cd,g.status_date,g.org_type_id,g.lan_id,g.region_id from es_goods_zj_org g where 1=1  and g.status_cd ='00A' and g.parent_party_id = ? and  g.org_level <> 4  and g.source_from = ? ";}
	
	public String getGOODS_TYPE_BRAND_MODEL_LIST (){return "select * from es_brand_model t where 1=1 ";}
	
	public String getGOODS_UPDATE_INTRO (){return "update es_goods set intro=? where goods_id=?";}
	
	public String getGOODS_UPDATE_IMG_INFO (){return "update es_goods set image_file=? ,image_default=? where goods_id=?";}
	
	public String getORDER_GIFT_INSERT (){return "insert into order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)";}
	
	public String getGET_GIFT_LIST (){return "select * from freeoffer where 1=1";}
	
	public String getGOODS_GET_BRAND_LIST_0 (){return "select * from es_brand where disabled=0";}
	
	public String getGOODS_GET_BRAND_LIST_1 (){return "select * from es_brand where disabled=1";}
	
	public String getGOODS_BRAND_REVERT (){return "update es_brand set disabled=0  where 1=1";}
	
	public String getGOODS_BRAND_CHECK_USED (){return "select count(0) from es_goods where 1=1";}
	
	public String getGOODS_BRAND_UPDATE_DISABLE (){return "update es_brand set disabled=1  where 1=1";}

	public String getGOODS_BRAND_MODEL_DELETE (){return " delete  from  es_brand_model where 1=1 ";}
	
	public String getGOODS_BRAND_DELETE (){return "delete  from  es_brand   where 1=1 ";}
	
	public String getGOODS_LIST_BY_TYPE_ID (){return "select b.* from es_type_brand tb inner join es_brand b  on    b.brand_id = tb.brand_id where tb.type_id=? and tb.source_from='"+ManagerUtils.getSourceFrom()+"' and b.disabled=0";}
	
	public String getGOODS_BRAND_BY_ID (){return "select * from es_brand where brand_id=?";}
	
	public String getGOODS_GET_BY_BRAND (){return "select * from es_goods where brand_id=? and disabled=0";}
	
	public String getGOODS_LIST_BY_CATID (){return "select b.* from es_brand b ,es_type_brand tb,es_goods_cat c where b.source_from='"+ManagerUtils.getSourceFrom()+"' and tb.brand_id=b.brand_id and c.type_id=tb.type_id and c.cat_id=?";}
	
	public String getGOODS_LIST_BY_GOODS_ID (){return "select b.* from es_brand b ,es_type_brand tb,es_goods_cat c,es_goods g " +
			" where tb.brand_id=b.brand_id and c.type_id=tb.type_id " +
			" and c.cat_id=g.cat_id and g.goods_id = ? " +
			" and tb.source_from = b.source_from "+
			" and c.source_from = g.source_from "+
			" and b.source_from = c.source_from "+
			" and c.source_from  = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getGET_GOODS_CAT (){return "select * from es_goods_cat where parent_id = 0 order by cat_order";}
	
	public String getGET_GOODS_CAT_BY_PATH (){return "select distinct type_id from es_goods_cat where cat_path like ?";}
	
	public String getGOODS_TYPE_BRAND (){return "select brand_id from es_type_brand where 1=1";}
	
	public String getGET_GOODS_BRAND_BY_IDS (){return "select * from es_brand where 1=1";}
	
	public String getGET_GOODS_BRAND_COUNT (){return "select count(0) from es_brand where name=? and brand_id!=?";}
	
	public String getGOODS_TYPE_BRAND_INSERT (){return "insert into es_type_brand(type_id,brand_id) values(?,?)";}
	
	public String getGOODS_TYPE_UPDATE(){return "update es_goods_type set join_brand =1  where type_id = ?";}
	
	public String getDC_PUBLIC_FOR_UPDATE (){return "select * from es_dc_public where stype='8013' and pkey = ? for update ";}
	
	public String getCARD_INFO_UPDATE (){return "update es_card_info set ship_state ='"+ Consts.SHIP_STATE_5 + "' where order_id = ? ";}
	
	public String getGOODS_GET_CARD_LIST (){return "select * from es_card_info a where 1=1";}
	
	public String getGET_TRANS_FERPRICE (){return "select sum(card_price) from es_card_info a where 1=1";}
	
	public String getRESET_CARD_BY_ORDERID (){return "update es_card_info set ship_state ='',order_id=first_orderid  where order_id = ? ";}
	
	public String getGET_TRANSFER_LIST (){return "select card_id, card_type, card_price,eff_date,exp_date, card_disc, card_code, card_password,deal_time, " +
			" state, create_date, state_date, in_month,sec_order_id," +
			" order_id, phone_number, to_userid, from_userid from es_card_info where 1 = 1 ";}
	
	public String getTRANSFER_ALL_LIST (){return "select c.card_id, c.card_type, c.card_price, c.card_disc, c.card_code, c.card_password,c.deal_time, " +
			" c.state, c.create_date, c.state_date, c.in_month, " +
			" c.order_id, c.phone_number, c.exp_date, c.to_userid, c.from_userid from es_card_info c where 1 = 1 ";}
	
	public String getDC_PUBLIC_8002 (){return "select * from es_dc_public where stype='8002' and pkey = ? for update ";}
	
	public String getGET_CARD_BY_USERID_AND_MONEY (){return "select * from es_card_info where goods_id = ? and  to_userid = ? and card_price = ? and ship_state= '"+Consts.SHIP_STATE_5+"' and state='"+Consts.CARD_INFO_STATE_0+"' and type_code = ?  and eff_date<"+ DBTUtil.current()+" and exp_date>"+DBTUtil.current()+"  and rownum<2 ";}
	
	public String getGET_CARDNOTNS_BY_USERID_AND_MONEY (){return "select * from es_card_info where to_userid = ? and card_price = ? and ship_state= '"+Consts.SHIP_STATE_5+"' and state='"+Consts.CARD_INFO_STATE_0+"' and type_code = ?  and eff_date<"+DBTUtil.current()+" and exp_date>"+DBTUtil.current()+"  and rownum<2 ";}
	
	public String getGET_TRANSFER_PRICE (){return "select sum(card_price) from es_card_info a where 1=1";}
	
	public String getGET_LANID_BY_ACCNBR (){return "select lan_id from es_access_prod_inst where acc_nbr = ?";}
	
	public String getGET_GOODSID_BY_CARDID (){return "select b.goods_id from es_card_info a,es_order b where a.order_id = b.order_id and a.card_id  = ?";}
	
	public String getGET_CARD_BY_ORDERID (){return "select * from es_card_info a where sec_order_id = ? ";}
	
	public String getGOODS_GET_CARD_INFO (){return "select * from es_card_info where order_id = ? ";}
	
	public String getRETURNED_CARDS (){return "update es_card_info set to_userid = '',deal_time="+DBTUtil.current()+" where sec_order_id = ? ";}
	
	public String getCHECK_UNAVAILABLE_CARD (){return "select * from es_card_info a where ( a.state <> ? or a.sec_order_id is not null)  ";}
	
	public String getOCCUPY_CARDS (){return "update es_card_info set sec_order_id = ? where 1=1";}
	
	public String getCARD_INFO_INSERT (){return "insert into es_card_info (card_id, card_code,card_password," +
			" card_price,create_date,state,type_code,batch_id,card_type,import_userid) values (S_ES_CARD_INFO.nextval, ? ,? ,? ,? ,? ,? ,? ,? ,?)";}
	
	public String getINSERT_FROMCARD_LOG (){return "insert into es_card_info (card_id,batch_id,card_type,comments,eff_date," +
			" exp_date,create_date,card_price,state,type_code,import_userid,card_code,card_password" +
			") values (S_ES_CARD_INFO.nextval, ? ,? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?)";}
	
	public String getIS_EXISTS_EXCARD (){return "select count(1) shit from es_card_info where  card_code = ? and type_code = ?";}
	
	public String getIS_EXISTS_TXTCARD (){return "select count(1) from es_card_info where  card_code = ? and type_code = ?";}
	
	public String getGET_GOODS_LIST (){return "select a.goods_id goods_id, a.name || decode(a.effective_area_flag, '0', '【全国】', '1', '【省】') goods_name," +
			" a.mktprice goods_price from es_goods a, es_goods_type b where a.type_id = b.type_id and a.disabled = '0'" +
			" and a.market_enable = 1 and b.type_code = ?  and a.goods_id in (select area.goods_id from es_goods_area area where area.goods_id = a.goods_id and area.state = 1 group by area.goods_id "+ManagerUtils.apSFromCond("area")+")";}
	
	public String getLIST_CAT_COMPLEX (){return "select * from es_cat_complex";}
	
	public String getFIND_CATCOMP (){return "select count(goods_id) from es_cat_complex where goods_id =? and cat_id =? ";}
	
	public String getFIND_LIST_BY_CATID (){return "select cat_id,goods_id,goods_name,manual from es_cat_complex where cat_id =? ";}
	
	public String getDEL_BY_CATID (){return "delete from es_cat_complex where cat_id =? and goods_id=?";}
	
	public String getDELETE_COMMENTS (){return "update comments set disabled='true' where 1 = 1";}
	
	public String getCLEAN_COMMENTS (){return "delete  from  comments   where 1=1 ";}
	
	public String getGET_COMMENTS (){return "select * from comments where comment_id=?";}
	
	public String getGET_COMMENTS_BY_FOR_COMMENT_ID (){return "select * from comments where for_comment_id = ? order by time desc";}
	
	public String getCOMMENTS_REVERT (){return "update comments set disabled='false' where 1=1 ";}
	
	public String getPAGE_COMMENTS_DISPLAY_0 (){return "select * from comments where for_comment_id is null and display='true' and object_id = ? and object_type=? order by time desc";}
	
	public String getPAGE_COMMENTS_DISPLAY_1 (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments where for_comment_id is null and display='true' and object_id = ? and object_type=? "+ManagerUtils.apSFromCond("")+" ) and display='true' order by time desc ";}
	
	public String getCOMMENTS_LIST_ALL_0 (){return "select * from comments where for_comment_id is null and display='true' and object_id = ? and commenttype=? order by time desc";}
	
	public String getCOMMENTS_LIST_ALL_1 (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments where for_comment_id is null and display='true' and object_id = ? and commenttype=? "+ManagerUtils.apSFromCond("")+" ) and display='true' order by time desc ";}
	
	public String getPAGE_COMMENTS_0 (){return "select c.*, case c.commenttype when 'wssdetails' then g.name when 'goods' then g.name when 'article' " + 
			" then a.title end name from es_comments c left join es_goods g on g.goods_id = c.object_id and g.source_from = c.source_from left join es_article a " + 
			" on a.id = c.object_id and a.source_from = c.source_from where  for_comment_id is null and c.disabled='false' and object_type=? and c.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSQL_ID_LIST (){return "select * from es_comments c where c.source_from = '" + ManagerUtils.getSourceFrom() + "' and for_comment_id in " + 
			" (select comment_id from es_comments ec where for_comment_id is null and c.source_from  = ec.source_from  and display='true' and object_type=? ) " ;}
	
	public String getICOUNT_COMMENTS (){return "select count(*) cnt from es_comments c left join es_goods g on  g.goods_id = c.object_id and g.source_from = '" + ManagerUtils.getSourceFrom() + "' " + 
			" left join es_article a on a.id = c.object_id and a.source_from = '" + ManagerUtils.getSourceFrom() + 
			"' where  for_comment_id is null and c.disabled='false' and object_type=? ";}
	
	public String getPAGE_COMMENTS_TRASH (){return "select * from comments where for_comment_id is null and disabled='true' and object_type=? order by time desc";}
	
	public String getCOMMENTS_SQL_ID_LIST (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments where for_comment_id is null and display='true' and object_type=?) order by time desc ";}
	
	public String getPAGE_COMMENTS_DISPLAY (){return "select * from comments where for_comment_id is null and display='true' and object_id = ? and object_type=? and commenttype = ? order by time desc";}
	
	public String getDISPLAY_SQL_ID_LIST (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments where for_comment_id is null and display='true' and object_id = ? and object_type=? and commenttype = ?) and display='true' order by time desc ";}
	
	public String getPAGE_COMMENTS_DISPLAY_01 (){return "select * from comments where for_comment_id is null and display='true' and author_id = ? order by time desc";}
	
	public String getDISPLAY_PAGE_COMMENTS_DISPLAY_01 (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments where for_comment_id is null and display='true' and author_id = ? ) and display='true' order by time desc ";}
	
	public String getGET_COMMENT (){return "select * from comments where for_comment_id is null and author_id = ? and order_id=? and object_id =? order by time desc";}
	
	public String getCOMMENT_SQL_ID_LIST (){return "select * from es_comments where for_comment_id in (select comment_id from es_comments " + 
	" where for_comment_id is null and display='true' and author_id = ? ) and display='true' order by time desc ";}
	
	public String getGET_LIST_COMMENTS (){return "select * from comments where for_comment_id is null and author_id = ? and object_type = ? order by time desc";}
	
	public String getCOUT_OBJECT_GRADE (){return "select grade, count(0) num  from comments where object_type ='discuss' and commenttype=? and object_id =? and   display='true'  and for_comment_id is null group by grade";}
	
	public String getCOUT_OBEJCT_NUM (){return "select object_type, count(0) num  from comments where commenttype=? and object_id =?  and   display='true'  and for_comment_id is null group by object_type";}
	
	public String getGOODS_COUPONS (){return "select c.pmt_id, cpns_id, cpns_name,cpns_prefix, cpns_status, pmt_time_begin, pmt_time_end, cpns_type, cpns_gen_quantity, " + 
			" cpns_point from es_coupons c  left join es_promotion p on c.pmt_id=p.pmt_id and c.source_from = p.source_from where c.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSAVE_EXCHANGE (){return "update coupons set cpns_point = ?,exchangabled=1 ";}
	
	public String getGOODS_COUPONS_DEL (){return "delete from coupons where 1=1";}
	
	public String getGOODS_COUPONS_DEL_0 (){return "delete from promotion where 1 = 1";}
	
	public String getLIST_CAN_EXCHANGE (){return "select * from coupons where cpns_type = '1' and (cpns_point is null or cpns_point = 0) and cpns_status = '1'";}
	
	public String getLIST_EXCHANGE (){return "select c.pmt_id,c.cpns_id,c.cpns_name,c.cpns_prefix,c.cpns_type,c.cpns_point,c.cpns_gen_quantity, " + 
		" c.cpns_status,c.userid,p.pmt_time_begin,p.pmt_time_end from es_coupons c left join es_promotion p on c.pmt_id=p.pmt_id and p.source_from = c.source_from " + 
		" where c.source_from = '" + ManagerUtils.getSourceFrom() + "' and cpns_type='1' and cpns_point > 0 and cpns_status='1' AND pmt_time_begin <= " + DBTUtil.current() + 
	    " and pmt_time_end >"+ DBTUtil.current();}
	
	public String getGET_COUPONS_BY_ID (){return "select * from coupons where cpns_id = ?";}
	
	public String getCOUPONS_UPDATE (){return "update coupons set cpns_point = null where 1=1";}
	
	public String getUPDATE_USETIMES (){return "update es_member_coupon t set t.memc_used_times=t.memc_used_times+? where t.memc_code=? and t.member_id=?";}
	
	public String getHAS_COUPONUSE_TIMES (){return "select count(*) from es_member_coupon t where t.memc_used_times<? and t.memc_code=? and t.member_id=?";}
	
	public String getUPDATE_COUPON_NAME (){return "update es_coupons c set c.cpns_name=?,c.cpns_status=? where c.cpns_id=?";}
	
	public String getGET_FAVORITE_BY_MEMBER_ID (){return "select g.*, f.favorite_id from es_favorite f left join es_goods g on g.goods_id = f.goods_id where f.member_id = ?";}
	
	public String getFAVORITE_DEL (){return "delete from favorite where favorite_id = ?";}
	
	public String getGET_FAVORITE_BY_MEMBER_ID_0 (){return "select * from favorite where member_id=?";}
	
	public String getCOUNT_FAVBY_MEM_IDANDGOODS_ID (){return "select count(*) from es_favorite f where f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.member_id = ? and f.goods_id = ? and f.favorite_type = ?";}
	
	public String getLIST_FAV_GOODS (){return "select g.*, f.favorite_id from es_favorite f left join es_goods g on g.goods_id = f.goods_id  where g.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.member_id = ? and f.FAVORITE_TYPE='goods'";}
	
	public String getLIST_FAVSUPPLER (){return "select g.*, f.favorite_id from es_favorite f left join es_supplier g on g.supplier_id = f.goods_id  where g.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.member_id = ? and f.FAVORITE_TYPE='suppler'";}
	
	public String getLIST_FAVPARTNER (){return "select g.*, f.favorite_id from es_favorite f left join es_partner g on g.partner_id = f.goods_id  where g.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.member_id = ? and f.FAVORITE_TYPE='partner'";}
	
	public String getFREEOFFER_CATEGORY_CLEAN (){return "delete  from  freeoffer_category   where 1=1";}
	
	public String getFREEOFFER_CATEGORY_UPDATE (){return "update freeoffer_category set disabled=1  where 1=1";}
	
	public String getFREE_OFFER_CATEGORY (){return "select * from freeoffer_category where cat_id=?";}
	
	public String getGET_FREE_OFFER_CATEGORY_LIST (){return "select * from freeoffer_category";}
	
	public String getFREEOFFER_CATEGORY_REVERT (){return "update freeoffer_category set disabled=0  where 1=1";}
	
	public String getFREEOFFER_CATEGORY (){return "select * from freeoffer_category";}
	
	public String getFREEOFFER_DELETE (){return "update freeoffer set disabled=1  where 1=1";}
	
	public String getFREEOFFER_REVERT (){return "update freeoffer set disabled=0  where 1=1";}
	
	public String getFREEOFFER_DELETE_BY_IDS (){return "delete  from  freeoffer where 1=1";}
	
	public String getGET_FREEOFFER_LIST (){return "select * from freeoffer where fo_id=?";}
	
	public String getGET_FREEOFFER_LIST_BY_DATE (){return "select f.*,'' cat_name from freeoffer f where disabled=0 and publishable=0 #cond  order by sorder asc ";}
	
	public String getGET_FREEOFFER_BY_CATALOG (){return "select fo.*,c.cat_name as cat_name from es_freeoffer fo left join es_freeoffer_category c on fo.fo_category_id=c.cat_id ";}
	
	public String getGET_FREEOFFER_BY_CATALOG_0 (){return "select fo.*,c.cat_name as cat_name from " +
			"es_freeoffer fo ,es_freeoffer_category c where " +
			"fo.fo_category_id = c.cat_id and fo.source_from = c.source_from and c.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getGET_ORDER_GIFT (){return "select * from order_gift where order_id = ?";}
	
	public String getGET_FREEOFFER_BY_FO_ID (){return "select * from freeoffer where 1=1";}
	
	public String getGET_GOODS_ADJUNCT_BY_GOODS_ID (){return "select * from es_goods_adjunct where goods_id = ?";}
	
	public String getGOODS_ADJUNCT_DELETE (){return "delete from goods_adjunct where goods_id = ?";}
	
	public String getGET_GOODS_COUNT (){return "select count(1) from es_goods where 1=1  and sn = ?";}
	
	public String getLIST_AGREEMENT (){return "select * from es_goods_agreement where status='OOA'";}
	
	public String getDELETE_AGREEMENT (){return "update  es_goods_agreement set status ='OOX' where agreement_id=?";}
	
	public String getGOODS_AGREEMENT (){return "select *　from es_goods_agreement where agreement_id = ?";}
	
	public String getGET_CONTROL_BY_ID (){return "select * from es_agreement_controls  where agreement_id =?";}
	
	public String getAGREEMENT_CONTROLS_DEL (){return "delete from es_agreement_controls where agreement_id=?";}
	
	public String getGET_NAME_BY_ID (){return "select agreement_name es_goods_agreement where agreement_id = ?";}
	
	public String getGET_IMAGE_FILE_Y_ID (){return "select image_file from goods where 1=1";}
	
	public String getGET_GOODS_AREA_LANID_GOODSID (){return "select * from es_goods_area a where a.goods_id = ? and lan_id = ?";}
	
	public String getGOODS_STATE_UPDATE (){return "UPDATE es_goods set audit_state = ? where goods_id = ?";}
	
	public String getGET_GOODS_AREA (){return "select distinct(lan_id),goods_id from es_goods_area where state<>? and goods_id=?";}
	
	public String getGET_GOODS_AREA_0 (){return "select lan_id,comments,state from es_goods_area where goods_id = ?";}
	
	public String getSERACH_GOODS_AREA_SUCC (){return "select lan_id,state from es_goods_area where goods_id = ? and state=?";}
	
	public String getSERACH_GOODS_WAITAUDIT (){return "select lan_id,state from es_goods_area where goods_id = ?";}
	
	public String getEDIT_AREA_STATE (){return "update es_goods_area set state='"+Consts.AREA_STATE_2+"' where lan_id=? and goods_id=?";}
	
	public String getGET_GOODS_AREA_BYID (){return "select * from es_goods_area where goods_id=? and lan_id=?";}
	
	public String getGOODS_AREA_DEL (){return "delete from es_goods_area a where a.goods_id = ? and lan_id = ? ";}
	
	public String getFIND_CATNODES_BY_PARENTID (){return "select a.* from es_goods_cat a where a.parent_id=? order by cat_order asc ";}
	
	public String getGOODS_CAT_CHECK_NAME (){return "select count(0) from es_goods_cat where name=? and cat_id!=?";}
	
	public String getGET_GOODS_CAT_COUNT(){return "select count(0) from es_goods_cat where parent_id = ?";}
	
	public String getGET_GOODS_COUNT_BY_CAT_ID (){return "select count(0) from es_goods where cat_id = ?";}
	
	public String getGOODS_CAT_DELETE (){return "delete from  es_goods_cat   where cat_id=?";}
	
	public String getGET_GOODS_CAT_BY_ID (){return "select * from es_goods_cat  where cat_id=?";}
	
	public String getGET_COMPLEX_GOODS_BY_CATID (){return "select b.* from es_cat_complex g,es_goods b where g.goods_id = b.goods_id  and b.disabled='"+Consts.GOODS_DISABLED_0+"' and b.market_enable=1 ";}
	
	public String getGET_GOODS_CAT_PATH (){return "select cat_path from es_goods_cat where cat_id = ?";}
	
	public String getGET_CATPATH_BY_GOODSID (){return "select cc.cat_path from " +
			"es_goods gg,es_goods_cat cc where gg.goods_id = ? and " +
			"gg.cat_id = cc.cat_id and gg.source_from = cc.source_from and cc.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getGET_CATS_BY_GOODSID (){return "select g.* from es_goods_cat g where 1=1 ";}
	
	public String getGET_HOT_CATS_BY_CATID (){return "select cat_id,name,hot_name from es_goods_cat g where 1=1 ";}
	
	public String getGET_CATS_BY_COND (){return "select g.* from es_goods_cat g where 1=1 ";}
	
	public String getGET_PARENTID_BYID (){return "select parent_id from es_goods_cat where cat_id = ?";}
	
	public String getLIST_CHILDREN (){return "select c.*,'' type_name from es_goods_cat c where parent_id=? ORDER BY cat_id";}
	
	public String getLIST_ALL_CHILDREN (){return "select c.*,l0.lv_id lvid0,l1.lv_id lvid1,l2.lv_id lvid2,l3.lv_id lvid3,t.name as type_name  from  es_goods_cat "
		+" c LEFT JOIN es_goods_lv_cat l0 ON(l0.cat_id=c.cat_id AND l0.lv_id=0 and l0.source_from = c.source_from)"
		+" LEFT JOIN es_goods_lv_cat l1 ON(l1.cat_id=c.cat_id AND l1.lv_id=1 and l1.source_from = c.source_from)"
		+" LEFT JOIN es_goods_lv_cat l2 ON(l2.cat_id=c.cat_id AND l2.lv_id=2 and l2.source_from = c.source_from)"
		+" LEFT JOIN es_goods_lv_cat l3 ON(l3.cat_id=c.cat_id AND l3.lv_id=3 and l3.source_from = c.source_from)"
		+ " left join es_goods_type t on c.type_id = t.type_id and t.source_from = c.source_from where 1=1 and c.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getLIST_ALL_CHILDREN_BY_AGENTNO (){return "select c.*,l0.lv_id lvid0,l1.lv_id lvid1,l2.lv_id lvid2,l3.lv_id lvid3,t.name as type_name  from es_goods_cat "
		+" c LEFT JOIN es_goods_lv_cat l0 ON(l0.cat_id=c.cat_id AND l0.lv_id=0 and l0.source_from ='"+ManagerUtils.getSourceFrom()+"' and c.source_from ='"+ManagerUtils.getSourceFrom()+"')"
		+" LEFT JOIN es_goods_lv_cat l1 ON(l1.cat_id=c.cat_id AND l1.lv_id=1 and l1.source_from ='"+ManagerUtils.getSourceFrom()+"'  and c.source_from ='"+ManagerUtils.getSourceFrom()+"')"
		+" LEFT JOIN es_goods_lv_cat l2 ON(l2.cat_id=c.cat_id AND l2.lv_id=2 and l2.source_from ='"+ManagerUtils.getSourceFrom()+"'  and c.source_from ='"+ManagerUtils.getSourceFrom()+"')"
		+" LEFT JOIN es_goods_lv_cat l3 ON(l3.cat_id=c.cat_id AND l3.lv_id=3 and l3.source_from ='"+ManagerUtils.getSourceFrom()+"'  and c.source_from ='"+ManagerUtils.getSourceFrom()+"')"
		+ " left join es_goods_type t on c.type_id = t.type_id and t.source_from ='"+ManagerUtils.getSourceFrom()+"'  where t.source_from = c.source_from and 1=1 "+ManagerUtils.apSFromCond("c")+ " order by parent_id,cat_order";}
	
	public String getLIST_ALL_CHILDRENFOR_SECOND (){return "select c.*,t.name as type_name  from es_goods_cat "
		+" c LEFT JOIN es_goods_lv_cat l0 ON(l0.cat_id=c.cat_id AND l0.lv_id=0 "+ManagerUtils.apSFromCond("10")+")"
		+" LEFT JOIN es_goods_lv_cat l1 ON(l1.cat_id=c.cat_id AND l1.lv_id=1 "+ManagerUtils.apSFromCond("11")+")"
		+" LEFT JOIN es_goods_lv_cat l2 ON(l2.cat_id=c.cat_id AND l2.lv_id=2 "+ManagerUtils.apSFromCond("12")+")"
		+" LEFT JOIN es_goods_lv_cat l3 ON(l3.cat_id=c.cat_id AND l3.lv_id=3 "+ManagerUtils.apSFromCond("13")+")"
		+ "  left join es_goods_type t on c.type_id = t.type_id "+ManagerUtils.apSFromCond("t")+"  "
		+ " order by parent_id,cat_order";}
	
	public String getGET_GOODS_CAT_BY_ID_0 (){return "select * from es_goods_cat  where cat_id=?";}
	
	public String getUPDATE_GOODS_CAT_PATH (){return "update es_goods_cat set  cat_path=? where  cat_id=?";}
	
	public String getGOODS_CAT_SELECT_BY_ID (){return "select * from es_goods_cat where cat_id=?";}
	
	public String getSAVE_SORT (){return "update  es_goods_cat  set cat_order=? where cat_id=?";}
	
	public String getGET_HOT_LIST (){return "select a.*,'' type_name  from es_goods_cat a where hot_type =1";}
	
	public String getGET_HOT_LIST_COUNT (){return "SELECT COUNT(1) FROM  ES_GOODS_CAT WHERE hot_type =1";}
	
	public String getDELET_EMEMBERLV_BY_ID (){return "delete from es_goods_lv_cat where cat_id=? and lv_id=?";}
	
	public String getGET_CAT_BY_GOODID (){return "select * from es_goods_cat where  cat_id in(select cat_id from es_goods where goods_id=? "+ManagerUtils.apSFromCond("")+") "+ManagerUtils.apSFromCond("")+"";}
	
	public String getGET_GOODS_CAT_BY_ID_1 (){return "select * from es_goods_cat where cat_id=?";}
	
	public String getLIST_MEMBER_LV (){return "SELECT lv_id,NAME FROM es_member_lv where source_from ='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIST_ALL_COMPLEX (){return "select   g.goods_id, g.sn, g.name, g.image_default image, g.price, g.mktprice from es_goods g where ( g.goods_id in (select goods_2 from es_goods_complex where goods_1= ? "+ManagerUtils.apSFromCond("")+" )  "+ManagerUtils.apSFromCond("")+" )"+  
			" or ( goods_id in( select goods_1 from es_goods_complex where goods_2= ? and manual='both' "+ManagerUtils.apSFromCond("")+")  "+ManagerUtils.apSFromCond("")+")";}
	public String getGLOBAL_GOODS_COMPLEX (){return "delete from goods_complex where goods_1 = ?";}
	
	public String getLIST_COMPLEX (){return "select g.*,c.manual from es_goods g, es_goods_complex c where g.source_from=c.source_from and g.source_from='" + ManagerUtils.getSourceFrom() + "' and goods_2 =goods_id and  goods_1=? ";}
	
	public String getLIST_GOODS_COMPLEX (){return "select a.goods_1 goods_id,b.goods_id goods_complex_id,b.name,b.sn,b.image_default image,b.price,b.mktprice "+
			" from es_goods_complex a,es_goods b where a.goods_2=b.goods_id";}
	
	public String getGOODS_FIELD_MANAGER (){return "select * from goods_field where type_id=?";}
	
	public String getGET_GOODS_FIELD (){return "select * from goods_field where field_id=?";}
	
	public String getDELETE_GOODS_FIELD (){return "delete from  goods_field where field_id=?";}
	
	public String getGOODS_FIELD_SELECT (){return "select * from goods_field where 1=1";}
	
	public String getGOODS_FIELD_DELETE_BY_TYPE_ID (){return "delete from goods_field where 1=1";}
	
	public String getGOODS_UPDATE_IMG (){return "update es_goods set image_file=? ,image_default=? where goods_id=?";}
	
	public String getGET_WARHOUSEINVENTORY (){return "select * from es_warehouse_inventory where inventory_id=?";}
	
	public String getQRY_GOODSINVENTORY_BY_INVENTORYID (){return "select i.*,g.name as goodsName from es_warehouse_goods_inventory i,es_goods g where i.goods_id=g.goods_id and i.inventory_id=? "+ManagerUtils.apSFromCond("i")+" "+ManagerUtils.apSFromCond("g")+"";}
	
	public String getQRY_BY_HOUSEID (){return "select * from es_warehouse_inventory where house_id=?";}
	
	public String getQRY_WARHOUSEINVENTORY_PAGE (){return "select wi.*,w.house_name from es_warehouse_inventory wi,es_warehouse w where " +
			"wi.house_id=w.house_id and wi.source_from = w.source_from and w.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getQRY_HOUSE_GOODS (){return "select distinct g.goods_id,g.name,g.price,g.mktprice,g.store from es_goods g,es_warehouse_seat_goods ws where g.goods_id=ws.goods_id and ws.house_id=?";}
	
	public String getDEL_GOODSINVENTORY_BY_INVENTORYID (){return "delete from es_warehouse_goods_inventory where inventory_id=?";}
	
	public String getUPDATE_INVENTORY_NAME (){return "update es_warehouse_inventory set name=? where inventory_id=?";}
	
	public String getUPDATE_WARHOUSEINVENTORY_STATUS (){return "update es_warehouse_inventory set status=?,remark=?,confirm_id=?,confirm_name=?,confirm_time="+DBTUtil.current()+" where inventory_id=?";}
	
	public String getGOOD_SISINVENTORY (){return "select count(*) from es_warehouse_inventory wi,es_warehouse_goods_inventory wgi where wi.inventory_id=wgi.inventory_id and wi.status=0 and wgi.goods_id=?";}
	
	public String getGET_CONTROL_NAME (){return "select pname from es_dc_public where stype='6661' and pkey=?";}
	
	public String getGET_CONTROL_BY_ID_0 (){return "select * from es_goods_control  where goods_id =?" ;}
	
	public String getIS_OVERQTY_OR_MONEY_SELECT (){return "select control_value,control_type,sub_control_type from es_Agreement_controls where agreement_id=? and control_lan_code=?";}
	
	public String getIS_OVERQTY_OR_MONEY_COUNT (){return "select count(*) from es_Agreement_controls where agreement_id=? and control_lan_code=?";}
	
	public String getIS_OVERQTY_OR_MONEY_COUNT_VALUE (){return "select count(control_value) from es_goods_control where goods_id in("+
				" select goods_id from es_goods ) and control_lan_code =? and control_type=?";}
	
	public String getGET_NAMELIST_BY_ID (){return "select agreement_name from es_goods_agreement where agreement_id =?";}
	
	public String getDEL_ALL_CONTROL_BY_AGTID (){return "delete from es_goods_control where goods_id=?";}
	
	public String getPRICE_PRIV_DEL_BY_GOODS_ID (){return "delete from es_price_priv where goods_id=?";}
	
	public String getMEMBER_LV_SELECT (){return "select lv_id  from es_member_lv where source_from = ?";}
	
	public String getGOODS_GET_BY_TYPE_CODE (){return "select name,goods_id from es_goods where type_code=? ";}
	
	public String getADD_GOODS_COUNT (){return "update goods_cat set goods_count=goods_count+1 where 1=1";}
	
	public String getGET_GOODS_EDITDATA (){
		return "select a.*,b.stype_code,p.product_id from es_goods a  "
				+ " left join es_product p on a.goods_id=p.goods_id and a.source_from=p.source_from "
				+ " left join es_goods_stype b on a.stype_id=b.stype_id and  a.source_from = b.source_from "
				+ " where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=?";}
	
	public String getGOODS_SELECT_BY_ID(){return "select g.* from es_goods g where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.goods_id=?";}
	
	public String getGOODS_CAT_SELECT (){return "select g.*,'' agent_name,b.name as brand_name ,t.name as type_name,c.name as cat_name from es_goods "
			+ " g left join es_goods_cat "
			+ " c on g.cat_id=c.cat_id and g.source_from = c.source_from left join es_brand"
			+ " b on g.brand_id = b.brand_id and b.disabled=0 and g.source_from = b.source_from left join es_goods_type"
			+ " t on g.type_id =t.type_id and t.source_from = g.source_from";}
	
	public String getGOODS_SELECT_DIALOG(){
		return "select t.*,'' agent_name,(select c.name from es_goods_cat c where c.cat_id=t.cat_id and c.source_from='"+ManagerUtils.getSourceFrom()+"') cat_name, "+
				"(select gt.name from es_goods_type gt where gt.type_id=t.type_id and gt.source_from='"+ManagerUtils.getSourceFrom()+"') type_name, "+
				"(select b.name from es_brand b where t.brand_id=b.brand_id and b.source_from='"+ManagerUtils.getSourceFrom()+"') brand_name from es_goods t "+
				"where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.disabled=0 ";
	}
	
	public String getGOODS_CAT_SELECT_0 (){return "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name from es_goods "
			+ " g left join es_goods_cat "
			+ " c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+" left join es_brand "
			+ " b on g.brand_id = b.brand_id "+ManagerUtils.apSFromCond("b")+" left join es_goods_type "
			+ " t on g.type_id =t.type_id "+ManagerUtils.apSFromCond("t")+""+ManagerUtils.apSFromCond("g")+"";}
	
	public String getSEARCH_GOODS_0_1 (){return "select g.goods_id,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.store,g.market_enable,g.create_time,t.name as type_name," +
			"t.have_stock as have_stock,t.have_price as have_price," 
			+"   g.creator_user  apply_userid,  (select username from es_adminuser u where u.userid = g.creator_user and g.source_from = u.source_from) apply_username,g.audit_state "
			+" from es_goods g "
			+ " left join es_goods_type t on g.type_id=t.type_id and g.source_from = t.source_from where 1=1 and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'" ;}
	
	//广东联通ecs
	public String getSEARCH_GOODS_0_2 (){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct g.goods_id,g.sku,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.mktprice,");
		sql.append("g.market_enable,g.create_time,t.name as type_name,t.have_stock as have_stock," );
		sql.append("t.have_price as have_price,c.name cat_name,b.name brand_name,g.creator_user  apply_userid, g.audit_state ,");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user and g.source_from = u.source_from) apply_username ");
		sql.append("from es_goods g  left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"");
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
		sql.append("left join es_goods_co egc  on egc.goods_id = g.goods_id "+ManagerUtils.apSFromCond("egc")+"");
		sql.append("left join es_goods_org  ego  on ego.party_id = egc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		
		//sql.append("left join es_brand_model m on m.brand_code = b.brand_code "+ManagerUtils.apSFromCond("m")+"");//add by liqingyi
		sql.append("and g.source_from = t.source_from  ");
//		sql.append("and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'");
		return sql.toString();
	}
	
	public String getSEARCH_GOODS_TAOBAO_ECS (){return "select g.sku, g.goods_id, g.type_id, g.crm_offer_id, g.sn, g.name,"
			+ " g.price, g.market_enable, g.create_time, t.name as type_name, t.have_stock as have_stock,"
			+ " t.have_price as have_price, c.name cat_name, b.name as brand_name, g.creator_user as apply_userid,"
			+ " (select username from es_adminuser u where u.userid = g.creator_user and g.source_from = u.source_from) as apply_username,"
			+ " g.audit_state, p.p_code, p.sn as barcode"
			+ " from es_goods g "
			+ " left join es_goods_package p on g.goods_id = p.goods_id "+ManagerUtils.apSFromCond("p")+""
			+ " left join es_goods_type t on g.type_id=t.type_id and g.source_from = t.source_from "+ManagerUtils.apSFromCond("t")+""
			+ " left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+""
			+ " left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+""
			+ " where 1=1 "
			+ " and g.type = 'goods'"
			+ " and g.disabled = '"+Consts.GOODS_DISABLED_0+"'"
			+ " and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	

	public String getSEARCH_GOODS_6 (){return "select  g.goods_id,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.store,g.market_enable,g.create_time,t.name as type_name,state," +
		    " t.have_stock as have_stock,t.have_price as have_price,g.creator_user  apply_userid,  " +
		    " (select username from es_adminuser u where u.userid = g.creator_user and u.source_from = g.source_from) apply_username,g.audit_state " +
		    "   from es_goods g "
		    + " left join es_goods_type t on g.type_id=t.type_id and t.source_from = g.source_from " ;}

	//广东联通ecs
	public String getSEARCH_GOODS_6_1 (){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct g.goods_id,g.sku,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.mktprice,g.market_enable,");
		sql.append("g.create_time,t.name as type_name,state,t.have_stock as have_stock,t.have_price as have_price,");
		sql.append("g.creator_user  apply_userid, c.name cat_name,b.name brand_name,g.audit_state, ");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user and u.source_from = g.source_from) apply_username ");
		sql.append("from es_goods g left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" ");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+" ");
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
		sql.append("left join es_goods_co egc  on egc.goods_id = g.goods_id "+ManagerUtils.apSFromCond("egc")+"");
		sql.append("left join es_goods_org  ego  on ego.party_id = egc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		
		sql.append("and t.source_from = g.source_from ");
	return sql.toString();
	}
	//广东联通ecs
	public String getSEARCH_PRODUCTS_6_1(){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct p.product_id, g.name as gname,g.cat_id as cid,p.sku ,g.goods_id,g.model_code,");
		sql.append(" g.type_id,g.crm_offer_id,g.sn,p.name,g.price,g.market_enable,g.create_time,");
		sql.append(" t.name as type_name,g.state,t.have_stock as have_stock, t.have_price as have_price,");
		sql.append(" g.creator_user as apply_userid, c.name as cat_name,b.name as brand_name,g.audit_state,");
		sql.append(" u.username as apply_username ");
		sql.append(" from es_product p ");
		sql.append(" inner join es_goods g on p.goods_id=g.goods_id and g.source_from = p.source_from");
		sql.append(" left join es_goods_type t on g.type_id=t.type_id and t.source_from = g.source_from");
		sql.append(" left join es_goods_cat c on g.cat_id=c.cat_id and c.source_from = g.source_from");
		sql.append(" left join es_brand b on g.brand_id=b.brand_id and b.source_from = g.source_from");
//		sql.append(" left join es_brand_model m on m.brand_code = b.brand_code and m.source_from = g.source_from");//add by liqingyi
		sql.append(" left join es_product_co epc on epc.product_id = p.product_id and epc.source_from = g.source_from");
		sql.append(" left join es_goods_org ego on ego.party_id = epc.org_id and ego.source_from = g.source_from");
		sql.append(" left join es_adminuser u on u.userid = g.creator_user and u.source_from = g.source_from");
		sql.append(" where 1 = 1 ");
		
		return sql.toString();
	}
	
	//广东联通ecs
	public String getSEARCH_PRODUCTS_6_1_LIST(){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct p.product_id, g.name as gname,g.cat_id as cid,p.sku ,g.goods_id,g.model_code,");
		sql.append(" g.type_id,g.crm_offer_id,g.sn,p.name,g.price,g.market_enable,g.create_time,");
		sql.append(" t.name as type_name,g.state,t.have_stock as have_stock, t.have_price as have_price,");
		sql.append(" g.creator_user as apply_userid, c.name as cat_name,b.name as brand_name,g.audit_state,");
		sql.append(" u.username as apply_username,g.last_modify ");
		sql.append(" from es_product p ");
		sql.append(" inner join es_goods g on p.goods_id=g.goods_id and g.source_from = p.source_from");
		sql.append(" left join es_goods_type t on g.type_id=t.type_id and t.source_from = g.source_from");
		sql.append(" left join es_goods_cat c on g.cat_id=c.cat_id and c.source_from = g.source_from");
		sql.append(" left join es_brand b on g.brand_id=b.brand_id and b.source_from = g.source_from");
//		sql.append(" left join es_brand_model m on m.brand_code = b.brand_code and m.source_from = g.source_from");//add by liqingyi
		sql.append(" left join es_product_co epc on epc.product_id = p.product_id and epc.source_from = g.source_from");
		sql.append(" left join es_goods_org ego on ego.party_id = epc.org_id and ego.source_from = g.source_from");
		sql.append(" left join es_adminuser u on u.userid = g.creator_user and u.source_from = g.source_from");
		sql.append(" where 1 = 1 ");
			
			return sql.toString();
	}
		
	public String getSEARCH_PRODUCTS_0_2() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct p.product_id, g.name as gname,g.cat_id as cid,p.sku ,g.goods_id,");
		sql.append(" g.type_id,g.crm_offer_id,g.sn,p.name,g.price,g.market_enable,g.create_time,");
		sql.append(" t.name as type_name,g.state,t.have_stock as have_stock, t.have_price as have_price,");
		sql.append(" g.creator_user as apply_userid, c.name as cat_name,b.name as brand_name,g.audit_state,");
		sql.append(" u.username as apply_username ");
		sql.append(" from es_product p ");
		sql.append(" inner join es_goods g on p.goods_id=g.goods_id and g.source_from = p.source_from");
		sql.append(" left join es_goods_type t on g.type_id=t.type_id and t.source_from = g.source_from");
		sql.append(" left join es_goods_cat c on g.cat_id=c.cat_id and c.source_from = g.source_from");
		sql.append(" left join es_brand b on g.brand_id=b.brand_id and b.source_from = g.source_from");
//		sql.append(" left join es_brand_model m on m.brand_code = b.brand_code and m.source_from = g.source_from");//add by liqingyi
		sql.append(" left join es_product_co epc on epc.product_id = p.product_id and epc.source_from = g.source_from");
		sql.append(" left join es_goods_org ego on ego.party_id = epc.org_id and ego.source_from = g.source_from");
		sql.append(" left join es_adminuser u on u.userid = g.creator_user and u.source_from = g.source_from");
		sql.append(" where 1=1 and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'" );
			
		return sql.toString();
		
		}
	
	//广东联通ecs
	public String getSEARCH_PRODUCTS_OTHER_1 (){
		StringBuilder sql = new StringBuilder();
		sql.append("select p.sku, g.name as gname,g.cat_id as cid,g.goods_id,p.product_id ,g.type_id,g.crm_offer_id,");
		sql.append("g.sn,p.name,g.price,g.market_enable,g.create_time,t.name as type_name,state,");
		sql.append("t.have_stock as have_stock,t.have_price as have_price,g.creator_user  apply_userid, c.name cat_name,");
		sql.append("b.name brand_name,g.audit_state ");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user) apply_username ");
		sql.append("from es_product p left join es_goods g on p.goods_id=g.goods_id "+ManagerUtils.apSFromCond("g"));
		sql.append("left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" ");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c"));
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b"));
	//	sql.append(" left join es_brand_model m on m.brand_code = b.brand_code "+ManagerUtils.apSFromCond("m"));//add by liqingyi
		sql.append(" left join es_product_co epc  on epc.product_id = p.product_id "+ManagerUtils.apSFromCond("epc"));
		sql.append(" left join es_goods_org  ego  on ego.party_id = epc.org_id "+ManagerUtils.apSFromCond("ego"));
		sql.append("left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"");
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
		sql.append(" left join es_product_co epc  on epc.product_id = p.product_id "+ManagerUtils.apSFromCond("epc")+"");
		sql.append(" left join es_goods_org  ego  on ego.party_id = epc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		sql.append("where  g.disabled = '"+Consts.GOODS_DISABLED_0+"' ");
		return sql.toString();
		}
	
	
	public String getSEARCH_GOODS_OTHER (){
		StringBuilder sql = new StringBuilder("select  g.goods_id,g.type_id,g.crm_offer_id,g.sn,g.name,");
		sql.append("g.price,g.store,g.market_enable,g.create_time,t.name as type_name,state,");
		sql.append("t.have_stock as have_stock,t.have_price as have_price,g.creator_user  apply_userid,g.audit_state ,");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user) apply_username ");
		sql.append("from es_goods g left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append(" left join es_product p on g.goods_id = p.goods_id and p.source_from = g.source_from");
		sql.append(" left join es_product_co epc  on epc.product_id = p.product_id "+ManagerUtils.apSFromCond("epc")+"");
		sql.append(" left join es_goods_org  ego  on ego.party_id = epc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		sql.append(" where  g.disabled = '"+Consts.GOODS_DISABLED_0+"' ");
		return sql.toString();
	}
	
	//广东联通ecs
	public String getSEARCH_GOODS_OTHER_1 (){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct g.goods_id,g.sku,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.market_enable,g.create_time,");
		sql.append("t.name as type_name,state,t.have_stock as have_stock,t.have_price as have_price, ");
		sql.append("g.creator_user  apply_userid,c.name cat_name,b.name brand_name, g.audit_state");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user) apply_username ");
		sql.append("from es_goods ");
		sql.append("left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"");
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
		sql.append("left join es_goods_co egc  on egc.goods_id = g.goods_id "+ManagerUtils.apSFromCond("egc")+"");
		sql.append("left join es_goods_org  ego  on ego.party_id = egc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		
		return sql.toString();
	}
	public String getSEARCH_AUDIT_GOODS (){return "select  a.create_time, (select username from es_adminuser u where u.userid  = a.staff_no ) apply_username , a.goods_id,a.sn,a.name,a.price,a.store,a.effect_date,a.fail_date from es_goods a "
			+ " where a.audit_state = '"+Consts.GOODS_AUDIT_NOT+"'";}
	
	public String getSEARCH_APPLY_GOODS_0_1 (){return "select a.goods_id as goods_id,a.sn as sn,a.name as goods_name,a.create_time as create_time,a.store,c.name as type_name,area.state,a.type_id as type_id,a.type_code as type_code,a.crm_offer_id as crm_offer_id from es_goods a,es_goods_type c,es_goods_area area where 1=1 and a.source_from='"+ManagerUtils.getSourceFrom()+"' and area.source_from='"+ManagerUtils.getSourceFrom()+"' and c.source_from ='"+ManagerUtils.getSourceFrom()+"'  and a.goods_id = area.goods_id and a.type_id =c.type_id ";}
	
	public String getSEARCH_APPLY_GOODS_2 (){return "select a.goods_id as goods_id,a.sn as sn,a.name as goods_name,a.create_time as create_time,a.store,c.name as type_name,a.type_id as type_id,a.type_code as type_code,a.crm_offer_id as crm_offer_id from es_goods a,"
			+ " es_goods_type c,es_goods_usage b where a.goods_id = b.goods_id  and a.type_id=c.type_id ";}
	
	public String getSEARCH_APPLY_GOODS_3 (){return "select a.goods_id as goods_id,a.sn as sn,a.name as goods_name,a.create_time as create_time,a.store,c.name as type_name,a.type_id as type_id,a.type_code as type_code,a.crm_offer_id as crm_offer_id from es_goods a,es_goods_type c "//area.lan_id,  es_goods_area area
			+ "  where a.disabled = '"+Consts.GOODS_DISABLED_0+"' and a.market_enable = 1 and a.type_id=c.type_id and a.goods_id in (select area.goods_id from es_goods_area area where area.goods_id=a.goods_id and area.state='"+Consts.AREA_STATE_1+"' group by area.goods_id ) ";}
	
	public String getLIST_GOODS_APPLY_0_1 (){return "select a.goods_id,a.name from es_goods a,es_goods_area b, es_goods_type t where a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and 1=2";}
	
	public String getLIST_GOODS_APPLY_2 (){return "select a.goods_id,a.name from es_goods a,es_goods_usage u, es_goods_type t where a.goods_id=u.goods_id ";}
	
	public String getLIST_GOODS_APPLY_3 (){return "select a.goods_id,a.name from es_goods a, es_goods_type t where  t.type_id = a.type_id and a.disabled = '" 
			+ Consts.GOODS_DISABLED_0+"' and a.market_enable=1 and a.goods_id in " + 
			" (select area.goods_id from es_goods_area area where area.goods_id=a.goods_id and " + 
			" area.state='"+Consts.AREA_STATE_1+"' "+ManagerUtils.apSFromCond("area")+" group by area.goods_id ) ";}
	
	public String getSEARCH_APPLYYES_GOODS (){return "select a.sn,a.goods_id,a.name as goods_name,a.cat_id,c.name as type_name,a.goods_type,b.create_date,b.state,b.stock_num,b.baseline_num,b.usageid,a.type_id as type_id,a.crm_offer_id as crm_offer_id "
			+ " from es_goods a,es_goods_usage b,es_goods_type c "
			+ " where a.source_from ='"+ManagerUtils.getSourceFrom()+"' and  c.type_id=a.type_id and a.goods_id = b.goods_id and b.userid = ? and a.disabled='"+Consts.GOODS_DISABLED_0+"' and a.market_enable=1";}
    
	public String getAPPLY_SUCC_NUM (){return "select a.sn,a.goods_id  from es_goods a, es_goods_usage b, es_goods_type c where a.source_from ='"+ManagerUtils.getSourceFrom()+"' and  c.type_id = a.type_id   and a.goods_id = b.goods_id   and b.userid = ?  and a.disabled = '0'   and a.market_enable = 1";}
	
	public String getGET_TRASH_NUM (){return "select g.goods_id,g.name from es_goods g where g.disabled=1 and g.staff_no=? ";}//1回收站,0正常
	
	public String getCHECK_GOODS_SN (){return " select count(1) num from es_goods g where g.sn = ?";}
	
	
	public String getPAGE_TRASH (){
		StringBuilder sql = new StringBuilder();
		sql.append("select g.goods_id,g.name,g.sn,g.sku,g.price,t.have_price,g.create_time,t.name as type_name ,");
		sql.append(" (case when g.type ='product' then '货品' when g.type='goods' then '商品' else '0' end ) type ");
		sql.append("from es_goods g  left join es_goods_type t on g.type_id = t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append("and g.source_from = t.source_from ");
		sql.append("where 1 = 1 and g.disabled = 1 and g.source_from = '" + ManagerUtils.getSourceFrom() + "'");
		return sql.toString();
	}
	
	public String getGOODS_UPDATE_DISABLED_1 (){return "update  goods set disabled='"+Consts.GOODS_DISABLED_1+"'";}
	
	public String getDEL_GOODS_BY_STAFFNO (){return "update  goods set disabled='"+Consts.GOODS_DISABLED_1+"',market_enable=0 where staff_no =?";}
	
	public String getGOODS_REVERT (){return "update  goods set disabled='"+Consts.GOODS_DISABLED_0+"'  where 1=1";}
	
	public String getDEL_GOODS_BY_ID (){return "delete  from goods  where 1=1";}
	
	public String getDEL_GOODS_AREA_BY_ID (){return "delete from es_goods_area where 1=1";}
	
	public String getGOODS_SELECT_BY_IDS (){return "select * from goods where goods_id in ";}
	
	public String getLIMITBUYGOODS_SELECT_BY_ID (){return "select * from ES_LIMITBUY_GOODS where  LIMITBUYID=?";}
	
	public String getGOODS_SELECT_1_0 (){return "select count(0) from es_goods where market_enable=1 and  disabled = '" + Consts.GOODS_DISABLED_0+"'";}
	
	public String getGOODS_SELECT_0_0 (){return "select count(0) from es_goods where market_enable=0 and  disabled = '"+Consts.GOODS_DISABLED_0+"'";}
	
	public String getGOODS_SELECT_0_1 (){return "select count(0) from es_goods where market_enable=0 and  disabled = '"+Consts.GOODS_DISABLED_1+"'";}
	
	public String getGOODS_COMMENTS_COUNT (){return "select count(0) from es_comments t where for_comment_id is null  and commenttype='goods' ";}
	
	public String getGOODS_SELECT_DISABLE_0 (){return "select * from goods where disabled = '"+Consts.GOODS_DISABLED_0+"'";}
	
	//此脚本不适用开启规格的商品【开启规格，es_goods与es_product表的关系是一对多】
	public String getLIST_GOODS(){
		return "select c.cat_path,g.goods_id,g.name,g.sku,g.model_code,g.sn,g.brand_id,b.name brand_name,b.brand_code,g.cat_id,c.name cat_name,g.type_id,t.name type_name,g.weight,g.market_enable,g.brief,g.price,g.cost,g.mktprice,g.have_spec,g.create_time,g.crm_offer_id,g.sku, " +
				"pi.pc_image_default,pi.pc_image_file,pi.mbl_image_default,pi.mbl_image_file,pi.wx_image_default,pi.wx_image_file,pi.other_image_default,pi.other_image_file, "+
					" g.view_count,g.buy_count,g.store,g.point,g.isgroup,g.islimit,g.grade,g.image_default,g.image_file,g.state,g.supper_id,g.audit_state,g.simple_name,g.specifications,staff_no apply_userid,g.params,g.p1,g.p2,g.p3,g.p4,g.p5,g.p6,g.p7,g.p8,g.p9,g.p10,p.product_id,p.color,b.brand_code, " +
					"(select t.type_code from es_goods_type t where t.type_id=g.type_id and t.source_from='"+ManagerUtils.getSourceFrom()+"') type_code," +
					"(select username from es_adminuser u where u.userid =g.staff_no and g.source_from='"+ManagerUtils.getSourceFrom()+"') apply_username," +
					"(select company_name from es_supplier s where s.supplier_id=g.supper_id and s.source_from='"+ManagerUtils.getSourceFrom()+"') agent_name," +
					"(select pname from es_dc_public dc where dc.stype = '10002' and dc.source_from = '"+ManagerUtils.getSourceFrom()+"' and dc.pkey = p.color) color_name," +
					"(select m.model_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') model_name," +
					"(select m.machine_code from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_code," +
					"(select m.machine_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_name " +
				    "from es_goods g left join es_goods_plantform_info pi on g.goods_id=pi.goods_id "+ManagerUtils.apSFromCond("pi")+" left join es_product p on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("p")+" left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+
				    " left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+" left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled = ?";}
	
	//此脚本使用与开启规格的商品
	public String getLIST_GOODS_FOR_CORE(){
		return "select g.goods_id,g.name,g.sku,g.model_code,g.sn,g.brand_id,b.name brand_name,b.brand_code,g.cat_id,c.name cat_name,g.type_id,t.name type_name,g.weight,g.market_enable,g.brief,g.price,g.cost,g.mktprice,g.have_spec,g.create_time,g.crm_offer_id,g.sku,g.creator_user, " +
					" g.view_count,g.buy_count,g.store,g.point,g.isgroup,g.islimit,g.grade,g.image_default,g.image_file,g.state,g.supper_id,g.audit_state,g.simple_name,g.specifications,staff_no apply_userid,g.params,g.p1,g.p2,g.p3,g.p4,g.p5,g.p6,g.p7,g.p8,g.p9,g.p10,b.brand_code, " +
					"pi.pc_image_default,pi.mbl_image_default,pi.wx_image_default,pi.other_image_default, "+
					"(select t.type_code from es_goods_type t where t.type_id=g.type_id and t.source_from='"+ManagerUtils.getSourceFrom()+"') type_code," +
					"(select username from es_adminuser u where u.userid =g.staff_no and g.source_from='"+ManagerUtils.getSourceFrom()+"') apply_username," +
					"(select company_name from es_supplier s where s.supplier_id=g.supper_id and s.source_from='"+ManagerUtils.getSourceFrom()+"') agent_name," +
					"(select m.model_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') model_name," +
					"(select m.machine_code from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_code," +
					"(select m.machine_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_name " +
				    "from es_goods g left join es_goods_plantform_info pi on g.goods_id=pi.goods_id "+ManagerUtils.apSFromCond("pi")+" left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+
				    " left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+" left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.audit_state='00A' and g.market_enable=1 and g.disabled = ?";}
	
	public String getLIST_PRODUCTS (){
		return "select c.cat_path,p.product_id,p.name,p.sku,p.sn,p.color,g.model_code,g.brand_id,b.name brand_name,g.cat_id,c.name cat_name,g.type_id,t.name type_name,g.weight,g.market_enable,g.brief,g.price,g.cost,g.mktprice,g.have_spec,g.create_time, "+
				" g.view_count,g.buy_count,g.store,g.point,g.isgroup,g.islimit,g.grade,g.image_default,g.image_file,g.type_code,g.state,g.supper_id,g.audit_state,g.simple_name,g.specifications,staff_no apply_userid,g.params, " +
				"(select username from es_adminuser u where u.userid =g.staff_no "+ManagerUtils.apSFromCond("g")+") apply_username,(select company_name from es_supplier s where s.supplier_id=g.supper_id "+ManagerUtils.apSFromCond("s")+") agent_name " +
				"from es_product p left join es_goods g on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("g")+" left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+" left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+
				" left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.type='product' and g.disabled = ? ";}


	public String getLIST_V_PRODUCTS (){return " select * from v_product c where 1=1 ";
			
	}
	public String getLIST_GOODS_REL_PRODUCTS (){
		return  "select r.a_goods_id goods_id,g.goods_id prod_goods_id,g.name,g.sku,g.model_code,g.sn,g.brand_id,b.name brand_name,b.brand_code,g.cat_id,c.name cat_name,g.type_id,t.name type_name,g.weight,g.market_enable,g.brief,g.price,g.cost,g.mktprice,g.have_spec,g.create_time,g.crm_offer_id,g.sku, "+
				" g.view_count,g.buy_count,g.store,g.point,g.isgroup,g.islimit,g.grade,g.image_default,g.image_file,g.type_code,g.state,g.supper_id,g.audit_state,g.simple_name,g.specifications,staff_no apply_userid,g.params,g.p1,g.p2,g.p3,g.p4,g.p5,g.p6,g.p7,g.p8,g.p9,g.p10,p.product_id,p.color,b.brand_code, " +
				"(select username from es_adminuser u where u.userid =g.staff_no and g.source_from='"+ManagerUtils.getSourceFrom()+"') apply_username," +
				"(select company_name from es_supplier s where s.supplier_id=g.supper_id and s.source_from='"+ManagerUtils.getSourceFrom()+"') agent_name," +
				"(select pname from es_dc_public dc where dc.stype = '10002' and dc.source_from = '"+ManagerUtils.getSourceFrom()+"' and dc.pkey = p.color) color_name," +
				"(select m.model_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') model_name," +
				"(select m.machine_code from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_code," +
				"(select m.machine_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_name " +
			    "from es_goods_rel r,es_goods g left join es_product p on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("p")+" left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+" left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" " +
			    		"where r.product_id=p.product_id and r.rel_type = 'PRO_REL_GOODS' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled = ? ";}
	
	public String getLIST_GOODS_REL_PRODUCTS_GOODSID (){
		return  "select c.cat_path,r.a_goods_id goods_id,g.goods_id prod_goods_id,g.name,p.sku,g.model_code,g.sn,g.brand_id,b.name brand_name,b.brand_code,g.cat_id,c.name cat_name,g.type_id,t.name type_name,g.weight,g.market_enable,g.brief,g.price,g.cost,g.mktprice,g.have_spec,g.create_time,g.crm_offer_id,g.sku, "+
				" g.view_count,g.buy_count,g.store,g.point,g.isgroup,g.islimit,g.grade,g.image_default,g.image_file,g.state,g.supper_id,g.audit_state,g.simple_name,g.specifications,staff_no apply_userid,g.params,g.p1,g.p2,g.p3,g.p4,g.p5,g.p6,g.p7,g.p8,g.p9,g.p10,p.product_id,p.color,b.brand_code, " +
				"(select t.type_code from es_goods_type t where t.type_id=g.type_id) type_code," +
				"(select username from es_adminuser u where u.userid =g.staff_no and g.source_from='"+ManagerUtils.getSourceFrom()+"') apply_username," +
				"(select company_name from es_supplier s where s.supplier_id=g.supper_id and s.source_from='"+ManagerUtils.getSourceFrom()+"') agent_name," +
				"(select pname from es_dc_public dc where dc.stype = '10002' and dc.source_from = '"+ManagerUtils.getSourceFrom()+"' and dc.pkey = p.color) color_name," +
				"(select m.model_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') model_name," +
				"(select m.machine_code from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_code," +
				"(select m.machine_name from es_brand_model m where g.model_code = m.model_code and b.brand_code = m.brand_code and m.source_from='"+ManagerUtils.getSourceFrom()+"') machine_name " +
			    "from es_goods_rel r,es_goods g left join es_product p on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("p")+" left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+
			    " left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+" " +
			    "where r.product_id=p.product_id and r.rel_type = 'PRO_REL_GOODS' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled = ? and r.a_goods_id=? ";}

	public String getLIST_GOODS_JOIN_ADJUNCT (){return "select g.goods_id,g.name,g.sn,(select count(adjunct_id) from es_goods_adjunct a where a.goods_id=g.goods_id "+ManagerUtils.apSFromCond("a")+") adj_num from es_goods g where disabled = ?";}
	
	public String getLIST_GOODS_COMPLEX_COUNT (){return "select g.goods_id,count(c.goods_2) complex_num from es_goods g left join es_goods_complex c on c.goods_1=g.goods_id where g.disabled=? and g.source_from='"+ManagerUtils.getSourceFrom()+"' group by g.goods_id";}
	
	public String getSEARCH_HOT_GOODS (){return "select * from es_goods g where  g.disabled='" + Consts.GOODS_DISABLED_0 + "' and market_enable=1 ";}
	
	public String getALL_GOODS_NUM (){return "select g.goods_id,g.sn from es_goods g left join es_goods_type t on g.type_id = t.type_id  where 1 = 1 and exists (select 1 from es_goods_area b where b.goods_id = g.goods_id and g.disabled = '0')";}
	
	public String getALL_GOODS_NUM_0_1 (){return "select a.goods_id,a.name from es_goods a  left join es_goods_type t on a.type_id = t.type_id where 1=1 and exists (select 1 from es_goods_area b where b.goods_id=a.goods_id and a.disabled = '"+Consts.GOODS_DISABLED_0+"' and state = '"+Consts.AREA_STATE_1;}
	
	public String getALL_GOODS_AUDITNUM (){return "select a.goods_id,a.name from es_goods a,es_goods_area b,es_goods_type c where a.goods_id = b.goods_id and b.state='0' and a.disabled='"+Consts.GOODS_DISABLED_0+"' and a.market_enable=1 and a.type_id=c.type_id ";}
	
	public String getFIND_CRM_OFFERID (){return "select distinct a.offer_id ,a.offer_name  from es_cloud_info a where a.offer_id is not null and a.state=?";}
	
	public String getFIND_CRM_OFFERID_COUNT (){return "select count( distinct a.offer_id) from es_cloud_info a where a.offer_id is not null and a.state=?";}
	
	public String getFIND_CRM_OFFERID_2 (){return "select a.prod_offer_name offer_name, a.prod_offer_id offer_id from es_prod_offer a, es_prod_offer_region b where a.prod_offer_id = b.prod_offer_id   and a.prod_offer_id in (select c.prod_offer_id from es_offer_prod_rel c where c.element_type = '10A' and c.state = '10' and c.product_id = '80000045') and a.status_cd = '10' and a.offer_type = '11' and a.main_flag = '1' and a.is_new = 'T' ";}
	
	public String getFIND_CRM_OFFERID_2_COUNT (){return "select a.prod_offer_name offer_name, a.prod_offer_id offer_id from es_prod_offer a, es_prod_offer_region b where a.prod_offer_id = b.prod_offer_id   and b.common_region_id in('"+ ManagerUtils.getLanId()+"','"+Consts.LAN_PROV+"') and a.prod_offer_id in (select c.prod_offer_id from es_offer_prod_rel c where c.element_type = '10A' and c.state = '10' and c.product_id = '80000045') and a.status_cd = '10' and a.offer_type = '11' and a.main_flag = '1' and a.is_new = 'T' ";}
	
	public String getGET_GOODS_BY_TYPE (){return "select a.* from es_goods a,es_goods_type b where a.type_id = b.type_id " +
			" and a.disabled='0' and b.type_code = ? and a.mktprice  = ? and rownum<2";} //查询有效的
	
	public String getGROUNDING_GOODS (){return "SELECT COUNT(*) FROM es_goods a WHERE a.market_enable=1 AND disabled='"+Consts.GOODS_DISABLED_0+"' ";}
	
	public String getUNDER_CARRIAGE_GOODS (){return "SELECT COUNT(*) FROM es_goods a WHERE a.market_enable=0 AND disabled='"+Consts.GOODS_DISABLED_0+"'";}
	
	public String getGET_CATBY_GOODSID (){return "select cat_id from es_goods where goods_id = ? ";}
	
	public String getGET_LV_PRICE (){return "select l.price from es_goods_lv_price l " +
			" inner join es_product e on l.goodsid = e.goods_id " +
			" and l.productid = e.product_id and l.status = '00A' and l.lvid = '3' "+
			" where e.goods_id = ? ";}
	
	public String getGET_GOODS_BY_ID (){return "select * from es_goods where  goods_id=? and disabled=? and source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getGOODS_INTRO_GET (){return "select g.*,(select c.name from es_goods_cat c where c.cat_id=g.cat_id and c.source_from=g.source_from) cat_name,(select name from es_goods_type i where i.type_id=g.type_id and i.source_from=g.source_from) type_name," +
			"p.pc_image_default,p.pc_image_file,p.pc_remark,p.pc_intors,p.mbl_image_default,p.mbl_image_file,p.mbl_remark,p.mbl_intors,p.wx_image_default,p.wx_image_file,p.wx_remark,p.wx_intors " +
			"from es_goods g left join es_goods_plantform_info p on g.goods_id=p.goods_id and g.source_from=p.source_from where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.goods_id=?  and g.disabled=?";}
	
	public String getALL_GOODS (){return "select count(goods_id) from es_goods where 1=1";}
	
	public String getGET_SUPPLIER_ADMINUSERID (){return "select p.userid from es_supplier p where p.supplier_id=?";}
	
	public String getMARKET_ENABLE (){return "update es_goods set market_enable=? where goods_id=?";}
	
	public String getGET_GOODS_BY_PRODUCTID (){return "select g.* from es_goods g,es_product p where g.goods_id=p.goods_id " +
			"and p.product_id=? and g.source_from = p.source_from and p.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getGET_INTEREST_GOOD_BY_GOODIDS (){return "select * from es_goods_cat where cat_id in(select cat_id from es_goods where goods_id in(:replaceSql))";}
	
	public String getGET_INTEREST_GOODBYGOODIDS (){return "select * from es_goods where cat_id in(:instr)";}
	
	public String getJOIN_APPLY (){return "update es_goods set  audit_state='00M' where goods_id=? " ;}
	
	public String getAPPRECIATE_APPLY (){return "update es_goods set audit_state='00M' where goods_id=? " ;}
	
	public String getOUT_OFRE_GISTER (){return "select count(*) from es_product a where a.store =0";}
	
	public String getGOODS_TEMP_DEL (){return "delete from es_goods_temp where goods_id =  ? ";}
	
	public String getQRY_GOODS_TEMP (){return "select * from GOODS_TEMP  where goods_id=?";}
	
	public String getGET_PRICE_LIST (){return "select  (select name from es_member_lv c where a.role_type =c.lv_id "+ManagerUtils.apSFromCond("c")+" ) price_type_name,"+
	    " (select b.price from es_goods_lv_price b where goodsid =? and lvid = a.role_type "+ManagerUtils.apSFromCond("b")+") price_val " +
	    " from   es_price_priv a  where a.state='00A' and  a.goods_id =?";}
	
	public String getQUERY_GOODS_RULES (){return "select * from es_goods_action_rule where goods_id = ? and service_code= ? and disabled = '"+Consts.GOODS_RULES_ENABLE+"'";}
	
	public String getGOODS_ACTION_RULE_COUNT (){return "select count(*) from es_goods_action_rule where goods_id =? and action_code=? and service_code=?";}
	
	public String getGET_GOODS_LV_PRICE (){return "select t.* from es_goods_lv_price t where t.goodsid=? and t.lvid=? and t.status='00A'";}
	
	public String getQRY_GOODS_CONTROL_BY_GOODSID (){return "select t.*,r.local_name from es_goods_control t left join es_regions r on t.control_lan_code=r.lan_code where t.status='00A' and t.goods_id=? and t.source_from = '"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getGET_GOODS_PLUGIN (){return "select * from es_goods_plugin p where source_from in (select cf_value from es_config_info where cf_id ='SOURCE_FROM' ) and p.disabled =0";}
	
	public String getLIST_ALL_GOODS (){return "select goods_id,name,sn from es_goods where 1=1";}
	
	public String getLIST_SEL_GOODS (){return "select goods_id,name,sn from es_goods  where goods_id in(";}
	
	public String getLIST_GOODS_BY_STYPESID (){return "select　goods_id,name,sn from es_goods where stype_id in(";}
	
	public String getLIST_GOODS_BY_TYPESID (){return "select　goods_id,name,sn from es_goods where type_id in(";}
	
	public String getGET_GOODS_BY_CRMOFFERID (){return "SELECT b.*,a.crm_offer_id  FROM es_goods a, es_product b, es_goods_area c  WHERE a.goods_id = b.goods_id  AND a.goods_id = c.goods_id  AND c.lan_id = ?  AND a.crm_offer_id = ?";}
	
	public String getQRY_STYPE_LIST (){return "SELECT stype_id key,name value FROM es_goods_stype WHERE disabled = '0' AND source_from = ?";}
	
	public String getQRY_SYS_SERVS (){return "SELECT * FROM es_goods WHERE stype_id = ?";}
	
	public String getFIND_GOODS_BY_GOODSSN (){return "SELECT * FROM es_goods WHERE sn = upper(?)";}
	
	public String getLIST_GOODS_BY_USERID (){return "SELECT a.sn,a.name,a.goods_id,b.price,a.store,b.product_id productid FROM es_goods a,es_product b,es_price_priv c  WHERE a.goods_id=b.goods_id and a.source_from=b.source_from and a.source_from=c.source_from AND a.goods_id=c.goods_id AND c.role_type=0 AND c.state='00A' ";}
	
	public String getGOODS_PRICE_SELECT (){return " select a.goods_id,a.price_id,a.price_val,a.price_type,a.role_type, "+
		 " (select name from es_member_lv l where l.lv_id=a.role_type "+ManagerUtils.apSFromCond("l")+" ) role_name, "+
		 " a.prod_num_a,a.prod_num_z,a.state from es_goods_price a where goods_id = ?";}
	
	public String getGOODS_PRICE_DELETE (){return "delete from es_goods_price where goods_id = ?";}
	
	public String getGET_PROP_LIST_BY_CAT (){return "select g.* from es_goods g where g.disabled=0 and g.market_enable=1 and g.cat_id in(select c.cat_id from es_goods_cat c where c.cat_path like ?)";}
	
	public String getGET_SPECLIST_BY_CATID (){return "select s.* from es_product s, es_goods g  where s.goods_id=g.goods_id and s.source_from = g.source_from  and g.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getGEN_BASE_SQL (){return "select g.*,p.pc_image_default,p.pc_image_file,p.pc_remark,p.mbl_image_default,p.mbl_image_file,p.mbl_remark,p.wx_image_default,p.wx_image_file,p.wx_remark," +
			" p.other_image_default,p.other_image_file,p.other_remark from es_goods g left join es_goods_plantform_info p on g.goods_id=p.goods_id and g.source_from=p.source_from " +
			" where g.goods_type = 'normal' and g.audit_state='00A' and g.disabled=0 and market_enable=1 and g.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getGET_QUERY_SQL (){return "select cat_id,name cat_name,image cat_image from es_goods_cat where parent_id =0 order by cat_order asc";}
	
	public String getGEN_PRODUCT_SQL (){return "select g.*,p.product_id from es_goods g,es_product p,es_price_priv pp where " +
			"g.goods_id=p.goods_id and p.goods_id=pp.goods_id and pp.role_type=? and pp.state='00A' " +
			"and g.goods_type = 'normal' and g.disabled=0 and g.market_enable=1 and g.source_from = p.source_from and g.source_from=pp.source_from and " +
			"p.source_from = pp.source_from and pp.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getCOUNT_BY_CATID (){return "select count(0) from es_goods g where g.disabled=0 and market_enable=1 ";}
	
	public String getGOODS_ID_INTAGS (){return "select rel_id from tag_rel where 1=1";}
	
	public String getGOODS_SELECT_BY_0_1 (){return "select g.* from es_goods  g where g.goods_type = 'normal' and g.disabled=0 and market_enable=1 ";}
	
	public String getGOODS_COUNT_BY_0_1 (){return "select count(0) from es_goods g where g.disabled=0 and market_enable=1 ";}
	
	public String getGOODS_TYPE_BY_0 (){return "select * from es_goods_type where disabled=0 ";}
	
	public String getGOODS_TYPE_BY_0_0 (){return "select * from es_goods_type where disabled=0 and type='"+Consts.ECS_QUERY_TYPE_GOOD+"'";}
	
	public String getGOODS_TYPE_BY_0_1 (){return "select * from es_goods_type where disabled=0 and type='"+Consts.ECS_QUERY_TYPE_PRODUCT+"'";}
	
	public String getLIST_STYPE (){return "select * from ES_GOODS_STYPE t where disabled=0 AND T.Parent_Id='-1'";}
	
	public String getCHILD_LIST_STYPE (){return "select * from es_goods_stype t where t.parent_id=? and t.disabled='0'";}
	
	public String getPAGE_TYPE_0 (){return "select * from es_goods_type where disabled=0 ";}
	
	public String getPAGE_TYPE_1 (){return "select * from es_goods_type where disabled=1 ";}
	
	public String getGOODS_TYPE_BY_ID (){return "select * from es_goods_type where type_id=?";}
	
	public String getGET_GOODS_TYPE_LIST (){return "select * from es_goods_type where disabled=?";}
	
	public String getGET_TAG_NAME (){return "select tag_name from es_tags where tag_id = ?";}
	
	public String getGET_BRAND_LIST_BY_TYPE_ID (){return "select b.name name ,b.brand_id brand_id,0 as num from es_type_brand tb inner join es_brand b  on b.brand_id = tb.brand_id and b.source_from=tb.source_from where tb.type_id=? and b.disabled=0 and tb.source_from='" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getLIST_BY_TYPE_ID (){return "select b.* from es_type_brand tb inner join es_brand b  on b.brand_id = tb.brand_id where tb.type_id=? and tb.source_from='"+ManagerUtils.getSourceFrom()+"' and b.disabled=0";}
	
	public String getGET_PARAMS_BY_TYPEID (){return "select params from es_goods_type where disabled=0 and type_id= ?";}
	
	public String getTYPE_BRAND_DEL (){return "delete from es_type_brand where type_id = ?";}
	
	public String getTYPE_BRAND_INSERT (){return "insert into es_type_brand(type_id,brand_id,source_from) values(?,?,?)";}
	
	public String getGOODS_GET_BY_TYPE_ID (){return "select count(0) from es_goods where 1=1";}
	
	public String getGOODS_CAT_COUNT (){return "select count(0) from es_goods_cat where 1=1";}
	
	public String getGOODS_TYPE_UPDATE_BY_TYPE_ID (){return "update  es_goods_type set disabled=1  where  1=1 ";}
	
	public String getGOODS_TYPE_DEL_BY_TYPE_ID (){return "delete from es_goods_type where 1=1";}
	
	public String getGOODS_TYPE_UPDATE_0 (){return "update  es_goods_type set disabled=0  where 1=1";}
	
	public String getCHECK_NAME (){return "select count(0) from es_goods_type where name=? and type_id!=? and disabled=0";}
	
	public String getGET_GOODS_USAGEBYGOODSID (){return "select * from es_goods_usage where goods_id=? and userid=? and usageid=?";}
	
	public String getFIND_GOODS_USAGELIST (){return "select * from es_goods_usage where goods_id=? and userid=? and state=?";}
	
	public String getGET_LAN_BY_GOODSID (){return "select a.lan_id, (select lan_name from es_lan l where l.lan_id = a.lan_id) lan_name  from es_goods_area a  where a.goods_id = ? and a.state = '" + Consts.AREA_STATE_1 + "'";}
	
	public String getGET_CARD_STOCK (){return "select u.usageid, u.state, cloud_stock.stock stock_num, u.goods_id, u.userid, g.name goods_name, t.type_id, t.type_code, t.name type_name, u.baseline_num "
			+ " from es_goods_usage u, es_goods g, es_goods_type t, "
			+ " (select  t.to_userid,t.offer_id, count(*) stock from es_cloud_info t where t.state = '" + Consts.CLOUD_INFO_STATE_0 + "' group by t.to_userid, t.offer_id) cloud_stock"
			+ " where u.goods_id = g.goods_id"
			+ " and g.type_id = t.type_id"
			+ " and t.type_code = 'CLOUD'"
			+ " and cloud_stock.to_userid = u.userid"
			+ " and g.crm_offer_id = cloud_stock.offer_id"
			+ " and u.state = '" + Consts.GOODS_USAGE_STATE_0 + "' "
			+ " union all "
			+ " select u.usageid, u.state, card_stock.stock stock_num, u.goods_id, u.userid, g.name goods_name, t.type_id, t.type_code, t.name type_name, u.baseline_num"
			+ " from es_goods_usage u, es_goods g, es_goods_type t ,"
			+ " (select t.to_userid, t.card_price, count(*) stock from es_card_info t where t.state = '" + Consts.CARD_INFO_STATE_0 + "' " +
					" and t.type_code = 'CLOUD'" +  // 充值卡
					" group by t.to_userid, t.card_price) card_stock"
			+ " where u.goods_id = g.goods_id"
			+ " and g.type_id = t.type_id"
			+ " and t.type_code = 'CLOUD'" 
			+ " and card_stock.to_userid = u.userid"
			+ " and u.state = '" + Consts.GOODS_USAGE_STATE_0 + "'"
			+ " and g.price = card_stock.card_price"
			+ " union all "
			+ " select u.usageid, u.state, card_stock.stock stock_num, u.goods_id, u.userid, g.name goods_name, t.type_id, t.type_code, t.name type_name, u.baseline_num"
			+ " from es_goods_usage u, es_goods g, es_goods_type t ,"
			+ " (select t.to_userid, t.card_price, count(*) stock from es_card_info t where t.state = '" + Consts.CARD_INFO_STATE_0 + "' " +
					" and t.type_code = 'RECHARGE_CARD'" + //流量卡
					" group by t.to_userid, t.card_price) card_stock"
			+ " where u.goods_id = g.goods_id"
			+ " and g.type_id = t.type_id"
			+ " and t.type_code = 'RECHARGE_CARD'" 
			+ " and card_stock.to_userid = u.userid"
			+ " and u.state = '" + Consts.GOODS_USAGE_STATE_0 + "'"
			+ " and g.price = card_stock.card_price";}
	
	public String getGOODS_RULE_COUNT (){return "select count(*) from es_goods_action_rule where disabled =0 and  goods_id =? and service_code=?";}
	
	public String getGOODS_USERS_SELECT (){return "select goodsUser.*,goods.name goods_name,(select a.service_offer_name from es_service_offer a where a.service_id=goodsUser.service_code) service_code_name  from es_goods_Users goodsUser inner join es_goods goods on goodsUser.goods_id =goods.goods_id where goods.source_from='"+ManagerUtils.getSourceFrom()+"' AND goodsUser.source_from='"+ManagerUtils.getSourceFrom()+"' AND goodsUser.disabled =0 and goodsUser.goods_id =? and goodsUser.user_type=? and goodsUser.service_code=?";}
	
	public String getGET_GROUP_NAME (){return "select group_name from es_order_group_def where group_id = ?";}
	
	public String getORDER_GROUP_ROLE_REL_SELECT (){return "select userid,role_id  from es_order_group_role_rel where group_id =?";}
	
	public String getADMINUSER_SELECT (){return "select distinct(userid),username,realname from es_adminuser where 1=1";}
	
	public String getGOODS_ALL_LIST_SELECT (){return "select goods_id,name,sn from es_goods where 1=1 ";}
	
	public String getORDER_GROUP_DEF_SELECT (){return "select * from es_order_group_def where disabled = 0 and group_type=?";}
	
	public String getGOODS_USER_LIST (){return "SELECT u.*,G.NAME GOODS_NAME," +
			    " (SELECT STATUS FROM es_goods_action_rule r WHERE R.GOODS_ID = u.Goods_Id AND " +
			    "  r.service_code = u.service_code AND r.Action_Code ='confirm' AND r.source_from='"+ManagerUtils.getSourceFrom()+"') QRTYPE," +
			    "  (SELECT STATUS FROM es_goods_action_rule r WHERE " +
			    " r.Goods_Id = u.Goods_Id AND r.service_code = U.service_code AND r.Action_Code ='pay' AND r.source_from='"+ManagerUtils.getSourceFrom()+"' ) payType," +
			    " (SELECT STATUS FROM ES_GOODS_ACTION_RULE  r WHERE R.GOODS_ID = u.Goods_Id AND R.service_code = u.service_code AND r.Action_Code ='accept' AND r.source_from='"+ManagerUtils.getSourceFrom()+"')ACCEPTTYPE," +
			    " (SELECT STATUS FROM ES_GOODS_ACTION_RULE r WHERE R.GOODS_ID = u.Goods_Id AND " +
			    " r.service_code = u.service_code AND R.ACTION_CODE ='delivery' AND r.source_from='"+ManagerUtils.getSourceFrom()+"' ) deliveryType," +
			    " (SELECT STATUS FROM ES_GOODS_ACTION_RULE r WHERE " +
			    " r.Goods_Id = u.Goods_Id AND R.service_code = u.service_code AND R.ACTION_CODE ='query' AND r.source_from='"+ManagerUtils.getSourceFrom()+"' ) queryType" +
			    "  FROM ES_GOODS_USERS u,ES_GOODS G WHERE u.source_from='"+ManagerUtils.getSourceFrom()+"' AND g.source_from='"+ManagerUtils.getSourceFrom()+"'" +
			    " AND U.Goods_Id=G.GOODS_ID AND U.Disabled='0' ";}
	
	public String getGOODS_USER_COUNT (){return " SELECT COUNT(1) FROM ES_GOODS_USERS u,ES_GOODS G WHERE 1=1  AND U.Goods_Id=G.GOODS_ID AND U.Disabled='0' and u.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() +"'";}
	
	public String getDEL_GOODS_USER (){return "update es_goods_users set disabled =1 where goods_id =? and user_type=? and service_code=?";}
	
	public String getGET_GOODS_USERCOUNT (){return "select count(*) from es_goods_users where disabled=0 and goods_id=? and service_code=?";}
	
	public String getGOODS_UPDATE_ISGROUP (){return "update es_goods set isgroup=? where goods_id=?";}
	
	public String getGROUP_BUY_COUNT_DEL (){return "delete from group_buy_count where groupid=? ";}
	
	public String getGROUP_BUY_DEL (){return "delete from group_buy where groupid=? ";}
	
	public String getGROUP_BUY_COUNT_DEL_0 (){return "delete from group_buy_count where groupid=? ";}
	
	public String getGROUP_BUY_SELECT_BY_ID (){return "select  * from group_buy where groupid=?";}
	
	public String getGROUP_BUY_GET (){return "select  * from group_buy where 1=1 order by add_time desc";}
	
	public String getGROUP_BUY_GET_0 (){return "select  a.*,b.name from es_group_buy a,es_goods b where a.goodsid=b.goods_id and a.source_from=b.source_from and a.source_from='"+ ManagerUtils.getSourceFrom()+"' ";}
	
	public String getGROUP_BUY_SELECT (){return "select gb.* ,g.cat_id catid ,g.price oldprice  from es_group_buy gb , es_goods g where g.source_from='"+ManagerUtils.getSourceFrom()+"' and gb.source_from='"+ManagerUtils.getSourceFrom()+"' and gb.goodsid = g.goods_id and  gb.start_time<"+DBTUtil.current()+" and gb.end_time>"+DBTUtil.current()+"  and gb.disabled=0 order by add_time desc";}
	
	public String getGROUP_BUY_COUNT_SELECT (){return "select * from group_buy_count where groupid =? ";}
	
	public String getGROUP_BUY_COUNT_SELECT_0 (){return "select * from es_group_buy_count where groupid in(select groupid from es_group_buy where disabled=0  and start_time<"+ DBTUtil.current()+" and end_time>"+DBTUtil.current()+") ";}
	
	public String getGROUP_BUY_UPDATE (){return "update group_buy set buy_count= buy_count+? where groupid=?";}
	
	public String getGROUP_BUY_UPDATE_0 (){return "update es_group_buy set disabled=? where groupid=? ";}
	
	public String getINVOICE_APPLY_DEL (){return "delete from invoice_apply where id=?";}
	
	public String getINVOICE_APPLY_SELECT (){return "select i.*,o.sn ordersn,o.order_amount amount from es_invoice_apply i,es_order o where o.order_id=i.orderid i.id =?";}
	
	public String getINVOICE_APPLY_SELECT_0 (){return "select i.*,o.sn ordersn,o.order_amount amount from es_invoice_apply i, es_order o  order by add_time desc";}

	public String getGOODS_SELECT_BY_TYPE (){return "select g.* from es_goods g,es_goods_type b   where g.source_from='"+ManagerUtils.getSourceFrom()+"' and b.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled=0 and g.audit_state='"+Consts.GOODS_AUDIT_SUC+"' and  market_enable=1 and g.type_id = b.type_id  ";}

	public String getCAT_ID_SELECT (){return "select cat_id from es_goods_cat where 1=1 ";}
	
	public String getREL_ID_SELECT (){return "select rel_id from tag_rel where 1=1 ";}
	
	public String getLIST_MEMBER (){return "select i.*,o.sn ordersn,o.order_amount amount from  es_invoice_apply i,es_order o where o.member_id=? order by add_time desc";}
	
	public String getUPDATE_INVOICE_APPLY_STATE (){return "update invoice_apply set state=? where id=?";}
	
	public String getREFUSE_INVOICE_APPLY (){return "update invoice_apply set state=?,refuse_reson=? where id=?";}
	
	public String getADD_GOODS (){return "insert into limitbuy_goods (limitbuyid,goodsid,price)values(?,?,?)";}
	
	public String getDELETE_LIMITBUY_GOODS (){return "delete from limitbuy_goods where limitbuyid=?";}
	
	public String getDELETE_LIMITBUY (){return "delete from limitbuy where id=?";}
	
	public String getDELETE_LIMITBUY_GOODS_0 (){return "delete from limitbuy_goods where limitbuyid=?";}
	
	public String getLIMITBUY_GOODS (){return "SELECT a.*,b.name FROM es_limitbuy_goods a,es_goods b WHERE a.goodsid=b.goods_id and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getUPDATE_GOODS_LIMIT (){return "update es_goods set islimit=? where goods_id in(select goodsid from es_limitbuy_goods where limitbuyid=?)";}
	
	public String getGET_LIMITBUY_LIST (){return "select * from limitbuy order by add_time desc";}
	
	public String getGET_LIMITBUY_LIST_0 (){return "SELECT distinct a.* FROM es_limitbuy a,es_limitbuy_goods b WHERE a.id=b.limitbuyid and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIST_ENABLE (){return "select * from limitbuy where start_time<? and end_time>? and disabled = '0' order by add_time desc";}
	
	public String getLIST_ENABLE_1 (){return "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice  from es_limitbuy_goods lg  ,es_goods g where g.source_from='"+ManagerUtils.getSourceFrom()+"' and lg.goodsid= g.goods_id and lg.limitbuyid in (select id from es_limitbuy where disabled = '0' and start_time<? and end_time>?)";}
	
	public String getLIST_ENABLE_GOODS (){return "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice,lg.cart_num,lg.buy_num,lg.num  from es_limitbuy_goods lg  ,es_goods g where lg.goodsid= g.goods_id and g.source_from ='"+ManagerUtils.getSourceFrom()+"'  and  lg.limitbuyid in (select id from es_limitbuy where 1=1 and  disabled = '0' and start_time<"+DBTUtil.current()+" and end_time>"+DBTUtil.current()+")";}
	
	public String getLIMITBUY_SELECT_BY_ID (){return "select * from limitbuy where  id=?";}
	
	public String getLIMIT_BUY_SELECT (){return "select g.* ,lg.limitbuyid limitbuyid from es_limitbuy_goods lg  ,es_goods g where lg.goodsid= g.goods_id and lg.limitbuyid =? and g.source_from = '"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIMIT_BUY_SELECT_0 (){return "select g.name ,lg.* from es_limitbuy_goods lg  ,es_goods g where lg.goodsid= g.goods_id and lg.limitbuyid =? and g.source_from=lg.source_from and lg.source_from = '"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIMIT_BUY_UPDATE (){return "update es_limitbuy set disabled=? where id=? ";}
	
	public String getPAGE_MEMBER_COUPONS (){return "select * from es_member_coupon mc left join es_coupons c on c.cpns_id=mc.cpns_id  left join es_promotion p on c.pmt_id=p.pmt_id where member_id=? and mc.source_from='"+ManagerUtils.getSourceFrom()+"' and mc.source_from = c.source_from and mc.source_from = p.source_from order by mc.memc_gen_time desc";}
	
	public String getPAGE_EXCHANGE_COUPONS (){return "select c.*,p.pmt_time_begin,p.pmt_time_end from es_coupons c left join es_promotion p on c.pmt_id=p.pmt_id"
			+ " where cpns_type='1' and cpns_point is not null"
			+ " and cpns_status='1' and exchangabled=1 AND pmt_time_begin <= "+DBTUtil.current()
			+ " and pmt_time_end >"+DBTUtil.current() ;}
	
	public String getMEMBER_POINT_SQL (){return "select m.point from es_member m where m.member_id=?";}
	
	public String getCPNS_POINT_GET_BY_ID (){return "select cpns_point from coupons where cpns_status='1' and cpns_type='1' and cpns_point is not null and cpns_id=?";}
	
	public String getPMT_MEMBER_LV_GET_BY_LV_ID (){return "select count(lv_id) from pmt_member_lv where pmt_id = ? and (lv_id = ? or lv_id = '-1')";}
	
	public String getGENERATE_COUPON (){return "select * from es_coupons  c left join es_promotion  p on c.pmt_id=p.pmt_id" +
	" where cpns_status='1' and cpns_type='1' and c.cpns_id= ? and pmt_time_begin <= ?" +
	" and pmt_time_end >?" ;}
	
	public String getCOUPONS_SELECT_BY_ID (){return "select * from coupons where cpns_id = ?";}
	//and a.source_from = b.source_from
	public String getORDER_RULE_MANAGER (){return "select a.* from es_rule a,es_goods_rule b where a.rule_id = b.rule_id and a.disabled =0 and a.rule_time_begin< "+DBTUtil.current()+
	" and a.rule_time_end> "+DBTUtil.current()+"  and b.goods_id = ? and service_code = ?  and b.source_from='"+ManagerUtils.getSourceFrom()+"'   order by b.order_id asc ";}
	
	public String getORDER_RULE_MANAGER_TYPE (){return "select a.* from es_rule a,es_goods_rule b where a.rule_id = b.rule_id and a.rule_type=? and a.disabled =0 and a.rule_time_begin< "+DBTUtil.current()+
			" and a.rule_time_end> "+DBTUtil.current()+"  and b.goods_id = ? and service_code = ? and a.source_from = b.source_from and b.source_from='"+ManagerUtils.getSourceFrom()+"'";} // and a.source_from=' " +ManagerUtils.getSourceFrom() + "'   add by wui规则是可以整个平台公用的的
	
	public String getGET_RULES_BY_SERVICEID (){return "select a.* from es_rule a, es_service_offer b, es_service_offer_rule c  where a.rule_id = c.rule_id" +
	" and b.service_id = c.service_id  and a.rule_time_begin <=" +DBTUtil.current()+
	" and a.rule_time_end >(){return "+DBTUtil.current()+"  and c.source_from = ?  and a.source_from = b.source_from  and b.source_from = c.source_from and b.service_code = ? and  a.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getGET_RULES_BY_SERVICEID_TYPE (){return "select a.* from es_rule a, es_service_offer b, es_service_offer_rule c  where a.rule_id = c.rule_id and a.rule_type=?" +
			" and b.service_id = c.service_id  and a.rule_time_begin <= " +DBTUtil.current()+
			" and a.rule_time_end >= "+DBTUtil.current()+"  and c.source_from = ?  and a.source_from = b.source_from  and b.source_from = c.source_from and b.service_code = ? ";} //and  a.source_from='"+ManagerUtils.getSourceFrom()+"'"add by wui规则是可以整个平台公用的的
	
	public String getGOODS_USERS_SELECT_BY_GOODS_ID (){return "select * from ES_GOODS_USERS a where goods_id = ? and disabled=0 ";}
	
	public String getDELETE_PACKAGE_PRODUCT (){return "delete from package_product where goods_id = ?";}
	
	public String getPACKAGE_PRODUCT_SELECT (){return "select pp.*, p.product_id, p.sn, p.price, p.goods_id as pgoods_id, p.weight, g.name from es_package_product pp left join es_product p on p.product_id = pp.product_id left join es_goods g on g.goods_id = p.goods_id  where pp.source_from='"+ManagerUtils.getSourceFrom()+"' and p.source_from='"+ManagerUtils.getSourceFrom()+"' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and pp.goods_id = ?";}
	
	public String getDELETE_GOODS_SPEC (){return "delete from  goods_spec  where goods_id=?";}
	
	public String getDELETE_GOODS_LV_PRICE (){return "delete from  goods_lv_price  where goodsid=?";}
	
	public String getPRODUCT_DELETE (){return "delete from product where goods_id=? ";}
	
	public String getGOODS_SPEC_INSERT (){return "insert into es_goods_spec (spec_id,spec_value_id,goods_id,product_id)values(?,?,?,?)";}
	
	public String getGOODS_LV_PRICE_UPDATE (){return "update es_goods_lv_price set PRODUCTID = ? where goodsid=?";}
	
	public String getGOODS_UPDATE (){return "update goods set specs=? ,have_spec=? where goods_id=?";}
	
	public String getPRODUCT_SELECT_BY_ID (){return "select * from product where product_id=? and source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIST_SPECS (){return "select distinct s.spec_id,s.spec_type,s.spec_name,sv.spec_value_id,sv.spec_value,sv.spec_image ,gs.goods_id as goods_id " +
			" from es_specification s, es_spec_values sv, es_goods_spec gs" +
			" where s.source_from = '"+ManagerUtils.getSourceFrom() +"' and sv.source_from = '"+ManagerUtils.getSourceFrom() +"' and gs.source_from = '"+ManagerUtils.getSourceFrom() +"' and s.spec_id = sv.spec_id  and gs.spec_value_id = sv.spec_value_id and gs.goods_id=?";}
	
	public String getPRODUCT_SELECT_BY_GOODS_ID (){return "select * from product where goods_id=?";}
	
	public String getGOODS_SPEC_SELECT (){return "select sv.*,gs.product_id product_id,es.spec_name from  es_goods_spec  gs inner join es_specification es on gs.spec_id=es.spec_id and es.source_from='"+ManagerUtils.getSourceFrom()+"' inner join es_spec_values  sv on gs.spec_value_id = sv.spec_value_id and sv.source_from = '"+ManagerUtils.getSourceFrom() +"' where  gs.goods_id=? and gs.source_from = '"+ManagerUtils.getSourceFrom() +"' order by  to_number(gs.product_id),sv.spec_type desc";}
	
	public String getGOODS_SPEC_DELETE (){return "delete from goods_spec where 1=1";}
	
	public String getGOODS_LV_PRICE_DELETE (){return "delete from goods_lv_price where 1=1";}
	
	public String getPRODUCT_DELETE_BY_IDS (){return "delete from product where 1=1";}
	
	public String getPRODUCT_SELECT (){return "select p.* from es_product p left join es_goods g on g.goods_id = p.goods_id  where g.source_from='"+ManagerUtils.getSourceFrom()+"' and p.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled=0";}
	
	public String getPRODUCT_SELECT_BY_IDS (){return "select p.* from es_product p left join es_goods g on g.goods_id = p.goods_id where g.source_from='"+ManagerUtils.getSourceFrom()+"' and p.source_from='"+ManagerUtils.getSourceFrom()+"' and g.disabled=0 ";}
	
	public String getGET_BY_GOODSID (){return "select * from product where goods_id=?";}
	
	public String getPROMOTION_ACTIVITY_DELETE (){return "delete from promotion_activity where 1=1";}
	
	public String getPMT_MEMBER_LV_DELETE (){return "delete from es_pmt_member_lv where pmt_id in(select pmt_id from es_promotion where pmta_id in(replaceSql) "+ManagerUtils.apSFromCond("")+")";}
	
	public String getPMT_GOODS_DELETE (){return "delete from es_pmt_goods where pmt_id in(select pmt_id from es_promotion where pmta_id in(replaceSql)  "+ManagerUtils.apSFromCond("")+")";}
	
	public String getPROMOTION_DELETE (){return "delete from promotion where 1=1";}
	
	public String getPROMOTION_ACTIVITY_SELECT (){
		return "select a.id, a.name, a.enable, a.begin_time, a.end_time, a.disabled, "
				+ " a.brief, a.userid, a.source_from, a.atturl, a.rank, a.pmt_code, "
				+ " a.min_price, a.max_price, a.region, a.package_class, a.relief_no_class, "
				+ " a.relation_id, a.modify_eff_time, a.status_date, a.user_type"
				+ " from promotion_activity a "
				+ " where 1=1 and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				+ " and a.id = ?";
		}
	public String getPROMOTION_ACTIVITY_SELECT_1 (){return "select * from promotion_activity where id in ";}
	
	public String getPROMOTION_ACTIVITY_GET (){return "select a.*, b.realname "
			+ " from promotion_activity a "
			+ " left join adminuser b on a.userid = b.userid"
			+ " where 1=1 "
			+ " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getPROMOTION_ACTIVITY_GET_ECS (){
		return "	select a.id,a.pmt_code,a.name,b.pmt_id,b.pmt_type,b.pmt_describe,	"+
				"	  a.begin_time,a.end_time,a.enable,	"+
				"	  (select s.pname from es_dc_public s "
				+ " where s.stype=" + Consts.DC_PUBLIC_STYPE_PMT_TYPE
				+ " and s.pkey=b.pmt_type "
				+ " and s.source_from = '" + ManagerUtils.getSourceFrom() + "') pmt_type_name	"+
				"	  from es_promotion_activity a ,es_promotion b	"+
				"	 where a.id=b.pmta_id	"+
				"	   and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.pmt_type <> '"+Consts.PMT_TYPE_GIFT+"'";
		}
	
	public String getPROMOTION_ACTIVITY_LOG_GET_ECS (){
		return "	select a.id,a.pmt_code,a.name,a.status_date,c.realname oper_name,b.pmt_id,b.pmt_type,b.pmt_describe,	"+
				"	 b.order_money_from,b.order_money_to, a.begin_time,a.end_time,a.enable,	"+
				"	  (select s.pname from es_dc_public s "
				+ " where s.stype=" + Consts.DC_PUBLIC_STYPE_PMT_TYPE
				+ " and s.pkey=b.pmt_type ) pmt_type_name	"+
				"	  from es_promotion_activity_log a ,es_promotion_log b,es_adminuser c	"+
				"	 where a.id=b.pmta_id and a.oper_id=c.userid "+
				"	   and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.pmt_type <> '"+Consts.PMT_TYPE_GIFT+"'";
		}
	
	public String getPROMOTION_ACTIVITY_BATCH_PUBLISH(){
		return "select a.id,a.pmt_code,a.name,a.begin_time,a.end_time,a.enable from es_promotion_activity a ,es_promotion b	where a.id=b.pmta_id and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.pmt_type <> '"+Consts.PMT_TYPE_GIFT+"'";
	}
	public String getPROMOTION_ACTIVITY_GET_GOODS_ECS(){
		return "select a.*,b.name from es_pmt_goods a,es_goods b  where a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
				" and a.goods_id=b.goods_id and b.type='goods' and a.pmt_id=?";
	}
	
	public String getPROMOTION_ACTIVITY_GET_GOODS_LOG_ECS(){
		return "select a.*,b.name from es_pmt_goods_log a,es_goods b  where a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
				" and a.goods_id=b.goods_id and b.type='goods' and a.pmt_id=?";
	}
	
	public String getPROMOTION_ACTIVITY_GET_CO_ECS(){
		return "select a.*,b.org_name from es_activity_co a,es_goods_org b  where a.org_id=b.party_id and a.source_from=b.source_from"+ 
                " and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.activity_id=?";
	}
	
	public String getUPDATE_ACTIVITY_ENABLE_ECS(){
		return "update es_promotion_activity a set a.enable=? where a.id=?";
	}
	
	public String getACTIVITY_PUBLISH_STATUS_ECS(){
		return "select * from es_activity_co r where r.activity_id=?";
	}
	
	public String getGOODS_ORG_BY_NAME(){
		return "select * from es_goods_org a where 1=1";
	}
	
	
	public String getUPDATE_ACTIVITY_STATUS_ECS(){
		//zengxianlian
		return "update es_activity_co a set a.status=? where a.activity_id=? and org_id=?";
		//return "update es_activity_co a set a.status=? where a.activity_id=?";
	}
	
	public String getPROMOTION_ACTIVITY_GET_1 (){return "select a.* "
			+ " from es_promotion_activity a "
			+ " where 1=1 "
			+ " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getPROMOTION_ACTIVITY_GET_BY_TAG_ID (){return "select a.* "
			+ " from promotion_activity a"
			+ " inner join tag_rel b on a.id = b.rel_id "
			+ " where 1 = 1"
			+ " and a.enable = 1"
			+ " and a.begin_time < " +  DBTUtil.current() 
			+ " and a.end_time > " +  DBTUtil.current()
			+ " and a.source_from = ?"
			+ " and b.tag_id = ? ";}
	
	public String getPMT_MEMBER_LV_DEL (){return "delete from pmt_member_lv where pmt_id=?";}
	
	public String getPMT_GOODS_DEL (){return "delete from pmt_goods where pmt_id=?";}
	
	public String getPROMOTION_SELECT (){return "select * from es_promotion where pmt_basic_type='goods'" +
		    " and  pmt_time_begin<? and  pmt_time_end>?" +
		    " and ((pmt_id in  (select pmt_id from es_pmt_goods where goods_id=?  "+ManagerUtils.apSFromCond("")+")" +
		    " and pmt_id in (select pmt_id from es_pmt_member_lv where lv_id =?   "+ManagerUtils.apSFromCond("")+")" + 
		    " and pmt_type='0'  "+ManagerUtils.apSFromCond("")+"";}
	/**
	 * 根据pmt_solution查找pmta_id 
	 * @return
	 */
	public String getPMTA_ID(){
		return " select t.pmta_id from es_promotion t where t.pmt_solution =?";
	}
	
	public String getPROMOTION_ACTIVITY_ENABLE(){
		return " update es_promotion_activity set enable=? where id =?";
	}
	/*
	 * 改造活动展示列表
	 */
	public String getPROMOTION_LIST(){
		return "select distinct t.id,t.begin_time,t.end_time,t.name,t.enable,a.pmt_id,a.disabled,a.promotion_type,a.pmt_describe,a.pmts_id,a.pmt_solution"+
				" from es_promotion_activity t, es_promotion a  where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.source_from = a.source_from and t.id = a.pmta_id  ";
	}
	/**
	 * 获得promotion_activity的主键
	 * @return
	 */
	public String getPROMOTION_ACTIVITY_ID(){
		return " select t.id  from es_promotion_activity t where 1=1  ";
	}

	public String getPROMOTION_SELECT_0 (){return "select * from  es_promotion where pmt_basic_type='order' " +
		    " and pmt_time_begin<"+DBTUtil.current()+" and  pmt_time_end>" +DBTUtil.current()+
		    " and order_money_from<=? and order_money_to>=?" +
		    " and ((pmt_id in (select pmt_id from es_pmt_member_lv where lv_id =? ) " + 
		    " and pmt_type='0'  ";}
	
	public String getGET_COUP_PMTID (){return "select  c.pmt_id from es_member_coupon mc ,es_coupons c where mc.cpns_id = c.cpns_id and mc.memc_code=?  and mc.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getPMT_GOODS_SELECT (){return "select COUNT(*) count from es_pmt_goods where pmt_id = ? and goods_id = ? or goods_id =-1 ";}
	
	public String getPMT_GOODS_COUNT (){return "select count(*) from es_pmt_goods where pmt_id = ? and goods_id = ? or goods_id =-1 ";}
	
	public String getLIST_BY_ACTIVITYID (){return "select * from promotion where disabled='false' and pmta_id=?";}
	
	public String getPLUGIN_SAVE (){return "update promotion set pmt_solution =? where pmt_id=?";}
	
	public String getSAVE_MEMBER_LV (){return "insert into pmt_member_lv(pmt_id,lv_id)values(?,?)";}
	
	public String getPMT_GOODS_INSERT (){return "insert into pmt_goods(pmt_id,goods_id)values(?,?)";}
	
	public String getPMT_GOODS_DEL_BY_IDS (){return "delete from pmt_goods where 1=1";}
	
	public String getPMT_MEMBER_LV_BY_IDS (){return "delete from pmt_member_lv where 1=1";}
	
	public String getPROMOTION_DEL_BY_IDS (){return "delete from promotion where 1=1";}
	
	public String getPROMOTION_SELECT_BY_ID (){return "select * from promotion where pmt_id =?";}
	
	public String getGOODS_SELECT_BY_PMT_ID (){return "select * from  es_goods where goods_id in(select goods_id from es_pmt_goods where pmt_id =?  "+ManagerUtils.apSFromCond("")+")";}
	
	public String getLIST_MEMBER_LVID (){return "select lv_id from pmt_member_lv where pmt_id =? ";}
	
	public String getUSE_COUPON (){return "select  c.* from  es_member_coupon mc , es_coupons c, es_promotion p where mc.cpns_id = c.cpns_id and mc.memc_code=? and mc.member_id=? and ?>mc.memc_used_times" +
            " and c.cpns_status='1' and c.pmt_id=p.pmt_id and sysdate between p.pmt_time_begin and p.pmt_time_end  and p.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getLIST_ORDER_PMT (){return "select * from order_pmt where order_id = ?";}
	
	public String getHAS_ORDER_PMT (){return "select count(*) from  es_promotion where pmt_basic_type='order' and pmt_time_begin<? and  pmt_time_end>? " +
    " and pmt_id in (select pmt_id from es_pmt_member_lv where lv_id =?  "+ManagerUtils.apSFromCond("")+") and pmt_type='0'";}
	
	public String getCHECK_USED (){return "select count(0)  from  es_goods_spec where 1=1";}
	
	public String getSPECIFICATION_DEL_BY_IDS (){return "delete from es_specification where 1 = 1 ";}
	
	public String getSPEC_VALUES_DEL (){return "delete from es_spec_values where 1=1 ";}
	
	public String getGOODS_SPEC_DEL (){return "delete from es_goods_spec where 1=1 ";}
	
	public String getSPEC_VALUES_DEL_BY_ID (){return "delete from es_spec_values where spec_id=?";}
	
	public String getSPECIFICATION_LIST_ALL (){return "select * from es_specification where source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSPECIFICATION_GET_BY_ID (){return "select * from es_specification where spec_id=?";}
	
	public String getSPEC_VALUES_GET_BY_ID (){return "select * from es_spec_values where spec_id =?";}
	
	public String getSPEC_VALUES_GET_BY_SPECIFICATION (){return "select sv.*,s.spec_type from es_spec_values sv, es_specification s where sv.source_from=s.source_from and sv.source_from='" + ManagerUtils.getSourceFrom() + "' and sv.spec_id=s.spec_id and sv.spec_value_id =?";}
	
	public String getTAGS_GET_COUNT (){return "select count(0) from tags where tag_name=? and tag_id!=?";}
	
	public String getCHECK_JOIN_GOODS (){return "select count(0)  from tag_rel where  1=1 ";}
	
	public String getTAGS_DEL_BY_ID (){return "delete from tags where 1=1";}
	
	public String getTAG_REL_DEL_BY_IDS (){return "delete from es_tag_rel where 1=1";}
	
	public String getTAGS_GET_BY_ID (){return "select * from tags where tag_id=?";}
	
	public String getTAGS_LIST_ALL (){return "select * from tags where 1=1";}
	
	public String getTAGS_LIST_ALL_EDIT (){return "select * from tags where cat_type='100'";}
	
	public String getTAG_REL_DEL_BY_ID (){return "delete from es_tag_rel where rel_id=?";}
	
	public String getTAG_REL_INSERT (){return "insert into es_tag_rel(tag_id,rel_id)values(?,?)";}
	
	public String getTAG_REL_GET_BY_ID (){return "select tag_id from es_tag_rel where rel_id=?";}
	
	public String getGOODS_REL_BY_TAG (){return "select g.*,staff_no apply_id, (select username from es_adminuser u where u.userid =g.staff_no ) apply_username from es_goods g where disabled = ? and g.goods_id in(select rel_id from es_tag_rel where tag_id=?  "+ManagerUtils.apSFromCond("")+")";}
	
	public String getFIND_WARE_HOUSE_BY_HOUSTID (){return "select *from es_warehouse where house_id=?";}
	
	public String getWAREHOUSE_GET_ALL (){return "select a.*,p.comp_name from es_warehouse a left join es_product_comp p on a.comp_code=p.comp_code where a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSCOPENAME_BY_CODE(){return "select local_name,region_id,p_region_id,region_grade from es_regions where source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getWAREHOUSE_DEL (){return "delete from es_warehouse where 1=1 ";}
	
	public String getIS_WAREHOUSE_CODE_EXITS (){return "select house_code from es_warehouse where house_code=? ";}
	
	public String getIS_WAREHOUSE_NAME_EXITS (){return "select house_name from es_warehouse where house_name=? ";}
	
	public String getWAREHOUSE_LIST (){return "select house_name,house_id from es_warehouse where source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getWAREHOUSE_LIST_ALL (){return "select t.* from es_warehouse t where 1=1 ";}
	
	public String getWAREHOUSE_SEAT_DEL_BY_IDS (){return "delete from es_warehouse_seat where 1=1";}
	
	public String getWAREHOUSE_SEAT_GOODS_UPDATE (){return "UPDATE es_warehouse_seat_goods SET disabled=1 where seat_id =? and goods_id =?";}
	
	public String getFIND_WAREHOUSE_SEAT_BY_SEAT_ID (){return "select b.house_name,b.house_id,a.seat_id,a.seat_name from es_warehouse_seat a,es_warehouse b where a.house_id=b.house_id and seat_id=?";}
	
	public String getIS_WAREHOUSENAME_EXITS (){return "select seat_name from es_warehouse_seat where seat_name=? and house_id=?";}
	
	public String getIS_HOUSE_NAME_EXITS (){return "select * from es_warehouse_seat_goods where goods_id=? and seat_id=? and house_id=? and disabled=0";}
	
	public String getWAREHOUSE_SEAT_SELECT (){return "select a.seat_id,a.seat_name,b.house_id,b.house_name,g.specifications,g.name,g.goods_id,g.sn,h.disabled,h.create_date from " +
			"es_warehouse_seat a,es_warehouse b,es_goods g,es_warehouse_seat_goods h where " +
			"a.source_from = b.source_from and g.source_from = a.source_from and h.source_from = a.source_from and a.source_from = '" + 
			ManagerUtils.getSourceFrom() + "' and H.GOODS_ID = G.GOODS_ID AND a.seat_id=h.seat_id and b.house_id=h.house_id ";}
	
	
	public String getWARHOUSE_SEAT_LIST (){return " SELECT DISTINCT c.seat_name FROM es_warehouse_seat_goods a, es_warehouse b,es_warehouse_seat c  WHERE a.house_id=b.house_id AND a.seat_id=c.seat_id and a.disabled = '0' and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getWARHOUSE_SEAT_COUNT (){return " SELECT count(DISTINCT c.seat_name) FROM es_warehouse_seat_goods a, es_warehouse b,es_warehouse_seat c  WHERE a.house_id=b.house_id AND a.seat_id=c.seat_id and a.disabled = '0' and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getWAREHOUSE_SEAT_BY_WAREHOUSE (){return "SELECT a.seat_id,a.seat_name,b.house_id,b.house_name FROM " +
			"es_warehouse_seat a,es_warehouse b WHERE a.house_id=b.house_id and " +
			"a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getWAREHOUSE_LIST_PART (){return "select house_name,house_id from es_warehouse where 1=1";}
	
	public String getWAREHOUSE_SEAT_LIST (){return "select seat_id,seat_name,house_id from es_warehouse_seat where house_id=?";}
	
	public String getFIND_GOODS_SEAT (){return "select a.seat_id,a.seat_name,b.house_id,b.house_name from es_warehouse_seat a,es_warehouse b where 1=1 and a.house_id=b.house_id  ";}
	
	public String getSUM_STORE_BY_GOODSID (){return "select sum(t.store) from es_warehouse_goods_store t where t.goods_id=? and t.house_id=?";}
	
	public String getSUM_STORE_BY_GOODSANDHOUSE (){return "select sum(t.store) store,sum(t.freeze_store) freeze_store,t.house_id,t.goods_id,g.name goodsName from es_warehouse_goods_store t,es_goods g where t.source_from=g.source_from and t.source_from='"+ManagerUtils.getSourceFrom()+"' and g.goods_id=t.goods_id and t.house_id=? and t.goods_id in(replaceSql) group by t.house_id,t.goods_id,g.name";}
	
	public String getWARHOUSE_GOODS_STORE (){return "select * from es_warehouse_goods_store t where t.goods_id=? and t.house_id=?";}
	
	public String getwarhouse_goods_store_update (){return "update es_warehouse_goods_store t set t.store=t.store+? where t.goods_id=? and t.house_id=?";}
	
	public String getDESC_STORE (){return "update es_warehouse_goods_store t set t.store=t.store-? where t.goods_id=? and t.house_id=?";}
	
	public String getUPDATE_GOODS_STORE (){return "update es_goods set store=store+? where goods_id=?";}
	
	public String getGOODS_STORE_LIST (){return "select * from (SELECT a.store,a.freeze_store,d.sn,d.name,d.SPECIFICATIONS,c.house_name,a.goods_id, "
			+ " (SELECT "+ DBTUtil.nvl()+"(sum(i.ship_num),0) from " +
					"es_order o,es_order_items i,ES_DELIVERY y where " +
					"o.order_id=i.order_id AND o.order_id=y.order_id and o.status=5 AND " +
					"i.goods_id=a.goods_id AND y.house_id=a.house_id and o.source_from = '" + ManagerUtils.getSourceFrom() + "' " +
					"and i.source_from = '" + ManagerUtils.getSourceFrom() + "' and y.source_from = '" + ManagerUtils.getSourceFrom() + "')  transit_store"
			+"  FROM es_warehouse_goods_store a"
			+ "  LEFT JOIN es_warehouse_seat_goods b ON(a.goods_id=b.goods_id AND a.house_id=b.house_id and b.disabled=0 and b.source_from = a.source_from)"
			+ "  LEFT JOIN es_warehouse c ON(c.house_id=a.house_id and c.source_from = a.source_from)"
			+ " LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and a.source_from = d.source_from) where a.source_from = '" + ManagerUtils.getSourceFrom() + "'  #cont GROUP BY c.house_name,a.store,a.freeze_store,d.sn,d.name,d.SPECIFICATIONS,a.goods_id,a.house_id) ty";}
	
	public String getGOODS_STORE_COUNT (){return "SELECT COUNT(1) FROM (SELECT c.house_name  house_name FROM " +
			"es_warehouse_goods_store a " +
			"LEFT JOIN es_warehouse_seat_goods b ON(a.goods_id=b.goods_id AND a.house_id=b.house_id and b.source_from = a.source_from) " +
			"LEFT JOIN es_warehouse c ON(c.house_id=a.house_id and c.source_from = a.source_from) " +
			"LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and d.source_from = a.source_from) where " +
			"a.source_from = '" + ManagerUtils.getSourceFrom() + "'  #cont GROUP BY c.house_name,a.store,a.freeze_store,d.sn,d.name,d.SPECIFICATIONS,a.goods_id,a.house_id ) ty";}
	
	public String getWARHOUSE_STORE_LIST (){return " select a.store,a.freeze_store,d.sn,d.name,d.SPECIFICATIONS,b.house_name,"
			+ " (SELECT nvl(sum(i.ship_num),0) from es_order o,es_order_items i,ES_DELIVERY y where " +
			"o.order_id=i.order_id AND o.order_id=y.order_id and o.status=5 " +
			"AND i.goods_id=a.goods_id AND y.house_id=a.house_id and o.source_from = i.source_from " +
			"and i.source_from = y.source_from and y.source_from = '" + ManagerUtils.getSourceFrom() + "')  transit_store"
			+ " from es_warehouse_goods_store a "
			+ " LEFT JOIN es_warehouse b ON(a.house_id=b.house_id and b.source_from = a.source_from)"
			+ " LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and d.source_from=a.source_from) where a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont";}
	
	public String getWARHOUSE_GOODS_STORE_COUNT (){return "SELECT COUNT(*) from es_warehouse_goods_store a  " +
			"LEFT JOIN es_warehouse b ON(a.house_id=b.house_id and b.source_from = a.source_from)  " +
			"LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and b.source_from = a.source_from) where " +
			"a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont";}
	
	public String getSUM_STORE_LIST (){return "select sum(a.store) sum_store,sum(a.freeze_store) sum_freeze_store,d.goods_id,d.sn,d.name," +
			"d.SPECIFICATIONS,d.last_modify,d.create_time,d.unit,d.weight,"
			+ " SUM((SELECT nvl(sum(i.ship_num),0) from es_order o,es_order_items i,ES_DELIVERY y where " +
			"o.order_id=i.order_id AND o.order_id=y.order_id and o.status=5 AND " +
			"i.goods_id=a.goods_id AND y.house_id=a.house_id and o.source_from = i.source_from and i.source_from = y.source_from " +
			"and y.source_from = '" + ManagerUtils.getSourceFrom() + "'))  sum_transit_store"
			+ " from es_warehouse_goods_store a "
			+ " LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and d.source_from = a.source_from) where a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont"
			+ " Group BY d.NAME,d.sn,d.SPECIFICATIONS,d.last_modify,d.create_time,d.unit,d.weight,d.goods_id";}
	
	public String getSUM_STORE_COUNT (){return "SELECT COUNT(*) FROM (SELECT  NAME count_sum from es_warehouse_goods_store a " +
			"LEFT JOIN es_goods d ON(d.goods_id=a.goods_id and a.source_from = d.source_from) where a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont GROUP BY d.NAME) b";}
	
	public String getADV_UPDATE_1 (){return "update es_adv set state = '1' where aid = ? " ;}
	
	public String getADV_UPDATE_2 (){return "update es_adv set state = '2' where aid = ? " ;}
	
	public String getGOODS_TEMP_GET_BY_ID (){return "select * from GOODS_TEMP  where goods_id=?";}
	
	public String getPRODUCT_GET_BY_GOODS_ID (){return "select o.product_id from es_product o where o.goods_id  = ?";}
	
	public String getGOODS_UPADTE (){return "update es_goods set audit_state='00A', name=?,sn=?  where goods_id=? ";}
	
	public String getGOODS_LV_PRICE_UPDATE_3 (){return " update es_goods_lv_price set price = ? where lvid = '3' and status = '00A' and goodsid = ? and productid = ?";}
	
	public String getGOODS_UPDATE_00X (){return "update es_goods set audit_state='00X' where goods_id=?" ;}
	
	public String getGOODS_UPDATE_BY_GOODS_ID (){return "update es_goods set audit_state='00X' where goods_id=?" ;}
	
	public String getGOODS_UPDATE_BY_GOODS_ID_0 (){return "update es_goods set audit_state='00X' where goods_id=?" ;}
	
	public String getGOODS_UPDATE_BY_GOODS_ID_1 (){return "update es_goods set audit_state='00X' where goods_id=?" ;}
	
	public String getGOODS_UPDATE_BY_GOODS_ID_2 (){return "update es_goods set audit_state='00X' where goods_id=?" ;}
	
	public String getGOODS_UPDATE_BY_GOODS_ID_3 (){return "update es_goods set audit_state='00A'  where goods_id=?" ;}
	
	public String getGOODS_LV_PRICE_UPDATE_BY_GOODS_ID (){return "update es_goods_lv_price set status='00A' where goods_id=? and status='00X'" ;}
	
	public String getGOODS_LV_PRICE_DEL (){return "delete from es_goods_lv_price  where goods_id=? and id=?" ;}
	
	public String getGOODS_PRICE_BY_GOODS_ID (){return "update es_goods set price=? where goods_id=?" ;}
	
	public String getGOODS_LV_PRICE_BY_GOODS_ID (){return "update es_goods_lv_price set price=? where id=?" ;}
	
	public String getHANDLE_COND (){return "select k.old_change,k.new_change from es_mod_info_inst k where k.flow_inst_id =? and k.ref_pk_id =? ";}
	
	public String getGOODS_ARTICLES_BY_ID (){return "select * from goods_articles where goodsid=?";}
	
	public String getGOODS_ARTICLES_DEL (){return "delete from goods_articles where goodsid=?";}
	
	public String getGOODS_ARTICLES_INSERT (){return "insert into goods_articles(articleid,goodsid,title)values(?,?,?)";}
	
	public String getNUM_GET_COUNT (){return "select count(0) num from goods where sn=?";}
	
	public String getSKU_NUM_GET_COUNT (){return "select count(0) num from es_goods a,es_product b where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=b.goods_id and b.sku=?";}
	
	public String getNUM_GET_COUNT_BY_ID (){return "select count(0) num from goods where sn=? and goods_id!= ?";}
	
	public  String getSERVICE_GOODS_SALES_COUNT (){return "select sum(t.ship_num) from es_order_items t where t.goods_id=? and t.lv_id=?";}

	public String getGOODS_REL_GOODS (){return "select * from es_goods where goods_id in(select z_goods_id from es_goods_rel where a_goods_id=? and rel_type=?)";}
	
	public String getGOODS_REL_GOODS_PAGE (){return "select * from es_goods where goods_id in (select z_goods_id from es_goods_rel where 1=1 #cond) and source_from=? ";}

	public String getTERMINAL_REL_TERMINAL (){return "select * from es_goods where disabled=? and goods_id in(select goods_2 from es_goods_complex where goods_1=?)";}

	public String getACCEPT_PRICE_GET (){return "select price from es_goods_lv_price where goodsid=? and productid=? and lvid=?";}
	
	public String getLIST_GOODS_ATTR_DEF (){return "select attr_spec_type,sub_attr_spec_type, field_name, field_desc, field_attr_id,field_type,default_value,default_value_desc,sec_field_name,sec_field_desc, "+
			" is_edit,is_null,check_message,rel_table_name,owner_table_fields "+
			" from es_attr_def where attr_spec_type = 'goods' "+
			" and (sub_attr_spec_type = 'accept' or sub_attr_spec_type = 'pay' or sub_attr_spec_type = 'delivery') "+
			" and attr_spec_id = ?";}
	
	public String getGOODS_STORE_GET (){return "select store from es_goods where goods_id=? ";}
	
	public String getGET_GOODS_ADJUNCT (){return "select * from es_goods_adjunct t where t.adjunct_id=?";}
	
	public String getGET_GOODS_BUSINESS (){return "select * from es_goods_business where goods_id=?";}
	
	public String getTAG_GET_BY_CAT_TYPE (){return "select * from es_tags where cat_type = ?";}
	
	public String getLIST_CAT_GOODS_COUNT (){return "select count(a.goods_id) goods_count,b.cat_id,b.name,b.cat_path,b.type_id "+
					" from es_goods a, es_goods_cat b "+
					" where a.cat_id = b.cat_id and a.goods_type='normal' and a.disabled = 0 and a.market_enable = 1 "+
					" and a.audit_state = '"+Consts.GOODS_AUDIT_SUC+"' and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.source_from='"+ManagerUtils.getSourceFrom()+"' and b.parent_id = ? #cond "+
					" group by b.cat_id,b.name,b.cat_path,b.type_id";}
	
	public String getLIST_GOODS_ATTR_DEF2 (){return "select g.goods_id,d.attr_code,d.field_attr_id,d.attr_spec_id,d.field_type,d.attr_spec_type,d.field_name,d.field_desc,d.o_field_name,d.rel_table_name from es_goods g,es_attr_def d where g.goods_id=d.attr_spec_id and g.source_from='"+ManagerUtils.getSourceFrom()+"' and d.source_from='"+ManagerUtils.getSourceFrom()+"' and attr_spec_id=?";} 
    
	public String getLIST_GOODS_TEMP_INST (){return "select  g.goods_id,t.temp_inst_id,t.temp_name,t.temp_cols,t.source_from,t.goods_id from es_goods g,es_temp_inst t where g.goods_id=t.goods_id and g.source_from='"+ManagerUtils.getSourceFrom()+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.goods_id=?";} 
  
	public String getQRT_SERVICE_CODE_LIST (){return "SELECT * FROM es_service_offer WHERE display_flag='0'";}
	
	public String getQRY_RULE_LIST (){return "SELECT * FROM es_rule a WHERE a.source_from = '"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getQRY_RULE_LIST_COUNT (){return "SELECT count(a.rule_id) FROM es_rule a WHERE a.source_from = '"+ManagerUtils.getSourceFrom()+"'";}

	public String getQRY_RULE_BY_GOODS_SERVICE (){return "SELECT a.* FROM es_rule a,es_goods_rule b WHERE a.rule_id = b.rule_id AND b.goods_id = ? AND b.service_code = ? AND b.source_from = '"+ManagerUtils.getSourceFrom()+"'";}

	public String getDEL_GOODS_RULE (){return "DELETE FROM es_goods_rule WHERE source_from = '"+ManagerUtils.getSourceFrom()+"' AND goods_id = ? AND service_code = ?";}

	public String getGOODS_SPEC_LIST (){return "select distinct b.spec_id,b.spec_name,b.spec_type from ES_GOODS_SPEC a,es_specification b where a.spec_id=b.spec_id and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id=?";}

	public String getSPEC_VALUES_LIST (){return "select b.spec_id,b.spec_value_id,b.spec_value,b.spec_image from es_spec_values b "+
								" where b.spec_value_id in(select spec_value_id from ES_GOODS_SPEC where goods_id=? and spec_id=?) order by b.spec_value_id asc";}

	public String getPRODUCT_ID_LIST (){return "select * from es_goods_spec where goods_id = ? and spec_value_id=?";}
	
	public String getGOODS_COUPONS_GET_GOODS_ID (){return "select c.pmt_id, cpns_id, cpns_name,cpns_prefix, cpns_status, pmt_time_begin, pmt_time_end, cpns_type, cpns_gen_quantity, " + 
			" cpns_point from es_coupons c  left join es_promotion p on c.pmt_id=p.pmt_id and c.source_from = p.source_from left join ES_PMT_GOODS t on t.pmt_id=p.pmt_id where c.source_from = '" + ManagerUtils.getSourceFrom() + "' and t.goods_id=? ";}

	public String getGOODS_SCORE_GET (){return "select to_char(nvl(sum(grade)/count(*),0),990.99) score,count(*) comm_num from es_comments "+
					" where object_type='discuss' and commenttype='wssdetails' and disabled<>'true' and object_id=?";}

	public String getGOODS_DETAIL_GET (){return "select intro from es_goods where goods_id=?";}
	
	public String getQRY_TAGS_LIST_GET (){return "select a.*, b.pname as cat_type_name "
			+ " from tags a "
			+ " left join dc_public b "
			+ " on a.cat_type = b.pkey "
			+ " and b.stype = ?"
			+ " where 1=1" 
			+ " and a.source_from = b.source_from"
			+ " and a.source_from = ?";}
			
	public String getGOODS_SPEC_CHECK (){return "select g.have_spec from es_goods g where g.goods_id=? ";}
	
	public String getGOODS_PAGE_LIST_BY_TYPE (){return "select * from es_goods where type_id=? and source_from=? ";}
	
	
	public String getGOODS_PAGE_LIST_BY_IDS (){return "SELECT * FROM es_goods WHERE source_from='"+ManagerUtils.getSourceFrom()+"' AND disabled='0'";}

	public String getGOODS_PAGE_LIST_BY_IDS_COUNT (){return "SELECT count(goods_id) FROM es_goods WHERE source_from='"+ManagerUtils.getSourceFrom()+"' AND disabled='0'";}

	public String getBRAND_MODEL_LIST (){return "select m.* from es_brand_model m,es_brand b where m.brand_code = b.brand_code and m.source_from=b.source_from and b.source_from='"+ManagerUtils.getSourceFrom()+"' and b.brand_id=?";}

	
	public String getGOODS_TERMINAL_LIST (){return "	select a.product_id,a.name,a.sku,a.goods_id,a.sn,b.brand_id,b.model_code,b.cat_id," +
	        "  (select s.name from es_goods_cat s where s.cat_id=b.cat_id and s.source_from=b.source_from) cat_name"+
			"  from es_product a,es_goods b where b.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.type_id="+Consts.GOODS_TYPE_TERMINAL+"	"+
			"	 and a.sku is not null and a.goods_id = b.goods_id and a.source_from = b.source_from  ";
	}
	
	public String getGOODS_CONTRACT_LIST (){return "	select a.product_id,a.name,a.sku,a.goods_id,c.params from es_product a,es_goods b,es_goods_type c 	"+
			"	where b.type_id = '"+Consts.GOODS_TYPE_CONTRACT+"' and a.sku is not null and a.goods_id = b.goods_id 	"+
			"	and a.source_from = b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.type_id=c.type_id	";}
	
	
	public String getGOODS_OFFER_LIST_BY_CONTRACT (){return "	select c.goods_id,c.product_id,c.sku,c.name,b.params,b.stype_id,a.rel_code from es_goods_rel a,es_goods b,es_product c 	"+
			"	where a.z_goods_id=b.goods_id and b.goods_id=c.goods_id 	"+
			"	and  a.rel_type='CONTRACT_OFFER' and a.source_from = b.source_from and b.type='product'	"+
			"	and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.type_id='"+Consts.GOODS_TYPE_OFFER+"' ";}
	
	public String getGOODS_GET_CONTRACT_PARAMS (){return "	select b.goods_id,b.name,c.params from es_goods_rel a,es_goods b,es_goods_type c 	"+
			"	where a.a_goods_id=b.goods_id and b.type_id=c.type_id	"+
			"	and  a.rel_type='CONTRACT_OFFER' and a.source_from='"+ManagerUtils.getSourceFrom()+"' and  a.a_goods_id=? and a.z_goods_id=?	";}
	
	public String getBRAND_LIST (){return "select * from es_brand where source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getPRODUCT_REL_GOODS_LIST (){return "select g.goods_id,g.sn,g.sku,g.name goods_name,g.create_time,g.market_enable,c.name cat_name,b.name brand_name,t.name type_name from es_goods g left join es_goods_cat c on g.cat_id = c.cat_id left join es_brand b on g.brand_id = b.brand_id "+
				" left join es_goods_type t on g.type_id = t.type_id where g.type='goods' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and exists (select 1 from es_goods_rel r where r.a_goods_id=g.goods_id and r.source_from='"+ManagerUtils.getSourceFrom()+"' and r.product_id=?)";}
	
	public String getGOODS_PUBLISH_ORG_GET (){return "select c.*,o.org_name from es_goods_co c, es_goods_org o where c.org_id=o.party_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.goods_id=?";}
	//添加发布状态条件过滤---zengxianlian
	public String getGOODS_PUBLISH_ORG_GET_STATUS (){return "select c.*,o.org_name from es_goods_co c, es_goods_org o where c.org_id=o.party_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.goods_id=? and c.status=?";}
	
	public String getGOODS_PUBLISH_ORG_ZJ_GET_STATUS (){return "select c.*,o.org_name from es_goods_co c, es_goods_zj_org o where c.org_id=o.party_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.goods_id=? and c.status=?";}
	
	public String getGOODS_CO_QUEUE_STATUS(){return "select status,failure_desc from es_co_queue where service_code='CO_SHANGPIN' and object_id=? order by status_date desc";}
	 public String getATTR_CODE_GET() {
		    return "select dc_sql from es_dc_sql where source_from='" + ManagerUtils.getSourceFrom() + "' and dc_name=?";
		  }
	public String getPRODUCT_CO_QUEUE_STATUS(){return "select status,failure_desc from es_co_queue where service_code='CO_HUOPIN' and object_id=? order by status_date desc";}
	
	public String getPRODUCT_PUBLISH_ORG_GET (){return "select c.*,o.org_name from es_product_co c, es_goods_org o where c.org_id=o.party_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.product_id=?";}
	//添加发布状态条件过滤---zengxianlian
	public String getPRODUCT_PUBLISH_ORG_GET_STATUS (){return "select c.*,o.org_name from es_product_co c, es_goods_org o where c.org_id=o.party_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.product_id=? and c.status=?";}
		
	public String getPRODUCT_LIST (){return "select product_id from es_service_offer where service_id=?";}
	
	
	public String getGOODS_CO_MODIFY_STATUS_BY_ID (){return "update es_goods_co set status = ? where 1=1 and batch_id = ? and org_id=?";}
	public String getPRODUCT_CO_MODIFY_STATUS_BY_ID (){return "update es_product_co set status = ? where 1=1 and batch_id = ? and org_id=?";}
	public String getNO_CO_MODIFY_STATUS_BY_ID (){return "update es_no_co set status = ? where 1=1 and batch_id = ?";}
	public String getACTIVITY_CO_MODIFY_STATUS_BY_ID () {
		return "update es_activity_co set status = ? where 1=1 and batch_id = ? ";}
	
	public String getOFFER_CHANGE_LIST (){return "select goods.goods_id goods_id,goods.name name,goods.crm_offer_id crm_offer_id,goods.p1 p1,goods.p2 p2,goods.p3 p3,goods.p4 p4,goods.p5 p5,goods.p6 p6,goods.p7 p7,goods.price price,goods.create_time create_time,goods.last_modify end_time,goods.intro specs,p.product_id product_id,p.name product_name,area.lan_id lan_id from es_goods goods  join es_product p on goods.goods_id = p.goods_id join es_goods_area area on goods.goods_id= area.goods_id  where goods.type_id=? and goods.source_from='"+ManagerUtils.getSourceFrom()+"' and p.source_from='"+ManagerUtils.getSourceFrom()+"' and area.source_from='"+ManagerUtils.getSourceFrom()+"'";}

	
	public String getGOODS_INFO_GET_0 (){
		
		return "select g.goods_id,g.name goods_name,g.type_id,g.cat_id product_type,g.params, "
				+ " p.price goods_price,m.model_code specification_code,g.source_from, "
				+ " m.model_name specification_name,b.name brand_name,b.brand_code brand_number, "
				+ " m.machine_code model_code,m.machine_name model_name,s.spec_value_id color_code, "
				+ " v.spec_value color_name, p.product_id " 
				+ " from es_goods g "
				+ " left join es_brand b on g.brand_id=b.brand_id " + ManagerUtils.apSFromCond("b")
				+ " left join es_brand_model m on m.model_code= g.model_code " + ManagerUtils.apSFromCond("m")
				+ " left join es_goods_package pkg on g.goods_id=pkg.goods_id "+ManagerUtils.apSFromCond("pkg")
				+ " left join es_product p on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("p")+""
				+ " left join es_goods_spec s on s.product_id=p.product_id and s.spec_id=1  "+ManagerUtils.apSFromCond("s")
				+ " left join es_spec_values v on s.spec_value_id=v.spec_value_id "+ManagerUtils.apSFromCond("v")+""
				+ " where p.type='goods' and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		}
	
	public String getHAVE_GIFTS() {
		
		return "select 1 from es_promotion a, es_promotion_activity b, es_activity_co c"
				+ " where a.pmta_id = b.id "
				+ " and a.source_from = b.source_from"
				+ " and b.id = c.activity_id"
				+ " and b.source_from = c.source_from"
				+ " and b.source_from = ?"
				+ " and (a.pmt_type = '200' or a.pmt_type = '003')"
				+ " and b.begin_time <= sysdate"
				+ " and b.end_time >= sysdate"
				+ " and c.org_id = ?"
				+ " and a.pmta_id in("
				+ " select a.pmta_id from es_promotion a, es_pmt_goods b"
				+ " where a.pmt_id = b.pmt_id"
				+ " and b.goods_id = ?)";
	}
	
	public String getACTIVE_TYPE_GET(){return "select (case "+
         " when b.cat_id = '690301000' then  5 "+
			" when b.cat_id = '690302000' then  4 "+
			" when b.cat_id = '690303000' then  3 "+
			" end) ative_type "+
			" from es_goods_rel a, es_goods b "+
			" where a.a_goods_id = ? "+
			" and b.type_id = 10001 "+
			" and b.type = 'product'" +
			" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
			" and a.z_goods_id = b.goods_id";}
	
	
	public String getPRODUCT_MODEL(){return "select b.model_code as specification_code, "+
				" (select c.model_name from es_brand_model c where c.model_code=b.model_code  "+ManagerUtils.apSFromCond("c")+") as specification_name "+
				" from es_goods_rel a, es_goods b "+
				" where a.a_goods_id = ? "+
				" and b.type_id = 10000 "+
				" and b.type = 'product'"+
				" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and a.z_goods_id = b.goods_id";}
	
	public String getIS_IPHONE_PLAN(){return "select (case "+
        " when b.cat_id = '690102000' then  '1' "+
			" else '0' "+
			" end) is_iphone_plan "+
			" from es_goods_rel a, es_goods b "+
			" where a.a_goods_id = ? "+
			" and b.type_id = 10002 "+
			" and b.type = 'product' "+
			" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
			" and a.z_goods_id = b.goods_id";}
	
	public String getCOLOR_GET(){return "select color color_code,(select pname from es_dc_public t "
			+ " where p.color=t.pkey and t.stype=10002"
			+ " and t.source_from = '" + ManagerUtils.getSourceFrom() + "') color_name "+
			" from es_goods g,es_product p, es_goods_rel r where g.goods_id=p.goods_id and p.product_id=r.product_id "+
			" and g.source_from='"+ManagerUtils.getSourceFrom()+"' and p.color is not null and a_goods_id=?";}
	
	public String getGOODS_INFO_GET_1 (){return "select g.goods_id,g.name goods_name,g.type_id product_type,g.params,p.price goods_price,m.model_code specification_code, "+
		       " m.model_name specificatio_name,b.name brand_name,b.brand_code brand_number,m.machine_code model_code,m.machine_name model_name,s.spec_value_id color_code,v.spec_value color_name,(case when (select count(*) from es_pmt_goods pmt where pmt.goods_id=g.goods_id) > 0 then 1 else 0 end) isgifts,p.product_id"+ 
		       " from es_goods g left join es_brand b on g.brand_id=b.brand_id left join es_brand_model m on b.brand_code=m.brand_code left join es_product p on g.goods_id=p.goods_id "+
		       " left join es_goods_spec s on s.product_id=p.product_id and s.spec_id=1 left join es_spec_values v on s.spec_value_id=v.spec_value_id"+
		       " where g.type='goods' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.sku=? ";}
	
	public String getGOODS_COLOR_GET (){return "select s.spec_value_id,v.spec_value from es_goods_spec s,es_spec_values v "+
			" where s.spec_id='1' and s.spec_value_id = v.spec_value_id and s.source_from='"+ManagerUtils.getSourceFrom()+"' and s.product_id=?";}
	
	public String getGOODS_REL_PRODUCT_LIST (){return "select p.* from es_product p left join es_goods_rel r on p.product_id=r.product_id where p.source_from=r.source_from and p.source_from='"+ManagerUtils.getSourceFrom()+"' and r.a_goods_id=?";}

	public String getGOODS_CHECK_SAVE(){return "select count(0) from ES_GOODS where  sku = ?";}
	public String getPRODUCT_COLOR_LIST (){return "SELECT P.PKEY VALUE, P.PNAME VALUE_DESC FROM ES_DC_PUBLIC P WHERE P.STYPE=10002 ORDER BY P.PKEY";}

	public String getPRODUCT_COLOR_GET (){return "select color from es_product where source_from='"+ManagerUtils.getSourceFrom()+"' and goods_id=?";}

	public String getGOODS_PRODUCT_LIST (){return "select g.goods_id,p.sku,p.product_id,p.name from es_goods g, es_product p where g.goods_id=p.goods_id ";}

	public String getCONTRACT_OFFER_LIST(){
		return "select g.goods_id,g.params,g.name, p.product_id, r.rel_code,p.sku,g.stype_id "
				+ " from es_goods g, es_goods_rel r,es_product p "
				+ " where g.goods_id=r.z_goods_id "
				+ " and g.goods_id=p.goods_id "
				+ " and g.source_from=p.source_from "
				+ " and g.source_from=r.source_from "
				+ " and g.source_from='"+ManagerUtils.getSourceFrom()+"'"
				+ " and r.a_goods_id=?";
		}
	
	public String getGOODS_ORG_LIST() {return "select b.* from es_goods_co a ,es_goods_org b where a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			" and a.source_from=b.source_from and a.org_id=b.party_id and a.goods_id=?" ;}
	
	//批量
	public String getBATCH_GOODS_ORG_LIST() {return "select b.*,g.name,g.goods_id from es_goods_co a ,es_goods_org b,es_goods g where a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			" and a.source_from=b.source_from and a.org_id=b.party_id and a.goods_id=g.goods_id " ;}
	
	public String getPRODUCT_ORG_LIST() {return "select b.* from es_product_co a ,es_goods_org b where b.org_level=3 and a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			" and a.source_from=b.source_from and a.org_id=b.party_id and a.product_id=?" ;}
	//批量
	public String getBATCH_PRODUCT_ORG_LIST() {return "select b.*,g.name,g.goods_id from es_product_co a ,es_goods_org b,es_goods g where b.org_level=3 and a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			" and a.source_from=b.source_from and a.org_id=b.party_id and a.goods_id=g.goods_id " ;}

	public String getGOODS_PUBLISH_CHECK(){
		return "select count(*) from es_goods_co where source_from='"+ManagerUtils.getSourceFrom()+"' and goods_id=?";
	}
	
	public String getPRODUCT_PUBLISH_CHECK(){
		return "select count(*) from es_product_co where source_from='"+ManagerUtils.getSourceFrom()+"' and product_id=?";
	}
	
	public String getGOODS_TAG_GET (){ return "select t.* from es_tag_rel r,es_tags t where r.tag_id=t.tag_id and t.source_from='"+ManagerUtils.getSourceFrom()+"' and r.rel_id=?";}

	public String getGOODS_REL_PRODUCT_DELETE (){return "delete from es_goods_rel where source_from='"+ManagerUtils.getSourceFrom()+"' and a_goods_id=?";}

	public String getGOODS_SELECT_TAGS(){return "select a.tag_id,a.tag_name,a.rel_count,a.cat_type from es_tags a  where  a.cat_type=100 and a.source_from='"+ManagerUtils.getSourceFrom()+"'";}

	public String getGET_SKU(){return "select p.sku from es_product p left join es_goods g on  p.goods_id = g.goods_id where 1 = 1";}

	public String getGOODS_GET(){return "select g.*,b.name brand_name,c.name cat_name,t.name type_name from es_goods g left join es_brand b on g.brand_id=b.brand_id left join es_goods_cat c on g.cat_id=c.cat_id "+
						" left join es_goods_type t on g.type_id=t.type_id where g.type='goods' and g.market_enable=1 and g.disabled=0 and g.goods_id=?";}
	
	public String getPRODUCT_GET(){return "select g.goods_id,g.brand_id,g.cat_id,g.type_id,g.weight,g.market_enable,g.brief,g.intro,g.cost,g.mktprice,g.have_spec,g.create_time,g.view_count,g.buy_count,g.isgroup,g.islimit,g.grade, "+
						" g.model_code,g.specifications,g.type_code,g.adjuncts,g.params,g.image_default,g.image_file,p.name,p.price,p.sku,p.sn,b.name brand_name,c.name cat_name,t.name type_name,p.sku psku,p.name pname,p.color "+
						" from es_goods g inner join es_product p on g.goods_id=p.goods_id "+ 
						" left join es_brand b on g.brand_id=b.brand_id "+
						" left join es_goods_cat c on g.cat_id=c.cat_id "+
						" left join es_goods_type t on g.type_id=t.type_id "+
						" where g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.type='product' and g.disabled=0 and g.market_enable=1 and p.product_id=?";}
	
	public String getUPDATE_GROUPBY_BUYED_COUNT_SELECT_BY_ID (){return "update es_group_buy set BUYED_COUNT =? where groupid=? and GOODSID = ?";}

	public String getMEMBER_PROVINCE_REGION(){return "select * from es_regions where region_grade =1";}
	
	public String getMEMBER_CITY_REGION(){return "select * from es_regions e where e.p_region_id in( select region_id from es_regions where region_grade =1)";}
	
	public String getMEMBER_COUNTRY_REGION(){return "select * from es_regions e where e.p_region_id = ? ";}
	
	public String getCHECKSTORESIZE(){return "select w.house_name,w.house_code from es_warehouse w join es_goods_inventory g on w.house_id=g.house_id and g.house_id=?  and g.inventory_num>0 ";}

	
	public String getGOODS_UPDATE_DISABLED_0(){ return "update  goods set disabled='"+Consts.GOODS_DISABLED_0+"' ";};
	
	
	public String getGOODS_CLARO(){return  "delete from es_goods t where 1=1 and t.disabled=1 ";}
	public String getGOODS_CLARO1(){return  "delete from es_product t where 1=1 ";}
	public String getGOODS_CLARO2(){return  "delete from es_goods_rel t where 1=1 ";}
	public String getGOODS_CLARO3(){return  "delete from es_goods_package t where 1=1 ";}
	
	final String WAREHOUSE_NOT_ASSIGN= " ";
	public String getVIRTUALHOUSE_GET_ALL (){return "select *from es_virtual_warehouse a where 1=1 ";}
	
	public String getINSERT_VIRTUALHOUSE(){return "update es_virtual_warehouse v set v.house_name=?,v.house_code=?,v.org_id_str=?,v.org_name_str=?,v.status=?,v.status_date=to_date(?,'yyyy-MM-dd hh24:mi:ss'),v.house_desc=?,v.org_id_belong=?,v.attr_inline_type=? where v.house_id=? ";}
	
	public String getGOODS_ORG_FIRST(){return "select o.PARTY_ID,o.parent_party_id,o.org_code,o.org_name,o.org_level,o.status_cd,o.party_type_id,o.store_id from es_goods_org o where o.parent_party_id=? ";}
	
	public String getCHECKVIRTUALSTORESIZE(){return "select v.house_id,v.house_name,v.house_code from es_virtual_warehouse v join es_goods_inventory_apply e on v.house_id=e.virtual_house_id and v.house_id= ? ";}
	
	public String getGOODSINVENTORYM(){return "select g.product_id,w.house_id,w.house_name,p.name,g.sku,g.inventory_num,g.no_apply_num,g.apply_num from es_goods_inventory g join es_warehouse w on w.house_id = g.house_id join es_product p on p.product_id=g.product_id where 1 = 1   and w.source_from = g.source_from and w.source_from ='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getHOUSENAMELIST(){return "select t.*,pc.comp_name from es_warehouse t left join es_product_comp pc on pc.comp_code=t.comp_code where t.source_from='"+ManagerUtils.getSourceFrom()+"'  ";}
	
	public String getPCLIST(){return "select * from es_product_comp t where 1=1 ";}
	
	public String getGOODS_PRODUCT(){return "select p.product_id,p.name,p.sku,p.sn,g.create_time from es_product p left join es_goods g on g.goods_id = p.goods_id and  g.type='product' and g.goods_type='normal' where g.disabled = 0 and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	final String GOODS_TAG_GET = "select t.* from es_tag_rel r,es_tags t where r.tag_id=t.tag_id and t.source_from='"+ManagerUtils.getSourceFrom()+"' and r.rel_id=?";

	final String GET_SKU = "select p.sku from es_product p left join es_goods g on  p.goods_id = g.goods_id where 1 = 1";
		
	public String  getOFFER_LIST(){
		return "select p.sku,g.goods_id,p.product_id,p.name,g.params,g.stype_id "
			+ " from es_goods g ,es_product p "
			+ " where g.goods_id=p.goods_id "
			+ " and g.source_from = p.source_from"
			+ " and g.source_from='"+ManagerUtils.getSourceFrom()+"' "
			+ " and g.type_id='"+Consts.GOODS_TYPE_OFFER+"' "
			+ " and g.type='product' ";}

	public String getP_CODE(){return "select rel_code from es_goods_rel where rel_type='CONTRACT_OFFER' and source_from='"+ManagerUtils.getSourceFrom()+"' and a_goods_id=? and z_goods_id=?";}
	
	public String getACTIVITY_ID_GET(){
		return "select id from es_promotion_activity where status_date =(select max(status_date) from es_promotion_activity where name =?) and name =?";
	}
	
	public String getPROMOTION_ID_GET(){
		return "select pmt_id from es_promotion where pmta_id=? and pmts_id='activity'";
	}

	public String getISEXITSSTORE(){return "select * from es_goods_inventory i where i.product_id=? and i.house_id=? ";}
	
	public String getUPDATEGOODSINVENTORY(){
		return "update es_goods_inventory "
				+ " set INVENTORY_NUM = INVENTORY_NUM + ?,"
				+ " NO_APPLY_NUM = NO_APPLY_NUM + ?,"
				+ " STATUS_DATE = " + DBTUtil.current()
				+ " where PRODUCT_ID=? and HOUSE_ID=? ";}

	/**
	 * 活动基本信息--活动标识、编码、名称、开始时间、结束时间、活动描述、活动类型、活动规则
	 * @return
	 */
	public String getGET_POMOTION_MAP() {
		return "select b.pmt_id, a.pmt_code, a.name as pmt_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = ?"
				+ " and source_from= '" + ManagerUtils.getSourceFrom() + "') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution"
				+ " from es_promotion_activity a"
				+ " inner join es_promotion b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.id = ?"
				+ " union"
				+ " select b.pmt_id, a.pmt_code, a.name as pmt_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = ?"
				+ " and source_from= '" + ManagerUtils.getSourceFrom() + "') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution"
				+ " from es_promotion_activity_log a"
				+ " inner join es_promotion_log b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.id = ?"; 
	};
	
	public String getACTIVITY_SPEC_MAP(){
		return "select b.pmt_id, a.pmt_code, a.name as pmt_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = ?"
				+ " and source_from= '" + ManagerUtils.getSourceFrom() + "') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution"
				+ " from es_promotion_activity a"
				+ " inner join es_promotion b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and b.pmts_id = 'activity' "
				+ " and a.source_from = ?"
				+ " and a.pmt_code = ? and rownum<2";
	}
	
	public String getACTIVITY_SPEC_MAP_LIST(){
		return "select b.pmt_id, a.pmt_code activity_code, a.name as activity_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type activity_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = ?"
				+ " and source_from= '" + ManagerUtils.getSourceFrom() + "') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution disacount_range, b.pmt_solution disacount_num"
				+ " from es_promotion_activity a"
				+ " inner join es_promotion b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and b.pmts_id = 'activity' "
				+ " and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
	}
	
	/**
	 * 参加折扣、直降、预售、满赠活动的商品列表（商品类型、商品名称、商品编码）
	 * @return
	 */
	public String getGET_GOODS_PMT() {
		return "select a.pmt_id, b.goods_id, b.sku, b.name as goods_name, "
				+ " c.type_id as goods_type_id, c.name as goods_type_name"
				+ " from es_pmt_goods a"
				+ " inner join es_goods b on a.goods_id = b.goods_id"
				+ " inner join es_goods_type c on b.type_id = c.type_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.pmt_id = ?"
				+ " union "
				+ " select a.pmt_id, b.goods_id, b.sku, b.name as goods_name, "
				+ " c.type_id as goods_type_id, c.name as goods_type_name"
				+ " from es_pmt_goods_log a"
				+ " inner join es_goods b on a.goods_id = b.goods_id"
				+ " inner join es_goods_type c on b.type_id = c.type_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.pmt_id = ?"; 
	};
	
	/**
	 * 团购 --团购价格 price、参加团购人数下限 limit_count、适用此团购活动的商品名称、商品编码
	 * @return
	 */
	public String getGET_GOODS_GROUP_BUY() {
		return "select a.groupid, a.price, a.buy_count as limit_count, "
				+ " b.goods_id, b.sku, b.name as goods_name"
				+ " from es_group_buy a"
				+ " inner join es_goods b on a.goodsid = b.goods_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.groupid = ?"; 
	};
	
	/**
	 * 秒杀 --秒杀价格、秒杀数量限制、适用此团购活动的商品名称、商品编码
	 * @return
	 */
	public String getGET_GOODS_LIMITBUY() {
		return "select a.limitbuyid, a.price, a.num as limit_count, "
				+ " b.goods_id, b.sku, b.name as goods_name"
				+ " from es_limitbuy_goods a"
				+ " inner join es_goods b on a.goodsid = b.goods_id"
				+ " where 1=1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = ?"
				+ " and a.limitbuyid = ?"; 
	};
	
	public String getACTIVITY_BASE_SQL() {
		return "select id, name, enable, begin_time, end_time, disabled,"
				+ " brief, userid, source_from, atturl, rank, pmt_code, "
				+ " min_price, max_price, region, package_class, relief_no_class, "
				+ " relation_id, modify_eff_time"
				+ " from es_promotion_activity "
				+ " where 1=1 ";
	}
	
	public String getPROMOTION_BASE_SQL() {
		return "select pmt_id, pmts_id, pmta_id, pmt_time_begin, pmt_time_end, order_money_from, "
				+ " order_money_to, seq, pmt_type, pmt_belong, pmt_bond_type, pmt_ifcoupon, "
				+ " pmt_update_time, pmt_basic_type, disabled, pmt_ifsale,pmt_distype, "
				+ " pmt_describe, pmt_solution, source_from, promotion_type "
				+ " from es_promotion "
				+ " where 1=1 ";
	}
	
	public String getPMT_GOODS_BASE_SQL() {
		return "select pmt_id, goods_id, source_from from es_pmt_goods "
				+ " where 1=1 ";
	}

	public String getECGOODSCO_LIST(){ 
		StringBuilder sql = new StringBuilder("select go.org_name, pc.created_date,");
		sql.append(" (case when  pc.status = '0' then  '未发布'   when  pc.status = '1' then  '已发布'  ");
		sql.append("   when  pc.status = '2' then  '发布中' when  pc.status = '3' then  '发布失败' else '0'  end ) status ");
		sql.append("  from es_goods_co pc ");
		sql.append("  inner join es_goods_org go    on pc.org_id = go.party_id");
		sql.append("  where 1 = 1 ");
		return	sql.toString();
	}
	public String getCHECK_SAVE(){return "select count(0) from es_goods_co where goods_id = ? ";}
	public String getECGOODSCO_ESTADOS(){return "select p.pkey key, p.pname value from es_dc_public p where p.stype=800420 order by p.pkey";}

	public String getGOODS_BY_IDS(){
		return "SELECT a.* FROM es_goods a WHERE 1=1";
	}
	
	public String getPARAMS_LIST(){return "select goods_id, params from es_goods where disabled=0";}
	
	public String getTYPE_PARAMS_LIST(){return "select type_id, params from es_goods_type where disabled=0";}
	
	public String getVIRTUAL_HOUSE_ID() {
		
		return "select a.inventory_num, b.house_id as virtual_house_id, b.attr_inline_type, b.org_id_str "
				+ " from es_goods_inventory_apply a, es_virtual_warehouse b"
				+ " where a.virtual_house_id = b.house_id"
				+ " and a.source_from = b.source_from"
				+ " and a.goods_id = ?"
				+ " and a.type = ?"
				+ " and a.house_id = ?"
				+ " and a.org_id = ?"
				+ " and a.inventory_num > 0"
				+ " and a.source_from = ?"
				+ " order by a.inventory_num desc";
	}
	
	public String getPRODUCT_FROM_GOODS() {
		return "select product_id from es_goods_rel"
				+ " where a_goods_id = ?"
				+ " and rel_type = ?"
				+ " and source_from = ?";
	}
	
	public String getUPDATE_GOODS_INVENTORY() {
		return "update es_goods_inventory set "
				+ " inventory_num = inventory_num - ?, "
				+ " apply_num = apply_num - ?, "
				+ " no_apply_num = no_apply_num - ?"
				+ " where product_id = ? and house_id = ? ";
	}
	
	public String getUPDATE_GOODS_INVENTORY_APPLY() {
		return "update es_goods_inventory_apply set "
				+ " inventory_num = inventory_num - ? "
				+ " where goods_id = ? and type = ? and virtual_house_id = ?";
	}
	
	public String getWARE_HOUSE_BY_HOUSE_ID() {
		return "select * from es_warehouse where house_id=?";
	}
	
	public String getWAREHOUSE_GET_GOODS_APPLY_LOG() {
		return "select a.*,"+
			 " (select p.pname from es_dc_public p where p.stype=111105 and p.pkey=a.action "
			 + " and p.source_from = '" + ManagerUtils.getSourceFrom() + "') action_name "+
			 " from es_goods_inventory_apply_log a where a.log_id=?";
	}
	
	public String getWAREHOUSE_PRODUCT_LIST_NOTASSIGN() {
		return 	"	select a.product_id,a.house_id,a.sku,a.inventory_num,a.no_apply_num,a.apply_num,b.house_name,c.name,c.goods_id	"+
						"	from es_goods_inventory a, es_warehouse b,es_product c,es_goods d	"+
						"	where a.house_id=b.house_id  and a.product_id=c.product_id 	"+
						"	and c.goods_id=d.goods_id and d.type='product'  and a.source_from='" + ManagerUtils.getSourceFrom() + "' ";
	}
	
	public String getWAREHOUSE_ASSIGN_LIST() {
		return 	"	select 	"+
				"	(select r.org_name from es_goods_org r where r.party_id=a.org_id "+ManagerUtils.apSFromCond("r")+") org_name,	"+
				"	c.name name,	"+
				"   (select s.product_id from es_product s where s.goods_id=a.goods_id  "+ManagerUtils.apSFromCond("s")+") product_id,"+
				"	(select t.house_name from es_warehouse t where t.house_id=a.house_id  "+ManagerUtils.apSFromCond("t")+") house_name,	"+
				"	(select t.house_name from es_virtual_warehouse t where t.house_id=a.virtual_house_id  "+ManagerUtils.apSFromCond("t")+") v_house_name,	"+
				"	(select t.org_id_str from es_virtual_warehouse t where t.house_id=a.virtual_house_id  "+ManagerUtils.apSFromCond("t")+") org_id_str,	"+
				"	(select t.org_name_str from es_virtual_warehouse t where t.house_id=a.virtual_house_id  "+ManagerUtils.apSFromCond("t")+") org_name_str,	"+
				"	a.is_share,a.inventory_num,a.goods_id,a.org_id,a.house_id,a.virtual_house_id,a.sku	"+
				"	from es_goods_inventory_apply a,es_product b,es_goods c where a.goods_id=b.goods_id and b.goods_id=c.goods_id   "+ManagerUtils.apSFromCond("b")+"  "+ManagerUtils.apSFromCond("c")+" and a.source_from='" + ManagerUtils.getSourceFrom() + "' and a.inventory_num>0 ";
	}
	
	public String getWAREHOUSE_GET_PRODUCT_LIST() {
		return 	"  select a.product_id,a.name,a.sn,a.sku,a.goods_id from es_product a,es_goods b " +
				"  where a.goods_id=b.goods_id and a.source_from='" + ManagerUtils.getSourceFrom() + "'"; 
	}
	
	public String getWAREHOUSE_GET_GOODS_LIST() {
		return 	"	  select distinct a.product_id,b.name,a.sn,a.sku,a.goods_id from es_product a,es_goods b,es_goods_rel c 	"+
				"	   where a.goods_id=b.goods_id and b.goods_id=c.a_goods_id and c.rel_type='"+Consts.PRO_REL_GOODS+"' 	"+
				"	   and a.source_from='" + ManagerUtils.getSourceFrom() + "'"; 
	}
	
	public String getWAREHOUSE_VIRTUAL_HOUSE_LIST() {
		return 	" select a.house_id,a.house_name,a.house_code,a.org_id_str,a.org_name_str,a.attr_inline_type from es_virtual_warehouse a where  a.status='00A'"; 
	}
	

	
	public String getACTIVITY_GOODS_LIST (){
		return "	select a.product_id,a.name,a.sku,a.goods_id,a.sn,b.brand_id,b.model_code from es_product a,es_goods b where b.source_from = '"+ManagerUtils.getSourceFrom()+"'	"+
			"	 and a.sku is not null and a.goods_id = b.goods_id and a.source_from = b.source_from  ";
	}
	
	public String getACTIVITY_GOODS_PACKAGE_LIST (){
		return "select distinct a.relation_id as goods_id, a.relation_id as sku, a.relation_name as name "
				+ " from es_relation a "
				+ " left join es_relation_detail b on a.relation_id=b.relation_id "
				+ " left join es_goods c on b.object_id = c.goods_id "
				+ " where a.source_from = '" + ManagerUtils.getSourceFrom() + "'";
	}
	
	public String getDELETE_PROMOTION_GIFTS() {
		
		return "delete from es_promotion a where 1=1 and a.pmta_id=? "
				+ " and a.pmt_type='"+ Consts.PMT_TYPE_GIFT + "'";
	}
	public String getDELETE_PROMOTION_GOODS(){
		return "delete from es_pmt_goods a where a.pmt_id=?";
	}
	
	public String getDELETE_PROMOTION_CO(){
		return "delete from es_activity_co a where a.activity_id=?";
	}
	
	public String getPROMOATION_GIFT(){
		return "select * from es_promotion a where a.pmt_type='"+Consts.PMT_TYPE_GIFT+"' and a.pmta_id=?";
	}
	
	public String getACTIVITY_PROMO_GOODS(){
		return " select a.* from es_goods a , es_pmt_goods b where a.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			   " and a.source_from=b.source_from and  a.goods_id=b.goods_id and b.pmt_id=?";
	}
	
	public String getACTIVITY_CO_BY_ACTIVITY_ID(){
		return " select a.*," +
				"  (select s.org_name from es_goods_org s where s.party_id=a.org_id  "+ManagerUtils.apSFromCond("s")+") org_name" +
				"  from es_activity_co a where a.activity_id = ?";
	}
	
	public String getPROMOATION_ACTIVITY(){
		return "select * from es_promotion a where a.pmt_type!='"+Consts.PMT_TYPE_GIFT+"' and a.pmta_id=?";
	}

	public String getTERMINAL_BY_MODELCODE_COLOR(){
		return "select a.goods_id,a.model_code,b.product_id,a.brand_id,b.sku,b.sn,a.cat_id "
				+ " from es_goods a, es_product b where a.goods_id = b.goods_id "
				+ " and a.source_from=b.source_from "
				+ " and a.source_from='"+ManagerUtils.getSourceFrom()+"' "
				+ " and a.type = 'product' and a.type_id = '10000' "
				+ " and a.model_code = ? and b.color = ?";
	}
	
	public String getTERMINAL_BY_TERMINALS_CODE(){
		return "select a.goods_id,a.model_code,b.product_id,a.brand_id,b.sku,b.sn,a.cat_id "
				+ " from es_goods a, es_product b where a.goods_id = b.goods_id "
				+ " and a.source_from=b.source_from "
				+ " and a.source_from='"+ManagerUtils.getSourceFrom()+"' "
				+ " and a.type = 'product' and a.type_id = '10000' "
				+ " and a.sn= ?";
	}
	
	public String getOFFER_GET_BY_ESSCODE(){
		return "select a.goods_id,a.name,b.product_id,a.sku,a.model_code,b.sn from es_goods a,es_product b where a.goods_id=b.goods_id and a.type = 'product' "+ 
				" and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.type_id = '10002' ";
	}
	
	public String getCONTRACT_GET_BY_NAME(){
		return "select a.goods_id,b.product_id,a.sku,b.sn from es_goods a,es_product b where a.goods_id=b.goods_id and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and type_id='10001' and a.name=?";
	}
	
	public String getCAT_LIST(){
		return "select cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,floor_list_show,type,hot_type from es_goods_cat where 1=1 ";
	}
	
	public String getBrandModelListECS(){
		return "select * from es_brand_model a where 1=1 ";
	}
	
	public String getPARENT_PARTY_ID(){
		return "select parent_party_id from es_goods_org where party_id=?";
	}
	
	public String getGOODS_ORG_ID() {
		return "select distinct a.party_id from es_goods_org a where 1=1 ";
	}
	
	public String getGOODS_ZJ_ORG_ID() {
		return "select distinct a.party_id from es_goods_zj_org a where 1=1 ";
	}
	

	public String getBRAND_MODEL_BY_NAME(){
		return "select * from es_brand_model a where a.model_name=? ";
	}
	
	public String getBRAND_MODEL_BRAND_LIST(){
		return "select * from es_brand e where 1=1 and e.disabled=0 ";
	}
	
	public String getBRAND_MODEL_DETAIL(){
		return "select * from es_brand_model a where a.brand_model_id=? ";
	}
	
	public String getGOODS_LEVEL_LIST(){
		return "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=800423 order by p.sortby asc";
	}
	
	public String getGOODS_REF_LIST(){
		return "select * from es_goods_rel a where a.a_goods_id = ?";
	}
	
	
	
	public String getCONTRACT_MACHINE(){
		return "select * from es_goods a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and exists (select 1 from es_goods_rel b where b.a_goods_id=a.goods_id and product_id=?  "+ManagerUtils.apSFromCond("b")+") "+
				"and exists (select 1 from es_goods_rel b where b.a_goods_id=a.goods_id and product_id=?  "+ManagerUtils.apSFromCond("b")+")  and exists (select 1 from es_goods_rel b where b.a_goods_id=a.goods_id and product_id=?  "+ManagerUtils.apSFromCond("b")+")";
	}
	
	public String getGOODS_PRICE_UPDATE(){
		return "update es_goods set price=?,mktprice=? where source_from='"+ManagerUtils.getSourceFrom()+"' and goods_id=?";
	}
	
	public String getNUMBER_CARD_GET(){
		return "select * from es_goods a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and exists (select 1 from es_goods_rel b where b.a_goods_id=a.goods_id and product_id=?  "+ManagerUtils.apSFromCond("b")+")  " +
				"and exists (select 1 from es_goods_rel b where b.a_goods_id=a.goods_id and product_id=?  "+ManagerUtils.apSFromCond("b")+")";
	}
	
	public String getCAT_ID(){
		return "select cat_id from es_goods a,es_product b where a.goods_id=b.goods_id and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and product_id=? and rownum<2";
	}
	
	public String getCONTRACT_GET_BY_SPEC(){
		return "select a.goods_id,b.product_id,b.sn,c.sku from es_contract_map a,es_product b,es_goods c where a.goods_id=b.goods_id and b.goods_id=c.goods_id and c.source_from='"+ManagerUtils.getSourceFrom()+"' and a.contract_type=? and a.contract_limit=? and a.contract_net=? and a.is_iphone=? ";
	}
	
	public String getCONTRACT_GET(){
		return "select a.goods_id,a.sku,b.product_id,b.sn from es_goods a,es_product b where a.goods_id=b.goods_id and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and type_id='10001' and a.goods_id=?";
	}
	
	public String getGOODS_BATCH_ID(){
		return "select batch_id from es_goods_co where source_from='"+ManagerUtils.getSourceFrom()+"' and batch_id is not null and goods_id=?";
	}
	
	public String getPRODUCT_BATCH_ID(){
		return "select batch_id from es_product_co where source_from='"+ManagerUtils.getSourceFrom()+"' and batch_id is not null and product_id=?";
	}
	
	public String getGOODS_IMPORT_LOGS(){
		return " select a.batch_id,a.log_id,a.rel_code,a.atv_code,a.atv_name,a.atv_months,a.deposit_fee,a.order_return,a.mon_return,a.product_code,a.product_name,a.model_name,a.model_code,"+
				" a.created_date,a.oper_id,b.realname oper_name,a.deal_flag,a.status_date,a.deal_num,a.atv_desc,a.offer_terminals,a.comments,a.prod_brand_desc,"+
				" a.fee_type,a.no_gen,a.contract_fee,a.terminals_name,a.terminals_code,a.brand_name,a.brand_code,a.color_name,a.color_code,a.goods_type_name,"+
				" a.deal_desc,a.batch_amount,a.goods_desc from es_goods_import_logs a left join es_adminuser b on a.oper_id=b.userid where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getJUDGE_GOODS_IMPORT_LOGS() {
		
		return "select count(*) from es_goods_import_logs where 1=1 ";
	}
	
	public String getJUDGE_ACTIVITY_IMPORT_LOGS() {
		
		return "select count(*) from es_activity_import_logs where 1=1 ";
	}
	
	public String getJUDGE_ZDB_IMPORT_LOGS() {
	
		return "select count(*) from es_zdb_import_logs where 1=1 ";
	}
	
	public String getZDB_TO_PRO(){
		return "select * from es_zdb_import_logs where deal_type='PLDR' and deal_flag='0' ";
	}
	
	public String getZDB_EDIT_PRO(){
		return "select * from es_zdb_import_logs where deal_type='PLXG' and deal_flag='0' ";
	}
	
	public String getZDB_DEL_PRO(){
		return "select * from es_zdb_import_logs where deal_type='PLHS' and deal_flag='0' ";
	}

	public String getJUDGE_TERMINAL_IMPORT_LOGS() {
	
	return "select count(*) from es_terminal_import_logs where 1=1 ";
	}
	
	public String getADMINUSER(){
		return "select a.userid,a.paruserid,a.founder from es_adminuser a where userid=? ";
	}
	
	public String getACTIVITY_IMPORT_LOGS(){
		return "select a.*,b.realname from es_activity_import_logs a left join es_adminuser b on a.oper_id=b.userid where 1=1";
	}
	
	//根据货品包名称获取商品的ID
	public String getGOODS_ID_RELATION(){
		return "select object_id from es_relation_detail where relation_id=(" +
				"select relation_id from es_relation where status_date in( select max(status_date) from es_relation where relation_name=?) and relation_name=?)";
	}
	
	//根据商城名字获取商城的ID
	public String getORG_ID_STR(){
		return "select party_id from es_goods_org where org_name=?";
	}
	
	//根据SKU获取goods_id
	public String getGOODS_ID_SKU(){
		return "select goods_id from es_goods where sku=?";
	}
	
	//商品同步失败、未发送、发送中
	public String getGOODS_SYNCH_FAIL_LOG(){
		return " select q.batch_id,q.co_id,q.co_name,q.action_code,q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.goods_id,g.sku,g.name "+
				" from es_goods g,es_co_queue q where g.goods_id=q.object_id and g.source_from=q.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and g.type='goods' and q.service_code='CO_SHANGPIN' ";
	}
	
	//商品同步成功
	public String getGOODS_SYNCH_SUCCESS_LOG(){
		return "select q.batch_id,q.co_id,q.co_name,q.action_code,q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.goods_id,g.sku,g.name "+
				" from es_goods g,es_co_queue_bak q where g.goods_id=q.object_id and g.source_from=q.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and g.type='goods' and q.service_code='CO_SHANGPIN'";
	}
	//货品同步失败、未发送、发送中
	public String getPRODUCT_SYNCH_FAIL_LOG(){
		return " select q.batch_id,q.co_id,q.co_name,q.action_code,q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.goods_id,g.sku,g.name "+
				" from es_goods g,es_product p,es_co_queue q where g.goods_id=p.goods_id and p.product_id=q.object_id and g.source_from=p.source_from and p.source_from=q.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and g.type='product' and q.service_code='CO_HUOPIN' ";
	}
	//货品同步成功
	public String getPRODUCT_SYNCH_SUCCESS_LOG(){
		return "select q.batch_id,q.co_id,q.co_name,q.action_code,q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.goods_id,g.sku,g.name "+
				" from es_goods g,es_product p,es_co_queue_bak q where g.goods_id=p.goods_id and p.product_id=q.object_id and g.source_from=p.source_from and p.source_from=q.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and g.type='product' and q.service_code='CO_HUOPIN'";
	}
	//型号同步成功
		public String getMODEL_SYNCH_SUCCESS_LOG(){
			return "select q.batch_id,  q.co_id, q.co_name, q.action_code, q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.brand_model_id,g.model_code,g.Model_Name"+
					" from es_brand_model g, es_co_queue_bak q where g.Brand_Model_Id = q.object_id and g.source_from = q.source_from and g.source_from ='"+ManagerUtils.getSourceFrom()+"'"+
					"and q.service_code = 'CO_XINGHAO'";
		}
		
		//型号同步成功
				public String getMODEL_SYNCH_FAIL_LOG(){
					return "select q.batch_id,  q.co_id, q.co_name, q.action_code, q.service_code,q.status,q.status_date,q.created_date,q.failure_desc,q.deal_num,q.org_id_str,q.oper_id,g.brand_model_id,g.model_code,g.Model_Name"+
							" from es_brand_model g, es_co_queue q where g.Brand_Model_Id = q.object_id and g.source_from = q.source_from and g.source_from ='"+ManagerUtils.getSourceFrom()+"'"+
							" and q.service_code='CO_XINGHAO'";
				}
	
	//每个批次同步成功、失败、未发送、发送中数量统计
	public String getCYNCH_NUM_COUNT(){
		return "select count(*) amount,'WFS' status from es_co_queue where batch_id=? and status='WFS' and source_from='"+ManagerUtils.getSourceFrom()+"' "+
				" union"+
				" select count(*) amount,'FSZ' status from es_co_queue where batch_id=? and status='FSZ' and source_from='"+ManagerUtils.getSourceFrom()+"' "+
				" union"+
				" select count(*) amount,'XYSB' status from es_co_queue where batch_id=? and status='XYSB' and source_from='"+ManagerUtils.getSourceFrom()+"' "+
				" union"+
				" select count(*) amount,'XYCG' status from es_co_queue_bak where batch_id=? and status='XYCG' and source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	
	//批量查询多个批次同步成功、失败、未发送、发送中数量统计
	public String getBATCH_CYNCH_NUM_COUNT(){
		return "select count(*) amount,'WFS' status, batch_id from es_co_queue where batch_id in($PARAMS$) and status='WFS' and source_from='"+ManagerUtils.getSourceFrom()+"'  group by batch_id"+
				" union all"+
				" select count(*) amount,'FSZ' status, batch_id from es_co_queue where batch_id in($PARAMS$) and status='FSZ' and source_from='"+ManagerUtils.getSourceFrom()+"'  group by batch_id"+
				" union all"+
				" select count(*) amount,'XYSB' status, batch_id from es_co_queue where batch_id in($PARAMS$) and status='XYSB' and source_from='"+ManagerUtils.getSourceFrom()+"'  group by batch_id"+
				" union all"+
				" select count(*) amount,'XYCG' status, batch_id from es_co_queue_bak where batch_id in($PARAMS$) and status='XYCG' and source_from='"+ManagerUtils.getSourceFrom()+"'  group by batch_id";
	}
	
	
	public String getSYNCH_ORG_GET(){
		return "SELECT wmsys.wm_concat(org_name) org_name_str FROM ES_GOODS_ORG WHERE SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"'";
	}
	
	public String getMARKET_ENABLE_VALUE(){
		return "select market_enable from es_goods where goods_id=? ";
	}
	
	/**************************** 手机货品导入  ********************************/
	
	public String getPHONE_IMPORT_LOGS(){
		return  "select batch_id,batch_amount,log_id,cat_id,brand_name,model_name,color_name,is_4g,sn,brand_code,model_code,color_code,terminal_shape,system_type_code,system_type_name,network,data_transfer,browser,terminal_memory,ex_memory,screen_size," +
				"is_widescreen,screen_type,screen_ratio,touch_screen,screen_color,music_play,video_play,e_book,sound_record,is_mms,is_office,front_camera,back_camera,video_record,sensor_type,zoom_model,is_gprs,is_bluetooth,battery,battery_capacity," +
				"talk_time,standby_time,long_standby,weight,character,special_feature,va_business,terminal_size,battery_type,system_version,cpu_desc,ram_desc,run_memory,is_double_card,oper_id,deal_flag,deal_num,status_date,created_date,deal_desc " +
				"from es_terminal_import_logs where 1=1 ";
	}
	
	public String getTERMINAL_GET_BY_SN(){
		return "select g.goods_id,g.brand_id,p.product_id from es_goods g,es_product p where g.goods_id=p.goods_id and g.source_from=p.source_from and g.type='product' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.sn=? ";
	}
	
	public String getTERMINAL_GET_BY_NAME(){
		return "select g.goods_id,g.brand_id,p.product_id from es_goods g,es_product p where g.goods_id=p.goods_id and g.source_from=p.source_from and g.type='product' and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.name=? ";
	}
	
	public String getMODEL_COUNT(){
		return "select count(*) from es_brand_model where source_from='"+ManagerUtils.getSourceFrom()+"' and model_code=? ";
	}
	
	public String getBRAND_COUNT(){
		return "select brand_id from es_brand where source_from='"+ManagerUtils.getSourceFrom()+"' and brand_code=? ";
	}
	
	public String getCOLOR_COUNT(){
		return "select count(*) from es_dc_public where stype=10002 and pkey=?";
	}
	
	public String getGOODS_TYPE_PARAMS(){
		return "select params from es_goods_type where source_from='"+ManagerUtils.getSourceFrom()+"' and type=? and type_id=? ";
	}
	
	public String getDELETE_ACTIVITY_BY_ID(){
		return "delete from es_promotion_activity t where t.id=?";
	}
	
	public String getDELETE_PMT_GOODS(){
		return "delete from es_pmt_goods where 1=1 ";
	}
	
	/**************************** 手机货品导入  ********************************/
	
	/**************************** 货品包  ********************************/
	public String getPRODUCT_PKG_LIST(){
		return "select relation_id,relation_name,b.name relation_type,comments,oper_id,created_date,status,status_date,contract_goods_id,model_code " +
				"from es_relation a,es_goods_type b where a.relation_type=b.type_id and a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCOLOR_LIST_BY_MODEL_CODE(){
		return "SELECT distinct b.color color_code,(select pname from es_dc_public c where stype=10002 and c.pkey=b.color) color_name "+
         " FROM ES_GOODS A, ES_PRODUCT B "+
         " WHERE A.Goods_Id=b.goods_id and a.source_from=b.source_from and b.color is not null and a.source_from='"+ManagerUtils.getSourceFrom()+"' and MODEL_CODE = ?";
	}
	
	public String getPACKAGE_GOODS_LIST(){
		return " select g.goods_id,g.sku,g.name,g.type_id,t.name type_name,g.create_time,g.market_enable, "+
				" (select count(*) from es_goods_co gc where gc.goods_id=g.goods_id) state "+
				" from es_relation_detail rd,es_goods g,es_goods_type t " +
				" where rd.object_id=g.goods_id and g.type_id=t.type_id and rd.source_from=g.source_from and g.source_from=t.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getRELATION_DETAIL_LIST(){
		return "select detail_id,relation_id,object_id from es_relation_detail where relation_id=? ";
	}
	
	public String getGOODS_TYPE_BY_GOODS_ID(){
		return "select goods_id,type_id,model_code from es_goods where 1=1 ";
	}
	
	public String getPACKAGE_GOODS_CHECK(){
		return "select count(*) from es_relation_detail where relation_id=? and object_id=? ";
	}
	
	public String getPACKAGE_GOODS_DELETE(){
		return "delete from es_relation_detail where object_id=? ";
	}
	
	/**************************** 货品包  ********************************/
	
	//活动同步日志
	public String getACTIVITY_SYNCH_LOGS(){
		return "select a.pmt_code,a.name,b.batch_id,b.action_code,b.object_id,b.status,b.status_date,b.created_date,b.failure_desc,b.org_id_str,b.co_id " +
				"from es_promotion_activity a,";
	}
	
	public String getTERMINAL_MODEL_CODE(){
		return "select model_code from es_goods where type_id=? ";
	}
	
	public String getGOODS_IDS_LIST(){
		return "select goods_id from es_goods t where t.disabled=0 and t.market_enable=1 and t.type='goods' ";
	}
	
	public String getGOODS_SPEC(){
		return "select g.*,p.product_id,b.brand_code,b.name brand_name,p.color color_code,g.CRM_OFFER_ID," +
				"(select pname from es_dc_public dc where dc.stype='10002' and dc.source_from='"+ManagerUtils.getSourceFrom()+"' and dc.pkey=p.color ) color_name," +
				"(select m.model_name from es_brand_model m where g.model_code=m.model_code and b.brand_code=m.brand_code) model_name," +
				"(select m.machine_code from es_brand_model m where g.model_code=m.model_code and b.brand_code=m.brand_code) machine_code," +
				"(select m.machine_name from es_brand_model m where g.model_code=m.model_code and b.brand_code=m.brand_code) machine_name "+
				"from es_goods g left join es_product p on g.goods_id=p.goods_id "+
				"left join es_brand b on g.brand_id=b.brand_id where g.brand_id=b.brand_id and g.disabled = ? and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCONTRACT_P_CODE(){
		return "select pk.p_code from es_goods_package pk,es_goods g where pk.goods_id=g.goods_id and pk.source_from=g.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"' and g.type_id='20002' and pk.goods_id=? ";
	}
	
	public String getP_CODE_1(){
		return "select distinct a.p_code from es_goods_package_for_10003 a where a.goods_id = ? and p_code is not null and sn is not null";
	}
	
	public String getP_CODE_2(){
		return "select distinct c.p_code from es_goods_package c where c.goods_id = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"' and p_code is not null and sn is not null and not exists (select 1 from ES_GOODS_PACKAGE_FOR_10003 d where d.goods_id = c.goods_id and d.p_code = c.p_code and d.sn = c.sn and d.source_from = '"+ManagerUtils.getSourceFrom()+"' and d.source_from = c.source_from)";
	}
	
	public String getACTIVITY_ID(){
		return "select id from es_promotion_activity c where c.pmt_code = ? ";
	}
	
	public String getGOODS_PACKAGE_UPDATE(){
		return "update es_goods_package set p_code=? where goods_id=? ";
	}
	
	public String getGOODS_CAT_LIST_BY_TYPE(){
		return "select * from es_goods_cat where type_id=? and source_from = '"+ManagerUtils.getSourceFrom()+"' order by parent_id";
	}
	
	public String getGOODS_PARAMS_LIST(){
		return "select egc.cat_path,c.goods_id,c.name,c.sku,c.type_id,c.cat_id,c.params from es_goods c left join es_goods_cat egc on egc.cat_id = c.cat_id and egc.source_from = c.source_from where c.source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	
	public String getCOUNT_GOODS(){
		return "select count(*) from es_goods where goods_id=? ";
	}
	
	public String getCOUNT_PRODUCT(){
		return "select count(*) from es_product where goods_id=? ";
	}
	
	public String getCOUNT_GOODS_REL(){
		return "select count(*) from es_goods_rel where a_goods_id=? and z_goods_id=? ";
	}
	
	public String getCOUNT_GOODS_PACKAGE2(){
		return "select count(*) from es_goods_package where goods_id=?";
	}
	
	public String getTERMINAL_NUM_LIST(){
		return "select a.* from es_gd_es_terminal a where 1=1";
	}

	public String getPROMOTION_COUNT(){
		return "select count(*) from es_promotion where pmt_id=? ";
	}
	
	public String getPROMOTION_ACTIVITY_COUNT(){
		return "select count(*) from es_promotion_activity where id=? ";
	}
	
	public String getACTIVITY_EXPORT_DATA(){
		return "select a.id activity_id, a.pmt_code,a.name activity_name,a.enable, b.pmt_type pmt_type_code, b.pmt_solution, a.relation_id, " +
				"a.package_class,a.min_price,a.max_price,a.region,a.modify_eff_time, a.begin_time, " +
				"a.end_time,a.brief,a.relation_id,a.relief_no_class from es_promotion_activity a, es_promotion b " +
				"where a.id = b.pmta_id and b.pmts_id = 'activity' and a.source_from='"+ManagerUtils.getSourceFrom()+"' and b.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getQUEUE_SYN_STATUS_COUNT(){
		return "select * from es_co_queue_bak a where status_date in(select max(status_date) from es_co_queue_bak a " +
				"where object_id=? group by a.org_id_belong ) and object_id=?";
	}
	
	public String getGOODS_PLATFORM_INFO_DELETE(){
		return "delete from es_goods_plantform_info where goods_id =?";
	}
	
	public String getSPEC_VALUE_GET(){
		return "select t.* from es_spec_values t where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.spec_value=?";
	}
	
	public String getPROXY_GOODS_DELETE(){
		return "delete from es_goods_proxy where userid=? and p_goods_id=? and source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getGOODS_SPEC_COPY(){
		return "select t.* from es_goods_spec t where t.goods_id=? ";
	}
	
	public String getGOODS_TAG_REL_COPY(){
		return "select t.* from es_tag_rel t where t.rel_id=? ";
	}
	
	public String getGOODS_PLATFORM_INFO_COPY(){
		return "select t.* from es_goods_plantform_info t where t.goods_id=? ";
	}
	
	public String getGOODS_PRICE_PRIV_COPY(){
		return "select t.* from es_price_priv t where t.goods_id=? ";
	}
	
	public String getCAT_GOODS_NUM_UPDATE(){
		return "update es_goods_cat set goods_count = goods_count+1 where cat_id=? and source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCAT_GOODS_NUM_UPDATE_MINUS(){
		return "update es_goods_cat set goods_count = goods_count-1 where cat_id=? and goods_count>0 and source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	public String getORG_ID_BY_ACTIVITY_ID(){
		return "select a.org_id from es_activity_co a where a.activity_id=? and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getACTIVITY_BY_ID(){
		return 
				"select a.pmt_code pmt_code,a.name name,case a.enable when 0 then '失效' when 1 then '生效' else '' end enable, " +
				"       case a.user_type when 1 then '' when 2 then '新用户' when 3 then '老用户' else '用户类型错误' end user_type, " + 
				"       case b.pmt_type when '001' then '打折' when '006' then '直降' when '005' then '预售' when '008' then '团购' " + 
				"         when '009' then '秒杀' when '010' then '促销' when '011' then '溢价' when '012' then '常规促销' " + 
				"         when '013' then '月度主促'  when '014' then '年度大促' else '' end pmt_type, " + 
				"       b.pmt_solution pmt_price, a.relation_id relation_name, a.package_class package_class, " + 
				"       a.min_price min_price, a.max_price max_price, a.region region, to_char(a.modify_eff_time,'yyyy-mm-dd') modify_eff_time, " + 
				"       org.org_name org_id_str, " + 
				"       to_char(a.begin_time,'yyyy-mm-dd hh24:mi:ss') || '~' || to_char(a.end_time,'yyyy-mm-dd hh24:mi:ss') available_period, " + 
				"       a.brief brief " + 
				"  from es_promotion_activity a, es_promotion b, es_activity_co c " + 
				"  left join es_goods_org org on c.org_id = org.party_id " + 
				" where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.id = b.pmta_id and a.id = c.activity_id and a.id = ?";
		}
	public String getPRODUCT_INFO_LIST(){
		return "select t.* from es_product t where 1=1 ";
	}
	
	/**================BEGIN zhengchuiliu====================*/
	public String getES_TERMINAL_LIST(){
		return "select t.* from es_gd_es_terminal t where 1=1 ";
	}
	
	public String getES_TERMINAL_DETAIL(){
		return "select t.* from es_gd_es_terminal t where t.sn=? ";
	}
	
	public String getES_TERMINAL_DELETE(){
		return "delete from es_gd_es_terminal where 1=1 ";
	}
	
	public String getPRODUCTS_SELECT_TAGS(){
		return "select t.sn from es_gd_es_terminal t where 1=1 ";
	}
	
	public String getIS_EXISTS_SN (){
		return "select count(1) sn from es_gd_es_terminal t where t.sn = ?";
	}
	
	public String getGOODS_IMPORT_LOGS_BY_OPERNAME (){
		return "select * from (select rownum as rn , t.product_code,t.atv_code,t.atv_months,t.product_name,t.model_code,t.color_code,t.status_date,a.realname  "+
			   "from es_goods_import_logs t left join es_adminuser a on a.userid = t.oper_id " +
			   "where a.realname like ? and t.deal_desc like '%成功%' and t.source_from = '" + ManagerUtils.getSourceFrom() + "' " +
			   "and rownum <= ?) tb where tb.rn >=?";
	}
	
	public String getGOODS_IMPORT_LOGS_BY_OPERNAME_LIST (){
		return "select t.product_code,t.atv_code,t.atv_months,t.product_name,t.model_code,t.color_code,t.status_date,a.realname,t.create_date  "+
			   "from es_goods_import_logs t left join es_adminuser a on a.userid = t.oper_id " +
			   "where  a.realname like ? and t.deal_desc like '%成功%' and t.source_from = '" + ManagerUtils.getSourceFrom() + "' " ;
	}
	
	//广东联通ecs
	public String getSEARCH_GOODS_0_2_LIST (){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct g.goods_id,g.sku,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.mktprice,g.simple_name,g.weight,");
		sql.append("g.market_enable,g.create_time,g.last_modify,t.name as type_name,t.have_stock as have_stock," );
		sql.append("t.have_price as have_price,c.name cat_name,b.name brand_name,g.creator_user  apply_userid, g.audit_state ,");
		sql.append("(select username from es_adminuser u where u.userid = g.creator_user and g.source_from = u.source_from) apply_username ");
		sql.append("from es_goods g  left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
		sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"");
		sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
		sql.append("left join es_goods_co egc  on egc.goods_id = g.goods_id "+ManagerUtils.apSFromCond("egc")+"");
		sql.append("left join es_goods_org  ego  on ego.party_id = egc.org_id "+ManagerUtils.apSFromCond("ego")+"");
		
		//sql.append("left join es_brand_model m on m.brand_code = b.brand_code "+ManagerUtils.apSFromCond("m")+"");//add by liqingyi
		sql.append("and g.source_from = t.source_from ");
		
		return sql.toString();
	}
	
	public String getGOODS_ (){
		return "select * from ( " +
				"select rownum as rn ,t.product_code,t.atv_code,t.atv_months,t.product_name,t.model_code,t.color_code " +
				"from es_goods_import_logs t left join es_adminuser a on a.userid = t.oper_id " +
				"where a.realname like ? and t.deal_desc like '%成功%' and t.source_from = '" + ManagerUtils.getSourceFrom() + "' " +
				"and rownum <= ?) tb where tb.rn >=?";
	}
	/**================END zhengchuiliu====================*/
	
	public String getGoods_Is_Sync(){
		return "select count(*) from es_co_queue_bak t where t.object_id=? and t.org_id_str like ? and t.service_code='CO_SHANGPIN'";
	}
	
	public String getACTIVITY_PROMO_GOODS_BY_ID(){
		return " select t.* from es_goods t  where t.source_from='"+ManagerUtils.getSourceFrom()+"' " +
			   " and exists  (select 1 from es_promotion b,es_pmt_goods c where  b.pmt_id=c.pmt_id" +
			   " and t.goods_id=c.goods_id  and b.pmta_id=? )";
	}
	
	public String getAct_Is_Sync(){
		return " select count(*) from es_co_queue_bak t where t.object_id=?  and t.org_id_str=? and t.status='1' and t.service_code='CO_HUODONG'";
	}
	
	public String getProducts_Is_Sync(){
		return "select count(*) from es_co_queue_bak t where t.object_id=? and t.org_id_str like ? and t.service_code='CO_HUOPIN'";
	}
	
	
	//广东联通ecs操作日志
		public String getSEARCH_GOODS_0_2_OPER_LOG(){
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct g.goods_id,g.sku,g.type_id,g.crm_offer_id,g.sn,g.name,g.price,g.mktprice,g.simple_name,g.weight,");
			sql.append("g.market_enable,g.create_time,t.name as type_name,t.have_stock as have_stock,g.oper_no,g.oper_name,g.oper_date,g.serial_no,");
			sql.append("t.have_price as have_price,c.name cat_name,b.name brand_name,g.creator_user  apply_userid, g.audit_state ,");
			sql.append("(select username from es_adminuser u where u.userid = g.creator_user and g.source_from = u.source_from) apply_username ");
			sql.append("from es_goods_l g  left join es_goods_type t on g.type_id=t.type_id "+ManagerUtils.apSFromCond("t")+"");
			sql.append("left join es_goods_cat c on g.cat_id=c.cat_id "+ManagerUtils.apSFromCond("c")+"");
			sql.append("left join es_brand b on g.brand_id=b.brand_id "+ManagerUtils.apSFromCond("b")+"");
			sql.append("left join es_goods_co egc  on egc.goods_id = g.goods_id "+ManagerUtils.apSFromCond("egc")+"");
			sql.append("left join es_goods_org  ego  on ego.party_id = egc.org_id "+ManagerUtils.apSFromCond("ego")+"");
			
			//sql.append("left join es_brand_model m on m.brand_code = b.brand_code "+ManagerUtils.apSFromCond("m")+"");//add by liqingyi
			sql.append("and g.source_from = t.source_from where 1=1 ");
			sql.append("and g.disabled='"+Consts.GOODS_DISABLED_0+"' and g.source_from = '" + ManagerUtils.getSourceFrom() + "'");
			return sql.toString();
		}
		
		public String getGET_GOODS_EDITDATA_LOG (){
			return "select a.*,b.stype_code,p.product_id from es_goods_l a  "
					+ " left join es_product p on a.goods_id=p.goods_id and a.source_from=p.source_from "
					+ " left join es_goods_stype b on a.stype_id=b.stype_id and  a.source_from = b.source_from "
					+ " where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
		
		public String getGOODS_REL_PRODUCT_LOG (){return "select p.* from es_product p left join es_goods_rel_l r on p.product_id=r.product_id where p.source_from=r.source_from and p.source_from='"+ManagerUtils.getSourceFrom()+"' and r.a_goods_id=?";}

		public String getPRODUCT_COMPANY_ALL (){return "select *from es_product_comp a where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";}
		/**================华盛开始====================
		 * select t.id,t.matnr,t.maktx,t.meins,t.mtart,t.matkl,t.xchpf,t.cmchk,t.cmmag from es_goods_huasheng t
			where not exists(select 1 from es_product b where t.matnr=b.sku);
		 * */
		public String getHS_GOODS_LIST(){
			return "select T.ID,T.MATNR,T.MAKTX,T.MEINS,T.MTART,T.MATKL,T.XCHPF,T.MCHK,T.CMMAG,T.SOURCE_FROM,T.CREATE_TIME,T.UPDATE_TIME from es_goods_huasheng t"
					+" where t.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		}
		/**================华盛结束====================*/
		
		/**
		 * 判断合约类型是否存在
		 * @return
		 */
		public String getQUERY_ACT_CODE_VALUE_EXISTS(){
			return "select count(1) from es_dc_public c where c.stype = '201605120001' and c.pkey = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		}
		
		/**
		 * 获取定制机合约的商品小类
		 * @return
		 */
		public String getQUERY_ACT_CODE_CAT_ID_DZ(){
			return "select c.pname from es_dc_public c where c.stype = '201605120002' and pkey = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		}
		
		/**
		 * 获取社会机合约的商品小类
		 * @return
		 */
		public String getQUERY_ACT_CODE_CAT_ID_SH(){
			return "select c.pname from es_dc_public c where c.stype = '201605120003' and pkey = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		}
		
		/**
		 * 获取小区管理信息列
		 * @return
		 */
		public String getES_COMMUNITY_GET(){
			return "select a.community_code, a.community_name, a.city_name, a.area_code, a.area_name, "
					+ " a.county_comp_id, a.county_comp_name, a.std_addr, a.net_grid"
					+ " from es_community a "
					+ " where 1=1 and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";
		}
		
		/**
		 * 获取小区商品关系信息列
		 * @return
		 */
		public String getES_COMMUNITY_GOODS_GET(){
			return "SELECT a.community_code,a.community_name,b.name,b.goods_id,b.sku FROM es_community a,es_goods b,es_goods_rel_community c"
					+ " WHERE a.community_code=c.community_code AND b.goods_id=c.goods_id"
					+ " AND a.source_from=c.source_from AND b.source_from=c.source_from"
					+ " AND a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
					+ " AND b.source_from = '" + ManagerUtils.getSourceFrom() + "'"
					+ " AND c.source_from = '" + ManagerUtils.getSourceFrom() + "'";
		}
		/**
		 * 获取商品信息列
		 * @return
		 */
		public String getES_GOODS_GET(){
			return "SELECT a.goods_id,a.name,a.sku,a.source_from FROM es_goods a where a.source_from='" + ManagerUtils.getSourceFrom() + "' "
					+ "AND a.goods_id not in"
					+ "(SELECT b.goods_id FROM es_goods_rel_community b WHERE b.source_from='" + ManagerUtils.getSourceFrom() + "')";
		}
		
		/**
		 * 删除小区与商品关系
		 * @return
		 */
		public String getES_COMMUNITY_GOODS_DEL(){
			return "DELETE FROM es_goods_rel_community a WHERE 1=1 AND a.source_from='" + ManagerUtils.getSourceFrom() + "'";
		}
		
		/**
		 * 判断小区数据在excel表中是否存在,存在则删除，重新新增覆盖
		 * @return
		 */
		public String getES_COMMUNITY_DEL(){
			return  "DELETE FROM es_community a WHERE 1=1 AND a.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		}
}