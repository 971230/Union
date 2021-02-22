var action_code="";
$(function(){
	$("body").children("div").children("div").attr("class","panel-body-noheader layout-body");
	$.ajaxSetup({ cache: false }); 
	$("#org_list").treegrid({
       
        onBeforeExpand: function (node) {
        	if(node){
	            var mcode = node.party_id;
	            
	            var nodes = $("#org_list").treegrid('getChildren', mcode);
	          
	            $.ajax({
	            	url:ctx+"/core/admin/user/userAdmin!getChildrenOrg.do?ajax=yes",
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
        	if(node){
        		showOrgDetail(node);
        	}
        }
    });
});

/**
 * 选择标签页
 * @param flag
 */
function doTabSelect(flag){
	if("1" == flag){
		$("#org_tree_li").addClass("tab_li_select");
		$("#org_search_li").removeClass("tab_li_select");
		
		$("#div_org_tree").show();
		$("#div_org_search").hide();
	}else{
		$("#org_tree_li").removeClass("tab_li_select");
		$("#org_search_li").addClass("tab_li_select");
		
		$("#div_org_tree").hide();
		$("#div_org_search").show();
	}
}

/**
 * 展示组织下工号信息
 * @param orgInfo
 */
function showOrgDetail(orgInfo){
	
	var org_id  = orgInfo['party_id'];
    var dept_name =orgInfo['org_name'];
    var org_level = orgInfo['org_level'];
    var lan_id = orgInfo['lan_id'];
    var lan_name = orgInfo['lan_name'];
    
    var url = ctx+"/core/admin/user/userAdmin!orgUserList.do?org_id="+org_id;
    
    $(".detail #orgid", window.parent.document).val(org_id);	
    $(".detail #dep_name", window.parent.document).val(dept_name);
    $("#party_id",window.parent.document).val(org_id);
    $(".detail #lan_name",window.parent.document).val(lan_name);
    
    if(org_level==1||org_level==2){
    	$("#lan_id",window.parent.document).val(org_id);
    }else{
    	$("#lan_id",window.parent.document).val(lan_id);
    }
    
    $("#userListIframe", window.parent.document).attr("src",url);
}