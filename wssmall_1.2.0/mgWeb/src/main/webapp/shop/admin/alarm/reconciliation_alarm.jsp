<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/alarm.js"></script>
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
	<form id="reconciliation_alarm_form" action="" method="post" class="validate">
		<div class="tableform">
			<h4>
				对账告警设置
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%">
					<tbody>
						<tr>
							<th>
								对账告警名称：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="alarm_task_name_btn" name="alarm_task_name"
									value="${alarm_task_name }" class="searchipt"  dataType="string"/>
								<span id="alarm_task_name_message"></span>
							</td>
						</tr>
						<tr>
							<th>
								对账文本格式：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" id="text_pattern" name="text_pattern"
									value="${text_pattern }" class="searchipt"  dataType="string"/>
							</td>
						</tr>
						<tr>
							<th>
								对账异常金额：
							</th>
							<td>
								<input type="text" size="10" class="ipttxt" id="min_money" name="min_money"
									value="${min_money }" class="searchipt"  dataType="float"/>
								&lt;=异常对账金额&lt;
								<input type="text" size="10" class="ipttxt" id="max_money" name="max_money"
									value="${max_money }" class="searchipt"  dataType="float"/>
							</td>
						</tr>
						<tr>
							<th>
								对账异常数量：
							</th>
							<td>
								<input type="text" size="10" class="ipttxt" id="min_amount" name="min_amount"
									value="${min_amount }" class="searchipt"  dataType="int"/>
								&lt;=异常对账数量&lt;
								<input type="text" size="10" class="ipttxt" id="max_amount" name="max_amount"
									value="${max_amount }" class="searchipt"  dataType="int"/>
							</td>
						</tr>
						<tr>
							<th>
								开始日期：
							</th>
							<td id="member_lv_td_sp">
								<input type="text" name="task_create_time"
									id="task_create_time" size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${task_create_time }" />'
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
									id="task_aead_time" size="18"
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${task_aead_time}" />'
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
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${task_create_time }" />'
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
									value='<html:dateformat pattern="yyyy-MM-dd" d_time="${task_aead_time}" />'
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
									value="${partner_name }" class="searchipt" required="true"/>
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
									value="${phone }" class="searchipt" required="true"/>
								<input type="button" id="phone_btn" class="graybtn1"
									value="选择" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 
		<div class="tableform">
			<h4>
				要素对象
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%">
					<tbody>
						<tr>
							<td>
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
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		 -->
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
								对账要素:&nbsp;&nbsp;
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
						</tr>
						<tr>
							<td>
								<textarea vtype="textarea" required="true" rows="6" cols="60"
									name="task_content" type="textarea"></textarea>
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
						<input id="alarm_task_type" name="alarm_task_type" type="hidden" value="1"/>
						<input id="addAlarmBaseBtn" type="button" value=" 保存 " class="submitBtn" />
						<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
</div>
<!-- 选择分销商对话框 -->
<div id="showPartner"></div>

<!-- 选择联系号码对话框 -->
<div id="showPhone"></div>
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
	  $("#reconciliation_alarm_form").validate();
      $("#addAlarmBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/alarm!addReconciliationAlarm.do?ajax=yes";
			Cmp.ajaxSubmit('reconciliation_alarm_form', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
				   }
			       if (responseText.result == 0) {
					       //成功
						    alert(responseText.message);
						     location.href=ctx+ "/shop/admin/alarm!list.do?alarm_task_type=1";
					}
			},'json');
		})
		
	  $("[name='elements_object']").click(function(){
	  		if($(this).attr("checked")){
	  			$("[name='task_content']").val($("[name='task_content']").val()+$(this).attr("attr_value"));
	  		}
	  });
	  
	  $("#alarm_task_name_btn").blur(function() {
		var alarm_task_name = $.trim($('#alarm_task_name_btn').val());
		alarm_task_name = encodeURI(encodeURI(alarm_task_name, true), true);
		
		url = ctx
				+ "/shop/admin/alarm!isExits.do?alarm_task_name="
				+ alarm_task_name + "&ajax=yes";
		if (alarm_task_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {
					
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#alarm_task_name_message").html(responseText.message);
						$('#alarm_task_name_btn').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#alarm_task_name_message").html(responseText.message);
					}

				});
	});
})
</script>