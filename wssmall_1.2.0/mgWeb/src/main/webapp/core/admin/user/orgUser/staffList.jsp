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
<form id="staffSerchform">
  <div class="searchformDiv" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tr>
	<input type="hidden" name="party_id" id="party_id" value="${party_id }">
	<th>工号：</th><td><input type="text" class="ipttxt"  style="width:90px" id="staff_id" name="staff_id" value="${staff_id}"/></td>
	<th>姓名：</th><td><input type="text" class="ipttxt"  style="width:90px" id="staff_name" name="staff_name" value="${staff_name}"/></td>
	 <th></th><td>  <input type="button" id="staffSearchBtn"   name="searchBtn" class="comBtn" value="搜索"></td>
	   </tr>
	</div>	
     <div style="clear:both"></div>
	</div>
	
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
		<grid:cell width="50px"> 选择</grid:cell> 
		<grid:cell  width="120px">工号</grid:cell> 
		<grid:cell  width="120px">姓名</grid:cell>
		<grid:cell width="50px" >性别</grid:cell>
  </grid:header>
  <grid:body item="staff" > 
  		<grid:cell> <input type="radio" name="staffid" staff_id="${staff.staff_id}" 
  		staff_name="${staff.staff_name }" sex="${staff.sex }" birthday="${staff.birthday}"
  		 user_pid="${staff.user_pid }" serial_number="${staff.serial_number}" depart_id="${staff.depart_id }" /></grid:cell>
        <grid:cell>&nbsp;${staff.staff_id } </grid:cell>
        <grid:cell>&nbsp;${staff.staff_name } </grid:cell>
        <grid:cell>
          <c:if test="${staff.sex==0}">男</c:if>
          <c:if test="${staff.sex==1}">女</c:if>
        </grid:cell>
  
  </grid:body>
  
</grid:grid>
</div>
 <div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="btn" type="button" value=" 确    定   "  id="qrStaffBtn" class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>