<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">定义异常单</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="orderExcepCollect.order_id" value="${orderId}"/>
			<tr>
				<th><label class="text"><span class='red'>*</span>异常类型：</label></th>
				<td><select class="ipttxt" style="width: 100px"
						name="orderExcepCollect.exception_id">
							<c:forEach var="list" items="${exceptionList}">
								<option value="${list.exception_id }">${list.exception_name}</option>
							</c:forEach>
					</select></td>
			</tr>
			<tr>
				<th><label class="text">异常描述：</label></th>
				<td><textarea  style="height: 100px;" name="orderExcepCollect.comments"
					dataType="string"  value=""></textarea></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td> <input type="submit" id="btn"
						value=" 确  定 " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	$(function(){
		$(".submitBtn").bind("click",function(){
			$("#theForm.validate").validate();
			var url = ctx+ "/shop/admin/ordern!saveOrderFail.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, orderFail.jsonBack,'json');
		});
	});
	
	
	var orderFail = {
		jsonBack:function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				Eop.Dialog.close("order_fail_div");
				$("#orderInfoButton").trigger("click");
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
</script>