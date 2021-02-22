<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderTreeBusiRequest"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest"%>
<%@page import="java.util.List" %>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String order_id = (String)request.getAttribute("order_id");
	OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests = orderTree.getOrderPhoneInfoFukaBusiRequests();
%>
<script src="<%=request.getContextPath()%>/ecs_ord/js/order_fuka.js"></script>
<%-- <form  method="post"  id="phoneFukaInfoForm">
	<input type="hidden" id="order_id" value="${order_id }" />
<div class="grid_n_cont_sub">
    <h3>副卡号码信息：</h3>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form" name="fukaxinxi">
		<% if (orderPhoneInfoFukaBusiRequests != null && orderPhoneInfoFukaBusiRequests.size() > 0) {
				String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				for (int i = 0; i < orderPhoneInfoFukaBusiRequests.size(); i ++) {
					OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest = orderPhoneInfoFukaBusiRequests.get(i);
		%>
				<tr>
					<th>副卡号码：</th>
					<%if (flow_trace_id!=null&&flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_B)) { %>
						<td name="dianhuahaoma">
							<input type="text" value="<%=orderPhoneInfoFukaBusiRequest.getPhoneNum() %>" name="fuka_phonenum" id="fuka_phone_num"/>
						</td>
					<%} else {%>
						<td name="dianhuahaoma"><%=(orderPhoneInfoFukaBusiRequest.getPhoneNum()!=null?orderPhoneInfoFukaBusiRequest.getPhoneNum():"") %></td>
					<%} %>
	                <th>操作状态：</th>
	                <td>
			  			<span style="color:black" id="spanOperState">
			  				<c:forEach items="${operatorStateList }" var="pm">
			  					<c:if test="${pm.pkey == orderPhoneInfoFukaBusiRequest.operatorState}">${pm.pname }</c:if>
							</c:forEach>
			  			</span>                 
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>套餐名称：</th>
					<td name="taocanmingcheng"><%=(orderPhoneInfoFukaBusiRequest.getProductName()!=null?orderPhoneInfoFukaBusiRequest.getProductName():"") %></td>
					<th>合约名称：</th>
					<td name="heyuemingcheng"><%=(orderPhoneInfoFukaBusiRequest.getActivityName()!=null?orderPhoneInfoFukaBusiRequest.getActivityName():"") %></td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>入网类型1：</th>
					<td name="ruwangleixing">
						<html:orderattr attr_code="DC_CUSTOMER_USER_TYPE" order_id="${order_id}"  field_name="userType" field_value="${orderPhoneInfoFukaBusiRequest.userType}"  field_desc ="入网类型" field_type="select" disabled="disabled" ></html:orderattr>
					</td>
					<td name="kaleixing">
						<html:orderattr attr_code="DC_PRODUCT_CARD_TYPE" order_id="${order_id}"  field_name="cardType" field_value="${orderPhoneInfoFukaBusiRequest.cardType}"  field_desc ="卡类型" field_type="select" disabled="disabled" ></html:orderattr>
					</td>
					<th></th>
					<td></td>
				</tr>
		<%
				}
			} 
		%>
	</table>
</div>
</form>
<div id="selFukaPhoneDlg"></div>
<script type="text/javascript">
	$(function() {
		//FuKaInfo.init();
	});
	
	$(function(){
		 Eop.Dialog.init({id:"selFukaPhoneDlg",modal:true,title:"选择号码",width:'800px'});
		 selPhone();
	});
	function selPhone(){
		 $("#changePhoneFukaBtn").unbind("click").click(function(){	
		    var old_phone_num = $(this).parent("td").find("input[name='fuka_phonenum']").val();
		    var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumFukaList.do?ajax=yes&isQuery=false&order_id=${order_id}&old_phone_num="+old_phone_num;
		    $("#selFukaPhoneDlg").load(url,{},function(){});
			Eop.Dialog.open("selFukaPhoneDlg");
		 });
	}
</script> --%>