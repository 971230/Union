var noticeAdd={
  init:function(){
      $("form.validate").validate();
      $('#content').ckeditor();
      noticeAdd.addSave();
  },
  addSave:function(){
        $("#noticeAddSaveBtn").unbind("click").bind("click",function(){
                  var url ="noticeAction!addSave.do?ajax=yes";
                
                  Cmp.ajaxSubmit('addNoticeForm', '', url, {}, noticeAdd.jsonBack, 'json');
        });
  },
  jsonBack:function(reply){
      if(reply.result=='0'){
	      alert(reply.message);
	      window.location.href="noticeAction!list.do";
       }
      if(reply.result=='1'){
         alert(reply.message);
       }
   }
};
$(function(){
    noticeAdd.init();
});