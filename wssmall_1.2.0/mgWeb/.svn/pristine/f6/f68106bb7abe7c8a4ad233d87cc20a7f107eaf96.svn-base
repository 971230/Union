<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
	<form action="infLogs!showInfLogsList.do" id="uploadForm" method="post"
				enctype="multipart/form-data">
		<div class="searchformDiv">
			<table width="50%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<%--<th>时间段：</th>
						<td><input type="text" name="params.start_date"
							value="${params.start_date }" id="start_date" readonly="readonly"
							class="dateinput ipttxt" dataType="date" />-<input type="text" name="params.end_date"
							value="${params.end_date }" id="end_date" readonly="readonly"
							class="dateinput ipttxt" dataType="date" />						
						</td> --%>
						<th>内部订单号：</th>
						<td>
							<input type="text" name="params.rel_obj_id"
							value="${params.rel_obj_id }" id="rel_obj_id"class="ipttxt" />
						</td>
						<td>
							<input type="button" style="margin-right:20px;" class="comBtn" onclick="search();" value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">	
			<input class="comBtn" type="button" value="批量记录写入" onclick="writeByIds();" style="margin-right:20px;" /> 
			<input class="comBtn" type="button" onclick="writeByDays();" value="按时间段写入" style="margin-right:20px;"  /> 
		</div>
	</form>
	
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell width="10px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell width="110px">内部订单号</grid:cell>
				<grid:cell width="80px">接口编码</grid:cell>
				<grid:cell width="80px">请求时间</grid:cell>
				<grid:cell width="80px">请求地址</grid:cell>
				<grid:cell width="30px">状态</grid:cell>
				<grid:cell width="80px">状态描述</grid:cell>
				<grid:cell width="80px">接口报文</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>
						<input type="checkbox" name="eckBox" value="${objeto.log_id}" id="${objeto.log_id }" />
				</grid:cell>
				<grid:cell>${objeto.col3 } </grid:cell>
				<grid:cell>${objeto.op_code }  </grid:cell>
				<grid:cell>${fn:substring(objeto.req_time , 0 , 19)}</grid:cell>
				<grid:cell>${objeto.ep_address } </grid:cell>
				<grid:cell> 
					<c:if test="${objeto.state == '0' }">成功</c:if>
				  	<c:if test="${objeto.state == '1' }">失败</c:if>
				</grid:cell>
				<grid:cell>${objeto.state_msg }  </grid:cell>
				<grid:cell>
				  <a name="log_id_req" id ="log_id_req" log_id="${objeto.log_id }" href="javascript:searchEsearch(${objeto.log_id});">查看详情</a>
			    </grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<div id="daysDlg"></div>
<div id="esearchDlg"></div>

<script>
	$(function(){
		 Eop.Dialog.init({id:"daysDlg",modal:true,title:"选择时间段",width:'800px'});
		 Eop.Dialog.init({id:"esearchDlg",modal:true,title:"展示报文信息",width:'800px'});
	});
	function writeByIds() {
		var map = getEckValue("eckBox");
		if (map[0]<1) {
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return;
		}
		if(confirm("确定需要将选中的数据写入文件系统？")){
			$.ajax({
				type : "post",
				async : false,
				url : "infLogs!writeEsearchByIds.do?ajax=yes",
				data : {
					"ids" : map[1]
				},
				dataType : "json",
				success : function(data) {
					alert(data.message);		
				}
			});
		}
	}
	function writeByDays() {
		
		var begin_date = $("#start_date").val();
		var finish_date = $("#end_date").val();
		if(confirm("确定需要将 "+begin_date+"~"+finish_date+" 时间段的数据写入文件系统？")){
			$.ajax({
				type : "post",
				async : false,
				url : "infLogs!writeEsearchByDays.do?ajax=yes",
				data : {
					"begin_date" : begin_date,
					"finish_date": finish_date
				},
				dataType : "json",
				success : function(data) {
					if(data.result=='0'){
						alert(data.message);
					 	Eop.Dialog.close("daysDlg");
				 	}else{
				 		alert(data.message);
				 	}		
				}
			});
		}
	}
	function showDays(){
	    var url= app_path+"/shop/admin/infLogs!showDays.do?ajax=yes";
	    $("#daysDlg").load(url,{},function(){});
		Eop.Dialog.open("daysDlg");
	}
	
	function search(){
		document.getElementById("uploadForm").submit();
	}
	
	function searchEsearch(log_id){
		var url= app_path+"/shop/admin/infLogs!showEsearch.do?ajax=yes&log_id="+log_id;
	    $("#esearchDlg").load(url,{},function(){});
	    $("#out_param").val("");
		$("#in_param").val("");
		Eop.Dialog.open("esearchDlg");
	}
</script>