<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- 订单模板信息配置 -->
 
	<table>
		<tr>
			 <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>表名:<input type="text" name="esAttrDef.rel_table_name" id="esAttrDef_rel_table_name" class="ipttxt" value="${nodeName}" /></td>
			 
			<td>字段名称:<input type="text" name="esAttrDef.field_name" id="esAttrDef_field_name" class="ipttxt" value="${nodePath}"/></td>
		<td >
			<a href="javascript:void(0)" id="rule_obj_searchBtn" class="blueBtns"><span>确定</span></a>
			</td>
		</tr>
	</table>
	
	<form id="gridform" class="grid">
	<grid:grid from="webpage" ajax="yes" >
			<grid:header>
			<grid:cell>
					<input type="radio" id="toggleChk" value="选择记录"/>
				</grid:cell>
				<grid:cell>表名</grid:cell>
				<grid:cell>字段名称</grid:cell>
				<grid:cell>字段描述</grid:cell>
				<grid:cell>业务对象名称</grid:cell>
				<grid:cell>业务对象属性名称</grid:cell>
				<grid:cell>属性ID</grid:cell>
				<grid:cell>来源</grid:cell>
				<grid:cell>默认值</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="radionames" id="${obj.field_attr_id}" value="${obj.rel_table_name}##${obj.field_name}##${obj.class_name}##${obj.class_field_name}" 
						agent="state" record_state="${obj.rel_table_name}">
				</grid:cell>
				<grid:cell>${obj.rel_table_name}</grid:cell>
				<grid:cell>${obj.field_name}</grid:cell>
				<grid:cell>${obj.field_desc} </grid:cell>
				<grid:cell>${obj.field_desc} </grid:cell>
				<grid:cell>${obj.class_name} </grid:cell>
				<grid:cell>${obj.class_field_name}</grid:cell>
				<grid:cell>${obj.source_from}</grid:cell>
				<grid:cell>${obj.default_value}</grid:cell>
			</grid:body>
	</grid:grid>
	<br><br><br>
	<table>
		<tr>
			<td>
				<a href="javascript:void(0)" id="obj_select_confirm_btn" class="blueBtns"><span>确定</span></a>
				<a href="javascript:void(0)" id="obj_select_not_btn" class="blueBtns"><span>取消</span></a>
			</td>
		</tr>
	</table>
	
</form>