<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/alarm_log.js"></script>
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
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>日志编号</grid:cell>
				<grid:cell>告警时间</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<a title="查看" href="javascript:void(0);" name="showAlarmLogDialogs" alarm_log_id="${obj.alarm_log_id}">${obj.alarm_log_id }</a>
				</grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.alarm_time }"></html:dateformat></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="showAlarmLogDalog"></div>