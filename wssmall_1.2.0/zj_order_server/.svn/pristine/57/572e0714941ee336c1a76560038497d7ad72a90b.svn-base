package com.ztesoft.net.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;
import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM_REL;
import bsh.This;

import com.alibaba.fastjson.JSONArray;
import com.tangosol.dev.assembler.New;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.iom.workorder.dto.returnDataTypeDto;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.workCustom.interfaces.ITeamCustomManager;

/**
 * <p>Description: 自定义流程支持团队</p>
 * @time 2018年10月9日 下午2:22:01
 */
public class TeamCustomAction extends WWAction{
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(TeamCustomAction.class);
    public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ES_USER_TEAM team;
    private ES_USER_TEAM_REL reamRel;
    private String user_id;
    private List<Map> dc_MODE_REGIONList;
    private String team_id;
    private String team_name;
    private String team_rel;
    private String county_id;
    private String region_id;
    private List<Map<String, Object>> countyList;// 县分列表
    private Map<String, Object> teamlist;// 意向单详情
    @Resource
    private ITeamCustomManager teamCustomManager;
    @Resource
    private IEcsOrdManager ecsOrdManager;
    /**
     * 
     * <p>Title: showTeamList</p>
     * <p>Description: 团队列表查询</p>
     */
    public String showTeamList() throws Exception{
        dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
        if(this.team != null){
            countyList = teamCustomManager.getIntentCountyList(this.team.getRegion_id());
        }else{
            countyList = teamCustomManager.getIntentCountyList(null);
        }
        this.webpage = teamCustomManager.queryForList(this.team,this.user_id,this.getPage(),this.getPageSize());
        return "team_custom_table";
    }
    
    /**
     * 县分联动效果
     */
    public String getCountyListByCity() {
        try {
            if (this.team == null) {
                team = new ES_USER_TEAM();
            }
            if("-99".equals(this.team.getRegion_id())){
                countyList = teamCustomManager.getIntentCountyList(null);
            }else{
                countyList = teamCustomManager.getIntentCountyList(team.getRegion_id());
            }
            if (countyList.size() > 0) {
                Map<String, Object> respMap = new HashMap<String, Object>();
                respMap.put("list", countyList);
                String list = JSONObject.fromObject(respMap).toString();
                list = list.substring(1, list.length() - 1).replace("\"list\":", "");
                json = "{result:0,message:'查询成功',list:'" + list + "'}";
            } else {
                json = "{result:2,message:'根据地市编码获取行政县分为空,请联系管理员'}";
            }
        } catch (Exception e) {
            json = "{result:1,message:'根据地市编码获取行政县分失败" + e.getMessage() + "'}";
            e.printStackTrace();
        }
        return JSON_MESSAGE;
    }

    /**
     * 团队基本信息修改
     * <p>Title: </p>
     * @time 2018年10月10日 上午9:28:33
     */
    public String teamInfoChange(){
        if(!StringUtil.isEmpty(this.team_id)){
            ES_USER_TEAM pojo = new ES_USER_TEAM();
            pojo.setTeam_id(this.team_id);
            teamlist = teamCustomManager.getTeamInfoByPojo(pojo);
            dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
            if(!teamlist.isEmpty()){
                countyList = teamCustomManager.getIntentCountyList(this.teamlist.get("region_id")+"");
            }
            return "team_change_info";
        }
        return "team_change_info";
    }
    public String toInsertTeamJsp(){
        dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
        if(this.team != null){
            countyList = teamCustomManager.getIntentCountyList(this.team.getRegion_id());
        }else{
            countyList = teamCustomManager.getIntentCountyList(null);
        }
        return "team_custom_add";
    }
   /**
    * 
    * <p>Description: 团队功能新增</p>
    */
    public String teamInfoInsert(){
        if(this.team==null){
            json = "{result:1,message:'新增失败'}";
            return JSON_MESSAGE;
        }
        team.setTeam_id("");
        Integer count  = teamCustomManager.checkByTeamName(team);
        if(count != 0){
            this.json = "{result:2,message:'团队名称重复'}";
            return JSON_MESSAGE;
        }
        this.team = checkRegionNameAndCountyName(team);
        team.setCreate_date(DF.format(new Date()));
        team.setSource_from(ManagerUtils.getSourceFrom());
        teamCustomManager.teamInfoInsert(team);
        json = "{result:0,message:'新增失败'}";
        return JSON_MESSAGE;
    }
    /**
     * 修改
     */
    public String teamUpdateInfo(){
        if(this.team==null){
            this.json = "{result:1,message:'修改失败'}";
            return JSON_MESSAGE;
        }
        Integer count  = teamCustomManager.checkByTeamName(this.team);
        if(count != 0){
            this.json = "{result:2,message:'团队名称重复'}";
            return JSON_MESSAGE;
        }
        this.team = checkRegionNameAndCountyName(team);
        team.setUpdate_date(DF.format(new Date()));
        teamCustomManager.teamUpdateInfo(this.team);
        json = "{result:0,message:'修改成功'}";
        return JSON_MESSAGE;
    }
    private ES_USER_TEAM checkRegionNameAndCountyName(ES_USER_TEAM team2) {
        if(!StringUtil.isEmpty(team2.getRegion_id()) && !"-99".equals(team2.getRegion_id())){
            List<Map>  list = dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
            if(list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    if(team2.getRegion_id().equals(list.get(i).get("value")+"")){
                        team2.setRegion_name(list.get(i).get("value_desc")+"");
                        break;
                    }
                }
            }
         }
         if(!StringUtil.isEmpty(team2.getCounty_id()) && !"-99".equals(team2.getCounty_id())){
             List<Map<String,Object>> list = teamCustomManager.getIntentCountyList(team2.getRegion_id());
             if(list.size()>0){
                 for (int i = 0; i < list.size(); i++) {
                     if(team2.getCounty_id().equals(list.get(i).get("field_value")+"")){
                         team2.setCounty_name(list.get(i).get("field_value_desc")+"");
                         break;
                     }
                 }
             }
        }
        return team2;
    }

    /**
     * 团队删除 
     */
    public String TeamDelete(){
        if(!StringUtil.isEmpty(this.team_id)){
          Integer count = teamCustomManager.checkTeamForDealer(this.team_id);
          if(count !=0){
              this.json = "{result:1,message:'该团队已配置为处理单位信息，不允许删除'}";
              return JSON_MESSAGE;
          }
          Integer countIns = teamCustomManager.checkTeamForNodeIns(this.team_id);
          if(countIns !=0){
             this.json = "{result:1,message:'该团队已被实例化为处理人，不允许删除'}";
             return JSON_MESSAGE;
          }
          ES_USER_TEAM pojo = new ES_USER_TEAM();
          pojo.setTeam_id(team_id);
          teamCustomManager.deleteTeam(pojo);
          this.json = "{result:0,message:'删除成功'}";
        }else {
            this.json="{result:1,message:'删除失败,团队编号为空'}";
        }
        return JSON_MESSAGE;
    }
    /**
     * 团队人员配置
     */
    public String teamUserStaff(){
        if(!StringUtil.isEmpty(this.team_id)){
            ES_USER_TEAM pojo = new ES_USER_TEAM();
            pojo.setTeam_id(this.team_id);
            teamlist = teamCustomManager.getTeamInfoByPojo(pojo);
            dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
            if(!teamlist.isEmpty()){
                countyList = teamCustomManager.getIntentCountyList(this.teamlist.get("region_id")+"");
            }
            return "team_user_staff";
        }
        return "team_user_staff";
    }
    /**
     * 团队人员信息
     */
    @SuppressWarnings("unchecked")
    public String teamRelList(){
        if(!StringUtil.isEmpty(this.team_id)){
           List<Map<String, Object>> list = teamCustomManager.findTeamRelUser(team_id);
           Map<String, Object> respMap = new HashMap<String, Object>();
           respMap.put("list", list);
           String listTrem = JSONObject.fromObject(respMap).toString();
           listTrem = listTrem.substring(1, listTrem.length() - 1).replace("\"list\":", "");
           json = "{result:0,message:'查询成功',list:'" + listTrem + "'}";
           return this.JSON_MESSAGE;
        }
        json = "{result:1,message:''}";
        return this.JSON_MESSAGE;
    }
    
    /**
     * 团队人员信息
     */
    public String teamUserAll(){
       String other_field_value="";
       if(!StringUtil.isEmpty(this.county_id) && !"-99".equals(this.county_id)){
            other_field_value = teamCustomManager.findCountyIdFromRealtion(county_id);
       }
       List<Map<String, Object>> list = teamCustomManager.findAdminUserAll(user_id,this.region_id,this.county_id,other_field_value,this.team_id);
       if(list.size()>0){
           Map<String, Object> respMap = new HashMap<String, Object>();
           respMap.put("list", list);
           String listTrem = JSONObject.fromObject(respMap).toString();
           listTrem = listTrem.substring(1, listTrem.length() - 1).replace("\"list\":", "");
           json = "{result:0,message:'查询成功',list:'" + listTrem + "'}";
       }else{
           json = "{result:1,message:'未查询到有效人员'}";
       }
       return this.JSON_MESSAGE;
    }
    public String TeamUserRemove(){
        if(!StringUtil.isEmpty(this.team_id)){
           ES_USER_TEAM_REL pojo = new ES_USER_TEAM_REL();
           pojo.setUserid(user_id);
           pojo.setTeam_id(team_id);
           teamCustomManager.deleteTeamRel(pojo);
           this.json = "{result:0,message:'删除成功'}";
         }
       return JSON_MESSAGE;
    }
    
    public String teamRelRemove() throws Exception{
        if(!StringUtil.isEmpty(this.team_id) && !StringUtil.isEmpty(this.team_rel)){//如果团队和人员选择不为空
            String [] team_rels = this.team_rel.split(",");
            for (int i = 0; i < team_rels.length; i++) {
                   ES_USER_TEAM_REL pojo = new ES_USER_TEAM_REL();
                   pojo.setTeam_id(team_id);
                   pojo.setUserid(team_rels[i]);
                   teamCustomManager.teamRelRemove(pojo);
            }
            this.json = "{result:0,message:'删除成功'}";
          }else{
              this.json="{result:1,message:'删除失败'}";
          }
        return JSON_MESSAGE;
    }
    
    public String teamRelAdd() throws Exception{
        if(!StringUtil.isEmpty(this.team_id) && !StringUtil.isEmpty(this.team_rel)){//如果团队和人员选择不为空
            String [] team_rels = this.team_rel.split(",");
            for (int i = 0; i < team_rels.length; i++) {
               List list =  teamCustomManager.findTeamRelUser(team_id);
               Boolean flag = true;
               if(list.size()>0){
                   if(list.toString().contains(team_rels[i])){
                       flag = false;
                   }
               }
               if(flag){
                   ES_USER_TEAM_REL pojo = new ES_USER_TEAM_REL();
                   pojo.setSource_from(ManagerUtils.getSourceFrom());
                   pojo.setTeam_id(team_id);
                   pojo.setState("1");
                   pojo.setCreate_date(DF.format(new Date()));
                   pojo.setUserid(team_rels[i]);
                   teamCustomManager.addTeamRel(pojo);
               }
            }
            this.json = "{result:0,message:'添加成功'}";
          }
        return JSON_MESSAGE;
    }
    
    public List<Map> getDc_MODE_REGIONList() {
        return dc_MODE_REGIONList;
    }
    public void setDc_MODE_REGIONList(List<Map> dc_MODE_REGIONList) {
        this.dc_MODE_REGIONList = dc_MODE_REGIONList;
    }
    public ES_USER_TEAM_REL getReamRel() {
        return reamRel;
    }
    public void setReamRel(ES_USER_TEAM_REL reamRel) {
        this.reamRel = reamRel;
    }
    public ES_USER_TEAM getTeam() {
        return team;
    }
    public void setTeam(ES_USER_TEAM team) {
        this.team = team;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public List<Map<String, Object>> getCountyList() {
        return countyList;
    }
    public void setCountyList(List<Map<String, Object>> countyList) {
        this.countyList = countyList;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public Map<String, Object> getTeamlist() {
        return teamlist;
    }

    public void setTeamlist(Map<String, Object> teamlist) {
        this.teamlist = teamlist;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_rel() {
        return team_rel;
    }

    public void setTeam_rel(String team_rel) {
        this.team_rel = team_rel;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
    
}
