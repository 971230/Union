<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="addLlkjUserAdmin.js"></script>
<script type="text/javascript" src="selector_staff.js"></script>
<script type="text/javascript" src="selector_agent.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">添加员工</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="theForm" id="theForm" enctype="multipart/form-data">
<table  class="form-table">

<c:if test="${multiSite==1}">
	<tr>
		<th><label class="text">站点：</label></th>
		<td >
		<select  class="ipttxt"  name="adminUser.siteid" id="adminUserSite"/>
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
		<th><label class="text"><span class='red'>*</span>用户类型：</label></th>
		<td>
		   <c:if test="${currFounder=='3'}">
		        <html:selectdict name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_PARTNER_TYPE" style="width:155px"  id="user_type"></html:selectdict>
	  
		   </c:if>
		   <c:if test="${currFounder=='2'}">
		        <html:selectdict name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_AGENT_TYPE" style="width:155px"  id="user_type"></html:selectdict>
	  
		   </c:if>
		   
		    <c:if test="${currFounder!='3' && currFounder!='2'}">
		       <html:selectdict name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_USER_TYPE" style="width:155px"  id="user_type"></html:selectdict>
	  
		    </c:if>
			  </td>
		   <%-- <c:if test="${currFounder==1}">
		    <input type="radio"  name="adminUser.founder" value="0" id="notSuperChk" checked="true">${typeMap.manager}&nbsp;&nbsp;
		   <!--  <input type="radio" name="adminUser.founder" value="3" id="oneAgentChk">一级分销商--> 
		   </c:if>
		   <c:if test="${currFounder==0}">
		     <input type="radio"  attr_founder='ck_founder' name="adminUser.founder" value="0" id="notSuperChk" checked="true">${typeMap.manager}&nbsp;&nbsp;
		      <c:if test="${typeMap.manager=='电信员工'}">
		       <input type="radio"  attr_founder='ck_founder' name="adminUser.founder" value="3" id="oneAgentChk" >${typeMap.partner}
		      </c:if>
		   </c:if>
		   <c:if test="${currFounder==3}">
		     <input type="radio" name="adminUser.founder" value="2" id="twoAgentChk" checked="true">{typeMap.second_partner}
		   </c:if>
		   
		   <c:if test="${currFounder==4}">
		     <input type="radio" name="adminUser.founder" value="5" id="twoAgentChk" checked="true">{typeMap.supplier_employee}
		   </c:if> --%>
		   
		   
		   
		
	</tr>
    <tr id="agentTr" style="display:none">
        <input type ="hidden" id ="adminUser.reltype" id="reltype">
        <input type ="hidden" name ="adminUser.relcode" id ="relcode">
        <th><label class="text"><span class='red'>*</span>代理商</label></th>
		<td><input type="text" class="ipttxt"  name="partner_name" readonly="readonly"  dataType="string"  id ="partner_name" /> <a class="graybtn1" href="javascript:void(0);"><span id="refAgentBtn" >关联代理商 </span></a></td>
    </tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>用户工号：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.username"  dataType="string"  id ="username" required="true" /><span class='red'>可修改、或不做修改</span></td>
	</tr>
	<!-- 
	<tr>
	   <input type="hidden" name="adminUser.org_id" value="${adminUser.org_id }" id="org_id"/>
		<th><label class="text"><span class='red'>*</span>组织部门：</label></th>
		<td>
			<input type="text" name="adminUser.dep_name"  value="${adminUser.dep_name }"  required="true"  dataType="string"  id ="dep_name"/>
			     <a class="graybtn1" href="#">
		    <span id="refOrgBtn" >关联组织部门</span>
						</a>
		</td>
	</tr>
	 -->
	<tr>
		<th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.realname"  id="realname"  required="true"  /></td>
	</tr>
	<tr>
	  <th><label class="text">性别：</label></th>
	  <td><input name="adminUser.sex"   type="radio" value ="0" <c:if test="${adminUser.sex==0}">checked='true'</c:if>>男 <input name="adminUser.sex"  type="radio" <c:if test="${adminUser.sex==1}">checked='true'</c:if> value="1">女 </td>
	</tr>
	<tr>
	   <th><label class="text">用户邮箱：</label></th>
	   <td><input type="text" class="ipttxt"  value="" name="adminUser.email"  id="email"   /></td>
	</tr>
		<tr>
		<th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
	</tr>
	<tr  >
		<th><label class="text"><span class='red'>*</span>密码：</label></th>
		<td><input type="password"  class="ipttxt"  name="adminUser.password" id="pwd" dataType="string" required="true"  /></td>
	</tr>
	<tr >
		<th><label class="text">确认密码：</label></th>
		<td><input type="password"  class="ipttxt"  id="repwd" /></td>
	</tr>
	<c:if test="${currFounder==1 or currFounder==0 or currFounder==3 or currFounder==2}">
	<tr id="lantr">
		<th><label class="text"><span class='red'>*</span>本地网：</label></th>
		<td>
		   <input type="hidden" vlaue="${lan_name }" name="adminUser.lan_name" id="lan_name">
			<select class="ipttxt"  style="width:15%" id="lan_sel" name = "adminUser.lan_id" required="true">
				<c:forEach var="lan" items="${lanList}">
				 	<option value="${lan.lan_code}">${lan.lan_name }</option>
				 </c:forEach>
			</select>
			
		</td>		
	</tr>
 	</c:if>
 	
	
	<tr id="roletr">
		<th><label class="text"><span class='red'>*</span>角色：</label></th>
		<td>
			<ul style="width:100%" id="rolesbox">
				<c:forEach var="role" items="${roleList }">
				<li style="width:33%;display:block"><input type="checkbox" name="roleids" value="${role.roleid }"  />
					${role.rolename }&nbsp;</li>
				</c:forEach>
			</ul>
		</td>		
	</tr>
	
	<tr>
		<th><label class="text">状态：</label></th>
		<td>
			<input type="radio"  name="adminUser.state" value="1" checked="true">启用&nbsp;&nbsp;
			<input type="radio"  name="adminUser.state" value="0">禁用 
		</td>
	</tr>
	
	<!-- 
	<tr>
		<th><label class="text">编号：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.userno"   /></td>
	</tr>
	
	<tr>
		<th><label class="text">部门：</label></th>
		<td><input  type="text" class="ipttxt"  name="adminUser.userdept"   /></td>
	</tr>
	-->
	<tr>
		<th><label class="text">备注：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.remark"   /></td>
	</tr>


</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td> <input type="hidden"  name="adminUser.paruserid" value="${currUserId }" >
     	 <input  type="submit" id="btn"	 value=" 确  定 "  class="submitBtn"  /></td>

	</tr>
	 </table>
	</div>  


</form>
<div title="职员" id="refStaffDlg"></div>
<div title="代理" id="refAgentDlg"></div>
<div title="职员" id="refOrgDlg"></div>
</div>
<script>
	$(function(){
		$("#lan_sel").val('${lan_id }');
	     if(${currFounder ==3}){
	    	 $("#reltype").val("partner");
	    	 $("#agentTr").show();
	     }
             return false;
		$("#oneAgentChk").attr("checked",true);
		
		$("[attr_founder='ck_founder']").bind("click",function(){
			
			if($(this).val() =="0" && $(this).attr("checked"))
			{
				$("[value='201305061747000080']:checkbox").closest("li").hide();
				$("[value='201305061747000080']:checkbox").removeAttr("checked"); 
				$("[value='201305063335000079']:checkbox").closest("li").show();
				$("[value='201305068463000078']:checkbox").closest("li").show();
				
			}else if($(this).val() =="3"){	//本地网
				$("[value='201305061747000080']:checkbox").closest("li").show();
				$("[value='201305061747000080']:checkbox").attr("checked",true);
				$("[value='201305063335000079']:checkbox").closest("li").hide();
				$("[value='201305068463000078']:checkbox").closest("li").hide();
			}
		})
		
		$("#oneAgentChk").trigger("click");
		
		 
	})
</script>