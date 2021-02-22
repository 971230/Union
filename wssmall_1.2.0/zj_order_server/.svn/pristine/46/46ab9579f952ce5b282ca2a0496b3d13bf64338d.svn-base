package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.req.QueryRunThreadStatusReq;
import params.resp.QueryRunThreadStatusrResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;

/**
 *   查询线程的启用状态
 *
 */
public class QryThreadStateTimer {
	private static Logger logger = Logger.getLogger(QryThreadStateTimer.class);
	@Resource
	private IEcsOrdManager ecsOrdManager;
	public QryThreadStateTimer() {
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(){
		
		if("N".equals(EopSetting.DB_CONNECT)){
			return;
		}
		
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//获取IP，port列表（去重）
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		StringBuilder sqlBuilder = new StringBuilder("select distinct a.ip,a.port from es_crawler_proc a where a.SOURCE_FROM = '"+ManagerUtils.getSourceFrom()+"' ");
		List<Map> listMap = baseDaoSupport.queryForList(sqlBuilder.toString());
		if(null!=listMap&&listMap.size()>0){
			for(Map map:listMap){
				String ip = (String) map.get("ip");
				String port = (String) map.get("port");
//				ZteClient client = ClientFactory.getZteRopClient("http://"+ip+":"+port+"/router", "wssmall_ecsord", "123456");
				ZteClient client = ClientFactory.getZteDubboClient("ECS");
				QueryRunThreadStatusReq req = new QueryRunThreadStatusReq();
				QueryRunThreadStatusrResp resp = null;
				try {
					resp = client.execute(req, QueryRunThreadStatusrResp.class);
				} catch (Exception e) {
					logger.info(e.getMessage());
					//设置该ip,port，下所有线程为禁用状态
					ecsOrdManager.editThreadState(ip, port, "", "allStop");
					continue;
				}
				if(StringUtil.equals(resp.error_code, "0")){
					//获取当前进程正在运行的线程名称
					Map<String, String> runThreadName = resp.getRunThreadName();
					if(runThreadName == null || runThreadName.size() == 0){
						ecsOrdManager.editThreadState(ip, port, "", "allStop");
						continue;
					}
					//获取数据库表中该IP，port下的所有线程名称
					sqlBuilder = new StringBuilder("select a.THREAD_NAME from es_crawler_proc a where a.SOURCE_FROM = '"+ManagerUtils.getSourceFrom()+"' and a.IP = ? and a.port = ? ");
					List<Map> listNameMap = baseDaoSupport.queryForList(sqlBuilder.toString(),ip,port);
					//遍历相似线程名称
						for(Map nameMap :listNameMap){
							String thread_name = nameMap.get("thread_name").toString();
							if(!StringUtil.isEmpty(runThreadName.get(thread_name)))
							//根据ip,port,thread_name逐一启用线程
							ecsOrdManager.editThreadState(ip, port,thread_name, "oneStart");
							else
							//根据ip,port,thread_name逐一禁用线程
							ecsOrdManager.editThreadState(ip, port,thread_name, "oneStop");
					}
				}
				else{
					logger.info(resp.getError_msg());
					ecsOrdManager.editThreadState(ip, port, "", "allStop");
				}
			}
		
		}
		
	}
		
}
