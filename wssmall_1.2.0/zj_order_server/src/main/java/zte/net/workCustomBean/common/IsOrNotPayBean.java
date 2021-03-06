package zte.net.workCustomBean.common;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class IsOrNotPayBean extends BasicBusiBean{
	IDaoSupport baseDaoSupport;
	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		String order_id=this.getFlowData().getOrderTree().getOrder_id();
		baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		StringBuffer sql_pay=new StringBuffer();
		sql_pay.append("select a.order_amount,a.pay_status from es_order a where a.order_id='").append(order_id).append("' ");
		List<Map<String, Object>> map_param=baseDaoSupport.queryForList(sql_pay.toString());
		String order_amount= "";

		String pay_status= "";
		if(map_param!=null&&map_param.size()>0) {
			order_amount= String.valueOf(map_param.get(0).get("order_amount"));
			pay_status=String.valueOf(map_param.get(0).get("pay_status"));
		}
		if("1".equals(pay_status)&&(!"0".equals(order_amount))) {
			return "已支付";
		}else {
			return "未支付";
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
		System.out.println("doBackWaitCheck()");
		return true;
	}

}
