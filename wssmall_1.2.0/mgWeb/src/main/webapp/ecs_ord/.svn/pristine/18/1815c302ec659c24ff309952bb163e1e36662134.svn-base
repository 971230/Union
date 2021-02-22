<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>

<div id="order_syn_service">
	<form  action="${listFormActionVal}"  id="searchOrderGjCoQueueForm" method="post" >
		<div class="searchformDiv">
	    	<%--<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							外部订单号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="ext_order_id" name="params.ext_order_id" value="${params.ext_order_id }" />
						</td>
						<th>
							服务编码：
						</th>
						<td style="width:180px;">
							<select name="params.service_code" id="service_code">
								<option value="CO_GUIJI" <c:if test="${params.service_code=='CO_GUIJI'}">selected</c:if>>CO_GUIJI</option>
								<option value="CO_GUIJI_NEW" <c:if test="${params.service_code=='CO_GUIJI_NEW'}">selected</c:if>>CO_GUIJI_NEW</option>
							</select>
						</td>
					</tr>
	  	    	</tbody>
	  	    </table>
    	--%></div>
	</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" ajax="yes" formId="searchOrderGjCoQueueForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell width="60px">模板ID</grid:cell>
				<grid:cell width="80px">订单来源系统</grid:cell>
				<grid:cell width="150px">最后一次执行时间</grid:cell>
				<grid:cell width="150px">下次执行时间</grid:cell>
				<grid:cell width="150px">接口执行结束时间</grid:cell>
				<grid:cell width="60px">运行状态</grid:cell>
			</grid:header>
			<grid:body item="service">
				<grid:cell>
					<input type="checkbox" name="tmpl_id" id="tmpl_id" value="${service.tmpl_id }">
				</grid:cell>
				<grid:cell>
					${service.tmpl_id}
				</grid:cell>
				<grid:cell>
					${service.order_from}
				</grid:cell>
				<grid:cell>
					${fn:substring(service.last_execute , 0 , 19)}
				</grid:cell>
				<grid:cell>
					${fn:substring(service.next_execute , 0 , 19)}
				</grid:cell>
				<grid:cell>
					${fn:substring(service.data_end_time , 0 , 19)}
				</grid:cell>
				<grid:cell>
					<c:if test="${service.run_status=='00X' }">已暂停</c:if>
					<c:if test="${service.run_status=='00A' }">已启动</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
    <div style="margin-left: auto;margin-right: auto;" align="center">
        <a href="javascript:void(0);" name="stopOrderSyn" id="stopOrderSyn" class="graybtn1" ><span>暂&nbsp停</span></a>
        <a href="javascript:void(0);" name="startOrderSyn" id="startOrderSyn" class="graybtn1"><span>启&nbsp动</span></a>
    </div>
</div>
<div id="order_syn_dialog"></div>
<script type="text/javascript">
$(function(){
	$("#stopOrderSyn").click(function(){
		var tmpl_ids = $("#order_syn_service input[name='tmpl_id']:checked");
		if(tmpl_ids.length==0){
			alert("请选择要暂停的服务");
			return ;
		}
		var tmpl_id = "";
		tmpl_ids.each(function(idx,item){
			tmpl_id = tmpl_id + $(item).val() + ",";
		})
		tmpl_id = tmpl_id.substring(0, tmpl_id.length-1);
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!stopOrderSyn.do?ajax=yes",
			data:"params.tmpl_id=" + tmpl_id ,
			dataType:"json",
			success:function(result){
				alert(result.message);
				Eop.Dialog.close("order_syn_dialog");
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	})
	$("#startOrderSyn").click(function(){
		var tmpl_ids = $("#order_syn_service input[name='tmpl_id']:checked");
		if(tmpl_ids.length==0){
			alert("请选择要暂停的服务");
			return ;
		}
		var tmpl_id = "";
		tmpl_ids.each(function(idx,item){
			tmpl_id = tmpl_id + $(item).val() + ",";
		})
		tmpl_id = tmpl_id.substring(0, tmpl_id.length-1);
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!startOrderSyn.do?ajax=yes",
			data:"params.tmpl_id=" + tmpl_id ,
			dataType:"json",
			success:function(result){
				alert(result.message);
				Eop.Dialog.close("order_syn_dialog");
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	})
})
</script>

