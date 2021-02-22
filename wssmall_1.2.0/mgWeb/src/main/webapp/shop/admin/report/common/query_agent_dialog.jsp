<%@page import="com.ztesoft.net.mall.core.consts.Consts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<form id="query_agent_form" method="post">
<div class="grid">

	<div class="toolbar" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
	<div>用户工号/名称：<input type="text" class="ipttxt"  style="width:90px" name="keyWord" value="${keyWord }"/></div>
	   <input type="button" id="searchBtn" name="searchBtn" value="搜索" class="comBtn">
	</div>	
     <div style="clear:both"></div>
	</div>

<grid:grid from="webpage" ajax="yes" formId="query_agent_form">
 <grid:header>
		<grid:cell width="50px"> 选择</grid:cell> 
		<grid:cell width="180px">用户工号</grid:cell>
		<grid:cell width="180px">用户名称</grid:cell>
  </grid:header>
  <grid:body item="staff" > 
  		<grid:cell> 
  			<input type="radio" name="agentid" value="${staff.userid},${staff.username},${staff.realname}" />
  		</grid:cell>
        <grid:cell>&nbsp;${staff.username } </grid:cell>
        <grid:cell>&nbsp;${staff.realname } </grid:cell>
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