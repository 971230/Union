<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
    border-style: none;
}
-->
</style>

<div class="input" >
	<form class="validate" method="post" id="exceptionform" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
				<tr>
					<th>异常类型：</th>
					<td>
						<select name="queueWriteCardvo.exception_type" id="exception_type">
							<option value="-1">请选择异常类型</option>
							<c:forEach var="exception" items="${exceptionList }" varStatus="var_status">
								<c:if test="${exception.codea!='hide' }">
									<option value="${exception.pkey }">${exception.pname }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td colspan="3">
						<textarea rows="5" cols="55" name="queueWriteCardvo.exception_msg" id="exception_msg"></textarea>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pop_btn" align="center">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
		</div>
		<input id="order_id" type="hidden" value="${order_id }" name="queueWriteCardvo.order_id"/>
	</div>
	</form>
</div>

<script>

$(function(){
	$("#savebtn").bind("click", function() {
		var exception_id = $("#exception_id").val();
		if (exception_id == "-1") {
			alert("请选择异常类型！");return false;
		}
		var url = ctx+"/shop/admin/queueCardMateManagerAction!rebackQueueOrder.do?ajax=yes";
		
		$.ajax({
			   url: "shop/admin/queueCardMateManagerAction!rebackQueueOrder.do?ajax=yes",
			   type: "POST",
			   dataType :"json",
			   beforeSend : function(){
				   $.Loading.show("操作中，请稍侯...");
			   },
			   data:$("#exceptionform").serialize() ,
			   success: function(data){
				   alert(data.message);	
				   cerrarCaja("rebackDiv");		
				   qry();
			   },
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus)
				},
			   complete: function(){
				   $.Loading.hide();
			   }	   
		});
	});
});

</script>


