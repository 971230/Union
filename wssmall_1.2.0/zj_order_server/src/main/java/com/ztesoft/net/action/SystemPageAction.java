/**
 * 
 */
package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import test.TestAipAop;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.service.ISystemPageManager;

/**
 * @author zengjunhua
 *
 */
public class SystemPageAction extends WWAction{

	private ISystemPageManager systemPageManager;
		private List noticeList;
	    private List pendingOrderList;
	    private List orderList;
	    private List orderListCount = new ArrayList();
	    private List orderBList;
	    private List orderFList;
	    private List orderCList;
	    private List orderHList;
	    private int orderBCount;
	    private int orderFCount;
	    private int orderCCount;
	    private int orderHCount;
	    
	    public String showPage(){//TestAipAop test = new TestAipAop();test.test();
	    	noticeList = this.systemPageManager.getNoticeList();
	    	pendingOrderList=this.systemPageManager.getPendingOrderList();
	    	List<Map> trach=this.systemPageManager.getOrderListCount();
	    	List<String> trach_id = new ArrayList<String>();
	    	String str = "";
	    	for (int i=0;i<trach.size();i++) {
				Map<String, Object> map = (HashMap<String, Object>)trach.get(i);
				
					String trachId = (String)map.get("flow_trace_id");
					//trach_id.add(trachId);
					if(ZjEcsOrderConsts.DIC_ORDER_NODE_B.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_B_DESC);
					}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_D.equals(trachId)){
						int indexOf = str.indexOf(ZjEcsOrderConsts.DIC_ORDER_NODE_D);
						if(indexOf != -1){
							Map<String, Object> mapD = (HashMap<String, Object>)orderListCount.get(indexOf);
							int countD = (Integer)mapD.get("count");
							int countX = (Integer)map.get("count");
							mapD.put("count", String.valueOf(countD+countX));
							orderListCount.set(indexOf, mapD);
						}else{
							str += ZjEcsOrderConsts.DIC_ORDER_NODE_D;
							trach_id.add(ZjEcsOrderConsts.DIC_ORDER_NODE_D);
							map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_D_DESC+ZjEcsOrderConsts.DIC_ORDER_NODE_X_DESC);
						}
					}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_F.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_F_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_H.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_H_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_X.equals(trachId)){
	    				int indexOf = str.indexOf(ZjEcsOrderConsts.DIC_ORDER_NODE_D);
						if(indexOf != -1){
							Map<String, Object> mapD = (HashMap<String, Object>)orderListCount.get(indexOf);
							int countD = (Integer)mapD.get("count");
							int countX = (Integer)map.get("count");
							mapD.put("count", String.valueOf(countD+countX));
							orderListCount.set(indexOf, mapD);
						}else{
							str += ZjEcsOrderConsts.DIC_ORDER_NODE_D;
							trach_id.add(ZjEcsOrderConsts.DIC_ORDER_NODE_D);
							map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_D_DESC+ZjEcsOrderConsts.DIC_ORDER_NODE_X_DESC);
						}
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_A.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_A_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_C.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_C_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_E.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_E_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_G.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_G_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_I.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_I_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_J.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_J_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_K.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_K_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_L.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_L_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_M.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_M_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_O.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_O_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_R.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_R_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_S.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_S_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_T.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_T_DESC);
	    			}else if(ZjEcsOrderConsts.DIC_ORDER_NODE_Y.equals(trachId)){
						str += trachId;
						trach_id.add(trachId);
						map.put("tache_name", ZjEcsOrderConsts.DIC_ORDER_NODE_Y_DESC);
	    			}
					
					if(str.indexOf(trachId) != -1)
						orderListCount.add(map);
				}
			
	    	orderList=this.systemPageManager.getOrderList(trach_id);
	    	
	    	return"notice_page";
	    }
	    
		public ISystemPageManager getSystemPageManager() {
			return systemPageManager;
		}

		public void setSystemPageManager(ISystemPageManager systemPageManager) {
			this.systemPageManager = systemPageManager;
		}

		public List getNoticeList() {
			return noticeList;
		}
		public void setNoticeList(List noticeList) {
			this.noticeList = noticeList;
		}
		public List getPendingOrderList() {
			return pendingOrderList;
		}
		public void setPendingOrderList(List pendingOrderList) {
			this.pendingOrderList = pendingOrderList;
		}
		public List getOrderBList() {
			return orderBList;
		}
		public void setOrderBList(List orderBList) {
			this.orderBList = orderBList;
		}
		public List getOrderFList() {
			return orderFList;
		}
		public void setOrderFList(List orderFList) {
			this.orderFList = orderFList;
		}
		public List getOrderCList() {
			return orderCList;
		}
		public void setOrderCList(List orderCList) {
			this.orderCList = orderCList;
		}
		public List getOrderHList() {
			return orderHList;
		}
		public void setOrderHList(List orderHList) {
			this.orderHList = orderHList;
		}
		public int getOrderBCount() {
			return orderBCount;
		}
		public void setOrderBCount(int orderBCount) {
			this.orderBCount = orderBCount;
		}
		public int getOrderFCount() {
			return orderFCount;
		}
		public void setOrderFCount(int orderFCount) {
			this.orderFCount = orderFCount;
		}
		public int getOrderCCount() {
			return orderCCount;
		}
		public void setOrderCCount(int orderCCount) {
			this.orderCCount = orderCCount;
		}
		public int getOrderHCount() {
			return orderHCount;
		}
		public void setOrderHCount(int orderHCount) {
			this.orderHCount = orderHCount;
		}

		public List getOrderList() {
			return orderList;
		}

		public void setOrderList(List orderList) {
			this.orderList = orderList;
		}

		public List<Map<String, String>> getOrderListCount() {
			return orderListCount;
		}

		public void setOrderListCount(List<Map<String, String>> orderListCount) {
			this.orderListCount = orderListCount;
		}
	    
	    
	    
	    
	    
}
