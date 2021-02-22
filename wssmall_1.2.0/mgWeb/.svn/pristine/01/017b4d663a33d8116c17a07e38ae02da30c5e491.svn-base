var agreement={
  
  addSave:function(){
    var self =this;
    
    var url =  "goodsAgreementAction!goodsAgreementSave.do?ajax=yes";
	   Cmp.ajaxSubmit('goods_agreement', '', url, {}, agreement.jsonBack, 'json');
  },
 
  del:function(){
     var self = this;
     var checkLength = $("[name='agreement_checked']:checked").length;
     if(checkLength==0){
       alert("请选择要删除的框架协议");
       return false;
     }
     if(!confirm("确认要删除选中的框架协议吗?")) {
			return;
		}
     var agreement_id  = $("[name='agreement_checked']:checked").val();
     	
     var url =  "goodsAgreementAction!delAgreement.do?ajax=yes&agreement_id="+agreement_id;
	   Cmp.ajaxSubmit('gridform', '', url, {}, agreement.deljsonBack, 'json');
  },
  
  editSave:function(){
     
      var url =  "goodsAgreementAction!GoodsAgreementEditSave.do?ajax=yes";
	  Cmp.ajaxSubmit('edit_goods_agreement', '', url, {}, agreement.jsonBack, 'json');
  },
  showDialg:function(){
    Eop.Dialog.init({id:"refsupplierDlg",modal:true,title:"选择供应商",width:'850px'});
         
         $("#refsupplierBtn").click(function()
          {
            supplierSelector.open("refsupplierDlg",null);
           
         });
  },
  jsonBack : function(responseText) { // json回调函数
       
		if (responseText.result == 1) {
			alert(responseText.message);
			window.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
			 window.location="goodsAgreementAction!goodsAgreementList.do";
		}
	},
	 deljsonBack : function(responseText) { // json回调函数
       var  agreement_name = $("#agreement_name").val();
		if (responseText.result == 1) {
			alert(responseText.message);
		}
		if (responseText.result == 0) {
			alert(responseText.message);
			window.location.reload(true);
		}
	},
	    
  checkVal:function(){
   var supplier_id = $("#supplier_id").val();
   var flow_id = $("#flow_id").val();
   var order_manager = $("#order_manager").val();
   //为true 是非数字
  
     
      if(isNaN(flow_id)||flow_id.length>=9){
       alert("流程ID必须是小于9位的数字");
       return false;
     }
     else if(isNaN(order_manager)||order_manager.length>=9){
       alert("订单管理员ID必须是小于9位的数字");
       return false;
     }
     else{
      var flag = true ;
      var  selCompanTypeLength  = $("[name='checkCompany']:checked").length;
      var portionLength = $("[name='agreement.portion']:checked").length;
       
      if(selCompanTypeLength==0){
        alert("请选择份额控制类型");
           flag = false;
           return false;
      }
      else if(portionLength==0){
          alert("请选择协议控制类型");
           flag = false;
           return false;
         }
      $("[name='checkCompany']").each(function(){
        
          var $obj = $(this);
           if($(this).attr("checked")==true){
             var val = $("#"+$obj.val()+"_id").val();
             if(val.length==0||val=='undefined'){
               alert("请输入"+$obj.attr("txt_vlaue")+"编码");
               flag = false;
               return false;
             }
           }
       });
       
       
         $("[name='agreement.portion']").each(function(){
             var $obj = $(this);
             if($obj.attr("checked")==true){
                  if($obj.val()=="CO"){
                   var sub_type = $("[name='agreementControl.sub_control_type']:checked").val();
                   var sub_type_length = $("[name='agreementControl.sub_control_type']:checked").length; 
                   if(sub_type_length==0){
                     alert("请选择控制类型");
                     flag = false;
                     return false;
                   }
                   var lan_codeSel = $("[name='agreementControl.control_lan_code']:checked").length;
                    
                   if(lan_codeSel==0&&sub_type=="MO"){
                       alert("请选择控制本地网公司");
                       flag = false;
                       return false;
                   }
                  } 
             }
            
         });
       
         return flag;
         }
         
  },
	
  init:function(){
     $("[name='agreementControl.sub_control_type'][value='MO']").click(function(){
           $("#city_control").show();
       });
       $("[name='agreementControl.sub_control_type'][value='PN']").click(function(){
           $("[name='agreementControl.control_lan_code']").attr("checked",false);
           $("#city_control").hide();
       });
    
    $("#agree_input_btn").bind("click",function(){
        if(agreement.checkVal()==false){
        return false;
       }
        agreement.sendValue();
        agreement.addSave();
    });
    $("#agreement_deleteBtn").bind("click",function(){
     
     agreement.del();
    });
    $("#agree_input_btn").bind("click",function(){
       
    });
    
    $("#agree_edit_btn").bind("click",function(){
       if(agreement.checkVal()==false){
        return false;
       }
        agreement.sendValue();
        agreement.editSave();
      
    });
    
    agreement.showDialg();
    agreement.bindCheck();
    agreement.bindRadioClick();
   
  },
  
  bindCheck:function(){
     
     $("[name='checkCompany']").each(function(){
        var $obj = $(this);
        $(this).click(function(){
           if($(this).attr("checked")==true){
             $("#"+$obj.val()).show();
           }
           if($(this).attr("checked")==false){
             $("#"+$obj.val()+"_id").val("");
             $("#"+$obj.val()).hide();
           }
        });
     });
     
  } ,
  bindRadioClick:function(){
    
    $("[name='agreement.portion']").each(function(){
       var $obj = $(this);
        $(this).click(function(){
            $("[name='agreement.portion']").each(function(){
               $("#"+$(this).val()).hide();
            });
            $("#city_control").hide();
             $("#"+$obj.val()).show();
             if($obj.val()=="CO"){
              $("#city_control").show();
             }
        });
       
    });
  },
  
  sendValue:function(){
     var portionStr = $("[name='agreement.portion']:checked");
      $("#controlType").val(portionStr.val());
      $("#controlName").val(portionStr.attr("control_name"));
     agreement.getAgreementCode();
     agreement.getControlType();
  },
  getControlType:function(){
    	var companyType_valArr = [];
	
		$("[name='checkCompany']:checked").each(function(){
					var type_val = $(this).val();
					var code = $("#"+type_val+"_id").val();
					companyType_valArr.push(type_val+code);
				});
		var companyType_valstr = companyType_valArr.join(",");
		 $("#agreementCode").val(companyType_valstr);
  },
  
  getAgreementCode:function(){
     var controlTypeValue = $("[name='agreement.portion']:checked").val();
    
       if(controlTypeValue=='CO'){
          var codeArr = [];
          var controlValArr = [];
          $("[name='agreementControl.control_lan_code']:checked").each(function(){
					var codeValue = $(this).val();
					var control_val = $("#"+codeValue).val();
					codeArr.push(codeValue);
					controlValArr.push(control_val);
				});
		  var codeStr = codeArr.join(",");
		  var controlValStr = controlValArr.join(",");
		  $("#codeStr").val(codeStr);
		  $("#controlValStr").val(controlValStr);
       }
  }

};

$(function(){

   agreement.init();
});