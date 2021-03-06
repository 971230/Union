<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
<form id="back_desc" action="" method="post">
            <div class="grid_n_div">
			    <div class="grid_n_div">
					<div class="grid_n_cont">
			  		<div class="grid_n_cont_sub">
			              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
			                <tbody><tr>
			                  <th></th>
			                  <th></th>
			                  <th></th>
			                  <th></th>
			                </tr>
			                <tr id="refund_tr">
			                	<td><span style="color:red;">*</span>是否退款：</td>
				                <td> 
					            	<select name="is_refund" class="ipt_new" style="width:140px;">
					            		<option value=""  >请选择</option>
					               		<option value="1"  >是</option>
					               		<option value="2"  >否</option>
									</select> 
							    </td>
			                </tr>
			                <tr id="back_desc_tr">
			                	<td><span style="color:red;">*</span>退单描述：</td>
				                <td> 
					            	<select name="returnedReasonCode" class="ipt_new" style="width:140px;">
			                			<c:forEach items="${ordReturnedReasonList }" var="ds">
			                				<option value="${ds.pkey }" >${ds.pname }</option>
			                			</c:forEach>
									</select> 
							    </td>
			                </tr>
			                <tr>
			                     <td><span style="color:red;padding-right: 3px">*</span>处理意见：</td>
			                     <td><textarea id="node_deal_message" name="dealDesc" cols="45" rows="5"></textarea></td>
			                </tr>
			              </tbody></table>
				  </div>
				  
				<div style="margin-left: auto;margin-right: auto;" align="center">
                	<input name="sure" type="button" id="sure"  query_type="${query_type }" orders="${orders}" show_flag="${show_flag}" value="确认退单"  class="graybtn1" />
                	<input name="back" type="button" id="back" name="back" value="返回 "  class="graybtn1" />
            	</div>
				  
			</div>	
</form>
<script type="text/javascript">
$(function(){
	
	var query_type = $("#sure").attr("query_type");
	if(query_type=='returned'||query_type=='order_receive'){//退单申请
		$("#refund_tr").remove();
	}else if(query_type=='returned_cfm'){//退单处理
		$("#back_desc_tr").remove();
		var show_flag = $("#sure").attr("show_flag");
		if(show_flag==0){
			$("#refund_tr").remove();
		}
	}else if(query_type=='refund'){
		$("#refund_tr").remove();
		$("#back_desc_tr").remove();
		$("input[name='sure']").val("退款");
	}
	
	$("input[name='sure']").unbind("click").bind("click",function(){
		var dealDesc = $("[name='dealDesc']").val();
		var orders = $(this).attr("orders");
		var returnedReasonCode = "";
		var is_refund = "";
		var query_type = $("#sure").attr("query_type");
		if(query_type=='returned'||query_type=='order_receive'){//退单申请
			returnedReasonCode = $("[name='returnedReasonCode']").val();
			if(returnedReasonCode=='' || returnedReasonCode==null){
				alert("请选择退单描述!");
				return;
			}
			var url = ctx + "/shop/admin/ordReturned!batchBackApply.do?ajax=yes&applyFrom=2";
			
		}else if(query_type=='returned_cfm'){//退单处理
			var show_flag = $("#sure").attr("show_flag");
			if(show_flag!=0){
				var is_refund = $("select[name='is_refund'] option:selected").val();
				if(is_refund==null || is_refund==''){
					alert("退款方式不能为空!");
					return;
				}
			}
			var url = ctx + "/shop/admin/ordReturned!batchOrOneReturned.do?ajax=yes";
		}else if(query_type=='refund'){//退款处理
			var url = ctx + "shop/admin/orderCrawlerAction!batchOrderRefund.do?ajax=yes";
		}
		
		if(dealDesc==null || dealDesc==''){
			alert("处理描述及意见不能为空!");
			return;
		}
		
		$.ajax({
			url : url,
			data : {"orders":orders,
					"dealDesc":dealDesc,
					"is_refund":is_refund,
					"returnedReasonCode":returnedReasonCode
			},
			type : "post",
			dataType : "json",
			success : function(rsq) {
				if(rsq.status=='0'){
					alert(rsq.msg);
					Eop.Dialog.close("backDesc");
					if(query_type=='returned'){//退单申请
						var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned";
						window.location.href = url; 
					}else if(query_type=='returned_cfm'){//退单处理
						var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned_cfm";
						window.location.href = url; 
					}else if(query_type=='refund'){//退单处理
						var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=refund_audit";
						window.location.href = url; 
					}else if(query_type=='order_receive'){
						var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=order_receive";
						window.location.href = url; 
					}
				}else{
					alert(rsq.msg);
				}
			},
			error : function() {
				alert("操作失败!");
			}
		});
	});
	
	$("input[name='back']").unbind("click").bind("click",function(){
		var query_type = $("#sure").attr("query_type");
		Eop.Dialog.close("backDesc");
		if(query_type=='returned'){//退单申请
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned";
			window.location.href = url; 
		}else if(query_type=='returned_cfm'){//退单处理
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned_cfm";
			window.location.href = url; 
		}else if(query_type=='order_receive'){
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=order_receive";
			window.location.href = url; 
		}

	});
	
	$(".closeBtn").unbind("click").bind("click",function(){
		var query_type = $("#sure").attr("query_type");
		Eop.Dialog.close("backDesc");
		if(query_type=='returned'){//退单申请
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned";
			window.location.href = url; 
		}else if(query_type=='returned_cfm'){//退单处理
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned_cfm";
			window.location.href = url; 
		}else if(query_type=='order_receive'){
			var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=order_receive";
			window.location.href = url; 
		}
		
	})
})
</script>