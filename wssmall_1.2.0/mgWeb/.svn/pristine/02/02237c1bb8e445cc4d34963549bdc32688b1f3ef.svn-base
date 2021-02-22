
(function($){
	$.fn.selectTree = function(json_url){
		var json ;
		var that = this;
		if (typeof json_url == 'string') {
			$.ajax({
				 type: "GET",
				 url: json_url,
				 data:   "ajax=yes",
				 dataType:'json',
				 success: function(result){
					 if(result.result==0){
						 json = result.data;
						 $(that).append(explore(json, 0));
					 }else{
						 alert("站点列表获取失败，请重试");
					 }
			     },
			     error:function(){
					alert("站点列表获取失败");
				 }
			}); 
		}else{
			$(this).append(explore(json_url, 0));
		}
		
		
		
		function explore(json, level){
			var s = "";
			var data=json;
			$.each(data, function(entryIndex,entry){
				s = s + "<option value='" + entry["siteid"]+"'>";
				for(i=0;i<level;i++){s = s +"&nbsp;&nbsp;";}
				s = s +entry["name"]+"</option>";
				if(entry["children"]){ s = s + explore(entry["children"], level + 1);}
			});
			return s;
		}
		
	}
	
})(jQuery);