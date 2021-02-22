package com.ztesoft.net.mall.cmp;

import java.util.Map;

import com.ztesoft.net.mall.cmp.CompBillResult;
/**
 * 流量卡充值卡对账，暂时先废弃，代码可供参考
 * @author wui
 *
 */
@Deprecated
public interface ICompCharge {
	
	public void compCharge(Map map,CompBillResult compBillResult);
}
