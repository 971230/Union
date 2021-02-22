package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 促销活动
 * @author kingapex
 *2010-4-15上午11:57:56
 */
public class PromotionActivity implements Serializable {
	private String id;	      
	private String  name;	   	
	private int enable = -1;		
	private String  begin_time;	
	private String  end_time;	
	private String brief;		
	private int disabled ;
	private String userid;
	private String atturl;
	private int rank;
	private String source_from;
	private String real_name;
	private String pmt_code ;
	private String min_price;  //最低套餐档次
	private String max_price; //最高套餐档次
	private String region;  //活动地市
	private String package_class;  //套餐分类，多个用半角逗号隔开如：A,B,C
	private String relief_no_class;  //靓号减免类型,多个用半角逗号隔开如:1,2,3
	private String relation_id;  //货品包标识
	private String relation_name; //货品包名称 
	private String modify_eff_time; //活动修改后的生效时间
	private String status_date;  //状态时间
	private int user_type;//用戶類型,參見EcsOrderConsts.java
	
	private static final SimpleDateFormat DATEFORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String partner_id ;
	private String buy_mode ;
	
	@NotDbField
	public String getPartner_id() {
		return partner_id;
	}
	
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getAtturl() {
		return atturl;
	}
	public void setAtturl(String atturl) {
		this.atturl = atturl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String beginTime) {		
		try
		{
//			java.util.Date date=DATEFORMAT.parse(beginTime);
//			begin_time = DATEFORMAT.format(date);
			begin_time = beginTime;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
		
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime)  {
		try
		{
//			java.util.Date date=DATEFORMAT.parse(endTime);
//			end_time = DATEFORMAT.format(date);
			end_time = endTime;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}		
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	@NotDbField
	public String getReal_name() {
		return real_name;
	}
	@NotDbField
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getPmt_code() {
		return pmt_code;
	}
	public void setPmt_code(String pmt_code) {
		this.pmt_code = pmt_code;
	} 	
	
	public String getMin_price() {
		return min_price;
	}
	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}
	public String getMax_price() {
		return max_price;
	}
	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPackage_class() {
		return package_class;
	}
	public void setPackage_class(String package_class) {
		this.package_class = package_class;
	}
	public String getRelief_no_class() {
		return relief_no_class;
	}
	public void setRelief_no_class(String relief_no_class) {
		this.relief_no_class = relief_no_class;
	}
	public String getRelation_id() {
		return relation_id;
	}
	public void setRelation_id(String relation_id) {
		this.relation_id = relation_id;
	}
	public String getModify_eff_time() {
		return modify_eff_time;
	}
	public void setModify_eff_time(String modify_eff_time)  {
		try
		{
			java.util.Date date=DATEFORMAT.parse(modify_eff_time);
			this.modify_eff_time = DATEFORMAT.format(date);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}		
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		try
		{
			java.util.Date date=DATEFORMAT.parse(status_date);
			this.status_date = DATEFORMAT.format(date);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}		
	}
	@NotDbField
	public String getRelation_name() {
		return relation_name;
	}
	@NotDbField
	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}
	
	public int getUser_type()
	{
		return this.user_type;
	}
	
	public void setUser_type(int user_type) throws InvalidParameterException
	{
		if(EcsOrderConsts.ACTIVITY_USER_BOTH<= user_type && EcsOrderConsts.ACTIVITY_USER_LAST>user_type)
			this.user_type=user_type;
		else 
			throw new InvalidParameterException("the user type of this activity is in wrong range");
	}

	public String getBuy_mode() {
		return buy_mode;
	}

	public void setBuy_mode(String buy_mode) {
		this.buy_mode = buy_mode;
	}
}
