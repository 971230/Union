var noticeEdit={
  init:function(){
      $("form.validate").validate();
      $('#content').ckeditor();
      noticeEdit.editSave();
      
  },
  editSave:function(){
        $("#noticeEditvSaveBtn").unbind("click").bind("click",function(){
                  var url ="ottNotice!addSave.do?ajax=yes";
                  Cmp.ajaxSubmit('editNoticeForm', '', url, {}, noticeEdit.jsonBack, 'json');
        });
  },
  jsonBack:function(reply){
      if(reply.result=='0'){
	      alert(reply.message);
	      window.location.href="ottNotice!listOttNotice.do";
       }
      if(reply.result=='1'){
         alert(reply.message);
       }
   }
};
$(function(){
    noticeEdit.init();
    Eop.Dialog.init({id:"cms_modual_list", modal:false, title:"选择模块", width:"700px"});
});