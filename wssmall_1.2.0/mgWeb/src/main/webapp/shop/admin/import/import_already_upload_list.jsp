<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="css/import.css" type="text/css" />
<div class="searchWarp clearfix">
	<div class="newQuick">
    	<ul class="clearfix">
    		<li><a href="import!searchImportTemplates.do" class="template">模板管理</a></li>
        	<li><a href="import!importDownloadFile.do" class="file">文件导入</a></li>
        	<li><a href="import!importLogList.do" class="note">导入日志</a></li>
        	<li class="curr"><a href="import!importAlreadyUploadData.do" class="upload">数据中心</a></li>
        </ul>
    </div>
    <form  action="${actionName }" id="importLogForm" method="post">
	    <div class="newSearch">
	    	<input type="hidden" id="batch_id_input" value="${params.batch_id }" name="params.batch_id" />
	    	<input type="hidden" id="batch_status_input" value="" name="params.status" />
	        <input type="text" value="${params.batch_id}" id="search_batch_id" class="newIpt" />
	        <a href="javascript:void(0);" class="searchBtn2" style="margin-left:5px;">搜索</a>
	    </div>
    </form>
    <div class="clear"></div>
    
</div>
<div class="grid" id="typeList">
	<form validate="true" class="gridContent">
		<grid:grid from="webpage" formId="importLogForm">
			<grid:header>
				<grid:cell ><input style="display:none;" type="radio" id="toggleChk" /></grid:cell>
				<grid:cell style="width:140px;">批次号</grid:cell> 
				<grid:cell>上传文件名</grid:cell> 
				<%--<grid:cell >模板名称</grid:cell> 
				--%>
				<grid:cell style="width:130px;">创建时间</grid:cell>
				<grid:cell style="width:130px;">处理时间</grid:cell> 
				<grid:cell >状态</grid:cell> 
				<grid:cell style="width:200px;">处理结果</grid:cell> 
			</grid:header>
		
		  <grid:body item="mid">
		  		<grid:cell><input type="radio" total_num="${mid.total_num }" wait_num="${mid.wait_num }" succ_num="${mid.succ_num }" fail_num="${mid.fail_num }" oper_name="${mid.oper_name }" deal_time="<html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${mid.deal_time }"></html:dateformat>" value="${mid.log_id }"  style="display:none;" /><textarea style="display:none">${mid.data_json}</textarea></grid:cell>
		        <grid:cell>${mid.batch_id } </grid:cell>
		        <grid:cell>${mid.file_name }</grid:cell>
		        <%--<grid:cell>${mid.template_name }</grid:cell>
		        --%>
		        <grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${mid.created_time }"></html:dateformat></grid:cell>
		        <grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${mid.deal_time }"></html:dateformat></grid:cell>
		        <grid:cell>${mid.status }</grid:cell> 
		        <grid:cell>${mid.deal_desc }</grid:cell> 
		  </grid:body>  
		  
		</grid:grid>  
	</form>	
	</div>
	<div style="top:53px;" class="gridInfoWarp">
        <div class="proInfoWarp">
        	<div class="tabBg">
                <div class="stat_graph">
                    <h3>
                        <div class="graph_tab">
                            <ul>
                                <li class="selected" id="show_click_1"><span class="word">批次信息</span><span class="bg"></span></li>
                                <div class="clear"></div>
                            </ul>
                        </div>
                    </h3>
                </div>
            </div>
        	<div class="newOrderInfo">
                <div class="orderMsg">
                	<h2>批次信息</h2>
                    <div class="orderTab">
                        <table width="100%" cellspacing="0" cellpadding="0" border="0" class="newOrderTable">
                          <tbody>
                          <tr>
                            <th>上传总数：<input type="hidden" id="curr_batch_id" /></th>
                            <td><a href="javascript:void(0);" id="curr_total_num"></a></td>
                          </tr>
                          <tr>
                            <th>处理成功：</th>
                            <td><a href="javascript:void(0);" id="curr_succ_num"></a></td>
                          </tr>
                          <tr>
                            <th>处理失败：</th>
                            <td><a href="javascript:void(0);" id="curr_fail_num"></a></td>
                          </tr>
                          <tr>
                            <th>待处理：</th>
                            <td><a href="javascript:void(0);" id="curr_wait_num"></a></td>
                          </tr>
                        </tbody></table>
                    </div>
                </div>
            </div>
        	<div class="newOrderInfo">
                <div class="orderMsg">
                	<h2>数据信息</h2>
                    <div class="newOrderTab">
                        <table width="100%" cellspacing="0" cellpadding="0" border="0" class="newborTable">
                          <tbody>
                          <tr style="display:none;">
                            <th style="width:110px;"></th>
                            <td></td>
                          </tr>
                          
                        </tbody></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<div style="clear:both;padding-top:5px;"></div>
<script>
var AlreadyUpload  = {
	init : function(){
		var me = this;
		me.initClk();
	},
	initClk : function(){
		var me = this;
		$(".searchBtn2").bind("click",function(){
			$("#importLogForm").submit();
		});
		$("#search_batch_id").focus(function(){
			if($.trim($(this).val())==""||$.trim($(this).val())=="请输入批次号"){
				$(this).val("");
				$("#batch_id_input").val("");
			}
		}).blur(function(){
			if($.trim($(this).val())==""){
				$(this).val("请输入批次号");
				$("#batch_id_input").val("");
			}else{
				$("#batch_id_input").val($.trim($(this).val()));
			}
		});
		$("#typeList").find("tbody").find("tr").each(function(i,data){
			$(data).bind("click",function(){
				var json = eval("("+$(this).find("td:eq(0)").find("textarea").html()+")");
				$("#curr_batch_id").val($(this).find("td:eq(1)").html());
				$(".newOrderTable").find("tr:eq(0)").find("td a").html($(this).find("td:eq(0)").find("input").attr("total_num"));
				$(".newOrderTable").find("tr:eq(1)").find("td a").html($(this).find("td:eq(0)").find("input").attr("succ_num"));
				$(".newOrderTable").find("tr:eq(2)").find("td a").html($(this).find("td:eq(0)").find("input").attr("fail_num"));
				$(".newOrderTable").find("tr:eq(3)").find("td a").html($(this).find("td:eq(0)").find("input").attr("wait_num"));
				var attrDiv = $(".newborTable");
				attrDiv.find("tr:gt(0)").remove();
				for(var i=0,j=json.length;i<j;i++){
					var emptyTr = $(attrDiv).find("tbody").find("tr:eq(0)").clone().show();
					emptyTr.find("th").html(json[i]["cname"]+"：");
					emptyTr.find("td").html(json[i]["value"]);
					$(attrDiv).find("tbody").append($(emptyTr));
				}
			});
		});
		$("#typeList").find("tbody").find("tr:eq(0)").trigger("click");
		//成功数，失败数，待处理数点击事件
		$("#curr_succ_num").bind("click",function(){
			me.ajaxSubmitForm("1");
		});
		$("#curr_fail_num").bind("click",function(){
			me.ajaxSubmitForm("2");
		});
		$("#curr_wait_num").bind("click",function(){
			me.ajaxSubmitForm("0");
		});
	},
	ajaxSubmitForm:function(status){
		$("#batch_id_input").val($("#curr_batch_id").val());
		$("#batch_status_input").val(status);
		$("#importLogForm").submit();
	}
};
$(function(){
	AlreadyUpload.init();
});
</script>