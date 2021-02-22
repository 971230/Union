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
		<form action="" method="post" id="aito_order_f">
			 <input type="hidden" name="btnType" value="ordList"/>
			 <input type="hidden" name="query_type" value="${query_type }" />
			 <input type="hidden" name="params.query_btn_flag" value="yes" />
        <%-- <jsp:include page="params.jsp?exception_type=0"/>--%>
        	<div class="searchBx">
        		<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
        			<tbody>
        			 	<tr>
		                   <th>领取条形码：</th>
			                <td>
			                	<input type="text" name="params.order_sn" value="${params.order_sn }" style="width:138px;" class="ipt_new">
			                </td>
			                <th>终端条形码：</th>
			                <td><input type="text" name="terminal_num" value="" style="width:138px;" class="ipt_new"></td>
			                
			                <th>ICCID：</th>
			                <td><input type="text" name="params.iccid" value="${params.iccid }" style="width:138px;" class="ipt_new">
			                </td>
			                <td id="result_tip" style="color:blue"></td>
			            </tr>
        			</tbody>
        		</table>
        	</div>
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">
           
        	<grid:grid from="webpage" formId="aito_order_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; width:100px;"><c:if test="${query_type=='order' }">状态<input type="checkbox" id="checkAlls" /></c:if></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >收货信息</grid:cell>
				<grid:cell width="10%">校验信息</grid:cell>
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
                            	<li><span>外部编号：</span><div class="dddd"><%-- <a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" sq="${order.orderTree.orderExtBusiRequest.shipping_quick }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a> --%>${order.orderTree.orderExtBusiRequest.out_tid }</div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            	<li><span>支付方式：</span><div>${order.pay_method }</div></li>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell>
		        		<div class="order_pri">
                        	<p class="tit">${order.goods_package }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<p class="ps">终端：${order.terminal }</p>
                           	<%-- <p class="ps">业务办理：${order.bss_status=='1'?'已办理':'未办理' }</p> --%>
                           	<p class="ps">手机型号：${order.specificatio_nname }</p>
                            <p class="ps">活动：${order.activity_name }</p>
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
		       <grid:cell style="width:300px;">
		        <div class="list_t">
                        	<ul>
                            	<li><span>用户姓名：</span><div>${order.ship_name }</div></li>
                            	<li><span>联系方式：</span><div>${order.receiver_mobile }</div></li>
                            	<li><span>收货地址：</span><div>${order.ship_addr }</div></li>
                            	
                            </ul>
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
		Eop.Dialog.init({id:"dealTipDialog",modal:false,title:"提示框",width:'450px'});
		
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
			
			var o_url= $this.attr("detail_url");
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
				o_url += "&order_id="+order_id+"&query_type=${query_type}";
			}else{
				o_url += "?order_id="+order_id+"&query_type=${query_type}";
			}
			var url = ctx+"/"+o_url;
			//var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
			window.location.href=url;
			
		});
		//号卡条形码输入框监听键盘回车键
		$("input[name='params.order_sn']").bind('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				$.Loading.show("正在加载所需内容，请稍侯...");
				$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do?").submit();
			}
		});
		$("input[name='params.iccid']").bind('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				$.Loading.show("正在加载所需内容，请稍侯...");
				$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do?").submit();
			}
		});
		//令输入框获得焦点
		if($("input[name='params.order_sn']").val() != ''){
			$("input[name='terminal_num']").focus();
		}else{
			$("input[name='params.order_sn']").focus();
		}
		//终端串号输入框监听键盘回车键
		$("input[name='terminal_num']").bind('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				if($("input[name='params.order_sn']").val() == '' ){
					alert("请先扫描领取条形码，然后再扫描终端条形码！");
				}else{
					$.Loading.show("正在处理，请稍侯...");
					//匹配订单上输入的终端串号和终端实体上的是否一致
					var order_sn = $("input[name='params.order_sn']").val();
					var terminal_nums = $("input[name='terminal_num']").val();
					//直接比对两个条形码
					if(order_sn == terminal_nums){
						 //执行发货方案，订单流转
						 $("#result_tip").css("color","blue");
						 $("#result_tip").text("校验成功");
						 callPlan();
					}else{
						//alert("校验失败，领取条形码和终端条形码不一致！");
					 	 $("#result_tip").css("color","red");
					 	 $("#result_tip").text("校验失败，领取条形码和终端条形码不一致！");
						 $("input[name='params.order_sn']").val("");
						 $("input[name='terminal_num']").val("");
						 $("input[name='params.iccid']").val("");
						 $("input[name='params.order_sn']").focus();
						 $.Loading.hide();
					}
				}
			}
		});
	});
</script>
<script type="text/javascript">
 /* 异步检查预警信息*/
$(document).ready(function() {
	callPlan();
});
function getBrowserVersion(){
	var userAgent = navigator.userAgent.toLowerCase();   
	var isOpera = userAgent.indexOf("opera") > -1;
	if (isOpera) {
        return "Opera"
    }
    if (userAgent.indexOf("firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("chrome") > -1){
    	return "Chrome";
   	}
	if(userAgent.match(/msie ([\d.]+)/)!=null){//ie6--ie9                
		uaMatch = userAgent.match(/msie ([\d.]+)/);                
		return 'IE'+uaMatch[1];        
	}else if(userAgent.match(/(trident)\/([\w.]+)/)){    
		uaMatch = userAgent.match(/trident\/([\w.]+)/);    
		switch (uaMatch[1]){                                
		case "4.0": return "IE8" ;
		break;                                
		case "5.0": return "IE9" ;
		break;                                
		case "6.0": return "IE10";
		break;                                
		case "7.0": return "IE11";
		break;                                
		default:return "undefined" ;            
		}  
	}       
	return "undefined";  
}
function callPlan(){
	var sn = $("input[name='params.order_sn']").val();
	var iccid = $("input[name='params.iccid']").val();
	var ternum = $("input[name='terminal_num']").val();
	var ordnum = $("#order_list_fm tbody tr").length;
	if((sn && !ternum && ordnum==1)||(iccid && !ternum && ordnum==1)){
		var tr = $("#order_list_fm tbody tr");
		var ordid = $("input[name='orderids']",tr).val();
		$.ajax({
			type : "post",
			async : false,
			url : ctx + "/shop/admin/ordAuto!checkFh.do?ajax=yes",
			data : {
				"order_id" : ordid
			},
			dataType : "json",
			success : function(result) {
				var has_terminal = result.has_terminal;
				if(has_terminal == "0"){
					showDevPrint(ordid);
					exeFhPlan(ordid);
				}
				else{
					$("#result_tip").css("color","blue");
					$("#result_tip").text(result.message);
				}
				$.Loading.hide();
			},
			error : function(){
				$.Loading.hide();
			}
		});
	}
	else if(sn && ternum && ordnum==1){
		var tr = $("#order_list_fm tbody tr");
		var ordid = $("input[name='orderids']",tr).val();
		showDevPrint(ordid);
		exeFhPlan(ordid);
	}
	else{
		$.Loading.hide();
	}
}
 
 function showDevPrint(order_id){	
		 $.ajax({
				type : "post",
				async : false,
				url : ctx + "/shop/admin/orderPostPrintAction!doAddHotFree.do?ajax=yes",
				data : {
					"order_id" : order_id
				},
				dataType : "json",
				success : function(data) {
					 $.Loading.hide();		 
					 if(data.result=='0'){
							var printUrl=ctx+'/shop/admin/orderPostModePrint!hotFreePostModelNew.do?ajax=yes&order_id='+order_id+'&delvery_print_id='+data.delveryPrintId+
									'&itmesIds='+data.itmesIds+'&postId='+data.logi_company+'&wlnum='+data.logi_no+'&post_type=0';
							var printRe=window.open(printUrl,'','dialogHeight=630px;dialogWidth=960px');		
						    if(data.is_signback=='1'){				
								var rePrintUrl=ctx+'/shop/admin/orderPostModePrint!hotFreePostModelNew.do?ajax=yes&order_id='+order_id+'&delvery_print_id='+data.delveryPrintId+
								'&itmesIds='+data.itmesIds+'&postId='+data.logi_company+'&wlnum='+data.logi_no+'&post_type=0&is_receipt=1';
							    printRe=window.open(rePrintUrl,'','dialogHeight=630px;dialogWidth=960px');		
							}
							//closeDialog();
					 }else{
							alert(data.message);
					 }
					 $.Loading.hide();
				},
				error : function(){
					$.Loading.hide();
				}
		});
 }
 
 
 //执行发货方案
 function exeFhPlan(order_id){
	 $.Loading.show("订单正在发货处理，请稍侯...");
	 $.ajax({
			type : "post",
			async : false,
			url : ctx + "/shop/admin/ordAuto!exeFhPlan.do?ajax=yes",
			data : {
				"order_id" : order_id
			},
			dataType : "json",
			success : function(result) {
				 $.Loading.hide();		 
				 if(result.result=='0'){
					 $("#result_tip").css("color","blue");
					 $("#result_tip").text(result.message);
					 $("input[name='params.order_sn']").val("");
					 $("input[name='terminal_num']").val("");
					 $("input[name='params.iccid']").val("");
					 $("input[name='params.order_sn']").focus();
					 /* $("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do?").submit(); */
				 }else{
				 	 alert(result.message);
				 	 $("#result_tip").css("color","red");
				 	 $("#result_tip").text("校验成功，但发货处理失败");
				 	 $("input[name='params.order_sn']").val("");
					 $("input[name='terminal_num']").val("");
					 $("input[name='params.iccid']").val("");
					 $("input[name='params.order_sn']").focus();
				 }
			},
			error : function(){
				$.Loading.hide();
			}
	});
 }
 
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