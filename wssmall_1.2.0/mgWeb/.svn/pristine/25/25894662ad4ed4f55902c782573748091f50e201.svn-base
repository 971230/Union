<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
	border-style: none;
}
-->

.grid table td {
    border-bottom: 1px dashed #e3e3e3;
    color: #666;
    height: 30px;
    text-align: left;
    vertical-align: middle;
}
.grid table th {
    background: none repeat scroll 0 0 #f6f6f6;
    border-bottom: 1px solid #cdcdcd;
    border-top: 1px solid #cdcdcd;
    color: #434343;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
    text-align: left;
    vertical-align: middle;
}
</style>

<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		对象名称：
		</th>
		<td>
		 <input type="text" class="ipttxt"  name="obj_name" id="obj_name" value="${obj_name }"/>
		<a href="javascript:void(0)" id="rule_obj_searchBtn" class="blueBtns"><span>搜索</span></a>
		<a id="shuaxinyewuzujian" href="javascript:void(0);" class="blueBtns"><span>刷新业务组件</span></a>
		</td>
	</tr>
</table>
</div>
<div class="grid">
<form method="POST" id="ruleForm">
		<grid:grid from="webpagelist" ajax="yes" formId="ruleForm">
			<grid:header>
				<grid:cell style="width: 53px;">选择</grid:cell>
				<grid:cell style="width: 42px;">动作名称</grid:cell>
				<%-- <grid:cell style="width: 165px;">动作执行路径</grid:cell> --%>
				<grid:cell style="width: 5px;">状态</grid:cell>
			</grid:header>
			<grid:body item="assemblyList" >
				<grid:cell>
					<input type="radio" id="radios_"+${assemblyList.ass_id } 
					name="radionames" value="${assemblyList.ass_id }##${assemblyList.ass_code }
					##${assemblyList.ass_name}##${assemblyList.exe_path }##${assemblyList.status } " />
					${assemblyList.ass_code }
				</grid:cell>
				<grid:cell>${assemblyList.ass_name }</grid:cell>
				<%-- <grid:cell>${assemblyList.exe_path }</grid:cell> --%>
				<grid:cell>
					<c:if test="${assemblyList.status==0 }">有效</c:if>
        			<c:if test="${assemblyList.status==1 }">无效</c:if>
					<input type="hidden" id="status" value="${assemblyList.status }" />
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<div class="pop_btn">
	<a href="javascript:void(0)" id="obj_select_confirm_btn" class="blueBtns"><span>确定</span></a>
</div>

<%-- <tr>
			<th>选择</th>
			<th>动作名称</th>
			<th>动作执行路径</th>
			<th>状态</th>
		</tr>
		<c:forEach items="${assemblyList }" var="assembly">
			<tr class="double">
				<td class="align_left"><input type="radio" id="radio_"
					+${assembly.ass_id } name="radionames"
					value="${assembly.ass_id }##${assembly.ass_code  }##${assembly.ass_name}
							##${assembly.exe_path }##${assembly.status } " />
					${assembly.ass_code }</td>
				<td class="align_left">${assembly.ass_name }</td>
				<td class="align_left">${assembly.exe_path }</td>
				<td><c:if test=${assembly.status=0}”>有效</c:if> <c:if
						test=${assembly.status=1}”>无效</c:if> <input type="hidden"
					id="status" value="${assembly.status }" /></td>
			</tr>
		</c:forEach>
	</table>
</div>
 --%>


<script>
	$(function() {
		$("#surebtn").click(function() {
			var radios = document.getElementsByName("radionames");
			var radio_values = "";
			var j = -1;
			for ( var i = 0; i < radios.length; i++) {
				if (radios[i].checked == true) {
					radio_values += radios[i].value;
					alert("radio_values " + radio_values);
					j++;
					break;
				}
			}
			if (j < 0) {
				alert("请选择一条记录后在点击确认！！");
				return;
			}
		});

		$("#shuaxinyewuzujian").click(function(){
			$.ajax({
				type : "post",
				async : false,
				url : "ruleManager!refreshAssembly.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data.result == 0) {
						alert("刷新 ： "+data.size+" 条组件记录！");
					} else {
						alert(data.message);
					}
				}
			});
		});
		
	});
</script>
