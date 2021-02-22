/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
var StaffSelector ={
	wrapper_id:'refStaffDlg', //弹出页面id
	form_id:'serchform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"refStaffDlg",modal:false,title:"关联CRM",width:'800px'});
        $("#refStaffBtn").click(function(){ //点击按钮弹出界面
            if(ischeckedLength()){
            	var userType = $('input[name="adminUser.founder"]:checked').val(); 
           	 	self.open(userType);
            };
        });    
	},
	page_init:function(){ //ajax页面init
		var self =this;
		$("#searchBtn").click(function(){//searchBtn 搜索
			self.search();
		});
		$("#"+this.form_id+" .submitBtn").click(function(){
			if($('input[name="staffid"]:checked').length >0)
			{	
				//赋值
				var value = $('input[name="staffid"]:checked').val();
			  //$("#refCrm_no").val(value.split(",")[0]);
				$("#refCrm_code").val(value.split(",")[1]);
			    $("#username").val(value.split(",")[1]);
			    $("#realname").val(value.split(",")[2]);
			    $("#phone_num").val(value.split(",")[3]);
			    $("#lan_sel").val(value.split(",")[4]);
				
			}
			Eop.Dialog.close(this.wrapper_id);
			$(".closeBtn").trigger("click");
		});
	},
	onAjaxCallBack:function(){//ajax回调函数
		StaffSelector.page_init();
	},
	open:function(userType){ //弹出页面
		var url=app_path+'/core/admin/user/userAdmin!getStaffFind.do?ajax=yes&userType=' + userType;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	},
	search:function(params){ //点击搜索按钮搜索
		jQuery.extend(params,this.params);
		var userType = $('input[name="adminUser.founder"]:checked').val(); 
		var url=app_path+'/core/admin/user/userAdmin!getStaffFind.do?ajax=yes&userType=' + userType;
		Cmp.ajaxSubmit(this.form_id,this.wrapper_id,url,this.params,this.onAjaxCallBack);
	}
}

var OrgSelector ={
	wrapper_org_id:'refOrgDlg', //弹出页面id
	form_id:'orgSelectedFrm',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
	    Eop.Dialog.init({id:"refOrgDlg",modal:false,title:"组织架构",width:'800px'});
        $("#refOrgBtn").click(function(){ //点击按钮弹出界面
         	self.openOrg()
        });         
	},
	page_init:function(){ //ajax页面init
		var self =this;
		$("#"+this.form_id+" .submitBtn").click(function(){
			var org_id = $('input[name="org"]:checked').val();
			var dep_name = $('input[name="org"]:checked').attr('depname');
			$("#org_id").val(org_id);
			$("#dep_name").val(dep_name);
			$(".closeBtn").trigger("click");
		})

	},
	onAjaxCallBack:function(){//ajax回调函数
		OrgSelector.page_init();
	},
	openOrg:function(userType){ //弹出页面
		var url=app_path+'/core/admin/user/userAdmin!getOrgFind.do?ajax=yes';
		Cmp.excute(this.wrapper_org_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_org_id);
	}
	
}

$(function(){
	StaffSelector.init();
	OrgSelector.init();
})

