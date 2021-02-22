<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/cms/js/cms_widget_add.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">插件桩</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="cmsWidget.widget_id" value="${cmsWidget.widget_id}" />
			<tr>
				<th><label class="text"><span class='red'>*</span>所属模板：</label></th>
				<input type="hidden" class="ipttxt" name="template.template_id" dataType="string" value="" />
				<td><input type="text" class="ipttxt" required="true" name="cmsWidget.template_name" dataType="string" readonly="readonly" value="${cmsWidget.template_name}" />
					<a class="sgreenbtn" href="javascript:void(0);" id="select_tpl"><span>选择模板</span>
				</a></td>
			</tr>


			<tr>
				<th><label class="text"><span class='red'>*</span>插件编码</label></th>
				<td><input type="text" class="ipttxt" name="cmsWidget.code"
					dataType="string" required="true" value="${cmsWidget.code}" />
				</td>
			</tr>
			
			<tr>
				<th><label class="text">前台处理bean</label></th>
				<td><input type="text" class="ipttxt" name="cmsWidget.type"
					dataType="string"  value="${cmsWidget.type}" />
				</td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>后台处理bean</label></th>
				<td><input type="text" class="ipttxt" name="cmsWidget.cms_type"
					dataType="string" required="true" value="${cmsWidget.cms_type}" />
				</td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>是否启用</label></th>
				<td><input type="radio" value="yes" name="cmsWidget.state" checked="checked">是</input>
					<input type="radio" value="no" name="cmsWidget.state">否</input></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="submitWidgetInfo" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div  id="cms_tpl_dialog"></div>
