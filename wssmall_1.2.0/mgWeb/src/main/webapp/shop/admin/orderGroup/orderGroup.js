var orderGroup={
  init:function(){
    var self = this;
    Eop.Dialog.init({id:"addOrderGroupDlg",modal:false,title:"添加分组",width:'650px'});
    this.bindAdd();
    this.binddel();
  },
  bindAdd:function(){
     $("#addOrderGroupBtn").unbind("click").bind("click",function(){
          $("#addOrderGroupDlg").load(app_path+"/shop/admin/orderGroupAction!addOrderGroupDef.do?ajax=yes",function(){
                $("#orderGroupAddSaveBtn").unbind("click").bind("click",function(){
                        var group_type = $("#group_type").val();
                           if(group_type=='0'){
					         alert("请选择分组类型");
					          return false;
					        }
                       var url =app_path + "/shop/admin/orderGroupAction!saveOrderGroupDef.do?ajax=yes";
                       Cmp.ajaxSubmit('addOrderGroupForm', '', url, {}, orderGroup.addJsonBack, 'json');
                });
          });
           Eop.Dialog.open("addOrderGroupDlg");
     });
   
  },
  binddel:function(){
    $("#del").click(function(){
       if(window.confirm("确认要删除该条记录吗?")){
         var group_id = $(this).attr("group_id");
         var url =app_path+"/shop/admin/orderGroupAction!delOrderGroupDef.do?ajax=yes";
         Cmp.excute('', url, {group_id:group_id}, orderGroup.delJsonBack, 'json');
         }else{
        	 return false;
         }
    });
  },
  addJsonBack:function(reply){
    if(reply.result =='0'){
      alert("操作成功!");
      Eop.Dialog.close("addOrderGroupDlg");
      window.location.reload();
    }
    if(reply.result=='1'){
       alert(reply.message);
       //Eop.Dialog.close("addOrderGroupDlg");
    }
  },
  delJsonBack:function(reply){
    if(reply.result =='0'){
      alert("操作成功!");
      window.location.reload();
    }
    if(reply.result=='1'){
       alert(reply.message);
    }
  }
};

$(function(){
  orderGroup.init();
});
