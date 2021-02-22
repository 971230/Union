<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<form id="query_agent_form" method="post">
<div class="grid">

	<div class="toolbar" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
	<div>公司名称：<input type="text" class="ipttxt"  style="width:90px" id="parent_dialog" name="parent_dialog" name="parent_dialog" value="${company_name }"/></div>
	   <input type="button" id="searchBtn" name="searchBtn" value="搜索" class="comBtn">
	</div>	
     <div style="clear:both"></div>
	</div>

<grid:grid from="webpage" ajax="yes" formId="query_agent_form">
 <grid:header>
		<grid:cell width="50px"> 公司编号</grid:cell> 
		<grid:cell width="180px">公司名称</grid:cell>
		<grid:cell width="180px">注册地址</grid:cell>
  </grid:header>
  <grid:body item="company" > 
  		<grid:cell> 
  			<input type="radio" name="company_id" value="${company.company_id},${company.company_name}" />
  		</grid:cell>
        <grid:cell>&nbsp;${company.company_name}</grid:cell>
        <grid:cell>&nbsp;${company.register_address} </grid:cell>
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center"  style="padding:28px 0 5px 0;">
 <table>
 <tr><td style="padding-left:0px;">
  <input name="btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>