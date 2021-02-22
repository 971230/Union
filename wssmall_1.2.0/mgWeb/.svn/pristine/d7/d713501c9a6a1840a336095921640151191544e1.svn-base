<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/search_rule.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/search_attr_factory.js" type="text/javascript"></script>
<style>
.trees ul li:hover {
	cursor: pointer;
}
</style>
<form method="post" id="rule_show_form"
	action="${pageContext.request.contextPath}/shop/admin/rule!ruleConfigList.do">
	<!-- 规则搜索结果 -->
	<div id="rule_search_reslut"></div>
	<div style="width:770px;">
		<div style="height:470px;" class="pop_cont">
			<div class="pop_left" style="height:450px; overflow:auto;">
				<div class="trees">
					<ul>
						<input type="hidden" value="" id="fact_obj_id"/>
						<input type="hidden" value="${plan_id}" id="plan_id" />
						<input type="hidden" value="${dir_id}" id="dir_id" />
						<c:forEach items="${ruleObjList}" var="ruleObj" varStatus="ruleObjStatus">
							<li name="rule_obj" obj_id="${ruleObj.obj_id}" obj_name="${ruleObj.obj_name}" obj_code="${ruleObj.obj_code}">
								<a href="javascript:void(0);"> <i class="treeic6"></i>${ruleObj.obj_name}
							</a>
								<c:choose>
									<c:when test="${ruleObjStatus.index == 0}">
										<ul>
									</c:when>
									<c:otherwise>
										<ul style="display: none;">
									</c:otherwise>
								</c:choose>
								<%-- <li name="fact_attr_name">
									<input fact_obj_id="${ruleObj.obj_id }" check_name="fact_attr_input_${ruleObj.obj_id }" type="checkbox" name="fact_attr_input" id="without_cond">
									<label for="without_cond">适用所有条件</label>
								</li>
								<li name="fact_attr_name">
									<input fact_obj_id="${ruleObj.obj_id }" check_name="fact_attr_input_${ruleObj.obj_id }" type="checkbox" name="fact_attr_input" id="never_run_cond">
									<label for="never_run_cond">任何条件都不满足</label>
								</li> --%>
								<c:forEach items="${ruleObj.objAttrList}" var="objAttr">
									<li name="fact_attr_name" >
										<input 
											check_name="fact_attr_input_${ruleObj.obj_id }"
											type="checkbox"
											fact_obj_id="${ruleObj.obj_id }"
											fact_attr_id="${objAttr.attr_id }"
											fact_attr_code="never_run_cond"
											id="attr_id_${objAttr.attr_id }"
											name="fact_attr_input"
											fact_attr_ele_type="${objAttr.ele_type }"
											fact_attr_stype_code="${objAttr.stype_code }"
											fact_attr_name="${objAttr.attr_name }"
											search_obj_code="${ruleObj.obj_code }"
											search_attr_code="${objAttr.attr_code }"
											/>
										<label for="attr_id_${objAttr.attr_id }"> 
											${objAttr.attr_name }
										</label>
									</li>
								</c:forEach>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="pop_right">
				<h3>属性值展示 </h3>
				<!-- <div class="ptit"><span class="name">是否匹配：</span>
				<select id="is_include">
					<option value="-1">--请选择--</option>
					<option value="1">匹配</option>
					<option value="0">不匹配</option>
				</select></div> -->
				<div class="ptit" id="fact_attr_name">
					<span class="name"></span>
				</div>
				<div id="fact_attr_value"></div>
				<div class="clear"></div>
				<div class="pop_btn">
					<c:choose>
					<c:when test="${source=='search_plan_cond' }">
						<a class="blueBtns" id="savePlanCond" href="javascript:void(0);"><span>确定</span></a>
					</c:when>
					<c:otherwise>
						<a class="blueBtns" id="searchRuleBtn" href="javascript:void(0);"><span>搜索</span></a>
					</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div>
			<input id="rule_id" type="hidden" value="${rule_id}" />
		</div>
	</div>
	
	<!-- 列表属性，弹出框 -->
	<div id="fact_list_attr_dlg"></div>
	
	<div id="workflow_list"></div>
	<!-- 页面属性模板 -->
	<div style="display: none;">
		<!-- 复选框模板 -->
		<div class="formCont" id="checkbox_tpl" style="display:inline-block;width:89%;">
			<label> 
				<input type="checkbox" checked="checked" value="" name="CheckboxGroup1">省仓
			</label>
		</div>
		<!-- 单选框模板 -->
		<div class="formCont" id="radio_tpl">
			<label> 
				<input type="radio" checked="checked" value="" name="RadioGroup1"> 自动化模式
			</label>
		</div>
		
		<!-- 列表模板 -->
		<div id="list_tpl" class="formCont" style="padding-top: 10px;">
			
		</div>
		<!-- 日期模板-->
		<div class="formCont" id="date_tpl">
			<input type="text" class="dateinput" value="" name="textfield">
		</div>
		<!-- 下拉框模板-->
		<div class="formCont" id="select_tpl">
			<select name="select" ></select>
		</div>
		<!-- 文本模板-->
		<div class="formCont" id="text_tpl">
			<input type="text" value="" name="textfield" id="">
		</div>
		<!-- 原样输出 -->
		<div class="formCont" id="html_tpl">
			适用于任务条件，总是执行该规则
		</div>
		<div class="formCont" id="never_run_tpl">
			任何情况都不执行
		</div>
		<div id="searchResultDiv" class="formCont" style="padding-top: 10px;">
			<table width="90%" cellspacing="0" cellpadding="0" border="0" class="grid_g">
				<tbody>
					<tr>
						<th>规则编号</th>
						<th>规则名称</th>
						<th>操作</th>
					</tr>
					<tr name="fact_rule_tr">
						<td name="fact_rule_id"></td>
						<td name="fact_rule_name"></td>
						<td><a href="javascript:void(0);" name="fact_rule_link" >配置规则</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</form>
<script type="text/javascript">
$(function(){
	FactList.cond_type = '${cond_type}';
	Eop.Dialog.init({id:"rule_search_reslut",modal:false,title:"规则搜索结果",width:"700px"});
	Eop.Dialog.init({id:"workflow_list",modal:false,title:"流程搜索结果",width:"700px"});
});
</script>