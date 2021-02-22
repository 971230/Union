package com.ztesoft.net.outter.inf.taobao;

import com.ztesoft.net.eop.sdk.database.BaseSupport;

public class TestOrderTimer extends BaseSupport{
	
	public TestOrderTimer(){
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				for(;;){
					try{
						Thread.sleep(1000*2);
						testSync();
					}catch(Exception ex){
						ex.printStackTrace();
						continue ;
					}
				}
			}
		}).start();*/
	}

	private void testSync(){
//		String sql = "select t.*,t.rowid from es_co_queue t where t.object_type='DINGDAN' and status='XYCG' and rownum<11";
//		List<CoQueue> queueList = this.baseDaoSupport.queryForList(sql, CoQueue.class);
//		for(CoQueue c:queueList){
//			CoQueueRuleReq creq = new CoQueueRuleReq();
//			creq.setCoQueue(c);
//			creq.setCo_id(c.getCo_id());
//			creq.setObject_id(c.getObject_id());
//			creq.setService_code(c.getService_code());
//			creq.setSource_from(c.getSource_from());
//			
//			EcsTestReq ecsTestReq = new EcsTestReq();
//			ecsTestReq.setCreq(creq);
//			GlobalThreadLocalHolder.getInstance().clear();
//			String sessionid = GlobalThreadLocalHolder.getInstance().getUUID();
//			ecsTestReq.setUserSessionId(sessionid);
//			creq.setUserSessionId(sessionid);
//			ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC).execute(ecsTestReq, ZteResponse.class);
////			OrderStandardizing orderStandardizing = SpringContextHolder.getBean("orderStandardizing");
////			orderStandardizing.coQueue(creq);
//		}
	}
}
