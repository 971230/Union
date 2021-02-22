$(function(){
	$("#lan_code").change(function(){
		var lan_code  = $(this).val();
		var url = ctx + "/shop/admin/task!getRegion.do?ajax=yes&lan_code="+lan_code;
		$.ajax({  
	              url :url,  //后台处理程序  
	              type:"post",    //数据发送方式  
	              async:false,  
	              dataType:"json",   //接受数据格式             
	              error: function(){  
	                  alert("服务器没有返回数据，可能服务器忙，请重试");  
	              },  
	              success: function(json){  
	                 var mySelect = document.getElementById("city_code");
	                 mySelect.options.length=0;
	                 var opp = new Option("-请选择-","");
	                 	mySelect.add(opp);
	                 for(var xk=0;xk<json.length;xk++){
	                 	var obj = json[xk];
	                 	obj.region_name;
	                 	var opp = new Option(obj.region_name,obj.region_id);
	                 	mySelect.add(opp);
	                 }
	              }   
	          });                     
	});
});