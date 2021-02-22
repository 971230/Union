<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="js/column_relate.js"></script>
<script src="js/column_select.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">关联栏目</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input" >
	<form method="post" action="template!addTemplateColumn.do?template_id=${template.template_id}" class="validate" >
		<input　type="hidden" name="template_id" value="${template.template_id}" id="template_id">
		<input　type="hidden" name="onclickType" value="2">
		<div class="grid" style="width:75%">
			<div class="" style="margin-left: 10px;margin-bottom: 10px">
				栏目列表：<a href="javascript:;" class="graybtn1" id="addColumnBtn">关联栏目</a>
			</div>
			 <table id="columnstable">
				 <tr>
					 <th>栏目名称</th>
					 <th>栏目类型</th>
					 <th>栏目标识</th>
					 <th>栏目顺序</th>
					 <th>操作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				 </tr>
				 <c:forEach items="${webpage.data}" var="column">
				 <tr id="${column.column_id}">
			 		<td>${column.title}</td>
			 		<td>${column.type}<input type="hidden" name="columnid" value= "${column.column_id}" /></td>
			 		<td><input type="text" class="ipttxt"  name="c_code" value="${column.c_code}" style="width: 80px;text-align: center;"/></td>
			 		<td><input type="text" class="ipttxt"  name="sort" value="${column.sort}" style="width: 80px;text-align: center;"/></td>
			 		<td>
			 			<a href="javascript:;" ><img class="delete" src="images/transparent.gif" ></a>
			 		</td>
			 	</tr>
				</c:forEach> 	
			</table>
		</div>
		<div class="submitlist" align="center">
			<table style="margin-left: 50px;">
				<tr>
					<td><input type="submit" value=" 确    定   " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<table id="temp_table" style="display:none">
 	 <tr>
 		<td class="columnTitle">
 		</td>
 		<td class="columnType">
 		</td>
 		<td><input type="text" class="ipttxt" name="c_code" style="width: 80px;text-align: center;" />
 		<input type="hidden" name="columnid" value= "" />
 		</td>
 		<td><input type="text" class="ipttxt" name="sort" style="width: 80px;text-align: center;" />
 		</td>
 		<td>
 			<a href="javascript:;" ><img class="delete" src="images/transparent.gif" ></a>
 		</td>
 	</tr>
</table>
<div id="columnInfo_dlg"></div>
