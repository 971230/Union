<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript"  src="<%=request.getContextPath() %>/ecs_ord/js/money_audit_list.js"></script> 
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>


<div >
	 <div class="comBtnDiv">
		
		     <a href="javascript:;" class="importExcel graybtn1" parentid="0"><span>稽核</span></a>
		     <a href="javascript:;" class="delExcle graybtn1" parentid="0"><span>删除报表</span></a>
		     
		      <a href="javascript:;" class="export_busi_excle graybtn1" parentid="0"><span>营业款稽核报表导出 </span></a>
		      <a href="javascript:;" class="export_receive_excle graybtn1" parentid="0"><span>实收款稽核报表导出 </span></a>
		</ul>
	</div>
	<!-- 查询表单 -->
	<form method="post" action='' id="queryListForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
					    <th style="width: 100px;">订单编号：</th>
						 <td>
							<input type="text" class="ipt_new" style="width: 138px;"	name="auditQueryParame.order_id"
							 value="${auditQueryParame.order_id}" class="searchipt" />
						 </td>
					    
						 <th style="width: 100px;">营业款稽核状态：</th>
			             <td>
				            <select name="auditQueryParame.audit_busi_money_status" class="ipt_new" style="width:140px;">
		               		   <c:forEach items="${audit_busi_money_status }" var="ds">
		               			 <option value="${ds.pkey }" ${ds.pkey==auditQueryParame.audit_busi_money_status ?'selected':'' } >${ds.pname }</option>
		               		   </c:forEach>
						     </select> 
						 </td>
						 
						 <th style="width: 100px;">实收款稽核状态：</th>
			             <td>
				           <select name="auditQueryParame.audit_receive_money_status" class="ipt_new" style="width:140px;">
		               		  <c:forEach items="${audit_busi_money_status }" var="ds">
		               			 <option value="${ds.pkey }" ${ds.pkey==auditQueryParame.audit_receive_money_status?'selected':'' } >${ds.pname }</option>
		               		  </c:forEach>
						   </select> 
						 </td>
					</tr> 
					<tr>  
		                 <th>开始时间：</th>
		                 <td>
		                     <input style="width: 138px;" type="text" name="auditQueryParame.start_time" value="${auditQueryParame.start_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		                 </td>
		                
		                 <th>结束时间：</th>
		                 <td>
		                     <input style="width: 138px;" type="text" name="auditQueryParame.end_time" value="${auditQueryParame.end_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		                 </td>
		                 
		                 <th>支付机构：</th>
			             <td>
		                	<select name="auditQueryParame.payproviderid" class="ipt_new" style="width:140px;">
		                		<c:forEach items="${payment_code_list }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==auditQueryParame.payproviderid?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
			             </td>  
					</tr>  
					<tr>    
					     
						 
			             <th>订单来源：</th>
			             <td>
		                	<select name="auditQueryParame.order_from" class="ipt_new" style="width:140px;">
		                		<c:forEach items="${order_from_list }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==auditQueryParame.order_from?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
			              </td>
			              
			             <th>支付方式：</th>
			             <td>
		                	<select name="auditQueryParame.pay_type" class="ipt_new" style="width:140px;">
		                	  <c:forEach items="${pay_type_list }" var="ds">
		                		<option value="${ds.pkey }" ${ds.pkey==auditQueryParame.pay_type?'selected':'' }>${ds.pname }</option>
		                	  </c:forEach>
							</select>
			              </td>
			              
			             <th>订单类型：</th>
			             <td>
		                	<select name="auditQueryParame.order_type" class="ipt_new" style="width:140px;">
		                		<c:forEach items="${order_type_list }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==auditQueryParame.order_type?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
			              </td> 
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	
	<!-- 查询按钮 -->
	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
			</li>
	    </ul>
		<div style="clear:both;"></div>
	</div>

   <!-- 列表数据显示 -->
   <div class="grid"  >
	<form method="POST" id="print_tpl_list">
		<grid:grid  from="webpage" ajax="yes" formId ="queryListForm">
			<grid:header >
				<span>
					<grid:cell>订单编号</grid:cell>
					<grid:cell>开户时间</grid:cell>
					<grid:cell>订单来源</grid:cell>
					<grid:cell>订单类型</grid:cell>
					<grid:cell>商品名称</grid:cell>
					<grid:cell>支付类型</grid:cell>
					<grid:cell>处理人</grid:cell>
					<grid:cell>开户姓名</grid:cell>
					<grid:cell>业务号码</grid:cell>
					<grid:cell>地市</grid:cell>
					<grid:cell>新老用户</grid:cell>
					<grid:cell>营业款稽核状态</grid:cell>
					<grid:cell>实收款稽核状态</grid:cell>
					<grid:cell>稽核操作</grid:cell>
				</span>
			</grid:header>
		
		    <grid:body item="pmt" >
<!-- 			    <grid:cell> <a href="orderWarningAction!queryDetails.do?queryType=edit&orderWarning.warning_id=" >编辑</a></grid:cell>
 -->			
				<grid:cell><a onclick="openOrderDetails('${pmt.order_id}')" >${pmt.order_id}</a></grid:cell>
				<grid:cell>${pmt.bss_account_time}</grid:cell>
				<grid:cell>${pmt.order_from_name}</grid:cell>
				<grid:cell>${pmt.order_type_name}</grid:cell>
				<grid:cell>${pmt.goodsName}</grid:cell>
				<grid:cell>${pmt.pay_type_name}</grid:cell>
				<grid:cell>${pmt.lock_user_id}</grid:cell>
				<grid:cell>${pmt.phone_owner_name}</grid:cell>
				<grid:cell>${pmt.phone_num}</grid:cell>
				<grid:cell>${pmt.order_city_code_name}</grid:cell>
				<grid:cell>${pmt.is_old_name}</grid:cell>
				<grid:cell>${pmt.audit_busi_money_status_name}</grid:cell>
				<grid:cell>${pmt.audit_receive_money_status_name}</grid:cell>
				<grid:cell><a href="javascript:void(0);" order_id="${pmt.order_id}" onclick="manualAudit(this)">人工稽核</a></grid:cell>
			<%-- <grid:cell>
				  <c:forEach items="${order_from_list }" var="ds">
					   <c:if test="${ds.pkey==pmt.order_from}">
					      ${ds.pname }
					   </c:if>
				  </c:forEach>
				</grid:cell>
			--%>
		  </grid:body>
		</grid:grid>  
	 </form>	
	 <div style="clear:both;"></div>
  </div>
</div>



<!-- 添加对话框 -->
<div id="importExcelDlg"></div>

<div id="manualAuditDlg"></div>

<div id="delExcelDlg"></div>

