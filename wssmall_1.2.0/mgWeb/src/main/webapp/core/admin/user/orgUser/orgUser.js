var orgUser = {
  init:function(){
   var self = this;
  this.bindOrgClick();
  this.search();
   $("#orgSel").click(function(){
      var url = ctx + '/core/admin/user/userAdmin!list.do?ajax=yes';
		
        Eop.Dialog.open("refOrgDlg");
		$("#refOrgDlg").load(url,function(){
			 self.initDlgClk(); 
		}); 		  
     });
    $("#refAgentBtn").click(function(){
    	AgentSelector.open("refAgentDlg",null);
    });
    $("#refSupplierBtn").click(function(){
    	SupplierSelector.open("refSupplierDlg",null);
    });
  },
  bindOrgClick:function(){
   $("#load").click(function(){
  
        var org_id =$(this).attr("org_id");
        var dept_name =$(this).attr("dept_name");
      
        var url = ctx + "/core/admin/user/userAdmin!orgUserList.do?ajax=yes";
          $("#userPanle").empty();
         // $("#userDetail").html("");
          $("#userPanle").load(url,{org_id:org_id,dept_name:dept_name},function(){
            // orgUser.showUserDetail();
          }); 
  });
  },
  search:function(){
     $("#searchBtn").bind("click",function(){
        var realname =$("#realname").val();
        var username =$("#username").val();
        var state = $("#state").val();
        var org_id = $("#org_id").val();
 
        var url = ctx + "/core/admin/user/userAdmin!orgUserList.do?realname="+encodeURI(realname)+"&username="+encodeURI(username)+"&state="+state+"&org_id="+org_id;
       
        $("#userListIframe").attr("src",encodeURI(url));
          //$("#userDetail").empty();
        //  $("#userDetail").html("");
//          $("#userPanle").empty();
//          $("#userPanle").load(url,{realname:realname,username:username,state:state,org_id:org_id},function(){ 
//            //  orgUser.showUserDetail();  
//          }); 
    });
    	
  },
  initDlgClk:function(){
	  $("#orgForm .schConBtn").unbind("click").click(function(){//searchBtn 搜索
		var url = ctx + "/core/admin/user/userAdmin!list.do?ajax=yes";
		var party_id = $("#party_id").val();
		var org_name = $("#org_name").val();
		var lan_id = $("#lan_city").val();
		$("#lan_id").val(lan_id);
		Cmp.ajaxSubmit('orgForm','refOrgDlg',url,{"party_id":party_id, "org_name":org_name, "lan_id":lan_id},orgUser.initDlgClk);
	  });
			
	$("#orgForm .insureBtn").unbind("click").click(function(){//searchBtn 搜索
		var selectOrg = $("#gridform [name='selectOrg']:checked");
			
			if(selectOrg.length == 0){
				alert("请选择一条记录！");
				return;
			}
			var orgInfo = selectOrg.attr("ele").split("_");
			
			$("#org_id").val(orgInfo[0]);
			$("#org_name").val(orgInfo[1]);
		Eop.Dialog.close("refOrgDlg");
	});
  },		
  onAjaxCallBack:function(){
        $("#orgSelectedFrm .submitBtn").click(function(){
			var org_id = $('input[name="org"]:checked').val();
			var dep_name = $('input[name="org"]:checked').attr('depname');
			$("#org_id").val(org_id);
			$("#dep_name").val(dep_name);
			$(".closeBtn").trigger("click");
		})
  },
  staffJsonBack:function(){
     $("#qrStaffBtn").bind("click",function(){
        var $obj = $('input[name="staffid"]:checked');
          var staff_id = $obj.attr("staff_id");
        
          $('input[name="adminUser.username"]').val(staff_id);
          $('input[name="adminUser.relcode"]').val(staff_id);
          $("#phone_num").val($obj.attr("serial_number"));
          $('input[name="adminUser.user_pid"]').val($obj.attr("user_pid"));
          $('input[name="adminUser.realname"]').val($obj.attr("staff_name"));
          $("input[name='adminUser.sex'][value='"+$obj.attr("sex")+"']").attr("checked","checked");
          $("input[name='adminUser.birthday']").val($obj.attr("birthday"));
          
          Eop.Dialog.close("refStaffDlg");
     });
      $("#staffSearchBtn").bind("click",function(){
        var url= ctx + '/core/admin/user/userAdmin!getStaffList.do?ajax=yes';
        $("#staffSerchform").ajaxSubmit(url);	
      });
  },
  showUserDetail:function(){
        // $(".grid table tr").unbind("click").("click",function(){
        // $(".grid table tr").live("click",function(){
        $(".grid table tr").live("click",function(){
     	 $(".grid table tr").attr("class","");
         $(this).attr("class","clickClass");
         
       //  $(".clickClass #userid").attr("checked","checked");
         var $obj = $("input[type='radio']:checked");
         var id =$obj.attr("value");
         
         var org_id = $obj.attr("org_id");
         var dept_name = $obj.attr("dept_name");
        
         $("#userDetail").empty();
         var url = ctx + "/core/admin/user/userAdmin!userDetail.do?ajax=yes&id="+id;
         $("#userDetail").load(url,{org_id:org_id,dept_name:dept_name},function(){
             orgUser.selRole();
             $("#refStaffBtn").click(function(){
                   var party_id = $("#listOrgId").val();
                   var url = ctx + '/core/admin/user/userAdmin!getStaffList.do?ajax=yes&party_id='+party_id;
		         	$("#refStaffDlg").load(url,function(){
		         	     orgUser.staffJsonBack();
		         	});
				    Eop.Dialog.open("refStaffDlg");
             });
         });
    });
   
  },
  selRole:function(){
      $("#selRoleBtn").unbind("click").click(function(){
        //var id =  $("input[name='adminUser.userid']").val();
        //var id = $("#showRoleUserid").val();
      
        var url= ctx + '/core/admin/user/userAdmin!roleList.do?ajax=yes';
        //var userState = $("#userState").val();
      
		$("#selRoleDlg").load(url,{},function(){});
		 Eop.Dialog.open("selRoleDlg");
      });
  }
};
$(function(){
  Eop.Dialog.init({id:"refOrgDlg",modal:false,title:"组织架构",width:'1000px'});
  Eop.Dialog.init({id:"refAgentDlg",modal:true,title:"代理商",width:'800px'});
  Eop.Dialog.init({id:"refSupplierDlg",modal:true,title:"供应商",width:'800px'});
  Eop.Dialog.init({id:"selRoleDlg",modal:true,title:"选择角色",width:'800px'});
  orgUser.init();
});