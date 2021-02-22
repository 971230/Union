package zte.net.ecsord.params.writecard;

import java.io.Serializable;
import java.util.ArrayList;

import zte.net.ecsord.utils.DataUtil;

/**
 * 写卡工位
 * @author duan.shaochu
 *
 */
public class WriteCardWorkStation implements Serializable{

	private static final long serialVersionUID = -6287799999025724233L;
	private ArrayList<String> workStations = new ArrayList<String>();	////写卡工位列表,一个写卡工位多台写卡机
	
	public ArrayList<String> getWorkStations() {
		return workStations;
	}

	public void setWorkStations(ArrayList<String> workStations) {
		this.workStations = workStations;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DataUtil.toJson(this);
	}
}
