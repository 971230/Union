package com.ztesoft.net.service.workCustom.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;










import org.springframework.jdbc.core.RowMapper;

import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;
import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM_REL;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.workCustom.interfaces.ITeamCustomManager;
import com.ztesoft.workCustom.dao.ES_USER_TEAM_DAO;
import com.ztesoft.workCustom.dao.ES_USER_TEAM_REL_DAO;

/**
 * 
 * <p>Title: TeamCustomManager.java</p>
 * <p>Description: 团队管理实现类</p>
 * @author sgf
 * @time 2018年10月9日 下午2:39:37
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class TeamCustomManager extends BaseSupport implements ITeamCustomManager {

    private ES_USER_TEAM_DAO teamDao;
    private ES_USER_TEAM_REL_DAO teamRelDao;

    private ES_USER_TEAM_DAO getTeamDao() throws Exception {
        if (this.teamDao == null)
            this.teamDao = SpringContextHolder.getBean("ES_USER_TEAM_DAO");

        return this.teamDao;
    }

    @SuppressWarnings("unused")
    private ES_USER_TEAM_REL_DAO getTeamRelDao() throws Exception {
        if (this.teamRelDao == null)
            this.teamRelDao = SpringContextHolder
                    .getBean("ES_USER_TEAM_REL_DAO");

        return this.teamRelDao;
    }

    @Override
    public Page queryForList(ES_USER_TEAM team, String user_id,int page, int pageSize) throws Exception {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" select a.team_id, ");
        sbBuilder.append(" a.team_name, decode(a.team_level,'province','省', 'region', '地市', 'county', '县分') as team_level, ");
        sbBuilder.append(" a.region_id,  re.local_name as region_name, a.county_id,edpdr.field_value_desc as county_name, a.busi_region_id, a.busi_county_id, ");
        sbBuilder.append(" decode(a.state, '1', '正常', '禁用') as state ");
        sbBuilder.append("  from es_user_team a ");
        sbBuilder.append("  left join es_regions re  on re.region_id = a.region_id and re.p_region_id='330000' ");
        sbBuilder.append("  left join es_dc_public_dict_relation edpdr  on edpdr.field_value = a.county_id and edpdr.stype = 'county' ");
        sbBuilder.append(" where a.source_from='").append(ManagerUtils.getSourceFrom()).append("' ");
        String whereParam= this.sqlBuildParam(team,user_id);
        sbBuilder.append(whereParam);
        sbBuilder.append(" order by a.team_id desc");
        StringBuilder countbBuilder = new StringBuilder();
        countbBuilder.append(" select count(1) ");
        countbBuilder.append("  from es_user_team a ");
        countbBuilder.append("  left join es_regions re  on re.region_id = a.region_id and re.p_region_id='330000' ");
        countbBuilder.append(" where a.source_from='").append(ManagerUtils.getSourceFrom()).append("' ");
        countbBuilder.append(whereParam);
        logger.info("人员团队查询:"+sbBuilder.toString());
        Page webpage = this.baseDaoSupport.queryForPage(sbBuilder.toString(),countbBuilder.toString(), page, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("team_id", rs.getString("team_id"));
                map.put("team_name", rs.getString("team_name"));
                map.put("team_level", rs.getString("team_level"));
                map.put("region_id", rs.getString("region_id"));
                map.put("region_name", rs.getString("region_name"));
                map.put("county_name", rs.getString("county_name"));
                map.put("busi_region_id", rs.getString("busi_region_id"));
                map.put("busi_county_id", rs.getString("busi_county_id"));
                map.put("county_id", rs.getString("county_id"));
                map.put("state", rs.getString("state"));
                return map;
            }
        });
        return webpage;
    }
    
    private String sqlBuildParam(ES_USER_TEAM team, String user_id) {
        StringBuilder sbBuilder = new StringBuilder();
        if(team !=null){
            if(!StringUtil.isEmpty(user_id)){
                sbBuilder.append(" and a.team_id in (select tr.team_id from es_user_team_rel tr where tr.userid like '%").append(user_id).append("%') ");
            }
            if(!StringUtil.isEmpty(team.getTeam_id()) && !"%".equals(team.getTeam_id())){//团队编号
                sbBuilder.append(" and a.team_id ='").append(team.getTeam_id()).append("' ");
            }
            if(!StringUtil.isEmpty(team.getTeam_name()) && !"%".equals(team.getTeam_name()) ){//团队名称
                sbBuilder.append(" and a.team_name like '%").append(team.getTeam_name()).append("%' ");
            }
            if(!StringUtil.isEmpty(team.getRegion_id()) && !"-99".equals(team.getRegion_id())){//地市
                sbBuilder.append(" and  a.region_id ='").append(team.getRegion_id()).append("' ");
            }
            if(!StringUtil.isEmpty(team.getTeam_level()) && !"-1".equals(team.getTeam_level())){//团队级别
                sbBuilder.append(" and  a.team_level ='").append(team.getTeam_level()).append("' ");
            }
            if(!StringUtil.isEmpty(team.getState()) && !"-1".equals(team.getState()) ){//状态
                sbBuilder.append(" and  a.state ='").append(team.getState()).append("' ");
            }
            if(!StringUtil.isEmpty(team.getCounty_id()) && !"-99".equals(team.getCounty_id())){//县分
                sbBuilder.append(" and a.county_id ='").append(team.getCounty_id()).append("' ");
            }
        }
        return sbBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getTeamInfoByPojo(ES_USER_TEAM pojo) {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" select a.team_id, ");
        sbBuilder.append(" a.team_name, a.team_level, ");
        sbBuilder.append(" a.region_id,  a.region_name, a.county_id,a.county_name, a.busi_region_id, a.busi_county_id, ");
        sbBuilder.append(" a.state ");
        sbBuilder.append("  from es_user_team a ");
        sbBuilder.append(" where a.source_from='").append(ManagerUtils.getSourceFrom()).append("' ");      
        String sqlParam = this.sqlBuildParam(pojo, null);
        sbBuilder.append(sqlParam);
        logger.info("团队查询sql:"+sbBuilder.toString());
        List<Map<String, Object>> list =  this.baseDaoSupport.queryForList(sbBuilder.toString());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer checkByTeamName(ES_USER_TEAM team) {
       StringBuilder abBuilder = new StringBuilder();
       abBuilder.append("   select count(1) from  es_user_team c where c.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
       if(!StringUtil.isEmpty(team.getTeam_name())){
           abBuilder.append(" and c.team_name ='").append(team.getTeam_name().trim()).append("' ");
       }
       if(!StringUtil.isEmpty(team.getTeam_id())){
           abBuilder.append(" and c.team_id !='").append(team.getTeam_id().trim()).append("' ");
       }
      return  this.baseDaoSupport.queryForInt(abBuilder.toString());
    }

    @Override
    public void teamInfoInsert(ES_USER_TEAM team) {
       String team_id ="";
    try {
        team_id =  this.baseDaoSupport.getSequences("seq_es_user_team", "5", 0);
    } catch (Exception e) {
        e.printStackTrace();
    }
    if("-99".equals(team.getCounty_id())){
        team.setCounty_id("");
    }
    if("-99".equals(team.getRegion_id())){
        team.setRegion_id("");
    }
    if(!StringUtil.isEmpty(team_id)){
        team.setTeam_id(team_id);
        this.baseDaoSupport.insert("es_user_team", team);
    }
    }

    @Override
    public Integer checkTeamForDealer(String team_id) {
        StringBuilder abBuilder = new StringBuilder();
        abBuilder.append("   select count(1) from  es_work_custom_dealer c where c.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
        if(!StringUtil.isEmpty(team_id)){
            abBuilder.append(" and c.dealer_code ='").append(team_id).append("'");
        }
        abBuilder.append(" and c.state = '1'");
       return  this.baseDaoSupport.queryForInt(abBuilder.toString());
    }

    @Override
    public void deleteTeam(ES_USER_TEAM pojo) {
        String sql = "delete from es_user_team where team_id='"+pojo.getTeam_id()+"' and source_from ='"+ManagerUtils.getSourceFrom()+"' ";
        String delete_sql_rel = "delete from es_user_team_rel where team_id='"+pojo.getTeam_id()+"' and source_from ='"+ManagerUtils.getSourceFrom()+"' ";
        this.baseDaoSupport.execute(sql);
        this.baseDaoSupport.execute(delete_sql_rel);
    }

    @Override
    public void teamUpdateInfo(ES_USER_TEAM team) {
        this.updateInfo(team);
    }

    private void updateInfo(ES_USER_TEAM pojo) {
        Map<String,Object> setMap = new HashMap<String, Object>();
        Map<String, Object> whereMap = new HashMap<String, Object>();
        if(!StringUtil.isEmpty(pojo.getTeam_id())){
            whereMap.put("team_id", pojo.getTeam_id());
        }
        if(!StringUtil.isEmpty(pojo.getTeam_name())){
            setMap.put("team_name", pojo.getTeam_name());        
        }
        if(!StringUtil.isEmpty(pojo.getCounty_id()) && !"-99".equals(pojo.getCounty_id())){
            setMap.put("county_id", pojo.getCounty_id()); 
        }
        if(!StringUtil.isEmpty(pojo.getRegion_id()) && !"-99".equals(pojo.getCounty_id())){
            setMap.put("region_id", pojo.getRegion_id()); 
        }
        if(!StringUtil.isEmpty(pojo.getCounty_name())){
            setMap.put("county_name", pojo.getCounty_name()); 
        }
        if(!StringUtil.isEmpty(pojo.getRegion_name())){
            setMap.put("region_name", pojo.getRegion_name()); 
        }
        if(!StringUtil.isEmpty(pojo.getTeam_level()) && !"-1".equals(pojo.getTeam_level())){
            setMap.put("team_level", pojo.getTeam_level()); 
        }
        if(!StringUtil.isEmpty(pojo.getState()) && !"-1".equals(pojo.getTeam_level())){
            setMap.put("state", pojo.getState()); 
        }
        if(!StringUtil.isEmpty(pojo.getUpdate_date())){//更新时间
            setMap.put("update_date", pojo.getUpdate_date()); 
        }
        if(!StringUtil.isEmpty(pojo.getCreate_date())){//创建时间
            setMap.put("create_date", pojo.getCreate_date()); 
        }
        if(!StringUtil.isEmpty(pojo.getRemark())){
            setMap.put("remark", pojo.getRemark());
        }
        this.baseDaoSupport.update("es_user_team", setMap, whereMap);
        
    }

    @Override
    public Page queryForListTeamRel(String team_id, int page, int pageSize) {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" select eutl.userid as userid,ea.realname realname,ea.lan_name as lan_name");
        sbBuilder.append("    from es_user_team eut  left join es_user_team_rel eutl  on eut.team_id = eutl.team_id left join es_adminuser ea on ea.userid=eutl.userid ");
        sbBuilder.append(" where eut.source_from='").append(ManagerUtils.getSourceFrom()).append("' ");
        sbBuilder.append("  and  eut.state='1' and eutl.state='1' ");
        if(!StringUtil.isEmpty(team_id)){
            sbBuilder.append("  and  eutl.team_id='").append(team_id).append("'");
        }
        logger.info("人员信息:"+sbBuilder.toString());
        Page webpage = this.baseDaoSupport.queryForPage(sbBuilder.toString(), page, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userid", rs.getString("userid"));
                map.put("realname", rs.getString("realname"));
                map.put("lan_name", rs.getString("lan_name"));
                return map;
            }
        });
        return webpage;
    }

    @Override
    public void deleteTeamRel(ES_USER_TEAM_REL pojo) {
        String sql = "delete from es_user_team_rel where team_id='"+pojo.getTeam_id()+"' and userid='"+pojo.getUserid()+"'";
        this.baseDaoSupport.execute(sql);
    }

    @Override
    public List findTeamRelUser(String team_id) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("select eutl.userid, ea.realname,ea.lan_name, edpdr.field_value_desc as county_name  from es_user_team_rel eutl left join es_user_team eut   on eut.team_id = eutl.team_id left join es_adminuser ea  on ea.userid = eutl.userid   left join es_dc_public_dict_relation edpdr on (edpdr.field_value = ea.org_id or ea.syn_county_id=edpdr.other_field_value) and edpdr.stype = 'county' ");
        sBuilder.append("  where eut.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
        if(!StringUtil.isEmpty(team_id)){
            sBuilder.append(" and eut.team_id ='").append(team_id).append("'");
        }
        return this.baseDaoSupport.queryForList(sBuilder.toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAdminUserAll(String user_id, String region_id, String county_id, String other_field_value,String team_id) {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append("select ea.userid,ea.realname,ea.lan_name,edpdr.field_value_desc as county_name from es_adminuser ea  left join es_dc_public_dict_relation edpdr on (edpdr.field_value = ea.org_id or ea.syn_county_id=edpdr.other_field_value) and edpdr.stype = 'county' where ea.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ").append(" and ea.state='1'");
        if(!StringUtil.isEmpty(user_id)){
            sbBuilder.append(" and  (ea.userid like '%").append(user_id).append("%' or ea.realname like '%").append(user_id).append("%') ");
        }
        if(!StringUtil.isEmpty(team_id)){
            sbBuilder.append(" and ea.userid not in( select userid from es_user_team_rel where team_id='").append(team_id).append("') ");
        }
        if(!StringUtil.isEmpty(region_id)){
            sbBuilder.append(" and ea.lan_id='").append(region_id).append("' ");
        }
        if(!StringUtil.isEmpty(county_id) ){
            sbBuilder.append(" and (ea.org_id='").append(county_id).append("' ");
            if(!StringUtil.isEmpty(other_field_value)){
               sbBuilder.append(" or syn_county_id = '").append(other_field_value).append("' "); 
            }
            sbBuilder.append(" ) ");
        }
        return this.baseDaoSupport.queryForList(sbBuilder.toString());
    }

    @Override
    public void addTeamRel(ES_USER_TEAM_REL pojo) throws Exception {
       String   new_id =  this.baseDaoSupport.getSequences("seq_es_user_team", "5", 0);
       pojo.setUser_team_rel_id(new_id);
       this.baseDaoSupport.insert("es_user_team_rel", pojo);
    }

    @Override
    public void teamRelRemove(ES_USER_TEAM_REL pojo) {
        String sql = "delete from es_user_team_rel where team_id='"+pojo.getTeam_id()+"' and userid='"+pojo.getUserid()+"' and source_from ='"+ManagerUtils.getSourceFrom()+"' ";
        this.baseDaoSupport.execute(sql);
    }

    @Override
    public String findCountyIdFromRealtion(String county_id) {
        String sql = "select distinct t.other_field_value from  es_dc_public_dict_relation t where t.stype = 'county' and t.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"' and t.field_value='"+county_id+"' ";
        return this.baseDaoSupport.queryForString(sql);
    }

    @Override
    public List<Map<String, Object>> getIntentCountyList(String order_city_code) {
        String sql = "select distinct field_value,field_value_desc,other_field_value_desc,other_field_value from es_dc_public_dict_relation  where stype ='county' ";
        if (!StringUtils.isEmpty(order_city_code)) {
            sql = sql + "and col1='" + order_city_code + "'";
        }
        List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
        return list;
    }

    @Override
    public Integer checkTeamForNodeIns(String team_id) {
        StringBuilder abBuilder = new StringBuilder();
        abBuilder.append("   select count(1) from  es_work_custom_node_ins c where c.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
        if(!StringUtil.isEmpty(team_id)){
            abBuilder.append(" and c.dealer_code ='").append(team_id).append("'");
        }
        abBuilder.append(" and c.state = '1'");
       return  this.baseDaoSupport.queryForInt(abBuilder.toString());
    }
}
