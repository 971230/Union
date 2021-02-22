<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("goods/js/synch_logs_ecs.js");
</script>

<div >
	<form  action="${listFormActionVal}"  id="searchSynchLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="batch_id" name="params.batch_id" value="${params.batch_id }" />
						</td>
						<th>
							商品货品型号名称：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="name" name="params.name" value="${params.name }" />
						</td>
		    	      
		  	      		<th>同步开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.start_date"
								value="${params.start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>同步结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.end_date"
								value="${params.end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
					</tr>
					<tr>
						<th>SKU：</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="sku" name="params.sku" value="${params.sku }" />
						</td>
						<th>状态：</th>
						<td style="width:180px;">
							<select name="params.status" id="status" style="width:100px;">
								<option value="">--请选择--</option>
								<option value="WFS" <c:if test="${params.status=='WFS' }">selected</c:if>>未发送</ption>
								<option value="FSZ" <c:if test="${params.status=='FSZ' }">selected</c:if>>发送中</option>
								<option value="XYCG" <c:if test="${params.status=='XYCG' }">selected</c:if>>成功</option>
								<option value="XYSB" <c:if test="${params.status=='XYSB' }">selected</c:if>>失败</option>
							</select>
						</td>
						<th>同步类型：</th>
						<td style="width:180px;">
							<select name="params.object_type" id="object_type" style="width:100px;">
								<option value="goods" <c:if test="${params.object_type=='goods' }">selected</c:if>>商品</ption>
								<option value="product" <c:if test="${params.object_type=='product' }">selected</c:if>>货品</option>
								<option value="model" <c:if test="${params.object_type=='model' }">selected</c:if>>型号</option>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
						</td>
				 	</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
		  <a href="javascript:void(0);" style="margin-left:20px;" onclick="checkDate();" class="graybtn1"><span>搜 &nbsp 索</span></a>
		  <a href="javascript:void(0);" style="margin-left:10px;" onclick="publishAgain();" class="graybtn1"><span>重 &nbsp 发</span></a>
		  <a href="javascript:void(0);" style="margin-left:10px;" onclick="exportLogs()" class="graybtn1"><span>导 &nbsp 出</span></a>
		</div>
	</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchSynchLogsForm" action="${listFormActionVal}" >
			<grid:header>
				
				<%-- <grid:cell width="160px">商品ID</grid:cell> --%>
				<grid:cell width="130px">SKU/MODEL_CODE</grid:cell>
				<grid:cell width="150px">商品/货品/型号名称</grid:cell>
				<grid:cell width="130px">批次号</grid:cell>
				<grid:cell width="60px">同步总数</grid:cell>
				<grid:cell width="60px">成功数</grid:cell>
				<grid:cell width="60px">失败数</grid:cell>
				<grid:cell width="40px">动作</grid:cell>
				<grid:cell width="110px">创建时间</grid:cell>
				<grid:cell width="110px">同步时间</grid:cell>
				<grid:cell width="50px">状态</grid:cell>
				<grid:cell>同步结果</grid:cell>
			</grid:header>
			<grid:body item="log">
				<%-- <grid:cell>${log.goods_id } </grid:cell> --%>
				<grid:cell>${log.sku } </grid:cell>
				<grid:cell>
				<span title="${log.name }">${fn:substring(log.name , 0 , 10)}...</span>
				</grid:cell>
				<grid:cell>
					${log.batch_id}
				</grid:cell>
				<grid:cell>
					${log.batch_amount}
				</grid:cell>
				<grid:cell>
					${log.XYCG}
				</grid:cell>
				<grid:cell>
					${log.XYSB}
				</grid:cell>
				<grid:cell>
				    <c:if test="${log.action_code == 'A' }">新增</c:if>
				    <c:if test="${log.action_code == 'M' }">修改</c:if>
				    <c:if test="${log.action_code == 'D' }">删除</c:if>
				</grid:cell>
				<grid:cell>${fn:substring(log.created_date , 0 , 19)}</grid:cell>
				<grid:cell>${fn:substring(log.status_date , 0 , 19)}</grid:cell>
				<grid:cell>
					<div class="relaDiv">
					    <c:if test="${log.status == 'FSZ' }">发送中</c:if>
					    <c:if test="${log.status == 'WFS' }">未发送</c:if>
					    <c:if test="${log.status == 'XYSB'}"><span style='color:red'>失败</span></c:if>
					    <c:if test="${log.status == 'XYCG'}"><span style='color:green'>成功</span></c:if>
					    <div class="absoDiv">
						   <div class="absoDivHead">发布组织</div>
						   <div class="absoDivContent">
						      ${log.org_name_str}<c:if test="${empty log.org_name_str}">无发布组织</c:if>
						   </div>
						</div>
				    </div>
				</grid:cell>
				<grid:cell style="width:450px;">
					<%-- <span title="${log.failure_desc }">${fn:substring(log.failure_desc , 0 , 20)}...</span> --%>
				    <span title="${log.failure_desc }">${log.failure_desc}</span>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="goodsImportDiv"></div>

