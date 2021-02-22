<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<div>
<form action="queueCardMateManagerAction!list.do" id="queueCardMateForm" method="post" enctype="multipart/form-data">
		<div class="searchBx">
			<table width="100%" cellspacing="0" cellpadding="0" border="0"  class="tab_form">
				<tbody id="tbody_A">
					<tr>
						<th>队列编码：</th>
						<td><select name="queue_code" id="card_mate_queue_id" style="width: 120px">
								<option value="">请选择队列</option>	
								<c:forEach items="${card_mate_queue_list}" var="v">
									<option
										<c:if test="${queue_code eq v.pkey }"> selected='selected'</c:if>
										value="${v.pkey }">${v.pname }</option>
								</c:forEach>
						</select></td>
						<th>写卡机编码：</th>
						<td><input type="text" name="card_mate_code" value="${card_mate_code}" class="ipttxt" /></td>
						<th>写卡机状态：</th>
						<td><select name="card_mate_status" id="card_mate_status_id" style="width: 120px">
								<option value="">请选择写卡机状态</option>
								<c:forEach items="${card_mate_status_list}" var="v">
									<option
										<c:if test="${card_mate_status eq v.pkey }"> selected='selected'</c:if>
										value="${v.pkey }">${v.pname }</option>
								</c:forEach>
						</select></td>
						<td>
						<input type="button" style="margin-right:20px;" class="comBtn" onclick="queryQueueCardMate();" value="查&nbsp;询" id="buttonQueue" name="button">
						</td>
						
					</tr>
					<tr>
					<th><input type="button" class="comBtn" onclick="addQueueCardMate();" value="添&nbsp;加" id="buttonAdd" name="button"></th>
					<td colspan="6"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>


	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>队列编码</grid:cell>
				<grid:cell>写卡机编码</grid:cell>
				<grid:cell>写卡机状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>描述</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>${objeto.queue_code } </grid:cell>
				<grid:cell>${objeto.card_mate_code } </grid:cell>
				<grid:cell>
				<c:forEach items="${card_mate_status_list }" var="oi" varStatus="item">
		        		<c:if test="${objeto.card_mate_status eq oi.pkey}">
		        			${oi.pname}
                        </c:if>
                     </c:forEach>
				</grid:cell>
				<grid:cell>${fn:substring(objeto.create_time , 0 , 19)}  </grid:cell>
				<grid:cell>${objeto.describe } </grid:cell>
				<grid:cell>
					<a href="#" onclick="toModifyQueueCardMate('${objeto.queue_card_mate_id }');">修改</a>&nbsp;&nbsp;
					<a href="#" onclick="deleteQueueCardMate('${objeto.queue_card_mate_id }');">删除</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>


<div id="addQueueCardMateDiv"></div>
<div id="modifyQueueCardMateDiv"></div>
<script>
	initDialog("addQueueCardMateDiv", true, "队列写卡机添加", "600px", "550px");
	initDialog("modifyQueueCardMateDiv", true, "队列写卡机修改", "600px", "550px");

	function queryQueueCardMate(){
		document.getElementById("queueCardMateForm").submit();
	}
	
	function addQueueCardMate(){
		var url = "queueCardMateManagerAction!toQueueCardMate.do?action_type=0";
		abrirCajaVentana("addQueueCardMateDiv",url);
	}
	
	function toModifyQueueCardMate(queue_card_mate_id){
		var url = "queueCardMateManagerAction!toQueueCardMate.do?action_type=1&queue_card_mate_id="+queue_card_mate_id;
		abrirCajaVentana("modifyQueueCardMateDiv",url);
	}
	
	function deleteQueueCardMate(queue_card_mate_id){
		if(!confirm("确定删除该条记录吗？")){
			return ;
		}
		var url = "queueCardMateManagerAction!deleteQueueCardMate.do?ajax=yes";
		$.ajax({
            url:url,
            data:{queue_card_mate_id:queue_card_mate_id},
            type:'post',
            dataType:'json',
            success:function(data){
            	if(data.result==0){
					alert(data.message);
				    queryQueueCardMate();
				}else{
					alert(data.message);
				}
            },
			error : function(e) {
				$.Loading.hide();
				alert("操作失败，请重试"+e);
			}
        });
	
	}
</script>