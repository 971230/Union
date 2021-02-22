package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrLeagueInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import consts.ConstsCore;
/**
 * 
 * @author zou.qh
 * @date 2015-01-07
 */
public class LeagueInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String leagueInfos = orderAttrValues.get(AttrConsts.LEAGUE_INFO)==null?null : orderAttrValues.get(AttrConsts.LEAGUE_INFO).toString();
		if(!StringUtils.isEmpty(leagueInfos) && !"[]".equals(leagueInfos) && !"-1".equals(leagueInfos)){
			try{
				JSONArray jsonArray = JSONArray.fromObject(leagueInfos);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					Map league = new HashMap();
					league.put("league_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_LEAGUE_INFO"));
					league.put("inst_id", inst_id);
					league.put("order_id", order_id);
					league.put("league_id", jsonObject.get("leagueId"));
					league.put("league_name", jsonObject.get("leagueName"));
					league.put("higher_league_id", jsonObject.get("higherLeagueId"));
					league.put("higher_league_name", jsonObject.get("higherLeagueName"));
					
					AttrLeagueInfoBusiRequest leagueInfo = new AttrLeagueInfoBusiRequest();
					BeanUtils.copyProperties(leagueInfo, league);
					
					leagueInfo.setDb_action(ConstsCore.DB_ACTION_INSERT);
					leagueInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					leagueInfo.store();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}
