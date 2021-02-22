package com.ztesoft.net.service.workCustom.interfaces;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;
import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM_REL;

import com.ztesoft.net.framework.database.Page;

/**
 * 
 * <p>Title: ITeamCustomManager.java</p>
 * <p>Description: 团队管理接口</p>
 * @author sgf
 * @time 2018年10月9日 下午2:37:53
 * @version 1.0
 */
public interface ITeamCustomManager {
    
    /**
     * <p>Description: 查询团队列表</p>
     */
    Page queryForList(ES_USER_TEAM team, String user_id, int page, int pageSize) throws Exception;
    /**
     * 团队信息修改查询
     */
    Map<String, Object> getTeamInfoByPojo(ES_USER_TEAM pojo);
    /**
     *团队名称校验 
     */
    Integer checkByTeamName(ES_USER_TEAM team);
    /**
     *团队新增 
     */
    void teamInfoInsert(ES_USER_TEAM team);
    /**
     *校验是否存在处理人 
     * @return
     */
    Integer checkTeamForDealer(String team_id);
    /**
     *删除团队配置 
     */
    void deleteTeam(ES_USER_TEAM pojo);
    /**
     *修改团队信息 
     */
    void teamUpdateInfo(ES_USER_TEAM team);
    /**
     *配置团队人员信息 
     */
    Page queryForListTeamRel(String team_id, int page, int pageSize);
    void deleteTeamRel(ES_USER_TEAM_REL pojo);
    /**
     * 查询团队人员
     */
    List findTeamRelUser(String team_id);
    /**
     * 查询员工
     * @param other_field_value 三位编码
     * @param county_id 
     * @param region_id 
     * @param team_iStringd 
     */
    List<Map<String, Object>> findAdminUserAll(String user_id, String region_id, String county_id, String other_field_value, String team_iStringd);
    /**
     * 批量添加
     */
    void addTeamRel(ES_USER_TEAM_REL pojo) throws Exception;
    /**
     * 人员团队删除
     */
    void teamRelRemove(ES_USER_TEAM_REL pojo);
    String findCountyIdFromRealtion(String county_id);
    List<Map<String, Object>> getIntentCountyList(String region_id);
    Integer checkTeamForNodeIns(String team_id);

}
