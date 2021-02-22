package zte.net.ecsord.params.writecard;

import java.io.Serializable;
import java.util.ArrayList;

import zte.net.ecsord.utils.DataUtil;

/**
 * 写卡机组
 * @author duan.shaochu
 *
 */
public class MachinesGroup implements Serializable {

	private static final long serialVersionUID = -6287798088111725533L;

	private ArrayList<MachinesModel> machines = new ArrayList<MachinesModel>();
	
	private String WorkStation;

	public ArrayList<MachinesModel> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<MachinesModel> machines) {
		this.machines = machines;
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
