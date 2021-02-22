<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="searchBx">
	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
		<tbody>
			<tr>
			     <th>外部订单编号：</th>
				<td><input type="text" name="params.out_tid" value="${params.out_tid }" style="width:130px;" class="ipt_new"></td>
				
 				<th>内部订单编号：</th>
				<td><input type="text" name="params.order_id" value="${params.order_id }" style="width:130px;" class="ipt_new"></td>
				
				 <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>
				
				
				</tr>
				<tr>
				<th>开户号码：</th>
				<td><input type="text" name="params.phone_num" value="${params.phone_num }" style="width:130px;" class="ipt_new"></td>		
				
			
               <th>配送方式：</th>
               <td>
               	 <select name="params.shipping_id" style="width:100px;">
               	    <c:forEach items="${shipping_type_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.shipping_id?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 
				 
               </td>
				<th>历史订单：</th>
               <td>
               	 <select name="params.order_is_his" style="width:100px;">
               	    <c:forEach items="${is_order_his_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_is_his?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 
				  <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">查询</a>
				  <c:if test="${whFrom!=null&&whFrom=='quality' }">
				    <a  class="dobtn" style="margin-left:5px;"  href="javascript:history.go(-1);" ><span>返回质检稽核</span></a>
				   </c:if>
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
			$("#ord_prt_f").attr("action",ctx+"/shop/admin/ordPrt!ordPrtList.do").submit();
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