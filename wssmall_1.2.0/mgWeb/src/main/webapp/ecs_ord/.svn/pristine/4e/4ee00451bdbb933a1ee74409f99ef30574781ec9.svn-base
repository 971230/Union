<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口日志监控管理</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/toInfLogPage.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
</head>
<body>
	<form method="post" id="log_list_f">
	<div class="searchBx">
		<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
    	<tbody id="">
			<tr>
			<th>始结时间：</th>
			<td>
				<input type="text" name="startTime" value="${startTime }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
				<input type="text" name="endTime" value="${endTime }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			</td>
			<td>
				<a href="javascript:void(0)" id="qryBtn" class="blueBtns"><span>搜索</span></a>
			</td>
			<td>
				<a href="javascript:void(0)" id="dealBtn" class="blueBtns"><span>日志分析</span></a>
			</td>
			</tr>
		</tbody>
		</table>
	</div>
	</form>
	<div class="grid">
	<form action="" id="log_form_f">
		<grid:grid from="pageWeb" formId="log_list_f">
			<grid:header>
				<grid:cell>订单编号</grid:cell>
				<grid:cell>组件编码</grid:cell>
				<grid:cell>请求时间</grid:cell>
				<grid:cell>响应时间</grid:cell>
				<grid:cell>状态描述</grid:cell>
			</grid:header>
			<grid:body item="calllog">
				<grid:cell>${calllog.col3}</grid:cell>
				<grid:cell>${calllog.op_code}</grid:cell>
				<grid:cell>${calllog.req_time}</grid:cell>
				<grid:cell>${calllog.rsp_time}</grid:cell>
				<grid:cell>${calllog.state_msg}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	</div>
</body>
</html>