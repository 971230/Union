<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="infoPanel" style="">
<div class="infoContent" container="true"
	style="visibility: visible; opacity: 1;">
<div class="input">
<table class="form-table" style="width: 650px;">
		<tr>
			<th>外部单号：</th>
			<td valign="center" colspan="3">&nbsp;${eeiq.out_tid }</td> 
		</tr>
	    <tr>
			<th style="width: 120px;">异常单号：</th>
			<td valign="center" style="width: 230px;">&nbsp;${eeiq.excp_inst_id }</td>
			<th style="width: 120px;">关联实例类型：</th>
			<td valign="center" style="width: 230px;">
				&nbsp;
				<c:if test="${eeiq.rel_obj_type == 'order'}">订单</c:if>
				<c:if test="${eeiq.rel_obj_type == 'order_queue'}">订单队列</c:if>
			</td>
		</tr>
		<tr>
			<th>订单ID：</th>
			<td valign="center">&nbsp;${eeiq.rel_obj_id }</td>
			<th>日志报文ID：</th>
			<td valign="center">&nbsp;${eeiq.log_id }</td>
		</tr>
		<tr>
			<th>处理状态：</th>
			<td valign="center">
				&nbsp;
				<c:if test="${eeiq.record_status == '0'}">未处理</c:if>
				<c:if test="${eeiq.record_status == '1'}">已处理</c:if>
			</td>
			<th>订单所属地市：</th>
			<td valign="center">
				<p class="tit f_bold">&nbsp;${eeiq.order_city }</p>
			</td>
		</tr>
        <tr>
			<th>订单来源：</th>
			<td valign="center">
				&nbsp;${eeiq.order_from_name }
			</td>
			<th>订单生产模式：</th>
			<td valign="center">
				&nbsp;${eeiq.order_mode_name }
			</td>
		</tr>
		<tr>
			<th>订单创建时间：</th>
			<td valign="center">
				&nbsp;<html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${eeiq.rel_obj_create_time}"></html:dateformat>
			</td>
			<th>异常生成时间：</th>
			<td valign="center">
				&nbsp;<html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${eeiq.excp_create_time}"></html:dateformat>
			</td>
		</tr>
		<tr>
			<th>搜索编码：</th>
			<td valign="center">
				&nbsp;${eeiq.search_code }
			</td>
			<th>搜索名称：</th>
			<td valign="center">
				&nbsp;${eeiq.search_name }
			</td>
		</tr>
		<tr>
			<th>关键字名称：</th>
			<td valign="center">
				&nbsp;${eeiq.match_content }
			</td>
			<th>所属归类：</th>
			<td valign="center">
				&nbsp;${eeiq.catalog_name }
			</td>
		</tr>
		<tr>
			<th>处理人：</th>
			<td valign="center">
				&nbsp;${eeiq.deal_staff_realname }
			</td>
			<th>处理时间：</th>
			<td valign="center">
				&nbsp;<html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${eeiq.deal_time}"></html:dateformat>
			</td>
		</tr>
		<tr>
			<th>处理人电话：</th>
			<td valign="top" colspan="3">
				&nbsp;${eeiq.deal_staff_phoneno }
			</td>
		</tr>
		<tr>
			<th>处理结果：</th>
			<td colspan="3">
				<textarea rows="5" cols="75" readonly="readonly">${eeiq.deal_result }</textarea>
			</td>
		</tr>
</table>

</div>
</div>
</div>