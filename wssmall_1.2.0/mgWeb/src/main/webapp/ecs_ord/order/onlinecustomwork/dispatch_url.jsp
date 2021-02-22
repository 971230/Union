<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/common/control/js/splitScreen.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/common/control/js/artZoom.min.js"></script>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}

String order_id = request.getParameter("order_id");
String jump_node = request.getParameter("jump_node");
%>

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

	<!-- 流程图 -->
	<div id="auto_flows_div">
		<jsp:include page="../custom_flow.jsp?order_id=${order_id }&jump_node=<%=jump_node%>"/>
	</div>

	<!-- 下派工单 -->
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_work_order"></a>下派工单</h2>
		
		<div id="div_work_order input">
			<div class="grid_n_cont">
				<form class="validate" method="post" id="workform" validate="true" >
					<div>
						<div style="margin-top: 5px;">
							<table cellspacing="0" cellpadding="0" border="0" width="100%">
								<tbody>
									<tr>
										<th>工单类型：</th>
										<td>
											<div id="work_custome">
											<c:choose>
													
													<c:when test="${work_type == '1' }">
														<input type="checkbox" checked="checked" class="radioclass" name="work_type" value="10">
														<span>外派单</span>
													</c:when>
													<c:otherwise>
														<input type="checkbox" checked="checked" class="radioclass" name="work_type"
															value="07">
														<span>意向单</span>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr style="height: 40px; ">
										<th>工单操作人员：</th>
										<td style="position: absolute;">
											<span class="second">  
											    <input type="text" name="operator_num" id="makeupCo" class="makeinp" onfocus="setfocus(this)" oninput="setinput(this);" placeholder="请选择或输入"  autocomplete="off"/>  
											    <select name="makeupCoSe" id="typenum" onchange="changeF(this)" size="10" style="display:none;">  
											    </select>  
											</span>  
										</td>
									</tr>
									
									<tr style="height: 100px;">
										<th style="width: 150px;text-align: right;">备注：</th>
										<td><textarea rows="5" cols="88" id="work_reason" name="work_reason"></textarea></td>
									</tr>
								
								</tbody>
							</table>
						</div>
						
						<div class="pop_btn" align="center">
							<a id="savebtn" class="dobtn" style="cursor: pointer;"><span>下派工单</span></a>
							<a id="work_back_btn" class="dobtn" style="cursor: pointer;"><span>工单回收</span></a>
							<a id="qryOrdWorkbtn" class="dobtn" style="cursor: pointer;"><span>工单详情</span></a>
						</div>
						<input id="operatorList" name="operatorList" type="hidden" value="${operatorList}" />
					</div>
				</form>
			</div>
		
			<tr>
				<div class="grid_n_div" id="showOrHideDiv" style="display: none;" >
		            <div class="grid_n_cont">
						<div class="grid" >
						 	<form method="POST"  id="roleform" >
								<grid:grid from="webpage"  formId="workform1"  ajax="yes" >
									<grid:header>
										<grid:cell width="10%" >工单类型</grid:cell>
										<grid:cell width="10%" >工单编号</grid:cell>
										<grid:cell width="10%" >工单状态</grid:cell>
										<grid:cell width="10%" >派单人</grid:cell>
										<grid:cell width="10%" >派单时间</grid:cell>
										<grid:cell width="10%" >回单时间</grid:cell>
										<grid:cell width="10%" >处理人</grid:cell>
										<grid:cell width="5%" >工单备注</grid:cell>
										<grid:cell width="5%" >工单反馈</grid:cell>
										<grid:cell width="10%" >处理详情</grid:cell>
									</grid:header>
									<grid:body item="data">
										<grid:cell>${data.type} </grid:cell>
										<grid:cell>${data.work_order_id} </grid:cell>
										<grid:cell>${data.status} </grid:cell>
										<grid:cell>${data.order_send_username} </grid:cell>
										<grid:cell>${data.create_time} </grid:cell>
										<grid:cell>${data.update_time} </grid:cell>
										<grid:cell>${data.operator_name} </grid:cell>
										<grid:cell>
											<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"title="${data.remark}">&nbsp;&nbsp;${data.remark}</div>
										 </grid:cell>
										<grid:cell>
											<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"title="${data.result_remark}">&nbsp;&nbsp;${data.result_remark}</div>
										 </grid:cell>
										<grid:cell>
											<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"title="${data.detail}">&nbsp;&nbsp;${data.detail}</div>
										</grid:cell>
									</grid:body>
								</grid:grid>
							</form>	
						</div>
					</div>
				</div>
			</tr>
		            
			<div id="queryUserListDlg"></div>
		</div>
	</div>
	
	<!-- 订单信息 -->
	<div id="order_info_div">
		<jsp:include page="../detail/order_detail.jsp?order_id=${order_id }"/>
	</div>
	
	<!-- 按钮 -->
	<div id="btnlists">
		<div class="comBtns" style="position: fixed;bottom: 0px;width: 100%;">
			<a id="closeIntent" class="dobtn" style="cursor: pointer;"><span>结单</span></a>
		</div>
	</div>
</body>

<script>
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;

var TempArr=[];//存储option 
var NumArr=[];//存储号码 

var order_id = "<%=order_id%>";
var instance_id = "";
var rollback_remark_id = "work_reason";

$(function() {
	
	OrderInfoTool.option_stypes = "source_from,active_flag,sign_status,DIC_ORDER_KINGCARD_STATE,certificate_type,"
		+"CustomerType,DIC_PAY_STATUS,pay_way,pay_type";
	
	OrderInfoTool.option_stypes = "source_from,active_flag,sign_status,DIC_ORDER_KINGCARD_STATE,certificate_type,"
		+"CustomerType,DIC_PAY_STATUS,pay_way,pay_type";
	
	OrderInfoTool.field_names = "out_tid,zb_inf_id,order_city_code,district_code,tid_time,"
		+"order_from,phone_num,mainNumber,order_state,if_Send_Photos,refund_status,"
		+"kingcard_status,service_remarks,is_realname,phone_owner_name,certi_type,"
		+"cert_card_num,cert_address,cert_card_nation,certi_sex,birthday,"
		+"cert_issuer,cert_failure_time,cert_eff_time,phone_num,cust_id,"
		+"CustomerType,group_code,cert_num2,messages_send_lasttime,messages_send_count,"
		+"cert_pic_flag,GoodsID,GoodsName,goods_type,goods_cat_id,sell_price,"
		+"develop_code_new,develop_name_new,develop_point_code_new,development_point_name,"
		+"out_operator,out_operator_name,out_office,out_office_name,order_origfee,"
		+"pay_time,order_realfee,pay_status,paytype,pay_method,pay_sequ,pay_back_sequ,"
		+"logi_no,post_fee,n_shipping_amount,shipping_company,receiver_mobile,shipping_time,"
		+"ship_name,province_id,city_code,area_code,ship_addr,order_deal_method,contact_addr,"
		+"carry_person_mobile,reference_phone,receiver_mobile";
	
	OrderInfoTool.change_fields = "contact_results_frist,contact_results_second";
		
	OrderInfoTool.orderTree = WorkFlowTool.getOrderTree("<%=order_id%>");
	
	var ret = WorkFlowTool.getOrderAttrValues("<%=order_id%>",
			OrderInfoTool.field_names,OrderInfoTool.option_stypes);
	OrderInfoTool.attrs = ret["attrs"];
	OrderInfoTool.options = ret["options"];
	
	OrderInfoTool.loadOrderData();
		
	$("#operator_num").empty(); //清空
	$("#operator_num").append("<option value='空'>请选择任务人员</option>"); //为Select追加一个Option(下拉项)
	$("#operator_num").val("空");
	
	var work_type = $("#work_type").val();
	var order_id = $("#order_id").val();
	var url = ctx + "/shop/admin/ordAuto!getOperator.do?ajax=yes&order_id=" + order_id;
	
	Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
		if (responseText.result == 0) { 
			var operatorList = responseText.list;
			var list = "";
			$.each(operatorList, function (index, obj) {
				$("#typenum").append("<option value='"+obj.phoneNum+"'>"+obj.phoneNum+"---"+obj.staffName+"</option>");
				list = list + obj.phoneNum + "---" + obj.staffName + ";";
				TempArr[index]= obj.phoneNum + "---" + obj.staffName;
			});
			 $("#operatorList").val(list);
		} else {
			alert(responseText.message);
		}
		//closeDialog();
	}, 'json');
		
	$("#qryOrdWorkbtn").click(function(){
		if( document.getElementById('showOrHideDiv').style.display=='none') {
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
			document.getElementById('showOrHideDiv').style.display='block';
		}else {
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
			document.getElementById('showOrHideDiv').style.display='none';
		}
	});
		
	$("#hide_params_tb").bind("click",function(){
		document.getElementById('showOrHideDiv').style.display='none';
		$("#hide_params_tb").hide();
		$("#show_params_tb").show();
	});
		
	$("#show_params_tb").bind("click",function(){
		document.getElementById('showOrHideDiv').style.display='block';
		$("#hide_params_tb").show();
		$("#show_params_tb").hide();
	});
	
	$("#closeIntent").click(function(){
		OrderInfoTool.cancelOrder();
	});
	
	$("#savebtn").click(
		function() {
			var falg = 0; 
			
			instance_id = canvas.getCurrInsId();
			
			$("input[name='work_type']:checkbox").each(function () { 
				if ($(this).attr("checked")) { 
				falg += 1; 
				} 
			})
			if (falg < 1){
				alert("请选择工单类型！");
				return;
			}
			//var work_type = $("input[name='work_type']").val(); 
			//var work_type=$('input:checkbox[name="work_type"]:checked').val();
			//var work_type=$("input[type='checkbox']").attr('value');
			var work_type = '';
			$('input[name="work_type"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数  
				if (work_type == "") {
					work_type=$(this).val();
				}else{
					work_type = work_type+"," + $(this).val();
				}
			});
			if (work_type == "") {
				alert("请选择工单类型！");
				return;
			}
			var operator_num = $("input[name='operator_num']").val();
			if (operator_num== null || operator_num == "空" || operator_num == "" || operator_num.length<14) {
				alert("请选择工单任务人员！");
				return;
			}
			if(null != TempArr && TempArr.length>0){
				var flag = false;
				for(j = 0,len = TempArr.length; j < len; j++) {
					 if(TempArr[j] == operator_num){
						 flag = true;
						 break;
					 }  
				}
				if(!flag){
					alert("请选择下拉框中的工单任务人员！");
					return;
				}
			}else{
				alert("可选工单任务人员为空，请联系管理员");
				return;
			}
			var work_reason = $("#work_reason").val().trim();
			if (work_reason == "") {
				alert("请输入工单申请备注！");
				return;
			}
			if(window.confirm('你确定要提交工单吗？')){
                 //alert("确定");
                 //return true;
            }else{
                 //alert("取消");
                 return;
            }
			var order_id = $("#order_id").val();
			var operator_num = $("input[name='operator_num']").val();
			var operatorList = $("#operatorList").val();
			var url = ctx + "/shop/admin/ordAuto!save_work.do?ajax=yes&order_id=" + order_id+"&operator_num="+operator_num+"&work_type="+work_type+"&work_reason="+work_reason;
			
			/* Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
				if (responseText.result == 0) {
					alert(responseText.message);
					closeshow_workList();
					closeDialog();
					//Eop.Dialog.close("show_workList");
				} else {
					alert(responseText.message);
				}
			}, 'json'); */
			
			
			$.ajax({  
                    type: "POST",  
                    data:"ajax=yes&order_id=" + order_id+"&operator_num="+operator_num+"&work_type="+work_type+"&work_reason="+work_reason+"&instance_id="+instance_id,  
                    url: ctx + "/shop/admin/ordAuto!save_work.do",  
                    dataType: "json",  
                    cache: false,  
                    success: function(responseText){   
	                    	if (responseText.result == 0) {
	                    		
								runNodeManual();
								
								if(window.parent.closeCustomWork){
									window.parent.closeCustomWork();
								}
							} else {
								alert(responseText.message);
							}  
						}  
					}); 
			
		}
	); 
	
	$("#work_back_btn").click(function() {
			if ($("#work_reason").val().trim() == "") {
				alert("请在备注中输入工单回收原因！");
				$("#work_reason").focus();
				return;
			}
			
			if(window.confirm('你确定要回收工单吗？')){
				var workReason = $("#work_reason").val().trim();
				
				//提交后台
				var param = {
					"order_id":order_id,
					"workReason":workReason
				};
				
				$.ajax({
			     	url:ctx+"/shop/admin/ordAuto!closeOrdWork.do?ajax=yes",
			     	type: "POST",
			     	dataType:"json",
			     	async:false,
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["result"]){
			     				alert(reply["message"]);
			     				
								//暂时 
								closeshow_workList();
								closeDialog();
			     			}else{
			     				var msg = reply["message"];
			     				alert("工单回收失败："+msg);
			     			}
			     		}else{
			     			alert("工单回收失败");
			     		}
			     	},
			     	error:function(msg){
			     		alert("工单回收失败："+msg);
			     	}
				});
            }
		}
	); 
	
	function closeshow_workList(){
		//关闭页面-意向单弹出改页面
		Eop.Dialog.close("addWork");
	};
	
	$("#operator_num").click(
			function() {
				if ($("#work_type").val() == "00") {
					alert("请选择工单类型！");
					return;
				}
			}
	);
	
	$("#operator_num").change(
			function() {
			}
	);
		
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
	var remark = $("#work_reason").val();
	
	instance_id = canvas.getCurrInsId();
	
	if("" == instance_id){
		alert("未找到当前环节！");
		return false;
	}
	
	canvas.runNodeManual(instance_id,condition,remark);
}
</script>
