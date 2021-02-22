<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<script type="text/javascript" src="editLlkjUserAdmin.js"></script>
<script type="text/javascript" src="selector_staff.js"></script>
<script type="text/javascript" src="selector_agent.js"></script>

<style>
#repwdtr,#pwdtr{display:none}
</style>
<div class="input">
<form action="javascript:void(0)"  class="validate" method="post" name="updateForm" id="updateForm">
<input type="hidden" name="adminUser.userid" value="${adminUser.userid }" />
<table  class="form-table">
<c:if test="${multiSite==1}">
	<tr>
		<th><label class="text">站点：</label></th>
		<td ><select name="adminUser.siteid" id="adminUserSite"/>
		</td>
	</tr>
<script>
$(function(){

	$.ajax({
		 type: "GET",
		 url: "../multiSite!listJson.do",
		 data:   "ajax=yes",
		 dataType:'json',
		 success: function(result){
			 if(result.result==0){
				 $("#adminUserSite").selectTree(result.data);
				 $("#adminUserSite").val(${siteid});
			 }else{
				 alert("站点列表获取失败，请重试");
			 }
	     },
	     error:function(){
			alert("站点列表获取失败");
		 }
	}); 
});
</script>
</c:if>
     <tr>
		<th><label class="text">类型：</label></th>
		<td>
			 <c:if test="${currFounder=='3'}">
		        <html:selectdict  name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_PARTNER_TYPE" style="width:155px"  ></html:selectdict>
	  
		   </c:if>
		   <c:if test="${currFounder=='2'}">
		        <html:selectdict  name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_AGENT_TYPE" style="width:155px"  ></html:selectdict>
	  
		   </c:if>
		   
		    <c:if test="${currFounder!='3' && currFounder!='2'}">
		       <html:selectdict  name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_USER_TYPE" style="width:155px"  id="user_type"></html:selectdict>
	  
		    </c:if>
		    <%--<input type="radio"  name="adminUser.founder" value="${adminUser.founder}" id="notSuperChk" checked="true"> 
		    <c:if test="${adminUser.founder==0}">${typeMap.manager}</c:if>
			<c:if test="${adminUser.founder==2}">${typeMap.second_partner}</c:if>
			<c:if test="${adminUser.founder==3}">${typeMap.partner}</c:if>
			<c:if test="${adminUser.founder==4}">${typeMap.supplier}</c:if>
			<c:if test="${adminUser.founder==5}">${typeMap.supplier_employee}</c:if> --%>
		</td>
	</tr>
	<input type="hidden"  id ="cur_founder" value="${adminUser.founder}" />
	<!-- tt  <c:if test="${adminUser.founder==0 or adminUser.founder==3}">
	<tr id="crmTr" <c:if test="${currFounder==2}">style="display: none;"</c:if>>
	 <th><span class='red'>*</span>CRM工号：</th>
	 <td>
	    <input type='hidden' name='adminUser.reltype' value="STAFF"  /> tt-->
		<!--  <input type='hidden' name='adminUser.relno' id='refCrm_no'  value="" />-->
		<!--    <input type="button" id="refStaffBtn" value="关联CRM工号" class="comBtn" /> -->
	 <!-- tt 
		<input type='text' readonly="readonly" name='adminUser.relcode' id='refCrm_code' value="${adminUser.relcode}" dataType="string"/>
	     <a class="sgreenbtn" href="#">
		    <span id="refStaffBtn" >关联CRM工号</span>
						</a>
	 </td>
	</tr>
	</c:if>
	
	tt-->
	<tr>
	   <input type="hidden" name="user_no" value="${adminUser.username }"/>
		<th><label class="text"><span class='red'>*</span>用户工号：</label></th>
		<td>
			<input type="text" name="adminUser.username" value="${adminUser.username }"  dataType="string" required="true" id ="username"/>
		</td>
	</tr>
	<!-- 
	<tr>
	   <input type="hidden" name="adminUser.org_id" value="${adminUser.org_id }" id="org_id"/>
		<th><label class="text"><span class='red'>*</span>组织部门：</label></th>
		<td>
			<input type="text" name="adminUser.dep_name" value="${adminUser.dep_name }"  dataType="string" required="true"   id ="dep_name"/>
			     <a class="sgreenbtn" href="#">
		    <span id="refOrgBtn" >关联组织部门</span>
						</a>
		</td>
	</tr>
	 -->
	<tr>
		<th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		<td><input type="text" name="adminUser.realname" value="${adminUser.realname }" required="true"  id="realname" /></td>
	</tr>
	<tr>
	  <th><label class="text">性别：</label></th>
	  <td>
	   		<c:choose>
		           <c:when test="${adminUser.sex eq '0'}">
		           		<input name="adminUser.sex" type="radio" checked="checked" value="0"/>男&nbsp;&nbsp;
		           		<input name="adminUser.sex" type="radio" value="1" />女
		           </c:when>
		           <c:when test="${adminUser.sex eq '1'}">
		           		<input name="adminUser.sex" type="radio" value="0"/>男&nbsp;&nbsp; 
		           		<input name="adminUser.sex" type="radio" checked="checked" value="1" />女
		           </c:when>
		           <c:otherwise>
			           <input name="adminUser.sex" type="radio"  value="0"/>男&nbsp;&nbsp; 
			           <input name="adminUser.sex" type="radio" value="1" />女
		           </c:otherwise>
	        </c:choose>
	  </td>
	</tr>
	<tr>
	   <th><label class="text">用户邮箱：</label></th>
	   <td><input type="text" class="ipttxt"  value="${adminUser.email}" name="adminUser.email"  id="email"   /></td>
	</tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
		<td><input type="text" class="ipttxt"  value="${adminUser.phone_num }" name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
	</tr>
	<tr>
		<th> </th>
		<td> <input type="checkbox" name="updatePwd" id="updatePwd" value="yes"  />同时修改密码 </td>
	</tr>
	<tr id="pwdtr">
		<th><label class="text"><span class='red'>*</span>密码：</label></th>
		<td><input type="password" name="newPassword" id="pwd"  /></td>
	</tr>
	<tr id="repwdtr">
		<th><label class="text">确认密码：</label></th>
		<td><input type="password" id="repwd" dataType="string"  /></td>
	</tr>
	
	<!-- 一级分销商 查看自己时候 不显示角色· -->
	<c:if test="${roleList ne null}">
		<tr id="roletr">
			<th><label class="text"><span class='red'>*</span>角色：</label></th>
			<td>
				<ul style="width:100%" id="rolesbox">
						<c:forEach var="role" items="${roleList}">
							<li style="width:33%;display:block"><input type="checkbox" name="roleids" id="${role.roleid}" value="${role.roleid }"  />
							${role.rolename }&nbsp;</li>
						</c:forEach>
				</ul> 
			</td>		
		</tr>
	</c:if>
	
	<tr>
		<th><label class="text">状态：</label></th>
		<td>
			<c:choose>
					<c:when test="${adminUser.state eq '1'}">
		           		<input name="adminUser.state" type="radio" checked="checked" value="1"/>启用&nbsp;&nbsp;
		           		<input name="adminUser.state" type="radio" value="0" />禁用
		           	</c:when>
		           	<c:when test="${adminUser.state eq '0'}">
		           		 <input name="adminUser.state" type="radio"  value="1"/>启用&nbsp;&nbsp; 
		           		 <input name="adminUser.state" type="radio" checked="checked" value="0" />禁用
		           	</c:when>
		           	<c:otherwise>
			           <input name="adminUser.state" type="radio"  value="1"/>启用&nbsp;&nbsp; 
			           <input name="adminUser.state" type="radio" value="0" />禁用
		           	</c:otherwise>
		       </c:choose>    
		</td>
	</tr>
	
	<!-- 
	<tr>
		<th><label class="text">编号：</label></th>
		<td><input type="text" name="adminUser.userno"  value="${adminUser.userno }" /></td>
	</tr>
	<tr>
		<th><label class="text">部门：</label></th>
		<td><input type="text" name="adminUser.userdept" value="${adminUser.userdept }"  /></td>
	</tr>
	 -->
	  <c:if test="${adminUser.founder==0 or adminUser.founder==3 or adminUser.founder==2}">
	  <input type="hidden" vlaue="${adminUser.lan_name }" name="adminUser.lan_name" id="lan_name">
	<tr id="lantr">
		<th><label class="text"><span class='red'>*</span>本地网：</label></th>
		<td>
			<select style="width:15%" id="lan_sel" name = "adminUser.lan_id" class="ipttxt">
				 <c:forEach var="lan" items="${lanList}">
				 <option value="${lan.lan_code}">${lan.lan_name }</option>
				 </c:forEach>
			</select>	
		</td>		
	</tr>
	<script type="text/javascript">
         $(function(){
       		$("#lan_sel option[value='${adminUser.lan_id }']").attr("selected", true);
      });
    </script>
 	</c:if>
	<tr>
		<th><label class="text">备注：</label></th>
		<td><input type="text" name="adminUser.remark"  value="${adminUser.remark }" /></td>
	</tr>
	
  
</table> 
<div class="submitlist" align="center">
 <table>
 <tr>
 <th></th>
 <td >
  <input  type="submit"	id="editbtn"  value=" 确  定 " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>

<script type="text/javascript">
$(function(){
    $("#lan_sel option[value='${adminUser.lan_id }']").attr("selected", true);
  
	$("form.validate").validate();
	//  $("#editbtn").click(function(){
      //    return  checkpwd1();
        //  });
	$("#notSuperChk").click(function(){
		if(this.checked)
		$("#roletr").show();
	});
	$("#superChk").click(function(){
		if(this.checked)
		$("#roletr").hide();
	});	
	$("#updatePwd").click(function(){
		if(this.checked){
			$("#pwdtr").show();
			$("#repwdtr").show();			
		}else{
			$("#pwdtr").hide();
			$("#repwdtr").hide();
		}
	});

	<c:forEach var="userRole" items="${userRoles }">
		$("#rolesbox input[value='${userRole.roleid}']").attr("checked",true);
	</c:forEach>
	<c:if test="${adminUser.founder==1}" >
 	$("#superChk").click()
</c:if>
<c:if test="${adminUser.founder==0}" >
	$("#notSuperChk").click();
</c:if>
});
</script>

</form>
<div title="职员" id="refStaffDlg"></div>
<div title="职员" id="refOrgDlg"></div>
</div>