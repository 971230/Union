<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">重复订单刷新</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
	<form method="post">
	<table class="tab_form">
		<tr>	
			<th>请输入订单编号   （","分开）：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="order_ids" name="order_ids"></textarea>
			</td>
		</tr>
		<tr>
		   <th>订单来源(source_from)：</th>
		   <td>
		     <html:selectdict curr_val="${order_from}" style="width:120px" id="order_from" name ="order_from"  attr_code="ORDER_FROM"></html:selectdict>
           </td>
		</tr>
		<tr>
			<th>是否一并删除es_order_outer记录：</th>
			<td>
			  <select  style="width:120px"  class="ipttxt"  id="is_remove_outer_data" name="is_remove_outer_data">
			      <option value="1">   是      </option>
			      <option value="0" selected="selected">   否      </option>
			  </select>
		    </td>
		</tr>
		<tr>
		    <th></th>
		    <td>
				<a href="javascript:void(0);" id="refreshRepeatOrd_a" class="dobtn" style="margin-left:5px;">刷新重复订单</a>
				<a href="javascript:void(0);" id="resetOrderId_a" class="dobtn" style="margin-left:5px;">重置订单ID</a>
			</td>
		</tr>
	</table>
	</form>
</div>

<script>
	$(function() {

		
		$("#refreshRepeatOrd_a").bind("click", function() {
			var order_ids = $("#order_ids").val();
			if (order_ids == "") {
				alert("请输入订单编号！");
				return;
			}
			var order_from = $("#order_from").val();
			if(order_from==null||order_from==''){
				alert("请选择订单来源");
				return false;
			}
			var is_remove_outer_data = $("#is_remove_outer_data").val();
			if(is_remove_outer_data=='1'){
				flag = confirm("确认刷新重复订单并且删除es_order_outer的记录吗？");
			}else{
				flag = confirm("确认只删除缓存不清除es_order_outer记录？");
			}
			if (!flag)return;
				$.ajax({
					type : "post",
					async : false,
					url : "orderRefreshTreeAttrAction!refreshRepeatOrder.do?ajax=yes&order_ids="+order_ids+"&order_source_from="+order_from+"&is_remove_outer_data="+is_remove_outer_data,
					data : {},
					dataType : "json",
					success : function(data) {
						alert(data.msg);
					}
				});
			
		});	
		
		$("#resetOrderId_a").bind("click", function() {
			$("#order_ids").val("");
		});
	});
</script>

