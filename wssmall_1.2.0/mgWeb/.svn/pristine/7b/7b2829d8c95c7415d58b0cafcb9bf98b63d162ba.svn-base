<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Activity.js"></script>
<form method="post" id='activity_list_form'
	action='activity!list.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>活动名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="activity.name"
						value="${activity.name}" class="searchipt" /></td>
					<th>所属商家:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="activity.real_name"
						value="${activity.real_name}" class="searchipt" /></td>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="activity.begin_time"
						value="${activity.begin_time}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="activity.end_time"
						value="${activity.end_time}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="activity!add.do" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" class="graybtn1"
			id="delBtn"><span>删除</span></a>
		<div style="clear:both"></div>
	</div>
</form>
<div class="grid">
	<form method="POST" id="activity_list_form">
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="name" width="250px">活动名称</grid:cell>
				<grid:cell sort="enable">是否开启</grid:cell>
				<grid:cell sort="begin_time">起始时间</grid:cell>
				<grid:cell sort="end_time">终止时间</grid:cell>
				<grid:cell sort="realname">所属商家</grid:cell>
				<grid:cell width="200px">操作</grid:cell>
			</grid:header>

			<grid:body item="activity">
				<grid:cell>
					<input type="checkbox" name="id" value="${activity.id }" />
					<!--  ${activity.id }-->
				</grid:cell>
				<grid:cell>${activity.name } </grid:cell>
				<grid:cell>
					<c:if test="${activity.enable == 0}">否</c:if>
					<c:if test="${activity.enable == 1}">是</c:if>
				</grid:cell>
				<grid:cell>${fn:substring(activity.begin_time  , 0 , 10)}</grid:cell>
				<grid:cell>${fn:substring(activity.end_time  , 0 , 10)}</grid:cell>
				<grid:cell>${activity.realname}</grid:cell>
				<grid:cell>
					<a href="activity!edit.do?activity_id=${activity.id}">修改活动</a>
					<span class='tdsper'></span>
					<a href="promotion!list.do?activityid=${activity.id }">规则管理</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>

<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>
