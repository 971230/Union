
var action_code="";
var lanRegionChanger = new LanRegionChanger();
var orgChannelChanger = new OrgChannelChanger();

initOrgType();

$("#channel_type").change(function(){
	var channel_type = $("#channel_type").val();
	
	orgChannelChanger.doOrgChannelChange(channel_type);
});

$("#lan_sel").change(function(){
	var lanId = $("#lan_sel").val();
	
	lanRegionChanger.doLanRegionChange(lanId);
});

$(".tr_syn_info").hide();

$(".red_mark").hide();

$(function(){
	$.ajaxSetup({ cache: false }); 
	$("#org_list").treegrid({
        //展开下级组织
        onBeforeExpand: function (node) {
        	if(node){
        		//当前节点组织编号
	            var mcode = node.party_id;
	            //当前节点组织层级
	            var org_level = node.org_level;
	            //获取当前节点已有的子节点
	            var nodes = $("#org_list").treegrid('getChildren', mcode);
	           
	            $.ajax({
	            	url:app_path+"/core/admin/org/orgAdmin!getChildrenOrg.do?ajax=yes",
	            	dataType:"json",
	            	data:{"org_id" : mcode,"org_level":org_level},
	            	success:function(reply){
	            		//首先清除原有的子节点
	            		if (nodes.length > 0) {
                        	for (var i = nodes.length - 1; i >= 0; i--) {
                            	$("#org_list").treegrid('remove', nodes[i].code);
                        	}
                    	}
	            		
	            		//挂载查询返回的子节点
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
        //单击节点事件
        onClickRow:function(node){
        	action_code = "";
        	if(node){
        		showOrgDetail(node);
        	}
        }
    });
    
    $("#btn_add").click(function(){
    	
    	//设置当前操作
    	action_code = "add";
    	
    	//设置上级组织信息
    	var party_id = $("#party_id").val();
    	var org_name = $("#org_name").val();
    	var region_id = $("#region_sel").val();
    	
    	if(party_id == null || party_id == ""){
    		alert("请为新增的组织选择上级组织");
            return;
    	}
    	
    	$("#parent_party_id").val(party_id);
    	$("#parent_org_name").val(org_name);
    	
    	//清空其它信息
    	$("#party_id").val("");
        $("#org_name").val("");
        $("#org_type_id").val("");
        $("#org_content").val("");
        $("#org_code").val("");
        $("#channel_type").val("");
        $("#channel_type_id").val("");
        
        //可编辑
        $("#org_name").removeAttr("disabled");
        $("#org_type_id").removeAttr("disabled");
        $("#org_content").removeAttr("disabled");
        $("#lan_sel").removeAttr("disabled");
        $("#region_sel").removeAttr("disabled");
        $("#channel_type").removeAttr("disabled");
        $("#channel_type_id").removeAttr("disabled");
        
        //样式修改
    	$(".searchformDiv").show();
    	
    	$(".tr_syn_info").hide();
    	
    	$("#btn_mod").hide();
    	$("#btn_del").hide();
    	
    	$(".red_mark").show();
    });
    
     $("#btn_mod").click(function(){
    	
    	action_code = "mod";
    	
    	var party_id = $("#party_id").val();
    	if(party_id == null || party_id == ""){
    		alert("请选择要修改的组织");
            return false;
    	}
    	
    	var is_syn = $("#is_syn").val();
    	
    	if("1" == is_syn){
    		alert("不能修改新增同步的组织信息");
            return false;
    	}
    	
    	$("#org_name").removeAttr("disabled");
    	$("#org_content").removeAttr("disabled");
	    $("#channel_type").removeAttr("disabled");
	    $("#channel_type_id").removeAttr("disabled");
        
	    $("#org_type_id").removeAttr("disabled");
    	$("#lan_sel").removeAttr("disabled");
	    $("#region_sel").removeAttr("disabled");
        
        $(".searchformDiv").show();
        $("#btn_add").hide();
        $("#btn_del").hide();
        $(".red_mark").show();
    });
    
     $("#btn_del").click(function(){
    	var party_id = $("#party_id").val();
    	
    	if(party_id == null || party_id == ""){
    		alert("请选择要删除的组织");
            return;
    	}
    	
    	var is_syn = $("#is_syn").val();
    	
    	if("1" == is_syn || "2" == is_syn){
    		alert("不能删除同步的组织信息");
            return false;
    	}
    	
       if(window.confirm('确认要删除此组织？')){
	         $.ajax({
		     	url:"orgAdmin!delOrg.do?ajax=yes",
		     	type : "post",
		        dataType:"json",
		        data:{"party_id" : party_id},
		        success:function(reply){
		        	var code = reply["code"];
	    			
	    			if("0" == code){
	    				alert("删除成功");
	    				
	    				$("#div_org_info input").val("");
	    				$("#div_org_info select").val("");
	    				
	    				freshOrgTree();
	    				
	    				resetOrgBtn();
	    			}else{
	    				var msg = reply["msg"];
	    				
	    				alert(msg);
	    			}
	        	},
	        	error:function(msg){
	        		alert(msg)
	        	}
		    });
       }
    });
    
     $("#btn_confirm").click(function(){
    	//取值
    	var org_level  = $("#org_level").val();
    	
    	if(action_code == "add"){
    		org_level = parseInt(org_level) + 1;
    	}
    	
    	var party_id = $("#party_id").val();
    	var org_name = $("#org_name").val();
    	var org_type_id = $("#org_type_id").val();
    	var org_content = $("#org_content").val();
    	var parent_party_id = $("#parent_party_id").val();
    	var union_org_code = $("#union_org_code").val();
    	var lan_id = $("#lan_sel").val();
    	var region_id = $("#region_sel").val();
        var parent_channel_type = $("#channel_type").val();
        var channel_type = $("#channel_type_id").val();
        
        if (action_code != "add" && 
        		(typeof(party_id) == "undefined" || party_id=="" || party_id.trim() == "")) {
			alert("组织编码不能为空");
			return false;
		}
        
        if (/^[a-zA-Z0-9]*$/.test(party_id) == false) {
			alert("组织编码由数字或者字母组成");
			return false;
		}
        
        if(typeof(org_name) == "undefined" || org_name=="" || 
  	    		org_name.trim() == ""){
  	    	alert("请填写组织名称");
            return;
  	    }
        
        if(typeof(org_type_id) == "undefined" || org_type_id=="" || 
    		org_type_id.trim() == ""){
//        		$.messager.alert('系统提示', '请为新增的组织选择组织类型', 'info');
    		alert("请选择组织类型");
            return false;
    	}
        
        var content = "";
        if(org_content != null && org_content != ""){
  	    	content = encodeURIComponent(org_content);
  	    }
        
    	if(typeof(lan_id) == "undefined" || lan_id=="" || lan_id.trim() == ""){
    		alert("请选择本地网");
    		return false;
    	}
    	
    	if(typeof(region_id) == "undefined" || region_id=="" || region_id.trim() == ""){
    		alert("请选择辖区");
    		return false;
    	}
    	
    	 if(typeof(parent_channel_type) == "undefined" || parent_channel_type=="" || 
    			 parent_channel_type.trim() == ""){
         	alert("请选择经营渠道类型");
 			return false;
         }
    	 
    	 if(typeof(channel_type) == "undefined" || channel_type=="" || 
    			 channel_type.trim() == ""){
          	alert("请选择经营渠道");
  			return false;
         }
    	
  	    if(action_code == "add"){
  	    	var data = {
      		  org_name:encodeURIComponent(org_name),
      		  org_type_id:org_type_id,
      		  org_content:content,
      		  parent_party_id:parent_party_id,
      		  union_org_code:union_org_code,
      		  lan_id:lan_id,
      		  region_id:region_id,
      		  channel_type:channel_type,
      		  org_level:org_level
      		  };
  	    	
  	    	doAddOrg(data);
  	    }else{
  	    	var data = {
  	    	  party_id:party_id,
  	    	  org_name:encodeURIComponent(org_name), 
  	    	  org_content:content,
      		  channel_type:channel_type,
      		  org_type_id:org_type_id, 
      		  lan_id:lan_id,
    		  region_id:region_id,
      		  union_org_code:union_org_code
      		  };
      		 
      		doModOrg(data);
  	    }
  	 });
    
     $("#btn_cancel").click(function(){
    	action_code = "";
    	
    	var node = $("#org_list").treegrid('getSelected');
    	
    	if(node){
    		showOrgDetail(node);
    	}
    	
    	resetOrgBtn();
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
 * 新增组织提交
 * @param data
 */
function doAddOrg(data){
	$.ajax({
    	url:"orgAdmin!addOrg.do?ajax=yes",
    	type : "post",
    	dataType:"json",
    	data:data,
    	success:function(reply){
    		if(typeof(reply)!="undefined"){
    			var code = reply["code"];
    			
    			if("0" == code){
    				$("#is_syn").val("0");
    				
    				var id = reply["newId"];
    				
    				alert("新增成功，组织编号为" + id);
    				
    				$("#party_id").val(id);
    				
    				freshOrgTree();
    				
    				resetOrgBtn();
    			}else{
    				var msg = reply["msg"];
    				
    				alert(msg);
    			}
    		}else{
    			alert("新增失败！");
    		}
    	},
    	error:function(msg){
    		alert("新增失败！" + msg);
    	}
    });
}

/**
 * 修改组织提交
 * @param data
 */
function doModOrg(data){
	$.ajax({
    	url:"orgAdmin!modOrg.do?ajax=yes",
    	type : "post",
    	dataType:"json",
    	data:data,
    	success:function(reply){
    		var code = reply["code"];
			
			if("0" == code){
				
				alert("修改成功");
				
				freshOrgTree();
				
				resetOrgBtn();
			}else{
				var msg = reply["msg"];
				
				alert(msg);
			}
    	},
    	error:function(e){
    		alert("出错啦:"+e);
    	}
    });
}

/**
 * 重置组织操作按钮
 */
function resetOrgBtn(){
	$(".searchformDiv").hide();
	
	var is_syn = $("#is_syn").val();
	
	if("1" == is_syn || "2" == is_syn)
		$(".tr_syn_info").show();
	else
		$(".tr_syn_info").hide();
	
	$("#div_org_info input").attr("disabled",true);
	$("#div_org_info select").attr("disabled",true);
	
	$("#btn_add").show();
	$("#btn_mod").show();
	$("#btn_del").show();
	
	action_code = "";
	
	$(".red_mark").hide();
}

/**
 * 刷新订单树
 */
function freshOrgTree(){
	$("#org_tree_li").addClass("tab_li_select");
	$("#org_search_li").removeClass("tab_li_select");
	
	//显示组织树，再刷新，否则组织树刷新会出现问题
	$("#div_org_tree").show();
	$("#div_org_search").hide();
	
	$("#org_list").treegrid('reload');
	
	//组织查询子页面重新查询
	if(document.getElementById("searchOrgFrame").contentWindow.doQuery)
		document.getElementById("searchOrgFrame").contentWindow.doQuery();
}

/**
 * 显示组织信息
 * @param orgInfo
 */
function showOrgDetail(orgInfo){
	//设置单签组织信息
	$("#party_id").val(orgInfo['party_id']);
	$("#org_code").val(orgInfo['org_code']);
	$("#org_name").val(orgInfo['org_name']);
	$("#org_name").attr("title",orgInfo['org_name']);
	$("#parent_party_id").val(orgInfo['parent_party_id']);
	$("#parent_org_name").val(orgInfo['parent_org_name']);
	$("#parent_org_name").attr("title",orgInfo['parent_org_name']);
	$("#org_type_id").val(orgInfo['org_type_id']);
	$("#org_level").val(orgInfo['org_level']);
	$("#org_content").val(orgInfo['org_content']);
	$("#org_content").attr("title",orgInfo['org_content']);
	
	lanRegionChanger.doLanRegionChange(orgInfo['lan_id'],orgInfo['region_id']);
	
	$("#union_org_code").val(orgInfo['union_org_code']);
	$("#org_type_name").val(orgInfo['org_type_name']);
	
	orgChannelChanger.doOrgChannelChange(orgInfo['parent_channel_type'],orgInfo['channel_type']);
	
	//设置组织同步信息
	$("#dept_id").val(orgInfo['dept_id']);
	$("#hq_dept_id").val(orgInfo['hq_dept_id']);
	$("#dept_name").val(orgInfo['dept_name']);
	$("#dept_name").attr("title",orgInfo['dept_name']);
	$("#dept_lvl").val(orgInfo['dept_lvl']);
	$("#areaid").val(orgInfo['areaid']);
	
	$("#countyid").val(orgInfo['countyid']);
	$("#zb_admin_depart_code").val(orgInfo['zb_admin_depart_code']);
	$("#syn_channel_type").val(orgInfo['syn_channel_type']);
	$("#syn_sub_type").val(orgInfo['syn_sub_type']);
	$("#type_id").val(orgInfo['type_id']);
	
	$("#chnl_kind_id").val(orgInfo['chnl_kind_id']);
	$("#is_syn").val(orgInfo['is_syn']);
	$("#syn_date").val(orgInfo['syn_date']);
	
	resetOrgBtn();
}

/**
 * 本地网，辖区联动
 */
function LanRegionChanger(){
	this.lans = {};
	this.regions = {};
	
	$.ajax({
     	url:ctx+"/core/admin/org/orgAdmin!getLanRegionInfo.do?ajax=yes",
     	dataType:"json",
     	data:{},
     	success:function(reply){
     		if(typeof(reply) != "undefined" && typeof(reply["lan"]) != "undefined" &&
     				typeof(reply["region"]) != "undefined"){
     			lans = reply["lan"];
     			regions = reply["region"];
     			
     			$("#lan_sel").empty();
     			
     			$("#lan_sel").append("<option value=''>请选择</option>");
     			
     			for(var i=0;i<lans.length;i++){
     				var option = "<option value="+lans[i].lan_id+">"+lans[i].lan_name+"</option>";
     				
     				$("#lan_sel").append(option);
     			}
     		}
     	},
     	error:function(msg){
     		alert(msg);
     	}
	});
	
	this.doLanRegionChange = function(lanId,regionId){
		$("#lan_sel").val(lanId);
		
		$("#region_sel").empty();
		$("#region_sel").append("<option value=''>请选择</option>");
			
		for(var i=0;i<regions.length;i++){
			var region_Id = regions[i]["region_id"];
			var region_Name = regions[i]["region_name"];
			var pRegion_Id = regions[i]["parent_region_id"];
			
			if(lanId == region_Id || lanId == pRegion_Id){
				var option = "<option value="+region_Id+">"+region_Name+"</option>";
			
				$("#region_sel").append(option);
			}
		}
		
		if(regionId)
			$("#region_sel").val(regionId);
	}
}

/**
 * 经营渠道联动
 */
function OrgChannelChanger(){
	this.orgChannels = {};
	
	$.ajax({
     	url:ctx+"/core/admin/org/orgAdmin!getOrgChannel.do?ajax=yes",
     	dataType:"json",
     	data:{},
     	success:function(reply){
     		if(typeof(reply) != "undefined" && typeof(reply["orgChannel"]) != "undefined"){
     			orgChannels = reply["orgChannel"];
     			
     			$("#channel_type").empty();
     			
     			$("#channel_type").append("<option value=''>请选择</option>");
     			
     			for(var i=0;i<orgChannels.length;i++){
     				var pChannelId  = orgChannels[i].parent_channel_id;
     				
     				if("-1" == pChannelId){
     					var option = "<option value="+orgChannels[i].channel_id+">"+orgChannels[i].channel_name+"</option>";
     					
     					$("#channel_type").append(option);
     				}
     			}
     		}
     	},
     	error:function(msg){
     		alert(msg);
     	}
	});
	
	this.doOrgChannelChange = function(channel_type,channel_type_id){
		$("#channel_type").val(channel_type);
		
		$("#channel_type_id").empty();
		
		$("#channel_type_id").append("<option value=''>请选择</option>");
			
		for(var i=0;i<orgChannels.length;i++){
			var channel_id = orgChannels[i]["channel_id"];
			var parent_channel_id = orgChannels[i]["parent_channel_id"];
			var channel_name = orgChannels[i]["channel_name"];
			
			if(channel_type == parent_channel_id){
				var option = "<option value="+channel_id+">"+channel_name+"</option>";
			
				$("#channel_type_id").append(option);
			}
		}
		
		if(channel_type_id)
			$("#channel_type_id").val(channel_type_id);
	};
}

var orgTypes = {};
/**
 * 组织类型初始化
 */
function initOrgType(){
	$.ajax({
     	url:ctx+"/core/admin/org/orgAdmin!getOrgType.do?ajax=yes",
     	dataType:"json",
     	data:{},
     	success:function(reply){
     		if(typeof(reply) != "undefined" && typeof(reply["orgType"]) != "undefined"){
     			orgTypes = reply["orgType"];
     			
     			$("#org_type_id").empty();
     			
     			$("#org_type_id").append("<option value=''>请选择</option>");
     			
     			for(var i=0;i<orgTypes.length;i++){
     				
 					var option = "<option value="+orgTypes[i].org_type_id+">"+orgTypes[i].org_type_name+"</option>";
 					
 					$("#org_type_id").append(option);
     				
     			}
     		}
     	},
     	error:function(msg){
     		alert(msg);
     	}
	});
}
