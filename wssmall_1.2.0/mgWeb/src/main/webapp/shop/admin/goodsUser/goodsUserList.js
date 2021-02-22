var goodsUserList ={
  init:function(){
    var self = this;
    self.editSave();
    self.del();
  },
  editSave:function(){
     $("#editGoodsUserBtn").click(function(){
        var url =ctx+"/shop/admin/goodsUserAction!saveEditGoodsUser.do?ajax=yes";
        Cmp.ajaxSubmit('editGoodsUserForm', '', url, {}, goodsUserList.JsonBack, 'json');
     });
     
  },
  
  del:function(){
    $("a[name='del']").live("click",function(){
     if(window.confirm("确定要删除这条记录吗?")){
     var goods_id = $(this).attr("goods_id");
     var user_type = $(this).attr("user_type");
     var service_code = $(this).attr("service_code");
     var url =ctx+"/shop/admin/goodsUserAction!delGoodsUser.do?ajax=yes";
     Cmp.excute('', url, {goods_id:goods_id,user_type:user_type,service_code:service_code}, goodsUserList.JsonBack, 'json');
     }
    });
  },
  JsonBack:function(reply){
     if(reply.result=='0'){
       alert("操作成功");
       window.location.href =ctx+"/shop/admin/goodsUserAction!goodsUserList.do";
     }
     if(reply.result=='1'){
      alert(reply.message);
     }
  }
};

$(function(){
  goodsUserList.init();
});