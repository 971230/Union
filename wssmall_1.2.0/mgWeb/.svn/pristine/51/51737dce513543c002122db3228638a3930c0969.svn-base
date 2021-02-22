<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">号码分组维护</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="theForm" id="theForm" >
		<table class="form-table">

			<tr>
				<th><label class="text"><span class='red'>*</span>分组名称：</label></th>
				<td><input type="text" class="ipttxt"
					name="ng.group_name" id="nombre" dataType="string" value="${ng.group_name}"
					required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>分组编码：</label></th>
				<td><input type="text" class="ipttxt"  value = "${ng.group_code}" name = "ng.group_code" id="codigo" required="true" /></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>状态：</th>
				<td>
				
					<select id="estado"    name ="ng.status"   >
					<c:forEach items="${estatos}"  var = "v">
						<option value="${v.key }" <c:if test="${ng.status eq v.key }"> selected='selected'</c:if> >${v.value }</option>
					</c:forEach>
					</select>
					
					</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>展示顺序：</th>
				<td><input type="text" class="ipttxt"  value = "${ng.sort_by}" name = "ng.sort_by" id="orden" required="true" /></td>
			</tr>

			<tr>
				<th><label class="text">备注：</label></th>
				<td><textarea type="textarea"  rows="6" cols="40" name="ng.group_desc" type="textarea">${ng.group_desc }</textarea></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
					<input type ="hidden" name ="params.estato" />
					<input type="hidden" name="ng.group_id"
						value="${ng.group_id}"> <input type="button" id="btn"
						value=" 确  定 " class="submitBtn"  onclick="javascript:presentar();"/></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function presentar() {
		if(!comprobar()){
			return ;
		}
		var order = $("#orden").val();
		if(order > 999){
			MessageBox.show("排序格式范围1-999,请重新输入", 3, 2000);
			return false;
		}
		doAjax("theForm", "grupo!save.do");
	}

	function comprobar() {
		var orden = $("#orden").val();
		if (!isNaN(orden)) {
			MessageBox.show("排序格式不正确,请重新输入", 3, 2000);
			return false;
		}
		return true
	}

</script>