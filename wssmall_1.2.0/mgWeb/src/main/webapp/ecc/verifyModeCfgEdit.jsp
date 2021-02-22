<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">校验方式配置</span><span class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"		name="theForm" id="theForm" >
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'>*</span>校验名称：</label></th>
				<td><input type="text" class="ipttxt"
					name="fun.fun_name" id="nombre" dataType="string" value="${fun.fun_name}"
					required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>校验方式：</label></th>
				<td>
					<select id="esimpl"  name ="fun.impl"   >
						<c:forEach items="${esimpls}"  var = "v">
							<option value="${v.value }" <c:if test="${fun.impl eq v.value }"> selected='selected'</c:if> >${v.value_desc }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>状态：</label></th>
				<td>				
					<select id="estado"    name ="fun.status"   >
						<c:forEach items="${estatos}"  var = "v">
							<option value="${v.value }" <c:if test="${fun.status eq v.value }"> selected='selected'</c:if> >${v.value_desc }</option>
						</c:forEach>
					</select>					
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>校验逻辑：</label></th>
				<td><textarea type="textarea"  rows="6" cols="140" name="fun.clazz" type="textarea" required="true" >${fun.clazz }</textarea></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>				
						<input type="hidden" name="fun.fun_id" 	value="${fun.fun_id}"> 
						<input type="button" id="btn" value=" 确  定 " class="submitBtn"  onclick="javascript:presentar();"/>
						<input type="button" id="btn" value=" 返  回 " class="submitBtn"  onclick="javascript:presentList();"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function presentar() {
		doAjax("theForm", "verifyModeCfg!save.do");
	}
	function presentList() {
		window.location.href="verifyModeCfg!list.do";
	}
</script>