package zte.net.ecsord.params.ecaop.req.vo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class BSSGonghaoInfoVO {

	private String city_name;
	private String area;
	private String city_id;
	private String user_code;
	private String dept_id;
	private String order_from;
	private String county_id;
	private Map GongHaoInfo;
	
	public String getCity_name() {
		if(!StringUtils.isEmpty(city_id)&&StringUtil.equals("330100", city_id)){
			city_name="杭州";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330300", city_id)){
			city_name="温州";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330800", city_id)){
			city_name="衢州";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330500", city_id)){
			city_name="湖州";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330400", city_id)){
			city_name="嘉兴";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330600", city_id)){
			city_name="绍兴";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331000", city_id)){
			city_name="台州";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331100", city_id)){
			city_name="丽水";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330700", city_id)){
			city_name="金华";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330900", city_id)){
			city_name="舟山";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330200", city_id)){
			city_name="宁波";
		}
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getArea() {
		if(!StringUtils.isEmpty(city_id)&&StringUtil.equals("330100", city_id)){
			area="360";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330300", city_id)){
			area="470";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330800", city_id)){
			area="468";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330500", city_id)){
			area="362";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330400", city_id)){
			area="363";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330600", city_id)){
			area="365";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331000", city_id)){
			area="476";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331100", city_id)){
			area="469";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330700", city_id)){
			area="367";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330900", city_id)){
			area="364";
		}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330200", city_id)){
			area="370";
		}
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getUser_code() {
		/*if(StringUtils.equals("10078", order_from)) {
			if(!StringUtils.isEmpty(city_id)&&StringUtil.equals("330100", city_id)){//杭州
				user_code="ABDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330300", city_id)){//温州
				user_code="BBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330800", city_id)){//衢州
				user_code="CBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330500", city_id)){//湖州
				user_code="DBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330400", city_id)){//嘉兴
				user_code="EBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330600", city_id)){//绍兴
				user_code="FBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331000", city_id)){//台州
				user_code="GBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331100", city_id)){//丽水
				user_code="HBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330700", city_id)){//金华
				user_code="IBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330900", city_id)){//舟山
				user_code="JBDQ0220";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330200", city_id)){//宁波
				user_code="KBDQ0220";
			}
		}else {
			if(!StringUtils.isEmpty(city_id)&&StringUtil.equals("330100", city_id)){
				user_code="AEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330300", city_id)){
				user_code="BEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330800", city_id)){
				user_code="CEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330500", city_id)){
				user_code="DEdq0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330400", city_id)){
				user_code="EEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330600", city_id)){
				user_code="FEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331000", city_id)){
				user_code="GEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331100", city_id)){
				user_code="HEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330700", city_id)){
				user_code="IEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330900", city_id)){
				user_code="JEDQ0033";
			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330200", city_id)){
				user_code="KEDQ0033";
			}
//			if(!StringUtils.isEmpty(city_id)&&StringUtil.equals("330100", city_id)){
//				user_code="AQZDQD02";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330300", city_id)){
//				user_code="BQDZQD08";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330800", city_id)){
//				user_code="CQQZdzqd";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330500", city_id)){
//				user_code="DQDZQD02";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330400", city_id)){
//				user_code="EQ178827";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330600", city_id)){
//				user_code="FQYC0253";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331000", city_id)){
//				user_code="GD668451";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("331100", city_id)){
//				user_code="HQ050850";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330700", city_id)){
//				user_code="IQ006779";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330900", city_id)){
//				user_code="JQZS0195";
//			}else if (!StringUtils.isEmpty(city_id)&&StringUtil.equals("330200", city_id)){
//				user_code="KQDZQD02";
//			}
		}*/
		String usercode = Const.getStrValue(getGongHaoInfo(), "user_code");
		user_code = usercode;
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getDept_id() {
		/*if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330100", city_id)) {
			dept_id = "A66839";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330300", city_id)) {
			dept_id = "100965";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330800", city_id)) {
			dept_id = "C66840";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330500", city_id)) {
			dept_id = "D66841";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330400", city_id)) {
			dept_id = "E66842";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330600", city_id)) {
			dept_id = "Q13648";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("331000", city_id)) {
			dept_id = "G66845";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("331100", city_id)) {
			dept_id = "H66847";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330700", city_id)) {
			dept_id = "I66848";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330900", city_id)) {
			dept_id = "S01663";
		} else if (!StringUtils.isEmpty(city_id) && StringUtil.equals("330200", city_id)) {
			dept_id = "K66843";
		}*/
		String deptid = Const.getStrValue(getGongHaoInfo(), "dept_id");
		dept_id = deptid;
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getCounty_id() {
		String countyid = Const.getStrValue(getGongHaoInfo(), "county_id");
		county_id = countyid;
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public Map getGongHaoInfo() {
		if(null==GongHaoInfo){
			String stype = "DIC_BSSGonghaoInfoVO";
			if(StringUtil.isEmpty(city_id)){
				throw new RuntimeException("需要city_id入参");
			}
			String new_stype = stype+order_from;
			String sql = " select t.pkey as city_id,t.pname as user_code,t.codea as county_id,t.codeb as dept_id from es_dc_public_ext t where t.stype='"+new_stype+"' and t.pkey='"+city_id+"' ";
			String sql1 = " select t.pkey as city_id,t.pname as user_code,t.codea as county_id,t.codeb as dept_id from es_dc_public_ext t where t.stype='"+stype+"' and t.pkey='"+city_id+"' ";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			List list = baseDaoSupport.queryForList(sql, null);
			if(list.size()<=0){
				list = baseDaoSupport.queryForList(sql1, null);
			}
			if(list.size()<=0){
				throw new RuntimeException("没有配置来源对应BSS工号信息和默认BSS工号信息");
			}else{
				Map gonghao = (Map)list.get(0);
				GongHaoInfo = gonghao;
			}
		}
		
		return GongHaoInfo;
	}
	public void setGongHaoInfo(Map gongHaoInfo) {
		GongHaoInfo = gongHaoInfo;
	}
	
	
}
