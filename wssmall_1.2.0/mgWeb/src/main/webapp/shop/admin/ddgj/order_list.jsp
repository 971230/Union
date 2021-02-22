<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form class="grid" id="gridform_order">
<grid:grid from="webpage" formId="gridform_order" ajax="yes" >
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell width="150px">订单号&nbsp;&nbsp;<span class="help_icon" helpid="order_showdetail"></span></grid:cell>
				<grid:cell >处理人</grid:cell>
				<grid:cell>订单总额</grid:cell>
				<%-- <grid:cell >申请区域</grid:cell> --%>
				<grid:cell >购买人</grid:cell>
				<grid:cell >收货人</grid:cell>  
				<grid:cell >订单状态</grid:cell> 
				<grid:cell >支付状态</grid:cell> 
				<grid:cell >发货状态</grid:cell>
				<grid:cell width="120px">下单日期</grid:cell> 
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell width="30px">
		  	    	<input type="radio" name="order_id" order_id="${order.order_id }" remark="${order.remark }"
		  	    		ship_name="${order.ship_name}" ship_addr="${order.shipping_area } ${order.ship_addr}"  order_status="${order.status }"
		  	    		pay_status="${order.payStatus}" ship_status="${order.shipStatus}" order_amount="${order.order_amount}" 
		  	    		ship_mobile="${order.ship_mobile }" />
		  	    </grid:cell>
		        <grid:cell>
		        	<c:if test="${order.status<3}"><B></c:if>${order.order_id}<c:if test="${order.status<3}"></B></c:if>
		        	<c:if test="${order.todo_user_id!=null && order.status!=-10}">
		        		<img order_id="${order.order_id }" name="locked_img_btn${monitor }" src="${ctx }/publics/images/icon_right_1.gif" />	
		        	</c:if>
		        	<c:if test="${order.todo_user_id==null && order.status!=-10}">
		        		<img order_id="${order.order_id }" name="unlock_img_btn${monitor }" src="${ctx }/publics/images/icon_right_2.gif" />	
		        	</c:if>
		        </grid:cell>
		        <grid:cell><span name="${order.order_id }">${order.todo_user_id}</span></grid:cell> 
		       <grid:cell>￥${order.order_amount}</grid:cell> 
		       <%-- <grid:cell>${order.lan_name}</grid:cell> --%>
		       <grid:cell>
			        ${order.uname}
		         </grid:cell>
		         <grid:cell>
			        ${order.ship_name}
		         </grid:cell>
		         <grid:cell><span style="color: red;" name="order_status_sp">
		         	<c:if test="${order.order_record_status==99}">异常单</c:if>
		         	<c:if test="${order.order_record_status!=99}">${order.orderStatus }</c:if>
		         </span></grid:cell> 
		         <grid:cell><span style="color: blue;">${order.payStatus}</span></grid:cell> 
		         <grid:cell><span style="color: blue;">${order.shipStatus}</span></grid:cell> 
		        <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${order.create_time}"></html:dateformat></grid:cell> 
		  </grid:body>
      </grid:grid>		
 </form>