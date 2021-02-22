var noticeEdit={
  init:function(){
      $("form.validate").validate();
      $('#content').ckeditor();
      noticeEdit.editSave();
  },
  editSave:function(){
        $("#noticeEditvSaveBtn").unbind("click").bind("click",function(){
                  var url ="noticeAction!editSave.do?ajax=yes";
                  Cmp.ajaxSubmit('editNoticeForm', '', url, {}, noticeEdit.jsonBack, 'json');
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
    noticeEdit.init();
});