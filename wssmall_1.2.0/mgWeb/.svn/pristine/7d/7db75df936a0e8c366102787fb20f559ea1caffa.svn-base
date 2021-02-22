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
<div class="grid">
<!-- 
<form action="agent!list.do" method="post">
	<div class="toolbar" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<div>工号：<input type="text" class="ipttxt"  style="width:90px" name="username" value="${username}"/></div>
	
		<input type="submit"  value="搜索">
	</div>
 		
<div style="clear:both"></div>
	</div>
</form>	
 -->
<form id="gridform">
<grid:grid  from="webpage">
 <grid:header>
		<!--<grid:cell  width="50px"><!-- <input type="checkbox" id="toggleChk"/></grid:cell> -->
		<grid:cell sort="username" width="120px">用户名</grid:cell> 
		<grid:cell sort="realname" width="180px">姓名</grid:cell>
		<grid:cell  width="50px">关联用户</grid:cell> 
  </grid:header>
  <grid:body item="staff" > 
  		<!--<grid:cell> <input type="checkbox" name="id" value="${staff.userid}" />${staff.userid }</grid:cell>-->
        <grid:cell>&nbsp;${staff.username } </grid:cell>
        <grid:cell>&nbsp;${staff.realname } </grid:cell>
        <grid:cell>&nbsp;
        	<button onclick="" name="selectStaffBtn" class="p_prted" userid=${staff.userid} username=${staff.username} realname=${staff.realname} title="关联用户">选</button>
        </grid:cell>
  </grid:body>
  
</grid:grid>
</form>
