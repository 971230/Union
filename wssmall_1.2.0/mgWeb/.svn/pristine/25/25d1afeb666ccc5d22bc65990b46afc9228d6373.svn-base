/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//分销商选择器

var agreementSelector={
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
			if($('input[name="agreement_id"]:checked').length >0)
			{//赋值
				var value = $('input[name="agreement_id"]:checked').val();
				$("#"+onConfirm+"id").val(value.split(",")[0]);
				$("#"+onConfirm+"name").val(value.split(",")[1]);
				
				
				self.hideTr();//初始话隐藏tr
				var control_type= value.split(",")[2];
				var sub_control_type = value.split(",")[3];
				
				$("#portion_type").val(control_type);
				$("#sub_portion_type").val(sub_control_type);
				
				if(control_type=='NO'){
				    $("#PN_id option[value='0']").attr("selected", true);
				    $("#MO_id option[value='0']").attr("selected", true);
				}else if(control_type=='CO'){
				    $("#"+control_type).show();
				    $("#"+sub_control_type+"_id option[value='1']").attr("selected", true);
				    $("#"+sub_control_type+"_control").show();
				}else{
				    
				    $("#"+control_type).show();
				    $("#"+control_type+"_id option[value='1']").attr("selected", true);
				    $("#"+control_type+"_control").show();
				}
				
				
			
			}
		   Eop.Dialog.close(conid);
		   $('#up').parent().show();
		});
		
		$(".closeBtn").bind("click",function(){
			$('#up').parent().show();
			Eop.Dialog.close(conid);
		});
	},
	
	search:function(onConfirm){
	    
		var self = this;
		var options = {
		       url : 'goods!goodsAgreementList.do?ajax=yes',
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
 
		$("#agreementserchform").ajaxSubmit(options);		
	},
	open:function(conid,onConfirm){
	
	  	var self= this;
	  	
		var url='goods!goodsAgreementList.do?ajax=yes';
		
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	},
	hideTr:function(){
	  $("#PN_control").hide();
	  $("#MO_control").hide();
	  $("#MO").hide();
	  $("#PN").hide();
	  $("#CO").hide();
	  $("#PN_id option[value='0']").attr("selected", true);
	  $("#MO_id option[value='0']").attr("selected", true);
	}
};

$(function(){
	agreementSelector.init();
});


