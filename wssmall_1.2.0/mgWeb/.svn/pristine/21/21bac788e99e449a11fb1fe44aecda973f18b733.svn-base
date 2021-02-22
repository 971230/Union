<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>

<div class="grid">

	<form action="regla!list.do" method="post">
		<div class="searchformDiv">
			<table>
				<tr>
					<td>规则名称： <input type="text" class="ipttxt"
						name="params.nombre" value="${params.nombre}" /></td>
					<td>靓号类型：<select name="params.tipo">
							<option value="">请选择状态</option>
							<c:forEach items="${tipos}" var="v">
								<option
									<c:if test="${params.tipo eq v.key }"> selected='selected'</c:if>
									value="${v.key }">${v.value }</option>
							</c:forEach></td>
					<td><input class="comBtn" type="submit" name="searchBtn"
						id="searchBtn" value="搜索" style="margin-right:30px;" /><input class="comBtn" type="button" id="searchBtn"
						value="添加" style="margin-right:10px;"
						onclick="window.location.href='regla!add.do'" /></td>
				</tr>

			</table>
		</div>

	</form>

	<form id="theForm" method="POST">
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell>规则名称</grid:cell>
				<grid:cell>靓号类型</grid:cell>
				<grid:cell>号码预存款</grid:cell>
				<grid:cell>合约期</grid:cell>
				<grid:cell>最低消费额度</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>${objeto.rule_name }</grid:cell>
				<grid:cell>${objeto.no_classify } </grid:cell>
				<grid:cell>${objeto.deposit }  </grid:cell>
				<grid:cell>${objeto.period }</grid:cell>
				<grid:cell>${objeto.lowest } </grid:cell>
				<grid:cell>
					<a href="regla!add.do?id=${objeto.rule_id}">修改</a>&nbsp;&nbsp;
					<a href="#" onclick="del('${objeto.rule_id }');">删除</a>
				</grid:cell>

			</grid:body>

		</grid:grid>
	</form>
	<div style="clear:both;padding-top:5px;"></div>
</div>
<script>
	function del(ids) {
		if (!confirm("确定要删除这条数据吗？")) {
			return false;
		}
		url = "regla!del.do?ids=" + ids;
		doAjax("theForm", url, "callBack");
	}
</script>