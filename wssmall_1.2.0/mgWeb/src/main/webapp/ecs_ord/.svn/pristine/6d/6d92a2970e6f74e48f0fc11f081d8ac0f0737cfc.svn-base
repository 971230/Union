<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顺丰物流订单查询</title>
<style type="text/css">.red{color:red;}</style>
<script type="text/javascript">
	$(function(){
		$("#hide_params_tb").bind("click",function(){
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click",function(){
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		});
		$("#query_order_s").bind("click",function(){
			var order_id = $("#order_id").val();
			var out_id = $("#out_id").val();
			var phone_num = $("#phone_num").val();
			var delivery_id = $("#delivery_id").val();
			if((order_id ==null || order_id == "") && (out_id == null || out_id == "") && (phone_num == null || phone_num == "") && (delivery_id == null || delivery_id == "")){
				alert("请输入您要查询的编号");
				return;
			}
			$("#ord_prt_f").attr("action",ctx+"/shop/admin/ordAuto!getSFlogiOrder.do").submit();
		});
	});
	(function($){
		$.fn.aramsDiv = function(){
			var $this = $(this);
			$this.bind("mouseout",function(){
			});
			$(this).bind("mouseover",function(){
			});
		};
	})(jQuery);
</script>
</head>
<body>
<div class = "searchBx">
	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
			<form action="" method="post" id="ord_prt_f">
        
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
		<tbody>
			<tr>
			     <th>外部订单编号：</th>
				<td><input type="text" id="out_id" name="params.out_tid" value="${params.out_tid }" style="width:130px;" class="ipt_new"></td>				
 				<th>内部订单编号：</th>
				<td><input type="text" id="order_id" name="params.order_id" value="${params.order_id }" style="width:130px;" class="ipt_new"></td>							
				</tr>
			<tr>
				<th>开户号码：</th>
				<td><input type="text" id="phone_num" name="params.phone_num" value="${params.phone_num }" style="width:130px;" class="ipt_new"></td>		
                <th>物流单号：</th>
				<td><input type="text" id="delivery_id" name="params.delivery_id" value="${params.delivery_id }" style="width:130px;" class="ipt_new"></td>				
			</tr>
			<tr>
			<td>
			<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:20px;">查询</a>
			</td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="ordAuto!getSFlogiOrder.do" >
			<grid:header>
				<!-- 物流ID  物流类型   收货人姓名  收货人地址  收货人电话  收货人座机   下单时间  内部编号  外部编号  物流详情  -->				
				<grid:cell sort="sn" width="170px">客户订单号</grid:cell>
				<grid:cell sort="sn" width="170px">顺丰运单号</grid:cell>
				<grid:cell sort="sn" width="170px">外部订单号</grid:cell>
				<grid:cell sort="sn" width="170px">物流类型</grid:cell>
				<grid:cell sort="sn" width="170px">收货人姓名</grid:cell>
				<grid:cell sort="sn" width="170px">收货人地址</grid:cell>
				<grid:cell sort="sn" width="170px">收货人电话</grid:cell>	
				<grid:cell sort="sn" width="170px">收货人座机</grid:cell>
				<grid:cell sort="sn" width="170px">下单时间</grid:cell>	
									
			</grid:header>						
			<grid:body item="goods">				
				<grid:cell>&nbsp;${goods.orderid } </grid:cell>
				<grid:cell>&nbsp;${goods.mailno } </grid:cell>
				<grid:cell>&nbsp;${goods.out_tid} </grid:cell>
				<grid:cell>&nbsp;${goods.ship_type } </grid:cell>
				<grid:cell>&nbsp;${goods.ship_name } </grid:cell>
				<grid:cell>&nbsp;${goods.ship_addr } </grid:cell>	
				<grid:cell>&nbsp;${goods.ship_tel } </grid:cell>
				<grid:cell>&nbsp;${goods.ship_mobile } </grid:cell>
				<grid:cell>&nbsp;${goods.create_time } </grid:cell>
				
			</grid:body>
		</grid:grid>
	</form>
</div>
</body>
</html>