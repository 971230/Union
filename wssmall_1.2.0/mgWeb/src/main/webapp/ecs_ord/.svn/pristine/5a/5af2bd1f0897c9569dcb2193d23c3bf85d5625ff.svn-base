<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<%
String order_id = request.getParameter("order_id");
%>

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

<body>

<div class="input">
	<div>
		<div style="margin-top: 5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th style="width: 150px;text-align: right;" >客户联系结果：</th>
						<td>
							<select id="select_contact_results_frist" class="ipt_new grid_form_select" style="width: 200px;">
	                        		
                            </select>
                            --
                            <select id="select_contact_results_second" class="ipt_new grid_form_select" style="width: 200px;">
                        		
                            </select>
						</td>
					</tr>
					
					<tr>
						<th style="width: 150px;text-align: right;" >退单备注：</th>
						<td>
							<textarea rows="5" cols="63" id="cancel_order_remark" ></textarea>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div>

		<div class="pop_btn" align="center">
			<a id="cancel_order_comfirm_btn" class="blueBtns" style="cursor: pointer;"><span>确定</span></a>
		</div>
	</div>
</div>

</body>

<script>

var ComfirmContactResult = new CancelOrderContactResult("select_contact_results_frist","select_contact_results_second");

/*客户联系结果加载*/
function CancelOrderContactResult(frist_select,second_select){
	this.frist=[];
	this.second=[];
	
	this.frist_select=frist_select;
	this.second_select=second_select;
	
	$.ajax({
		url: ctx + '/shop/admin/orderIntentAction!getFristContactResults.do?ajax=yes',
		dataType:"json",
     	data:{},
     	async:false,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			frist = reply["frist_results"];
     			
     			$("#"+frist_select).empty();
     			
				$("#"+frist_select).append("<option value=''>请选择</option>");
				
				for(var i=0;i<frist.length;i++){
					var option = "<option value="+frist[i].result_id+">"+frist[i].results+"</option>";
					$("#"+frist_select).append(option);
				}
     		}else{
     			alert("客户联系结果为空");
     		}
     	},error:function(msg){
     		alert("初始化联系结果失败："+msg);
     	}
	});
	
	this.doGoodsTypeChange = function(frist,second){
		$("#"+frist_select).val(frist);
		$("#"+frist_select).change();
		
		$("#"+second_select).val(second);
		$("#"+second_select).change();
	};
	
	$("#"+frist_select).change(function(){
		var fristid = $("#"+frist_select).val();
		var param = {
				frist_id:fristid
		};
		$("#"+second_select).empty();
		
		$("#"+second_select).append("<option value=''>请选择</option>");
		
		$.ajax({
			url: ctx + '/shop/admin/orderIntentAction!getSecondContactResults.do?ajax=yes',
			dataType:"json",
	     	data:param,
	     	async:false,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			second = reply["second_results"];
	     			
	     			$("#"+second_select).empty();
	     			
					$("#"+second_select).append("<option value=''>请选择</option>");
					
					for(var i=0;i<second.length;i++){
						var option = "<option value="+second[i].result_id+">"+second[i].results+"</option>";
						$("#"+second_select).append(option);
					}
	     		}else{
	     			alert("客户联系结果为空");
	     		}
	     	},error:function(msg){
	     		alert("初始化联系结果失败："+msg);
	     	}
		});
	});
}

</script>