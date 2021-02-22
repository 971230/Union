<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form class="validate" method="post" action="" validate="true">
		<div class="">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							日志编号：
						</th>
						<td>
							<input type="text" size="18" readonly class="ipttxt"
								id="template_name" name="template_name"
								value="${alarmLog.alarm_log_id  }" class="searchipt" />
						</td>
					</tr>
					<tr>
						<th>
							模板名称：
						</th>
						<td>
							<input type="text" size="18" readonly class="ipttxt"
								id="template_name" name="template_name"
								value="${alarmLog.template_name }" class="searchipt" />
						</td>
					</tr>
					<tr>
						<th>
							日志内容：
						</th>
						<td>
							<textarea vtype="textarea" required="true" rows="6" cols="60"
									id="content" name="content" type="textarea">${alarmLog.content }</textarea>
						</td>
					</tr>
				<tbody>
			</table>
		</div>
		<div class="clear"></div>
	</form>
</div>

<script type="text/javascript"> 
$(function (){
     $("input").attr("class","noborder");
     $("#up").attr("style","display:none;");
})
</script>