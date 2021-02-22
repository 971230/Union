/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//供应商选择器
var SupplierSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#supplierSearchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		//$("#toggleChk").click(function(){//点击了全选复选框
			//$("input[name=staffid]").attr("checked",this.checked);
	//	});
		$("#supplierSelInsureBtn").click(function(){
		  
			if($('input[name="supplierId"]:checked').length >0)
			{	
				//赋值
				var value = $('input[name="supplierId"]:checked').val();
			
				//$("#ref_no").val(value.split(",")[0]);
				$("#relcode").val(value.split(",")[0]);
			   // $("#real_name").val(value.split(",")[1]);
			    //$("#user_name").val(value.split(",")[2]);
				$("#supplier_name").val(value.split(",")[1]);
				var phoneNumber = value.split(",")[3];
			    $("#phone_no").val(phoneNumber);
				$("#phone_num").val(phoneNumber);
			   
			}
			Eop.Dialog.close(conid);
		});
		 
	},
	search:function(onConfirm){
	    
		var self = this;
		var options = {
		       url : app_path+'/core/admin/user/userAdmin!getSupplierFind.do?ajax=yes',
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#supplierserchform").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	  	var self= this;
		var url=app_path+'/core/admin/user/userAdmin!getSupplierFind.do?ajax=yes';
		
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	}
};


