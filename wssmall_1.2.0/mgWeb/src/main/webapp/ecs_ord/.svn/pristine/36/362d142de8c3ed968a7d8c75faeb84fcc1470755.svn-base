<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
	<!-- .noborder {
		border-style: none;
	}
	 
	-->.icoFontlist {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.icoFontlist:hover {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		overflow: scroll;
		text-overflow: ellipsis;
		white-space: nowrap;
		cursor: pointer;
	}
	
	.second select {
		width: 352px;
		height: 106px;
		margin: 0px;
		outline: none;
		border: 1px solid #999;
		margin-top: 33px;
		background-color: white;
	}
	
	.second select option {
		background-color: inherit;
	}
	
	.op {
		background-color: transparent;
		bacground: tansparent;
		-webkit-appearance: none;
	}
	
	.second input {
		width: 350px;
		top: 9px;
		outline: none;
		border: 0pt;
		position: absolute;
		line-height: 30px;
		/* left: 8px; */
		height: 30px;
		border: 1px solid #999;
	}
	
	.blue {
		background: #1e91ff;
	}
	.SilverEdit 
	{
		color: #F00;
		height: 24px;
		line-height: 24px;
		text-align:center;
		width: 500px;
		margin-top:3px;
		border-top-style:none;
		border-left-style:none;
		border-right-style:none;
		border-bottom-style:none
	}
</style>
<%-- <%
  String checkFee = (String)request.getAttribute("checkFee");
  String checkcode = checkFee.split(",")[0];
  request.setAttribute("checkcode", checkcode);
  String checkmsg = checkFee.split(",")[1];
  request.setAttribute("checkmsg", checkmsg);
%> --%>
<script>
	
	
	$(function() {
		checkOrderState();
		function checkOrderState(){
			var order_id = $("#order_id").val();
			
			var url = ctx + "/shop/admin/ordAuto!ordAduit.do?ajax=yes&order_id=" + order_id;
			$('#workform').css('display','none');
			$('#savebtn').css('display','none');
			$('#cancelBtn').css('display','none');
			Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
				if (responseText.result == 1) {
					alert(responseText.message);
					Eop.Dialog.close("order_btn_event_dialog");
				} else if(responseText.result == 2){
					 $("#checkmsg").val(responseText.message);
					 $('#workform').css('display','');
					 $('#savebtn').css('display','');
					 $('#cancelBtn').css('display','');
				}else{
					$('#workform').css('display','');
					$('#savebtn').css('display','');
					$('#cancelBtn').css('display','');
				}
				//closeDialog();
			}, 'json');
		};
	});
</script>
<%-- <div class="input">
<c:if test="${checkcode!=0&&checkcode!=2}">
 <div style="text-align:center;color:#F00;">${checkmsg }</div>
</c:if>
<c:if test="${checkcode==0||checkcode==2}"> --%>
    <form class="validate" method="post" id="workform" validate="true">
		<div>
		<%-- <c:if test="${checkcode==2}"> --%><div style="text-align:center;color:#F00;"><input type="text" id="checkmsg" class="SilverEdit" disabled="disabled" value=""></div><%-- </c:if> --%>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th style="width: 150px;text-align: right;">订单审核/备注信息：</th>
							<td><textarea rows="5" cols="88" id="work_reason" name="work_reason"></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center">
				<a id="savebtn" class="blueBtns" ><span>确定</span></a>
				&nbsp;&nbsp;
				<a id="cancelBtn" class="blueBtns"><span>取消</span></a>
				<input id="order_id" type="hidden" value="${order_id}" />
			</div>

		</div>
	</form>
<%-- </c:if> --%>
	
</div>
<div id="queryUserListDlg"></div>
<script>
	
	
	$(function() {

		// 取消 
		$("#cancelBtn").click(function() {
			Eop.Dialog.close("order_btn_event_dialog");
			window.location.reload();
		});
		
		//确定 
		$("#savebtn").click(function(){
			if(window.confirm('你确定要提交工单吗？')) {
				//return true;
			} else {
				return;
			}
			var order_id = $("#order_id").val();
			var work_reason = $("#work_reason").val();
			var url = ctx + "/shop/admin/ordAuto!save_ordAduit.do";
			$.ajax({
				type: "POST",
				data: "ajax=yes&order_id=" + order_id + "&work_reason=" + work_reason,
				url: url,
				dataType: "json",
				cache: false,
				success: function(responseText) {
					if(responseText.result == 0) {
						alert(responseText.message);
						Eop.Dialog.close("order_btn_event_dialog");
						window.location.reload();
					} else {
						alert(responseText.message);
					}
				}
			});
		});
	});
</script>