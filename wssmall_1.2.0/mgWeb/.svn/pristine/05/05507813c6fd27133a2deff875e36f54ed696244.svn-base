warehouseList = {
     init : function() {
          var me = this;
          me.initClk();
     },
 initClk : function() {
          var me = this;
          $("#searchBot").bind("click",function() {
               me.searchBottonClk();
          });       
          $("#selectOkBot").unbind("click").bind("click",function(){
               me.selectOkClk();
          });       
          //点击行选择该行相应单选框
          $("#selectChoice").find("tbody").each(function(i,data) {
               $(data).find("tr").bind("click",function() {
                    $(this).find("td:eq(0)").find("input").attr("checked","checked");               
               });
          });
     },
     selectOkClk : function() {
          var selectObj = $("#selectChoice").find("input[type='radio']:checked");
       	  var house_name=$("[name='checkBox']:checked").val();
       	  var house_id=$("[name='checkBox']:checked").attr("house_id");        
          if(selectObj.length>0){
               $("#house_name").val(house_name);
               $("#house_id").val(house_id);
               Eop.Dialog.close("showWarehouseDiog");
          }else{
               alert("您还没选择仓库");
          }
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClk : function() {
          
          var me = this;
          
          var options = {
                    url :'warehouseGoodsStoreAction!showWarehouse.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#showWarehouseDiog").empty().append(result);
                         me.init();
                    },
                    error : function(e) {
                         alert("出错啦:(");
                    }
          };
          $("#choiceform").ajaxSubmit(options);
          
     }
     };
$(function(){
     warehouseList.init();
});