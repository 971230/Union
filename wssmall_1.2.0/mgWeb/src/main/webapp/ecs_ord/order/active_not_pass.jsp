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
					<th>审核失败原因：</th>
					<td>
						<select name="active_fail_type" id="active_fail_type">
							<c:forEach var="active" items="${activeDescList }" varStatus="var_status">
								<option value="${active.pkey }">${active.pname }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pop_btn" align="center">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
		</div>
		<input id="order_id" type="hidden" value="${order_id }"/>
	</div>
	</form>
</div>

<script>

$(function(){
	$("#savebtn").bind("click", function() {
		var order_id = $("#order_id").val();
		var active_fail_type = $("#active_fail_type").val();
		$.ajax({
         	url:app_path+"/shop/admin/orderCrawlerAction!activeStatusNotPass.do?ajax=yes&order_id="+order_id+"&active_status=0&active_fail_type="+active_fail_type,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		alert(reply.message);
         		if(reply.result==0){
         			closeDialog();
         		}
         	},
         	error:function(){
         		
         	}
		});
	});
});

</script>


