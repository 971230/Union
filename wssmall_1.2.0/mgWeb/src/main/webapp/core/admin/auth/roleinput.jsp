<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>

<div class="input">

<c:if test="${isEdit==1}">
<form method="post" action="role!saveEdit.do" class="validate"   id="brandForm" >
<input type="hidden" name="role.roleid" value="${role.roleid }" />

</c:if> 
<c:if test="${isEdit==0}">
<form method="post" action="role!saveAdd.do" class="validate"   id="brandForm" >
</c:if>



<div class="top_up_div">
   <h2>角色基本信息</h2>
		<div class="top_up_con">
				<div class="user_msg_table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody>
							     <tr>
							       <th><label class="text">角色名称：</label></th>
							       <td><input type="text" class="ipttxt"  name="role.rolename" id="rolename" maxlength="60" value="${role.rolename }"  dataType="string" required="true" /></td>
							      </tr>
							     <tr>
							       <th><label class="text">角色分组：</label></th>
							       <td> 
							    	    <select name ="role.role_group" id="role_group" class="ipttxt" style="width:150px;">
							    	      <c:choose>
							    	      <c:when test="${isEdit==1}">
							    	        <option value="${role_group}" selected="selected" >${role_group_name}</option>
							    	      </c:when>
							    	      <c:otherwise>							    	      
			                              <option value="00">---请选择---</option>
							    	      </c:otherwise>
							    	      </c:choose>
			                              <c:forEach var="role" items="${roleGroupListMap}">			     
			                              <option value="${role.pkey}">${role.pname}</option>
			                              </c:forEach>
			                            </select>
							       </td>
							      </tr>
							      <tr>
							       <th><label class="text">描述：</label></th>
							       <td> 
							    	  <textarea name="role.rolememo" class="ipttxt" style="width:200px;height:100px;">${role.rolememo}</textarea>
							       </td>
							      </tr>
							      </tbody>
							</table>
				 </div>
              </div>
          
</div>

<div class="top_up_div">
   <h2>权限信息</h2>
		<div class="top_up_con">
			<div class="top_up_search" > 
			   <input type="button" value="选择权限" id="sel_auth" class="graybtn1" style="margin-left: 10px;" />
			</div>
			<div class="business_div">
					<div class="msg_table grid">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody id="authBody">
								<tr align="center"> 
								   <th style='text-align:center'>权限ID</th>
								   <th style='text-align:center'>权限名称</th>
								   <th style='text-align:center'>类型</th>
								   <th style='text-align:center'>编辑</th>
								</tr>
							    <c:if test="${isEdit==1}">
									<c:forEach items="${authList}" var="act">
										<tr id="tr_${act.actid}">
										   <input type="hidden" value="${act.actid}" name="acts">
										   <td style='text-align:center'>${act.actid}</td>
										   <td style='text-align:center'>${act.name}</td>
										   <td style='text-align:center'>
							                   	<c:if test="${act.type=='app'}">系统</c:if>
							                   	<c:if test="${act.type=='menu'}">菜单</c:if>
							                  	<c:if test="${act.type=='data'}">数据</c:if>
							                  	<c:if test="${act.type=='btn'}">数据</c:if>
							        	   </td>
										   <td style='text-align:center'>
										   		<a title ="删除" href="javascript:;" class="p_prted" actid="${act.actid}" name="removeAuth">删除</a>
										   </td>
										</tr>
								    </c:forEach>
								</c:if>
							 </tbody>
					     </table>
					 </div>
			  </div>
	 </div>
</div>

<div class="submitlist" align="center">
	 <table>
			<tr>
				<th></th>
				<td><input type="submit"   value=" 确    定   " id="submitBtn" class="graybtn1"  />
			       <!--  <input type="button" name="returnBtn"  id="returnBtn" value=" 返    回   " class="submitBtn"  /> -->
			    </td>

	         </tr>
	 </table>
</div>


</form>


<div id="actionDlg"></div>
<div id="authSelDlg"></div>
</div>

<script>

$(function(){
	$("form.validate").validate();
	authSel.init();
	authSel.removeTr();
	
/*	<c:if test="${isEdit==1}">
		<c:forEach items="${role.actids}" var="actid">
			$("#actbox input[value=${actid}]").attr("checked",true);
		</c:forEach>
	</c:if>*/
	//AuthAction.init();
	
		$("#submitBtn").click(function(){
		if($("input[name='acts']").length==0){
	       alert("请选择权限");
	     return false;
	      }
	   
	   });
	  
	   
});
</script>
