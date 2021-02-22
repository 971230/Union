<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file="/commons/taglibs.jsp"%>

<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>

<style type="text/css">
	.thstyle {
		width: 150px;
		text-align: right;
		-web-kit-appearance:none;
  		-moz-appearance: none;
  		font-size:1.0em;
		height:1.5em;
		outline:0;
	}
	.accept{
		width: 400px;
		-web-kit-appearance:none;
  		-moz-appearance: none;
  		font-size:1.2em;
		height:1.5em;
		border-radius:4px;
		border:1px solid #c8cccf;
		color:#6a6f77;
		outline:0;
	}
	a{
		cursor:pointer;
		text-decoration:none;
	}
	textarea{
		border:1px solid #c8cccf;
		border-radius:4px;
	}
	.input table th {

    border-bottom: 0px dashed #e0e0e0;
    color: #333333;
    font-size: 12px;
    padding-top: 8px;
    vertical-align: top;
    width: 134px;
    text-align: right;
    padding-right: 5px;
}
.input table tr {
}
	.sl{
		width: 200px;
		appearance:none;
		-web-kit-appearance:none;
  		-moz-appearance: none;
		border:1px solid #c8cccf;
		border-radius:4px;
		padding-right: 14px;
	}
</style>
<%
Object acceptDetail = (Object)request.getAttribute("acceptDetail");
JSONObject obj = JSONObject.fromObject(acceptDetail);
String can_update = obj.getString("can_update");
%>
<div class="input">
	<form class="validate" method="post" id="handleIntentForm" validate="true">
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th class="thstyle">营业县分：</th>
							<td>
								<select id="order_county_code1" name="order_county_code1" class="sl">
									<c:forEach items="${countyList}" var="ds">
										<option value="${ds.field_value}" ${ds.other_field_value==acceptDetail.county_id? 'selected': ''}>${ds.field_value_desc}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>客户联系结果：</th>
							<td>
								<select id="contact_result_frist" name="contact_result_frist"  class="sl">
								</select>
								<select id="contact_result_second" name="contact_result_second" class="sl">
								</select>
								</td>
						</tr>
						<tr>
							<th class="thstyle">客户联系说明：</th>
							<td><textarea rows="5" cols="88" id="contactDetails"  name="contactDetails"></textarea></td>
						</tr>
						<tr>
							<th class="thstyle">预约受理姓名：</th>
							<td >
							<input id="acceptName" name="acceptDetail.acceptName" value="" placeholder="${acceptDetail.acceptName}"  class="accept" <%if(!"yes".equals(can_update)) {%>readonly<%} %> autocomplete="off" /></td>
						</tr>
						<tr>
							<th class="thstyle">预约受理身份证：</th>
							<td><input id="acceptCert" name="acceptDetail.acceptCert" value="" placeholder="${acceptDetail.acceptCert}" class="accept" <%if(!"yes".equals(can_update)) {%>readonly<%} %> autocomplete="off"/></td>
						</tr>
						<tr>
							<th class="thstyle">预约受理地址：</th>
							<td><input id="acceptAddr" name="acceptDetail.acceptAddr" value="" placeholder="${acceptDetail.acceptAddr}" class="accept" autocomplete="off"/></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center">
				<a name="saveIntent" id="saveIntent" value="${order_id}" class="blueBtns"><span>保 存</span></a>
				&nbsp;&nbsp;
				<a name="addIntentWork" id="addIntentWork" value="${order_id}" class="blueBtns"><span>派单</span></a>
				&nbsp;&nbsp;
				<c:if test="${sms_open=='open'}">
					<a name="sendingSMSForm" value="${order_id}" class="blueBtns"><span>发送短信</span></a>
					&nbsp;&nbsp;
				</c:if>
				<a name="closeIntent" id="closeIntent" value="${order_id}" class="blueBtns"><span>结单</span></a>
				&nbsp;&nbsp;
				<a name="toinitiationCall" id="toinitiationCall" value="${order_id}" class="blueBtns"><span>发起电话外呼</span></a>
				&nbsp;&nbsp;
				<a name="cancelIntent" id="cancelIntent" class="blueBtns"><span>取消</span></a>
			</div>
			<input id="order_id" type="hidden" value="${order_id}" />
		</div>
	</form>
</div>
<div id="addWork"></div>
<div id="sendingSMSForm"></div>
<div id="initiationCall"></div>
<script type="text/javascript">
var contactResult;

	$(function() {
		contactResult = new ContactResult("contact_result_frist","contact_result_second");
		
		//保存
		$("a[name='saveIntent']").bind("click",
			function() {
				var order_id = $(this).attr("value");
				if(order_id == null || order_id == "") {
					alert("异常：order_id为空");
					return;
				}
				var contactDetails = $("#contactDetails").val();
				var contactResults = $("#contact_result_frist").val();
				var contactSecondResults = $("#contact_result_second").val();
				if(contactResults==null || contactResults=="" ){
					alert("请选择一级客户联系结果");
					return;
				}
				if(contactSecondResults==null || contactSecondResults=="" ){
					alert("请选择二级客户联系结果");
					return;
				}
				var checkText = $("#contact_result_frist").find("option:selected").text();
				var checkText2 = $("#contact_result_second").find("option:selected").text();
				if(contactDetails == null || contactDetails == "") {
					if(checkText2 == "其他情况") {
						alert("客户联系结果：其他，需要添加客户联系说明");
						return;
					}
				} 
				if(confirm("请确认是否保存!")) {} else {
					return;
				}
				
				var acceptName = $("#acceptName").val();
				var acceptCert = $("#acceptCert").val();
				var acceptAddr = $("#acceptAddr").val();
				var order_county_code = $("#order_county_code1").val();
				debugger;
				
				$.ajax({  
                    type: "POST",  
                    data:"ajax=yes&order_id=" + order_id+"&contactDetails="+contactDetails+"&checkText="+checkText+"&contactResults="+contactResults+"&checkText2="+checkText2+"&contactSecondResults="+contactSecondResults+"&acceptAddr="+acceptAddr+"&acceptName="+acceptName+"&acceptCert="+acceptCert+"&order_county_code="+order_county_code,  
                    url: ctx + "/shop/admin/orderIntentAction!saveIntent.do",  
                    dataType: "json",  
                    cache: false,  
                    success: function(responseText){   
                    	if(responseText.result == 0) {
    						alert(responseText.message);
    						Eop.Dialog.close("handleIntent");
    					} else {
    						alert(responseText.message);
    					} 
                    }  
                });
				
			});
		//结单
		$("a[name='closeIntent']").bind("click",
			function() {
				var order_id = $(this).attr("value");
				if(order_id == null || order_id == "") {
					alert("异常：order_id为空");
					return;
				}
				var contactDetails = $("#contactDetails").val();
				var contactResults = $("#contact_result_frist").val();
				var contactSecondResults = $("#contact_result_second").val();
				var checkText = $("#contact_result_frist").find("option:selected").text();
				var checkText2 = $("#contact_result_second").find("option:selected").text();
				debugger;
				if(contactResults == null || contactResults == "") {
					alert("请选择一级客户联系结果");
					return;
				}
				if(contactSecondResults==null || contactSecondResults=="" ){
					alert("请选择二级客户联系结果");
					return;
				}
				if(contactDetails == null || contactDetails == "") {
					if(checkText2 == "其他情况") {
						alert("客户联系结果：其他，需要添加客户联系说明");
						return;
					}
				} 
				if(confirm("请确认是否结单!")) {} else {
					return;
				}
				
				$.ajax({  
                    type: "POST",  
                    data:"ajax=yes&order_id=" + order_id+"&contactDetails="+contactDetails+"&checkText="+checkText+"&contactResults="+contactResults+"&checkText2="+checkText2+"&contactSecondResults="+contactSecondResults+"&acceptAddr="+acceptAddr+"&acceptName="+acceptName+"&acceptCert="+acceptCert,  
                    url: ctx + "/shop/admin/orderIntentAction!closeIntent.do",  
                    dataType: "json",  
                    cache: false,  
                    success: function(responseText){   
                    	if(responseText.result == 0) {
    						alert(responseText.message);
    						Eop.Dialog.close("handleIntent");
    					} else {
    						alert(responseText.message);
    					} 
                    }  
                });
			});

		Eop.Dialog.init({
			id: "addWork",
			modal: true,
			title: "派发工单",
			width: '1000px'
		});
		Eop.Dialog.init({
			id: "initiationCall",
			modal: true,
			title: "电话外呼",
			width: '1000px'
		});
		$("a[name='addIntentWork']").bind("click",
			function() {
				var order_id = $(this).attr("value");
				if(order_id == null || order_id == "") {
					alert("异常：order_id为空");
					return;
				}

				var url = ctx + "/shop/admin/ordAuto!checkWork.do?ajax=yes&order_id=" + order_id;
				Cmp.ajaxSubmit('handleIntentForm', '', url, {}, function(responseText) {
					if(responseText.result == 0) {
						Eop.Dialog.open("addWork");
						url = ctx + "/shop/admin/ordAuto!ord_Work.do?ajax=yes&order_id=" + order_id;
						$("#addWork").load(url, {}, function() {});
					} else {
						alert(responseText.message);
					}
				}, 'json');

			});
		
		Eop.Dialog.init({id: "sendingSMSForm",modal: true,title: "发送短信",width: '800px'});
		$("a[name='sendingSMSForm']").bind("click",
			function() {
				Eop.Dialog.open("sendingSMSForm");
				var order_id = $(this).attr("value");
				if(order_id == null || order_id == "") {
					alert("异常：order_id为空");
					return;
				}
				var url = ctx + "/shop/admin/orderIntentAction!sendingSMSForm.do?ajax=yes&order_id=" + order_id;
				$("#sendingSMSForm").load(url, {}, function() {});

			});
		$("a[name='toinitiationCall']").bind("click",
				function() {
					var order_id = $(this).attr("value");
					if(order_id == null || order_id == "") {
						alert("异常：order_id为空");
						return;
					}
							Eop.Dialog.open("initiationCall");
							url = ctx + "shop/admin/ordCall!toinitiationCall.do?ajax=yes&order_id=" + order_id +"&call_order_type=intent";
							$("#initiationCall").load(url, {}, function() {});
						

				});
		$("a[name='cancelIntent']").bind("click",
			function() {
				if(confirm("请确认关闭!")) {
					Eop.Dialog.close("handleIntent");
				} else {
					return;
				}
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