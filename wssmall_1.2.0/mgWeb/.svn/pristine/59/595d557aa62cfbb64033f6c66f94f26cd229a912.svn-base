//新增模板
var Add_tpl={
	url:"",
	dlg_id:"add_tpl",
	page_close:function(){
		Eop.Dialog.close(this.dlg_id);
	},
	init:function(){
		//初始化对话框
		Eop.Dialog.init({id:this.dlg_id,modal:false,title:'新增模板',width:"700px"});
		$("#btn_add").bind("click",function(){
			//增加之前先判断是否有选中树结点
			var catalogueId=$("#catalogueId").val();
			if(catalogueId==null||catalogueId==""){
				alert("请选择菜单树结点信息!");
				return;
			}
			//跳转到配置页面
			Eop.Dialog.open(this.dlg_id);
			$(".title").text('新增模板');
			url=ctx+"/shop/admin/ordTplAction!toTplConfig.do?ajax=yes&ordTplDTO.flag=add&ordTplDTO.catalogue_id="+catalogueId;
			$("#add_tpl").load(url);
			$("[name='ordTplChk']:checked").attr("checked",false);
		});
		$("#btn_mod").bind("click",function(){
			//获取选中的模板
			var len = $("[name='ordTplChk']:checked").length;
			if(len!=1){
				alert("请选择1条模板修改");
				return;
			}
			var tpl_id=$("[name='ordTplChk']:checked").val();
			Eop.Dialog.open(this.dlg_id);
			$(".title").text('修改模板');
			url=ctx+"/shop/admin/ordTplAction!toTplConfig.do?ajax=yes&ordTplDTO.flag=update&ordTplDTO.order_template_id="+tpl_id;
			$("#add_tpl").load(url);
			$("[name='ordTplChk']:checked").attr("checked",false);
		});
		$("#btn_del").bind("click",function(){
			//获取选中的模板
			var len = $("[name='ordTplChk']:checked").length;
			if(len!=1){
				alert("请选择1条模板删除");
				return;
			}
			if(confirm("确定要删除该模板?")){
				var tpl_id=$("[name='ordTplChk']:checked").val();
				url=ctx+"/shop/admin/ordTplAction!delTel.do?ordTplDTO.order_template_id="+tpl_id+"&ajax=yes";
				Cmp.excute('', url, {}, function(data){
					alert(data.msg);
					Add_tpl.page_close();
					location.reload();
					$("[name='ordTplChk']:checked").attr("checked",false);
				},"json");
			}
		});
		$("#tplFrom table tr").live("click",function(){
			var v = $(this).find('input[name="tempInput"]').attr('template_id');
			//隐藏变量
			if(v!=null&&v!=""){
				$(this).find("#hiddenordTpl").val(v);
				//展示数
				v=1;
			    getTemplateTree(v);
			}
		});
	}
};
$(function(){
	Add_tpl.init();
});