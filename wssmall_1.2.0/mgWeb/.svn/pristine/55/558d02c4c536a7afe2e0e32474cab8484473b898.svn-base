<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">靓号规则</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">

			<tr>
				<th><label class="text"><span class='red'>*</span>规则名称：</label></th>
				<td>
				
				<input type="text" class="ipttxt"
					name="regla.rule_name"  dataType="string" value="${regla.rule_name}"
					required="true" 				<c:if test="${regla.rule_id ne null }">" disabled="disabled"	</c:if>  /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>靓号类型：</label></th>
				<td>
					<c:if test="${regla.rule_id eq null }" >
						
						<select id="estado"    name ="regla.no_classify"   >
						<c:forEach items="${estatos}"  var = "v">
							<option value="${v.key }" >${v.value }</option>
						</c:forEach>
						</select>
						</c:if>
					
					<c:if test="${regla.rule_id ne null}" >
					<input type="text" class="ipttxt" name="regla.no_classify"  dataType="string" value="${regla.no_classify}" disabled="disabled"/>
					</c:if>
				</td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>号码预存款：</th>
				<td><input type="text" id="almacenado"class="ipttxt"  value = "${regla.deposit}" name = "regla.deposit" id="segmento" required="true" />&nbsp;&nbsp;元</td>
			</tr>
					<tr>
				<th><label class="text"><span class='red'>*</span>合约期：</th>
				<td><input id = "tope" type="text" class="ipttxt"  value = "${regla.period}" name = "regla.period" id="segmento" required="true" />&nbsp;&nbsp;月</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>最低消费额度：</th>
				<td><input type="text" id = "bajo" class="ipttxt"  value = "${regla.lowest}" name = "regla.lowest" id="segmento" required="true" />&nbsp;&nbsp;元/月</td>
			</tr>
			
					<tr>
				<th><label class="text">备注：</label></th>
				<td><textarea type="textarea"  rows="6" cols="40" name="regla.rule_desc" type="textarea">${regla.rule_desc}</textarea></td>
			</tr>

		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
					<input type="hidden" name="regla.rule_id"
						value="${regla.rule_id}"> <input type="button" id="btn"
						value=" 确  定 " class="submitBtn"  onclick="javascript:presentar();"/></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function presentar() {
		var call = devolucion();
		if(!comprobar()){
			return ;
		}
		doAjax("theForm", "regla!save.do", call);
	}

	function comprobar() {
		var almacenado = $("#almacenado").val();
		var tope = $("#tope").val();
		var bajo = $("#bajo").val();
		
		if (!isNaN(almacenado)) {
			MessageBox.show("号码预存款格式不正确,请重新输入", 3, 2000);
			return false;
		}
		if (!isNaN(tope)) {
			MessageBox.show("合约期格式不正确,请重新输入", 3, 2000);
			return false;
		}
		
		if (!isNaN(bajo)) {
			MessageBox.show("最低消费格式不正确,请重新输入", 3, 2000);
			return false;
		}
		
		return true
	}

	function devolucion() {

	}
</script>