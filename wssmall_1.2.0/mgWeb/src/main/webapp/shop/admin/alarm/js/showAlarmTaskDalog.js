alarmTaskList = {
     init : function() {
          var me = this;
          me.initClk();
     },
 initClk : function() {
          var me = this;
          $("#searchAlarmTaskBot").bind("click",function() {
               me.searchBottonClk();
          });       
          $("#selectAlarmTaskOkBot").unbind("click").bind("click",function(){
               me.selectOkClk();
          });       
          //点击行选择该行相应单选框
          $("#selectAlarmTask").find("tbody").each(function(i,data) {
               $(data).find("tr").bind("click",function() {
                    $(this).find("td:eq(0)").find("input").attr("checked","checked");               
               });
          });
     },
     selectOkClk : function() {
          var selectObj = $("#selectAlarmTask").find("input[type='radio']:checked");
       	  var alarm_task_id=$("#selectAlarmTask").find("input[type='radio']:checked").val();
       	  var alarm_task_name=$("#selectAlarmTask").find("input[type='radio']:checked").attr("alarm_task_name");
       	  var alarm_task_type=$("#selectAlarmTask").find("input[type='radio']:checked").attr("alarm_task_type");
          if(selectObj.length>0){
          
          	   //$("[name='reset']").trigger("click");
               $("#applic_scene_name").val(alarm_task_name);
               $("#applic_scene_id").val(alarm_task_id);
               
               $("[name='elements_object']").attr("checked",false);
               if(alarm_task_type==1){
               		$("#reconciliation_alarm").show();
               		$("#task_alarm").hide();
               }else if(alarm_task_type==2){
               		$("#reconciliation_alarm").hide();
               		$("#task_alarm").show();
               }
               Eop.Dialog.close("showAlarmTaskDalog");
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
                    url :'alarm!search_applic_scene.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#showAlarmTaskDalog").empty().append(result);
                         me.init();
                    },
                    error : function(e) {
                         alert("出错啦:(");
                    }
          };
          $("#alarmTaskform").ajaxSubmit(options);
          
     }
     };
$(function(){
     alarmTaskList.init();
});