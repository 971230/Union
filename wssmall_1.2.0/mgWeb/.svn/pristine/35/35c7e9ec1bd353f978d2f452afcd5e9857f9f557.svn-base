 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">异常操作</span><span
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
			<input type="hidden" name="orderException.exception_id" value="${orderException.exception_id}"/>
			<tr>
				<th><label class="text"><span class='red'>*</span>异常名称：</label></th>
				<td><input type="text" class="ipttxt" name="orderException.exception_name"
					dataType="string" required="true" value="${orderException.exception_name}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>异常编码：</label></th>
				<td><input type="text" class="ipttxt" name="orderException.exception_code"
					dataType="string" required="true" value="${orderException.exception_code}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>异常描述：</label></th>
				<td><input type="text" class="ipttxt" name="orderException.comments"
					dataType="string" required="true" value="${orderException.comments}"/></td>
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
			var url = ctx+ "/shop/admin/ordern!saveOrderException.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, AddException.jsonBack,'json');
		});
	});
	
	
	var AddException = {
		jsonBack:function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="ordern!listOrderException.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
</script>