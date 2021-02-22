package com.ztesoft.net.mall.core.action.gray;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.HostEnvVo;
import com.ztesoft.net.mall.core.service.IGrayDataSyncManager;

public class GrayDataSyncAction extends WWAction{
	private String data_type;
	private String from_db_type;
	private String to_db_type;
	private IGrayDataSyncManager grayDataSyncManagerImpl;
	private HostEnvVo hostEnvVo;
	private String order_id;
	private List<Map> hostEnvList;
	public String toGrayDataSync(){
		hostEnvList = this.grayDataSyncManagerImpl.getDataSyncHostEnv();
		return "toGrayDataSync";
	}
	
	public String doGrayDataSync(){
		try{
			String result =grayDataSyncManagerImpl.doGrayDataSync(data_type, from_db_type, to_db_type);
			this.json = "{result:0,message:'数据同步完成！"+result+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	public String toHostEnvList(){
		this.webpage =  this.grayDataSyncManagerImpl.getHostEnvList(hostEnvVo, this.getPageSize(), this.getPage());
		return "toHostEnvList";
	}
	public String toRefreshOrderEnv(){
		return "toRefreshOrderEnv";
	}
	public String doRefreshOrderEnv(){
		try{
			String result =BeanUtils.doRefreshOrderEnv(order_id);
			if(!"".equals(result)){
				result+="订单刷新失败";
			}
			this.json = "{result:0,message:'数据同步完成！"+result+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	public String refreshGrayConfig(){
		try{
			//刷新主机
			this.grayDataSyncManagerImpl.refreshHostEnv();
			//刷新规则配置表es_data_source_config
			this.grayDataSyncManagerImpl.refrenshDataSourceConfig();
			this.json = "{result:0,msg:'主机环境刷新完成！'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,msg:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getFrom_db_type() {
		return from_db_type;
	}

	public void setFrom_db_type(String from_db_type) {
		this.from_db_type = from_db_type;
	}

	public String getTo_db_type() {
		return to_db_type;
	}

	public void setTo_db_type(String to_db_type) {
		this.to_db_type = to_db_type;
	}

	public IGrayDataSyncManager getGrayDataSyncManagerImpl() {
		return grayDataSyncManagerImpl;
	}

	public void setGrayDataSyncManagerImpl(
			IGrayDataSyncManager grayDataSyncManagerImpl) {
		this.grayDataSyncManagerImpl = grayDataSyncManagerImpl;
	}

	public HostEnvVo getHostEnvVo() {
		return hostEnvVo;
	}

	public void setHostEnvVo(HostEnvVo hostEnvVo) {
		this.hostEnvVo = hostEnvVo;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public List<Map> getHostEnvList() {
		return hostEnvList;
	}

	public void setHostEnvList(List<Map> hostEnvList) {
		this.hostEnvList = hostEnvList;
	}


	
	
}
