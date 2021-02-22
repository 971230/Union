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
								<input type="text" size="18" class="ipttxt" id="alarm_task_name" name="alarm_task_name"
									value="${alarmTask.alarm_task_name }" class="searchipt"  readonly dataType="string"/>
							</td>
						</tr>
						
						<c:forEach var="condition" items="${conditionJsonLists}">
							<c:if test="${condition.e_name=='text_pattern'}">
								<tr>
									<th>
										对账文本格式：
									</th>
									<td>
										<input type="text" size="18" class="ipttxt" id="text_pattern" name="text_pattern"
											value="${condition.c_value }" class="searchipt"  dataType="string"/>
									</td>
								</tr>
							</c:if>
							<c:if test="${condition.e_name=='alarm_money'}">
								<tr>
									<th>
										对账异常金额：
									</th>
									<td>
										<input type="text" size="10" class="ipttxt" id="min_money" name="min_money"
											value="${condition.min_value }" class="searchipt"  dataType="float"/>
										&lt;=异常对账金额&lt;
										<input type="text" size="10" class="ipttxt" id="max_money" name="max_money"
											value="${condition.max_value }" class="searchipt"  dataType="float"/>
									</td>
								</tr>
							</c:if>
							<c:if test="${condition.e_name=='alarm_amount'}">
								<tr>
									<th>
										对账异常数量：
									</th>
									<td>
										<input type="text" size="10" class="ipttxt" id="min_amount" name="min_amount"
											value="${condition.min_value }" class="searchipt"  dataType="float"/>
										&lt;=异常对账数量&lt;
										<input type="text" size="10" class="ipttxt" id="max_amount" name="max_amount"
											value="${condition.max_value }" class="searchipt"  dataType="float"/>
									</td>
								</tr>
							</c:if>
						</c:forEach>
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
									id="task_aead_time" size="18"
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
									value="${alarmReceiver.recevier_userid }" class="searchipt"/>
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
									name="elements_object" value="alarm_amount"  <c:if test="${alarm_amount_flag=='alarm_amount' }">checked</c:if> attr_value="{异常对账数量}"/>
								异常对账数量 
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_money" <c:if test="${alarm_money_flag=='alarm_money' }">checked</c:if>  attr_value="{异常对账金额}"/>
								异常对账金额
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="text_pattern" <c:if test="${text_pattern_flag=='text_pattern' }">checked</c:if> attr_value="{对账文本格式}"/>
								对账文本格式
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="reconciliation_time" <c:if test="${reconciliation_time_flag=='reconciliation_time' }">checked</c:if>  attr_value="{对账时间}"/>
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
									name="elements_object" value="alarm_amount"  <c:if test="${alarm_amount_flag=='alarm_amount' }">checked</c:if> attr_value="{异常对账数量}"/>
								异常对账数量 
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="alarm_money" <c:if test="${alarm_money_flag=='alarm_money' }">checked</c:if>  attr_value="{异常对账金额}"/>
								异常对账金额
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="text_pattern" <c:if test="${text_pattern_flag=='text_pattern' }">checked</c:if> attr_value="{对账文本格式}"/>
								对账文本格式
								<input type="checkbox" size="18" class="" 
									name="elements_object" value="reconciliation_time" <c:if test="${reconciliation_time_flag=='reconciliation_time' }">checked</c:if>  attr_value="{对账时间}"/>
								对账时间
							</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="recevier_id" value="${alarmReceiver.recevier_id }">
								<input type="hidden" name="alarm_task_id" value="${alarmTask.alarm_task_id }">
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
						<input id="alarm_task_type" name="alarm_task_type" type="hidden" value="1"/>
						<input id="addAlarmBaseBtn" type="button" value=" 保存 " class="submitBtn" />
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
            var  url = ctx+ "/shop/admin/alarm!editReconciliationAlarm.do?ajax=yes";
			Cmp.ajaxSubmit('reconciliation_alarm_form', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
				   }
			       if (responseText.result == 0) {
					       //成功
						    alert(responseText.message);
						    //location.reload();
						    location.href=ctx+ "/shop/admin/alarm!list.do?alarm_task_type=1";
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