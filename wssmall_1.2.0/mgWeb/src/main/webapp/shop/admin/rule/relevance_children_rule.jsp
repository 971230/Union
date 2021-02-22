<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
	border-style: none;
}
-->
</style>

<div class="pop_cont" style="height:230px;">
	<div class="pop_warp">
		<div class="formCont" style="margin-top:10px;">
		<form id="ruleform0">
		
			<input type="hidden" value="" id="attr_children_id" name="editChildrenRuleVo.attr_children_id" />
			<input type="hidden" value="" id="attr_rule_code" name="editChildrenRuleVo.attr_rule_code" />
			<input type="hidden" value="" id="attr_rule_name" name="editChildrenRuleVo.attr_rule_name" />
			<input type="hidden" value="" id="attr_priority" name="editChildrenRuleVo.attr_priority" />
			<input type="hidden" value="" id="attr_rel" name="editChildrenRuleVo.attr_rel" />
			<input type="hidden" value="" id="attr_relyon" name="editChildrenRuleVo.attr_relyon" />
			<input type="hidden" value="" id="attr_status" name="editChildrenRuleVo.attr_status" />
			<input type="hidden" value="" id="attr_autoexe" name="editChildrenRuleVo.attr_autoexe" />
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="grid_g" id="relevance_tab_id">
				<tr>
					<th style="display: none">选择</th>
					<th>规则编号</th>
					<th>规则名称</th>
					<th><a href="javascript:void(0);" id="youxianji" >优先级</a></th>
					<th>规则关系</th>
					<th>关系值</th>
					<th>审核状态</th>
					<th>执行方式</th>
					<th style="display: none;">操作</th>
				</tr>
				<c:forEach var="ruleConfig" items="${ruleConfigList }" varStatus="status" >
					<tr id="tr_id${ruleConfig.ruleRel.rule_id }" tr_attr="tr_attr" tr_order="${status.count }">
						<input type="hidden" id="rule_id${ruleConfig.ruleRel.rule_id }" value="${ruleConfig.ruleRel.rule_id }" />
						<td class="align_left" style="display: none;">
							<input type="hidden" attr_children_id="attr_children_id" value="${ruleConfig.ruleRel.rule_id }" />
							<input name="checkbox" type="checkbox" id="checkbox" /> ${ruleConfig.rule_code }
						</td>
						<td class="align_left" id="rule_code">
							${ruleConfig.rule_code }
							<input attr_rule_code="attr_rule_code" readonly="readonly" name="rule_code" type="hidden" id="rule_code${ruleConfig.ruleRel.rule_id }"  value="${ruleConfig.rule_code }"/>
						</td>
						<td class="align_left">
							${ruleConfig.rule_name }
							<input attr_rule_name="attr_rule_name" readonly="readonly" name="rule_name" type="hidden" id="rule_name${ruleConfig.ruleRel.rule_id }" value="${ruleConfig.rule_name }"/>
						</td>
						<td><select id="priority_select" attr_priority="attr_priority">
							<c:choose>
								<c:when test="${is_first_load == 'first' }">
									<option value="">选择优先级</option>
									<c:forEach var="i" begin="1" end="${selectSize}" step="1">
										<c:choose>
											<c:when test="${i == status.count}">
												<option value="${i }" selected="selected">${i }</option>
											</c:when>
											<c:otherwise>
												<option value="${i }">${i }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<option value="">选择优先级</option>
									<c:forEach var="i" begin="1" end="${selectSize}" step="1">
									<c:choose>
									<c:when test="${ruleConfig.ruleRel.priority != i}">
										<option value="${i }">${i }</option>
									</c:when>
									<c:otherwise>
										<option value="${i }" selected="selected">${i }</option>
									</c:otherwise>
									</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select></td>
						<td><select name="attr_rel" attr_rel="attr_rel" attr_id="rel_select" id="rel_select${ruleConfig.ruleRel.rule_id}" select_id="${ruleConfig.ruleRel.rule_id }">
								<option sel_id="${ruleConfig.ruleRel.rule_id}" value="">选择关联关系</option>
								<option sel_id="${ruleConfig.ruleRel.rule_id}"  value="0" <c:if test="${ruleConfig.ruleRel.rel_type==0 }">selected</c:if>>并行</option>
								<option sel_id="${ruleConfig.ruleRel.rule_id}"  value="1" <c:if test="${ruleConfig.ruleRel.rel_type==1 }">selected</c:if>>依赖</option>
								<option sel_id="${ruleConfig.ruleRel.rule_id}"  value="2" <c:if test="${ruleConfig.ruleRel.rel_type==2 }">selected</c:if>>互斥</option>
								<option sel_id="${ruleConfig.ruleRel.rule_id}"  value="3" <c:if test="${ruleConfig.ruleRel.rel_type==3 }">selected</c:if>>全部互斥</option>
						</select></td>
						<td>
							<select attr_relyon="attr_relyon" id="relyon_select${ruleConfig.ruleRel.rule_id}" name="attr_relyon_val">
								<c:if test="${ruleConfig.ruleRel.rel_type == 1 || ruleConfig.ruleRel.rel_type==2}">
									<option value="" >请选择依赖规则ID</option>
								<c:forEach var="relyon_rule" items="${ruleConfigList }">
									<c:choose>
										<c:when test="${ruleConfig.ruleRel.relyon_rule_id == relyon_rule.ruleRel.rule_id }">
										<option value="${relyon_rule.rule_id }" selected="selected">${relyon_rule.rule_name }</option>
										</c:when>
										<c:otherwise>
										<option value="${relyon_rule.rule_id }">${relyon_rule.rule_name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								</c:if>
							</select></td>
						<td><select attr_status="attr_status" id="status_select">
								<option value="">选择审核状态</option>
								<option <c:if test="${ruleConfig.status_cd=='00A' }">selected</c:if> value="00A">有效</option>
								<option <c:if test="${ruleConfig.status_cd=='00X' }">selected</c:if> value="00X">无效</option>
						</select></td>
						<td><select attr_autoexe="attr_autoexe" id="autoexe_select">
								<option value="">选择执行方式</option>
								<option <c:if test="${ruleConfig.ruleRel.auto_exe=='0' }">selected</c:if> value="0">自动</option>
								<option <c:if test="${ruleConfig.ruleRel.auto_exe=='1' }">selected</c:if> value="1">手动</option>
						</select></td>
						<td style="display: none;"><a href="javascript:void(0);" onclick="deltr(this)">删除</a></td>
					</tr>
				</c:forEach>
			</table>
			<div class="pop_btn">
				<a href="javascript:void(0);" class="blueBtns" id="surebtn"><span>确 定</span></a>
			</div>
			</form>
		</div>
	</div>
</div>
<input type="hidden" id="pid" value="${id }" />
<input type="hidden" id="plan_id" value="${plan_id }" />
<script>

$(function() {
	
	$("select[attr_id='rel_select']").bind("change", function() {
		select_id = $(this).attr("select_id");
		var $this = this;
		var rel_type = $this.value;
		if (rel_type == 1 || rel_type == 2) {
			
			$("#relyon_select"+select_id).empty();
			$.ajax({
				type : "post",
				async : false,
				url : "ruleManager!qryChilrenRuleList.do?ajax=yes&plan_id="+$("#plan_id").val()+"&id="+$("#pid").val(),
				data : {},
				dataType : "json",
				success : function(data) {
					var data = eval(data);
					if (data != null && data.length > 0) {
						optionStr = "<option value='-1'>请选择依赖规则ID</option>";
						$("#relyon_select"+select_id).append(optionStr);
						$.each(data, function(i, ruleconfig) {
							optionStr = "<option value='"+ruleconfig.rule_id+"'>"+ruleconfig.rule_name+"</option>";
							$("#relyon_select"+select_id).append(optionStr);
						});
					}
				}
			});
		} else if (rel_type == 3) {
			var attr_relyon_val = document.getElementsByName("attr_relyon_val");
			for (var d = 0; d < attr_relyon_val.length; d ++) {
				$("#"+attr_relyon_val[d].id).empty();;
			}
			var attr_rel_array = $("select[attr_rel='attr_rel']");
			for (var a=0; a<attr_rel_array.length; a++) {
				attr_rel_array[a].value=3;
			}
		} else {
			$("#relyon_select"+select_id).empty();
		}
	});
	
	$("#surebtn").bind("click", function() {
		
		var arr = $("select[attr_rel='attr_rel']");
		for (var m=0;m<arr.length;m++) {
			var v = $(arr[m]).val();
			if (v == '3') {
				for (var w=0;w<arr.length;w++) {
					var va = $(arr[w]).val();
					if (va != '3') {
						alert("请确认所有规则全部互斥！");
						return;
					}
				}
			}
		}
		
		var attr_children_id = "";
		var attr_rule_code = "";
		var attr_rule_name = "";
		var attr_priority = "";
		var attr_rel = "";
		var attr_relyon = "";
		var attr_status = "";
		var attr_autoexe = "";

		$("input[attr_children_id='attr_children_id']").each(function(index, data) {
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_children_id += ($(this).val()+",");
			} else {
				attr_children_id += ",";
			}
		});
		$("input[attr_rule_code='attr_rule_code']").each(function(index, data) {
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_rule_code += ($(this).val()+",");
			} else {
				attr_rule_code += ",";
			}
		});
		$("input[attr_rule_name='attr_rule_name']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_rule_name += ($(this).val()+",");
			} else {
				attr_rule_name += ",";
			}
		});
		$("select[attr_priority='attr_priority']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_priority += ($(this).val()+",");
			} else {
				attr_priority += ",";
			}
		});
		$("select[attr_rel='attr_rel']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined && $(this).val()!="") {
				attr_rel += ($(this).val()+",");
			} else {
				attr_rel += "0,";
			}
		});
		$("select[attr_relyon='attr_relyon']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_relyon += ($(this).val()+",");
			} else {
				attr_relyon += ",";
			}
		});
		$("select[attr_status='attr_status']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_status += ($(this).val()+",");
			} else {
				attr_status += ",";
			}
		});
		$("select[attr_autoexe='attr_autoexe']").each(function(index){
			if ($(this).val() != null && $(this).val() != undefined) {
				attr_autoexe += ($(this).val()+",");
			} else {
				attr_autoexe += ",";
			}
		});
		$("#attr_children_id").val(attr_children_id);
		$("#attr_rule_code").val(attr_rule_code);
		$("#attr_rule_name").val(attr_rule_name);
		$("#attr_priority").val(attr_priority);
		$("#attr_rel").val(attr_rel);
		$("#attr_relyon").val(attr_relyon);
		$("#attr_status").val(attr_status);
		$("#attr_autoexe").val(attr_autoexe);
		var  url = ctx+ "/shop/admin/ruleManager!editChildrenRule.do?ajax=yes";
		$.ajax({
			type : "post",
			url : url,
			data : {"editChildrenRuleVo.attr_children_id":attr_children_id,
				 "editChildrenRuleVo.attr_rule_code":attr_rule_code,
				 "editChildrenRuleVo.attr_priority":attr_priority,
				 "editChildrenRuleVo.attr_rel":attr_rel,
				 "editChildrenRuleVo.attr_relyon":attr_relyon,
				 "editChildrenRuleVo.attr_status":attr_status,
				 "editChildrenRuleVo.attr_autoexe":attr_autoexe},
			dataType : "json",
			success : function(responseText) {
				alert(responseText.message);
				var plan_id = $("#plan_id").val();
				var pid = $("#pid").val();
				
				Directory.page_close();
				
				if (pid != 0) {
					$("li[id=rulenode_li"+pid+"] ul").remove();
					var $this = nodeul.children("li").children("ul").hide();
					$("a[id='rulenode_id'"+pid+"]").click();
				} else {
					$("li[id=schemenode_li"+plan_id+"] ul").remove();
					$("a[id='schemenode_id"+plan_id+"']").click();
				}
			}
		});
	});
	
	$("#youxianji").bind("click", function() {
		var aTrCont = $("#table tr");
		var aSelectCont = $("select[id=priority_select]");
		for (var c=1; c<=aSelectCont.length; c++) {
			$("select[id=priority_select]").each(function(e){
				var sel_val = this.value;
				var tr = this.parentNode.parentNode;
				if (c == sel_val) {
					$("#relevance_tab_id").append($(tr));
				}
			});
		}
	});
	
});

function deltr(del) {
	var tr = $(del).parent().parent();
	tr.remove();
}

</script>

