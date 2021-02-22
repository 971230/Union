<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>外呼处理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="aito_order_f">
		 <input type="hidden" name="btnType" value="ordList"/>
        <jsp:include page="callOutParams.jsp?exception_type=0"/>
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">
           
        	<grid:grid from="webpage" formId="aito_order_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; width:100px;"><c:if test="${query_type=='order' }">状态<input type="checkbox" id="checkAlls" /></c:if></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell width="12%">校验信息</grid:cell>
				<grid:cell width="13%">操作</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	         <i  title=""  class=""  id="warning_li_${order.orderTree.order_id }"  ></i>
		  	          <input type="hidden" name="orderids" value="${order.orderTree.order_id }" />
		  	    	 <i  title="${order.orderTree.orderExtBusiRequest.lock_user_name}"  class="${order.lock_clazz }"  ></i>
		  	    	 <c:if test="${query_type=='order' }"><input type="checkbox" name="orderidArray" value="${order.orderTree.order_id }" /></c:if>
		  	    </grid:cell>
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><a name="inner_order_id" lock_clazz="${order.lock_clazz }" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" sq="${order.orderTree.orderExtBusiRequest.shipping_quick }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell style="width:420px;">
		        		<div class="order_pri">
                        	<p class="tit">${order.goods_name }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<p class="ps">终端：${order.terminal }</p>
                           	<p class="ps">业务办理：${order.bss_status=='1'?'已办理':'未办理' }</p>
                        </div>
		        </grid:cell> 
		       <grid:cell style="width:100px;">
		       		<div class="order_pri">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold">${order.flow_trace }</p>
                            <p class="ps">${order.shipping_type }</p>
                            <p class="ps">外呼原因：${order.oper_remark }</p>
                        </div>
		       </grid:cell> 
		       <grid:cell style="width:200px;">
	       			<p class="ps">
	       			<c:if test="${order.orderTree.orderExtBusiRequest.abnormal_type == '1' }">
	       			人工标记异常
	       			</c:if>
	       			<c:if test="${order.orderTree.orderExtBusiRequest.abnormal_type == '2' }">
	       			系统异常
	       			</c:if>
	       			<c:if test="${order.orderTree.orderExtBusiRequest.abnormal_type == '3' }">
	       			自动化异常
	       			</c:if>
	       			<c:if test="${order.orderTree.orderExtBusiRequest.abnormal_type == '0'}">
	       			正常单
	       			</c:if>
	       			</p>
                   <c:if test="${order.orderTree.orderExtBusiRequest.lock_user_name != 'NULL_VAL' 
                   			&& order.orderTree.orderExtBusiRequest.lock_user_name != ''}">
                   	<p class="ps">${order.orderTree.orderExtBusiRequest.lock_user_name }</p>
                   </c:if>
		       		<c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi" varStatus="orderItem">
		        		<c:if test="${orderItem.index==0}">
		        		<div class="order_pri">
                        	<p title="${oi.orderItemExtBusiRequest.ess_pre_desc }">ESS:${order.ess_pre_desc==null?'暂无':order.ess_pre_desc }</p>
                            <p title="${oi.orderItemExtBusiRequest.bss_pre_desc }">BSS:${order.bss_pre_desc==null?'暂无':order.bss_pre_desc }</p>
                        </div>
                        </c:if>
                     </c:forEach>
			        
                    <c:choose>
                        <c:when test="${order.orderTree.orderExtBusiRequest.if_Send_Photos == '1'}">
       					<p class="ps" style="color: green;"><img src="<%=request.getContextPath()%>/ecs_ord/order/images/correct.png" style="vertical-align:middle; margin-right:5px;"/>证件已上传</p>
       				</c:when>
                         	<c:when test="${order.orderTree.orderExtBusiRequest.if_Send_Photos == '2'}">
       					<p class="ps" style="color: green;"><img src="<%=request.getContextPath()%>/ecs_ord/order/images/correct.png" style="vertical-align:middle; margin-right:5px;"/>证件已重新上传</p>
       				</c:when>
                         	<c:when test="${order.orderTree.orderExtBusiRequest.if_Send_Photos == '9'}">
       					<p class="ps" style="color: green;"><img src="<%=request.getContextPath()%>/ecs_ord/order/images/correct.png" style="vertical-align:middle; margin-right:5px;"/>证件无需上传</p>
       				</c:when>
                         	<c:when test="${order.orderTree.orderExtBusiRequest.if_Send_Photos == '0'}">
       					<p class="ps" style="color: red;"><img src="<%=request.getContextPath()%>/ecs_ord/order/images/error.png" style="vertical-align:middle; margin-right:5px;"/>证件未上传</p>
       				</c:when>
                         	<c:when test="${order.orderTree.orderExtBusiRequest.if_Send_Photos == '8'}">
       					<p class="ps" style="color: red;"><img src="<%=request.getContextPath()%>/ecs_ord/order/images/error.png" style="vertical-align:middle; margin-right:5px;"/>证件等待重新上传</p>
       				</c:when>
	      			<c:otherwise>
	      						      				
	      			</c:otherwise>
	      			</c:choose>
		        </grid:cell>
		        <grid:cell style="width:200px;">
		       		<div >
                            <button type="button" class="call_out" lock_clazz="${order.lock_clazz }" order_id="${order.orderTree.order_id }">外呼处理</button>
                        </div>
		       </grid:cell> 
		  </grid:body>
      	</grid:grid>
      	<input type="hidden" id="order_id_hidden" />
      	<input type="hidden" id="isListBtn" value="true" />
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
		$("#checkAlls").bind("click",function(){
			$("input[type=checkbox][name=orderidArray]").trigger("click");
		});
		$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
		$("#order_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
		$("#order_list_fm .page").wrap("<form class=\"grid\"></form>");
		$("#order_list_fm div table tbody tr").unbind("click").bind("click",function(){
			var $this = $(this);
			var obj = $this.find("div.dddd").children("a");
			var order_id = obj.attr("order_id");
			var exception_status = obj.attr("exception_status");
			var visible_status = obj.attr("visible_status");
			var sq = obj.attr("sq");
			if(!sq)sq="01";
			var expType = obj.attr("exptype");
			if(!expType)expType="0";
			
			
			var page_hide = "list,expType"+expType+","+sq;
			if("1"==exception_status){
				page_hide += ",exception";
			}/* else{
				page_hide += ",detail";
			} */
			var q_type = "${query_type}";
			if("1"!=visible_status || "ycl"==q_type){
				//显示可见订单处理按钮
				var btn = "";
				if("ycl"==q_type)btn="ycl";
				//OrdBtns.showBtns(order_id,page_hide,btn);
			}
			
			$this.siblings("tr").removeClass("curr");
			$this.addClass("curr");
			/* 点击tr时，暂时不需要锁定
			//$this.find("i.lock").attr("class", "unlock");
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
			 */
			$.ajax({
				type : "post",
				async : false,
				url : "ordReturned!returnedApplyFromTaobao.do?ajax=yes&order_id="+order_id,
				data : {},
				dataType : "json",
				success : function(data) {
					if (data.result == "0") {
						window.location.reload();
					}
				}
			});
		});
		//外呼处理
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
			var lock_clazz = $(this).attr("lock_clazz");
			if(lock_clazz!='unlock'){
				alert("此订单已被其他外呼人员处理!");
				return;
			}
			//$this.find("i.lock").attr("class", "unlock");
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
			
			
			var resultUrl =  AutoFlow.getActionUrl(order_id);
			var resultObj = resultUrl.split("|");
			if(resultObj[0]==0){
				o_url = resultObj[1];	
			}else{
				alert(resultObj[1]);
				return false;
			}

			if(o_url && o_url.indexOf("?")!=-1){
				o_url += "&order_id="+order_id+"&query_type=${query_type}";
			}else{
				o_url += "?order_id="+order_id+"&query_type=${query_type}";
			}

			var url = ctx+"/"+o_url;
			
			//var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
			window.location.href=url;
			
		});

		$(".call_out").bind("click",function(){	
		
			var $this = $(this);
			var order_id = $(this).attr("order_id");
			var lock_clazz = $(this).attr("lock_clazz");
			if(lock_clazz!='unlock'){
				alert("此订单已被其他外呼人员处理!");
				return;
			}
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
			
			/* var resultUrl =  AutoFlow.getActionUrl(order_id);
			var resultObj = resultUrl.split("|");
			if(resultObj[0]==0){
				o_url = resultObj[1];	
			}else{
				alert(resultObj[1]);
				return false;
			} */
			o_url = "shop/admin/orderFlowAction!preDealOrd.do";
			if(o_url && o_url.indexOf("?")!=-1){
				o_url += "&order_id="+order_id+"&query_type=${query_type}";
			}else{
				o_url += "?order_id="+order_id+"&query_type=${query_type}";
			}
			var url = ctx+"/"+o_url;
			//var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
			window.location.href=url;
			
		});
		
		
	});
</script>
<script type="text/javascript">
 /* 异步检查预警信息*/
 $(document).ready(function() { 
	 queryWarning();
	
});
 
  function queryWarning(){
	  //获取
	  var orderids="";
	  $("input[name='orderids']").each(function(i){
		  var order_id = $(this).val();
			if (i== 0){
				orderids +=order_id;
			}else{
				orderids += ","+order_id;
			}
		  });
	 
	  if(orderids!=""){
		  $.ajax({
				type : "post",
				async : false,
				url : "ordAuto!order_warning.do?ajax=yes",
				data : {
					"order_ids" : orderids
				},
				dataType : "json",
				success : function(data) {
					 if(data.result=='0'){
						 var datalist=data.list;
						 for ( var i = 0; i < datalist.length; i++) {
								var owResult = datalist[i];
								var order_id=owResult.order_id;
								var warning_colo=owResult.warning_colo;
								var out_time=owResult.out_time;
								var run_time=owResult.run_time;
								var titleMsg="当前环节已开始"+run_time+"分钟，超时"+out_time+"分钟！";
								$("#warning_li_"+order_id).addClass(warning_colo); 
								$("#warning_li_"+order_id).attr("title",titleMsg);
						
							}
					 }
					
				}
			});
		  }
		
  };

 
</script>
</body>
</html>