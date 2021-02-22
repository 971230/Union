<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
<form   class="validate"  method="post" name="theForm" id="addForm" enctype="multipart/form-data">
     <div class="stat_graph">
        	<h3>
                <div class="graph_tab">
                    <ul>
                        <li divId="detail" class="selected"><span class="word">基本信息</span><span class="bg"></span></li>
                        <li divId="roleInfo" ><span class="word">人员角色</span><span class="bg"></span></li>
                        <div class="clear"></div>
                    </ul>
                </div>
            </h3>
    </div>
   <div class="tab-page">
		<div id="detail">
		
		   <input name="adminUser.relcode"  type ="hidden" value="adminUser.relcode" id='relcode'>
		   <input type="hidden" value ="${org_id }" name ="org_id" id="party_id">
		   <input type="hidden" value="${dept_name }" name ="dept_name">
		    <table  class="form-table">
		         <input type="hidden" value ="${userid}"  id ="editUserId"/>
		        <tr>
		         <th><label class="text"><span class='red'>*</span>用户工号：</label></th>
		          <td>
			        <input type="text" class="ipttxt" name="adminUser.username" value="${adminUser.username }"  dataType="string" required="true" id ="username"/>
<!-- 		              <a class="graybtn1" href="#" id="refStaffBtn" >关联Boss工号</a> -->
		          </td>
		          <th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		          <td><input type="text" name="adminUser.realname" class="ipttxt"  required="true"  id="realname" /></td>
	            </tr>
		         <tr id="pwdtr" >
		           <th><label class="text"><span class='red'>*</span>密码：</label></th>
		           <td><input type="password" class="ipttxt" name="newPassword" id="pwd"  /></td>
		           <th><label class="text">确认密码：</label></th>
		           <td><input type="password"  class="ipttxt" name="adminUser.password" id="repwd" dataType="string"  /></td>
	             </tr>
		       <tr>
		          <th>员工类型：</th>
		          <td>
		           <c:if test="${currFounder==1}">
		                <input type="radio" class="ipttxt"  name="adminUser.founder" value="0" id="" checked="true">员工
		           </c:if>
		            <c:if test="${currFounder==0}">
		                <input type="radio" class="ipttxt" name="adminUser.founder" value="3" id="" checked="true">分销商
		            </c:if>
		           </td>
		           <th><label class="text">性别：</label></th>
	               <td>
		               <c:if test="${adminUser.sex==0 }">
		               		<input name="adminUser.sex" class="ipttxt"  type="radio" value ="0" checked="true"/>男
		               		<input name="adminUser.sex" class="ipttxt" type="radio" value="1" />女
		               </c:if>
		               	<c:if test="${adminUser.sex==1}">
		               		<input name="adminUser.sex" class="ipttxt"  type="radio" value ="0"/>男
		               		<input name="adminUser.sex" class="ipttxt" type="radio" value="1"  checked="true" />女
		               	</c:if>
	               	</td>
	            </tr>
	            
	           <tr>
	              <th><label class="text"><span class='red'>*</span>身份证号码：</label></th>
	             <td><input name ="adminUser.user_pid" class="ipttxt"  class="ipttxt"  value ="${adminUser.user_pid }"></td>
	             <th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
		         <td><input type="text" class="ipttxt"  value="" name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
	           
	           </tr>
	          <tr>
	             <input type="hidden" name="adminUser.org_id" value="${org_id }" id="org_id"/>
		        <th><label class="text"><span class='red'>*</span>组织部门：</label></th>
		        <td>
			     <input type="text" name="adminUser.dep_name" class="ipttxt" value="${dept_name }" disabled="disabled"  dataType="string"  id ="dep_name"/>
			     </td>
	              <th><label class="text">用户邮箱：</label></th>
	             <td><input type="text" class="ipttxt"  value="" name="adminUser.email"  id="email"/></td>
		      </tr>
		      <!-- 
		       <tr>
	            <th><label class="text">用户级别：</label></th>
		         <td><input type="text"  value="" name="adminUser.user_level"  id="user_level"   /></td>
	             <th><label class="text">用户类型：</label></th>
	             <td><input type="text"  value="" name="adminUser.boss_user_type"  id="boss_user_type"   /></td>
		      </tr>
		       -->
	          <tr>
		         <input type="hidden"   name="adminUser.state" value="1"  />
		         <th><label class="text">备注：</label></th>
		          <td><input type="text" class="ipttxt" name="adminUser.remark" /></td>
		        <th></th>
		        <td></td>   
	         </tr>
		
		
	</table>
   </div>
		<div id="roleInfo" style="display:none;">
		  <table class="form-table">
		        <!-- 一级分销商 查看自己时候 不显示角色· -->
					<c:if test="${roleList !=null && fn:length(roleList) > 0 }">
					<tr id="roletr">
						<th><label class="text"><span class='red'>*</span>人员角色：</label></th>
						<td>
							<ul style="width:100%" id="rolesbox">
								<c:forEach var="role" items="${roleList }">
					<li style="width:33%;display:block"><input type="checkbox" name="roleids" id="${role.roleid}" value="${role.roleid }"  />
					${role.rolename }&nbsp;</li>
									</c:forEach>
								</ul> 
							</td>		
						</tr>
					</c:if>
		  </table>
		</div>
		<div class="submitlist" align="center">
 <table>
 <tr>
 <th></th>
 <td >
  <input  type="button"	id="addSaveBtn"  value="保存" class="submitBtn" />
  <input  type="button"	id="returnBtn"  value="返回" class="submitBtn" />
 
   </td>
   </tr>
 </table>
</div>
   </div>
</form>
</div>



<script type="text/javascript">
 
    $(".graph_tab li").each(function(){
      $(this).click(function(){
         $(".graph_tab li").attr("class","");
         $("#detail").hide();
         $("#roleInfo").hide();
         $(this).attr("class","selected");
          var id = $(this).attr("divId");
         $("#"+id).show();
      });
     });
    
    function addJsonBack(reply){
      if(reply.result==0){
        
        alert(reply.message);
          var  org_id = reply.org_id;
          var  dept_name = reply.dept_name;
       
        var url = ctx + "/core/admin/user/userAdmin!orgUserList.do?ajax=yes";
          $("#userPanle").empty();
          $("#userDetail").html("");
          $("#userPanle").load(url,{org_id:org_id,dept_name:dept_name},function(){
              //orgUser.showUserDetail();
          }); 
      }
      if(reply.result==1){
        alert(reply.messsage);
      }
    }
	
	  $("#addSaveBtn").click(function(){
	     if(checkpwdandCrm()){
	       var url = ctx + "/core/admin/user/userAdmin!orgUserSave.do?ajax=yes";
          Cmp.ajaxSubmit('addForm', '', url, {}, addJsonBack, 'json');
	     }
	   });
	$("#returnBtn").click(function(){
	      var id =$("#userid").val();
	      var org_id = $("#org_id").val();
          var dept_name = $("#dep_name").val();
        
         $("#userDetail").empty();
         var url = ctx + "/core/admin/user/userAdmin!userDetail.do?ajax=yes&id="+id;
         $("#userDetail").load(url,{org_id:org_id,dept_name:dept_name});
	});
     $("#editUserId").val();
	
	
	
	
	
	function checkpwdandCrm(){
        var username = $('input[name="adminUser.username"]').val();
		var pwd = $("#pwd").val();
		var repwd = $("#repwd").val();
	    var phone_num = $("#phone_num").val();
		var email = $("#email").val();
		
		if(username.length==0){
		  alert("工号不能为空");
		  return false;
		}
		if(email.trim() != ""){
			if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)) {
			    alert("邮箱格式不正确");
			   return false;
			}
		}
		
		if(pwd.length==0){
		 alert("密码不能为空");
		 return false;
		}
	
		if(pwd!=repwd ){
		alert("两次密码不一致！");
			return false;
		}
	    if(phone_num.length==11){
	      if (!(/^[0-9]+$/.test(phone_num))) {
	         alert("电话号码只能是11位的数字组成");
	         return false;
	         }
	       }else{
	       alert("电话号码只能是11位的数字组成");
	       return false;
	       }
	       
		if($("input[name='roleids']:checked").length==0){
		  alert("请至少选择一个角色");
		    return false;
		}
	   
		
	//	if($("#lan_sel").val().length==0){
	//	  alert("请选择本地网");
	//	  return false;
	//	}
		
		if(username.length>15){
	      alert("用户名长度不能大于18位");
	      return false;
	     }
		if(username.length>0){
	    if (/^[a-zA-Z0-9]+$/.test(username)) 
	                 {
	                     return true;
	                 }
	                 else {
	                 alert("用户名只能是数字或者字母组成");
	                     return false;
	                 }
	     }
	   
	     
		       return true;
}
</script>