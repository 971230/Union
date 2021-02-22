<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="searchBx">
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
     <tbody id="tbody_A">                
        <tr>
         <th>队列编码：</th>
         <td><input type="text" name="queueParams.queue_no" value="${queueParams.queue_no }" style="width:138px;" class="ipt_new"></td>
         
         <th>队列状态：</th>
         <td>
         	<select name="queueParams.queue_switch"  class="ipt_new" style="width:150px;">
             	<option value="" ${queueParams.queue_switch==''?'selected':'' }>--请选择--</option>
             	<option value="0" ${queueParams.queue_switch=='0'?'selected':'' } >开启</option>
             	<option value="1" ${queueParams.queue_switch=='1'?'selected':'' }>关闭</option>
			</select>
			<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;width: 80px;text-align: center" onclick="qry()">查&nbsp;&nbsp;&nbsp;询</a>
			</td>
         </tr> 
        </tbody>
        <tfoot>	        
	        <tr>
		        <td colspan="6">
		           <!--  <input type="button" id="single_add" class="dobtn" style="margin-left:15px;width: 80px;text-align: center;" value="新增"/> -->
					<a href="javascript:void(0);" id="batch_open" class="dobtn" style="margin-left:5px;width: 80px;text-align: center" >批量开启</a>
					<a href="javascript:void(0);" id="batch_close" class="dobtn" style="margin-left:5px;width: 80px;text-align: center">批量关闭</a>
				</td>
	        </tr>
        </tfoot>
       </table>  	
</div>  