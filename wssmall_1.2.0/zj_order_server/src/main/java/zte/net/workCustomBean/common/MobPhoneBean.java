package zte.net.workCustomBean.common;

import java.util.Map;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class MobPhoneBean extends BasicBusiBean {

	private String instance_id;
	private String order_id;
	
	public String getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String doBusi() throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		this.order_id=this.flowData.getOrderIntent().getOrder_id();
		String sql="select a.county_id,a.top_share_num from es_order_intent a where a.order_id='"+order_id+"' and a.source_from = 'ECS'";
		Map queryForMap = baseDaoSupport.queryForMap(sql, null);
		String county_id=(String) queryForMap.get("county_id");
		String share_svc_num = (String) queryForMap.get("top_share_num");
		if (StringUtil.isEmpty(county_id)|| StringUtil.isEmpty(share_svc_num)) {
			return "false";
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql2 = cacheUtil.getConfigInfo("query_mob_phone");
		String sql3 = sql2.replace("{top_share_num}", share_svc_num);
		String sql4 = sql3.replace("{county_id}", county_id.substring(0,1).toUpperCase());
		int count = baseDaoSupport.queryForInt(sql4, null);
		if (count > 0 ) {
			return "true";
		}else{
			return "false";
		}
		
	}
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		return true;
	}
}
