


function clickPrintBtn(){
	var upUrl=ctx+"/shop/admin/orderPostModePrint!updateItemsPrintStatus.do?ajax=yes";
		$.ajax({
			
			url : upUrl,
			type : "POST",
			data:{delivery_id:$("#delivery_id").val(),itmesIds:$("#itmesIds").val(),post_type:$("#post_type").val(),order_is_his:$("#order_is_his").val()},
			dataType : 'json',
			success : function(data1) {
				if(data1.result!='0'){
					alert("记录打印出错啦:(");
				}else{
					 window.print();
					 window.opener=null;
					 window.close();
				}
			},
			error : function(e) {
				$.Loading.hide();
				alert("记录打印出错啦:(");
			}
		});

 }
