package com.ztesoft.net.mall.core.service.impl;
//package com.enation.mall.core.service.impl;
//
//import com.enation.app.base.core.model.Member;
//import com.ztesoft.net.eop.sdk.database.BaseSupport;
//import com.enation.mall.core.service.IAgentManager;
//
///**
// * 会员管理
// * 
// * @author wui
// */
//public class AgentManager extends BaseSupport<Member> implements IAgentManager {
//
////	public int add(Agent agent) {
////		this.baseDaoSupport.insert("agent", agent);
////		String agentid = agent.getAgentid();
////		agent.setAgentid(agentid);
////		return 1;
////	}
////
////	public void approve(Integer[] ids) {
////		// TODO Auto-generated method stub
////
////	}
////
////	public void edit(Agent agent) {
////		if (logger.isDebugEnabled()) {
////			logger.debug("开始处理商户数据...");
////		}
////		Map agentMap = this.po2Map(agent);
////		this.baseDaoSupport.update("agent", agentMap, "agentid="
////				+ agent.getAgentid());
////
////	}
////
////	public List<Map> list() {
////		String sql = "select * from es_agent";
////		return this.baseDaoSupport.queryForList(sql);
////	}
////
////	public List<Map> agentList() {
////		String sql = "select * from es_agent where staff_no is not null and state =  "
////				+ AcceptConst.AGENT_APP_STATE_001;
////		return this.baseDaoSupport.queryForList(sql);
////	}
////
////	public Map getAgentById(String agent_id) {
////		String sql = "select g.*,u.username staff_code from es_agent g  left join "
////				+ this.getTableName("adminuser")
////				+ " u on g.staff_no=u.userid  where agentid = ? ";
////		return this.baseDaoSupport.queryForMap(sql, agent_id);
////	}
////
////	/**
////	 * 删除商户信息
////	 * 
////	 * @param agent_id
////	 */
////	public void delAgentById(String agent_id) {
////		String sql = "update es_agent set state=2 ,comments='删除商户信息' where agentid=?";
////		this.baseDaoSupport.execute(sql, agent_id);
////	}
////
////	/**
////	 * 后台搜索商品
////	 * 
////	 * @param params
////	 *            通过map的方式传递搜索参数
////	 * @param page
////	 * @param pageSize
////	 * @return
////	 */
////	public Page searchAgents(String username, String state, String order,
////			int page, int pageSize) {
////
////		StringBuffer sql = new StringBuffer();
////		sql.append("select g.*, (case g.state when 0 then '");
////		sql.append(AcceptConst.AGENT_APP_STATE_C_000);
////		sql.append("' when 1 then '");
////		sql.append(AcceptConst.AGENT_APP_STATE_C_001);
////		sql.append("' when 2 then '");
////		sql.append(AcceptConst.AGENT_APP_STATE_C_002);
////		sql.append("' else '");
////		sql.append(AcceptConst.AGENT_APP_STATE_C_002);
////		sql
////				.append("'  end  )  state_name,u.username staff_code from es_agent g  left join ");
////		sql.append(this.getTableName("adminuser"));
////		sql.append(" u on g.staff_no=u.userid  where 1=1 ");
////		if (order == null) {
////			order = "agentid desc";
////		}
////		if (!StringUtil.isEmpty(username)) {
////			sql.append("and username like  '%" + username + "%'");
////		}
////
////		if (!StringUtil.isEmpty(state)) {
////			sql.append("and g.state =" + state);
////		}
////		sql.append(" order by g." + order);
////		Page webpage = this.daoSupport.queryForPage(sql.toString(), page,
////				pageSize);
////
////		return webpage;
////	}
////
////	public Page searchStaff(String staff_no, String staff_name, int page,
////			int pageSize) {
////
////		String sql = "select * from es_adminuser a where founder=0 and not exists (select 1 from es_agent b where a.userid = b.staff_no) ";
////
////		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
////
////		return webpage;
////	}
////	/**
////	 * 前台查找分销商
////	 * @param staff_no
////	 * @param staff_name
////	 * @param page
////	 * @param pageSize
////	 * @return
////	 */
////	public List searchStaffList() {
////
////		String sql = "select * from es_adminuser a where founder=0 and not exists (select 1 from es_partner b where a.userid = b.userid) ";
////
////		return this.baseDaoSupport.queryForList(sql);
////	}
////	/***
////	 * 查找代理商
////	 * @param staff_no
////	 * @param staff_name
////	 * @param page
////	 * @param pageSize
////	 * @return
////	 */
////	public Page searchStaff2(String username, String realname, int page,
////			int pageSize) {
////
////		ArrayList params = new ArrayList();
////		String sql = "select * from es_adminuser a where founder=0 and not exists (select 1 from es_agent b where a.userid = b.staff_no) ";
////		
////		if(username!=null && !"".equals(username)){
////        	sql+=" and a.username like ? ";
////        	params.add( StringUtil.toUTF8(username.trim())+"%");
////        }
////        if(realname!=null && !"".equals(realname)){
////        	sql+=" and a.realname like ? ";//"+realname.trim()+"
////        	params.add(StringUtil.toUTF8(realname.trim()+"%"));
////        }
////		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,params.toArray());
////
////		return webpage;
////	}
////	/***
////	 * 查找CRM
////	 * @param staff_no
////	 * @param staff_name
////	 * @param page
////	 * @param pageSize
////	 * @return
////	 */
////	public Page searchStaff3(Map map, int pageNo,int pageSize) {
////  
////		String userCode = (String) map.get("userCode");
////		String userName = (String) map.get("userName");
////		String lanId = (String) map.get("lanId");
////		String userType = (String) map.get("userType");
////		
////		StringBuffer sql = new StringBuffer();
////		
////		sql.append("select distinct sys.staff_code,sys.staff_name,sys.system_user_id,sys.sms_tel,org.union_org_code,org.org_code,org.lan_id,el.lan_name");
////		sql.append(" from es_system_user sys, es_staff s, es_organization org,es_lan el");
////		sql.append(" where sys.staff_code = s.staff_code");
////		sql.append(" and el.lan_id = org.lan_id");
////		sql.append(" and s.org_id = org.party_id");
////		sql.append(" and sys.party_id = s.party_id");
////		sql.append(" and s.status_cd = '00A'");
////		sql.append(" and org.status_cd = '00A'");
////		
////		
////		if(Consts.CURR_FOUNDER_0.equals(userType)){
////			sql.append(" and org.org_type_id <> 8");
////		}else{
////			sql.append(" and org.org_type_id = 8");
////		}
////		
////		if(userName != null && !"".equals(userName)){
////			sql.append(" and sys.staff_name like '%"+ userName +"%'");
////		}
////		
////		if(userCode != null && !"".equals(userCode)){
////			sql.append(" and sys.staff_code like '%"+ userCode +"%'");
////		}
////		
////		if(lanId != null && !"".equals(lanId) && !Consts.LAN_PROV.equals(lanId)){
////			sql.append(" and org.lan_id = '"+ lanId +"'");
////		}
////		
////		Page webpage = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize);
////
////		return webpage;
////	}
////	/**
////	 * 将po对象中有属性和值转换成map
////	 * 
////	 * @param po
////	 * @return
////	 */
////	protected Map po2Map(Object po) {
////		Map poMap = new HashMap();
////		Map map = new HashMap();
////		try {
////			map = BeanUtils.describe(po);
////		} catch (Exception ex) {
////		}
////		Object[] keyArray = map.keySet().toArray();
////		for (int i = 0; i < keyArray.length; i++) {
////			String str = keyArray[i].toString();
////			if (str != null && !str.equals("class")) {
////				if (map.get(str) != null) {
////					poMap.put(str, map.get(str));
////				}
////			}
////		}
////		return poMap;
////	}
//
//}
