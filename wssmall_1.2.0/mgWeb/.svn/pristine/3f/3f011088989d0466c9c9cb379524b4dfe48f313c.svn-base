<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.division th{font-weight:normal;}
-->
</style>    
<div class="tableform">
<div class="submitlist">
 <table style="width: 80%;">
	 <tr>
	 	<td style="text-align: left;"><h4>物流列表</h4></td>
	 	<td style="text-align: right;">
           <input type="button" value="新增" class="submitBtn" name='flow_add_show' style="display:${logi_id==0?'block':'none' };"/>
	   </td>
	</tr>
 </table>
</div> 
<table cellspacing="10" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
			<td style="vertical-align: top;">
			<div class="division">
			<table cellspacing="0" cellpadding="0" border="0" class="gridlist" width="100%">
				<thead>
					<tr>
						<th style="width: 40px;">时间</th>
						<th style="width: 60px;">物流公司名称</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${flowList }" var="flow">
					<tr>
						<td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${flow.create_time}" /></td>
						<td>${flow.logi_name }</td>
						<td>${flow.description }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			</td>
		</tr>
	</tbody>
</table>
</div>