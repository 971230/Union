var action_code="";
$(function(){
	$("body").children("div").children("div").attr("class","panel-body-noheader layout-body");
	$.ajaxSetup({ cache: false }); 
	$("#org_list").treegrid({
       
        onBeforeExpand: function (node) {
        	if(node){
	            var par_id = node.party_id;
	            var mcode = par_id.split("_")[0];
	            var url = null;
	            var party_state = node.party_state;
	            
	         	if(mcode>=1 && mcode<=8){
		         	var nodes = $("#org_list").treegrid('getChildren', par_id);
		            $.ajax({
		            	url: ctx + "/shop/admin/task!getOrgRoot.do?ajax=yes",
		            	dataType:"json",
		            	data:{"org_id" : mcode, "party_state":party_state},
		            	success:function(reply){
		            		if (nodes.length > 0) {
	                        	for (var i = nodes.length - 1; i >= 0; i--) {
	                        		var child_node = nodes[i];
	                        		$("#org_list").treegrid("remove", child_node['party_id']);
	                        	}
	                    	}
	                    	$("#org_list").treegrid('append', {
	                        	parent: par_id,
	                        	data: reply
	                    	});
		            	},
		            	
		            	error:function(){
		            	
		            	}
		            }); 
	         	}else{
		            var parent_id = null;
		            var nodes = $("#org_list").treegrid('getChildren', par_id);
		            var parent = $("#org_list").treegrid('getParent', par_id);
		            if(parent){
		            	parent_id = parent.party_id.split("_")[0];
		            }
		            $.ajax({
		            	url: ctx + "/shop/admin/task!getChildrenOrg.do?ajax=yes",
		            	dataType:"json",
		            	data:{"org_id" : mcode,"parent_id":parent_id, "party_state":party_state},
		            	success:function(reply){
		            		if (nodes.length > 0) {
	                        	for (var i = nodes.length - 1; i >= 0; i--) {
	                            	var child_node = nodes[i];
	                        		$("#org_list").treegrid("remove", child_node['party_id']);
	                        	}
	                    	}
	                    	$("#org_list").treegrid('append', {
	                        	parent: par_id,
	                        	data: reply
	                    	});
		            	},
		            	
		            	error:function(){
		            	
		            	}
		            });
	            
	            } 
	            
        	}
        },
        onClickRow:function(node){
        	if(node){
        		var party_state = node['party_state'];
        		var org_id  = node['party_id'].split("_")[0];
        		var node_level =$("#org_list").treegrid("getLevel",node['party_id']);
        		var finished = "0";
        		var state = "";
        		var lan_code = node.lan_id;
        		var city_code = node.region_id;
        		
        		if(party_state != null && party_state != ""){
        			if("010" == party_state){
        				finished = "1";
        			}
        			else if("009" == party_state){
        				finished = "0";
        			}else{
        				state = party_state;
        			}
        		}else{
        			state = "";
        		}
        		if(lan_code == city_code){
        			city_code = "";
        		}
        		
                var url = ctx + "/shop/admin/task!searchTaskList.do?ajax=yes";
                
                $("#taskPanel", window.parent.document).empty();
		        $("#taskPanel", window.parent.document).load(url,{node_level:node_level,org_id:org_id,lan_code:lan_code,city_code:city_code,
		          										task_type:"",task_name:"",create_time:"",state:state,finished:finished, party_state:party_state},function(){
		        }); 
		    }
        }
    });
});

