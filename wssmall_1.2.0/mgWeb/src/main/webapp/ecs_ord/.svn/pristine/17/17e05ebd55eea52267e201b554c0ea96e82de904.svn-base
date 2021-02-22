<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量预处理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
<style type="text/css">
.icoFontlist {
		width: 250px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
.icoFontlist:hover {
	width: 250px;
	border: 0px solid #ddd;
	overflow: scroll;
	text-overflow: ellipsis;
	white-space: nowrap;
	cursor: pointer;
}
</style>
</head>
<body>
	
<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="aito_order_f">
		 <input type="hidden" name="btnType" value="ordList"/>
		 <jsp:include page="batch_pre_params.jsp"/>
        </form>
        <form action="" method="post" id="batch_pre_pass">
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">           
        	<grid:grid from="webpage" formId="aito_order_f" asynModel="1" >
			<grid:header>
				<grid:cell style="text-align:center; width:80px;">状态<input type="checkbox" id="checkAlls" /></grid:cell>
				<grid:cell style="text-align:left; width:230px;">订单信息</grid:cell>
				<grid:cell >商品信息</grid:cell>
				<grid:cell >收货地址</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell clazz="alignCen" >
		  	         <i  title="${order.lock_user_name}"  class="${order.lock_clazz }"  ></i>
		  	    	 <input type="checkbox" name="orderidArray" value="${order.order_id }" out_tid="${order.out_tid }" cert_card_num="${order.cert_card_num }"/>
		  	    </grid:cell>
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div>${order.out_tid }</div></li>
                            	<c:choose>
                            		<c:when test="${order.handle_flag != '0' }">
                            			<li><span style="color:red;">审核状态：</span><div style="color:red;">审核失败</div></li>
                            			<c:if test="${order.exception_desc !=null && order.exception_desc !=''}">
	                            			<div>
	                            			    <li><span style="color:red;">失败原因：</span>
		                            			    <p style="color:red;">
			                            			    <div class="icoFontlist" style="overflow: hidden;color:red;font-size: 12px; white-space: nowrap; text-overflow: ellipsis;" title="${order.exception_desc}">
			                            					<input style="border: 0px;color:red;width:250px;color:red;font-size: 12px;text-align:left;" value="${order.exception_desc}" readonly='readonly'/>
			                            				</div>
		                            				</p>
	                            				</li>
	                            			</div>
                            			</c:if>
                            		</c:when>
                            		<c:otherwise>
                            			<li><span style="">审核状态：</span><div style="">未审核</div></li>
                            		</c:otherwise>
                            	</c:choose>
                            </ul>
                        </div>
		        </grid:cell>
		       <grid:cell style="width:300px;">
		       		<div class="order_pri">
                           	<p class="ps">证件号码 ：${order.cert_card_num }</p>
                           	<p class="ps">证件地址 ：${order.cert_address }</p>
                           	<p class="ps">商品名称 ：${order.goods_name }</p>
                        </div>
		       </grid:cell> 
		       <grid:cell style="width:300px;">
		       		<div class="order_pri">
                           	<p class="ps">收件人 ：${order.ship_name } </p>
                           	<p class="ps">联系电话 ：${order.ship_tel }</p>
                           	<p class="ps">收货地址 ：${order.contact_addr }</p>
                        </div>
		        </grid:cell>
		       <grid:cell style="width:200px;">
		       		<div class="order_pri">
		       			<a href="javascript:void(0);" orderbtns="btnException" name="btnException" ac_url="shop/admin/ordAuto!addException.do?is_rg_exception=yes&exception_from=batch_pre" order_id="${order.order_id }" class="dobtn" style="margin-right:5px;">标记异常</a>
		       			<a href="javascript:void(0);" orderbtns="btnPass" name="btnPass" out_tid="${order.out_tid }" order_id="${order.order_id }" cert_card_num="${order.cert_card_num }" class="dobtn" style="margin-left:5px;">审核通过</a>
                        </div>
		        </grid:cell>
		  </grid:body>
      	</grid:grid>
        </form>        
     </div>
  </div>
</div>
<div class="order_pri">
	<a href="javascript:void(0);" id="btnBatchException" ac_url="shop/admin/ordAuto!addException.do?is_rg_exception=yes&exception_from=batch_pre" class="dobtn" style="margin-left:5px;" >批量异常</a>
	<a href="javascript:void(0);" id="btnBatchPass" class="dobtn" style="margin-left:5px;">批量审核</a>
</div>
<div id="order_rg_exception_dialog"></div>
<br />
<br />
<br />
<script type="text/javascript">
$(function(){
	$("#checkAlls").bind("click",function(){
		var flag = $(this).is(':checked');
			$("input[type=checkbox][name=orderidArray]").each(function(){
					$(this).attr('checked',flag);
			});
	});
	$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
	$("#order_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
	$("#order_list_fm .page").wrap("<form class=\"grid\"></form>");
	
	Eop.Dialog.init({id:"order_rg_exception_dialog",modal:true,title:"标记异常", height:"600px",width:"800px"});
	$("a[orderbtns=btnException]").bind("click",btnRGException);
	$("a[orderbtns=btnPass]").bind("click",btnPass);
	
	$("#btnBatchPass").click(function(){
		var orderids = "";
		var msgboxs ="";
		$('input[type="checkbox"][name="orderidArray"]:checked').each(function(){
			var msgbox = check_id_card($(this).attr("cert_card_num"),$(this).attr("out_tid"));
			if(msgbox != ""&&msgbox.length!=0){
				msgboxs += msgbox+"\n";
			}else{
				if(orderids == ""){
					
					orderids = $(this).val();
				}else{
					orderids += "," + $(this).val();
				}
			}
			
			
			
		});
		if(orderids == '' || orderids.length == 0){
			alert('请选择订单!'+msgboxs);
			return false;
		}
		var url = ctx+"/shop/admin/ordAuto!orderBatchPre.do?ajax=yes&order_ids="+orderids;
		Cmp.ajaxSubmit('batch_pre_pass', '', url, {}, function(responseText) {
			if (responseText.result==0) {
				alert(responseText.message+msgboxs);
				window.location.href='shop/admin/ordAuto!showBatchPreOrderList.do?is_return_back=1';
			} else {
				alert(responseText.message+msgboxs);
			}
			closeDialog();
		}, 'json');
	});
	
	//批量标记异常
	$("#btnBatchException").click(function(){
		var orderids = "";
		$('input[type="checkbox"][name="orderidArray"]:checked').each(function(){
			if(orderids == ""){
				orderids = $(this).val();
			}else{
				orderids += "," + $(this).val();
			}
		});
		if(orderids == '' || orderids.length == 0){
			alert('请选择订单!');
			return false;
		}
		var $this = $(this);
		var checkFn = $this.attr("check_fn");
		var ac_url = $this.attr("ac_url");	
		if(ac_url.indexOf("?")!=-1){
			ac_url = ac_url+"&order_ids="+orderids;
		}else{
			ac_url = ac_url+"?order_ids="+orderids;
		}		
		if(ac_url.indexOf("http:") == -1) {
			ac_url = ctx+ "/" + ac_url;
		}
		callcack_fn = "";
		ac_url += "&ajax=yes";
		Eop.Dialog.open("order_rg_exception_dialog");
		var options = {
			type : "post",
			url : ac_url,
			dataType : "html",
			success : function(result) {
				$("#order_rg_exception_dialog").empty().append(result);
				if(callcack_fn)callcack_fn(result);
			},
			error : function(e,b) {
				alert("处理失败，请重试!");
			}
		}
		debugger;
		var form = $("#aito_order_f");
		if(form && form.length>0){
			form.ajaxSubmit(options);
		}else{
			$("#order_btn_event_dialog").load(ac_url,function(responseText){
				if(callcack_fn)callcack_fn(responseText);
			});
		}
	});	
});

function closeDialog(){
	Eop.Dialog.close("order_rg_exception_dialog");
}

function check_id_card(uid,out_tid){
	if(uid.length!=18){
		return false;
	}
	var msgbox = false;
	var re = /^\d{15}(\d{2}[\dx])?$/i;
	if (!re.test(uid)){
	　　return false;
	}
　　var x = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
　　var y = [1,0,'X',9,8,7,6,5,4,3,2];
　　var getY = function(){
　　　　var v = 0;
　　　　for (i=0;i<=16;i++) v += parseInt(uid.substr(i,1)) * x[ i ];
　　　　v = y[v % 11];
　　　　return v;
　　}
　msgbox = (getY() != uid.substr(17,1))?false:true;
	var msgg = "";
	
	if(msgbox==true){
	msgg = check_age(uid,out_tid);
	}else{
		msgg = "订单：【"+out_tid+"】证件号码格式错误！";
	}
   return msgg;
}

function check_age(uid,out_tid){
	var dat = new Date();
	var y = dat.getFullYear();
	var m = dat.getMonth()+1;
	var d = dat.getDate();
	var y2 = uid.substr(6,4);
	var m2 = uid.substr(10,2);
	var d2 = uid.substr(12,2);
	var msgg = "";
	if(y-y2<12){
		msgg = "订单【"+out_tid+"】开户人未满12周岁！";
		return msgg;
	}else if (y-y2==12){
		if(m==parseInt(m2)){
			if(parseInt(d2)>d){
				msgg = "订单【"+out_tid+"】开户人未满12周岁！";
				return msgg;
			}
		}else if (m<parseInt(m2)){
			msgg = "订单【"+out_tid+"】开户人未满12周岁！";
			return msgg;
		}
	}return msgg;
}
//单个审核
function btnPass(){
	var $this = $(this);
	var order_id = $this.attr("order_id");
	var cert_card_num = $this.attr("cert_card_num");
	var out_tid = $this.attr("out_tid");
	var msgbox = check_id_card(cert_card_num,out_tid);
	if(msgbox !="" && msgbox !=false &&msgbox.length!=0){
		alert(msgbox);
		return false;
	}
	var url = ctx+"/shop/admin/ordAuto!orderBatchPre.do?ajax=yes&order_ids="+order_id;
	Cmp.ajaxSubmit('batch_pre_pass', '', url, {}, function(responseText) {
		if (responseText.result==0) {
			alert(responseText.message);
			window.location.href='shop/admin/ordAuto!showBatchPreOrderList.do?is_return_back=1';
		} else {
			alert(responseText.message);
		}
		closeDialog();
	}, 'json');
}

//单个标记异常
function btnRGException(){
	var $this = $(this);
	var checkFn = $this.attr("check_fn");
	var order_id = $this.attr("order_id");
	var ac_url = $this.attr("ac_url");
	if(ac_url.indexOf("?")!=-1){
		ac_url = ac_url+"&order_ids="+order_id;
	}else{
		ac_url = ac_url+"?order_ids="+order_id;
	}
	if(ac_url.indexOf("http:") == -1) {
		ac_url = ctx+ "/" + ac_url;
	}
	callcack_fn = "";
	ac_url += "&ajax=yes";
	Eop.Dialog.open("order_rg_exception_dialog");
	var options = {
		type : "post",
		url : ac_url,
		dataType : "html",
		success : function(result) {
			$("#order_rg_exception_dialog").empty().append(result);
			if(callcack_fn)callcack_fn(result);
		},
		error : function(e,b) {
			alert("处理失败，请重试!");
		}
	}
	debugger;
	var form = $("#aito_order_f");
	if(form && form.length>0){
		form.ajaxSubmit(options);
	}else{
		$("#order_btn_event_dialog").load(ac_url,function(responseText){
			if(callcack_fn)callcack_fn(responseText);
		});
	}
}
</script>
</body>
</html>