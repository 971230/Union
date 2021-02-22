<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/crmofferid.js"></script>
<div style="margin-left:10px">
	<form id="gridform" class="grid">
		<div class="searchformDiv">
	    	    <tr>
	    	      <th>销售品名称:</th><input type="hidden" id="cardTypeCode" value="${cardType}" name="cardType" />
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="offer_name" value="${offer_name }" class="searchipt" /></td>
	    	      <th>销售品ID:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="offer_id" value="${offer_id }" class="searchipt" /></td>
	    	      <td><input type="button" style="margin-left:40px;" id="crmSerchBtn" name="searchBtn" class="comBtn" value="搜&nbsp;索" ></td>
	  	      </tr>
    	</div>
<%--	</form>--%>
<%--	<form  >--%>
		<grid:grid from="webpage" formId="gridform" action="goods!findCrmOfferId.do" ajax="yes">
			<grid:header>
				<grid:cell width="40px">选择</grid:cell>
				<grid:cell width="300px">CRM销售品名称</grid:cell>
				<grid:cell >CRM销售品ID</grid:cell>
			</grid:header>
			<grid:body item="crm">
				<grid:cell>&nbsp;
					<input type="radio" name="crmRadio" crmName="${crm.offer_name}" crmId="${crm.offer_id}" />
				</grid:cell>
				<grid:cell>&nbsp;
					${crm.offer_name }
				</grid:cell>
				<grid:cell>&nbsp;${crm.offer_id}</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
<div class="submitlist" align="center">
	<table>
		<tr>
			<td>
				<input type="button" style="margin-left:10px" value="确定" class="submitBtn" name='submitBtn' />
			</td>
		</tr>
	</table>
</div>
</div>
