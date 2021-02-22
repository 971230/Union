var NocoLimit = {
	init : function(){
		var me = this;
		me.initClk();
		//修改禁用组织ID
		if($("#flag").val()=="true"){
			$("#org_name").attr("onclick","").attr("readonly","readonly");
		}
	},
	initClk:function(){
		var me = this;
		$("#add_btn").bind("click",function(){
			me.presentar();
		});
		$("#replenish_factor").bind("blur",function(){
			me.checkXishu($(this).val());
		});
		//普通号码上限
		$("#max_ordinary").bind("blur",function(){
			me.checkMaxOrdinary();
		});
		//靓号上限校验
		$("#max_lucky").bind("blur",function(){
			me.checkMaxLucky();
		});
	},
	checkMaxLucky : function(){
		var maxL=$.trim($("#max_lucky").val());
		var minL=$.trim($("#min_lucky").val());
		if(maxL==""||minL==""){
			alert("靓号上限值必须大于下限值，且不能为空！");
			return ;
		}else{
			if(maxL<=minL){
				alert("靓号上限值必须大于下限值，且不能为空！");
				return ;
			}
		}
		
	},
	checkMaxOrdinary : function(){
		var max=$.trim($("#max_ordinary").val());
		var min=$.trim($("#min_ordinary").val());
		if(max==""||min==""){
			alert("普通号码上限值必须大于下限值，且不能为空！");
			return ;
		}else{
			if(max<=min){
				alert("普通号码上限值必须大于下限值，且不能为空！");
				return ;
			}
		}
	},
	checkXishu : function(val){
		if(!/^0.0[0-9]{1}$/.test(val)){
			alert("“补货系数”输入错误,请输入0.01到0.09之间的小数");
			$("#replenish_factor").addClass("fail");
			$("#replenish_factor").focus();
			return ;
		}else{
			$("#replenish_factor").removeClass("fail");
		}
	},
	presentar : function(){
		var me = this;
		if($("form.validate").find("input[type='text'][value='']").length>0){
			alert("以上信息均不能为空！");
		}else{
			$("form.validate").validate();
			me.checkXishu($("#replenish_factor").val());
			me.checkMaxLucky();
			me.checkMaxOrdinary();
			Cmp.ajaxSubmit("theForm", '', "esconolimit!doAdds.do?ajax=yes", {}, me.jsonBack,"json");
		}
		
	},
	jsonBack : function(reply){
		if(reply.result==0){
			alert("操作成功");
			//MessageBox.show("操作成功", 3, 2000);
			window.location.href=reply.url;
		}else{
			alert("操作失败，请稍后再试！");
			//MessageBox.show("操作失败，请稍后再试！", 3, 2000);
		}
	}
};
$(function(){
	NocoLimit.init();
});