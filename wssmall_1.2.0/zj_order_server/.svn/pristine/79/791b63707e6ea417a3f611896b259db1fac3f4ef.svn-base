package com.ztesoft.net.mall.core.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.ecsord.params.sr.vo.MachineNoRG;
import zte.net.ecsord.params.sr.vo.MachineNoRGResp;
import zte.net.ecsord.utils.PCWriteCardTools;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 获取写卡机组状态
 * @author duan.shaochu
 *
 */
public class CardQueueStatusTimer {
	private static Logger logger = Logger.getLogger(CardQueueStatusTimer.class);
	private static String sql = "select d.queue_code,to_char(wmsys.wm_concat(d.card_mate_code)) write_machines"
			+ " from ES_QUEUE_MANAGER c, es_queue_card_mate_manager d where d.queue_code = c.queue_no"
			+ " and d.source_from = c.source_from and d.source_from = ? group by d.queue_code";

	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//调接口获取写卡机组状态并记录数据
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		//获取配置的队列编码（写卡机组）及写卡机编码
		List writeList = support.queryForList(sql, ManagerUtils.getSourceFrom());
		if(null != writeList && writeList.size() > 0){
			Map m = null;
			for(int i = 0; i < writeList.size(); i++){
				String queue_code = "";
				String write_machines = "";
				try{
					List<MachineNoRGResp> machinesList = new ArrayList<MachineNoRGResp>();
					List<MachineNoRG> param = new ArrayList<MachineNoRG>();
					m = (Map)writeList.get(i);
					queue_code = m.get("queue_code").toString();
					write_machines = m.get("write_machines").toString();
					logger.info("queue_code["+queue_code+"],write_machines["+write_machines+"]");
					String []tmp = write_machines.split("\\,");
					for(int j = 0; j < tmp.length; j++){
						MachineNoRG machine = new MachineNoRG();
						machine.setMachineNo(tmp[j]);
						param.add(machine);
					}
					//调森锐接口获取写卡机状态并更新es_queue_card_mate_manager表状态值
					machinesList = PCWriteCardTools.getMachineNoByMachineList(param);
					if(null != machinesList && machinesList.size() > 0){
						for(MachineNoRGResp mn : machinesList){
							PCWriteCardTools.modifyMachineStatus(mn.getStatus(), queue_code, mn.getMachineNo());
						}
						//更新写卡机队列维护表ES_QUEUE_MANAGER数据
						PCWriteCardTools.modifyQueueMacNums(queue_code, machinesList.size());
					}
				}catch(Exception e){
					logger.info("log===============批量获取写卡机状态失败queue_code["+queue_code+"],write_machines["+write_machines+"]");
					e.printStackTrace();
				}
			}
		}
	}
	
}
