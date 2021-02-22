var $objClick;
var control_val={
   
   init:function(){
      control_val.txtChange();
   },
   txtChange:function(){
    /*
      $("[name='qty_control_value']").keyup(function(){
         $objClick = $(this);
         
         var lan_code = '10000000';
         var curr_control_val = $(this).val();
         
        control_val.getIsOverInfo(lan_code,curr_control_val);
       
     });*/
     $("[name='money_control_value']").keyup(function(){
         $objClick = $(this);
        
         var lan_code = '10000000';
         var curr_control_val = $(this).val();
         
       control_val.getIsOverInfo(lan_code,curr_control_val);
        
     });
    
     
        $("[name='control_lan_code']").each(function(){
      
        $(this).keyup(function(){
         $objClick = $(this);
          var sub_control_type = $("#sub_portion_type").val();
          
         if(sub_control_type=="MO"){
         var lan_code = $(this).attr("id");
         var curr_control_val = $(this).val();
         control_val.getIsOverInfo(lan_code,curr_control_val);
           }
        });
     });
   
   },
   getIsOverInfo:function(lan_code,curr_control_val){ 
      var agreement_id = $("#prtcl_id").val();
      
      var url ="goods!controlValue.do?ajax=yes&agreement_id="+agreement_id+"&lan_code="+lan_code+"&curr_control_value="+curr_control_val;
     
   
		Cmp.excute('', url, {}, control_val.JsonBack, 'json');
	
	  
   },
  
	JsonBack : function(responseText) { // json回调函数
		if (responseText.result == 0) {
		     
			}
		 if (responseText.result == 1) {
			alert(responseText.message);
			
		      $objClick.val("");
	        }	
	}
};

$(function(){
    control_val.init();
});