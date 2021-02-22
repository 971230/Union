<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>物流单打印</title>
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
        <jsp:include page="params.jsp?exception_type=0"/>
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">
           
        	<grid:grid from="webpage" formId="aito_order_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; width:100px;"><c:if test="${query_type=='order' }">状态<input type="checkbox" id="checkAlls" /></c:if></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >物流信息</grid:cell>
				<grid:cell >物流打印</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	         <%-- <i  title=""  class=""  id="warning_li_${order.orderTree.order_id }"  ></i>
		  	          <input type="hidden" name="orderids" value="${order.orderTree.order_id }" />
		  	    	 <i  title="${order.orderTree.orderExtBusiRequest.lock_user_name}"  class="${order.lock_clazz }"  ></i>
		  	    	 <c:if test="${query_type=='order' }"><input type="checkbox" name="orderidArray" value="${order.orderTree.order_id }" /></c:if> --%>
		  	    </grid:cell>
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><%-- <a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" sq="${order.orderTree.orderExtBusiRequest.shipping_quick }" href="javascript:void(0);"> --%>${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
                        <div class="order_pri">  
                        	<p class="tit">${order.goods_package }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<p class="ps">终端：${order.terminal }</p>
                        </div>
		        </grid:cell>
		        <grid:cell>
		        		 <div class="order_pri">  
                        	<p class="tit">收件人：${order.orderTree.orderDeliveryBusiRequests[0].ship_name }</p>
                       		<p class="ps">联系电话：${order.orderTree.orderDeliveryBusiRequests[0].ship_mobile }</p>
                           	<p class="ps">收件人地址：${order.orderTree.orderDeliveryBusiRequests[0].ship_addr }</p>
                           <%-- 	<p class="ps">选择物流类型：
                               <c:forEach var="logicompany" items="${logiCompanyList }">
										<c:if test="${logicompany.id==shipping_company}">${logicompany.name }</c:if>
								</c:forEach>
                             </p> --%>
                        </div>
		        </grid:cell> 
		        <grid:cell>
		        		<input type="button"  class="dobtn" name="logi_no_bt" id="logi_no_bt_${order.orderTree.order_id }" value="物流单打印" order_id="${order.orderTree.order_id }"/><br></br>
		        		<input type="button"  class="dobtn" name="print_hot_free" id="print_hot_free" value="物流热敏单打印" order_id="${order.orderTree.order_id }"/><br></br>
		        		<input type="button"  class="dobtn" name="num_card_hot_free" id="num_card_hot_free" value="号卡领取热敏单打印" order_id="${order.orderTree.order_id }"/><br></br>
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
<div id="order_btn_event_dialog"></div>

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
	});
</script>
<script type="text/javascript">
 /* 异步检查预警信息*/
 $(document).ready(function() { 
	// queryWarning();
	
	 initBt();
	 
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


  function initBt(){
	  $("input[name=logi_no_bt]").unbind("click").click(function(){
		  $("#order_btn_event_dialog").empty();
			 printLogiNo($(this).attr("order_id"));
		});
	  
	  $("input[name=print_hot_free]").unbind("click").click(function(){//物流热敏单打印
		  $("#order_btn_event_dialog").empty();
		  var order_id = $(this).attr("order_id");
		  logisticsprinthotfree(order_id);
	  });
	  
	  $("input[name=num_card_hot_free]").unbind("click").click(function(){//号卡领取热敏单打印
		  $("#order_btn_event_dialog").empty();
		  var order_id = $(this).attr("order_id");
		  numcardhotfree(order_id);
	  }); 
  }
  
  //物流热敏单打印
  function logisticsprinthotfree(order_id){	     
		var ac_url = app_path+"/shop/admin/orderPostPrintAction!doGetHotFreeModel.do?ajax=yes&order_id="+order_id+"&post_type=0";
		Eop.Dialog.open("order_btn_event_dialog");
		$("#order_btn_event_dialog").load(ac_url,function(responseText){
		});
}
 
  //号卡领取热敏单打印
    function numcardhotfree(order_id){	     
		var ac_url = app_path+"/shop/admin/orderPostPrintAction!doGetHotFree.do?print_type=1&ajax=yes&order_id="+order_id+"&post_type=0";
		Eop.Dialog.open("order_btn_event_dialog");
		$("#order_btn_event_dialog").load(ac_url,function(responseText){
		});
}
  
  //物流单打印
  function printLogiNo(order_id){
	  var ac_url = app_path+"/shop/admin/orderPostPrintAction!doGetPiritModelNew.do?ajax=yes&order_id="+order_id+"&post_type=0";	
		                       
		Eop.Dialog.open("order_btn_event_dialog");
		$("#order_btn_event_dialog").load(ac_url,function(responseText){
		});
/* 		var options = {
				type : "post",
				url : ac_url,
				dataType : "html",
				success : function(result) {
					$("#order_btn_event_dialog").empty().append(result);
					if(callcack_fn)callcack_fn(result);
				},
				error : function(e,b) {
					alert("处理失败，请重试!");
				}
			} */
/* 			if(form_id){
				var form = $("#"+form_id);
				if(form && form.length>0){
					form.ajaxSubmit(options);
				}else{
					var form = $("form");
					if(form && form.length>0){
						$(form[0]).ajaxSubmit(options);
					}else{
						$("#order_btn_event_dialog").load(ac_url,function(responseText){
							if(callcack_fn)callcack_fn(responseText);
						});
					}
				}
			}else{
				var form = $("form");
				if(form && form.length>0){
					$(form[0]).ajaxSubmit(options);
				}else{
					$("#order_btn_event_dialog").load(ac_url,function(responseText){
						if(callcack_fn)callcack_fn(responseText);
					});
				}
			}	   */
	  
  };


	function btnEvent(){
		var $this = $(this);
		var checkFn = $this.attr("check_fn");
		var order_id = $this.attr("order_id");
		var form_id = $this.attr("form_id");
		if(checkFn){
			var flag = eval(checkFn)(order_id,form_id);
			if(!flag){
				//alert("数据验证没有通过，请检查必填的选项!");
				return ;
			}
		}
		var callcack_fn = $this.attr("callcack_fn");
		if(callcack_fn)callcack_fn=eval(callcack_fn);
		var ac_url = $this.attr("ac_url");
		/**
		 * execute 直接执行
		 * dialog 打开窗口
		 * open 打开浏览器原生窗口
		 * link 当前窗口打开连接
		 * submit 提交表单
		 * ajaxSubmit 提交表单
		 * his_back 返回
		 */
		var show_type = $this.attr("show_type");
		
		var dealDescIE8 = encodeURI(encodeURI($("#node_deal_message").val()));
		
		if(ac_url.indexOf("?")!=-1){
			ac_url = ac_url+"&order_id="+order_id+"&dealDescIE8="+dealDescIE8;
		}else{
			ac_url = ac_url+"?order_id="+order_id+"&dealDescIE8="+dealDescIE8;
		}
		
		if(ac_url.indexOf("http:") == -1) {
			ac_url = ctx+ "/" + ac_url;
		}
		
		if("ajaxSubmit"==show_type){
			$.Loading.show();
			ac_url += "&ajax=yes";
			//alert("0")
			var options = {
				type : "post",
				url : ac_url,
				dataType : "json",
				success : function(result) {
					$.Loading.hide();
					if(callcack_fn)callcack_fn(result);
				},
				error : function(e,b) {
					alert("处理失败，请重试!");
					$.Loading.hide();
				}
			}
			if(form_id){
				var form = $("#"+form_id);
				if(form && form.length>0){
					//alert($("#node_deal_message").val());
					form.ajaxSubmit(options);
				}else{
					$($("form")[0]).ajaxSubmit(options);
				}
			}else{
				$($("form")[0]).ajaxSubmit(options);
			}
		}else if("submit"==show_type){
			if(form_id){
				var form = $("#"+form_id);
				if(form && form.length>0){
					form.attr("action",ac_url).attr("method","post").submit();
				}else{
					$("form").attr("action",ac_url).attr("method","post").submit();
				}
			}else{
				$("form").attr("action",ac_url).attr("method","post").submit();
			}
		} else if("execute"==show_type){
			$.Loading.show();
			ac_url += "&ajax=yes";
			$.ajax({
				url:ac_url,
				type : "post",
				dataType:"json",
				success:function(data){
					$.Loading.hide();
					if(callcack_fn)callcack_fn(data);
				},error:function(a,b){
					$.Loading.hide();
				}
			});
		}else if("dialog"==show_type){
			ac_url += "&ajax=yes";
			Eop.Dialog.open("order_btn_event_dialog");
			var options = {
					type : "post",
					url : ac_url,
					dataType : "html",
					success : function(result) {
						$("#order_btn_event_dialog").empty().append(result);
						if(callcack_fn)callcack_fn(result);
					},
					error : function(e,b) {
						alert("处理失败，请重试!");
					}
				}
				if(form_id){
					var form = $("#"+form_id);
					if(form && form.length>0){
						form.ajaxSubmit(options);
					}else{
						var form = $("form");
						if(form && form.length>0){
							$(form[0]).ajaxSubmit(options);
						}else{
							$("#order_btn_event_dialog").load(ac_url,function(responseText){
								if(callcack_fn)callcack_fn(responseText);
							});
						}
					}
				}else{
					var form = $("form");
					if(form && form.length>0){
						$(form[0]).ajaxSubmit(options);
					}else{
						$("#order_btn_event_dialog").load(ac_url,function(responseText){
							if(callcack_fn)callcack_fn(responseText);
						});
					}
				}
		}else if("open"==show_type){
			window.open(ac_url,'_blank');
		}else if("link"==show_type){
			window.location.href=ac_url;
		}else if("his_back"==show_type){
			window.history.back(-1);
		}
	}
  
  $(function(){
		Eop.Dialog.init({id:"order_btn_event_dialog",modal:true,title:"订单处理", height:"600px",width:"800px"});
	});
	
	function closeDialog(){
		Eop.Dialog.close("order_btn_event_dialog");
	}
	
</script>
</body>
</html>