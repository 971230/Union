editChoiceList = {
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
          var staff_code =$("[name='checkBox']:checked").val();
         var staff_code=$("[name='checkBox']:checked").attr("staff_code");       
          if(selectObj.length>0){
               $("#nameInput").val(selectObj.val());
               $("#nameInputVal").val(staff_code);
               $("#staff_code").val(staff_code);
               
              $("#staff_code").val(staff_code)
               Eop.Dialog.close("staffDiog");
               
          }else{
               alert("您还没选择工号");
          }
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClk : function() {
          
          var me = this;
          
          var options = {
                    url :'mblLoginAction!staffList.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#staffDiog").empty().append(result);
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
     editChoiceList.init();
});