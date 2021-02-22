<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">规则操作</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="rule.rule_id" value="${rule.rule_id}"/>
			<tr>
				<th><label class="text"><span class='red'>*</span>规则名称：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_name"
					dataType="string" required="true" value="${rule.rule_name}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>规则编码：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_code"
					dataType="string" required="true" value="${rule.rule_code}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'></span>规则描述：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_desc"
					dataType="string" required="true" value="${rule.rule_desc}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>规则处理类：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_java"
					dataType="string" required="true" value="${rule.rule_java}"/></td>
			</tr>
			<tr>
				<th><label class="text">规则类型：</label></th>
				<td><select class="ipttxt" style="width: 100px"
						name="rule.rule_type">
							<c:forEach var="list" items="${ruleTypeList}">
								<option value="${list.pkey }"
									${rule.rule_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>生效时间：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_time_begin" readonly="readonly"
					dataType="date" required="true" value="<html:dateformat pattern="yyyy-MM-dd"
							d_time="${rule.rule_time_begin}"></html:dateformat>"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>失效时间：</label></th>
				<td><input type="text" class="ipttxt" name="rule.rule_time_end" readonly="readonly"
					dataType="date" required="true" value="<html:dateformat pattern="yyyy-MM-dd"
							d_time="${rule.rule_time_end}"></html:dateformat>"/></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td> <input type="submit" id="btn"
						value=" 确  定 " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	$(function(){
		$("input[dataType='date']").datepicker();
		
		$(".submitBtn").bind("click",function(){
			$("#theForm.validate").validate();
			var url = ctx+ "/shop/admin/sysRule!save.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, AddRule.jsonBack,'json');
		});
	});
	
	
	var AddRule = {
		jsonBack:function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="sysRule!listRule.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
</script>