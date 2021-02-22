<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript">
loadScript("goods/js/goods_import_logs_ecs.js");
</script>

<div >
	<form  action="${listFormActionVal}"  id="searchGoodsImportLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="params.batch_id" value="${params.batch_id }" />
						</td>
		    	      
		  	      		<th>开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.start_date"
								value="${params.start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.end_date"
								value="${params.end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>处理状态：</th>
						<td style="width:180px;">
							<html:selectdict name="params.deal_flag" curr_val="${params.deal_flag }"
					             attr_code="GOODS_IMPORT_STATUS" style="width:155px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
						</td>
				 	</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
	</form>
	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<div>
			       <form action="goods!importGoods.do" id="goodsUploadForm" method="post"
						enctype="multipart/form-data">
						   <input type="file" class="ipttxt" id="goodsUploadFile" name="file" /> 
						   <a href="javascript:void(0);" id="importActBtn" style="margin-right:10px;" class="graybtn1">导入商品</a>
						   <a href="javascript:void(0);" id="downloadBtn" style="margin-right:10px;" class="graybtn1">下载模板</a>
						   <a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsImportLogsForm" action="${listFormActionVal}" >
			<grid:header>
				
				<grid:cell sort="sn" width="80px">批次号</grid:cell>
				<%--<grid:cell width="130px">导入时间</grid:cell>
				<grid:cell>活动类型编码值</grid:cell>
				--%>
				<grid:cell>活动编码</grid:cell>
				<grid:cell>活动名称</grid:cell>
				<grid:cell>活动期限</grid:cell>
				<%--<grid:cell>合约费用</grid:cell>
				<grid:cell>预存金额</grid:cell>
				<grid:cell>按月返还金额</grid:cell>
				--%>
				<grid:cell>产品编码</grid:cell>
				<grid:cell>产品名称</grid:cell>
				<grid:cell>机型名称</grid:cell>
				<grid:cell>型号编码</grid:cell>
				<grid:cell>颜色编码</grid:cell>
				<grid:cell>导入时间</grid:cell>
				<grid:cell>状态更新时间</grid:cell>
				<grid:cell>处理状态</grid:cell>
				<grid:cell>处理次数</grid:cell>
				<grid:cell>结果描述</grid:cell>
			</grid:header>
			<grid:body item="log">
				<grid:cell>${log.batch_id } </grid:cell>
				<%--<grid:cell>
						${log.created_date}
				</grid:cell>
				<%--<grid:cell>${log.atv_code } </grid:cell>
				--%>
				<grid:cell>${log.rel_code } </grid:cell>
				<grid:cell>${log.atv_name } </grid:cell>
				<grid:cell>
					${log.atv_months}
				</grid:cell>
				<%--<grid:cell>
					${log.mktprice}
				</grid:cell>
				<grid:cell>
				    ${log.deposit_fee }	
				</grid:cell>
				<grid:cell>
					${log.mon_return }	
				</grid:cell>
				--%><grid:cell>
					${log.product_code }
				</grid:cell>
				<grid:cell>
				    ${log.product_name }
				</grid:cell>
				<grid:cell>
				    ${log.model_name }
				</grid:cell>
				<grid:cell>
				    ${log.model_code }
				</grid:cell>
				<grid:cell>
				    ${log.color_name }
				</grid:cell>
				<grid:cell>
				    ${fn:substring(log.created_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    ${fn:substring(log.status_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    <c:if test="${log.deal_flag == 0 }">未处理</c:if>
				    <c:if test="${log.deal_flag == 1 }">成功</c:if>
				    <c:if test="${log.deal_flag == 2}">失败</c:if>
				</grid:cell>
				<grid:cell>
				    ${log.deal_num }
				</grid:cell>
				<grid:cell>
					<span title="${log.deal_desc}">${fn:substring(log.deal_desc , 0 , 15)}...</span>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="goodsImportDiv"></div>

