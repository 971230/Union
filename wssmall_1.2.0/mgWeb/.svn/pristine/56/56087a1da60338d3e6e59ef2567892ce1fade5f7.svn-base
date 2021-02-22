
var contractSel={
    conid:undefined,
    init:function()	{
       // this.conid = conid;
        var self  = this;
    
        $("#toggleChk").click(function(){
            $("input[name=goodsid]").attr("checked",this.checked);
        });
        
        $("#selContractBtn").click(function(){
            self.callBack();
        });
       
    },
    search:function(onConfirm){
        var self = this;
        var options = {
            url :ctx+"/shop/admin/goodsContractAction!queryContract.do?ajax=yes",
            type : "GET",
            dataType : 'html',
            success : function(result) {
                $("#"+self.conid).empty().append(result);
                self.init(self.conid,onConfirm);
            },
            error : function(e) {
                alert("出错啦:(");
            }
        };

        $("#contractForm").ajaxSubmit(options);
    },
    fillHtml:function(goods_id,goods_name){
      
       var html = "<tr id='complex_"+goods_id+"'> <td width='20%'><div align='center'>"+goods_name+
                  "<input type='hidden' name='goods_Contract' goods_name='"+goods_name+"' goods_id='"+goods_id+"' value='"+goods_id+"' />"+
                  " <a href='javascript:void(0);' class='delete'/></div></td>"+
                  "<td width='20%'><div align='center'><a href='javascript:void(0);' goods_id="+goods_id+" name='delContract'>删除</a></div></td>"+
                  "</tr>";
          return html;
    },
    fillBtnHtml:function(){
      var html = "<div class='submitlist'> <table align='center'>"+
                 "<tr><td><input type='hidden'  name='adminUser.paruserid' value='${currUserId }' >"+
                 " &nbsp&nbsp&nbsp;<input  type='button' id='setContractBtn'	 value='关联合约'  class='submitBtn'  /></td>"+
                 "<td></td></tr></table></div>  ";
      return html;
    },
    callBack:function(){
      var $obj = $("#contractSel [name='goodsid']:checked");
      var goodsArr = [];
     
         $("[name='goods_Contract']").each(function(){
          
            goodsArr.push($(this).val());
         });
     
      if($obj.length>0){
         $obj.each(function(){
             var goods_id = $(this).val();
             var goods_name = $(this).attr("goods_name");
             var count = 0;
          for(var i=0;i<goodsArr.length;i++){
	            if(goods_id == goodsArr[i]){
	              	count = 1;
	             }
           }
           if(count == 0){
               var html = contractSel.fillHtml(goods_id,goods_name);
               var htmlJq = $(html); 
               htmlJq.appendTo($("#qrContract"));
               htmlJq.find("[name='delContract']").bind("click", function () {
                    $(this).closest("tr").remove();
               });
               
           }
         });
      
      }
       Eop.Dialog.close("listSelContract");
    },
    bindRegBtn:function(conid,onConfirm){
        //var setContractBtnHtml = contractSel.fillBtnHtml();
        //var htmlBtn = $(setContractBtnHtml);
        //$(".submitlist").remove();
        htmlBtn.appendTo("#contractDiv");
        htmlBtn.find("[id='setContractBtn']").bind("click", function () {
                  
                });
    },
    setContractInst:function(conid,onConfirm){
      $("#setContractBtn").unbind("click").bind("click",function(){
             var $contractObj = $("[name='goods_Contract']"); 
             var valueStr = "[";
	         $contractObj.each(function(){
	             var goods_id = $(this).attr("goods_id");
	             var goods_name = $(this).attr("goods_name");
	             valueStr += "{goods_id:'"+goods_id+"',goods_name:'"+goods_name+"'},";
	        });
	        valueStr =  valueStr.substring(0,valueStr.length-1)+"]";
	   
	        $("#tr_"+onConfirm).val("");
	        $("#tr_"+onConfirm).val(valueStr);
	        
	        Eop.Dialog.close(conid);
      });
    },
    openExitContract:function(conid,onConfirm,rel_contract_inst){
        var self= this;
       // var editGoodsId = $("#editGoodsId").val();
        var goods_id = onConfirm;
        
        $("#"+conid).load(ctx+'/shop/admin/goodsContractAction!queryContract.do?ajax=yes',{editGoodsId:goods_id},function(){
        	self.setContractInst(conid,onConfirm);
        });
        
           Eop.Dialog.open(conid); 
    },
    qryContract:function(conid, aGoodsId, onConfirm){
    	 var self= this;
       // var editGoodsId = $("#editGoodsId").val();
        var goods_id = onConfirm;
        
        $("#"+conid).load(ctx+'/shop/admin/goodsContractAction!queryContract.do?ajax=yes',{a_goods_id:aGoodsId, editGoodsId:goods_id},function(){
        	self.setContractInst(conid,onConfirm);
        });
        
           Eop.Dialog.open(conid); 
    },
    openContractList:function(){
          $("#listSelContract").load(ctx+'/shop/admin/goodsContractAction!selContractList.do?ajax=yes',function(){
                 contractSel.init();
                
          });
          Eop.Dialog.open("listSelContract"); 
    }
   
}