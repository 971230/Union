<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>

<div class="input" align="center">
	<form name="theForm" id="theForm">
	<input type="hidden" name="ids" value =${ids } /> 
<br>
	号码状态：&nbsp;&nbsp;&nbsp;<select name="params.estado" style="width: 100px" id = "estado">
						<option value="">请选择状态</option>
						<c:forEach items="${estados}" var="v">
							<option value="${v.key }">${v.value }</option>
						</c:forEach>
</select><br><br>
号码分组：&nbsp;&nbsp;&nbsp;<select name="params.grupo" style="width: 100px" id="grupo">
						<option value="">请选择分组</option>
						<c:forEach items="${grupos}" var="v">
							<option value="${v.key }">${v.value }</option>
						</c:forEach>
				</select>
				<br>
				<br>		<br>
			<div class="submitlist" align="center">
				<input class="comBtn" type="button"
					value="确定" onclick="doModificar();" style="margin-right:10px;" />
			</div>
			<br>			
			</form>
</div>
<script>
	function doModificar() {
		var url = "numero!doModificar.do";
		var grupo = $("#grupo").val();
		var estado = $("#estado");
		if(estado==""){
			MessageBox.show("请选择需要修改的状态", 2, 3000);
			return;
		}else if(grupo==""){
			MessageBox.show("请选择分组", 2, 3000);
			return ;
		}
		doAjax("theForm", url, "callBack");
	}
</script>