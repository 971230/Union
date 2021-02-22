package zte.net.ecsord.params.writecard;

import java.io.Serializable;
import zte.net.ecsord.utils.DataUtil;

/**
 * 料箱队订单
 * @author duan.shaochu
 *
 */
public class MaterielBoxOrder implements Serializable {

	private static final long serialVersionUID = -6287718888725725533L;
	
	private String order_id;	//内部订单ID
	
	private String WorkStation;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getWorkStation() {
		return WorkStation;
	}

	public void setWorkStation(String workStation) {
		WorkStation = workStation;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DataUtil.toJson(this);
	}
	
}
