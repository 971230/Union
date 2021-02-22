<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

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
	<form class="validate" metdod="post" id="" validate="true">
		<div>
			<div style="margin-top: 5px;">
				<div>
					<span style="font-weight:bold;">短信模版详情</span>
				</div>
				<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
					<tbody>
						<tr>
							<th class="property">模版编号：</th>
							<td class="value"><input value="${smsDetail.sms_id}" readonly="readonly"></td>
							<th class="property">状态：</th>
							<td class="value"><input value="${smsDetail.status}" readonly="readonly"></td>
							<th class="property">模版类别：</th>
							<td class="value"><input value="${smsDetail.sms_level}" readonly="readonly"></td>
						</tr>
						<tr>
							<th class="property">创建人：</th>
							<td class="value"><input value="${smsDetail.creater_id}" readonly="readonly"></td>
							<th class="property">创建时间：</th>
							<td class="value"><input value= "${smsDetail.creater_time}" readonly="readonly" ></td>
						</tr>
						<%-- <tr>
							<th class="property">内容：</th>
							<td class="value" >
								<div title="${smsDetail.sms_template}">
									<input value="${smsDetail.sms_template}" readonly="readonly">
									<textarea rows="8" cols="50" title="${smsDetail.sms_template}" readonly="readonly">${smsDetail.sms_template}</textarea>
								</div>
							</td>
						</tr> --%>
					</tbody>
				</table>
					<table cellspacing="0" cellpadding="0" border="0" widtd="100%">
						<tbody>
						<tr>
								<th class="property">内容：</th>
								<td class="value" >
									<div title="${smsDetail.sms_template}">
										<textarea rows="8" cols="100" title="${smsDetail.sms_template}" readonly="readonly">${smsDetail.sms_template}</textarea>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div style="margin-top: 20px;">
					<span style="font-weight:bold;">操作记录</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="点击展开" onclick="load_log4()" class="graybtn1">
				</div>
				<table id="smsDetailsLogsTable" cellspacing="0" cellpadding="0" border="0" widtd="100%" style="display:none">
					<tbody>
						<tr>
							<td style="width: 10%;">处理动作</td>
							<td style="width: 15%;">操作人员</td>
							<td style="width: 15%;">创建时间</td>
							<td style="width: 30%;">修改前</td>
							<td style="width: 30%;">修改后</td>
						</tr>
						<c:forEach items="${smsLogs}" var="log">
							<tr>
								<td>${log.action}</td>
								<td>${log.op_id}</td>
								<td>${log.create_time}</td>
								<td>${log.sms_template}</td>
								<td>${log.sms_template_new}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<input id="sms_id" type="hidden" value="${sms_id}" />
		</div>
	</form>
</div>

<script>

function load_log4() {
	$("#smsDetailsLogsTable").toggle();
};

$(function() { 
	
});
</script>
