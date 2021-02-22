
var timing = false;
var space_time;
var next_second;
function resetRfreshBtn(){
	$("input[name=refresh]").attr("class","comBtn");
	$("input[name=refresh]").removeAttr("disabled");
}

var IniAllStatus={
		init:function(){
			$("#next_time").hide();
			
			CommonLoad.loadJSP('show_station',ctx+'/shop/admin/queueCardMateManagerAction!getAllStatus.do',{ajax:"yes",includePage:"all_status"},false,null,true);
			
			$("input[name=refresh]").bind("click",function(){
				$(this).attr("disabled","disabled");
				$(this).attr("class","comBtnDisable");
				$("#show_station").html("");
				CommonLoad.loadJSP('show_station',ctx+'/shop/admin/queueCardMateManagerAction!getAllStatus.do',{ajax:"yes",includePage:"all_status"},false,null,true);
				setTimeout('resetRfreshBtn()',2000);
			});
			
			$("input[name=timing]").bind("click",function(){
				var checked = $(this).attr("checked");
				if(checked){
					var spacing = $("input[name=spacing_time]").val();
					spacing = spacing.replace(/[^0-9]*/g,"");
					if(''!=spacing){
						spacing = parseInt(spacing);
					}
					$("input[name=spacing_time]").val(spacing);
					if(null==spacing||undefined==spacing||''==spacing){
						alert("请输入间隔时间");
						$(this).attr("checked",false);
						return;
					}
					if(spacing<3){
						alert("间隔时间不能低于3秒");
						$(this).attr("checked",false);
						return;
					}
					timing = true;
					space_time = spacing;
					$("#next_time").show();
					$("#next_second_c").html(space_time);
					next_second = space_time-1;
					setTimeout('timing_refresh()',1000);
				}else{//停止定时任务
					timing = false;
					$("#next_time").hide();
				}
			});

			$("input[name=spacing_time]").keyup(function(){
				timing = false;
				$("#next_time").hide();
				$("input[name=timing]").attr("checked",false);
			});
			
		},
		test:function(){
			alert("aa");
		}
}

function timing_refresh(){
	if(timing){
		$("#next_second_c").html(next_second);
		if(next_second>0){
			next_second--;
			setTimeout('timing_refresh()',1000);
		}else{
			$("#show_station").html("");
			CommonLoad.loadJSP('show_station',ctx+'/shop/admin/queueCardMateManagerAction!getAllStatus.do',{ajax:"yes",includePage:"all_status"},false,null,true);
			next_second = space_time;
			timing_refresh();
		}
	}
}