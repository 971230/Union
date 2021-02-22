var action_code="";
$(function(){
	
	 $.ajaxSetup({ cache: false }); 
	$("#org_list").treegrid({       
        onBeforeExpand: function (node) {
        	if(node){
	            var mcode = node.party_id;	
	            var nodes = $("#org_list").treegrid('getChildren', mcode);
	            $.ajax({
	            	url:"goodsOrg!getChildrenOrg.do?ajax=yes",
	            	dataType:"json",
	            	data:{"org_id" : mcode},
	            	success:function(reply){
	            		if (nodes.length > 0) {
                        	for (var i = nodes.length - 1; i >= 0; i--) {
                            	$("#org_list").treegrid('remove', nodes[i].code);
                        	}
                    	}
                    	$("#org_list").treegrid('append', {
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
        	action_code = "";
        	if(node){
        		$("#party_id").val(node['party_id']);
        		$("#org_code").val(node['org_code']);
        		$("#org_name").val(node['org_name']);
        		$("#parent_party_id").val(node['parent_party_id']);
        		$("#parent_org_name").val(node['parent_org_name']);
        		$("#org_type_id").val(node['org_type_id']);
        		$("#org_content").val(node['org_content']);
        		$("#lan_name").val(node['lan_name']);
        		$("#city_name").val(node['city_name']);
        		$("#status_cd").val(node['status_cd']);
        		$("#union_org_code").val(node['union_org_code']);
        	}
        }
    });
    
    $("#btn_add").click(function(){
    	action_code = "add";
    	var party_id = $("#party_id").val();
    	var org_name = $("#org_name").val();
    	if(party_id == null || party_id == ""){
    		$.messager.alert('系统提示', '请为新增的发布区域选择上级发布区域', 'info');
            return;
    	}
    	$("#parent_party_id").val(party_id);
    	$("#parent_org_name").val(org_name);
    	$("#party_id").val("");
        $("#org_name").val("");
        $("#org_type_id").val("");
        $("#org_content").val("");
        $("#union_org_code").val("");
        $("#org_code").val("");
        
        $("#party_id").removeAttr("disabled");
        $("#org_code").removeAttr("disabled");
        $("#org_name").removeAttr("disabled");
        $("#org_type_id").removeAttr("disabled");
        $("#org_content").removeAttr("disabled");
        $("#union_org_code").removeAttr("disabled");
    	$("#btn_confirm").show();
        $("#btn_cancel").show();
    });
    
     $("#btn_mod").click(function(){
    	action_code = "mod";
    	var party_id = $("#party_id").val();
    	if(party_id == null || party_id == ""){
    		$.messager.alert('系统提示', '请选择要修改的发布区域', 'info');
            return;
    	}
        
        $("#org_name").removeAttr("disabled");
      	$("#org_code").removeAttr("disabled");
        $("#org_content").removeAttr("disabled");
        $("#union_org_code").removeAttr("disabled");
        $("#btn_confirm").show();
        $("#btn_cancel").show();
    	
    });
    
     $("#btn_del").click(function(){
    	
    	var party_id = $("#party_id").val();
    	
    	if(party_id == null || party_id == ""){
    		$.messager.alert('系统提示', '请选择要删除的发布区域', 'info');
            return;
    	}
    	
         $.ajax({
	     	url:"goodsOrg!delOrg.do?ajax=yes",
	        dataType:"text",
	        data:{"party_id" : party_id},
	        success:function(reply){
	        	$.messager.alert('系统提示', reply, 'info');
	        	var d = new Date();
                $("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        		$("#org_list").treegrid('reload');
        	},
        	
        	error:function(){
        		$.messager.alert('系统提示', reply, 'info');
        		var d = new Date();
                $("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        		$("#org_list").treegrid('reload');
        	}
        	
	    });
    
    });
    
     $("#btn_confirm").click(function(){
    	var party_id = $("#party_id").val();
    	var parent_party_id = $("#parent_party_id").val();
    	var org_name = $("#org_name").val();
    	var org_type_id = $("#org_type_id").val();
		var union_org_code = $("#union_org_code").val();
  	    var org_content = $("#org_content").val();
  	    var org_code = $("#org_code").val();
  	 	
  	    if(org_name =="" || org_name == null){
  	    	$.messager.alert('系统提示', '请为新增的发布区域填写发布区域名称', 'info');
            return;
  	    }
  	    
  	    if(action_code == "add"){
  	    	 $.ajax({
	            	url:"goodsOrg!addOrg.do?ajax=yes",
	            	type:'post',
	            	dataType:"json",
	            	data:{"party_id":party_id, "parent_party_id":parent_party_id, "org_code":org_code,
	            		  "org_name":org_name, "org_type_id":org_type_id, "union_org_code":union_org_code, "org_content":org_content},
	            	success:function(reply){
	            		$.messager.alert('系统提示', reply.message, 'info');
	            		var d = new Date();
                		$("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        				$("#org_list").treegrid('reload');
	            	},
	            	
	            	error:function(reply){
	            		$.messager.alert('系统提示', reply.message, 'info');
	            		var d = new Date();
                		$("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        				$("#org_list").treegrid('reload');
	            	}
	            });
  	    }else{
  	    	 $.ajax({
	            	url:"goodsOrg!modOrg.do?ajax=yes",
	            	type:'post',
	            	dataType:"json",
	            	data:{"party_id":party_id, "parent_party_id":parent_party_id, "org_code": org_code,
	            		  "org_name":org_name, "org_type_id":org_type_id, "union_org_code":union_org_code, "org_content":org_content},
	            	success:function(reply){
	            		$.messager.alert('系统提示', reply.message, 'info');
	            		var d = new Date();
                		$("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        				$("#org_list").treegrid('reload');
	            	},
	            	
	            	error:function(reply){
	            		$.messager.alert('系统提示', reply.message, 'info');
	            		var d = new Date();
                		$("#org_list").treegrid('options').url = 'goodsOrg!getCurrOrg.do?ajax=yes&random='+d.getTime();
        				$("#org_list").treegrid('reload');
	            	}
	            });
  	    }
  	    
  	     $("#btn_confirm").hide();
         $("#btn_cancel").hide();
  	    
         action_code = "";

  	   
  	 });
    
     $("#btn_cancel").click(function(){
    	action_code = "";
    	var node = $("#org_list").treegrid('getSelected');
    	if(node){
    		$("#party_id").attr({disabled:"true"});
        	$("#org_code").attr({disabled:"true"});
        	$("#org_name").attr({disabled:"true"});
        	$("#org_content").attr({disabled:"true"});
        	$("#union_org_code").attr({disabled:"true"});
        	
        	$("#party_id").val(node['party_id']);
        	$("#org_code").val(node['org_code']);
        	$("#org_name").val(node['org_name']);
        	$("#parent_party_id").val(node['parent_party_id']);
        	$("#parent_org_name").val(node['parent_org_name']);
        	$("#org_type_id").val(node['org_type_id']);
        	$("#org_content").val(node['org_content']);
        	$("#union_org_code").val(node['union_org_code']);
        	$("#btn_confirm").hide();
            $("#btn_cancel").hide();
    	}
    	
    	
    });
   
});
