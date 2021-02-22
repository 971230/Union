package zte.net.card.params.resp.vo;

import java.io.Serializable;

public class MachineGroupStateVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupNo;// 写卡机组编号
	private int allowCount;// 允许写入队列的数量

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public int getAllowCount() {
		return allowCount;
	}

	public void setAllowCount(int allowCount) {
		this.allowCount = allowCount;
	}

}
