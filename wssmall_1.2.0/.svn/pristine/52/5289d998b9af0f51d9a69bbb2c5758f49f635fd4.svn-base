package com.ztesoft.net.rule.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rule.core.ext.DefRuleResultProssor;

public class OrderGradeResultProssor  extends DefRuleResultProssor{
	
	@Override
	public String saveSumarryData(Map fact){
		String service_code = (String) fact.get("service_code");
		String spread_id = (String) fact.get("spread_id");
		SpreadMember spm = querySpreadMember(spread_id, service_code, null);
		if(spm!=null && "00A".equals(spm.getStatus())){
			if(spm.getParent_id()!=null && !"-1".equals(spm.getParent_id())){
				List<SpreadMember> list = new ArrayList<SpreadMember>();
				querySpreadMemberParents(list, spm.getParent_id(), service_code, null);
			}
		}else{
			return null;
		}
		//detail_id只能返回一个  如何处理？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
		//使用“，”号分开？？？？？？？？？？？
		return "";
	}
	
	@Override
	public void saveListData(List<Map> facts,String dataIndex){
		
	}
	
	/**
	 * 查询所有上级推广员 递归查询
	 * @作者 MoChunrun
	 * @创建日期 2014-3-5 
	 * @param parent_id
	 * @param serviceType
	 * @param status
	 * @return
	 */
	public void querySpreadMemberParents(List<SpreadMember> list,String parent_id,String serviceType,String status){
		SpreadMember sm = querySpreadMember(parent_id, serviceType, status);
		if(sm!=null){
			list.add(sm);
			if(sm.getParent_id()!=null && !"-1".equals(sm.getParent_id())){
				querySpreadMemberParents(list,sm.getParent_id(), serviceType, status);
			}
		}
	}
	
	/**
	 * 按业务查询推广员信息
	 * @作者 MoChunrun
	 * @创建日期 2014-3-5 
	 * @param spread_id 推广员id
	 * @param serviceType 业务类型
	 * @param status 可为空
	 * @return
	 */
	public SpreadMember querySpreadMember(String spread_id,String serviceType,String status){
		String sql = "select sm.*,smg.sevice_type,smg.grade,smg.parent_id from es_spread_member sm,es_spread_member_grade smg where sm.spread_id=smg.spread_id " +
				" and smg.spread_id=? and smg.sevice_type=? and sm.source_from=?";
		List params = new ArrayList();
		params.add(spread_id);
		params.add(serviceType);
		params.add(ManagerUtils.getSourceFrom());
		if(!StringUtils.isEmpty(status)){
			sql += " and sm.status=?";
			params.add(status);
		}
		List<SpreadMember> list = this.baseDaoSupport.queryForList(sql, SpreadMember.class, params.toArray());
		return list.size()>0?list.get(0):null;
	}
	
	public static class SpreadMember{
		private String spread_id;
		private String name;
		private String vested_object;
		private String vested_type;
		private String status;
		private String bank_account;
		private String bank_name;
		private String bank_account_name;
		private String mobile;
		private String create_time;
		
		private String sevice_type;
		private Integer grade; 
		private String parent_id;
		@NotDbField
		public String getParent_id() {
			return parent_id;
		}
		public void setParent_id(String parent_id) {
			this.parent_id = parent_id;
		}
		@NotDbField
		public String getSevice_type() {
			return sevice_type;
		}
		public void setSevice_type(String sevice_type) {
			this.sevice_type = sevice_type;
		}
		@NotDbField
		public Integer getGrade() {
			return grade;
		}
		public void setGrade(Integer grade) {
			this.grade = grade;
		}
		public String getSpread_id() {
			return spread_id;
		}
		public void setSpread_id(String spread_id) {
			this.spread_id = spread_id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVested_object() {
			return vested_object;
		}
		public void setVested_object(String vested_object) {
			this.vested_object = vested_object;
		}
		public String getVested_type() {
			return vested_type;
		}
		public void setVested_type(String vested_type) {
			this.vested_type = vested_type;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getBank_account() {
			return bank_account;
		}
		public void setBank_account(String bank_account) {
			this.bank_account = bank_account;
		}
		public String getBank_name() {
			return bank_name;
		}
		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}
		public String getBank_account_name() {
			return bank_account_name;
		}
		public void setBank_account_name(String bank_account_name) {
			this.bank_account_name = bank_account_name;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}
		
	}
}
