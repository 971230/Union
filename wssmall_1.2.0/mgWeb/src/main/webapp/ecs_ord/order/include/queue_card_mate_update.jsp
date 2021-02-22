<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>

<div class="input" align="center">
<form action="javascript:void(0)" class="validate" method="post"
		name="updateQueueCardMateForm" id="updateQueueCardMateForm" >
		<table class="form-table">
			<tr>
				<input type="hidden" name="queue_card_mate_id" value="${queue_card_mate_id }" />
				<input type="hidden" name="action_type" value="${action_type }" />
				<th><label class="text"><span class='red'>*</span>队列编码：</label></th>
				<td><select name="queueCardMateVo.queue_code" style="width: 120px" id = "queue_code">
						<option value="">请选择队列</option>
						<c:forEach items="${card_mate_queue_list}" var="v">
							<option <c:if test="${queueCardMateVo.queue_code eq v.pkey }"> selected='selected'</c:if> value="${v.pkey }">${v.pname }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>写卡机编码：</label></th>
				<td><input type="text" value="${queueCardMateVo.card_mate_code }" name="queueCardMateVo.card_mate_code" /></td>
			</tr>
			<tr>
				<th><label class="text">描述：</label></th>
				<td><textarea type="textarea"  rows="6" cols="40" name="queueCardMateVo.describe" type="textarea">${queueCardMateVo.describe}</textarea></td>
			</tr>
		</table>
		<div class="submitlist" align="center">
			<input class="comBtn" type="button"
				value="确定" onclick="updateQueueCardMate();" style="margin-right:10px;" />
		</div>
	</form>
</div>
<script>
	function updateQueueCardMate() {
		var url = "queueCardMateManagerAction!updateQueueCardMate.do?ajax=yes";
		var saveBack = function(reply){
			if(reply.result==0){
				alert(reply.message);
			    queryQueueCardMate();
			}else{
				alert(reply.message);
			}
		};
		Cmp.ajaxSubmit('updateQueueCardMateForm', '', url, {}, saveBack, 'json');
		
	}
</script>