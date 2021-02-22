package zte.net.workCustomBean.cbss;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.workCustomBean.common.BasicBusiBean;

/**
 * CB副卡预提交
 * @author zhaoc
 *
 */
public class CBssMobilePreCommitBean extends BasicBusiBean {
	private Logger logger = Logger.getLogger(this.getClass());

	private IOrdOpenAccountTacheManager getOrdOpenAccountTacheManager() throws Exception{
		return SpringContextHolder.getBean("ordOpenAccountTacheManager");
	}

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		return "false";
	}
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		if(!Const.PRE_COMMIT.equals(this.getFlowData().getJson_param()))
			//不是预提交接口调用，则等待
			return false;
		
		IOrdOpenAccountTacheManager ordOpenAccountTacheManager = this.getOrdOpenAccountTacheManager();
		OrderTreeBusiRequest orderTree = this.getFlowData().getOrderTree();
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.mainViceOpen(order_id,orderTree);
		
		if(!StringUtils.equals(openActResp.getError_code(), "0")){
			logger.error(order_id+"订单预提交失败："+openActResp.getError_msg());
			throw new Exception(order_id+"订单预提交失败："+openActResp.getError_msg());
		}
		
		OrderItemExtBusiRequest orderItemExt = orderTree.getOrderItemBusiRequests().get(0)
				.getOrderItemExtBusiRequest();
		String set_sql = "active_no='"+orderItemExt.getActive_no()+
				"' , bssOrderId='"+orderItemExt.getBssOrderId()+
				"' , totalFee='"+orderItemExt.getTotalFee()+"'";
		
		this.updateOrderTree(set_sql, "es_order_items_ext", order_id, orderTree);
		
		CommonDataFactory.getInstance().updateOrderTree(order_id, "active_no");
		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private void updateOrderTree(String set_sql,String table_name,String order_id,OrderTreeBusiRequest orderTree){
		String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
		
		IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
		
		baseDaoSupport.execute(sql, order_id);
		
		INetCache cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
		
		//更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
	}
}
