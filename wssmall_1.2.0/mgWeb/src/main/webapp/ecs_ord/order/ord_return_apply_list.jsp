<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
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
        	<input type="button" name="batch_back" query_type="${query_type }" class="comBtn" style="margin-right:10px;" value="批量退单"/>
        </div>
        <div class="right_warp">
        <form action="" id="order_return_list_fm">
        	<grid:grid from="webpage" formId="aito_order_return_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; width:100px;"><input type="checkbox" name = "checkbox_all" >状态</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >校验信息</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	    	<i class="${order.lock_clazz }"></i>
		  	    	<input type="checkbox" name="order_checkbox" order_id="${order.orderTree.order_id }" />
		  	    </grid:cell>
		        <grid:cell  style="width:300px;">
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell style="width:300px;">
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
		       <grid:cell style="width:80px;">
		       		<%-- <c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi">
		        		<div class="order_pri">
                        	<p title="${oi.orderItemExtBusiRequest.ess_pre_desc }">ESS:${order.ess_pre_desc==null?'暂无':order.ess_pre_desc }</p>
                            <p title="${oi.orderItemExtBusiRequest.bss_pre_desc }">BSS:${order.bss_pre_desc==null?'暂无':order.bss_pre_desc }</p>
                        </div>
                     </c:forEach> --%>
			        
		        </grid:cell>
		         <grid:cell style="width:200px;">
			         <div>
			        	<input type="button" name="back" class="comBtn" order_model="${order.orderTree.orderExtBusiRequest.order_model }" 
			        	order_id="${order.orderTree.order_id }" pay_type="${order.pay_type }" 
			        	order_amount="${order.orderTree.orderBusiRequest.order_amount } " query_type="${query_type }" style="margin-right:10px;" value="退单"/>
			        	<c:if test="${query_type =='returned_cfm' || query_type =='returned_cfmn'}">
				        	<input type="button" name="ignoreback" class="comBtn" order_model="${order.orderTree.orderExtBusiRequest.order_model }" 
				        	order_id="${order.orderTree.order_id }" pay_type="${order.pay_type }" 
				        	order_amount="${order.orderTree.orderBusiRequest.order_amount } " query_type="${query_type }" style="margin-right:10px;" value="驳回"/>
                  		  </c:if>
			      	</div>
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
		$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
		$("#order_return_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
		$("#order_return_list_fm .page").wrap("<form class=\"grid\"></form>");
		$("a[name='query_order_s']").remove();
		
		Eop.Dialog.init({id:"backDesc",modal:true,title:"退单说明",height:'400px',width:'550px'});
		/* $("#order_return_list_fm div table tbody tr").unbind("click").bind("click",function(){
			var $this = $(this);
			var obj = $this.find("div.dddd").children("a");
			var order_id = obj.attr("order_id");
			var exception_status = obj.attr("exception_status");
			var visible_status = obj.attr("visible_status");
			var page_hide = "list";
			if("1"==exception_status){
				page_hide += ",exception";
			}else{
				page_hide += ",detail";
			}
			if("1"!=visible_status){
				//显示可见订单处理按钮\
				var query_type = "${query_type}" ;
				var btnId = "RETURNED";
				if(query_type=="returned_cfm"){
					btnId = "RETURNEDCONFIRM";
				}else{
					OrdBtns.showBtns(order_id,page_hide,btnId);
				}
			}else{
				OrdBtns.removeMenu();
			}
			
			
			$this.siblings("tr").removeClass("curr");
			$this.addClass("curr");
			
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
		}); */
		//查看订单详细
		$("a[name=inner_order_id]").bind("click",function(){		
			/* try{
				if (event.stopPropagation) {
				    event.stopPropagation();
				 } else if (window.event) {
				    window.event.cancelBubble = true;
				 }
			}catch(e){
				
			} */
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
			//var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
			window.location.href=url;
			
		});
		//查询
		$("#query_order_s").bind("click",function(){
			$("#aito_order_return_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do");
		});
		
		//复选框全选
		$("input[name='checkbox_all']").unbind("click").bind("click", function() {
			if($(this).is(':checked')) {
				$("input[name='order_checkbox']").attr("checked", true);
			} else {
				$("input[name='v']").attr("checked", false);
			}
		});
		
		//单个退单
		$("input[name='back']").unbind("click").bind("click", function() {
			var orders = $(this).attr("order_id");//单号
			var order_model = $(this).attr("order_model");
			var pay_type = $(this).attr("pay_type");
			var order_amount = $(this).attr("order_amount");
			var query_type = $(this).attr("query_type");
			var model_flag = true;
			var pay_flag = true;
			var amount_flag = true;
			var show_flag = "0";
			if(query_type=='returned'){//退单申请页面
				
			}else if(query_type=='returned_cfm'){//退单处理
				//单据生产模式不为07,08、付款方式为货到付款、价格为0的不显示退款下拉框
				if(order_model=='07' || order_model=='08'){
					model_flag = true;
				}else{
					model_flag = false;
				}
				
				if(pay_type=='HDFK'){
					pay_flag = false;
				}
				
				if(order_amount==0){
					amount_flag = false;
				}
				
				if(model_flag&&pay_flag&&amount_flag){
					show_flag = "1";//可以显示退款下拉
				}
			}
			
			if($(this).parents("tr").find("[name='order_checkbox']").is(':checked')){
				var url = ctx + "/shop/admin/orderFlowAction!showBackDesc.do?ajax=yes&orders="+orders+"&show_flag="+show_flag+"&query_type="+query_type;
				Eop.Dialog.open("backDesc");
				$("#backDescDialog").html("loading...");
				$("#backDescDialog").load(url,function(){});
			}else{
				alert("请先勾选该订单!");
				return;
			}

		});
		
		//单个驳回
		$("input[name='ignoreback']").unbind("click").bind("click", function() {
			var orders = $(this).attr("order_id");//单号
			var query_type = $(this).attr("query_type");
			 if($(this).parents("tr").find("[name='order_checkbox']").is(':checked')){
				 $.ajax({
				     	url:ctx +"shop/admin/ordReturned!cancelReturned.do?ajax=yes&order_id="+orders,
				     	type: "POST",
				     	dataType:"json",
				     	async:false,
				     	success:function(reply){
				     		if(typeof(reply) != "undefined"){
				     			if("0" == reply["status"]){
				     				alert("驳回成功");
				     				if(query_type=='returned_cfm'){//退单处理
										var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned_cfm";
										window.location.href = url; 
									}else if(query_type=='returned_cfmn'){
										var url = ctx+"/shop/admin/ordAuto!showOrderList.do?query_type=returned_cfmn";
										window.location.href = url; 
									}
					     		}else{
					     			var msg = reply["msg"];
				     				alert(msg);
					     		}
				     		}
				     	},
					});
				}else{
					alert("请先勾选该订单!");
					return;
				}
	
		});
		
		//批量退单
		$("input[name='batch_back']").unbind("click").bind("click", function() {
			var query_type = $(this).attr("query_type");
			var len = $("input[type='checkbox'][name='order_checkbox']:checked").length;
			if(len==0){
				alert("请勾选需要退单的订单!")
				return;
			}
			var orders = '';
			var show_flag = "0";
			if(query_type=='returned'){//退单申请页面
				$("input[type='checkbox'][name='order_checkbox']:checked").each(function(index) {
					var order_id = $(this).attr("order_id");//单号
					orders += order_id+",";
				});
				
			}else if(query_type=='returned_cfm'){//退单处理
				var orders = '';
				var can_show_list = [];//用于判断勾选的单据能否显示退款下拉，是否存在冲突
				var show_flag = "0";
				$("input[type='checkbox'][name='order_checkbox']:checked").each(function(index) {
					var order_id = $(this).attr("order_id");//单号
					var order_model = $(this).parents("tr").find("[name='back']").attr("order_model");
					var pay_type = $(this).parents("tr").find("[name='back']").attr("pay_type");
					var order_amount = $(this).parents("tr").find("[name='back']").attr("order_amount");
					
					var model_flag = true;
					var pay_flag = true;
					var amount_flag = true;
					
					//单据生产模式不为07,08、付款方式为货到付款、价格为0的不显示退款下拉框
					if(order_model=='07' || order_model=='08'){
						model_flag = true;
					}else{
						model_flag = false;
					}
					
					if(pay_type=='HDFK'){
						pay_flag = false;
					}
					
					if(order_amount==0){
						amount_flag = false;
					}
					
					if(model_flag&&pay_flag&&amount_flag){
						show_flag = "1";//可以显示退款下拉
					}
					
					can_show_list.push(show_flag);
					orders += order_id+",";
					
				});
				
				var arr_order = orders.split(",");
				
				for(var i=0;i<can_show_list.length;i++){
					var a = can_show_list[i];
					var b = can_show_list[i+1];
					var order_id = arr_order[i];
					if(b==null || b=='' || b==undefined){
						break;
					}
					if(a==b){
					}else{
						alert("内部订单号为："+order_id+"的订单与其他勾选的单据是否存在退款功能有冲突，请重新勾选!");
						return;
					}
				}
			}
			
			var url = ctx + "/shop/admin/orderFlowAction!showBackDesc.do?ajax=yes&orders="+orders+"&show_flag="+show_flag+"&query_type="+query_type;
			Eop.Dialog.open("backDesc");
			$("#backDescDialog").html("loading...");
			$("#backDescDialog").load(url,function(){});
			
		});
	});
	
	
</script>
</body>
</html>