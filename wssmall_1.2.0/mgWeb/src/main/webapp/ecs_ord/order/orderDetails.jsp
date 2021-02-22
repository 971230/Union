<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style type="text/css">
	.property {
		text-align: right;
		width: 80px;
	}
	
	.value {
		/* color:#999; */
	}
	
	.value input {
		border: 0px;
		color: #999;
	}
	
	.icoFontlist {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.icoFontlist:hover {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		overflow: scroll;
		text-overflow: ellipsis;
		white-space: nowrap;
		cursor: pointer;
	}
	
	.detailDiv {
		display: none;
	}
</style>
<div class="input">
	<form class="validate" metdod="post" id="intentRemarkform" validate="true">
		<div>
			<div style="margin-top: 2px;">
				<div style="">
					<span style="font-weight:bold;">订单详情</span>
				</div>
				<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
					<tbody>
						<tr>
							<td class="property">订单号：</td>
							<td class="value"><input value="${orderDetail.order_id}" readonly="readonly"></td>
							<td class="property">外部单号：</td>
							<td class="value"><input value="${orderDetail.out_tid}" readonly="readonly"></td>
							<td class="property">订单来源：</td>
							<td class="value"><input value="${orderDetail.order_from}" readonly="readonly"></td>
							<td class="property">订单类型：</td>
							<td class="value"><input value="${orderDetail.order_type}" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="property">归属地市：</td>
							<td class="value"><input value="${orderDetail.order_city_code}" readonly="readonly"></td>
							<td class="property">归属县分：</td>
							<td class="value"><input value="${orderDetail.district_code}" readonly="readonly"></td>
							<td class="property">渠道标记：</td>
							<td class="value"><input value="${orderDetail.channel_mark}" readonly="readonly"></td>
							<td class="property">下单时间：</td>
							<td class="value"><input value="${orderDetail.tid_time}" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="property">发展人编码：</td>
							<td class="value"><input value="${orderDetail.development_code}" readonly="readonly"></td>
							<td class="property">发展人名称：</td>
							<td class="value"><input value="${orderDetail.development_name}" readonly="readonly"></td>
							<td class="property">订单总价（元）：</td>
							<td class="value"><input value="${orderDetail.order_amount}" readonly="readonly"></td>
							<td class="property">实收金额（元）：</td>
							<td class="value"><input value="${orderDetail.paymoney}" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="property">支付类型：</td>
							<td class="value"><input value="${orderDetail.pay_type}" readonly="readonly"></td>
							<td class="property">支付方式：</td>
							<td class="value"><input value="${orderDetail.pay_method}" readonly="readonly"></td>
							<td class="property">支付状态：</td>
							<td class="value"><input value="${orderDetail.pay_status}" readonly="readonly"></td>
							<td class="property">配送时间：</td>
							<td class="value"><input value="${orderDetail.shipping_time}" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="property">收货人姓名：</td>
							<td class="value"><input value="${orderDetail.ship_name}" readonly="readonly"></td>
							<td class="property">收货省份：</td>
							<td class="value"><input value="${orderDetail.province_id}" readonly="readonly"></td>
							<td class="property">收货地市：</td>
							<td class="value"><input value="${orderDetail.city_id}" readonly="readonly"></td>
							<td class="property">收货县分：</td>
							<td class="value">
								<c:if test="${orderDetail.region_id==''}">
									<input value="${orderDetail.district_code}" readonly="readonly">
								</c:if>
								<input value="${orderDetail.region_id}" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td class="property">客户姓名：</td>
							<td class="value"><input value="${orderDetail.buyer_name}" readonly="readonly"></td>
							<td class="property">客户联系号码：</td>
							<td class="value"><input value="${orderDetail.ship_tel}" readonly="readonly"></td>
							<td class="property">客户联系地址：</td>
							<td class="value">
								<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${orderDetail.ship_addr}">
									<input value="${orderDetail.ship_addr}" readonly="readonly">
								</div>
							</td>
							<td class="property">标准地址：</td>
							<td class="value">
								<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${orderDetail.adsl_addr_desc}">
									<input value="${orderDetail.adsl_addr_desc}" readonly="readonly">
								</div>
							</td>
						</tr>
						<tr>
							<td class="property">业务号码：</td>
							<td class="value"><input value="${orderDetail.phone_num}" readonly="readonly"></td>
						</tr>
						<%-- <tr>
							<td class="property">发展人联系电话：</td>
							<td class="value"><input value="${orderDetail.develop_phone}" readonly="readonly"></td>
							<td class="property">备注：</td>
							<td class="value">
								<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${orderDetail.remark}"><input value="${orderDetail.remark}" readonly="readonly"></div>
							</td>

						</tr> --%>
					</tbody>
				</table>
				 <div style="margin-top: 20px;">
					<span style="font-weight:bold;">操作记录</span>
				</div>
				<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
					<tbody>
						<tr>
							<td widtd="20%">环节</td>
							<td widtd="20%">处理方式</td>
							<td widtd="40%">处理意见</td>
							<td widtd="20%">处理时间</td>
							<td widtd="20%">处理人</td>
						</tr>
						<c:forEach items="${orderLogs}" var="log">
							<tr>
								<td>${log.trace_name}</td>
								<td>${log.type_name}</td>
								<td>${log.comments}</td>
								<td>${log.create_time}</td>
								<td>${log.op_name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<input id="order_id" type="hidden" value="${order_id}" />
		</div>
	</form>
</div>

<div id="queryUserListDlg"></div>
<script>
	var detailFlag = false;
	$(function() {

		$("a[name=inner_order_id]").bind("click", function() {
			if($(".orderDetails").css("display") == 'none') {
				if(!detailFlag) {
					var order_id = $("#order_id").val();
					var url = ctx + "/shop/admin/orderFlowAction!intentOrderDetails.do?ajax=yes&order_id=" + order_id;
					$("#intentOrderDetails").load(url, {}, function() {});
					detailFlag = true;
				}
				$(".orderDetails").show();
			} else {
				$(".orderDetails").hide();
			}
		});

	});
</script>