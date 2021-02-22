var readonly ="yes";
$(function () {

	//查看商品信息隐藏不必要的
	$('#up').parent().hide();//删除上传按钮
	$('#cat_id').attr("disabled","disabled");//禁用下拉列表框
	$('#type_id').attr("disabled","disabled");
	//$('input[type="text" class="ipttxt" ]').attr("disabled","disabled");
	$('input[type="text"]').attr("disabled","disabled");
	$('input[type="checkbox"]').attr("disabled","disabled");
	$('input[type="radio"]').attr("disabled","disabled");
	$('#charge_type').attr("disabled","disabled");
	$('#type_id').val($('#goodsTypeValue').val());
	$('#cat_id').val($('#goodsCatIdValue').val());
	//删除确定按键
	$('.greenbtnbig').parent().parent().remove();
	//隐藏图片预览下的删除按键
	$('.deleteBtn').hide();
	$("a[calss='deleteBtn']").hide();
	$('input[type="button"]').hide();
	
	
});

