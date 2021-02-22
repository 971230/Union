var CloudRandomList = {
	cloud_tansfer:function(){ //云卡调拨
		var self= this;
		var url =ctx+"/shop/admin/cloud!transfer.do?ajax=yes&orderid="+$("#orderid").val()+"&from_page="+$("#from_page").val();
		
		
		if($("[attr='p_begin_nbr']:visible,[attr='p_end_nbr']:visible").length>0)
		{
			if(!$("[attr='p_begin_nbr']").val()){
				alert("新起始号码不能为空！");
				return;
			}
			
			if(!$("[attr='p_end_nbr']").val()){
				alert("新结束号码不能为空！");
				return;
			}
			
		}
		
		Cmp.ajaxSubmit('cloud_query_form','',url,{},self.jsonBack,'json');
	},
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			try{Eop.Dialog.close("order_w_dialog");}catch(e){}
			window.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	
	init:function(){
		$("a[id='cloud_tansfer_a']").bind("click",function(){
			CloudRandomList.cloud_tansfer();
		})
	
		var self = this;
		Eop.Dialog.init({id:"cloud_dialog",modal:false,title:"云卡可用库存",width:"1000px"});
		$("[name='cloudStockView']").unbind("click").bind("click",function(){
			
			
			var url =ctx+"/shop/admin/cloud!list_avaible_forSearch.do?ajax=yes&goods_id=" 
						+ goods_id + "&lan_id=" + outLanId ;
			sel_type = $(this).attr("sel_type");
			
			//标志是从开始号码还是结束号码进入页面
			$("#hidden_sel_type").val(sel_type);
			
			$("#cloud_dialog").load(url,function(){
				Eop.Dialog.open("cloud_dialog");
				Eop.Dialog.close("cloud_dialog");
				Eop.Dialog.open("cloud_dialog");
				self.cloud_page_init();
				
			}); 
			
			
		});
		
		$("[attr='p_begin_nbr'],[attr='p_end_nbr']").bind("blur",function(){
			var begin_nbr =$("[attr='p_begin_nbr']").val();
			var end_nbr = $("[attr='p_end_nbr']").val();
			if (begin_nbr && begin_nbr.length == 11 &&	end_nbr && end_nbr.length == 11 ){
				//调用后台查询可用号码
					if (end_nbr < begin_nbr) {
						return;
					}
				
			 		var url = ctx+"/shop/admin/cloud!listCount.do?ajax=yes&begin_nbr="+begin_nbr+"&end_nbr="+end_nbr+"&goods_id="+goods_id;
					
					Cmp.ajaxSubmit('acc_number_form','',url,{},function(result){
						$("#order_goods_count_span").html("说明：选择号码段区间可用库存数为"+result['ableCloud_count']+"（个）");
						$("[attr='goods_num']").val(result['ableCloud_count']);
					},'json');
			}
		});
	},
	cloud_page_init:function(){ //充值卡页面inti操作
		var self =this;
		var sel_type = $("#hidden_sel_type").val();
		$("#cloud_forSearch_query_form .comBtn").unbind("click").bind("click",function(){
			var url =ctx+"/shop/admin/cloud!list_avaible_forSearch.do?ajax=yes&goods_id="+goods_id;
			Cmp.ajaxSubmit('cloud_forSearch_query_form','cloud_dialog',url,{},CloudRandomList.cloud_page_init); // 之前页面这里会有问题 直接把result当成了sel_type 
			return false;
		});
	
		$("#cloudInsureBtn").unbind("click").bind("click",function(){
			var selectNum = $("input[name='cloud_checkbox']:checked").attr("phone_num");
			if(sel_type =="begin"){
				$("[attr='p_begin_nbr']").val(selectNum);
				var end_nbr =$("[attr='p_end_nbr']").val();
				if (end_nbr != "" && end_nbr.length == 11) {
					if (selectNum.substring(0,3) != end_nbr.substring(0,3)) {	
						alert("开始号码 " + selectNum + "结束号码 " +  end_nbr + " 与 不是同个号段，请检查！");
						return false;
					}
					if (selectNum > end_nbr) {	
						alert("开始号码 " +  selectNum + " 大于结束号码 " + end_nbr + " ，请检查！");
						return false;
					}
				}
				$("[attr='p_begin_nbr']").trigger("blur");
			}else{
				$("[attr='p_end_nbr']").val(selectNum);
				var begin_nbr =$("[attr='p_begin_nbr']").val();
				if (selectNum.substring(0,3) != begin_nbr.substring(0,3)) {	
					alert("结束号码 " +  selectNum + " 与开始号码 " + begin_nbr + " 不是同个号段，请检查！");
					return false;
				}
				if (selectNum < begin_nbr) {	
					alert("结束号码 " +  selectNum + " 小于开始号码 " + begin_nbr + " ，请检查！");
					return false;
				}
				$("[attr='p_end_nbr']").trigger("blur");
			}
			Eop.Dialog.close("cloud_dialog");
			return false;
		});
		
	}
}
$(function() {
	CloudRandomList.init();
});