var action_code="";

$(function(){
	
	 $.ajaxSetup({ cache: false }); 
	$("#region_list").treegrid({
       
        onBeforeExpand: function (node) {
        	if(node){
	            var mcode = node.region_id;
	            
	            var nodes = $("#region_list").treegrid('getChildren', mcode);
	            var region_level = node.region_level;
	            $.ajax({
	            	url:"commonRegionAction!getChildrenRegion.do?ajax=yes",
	            	dataType:"json",
	            	data:{"region_id" : mcode,"region_level":region_level},
	            	success:function(reply){
	            		if (nodes.length > 0) {
                        	for (var i = nodes.length - 1; i >= 0; i--) {
                            	$("#region_list").treegrid('remove', nodes[i].code);
                        	}
                    	}
                    	$("#region_list").treegrid('append', {
                        	parent: mcode,
                        	data: reply
                    	});
	            	},
	            	error:function(){
	            	
	            	}
	            });
	            
        	}
        },
        onClickRow:function(node){
        	$("#btn_add").attr("clicked","");
    		$("#btn_mod").attr("clicked","");
        	action_code = "";
        	if(node){
        		var region_level = node['region_level'];
        		var region_level_desc = regionDetail.regionLevelDesc(region_level);
        		
        		$("#region_id").val(node['region_id']);
        		$("#region_code").val(node['region_code']);
        		$("#region_name").val(node['region_name']);
        		$("#parent_region_id").val(node['parent_region_id']);
        		$("#parent_region_name").val(node['parent_region_name']);
        		$("#region_level_desc").val(region_level_desc);
        		$("#region_desc").val(node['region_desc']);
        		$("#region_level").val(node['region_level']);
        	}
        }
    });
    
    $("#btn_add").click(function(){
    	
    	var clickedStr = $("#btn_add").attr("clicked");
    	if(clickedStr=="true"){
    		alert("请先保存当前的操作或者取消当前操作");
    		return false;
    	}
    	$("#btn_add").attr("clicked","true");
    	
    	action_code = "add";
    	var region_id = $("#region_id").val();
    	var region_level = $("#region_level").val();
    	
    	if(region_id == null || region_id == ""){
    		alert("请为新增的区域选择上级区域");
            return;
    	}
    	
    	if(region_level=='5'){
    		alert("已经是最后一级,不能添加再添加子区域");
            return;
    	}
    	var region_name = $("#region_name").val();
    	
    	region_level = parseInt(region_level)+1;
    	
    	var region_level_desc = regionDetail.regionLevelDesc(region_level);
    	
    	$("#parent_region_id").val(region_id);
    	$("#parent_region_name").val(region_name);
    	$("#region_level").val(region_level);
        $("#region_level_desc").val(region_level_desc);
    	$("#region_id").val("");
        $("#region_name").val("");
        $("#region_code").val("");
        $("#region_desc").val("");
        
        
        
        $("#region_id").removeAttr("disabled");
        $("#region_code").removeAttr("disabled");
        $("#region_name").removeAttr("disabled");
        $("#region_desc").removeAttr("disabled");
        
    	$("#searchformDiv").show();
    });
    
     $("#btn_mod").click(function(){
    	var clickedStr = $("#btn_mod").attr("clicked");
     	if(clickedStr=="true"){
     		alert("请先保存当前的操作或者取消当前操作");
     		return false;
     	}
     	$("#btn_mod").attr("clicked","true");
     	
    	action_code = "mod";
    	var region_id = $("#region_id").val();
    	if(region_id == null || region_id == ""){
    		alert("请选择要修改的区");
            return;
    	}
        $("#region_name").removeAttr("disabled");
      	$("#region_code").removeAttr("disabled");
        $("#region_desc").removeAttr("disabled");
        $("#region_id").removeAttr("disabled");
        $("#searchformDiv").show();
    });
    
     $("#btn_del").click(function(){
    	var region_id = $("#region_id").val();
    	if(region_id == null || region_id == ""){
    		alert("请选择要删除的区域");
            return;
    	}
      if(window.confirm('确认要删除此区域？')){
         $.ajax({
	     	url:"commonRegionAction!delRegion.do?ajax=yes",
	        dataType:"text",
	        data:{"region_id" : region_id},
	        success:function(reply){
	        	alert(reply);
	        	var d = new Date();
                $("#region_list").treegrid('options').url = 'commonRegionAction!getRootRegion.do?ajax=yes&random='+d.getTime();
        		$("#region_list").treegrid('reload');
        	},
        	error:function(){
        		var d = new Date();
                $("#region_list").treegrid('options').url = 'commonRegionAction!getRootRegion.do?ajax=yes&random='+d.getTime();
        		$("#region_list").treegrid('reload');
        	}
	    });
      }
    });
    
     $("#btn_confirm").click(function(){
    	$("#btn_add").attr("clicked","");
 		$("#btn_mod").attr("clicked","");
    	var node = $('#region_list').treegrid('getSelected');
    	var region_id = $("#region_id").val();
    	var parent_region_id = $("#parent_region_id").val();
    	var region_name = $("#region_name").val();
    	var region_level = $("#region_level").val();
		var region_code = $("#region_code").val();
  	    var region_desc = $("#region_desc").val();
  	    
  	    var content = "";
  	    if(region_desc != null && region_desc != ""){
  	    	region_desc = encodeURIComponent(region_desc);
  	    }
  	    if(region_name != null && region_name != ""){
  		region_name = encodeURIComponent(region_name);
	    }
  	  
  	    if(region_name =="" || region_name == null){
  	    	alert("请为新增的区域填写区域名称");
            return;
  	    }
  	   if(region_code =="" || region_code == null){
  	    	alert("请为新增的区域填写区域编码", 'info');
            return;
  	    }
  	  
	   if (/^[a-zA-Z0-9]{1,20}$/.test(region_code) == false) {

				alert("区域编码必须是长度小于20位的数字或者字母组成");
				return ;
	   }
	
  	    if(action_code == "add"){
  	    	 $.ajax({
	            	url:"commonRegionAction!addRegion.do?ajax=yes",
	            	dataType:"text",
	            	data:{region_id:region_id, region_name:region_name,region_code:region_code,
	            			 parent_region_id:parent_region_id,region_desc:region_desc,region_level:region_level},
	            	success:function(reply){
	            		if(reply=='操作成功'){
	            			$("#region_id").val("");
	            		}
	            		alert(reply);
	            		var d = new Date();
	            		$("#region_list").treegrid('options').url = 'commonRegionAction!getRootRegion.do?ajax=yes&random='+d.getTime();
        				$("#region_list").treegrid('reload');
        				/*if (node){
        					$('#region_list').treegrid('expandAll', node.region);
        				} else {
        					$('#region_list').treegrid('expandAll');
        				}*/
	            	},
	            	error:function(){
	            		var d = new Date();
        				$("#region_list").treegrid('reload');
	            	}
	            });
  	    }else{
  	    	 $.ajax({
	            	url:"commonRegionAction!modRegion.do?ajax=yes",
	            	dataType:"text",
	            	data:{region_id:region_id,region_name:region_name,region_desc:region_desc,region_code:region_code},
	            	success:function(reply){
	            		if(reply=='操作成功'){
	            			$("#region_id").val("");
	            		}
	            		alert(reply);
	            		var d = new Date();
                		$("#region_list").treegrid('options').url = 'commonRegionAction!getRootRegion.do?ajax=yes&random='+d.getTime();
        				$("#region_list").treegrid('reload');
        				/*if (node){
        					$('#region_list').treegrid('expandAll', node.region_id);
        				} else {
        					$('#region_list').treegrid('expandAll');
        				}*/
	            	},
	            	error:function(){
	            		var d = new Date();
                		$("#region_list").treegrid('options').url = 'commonRegionAction!getRootRegion.do?ajax=yes&random='+d.getTime();
        				$("#region_list").treegrid('reload');
	            	}
	            });
  	    }
  	   $("#searchformDiv").hide();
         action_code = "";
  	 });
    
     $("#btn_cancel").click(function(){
    	 $("#btn_mod").attr("clicked","false");
    	 $("#btn_add").attr("clicked","false");
    	action_code = "";
    	var node = $("#region_list").treegrid('getSelected');
    	if(node){
    		$("#region_id").attr({disabled:"true"});
        	$("#region_code").attr({disabled:"true"});
        	$("#region_name").attr({disabled:"true"});
        	$("#region_desc").attr({disabled:"true"});
        	$("#region_level_desc").attr({disabled:"true"});
        	
        	
        	$("#region_id").val(node['region_id']);
        	$("#region_code").val(node['region_code']);
        	$("#region_name").val(node['region_name']);
        	$("#parent_region_id").val(node['parent_region_id']);
        	$("#parent_region_name").val(node['parent_region_name']);
        	$("#region_level").val(node['region_level']);
        	$("#region_desc").val(node['region_desc']);
        	var region_level_desc = regionDetail.regionLevelDesc(node["region_level"]);
        	$("#region_level_desc").val(region_level_desc);
        	
        	$("#searchformDiv").hide();
    	}
    });
   
});

var regionDetail={
		  regionLevelDesc:function(region_level){
			  var region_level_desc ="";
			  if(region_level==2){
					region_level_desc="省";
				}
			    if(region_level==3){
					region_level_desc="本地网";
				}
			    if(region_level==4){
					region_level_desc="营业区";
				}
			    if(region_level==5){
					region_level_desc="分局";
				}
			    return region_level_desc;
		  }		
};
