package com.ztesoft.net.mall.core.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.outter.inf.huasheng.HuaShengGoodsService;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.OutterTmpl;

/**
 * 华盛商品主数据同步定时任务
 * @作者 Rapon
 * @创建日期 2016-3-21
 * @版本 V 1.0
 */
public class HuashengGoodsInfTimerService {
	private static Logger logger = Logger.getLogger(HuashengGoodsInfTimerService.class);
	public static final Object _SYNC_KEY = new Object();
	
	public HuashengGoodsInfTimerService(){
		//start();
	}
	
	public void exeGoodsSync() throws Exception{
		/**提交前此段代码要放开
		 * 
		 */
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"exeGoodsSync"))
			return ;
		logger.info("HuashengGoodsInfTimerService.exeGoodsSync()");
		
		synchronized(_SYNC_KEY){
			IOutterECSTmplManager outterTmplManager = SpringContextHolder.getBean("outterECSTmplManager");
			List<OutterTmpl>  list = outterTmplManager.listOuterTmpl(0);//1:华盛主商品数据获取
			for(OutterTmpl tmpl:list){
				if("93".equals(tmpl.getTmpl_id())){
					try{
						outterTmplManager.updateRunStatus("00R", tmpl.getTmpl_id());
						/*此处调华盛主商品数据接口*/
						HuaShengGoodsService service = SpringContextHolder.getBean("huashengGoodsService");
						boolean flag = service.executeInfService(tmpl);

						if(flag){//成功,执行时间与执行状态均要更新
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String last_execute = tmpl.getNext_execute();
							String next_execute = df.format(new Date(System.currentTimeMillis()+tmpl.getExecute_min()*1000*60));
							tmpl.setLast_execute(last_execute);
							tmpl.setNext_execute(next_execute);
							tmpl.setError_msg("success");
							outterTmplManager.updateTmplExecuteInfo(tmpl.getTmpl_id(), 0, 0, 5, tmpl.getData_end_time(), tmpl.getError_msg());
						}else{//失败,只将执行状态改为00A
							tmpl.setError_msg("failed");
						}
					}catch(Exception ex){//异常即失败,仅更新执行状态
						logger.info("华盛商品主数据获取异常HuashengGoodsInfTimerService");
						ex.printStackTrace();
						continue ;
					}
					outterTmplManager.updateRunStatus("00A", tmpl.getTmpl_id());
				}
			}
		}
	}
}
