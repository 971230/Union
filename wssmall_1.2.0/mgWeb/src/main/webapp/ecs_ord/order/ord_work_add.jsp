<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
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


<div class="input">
	<form class="validate" method="post" id="workform" validate="true" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
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
							<th style="width: 150px;text-align: right;">工单申请/回收备注：</th>
							<td><textarea rows="5" cols="88" id="work_reason" name="work_reason"></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="pop_btn" align="center">
				<a id="savebtn" class="blueBtns"><span>保 存</span></a>
				&nbsp;&nbsp;
				<a id="qryOrdWorkbtn" class="blueBtns"><span>工单详情</span></a>
				&nbsp;&nbsp;
				<!-- <a id="closeOrdWorkbtn" class="blueBtns"><span>工单回收</span></a> -->
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
<script>
var TempArr=[];//存储option 
var NumArr=[];//存储号码 

	$(function() {
		
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
				closeshow_workList();
				closeDialog();
				
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
		                        data:"ajax=yes&order_id=" + order_id+"&operator_num="+operator_num+"&work_type="+work_type+"&work_reason="+work_reason,  
		                        url: ctx + "/shop/admin/ordAuto!save_work.do",  
		                        dataType: "json",  
		                        cache: false,  
		                        success: function(responseText){   
		                        	if (responseText.result == 0) {
										alert(responseText.message);
										closeshow_workList();
										closeDialog();
										//Eop.Dialog.close("show_workList");
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
    
	
</script>
