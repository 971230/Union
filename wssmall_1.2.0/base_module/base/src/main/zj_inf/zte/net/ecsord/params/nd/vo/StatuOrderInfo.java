package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class StatuOrderInfo implements Serializable {

	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1245171458403261729L;

	@ZteSoftCommentAnnotationParam(name="外部订单编号",type="String",isNecessary="Y",desc="origOrderId：外部订单编号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name="状态编码",type="String",isNecessary="Y",desc="status：状态编码  02：成功接收  03：拒绝接收  05： 用户拒收  06： 开户失败   07： 确认配送   99:  其它状态（状态值为99时，由物流公司对状态名进行命名，以作不同状态的区分，如：用户改期、配送失败等）")
	private String status;	
	
	@ZteSoftCommentAnnotationParam(name="状态通知应用编码",type="String",isNecessary="Y",desc="statusName：状态名称,对应状态编码的中文名称，如果状态编码是:99，则状态名称自定义名称")
	private String statusName;

	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="N",desc="desc : 描述")
	private String desc;	
	
	@ZteSoftCommentAnnotationParam(name="状态变更时间",type="String",isNecessary="Y",desc="updateTime : 状态变更时间 ,格式YYYY-MM-DD HH:mm:ss")
	private String updateTime;

	public String getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}	


	

	
	
}
