package zte.net.ecsord.params.writecard;

import java.io.Serializable;
import java.util.ArrayList;

import zte.net.ecsord.utils.DataUtil;

/**
 * 料箱队列
 * @author duan.shaochu
 *
 */
public class MaterielBox implements Serializable {

	private static final long serialVersionUID = -6287798333325725533L;

	private String WorkStation;	//写卡工位,一个写卡工位多台写卡机
	private ArrayList<MaterielBoxOrder> orders = new ArrayList<MaterielBoxOrder>();	//料箱订单列表
	
	public String getWorkStation() {
		return WorkStation;
	}
	
	public void setWorkStation(String workStation) {
		WorkStation = workStation;
	}

	public ArrayList<MaterielBoxOrder> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<MaterielBoxOrder> orders) {
		this.orders = orders;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DataUtil.toJson(this);
	}
	
	
}
