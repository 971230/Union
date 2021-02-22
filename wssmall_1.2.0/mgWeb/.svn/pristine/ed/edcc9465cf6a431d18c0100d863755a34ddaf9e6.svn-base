<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
  function selVau(){
  var selVal = $("#sel_user").val();
  
   if(selVal==-1){
   $("#Sel_all").show();
   }
   else{
    $("#Sel_all").hide();
   }
  }
</script>
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
		<tr>
    <th>用户类型:</th><td> <html:selectdict curr_val="${founder}" name="founder" attr_code="dc_user_type"></html:selectdict></td>
	<th>姓    名：</th><td><input type="text" class="ipttxt"  style="width:90px" name="realname" value="${realname}"/></td>
	<th>用户工号：</th><td><input type="text" class="ipttxt"  style="width:90px" name="username" value="${username}"/></td>
   <!--   <th>发送范围：</th><td><select  class="ipttxt"  id ="sel_user" onchange=selVau()>
      <option value=-1>选特定的收件人</option>
      <option value=0>所有的电信管理员</option>
      <option value=3>所有的一级分销商</option>
      <option value=2>所有的二级分销商</option>
    </select></td>-->
    <th></th><td><input type="button" id="searchBtn" class="comBtn" name="searchBtn" value="搜索"></td>
    <!--  <input type="checkbox" id="toggleChk" name = "toggleChk"/>所有该类型的用户-->
		</tr>
	</div>
     <div style="clear:both"></div>
    </div>
  
   <div class="grid">
  <div id ="Sel_all"> 

<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
 		
        <grid:cell sort="realname" width="100">选择</grid:cell>
		<grid:cell sort="realname" width="150px">姓名</grid:cell>
		<grid:cell sort="username" width="150px">用户工号</grid:cell> 
		<grid:cell sort="realname" width="150px">用户类型</grid:cell>
	<!-- 	<grid:cell  width="50px">关联用户</grid:cell> --> 
  </grid:header>
  <grid:body item="user" > 
  	<!--  	<grid:cell> <input type="radio" name="agentid" value="${staff.userid},${staff.username },${staff.realname }" /></grid:cell>-->
        <grid:cell><input type="checkbox"  name="userid_checkbox" value="${user.userid }|${user.realname }" /></grid:cell>
        <grid:cell>&nbsp;${user.realname } </grid:cell>
        <grid:cell>&nbsp;${user.username } </grid:cell>
       
        <grid:cell>&nbsp; <c:if test="${user.founder==0}">电信员工</c:if> 
         <c:if test="${user.founder==1}">超级管理员</c:if> 
         <c:if test="${user.founder==4}">供货商</c:if> 
         <c:if test="${user.founder==5}">供货商员工</c:if> 
        </grid:cell>
     
  </grid:body>
  
  		
</grid:grid>
</div> 
</div>

<div class="submitlist" align="center">

 <table>
 <tr><td >
  <input name="btn" type="button" value=" 确   定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
 </tr>
</div>	
</form>
