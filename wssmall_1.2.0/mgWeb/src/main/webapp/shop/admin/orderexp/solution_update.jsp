<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript">
loadScript("orderexp/js/solution_update.js");
</script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">解决方案管理修改</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>

<div class="input" id="fileName_dis" style="display:none;">
	<form action="${ctx}/shop/admin/solutionManage!uploadFile.do?addValue=0&solution_id=${returnList[0].solution_id}" method="post" enctype="multipart/form-data">
		   <input type="file" class="ipttxt" name="fileName" /> 
		   <input type="submit" value="上传"/> 
	</form>
	<input type="hidden" name="flag"  id="flag" value="${uploadValue }"/>
	<div style="display:none;color:red;" id="uploadFlag">${uploadFlag }</div>
	<input type="hidden" name="uploadPathHidden" id="uploadPathHidden" value="${uploadPath }"/>
	<!-- <input type="hidden" name="truthHidden" id="truthHidden" value="1"/><!--  -->
	<input type="hidden" name="returnView" id="returnView" value="${returnView }"/>
	<input type="hidden" name="saveSolution_value" id="saveSolution_value" value=""/>
</div>

<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="solutiontheUpdateForm" id="solutiontheUpdateForm" >
		<input type="hidden" name="ajax" value="yes"/>
		<input type="hidden" name="solutionInner.solution_id" value="${returnList[0].solution_id }"/>
		<table class="form-table">
			<%-- <tr>
				<th><label class="text"><span class='red'>*</span>方案管理id：</label></th>
				<td>
					<input type="text" class="ipttxt"  value = "${returnList[0].solution_id}" name = "solutionInner.solution_id" id="solution_id" required="true" readonly="readonly" />
				</td>
			</tr>--%>
			<tr>
				<th style="width:110px;"><label class="text"><span class='red'>*</span>方案管理类型：</label></th>
				<td>
					<select id="solution_type" disabled  name ="solutionInner.solution_type" >
						<c:choose>
							<c:when test="${returnList[0].solution_type eq 'DEFAULT' }">
								<option value="DEFAULT" selected='selected'>DEFAULT</option>
								<%--<option value="PLAN">PLAN</option>
								<option value="RULE">RULE</option>
								<option value="URL">URL</option>
								<option value="SQL">SQL</option>
								<option value="DOC">DOC</option> --%>
							</c:when>
							<c:when test="${returnList[0].solution_type eq 'PLAN' }">
								<%--<option value="DEFAULT" >DEFAULT</option> --%>
								<option value="PLAN" selected='selected'>PLAN</option>
								<option value="RULE">RULE</option>
								<option value="URL">URL</option>
								<option value="SQL">SQL</option>
								<option value="DOC">DOC</option>
							</c:when>
							<c:when test="${returnList[0].solution_type eq 'RULE' }">
								<%-- <option value="DEFAULT" >DEFAULT</option>--%>
								<option value="PLAN">PLAN</option>
								<option value="RULE" selected='selected'>RULE</option>
								<option value="URL">URL</option>
								<option value="SQL">SQL</option>
								<option value="DOC">DOC</option>
							</c:when>
							<c:when test="${returnList[0].solution_type eq 'URL' }">
								<%-- <option value="DEFAULT" >DEFAULT</option>--%>
								<option value="PLAN">PLAN</option>
								<option value="RULE" >RULE</option>
								<option value="URL" selected='selected'>URL</option>
								<option value="SQL">SQL</option>
								<option value="DOC">DOC</option>
							</c:when>
							<c:when test="${returnList[0].solution_type eq 'SQL' }">
								<%-- <option value="DEFAULT" >DEFAULT</option>--%>
								<option value="PLAN">PLAN</option>
								<option value="RULE">RULE</option>
								<option value="URL" >URL</option>
								<option value="SQL" selected='selected'>SQL</option>
								<option value="DOC">DOC</option>
							</c:when>
							<c:otherwise>
								<%-- <option value="DEFAULT" >DEFAULT</option>--%>
								<option value="PLAN">PLAN</option>
								<option value="RULE">RULE</option>
								<option value="URL">URL</option>
								<option value="SQL">SQL</option>
								<option value="DOC" selected='selected'>DOC</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<c:if test="${returnList[0].solution_type eq 'RULE' || returnList[0].solution_type eq 'PLAN'}">
				<tr>
					<th><label class="text"><span class='red'>*</span>是否删除日志：</label></th>
					<td>
						<select id="is_delete_rule_log"  name ="solutionInner.is_delete_rule_log" >
							<option value="N" <c:if test="${returnList[0].is_delete_rule_log == 'N' }">selected</c:if> >否</option>
							<option value="Y" <c:if test="${returnList[0].is_delete_rule_log == 'Y' }">selected</c:if> >是</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label class="text"><span class='red'>*</span>执行所需的Fact：</label></th>
					<td>
						<input type="text" class="ipttxt"  value = "${returnList[0].hander_fact }" name = "solutionInner.hander_fact" id="hander_fact" required="true" />
					</td>
				</tr>
			</c:if>
			<tr>
				<th><label class="text"><span class='red'>*</span>解决方案名称：</label></th>
				<td>
					<textarea  rows="3" cols="50" name = "solutionInner.solution_name" id="solution_name" required="true">${returnList[0].solution_name}</textarea>
				</td>
			</tr>
			<tr id="solution_value_dis">
				<th><label class="text"><span class='red'>*</span>方案管理配置值：</label></th>
				<td>
					<textarea  rows="10" cols="50" value = "" name = "solutionInner.solution_value" id="solution_value" required="true">${returnList[0].solution_value}</textarea>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>方案管理描述：</label></th>
				<td>
					<textarea  rows="10" cols="50" value = "" name = "solutionInner.solution_desc" id="solution_desc" required="true">${returnList[0].solution_desc}</textarea>
				</td>
			</tr>
			<c:if test="${returnList[0].solution_type eq 'RULE' || returnList[0].solution_type eq 'PLAN' || returnList[0].solution_type eq 'SQL'}">
				<tr>
					<th><label class="text"><span class='red'>*</span>是否支持批处理：</label></th>
					<td>
						<select id="is_batch_process"  name ="solutionInner.is_batch_process" >
							<option value="N" <c:if test="${returnList[0].is_batch_process == 'N' }">selected</c:if> >否</option>
							<option value="Y" <c:if test="${returnList[0].is_batch_process == 'Y' }">selected</c:if> >是</option>
						</select>
					</td>
				</tr>
			</c:if>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="button" id="btn" value="确  定 " class="submitBtn" onclick="submit_data();" />
						<input type="button" id="btn" value="返  回 " class="submitBtn" onclick="window.location.href='${ctx}/shop/admin/solutionManage!list.do'" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function submit_data(){
		/*if($("#solution_id").val() == ""){
			alert("请输入解决方案管理id");
			return false;
		}*/
		if($("#solution_type").val() == ""){
			alert("请选择解决方案管理类型");
			return false;
		}
		if($("#solution_name").val() == ""){
			alert("请输入解决方案名称");
			return false;
		}
		/*if($("#solution_value").val() == ""){
			if($("#solution_type").val() == "DOC"){
				alert("请先上传文档");
			}else{
				alert("请输入解决方案管理配置值");
			}
			return false;
		}*/
		if($("#solution_type").val() != "DOC"){
			if($("#solution_value").val() == ""){
				alert("请输入解决方案管理配置值");
				return false;
			}
		}
		if($("#solution_desc").val() == ""){
			alert("请输入解决方案管理方案描述");
			return false;
		}
		if($("#solution_type").val() == "SQL"){ //SQL类型
			var val = $("#solution_value").val();
			//判断sql语句类型
			var patrn = /^update|^UPDATE|^INSERT|^insert/i;
			if(!patrn.test(val)){
				alert("请输入更新或插入语句！");
				return false;
			}
			//判断语句中是否有join
			if(val.indexOf("join") > 0 || val.indexOf("JOIN") > 0){
				alert("sql语句中不能用JOIN!");
				return false;
			}
		}
		
		var json_data = $("#solutiontheUpdateForm").serialize();
		$.ajax({
			 type: "POST",
			 url: "solutionManage!update.do",
			 data: json_data,
			 dataType:"json",
			 success: function(result){
				 if (result.errorCode == '0') {
					 window.location.href = ctx + '/shop/admin/solutionManage!list.do';
			     } else {
			    	 alert(result.errorDesc);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}

	$(function(){
		//添加文件清空按钮点击事件
		$("#fileName_dis").find("input[type='button']").unbind("click").bind("click",function(){
			$("#uploadFlag").text("");
			$("#uploadPathHidden").val("");
			//设置新的文件上传输入框
			var ele = '<input type="file" class="ipttxt" name="fileName" />';
			$(this).prev().remove();
			$(this).before(ele);
		});
	});
</script>