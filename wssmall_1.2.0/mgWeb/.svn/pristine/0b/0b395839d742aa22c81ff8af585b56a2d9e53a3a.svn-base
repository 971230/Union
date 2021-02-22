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
<form id="agentSerchform" method="post">



	<div class="searchformDiv" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tr>
	  <th>姓名：</th><td><input type="text" class="ipttxt"  style="width:90px" name="realNameKey" value="${realNameKey}"/></td>
	  <th>编号：</th><td><input type="text" class="ipttxt"  style="width:90px" name="partner_code" value="${partner_code}"/></td>
	  <th></th><td> <input type="button" id="agentSearchBtn" class="comBtn" name="searchBtn" value="搜索"></td>
	   </tr>
	</div>	
     <div style="clear:both"></div>
	</div>
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
		<grid:cell width="50px"> <a href='javascript:void(0);'>选择</a></grid:cell> 
		<grid:cell sort="username" width="120px"><a href='javascript:void(0);'>编号</a></grid:cell> 
		<grid:cell sort="realname" width="120px"><a href='javascript:void(0);'>姓名</a></grid:cell>
		<grid:cell width="120px">创建时间</grid:cell>
  </grid:header>
  <grid:body item="staff" > 
  		<grid:cell> <input type="radio" name="agentid" value="${staff.partner_id},${staff.partner_name },${staff.partner_code },${staff.phone_no }" /></grid:cell>
        <grid:cell>&nbsp;${staff.partner_code } </grid:cell>
        <grid:cell>&nbsp;${staff.partner_name } </grid:cell>
        <grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${staff.create_date}"></html:dateformat></grid:cell>
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="btn" type="button" id="searchBtn" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>