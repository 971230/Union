var serviceAppss = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id:"propertyEdit1111",modal:false,title:"编辑商品属性",height:"300px",width:"450px"});
    $("a[name='edit']").live("click",function(){
    	var sid = $(this).attr("attr_spec_id");
   		var id = $(this).attr("field_attr_id");
        self.edit(sid,id);
    });
    $("a[name='delProperty']").live("click",function(){
       var field_attr_id = $(this).attr("field_attr_id");
       self.del(field_attr_id);
    });
  },
   edit:function (sid,id){
			$("#propertyEdit1111").load(ctx+"/shop/admin/goodsPropertyAction!edit.do?ajax=yes&field_attr_id="+id+"&attr_spec_id="+sid,function(){
			
			$("#butSaves").click(function(){
				 url =ctx+"/shop/admin/goodsPropertyAction!editSave.do?ajax=yes&field_attr_id="+id+"&attr_spec_id="+sid;
				 Cmp.ajaxSubmit('editForm', '', url, {}, function(reply){
				 
        if(reply.result==0){
          alert(reply.message);
        }  
        if(reply.result==1){
          alert("操作成功!");
          Eop.Dialog.close("propertyEdit1111");
          var goods_id=$("input[name='goods_id_e']").val();
          var url = ctx+"/shop/admin/goodsPropertyAction!List.do?ajax=yes&goods_id="+goods_id;
          $("#contractNodes").empty().load(url);
        }
   
				 }, 'json');
			});
		});
		Eop.Dialog.open("propertyEdit1111");
	},
  del:function(field_attr_id){
     var self = this;
     if(!confirm("是否确定要删除?")){
        return ;
     }  
	   url ="goodsPropertyAction!delete.do?ajax=yes&field_attr_id="+field_attr_id;
       Cmp.excute('', url, {}, self.delJsonBack, 'json');
       
         
  },
   delJsonBack:function(reply){
        if(reply.result==0){
          alert(reply.message);
        }  
        if(reply.result==1){
          var goods_id=$("input[name='goods_id_e']").val();
          var url = ctx+"/shop/admin/goodsPropertyAction!List.do?ajax=yes&goods_id="+goods_id;
          $("#contractNodes").empty().load(url);
        }
   }
};
$(function(){
  serviceAppss.init();
});
   