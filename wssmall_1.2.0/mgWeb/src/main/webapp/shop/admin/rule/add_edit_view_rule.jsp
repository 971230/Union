<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
	border-style: none;
}
-->
</style>

<div class="input">
	<form class="validate" method="post" id="ruleForm" validate="true">
		<div>
			<div style="margin-top:5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr style="display: none;">
							<th>规则编号：</th>
							<td><input class="resigterIpt _x_ipt"  type="text" name="ruleConfig.rule_code" id="rule_code"  value="${ruleConfig.rule_code }" /></td>
						</tr>
						<tr>
							<th>规则名称：</th>
							<td><input class="resigterIpt _x_ipt"  type="text" name="ruleConfig.rule_name" id="rule_name" value="${ruleConfig.rule_name }" /></td>
							<input type="hidden" value="${ruleConfig.ruleRel.auto_exe }" id="auto_exe1" />
							<input type="hidden" value="${ruleConfig.ruleRel.rel_type }" id="rel_type1" />
						</tr>
						<tr style="display: none;">
							<th>生效时间：</th>
							<td><input style="width: 150px" dataType="date" class="dateinput ipttxt" type="text" name="ruleConfig.exp_date" id="eff_date"  value="${ruleConfig.eff_date }" /></td>
						</tr>
						<tr style="display: none;">
							<th>失效时间：</th>
							<td><input style="width: 150px" dataType="date" class="dateinput ipttxt" type="text" name="ruleConfig.eff_date" id="exp_date"  value="${ruleConfig.exp_date }" /></td>
						</tr>
						<tr style="display: none;">
							<th>审核状态：</th>
							<td>
								<input type="hidden" name="ruleConfig.status_cd" id="ruleConfig.status_cd"  value="00A" />
								<c:choose>
									<c:when test="${ruleConfig.status_cd == '00A'}">
									<input style="width: 150px" class="resigterIpt _x_ipt" type="text" value="有效" />
									</c:when>
									<c:when test="${ruleConfig.status_cd == '00N'}">
									<input style="width: 150px" class="resigterIpt _x_ipt" type="text" value="新建" />									
									</c:when>
									<c:when test="${ruleConfig.status_cd == '00X'}">
									<input style="width: 150px" class="resigterIpt _x_ipt" type="text" value="无效" />									
									</c:when>
									<c:when test="${ruleConfig.status_cd == '00M'}">
									<input style="width: 150px" class="resigterIpt _x_ipt" type="text" value="审核中" />									
									</c:when>
									<c:otherwise>
									<input style="width: 150px" class="resigterIpt _x_ipt" type="text" value="" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>规则描述：</th>
							<td>
								<%-- <input class="resigterIpt _x_ipt"  type="text" name="ruleConfig.rule_desc" id="rule_desc" value="${ruleConfig.rule_desc }" /> --%>
								<textarea rows="3" cols="35" name="ruleConfig.rule_desc" id="rule_desc" value="${ruleConfig.rule_desc }">${ruleConfig.rule_desc}</textarea>
							</td>
						</tr>
						<tr>
							<th>业务组件：</th>
							<td>
								<!-- <select name="ruleConfig.ass_id" id="selectId"></select> -->
								<input class="resigterIpt _x_ipt" type="text" name="ruleConfig.ass_name" id="ass_name" value="${ruleConfig.ass_name } " />
								<input type="hidden" name="ruleConfig.ass_id" id="ass_id" value="${ruleConfig.ass_id }" />
								<input type="hidden" name="ruleConfig.ass_code" id="ass_code" value="" />
								<input type="hidden" name="ruleConfig.exe_path" id="exe_path" value="" />
								<input type="hidden" name="ruleConfig.ass_status" id="ass_status" value="" />
								<c:if test="${flag != 'view' }">
								<a href="javascript:void(0);" class="blueBtns" id="xuanzezujian" ><span>选择组件</span></a>								
								</c:if>
							</td>
						</tr>
						<tr>
							<th>组件名称：</th>
							<td>
								${ruleConfig.ass_code }
								<input type="hidden" id="ass_code_view" value="${ruleConfig.ass_code }" readonly="readonly"/>
							</td>
						</tr>
						<input type="hidden" name="ruleConfig.resource_type" id="resource_type" value="DRL" />
						<input type="hidden" name="ruleConfig.rule_id" id="rule_id" value="${ruleConfig.rule_id }" /> 
						<input type="hidden" name="ruleConfig.pid" id="pid" value="${id }" />
						<input type="hidden" id="shangjiid" value="${ruleConfig.pid }" />
						<input type="hidden" name="planid" id="planid" value="${planid }" />
						<input type="hidden" name="ruleConfig.plan_id" id="plan_id" value="${ruleConfig.plan_id }" />
						<input type="hidden" name="flag" id="flag" value="${flag }" />
						<input type="hidden" name="rulexiugai" id="rulexiugai" value="${rulexiugai }" />
					</tbody>
				</table>
			</div>
			<c:if test="${flag != 'view'}">
				<div class="pop_btn">
					<a href="javascript:void(0);" class="blueBtns" id="savebtn" ><span>保 存</span></a>
				</div>
		</c:if>
		</div>
	</form>
</div>
<div id="choose_assembly_div"></div>
<script>
var Rule = {
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"choose_assembly_div",modal:false,title:"对象选择", height:"600px",width:"750px"});
		$("#xuanzezujian").bind("click",function(){
			var self = this;
			Eop.Dialog.open("choose_assembly_div");
			Rule.selectObj(self);
		});
	},
	selectObj:function(cobj){
		var url = ctx+"/shop/admin/ruleManager!qryAssemblyLs.do?ajax=yes";
		var name="";
		var obj = $("input[id='obj_name']");
		if(obj && obj.val()){
			name = obj.val();
		}
		$("#choose_assembly_div").load(url,{obj_name:name},function(){
			$("#rule_obj_searchBtn").bind("click",function(){Rule.selectObj(cobj);});
			$("#obj_select_confirm_btn").bind("click",function(){Rule.addSelectObj(cobj);});
		});
	},addSelectObj:function(cobj){
		var obj_id = $("input[type='radio'][name='radionames']:checked").val();
		if(obj_id == undefined){
			alert("请选择一个对象");
			return ;
		}
		var obj_ids = obj_id.split("##");
		$("#ass_id").val(obj_ids[0]!=undefined?obj_ids[0]:"");
		$("#ass_code").val(obj_ids[1]!=undefined?obj_ids[1]:"");
		$("#ass_code_view").val(obj_ids[1]!=undefined?$.trim(obj_ids[1]):"");
		$("#ass_name").val(obj_ids[2]!=undefined?obj_ids[2]:"")
		$("#exe_path").val(obj_ids[3]!=undefined?obj_ids[3]:"")
		$("#ass_status").val(obj_ids[4]!=undefined?obj_ids[4]:"");
		Eop.Dialog.close("choose_assembly_div");		
	}
};

$(function() {
	Rule.init();
	
	/* $("#xuanzezujian").bind("click", function() {
		var url = "/shop/admin/ruleManager!qryAssemblyLs.do";
		window.open(url,'newwindow', 'height=380,width=700,top=180,left=350,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	}); */
	
		/* $.ajax({
			type : "post",
			async : false,
			url : "ruleManager!qryAssemblyLs.do?ajax=yes&id=&assid=",
			data : {},
			dataType : "json",
			success : function(data) {
				if (data != null && data.length > 0) {
					$("#selectId").empty(); // 清空select
					$("#selectId").append("<option value='-1'>请选择组件</option>");
					var jsondata = eval(data);
					$.each(jsondata, function(index, optiondata) {
						if ($("#ass_id").val() == optiondata.ass_id)
							$("#selectId").append("<option value='"+optiondata.ass_id+"' selected>"+optiondata.ass_name+"</option>");
						else
							$("#selectId").append("<option value='"+optiondata.ass_id+"'>"+optiondata.ass_name+"</option>");
					});
				}
			}
		}); */
		
		/* $("#xuanzezujian").click(function() {
			var assembly = $.extend({},Eop.Grid,{
				dlg_id:'showDlg',
			    title:'选择组件',
				init:function(){
					var url = "ruleManager!qryAssemblyLs.do?ajax=yes";
					Eop.Dialog.init({id:'showDlg',modal:false,title:this.title,width:'450px'});
				},
				toUrlOpen:function(url){
					Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
					Eop.Dialog.open(this.dlg_id);
				},
				onAjaxCallBack:function(){//ajax回调函数	    
					$('input.dateinput').datepicker();
				},
				page_close:function(){
					Eop.Dialog.close(this.dlg_id);
				},				
			});
			
			
			
			alert("aaaaaaaaa");
			var  url = ctx+ "/shop/admin/ruleManager!qryAssemblyLs.do?ajax=yes";
			Cmp.ajaxSubmit('ruleForm', '', url, {}, function(responseText) {
				if (responseText.result == 0) {
					alert(responseText.message);
					location.reload();
				}
			}, 'json');
		}); */
		var ass_id = $("#ass_id").val();
		if (!($.trim(ass_id)) || ass_id != undefined) {
			$.ajax({
				type : "post",
				async : false,
				url : "ruleManager!qryAssembly.do?ajax=yes&ass_id="+ass_id,
				data : {},
				dataType : "json",
				success : function(data) {
					data = eval(data);
					if (data != null) {
						$("#ass_id").val(data.ass_id);
						$("#ass_code").val(data.ass_code);
						$("#ass_name").val(data.ass_name)
						$("#exe_path").val(data.exe_path)
						$("#ass_status").val(data.ass_status);
					}
				}
			});
		}
		
		if ($("#flag").val() == "view") {
			$("#rule_code").attr("disabled","disabled")
			$("#rule_name").attr("disabled","disabled")
			$("#eff_date").attr("disabled","disabled")
			$("#exp_date").attr("disabled","disabled")
			$("#rule_desc").attr("disabled","disabled")
			$("#ass_name").attr("disabled","disabled");
			$("#status_cd").attr("disabled","disabled");
		}
		$("#ruleForm").validate();
		$("#savebtn").click(function() {
			if ($("#pid").val() == "") {
				$("#pid").val("0");
			}
			if ($("#rulexiugai").val() == "yes") { // rulexiugai=yes 则是修改规
				$("#pid").val($("#shangjiid").val());
			}
			if ($("#rule_desc").val() == "") {
				alert("请输入描述信息！");return;
			}
			$("#plan_id").val($("#planid").val());
			var  url = ctx+ "/shop/admin/ruleManager!addEditViewRule.do?ajax=yes";
			Cmp.ajaxSubmit('ruleForm', '', url, {}, function(responseText) {
				if (responseText.result == 0) {
					alert(responseText.message);
					Directory.page_close();
					pid = $("#pid").val();
					plan_id = $("#plan_id").val();
					rule_id = $("#rule_id").val();
					if (pid != 0) {
						$("#rulenode_id"+pid).click();
					}
					else {
						if ($("#flag").val()=="save") {
							$("#schemenode_id"+plan_id).click();
						}
					}
					if ($("#flag").val()=="edit") {
						auto_exe1 = $("#auto_exe1").val();
						rel_type1 = $("#rel_type1").val();
						var _ic = getIcByRel(rel_type1);
						if (auto_exe1 == 0) {
							auto_exe1 = "自动";
						}
						if (auto_exe1 == 1) {
							auto_exe1 = "手动";
						}
						$("#rulenode_id"+rule_id).html(_ic+""+($("#rule_name").val()+"("+auto_exe1+")"));
					}
				}
			}, 'json');
		});
	});
</script>