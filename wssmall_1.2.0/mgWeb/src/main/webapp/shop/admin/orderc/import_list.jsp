<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
	<form method="POST" id="orderOutForm">
		<grid:grid  from="webpage">
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell sort="sn" width="110px">订单编号</grid:cell>
				<grid:cell width="60px">订单金额</grid:cell>
				<grid:cell width="60px">商品名称</grid:cell>
				<grid:cell width="60px">收货人姓名</grid:cell>
				<grid:cell width="60px">收货人联系电话</grid:cell>
				<grid:cell width="60px">收货人地址</grid:cell>
				<grid:cell width="60px">下单时间</grid:cell>
				<grid:cell width="60px">入网信息</grid:cell>
				<grid:cell width="60px">描述信息</grid:cell>
			</grid:header>
		  	<grid:body item="orderOuter">
		  		<grid:cell><input type="radio" name="orderouter_checkbox" value="${orderOuter.order_id}" /></grid:cell>
		        <grid:cell>${orderOuter.order_id}</grid:cell>
		        <grid:cell>${orderOuter.order_amount}</grid:cell>
		        <grid:cell>${orderOuter.goods_name}</grid:cell>
		        <grid:cell>${orderOuter.ship_name}</grid:cell>
		        <grid:cell>${orderOuter.ship_mobile}</grid:cell>
		        <grid:cell>${orderOuter.ship_addr}</grid:cell>
		        <grid:cell>${orderOuter.create_time}</grid:cell>
		        <grid:cell>${orderOuter.cust_name},${orderOuter.acc_nbr},${orderOuter.offer_name}</grid:cell>
		        <grid:cell>${orderOuter.comments}</grid:cell>
		  	</grid:body>
		</grid:grid>  
	</form>
</div>