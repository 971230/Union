var notice={
  init:function(){
     
      $(".del").bind("click",function(){
    	  
    	 if(!confirm("确认删除这条记录吗？")){
    		 return;
    	 }
    	  
         var notice_id =$(this).attr("notice_id");
         var url ="noticeAction!delete.do?ajax=yes";
          Cmp.excute('', url, {notice_id:notice_id}, notice.jsonBack, 'json');
      });
  },
  jsonBack:function(reply){
    if(reply.result=='0'){
      alert(reply.message);
      window.location.reload();
    }
    if(reply.result=='1'){
      alert(reply.message);
    }
  }
};

$(function(){
   notice.init();
});