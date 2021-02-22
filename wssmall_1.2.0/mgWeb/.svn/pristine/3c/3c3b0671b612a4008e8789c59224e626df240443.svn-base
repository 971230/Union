<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>

<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">修改密码</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div class="input">
 <form  class="validate" method="post" action="" id='editpasswordform' validate="true">
  
 <input type="hidden" name="adminuser.userid" value="${adminuser.userid}" />
   <table cellspacing="1"  cellpadding="3" width="100%" class="form-table">
   <tr> 
       <th><label class="text">账号:</label></th>
       <td>${adminuser.username } </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>原始密码:</label></th>
       <td><input type="password"  class="ipttxt"  name="oldPassword" id="oldPassword"  maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>新密码:</label></th>
       <td>
         <input type="password"  class="ipttxt"  name="newPassword" id="newPassword"  maxlength="60"   dataType="string" required="true"/> 
       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>确定新密码:</label></th>
       <td><input type="password"  class="ipttxt"  name="newPassword2" id="newPassword2"  maxlength="60"   dataType="string" required="true"/> </td>
     </tr>
     </table>
     <div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="paraddr.addr_id" value="${paraddr.addr_id }">
		 	 <input  type="button"  id="editpassword" value=" 保存 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
</form></div>

 <script type="text/javascript">
    $(function (){
       $("#oldPassword").focus();
       $("#editpasswordform").validate();
       $("#editpassword").click(function (){
           if($("#oldPassword").val()=="" && $("#newPassword").val()=="" && $("#newPassword2").val()==""){
              return false;
           }
          if($("#oldPassword").val()==$("#newPassword").val()){
              alert("新密码不能等于旧密码！");
              return false;
           }
           if($("#newPassword").val()!=$("#newPassword2").val()){
               alert("两次输入的密码不一致！");
               $("#newPassword").val("");
               $("#newPassword2").val("");
               return false;
           }
           var url = ctx+ "/shop/admin/partner!editPassword.do?ajax=yes";
			Cmp.ajaxSubmit('editpasswordform', '', url, {}, function(responseText){
					alert(responseText.message);
					$("input[type=password]").val("");
			},'json');
       });
    });
</script>

