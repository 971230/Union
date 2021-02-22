<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 

	   
	    <%-- 银行列表 --%>
	 	<div align="center" id="bankList" >
	 			<c:set var="count" value="0"></c:set>	
					<c:forEach items="${bankList}" var="bank" varStatus="status">   
						<c:if test="${bank.bank_type == '00A'}">
							<c:set var="count" value="${count + 1}"></c:set>
							<%-- <input   type="radio" name="payment.bank_id"  value="${bank.bank_id}"/>&nbsp; --%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input   type="radio" name="bankId"  value="${bank.bank_id}"/>&nbsp;
							<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:130px;">
							<c:if test="${count%4 == 0}">
								<br/><br/>
							</c:if>
						</c:if>
				</c:forEach>
	 	</div>
