<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单列表(历史)</title>
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
           <jsp:include page="params.jsp?exception_type=all" />
        </form>
	<table class="tab_form" id="input_area" display="hide">
		<tr>
			<td width="35%">请输入需要恢复的外部订单号<br>（订单号与订单号之间用英文逗号','分隔）：</td>
			<td style="width:100px;"><textarea 
					rows="5" cols="50" id="order_ids" name="order_ids"></textarea>
			</td>
			<td><a href="javascript:void(0);" id="hide_table" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隐&nbsp;&nbsp;&nbsp;&nbsp;藏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
		</tr>
	</table>
        <div class="right_warp">
        <form action="" id="order_list_fm">
        	<grid:grid from="webpage" formId="aito_order_f" asynModel="1">
			<grid:header>
				<grid:cell style="text-align:center; width:8%;"></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell width="25%">校验信息</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	    	 
		  	    	
		  	    </grid:cell>
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span>${order.orderTree.orderExtBusiRequest.out_tid }</li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            	<c:if test="${order.orderTree.orderExtBusiRequest.archive_type=='4'}">
                            	<li><div class="dddd"><a name="inner_order_id" class="dobtn" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" href="javascript:void(0);">数据恢复</a></div></li>
                            	</c:if>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell>
		        		<div class="order_pri">
                        	<p class="tit">${order.goods_package }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<c:if test="${order.terminal!=''}"><p class="ps">终端：${order.terminal }</p></c:if>
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
		       		<c:choose>
		      			<c:when test="${order.refund_status != '正常单'}">
	       					<p class="ps" style="color: red;">退单状态：${order.refund_status }</p>
	       					<c:if test="${order.pay_type != 'HDFK'}">
	       						<p class="ps" style="color: red;">退款状态：${order.bss_refund_status }</p>
			       			</c:if>
		      			</c:when>
		      			<c:otherwise>
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
		      			</c:otherwise>
		      		</c:choose>
                    <c:if test="${order.orderTree.orderExtBusiRequest.lock_user_id != 'NULL_VAL' 
                   			&& order.orderTree.orderExtBusiRequest.lock_user_id != ''}">
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
                     <c:if test="${params.order_is_his == '1'}">
                            <p class="ps">归档类型:${order.archive_type }</p>
                     </c:if>
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
			var expType = obj.attr("exptype");
			if(!expType)expType="0";
			
			var page_hide = "list,expType"+expType;
			if("1"==exception_status){
				page_hide += ",exception";
			}
			var q_type = "${query_type}";
			if("1"!=visible_status || "ycl"==q_type){
				//显示可见订单处理按钮
				var btn = "";
				if("ycl"==q_type)btn="ycl";
			}
			
			$this.siblings("tr").removeClass("curr");
			$this.addClass("curr");
		});
		$("#input_area").hide();
		//订单恢复
		$("a[name=inner_order_id]").bind("click",function(){
			var $this = $(this);
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
			if(o_url==''||o_url==null||o_url=='undefined'){
				var resultUrl =  AutoFlow.getActionUrl(order_id);
				var resultObj = resultUrl.split("|");
				if(resultObj[0]==0){
					o_url = resultObj[1];	
				}else{
					alert(resultObj[1]);
					return false;
				}
			}
			if(o_url && o_url.indexOf("?")!=-1){
				o_url += "&ajax=yes";
			}else{
				o_url += "?ajax=yes";
			}
			var url = ctx+"/"+o_url;

			$.ajax({
            url:url,
            data:{order_id:order_id},
            type:'post',
            dataType:'json',
            success:function(data){
            	alert(data.message);
            	if(data.result=='0000'){//恢复成功则不再可操作
            		$this.html("此单已恢复");
        			$this.unbind().bind("click",function(){
            			alert("此单已恢复，请到订单处理页面进行处理");
            		});
            	}
            },
			error : function(e) {
				$.Loading.hide();
				alert("操作失败，请重试"+e);
			}
        	})
		});
		
		$("#orders_recovery").bind("click",function(){
			var table = $("#input_area");
			var view = table.attr("display");
			if(view=="hide"){
				table.show();
				table.attr("display","show");
			}else{
				var url = ctx + "/shop/admin/ordAuto!ordRecoverByInp.do?ajax=yes";
				var order_ids = $("#order_ids").val();
				if (order_ids == "") {
					alert("请输入外部订单号！");
					return;
				}
				$.ajax({
		            url:url,
		            data:{order_ids:order_ids},
		            type:'post',
		            dataType:'json',
		            success:function(data){
		            	alert(data.message);
		            },
					error : function(e) {
						$.Loading.hide();
						alert("操作失败，请重试"+e);
					}
		        })
			}
		});
		
		$("#hide_table").bind("click",function(){
			$("#input_area").hide();
			$("#input_area").attr("display","hide");
		})
	});
	

	
	function selectObj (cobj){
		/* alert("selectObj"); */
		var url = ctx+"/shop/admin/ordAuto!entrust.do?ajax=yes";
		var name="";
		var obj = $("input[id='user_name']");
		if(obj && obj.val()){
			name = obj.val();
		}
		$("#order_btn_event_dialog").load(url,{obj_name:name},function(){
			$("#search_btn").bind("click",function(){selectObj(cobj);});
			$("#obj_select_confirm_btn").bind("click",function(){addSelectObj(cobj);});
		});
	}
	function addSelectObj (cobj){
		var order_id = cobj.attr("order_id");
		//alert("进入方法");
		var obj_id = $("input[type='radio'][name='radionames']:checked").val();
		if(obj_id == undefined){
			alert("请选择一个对象");
			return ;
		}
		var obj_ids = obj_id.split("##");
		var uid = obj_ids[0]!=undefined?$.trim(obj_ids[0]):"";
		var una = obj_ids[1]!=undefined?$.trim(obj_ids[1]):"";
		var rna = obj_ids[2]!=undefined?$.trim(obj_ids[2]):"";
		$.ajax({
			type : "post",
			async : true,
			url : "ordAuto!entrust_save.do?ajax=yes&order_id="+order_id+"&userid="+uid+"&username="+una+"&realname="+rna,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.message);
			}
		});
		Eop.Dialog.close("order_btn_event_dialog");
	}
	
	function checkData() {
		return true;
	}
	function callbackData(e) {
		var $this = $("a[name=o_entrust]");
		selectObj($this);
	}
</script>
</body>
</html>