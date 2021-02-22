<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form method="post" id="intentRemarkform" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>添加备注：</th>
							<td>
								<textarea rows="5" cols="88" id="remarks" name="remarks" value=""></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="pop_btn" align="center">
				<a id="saveRemarks" class="blueBtns"><span>保 存</span></a>
			</div>
			<input id="order_id" name="order_id" type="hidden" value="${order_id}" />
			<input id="type" name="type" type="hidden" value="${type}" />
		</div>
	</form>
</div>

<div id="queryUserListDlg"></div>
<script>

$(function() {
	$("#saveRemarks").click(function() {
		var remarks = $("#remarks").val();
		if ( remarks == null || remarks == "") {
			alert("请选择输入备注信息！");
			return;
		}
		
		if(window.confirm('你确定要提交备注吗？')){
                //alert("确定");
                //return true;
		}else{
               //alert("取消");
               return;
		}
		
		var order_id = $("#order_id").val();
		var type = $("#type").val();
		
	    var url = ctx + "/shop/admin/orderIntentAction!addRemarks.do?ajax=yes";
	    
	    var param = {
	    		"remarks":remarks,
	    		"order_id":order_id
	    };
	    
	    $.ajax({
	     	url:url,
	     	type: "POST",
	     	dataType:"json",
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert(reply["message"]);
	    				Eop.Dialog.close("addRemarks");//关闭页面
	     			}else{
	     				alert(reply["message"]);
	     			}
	     		}else{
	     			alert("保存失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("保存失败："+msg);
	     	}
		});
	}); 
});
	
</script>
