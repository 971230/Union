<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}

String is_work_custom = request.getParameter("is_work_custom");
String workflow_id = request.getParameter("workflow_id");
String instance_id = request.getParameter("instance_id");
String node_index = request.getParameter("node_index");
String order_id = request.getParameter("order_id");
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script src="<%=request.getContextPath() %>/ecs_ord/order/onlinecustomwork/js/btn_menu.js"></script>
<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

<style>
<!--
.noborder {
	border-style: none;
}
-->
.icoFontlist{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    color:#5f5f5f;  
    overflow: hidden;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
} 

.red_mark {
    color: red;
}

.color_gray {
    background-color: #CCC;
}

.icoFontlist:hover  
{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    overflow: scroll;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
    cursor:pointer;   
}  

.second select {  
    width: 352px;  
    height: 106px;  
    margin: 0px;  
    outline: none;  
    border: 1px solid #999;  
    margin-top: 33px; 
    background-color: white; 
}  
.second select option{
	background-color: inherit; 
}
.op{
	background-color: transparent; 
    bacground:tansparent;
    -webkit-appearance: none;
}
.second input {  
    width: 350px;  
    top: 9px;  
    outline: none;  
    border: 0pt;  
    position: absolute;  
    line-height: 30px;  
    /* left: 8px; */  
    height: 30px;  
    border: 1px solid #999;  
}  
.second ul {  
    position: absolute;  
    top: 27px;  
    border: 1px solid #999;  
    left: 8px;  
    width: 125px;  
    line-height: 16px;  
}  
.ul li{  
    list-style: none;  
    width: 161px;  
    /* left: 15px; */  
    margin-left: -40px;  
    font-family: 微软雅黑;  
    padding-left: 4px;  
}  
.blue {   
    background:#1e91ff;   
}  
</style>

<body style="background: white;">

<div class="input">
	<form class="validate" method="post" id="workform" validate="true" >
		<div>
			<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>发货信息</h2>
            	<input type="hidden"  id="post_type" name="post_type" value="0"/>
                <div class="grid_n_cont">
                  	<div class="grid_n_cont_sub">
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
                          <tbody>
                          <tr>
                            <th>物流单：</th>
                            <td colspan="3">
                            	<html:orderattr  order_id ="${order_id }" field_name="logi_no"  field_desc ="物流单号" field_type="text"></html:orderattr>
                            </td>
                          </tr>
                          <c:if test="${deal_type =='01'}">   
                          <tr>
                            <th>&nbsp;</th>
                            <td colspan="3" style="color:#F00;">
	                               订单处理类型：${deal_type} 闪电送物流公司：${quick_shipping_cop} 闪电送状态：${quick_shipping_status }
                            </td>
                          </tr>
                          </c:if>
                          <tr>
                            <th>物流公司：</th>
                            <td>
                                <html:orderattr disabled="disabled" attr_code="DIC_LOGI_COMPANY"  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司" field_type="select"></html:orderattr>
                            </td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <c:if test="${shipping_cop_id=='ZT0002' || shipping_cop_id=='ZY0002'}">
	                          <tr>
	                            <th>取货人：</th>
	                            <td>
	                            	<html:orderattr  order_id ="${order_id }" field_name="carry_person"  field_desc ="取货人" field_type="input"></html:orderattr>
	                            	<%-- <input name="logi_receiver" type="text" class="ipt_new" style="width:150px;" value="${delivery.logi_receiver }" > --%>
	                            </td>
	                            <th>取货人电话：</th>
	                            <td>
	                            	<html:orderattr  order_id ="${order_id }" field_name="carry_person_mobile"  field_desc ="取货人电话" field_type="input"></html:orderattr>
	                           		<%-- <input name="logi_receiver_phone" type="text" class="ipt_new" style="width:150px;" value="${delivery.logi_receiver_phone }" > --%>
	                           	</td>
	                          </tr>
                          </c:if>
                          
                        </tbody></table>
                  	</div>
                </div>
            </div>
            
            <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单信息</h2>
                <div class="grid_n_cont">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div> --%>
                  	<div class="grid_n_cont_sub">
                    	<div class="form_g">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_a">
                              <tbody>
                              <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="">
                                      <tbody><tr>
                                        <th>内部订单号：</th>
                                        <td>${orderTree.order_id }</td>
                                        <th>外部订单号：</th>
                                        <td>${orderTree.orderExtBusiRequest.out_tid }</td>
                                      </tr>
                                      <tr>
                                        <th>物流单号：</th>
                                        <td>${delivery.logi_no }</td>
                                        <th>收货人姓名：</th>
                                        <td>${orderTree.orderBusiRequest.ship_name }</td>
                                      </tr>
                                      <tr>
                                        <th>配送区域：</th>
                                        <td>${delivery.province }${delivery.city }${delivery.region }</td>
                                        <th>收货人电话：</th>
                                        <td>${orderTree.orderBusiRequest.ship_tel }</td>
                                      </tr>
                                      <tr>
                                        <th>收货人地址：</th>
                                        <td>${delivery.ship_addr }${delivery.ship_zip}</td>
                                        <th>&nbsp;</th>
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <th>是否已上传：</th>
                                        <td>
                                        	<html:orderattr disabled="disabled" attr_code="DIC_IS_UPLOAD" order_id ="${order_id }" field_name="is_upload"  field_desc ="资料回收方式" field_type="select" ></html:orderattr>
                                        </td>
                                        <th>资料回收类型：</th>
                                        <td>
                                        	<html:orderattr disabled="disabled" order_id ="${order_id }" field_name="file_return_type"  field_desc ="资料回收方式" field_type="text"></html:orderattr>
                                        </td>
                                      </tr>
                                      <tr>
                                        <th>发票号码：</th>
		                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
		                             	<th>发票代码：</th>
		                              	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                           			  </tr>
                                    </tbody></table>
                                </td>
                              </tr>
                            </tbody></table>
                        </div>
                  	</div>
            		<!-- ZX add 2015-12-30 start 副卡信息开始 -->
               	  	<div class="grid_n_cont_sub">
               	  	 	<jsp:include page="../include/phone_info_fuka.jsp?order_id=${order_id }"/>
               	  	</div>
               	  	<!-- ZX add 2015-12-30 end 副卡信息结束 -->
                </div>
            </div>
            
			<div class="pop_btn" align="center">
				<a id="workflow_submit_btn" onclick="runNodeManual();"  class="blue_b" style="cursor: pointer;"><span>发货</span></a>
				<a orderbtns="btn" name="o_print_wl" ac_url="shop/admin/orderPostPrintAction!doGetPiritModelNew.do" show_type="dialog" hide_page="unvisabled,list" order_id="${order_id }" form_id="" check_fn="checkPrintData" callcack_fn="" class="blue_b" style="margin-right:5px;cursor: pointer;"><span>打印物流单</span></a>
				<a orderbtns="btn" name="free_print_wl" ac_url="shop/admin/orderPostPrintAction!doGetHotFreeModel.do" show_type="dialog" hide_page="unvisabled,list" order_id="${order_id }" form_id="order_hotfree_print_fm" check_fn="" callcack_fn="" class="blue_b" style="margin-right:5px;cursor: pointer;"><span>物流热敏单打印</span></a>
				<a id="print_card_btn" onclick="doPrintCard();" class="blue_b" style="cursor: pointer;">号卡领取热敏单打印</a>
				<a orderbtns="btn" name="o_return_back" ac_url="shop/admin/ordAuto!showOrderList.do?is_return_back=1&amp;query_type=order" show_type="link" hide_page="list,exception" order_id="${order_id }" form_id="" check_fn="goBackCheck" callcack_fn="" class="blue_b" style="margin-right:5px;cursor: pointer;"><span>返回</span></a>
			</div>
			
			<input id="order_id" type="hidden" value="${order_id}" />
			<input id="operatorList" name="operatorList" type="hidden" value="${operatorList}" />
		</div>
	</form>
</div>
<div id="order_btn_event_dialog"></div>

            
<div id="queryUserListDlg"></div>

</body>

<script>
var TempArr=[];//存储option 
var NumArr=[];//存储号码 
var is_work_custom = "<%=is_work_custom%>";
var workflow_id = "<%=workflow_id%>";
var instance_id = "<%=instance_id%>";
var node_index = "<%=node_index%>";

	$(function() {
		
		var node = window.parent.canvas.nodes[node_index];
		$("#span_flow_info").html(node.remark);
	});
	
	function checkIsMixOrd(){
		$("#normal_radion").hide();
		$("#mixord_radion").hide();
		$("#intent_radion").hide();
		$("#refer_radion").hide();
		$("#enterprise_radion").hide();
		
		var order_id = $("#order_id").val();
		
		var url = ctx + "/shop/admin/ordAuto!isMixOrd.do?ajax=yes&order_id=" + order_id;
		
		Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				var isMixOrd = responseText.isMixOrd;
				if(isMixOrd=="mixord"){
					$("#mixord_radion").show();
				}else if(isMixOrd=="normal"){
					$("#normal_radion").show();
				}else if(isMixOrd=="intent"){
					$("#intent_radion").show();
				}else if(isMixOrd=="refer"){
					$("#refer_radion").show();
				}else if(isMixOrd=="enterprise"){
					$("#enterprise_radion").show();
				}else{
					alert("工单类型判断异常");
				}
			} else {
				alert(responseText.message);
			}
			//closeDialog();
		}, 'json');
	};
	
  
     /*先将数据存入数组*/  
     $("#typenum option").each(function(index, el) {  
         TempArr[index] = $(this).text();  
     });  
     $(document).bind('click', function(e) {    
         var e = e || window.event; //浏览器兼容性     
         var elem = e.target || e.srcElement;    
         while (elem) { //循环判断至跟节点，防止点击的是div子元素     
             if (elem.id && (elem.id == 'typenum' || elem.id == "makeupCo")) {    
                 return;    
             }    
             elem = elem.parentNode;    
         }    
         $('#typenum').css('display', 'none'); //点击的不是div或其子元素     
     });    
    
      
    function changeF(this_) {  
        $(this_).prev("input").val($(this_).find("option:selected").text());  
        $("#typenum").css({"display":"none"});  
    }  
    function setfocus(this_){  
        $("#typenum").css({"display":""});  
        var select = $("#typenum");  
        for(i=0;i<TempArr.length;i++){  
            var option = $("<option class='op'></option>").text(TempArr[i]);  
            option.val(TempArr[i].split("---")[0]);
            select.append(option);  
        }   
    }  
      
    function setinput(this_){  
        var select = $("#typenum");  
        select.html("");  
        for(i=0;i<TempArr.length;i++){ 
        	var value = this_.value;
        	value = value.replace(/(^\s*)|(\s*$)/g, ""); 
            //若找到以txt的内容开头的，添option  
            if(TempArr[i].indexOf(value)>=0){  
                var option = $("<option class='op'></option>").text(TempArr[i]); 
                option.val(TempArr[i].split("---")[0]);
                select.append(option);  
            }  
            
        }  
    }  
 
    
function runNodeManual(){
	if($("#workflow_submit_btn").hasClass("grayBtns"))
		return false;
	
	var condition = "";
	var remark = $("#node_deal_message").val();//备注信息
	
	if(window.parent.canvas){
		window.parent.canvas.runNodeManual(instance_id,condition,remark);
	}
}

function rollback(){
	if($("#workflow_rollback_btn").hasClass("grayBtns"))
		return false;
	
	var canvasInstance;
	
	if(window.parent.canvas)
		canvasInstance = window.parent.canvas;
	else{
		alert("流程画板JS对象为空");
		return false;
	}
	
	var remark = $("#work_reason").val();
	
	canvasInstance.rollback(instance_id,remark);
}

//物流单打印前校验
function checkPrintData () {
	  
	  return true;
}

function doPrintCard(){
	var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id+'&print_type=1';
	//弹出打印页面
	var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
}

</script>
