<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="js/column_select.js"></script>
<form id="columnsserchform" method="post">
<div class="input">
<table class="form-table">
	<tr>
		<th>
		栏目名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="c_title"  value="${c_title}" class="searchipt"/>
		<a href="javascript:void(0)" id="searchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="columnslist">

<grid:grid  from="webpage" ajax="yes" formId="columnsserchform">

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell> 
		<grid:cell style="width: 100px;">栏目名称</grid:cell>
		<grid:cell style="width: 100px;">栏目类型</grid:cell>
	</grid:header>

  <grid:body item="columns" >
  		<grid:cell><input type="checkbox" id="column_id" name="columnid" value="${columns.column_id }"/></grid:cell>
      	<grid:cell>${columns.title}<input type="hidden" id="c_title" value="${columns.title}"/></grid:cell>
		<grid:cell>${columns.type}<input type="hidden" id="c_type" value="${columns.type}"/></grid:cell>
         
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="goods_confirm_btn" type="button" value=" 确    定   " class="submitBtn comBtn" />
   </td>
   </tr>
 </table>
</div>	

</form>
<script type="text/javascript">
function selectChange(){
	$("#columnslist").find("input[name=column_id]").attr("checked",$("#toggleChk").is(":checked"));	
}
</script>