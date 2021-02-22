/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//分销商选择器
var supplierSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#searchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		//$("#toggleChk").click(function(){//点击了全选复选框
			//$("input[name=staffid]").attr("checked",this.checked);
	//	});
		$("#"+conid+" .submitBtn").click(function(){
		   
			if($('input[name="supplier_id"]:checked').length >0)
			{//赋值
				var value = $('input[name="supplier_id"]:checked').val();
				
				$("#supplier_id").val(value.split(",")[0]);
				$("#supplier_name").val(value.split(",")[1])
			}
			Eop.Dialog.close(conid);
			$('#up').parent().show();
		});
		 
	},
	search:function(onConfirm){
	    
		var self = this;
		var options = {
		       url : 'goodsAgreementAction!getSuppliesList.do?ajax=yes',
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
 
		$("#serchform").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	
	  	var self= this;
	  	
		var url=app_path+'/shop/admin/goods/goodsAgreementAction!getSuppliesList.do?ajax=yes';
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	}
};


