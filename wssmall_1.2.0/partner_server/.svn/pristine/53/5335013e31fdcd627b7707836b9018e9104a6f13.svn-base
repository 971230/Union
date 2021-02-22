package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import params.req.PartnerWdAddReq;
import params.resp.PartnerWdAddResp;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.framework.database.Page;

/**
 * 分销商申请
 * @author dengxiuping
 *
 */
public interface IPartnerManager {
	
	public boolean checkLlkjAccNbr(String acc_nbr);
	public void updateUserid(String partnerId,String userid);
	public Partner bakPartner(Partner old,String setState,Integer setSequ,Partner partner);
	public void bakPartner(String partnerId,String setState,Integer setSequ);
	public int add(Partner obj);
	public int addNotAudit(Partner partner);
	public int applyadd(Partner partner);
	public int getCountSql(int founder,String state,int sequ,String userid,String type);
	public int getMaxPartnerSequ(String partnerid);
	public int saveAuditAlterPartner(Partner partner);
	public int countRowParByPid(String parid);
	public int getMaxPartnerSequ(Partner partner);
	public int edit(Partner partner);
	public int edit_llkj(Partner partner);
	public int delete(String id);
	public int saveAuditPartner(Partner partner);
	public int saveBlockPartner(Partner partner);
	public int saveRenewPartner(Partner partner);
	public int editPartner(Partner partner,String state,Integer sequ);
	public int editUpdatePartner(Map map,String parid);
	public int editPartner(Partner partner);
	public int saveKeeponPartner(Partner partner,Integer addMonths);
	public int editGoodsUsage(String userid,String [] goodsid,String block_reason);
	public int editGoodsUsageOpen(String userid,String [] goodsid,String block_reason);//解冻
	
	public String getDcpublicName(String type,String key);
	public String getPartnerLevel(Partner partner);
	public String appendSql2();
	public String appendParentUseridSql();
	public String getDefaltType();
	public Partner getPartnerIdByUserId(String uId);
	public String currentLoginPartnerId();
	public String appendSql();
	public String get30yearDate();
	
	public boolean isFieldAuditIngExits(String partnerId);
	public boolean isPartnerCodeAndNameExits(String code,String name);
	public boolean isPartnerExits(String name, String code);
	public boolean isSaveExits(String partnerId,String state,Integer sequ);
	
	public Partner getPartnerByCurrentUserId(String userId, Partner sourcePartner);//根据userid查找分销商信息  --- 就为了得到存款。
	public Partner getPartnerByCurrentUserId2(String userId);//根据userid查找分销商信息 by liqingyi
	public Partner getPartnerById(String id);
	public Partner queryPartnerByID(String partnerId);
	public Partner getPartner(String id);
	public Partner getPartnerSequ0AndState1(String id);//获取正常有效的分销商资料
	public Partner getPartnerSequM1AndState0(String id);//获取待审核还未生效的分销商资料
//	public Partner getPartnerByUserId(String userId);
	public Partner getPartner(String id,Integer sequ,String state);
	
	public List partnerGoodsUsageList(String userid,String state);
	public List defaultValue();
	public List columnAuditList();
	public List histroyList(List columnNamelist,String partnerid);
	public List<Map> list() ;
//	public List partnerlist();
	public List<Map> auditResonAndDateList(String partner_id,Integer msequ,Integer ssequ);
	public List onelevelPartnerList();
	public List searchPartnerAdUserList();
//	public List searchPartnerTypeList();
//	public List searchPartnerLevelList();
	public List searchPartneShopTypeList();
//	public List searchPartneCatList();
	public List searchPartneShopStarList();
	public List<Partner> searchPartneridList();//获取冻结的分销商信息userid
	
	public Page fieldAuditlist(Partner obj, String type, int page, int pageSize);
	public Page list(Partner obj, String order, int page, int pageSize);
	public Page auditlist(Partner obj, String order, int page, int pageSize) ;
	public Page partnerAssignYeslist(Partner obj, int page, int pageSize);
	public Page searchPartner(Map map, int page,int pageSize);//关联二级分销商 分销商 查找分销商
	public Page searchPartnerList(String username, String partner_name, int page, int pageSize);//关联分销商 查询所有分销商
	
	public Page partnerAssignNolist(Partner obj, int page, int pageSize); //未分配工号
	public Page partnerAuditlist(Partner obj, int page, int pageSize);
	
	/**
	 * 查询代理商
	 * @param userid 用户id
	 * @param parnter_name 分销商名称 右模糊匹配
	 * @param parent_userid 所属上级分销商用户父id
	 * @param state 审核状态
	 * @param sequ 数据是否有效
	 * @param beginStateTime 审核时间 yyyy-MM-dd
	 * @param endStateTime 审核时间 yyyy-MM-dd
	 * @param founder 分销商级别
	 * @return
	 */
	public Page queryPartner(String userid, String partner_name, String parent_userid, String state, 
			Integer sequ, String beginStateTime, String endStateTime, String founder,
			int pageNo, int pageSize);
	
	/**
	 * @param partner_id partner_id
	 * @param userid 用户id
	 * @param parent_userid 所属上级分销商用户父id
	 * @param state 审核状态
	 * @param sequ 数据是否有效
	 * @param founder 分销商级别
	 * @return
	 */
	public Partner getPartner(String partner_id, String userid, String parent_userid, String state,
			Integer sequ, String founder);
	/**
	 * 分销商合约过期：短信提醒&冻结 定时任务一天执行一次
	 */
	public void partnerExpAndFreeze();
	
	public PartnerWdAddResp addParterWd(PartnerWdAddReq partnerWdAddReq);
	
}
