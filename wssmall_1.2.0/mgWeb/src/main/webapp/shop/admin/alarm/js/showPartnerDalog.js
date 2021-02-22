partnerList = {
     init : function() {
          var me = this;
          me.initClk();
     },
 initClk : function() {
          var me = this;
          $("#searchPartnerBot").bind("click",function() {
               me.searchBottonClk();
          });       
          $("#selectPartnerOkBot").unbind("click").bind("click",function(){
               me.selectOkClk();
          });       
          //点击行选择该行相应单选框
          $("#selectPartner").find("tbody").each(function(i,data) {
               $(data).find("tr").bind("click",function() {
                    $(this).find("td:eq(0)").find("input").attr("checked","checked");               
               });
          });
     },
     selectOkClk : function() {
          var selectObj = $("#selectPartner").find("input[type='radio']:checked");
       	  var partner_name=$("#selectPartner").find("input[type='radio']:checked").val();
       	  var partner_id=$("#selectPartner").find("input[type='radio']:checked").attr("partner_id");
          if(selectObj.length>0){
               $("#partner_name").val(partner_name);
               $("#partner_id").val(partner_id);
               Eop.Dialog.close("showPartner");
          }else{
               alert("您还没选择分销商");
          }
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClk : function() {
          
          var me = this;
          
          var options = {
                    url :'alarm!partnerList.do?ajax=yes',
                    type : "POST",
                    dataType : 'html',            
                    success : function(result) {  
                         $("#showPartner").empty().append(result);
                         me.init();
                    },
                    error : function(e) {
                         alert("出错啦:(");
                    }
          };
          $("#partnerform").ajaxSubmit(options);
          
     }
     };
$(function(){
     partnerList.init();
});