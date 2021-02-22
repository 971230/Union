<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志查询</title>
<script src="<%=request.getContextPath()%>/shop/admin/dictlogs/js/qryDataPraserInst.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>
	<form action="" method="post" id="dict_list_f">
	<div class="searchBx">
		<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
    	<tbody id="tbody_A">
			<tr>
			<th>订单编号：</th>
			<td>
				<input type="text" name="qryMatchDictLogsReq.obj_id" value="${qryMatchDictLogsReq.obj_id }" class="ipt_new" />
			</td>
			<th>日志类型：</th>
			<td>
				<select id="dict_type" name='qryMatchDictLogsReq.dict_type' value="${qryMatchDictLogsReq.dict_type }" style="width:138px;" class="ipt_new">
					
				</select>
			</td>
			<th>日志内容：</th>
			<td>
				<input type="text" name="qryMatchDictLogsReq.dict_desc" value="${qryMatchDictLogsReq.dict_desc }" style="width:138px;" class="ipt_new">
			</td>
			<th>始结时间：</th>
			<td>
				<input type="text" name="qryMatchDictLogsReq.begin_time" value="${qryMatchDictLogsReq.begin_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
				<input type="text" name="qryMatchDictLogsReq.end_time" value="${qryMatchDictLogsReq.end_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			</td>
			<td>
				<a href="javascript:void(0)" id="qryBtn" class="blueBtns"><span>搜索</span></a>
			</td>
			</tr>
		</tbody>
		</table>
	</div>
	</form>
	<div class="grid">
	<form action="" id="dict_form_f">
		<grid:grid from="webpage" formId="dict_list_f">
			<grid:header>
				<grid:cell>外部订单编号</grid:cell>
				<grid:cell>错误名称</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>修改时间</grid:cell>
				<grid:cell>处理建议</grid:cell>
				<grid:cell>处理状态</grid:cell>
				<grid:cell>处理内容</grid:cell>
			</grid:header>
			<grid:body item="dataPraserInst">
				<grid:cell>
					<a inst_id="${dataPraserInst.log_id }" name="out_id" href="javascript:void(0);">${dataPraserInst.obj_id}</a>
				</grid:cell>
				<grid:cell>${dataPraserInst.dict_name}</grid:cell>
				<grid:cell>${dataPraserInst.create_time}</grid:cell>
				<grid:cell>${dataPraserInst.update_time}</grid:cell>
				<grid:cell>
					<c:if test="${dataPraserInst.deal_opinion != '' }">
						${fn:substring(dataPraserInst.deal_opinion, 0, 12)}...
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:choose>
						<c:when test="${dataPraserInst.deal_state == '0'}"><a inst_id="${dataPraserInst.log_id }" name="no_handle" href="javascript:void(0);">未处理</a></c:when>
						<c:when test="${dataPraserInst.deal_state == '1'}">已处理</c:when>
					</c:choose>
				</grid:cell>
				<grid:cell>${dataPraserInst.deal_content}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	</div>
	<div id="deal_content"></div>
	<div id="detail_content"></div>
</body>
</html>