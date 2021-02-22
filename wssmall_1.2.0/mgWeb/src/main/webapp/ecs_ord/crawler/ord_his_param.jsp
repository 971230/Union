<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="searchBx">
	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
		<tbody>
			   <tr>
			     <th>订单状态：</th>
				<td>
				    <select name="params.order_status" style="width:100px;">
               	    <c:forEach items="${order_status_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_status?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>
				
 				<th>订单类型：</th>
				<td>
				    <select name="params.order_type" style="width:100px;">
               	    <c:forEach items="${order_type_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_type?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>
				<th>订单来源：</th>
				<td>
				    <select name="params.order_src" style="width:100px;">
               	    <c:forEach items="${order_src_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_src?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>
				 			
				</tr>
			   
			   <tr>	
				<th>数据来源：</th>
				<td>
				    <select name="params.data_src" style="width:100px;">
               	    <c:forEach items="${data_src_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.data_src?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>	
 				<th>支付类型：</th>
				<td>
				    <select name="params.pay_type" style="width:100px;">
               	    <c:forEach items="${pay_type_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.pay_type?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>
				
				 <th>所在城市：</th>
				<td>
				    <select name="params.area_id" style="width:100px;">
               	    <c:forEach items="${area_id_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.area_id?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 	
				</td>				
				</tr>
		
			<tr>
			     <th>订单编号：</th>
				<td><input type="text" name="params.out_tid" value="${params.out_tid }" style="width:130px;" class="ipt_new"></td>
				
 				<th>入网人姓名：</th>
				<td><input type="text" name="params.buyer" value="${params.buyer }" style="width:130px;" class="ipt_new"></td>
				
				 <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>				
				</tr>
				
				<tr>
				<th>订购号码：</th>
				<td><input type="text" name="params.subs_svc_number" value="${params.subs_svc_number }" style="width:130px;" class="ipt_new"></td>		
				
			
               <th>配送方式：</th>
               <td>
               	 <select name="params.express_type" style="width:100px;">
               	    <c:forEach items="${express_type_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.express_type?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 				 
               </td>	
               <th>&nbsp;</th>			
               <td>               	 
				  <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;查询&nbsp;</a>				 
               </td>                
			</tr>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$(function(){
		$("#hide_params_tb").bind("click",function(){
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click",function(){
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		});
		
		$("#query_order_s").bind("click",function(){
			$("#ord_prt_f").attr("action",ctx+"/shop/admin/ordAuto!ordHisList.do").submit();
		});
		//$("#order_from_dv").aramsDiv();
	});
	
	(function($){
		$.fn.aramsDiv = function(){
			//this.isOut = true;
			var $this = $(this);
			$this.bind("mouseout",function(){
				//isOut = false;
				//alert(111);
				//$this.hide();
				//console.log("===mouseout===========");
			});
			$(this).bind("mouseover",function(){
				//isOut = true;
				//console.log("===mouseover===========");
			});
		};
	})(jQuery);
</script>