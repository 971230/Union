package zte.params.store.resp;

import java.util.List;

import params.ZteResponse;

public class GoodsInventoryReduceResp extends ZteResponse{
	 private List<Object> wareHouseList;
     private String phy_house_id;
     private String log_house_id;
     private String Virt_house_id;
	public List<Object> getWareHouseList() {
		return wareHouseList;
	}
	public void setWareHouseList(List<Object> wareHouseList) {
		this.wareHouseList = wareHouseList;
	}
	public String getPhy_house_id() {
		return phy_house_id;
	}
	public void setPhy_house_id(String phy_house_id) {
		this.phy_house_id = phy_house_id;
	}
	public String getLog_house_id() {
		return log_house_id;
	}
	public void setLog_house_id(String log_house_id) {
		this.log_house_id = log_house_id;
	}
	public String getVirt_house_id() {
		return Virt_house_id;
	}
	public void setVirt_house_id(String virt_house_id) {
		Virt_house_id = virt_house_id;
	}
     
}
