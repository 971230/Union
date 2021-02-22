<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/template.js"></script>
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
	<form id="alarm_template_form" action="" method="post" class="validate">
		<div class="tableform">
			<h4>
				模板设置
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%">
					<tbody>
						<tr>
							<th>
								模板名称：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="template_name_btn" name="alarmTemplate.template_name"
									value="${alarmTemplate.template_name }" class="searchipt" required="true"/>
								<span id="template_name_message"></span>
							</td>
						</tr>
						<tr>
							<th>
								模板类型：
							</th>
							<td>
								<input type="radio" size="18" class=""  checked
									name="alarmTemplate.template_type" value="M" />
								短信模板
								<input type="radio" size="18" class="" 
									name="alarmTemplate.template_type" value="T" />
								页面模板
							</td>
						</tr>
						<tr>
							<th>
								模板应用场景：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="applic_scene_name" name="alarmTemplate.applic_scene_name"
									value="${alarmTemplate.applic_scene_name }" class="searchipt" readonly required="true"/>
								<input type="hidden"  id="applic_scene_id" name="alarmTemplate.applic_scene_id" />
								<input type="button" id="tapplic_scene_btn" class="graybtn1"
									value="选择" />
							</td>
						</tr>
						<!--  
						<tr>
							<th>
								已选要素：
							</th>
							<td>
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_amount" attr_value="{异常对账数量 }"/>
								异常对账数量 
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_money"  attr_value="{异常对账金额}"/>
								异常对账金额
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="text_pattern" attr_value="{对账文本格式}"/>
								对账文本格式
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="reconciliation_time"  attr_value="{对账时间}"/>
								对账时间
							</td>
						</tr>
						-->
						<tr>
							<th>
								开始日期：
							</th>
							<td id="member_lv_td_sp">
								<input type="text" name="alarmTemplate.able_time"
									id="able_time" size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTemplate.able_time }" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
						<tr>
							<th>
								结束日期：
							</th>
							<td>
								<input type="text" name="alarmTemplate.enable_time"
									id="enable_time"  size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTemplate.enable_time}" />'
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
								<input type="text" name="alarmTemplate.able_time"
									id="able_time"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTemplate.able_time }" />'
									maxlength="60" class="dateinput ipttxt" dataType="date"
									required="true" />
							</td>
						</tr>
						<tr>
							<th>
								活动规则结束日期：
							</th>
							<td>
								<input type="text" name="alarmTemplate.enable_time"
									id="enable_time"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${alarmTemplate.enable_time}" />'
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
				模板内容
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr >
							<td id="reconciliation_alarm" style="display:none;">已选要素：
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_amount" attr_value="{异常对账数量}"/>
								异常对账数量 
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_money"  attr_value="{异常对账金额}"/>
								异常对账金额
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="text_pattern" attr_value="{对账文本格式}"/>
								对账文本格式
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="reconciliation_time"  attr_value="{对账时间}"/>
								对账时间
							</td>
							<td id="task_alarm" style="display:none;">已选要素：
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="not_task_money" attr_value="{任务未完成指定金额}"/>
								任务未完成指定金额
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="not_task_target" attr_value="{任务未完成目标值}"/>
								任务未完成目标值
							</td>
						</tr>
						<tr>
							<td>
								<textarea vtype="textarea" required="true" rows="6" cols="60"
									id="temp_date" name="alarmTemplate.temp_date" type="textarea">${alarmTemplate.temp_date }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="submitlist" align="center">
			<table align="right">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input id="addAlarmTemplateBaseBtn" type="button" value=" 保存 "
							class="submitBtn" />
						<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
</div>
<div id="showAlarmTaskDalog"></div>

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
	  $("#alarm_template_form").validate();
      $("#addAlarmTemplateBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/alarm!addAlarmTemplate.do?ajax=yes";
			Cmp.ajaxSubmit('alarm_template_form', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
				   }
			       if (responseText.result == 0) {
					       //成功
						    alert(responseText.message);
						    //location.reload();
						    location.href=ctx+ "/shop/admin/alarm!alarmTemplateList.do";
					}
			},'json');
		})
		
	  $("[name='elements_object']").click(function(){
	  	$("#temp_date").val($("#temp_date").val()+$(this).attr("attr_value"));
	  });
	  
	  $("#template_name_btn").blur(function() {
		var template_name = $.trim($('#template_name_btn').val());
		template_name = encodeURI(encodeURI(template_name, true), true);
		
		url = ctx
				+ "/shop/admin/alarm!isExits.do?templet_name="
				+ template_name + "&ajax=yes";
		if (template_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {
					
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#template_name_message").html(responseText.message);
						$('#template_name_btn').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#template_name_message").html(responseText.message);
					}

				});
	});
})
</script>