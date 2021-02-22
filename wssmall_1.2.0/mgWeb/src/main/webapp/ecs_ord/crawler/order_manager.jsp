<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="aito_order_return_f">
        <jsp:include page="supply_retry_params.jsp"/>
        </form>
        <div class="right_warp">
        <form action="" id="order_return_list_fm">
        	<grid:grid from="webpage" formId="aito_order_return_f" asynModel="1" >
			<grid:header>
				 <grid:cell style="text-align:center; width:8%;"></grid:cell> 
				<grid:cell  >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell width="25%">校验信息</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	     <grid:cell  >
		  	    	<%-- <i class="${order.orderTree.orderExtBusiRequest.lock_status=='1'?'unlock':'lock' }"></i> --%>
		  	    </grid:cell> 
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd">
												<a name="inner_order_id"
													order_id="${order.orderTree.order_id }"
													exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }"
													visible_status="${order.orderTree.orderExtBusiRequest.visible_status }"
													detail_url="${order.action_url }"
													href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }
												</a>
											</div></li>
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
                           	<p class="ps">终端：${order.terminal }</p>
                        </div>
		        </grid:cell> 
		       <grid:cell>
		       		<div class="order_pri">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold">${order.flow_trace }</p>
                            <p class="ps">${order.shipping_type }</p>
                        </div>
		       </grid:cell> 
		       <grid:cell>
		       		<c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi">
		        		<div class="order_pri">
                        	<p>ESS:${oi.orderItemExtBusiRequest.ess_pre_desc==null?'暂无':oi.orderItemExtBusiRequest.ess_pre_desc }</p>
                            <p>BSS:${oi.orderItemExtBusiRequest.bss_pre_desc==null?'暂无':oi.orderItemExtBusiRequest.bss_pre_desc }</p>
                        </div>
                     </c:forEach>
			        
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
<br />
<br />
<br />
<script type="text/javascript">
	$(function(){
		$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
		$("#order_return_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
		$("#order_return_list_fm .page").wrap("<form class=\"grid\"></form>");
		
		/*$("#order_return_list_fm div table tbody tr").unbind("click").bind("click",function(){
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
				//显示可见订单处理按钮
				// OrdBtns.showBtns(order_id,page_hide,"RETURNED");
			}
			
			$this.siblings("tr").removeClass("curr");
			$this.addClass("curr");
			
			$.ajax({
				type : "post",
				async : true,
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
		});*/
		//查看订单详细
		$("a[name=inner_order_id]").bind("click",function(){		
			try{
				if (event.stopPropagation) {
				    event.stopPropagation();
				 } else if (window.event) {
				    window.event.cancelBubble = true;
				 }
			}catch(e){
				
			}
			var order_id = $(this).attr("order_id");
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
	});
	
	
</script>
</body>
</html>