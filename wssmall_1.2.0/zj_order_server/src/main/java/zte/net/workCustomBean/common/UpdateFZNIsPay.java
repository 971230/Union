package zte.net.workCustomBean.common;

import org.apache.poi.hssf.record.formula.functions.T;

import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * 泛智能终端翻转支付状态
 * 
 * @author zzj
 *
 */
public class UpdateFZNIsPay extends BasicBusiBean {
	
	protected IDaoSupport<T> baseDaoSupport;
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	/**
	 * 业务处理方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception {
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		/*
		 * 泛智能终端支付状态更改为未支付状态，is_pay在mobile收单设为SMSF(上门收费=1)
		 * 支付状态应该是未支付，支付类型为上门收款，实收金额为0
		 */
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
		CommonDataFactory.getInstance().updateAttrFieldValue(
				order_id,
				new String[] { AttrConsts.IS_PAY, AttrConsts.PAY_TYPE,AttrConsts.PAY_METHOD, AttrConsts.ORDER_REAL_FEE },
				new String[] { "0", "SMSF", "WZF", "0" });
		
		String set_sql = " pay_type = 'SMSF' ";
		String table_name = "es_order_ext";
		orderExtBusiReq.setOrder_model("SMSF");
		this.updateOrderTree(set_sql, table_name, order_id, orderTree);

		return "false";
	}

	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断， 不是后台等待环节的业务处理代码无需实现该方法
	 * 
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception {
		return false;
	}
	
	public void updateOrderTree(String set_sql,String table_name,String order_id,OrderTreeBusiRequest orderTree){
		String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
		if(this.baseDaoSupport==null){
			this.baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
		}
		this.baseDaoSupport.execute(sql, order_id);
		//更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
		
	}
}
