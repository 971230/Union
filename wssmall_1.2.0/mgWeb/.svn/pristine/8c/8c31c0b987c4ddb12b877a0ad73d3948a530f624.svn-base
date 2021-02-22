var userSel = {
  init:function(){
      var self = this;
      this.bindAddClick();
      this.saveUser();
  },
  bindAddClick:function(){
    $("#addUserBtn").click(function(){
      var url =app_path+"/shop/admin/orderGroupAction!userList.do?ajax=yes";
     
       $("#addUserDlg").load(url,function(){
              userSel.bindQrBtn();
              userSel.search();
       });
       Eop.Dialog.open("addUserDlg");
    });
  },
  bindQrBtn:function(){
    $("#qrUserBtn").click(function(){
                 userSel.appendUser();
                 Eop.Dialog.close("addUserDlg");
              });
  },
  appendUser:function(){
      var htmlStr =""; 
      $("[name='userCheckBox']:checked").each(function(){
            var user_id = $(this).attr("userid");
            var username= $(this).attr("username");
            var realname = $(this).attr("realname");
            var count = 0;
            $("[name='orderGroupRel.userid']").each(function(){
                var userid = $(this).val();
                if(userid==user_id){
                   count = count+1;
                }
               
            });
            if(count==0){
                  htmlStr += userSel.htmlStr(user_id,username,realname);
              }
	      
	     });
	   
	  $("#userRow").append(htmlStr);
	  var htmlJq = $(htmlStr);
      htmlJq.find("[name='delContract']").bind("click", function () {
            $(this).closest("tr").remove();
      });
  },
  htmlStr:function(user_id,username,realname){
     var htmlStr = "<tr id='sel_'"+user_id+"><td width='20%'>"+
                    "<div align='center'>"+username+
                            "<input type='hidden' name='userIds' username='"+username+"' user_id='"+user_id+"' value='"+user_id+"' />"+
                        "</div>"+
                    "</td>"+
                    "<td>"+realname+"</td>"+
                    "<td width='20%'><div align='center'><a href='javascript:;' name='delContract'>删除</a></div></td>"+
                   "</tr>"; 
            return htmlStr;
  },
  search:function(){
     $("#searchBtn").click(function(){
         var self = this;
        
         var userid=$("userid").val();
         var username = $("username").val();
		 var options = {
		        url : app_path+'/shop/admin/orderGroupAction!userList.do?ajax=yes',
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#addUserDlg").empty().append(result);
					 userSel.bindQrBtn();
					 userSel.search();
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#serchform").ajaxSubmit(options);
     });
  },
  saveUser:function(){
    $("#saveUserBtn").click(function(){
                var userIdArr = [];
		        $("[name='userIds']").each(function(){
		         	var user_id = $(this).val();
					userIdArr.push(user_id);
				})
		        var userIdStr = userIdArr.join(",");
		       
		        $("#userIdStr").val(userIdStr);
         var url =app_path+"/shop/admin/orderGroupAction!saveUser.do?ajax=yes"
         Cmp.ajaxSubmit('userForm', '', url, {}, userSel.addUserJsonBack, 'json');
    });
  },
  addUserJsonBack:function(reply){
    if(reply.result=='0'){
      alert("操作成功");
        window.location.href =ctx+"/shop/admin/orderGroupAction!list.do";
     // window.location.reload();
    }
    if(reply.result=='1'){
      alert(reply.message);
    }
  }
};

$(function(){
    Eop.Dialog.init({id:"addUserDlg",modal:false,title:"添加员工",width:'800px'});
    userSel.init();
});