<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="rule_obj_form" >
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		对象名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="obj_name"  value="${obj_name }"/>
		<a href="javascript:void(0)" id="rule_obj_searchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>
<div class="grid">
<grid:grid  from="webpage" formId="rule_obj_form" ajax="yes">
	<grid:header>
		<grid:cell>选择</grid:cell>
		<grid:cell>对象名称</grid:cell>
		<grid:cell>类路径</grid:cell>
		<grid:cell>创建时间</grid:cell> 
	</grid:header>
    <grid:body item="ro">
        <grid:cell>
        	<input type="radio" name="objectid" value="${ro.obj_id }" />
        </grid:cell>
        <grid:cell>${ro.obj_name}</grid:cell>
        <grid:cell>${ro.clazz}</grid:cell>
        <grid:cell>${ro.create_date}</grid:cell>
    </grid:body>  
</grid:grid>  
<div style="clear:both;padding-top:5px;"></div>
</div>
</form>	

<div class="submitlist" style="width: 100%;">
 <table style="width: 100%;">
 <tr>
 <td style="text-align: center;">
 <a href="javascript:void(0)" id="obj_select_confirm_btn" style="margin-right:10px;" class="graybtn1"><span>确定</span></a>
   </td>
   </tr>
 </table>
</div>

<script type="text/javascript">

</script>
