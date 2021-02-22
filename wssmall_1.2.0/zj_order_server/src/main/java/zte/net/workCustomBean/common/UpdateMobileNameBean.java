package zte.net.workCustomBean.common;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class UpdateMobileNameBean extends BasicBusiBean{

	private String order_id;
	
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
		try{
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			OrderTreeBusiRequest orderTree = this.flowData.getOrderTree();
			String order_id = orderTree.getOrder_id();
			String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
			String element_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "element_id");
			String mobile_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "mobile_type");
			String sql = " select terminal_name from es_goods_action_element where element_id='"+element_id+"' and mobile_type='"+mobile_type+"' and goods_id='"+goods_id+"' and rownum=1 ";
			List<Map> list = baseDaoSupport.queryForList(sql, null);
			if(list.size()<=0){
				throw new Exception("未查询到终端名称，请联系管理员！");
			}
			String object_name = Const.getStrValue(list.get(0), "terminal_name");
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"object_name"}, new String[]{object_name});
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return "true";
		
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