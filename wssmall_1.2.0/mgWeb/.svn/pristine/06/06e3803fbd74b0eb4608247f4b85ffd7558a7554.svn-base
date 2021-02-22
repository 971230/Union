<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="usersMenu/menuSel.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">添加快捷菜单</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  

<div class="input">
   <form  action="<%=request.getContextPath() %>/core/admin/usersMenuAction!addSave.do"  class="validate" method="post" name="usersMenuAddForm" id="usersMenuAddForm" enctype="multipart/form-data">
      <table  class="form-table">
         <tr>
            <th><label class="text"><span class='red'>*</span>菜单名称：</label></th>
            <td><input name="usersMenu.menu_name" class="ipttxt" required="true"></td>
         </tr>
         <tr>
            <th><lable class="text"><span class="red">*</span>菜单路径</th>
            <td>
               <input name="usersMenu.menu_url" class="ipttxt" required="true" id="menu_url">
               <a class="graybtn1" href="javascript:;"><span id="refMenuBtn" >关联菜单</span></a>
            </td>
            <input type="hidden" name="usersMenu.menu_id" id="sel_menu_id">
         </tr>
         <tr>
          <th><label class="text">排序：</label></th>
          <td><input name="usersMenu.sort" onkeyup="this.value=this.value.replace(/\D/g,'')"   class="ipttxt"></td>
         </tr>
         <tr>
          <th><label class="text">快捷菜单图片：</label></th>
          <td><input type="file" id="img_path" name="file" ></td>
         </tr>
      </table>
       <div class="submitlist" align="center">
		 <table>
				<tr>
					<th></th>
					<td> 
				     	 <input  type="submit" id="addBtn"	 value=" 保   存 "  class="graybtn1"  />
				    </td>
				</tr>
		 </table>
	  </div>  
   </form>
</div>
<div id="selMenuDiv"></div> 
<script type="text/javascript">
  $(function(){
	  $("#usersMenuAddForm").validate();
	 /* $("#addBtn").unbind("click").bind("click",function(){
		   var url =ctx +"/core/admin/usersMenuAction!addSave.do?ajax=yes";
		   Cmp.ajaxSubmit("usersMenuAddForm",'',url,{},function addJsonBack(reply){
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