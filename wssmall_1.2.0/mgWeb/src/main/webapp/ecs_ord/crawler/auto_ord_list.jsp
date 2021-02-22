<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
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
<style type="text/css">.red{color:red;}</style>

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
				<grid:cell style="text-align:center; ">状态<input type="checkbox" id="checkAlls" /></grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >订单信息</grid:cell>
				<grid:cell >校验信息</grid:cell>
				<grid:cell ><c:if test="${query_type =='order_view'}">锁定人</c:if></grid:cell>
				<grid:cell ></grid:cell>
				<grid:cell >操作</grid:cell>
				
			</grid:header>
		    <grid:body item="order" >
		  	    <grid:cell clazz="alignCen" style="width:60px;">
		  	         <i  title=""  class=""  id="warning_li_${order.orderTree.order_id }"  ></i>
		  	          <input type="hidden" name="orderids" value="${order.orderTree.order_id }" />
		  	    	 <i  title="${order.orderTree.orderExtBusiRequest.lock_user_name}"  class="${order.lock_clazz }"  ></i>
		  	    	 <input type="checkbox" name="orderidArray" value="${order.orderTree.order_id }" />
		  	    </grid:cell>
		        <grid:cell style="width:260px;">
		        <c:choose>
		        	<c:when test="${order.bss_time_type == '1' }">
		        	<div class="list_t red">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" sq="${order.orderTree.orderExtBusiRequest.shipping_quick }" order_from="${order.orderTree.orderExtBusiRequest.order_from }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
		        	</c:when>
		        	<c:otherwise>
		        		<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div class="dddd"><a name="inner_order_id" order_id="${order.orderTree.order_id }" exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }" visible_status="${order.orderTree.orderExtBusiRequest.visible_status }" detail_url="${order.action_url }" exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }" sq="${order.orderTree.orderExtBusiRequest.shipping_quick }" href="javascript:void(0);">${order.orderTree.orderExtBusiRequest.out_tid }</a></div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            	
                            	
                            </ul>
                        </div>
		        	</c:otherwise>
		        </c:choose>
		        
		        	
		        </grid:cell>
		        <grid:cell style="width:300px;">
		        <c:choose>
		        		<c:when test="${order.bss_time_type == '1' }">
		        		<div class="order_pri">
                        	<p class="tit red">${order.goods_name }</p>
                       		<p class="red">号码：${order.phone_num }</p>
                           	<p class="red">终端：${order.terminal }</p>
                           	<p class="red">业务办理：${order.bss_status=='2'?'已办理已竣工':order.bss_status=='1'?'已办理未竣工':'未办理' }</p>
                        </div>
                        </c:when>
                        <c:otherwise>
                           	<div class="order_pri">
                        	<%-- <p class="tit">${order.goods_package }</p> --%>
                        	<p class="tit">${order.goods_name }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<p class="ps">终端：${order.terminal }</p>
                           	<p class="ps">业务办理：${order.bss_status=='1'?'已办理':'未办理' }</p>
                        </div>
                           	</c:otherwise>
                        </c:choose>
		        </grid:cell> 
		       <grid:cell style="width:120px;">
		       <c:choose>
		        <c:when test="${order.bss_time_type == '1' }">
		       		<div class="order_pri" id="order_info">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit red">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold red" order_id="${order.orderTree.order_id }" detail_url="${order.action_url }" flow_trace_id="${order.orderTree.orderExtBusiRequest.flow_trace_id }" >${order.flow_trace }</p>
                            <p class="tit red">${order.shipping_type }</p>
                        </div>
                 </c:when>
                 <c:otherwise>
                 		<div class="order_pri" id="order_info">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold" order_id="${order.orderTree.order_id }" detail_url="${order.action_url }" flow_trace_id="${order.orderTree.orderExtBusiRequest.flow_trace_id }">${order.flow_trace }</p>
                            <p class="ps">${order.shipping_type }</p>
                        </div>
                 </c:otherwise>
                </c:choose>
		       </grid:cell> 
		       <grid:cell style="width:120px;">
		       <c:choose>
		       		<c:when test="${order.bss_time_type == '1' }">
		       		<p class="ps red">
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
                   	<p class="ps red">${order.orderTree.orderExtBusiRequest.lock_user_name }</p>
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
                   <c:if test="${order.orderTree.orderExtBusiRequest.lock_user_name != 'NULL_VAL' 
                   			&& order.orderTree.orderExtBusiRequest.lock_user_name != ''}">
                   	<p class="ps">${order.orderTree.orderExtBusiRequest.lock_user_name }</p>
                   </c:if>
		       		</c:otherwise>
		       </c:choose>
	       			
		       		<c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi" varStatus="orderItem">
		        		<c:if test="${orderItem.index==0}">
		        		<c:choose>
		       			<c:when test="${order.bss_time_type == '1' }">
		        		<div class="order_pri red">
                        	<p title="${oi.orderItemExtBusiRequest.ess_pre_desc }">ESS:${order.ess_pre_desc==null?'暂无':order.ess_pre_desc }</p>
                            <p title="${oi.orderItemExtBusiRequest.bss_pre_desc }">BSS:${order.bss_pre_desc==null?'暂无':order.bss_pre_desc }</p>
                        </div>
                        </c:when>
                        <c:otherwise>
                        <div class="order_pri">
                        	<p title="${oi.orderItemExtBusiRequest.ess_pre_desc }">ESS:${order.ess_pre_desc==null?'暂无':order.ess_pre_desc }</p>
                            <p title="${oi.orderItemExtBusiRequest.bss_pre_desc }">BSS:${order.bss_pre_desc==null?'暂无':order.bss_pre_desc }</p>
                        </div>
                        </c:otherwise>
                        </c:choose>
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
	      			<c:choose>
		      			<c:when test="${order.orderTree.orderRealNameInfoBusiRequest.later_active_flag=='1' }">
		      				<p class="ps">后向实名制</p>
		      			</c:when>
		      			<c:otherwise>
		      				<p class="ps">普通订单</p>
		      			</c:otherwise>
	      			</c:choose>
		        </grid:cell>
				<grid:cell style="width:80px;">	
				<c:if test="${query_type =='order_view' && order.lock_user_name !=null}">
                            	<div>${order.lock_user_name }</div>
                    </c:if>
		          </grid:cell>
		        <grid:cell style="width:140px;">
	         	<c:if test="${order.orderTree.orderExtBusiRequest.flow_trace_id == 'C'}">
		         	<c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi" varStatus="orderItem">
		        		<c:if test="${orderItem.index==0&&(oi.orderItemExtBusiRequest.goods_type=='20003'||oi.orderItemExtBusiRequest.goods_type=='20002')}"><!-- 商品 类型是合约机或者裸机-->
		        		  
			           		<input  type="text" value="" class="ipt_new" id="terminal_num_${order.orderTree.order_id }" name="terminal_num" order_id="${order.orderTree.order_id }" placeholder="终端串号"/> 
			           		<%-- <input type="button"  name="terminal_num_bt" id="terminal_num_bt_${order.orderTree.order_id }" disabled="disabled" value="确认" order_id="${order.orderTree.order_id }"/> --%>
					      
                        </c:if>
                     </c:forEach>
				</c:if>
		          </grid:cell>
					
		        <grid:cell>
		        	<div class="order_pri">
		        		<c:if test="${order.orderTree.orderExtBusiRequest.flow_trace_id == 'C'}">
				         	<c:forEach items="${order.orderTree.orderItemBusiRequests }" var="oi" varStatus="orderItem">
				        		<c:if test="${orderItem.index==0&&(oi.orderItemExtBusiRequest.goods_type=='20003'||oi.orderItemExtBusiRequest.goods_type=='20002')}"><!-- 商品 类型是合约机或者裸机-->
				        		  
					           		<p><a href="javascript:void(0);" class="dobtn" style="margin-left:5px;" name="terminal_num_bt" id="terminal_num_bt_${order.orderTree.order_id }" value="确认" order_id="${order.orderTree.order_id }">确认</a></p>
							      
		                        </c:if>
		                     </c:forEach>
						</c:if>
                        <p>
                        	<a href="javascript:void(0);" name="add_comments" innerId=${order.orderTree.order_id } class="dobtn" style="margin-left:5px;margin-top:5px;">添加备注</a>
                        </p>
                        
                        <p><span id="resultaa"></span></p>
                    </div>
		        
		        </grid:cell>
		        

		  </grid:body>
      	</grid:grid>
      	<input type="hidden" id="order_id_hidden" />
      	<input type="hidden" id="isListBtn" value="true" />
      	<!-- 后续把权限控制加上，admin 和库管人员可以见 -->
      	<input type="button"  name="terminal_num_batch_bt" id="terminal_num_batch_bt"  value="批量确认" /> -->
      	<%if(ManagerUtils.getAdminUser().getFounder()==1 ||(ManagerUtils.getAdminUser().getFounder()!=1 && ManagerUtils.getAdminUser().getTacheAuth()!=null && ManagerUtils.getAdminUser().getTacheAuth().contains("C"))){%>
      	<a href="javascript:void(0);" class="dobtn" style="margin-left:5px" name="terminal_num_batch_bt" id="terminal_num_batch_bt">配货批量确认</a>
             	<!-- '批量处理'按钮如果是order_view则隐藏 -->
         <script type="text/javascript">
               var is_order_view='${is_order_view}';//隐藏操作作按钮-1 从订单查询界面OrderAutoAction过来 
               if(is_order_view =="order_view") {	
              	 $("#terminal_num_batch_bt").hide();
               }
        </script>
       <%} %>
       </form>
       <div class="clear"></div>
     </div>
  </div>
</div>
<div id="choose_user_div"></div>
<%--显示订单详情弹框 --%>
<div id="orderDetailDialog"></div>
<div id="order_detail_btn_event_dialog"></div>
<div id="addCommentsDlg">
<form method="post" id="saveCommentsForm">
<table  border="0" cellpadding="0">
<tr><textarea rows="4" cols="50" name="dealDesc" id="dealDesc"></textarea></tr>
<tr><td><input type="button" name="sava_comments" id="sava_comments" order_id="" style="margin-left:165px;margin-top:6px;"  class="graybtn1" value="确&nbsp;定" onclick="addComments()"/></td></tr>
</table>
</form>
</div>
<div id="queryUserListDlg"></div>
<br />
<br />
<br />

<script type="text/javascript">
//添加备注功能
$(function(){
	Eop.Dialog.init({id:"addCommentsDlg",modal:true,title:"添加备注",width:'400px'});
	Eop.Dialog.init({id:"orderDetailDialog",modal:true,title:"订单详情",width:'1100px'});
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"锁定人信息", width:"800px"});
	$("a[name='add_comments']").click(function(){
		var orderId= $(this).attr("innerId");
		openCommentsDlg(orderId);
		return false;
	});
	//如果order_sn或者是iccid查询出来的订单并且为质检稽核环节的订单弹框显示详情页
	//showOrderDetailDialog();
});

//如果order_sn或者是iccid查询出来的订单并且为质检稽核环节的订单弹框显示详情页
function showOrderDetailDialog(){
	var order_sn = $("input[name='params.order_sn']").val();
	var iccid = "";
	if(order_sn != ""){
		$("#order_list_fm").find("table>tbody>tr").each(function(){
			var flow_trace_id = $(this).find("#order_info").find("p.f_bold").attr("flow_trace_id");
			if(flow_trace_id == "F"){
				//获取订单详情页的URL
				var o_url= $(this).find("#order_info").find("p.f_bold").attr("detail_url");
				var order_id = $(this).find("#order_info").find("p.f_bold").attr("order_id");
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
				$("#orderDetailDialog").html("loading...");
				$("#orderDetailDialog").load(url,{},function(){
					  CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:order_id,order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,false);
					  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:order_id,order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,false);
					  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:order_id,ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},false);
					  OrdBtns.showBtns(order_id,"","","orderDetailDialog",null,showOpenBtn);
				});
				Eop.Dialog.open("orderDetailDialog");
			}
		});
		
	}
}

function openCommentsDlg(orderId){
	$("#addCommentsDlg").load();
	Eop.Dialog.open("addCommentsDlg");
	$("#sava_comments").attr("order_id",orderId);
}

function addComments(){
	var comments=$("#dealDesc").val();
	if(comments!=""){
	         var order_Id=$("#sava_comments").attr("order_id");
			 var url= app_path+"/shop/admin/orderFlowAction!saveOrderComments.do?ajax=yes&order_id="+order_Id;
			 var saveBack = function(reply){
				 if(reply.result==0){
						alert("备注添加成功");
						Eop.Dialog.close("addCommentsDlg");
   				 }
				 $("#dealDesc").val("");
			 }; 
			Cmp.ajaxSubmit('saveCommentsForm', '', url, {}, saveBack, 'json');
	}else{
		alert("备注不能为空");
	}	
}
function queryUserId(){
	Eop.Dialog.open("queryUserListDlg");
	lock_user_id = $("#lock_user_id").val();
	var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
	    $("#queryUserListDlg").load(url,{},function(){});
} 

</script>




<script type="text/javascript">
	function copyIframe(){
		var mainContent = $(window.top.document).find(".mainContent");
		var ordDetailIframe = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']");
		if(ordDetailIframe.length==0){
			ordDetailIframe = $(window.top.document).find(".main_all_iframe[app_menu_id='2014112501']").clone(true);
			$(ordDetailIframe).attr("app_menu_id","2016121601");
			$(ordDetailIframe).hide();
			$(ordDetailIframe).find("#right_content").html("");
			ordDetailIframe.appendTo(mainContent);
		}
	}
	/*删除已经关闭的iframe */
	function deleteIframe(){
		$(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").contents().find("#right_content").find(".auto_frame").each(function(){
			var order_id = $(this).attr("order_id");
			var bottom_tab_ul = $(window.top.document).find("#bottom_tab_ul");
			var orderli = bottom_tab_ul.find("li[order_detail='"+order_id+"']");
			if(!orderli || orderli.length==0){
				$(this).remove();
			}
		})
	}
	$(function(){
		copyIframe();
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
			var order_from = obj.attr("order_from");
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
			if(order_from == '10012'){
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
			}
			
		});
		//查看订单详细
		$("a[name=inner_order_id]").bind("click",function(){
			deleteIframe();
			var order = $(this).parents("tr")[0];
			var checked = $("input[name='orderidArray']",order).attr("checked");
			if(!checked){
				var ordnum = $("#order_list_fm tbody tr input[name='orderidArray']:checked").length;
				if(ordnum<6){
					$("input[name='orderidArray']",order).attr("checked", true);
				}
				if(ordnum>=6){
					alert("系统最多可以打开6个订单详情!");
					return ;
				}
			}
			var ordnum = $("#order_list_fm tbody tr input[name='orderidArray']:checked").length;
			if(ordnum>6){
				alert("系统最多可以打开6个订单详情!");
				return ;
			}
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
			if(!$(this).closest("tr").find("input[name='orderidArray'][type='checkbox']").is(':checked')){
				window.location.href=url;
			}else{
				//多窗口打开子菜单begin
				var app_menu_id = "2016121601";//一级菜单“订单管理”
				var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").contents().find("#right_content");//右侧主界面
				
				var main_doc = $(window.top.document);	//
				
				var v = {};
				v.up_id="2016121601";
				v.url=url;
				v.target="blank";
				v.id=order_id;
				v.text=order_id+"订单详情";
				
				
				//--底部加tab--begin
				//var bottom_li ='<li id="appmenu" up_id="'+v.up_id+'" href="'+v.url+'" target="'+v.target+'" class="curr" app_menu_id="'+v.id+'"><a href="javascript:void(0);"><span>'+v.text+'</span></a><a href="javascript:void(0);" class="tabClose">关闭</a></li>';	
				var bottom_li ='<li li_type="ORD" target="ORDER" class="curr" app_menu_id="2016121601" order_detail="'+v.id+'"><a href="javascript:void(0);"><span>'+v.text+'</span></a><a href="javascript:void(0);" class="tabClose">关闭</a></li>';	
				var bottom_tab_ul = $(window.top.document).find("#bottom_tab_ul");//底部tab
				var is_exists = false;
				bottom_tab_ul.find('li[li_type="ORD"]').each(function(){
						var tab_li = $(this);
						if($(this).attr("order_detail")==v.id){
								is_exists = true;
								tab_li.siblings().removeClass("curr"); 
								tab_li.addClass("curr");
								$(window.top.document).find(".main_all_iframe").each(function(){
										$($(this)).contents().find("#right_content").find(".auto_frame").hide();
										$($(this)).contents().find("#right_content").find(".auto_frame[order_id='"+v.id+"']").show();
								})
								return false;
						}	
				});
				if(is_exists) return;
				if(6==bottom_tab_ul.find('li[li_type="ORD"]').length){
						alert("系统最多可以打开6个订单详情!");
						return;
				}
				bottom_tab_ul.find("li").removeClass("curr");
				
				bottom_tab_ul.append(bottom_li);
			  	//--底部加tab--end
				//--底部tab切换事件-- begin
				bottom_tab_ul.find("li").each(function(){
					var tab_li = $(this);
					tab_li.bind("click",function(){
						$(this).siblings().removeClass("curr");
						var app_menu_id = $(this).attr("app_menu_id");
						var order_id = $(this).attr("order_detail");
						var li_type = $(this).attr("li_type");
						if("ORD" == li_type){
							$(window.top.document).find(".main_all_iframe").hide();
							$(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").show();
							var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").contents().find("#right_content");//右侧主界面  		       						
						}else{
							var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").contents().find("#right_content");//右侧主界面  		       					
						}
						var app_content = right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']");
						var order_detail_content = right_content.find(".auto_frame[order_sub='yes'][order_id='"+order_id+"']");
     					var up_menu = main_doc.find(".main_all_iframe[app_menu_id='2014112501']");
     					right_content.find(".auto_frame").css('display','none');
			    		if(li_type=="ORD"){
			    			$(order_detail_content).css('display','block');//显示详情
			    		}
			    		else{
			    			$(app_content).css('display','block');//显示
			    		}
 						var leftMenu = up_menu.find(".leftMenu");
     					leftMenu.find("a").removeClass("curr")
     					leftMenu.find("a[app_menu_id='"+app_menu_id+"']").attr("class","curr");    
     					tab_li.addClass("curr").siblings("li").removeClass("curr");
						main_doc.find("#appmenu").find("li").removeClass("curr");
						main_doc.find("#appmenu").find("li[app_menu_id='2014112501']").addClass("curr");  //一级菜单“订单管理”  */
					});
					tab_li.find("a[class='tabClose']").each(function(){
						$(this).bind("click",function(){
							var order_id = tab_li.attr("order_detail");
							var app_menu_id = tab_li.attr("app_menu_id");
							var li_type = $(tab_li).attr("li_type");
							if("ORD" == li_type){
								var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").find("#right_content");//右侧主界面  	
								right_content.find(".auto_frame[app_menu_id='"+order_id+"']").remove();
							}else{
								var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").find("#right_content");//右侧主界面  		    
								right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']").remove();
							}
							//$(auto_frame).hide();
							//$(auto_frame).remove();
							$(tab_li).prev().trigger("click");
							$(tab_li).removeClass("curr").remove();
							$("#order_list_fm tbody tr input[name='orderidArray'][value='"+order_id+"']").attr("checked",false);
							return false;
						});		
					});	
					
				});			
				//--右侧主界面加frame-- begin			
				$(window.top.document).find(".main_all_iframe").each(function(){
					var id = $(this).attr("app_menu_id");
					if(id=="2016121601"){
						$(this).show();
					}
					else{
						$(this).hide();
					}
				});
				var windContent = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").contents().find("#right_content");
				var frame = $("<iframe order_sub='yes' order_id='"+v.id+"' class='auto_frame' app_menu_id='"+v.id+"' width='100%' height='100%'  frameborder='0' ></iframe>");
				windContent.append(frame);
				frame.css("height","259px");	
				var a = v.url;
				if(a.indexOf("?")>-1)
					 a +="&version="+new Date();
				else
					 a +="?version="+new Date();
				frame.attr("src", a);
				frame.load(function() {
					$(window.top.document).find("#bottom_tab_ul li[order_detail='"+order_id+"']").trigger("click");
				}); 
			}
		});
	});
</script>


<script type="text/javascript">
 /* 异步检查预警信息*/
 $(document).ready(function() { 
	 //queryWarning();
	 var main_doc = $(window.top.document);
	 var bottom_tab_ul = main_doc.find("#bottom_tab_ul");
	 bottom_tab_ul.find("li[li_type='ORD']").each(function(){
		var tab_li = $(this);
		tab_li.bind("click",function(){
			$(this).siblings().removeClass("curr");
			var app_menu_id = $(this).attr("app_menu_id");
			var order_id = $(this).attr("order_detail");
			var li_type = $(this).attr("li_type");
			if("ORD" == li_type){
				$(window.top.document).find(".main_all_iframe").hide();
				$(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").show();
				var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").find("#right_content");//右侧主界面  		       						
			}else{
				var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").find("#right_content");//右侧主界面  		       					
			}
			var app_content = right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']").get(0);
			var order_detail_content = right_content.find(".auto_frame[order_sub='yes'][order_id='"+order_id+"']").get(0);
			var up_menu = $(window.top.document).find(".main_all_iframe[app_menu_id='2014112501']");							
    		right_content.find(".auto_frame").css('display','none');
    		if(li_type=="ORD"){
    			$(order_detail_content).css('display','block');//显示详情
    		}
    		else{
    			$(app_content).css('display','block');//显示
    		}
			var leftMenu = up_menu.find(".leftMenu");
			leftMenu.find("a").removeClass("curr")
			leftMenu.find("a[app_menu_id='"+app_menu_id+"']").attr("class","curr");    
			$(this).siblings().removeClass("curr");
			$(this).addClass("curr");
			$(window.top.document).find("#appmenu").find("li").removeClass("curr");
			$(window.top.document).find("#appmenu").find("li[app_menu_id='2014112501']").addClass("curr");  //一级菜单“订单管理”  */
		});
		tab_li.find("a[class='tabClose']").each(function(){
			$(this).bind("click",function(){
				var order_id = tab_li.attr("order_detail");
				var app_menu_id = tab_li.attr("app_menu_id");
				var li_type = $(tab_li).attr("li_type");
				var auto_frame;
				if("ORD" == li_type){
					var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='2016121601']").find("#right_content");//右侧主界面  	
					right_content.find(".auto_frame[app_menu_id='"+order_id+"']").remove();
				}else{
					var right_content = $(window.top.document).find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").find("#right_content");//右侧主界面  		    
					right_content.find(".auto_frame[app_menu_id='"+app_menu_id+"']").remove();
				}
				//$(auto_frame).hide();
				//$(auto_frame).remove();
				$(tab_li).prev().trigger("click");
				$(tab_li).removeClass("curr").remove();
				$("#order_list_fm tbody tr input[name='orderidArray'][value='"+order_id+"']").attr("checked",false);
				return false;
			});		
		});	
		
	});	
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