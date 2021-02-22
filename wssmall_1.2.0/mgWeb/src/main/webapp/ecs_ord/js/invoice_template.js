 //保存字段位置修改
var ActivityAddEdit = {
   init:function(){
	   var self =this;
	   
	   self.setCheckBox();
	 
	   $("#saveBtn").bind("click",function(){
		   self.saveActivity();
		     // editForm.submit();
		});
		
		
   },
  
  setCheckBox:function(){
	 
	  var relief_field_arr = [];
	  
	  $("input[attr='field_value']").each(function() 
			   {
		
		  	  relief_field_arr.push($(this).val());
			    		
			  });
	 for(var i=0;i<relief_field_arr.length;i++)
		 {
		  $("#"+relief_field_arr[i]).attr("checked",true);
		 }

	 
  },
  saveActivity:function(){
	    var self =this;
	    var params = self.getActivityData();
	   $.ajax({
	    async:false,
	    type : "POST",
	    dataType : 'json',
	    data :params,
	    url: "invoicePrintAction!editTemplateInformation.do?ajax=yes",
	    success : function(reply) {
	     //console.log(reply+" 11111");
	     if(reply.result == '1'){
	      debugger;
	      alert("保存成功!");
	      window.location.href = ctx + "/shop/admin/invoicePrintAction!search.do";;
	     }else{
	      alert("保存失败!");
	      //$("#saveAndPublishBtn").attr("disabled",false);
	     }
	    },
	    error : function(XMLHttpRequest, textStatus, errorThrown){
	     alert("出错了 ! XMLHttpRequest="+XMLHttpRequest);
	     //$("#saveAndPublishBtn").attr("disabled",false);
	    }
	   });
	  
	   },
	   //获取活动表单数据 
	   getActivityData:function(){
	   var params = {};
	   debugger;
	   //模板对应的域
	   var relief_no_class_arr = [];
	   $("input[name='relief_no_class_chk']:checkbox").each(function() 
	    {
	    if ($(this).attr("checked"))
	    {
	     relief_no_class_arr.push($(this).val());
	    }
	   });
	   
	  var mode_cd=$("#mode_id").val();
	  params["invoiceModeFieldParams.model_cd"]=mode_cd;
	  params["invoiceModeFieldParams.field_cdArr"] = relief_no_class_arr;
	
	   return params;
	   } 
}
