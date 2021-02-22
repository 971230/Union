var GroupBuy={
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"activity_selector",modal:false,title:"选择已有活动", width:"600px"});
		$("#addRuleBtn").click(function(){
			self.addRule();
		});
		
		$("#ruletable .delete").click(function(){
			self.removeRule($(this));
		});

		function  appendActivity(activityList){
			self.appendActivity(activityList);
		}
		
		$("#checkAct").click(function(){
			ActivitySelector.open("activity_selector",appendActivity);
			$('#dlg_activity_selector').height(350);
		});
		$("#searchAct").click(function(){
			self.search(onConfirm);
		});
		$("#dis0").click(function(){
			$("#discount").attr("disabled",false);
			$("#price").attr("disabled",true);
		});

		$("#dis1").click(function(){
			$("#discount").attr("disabled",true);
			$("#price").attr("disabled",false);
		});
		
		$("#discount").change(function(){
			var price  = parseFloat( $("#price").val() ); 
			var discount = parseFloat( $("#discount").val() ); 
			if(price!=0 && discount!=0)
			$("#price").val( price*discount  );
		});
	},
	
	appendActivity:function(activityList){
		if(activityList.length>1) {alert("只能选择一个活动"); return ;}
		var activity = activityList[0];
		$("#id").val(activity.id);
		$("#name").val(activity.name);
		if($("#name").is("span")){
			$("#name").html(activity.name);
		}else if($("#name").is("input")){
			$("#name").val(activity.name);
		}
	}
	,
	addRule:function(){
		var self = this;
		var html = $( $("#temp_table").html() );
		var btn =html.find(".delete");
		$("#ruletable").append( html );
		 btn.click(function(){
			 self.removeRule($(this));
		 });
	},
	removeRule:function(btn){
		btn.parents("tr").remove();
	}
	
};
$(function(){
	GroupBuy.init();
});