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
<form id="serchform">
<div class="grid">

	<div class="toolbar" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
	<div>用户名：<input type="text" class="ipttxt"  style="width:90px" name="usercode_agent" value="${usercode_agent}"/></div>
	<div>姓名：<input type="text" class="ipttxt"  style="width:90px" name="username_agent" value="${username_agent}"/></div>
	   <input type="button" id="searchBtn" name="searchBtn" value="搜索">
	</div>	
     <div style="clear:both"></div>
	</div>

<grid:grid  from="webpage"  ajax="yes">
 <grid:header>
		<grid:cell width="50px"> 选择</grid:cell> 
		<grid:cell sort="username" width="120px">分销商编号</grid:cell> 
		<grid:cell sort="realname" width="180px">分销商名称</grid:cell>
	<!-- 	<grid:cell  width="50px">关联用户</grid:cell> --> 
  </grid:header>
  <grid:body item="staff" > 
  		<grid:cell> <input type="radio" name="partner_id" value="${staff.partner_id},${staff.partner_name }" /></grid:cell>
        <grid:cell>&nbsp;${staff.partner_id } </grid:cell>
        <grid:cell>&nbsp;${staff.partner_name } </grid:cell>
      <!--   <grid:cell>&nbsp;
        	<button id="selectStaffBtn" name="selectStaffBtn" class="p_prted" userid=${staff.userid} username=${staff.username} realname=${staff.realname} title="关联用户">选</button>
        </grid:cell> -->
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr> <th> &nbsp;</th><td >
  <input name="btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>