package zte.net.ecsord.params.writecard;

import java.io.Serializable;

import zte.net.ecsord.utils.DataUtil;

/**
 * 写卡机
 * @author duan.shaochu
 *
 */
public class MachinesModel implements Serializable {
	
	private static final long serialVersionUID = -6287766222225725533L;

	private String key;	//写卡机编码
	
	private String value;	//写卡机状态 1-正常、2-写卡中、3-空闲、4-异常、订单号（订单号正在写卡）、空（管理状态空闲）
	
	private String WorkStation;

	public String getWorkStation() {
		return WorkStation;
	}

	public void setWorkStation(String workStation) {
		WorkStation = workStation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DataUtil.toJson(this);
	}
	
}
