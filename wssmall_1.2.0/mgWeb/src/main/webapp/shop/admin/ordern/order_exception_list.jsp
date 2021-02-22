<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='server_offer_form'
	action='ordern!listOrderException.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>异常名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="orderException.exception_name"
						value="${orderException.exception_name}" class="searchipt" /></td>
					<th>异常编码:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="orderException.exception_code"
						value="${orderException.exception_code}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a style="margin-right:10px;" class="graybtn1"
			href="${ctx}/shop/admin/ordern!addOrderException.do"><span>添加</span></a>
	</div>
</form>

<div id="serverList">
	<div class="grid">
		<form method="POST" id="server_offer_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell sort="sn" style="width: 100px;">异常名称</grid:cell>
					<grid:cell style="width: 100px;">异常编码</grid:cell>
					<grid:cell style="width: 80px;">异常描述</grid:cell>
					<grid:cell style="width: 80px;">创建时间</grid:cell>
					<grid:cell style="width: 80px;">操作</grid:cell>
				</grid:header>
				<grid:body item="exception">
					<grid:cell>${exception.exception_name}</grid:cell>
					<grid:cell>${exception.exception_code}</grid:cell>
					<grid:cell>${exception.comments}</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd"
							d_time="${exception.create_date}"></html:dateformat>
					</grid:cell>
					<grid:cell>
						<a href="ordern!editOrderException.do?exception_id=${exception.exception_id}">修改
						</a>
						<span class='tdsper'></span>
						<a href="javascript:void(0);" attr="${exception.exception_id}"
							class="delException">删除 </a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div id="del_exception_div"></div>
<script>
	$(function() {
		$(".delException").bind("click",function() {
			if(!confirm("是否确认删除此条记录？")){
				return;
			}
			var exception_id = $(this).attr("attr");
			var url = ctx
					+ "/shop/admin/ordern!delOrderException.do?ajax=yes&exception_id="
					+ exception_id;
			Cmp.ajaxSubmit('del_exception_div', '', url, {},ExceptionList.jsonBack, 'json');
		});
	});
	
	
	var ExceptionList = {
		jsonBack : function(responseText) {
			if (responseText.result == 1) {
				alert("操作成功");
				$("#submitButton").trigger("click");
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}
	}
</script>
