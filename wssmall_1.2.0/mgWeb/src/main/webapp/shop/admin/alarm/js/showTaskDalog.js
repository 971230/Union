taskList = {
     init : function() {
          var me = this;
          me.initClk();
     },
 initClk : function() {
          var me = this;
          $("#searchTaskBot").bind("click",function() {
               me.searchBottonClk();
          });       
          $("#selectTaskOkBot").unbind("click").bind("click",function(){
               me.selectOkClk();
          });       
          //点击行选择该行相应单选框
          $("#selectTask").find("tbody").each(function(i,data) {
               $(data).find("tr").bind("click",function() {
                    $(this).find("td:eq(0)").find("input").attr("checked","checked");               
               });
          });
     },
     selectOkClk : function() {
          var selectObj = $("#selectTask").find("input[type='radio']:checked");
       	  var task_id=$("#selectTask").find("input[type='radio']:checked").val();
       	  var task_name=$("#selectTask").find("input[type='radio']:checked").attr("task_name");
          if(selectObj.length>0){
               $("#task_name").val(task_name);
               $("#owner_task_id").val(task_id);
               Eop.Dialog.close("showTask");
          }else{
               alert("您还没选择任务");
          }
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClk : function() {
          
          var me = this;
          
          var options = {
                    url :'alarm!taskList.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#showTask").empty().append(result);
                         me.init();
                    },
                    error : function(e) {
                         alert("出错啦:(");
                    }
          };
          $("#taskform").ajaxSubmit(options);
          
     }
     };
$(function(){
     taskList.init();
});