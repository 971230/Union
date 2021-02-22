<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/public/comun.js"></script>
<div class="input" >
	<form class="validate" method="post" id="exceptionform" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
				<tr>
					<th>原因：</th>
					<td colspan="3">
						<textarea rows="5" cols="55" name="queueManagevo.deal_reason" id="deal_reason"></textarea>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pop_btn" align="center">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
		</div>
		<input id="order_id" type="hidden" value="${queueManagevo.id }" name="queueManagevo.id"/>
		<input id="order_id" type="hidden" value="${queueManagevo.queue_switch }" name="queueManagevo.queue_switch"/>
	</div>
	</form>
</div>

<script>

$(function(){
	$("#savebtn").bind("click", function() {	
		if($("#deal_reason").val()==""){
			alert("请填写关闭原因！");
			return;
		}
		$.ajax({
			   url: "shop/admin/queueCardMateManagerAction!openOrCloseQueue.do?ajax=yes",
			   type: "POST",
			   dataType :"json",
			   beforeSend : function(){
				   $.Loading.show("操作中，请稍侯...");
			   },
			   data:$("#exceptionform").serialize() ,
			   success: function(data){
				   alert(data.message);	
				   cerrarCaja("switchDiv");
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


