package zte.net.ecsord.params.taobao.resp;

import params.ZteResponse;

import com.taobao.api.response.LogisticsDummySendResponse;
import com.taobao.api.response.LogisticsOfflineSendResponse;
import com.taobao.api.response.LogisticsOnlineSendResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class LogisticsTAOBAOResponse  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="is_success:业务返还码 true-成功；false-失败")
	private String is_success;

	@ZteSoftCommentAnnotationParam(name="发货标识",type="String",isNecessary="Y",desc="在线：EcsOrderConsts.TB_SHIPPING_MARK_ONLINE 虚拟：EcsOrderConsts.TB_SHIPPING_MARK_VIRTUAL")
	private String mark;
	
	@ZteSoftCommentAnnotationParam(name="虚拟商品下返回",type="String",isNecessary="Y",desc="虚拟商品下返回")
	private LogisticsDummySendResponse dumprespo;
	
	@ZteSoftCommentAnnotationParam(name="实物商品：货到付款",type="String",isNecessary="Y",desc="实物商品：货到付款")
	private LogisticsOnlineSendResponse logonresp;
	
	@ZteSoftCommentAnnotationParam(name="实物商品：非货到付款",type="String",isNecessary="Y",desc="实物商品：非货到付款")
	private LogisticsOfflineSendResponse logoffresp;
	
	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public LogisticsDummySendResponse getDumprespo() {
		return dumprespo;
	}

	public void setDumprespo(LogisticsDummySendResponse dumprespo) {
		this.dumprespo = dumprespo;
	}

	public LogisticsOnlineSendResponse getLogonresp() {
		return logonresp;
	}

	public void setLogonresp(LogisticsOnlineSendResponse logonresp) {
		this.logonresp = logonresp;
	}

	public LogisticsOfflineSendResponse getLogoffresp() {
		return logoffresp;
	}

	public void setLogoffresp(LogisticsOfflineSendResponse logoffresp) {
		this.logoffresp = logoffresp;
	}
	
	
}
