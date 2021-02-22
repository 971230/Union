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
							<th style="width: 150px;text-align: right;" >被叫叫号码：</th>
							<td>
							<input  name="calleePhone" id="calleePhone" value="${calleePhone}" disabled="disabled">
                            </td>
							<th style="width: 150px;text-align: right;">主叫号码：</th>
							<td>
							<input  name="callerPhone" id="callerPhone" value="${callerPhone}" disabled="disabled">
                            </td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center">
				<a id="callbtn" class="blueBtns"><span>确定</span></a>
				&nbsp;&nbsp;
				<a id="cancelBtn" class="blueBtns"><span>取消</span></a>
				<input id="order_id" type="hidden" value="${order_id}" />
				<input  type="hidden" id="call_order_type" value="${call_order_type}">
			</div>

		</div>
	</form>
</div>
<script>
	
	
	$(function() {
		// 取消 
		$("#cancelBtn").click(function() {
			Eop.Dialog.close("initiationCall");
			Eop.Dialog.close("order_btn_event_dialog");
		});

		//确定 按钮 
		$("#callbtn").click( function() {
				var order_id = $("#order_id").val();
				var calleePhone = $("#calleePhone").val();
				var callerPhone = $("#callerPhone").val().trim();
				var call_order_type = $("#call_order_type").val();
				if(callerPhone == null || callerPhone ==''){
					alert("请输入主叫号码");
					return ;
				}
				if(calleePhone == null || calleePhone ==''){
					alert("请输入被叫号码");
					return ;
				}
				if(window.confirm('确定要拨打电话吗？')) {
				} else {
					return;
				}
				$.Loading.show("正在处理。请稍候····");
				var url = ctx + "/shop/admin/ordCall!initiationCall.do";
				$.ajax({
					type: "POST",
					data: "ajax=yes&order_id=" + order_id +"&calleePhone=" + calleePhone +"&callerPhone=" + callerPhone +"&call_order_type=" +call_order_type ,
					url: url,
					dataType: "json",
					cache: false,
					success: function(responseText) {
						if(responseText.result == 0) {
							alert(responseText.message);
							$.Loading.hide();
							if("order"==call_order_type){
								Eop.Dialog.close("order_btn_event_dialog");
							}else{
								Eop.Dialog.close("initiationCall");
							}
						} else {
							alert(responseText.message);
						}
					}
				});
		});
	});
	
</script>