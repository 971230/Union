<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="searchBx">
       	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
       	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
       	<input type="hidden" name="params.query_btn_flag" value="yes" />
       	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody><tr>
             
               <th>内部单号：</th>
               <td><input type="text" name="params.order_id" value="${params.order_id }" style="width:100px;" class="ipt_new"></td>
               
               <th>外部单号：</th>
               <td>
               	<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:100px;" class="ipt_new">
               </td>
                 <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                
	                 </td>
               
             </tr>
             <tr>
               
               <th>订单来源：</th>
               <td>
               	<select name="params.order_from" style="width:100px;">
               		<option value="-1" >--请选择--</option>
               		<c:forEach items="${order_from_list }" var="ds">
               			<option value="${ds.value }" ${ds.pkey==params.order_from?'selected':'' }>${ds.value_desc }</option>
               		</c:forEach>
				</select>
               </td>
               
               <th>归属区域：</th>
               <td>
               	<select name="params.order_city_code" style="width:100px;">
               		<c:forEach items="${dc_MODE_REGIONList }" var="ds">
               			<option value="${ds.value }" ${ds.value==params.order_city_code?'selected':'' }>${ds.value_desc }</option>
               		</c:forEach>
				</select>
               </td>
               
               <th>是否闪电送：</th>
               <td>
               	<select name="params.quick_shipping_status" style="width:100px;">
               		
               		<c:forEach items="${is_quick_ship_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.quick_shipping_status?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				</select>
				
               </td>
             </tr>
             <tr>
             
              <th>历史订单：</th>
               <td>
               	 <select name="params.order_is_his" style="width:100px;">
               	    <c:forEach items="${is_order_his_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_is_his?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				 </select> 
				
               </td>
               <th>物流单号：</th>
               <td><input type="text" name="params.logi_no" value="${params.logi_no }" style="width:100px;" class="ipt_new"></td>
        
                <th>补寄状态：</th>
               <td>
                  <select name="params.order_supply_status" style="width:100px;">
               		<c:forEach items="${order_supply_status_list }" var="ds">
               			<option value="${ds.pkey }" ${ds.pkey==params.order_supply_status?'selected':'' }>${ds.pname }</option>
               		</c:forEach>
				  </select> 
				       <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">查询</a>
               </td> 
               
             </tr>
           </tbody>
           </table>
</div>
<input type="hidden" name="query_type" value="${query_type }" />
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
			$("#aito_order_return_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
		});
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