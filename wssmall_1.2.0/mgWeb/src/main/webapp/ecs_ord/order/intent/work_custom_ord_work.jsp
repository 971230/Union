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
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th class="thstyle"><span class="red_mark">流转信息：</span></th>
							<td>
								<span id="span_flow_info" class="red_mark"></span>
							</td>
						</tr>
						<tr>
							<th>工单类型：</th>
							<td>
								<%-- <html:selectdict name="work_type" id="work_type"
									curr_val="${work_type}" style="color:#000"
									attr_code="DC_WORK_TYPE">
								</html:selectdict> --%>
								<div id="normal_radion">
									<input type="checkbox" class="radioclass" name="work_type" value="01">
									<span>收费单</span> 
									<input type="checkbox" class="radioclass" name="work_type" value="02">
									<span>外勘单</span>
									<input type="checkbox" class="radioclass" name="work_type" value="03">
									<span>实名单</span>
									<input type="checkbox" class="radioclass" name="work_type" value="04">
									<span>挽留单</span>
									<input type="checkbox" class="radioclass" name="work_type" value="05">
									<span>写卡单</span>
								</div>
								<div id="mixord_radion">
									<input type="checkbox" class="radioclass" name="work_type" value="06">
									<span>固移融合单</span>
								</div>
								<div id="intent_radion">
									<input type="checkbox" class="radioclass" name="work_type" value="07">
									<span>意向单</span>
								</div>
								<div id="refer_radion">
									<input type="checkbox" class="radioclass" name="work_type" value="08">
									<span>自传播</span>
								</div>
								<div id="enterprise_radion" style="display: none;">
									<input type="checkbox" class="radioclass" name="work_type" value="09">
									<span>政企工单</span>
								</div>
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
						
						<tr>
							<th style="width: 150px;text-align: right;">备注：</th>
							<td><textarea rows="5" cols="88" id="work_reason" name="work_reason"></textarea></td>
						</tr>
					
					</tbody>
				</table>
			</div>
			
			<div class="pop_btn" align="center">
				<a id="workflow_rollback_btn" onclick="rollback();" class="grayBtns"><span>回退到上一环节</span></a>
				<a id="savebtn" class="blueBtns" style="cursor: pointer;"><span>派单</span></a>
				<a id="qryOrdWorkbtn" class="blueBtns" style="cursor: pointer;"><span>工单详情</span></a>
				<a id="closeIntent" class="blueBtns" style="cursor: pointer;"><span>结单</span></a>
				<a id="workflow_submit_btn" onclick="runNodeManual();" class="grayBtns"><span>提交</span></a>
			</div>
			<div class = "searchBx">
				<a href="javascript:void(0);" id="show_params_tb" class="arr close" >展开</a>
				<a href="javascript:void(0);" id="hide_params_tb" class="arr open" style="display:none;">收起</a>
			</div>
			<input id="order_id" type="hidden" value="${order_id}" />
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
		
		initBtn();
		
		checkIsMixOrd();
		
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
			var order_id = $("#order_id").val();
			var statement_work = $("#work_reason").val().trim();
			if (work_reason == "") {
				alert("请输入结单备注！");
				return;
			}
			if(confirm("请确认是否结单!")) {} else {
				return;
			}
			$.ajax({  
                type: "POST",  
                data:"ajax=yes&order_id=" + order_id+"&statement_work="+statement_work,  
                url: ctx + "/shop/admin/orderIntentAction!closeIntent.do",  
                dataType: "json",  
                cache: false,  
                success: function(responseText){   
                	if(responseText.result == 0) {
						alert(responseText.message);
						if(window.parent.closeCustomWork){
							window.parent.closeCustomWork();
						}
					} else {
						alert(responseText.message);
					} 
                }  
            });
		});
		$("#savebtn").click(
						function() {
							var falg = 0; 
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
										alert(responseText.message);
										
										initBtn();
										
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
		$("#closeOrdWorkbtn11111").click(function() {
				if ($("#work_reason").val().trim() == "") {
					alert("请输入工单回收原因！");
					return;
				}
				if(window.confirm('你确定要回收工单吗？')){
	                 //alert("确定");
	                 //return true;
	            }else{
	                 //alert("取消");
	                 return;
	            }
				var order_id = $("#order_id").val();
				var url = ctx + "/shop/admin/ordAuto!closeOrdWork.do?ajax=yes&order_id=" + order_id;
				Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
					if (responseText.result == 0) {
						alert(responseText.message);
						//暂时 
						closeshow_workList();
						closeDialog();
						//Eop.Dialog.close("show_workList");
					} else {
						alert(responseText.message);
					}
				}, 'json');
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
		var order_id = $("#order_id").val();
		
		var url = ctx + "/shop/admin/ordAuto!isMixOrd.do?ajax=yes&order_id=" + order_id;
		
		Cmp.ajaxSubmit('workform', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				var isMixOrd = responseText.isMixOrd;
				var type = responseText.type;
				if(isMixOrd=="mixord"){
					$("#mixord_radion").show();
				}else if(isMixOrd=="intent"){
					$("#intent_radion").show();
				}else if(isMixOrd=="refer"){
					$("#refer_radion").show();
				}else if(isMixOrd=="normal"){
					$("#normal_radion").show();
					$("input[value='01']").attr("disabled", true);
					$("input[value='02']").attr("disabled", true);
					$("input[value='03']").attr("disabled", true);
					$("input[value='04']").attr("disabled", true);
					$("input[value='05']").attr("disabled", true);
					if(type!=""){
						var types = type.split(",");
						$.each(types, function (index, obj) {
							if(""!=types[index] && null!=types[index]){
								$("input[value='"+types[index]+"']").attr("disabled", false);
							}
						});
						if("" != responseText.should && null != responseText.should){
							if(responseText.should.indexOf("01")!=-1){
								$("input[value='01']").attr("disabled", false);
								checkedInput("01");
							}
							if(responseText.should.indexOf("03")!=-1){
								$("input[value='03']").attr("disabled", false);
								checkedInput("03");
							}
						}
					}
					return;
				}
				if(responseText.flag=="false"){
					disabledInput(type); 
				}else{
					checkedInput(type);
				}
			} else {
				alert(responseText.message);
			}
			//closeDialog();
		}, 'json');
	};
	
	function disabledInput(type){
		alert("存在未处理工单不能重复派发工单！");
		$("#typenum").attr("disabled",true);
		$("#makeupCo").attr("disabled",true); 
		$("#work_reason").attr("disabled",true);
		$("input[value='"+type+"']").attr("disabled", true);
	};
	
	function checkedInput(type){
		$("input[value='"+type+"']").attr("checked", true);
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

function initBtn(){
	var order_id = $("#order_id").val();
	
	var param = {
		"order_id":order_id
	};
	
	$.ajax({
     	url:ctx+"/shop/admin/ordAuto!getOrderIntentCount.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				var todo = reply["todo"];
     				var done = reply["done"];
     				
     				todo = parseInt(todo);
     				done = parseInt(done);
     				
     				if(todo > 0){
     					//有待办工单，不能回退，提交
     					$("#workflow_rollback_btn").removeClass();
     					$("#workflow_submit_btn").removeClass();
     					
     					$("#workflow_rollback_btn").addClass("grayBtns");
     					$("#workflow_submit_btn").addClass("grayBtns");
     					
     					$("#workflow_rollback_btn").removeAttr("style");
     					$("#workflow_submit_btn").removeAttr("style");
     				}else if(done > 0){
     					//有完成的工单，可以回退，可以提交
     					$("#workflow_rollback_btn").removeClass();
     					$("#workflow_submit_btn").removeClass();
     					
     					$("#workflow_rollback_btn").addClass("blueBtns");
     					$("#workflow_submit_btn").addClass("blueBtns");
     					
     					$("#workflow_rollback_btn").css("cursor","pointer");
     					$("#workflow_submit_btn").css("cursor","pointer");
     				}else{
     					//可以回退，不能提交
     					$("#workflow_rollback_btn").removeClass();
     					$("#workflow_submit_btn").removeClass();
     					
     					$("#workflow_rollback_btn").addClass("blueBtns");
     					$("#workflow_submit_btn").addClass("grayBtns");
     					
     					$("#workflow_rollback_btn").css("cursor","pointer");
     					$("#workflow_submit_btn").removeAttr("style");
     				}
     			}else{
     				var msg = reply["msg"];
     				alert("查询工单信息失败："+msg);
     			}
     		}else{
     			alert("查询工单信息失败");
     		}
     	},
     	error:function(msg){
     		alert("查询工单信息失败："+msg);
     	}
	});
}
</script>
