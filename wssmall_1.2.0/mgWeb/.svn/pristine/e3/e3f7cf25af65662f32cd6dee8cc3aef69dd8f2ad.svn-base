<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/alarm.js"></script>
<script type="text/javascript" src="alarm/js/showTaskDalog.js"></script>
<script type="text/javascript" src="alarm/js/showPhoneDalog.js"></script>
<script type="text/javascript" src="alarm/js/showPartnerDalog.js"></script>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<div class="input">
	<form id="task_alarm_form" action="" method="post" class="validate">
		<div class="tableform">
			<h4>
				任务告警设置
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%">
					<tbody>
						<tr>
							<th>
								任务告警名称：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="alarm_task_name" name="alarm_task_name"
									value="${alarmTask.alarm_task_name }" class="searchipt" readonly required="true"/>
							</td>
						</tr>
						<tr>
							<th>
								任务名称：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="task_name" name="task_name"
									value="${task_name}" class="searchipt" required="true" readonly required="true"/>
								<input type="hidden"  id="owner_task_id" name="owner_task_id" />
								<input type="button" id="task_name_btn" class="graybtn1"
									value="选择" />
							</td>
						</tr>
						<!-- 
						<tr>
							<th>
								任务条件设置：
							</th>
							<td>
								 
								<input type="checkbox" size="18" class=""  <c:if test="${not_task_deadlines_flag=='not_task_deadlines' }">checked</c:if>
									name="elements_object" value="not_task_deadlines" />
								未达到任务完成期限
								 
								<input type="checkbox" size="18" class="" <c:if test="${not_task_money_flag=='not_task_money' }">checked</c:if>
									name="elements_object" value="not_task_money" />
								任务未完成指定金额
								<input type="checkbox" size="18" class="" <c:if test="${not_task_target_flag=='not_task_target' }">checked</c:if>
									name="elements_object" value="not_task_target" />
								任务未完成目标值
							</td>
						</tr>
						 -->
						<tr>
							<th>
								开始日期：
							</th>
							<td id="member_lv_td_sp">
								<input type="text" name="task_create_time"
									id="task_create_time" size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTask.task_create_time }" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
						<tr>
							<th>
								结束日期：
							</th>
							<td>
								<input type="text" name="task_aead_time"
									id="task_aead_time"  size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTask.task_aead_time}" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 
		<div class="tableform">
			<h4>
				活动时间设置
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<th>
								活动规则开始日期：
							</th>
							<td id="member_lv_td_sp">
								<input type="text" name="task_create_time"
									id="task_create_time"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTask.task_create_time }" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
						<tr>
							<th>
								活动规则结束日期：
							</th>
							<td>
								<input type="text" name="task_aead_time"
									id="task_aead_time"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTask.task_aead_time}" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		 -->
		<div class="tableform">
			<h4>
				告警人员设置
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<th>
								告警联系人：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="partner_name" name="partner_name" readonly
									value="${alarmReceiver.recevier_username }" class="searchipt" required="true"/>
								<input type="hidden" class="ipttxt" id="partner_id" name="partner_id"
									value="${partner_id }" class="searchipt"/>
								<input type="button" id="partner_name_btn" class="graybtn1"
									value="选择" />
							</td>
						</tr>
						<tr>
							<th>
								告警联系号码：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="phone" name="phone"
									value="${alarmReceiver.recevier_phone_num }" class="searchipt" required="true"/>
								<input type="button" id="phone_btn" class="graybtn1"
									value="选择" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="tableform">
			<h4>
				告警内容描述
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<td>
								任务告警要素：
								<!-- 
								<input type="checkbox" size="18" class=""  <c:if test="${not_task_deadlines_flag=='not_task_deadlines' }">checked</c:if>
									name="elements_object" value="not_task_deadlines" />
								未达到任务完成期限
								 -->
								<input type="checkbox" size="18" class="" <c:if test="${not_task_money_flag=='not_task_money' }">checked</c:if>
									name="elements_object" value="not_task_money" attr_value="{任务未完成指定金额}"/>
								任务未完成指定金额
								<input type="checkbox" size="18" class="" <c:if test="${not_task_target_flag=='not_task_target' }">checked</c:if>
									name="elements_object" value="not_task_target" attr_value="{任务未完成目标值}"/>
								任务未完成目标值
							</td>
						</tr>
						<tr>
							<td>
								<textarea vtype="textarea" required="true" rows="6" cols="60"
									name="task_content" type="textarea">${alarmTask.task_content }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${flag=='add' || flag=='edit' }">
		<div class="submitlist" align="center">
			<table align="right">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input id="recevier_id" name="recevier_id" type="hidden" value="${alarmReceiver.recevier_id }"/>
						<input id="alarm_task_id" name="alarm_task_id" type="hidden" value="${alarmTask.alarm_task_id }"/>
						<input id="alarm_task_type" name="alarm_task_type" type="hidden" value="2"/>
						<input id="addAlarmBaseBtn" type="button" value=" 保存 "
							class="submitBtn" />
						<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<div class="clear"></div>
	</form>
</div>
<!-- 选择分销商对话框 -->
<div id="showPartner"></div>

<!-- 选择联系号码对话框 -->
<div id="showPhone"></div>

<!-- 选择任务名称对话框 -->
<div id="showTask"></div>

<script type="text/javascript">
var load = {
	asSubmit : function(url, params, dataType, callBack) {
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数

					}
				});
	}
};

$(function(){
	  $("#task_alarm_form").validate();
	  
      $("#addAlarmBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/alarm!editTaskAlarm.do?ajax=yes";
			Cmp.ajaxSubmit('task_alarm_form', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
				   }
			       if (responseText.result == 0) {
					       //成功
						    alert(responseText.message);
						     location.href=ctx+ "/shop/admin/alarm!list.do?alarm_task_type=2";
					}
			},'json');
		})
	$("[name='elements_object']").click(function(){
	  		if($(this).attr("checked")){
	  			$("[name='task_content']").val($("[name='task_content']").val()+$(this).attr("attr_value"));
	  		}
	  });
})
</script>