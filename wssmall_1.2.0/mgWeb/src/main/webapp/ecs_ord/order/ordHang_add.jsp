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
</style>

<div class="input">
	<form class="validate" method="post" id="workform" validate="true">
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th style="width: 150px;text-align: right;">订单挂起/备注信息：</th>
							<td><textarea rows="5" cols="88" id="work_reason" name="work_reason"></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center">
				<a id="savebtn" class="blueBtns"><span>确定</span></a>
				&nbsp;&nbsp;
				<a id="cancelBtn" class="blueBtns"><span>取消</span></a>
			    <input id="order_id" type="hidden" value="${order_id}" />
			</div>

		</div>
	</form>
</div>
<div id="queryUserListDlg"></div>
<script>
	
	
	$(function() {

		// 取消 
		$("#cancelBtn").click(function() {
			Eop.Dialog.close("order_btn_event_dialog");
		});

		//确定 按钮 
		$("#savebtn").click(function() {
				if(window.confirm('你确定要挂起订单吗？')) {
					//return true;
				} else {
					//alert("取消");
					return;
				}
				var order_id = $("#order_id").val();
				var work_reason = $("#work_reason").val();
				var url = ctx + "/shop/admin/ordAuto!save_ordHang.do";
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
							window.location.href=ctx+"/shop/admin/ordAuto!showOrderList.do?is_return_back=1";
						} else {
							alert(responseText.message);
						}
					}
			});
				
		});
	});
	
</script>