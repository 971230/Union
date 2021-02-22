<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tpl_akeyset.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
 <script type="text/javascript">
 $(function(){
 Lucene.init('lucence_search_field');
 Lucene.init('lucence_search_table');
 })
	 
 </script>
 表名:<input type="text" style="width:250px" class="ipttxt" id="lucence_search_field" name="name" value="${field_name }" objType="goods" class="searchipt" tableColumn="name" keyFun="searchResult" />
字段名:<input type="text" style="width:250px" class="ipttxt" id="lucence_search_table" name="name" value="${table_name }" objType="goods" class="searchipt" tableColumn="name" keyFun="searchResult" />
	<!-- 订单模板信息配置 -->
	<!-- 
	 
	<form id="gridform" class="grid">
	<grid:grid from="webpage" ajax="yes" >
			<grid:header>
			<grid:cell>
					<input type="radio" id="toggleChk" value="选择记录"/>
				</grid:cell>
				<grid:cell>表名</grid:cell>
				<grid:cell>属性名</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="radionames" id="${obj.attr_spec_id}" value="${obj.attr_spec_id}" 
						agent="state" record_state="${obj.rel_table_name}">
				</grid:cell>
				<grid:cell>${obj.rel_table_name}</grid:cell>
				<grid:cell>${obj.field_name} </grid:cell>
			</grid:body>
	</grid:grid>
	<br><br><br>
	<table>
		<tr>
			<td>
				<a href="" id="obj_select_confirm_btn" class="blueBtns"><span>确定</span></a>
				<a href="" id="obj_select_not_btn" class="blueBtns"><span>取消</span></a>
			</td>
		</tr>
	</table>
	
</form>
 -->