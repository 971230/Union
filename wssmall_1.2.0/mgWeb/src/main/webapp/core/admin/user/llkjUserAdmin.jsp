<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="../js/PasswordOperator.js"></script>

<form method="post" id="serchform" action='userAdmin!llkjUserList.do'>
 <div class="searchformDiv">
 <table>
	<tr>
		<th>用户工号：</th>
		<td><input type="text" class="ipttxt"  name="username"  value="${username}"/></td>
		 <th>用户名称：</th>
		<td><input type="text" class="ipttxt"  name="realname"  value="${realname}"/></td>
		<td>
		<th>启用状态：</th>
		<!-- <td><input type="text" class="ipttxt"  name="strstate"  value="${strstate}"/></td> -->	
		<td>
		
		<html:selectdict curr_val="${state}"  style="width:90px" id="state" name ="state"  attr_code="DC_USER_ADMIN_STATE"></html:selectdict>
     
		<!-- <select  style="width:90px"  class="ipttxt"  name ="state" id="state" value="${state}" >
		<option value="2" checked ="true">--请选择--</option>
		<option value="1">启用</option>
		<option value="0">禁用</option>
		</select>
		 -->
		
		</td>
		<c:if test="${currFounder==1||currFounder==0}">
		 <th>用户类型：</th>
		 <td>
		 <c:if test="${currFounder=='3'}">
		        <html:selectdict name="usertype" curr_val="${usertype}"
							attr_code="DC_PARTNER_TYPE" style="width:155px" id="usertype" ></html:selectdict>
	  
		   </c:if>
		    <c:if test="${currFounder!='3'}">
		       <html:selectdict name="usertype" curr_val="${usertype}"
							attr_code="DC_USER_TYPE" style="width:155px"  id="usertype"></html:selectdict>
		    </c:if>
		 
		 <!--  <select  style="width:90px" class="ipttxt"  name ="usertype" id="usertype" value="${usertype}">
		 <option value=1>--请选择--</option>
		 <option value=0 >电信员工</option>
		 <option value=2 >二级分销商</option>
		 <option value=3>一级分销商</option>
		</select>
		-->
		</td>
		
		</c:if>
		<!--  
		<c:if test="${currFounder==0}">
		 <th>用户类型：</th>
		 <td><select  class="ipttxt"  name ="usertype" id="usertype" value="${usertype}">
		 <option value=1>--请选择--</option>
		 <option value=0 >电信员工</option>
		 <option value=3>一级分销商</option>
		</select></td>
		</c:if>
		-->
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
		<td>
			<!-- <input class="comBtn" type="reset" name="resetbtn" id="searchBtn" value="重置" style="margin-right:10px;"/> -->
	   </td>
	</tr>
	   <div style="clear:both"></div>
</table>	
</div>
</form>
<div class="grid" id="goodslist">

<form method="POST"  id="userform" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	
		<grid:cell width="200px">用户工号</grid:cell>
		<grid:cell width="200px">用户名称</grid:cell>
		<grid:cell width="200px">用户类型</grid:cell>
		<grid:cell width="150px">状态</grid:cell>
		<grid:cell>创建时间</grid:cell>
		<grid:cell width="200px">操作</grid:cell>
	</grid:header>
	<grid:body item="userAdmin">
		<grid:cell>&nbsp;${userAdmin.username }
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.realname } </grid:cell>
		<grid:cell>
         <c:if test="${userAdmin.founder==0}">${typeMap.manager}</c:if>
         <c:if test="${userAdmin.founder==1}">${typeMap.admin}</c:if>
         <c:if test="${userAdmin.founder==2}">${typeMap.second_partner}</c:if>
         <c:if test="${userAdmin.founder==3}">${typeMap.partner}</c:if>
         <c:if test="${userAdmin.founder==4}">${typeMap.supplier}</c:if>
         <c:if test="${userAdmin.founder==5}">${typeMap.supplier_employee}</c:if>
        </grid:cell>
		<grid:cell> 
		 <c:if test="${userAdmin.state==1}">启用</c:if>
		 <c:if test="${userAdmin.state==0}">禁用</c:if>
		</grid:cell>
		
		 <!--<grid:cell>${userAdmin.create_time}</grid:cell>-->
		  <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${userAdmin.create_time}"></html:dateformat></grid:cell>
		<grid:cell>&nbsp; 
		
		<c:if test="${userAdmin.paruserid==currUserId||currFounder==1}">
			<a title ="编辑员工信息" href="userAdmin!editLLkjAgent.do?id=${userAdmin.userid }" class="p_prted">修改</a><!-- <span class='tdsper'></span> -->
		</c:if>
			<!-- <a title ="删除员工信息" href="javascript:void(0)" class="p_prted" id="detBtn" onclick="del('${userAdmin.userid }')">删除</a> -->
            <c:if test="${userAdmin.fail_logincount>=limitFailCount}"><span class='tdsper'></span><a title="解冻员工账号" href="userAdmin!cleanFailCount.do?id=${userAdmin.userid }" style="margin-right:10px;" class="p_prted" >解冻</a></c:if>
		</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>
<!-- userAdmin!delete.do?id=${userAdmin.userid }" onclick="javascript:return confirm('确认删除此管理员吗？')" -->
 <script type="text/javascript">
$(function(){
    $("#state option[value='${state}']").attr("selected", true);
    if(${currFounder}==1 || ${currFounder}==0){
      $("#usertype option[value='${usertype}']").attr("selected", true);
     }
  
});
function del(id){
 
		  var self =this;
         if(window.confirm('确认要删除此工号？')){
		  var url =  "userAdmin!delete.do?ajax=yes&id="+id;
	      
	    Cmp.ajaxSubmit('userform', '', url, {},  function jsonback(responseText) { // json回调函数
	   
		if (responseText.result == 1) {
			alert(responseText.message);
		     window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);	
		  
		   window.location="userAdmin.do?state=2&usertype=1";
		}
	}, 'json');

	}
	

}
</script>
