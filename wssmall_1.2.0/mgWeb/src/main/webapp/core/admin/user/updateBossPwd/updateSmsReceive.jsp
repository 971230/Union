<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected">
						<span class="word">修改短信接收开关</span>
						<span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
</div>
</h3>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post" name="updateSmsReceiveForm" id="updateSmsReceiveForm" enctype="multipart/form-data">
		<table class="form-table">
			<tr>
				<th><label class="text">短信开关：</label></th>
				<td id="td_state">
					<c:choose>
							<c:when test="${adminUser.sms_receive eq '1'}">
				           		<input name="adminUser.sms_receive" type="radio" checked="checked" value="1"/>开&nbsp;&nbsp;
				           		<input name="adminUser.sms_receive" type="radio" value="0" />关
				           	</c:when>
				           	<c:when test="${adminUser.sms_receive eq '0'}">
				           		 <input name="adminUser.sms_receive" type="radio"  value="1"/>开&nbsp;&nbsp; 
				           		 <input name="adminUser.sms_receive" type="radio" checked="checked" value="0" />关
				           	</c:when>
				           	<c:otherwise>
					           <input name="adminUser.sms_receive" type="radio"  value="1"/>开&nbsp;&nbsp; 
					           <input name="adminUser.sms_receive" type="radio" value="0" />关
				           	</c:otherwise>
				       </c:choose>    
				</td>
			</tr>
		</table>
		<input type="hidden" name="userid"  value="${adminUser.userid}" />
		<input type="hidden" name="adminUser.userid"  value="${adminUser.userid}" />
		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="submit" id="updateSmsReceiveBtn" value="确    定" class="graybtn1" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	$(function() {
		$("#updateSmsReceiveBtn")
			.bind(
				"click",
				function() {
					if(window.confirm('你确定要修改吗？')){}else{
			               return;
					}
					var sms_receive = $('input:radio[name=adminUser.sms_receive]:checked').val();
					var userid = $('input[name=userid]').val();
					var url = ctx +
						"/core/admin/user/userAdmin!updateSmsReceive.do?ajax=yes";
					var param = {
				    		"userid":userid,
				    		"sms_receive":sms_receive
				    };
				    $.ajax({
				     	url:url,
				     	type: "POST",
				     	dataType:"json",
				     	data:param,
				     	success:function(reply){
				     		if(typeof(reply) != "undefined"){
				     			if("0" == reply["result"]){
				     				alert(reply["message"]);
				     			}else{
				     				alert(reply["message"]);
				     			}
				     		}else{
				     			alert("保存失败");
				     		}
				     	},
				     	error:function(msg){
				     		alert("保存失败："+msg);
				     	}
					});
				});
	});
</script>