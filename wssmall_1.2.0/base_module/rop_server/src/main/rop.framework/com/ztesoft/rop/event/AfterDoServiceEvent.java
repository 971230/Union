package com.ztesoft.rop.event;

import com.ztesoft.rop.common.RopRequestContext;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author 陈雄华
 * @version 1.0
 */
public class AfterDoServiceEvent extends RopEvent {

	private RopRequestContext ropRequestContext;

	public AfterDoServiceEvent(Object source,
			RopRequestContext ropRequestContext) {
		super(source, ropRequestContext.getRopContext());
		this.ropRequestContext = ropRequestContext;
	}

	public long getServiceBeginTime() {
		return ropRequestContext.getServiceBeginTime();
	}

	public long getServiceEndTime() {
		return ropRequestContext.getServiceEndTime();
	}

	public RopRequestContext getRopRequestContext() {
		return ropRequestContext;
	}
}
