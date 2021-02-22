editChoiceLists = {
     init : function() {
          var me = this;
          me.initClks();
     },
 initClks : function() {
          var me = this;
          $("#searchBot1").bind("click",function() {
               me.searchBottonClks();
          });       
          $("#selectOkBot1").unbind("click").bind("click",function(){
               me.selectOkClks();
          });       
          //点击行选择该行相应单选框
          $("#selectChoices").find("tbody").each(function(i,data) {
               $(data).find("tr").bind("click",function() {
                    $(this).find("td:eq(0)").find("input").attr("checked","checked");               
               });
          });
     },
     selectOkClks : function() {
  
          var selectObj = $("#selectChoices").find("input[type='radio']:checked");
             
          var userid =$("[name='checkBox']:checked").val();
          var username=$("[name='checkBox']:checked").attr("username");  
          if(selectObj.length>0){
                $("#nameInput").val(selectObj.val());
                $("#nameInputVal").val(userid);
                $("#userid").val(userid)
                $("#username").val(username);
                Eop.Dialog.close("workList");
               
          }else{
               alert("您还没选择工号");
          }
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClks : function() {
          
          var me = this;
          
          var options = {
                    url :'mblLoginAction!workList.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#workList").empty().append(result);
                         me.init();
                    },
                    error : function(e,b) {
                         alert("出错啦:(");
                    }
          };
          $("#choiceform1").ajaxSubmit(options);
          
     }
     };
$(function(){
     editChoiceLists.init();
});