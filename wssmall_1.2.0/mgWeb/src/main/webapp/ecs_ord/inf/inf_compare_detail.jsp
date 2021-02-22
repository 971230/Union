<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="inf_compare_detail" action="${pageContext.request.contextPath}/shop/admin/ordInf!infCompareDetail.do" >
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<td>
					<a href="ordInf!getInfList.do" style="margin-right:10px;" class="graybtn1">
						<span>返回</span>
					</a>
				</td>
				<th>全部：</th>
				<td>
					<input type="radio" name="qry_cond"  <c:if test="${qry_cond == null || qry_cond == '' || qry_cond == 'ALL'}">checked="checked"</c:if> value="ALL"/>
				</td>
				<th>已通过：</th>
				<td>
					<input type="radio" name="qry_cond" <c:if test="${qry_cond == 'SUCC'}"> checked="checked"</c:if> value="SUCC"/>
				</td>
				<th>未通过：</th>
				<td>
					<input type="radio" name="qry_cond" <c:if test="${qry_cond == 'FAIL'}"> checked="checked"</c:if> value="FAIL"/>
				</td>
				<th>可忽略字段：</th>
				<td>
					<input type="radio" name="qry_cond" <c:if test="${qry_cond == 'IGNORE'}"> checked="checked"</c:if> value="IGNORE"/>
				</td>
			</tr>
		</table>		
	</div>
	<div class="grid">
		<grid:grid from="webpage" ajax="yes" formId="inf_compare_detail">
			<grid:header>
				<grid:cell>节点路径</grid:cell>
				<grid:cell>字段名称</grid:cell>
				<grid:cell style="width:400px;">现网报文</grid:cell>
				<grid:cell style="width:400px;">订单系统</grid:cell>
				<grid:cell>对比结果</grid:cell>
			</grid:header>
			<grid:body item="inf">
				<grid:cell>
					<c:if test="${inf.filed_path == -1}">
						根目录
					</c:if>
					<c:if test="${inf.filed_path != -1}">
						${inf.filed_path}
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:choose>
						<c:when test="${inf.ignore_field == 'N' && inf.comp_result == '02'}">
							<font style="color: red;">${inf.filed_name}</font>
						</c:when>
						<c:when test="${inf.ignore_field == 'N' && (inf.comp_result == null || inf.comp_result == '')}">
							<font style="color: blue;">${inf.filed_name}</font>
						</c:when>
						<c:otherwise>
							${inf.filed_name}
						</c:otherwise>
					</c:choose>
				</grid:cell>
				<grid:cell>
					<c:if test="${inf.comp_result == '04'}">
						<a>列表数据</a>
					</c:if>
					<c:if test="${inf.comp_result != '04' }">
						${inf.old_filed_value}
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${inf.comp_result == '04'}">
						<a>列表数据</a>
					</c:if>
					<c:if test="${inf.comp_result != '04' }">
						${inf.new_filed_value}
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:choose>
						<c:when test="${inf.comp_result == '01'}">一致</c:when>
						<c:when test="${inf.comp_result == '02'}">不一致</c:when>
						<c:otherwise>
							无法比较
						</c:otherwise>
					</c:choose>
				</grid:cell>
			</grid:body>
		</grid:grid>
		<div style="clear:both;padding-top:5px;"></div>
	</div>
</form>
<script type="text/javascript">
	$(function(){
		$("input[name='qry_cond']").unbind("click").bind("click", function(){
			$("#inf_compare_detail").submit();
		});
	});
</script>