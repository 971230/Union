/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//分销商选择器

var addBrand={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		
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
	add:function(){
	  $("#add_brand_btn").click(function(){
	    
	     /* $.Loading.show("正在检测品牌名是否重复...");
				var name = $("#name").val();
				$("form").ajaxSubmit({
					url:'brand!checkname.do?ajax=yes&goodsAddBindType=T',
					type:'POST',
					dataType:'json',
					success:function(result){
						var form = $("form");
						if(result.result==1){
							if(confirm("品牌"+name+"已经存在，您确定要保存吗？")){
								$.Loading.hide();
							
								form.submit();
								if( form.attr("validate")=="true" ){
									form[0].submit();
								}								
							}
							$.Loading.hide();
						} else{
							$.Loading.hide();
							form.submit();
							if( form.attr("validate")=="true" ){
								form[0].submit();
							}			
						} 
					},error:function(){
						alert("检测名称出错");
					}
				});
	   */
	   });
	},
	open:function(conid,onConfirm){
	  	var self= this;
	  	
	  	 var type_id =  $("#type_id").val();
	    var url=app_path+"/shop/admin/brand!add.do?selfProdType=F&type_id="+type_id+"&ajax=yes";
		//var url='goods!goodsAgreementList.do?ajax=yes';
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
		  
		});
		Eop.Dialog.open(conid);		
	}
	
};

$(function(){
	addBrand.init();
	addBrand.add();
});


