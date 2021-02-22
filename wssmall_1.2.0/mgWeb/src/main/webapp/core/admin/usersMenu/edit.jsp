<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="usersMenu/menuSel.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">修改快捷菜单</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  


<div class="input">
   <form  action="<%=request.getContextPath() %>/core/admin/usersMenuAction!editSave.do" class="validate" method="post" name="usersMenuEditForm" id="usersMenuEditForm" enctype="multipart/form-data">
      <table  class="form-table">
         <tr>
            <th><label class="text"><span class='red'>*</span>菜单名称：</label></th>
            <td><input name="usersMenu.menu_name" class="ipttxt" required="true" value="${usersMenu.menu_name}"></td>
         </tr>
         <tr>
            <th><lable class="text"><span class="red">*</span>菜单路径</th>
            <td>
               <input name="usersMenu.menu_url" class="ipttxt" required="true" id="menu_url" value="${usersMenu.menu_url}">
               <a class="graybtn1" href="#"><span id="refMenuBtn" >关联菜单</span></a>
            </td>
            <input type="hidden" name="menu_id" value="${usersMenu.menu_id}">
            <input type="hidden" name="user_id" value="${usersMenu.user_id}">
            <input type="hidden" name="usersMenu.menu_id" id="sel_menu_id" value="${usersMenu.menu_id}">
         </tr>
         <tr>
	         <th><label class="text">排序：</label></th>
	         <td><input name="usersMenu.sort" onkeyup="this.value=this.value.replace(/\D/g,'')"  class="ipttxt" value="${usersMenu.sort}"></td>
	     </tr>
	     <tr><th></th><td colspan="3"><input type="checkbox" id="imgChk">同时修改图片</td></tr>
	                 
	     <tr id="imgTr" style="display:none;">
          <th><label class="text">快捷菜单图片：</label></th>
          <td><input type="file" id="img_path" name="file" ></td>
         </tr>
      </table>
     
		 <table>
				<tr>
					<th></th>
					<td> 
				     	 <input  type="submit" id="editBtn"	 value=" 保   存 "  class="graybtn1"  />
				    </td>
				</tr>
		 </table>
	   
   </form>
</div>
<div id="selMenuDiv"></div> 
<script type="text/javascript">
  $(function(){
	  $("#usersMenuEditForm").validate();
	  $("#imgChk").click(function(){
		  if($(this).attr("checked")==true){
			  $("#imgTr").show();
			  $("#img_path").attr("required",true);
		  }else{
			  $("#img_path").val("");
			  $("#imgTr").hide();
			  $("#img_path").attr("required","");
		  }
	  });
	/*  $("#editBtn").unbind("click").bind("click",function(){
		   var url =ctx +"/core/admin/usersMenuAction!editSave.do?ajax=yes";
		   Cmp.ajaxSubmit("usersMenuEditForm",'',url,{},function editJsonBack(reply){
			  if(reply.result ==0){
				  alert("操作成功")
				  window.location.href =ctx +"/core/admin/usersMenuAction!listUsersMenu.do"
			  } else{
				  alert(reply.message);
			  }
		   },'json');
	  });*/
  });
</script>
