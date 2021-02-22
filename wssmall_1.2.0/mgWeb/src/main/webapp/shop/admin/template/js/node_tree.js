/**
 * 节点树管理
 */
var nodeTreeByTpl;
var copy_tree_node=[];
var copyOrCutFlag="copy";
var NodeManager = jQuery.extend({
    title:'标题',
    table_title : [ '节点编码', '节点名称', '节点归属实例表' ],
	titleWidth : [ '30%', '20%', '40%'   ],
	title_code : [ 'node_code', 'node_name',  'node_table_field_code'],
	event : ['NodeManager.field_edit', 'NodeManager.field_edit',  'NodeManager.setNodeInfo'],
	testtree : null,
	init: function(){
		var me = this;
		testtree = new TreeBase('aimDiv', '100%', this.titleWidth, this.table_title, this.title_code, this.event);
		this.qry_nodes();
		var self = this;
		$("#cutNodeTree").unbind('click').bind('click',function() {
			copy_tree_node=[];
			copyOrCutFlag="cut";
			me.testtree = $("#aimDiv").data('tree_obj');
			var data=$("tr[curr_check='yes']").data('node_info');
			var tr=$("tr[curr_check='yes']");
			if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	copy_tree_node=me.testtree.copyTreeNode(nodeId);
        	//alert(copy_tree_node.length);
        	$("tr[id='"+nodeId+"']").remove();
			me.testtree.delTreeNode(nodeId);
 		});
        $("#copyNodeTree").unbind('click').bind('click',function() {
        	copy_tree_node=[];
        	copyOrCutFlag="copy";
        	me.testtree = $("#aimDiv").data('tree_obj');
			var data=$("tr[curr_check='yes']").data('node_info');
			var tr=$("tr[curr_check='yes']");
			if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	copy_tree_node=me.testtree.copyTreeNode(nodeId);
        	//alert(copy_tree_node.length);
 		});
        $("#pasteNodeTree").unbind('click').bind('click',function() {
        	me.testtree = $("#aimDiv").data('tree_obj');
			var data=$("tr[curr_check='yes']").data('node_info');
			var tr=$("tr[curr_check='yes']");
			if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	
        	if(copy_tree_node==null||copy_tree_node.length==0){
        		alert("请先进行复制或者粘贴!");
        		return;
        	}
        	var order_template_id=$("#orderTemplateId").val();
        	var delNode=copy_tree_node[0];
        	var delNodeId=delNode.trId;
        	var retList=me.testtree.pasteTreeNode(nodeId,copy_tree_node);
        	 if(retList!=null&&retList.length>0){
             	for(var i=0;i<retList.length;i++){
             		var obj=retList[i];
             		var trId=obj.trId;
             		var trPid=obj.trPid;
             		var node_code=obj.tdValue.node_code;
             		var node_name=obj.tdValue.node_name;
             		var node_table_code=obj.tdValue.node_table_code;
             		var node_path="";//预留
             		var nodeDepth=obj.tdValue.node_depth;
             		var node_seq=obj.tdValue.node_seq;
             		$.ajax({
         				type : "post",
         				async : false,
         				url :"ordTplAction!addTplNode.do?id="+trId+"&super_id="+trPid+"&orderTemplateId="+order_template_id+"&nodeName="+encodeURI(encodeURI(node_name))+"&nodePath="+encodeURI(encodeURI(node_path))+"&flag="+node_table_code+"&pid="+encodeURI(encodeURI(node_code))+"&nodeDepth="+nodeDepth+"&sequ="+node_seq+"&ajax=yes",
         				data : {},
         				dataType : "json",
         				success : function(data) {
         					//alert(data.message);
         				}
         			});
             	}
             }
        	 
        	 if(copyOrCutFlag=="cut"){
        		 $.ajax({
     				type : "post",
     				async : false,
     				url : "ordTplAction!delTemplateNode.do?id="+delNodeId+"&ajax=yes",
     				data : {},
     				dataType : "json",
     				success : function(data) {
     				}
     			});
        	 }
 		});
        $("#delNodeTree").unbind('click').bind('click',function() {
        	
        	me.testtree = $("#aimDiv").data('tree_obj');
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	var delName=data.node_name;
        	var bool = confirm("确认删除节点【"+delName+"】以及其子节点吗？");
        	if(bool==true){
        		$.ajax({
    				type : "post",
    				async : false,
    				url : "ordTplAction!delTemplateNode.do?id="+nodeId+"&ajax=yes",
    				data : {},
    				dataType : "json",
    				success : function(data) {
    					//alert(data.message);
    					$("tr[id='"+nodeId+"']").remove();
    					me.testtree.delTreeNode(nodeId);
    					
    				}
    			});
        	}
			
 		});
        $("#upNodeTree").unbind('click').bind('click',function() {
            me.testtree = $("#aimDiv").data('tree_obj');
        	
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	var node_depth=data.node_depth;
        	var retStr=me.testtree.upMove(nodeId,superNodeId,node_depth);
        	
        	if(retStr!=null&&retStr!=""){
        		$.ajax({
    				type : "post",
    				async : false,
    				url : "ordTplAction!downNodeTree.do?id="+retStr+"&ajax=yes",
    				data : {},
    				dataType : "json",
    				success : function(data) {
    					
    				}
    			});
        	}
        	
 		});
        $("#downNodeTree").unbind('click').bind('click',function() {
        	me.testtree = $("#aimDiv").data('tree_obj');
        	
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	var node_depth=data.node_depth;
        	var retStr=me.testtree.downMove(nodeId,superNodeId,node_depth);
        	
        	if(retStr!=null&&retStr!=""){
        		$.ajax({
    				type : "post",
    				async : false,
    				url : "ordTplAction!downNodeTree.do?id="+retStr+"&ajax=yes",
    				data : {},
    				dataType : "json",
    				success : function(data) {
    					
    				}
    			});
        	}
        	
        	
        	
 		});
        $("#addNodeTree").unbind('click').bind('click',function() {
        	if($(this).siblings('div.tool_posi').is(':visible')){
        		$(this).siblings('div.tool_posi').hide();
        		$(this).closest('li').removeClass("curr");
        		return ;
        	}
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	$(this).closest('li').addClass("curr");
        	$(this).siblings('div.tool_posi').show();
 		});
        $("#addNodeTreeButton").unbind('click').bind('click',function() {
        	$(this).closest('div.tool_posi').hide();
        	$(this).closest('li').removeClass("curr");
        	var num=$("#textfield").val();
        	var intHot = $("input[name='RadioGroup1']:checked").val();
        	if(num==null||num==""){
        		alert("请输入添加节点数目!");
        		return;
        	}
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var tr=$("tr[curr_check='yes']");
        	var nodeId="";
            if(intHot==1){
            	nodeId=tr[0].getAttribute('pid');
        	}else{
        		nodeId=tr[0].id;
        	}
        	var order_template_id=$("#orderTemplateId").val();
        	var node_path=data.node_path;
        	if(node_path==null||node_path==""){
        		node_path=data.node_code;
        	}
        	
        	me.testtree = $("#aimDiv").data('tree_obj');
        	var list=me.testtree.addData(nodeId,num);
            if(list!=null&&list.length>0){
            	for(var i=0;i<list.length;i++){
            		var obj=list[i];
            		var trId=obj.trId;
            		var trPid=obj.trPid;
            		var node_code=obj.tdValue.node_code;
            		var node_name=obj.tdValue.node_name;
            		var node_table_code=obj.tdValue.node_table_field_code;
            		var node_seq=obj.tdValue.node_seq;
            		var nodeDepth=obj.tdValue.node_depth;
            		$.ajax({
        				type : "post",
        				async : false,
        				url :"ordTplAction!addTplNode.do?id="+trId+"&super_id="+trPid+"&orderTemplateId="+order_template_id+"&nodeName="+encodeURI(encodeURI(node_name))+"&nodePath="+encodeURI(encodeURI(node_path))+"&flag="+node_table_code+"&pid="+encodeURI(encodeURI(node_code))+"&nodeDepth="+nodeDepth+"&sequ="+node_seq+"&ajax=yes",
        				data : {},
        				dataType : "json",
        				success : function(data) {
        				}
        			});
            	}
            }
 		});
        $("#ModNodeTree").unbind('click').bind('click',function() {
        	var data=$("tr[curr_check='yes']").data('node_info');
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	//alert(JSON.stringify(data));
        	var url = "ordTplAction!addTemplateNode.do?ajax=yes&flag=edit&id="+nodeId;
        	self.title="编辑节点";
        	self.toUrlOpen(url, self.title);
 		});
		$("#reflashSetUpTree").unbind('click').bind('click',function() {
			
			$.ajax({
				type : "post",
				async : false,
				url :"lucenceTableSearchAction!searchPage.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					alert(data.message);
				}
			});
			
			$.ajax({
				type : "post",
				async : false,
				url :"lucenceAttrTableAction!searchPage.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					alert(data.message);
				}
			});
			
 		});
		$("#setUpTree").unbind('click').bind('click',function() {
			var data=$("tr[curr_check='yes']").data('node_info');
        	var tr=$("tr[curr_check='yes']");
        	if(tr[0]==null||tr[0].id==undefined||tr[0].id=='undefined'||tr[0].id==null){
        		alert("请选择节点进行操作!");
        		return;
        	}
        	var nodeId=tr[0].id;
        	var superNodeId=tr[0].getAttribute('pid');
        	
        	var url = "ordTplAction!setupTree.do?id="+nodeId+"&ajax=yes";
        	self.title="一键设置";
        	self.toUrlOpen(url, self.title);
        	
 		});
		Eop.Dialog.init({id:'choose_div',modal:false,title:self.title,height:"600px",width:"750px"});
	},
	toUrlOpen:function(url, title){
		Cmp.excute("choose_div",url,{},function(){});
	    Eop.Dialog.open("choose_div");
	 	//$("#dlg_choose_div").children("div").children("div").children("div").text(title);
	},
	field_edit: function(param,field_name,field_value){
		//var tr=$("tr[curr_check='yes']");
		//var nodeId=tr[0].id;
		if(param==null || param=="" || field_name==null || field_value==null){
			return;
		}
		var data=$("tr[curr_check='yes']").data('node_info'); 
		var node_id=param.node_id;
		var tpl_id=param.order_template_id;
		field_value=encodeURI(encodeURI(field_value));
		var url = "ordTplAction!modTemplateNode.do?ajax=yes&orderTemplateNode.node_id="+node_id+"&orderTemplateNode.order_template_id="+tpl_id+"&";
		if(field_name=="node_code"){
			data.node_code=field_value;
			url+="orderTemplateNode.node_code="+field_value;
		}else if(field_name=="node_name"){
			data.node_name=field_value;
			url+="orderTemplateNode.node_name="+field_value;
		}
		Cmp.excute('', url, {}, function(data){
			alert(data.msg);
		},"json");
	},
	setNodeInfo : function(id){			//设置 节点值，入参为input框的id
		var itemVal = $('input[id="' + id + '"]').data('itemValObj');			//选中的值
		var node_id = $('input[id="' + id + '"]').closest('tr').attr('id');		//当前节点ID
		var table_code=itemVal.re_table_name;
		var field_code=itemVal.re_field_name;
		$("#node_"+node_id).val(field_code);
		var data=$("tr[id='" + node_id + "']").data('node_info');
		data.node_table_code=table_code;
		data.node_table_field_code=field_code;
		$.ajax({
			type : "post",
			async : false,
			url :"ordTplAction!updateNodeTable.do?id="+node_id+"&nodeName="+table_code+"&nodePath="+field_code+"&ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				
			}
		});
		
	},
	page_close:function(){
	     Eop.Dialog.close("choose_div");
	},
	qry_nodes : function(){
		
		var list = [];
		var id = $("#orderTemplateId").val();
		$.ajax({
			type : "post",
			async : false,
			url : "ordTplAction!getNodeTreeByTpl.do?ajax=yes&id="+id,
			data : {},
			dataType : "json",
			success : function(data) {
				nodeTreeByTpl=data;
			}
		});
		
		if(nodeTreeByTpl.length>0){
			for(var i=0;i<nodeTreeByTpl.length;i++){
				var nodeTree=nodeTreeByTpl[i];
				var trobj = new TreeObj();
				trobj.tdValue=nodeTree;
				trobj.tdAlt = "超链接提示信息";
				// 超链接地址
				trobj.tdLink = "http://www.baidu.com";
				// 文字的颜色,默认黑色
				trobj.tdColor = "#000000";
				// 节点id
				trobj.trId = nodeTree.node_id;
				// 父节点id
				if(nodeTree.super_node_id==null||nodeTree.super_node_id==""){
					trobj.trPid ="-0";
				}else{
					trobj.trPid = nodeTree.super_node_id;
				}
				list.push(trobj);
			}
		}
		
		testtree.addRows(list);
	}
	
}, GridEdit);

$(function(){
	NodeManager.init();
	$('ul[name="model_switch"] li[name="simple_model"]').trigger('click');
});