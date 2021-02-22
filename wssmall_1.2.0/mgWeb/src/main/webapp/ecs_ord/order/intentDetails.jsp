<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js"></script>
<style type="text/css">
	.property {
		text-align: right;
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
			<div style="margin-top: 5px;">
				<div style="">
					<span style="font-weight:bold;">意向单详情</span>
				</div>
				<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
					<tbody>
						<tr>
							<th class="property">意向单号：</th>
							<td class="value"><input value="${intentDetail.intent_order_id}" readonly="readonly"></td>
							<th class="property">订单号：</th>
							<td class="value"><input value="${intentDetail.order_id}" readonly="readonly"></td>
							<th class="property">业务号码：</th>
							<td class="value"><input value="${intentDetail.acc_nbr }" readonly="readonly"></td>
							<th class="property">订单来源：</th>
							<td class="value"><input value="${intentDetail.source_system_type}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">自传播营销id：</th>
							<td class="value"><input value="${intentDetail.market_user_id}" readonly="readonly"></td>
							<th class="property">种子用户id：</th>
							<td class="value"><input value="${intentDetail.seed_user_id}" readonly="readonly"></td>
							<th class="property">商品名称：</th>
							<td class="value"><input value="${intentDetail.goods_name}" readonly="readonly"></td>
							<th class="property">所属地市：</th>
							<td class="value"><input value="${intentDetail.order_city_code}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">营业县分：</th>
							<td class="value"><input value="${intentDetail.order_county_code}" readonly="readonly"></td>
							<th class="property">状态：</th>
							<td class="value"><input value="${intentDetail.status}" readonly="readonly"></td>
							<th class="property">联系人姓名：</th>
							<td class="value"><input value="${intentDetail.ship_name}" readonly="readonly"></td>
							<th class="property">联系人电话：</th>
							<td class="value"><input value="${intentDetail.ship_tel}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">联系人电话2：</th>
							<td class="value"><input value="${intentDetail.ship_tel2}" readonly="readonly"></td>
							<th class="property">联系人地址：</th>
							<td class="value"><input value="${intentDetail.ship_addr}" readonly="readonly"></td>
							<th class="property">锁单人工号：</th>
							<td class="value"><input value="${intentDetail.intent_lock_id}" readonly="readonly"></td>
							<th class="property">锁单人姓名：</th>
							<td class="value"><input value="${intentDetail.intent_lock_name}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">推荐人：</th>
							<td class="value"><input value="${intentDetail.referee_name}" readonly="readonly"></td>
							<th class="property">推荐人号码：</th>
							<td class="value"><input value="${intentDetail.referee_num}" readonly="readonly"></td>
							<th class="property">渠道类型：</th>
							<td class="value"><input value="${intentDetail.deal_office_type}" readonly="readonly"></td>
							<th class="property">渠道分类AOP：</th>
							<td class="value"><input value="${intentDetail.channeltype}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">下单渠道：</th>
							<td class="value"><input value="${intentDetail.deal_office_id}" readonly="readonly"></td>
							<th class="property">下单渠道名称：</th>
							<td class="value"><input value="${intentDetail.deal_office_name}" readonly="readonly"></td>
							<th class="property">下单人工号：</th>
							<td class="value"><input value="${intentDetail.deal_operator}" readonly="readonly"></td>
							<th class="property">下单人姓名：</th>
							<td class="value"><input value="${intentDetail.deal_operator_name}" readonly="readonly"></td>
							<%-- <th class="property">下单人电话：</th>
							<td class="value"><input value="${intentDetail.deal_operator_num}" readonly="readonly"></td> --%>
						</tr>
						<tr>
							<th class="property" >下单发展点编码：</th>
							<td class="value"><input value="${intentDetail.development_point_code}" readonly="readonly"></td>
							<th class="property">下单发展点名称：</th>
							<td class="value"><input value="${intentDetail.develop_point_name}" readonly="readonly"></td>
							<th class="property" >下单发展人编码：</th>
							<td class="value"><input value="${intentDetail.development_code}" readonly="readonly"></td>
							<th class="property">下单发展人姓名：</th>
							<td class="value"><input value="${intentDetail.development_name}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">开户点编码：</th>
							<td class="value"><input value="${intentDetail.accept_point}" readonly="readonly"></td>
							<th class="property">开户点名称：</th>
							<td class="value"><input value="${intentDetail.accept_name}" readonly="readonly"></td>
							<th class="property">开户人工号：</th>
							<td class="value"><input value="${intentDetail.accept_man_id}" readonly="readonly"></td>
							<th class="property">开户人姓名：</th>
							<td class="value"><input value="${intentDetail.accept_man_name}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">价格：</th>
							<td class="value"><input value="${intentDetail.real_offer_price}" readonly="readonly"></td>
							<th class="property">创建时间：</th>
							<td class="value"><input value="${intentDetail.create_time}" readonly="readonly"></td>
							<th class="property">结束时间：</th>
							<td class="value"><input value="${intentDetail.intent_finish_time}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">主卡号码：</th>
							<td class="value">
								<input value="${intentDetail.mainnumber}" readonly="readonly">
							</td>
							<th class="property">活动名称：</th>
							<td class="value">
								<input value="${intentDetail.activity_name}" readonly="readonly">
							</td>

							<th class="property">商品小类：</th>
							<td class="value">
								<input value="${intentDetail.name}" readonly="readonly">
							</td>

							<th class="property">备注：</th>
							<td class="value">
								<div class="icoFontlist" style="overflow： hidden; white-space： nowrap; text-overflow： ellipsis;" title="${intentDetail.remark}"><input value="${intentDetail.remark}" readonly="readonly"></div>
							</td>
						</tr>
						
						<tr>
							<th class="property">顶级种子专业线：</th>
							<td class="value">
								<input value="${intentDetail.top_seed_professional_line}" readonly="readonly">
							</td>
							<th class="property">顶级种子类型：</th>
							<td class="value">
								<input value="${intentDetail.top_seed_type}" readonly="readonly">
							</td>
							<th class="property">顶级种子分组：</th>
							<td class="value">
								<input value="${intentDetail.top_seed_group_id}" readonly="readonly">
							</td>

				
						</tr>
						 
					</tbody>
				</table>
				<div style="margin-top:5px">
					<span style="font-weight:bold;">客户信息</span>
				</div>
				<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
					<tbody>
						<tr>
							<th class="property">客户姓名：</th>
							<td class="value"><input value="${intentDetail.customer_name}" readonly="readonly"></td>
							<th class="property">证件类型：</th>
							<td class="value"><input value="${intentDetail.cert_type}" readonly="readonly"></td>
							<th class="property">证件号码：</th>
							<td class="value"><input value="${intentDetail.cert_num}" readonly="readonly"></td>
							<th class="property">证件地址：</th>
							<td class="value"><input value="${intentDetail.cert_addr}" readonly="readonly"></td>
						</tr>
					</tbody>
				</table>
				<div style="margin-top: 20px;">
				<span style="font-weight:bold;">操作记录</span>
				<div style="margin-left: 20px;">
					<span style="font-weight:bold;">操作记录</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="点击展开" onclick="load_log2()" class="graybtn1">
				</div>
				<table id='intentLogsTable' cellspacing="0" cellpadding="0" border="0" widtd="100%" style="display:none">
					<tbody>
						<tr>
							<td widtd="10%">处理动作</td>
							<td widtd="10%">操作人员</td>
							<td widtd="10%">创建时间</td>
							<td widtd="25%">备注</td>
							<td widtd="10%">工单处理人</td>
							<td widtd="10%">工单反馈时间</td>
							<td widtd="25%">工单反馈内容</td>
						</tr>
						<c:forEach items="${intentLogs}" var="log">
							<tr>
								<td>${log.action}</td>
								<td>${log.op_name}</td>
								<td>${log.create_time}</td>
								<td>${log.remark}</td>
								<td>
									${log.work_handle_name}${log.operator_num}
								</td>
								<td>${log.work_result_time}</td>
								<td>${log.work_result_remark}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				
				<div style="margin-left: 20px;">
					<span style="font-weight:bold;">短信发送记录</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="点击展开" onclick="load_log3()" class="graybtn1">
				</div>
				<table id='smsSendLogsTable' cellspacing="0" cellpadding="0" border="0" widtd="100%" style="display:none">
					<tbody>
						<tr>
							<td widtd="10%">发送人员</td>
							<td widtd="10%">发送时间</td>
							<td widtd="25%">发送类容</td>
							<td widtd="25%">接收号码</td>
						</tr>
						<c:forEach items="${smsSendLogs}" var="log">
							<tr>
								<td>${log.username}</td>
								<td>${log.create_time}</td>
								<td>${log.sms_data}</td>
								<td>${log.callee}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div style="margin-left: 20px;">
					<span style="font-weight:bold;">电话外呼记录</span>
					<div class="grid_n_cont">
						<div id="calllog_info_before" style="height: 80px;"></div>
					</div>
				</div>
				</div>
			</div>

			<input id="order_id" type="hidden" value="${order_id}" />
		</div>
	</form>
</div>

<div id="queryUserListDlg"></div>
<script type="text/javascript">
	jQuery('a.artZoom').artZoom();

	function load_calllog() {
		$("#log_table_show").toggle();
	};

	function load_log2() {
		$("#intentLogsTable").toggle();
	};
	function load_log3() {
		$("#smsSendLogsTable").toggle();
	};
</script>
<script>
	var detailFlag = false;
	$(function() {
		CommonLoad.loadJSP('calllog_info_before', '<%=request.getContextPath() %>/shop/admin/ordCall!queryCalllog.do', {
			order_id: "${order_id}",
			first_load: "yes",
			call_order_type: "intent",
			ajax: "yes",
			includePage: "calllog_info_before"
		}, false, null, true);

	});
</script>