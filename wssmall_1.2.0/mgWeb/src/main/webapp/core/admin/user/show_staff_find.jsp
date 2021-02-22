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


  <div class="searchformDiv" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tr>
	<th>本地网：</th>
			<td>
					<select  class="ipttxt"  style="width: 100px" id="lan_id" name="lan_id" >
						<option  value="">--请选择--</option>
		                <option  value="${provice_lanId}">全省</option>
						<c:forEach var="lan" items="${lanList}">
						 	<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
						 </c:forEach>
				    </select>
			<td>	
	<th>编号：</th><td><input type="text" class="ipttxt"  style="width:90px" name="usercode_staff" value="${usercode_staff}"/></td>
	<th>姓名：</th><td><input type="text" class="ipttxt"  style="width:90px" name="username_staff" value="${username_staff}"/></td>
	
	
	
	 <th></th><td>  <input type="button" id="searchBtn" name="searchBtn" class="comBtn" value="搜索"></td>
	   </tr>
	</div>	
     <div style="clear:both"></div>
	</div>
	
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
		<grid:cell width="50px"> 选择</grid:cell> 
		<grid:cell  width="120px">编号</grid:cell> 
		<grid:cell  width="180px">姓名</grid:cell>
		<grid:cell width="180px">本地网</grid:cell>
	<!-- 	<grid:cell  width="50px">关联用户</grid:cell> --> 
  </grid:header>
  <grid:body item="staff" > 
  		<grid:cell> <input type="radio" name="staffid" value="${staff.system_user_id },${staff.staff_code },${staff.staff_name },${staff.sms_tel},${staff.lan_id}" /></grid:cell>
        <grid:cell>&nbsp;${staff.staff_code } </grid:cell>
        <grid:cell>&nbsp;${staff.staff_name } </grid:cell>
        <grid:cell>&nbsp;${staff.lan_name } </grid:cell>
      <!--   <grid:cell>&nbsp;
        	<button id="selectStaffBtn" name="selectStaffBtn" class="p_prted" userid=${staff.userid} username=${staff.username} realname=${staff.realname} title="关联用户">选</button>
        </grid:cell> -->
  </grid:body>
  
</grid:grid>
</div>

 <div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>