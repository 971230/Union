<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">关联订购组角色</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="theForm" id="roleSaveForm" enctype="multipart/form-data">
<table  class="form-table">

	<input type="hidden" name="orderGroupRel.group_id" value ="${group_id}"> 
	<tr id="roletr">
		<th><label class="text"><span class='red'>*</span>已有角色：</label></th>
		<td>
			<ul style="width:100%" id="">
				<c:forEach var="orderGroupRole" items="${orderGroupRole }">
				<li style="width:33%;display:block">${orderGroupRole.rolename }&nbsp;</li>
				</c:forEach>
			</ul>
		</td>		
	</tr>
	<tr id="roletr">
		<th><label class="text"><span class='red'>*</span>设置角色：</label></th>
		<td>
			<ul style="width:100%" id="rolesbox">
				<c:forEach var="role" items="${roleList }">
				<li style="width:33%;display:block"><input type="checkbox" name="roleIds" value="${role.roleid }"  />
					${role.rolename }&nbsp;</li>
				</c:forEach>
			</ul>
		</td>		
	</tr>

</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td> 
     <input  type="submit" id="roleSaveBtn"	 value=" 确    定   "  class="submitBtn"  /></td>

	</tr>
	 </table>
	</div>  

<input type="hidden" name ="rolIdstr" id="rolIdstr">
</form>

</div>
<script>
	$(function(){
	   <c:forEach var="userRole" items="${orderGroupRole }">
		$("#rolesbox input[value='${userRole.roleid}']").attr("checked",true);
	   </c:forEach>	 
	   
	   $("#roleSaveBtn").click(function(){
	        var url = app_path +"/shop/admin/orderGroupAction!saveRole.do?ajax=yes";
	        
	            var roleIdArr = [];
		       $("[name='roleIds']:checked").each(function(){
					var role_id = $(this).val();
					roleIdArr.push(role_id);
				})
		   var rolIdstr = roleIdArr.join(",");
		  
		   $("#rolIdstr").val(rolIdstr);
	      Cmp.ajaxSubmit('roleSaveForm', '', url, {},function jsonBack(reply){
	          if(reply.result=='0'){
	            alert("操作成功");
	            window.location.href =ctx+"/shop/admin/orderGroupAction!list.do";
	            //window.location.reload();
	          }
	          if(reply.result=='1'){
	            alert(reply.message);
	          }
	      }, 'json');
	   });
	})
</script>