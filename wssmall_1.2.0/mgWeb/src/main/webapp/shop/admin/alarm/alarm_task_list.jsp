<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/alarm_task.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form action="alarm!list.do" method="post" id="alarmTaskForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							告警任务名称:
						</th>
						<td>
							<input type="hidden" name="alarm_task_type" value="${alarm_task_type }"/>
							<input type="text" class="ipttxt" 
								name="alarm_task_name" value="${alarm_task_name }" class="searchipt" />
						</td>
						<th>
							告警联系人:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="recevier_username" value="${recevier_username }" class="searchipt" />
						</td>
						<th>
							告警联系号码:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="recevier_phone_num" value="${recevier_phone_num }" class="searchipt" />
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div class="comBtnDiv">
			<a href="javascript:;" id="delAlarmTaskBtn"
				style="margin-right: 10px;" class="graybtn1"><span>删除</span>
			</a>
			<a href="javascript:;" id="addAlarmTaskBtn"
				style="margin-right: 10px;" class="graybtn1"><span>添加</span>
			</a>
		</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>告警任务名称</grid:cell>
				<grid:cell>告警联系人</grid:cell>
				<grid:cell>告警联系号码</grid:cell>
				<grid:cell>任务发布人</grid:cell>
				<grid:cell>任务生效时间</grid:cell>
				<grid:cell>任务失效时间</grid:cell>
				<grid:cell>任务发布时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" value="${obj.alarm_task_id}" alarm_task_id="${obj.alarm_task_id }"/>
				</grid:cell>
				<grid:cell>
					<a title="查看" href="alarm!detail.do?flag=view&alarm_task_id=${obj.alarm_task_id }&alarm_task_type=${alarm_task_type }">${obj.alarm_task_name }</a>
				</grid:cell>
				<grid:cell>${obj.recevier_username}</grid:cell>
				<grid:cell>${obj.recevier_phone_num}</grid:cell>
				<grid:cell>${obj.username}</grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.task_create_time}"></html:dateformat></grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.task_aead_time}"></html:dateformat></grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.release_time}"></html:dateformat></grid:cell>
				<grid:cell>
					<a title="编辑"href="alarm!detail.do?flag=edit&alarm_task_id=${obj.alarm_task_id }&alarm_task_type=${alarm_task_type }">修改 </a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="addAlarmTask"></div>