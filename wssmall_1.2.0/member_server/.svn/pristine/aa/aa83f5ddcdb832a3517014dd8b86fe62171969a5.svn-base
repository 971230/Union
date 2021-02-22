package com.ztesoft.net.mall.widget.memberlv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class MemberLVSpecManager extends BaseSupport implements IMemberLVSpec {
	
	public static final String GOODS_LV_STATUS = "00A";

	private IMemberLvManager memberLvManager;
	
	/**
	 * 按会员ID查询会员等级
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-16 
	 * @param memberID
	 * @return
	 */
	@Override
	public List<MemberLv> qryMLVbyIDS(String lvIDs) {
		String sql = SF.memberSql("MEMBER_QRY_MLV_BY_IDS").replace(":instr", lvIDs);
		List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class);
		return list;
	}

	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}

	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

	@Override
	public List<MemberLv> qryMLVbyIdsAndGoodsID(String lvIDs,String goods_id) {
		String sql = SF.memberSql("MEMBER_QRYMLV_BY_IDS_AND_GOODSID").replace(":instr", lvIDs);
		//String sql="select l.* from es_member_lv l,es_price_priv pp where l.source_from = ? and l.lv_id in(0) and l.lv_id=pp.role_type and pp.state= ? and pp.goods_id=? order by l.lv_id";
        List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new String[]{GOODS_LV_STATUS, goods_id});
		return list;
	}

	@Override
	public boolean memberLvCanBy(String productID,String lvID) {
		String sql = SF.memberSql("MEMBER_LV_CANBY");
		List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class,new String[]{productID, lvID, GOODS_LV_STATUS});
		return list.size()>0;
	}

	@Override
	public Map getMemerLvPrice(String productID, String goodsID, String lvid) {
		String sql = SF.memberSql("MEMBER_GET_MEMER_LV_PRICE");
		List price = this.baseDaoSupport.queryForList(sql,productID,lvid,goodsID);
		if(price!=null && price.size()>0){
			Map map = (Map) price.get(0);
			return map;
		}
		return null;
	}

    @Override
    public boolean memberLvCanByGoodsid(String goods_id, String lvID) {
        String sql="select pp.* from es_price_priv pp,es_product p where pp.goods_id=p.goods_id " +
                "and p.goods_id=? and pp.role_type=? and pp.state= ? and pp.source_from = p.source_from and p.source_from =?";
        List param=new ArrayList();
        param.add(goods_id);
        param.add(lvID);
        param.add(GOODS_LV_STATUS);
        param.add(ManagerUtils.getSourceFrom());
        List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class,param.toArray());
        return list.size()>0;
    }
}
