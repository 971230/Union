<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="aito_order_return_f">
        <jsp:include page="return_params.jsp"/>
        </form>
        <div class='searchformDiv'>
        	<input type="button" class="comBtn" id="query_order_s" style="margin-right:10px;" value="查询"/>
        	<input type="button" name="batchRefundBtn" query_type="${query_type }" class="comBtn" style="margin-right:10px;" value="批量退款"/>
        </div>
        <div class="right_warp">
        <form action="" id="order_return_list_fm">
        	<grid:grid from="webpage" formId="aito_order_return_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; ">状态<input type="checkbox" id="checkAlls" /></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell width="25%">校验信息</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	    	<i class="${order.lock_clazz }"></i>
		  	    	<input type="checkbox" name="orderidArray" value="${order.orderTree.order_id }" />
		  	    </grid:cell>
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell>
		        		<div class="order_pri">
                        	<p class="tit">${order.goods_package }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<c:if test="${order.terminal!=''}"><p class="ps">终端：${order.terminal }</p></c:if>
                           	<p class="ps" style="color: red;">退单状态：${order.refund_status }</p>
                           	<c:if test="${order.refund_status != '正常单' && order.pay_type != 'HDFK'}">
                           		<p class="ps" style="color: red;">退款状态：${order.bss_refund_status }</p>
                           	</c:if>
                        </div>
		        </grid:cell> 
		        <grid:cell style="width:100px;">
		       		<div class="order_pri">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold">${order.flow_trace }</p>
                            <p class="ps">${order.shipping_type }</p>
                        </div>
		        </grid:cell> 
		        <grid:cell style="width:400px;">
					
				</grid:cell>
		        <grid:cell style="width:100px;">
		        	<input type="button" name="refundBtn" class="comBtn" order_model="${order.orderTree.orderExtBusiRequest.order_model }" 
		        		order_id="${order.orderTree.order_id }" pay_type="${order.pay_type }" 
		        		order_amount="${order.orderTree.orderBusiRequest.order_amount } " query_type="${query_type }" style="margin-right:10px;" value="退款"/>
		        	<input type="hidden" name="bss_refund_status" value="${order.bss_refund_status }"/>
		        	<input type="hidden" name="refund_status" value="${order.refund_status }"/>
		        </grid:cell>
		  </grid:body>
      	</grid:grid>
      	<input type="hidden" id="order_id_hidden" />
        </form>
        
       <div class="clear"></div>
     </div>
  </div>
</div>
<div id="choose_user_div"></div>
<div id="backDesc">
<div id="backDescDialog">
</div>
</div>
<br />
<br />
<br />
<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"backDesc",modal:true,title:"退款说明",height:'400px',width:'550px'});
		$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
		$("#order_return_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
		$("#order_return_list_fm .page").wrap("<form class=\"grid\"></form>");
		$("#checkAlls").bind("click",function(){
			$("input[type=checkbox][name=orderidArray]").trigger("click");
		});
		//查询
		$("#query_order_s").bind("click",function(){
			$("#aito_order_return_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do");
		});
		$("input[name='refundBtn']").unbind("click").bind("click",function(){
			var bss_refund_status = $(this).next().val();
			var refund_status = $(this).next().next().val();
			if(refund_status!='退单确认'){
				alert("请先退单确认");
				return ;
			}
			if(bss_refund_status=='线下退款成功' || bss_refund_status=='线上退款成功'){
				alert("订单已经退款成功");
				return ;
			}
			var orders = $(this).attr("order_id");
			var url = ctx + "/shop/admin/orderFlowAction!showBackDesc.do?ajax=yes&orders="+orders+"&query_type=refund";
			Eop.Dialog.open("backDesc");
			$("#backDescDialog").html("loading...");
			$("#backDescDialog").load(url,function(){});
			
		})
		$("input[name='batchRefundBtn']").unbind("click").bind("click",function(){
			var bss_refund_status = $(this).next().val();
			var refund_status = $(this).next().next().val();
			var len = $("input[type='checkbox'][name='orderidArray']:checked").length;
			if(len==0){
				alert("请勾选需要退款的订单!")
				return;
			}
			var cando = true;
			var orders = [];
			$("input[type='checkbox'][name='orderidArray']:checked").each(function(){
				var order_id = $(this).val();
				var refundBtn = $("input[name='refundBtn'][order_id='"+order_id+"']");
				var bss_refund_status = refundBtn.next().val();
				var refund_status = refundBtn.next().next().val();
				if(refund_status!='退单确认'){
					alert("订单"+order_id+"请先退单确认");
					cando = false;
					return false;
				}
				if(bss_refund_status=='线下退款成功' || bss_refund_status=='线上退款成功'){
					alert("订单"+order_id+"已经退款成功");
					cando = false;
					return false;
				}
				orders.push(order_id);
			})
			if(cando){
				var url = ctx + "/shop/admin/orderFlowAction!showBackDesc.do?ajax=yes&orders="+orders.join(",")+"&query_type=refund";
				Eop.Dialog.open("backDesc");
				$("#backDescDialog").html("loading...");
				$("#backDescDialog").load(url,function(){});
			}
			
		})
		//查看订单详细
		$("a[name=inner_order_id]").bind("click",function(){		
			var $this = $(this);
			var order_id = $(this).attr("order_id");
			var query_type = '${query_type}';
			if(query_type!='order_view'){
				$.ajax({
					type : "post",
					async : false,
					url : "ordAuto!order_lock.do?ajax=yes&order_id="+order_id,
					data : {},
					dataType : "json",
					success : function(data) {
						if (data.result == "0") {
							$this.find("i.lock").attr("class", "unlock");
						} else {
							$this.find("i.unlock").attr("class", "lock");
						}
					}
				});	
			}
			
			var o_url= $(this).attr("detail_url");
			if(o_url && o_url.indexOf("?")!=-1){
				o_url += "&order_id="+order_id;
			}else{
				o_url += "?order_id="+order_id;
			}
			var url = ctx+"/"+o_url;
			window.location.href=url;
			
		});
	});
	
	
</script>
</body>
</html>