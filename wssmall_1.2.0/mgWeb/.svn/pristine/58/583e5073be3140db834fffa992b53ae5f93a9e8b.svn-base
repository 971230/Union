<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>



<form id="serchform" >

 <div class="searchformDiv">
   <table>
	 <tr>
	    
	    <th>员工工号：</th><td><input type="text" class="ipttxt"  style="width:90px" id="username" name="username" value="${username}"/></td>
	    <th>员工名称：</th><td><input type="text" class="ipttxt"  style="width:90px" id="realname" name="realname" value="${realname}"/></td>
	    <th></th><td> <input class="comBtn" type="button" name="searchBtn"  id="searchBtn" value="搜索" style="margin-right:10px;"/></td>
	 </tr>
	</table>
     <div style="clear:both"></div>
</div>
</form>
<form >
<div class="grid">
<grid:grid  from="webpage"  ajax="yes">
 <grid:header>
		<grid:cell width="50px"> <input type="checkbox" id ="checkBoxClick"></grid:cell>  
		<grid:cell sort="username" width="180px">员工工号</grid:cell> 
		<grid:cell sort="realname" width="180px">员工名称</grid:cell>
	
  </grid:header>
  <grid:body item="user" > 
  		<grid:cell> <input type="checkbox" name="userCheckBox" userid ="${user.userid}" username="${user.username }"  realname ="${user.realname}"/></grid:cell>
        <grid:cell>&nbsp;${user.username } </grid:cell>
        <grid:cell>&nbsp;${user.realname } </grid:cell>
    
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr> <th> &nbsp;</th><td >
  <input name="btn" type="button" id="qrUserBtn" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>

<script type="text/javascript">
  $(function(){
    $("#checkBoxClick").click(function(){
       
        if($("[name='userCheckBox']").not("input:checked").length==0){
           $("[name='userCheckBox']").attr("checked","");
        }else{
           $("[name='userCheckBox']").attr("checked","checked");
        }
       
    });
  });
</script>
