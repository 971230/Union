var editOrgUser={
    init:function(){
       var self = this;
       self.tabclick();
       self.editSave();
       self.add();
     },
    tabclick:function(){
     $(".graph_tab li").each(function(){
      $(this).click(function(){
          
         $(".graph_tab li").attr("class","");
         $(".detail").hide();
         $(".roleInfo").hide();
         $(this).attr("class","selected");
          var id = $(this).attr("divId");
         $("."+id).show();
      });
     });
    },
    
     editJsonBack:function(reply){
      if(reply.result==0){
        alert(reply.message);
        var  org_id = reply.org_id;
        var  dept_name = reply.dept_name;
        var  id = reply.id;
        var url = ctx+ "/core/admin/user/userAdmin!orgUserList.do?ajax=yes";
          $("#userPanle").empty();
          $("#userDetail").html("");
          $("#userPanle").load(url,{org_id:org_id,dept_name:dept_name},function(){
            
             $("#userid[value='"+id+"']").attr("checked","checked");
             $("#userid[value='"+id+"']").attr("class","clickClass");
             var url =app_path+"/core/admin/user/userAdmin!userDetail.do?ajax=yes&id="+id;
           
             $("#userDetail").load(url,{org_id:org_id,dept_name:dept_name},function(){
                // orgUser.showOrgDlg();
                $("#refStaffBtn").click(function(){
                   var party_id = $("#listOrgId").val();
                   var url=app_path+'/core/admin/user/userAdmin!getStaffList.do?ajax=yes&party_id='+party_id;
		         	$("#refStaffDlg").load(url,function(){
		         	     orgUser.staffJsonBack();
		         	});
				    Eop.Dialog.open("refStaffDlg");
             });
             });
          }); 
          
      }
      if(reply.result==1){
        alert(reply.messsage);
      }
    },
	
	
	  editSave:function(){
	    $("#editbtn").click(function(){
	          if($("input[name='roleids']:checked").length==0){
		       alert("请至少选择一个角色");
		       return false;
		       }
          // if(editOrgUser.checkpwdandCrm()){
	            var url = ctx + "/core/admin/user/userAdmin!orgUserSave.do?ajax=yes";
                Cmp.ajaxSubmit('editForm', '', url, {}, editOrgUser.editJsonBack, 'json');
              
	       // }
	  	 // else{
	  	 // return false;
	      //	}
        });
      },
	   add:function(){
	       $("#addBtn").click(function(){
	       var org_id = $("#listOrgId").val();
	       var dept_name = $("#listDeptName").val();
	       var userid =$("#paruserid").val();
	       var url =ctx+ "/core/admin/user/userAdmin!addOrgUser.do?ajax=yes";
	       $("#userDetail").empty();
	       $("#userDetail").load(url,{org_id:org_id,dept_name:dept_name,userid:userid},function(){
	           $("#refStaffBtn").click(function(){
	             var party_id = $("#party_id").val();
	             var url=app_path+'/core/admin/user/userAdmin!getStaffList.do?ajax=yes&party_id='+party_id;
		         	$("#refStaffDlg").load(url,function(){
		         	     orgUser.staffJsonBack();
		         	});
				    Eop.Dialog.open("refStaffDlg");
	             });
	          });
	    }); 
	   },
    checkpwdandCrm:function(){
        var username = $("#username").val();
		var pwd = $("#pwd").val();
		var repwd = $("#repwd").val();
	    var phone_num = $("#phone_num").val();
		var email = $("#email").val();
		if(username.length==0){
		  alert("工号不能为空");
		  return false;
		}
		if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)) {
		    alert("邮箱格式不正确");
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
};

$(function(){
  editOrgUser.init();
});