<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/shop/admin/js/Order.js"></script>
<form method="post" id="serchform" action='ordern!listAfterService.do'>
	<jsp:include page="order_select.jsp" />
</form>
<div class="grid" style="height: 450px;overflow-Y: auto;">
	<form method="POST">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="sn" width="150px">订单号</grid:cell>
				<grid:cell>服务名</grid:cell>
				<grid:cell>服务地址</grid:cell>
				<grid:cell>申请人</grid:cell>
				<grid:cell>联系方式</grid:cell>
				<grid:cell>预约时间</grid:cell>
				<grid:cell width="120px">下单日期</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>
			<grid:body item="order">
				<grid:cell>
					<input type="checkbox" name="id" value="${order.order_id }"
						style='display:none;' />
					<B> ${order.order_id} </B>
				</grid:cell>
				<grid:cell><a href="goods!view.do?goods_id=${order.goods_id}">${order.col6}</a></grid:cell>
				<grid:cell>${order.shipping_area}<br />${order.ship_addr}</grid:cell>
				<grid:cell>${order.ship_name}</grid:cell>
				<grid:cell>${order.ship_mobile}</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd"
						d_time="${order.ship_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd HH:mm:ss"
						d_time="${order.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>未处理</grid:cell>
				<grid:cell>
					<a href="#">处理</a>
				</grid:cell>

			</grid:body>
		</grid:grid>
	</form>
	<div style="clear:both;padding-top:5px;"></div>
</div>
