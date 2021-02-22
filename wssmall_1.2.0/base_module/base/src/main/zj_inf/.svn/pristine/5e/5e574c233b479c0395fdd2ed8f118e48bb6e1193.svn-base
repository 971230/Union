package zte.net.ecsord.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.order.req.OrderActionLogReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.sr.req.WriMachStaQueRequest;
import zte.net.ecsord.params.sr.resp.WriMachStaQueResponse;
import zte.net.ecsord.params.sr.vo.Station;
import zte.net.ecsord.params.sr.vo.WriMachStaQue;
import zte.net.ecsord.params.sr.vo.WriMachStaQueBody;
import zte.net.ecsord.params.sr.vo.WriMachStaResp;
import zte.net.ecsord.params.writecard.MachinesGroup;
import zte.net.ecsord.params.writecard.MachinesModel;
import zte.net.ecsord.params.writecard.MaterielBox;
import zte.net.ecsord.params.writecard.MaterielBoxOrder;
import zte.net.ecsord.params.writecard.WriteCardWorkStation;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;


public class AutoWriteCardCacheTools {

	private static final String WRITE_CARD_WORKSTATION_KEY_PREFIX = "AUTO_CARD_MACHINES";	//写卡机位缓存
	private static final String MATERIEL_BOX_KEY_PREFIX = "AUTO_CARD_MATERIEL";	//料箱队列缓存
	private static final String MACHINE_GROUP_KEY_PREFIX = "AUTO_CARD_MACHINE_GROUP";	//写卡机组缓存
	private static final String MACHINE_STATUS_KEY_PREFIX = "AUTO_CARD_MACHINE_STATUS";	//写卡机状态缓存
	private static final String ARRIVE_MACHINE_KEY_PREFIX = "AUTO_ARRIVE_MACHINE_ORDER"; //到达写卡机位订单缓存
	public static final String MACHINE_CACHE_LOCK_KEY_PREFIX = "CACHE_LOCK";	//缓存锁
	public static final String MACHINE_CACHE_LOCK_KEY_PREFIX_B = "CACHE_LOCK_B";	//缓存锁
	public static final String OUTCARD_CACHE_LOCK_KEY_PREFIX = "OUTCARD_CACHE_LOCK";	//退卡缓存锁
	public static final String OUTCARD_CACHE_LOCK_KEY_PREFIX_B = "OUTCARD_CACHE_LOCK_B";	//退卡缓存锁
	public static final String SHOTOFF_CACHE_LOCK_KEY_PREFIX = "SHOTOFF_CACHE_LOCK";	//缓存锁
	public static final String TEMP_CACHE_ARRIVE_KEY_PREFIX = "TEMP_ARRIVE_CACHE_LOCK";	//到达写卡机位
	public static final String TEMP_CACHE_WRITE_S_KEY_PREFIX = "TEMP_WRITE_S_CACHE_LOCK";	//物理写卡完成
	public static final String TEMP_CACHE_WRITE_STAT_KEY_PREFIX = "TEMP_WRITE_STAT_CACHE_LOCK";	//写卡机位编码
	private static final String MACHINE_STATUS_1 = "1";	//正常
	private static final String MACHINE_STATUS_2 = "2";	//写卡中
	private static final String MACHINE_STATUS_3 = "3";	//空闲
	private static final String MACHINE_STATUS_4 = "4";	//异常	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHH");

	
	static INetCache cache;
	private static int NAMESPACE = 400;
	private static int time = 60*24*60*5;
	private static int addTime = 300;
	private static Logger logger = Logger.getLogger(AutoWriteCardCacheTools.class);
	static{
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	private static List<Map<String, String>> getWriteCarMacSta(String station,String read_id,IDaoSupport support) {
		if (support == null)
			support = SpringContextHolder.getBean("baseDaoSupport");
		return support.queryForList("select t.station_id,t.read_id,t.status from ES_WIRTE_CAR_MAC_STA t where t.station_id=? and t.read_id=? and t.source_from='"+ManagerUtils.getSourceFrom()+"'",station,read_id);
	}
	
	/**
	 * 获取写卡机位缓存锁
	 * @param workStation
	 */
	public static boolean addCacheLock(String key, String value){
		boolean flag = false;
		try{
			flag = cache.add(NAMESPACE,key,value,addTime);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox缓存写卡机位失败[" + key + ","+value+"]," + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 移除写卡机位缓存锁
	 * @param workStation
	 */
	public static void removeCacheLock(String key){
		try{
			cache.delete(NAMESPACE, key);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox移除写卡机位缓存失败["+key+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取写卡机位缓存锁
	 * @param String
	 */
	public static String getCacheLock(String key){
		try{
			return (String)cache.get(NAMESPACE, key);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取写卡机位缓存失败["+key+"]," + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取写卡机位列表
	 * @return
	 */
	public static WriteCardWorkStation getWorkStation(){
		WriteCardWorkStation workStations = new WriteCardWorkStation();
		try{
			workStations = (WriteCardWorkStation)cache.get(NAMESPACE, WRITE_CARD_WORKSTATION_KEY_PREFIX);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取写卡机位列表失败." + e.getMessage());
			e.printStackTrace();
		}
		return workStations;
	}
	
	/**
	 * 缓存写卡机位列表
	 * @param workStations
	 */
	public static void setWorkStation(String station){
		try{
			appendWorkStation(station);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox设置写卡机位缓存出错." + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 追加写卡机位
	 * @param workStations
	 */
	public static void appendWorkStation(String station){
		try{
			WriteCardWorkStation workStations = (WriteCardWorkStation)cache.get(NAMESPACE, WRITE_CARD_WORKSTATION_KEY_PREFIX);
			if(null != workStations && workStations.getWorkStations().size() > 0){
				if(!workStationISExists(station,workStations)){
					//追加新的写卡机位
					workStations.getWorkStations().add(station);
				}
			}else{
				workStations = new WriteCardWorkStation();
				ArrayList<String> works = new ArrayList<String>();
				works.add(station);
				workStations.setWorkStations(works);
			}
			cache.set(NAMESPACE,WRITE_CARD_WORKSTATION_KEY_PREFIX,workStations,time);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox追加写卡机位缓存出错." + station + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断写卡机位是否存在
	 * @param workStations
	 * @return
	 */
	public static boolean workStationISExists(String station , WriteCardWorkStation workStation){
		boolean flag = false;
		try{
			if(null != workStation && workStation.getWorkStations().size() > 0){
				for(String str : workStation.getWorkStations()){
					if(StringUtils.equals(str, station)){
						flag = true;
						break;
					}
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox判断写卡机位是否存在出错." + station + "," + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据写卡机位获取料箱队列
	 * @param station
	 * @return
	 */
	public static MaterielBox getMaterielBoxByWorkStation(String station){
		MaterielBox box = new MaterielBox();
		try{
			box = (MaterielBox)cache.get(NAMESPACE, MATERIEL_BOX_KEY_PREFIX + station);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox根据写卡机位获取料箱队列失败." + station + "," + e.getMessage());
			e.printStackTrace();
		}
		return box;
	}
	
	/**
	 * 缓存料箱队列
	 * @param order
	 */
	public static void setMaterielBox(MaterielBoxOrder order){
		try{
			if(null == order){
				return;
			}
			appendMaterielBox(order);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox缓存物料箱队列失败." + order.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 追加料箱队列
	 * @param order
	 */
	public static void appendMaterielBox(MaterielBoxOrder order){
		try{
			if(null == order){
				return;
			}
			MaterielBox box = (MaterielBox)cache.get(NAMESPACE, MATERIEL_BOX_KEY_PREFIX + order.getWorkStation());
			if(null != box && box.getOrders().size() > 0){
				if(!materielBoxISExists(box,order)){
					//不存在添加队列
					box.getOrders().add(order);
				}
			}else{
				box = new MaterielBox();
				box.getOrders().add(order);
			}
			cache.set(NAMESPACE,MATERIEL_BOX_KEY_PREFIX + order.getWorkStation(),box,time);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox追加物料箱队列失败." + order.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断订单在料箱队列中是否存在
	 * @param order
	 * @return
	 */
	public static boolean materielBoxISExists(MaterielBox box , MaterielBoxOrder order){
		boolean flag = false;
		try{
			if(null == order){
				return false;
			}
			if(null != box){
				ArrayList<MaterielBoxOrder> orders = box.getOrders();
				for(MaterielBoxOrder o : orders){
					if(StringUtils.equals(o.getOrder_id(), order.getOrder_id())){
						flag = true;
						break;
					}
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox判断订单在料箱队列中是否存在出错." + order.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取料箱队列第一个订单
	 * @param workStation  写卡机位
	 * @return
	 */
	public static String getMaterielBoxFirstOrder(String workStation){
		String order_id = "";
		try{
			MaterielBox box = (MaterielBox)cache.get(NAMESPACE, MATERIEL_BOX_KEY_PREFIX + workStation);
			if(null != box){
				if(null != box.getOrders() && box.getOrders().size() > 0){
					ArrayList<MaterielBoxOrder> orders = box.getOrders();
					order_id = orders.get(0).getOrder_id();
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取料箱队列第一个订单失败" + e.getMessage());
			e.printStackTrace();
		}
		return order_id;
	}
	
	/**
	 * 初始化料箱队列
	 */
	public static String initMaterielBox(){
		String msg = "";
		try{
			WriteCardWorkStation workStation = (WriteCardWorkStation)cache.get(NAMESPACE, WRITE_CARD_WORKSTATION_KEY_PREFIX);
			if(null != workStation){
				for(String station : workStation.getWorkStations()){
					//清除缓存锁
					cache.delete(NAMESPACE, MACHINE_CACHE_LOCK_KEY_PREFIX + station);
					//料箱队列
					cache.delete(NAMESPACE, MATERIEL_BOX_KEY_PREFIX + station);
					//到达写卡位机订单
					cache.delete(NAMESPACE, ARRIVE_MACHINE_KEY_PREFIX + station);
					//写卡机状态
					MachinesGroup mgroup = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + station);
					if(null != mgroup && mgroup.getMachines().size() > 0){
						IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
						support.execute("delete from ES_WIRTE_CAR_MAC_STA where STATION_ID=? and SOURCE_FROM = '"+ManagerUtils.getSourceFrom()+"'",station);
						/*
						for(MachinesModel m : mgroup.getMachines()){							
							cache.delete(NAMESPACE, MACHINE_STATUS_KEY_PREFIX + station + m.getKey());
						}
						*/
					}
					//写卡机组
					cache.delete(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + station);
				}
				//写卡机位
				cache.delete(NAMESPACE, WRITE_CARD_WORKSTATION_KEY_PREFIX);
			}
		}catch(Exception e){
			msg = "初始化缓存失败" + e.getMessage();
			logger.info("log===============dealMaterielBox初始化缓存失败" + e.getMessage());
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 从料箱队列中移出订单
	 * @param materiel
	 */
	public static void removeOrderFromMaterielBox(MaterielBoxOrder order){
		try{
			if(null == order){
				return;
			}
			int index = -1;
			MaterielBox box = (MaterielBox)cache.get(NAMESPACE, MATERIEL_BOX_KEY_PREFIX + order.getWorkStation());
			if(null != box){
				ArrayList<MaterielBoxOrder> orders = box.getOrders();
				for(int i = 0; i < orders.size(); i++){
					if(StringUtils.equals(order.getOrder_id(), orders.get(i).getOrder_id())){
						index = i;
						break;
					}
				}
				//删除后缓存
				if(index >= 0){
					orders.remove(index);
					box.setOrders(orders);
					cache.set(NAMESPACE,MATERIEL_BOX_KEY_PREFIX + order.getWorkStation(),box,time);
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox从料箱队列中移出订单失败." + order.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置写卡机组缓存
	 * @param machine
	 */
	public static void setMachinesGroup(MachinesModel machine){
		try{
			//调森锐接口获取最新的写机状态(硬件状态)
			if(null != machine && null != machine.getWorkStation() && !"".equals(machine.getWorkStation())){
				List<Station> station = getMachineNoByWorkStation(machine.getWorkStation());
				if(null != station && station.size() > 0){
					for(Station s : station){
						MachinesModel m = new MachinesModel();
						m.setKey(s.getMachineNo());
						m.setValue(s.getStatus());
						m.setWorkStation(machine.getWorkStation());
						appendMachinesGroup(m);
					}
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox设置写卡组缓存失败." + machine.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 新增写卡机组缓存
	 * 写卡机组中的状态为硬件状态
	 * @param machine
	 */
	public static void appendMachinesGroup(MachinesModel machine){
		try{
			if(null == machine){
				return;
			}
			MachinesGroup mgroup = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + machine.getWorkStation());
			ArrayList<MachinesModel> mms = null;
			if(null != mgroup && mgroup.getMachines().size() > 0){
				mms = mgroup.getMachines();
				boolean flag = true;
				for(int i = 0; i < mms.size(); i++){
					//缓存中存在则更新
					if(mms.get(i).getKey().equals(machine.getKey())){
						mms.set(i, machine);
						flag = false;
					}
				}
				//缓存中不存在则添加
				if(flag){
					mms.add(machine);
				}
				mgroup.setMachines(mms);
			}else{
				mgroup = new MachinesGroup();
				mms = new ArrayList<MachinesModel>();
				mms.add(machine);
				mgroup.setMachines(mms);
			}
			//缓存数据
			cache.set(NAMESPACE,MACHINE_GROUP_KEY_PREFIX + machine.getWorkStation(),mgroup,time);
//			logger.info("log===============dealMaterielBox缓存写卡机组" + DataUtil.toJson(mgroup));

			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			//缓存写卡机状态			
			for(MachinesModel m : mms){
				//管理状态默认为空，表示空闲状态，为订单id时表示该订单正常在写卡
				support.execute(
						"insert into ES_WIRTE_CAR_MAC_STA (STATION_ID,READ_ID,STATUS,SOURCE_FROM) select ?,?,'','"+ManagerUtils.getSourceFrom()+"' from dual where not exists(select 1 from ES_WIRTE_CAR_MAC_STA where station_id=? and read_id=? and source_from='ECS')",
						m.getWorkStation(), m.getKey(), m.getWorkStation(),
						m.getKey());
				/*
				m.setValue("");
				MachinesModel cacheMachine = (MachinesModel)cache.get(NAMESPACE, MACHINE_STATUS_KEY_PREFIX + m.getWorkStation() + m.getKey());
				if(null == cacheMachine){
					//未缓存写卡机状态的则缓存
					cache.set(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + m.getWorkStation() + m.getKey(),m,time);
				}
				*/
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox设置写卡组缓存失败." + machine.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取写卡机组中的空闲写卡机列表（硬件空闲）
	 * @param group
	 * @return
	 */
	public static ArrayList<MachinesModel> getIdleMachinesFromGroup(MachinesGroup group){
		ArrayList<MachinesModel> idleMachines = new ArrayList<MachinesModel>();
		try{
			MachinesModel machine = new MachinesModel();
			machine.setWorkStation(group.getWorkStation());
			setMachinesGroup(machine);//根据写卡机位从森锐获取写卡机
			idleMachines = new ArrayList<MachinesModel>();
			MachinesGroup mgroup = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + group.getWorkStation());
			if(null == mgroup){
				logger.info("log===============dealMaterielBox写卡机组缓存为空["+group.getWorkStation()+"]");
				return idleMachines;
			}
			ArrayList<MachinesModel> machines = mgroup.getMachines();
			if(null != machines && machines.size() > 0){
				for(MachinesModel m : machines){
					if(StringUtils.equals(m.getValue(), MACHINE_STATUS_3)){
						idleMachines.add(m);
					}
				}
			}
//			logger.info("log===============dealMaterielBox获取硬件空闲写卡机" + DataUtil.toJson(idleMachines));
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取写卡机组中的空闲写卡机列表失败." + group.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
		return idleMachines;
	}
	
	/**
	 * 根据写卡机位获取写卡机组
	 * @param workStation
	 * @return
	 */
	public static MachinesGroup getMachinesGroupByWorkStation(String station){
		MachinesGroup group = new MachinesGroup();
		try{
			group = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + station);
		}catch(Exception e){
			logger.info("log===============dealMaterielBox根据写卡机位获取写卡机组失败." + station + "," + e.getMessage());
			e.printStackTrace();
		}
		return group;
	}
	
	
	/**
	 * 修改写卡机状态管理状态（非硬件状态）
	 * @param machine
	 */
	public static void modifyMachineStatus(MachinesModel machine){
		if(null == machine){
			return;
		}
		String lock_name ="modifyMachine" + machine.getWorkStation() + machine.getKey();
		Map lock = getMutexLock(lock_name);
		synchronized(lock){
			try{
				logger.info(dateFormat.format(new Date()) + "modifyMachineStatusmodifyMachineStatusLock" + machine.toString());
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				List<Map<String, String>> macs = getWriteCarMacSta(machine.getWorkStation(),machine.getKey(), support);
				if (macs != null && macs.size() > 0) {
					logger.info(dateFormat.format(new Date()) + "modifyMachinemodifyMachinebefore" + macs.get(0).toString());
					support.execute("update ES_WIRTE_CAR_MAC_STA set status=? where station_id=? and read_id=? and SOURCE_FROM = '"+ManagerUtils.getSourceFrom()+"'", machine.getValue(),machine.getWorkStation(),machine.getKey());
				} else {
					support.execute("insert into ES_WIRTE_CAR_MAC_STA (STATION_ID,READ_ID,STATUS,SOURCE_FROM) values (?,?,?,'"+ManagerUtils.getSourceFrom()+"')",machine.getWorkStation(),machine.getKey(),machine.getValue());
				}
				
				/*
				MachinesModel modifyMachine = new MachinesModel();
				modifyMachine.setKey(machine.getKey());
				modifyMachine.setValue(machine.getValue());
				modifyMachine.setWorkStation(machine.getWorkStation());
				cache.set(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + modifyMachine.getWorkStation() + modifyMachine.getKey(),modifyMachine,time);
				logger.info(dateFormat.format(new Date()) + "log===============dealMaterielBox修改写卡机管理状态为:" + modifyMachine.toString());
				MachinesModel currentMachine = (MachinesModel)cache.get(NAMESPACE, MACHINE_STATUS_KEY_PREFIX + modifyMachine.getWorkStation() + modifyMachine.getKey());
				logger.info(dateFormat.format(new Date()) + "log===============dealMaterielBox修改写卡机管理状态为,保存后的对象:" + currentMachine.toString());
				
				if(null == currentMachine || StringUtils.equals(currentMachine.getValue(), modifyMachine.getValue())){
					logger.info("log===============dealMaterielBox写卡机["+modifyMachine.getWorkStation() + modifyMachine.getKey()+"]保存失败");
					logger.info("log===============dealMaterielBox写卡机保存前的值" + modifyMachine.toString());
					logger.info("log===============dealMaterielBox写卡机保存后的值" + currentMachine.toString());
					cache.set(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + modifyMachine.getWorkStation() + modifyMachine.getKey(),modifyMachine,time);
				}
				*/
				// 则同时手动更新一下硬件状态
				MachinesGroup mgroup = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + machine.getWorkStation());
				if(null != mgroup){
					ArrayList<MachinesModel> machines = mgroup.getMachines();
					if(null != machines && machines.size() > 0){
						for(MachinesModel m : machines){
							if(StringUtils.equals(m.getKey(), machine.getKey())){
								if (StringUtils.isEmpty(machine.getValue())) { // 空闲
									m.setValue(MACHINE_STATUS_3);
								}else{ // 分配了订单则写卡中
									m.setValue(MACHINE_STATUS_2);
								}
							}
						}
					}
					cache.set(NAMESPACE,MACHINE_GROUP_KEY_PREFIX + machine.getWorkStation(),mgroup,time);
				}
			}catch(Exception e){
				logger.info("log===============dealMaterielBox修改写卡机状态管理状态（非硬件状态）失败." + machine.toString() + "," + e.getMessage());
				e.printStackTrace();
			}finally{
				removeMutexLock(lock_name);
			}
		}
	}
	
	/**
	 * 获取空闲写卡机(硬件空闲、管理状态也空闲，可正常写卡)
	 * @param group
	 * @return
	 */
	public static ArrayList<MachinesModel> getIdleMachine(MachinesGroup group){
		ArrayList<MachinesModel> resultMachines = new ArrayList<MachinesModel>();
		try{
			//获取硬件状态(空闲)
			ArrayList<MachinesModel> idleMachines = getIdleMachinesFromGroup(group);
			for(MachinesModel m : idleMachines){
				//写卡机的管理状态(空闲)
				List<Map<String, String>> macs = getWriteCarMacSta(m.getWorkStation(),m.getKey(), null);
				if (macs != null && macs.size() > 0) {
					if(StringUtils.isEmpty(macs.get(0).get("status"))){
						resultMachines.add(m);
					}
				}
				
				/*
				MachinesModel mm = (MachinesModel)cache.get(NAMESPACE, MACHINE_STATUS_KEY_PREFIX + m.getWorkStation() + m.getKey());
				if(StringUtils.isEmpty(mm.getValue())){
					resultMachines.add(m);
				}
				*/
			}
			logger.info("log===============dealMaterielBox获取空闲写卡机组resultMachines" + DataUtil.toJson(resultMachines));
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取空闲写卡机失败." + group.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
		return resultMachines;
	}
	
	/**
	 * 根据写卡机位调森锐接口获取写卡机信息
	 * @param workStation
	 * @return
	 */
	public static List<Station> getMachineNoByWorkStation(String workStation){
		List<Station> station = new ArrayList<Station>();
		try{
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			WriMachStaQueRequest wriReq = new WriMachStaQueRequest();
			WriMachStaQue staQue = new WriMachStaQue();
			staQue.setWorkStation(workStation);
			wriReq.setNotNeedReqStrData(staQue);
			WriMachStaQueResponse wriResp = client.execute(wriReq, WriMachStaQueResponse.class);
			if(null != wriResp && ConstsCore.ERROR_SUCC.equals(wriResp.getError_code())){
				WriMachStaResp sr_response = wriResp.getSr_response();
				if(null != sr_response){
					WriMachStaQueBody body = sr_response.getBody();
					if(null != body){
						station = body.getStation();
					}
				}
			}else{
				logger.info("log===============dealMaterielBox调森锐获取写卡机失败["+workStation+"]");
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox根据写卡机位获取写卡机信息失败,["+workStation+"]" + e.getMessage());
			e.printStackTrace();
		}
		return station;
	}
	
	/**
	 * 根据接口初始化多写卡相关缓存
	 * @param response
	 */
	public static void initCacheFromInf(String order_id , String workStation){
		logger.info("log===============dealMaterielBox料箱入队列,写卡位["+workStation+"],订单["+order_id+"]");
//		初始化时不获取写卡机组缓存
//		List<Station> station = getMachineNoByWorkStation(workStation);
//		if(null != station && station.size() > 0){
			//缓存写卡机位
			setWorkStation(workStation);
			
			//缓存料箱队列
			MaterielBoxOrder order = new MaterielBoxOrder();
			order.setOrder_id(order_id);
			order.setWorkStation(workStation);
			setMaterielBox(order);
			
//			for(Station s : station){
//				//缓存写卡机组
//				MachinesModel machine = new MachinesModel();
//				machine.setWorkStation(workStation);
//				machine.setKey(s.getMachineNo());
//				machine.setValue(s.getStatus());
//				appendMachinesGroup(machine);
//			}
//		}
	}
	
	/**
	 * 判断写卡机组下的写卡机是否都为异常  是:true，否:false
	 * @param group
	 * @return
	 */
	public static boolean machineISAllException(MachinesGroup group){
		boolean flag = true;
		try{
			MachinesGroup mgroup = (MachinesGroup)cache.get(NAMESPACE, MACHINE_GROUP_KEY_PREFIX + group.getWorkStation());
			if(null == mgroup || mgroup.getMachines().size() <= 0){
				logger.info("log===============dealMaterielBox machineISAllException写卡机组为空");
				return true;
			}
			ArrayList<MachinesModel> machines = mgroup.getMachines();
			if(null != machines && machines.size() > 0){
				for(MachinesModel m : machines){
					if(!StringUtils.equals(m.getValue(), MACHINE_STATUS_4)){
						flag = false;
						break;
					}
				}
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox判断写卡机组下的写卡机是否都为异常失败." + group.toString() + "," + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取写卡机管理状态
	 * @return
	 */
	public static MachinesModel getMachineStatus(String station,String machineKey){
		MachinesModel mm = new MachinesModel();
		try{
			List<Map<String, String>> macs = getWriteCarMacSta(station,machineKey, null);
			if (macs == null || macs.size()<=0) {
				logger.info("log===============dealMaterielBox获取写卡机状态失败,缓存为空." + station + "," + machineKey);
			} else {
				mm.setWorkStation(macs.get(0).get("station_id"));
				mm.setKey(macs.get(0).get("read_id"));
				mm.setValue(macs.get(0).get("status"));
			}
			
			/*
			mm = (MachinesModel)cache.get(NAMESPACE, MACHINE_STATUS_KEY_PREFIX + station + machineKey);
			if(null == mm){
				logger.info("log===============dealMaterielBox获取写卡机状态失败,缓存为空." + station + "," + machineKey);
			}
			*/
		}catch(Exception e){
			logger.info("log===============dealMaterielBox获取写卡机状态失败." + e.getMessage());
			e.printStackTrace();
		}
		return mm;
	}
	
	/**
	 * 保存多写卡流水日志
	 */
	public static void saveWriteCardActiveLog(OrderActionLogReq logReq){
		try{
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(logReq.getOrder_id(), AttrConsts.ORDER_MODEL);
			//只保存自动化模式订单
			if(!StringUtils.equals(order_model, EcsOrderConsts.ORDER_MODEL_01)){
				logger.info("log===============dealMaterielBox订单["+logReq.getOrder_id()+"]不是自动化模式["+order_model+"].");
				return;
			}
			logReq.setSource_from(ManagerUtils.getSourceFrom());
			
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(logReq) {
				public ZteResponse execute(ZteRequest zteRequest) {
					IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
					OrderActionLogReq req = (OrderActionLogReq)zteRequest;
					support.execute("insert into es_order_action_log(action,action_desc,action_time,write_station_code,order_id,write_machine_code,batch_id,source_from) values(?,?,sysdate,?,?,?,to_char(sysdate,'yyyymmddHH24'),?)", 
							req.getAction(),req.getAction_desc(),req.getWrite_station_code(),req.getOrder_id(),req.getWrite_machine_code(),req.getSource_from());
					return null;
				}
			});
			ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存多写卡流水日志(与订单无关的操作,如初始化、回收卡)
	 */
	public static void saveActiveLog(OrderActionLogReq logReq){
		try{
			logReq.setSource_from(ManagerUtils.getSourceFrom());
			
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(logReq) {
				public ZteResponse execute(ZteRequest zteRequest) {
					IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
					OrderActionLogReq req = (OrderActionLogReq)zteRequest;
					support.execute("insert into es_order_action_log(action,action_desc,action_time,write_station_code,order_id,write_machine_code,batch_id,source_from) values(?,?,sysdate,?,?,?,to_char(sysdate,'yyyymmddHH24'),?)", 
							req.getAction(),req.getAction_desc(),req.getWrite_station_code(),req.getOrder_id(),req.getWrite_machine_code(),req.getSource_from());
					return null;
				}
			});
			ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置到达写卡机位的订单缓存
	 * @param workStation
	 */
	public static void setArriveWorkStationOrder(String workStation, String orderId){
		try{
			cache.set(NAMESPACE,ARRIVE_MACHINE_KEY_PREFIX + workStation,orderId,time);
		}catch(Exception e){
			logger.info("log===============设置临时记录缓存失败["+ARRIVE_MACHINE_KEY_PREFIX+workStation+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取到达写卡机位的订单缓存
	 * @param workStation
	 */
	public static String getArriveWorkStationOrder(String workStation){
		String value = null;
		try{
			value = (String)cache.get(NAMESPACE, ARRIVE_MACHINE_KEY_PREFIX + workStation);
		}catch(Exception e){
			logger.info("log===============获取到达写卡机位的订单缓存["+ARRIVE_MACHINE_KEY_PREFIX+workStation+"]," + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 移除到达写卡机位的订单缓存
	 * @param workStation
	 */
	public static void removeArriveWorkStationOrder(String workStation){
		try{
			cache.set(NAMESPACE,ARRIVE_MACHINE_KEY_PREFIX + workStation,"",time);
		}catch(Exception e){
			logger.info("log===============移除到达写卡机位的订单缓存["+ARRIVE_MACHINE_KEY_PREFIX+workStation+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 临时记录缓存，用于退卡方法判断
	 * @param workStation
	 */
	public static void setTempCache(String key, String orderId){
		try{
			cache.set(NAMESPACE,key + orderId,orderId,600);
		}catch(Exception e){
			logger.info("log===============设置临时记录缓存失败["+key+orderId+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 临时记录缓存，用于退卡方法判断
	 * @param workStation
	 */
	public static void setTempCache(String key, String orderId, String value){
		try{
			cache.set(NAMESPACE,key + orderId,value,600);
		}catch(Exception e){
			logger.info("log===============设置临时记录缓存失败["+key+orderId+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取临时记录缓存
	 * @param workStation
	 */
	public static String getTempCache(String key, String orderId){
		String value = null;
		try{
			value = (String)cache.get(NAMESPACE, key + orderId);
		}catch(Exception e){
			logger.info("log===============获取临时记录缓存失败["+key+orderId+"]," + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 移除临时记录缓存
	 * @param workStation
	 */
	public static void removeTempCache(String key, String orderId){
		try{
			cache.set(NAMESPACE,key + orderId,"",10);
		}catch(Exception e){
			logger.info("log===============移除临时记录缓存失败["+key+orderId+"]," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static Map mutexLocks = Collections.synchronizedMap(new HashMap());
    public static  Map getMutexLock(String lockName) {
    	HashMap lock = (HashMap) mutexLocks.get(lockName);
        if (lock == null) {
            lock = new HashMap();
            mutexLocks.put(lockName, lock);
        }
        return lock;
    }
    public static  void removeMutexLock(String lockName) {
        mutexLocks.remove(lockName);
    }

	public static void main(String[] args) {
//		cache.set(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + modifyMachine.getWorkStation() + modifyMachine.getKey(),modifyMachine,time);
//		MachinesModel m = (MachinesModel)cache.get(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + "CRMS04" + "CRMS04");
////		logger.info(m.toString());
//		MachinesModel mm = new MachinesModel();
//		mm.setKey("CRMS04");
//		mm.setValue("");
//		mm.setWorkStation("CRMS04");
//		cache.set(NAMESPACE,MACHINE_STATUS_KEY_PREFIX + mm.getWorkStation() + mm.getKey(),mm,time);
		
//		boolean flag = cache.add(NAMESPACE,OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC","2222222",20);
//		logger.info("ffffffff:" + flag);
//		String dd = (String)cache.get(NAMESPACE, OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC");
//		logger.info("ffffffff:" + dd);
//		flag = cache.add(NAMESPACE,OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC","333333333",20);
//		logger.info("ffffffff:" + flag);
//		logger.info("ffffffff:" + dd);
//		cache.delete(NAMESPACE, OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC");
//		dd = (String)cache.get(NAMESPACE, OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC");
//		logger.info("DDDDDDDDDDDDDDDDDDDDDDDDDDD" + dd);
//		flag = cache.add(NAMESPACE,OUTCARD_CACHE_LOCK_KEY_PREFIX + "DSC","333333333",20);
//		logger.info("eeeeeeeeeee:" + flag);
//		ArrayList<WriteCardMachines> workStations = new ArrayList<WriteCardMachines>();
//		WriteCardMachines w1 = new WriteCardMachines();
//		w1.setWorkStation("0001");
//		w1.setMachineNo("writecard001");
//		workStations.add(w1);
////		setWorkStation(workStations);
//		workStations = (ArrayList)cache.get(NAMESPACE, WRITE_CARD_MACHINES_KEY_PREFIX);
//		for(int i = 0 ; i < workStations.size(); i++){
//			logger.info(workStations.get(i));
//		}
		
//		ArrayList list = new ArrayList();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		logger.info(list);
//		list.remove(2);
//		logger.info(list);
	}
}
