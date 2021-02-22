<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>

<form method="POST"  id="from1" action="/shop/admin/queueCardMateManagerAction!showStatus.do"  >
<tr>
<th>创建时间：</th>	
	                <td>
	                    <input type="text" name="queueParams.start_time" value="${queueParams.start_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">-
	                    <input type="text" name="queueParams.end_time" value="${queueParams.end_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">
	                </td>
	            </tr>
							 <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							 
              	</td>  
</tr>
</form>
<div class="grid" >
<div class="comBtnDiv">
<form method="POST"  id="from2"  >
	<grid:grid from="cardwriterstatus" formId="from1" >
			<grid:header>
			<grid:cell style="text-align:center; width:8%;vertical-align:middle">写卡机编码</grid:cell>
				<grid:cell width="22%">写卡机状态</grid:cell>
				<grid:cell width="22%">成功量</grid:cell>
				<grid:cell width="20%">失败量</grid:cell>
				<grid:cell width="20%">总量</grid:cell>
			</grid:header>
			<grid:body item="StatusAndNum" >
			<grid:cell>
			${StatusAndNum.machine_no}
			</grid:cell>
			<grid:cell>
			${StatusAndNum.card_mate_status}
			</grid:cell>
			<grid:cell>
			<a href="javascript:void(0);" id="sucnum"  style="margin-left:5px;"  onclick="openItemsDlg(this)"
			cardmateid='${StatusAndNum.machine_no}' sucflag='suc' sta_time='${queueParams.start_time }' end_time='${queueParams.end_time }'>${StatusAndNum.success}</a>
			</grid:cell>
			<grid:cell>
			<a href="javascript:void(0);" id="failnum"  style="margin-left:5px;" onclick="openItemsDlg(this)"
			cardmateid='${StatusAndNum.machine_no}' sucflag='fail' sta_time='${queueParams.start_time }' end_time='${queueParams.end_time }'>${StatusAndNum.fail}</a>
			</grid:cell>
			<grid:cell>
			<a href="javascript:void(0);" id="allnum"  style="margin-left:5px;" onclick="openItemsDlg(this)"
			cardmateid='${StatusAndNum.machine_no}' sucflag='all' sta_time='${queueParams.start_time }' end_time='${queueParams.end_time }'>${StatusAndNum.num}</a>
			</grid:cell>
			</grid:body>
	</grid:grid>
</form>
</div>
</div>

<div id="itemsDlg"></div>
<script type="text/javascript">
Eop.Dialog.init({id:"itemsDlg",modal:true,title:"详情",width:'1100px'});
$(function(){;
	
	$("#query_order_s").bind("click",function(){
		$("#from1").attr("action",ctx+"/shop/admin/queueCardMateManagerAction!showStatus.do").submit();
	});
	
});	
function openItemsDlg(aa){
	Eop.Dialog.open("itemsDlg");
	cardmateid = aa.getAttribute("cardmateid");
	sucflag = aa.getAttribute("sucflag");
	sta_time = aa.getAttribute("sta_time");
	end_time = aa.getAttribute("end_time");
	var url= ctx+"/shop/admin/queueCardMateManagerAction!showOrderItemsByStatus.do?ajax=yes&cardmateid="+cardmateid+"&sucflag="+sucflag+"&sta_time="+sta_time+"&end_time="+end_time;
	$("#itemsDlg").load(url,{},function(){});
	
}
</script>