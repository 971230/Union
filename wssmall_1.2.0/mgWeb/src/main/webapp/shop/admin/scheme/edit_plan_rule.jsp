<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}
h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}
h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<div class="input" id="addPP">

<form class="validate" validate="true" method="post" action="" id='addParSss' >
<input type="hidden" name="action" value="edit"/>
<input name="plan_id" type="hidden" value="${plan.plan_id }">
		<div class="tableform">
			<h4>
				修改规则管理
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<th>
								方案名称：
							</th>
							<td id="member_lv_td_sp">
								 <input type="text" class=" ipttxt" id="plan_name" name="plan.plan_name" value="${plan.plan_name}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								方案编码：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="plan_code" name="plan.plan_code" value="${plan.plan_code}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								付款用户：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="admin_name" name="admin_name" value="${plan.admin_name}" required="true" autocomplete="on" dataType="string" readonly="readonly">
								<input type="hidden" id="admin_user_id" name="plan.pay_user_id" value="${plan.pay_user_id }" />
							</td>
						</tr>
						<tr>
							<th>
								方案类型：
							</th>
							<td>
								<select id="plan_type" class="ipttxt" name="plan.service_type" value="${plan.service_type }" style="width: 168px;height: 25px" required="true" >
								   <option  value="">--请选择--</option>
       					           <c:forEach var="type" items="${offer}">
					 	             <option  name="offType" value="${type.service_id }" ${service_id == service.service_type ? ' selected="selected" ':''}>${type.service_offer_name }</option>
					               </c:forEach>
								</select> 
							</td>
						</tr>
						<tr>
							<th>
								方案运行类型：
							</th>
							<td>
								 <select id="plan_exe_type" class="ipttxt" name="plan.run_type" value="${plan.run_type }"style="width: 168px;height: 25px" required="true" >
									<option value="D" ${(plan.run_type ==null || plan.run_type =='D')?'selected':'' }>直接调用</option>
									<option value="T" ${plan.run_type =='T'?'selected':'' }>定时执行</option>
								</select>   
							</td>
						</tr>
						
						<tr name="T_EXECUTE" ${plan.run_type =='D'?'style=display:none;':''}">
							<th>
								执行时间：
							</th>
							<td>
								 <input type="text"  name="plan.exec_time" id="cre1" value='${plan.exec_time}'
									readonly="readonly"
									maxlength="60" class="dateinput ipttxt"  dataType="date"/>  
									<span id="alarm_task_name_message"></span> 
							</td>
						</tr>
						<tr name="T_EXECUTE" ${plan.run_type =='D'?'style=display:none;':''}">
							<th>
								执行周期：
							</th>
							<td>
								<select id="exec_cycle" class="ipttxt" name="plan.exec_cycle" value="${plan.exec_cycle }"style="width: 168px;height: 25px" required="true" >
									<option value="MI" ${(plan.exec_cycle ==null || plan.exec_cycle =='MI')?'selected':'' }>分钟</option>
									<option value="DD" ${plan.exec_cycle =='DD'?'selected':'' }>天</option>
									<option value="WW" ${plan.exec_cycle =='WW'?'selected':'' }>周</option>
									<option value="MM" ${plan.exec_cycle =='MM'?'selected':'' }>月</option>
									<option value="QQ" ${plan.exec_cycle =='QQ'?'selected':'' }>季度</option>
									<option value="YY" ${plan.exec_cycle =='YY'?'selected':'' }>年</option>
								</select> 
								&nbsp;&nbsp;&nbsp;&nbsp;<span id="plan_cycleid" ${(plan.exec_cycle ==null || plan.exec_cycle =='MI')?'':'style=display: none' }><input type="text" name="plan.cycle_id" value="${plan.cycle_id }" class=" ipttxt" />&nbsp;&nbsp;分钟</span>
							</td>
						</tr>
						
						<tr name="T_EXECUTE" ${plan.run_type =='D'?'style=display:none;':''}">
							<th>
								有效期：
							</th>
							<td>
								 <input type="text"  name="plan.eff_date" id="eff_date"
									value="${plan.eff_date}" readonly="readonly"
									maxlength="60" class="dateinput ipttxt"  dataType="date"/>   
								 &nbsp;&nbsp;至	&nbsp;&nbsp;
								 <input type="text"  name="plan.exp_date" 
									value="${plan.exp_date}" readonly="readonly" id="exp_date"
									maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
							</td>
						</tr>
						
						<tr style="display: none;">
							<th>
								状态：
							</th>
							<td>
								 <select id="status_cd" class="ipttxt" name="plan.status_cd" value="${plan.status_cd }"style="width: 168px;height: 25px" required="true" >
									<option value="00N"  ${(plan.status_cd==null || plan.status_cd=='00N')?'selected':'' }>新建</option>
									<option value="00X" ${plan.status_cd=='00X'?'selected':'' }>无效</option>
									<option value="00A" ${plan.status_cd=='00A'?'selected':'' }>有效</option>
									<option value="00M"  ${plan.status_cd=='00M'?'selected':'' }>审核中</option>
								</select>   
							</td>
						</tr>
						<tr style="display: none;">
							<th>
								启动控制方式：
							</th>
							<td>
							    
								<input type="radio" name="plan.ctrl_type" value="sql" ${(plan.ctrl_type==null || plan.ctrl_type=='sql')?'checked':'' }/>sql
								<input type="radio" name="plan.ctrl_type" value="java" ${plan.ctrl_type=='java'?'checked':'' }/>java
							</td>
						</tr>
						<tr style="display: none;">
							<th>
								启动控制取值：
							</th>
							<td>
							 <textarea style="width: 60%;height: 60px;" class=" ipttxt" id="ctrl_val" name="plan.ctrl_val" value="${ctrl_val}" required="true" autocomplete="on" dataType="string">${plan.ctrl_val}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableform">
		    <h4>
				规则设置：     <a href="javascript:void(0);" style="margin-right:10px;" name="propss" id="addBtnProperty" class="graybtn1">规则设置</a>
			</h4>
		<div class="table_list" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="rule_attr">
					<tbody>
						<tr id="grid_head" style="height: 35px;">
							<td style="text-align:center;" >规则标识</td>
							<td style="text-align:center;" >规则名称</td>
							<td style="text-align:center;display: none;">组内排斥</td>
							<td style="text-align:center;display: none;">焦点</td>
							<td style="text-align:center;">规则优先级</td>
							<td style="text-align:center;">操作</td>
						</tr>
					</tbody>
					<tbody id="inputtext" class="division">
						 <c:forEach var="prop" items="${atts }">
						<tr >
							<td style="text-align:center;"><a name="edit_rule_a" href="javascript:void(0);return false;">${prop.rule_id }</a>
								<input type="hidden" name="ruleIdArray" value="${prop.rule_id }" />
							</td>
							<th style="text-align:center;" >${prop.rule_name }</th>
							<td style="text-align:center;display: none;"><input name="activation_group" type="text" class='ipttxt'value="${prop.activation_group }"/></td>
							<td style="text-align:center;display: none;">
								 <select id="status_cd" class="ipttxt" name="auto_focus" value="${prop.auto_focus }"style="width: 150px;height: 25px" required="true" >
									<option   value="true">true</option>
									<option   value="false">false</option>
								</select>
							</td>
							<td style="text-align:center;"><input name="priority" type="text" class='ipttxt' value="${prop.priority }"></td>
							<td style="text-align:center;"><a href="javascript:;"  name="deleteEditProper" class="delPaymentsc" >删除</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		</div>
		<div class="tableform">
	    <h4>
			中间数据设置:  <a href="javascript:void(0);" style="margin-right:10px;" id="add_proces" name="add_pro" class="graybtn1">添加</a>
		</h4>
		<div class="table_list">
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mid_config_process">
					<tbody>
						<tr id="grid_head" style="height: 35px;">
					     	<td style="text-align:center;display:none;">数据编码</td>
							<td style="text-align:center;width: 130px;">计算方式</td>
							<td style="text-align:center;">计算逻辑</td>
							<td style="text-align:center;">Fact的Java类型</td>
							<td style="text-align:center;">是否需要处理结果</td>
							<td style="text-align:center;">清单计算方式</td>
							<td style="text-align:center;display: none;">清单计算逻辑</td>
							<td style="text-align:center;">详情计算方式</td>
							<td style="text-align:center;display: none;">详情计算逻辑</td>
							<td style="text-align:center;width: 130px;"">操作</td>
						</tr>
					</tbody>
					<tbody id="midListss" class="division">
						<c:forEach var="accP" items="${midProcess}">
							 <tr>
								<input type='hidden' name='dataCode' value='${accP.mid_data_code}'>
								<input type='hidden' name='caltype' value='${accP.cal_type }'>
								<input type='hidden' name='calLogic' value='${accP.cal_logic }'>
								<input type='hidden' name='javaClass' value='${accP.fact_java_class }'>
								<input type='hidden' name='processData' value='${accP.need_process_data }'>
								<input type='hidden' name='listCalType' value='${accP.list_cal_type }'>
								<input type='hidden' name='listCallogic' value='${accP.list_cal_logic }'>
								<input type='hidden' name='detailCalType' value='${accP.detail_cal_type }'>
								<input type='hidden' name='detailCalLogic' value='${accP.detail_cal_logic }'>
								 
								<td style='text-align:center;display:none;'>${accP.mid_data_code}</td>
								<td style='text-align:center'>${accP.cal_type }</td>
								<td style='text-align:center'>${accP.cal_logic }</td>
								<td style='text-align:center'>${accP.fact_java_class }</td>
								<td style='text-align:center'>${accP.need_process_data }</td>
								<td style='text-align:center'>${accP.list_cal_type }</td>
								<td style='text-align:center;display: none;'>${accP.list_cal_logic }</td>
								<td style='text-align:center'>${accP.detail_cal_type }</td>
								<td style='text-align:center;display: none;'>${accP.detail_cal_logic }</td>
								<td style='text-align:center;'>
									<a href="javascript:;"  name="editPro"　id="${accP.mid_data_code }" class="edit_mid_proces" value="${accP.mid_data_code}">修改</a>	
									&nbsp;&nbsp;
									<a href="javascript:;"  name="delMid"　id="${accP.mid_data_code }" class="delMids" value="${accP.mid_data_code}">删除</a>	
								</td>
							</tr> 
						</c:forEach>
					</tbody>
					
				</table>
		</div>
		</div> 
		 <div class="submitlist" align="center" style="height: 100px">
			 <input type="button"  id="editSave" value=" 保存 " class="submitBtn" name='submitBtn'/>
		</div>

 	</form> 
 </div>
  <div id="addProcessMid"></div>
 <div id="editProcessMid"></div>
 <div id="ruleDiv"></div>
 
 <div id="rule_edit_dialog"></div>
 <div id="admin_user_select_dialog"></div>
 <input type="hidden" id="plan_rule_edit_v" value="yes" />
 
<script type="text/javascript" >

function selectAdminUser(){
	 var url = ctx + "/shop/admin/schemeAction!queryAdmin.do?ajax=yes";
	 var params = {};
	 var isSearchBtn = $(this).attr("id");
	 if(isSearchBtn=='searchAdminUserBtn'){
		 var userName = $("#admin_name_i").val();
		 if(userName)params = {admin_name:userName};
	 }else{
		 Eop.Dialog.open("admin_user_select_dialog");
	 }
	 $("#admin_user_select_dialog").load(url,params,function(){
		 $("#searchAdminUserBtn").bind("click",selectAdminUser);
		 $("#select_cfg_admin_Btn").bind("click",function(){
			 var admin = $("input[name=admin_user_id]:checked");
			 if(!admin && admin.length==0){
				 alert("请选择一条记录");
				 return ;
			 }
			 $("#admin_user_id").val(admin.attr("userid"));
			 $("#admin_name").val(admin.attr("user_name"));
			 Eop.Dialog.close("admin_user_select_dialog");
		 });
	 });
}

$(function(){
	 //编辑规则
	 Eop.Dialog.init({id:"rule_edit_dialog",modal:true,title:"编辑规则",height:"400px",width:"1200px"});
	 Eop.Dialog.init({id:"admin_user_select_dialog",modal:true,title:"选择付款用户",height:"400px",width:"1000px"});
	 //选择用户
	 $("#admin_name").bind("click",selectAdminUser);
	 
	 $("a[name=edit_rule_a]").live("click",function(){
		 Eop.Dialog.open("rule_edit_dialog");
		 var rule_id = $(this).html();
	     var url =ctx+"/shop/admin/rule!showConfigAdd.do?ajax=yes&action=1&rule_id="+rule_id; 
		 $("#rule_edit_dialog").load(url,function(){});
	 });
	 
	 $("#exec_cycle").bind("change",function(){
			var v = $(this).val();
			if("MI"==v){
				$("#plan_cycleid").show();
			}else{
				$("#plan_cycleid").hide();
			}
		 });
	 
});


var addRulement = {
	    init:function(){
	    var self = this;
	    Eop.Dialog.init({id:"addProcessMid",modal:true,title:"配置数据",height:"400px",width:"800px"});
	    Eop.Dialog.init({id:"editProcessMid",modal:true,title:"修改配置数据",height:"400px",width:"800px"});
	    Eop.Dialog.init({id:"ruleDiv",modal:true,title:"规则列表",height:"400px",width:"800px"});
	    
	    $("#plan_exe_type").bind("change",function(){
	    	 e_type = $(this).val();
	    	 if('T'==e_type){
	    		 $("tr[name=T_EXECUTE]").show();
	    	 }else{
	    		 $("tr[name=T_EXECUTE]").hide();
	    	 }
	     });
	    
	    $("a[name=propss]").unbind("click").bind("click",function() {
	    	self.addRule();
	     });
	     $("a[name=add_pro]").unbind("click").bind("click",function() {
	          self.addProcesed();
	     });
	     $(".delMids").bind("click",function(){
	          var mid_data_code = $(this).attr("value");
	          self.del(mid_data_code);
	      });
	     $("a[name='del_mid_procesed']").live("click",function(){
	      	if(!confirm("是否确定要删除?")){
	            return ;
	          } 
	      	var tr = $(this).closest("tr");
	      	tr.remove();
	      });
	      $("a[name='deleteEditProper']").live("click",function(){
		      	if(!confirm("是否确定要删除?")){
		            return ;
		          } 
		      	var tr = $(this).closest("tr");
		      	tr.remove();
		      });
	     $("a[name='del_mid_process']").live("click",function(){
		      	if(!confirm("是否确定要删除?")){
		            return ;
		          } 
		      	var tr = $(this).closest("tr");
		      	tr.remove();
		   });
	    $("a[name='deletedes']").live("click",function(){
		      	if(!confirm("是否确定要删除?")){
		            return ;
		          } 
		      	var tr = $(this).closest("tr");
		      	tr.remove();
		 });
	     $("a[name='delMid']").live("click",function(){
         	if(!confirm("是否确定要删除?")){
               return ;
             } 
         	var tr = $(this).closest("tr");
         	tr.remove();
         });
	     
	  },
	  addRule:function(){
	    	 Eop.Dialog.open("ruleDiv");
		     var url ="schemeAction!ruleList.do?ajax=yes"; 
		     $("#ruleDiv").load(url,function(){});
	  },
	    addProcesed : function() {
		     Eop.Dialog.open("addProcessMid");
		     var url ="schemeAction!addMidPro.do?ajax=yes"; 
		     $("#addProcessMid").load(url,function(){
	      });
	     },
	    /*  showEditProcess:function (mid_data_code,obj){
				$("#editProcessMid").load(ctx+"/shop/admin/schemeAction!updateMid.do?ajax=yes&mid_data_code="+mid_data_code,function(){
					$(obj).parents("tr").remove();
				});
			Eop.Dialog.open("editProcessMid");
		}, */
	}; 
	$(function(){
		addRulement.init();
		$("#editSave").click(function(){
			var plan_id=$("input[name='plan_id']").val();
			 url =ctx+"/shop/admin/schemeAction!editSave.do?ajax=yes&plan.plan_id="+plan_id;
			 Cmp.ajaxSubmit('addParSss', '', url, {}, function(reply){
				 if(reply.result==1){
			          alert("操作成功!");
			          location.href=ctx+"/shop/admin/schemeAction!schemeList.do";
			     }	 
		        if(reply.result==0){
					   alert("请添加规则和中间数据设置！");
		        }  
			 }, 'json');
		});
	});
	 $('.edit_mid_proces').live('click', function () {
  	    var tr1 = $(this).parents("tr");
		$("#editProcessMid").load('schemeAction!addMid.do?ajax=yes', function () {
			//点击修改保存时拿到文本框的值append到父页面，把原有的那条数据删除
			 $("#editSaveProcessed").unbind("click").bind("click",function(){
					var dataCode=$("input[name=mid_data_code]").val();
					var caltype=$("input[name=cal_type]:checked").val();
					var calLogic=$("textarea[name=cal_logic]").val();
					var javaClass=$("input[name=fact_java_class]").val();
					var processData=$("input[name=need_process_data]:checked").val();
					var listCalType=$("input[name=list_cal_type]:checked").val();
					var listCallogic=$("textarea[name=list_cal_logic]").val();
					var detailCalType=$("input[name=detail_cal_type]:checked").val();
					var detailCalLogic=$("textarea[name=detail_cal_logic]").val();
					
					var tr= "<tr >";
					tr += "<input type='hidden' name='dataCode' value='"+dataCode+"'>";
					tr += "<input type='hidden' name='caltype' value='"+caltype+"'>";
					tr += "<input type='hidden' name='calLogic' value='"+calLogic+"'>";
					tr += "<input type='hidden' name='javaClass' value='"+javaClass+"'>";
					tr += "<input type='hidden' name='processData' value='"+processData+"'>";
					tr += "<input type='hidden' name='listCalType' value='"+listCalType+"'>";
					tr += "<input type='hidden' name='listCallogic' value='"+listCallogic+"'>";
					tr += "<input type='hidden' name='detailCalType' value='"+detailCalType+"'>";
					tr += "<input type='hidden' name='detailCalLogic' value='"+detailCalLogic+"'>";
					 
					//tr += "<td style='text-align:center;display:none;'>"+dataCode+"</td>";
					tr += "<td style='text-align:center'>"+caltype+"</td>";
					tr += "<td style='text-align:center'>"+calLogic+"</td>";
					tr += "<td style='text-align:center'>"+javaClass+"</td>";
					tr += "<td style='text-align:center'>"+processData+"</td>";
					tr += "<td style='text-align:center'>"+listCalType+"</td>";
					tr += "<td style='text-align:center;display:none;'>"+listCallogic+"</td>";
					tr += "<td style='text-align:center'>"+detailCalType+"</td>";
					tr += "<td style='text-align:center;display:none;'>"+detailCalLogic+"</td>";
					
					tr += "<td style='text-align:center'><a href='javascript:void(0);' class='edit_mid_proces'>修改</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' name='del_mid_process'>删除</a></td>";
					tr += "</tr>";
					$("#midListss").append(tr);
					tr1.remove();
					Eop.Dialog.close("editProcessMid");
				}); 
			//拿到这条数据的值一个个的放入修改页面的文本框
		var dataCode=tr1.find("input[name='dataCode']").val();
		$("input[name=mid_data_code]").val(dataCode);
        var caltype=tr1.find("input[name='caltype']").val();
      	$("input[name=cal_type][value="+caltype+"]").attr("checked","checked");
      	var calLogic=tr1.find("input[name='calLogic']").val();
      	$("#cal_logic").val(calLogic);
      	var javaClass=tr1.find("input[name='javaClass']").val();
      	$("#fact_java_class").val(javaClass);
      	var processData=tr1.find("input[name='processData']").val();
      	$("#need_process_data").val(processData); 
      	var listCalType=tr1.find("input[name='listCalType']").val();
      	$("input[name=list_cal_type][value="+listCalType+"]").attr("checked","checked");
      	var listCallogic=tr1.find("input[name='listCallogic']").val();
      	$("#list_cal_logic").val(listCallogic);
      	var detailCalType=tr1.find("input[name='detailCalType']").val();
      	$("input[name=detail_cal_type][value="+detailCalType+"]").attr("checked","checked");
      	var detailCalLogic=tr1.find("input[name='detailCalLogic']").val();
      	$("#detail_cal_logic").val(detailCalLogic);
      });
      Eop.Dialog.open("editProcessMid");
  });
	 
</script>

