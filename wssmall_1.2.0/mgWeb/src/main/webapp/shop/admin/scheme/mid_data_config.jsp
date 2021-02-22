<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <div class="searchformDiv">
   <a href="schemeAction!addMid.do?plan_id=${plan_id }" style="margin-right:10px;" plan_id=${plan_id } name="addAccountss" class="graybtn1" >新增</a>	 
</div> 
 <div class="grid" id="selectChoices" >	
 <input name="plan_id" type="hidden" value="${plan_id }">
 <form id="gridform" class="grid" ajax="yes">
	<table>
		<tr>
			<td>业务方案编码</td>
			<td>中间数据编码</td>
			<td>计算方式</td>
			<td>计算逻辑</td>
			<td>Fact的Java类型</td>
			<td>是否需要处理结果</td>
			<td>状态</td>
			<td>操作</td>
		</tr>
		<c:forEach var="acc" items="${midData}">
		<tr>
			<td>${acc.plan_id}</td>
			<td>${acc.mid_data_code}</td>
			<td>${acc.cal_type }</td>
			<td>${acc.cal_logic }</td>
			<td>${acc.fact_java_class }</td>
			<td>
				<c:if test="${acc.need_process_data eq 'T'}">处理</c:if>
				<c:if test="${acc.need_process_data eq 'F'}">不处理</c:if>
			</td>
			<td>
				<c:if test="${acc.status_cd eq '00N'}">新建</c:if>
				<c:if test="${acc.status_cd eq '00X'}">无效</c:if>
				<c:if test="${acc.status_cd eq '00A'}">有效</c:if>
				<c:if test="${acc.status_cd eq '00M'}">审核中</c:if>
			</td>
			<td><a href="schemeAction!updateMid.do?mid_data_id=${acc.mid_data_id }&plan_id=${acc.plan_id}"  name="addBtn3"  mid_data_id="${acc.mid_data_id }"  class="updateMid" value="${acc.mid_data_id}">修改</a>	</td>
		</tr>
		</c:forEach>
	</table>
</form>
</div>	   

<div title="新增支付方式" id ="addAccountDialogssss"></div> 

