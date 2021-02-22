<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
<div>
	<input id="lucence_search_tx" name="se_show_name" type="text" tableColumn="se_show_name" keyFun="searchResult" search_url="lucenceTableSearchAction!search.do?ajax=yes"/>
	<input type="button" id="setupBtn" value="一键设置">
	<input type="hidden" id="id" value="${id }">
</div>

<script type="text/javascript">
	$(function(){
		Lucene.init('lucence_search_tx');
		
		$("#setupBtn").click(function() {
			var nodeId=$("#id").val();
			var tableCode=$("#lucence_search_tx").val();
			$.ajax({
 				type : "post",
 				async : false,
 				url :"ordTplAction!setupTreeNode.do?id="+nodeId+"&nodeName="+tableCode+"&ajax=yes",
 				data : {},
 				dataType : "json",
 				success : function(data) {
 					 alert(data.message);
 					 NodeManager.page_close();
 					 if(data.result=="0"){
 						 var retStr=data.retStr;
 						 //alert(retStr);
 						 if(retStr!=null&&retStr.length>0){
 							var list=retStr.split(";");
 							for(var i=0;i<list.length;i++){
 								var nodeInfo=list[i];
 								var node=nodeInfo.split(",");
 								var nodeId=node[0];
 								var tableCode=node[1];
 								var fieldCode=node[2];
 								$("#node_"+nodeId).val(fieldCode);
 								var curData=$("tr[id='" + nodeId + "']").data('node_info');
 			 					 curData.node_table_code=tableCode;
 			 					 curData.node_table_field_code=fieldCode;
 							}
						 }
 					 }
 					
 				}
 			});
		    
					
				
		});	
	});
	
	function searchResult(event,data){
		console.log(data);
		var hvalue = $("#lucence_search_tx").attr("hiddenValue");
		var itemVal = $("#lucence_search_tx").data("itemValObj");
		$("#lucence_search_tx").val(itemVal.se_table_name);
		
	}
</script>