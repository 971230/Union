package zte.net.ecsord.params.sf.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RouteResponse implements Serializable {

	private static final long serialVersionUID = 7287651453992589189L;
	
	@ZteSoftCommentAnnotationParam(name="运单号",type="String",isNecessary="Y",desc="mailno：运单号")
	private String mailno;
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="N",desc="orderid：订单号")
	private String orderid;
	@ZteSoftCommentAnnotationParam(name="路由明细",type="String",isNecessary="Y",desc="routeList：路由明细")
	private List<Route> routeList;
	
	public String getMailno() {
		return mailno;
	}
	public void setMailno(String mailno) {
		this.mailno = mailno;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public List<Route> getRouteList() {
		return routeList;
	}
	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}
	
	
}
