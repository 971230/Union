
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>




<form id="calllogPageForm">

</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_n_cont_sub">
	<tbody>
	
        	<tr>
				<%-- &nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;${calllogPageRowCount}&nbsp;条外呼记录 &nbsp;&nbsp;<input type="button" id="open_calllog_list" value="点击展开" onclick="load_calllog()" class="graybtn1"></th> --%>
			</tr>
	</tbody>
	
</table>
										
	<div class="grid_n_div" id="log_table_show" style="display:none">
			<div id="wrapper" align="center" style="display:none;">
											<audio preload="auto" controls>
										    <source src="" />
											</audio>
										</div>
              	<div class="grid_n_cont">
					<div class="grid" >
						<form method="POST"  id="calllogPageForm2" >
                                 <grid:grid from="calllogPage"  formId="calllogPageForm" action="/shop/admin/orderFlowAction!queryCalllog.do?ajax=yes&order_id=${order_id }&call_order_type=${call_order_type }" ajax="yes">
									<grid:header>
	    								<grid:cell width="10%" >地区</grid:cell>
	    								<grid:cell width="20%">县分</grid:cell>
										<grid:cell width="20%">工号</grid:cell>
										<grid:cell width="10%">呼叫时间</grid:cell>
										<grid:cell width="10%">主叫号码</grid:cell>
										<grid:cell width="10%">被叫号码</grid:cell>
										<grid:cell width="20%">操作</grid:cell>
									</grid:header>
									<grid:body item="calllogPageF">
										<grid:cell>${calllogPageF.lan_name}</grid:cell>
										<grid:cell>${calllogPageF.dep_name}</grid:cell>
										<grid:cell>${calllogPageF.realname}</grid:cell>
										<grid:cell>${calllogPageF.create_time}</grid:cell>
										<grid:cell>${calllogPageF.mobile}</grid:cell>
										<grid:cell>${calllogPageF.ship_mobile}</grid:cell>
										<grid:cell>
										<a href="javascript:void(0);" is_play="stop" file_id="${calllogPageF.file_id}" order_id="${calllogPageF.order_id}" calleePhone="${calllogPageF.ship_mobile}" name="palyCalllog" class="dobtn" style="margin-left: 1px;">播放</a> 
										<%-- <a href="javascript:void(0);" file_id="${calllogPage.file_id}" order_id="${calllogPage.order_id}" name="downCalllog" class="dobtn" style="margin-left: 1px;">下载</a> --%>
										</grid:cell>
										
									</grid:body>
								</grid:grid>
						</form>	
					</div>
                </div>
            </div>
            <input id="call_order_type" type="hidden" value="${call_order_type}" />
  

      
<script type="text/javascript">

$(function(){
	
	
	$("a[name='palyCalllog']").bind("click",function(){
		var file_id = $(this).attr("file_id");
		var order_id = $(this).attr("order_id");
		var calleePhone = $(this).attr("calleePhone");
		var call_order_type = $("#call_order_type").val();
		if(file_id == null || file_id==""){
			alert("异常：file_id为空");
			return;
		}
		if(order_id == null || order_id==""){
			alert("异常：order_id为空");
			return;
		}
		 var url = app_path+"/shop/admin/ordCall!qryCalllog.do?ajax=yes&file_id="+file_id+"&order_id="+order_id+"&calleePhone="+calleePhone+"&call_order_type="+call_order_type;
		 $.Loading.show("正在查询。请稍候...");
		 $.ajax({
			 url:url,
			 dataType:'json',
			 success: function(reply){
				if(reply.result==0) {
					var source = document.getElementsByTagName('source')[0];
					var audio = document.getElementsByTagName('audio')[0];
					audio.src = reply.playurl;
					audio.load();
			        console.log(audio);
					document.getElementById("wrapper").style.display="block"; 
					$.Loading.hide();
				}else {
					$.Loading.hide();
					alert(reply.message);
				}
		     }
		});
		
	});
});

</script>
