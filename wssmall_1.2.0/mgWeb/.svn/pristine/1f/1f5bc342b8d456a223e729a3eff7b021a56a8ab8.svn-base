$(document).ready(function(){
    Eop.Dialog.init({id:"balanceApplyDlg", modal:false, title:"结算申请", width:"700px"});
	$(".itemRecount").bind("click",function(){
		var detail_id = $(this).attr("attr");
		alert("detail_id:"+detail_id);
	});
	
	$(".itemEndApply").bind("click",function(){
		var select_item = $("#comm_itme_form [type='checkbox']:checked");
		var list_id = $("#listId").val();
		
		if(select_item.length==0){
		 alert("请选择要结算的单");
		  return false;
		}
		var apply_type ='00B';
		var url = ctx + "/shop/admin/commissionApply!balanceApplyByGoodsId.do?ajax=yes";
		var detailIds = [];
		$("#comm_itme_form [type='checkbox']:checked").each(function(){
		  detailIds.push($(this).val());
		});
		var detailIdStr =  detailIds.join(",");
		 $("#balanceApplyDlg").load(url,{list_id:list_id,apply_type:apply_type,detailIdStr:detailIdStr},function(){
         }); 
		Eop.Dialog.open("balanceApplyDlg");
		//comm_itme_form
		/*var ids = "";
		select_item.each(function(){
			var id = $(this).attr("attr");
			ids += id + "_"
		});
		if(ids != ""){
			ids = ids.substring(0,ids.length-1);
		}
		alert("selectDetailIds:"+ids);*/
		
	});
});