package zte.net.workCustomBean.common;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.vo.BSSGonghaoInfoVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class UpdateOutDealerBean extends BasicBusiBean{

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
			OrderExtBusiRequest  orderExt = orderTree.getOrderExtBusiRequest();
			String order_id = orderTree.getOrder_id();
			String order_city_code = orderExt.getOrder_city_code();
			String order_from = orderExt.getOrder_from();
			String out_opeator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
			String out_office = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
			BSSGonghaoInfoVO gonghaoInfo = new BSSGonghaoInfoVO();
			gonghaoInfo.setCity_id(order_city_code);
			gonghaoInfo.setOrder_from(order_from);
			String new_out_opeater = gonghaoInfo.getUser_code();
			String new_out_office = gonghaoInfo.getDept_id();
			if(!StringUtil.isEmpty(new_out_opeater)&&!StringUtil.isEmpty(new_out_office)
					&&!new_out_opeater.equals(out_opeator)&&!new_out_office.equals(out_office)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{"out_operator","out_office","old_out_operator","old_out_office"}, 
						new String[]{gonghaoInfo.getUser_code(),gonghaoInfo.getDept_id(),out_opeator,out_office});
			}
			
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
