package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WriMachStaQueBody implements Serializable {

	@ZteSoftCommentAnnotationParam(name="detail",type="String",isNecessary="Y",desc="detail：描述")
	private List<Station> station;

	public List<Station> getStation() {
		return station;
	}

	public void setStation(List<Station> station) {
		this.station = station;
	}
	
	
}
