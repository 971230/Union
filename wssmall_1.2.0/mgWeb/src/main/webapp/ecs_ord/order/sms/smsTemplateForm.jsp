<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript">
	$(function() {
		 $("#hide_params_tb").bind("click", function() {
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click", function() {
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		}); 
		$("#querysmsTemplateForm").bind("click",
				function() {
					$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
				});

		Eop.Dialog.init({id:"addSMSForm",modal:false,title:"新增短信模版",width:'1100px'});
		$("a[name='addSMS']").bind("click",function(){
			$("#addSMSForm").empty();
			var url= ctx+"/shop/admin/orderIntentAction!addSMSForm.do?ajax=yes";
			$("#addSMSForm").load(url);
			Eop.Dialog.open("addSMSForm");
			
		});
		
		Eop.Dialog.init({id:"editSMSForm",modal:true,title:"编辑短信模版",width:'1100px'});
		$("a[name='editSMSForm']").bind("click",
				function() {
					$("#editSMSForm").empty();
					var sms_id = $(this).attr("value");
					if(sms_id == null || sms_id==""){
						alert("异常：短信模版编号为空");
						return;
					}
					var url= ctx+"/shop/admin/orderIntentAction!editSMSForm.do?ajax=yes&sms_id="+sms_id;
					$("#editSMSForm").load(url,{},function(){});
					Eop.Dialog.open("editSMSForm");
				});
		
		Eop.Dialog.init({id:"smsDetailsForm",modal:true,title:"短信详情",width:'1100px'});
		$("a[name='smsDetailsForm']").bind("click",
				function() {
					$("#smsDetailsForm").empty();
					var sms_id = $(this).attr("value");
					if(sms_id == null || sms_id==""){
						alert("异常：短信模版编号为空");
						return;
					}
					var url= ctx+"/shop/admin/orderIntentAction!smsDetailsForm.do?ajax=yes&sms_id="+sms_id;
					$("#smsDetailsForm").load(url,{},function(){});
					Eop.Dialog.open("smsDetailsForm");
				});
		
		$("a[name='openSmsSend']").bind("click",function(){
			if(confirm("请确认是否开启短信发送功能!")) {} else {
				return;
			}
			$.ajax({  
                type: "POST",  
                data:"ajax=yes",  
                url: ctx + "/shop/admin/orderIntentAction!openSmsSend.do",  
                dataType: "json",  
                cache: false,  
                success: function(responseText){   
                	if(responseText.result == 0) {
						alert(responseText.message);
						$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
					} else {
						alert(responseText.message);
					} 
                }  
            });
		});
		$("a[name='colseSmsSend']").bind("click",function(){
			if(confirm("请确认是否关闭短信发送功能!")) {} else {
				return;
			}
			$.ajax({  
                type: "POST",  
                data:"ajax=yes",  
                url: ctx + "/shop/admin/orderIntentAction!colseSmsSend.do",  
                dataType: "json",  
                cache: false,  
                success: function(responseText){   
                	if(responseText.result == 0) {
						alert(responseText.message);
						$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
					} else {
						alert(responseText.message);
					} 
                }  
            });
		});
		
	});
	 (function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery); 
	 
</script>
	<div class="searchBx">
		<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
		<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display: none;">展开</a>
		<form action="/shop/admin/orderIntentAction!smsTemplateForm.do" method="post" id="smsTemplateForm" >
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>模版号：</th>
						<td>
							<input type="text" name="params.sms_id" value="${params.sms_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>状态：</th>
						<td>
							<select name="params.status" class="ipt_new" style="width: 145px;">
							<option value="">--请选择--</option>
								<c:forEach items="${smsStatusList}" var="ds">
			               			<option value="${ds.value}" ${ds.value==params.status?'selected':''}>${ds.value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
						<th>时间：</th>
						<td>
							<input type="text" name="params.create_start" value="${params.create_start}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							-
	                    	<input type="text" name="params.create_end" value="${params.create_end}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
					</tr>
					<tr>
						<th>类别：</th>
						<td>
							<select name="params.sms_level" class="ipt_new" style="width: 145px;">
								<c:forEach items="${smsLevelList}" var="ds">
			               			<option value="${ds.value}" ${ds.value==params.sms_level?'selected':''}>${ds.value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
						<th></th>
						<td>
							<a href="javascript:void(0);" name="addSMS" class="dobtn">新 增</a>
							<c:if test="${sms_open=='open'}">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" name="colseSmsSend" class="dobtn">关闭短信发送</a>
							</c:if>
							<c:if test="${sms_open=='colse'}">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" name="openSmsSend" class="dobtn">开启短信发送</a>
							</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" id="querysmsTemplateForm" class="dobtn">查 询</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="smsTemplateForm" action="/shop/admin/orderIntentAction!smsTemplateForm.do">
				<grid:header>
					<grid:cell>模版号</grid:cell>
					<grid:cell>内容</grid:cell>
					<grid:cell>创建人</grid:cell>
					<grid:cell>状态</grid:cell>
					<grid:cell>类别</grid:cell>
					<grid:cell>处理</grid:cell>
				</grid:header>
				<grid:body item="template">
					<grid:cell style="width: 10%;">${template.sms_id}</grid:cell>
					<grid:cell style="width: 50%;">
						<div title="${template.sms_template}">${template.sms_template}</div>
					</grid:cell>
					<grid:cell style="width: 10%;">&nbsp;${template.creater_id}</grid:cell>
					<grid:cell style="width: 5%;">${template.status}</grid:cell>
					<grid:cell style="width: 5%;">${template.sms_level}</grid:cell>
					<grid:cell style="width: 20%;"> 
						<c:if test="${params.sms_level!=1}">
							<a href="javascript:void(0);" value="${template.sms_id}" name="editSMSForm" class="dobtn" style="margin-left: 1px;">编 辑</a>
						</c:if>
						<a href="javascript:void(0);" value="${template.sms_id}" name="smsDetailsForm" class="dobtn" style="margin-left: 1px;">详 情</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
	<div id="addSMSForm"></div>
	<div id="editSMSForm"></div>
	<div id="smsDetailsForm"></div>
