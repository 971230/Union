<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="searchBx">
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
     <tbody id="tbody_A">                
        <tr>
         <th>队列编码：</th>
         <td><input type="text" name="queueParams.queue_no" value="${queueParams.queue_no }" style="width:138px;" class="ipt_new"></td>
         <th>订单编号：</th>
         <td><input type="text" name="queueParams.order_id" value="${queueParams.order_id }" style="width:138px;" class="ipt_new"></td>
         
         <th>订单状态：</th>
         <td>
         	<select name="queueParams.queue_status"  class="ipt_new" style="width:150px;">
             	<option value="" ${queueParams.queue_status==''?'selected':'' }>--请选择--</option>
             	<option value="1" ${queueParams.queue_status=='1'?'selected':'' } >回退中</option>
             	<option value="0" ${queueParams.queue_status=='0'?'selected':'' }>正常</option>
			</select>
			<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;width: 80px;text-align: center" onclick="qry()">查&nbsp;&nbsp;&nbsp;询</a>
			</td>
         </tr> 
        </tbody>
        <tfoot>	        
	        <tr>
		        <td colspan="6">
					<input type="button" id="batch_reback" class="dobtn" style="margin-left:5px;width: 80px;text-align: center" value="批量回退"/>
				</td>
	        </tr>
        </tfoot>
       </table>  	
</div> 